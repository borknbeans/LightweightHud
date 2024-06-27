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

    // Held Item Settings
    public static boolean showHeldItem = true;
    public static HudPosition heldItemHudPosition = HudPosition.BOTTOM_LEFT;
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

    // Navigation Settings
    public static boolean showPlayerPosition = false;
    public static boolean showPlayerChunk = false;
    public static boolean showPlayerDirection = false;
    public static HudPosition playerPositionHudPosition = HudPosition.TOP_LEFT;
    public static int playerNavigationXOffset = 0;
    public static int playerNavigationYOffset = 0;

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);

                // Held Items
                showHeldItem = data.showHeldItem;
                heldItemHudPosition = data.heldItemHudPosition;
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

                // Navigation
                showPlayerPosition = data.showPlayerPosition;
                showPlayerChunk = data.showPlayerChunk;
                showPlayerDirection = data.showPlayerDirection;
                playerPositionHudPosition = data.playerPositionHudPosition;
                playerNavigationXOffset = data.heldItemXOffset;
                playerNavigationYOffset = data.heldItemYOffset;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            ConfigData data = new ConfigData();

            // Held Items
            data.showHeldItem = showHeldItem;
            data.heldItemHudPosition = heldItemHudPosition;
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

            // Navigation
            data.showPlayerPosition = showPlayerPosition;
            data.showPlayerChunk = showPlayerChunk;
            data.showPlayerDirection = showPlayerDirection;
            data.playerPositionHudPosition = playerPositionHudPosition;
            data.playerNavigationXOffset = playerNavigationXOffset;
            data.playerNavigationYOffset = playerNavigationYOffset;

            GSON.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ConfigData {
        // Held Items
        boolean showHeldItem;
        HudPosition heldItemHudPosition;
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

        // Navigation
        boolean showPlayerPosition;
        boolean showPlayerChunk;
        boolean showPlayerDirection;
        HudPosition playerPositionHudPosition;
        int playerNavigationXOffset;
        int playerNavigationYOffset;
    }
}
