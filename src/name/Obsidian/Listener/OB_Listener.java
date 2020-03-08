/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石                              */
package name.Obsidian.Listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.*;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import name.Obsidian.Tasks.Leaves_Task;
import name.Obsidian.Tasks.OB_Task;
import name.Obsidian.Obsidian;

public class OB_Listener implements Listener {

    @EventHandler
    public void OnInt(PlayerInteractEvent event) {
        //黑曜石还原岩浆
        if (Obsidian.getOB_config().getOR()){
            Item item = event.getItem();
            if(item.getId() == 325 && item.getDamage() == 0){
                Block block = event.getBlock();
                if(block.getId() == 49 && block.getDamage() == 0){
                    Player player = event.getPlayer();
                    //潜行有效
                    if (Obsidian.getOB_config().getSK()){
                        if (!player.isSneaking()){ return; }
                    }
                    block.getLevel().setBlock(block,new BlockAir());
                    player.getInventory().removeItem(Item.get(325, 0));
                    Server.getInstance().getScheduler().scheduleDelayedTask(new OB_Task(player), 5);
                }
            }
        }
    }

    @EventHandler
    public void OnBUP(BlockUpdateEvent event) {
        Block block = event.getBlock();
        int x = block.getFloorX();
        int y = block.getFloorY();
        int z = block.getFloorZ();
        Level level = block.getLevel();
        //允许错误的刷石机
        if (Obsidian.getOB_config().getERS()) {
            if(block.getId() == 49 && block.getDamage() == 0){
                if(level.getBlock(x-1, y, z).getId() == 8){
                    level.setBlock(level.getBlock(x-1, y, z),Block.get(4));
                    level.setBlock(block,Block.get(10));
                    return;
                }
                if(level.getBlock(x+1, y, z).getId() == 8){
                    level.setBlock(level.getBlock(x+1, y, z),Block.get(4));
                    block.getLevel().setBlock(block,Block.get(10));
                    return;
                }
                if(level.getBlock(x, y, z-1).getId() == 8){
                    level.setBlock(level.getBlock(x, y, z-1),Block.get(4));
                    level.setBlock(block,Block.get(10));
                    return;
                }
                if(level.getBlock(x, y, z+1).getId() == 8){
                    level.setBlock(level.getBlock(x, y, z+1),Block.get(4));
                    level.setBlock(block,Block.get(10));
                    return;
                }
                if(level.getBlock(x, y+1, z).getId() == 8){
                    level.setBlock(level.getBlock(x, y+1, z),Block.get(4));
                    level.setBlock(block,Block.get(10));
                }
            }
        }
    }

    @EventHandler
    public void OnLFE(LiquidFlowEvent event) {
        //禁止高空流水
        if (Obsidian.getOB_config().getWFW()) {
            int x = event.getTo().getFloorX();
            int y = event.getTo().getFloorY();
            int z = event.getTo().getFloorZ();
            if ((event.getTo().getLevel().getBlock(x,y+Obsidian.getOB_config().getWFWY(),z).getName().contains("Water")) ||
                    (event.getTo().getLevel().getBlock(x,y+Obsidian.getOB_config().getWFWY(),z).getName().contains("Lava"))){
                event.setCancelled();
                return;
            }
            for (char i = 1; i < Obsidian.getOB_config().getWFWY(); i++) {
                if ((event.getTo().getLevel().getBlock(x,y-i,z).getId() != 0) &&
                        (!event.getTo().getLevel().getBlock(x,y-i,z).getName().contains("Water")) &&
                        (!event.getTo().getLevel().getBlock(x,y-i,z).getName().contains("Lava"))) {
                    return;
                }
            }
            event.setCancelled();
        }
    }

    @EventHandler
    public void OnLDE(LeavesDecayEvent event) {
        //树叶掉落保底
        if (Obsidian.getOB_config().getSMBD() &&
                (!Obsidian.getOB_config().changeLeaves(event.getBlock(), false))) {
            Server.getInstance().getScheduler().scheduleDelayedTask(new Leaves_Task(event.getBlock()), 1);
        }
    }

    @EventHandler
    public void onBBE(BlockBreakEvent event) {
        //树叶掉落保底
        if (Obsidian.getOB_config().getSMBD() &&
                (!Obsidian.getOB_config().changeLeaves(event.getBlock(), false)) &&
                (event.getPlayer().getGamemode() == 0)) {
            Server.getInstance().getScheduler().scheduleDelayedTask(new Leaves_Task(event.getBlock()), 1);
        }
    }

    @EventHandler
    public void OnBPE(BlockPlaceEvent event) {
        if (Obsidian.getOB_config().getSMBD()) {
            Block block = event.getBlock();
            if (block.getName().contains("Leaves")) {
                Obsidian.getOB_config().changeLeaves(block, true);
            }
        }
    }
}
