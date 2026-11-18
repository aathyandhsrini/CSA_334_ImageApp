import java.awt.Color;
import java.util.Scanner;

/** Image operations for the PLTW 3.3.4 Image App gallery walk. */
public class ImageApp {
    private static final String DEFAULT_BACKGROUND = "lib/beach.jpg";
    private static final String DEFAULT_OVERLAY = "lib2/cat.png";

    /** Make an image more red and less green/blue. */
    public static Pixel[][] changeColor(Pixel[][] source) {
        return changeColor(source, 40, -20, -20);
    }
    /** Add RGB offsets to each pixel; clamp components to 0..255. */
    public static Pixel[][] changeColor(Pixel[][] source, int redOffset, int greenOffset, int blueOffset) {
        Pixel[][] result = copyPixels(source);
        for (int r = 0; r < source.length; r++) {
            for (int c = 0; c < source[0].length; c++) {
                Color old = source[r][c].getColor();
                result[r][c].setColor(new Color(
                    clamp(old.getRed() + redOffset),
                    clamp(old.getGreen() + greenOffset),
                    clamp(old.getBlue() + blueOffset)));
            }
        }
        return result;
    }
    /** Invert each RGB component: new channel = 255 - old channel. */
    public static Pixel[][] negativeColor(Pixel[][] source) {
        Pixel[][] result = copyPixels(source);
        for (int r = 0; r < source.length; r++) {
            for (int c = 0; c < source[0].length; c++) {
                Color old = source[r][c].getColor();
                result[r][c].setColor(new Color(255 - old.getRed(),
                    255 - old.getGreen(), 255 - old.getBlue()));
            }
        }
        return result;
    }
    /** Luminance-weighted grayscale; all three output components equal. */
    public static Pixel[][] grayscale(Pixel[][] source) {
        Pixel[][] result = copyPixels(source);
        for (int r = 0; r < source.length; r++) {
            for (int c = 0; c < source[0].length; c++) {
                Color old = source[r][c].getColor();
                int gray = clamp((int) Math.round(0.299 * old.getRed()
                    + 0.587 * old.getGreen() + 0.114 * old.getBlue()));
                result[r][c].setColor(new Color(gray, gray, gray));
            }
        }
        return result;
    }
    /** Rotate using matrix multiplication. Positive angles turn counterclockwise. */
    public static Pixel[][] rotate(Pixel[][] source, int degrees) {
        checkPixels(source);
        Matrix2by2 matrix = Matrix2by2.rotation(degrees);
        int height = source.length;
        int width = source[0].length;
        int angle = ((degrees % 360) + 360) % 360;

        // Quarter turns swap the image's height and width.
        int newHeight = (angle == 90 || angle == 270) ? width : height;
        int newWidth = (angle == 90 || angle == 270) ? height : width;
        Pixel[][] result = new Picture(newHeight, newWidth).getPixels2D();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Vector1by2 point = new Vector1by2(row, col);
                Vector1by2 rotated = Matrix2by2.multiply(point, matrix);
                int newRow = (int) rotated.getRow();
                int newCol = (int) rotated.getCol();

                // Move negative coordinates back inside the image.
                if (angle == 90 || angle == 180) newRow += newHeight - 1;
                if (angle == 180 || angle == 270) newCol += newWidth - 1;
                result[newRow][newCol].setColor(source[row][col].getColor());
            }
        }
        return result;
    }
    public static Pixel[][] rotate180(Pixel[][] source) { return rotate(source, 180); }
    public static Pixel[][] rotate90(Pixel[][] source) { return rotate(source, 90); }
    public static Pixel[][] rotateMinus90(Pixel[][] source) { return rotate(source, -90); }

    /** Paste the entire overlay, including white pixels, at a specified location. */
    public static Pixel[][] insert(Pixel[][] background, Pixel[][] overlay, int startRow, int startCol) {
        return insert(background, overlay, startRow, startCol, false, false);
    }
    /** Paste with white/transparent background removal. */
    public static Pixel[][] insertWithoutWhite(Pixel[][] background, Pixel[][] overlay,
                                               int startRow, int startCol) {
        return insert(background, overlay, startRow, startCol, true, false);
    }
    /** Paste with 50% overlay/background blending, ignoring white/transparent pixels. */
    public static Pixel[][] insertBlended(Pixel[][] background, Pixel[][] overlay,
                                          int startRow, int startCol) {
        return insert(background, overlay, startRow, startCol, true, true);
    }
    /**
     * Paste an overlay into a background without changing either source.
     * If removeWhite is true, near-white and transparent pixels are skipped.
     * If blend is true, visible pixels are mixed 50/50 with the destination.
     */
    public static Pixel[][] insert(Pixel[][] background, Pixel[][] overlay,
                                   int startRow, int startCol, boolean removeWhite, boolean blend) {
        return insertPortion(background, overlay, startRow, startCol,
            0, 0, overlay.length, overlay[0].length, removeWhite, blend);
    }
    /** Paste only a rectangular portion of an overlay (clipped at both image borders). */
    public static Pixel[][] insertPortion(Pixel[][] background, Pixel[][] overlay,
                                         int startRow, int startCol, int fromRow, int fromCol,
                                         int portionHeight, int portionWidth,
                                         boolean removeWhite, boolean blend) {
        checkPixels(background);
        checkPixels(overlay);
        if (portionHeight < 0 || portionWidth < 0)
            throw new IllegalArgumentException("Portion dimensions cannot be negative");
        Pixel[][] output = copyPixels(background);
        for (int r = 0; r < portionHeight; r++) {
            int srcRow = fromRow + r, dstRow = startRow + r;
            if (srcRow < 0 || srcRow >= overlay.length || dstRow < 0 || dstRow >= output.length)
                continue;
            for (int c = 0; c < portionWidth; c++) {
                int srcCol = fromCol + c, dstCol = startCol + c;
                if (srcCol < 0 || srcCol >= overlay[0].length || dstCol < 0 || dstCol >= output[0].length)
                    continue;
                Pixel overlayPixel = overlay[srcRow][srcCol];
                Color foreground = overlayPixel.getColor();
                // Transparent PNG pixels and near-white backgrounds are not pasted.
                if (removeWhite && (overlayPixel.getAlpha() < 20 || isWhite(foreground))) continue;
                if (blend) {
                    Color behind = output[dstRow][dstCol].getColor();
                    foreground = new Color((foreground.getRed() + behind.getRed()) / 2,
                        (foreground.getGreen() + behind.getGreen()) / 2,
                        (foreground.getBlue() + behind.getBlue()) / 2);
                }
                output[dstRow][dstCol].setColor(foreground);
            }
        }
        return output;
    }
    /** Recolor visible overlay pixels, retaining white/transparent mask pixels. */
    public static Pixel[][] recolorOverlay(Pixel[][] overlay, Color chosenColor) {
        Pixel[][] output = copyPixels(overlay);
        for (int r = 0; r < overlay.length; r++) {
            for (int c = 0; c < overlay[0].length; c++) {
                if (overlay[r][c].getAlpha() < 20) {
                    // Fresh Picture buffers are RGB-only, so encode transparent areas as white.
                    output[r][c].setColor(Color.WHITE);
                } else if (!isWhite(overlay[r][c].getColor())) {
                    output[r][c].setColor(chosenColor);
                }
            }
        }
        return output;
    }
    private static boolean isWhite(Color color) {
        return color.getRed() >= 245 && color.getGreen() >= 245 && color.getBlue() >= 245;
    }
    private static int clamp(int value) { return Math.max(0, Math.min(255, value)); }
    private static void checkPixels(Pixel[][] pixels) {
        if (pixels == null || pixels.length == 0 || pixels[0] == null || pixels[0].length == 0)
            throw new IllegalArgumentException("Image must have at least one pixel");
        int width = pixels[0].length;
        for (Pixel[] row : pixels)
            if (row == null || row.length != width)
                throw new IllegalArgumentException("All image rows must have the same width");
    }
    private static Pixel[][] copyPixels(Pixel[][] source) {
        checkPixels(source);
        Pixel[][] result = new Picture(source.length, source[0].length).getPixels2D();
        for (int r = 0; r < source.length; r++)
            for (int c = 0; c < source[0].length; c++)
                result[r][c].setColor(source[r][c].getColor());
        return result;
    }
    /** Convert a Pixel array into a Picture for explore(), show(), or write(). */
    public static Picture pictureFromPixels(Pixel[][] pixels) {
        checkPixels(pixels);
        Picture picture = new Picture(pixels.length, pixels[0].length);
        Pixel[][] result = picture.getPixels2D();
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                result[row][col].setColor(pixels[row][col].getColor());
            }
        }
        return picture;
    }
    /** Print and display one generated image. */
    private static void display(String title, Pixel[][] pixels) {
        System.out.println(title + " (" + pixels[0].length + " x " + pixels.length + ")");
        pictureFromPixels(pixels).explore();
    }
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try { return Integer.parseInt(line); }
            catch (NumberFormatException e) { System.out.println("Please enter an integer."); }
        }
    }
    /** Interactive UI allows repeated transformations and inserts. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String backgroundFile = args.length > 0 ? args[0] : DEFAULT_BACKGROUND;
            String overlayFile = args.length > 1 ? args[1] : DEFAULT_OVERLAY;
            Pixel[][] current = new Picture(backgroundFile).getPixels2D();
            Pixel[][] overlay = new Picture(overlayFile).getPixels2D();
            System.out.println("Image App - original pixel color: " + current[0][0].getColor());
            System.out.println("Background: " + backgroundFile + "; overlay: " + overlayFile);
            while (true) {
                System.out.println("\n1 Change color  2 Negative  3 Grayscale");
                System.out.println("4 Rotate 180  5 Rotate 90 CCW  6 Rotate 90 CW");
                System.out.println("7 Insert overlay  8 Insert without white  9 Blend overlay");
                System.out.println("10 Recolor overlay and insert  11 Insert overlay portion");
                System.out.println("12 View current  13 Reset background  0 Exit");
                int choice = readInt(scanner, "Choose: ");
                if (choice == 0) break;
                int row, col;
                switch (choice) {
                    case 1:
                        current = changeColor(current, readInt(scanner, "Red offset: "),
                            readInt(scanner, "Green offset: "), readInt(scanner, "Blue offset: "));
                        break;
                    case 2: current = negativeColor(current); break;
                    case 3: current = grayscale(current); break;
                    case 4: current = rotate(current, 180); break;
                    case 5: current = rotate(current, 90); break;
                    case 6: current = rotate(current, -90); break;
                    case 7: case 8: case 9: case 10: case 11:
                        row = readInt(scanner, "Destination top row: ");
                        col = readInt(scanner, "Destination left column: ");
                        if (choice == 7) current = insert(current, overlay, row, col);
                        else if (choice == 8) current = insertWithoutWhite(current, overlay, row, col);
                        else if (choice == 9) current = insertBlended(current, overlay, row, col);
                        else if (choice == 10) {
                            Color color = new Color(clamp(readInt(scanner, "New red (0-255): ")),
                                clamp(readInt(scanner, "New green (0-255): ")),
                                clamp(readInt(scanner, "New blue (0-255): ")));
                            current = insertWithoutWhite(current, recolorOverlay(overlay, color), row, col);
                        } else {
                            int fromRow = readInt(scanner, "Overlay start row: ");
                            int fromCol = readInt(scanner, "Overlay start col: ");
                            int h = readInt(scanner, "Portion height: ");
                            int w = readInt(scanner, "Portion width: ");
                            current = insertPortion(current, overlay, row, col,
                                fromRow, fromCol, h, w, true, false);
                        }
                        break;
                    case 12: display("Current image", current); continue;
                    case 13: current = new Picture(backgroundFile).getPixels2D(); break;
                    default: System.out.println("Unknown menu choice."); continue;
                }
                display("Updated image", current);
            }
        }
    }
}
