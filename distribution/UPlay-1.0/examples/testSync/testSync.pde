
import tododesign.*;

UPlayer player;

int endLoopPlayerMillis = millis();
int endLoopMillis = millis();


void setup() 
{
    size (640, 480);
    // create a player
    player = new UPlayer(this);
    frameRate(1);
    endLoopPlayerMillis = millis();
    endLoopMillis = millis();
    
    int[] durs = {250, 500, 1000, 2000};
    
    for(int i=0; i<1; ++i)
    {
      UTrack t = new UTrack();
      player.add(t);
      
      for(int j=0; j<4; ++j)
      {
        UNote n = new UNote("C3", durs[0]);
        t.add(n);
      }
    }
    
    player.start();
}

void draw() 
{
    background (255);
}

void endLoopPlayer()
{
    println("---------------");
    UTrack[] tt = player.getActiveTracks();
    for(int i=0; i<tt.length; ++i)
    {
        UTrack t = tt[i];
        int cMillis = millis();
        //println(t.index + " | " + t.currentTik + " | " + t.totalTik + " | " + (cMillis-endLoopPlayerMillis));
        endLoopPlayerMillis = cMillis;
    }
}

// callback for each end loop track
void endLoop(UTrack _track)
{
    int cMillis = millis();
    //println((cMillis-endLoopMillis));
    endLoopMillis = cMillis;
    //println("     " + _track.index + " | " + player.currentTik + " | " + millis());
}

// callback for each note to play
int endNoteMillis =0;
void playNote(UNote _note)
{
    int nMillis = millis();
    println(_note.index + " | " + (nMillis-endNoteMillis));
    endNoteMillis = nMillis;

    //println("             " + _note.sequence.track.index + " | " + player.currentTik + " | " + millis());
}
