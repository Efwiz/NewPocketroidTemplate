package org.jetbrains.kotlin.org.jline.utils;

import com.badlogic.gdx.graphics.GL30;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AttributedCharSequence implements CharSequence {
    private static final int[] COLORS_256 = {0, 8388608, 32768, 8421376, 128, 8388736, 32896, 12632256, 8421504, 16711680, 65280, 16776960, 255, 16711935, 65535, 16777215, 0, 95, 135, 175, 215, 255, 24320, 24415, 24455, 24495, 24535, 24575, 34560, 34655, 34695, 34735, 34775, GL30.GL_PROGRAM_BINARY_FORMATS, 44800, 44895, 44935, 44975, 45015, 45055, 55040, 55135, 55175, 55215, 55255, 55295, 65280, 65375, 65415, XKeySym.XK_KP_Divide, 65495, 65535, 6225920, 6226015, 6226055, 6226095, 6226135, 6226175, 6250240, 6250335, 6250375, 6250415, 6250455, 6250495, 6260480, 6260575, 6260615, 6260655, 6260695, 6260735, 6270720, 6270815, 6270855, 6270895, 6270935, 6270975, 6280960, 6281055, 6281095, 6281135, 6281175, 6281215, 6291200, 6291295, 6291335, 6291375, 6291415, 6291455, 8847360, 8847455, 8847495, 8847535, 8847575, 8847615, 8871680, 8871775, 8871815, 8871855, 8871895, 8871935, 8881920, 8882015, 8882055, 8882095, 8882135, 8882175, 8892160, 8892255, 8892295, 8892335, 8892375, 8892415, 8902400, 8902495, 8902535, 8902575, 8902615, 8902655, 8912640, 8912735, 8912775, 8912815, 8912855, 8912895, 11468800, 11468895, 11468935, 11468975, 11469015, 11469055, 11493120, 11493215, 11493255, 11493295, 11493335, 11493375, 11503360, 11503455, 11503495, 11503535, 11503575, 11503615, 11513600, 11513695, 11513735, 11513775, 11513815, 11513855, 11523840, 11523935, 11523975, 11524015, 11524055, 11524095, 11534080, 11534175, 11534215, 11534255, 11534295, 11534335, 14090240, 14090335, 14090375, 14090415, 14090455, 14090495, 14114560, 14114655, 14114695, 14114735, 14114775, 14114815, 14124800, 14124895, 14124935, 14124975, 14125015, 14125055, 14135040, 14135135, 14135175, 14135215, 14135255, 14135295, 14145280, 14145375, 14145415, 14145455, 14145495, 14145535, 14155520, 14155615, 14155655, 14155695, 14155735, 14155775, 16711680, 16711775, 16711815, 16711855, 16711895, 16711935, 16736000, 16736095, 16736135, 16736175, 16736215, 16736255, 16746240, 16746335, 16746375, 16746415, 16746455, 16746495, 16756480, 16756575, 16756615, 16756655, 16756695, 16756735, 16766720, 16766815, 16766855, 16766895, 16766935, 16766975, 16776960, 16777055, 16777095, 16777135, 16777175, 16777215, 526344, 1184274, 1842204, 2500134, 3158064, 3815994, 4473924, 5131854, 5789784, 6447714, 7105644, 7763574, 8421504, 9079434, 9737364, 10395294, 11053224, 11711154, 12369084, 13027014, 13684944, 14342874, 15000804, 15658734};

    static int sqr(int i) {
        return i * i;
    }

    protected abstract char[] buffer();

    protected abstract int offset();

    public abstract AttributedStyle styleAt(int i);

    @Override // java.lang.CharSequence
    public abstract AttributedString subSequence(int i, int i2);

    public String toAnsi(Terminal terminal) {
        String str;
        String str2;
        String str3;
        Integer numericCapability;
        if (terminal != null && "dumb".equals(terminal.getType())) {
            return toString();
        }
        StringBuilder sb = new StringBuilder();
        int iIntValue = (terminal == null || (numericCapability = terminal.getNumericCapability(InfoCmp.Capability.max_colors)) == null) ? 8 : numericCapability.intValue();
        int i = 0;
        int i2 = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < length(); i4++) {
            char cCharAt = charAt(i4);
            int iStyleCodeAt = styleCodeAt(i4);
            int i5 = iStyleCodeAt & (-1025);
            if (i != i5) {
                int i6 = i ^ i5;
                int i7 = (iStyleCodeAt & 256) != 0 ? (16711680 & iStyleCodeAt) >>> 16 : -1;
                int i8 = (iStyleCodeAt & 512) != 0 ? ((-16777216) & iStyleCodeAt) >>> 24 : -1;
                if (i5 == 0) {
                    sb.append("\u001b[0m");
                    i2 = -1;
                    i3 = -1;
                } else {
                    sb.append("\u001b[");
                    boolean zAttr = true;
                    if ((i6 & 3) != 0) {
                        int i9 = i6 & 1;
                        if ((i9 != 0 && (iStyleCodeAt & 1) == 0) || ((i6 & 2) != 0 && (iStyleCodeAt & 2) == 0)) {
                            zAttr = attr(sb, "22", true);
                        }
                        if (i9 != 0 && (iStyleCodeAt & 1) != 0) {
                            zAttr = attr(sb, SchemaSymbols.ATTVAL_TRUE_1, zAttr);
                        }
                        if ((i6 & 2) != 0 && (iStyleCodeAt & 2) != 0) {
                            zAttr = attr(sb, "2", zAttr);
                        }
                    }
                    if ((i6 & 4) != 0) {
                        if ((iStyleCodeAt & 4) != 0) {
                            str3 = "3";
                        } else {
                            str3 = "23";
                        }
                        zAttr = attr(sb, str3, zAttr);
                    }
                    if ((i6 & 8) != 0) {
                        if ((iStyleCodeAt & 8) != 0) {
                            str2 = "4";
                        } else {
                            str2 = "24";
                        }
                        zAttr = attr(sb, str2, zAttr);
                    }
                    if ((i6 & 16) != 0) {
                        zAttr = attr(sb, (iStyleCodeAt & 16) != 0 ? "5" : "25", zAttr);
                    }
                    if ((i6 & 32) != 0) {
                        zAttr = attr(sb, (iStyleCodeAt & 32) != 0 ? "7" : "27", zAttr);
                    }
                    if ((i6 & 64) != 0) {
                        zAttr = attr(sb, (iStyleCodeAt & 64) != 0 ? "8" : "28", zAttr);
                    }
                    if ((i6 & 128) != 0) {
                        if ((iStyleCodeAt & 128) != 0) {
                            str = "9";
                        } else {
                            str = "29";
                        }
                        zAttr = attr(sb, str, zAttr);
                    }
                    if (i2 != i7) {
                        if (i7 >= 0) {
                            int iRoundColor = roundColor(i7, iIntValue);
                            if (iRoundColor < 8) {
                                zAttr = attr(sb, "3" + Integer.toString(iRoundColor), zAttr);
                            } else if (iRoundColor < 16) {
                                zAttr = attr(sb, "9" + Integer.toString(iRoundColor - 8), zAttr);
                            } else {
                                zAttr = attr(sb, "38;5;" + Integer.toString(iRoundColor), zAttr);
                            }
                        } else {
                            zAttr = attr(sb, "39", zAttr);
                        }
                        i2 = i7;
                    }
                    if (i3 != i8) {
                        if (i8 >= 0) {
                            int iRoundColor2 = roundColor(i8, iIntValue);
                            if (iRoundColor2 < 8) {
                                attr(sb, "4" + Integer.toString(iRoundColor2), zAttr);
                            } else if (iRoundColor2 < 16) {
                                attr(sb, "10" + Integer.toString(iRoundColor2 - 8), zAttr);
                            } else {
                                attr(sb, "48;5;" + Integer.toString(iRoundColor2), zAttr);
                            }
                        } else {
                            attr(sb, "49", zAttr);
                        }
                        i3 = i8;
                    }
                    sb.append("m");
                }
                i = i5;
            }
            sb.append(cCharAt);
        }
        if (i != 0) {
            sb.append("\u001b[0m");
        }
        return sb.toString();
    }

    public static int roundColor(int i, int i2) {
        if (i < i2) {
            return i;
        }
        int[] iArr = COLORS_256;
        int i3 = iArr[i];
        return roundColor((i3 >> 16) & 255, (i3 >> 8) & 255, i3 & 255, iArr, i2);
    }

    private static int roundColor(int i, int i2, int i3, int[] iArr, int i4) {
        int i5 = Integer.MAX_VALUE;
        int i6 = Integer.MAX_VALUE;
        for (int i7 = 0; i7 < i4; i7++) {
            int i8 = iArr[i7];
            int iSqr = (sqr(i - ((i8 >> 16) & 255)) * 2) + (sqr(i2 - ((i8 >> 8) & 255)) * 4) + (sqr(i3 - (i8 & 255)) * 3);
            if (iSqr <= i6) {
                i5 = i7;
                i6 = iSqr;
            }
        }
        return i5;
    }

    private static boolean attr(StringBuilder sb, String str, boolean z) {
        if (!z) {
            sb.append(";");
        }
        sb.append(str);
        return false;
    }

    int styleCodeAt(int i) {
        return styleAt(i).getStyle();
    }

    public boolean isHidden(int i) {
        return (styleCodeAt(i) & 1024) != 0;
    }

    public AttributedString substring(int i, int i2) {
        return subSequence(i, i2);
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return buffer()[offset() + i];
    }

    public int codePointAt(int i) {
        return Character.codePointAt(buffer(), i + offset());
    }

    public int columnLength() {
        int length = length();
        int iCharCount = 0;
        int iWcwidth = 0;
        while (iCharCount < length) {
            int iCodePointAt = codePointAt(iCharCount);
            if (!isHidden(iCharCount)) {
                iWcwidth += WCWidth.wcwidth(iCodePointAt);
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return iWcwidth;
    }

    public AttributedString columnSubSequence(int i, int i2) {
        int iCodePointAt;
        int i3 = 0;
        int iWcwidth = 0;
        while (i3 < length()) {
            int iWcwidth2 = (isHidden(i3) ? 0 : WCWidth.wcwidth(codePointAt(i3))) + iWcwidth;
            if (iWcwidth2 > i) {
                break;
            }
            i3++;
            iWcwidth = iWcwidth2;
        }
        int i4 = i3;
        while (i4 < length() && (iCodePointAt = codePointAt(i4)) != 10) {
            iWcwidth += isHidden(i4) ? 0 : WCWidth.wcwidth(iCodePointAt);
            if (iWcwidth > i2) {
                break;
            }
            i4++;
        }
        return subSequence(i3, i4);
    }

    public List<AttributedString> columnSplitLength(int i) {
        return columnSplitLength(i, false, true);
    }

    public List<AttributedString> columnSplitLength(int i, boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        int iCharCount = 0;
        int i2 = 0;
        int i3 = 0;
        while (iCharCount < length()) {
            int iCodePointAt = codePointAt(iCharCount);
            int iWcwidth = isHidden(iCharCount) ? 0 : WCWidth.wcwidth(iCodePointAt);
            if (iCodePointAt == 10) {
                arrayList.add(subSequence(i2, z ? iCharCount + 1 : iCharCount));
                i2 = iCharCount + 1;
                i3 = 0;
            } else {
                i3 += iWcwidth;
                if (i3 > i) {
                    arrayList.add(subSequence(i2, iCharCount));
                    i2 = iCharCount;
                    i3 = iWcwidth;
                }
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        arrayList.add(subSequence(i2, iCharCount));
        return arrayList;
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return new String(buffer(), offset(), length());
    }

    public AttributedString toAttributedString() {
        return substring(0, length());
    }
}
