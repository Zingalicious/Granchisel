package us.zingalicio.granchisel.lead;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;

import us.zingalicio.granchisel.util.LeadUtil;

public class LeadPlacer 
{	
	LeadUtil leadUtil;
	
	public LeadPlacer(LeadUtil leadUtil)
	{
		this.leadUtil = leadUtil;
	}
	
	public void connect(Entity entity, Entity holder)
	{
		if(this.leadUtil.leashable.containsKey(holder.getType()) 
				&& this.leadUtil.leashable.containsKey(entity.getType()))
		{
			if(((LivingEntity) entity).setLeashHolder(holder))
			{
				leadUtil.addConnection(entity, holder);
			}
		}
		
	}
	
	public void disconnect(Entity entity , UnleashReason reason)
	{
		((LivingEntity) entity).setLeashHolder(null);
		
		EntityUnleashEvent event = new EntityUnleashEvent(entity, reason);
		Bukkit.getPluginManager().callEvent(event);
	}
}
