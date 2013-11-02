package com.brightcove.proserve.mediaapi.webservices;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import com.brightcove.proserve.mediaapi.webservices.BrcService;
import com.brightcove.proserve.mediaapi.webservices.BrcUtils;

@Service
@Component
@Property(name = "sling.servlet.paths", value = "/bin/brightcove/suggestions")
public class BrcSuggestions extends SlingAllMethodsServlet {
	
	@Override
	protected void doPost(final SlingHttpServletRequest request,
            final SlingHttpServletResponse response) throws ServletException,
            IOException {
    		PrintWriter outWriter = response.getWriter();
    		response.setContentType("application/json");
    		JSONObject root = new JSONObject();
    		
    		BrcService brcService = BrcUtils.getSlingSettingService();
    		String ReadToken = brcService.getReadToken();
    		String WriteToken = brcService.getWriteToken();
    		
    		int requestedAPI = 0;
    		String requestedToken="";
    		if (request.getParameter("query") != null) {
    		    response.setContentType("application/json");
    		    outWriter.write(BrcUtils.getList(ReadToken,"name,id,thumbnailURL",false,request.getParameter("start"),request.getParameter("limit"),request.getParameter("query")));
    		            	
    		               
    		} else {
    			outWriter.write("{\"items\":[],\"results\":0}");
    		}
    		
			

    }
    @Override
    protected void doGet(final SlingHttpServletRequest request,
            final SlingHttpServletResponse response) throws ServletException,
            IOException {
    		PrintWriter outWriter = response.getWriter();
    		response.setContentType("application/json");
    		JSONObject root = new JSONObject();
    		
    		BrcService brcService = BrcUtils.getSlingSettingService();
    		String ReadToken = brcService.getReadToken();
    		String WriteToken = brcService.getWriteToken();
    		
    		int requestedAPI = 0;
    		String requestedToken="";
    		if (request.getParameter("query") != null) {
    		    response.setContentType("application/json");
    		    if ("playlist".equalsIgnoreCase(request.getParameter("type"))) {
    		    	outWriter.write(BrcUtils.getPlaylistByID(request.getParameter("query"), request.getParameter("start"),request.getParameter("limit")));
    		    } else {
    		    	outWriter.write(BrcUtils.getSuggestions(request.getParameter("query"), request.getParameter("start"),request.getParameter("limit")));	
    		    }
    		    
    		} else {
    			outWriter.write("{\"items\":[],\"results\":0}");
    		}

    }

}
