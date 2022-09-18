package me.alpha432.oyvey.features.modules.module2b2t;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StashLogger
extends Module {
    private final Setting<Integer> chestsToImportantNotify = this.register(new Setting<Integer>("Chests", 15, 1, 30));
    private final Setting<Boolean> chests = this.register(new Setting<Boolean>("Chests", true));
    private final Setting<Boolean> Shulkers = this.register(new Setting<Boolean>("Shulkers", true));
    private final Setting<Boolean> donkeys = this.register(new Setting<Boolean>("Donkeys", false));
    private final Setting<Boolean> writeToFile = this.register(new Setting<Boolean>("CoordsSaver", true));
    File mainFolder;
    final Iterator<NBTTagCompound> iterator;

    public StashLogger() {
        super("StashLogger", "sl", Module.Category.MODULE2B2T, true, false, false);
        this.mainFolder = new File(Minecraft.getMinecraft().gameDir + File.separator + "Incoming");
        this.iterator = null;
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (StashLogger.nullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketChunkData) {
            SPacketChunkData l_Packet = (SPacketChunkData)event.getPacket();
            int l_ChestsCount = 0;
            int shulkers = 0;
            for (NBTTagCompound l_Tag : l_Packet.getTileEntityTags()) {
                String l_Id = l_Tag.getString("id");
                if (l_Id.equals("minecraft:chest") && this.chests.getValue(true).booleanValue()) {
                    ++l_ChestsCount;
                    continue;
                }
                if (!l_Id.equals("minecraft:shulker_box") || !this.Shulkers.getValue(true).booleanValue()) continue;
                ++shulkers;
            }
            if (l_ChestsCount >= this.chestsToImportantNotify.getValue(true)) {
                this.SendMessage(String.format("%s chests located at X: %s, Z: %s", l_ChestsCount, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16), true);
            }
            if (shulkers > 0) {
                this.SendMessage(String.format("%s shulker boxes at X: %s, Z: %s", shulkers, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16), true);
            }
        }
    }

    private void SendMessage(String message, boolean save) {
        String server;
        String string = server = Minecraft.getMinecraft().isSingleplayer() ? "singleplayer".toUpperCase() : StashLogger.mc.getCurrentServerData().serverIP;
        if (this.writeToFile.getValue(true).booleanValue() && save) {
            try {
                FileWriter writer = new FileWriter(this.mainFolder + "/stashes.txt", true);
                writer.write("[" + server + "]: " + message + "\n");
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord((SoundEvent)SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, (float)1.0f, (float)1.0f));
        StashLogger.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.RED + "[StashLogger] " + ChatFormatting.GREEN + message));
    }
}

