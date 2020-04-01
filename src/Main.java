import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;

public class Main extends JFrame implements KeyListener, MouseListener{

    private int w = 1680, h = 1028;
    //private Map<String, BufferedImage> Images = new HashMap<>();
    static double[] input = new double[4]; //array of input values
    static Neuron[] output = new Neuron[4]; //array to hold output values
    static int numHiddens = 4, hiddenSize = 4;
    MLP Perceptron = new MLP(input.length, hiddenSize, numHiddens, output.length);
    double[] expected = new double[output.length];
    boolean ready = true;

    private Main() throws IOException{
        super("title");
        setSize(w,h);
        setVisible(true);
        requestFocusInWindow();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);
        //getImages();
        double error = 1;
        int i = 1, correct = 0;

        while(true){
            input = randInput(input.length); //sets input values for the MLP
            expected = new double[]{input[0], input[1], input[2], input[3]};
            output = Perceptron.think(input, randOutput(output.length));//sets the output to
            error = Math.abs(expected[0] - output[0].value);
            //System.out.println("-----------------" + Arrays.toString(input) + "-----------------");
            //System.out.println(i + ": Expected = " + Arrays.toString(expected) + ", Calculated = " + output[0].value);
            //System.out.println("Error = " + error);//the output of the MLP
            Perceptron.backPropagate(expected, input, output);
            if(i>350000 && error<.08){
                correct++;
                //System.out.println((correct*100)/(i-35000.0));
            }
            i++;
            this.repaint();
            //try { Thread.sleep(100); }
            //catch(InterruptedException ex){ Thread.currentThread().interrupt(); }
        }
    }
    public static void main(String[] args) throws IOException{
        Main m = new Main();//basically starts program
    }
    public void paint(Graphics g){
        BufferedImage B = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics r = B.getGraphics();
        r.setColor(Color.BLUE);
        r.fillRect(20, 20, input.length*40+40, 80);
        for(int i = 0; i < input.length; i++) {
            int value = (int)(input[i]*255);
            r.setColor(new Color(value,value,value));
            r.fillRect(40*i+40, 40, 40, 40);
        }
        r.setColor(Color.YELLOW);
        r.fillRect(20, 120, 40*hiddenSize+40, 40*numHiddens+40);
        for(int i = 0; i < numHiddens; i++)
            for(int j = 0; j < hiddenSize; j++){
                int value = (int)(Perceptron.hidden[i][j].value*255);
                r.setColor(new Color(value,value,value));
                r.fillRect(40*j+40, 40*i+140, 40, 40);
            }
        r.setColor(Color.RED);
        r.fillRect(20, 40*numHiddens+180, output.length*40+40, 80);
        for(int i = 0; i < output.length; i++) {
            int value = (int)(output[i].value*255);
            r.setColor(new Color(value,value,value));
            r.fillRect(40*i+40, 40*numHiddens+200, 40, 40);
        }
        r.setColor(Color.GREEN);
        r.fillRect(output.length*40+80, 40*numHiddens+180, expected.length*40+40, 80);
        for(int i = 0; i < expected.length; i++) {
            int value = (int)(expected[i]*255);
            r.setColor(new Color(value,value,value));
            r.fillRect(40*i+(output.length*40+100), 40*numHiddens+200, 40, 40);
        }
        g.drawImage(B, 0, 22, this);
    }
    private static Neuron[] randOutput(int num){    //creates an array of neurons
        Neuron[] ret = new Neuron[num];             //with random biases
        for(int i = 0; i < num; i++) ret[i] = new Neuron();
        return ret;
    }
    private static double[] randInput(int size){ //generates random input values 0 or 1
        double[] ret = new double[size];
        for(int i = 0; i < size; i++) ret[i] = (int)(Math.random()*2);
        return ret;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            //code
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ready = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
