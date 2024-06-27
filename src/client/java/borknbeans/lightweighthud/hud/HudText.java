package borknbeans.lightweighthud.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class HudText implements HudObject {
    private String text;

    public HudText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getWidth() {
        return MinecraftClient.getInstance().textRenderer.getWidth(text);
    }

    @Override
    public int getHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    @Override
    public void draw(DrawContext context, int x, int y) {
        context.drawText(MinecraftClient.getInstance().textRenderer, text, x, y, 0xFFFFFF, true);
    }
}
