package org.apache.xerces.impl;

import io.netty.util.internal.StringUtil;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.IOException;
import net.bytebuddy.asm.Advice;
import org.apache.xerces.impl.io.MalformedByteSequenceException;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class XMLVersionDetector {
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private static final char[] XML11_VERSION = {ExternalAnnotationProvider.NONNULL, '.', ExternalAnnotationProvider.NONNULL};
    protected static final String fVersionSymbol = "version".intern();
    protected static final String fXMLSymbol = "[xml]".intern();
    protected XMLEntityManager fEntityManager;
    protected XMLErrorReporter fErrorReporter;
    protected SymbolTable fSymbolTable;
    protected String fEncoding = null;
    private final char[] fExpectedVersionString = {'<', MangleConstant.Q_MARK, 'x', Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL, 'l', StringUtil.SPACE, 'v', 'e', Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL, Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL, 'i', 'o', 'n', '=', StringUtil.SPACE, StringUtil.SPACE, StringUtil.SPACE, StringUtil.SPACE, StringUtil.SPACE};

    private void fixupCurrentEntity(XMLEntityManager xMLEntityManager, char[] cArr, int i) {
        XMLEntityManager.ScannedEntity currentEntity = xMLEntityManager.getCurrentEntity();
        if ((currentEntity.count - currentEntity.position) + i > currentEntity.ch.length) {
            char[] cArr2 = currentEntity.ch;
            currentEntity.ch = new char[((currentEntity.count + i) - currentEntity.position) + 1];
            System.arraycopy(cArr2, 0, currentEntity.ch, 0, cArr2.length);
        }
        if (currentEntity.position < i) {
            System.arraycopy(currentEntity.ch, currentEntity.position, currentEntity.ch, i, currentEntity.count - currentEntity.position);
            currentEntity.count += i - currentEntity.position;
        } else {
            for (int i2 = i; i2 < currentEntity.position; i2++) {
                currentEntity.ch[i2] = StringUtil.SPACE;
            }
        }
        System.arraycopy(cArr, 0, currentEntity.ch, 0, i);
        currentEntity.position = 0;
        currentEntity.baseCharOffset = 0;
        currentEntity.startPosition = 0;
        currentEntity.lineNumber = 1;
        currentEntity.columnNumber = 1;
    }

    public short determineDocVersion(XMLInputSource xMLInputSource) throws IOException {
        char[] cArr;
        int i = 0;
        this.fEncoding = this.fEntityManager.setupCurrentEntity(fXMLSymbol, xMLInputSource, false, true);
        this.fEntityManager.setScannerVersion((short) 1);
        XMLEntityScanner entityScanner = this.fEntityManager.getEntityScanner();
        try {
            if (!entityScanner.skipString("<?xml")) {
                return (short) 1;
            }
            if (!entityScanner.skipDeclSpaces()) {
                fixupCurrentEntity(this.fEntityManager, this.fExpectedVersionString, 5);
                return (short) 1;
            }
            if (!entityScanner.skipString("version")) {
                fixupCurrentEntity(this.fEntityManager, this.fExpectedVersionString, 6);
                return (short) 1;
            }
            entityScanner.skipDeclSpaces();
            if (entityScanner.peekChar() != 61) {
                fixupCurrentEntity(this.fEntityManager, this.fExpectedVersionString, 13);
                return (short) 1;
            }
            entityScanner.scanChar();
            entityScanner.skipDeclSpaces();
            this.fExpectedVersionString[14] = (char) entityScanner.scanChar();
            for (int i2 = 0; i2 < XML11_VERSION.length; i2++) {
                this.fExpectedVersionString[i2 + 15] = (char) entityScanner.scanChar();
            }
            this.fExpectedVersionString[18] = (char) entityScanner.scanChar();
            fixupCurrentEntity(this.fEntityManager, this.fExpectedVersionString, 19);
            while (true) {
                cArr = XML11_VERSION;
                if (i >= cArr.length || this.fExpectedVersionString[i + 15] != cArr[i]) {
                    break;
                    break;
                }
                i++;
            }
            return i == cArr.length ? (short) 2 : (short) 1;
        } catch (EOFException unused) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "PrematureEOF", null, (short) 2);
            return (short) -1;
        } catch (MalformedByteSequenceException e) {
            this.fErrorReporter.reportError(e.getDomain(), e.getKey(), e.getArguments(), (short) 2, (Exception) e);
            return (short) -1;
        } catch (CharConversionException e2) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[]) null, (short) 2, (Exception) e2);
            return (short) -1;
        }
    }

    public void reset(XMLComponentManager xMLComponentManager) throws XMLConfigurationException {
        this.fSymbolTable = (SymbolTable) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fErrorReporter = (XMLErrorReporter) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        this.fEntityManager = (XMLEntityManager) xMLComponentManager.getProperty(ENTITY_MANAGER);
        int i = 14;
        while (true) {
            char[] cArr = this.fExpectedVersionString;
            if (i >= cArr.length) {
                return;
            }
            cArr[i] = StringUtil.SPACE;
            i++;
        }
    }

    public void startDocumentParsing(XMLEntityHandler xMLEntityHandler, short s) {
        XMLEntityManager xMLEntityManager;
        short s2 = 1;
        if (s == 1) {
            xMLEntityManager = this.fEntityManager;
        } else {
            xMLEntityManager = this.fEntityManager;
            s2 = 2;
        }
        xMLEntityManager.setScannerVersion(s2);
        this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
        this.fEntityManager.setEntityHandler(xMLEntityHandler);
        xMLEntityHandler.startEntity(fXMLSymbol, this.fEntityManager.getCurrentResourceIdentifier(), this.fEncoding, null);
    }
}
