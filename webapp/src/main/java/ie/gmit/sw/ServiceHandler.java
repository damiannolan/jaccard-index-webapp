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

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ServiceHandler extends HttpServlet {
	private static final long serialVersionUID = 42L;

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
			inQueueService.consumeRequest();
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/poll");
			dispatcher.forward(req, resp);
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
		out.print("<br>Shinglizing Document. Calculating MinHash and Saving to Db4o. Calculating Jaccard Index.");
		out.print("Note: If the database file is empty your first upload should result in a 1.0 Jaccard Index");
		out.print("</b></font>");

		out.print("<form name=\"frmRequestDetails\" action=\"poll\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		// JavaScript to periodically poll the server for updates (this is ideal
		// for an asynchronous operation)
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
		out.print("</script>");

		// Spit out the Document to the browser
		out.print("<h3>Uploaded Document</h3>");
		out.print("<font color=\"0000ff\">");
		BufferedReader br = new BufferedReader(new InputStreamReader(part.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null) {
			out.print(line);
		}
		out.print("</font>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}