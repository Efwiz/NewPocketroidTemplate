package org.eclipse.jgit.revwalk;

import java.nio.charset.Charset;
import org.eclipse.jgit.util.RawParseUtils;

/* JADX INFO: loaded from: classes13.dex */
public final class FooterLine {
    private final byte[] buffer;
    private final Charset enc;
    private final int keyEnd;
    private final int keyStart;
    private final int valEnd;
    private final int valStart;

    FooterLine(byte[] bArr, Charset charset, int i, int i2, int i3, int i4) {
        this.buffer = bArr;
        this.enc = charset;
        this.keyStart = i;
        this.keyEnd = i2;
        this.valStart = i3;
        this.valEnd = i4;
    }

    public boolean matches(FooterKey footerKey) {
        byte[] bArr = footerKey.raw;
        int length = bArr.length;
        int i = this.keyStart;
        if (this.keyEnd - i != length) {
            return false;
        }
        int i2 = 0;
        while (i2 < length) {
            int i3 = i + 1;
            byte b = this.buffer[i];
            if (65 <= b && b <= 90) {
                b = (byte) (b + 32);
            }
            int i4 = i2 + 1;
            if (b != bArr[i2]) {
                return false;
            }
            i2 = i4;
            i = i3;
        }
        return true;
    }

    public String getKey() {
        return RawParseUtils.decode(this.enc, this.buffer, this.keyStart, this.keyEnd);
    }

    public String getValue() {
        return RawParseUtils.decode(this.enc, this.buffer, this.valStart, this.valEnd);
    }

    public String getEmailAddress() {
        int iNextLF = RawParseUtils.nextLF(this.buffer, this.valStart, '<');
        if (this.valEnd <= iNextLF) {
            int iNextLF2 = RawParseUtils.nextLF(this.buffer, this.valStart, '@');
            if (this.valStart >= iNextLF2 || iNextLF2 >= this.valEnd) {
                return null;
            }
            return getValue();
        }
        int iNextLF3 = RawParseUtils.nextLF(this.buffer, iNextLF, '>');
        if (this.valEnd < iNextLF3) {
            return null;
        }
        return RawParseUtils.decode(this.enc, this.buffer, iNextLF, iNextLF3 - 1);
    }

    public String toString() {
        return String.valueOf(getKey()) + ": " + getValue();
    }
}
