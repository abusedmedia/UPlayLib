package tododesign;



public class USequence extends UObject implements Cloneable
{
	private UNote[] notes;
	private static int NOTES_NUM = 128;
	private int activeNotes = 0;
	

	/**
	 * The offset time, from the beginning to the first note.
	 */
    public int offset = 0;
    
    
    /**
     * The track which hold this sequence
     */
    public UTrack track;

    
    

    
    public USequence()
    {
    	init();
    }
    
    
    
    /*
    Create new sequence with offset
    */
    public USequence(int off)
    {
        offset = off;
        init();
    }
    

    
    
    private void init()
    {
        notes = new UNote[NOTES_NUM];
    }
    
    
    /*
    Add new Note to the sequence
    */
    public void add(UNote n)
    {
		if(activeNotes >= NOTES_NUM)
		{
			System.out.println("UPlay: Reached max UNote objects allowed ("+NOTES_NUM+")");
			return;
		}
		
		notes[activeNotes] = n;
        n.index = activeNotes;
		activeNotes++;

        n.sequence = this;
        updateDuration();
    }
    
    /**
     * Remove a Note to the sequence
     */
    public void remove(UNote n)
    {
        //notes.remove(n);
        updateDuration();
    }
    
    
    /**
     * Reset the sequence, delete all the notes
     */
    public void reset()
    {
         notes = new UNote[NOTES_NUM];
         activeNotes=0;
         updateDuration();
    }
    
    
    
    /**
     * Dispose this sequence, so it'll be ready to be removed
     */
    public void dispose()
    {
    	mute=true;
    	index = -1;
    	for(int i=0; i<activeNotes; i++)
    	{
    		UNote n = notes[i];
    		n.dispose();
    	}
    	notes = null;
    	track = null;
    }
    
    
    /**
     * Set a fixed duration of this sequence
     * Every notes duration will be mapped to the new duration
     * @param d The new duration in milliseconds
     */
    public void setDuration(int d)
    {
        int cDuration = duration;
        for(int i=0; i<activeNotes; i++)
        {
            UNote n = notes[i];
            float nd = (float)n.duration / (float)cDuration;
            n.setDuration( (int) (nd*d) );
        }      
        //duration = d;
        //updateDuration();
    }
    
    
    
    /**
     * Set a channel number to all the notes present
     * Since each note can have a proper channel value, you can use this method to 
     * assign a channel value to all of them
     * @param c The channel number
     */
    public void setChannel(int c)
    {
        for(int i=0; i<activeNotes; i++)
        {
            UNote n = notes[i];
            n.channel = c;
        }      
    }
    
    
    public int getTotalNotes()
    {
        return activeNotes;
    }
    
    /**
     * @private
     * Calculate the total duration of the sequence
     * Called by UnTrack object
     */
    private int notesDuration()
    {
        int dur = 0;
        for(int i=0; i<activeNotes; i++)
        {
            UNote n = notes[i];
            dur += n.duration;
        }
        return dur;
    }
    
    // called from Note duration change
    public void updateDuration()
    {
        int tdur = notesDuration() + offset;
        if(duration!=tdur)
        {
            duration = tdur;
            //System.out.println("   Sequence duration updated: " + duration);
            if(track != null) track.updateDuration();
        }
    }
    

    

    /**
     * Get all the active notes in this sequence
     * @return The UNote array
     */
	public UNote[] getActiveNotes()
	{
		UNote[] _activeNotes = new UNote[activeNotes];
		for(int i=0; i<activeNotes; ++i)
		{
			_activeNotes[i] = notes[i];
		}
		return _activeNotes;
	}

	
	
	/**
	 * Get the normalized value of its start position relative to its track
	 * Return -1 if it doesn't have UTrack assigned to
	 * @return
	 */
	public float getStartPosition()
	{
		if(track!=null)
		{
			return (offset>0 && track.duration>0) ? (float)offset/(float)track.duration : 0;
		}else{
			return -1;
		}
	}
	
	/**
	 * Get the normalized value of its end position relative to its track
	 * Return -1 if it doesn't have UTrack assigned to
	 * @return
	 */
	public float getEndPosition()
	{
		if(track!=null)
		{
			return (duration>0 && track.duration>0) ?  (float)duration/(float)track.duration - getStartPosition() : 0;
		}else{
			return -1;
		}
	}
	
	
	
    /*
    @private
    Get the note to be played by a particular tik time
    Called by UnTrack object
    */
    public UNote getNote(int _currentTik, int rest)
    {        
        int dur = offset;
        int pdur = 0;
        for(int i=0; i<activeNotes; i++)
        {
            UNote n = notes[i];
            dur += pdur;
            
            // play the note
            if(_currentTik>=dur && _currentTik<=(dur+rest) && !n.mute) return n;
            
            // off the note
            //if(n.liveness>0)
            //{
                //if(_currentTik == dur+n.liveness && !n.mute) return n;
            //}
            
            // in case something goes wrong
            if(dur > _currentTik) return null;
            
            pdur = n.duration;
        }      
        
        return null;
    }
    
    
    public UNote getOffNote(int _currentTik, int rest)
    {        
        int dur = offset;
        int pdur = 0;
        for(int i=0; i<activeNotes; i++)
        {
            UNote n = notes[i];
            dur += pdur;
            
            // play the note
            if(n.liveness>0)
            {
            	if(_currentTik>=(dur+n.liveness) && _currentTik<=(dur+n.liveness+rest) && !n.mute)
                //if(_currentTik == (dur+n.liveness - rest) && !n.mute)
                {
                    n.isOff = true;
                    return n;
                }
            }
           
            
            // in case something goes wrong
            if(dur > _currentTik) return null;
            
            pdur = n.duration;
        }      
        
        return null;
    }

    
    
    
    
    public boolean isDifferent(USequence s)
    {
    	if(offset!=s.offset) return true;
    	if(mute!=s.mute) return true;
    	if(activeNotes!=s.activeNotes) return true;
   	
    	UNote[] nn = s.getActiveNotes();
    	for(int i=0; i<activeNotes; ++i)
    	{
    		if(notes[i].isDifferent(nn[i])) return true;
    	}
    	
    	return false;
    }

    
    
    /**
     * Clone this sequence and its relative notes
 	 * @return The USequence cloned object
    */
    public USequence clone() 
    {
        Object o = null;
        try {
           o = super.clone();
        } catch  (CloneNotSupportedException e) {
           System.out.println ( "Some error in cloning Object" );
        }
        
        USequence cloned = (USequence) o;
        
        cloned.track = null;
        cloned.reset();
        
        for(int i=0; i<activeNotes; ++i)
        {
        	UNote clonedNote = notes[i].clone();
        	cloned.add( clonedNote );
        }
        
        return cloned;
    }


}
