package us.zingalicio.granchisel.lead;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import us.zingalicio.cordstone.util.ChunkUtil;
import us.zingalicio.granchisel.Granchisel;

public class LeadListener implements Listener
{
	Granchisel plugin;
	public LeadListener(Granchisel plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e)
	{
		Player p = e.getPlayer();
		Entity en = e.getRightClicked();
		e.setCancelled(LeadPlacer.execute(p, en));
	}
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e)
	{
		for(Entity entity : e.getChunk().getEntities())
		{
			if(entity instanceof LivingEntity && ((LivingEntity) entity).isLeashed())
			{
				for(Player p : e.getWorld().getPlayers())
				{
					if(p.getLocation().distance(entity.getLocation()) < Bukkit.getViewDistance())
					{
						LeadRefresher.refresh(p, (LivingEntity) entity, ((LivingEntity) entity).getLeashHolder());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e)
	{
		Location loc = e.getTo();
		ArrayList<Chunk> chunks = ChunkUtil.getChunks(loc, 3);
		for(Chunk c : chunks)
		{
			if(!c.isLoaded())
			{
				c.load();
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		ArrayList<Chunk> chunks = ChunkUtil.getChunks(loc, 3);
		for(Chunk c : chunks)
		{
			if(!c.isLoaded())
			{
				c.load();
			}
		}
		LeadRefresher.refresh(p, 5);
	}
}
