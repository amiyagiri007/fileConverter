package FileManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/checkConverion")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB 
				maxFileSize = 1024 * 1024 * 100, // 100MB
				maxRequestSize = 1024 * 1024 * 200) // 200MB
public class CheckingFile extends HttpServlet{
	int responseCode=0;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Part filePart = req.getPart("fileFromDevice");
		
		String fileExt=getFileName(filePart);		//getting file name no extention

		String ConvExt=req.getParameter("convertedFileType").trim().toLowerCase();
		
		String urlFile=getFileExtName(req.getParameter("FileWebUrl").trim());
		
		URL url=null;
		
		System.out.println("Web URL: "+urlFile);
		try {
			if(urlFile==null || urlFile.isBlank()|| urlFile.isEmpty())
				url = new URL("https://v2.convertapi.com/info/canconvert/"+fileExt+"/to/"+ConvExt);
			else
			{	System.out.println("else block");
				url = new URL("https://v2.convertapi.com/info/canconvert/"+urlFile+"/to/"+ConvExt);
				req.getRequestDispatcher("/UrlDownload").forward(req, resp);
				return;
			}
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			connection.setRequestProperty("Authorization", "CbIZuWLjNHBprdAn");

			connection.connect();

			responseCode = connection.getResponseCode();

			connection.disconnect();
			
			System.out.println("Cheacking File supported formats...");
			System.out.println("Original File Type: "+fileExt);
			System.out.println("Convert File Type: "+ConvExt);
			System.out.println("Convertable Status Code: "+responseCode + "\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			req.getRequestDispatcher("/FileUploadServlet").forward(req, resp);
		}else {
			resp.sendRedirect(req.getContextPath()+"\\FileConvertWebsite\\Webpages\\convert.jsp");
		}
	}
	
	//extract File name and give extention 
	private String getFileName(Part part) {
		String contentDispositionHeader = part.getHeader("content-disposition");
		String[] elements = contentDispositionHeader.split(";");
		for (String element : elements) {
			if (element.trim().startsWith("filename")) {
				return getFileExtName(element.substring(element.indexOf('=') + 1).trim().replace("\"", ""));
			}
		}
		return "unknown";
	}
	
	//extract extention name from String File Name
	private String getFileExtName(String fileName) {
		 int dotIndex = fileName.lastIndexOf('.');
	        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
	            return fileName.substring( dotIndex+1,fileName.length());
	        }
	        return fileName; //jpg
	}
	
	
}
