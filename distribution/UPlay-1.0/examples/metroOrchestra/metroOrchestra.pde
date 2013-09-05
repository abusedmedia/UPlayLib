/*
Metronome example with Midi notes and visual example
In order to run this example you must have TheMidiBus library installed (https://github.com/sparks/themidibus)
*/

import processing.opengl.*;

import tododesign.*;
import themidibus.*;

UPlayer player = new UPlayer(this);

MidiBus myBus;

MetroObject[] mos = new MetroObject[16];

void setup()
{
  size(400,400,OPENGL);
  smooth();
  noStroke();
  rectMode(CENTER);
  
  myBus = new MidiBus(this, -1, "Java Sound Synthesizer");
  player.start();
  
  int count = 0;
  for(int i=0; i<4; ++i)
  {
      for(int j=0; j<4; ++j)
      {
          MetroObject mo = new MetroObject(i,j,count);
          player.add(mo); 
          mos[count] = mo; 
          count++; 
      }
  }
  
}



void draw()
{
  background(0);
  
  for(int i=0; i<mos.length; ++i)
  {
     if(mos[i]!=null) mos[i].draw(); 
  }
}

int cc=0;
void keyPressed()
{
   if(key == '1')
   {
        mos[cc].mute = false;
        cc++;
        if(cc==mos.length) cc=0;
   } 
}

void endLoop(UTrack t)
{
  //MetroObject mo = (MetroObject) t;
  //mo.loop();
}


// UPlay callback to send to midi device the note information
void playNote(UNote n)
{
    myBus.sendNoteOff(n.channel, n.note, n.velocity);
    int v = int(random(30,127));
    if(n.isOff)
    {
        n.isOff = false;
        myBus.sendNoteOn(n.channel, n.note, v);
        UTrack t = n.sequence.track;
        MetroObject mo = (MetroObject) t;
        mo.tik(v);
    }
    
    
}



