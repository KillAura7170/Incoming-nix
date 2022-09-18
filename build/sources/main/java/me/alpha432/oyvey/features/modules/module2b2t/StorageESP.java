package me.alpha432.oyvey.features.modules.module2b2t;

import me.alpha432.oyvey.event.events.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.ColorUtill;
import me.alpha432.oyvey.util.MathUtil;
import me.alpha432.oyvey.util.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public
class StorageESP
        extends Module {
    private final Setting<Float> range = this.register(new Setting<>("Range", 50.0f, 1.0f, 300.0f));
    private final Setting<Boolean> chest = this.register(new Setting<>("Chest", true));
    private final Setting<Boolean> dispenser = this.register(new Setting<>("Dispenser", false));
    private final Setting<Boolean> shulker = this.register(new Setting<>("Shulker", true));
    private final Setting<Boolean> echest = this.register(new Setting<>("Ender Chest", true));
    private final Setting<Boolean> furnace = this.register(new Setting<>("Furnace", false));
    private final Setting<Boolean> hopper = this.register(new Setting<>("Hopper", false));
    private final Setting<Boolean> cart = this.register(new Setting<>("Minecart", false));
    private final Setting<Boolean> frame = this.register(new Setting<>("Item Frame", false));
    private final Setting<Boolean> box = this.register(new Setting<>("Box", false));
    private final Setting<Integer> boxAlpha = this.register(new Setting<Object>("BoxAlpha", 125, 0, 255, v -> this.box.getValue(true)));
    private final Setting<Boolean> outline = this.register(new Setting<>("Outline", true));
    private final Setting<Float> lineWidth = this.register(new Setting<Object>("LineWidth", 1.0f, 0.1f, 5.0f, v -> this.outline.getValue(true)));

    public StorageESP() {
        super("StorageESP", "Highlights Containers.", Module.Category.MODULE2B2T, false, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        int color;
        BlockPos pos;
        HashMap<BlockPos, Integer> positions = new HashMap<>();
        for (TileEntity tileEntity : StorageESP.mc.world.loadedTileEntityList) {
            if (!(tileEntity instanceof TileEntityChest && this.chest.getValue(true) || tileEntity instanceof TileEntityDispenser && this.dispenser.getValue(true) || tileEntity instanceof TileEntityShulkerBox && this.shulker.getValue(true) || tileEntity instanceof TileEntityEnderChest && this.echest.getValue(true) || tileEntity instanceof TileEntityFurnace && this.furnace.getValue(true)) && (!(tileEntity instanceof TileEntityHopper) || !this.hopper.getValue(true)) || !(StorageESP.mc.player.getDistanceSq(pos = tileEntity.getPos()) <= MathUtil.square(this.range.getValue(true))) || (color = this.getTileEntityColor(tileEntity)) == -1)
                continue;
            positions.put(pos, color);
        }
        for (Entity entity : StorageESP.mc.world.loadedEntityList) {
            if ((!(entity instanceof EntityItemFrame) || !this.frame.getValue(true)) && (!(entity instanceof EntityMinecartChest) || !this.cart.getValue(true)) || !(StorageESP.mc.player.getDistanceSq(pos = entity.getPosition()) <= MathUtil.square(this.range.getValue(true))) || (color = this.getEntityColor(entity)) == -1)
                continue;
            positions.put(pos, color);
        }
        for (Map.Entry<BlockPos, Integer> entry : positions.entrySet()) {
            BlockPos blockPos = entry.getKey();
            color = entry.getValue();
            RenderUtil.drawBoxESP(blockPos, new Color(color), false, new Color(color), this.lineWidth.getValue(true), this.outline.getValue(true), this.box.getValue(true), this.boxAlpha.getValue(true), false);
        }
    }

    private int getTileEntityColor(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            return ColorUtill.Colors.BLUE;
        }
        if (tileEntity instanceof TileEntityShulkerBox) {
            return ColorUtill.Colors.RED;
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            return ColorUtill.Colors.PURPLE;
        }
        if (tileEntity instanceof TileEntityFurnace) {
            return ColorUtill.Colors.GRAY;
        }
        if (tileEntity instanceof TileEntityHopper) {
            return ColorUtill.Colors.DARK_RED;
        }
        if (tileEntity instanceof TileEntityDispenser) {
            return ColorUtill.Colors.ORANGE;
        }
        return -1;
    }

    private int getEntityColor(Entity entity) {
        if (entity instanceof EntityMinecartChest) {
            return ColorUtill.Colors.ORANGE;
        }
        if (entity instanceof EntityItemFrame && ((EntityItemFrame) entity).getDisplayedItem().getItem() instanceof ItemShulkerBox) {
            return ColorUtill.Colors.YELLOW;
        }
        if (entity instanceof EntityItemFrame && !(((EntityItemFrame) entity).getDisplayedItem().getItem() instanceof ItemShulkerBox)) {
            return ColorUtill.Colors.ORANGE;
        }
        return -1;
    }
}
