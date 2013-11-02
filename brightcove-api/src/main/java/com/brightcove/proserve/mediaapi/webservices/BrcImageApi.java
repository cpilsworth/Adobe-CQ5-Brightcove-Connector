package com.brightcove.proserve.mediaapi.webservices;
import java.awt.image.*;
import com.sun.image.codec.jpeg.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brightcove.proserve.mediaapi.webservices.BrcService;
import com.brightcove.proserve.mediaapi.webservices.BrcUtils;
import com.brightcove.proserve.mediaapi.wrapper.ReadApi;
import com.brightcove.proserve.mediaapi.wrapper.apiobjects.Video;
import com.brightcove.proserve.mediaapi.wrapper.apiobjects.enums.VideoFieldEnum;
import com.brightcove.proserve.mediaapi.wrapper.utils.CollectionUtils;

@Service
@Component
@Property(name = "sling.servlet.paths", value = "/bin/brightcove/image")
public class BrcImageApi extends SlingAllMethodsServlet {
	
	@Override
	protected void doPost(final SlingHttpServletRequest request,
            final SlingHttpServletResponse response) throws ServletException,
            IOException {
    		PrintWriter outWriter = response.getWriter();
    		response.setStatus(404);
    		
			

    }
    @Override
    protected void doGet(final SlingHttpServletRequest request,
            final SlingHttpServletResponse response) throws ServletException,
            IOException {
    	
    	BrcService brcService = BrcUtils.getSlingSettingService();
    	String ReadToken = brcService.getReadToken();
    	int requestedAPI = 0;
    	String requestedToken="";
    	if (request.getParameter("id") != null) {
    	       Logger logger = LoggerFactory.getLogger(BrcImageApi.class);
    	       String VideoIDStr = request.getParameter("id");
    	       Long   videoId = Long.parseLong(VideoIDStr); 
    	    // Return only ID and Name on all videos
    	       EnumSet<VideoFieldEnum> videoFields = VideoFieldEnum.CreateEmptyEnumSet();
    	       videoFields.add(VideoFieldEnum.ID);
    	       videoFields.add(VideoFieldEnum.VIDEOSTILLURL);
    	       
    	       // Return no custom fields on all videos
    	       Set<String> customFields = CollectionUtils.CreateEmptyStringSet();
    	       
    	       // Create the Read API wrapper
    	       ReadApi rapi = new ReadApi(logger);
    	       
    	       Video       found     = null;
    	       java.util.List<Video> videoList = null;
    	       
    	       try{
    	           // Find a single video
    	           found = rapi.FindVideoById(ReadToken, videoId, videoFields, customFields);
    	           if (found != null) {
    	               String urlStr = found.getVideoStillUrl();
    	               
    	               URL url = new URL(urlStr);
    	               BufferedImage img = null;
    	               try{
    	                   img = ImageIO.read(url);
    	                   //out.println(" READ SUCCESS" + "<br>");
    	            }catch(Exception e) {
    	                    response.setStatus(404);
    	                    PrintWriter outWriter = response.getWriter();
    	                    outWriter.println("READ ERROR "  + "<br>");
    	                    
    	            }
    	            if (img != null) {
    	                try {
    	                        response.setContentType("image/jpeg");
    	                        ImageIO.write(img, "jpeg", response.getOutputStream());
    	                     }catch(Exception ee) {
    	                                response.setStatus(404);
    	                                response.setContentType("text/html");
    	                                PrintWriter outWriter = response.getWriter();
    	                                outWriter.println("ENCODING ERROR "  + "<br>");
    	                }
    	            } 
    	        //out.write("aaa");
    	       } else {
    	           response.setStatus(404);
    	           
    	       }
    	               
    	       }
    	       catch(Exception e){
    	           //System.out.println("Exception caught: '" + e + "'.");
    	           //System.exit(1);
    	           response.setStatus(404);
    	       }
    	} else {
    	    if (request.getRequestPathInfo().getSuffix() != null) {
    	        String vidID= request.getRequestPathInfo().getSuffix();
    	        vidID = vidID.substring(vidID.lastIndexOf("/"));
    	        vidID = vidID.substring(1,vidID.indexOf("."));
    	        Logger logger = LoggerFactory.getLogger("Brightcove");
    	        Long   videoId = Long.parseLong(vidID); 
    	     // Return only ID and Name on all videos
    	        EnumSet<VideoFieldEnum> videoFields = VideoFieldEnum.CreateEmptyEnumSet();
    	        videoFields.add(VideoFieldEnum.ID);
    	        videoFields.add(VideoFieldEnum.VIDEOSTILLURL);
    	        
    	        // Return no custom fields on all videos
    	        Set<String> customFields = CollectionUtils.CreateEmptyStringSet();
    	        
    	        // Create the Read API wrapper
    	        ReadApi rapi = new ReadApi(logger);
    	        
    	        Video       found     = null;
    	        java.util.List<Video> videoList = null;
    	        
    	        try{
    	            // Find a single video
    	            found = rapi.FindVideoById(ReadToken, videoId, videoFields, customFields);
    	            if (found != null) {
    	                String urlStr = found.getVideoStillUrl();
    	                
    	                URL url = new URL(urlStr);
    	                BufferedImage img = null;
    	                 try{
    	                         img = ImageIO.read(url);
    	                         //out.println(" READ SUCCESS" + "<br>");
    	                 }catch(Exception e) {
    	                         response.setStatus(404);
    	                         PrintWriter outWriter = response.getWriter();
    	                         outWriter.println("READ ERROR "  + "<br>");
    	                         
    	                 }
    	                 if (img != null) {
    	                     try {
    	                             response.setContentType("image/jpeg");
    	                             ImageIO.write(img, "jpeg", response.getOutputStream());
    	                          }catch(Exception ee) {
    	                                     response.setStatus(404);
    	                                     response.setContentType("text/html");
    	                                     PrintWriter outWriter = response.getWriter();
    	                                 	
    	                                     outWriter.println("ENCODING ERROR "  +ee.getMessage()+ "<br>");
    	                     }
    	                 } 
    	              //out.write("aaa");
    	            } else {
    	                response.setStatus(404);
    	                
    	            }
    	                
    	        }
    	        catch(Exception e){
    	            //System.out.println("Exception caught: '" + e + "'.");
    	            //System.exit(1);
    	            response.setStatus(404);
    	        }
    	        //out.write(vidID);
    	    }
    	    //{out.write("{\"items\":[],\"results\":0}");}
    	}
    }

}
