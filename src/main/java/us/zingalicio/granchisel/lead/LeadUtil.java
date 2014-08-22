package us.zingalicio.granchisel.lead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class LeadUtil 
{
	public static final Map<EntityType, Float> leashable = new HashMap<EntityType, Float>();
	public static final Map<EntityType, Float> vehicles = new HashMap<EntityType, Float>();
	static {
		leashable.put(EntityType.BLAZE, .3F);
		leashable.put(EntityType.CAVE_SPIDER, .1F);
		leashable.put(EntityType.CHICKEN, .1F);
		leashable.put(EntityType.COW, .7F);
		leashable.put(EntityType.CREEPER, .3F);
		leashable.put(EntityType.GHAST, 1.5F);
		leashable.put(EntityType.GIANT, 3F);
		leashable.put(EntityType.HORSE, .9F);
		leashable.put(EntityType.IRON_GOLEM, .8F);
		leashable.put(EntityType.MAGMA_CUBE, .2F);
		leashable.put(EntityType.MUSHROOM_COW, .5F);
		leashable.put(EntityType.OCELOT, .1F);
		leashable.put(EntityType.PIG, .3F);
		leashable.put(EntityType.PIG_ZOMBIE, .4F);
		leashable.put(EntityType.PLAYER, .4F);
		leashable.put(EntityType.SHEEP, .3F);
		leashable.put(EntityType.SILVERFISH, .05F);
		leashable.put(EntityType.SKELETON, .3F);
		leashable.put(EntityType.SLIME, .2F);
		leashable.put(EntityType.SNOWMAN, .2F);
		leashable.put(EntityType.SPIDER, .2F);
		leashable.put(EntityType.SQUID, .1F);
		leashable.put(EntityType.VILLAGER, .4F);
		leashable.put(EntityType.WITCH, .4F);
		leashable.put(EntityType.WOLF, .3F);
		leashable.put(EntityType.ZOMBIE, .4F);
		
		vehicles.put(EntityType.BOAT, .6F);
		vehicles.put(EntityType.MINECART, .8F);
		vehicles.put(EntityType.MINECART_CHEST, .9F);
		vehicles.put(EntityType.MINECART_COMMAND, .9F);
		vehicles.put(EntityType.MINECART_FURNACE, .9F);
		vehicles.put(EntityType.MINECART_HOPPER, .9F);
		vehicles.put(EntityType.MINECART_MOB_SPAWNER, .9F);
		vehicles.put(EntityType.MINECART_TNT, .9F);
	}
	
	public static Entity getLed(LivingEntity entity)
	{
		if(!entity.isLeashed())
		{
			return null;
		}
		UUID id = entity.getLeashHolder().getUniqueId();
		for(Entity e : entity.getWorld().getEntities())
		{
			if(e.getUniqueId().equals(id))
			{
				return e;
			}
		}
		return null;
	}
	
	public static ArrayList<LivingEntity> getLeading(Entity holder)
	{
		UUID id = holder.getUniqueId();
		ArrayList<LivingEntity> leading = new ArrayList<>();
		for(LivingEntity e : holder.getWorld().getEntitiesByClass(LivingEntity.class))
		{
			if(e.isLeashed() && e.getLeashHolder().getUniqueId().equals(id))
			{
				leading.add(e);
			}
		}
		return leading;
	}
	
	public static Float getSpeedMod(LivingEntity entity)
	{
		float mod = 0;
		if(leashable.containsKey(entity.getType()))
		{
			mod += leashable.get(entity.getType());
		}
		ArrayList<LivingEntity> leading = getLeading(entity);
		for(LivingEntity e : leading)
		{
				mod += getSpeedMod(e);
		}
		return mod;
	}
}
