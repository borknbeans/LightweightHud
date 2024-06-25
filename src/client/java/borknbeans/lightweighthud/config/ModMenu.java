package borknbeans.lightweighthud.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> createConfigScreen(parent);
    }

    private Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("Lightweight HUD"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory heldItemSettings = builder.getOrCreateCategory(Text.of("Held Item Settings"));

        heldItemSettings.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("Show Held Item"),
                        LightweightHUDConfig.showHeldItem
                ).setDefaultValue(true)
                .setSaveConsumer(newValue -> LightweightHUDConfig.showHeldItem = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startTextDescription(Text.literal("HUD Placement").formatted(Formatting.BOLD))
                .build());

        heldItemSettings.addEntry(entryBuilder.startEnumSelector(
                        Text.translatable("GUI Position"),
                        GuiPosition.class,
                        LightweightHUDConfig.heldItemGuiPosition
                ).setDefaultValue(GuiPosition.TOP_LEFT)
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemGuiPosition = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startIntField(
                        Text.translatable("X Offset"),
                        LightweightHUDConfig.heldItemXOffset
                ).setDefaultValue(0)
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemXOffset = newValue)
                .setTooltip(Text.translatable("Move the HUD on the x-axis\nPOSITIVE: Right\nNEGATIVE: Left"))
                .build());

        heldItemSettings.addEntry(entryBuilder.startIntField(
                        Text.translatable("Y Offset"),
                        LightweightHUDConfig.heldItemYOffset
                ).setDefaultValue(0)
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemYOffset = newValue)
                .setTooltip(Text.translatable("Move the HUD on the y-axis\nPOSITIVE: Down\nNEGATIVE: Up"))
                .build());

        heldItemSettings.addEntry(entryBuilder.startTextDescription(Text.literal("\nHUD Settings").formatted(Formatting.BOLD))
                .build());

        heldItemSettings.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("Draw Block Count"),
                        LightweightHUDConfig.drawBlockCount
                ).setDefaultValue(true)
                .setSaveConsumer(newValue -> LightweightHUDConfig.drawBlockCount = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("Draw Tool Durability"),
                        LightweightHUDConfig.drawToolDurability
                ).setDefaultValue(true)
                .setSaveConsumer(newValue -> LightweightHUDConfig.drawToolDurability = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("Show Tool Durability as Percentage"),
                        LightweightHUDConfig.drawToolDurabilityAsPercentage
                ).setDefaultValue(true)
                .setSaveConsumer(newValue -> LightweightHUDConfig.drawToolDurabilityAsPercentage = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startTextDescription(Text.literal("\nTool Durability Settings").formatted(Formatting.BOLD))
                .build());

        heldItemSettings.addEntry(entryBuilder.startColorField(
                        Text.translatable("Slight Damaged Color"), LightweightHUDConfig.heldItemSlightDamageColor)
                .setDefaultValue(0xFFFF55)
                .setTooltip(Text.translatable("Color of text when tool has slight damage"))
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemSlightDamageColor = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startIntSlider(
                        Text.translatable("Slight Damage Threshold (%)"),
                        LightweightHUDConfig.heldItemSlightDamageThreshold,
                        1,
                        100
                ).setDefaultValue(50)
                .setTooltip(Text.translatable("Percentage at which point this color will be used"))
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemSlightDamageThreshold = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startColorField(
                        Text.translatable("High Damage Color"), LightweightHUDConfig.heldItemHighDamageColor)
                .setDefaultValue(0xFF5555)
                .setTooltip(Text.translatable("Color of text when tool has high damage"))
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemHighDamageColor = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startIntSlider(
                        Text.translatable("High Damage Threshold (%)"),
                        LightweightHUDConfig.heldItemHighDamageThreshold,
                        1,
                        100
                ).setDefaultValue(15)
                .setTooltip(Text.translatable("Percentage at which point this color will be used"))
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemHighDamageThreshold = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startColorField(
                        Text.translatable("Very High Damage Color"), LightweightHUDConfig.heldItemVeryHighDamageColor)
                .setDefaultValue(0xAA0000)
                .setTooltip(Text.translatable("Color of text when tool has very high damage"))
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemVeryHighDamageColor = newValue)
                .build());

        heldItemSettings.addEntry(entryBuilder.startIntSlider(
                        Text.translatable("Very High Damage Threshold (%)"),
                        LightweightHUDConfig.heldItemVeryHighDamageThreshold,
                        1,
                        100
                ).setDefaultValue(5)
                .setTooltip(Text.translatable("Percentage at which point this color will be used"))
                .setSaveConsumer(newValue -> LightweightHUDConfig.heldItemVeryHighDamageThreshold = newValue)
                .build());


        builder.setSavingRunnable(() -> LightweightHUDConfig.save());

        return builder.build();
    }
}
