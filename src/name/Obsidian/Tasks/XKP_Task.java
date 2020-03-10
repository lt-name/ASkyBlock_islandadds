/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 扫描附近空岛                */
package name.Obsidian.Tasks;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.Task;
import com.larryTheCoder.ASkyBlock;
import name.Obsidian.Obsidian;

public class XKP_Task extends Task {

    private Player player;
    private Level level;

    public XKP_Task(Player player, Level level) {
        this.player = player;
        this.level = level;
    }

    public void onRun(int i){
        if ((this.player == null) || (this.level == null)) { return; }
        if (Obsidian.get().getXKPM() == 2) {
            int x = this.player.getFloorX();
            int z = this.player.getFloorZ();
            //丧心病狂的扫描，这次总不会漏掉了吧
            //建筑建那么高？那您还是回主空岛去吧
            for (int y1 = 200; y1 > 0; --y1) {
                for (int x1 = 0; x1 < 40; ++x1) {
                    for (int z1 = 0; z1 < 40; ++z1) {
                        //获取合适的落脚点，岩浆和水当然不行
                        if ((this.level.getBlock(x - x1, y1, z - z1).getId() != 0) &&
                                (!this.level.getBlock(x - x1, y1, z - z1).getName().contains("Water")) &&
                                (!this.level.getBlock(x - x1, y1, z - z1).getName().contains("Lava"))) {
                            //还得防止传送到墙里
                            if ((this.level.getBlock(x - x1,y1 + 1,z - z1).getId() == 0) &&
                                    (this.level.getBlock(x - x1,y1 + 2,z - z1).getId() == 0)){
                                this.player.teleport(new Position(x - x1, y1 + 1, z - z1, level));
                                this.player.sendMessage("§a[虚空保护]：已将您拉回最近的空岛！");
                                return;
                            }
                        }
                        if ((this.level.getBlock(x - x1, y1, z + z1).getId() != 0) &&
                                (!this.level.getBlock(x - x1, y1, z + z1).getName().contains("Water")) &&
                                (!this.level.getBlock(x - x1, y1, z + z1).getName().contains("Lava"))) {
                            if ((this.level.getBlock(x - x1,y1 + 1,z + z1).getId() == 0) &&
                                    (this.level.getBlock(x - x1,y1 + 2,z + z1).getId() == 0)) {
                                this.player.teleport(new Position(x - x1, y1 + 1, z + z1, level));
                                this.player.sendMessage("§a[虚空保护]：已将您拉回最近的空岛！");
                                return;
                            }
                        }
                        if ((this.level.getBlock(x + x1, y1, z - z1).getId() != 0) &&
                                (!this.level.getBlock(x + x1, y1, z - z1).getName().contains("Water")) &&
                                (!this.level.getBlock(x + x1, y1, z - z1).getName().contains("Lava"))) {
                            if ((this.level.getBlock(x + x1,y1 + 1,z - z1).getId() == 0) &&
                                    (this.level.getBlock(x + x1,y1 + 2,z - z1).getId() == 0)) {
                                this.player.teleport(new Position(x + x1, y1 + 1, z - z1, level));
                                this.player.sendMessage("§a[虚空保护]：已将您拉回最近的空岛！");
                                return;
                            }
                        }
                        if ((this.level.getBlock(x + x1, y1, z + z1).getId() != 0) &&
                                (!this.level.getBlock(x + x1, y1, z + z1).getName().contains("Water")) &&
                                (!this.level.getBlock(x + x1, y1, z + z1).getName().contains("Lava"))) {
                            if ((this.level.getBlock(x + x1,y1 + 1,z + z1).getId() == 0) &&
                                    (this.level.getBlock(x + x1,y1 + 2,z + z1).getId() == 0)) {
                                this.player.teleport(new Position(x + x1, y1 + 1, z + z1, level));
                                this.player.sendMessage("§a[虚空保护]：已将您拉回最近的空岛！");
                                return;
                            }
                        }
                    }
                }
            }
            player.sendMessage("§a[虚空保护]：附近没有空岛,已将您拉回主空岛！");
        }else {
            player.sendMessage("§a[虚空保护]：已将您拉回主空岛！");
        }
        ASkyBlock.get().getGrid().homeTeleport(player);
    }
}
