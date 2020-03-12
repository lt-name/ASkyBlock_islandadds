/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 检测玩家是否掉入虚空         */
package name.Obsidian.Tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.PluginTask;
import com.larryTheCoder.ASkyBlock;
import name.Obsidian.Obsidian;
import java.util.Map;
import java.util.UUID;

public class playermove extends PluginTask {

    private int mode;

    public playermove (Obsidian owner, int mode) {
        super(owner);
        this.mode = mode;
    }

    @Override
    public void onRun(int i) {
        Map<UUID, Player> players = Server.getInstance().getOnlinePlayers();
        //遍历在线玩家
        for (Player player : players.values()) {
            if ((player != null) && (player.getFloorY() <= 0)) {
                Level level = player.getLevel();
                if ((Server.getInstance().getPluginManager().getPlugin("ASkyBlock") != null) &&
                        ASkyBlock.get().inIslandWorld(player)) {
                    //模式选择 1-直接拉回主岛 2-扫描附近空岛
                    if (this.mode == 2) {
                        //先向上传送100格防止二次触发
                        player.move(0, 100, 0);
                        if (tpisland(player, level)) { continue; }
                    } else {
                        player.sendMessage("§a[虚空保护]：已将您拉回主空岛！");
                    }
                    ASkyBlock.get().getGrid().homeTeleport(player);
                } else {
                    //未安装空岛插件，或者不在空岛世界
                    player.teleport(Server.getInstance().getDefaultLevel().getSafeSpawn());
                    player.sendMessage("§a[虚空保护]：已将您拉回主世界！");
                }
            }
        }
    }

    private boolean tpisland(Player player, Level level) {
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
        player.sendMessage("§a[虚空保护]：附近没有空岛,已将您拉回主空岛！");
        ASkyBlock.get().getGrid().homeTeleport(player);
        return false;
    }
}
