package FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.convertapi.client.Config;
import com.convertapi.client.ConversionResult;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;

@WebServlet("/UrlDownload")
public class UrlDownload extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String ConvExt = req.getParameter("convertedFileType").trim().toLowerCase();
		
		String urlFileExt = getFileExtName(extractFileNameWithExtension(req.getParameter("FileWebUrl").trim()));
		
		String urlOfFile=req.getParameter("FileWebUrl").trim();
		
		String filename = extractFileNameWithoutExtension(
				extractFileNameWithExtension(req.getParameter("FileWebUrl").trim()));
		
		System.out.println("Convertable Status Code: 200\n");
		System.out.println("Download started...");
		System.out.println("Original File Type From URL: "+urlFileExt);  //pdf
		System.out.println("Convert File Type: "+ConvExt); //png
		System.out.println("File name: "+filename);  //dummy
		System.out.println("Converting remote File"+urlFileExt+" to "+ConvExt);
		System.out.println("Download ended...");
		
		
		Config.setDefaultSecret("CbIZuWLjNHBprdAn"); // Get your secret at https://www.convertapi.com/a

		
		CompletableFuture<ConversionResult> result = ConvertApi.convert(urlFileExt, ConvExt,
				new Param("file", urlOfFile));
		
		String uploadedpath=getServletContext().getInitParameter("UploadPath") + "\\" + filename+"."+ConvExt;
		Path Filesave = Paths
				.get(uploadedpath);
		
		try {
			result.get().saveFile(Filesave).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		
		File file = new File(uploadedpath);
		
		if (!file.exists()) {
            // Handle the case where the file does not exist
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }
		
        FileInputStream fis = new FileInputStream(file);
        
        int length = fis.available();
        
        // Configure the response
        resp.setContentType("application/octet-stream"); 
        resp.setContentLength(length); 
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\""); 
        
        ServletOutputStream outputStream = resp.getOutputStream();
        
        byte[] buffer = new byte[4096];
        int bytesRead;
        
        while ((bytesRead = fis.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        
        outputStream.flush();
        outputStream.close();
        fis.close();
	}

	// extract extention name from String File Name
	private String getFileExtName(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
			return fileName.substring(dotIndex + 1, fileName.length());
		}
		return fileName;
	}

	public static String extractFileNameWithExtension(String urlString) {
		String regex = ".*/([^/?]+)(?:\\.[^./?]+)?";
		// Extract file name without extension using the regex
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(urlString);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	public static String extractFileNameWithoutExtension(String filenamewithExt) {
		int lastDotIndex = filenamewithExt.lastIndexOf('.');
		if (lastDotIndex != -1) {
			return filenamewithExt.substring(0, lastDotIndex);
		} else {
			return null;
		}
	}
}
