package name.islandadds;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;

public class delaytask extends PluginTask {

    private Player player;

    public delaytask(islandadds owner, Player player) {
        super(owner);
        this.player = player;
    }

    @Override
    public void onRun(int i) {
        this.player.getInventory().addItem(Item.get(325,10));
        this.player.sendMessage(TextFormat.GREEN + ">>" + TextFormat.YELLOW + "已将黑曜石还原为岩浆,下次要小心一点哦");
    }
}
