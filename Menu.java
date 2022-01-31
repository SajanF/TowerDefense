import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
class Menu{
    private int destx, desty, oldx=0, oldy=0;
    private Image mainMenu=new ImageIcon("mainmenu.png").getImage();
    private Image levels=new ImageIcon("levels.png").getImage();
    private Image instructions=new ImageIcon("instructions.png").getImage();
    private Image creators=new ImageIcon("creators.png").getImage();
    private Image gameFinish=new ImageIcon("mapFinish.png").getImage();
    private Rectangle level, map1, map2, map3, map4, map5, map6,quit, again, inst, creatRect, exitLevel, exitInst, exitCreator, infinite;
    private boolean ifLevels, ifInfinite;
    private ArrayList<Rectangle>rectangleLists=new ArrayList<Rectangle>();//arraylist of map rects
    private int mapNumber=0,pg=1;
    private int round, map, wait=0;
    Base base;

    public Menu() {
    	creatRect= new Rectangle(669,293,185,149);
        level=new Rectangle(245,293,185,149);
        infinite=new Rectangle(457,293,185,149);
        inst= new Rectangle(33,293,185,149);
        exitInst= new Rectangle(740,70,60,60);
        exitCreator= new Rectangle(650,120,60,60);
        exitLevel= new Rectangle(656,115,44,65);
        map1=new Rectangle(0,0,0,0);
        map2=new Rectangle(0,0,0,0);
        map3=new Rectangle(0,0,0,0);
        map4=new Rectangle(0,0,0,0);
        map5=new Rectangle(0,0,0,0);
        map6=new Rectangle(0,0,0,0);
        map1=new Rectangle(226,261,93,96);
        rectangleLists.add(map1);
        map2=new Rectangle(226,359,93,96);
        rectangleLists.add(map2);
        map3=new Rectangle(226,456,93,96);
        rectangleLists.add(map3);
        map4=new Rectangle(445,261,93,96);
        rectangleLists.add(map4);
        map5=new Rectangle(445,359,93,96);
        rectangleLists.add(map5);
        map6=new Rectangle(445,456,93,96);
        rectangleLists.add(map6);
        again=new Rectangle(243,100,150,100);
        quit=new Rectangle(457,100,150,100);
        ifLevels=false;
        ifInfinite=false;
        base=TowerDefense.getBase();
    }
    public boolean click(){//if u click
    	if(oldx!=destx && oldy!=desty){//compare last place u clicked and where u just clicked
    		return true;
    	}
    	return false;
    }

    public void draw(Graphics g){
    	if(pg==1){//main menu
    		drawHome(g);
    	}
    	else if(pg==2){//levels
    		drawMaps(g);
    	}
    	else if(pg==3){//instructions
    		drawInst(g);
    	}
    	else if(pg==4){//creators page
    		drawCreators(g);
    	}
    	else if(pg==5){//end page
    		drawEnd(g);
    	}
    }

    public void drawHome(Graphics g){//main menu where u can click on rounds, infinite wave, creators, or instructions
	    g.drawImage(mainMenu,0,0,null);//main menu pic
	    destx=base.getXCord();//getting coords
	    desty=base.getYCord();
	    if(level.contains(destx,desty)) {//if u click on rounds
			round=0;//resetting round
			pg=2;//setting next page
			oldx=destx;//setting old coords
			oldy=desty;
	    }
	    if(infinite.contains(destx,desty)) {//if u click on infinite waves
	 		round=-1;//setting round
	 		pg=2;//setting next page
	 		oldx=destx;//setting old coords
			oldy=desty;
	     }
	     if(inst.contains(destx,desty)){//if u click on instructions
	        pg=3;//setting next page
	        oldx=destx;//setting old coords
			oldy=desty;
    	}
    	if(creatRect.contains(destx,desty)){//if u click on creators
	        pg=4;//setting next page
	        oldx=destx;//setting old coords
			oldy=desty;
    	}
    }

    public void drawMaps(Graphics g){//if u selected a game mode
    	destx=base.getXCord();//getting coords
	    desty=base.getYCord();
	    g.drawImage(levels,0,0,null);//drawing all the maps
	    if(click()==true){//if u clicked
		    for(int i=0; i<rectangleLists.size(); i++){//going through all the map rects
			    Rectangle x=rectangleLists.get(i);
			    if(x.contains(destx,desty)) {//if u clicked on a map
				    base.setStart(true);//setting base to start so that it can start drawing the map and badguys
					Maps maps=new Maps(i+1);//setting the map
					TowerDefense.setRound(round);//setting the round
					TowerDefense.setMap(maps);//setting map
					TowerDefense.setLoad(true);//allowing badguys to be enterd into the badlist(arrayList of badguy objects)
					TowerDefense.loadBadGuys();//loading the badguy objects into the badlist
					pg=5;//setting the page
				}
			}
			if(exitLevel.contains(destx,desty)){//if u click on the x
				pg=1;//setting the page
				oldx=destx;//setting old coords
				oldy=desty;
			}
	    }
    }

    public void drawInst(Graphics g){
    	g.drawImage(instructions,0,0,null);//instructions page
    	destx=base.getXCord();//getting coords
    	desty=base.getYCord();
    	if(exitInst.contains(destx,desty)){//if u click thex
    		pg=1;//setting page
    		oldx=destx;//setting old coords
    		oldy=desty;
    	}
    }

    public void drawCreators(Graphics g){
    	g.drawImage(creators,0,0,null);//drawing creators page
    	destx=base.getXCord();//getting coords
    	desty=base.getYCord();
    	if(exitCreator.contains(destx,desty)){//if u click the x
    		pg=1;//setting page
    		oldx=destx;//setting old coords
    		oldy=desty;
    	}
    }

    public void drawEnd(Graphics g){//page when u finsihed the game
    	g.drawImage(gameFinish,0,0,null);//drawing end page
    	destx=base.getXCord();//getting coords
    	desty=base.getYCord();
    	if(quit.contains(destx,desty)){//if u dont want to play again
			System.exit(0);//closes
    	}
    	if(again.contains(destx,desty)){//if u do want to play again
    		pg=1;//setting page
    		oldx=destx;//setting old coords
    		oldy=desty;
    		base.setStart(false);//resetting everything
    	}
    }
	//getter methods
    public int getRound() {
        return round;
    }
    public int getmap() {
        return mapNumber;
    }
    //setter methods
    public void setPg(int n){
    	pg=n;
    }
}
            
            
            
        
            
            
    
        
    
    

