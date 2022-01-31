import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
class BadGuys {
    int difx,dify=0,numBadGuys=0;
    int[][]verts;
    private int[][]pathList;//path that the badguys follow
    Color green2;
    ArrayList<BadGuy> badList;//arraylist of badguy objects
    Base base;
    Maps maps;
    BadGuy badGuy;
    //pics
    Image[] orcRight= new Image[9];
    Image[] orcLeft= new Image[9];
    Image[] orcUp= new Image[9];
    Image[] orcDown= new Image[9];
    Image[] goblinRight= new Image[9];
    Image[] goblinLeft= new Image[9];
    Image[] goblinUp= new Image[9];
    Image[] goblinDown= new Image[9];
    Image[] trollRight= new Image[9];
    Image[] trollLeft= new Image[9];
    Image[] trollUp= new Image[9];
    Image[] trollDown= new Image[9];
    Image[] witchRight= new Image[9];
    Image[] witchLeft= new Image[9];
    Image[] witchUp= new Image[9];
    Image[] witchDown= new Image[9];
    Image[] witchSpell= new Image[6];


    public BadGuys(){
        badList=TowerDefense.getBadGuysList();//getting badguys from towerdefense
        base=TowerDefense.getBase();//getting base from towerdefense
		//pics
        for(int i=0; i<9; i++){
            orcRight[i] = new ImageIcon("orc/orc right/orc right "+i+".png").getImage();
            orcLeft[i] = new ImageIcon("orc/orc left/orc left "+i+".png").getImage();
            orcDown[i] = new ImageIcon("orc/orc down/orc down "+i+".png").getImage();
            orcUp[i] = new ImageIcon("orc/orc up/orc up "+i+".png").getImage();
            goblinRight[i] = new ImageIcon("goblin/goblin right/goblin right "+i+".png").getImage();
            goblinLeft[i] = new ImageIcon("goblin/goblin left/goblin left "+i+".png").getImage();
            goblinDown[i] = new ImageIcon("goblin/goblin down/goblin down "+i+".png").getImage();
            goblinUp[i] = new ImageIcon("goblin/goblin up/goblin up "+i+".png").getImage();
            trollRight[i] = new ImageIcon("troll/troll right/troll right "+i+".png").getImage();
            trollLeft[i] = new ImageIcon("troll/troll left/troll left "+i+".png").getImage();
            trollDown[i] = new ImageIcon("troll/troll down/troll down "+i+".png").getImage();
            trollUp[i] = new ImageIcon("troll/troll up/troll up "+i+".png").getImage();
            witchRight[i] = new ImageIcon("witch/witch right/witch right "+i+".png").getImage();
            witchLeft[i] = new ImageIcon("witch/witch left/witch left "+i+".png").getImage();
            witchDown[i] = new ImageIcon("witch/witch down/witch down "+i+".png").getImage();
            witchUp[i] = new ImageIcon("witch/witch up/witch up "+i+".png").getImage();
        }
        for(int i=0; i<6; i++){
        	witchSpell[i] = new ImageIcon("witch/witch spell/witch spell "+i+".png").getImage();
        }
    }

    public void move(){
    //	System.out.println(badList.size());
        maps=TowerDefense.getMaps();//getting maps from towerdefense
        pathList=maps.getPath();//gets the path that the badguys follow from maps
        int part, desx, desy;
        if(badList.size()==0){//when a round is over
            TowerDefense.loadBadGuys();//loading the next round of badguys in
        }
        for(int i=0; i<badList.size(); i++){//going through all badguys
            badGuy=badList.get(i);
            die();//checking health
            part=badGuy.getPart();//what part of the path the badguy is on
	        if(part==0 && maps.getMap()==3  && badGuy.getY()<500 || part==0 && maps.getMap()==5 && badGuy.getX()>=900){//some paths start from 2 diffrent directions so they have to skip over some parts of the pathlist that isnt part of their path
	        	badGuy.setPart();//setting which part the badguy is in
	        }
            desx=pathList[part][0];
            desy=pathList[part][1];
            if(badGuy.getDir()=="spell" && badGuy.getFrame()>=5){//when the witch goes through the spell casting animation once
		        badGuy.setDir(badGuy.getOldDir());//oldDir used for when a badguy changes direction, so that the frames can reset back to 0
		        badGuy.setFrame(0);
		    }
            if(badGuy.getDir()!="spell"){//when the witch casts a spell it stops moving
            	//moving the badguy,checking how it should move based on where it is comapred to where it should go in the pathlist
	            if(badGuy.getX()>desx+1) {//if badguys x coord is to the right of where it needs to go  +1 because some badguys move 2 pixels at a time
	                badGuy.setX(badGuy.getSpeed());//moving badguy
	                badGuy.setDir("left");//setting direction
	            }
	            if(badGuy.getX()<desx) {//if badguys x coord is to the left of where it needs to go
	                badGuy.setX(badGuy.getSpeed()*-1);//moving badguy
	                badGuy.setDir("right");//setting direction
	            }
	            if(badGuy.getY()>desy+1) {//if badguys x coord is t below where it needs to go
	                badGuy.setY(badGuy.getSpeed());//moving badguy
	                badGuy.setDir("up");//setting direction
	            }
	            if(badGuy.getY()<desy) {//if badguys x coord is above where it needs to go
	                badGuy.setY(badGuy.getSpeed()*-1);//moving badguy
	                badGuy.setDir("down");//setting direction
	            }
	            if(badGuy.getX()>desx-2 && badGuy.getY()>desy-2 && badGuy.getX()<desx+2 && badGuy.getY()<desy+2) {//checking if it reached the next part
	                badGuy.setPart();//setting the next part
	            }
            }
            if(badGuy.getDelay()==20 && badGuy.getDir()=="spell"){//used for when the witch casts its spell spell
                badGuy.setFrame(badGuy.getFrame()+1);//increasing frame
                badGuy.setDelay(0);//resetting delay
            }
            else if(badGuy.getDelay()==5 && badGuy.getType()=="goblin"){//goblins go through animation faster because they move faster
                badGuy.setFrame(badGuy.getFrame()+1);//setting frame
                badGuy.setDelay(0);//resetting delay
            }
            else if(badGuy.getDelay()==10){//slower troops
                badGuy.setFrame(badGuy.getFrame()+1);//setting frame
                badGuy.setDelay(0);//resetting delay
            }
            badGuy.setDelay(badGuy.getDelay()+1);//increasing delay
            if(badGuy.getFrame()>8){//9 pics per type of badguy
                badGuy.setFrame(0);//resetting frames
            }
            if(badGuy.getOldDir()!=badGuy.getDir() && badGuy.getDir()!="spell"){//used for when changing directions so the frames can reset but the witch dosnet change directions when casting a spell
                badGuy.setFrame(0);//setting frame back to 0 when changing direction
                badGuy.setOldDir(badGuy.getDir());//setting oldDir to current diretion
            }
            if (badGuy.getSpecial().equals("regen") && badGuy.getDir()!="spell"){//if witch is casting its spell
                regen(badGuy);//increase health method
            }
        }
		if(TowerDefense.getRound()==-1  && badList.size()<10+numBadGuys*1.5){//increasing the amount of badguys in the list in infinte wave
			numBadGuys++;//numbadguys used to increase the amount of badguys in the badlist
			TowerDefense.loadBadGuys();//loading more badguys into the badlist
		}
    }

    public void die(){//checking badguy hp and if it reached the tower
        if(badGuy.getX()>805 && badGuy.getY()<10 && maps.getMap()==1){//if badguy reached the tower
            badGuy.setHp(0,0);//setting hp to 0
            base.setMyGold(-badGuy.getGold());//was adding gold when badguy reached the base, so subtracting here
            badList.remove(badGuy);//removing badguy from the badlist
            base.baseHp(badGuy.getDamage());//damaging the tower
        }
        else if(badGuy.getX()<470 && badGuy.getX()>430 && badGuy.getY()<100 && maps.getMap()==2){//if badguy reached the tower
            badGuy.setHp(0,0);
            base.setMyGold(-badGuy.getGold());//was adding gold when badguy reached the base, so subtracting here
            badList.remove(badGuy);
            base.baseHp(badGuy.getDamage());
        }
        else if(badGuy.getX()<=10 && maps.getMap()==3){//if badguy reached the tower
            badGuy.setHp(0,0);
            base.setMyGold(-badGuy.getGold());//was adding gold when badguy reached the base, so subtracting here
            badList.remove(badGuy);
            base.baseHp(badGuy.getDamage());
        }
        else if(badGuy.getX()<=0 && maps.getMap()==4){//if badguy reached the tower
        	badGuy.setHp(0,0);
        	base.setMyGold(-badGuy.getGold());//was adding gold when badguy reached the base, so subtracting here
            badList.remove(badGuy);
            base.baseHp(badGuy.getDamage());
        }
        else if(badGuy.getX()>=850 && badGuy.getY()<300 && maps.getMap()==5){//if badguy reached the tower
        	badGuy.setHp(0,0);
            badList.remove(badGuy);
            base.baseHp(badGuy.getDamage());//was adding gold when badguy reached the base, so subtracting here
            badList.remove(badGuy);
            base.baseHp(badGuy.getDamage());
        }
        else if(badGuy.getX()<=0 && badGuy.getY()>330 && maps.getMap()==6){//if badguy reached the tower
        	badGuy.setHp(0,0);
            badList.remove(badGuy);
            base.baseHp(badGuy.getDamage());//was adding gold when badguy reached the base, so subtracting here
            badList.remove(badGuy);
            base.baseHp(badGuy.getDamage());
        }
        if(badGuy.getHp()<=0){//if badguy is dead
            base.setMyGold(badGuy.getGold());//increasing your gold
            badList.remove(badGuy);//removing from the badlist
        }
    }

    public void regen(BadGuy badGuy){//witch ability to heal surrounding badguys
        if(randint(1,300)==1){//randomly decide if it will use its baility
        badGuy.setDir("spell");//setting direction to spell so it dosnet move
        badGuy.setFrame(0);//resetting frame
            for(int i=0; i<badList.size(); i++){//going throuh all badguys
                BadGuy bad=badList.get(i);
                if(dist(badGuy.getX(),bad.getX(),badGuy.getY(),bad.getY())<=100){//checking how far away a badguy is
					bad.setHp(bad.getOldHp(),0);//setting hp to the badguys max
                }
            }
        }
    }

    public double dist(int x1, int x2, int y1, int y2){//distance formula
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }

    public void draw(Graphics g){
        for(int i=0; i<badList.size(); i++){//drawing all badguys
            BadGuy badGuy=badList.get(i);
            int f=badGuy.getFrame();
            if (badGuy.getType().equals("orc")){
                g.setColor(Color.red);
                g.fillRect(badGuy.getX()+10, badGuy.getY()-4, 15,2);//hp bar
                g.setColor(Color.green);
                g.fillRect(badGuy.getX()+10, badGuy.getY()-4,badGuy.getHp()/(badGuy.getOldHp()/15),2);//hp bar
                //drawing sprites based on direction its going
                if(badGuy.getDir().equals("left")){
                    g.drawImage(orcLeft[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("up")){
                    g.drawImage(orcUp[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("right")){
                    g.drawImage(orcRight[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("down")){
                    g.drawImage(orcDown[f],badGuy.getX(),badGuy.getY(),null);
                }
            }

            else if (badGuy.getType().equals("troll")){
                g.setColor(Color.red);
                g.fillRect(badGuy.getX()+10, badGuy.getY()-4, 15,2);//hp bar
                g.setColor(Color.green);
                g.fillRect(badGuy.getX()+10, badGuy.getY()-4,badGuy.getHp()/(badGuy.getOldHp()/15),2);//hp bar
                //drawing sprites based on direction its going
                if(badGuy.getDir().equals("left")){
                    g.drawImage(trollLeft[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("up")){
                    g.drawImage(trollUp[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("right")){
                    g.drawImage(trollRight[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("down")){
                    g.drawImage(trollDown[f],badGuy.getX(),badGuy.getY(),null);
                }
            }

            else if (badGuy.getType().equals("goblin")){
                g.setColor(Color.red);
                g.fillRect(badGuy.getX()+3, badGuy.getY()-4, 15,2);//hp bar
                g.setColor(Color.green);
                g.fillRect(badGuy.getX()+3, badGuy.getY()-4,badGuy.getHp()/(badGuy.getOldHp()/15),2);//hp bar
                //drawing sprites based on direction its going
                if(badGuy.getDir().equals("left")){
                    g.drawImage(goblinLeft[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("up")){
                    g.drawImage(goblinUp[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("right")){
                    g.drawImage(goblinRight[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("down")){
                    g.drawImage(goblinDown[f],badGuy.getX(),badGuy.getY(),null);
                }
            }

            else if (badGuy.getType().equals("witch")){
                g.setColor(Color.red);
                g.fillRect(badGuy.getX()+3, badGuy.getY()-4, 15,2);//hp bar
                g.setColor(Color.green);
                g.fillRect(badGuy.getX()+3, badGuy.getY()-4,badGuy.getHp()/(badGuy.getOldHp()/15),2);//hp bar
                //drawing sprites based on direction its going
                if(badGuy.getDir().equals("left")){
                    g.drawImage(witchLeft[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("up")){
                    g.drawImage(witchUp[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("right")){
                    g.drawImage(witchRight[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("down")){
                    g.drawImage(witchDown[f],badGuy.getX(),badGuy.getY(),null);
                }
                if(badGuy.getDir().equals("spell")){
                	g.drawOval(badGuy.getX()-35, badGuy.getY()-35,100,100);//drawing green circle around the area that the witch heals
                	green2=new Color(0,255,0,100);
                	g.setColor(green2);
                	g.fillOval(badGuy.getX()-35, badGuy.getY()-35,100,100);//drawing slightly transparent oval around healing area
                	g.drawImage(witchSpell[f],badGuy.getX(),badGuy.getY(),null);
                }
            }
        }
    }
    public static int randint(int low, int high){//randint
        int range=high-low+1;
        return (int)(Math.random()*range)+low;
  	}
	//setter and getter methods
  	public void setBadList(ArrayList<BadGuy> list){//used when resetting
  		badList=list;
  	}
	public void setNumBadGuys(int n){//used when resetting
		numBadGuys=0;
	}
  	public int getNumBadGuys(){
  		return numBadGuys;
  	}
}

