/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerConnection;

/**
 *
 * @author kevin
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements Serializable {

    private BufferedImage image;

    public ImagePanel() {
    }

    public ImagePanel(String str) {
        try {
            image = ImageIO.read(new File(str));
            repaint();
        } catch (IOException ex) {
            // handle exception...
        }
    }

    public ImagePanel(String str, int width, int height) {
        this(str);
        ImageIcon myIcon2 = new ImageIcon();
        if (image == null) {
            return;
        }
        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        image = toBufferedImage(newimg);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters

    }

    public void setImage(String str, int width, int height) {
        image = null;
        try {
            image = ImageIO.read(new File(str));
        } catch (IOException ex) {
            return;
            // handle exception...
        }
        ImageIcon myIcon2 = new ImageIcon();
        if (image == null) {
            return;
        }
        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        image = toBufferedImage(newimg);
        repaint();
    }
    public BufferedImage getImage(){
        return image;
    }

    public static BufferedImage toBufferedImage(Image image) {
        image = new ImageIcon(image).getImage();

        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }
}

