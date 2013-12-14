package com.roflnoob.ptt.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.roflnoob.ptt.tileentity.TileEntityPlayerTransportTube;

public class PlayerTransportTube extends BlockContainer {

	public PlayerTransportTube(int par1) {
		super(par1, Material.iron);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		TileEntityPlayerTransportTube ptt = (TileEntityPlayerTransportTube) par1World.getBlockTileEntity(par2, par3, par4);
		if (ptt != null)

			super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World) {
		return new TileEntityPlayerTransportTube();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		TileEntityPlayerTransportTube ptt = (TileEntityPlayerTransportTube) par1World.getBlockTileEntity(par2, par3, par4);
		if (ptt != null) {
			// System.out.println("--------------------------------------------");
			// System.out.println("-My network contains:                      -");
			// for (int x = 1; x < ptt.network.size(); x++) {
			// System.out.println(ptt.network.get(x) + " : X=" +
			// ptt.network.get(x).xCoord + " Y=" + ptt.network.get(x).yCoord +
			// " Z=" + ptt.network.get(x).zCoord);
			// }
			// System.out.println("--------------------------------------------");
			System.out.println(ptt.network.size() + " " + ptt.id);
			if (ptt.networkHost != null)
				System.out.println(ptt.networkHost);
			System.out.println(ptt);
			if (ptt.networkHost != null)
				par5EntityPlayer.setPositionAndUpdate(ptt.networkHost.xCoord + 0.5D, ptt.networkHost.yCoord + 1, ptt.networkHost.zCoord + 0.5D);
		}
		return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileEntityPlayerTransportTube ptt = (TileEntityPlayerTransportTube) par1World.getBlockTileEntity(par2, par3, par4);
		ptt.onBroken();
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		TileEntityPlayerTransportTube ptt = (TileEntityPlayerTransportTube) par1World.getBlockTileEntity(par2, par3, par4);
		ptt.onPlaced();
		super.onBlockAdded(par1World, par2, par3, par4);
	}

}
