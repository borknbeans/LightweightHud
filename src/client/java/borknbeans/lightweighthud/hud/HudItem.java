package borknbeans.lightweighthud.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;

public class HudItem implements HudObject {
    private final int ITEM_SIZE = 16;

    private Item item;

    public HudItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public int getWidth() {
        return ITEM_SIZE;
    }

    @Override
    public int getHeight() {
        return ITEM_SIZE;
    }

    @Override
    public void draw(DrawContext context, int x, int y) {

    }
}
