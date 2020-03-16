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

public class PlayerMove extends PluginTask<Obsidian> {

    private int mode;

    public PlayerMove(Obsidian owner, int mode) {
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
                //先向上传送到256格防止二次触发
                player.teleport(new Position(player.getX(), 256, player.getZ(),level));
                if ((Server.getInstance().getPluginManager().getPlugin("ASkyBlock") != null) &&
                        ASkyBlock.get().inIslandWorld(player)) {
                    //模式选择 1-直接拉回主岛 2-扫描附近空岛
                    if (this.mode == 2) {
                        if (Obsidian.get().tpisland(player, level)) { continue; }
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

}
