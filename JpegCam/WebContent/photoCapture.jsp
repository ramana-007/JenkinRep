<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>JPEGCam Test Page 2</title>
	<meta name="generator" content="TextMate http://macromates.com/">
	<meta name="author" content="Joseph Huckaby">
	<!-- Date: 2008-03-15 -->
	<link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/font-awesome.css">
	<script async="" src="css/pubads_impl_115.js"></script>
    <link rel="stylesheet" href="css/bootsnipp.css">
   
   
</head>


<body>
<div class="panel panel-info">
	<div class="panel-heading" style='background-color:#337ab7 '>
          <h3 class="panel-title" style = 'color:#ffffff' ><center>Capture Photo</center></h3>
    </div>
    </div>
	<br>
	<br>
	<table align="center" ><tr><td valign=top >
	<!-- <h1>Take a PhotoShot </h1> -->
		
	<!-- First, include the JPEGCam JavaScript Library -->
	<script type="text/javascript" src="webcam.js"></script>
	
	<!-- Configure a few settings -->
	<script language="JavaScript">
		webcam.set_api_url( 'webcam' );
		webcam.set_quality( 90 ); // JPEG quality (1 - 100)
		webcam.set_shutter_sound( true ); // play shutter click sound
	</script>
	
	<!-- Next, write the movie to the page at 320x240 -->
	<script language="JavaScript">
		document.write( webcam.get_html(320, 240) );
	</script>
	
	<!-- Some buttons for controlling things -->
	<br/>
	<br>
	<br>
	<form>
		<!-- <input type=button value="Configure..." onClick="webcam.configure()">
		&nbsp;&nbsp;
		<input type=button value="Capture" onClick="webcam.freeze()">
		&nbsp;&nbsp;
		<input type=button value="Upload" onClick="do_upload()">
		&nbsp;&nbsp;
		<input type=button value="Reset" onClick="webcam.reset()"> -->
		
		<a href="#" class="btn btn-primary"	 onClick="webcam.configure()">Configure	</a> &nbsp;&nbsp;
		<a href="#" class="btn btn-primary"	 onClick="webcam.freeze()">Capture		</a> &nbsp;&nbsp;
		<a href="#" class="btn btn-primary"	 onClick="webcam.upload()"		>Upload		</a> &nbsp;&nbsp;
		<a href="#" class="btn btn-primary"	 onClick="webcam.reset()"	>Reset		</a>
         
	</form>
	
	<!-- Code to handle the server response (see test.php) -->
	<script language="JavaScript">
		webcam.set_hook( 'onComplete', 'my_completion_handler' );
		
		/* function do_upload() {
			// upload to server
			//document.getElementById('upload_results').innerHTML = '<h1>Uploading...</h1>';
			webcam.upload();
		} */
		
		function my_completion_handler(msg) {
			// extract URL out of PHP output
			if (msg.match(/(https\:\/\/\S+)/)) {
				var image_url = RegExp.$1;
				// show JPEG image in page
				/* document.getElementById('upload_results').innerHTML = 
					'<h1>Upload Successful!</h1>' + 
				//	'<h3>JPEG URL: ' + image_url + '</h3>' + 
					'<img src="' + msg + '">';
				
				// reset camera for another shot */
				//alert('image message location'+msg);
				
				if (window.opener != null && !window.opener.closed) {
				
					window.opener.document.getElementById("imageDiv").innerHTML="";
					alert('called second time '+msg);
		            var imageDiv = window.opener.document.getElementById("imageDiv");
		            
		           imageDiv.innerHTML = "<a href='javascript:void(0)' onclick= 'openPopup()'><img src='" + msg + "'/></a>";
					  
					  // 'itemcustom("+ ch +")'>Click Me </a>"; 
					
				//	'<a href = '"javascript:void();"'  onclick='"openPopup()"' style="cursor: pointer"><img alt="User Pic"  src="'+ msg +'" classx="img-circle img-responsive"></a>';
					
		            /* imagePathFrmChild.value = msg;
		            alert('imagePathFrmChild '+imagePathFrmChild.value);
		            
		            
		            document.getElementById('upload_results').innerHTML = 
					'<h1>Upload Successful!</h1>' + 
				//	'<h3>JPEG URL: ' + image_url + '</h3>' + 
					'<img src="' + msg + '">' */
		        }
		        window.close();
				
				webcam.reset();
			}
			else alert("Error while uploading: " + msg);
		}
	</script>
	
	</td>
	<td width=50>&nbsp;</td><td valign=top>
		<div id="upload_results" style="background-color:#eee;"></div>
	</td> 
	</tr></table>
</body>


</html>