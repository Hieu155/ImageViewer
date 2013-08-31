/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageviewer;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;
import tools.Histogram;
import tools.PGMFileReader;
import tools.PGMImage;
import tools.PPMFileReader;
import tools.Sobel;

/**
 *
 * @author Iheb
 */
public class ImageViewer extends JFrame implements ActionListener {
    
    PGMImage image;
    String imagePath;
    Stack <PGMImage> history;
    JMenuBar menuBar;
    JMenuItem open,save,saveAs,close,undo,negative,sobel,histogram;
    JMenu fileMenu,editMenu;
    JFileChooser fileChooser;
    //Image img=null;
    JLabel imageFrame;
    public ImageViewer() {
        
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "PGM or PPM Images", "pgm", "ppm");
        
        fileChooser.setFileFilter(filter);
        imagePath="";
        
        this.setLayout(new BorderLayout());
        this.setTitle("Image Viewer");
        
        history= new Stack<>();
        
        menuBar= new JMenuBar();
        fileMenu= new JMenu("File");
        editMenu= new JMenu("Edit");
        
        open= new JMenuItem("Open file");
        save =new JMenuItem("Save");
        saveAs = new JMenuItem("Save as");
        close = new JMenuItem("Close");
        
        open.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        close.addActionListener(this);
        
        fileMenu.add(open);
        fileMenu.add(new JPopupMenu.Separator());
        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.add(new JPopupMenu.Separator());
        fileMenu.add(close);
        
        menuBar.add(fileMenu);
        
        undo = new JMenuItem("Undo");
        negative = new JMenuItem("Negative");
        sobel = new JMenuItem("Sobel filter");
        histogram = new JMenuItem("Histogram");
        
        undo.addActionListener(this);
        negative.addActionListener(this);
        sobel.addActionListener(this);
        histogram.addActionListener(this);
        
        editMenu.add(undo);
        editMenu.add(new JPopupMenu.Separator());
        editMenu.add(negative);
        editMenu.add(sobel);
        editMenu.add(new JPopupMenu.Separator());
        editMenu.add(histogram);
        
        menuBar.add(editMenu);
        imageFrame= new JLabel();
        this.add(imageFrame,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
        
        histogram.setEnabled(false);
        negative.setEnabled(false);
        sobel.setEnabled(false);
        save.setEnabled(false);
        saveAs.setEnabled(false);
        undo.setEnabled(false);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setVisible(true);
        
        
        
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ImageViewer imageViewer=new ImageViewer();
    }
    
    public void draw ()
    {
        MemoryImageSource source = new MemoryImageSource(image.getWidth(), image.getHeight(), ColorModel.getRGBdefault(), image.toRGBModel(), 0, image.getWidth());
        Image img =Toolkit.getDefaultToolkit().createImage(source);
        this.remove(imageFrame);
        imageFrame=new JLabel (new ImageIcon(img));
        this.add(imageFrame,BorderLayout.CENTER);
        
        this.validate();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (e.getSource().equals(open)) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal==JFileChooser.APPROVE_OPTION) {
                imagePath=fileChooser.getSelectedFile().getAbsolutePath();
                if (imagePath.toLowerCase().matches(".+\\.pgm")) {
                    image=PGMFileReader.readImage(imagePath);
                }
                else if (imagePath.toLowerCase().matches(".+\\.ppm")) {
                    image=PPMFileReader.readImage(imagePath).convertToPGM();
                } 
                
                
            }
            
        } else  if (e.getSource().equals(save)) 
        {
            imagePath=imagePath.replaceAll("\\.ppm", ".pgm");
            PGMFileReader.writeImage(imagePath, image);
            save.setEnabled(false);
        }
        else  if (e.getSource().equals(saveAs)) 
        {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal==JFileChooser.APPROVE_OPTION) {
                imagePath=fileChooser.getSelectedFile().getAbsolutePath().replaceAll("\\.ppm", ".pgm");
                PGMFileReader.writeImage(imagePath, image);
                save.setEnabled(false);
            }
        }
        else  if (e.getSource().equals(close)) 
        {
            System.exit(-1);
        }
        else  if (e.getSource().equals(undo)) 
        {
            if (!history.isEmpty()) {
                image = history.pop();
                save.setEnabled(true);
            }
        }
        else  if (e.getSource().equals(negative)) 
        {
            history.push(image);
            image= image.toNegatif();
            save.setEnabled(true);
            
        }
        else  if (e.getSource().equals(sobel)) 
        {
            history.push(image);
            Sobel sob = new Sobel(image);
            image=sob.toPGM();
            save.setEnabled(true);
            
        }
        else  if (e.getSource().equals(histogram)) 
        {
            Histogram hs=new Histogram(image,this);
            Integer [] axis= hs.getAxis();
            if (axis!=null) {
                history.push(image);
                image=image.thrishot(axis);
                save.setEnabled(true);
            }
            
        }
        if (image != null) {
            draw();
            histogram.setEnabled(true);
            negative.setEnabled(true);
            sobel.setEnabled(true);
            
            saveAs.setEnabled(true);
            
        }
        if (!history.empty()) {
            undo.setEnabled(true);
        } else
        {
            undo.setEnabled(false);
        }
    }

   
    
}
