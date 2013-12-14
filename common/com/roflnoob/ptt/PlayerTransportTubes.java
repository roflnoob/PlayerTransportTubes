/**
 * 
 * BY ROFLNOOB FOR MODJAM 2013
 * PLAYER TRANSPORT TUBES
 * 
 */

package com.roflnoob.ptt;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;

import com.roflnoob.ptt.blocks.PlayerTransportTube;
import com.roflnoob.ptt.lib.PTTReference;
import com.roflnoob.ptt.packages.PTTPackageHandler;
import com.roflnoob.ptt.proxy.PTTCommonProxy;
import com.roflnoob.ptt.tileentity.TileEntityPlayerTransportTube;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = PTTReference.MOD_ID, name = PTTReference.MOD_NAME, version = PTTReference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {}, packetHandler = PTTPackageHandler.class)
public class PlayerTransportTubes {

	/**
	 * Instantiate the mod
	 */
	@Instance(PTTReference.MOD_ID)
	public static PlayerTransportTubes instance;

	/**
	 * Register the proxy
	 */
	@SidedProxy(clientSide = PTTReference.CLIENT_PROXY_CLASS, serverSide = PTTReference.SERVER_PROXY_CLASS)
	public static PTTCommonProxy proxy;

	public static Block playerTransportTube;

	/**
	 * What happens BEFORE minecraft starts? -We make a config file and get the
	 * Item ids
	 * 
	 * @param event
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		config.save();
		instance = this;
	}

	/**
	 * What happens when minecraft starts? -We register everything: -Items,
	 * entities, renderers, blocks
	 * 
	 * @param event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerTileEntity(TileEntityPlayerTransportTube.class, "tileEntityPlayerTube");
		playerTransportTube = new PlayerTransportTube(500).setUnlocalizedName(PTTReference.BLOCK_PTT).setCreativeTab(CreativeTabs.tabRedstone);
		GameRegistry.registerBlock(playerTransportTube, PTTReference.MOD_ID + playerTransportTube.getUnlocalizedName().substring(5));
		LanguageRegistry.addName(playerTransportTube, PTTReference.BLOCK_PTT_DISPLAYNAME);
	}

	/**
	 * What happens after minecraft has started?
	 * 
	 * @param event
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
