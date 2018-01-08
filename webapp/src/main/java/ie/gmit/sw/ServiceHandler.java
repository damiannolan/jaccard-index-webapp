package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ie.gmit.sw.documents.Document;
import ie.gmit.sw.documents.DocumentFactory;

/* NB: You will need to add the JAR file $TOMCAT_HOME/lib/servlet-api.jar to your CLASSPATH 
 *     variable in order to compile a servlet from a command line.
 */
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB. The file size in
														// bytes after which the
														// file will be
														// temporarily stored on
														// disk. The default
														// size is 0 bytes.
		maxFileSize = 1024 * 1024 * 10, // 10MB. The maximum size allowed for
										// uploaded files, in bytes
		maxRequestSize = 1024 * 1024 * 50) // 50MB. he maximum size allowed for
											// a multipart/form-data request, in
											// bytes.
public class ServiceHandler extends HttpServlet {
	private static final long serialVersionUID = 42L;

	/*
	 * Declare any shared objects here. For example any of the following can be
	 * handled from this context by instantiating them at a servlet level: 1) An
	 * Asynchronous Message Facade: declare the IN and OUT queues or
	 * MessageQueue 2) An Chain of Responsibility: declare the initial handler
	 * or a full chain object 1) A Proxy: Declare a shared proxy here and a
	 * request proxy inside doGet()
	 */
	private String appTitle = null;
	private static long jobNumber = 0;
	private InQueueService inQueueService;

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();										
		appTitle = ctx.getInitParameter("APPLICATION_TITLE");
		
		try {
			// Get a handle on the InQueueService
			this.inQueueService = InQueueService.getInstance();
		} catch (Exception e) {
			System.out.println("Queue Error occurred. Make sure RabbitMq is setup correctly");
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		Part part = req.getPart("txtDocument");
		
		DocumentFactory df = DocumentFactory.getInstance();
		Document doc = df.newDocument(title, part.getInputStream());
		System.out.println(doc.toString());
		System.out.println(doc.text());
	
		// We could use the following to track asynchronous tasks. Comment it
		// out otherwise...
		if (taskNumber == null) {
			taskNumber = new String("T" + jobNumber);
			jobNumber++;
			
			Request request = new Request(doc, taskNumber);
			// Add job to in-queue
			inQueueService.queueRequest(request);
			
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/poll");
			dispatcher.forward(req, resp);
			// Check out-queue for finished job with the given taskNumber
		}

		// Set ContentType of response and get handle on PrintWriter
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		// Process the input and write out the response.
		out.print("<html><head><title>" + appTitle + "</title>");
		out.print("</head>");
		out.print("<body>");

		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document Title: " + title + "</H3>");

		// Output some useful information for you (yes YOU!)
		out.print("<div id=\"r\"></div>");
		out.print("<font color=\"#993333\"><b>");
		out.print("Environmental Variable Read from web.xml: " + appTitle);
		out.print(
				"<br>This servlet should only be responsible for handling client request and returning responses. Everything else should be handled by different objects.");
		out.print(
				"Note that any variables declared inside this doGet() method are thread safe. Anything defined at a class level is shared between HTTP requests.");
		out.print("</b></font>");

		// We can also dynamically write out a form using hidden form fields.
		// The form itself is not
		// visible in the browser, but the JavaScript below can see it.
		out.print("<form name=\"frmRequestDetails\" action=\"poll\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		// JavaScript to periodically poll the server for updates (this is ideal
		// for an asynchronous operation)
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);"); // Refresh
																							// every
																							// 10
																							// seconds
		out.print("</script>");

		/*
		 * File Upload: The following few lines read the multipart/form-data
		 * from an instance of the interface Part that is accessed by Part part
		 * = req.getPart("txtDocument"). We can read bytes or arrays of bytes by
		 * calling read() on the InputStream of the Part object. In this case,
		 * we are only interested in text files, so it's as easy to buffer the
		 * bytes as characters to enable the servlet to read the uploaded file
		 * line-by-line. Note that the uplaod action can be easily completed by
		 * writing the file to disk if necessary. The following lines just read
		 * the document from memory... this might not be a good idea if the file
		 * size is large!
		 */
		out.print("<h3>Uploaded Document</h3>");
		out.print("<font color=\"0000ff\">");
		BufferedReader br = new BufferedReader(new InputStreamReader(part.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null) {
			// Break each line up into shingles and do something. The servlet
			// really should act as a
			// contoller and dispatch this task to something else... Divide and
			// conquer...! I've been
			// telling you all this since 2nd year...!
			out.print(line);
		}
		out.print("</font>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}