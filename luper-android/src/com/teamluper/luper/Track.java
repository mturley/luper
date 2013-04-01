//Track class will hold a sequence of clips. Tracks will be 
//played simultaneously with other tracks. Volume will be 
//adjustable at a track level as well. 
//
//Created by: Cam 3/30/13

package com.teamluper.luper;

import java.util.ArrayList;

public class Track 
{
	String ID = null;
	ArrayList <Clip>clips = new ArrayList<Clip>();
	String[] playBackList;
	long duration;
	//volume? an int?
	
	public Track()
	{
		ID = null;
		duration = 0;
		//clips?
	}
	
	//do we need another constructor? are we going to be passing
	//anything into the constructor?
	
	public String getID()
	{
		return this.ID;
	}
	
	public void setID(String newID)
	{
		this.ID = newID;
	}
	
	//SIZE
	public int size()
	{
		return clips.size();
	}
	
	public ArrayList<Clip> getClips()
	{
		return this.clips;
	}
	public void createPBList()
	{
		for(int i = 0; i < clips.size(); i++)
		{
			playBackList[i]=clips.get(i).name;
		}
	}
	//gets the track length by calculating the length of all the clips it contains
	public void getTrackLength()
	{
		long sum = 0;
		for(Clip c:clips)
		{
			sum+=c.duration;
		}
		duration = sum;
	}
	
	//gets the clip at the end of the track
	public Clip getClip()
	{
		return clips.get(clips.size());
	}
	
	//gets the clip at the specified index
	public Clip getClip(int index)
	{
		return clips.get(index);
	}
	
	//adds the clip to back of list
	public void putClip(Clip clip)
	{
		clips.add(clip);
	}
	
	
	//a method that will allow you to add a clip to the track
	//TIMES NEED TO BE FIXED
	public void putClip(int start, Clip clip)
	{
//		not sure how to do this, were going to need to find 
//		out what the start time is then add the clip to the 
//		array at the appropriate place
		int i = 0;
		for(Clip c : clips)
		{
			if(i==start)
			{
				clips.add(i, clip);
			}
			i++;
		}
	}
//  Removes a clip based on its name
//  TIMES NEED TO BE FIXED
	public void removeClip(Clip clip)
	{
		for(Clip c : clips)
		{
			if(c.name == clip.name)
			{
				clips.remove(c);
			}
		}
	}
	
//	will be used to select a portion of the track, then duplicate
//	that part 'howMany' times
//  TIMES NEED TO BE FIXED - USE MIKE'S DUPLICATE
	public void duplicate(int start, int end, int howMany)
	{
		ArrayList<Clip> clist = new ArrayList<Clip>();
		int i = 0;
		for(Clip c : clips)
		{
			if(i == start)
			{
				for(int j = start; j<=end; j++)
				{
					clist.add(clips.get(j));
				}
			}
			i++;
		}
		for(int k = 0; k<howMany; k++)
		{
			if(k==0)
			{
				clips.addAll(end, clist);
			}
			else
			{
				clips.addAll(end+clist.size(), clist);
			}
		}
	}

	//getVolume() and setVolume() will need to be added

}
