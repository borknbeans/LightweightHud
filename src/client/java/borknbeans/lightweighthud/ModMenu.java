package borknbeans.lightweighthud;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> createConfigScreen(parent);
    }

    private Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("Lightweight HUD"));

        ConfigCategory general = builder.getOrCreateCategory(Text.of("Block in Hand Visualizer"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startEnumSelector(
                        Text.translatable("GUI Position"),
                        GuiPosition.class,
                        LightweightHUDConfig.guiPosition
                ).setDefaultValue(GuiPosition.TOP_LEFT)
                .setSaveConsumer(newValue -> LightweightHUDConfig.guiPosition = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("Draw Block Count"),
                        LightweightHUDConfig.drawBlockCount
                ).setDefaultValue(true)
                .setSaveConsumer(newValue -> LightweightHUDConfig.drawBlockCount = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("Draw Tool Durability"),
                        LightweightHUDConfig.drawToolDurability
                ).setDefaultValue(true)
                .setSaveConsumer(newValue -> LightweightHUDConfig.drawToolDurability = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("Show Tool Durability as Percentage"),
                        LightweightHUDConfig.drawToolDurabilityAsPercentage
                ).setDefaultValue(false)
                .setSaveConsumer(newValue -> LightweightHUDConfig.drawToolDurabilityAsPercentage = newValue)
                .build());

        builder.setSavingRunnable(() -> LightweightHUDConfig.save());

        return builder.build();
    }
}
