package FileManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;

//file conversion
@WebServlet("/fileConvert")
public class FileConvertion extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName=getFileName( (String)req.getAttribute("fileName"));							
		
		String extentionName=(String) req.getAttribute("convertedFileType");

		Config.setDefaultSecret("CbIZuWLjNHBprdAn"); //Get your secret at https://www.convertapi.com/a

		String resourcePath = getServletContext().getInitParameter("UploadPath")+"\\"+(String)req.getAttribute("fileName");		
		

		String convertedPath=getServletContext().getInitParameter("convertedPath");								
		
		
		

		ConvertApi.convertFile(resourcePath, convertedPath +"\\"+ fileName+ extentionName);				
			
		req.setAttribute("fileNameWithDot", fileName);													
		
		System.out.println("File convertion Statred...");
		System.out.println("Original Uploaded File path: "+resourcePath);
		System.out.println("Converted File Path->"+convertedPath +"\\"+ fileName+ extentionName);
		System.out.println("File convertion ended...\n");
		
		req.getRequestDispatcher("Download").forward(req, resp);	

	}
	
	//milky-way-5295160_1280.jpg
	private String getFileName(String fileName) {		
		 int dotIndex = fileName.lastIndexOf('.');
	        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
	            return fileName.substring(0, dotIndex+1);
	        }
	        return fileName; //milky-way-5295160_1280.
	}
}


