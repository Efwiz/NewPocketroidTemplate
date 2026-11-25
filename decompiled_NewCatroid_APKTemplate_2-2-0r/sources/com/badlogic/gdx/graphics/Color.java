package com.badlogic.gdx.graphics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.NumberUtils;
import com.google.android.gms.cast.CastStatusCodes;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/Color.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Color {
    public static final Color BLACK;
    public static final Color BLUE;
    public static final Color BROWN;
    public static final Color CHARTREUSE;
    public static final Color CLEAR;
    public static final Color CLEAR_WHITE;
    public static final Color CORAL;
    public static final Color CYAN;
    public static final Color DARK_GRAY;
    public static final Color FIREBRICK;
    public static final Color FOREST;
    public static final Color GOLD;
    public static final Color GOLDENROD;
    public static final Color GRAY;
    public static final Color GREEN;
    public static final Color LIGHT_GRAY;
    public static final Color LIME;
    public static final Color MAGENTA;
    public static final Color MAROON;
    public static final Color NAVY;
    public static final Color OLIVE;
    public static final Color ORANGE;
    public static final Color PINK;
    public static final Color PURPLE;
    public static final Color RED;
    public static final Color ROYAL;
    public static final Color SALMON;
    public static final Color SCARLET;
    public static final Color SKY;
    public static final Color SLATE;
    public static final Color TAN;
    public static final Color TEAL;
    public static final Color VIOLET;
    public static final Color WHITE;
    public static final float WHITE_FLOAT_BITS;
    public static final Color YELLOW;
    public float a;
    public float b;
    public float g;
    public float r;

    public static int alpha(float f) {
        return (int) (f * 255.0f);
    }

    public static int argb8888(float f, float f2, float f3, float f4) {
        return (((int) (f * 255.0f)) << 24) | (((int) (f2 * 255.0f)) << 16) | (((int) (f3 * 255.0f)) << 8) | ((int) (f4 * 255.0f));
    }

    public static int luminanceAlpha(float f, float f2) {
        return (((int) (f * 255.0f)) << 8) | ((int) (f2 * 255.0f));
    }

    public static int rgb565(float f, float f2, float f3) {
        return (((int) (f * 31.0f)) << 11) | (((int) (f2 * 63.0f)) << 5) | ((int) (f3 * 31.0f));
    }

    public static int rgb888(float f, float f2, float f3) {
        return (((int) (f * 255.0f)) << 16) | (((int) (f2 * 255.0f)) << 8) | ((int) (f3 * 255.0f));
    }

    public static int rgba4444(float f, float f2, float f3, float f4) {
        return (((int) (f * 15.0f)) << 12) | (((int) (f2 * 15.0f)) << 8) | (((int) (f3 * 15.0f)) << 4) | ((int) (f4 * 15.0f));
    }

    public static int rgba8888(float f, float f2, float f3, float f4) {
        return (((int) (f * 255.0f)) << 24) | (((int) (f2 * 255.0f)) << 16) | (((int) (f3 * 255.0f)) << 8) | ((int) (f4 * 255.0f));
    }

    public static int toIntBits(int i, int i2, int i3, int i4) {
        return i | (i2 << 8) | (i3 << 16) | (i4 << 24);
    }

    static {
        Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        WHITE = color;
        LIGHT_GRAY = new Color(-1077952513);
        GRAY = new Color(2139062271);
        DARK_GRAY = new Color(1061109759);
        BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
        WHITE_FLOAT_BITS = color.toFloatBits();
        CLEAR = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        CLEAR_WHITE = new Color(1.0f, 1.0f, 1.0f, 0.0f);
        BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);
        NAVY = new Color(0.0f, 0.0f, 0.5f, 1.0f);
        ROYAL = new Color(1097458175);
        SLATE = new Color(1887473919);
        SKY = new Color(-2016482305);
        CYAN = new Color(0.0f, 1.0f, 1.0f, 1.0f);
        TEAL = new Color(0.0f, 0.5f, 0.5f, 1.0f);
        GREEN = new Color(16711935);
        CHARTREUSE = new Color(2147418367);
        LIME = new Color(852308735);
        FOREST = new Color(579543807);
        OLIVE = new Color(1804477439);
        YELLOW = new Color(-65281);
        GOLD = new Color(-2686721);
        GOLDENROD = new Color(-626712321);
        ORANGE = new Color(-5963521);
        BROWN = new Color(-1958407169);
        TAN = new Color(-759919361);
        FIREBRICK = new Color(-1306385665);
        RED = new Color(-16776961);
        SCARLET = new Color(-13361921);
        CORAL = new Color(-8433409);
        SALMON = new Color(-92245249);
        PINK = new Color(-9849601);
        MAGENTA = new Color(1.0f, 0.0f, 1.0f, 1.0f);
        PURPLE = new Color(-1608453889);
        VIOLET = new Color(-293409025);
        MAROON = new Color(-1339006721);
    }

    public Color() {
    }

    public Color(int i) {
        rgba8888ToColor(this, i);
    }

    public Color(float f, float f2, float f3, float f4) {
        this.r = f;
        this.g = f2;
        this.b = f3;
        this.a = f4;
        clamp();
    }

    public Color(Color color) {
        set(color);
    }

    public Color set(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
        return this;
    }

    public Color set(Color color, float f) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = MathUtils.clamp(f, 0.0f, 1.0f);
        return this;
    }

    public Color mul(Color color) {
        this.r *= color.r;
        this.g *= color.g;
        this.b *= color.b;
        this.a *= color.a;
        return clamp();
    }

    public Color mul(float f) {
        this.r *= f;
        this.g *= f;
        this.b *= f;
        this.a *= f;
        return clamp();
    }

    public Color add(Color color) {
        this.r += color.r;
        this.g += color.g;
        this.b += color.b;
        this.a += color.a;
        return clamp();
    }

    public Color sub(Color color) {
        this.r -= color.r;
        this.g -= color.g;
        this.b -= color.b;
        this.a -= color.a;
        return clamp();
    }

    public Color clamp() {
        float f = this.r;
        if (f < 0.0f) {
            this.r = 0.0f;
        } else if (f > 1.0f) {
            this.r = 1.0f;
        }
        float f2 = this.g;
        if (f2 < 0.0f) {
            this.g = 0.0f;
        } else if (f2 > 1.0f) {
            this.g = 1.0f;
        }
        float f3 = this.b;
        if (f3 < 0.0f) {
            this.b = 0.0f;
        } else if (f3 > 1.0f) {
            this.b = 1.0f;
        }
        float f4 = this.a;
        if (f4 < 0.0f) {
            this.a = 0.0f;
        } else if (f4 > 1.0f) {
            this.a = 1.0f;
        }
        return this;
    }

    public Color set(float f, float f2, float f3, float f4) {
        this.r = f;
        this.g = f2;
        this.b = f3;
        this.a = f4;
        return clamp();
    }

    public Color set(int i) {
        rgba8888ToColor(this, i);
        return this;
    }

    public Color add(float f, float f2, float f3, float f4) {
        this.r += f;
        this.g += f2;
        this.b += f3;
        this.a += f4;
        return clamp();
    }

    public Color sub(float f, float f2, float f3, float f4) {
        this.r -= f;
        this.g -= f2;
        this.b -= f3;
        this.a -= f4;
        return clamp();
    }

    public Color mul(float f, float f2, float f3, float f4) {
        this.r *= f;
        this.g *= f2;
        this.b *= f3;
        this.a *= f4;
        return clamp();
    }

    public Color lerp(Color color, float f) {
        float f2 = this.r;
        this.r = f2 + ((color.r - f2) * f);
        float f3 = this.g;
        this.g = f3 + ((color.g - f3) * f);
        float f4 = this.b;
        this.b = f4 + ((color.b - f4) * f);
        float f5 = this.a;
        this.a = f5 + (f * (color.a - f5));
        return clamp();
    }

    public Color lerp(float f, float f2, float f3, float f4, float f5) {
        float f6 = this.r;
        this.r = f6 + ((f - f6) * f5);
        float f7 = this.g;
        this.g = f7 + ((f2 - f7) * f5);
        float f8 = this.b;
        this.b = f8 + ((f3 - f8) * f5);
        float f9 = this.a;
        this.a = f9 + (f5 * (f4 - f9));
        return clamp();
    }

    public Color premultiplyAlpha() {
        float f = this.r;
        float f2 = this.a;
        this.r = f * f2;
        this.g *= f2;
        this.b *= f2;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && toIntBits() == ((Color) obj).toIntBits();
    }

    public int hashCode() {
        float f = this.r;
        int iFloatToIntBits = (f != 0.0f ? NumberUtils.floatToIntBits(f) : 0) * 31;
        float f2 = this.g;
        int iFloatToIntBits2 = (iFloatToIntBits + (f2 != 0.0f ? NumberUtils.floatToIntBits(f2) : 0)) * 31;
        float f3 = this.b;
        int iFloatToIntBits3 = (iFloatToIntBits2 + (f3 != 0.0f ? NumberUtils.floatToIntBits(f3) : 0)) * 31;
        float f4 = this.a;
        return iFloatToIntBits3 + (f4 != 0.0f ? NumberUtils.floatToIntBits(f4) : 0);
    }

    public float toFloatBits() {
        return NumberUtils.intToFloatColor((((int) (this.a * 255.0f)) << 24) | (((int) (this.b * 255.0f)) << 16) | (((int) (this.g * 255.0f)) << 8) | ((int) (this.r * 255.0f)));
    }

    public int toIntBits() {
        return (((int) (this.a * 255.0f)) << 24) | (((int) (this.b * 255.0f)) << 16) | (((int) (this.g * 255.0f)) << 8) | ((int) (this.r * 255.0f));
    }

    public String toString() {
        String hexString = Integer.toHexString((((int) (this.r * 255.0f)) << 24) | (((int) (this.g * 255.0f)) << 16) | (((int) (this.b * 255.0f)) << 8) | ((int) (this.a * 255.0f)));
        while (hexString.length() < 8) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    public static Color valueOf(String str) {
        return valueOf(str, new Color());
    }

    public static Color valueOf(String str, Color color) {
        if (str.charAt(0) == '#') {
            str = str.substring(1);
        }
        color.r = Integer.parseInt(str.substring(0, 2), 16) / 255.0f;
        color.g = Integer.parseInt(str.substring(2, 4), 16) / 255.0f;
        color.b = Integer.parseInt(str.substring(4, 6), 16) / 255.0f;
        color.a = str.length() != 8 ? 1.0f : Integer.parseInt(str.substring(6, 8), 16) / 255.0f;
        return color;
    }

    public static float toFloatBits(int i, int i2, int i3, int i4) {
        return NumberUtils.intToFloatColor(i | (i2 << 8) | (i3 << 16) | (i4 << 24));
    }

    public static float toFloatBits(float f, float f2, float f3, float f4) {
        return NumberUtils.intToFloatColor(((int) (f * 255.0f)) | (((int) (f2 * 255.0f)) << 8) | (((int) (f3 * 255.0f)) << 16) | (((int) (f4 * 255.0f)) << 24));
    }

    public static int rgb565(Color color) {
        return ((int) (color.b * 31.0f)) | (((int) (color.r * 31.0f)) << 11) | (((int) (color.g * 63.0f)) << 5);
    }

    public static int rgba4444(Color color) {
        return ((int) (color.a * 15.0f)) | (((int) (color.r * 15.0f)) << 12) | (((int) (color.g * 15.0f)) << 8) | (((int) (color.b * 15.0f)) << 4);
    }

    public static int rgb888(Color color) {
        return ((int) (color.b * 255.0f)) | (((int) (color.r * 255.0f)) << 16) | (((int) (color.g * 255.0f)) << 8);
    }

    public static int rgba8888(Color color) {
        return ((int) (color.a * 255.0f)) | (((int) (color.r * 255.0f)) << 24) | (((int) (color.g * 255.0f)) << 16) | (((int) (color.b * 255.0f)) << 8);
    }

    public static int argb8888(Color color) {
        return ((int) (color.b * 255.0f)) | (((int) (color.a * 255.0f)) << 24) | (((int) (color.r * 255.0f)) << 16) | (((int) (color.g * 255.0f)) << 8);
    }

    public static void rgb565ToColor(Color color, int i) {
        color.r = ((63488 & i) >>> 11) / 31.0f;
        color.g = ((i & CastStatusCodes.DEVICE_CONNECTION_SUSPENDED) >>> 5) / 63.0f;
        color.b = (i & 31) / 31.0f;
    }

    public static void rgba4444ToColor(Color color, int i) {
        color.r = ((61440 & i) >>> 12) / 15.0f;
        color.g = ((i & 3840) >>> 8) / 15.0f;
        color.b = ((i & 240) >>> 4) / 15.0f;
        color.a = (i & 15) / 15.0f;
    }

    public static void rgb888ToColor(Color color, int i) {
        color.r = ((16711680 & i) >>> 16) / 255.0f;
        color.g = ((65280 & i) >>> 8) / 255.0f;
        color.b = (i & 255) / 255.0f;
    }

    public static void rgba8888ToColor(Color color, int i) {
        color.r = (((-16777216) & i) >>> 24) / 255.0f;
        color.g = ((16711680 & i) >>> 16) / 255.0f;
        color.b = ((65280 & i) >>> 8) / 255.0f;
        color.a = (i & 255) / 255.0f;
    }

    public static void argb8888ToColor(Color color, int i) {
        color.a = (((-16777216) & i) >>> 24) / 255.0f;
        color.r = ((16711680 & i) >>> 16) / 255.0f;
        color.g = ((65280 & i) >>> 8) / 255.0f;
        color.b = (i & 255) / 255.0f;
    }

    public static void abgr8888ToColor(Color color, int i) {
        color.a = (((-16777216) & i) >>> 24) / 255.0f;
        color.b = ((16711680 & i) >>> 16) / 255.0f;
        color.g = ((65280 & i) >>> 8) / 255.0f;
        color.r = (i & 255) / 255.0f;
    }

    public static void abgr8888ToColor(Color color, float f) {
        int iFloatToIntColor = NumberUtils.floatToIntColor(f);
        color.a = (((-16777216) & iFloatToIntColor) >>> 24) / 255.0f;
        color.b = ((16711680 & iFloatToIntColor) >>> 16) / 255.0f;
        color.g = ((65280 & iFloatToIntColor) >>> 8) / 255.0f;
        color.r = (iFloatToIntColor & 255) / 255.0f;
    }

    public Color fromHsv(float f, float f2, float f3) {
        float f4 = ((f / 60.0f) + 6.0f) % 6.0f;
        int i = (int) f4;
        float f5 = f4 - i;
        float f6 = (1.0f - f2) * f3;
        float f7 = (1.0f - (f2 * f5)) * f3;
        float f8 = (1.0f - (f2 * (1.0f - f5))) * f3;
        if (i == 0) {
            this.r = f3;
            this.g = f8;
            this.b = f6;
        } else if (i == 1) {
            this.r = f7;
            this.g = f3;
            this.b = f6;
        } else if (i == 2) {
            this.r = f6;
            this.g = f3;
            this.b = f8;
        } else if (i == 3) {
            this.r = f6;
            this.g = f7;
            this.b = f3;
        } else if (i == 4) {
            this.r = f8;
            this.g = f6;
            this.b = f3;
        } else {
            this.r = f3;
            this.g = f6;
            this.b = f7;
        }
        return clamp();
    }

    public Color fromHsv(float[] fArr) {
        return fromHsv(fArr[0], fArr[1], fArr[2]);
    }

    public float[] toHsv(float[] fArr) {
        float fMax = Math.max(Math.max(this.r, this.g), this.b);
        float fMin = Math.min(Math.min(this.r, this.g), this.b);
        float f = fMax - fMin;
        if (f == 0.0f) {
            fArr[0] = 0.0f;
        } else {
            float f2 = this.r;
            if (fMax == f2) {
                fArr[0] = ((((this.g - this.b) * 60.0f) / f) + 360.0f) % 360.0f;
            } else {
                float f3 = this.g;
                if (fMax == f3) {
                    fArr[0] = (((this.b - f2) * 60.0f) / f) + 120.0f;
                } else {
                    fArr[0] = (((f2 - f3) * 60.0f) / f) + 240.0f;
                }
            }
        }
        if (fMax > 0.0f) {
            fArr[1] = 1.0f - (fMin / fMax);
        } else {
            fArr[1] = 0.0f;
        }
        fArr[2] = fMax;
        return fArr;
    }

    public Color cpy() {
        return new Color(this);
    }
}
