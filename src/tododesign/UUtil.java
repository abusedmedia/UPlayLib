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


import java.util.ArrayList;


public class UUtil 
{

	
    /**
    * Convert a String readable english note to Legacy Midi note
    * i.e.: "C3" will be convert to 60
    */
	private static ArrayList stringNotes;
	private static void initStringNotes()
	{
		stringNotes = new ArrayList();
        String[] tempNotes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
        for(int i=0; i<tempNotes.length; ++i)
        {
            stringNotes.add(tempNotes[i]);
        }
	}
	
	
	/**
	 * Convert readable string english note to Midi note
	 * @param note The String note (i.e. C3)
	 * @return The int Midi note
	 */
	public static int convertStringNoteToMidi(String note)
	{
		int inote;
		
		// inited at first use
		if(stringNotes == null) initStringNotes();
		
        String findLetter = (note.length()==2) ? note.substring(0, 1) : note.substring(0, 1)+"#";
        int findNumber = Integer.parseInt(  note.substring(1, 2) );
        int findNote = stringNotes.indexOf(findLetter);
        inote = findNote + 12*(findNumber+1);
		
        return inote;
	}
	
	
	
	/**
	 * Convert Midi note to readable english note
	 * @param note The Midi note to be converted
	 * @return The String note
	 */
	public static String convertMidiNoteToString(int note)
	{
		if(stringNotes == null) initStringNotes();
		
		int octave = (int)Math.floor( note / 12 ) - 1;
		//System.out.println(octave);
		String octaveStr = octave+"";
		int n = note - 12*(octave+1);
		//System.out.println(n);
		String res = stringNotes.get(n) + octaveStr;
		
		return res;
	}
	
	
	/**
	 * Convert the Midi note number to float frequency value
	 * @param note The Midi note to convert
	 * @return The frequency value
	 */
	public static float convertMidiNoteToFrequency(int note)
	{
		return (float) (440 * Math.pow( 2.0, (note-69.0)/12.0 ));
	}
	
}
