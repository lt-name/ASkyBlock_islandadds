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
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import com.larryTheCoder.ASkyBlock;
import name.Obsidian.Tasks.asyncLeaves;
import name.Obsidian.Tasks.delayGiveLava;
import name.Obsidian.Obsidian;
import name.Obsidian.Tasks.XKP_Task;

public class OB_Listener implements Listener {

    @EventHandler
    public void OnInt(PlayerInteractEvent event) {
        //黑曜石还原岩浆
        if (Obsidian.get().getOR()){
            Item item = event.getItem();
            if((item != null) && (item.getId() == 325) && (item.getDamage() == 0)){
                Block block = event.getBlock();
                if((block != null) && (block.getId() == 49) && (block.getDamage() == 0)){
                    Player player = event.getPlayer();
                    //潜行有效
                    if ((player == null) || (Obsidian.get().getSK() && (!player.isSneaking()))){
                        return;
                    }
                    block.getLevel().setBlock(block,new BlockAir());
                    player.getInventory().removeItem(Item.get(325, 0));
                    Server.getInstance().getScheduler().scheduleDelayedTask(new delayGiveLava(player), 5);
                }
            }
        }
    }

    @EventHandler
    public void OnBUP(BlockUpdateEvent event) {
        //允许错误的刷石机
        if (Obsidian.get().getERS()) {
            Block block = event.getBlock();
            int x = block.getFloorX();
            int y = block.getFloorY();
            int z = block.getFloorZ();
            Level level = block.getLevel();
            if((level != null) && (block.getId() == 49 && block.getDamage() == 0)){
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
        if (event.isCancelled()) { return; }
        //禁止高空流水
        if (Obsidian.get().getWFW()) {
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

    @EventHandler
    public void OnLDE(LeavesDecayEvent event) {
        if (Obsidian.get().getSMBD() &&
                (event.getBlock() != null) &&
                Obsidian.get().changeLeaves(event.getBlock(), false)) {
            Obsidian.get().getServer().getScheduler().scheduleAsyncTask(Obsidian.get(), new asyncLeaves(event.getBlock()));
        }
    }

    @EventHandler
    public void OnBBE(BlockBreakEvent event) {
        if (Obsidian.get().getSMBD() &&
                (event.getBlock() != null) &&
                Obsidian.get().changeLeaves(event.getBlock(), false) &&
                (event.getPlayer().getGamemode() == 0)) {
            Obsidian.get().getServer().getScheduler().scheduleAsyncTask(Obsidian.get(), new asyncLeaves(event.getBlock()));
        }
    }

    @EventHandler
    public void OnBPE(BlockPlaceEvent event) {
        if (Obsidian.get().getSMBD()) {
            Block block = event.getBlock();
            if ((block != null) && block.getName().contains("Leaves")) {
                Obsidian.get().changeLeaves(block, true);
            }
        }
    }

    @EventHandler
    public void OnPME(PlayerMoveEvent event) {
        if (Obsidian.get().getXKP()) {
            if ((event.getTo() != null) && (event.getTo().getFloorY() < 0)) {
                Player player = event.getPlayer();
                if (player == null) { return; }
                //在空岛世界就拉回到空岛，否则拉回到主世界
                if ((Server.getInstance().getPluginManager().getPlugin("ASkyBlock") != null) &&
                        ASkyBlock.get().inIslandWorld(player)) {
                    Level level = player.getLevel();
                    Server.getInstance().getScheduler().scheduleTask(new XKP_Task(player, level));
                    //Server.getInstance().getScheduler().scheduleAsyncTask(Obsidian.get(), new island(player, level));
                }else {
                    player.teleport(Server.getInstance().getDefaultLevel().getSafeSpawn());
                    player.sendMessage("§a[虚空保护]：已将您拉回主世界！");
                }
            }
        }
    }

/*    @EventHandler
    public void OnISE(ItemSpawnEvent event) {
        Entity entity = event.getEntity();
        //空的你触发监听器干什么？
        if (entity == null) { return; }
        int x = entity.getFloorX();
        int y = entity.getFloorY();
        int z = entity.getFloorZ();
        Level level = entity.getLevel();
        //从掉落物高度开始往下扫描 3为水面
        for (int y1 = y; y1 > 3; --y1) {
            if (level.getBlock(x, y1, z).getId() != 0) { return; }
        }
        //拉回 扫描附近 5X5X5 大小的八个区块
        for (int y1 = 0; y1 < 5; ++y1) {
            for (int x1 = 0; x1 < 5; ++x1) {
                for (int z1 = 0; z1 < 5; ++z1) {
//                    000   x-  y-   z-
                    if (level.getBlock(x - x1,y - y1,z - z1).getId() != 0) {
                        entity.move((double)-x1,(double)-y1,(double)-z1);
                        return;
                    }
//                    001   x-  y-  z+
                    if (level.getBlock(x - x1,y - y1,z + z1).getId() != 0) {
                        entity.move((double)-x1,(double)-y1,(double)z1);
                        return;
                    }
//                    010   x-  y+  z-
                    if (level.getBlock(x - x1,y + y1,z - z1).getId() != 0) {
                        entity.move((double)-x1,(double)y1,(double)-z1);
                        return;
                    }
//                    011   x-  y+  z+
                    if (level.getBlock(x - x1,y + y1,z + z1).getId() != 0) {
                        entity.move((double)-x1,(double)y1,(double)z1);
                        return;
                    }
//                    100   x+  y-  z-
                    if (level.getBlock(x + x1,y - y1,z - z1).getId() != 0) {
                        entity.move((double)x1,(double)-y1,(double)-z1);
                        return;
                    }
//                    101   x+  y-  z+
                    if (level.getBlock(x + x1,y - y1,z + z1).getId() != 0) {
                        entity.move((double)x1,(double)-y1,(double)z1);
                        return;
                    }
//                    110   x+  y+  z-
                    if (level.getBlock(x + x1,y + y1,z - z1).getId() != 0) {
                        entity.move((double)x1,(double)y1,(double)-z1);
                        return;
                    }
//                    111   x+  y+  z-
                    if (level.getBlock(x + x1,y + y1,z - z1).getId() != 0) {
                        entity.move((double)x1,(double)y1,(double)-z1);
                        return;
                    }
                }
            }
        }
    }*/
}