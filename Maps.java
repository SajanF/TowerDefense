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
class Maps{
    int map;
    Image grass, spot;
    ArrayList<Rectangle>rectangleList;
    private ArrayList<Rectangle>pathList=new ArrayList<Rectangle>();
    private Rectangle open1,open2,open3,open4,open5,open6,open7,open8,open9;//rects for the spots where u can build towers
    //a path for badguys to follow for each map
    int[][]pathList1={{69,661},{67,92},{244,91},{244,470},{414,470},{417,88},{591,88},{591,468},{817,464},{817,-50}};
    int[][]pathList2={{90,105},{340,105},{340,660},{820,660},{820,450},{540,450},{540,255},{810,255},{810,55},{460,55}};
    int[][]pathList3={{720,645},{525,645}, {860,250},{815,210},{630,210},{570,310},{570,440}, {470,450},{315,380},{255,335},{80,335},{20,150},{0,100}};
    int[][]pathList4={{820,620},{710,590},{665,350},{730,320},{805,290},{805,125},{665,70},{610,20},{540,70},{355,70},{290,300},{180,330},{150,365},{125,550},{-10,570}};
    int[][]pathList5={{350,620},{515,635},{460,560},{460,445},{320,390},{290,200},{385,125},{910,125}};
    int[][]pathList6={{0,238},{112,238},{186,295},{402,293},{437,255},{460,80},{530,40},{600,220},{750,320},{765,480},{730,535},{550,535},{510,590},{80,590},{50,450},{-10,380}};
    public Maps(int m){
        map=m;
        spot = new ImageIcon("open.png").getImage();
    }
    public ArrayList<Rectangle> setRectList(){//getting the arraylist of spots for building towers
    	rectangleList=new ArrayList<Rectangle>();
    	if(map==1){
    		setRectList1();//getting spots for map1
    	}
    	else if(map==2){
    		setRectList2();//getting spots for map2
    	}
    	else if(map==3){
    		setRectList3();//getting spots for map3
    	}
    	else if(map==4){
    		setRectList4();//getting spots for map4
    	}
    	else if(map==5){
    		setRectList5();//getting spots for map5
    	}
    	else if(map==6){
    		setRectList6();//getting spots for map6
    	}
    	return rectangleList;
    }
    public void setRectList1(){//setting rectangelList for map1
    	open1=new Rectangle(145,200,60,30);
        rectangleList.add(open1);
        open2=new Rectangle(315,200,60,30);
        rectangleList.add(open2);
        open3=new Rectangle(315,400,60,30);
        rectangleList.add(open3);
        open4=new Rectangle(495,400,60,30);
        rectangleList.add(open4);
        open5=new Rectangle(315,600,60,30);
        rectangleList.add(open5);
        open6=new Rectangle(515,600,60,30);
        rectangleList.add(open6);
       	open7=new Rectangle(700,300,60,30);
        rectangleList.add(open7);
        open8=new Rectangle(495,200,60,30);
        rectangleList.add(open8);
    }
    public void setRectList2(){//setting rectangelList for map2
    	open1=new Rectangle(150,500,60,30);
        rectangleList.add(open1);
       	open2=new Rectangle(260,200,60,30);
        rectangleList.add(open2);
        open3=new Rectangle(425,500,60,30);
        rectangleList.add(open3);
        open4=new Rectangle(605,400,60,30);
        rectangleList.add(open4);
        open5=new Rectangle(700,600,60,30);
        rectangleList.add(open5);
        open6=new Rectangle(700,200,60,30);
        rectangleList.add(open6);
        open7=new Rectangle(425,200,60,30);
        rectangleList.add(open7);
        open8=new Rectangle(260,650,60,30);
        rectangleList.add(open8);
    }
    public void setRectList3(){//setting rectangelList for map3
    	open1=new Rectangle(565,555,60,30);
        rectangleList.add(open1);
        open2=new Rectangle(745,320,60,30);
        rectangleList.add(open2);
        open3=new Rectangle(355,525,60,30);
        rectangleList.add(open3);
        open4=new Rectangle(115,235,60,30);
        rectangleList.add(open4);
        open5=new Rectangle(480,375,60,30);
        rectangleList.add(open5);
        open6=new Rectangle(165,435,60,30);
        rectangleList.add(open6);
    }
    public void setRectList4(){//setting rectangelList for map4
    	open1=new Rectangle(705,700,60,30);
        rectangleList.add(open1);
        open2=new Rectangle(740,460,60,30);
        rectangleList.add(open2);
        open3=new Rectangle(545,420,60,30);
        rectangleList.add(open3);
        open4=new Rectangle(700,210,60,30);
        rectangleList.add(open4);
        open5=new Rectangle(380,200,60,30);
        rectangleList.add(open5);
        open6=new Rectangle(220,450,60,30);
        rectangleList.add(open6);
        open7=new Rectangle(30,480,60,30);
        rectangleList.add(open7);
    }
    public void setRectList5(){//setting rectangelList for map5
    	open1=new Rectangle(224,706,60,30);
        rectangleList.add(open1);
        open2=new Rectangle(322,510,60,30);
        rectangleList.add(open2);
        open3=new Rectangle(528,526,60,30);
        rectangleList.add(open3);
       	open4=new Rectangle(727,526,60,30);
        rectangleList.add(open4);
        open5=new Rectangle(379,283,60,30);
        rectangleList.add(open5);
        open6=new Rectangle(569,283,60,30);
        rectangleList.add(open6);
        open7=new Rectangle(755,283,60,30);
        rectangleList.add(open7);
    }
    public void setRectList6(){//setting rectangelList for map6
    	open1=new Rectangle(43,144,60,30);
        rectangleList.add(open1);
        open2=new Rectangle(122,511,60,30);
        rectangleList.add(open2);
        open3=new Rectangle(326,200,60,30);
        rectangleList.add(open3);
        open4=new Rectangle(288,719,60,30);
        rectangleList.add(open4);
        open5=new Rectangle(513,176,60,30);
        rectangleList.add(open5);
        open6=new Rectangle(678,200,60,30);
        rectangleList.add(open6);
        open7=new Rectangle(439,431,60,30);
       	rectangleList.add(open7);
        open8=new Rectangle(655,434,60,30);
        rectangleList.add(open8);
        open9=new Rectangle(562,661,60,30);
        rectangleList.add(open9);
    }

    public void setMap(int m){//set the map
        map=m;
    }

    public int[][] getPath(){//used for getting the path that the badguys follow
        if (map==1){
            return pathList1;
        }
        if(map==2){
        	return pathList2;
        }
        if(map==3){
        	return pathList3;
        }
        if(map==4){
        	return pathList4;
        }
        if(map==5){
        	return pathList5;
        }
        if(map==6) {
            return pathList6;
        }
        return null;
    }

    public int getMap(){//getting the map
        return map;
    }

    public void draw(Graphics g){
        if(map==1){
            grass = new ImageIcon("grass"+map+".jpeg").getImage();
            g.drawImage(grass,0,0,null);//background
            //path
            g.setColor(Color.gray);
            g.fillRect(50,650,850,75);
            g.fillRect(50,75,75,600);
            g.fillRect(50,75,175,75);
            g.fillRect(225,75,75,450);
            g.fillRect(275,450,175,75);
            g.fillRect(400,75,75,450);
            g.fillRect(450,75,175,75);
            g.fillRect(575,75,75,450);
            g.fillRect(625,450,175,75);
            g.fillRect(800,0,75,525);
			//spots where u build towers
            g.drawImage(spot,145,200,null);
            g.drawImage(spot,315,200,null);
            g.drawImage(spot,315,400,null);
            g.drawImage(spot,495,400,null);
            g.drawImage(spot,315,600,null);
            g.drawImage(spot,515,600,null);
            g.drawImage(spot,700,300,null);
            g.drawImage(spot,495,200,null);

        }
        if(map==2){
            grass = new ImageIcon("grass"+map+".jpg").getImage();
            g.drawImage(grass,0,0,null);//background
            g.fillRect(75,100,75,750);
            g.fillRect(75,100,325,75);
            g.fillRect(325,100,75,625);
            g.fillRect(325,650,550,75);
            g.fillRect(800,450,75,200);
            g.fillRect(525,450,350,75);
		    g.fillRect(525,250,75,200);
		    g.fillRect(525,250,350,75);
		    g.fillRect(800,50,75,200);
            g.fillRect(500,50,350,75);
			//spots where u build towers
            g.drawImage(spot,150,500,null);
            g.drawImage(spot,260,200,null);
            g.drawImage(spot,425,500,null);
            g.drawImage(spot,605,400,null);
            g.drawImage(spot,700,600,null);
            g.drawImage(spot,700,200,null);
            g.drawImage(spot,425,200,null);
            g.drawImage(spot,260,650,null);
        }
        if (map==3){
            grass = new ImageIcon("grass3.png").getImage();
            g.drawImage(grass,0,0,null);//map
            //spot where u build towers
            g.drawImage(spot,565,555,null);
            g.drawImage(spot,741,320,null);
            g.drawImage(spot,355,525,null);
            g.drawImage(spot,115,235,null);
            g.drawImage(spot,480,375,null);
            g.drawImage(spot,165,435,null);
        }
        if (map==4){
        	grass = new ImageIcon("grass4.png").getImage();
        	g.drawImage(grass,0,0,null);//map
        }
        if (map==5){
        	grass = new ImageIcon("grass5.png").getImage();
        	g.drawImage(grass,0,0,null);//map
        }
        if(map==6) {
            grass = new ImageIcon("grass6.png").getImage();
            g.drawImage(grass,0,0,null);//map
        }
    }
    public void reset(){//resetting the list of spots where u build towers
    	rectangleList=new ArrayList<Rectangle>();
    }
}