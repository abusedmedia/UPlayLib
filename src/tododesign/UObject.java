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
