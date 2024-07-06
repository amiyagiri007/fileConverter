<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  errorPage = "Error500.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <title>File Convert Site</title>
    <link rel="stylesheet" href="../Asset/Css/styles.css">
</head>

<body>
    <div id="shapeContainer" class="shape">
        <div class="container">
            <h1>File Converter</h1>
            <p>Effortlessly convert your files between different formats.</p>
		<form action="../../checkConverion" method="post" enctype="multipart/form-data">
		
            <label for="fileUpload">Upload File:</label>
            <input type="file" id="fileUpload" name="fileFromDevice">

            <div class="hr-sect">OR</div>
            <input type="text" id="urlInput" placeholder="Enter URL" name="FileWebUrl">

            <div class="file-type">
                 <input type="text" id="convertedFileType" name="convertedFileType" placeholder="Converted File Type:">
            </div>

            <span style="float: right;">
            <br>
             <button id="convertBtn" type="submit">Convert</button>
            <button id="resetBtn" class="hidden">Reset</button>
        </span>
	 </form>    
            <div id="progressContainer" class="hidden">
                <div id="progressBarContainer">
                    <div id="progressBar"></div>
                    <div id="progressText">0%</div>
                </div>
            </div>
            <br>
        <b class="errorMsg" id="errorText">Sorry file conversion is not possible</b>
        </div>
    </div>
             
    
    <script src="../Asset/Script/script.js"></script>
</body>

</html>