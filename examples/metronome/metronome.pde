/*
Metronome example with Midi notes and visual example
In order to run this example you must have TheMidiBus library installed (https://github.com/sparks/themidibus)
*/

import tododesign.*;
import themidibus.*;

UPlayer player = new UPlayer(this);
UTrack track = new UTrack();

Dot mdot = new Dot();
MidiBus myBus;

void setup()
{
  size(400,400);
  smooth();
  noStroke();
  
  myBus = new MidiBus(this, -1, "Java Sound Synthesizer");

  player.start();

  for(int i=0; i<4; ++i)
  {
      int nval = (i==0) ? 85 : 75;
      UNote note = new UNote(nval, 500);
      note.channel = 9;
      track.add(note);
  }

  player.add(track);    
  

}



void draw()
{
  background(0);
  mdot.draw();
}

void mouseMoved()
{
  //track.setDuration(mouseX*8);
}

void endLoop(UTrack t)
{
  println(t);
  mdot.loop();
}

// UPlay callback to send to midi device the note information
void playNote(UNote n)
{
    myBus.sendNoteOn(n.channel, n.note, n.velocity);
    mdot.tik();
}

class Dot
{
  int r;
  int f = 4;
  int c;
  Dot()
  {
    tik();
    loop();
  }
  
  void draw()
  {
    fill(255, c, c);
    pushMatrix();
    translate(width/2, height/2);
    ellipse(0,0,r,r);
    popMatrix();
    r-=f;
    c+=f;
    if(c>255) c=255;
  }
  
  void tik()
  {
    r=int(width/1.25);
  }
  
  void loop()
  {
    c=0;
  }
}





