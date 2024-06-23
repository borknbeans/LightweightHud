package borknbeans.lightweighthud.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void renderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        // GuiPosition guiPosition = GuiPosition.TOP_LEFT;
        MinecraftClient client = MinecraftClient.getInstance();

        ItemStack stack = client.player.getMainHandStack();

        if (!stack.isEmpty()) {
            // TODO: Count all of the same item in the inventory
            int itemCount = stack.getCount();

            // TODO: Change drawing coordinates and what is drawn depending on type of stack in hand i.e. tool vs block
            int[] guiCoordinates = new int[] {0, 0}; // guiPosition.getCoordinates(client.getWindow().getWidth(), client.getWindow().getHeight());
            context.drawItem(stack, guiCoordinates[0], guiCoordinates[1]);
            context.drawText(client.textRenderer, String.valueOf(itemCount), 20, 4, 0xFFFFFF, true);
        }
    }
}

// TODO: Move ENUM outside of Mixin class
/*
enum GuiPosition {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_RIGHT,
    BOTTOM_LEFT;

    public int[] getCoordinates(int screenWidth, int screenHeight) {
        int x = 0, y = 0;

        switch (this) {
            case TOP_LEFT:
                x = 0;
                y = 0;
                break;
            case TOP_RIGHT:
                x = screenWidth;
                y = 0;
                break;
            case BOTTOM_RIGHT:
                x = screenWidth;
                y = screenHeight;
                break;
            case BOTTOM_LEFT:
                x = 0;
                y = screenHeight;
                break;
        }

        return new int[] {x, y};
    }
}
*/
