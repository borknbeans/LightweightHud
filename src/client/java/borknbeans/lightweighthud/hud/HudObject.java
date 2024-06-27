package borknbeans.lightweighthud.hud;

import net.minecraft.client.gui.DrawContext;

public interface HudObject {
    int getWidth();
    int getHeight();

    void draw(DrawContext context, int x, int y);
}
