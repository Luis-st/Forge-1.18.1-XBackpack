package net.luis.xbackpack.network.packet;

import java.util.function.Supplier;

import net.luis.xbackpack.BackpackConstans;
import net.luis.xbackpack.world.capability.XBackpackCapabilities;
import net.luis.xbackpack.world.inventory.BackpackToolSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkEvent.Context;

/**
 * 
 * @author Luis-st
 *
 */

public class BackpackToolTop {
	
	/**
	 * encode the data of the {@link BackpackToolTop} packet,<br>
	 * at the moment no data to encode
	 */
	public static void encode(BackpackToolTop packet, FriendlyByteBuf byteBuf) {
		
	}
	
	/**
	 * decode the data of the {@link BackpackToolTop} packet,<br>
	 * at the moment no data to decode
	 */
	public static BackpackToolTop decode(FriendlyByteBuf byteBuf) {
		return new BackpackToolTop();
	}
	
	/**
	 * handle the {@link BackpackToolTop} packet on server<br>
	 * <br>
	 * swap the {@link ItemStack} of the {@link Player#getMainHandItem()}<br>
	 * and the {@link ItemStack} of the upper {@link BackpackToolSlot}<br>
	 * <br>
	 * contains a simple anti cheat,<br> 
	 * which checks if the {@link ItemStack} is valid for the {@link BackpackToolSlot}
	 */
	public static void handle(BackpackToolTop packet, Supplier<Context> context) {
		ServerPlayer player = context.get().getSender();
		context.get().enqueueWork(() -> {
			IItemHandlerModifiable itemModifiable = player.getCapability(XBackpackCapabilities.BACKPACK, null).orElseThrow(NullPointerException::new);
			ItemStack main = player.getMainHandItem().copy();
			ItemStack top = itemModifiable.getStackInSlot(BackpackConstans.BACKPACK_TOOL_SLOT_TOP).copy();	
			if (BackpackConstans.VALID_TOOL_SLOT_ITEMS.contains(main.getItem())) {
				player.setItemInHand(InteractionHand.MAIN_HAND, top);
				itemModifiable.setStackInSlot(BackpackConstans.BACKPACK_TOOL_SLOT_TOP, main);
			}
		});
		context.get().setPacketHandled(true);
	}
	
}
