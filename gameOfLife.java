/*
 Game of Live
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class gameOfLive {
    final String NAME_GAME = "Game Of Life";
    final int START_LOCATION = 200;
    final int LIFE_SIZE = 50;
    final int POINT_RADIUS = 10;
    final int FIELD_SIZE = LIFE_SIZE * POINT_RADIUS + 7;
    final int BUTTON_PANEL_HEIGHT = 58;
    boolean[][] lifeGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];
    boolean[][] nextGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];
    Canvas gamePanel;

    public static void main(String[] args) {
        new gameOfLive().go();                                     //start our program
    }

    void go() {
        JFrame frame = new JFrame(NAME_GAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //set close-button
        frame.setSize(FIELD_SIZE,FIELD_SIZE + BUTTON_PANEL_HEIGHT);                      //set Size close-button
        frame.setLocation(START_LOCATION, START_LOCATION);          //set start window
        frame.setResizable(false);                                 // our window can't change size

        gamePanel = new Canvas();
        gamePanel.setBackground(Color.WHITE);

        JButton fillButton = new JButton("Fill");
        fillButton.addActionListener(new FillButtonListener());

        JButton stepButton = new JButton("Step");           //add button with text step on window
        stepButton.addActionListener(e -> {
            processOfLife();
            gamePanel.repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fillButton);
        buttonPanel.add(stepButton);

        frame.getContentPane().add(BorderLayout.CENTER, gamePanel);   //add gamePanel on window
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);  //add buttonPanel on window
        frame.setVisible(true);                                    // will do our window visible
    }


    //random fill cells
    public class FillButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            for ( int x = 0; x < LIFE_SIZE; x++)
            {
                for ( int y = 0; y < LIFE_SIZE; y++)
                {
                    lifeGeneration[x][y] = new Random().nextBoolean();
                }
            }
            gamePanel.repaint();
        }
    }

    int countNeighbours(int x, int y){
        int count = 0;
        for (int dx = -1; dx < 2; dx++)
        {
            for (int dy = -1; dy < 2; dy++)
            {
                int nX = x + dx;
                int nY = y + dy;
                nX = (nX < 0) ? LIFE_SIZE - 1 : nX;
                nY = (nY < 0) ? LIFE_SIZE - 1 : nY;
                nX = (nX > LIFE_SIZE - 1) ? 0 : nX;
                nY = (nY > LIFE_SIZE - 1) ? 0 : nY;
                count += (lifeGeneration[nX][nY]) ? 1 : 0;
            }
        }
        if (lifeGeneration[x][y]) count--;
        return count;
    }

    void processOfLife() {
        for (int x = 0; x < LIFE_SIZE; x++)
        {
            for (int y = 0; y < LIFE_SIZE; y++)
            {
                int count = countNeighbours(x, y);
                nextGeneration[x][y] = lifeGeneration[x][y];
                nextGeneration[x][y] = (count == 3) || nextGeneration[x][y];
                nextGeneration[x][y] = ((count >= 2) && (count <= 3)) && nextGeneration[x][y];
            }
        }
        for ( int x = 0; x < LIFE_SIZE; x++)
        {
            System.arraycopy(nextGeneration[x],0, lifeGeneration[x],0,LIFE_SIZE);
        }
    }

    public class Canvas extends JPanel {
        public void paint(Graphics g){
            super.paint(g);
            for ( int x = 0; x < LIFE_SIZE; x++)
            {
                for (int y = 0; y < LIFE_SIZE; y++)
                {
                    if (lifeGeneration[x][y])
                    {
                        g.fillOval(x * POINT_RADIUS, y * POINT_RADIUS,POINT_RADIUS,POINT_RADIUS);
                    }
                }
            }
        }
    }
}
