/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.util;

import java.lang.reflect.Field;

public class RusherHackUtil
implements Util {
    public static Field renderPosX;
    public static Field renderPosY;
    public static Field renderPosZ;

    public static double getRenderPosX() {
        try {
            return (Double)renderPosX.get(Wrapper.mc.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosY() {
        try {
            return (Double)renderPosY.get(Wrapper.mc.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosZ() {
        try {
            return (Double)renderPosZ.get(Wrapper.mc.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}

