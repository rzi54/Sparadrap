package fr.luzi.utilities.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

public class DashedBorder extends AbstractBorder {

    /**
	 * Cette classe permet de créer des uune bordure en pointillés.
	 */
	private static final long serialVersionUID = 1259481345162627644L;
	private int dashWidth;
    private int dashGap;
    private Color color;

    public DashedBorder(int dashWidth, int dashGap, Color color) {
        this.dashWidth = dashWidth;
        this.dashGap = dashGap;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);

        BufferedImage dashImage = new BufferedImage(dashWidth + dashGap, 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D dashGraphics = dashImage.createGraphics();
        dashGraphics.setColor(color);
        dashGraphics.drawLine(0, 1, dashWidth, 1);

        for (int xPos = x; xPos < x + width; xPos += dashWidth + dashGap) {
            g2d.drawImage(dashImage, xPos, y, null);
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(1, 1, 1, 1);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = 1;
        return insets;
    }

    public static void setDashedBorder(JComponent component, int dashWidth, int dashGap, Color color) {
        Border border = new DashedBorder(dashWidth, dashGap, color);
        component.setBorder(border);
    }
}