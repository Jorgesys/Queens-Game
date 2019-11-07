
package queens;

import javafx.application.Application;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.*;


public class Queens extends Application {
    
    
    @Override
    public void start(Stage primaryStage) {

    }

    public static void main(String[] args) {
        //launch(args);
      
        QueensGame frame = new QueensGame();
       
        frame.setTitle("Queen´s Game");
         
        JPanel mainPanel = new JPanel();
 
        // Add panel to frame
        frame.add(mainPanel);
        frame.pack();
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
        
    
    
}