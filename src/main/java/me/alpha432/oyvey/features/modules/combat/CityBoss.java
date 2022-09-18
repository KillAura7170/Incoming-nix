package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.Util;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class CityBoss extends Module {
  private BlockPos diggPos = null;
  
  private Setting<Float> range = register(new Setting("Range", Float.valueOf(6.1F), Float.valueOf(2.0F), Float.valueOf(13.0F)));
  
  private Setting<Boolean> placeCrystal = register(new Setting("Place Crystal", Boolean.valueOf(true)));
  
  private Setting<Boolean> toggleAutoCrystal = register(new Setting("Toggle AutoCrystal", Boolean.valueOf(false)));
  
  int stage = 0;
  
  public CityBoss() {
    super("CityBoss", "Fucking gay yet", Module.Category.COMBAT, true, false, false);
  }
  
  public void onEnable() {
    this.diggPos = null;
  }
  
  public void onTick() {
    if (this.stage == 0) {
      List<EntityPlayer> entity = Util.mc.world.playerEntities;
      List<BlockPos> blocks = new ArrayList<>();
      for (EntityPlayer player : entity) {
        if (player.entityId != Util.mc.player.entityId) {
          BlockPos[] offset = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
          for (int i = 0; i < offset.length; i++) {
            BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
            if (getBlock(pos).getBlock() == Blocks.AIR || 
              !(getBlock(pos).getBlock() instanceof net.minecraft.block.BlockLiquid))
              if (getBlock(pos.add((Vec3i)offset[i])).getBlock() == Blocks.OBSIDIAN)
                blocks.add(pos.add((Vec3i)offset[i]));  
          } 
        } 
      } 
      this
        .diggPos = blocks.stream().min(Comparator.comparing(b -> Double.valueOf(Util.mc.player.getDistance(b.getX(), b.getY(), b.getZ())))).orElse(null);
    } 
    if (this.stage == 1)
      Util.mc.player.swingArm(EnumHand.MAIN_HAND); 
  }
  
  private IBlockState getBlock(BlockPos b) {
    return Util.mc.world.getBlockState(b);
  }
}
