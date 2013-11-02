package com.brightcove.proserve.mediaapi.wrapper.apiobjects;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Not a real Media API object - a wrapper object to represent a list of Playlist objects.</p>
 * 
 * @author Sander Gates <three.4.clavins.kitchen @at@ gmail.com>
 *
 */
public class Playlists extends ArrayList<Playlist> {
	private static final long serialVersionUID = 232810143858103556L;
	
	private Integer totalCount = 0;
	
	public Playlists(JSONObject jsonObj) throws JSONException {
		JSONArray jsonItems = jsonObj.getJSONArray("items");
		for(int itemIdx=0;itemIdx<jsonItems.length();itemIdx++){
			JSONObject jsonItem = (JSONObject)jsonItems.get(itemIdx);
			Playlist playlist = new Playlist(jsonItem);
			add(playlist);
		}
		
		try{
			totalCount = jsonObj.getInt("total_count");
		}
		catch(JSONException jsone){
			// Don't fail altogether
			totalCount = -1;
		}
	}
	
	public Integer getTotalCount(){
		return this.totalCount;
	}
}
