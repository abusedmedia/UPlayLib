/*
a1_Basic example
Create a player, a track and a sequence with 4 notes
No sound here, only println
*/


import tododesign.*;

UPlayer player;
UTrack track;
USequence sequence;

void setup() 
{
    size (640, 480);
    
    // create a player
    player = new UPlayer(this);
    player.start();
    
    // create a track
    track = new UTrack();
    player.add(track);
    
    // create a sequence
    sequence = new USequence();
    track.add(sequence);
    
    // add a bounch of notes
    UNote note1 = new UNote("C3", 500);
    sequence.add(note1); 
    
    UNote note2 = new UNote("D3", 500);
    sequence.add(note2); 
    
    UNote note3 = new UNote("E3", 500);
    sequence.add(note3); 
    
    UNote note4 = new UNote("A4", 500);
    sequence.add(note4); 
    
}

void draw() 
{
    background (255);
}


// callback for each end loop track
void endLoop(UTrack _track)
{
    println("End LOOP");
}

// callback for each note to play
void playNote(UNote _note)
{
    println("Play NOTE: " + _note.strNote);
}
