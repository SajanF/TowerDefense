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
class BadGuy{//objects used throughout the game that attack the base
    private String type, special, dir, oldDir;
    private int gold, hp, damage, x, y, speed, oldHp, frame, delay, segment, part,counter=1, initialS;
    public BadGuy(String line){
        String[]stats=line.split(" ");
        type=stats[0];//type of badguy
        hp=Integer.parseInt(stats[1]);//health of badguy
        oldHp=Integer.parseInt(stats[1]);//health of badguy used so that hp dosent exceed its maximum
        damage=Integer.parseInt(stats[2]);//damage the badguy does on the base
        gold=Integer.parseInt(stats[3]);//how much gold you get for killing a badguy
        x=Integer.parseInt(stats[4]);//x coord
        y=Integer.parseInt(stats[5]);//y coords
        speed=Integer.parseInt(stats[6]);//how fast a badguy is
        special=stats[7];//its special ability
        dir=stats[8];//the direction the badguy is facing
        oldDir=stats[8];//the direction it was facing in the previous frame. used for when changing a direction and resetting the frames
        frame=0;//used for sprites
        delay=0;//counter for how long you wait until you go to the next sprite pic
        part=0;//part of the path
        initialS=speed;//for freeze tower
    }
    //getter methods
    public int getCounter() {
        return counter;
    }
    public void setCounter() {
        counter++;
    }
    public void setOrSpeed() {
        speed=initialS;
    }
    public String getType(){
        return type;
    }
    public String getSpecial(){
        return special;
    }
    public String getDir(){
        return dir;
    }
    public String getOldDir(){
        return oldDir;
    }
    public int getHp(){
        return hp;
    }
    public int getOldHp(){
        return oldHp;
    }
    public int getDamage(){
        return damage;
    }
    public int getPart() {
        return part;
    }
    public void setPart() {
        Maps maps=TowerDefense.getMaps();
        if(part==0 && 500>y && maps.getMap()==3){
            part+=2;
        }
        else if(part==1 && maps.getMap()==3){
            part+=6;
        }
        else if (part==0 && maps.getMap()==5 && x>=900){
            part++;
        }
        else if(part==0 && maps.getMap()==5){
            part+=2;
        }
        else{
            part++;
        }
    }
    public int getGold(){
        return gold;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int x){
        speed=x;;
    }
    public int getDelay(){
        return delay;
    }
    public int getSeg(){
        return segment;
    }

    public int getFrame(){
        return frame;
    }

    public void setX(int n){
        x-=n;
    }
    public void setY(int n){
        y-=n;
    }
    public void setFrame(int f){
        frame=f;
    }
    public void setHp(int hh,int n){
        hp=hh-n;
    }
    public void setDir(String d){
        dir=d;
    }
    public void setOldDir(String d){
        oldDir=d;
    }
    public void setDelay(int d){
        delay=d;
    }
    public void setSeg(int s){
        segment=s;
    }
}