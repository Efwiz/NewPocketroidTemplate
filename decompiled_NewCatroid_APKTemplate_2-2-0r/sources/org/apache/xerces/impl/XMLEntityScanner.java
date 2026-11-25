package org.apache.xerces.impl;

import java.io.EOFException;
import java.io.IOException;
import java.util.Locale;
import org.apache.commons.lang3.CharEncoding;
import org.apache.xerces.impl.io.UCSReader;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLString;

/* JADX INFO: loaded from: classes13.dex */
public class XMLEntityScanner implements XMLLocator {
    private static final boolean DEBUG_BUFFER = false;
    private static final boolean DEBUG_ENCODINGS = false;
    private static final EOFException END_OF_DOCUMENT_ENTITY = new EOFException() { // from class: org.apache.xerces.impl.XMLEntityScanner.1
        private static final long serialVersionUID = 980337771224675268L;

        @Override // java.lang.Throwable
        public Throwable fillInStackTrace() {
            return this;
        }
    };
    protected XMLErrorReporter fErrorReporter;
    private XMLEntityManager fEntityManager = null;
    protected XMLEntityManager.ScannedEntity fCurrentEntity = null;
    protected SymbolTable fSymbolTable = null;
    protected int fBufferSize = 2048;

    @Override // org.apache.xerces.xni.XMLLocator
    public final String getBaseSystemId() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity == null || scannedEntity.entityLocation == null) {
            return null;
        }
        return this.fCurrentEntity.entityLocation.getExpandedSystemId();
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public final int getCharacterOffset() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity != null) {
            return scannedEntity.isExternal() ? this.fCurrentEntity.baseCharOffset + (this.fCurrentEntity.position - this.fCurrentEntity.startPosition) : this.fCurrentEntity.getCharacterOffset();
        }
        return -1;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public final int getColumnNumber() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity != null) {
            return scannedEntity.isExternal() ? this.fCurrentEntity.columnNumber : this.fCurrentEntity.getColumnNumber();
        }
        return -1;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public final String getEncoding() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity != null) {
            return scannedEntity.isExternal() ? this.fCurrentEntity.encoding : this.fCurrentEntity.getEncoding();
        }
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public final String getExpandedSystemId() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity != null) {
            return (scannedEntity.entityLocation == null || this.fCurrentEntity.entityLocation.getExpandedSystemId() == null) ? this.fCurrentEntity.getExpandedSystemId() : this.fCurrentEntity.entityLocation.getExpandedSystemId();
        }
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public final int getLineNumber() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity != null) {
            return scannedEntity.isExternal() ? this.fCurrentEntity.lineNumber : this.fCurrentEntity.getLineNumber();
        }
        return -1;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public final String getLiteralSystemId() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity != null) {
            return (scannedEntity.entityLocation == null || this.fCurrentEntity.entityLocation.getLiteralSystemId() == null) ? this.fCurrentEntity.getLiteralSystemId() : this.fCurrentEntity.entityLocation.getLiteralSystemId();
        }
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public final String getPublicId() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity == null || scannedEntity.entityLocation == null) {
            return null;
        }
        return this.fCurrentEntity.entityLocation.getPublicId();
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public final String getXMLVersion() {
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity != null) {
            return scannedEntity.isExternal() ? this.fCurrentEntity.xmlVersion : this.fCurrentEntity.getXMLVersion();
        }
        return null;
    }

    public final boolean isExternal() {
        return this.fCurrentEntity.isExternal();
    }

    final boolean load(int i, boolean z) throws IOException {
        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
        int length = this.fCurrentEntity.ch.length - i;
        if (!this.fCurrentEntity.mayReadChunks && length > 64) {
            length = 64;
        }
        int i2 = this.fCurrentEntity.reader.read(this.fCurrentEntity.ch, i, length);
        if (i2 != -1) {
            if (i2 == 0) {
                return false;
            }
            this.fCurrentEntity.count = i2 + i;
            this.fCurrentEntity.position = i;
            this.fCurrentEntity.startPosition = i;
            return false;
        }
        this.fCurrentEntity.count = i;
        this.fCurrentEntity.position = i;
        this.fCurrentEntity.startPosition = i;
        if (z) {
            this.fEntityManager.endEntity();
            XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
            if (scannedEntity == null) {
                throw END_OF_DOCUMENT_ENTITY;
            }
            if (scannedEntity.position == this.fCurrentEntity.count) {
                load(0, true);
            }
        }
        return true;
    }

    public int peekChar() throws IOException {
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (this.fCurrentEntity.isExternal() && c == '\r') {
            return 10;
        }
        return c;
    }

    public final void reset(SymbolTable symbolTable, XMLEntityManager xMLEntityManager, XMLErrorReporter xMLErrorReporter) {
        this.fCurrentEntity = null;
        this.fSymbolTable = symbolTable;
        this.fEntityManager = xMLEntityManager;
        this.fErrorReporter = xMLErrorReporter;
    }

    final void resizeBuffer(int i, int i2) {
        char[] cArr = new char[this.fCurrentEntity.ch.length << 1];
        System.arraycopy(this.fCurrentEntity.ch, i, cArr, 0, i2);
        this.fCurrentEntity.ch = cArr;
    }

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
            if (c == '\r' && (zIsExternal = this.fCurrentEntity.isExternal())) {
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
        if (c == '\r' && zIsExternal) {
            if (this.fCurrentEntity.position < this.fCurrentEntity.count) {
                char[] cArr2 = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
                int i2 = scannedEntity2.position;
                scannedEntity2.position = i2 + 1;
                if (cArr2[i2] != '\n') {
                    this.fCurrentEntity.position--;
                }
            }
            c = '\n';
        }
        this.fCurrentEntity.columnNumber++;
        return c;
    }

    /* JADX WARN: Code duplicated, block: B:63:0x0104 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:67:? A[LOOP:1: B:13:0x0053->B:67:?, LOOP_END, SYNTHETIC] */
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
        if (c == '\n' || (c == '\r' && zIsExternal)) {
            int i3 = 0;
            while (true) {
                char[] cArr = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                int i4 = scannedEntity.position;
                scannedEntity.position = i4 + 1;
                char c2 = cArr[i4];
                if (c2 != '\r' || !zIsExternal) {
                    if (c2 != '\n') {
                        this.fCurrentEntity.position--;
                        i = i2;
                        break;
                    }
                    i3++;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i3;
                        this.fCurrentEntity.startPosition = i3;
                        if (load(i3, false)) {
                            break;
                        }
                        i2 = 0;
                    }
                    if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                        i = i2;
                        break;
                    }
                } else {
                    int i5 = i3 + 1;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i5;
                        this.fCurrentEntity.startPosition = i5;
                        if (load(i5, false)) {
                            i3 = i5;
                            break;
                        }
                        i2 = 0;
                    }
                    if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
                        this.fCurrentEntity.position++;
                        i2++;
                        i3 = i5;
                    } else {
                        i3 += 2;
                    }
                    if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                        i = i2;
                        break;
                    }
                }
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
        while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
            char[] cArr2 = this.fCurrentEntity.ch;
            XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
            int i8 = scannedEntity2.position;
            scannedEntity2.position = i8 + 1;
            if (!XMLChar.isContent(cArr2[i8])) {
                this.fCurrentEntity.position--;
                break;
            }
        }
        int i9 = this.fCurrentEntity.position - i2;
        this.fCurrentEntity.columnNumber += i9 - i;
        xMLString.setValues(this.fCurrentEntity.ch, i2, i9);
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            return -1;
        }
        char c3 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (c3 == '\r' && zIsExternal) {
            return 10;
        }
        return c3;
    }

    /* JADX WARN: Code duplicated, block: B:84:0x024b  */
    public boolean scanData(String str, XMLStringBuffer xMLStringBuffer) throws IOException {
        int i;
        boolean z;
        int i2;
        char c;
        int length = str.length();
        char cCharAt = str.charAt(0);
        boolean zIsExternal = this.fCurrentEntity.isExternal();
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        boolean zLoad = false;
        while (this.fCurrentEntity.position > this.fCurrentEntity.count - length && !zLoad) {
            System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.ch, 0, this.fCurrentEntity.count - this.fCurrentEntity.position);
            zLoad = load(this.fCurrentEntity.count - this.fCurrentEntity.position, false);
            this.fCurrentEntity.position = 0;
            this.fCurrentEntity.startPosition = 0;
        }
        if (this.fCurrentEntity.position > this.fCurrentEntity.count - length) {
            xMLStringBuffer.append(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.count - this.fCurrentEntity.position);
            this.fCurrentEntity.columnNumber += this.fCurrentEntity.count;
            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
            XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
            scannedEntity.position = scannedEntity.count;
            XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
            scannedEntity2.startPosition = scannedEntity2.count;
            load(0, true);
            return false;
        }
        int i3 = this.fCurrentEntity.position;
        char c2 = this.fCurrentEntity.ch[i3];
        if (c2 == '\n' || (c2 == '\r' && zIsExternal)) {
            i = 0;
            do {
                char[] cArr = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity3 = this.fCurrentEntity;
                int i4 = scannedEntity3.position;
                scannedEntity3.position = i4 + 1;
                char c3 = cArr[i4];
                if (c3 == '\r' && zIsExternal) {
                    int i5 = i + 1;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i5;
                        this.fCurrentEntity.startPosition = i5;
                        if (load(i5, false)) {
                            i3 = 0;
                            i = i5;
                            break;
                        }
                        i3 = 0;
                    }
                    if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
                        this.fCurrentEntity.position++;
                        i3++;
                        i = i5;
                    } else {
                        i += 2;
                    }
                } else {
                    if (c3 != '\n') {
                        this.fCurrentEntity.position--;
                        break;
                    }
                    i++;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i;
                        this.fCurrentEntity.startPosition = i;
                        this.fCurrentEntity.count = i;
                        if (load(i, false)) {
                            i3 = 0;
                            break;
                        }
                        i3 = 0;
                    }
                }
            } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
            for (int i6 = i3; i6 < this.fCurrentEntity.position; i6++) {
                this.fCurrentEntity.ch[i6] = '\n';
            }
            int i7 = this.fCurrentEntity.position - i3;
            if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                xMLStringBuffer.append(this.fCurrentEntity.ch, i3, i7);
                return true;
            }
        } else {
            i = 0;
        }
        while (true) {
            if (this.fCurrentEntity.position < this.fCurrentEntity.count) {
                char[] cArr2 = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity4 = this.fCurrentEntity;
                int i8 = scannedEntity4.position;
                scannedEntity4.position = i8 + 1;
                char c4 = cArr2[i8];
                if (c4 == cCharAt) {
                    int i9 = this.fCurrentEntity.position - 1;
                    int i10 = 1;
                    while (true) {
                        if (i10 < length) {
                            if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                                this.fCurrentEntity.position -= i10;
                            } else {
                                char[] cArr3 = this.fCurrentEntity.ch;
                                XMLEntityManager.ScannedEntity scannedEntity5 = this.fCurrentEntity;
                                int i11 = scannedEntity5.position;
                                scannedEntity5.position = i11 + 1;
                                if (str.charAt(i10) != cArr3[i11]) {
                                    this.fCurrentEntity.position--;
                                } else {
                                    i10++;
                                }
                            }
                        }
                        if (this.fCurrentEntity.position == i9 + length) {
                            z = true;
                        } else {
                            c = '\r';
                        }
                    }
                } else {
                    if (c4 != '\n') {
                        c = '\r';
                        if (!zIsExternal || c4 != '\r') {
                            if (XMLChar.isInvalid(c4)) {
                                this.fCurrentEntity.position--;
                                int i12 = this.fCurrentEntity.position - i3;
                                this.fCurrentEntity.columnNumber += i12 - i;
                                xMLStringBuffer.append(this.fCurrentEntity.ch, i3, i12);
                                return true;
                            }
                        }
                    }
                    this.fCurrentEntity.position--;
                }
                i2 = this.fCurrentEntity.position - i3;
                this.fCurrentEntity.columnNumber += i2 - i;
                if (z) {
                    i2 -= length;
                }
                xMLStringBuffer.append(this.fCurrentEntity.ch, i3, i2);
                return !z;
            }
            z = false;
            i2 = this.fCurrentEntity.position - i3;
            this.fCurrentEntity.columnNumber += i2 - i;
            if (z) {
                i2 -= length;
            }
            xMLStringBuffer.append(this.fCurrentEntity.ch, i3, i2);
            return !z;
        }
    }

    /* JADX WARN: Code duplicated, block: B:81:0x0104 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:85:? A[LOOP:1: B:13:0x0053->B:85:?, LOOP_END, SYNTHETIC] */
    public int scanLiteral(int i, XMLString xMLString) throws IOException {
        int i2 = 0;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        } else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
            this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[this.fCurrentEntity.count - 1];
            load(1, false);
            this.fCurrentEntity.position = 0;
            this.fCurrentEntity.startPosition = 0;
        }
        int i3 = this.fCurrentEntity.position;
        char c = this.fCurrentEntity.ch[i3];
        boolean zIsExternal = this.fCurrentEntity.isExternal();
        if (c == '\n' || (c == '\r' && zIsExternal)) {
            int i4 = 0;
            while (true) {
                char[] cArr = this.fCurrentEntity.ch;
                XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                int i5 = scannedEntity.position;
                scannedEntity.position = i5 + 1;
                char c2 = cArr[i5];
                if (c2 != '\r' || !zIsExternal) {
                    if (c2 != '\n') {
                        this.fCurrentEntity.position--;
                        i2 = i3;
                        break;
                    }
                    i4++;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i4;
                        this.fCurrentEntity.startPosition = i4;
                        if (load(i4, false)) {
                            break;
                        }
                        i3 = 0;
                    }
                    if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                        i2 = i3;
                        break;
                    }
                } else {
                    int i6 = i4 + 1;
                    this.fCurrentEntity.lineNumber++;
                    this.fCurrentEntity.columnNumber = 1;
                    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
                        this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
                        this.fCurrentEntity.position = i6;
                        this.fCurrentEntity.startPosition = i6;
                        if (load(i6, false)) {
                            i4 = i6;
                            break;
                        }
                        i3 = 0;
                    }
                    if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
                        this.fCurrentEntity.position++;
                        i3++;
                        i4 = i6;
                    } else {
                        i4 += 2;
                    }
                    if (this.fCurrentEntity.position >= this.fCurrentEntity.count - 1) {
                        i2 = i3;
                        break;
                    }
                }
            }
            for (int i7 = i2; i7 < this.fCurrentEntity.position; i7++) {
                this.fCurrentEntity.ch[i7] = '\n';
            }
            int i8 = this.fCurrentEntity.position - i2;
            if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                xMLString.setValues(this.fCurrentEntity.ch, i2, i8);
                return -1;
            }
            i3 = i2;
            i2 = i4;
        }
        while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
            char[] cArr2 = this.fCurrentEntity.ch;
            XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
            int i9 = scannedEntity2.position;
            scannedEntity2.position = i9 + 1;
            char c3 = cArr2[i9];
            if ((c3 == i && (!this.fCurrentEntity.literal || zIsExternal)) || c3 == '%' || (!XMLChar.isContent(c3) && (c3 != '\r' || zIsExternal))) {
                this.fCurrentEntity.position--;
                break;
            }
        }
        int i10 = this.fCurrentEntity.position - i3;
        this.fCurrentEntity.columnNumber += i10 - i2;
        xMLString.setValues(this.fCurrentEntity.ch, i3, i10);
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            return -1;
        }
        char c4 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (c4 == i && this.fCurrentEntity.literal) {
            return -1;
        }
        return c4;
    }

    public String scanNCName() throws IOException {
        int i = 0;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        int i2 = this.fCurrentEntity.position;
        if (!XMLChar.isNCNameStart(this.fCurrentEntity.ch[i2])) {
            i = i2;
            break;
        }
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        int i3 = scannedEntity.position + 1;
        scannedEntity.position = i3;
        if (i3 == this.fCurrentEntity.count) {
            this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[i2];
            if (load(1, false)) {
                this.fCurrentEntity.columnNumber++;
                return this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
            }
            i2 = 0;
        }
        while (true) {
            if (!XMLChar.isNCName(this.fCurrentEntity.ch[this.fCurrentEntity.position])) {
                i = i2;
                break;
            }
            XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
            int i4 = scannedEntity2.position + 1;
            scannedEntity2.position = i4;
            if (i4 == this.fCurrentEntity.count) {
                int i5 = this.fCurrentEntity.position - i2;
                if (i5 == this.fCurrentEntity.ch.length) {
                    resizeBuffer(i2, i5);
                } else {
                    System.arraycopy(this.fCurrentEntity.ch, i2, this.fCurrentEntity.ch, 0, i5);
                }
                if (load(i5, false)) {
                    break;
                }
                i2 = 0;
            }
        }
        int i6 = this.fCurrentEntity.position - i;
        this.fCurrentEntity.columnNumber += i6;
        if (i6 > 0) {
            return this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, i6);
        }
        return null;
    }

    public String scanName() throws IOException {
        int i = 0;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        int i2 = this.fCurrentEntity.position;
        if (!XMLChar.isNameStart(this.fCurrentEntity.ch[i2])) {
            i = i2;
            break;
        }
        XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
        int i3 = scannedEntity.position + 1;
        scannedEntity.position = i3;
        if (i3 == this.fCurrentEntity.count) {
            this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[i2];
            if (load(1, false)) {
                this.fCurrentEntity.columnNumber++;
                return this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
            }
            i2 = 0;
        }
        while (true) {
            if (!XMLChar.isName(this.fCurrentEntity.ch[this.fCurrentEntity.position])) {
                i = i2;
                break;
            }
            XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
            int i4 = scannedEntity2.position + 1;
            scannedEntity2.position = i4;
            if (i4 == this.fCurrentEntity.count) {
                int i5 = this.fCurrentEntity.position - i2;
                if (i5 == this.fCurrentEntity.ch.length) {
                    resizeBuffer(i2, i5);
                } else {
                    System.arraycopy(this.fCurrentEntity.ch, i2, this.fCurrentEntity.ch, 0, i5);
                }
                if (load(i5, false)) {
                    break;
                }
                i2 = 0;
            }
        }
        int i6 = this.fCurrentEntity.position - i;
        this.fCurrentEntity.columnNumber += i6;
        if (i6 > 0) {
            return this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, i6);
        }
        return null;
    }

    public String scanNmtoken() throws IOException {
        int i = 0;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        int i2 = this.fCurrentEntity.position;
        while (true) {
            if (!XMLChar.isName(this.fCurrentEntity.ch[this.fCurrentEntity.position])) {
                i = i2;
                break;
            }
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
                    break;
                }
                i2 = 0;
            }
        }
        int i5 = this.fCurrentEntity.position - i;
        this.fCurrentEntity.columnNumber += i5;
        if (i5 > 0) {
            return this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, i5);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0095  */
    /* JADX WARN: Code duplicated, block: B:27:0x0099  */
    /* JADX WARN: Code duplicated, block: B:29:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:33:0x00af  */
    /* JADX WARN: Code duplicated, block: B:48:0x0089 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:0x00ad A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:51:0x0057 A[SYNTHETIC] */
    public boolean scanQName(QName qName) throws IOException {
        String strAddSymbol;
        String strAddSymbol2;
        int i;
        int i2;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        int i3 = this.fCurrentEntity.position;
        if (XMLChar.isNCNameStart(this.fCurrentEntity.ch[i3])) {
            XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
            int i4 = scannedEntity.position + 1;
            scannedEntity.position = i4;
            if (i4 == this.fCurrentEntity.count) {
                this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[i3];
                if (load(1, false)) {
                    this.fCurrentEntity.columnNumber++;
                    String strAddSymbol3 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
                    qName.setValues(null, strAddSymbol3, strAddSymbol3, null);
                    return true;
                }
                i3 = 0;
            }
            int i5 = -1;
            while (XMLChar.isName(this.fCurrentEntity.ch[this.fCurrentEntity.position])) {
                if (this.fCurrentEntity.ch[this.fCurrentEntity.position] != ':') {
                    XMLEntityManager.ScannedEntity scannedEntity2 = this.fCurrentEntity;
                    i = scannedEntity2.position + 1;
                    scannedEntity2.position = i;
                    if (i != this.fCurrentEntity.count) {
                        i2 = this.fCurrentEntity.position - i3;
                        if (i2 == this.fCurrentEntity.ch.length) {
                            resizeBuffer(i3, i2);
                        } else {
                            System.arraycopy(this.fCurrentEntity.ch, i3, this.fCurrentEntity.ch, 0, i2);
                        }
                        if (i5 != -1) {
                            i5 -= i3;
                        }
                        if (load(i2, false)) {
                            i3 = 0;
                            break;
                        }
                        i3 = 0;
                    }
                } else {
                    if (i5 != -1) {
                        break;
                    }
                    i5 = this.fCurrentEntity.position;
                    XMLEntityManager.ScannedEntity scannedEntity3 = this.fCurrentEntity;
                    i = scannedEntity3.position + 1;
                    scannedEntity3.position = i;
                    if (i != this.fCurrentEntity.count) {
                        i2 = this.fCurrentEntity.position - i3;
                        if (i2 == this.fCurrentEntity.ch.length) {
                            resizeBuffer(i3, i2);
                        } else {
                            System.arraycopy(this.fCurrentEntity.ch, i3, this.fCurrentEntity.ch, 0, i2);
                        }
                        if (i5 != -1) {
                            i5 -= i3;
                        }
                        if (load(i2, false)) {
                            i3 = 0;
                            break;
                        }
                        i3 = 0;
                    }
                }
            }
            int i6 = this.fCurrentEntity.position - i3;
            this.fCurrentEntity.columnNumber += i6;
            if (i6 > 0) {
                String strAddSymbol4 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i3, i6);
                if (i5 != -1) {
                    int i7 = i5 - i3;
                    strAddSymbol2 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i3, i7);
                    int i8 = (i6 - i7) - 1;
                    int i9 = i5 + 1;
                    if (!XMLChar.isNCNameStart(this.fCurrentEntity.ch[i9])) {
                        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IllegalQName", null, (short) 2);
                    }
                    strAddSymbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i9, i8);
                } else {
                    strAddSymbol = strAddSymbol4;
                    strAddSymbol2 = null;
                }
                qName.setValues(strAddSymbol2, strAddSymbol, strAddSymbol4, null);
                return true;
            }
        }
        return false;
    }

    public final void setBufferSize(int i) {
        this.fBufferSize = i;
    }

    public final void setCurrentEntity(XMLEntityManager.ScannedEntity scannedEntity) {
        this.fCurrentEntity = scannedEntity;
    }

    public final void setEncoding(String str) throws IOException {
        XMLEntityManager.ScannedEntity scannedEntity;
        UCSReader uCSReader;
        XMLEntityManager.ScannedEntity scannedEntity2;
        UCSReader uCSReader2;
        if (this.fCurrentEntity.stream != null) {
            if (this.fCurrentEntity.encoding == null || !this.fCurrentEntity.encoding.equals(str)) {
                if (this.fCurrentEntity.encoding != null && this.fCurrentEntity.encoding.startsWith(CharEncoding.UTF_16)) {
                    String upperCase = str.toUpperCase(Locale.ENGLISH);
                    if (upperCase.equals(CharEncoding.UTF_16)) {
                        return;
                    }
                    if (upperCase.equals("ISO-10646-UCS-4")) {
                        if (this.fCurrentEntity.encoding.equals(CharEncoding.UTF_16BE)) {
                            scannedEntity2 = this.fCurrentEntity;
                            uCSReader2 = new UCSReader(this.fCurrentEntity.stream, (short) 8);
                        } else {
                            scannedEntity2 = this.fCurrentEntity;
                            uCSReader2 = new UCSReader(this.fCurrentEntity.stream, (short) 4);
                        }
                        scannedEntity2.reader = uCSReader2;
                        return;
                    }
                    if (upperCase.equals("ISO-10646-UCS-2")) {
                        if (this.fCurrentEntity.encoding.equals(CharEncoding.UTF_16BE)) {
                            scannedEntity = this.fCurrentEntity;
                            uCSReader = new UCSReader(this.fCurrentEntity.stream, (short) 2);
                        } else {
                            scannedEntity = this.fCurrentEntity;
                            uCSReader = new UCSReader(this.fCurrentEntity.stream, (short) 1);
                        }
                        scannedEntity.reader = uCSReader;
                        return;
                    }
                }
                XMLEntityManager.ScannedEntity scannedEntity3 = this.fCurrentEntity;
                scannedEntity3.setReader(scannedEntity3.stream, str, null);
                this.fCurrentEntity.encoding = str;
            }
        }
    }

    public final void setXMLVersion(String str) {
        this.fCurrentEntity.xmlVersion = str;
    }

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
        if (i != 10 || c != '\r' || !this.fCurrentEntity.isExternal()) {
            return false;
        }
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            this.fCurrentEntity.ch[0] = c;
            load(1, false);
        }
        this.fCurrentEntity.position++;
        if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
            this.fCurrentEntity.position++;
        }
        this.fCurrentEntity.lineNumber++;
        this.fCurrentEntity.columnNumber = 1;
        return true;
    }

    public final boolean skipDeclSpaces() throws IOException {
        boolean zLoad;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (!XMLChar.isSpace(c)) {
            return false;
        }
        boolean zIsExternal = this.fCurrentEntity.isExternal();
        do {
            if (c == '\n' || (zIsExternal && c == '\r')) {
                this.fCurrentEntity.lineNumber++;
                this.fCurrentEntity.columnNumber = 1;
                if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                    this.fCurrentEntity.ch[0] = c;
                    zLoad = load(1, true);
                    if (!zLoad) {
                        this.fCurrentEntity.position = 0;
                        this.fCurrentEntity.startPosition = 0;
                    }
                } else {
                    zLoad = false;
                }
                if (c == '\r' && zIsExternal) {
                    char[] cArr = this.fCurrentEntity.ch;
                    XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                    int i = scannedEntity.position + 1;
                    scannedEntity.position = i;
                    if (cArr[i] != '\n') {
                        this.fCurrentEntity.position--;
                    }
                }
            } else {
                this.fCurrentEntity.columnNumber++;
                zLoad = false;
            }
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

    public boolean skipSpaces() throws IOException {
        boolean zLoad;
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
        }
        char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (!XMLChar.isSpace(c)) {
            return false;
        }
        boolean zIsExternal = this.fCurrentEntity.isExternal();
        do {
            if (c == '\n' || (zIsExternal && c == '\r')) {
                this.fCurrentEntity.lineNumber++;
                this.fCurrentEntity.columnNumber = 1;
                if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
                    this.fCurrentEntity.ch[0] = c;
                    zLoad = load(1, true);
                    if (!zLoad) {
                        this.fCurrentEntity.position = 0;
                        this.fCurrentEntity.startPosition = 0;
                    }
                } else {
                    zLoad = false;
                }
                if (c == '\r' && zIsExternal) {
                    char[] cArr = this.fCurrentEntity.ch;
                    XMLEntityManager.ScannedEntity scannedEntity = this.fCurrentEntity;
                    int i = scannedEntity.position + 1;
                    scannedEntity.position = i;
                    if (cArr[i] != '\n') {
                        this.fCurrentEntity.position--;
                    }
                }
            } else {
                this.fCurrentEntity.columnNumber++;
                zLoad = false;
            }
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
