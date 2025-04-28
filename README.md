<h1>PacMan Java</h1>
<br>
<p>A classic Pac-Man clone written in Java using the Swing framework for rendering. This project is a fully functional single-player of Pac-Man, featuring multiple levels, power pellets, ghosts, cherry bonuses, score tracking, life updating, and sound effects.</p>
<br>
<h3>Features</h3>
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
<h3>Game Mechanics</h3>
<ul>
  <li>Movement: Use arrow keys to control Pac-Man's direction.</li>
  <li>Power Pellets: Eating one puts ghosts into a "scared" mode (for 8 seconds).</li>
  <li>Ghosts: If you collide with a scared ghost, it resets and you earn points. Otherwise, you lose a life.</li>
  <li>Food: Small dots give 10 points each.</li>
  <li>Cherries: Randomly placed; each gives 500 points.</li>
  <li>Game Over: Happens when you lose all 3 lives. High score is retained until you restart the application.</li>
</ul>
<br>
<h3>Controls</h3>
<ul>
  <li> ↑ ↓ ← →: Move Pac-Man </li>
  <li> P: Pause/Unpause </li>
  <li> Any arrow key after game over: Restarts the game</li>
</ul>
<br>
<h3>Assets</h3>
<h5>Images used:</h5>
<ul>
  <li>pacmanUp.png, pacmanDown.png, pacmanLeft.png, pacmanRight.png</li>
  <li>blueGhost.png, orangeGhost.png, pinkGhost.png, redGhost.png</li>
  <li>scaredGhost.png</li>
  <li>wall.png</li>
  <li>powerFood.png</li>
  <li>cherry.png</li>
</ul>
<br>
<h5>Sounds used:</h5>
<ul>
  <li>crunch1.wav: Food eaten</li>
  <li>bigcrunch.wav: Cherry eaten</li>
  <li>monsterbite.wav: Ghost eaten</li>
  <li>lifelostgame.wav: Life lost</li>
  <li>gameoversound.wav: Game over</li>
</ul>
<br>
<h3>Code Structure</h3>
<ul>
  <li>PacMan.java: Main game logic, drawing, input handling, and state transitions</li>
  <li>App.java: Entry point to launch the game window</li>
  <li>Sound.java: Handles playing audio files</li>
</ul>


