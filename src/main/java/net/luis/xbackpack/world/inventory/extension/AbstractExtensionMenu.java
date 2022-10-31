package net.luis.xbackpack.world.inventory.extension;

import java.util.function.Consumer;

import net.luis.xbackpack.world.extension.BackpackExtension;
import net.luis.xbackpack.world.inventory.AbstractExtensionContainerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * 
 * @author Luis-st
 *
 */

public abstract class AbstractExtensionMenu {
	
	protected final AbstractExtensionContainerMenu menu;
	protected final Player player;
	private final BackpackExtension extension;
	
	protected AbstractExtensionMenu(AbstractExtensionContainerMenu menu, Player player, BackpackExtension extension) {
		this.menu = menu;
		this.player = player;
		this.extension = extension;
	}
	
	public void open() {
		
	}
	
	public abstract void addSlots(Consumer<Slot> consumer);
	
	public boolean requiresTickUpdate() {
		return false;
	}
	
	public void slotsChanged(Container container) {
		
	}
	
	public void slotsChanged() {
		
	}
	
	public boolean clickMenuButton(Player player, int button) {
		return true;
	}
	
	public abstract boolean quickMoveStack(ItemStack slotStack, int index);
	
	protected boolean movePreferredMenu(ItemStack slotStack) {
		if (!this.menu.moveItemStackTo(slotStack, 0, 873, false)) { // into menu
			if (!this.menu.moveItemStackTo(slotStack, 900, 909, false)) { // into hotbar
				if (!this.menu.moveItemStackTo(slotStack, 873, 900, false)) { // into inventory
					return false;
				}
			}
		}
		return true;
	}
	
	protected boolean movePreferredInventory(ItemStack slotStack) {
		if (!this.menu.moveItemStackTo(slotStack, 900, 909, false)) { // into hotbar
			if (!this.menu.moveItemStackTo(slotStack, 873, 900, false)) { // into inventory
				if (!this.menu.moveItemStackTo(slotStack, 0, 873, false)) { // into menu
					return false;
				}
			}
		}
		return true;
	}
	
	public void close() {
		
	}
	
	public AbstractExtensionContainerMenu getMenu() {
		return this.menu;
	}
	
	public BackpackExtension getExtension() {
		return this.extension;
	}
	
}
