package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.events.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.util.RenderUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

public class BlockHighlight
        extends Module {
    private final Setting<Float> lineWidth = this.register(new Setting<>("LineWidth", 1.0f, 0.1f, 5.0f));
    private final Setting<Integer> cAlpha = this.register(new Setting<>("Alpha", 255, 0, 255));

    public BlockHighlight() {
        super("BlockHighlight", "Highlights the block u look at.", Module.Category.RENDER, false, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        RayTraceResult ray = BlockHighlight.mc.objectMouseOver;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ray.getBlockPos();
            RenderUtil.drawBlockOutline(blockpos, ClickGui.getInstance().rainbow.getValue(true) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue(true)) : new Color(ClickGui.getInstance().red.getValue(true), ClickGui.getInstance().green.getValue(true), ClickGui.getInstance().blue.getValue(true), this.cAlpha.getValue(true)), this.lineWidth.getValue(true), false);
        }
    }
}

