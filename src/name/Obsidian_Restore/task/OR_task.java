package name.Obsidian_Restore.task;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;

public class OR_task extends Task {

    private Player player;

    public OR_task(Player player) {
        this.player = player;
    }

    @Override
    public void onRun(int i) {
        this.player.getInventory().addItem(Item.get(325,10));
        this.player.sendMessage(TextFormat.GREEN + ">>" + TextFormat.YELLOW + "岩浆已经放到你的背包啦，下次要小心一点哦！");
    }
}
