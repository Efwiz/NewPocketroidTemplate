package com.leff.midi.util;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/util/MidiUtil.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class MidiUtil {
    private static final String HEX = "0123456789ABCDEF";

    public static int bpmToMpqn(float bpm) {
        return (int) (bpm * 6.0E7f);
    }

    public static float mpqnToBpm(int mpqn) {
        return mpqn / 6.0E7f;
    }

    public static double msToTicks(long ms, int mpqn, int ppq) {
        return ((ms * 1000.0d) * ((double) ppq)) / ((double) mpqn);
    }

    public static long ticksToMs(long ticks, int mpqn, int resolution) {
        return ((ticks * ((long) mpqn)) / ((long) resolution)) / 1000;
    }

    public static long ticksToMs(long ticks, float bpm, int resolution) {
        return ticksToMs(ticks, bpmToMpqn(bpm), resolution);
    }

    public static double msToTicks(long ms, float bpm, int ppq) {
        return msToTicks(ms, bpmToMpqn(bpm), ppq);
    }

    public static int bytesToInt(byte[] buff, int off, int len) {
        int i = 0;
        int i2 = 0;
        for (int i3 = (len + off) - 1; i3 >= off; i3--) {
            i += (buff[i3] & 255) << i2;
            i2 += 8;
        }
        return i;
    }

    public static byte[] intToBytes(int val, int byteCount) {
        byte[] bArr = new byte[byteCount];
        int[] iArr = new int[byteCount];
        for (int i = 0; i < byteCount; i++) {
            int i2 = val & 255;
            iArr[i] = i2;
            bArr[(byteCount - i) - 1] = (byte) i2;
            val >>= 8;
            if (val == 0) {
                break;
            }
        }
        return bArr;
    }

    public static boolean bytesEqual(byte[] buf1, byte[] buf2, int off, int len) {
        for (int i = off; i < off + len; i++) {
            if (i >= buf1.length || i >= buf2.length || buf1[i] != buf2[i]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] extractBytes(byte[] buffer, int off, int len) {
        byte[] bArr = new byte[len];
        for (int i = 0; i < len; i++) {
            bArr[i] = buffer[off + i];
        }
        return bArr;
    }

    public static String byteToHex(byte b) {
        return "" + HEX.charAt((b & 240) >> 4) + HEX.charAt(b & 15);
    }

    public static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte b2 : b) {
            sb.append(byteToHex(b2)).append(" ");
        }
        return sb.toString();
    }
}
