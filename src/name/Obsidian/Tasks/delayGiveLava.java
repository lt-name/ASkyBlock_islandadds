/*
   ___   _           _      _  _
  / _ \ | |__   ___ (_)  __| |( )  __ _  _ __
 | | | ||  _ \ / __|| | / _  || | / _  ||  _ \
 | |_| || |_) |\__ \| || (_| || || (_| || | | |
  \___/ |_ __/ |___/|_| \__ _||_| \__ _||_| |_|

Obsidian - 黑曜石 - 延迟给岩浆                   */
package name.Obsidian.Tasks;

import cn.nukkit.entity.vehicle.Minecart;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemIds;
import cn.nukkit.player.Player;
import cn.nukkit.registry.ItemRegistry;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;

public class delayGiveLava extends Task {

    private Player player;

    public delayGiveLava(Player player) {
        this.player = player;
    }

    @Override
    public void onRun(int i) {
        //this.player.getInventory().addItem(Item.get());
        this.player.getInventory().addItem(Item.get(ItemRegistry.get().fromLegacy(325), 10));
        this.player.sendMessage(TextFormat.GREEN + ">>" + TextFormat.YELLOW + "岩浆已经放到你的背包啦，下次要小心一点哦！");
    }
}
