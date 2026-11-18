/*
  ImageApp: 
 */

import java.awt.Color;

public class ImageApp
{
  public static void main(String[] args)
  {

    // use any file from the lib folder
    String pictureFile = "lib/beach.jpg";

    // Get an image, get 2d array of pixels, show a color of a pixel, and display the image
    Picture origImg = new Picture(pictureFile);
    Pixel[][] origPixels = origImg.getPixels2D();
    System.out.println(origPixels[0][0].getColor());
    origImg.explore();

    // Image #1 Using the original image and pixels, recolor an image by changing the RGB color of each Pixel
    Picture recoloredImg = new Picture(pictureFile);
    Pixel[][] recoloredPixels = recoloredImg.getPixels2D();

    for (int row = 0; row < recoloredPixels.length; row++)
    {
        for (int col = 0; col < recoloredPixels[row].length; col++)
        {
            Pixel pixel = recoloredPixels[row][col];

            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();

            // Swap RGB to BRG
            pixel.setColor(new Color(blue, red, green));
        }
    }

    // Display the recolored image
    recoloredImg.explore();

    // Image #2 Using the original image and pixels, create a photographic negative of the image
    Picture negImg = new Picture(pictureFile);
    Pixel[][] negPixels = negImg.getPixels2D();

    for (int row = 0; row < negPixels.length; row++)
    {
        for (int col = 0; col < negPixels[row].length; col++)
        {
            Pixel pixel = negPixels[row][col];

            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();

            // Invert colors
            pixel.setColor(new Color(255 - red, 255 - green, 255 - blue));
        }
    }

    // Display the negative image
    negImg.explore();

    // Image #3 Using the original image and pixels, create a grayscale version of the image
    Picture grayscaleImg = new Picture(pictureFile);
    Pixel[][] grayscalePixels = grayscaleImg.getPixels2D();

    for (int row = 0; row < grayscalePixels.length; row++)
    {
        for (int col = 0; col < grayscalePixels[row].length; col++)
        {
            Pixel pixel = grayscalePixels[row][col];

            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();

            // Calculate average for grayscale
            int average = (red + green + blue) / 3;
            pixel.setColor(new Color(average, average, average));
        }
    }

    // Display the grayscale image
    grayscaleImg.explore();

    // Image #4 Using the original image and pixels, rotate it 180 degrees
    Picture upsidedownImage = new Picture(pictureFile);
    Pixel[][] upsideDownPixels = upsidedownImage.getPixels2D();

    // Rotate the image 180 degrees using matrix multiplication
    Matrix2by2 rotate180 = new Matrix2by2(-1, 0, 0, -1);

    int height = origPixels.length;
    int width = origPixels[0].length;

    for (int row = 0; row < height; row++)
    {
        for (int col = 0; col < width; col++)
        {
            // Convert pixel position into a vector
            Vector1by2 position = new Vector1by2(row, col);

            // Rotate using the matrix
            Vector1by2 rotated = Matrix2by2.multiply(position, rotate180);

            // Adjust coordinates to fit inside the image
            int newRow = rotated.row + height - 1;
            int newCol = rotated.col + width - 1;

            // Copy original pixel color to rotated position
            upsideDownPixels[newRow][newCol].setColor(
                origPixels[row][col].getColor()
            );
        }
    }

    // Display the rotated image
    upsidedownImage.explore();


    // Image #5 Rotate image 90 degrees counterclockwise
    Picture rotateImg = new Picture(width, height);
    Pixel[][] rotatePixels = rotateImg.getPixels2D();

    Matrix2by2 rotate90 = new Matrix2by2(0, 1, -1, 0);

    for (int row = 0; row < height; row++)
    {
        for (int col = 0; col < width; col++)
        {
            Vector1by2 position = new Vector1by2(row, col);

            // Rotate using matrix multiplication
            Vector1by2 rotated = Matrix2by2.multiply(position, rotate90);

            // Adjust coordinates to prevent negative indexes
            int newRow = rotated.row + width - 1;
            int newCol = rotated.col;

            // Copy pixel color
            rotatePixels[newRow][newCol].setColor(
                origPixels[row][col].getColor()
            );
        }
    }

    // Display the rotated image
    rotateImg.explore();

    // Image #6 Rotate image -90 degrees
    Picture rotateImg2 = new Picture(width, height);
    Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();

    Matrix2by2 rotateNeg90 = new Matrix2by2(0, -1, 1, 0);

    for (int row = 0; row < height; row++)
    {
        for (int col = 0; col < width; col++)
        {
            Vector1by2 position = new Vector1by2(row, col);
            Vector1by2 rotated = Matrix2by2.multiply(position, rotateNeg90);

            int newRow = rotated.row;
            int newCol = rotated.col + height - 1;

            rotatePixels2[newRow][newCol].setColor(
                origPixels[row][col].getColor()
            );
        }
    }
    // Display the rotated image
    rotateImg2.explore();

    // Final Image: Add a small image to a larger one

    Picture finalImg = new Picture(pictureFile);
    Picture smallImg = new Picture("lib2/bird.png");

    Pixel[][] finalPixels = finalImg.getPixels2D();
    Pixel[][] smallPixels = smallImg.getPixels2D();

    // Insert the bird onto the beach
    for (int row = 0; row < smallPixels.length; row++)
    {
        for (int col = 0; col < smallPixels[row].length; col++)
        {
            int newRow = row + 50;
            int newCol = col + 100;

            Color color = smallPixels[row][col].getColor();

            if (newRow < finalPixels.length &&
                newCol < finalPixels[0].length &&
                !color.equals(Color.WHITE))
            {
                finalPixels[newRow][newCol].setColor(color);
            }
        }
    }
    // Display the final image
    finalImg.explore();

    // for testing  2D algorithms
    int[][] test1 = { { 1, 2, 3, 4 },
        { 5, 6, 7, 8 },
        { 9, 10, 11, 12 },
        { 13, 14, 15, 16 } };
    int[][] test2 = new int[4][4];
  }
}