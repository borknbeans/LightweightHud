package borknbeans.lightweighthud.hud;

import borknbeans.lightweighthud.config.HudPosition;
import net.minecraft.client.gui.DrawContext;

import java.util.List;

public class HudHelper {
    DrawContext context;

    HudPosition hudPosition;
    List<HudObject> hudObjects;
    boolean verticallyStack = false;
    boolean centered = false;
    int xOffset;
    int yOffset;

    public HudHelper(DrawContext context, HudPosition hudPosition, List<HudObject> hudObjects, int xOffset, int yOffset) {
        this.context = context;
        this.hudPosition = hudPosition;
        this.hudObjects = hudObjects;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void drawHud() {
        int[] startPosition = hudPosition.getCoordinates();
        int x = startPosition[0] + xOffset;
        int y = startPosition[1] + yOffset;

        if (hudPosition.isOnRight()) {
            if (verticallyStack) {
                x -= getMaxWidth();
            } else {
                x -= getWidth();
            }
        }

        if (hudPosition.isOnMiddleHorizontal()) {
            if (verticallyStack) {
                x -= getMaxWidth() / 2;
            } else {
                x -= getWidth() / 2;
            }
        }

        if (hudPosition.isOnMiddleVertical()) {
            if (verticallyStack) {
                y -= getHeight() / 2;
            } else {
                y -= getMaxHeight() / 2;
            }
        }

        if (hudPosition.isOnBottom()) {
            if (verticallyStack) {
                y -= getHeight();
            } else {
                y -= getMaxHeight();
            }
        }

        for (int i = 0; i < hudObjects.size(); i++) {
            HudObject hudObject = hudObjects.get(i);

            if (centered) {
                if (verticallyStack && hudObject.getWidth() != getMaxWidth()) {
                    x = x + (getMaxWidth() / 2) - (hudObject.getWidth() / 2);
                } else if (!verticallyStack && hudObject.getHeight() != getMaxHeight()) {
                    y = y + (getMaxHeight() / 2) - (hudObject.getHeight() / 2);
                }
            }

            hudObject.draw(context, x, y);

            if (verticallyStack) {
                y += hudObject.getHeight(); // Move down as we draw
            } else {
                x += hudObject.getWidth(); // Move right as we draw
            }
        }
    }

    public int getWidth() { // Gets combined width
        int width = 0;

        for (HudObject hudObject : hudObjects) {
            width += hudObject.getWidth();
        }

        return width;
    }

    public int getHeight() {
        int height = 0;

        for (HudObject hudObject : hudObjects) {
            height += hudObject.getHeight();
        }

        return height;
    }

    public int getMaxWidth() {
        int width = 0;

        for (HudObject hudObject : hudObjects) {
            if (hudObject.getWidth() > width) {
                width = hudObject.getWidth();
            }
        }

        return width;
    }

    public int getMaxHeight() {
        int height = 0;

        for (HudObject hudObject : hudObjects) {
            if (hudObject.getHeight() > height) {
                height = hudObject.getHeight();
            }
        }

        return height;
    }

    public HudHelper setVerticallyStack(boolean verticallyStack) {
        this.verticallyStack = verticallyStack;
        return this;
    }

    public HudHelper setCentered(boolean centered) {
        this.centered = centered;
        return this;
    }
}
