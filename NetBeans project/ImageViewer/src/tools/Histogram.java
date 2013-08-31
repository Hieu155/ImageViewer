/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import tools.PGMFileReader;
import tools.PGMImage;
import tools.Sobel;
/**
 *
 * @author Iheb
 */
public class Histogram extends JDialog implements MouseListener {
    private int []grayHistogram =null;
    private boolean initialized= false;
    private JPanel mainPanel,northPanel;
    //private JTextField fileNameField;
    //private JButton fileChoosingButton;
    private int maxGrayValue;
    private PGMImage image;
    Vector <Integer> axis;
    public Histogram(PGMImage image, JFrame parent) 
    {
        super(parent,true);
//        if (parent != null) {
//            Dimension parentSize = parent.getSize(); 
//            Point p = parent.getLocation(); 
//            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
//        }
        initialized= false;
        maxGrayValue=0;
        this.setTitle("Image Histogram");
        mainPanel= new JPanel(new BorderLayout());
        mainPanel.addMouseListener(this);
 //       northPanel = new JPanel();
//        fileChoosingButton=new JButton("Open file");
//        fileChoosingButton.addActionListener(this);
//        fileNameField=new JTextField(20);
        this.setBounds(100, 100, 400, 300);
        //this.setPreferredSize(new Dimension(300, 300));
//        northPanel.add(new JLabel("File name: "));
//        northPanel.add(fileNameField);
//        northPanel.add(fileChoosingButton);
//        mainPanel.add(northPanel,BorderLayout.NORTH);
        mainPanel.setToolTipText("Click to add an axis");
        this.getContentPane().add(mainPanel);
 //       this.pack();
        axis = new Vector <Integer>(10);
        this.image = image;
        if (image != null) {
            buildGrayHistogram();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (initialized)
        {
            displayGrayHistogram(g);
        }
    }
    
    

    public int[] getGrayHistogram() {
        return grayHistogram;
    }

    public void setGrayHistogram(int[] grayHistogram) {
        this.grayHistogram = grayHistogram;
    }
    
    public void buildGrayHistogram ()
    {
        grayHistogram = new int[image.getCoding()+1];
        maxGrayValue=0;
        for (int i = 0; i <= image.getCoding(); i++) {
            grayHistogram[i]=0;
        }
        for (int i = 0; i < image.getWidth()*image.getHeight(); i++) {
            grayHistogram[image.getPixel(i)]++;
        }
        for (int i = 0; i < grayHistogram.length; i++)
        {
            if (maxGrayValue<grayHistogram[i]) 
            {
                maxGrayValue=grayHistogram[i];
            }
        }
        initialized= true;
    }
    public void displayGrayHistogram (Graphics g)
    {
        int x1=75,y1=75;
        int h=150,w=grayHistogram.length;
        int x2=x1+w,y2=y1+h;
        g.setColor(Color.WHITE);
        //g.drawLine(10, 100, 120, 120);
        g.fillRect(x1, y1, w, h);
        g.setColor(Color.BLACK);
        for (int i = 0; i < grayHistogram.length; i++) {
            g.drawLine(x1+i, y2-(int)(h*((float)grayHistogram[i]/maxGrayValue)), x1+i, y2);
        }
        for (int i = 0; i < 5; i++) {
            g.drawLine(x1-10, y1+(int)((float)h/4)*i, x1, y1+(int)((float)h/4)*i);
            g.drawLine( x1+(int)((float)w/4)*i,y2+10, x1+(int)((float)w/4)*i,y2);
            g.drawString((int)(0.25*(4-i)*maxGrayValue)+"", x1-40, 5+y1+(int)((float)h/4)*i);
            g.drawString((int)(0.25*(i)*(grayHistogram.length-1))+"",x1+(int)((float)w/4)*i, y2+20);
        }
        //return g;
    }
//    public static void main(String[] args) {
//        //Histogram histogram= new Histogram();
//        PGMImage image=PGMFileReader.readImage("image1.pgm");
//        //PGMImage convertedImage=image.convertToPGM();
//        int [][]mat=image.toMatrice();
//        Sobel sobel= new Sobel(image);
//        //int [][] sX={{-1,0,1},{-2,0,2},{-1,0,1}};
//        //int [][] sY={{-1,-2,-1},{0,0,0},{1,2,1}};
//        
//        int [][] sX={{1,0,-1},{2,0,-2},{1,0,-1}};
//        int [][] sY={{1,2,1},{0,0,0},{-1,-2,-1}};
//        
//        //sobel.setsX(sX);
//        //sobel.setsY(sY);
//        
//        PGMImage imageX =sobel.toPGMX();
//        PGMImage imageY =sobel.toPGMY();
//        PGMImage imageR =sobel.toPGM();
//        
//        //PGMImage negatifImage=convertedImage.toNegatif();
//        //int axis[]= {0,15,36,55,255};
//        //image.thrishot(axis);
//        PGMFileReader.writeImage("outX.pgm", imageX);
//        PGMFileReader.writeImage("outY.pgm", imageY);
//        PGMFileReader.writeImage("outR.pgm", imageR);
//        //System.exit(-1);
//        
//    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        JFileChooser chooser = new JFileChooser(fileNameField.getText());
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//        "PGM Images", "pgm");
//        chooser.setFileFilter(filter);
//        int returnVal = chooser.showOpenDialog(this);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            fileNameField.setText(chooser.getSelectedFile().getAbsolutePath());
//            PGMImage image=PGMFileReader.readImage(chooser.getSelectedFile().getAbsolutePath());
//            buildGrayHistogram(image);
//            this.setTitle("Image Histogram: "+chooser.getSelectedFile().getName());
//            this.repaint();
//            
//    }
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x=e.getX()-67;
        int y=e.getY();
        if ((x>=0)&&(x<256)&&(y>=45)&&(y<=195)) {
            System.out.println(x+"        "+y);
            int answer=JOptionPane.showConfirmDialog(this, "Do you want to add "+x+" as an axis?",
                    "histogram thresholding", JOptionPane.YES_NO_OPTION);
            if (answer == 0) 
            {
                axis.add(new Integer(x));
            }
            
        }
        repaint();
    }
    
    public Integer[] getAxis ()
    {
        
        
        if (axis.size()!=0) {
            axis.add(255);
            axis.add(0);
            axis.trimToSize();
            Integer[] result = new Integer[axis.size()];
            for (int i = 0; i < result.length; i++) {
                Integer min = 0;
                for (int j = 0; j < axis.size(); j++) {
                    if (axis.get(j) < axis.get(min)) {
                        min = j;
                    }
                    
                }

                //axis.add(9999);
                result[i] = axis.get(min);
                axis.remove((int) min);
            }
            return result;
            
        }
        
        return null; 
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX()+"   ******     "+e.getY());
        
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        repaint();
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        repaint();
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
