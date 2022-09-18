/*    */ package me.alpha432.oyvey.features.modules.render;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TexturedChams
/*    */   extends Module
/*    */ {
/*    */   public static Setting<Integer> red;
/*    */   public static Setting<Integer> green;
/*    */   public static Setting<Integer> blue;
/*    */   public static Setting<Integer> alpha;
/*    */   public static Setting<Boolean> rainbow;
/*    */   public static Setting<Integer> rainbowhue;
/*    */   
/*    */   public TexturedChams() {
/* 19 */     super("TexturedChams", "hi yes", Module.Category.RENDER, true, false, true);
/* 20 */     red = register(new Setting("Red", Integer.valueOf(168), Integer.valueOf(0), Integer.valueOf(255)));
/* 21 */     green = register(new Setting("Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/* 22 */     blue = register(new Setting("Blue", Integer.valueOf(232), Integer.valueOf(0), Integer.valueOf(255)));
/* 23 */     alpha = register(new Setting("Alpha", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(255)));
/* 24 */     rainbow = register(new Setting("Rainbow", Boolean.valueOf(false)));
/* 25 */     rainbowhue = register(new Setting("Brightness", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(255)));
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\render\TexturedChams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */