package borknbeans.lightweighthud.mixin.client;

import borknbeans.lightweighthud.config.LightweightHUDConfig;
import borknbeans.lightweighthud.hud.HudHelper;
import borknbeans.lightweighthud.hud.HudItem;
import borknbeans.lightweighthud.hud.HudObject;
import borknbeans.lightweighthud.hud.HudText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    @Shadow public abstract void render(DrawContext context, RenderTickCounter tickCounter);

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void renderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        renderHeldItem(context);
        renderPlayerCoordinates(context);
    }

    private boolean isBlock(ItemStack stack) {
        return stack.getItem() instanceof BlockItem;
    }

    private boolean isTool(ItemStack stack) {
        return stack.getItem() instanceof ToolItem;
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

    private int decideHeldItemDamageColor(int durabilityPercentage) {
        if (durabilityPercentage < LightweightHUDConfig.heldItemVeryHighDamageThreshold) {
            return LightweightHUDConfig.heldItemVeryHighDamageColor;
        } else if (durabilityPercentage < LightweightHUDConfig.heldItemHighDamageThreshold) {
            return LightweightHUDConfig.heldItemHighDamageColor;
        } else if (durabilityPercentage < LightweightHUDConfig.heldItemSlightDamageThreshold) {
            return LightweightHUDConfig.heldItemSlightDamageColor;
        }

        return 0xFFFFFF;
    }

    public void renderHeldItem(DrawContext context) {
        if (!LightweightHUDConfig.showHeldItem) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        ItemStack heldItem = client.player.getMainHandStack();

        HudHelper hudHelper = null;

        if (isBlock(heldItem)) {
            if (!LightweightHUDConfig.drawBlockCount) {
                return;
            }

            int count = howManyOfThisItem(heldItem);

            HudObject[] hudObjects = {
                    new HudItem(client.player.getMainHandStack()),
                    new HudText(String.valueOf(count))
            };

            hudHelper = new HudHelper(context, LightweightHUDConfig.heldItemHudPosition, hudObjects, LightweightHUDConfig.heldItemXOffset, LightweightHUDConfig.heldItemYOffset);
        } else if (isTool(heldItem)) {
            if (!LightweightHUDConfig.drawToolDurability) {
                return;
            }

            int damage = heldItem.getDamage();
            int maxDurability = heldItem.getMaxDamage();

            float durabilityPercentage =  ((float)(maxDurability - damage) / maxDurability) * 100f;

            String durability;
            if (LightweightHUDConfig.drawToolDurabilityAsPercentage) {
                durability = String.format("%.0f", durabilityPercentage) + "%";
            } else {
                durability = String.format("%d/%d", maxDurability - damage, maxDurability);
            }

            int color = decideHeldItemDamageColor((int)durabilityPercentage);

            HudObject[] hudObjects = {
                    new HudItem(client.player.getMainHandStack()),
                    new HudText(durability, color)
            };

            hudHelper = new HudHelper(context, LightweightHUDConfig.heldItemHudPosition, hudObjects, LightweightHUDConfig.heldItemXOffset, LightweightHUDConfig.heldItemYOffset);
        }

        if (hudHelper != null) {
            hudHelper.setCentered(true);

            hudHelper.drawHud();
        }
    }

    public void renderPlayerCoordinates(DrawContext context) {
        if (!LightweightHUDConfig.showPlayerPosition) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        Vec3d playerPos = client.player.getPos();

        if (playerPos == null) {
            return;
        }

        String formattedPos = String.format("X: %.1f, Y: %.1f, Z: %.1f", playerPos.x, playerPos.y, playerPos.z);

        HudObject[] hudObjects = {
                new HudText(formattedPos)
        };

        HudHelper hudHelper = new HudHelper(context, LightweightHUDConfig.playerPositionHudPosition, hudObjects, LightweightHUDConfig.playerNavigationXOffset, LightweightHUDConfig.playerNavigationYOffset);

        hudHelper.drawHud();
    }
}
