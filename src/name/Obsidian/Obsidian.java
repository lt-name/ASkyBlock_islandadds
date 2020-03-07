package name.Obsidian;

import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import name.Obsidian.Listener.OB_Listener;

public class Obsidian extends PluginBase implements Listener {

    private Config config;
    private static Obsidian OB_config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.config = getConfig();
        OB_config = this;
        this.getServer().getPluginManager().registerEvents(new OB_Listener(), this);
        Server.getInstance().getLogger().info(TextFormat.GREEN+"[Obsidian] 加载完成！");
    }

    @Override
    public void onDisable() {
        //this.config.save();
        Server.getInstance().getLogger().info(TextFormat.RED+"[Obsidian] 已卸载！");
    }

    public static Obsidian getOB_config() {
        return OB_config;
    }

    public boolean getOR() {
        return this.config.getBoolean("空桶点击黑曜石还原岩浆",true);
    }

    public boolean getSK() {
        return this.config.getBoolean("仅潜行时有效",true);
    }

    public boolean getERS() {
        return this.config.getBoolean("允许错误的刷石机",false);
    }

    public boolean getWFW() {
        return this.config.getBoolean("禁止高空流水",true);
    }

    public int getWFWY() {
        return this.config.getInt("高空流水判断格数",5);
    }
}
