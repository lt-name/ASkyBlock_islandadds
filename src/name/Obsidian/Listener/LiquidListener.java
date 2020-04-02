/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 液体流动监听器              */
package name.Obsidian.Listener;

import cn.nukkit.block.BlockIds;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.LiquidFlowEvent;
import name.Obsidian.Obsidian;

public class LiquidListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void OnLFE(LiquidFlowEvent event) {
        //禁止高空流水
        if (event.getTo() == null) { return; }
        int x = event.getTo().getX();
        int y = event.getTo().getY();
        int z = event.getTo().getZ();
        if ((event.getTo().getLevel().getBlock(x,y+ Obsidian.get().getWFWY(),z).getId().getName().contains("Water")) ||
                (event.getTo().getLevel().getBlock(x,y+ Obsidian.get().getWFWY(),z).getId().getName().contains("Lava"))){
            event.setCancelled();
            return;
        }
        for (char i = 1; i < Obsidian.get().getWFWY(); i++) {
            if ((event.getTo().getLevel().getBlock(x,y-i,z).getId() != BlockIds.AIR) &&
                    (!event.getTo().getLevel().getBlock(x,y-i,z).getId().getName().contains("Water")) &&
                    (!event.getTo().getLevel().getBlock(x,y-i,z).getId().getName().contains("Lava"))) {
                return;
            }
        }
        event.setCancelled();
    }

}
