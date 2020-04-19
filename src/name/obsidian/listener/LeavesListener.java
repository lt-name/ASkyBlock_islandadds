/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 树叶掉落保底功能监听器       */
package name.obsidian.listener;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.LeavesDecayEvent;
import name.obsidian.Obsidian;
import name.obsidian.tasks.AsyncLeaves;

public class LeavesListener implements Listener {

    @EventHandler
    public void onLDE(LeavesDecayEvent event) {
        Block block = event.getBlock();
        if ((block != null) && Obsidian.get().changeLeaves(block, false)) {
            Obsidian.get().getServer().getScheduler().scheduleAsyncTask(Obsidian.get(), new AsyncLeaves(block));
        }
    }

    @EventHandler
    public void onBBE(BlockBreakEvent event) {
        Block block = event.getBlock();
        if ((block != null) &&
                (event.getBlock().getName().contains("Leaves")) &&
                Obsidian.get().changeLeaves(event.getBlock(), false) &&
                (event.getPlayer().getGamemode() == 0)) {
            Obsidian.get().getServer().getScheduler().scheduleAsyncTask(Obsidian.get(), new AsyncLeaves(block));
        }
    }

    @EventHandler
    public void onBPE(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if ((block != null) && block.getName().contains("Leaves")) {
            Obsidian.get().changeLeaves(block, true);
        }
    }

}
