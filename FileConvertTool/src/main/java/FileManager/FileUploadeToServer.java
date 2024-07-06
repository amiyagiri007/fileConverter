package FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB check size of file from api
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class FileUploadeToServer extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			final String UPLOAD_DIRECTORY = getServletContext().getInitParameter("UploadPath");

			String FileUrl = req.getParameter("FileWebUrl");

			String ExtType = req.getParameter("convertedFileType");

			Part filePart = req.getPart("fileFromDevice");

			String fileName = getFileName(filePart);
			

			File tempFile = new File(UPLOAD_DIRECTORY + "\\" + fileName);

			try (InputStream inputStream = filePart.getInputStream();
					OutputStream outputStream = new FileOutputStream(tempFile)) {

				byte[] buffer = new byte[1024];

				int bytesRead;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				
				System.out.println("File upload started...");
				System.out.println("File name: "+fileName);
				System.out.println("File upload Ended...\n");
				
				req.setAttribute("convertedFileType", ExtType);
				req.setAttribute("FileUrl", FileUrl);
				req.setAttribute("fileName", fileName);

				RequestDispatcher rd = req.getRequestDispatcher("/fileConvert");
				rd.forward(req, resp);

			}
		}


	//method used for extracting filename output:abc.pdf
	private String getFileName(Part part) {
		String contentDispositionHeader = part.getHeader("content-disposition");
		String[] elements = contentDispositionHeader.split(";");
		for (String element : elements) {
			if (element.trim().startsWith("filename")) {
				return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");	
			}
		}
		return "unknown";
	}
}
