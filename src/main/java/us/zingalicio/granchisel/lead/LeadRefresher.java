package us.zingalicio.granchisel.lead;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import us.zingalicio.cordstone.util.ChunkUtil;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

public class LeadRefresher 
{
	public static void refresh(Player p, LivingEntity e, Entity h)
	{
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.ATTACH_ENTITY);
		packet.getIntegers()
			.write(0, 1)
			.write(1, e.getEntityId())
			.write(2, h.getEntityId());
		
		try
		{
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
		}
		catch(InvocationTargetException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void refresh(Player p, int radius)
	{
		ArrayList<Chunk> chunks = ChunkUtil.getChunks(p.getLocation(), 3);
		for(Chunk c : chunks)
		{
			for(Entity e : c.getEntities())
			{
				if(e instanceof LivingEntity && ((LivingEntity) e).isLeashed())
				{
					refresh(p, (LivingEntity) e, ((LivingEntity) e).getLeashHolder());
				}
			}
		}
	}
}
