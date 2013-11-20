package us.zingalicio.granchisel.persistence;

import java.util.UUID;

import com.avaje.ebean.validation.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="granchisel_leash")
public class LeashData
{
	@Id
	private int id;
	
	@NotNull
	private UUID entity;
	
	@NotNull
	private UUID holder;
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setEntity(UUID entity)
	{
		this.entity = entity;
	}
	
	public UUID getEntity()
	{
		return entity;
	}
	
	public void setHolder(UUID holder)
	{
		this.holder = holder;
	}
	
	public UUID getHolder()
	{
		return holder;
	}
}
