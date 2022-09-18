/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatLogger
extends Module {
    public Setting<Boolean> numbers = this.register(new Setting<Boolean>("OnlyNumbers", false));
    File mainFolder;
    File txt;
    File folder;
    BufferedWriter out;

    public ChatLogger() {
        super("ChatLogger", "cl", Module.Category.MISC, true, false, false);
        this.mainFolder = new File(Minecraft.getMinecraft().gameDir + File.separator + "Incoming");
    }

    @Override
    public void onEnable() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
            Date date = new Date();
            this.folder = new File(this.mainFolder + File.separator + "logs");
            if (!this.folder.exists()) {
                this.folder.mkdirs();
            }
            String fileName = formatter.format(date) + "-chatlogs.txt";
            this.txt = new File(this.folder + File.separator + fileName);
            this.txt.createNewFile();
            this.out = new BufferedWriter(new FileWriter(this.txt));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (this.out != null) {
            try {
                this.out.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onChatRecieved(ClientChatReceivedEvent event) {
        try {
            String message = event.getMessage().getUnformattedText();
            if (this.numbers.getValue(true).booleanValue()) {
                if (message.matches(".*\\d.*")) {
                    this.out.write(message);
                    this.out.write(ChatLogger.endLine());
                    this.out.flush();
                }
            } else {
                this.out.write(message);
                this.out.write(ChatLogger.endLine());
                this.out.flush();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String endLine() {
        return "\r\n";
    }
}

