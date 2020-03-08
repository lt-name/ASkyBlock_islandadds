/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 扫描树叶附近                 */
package name.Obsidian.Tasks;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;

public class Leaves_Task extends Task {

    private Block block;

    public Leaves_Task(Block block) {
        this.block = block;
    }

    @Override
    public void onRun(int i) {
        int x = block.getFloorX();
        int y = block.getFloorY();
        int z = block.getFloorZ();
        Level level = block.getLevel();
        //以方块为中心，扫描Y九格，扫描X和Z七格
        //扫描Y
        for (int y1=0; y1<9; y1++) {
            //扫描X
            for (int x1=0; x1<7; x1++) {
                //扫描Z
                for (int z1=0; z1<7; z1++) {
//                    000   x-  y-   z-
                    if (level.getBlock(x - x1,y - y1,z - z1).getName().contains("Leaves")) {return;}
//                    001   x-  y-  z+
                    if (level.getBlock(x - x1,y - y1,z + z1).getName().contains("Leaves")) {return;}
//                    010   x-  y+  z-
                    if (level.getBlock(x - x1,y + y1,z - z1).getName().contains("Leaves")) {return;}
//                    011   x-  y+  z+
                    if (level.getBlock(x - x1,y + y1,z + z1).getName().contains("Leaves")) {return;}
//                    100   x+  y-  z-
                    if (level.getBlock(x + x1,y - y1,z - z1).getName().contains("Leaves")) {return;}
//                    101   x+  y-  z+
                    if (level.getBlock(x + x1,y - y1,z + z1).getName().contains("Leaves")) {return;}
//                    110   x+  y+  z-
                    if (level.getBlock(x + x1,y + y1,z - z1).getName().contains("Leaves")) {return;}
//                    111   x+  y+  z-
                    if (level.getBlock(x + x1,y + y1,z - z1).getName().contains("Leaves")) {return;}
                }
            }
        }
        //调试状态
        level.dropItem(block, Item.get(6,0));
        Server.getInstance().getLogger().info("没有扫描到附近的树叶");
    }
}
