package us.zingalicio.granchisel.persistence;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity
@Table(name="granchisel_lamps")
public class LampData 
{
	@Id
	private int id;
	
	@NotNull
	private int x;
	
	@NotNull
	private int y;
	
	@NotNull
	private int z;
	
	@NotNull
	private UUID world;

	public int getId()
	{
		return id;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getZ()
	{
		return z;
	}
	public UUID getWorld()
	{
		return world;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = id;
	}
	public void setZ(int z)
	{
		this.z = z;
	}
	public void setWorld(UUID world)
	{
		this.world = world;
	}
}
