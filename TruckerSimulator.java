import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;

import java.io.*;

import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import javax.swing.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class TruckerSimulator extends JFrame{
    int N;
    int w = 1600;
    int h = 768;
    int X = 0;
    int Y = 0;
    int V = 1000;
    public boolean day = true;
    final int ele = (int)( Math.random() * 100 + 15 );
    final int turnL = (int)( Math.random() * 30 + 10 );
    final int turnR = (int)( Math.random() * 30 + 10 );

    //boolean gameOver = false;
    DrawPanel dp = new DrawPanel();
    List<Line> lines = new ArrayList<>();

    int[] turnsL = new int[turnL];
    int[] turnsR = new int[turnR];
    int[] eles = new int[ele];

    //LocalTime time = LocalTime.now();
    Timer timer;
    Timer cycle;


    /*class Obstacle {
        int position;
        int lane;

        public Obstacle(int position, int lane) {
            this.position = position;
            this.lane = lane;
        }
        public void draw(Graphics g, Line line) {
            int obstacleWidth = (int)(line.W * 0.15);
            int obstacleHeight = (int)(line.W * 0.15);
            int obstacleX = (int)(line.X - obstacleWidth / 2) + lane * obstacleWidth;
            int obstacleY = (int)(line.Y);

            g.setColor(Color.RED);
            g.fillRect(obstacleX, obstacleY, obstacleWidth, obstacleHeight);
        }

    }
    public boolean check(Obstacle obstacle, Line) {
        int truckWidth = 50;
        int obstacleWidth = (int)(line.W * 0.15);
        int obstacleX = (int)(line.X - obstacleWidth / 2) + obstacle.lane * obstacleWidth;
        int obstacleY = (int) line.Y;

        if (X < obstacleX + truckWidth && X + truckWidth > obstacleX && Y < obstacleY) return true;
        return false;

    }


    List<Obstacle> obstacles = new ArrayList<>();
    public void GameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "ПРОИГРЫШ!!!!!!");
    }*/


    public TruckerSimulator(){
        final int a = (int)( Math.random() * 300 + 0 );
        eles[0] = a;
        for(int i = 1; i < ele; i++){
            final int a1 = (int)( Math.random() * 10 + eles[i-1] );
            eles[i] = a1;
        }
        final int b = (int)( Math.random() * 300 + 100 );
        turnsL[0] = b;
        for(int i = 1; i < turnL; i++){
            final int b1 = (int)( Math.random() * 300 + turnsL[i-1] + 100 );
            turnsL[i] = b1;
        }
        final int c = (int)( Math.random() * 300 + 100 );
        turnsR[0] = c;
        for(int i = 1; i < turnR; i++){
            final int c1 = (int)( Math.random() * 300 + turnsR[i-1] + 100 );
            turnsR[i] = c1;
        }

        /*for (int i = 0; i < 10; i++) {
            int randomPosition = (int)(Math.random() * 1600000);
            int randomLane = (int)(Math.random() * 3) - 1;
            obstacles.add(new Obstacle(randomPosition, randomLane * 8));
        }*/


        for (int i = 0; i < 9000000; i++) {

            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "HH:mm:ss" );

            Line line = new Line();
            line.z = i * 768;

            for(int j = 0; j < turnL; j++) if(i > turnsL[j] && i < turnsL[j]+100) line.curve = 5;
            for(int j = 0; j < turnR; j++) if(i > turnsR[j] && i < turnsR[j]+100) line.curve = -5;
            for(int j = 0; j < ele; j++) if(i > eles[j]) line.y = Math.sin(Math.toRadians((double) i / 5)) * 1500;

            /*if(     Integer.parseInt(time.format(formatter).substring(0, 2)) >= 21 ||
                    Integer.parseInt(time.format(formatter).substring(0, 2)) >= 0 &&
                            Integer.parseInt(time.format(formatter).substring(0, 2)) <= 6)
                day = false;

            if(     Integer.parseInt(time.format(formatter).substring(0, 2)) < 21 &&
                    Integer.parseInt(time.format(formatter).substring(0, 2)) > 6)
                day = true;*/

            lines.add(line);
        }
        N = lines.size();

        timer = new Timer(10, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                Y += V;
                dp.repaint();
                /*if (!GameOver) {
                    Y += V;
                    for (Obstacle obstacle : obstacles) {
                        Line = lines.get(obstacle.position / h % N);
                        if (check(obstacle, line)) {
                            GameOver = true;
                            GameOver();
                            return;
                        }
                    }
                    dp.repaint();
                }*/
            }
        });
        cycle = new Timer(10000, e -> {
            day = !day;
            dp.repaint();
        });
        timer.start();
        cycle.start();
        add( dp );
        pack();

        setTitle("Trucker Simulator");
        setIconImage( new ImageIcon("src\\ava.jpg").getImage() );

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    private class DrawPanel extends JPanel{

        public DrawPanel() {
            InputMap inputMap = getInputMap( WHEN_IN_FOCUSED_WINDOW );
            ActionMap actionMap = getActionMap();

            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "VK_LEFT");
            actionMap.put("VK_LEFT", new AbstractAction(){
                public void actionPerformed( ActionEvent e ){
                    if(X > -1000) X -= 150;
                    dp.repaint();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "VK_RIGHT");
            actionMap.put("VK_RIGHT", new AbstractAction(){
                public void actionPerformed( ActionEvent e ){
                    if(X < 1000) X += 150;
                    dp.repaint();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "VK_UP");
            actionMap.put("VK_UP", new AbstractAction(){
                public void actionPerformed( ActionEvent e ){
                    if(V < 1500) V += 100;
                }
            });
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "VK_DOWN");
            actionMap.put("VK_DOWN", new AbstractAction(){
                public void actionPerformed( ActionEvent e ){
                    if(V > 650) V -= 100;
                }
            });

        }
        protected void paintComponent( Graphics g ){
            super.paintComponent( g );
            drawval( g );
        }
        private void drawval( Graphics g ) {
            int sx = Y / h;
            int ch = 1500 + (int) lines.get( sx ).y;

            int c1 ;
            int c2;
            int c3;
            int c4;

            double x = 0;
            double dx = 0;
            double maxY = h;


            if( day ){
                c1 = 255;
                c2 = 16;
                c3 = 200;
                c4 = 180;
            }
            else{
                c1 = 85;
                c2 = 0;
                c3 = 128;
                c4 = 100;
            }


            for (int i = sx; i < sx + 300; i++) {
                Line l = lines.get(i % N);
                l.project(X - (int)x, ch, Y);
                x += dx;
                dx += l.curve;
                if ( l.Y > 0 && l.Y < maxY ) {
                    maxY = l.Y;

                    Color road = Color.black;
                    Color grass = ( ( i / 2 ) % 2 ) == 0 ? new Color( c2, c3, c2 ) : new Color(0, c4, 0);
                    Color rumble = new Color(c1, c1, c1);
                    Color markup = ( ( i / 2 ) % 2 ) == 0 ? new Color( c1, c1, c1 ) : Color.black;
                    Line p;

                    if( i == 0 ) p = l;
                    else p = lines.get( ( i - 1 ) % N );

                    dq(g, grass, 0, (int) p.Y, w, 0, (int) l.Y, w);
                    dq(g, rumble, (int) p.X, (int) p.Y, (int) (p.W * 1.5), (int) l.X, (int) l.Y, (int) (l.W * 1.5));
                    dq(g, road, (int) p.X, (int) p.Y, (int) (p.W * 1.4), (int) l.X, (int) l.Y, (int) (l.W * 1.4));
                    dq(g, markup, (int) p.X, (int) p.Y, (int) (p.W * 0.8), (int) l.X, (int) l.Y, (int) (l.W * 0.8));
                    dq(g, road, (int) p.X, (int) p.Y, (int) (p.W * 0.7), (int) l.X, (int) l.Y, (int) (l.W* 0.7));
                }
            }

            /*for (Obstacle obstacle : obstacles) {
                int lineIndex = obstacle.position / h;
                if (lineIndex >= sx && lineIndex < sx + 300) {
                    Line line = lines.get(lineIndex % N);
                    System.out.println("Отрисовка препятствия на полосе: " + obstacle.lane + " с координатами: X=" + obstacle.position + ", Y=" + line.Y); // Логирование
                    obstacle.draw(g, line);
                }
            }*/

            try{
                if(day)g.drawImage(ImageIO.read(new File("src\\day.jpg")), 0, 0, this);
                if(!day)g.drawImage(ImageIO.read(new File("src\\night.jpg")), 0, 0, this);
                g.drawImage(ImageIO.read(new File("src\\cabin.png")), 0, 0, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void dq( Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2 ){
            int[] x9Points = { x1 - w1, x2 - w2, x2 + w2, x1 + w1 };
            int[] y9Points = { y1, y2, y2, y1 };
            int n9Points = 4;
            g.setColor( c );
            g.fillPolygon( x9Points, y9Points, n9Points );
        }
        public Dimension getPreferredSize(){ return new Dimension(1500, 750); }
    }
    public static void main(String[] args) throws IOException, JavaLayerException {
        EventQueue.invokeLater( TruckerSimulator::new );
        playRadioStream();
    }
    private static void playRadioStream() throws IOException, JavaLayerException{
        URLConnection urlConnection = new URL("https://dorognoe.hostingradio.ru/radio").openConnection();
        urlConnection.connect();

        Player player = new Player ( urlConnection.getInputStream() );
        player.play();
    }

    class Line{
        double x, y, z;
        double X, Y, W;
        double scale, curve;
        public Line() { curve = x = y = z = 0; }
        void project( int camX, int camY, int camZ ) {
            scale = 0.84 / ( z - camZ );
            X = ( 1 + scale * ( x - camX) ) * w / 2;
            Y = ( 1 - scale * ( y - camY) ) * h / 2;
            W = scale * h * w / 2;
        }

    }
}