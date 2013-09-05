/*
b1_Csoundo example
In order to run this example you must have Csoundo library installed (see its documentation)
This example is quite identical to a1_Basic example, with the addition of Csoundo lib
*/

import tododesign.*;

UPlayer player;
UTrack track;
USequence sequence;



void setup() 
{
    size (640, 480);
    
    cs = new Csoundo(this, "tone.csd");
    cs.run();
    
    // create a player
    player = new UPlayer(this);
    
    // create a track
    track = new UTrack();
    player.add(track);
    
    // create a sequence
    sequence = new USequence();
    track.add(sequence);
    
    // add a bounch of notes
    UNote note1 = new UNote("C3", 500);
    sequence.add(note1); 
    
    UNote note2 = new UNote("D4", 500);
    sequence.add(note2); 
    
    UNote note3 = new UNote("E4", 500);
    sequence.add(note3); 
    
    UNote note4 = new UNote("C5", 500);
    sequence.add(note4); 
    
    player.start();
}

void draw() 
{
}


// callback for each end loop track
void endLoop(UTrack _track)
{
}

// callback for each note to play
void playNote(UNote _note)
{
    cs.event("i 2 0 2 "+.3+" "+_note.frequency);
}
