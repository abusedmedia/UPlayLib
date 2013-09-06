UPlayLib
=========

General purpose Processing library for structured event algorithmic compositions that can be used for music pattern generations as well as visual rhythmical animations.


#How does it work?
Simply create a Player and add Tracks, Sequences and Notes. Use the callbacks to do whatever you want with the time-based structure.
While it is intended for music compositions, you are able to use its events to trigger and animate visual elements.

###Some hints
Keep in mind that adding notes to the same sequence, they will be queued accordingly to the specified duration.
The same happen when adding multiple sequences to the same track.
Tracks won't be queued. Instead they will be run independently.


#Download the library
Read the README inside in order to install it properly.
[Download UPlay 1.0](https://github.com/abusedmedia/UPlayLib/blob/master/distribution/UPlay-1.0/download/UPlay-1.0.zip?raw=true)


#Basic example

Create a Player:

    UPlayer player = new UPlayer(this);
    player.start();
    
Add a Track:
    
    UTrack track = new UTrack();
    player.add(track);
    
Add a Sequence:
    
    USequence sequence = new USequence();
    track.add(sequence);
    
Add Notes specifing the tone and the duration in milliseconds:

    UNote note = new UNote("C3", 500);
    sequence.add(note); 
    
By adding additional notes, they will be queued automatically:

	UNote anotherNote = new UNote("D4", 1000);
    sequence.add(anotherNote);

Use the callback to create something with its events:

	// callback for each end loop track
	void endLoop(UTrack _track){
	    println("End LOOP");
	}

	// callback for each note to play
	void playNote(UNote _note){
	    println("Play NOTE: " + _note.strNote);
	}