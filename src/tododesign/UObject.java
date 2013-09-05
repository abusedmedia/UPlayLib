package tododesign;

public class UObject
{
	
    /**
     * The optional name of this object
     */
	public String name;
	
	/**
	 * The optional group name of this object
	 */
	public String group;

	
	/**
	 * the mute property of this object
	 */
	public boolean mute = false;
	
	
	/**
	 * the duration of this object
	 */
	public int duration = 0;
	
	
	/**
	 * the current index position of this object in its parent array
	 */
	public int index = -1;
	
	
	
	
    /* (non-Javadoc) */
	public int totalTik = 0;
	/* (non-Javadoc) */
	public int currentTik = 0;
	/* (non-Javadoc) */
	public int restTik = 0;
	
	
	
	
	
	
	public UObject()
	{
	}
	
	

}
