package ie.gmit.sw;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class OutQueueService {
	private static OutQueueService instance;
	
	private final static String HOST = "localhost";
	private final static String QUEUE_NAME = "OUTQUEUE";
	private Connection connection;
	private Channel channel;
	private Consumer consumer;
	private HashMap<String, Response> responseMap;
	
	/*
	 * Get a handle on the RabbitMQ Connection Factory
	 * Create a new Connection
	 * Create a new Channel
	 */
	private OutQueueService() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		this.connection = factory.newConnection();
		this.channel = connection.createChannel();
		this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		this.consumer = new InnerResponseHandler(channel);
		this.responseMap = new HashMap<String, Response>();
	}
	
	/*
	 * Static getInstance() method to get a handle on the instance
	 */
	public static OutQueueService getInstance() throws Exception {
		if(instance == null) {
			instance = new OutQueueService();
		}
		return instance;
	}
	
	/*
	 * Serialize the response from the queue
	 * via channel.basicPublish() using SerializationUtils
	 */
	public void queueResponse(Response response) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(response));
	}
	
	/*
	 * Consume the Response from the queue
	 * via channel.basicConsume() passing the Consumer defined below
	 */
	public void consumeResponse() throws IOException {
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
	public Response pollResponse(String taskNumber) {
		// Retrieve the response obj from the HashMap
		Response response = responseMap.get(taskNumber);
		// Remove the response obj and return
		responseMap.remove(taskNumber);
		return response;
	}
	
	private class InnerResponseHandler extends DefaultConsumer {
		
		private InnerResponseHandler(Channel channel) {
			super(channel);
		}
		
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException {
			// Deserialize the body to a Response obj
			Response response = (Response) SerializationUtils.deserialize(body);
			// Log the response
			System.out.println("[DEBUG] Logging response @HandleDelivery: " + response.toString());
			// Store in the HashMap
			responseMap.put(response.getTaskNumber(), response);
		}
	}
	
}
