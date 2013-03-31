//Track class will hold a sequence of clips. Tracks will be 
//played simultaneously with other tracks. Volume will be 
//adjustable at a track level as well. 
//
//Created by: Cam 3/30/13

package com.teamluper.luper;

import java.util.ArrayList;

public class Track {
	String ID = null;
	ArrayList <Clip>clips = new ArrayList<Clip>();
	//volume? an int?
	
	public Track(){
		ID = null;
		//clips?
	}
	
	//do we need another constructor? are we going to be passing
	//anything into the constructor?
	
	public String getID(){
		return this.ID;
	}
	
	public void setID(String newID){
		this.ID = newID;
	}
	
	public ArrayList<Clip> getClips(){
		return this.clips;
	}
	
	//a method that will allow you to add a clip to the track
	public void putClip(int start, Clip clip){
//		not sure how to do this, were going to need to find 
//		out what the start time is then add the clip to the 
//		array at the appropriate place
	}
	
	public void removeClip(Clip clip){
		//will need to remove the clip based on its name?
	}
	
//	will be used to select a portion of the track, then duplicate
//	that part 'howMany' times
	public void duplicate(int howMany){
		
	}
	//getVolume() and setVolume() will need to be added

}
