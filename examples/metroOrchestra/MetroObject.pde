class MetroObject extends UTrack
{
  
  Dot mdot = new Dot();
  int pi;
  int pj;
  
  String[] strNotes = {"C6", "D5", "C5", "B5", "A5", "E4", "D4", "C4", "C3", "D3", "E2", "A3", "A5", "B4", "E3", "C4", "F2"};

  
  MetroObject(int _pi, int _pj, int count)
  {
      pi=_pi;
      pj=_pj;
      
      //this.mute=true;
      
      int dur = 750 * (count+1);// + int(random(0,16))*100;
      for(int i=0; i<4; ++i)
      {
          UNote note = new UNote(strNotes[count], dur);
          note.channel = 1;
          note.liveness = 50+10*i;
          note.velocity = int(random(70,127));
          this.add(note);
      }

  }
  
  
  void draw()
  {
      pushMatrix();
      translate(width/4 * pj, height/4 * pi);
      mdot.draw();
      popMatrix();
  }
  
  void tik(int v)
  {
      mdot.tik(v);
  }
  
  public void endLoop()
  {
      mdot.loop();
  }
}
  
  
  
class Dot
{
  float r;
  float f = random(1.1,1.9);
  float c;
  Dot()
  {
    tik(0);
    loop();
  }
  
  void draw()
  {
    fill(255, 255, 255);
    pushMatrix();
    translate(width/8, height/8);
    ellipse(0,0,r,r);
    popMatrix();
    r-=f;
    if(r<0) r=0;
    //c+=10;
    c-=f;
    if(c<0) c=0;
  }
  
  void tik(int v)
  {
    //r=int(width/4);
    r = map(v, 0, 127, width/8, width/4);
    c = map(v, 0, 127, 0, 255);
  }
  
  void loop()
  {
    c=0;
  }
}





