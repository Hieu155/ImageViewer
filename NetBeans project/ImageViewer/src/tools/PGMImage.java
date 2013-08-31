/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Iheb
 */
public class PGMImage {
    private int width,height,coding;
    private String type;
    private int[] pixels;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCoding() {
        return coding;
    }

    public void setCoding(int coding) {
        this.coding = coding;
    }

    public PGMImage(int width, int height, int coding, String type, int[] values) {
        this.width = width;
        this.height = height;
        this.coding = coding;
        this.type = type;
        this.pixels = values;
    }

    public PGMImage() {
    }

    public int[] getValues() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int getPixel(int index) {
        return this.pixels[index];
    }

    public void setPixel(int index, int pixel) {
        this.pixels[index] = pixel;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public PGMImage toNegatif ()
    {
        PGMImage image=new PGMImage(width, height, coding, type, pixels);
        int [] resultPixels= new int [pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            resultPixels[i]=coding-pixels[i];
        }
        image.setPixels(resultPixels);
        return image;
                
    }
    public void thrishot (int axis)
    {
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i]<=axis) {
                pixels[i]=0;
            }
            else
            {
                pixels[i]=255;
            }
        }
    }
//    public void thrishot (int[] axis)
//    {
//        for (int i = 0; i < pixels.length; i++) {
//            int color=0;
//            for (int j = 1; j < axis.length; j++) {
//                if ((pixels[i]<=axis[j])&&(pixels[i]>axis[j-1])) {
//                    color=((int)((double)coding/axis.length)*j);
//                }
//                
//            
//            }
//            pixels[i]=color;    
//            
//        }
//    }
    public PGMImage thrishot (Integer[] axis)
    {
        PGMImage result = new PGMImage(width, height, coding, type, pixels);
        int [] pix = new int[result.getValues().length];
        for (int i = 0; i < result.getValues().length; i++) {
            int color=0;
            for (int j = 1; j < axis.length; j++) {
                if ((result.getPixel(i) <=axis[j])&&(result.getPixel(i)>axis[j-1])) {
                    color=((int)((double)result.getCoding()/axis.length)*j);
                }
                
            
            }
            pix[i]=color;    
            
        }
        result.setPixels(pix);
        return result;
    }
    public int [][] toMatrice () 
    {
        int [][] result = new int [this.width][this.height];
        
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                result [i][j]=this.pixels[i+j*this.width];
            }
        }
        
        return result;
    }
    public int[] toRGBModel ()
    {
        int [] result = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            result [i] = 0xFF000000+getPixel(i)*0x010101;
        }
        return result;
    }
}
