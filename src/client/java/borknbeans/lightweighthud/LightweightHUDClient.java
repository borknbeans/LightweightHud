package borknbeans.lightweighthud;

import borknbeans.lightweighthud.config.LightweightHUDConfig;
import net.fabricmc.api.ClientModInitializer;

public class LightweightHUDClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		LightweightHUDConfig.load();
	}
}