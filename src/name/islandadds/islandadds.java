package name.islandadds;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class islandadds extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        Server.getInstance().getLogger().info("[islandadds] 加载完成！");
    }

    @EventHandler
    public void onInt(PlayerInteractEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Item item = e.getItem();
        if(item.getId() == 325 && item.getDamage() == 0){
            if(block.getId() == 49 && block.getDamage() == 0){
                if(player.isSneaking()){
                    block.getLevel().setBlock(block,Block.get(0));
                    player.getInventory().removeItem(Item.get(325,0));
                    this.getServer().getScheduler().scheduleDelayedTask(new delaytask(this,player),5);
                }
            }
        }
    }

}
