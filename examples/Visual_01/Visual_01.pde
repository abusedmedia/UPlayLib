/*
Visual_01 example
Create a player, a track and a sequence with 4 notes
No sound here, only println
*/


import tododesign.*;

UPlayer player;
UTrack track;
USequence sequence;

int num = 16;
Dot[] dots = new Dot[num];

void setup() 
{
    size (640, 480);
    background(0);
    smooth();
    noStroke();
    
    // create a player
    player = new UPlayer(this);
    
    // create a track
    track = new UTrack();
    player.add(track);
    
    // create a sequence
    sequence = new USequence();
    track.add(sequence);
    
    // add a bounch of notes
    for(int i=0; i<num; i++)
    {
        Dot note = new Dot("C3", 100+int(random(0,200)));
        sequence.add(note);
        
        dots[i] = note;
    }
    
    player.start();
}

void draw() 
{
    background (0, 10);
    for(int i=0; i<num; i++)
    {
        dots[i].draw();
    }
}


// callback for each end loop track
void endLoop(UTrack _track)
{
    println("End LOOP");
}

// callback for each note to play
void playNote(UNote _note)
{
}



class Dot extends UNote
{
  int x;
  int y;
  int r;
  float s = 1.0;
  int col=0;
  
  Dot(String strnote, int dur)
  {
    super(strnote, dur);
    x=int(random(0,width));
    y=int(random(0,height));
    r=10;
  }
  
  void draw()
  {
    col-=4;
    if(col<=0) col=0;
    
    s-=.05;
    if(s<=1) s=1;
    
    fill(255,col);
    pushMatrix();
    translate(x,y);
    scale(s);
    ellipse(0,0,r,r);
    popMatrix();
  }
  
  public void playNote()
  {
    col=255;
    s=4;
  }
  
}
