package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.events.UpdateWalkingPlayerEvent;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeetXP extends Module {
   public FeetXP() {
      super("FootXP", "??.", Module.Category.PLAYER, true, false, false);
   }

   @SubscribeEvent
   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
      boolean mainHand = FastPlace.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE;
      boolean offHand = FastPlace.mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE;
      if (FastPlace.mc.gameSettings.keyBindUseItem.isKeyDown() && (FastPlace.mc.player.getActiveHand() == EnumHand.MAIN_HAND && mainHand || FastPlace.mc.player.getActiveHand() == EnumHand.OFF_HAND && offHand)) {
         OyVey.rotationManager.setPlayerYaw(-90.0F);
      }

   }
}
