import javax.swing.JFrame;

public class App extends  JFrame {
    
    public static void main(String args[]){
        int rowCount = 21;
        int colCount = 19;
        int tileSize = 32;
        int boardWidth = colCount*tileSize;
        int boardHeight = rowCount*tileSize;


        JFrame frame = new JFrame("Pace Man");
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        PacMan packManGame = new PacMan();
        frame.add(packManGame);
        frame.pack();
        packManGame.requestFocus();
        frame.setVisible(true);
    }
    
}
