package borknbeans.lightweighthud.mixin.client;

import borknbeans.lightweighthud.config.GuiPosition;
import borknbeans.lightweighthud.config.LightweightHUDConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    private final int TEXT_SPACING = 2;

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void renderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {

        if (LightweightHUDConfig.showHeldItem) { // Held Item
            renderHeldItem(context);
        }
    }

    private void renderHeldItem(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ItemStack stack = client.player.getMainHandStack();

        if (stack.isEmpty()) {
            return;
        }

        GuiPosition guiPosition = LightweightHUDConfig.heldItemGuiPosition;
        int[] guiCoordinates = guiPosition.getCoordinates(client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), LightweightHUDConfig.heldItemXOffset, LightweightHUDConfig.heldItemYOffset);

        if (isBlock(stack) && LightweightHUDConfig.drawBlockCount) { // Draw Block count
            int itemCount = howManyOfThisItem(stack);
            String count = String.valueOf(itemCount);

            int textWidth = client.textRenderer.getWidth(count);
            guiCoordinates[0] = (guiPosition == GuiPosition.TOP_LEFT || guiPosition == GuiPosition.BOTTOM_LEFT) ? guiCoordinates[0] : guiCoordinates[0] - textWidth - TEXT_SPACING;

            context.drawText(client.textRenderer, count, guiCoordinates[0] + 16 + TEXT_SPACING, guiCoordinates[1] + 4, 0xFFFFFF, true);
        } else if (isTool(stack) && LightweightHUDConfig.drawToolDurability) { // Draw tool durability
            int maxDurability = stack.getMaxDamage();
            int remainingDurability = maxDurability - stack.getDamage();
            int durabilityPercentage = (int)(((float) remainingDurability / maxDurability) * 100);

            String durability = "";
            if (LightweightHUDConfig.drawToolDurabilityAsPercentage) { // Draw tool durability as percentage
                durability = durabilityPercentage + "%";
            } else {
                durability = remainingDurability + "/" + maxDurability;
            }

            int textWidth = client.textRenderer.getWidth(durability);
            guiCoordinates[0] = (guiPosition == GuiPosition.TOP_LEFT || guiPosition == GuiPosition.BOTTOM_LEFT) ? guiCoordinates[0] : guiCoordinates[0] - textWidth - TEXT_SPACING;

            context.drawText(client.textRenderer, durability, guiCoordinates[0] + 16 + TEXT_SPACING, guiCoordinates[1] + 4, decideHeldItemDamageColor(durabilityPercentage), true);

            // Tools have 2 pixel space at the top
            guiCoordinates[1] -= 1;
        } else {
            // Dont render
            return;
        }

        context.drawItem(stack, guiCoordinates[0], guiCoordinates[1]);
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

    private int decideHeldItemDamageColor(int durabilityPercentage) {
        if (durabilityPercentage < LightweightHUDConfig.heldItemVeryHighDamageThreshold) {
            return LightweightHUDConfig.heldItemVeryHighDamageColor;
        } else if (durabilityPercentage < LightweightHUDConfig.heldItemHighDamageThreshold) {
            return LightweightHUDConfig.heldItemHighDamageColor;
        } else if (durabilityPercentage < LightweightHUDConfig.heldItemSlightDamageThreshold) {
            return LightweightHUDConfig.heldItemSlightDamageColor;
        }

        return 0x000000;
    }

    private void renderNavigation(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

        // client.player.getPos();
        // client.player.getMovementDirection() ?
        // client.player.getChunkPos();
    }
}
