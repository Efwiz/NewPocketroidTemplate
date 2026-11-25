package org.jetbrains.kotlin.org.jline.utils;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.function.Function;

/* JADX INFO: loaded from: classes4.dex */
public class AttributedStringBuilder extends AttributedCharSequence implements Appendable {
    private char[] buffer;
    private AttributedStyle current;
    private int length;
    private int[] style;
    private int tabs;

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence
    protected int offset() {
        return 0;
    }

    public static AttributedString append(CharSequence... charSequenceArr) {
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        for (CharSequence charSequence : charSequenceArr) {
            attributedStringBuilder.append(charSequence);
        }
        return attributedStringBuilder.toAttributedString();
    }

    public AttributedStringBuilder() {
        this(64);
    }

    public AttributedStringBuilder(int i) {
        this.tabs = 0;
        this.current = AttributedStyle.DEFAULT;
        this.buffer = new char[i];
        this.style = new int[i];
        this.length = 0;
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.length;
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence, java.lang.CharSequence
    public char charAt(int i) {
        return this.buffer[i];
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence
    public AttributedStyle styleAt(int i) {
        int i2 = this.style[i];
        return new AttributedStyle(i2, i2);
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence
    int styleCodeAt(int i) {
        return this.style[i];
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence
    protected char[] buffer() {
        return this.buffer;
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence, java.lang.CharSequence
    public AttributedString subSequence(int i, int i2) {
        return new AttributedString(Arrays.copyOfRange(this.buffer, i, i2), Arrays.copyOfRange(this.style, i, i2), 0, i2 - i);
    }

    @Override // java.lang.Appendable
    public AttributedStringBuilder append(CharSequence charSequence) {
        return append(new AttributedString(charSequence, this.current));
    }

    @Override // java.lang.Appendable
    public AttributedStringBuilder append(CharSequence charSequence, int i, int i2) {
        return append(charSequence.subSequence(i, i2));
    }

    @Override // java.lang.Appendable
    public AttributedStringBuilder append(char c) {
        return append((CharSequence) Character.toString(c));
    }

    public AttributedStringBuilder style(AttributedStyle attributedStyle) {
        this.current = attributedStyle;
        return this;
    }

    public AttributedStringBuilder style(Function<AttributedStyle, AttributedStyle> function) {
        this.current = function.apply(this.current);
        return this;
    }

    public AttributedStyle style() {
        return this.current;
    }

    public AttributedStringBuilder append(AttributedString attributedString) {
        return append(attributedString, 0, attributedString.length());
    }

    public AttributedStringBuilder append(AttributedString attributedString, int i, int i2) {
        ensureCapacity((this.length + i2) - i);
        while (i < i2) {
            char cCharAt = attributedString.charAt(i);
            int iStyleCodeAt = (attributedString.styleCodeAt(i) & (~this.current.getMask())) | this.current.getStyle();
            if (this.tabs > 0 && cCharAt == '\t') {
                insertTab(new AttributedStyle(iStyleCodeAt, 0));
            } else {
                ensureCapacity(this.length + 1);
                char[] cArr = this.buffer;
                int i3 = this.length;
                cArr[i3] = cCharAt;
                this.style[i3] = iStyleCodeAt;
                this.length = i3 + 1;
            }
            i++;
        }
        return this;
    }

    protected void ensureCapacity(int i) {
        char[] cArr = this.buffer;
        if (i > cArr.length) {
            int length = cArr.length;
            while (length <= i) {
                length *= 2;
            }
            this.buffer = Arrays.copyOf(this.buffer, length);
            this.style = Arrays.copyOf(this.style, length);
        }
    }

    /* JADX WARN: Code duplicated, block: B:67:0x0125  */
    public void appendAnsi(String str) {
        ensureCapacity(this.length + str.length());
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (i == 0 && cCharAt == 27) {
                i++;
            } else if (i == 1 && cCharAt == '[') {
                i++;
                i2 = i3 + 1;
            } else if (i == 2) {
                if (cCharAt == 'm') {
                    String[] strArrSplit = str.substring(i2, i3).split(";");
                    int i4 = 0;
                    while (i4 < strArrSplit.length) {
                        int i5 = strArrSplit[i4].isEmpty() ? 0 : Integer.parseInt(strArrSplit[i4]);
                        if (i5 == 0) {
                            this.current = AttributedStyle.DEFAULT;
                        } else if (i5 == 1) {
                            this.current = this.current.bold();
                        } else if (i5 == 2) {
                            this.current = this.current.faint();
                        } else if (i5 == 3) {
                            this.current = this.current.italic();
                        } else if (i5 == 4) {
                            this.current = this.current.underline();
                        } else if (i5 == 5) {
                            this.current = this.current.blink();
                        } else if (i5 == 7) {
                            this.current = this.current.inverse();
                        } else if (i5 == 8) {
                            this.current = this.current.conceal();
                        } else if (i5 == 9) {
                            this.current = this.current.crossedOut();
                        } else {
                            switch (i5) {
                                case 22:
                                    this.current = this.current.boldOff().faintOff();
                                    break;
                                case 23:
                                    this.current = this.current.italicOff();
                                    break;
                                case 24:
                                    this.current = this.current.underlineOff();
                                    break;
                                case 25:
                                    this.current = this.current.blinkOff();
                                    break;
                                default:
                                    switch (i5) {
                                        case 27:
                                            this.current = this.current.inverseOff();
                                            break;
                                        case 28:
                                            this.current = this.current.concealOff();
                                            break;
                                        case 29:
                                            this.current = this.current.crossedOutOff();
                                            break;
                                        case 30:
                                        case 31:
                                        case 32:
                                        case 33:
                                        case 34:
                                        case 35:
                                        case 36:
                                        case 37:
                                            this.current = this.current.foreground(i5 - 30);
                                            break;
                                        case 38:
                                        case 48:
                                            int i6 = i4 + 1;
                                            if (i6 < strArrSplit.length) {
                                                int i7 = Integer.parseInt(strArrSplit[i6]);
                                                if (i7 == 2) {
                                                    if (i4 + 4 < strArrSplit.length) {
                                                        int i8 = Integer.parseInt(strArrSplit[i4 + 2]);
                                                        int i9 = Integer.parseInt(strArrSplit[i4 + 3]);
                                                        i4 += 4;
                                                        int i10 = ((i8 >> 3) * 36) + 16 + ((i9 >> 3) * 6) + (Integer.parseInt(strArrSplit[i4]) >> 3);
                                                        if (i5 == 38) {
                                                            this.current = this.current.foreground(i10);
                                                        } else {
                                                            this.current = this.current.background(i10);
                                                        }
                                                    } else {
                                                        i4 = i6;
                                                    }
                                                } else if (i7 != 5 || (i4 = i4 + 2) >= strArrSplit.length) {
                                                    i4 = i6;
                                                } else {
                                                    int i11 = Integer.parseInt(strArrSplit[i4]);
                                                    if (i5 == 38) {
                                                        this.current = this.current.foreground(i11);
                                                    } else {
                                                        this.current = this.current.background(i11);
                                                    }
                                                }
                                            }
                                            break;
                                        case 39:
                                            this.current = this.current.foregroundOff();
                                            break;
                                        case 40:
                                        case 41:
                                        case 42:
                                        case 43:
                                        case 44:
                                        case 45:
                                        case 46:
                                        case 47:
                                            this.current = this.current.background(i5 - 40);
                                            break;
                                        case 49:
                                            this.current = this.current.backgroundOff();
                                            break;
                                        default:
                                            switch (i5) {
                                                case 90:
                                                case 91:
                                                case 92:
                                                case 93:
                                                case 94:
                                                case 95:
                                                case 96:
                                                case 97:
                                                    this.current = this.current.foreground(i5 - 82);
                                                    break;
                                                default:
                                                    switch (i5) {
                                                        case 100:
                                                        case 101:
                                                        case 102:
                                                        case 103:
                                                        case 104:
                                                        case 105:
                                                        case 106:
                                                        case 107:
                                                            this.current = this.current.background(i5 - 92);
                                                            break;
                                                    }
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                            }
                        }
                        i4++;
                    }
                } else if ((cCharAt < '0' || cCharAt > '9') && cCharAt != ';') {
                }
                i = 0;
            } else if (cCharAt == '\t' && this.tabs > 0) {
                insertTab(this.current);
            } else {
                ensureCapacity(this.length + 1);
                char[] cArr = this.buffer;
                int i12 = this.length;
                cArr[i12] = cCharAt;
                this.style[i12] = this.current.getStyle();
                this.length++;
            }
        }
    }

    protected void insertTab(AttributedStyle attributedStyle) {
        int i = this.tabs;
        int i2 = this.length;
        int i3 = i - (i2 % i);
        ensureCapacity(i2 + i3);
        for (int i4 = 0; i4 < i3; i4++) {
            char[] cArr = this.buffer;
            int i5 = this.length;
            cArr[i5] = StringUtil.SPACE;
            this.style[i5] = attributedStyle.getStyle();
            this.length++;
        }
    }

    public void setLength(int i) {
        this.length = i;
    }

    public AttributedStringBuilder tabs(int i) {
        this.tabs = i;
        return this;
    }
}
