/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkwithweb.jpegcam;

/**
 *
 * @author Administrator
 */
  
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.OutputStream;
//import java.net.URI;
//import java.util.Collections;
import java.util.Hashtable;
//import java.util.Base64;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {
        
        private OutputStream generatedFileStream;
        
        private File logoFile;

        /** Main function for console app. Just launch without parameters
         * @param args
         */
        public static void main(String[] args) {
             //   BufferedReader appInput = new BufferedReader(new InputStreamReader(System.in));
        	String header_info="BEGIN:VCARD";
        //	String version="VERSION:2.1";
        	   //vCard format has a new line separator at the end of the code, we only need to put that later
        	String Contact_name="N:J V RamanaMurthy IBM"; //N: is the prefix for MECARD Name
        	String Contact_company="ORG:IBM"; //ORG: is the prefix for company
            String phone_number="TEL:7032241164";//TEL: is the prefix for telephone number
            String website="URL:--";//URL: is the prefix for website
        	String contact_email="EMAIL:vjammala@in.ibm.com";//EMAIL: is the prefix for email address
        	String address="ADR;TYPE=work:Mindspace";//ADR: is the prefix for address
        	String notes="NOTE:I am your collegue";//NOTE: is the prefix for notes.
        	
        	//File f = new File("C:/QRCode/profilepic.jpg");
        	try {
        /*	ByteArrayOutputStream output = new ByteArrayOutputStream();
        	FileInputStream fileIutputStream= new FileInputStream(f);
        	int res,bytesRead;
			while ((res = fileIutputStream.read()) != -1) {
				output.write(res);
			}
			byte[] bytes = output.toByteArray();
        	
        	String encodedString = Base64.getEncoder().encodeToString(bytes); */
        	
        	//String profilePic="PHOTO;JPEG:http://i.investopedia.com/profileimages/fa/sampleprofilerobfox14732806905259.jpg?quality=80&height=50";
        //	System.out.println("encodedPhoto::"+profilePic);
        	String footer="END:VCARD";
        	//Construct one final contact string in MECARD format
        	String final_vCard=String.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s", header_info,Contact_name, Contact_company, phone_number, website,contact_email,address,notes,footer);
        	System.out.println("Vcard::"+final_vCard);
               // String data = "ramana murthy, how r u man?";
              //  System.out.print("Please enter data to encode: ");
//                while (data == null) {
//                        try {
//                                data = appInput.readLine().trim();
//                        } catch (IOException e1) {
//                                System.out.println(e1.getMessage());
//                        }
//                }
                QRCode generator = new QRCode();
                
                while (generator.getLogoFile() == null) {
                        try {
                               // System.out.print("Enter path to logo file: ");
                              //  String input = appInput.readLine().trim();
                                generator.setLogoFile( new File( "C:\\QRCode\\add.png"));
                                System.out.println("File accepted.");
                        } catch (Exception e) {
                                System.out.println(e.getMessage());
                        }
                }
                
                   
                
                while (generator.getGeneratedFileStream() == null) {
                        try { 
                               // System.out.print("Enter path to generated file: ");
                              //  String input = appInput.readLine().trim();
                                generator.setGeneratedFileStream( new FileOutputStream(
                                                new File( "C:\\QRCode\\QRCode.jpg")));
                                System.out.println("File accepted.");
                        } catch (Exception e) {
                                System.out.println(e.getMessage());
                        }
                }
                 
                generator.createQrCode( final_vCard, 300, "png");
                System.out.println("done.");
                try {
                        generator.getGeneratedFileStream().close();
                } catch (IOException ignored) {
                  System.out.println("In Catch Block");
                }
        	} catch (Exception e) {
    			e.printStackTrace();
    		} finally {

    		}
        }
        
 /* private static void OuterRect(Graphics2D graphics,int matrixWidth,Color BackColor,Color LineColor)
    {
       // To draw Transpency
        // graphics.setComposite(makeComposite(10*0.1F));
      //  graphics.setColor(BackColor);
     //   graphics.fillRect(0, 0, matrixWidth-10, matrixWidth-10);
       
        graphics.setColor(LineColor);
        graphics.fillRoundRect(0,0,matrixWidth-10,matrixWidth-10,5,5);

        graphics.setColor(BackColor);
        graphics.fillRoundRect(10,10,matrixWidth-20,matrixWidth-20,5,5);

    } */ 
         
        /**
         * Call this method to create a QR-code image. You must provide the
         * OutputStream where the image data can be written.
         *
         * @param content
         * The string that should be encoded with the QR-code.
         * @param qrCodeSize
         * The QR-code must be quadratic. So this is the number of pixel
         * in width and height.
         * @param imageFormat
         * The image format in which the image should be rendered. As
         * Example 'png' or 'jpg'. See @javax.imageio.ImageIO for more
         * information which image formats are supported.
         * @throws Exception
         * If an Exception occur during the create of the QR-code or
         * while writing the data into the OutputStream.
         */
        private void createQrCode(String content, int qrCodeSize, String imageFormat){
        try {
                // Correction level - HIGH - more chances to recover message
                Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap =
                                new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            // Generate QR-code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content,
                            BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
            
            // Start work with picture
            int matrixWidth = bitMatrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
                            BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
            graphics.setFont(font);
            graphics.setColor(Color.white); 
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);
            Color mainColor = new Color(51, 102, 153);
            graphics.setColor(mainColor);
            // Write message under the QR-code
           // graphics.drawString( content, 30, image.getHeight() - graphics.getFont().getSize());
          
            //Write Bit Matrix as image
            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                        
                    }
                }
            }
            
            // Add logo to QR code
            BufferedImage logo = ImageIO.read( this.getLogoFile());

            //scale logo image and insert it to center of QR-code
            double scale = calcScaleRate(image, logo);
            logo = getScaledImage( logo,
                            (int)( logo.getWidth() * scale),
                            (int)( logo.getHeight() * scale) );
            graphics.drawImage( logo,
                            image.getWidth()/2 - logo.getWidth()/2,
                            image.getHeight()/2 - logo.getHeight()/2,
                            image.getWidth()/2 + logo.getWidth()/2,
                            image.getHeight()/2 + logo.getHeight()/2,
                            0, 0, logo.getWidth(), logo.getHeight(), null);
            
            //Add border to QR code
//            graphics.drawRect(5, 5, image.getWidth()-1, image.getHeight()-1);
            graphics.drawRoundRect(5, 5, image.getWidth()-10, image.getHeight()-10,5,5);
         
                       
            // Check correctness of QR-code
            if ( isQRCodeCorrect(content, image,hintMap)) {
                    ImageIO.write(image, imageFormat, this.getGeneratedFileStream());
                    System.out.println("Your QR-code was succesfully generated.");
            } else {
                    System.out.println("Sorry, your logo has broke QR-code. ");
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
        
        /**
         * Calc scale rate of logo. It is 30% of QR-code size
         * @param image
         * @param logo
         * @return
         */
        private double calcScaleRate(BufferedImage image, BufferedImage logo){
                 double scaleRate = logo.getWidth() / image.getWidth();
         if (scaleRate > 0.3){
                 scaleRate = 0.3;
         } else {
                 scaleRate = 1;
         }
                 return scaleRate;
        }
        
        /**
         * Check is QR-code correct
         * @param content
         * @param image
         * @return
         */
        private boolean isQRCodeCorrect(String content, BufferedImage image,Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap){
                boolean result = false;
                Result qrResult = decode(image,hintMap);
                if (qrResult != null && content != null && content.equals(qrResult.getText())){
                        result = true;
                }                
                return result;
        }
        
        /**
         * Decode QR-code.
         * @param image
         * @return
         */
        private Result decode(BufferedImage image,Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap){
                if (image == null) {
                        return null;
                }
                try {
                        LuminanceSource source = new BufferedImageLuminanceSource(image);        
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));        
                        Result result = new MultiFormatReader().decode(bitmap,hintMap);         
                        return result;
                } catch (NotFoundException nfe) {
                        nfe.printStackTrace();
                        return null;
                }
        }
        
        /**
         * Scale image to required size
         * @param image
         * @param width
         * @param height
         * @return
         * @throws IOException
         */
        private BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
                int imageWidth = image.getWidth();
         int imageHeight = image.getHeight();

         double scaleX = (double)width/imageWidth;
         double scaleY = (double)height/imageHeight;
         AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
         AffineTransformOp bilinearScaleOp = new AffineTransformOp(
                         scaleTransform, AffineTransformOp.TYPE_BILINEAR);

         return bilinearScaleOp.filter(
         image,
         new BufferedImage(width, height, image.getType()));
        }

        public OutputStream getGeneratedFileStream() {
                return generatedFileStream;
        }

        public void setGeneratedFileStream(OutputStream generatedFileStream) {
                this.generatedFileStream = generatedFileStream;
        }

        public File getLogoFile() {
                return logoFile;
        }

        public void setLogoFile(File logoFile) {
                this.logoFile = logoFile;
        }
        
}