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
class Damage {
    Base base;
    private int count, frame; //frame counters
    private Image damage1, damage2, damage3, damage4, damage5, damage6, damage7, damage8, damage9, damage10; //damage animation pictures
    private boolean removeItem;
    public Damage() {
        count=0;
        frame=1;
        damage1=new ImageIcon("damage/1.png").getImage(); //images are initialized
        damage2=new ImageIcon("damage/2.png").getImage();
        damage3=new ImageIcon("damage/3.png").getImage();
        damage4=new ImageIcon("damage/4.png").getImage();
        damage5=new ImageIcon("damage/5.png").getImage();
        damage6=new ImageIcon("damage/6.png").getImage();
        damage7=new ImageIcon("damage/7.png").getImage();
        damage8=new ImageIcon("damage/8.png").getImage();
        damage9=new ImageIcon("damage/9.png").getImage();
        damage10=new ImageIcon("damage/10.png").getImage();
        removeItem=false; //if the damage ability is over
    }
    public void draw(Graphics g) {
        if(frame<=5) { //if frame is less than pr equal to 5
            g.drawImage(damage1,330,-468,null); //draws first image
        }
        else if(frame<=10) { //if the frame is less or equal to 10
            g.drawImage(damage2,330,-468,null); //draws second image
        }
        else if(frame<=15) {
            g.drawImage(damage3,330,-468,null);
        }
        else if(frame<=20) {
            g.drawImage(damage4,330,-468,null);
        }
        else if(frame<=25) {
            g.drawImage(damage5,330,-468,null);
        }
        else if(frame<=30) {
            g.drawImage(damage6,330,-468,null);
        }
        else if(frame<=35) {
            g.drawImage(damage7,330,-468,null);
        }
        else if(frame<=40) {
            g.drawImage(damage8,330,-468,null);
        }
        else if(frame<=45) {
            g.drawImage(damage9,330,-468,null);
        }
        else if(frame<=50) {
            g.drawImage(damage10,330,-468,null);
        }
        if(frame==50) { //if frame has reached 50
            frame=0; //frame is set to 0
        }
        frame++; //frame increases
        count++; //count increases
        if(count==600) { //when count reaches 600 meaning the animation has been looped 12 times
            removeItem=true; //ability is over
        }
    }
    public boolean getRemove() { //returns if the ability is over or not to be removed
        return removeItem;
    }
}