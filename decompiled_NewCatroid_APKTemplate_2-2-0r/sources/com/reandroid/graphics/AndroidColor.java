package com.reandroid.graphics;

import com.reandroid.utils.HexUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes11.dex */
public class AndroidColor {
    public static final double LENGTH = ObjectsUtil.of(441.67d);
    private int alpha;
    private int blue;
    private int green;
    private int red;
    private Type type;
    private boolean upperCase;

    private static int deltaSquare(int i, int i2) {
        int i3 = i - i2;
        return i3 * i3;
    }

    public AndroidColor(int i, int i2, int i3, int i4) {
        this.alpha = i;
        this.red = i2;
        this.green = i3;
        this.blue = i4;
    }

    public AndroidColor() {
        this(0, 0, 0, 0);
    }

    public void setUpperCase(boolean z) {
        this.upperCase = z;
    }

    public AndroidColor copy() {
        AndroidColor androidColor = new AndroidColor(this.alpha, this.red, this.green, this.blue);
        androidColor.setType(getType());
        androidColor.setUpperCase(this.upperCase);
        return androidColor;
    }

    public AndroidColor inverse() {
        AndroidColor androidColorCopy = copy();
        int iMask = getType().mask();
        androidColorCopy.red((~androidColorCopy.red()) & iMask);
        androidColorCopy.green((~androidColorCopy.green()) & iMask);
        androidColorCopy.blue(iMask & (~androidColorCopy.blue()));
        return androidColorCopy;
    }

    public AndroidColor argb() {
        Type type;
        AndroidColor androidColor = new AndroidColor(0, this.red, this.green, this.blue);
        if (getType().isEightBit()) {
            type = Type.ARGB8;
        } else {
            type = Type.ARGB4;
        }
        androidColor.setType(type);
        androidColor.setUpperCase(this.upperCase);
        androidColor.alpha(alpha());
        return androidColor;
    }

    public AndroidColor rgb() {
        Type type;
        AndroidColor androidColor = new AndroidColor(0, this.red, this.green, this.blue);
        if (getType().isEightBit()) {
            type = Type.RGB8;
        } else {
            type = Type.RGB4;
        }
        androidColor.setType(type);
        androidColor.setUpperCase(this.upperCase);
        return androidColor;
    }

    public AndroidColor toEightBit() {
        if (isEightBit()) {
            return this;
        }
        AndroidColor androidColorCopy = copy();
        androidColorCopy.setEightBit(true);
        return androidColorCopy;
    }

    public AndroidColor toFourBit() {
        if (!isEightBit()) {
            return this;
        }
        AndroidColor androidColorCopy = copy();
        androidColorCopy.setEightBit(false);
        return androidColorCopy;
    }

    public boolean hasAlpha() {
        return getType().hasAlpha();
    }

    public void setHasAlpha(boolean z) {
        Type type = getType();
        if (z != type.hasAlpha()) {
            if (type == Type.ARGB4) {
                type = Type.RGB4;
            } else if (type == Type.RGB4) {
                type = Type.ARGB4;
            } else if (type == Type.ARGB8) {
                type = Type.RGB8;
            } else if (type == Type.RGB8) {
                type = Type.ARGB8;
            }
            int iFit = z ? type.fit(alpha()) : 0;
            setType(type);
            alpha(iFit);
        }
    }

    public int alpha() {
        return this.alpha;
    }

    public void alpha(int i) {
        if (this.alpha != i || i == 0) {
            this.alpha = getType().fit(i);
        }
    }

    public int red() {
        return this.red;
    }

    public void red(int i) {
        this.red = getType().fit(i);
    }

    public int green() {
        return this.green;
    }

    public void green(int i) {
        this.green = getType().fit(i);
    }

    public int blue() {
        return this.blue;
    }

    public void blue(int i) {
        this.blue = getType().fit(i);
    }

    public AndroidColor addRgb(int i) {
        if (i == 0) {
            return this;
        }
        AndroidColor androidColorCopy = copy();
        int i2 = androidColorCopy.isEightBit() ? 255 : 15;
        int i3 = androidColorCopy.red + i;
        if (i3 >= 0 && i3 <= i2) {
            androidColorCopy.red = i3;
            int i4 = androidColorCopy.green + i;
            if (i4 >= 0 && i4 <= i2) {
                androidColorCopy.green = i4;
                int i5 = androidColorCopy.blue + i;
                if (i5 >= 0 && i5 <= i2) {
                    androidColorCopy.blue = i5;
                    return androidColorCopy;
                }
            }
        }
        return this;
    }

    public boolean isEightBit() {
        return getType().isEightBit();
    }

    public void setEightBit(boolean z) {
        Type fourBit;
        Type type = getType();
        if (z == type.isEightBit()) {
            return;
        }
        int i = 255;
        int i2 = 15;
        if (z) {
            fourBit = type.toEightBit();
        } else {
            fourBit = type.toFourBit();
            i2 = 255;
            i = 15;
        }
        this.alpha = (this.alpha * i) / i2;
        this.red = (this.red * i) / i2;
        this.green = (this.green * i) / i2;
        this.blue = (this.blue * i) / i2;
        this.type = fourBit;
    }

    public Type getType() {
        Type type = this.type;
        return type == null ? Type.getFor(alpha(), red(), green(), blue()) : type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String toHexString() {
        Type type = getType();
        StringBuilder sb = new StringBuilder("#");
        int i = type.isEightBit() ? 2 : 1;
        if (type.hasAlpha()) {
            sb.append(HexUtil.toHex((String) null, this.alpha, i));
        }
        sb.append(HexUtil.toHex((String) null, this.red, i));
        sb.append(HexUtil.toHex((String) null, this.green, i));
        sb.append(HexUtil.toHex((String) null, this.blue, i));
        String string = sb.toString();
        return this.upperCase ? StringsUtil.toUpperCase(string) : string;
    }

    public int intValue() {
        return (this.alpha << 24) | (this.red << 16) | (this.green << 8) | this.blue;
    }

    public int compareTo(AndroidColor androidColor, AndroidColor androidColor2) {
        return Double.compare(androidColor.distance(this), androidColor.distance(androidColor2));
    }

    public double distance(AndroidColor androidColor) {
        AndroidColor eightBit;
        if (androidColor == this) {
            return 0.0d;
        }
        if (isEightBit() == androidColor.isEightBit()) {
            eightBit = this;
        } else if (isEightBit()) {
            androidColor = androidColor.toEightBit();
            eightBit = this;
        } else {
            eightBit = toEightBit();
        }
        return Math.round(Math.sqrt((((double) deltaSquare(androidColor.red(), eightBit.red())) + ((double) deltaSquare(androidColor.green(), eightBit.green()))) + ((double) deltaSquare(androidColor.blue(), eightBit.blue()))) * 100.0d) / 100.0d;
    }

    public boolean equalsRgb(Object obj) {
        AndroidColor eightBit;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AndroidColor)) {
            return false;
        }
        AndroidColor eightBit2 = (AndroidColor) obj;
        if (isEightBit() == eightBit2.isEightBit()) {
            eightBit = this;
        } else if (isEightBit()) {
            eightBit2 = eightBit2.toEightBit();
            eightBit = this;
        } else {
            eightBit = toEightBit();
        }
        return eightBit.red == eightBit2.red && eightBit.green == eightBit2.green && eightBit.blue == eightBit2.blue;
    }

    public boolean equals(AndroidColor androidColor, float f) {
        if (androidColor == null) {
            return false;
        }
        return androidColor == this || distance(androidColor) <= toDistance(f);
    }

    public boolean equals(AndroidColor androidColor, double d) {
        if (androidColor == null) {
            return false;
        }
        return androidColor == this || distance(androidColor) <= d;
    }

    public boolean equals(Object obj) {
        AndroidColor eightBit;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AndroidColor)) {
            return false;
        }
        AndroidColor eightBit2 = (AndroidColor) obj;
        if (isEightBit() == eightBit2.isEightBit()) {
            eightBit = this;
        } else if (isEightBit()) {
            eightBit2 = eightBit2.toEightBit();
            eightBit = this;
        } else {
            eightBit = toEightBit();
        }
        return eightBit.alpha == eightBit2.alpha && eightBit.red == eightBit2.red && eightBit.green == eightBit2.green && eightBit.blue == eightBit2.blue;
    }

    public int hashCode() {
        return intValue();
    }

    public String toString() {
        return toHexString();
    }

    public static ColorIterator decodeAll(String str) {
        return new ColorIterator(str);
    }

    public static AndroidColor decode(String str) {
        int length;
        if (str == null || (length = str.length()) < 4 || str.charAt(0) != '#') {
            return null;
        }
        StringBuilder sb = new StringBuilder(9);
        sb.append('#');
        for (int i = 1; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (!HexUtil.isHexChar(cCharAt)) {
                break;
            }
            if (i > 8) {
                return null;
            }
            sb.append(cCharAt);
        }
        return parseHex(sb.toString(), false);
    }

    public static AndroidColor parseHex(String str) {
        return parseHex(str, true);
    }

    private static AndroidColor parseHex(String str, boolean z) {
        if (str == null || str.length() == 0 || str.charAt(0) != '#') {
            if (z) {
                throw new NumberFormatException("Invalid hex color: " + str);
            }
            return null;
        }
        Type typeValueOf = Type.valueOf(str.length());
        if (typeValueOf == null) {
            if (z) {
                throw new NumberFormatException("Invalid hex color string length: " + str);
            }
            return null;
        }
        AndroidColor androidColor = new AndroidColor();
        androidColor.type = typeValueOf;
        String strSubstring = str.substring(1);
        androidColor.setUpperCase(StringsUtil.containsUpperAZ(strSubstring));
        int i = typeValueOf.isEightBit() ? 2 : 1;
        if (typeValueOf.hasAlpha()) {
            int iDecodeHex = HexUtil.decodeHex(strSubstring.substring(0, i), -1);
            if (iDecodeHex == -1) {
                if (z) {
                    throw new NumberFormatException("Invalid hex: " + str);
                }
                return null;
            }
            androidColor.alpha = iDecodeHex;
            strSubstring = strSubstring.substring(i);
        }
        int iDecodeHex2 = HexUtil.decodeHex(strSubstring.substring(0, i), -1);
        if (iDecodeHex2 == -1) {
            if (z) {
                throw new NumberFormatException("Invalid hex: " + str);
            }
            return null;
        }
        androidColor.red = iDecodeHex2;
        String strSubstring2 = strSubstring.substring(i);
        int iDecodeHex3 = HexUtil.decodeHex(strSubstring2.substring(0, i), -1);
        if (iDecodeHex3 == -1) {
            if (z) {
                throw new NumberFormatException("Invalid hex: " + str);
            }
            return null;
        }
        androidColor.green = iDecodeHex3;
        String strSubstring3 = strSubstring2.substring(i);
        int iDecodeHex4 = HexUtil.decodeHex(strSubstring3.substring(0, i), -1);
        if (iDecodeHex4 != -1) {
            androidColor.blue = iDecodeHex4;
            return androidColor;
        }
        if (z) {
            throw new NumberFormatException("Invalid hex: " + strSubstring3);
        }
        return null;
    }

    public static float toPercent(double d) {
        return (float) ((d * 100.0d) / LENGTH);
    }

    public static double toDistance(float f) {
        return (((double) f) * LENGTH) / 100.0d;
    }

    public static class Type {
        private final boolean eightBit;
        private final boolean hasAlpha;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f257name;
        public static final Type RGB4 = new Type("RGB4", false, false);
        public static final Type ARGB4 = new Type("ARGB4", true, false);
        public static final Type RGB8 = new Type("RGB8", false, true);
        public static final Type ARGB8 = new Type("ARGB8", true, true);

        public boolean equals(Object obj) {
            return obj == this;
        }

        private Type(String str, boolean z, boolean z2) {
            this.f257name = str;
            this.hasAlpha = z;
            this.eightBit = z2;
        }

        public boolean fits(int i, int i2, int i3, int i4) {
            int i5;
            if (i != 0 && !this.hasAlpha) {
                return false;
            }
            int iMask = mask();
            return (i & iMask) == i && (i5 = i2 & iMask) == i2 && i5 == i3 && i5 == i4;
        }

        public int fit(int i) {
            int iMask = mask();
            if (i > iMask) {
                return iMask;
            }
            if (i < 0) {
                return 0;
            }
            return i;
        }

        public int mask() {
            return this.eightBit ? 255 : 15;
        }

        public boolean hasAlpha() {
            return this.hasAlpha;
        }

        public boolean isEightBit() {
            return this.eightBit;
        }

        public Type toEightBit() {
            if (isEightBit()) {
                return this;
            }
            if (this == RGB4) {
                return RGB8;
            }
            return ARGB8;
        }

        public Type toFourBit() {
            if (!isEightBit()) {
                return this;
            }
            if (this == RGB8) {
                return RGB4;
            }
            return ARGB4;
        }

        public int hashCode() {
            return this.f257name.hashCode();
        }

        public String toString() {
            return this.f257name;
        }

        public static Type getFor(int i, int i2, int i3, int i4) {
            Type type = RGB4;
            if (type.fits(i, i2, i3, i4)) {
                return type;
            }
            Type type2 = ARGB4;
            if (type2.fits(i, i2, i3, i4)) {
                return type2;
            }
            Type type3 = RGB8;
            return type3.fits(i, i2, i3, i4) ? type3 : ARGB8;
        }

        public static Type valueOf(int i) {
            if (i == 4) {
                return RGB4;
            }
            if (i == 5) {
                return ARGB4;
            }
            if (i == 7) {
                return RGB8;
            }
            if (i == 9) {
                return ARGB8;
            }
            return null;
        }
    }

    public static class ColorIterator implements Iterator<AndroidColor> {
        private AndroidColor current;
        private int index;
        private String lastColor;
        private int lastIndex = -1;
        private String text;

        public ColorIterator(String str) {
            this.text = str;
        }

        public String getText() {
            return this.text;
        }

        public String replace(AndroidColor androidColor) {
            String text = getText();
            if (androidColor == null || this.lastColor == null) {
                return text;
            }
            String strSubstring = text.substring(0, this.lastIndex);
            String strSubstring2 = text.substring(this.lastIndex + this.lastColor.length());
            this.lastColor = androidColor.toHexString();
            String str = strSubstring + this.lastColor + strSubstring2;
            this.text = str;
            return str;
        }

        @Override // java.util.Iterator
        public void remove() {
            String str = this.lastColor;
            if (str == null) {
                return;
            }
            String text = getText();
            this.lastColor = null;
            this.text = text.substring(0, this.lastIndex) + text.substring(this.lastIndex + str.length());
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return getNext() != null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public AndroidColor next() {
            AndroidColor next = getNext();
            if (next == null) {
                throw new NoSuchElementException();
            }
            this.lastColor = next.toHexString();
            this.current = null;
            return next;
        }

        private int nextIndex() {
            String str = this.text;
            if (str == null) {
                return -1;
            }
            int length = str.length();
            while (true) {
                int i = this.index;
                if (i >= length) {
                    return -1;
                }
                if (this.text.charAt(i) == '#') {
                    return this.index;
                }
                this.index++;
            }
        }

        private AndroidColor getNext() {
            while (this.current == null && nextIndex() != -1) {
                AndroidColor androidColorDecode = AndroidColor.decode(this.text.substring(this.index));
                this.current = androidColorDecode;
                if (androidColorDecode != null) {
                    this.lastIndex = this.index;
                }
                this.index++;
            }
            return this.current;
        }
    }
}
