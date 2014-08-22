package us.zingalicio.granchisel.lead;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;
import org.bukkit.inventory.ItemStack;

public class LeadPlacer 
{
	public static boolean execute(Player player, Entity entity)
	{
		if(player.isSneaking())
		{
			ArrayList<LivingEntity> playerLeading = LeadUtil.getLeading(player);
			if(playerLeading.size() != 0)
			{
				give(entity, playerLeading);
				return true;
			}
			if(give(player, entity))
			{
				return true;
			}
		}
		if(LeadUtil.vehicles.containsKey(entity.getType()))
		{
			return false;
		}
		if(player.getItemInHand().getType() == Material.LEASH)
		{
			return give(player, entity);
		}
		ArrayList<LivingEntity> entityLeading = LeadUtil.getLeading(entity);
		if(entityLeading.size() != 0)
		{
			give(player, entityLeading);
			return true;
		}
		return give(player, entity);
	}
	
	private static void give(Entity holder, ArrayList<LivingEntity> entities)
	{
		for(LivingEntity e : entities)
		{
			disconnect(e, UnleashReason.PLAYER_UNLEASH);
			connect(e, holder);
		}
	}
	
	private static boolean give(Player player, Entity entity)
	{
		if(entity instanceof LivingEntity && LeadUtil.getLed((LivingEntity) entity) != null && LeadUtil.getLed((LivingEntity) entity) != player)
		{
			disconnect((LivingEntity) entity, UnleashReason.PLAYER_UNLEASH);
			connect((LivingEntity) entity, player);
			return true;
		}
		return false;
	}
	
	public static void connect(LivingEntity entity, Entity holder)
	{
		if(entity == holder)
		{
			entity.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.LEASH));
			return;
		}
		else
		{
			entity.setLeashHolder(holder);
		}
	}
	public static void disconnect(LivingEntity entity, UnleashReason reason)
	{
		entity.setLeashHolder(null);
		
		EntityUnleashEvent event = new EntityUnleashEvent(entity, reason);
		Bukkit.getPluginManager().callEvent(event);
	}
}
