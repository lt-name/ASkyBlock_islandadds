/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 液体流动监听器              */
package name.Obsidian.Listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.LiquidFlowEvent;
import name.Obsidian.Obsidian;

public class liquidListener implements Listener {

    @EventHandler
    public void OnLFE(LiquidFlowEvent event) {
        if (event.isCancelled()) { return; }
        //禁止高空流水
        if (event.getTo() == null) { return; }
        int x = event.getTo().getFloorX();
        int y = event.getTo().getFloorY();
        int z = event.getTo().getFloorZ();
        if ((event.getTo().getLevel().getBlock(x,y+ Obsidian.get().getWFWY(),z).getName().contains("Water")) ||
                (event.getTo().getLevel().getBlock(x,y+ Obsidian.get().getWFWY(),z).getName().contains("Lava"))){
            event.setCancelled();
            return;
        }
        for (char i = 1; i < Obsidian.get().getWFWY(); i++) {
            if ((event.getTo().getLevel().getBlock(x,y-i,z).getId() != 0) &&
                    (!event.getTo().getLevel().getBlock(x,y-i,z).getName().contains("Water")) &&
                    (!event.getTo().getLevel().getBlock(x,y-i,z).getName().contains("Lava"))) {
                return;
            }
        }
        event.setCancelled();
    }

}
