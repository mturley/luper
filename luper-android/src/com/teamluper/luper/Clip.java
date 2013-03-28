package com.teamluper.luper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import android.media.MediaPlayer;

public class Clip {
	File name = null;
	int begin, end, length;
	
	//needs an array of attributes/filters/modifications?
	public Clip()
	{
		name = null;
		length = 0;
		begin = 0;
		end = length;
	}
	public Clip(File cName)
	{
		name = cName;
		length = 0;
		begin = 0;
		end = length;
	}
	//Getters and setters for the fields
	public File getClip()
	{
		return name;
	}
	public long getBegin()
	{
		return begin;
	}
	public long getEnd()
	{
		return end;
	}
	public void setClip(File cName)
	{
		name = cName;
	}
	public void setStart(int start)
	{
		begin = start;
	}
	public void setEnd(int stop)
	{
		end = stop;
	}
	//Extra length method using mediaplayer which oddly uses an integer for the duration
	public void getLength() throws IOException
	{
		MediaPlayer mp = new MediaPlayer();
		FileInputStream fs;
		FileDescriptor fd;
		fs = new FileInputStream(name);
		fd = fs.getFD();
		mp.setDataSource(fd);
		mp.prepare();
		int len = mp.getDuration();
		mp.release();
		length = len;
	}
	//calculates the total playing length of the clip as an int, keeping in line with getDuration
	public int calcLength()
	{
		length = end-begin;
		return length;
	}
	//returns the clips path and save location as a string
	public String getClipPath()
	{
		String path = this.name.getPath();
		return path;
	}
}
