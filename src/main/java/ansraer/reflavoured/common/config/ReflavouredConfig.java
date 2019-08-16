package ansraer.reflavoured.common.config;

import ansraer.reflavoured.VanillaReflavoured;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1.annotation.ConfigEntry;

@Config(name = VanillaReflavoured.MODID)
public class ReflavouredConfig implements ConfigData {
    public static ReflavouredConfig getInstance(){
        return AutoConfig.getConfigHolder(ReflavouredConfig.class).getConfig();
    }

    @ConfigEntry.Gui.Tooltip
    public boolean forceSmokerRecipes = true;

    @ConfigEntry.Gui.Tooltip
    public boolean showAllPainterColors = true;
}
