package borknbeans.lightweighthud;

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

    public static GuiPosition guiPosition = GuiPosition.BOTTOM_LEFT;
    public static boolean drawBlockCount = true;
    public static boolean drawToolDurability = true;
    public static boolean drawToolDurabilityAsPercentage = false;

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);
                guiPosition = data.guiPosition;
                drawBlockCount = data.drawBlockCount;
                drawToolDurability = data.drawToolDurability;
                drawToolDurabilityAsPercentage = data.drawToolDurabilityAsPercentage;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            ConfigData data = new ConfigData();
            data.guiPosition = guiPosition;
            data.drawBlockCount = drawBlockCount;
            data.drawToolDurability = drawToolDurability;
            data.drawToolDurabilityAsPercentage = drawToolDurabilityAsPercentage;
            GSON.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ConfigData {
        GuiPosition guiPosition;
        boolean drawBlockCount;
        boolean drawToolDurability;
        boolean drawToolDurabilityAsPercentage;
    }
}
