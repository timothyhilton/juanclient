package juan.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class BedwarsItemCounter extends Module {
    private final Map<Item, Integer> itemCounts = new HashMap<>();
    private final Item[] bedwarsItems = {
        Item.getItemById(265), // Iron Ingot
        Item.getItemById(266), // Gold Ingot
        Item.getItemById(264), // Diamond
        Item.getItemById(388)  // Emerald
    };

    public BedwarsItemCounter() {
        super("BedwarsItemCounter", Keyboard.KEY_NONE, Category.RENDER, true);
    }

    @Override
    public void onEnable() {
        // Initialize counts
        for (Item item : bedwarsItems) {
            itemCounts.put(item, 0);
        }
    }

    @Override
    public void onDisable() {
        // Clear counts
        itemCounts.clear();
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRenderGUI) {
            updateItemCounts();
            renderItemCounts((EventRenderGUI) e);
        }
    }

    private void updateItemCounts() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;

        // Reset counts
        for (Item item : bedwarsItems) {
            itemCounts.put(item, 0);
        }

        // Count items in inventory
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack != null) {
                for (Item item : bedwarsItems) {
                    if (stack.getItem() == item) {
                        itemCounts.put(item, itemCounts.get(item) + stack.stackSize);
                        break;
                    }
                }
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
        
        Gui.drawRect(screenWidth - 59, screenHeight - 47, screenWidth - 1, screenHeight - 1, 0x90000000);
        
        int indexOffset = 10;
        int index = 1;
        for (Item item : bedwarsItems) {
            String itemName = getItemName(item);
            String rowContent = itemName + ": " + itemCounts.get(item);
            
            int textX = screenWidth - fr.getStringWidth(rowContent) - 5;
            int textY = screenHeight - indexOffset * index + globalYOffset;
            
            fr.drawStringWithShadow(rowContent, textX, textY, Color.WHITE.getRGB());
            
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