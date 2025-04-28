<h1>PacMan Java</h1>
<br>
<p>A classic Pac-Man clone written in Java using the Swing framework for rendering.</p>
<p> This project is a fully functional single-player of Pac-Man, featuring multiple levels, power pellets, ghosts, cherry bonuses, score tracking, life updating, and sound effects.</p>
<br>
<h2>Features</h2>
<ul>
  <li>Classic Pac-Man gameplay with keyboard controls (Arrow keys)</li>
  <li>Ghosts with randomized movement and scared state</li>
  <li>Power pellet and cherry mechanics</li>
  <li>Lives, score, and high score tracking</li>
  <li>Level progression with multiple maps</li>
  <li>Sound effects for actions like eating pellets, dying, and more</li>
  <li>Pause and restart functionality</li>
</ul>
<br>
<h2>Game Mechanics</h2>
<ul>
  <li><em>Movement:</em> Use arrow keys to control Pac-Man's direction.</li>
  <li><em>Power Pellets:</em> Eating one puts ghosts into a "scared" mode (for 8 seconds).</li>
  <li><em>Ghosts:</em> If you collide with a scared ghost, it resets and you earn points. Otherwise, you lose a life.</li>
  <li><em>Food:</em> Small dots give 10 points each.</li>
  <li><em>Cherries:</em> Randomly placed; each gives 500 points.</li>
  <li><em>Game Over:</em> Happens when you lose all 3 lives. High score is retained until you restart the application.</li>
</ul>
<br>
<h2>Controls</h2>
<ul>
  <li> ↑ ↓ ← →: Move Pac-Man </li>
  <li> P: Pause/Unpause </li>
  <li> Any arrow key after game over: Restarts the game</li>
</ul>
<br>
<h2>Assets</h2>
<h3>Images used:</h3>
<ul>
  <li>pacmanUp.png, pacmanDown.png, pacmanLeft.png, pacmanRight.png</li>
  <li>blueGhost.png, orangeGhost.png, pinkGhost.png, redGhost.png</li>
  <li>scaredGhost.png</li>
  <li>wall.png</li>
  <li>powerFood.png</li>
  <li>cherry.png</li>
</ul>
<h3>Sounds used:</h3>
<ul>
  <li><em>crunch1.wav:</em> Food eaten</li>
  <li><em>bigcrunch.wav:</em> Cherry eaten</li>
  <li><em>monsterbite.wav:</em> Ghost eaten</li>
  <li><em>lifelostgame.wav:</em> Life lost</li>
  <li><em>gameoversound.wav:</em> Game over</li>
</ul>
<h2>Code Structure</h2>
<ul>
  <li>PacMan.java: Main game logic, drawing, input handling, and state transitions</li>
  <li>App.java: Entry point to launch the game window</li>
  <li>Sound.java: Handles playing audio files</li>
</ul>
<h3>Map Layout</h3>
<h3>Maps are defined in a 2D string array:</h3>
<ul>
  <li>'X': Wall</li>
  <li>'P': Pac-Man starting position</li>
  <li>'b', 'o', 'p', 'r': Ghosts (Blue, Orange, Pink, Red)</li>
  <li>' ': Food dot</li>
  <li>'O': Portal zones (for tunnel traversal)</li>
</ul>
<h3>Getting Started</h3>
<h4>Prerequisites</h4>
<ul>
  <li>Java JDK 8 or higher</li>
  <li>An IDE like IntelliJ IDEA, Eclipse, or any text editor + terminal</li>
</ul>
<h4>Run Instructions</h4>
<ul>
  <li><em>Clone the repo:</em>em><br>git clone https://github.com/yourusername/pacman-java.git
cd pacman-java
</li>
  <li><em>Compile the project:</em> javac *.java</li>
  <li><em>Run the game: </em>java App</li>
</ul>
<h3>Screen Shoot</h3>
<img url = "![gamePausedss](https://github.com/user-attachments/assets/13a50e25-958c-48df-844e-d77e7e193e29)
">

