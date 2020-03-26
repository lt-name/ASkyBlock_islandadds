package name.Obsidian.Listener;

import cn.nukkit.block.Block;
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

}
