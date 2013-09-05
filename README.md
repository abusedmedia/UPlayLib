UPlayLib
=========

General purpose Processing library for structured event algorithmic compositions that can be used for music pattern generations as well as visual rhythmical animations.


#Basic example

Define the structure using the music score paradigm:

	// create a player
    player = new UPlayer(this);
    player.start();
    
    // create a track
    track = new UTrack();
    player.add(track);
    
    // create a sequence
    sequence = new USequence();
    track.add(sequence);
    
    // add a note
    UNote note1 = new UNote("C3", 500);
    sequence.add(note1); 

Use the callback to create something with its events:

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