package org.apache.xerces.impl.io;

import com.gaurav.avnc.vnc.XKeySym;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Locale;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.MessageFormatter;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;

/* JADX INFO: loaded from: classes13.dex */
public final class UTF8Reader extends Reader {
    private static final boolean DEBUG_READ = false;
    public static final int DEFAULT_BUFFER_SIZE = 2048;
    protected final byte[] fBuffer;
    private final MessageFormatter fFormatter;
    protected final InputStream fInputStream;
    private final Locale fLocale;
    protected int fOffset;
    private int fSurrogate;

    public UTF8Reader(InputStream inputStream) {
        this(inputStream, 2048, new XMLMessageFormatter(), Locale.getDefault());
    }

    public UTF8Reader(InputStream inputStream, int i, MessageFormatter messageFormatter, Locale locale) {
        this(inputStream, new byte[i], messageFormatter, locale);
    }

    public UTF8Reader(InputStream inputStream, MessageFormatter messageFormatter, Locale locale) {
        this(inputStream, 2048, messageFormatter, locale);
    }

    public UTF8Reader(InputStream inputStream, byte[] bArr, MessageFormatter messageFormatter, Locale locale) {
        this.fSurrogate = -1;
        this.fInputStream = inputStream;
        this.fBuffer = bArr;
        this.fFormatter = messageFormatter;
        this.fLocale = locale;
    }

    private void expectedByte(int i, int i2) throws MalformedByteSequenceException {
        throw new MalformedByteSequenceException(this.fFormatter, this.fLocale, "http://www.w3.org/TR/1998/REC-xml-19980210", "ExpectedByte", new Object[]{Integer.toString(i), Integer.toString(i2)});
    }

    private void invalidByte(int i, int i2, int i3) throws MalformedByteSequenceException {
        throw new MalformedByteSequenceException(this.fFormatter, this.fLocale, "http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidByte", new Object[]{Integer.toString(i), Integer.toString(i2)});
    }

    private void invalidSurrogate(int i) throws MalformedByteSequenceException {
        throw new MalformedByteSequenceException(this.fFormatter, this.fLocale, "http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidHighSurrogate", new Object[]{Integer.toHexString(i)});
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.fInputStream.close();
    }

    @Override // java.io.Reader
    public void mark(int i) throws IOException {
        throw new IOException(this.fFormatter.formatMessage(this.fLocale, "OperationNotSupported", new Object[]{"mark()", "UTF-8"}));
    }

    @Override // java.io.Reader
    public boolean markSupported() {
        return false;
    }

    @Override // java.io.Reader
    public int read() throws IOException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = this.fSurrogate;
        int i6 = -1;
        if (i5 == -1) {
            int i7 = 0;
            if (this.fOffset == 0) {
                i = this.fInputStream.read();
            } else {
                i = this.fBuffer[0] & 255;
                i7 = 1;
            }
            if (i == -1) {
                return -1;
            }
            if (i < 128) {
                return (char) i;
            }
            if ((i & 224) == 192 && (i & 30) != 0) {
                int i8 = i7 == this.fOffset ? this.fInputStream.read() : this.fBuffer[i7] & 255;
                if (i8 == -1) {
                    expectedByte(2, 2);
                }
                if ((i8 & 192) != 128) {
                    invalidByte(2, 2, i8);
                }
                return (i8 & 63) | ((i << 6) & 1984);
            }
            if ((i & 240) == 224) {
                if (i7 == this.fOffset) {
                    i4 = this.fInputStream.read();
                } else {
                    i4 = this.fBuffer[i7] & 255;
                    i7++;
                }
                if (i4 == -1) {
                    expectedByte(2, 3);
                }
                if ((i4 & 192) != 128 || ((i == 237 && i4 >= 160) || ((i & 15) == 0 && (i4 & 32) == 0))) {
                    invalidByte(2, 3, i4);
                }
                int i9 = i7 == this.fOffset ? this.fInputStream.read() : this.fBuffer[i7] & 255;
                if (i9 == -1) {
                    expectedByte(3, 3);
                }
                if ((i9 & 192) != 128) {
                    invalidByte(3, 3, i9);
                }
                return ((i4 << 6) & ASTNode.OperatorMASK) | ((i << 12) & 61440) | (i9 & 63);
            }
            if ((i & 248) != 240) {
                invalidByte(1, 1, i);
                return i5;
            }
            if (i7 == this.fOffset) {
                i2 = this.fInputStream.read();
            } else {
                i2 = this.fBuffer[i7] & 255;
                i7++;
            }
            if (i2 == -1) {
                expectedByte(2, 4);
            }
            if ((i2 & 192) != 128 || ((i2 & 48) == 0 && (i & 7) == 0)) {
                invalidByte(2, 3, i2);
            }
            if (i7 == this.fOffset) {
                i3 = this.fInputStream.read();
            } else {
                i3 = this.fBuffer[i7] & 255;
                i7++;
            }
            if (i3 == -1) {
                expectedByte(3, 4);
            }
            if ((i3 & 192) != 128) {
                invalidByte(3, 3, i3);
            }
            int i10 = i7 == this.fOffset ? this.fInputStream.read() : this.fBuffer[i7] & 255;
            if (i10 == -1) {
                expectedByte(4, 4);
            }
            if ((i10 & 192) != 128) {
                invalidByte(4, 4, i10);
            }
            int i11 = ((i << 2) & 28) | ((i2 >> 4) & 3);
            if (i11 > 16) {
                invalidSurrogate(i11);
            }
            i5 = ((i2 << 2) & 60) | (((i11 - 1) << 6) & XKeySym.XK_Amacron) | 55296 | ((i3 >> 4) & 3);
            i6 = ((i3 << 6) & XKeySym.XK_Amacron) | 56320 | (i10 & 63);
        }
        this.fSurrogate = i6;
        return i5;
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3;
        int length;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12 = this.fOffset;
        int i13 = -1;
        if (i12 == 0) {
            byte[] bArr = this.fBuffer;
            length = i2 > bArr.length ? bArr.length : i2;
            int i14 = this.fSurrogate;
            if (i14 != -1) {
                i3 = i + 1;
                cArr[i] = (char) i14;
                this.fSurrogate = -1;
                length--;
            } else {
                i3 = i;
            }
            int i15 = this.fInputStream.read(bArr, 0, length);
            if (i15 == -1) {
                return -1;
            }
            i12 = i15 + (i3 - i);
        } else {
            this.fOffset = 0;
            i3 = i;
            length = i2;
        }
        int i16 = 0;
        while (i16 < i12) {
            byte b = this.fBuffer[i16];
            if (b < 0) {
                break;
            }
            cArr[i3] = (char) b;
            i16++;
            i3++;
        }
        int i17 = i12;
        while (i16 < i12) {
            byte[] bArr2 = this.fBuffer;
            byte b2 = bArr2[i16];
            if (b2 >= 0) {
                cArr[i3] = (char) b2;
                i3++;
            } else {
                int i18 = b2 & 255;
                int i19 = 2;
                if ((b2 & 224) != 192 || (b2 & 30) == 0) {
                    i19 = 3;
                    if ((b2 & 240) == 224) {
                        int i20 = i16 + 1;
                        if (i20 < i12) {
                            i9 = bArr2[i20] & 255;
                        } else {
                            i9 = this.fInputStream.read();
                            if (i9 == i13) {
                                if (i3 > i) {
                                    this.fBuffer[0] = (byte) i18;
                                    this.fOffset = 1;
                                } else {
                                    expectedByte(2, 3);
                                }
                            }
                            i17++;
                        }
                        if ((i9 & 192) != 128 || ((i18 == 237 && i9 >= 160) || ((b2 & 15) == 0 && (i9 & 32) == 0))) {
                            if (i3 > i) {
                                byte[] bArr3 = this.fBuffer;
                                bArr3[0] = (byte) i18;
                                bArr3[1] = (byte) i9;
                                this.fOffset = 2;
                            } else {
                                invalidByte(2, 3, i9);
                            }
                        }
                        i16 += 2;
                        if (i16 < i12) {
                            i10 = this.fBuffer[i16] & 255;
                        } else {
                            i10 = this.fInputStream.read();
                            if (i10 == i13) {
                                if (i3 > i) {
                                    byte[] bArr4 = this.fBuffer;
                                    bArr4[0] = (byte) i18;
                                    bArr4[1] = (byte) i9;
                                    i7 = 2;
                                    this.fOffset = i7;
                                } else {
                                    expectedByte(3, 3);
                                }
                            }
                            i17++;
                        }
                        if ((i10 & 192) != 128) {
                            if (i3 > i) {
                                byte[] bArr5 = this.fBuffer;
                                bArr5[0] = (byte) i18;
                                bArr5[1] = (byte) i9;
                                bArr5[2] = (byte) i10;
                                this.fOffset = i19;
                            } else {
                                invalidByte(3, 3, i10);
                            }
                        }
                        int i21 = ((i9 << 6) & ASTNode.OperatorMASK) | ((i18 << 12) & 61440) | (i10 & 63);
                        i8 = i3 + 1;
                        cArr[i3] = (char) i21;
                        i17 -= 2;
                        i3 = i8;
                    } else if ((b2 & 248) == 240) {
                        int i22 = i16 + 1;
                        if (i22 < i12) {
                            i4 = bArr2[i22] & 255;
                        } else {
                            i4 = this.fInputStream.read();
                            if (i4 == i13) {
                                if (i3 > i) {
                                    this.fBuffer[0] = (byte) i18;
                                    this.fOffset = 1;
                                } else {
                                    expectedByte(2, 4);
                                }
                            }
                            i17++;
                        }
                        if ((i4 & 192) != 128 || ((i4 & 48) == 0 && (b2 & 7) == 0)) {
                            if (i3 > i) {
                                byte[] bArr6 = this.fBuffer;
                                bArr6[0] = (byte) i18;
                                bArr6[1] = (byte) i4;
                                this.fOffset = 2;
                            } else {
                                invalidByte(2, 4, i4);
                            }
                        }
                        int i23 = i16 + 2;
                        if (i23 < i12) {
                            i5 = this.fBuffer[i23] & 255;
                        } else {
                            i5 = this.fInputStream.read();
                            if (i5 == i13) {
                                if (i3 > i) {
                                    byte[] bArr7 = this.fBuffer;
                                    bArr7[0] = (byte) i18;
                                    bArr7[1] = (byte) i4;
                                    i7 = 2;
                                    this.fOffset = i7;
                                } else {
                                    expectedByte(3, 4);
                                }
                            }
                            i17++;
                        }
                        if ((i5 & 192) != 128) {
                            if (i3 > i) {
                                byte[] bArr8 = this.fBuffer;
                                bArr8[0] = (byte) i18;
                                bArr8[1] = (byte) i4;
                                bArr8[2] = (byte) i5;
                                this.fOffset = 3;
                            } else {
                                invalidByte(3, 4, i5);
                            }
                        }
                        i16 += 3;
                        if (i16 < i12) {
                            i6 = this.fBuffer[i16] & 255;
                        } else {
                            i6 = this.fInputStream.read();
                            if (i6 == i13) {
                                if (i3 > i) {
                                    byte[] bArr9 = this.fBuffer;
                                    bArr9[0] = (byte) i18;
                                    bArr9[1] = (byte) i4;
                                    bArr9[2] = (byte) i5;
                                    i7 = 3;
                                    this.fOffset = i7;
                                } else {
                                    expectedByte(4, 4);
                                }
                            }
                            i17++;
                        }
                        if ((i6 & 192) != 128) {
                            if (i3 > i) {
                                byte[] bArr10 = this.fBuffer;
                                bArr10[0] = (byte) i18;
                                bArr10[1] = (byte) i4;
                                bArr10[2] = (byte) i5;
                                bArr10[3] = (byte) i6;
                                this.fOffset = 4;
                            } else {
                                invalidByte(4, 4, i5);
                            }
                        }
                        int i24 = ((i18 << 2) & 28) | ((i4 >> 4) & 3);
                        if (i24 > 16) {
                            invalidSurrogate(i24);
                        }
                        int i25 = i5 & 63;
                        int i26 = (((i24 - 1) << 6) & XKeySym.XK_Amacron) | 55296 | ((i4 & 15) << 2) | (i25 >> 4);
                        int i27 = ((i25 << 6) & XKeySym.XK_Amacron) | 56320 | (i6 & 63);
                        i8 = i3 + 1;
                        cArr[i3] = (char) i26;
                        int i28 = i17 - 2;
                        if (i28 <= length) {
                            i3 += 2;
                            cArr[i8] = (char) i27;
                            i17 = i28;
                        } else {
                            this.fSurrogate = i27;
                            i17 -= 3;
                            i3 = i8;
                        }
                    } else if (i3 > i) {
                        bArr2[0] = (byte) i18;
                        this.fOffset = 1;
                    } else {
                        invalidByte(1, 1, i18);
                    }
                    return i3 - i;
                }
                i16++;
                if (i16 < i12) {
                    i11 = bArr2[i16] & 255;
                } else {
                    i11 = this.fInputStream.read();
                    if (i11 == i13) {
                        if (i3 > i) {
                            this.fBuffer[0] = (byte) i18;
                            this.fOffset = 1;
                            return i3 - i;
                        }
                        expectedByte(2, 2);
                    }
                    i17++;
                }
                if ((i11 & 192) != 128) {
                    if (i3 > i) {
                        byte[] bArr11 = this.fBuffer;
                        bArr11[0] = (byte) i18;
                        bArr11[1] = (byte) i11;
                        this.fOffset = i19;
                        return i3 - i;
                    }
                    invalidByte(2, 2, i11);
                }
                i8 = i3 + 1;
                cArr[i3] = (char) ((i11 & 63) | ((i18 << 6) & 1984));
                i17 += i13;
                i3 = i8;
            }
            i16++;
            i13 = -1;
        }
        return i17;
    }

    @Override // java.io.Reader
    public boolean ready() throws IOException {
        return false;
    }

    @Override // java.io.Reader
    public void reset() throws IOException {
        this.fOffset = 0;
        this.fSurrogate = -1;
    }

    @Override // java.io.Reader
    public long skip(long j) throws IOException {
        int length = this.fBuffer.length;
        char[] cArr = new char[length];
        long j2 = j;
        do {
            int i = read(cArr, 0, ((long) length) < j2 ? length : (int) j2);
            if (i <= 0) {
                break;
            }
            j2 -= (long) i;
        } while (j2 > 0);
        return j - j2;
    }
}
