package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.events.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.BlockUtil;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.util.RenderUtill;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.awt.*;


public class HoleESP extends Module {
    public Setting<Boolean> renderOwn = register(new Setting<>("RenderOwn", true));
    public Setting<Boolean> fov = register(new Setting<>("InFov", true));
    public Setting<Boolean> rainbow = register(new Setting<>("Rainbow", false));
    private final Setting<Integer> range = register(new Setting<>("RangeX", 0, 0, 10));
    private final Setting<Integer> rangeY = register(new Setting<>("RangeY", 0, 0, 10));
    public Setting<Boolean> box = register(new Setting<>("Box", true));
    public Setting<Boolean> gradientBox = register(new Setting<Object>("Gradient", Boolean.FALSE, v -> box.getValue(true)));
    public Setting<Boolean> invertGradientBox = register(new Setting<Object>("ReverseGradient", Boolean.FALSE, v -> gradientBox.getValue(true)));
    public Setting<Boolean> outline = register(new Setting<>("Outline", true));
    public Setting<Boolean> gradientOutline = register(new Setting<Object>("GradientOutline", Boolean.FALSE, v -> outline.getValue(true)));
    public Setting<Boolean> invertGradientOutline = register(new Setting<Object>("ReverseOutline", Boolean.FALSE, v -> gradientOutline.getValue(true)));
    public Setting<Double> height = register(new Setting<>("Height", 0.0, -2.0, 2.0));
    private final Setting<Integer> red = register(new Setting<>("Red", 0, 0, 255));
    private final Setting<Integer> green = register(new Setting<>("Green", 255, 0, 255));
    private final Setting<Integer> blue = register(new Setting<>("Blue", 0, 0, 255));
    private final Setting<Integer> alpha = register(new Setting<>("Alpha", 255, 0, 255));
    private final Setting<Integer> boxAlpha = register(new Setting<Object>("BoxAlpha", 125, 0, 255, v -> box.getValue(true)));
    private final Setting<Float> lineWidth = register(new Setting<Object>("LineWidth", 1.0f, 0.1f, 5.0f, v -> outline.getValue(true)));
    public Setting<Boolean> safeColor = register(new Setting<>("BedrockColor", false));
    private final Setting<Integer> safeRed = register(new Setting<Object>("BedrockRed", 0, 0, 255, v -> safeColor.getValue(true)));
    private final Setting<Integer> safeGreen = register(new Setting<Object>("BedrockGreen", 255, 0, 255, v -> safeColor.getValue(true)));
    private final Setting<Integer> safeBlue = register(new Setting<Object>("BedrockBlue", 0, 0, 255, v -> safeColor.getValue(true)));
    private final Setting<Integer> safeAlpha = register(new Setting<Object>("BedrockAlpha", 255, 0, 255, v -> safeColor.getValue(true)));
    public Setting<Boolean> customOutline = register(new Setting<Object>("CustomLine", Boolean.FALSE, v -> outline.getValue(true)));
    private final Setting<Integer> cRed = register(new Setting<Object>("OL-Red", 0, 0, 255, v -> customOutline.getValue(true) && outline.getValue(true)));
    private final Setting<Integer> cGreen = register(new Setting<Object>("OL-Green", 0, 0, 255, v -> customOutline.getValue(true) && outline.getValue(true)));
    private final Setting<Integer> cBlue = register(new Setting<Object>("OL-Blue", 255, 0, 255, v -> customOutline.getValue(true) && outline.getValue(true)));
    private final Setting<Integer> cAlpha = register(new Setting<Object>("OL-Alpha", 255, 0, 255, v -> customOutline.getValue(true) && outline.getValue(true)));
    private final Setting<Integer> safecRed = register(new Setting<Object>("OL-SafeRed", 0, 0, 255, v -> customOutline.getValue(true) && outline.getValue(true) && safeColor.getValue(true)));
    private final Setting<Integer> safecGreen = register(new Setting<Object>("OL-SafeGreen", 255, 0, 255, v -> customOutline.getValue(true) && outline.getValue(true) && safeColor.getValue(true)));
    private final Setting<Integer> safecBlue = register(new Setting<Object>("OL-SafeBlue", 0, 0, 255, v -> customOutline.getValue(true) && outline.getValue(true) && safeColor.getValue(true)));
    private final Setting<Integer> safecAlpha = register(new Setting<Object>("OL-SafeAlpha", 255, 0, 255, v -> customOutline.getValue(true) && outline.getValue(true) && safeColor.getValue(true)));
    private static HoleESP INSTANCE = new HoleESP();
    private final int currentAlpha = 0;

    public HoleESP() {
        super("HoleESP", "Shows safe spots.", Module.Category.RENDER, false, false, false);
        setInstance();
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public static HoleESP getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HoleESP();
        }
        return INSTANCE;
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        assert (HoleESP.mc.renderViewEntity != null);
        Vec3i playerPos = new Vec3i(HoleESP.mc.renderViewEntity.posX, HoleESP.mc.renderViewEntity.posY, HoleESP.mc.renderViewEntity.posZ);
        for (int x = playerPos.getX() - range.getValue(true); x < playerPos.getX() + range.getValue(true); ++x) {
            for (int z = playerPos.getZ() - range.getValue(true); z < playerPos.getZ() + range.getValue(true); ++z) {
                for (int y = playerPos.getY() + rangeY.getValue(true); y > playerPos.getY() - rangeY.getValue(true); --y) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!HoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || !HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || pos.equals(new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ)) && !renderOwn.getValue(true) || !BlockUtil.isPosInFov(pos) && fov.getValue(true))
                        continue;
                    if (HoleESP.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) {
                        RenderUtill.drawBoxESP(pos, rainbow.getValue(true) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue(true)) : new Color(safeRed.getValue(true), safeGreen.getValue(true), safeBlue.getValue(true), safeAlpha.getValue(true)), customOutline.getValue(true), new Color(safecRed.getValue(true), safecGreen.getValue(true), safecBlue.getValue(true), safecAlpha.getValue(true)), lineWidth.getValue(true), outline.getValue(true), box.getValue(true), boxAlpha.getValue(true), true, height.getValue(true), gradientBox.getValue(true), gradientOutline.getValue(true), invertGradientBox.getValue(true), invertGradientOutline.getValue(true), currentAlpha);
                        continue;
                    }
                    if (!BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.down()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.east()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.west()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.south()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.north()).getBlock()))
                        continue;
                    RenderUtill.drawBoxESP(pos, rainbow.getValue(true) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue(true)) : new Color(red.getValue(true), green.getValue(true), blue.getValue(true), alpha.getValue(true)), customOutline.getValue(true), new Color(cRed.getValue(true), cGreen.getValue(true), cBlue.getValue(true), cAlpha.getValue(true)), lineWidth.getValue(true), outline.getValue(true), box.getValue(true), boxAlpha.getValue(true), true, height.getValue(true), gradientBox.getValue(true), gradientOutline.getValue(true), invertGradientBox.getValue(true), invertGradientOutline.getValue(true), currentAlpha);
                }
            }
        }
    }
}