import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


class Towers {
  private int dammage, level, goldBonus, Exp, locX, locY, radius, radX, radY, upgradeCost, kills=0;
  private String type, effective, weakness;
  double timeInterval;
  private int count=0;
  private int count2=1;
  private int count3=1;
  private int frame=1; //frame counters
  private float gravity=0.5f;
  private float yPos, xPos;
  private float YVel=3.0f;
  private Image tower;
  private Image backleft, backright, arrowright, arrowleft, freeze1, freeze2, freeze3, freeze4, freeze5, freeze6; //images
  private Image fire1, fire2, fire3, fire4, fire5, fire6, fire7, fire8;
  private BadGuy enemy=null;
  private int exp2=500;
  private int exp3=2000;
  private int exp4=7000;
  private static ArrayList<BadGuy>badList=TowerDefense.getBadGuysList(); //gets badGuysList
  private static ArrayList<BadGuy>freezeList=new ArrayList<BadGuy>(); //list of enemies affected by the freeze tower
  private static ArrayList<BadGuy>removedList=new ArrayList<BadGuy>(); //enemies dead or out of radius to be removed
  private Image purpleray;
  private Image[] archerRight= new Image[12]; //archer sprites
  private Image[] archerLeft= new Image[12];
  private Image[] archerBehind= new Image[12];
  private Image[] archerBehindLeft= new Image[12];
  private Rectangle sizeCollide=new Rectangle(0,0,0,0);
  private boolean done=false;

  public Towers(String types, int x, int y) {
    type=types;
    locX=x;
    locY=y;
    yPos=locY-5;
    xPos=locX+50;
    backleft=new ImageIcon("backleft.png").getImage();
    backright=new ImageIcon("backright.png").getImage();
    arrowright=new ImageIcon("arrowright.png").getImage();
    arrowleft=new ImageIcon("arrowleft.png").getImage();
    for(int i=1; i<12; i++){ //loads the archer sprites
    archerRight[i] = new ImageIcon("archer right/"+i+".png").getImage();
    archerLeft[i] = new ImageIcon("archer left/"+i+".png").getImage();
    archerBehind[i] = new ImageIcon("archer behind/"+i+".png").getImage();
    archerBehindLeft[i] = new ImageIcon("behind left/"+i+".png").getImage();
    }
    freeze1=new ImageIcon("freeze/1.png").getImage(); //loads the freeze animation pictures
    freeze2=new ImageIcon("freeze/2.png").getImage();
    freeze3=new ImageIcon("freeze/3.png").getImage();
    freeze4=new ImageIcon("freeze/4.png").getImage();
    freeze5=new ImageIcon("freeze/5.png").getImage();
    freeze6=new ImageIcon("freeze/6.png").getImage();

    if(type.equals("Archer")) { //if tower is archer
      tower=new ImageIcon("archer.png").getImage(); //sets the tower image
      dammage=200; //sets the damage
      level=1; //initial level is 1
      radius=200; //sets radius
      sizeCollide.setBounds(locX+50,locY-5,60,67); //tower rectangle
      radX=80; //number to add to x postion to find middle of tower
      radY=28; //number to add to y postion to find middle of tower
      upgradeCost=250; //cost to upgrade
      effective="Goblin"; //effective against goblins
      weakness="Troll"; //weak against trolls
    }
    if(type.equals("Fire")) {
      tower=new ImageIcon("fire.png").getImage();
      dammage=2;
      level=1;
      radius=150;
      sizeCollide.setBounds(locX+100+7,locY-5-7,72,95);
      radX=135;
      radY=35;
      fire1=new ImageIcon("explosion/1.png").getImage();//loads fire animation pictures
      fire2=new ImageIcon("explosion/2.png").getImage();
      fire3=new ImageIcon("explosion/3.png").getImage();
      fire4=new ImageIcon("explosion/4.png").getImage();
      fire5=new ImageIcon("explosion/5.png").getImage();
      fire6=new ImageIcon("explosion/6.png").getImage();
      fire7=new ImageIcon("explosion/7.png").getImage();
      fire8=new ImageIcon("explosion/8.png").getImage();
      upgradeCost=300;
      effective="Troll";
      weakness="Orc";
    }
    if(type.equals("Magic")) {
      tower=new ImageIcon("magic.png").getImage();
      dammage=2;
      level=1;
      radius=200;
      sizeCollide.setBounds(locX+50,locY-5,60,115);
      radX=80;
      radY=52;
      upgradeCost=275;
      effective="Orc";
      weakness="Witch";
    }
    if(type.equals("Freeze")) {
      tower=new ImageIcon("freeze.png").getImage();
      dammage=0;
      level=1;
      radius=125;
      sizeCollide.setBounds(locX,locY,70,87);
      radX=35;
      radY=43;
      upgradeCost=325;
      effective="N/A";
      weakness="N/A";
    }
  }
  public void upgrade() { //method that updates tower stats if the tower has been upgraded
      if(type.equals("Archer") && level==2) { //if archer is level 2
          tower=new ImageIcon("archer2.png").getImage(); //new archer image is imported
          dammage+=10; //damage is increased
          upgradeCost+=250; //upgrade cost is increased
          radius+=15; // range and radius is incread
      }
      if(type.equals("Archer") && level==3) {
          tower=new ImageIcon("archer3.png").getImage();
          dammage+=20;
          upgradeCost+=325;
          radius+=18;
      }
      if(type.equals("Archer") && level==4) {
          tower=new ImageIcon("archer4.png").getImage();
          dammage+=50;
          radius+=20;
      }
      if(type.equals("Magic") && level==2) {
          tower=new ImageIcon("magic2.png").getImage();
          dammage++;
          upgradeCost+=500;
          radius+=10;
      }
      if(type.equals("Magic") && level==3) {
          tower=new ImageIcon("magic3.png").getImage();
          radius+=12;
          dammage++;
      }
      if(type.equals("Freeze") && level==2) {
          tower=new ImageIcon("freeze2.png").getImage();
          upgradeCost+=350;
      }
      if(type.equals("Freeze") && level==3) {
          tower=new ImageIcon("freeze3.png").getImage();
      }
      if(type.equals("Fire") && level==2) {
          tower=new ImageIcon("fire2.png").getImage();
          dammage++;
          upgradeCost+=500;
          radius+=12;
      }
      if(type.equals("Fire") && level==3) {
          tower=new ImageIcon("fire3.png").getImage();
          dammage++;
          radius+=14;
      }
  }
  public void dammage() { //damage method for every tower except freeae
      badList=TowerDefense.getBadGuysList(); //gets the badGuysList
      if(enemy==null) { //if there is no enemy 
          for(BadGuy w:badList) { //loops through the list
              if(Math.sqrt(((locX+radX-w.getX())*(locX+radX-w.getX()))+((locY+radY-w.getY())*(locY+radY-w.getY())))<=radius) { //uses circle formula to find if the enemy is in the tower's range
                  enemy=w; //enemy becomes that bad guy
                  break;//breaks the loop
              }
          }
      }
      if(enemy!=null) {//if there is an enemy
          if(Math.sqrt(((locX+radX-enemy.getX())*(locX+radX-enemy.getX()))+((locY+radY-enemy.getY())*(locY+radY-enemy.getY())))>radius) { //checks if the enemy is not in the tower's range anymore
              enemy=null; //removes the enemy
          }
      }

  }
  public void grayscale(Graphics g) { //draws the range of the towers
    Graphics2D g2d = (Graphics2D) g.create(); //Graphics2D is created
    Color black=new Color(0,0,0); //black colour is set
    //set the stroke of the copy, not the original
    g2d.setColor(black);
    Stroke dashed = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0); //dotted line command
    g2d.setStroke(dashed); //sets the stroke as dashed
    g2d.drawOval(locX+radX-radius,locY+radY-radius,radius*2,radius*2); // draws the circle
    g2d.dispose();
  }

  public void dammage2() { //method for the freeze tower to find and slow down enemies
  	badList=TowerDefense.getBadGuysList();
      for(BadGuy w:badList) { //loops through the badguys
              if(Math.sqrt(((locX+35-w.getX())*(locX+35-w.getX()))+((locY+43-w.getY())*(locY+43-w.getY())))<=radius) { //if the enemies are in the range
                  if(removedList.contains(w)==false) { //if enemies are not yet to be removed
                      if(w.getHp()<=0) { //if the enemey dies while under the freeze tower, added to the removedList
                          removedList.add(w);
                      }
                      if(Math.sqrt(((locX+35-w.getX())*(locX+35-w.getX()))+((locY+43-w.getY())*(locY+43-w.getY())))>radius) { //if the enemy is out of the range
                          removedList.add(w); //enemy is added to the remove list
                      }
                      if(level==1) { //if level is 1
                          if(w.getCounter()%2==0) { //speed of enemy is set to 0 when the counter has no remainder when divided by 2 (every 2 frames)
                              w.setSpeed(0);
                          }
                          else {
                              w.setOrSpeed(); //if not, speed is rgular
                          }
                      }
                      else if(level==2) { //if level is 2
                          if(w.getCounter()%2==0 || w.getCounter()%3==0) { //speed is 0 when the frame has no remainder when divided by both 2 and 3
                              w.setSpeed(0);
                          }
                          else {
                              w.setOrSpeed();//if not, speed is rgular
                          }
                      }
                      else { //level 3
                          if(w.getCounter()%2==0 || w.getCounter()%3==0 || w.getCounter()%5==0) {//speed is 0 when the frame has no remainder when divided by both 2, 3 and 5
                              w.setSpeed(0);
                          }
                          else {
                              w.setOrSpeed();//if not, speed is rgular
                          }
                      }
                      w.setCounter(); //increased the counter
                  }
              }

      }
  }

  public void draw(Graphics g) { //draws magic tower
    dammage(); //calls the damage method
    g.drawImage(tower,locX+50,locY-5,null); //draws the tower
    if(enemy!=null) { //if there is an enemy
        Graphics2D g2=(Graphics2D)g;
        Color purple=new Color(102,0,153);
        g.setColor(purple);
        g2.setStroke(new BasicStroke(7)); //purple stroke is set of thickness 7
        if(enemy.getType().equals("troll")) { //if the enemy is a troll, the line is drawn to the top of the tower to the middle of the troll (biggest so it needs its own if statement)
            g2.draw(new Line2D.Float(locX+77,locY-3,enemy.getX()+18,enemy.getY()+20));
        }
        else { //if other enemy, drawn to the same point (they are similar in size so it does not matter)
            g2.draw(new Line2D.Float(locX+77,locY-3,enemy.getX()+15,enemy.getY()+20));
        }
        int x=enemy.getHp(); //gets hp of enemy
        String enemyType=enemy.getType(); //gets enemy type
        if(enemyType.toUpperCase().equals(effective.toUpperCase())) { //if the tower is effective against enemy
            enemy.setHp(x,dammage*2); //enemy takes double damage
        }
        else if(enemyType.toUpperCase().equals(weakness.toUpperCase())) { //if tower is weak against enemy
            enemy.setHp(x,dammage/2); //enemy takes damage cut in half
        }
        else { //if neither, enemy takes regualr damage
            enemy.setHp(x,dammage);
        }
        if(enemy.getHp()<=0) { //if enemy has 0 or negative health
            kills++; //killstreak increases by 1
            enemy=null; 
            dammage();//tries to find new enemy
        }
    }
  }
  public void draw3(Graphics g) { //fire tower
      dammage(); //damage method is called
      g.drawImage(tower,locX+100+7,locY-5-7,null);
      if(enemy!=null) {
         Graphics2D g2d = (Graphics2D) g.create();
         Color red=new Color(255,0,0);
        //set the stroke of the copy, not the original
        g2d.setColor(red);
        Stroke dashed = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0); //code for dashed stroke of thickness 6
        g2d.setStroke(dashed);
        if(enemy.getType().equals("troll")) { //same as above, the line is drawn to the middle of the enemies, only troll is different since the end point needs to be modified
            g2d.drawLine(locX+100+7+36,locY-5-7,enemy.getX()+18,enemy.getY()+20);
        }
        else {
            g2d.drawLine(locX+100+7+36,locY-5-7,enemy.getX()+15,enemy.getY()+20);
        }
        //gets rid of the copy
        g2d.dispose();
        //draws explosion sprites
            if(count3<=3) { //count3 is frame counter, if frame is less or equal to 3
                g.drawImage(fire1,enemy.getX(),enemy.getY()-5,null); //draws picture 1
            }
            else if(count3<=6) {
                g.drawImage(fire2,enemy.getX(),enemy.getY()-5,null); //draws picture 2
            }
            else if(count3<=9) {
                g.drawImage(fire3,enemy.getX(),enemy.getY()-5,null);
            }
            else if(count3<=12) {
                g.drawImage(fire4,enemy.getX(),enemy.getY()-5,null);
            }
            else if(count3<=15) {
                g.drawImage(fire5,enemy.getX(),enemy.getY()-5,null);
            }
            else if(count3<=18) {
                g.drawImage(fire6,enemy.getX(),enemy.getY()-5,null);
            }
            else if(count3<=21) {
                g.drawImage(fire7,enemy.getX(),enemy.getY()-5,null);
            }
            else if(count3<=24) {
                g.drawImage(fire8,enemy.getX(),enemy.getY()-5,null);
            }
            if(count3==24) { // if counter reaches 24, resets back to 0
                count3=0;
            }
            count3++; //counter is increased

        int x=enemy.getHp(); //gets enemy hp
        String enemyType=enemy.getType(); //gets enemy type
        if(enemyType.toUpperCase().equals(effective.toUpperCase())) { //if strong, damage doubled
            enemy.setHp(x,dammage*2);
        }
        else if(enemyType.toUpperCase().equals(weakness.toUpperCase())) { //if weak, damage halfed
            enemy.setHp(x,dammage/2);
        }
        else { //regular damage
            enemy.setHp(x,dammage);
        }
        if(enemy.getHp()<=0) { //if enemy is dead
            kills++;
            enemy=null; //removed
            dammage(); //tries to find new enemy
            count3=1; //counter is reset
        }
    }
  }
  public void draw4(Graphics g) { //freeze tower
      if(done==false) { //if the freeze animation is not done
          if(count2<10) { //if frame counter is less than 10
              g.drawImage(freeze1,locX-90,locY-82,null); //draws 1st picture
          }
          else if(count2<20) {
              g.drawImage(freeze2,locX-90,locY-82,null);
          }
          else if(count2<30) {
              g.drawImage(freeze3,locX-90,locY-82,null);
          }
          else if(count2<40) {
              g.drawImage(freeze4,locX-90,locY-82,null);
          }
          else if(count2<50) {
              g.drawImage(freeze5,locX-90,locY-82,null);
          }
          else if(count2<60) {
              g.drawImage(freeze6,locX-90,locY-82,null);
          }
          g.drawImage(tower,locX,locY,null); //draws the tower
          count2++;
          if(count2>59) { //animation is done
              done=true;
          }
      }
      else {
          g.drawImage(freeze6,locX-90,locY-82,null); //if the animation is done, tower is drawn above final ice surface
          g.drawImage(tower,locX,locY,null);
          dammage2(); //damage2 method is called
      }
  }
  public void draw2(Graphics g) {// freeze tower
      dammage();
      dammage();
      g.drawImage(tower,locX+50,locY-5,null);// draws the archer base
      if(enemy!=null) { //if there is an enemy
          if(enemy.getX()>=locX+50+7 && enemy.getY()>locY-5-10) { //if enemy is in the bottom right quadrant compared to the tower
              if(frame==1 || frame==2) { //frames and draws the right animation (there are 4 different animations for the 4 quadrants
                  g.drawImage(archerRight[frame],locX+50+7+3,locY-5-28-3,null); //the pics are different sizes so the coordinates are set so that the archer stays in place
              }
              if(frame>2 && frame<9) {
                  g.drawImage(archerRight[frame],locX+50+7+3,locY-5-5-28-3,null);
              }
              if(frame>8 && frame<11) {
                  g.drawImage(archerRight[frame],locX+50+7+3,locY-5-28-3,null);
              }
              if(frame==11 || frame==12) {
                  g.drawImage(archerRight[frame],locX+50+7+3,locY-5-28-3,null);
              }
              if(frame>8) {
                  g.drawImage(arrowright,locX+50+7+37+(frame-8)*4,locY-5-28+7+(frame-8),null);
              }
          }
          if(enemy.getX()<locX+50+7 && enemy.getY()>locY-5-10) { //if enemy is in the top right quadrant compared to the tower
              if(frame==1 || frame==2) {
                  g.drawImage(archerLeft[frame],locX+50+5+3,locY-5-28-3,null);
              }
              if(frame>2 && frame<9) {
                  g.drawImage(archerLeft[frame],locX+50-9+5+3,locY-5-5-28-3,null);
              }
              if(frame>8 && frame<11) {
                  g.drawImage(archerLeft[frame],locX+50-8+5+3,locY-5-28-3,null);
              }
              if(frame==11 || frame==12) {
                  g.drawImage(archerLeft[frame],locX+50+5+3,locY-5-28-3,null);
              }
              if(frame>8) {
                  g.drawImage(arrowleft,locX+20-((frame-8)*4),locY-5-28+7+(frame-8),null);
              }
          }
          if(enemy.getY()<=locY-5-10 && enemy.getX()>=locX+50+7) { //if enemy is in the bottom left quadrant compared to the tower
              g.drawImage(archerBehind[frame],locX+50+7+3,locY-5-29-3,null);
              if(frame>6 && frame<11) {
                  g.drawImage(backright,locX+55+37+((frame-6)*4),locY-5-33+7-((frame-6)*2),null);
              }
          }
          if(enemy.getY()<=locY-5-10 && enemy.getX()<locX+50+7) { //if enemy is in the top left quadrant compared to the tower
              if(frame==1 || frame==2) {
                  g.drawImage(archerBehindLeft[frame],locX+50+7+3,locY-5-29-3,null);
              }
              if(frame>2 && frame<7) {
                  g.drawImage(archerBehindLeft[frame],locX+50+7-4+3,locY-5-29-3,null);
              }
              if(frame>6 && frame<9) {
                  g.drawImage(archerBehindLeft[frame],locX+50+7-6+3,locY-5-29-3,null);
              }
              if(frame==9 || frame==10) {
                  g.drawImage(archerBehindLeft[frame],locX+50+7+3,locY-5-29-3,null);
              }
              if(frame==11 || frame==12) {
                  g.drawImage(archerBehindLeft[frame],locX+50+7+9+3,locY-5-29-3,null);
              }
              if(frame>6 && frame<11) {
                  g.drawImage(backleft,locX+20-((frame-8)*4),locY-5-33+7-((frame-6)*2),null);
              }
          }
          count++; //counter is increased
          if(count==5) {//every 5 frames, frame increases
              frame++;
              count=0;
          }
          if(frame>11) { //when frame reaches 11, the archer shoots
              int x=enemy.getHp(); //gets enemy hp
              String enemyType=enemy.getType(); //gets enemy type
              if(enemyType.toUpperCase().equals(effective.toUpperCase())) { //if tower is effective
                  enemy.setHp(x,dammage*2); //damage isdoubled
              }
              else if(enemyType.toUpperCase().equals(weakness.toUpperCase())) { //if tower is weak
                  enemy.setHp(x,dammage/2); //damage is cut in half
              }
              else { //regular damage
                  enemy.setHp(x,dammage);
              }
              if(enemy.getHp()<=0) { //if enemy is dead
                   kills++; //killstreak increases
                   enemy=null; //enemy is removed
                   dammage(); //tries to locate a new enemy
              }
              frame=1; //frame resets to 1
          }
      }
      else { //if no enemy, basic archer stance is drawn
          g.drawImage(archerRight[1],locX+50+7+3,locY-5-28-3,null);
      }
  }
  public int getKills() { //returns amount of kills
      return kills;
  }
  public int getUpgradeCost() { //returns upgrade cost
      return upgradeCost;
  }
  public int getRadius() { //returns radius
      return radius;
  }
  public void setLevel() { //sets level
      level++;
  }
  public int getLevel() { //gets level
      return level;
  }
  public int getDammage() { //gets damage
      return dammage;
  }
  public Rectangle getRectangle() { //gets the tower rectangle
      return sizeCollide;
  }
  public String getType() {
      return type;
  }
  public int getX() {
      return locX;
   }
  public int getY() {
      return locY;
  }
  public String getEffective() {
      return effective;
  }
  public String getWeakness() {
      return weakness;
  }
  public void increaseDamage() { //method that increases damage for the damage ability
      if(type.equals("Archer")) { //if archer
          dammage+=200; //damage increased by 200
      }
      else if(type.equals("Freeze")==false) {
            dammage++; //damage increased by 1
      }
  }
  public void decreaseDamage() { //method that decreases damage for the damage ability
      if(type.equals("Archer")) { //if archer
          dammage-=200; //decrase by 200 to normal
      }
      else if(type.equals("Freeze")==false) {
            dammage--; //decrease damage by 1
      }
  }
  public void reset(){ //resets the removedList
  	ArrayList<BadGuy>removedList=new ArrayList<BadGuy>();
  }
}
