package com.reandroid.dex.smali;

import com.reandroid.common.ByteSource;
import com.reandroid.common.Origin;
import com.reandroid.common.TextPosition;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.NumbersUtil;
import com.reandroid.utils.io.IOUtil;
import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliReader {
    private final ByteSource byteSource;
    private Origin origin;
    private int position;

    private static int base10Digit(byte b) {
        if (b < 48 || b > 57) {
            return -1;
        }
        return b - 48;
    }

    private static char decodeSkippedChar(char c) {
        if (c == 'b') {
            return '\b';
        }
        if (c == 'f') {
            return '\f';
        }
        if (c == 'n') {
            return '\n';
        }
        if (c == 'r') {
            return '\r';
        }
        if (c != 't') {
            return c;
        }
        return '\t';
    }

    private static boolean isLineEnd(byte b) {
        return b == 10 || b == 13 || b == 35;
    }

    private static boolean isNumber(byte b) {
        if (b >= 48 && b <= 57) {
            return true;
        }
        if (b < 97 || b > 122) {
            return (b >= 65 && b <= 122) || b == 43 || b == 45 || b == 46;
        }
        return true;
    }

    public static boolean isSpace(byte b) {
        return b == 9 || b == 32;
    }

    public static boolean isWhiteSpace(byte b) {
        return b == 9 || b == 10 || b == 13 || b == 32;
    }

    public SmaliReader(ByteSource byteSource) {
        this.byteSource = byteSource;
    }

    public SmaliReader(byte[] bArr) {
        this(ByteSource.of(bArr));
    }

    public void reset() {
        position(0);
    }

    public int position() {
        return this.position;
    }

    public void position(int i) {
        this.position = i;
    }

    public int available() {
        return this.byteSource.length() - this.position;
    }

    public boolean finished() {
        return available() == 0;
    }

    public void offset(int i) {
        position(position() + i);
    }

    public byte get() {
        return get(position());
    }

    public char getASCII(int i) {
        return (char) (get(i) & 255);
    }

    public byte get(int i) {
        return this.byteSource.read(i);
    }

    public char readASCII() {
        return (char) (read() & 255);
    }

    public byte read() {
        int i = this.position;
        this.position = i + 1;
        return this.byteSource.read(i);
    }

    public String getString(int i) {
        return new String(getBytes(i), StandardCharsets.UTF_8);
    }

    public String readString(int i) {
        return new String(readBytes(i), StandardCharsets.UTF_8);
    }

    public String readEscapedString(char c) throws IOException {
        int iPosition = position();
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        boolean z2 = false;
        while (!finished()) {
            char ascii = readASCII();
            if (ascii > 127) {
                z2 = true;
            }
            if (z) {
                sb.append(decodeSkipped(this, ascii));
                z = false;
            } else if (ascii == '\\') {
                z = true;
            } else {
                if (ascii == c) {
                    skip(-1);
                    if (z2) {
                        int iPosition2 = position() - iPosition;
                        position(iPosition);
                        return decodeEscapedString(readString(iPosition2));
                    }
                    return sb.toString();
                }
                sb.append(ascii);
            }
        }
        skip(-1);
        throw new SmaliParseException("Missing character '" + c + FormatHelper.QUOTE, this);
    }

    public String readStringForNumber() {
        int iIndexOfLineEnd = indexOfLineEnd();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int iPosition = position(); iPosition < iIndexOfLineEnd; iPosition++) {
            byte b = get(iPosition);
            if (!isNumber(b)) {
                break;
            }
            sb.append((char) (b & 255));
            i++;
        }
        skip(i);
        return sb.toString();
    }

    public int readInteger() throws IOException {
        byte b = get();
        int i = 0;
        boolean z = b == 45;
        if (z || b == 43) {
            skip(1);
        }
        int iPosition = position();
        while (!finished()) {
            int iBase10Digit = base10Digit(read());
            if (iBase10Digit == -1 || i < 0) {
                skip(-1);
                break;
            }
            i = (i * 10) + iBase10Digit;
        }
        if (iPosition == position()) {
            throw new SmaliParseException("Invalid integer format", this);
        }
        if (i >= 0) {
            return z ? -i : i;
        }
        skip(-1);
        throw new SmaliParseException("Integer overflow", this);
    }

    public byte[] readBytes(int i) {
        byte[] bytes = getBytes(i);
        offset(i);
        return bytes;
    }

    public byte[] getBytes(int i) {
        byte[] bArr = new byte[i];
        int iPosition = position();
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = get(iPosition + i2);
        }
        return bArr;
    }

    public boolean startsWith(byte[] bArr) {
        return startsWith(bArr, position());
    }

    public boolean startsWith(byte[] bArr, int i) {
        if (available() < bArr.length) {
            return false;
        }
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (bArr[i2] != get(i + i2)) {
                return false;
            }
        }
        return true;
    }

    public int startsWithSqueezeSpaces(byte[] bArr) {
        int iPosition = position();
        int iAvailable = available();
        int length = bArr.length;
        if (iAvailable != 0 && length != 0) {
            int i = 0;
            boolean z = false;
            for (int i2 = 0; i2 < iAvailable; i2++) {
                byte b = get(iPosition + i2);
                if (b == 32) {
                    if (z) {
                        continue;
                    } else {
                        z = true;
                    }
                } else {
                    z = false;
                }
                if (i == length) {
                    return i2;
                }
                byte b2 = bArr[i];
                i++;
                if (b != b2) {
                    return -1;
                }
            }
            if (i == length) {
                return length;
            }
        }
        return -1;
    }

    public int indexOf(char c) {
        return indexOf((byte) c);
    }

    public int indexOfWhiteSpace() {
        int iPosition = position();
        int iAvailable = available() + iPosition;
        while (iPosition < iAvailable) {
            if (isWhiteSpace(get(iPosition))) {
                return iPosition;
            }
            iPosition++;
        }
        return iAvailable;
    }

    public int indexOfWhiteSpaceOrComment() {
        int iPosition = position();
        int iAvailable = available() + iPosition;
        while (iPosition < iAvailable) {
            if (isWhiteSpaceOrComment(get(iPosition))) {
                return iPosition;
            }
            iPosition++;
        }
        return iAvailable;
    }

    public int indexOfLineEnd() {
        int iPosition = position();
        int iAvailable = available() + iPosition;
        while (iPosition < iAvailable) {
            if (isLineEnd(get(iPosition))) {
                return iPosition;
            }
            iPosition++;
        }
        return iAvailable;
    }

    public int indexOf(byte b) {
        return indexOf(position(), b);
    }

    public int indexOf(int i, byte b) {
        return this.byteSource.indexOf(i, b);
    }

    public int indexOfBeforeLineEnd(char c) {
        int iPosition = position();
        int iAvailable = available() + iPosition;
        while (iPosition < iAvailable) {
            byte b = get(iPosition);
            if (c == b) {
                return iPosition;
            }
            if (isLineEnd(b)) {
                return -1;
            }
            iPosition++;
        }
        return -1;
    }

    public int indexOf(byte[] bArr) {
        int length = bArr.length;
        if (length == 0) {
            return -1;
        }
        int iPosition = position();
        int iAvailable = (available() + iPosition) - length;
        while (iPosition <= iAvailable) {
            if (equalsAt(iPosition, bArr)) {
                return iPosition;
            }
            iPosition++;
        }
        return -1;
    }

    private boolean equalsAt(int i, byte[] bArr) {
        int length = bArr.length;
        if (length > available() - i) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (bArr[i2] != get(i2 + i)) {
                return false;
            }
        }
        return true;
    }

    public boolean skipWhitespacesOrComment() {
        boolean z = false;
        if (finished()) {
            return false;
        }
        z = true;
        if (get() == 35) {
            nextLine();
            z = true;
        }
        while (skipWhitespaces()) {
            if (get() == 35) {
                nextLine();
            }
        }
        return z;
    }

    public boolean skipWhitespaces() {
        if (finished()) {
            return false;
        }
        int iPosition = position();
        int iAvailable = available() + iPosition;
        int i = iPosition;
        int i2 = i;
        while (i < iAvailable && isWhiteSpace(get(i))) {
            i2 = i + 1;
            i = i2;
        }
        if (i2 == iPosition) {
            return false;
        }
        position(i2);
        return i2 != iAvailable;
    }

    public boolean skipSpaces() {
        int iPosition = position();
        int iAvailable = available() + iPosition;
        int i = iPosition;
        while (true) {
            if (i >= iAvailable) {
                i = iPosition;
                break;
            }
            if (!isSpace(get(i))) {
                break;
            }
            i++;
        }
        if (i == iPosition) {
            return false;
        }
        position(i);
        return true;
    }

    public void nextLine() {
        int iIndexOf = indexOf('\n');
        if (iIndexOf < 0) {
            iIndexOf = position() + available();
        }
        position(iIndexOf);
    }

    public void skip(int i) {
        int iAvailable = available();
        if (i > iAvailable) {
            i = iAvailable;
        }
        position(i + position());
    }

    public Origin getCurrentOrigin() {
        return getOrigin(position());
    }

    public Origin getOrigin(int i) {
        return getOrigin().createChild(new SmaliTextPosition(this.byteSource, i));
    }

    public Origin getOrigin() {
        Origin origin = this.origin;
        if (origin != null) {
            return origin;
        }
        Origin originNewRoot = Origin.newRoot();
        this.origin = originNewRoot;
        return originNewRoot;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public String toString() {
        return getCurrentOrigin().toString();
    }

    public static boolean isWhiteSpaceOrComment(byte b) {
        return isWhiteSpace(b) || b == 35;
    }

    private static char decodeSkipped(SmaliReader smaliReader, char c) {
        if (c == 'u') {
            return decodeFourHex(smaliReader);
        }
        return decodeSkippedChar(c);
    }

    private static char decodeFourHex(SmaliReader smaliReader) {
        return (char) (HexUtil.decodeHexChar(smaliReader.read()) | (((((HexUtil.decodeHexChar(smaliReader.read()) << 4) | HexUtil.decodeHexChar(smaliReader.read())) << 4) | HexUtil.decodeHexChar(smaliReader.read())) << 4));
    }

    public static String decodeEscapedString(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int i = 0;
        boolean z = false;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (z) {
                if (cCharAt == 'u') {
                    char cCharAt2 = str.charAt(i + 1);
                    char cCharAt3 = str.charAt(i + 2);
                    char cCharAt4 = str.charAt(i + 3);
                    i += 4;
                    sb.append(decodeHex(cCharAt2, cCharAt3, cCharAt4, str.charAt(i)));
                } else {
                    sb.append(decodeSkippedChar(cCharAt));
                }
                z = false;
            } else if (cCharAt == '\\') {
                z = true;
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
        return sb.toString();
    }

    private static char decodeHex(char c, char c2, char c3, char c4) {
        return (char) ((((((HexUtil.decodeHexChar(c) << 4) | HexUtil.decodeHexChar(c2)) << 4) | HexUtil.decodeHexChar(c3)) << 4) | HexUtil.decodeHexChar(c4));
    }

    public static SmaliReader of(String str) {
        SmaliReader smaliReader = new SmaliReader(str.getBytes(StandardCharsets.UTF_8));
        smaliReader.setOrigin(Origin.createNew("<text-source>"));
        return smaliReader;
    }

    public static SmaliReader of(File file) throws IOException {
        SmaliReader smaliReader = new SmaliReader(IOUtil.readFully(file));
        smaliReader.setOrigin(Origin.createNew(file));
        return smaliReader;
    }

    public static SmaliReader of(InputStream inputStream) throws IOException {
        SmaliReader smaliReader = new SmaliReader(IOUtil.readFully(inputStream));
        smaliReader.setOrigin(Origin.createNew("<" + inputStream.getClass().getName() + ">"));
        return smaliReader;
    }

    static class SmaliTextPosition extends TextPosition {
        private ByteSource byteSource;
        private final int position;

        public SmaliTextPosition(ByteSource byteSource, int i) {
            this.byteSource = byteSource;
            this.position = i;
        }

        @Override // com.reandroid.common.TextPosition
        public int getLineNumber() {
            computeValues();
            return super.getLineNumber();
        }

        @Override // com.reandroid.common.TextPosition
        public int getColumnNumber() {
            computeValues();
            return super.getColumnNumber();
        }

        private void computeValues() {
            int i;
            int i2;
            ByteSource byteSource = this.byteSource;
            if (byteSource == null) {
                return;
            }
            this.byteSource = null;
            int i3 = 1;
            try {
                int iMin = NumbersUtil.min(this.position, byteSource.length());
                i2 = 1;
                i = 1;
                for (int i4 = 0; i4 < iMin; i4++) {
                    try {
                        if (byteSource.read(i4) == 10) {
                            i2++;
                            i = 1;
                        } else {
                            i++;
                        }
                    } catch (Throwable th) {
                        th = th;
                        i3 = i2;
                        setDescription(th.getMessage());
                        i2 = i3;
                        setLineNumber(i2);
                        setColumnNumber(i);
                        this.byteSource = null;
                    }
                }
                setDescription(computePositionDescription(byteSource));
            } catch (Throwable th2) {
                th = th2;
                i = 1;
            }
            setLineNumber(i2);
            setColumnNumber(i);
            this.byteSource = null;
        }

        private String computePositionDescription(ByteSource byteSource) {
            int i = this.position;
            if (i >= byteSource.length()) {
                return "EOF";
            }
            StringBuilder sb = new StringBuilder("\n");
            int i2 = i;
            while (byteSource.read(i2) != 10 && i2 != 0) {
                i2--;
            }
            if (byteSource.read(i2) == 10) {
                i2++;
            }
            if (i - i2 > 38) {
                i2 = i - 38;
            }
            int iIndexOf = byteSource.length() - i > 1 ? byteSource.indexOf(i2, (byte) 10) : -1;
            if (iIndexOf < 0) {
                iIndexOf = (i == 0 ? i2 : i) + (byteSource.length() - i);
            }
            if (iIndexOf - i > 38) {
                iIndexOf = i + 38;
            }
            for (int i3 = i2; i3 < iIndexOf; i3++) {
                sb.append((char) (byteSource.read(i3) & 255));
            }
            sb.append('\n');
            while (i2 < i) {
                sb.append(StringUtil.SPACE);
                i2++;
            }
            sb.append(Util.C_EXCEPTION_START);
            return sb.toString();
        }
    }
}
