package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.events.UpdateWalkingPlayerEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.BlockUtil;
import me.alpha432.oyvey.util.MathUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public
class Lawnmower extends Module {
    public Setting <Integer> playerRange = new Setting <>("Range", 2, 0, 6);
    public Setting <Integer> playerHeight = new Setting <>("Height", 2, 0, 6);
    public Setting <Boolean> rotate = new Setting<>("Rotate", true);
    public Lawnmower() {
        super("LawnMower", "haha funny meme", Category.MISC, true, false, false);
        register(playerRange);
        register(playerHeight);
        register(rotate);
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayer ( UpdateWalkingPlayerEvent event ) {
        for (BlockPos pos : BlockUtil.getSphere(mc.player.getPosition(), playerRange.getValue(true), playerHeight.getValue(true), false, true, 0)) {
            if (!check(pos)) continue;
            if (pos != null) {
                if (rotate.getValue(true)) {
                    float[] angle = MathUtil.calcAngle ( mc.player.getPositionEyes ( mc.getRenderPartialTicks ( ) ) , new Vec3d( (float) pos.getX ( )  , (float) pos.getY ( ) , (float) pos.getZ ( )  ) );
                    OyVey.rotationManager.setPlayerRotations ( angle[0] , angle[1] );
                }
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
            }
        }
    }

    boolean check(final BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock() == Blocks.TALLGRASS || mc.world.getBlockState(pos).getBlock() == Blocks.DOUBLE_PLANT;
    }
}