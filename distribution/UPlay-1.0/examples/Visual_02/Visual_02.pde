import tododesign.*;

UPlayer player;

LineTrack[] lines = new LineTrack[5];
DotNote[] dots = new DotNote[100];

int radius = 60;

void setup() {
  size(600,400);
  smooth();
  
  // create the main player and start it, 
  // so it will be ready for any new tracks added
  player = new UPlayer(this);
  player.start();
  
  for(int i=0; i<dots.length; ++i) dots[i] = new DotNote(i);

  for(int i=0; i<lines.length; ++i) lines[i] = new LineTrack(i);
  
}





void draw()
{
  background(0);
  pushMatrix();
  translate(width/2,height/2);
  fill(255);
  ellipse(0,0,20,20);
  
  for(int i=0; i<lines.length; ++i) lines[i].draw();

  for(int i=0; i<dots.length; ++i) dots[i].draw();
  
  popMatrix();
}


void endLoop(UTrack t)
{
    //println("End loop");
}

void playNote(UNote n)
{
    USequence s = n.sequence;
    
    float ang = TWO_PI/s.getTotalNotes();
    
    
    int _radius = (s.index+1) * radius/2;
    
    float x = cos(ang*n.index) * _radius;
    float y = sin(ang*n.index) * _radius;
    
    int noteIndex = s.index * 20 + n.index;
    
    DotNote d = dots[noteIndex];
    
    d.x = x;
    d.y = y;
    d.play();
    
}

void keyPressed()
{
  if(key=='1') example0201();
  if(key=='2') example0202();
  if(key=='3') example0203();
  if(key=='4') example0204();
  if(key=='5') example0205();
}






void example_01()
{
  // create a track and add to the player.
  // since it is empty, nothing will happen so far.
  UTrack track = new UTrack();
  player.add(track);
  
  // create a sequence and add it to the track
  USequence sequence = new USequence();
  track.add(sequence);
  
  // add a bunch of notes
  String notes[] = {"C3","C4","C5","A3","A4","A5","B3","B4","B5","D4","D5","D3","E3","E4","E5"};
  for(int i=0; i<8; i++)
  {
      String pickNote = notes[int(random(0,notes.length))];
      int duration = 500;
      UNote n = new UNote(pickNote, duration);
      sequence.add( n ); 
  }
  
}

UTrack track = new UTrack();
String notes[] = {"C3","C4","C5","A3","A4","A5","B3","B4","B5","D4","D5","D3","E3","E4","E5"};
void example0201()
{
  // create a track and add to the player.
  // since it is empty, nothing will happen so far.
  
  player.add(track);
  
  // create a sequence and add it to the track
  USequence sequence = new USequence();
  track.add(sequence);
  
  // add a bunch of notes
  
  for(int i=0; i<4; i++)
  {
      String pickNote = notes[int(random(0,notes.length))];
      int duration = 100;
      
      UNote n = new UNote(pickNote, duration);
      //if(random(0,1)>.8) n.mute = true;
      sequence.add( n ); 
  }
  sequence.setDuration(4000);
}


void example0202()
{
  USequence sequence = new USequence();
  track.add(sequence);
  
  for(int i=0; i<8; i++)
  {
      String pickNote = notes[int(random(0,notes.length))];
      int duration = 100;
      UNote n = new UNote(pickNote, duration);
      sequence.add( n ); 
  }
  sequence.setDuration(4000);
}

void example0203()
{
  USequence sequence = new USequence();
  track.add(sequence);
  
  for(int i=0; i<16; i++)
  {
      String pickNote = notes[int(random(0,notes.length))];
      int duration = 100;
      UNote n = new UNote(pickNote, duration);
      sequence.add( n ); 
  }
  sequence.setDuration(4000);
}
void example0204()
{
  USequence sequence = new USequence();
  track.add(sequence);
  
  for(int i=0; i<32; i++)
  {
      String pickNote = notes[int(random(0,notes.length))];
      int duration = 100;
      UNote n = new UNote(pickNote, duration);
      sequence.add( n ); 
  }
  sequence.setDuration(4000);
}
void example0205()
{
  USequence sequence = new USequence();
  track.add(sequence);
  
  for(int i=0; i<64; i++)
  {
      String pickNote = notes[int(random(0,notes.length))];
      int duration = 100;
      UNote n = new UNote(pickNote, duration);
      if(random(0,1)>.7) n.mute=true;
      sequence.add( n ); 
  }
  sequence.setDuration(4000);
}


class LineTrack
{
  
  int x;
  int y;
  int id;
  
  LineTrack(int _id)
  {
    id=_id;
  }
  
  void draw()
  {
    stroke(70);
    noFill();
    ellipse(0,0,(id+1)*radius, (id+1)*radius);
  }
}


class DotNote
{
  float r = 8;
  float minR = 8;
  float x=-width;
  float y=-height;
  
  int col = 0;
  int minCol=10;
  int i=-4;
  
  int strcol=0;
  
  int id;
  
  DotNote(int _id)
  {
      id=_id;
  }
  
  void draw()
  {
      stroke(255,strcol);
      line(0,0,x,y);

      fill(col);
      stroke(50);
      pushMatrix();
      translate(x,y);
      ellipse(0,0,r,r);
      popMatrix();
      
      col+=i;
      if(col<=minCol)
      {
         i=0;
         col=minCol; 
      }
      
      r-=.5;
      if(r<minR) r=minR;
      
      strcol-=10;
      if(strcol<=0) strcol=0;
  }
  
  void play()
  {
      i=-4;
      col = 255;
      
      r=16;
      
      strcol=255;
  }
  
}
