package us.zingalicio.granchisel;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;

import us.zingalicio.granchisel.lead.DelayLead;
import us.zingalicio.granchisel.lead.LeadPlacer;
import us.zingalicio.granchisel.lead.Reconnect;
import us.zingalicio.granchisel.util.LeadUtil;

public class GranchiselListener implements Listener
{
	private final Granchisel plugin;
	private final LeadUtil leadUtil;
	private final LeadPlacer leadPlacer;
	
	public GranchiselListener(final Granchisel plugin)
	{
		this.plugin = plugin;
		leadUtil = new LeadUtil(plugin);
		leadPlacer = new LeadPlacer(leadUtil);
	}
	
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		
		if(entity == leadUtil.getLeading(player))
		{
			event.setCancelled(true);
		}
		
		DelayLead delayLead = new DelayLead(leadUtil, leadPlacer, entity, player, 
				((LivingEntity) entity).isLeashed() && ((LivingEntity) entity).getLeashHolder() == player);
		delayLead.runTaskLater(this.plugin, 1);
		
	}
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event)
	{
		Reconnect reconnect = new Reconnect(this.plugin.getDatabase(), event.getChunk());
		reconnect.runTaskLater(this.plugin,20);
	}
	
	@EventHandler
	public void onPlayerLeashEntity(PlayerLeashEntityEvent event)
	{
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityUnleash(EntityUnleashEvent event)
	{
		leadUtil.breakConnection(event.getEntity());
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		Entity entity = event.getEntity();
		Entity led = leadUtil.getLed(entity);
		if(led != null)
		{
			leadPlacer.disconnect(entity, UnleashReason.UNKNOWN);
			Location loc = entity.getLocation();
			ItemStack item = new ItemStack(Material.LEASH);
			World world = loc.getWorld();
			world.dropItem(loc, item);
		}
	}
}
