/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Iheb
 */
public class PPMImage {
    private int width,height,coding;
    private String type;
    private int[] redPixels,greenPixels,bluPixels;

    public PPMImage() {
    }

    public int[] getRedPixels() {
        return redPixels;
    }

    public void setRedPixels(int[] redPixels) {
        this.redPixels = redPixels;
    }

    public int[] getGreenPixels() {
        return greenPixels;
    }

    public void setGreenPixels(int[] greenPixels) {
        this.greenPixels = greenPixels;
    }

    public int[] getBluPixels() {
        return bluPixels;
    }

    public void setBluPixels(int[] bluPixels) {
        this.bluPixels = bluPixels;
    }

    public PPMImage(int width, int height, int coding, String type, int[] redPixels, int[] greenPixels, int[] bluPixels) {
        this.width = width;
        this.height = height;
        this.coding = coding;
        this.type = type;
        this.redPixels = redPixels;
        this.greenPixels = greenPixels;
        this.bluPixels = bluPixels;
    }

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



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public PGMImage convertToPGM ()
    {
        PGMImage image= new PGMImage();
        int []pixels= new int [redPixels.length];
//        for (int i = 0; i < redPixels.length; i++) {
//            pixels[i]=0;
//        }
//        //System.out.println(redPixels.length/height);
        for (int i = 0; i < redPixels.length; i++) {
            pixels[i]= (int)((double)((redPixels[i]+greenPixels[i]+bluPixels[i])/3));
            //System.out.println((int)((double)((redPixels[i]+greenPixels[i]+bluPixels[i]))));
        }
        image.setCoding(coding);
        image.setType("P2");
        image.setHeight(height);
        image.setWidth(width);
        image.setPixels(pixels);
        return image;
    }
    
}
