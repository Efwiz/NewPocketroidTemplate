package org.jetbrains.kotlin.org.jline.utils;

import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes4.dex */
public class AttributedStyle {
    public static final AttributedStyle BOLD;
    public static final AttributedStyle BOLD_OFF;
    public static final AttributedStyle DEFAULT;
    public static final AttributedStyle HIDDEN;
    public static final AttributedStyle HIDDEN_OFF;
    public static final AttributedStyle INVERSE;
    public static final AttributedStyle INVERSE_OFF;
    final int mask;
    final int style;

    static {
        AttributedStyle attributedStyle = new AttributedStyle();
        DEFAULT = attributedStyle;
        BOLD = attributedStyle.bold();
        BOLD_OFF = attributedStyle.boldOff();
        INVERSE = attributedStyle.inverse();
        INVERSE_OFF = attributedStyle.inverseOff();
        HIDDEN = attributedStyle.hidden();
        HIDDEN_OFF = attributedStyle.hiddenOff();
    }

    AttributedStyle() {
        this(0, 0);
    }

    AttributedStyle(int i, int i2) {
        this.style = i;
        this.mask = (i2 & 2047) | ((i & 256) != 0 ? 16711680 : 0) | ((i & 512) != 0 ? ViewCompat.MEASURED_STATE_MASK : 0);
    }

    public AttributedStyle bold() {
        return new AttributedStyle(this.style | 1, this.mask | 1);
    }

    public AttributedStyle boldOff() {
        return new AttributedStyle(this.style & (-2), this.mask | 1);
    }

    public AttributedStyle faint() {
        return new AttributedStyle(this.style | 2, this.mask | 2);
    }

    public AttributedStyle faintOff() {
        return new AttributedStyle(this.style & (-3), this.mask | 2);
    }

    public AttributedStyle italic() {
        return new AttributedStyle(this.style | 4, this.mask | 4);
    }

    public AttributedStyle italicOff() {
        return new AttributedStyle(this.style & (-5), this.mask | 4);
    }

    public AttributedStyle underline() {
        return new AttributedStyle(this.style | 8, this.mask | 8);
    }

    public AttributedStyle underlineOff() {
        return new AttributedStyle(this.style & (-9), this.mask | 8);
    }

    public AttributedStyle blink() {
        return new AttributedStyle(this.style | 16, this.mask | 16);
    }

    public AttributedStyle blinkOff() {
        return new AttributedStyle(this.style & (-17), this.mask | 16);
    }

    public AttributedStyle inverse() {
        return new AttributedStyle(this.style | 32, this.mask | 32);
    }

    public AttributedStyle inverseNeg() {
        int i = this.style;
        return new AttributedStyle((i & 32) != 0 ? i & (-33) : i | 32, this.mask | 32);
    }

    public AttributedStyle inverseOff() {
        return new AttributedStyle(this.style & (-33), this.mask | 32);
    }

    public AttributedStyle conceal() {
        return new AttributedStyle(this.style | 64, this.mask | 64);
    }

    public AttributedStyle concealOff() {
        return new AttributedStyle(this.style & (-65), this.mask | 64);
    }

    public AttributedStyle crossedOut() {
        return new AttributedStyle(this.style | 128, this.mask | 128);
    }

    public AttributedStyle crossedOutOff() {
        return new AttributedStyle(this.style & (-129), this.mask | 128);
    }

    public AttributedStyle foreground(int i) {
        return new AttributedStyle(((i << 16) & 16711680) | (this.style & (-16711681)) | 256, this.mask | 256);
    }

    public AttributedStyle foregroundOff() {
        return new AttributedStyle(this.style & (-16711937), this.mask | 256);
    }

    public AttributedStyle background(int i) {
        return new AttributedStyle(((i << 24) & ViewCompat.MEASURED_STATE_MASK) | (this.style & 16777215) | 512, this.mask | 512);
    }

    public AttributedStyle backgroundOff() {
        return new AttributedStyle(this.style & 16776703, this.mask | 512);
    }

    public AttributedStyle hidden() {
        return new AttributedStyle(this.style | 1024, this.mask | 1024);
    }

    public AttributedStyle hiddenOff() {
        return new AttributedStyle(this.style & (-1025), this.mask | 1024);
    }

    int getStyle() {
        return this.style;
    }

    int getMask() {
        return this.mask;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AttributedStyle attributedStyle = (AttributedStyle) obj;
        return this.style == attributedStyle.style && this.mask == attributedStyle.mask;
    }

    public int hashCode() {
        return (this.style * 31) + this.mask;
    }
}
