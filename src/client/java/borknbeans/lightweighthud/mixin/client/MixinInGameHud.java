package borknbeans.lightweighthud.mixin.client;

import borknbeans.lightweighthud.GuiPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    private final int TEXT_SPACING = 2;

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void renderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        GuiPosition guiPosition = GuiPosition.TOP_RIGHT;
        MinecraftClient client = MinecraftClient.getInstance();

        ItemStack stack = client.player.getMainHandStack();

        if (!stack.isEmpty()) {
            int[] guiCoordinates = guiPosition.getCoordinates(client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());

            if (isBlock(stack)) {
                int itemCount = howManyOfThisItem(stack);
                String count = String.valueOf(itemCount);

                int textWidth = client.textRenderer.getWidth(count);
                guiCoordinates[0] = guiCoordinates[0] == 0 ? 0 : guiCoordinates[0] - textWidth - TEXT_SPACING;

                context.drawText(client.textRenderer, count, guiCoordinates[0] + 16 + TEXT_SPACING, guiCoordinates[1] + 4, 0xFFFFFF, true);
            }

            context.drawItem(stack, guiCoordinates[0], guiCoordinates[1]);
        }
    }

    private int howManyOfThisItem(ItemStack stack) {
        int count = 0;

        MinecraftClient client = MinecraftClient.getInstance();
        PlayerInventory inventory = client.player.getInventory();

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack s = inventory.getStack(i);

            if (s.getItem() == stack.getItem()) {
                count += s.getCount();
            }
        }

        return count;
    }

    private boolean isBlock(ItemStack stack) {
        return stack.getItem() instanceof BlockItem;
    }

    private boolean isTool(ItemStack stack) {
        return stack.getItem() instanceof ToolItem;
    }
}
