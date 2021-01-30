/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石                              */
package name.obsidian;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import name.obsidian.listener.*;
import updata.AutoData;

import java.util.ArrayList;

public class Obsidian extends PluginBase {

    public static final String VERSION = "?";

    private static Obsidian Obsidian;

    private Config config, Leaves;


    @Override
    public void onEnable() {
        Obsidian = this;
        if (getServer().getPluginManager().getPlugin("AutoUpData") != null) {
            getLogger().info(TextFormat.YELLOW + " 检查更新中");
            if (AutoData.defaultUpData(this, getFile(), "lt-name", "Obsidian")) {
                return;
            }
        }
        saveDefaultConfig();
        this.config = getConfig();
        this.Leaves = new Config(getDataFolder() + "/Leaves.yml", 2);
        //黑曜石还原岩浆
        if (getOR()) {
            getServer().getPluginManager().registerEvents(new OresListener(), this);
        }
        //禁止高空流水
        if (getWFW()) {
            getServer().getPluginManager().registerEvents(new LiquidListener(), this);
        }
        //树苗掉落
        if (getSMBD()) {
            getServer().getPluginManager().registerEvents(new LeavesListener(), this);
        }
        //虚空保护
        if (getXKP()) {
            getServer().getPluginManager().registerEvents(new DamageListener(), this);
        }
        //错误的刷石机
        if (getERS()) {
            getServer().getPluginManager().registerEvents(new ERSListener(), this);
        }
        getServer().getLogger().info(TextFormat.GREEN + "[Obsidian] 加载完成！");
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info(TextFormat.RED + "[Obsidian] 已卸载！");
    }

    public boolean changeLeaves(Block block, boolean add) {
        //此函数参考若水的CreateBlock插件（已获得授权）
        String s = block.getX() + ":" + block.getY() + ":" + block.getZ() + ":" + block.getLevel().getFolderName();
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

    public boolean getXKP() {
        return  this.config.getBoolean("虚空保护", true);
    }

    public int getXKPM() {
        return  this.config.getInt("虚空保护模式", 2);
    }

    public boolean tpisland(Player player, Level level) {
        int x = player.getFloorX();
        int z = player.getFloorZ();
        //丧心病狂的扫描，这次总不会漏掉了吧
        //建筑建那么高？那您还是回主空岛去吧
        for (int y1 = 200; y1 > 0; --y1) {
            for (int x1 = 0; x1 < 40; ++x1) {
                for (int z1 = 0; z1 < 40; ++z1) {
                    //获取合适的落脚点，岩浆和水当然不行
                    if ((level.getBlock(x - x1, y1, z - z1).getId() != 0) &&
                            (!level.getBlock(x - x1, y1, z - z1).getName().contains("Water")) &&
                            (!level.getBlock(x - x1, y1, z - z1).getName().contains("Lava"))) {
                        //还得防止传送到墙里
                        if ((level.getBlock(x - x1, y1 + 1, z - z1).getId() == 0) &&
                                (level.getBlock(x - x1, y1 + 2, z - z1).getId() == 0)) {
                            player.teleport(new Position(x - x1, y1 + 1, z - z1, level));
                            player.sendMessage("§a[虚空保护]：已将您拉回最近的空岛！");
                            return true;
                        }
                    }
                    if ((level.getBlock(x - x1, y1, z + z1).getId() != 0) &&
                            (!level.getBlock(x - x1, y1, z + z1).getName().contains("Water")) &&
                            (!level.getBlock(x - x1, y1, z + z1).getName().contains("Lava"))) {
                        if ((level.getBlock(x - x1, y1 + 1, z + z1).getId() == 0) &&
                                (level.getBlock(x - x1, y1 + 2, z + z1).getId() == 0)) {
                            player.teleport(new Position(x - x1, y1 + 1, z + z1, level));
                            player.sendMessage("§a[虚空保护]：已将您拉回最近的空岛！");
                            return true;
                        }
                    }
                    if ((level.getBlock(x + x1, y1, z - z1).getId() != 0) &&
                            (!level.getBlock(x + x1, y1, z - z1).getName().contains("Water")) &&
                            (!level.getBlock(x + x1, y1, z - z1).getName().contains("Lava"))) {
                        if ((level.getBlock(x + x1, y1 + 1, z - z1).getId() == 0) &&
                                (level.getBlock(x + x1, y1 + 2, z - z1).getId() == 0)) {
                            player.teleport(new Position(x + x1, y1 + 1, z - z1, level));
                            player.sendMessage("§a[虚空保护]：已将您拉回最近的空岛！");
                            return true;
                        }
                    }
                    if ((level.getBlock(x + x1, y1, z + z1).getId() != 0) &&
                            (!level.getBlock(x + x1, y1, z + z1).getName().contains("Water")) &&
                            (!level.getBlock(x + x1, y1, z + z1).getName().contains("Lava"))) {
                        if ((level.getBlock(x + x1, y1 + 1, z + z1).getId() == 0) &&
                                (level.getBlock(x + x1, y1 + 2, z + z1).getId() == 0)) {
                            player.teleport(new Position(x + x1, y1 + 1, z + z1, level));
                            player.sendMessage("§a[虚空保护]：已将您拉回最近的空岛！");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
