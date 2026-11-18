import java.awt.Color;
import java.util.Scanner;

/**
 * ImageApp allows users to change colors, rotate images,
 * and insert smaller images onto larger images.
 */
public class ImageApp
{
    /** Runs the ImageApp menu and handles user choices. */
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        // Ask the user which background image to use
        System.out.print("Enter background filename (example: beach.jpg): ");
        String filename = input.nextLine();

        // Load the background image from the lib folder
        Picture image = new Picture("lib/" + filename);

        int choice = -1;

        // Keep displaying the menu until the user exits
        while (choice != 0)
        {
            // Display all available image transformations
            System.out.println("\n===== IMAGE APP MENU =====");
            System.out.println("1. Display image");
            System.out.println("2. Change color");
            System.out.println("3. Negative image");
            System.out.println("4. Grayscale");
            System.out.println("5. Rotate 180 degrees");
            System.out.println("6. Rotate 90 degrees");
            System.out.println("7. Rotate -90 degrees");
            System.out.println("8. Insert image");
            System.out.println("9. Insert portion of image");
            System.out.println("10. Reset original image");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            // Get the user's menu choice
            choice = input.nextInt();
            input.nextLine();

            // Display the current image
            if (choice == 1)
            {
                image.explore();
            }

            // Change the RGB values of the image
            else if (choice == 2)
            {
                changeColor(image.getPixels2D());
                image.explore();
            }

            // Create a photographic negative
            else if (choice == 3)
            {
                negativeColor(image.getPixels2D());
                image.explore();
            }

            // Convert the image to grayscale
            else if (choice == 4)
            {
                grayscale(image.getPixels2D());
                image.explore();
            }

            // Handle all three rotation options
            else if (choice >= 5 && choice <= 7)
            {
                int angle = 180;

                // Determine the angle based on the user's choice
                if (choice == 6)
                {
                    angle = 90;
                }
                else if (choice == 7)
                {
                    angle = -90;
                }

                // Rotate the pixels using matrix multiplication
                Pixel[][] rotated = rotate(image.getPixels2D(), angle);

                // Create an image with the new dimensions
                image = new Picture(rotated.length,
                                    rotated[0].length);

                Pixel[][] pixels = image.getPixels2D();

                // Copy the rotated pixels into the new image
                for (int row = 0; row < rotated.length; row++)
                {
                    for (int col = 0; col < rotated[row].length; col++)
                    {
                        pixels[row][col].setColor(
                            rotated[row][col].getColor());
                    }
                }

                // Display the rotated image
                image.explore();
            }

            // Insert a full image or a portion of an image
            else if (choice == 8 || choice == 9)
            {
                // Ask which small image to insert
                System.out.print("Enter small image filename: ");
                String smallFile = input.nextLine();

                // Load the small image from the lib2 folder
                Picture smallImage = new Picture("lib2/" + smallFile);

                // Ask where the small image should be placed
                System.out.print("Enter destination row: ");
                int row = input.nextInt();

                System.out.print("Enter destination column: ");
                int col = input.nextInt();

                Pixel[][] small = smallImage.getPixels2D();

                // Insert the entire small image
                if (choice == 8)
                {
                    insert(image.getPixels2D(), small, row, col);
                }

                // Insert only a selected portion of the small image
                else
                {
                    System.out.print("Enter portion height: ");
                    int height = input.nextInt();

                    System.out.print("Enter portion width: ");
                    int width = input.nextInt();

                    insertPortion(image.getPixels2D(), small,
                                  row, col, height, width);
                }

                // Display the image after insertion
                image.explore();
            }

            // Reset the image back to its original state
            else if (choice == 10)
            {
                image = new Picture("lib/" + filename);
                image.explore();
            }

            // Handle invalid menu choices
            else if (choice != 0)
            {
                System.out.println("Invalid choice.");
            }
        }

        // Close the scanner when the user exits
        input.close();
        System.out.println("ImageApp closed.");
    }

    /**
     * Changes the colors of an image from RGB to BRG.
     * @param pixels the pixels of the image
     */
    public static void changeColor(Pixel[][] pixels)
    {
        // Loop through every pixel in the image
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[row].length; col++)
            {
                Pixel pixel = pixels[row][col];

                // Get the original RGB values
                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();

                // Rearrange the RGB values
                pixel.setColor(new Color(blue, red, green));
            }
        }
    }

    /**
     * Creates a photographic negative of an image.
     * @param pixels the pixels of the image
     */
    public static void negativeColor(Pixel[][] pixels)
    {
        // Loop through every pixel
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[row].length; col++)
            {
                Pixel pixel = pixels[row][col];

                // Get the original color values
                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();

                // Invert each color by subtracting it from 255
                pixel.setColor(new Color(
                    255 - red, 255 - green, 255 - blue));
            }
        }
    }

    /**
     * Converts an image to grayscale.
     * @param pixels the pixels of the image
     */
    public static void grayscale(Pixel[][] pixels)
    {
        // Loop through every pixel
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[row].length; col++)
            {
                Pixel pixel = pixels[row][col];

                // Calculate the average of the RGB values
                int average = (pixel.getRed() +
                               pixel.getGreen() +
                               pixel.getBlue()) / 3;

                // Set all three colors to the same value
                pixel.setColor(new Color(
                    average, average, average));
            }
        }
    }

    /**
     * Rotates an image using matrix multiplication.
     * @param pixels the original image pixels
     * @param angle the rotation angle in degrees
     * @return the rotated image pixels
     */
    public static Pixel[][] rotate(Pixel[][] pixels, int angle)
    {
        // Get the original dimensions of the image
        int height = pixels.length;
        int width = pixels[0].length;

        Picture rotatedImage;

        // Swap the height and width for 90 degree rotations
        if (angle == 90 || angle == -90)
        {
            rotatedImage = new Picture(width, height);
        }

        // Keep the same dimensions for a 180 degree rotation
        else if (angle == 180)
        {
            rotatedImage = new Picture(height, width);
        }

        // Reject unsupported rotation angles
        else
        {
            throw new IllegalArgumentException("Invalid rotation angle");
        }

        Pixel[][] rotatedPixels = rotatedImage.getPixels2D();

        // Create the rotation matrix for the selected angle
        Matrix2by2 matrix = new Matrix2by2(0, 0, 0, 0);
        matrix.setRotationMatrix(angle);

        // Loop through every original pixel
        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                // Store the pixel coordinates in a vector
                Vector1by2 position = new Vector1by2(row, col);

                // Multiply the vector by the rotation matrix
                Vector1by2 rotated = matrix.multiply(position);

                // Get the new pixel coordinates
                int newRow = rotated.getRow();
                int newCol = rotated.getCol();

                // Adjust coordinates to prevent negative indexes
                if (angle == 180)
                {
                    newRow += height - 1;
                    newCol += width - 1;
                }
                else if (angle == 90)
                {
                    newRow += width - 1;
                }
                else if (angle == -90)
                {
                    newCol += height - 1;
                }

                // Copy the original color to the rotated position
                rotatedPixels[newRow][newCol].setColor(
                    pixels[row][col].getColor());
            }
        }

        // Return the rotated pixel array
        return rotatedPixels;
    }

    /**
     * Inserts an entire small image onto a larger image.
     * @param large the background image pixels
     * @param small the small image pixels
     * @param startRow the destination row
     * @param startCol the destination column
     */
    public static void insert(Pixel[][] large, Pixel[][] small,
                              int startRow, int startCol)
    {
        // Use the full height and width of the small image
        insertPortion(large, small, startRow, startCol,
                      small.length, small[0].length);
    }

    /**
     * Inserts a portion of a small image without its white background.
     * @param large the background image pixels
     * @param small the small image pixels
     * @param startRow the destination row
     * @param startCol the destination column
     * @param height the height of the inserted portion
     * @param width the width of the inserted portion
     */
    public static void insertPortion(Pixel[][] large, Pixel[][] small,
                                    int startRow, int startCol,
                                    int height, int width)
    {
        // Loop through the selected portion of the small image
        for (int row = 0; row < height && row < small.length; row++)
        {
            for (int col = 0; col < width && col < small[row].length; col++)
            {
                // Calculate the destination position
                int newRow = row + startRow;
                int newCol = col + startCol;

                // Get the color of the small image pixel
                Color color = small[row][col].getColor();

                // Check that the pixel is inside the background
                // and skip white pixels to remove the white background
                if (newRow >= 0 && newRow < large.length &&
                    newCol >= 0 && newCol < large[0].length &&
                    !color.equals(Color.WHITE))
                {
                    // Copy the small image pixel onto the background
                    large[newRow][newCol].setColor(color);
                }
            }
        }
    }
}