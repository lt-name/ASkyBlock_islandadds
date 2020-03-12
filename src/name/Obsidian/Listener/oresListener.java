/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 黑曜石还原岩浆               */
package name.Obsidian.Listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import name.Obsidian.Tasks.delayGiveLava;
import name.Obsidian.Obsidian;

public class oresListener implements Listener {

    @EventHandler
    public void onInt(PlayerInteractEvent event) {
        //黑曜石还原岩浆
        Item item = event.getItem();
        if((item != null) && (item.getId() == 325) && (item.getDamage() == 0)) {
            Block block = event.getBlock();
            if((block != null) && (block.getId() == 49) && (block.getDamage() == 0)) {
                Player player = event.getPlayer();
                //潜行有效
                if ((player == null) || (Obsidian.get().getSK() && (!player.isSneaking()))) {
                    return;
                }
                block.getLevel().setBlock(block,new BlockAir());
                player.getInventory().removeItem(Item.get(325, 0));
                Obsidian.get().getServer().getScheduler().scheduleDelayedTask(new delayGiveLava(player), 5);
            }
        }
    }

}