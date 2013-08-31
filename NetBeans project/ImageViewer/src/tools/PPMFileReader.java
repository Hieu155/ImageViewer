/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iheb
 */
public class PPMFileReader {
    
    public static PPMImage readImage (String fileName)
    {
        PPMImage image= new PPMImage();
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            image.setType(scanner.nextLine());
            scanner.skip("(#.*\n)*");
            image.setWidth(scanner.nextInt());
            image.setHeight(scanner.nextInt());
            image.setCoding(scanner.nextInt());
            int []pixels1 = new int[image.getHeight()*image.getWidth()];
            int []pixels2 = new int[image.getHeight()*image.getWidth()];
            int []pixels3 = new int[image.getHeight()*image.getWidth()];
            
            for (int i = 0; i < image.getWidth()*image.getHeight(); i++) {
                pixels1[i]=scanner.nextInt();
                pixels2[i]=scanner.nextInt();
                pixels3[i]=scanner.nextInt();
            }
            image.setRedPixels(pixels1);
            image.setGreenPixels(pixels2);
            image.setBluPixels(pixels3);
            scanner.close();
        } catch (Exception e) {
            
            System.out.println("Exception during reading the file");
        }
        return image;
        
    }
    public static void writeImage(String fileName)
    {
        try {
            FileOutputStream stream = new FileOutputStream(fileName);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PPMFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
