/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石                              */
package name.Obsidian;

import cn.nukkit.block.Block;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import name.Obsidian.Listener.OB_Listener;
import name.Obsidian.Tasks.playermove;
import java.util.ArrayList;

public class Obsidian extends PluginBase {

    private Config config,Leaves;
    private static Obsidian Obsidian;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.config = getConfig();
        this.Leaves = new Config(getDataFolder() + "/Leaves.yml", 2);
        Obsidian = this;
        getServer().getPluginManager().registerEvents(new OB_Listener(), this);
/*        if (this.config.getBoolean("虚空保护", true)){
            //异步检测玩家移动
            getServer().getScheduler().scheduleDelayedRepeatingTask(
                    new playermove(this,this.config.getInt("虚空保护模式", 1)), 20, 3, true);
        }*/

        getLogger().info(TextFormat.GREEN+"[Obsidian] 加载完成！");
    }

    @Override
    public void onDisable() {
        getLogger().info(TextFormat.RED+"[Obsidian] 已卸载！");
    }

    public boolean changeLeaves(Block block, boolean add) {
        //此函数参考若水的CreateBlock插件（已获得授权）
        String s = block.getX() + ":" + block.getY() + ":" + block.getZ() + ":" + block.getLevel().getName();
        ArrayList<String> list = new ArrayList<>(this.Leaves.getStringList("Leaves"));
        if (add) {
            list.add(s);
            this.Leaves.set("Leaves", list);
            this.Leaves.save();
            return true;
        }else if (list.contains(s)) {
            list.remove(s);
            this.Leaves.set("Leaves", list);
            this.Leaves.save();
            return false;
        }
        return true;
    }

    public static Obsidian get() {
        return Obsidian;
    }

    //获取配置
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
        return this.config.getInt("高空流水判断格数",3);
    }

    public boolean getSMBD() {
        return this.config.getBoolean("树苗掉落保底",true);
    }

}
