package ie.gmit.sw;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServicePollHandler extends HttpServlet {
	private static final long serialVersionUID = 42L;
	
	private OutQueueService outQueueService;

	public void init() throws ServletException {
		try {
			this.outQueueService = OutQueueService.getInstance();
		} catch (Exception e) {
			System.out.println("Queue Error occurred with RabbitMQ.");
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		outQueueService.consumeResponse();
		
		resp.setContentType("text/html"); 
		PrintWriter out = resp.getWriter(); 
		
		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		int counter = 1;
		if (req.getParameter("counter") != null){
			counter = Integer.parseInt(req.getParameter("counter"));
			counter++;
		}

		out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");		
		out.print("</head>");		
		out.print("<body>");
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document Title: " + title + "</H3>");
		
		Response response = outQueueService.pollResponse(taskNumber);
		
		if(response == null) {
			out.print("<b><font color=\"ff0000\">A total of " + counter + " polls have been made for this request.</font></b> ");
			out.print("Place the final response here... a nice table (or graphic!) of the document similarity...");
			
			out.print("<form name=\"frmRequestDetails\">");
			out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
			out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
			out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
			out.print("</form>");								
			out.print("</body>");	
			out.print("</html>");	
			
			out.print("<script>");
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);"); //Refresh every 5 seconds
			out.print("</script>");
		} else {
			out.print("<h2>" + response.toString() + "</h2>");
			out.print("<a href=\"index.jsp\">Home - Try another document</a>");
			out.print("</body>");	
			out.print("</html>");	
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}