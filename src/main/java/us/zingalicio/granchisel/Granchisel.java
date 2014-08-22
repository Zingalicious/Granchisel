package us.zingalicio.granchisel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import us.zingalicio.cordstone.ZingPlugin;
import us.zingalicio.granchisel.commands.RefreshLeads;
import us.zingalicio.granchisel.lead.LeadListener;
import us.zingalicio.granchisel.persistence.LampData;

public class Granchisel extends ZingPlugin
{
	private final LeadListener leadListener;
	private final ProtocolManager protocolManager;
	private final RefreshLeads refreshLeads;
	
	public Granchisel()
	{
		leadListener = new LeadListener(this);
		protocolManager = ProtocolLibrary.getProtocolManager();
		refreshLeads = new RefreshLeads(this);
	}
	
	@Override
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(leadListener, this);
		getCommand("refreshleads").setExecutor(refreshLeads);
		
		setupDatabase();
	}
	
	private void setupDatabase()
	{
		try
		{
			getDatabase().find(LampData.class).findRowCount();
		}
		catch (PersistenceException ex)
		{
			Bukkit.getLogger().log(Level.INFO, "Installing database for Granchisel due to first time usage.");
			installDDL();
		}
	}
	
	@Override
	public List<Class<?>> getDatabaseClasses()
	{
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(LampData.class);
		return list;
	}

	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}
}
