/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Iheb
 */
public class Sobel {
    private static final int [][] SX ={{1,0,-1},{2,0,-2},{1,0,-1}} , SY={{1,2,1},{0,0,0},{-1,-2,-1}};
    int height;
    int width;
    int [][] entry,sX,sY,maskedImageX,maskedImageY,maskedImage;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int[][] getEntry() {
        return entry;
    }

    public void setEntry(int[][] entry) {
        this.entry = entry;
    }

    public int[][] getsX() {
        return sX;
    }

    public void setsX(int[][] sX) {
        this.sX = sX;
        this.maskedImageX=convolution(sX);
    }

    public int[][] getsY() {
        return sY;
    }

    public void setsY(int[][] sY) {
        this.sY = sY;
        this.maskedImageY=convolution(sY);
    }

    public int[][] getMaskedImageX() {
        return maskedImageX;
    }

    public void setMaskedImageX(int[][] maskedImageX) {
        this.maskedImageX = maskedImageX;
    }

    public int[][] getMaskedImageY() {
        return maskedImageY;
    }

    public void setMaskedImageY(int[][] maskedImageY) {
        this.maskedImageY = maskedImageY;
    }
    

    public Sobel(int height, int width, int[][] entry, int[][] sX, int[][] sY) {
        this.height = height;
        this.width = width;
        this.entry = entry;
        this.sX = sX;
        this.sY = sY;
        this.maskedImageX=convolution(sX);
        this.maskedImageY=convolution(sY);
    }
    

    public Sobel() {
    }

    public Sobel(PGMImage image) {
        this.height=image.getHeight();
        this.width=image.getWidth();
        this.entry=image.toMatrice();
        this.sX=SX;
        this.sY=SY;
        this.maskedImageX=convolution(sX);
        this.maskedImageY=convolution(sY);
    }

    public Sobel(int height, int width, int[][] entry) {
        this.height = height;
        this.width = width;
        this.entry = entry;
        this.sX=SX;
        this.sY=SY;
        this.maskedImageX=convolution(sX);
        this.maskedImageY=convolution(sY);
    }
    
    private int[][] convolution 
            (int  mask [][] )
    {
        int [][] result = new int [this.width][this.height];
        
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                result [i][j]=0;
            }
        }
        
        for (int i = 1; i < this.width-1; i++) {
            for (int j = 1; j < this.height-1; j++) {
                int sum=0;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        int x=mask [k][l];
                        x*=this.entry[i-1+k][j-1+l];
                        sum+= x;
                    }
                }
                result [i][j]=sum;
            }
        }
        
        return result;
    }
    
    private PGMImage toPGM (int [][] matrix)
    {
        PGMImage image= new PGMImage();
        image.setWidth(this.width);
        image.setHeight(this.height);
        image.setCoding(255);
        image.setType("P2");
        int []pixels= new int [width*height];
        int k=0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[k]=matrix[j][i];
                k++;
            }
        }
        image.setPixels(pixels);
        return image;
    }
    
    public PGMImage toPGMX ()
    {
        return this.toPGM(this.maskedImageX);
        
    }
    public PGMImage toPGMY ()
    {
        return this.toPGM(this.maskedImageY);
        
    }
    public PGMImage toPGM ()
    {
        summarize();
        clean(maskedImage);
        return this.toPGM(this.maskedImage);
        
    }
    private void summarize ()
    {
        maskedImage = new int [this.width][this.height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                maskedImage[i][j]=(int) Math.sqrt(maskedImageX[i][j]*maskedImageX[i][j]+maskedImageY[i][j]*maskedImageY[i][j]);
            }
        }
    }
    private void clean (int [][] mat)
    {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (mat[i][j]>255)
                {
                   mat[i][j] = 255;
                }
                else if (mat[i][j]<0)
                {
                   mat[i][j] = 0;
                }
            }
        }
    }
}
