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
import java.awt.MouseInfo;
class Base extends JPanel{
    Image grass, base, spot, towerSelect, archerUpgrade, magicUpgrade, freezeUpgrade, fireUpgrade, table, up;
    Image fireprogression, magicprogression, iceprogression, archerprogression, meteorIcon, redellipse, gold, damage;
    BadGuys badGuys;
    Maps maps;
    Menu menu;
    Font fontSys=null;
    private boolean ifOpen=false;
    private Towers tracker2;
    private Rectangle tracker;
    private Rectangle upgradelvl=new Rectangle(0,0,0,0);
    private boolean built=false,start=false, end=false;
    public boolean ready=false;
    int baseHp=500, myGold=600;
    private int destx, desty, olddestx=0, olddesty=0, olderX=0, olderY=0, towersSize=0;
    private Rectangle open1,open2,open3,open4,open5,open6;
    private Rectangle select1=new Rectangle(0,0,0,0);
    private Rectangle select2=new Rectangle(0,0,0,0);
    private Rectangle select3=new Rectangle(0,0,0,0);
    private Rectangle select4=new Rectangle(0,0,0,0);
    private Rectangle meteorSelect=new Rectangle(170,15,55,55);
    private Rectangle damageSelect=new Rectangle(270,15,55,55);
    private Rectangle screenRect=new Rectangle(0,0,0,0);
    private Rectangle ups=new Rectangle(430,737,25,25);
    private Rectangle down=new Rectangle(0,0,0,0);
    private Rectangle tables=new Rectangle(0,0,0,0);
    private ArrayList<Rectangle>rectangleList=new ArrayList<Rectangle>();
    private ArrayList<Towers>towersList=new ArrayList<Towers>();
    private ArrayList<Rectangle>removeList=new ArrayList<Rectangle>();
    private ArrayList<Fireball>fireballList=new ArrayList<Fireball>();
    private ArrayList<Damage>damageList=new ArrayList<Damage>();

    public Base(){
        damage=new ImageIcon("damage.png").getImage();
        gold=new ImageIcon("gold.png").getImage();
        redellipse=new ImageIcon("redellipse.png").getImage();
        meteorIcon=new ImageIcon("meteoricon.png").getImage();
        up= new ImageIcon("up.png").getImage();
        table= new ImageIcon("table.png").getImage();
        grass = new ImageIcon("grass.jpeg").getImage();
        spot = new ImageIcon("open.png").getImage();
        base = new ImageIcon("base3.png").getImage();
        towerSelect = new ImageIcon("towerSelect.png").getImage();
        archerUpgrade=new ImageIcon("archerupgrade.png").getImage();
        magicUpgrade=new ImageIcon("magicupgrade.png").getImage();
        freezeUpgrade=new ImageIcon("freezeupgrade.png").getImage();
        fireUpgrade=new ImageIcon("fireupgrade.png").getImage();
        fireprogression=new ImageIcon("fireprogression.png").getImage();
        magicprogression=new ImageIcon("magicprogression.png").getImage();
        archerprogression=new ImageIcon("archerprogression.png").getImage();
        iceprogression=new ImageIcon("iceprogression.png").getImage();
        addMouseListener(new clickListener());
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready=true;
    }

    public void reset(){ //resets all variables etc when done playing
        TowerDefense.setLoad(false);//stops loading badguy objects into the badlist
        badGuys.setNumBadGuys(0);//counter used in infinite wave is reset
		towersList=new ArrayList<Towers>();//getting rid of all the built towers
    	start=false;//so the menu is drawn instead of the map
        badGuys.setBadList(new ArrayList<BadGuy>());//emptying the badlist
        TowerDefense.reset();//resetting towerdefense
        TowerDefense.setRound(0);//setting the round back to 0
        maps.reset();//resetting maps
		baseHp=500;//resets base health
		myGold=600; // resets starting gold
		built=false;//method that gets spots where u can build towers is called
    }

    public void baseHp(int damage){ //method that resets base if the player lost the level
        baseHp-=damage;//subtracting damage
        if(baseHp<=0){//if u lose
         	reset();//resetting the game
        }
    }
    public void setMyGold(int n){ //method that adds to the player's gold
        myGold+=n;//adding gold
    }

    public void paintComponent(Graphics g){
        if(start==false){ //if a level has not been chosen yet
            menu=TowerDefense.getMenu(); //gets the menu
            menu.draw(g); //draws the menu
            TowerDefense.setMenu(menu);//setting menu
        }
        else{
            if(TowerDefense.getRound()==5){ //if the player completes all 4 rounds
                reset(); //resets base
            }
            badGuys=TowerDefense.getBadGuys(); //gets the badguys list
            maps=TowerDefense.getMaps(); //gets the maps
            badGuys.move(); //calls the move method in BadGuys
            if(rectangleList.size()==0 && built==false){
               	rectangleList=maps.setRectList();
            }
           	//System.out.println(rectangleList.size());
            maps.draw(g); //draws the map
            g.setColor(Color.black); //sets colour to black
            fontSys = new Font("Comic Sans MS",Font.PLAIN,20); //new font
            g.setFont(fontSys); //sets the font
            String finalString="Gold: "+Integer.toString(myGold); //String that shows the player's gold
            g.drawString(finalString, 50, 50); //draws the string
            drawTheMaps(g);//draws the map base
            for(Towers x:towersList) { //loops through the towers list and draws the freeze tower
                if(x.getType().equals("Freeze")) {
                    x.draw4(g);
                }
            }
            badGuys.draw(g); //draws the badguys afterwards so they walk on top of the ice
            for(Towers x:towersList) { //then loops through the arraylist and draws the other 3 towers
                if(x.getType().equals("Magic")) {
                    x.draw(g);
                }
                if(x.getType().equals("Archer")) {
                    x.draw2(g);
                }
                if(x.getType().equals("Fire")) {
                    x.draw3(g);
                }
            }
            selectTheTowers(g); //method that selects the towers
            g.drawImage(meteorIcon,170,15,null); //draws the meteor ability
            fontSys = new Font("Comic Sans MS",Font.PLAIN,20);
            g.setFont(fontSys);
            String cost1="35"; //cost of meteor ability icon
            String cost2="100"; //cost of damage ability
            g.setColor(Color.black);
            g.drawString(cost1, 230, 50);
            g.drawImage(damage,270,15,null); //draws the damage ability icon
            g.drawString(cost2,330,49);
            damage(g); //calls the damage method
            upgrade(g); //method for if the player wants to upgrade a tower
            meteor(g); //calls the meteor ability
            g.drawImage(up,430,737,null); //draws the arrow that the player uses to pull up the tower information menu
            table(g); //calls the information table method
        }
    }
    public void selectTheTowers(Graphics g) { //method that selects the towers
        for(int i=0; i<rectangleList.size(); i++){ //loops through the available tower spots
                Rectangle x=rectangleList.get(i);  //x takes the value of each rectangle
                if(x.contains(destx,desty) || select1.contains(destx,desty) || select2.contains(destx,desty)
                || select3.contains(destx,desty) || select4.contains(destx,desty)) { //if the player clicked on any of the rectangles
                    if(x.contains(destx,desty)) { //if the spot is clicked
                        tracker=x; //tracker is a variable used to keep track of the rectangle before so that the wrong spot is not removed
                        //(after the spot is removed from the list, the indexes change so we need to keep track of the previous rectangle
                        int xs=(int) x.getX(); //gets x coord of the spot
                        int ys=(int) x.getY(); //gets y coord of the spot
                        g.drawImage(towerSelect,xs-71,ys-120,null); //draws the towerSelect table
                        select1.setBounds(xs-71+144,ys-120+15,42,42); //4 rectangles for each 4 towers are set
                        select2.setBounds(xs-71+14,ys-120+15,42,42);
                        select3.setBounds(xs-71+14,ys-120+119,42,42);
                        select4.setBounds(xs-71+144,ys-120+119,42,42);
                    }
                    if(select1.contains(destx,desty) && myGold>=100) { // if the 1st rectangle is then clicked
                        built=true;
                        int xs=(int) select1.getX();//gets x coord of the spot
                        int ys=(int) select1.getY();//gets y coord of the spot
                        loadTowers(xs-122,ys+26,"Magic"); //loads a magic tower
                        setSelect();//method that changes the 4 rectangles back to (0,0,0,0) -> non existant
                        rectangleList.remove(tracker);
                    }
                    else if(select2.contains(destx,desty) && myGold>=100) { //if the user has enough gold
                        built=true;
                        int xs=(int) select2.getX();
                        int ys=(int) select2.getY();
                        loadTowers(xs-85+95,ys+49+25,"Archer");//loads an archer tower
                        setSelect();
                        rectangleList.remove(tracker);
                    }
                    else if(select3.contains(destx,desty) && myGold>=100) {
                        built=true;
                        int xs=(int) select3.getX();
                        int ys=(int) select3.getY();
                        loadTowers(xs-50,ys-45,"Fire");//loads a fire tower
                        setSelect();
                        rectangleList.remove(tracker);
                    }
                    else if(select4.contains(destx,desty) && myGold>=100) {
                        built=true;
                        int xs=(int) select4.getX();
                        int ys=(int) select4.getY();
                        loadTowers(xs-79,ys-58,"Freeze"); //loads a freeze tower
                        setSelect();
                        rectangleList.remove(tracker); //removes the tracker rectangle from the list (previous rectangle after the arraylist changed)
                    }
                }
            }
    }
    public void drawTheMaps(Graphics g) {
        if(maps.getMap()==1){ //if first level
                g.drawImage(base,807,0,null); //draws the player base
                g.setColor(Color.red); //sets colour to red for the hp
                g.fillRect(812,5,40,5); //draws the red hp bar which is underneath the green hp bar
                g.setColor(Color.green); //the green rectangle is the one that changes shape to give the appearence of an hp bar
                g.fillRect(812,5,baseHp/(500/40),5);
            }
            if(maps.getMap()==2){ //if second level
                g.drawImage(base,450,60,null);
                g.setColor(Color.red);
                g.fillRect(455,65,40,5);
                g.setColor(Color.green);
                g.fillRect(455,65,baseHp/(500/40),5);
            }
            if(maps.getMap()==3){
                g.drawImage(base,0,100,null);
                g.setColor(Color.red);
                g.fillRect(5,105,40,5);
                g.setColor(Color.green);
                g.fillRect(5,105,baseHp/(500/40),5);
            }
            if(maps.getMap()==4){
                g.drawImage(base,0,565,null);
                g.setColor(Color.red);
                g.fillRect(5,570,40,5);
                g.setColor(Color.green);
                g.fillRect(5,570,baseHp/(500/40),5);
            }
            if(maps.getMap()==5){
                g.drawImage(base,830,130,null);
                g.setColor(Color.red);
                g.fillRect(835,135,40,5);
                g.setColor(Color.green);
                g.fillRect(835,135,baseHp/(500/40),5);
            }
	    if(maps.getMap()==6){
                g.drawImage(base,0,350,null);
                g.setColor(Color.red);
                g.fillRect(5,340,40,5);
                g.setColor(Color.green);
                g.fillRect(5,340,baseHp/(500/40),5);
            }
    }
    public void damage(Graphics g) { //damage ability method
        ArrayList<Towers>towersListt=getTowersList(); //gets the towerslist (updates each time)
        if(damageList.size()==0) { //if there are no damage abilities working at this time
            if(damageSelect.contains(destx,desty) && myGold>=100 && destx!=olderX && desty!=olderY) { //if the user clicks on the icon, has enough gold, and has clicked somewhere else
                olderX=destx; //keeps track of previous mouse click so that the ability is not called over and over if the player has not clicked anywhere else
                olderY=desty;
                damageList.add(new Damage()); //new ability is added
                myGold-=100; //subbtracts the cost
                if(towersList.size()!=0) { //if there are active towers
                towersSize=towersListt.size();
                    for(int i=0; i<towersListt.size(); i++){ //loops through every existing tower
                        Towers w=towersListt.get(i);
                        w.increaseDamage(); //calls a method that increases their damage
                    }
                }
            }
        }
        else { //if the ability is already called
            Damage x=damageList.get(0);
            if(x.getRemove()==true) { //if the ability has expired
                if(towersListt.size()!=0) {
                    for(int i=0; i<towersSize; i++){ //loops through the towers
                        Towers w=towersListt.get(i);
                        w.decreaseDamage(); //damage is decreased and back to normal
                    }
                }
                towersSize=0;
                damageList.remove(x); //ability is removed from list
            }
            else { //if the ability is not over yet
                x.draw(g); //damage animation is drawn
            }
        }
    }

    public void meteor(Graphics g) { //meteor ability method
        if(fireballList.size()==0) { //if no meteor is active
            if(meteorSelect.contains(destx,desty) && myGold>=35) { //if the icon is clicked
                olddestx=destx; //keeps trach of previous click
                olddesty=desty;
            }
            if(olddestx==destx && olddesty==desty) { //if the user has not clicked anywhere
                int mouseX=(int)MouseInfo.getPointerInfo().getLocation().getX(); //gets mouse x and y pos (not when clicked)
                int mouseY=(int)MouseInfo.getPointerInfo().getLocation().getY();
                g.drawImage(redellipse,mouseX-55,mouseY-60,null); //draws a red ellipse where the mouse is moved to show the enemy where the fireball will hit
            }
            if(olddestx!=0 && olddesty!=0) {
                if(olddestx!=destx && olddesty!=desty) {//if the user clicked, the target location is set
                    myGold-=35; //subtracts the cost
                    fireballList.add(new Fireball(destx,desty)); //new fireball is added
                }
            }
        }
        else { //if a fireball already exists
            Fireball x=fireballList.get(0);
            if(x.getRemove()==true) { //if the ability is over
                    fireballList.remove(x); //removes from list
                    olddestx=0;
                    olddesty=0;
            }
            else {
                 x.draw(g); //draws the fireball
            }
        }
    }

    public void table(Graphics g) { //draws the information table
        if(ups.contains(destx,desty) || screenRect.contains(desty,desty) ) { //if the up arrow is clicked
            if(down.contains(destx,desty)==false) { //if the down button is not clicked (user dosent want the table to dissapear
                g.drawImage(table,0,672,null); //draws the table
                down.setBounds(430,672,25,25); //sets the rectangles
                tables.setBounds(0,672,900,90);
                screenRect.setBounds(0,0,900,800);
                ifOpen=true;
                updateTable(g); //method that finds and draws all the information is called
            }
        }
        if(down.contains(destx,desty)==true) { // if user wants to exit
            down.setBounds(0,0,0,0); //rectangles are set to not exist
            tables.setBounds(0,0,0,0);
            screenRect.setBounds(0,0,0,0);
            ifOpen=false;
        }
    }

    public void upgrade(Graphics g) { //upgrade method
        fontSys = new Font("Comic Sans MS",Font.PLAIN,19);
        g.setFont(fontSys);
        g.setColor(Color.black);
        for(int i=0; i<towersList.size();i++){ //loops through all the towers
            Towers x=towersList.get(i); //i takes the value of every tower
                if(x.getRectangle().contains(destx,desty) || upgradelvl.contains(destx,desty)) { //if the user clickes on the tower or the upgrade button
                    if(x.getLevel()<=3 && x.getType().equals("Archer")) { //if tower is archer, less or equal to level 3
                        if(x.getRectangle().contains(destx,desty)) {
                            tracker2=x;
                            g.drawImage(archerUpgrade,x.getX()+32,x.getY()-100,null); //draws the appropriate upgrade sign
                            String finalString=Integer.toString(x.getUpgradeCost()); //draws the appropriate cost on the sign
                            g.drawString(finalString,x.getX()+32+40,x.getY()-100+36);
                            upgradelvl.setBounds(x.getX()+32,x.getY()-100,90,59); //sets the upgrade rectangle
                            x.grayscale(g); //method that draws the range of the tower
                        }
                        update2(x); //method called that upgrades the tower
                    }
                    if(x.getLevel()<=2 && x.getType().equals("Magic")) {
                        if(x.getRectangle().contains(destx,desty)) {
                            tracker2=x;
                            g.drawImage(magicUpgrade,x.getX()+32,x.getY()-70,null);
                            String finalString=Integer.toString(x.getUpgradeCost());
                            g.drawString(finalString,x.getX()+32+39,x.getY()-100+66);
                            upgradelvl.setBounds(x.getX()+32,x.getY()-70,90,59);
                            x.grayscale(g);
                        }
                        update2(x);
                    }
                    if(x.getLevel()<=2 && x.getType().equals("Freeze")) {
                        if(x.getRectangle().contains(destx,desty)) {
                            tracker2=x;
                            g.drawImage(freezeUpgrade,x.getX()-10,x.getY()-70,null);
                            String finalString=Integer.toString(x.getUpgradeCost());
                            g.drawString(finalString,x.getX()+30,x.getY()-100+66);
                            upgradelvl.setBounds(x.getX()-10,x.getY()-70,90,59);
                            x.grayscale(g);
                        }
                        update2(x);
                    }
                    if(x.getLevel()<=2 && x.getType().equals("Fire")) {
                        if(x.getRectangle().contains(destx,desty)) {
                            tracker2=x;
                            g.drawImage(fireUpgrade,x.getX()+95,x.getY()-70,null);
                            String finalString=Integer.toString(x.getUpgradeCost());
                            g.drawString(finalString,x.getX()+32+103,x.getY()-100+67);
                            upgradelvl.setBounds(x.getX()+95,x.getY()-70,90,59);
                            x.grayscale(g);
                            System.out.println("firre");
                        }
                        update2(x);
                    }
                }
        }
    }
    public void updateTable(Graphics g) { //method that draws all the tower information
        if(ifOpen==true) {
            for(int i=0; i<towersList.size();i++){ //loops through all the towers
                Towers x=towersList.get(i);
                if(x.getRectangle().contains(destx,desty)) {
                     g.setColor(Color.black);
                     fontSys = new Font("Comic Sans MS",Font.PLAIN,15);
                     g.setFont(fontSys);
                     String baseDammage=Integer.toString(x.getDammage()); //calls all the get methods to find all the information
                     String radius=Integer.toString(x.getRadius());
                     String effective=x.getEffective();
                     String weakness=x.getWeakness();
                     String kills=Integer.toString(x.getKills());
                     String levelOn=Integer.toString(x.getLevel());
                     String finalString1="Base Damage: "+baseDammage; //stores all the information into strings
                     g.drawString(finalString1, 390, 718); //draws the string in an organized manner
                     String finalString2="Damage Radius: "+radius;
                     g.drawString(finalString2, 390, 738);
                     String finalString3="Effective Against: "+effective;
                     g.drawString(finalString3, 545, 718);
                     String finalString4="Weak Against: "+weakness;
                     g.drawString(finalString4, 545, 738);
                     String finalString5="Enemies Killed: "+kills;
                     g.drawString(finalString5, 735, 718);
                     String finalString6="Level: "+levelOn;
                     g.drawString(finalString6, 735, 738);
                     if(x.getType().equals("Archer")) { //for every tower, and image is drawn of their upgrade progressions
                         g.drawImage(archerprogression,5,672,null);
                     }
                     if(x.getType().equals("Magic")) {
                         g.drawImage(magicprogression,5,672,null);
                     }
                     if(x.getType().equals("Freeze")) {
                         g.drawImage(iceprogression,5,672,null);
                     }
                     if(x.getType().equals("Fire")) {
                         g.drawImage(fireprogression,5,672,null);
                     }
                }
            }
        }
    }

    public void update2(Towers x) { //method that upgrades the tower
        if(upgradelvl.contains(destx,desty) && myGold>=x.getUpgradeCost()) { //if the user clicked on the upgrade button and has enough gold
           myGold-=x.getUpgradeCost(); //gold is subtracted
           tracker2.setLevel(); //sets the level of the tower
           tracker2.upgrade(); //upgrades the tower
           upgradelvl.setBounds(0,0,0,0); //sets the upgrade rectangle to non existant
        }
    }

    public void loadTowers(int x, int y, String type) { //adds towers to the towersList and subtracts the cost
        towersList.add(new Towers(type,x,y));
        myGold-=100;
    }
    public void setSelect() { //sets all the tower selection rectangles to non-existant
        select1.setBounds(0,0,0,0);
        select2.setBounds(0,0,0,0);
        select3.setBounds(0,0,0,0);
        select4.setBounds(0,0,0,0);
    }

    class clickListener implements MouseListener { //mouse class
        //track events of the mouse
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
        //only necessary event is when the mouse is pressed
        public void mousePressed(MouseEvent e){
           destx = e.getX(); //takes the value of the x-coordinate where the mouse was clicked
           desty = e.getY(); //takes the value of the y-coordinate where the mouse was clicked
           System.out.println(destx+" "+desty);
        }
    }

    public ArrayList<Towers> getTowersList(){ //gets the towersList
        return towersList;
    }

    public int getXCord(){ //get mouse x coord clicked
        return destx;
    }
    public int getYCord(){ //get mouse y coord clicked
        return desty;
    }
    public int getBaseHp(){ //return base hp
        return baseHp;
    }
    public void setBaseHp(int hp){ //sets base hp
        baseHp=hp;
    }
    public void setStart(boolean s){ //set if the base starts
        start=s;
    }
    public void setRectList(){ //recreates the rectangle list, after a new level is in place
  	rectangleList=new ArrayList<Rectangle>();
    }

    public int getGold(){//gets gold
    	return myGold;
    }
}