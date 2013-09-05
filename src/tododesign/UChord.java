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
