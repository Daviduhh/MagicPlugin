package com.elmakers.mine.bukkit.utilities;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.elmakers.mine.bukkit.dao.MaterialList;
import com.elmakers.mine.bukkit.plugins.magic.PlayerSpells;
import com.elmakers.mine.bukkit.plugins.magic.SpellResult;

public class ReplaceMaterialAction extends SimpleBlockAction
{
	protected PlayerSpells playerSpells;
	protected Material replace;
	protected byte replaceData;
	protected MaterialList replaceable = new MaterialList();

	public ReplaceMaterialAction(PlayerSpells playerSpells, Block targetBlock, Material replaceMaterial, byte replaceData)
	{
		this.playerSpells = playerSpells;
		replaceable.add(targetBlock.getType());
		replace = replaceMaterial;
		this.replaceData = replaceData;
	}

	public ReplaceMaterialAction(PlayerSpells playerSpells, Material replaceMaterial, byte replaceData)
	{
		this.playerSpells = playerSpells;
		replace = replaceMaterial;
		this.replaceData = replaceData;
	}

	public void addReplaceable(Material material)
	{
		replaceable.add(material);
	}

	@SuppressWarnings("deprecation")
	public SpellResult perform(Block block)
	{
		if (replace == null)
		{
			return SpellResult.FAILURE;
		}
		
		if (!playerSpells.hasBuildPermission(block))
		{
			return SpellResult.INSUFFICIENT_PERMISSION;
		}
		
		if (playerSpells.isIndestructible(block))
		{
			return SpellResult.FAILURE;
		}

		if (replaceable == null || replaceable.contains(block.getType()))
		{
			block.setType(replace);
			block.setData(replaceData);
			super.perform(block);
			return SpellResult.SUCCESS;
		}

		return SpellResult.FAILURE;
	}
}
