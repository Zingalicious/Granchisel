package us.zingalicio.granchisel.lead;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.avaje.ebean.EbeanServer;

import us.zingalicio.granchisel.persistence.LeashData;

public class Reconnect extends BukkitRunnable
{
	EbeanServer database;
	List<World> worlds;
	Chunk chunk;
	
	public Reconnect(EbeanServer database, Chunk chunk)
	{
		this.database = database;
		worlds = Bukkit.getWorlds();
		this.chunk = chunk;
	}

	@Override
	public void run()
	{
		List<LeashData> connections = this.database.find(LeashData.class).findList();
		for(LeashData c : connections)
		{
			Entity entity = null;
			Entity holder = null;
			
				for(Entity e : chunk.getEntities())
				{
					if(e.getUniqueId().equals(c.getEntity()))
					{
						entity = e;
					}
					if(e.getUniqueId().equals(c.getHolder()))
					{
						holder = e;
					}
				}
			if(entity != null && holder != null)
			{
				((LivingEntity) entity).setLeashHolder(holder);
			}
			if((entity != null && holder == null) || (entity == null && holder != null))
			{
				this.database.delete(c);
			}
		}
	}
}
