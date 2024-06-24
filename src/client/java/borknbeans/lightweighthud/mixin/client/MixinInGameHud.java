package borknbeans.lightweighthud.mixin.client;

import borknbeans.lightweighthud.GuiPosition;
import borknbeans.lightweighthud.LightweightHUDConfig;
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

    private final float SLIGHT_DAMAGE_THRESHOLD = 0.5f;
    private final float HIGH_DAMAGE_THRESHOLD = 0.15f;
    private final float VERY_HIGH_DAMAGE_THRESHOLD = 0.05f;

    private final int SLIGHT_DAMAGE_COLOR = 0xFFFF55;
    private final int HIGH_DAMAGE_COLOR = 0xFF5555;
    private final int VERY_HIGH_DAMAGE_COLOR = 0xAA0000;

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void renderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        GuiPosition guiPosition = LightweightHUDConfig.guiPosition;
        MinecraftClient client = MinecraftClient.getInstance();

        ItemStack stack = client.player.getMainHandStack();

        if (!stack.isEmpty()) {
            int[] guiCoordinates = guiPosition.getCoordinates(client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());

            if (isBlock(stack) && LightweightHUDConfig.drawBlockCount) {
                int itemCount = howManyOfThisItem(stack);
                String count = String.valueOf(itemCount);

                int textWidth = client.textRenderer.getWidth(count);
                guiCoordinates[0] = (guiPosition == GuiPosition.TOP_LEFT || guiPosition == GuiPosition.BOTTOM_LEFT) ? guiCoordinates[0] : guiCoordinates[0] - textWidth - TEXT_SPACING;

                context.drawText(client.textRenderer, count, guiCoordinates[0] + 16 + TEXT_SPACING, guiCoordinates[1] + 4, 0xFFFFFF, true);
            } else if (isTool(stack) && LightweightHUDConfig.drawToolDurability) {
                int maxDurability = stack.getMaxDamage();
                int remainingDurability = maxDurability - stack.getDamage();
                float durabilityPercentage = (float) remainingDurability / maxDurability;

                String durability = "";
                if (LightweightHUDConfig.drawToolDurabilityAsPercentage) {
                    durability = (int)(durabilityPercentage * 100) + "%";
                } else {
                    durability = remainingDurability + "/" + maxDurability;
                }

                int textWidth = client.textRenderer.getWidth(durability);
                guiCoordinates[0] = (guiPosition == GuiPosition.TOP_LEFT || guiPosition == GuiPosition.BOTTOM_LEFT) ? guiCoordinates[0] : guiCoordinates[0] - textWidth - TEXT_SPACING;

                context.drawText(client.textRenderer, durability, guiCoordinates[0] + 16 + TEXT_SPACING, guiCoordinates[1] + 4, decideDamageColor(durabilityPercentage), true);

                // Tools have 2 pixel space at the top
                guiCoordinates[1] -= 1;
            } else {
                // Dont render
                return;
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

    private int decideDamageColor(float durabilityPercentage) {
        if (durabilityPercentage < VERY_HIGH_DAMAGE_THRESHOLD) {
            return VERY_HIGH_DAMAGE_COLOR;
        } else if (durabilityPercentage < HIGH_DAMAGE_THRESHOLD) {
            return HIGH_DAMAGE_COLOR;
        } else if (durabilityPercentage < SLIGHT_DAMAGE_THRESHOLD) {
            return SLIGHT_DAMAGE_COLOR;
        }

        return 0xFFFFFF;
    }
}
