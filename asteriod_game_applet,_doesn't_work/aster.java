import java.util.*;
import java.lang.*;
import java.awt.*;
import java.applet.*;
import java.net.*;

/* This is all original material, with the exception of the double buffering, which
* I hacked out of a quote-ticker program.  The game is a GREAT example of object-heirarchy.
* I also supported multithreading, but it just made things run slower, actually!
* For this reason the multithreading was removed...

* PARAMETERS:
*	wid: the width of the applet (integer)
*	hei: the height of the applet (integer)

* Feel free to modify or distribute as you wish, as long as you include:

Original programmed by: Ben Sigelman, sigelman@javanet.com
http://www.javanet.com/~sigelman/   |||  FOR HIRE! ALWAYS!

* Appletviewer has its bugs.... netscape _is_ a bug.
*/

public class aster extends java.applet.Applet implements Runnable { //this is the main program, aster(noid)
    public static int score; //self-explanatory... its the score accumulated
    ShotHandler shotH; //this is an implementation of my "shothandler" class, which keeps track of all your shots.
    PlayerHandler playH; //this is an implementation of my "playerhandler" class, which keeps track of your players needs... leaves open support for multi-player
    AstHandler astH; //same deal:  handling class for all of the "asteroids" in the game. (the things you shoot, that is)
    Color col;  //i like to define things like this in case their needed... this one is used to change colors
    Image im; //this is used in the double-buffering lines.
    Graphics offscreen; //more double-buffering crap
    int keyp, i1, i2, i3, i4; //keyp was used for debugging.  the rest are just temp. integers
    int level = 0; //this is the current level
    int levelend = 0; //this is used in the pause at the end of every level
    int levelstart = 0; //this is used in the invulnerability period that starts each level.
    double d1, d2, d3, d4; //temp double things
    int keys[] = new int[3]; //VERY IMPORTANT! this has to do with overriding the default keyboard handling
    AudioClip /*shotsound, */explode; //the explosion sound in the game... shotsound was too obnoxious to include
    URL codeb; //code base URL.  this is the documents url... a kind of base directory
    int width, height; //width and height as specified by the html file
    boolean apprun = false; //primal boolean which says "is this applet running, or not"
    Font bigfont = new Font("Arial", Font.BOLD, 24); //big font
    Font littlefont = new Font("Arial", Font.PLAIN, 12); //smaller font
    Date currtime = new Date(); //used for the time sequencing - better than netscape's built-in
    long now, then, diff; //used for the time crap

    public boolean mouseDown(Event ev, int x, int y) { //if mouse is pressed
        int li1; //temp integer
        boolean b1 = apprun; //temp boolean for storage of apprun
        if (b1 == true) {
            stop();
        } //stops execution of program if apprun is true
        if (b1 == false) {
            start();
        } //starts execution of program if apprun is false
        if (playH.players.active == false) { //if the player is "no more"
            level = 1; //change level to 1
            score = 0; //obvious...
            keys[0] = -1; //unpresses key #1
            keys[1] = -1; //unpresses key #2
            keys[2] = -1; //unpresses key #3
            playH.players.active = true; //he's alive!
            playH.players.angdriftx = 0; //not moving
            playH.players.angdrifty = 0; //not moving
            playH.ss = 100; //full shield
            levelstart = 0; //restart invulnerability counter
            for (li1 = 0; li1 < 40; li1++) { //cycle asteroids
                astH.asts[li1].state = -1; //get rid of asteroids
            }
            astH.AstCreate(level, width, height); //makes the first few asteroids...
            repaint();
        }
        return true; //required by java
    }

    public boolean keyDown(Event ev, int key) { //keyDown event automatically calls this function.
        int li1; //temp integer
        if (key == 32) { //space bar: shoot
            if (shotH.shotdown == 1) {
                shotH.shotdown = 2;
            } //these lines dont allow you to hold down
            if ((playH.players.active == true) & (shotH.shotdown == 0)) {
                shotH.shotdown = 1;
            } //if player is alive, space to shoot.  no shots fired at "2" or "0"
        }
        keyp = key; //for debugging of key codes only... deleteable
        for (li1 = 0; li1 < 3; li1++) { //cycles through all three key variables
            if ((keys[li1] == -1) && ((keys[2] != key) && (keys[0] != key) && (keys[1] != key))) {
                keys[li1] = key;
            } //assigns the key to any free spaces... no duplicate key refs. "-1" = free
        }
        return true; //required by java
    }

    public boolean keyUp(Event ev, int key) { //keyUp event automatically calls this function
        int li1; //temp integer
        for (li1 = 0; li1 < 3; li1++) { //cycle through keys
            if (keys[li1] == key) {
                keys[li1] = -1;
            } //"unpresses" the key from the games perspective
        }
        if (key == 122) {
            playH.players.shield = false;
        } //turns off the shield immediatly after key is released
        shotH.shotdown = 0; //lets shots be fired on next keydown
        return true; //required by java
    }

    public void checkKeys(int key) { //checks for the given key (in the array)
        playH.keyDown(key); //tells the playerhandler that the key has been pressed.
        shotH.keyDown(key, playH.players.ang, playH.players.xco, playH.players.yco); //tells the shothandler the given key has been pressed
    }

    public void init() {
        level = 1; //change level to 1
        score = 0; //obvious...
        keys[0] = -1; //unpresses key #1
        keys[1] = -1; //unpresses key #2
        keys[2] = -1; //unpresses key #3
        width = 640; //default width
        height = 480; //default height
        width = Integer.valueOf(getParameter("wid")).intValue(); //gets the width of the applet
        height = Integer.valueOf(getParameter("hei")).intValue(); //gets the height of the applet
        shotH = new ShotHandler(); //starts up shotH
        playH = new PlayerHandler(width, height); //starts up playerH
        playH.players.active = true;
        astH = new AstHandler(); //starts up astH
        astH.AstCreate(level, width, height); //makes the first few asteroids... #asteroids relates to #level
        /*these next lines regard double buffer initialization.*/
        try {
            im = createImage(width, height); //applet size is width*height
            offscreen = im.getGraphics();
        } catch (Exception e) {
            offscreen = null;
        }
        /*end of double buffering*/
        codeb = getCodeBase(); //gets the code base, described in variable definition of "codeb"
        explode = getAudioClip(codeb, "explode.au"); //gets the explosion audio clip in the applet's base directory
        (new Thread(this)).start(); //initializes main thread
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY); //its necessary... try changing it sometime!
        now = currtime.getTime(); //get the time
        then = currtime.getTime(); //get the time
    }

    public void start() {
        apprun = true;
    }

    public void stop() {
        apprun = false;
    }

    public void run() {
        apprun = false; //applet must start paused!
        while (true) { //always runs until stopped
//			currtime = new Date(); //set the current time
//			now = currtime.getTime(); //get the current time (in milliseconds)
//			diff = now-then; //difference in time since last update
            if ((apprun)/*&(diff>40)*/) { //if app is active and enough time has gone by
//				then = now; //restart clock
                levelstart++; //50 ticks of invulnerability to begin each level
                if (levelstart > 75) {
                    levelstart = 51;
                } //doesn't let the number get out of hand.
                checkKeys(keys[0]); //checks key#1
                checkKeys(keys[1]); //checks key#2
                checkKeys(keys[2]); //checks key#3
                try {
                    astH.move(); //tells astH to move all its asteroids
                    i2 = astH.check(shotH, level, score, explode, width, height); //tells it to check its asteroids... needs all shot locations, the level of difficulty, needs soundfx, RETURNS score or a "level-over indicator"
                    shotH.move(); //tells shotH to move all the shots
                    shotH.check(width, height); //tells shotH to check the shots' movement
                    playH.move(); //moves player(s)
                    playH.check(astH, levelstart, width, height); //checks the player... need invulnerability info, and location of all asteroids.
                } catch (NullPointerException e) {
                }
                repaint(); //repaints the screen
                if (i2 == -1) {
                    levelend++;
                } //i2 is -1 when all asteroids are destroyed - it will increase levelend ticker
                if (i2 != -1) {
                    score = i2;
                } //i2 is usually the score (returned by astH.check).  updates the score
                if (levelend > 50) {
                    nextlevel();
                } //if enough ticks have gone by, next level is initialized
                try {
                    Thread.currentThread().sleep(40);
                } catch (InterruptedException e) {
                } //adds some time to keep the speed normal
            }
            Thread.currentThread().yield(); //REALLY REALLY REALLY REALLY IMPORTANT! THIS WILL DRAMATICALLY ENHANCE NETSACAPE PERFORMANCE!
        }
    }

    public void nextlevel() {
        score = score + (100 * level); //"finish level" bonus
        levelend = 0; //resets levelend ticker
        playH.ss = playH.ss + (level / 2) * 10; //gives a shield bonus for the player
        if (playH.ss > 100) {
            playH.ss = 100;
        } //caps shield at 100
        level++; //next level (finally)
        keys[0] = -1; //resets key#1
        keys[1] = -1; //resets key#2
        keys[2] = -1; //resets key#3
        astH = new AstHandler(); //makes a new set of asts
        astH.AstCreate(level, width, height); //creates more asteroids in relation to the level
        levelstart = 0; //allows 50 more ticks of invulnerability at start of next level
    }

    public void update(Graphics g) {
        paint(g); //overrides update
    }

    public void paint(Graphics g) {
        if (offscreen != null) { //more double-buffering crap
            paintApplet(offscreen); //the REAL paint method
            g.drawImage(im, 0, 0, this);
        } else {
            paintApplet(g); //the REAL paint method
        }
    }

    public void paintApplet(Graphics g) {
        g.setFont(littlefont); //sets normal font
        g.setColor(col.black); //prepares screen wash
        g.fillRect(0, 0, width, height); //makes big, black rectangle washover
        playH.paint(g, width, height); //paints the player
        astH.paint(g); //paints the asteroids
        try {
            shotH.paint(g);
        } catch (NullPointerException e) {
        } //shotH.paint likes to crash applet, for some reason
        g.setColor(col.white); //you know this, hopefully
//		g.fillRect(0,70,(int)diff,10);
//		g.drawString("difference:  " + diff, 0, 90);
        this.showStatus("level: " + level); //puts the level number in the status bar
        g.drawString("SCORE:  " + score, 0, 30); //shows the current score
        if (playH.players.active == false) { //if player is dead...
            g.setFont(bigfont); //make a big, bold font
            g.drawString("Click to restart game", (int) (width / 3), (int) (height / 3)); //display restart instructions
        }
    }
}

class ShotHandler extends java.lang.Object/* implements Runnable*/ { //handles basic instructions and breaks them down to a shot-by-shot level
    Shot shots[] = new Shot[17]; //makes all 17 shots (program only uses 16: 17 is to prevent buggy array access exceptions)
    Color col1; //temp color variable
    int shotdown = 0; //is there a shot being fired? 1 is true
    private int li1, li2; //temp integers

    public ShotHandler() { //constructor when a new instance is called for
        for (li1 = 0; li1 < 16; li1++) { //cycles through the shots
            shots[li1] = new Shot(); //new instance of shot for each segment of array
        }
    }

    public void keyDown(int key, double ang, double xcor, double ycor) { //handles what is passed in from the main applet's keydown event
        int freeshot = -1; //initializes as nonexistent
        if (key == 32) { //if the key is space bar,
            for (li1 = 0; li1 < 16; li1++) { //cycle shots
                if (shots[li1].active == false) {
                    freeshot = li1;
                } //if shot isn't being used, make temp = shot #
            }
            if ((shotdown == 1) && (freeshot != -1)) { //if this is the FIRST pressing of space (not held) and freeshot "found a home" then:
                shots[freeshot].shoot(16, ang, xcor, ycor); //makes a shot of velocity 16, the players current angle, player's xcord, and player's ycord
            }
            shotdown = 2; //space has been held down
        }
    }

    public void check(int wid, int hei) {
        for (li1 = 0; li1 < 16; li1++) { //cycle shots
            if (shots[li1].active) { //if shot exists
                shots[li1].check(wid, hei); //call shot's individual check method
                if (shots[li1].cycles > 16) { //if shot has been alive for 16 cycles
                    shots[li1].stop(); //kill the friggin' thing
                }
            }
        }
    }

    public void move() { //obvious...
        for (li1 = 0; li1 < 16; li1++) { //cycle shots
            if (shots[li1].active) { //if alive
                shots[li1].move(); //call upon individual move method
            }
        }
    }

    public void paint(Graphics g) { //the shotH paint method.
        li1 = 0; //init temp var... this solves a NullPointerException often encountered... not TOTALLY sure why
        for (li1 = 0; li1 < 16; li1++) { //cycle shots (again :-[)
            if (shots[li1].active) { //if alive
                shots[li1].paint(g); //call individual paint method
            }
        }
    }
}

class Shot extends java.lang.Object { //an individual shot method, owned by ShotH's "shots" array
    boolean active = false; //default: shot is non-existent
    Color col1; //temp color variable
    int cycles, rot;  //sequence of color
    double pxc, pyc, xco, yco; //prev xcord, prev ycord, curr xcord, curr ycord
    double ang, vel; //shot angle direction (in radians), velocity in units moved per program tick

    public Shot() { //the constructor
        active = false; //yeah, its redundant
    }

    public void shoot(double veloc, double angle, double xcor, double ycor) { //make an active shot with the given attributes
        xco = xcor; //transfer x coordinate
        yco = ycor; //transfer y coordinate
        vel = veloc; //transfer velocity
        ang = angle; //transfer angle
        active = true; //make shot active, or visible.
        cycles = 0; //after 16 cycles, the shot is destroyed.
        rot = 0; //color rotation variable
        pxc = xco; //initializes the previous xcord variable
        pyc = yco; //initializes the previous ycord variable
    }

    public void stop() { //kills the shot
        active = false; //take a guess...
    }

    public void paint(Graphics g) { //this is the shots paint method... draws a line from its last position to its current position, basically
        rot++; //advances color rotation
        if (rot == 16) {
            rot = 0;
        } //keeps rotation below 17
        col1 = new Color(255 - (rot * 8), 0, 127 + rot * 8); //makes the new color
        g.setColor(col1); //sets the current color to the rotation color
        g.drawLine((int) xco, (int) yco, (int) (xco - (xco - pxc)), (int) (yco - (yco - pyc))); //draws a line from the current coords to the previous coords
    }

    public void move() { //moves the shot
        cycles++; //advances the shot's "age" in cycles
        pxc = xco; //makes a new prev. xcord
        pyc = yco; //makes a new prev. ycord
        xco = pxc + (Math.cos(ang) * vel); //defines the new xcord as a trig function utilizing angle and velocity (hypothenuse)
        yco = pyc + (Math.sin(ang) * vel); //defines the new ycord as a trig function utilizing angle and velocity (hypothenuse)
    }

    public void check(int wi, int he) { //checks the shot's coords
        if (xco > wi + 10) { //if xcord is more than 10 off the right side
            xco = xco - (wi + 20); //move to the left side of screen
            pxc = xco; //disable the possibility of drawing a line across the screen
        }
        if (yco > he + 10) { //if ycord is more than 10 below the bottom
            yco = yco - (he + 20); //move to the top of screen
            pyc = yco; //disable the possibility of drawing a line across the screen
        }
        if (xco < -10) { //if xcord is more than 10 off the left side
            xco = xco + wi + 20; //move to the right side of screen
            pxc = xco; //disable the possibility of drawing a line across the screen
        }
        if (yco < -10) { //if ycord is more than 10 off the top
            yco = yco + he + 20; //move to the bottom of screen
            pyc = yco; //disable the possibility of drawing a line across the screen
        }
    }
}

class Player extends java.lang.Object { //a player object
    boolean active = true; //is player alive?
    boolean shield = false; //is shield on?
    int rot; //color rotation
    Color col2; //temp color variable
    double pxc, pyc, xco, yco; //prev xcord, prev ycord, curr xcord, curr ycord
    double ang, angdriftx, angdrifty, vel; //player angle direction, actual movement direction (x), actual movement direction (y), speed
    private int li1, li2; //some temp ints

    public Player() { //the default constructor
        active = true; //redundant? yes...
    }

    public void start(int wi, int he) { //the REAL constructor
        xco = (Math.random() * wi); //chooses a random x value
        yco = (Math.random() * he); //chooses a random y value
        pxc = xco; //inits the prev xcord
        pyc = yco; //inits the prev ycord
        vel = 0; //sets velocity to 0
        ang = .01; //makes angle a little past default: this eliminates trigonometry errors computing the angle (ie tan(0))
        angdriftx = 0; //no movement
        angdrifty = 0; //no movement
        rot = (int) (Math.random() * 15); //gets a random value for the color rotation
    }

    public void thrust(double amount) { //if the letter "k" is pressed, the ship must make movement adjustments
        double lld1, lld2, lld3, lld4; //temp doubles
        lld1 = angdriftx; //assigned to the x angle-drift
        lld2 = angdrifty; //assigned to the y angle-drift
        lld3 = (Math.cos(ang) * amount); //assigned to the amount of x-change there must be
        lld4 = (Math.sin(ang) * amount); //assigned to the amount of y-change there must be
        angdriftx = (lld1 + lld3); //just what you'd expect next
        angdrifty = (lld2 + lld4); //same thing...
        vel = Math.pow((angdriftx * angdriftx + angdrifty * angdrifty), .5); //distance formula modification.  pow(x, .5) takes the square root of x
        if (angdriftx > 8) { //if fast x movement
            angdrifty = 8 * (angdrifty / angdriftx); //keeps things in proportion
            angdriftx = 8; //speed limiter
        }
        if (angdrifty > 8) { //if fast y movement
            angdriftx = 8 * (angdriftx / angdrifty); //keeps things in proportion
            angdrifty = 8; //speed limiter
        }
        if (angdriftx < -8) { //if fast x movement
            angdrifty = -8 * (angdrifty / angdriftx); //keeps things in proportion
            angdriftx = -8; //speed limiter
        }
        if (angdrifty < -8) { //if fast y movement
            angdriftx = -8 * (angdriftx / angdrifty); //keeps things in proportion
            angdrifty = -8; //speed limiter
        }
    }

    public void rotate(double degree) { //change angle of ship
        ang = ang + degree; //simple math
    }

    public boolean alive() { //function to check if player is alive
        return active; //returns the active variable
    }

    public void paint(Graphics g) { //paints the player to the screen
        if (shield) { //if shield is on
            col2 = new Color(0, 64 + rot * 4, 0); //define a color based on the color rotater
            g.setColor(col2); //set the new color
            g.fillOval((int) (xco - 12 - (rot / 2)), (int) (yco - 12 - (rot / 2)), 24 + rot, 24 + rot); //make a quickly expanding circle
        }
        for (li1 = 0; li1 < 13; li1++) { //draw all thirteen "speed indicator" (?) lines
            rot++; //cycle through another color rotation
            if (rot == 15) {
                rot = 0;
            } //put cieling on color rotation
            col2 = new Color(0, 255 - (rot * 16), rot * 16); //define new color
            g.setColor(col2); //set new color
            g.drawLine((int) (xco - (Math.cos(ang - Math.PI / 8) * li1 * vel / 2)), (int) (yco - (Math.sin(ang - Math.PI / 8) * li1 * vel / 2)), (int) (xco - (Math.cos(ang + Math.PI / 8) * li1 * vel / 2)), (int) (yco - (Math.sin(ang + Math.PI / 8) * li1 * vel / 2))); //draw a line from a distance&-angle to distance&+angle behind the ship
        }
        col2 = new Color(0, 172, 0); //make another new color
        g.setColor(col2); //set new color
        g.drawLine((int) (xco - (Math.cos(ang) * 14)), (int) (yco - (Math.sin(ang) * 14)), (int) (xco + (Math.cos(ang) * 5)), (int) (yco + (Math.sin(ang) * 5))); //make another trig line going behind player
        g.drawLine((int) (xco - (Math.cos(ang - Math.PI / 6) * 14)), (int) (yco - (Math.sin(ang - Math.PI / 6) * 14)), (int) xco, (int) yco); //make another trig line going diagonally behind player
        g.drawLine((int) (xco - (Math.cos(ang + Math.PI / 6) * 14)), (int) (yco - (Math.sin(ang + Math.PI / 6) * 14)), (int) xco, (int) yco); //make another trig line going diagonally behind player
    }

    public void move() { //moves the player
        pxc = xco; //redefines prev xcord
        pyc = yco; //redefines prev ycord
        xco = pxc + angdriftx; //adds the drift to the current xcord
        yco = pyc + angdrifty; //adds the drift to the current ycord
    }

    public void check(AstHandler ah, int starting, int wi, int he) { //checks for hits, position (using the list of asteroids)
        int li1;
        if (xco > wi + 10) { //if player is off right side of screen
            xco = xco - (wi + 20); //put player on left side of screen
        }
        if (yco > he + 10) { //if player is off bottom of screen
            yco = yco - (he + 20); //put player on top of screen
        }
        if (xco < -10) { //if player is off left side of screen
            xco = xco + wi + 20; //put player on right side of screen
        }
        if (yco < -10) { //if player is off top of screen
            yco = yco + he + 20; //put player on bottom of screen
        }
        if ((shield == false) && (starting > 50)) { //if the shield ain't on...
            for (li1 = 0; li1 < 40; li1++) { //cycle through all possible asteroids
                if ((ah.asts[li1].state == 1) && (Math.abs(ah.asts[li1].xco - xco) < ah.asts[li1].size) && (Math.abs(ah.asts[li1].yco - yco) < ah.asts[li1].size)) { //if the asteroid exists and is touching the player, then:
                    active = false; //player is DEAD!
                }
            }
        }
    }

    public void stop() { //if program is stopped
        active = false; //player is killed... oh, the humanity...
    }
}

class PlayerHandler extends java.lang.Object/* implements Runnable*/ { //handles the player(s)
    Player players = new Player(); //make the new player
    int ss; //shield strength remaining
    private int li1, li2; //temp integers

    public PlayerHandler(int wid, int hei) { //constructor
        players.start(wid, hei); //define player
        ss = 100; //put the shield at 100
    }

    public void keyDown(int key) {
        if (key == 107) {
            players.thrust((double) .4);
        } //if key is "k", use the thrust(double) method to move player
        if (key == 106) {
            players.rotate((double) (Math.PI / -24));
        } //if key is "j", rotate left
        if (key == 108) {
            players.rotate((double) (Math.PI / 24));
        } //if key is "k", rotate right
        if (key == 122) { //if key is "z"
            players.shield = true; //turn on shield
            ss = ss - 1; //take of the shield strength by one unit
            if (ss < 0) { //if there isn't any shield left....
                ss = -1; //"turn off" shield strength
                players.shield = false; //turn off shield
            }
        }
    }

    public void check(AstHandler ah, int sta, int wid, int hei) { //check for asteroid hits, etc
        players.check(ah, sta, wid, hei); //let the player object do the REAL work... just passes arguments through the "chain of command"
        if (players.alive() == false) { //if our boy is wounded...
            players.stop(); //kill him, have mercy!
        }
    }

    public void move() { //move the player
        if (players.active) { //if its alive...
            players.move(); //it should be moving, right?
        }
    }

    public void paint(Graphics g, int wid, int hei) { //paint the player, do the shield display
        Color col2 = new Color(0); //make a color... must instantiate for future references
        if (players.active) { //if player is alive
            g.setColor(col2.white); //set the color to white
            g.drawString("Shield strength: ", 0, 10); //type shield strength on the graphics window
            g.drawRect(100, 0, wid - 101, 10); //make a bar shaped rectangle
            g.setColor(col2.red); //set color to red
            g.fillRect(101, 1, (int) (ss * ((wid - 101.5) / 100)), 9); //make a rectangle with a width of the "ss" shield strength variable
            players.paint(g); //call the player paint method
        }
    }
}

///////////////////////////////////////

class AstHandler extends java.lang.Object/* implements Runnable*/ { //asteroid handling class
    Ast asts[] = new Ast[41]; //make the asteroids! extra to avoid array exceptions
    Color col1; //temp color variable
    private int li1, li2; //temp integers

    public AstHandler() { //asteroid handler constructor
        for (li1 = 0; li1 < 41; li1++) { //cycle asteroids
            asts[li1] = new Ast(); //make a new asteroid
        }
    }

    public void AstCreate(int lev, int wid, int hei) { //create the asteroids with speed and number matching level
        for (li1 = 0; ((li1 < (int) (Math.random() * 4 + 2 + lev * 2)) && (li1 < 40)); li1++) { //cycle through a number of asteroids related to level number
            asts[li1].create(Math.random() * wid, Math.random() * hei, Math.random() * 2 * Math.PI, Math.random() * (lev / 2 + 1), Math.random() * 28 + 16); //make that asteroid with random xcord, ycord, angle, velocity (defined w/ level #), and random size
        }
    }

    public int check(ShotHandler shotH, int leve, int sco, AudioClip expl, int wid, int hei) { //check using the shot handler, level number, score, and explosion audio clip.  Returns the score or a level end value (-1)
        int li2 = -1; //start li2 out as "-1"
        for (li1 = 0; li1 < 40; li1++) { //cycle asteroids
            if (asts[li1].state == 1) { //if its alive and kicking:
                try {
                    asts[li1].check(shotH, wid, hei);
                } catch (NullPointerException e) {
                } //try to call the individual asteroids check method using the shot handler
                li2++; //advance li2
            }
            if (asts[li1].state == 2) { //if asteroid has JUST been hit (ready to be split)
                try {
                    split(li1, leve);
                } catch (NullPointerException e) {
                } //split the asteroid into two smaller ones
                sco = sco + ((int) (5 * leve)); //advance score
                expl.play(); //play the audio clip
            }
            if (asts[li1].state == 3) { //if asteroid is exploding...
                try {
                    asts[li1].kill();
                } catch (NullPointerException e) {
                } //kill the asteroid
            }
        }
        li1 = 0; //to descrease array exceptions
        if (li2 == -1) {
            sco = li2;
        } //if no asteroids were detected, tell main app. that the level is over
        return sco; //return the score (or indication that level is over)
    }

    public void split(int num, int lev) { //splits an asteroid into two new ones
        int li1, li2; //temp ints
        int freespot[] = new int[2]; //array for free asteroid spaces
        freespot[0] = -1; //make first space null
        freespot[1] = -1; //make second space also null
        for (li1 = 0; li1 < 40; li1++) { //cycle asteroids
            if (asts[li1].state == -1) { //if the space is free
                if (freespot[0] == -1) {
                    freespot[0] = li1;
                } //claim it if the freespot has not already been defined
            }
        }
        for (li1 = 0; li1 < 40; li1++) { //cycle asteroids
            if (asts[li1].state == -1) { //if the space is free
                if ((freespot[0] != li1) && (freespot[1] == -1)) {
                    freespot[1] = li1;
                } //if this isn't freespot[0]'s spot, and freespot[1] is still undefined, claim the space
            }
        }
        for (li1 = 0; li1 < 2; li1++) { //cycle freespots
            if (freespot[li1] != -1) { //if this freespot found a place to take
                if (asts[num].size > 12) {
                    asts[freespot[li1]].create(asts[num].xco, asts[num].yco, Math.random() * 2 * Math.PI, Math.random() * (lev / 2 + 2), asts[num].size / 2);
                } //IF THIS ASTEROID ISN'T TOO SMALL, make the (li1)'th new asteroid
            }
        }
        asts[num].state = 3; //mark this asteroid for certain death
    }

    public void move() { //moves the asteroids
        for (li1 = 0; li1 < 40; li1++) { //cycle through asteroids
            if (asts[li1].state == 1) { //if asteroid is alive...
                asts[li1].move(); //move it.
            }
        }
    }

    public void paint(Graphics g) { //paint the asteroids
        for (li1 = 0; li1 < 40; li1++) { //cycle asteroids
            if (asts[li1].state != -1) { //if asteroid isn't totally dead
                asts[li1].paint(g); //draw the asteroid
            }
        }
    }
}

class Ast extends java.lang.Object { //a single asteroid object
    int state = -1; //start off as non-existent
    Color col1; //temp color variable
    int dierot, rot;  //the "death color rotation", normal color rotation
    double size, pxc, pyc, xco, yco; //asteroid size, prev xcord, prev ycord, current xcord, current ycord
    double ang, vel; //asteroid angle direction, asteroid velocity

    public Ast() { //asteroid constructor... not much goin' on here, really
        state = -1; //asteroid is dead/non-existent
    }

    public void create(double xcor, double ycor, double angle, double veloc, double siz) { //makes a new asteroid based on paramaters
        xco = xcor; //transfer xcord
        yco = ycor; //transfer ycord
        vel = veloc; //transfer velocity
        ang = angle; //transfer angle
        state = 1; //make the asteroid alive
        size = siz; //transfer size
        rot = 0; //make a new color rotator
        dierot = 0; //make a new death color rotator
        pxc = xco; //define a new prev xcord
        pyc = yco; //define a new prev ycord
    }

    public void kill() { //destroy the asteroid
        dierot++; //move the dierot up one
        if (dierot > 32) { //if its been 32 ticks off dierot,
            state = -1; //finish the job and destroy asteroid for good
        }
    }

    public void paint(Graphics g) { //draws asteroid
        int li1; //temp integer
        if (state == 1) { //if asteroid is in normal state
            rot++; //advance color rotation
            if (rot == 16) {
                rot = 0;
            } //restart color rot at 16
            col1 = new Color(64 + (Math.abs(rot - 8) * 6), 64 + Math.abs(rot - 8) * 5, 255 - (64 + Math.abs(rot - 8) * 6)); //define grey-blue asteroid color
            g.setColor(col1); //set the new color
            g.fillOval((int) (xco - (size / 2)), (int) (yco - (size / 2)), (int) (size), (int) (size)); //draw the asteroid
        }
        if ((state == 2) || (state == 3)) { //if asteroid is splitting or dying
            if (dierot < 17) { //first half of death
                col1 = new Color(255, dierot * 8, 0); //new color (red->orange->yellow)
                g.setColor(col1); //set the new color
                g.fillOval((int) (xco - dierot / 2 - 4), (int) (yco - dierot / 2 - 4), (int) ((dierot + 8)), (int) ((dierot + 8))); //make the explosion circle
            }
            if (dierot > 16) { //second half of death
                col1 = new Color(255, (dierot - 16) * 5 + 127, 0); //new color (red->orange->yellow)
                g.setColor(col1); //set the new color
                g.fillOval((int) (xco - (32 - dierot) / 2 - 4), (int) (yco - (32 - dierot) / 2 - 4), (int) ((32 - dierot) + 8), (int) ((32 - dierot) + 8)); //make the explosion circle
            }
        }
    }

    public void move() { //moves the asteroid
        pxc = xco; //new prev xcord
        pyc = yco; //new prev ycord
        xco = pxc + (Math.cos(ang) * vel); //new xcord
        yco = pyc + (Math.sin(ang) * vel); //new ycord
    }

    public void check(ShotHandler sh, int wi, int he) { //check asteroid coordinates, see if its been hit (uses the shot handler)
        int li1, li2; //temp integers
        double sx[] = new double[16]; //array of shot xcords
        double sy[] = new double[16]; //array of shot ycords
        if (xco > wi + 10) { //if asteroid is off the right side of screen
            xco = xco - (wi + 20); //put asteroid on left side of screen
            pxc = xco; //define new prev xcord to prevent drawing problems
        }
        if (yco > he + 10) { //if asteroid is off the bottom of screen
            yco = yco - (he + 20); //put asteroid on top of screen
            pyc = yco; //define new prev ycord to prevent drawing problems
        }
        if (xco < -10) { //if asteroid is off the left side of screen
            xco = xco + wi + 20; //put asteroid on right side of screen
            pxc = xco; //define new prev xcord to prevent drawing problems
        }
        if (yco < -10) { //if asteroid is off the top of screen
            yco = yco + he + 20; //put asteroid on bottom of screen
            pyc = yco; //define new prev ycord to prevent drawing problems
        }
        for (li1 = 0; li1 < 16; li1++) { //cycle through shots in the imported shot handler
            sx[li1] = sh.shots[li1].xco; //move the xcord to the local array
            sy[li1] = sh.shots[li1].yco; //move the ycord to the local array
            if (sh.shots[li1].active != true) {
                sx[li1] = -127;
            } //if shot is non-existent, raise red flag w/ the -127 value.  -1 is a possible xcord, so I couldn't use that
        }
        for (li1 = 0; li1 < 16; li1++) { //cycle local "virtual shot array"
            if ((sx[li1] != -127) && (Math.abs(sx[li1] - xco) < size) && (Math.abs(sy[li1] - yco) < size)) { //if the shot exists, and is close enough to the asteroid...
                state = 2; //prepare asteroid for splitting
                sh.shots[li1].stop(); //kill the shot, as well
            }
        }
    }
}

/* THATS ALL, FOLKS!
 * once again, sigelman@javanet.com is my email address, and you
 * can see this applet (crash netscape) at my home page,
 * "Ben Sigelman's Black Russian Page (JAVA)" - http://www.javanet.com/~sigelman/
 *
 * Thanx for looking at my applet!
 */
