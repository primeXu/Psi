/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [May 31, 2014, 9:58:26 PM (GMT)]
 */
package vazkii.psi.client.core.version;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import vazkii.psi.common.lib.LibMisc;

public final class VersionChecker {

	private static final int FLAVOUR_MESSAGES = 10;

	public static boolean doneChecking = false;
	public static String onlineVersion = "";
	public static boolean triedToWarnPlayer = false;

	public static boolean startedDownload = false;
	public static boolean downloadedFile = false;

	public void init() {
		new ThreadVersionChecker();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if(doneChecking && event.phase == Phase.END && Minecraft.getMinecraft().thePlayer != null && !triedToWarnPlayer) {
			if(!onlineVersion.isEmpty()) {
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				int onlineBuild = Integer.parseInt(onlineVersion.split("-")[1]);
				int clientBuild = LibMisc.BUILD.contains("GRADLE") ? Integer.MAX_VALUE : Integer.parseInt(LibMisc.BUILD);
				if(onlineBuild > clientBuild) {
					player.addChatComponentMessage(new ChatComponentTranslation("psi.versioning.flavour" + player.worldObj.rand.nextInt(FLAVOUR_MESSAGES)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
					player.addChatComponentMessage(new ChatComponentTranslation("psi.versioning.outdated", clientBuild, onlineBuild));
					// TODO Write messages

					IChatComponent component = IChatComponent.Serializer.jsonToComponent(StatCollector.translateToLocal("psi.versioning.updateMessage").replaceAll("%version%", onlineVersion));
					player.addChatComponentMessage(component);
				}
			}

			triedToWarnPlayer = true;
		}
	}

}