import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;

import javax.swing.*;
    

public class PacMan extends JPanel implements ActionListener, KeyListener{

     class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;
        char direction = 'U'; // U L R D
        int velocityX = 0;
        int velocityY = 0;

        boolean scared = false;
        Image originalImage;

        // constructor
        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.originalImage = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }
        void updateDirection(char direction){
            char prevDirection = this.direction;
            this.direction = direction;
            updatedVelocity();
            this.x += velocityX;
            this.y += velocityY;
            for(Block  wall: walls){
                if (collision(this, wall)) {
                    this.x -= velocityX;
                    this.y -= velocityY;
                    this.direction = prevDirection;
                    updatedVelocity();
                }
            }

        }
        void updatedVelocity(){
           switch (this.direction) {
            case 'U'-> {velocityX = 0; velocityY = -tileSize/4;}
            case 'D'-> {velocityX = 0; velocityY = tileSize/4;}
            case 'R'-> {velocityX = tileSize/4; velocityY = 0;}
            case 'L'-> {velocityX = -tileSize/4; velocityY = 0;}
           }
        }
        public void setscared(boolean scared){
            this.scared = scared;
            if (scared) {
                this.image = scaredGhostImage;
            }else{
                this.image = originalImage;
            }
        }
        public void reset() {
            this.x = startX;
            this.y = startY;
        }
       

    }

    boolean paused = false; 

    private int rowCount = 21;
    private int colCount = 19;
    private int tileSize = 32;
    private int boardWidth = colCount*tileSize;
    private int boardHeight = rowCount*tileSize;
   
    private boolean isPowerMode = false;
    private long powerModeEndTime;
    private final long POWER_MODE_DURATION = 8000; // 8second

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image redGhostImage;
    private Image pintGhostImage;
    private Image scaredGhostImage;

    private Image redCherryImage;

    private Image powerPelletImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanRightImage;
    private Image pacmanLeftImage;

    /* x = walls, o = ship, P = pac man, ' ' = food;
     * Ghosts b= blue, r= red, o = orange, p = pink;
     *  
     */

     private String[][] tileMap = {{
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
     },
     { "XXXXXXXXXXXXXXXXXXX",
     "X   o     X     o X",
     "X XXXXX X X XXXXX X",
     "X X   X X X X   X X",
     "X X X X X X X X X X",
     "X X X X     X X X X",
     "X   X XXXXXXX X   X",
     "XXX X    p    X XXX",
     "X   XXXXX XXXXX   X",
     "X X             X X",
     "X X XXXXXPXXXXX X X",
     "X X             X X",
     "X   XXXXX XXXXX   X",
     "XXX X    b    X XXX",
     "X   X XXXXXXX X   X",
     "X X X     X     X X",
     "X X X X X X X X X X",
     "X X   X X X X   X X",
     "X XXXXX X X XXXXX X",
     "X r     X     r   X",
     "XXXXXXXXXXXXXXXXXXX"}
    };

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    HashSet<Block> powePallet;
    HashSet<Block> cherries;
    Block pacman;

    Timer gameLoop;
    char[] directions = {'U','D','R','L'};
    Random random = new Random();
    int score = 0;
    public int highScore = 0;
    int lives = 3;
    boolean gameOver = false;

    private int currentLevel = 0;

    // constructor 
    PacMan(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        // Image loader 
        wallImage = new ImageIcon(getClass().getResource("./wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./redGhost.png")).getImage();
        pintGhostImage = new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();
        scaredGhostImage = new ImageIcon(getClass().getResource("./scaredGhost.png")).getImage();

        redCherryImage = new ImageIcon(getClass().getResource("./cherry.png")).getImage();
        powerPelletImage = new ImageIcon(getClass().getResource("./powerFood.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage();

        // colling load map
        loadMap();
        for(Block ghost : ghosts){
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        // how long it takes to start timer, miliseconds gone between frames
        gameLoop = new Timer(20, this);// 20fps(1000÷50)
        gameLoop.start(); 
    }

    
    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();
        powePallet = new HashSet<>();
        cherries = new HashSet<>();
        Random rand = new Random();
        

        int cherryCount = 2 + rand.nextInt(2);

        while (cherryCount> 0) {
            int r = rand.nextInt(rowCount);
            int c = rand.nextInt(colCount);
            char tile = tileMap[currentLevel][r].charAt(c);

            if (tile == ' ') {
                int x = c*tileSize +8;
                int y = r*tileSize +8;
                Block cherry = new Block(redCherryImage, x, y, 16, 16);
                cherries.add(cherry);
                cherryCount--;
            }


        }

        powePallet.clear();
        int poweCount = 4;
        while (poweCount>0) {
            int r = rand.nextInt(rowCount);
            int c = rand.nextInt(colCount);
            char tile = tileMap[currentLevel][r].charAt(c);

            if (tile == ' ') {
                int x = c*tileSize+12;
                int y = r*tileSize+12;
                Block pallet = new Block(powerPelletImage, x, y, 8, 8);
                powePallet.add(pallet);
                poweCount--;

            }

        }


        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                String row = tileMap[currentLevel][r];
                char tileMapChar = row.charAt(c);

                int x = c*tileSize;
                int y = r*tileSize;



                if (tileMapChar == 'X') { //block wall
                    Block wall = new Block(wallImage, x, y, tileSize, tileSize);
                    walls.add(wall);
                }
                else if (tileMapChar == 'b') { //blue ghost
                    Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'o') { //orange ghost
                    Block ghost = new Block(orangeGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'p') { //pink ghost
                    Block ghost = new Block(pintGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'r') { //red ghost
                    Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'P') { //pacman
                    pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                }
                else if (tileMapChar == ' ') { //food
                    Block food = new Block(null, x + 14, y + 14, 4, 4);
                    foods.add(food);
                }
            }
            
        }
        
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        g.setColor(Color.YELLOW);
        for (Block pellet : powePallet)
            g.fillOval(pellet.x, pellet.y, pellet.width, pellet.height);


        g.setColor(Color.WHITE);
        for (Block food : foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
        }
        for(Block cherry : cherries){
            g.drawImage(cherry.image, cherry.x, cherry.y, cherry.width, cherry.height, null);
        }
        //score

       // g.setFont(new Font("Arial", Font.PLAIN, 19));
      
       
        if (paused) {
            g.setFont(new Font("Arial",Font.BOLD,36));
            g.setColor(Color.yellow);
            g.drawString("Paused", boardWidth/2-60, boardHeight/2);
           
        }else if (gameOver ) {
            g.setFont(new Font("Arial",Font.BOLD,30));
            g.setColor(Color.RED);
            g.drawString("Game Over "+" :Height Score" + highScore , boardWidth/2-180, boardHeight/2);
        }
        else {
            g.setFont(new Font("Arial", Font.PLAIN,19));
            g.drawString("X"+ String.valueOf(lives)+"Score: "+(score)+" Height Score "+ String.valueOf(highScore),tileSize/2, tileSize/2);
        }
    }
   // move function of pacman
    public void move(){
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        if (pacman.y == 9 *tileSize) {
            if (pacman.x<0) {
                    pacman.x =boardWidth-pacman.height;
            }else if (pacman.x + pacman.width > boardWidth) {
                pacman.x = 0;
            }
        }

        // check wall collision 
        for(Block wall: walls){
            if (collision(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
            }
            // check ghost collision
            for(Block ghost: ghosts){
                if (collision(ghost, pacman)) {
                    if (ghost.scared) {
                        score += 200;
                        Sound.playSound("./monsterbite.wav");
                        ghost.reset();
                        ghost.setscared(false);
                    }else{

                    
                    lives -= 1;
                    Sound.playSound("./lifelostgame.wav");
                    if (lives == 0) {
                        if (score> highScore) {
                            highScore = score;
                        }
                        gameOver = true;
                        Sound.playSound("./gameoversound.wav");
                        return;
                    }
                    resetPositions();
                }
                  
                }
                
                if (ghost.y == tileSize*9 && ghost.direction != 'U' && ghost.direction != 'D') {
                    ghost.updateDirection('U');
                }
                

                ghost.x += ghost.velocityX;
                ghost.y += ghost.velocityY;
                for (Block wall : walls) {
                    if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= boardWidth) {
                        ghost.x -= ghost.velocityX;
                        ghost.y -= ghost.velocityY;
                        char newDirection = directions[random.nextInt(4)];
                        ghost.updateDirection(newDirection);
                    }
                }
        }

        // cherries 
        Block eatenCherry = null;
        for(Block cherry: cherries){
            if (collision(pacman, cherry)) {
                eatenCherry = cherry;
                score += 500;
                Sound.playSound("./bigcrunch.wav");
            }

        }cherries.remove(eatenCherry);

        // food eaten by pacman
        Block eatenFood = null;
        for(Block food : foods){
            if (collision(pacman, food)) {
                eatenFood = food;
                score += 10;
                Sound.playSound("./crunch1.wav");
            }
            
        } foods.remove(eatenFood);
        
        Block eatenPellet = null;
        for(Block pellet : powePallet){
            if (collision(pacman, pellet)) {
                eatenPellet = pellet;
                score+=50;
                isPowerMode = true;
                powerModeEndTime = System.currentTimeMillis()+POWER_MODE_DURATION;
                scaredGhost();
            }
        }
        powePallet.remove(eatenPellet);


        // level handling 
        if (foods.isEmpty() && powePallet.isEmpty()) {
            currentLevel++;
            
            if (currentLevel >= tileMap.length) {
                currentLevel = 0;
            }
            loadMap();
            resetPositions();
        }
    }
    private void scaredGhost(){
        for(Block ghost: ghosts){
            ghost.setscared(true);
        }
        if (isPowerMode && System.currentTimeMillis() > powerModeEndTime) {
            isPowerMode = false;
            for(Block ghost: ghosts){
                ghost.setscared(false);
            }
        }
    }
    public boolean collision(Block a, Block b){
        return a.x < b.x + b.width && 
                a.x+ a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    public void resetPositions(){
        pacman.reset();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for(Block ghost: ghosts){
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused && !gameOver) {
        move();
        repaint();
        }
        else {
            gameLoop.stop();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) {
       // System.out.println("KeyEvent: "+e.getKeyCode());
       if (e.getKeyCode() == KeyEvent.VK_P) {
           if (gameLoop.isRunning()) {
                gameLoop.stop();
                paused = true;
                
           }
           else  {
                gameLoop.start();
                paused = false;
           }
           repaint();
           return;
       }
       
        if (gameOver){
            loadMap();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }
        

       
       if (e.getKeyCode() == KeyEvent.VK_UP) {
        pacman.updateDirection('U');
        pacman.image = pacmanUpImage;
       }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        pacman.updateDirection('D');
        pacman.image = pacmanDownImage;
       }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        pacman.updateDirection('L');
        pacman.image = pacmanLeftImage;
       }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        pacman.updateDirection('R');
        pacman.image = pacmanRightImage;
       }

     }

}