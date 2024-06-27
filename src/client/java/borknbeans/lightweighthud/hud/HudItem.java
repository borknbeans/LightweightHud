package borknbeans.lightweighthud.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class HudItem implements HudObject {
    private final int WIDTH = 16;
    private final int HEIGHT = 16;

    private ItemStack stack;

    public HudItem(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(DrawContext context, int x, int y) {
        context.drawItem(stack, x, y);
    }
}
