/**
 * Copyright (c) 2013 Franchino Fabio
 *
 * This file is part of a library called UPlay - http://github.com/abusedmedia
 *
 * UPlay is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * UPlay is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the UPlay. If not, see <http://www.gnu.org/licenses/>.
*/


package tododesign;

import java.lang.reflect.Method;
import java.util.Arrays;



public class UTrack extends UObject implements Cloneable
{
	
	
	/* (non-Javadoc) */
	public Object parent;
	/* (non-Javadoc) */
	public Method endLoop;
	/* (non-Javadoc) */
	public Method playNote;
	
	private USequence[] sequences;
	private static int MAX_SEQUENCES = 64;
	private int activeSequence = 0;
	private int MAX_NOTE_SAME_TIME = 64;
	
   /**
    *  Create new Track, using UPlayer "endLoop(UTrack t)" and "playNote(UNote n)" callbacks
    */
	public UTrack()
	{
		init();
	}
	
	
   /**
    *  Create new Track by passing a custom class object to attach the "endLoop(UTrack t)" and "playNote(UNote n)" callbacks
    */
    public UTrack(Object p)
    {
        parent = p;
        
        try {
            endLoop = parent.getClass().getMethod(UPlayer.END_LOOP_METHOD_NAME, new Class[] { UTrack.class});
        } catch (Exception e) {
        }
  
        try {
            playNote = parent.getClass().getMethod(UPlayer.PLAY_NOTE_METHOD_NAME, new Class[] { UNote.class}); 
        } catch (Exception e) {
        }
        
        init();
    }
    
    /* (non-Javadoc)
     * init()
     */
    private void init()
    {
    	sequences = new USequence[MAX_SEQUENCES];
    }
    
    
    
    /**
     *  To be override if you extend this class
     *  This method will be called each time this track reach the its end
     */
    public void endLoop()
    {
    }
    
    
    /**
    *  Add new sequence to this track
    *  @param s The sequence to add to
    */
    public void add(USequence s)
    {
    	//int isFound = Arrays.binarySearch(sequences, s);
    	//System.out.println(isFound);
    	
		if(activeSequence >=MAX_SEQUENCES)
		{
			System.out.println("UPlay: Reached max USequence objects allowed ("+MAX_SEQUENCES+")");
			return;
		}
		
		sequences[activeSequence] = s;
		s.track = this;
        s.index = activeSequence;
		activeSequence++;
		
		updateDuration();
			

    }
    
    
    
    /**
     * Add notes straight to the track without using a sequence object.
     * Keep in mind that this should be considered a shortcut and it will be used the first sequence
     * in the sequence's array, automatically created if not present.
     * @param n the UNote object to add
     */
    public void add(UNote n)
    {
    	// create the sequence first if not present
    	USequence s;
    	if(activeSequence == 0)
    	{
    		s = new USequence();
    		add(s);
    	}else{
    		s = sequences[0];
    	}
    	s.add(n);
    }
    
    
    
    /**
    *  Add new chord to this track
    */
//    public void add(UChord c)
//    {
//        for(int i=0; i<c.length; ++i)
//        {
//            USequence s = (USequence) c.get(i);
//            add(s);
//        }
//    }
    
    
    
    /** 
    *  Reset this track, by deleting all the sequences present
    */
    public void reset()
    {
    	sequences = new USequence[MAX_SEQUENCES];
    	activeSequence=0;
    	totalTik = currentTik = duration = 0;
    	index = -1;
    }
   
    
    
    /**
     * Remove a sequence reference from this track
     * @param s The USequence reference to remove
     */
    public void remove(USequence s)
    {
		int _index = -1;
		for(int i=0; i<activeSequence; ++i)
		{
			if(s == sequences[i])
			{
				_index = i;
			}
		}
		
		if(_index != s.index) System.out.println("Something wrong in UTrack, sequence.index != its real index");
		remove(s.index);
    }

    
    /**
     * Remove a sequence from this track, using its index position in the sequences array
     * @param _i The index position in the array
     */
    public void remove(int _i)
    {
		if(_i<activeSequence)
		{
			USequence remS = sequences[_i];
			remS.dispose();
			
			for(int i=_i; i<activeSequence-1; ++i)
			{
				USequence nextS = sequences[i+1];
				sequences[i] = nextS;
				nextS.index = i;
			}
			activeSequence--;
			sequences[sequences.length-1] = null;	
			
			updateDuration();
		}
    }

    
    /**
     * Set a fixed duration of this track
     * Every sequences duration will be mapped to the new duration
     * @param d The new duration in milliseconds
     */
    public void setDuration(int d)
    {
        int cDuration = totalTik;
        for(int i=0; i<activeSequence; i++)
        {
            USequence s = sequences[i];
            float nd = (float)s.duration / (float)cDuration;
            s.setDuration( (int) (nd*d) );
            //System.out.println(s.duration);
        }      
        //duration = d;
        //updateDuration();
    }

    
    
    
    /**
     * Get the normalized time position of the header of this track
     * @return The normalized value
     */
    public float getPosition()
    {
    	return (float)currentTik/(float)totalTik;
    }
    
    
    /**
     * 
     * Called from USequence object on duration changes 
     * and on add sequence to this track
     * 
     */
    public void updateDuration()
    {
        update();
    }
    
    
    /**
     * Update the duration of this UTrack
     */
    private void update()
    {
//        if(duration<=0)
//        {
            totalTik = 0;
            if(activeSequence>0)
            {
	            for(int i=0; i<activeSequence; ++i)
	            {
	               USequence s = sequences[i];
	               if(s.duration>totalTik) totalTik = duration = s.duration;
	            }    
            }else{
            	totalTik = duration = currentTik = 0;
            }
            //System.out.println("Track duration updated: " + totalTik);
//        }
    }
    


    
    /**
     * Get all the active sequences in this track
     * @return The USequence array
     */
	public USequence[] getActiveSequences()
	{
		USequence[] _activeSequences = new USequence[activeSequence];
		for(int i=0; i<activeSequence; ++i)
		{
			_activeSequences[i] = sequences[i];
		}
		return _activeSequences;
	}
	
	
	

	
	
	
	/**
	 * Get the notes to be played, according of each position in timeline
	 * @return The UNote array
	 */
	public UNote[] getNotes()
	{
		  UNote[] tempnotes = new UNote[MAX_NOTE_SAME_TIME];
		  int index = 0;
		  
	      for(int i=0; i<activeSequence; ++i)
	      {
	          USequence s = sequences[i];
	          if(!s.mute)
	          {
	              UNote n = s.getNote(currentTik, restTik);
	              if(n != null)
	              {
	            	  tempnotes[index] = n;
	            	  index++;
	              }
	          
	              UNote o = s.getOffNote(currentTik, restTik);
	              if(o != null)
	              {
	            	  tempnotes[index] = o;
	            	  index++;
	              }
	          }
	      }
	      
	      UNote[] notes = null;
	      if(index>0)
	      {
		      notes = new UNote[index];
		      for(int i=0; i<index; ++i)
		      {
		    	  notes[i] = tempnotes[i];
		      }
	      }
	      
	      return notes;
	}
	
	
	
	
	
	
    public boolean isDifferent(UTrack t)
    {
    	if(mute!=t.mute) return true;
    	if(activeSequence!=t.activeSequence) return true;
    	
    	USequence[] ss = t.getActiveSequences();
    	for(int i=0; i<activeSequence; ++i)
    	{
    		if(sequences[i].isDifferent(ss[i])) return true;
    	}
    	
    	return false;
    }

    
 
    
	
	
	
	/**
	 * Clone this track and its relative sequences and notes
	 * @return The UTrack cloned object
	 */
    public UTrack clone () 
    {
        Object o = null;
        try {
           o = super.clone();
        } catch  (CloneNotSupportedException e) {
           System.out.println ( "Some error in cloning Object" );
        }
        
        UTrack cloned = (UTrack) o;
        
        cloned.reset();
        
        for(int i=0; i<activeSequence; ++i)
        {
        	USequence clonedSequence = sequences[i].clone();
        	cloned.add( clonedSequence );
        }
        
        return cloned;
    }

}
