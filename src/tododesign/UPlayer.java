/**
 * UPlay Lib: An algorithmic composition library for Processing
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		##author##
 * @modified	##date##
 * @version		##version##
 */

package tododesign;

import java.lang.reflect.*;
import java.util.ArrayList;


/**
 * This is a template class and can be used to start a new processing library or tool.
 * Make sure you rename this class as well as the name of the example package 'template' 
 * to your own library or tool naming convention.
 * 
 * @example a1_Basic 
 * 
 * (the tag @example followed by the name of an example included in folder 'examples' will
 * automatically include the example in the javadoc.)
 *
 */


public class UPlayer extends Thread 
{
	public final static String VERSION = "##version##";

	        
	private Object parent;
	private Method endLoop;
	private Method playNote;
	private Method endLoopPlayer;
	
  
	public static String END_LOOP_METHOD_NAME = "endLoop";
	public static String PLAY_NOTE_METHOD_NAME = "playNote";
	public static String END_LOOP_PLAYER_METHOD_NAME = "endLoopPlayer";
	
	private boolean running;  
	public int currentTik;
	public int totalTik;
  
	private static int TRACKS_NUM = 32;
	private int activeTracks = 0;
	private UTrack[] tracks;

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 * 
	 * @example a1_Basic
	 * @param _parent the Parent Object
	 */
	public UPlayer(Object _parent) 
	{
		init(_parent);
	}
  

  
    
	/**
	 * private: init the Player
	 * @param p
	 */
	private void init(Object p)
	{
		tracks = new UTrack[TRACKS_NUM];
		
	    parent = p;
	    
	    try {
	        endLoop = parent.getClass().getMethod(END_LOOP_METHOD_NAME, new Class[] { UTrack.class});
	    } catch (Exception e) {
	  	  	System.out.println(END_LOOP_METHOD_NAME + "(UTrack t) method is not set!");
	    }

	    try {
	        playNote = parent.getClass().getMethod(PLAY_NOTE_METHOD_NAME, new Class[] { UNote.class}); 
	    } catch (Exception e) {
	  	  	System.out.println(PLAY_NOTE_METHOD_NAME + "(UNote n) method is not set!");
	    }
	    
	    try {
	    	endLoopPlayer = parent.getClass().getMethod(END_LOOP_PLAYER_METHOD_NAME, new Class[] { }); 
	    } catch (Exception e) {
	  	  	//System.out.println(END_LOOP_PLAYER_METHOD_NAME + "() method is not set!");
	    }
	    
	    running = false;
	    
	    
	    System.out.println("UPlay lib " + VERSION);
	}
  
  
	/**
	* Add new track to this player
	* @param _track The UTrack to be added
	*/
	public void add(UTrack _track)
	{
		if(activeTracks >=TRACKS_NUM)
		{
			System.out.println("UPlay: Reached max UTrack objects allowed ("+TRACKS_NUM+")");
			return;
		}
		tracks[activeTracks] = _track;
		_track.index = activeTracks;
		activeTracks++;
		update();
	    //tracks.add(t);
	}

	/*
	private UTrack[] laterToAdd = new UTrack[TRACKS_NUM];
	private int currAvailablePosToAdd = 0;
	
	public void procatinateAdd(UTrack _track)
	{
		if(currAvailablePosToAdd < TRACKS_NUM)
		{
			laterToAdd[currAvailablePosToAdd] = _track;
			currAvailablePosToAdd++;
		}
	}
	*/
	
	
	
	/**
	 * Remove a Track according to its index position
	 * @param _i The index position of the Utrack to remove
	 */
	public void remove(int _i)
	{
		if(_i<activeTracks)
		{
			UTrack remT = tracks[_i];
			remT.index = -1;
			
			for(int i=_i; i<activeTracks-1; ++i)
			{
				UTrack nextT = tracks[i+1];
				tracks[i] = nextT;
				nextT.index = i;
			}
			activeTracks--;
			tracks[tracks.length-1] = null;	
			update();
		}
	}
		
	/**
	 * Remove a Track passing its reference
	 * @param _track The UTrack object to remove
	 */
	public void remove(UTrack _track)
	{  
		int _index = -1;
		for(int i=0; i<activeTracks; ++i)
		{
			if(_track == tracks[i])
			{
				_index = i;
			}
		}
		
		if(_index != _track.index) System.out.println("Something wrong in Player, track.index != its real index");
		remove(_track.index);
	}

	/*
	private UTrack[] laterToRem = new UTrack[TRACKS_NUM];
	private int currAvailablePosToRem = 0;
	
	
	public void procastinateRemove(UTrack _track)
	{
		if(currAvailablePosToRem < TRACKS_NUM)
		{
			laterToRem[currAvailablePosToRem] = _track;
			currAvailablePosToRem++;
		}
	}
	*/
	
	
	
	/**
	 * Replace a UTrack object with the passed one
	 * @param _track The UTrack object which replace the old one
	 * @param _i The index position of the UTrack to replace
	 */
	public void replace(UTrack _track, int _i)
	{
		if(_i < activeTracks)
		{
			UTrack prevT = tracks[_i];
			if(prevT!=null)
			{
				prevT.index = -1;
				tracks[_i] = _track;
				_track.index = _i;
				System.out.println("UPlayer:   track replaced");
				update();
			}
		}
	}
	
	
	
	public void replace(UTrack _old, UTrack _new)
	{
		int findIndex = -1;
		for(int i=0; i<activeTracks; ++i)
		{
			if(tracks[i] == _old)
			{
				findIndex = i;
			}
		}
		if(findIndex>=0) replace(_new, findIndex);
	}

	
	public void replace(UTrack _new, String _name)
	{
		int findIndex = -1;
		for(int i=0; i<activeTracks; ++i)
		{
			if(tracks[i].name.equals(_name))
			{
				findIndex = i;
			}
		}
		if(findIndex>=0) replace(_new, findIndex);
	}

	/*
	private UTrack[] laterToMod = new UTrack[TRACKS_NUM];
	private String[] laterToModName = new String[TRACKS_NUM];
	private int currAvailablePosToMod = 0;
	
	public void procastinateReplace(UTrack _new, String _name)
	{
		if(currAvailablePosToMod < TRACKS_NUM)
		{
			laterToMod[currAvailablePosToMod] = _new;
			laterToModName[currAvailablePosToMod] = _name;
			currAvailablePosToMod++;
		}
	}
	*/
	
	
	
	
	   /**
     * Update the duration of the Player
     */
    private void update()
    {
        totalTik = 0;
        int newCurrentTik = currentTik;
        if(activeTracks>0)
        {
            for(int i=0; i<activeTracks; ++i)
            {
               UTrack t = tracks[i];
               if(t.duration>totalTik)
        	   {
            	   totalTik = t.duration;
            	   newCurrentTik = t.currentTik;
        	   }
            }   
            currentTik = newCurrentTik;
        }else{
        	totalTik = currentTik = 0;
        }
        //System.out.println("Track duration updated: " + totalTik);
    }
    

    
    
    
	
	
	/**
	 * Get all the active tracks in this player
	 * @return The UTrack[] array
	 */
	public UTrack[] getActiveTracks()
	{
		UTrack[] _activeTracks = new UTrack[activeTracks];
		for(int i=0; i<activeTracks; ++i)
		{
			_activeTracks[i] = tracks[i];
		}
		return _activeTracks;
	}
	
	
	
	/**
	 * Get all the active tracks in this player by passing group name
	 * @return The UTrack[] array
	 */
	public UTrack[] getActiveTracksByGroup(String _name)
	{
		UTrack[] _activeTracks = new UTrack[activeTracks];
		int counter = 0;
		for(int i=0; i<activeTracks; ++i)
		{
			UTrack t = tracks[i];
			if(t.group != null)
			{
				if(t.group.equals(_name))
				{
					_activeTracks[counter] = t;
					counter++;
				}
			}
		}
		
		if(counter == 0)
		{
			return null;
		}
		
		UTrack[] _res = new UTrack[counter];
		for(int i=0; i<counter; ++i)
		{
			_res[i] = _activeTracks[i];
		}
		return _res;
	}
	
	

	/**
	* Reset this player by deleting all the tracks present
	* Doesn't work yet
	*/
	public void reset()
	{
		tracks = new UTrack[TRACKS_NUM];
		totalTik = currentTik = 0;
    }


	/**
	* Start the player thread
	* One call only
	*/
	public void start () 
	{
	  if(!running)
	  {
	      System.out.println("UPlayer started");
	      running = true;
	      setPriority(Thread.MAX_PRIORITY - 1);
	      super.start();
	  }
	}

	/**
	* Restart the playback
	*/
	public void restart()
	{
	    for(int i=0; i<activeTracks; i++)
	    {
	          UTrack t = tracks[i];
	          t.currentTik = 0;
	    }
	    currentTik=0;
	}


	/**
	 *Sync all the tracks of this player
	 */
	void syncTracks()
	{
	    for(int i=0; i<activeTracks; i++)
	    {
	          UTrack t = tracks[i];
	          if(t.totalTik>0)
	          {
	              float diffTiks = (totalTik % t.totalTik);
	              float diffCalc = t.totalTik / diffTiks;
	              t.currentTik = (int) diffCalc;
	          }
	    }    
	}
	
	
    /**
     * Get the normalized time position of the header of the player
     * @return The normalized value
     */
    public float getPosition()
    {
    	return (float)currentTik/(float)totalTik;
    }


    
    private long currentMillis = 0;
    private int restMillis = 0;
    private int pRest = 0;
    
	public void run () 
	{
	      while (running) 
	      {
	    	  // this fixes the millis thread error
	    	  long cMillis = System.currentTimeMillis();
	    	  restMillis = (int) (cMillis - currentMillis);
	    	  currentMillis = cMillis;

	          // loop for note play
	          for(int i=0; i<activeTracks; i++)
	          {
	              UTrack t = tracks[i];
	              
	              if(t.totalTik>0)
	              {
	            	   // keep totalTik consistent
	                   if(totalTik<t.totalTik)
	                   {
	                       totalTik=t.totalTik;
	                       System.out.println("totalTik: " + totalTik);
	                   }
	                   
	                    // play note
	                   if(!t.mute)
	                   {
	                       UNote[] notes = t.getNotes();
	                       if(notes != null)
	                       {
	                          for(int j=0; j<notes.length; j++)
	                          {
	                        	  UNote n = notes[j];
	                        	  n.playNote();
	                              if(t.playNote!=null)
	                              {
	                                   try{
	                                       t.playNote.invoke(t.parent, new Object[] { n });
	                                   }catch(Exception e){
	                                   } 
	                              }
	                              if(playNote!=null)
	                              {
	                                   try{
	                                       playNote.invoke(parent, new Object[] { n });
	                                   }catch(Exception e){
	                                   }
	                              }
	                          } 
	                       }
	                   }

	                   
	                   
	                     // end loop and update routine
	                    if(t.currentTik >= t.totalTik-1)
	                    {
	                    	t.endLoop();
	                        if(t.endLoop!=null)
	                        {
	                             try{
	                                 t.endLoop.invoke(t.parent, new Object[] { t });
	                             }catch(Exception e){
	                             } 
	                        }
	                        if(endLoop!=null)
	                        {
	                             try{
	                                 endLoop.invoke(parent, new Object[] { t });
	                             }catch(Exception e){
	                             }                         
	                        }         
	                        t.currentTik=0;
	                    }else{
	                        t.currentTik += restMillis;
	                    }	            	  	
	                    t.restTik = restMillis-1;

	              }
	              
	          } // end for tracks
	          
	          
	          // loop for
//	          for(int i=0; i<activeTracks; i++)
//	          {
//	              UTrack t = tracks[i];
//	              
//	              if(t.totalTik>0)
//	              {
//	                     // end loop and update routine
//	                    if(t.currentTik >= t.totalTik-1)
//	                    {
//	                    	t.endLoop();
//	                        if(t.endLoop!=null)
//	                        {
//	                             try{
//	                                 t.endLoop.invoke(t.parent, new Object[] { t });
//	                             }catch(Exception e){
//	                             } 
//	                        }
//	                        if(endLoop!=null)
//	                        {
//	                             try{
//	                                 endLoop.invoke(parent, new Object[] { t });
//	                             }catch(Exception e){
//	                             }                         
//	                        }         
//	                        t.currentTik=0;
//	                    }else{
//	                        t.currentTik++;
//	                    }	            	  	
//	              }
//	          }
	          
	          
	          
	          if(totalTik>0)
	          {
	              if(currentTik >= totalTik-1)
	              {
	            	  // player loop
	                  currentTik=0;
	                  pRest=0;
	                  if(endLoopPlayer!=null)
	                  {
	                       try{
	                    	   endLoopPlayer.invoke(parent, new Object[] {  });
	                       }catch(Exception e){
	                       }
	                  }
	              }else{
	                  currentTik += restMillis;
	                  pRest = restMillis-1;
	              }
	              
	          }
	          
	          
	          
	          try {
	            sleep(1);
	          } catch (Exception e) {
	          }

 	      }
	}


	/**
	* Quit the Thread
	*/
	public void dispose() 
	{
		System.out.println("UPlay dispose");
		running = false;
		super.stop();
		interrupt();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	private void addTracksFromProcastination()
	{
		if(currAvailablePosToAdd>0)
		{
			for(int i=0; i<currAvailablePosToAdd; ++i)
			{
				UTrack t = laterToAdd[i];
				if(t != null)
				{
					add(t);
				}
			}
			for(int i=0; i<currAvailablePosToAdd; ++i) laterToAdd[i] = null;
			currAvailablePosToAdd = 0;
		}
		
	}
	
	
	public UTrack[] getQueuedToAddTracks()
	{
		UTrack[] res = new UTrack[currAvailablePosToAdd];
		for(int i=0; i<currAvailablePosToAdd; ++i)
		{
			res[i] = laterToAdd[i];
		}
		return res;
	}
	
	
	private void removeTracksFromProcastination()
	{
		if(currAvailablePosToRem>0)
		{
			for(int i=0; i<currAvailablePosToRem; ++i)
			{
				UTrack t = laterToRem[i];
				if(t != null)
				{
					remove(t);
				}
			}
			for(int i=0; i<currAvailablePosToRem; ++i) laterToRem[i] = null;
			currAvailablePosToRem = 0;
		}
	}
	
	
	public UTrack[] getQueuedToRemoveTracks()
	{
		UTrack[] res = new UTrack[currAvailablePosToRem];
		for(int i=0; i<currAvailablePosToRem; ++i)
		{
			res[i] = laterToRem[i];
		}
		return res;
	}

	private void replaceTracksFromProcastination()
	{
		if(currAvailablePosToMod>0)
		{
			for(int i=0; i<currAvailablePosToMod; ++i)
			{
				UTrack t = laterToMod[i];
				String n = laterToModName[i];
				if(t != null && !n.isEmpty())
				{
					replace(t, n);
				}
			}
			for(int i=0; i<currAvailablePosToMod; ++i)
			{
				laterToMod[i] = null;
				laterToModName[i] = null;
			}
			currAvailablePosToMod = 0;
		}
	}
*/

}
