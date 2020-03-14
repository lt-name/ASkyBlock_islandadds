package name.Obsidian.Listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import com.larryTheCoder.ASkyBlock;
import name.Obsidian.Obsidian;

public class moveListener implements Listener {

    @EventHandler
    public void onPMV(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if ((player != null) && (player.getFloorY() < 0)) {
            Level level = player.getLevel();
            //先向上传送到256格防止二次触发
            player.teleport(new Position(player.getX(), 256, player.getZ(),level));
            if ((Server.getInstance().getPluginManager().getPlugin("ASkyBlock") != null) &&
                    ASkyBlock.get().inIslandWorld(player)) {
                //模式选择 1-直接拉回主岛 2-扫描附近空岛
                if (Obsidian.get().getXKPM() == 2) {
                    if (Obsidian.get().tpisland(player, level)) {
                        return;
                    }
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
