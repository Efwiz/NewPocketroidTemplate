package org.jetbrains.kotlin.com.fasterxml.aalto.in;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import org.jetbrains.kotlin.com.fasterxml.aalto.impl.LocationImpl;
import org.jetbrains.kotlin.com.fasterxml.aalto.util.DataUtil;
import org.jetbrains.kotlin.com.fasterxml.aalto.util.XmlCharTypes;
import org.jetbrains.kotlin.com.fasterxml.aalto.util.XmlChars;
import org.jetbrains.kotlin.org.codehaus.stax2.XMLStreamLocation2;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ByteBasedScanner extends XmlScanner {
    protected int _inputEnd;
    protected int _inputPtr;
    protected int _tmpChar;

    @Override // org.jetbrains.kotlin.com.fasterxml.aalto.in.XmlScanner
    protected abstract void _closeSource() throws IOException;

    protected abstract int decodeCharForError(byte b) throws XMLStreamException;

    protected ByteBasedScanner(ReaderConfig readerConfig) {
        super(readerConfig);
        this._tmpChar = 0;
        this._pastBytesOrChars = 0L;
        this._rowStartOffset = 0;
    }

    @Override // org.jetbrains.kotlin.com.fasterxml.aalto.in.XmlScanner
    public XMLStreamLocation2 getCurrentLocation() {
        return LocationImpl.fromZeroBased(this._config.getPublicId(), this._config.getSystemId(), this._pastBytesOrChars + ((long) this._inputPtr), this._currRow, this._inputPtr - this._rowStartOffset);
    }

    protected final void markLF(int i) {
        this._rowStartOffset = i;
        this._currRow++;
    }

    protected final void markLF() {
        this._rowStartOffset = this._inputPtr;
        this._currRow++;
    }

    protected final void setStartLocation() {
        this._startRawOffset = this._pastBytesOrChars + ((long) this._inputPtr);
        this._startRow = this._currRow;
        this._startColumn = this._inputPtr - this._rowStartOffset;
    }

    /* JADX WARN: Code duplicated, block: B:103:0x01cb  */
    /* JADX WARN: Code duplicated, block: B:106:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:115:0x01d9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:69:0x010a  */
    /* JADX WARN: Code duplicated, block: B:72:0x0122  */
    /* JADX WARN: Code duplicated, block: B:75:0x012c  */
    /* JADX WARN: Code duplicated, block: B:77:0x013f  */
    /* JADX WARN: Code duplicated, block: B:80:0x014a  */
    /* JADX WARN: Code duplicated, block: B:82:0x015d  */
    /* JADX WARN: Code duplicated, block: B:84:0x0168 A[PHI: r5 r12
      0x0168: PHI (r5v21 int) = (r5v20 int), (r5v25 int) binds: [B:74:0x012a, B:79:0x0148] A[DONT_GENERATE, DONT_INLINE]
      0x0168: PHI (r12v7 int) = (r12v6 int), (r12v10 int) binds: [B:74:0x012a, B:79:0x0148] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:87:0x0173  */
    /* JADX WARN: Code duplicated, block: B:89:0x0179  */
    /* JADX WARN: Code duplicated, block: B:91:0x0195  */
    protected final PName addUTFPName(ByteBasedPNameTable byteBasedPNameTable, XmlCharTypes xmlCharTypes, int i, int[] iArr, int i2, int i3) throws XMLStreamException {
        int i4;
        int i5;
        boolean zIs10NameStartChar;
        int i6;
        boolean zIs10NameChar;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15 = ((i2 << 2) - 4) + i3;
        if (i3 < 4) {
            int i16 = i2 - 1;
            i4 = iArr[i16];
            iArr[i16] = i4 << ((4 - i3) << 3);
        } else {
            i4 = 0;
        }
        int i17 = iArr[0] >>> 24;
        char[] cArrGrowArrayBy = this._nameBuffer;
        int[] iArr2 = xmlCharTypes.NAME_CHARS;
        int i18 = iArr2[i17];
        int i19 = 2;
        if (i18 == 0 || i18 == 1 || i18 == 2) {
            i5 = 1;
            zIs10NameStartChar = false;
            i6 = 0;
        } else {
            if (i18 == 3) {
                zIs10NameStartChar = true;
                i5 = 1;
            } else if (i18 != 4) {
                if ((i17 & 224) == 192) {
                    i13 = i17 & 31;
                    i14 = 1;
                } else if ((i17 & 240) == 224) {
                    i13 = i17 & 15;
                    i14 = 2;
                } else if ((i17 & 248) == 240) {
                    i13 = i17 & 7;
                    i14 = 3;
                } else {
                    reportInvalidInitial(i17);
                    i13 = 1;
                    i14 = 1;
                }
                i5 = 1 + i14;
                if (i5 > i15) {
                    reportEofInName(cArrGrowArrayBy, 0);
                }
                int i20 = iArr[0];
                int i21 = i20 >> 16;
                int i22 = i21 & 255;
                if ((i21 & 192) != 128) {
                    reportInvalidOther(i22);
                }
                int i23 = (i13 << 6) | (i21 & 63);
                if (i14 > 1) {
                    int i24 = i20 >> 8;
                    int i25 = i24 & 255;
                    if ((i24 & 192) != 128) {
                        reportInvalidOther(i25);
                    }
                    i23 = (i23 << 6) | (i24 & 63);
                    if (i14 > 2) {
                        if ((i20 & 192) != 128) {
                            reportInvalidOther(i20 & 255);
                        }
                        i23 = (i23 << 6) | (i20 & 63);
                    }
                }
                i17 = i23;
                zIs10NameStartChar = XmlChars.is10NameStartChar(i17);
                if (i14 > 2) {
                    int i26 = i17 - 65536;
                    cArrGrowArrayBy[0] = (char) ((i26 >> 10) + 55296);
                    i17 = (i26 & 1023) | 56320;
                    i6 = 1;
                }
            } else {
                i5 = 1;
                zIs10NameStartChar = false;
            }
            i6 = 0;
        }
        if (!zIs10NameStartChar) {
            reportInvalidNameChar(i17, 0);
        }
        int i27 = i6 + 1;
        cArrGrowArrayBy[i6] = (char) i17;
        int i28 = -1;
        while (i5 < i15) {
            int i29 = iArr[i5 >> 2] >> ((3 - (i5 & 3)) << 3);
            int i30 = i29 & 255;
            int i31 = i5 + 1;
            int i32 = iArr2[i30];
            if (i32 != 0) {
                if (i32 != 1) {
                    if (i32 != i19 && i32 != 3) {
                        if (i32 != 4) {
                            if ((i29 & 224) == 192) {
                                i7 = i29 & 31;
                                i8 = 1;
                            } else {
                                if ((i29 & 240) == 224) {
                                    i7 = i29 & 15;
                                    i8 = i19;
                                } else if ((i29 & 248) == 240) {
                                    i7 = i29 & 7;
                                    i8 = 3;
                                } else {
                                    reportInvalidInitial(i30);
                                    i7 = 1;
                                    i8 = 1;
                                }
                                if (i31 + i8 > i15) {
                                    reportEofInName(cArrGrowArrayBy, i27);
                                }
                                i9 = iArr[i31 >> 2] >> ((3 - (i31 & 3)) << 3);
                                i31 = i5 + 2;
                                if ((i9 & 192) != 128) {
                                    reportInvalidOther(i9);
                                }
                                i10 = (i7 << 6) | (i9 & 63);
                                if (i8 > 1) {
                                    i11 = iArr[i31 >> 2] >> ((3 - (i31 & 3)) << 3);
                                    i31 = i5 + 3;
                                    if ((i11 & 192) != 128) {
                                        reportInvalidOther(i11);
                                    }
                                    i10 = (i10 << 6) | (i11 & 63);
                                    if (i8 > 2) {
                                        i12 = iArr[i31 >> 2] >> ((3 - (i31 & 3)) << 3);
                                        i31 = i5 + 4;
                                        if ((i12 & 192) != 128) {
                                            reportInvalidOther(i12 & 255);
                                        }
                                        i10 = (i10 << 6) | (i12 & 63);
                                    }
                                }
                                i30 = i10;
                                zIs10NameChar = XmlChars.is10NameChar(i30);
                                if (i8 > 2) {
                                    int i33 = i30 - 65536;
                                    if (i27 >= cArrGrowArrayBy.length) {
                                        cArrGrowArrayBy = DataUtil.growArrayBy(cArrGrowArrayBy, cArrGrowArrayBy.length);
                                        this._nameBuffer = cArrGrowArrayBy;
                                    }
                                    cArrGrowArrayBy[i27] = (char) ((i33 >> 10) + 55296);
                                    i30 = (i33 & 1023) | 56320;
                                    i27++;
                                }
                            }
                            if (i31 + i8 > i15) {
                                reportEofInName(cArrGrowArrayBy, i27);
                            }
                            i9 = iArr[i31 >> 2] >> ((3 - (i31 & 3)) << 3);
                            i31 = i5 + 2;
                            if ((i9 & 192) != 128) {
                                reportInvalidOther(i9);
                            }
                            i10 = (i7 << 6) | (i9 & 63);
                            if (i8 > 1) {
                                i11 = iArr[i31 >> 2] >> ((3 - (i31 & 3)) << 3);
                                i31 = i5 + 3;
                                if ((i11 & 192) != 128) {
                                    reportInvalidOther(i11);
                                }
                                i10 = (i10 << 6) | (i11 & 63);
                                if (i8 > 2) {
                                    i12 = iArr[i31 >> 2] >> ((3 - (i31 & 3)) << 3);
                                    i31 = i5 + 4;
                                    if ((i12 & 192) != 128) {
                                        reportInvalidOther(i12 & 255);
                                    }
                                    i10 = (i10 << 6) | (i12 & 63);
                                }
                            }
                            i30 = i10;
                            zIs10NameChar = XmlChars.is10NameChar(i30);
                            if (i8 > 2) {
                                int i34 = i30 - 65536;
                                if (i27 >= cArrGrowArrayBy.length) {
                                    cArrGrowArrayBy = DataUtil.growArrayBy(cArrGrowArrayBy, cArrGrowArrayBy.length);
                                    this._nameBuffer = cArrGrowArrayBy;
                                }
                                cArrGrowArrayBy[i27] = (char) ((i34 >> 10) + 55296);
                                i30 = (i34 & 1023) | 56320;
                                i27++;
                            }
                        }
                    }
                    if (!zIs10NameChar) {
                        reportInvalidNameChar(i30, i27);
                    }
                    if (i27 >= cArrGrowArrayBy.length) {
                        char[] cArrGrowArrayBy2 = DataUtil.growArrayBy(cArrGrowArrayBy, cArrGrowArrayBy.length);
                        this._nameBuffer = cArrGrowArrayBy2;
                        cArrGrowArrayBy = cArrGrowArrayBy2;
                    }
                    cArrGrowArrayBy[i27] = (char) i30;
                    i27++;
                    i5 = i31;
                    i19 = 2;
                } else {
                    if (i28 >= 0) {
                        reportMultipleColonsInName();
                    }
                    i28 = i27;
                }
                zIs10NameChar = true;
                if (!zIs10NameChar) {
                    reportInvalidNameChar(i30, i27);
                }
                if (i27 >= cArrGrowArrayBy.length) {
                    char[] cArrGrowArrayBy3 = DataUtil.growArrayBy(cArrGrowArrayBy, cArrGrowArrayBy.length);
                    this._nameBuffer = cArrGrowArrayBy3;
                    cArrGrowArrayBy = cArrGrowArrayBy3;
                }
                cArrGrowArrayBy[i27] = (char) i30;
                i27++;
                i5 = i31;
                i19 = 2;
            }
            zIs10NameChar = false;
            if (!zIs10NameChar) {
                reportInvalidNameChar(i30, i27);
            }
            if (i27 >= cArrGrowArrayBy.length) {
                char[] cArrGrowArrayBy4 = DataUtil.growArrayBy(cArrGrowArrayBy, cArrGrowArrayBy.length);
                this._nameBuffer = cArrGrowArrayBy4;
                cArrGrowArrayBy = cArrGrowArrayBy4;
            }
            cArrGrowArrayBy[i27] = (char) i30;
            i27++;
            i5 = i31;
            i19 = 2;
        }
        String str = new String(cArrGrowArrayBy, 0, i27);
        if (i3 < 4) {
            iArr[i2 - 1] = i4;
        }
        return byteBasedPNameTable.addSymbol(i, str, i28, iArr, i2);
    }

    protected void reportInvalidInitial(int i) throws XMLStreamException {
        reportInputProblem("Invalid UTF-8 start byte 0x" + Integer.toHexString(i));
    }

    protected void reportInvalidOther(int i) throws XMLStreamException {
        reportInputProblem("Invalid UTF-8 middle byte 0x" + Integer.toHexString(i));
    }
}
