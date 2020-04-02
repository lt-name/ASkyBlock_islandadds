/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 黑曜石还原岩浆               */
package name.Obsidian.Listener;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockIds;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemIds;
import cn.nukkit.player.Player;
import name.Obsidian.Tasks.DelayGiveLava;
import name.Obsidian.Obsidian;

public class OresListener implements Listener {

    @EventHandler
    public void onInt(PlayerInteractEvent event) {
        //黑曜石还原岩浆
        Item item = event.getItem();
        if((item != null) && (item.getId() == ItemIds.BUCKET) && (item.getMeta() == 0)) {
            Block block = event.getBlock();
            if((block != null) && (block.getId() == BlockIds.OBSIDIAN) && (block.getMeta() == 0)) {
                Player player = event.getPlayer();
                //潜行有效
                if ((player == null) || (Obsidian.get().getSK() && (!player.isSneaking()))) {
                    return;
                }
                block.getLevel().setBlock(block.getPosition(), BlockIds.AIR);
                player.getInventory().removeItem(Item.get(ItemIds.BUCKET, 0));
                Obsidian.get().getServer().getScheduler().scheduleDelayedTask(new DelayGiveLava(player), 5);
            }
        }
    }

}