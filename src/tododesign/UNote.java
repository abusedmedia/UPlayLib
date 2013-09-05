package tododesign;



public class UNote extends UObject implements Cloneable
{
	
    // variables that can be set either from constructor
    public String strNote;
    public float frequency;
    public int note;
    
    
    // variables that can be set after creation
    public float pan = 0;
    public int velocity = 127;
    public int sustain = 127;
    public String comment;
    public int control = -1;
    public int channel = 0;
    public int liveness = 0;
    public int sendNet = 0;


    public int customId;
    
    public boolean isOff = false;
    // set from its sequence
    public USequence sequence;
    
    
    
    /**
     * Mute note without duration, you must set duration later with setDuration()
     */
    public UNote()
    {
    	mute=true; 
    }
    
    
    /**
     * Mute note, only duration
     */
    public UNote(int d)
    {
        mute=true; 
        duration = d;
    }
  
    /**
     * Note with frequency
     */
    public UNote(float f, int d)
    {
        frequency = f;
        duration = d;
    }
    
    /**
     * Midi Note value
     */
    public UNote(int n, int d)
    {
        note = n;
        duration = d;
        frequency = UUtil.convertMidiNoteToFrequency(note);
        strNote = UUtil.convertMidiNoteToString(n);
    }
    
    /**
     * String note
     */
    public UNote(String n, int d)
    {
        strNote = n.toUpperCase();
        note = UUtil.convertStringNoteToMidi(n);
        duration = d;
        frequency = UUtil.convertMidiNoteToFrequency(note);
    }

    
    
    
    
    
    /**
     * Special for Midi ControlChange
     */
    public UNote(int n, int d, int cc)
    {
        note = n;
        control = cc;
        duration = d;
    }
    
    
    
    /**
     *  To be override if you extend this class
     *  This method will be called each time this note will be played
     */
    public void playNote()
    {
    }
    
    
   
    
    /**
     * Dispose this note, so it'll be ready to be removed
     */
    public void dispose()
    {
    	sequence = null;
    }
    
    
    /**
     * Set a new duration of this note
     * USequence object will be informed automatically
     * @param d The new duration in milliseconds
     */
    public void setDuration(int d)
    {
        if(d!=duration)
        {
            duration = d;
            // notify sequence
            //System.out.println("      Note duration updated: " + duration);
            if(sequence != null) sequence.updateDuration();
        }
    }
    
    /**
    * Set the liveness of the note (time of dying), useful for some instrument which needs off command shortly
    */
    public void setLiveness(int l)
    {
        liveness = l;
        if(l>=duration) setLiveness();
    }
    
    /**
     * Set the liveness of the note equals to its duration (time of dying).
     */
    public void setLiveness()
    {
        liveness = duration-1;
    }
    
    
    
    
 	
	/**
	 * Get the normalized duration relative to its sequence
	 * Return -1 if it doesn't have USequence assigned to
	 * @return
	 */
	public float getNormDuration()
	{
		if(sequence!=null)
		{
			return (duration>0 && sequence.duration>0) ? (float)duration/(float)sequence.duration : 0;
		}else{
			return -1;
		}
	}

    
    
    
    
    
    
    
    
    
    

    public boolean isDifferent(UNote n)
    {
    	if(note!=n.note) return true;
    	if(duration!=n.duration) return true;
    	if(channel!=n.channel) return true;
    	if(velocity!=n.velocity) return true;
    	if(liveness!=n.liveness) return true;
    	if(mute!=n.mute) return true;
    	if(pan!=n.pan) return true;
    	if(sustain!=n.sustain) return true;
    	if(sendNet!=n.sendNet) return true;
    	
    	return false;
    }
    
    
    
    /**
     * Clone this note
	 * @return The UNote cloned object
     */
    public UNote clone()
    {
        Object o = null;
        try {
           o = super.clone();
        } catch  (CloneNotSupportedException e) {
           System.out.println ( "Some error in cloning Object" );
        }
        
        UNote cloned = (UNote) o;
        cloned.sequence = null;
        
        return cloned;
    }



}
