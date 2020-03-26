package name.Obsidian.Listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.Level;
import com.larryTheCoder.ASkyBlock;
import name.Obsidian.Obsidian;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                event.setCancelled();
                Player player = ((Player) event.getEntity()).getPlayer();
                Level level = player.getLevel();
                if ((Server.getInstance().getPluginManager().getPlugin("ASkyBlock") != null) &&
                        ASkyBlock.get().inIslandWorld(player)) {
                    //模式选择 1-直接拉回主岛 2-扫描附近空岛
                    if (Obsidian.get().getXKPM() == 2 &&
                            Obsidian.get().tpisland(player, level)) {
                        return;
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
