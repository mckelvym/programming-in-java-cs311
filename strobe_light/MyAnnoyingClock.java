// mark mckelvy
// strobe
import java.awt.*;
import java.applet.*;

public class MyAnnoyingClock extends Applet implements Runnable{
	Color altColor = Color.green;
	int Delay_ms = 50;
	Thread myThread;
	Color c;
	boolean white = true;
	
	public void init(){
		//initialize thread
		myThread = new Thread(this);
		
		if(myThread != null){
			myThread.start();// calling thread's start method
		}
	}
	public void run(){
            while(true){
                repaint();
                try{
                    Thread.sleep(Delay_ms);
                }
                catch(InterruptedException e){
                    System.out.println("Oops!");
                }
            }	
        }
	public void stop(){
            if(myThread.isAlive()){
                myThread = null;
            }
	}
	public void paint(Graphics g){
            if(white){
                c = Color.black;
                white = false;
            }
            else{
                c = altColor;
                white = true;
            }
            g.setColor(c); 
            g.fillRect(0,0,1050,750);
	}
}