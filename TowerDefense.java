/*Tower Defense by Aser Herman and Sajan Flora
this is a tower defense game where you have a base at the end of a path and badguys that follow that path, trying to attack it.
You can build towers in the specified spots indicated by a patch of dirt on the ground. The towers are magic tower, archer tower,
fire tower and a freeze tower. each type of tower is weak against a type of troop and effective against a type of troop. There are
diffrent types of badguys aswell, there are orcs, goblins, trolls, and witchs. The orcs are slow with average damage and average
health, the trolls are slow with high damage and high health, the goblins are fast with low damage and low health and the witchs
have an ability where they can heal the badguys around them to full health, they are slow with average damage and average health.
There are two powerups that you can use, the meteor, and the damage increase. With the meteor ability, a meteor falls from the sky
and deals high amounts of damage to the badguys around it. The damage increase increases the damage of the towers, with the weaker
towers getting more of a boost from the powerup. When playing you have two game modes, rounds and infinite waves. In rounds, you
play 4 rounds where the badGuys get incresingly stronger as the rounds go on. In the infinite wave game mode, you play for as long
as you can with the number of badguys increasing as you kill them.
*/
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
import javax.sound.midi.*;

public class TowerDefense extends JFrame {
    private static ArrayList<BadGuy>badList=new ArrayList<BadGuy>();
    private static ArrayList<String>randList=new ArrayList<String>();
    static Base base;
    BadGuy badGuy;
    static Maps maps;
    static Menu menu;
    int menuPg;
    static BadGuys badGuys;
    Timer myTimer;
    static int round=4;//which round
    static boolean load=true;//used to stop adding badguy objects to the badList
    private static boolean infWave=false;//if infinite wave is being played
    private static Sequencer midiPlayer;

    public TowerDefense() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,800);
        startMidi("music.mid");
        myTimer = new Timer(10, new TickListener());
        setLayout(new BorderLayout());
        base= new Base();
        badGuys= new BadGuys();
        maps=new Maps(1);
        menu=new Menu();
        add(base);
        setVisible(true);
        myTimer.start();
    }

    class TickListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(base!= null && base.ready){
                base.repaint();
            }
        }
    }
    public static int randint(int low, int high){//randint used for when randomly slecting badguys when in infinite waves mode
        int range=high-low+1;
        return (int)(Math.random()*range)+low;
  }


    public static void loadBadGuys(){//loads the badguy objects into arraylists
    	if(round==0  || badGuys.getNumBadGuys()==0){//begining of the game
        	base.setRectList();//setting the avalible spots to build towers
        	base.setMyGold(-base.getGold());//setting gold to 0(fixes problem with gold being added)
        	base.setMyGold(600);//setting gold to 600
    	}
        if(round!=-1 && load==true){//-1 means infinite waves game mode, infinite wave dosent have rounds
            round++;//increasing round whenever previous round was done
        }
        if(round<5 && load==true){//there are 4 rounds
            try{
                BufferedReader inFile = new BufferedReader(new FileReader("badGuyStats"+maps.getMap()+".txt"));//reading the file of badguys
                String stats=inFile.readLine();
                 while(true){//loops until it finds the correct round
                     if(round==-1){//if infinite wave
                         if(randList.size()==0){//if the list of badguys that are randomly generated isnt filled yet
                             if(stats.equals("round -1")){
                                 infWave=true;
                                 for(int i=0; i<24; i++){//24 possible badguys
                                     stats=inFile.readLine();
                                     randList.add(stats);//adding to a list to be randomly selected
                                 }
                             }
                         }
                         if(infWave==true){
                             for(int i=0; i<10; i++){//add 10 badguys to the list at a time
                                 badList.add(new BadGuy(randList.get(randint(0,23))));//randomly choosing a badguy
                             }
                             badGuys.setBadList(badList);//setting badlist
                             break;
                         }
                     }
                    else if(stats.equals("round "+round)){//when u find the round
                        stats=inFile.readLine();
                        while(!"".equals(stats)){//goes until a blank line
                            badList.add(new BadGuy(stats));//adding a badguy object to the list
                            stats=inFile.readLine();//next line
                        }
                        badGuys.setBadList(badList);
                        break;
                    }
                    stats=inFile.readLine();//next line
                }
            }
            catch(IOException ex){
              System.out.println(ex);
            }
        }
    }

    public static void main(String[] arguments) {
        TowerDefense frame = new TowerDefense();
    }
    public static void startMidi(String midFilename) {
      try {
         File midiFile = new File(midFilename);
         Sequence song = MidiSystem.getSequence(midiFile);
         midiPlayer = MidiSystem.getSequencer();
         midiPlayer.open();
         midiPlayer.setSequence(song);
         midiPlayer.setLoopCount(-1); // repeat 0 times (play once)
         midiPlayer.start();
      } catch (MidiUnavailableException e) {
         e.printStackTrace();
      } catch (InvalidMidiDataException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//getter and setter methods
    public static ArrayList<BadGuy> getBadGuysList(){
        return badList;
    }
    public static Base getBase(){
        return base;
    }
    public static BadGuys getBadGuys(){
        return badGuys;
    }
    public static Maps getMaps(){
        return maps;
    }
    public static Menu getMenu(){
        return menu;
    }
    public static void setMenu(Menu m){
        menu=m;
    }
    public static void setMap(Maps m){
        maps=m;
    }
    public static int getRound(){
        return round;
    }
    public static void setRound(int n){
        round=n;
    }
    public static void setLoad(boolean l){
    	load=l;
    }
    public static void reset(){//used when your done playing, to reset everything so you can play again
  		badList=new ArrayList<BadGuy>();
  		randList=new ArrayList<String>();
  		round=0;
  	}
}

