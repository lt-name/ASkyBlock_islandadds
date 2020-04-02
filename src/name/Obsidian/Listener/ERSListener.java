package name.Obsidian.Listener;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockIds;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.level.Level;
import name.Obsidian.Obsidian;

public class ERSListener implements Listener {

    @EventHandler
    public void onBUP(BlockUpdateEvent event) {
        //允许错误的刷石机
        if (Obsidian.get().getERS()) {
            Block block = event.getBlock();
            int x = block.getX();
            int y = block.getY();
            int z = block.getZ();
            Level level = block.getLevel();
            if((level != null) && (block.getId() == BlockIds.OBSIDIAN && block.getMeta() == 0)){
                if(level.getBlock(x-1, y, z).getId() == BlockIds.WATER){
                    level.setBlock(level.getBlock(x-1, y, z).getPosition(), BlockIds.COBBLESTONE);
                    level.setBlock(block.getPosition(), BlockIds.LAVA);
                    return;
                }
                if(level.getBlock(x+1, y, z).getId() == BlockIds.WATER){
                    level.setBlock(level.getBlock(x+1, y, z).getPosition(), BlockIds.COBBLESTONE);
                    level.setBlock(block.getPosition(), BlockIds.LAVA);
                    return;
                }
                if(level.getBlock(x, y, z-1).getId() == BlockIds.WATER){
                    level.setBlock(level.getBlock(x, y, z-1).getPosition(), BlockIds.COBBLESTONE);
                    level.setBlock(block.getPosition(), BlockIds.LAVA);
                    return;
                }
                if(level.getBlock(x, y, z+1).getId() == BlockIds.WATER){
                    level.setBlock(level.getBlock(x, y, z+1).getPosition(), BlockIds.COBBLESTONE);
                    level.setBlock(block.getPosition(), BlockIds.LAVA);
                    return;
                }
                if(level.getBlock(x, y+1, z).getId() == BlockIds.WATER){
                    level.setBlock(level.getBlock(x, y+1, z).getPosition(), BlockIds.COBBLESTONE);
                    level.setBlock(block.getPosition(), BlockIds.LAVA);
                }
            }
        }
    }

}
