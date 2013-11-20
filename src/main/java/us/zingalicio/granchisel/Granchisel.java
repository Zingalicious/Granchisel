package us.zingalicio.granchisel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import us.zingalicio.granchisel.persistence.ItemData;
import us.zingalicio.granchisel.persistence.LeashData;

public class Granchisel extends JavaPlugin
{	
	PluginManager pm;
	GranchiselListener listener;
	
	@Override
	public void onEnable()
	{
		pm = this.getServer().getPluginManager();
		listener = new GranchiselListener(this);
		
		pm.registerEvents(listener, this);
		
		setupDatabase();
	}
	
	private void setupDatabase()
	{
		try
		{
			getDatabase().find(LeashData.class).findRowCount();
			getDatabase().find(ItemData.class).findRowCount();
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
		list.add(LeashData.class);
		list.add(ItemData.class);
		return list;
	}
}
