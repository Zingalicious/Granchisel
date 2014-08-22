package us.zingalicio.granchisel.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.zingalicio.granchisel.Granchisel;
import us.zingalicio.granchisel.lead.LeadRefresher;

public class RefreshLeads implements CommandExecutor
{	
	public RefreshLeads(Granchisel plugin)
	{
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) 
	{
		if(sender instanceof Player)
		{
			LeadRefresher.refresh((Player) sender, 5);
		}
		return false;
	}
	
}
