package borknbeans.lightweighthud.hud;

import borknbeans.lightweighthud.config.HudPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.List;

public class HudHelper {
    DrawContext context;

    HudPosition hudPosition;
    HudObject[] hudObjects;
    int xOffset; // TODO: Use
    int yOffset;

    public HudHelper(DrawContext context, HudPosition hudPosition, HudObject[] hudObjects) {
        this.context = context;
        this.hudPosition = hudPosition;
        this.hudObjects = hudObjects;
    }

    public void drawHud() {
        MinecraftClient client = MinecraftClient.getInstance();

        int[] startPosition = hudPosition.getCoordinates();
        int x = startPosition[0];
        int y = startPosition[1];

        if (hudPosition.isOnRight()) { // Shift x position over the entire width
            x -= getWidth();
        }

        // TODO: Account for middle and move back by half?
        if (hudPosition.isOnMiddleHorizontal()) {
            x -= getWidth() / 2;
        }

        if (hudPosition.isOnBottom()) {
            y -= getHeight();
        }

        for (int i = 0; i < hudObjects.length; i++) {
            HudObject hudObject = hudObjects[i];

            hudObject.draw(context, x, y);
            x += hudObject.getWidth();
        }
    }

    public int getWidth() { // Gets combined width
        int width = 0;

        for (HudObject hudObject : hudObjects) {
            width += hudObject.getWidth();
        }

        return width;
    }

    public int getHeight() { // Gets biggest height value TODO: Maybe change this
        int height = 0;

        for (HudObject hudObject : hudObjects) {
            if (hudObject.getHeight() > height) {
                height = hudObject.getHeight();
            }
        }

        return height;
    }
}
