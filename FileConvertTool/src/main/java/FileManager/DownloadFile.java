package FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Download")
public class DownloadFile extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String ExtType = req.getParameter("convertedFileType").toLowerCase();					
		
		String filename= (String) req.getAttribute("fileNameWithDot");							
		
		filename=filename.substring(0,filename.length());										
		
		String filePath = getServletContext().getInitParameter("convertedPath") + "\\"			
				+ filename+ExtType;																
		
		System.out.println("Download statred...");
		System.out.println("Downloded File Name: "+filename);
		System.out.println("Downloaded File location: "+filePath);
		System.out.println("Download ended...");
		
		File file = new File(filePath);															
		
		if (!file.exists()) {																	
            // Handle the case where the file does not exist
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");					
            return;
        }
		
        FileInputStream fis = new FileInputStream(file);										
        
        int length = fis.available();															
        
        // Configure the response which type file, length of file(no of bytes) and download prompt
        resp.setContentType("application/octet-stream");												
        resp.setContentLength(length); 																	
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\""); 		
        
        ServletOutputStream outputStream = resp.getOutputStream();										
        
        byte[] buffer = new byte[4096];															
        int bytesRead;
        
        // Read from the file and write to the response output stream
        while ((bytesRead = fis.read(buffer)) != -1) {												
            outputStream.write(buffer, 0, bytesRead);												
        }
        
        outputStream.flush();									
        outputStream.close();											
        fis.close();											

	}
}
