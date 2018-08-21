package rendering.Images;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage sheet;

    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public BufferedImage crop(int x, int y, int width, int height) {
        //width and height will equal the width and height of
        //normal sprites in the sheet
        return sheet.getSubimage(x, y, width, height);
    }
}
