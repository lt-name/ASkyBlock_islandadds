/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 扫描树叶附近                 */
package name.Obsidian.Tasks;

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
        if (block.getName().contains("Leaves")) {
            int x = block.getFloorX();
            int y = block.getFloorY();
            int z = block.getFloorZ();
            Level level = block.getLevel();
            //以方块为中心，扫描周边八个 Y九格，扫描X和Z七格 的方形区块
            //也就是 长14 宽14 高18的长方形范围
            for (int y1=0; y1<9; y1++) {
                for (int x1=0; x1<7; x1++) {
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
            //判断树叶类型，并掉落树苗
            //这随机的子ID是什么玩意o(╥﹏╥)o害得我检查了半天
            if (block.getId() == 18) {
                switch (block.getDamage()){
                    //橡树叶
                    case 0:
                    case 4:
                        level.dropItem(block, Item.get(6,0));
                        break;
                    //云杉叶
                    case 1:
                    case 5:
                        level.dropItem(block, Item.get(6,1));
                        break;
                    //桦木叶
                    case 2:
                    case 6:
                        level.dropItem(block, Item.get(6,2));
                        break;
                    //从林木叶
                    case 3:
                    case 7:
                        level.dropItem(block, Item.get(6,3));
                        break;
                }
            }else if ((block.getDamage() == 0) || (block.getDamage() == 4)){  //合金欢叶
                level.dropItem(block, Item.get(6,4));
            }else if ((block.getDamage() == 1) || (block.getDamage() == 5)){ //深色橡木叶
                level.dropItem(block, Item.get(6,5));
            }
        }
    }
}
