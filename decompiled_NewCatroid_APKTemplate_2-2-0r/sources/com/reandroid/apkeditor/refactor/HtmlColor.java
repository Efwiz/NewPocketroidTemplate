package com.reandroid.apkeditor.refactor;

import com.beust.jcommander.Parameters;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX INFO: loaded from: classes10.dex */
class HtmlColor {
    private static final Pattern PATTERN_LINE = Pattern.compile("^\\s*(#[A-fa-f0-9]{6,8})\\s*,\\s*([^\\s]+.*[^\\s]+)\\s*$");
    private static final String RES_NAME_PREFIX = "col_";
    private static Map<Integer, HtmlColor> knownColors;
    private final int alpha;
    private final int blue;
    private final int green;
    private String mSplitName;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f217name;
    private final int red;

    HtmlColor(String str) {
        this((String) null, str);
    }

    HtmlColor(String str, String str2) {
        this(str, parseHex(str2));
    }

    HtmlColor(String str, int i) {
        if (str != null && str.trim().length() == 0) {
            str = null;
        }
        this.f217name = str;
        this.alpha = (i >> 24) & 255;
        this.red = (i >> 16) & 255;
        this.green = (i >> 8) & 255;
        this.blue = i & 255;
    }

    String getName() {
        return this.f217name;
    }

    int getARGBValue() {
        return (this.alpha << 24) + (this.red << 16) + (this.green << 8) + this.blue;
    }

    int getRGBValue() {
        return (this.red << 16) + (this.green << 8) + this.blue;
    }

    String getRGBHex() {
        return toRgbHex(getRGBValue());
    }

    String getARGBHex() {
        return toARgbHex(getARGBValue());
    }

    String toResourceName() {
        return getSplitName();
    }

    private String getSplitName() {
        if (this.mSplitName == null) {
            this.mSplitName = splitByUpperCase(RES_NAME_PREFIX, getName());
        }
        return this.mSplitName;
    }

    double getDistance(HtmlColor htmlColor) {
        double d = this.red - htmlColor.red;
        double d2 = this.green - htmlColor.green;
        double d3 = this.blue - htmlColor.blue;
        return Math.sqrt((d * d) + (d2 * d2) + (d3 * d3));
    }

    boolean isEqualRGB(HtmlColor htmlColor, double d) {
        return getDistance(htmlColor) <= d;
    }

    int getAlpha() {
        return this.alpha;
    }

    private int getRed() {
        return this.red;
    }

    private int getGreen() {
        return this.green;
    }

    private int getBlue() {
        return this.blue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getRGBHex());
        if (getName() != null) {
            sb.append(": ");
            sb.append(getName());
            sb.append(", @color/");
            sb.append(getSplitName());
        }
        return sb.toString();
    }

    private static String splitByUpperCase(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        char[] charArray = str2.trim().toCharArray();
        int length = charArray.length;
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
        }
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char c = charArray[i];
            if (c == ' ') {
                if (!z) {
                    sb.append("_");
                    z = true;
                }
            } else if (i > 0 && Character.isUpperCase(c) && !z) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
                z = true;
            } else {
                sb.append(Character.toLowerCase(c));
                z = false;
            }
        }
        return sb.toString();
    }

    private static String toRgbHex(int i) {
        return String.format("#%06x", Integer.valueOf(i));
    }

    private static String toARgbHex(int i) {
        return String.format("#%08x", Integer.valueOf(i));
    }

    private static int parseHex(String str) {
        boolean z = true;
        if (str.startsWith("#")) {
            str = str.substring(1);
        }
        if (str.startsWith(Parameters.DEFAULT_OPTION_PREFIXES)) {
            str = str.substring(1);
        } else {
            z = false;
        }
        if (str.startsWith(PsiLiteralUtil.HEX_PREFIX)) {
            str = str.substring(2);
        }
        long j = Long.parseLong(str.toUpperCase(), 16);
        if (z) {
            j = -j;
        }
        return (int) j;
    }

    static HtmlColor getBestMatch(HtmlColor htmlColor, double d) {
        Map<Integer, HtmlColor> knownColors2 = getKnownColors();
        HtmlColor htmlColor2 = knownColors2.get(Integer.valueOf(htmlColor.getRGBValue()));
        if (htmlColor2 != null) {
            return htmlColor2;
        }
        double d2 = 0.0d;
        for (HtmlColor htmlColor3 : knownColors2.values()) {
            double distance = htmlColor3.getDistance(htmlColor);
            if (distance <= d) {
                if (htmlColor2 != null) {
                    if (distance >= d2) {
                        continue;
                    } else if (distance == 0.0d) {
                        return htmlColor3;
                    }
                }
                htmlColor2 = htmlColor3;
                d2 = distance;
            }
        }
        return htmlColor2;
    }

    private static Map<Integer, HtmlColor> getKnownColors() {
        if (knownColors == null) {
            knownColors = loadColorNames();
        }
        return knownColors;
    }

    private static Map<Integer, HtmlColor> loadColorNames() {
        HashMap map = new HashMap();
        InputStream colorNamesResource = getColorNamesResource();
        if (colorNamesResource == null) {
            return map;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(colorNamesResource));
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                HtmlColor line2 = parseLine(line);
                if (line2 != null) {
                    map.putIfAbsent(Integer.valueOf(line2.getRGBValue()), line2);
                }
            } catch (IOException unused) {
            }
        }
        bufferedReader.close();
        return map;
        try {
            bufferedReader.close();
        } catch (Exception unused2) {
        }
        return map;
    }

    private static InputStream getColorNamesResource() {
        try {
            return HtmlColor.class.getResourceAsStream("/color_names.txt");
        } catch (Exception unused) {
            return null;
        }
    }

    private static HtmlColor parseLine(String str) {
        Matcher matcher = PATTERN_LINE.matcher(str);
        if (!matcher.find()) {
            return null;
        }
        return new HtmlColor(matcher.group(2), matcher.group(1));
    }
}
