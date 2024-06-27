package borknbeans.lightweighthud.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class HudItem implements HudObject {
    private final int ITEM_SIZE = 16;

    private ItemStack stack;

    public HudItem(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public int getWidth() {
        return ITEM_SIZE;
    }

    @Override
    public int getHeight() {
        return getWidth();
    }

    @Override
    public void draw(DrawContext context, int x, int y) {
        context.drawItem(stack, x, y);
    }
}
