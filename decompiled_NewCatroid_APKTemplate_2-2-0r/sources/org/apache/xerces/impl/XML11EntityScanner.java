package org.apache.xerces.impl;

import java.io.IOException;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLString;

/* JADX INFO: loaded from: classes13.dex */
public class XML11EntityScanner extends XMLEntityScanner {
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public int peekChar() throws IOException {
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (!this.fCurrentEntity.isExternal()) {
            return c;
        }
        if (c == '\r' || c == 133 || c == 8232) {
            return 10;
        }
        return c;
    }

    @Override // org.apache.xerces.impl.XMLEntityScanner
    public int scanChar() throws IOException {
        boolean zIsExternal;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        char[] cArr = this.fCurrentEntity.ch;
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        int i = scannedEntity.position;
        scannedEntity.position = i + 1;
        char c = cArr[i];
        if (c != '\n') {
            if ((c == '\r' || c == 133 || c == 8232) && (zIsExternal = this.fCurrentEntity.isExternal())) {
            }
            this.fCurrentEntity.columnNumber++;
            return c;
        }
        zIsExternal = false;
        this.fCurrentEntity.lineNumber++;
        this.fCurrentEntity.columnNumber = 1;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            this.fCurrentEntity.ch[0] = c;
            load(1, false);
        }
        if (c == '\r' && zIsExternal && this.fCurrentEntity.position < this.fCurrentEntity.count) {
            char[] cArr2 = this.fCurrentEntity.ch;
            XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
            int i2 = scannedEntity2.position;
            scannedEntity2.position = i2 + 1;
            char c2 = cArr2[i2];
            if (c2 != '\n' && c2 != 133) {
                this.fCurrentEntity.position--;
            }
        }
        c = '\n';
        this.fCurrentEntity.columnNumber++;
        return c;
    }

    /* JADX WARN: Code duplicated, block: B:89:0x0116 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:? A[LOOP:2: B:15:0x005b->B:90:?, LOOP_END, SYNTHETIC] */
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public int scanContent(XMLString xMLString) throws IOException {
        int i = 0;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        } else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
            this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[this.fCurrentEntity.count - 1];
            load(1, false);
            this.fCurrentEntity.position = 0;
            this.fCurrentEntity.startPosition = 0;
        }
        int i2 = this.fCurrentEntity.position;
        char c = this.fCurrentEntity.ch[i2];
        boolean zIsExternal = this.fCurrentEntity.isExternal();
        if (c == '\n' || ((c == '\r' || c == 133 || c == 8232) && zIsExternal)) {
            int i3 = 0;
            while (true) {
                char[] cArr = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                int i4 = scannedEntity.position;
                scannedEntity.position = i4 + 1;
                char c2 = cArr[i4];
                if (c2 == '\r' && zIsExternal) {
                    int i5 = i3 + 1;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i5;
                        this.fCurrentEntity.startPosition = i5;
                        if (load(i5, false)) {
                            i3 = i5;
                        } else {
                            i2 = 0;
                        }
                    }
                    char c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                    if (c3 == '\n' || c3 == 133) {
                        this.fCurrentEntity.position++;
                        i2++;
                    } else {
                        i5 = i3 + 2;
                    }
                    i3 = i5;
                    if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                        i = i2;
                    }
                } else if (c2 == '\n' || ((c2 == 133 || c2 == 8232) && zIsExternal)) {
                    i3++;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i3;
                        this.fCurrentEntity.startPosition = i3;
                        if (!load(i3, false)) {
                            i2 = 0;
                        }
                    }
                    if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                        i = i2;
                    }
                } else {
                    this.fCurrentEntity.position--;
                    i = i2;
                }
                for (int i6 = i; i6 < this.fCurrentEntity.position; i6++) {
                    this.fCurrentEntity.ch[i6] = '\n';
                }
                int i7 = this.fCurrentEntity.position - i;
                if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                    xMLString.setValues(this.fCurrentEntity.ch, i, i7);
                    return -1;
                }
                i2 = i;
                i = i3;
            }
        }
        if (!zIsExternal) {
            while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
                char[] cArr2 = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
                int i8 = scannedEntity2.position;
                scannedEntity2.position = i8 + 1;
                if (!XML11Char.isXML11InternalEntityContent(cArr2[i8])) {
                    this.fCurrentEntity.position--;
                    break;
                }
            }
        } else {
            while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
                char[] cArr3 = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity3 = this.fCurrentEntity;
                int i9 = scannedEntity3.position;
                scannedEntity3.position = i9 + 1;
                char c4 = cArr3[i9];
                if (!XML11Char.isXML11Content(c4) || c4 == 133 || c4 == 8232) {
                    this.fCurrentEntity.position--;
                    break;
                }
            }
        }
        int i10 = this.fCurrentEntity.position - i2;
        this.fCurrentEntity.columnNumber += i10 - i;
        xMLString.setValues(this.fCurrentEntity.ch, i2, i10);
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            return -1;
        }
        char c5 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if ((c5 == '\r' || c5 == 133 || c5 == 8232) && zIsExternal) {
            return 10;
        }
        return c5;
    }

    /* JADX WARN: Code duplicated, block: B:130:0x0310 A[LOOP:6: B:24:0x00cf->B:130:0x0310, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:167:0x019a A[EDGE_INSN: B:167:0x019a->B:58:0x019a BREAK  A[LOOP:6: B:24:0x00cf->B:130:0x0310], SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v16 */
    /* JADX WARN: Type inference failed for: r11v17, types: [int] */
    /* JADX WARN: Type inference failed for: r11v20, types: [int] */
    /* JADX WARN: Type inference failed for: r11v51 */
    /* JADX WARN: Type inference failed for: r16v0, types: [org.apache.xerces.impl.XML11EntityScanner] */
    /* JADX WARN: Type inference failed for: r18v0, types: [org.apache.xerces.util.XMLStringBuffer] */
    /* JADX WARN: Type inference failed for: r1v13, types: [int] */
    /* JADX WARN: Type inference failed for: r1v18, types: [int] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17, types: [int] */
    /* JADX WARN: Type inference failed for: r8v18, types: [int] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v25 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27, types: [int] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v34 */
    /* JADX WARN: Type inference failed for: r8v35 */
    /* JADX WARN: Type inference failed for: r8v36 */
    /* JADX WARN: Type inference failed for: r8v37 */
    /* JADX WARN: Type inference failed for: r8v38 */
    /* JADX WARN: Type inference failed for: r8v39 */
    /* JADX WARN: Type inference failed for: r8v40 */
    /* JADX WARN: Type inference failed for: r8v41 */
    /* JADX WARN: Type inference failed for: r8v42 */
    /* JADX WARN: Type inference failed for: r8v43 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11, types: [int] */
    /* JADX WARN: Type inference failed for: r9v12, types: [int] */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18, types: [int] */
    /* JADX WARN: Type inference failed for: r9v19, types: [int] */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v29 */
    /* JADX WARN: Type inference failed for: r9v30 */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v32 */
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public boolean scanData(String str, XMLStringBuffer xMLStringBuffer) throws IOException {
        ?? r8;
        ?? r9;
        ?? r10;
        ?? r11;
        int length = str.length();
        ?? r4 = 0;
        char cCharAt = str.charAt(0);
        boolean zIsExternal = this.fCurrentEntity.isExternal();
        boolean z = false;
        while (true) {
            if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                load(r4, true);
            }
            ?? r12 = r4;
            while (this.fCurrentEntity.position >= this.fCurrentEntity.count - length && r12 == 0) {
                System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.ch, r4, this.fCurrentEntity.count - this.fCurrentEntity.position);
                boolean zLoad = load(this.fCurrentEntity.count - this.fCurrentEntity.position, r4);
                this.fCurrentEntity.position = r4;
                this.fCurrentEntity.startPosition = r4;
                r12 = zLoad;
            }
            if (this.fCurrentEntity.position >= this.fCurrentEntity.count - length) {
                xMLStringBuffer.append(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.count - this.fCurrentEntity.position);
                this.fCurrentEntity.columnNumber += this.fCurrentEntity.count;
                this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                this.fCurrentEntity.position = this.fCurrentEntity.count;
                this.fCurrentEntity.startPosition = this.fCurrentEntity.count;
                load(r4, true);
                return r4;
            }
            int i = this.fCurrentEntity.position;
            char c = this.fCurrentEntity.ch[i];
            if (c == '\n' || ((c == '\r' || c == 133 || c == 8232) && zIsExternal)) {
                ?? r13 = r4;
                ?? r14 = i;
                while (true) {
                    char[] cArr = this.fCurrentEntity.ch;
                    XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                    int i2 = scannedEntity.position;
                    scannedEntity.position = i2 + 1;
                    char c2 = cArr[i2];
                    if (c2 == '\r' && zIsExternal) {
                        int i3 = r13 + 1;
                        this.fCurrentEntity.lineNumber++;
                        this.fCurrentEntity.columnNumber = 1;
                        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                            this.fCurrentEntity.position = i3;
                            this.fCurrentEntity.startPosition = i3;
                            if (load(i3, r4)) {
                                r8 = r14;
                                r14 = r4;
                                r13 = i3;
                                break;
                            }
                            r8 = r14;
                            r8 = r4;
                        }
                        r8 = r14;
                        char c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                        if (c3 == '\n' || c3 == 133) {
                            this.fCurrentEntity.position++;
                            r9 = r8 + 1;
                        } else {
                            i3 = r13 + 2;
                            r9 = r8;
                        }
                        r13 = i3;
                        r14 = r9;
                        if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                            break;
                            break;
                        }
                        r14 = r14;
                        r13 = r13;
                    } else {
                        if (c2 != '\n' && ((c2 != 133 && c2 != 8232) || !zIsExternal)) {
                            this.fCurrentEntity.position--;
                            break;
                        }
                        r13++;
                        this.fCurrentEntity.lineNumber++;
                        this.fCurrentEntity.columnNumber = 1;
                        r14 = r14;
                        r13 = r13;
                        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                            this.fCurrentEntity.position = r13;
                            this.fCurrentEntity.startPosition = r13;
                            this.fCurrentEntity.count = r13;
                            if (load(r13, r4)) {
                                r14 = r4;
                                break;
                            }
                            r14 = r4;
                            r13 = r13;
                            if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                                break;
                            }
                            r14 = r14;
                            r13 = r13;
                        } else {
                            if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                                break;
                                break;
                            }
                            r14 = r14;
                            r13 = r13;
                        }
                    }
                }
                for (?? r15 = r14; r15 < this.fCurrentEntity.position; r15++) {
                    this.fCurrentEntity.ch[r15] = '\n';
                }
                ?? r16 = this.fCurrentEntity.position - r14;
                r11 = r14;
                r10 = r13;
                if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                    xMLStringBuffer.append(this.fCurrentEntity.ch, r14, r16);
                    return true;
                }
            } else {
                r10 = r4;
                r11 = i;
            }
            if (zIsExternal) {
                while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
                    char[] cArr2 = this.fCurrentEntity.ch;
                    XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
                    int i4 = scannedEntity2.position;
                    scannedEntity2.position = i4 + 1;
                    char c4 = cArr2[i4];
                    if (c4 == cCharAt) {
                        int i5 = this.fCurrentEntity.position - 1;
                        for (int i6 = 1; i6 < length; i6++) {
                            if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                                this.fCurrentEntity.position -= i6;
                                break;
                            }
                            char[] cArr3 = this.fCurrentEntity.ch;
                            XMLEntityManager.ScannedEntity scannedEntity3 = this.fCurrentEntity;
                            int i7 = scannedEntity3.position;
                            scannedEntity3.position = i7 + 1;
                            if (str.charAt(i6) != cArr3[i7]) {
                                this.fCurrentEntity.position--;
                                break;
                            }
                        }
                        if (this.fCurrentEntity.position == i5 + length) {
                            z = true;
                            break;
                        }
                    } else {
                        if (c4 == '\n' || c4 == '\r' || c4 == 133 || c4 == 8232) {
                            this.fCurrentEntity.position--;
                            break;
                        }
                        if (!XML11Char.isXML11ValidLiteral(c4)) {
                            this.fCurrentEntity.position--;
                            ?? r1 = this.fCurrentEntity.position - r11;
                            this.fCurrentEntity.columnNumber += r1 - r10;
                            xMLStringBuffer.append(this.fCurrentEntity.ch, r11, r1);
                            return true;
                        }
                    }
                }
            } else {
                while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
                    char[] cArr4 = this.fCurrentEntity.ch;
                    XMLEntityManager.ScannedEntity scannedEntity4 = this.fCurrentEntity;
                    int i8 = scannedEntity4.position;
                    scannedEntity4.position = i8 + 1;
                    char c5 = cArr4[i8];
                    if (c5 == cCharAt) {
                        int i9 = this.fCurrentEntity.position - 1;
                        for (int i10 = 1; i10 < length; i10++) {
                            if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                                this.fCurrentEntity.position -= i10;
                                break;
                            }
                            char[] cArr5 = this.fCurrentEntity.ch;
                            XMLEntityManager.ScannedEntity scannedEntity5 = this.fCurrentEntity;
                            int i11 = scannedEntity5.position;
                            scannedEntity5.position = i11 + 1;
                            if (str.charAt(i10) != cArr5[i11]) {
                                this.fCurrentEntity.position--;
                                break;
                            }
                        }
                        if (this.fCurrentEntity.position == i9 + length) {
                            z = true;
                            break;
                        }
                    } else {
                        if (c5 == '\n') {
                            this.fCurrentEntity.position--;
                            break;
                        }
                        if (!XML11Char.isXML11Valid(c5)) {
                            this.fCurrentEntity.position--;
                            ?? r2 = this.fCurrentEntity.position - r11;
                            this.fCurrentEntity.columnNumber += r2 - r10;
                            xMLStringBuffer.append(this.fCurrentEntity.ch, r11, r2);
                            return true;
                        }
                    }
                }
            }
            int i12 = this.fCurrentEntity.position - r11;
            this.fCurrentEntity.columnNumber += i12 - r10;
            if (z) {
                i12 -= length;
            }
            xMLStringBuffer.append(this.fCurrentEntity.ch, r11, i12);
            if (z) {
                return !z;
            }
            r4 = 0;
        }
    }

    /* JADX WARN: Code duplicated, block: B:108:0x011f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:109:? A[LOOP:2: B:17:0x0064->B:109:?, LOOP_END, SYNTHETIC] */
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public int scanLiteral(int i, XMLString xMLString) throws IOException {
        int i2;
        int i3 = 0;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        } else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
            this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[this.fCurrentEntity.count - 1];
            load(1, false);
            this.fCurrentEntity.startPosition = 0;
            this.fCurrentEntity.position = 0;
        }
        int i4 = this.fCurrentEntity.position;
        char c = this.fCurrentEntity.ch[i4];
        boolean zIsExternal = this.fCurrentEntity.isExternal();
        if (c == '\n' || ((c == '\r' || c == 133 || c == 8232) && zIsExternal)) {
            int i5 = 0;
            while (true) {
                char[] cArr = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                int i6 = scannedEntity.position;
                scannedEntity.position = i6 + 1;
                char c2 = cArr[i6];
                if (c2 == '\r' && zIsExternal) {
                    int i7 = i5 + 1;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i7;
                        this.fCurrentEntity.startPosition = i7;
                        if (load(i7, false)) {
                            i5 = i7;
                        } else {
                            i4 = 0;
                        }
                    }
                    char c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                    if (c3 == '\n' || c3 == 133) {
                        this.fCurrentEntity.position++;
                        i4++;
                    } else {
                        i7 = i5 + 2;
                    }
                    i5 = i7;
                    if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                        i3 = i4;
                    }
                } else if (c2 == '\n' || ((c2 == 133 || c2 == 8232) && zIsExternal)) {
                    i5++;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i5;
                        this.fCurrentEntity.startPosition = i5;
                        if (!load(i5, false)) {
                            i4 = 0;
                        }
                    }
                    if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                        i3 = i4;
                    }
                } else {
                    this.fCurrentEntity.position--;
                    i3 = i4;
                }
                for (int i8 = i3; i8 < this.fCurrentEntity.position; i8++) {
                    this.fCurrentEntity.ch[i8] = '\n';
                }
                int i9 = this.fCurrentEntity.position - i3;
                if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                    xMLString.setValues(this.fCurrentEntity.ch, i3, i9);
                    return -1;
                }
                i2 = -1;
                i4 = i3;
                i3 = i5;
            }
        } else {
            i2 = -1;
        }
        if (zIsExternal) {
            while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
                char[] cArr2 = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
                int i10 = scannedEntity2.position;
                scannedEntity2.position = i10 + 1;
                char c4 = cArr2[i10];
                if (c4 == i || c4 == '%' || !XML11Char.isXML11Content(c4) || c4 == 133 || c4 == 8232) {
                    this.fCurrentEntity.position--;
                    break;
                }
            }
        } else {
            while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
                char[] cArr3 = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity3 = this.fCurrentEntity;
                int i11 = scannedEntity3.position;
                scannedEntity3.position = i11 + 1;
                char c5 = cArr3[i11];
                if ((c5 == i && !this.fCurrentEntity.literal) || c5 == '%' || (!XML11Char.isXML11InternalEntityContent(c5) && c5 != '\r')) {
                    this.fCurrentEntity.position--;
                    break;
                }
            }
        }
        int i12 = this.fCurrentEntity.position - i4;
        this.fCurrentEntity.columnNumber += i12 - i3;
        xMLString.setValues(this.fCurrentEntity.ch, i4, i12);
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            return i2;
        }
        char c6 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        return (c6 == i && this.fCurrentEntity.literal) ? i2 : c6;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:41:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:47:0x0111  */
    /* JADX WARN: Code duplicated, block: B:49:0x011e  */
    /* JADX WARN: Code duplicated, block: B:51:0x012a  */
    /* JADX WARN: Code duplicated, block: B:52:0x012e  */
    /* JADX WARN: Code duplicated, block: B:56:0x014e  */
    /* JADX WARN: Code duplicated, block: B:57:0x014f A[PHI: r0
      0x014f: PHI (r0v26 int) = (r0v19 int), (r0v31 int) binds: [B:48:0x011c, B:56:0x014e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:59:0x015f  */
    /* JADX WARN: Code duplicated, block: B:75:0x01af  */
    /* JADX WARN: Code duplicated, block: B:79:0x010b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x00e8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x00db A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:82:0x01a0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:83:0x013f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x0199 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x00cb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x0107 -> B:13:0x004b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x0196 -> B:13:0x004b). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:57:0x014f
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public java.lang.String scanNCName() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 450
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.impl.XML11EntityScanner.scanNCName():java.lang.String");
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:41:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:47:0x0111  */
    /* JADX WARN: Code duplicated, block: B:49:0x011e  */
    /* JADX WARN: Code duplicated, block: B:51:0x012a  */
    /* JADX WARN: Code duplicated, block: B:52:0x012e  */
    /* JADX WARN: Code duplicated, block: B:56:0x014e  */
    /* JADX WARN: Code duplicated, block: B:57:0x014f A[PHI: r0
      0x014f: PHI (r0v26 int) = (r0v19 int), (r0v31 int) binds: [B:48:0x011c, B:56:0x014e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:59:0x015f  */
    /* JADX WARN: Code duplicated, block: B:75:0x01af  */
    /* JADX WARN: Code duplicated, block: B:79:0x010b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x00e8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x00db A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:82:0x01a0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:83:0x013f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x0199 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x00cb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x0107 -> B:13:0x004b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x0196 -> B:13:0x004b). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:57:0x014f
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public java.lang.String scanName() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 450
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.impl.XML11EntityScanner.scanName():java.lang.String");
    }

    @Override // org.apache.xerces.impl.XMLEntityScanner
    public String scanNmtoken() throws IOException {
        int i = 0;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        int i2 = this.fCurrentEntity.position;
        while (true) {
            char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
            if (!XML11Char.isXML11Name(c)) {
                if (XML11Char.isXML11NameHighSurrogate(c)) {
                    XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                    int i3 = scannedEntity.position + 1;
                    scannedEntity.position = i3;
                    if (i3 == this.fCurrentEntity.count) {
                        int i4 = this.fCurrentEntity.position - i2;
                        if (i4 == this.fCurrentEntity.ch.length) {
                            resizeBuffer(i2, i4);
                        } else {
                            System.arraycopy(this.fCurrentEntity.ch, i2, this.fCurrentEntity.ch, 0, i4);
                        }
                        if (load(i4, false)) {
                            this.fCurrentEntity.startPosition--;
                            this.fCurrentEntity.position--;
                            break;
                        }
                        i2 = 0;
                    }
                    char c2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                    if (XMLChar.isLowSurrogate(c2) && XML11Char.isXML11Name(XMLChar.supplemental(c, c2))) {
                        XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
                        int i5 = scannedEntity2.position + 1;
                        scannedEntity2.position = i5;
                        if (i5 != this.fCurrentEntity.count) {
                            continue;
                        } else {
                            int i6 = this.fCurrentEntity.position - i2;
                            if (i6 == this.fCurrentEntity.ch.length) {
                                resizeBuffer(i2, i6);
                            } else {
                                System.arraycopy(this.fCurrentEntity.ch, i2, this.fCurrentEntity.ch, 0, i6);
                            }
                            if (load(i6, false)) {
                                break;
                            }
                            i2 = 0;
                        }
                    } else {
                        this.fCurrentEntity.position--;
                    }
                }
                i = i2;
                break;
            }
            XMLEntityManager.ScannedEntity scannedEntity3 = this.fCurrentEntity;
            int i7 = scannedEntity3.position + 1;
            scannedEntity3.position = i7;
            if (i7 != this.fCurrentEntity.count) {
                continue;
            } else {
                int i8 = this.fCurrentEntity.position - i2;
                if (i8 == this.fCurrentEntity.ch.length) {
                    resizeBuffer(i2, i8);
                } else {
                    System.arraycopy(this.fCurrentEntity.ch, i2, this.fCurrentEntity.ch, 0, i8);
                }
                if (load(i8, false)) {
                    break;
                }
                i2 = 0;
            }
        }
        int i9 = this.fCurrentEntity.position - i;
        this.fCurrentEntity.columnNumber += i9;
        if (i9 > 0) {
            return this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, i9);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0123 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:103:0x01c3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:104:0x00f9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:105:0x015a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:106:0x01ba A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:109:0x011d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:110:0x01c3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:111:0x00e0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:0x00d0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:32:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:34:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:40:0x00e4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:42:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:47:0x0105  */
    /* JADX WARN: Code duplicated, block: B:48:0x0109  */
    /* JADX WARN: Code duplicated, block: B:50:0x0116  */
    /* JADX WARN: Code duplicated, block: B:57:0x0129  */
    /* JADX WARN: Code duplicated, block: B:59:0x0136  */
    /* JADX WARN: Code duplicated, block: B:61:0x0142  */
    /* JADX WARN: Code duplicated, block: B:62:0x0146  */
    /* JADX WARN: Code duplicated, block: B:64:0x0153  */
    /* JADX WARN: Code duplicated, block: B:68:0x016b  */
    /* JADX WARN: Code duplicated, block: B:69:0x016c A[PHI: r0 r4
      0x016c: PHI (r0v31 int) = (r0v22 int), (r0v37 int) binds: [B:58:0x0134, B:68:0x016b] A[DONT_GENERATE, DONT_INLINE]
      0x016c: PHI (r4v20 int) = (r4v9 int), (r4v23 int) binds: [B:58:0x0134, B:68:0x016b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:71:0x017c  */
    /* JADX WARN: Code duplicated, block: B:89:0x01d2  */
    /* JADX WARN: Code duplicated, block: B:91:0x01de  */
    /* JADX WARN: Code duplicated, block: B:93:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:98:0x021b  */
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public boolean scanQName(QName qName) throws IOException {
        char c;
        int i;
        String strAddSymbol;
        int i2;
        char c2;
        int i3;
        int i4;
        boolean z;
        int i5;
        String strAddSymbol2;
        String strAddSymbol3;
        String strAddSymbol4;
        int i6;
        int i7;
        char c3;
        int i8;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        int i9 = this.fCurrentEntity.position;
        char c4 = this.fCurrentEntity.ch[i9];
        if (XML11Char.isXML11NCNameStart(c4)) {
            XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
            int i10 = scannedEntity.position + 1;
            scannedEntity.position = i10;
            if (i10 == this.fCurrentEntity.count) {
                this.fCurrentEntity.ch[0] = c4;
                if (load(1, false)) {
                    this.fCurrentEntity.columnNumber++;
                    strAddSymbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
                    qName.setValues(null, strAddSymbol, strAddSymbol, null);
                    return true;
                }
                i9 = 0;
            }
            i2 = -1;
            while (true) {
                c2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                if (!XML11Char.isXML11Name(c2)) {
                    if (!XML11Char.isXML11NameHighSurrogate(c2)) {
                        z = false;
                        break;
                    }
                    XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
                    i7 = scannedEntity2.position + 1;
                    scannedEntity2.position = i7;
                    if (i7 == this.fCurrentEntity.count) {
                        i8 = this.fCurrentEntity.position - i9;
                        if (i8 == this.fCurrentEntity.ch.length) {
                            resizeBuffer(i9, i8);
                        } else {
                            System.arraycopy(this.fCurrentEntity.ch, i9, this.fCurrentEntity.ch, 0, i8);
                        }
                        if (i2 != -1) {
                            i2 -= i9;
                        }
                        if (!load(i8, false)) {
                            i9 = 0;
                            c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                            if (XMLChar.isLowSurrogate(c3)) {
                            }
                            this.fCurrentEntity.position--;
                            z = true;
                            break;
                        }
                        this.fCurrentEntity.startPosition--;
                        this.fCurrentEntity.position--;
                        z = true;
                        i9 = 0;
                        break;
                    }
                    c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                    if (XMLChar.isLowSurrogate(c3) || !XML11Char.isXML11Name(XMLChar.supplemental(c2, c3))) {
                        this.fCurrentEntity.position--;
                        z = true;
                        break;
                    }
                    XMLEntityManager.ScannedEntity scannedEntity3 = this.fCurrentEntity;
                    int i11 = scannedEntity3.position + 1;
                    scannedEntity3.position = i11;
                    if (i11 != this.fCurrentEntity.count) {
                        continue;
                    } else {
                        int i12 = this.fCurrentEntity.position - i9;
                        if (i12 == this.fCurrentEntity.ch.length) {
                            resizeBuffer(i9, i12);
                        } else {
                            System.arraycopy(this.fCurrentEntity.ch, i9, this.fCurrentEntity.ch, 0, i12);
                        }
                        if (i2 != -1) {
                            i2 -= i9;
                        }
                        if (load(i12, false)) {
                            i9 = 0;
                            z = false;
                            break;
                        }
                        i9 = 0;
                    }
                } else {
                    if (c2 == ':') {
                        if (i2 != -1) {
                            z = false;
                            break;
                        }
                        i2 = this.fCurrentEntity.position;
                    }
                    XMLEntityManager.ScannedEntity scannedEntity4 = this.fCurrentEntity;
                    i3 = scannedEntity4.position + 1;
                    scannedEntity4.position = i3;
                    if (i3 == this.fCurrentEntity.count) {
                        continue;
                    } else {
                        i4 = this.fCurrentEntity.position - i9;
                        if (i4 == this.fCurrentEntity.ch.length) {
                            resizeBuffer(i9, i4);
                        } else {
                            System.arraycopy(this.fCurrentEntity.ch, i9, this.fCurrentEntity.ch, 0, i4);
                        }
                        if (i2 != -1) {
                            i2 -= i9;
                        }
                        if (load(i4, false)) {
                            i9 = 0;
                            z = false;
                            break;
                        }
                        i9 = 0;
                    }
                }
            }
            i5 = this.fCurrentEntity.position - i9;
            this.fCurrentEntity.columnNumber += i5;
            if (i5 > 0) {
                strAddSymbol2 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i9, i5);
                if (i2 != -1) {
                    int i13 = i2 - i9;
                    strAddSymbol4 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i9, i13);
                    int i14 = (i5 - i13) - 1;
                    i6 = i2 + 1;
                    if (!XML11Char.isXML11NCNameStart(this.fCurrentEntity.ch[i6]) && (!XML11Char.isXML11NameHighSurrogate(this.fCurrentEntity.ch[i6]) || z)) {
                        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IllegalQName", null, (short) 2);
                    }
                    strAddSymbol3 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i6, i14);
                } else {
                    strAddSymbol3 = strAddSymbol2;
                    strAddSymbol4 = null;
                }
                qName.setValues(strAddSymbol4, strAddSymbol3, strAddSymbol2, null);
                return true;
            }
        } else if (XML11Char.isXML11NameHighSurrogate(c4)) {
            XMLEntityManager.ScannedEntity scannedEntity5 = this.fCurrentEntity;
            int i15 = scannedEntity5.position + 1;
            scannedEntity5.position = i15;
            if (i15 != this.fCurrentEntity.count) {
                c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                if (XMLChar.isLowSurrogate(c)) {
                    XMLEntityManager.ScannedEntity scannedEntity6 = this.fCurrentEntity;
                    i = scannedEntity6.position + 1;
                    scannedEntity6.position = i;
                    if (i == this.fCurrentEntity.count) {
                        this.fCurrentEntity.ch[0] = c4;
                        this.fCurrentEntity.ch[1] = c;
                        if (load(2, false)) {
                            this.fCurrentEntity.columnNumber += 2;
                            strAddSymbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 2);
                            qName.setValues(null, strAddSymbol, strAddSymbol, null);
                            return true;
                        }
                        i9 = 0;
                    }
                    i2 = -1;
                    while (true) {
                        c2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                        if (!XML11Char.isXML11Name(c2)) {
                            if (!XML11Char.isXML11NameHighSurrogate(c2)) {
                                z = false;
                                break;
                            }
                            XMLEntityManager.ScannedEntity scannedEntity7 = this.fCurrentEntity;
                            i7 = scannedEntity7.position + 1;
                            scannedEntity7.position = i7;
                            if (i7 == this.fCurrentEntity.count) {
                                i8 = this.fCurrentEntity.position - i9;
                                if (i8 == this.fCurrentEntity.ch.length) {
                                    resizeBuffer(i9, i8);
                                } else {
                                    System.arraycopy(this.fCurrentEntity.ch, i9, this.fCurrentEntity.ch, 0, i8);
                                }
                                if (i2 != -1) {
                                    i2 -= i9;
                                }
                                if (!load(i8, false)) {
                                    i9 = 0;
                                    c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                                    if (XMLChar.isLowSurrogate(c3)) {
                                    }
                                    this.fCurrentEntity.position--;
                                    z = true;
                                    break;
                                }
                                this.fCurrentEntity.startPosition--;
                                this.fCurrentEntity.position--;
                                z = true;
                                i9 = 0;
                                break;
                            }
                            c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                            if (XMLChar.isLowSurrogate(c3)) {
                            }
                            this.fCurrentEntity.position--;
                            z = true;
                            break;
                        }
                        if (c2 == ':') {
                            if (i2 != -1) {
                                z = false;
                                break;
                            }
                            i2 = this.fCurrentEntity.position;
                        }
                        XMLEntityManager.ScannedEntity scannedEntity8 = this.fCurrentEntity;
                        i3 = scannedEntity8.position + 1;
                        scannedEntity8.position = i3;
                        if (i3 == this.fCurrentEntity.count) {
                            i4 = this.fCurrentEntity.position - i9;
                            if (i4 == this.fCurrentEntity.ch.length) {
                                resizeBuffer(i9, i4);
                            } else {
                                System.arraycopy(this.fCurrentEntity.ch, i9, this.fCurrentEntity.ch, 0, i4);
                            }
                            if (i2 != -1) {
                                i2 -= i9;
                            }
                            if (load(i4, false)) {
                                i9 = 0;
                                z = false;
                                break;
                            }
                            i9 = 0;
                        } else {
                            continue;
                        }
                    }
                    i5 = this.fCurrentEntity.position - i9;
                    this.fCurrentEntity.columnNumber += i5;
                    if (i5 > 0) {
                        strAddSymbol2 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i9, i5);
                        if (i2 != -1) {
                            int i16 = i2 - i9;
                            strAddSymbol4 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i9, i16);
                            int i17 = (i5 - i16) - 1;
                            i6 = i2 + 1;
                            if (!XML11Char.isXML11NCNameStart(this.fCurrentEntity.ch[i6])) {
                                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IllegalQName", null, (short) 2);
                            }
                            strAddSymbol3 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i6, i17);
                        } else {
                            strAddSymbol3 = strAddSymbol2;
                            strAddSymbol4 = null;
                        }
                        qName.setValues(strAddSymbol4, strAddSymbol3, strAddSymbol2, null);
                        return true;
                    }
                }
                this.fCurrentEntity.position--;
                return false;
            }
            this.fCurrentEntity.ch[0] = c4;
            if (load(1, false)) {
                this.fCurrentEntity.startPosition--;
            } else {
                i9 = 0;
                c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                if (XMLChar.isLowSurrogate(c) && XML11Char.isXML11NCNameStart(XMLChar.supplemental(c4, c))) {
                    XMLEntityManager.ScannedEntity scannedEntity9 = this.fCurrentEntity;
                    i = scannedEntity9.position + 1;
                    scannedEntity9.position = i;
                    if (i == this.fCurrentEntity.count) {
                        this.fCurrentEntity.ch[0] = c4;
                        this.fCurrentEntity.ch[1] = c;
                        if (load(2, false)) {
                            this.fCurrentEntity.columnNumber += 2;
                            strAddSymbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 2);
                            qName.setValues(null, strAddSymbol, strAddSymbol, null);
                            return true;
                        }
                        i9 = 0;
                    }
                    i2 = -1;
                    while (true) {
                        c2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                        if (!XML11Char.isXML11Name(c2)) {
                            if (!XML11Char.isXML11NameHighSurrogate(c2)) {
                                z = false;
                                break;
                            }
                            XMLEntityManager.ScannedEntity scannedEntity10 = this.fCurrentEntity;
                            i7 = scannedEntity10.position + 1;
                            scannedEntity10.position = i7;
                            if (i7 == this.fCurrentEntity.count) {
                                i8 = this.fCurrentEntity.position - i9;
                                if (i8 == this.fCurrentEntity.ch.length) {
                                    resizeBuffer(i9, i8);
                                } else {
                                    System.arraycopy(this.fCurrentEntity.ch, i9, this.fCurrentEntity.ch, 0, i8);
                                }
                                if (i2 != -1) {
                                    i2 -= i9;
                                }
                                if (!load(i8, false)) {
                                    i9 = 0;
                                    c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                                    if (XMLChar.isLowSurrogate(c3)) {
                                    }
                                    this.fCurrentEntity.position--;
                                    z = true;
                                    break;
                                }
                                this.fCurrentEntity.startPosition--;
                                this.fCurrentEntity.position--;
                                z = true;
                                i9 = 0;
                                break;
                            }
                            c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                            if (XMLChar.isLowSurrogate(c3)) {
                            }
                            this.fCurrentEntity.position--;
                            z = true;
                            break;
                        }
                        if (c2 == ':') {
                            if (i2 != -1) {
                                z = false;
                                break;
                            }
                            i2 = this.fCurrentEntity.position;
                        }
                        XMLEntityManager.ScannedEntity scannedEntity11 = this.fCurrentEntity;
                        i3 = scannedEntity11.position + 1;
                        scannedEntity11.position = i3;
                        if (i3 == this.fCurrentEntity.count) {
                            i4 = this.fCurrentEntity.position - i9;
                            if (i4 == this.fCurrentEntity.ch.length) {
                                resizeBuffer(i9, i4);
                            } else {
                                System.arraycopy(this.fCurrentEntity.ch, i9, this.fCurrentEntity.ch, 0, i4);
                            }
                            if (i2 != -1) {
                                i2 -= i9;
                            }
                            if (load(i4, false)) {
                                i9 = 0;
                                z = false;
                                break;
                            }
                            i9 = 0;
                        } else {
                            continue;
                        }
                    }
                    i5 = this.fCurrentEntity.position - i9;
                    this.fCurrentEntity.columnNumber += i5;
                    if (i5 > 0) {
                        strAddSymbol2 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i9, i5);
                        if (i2 != -1) {
                            int i18 = i2 - i9;
                            strAddSymbol4 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i9, i18);
                            int i19 = (i5 - i18) - 1;
                            i6 = i2 + 1;
                            if (!XML11Char.isXML11NCNameStart(this.fCurrentEntity.ch[i6])) {
                                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IllegalQName", null, (short) 2);
                            }
                            strAddSymbol3 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i6, i19);
                        } else {
                            strAddSymbol3 = strAddSymbol2;
                            strAddSymbol4 = null;
                        }
                        qName.setValues(strAddSymbol4, strAddSymbol3, strAddSymbol2, null);
                        return true;
                    }
                }
            }
            this.fCurrentEntity.position--;
            return false;
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x004c  */
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public boolean skipChar(int i) throws IOException {
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (c == i) {
            this.fCurrentEntity.position++;
            if (i == 10) {
                this.fCurrentEntity.lineNumber++;
                this.fCurrentEntity.columnNumber = 1;
            } else {
                this.fCurrentEntity.columnNumber++;
            }
            return true;
        }
        if (i == 10 && ((c == 8232 || c == 133) && this.fCurrentEntity.isExternal())) {
            this.fCurrentEntity.position++;
        } else {
            if (i != 10 || c != '\r' || !this.fCurrentEntity.isExternal()) {
                return false;
            }
            if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                this.fCurrentEntity.ch[0] = c;
                load(1, false);
            }
            char[] cArr = this.fCurrentEntity.ch;
            XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
            int i2 = scannedEntity.position + 1;
            scannedEntity.position = i2;
            char c2 = cArr[i2];
            if (c2 == '\n' || c2 == 133) {
                this.fCurrentEntity.position++;
            }
        }
        this.fCurrentEntity.lineNumber++;
        this.fCurrentEntity.columnNumber = 1;
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:47:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:50:0x00fe  */
    @Override // org.apache.xerces.impl.XMLEntityScanner
    public boolean skipSpaces() throws IOException {
        boolean zLoad;
        boolean zLoad2;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (this.fCurrentEntity.isExternal()) {
            if (XML11Char.isXML11Space(c)) {
                do {
                    if (c == '\n' || c == '\r' || c == 133 || c == 8232) {
                        this.fCurrentEntity.lineNumber++;
                        this.fCurrentEntity.columnNumber = 1;
                        if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                            this.fCurrentEntity.ch[0] = c;
                            zLoad2 = load(1, true);
                            if (!zLoad2) {
                                this.fCurrentEntity.startPosition = 0;
                                this.fCurrentEntity.position = 0;
                            }
                        } else {
                            zLoad2 = false;
                        }
                        if (c == '\r') {
                            char[] cArr = this.fCurrentEntity.ch;
                            XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                            int i = scannedEntity.position + 1;
                            scannedEntity.position = i;
                            char c2 = cArr[i];
                            if (c2 != '\n' && c2 != 133) {
                                this.fCurrentEntity.position--;
                            }
                        }
                    } else {
                        this.fCurrentEntity.columnNumber++;
                        zLoad2 = false;
                    }
                    if (!zLoad2) {
                        this.fCurrentEntity.position++;
                    }
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        load(0, true);
                    }
                    c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                } while (XML11Char.isXML11Space(c));
                return true;
            }
        } else if (XMLChar.isSpace(c)) {
            do {
                if (c == '\n') {
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                        this.fCurrentEntity.ch[0] = c;
                        zLoad = load(1, true);
                        if (!zLoad) {
                            this.fCurrentEntity.startPosition = 0;
                            this.fCurrentEntity.position = 0;
                        }
                    }
                    if (!zLoad) {
                        this.fCurrentEntity.position++;
                    }
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        load(0, true);
                    }
                    c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
                } else {
                    this.fCurrentEntity.columnNumber++;
                }
                zLoad = false;
                if (!zLoad) {
                    this.fCurrentEntity.position++;
                }
                if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                    load(0, true);
                }
                c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
            } while (XMLChar.isSpace(c));
            return true;
        }
        return false;
    }

    @Override // org.apache.xerces.impl.XMLEntityScanner
    public boolean skipString(String str) throws IOException {
        XMLEntityManager.ScannedEntity scannedEntity;
        int i;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char[] cArr = this.fCurrentEntity.ch;
            XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
            int i3 = scannedEntity2.position;
            scannedEntity2.position = i3 + 1;
            if (cArr[i3] != str.charAt(i2)) {
                scannedEntity = this.fCurrentEntity;
                i = scannedEntity.position - (i2 + 1);
            } else {
                if (i2 < length - 1 && this.fCurrentEntity.position == this.fCurrentEntity.count) {
                    int i4 = i2 + 1;
                    System.arraycopy(this.fCurrentEntity.ch, (this.fCurrentEntity.count - i2) - 1, this.fCurrentEntity.ch, 0, i4);
                    if (load(i4, false)) {
                        this.fCurrentEntity.startPosition -= i4;
                        scannedEntity = this.fCurrentEntity;
                        i = scannedEntity.position - i4;
                    }
                }
            }
            scannedEntity.position = i;
            return false;
        }
        this.fCurrentEntity.columnNumber += length;
        return true;
    }
}
