package us.zingalicio.granchisel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.avaje.ebean.EbeanServer;

import us.zingalicio.granchisel.Granchisel;
import us.zingalicio.granchisel.persistence.LeashData;

public class LeadUtil 
{
	public Map<EntityType,Double> leashable = new HashMap<>();
	public Map<EntityType,Double> specialLeashable = new HashMap<>();
	private final Granchisel plugin;
	private final EbeanServer database;
	
	public LeadUtil(Granchisel plugin)
	{	
		this.plugin = plugin;
		
		this.database = this.plugin.getDatabase();
		
		leashable.put(EntityType.BLAZE, .3);
		leashable.put(EntityType.CAVE_SPIDER, .3);
		leashable.put(EntityType.CHICKEN, .1);
		leashable.put(EntityType.COW, .7);
		leashable.put(EntityType.CREEPER, .3);
		leashable.put(EntityType.GHAST, .9);
		leashable.put(EntityType.GIANT, .9);
		leashable.put(EntityType.HORSE, .9);
		leashable.put(EntityType.IRON_GOLEM, .8);
		leashable.put(EntityType.MAGMA_CUBE, .2);
		leashable.put(EntityType.MUSHROOM_COW, .5);
		leashable.put(EntityType.OCELOT, .1);
		leashable.put(EntityType.PIG, .2);
		leashable.put(EntityType.PIG_ZOMBIE, .4);
		leashable.put(EntityType.PLAYER, .4);
		leashable.put(EntityType.SHEEP, .3);
		leashable.put(EntityType.SILVERFISH, .1);
		leashable.put(EntityType.SKELETON, .3);
		leashable.put(EntityType.SLIME, .2);
		leashable.put(EntityType.SNOWMAN, .2);
		leashable.put(EntityType.SPIDER, .2);
		leashable.put(EntityType.SQUID, .1);
		leashable.put(EntityType.VILLAGER, .4);
		leashable.put(EntityType.WITCH, .4);
		leashable.put(EntityType.WOLF, .2);
		leashable.put(EntityType.ZOMBIE, .4);
		
		specialLeashable.put(EntityType.BOAT, .6);
		specialLeashable.put(EntityType.MINECART, .8);
		specialLeashable.put(EntityType.MINECART_CHEST, .9);
		specialLeashable.put(EntityType.MINECART_FURNACE, .9);
		specialLeashable.put(EntityType.MINECART_HOPPER, .9);
		specialLeashable.put(EntityType.MINECART_MOB_SPAWNER, .9);
		specialLeashable.put(EntityType.MINECART_TNT, .9);
	}
	
	
	public void addConnection(Entity entity, Entity holder)
	{
		UUID entityId = entity.getUniqueId();
		UUID holderId = holder.getUniqueId();

		LeashData connection = this.database.find(LeashData.class).where().eq("entity", entityId).eq("holder", holderId).findUnique();
		
		if(connection == null)
		{
			connection = new LeashData();
			connection.setEntity(entityId);
			connection.setHolder(holderId);
		}
		
		this.database.save(connection);
	}
	
	public void breakConnection(Entity entity)
	{
		UUID entityId = entity.getUniqueId();
		List<LeashData> connections = this.database.find(LeashData.class).where().eq("entity", entityId).findList();
		
		for(LeashData c : connections)
		{
			if(c != null)
			{
				this.database.delete(c);
			}
		}
	}
	
	public ArrayList<Entity> getLeading(Entity holder)
	{
		UUID holderId = holder.getUniqueId();
		List<LeashData> connections = this.database.find(LeashData.class).where().eq("holder", holderId).findList();
		
		if(connections.isEmpty())
		{
			return null;
		}
		else
		{
			ArrayList<Entity> connected = new ArrayList<>();
			
			for(LeashData l : connections)
			{
				for(Entity e : holder.getWorld().getEntitiesByClass(LivingEntity.class))
				{
					if(e.getUniqueId().equals(l.getEntity()))
					{
						connected.add(e);
					}
				}
			}
			
			if(connected.isEmpty())
			{
				return null;
			}
			return connected;
		}
	}

	public Entity getLed(Entity entity)
	{
		UUID entityId = entity.getUniqueId();
		LeashData connection = this.database.find(LeashData.class).where().eq("entity", entityId).findUnique();
		
		if(connection != null)
		{
			Entity holder = null;
			for(Entity e : entity.getWorld().getEntitiesByClass(LivingEntity.class))
			{
				if(e.getUniqueId().equals(connection.getEntity()))
				{
					holder = e;
				}
			}
		return holder;
		}
		else
		{
			return null;
		}
	}
}
