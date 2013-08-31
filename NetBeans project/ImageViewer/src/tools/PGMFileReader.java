/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iheb
 */
public class PGMFileReader {
    
    public static PGMImage readImage (String fileName)
    {
        PGMImage image= new PGMImage();
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            image.setType(scanner.nextLine());
            scanner.skip("(#.*\n)*");
            image.setWidth(scanner.nextInt());
            image.setHeight(scanner.nextInt());
            image.setCoding(scanner.nextInt());
            int []pixels = new int[image.getHeight()*image.getWidth()];
            for (int i = 0; i < image.getWidth()*image.getHeight(); i++) {
                pixels[i]=scanner.nextInt();
            }
            image.setPixels(pixels);
            scanner.close();
        } catch (Exception e) {
            
            System.out.println("Exception during reading the file");
            e.printStackTrace();
        }
        return image;
        
    }
    public static void writeImage(String fileName, PGMImage image)
    {
        try {
            //FileOutputStream stream = new FileOutputStream(fileName);
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            //out.write("Hello Java");
            out.append(image.getType()+" \n");
            out.append(image.getWidth()+" "+image.getHeight()+" \n");
            out.append(image.getCoding()+" \n");
            int []pixels= image.getValues();
            for (int i = 0; i <pixels.length ; i++) {
                out.append(pixels[i]+" ");
            }
            out.close();
            fstream.close();
            
        } catch (Exception ex) {
            Logger.getLogger(PGMFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
