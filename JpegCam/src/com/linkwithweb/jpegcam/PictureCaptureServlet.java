/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
package com.linkwithweb.jpegcam; 

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.IOUtils;
   

/**
 * Ashwin Kumar
 * Servlet implementation class PictureCaptureServlet  
 */
public class PictureCaptureServlet extends HttpServlet {   
	private static final long serialVersionUID = 1L;    
	private String fileStoreURL = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PictureCaptureServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
              
		fileStoreURL = config.getServletContext().getRealPath("") + "/uploads";
		try {
			File f = new File(fileStoreURL);
			fileStoreURL = f.getAbsolutePath();
			
			//System.out.println("path is  is "+f.getAbsolutePath());
			if (!f.exists()) {
				f.mkdirs();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			
			byte[] bytes;
			//byte[] buffer = new byte[8192];
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			System.out.println("contextPath=="+fileStoreURL);
			
		InputStream inputStream=request.getInputStream();
		System.out.println("inputStream="+inputStream.toString());
		String binaryImage=inputStream.toString();
		StringTokenizer token=new StringTokenizer(binaryImage);
		String imageToken=null;
		
			imageToken=token.nextToken(",");
			Base64.getDecoder().decode(imageToken);
			FileOutputStream fileOutputStream = new FileOutputStream(
					fileStoreURL + "/"+"sample"+".jpg");
			int res,bytesRead;
		while ((res = imageToken.read()) != -1) {
				fileOutputStream.write(res);
				// output.write(res);
			}    
										    
			/**     
			 * To make sure each url is differeent and not cached added time to tit
			 */      
			//IOUtils.copy(request.getInputStream(), response.getOutputStream());  
		/*	bytes = output.toByteArray();   
		 *     
			String encodedString = Base64.getEncoder().encodeToString(bytes);	
			System.out.println("Base64image::::"+encodedString);
			fileOutputStream.close();
			response.getWriter().append(encodedString); */
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally {  
		}
	}
}