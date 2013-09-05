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

public class UChord 
{

	 ArrayList sequences = new ArrayList();
	 int length;
	 
	 public UChord()
	 {
	 }
	 
	 public void add(UNote[] nts)
	 {
	     checkSequencePresence(nts);
	     addNotes(nts, 0);
	 }


	 public void add(UNote[] nts, int shift)
	 {
	     checkSequencePresence(nts);
	     addNotes(nts, shift);
	 }
	 
	 // private
	 private void checkSequencePresence(UNote[] nts)
	 {
	     // add first the sequences if not present
	     int diffSeqNote = nts.length - sequences.size();
	     if(diffSeqNote>0)
	     {
	         for(int i=0; i<diffSeqNote; ++i)
	         {
	             USequence s = new USequence();
	             sequences.add(s);
	         }
	         length = sequences.size();
	     }      
	 }
	 
	 
	 
	 /*
	 Reset the chord, delete all the sequences and notes
	 */
	 public void reset()
	 {
	      sequences = new ArrayList(); 
	 }

	 
	 // private
	 private void addNotes(UNote[] nts, int shift)
	 {
	     int duration = 0;
	     for(int i=0; i<nts.length; ++i)
	     {
	         UNote n = nts[i];
	         if(duration<n.duration) duration = n.duration;
	     }
	     
	     for(int i=0; i<nts.length; ++i)
	     {
	         USequence s = (USequence)sequences.get(i);
	         UNote n = nts[i];
	         int shiftNote = shift*i;
	         n.duration = duration - shiftNote;
	         if(shiftNote>0)
	         {
	             UNote muted = new UNote(shiftNote);
	             s.add(muted);
	         }
	         s.add(n);
	     }      
	 }
	 
	 
	 
	 public USequence get(int i)
	 {
	    return (USequence)sequences.get(i);
	 }
	 
}
