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
class Fireball {
    private int locX, locY; //location
    private int destX, destY;
    private int frame, count;
    private Image fire1,fire2,fire3,fire4,fire5,fire6,fire7,fire8,fire9,fire10,fire11,fire12,fire13,fire14,fire15; //fireball images
    private boolean explosion, removeItem;
    private Rectangle imageRect=new Rectangle(0,0,0,0);
    private static ArrayList<BadGuy>badList=TowerDefense.getBadGuysList(); //gests the badguys list
    public Fireball(int x, int y) {
        removeItem=false; //if the ability should be removed
        destX=x;
        destY=y;
        count=0;
        frame=1;
        fire1=new ImageIcon("fireball/1.png").getImage(); //all sprites are loaded
        fire2=new ImageIcon("fireball/2.png").getImage();
        fire3=new ImageIcon("fireball/3.png").getImage();
        fire4=new ImageIcon("fireball/4.png").getImage();
        fire5=new ImageIcon("fireball/5.png").getImage();
        fire6=new ImageIcon("fireball/6.png").getImage();
        fire7=new ImageIcon("fireball/7.png").getImage();
        fire8=new ImageIcon("fireball/8.png").getImage();
        fire9=new ImageIcon("fireball/9.png").getImage();
        fire10=new ImageIcon("fireball/10.png").getImage();
        fire11=new ImageIcon("fireball/11.png").getImage();
        fire12=new ImageIcon("fireball/12.png").getImage();
        fire13=new ImageIcon("fireball/13.png").getImage();
        fire14=new ImageIcon("fireball/14.png").getImage();
        fire15=new ImageIcon("fireball/15.png").getImage();
        explosion=false; //if the explosion has happened or not
        locX=x-19; //x location set relative to mouse clicked
        locY=-107;
    }
    
    public void draw(Graphics g) {
        if(explosion==false) { //if explosion has not happened yet
            if(locY+107<destY) { //y coordinate of fireball increases until it reaches the clicked position
                locY+=3;
            }
            g.drawImage(fire1,locX,locY,null); //draws the falling fireball
            if(locY+107>=destY) { //if the fireball reaches the position
                explosion=true; //explosion is set to true
            }
        }
        if(explosion==true) { //if explosion is ture
            if(frame==1) { //if the frame is 1
                g.drawImage(fire2,destX-(fire2.getWidth(null)/2),destY-(fire2.getHeight(null)/2),null); //draws the image with modified coordinates so that its center is the mouse clicked position
                imageRect.setBounds(destX-(fire2.getWidth(null)/2),destY-(fire2.getHeight(null)/2),fire2.getWidth(null),fire2.getHeight(null)); //each image changes the explosion rectangle
                explosionDammage(); //method called that does damage to enemies
            }
            if(frame==2) {
                g.drawImage(fire3,destX-(fire3.getWidth(null)/2),destY-(fire3.getHeight(null)/2),null); //every image is a different size which is accounted for
                imageRect.setBounds(destX-(fire3.getWidth(null)/2),destY-(fire3.getHeight(null)/2),fire3.getWidth(null),fire3.getHeight(null));
                explosionDammage(); //every frame has its own rectangle of damage
            }
            if(frame==3) {
                g.drawImage(fire4,destX-(fire4.getWidth(null)/2),destY-(fire4.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire4.getWidth(null)/2),destY-(fire4.getHeight(null)/2),fire4.getWidth(null),fire4.getHeight(null));
                explosionDammage();
            }
            if(frame==4) {
                g.drawImage(fire5,destX-(fire5.getWidth(null)/2),destY-(fire5.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire5.getWidth(null)/2),destY-(fire5.getHeight(null)/2),fire5.getWidth(null),fire5.getHeight(null));
                explosionDammage();
            }
            if(frame==5) {
                g.drawImage(fire6,destX-(fire6.getWidth(null)/2),destY-(fire6.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire6.getWidth(null)/2),destY-(fire6.getHeight(null)/2),fire6.getWidth(null),fire6.getHeight(null));
                explosionDammage();
            }
            if(frame==6) {
                g.drawImage(fire7,destX-(fire7.getWidth(null)/2),destY-(fire7.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire7.getWidth(null)/2),destY-(fire7.getHeight(null)/2),fire7.getWidth(null),fire7.getHeight(null));
                explosionDammage();
            }
            if(frame==7) {
                g.drawImage(fire8,destX-(fire8.getWidth(null)/2),destY-(fire8.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire8.getWidth(null)/2),destY-(fire8.getHeight(null)/2),fire8.getWidth(null),fire8.getHeight(null));
                explosionDammage();
            }
            if(frame==8) {
                g.drawImage(fire9,destX-(fire9.getWidth(null)/2),destY-(fire9.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire9.getWidth(null)/2),destY-(fire9.getHeight(null)/2),fire9.getWidth(null),fire9.getHeight(null));
                explosionDammage();
            }
            drawPart2(g); //the other frames
            count++;
            if(frame==14) {
                removeItem=true;
            }
            if(count==5) {
                frame++;
                count=0;
            }
        }
    }
    public void drawPart2(Graphics g) { //part 2 of the frames
        if(frame==9) {
                g.drawImage(fire10,destX-(fire10.getWidth(null)/2),destY-(fire10.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire10.getWidth(null)/2),destY-(fire10.getHeight(null)/2),fire10.getWidth(null),fire10.getHeight(null));
                explosionDammage();
        }
        if(frame==10) {
                g.drawImage(fire11,destX-(fire11.getWidth(null)/2),destY-(fire11.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire11.getWidth(null)/2),destY-(fire11.getHeight(null)/2),fire11.getWidth(null),fire11.getHeight(null));
                explosionDammage();
        }
        if(frame==11) {
                g.drawImage(fire12,destX-(fire12.getWidth(null)/2),destY-(fire12.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire12.getWidth(null)/2),destY-fire12.getHeight(null),fire12.getWidth(null),fire12.getHeight(null));
                explosionDammage();
        }
        if(frame==12) {
                g.drawImage(fire13,destX-(fire13.getWidth(null)/2),destY-(fire13.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire13.getWidth(null)/2),destY-(fire13.getHeight(null)/2),fire13.getWidth(null),fire13.getHeight(null));
                explosionDammage();
        }
        if(frame==13) {
                g.drawImage(fire14,destX-(fire14.getWidth(null)/2),destY-(fire14.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire14.getWidth(null)/2),destY-(fire14.getHeight(null)/2),fire14.getWidth(null),fire14.getHeight(null));
                explosionDammage();
        }
        if(frame==14) {
                g.drawImage(fire15,destX-(fire15.getWidth(null)/2),destY-(fire15.getHeight(null)/2),null);
                imageRect.setBounds(destX-(fire15.getWidth(null)/2),destY-(fire15.getHeight(null)/2),fire15.getWidth(null),fire15.getHeight(null));
                explosionDammage();
        }
    }
    public void explosionDammage() {
        badList=TowerDefense.getBadGuysList(); //refreshes the list each time
        for(BadGuy w:badList) {//loops through all the badguys
            if(imageRect.contains(w.getX()+5,w.getY()+5)) { //if the rect contains the enemy
                int x=w.getHp(); //gets enemy hp
                w.setHp(x,80);//subtracts 80 hp every frame the enemy is in the rect
            }
        }
    }
    public boolean getRemove() { //returns if the ability is done or not
        return removeItem;
    }
}
                
                
        
            
            
        
        
    
        
