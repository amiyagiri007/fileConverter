<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  errorPage = "Error500.jsp" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Error 505</title>
<link rel="stylesheet" href="/FileConvertTool/FileConvertWebsite/Asset/Css/Error500.css">
<link
	href="https://fonts.googleapis.com/css?family=Encode+Sans+Semi+Condensed:100,200,300,400"
	rel="stylesheet">
</head>
<body class="loading">
	<h1>500</h1>
	<h2>
		Unexpected Error <b>:(</b> <br> If you continue to see this
		error, please contact <a href="mailto:kesol55@outlook.com">kesol55@outlook.com</a>
		<br>
		<a href="/FileConvertTool/FileConvertWebsite/Webpages/convert.jsp">Try Again</a>
	</h2>
	
	<div class="gears">
		<div class="gear one">
			<div class="bar"></div>
			<div class="bar"></div>
			<div class="bar"></div>
		</div>
		<div class="gear two">
			<div class="bar"></div>
			<div class="bar"></div>
			<div class="bar"></div>
		</div>
		<div class="gear three">
			<div class="bar"></div>
			<div class="bar"></div>
			<div class="bar"></div>
		</div>
	</div>

	<script type="text/javascript">
		document.addEventListener('DOMContentLoaded', function() {
			setTimeout(function() {
				document.body.classList.remove('loading');
			}, 1000);
		});
	</script>
</body>
</html>