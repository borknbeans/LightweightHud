package borknbeans.lightweighthud.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LightweightHUDConfig {
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "lightweight-hud.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static boolean showHeldItem = true;
    public static GuiPosition heldItemGuiPosition = GuiPosition.BOTTOM_LEFT;
    public static boolean drawBlockCount = true;
    public static boolean drawToolDurability = true;
    public static boolean drawToolDurabilityAsPercentage = true;
    public static int heldItemXOffset = 0;
    public static int heldItemYOffset = 0;
    public static int heldItemSlightDamageColor = 0xFFFF55;
    public static int heldItemHighDamageColor = 0xFF5555;
    public static int heldItemVeryHighDamageColor = 0xAA0000;
    public static int heldItemSlightDamageThreshold = 50;
    public static int heldItemHighDamageThreshold = 15;
    public static int heldItemVeryHighDamageThreshold = 5;

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);
                showHeldItem = data.showHeldItem;
                heldItemGuiPosition = data.heldItemGuiPosition;
                drawBlockCount = data.drawBlockCount;
                drawToolDurability = data.drawToolDurability;
                drawToolDurabilityAsPercentage = data.drawToolDurabilityAsPercentage;
                heldItemXOffset = data.heldItemXOffset;
                heldItemYOffset = data.heldItemYOffset;
                heldItemSlightDamageColor = data.heldItemSlightDamageColor;
                heldItemHighDamageColor = data.heldItemHighDamageColor;
                heldItemVeryHighDamageColor = data.heldItemVeryHighDamageColor;
                heldItemSlightDamageThreshold = data.heldItemSlightDamageThreshold;
                heldItemHighDamageThreshold = data.heldItemHighDamageThreshold;
                heldItemVeryHighDamageThreshold = data.heldItemVeryHighDamageThreshold;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            ConfigData data = new ConfigData();
            data.showHeldItem = showHeldItem;
            data.heldItemGuiPosition = heldItemGuiPosition;
            data.drawBlockCount = drawBlockCount;
            data.drawToolDurability = drawToolDurability;
            data.drawToolDurabilityAsPercentage = drawToolDurabilityAsPercentage;
            data.heldItemXOffset = heldItemXOffset;
            data.heldItemYOffset = heldItemYOffset;
            data.heldItemSlightDamageColor = heldItemSlightDamageColor;
            data.heldItemHighDamageColor = heldItemHighDamageColor;
            data.heldItemVeryHighDamageColor = heldItemVeryHighDamageColor;
            data.heldItemSlightDamageThreshold = heldItemSlightDamageThreshold;
            data.heldItemHighDamageThreshold = heldItemHighDamageThreshold;
            data.heldItemVeryHighDamageThreshold = heldItemVeryHighDamageThreshold;
            GSON.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ConfigData {
        boolean showHeldItem;
        GuiPosition heldItemGuiPosition;
        boolean drawBlockCount;
        boolean drawToolDurability;
        boolean drawToolDurabilityAsPercentage;
        int heldItemXOffset;
        int heldItemYOffset;
        int heldItemSlightDamageColor;
        int heldItemHighDamageColor;
        int heldItemVeryHighDamageColor;
        int heldItemSlightDamageThreshold;
        int heldItemHighDamageThreshold;
        int heldItemVeryHighDamageThreshold;
    }
}
