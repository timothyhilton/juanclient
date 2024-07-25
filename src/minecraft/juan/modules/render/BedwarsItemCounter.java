package juan.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import juan.settings.BooleanSetting;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BedwarsItemCounter extends Module {
    private final Map<Item, Integer> itemCounts = new HashMap<>();
    private final Map<Item, Integer> enderChestCounts = new HashMap<>();
    private final Item[] bedwarsItems = {
        Item.getItemById(265), // iron
        Item.getItemById(266), // gold
        Item.getItemById(264), // diamond
        Item.getItemById(388)  // emerald
    };
    private BooleanSetting includeEnderchest = new BooleanSetting("include enderchest", true);

    public BedwarsItemCounter() {
        super("BedwarsItemCounter", Keyboard.KEY_NONE, Category.RENDER, true);
        this.addSettings(includeEnderchest);
    }

    public void onEnable() {
        for (Item item : bedwarsItems) {
            itemCounts.put(item, 0);
            enderChestCounts.put(item, 0);
        }
    }

    public void onDisable() {
        itemCounts.clear();
        enderChestCounts.clear();
    }

    public void onEvent(Event e) {
        if (e instanceof EventRenderGUI) {
            updateItemCounts();
            renderItemCounts((EventRenderGUI) e);
        } else if (e instanceof EventUpdate) {
            checkEnderChestOpen();
        }
    }

    private void checkEnderChestOpen() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        Container container = player.openContainer;
        
        if (container instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest) container;
            if (chest.getLowerChestInventory().getName().contains("Ender Chest")) {
                updateEnderChestCounts(chest);
            }
        }
    }

    private void updateEnderChestCounts(ContainerChest chest) {
        for (Item item : bedwarsItems) {
            enderChestCounts.put(item, 0);
        }

        IInventory lowerChestInventory = chest.getLowerChestInventory();
        int size = lowerChestInventory.getSizeInventory();
        for (int i = 0; i < size; i++) {
            ItemStack stack = lowerChestInventory.getStackInSlot(i);
            if (stack != null) {
                for (Item item : bedwarsItems) {
                    if (stack.getItem() == item) {
                        enderChestCounts.put(item, enderChestCounts.get(item) + stack.stackSize);
                        break;
                    }
                }
            }
        }
    }

    private void updateItemCounts() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        
        for (Item item : bedwarsItems) {
            itemCounts.put(item, 0);
        }
        
        List<ItemStack> stacks = player.inventoryContainer.getInventory();
            
        stacks.forEach(stack -> {
            for(Item item : bedwarsItems) {
                if(stack != null && stack.getItem() == item) {
                    itemCounts.put(item, itemCounts.get(item) + stack.stackSize);
                    break;
                }
            }
        });

        if (includeEnderchest.isEnabled()) {
            for (Item item : bedwarsItems) {
                itemCounts.put(item, itemCounts.get(item) + enderChestCounts.get(item));
            }
        }
    }

    private void renderItemCounts(EventRenderGUI event) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        int screenWidth = sr.getScaledWidth();
        int screenHeight = sr.getScaledHeight();
        int fontHeight = fr.FONT_HEIGHT;
        int globalYOffset = -3;
        
        int maxWidth = 29;
        for (Item item : bedwarsItems) {
            String countText = itemCounts.get(item).toString();
            int textWidth = fr.getStringWidth(countText);
            maxWidth = Math.max(maxWidth, textWidth + 25);
        }
        
        Gui.drawRect(screenWidth - maxWidth, screenHeight - 65, screenWidth, screenHeight, 0x90000000);
        
        int indexOffset = 16;
        int index = 1;
        for (Item item : bedwarsItems) {
            ItemStack itemStack = new ItemStack(item);
            String countText = itemCounts.get(item).toString();
            
            int iconX = screenWidth - maxWidth + 2;
            int iconY = screenHeight + 3 - indexOffset * index + globalYOffset;
            int textX = iconX + 20;
            int textY = iconY + 4;
            
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, iconX, iconY);
            RenderHelper.disableStandardItemLighting();
            
            fr.drawStringWithShadow(countText, textX, textY, Color.WHITE.getRGB());
            
            index++;
        }
    }

    private String getItemName(Item item) {
        if (item == Item.getItemById(265)) return "Iron";
        if (item == Item.getItemById(266)) return "Gold";
        if (item == Item.getItemById(264)) return "Diamond";
        if (item == Item.getItemById(388)) return "Emerald";
        return "UNKNOWN";
    }
}