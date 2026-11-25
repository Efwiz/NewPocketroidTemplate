package org.apache.xerces.impl.dtd;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;
import org.apache.xerces.impl.XMLDTDScannerImpl;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.DefaultErrorHandler;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarLoader;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public class XMLDTDLoader extends XMLDTDProcessor implements XMLGrammarLoader {
    protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    private boolean fBalanceSyntaxTrees;
    protected XMLDTDScannerImpl fDTDScanner;
    protected XMLEntityManager fEntityManager;
    protected XMLEntityResolver fEntityResolver;
    protected Locale fLocale;
    private boolean fStrictURI;
    protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
    protected static final String BALANCE_SYNTAX_TREES = "http://apache.org/xml/features/validation/balance-syntax-trees";
    private static final String[] LOADER_RECOGNIZED_FEATURES = {"http://xml.org/sax/features/validation", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", "http://apache.org/xml/features/scanner/notify-char-refs", STANDARD_URI_CONFORMANT_FEATURE, BALANCE_SYNTAX_TREES};
    protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    protected static final String LOCALE = "http://apache.org/xml/properties/locale";
    private static final String[] LOADER_RECOGNIZED_PROPERTIES = {"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", ERROR_HANDLER, "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/validator/dtd", LOCALE};

    public XMLDTDLoader() {
        this(new SymbolTable());
    }

    public XMLDTDLoader(SymbolTable symbolTable) {
        this(symbolTable, null);
    }

    public XMLDTDLoader(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this(symbolTable, xMLGrammarPool, null, new XMLEntityManager());
    }

    XMLDTDLoader(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool, XMLErrorReporter xMLErrorReporter, XMLEntityResolver xMLEntityResolver) {
        this.fStrictURI = false;
        this.fBalanceSyntaxTrees = false;
        this.fSymbolTable = symbolTable;
        this.fGrammarPool = xMLGrammarPool;
        if (xMLErrorReporter == null) {
            xMLErrorReporter = new XMLErrorReporter();
            xMLErrorReporter.setProperty(ERROR_HANDLER, new DefaultErrorHandler());
        }
        this.fErrorReporter = xMLErrorReporter;
        if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
            XMLMessageFormatter xMLMessageFormatter = new XMLMessageFormatter();
            this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xMLMessageFormatter);
            this.fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xMLMessageFormatter);
        }
        this.fEntityResolver = xMLEntityResolver;
        if (xMLEntityResolver instanceof XMLEntityManager) {
            this.fEntityManager = (XMLEntityManager) xMLEntityResolver;
        } else {
            this.fEntityManager = new XMLEntityManager();
        }
        this.fEntityManager.setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter);
        XMLDTDScannerImpl xMLDTDScannerImplCreateDTDScanner = createDTDScanner(this.fSymbolTable, this.fErrorReporter, this.fEntityManager);
        this.fDTDScanner = xMLDTDScannerImplCreateDTDScanner;
        xMLDTDScannerImplCreateDTDScanner.setDTDHandler(this);
        this.fDTDScanner.setDTDContentModelHandler(this);
        reset();
    }

    protected XMLDTDScannerImpl createDTDScanner(SymbolTable symbolTable, XMLErrorReporter xMLErrorReporter, XMLEntityManager xMLEntityManager) {
        return new XMLDTDScannerImpl(symbolTable, xMLErrorReporter, xMLEntityManager);
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public XMLEntityResolver getEntityResolver() {
        return this.fEntityResolver;
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public XMLErrorHandler getErrorHandler() {
        return this.fErrorReporter.getErrorHandler();
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public boolean getFeature(String str) throws XMLConfigurationException {
        if (str.equals("http://xml.org/sax/features/validation")) {
            return this.fValidation;
        }
        if (str.equals("http://apache.org/xml/features/validation/warn-on-duplicate-attdef")) {
            return this.fWarnDuplicateAttdef;
        }
        if (str.equals("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef")) {
            return this.fWarnOnUndeclaredElemdef;
        }
        if (str.equals("http://apache.org/xml/features/scanner/notify-char-refs")) {
            return this.fDTDScanner.getFeature(str);
        }
        if (str.equals(STANDARD_URI_CONFORMANT_FEATURE)) {
            return this.fStrictURI;
        }
        if (str.equals(BALANCE_SYNTAX_TREES)) {
            return this.fBalanceSyntaxTrees;
        }
        throw new XMLConfigurationException((short) 0, str);
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public Locale getLocale() {
        return this.fLocale;
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public Object getProperty(String str) throws XMLConfigurationException {
        if (str.equals("http://apache.org/xml/properties/internal/symbol-table")) {
            return this.fSymbolTable;
        }
        if (str.equals("http://apache.org/xml/properties/internal/error-reporter")) {
            return this.fErrorReporter;
        }
        if (str.equals(ERROR_HANDLER)) {
            return this.fErrorReporter.getErrorHandler();
        }
        if (str.equals("http://apache.org/xml/properties/internal/entity-resolver")) {
            return this.fEntityResolver;
        }
        if (str.equals(LOCALE)) {
            return getLocale();
        }
        if (str.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
            return this.fGrammarPool;
        }
        if (str.equals("http://apache.org/xml/properties/internal/validator/dtd")) {
            return this.fValidator;
        }
        throw new XMLConfigurationException((short) 0, str);
    }

    @Override // org.apache.xerces.impl.dtd.XMLDTDProcessor, org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedFeatures() {
        return (String[]) LOADER_RECOGNIZED_FEATURES.clone();
    }

    @Override // org.apache.xerces.impl.dtd.XMLDTDProcessor, org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedProperties() {
        return (String[]) LOADER_RECOGNIZED_PROPERTIES.clone();
    }

    protected short getScannerVersion() {
        return (short) 1;
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public Grammar loadGrammar(XMLInputSource xMLInputSource) throws IOException, XNIException {
        reset();
        XMLDTDDescription xMLDTDDescription = new XMLDTDDescription(xMLInputSource.getPublicId(), xMLInputSource.getSystemId(), xMLInputSource.getBaseSystemId(), XMLEntityManager.expandSystemId(xMLInputSource.getSystemId(), xMLInputSource.getBaseSystemId(), this.fStrictURI), null);
        this.fDTDGrammar = !this.fBalanceSyntaxTrees ? new DTDGrammar(this.fSymbolTable, xMLDTDDescription) : new BalancedDTDGrammar(this.fSymbolTable, xMLDTDDescription);
        this.fGrammarBucket = new DTDGrammarBucket();
        this.fGrammarBucket.setStandalone(false);
        this.fGrammarBucket.setActiveGrammar(this.fDTDGrammar);
        try {
            this.fDTDScanner.setInputSource(xMLInputSource);
            this.fDTDScanner.scanDTDExternalSubset(true);
        } catch (EOFException unused) {
        } catch (Throwable th) {
            this.fEntityManager.closeReaders();
            throw th;
        }
        this.fEntityManager.closeReaders();
        if (this.fDTDGrammar != null && this.fGrammarPool != null) {
            this.fGrammarPool.cacheGrammars("http://www.w3.org/TR/REC-xml", new Grammar[]{this.fDTDGrammar});
        }
        return this.fDTDGrammar;
    }

    public void loadGrammarWithContext(XMLDTDValidator xMLDTDValidator, String str, String str2, String str3, String str4, String str5) throws IOException, XNIException {
        DTDGrammarBucket grammarBucket = xMLDTDValidator.getGrammarBucket();
        DTDGrammar activeGrammar = grammarBucket.getActiveGrammar();
        if (activeGrammar == null || activeGrammar.isImmutable()) {
            return;
        }
        this.fGrammarBucket = grammarBucket;
        this.fEntityManager.setScannerVersion(getScannerVersion());
        reset();
        if (str5 != null) {
            try {
                StringBuffer stringBuffer = new StringBuffer(str5.length() + 2);
                stringBuffer.append(str5).append("]>");
                this.fEntityManager.startDocumentEntity(new XMLInputSource((String) null, str4, (String) null, new StringReader(stringBuffer.toString()), (String) null));
                this.fDTDScanner.scanDTDInternalSubset(true, false, str3 != null);
            } catch (EOFException unused) {
            } finally {
                this.fEntityManager.closeReaders();
            }
        }
        if (str3 != null) {
            this.fDTDScanner.setInputSource(this.fEntityManager.resolveEntity(new XMLDTDDescription(str2, str3, str4, null, str)));
            this.fDTDScanner.scanDTDExternalSubset(true);
        }
    }

    @Override // org.apache.xerces.impl.dtd.XMLDTDProcessor
    protected void reset() {
        super.reset();
        this.fDTDScanner.reset();
        this.fEntityManager.reset();
        this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public void setEntityResolver(XMLEntityResolver xMLEntityResolver) {
        this.fEntityResolver = xMLEntityResolver;
        this.fEntityManager.setProperty("http://apache.org/xml/properties/internal/entity-resolver", xMLEntityResolver);
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public void setErrorHandler(XMLErrorHandler xMLErrorHandler) {
        this.fErrorReporter.setProperty(ERROR_HANDLER, xMLErrorHandler);
    }

    @Override // org.apache.xerces.impl.dtd.XMLDTDProcessor, org.apache.xerces.xni.parser.XMLComponent
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        if (str.equals("http://xml.org/sax/features/validation")) {
            this.fValidation = z;
            return;
        }
        if (str.equals("http://apache.org/xml/features/validation/warn-on-duplicate-attdef")) {
            this.fWarnDuplicateAttdef = z;
            return;
        }
        if (str.equals("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef")) {
            this.fWarnOnUndeclaredElemdef = z;
            return;
        }
        if (str.equals("http://apache.org/xml/features/scanner/notify-char-refs")) {
            this.fDTDScanner.setFeature(str, z);
        } else if (str.equals(STANDARD_URI_CONFORMANT_FEATURE)) {
            this.fStrictURI = z;
        } else {
            if (!str.equals(BALANCE_SYNTAX_TREES)) {
                throw new XMLConfigurationException((short) 0, str);
            }
            this.fBalanceSyntaxTrees = z;
        }
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarLoader
    public void setLocale(Locale locale) {
        this.fLocale = locale;
        this.fErrorReporter.setLocale(locale);
    }

    @Override // org.apache.xerces.impl.dtd.XMLDTDProcessor, org.apache.xerces.xni.parser.XMLComponent
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (!str.equals("http://apache.org/xml/properties/internal/symbol-table")) {
            if (str.equals("http://apache.org/xml/properties/internal/error-reporter")) {
                this.fErrorReporter = (XMLErrorReporter) obj;
                if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
                    XMLMessageFormatter xMLMessageFormatter = new XMLMessageFormatter();
                    this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xMLMessageFormatter);
                    this.fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xMLMessageFormatter);
                }
            } else {
                if (str.equals(ERROR_HANDLER)) {
                    this.fErrorReporter.setProperty(str, obj);
                    return;
                }
                if (!str.equals("http://apache.org/xml/properties/internal/entity-resolver")) {
                    if (str.equals(LOCALE)) {
                        setLocale((Locale) obj);
                        return;
                    } else {
                        if (!str.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
                            throw new XMLConfigurationException((short) 0, str);
                        }
                        this.fGrammarPool = (XMLGrammarPool) obj;
                        return;
                    }
                }
                this.fEntityResolver = (XMLEntityResolver) obj;
            }
            this.fEntityManager.setProperty(str, obj);
        }
        this.fSymbolTable = (SymbolTable) obj;
        this.fDTDScanner.setProperty(str, obj);
        this.fEntityManager.setProperty(str, obj);
    }
}
