package us.zingalicio.granchisel.lead;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;
import org.bukkit.scheduler.BukkitRunnable;

import us.zingalicio.granchisel.util.LeadUtil;
 
public class DelayLead extends BukkitRunnable 
{
	private final LeadUtil leadUtil;
	private final LeadPlacer leadPlacer;
	private final Entity entity;
	private final Player player;
	private final boolean leash;
	private final boolean playerLeading;
	private final boolean b;
	
	public DelayLead(LeadUtil leadUtil, LeadPlacer leadPlacer, Entity entity, Player player, boolean b) 
	{
		this.leadUtil = leadUtil;
		this.leadPlacer = leadPlacer;
		this.entity = entity;
		this.player = player;
		this.leash = player.getItemInHand().getType() == Material.LEASH;
		this.playerLeading = leadUtil.getLeading(player) != null;
		this.b = b;
	}
	
	public void run() 
	{
		boolean led = leadUtil.getLed(entity) != null;
		boolean leading = leadUtil.getLeading(entity) != null;

		if(b)
		{
			leadPlacer.disconnect(entity, UnleashReason.PLAYER_UNLEASH);
			return;
		}
		
		if(playerLeading)
		{
			if(player.isSneaking())
			{
				if(leash)
				{
					if(leading)
					{
						if(led)
						{
							switchLeash();
							return;
						}
						else
						{
							leash();
							return;
						}
					}
					else
					{
						if(led)
						{
							switchLeash();
							return;
						}
						else
						{
							leash();
							return;
						}
					}
				}
				else
				{
					if(leading)
					{
						if(led)
						{
							takeLeash();
							return;
						}
						else
						{
							takeLeash();
							return;
						}
					}
					else
					{
						if(led)
						{
							switchLeash();
							return;
						}
						else
						{
							giveLeash();
							return;
						}
					}
				}
			}
			else
			{
				if(leash)
				{
					if(leading)
					{
						if(led)
						{
							giveLeash();
							return;
						}
						else
						{
							giveLeash();
							return;
						}
					}
					else
					{
						if(led)
						{
							giveLeash();
							return;
						}
						else
						{
							giveLeash();
							return;
						}
					}
				}
				else
				{
					if(leading)
					{
						if(led)
						{
							giveLeash();
							return;
						}
						else
						{
							giveLeash();
							return;
						}
					}
					else
					{
						if(led)
						{
							giveLeash();
							return;
						}
						else
						{
							giveLeash();
							return;
						}
					}
				}
			}
		}
		else
		{
			if(player.isSneaking())
			{
				if(leash)
				{
					if(leading)
					{
						if(led)
						{
							switchLeash();
							return;
						}
						else
						{
							leash();
							return;
						}
					}
					else
					{
						if(led)
						{
							leash();
							return;
						}
						else
						{
							leash();
							return;
						}
					}
				}
				else
				{
					if(leading)
					{
						if(led)
						{
							switchLeash();
							return;
						}
						else
						{
							takeLeash();
							return;
						}
					}
					else
					{
						if(led)
						{
							switchLeash();
							return;
						}
						else
						{
							return;
						}
					}
				}
			}
			else
			{
				if(leash)
				{
					if(leading)
					{
						if(led)
						{
							takeLeash();
							return;
						}
						else
						{
							takeLeash();
							return;
						}
					}
					else
					{
						if(led)
						{
							leash();
							return;
						}
						else
						{
							leash();
							return;
						}
					}
				}
				else
				{
					if(leading)
					{
						if(led)
						{
							takeLeash();
							return;
						}
						else
						{
							takeLeash();
							return;
						}
					}
					else
					{
						if(led)
						{
							switchLeash();
							return;
						}
						else
						{
							return;
						}
					}
				}
			}
		}
	}

	private void switchLeash() 
	{
		leadPlacer.disconnect(entity, UnleashReason.PLAYER_UNLEASH);
		leadPlacer.connect(entity, player);
	}

	private void giveLeash() 
	{
		ArrayList<Entity> held = leadUtil.getLeading(player);
		for(Entity e : held)
		{
			leadPlacer.disconnect(e, UnleashReason.PLAYER_UNLEASH);
			leadPlacer.connect(e, entity);
		}
	}

	private void leash() 
	{
		leadPlacer.connect(entity, player);
		takeLead();
	}

	private void takeLeash() 
	{
		ArrayList<Entity> held = leadUtil.getLeading(entity);
		for(Entity e : held)
		{
			leadPlacer.disconnect(e, UnleashReason.PLAYER_UNLEASH);
			leadPlacer.connect(e, player);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void takeLead()
	{
		player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
		player.updateInventory();
	}
}