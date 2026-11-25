package org.apache.xerces.xinclude;

import com.reandroid.apk.ApkUtil;
import io.netty.util.internal.StringUtil;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.io.MalformedByteSequenceException;
import org.apache.xerces.util.AugmentationsImpl;
import org.apache.xerces.util.HTTPInputSource;
import org.apache.xerces.util.IntStack;
import org.apache.xerces.util.ParserConfigurationSettings;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLLocatorWrapper;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDFilter;
import org.apache.xerces.xni.parser.XMLDTDSource;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xerces.xpointer.XPointerHandler;
import org.apache.xerces.xpointer.XPointerProcessor;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class XIncludeHandler implements XMLComponent, XMLDocumentFilter, XMLDTDFilter {
    protected static final String ALLOW_UE_AND_NOTATION_EVENTS = "http://xml.org/sax/features/allow-dtd-events-after-endDTD";
    protected static final String BUFFER_SIZE = "http://apache.org/xml/properties/input-buffer-size";
    public static final String CURRENT_BASE_URI = "currentBaseURI";
    protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
    protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static final Boolean[] FEATURE_DEFAULTS;
    public static final String HTTP_ACCEPT = "Accept";
    public static final String HTTP_ACCEPT_LANGUAGE = "Accept-Language";
    private static final int INITIAL_SIZE = 8;
    protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    private static final QName NEW_NS_ATTR_QNAME;
    protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
    private static final Object[] PROPERTY_DEFAULTS;
    private static final String[] RECOGNIZED_FEATURES;
    private static final String[] RECOGNIZED_PROPERTIES;
    protected static final String SCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
    protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private static final int STATE_EXPECT_FALLBACK = 3;
    private static final int STATE_IGNORE = 2;
    private static final int STATE_NORMAL_PROCESSING = 1;
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    protected static final String VALIDATION = "http://xml.org/sax/features/validation";
    private static final String XINCLUDE_BASE;
    public static final String XINCLUDE_DEFAULT_CONFIGURATION = "org.apache.xerces.parsers.XIncludeParserConfiguration";
    protected static final String XINCLUDE_FIXUP_BASE_URIS = "http://apache.org/xml/features/xinclude/fixup-base-uris";
    protected static final String XINCLUDE_FIXUP_LANGUAGE = "http://apache.org/xml/features/xinclude/fixup-language";
    private static final String XINCLUDE_LANG;
    private static final QName XML_BASE_QNAME;
    private static final QName XML_LANG_QNAME;
    public static final String XPOINTER = "xpointer";
    private static final char[] gAfterEscaping1;
    private static final char[] gAfterEscaping2;
    private static final char[] gHexChs;
    private static final boolean[] gNeedEscaping;
    protected final Stack fBaseURI;
    protected final IntStack fBaseURIScope;
    protected XMLParserConfiguration fChildConfig;
    protected final XMLResourceIdentifier fCurrentBaseURI;
    protected String fCurrentLanguage;
    protected XMLDTDHandler fDTDHandler;
    protected XMLDTDSource fDTDSource;
    protected XMLLocator fDocLocation;
    protected XMLDocumentHandler fDocumentHandler;
    protected XMLDocumentSource fDocumentSource;
    protected XMLEntityResolver fEntityResolver;
    protected XMLErrorReporter fErrorReporter;
    protected final Stack fExpandedSystemID;
    boolean fHasIncludeReportedContent;
    protected String fHrefFromParent;
    private boolean fInDTD;
    private boolean fIsXML11;
    protected final IntStack fLanguageScope;
    protected final Stack fLanguageStack;
    protected final Stack fLiteralSystemID;
    protected XIncludeNamespaceSupport fNamespaceContext;
    private final ArrayList fNotations;
    protected String fParentRelativeURI;
    protected XIncludeHandler fParentXIncludeHandler;
    private int fResultDepth;
    private boolean[] fSawFallback;
    private boolean[] fSawInclude;
    protected SecurityManager fSecurityManager;
    private boolean fSeenRootElement;
    private boolean fSendUEAndNotationEvents;
    protected ParserConfigurationSettings fSettings;
    private int[] fState;
    protected SymbolTable fSymbolTable;
    private final ArrayList fUnparsedEntities;
    protected XIncludeTextReader fXInclude10TextReader;
    protected XIncludeTextReader fXInclude11TextReader;
    protected XMLParserConfiguration fXIncludeChildConfig;
    protected XMLParserConfiguration fXPointerChildConfig;
    public static final String XINCLUDE_NS_URI = "http://www.w3.org/2001/XInclude".intern();
    public static final String XINCLUDE_INCLUDE = "include".intern();
    public static final String XINCLUDE_FALLBACK = "fallback".intern();
    public static final String XINCLUDE_PARSE_XML = "xml".intern();
    public static final String XINCLUDE_PARSE_TEXT = "text".intern();
    public static final String XINCLUDE_ATTR_HREF = "href".intern();
    public static final String XINCLUDE_ATTR_PARSE = "parse".intern();
    public static final String XINCLUDE_ATTR_ENCODING = "encoding".intern();
    public static final String XINCLUDE_ATTR_ACCEPT = "accept".intern();
    public static final String XINCLUDE_ATTR_ACCEPT_LANGUAGE = "accept-language".intern();
    public static final String XINCLUDE_INCLUDED = "[included]".intern();
    protected int fBufferSize = 2048;
    protected XPointerProcessor fXPtrProcessor = null;
    protected XMLLocatorWrapper fXIncludeLocator = new XMLLocatorWrapper();
    protected XIncludeMessageFormatter fXIncludeMessageFormatter = new XIncludeMessageFormatter();
    private boolean fFixupBaseURIs = true;
    private boolean fFixupLanguage = true;
    private boolean fNeedCopyFeatures = true;
    private int fDepth = 0;

    protected static class Notation {
        public Augmentations augmentations;
        public String baseURI;
        public String expandedSystemId;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f481name;
        public String publicId;
        public String systemId;

        protected Notation() {
        }

        private boolean isEqual(String str, String str2) {
            return str == str2 || (str != null && str.equals(str2));
        }

        public boolean equals(Object obj) {
            if (obj != null && (obj instanceof Notation)) {
                return this.f481name.equals(((Notation) obj).f481name);
            }
            return false;
        }

        public boolean isDuplicate(Object obj) {
            if (obj == null || !(obj instanceof Notation)) {
                return false;
            }
            Notation notation = (Notation) obj;
            return this.f481name.equals(notation.f481name) && isEqual(this.publicId, notation.publicId) && isEqual(this.expandedSystemId, notation.expandedSystemId);
        }
    }

    protected static class UnparsedEntity {
        public Augmentations augmentations;
        public String baseURI;
        public String expandedSystemId;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f482name;
        public String notation;
        public String publicId;
        public String systemId;

        protected UnparsedEntity() {
        }

        private boolean isEqual(String str, String str2) {
            return str == str2 || (str != null && str.equals(str2));
        }

        public boolean equals(Object obj) {
            if (obj != null && (obj instanceof UnparsedEntity)) {
                return this.f482name.equals(((UnparsedEntity) obj).f482name);
            }
            return false;
        }

        public boolean isDuplicate(Object obj) {
            if (obj == null || !(obj instanceof UnparsedEntity)) {
                return false;
            }
            UnparsedEntity unparsedEntity = (UnparsedEntity) obj;
            return this.f482name.equals(unparsedEntity.f482name) && isEqual(this.publicId, unparsedEntity.publicId) && isEqual(this.expandedSystemId, unparsedEntity.expandedSystemId) && isEqual(this.notation, unparsedEntity.notation);
        }
    }

    static {
        String strIntern = ApkUtil.DEF_MODULE_NAME.intern();
        XINCLUDE_BASE = strIntern;
        XML_BASE_QNAME = new QName(XMLSymbols.PREFIX_XML, strIntern, (XMLSymbols.PREFIX_XML + ":" + strIntern).intern(), NamespaceContext.XML_URI);
        String strIntern2 = "lang".intern();
        XINCLUDE_LANG = strIntern2;
        XML_LANG_QNAME = new QName(XMLSymbols.PREFIX_XML, strIntern2, (XMLSymbols.PREFIX_XML + ":" + strIntern2).intern(), NamespaceContext.XML_URI);
        NEW_NS_ATTR_QNAME = new QName(XMLSymbols.PREFIX_XMLNS, "", XMLSymbols.PREFIX_XMLNS + ":", NamespaceContext.XMLNS_URI);
        RECOGNIZED_FEATURES = new String[]{ALLOW_UE_AND_NOTATION_EVENTS, XINCLUDE_FIXUP_BASE_URIS, XINCLUDE_FIXUP_LANGUAGE};
        FEATURE_DEFAULTS = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
        RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-resolver", SECURITY_MANAGER, BUFFER_SIZE};
        PROPERTY_DEFAULTS = new Object[]{null, null, null, new Integer(2048)};
        gNeedEscaping = new boolean[128];
        gAfterEscaping1 = new char[128];
        gAfterEscaping2 = new char[128];
        gHexChs = new char[]{ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'A', Util.C_BYTE, Util.C_CHAR, Util.C_DOUBLE, 'E', Util.C_FLOAT};
        char[] cArr = {StringUtil.SPACE, '<', '>', '\"', '{', '}', '|', '\\', Util.C_EXCEPTION_START, '`'};
        for (int i = 0; i < 10; i++) {
            char c = cArr[i];
            gNeedEscaping[c] = true;
            char[] cArr2 = gAfterEscaping1;
            char[] cArr3 = gHexChs;
            cArr2[c] = cArr3[c >> 4];
            gAfterEscaping2[c] = cArr3[c & 15];
        }
    }

    public XIncludeHandler() {
        boolean[] zArr = new boolean[8];
        this.fSawInclude = zArr;
        boolean[] zArr2 = new boolean[8];
        this.fSawFallback = zArr2;
        int[] iArr = new int[8];
        this.fState = iArr;
        zArr2[0] = false;
        zArr[0] = false;
        iArr[0] = 1;
        this.fNotations = new ArrayList();
        this.fUnparsedEntities = new ArrayList();
        this.fBaseURIScope = new IntStack();
        this.fBaseURI = new Stack();
        this.fLiteralSystemID = new Stack();
        this.fExpandedSystemID = new Stack();
        this.fCurrentBaseURI = new XMLResourceIdentifierImpl();
        this.fLanguageScope = new IntStack();
        this.fLanguageStack = new Stack();
        this.fCurrentLanguage = null;
    }

    private void checkMultipleRootElements() {
        if (getRootElementProcessed()) {
            reportFatalError("MultipleRootElements");
        }
        setRootElementProcessed(true);
    }

    private void checkWhitespace(XMLString xMLString) {
        int i = xMLString.offset + xMLString.length;
        for (int i2 = xMLString.offset; i2 < i; i2++) {
            if (!XMLChar.isSpace(xMLString.ch[i2])) {
                reportFatalError("ContentIllegalAtTopLevel");
                return;
            }
        }
    }

    private void copyFeatures1(Enumeration enumeration, String str, XMLComponentManager xMLComponentManager, ParserConfigurationSettings parserConfigurationSettings) {
        while (enumeration.hasMoreElements()) {
            String str2 = str + ((String) enumeration.nextElement());
            parserConfigurationSettings.addRecognizedFeatures(new String[]{str2});
            try {
                parserConfigurationSettings.setFeature(str2, xMLComponentManager.getFeature(str2));
            } catch (XMLConfigurationException unused) {
            }
        }
    }

    private void copyFeatures1(Enumeration enumeration, String str, XMLComponentManager xMLComponentManager, XMLParserConfiguration xMLParserConfiguration) {
        while (enumeration.hasMoreElements()) {
            String str2 = str + ((String) enumeration.nextElement());
            try {
                xMLParserConfiguration.setFeature(str2, xMLComponentManager.getFeature(str2));
            } catch (XMLConfigurationException unused) {
            }
        }
    }

    private XMLInputSource createInputSource(String str, String str2, String str3, String str4, String str5) {
        HTTPInputSource hTTPInputSource = new HTTPInputSource(str, str2, str3);
        if (str4 != null && str4.length() > 0) {
            hTTPInputSource.setHTTPRequestProperty("Accept", str4);
        }
        if (str5 != null && str5.length() > 0) {
            hTTPInputSource.setHTTPRequestProperty("Accept-Language", str5);
        }
        return hTTPInputSource;
    }

    private String escapeHref(String str) {
        char c;
        int iSupplemental;
        char cCharAt;
        char c2;
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length * 3);
        int i = 0;
        while (i < length && (cCharAt = str.charAt(i)) <= '~') {
            if (cCharAt < ' ') {
                return str;
            }
            if (gNeedEscaping[cCharAt]) {
                stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                stringBuffer.append(gAfterEscaping1[cCharAt]);
                c2 = gAfterEscaping2[cCharAt];
            } else {
                c2 = cCharAt;
            }
            stringBuffer.append(c2);
            i++;
        }
        if (i < length) {
            int i2 = i;
            while (i2 < length) {
                char cCharAt2 = str.charAt(i2);
                if ((cCharAt2 < ' ' || cCharAt2 > '~') && ((cCharAt2 < 160 || cCharAt2 > 55295) && ((cCharAt2 < 63744 || cCharAt2 > 64975) && (cCharAt2 < 65008 || cCharAt2 > 65519)))) {
                    if (XMLChar.isHighSurrogate(cCharAt2) && (i2 = i2 + 1) < length) {
                        char cCharAt3 = str.charAt(i2);
                        if (!XMLChar.isLowSurrogate(cCharAt3) || (iSupplemental = XMLChar.supplemental(cCharAt2, cCharAt3)) >= 983040 || (iSupplemental & 65535) > 65533) {
                        }
                    }
                    return str;
                }
                i2++;
            }
            try {
                byte[] bytes = str.substring(i).getBytes("UTF-8");
                int length2 = bytes.length;
                for (byte b : bytes) {
                    if (b < 0) {
                        int i3 = b + 256;
                        stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        char[] cArr = gHexChs;
                        stringBuffer.append(cArr[i3 >> 4]);
                        c = cArr[i3 & 15];
                    } else if (gNeedEscaping[b]) {
                        stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        stringBuffer.append(gAfterEscaping1[b]);
                        c = gAfterEscaping2[b];
                    } else {
                        c = (char) b;
                    }
                    stringBuffer.append(c);
                }
                length = length2;
            } catch (UnsupportedEncodingException unused) {
                return str;
            }
        }
        return stringBuffer.length() != length ? stringBuffer.toString() : str;
    }

    private String getIncludeParentBaseURI() {
        int includeParentDepth = getIncludeParentDepth();
        return (isRootDocument() || includeParentDepth != 0) ? getBaseURI(includeParentDepth) : this.fParentXIncludeHandler.getIncludeParentBaseURI();
    }

    private int getIncludeParentDepth() {
        for (int i = this.fDepth - 1; i >= 0; i--) {
            if (!getSawInclude(i) && !getSawFallback(i)) {
                return i;
            }
        }
        return 0;
    }

    private String getIncludeParentLanguage() {
        int includeParentDepth = getIncludeParentDepth();
        return (isRootDocument() || includeParentDepth != 0) ? getLanguage(includeParentDepth) : this.fParentXIncludeHandler.getIncludeParentLanguage();
    }

    private int getResultDepth() {
        return this.fResultDepth;
    }

    private boolean getRootElementProcessed() {
        return isRootDocument() ? this.fSeenRootElement : this.fParentXIncludeHandler.getRootElementProcessed();
    }

    private boolean isEqual(String str, String str2) {
        return str == str2 || (str != null && str.equals(str2));
    }

    private boolean isValidInHTTPHeader(String str) {
        for (int length = str.length() - 1; length >= 0; length--) {
            char cCharAt = str.charAt(length);
            if (cCharAt < ' ' || cCharAt > '~') {
                return false;
            }
        }
        return true;
    }

    private void reportError(String str, Object[] objArr, short s, Exception exc) {
        XMLErrorReporter xMLErrorReporter = this.fErrorReporter;
        if (xMLErrorReporter != null) {
            xMLErrorReporter.reportError(XIncludeMessageFormatter.XINCLUDE_DOMAIN, str, objArr, s, exc);
        }
    }

    private int scopeOfBaseURI(int i) {
        for (int size = this.fBaseURIScope.size() - 1; size >= 0; size--) {
            if (this.fBaseURIScope.elementAt(size) <= i) {
                return size;
            }
        }
        return -1;
    }

    private int scopeOfLanguage(int i) {
        for (int size = this.fLanguageScope.size() - 1; size >= 0; size--) {
            if (this.fLanguageScope.elementAt(size) <= i) {
                return size;
            }
        }
        return -1;
    }

    private void setErrorReporter(XMLErrorReporter xMLErrorReporter) {
        this.fErrorReporter = xMLErrorReporter;
        if (xMLErrorReporter != null) {
            xMLErrorReporter.putMessageFormatter(XIncludeMessageFormatter.XINCLUDE_DOMAIN, this.fXIncludeMessageFormatter);
            XMLLocator xMLLocator = this.fDocLocation;
            if (xMLLocator != null) {
                this.fErrorReporter.setDocumentLocator(xMLLocator);
            }
        }
    }

    private void setRootElementProcessed(boolean z) {
        if (isRootDocument()) {
            this.fSeenRootElement = z;
        } else {
            this.fParentXIncludeHandler.setRootElementProcessed(z);
        }
    }

    protected void addNotation(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) {
        Notation notation = new Notation();
        notation.f481name = str;
        notation.systemId = xMLResourceIdentifier.getLiteralSystemId();
        notation.publicId = xMLResourceIdentifier.getPublicId();
        notation.baseURI = xMLResourceIdentifier.getBaseSystemId();
        notation.expandedSystemId = xMLResourceIdentifier.getExpandedSystemId();
        notation.augmentations = augmentations;
        this.fNotations.add(notation);
    }

    protected void addUnparsedEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) {
        UnparsedEntity unparsedEntity = new UnparsedEntity();
        unparsedEntity.f482name = str;
        unparsedEntity.systemId = xMLResourceIdentifier.getLiteralSystemId();
        unparsedEntity.publicId = xMLResourceIdentifier.getPublicId();
        unparsedEntity.baseURI = xMLResourceIdentifier.getBaseSystemId();
        unparsedEntity.expandedSystemId = xMLResourceIdentifier.getExpandedSystemId();
        unparsedEntity.notation = str2;
        unparsedEntity.augmentations = augmentations;
        this.fUnparsedEntities.add(unparsedEntity);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void attributeDecl(String str, String str2, String str3, String[] strArr, String str4, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.attributeDecl(str, str2, str3, strArr, str4, xMLString, xMLString2, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (getState() == 1) {
            if (this.fResultDepth == 0) {
                checkWhitespace(xMLString);
            } else if (this.fDocumentHandler != null) {
                this.fDepth++;
                this.fDocumentHandler.characters(xMLString, modifyAugmentations(augmentations));
                this.fDepth--;
            }
        }
    }

    protected void checkAndSendNotation(Notation notation) {
        XMLDTDHandler xMLDTDHandler;
        if (!isRootDocument()) {
            this.fParentXIncludeHandler.checkAndSendNotation(notation);
            return;
        }
        int iIndexOf = this.fNotations.indexOf(notation);
        if (iIndexOf != -1) {
            if (notation.isDuplicate((Notation) this.fNotations.get(iIndexOf))) {
                return;
            }
            reportFatalError("NonDuplicateNotation", new Object[]{notation.f481name});
        } else {
            XMLResourceIdentifierImpl xMLResourceIdentifierImpl = new XMLResourceIdentifierImpl(notation.publicId, notation.systemId, notation.baseURI, notation.expandedSystemId);
            addNotation(notation.f481name, xMLResourceIdentifierImpl, notation.augmentations);
            if (!this.fSendUEAndNotationEvents || (xMLDTDHandler = this.fDTDHandler) == null) {
                return;
            }
            xMLDTDHandler.notationDecl(notation.f481name, xMLResourceIdentifierImpl, notation.augmentations);
        }
    }

    protected void checkAndSendUnparsedEntity(UnparsedEntity unparsedEntity) {
        XMLDTDHandler xMLDTDHandler;
        if (!isRootDocument()) {
            this.fParentXIncludeHandler.checkAndSendUnparsedEntity(unparsedEntity);
            return;
        }
        int iIndexOf = this.fUnparsedEntities.indexOf(unparsedEntity);
        if (iIndexOf != -1) {
            if (unparsedEntity.isDuplicate((UnparsedEntity) this.fUnparsedEntities.get(iIndexOf))) {
                return;
            }
            reportFatalError("NonDuplicateUnparsedEntity", new Object[]{unparsedEntity.f482name});
        } else {
            XMLResourceIdentifierImpl xMLResourceIdentifierImpl = new XMLResourceIdentifierImpl(unparsedEntity.publicId, unparsedEntity.systemId, unparsedEntity.baseURI, unparsedEntity.expandedSystemId);
            addUnparsedEntity(unparsedEntity.f482name, xMLResourceIdentifierImpl, unparsedEntity.notation, unparsedEntity.augmentations);
            if (!this.fSendUEAndNotationEvents || (xMLDTDHandler = this.fDTDHandler) == null) {
                return;
            }
            xMLDTDHandler.unparsedEntityDecl(unparsedEntity.f482name, xMLResourceIdentifierImpl, unparsedEntity.notation, unparsedEntity.augmentations);
        }
    }

    protected void checkNotation(String str) {
        Notation notation = new Notation();
        notation.f481name = str;
        int iIndexOf = this.fNotations.indexOf(notation);
        if (iIndexOf != -1) {
            checkAndSendNotation((Notation) this.fNotations.get(iIndexOf));
        }
    }

    protected void checkUnparsedEntity(String str) {
        UnparsedEntity unparsedEntity = new UnparsedEntity();
        unparsedEntity.f482name = str;
        int iIndexOf = this.fUnparsedEntities.indexOf(unparsedEntity);
        if (iIndexOf != -1) {
            UnparsedEntity unparsedEntity2 = (UnparsedEntity) this.fUnparsedEntities.get(iIndexOf);
            checkNotation(unparsedEntity2.notation);
            checkAndSendUnparsedEntity(unparsedEntity2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fInDTD) {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.comment(xMLString, augmentations);
                return;
            }
            return;
        }
        if (this.fDocumentHandler == null || getState() != 1) {
            return;
        }
        this.fDepth++;
        this.fDocumentHandler.comment(xMLString, modifyAugmentations(augmentations));
        this.fDepth--;
    }

    protected void copyFeatures(XMLComponentManager xMLComponentManager, ParserConfigurationSettings parserConfigurationSettings) {
        copyFeatures1(Constants.getXercesFeatures(), Constants.XERCES_FEATURE_PREFIX, xMLComponentManager, parserConfigurationSettings);
        copyFeatures1(Constants.getSAXFeatures(), Constants.SAX_FEATURE_PREFIX, xMLComponentManager, parserConfigurationSettings);
    }

    protected void copyFeatures(XMLComponentManager xMLComponentManager, XMLParserConfiguration xMLParserConfiguration) {
        copyFeatures1(Constants.getXercesFeatures(), Constants.XERCES_FEATURE_PREFIX, xMLComponentManager, xMLParserConfiguration);
        copyFeatures1(Constants.getSAXFeatures(), Constants.SAX_FEATURE_PREFIX, xMLComponentManager, xMLParserConfiguration);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler;
        if (!isRootDocument() || (xMLDocumentHandler = this.fDocumentHandler) == null) {
            return;
        }
        xMLDocumentHandler.doctypeDecl(str, str2, str3, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void elementDecl(String str, String str2, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.elementDecl(str, str2, augmentations);
        }
    }

    /* JADX WARN: Code duplicated, block: B:43:0x009b  */
    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        int i = this.fDepth;
        this.fDepth = i + 1;
        int state = getState(i);
        if (state == 3 && getState(this.fDepth - 2) == 3) {
            setState(2);
        } else {
            setState(state);
        }
        processXMLBaseAttributes(xMLAttributes);
        if (this.fFixupLanguage) {
            processXMLLangAttributes(xMLAttributes);
        }
        if (isIncludeElement(qName)) {
            if (handleIncludeElement(xMLAttributes)) {
                setState(2);
            } else {
                reportFatalError("NoFallback");
            }
        } else if (isFallbackElement(qName)) {
            handleFallbackElement();
        } else if (hasXIncludeNamespace(qName)) {
            if (getSawInclude(this.fDepth - 1)) {
                reportFatalError("IncludeChild", new Object[]{qName.rawname});
            }
            if (getSawFallback(this.fDepth - 1)) {
                reportFatalError("FallbackChild", new Object[]{qName.rawname});
            }
            if (getState() == 1) {
                if (this.fResultDepth == 0) {
                    checkMultipleRootElements();
                }
                if (this.fDocumentHandler != null) {
                    this.fDocumentHandler.emptyElement(qName, processAttributes(xMLAttributes), modifyAugmentations(augmentations));
                }
            }
        } else if (getState() == 1) {
            if (this.fResultDepth == 0) {
                checkMultipleRootElements();
            }
            if (this.fDocumentHandler != null) {
                this.fDocumentHandler.emptyElement(qName, processAttributes(xMLAttributes), modifyAugmentations(augmentations));
            }
        }
        setSawFallback(this.fDepth + 1, false);
        setSawInclude(this.fDepth, false);
        if (this.fBaseURIScope.size() > 0 && this.fDepth == this.fBaseURIScope.peek()) {
            restoreBaseURI();
        }
        this.fDepth--;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endAttlist(Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.endAttlist(augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
        if (this.fDocumentHandler == null || getState() != 1 || this.fResultDepth == 0) {
            return;
        }
        this.fDocumentHandler.endCDATA(augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endConditional(Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.endConditional(augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endDTD(Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.endDTD(augmentations);
        }
        this.fInDTD = false;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
        if (isRootDocument()) {
            if (!this.fSeenRootElement) {
                reportFatalError("RootElementRequired");
            }
            XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
            if (xMLDocumentHandler != null) {
                xMLDocumentHandler.endDocument(augmentations);
            }
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        if (isIncludeElement(qName) && getState() == 3 && !getSawFallback(this.fDepth + 1)) {
            reportFatalError("NoFallback");
        }
        if (isFallbackElement(qName)) {
            if (getState() == 1) {
                setState(2);
            }
        } else if (getState() == 1) {
            this.fResultDepth--;
            XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
            if (xMLDocumentHandler != null) {
                xMLDocumentHandler.endElement(qName, augmentations);
            }
        }
        setSawFallback(this.fDepth + 1, false);
        setSawInclude(this.fDepth, false);
        if (this.fBaseURIScope.size() > 0 && this.fDepth == this.fBaseURIScope.peek()) {
            restoreBaseURI();
        }
        if (this.fLanguageScope.size() > 0 && this.fDepth == this.fLanguageScope.peek()) {
            this.fCurrentLanguage = restoreLanguage();
        }
        this.fDepth--;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endExternalSubset(Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.endExternalSubset(augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
        if (this.fDocumentHandler == null || getState() != 1 || this.fResultDepth == 0) {
            return;
        }
        this.fDocumentHandler.endGeneralEntity(str, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endParameterEntity(String str, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.endParameterEntity(str, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void externalEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.externalEntityDecl(str, xMLResourceIdentifier, augmentations);
        }
    }

    public String getBaseURI(int i) {
        return (String) this.fExpandedSystemID.elementAt(scopeOfBaseURI(i));
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDSource
    public XMLDTDHandler getDTDHandler() {
        return this.fDTDHandler;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public XMLDTDSource getDTDSource() {
        return this.fDTDSource;
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentSource
    public XMLDocumentHandler getDocumentHandler() {
        return this.fDocumentHandler;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public XMLDocumentSource getDocumentSource() {
        return this.fDocumentSource;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public Boolean getFeatureDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_FEATURES;
            if (i >= strArr.length) {
                return null;
            }
            if (strArr[i].equals(str)) {
                return FEATURE_DEFAULTS[i];
            }
            i++;
        }
    }

    public String getLanguage(int i) {
        return (String) this.fLanguageStack.elementAt(scopeOfLanguage(i));
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public Object getPropertyDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_PROPERTIES;
            if (i >= strArr.length) {
                return null;
            }
            if (strArr[i].equals(str)) {
                return PROPERTY_DEFAULTS[i];
            }
            i++;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedFeatures() {
        return (String[]) RECOGNIZED_FEATURES.clone();
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedProperties() {
        return (String[]) RECOGNIZED_PROPERTIES.clone();
    }

    protected String getRelativeBaseURI() throws URI.MalformedURIException {
        int includeParentDepth = getIncludeParentDepth();
        String relativeURI = getRelativeURI(includeParentDepth);
        if (isRootDocument()) {
            return relativeURI;
        }
        if (relativeURI.length() == 0) {
            relativeURI = this.fCurrentBaseURI.getLiteralSystemId();
        }
        if (includeParentDepth != 0) {
            return relativeURI;
        }
        if (this.fParentRelativeURI == null) {
            this.fParentRelativeURI = this.fParentXIncludeHandler.getRelativeBaseURI();
        }
        if (this.fParentRelativeURI.length() == 0) {
            return relativeURI;
        }
        URI uri = new URI(this.fParentRelativeURI, true);
        URI uri2 = new URI(uri, relativeURI);
        if (!isEqual(uri.getScheme(), uri2.getScheme())) {
            return relativeURI;
        }
        if (!isEqual(uri.getAuthority(), uri2.getAuthority())) {
            return uri2.getSchemeSpecificPart();
        }
        String path = uri2.getPath();
        String queryString = uri2.getQueryString();
        String fragment = uri2.getFragment();
        if (queryString == null && fragment == null) {
            return path;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (path != null) {
            stringBuffer.append(path);
        }
        if (queryString != null) {
            stringBuffer.append(MangleConstant.Q_MARK);
            stringBuffer.append(queryString);
        }
        if (fragment != null) {
            stringBuffer.append('#');
            stringBuffer.append(fragment);
        }
        return stringBuffer.toString();
    }

    public String getRelativeURI(int i) throws URI.MalformedURIException {
        int iScopeOfBaseURI = scopeOfBaseURI(i);
        int i2 = iScopeOfBaseURI + 1;
        if (i2 == this.fBaseURIScope.size()) {
            return "";
        }
        URI uri = new URI("file", (String) this.fLiteralSystemID.elementAt(i2));
        int i3 = iScopeOfBaseURI + 2;
        while (i3 < this.fBaseURIScope.size()) {
            URI uri2 = new URI(uri, (String) this.fLiteralSystemID.elementAt(i3));
            i3++;
            uri = uri2;
        }
        return uri.getPath();
    }

    protected boolean getSawFallback(int i) {
        boolean[] zArr = this.fSawFallback;
        if (i >= zArr.length) {
            return false;
        }
        return zArr[i];
    }

    protected boolean getSawInclude(int i) {
        boolean[] zArr = this.fSawInclude;
        if (i >= zArr.length) {
            return false;
        }
        return zArr[i];
    }

    protected int getState() {
        return this.fState[this.fDepth];
    }

    protected int getState(int i) {
        return this.fState[i];
    }

    protected void handleFallbackElement() {
        if (!getSawInclude(this.fDepth - 1)) {
            if (getState() == 2) {
                return;
            } else {
                reportFatalError("FallbackParent");
            }
        }
        setSawInclude(this.fDepth, false);
        this.fNamespaceContext.setContextInvalid();
        if (getSawFallback(this.fDepth)) {
            reportFatalError("MultipleFallbacks");
        } else {
            setSawFallback(this.fDepth, true);
        }
        if (getState() == 3) {
            setState(1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0214  */
    /* JADX WARN: Code duplicated, block: B:103:0x0217  */
    /* JADX WARN: Code duplicated, block: B:106:0x0220  */
    /* JADX WARN: Code duplicated, block: B:108:0x023e  */
    /* JADX WARN: Code duplicated, block: B:110:0x025e  */
    /* JADX WARN: Code duplicated, block: B:116:0x0275  */
    /* JADX WARN: Code duplicated, block: B:119:0x027d  */
    /* JADX WARN: Code duplicated, block: B:123:0x029d A[Catch: all -> 0x02cb, IOException -> 0x02cd, XNIException -> 0x02f9, TryCatch #7 {IOException -> 0x02cd, blocks: (B:121:0x0286, B:123:0x029d, B:125:0x02a4, B:127:0x02ac, B:129:0x02b0, B:131:0x02b6), top: B:224:0x0286, outer: #4 }] */
    /* JADX WARN: Code duplicated, block: B:129:0x02b0 A[Catch: all -> 0x02cb, IOException -> 0x02cd, XNIException -> 0x02f9, TryCatch #7 {IOException -> 0x02cd, blocks: (B:121:0x0286, B:123:0x029d, B:125:0x02a4, B:127:0x02ac, B:129:0x02b0, B:131:0x02b6), top: B:224:0x0286, outer: #4 }] */
    /* JADX WARN: Code duplicated, block: B:130:0x02b5  */
    /* JADX WARN: Code duplicated, block: B:154:0x031f  */
    /* JADX WARN: Code duplicated, block: B:156:0x0327  */
    /* JADX WARN: Code duplicated, block: B:159:0x0336 A[Catch: all -> 0x037b, IOException -> 0x037e, CharConversionException -> 0x03a8, MalformedByteSequenceException -> 0x03c9, TryCatch #14 {IOException -> 0x037e, blocks: (B:157:0x0330, B:159:0x0336, B:161:0x033a, B:163:0x0347, B:169:0x035d, B:162:0x0344, B:164:0x034a, B:166:0x034e, B:168:0x035b, B:167:0x0358), top: B:234:0x0330, outer: #13 }] */
    /* JADX WARN: Code duplicated, block: B:161:0x033a A[Catch: all -> 0x037b, IOException -> 0x037e, CharConversionException -> 0x03a8, MalformedByteSequenceException -> 0x03c9, TryCatch #14 {IOException -> 0x037e, blocks: (B:157:0x0330, B:159:0x0336, B:161:0x033a, B:163:0x0347, B:169:0x035d, B:162:0x0344, B:164:0x034a, B:166:0x034e, B:168:0x035b, B:167:0x0358), top: B:234:0x0330, outer: #13 }] */
    /* JADX WARN: Code duplicated, block: B:162:0x0344 A[Catch: all -> 0x037b, IOException -> 0x037e, CharConversionException -> 0x03a8, MalformedByteSequenceException -> 0x03c9, TryCatch #14 {IOException -> 0x037e, blocks: (B:157:0x0330, B:159:0x0336, B:161:0x033a, B:163:0x0347, B:169:0x035d, B:162:0x0344, B:164:0x034a, B:166:0x034e, B:168:0x035b, B:167:0x0358), top: B:234:0x0330, outer: #13 }] */
    /* JADX WARN: Code duplicated, block: B:164:0x034a A[Catch: all -> 0x037b, IOException -> 0x037e, CharConversionException -> 0x03a8, MalformedByteSequenceException -> 0x03c9, TryCatch #14 {IOException -> 0x037e, blocks: (B:157:0x0330, B:159:0x0336, B:161:0x033a, B:163:0x0347, B:169:0x035d, B:162:0x0344, B:164:0x034a, B:166:0x034e, B:168:0x035b, B:167:0x0358), top: B:234:0x0330, outer: #13 }] */
    /* JADX WARN: Code duplicated, block: B:166:0x034e A[Catch: all -> 0x037b, IOException -> 0x037e, CharConversionException -> 0x03a8, MalformedByteSequenceException -> 0x03c9, TryCatch #14 {IOException -> 0x037e, blocks: (B:157:0x0330, B:159:0x0336, B:161:0x033a, B:163:0x0347, B:169:0x035d, B:162:0x0344, B:164:0x034a, B:166:0x034e, B:168:0x035b, B:167:0x0358), top: B:234:0x0330, outer: #13 }] */
    /* JADX WARN: Code duplicated, block: B:167:0x0358 A[Catch: all -> 0x037b, IOException -> 0x037e, CharConversionException -> 0x03a8, MalformedByteSequenceException -> 0x03c9, TryCatch #14 {IOException -> 0x037e, blocks: (B:157:0x0330, B:159:0x0336, B:161:0x033a, B:163:0x0347, B:169:0x035d, B:162:0x0344, B:164:0x034a, B:166:0x034e, B:168:0x035b, B:167:0x0358), top: B:234:0x0330, outer: #13 }] */
    /* JADX WARN: Code duplicated, block: B:214:0x0406  */
    /* JADX WARN: Code duplicated, block: B:216:0x00f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:225:0x0368 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:49:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:67:0x0146  */
    /* JADX WARN: Code duplicated, block: B:69:0x0149  */
    /* JADX WARN: Code duplicated, block: B:73:0x015a  */
    /* JADX WARN: Code duplicated, block: B:76:0x0174  */
    /* JADX WARN: Code duplicated, block: B:83:0x0182  */
    /* JADX WARN: Code duplicated, block: B:84:0x0185  */
    /* JADX WARN: Code duplicated, block: B:87:0x0197  */
    /* JADX WARN: Code duplicated, block: B:90:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:93:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:96:0x01ea  */
    /* JADX WARN: Code duplicated, block: B:98:0x0204  */
    /* JADX WARN: Code duplicated, block: B:99:0x0207  */
    protected boolean handleIncludeElement(XMLAttributes xMLAttributes) throws XNIException {
        String str;
        String str2;
        String str3;
        XMLInputSource xMLInputSourceResolveEntity;
        XIncludeTextReader xIncludeTextReader;
        XIncludeTextReader xIncludeTextReader2;
        XIncludeTextReader xIncludeTextReader3;
        XMLErrorReporter xMLErrorReporter;
        XMLErrorReporter xMLErrorReporter2;
        Locale locale;
        String str4;
        XMLParserConfiguration xMLParserConfiguration;
        SymbolTable symbolTable;
        XMLErrorReporter xMLErrorReporter3;
        XMLEntityResolver xMLEntityResolver;
        XMLParserConfiguration xMLParserConfiguration2;
        Boolean bool;
        Boolean bool2;
        XMLErrorReporter xMLErrorReporter4;
        if (getSawInclude(this.fDepth - 1)) {
            reportFatalError("IncludeChild", new Object[]{XINCLUDE_INCLUDE});
        }
        if (getState() == 2) {
            return true;
        }
        setSawInclude(this.fDepth, true);
        this.fNamespaceContext.setContextInvalid();
        String value = xMLAttributes.getValue(XINCLUDE_ATTR_HREF);
        String value2 = xMLAttributes.getValue(XINCLUDE_ATTR_PARSE);
        String value3 = xMLAttributes.getValue(XPOINTER);
        String value4 = xMLAttributes.getValue(XINCLUDE_ATTR_ACCEPT);
        String value5 = xMLAttributes.getValue(XINCLUDE_ATTR_ACCEPT_LANGUAGE);
        if (value2 == null) {
            value2 = XINCLUDE_PARSE_XML;
        }
        String str5 = value2;
        if (value == null) {
            value = XMLSymbols.EMPTY_STRING;
        }
        XIncludeTextReader xIncludeTextReader4 = null;
        if (value.length() == 0 && XINCLUDE_PARSE_XML.equals(str5)) {
            if (value3 != null) {
                XMLErrorReporter xMLErrorReporter5 = this.fErrorReporter;
                reportResourceError("XMLResourceError", new Object[]{value, this.fXIncludeMessageFormatter.formatMessage(xMLErrorReporter5 != null ? xMLErrorReporter5.getLocale() : null, "XPointerStreamability", null)});
                return false;
            }
            reportFatalError("XpointerMissing");
        }
        try {
            if (new URI(value, true).getFragment() != null) {
                reportFatalError("HrefFragmentIdentifierIllegal", new Object[]{value});
            }
        } catch (URI.MalformedURIException unused) {
            String strEscapeHref = escapeHref(value);
            if (value != strEscapeHref) {
                try {
                    if (new URI(strEscapeHref, true).getFragment() != null) {
                        reportFatalError("HrefFragmentIdentifierIllegal", new Object[]{strEscapeHref});
                    }
                } catch (URI.MalformedURIException unused2) {
                    reportFatalError("HrefSyntacticallyInvalid", new Object[]{strEscapeHref});
                }
                str = strEscapeHref;
            } else {
                reportFatalError("HrefSyntacticallyInvalid", new Object[]{value});
            }
            if (value4 != null) {
                str2 = value4;
            } else {
                str2 = value4;
            }
            if (value5 != null) {
                str3 = value5;
            } else {
                str3 = value5;
            }
            if (this.fEntityResolver != null) {
                try {
                    xMLInputSourceResolveEntity = this.fEntityResolver.resolveEntity(new XMLResourceIdentifierImpl(null, str, this.fCurrentBaseURI.getExpandedSystemId(), XMLEntityManager.expandSystemId(str, this.fCurrentBaseURI.getExpandedSystemId(), false)));
                    if (xMLInputSourceResolveEntity != null) {
                        xMLInputSourceResolveEntity = createInputSource(xMLInputSourceResolveEntity.getPublicId(), xMLInputSourceResolveEntity.getSystemId(), xMLInputSourceResolveEntity.getBaseSystemId(), str2, str3);
                    }
                } catch (IOException e) {
                    reportResourceError("XMLResourceError", new Object[]{str, e.getMessage()}, e);
                    return false;
                }
            } else {
                xMLInputSourceResolveEntity = null;
            }
            if (xMLInputSourceResolveEntity == null) {
                if (str2 == null) {
                    xMLInputSourceResolveEntity = createInputSource(null, str, this.fCurrentBaseURI.getExpandedSystemId(), str2, str3);
                } else {
                    xMLInputSourceResolveEntity = createInputSource(null, str, this.fCurrentBaseURI.getExpandedSystemId(), str2, str3);
                }
            }
            if (str5.equals(XINCLUDE_PARSE_XML)) {
                if (value3 == null) {
                    if (value3 != null) {
                        str4 = "org.apache.xerces.parsers.XPointerParserConfiguration";
                    } else {
                        str4 = XINCLUDE_DEFAULT_CONFIGURATION;
                    }
                    xMLParserConfiguration = (XMLParserConfiguration) ObjectFactory.newInstance(str4, ObjectFactory.findClassLoader(), true);
                    this.fChildConfig = xMLParserConfiguration;
                    symbolTable = this.fSymbolTable;
                    if (symbolTable != null) {
                        xMLParserConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
                    }
                    xMLErrorReporter3 = this.fErrorReporter;
                    if (xMLErrorReporter3 != null) {
                        this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter3);
                    }
                    xMLEntityResolver = this.fEntityResolver;
                    if (xMLEntityResolver != null) {
                        this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/entity-resolver", xMLEntityResolver);
                    }
                    this.fChildConfig.setProperty(SECURITY_MANAGER, this.fSecurityManager);
                    this.fChildConfig.setProperty(BUFFER_SIZE, new Integer(this.fBufferSize));
                    this.fNeedCopyFeatures = true;
                    this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/namespace-context", this.fNamespaceContext);
                    this.fChildConfig.setFeature(XINCLUDE_FIXUP_BASE_URIS, this.fFixupBaseURIs);
                    this.fChildConfig.setFeature(XINCLUDE_FIXUP_LANGUAGE, this.fFixupLanguage);
                    xMLParserConfiguration2 = this.fChildConfig;
                    if (value3 != null) {
                        XPointerHandler xPointerHandler = (XPointerHandler) xMLParserConfiguration2.getProperty("http://apache.org/xml/properties/internal/xpointer-handler");
                        this.fXPtrProcessor = xPointerHandler;
                        xPointerHandler.setProperty("http://apache.org/xml/properties/internal/namespace-context", this.fNamespaceContext);
                        XPointerHandler xPointerHandler2 = (XPointerHandler) this.fXPtrProcessor;
                        if (this.fFixupBaseURIs) {
                            bool = Boolean.TRUE;
                        } else {
                            bool = Boolean.FALSE;
                        }
                        xPointerHandler2.setProperty(XINCLUDE_FIXUP_BASE_URIS, bool);
                        XPointerHandler xPointerHandler3 = (XPointerHandler) this.fXPtrProcessor;
                        if (this.fFixupLanguage) {
                            bool2 = Boolean.TRUE;
                        } else {
                            bool2 = Boolean.FALSE;
                        }
                        xPointerHandler3.setProperty(XINCLUDE_FIXUP_LANGUAGE, bool2);
                        xMLErrorReporter4 = this.fErrorReporter;
                        if (xMLErrorReporter4 != null) {
                            ((XPointerHandler) this.fXPtrProcessor).setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter4);
                        }
                        xPointerHandler.setParent(this);
                        xPointerHandler.setHref(str);
                        xPointerHandler.setXIncludeLocator(this.fXIncludeLocator);
                        xPointerHandler.setDocumentHandler(getDocumentHandler());
                        this.fXPointerChildConfig = this.fChildConfig;
                    } else {
                        XIncludeHandler xIncludeHandler = (XIncludeHandler) xMLParserConfiguration2.getProperty("http://apache.org/xml/properties/internal/xinclude-handler");
                        xIncludeHandler.setParent(this);
                        xIncludeHandler.setHref(str);
                        xIncludeHandler.setXIncludeLocator(this.fXIncludeLocator);
                        xIncludeHandler.setDocumentHandler(getDocumentHandler());
                        this.fXIncludeChildConfig = this.fChildConfig;
                    }
                } else {
                    if (value3 != null) {
                        str4 = "org.apache.xerces.parsers.XPointerParserConfiguration";
                    } else {
                        str4 = XINCLUDE_DEFAULT_CONFIGURATION;
                    }
                    xMLParserConfiguration = (XMLParserConfiguration) ObjectFactory.newInstance(str4, ObjectFactory.findClassLoader(), true);
                    this.fChildConfig = xMLParserConfiguration;
                    symbolTable = this.fSymbolTable;
                    if (symbolTable != null) {
                        xMLParserConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
                    }
                    xMLErrorReporter3 = this.fErrorReporter;
                    if (xMLErrorReporter3 != null) {
                        this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter3);
                    }
                    xMLEntityResolver = this.fEntityResolver;
                    if (xMLEntityResolver != null) {
                        this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/entity-resolver", xMLEntityResolver);
                    }
                    this.fChildConfig.setProperty(SECURITY_MANAGER, this.fSecurityManager);
                    this.fChildConfig.setProperty(BUFFER_SIZE, new Integer(this.fBufferSize));
                    this.fNeedCopyFeatures = true;
                    this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/namespace-context", this.fNamespaceContext);
                    this.fChildConfig.setFeature(XINCLUDE_FIXUP_BASE_URIS, this.fFixupBaseURIs);
                    this.fChildConfig.setFeature(XINCLUDE_FIXUP_LANGUAGE, this.fFixupLanguage);
                    xMLParserConfiguration2 = this.fChildConfig;
                    if (value3 != null) {
                        XPointerHandler xPointerHandler4 = (XPointerHandler) xMLParserConfiguration2.getProperty("http://apache.org/xml/properties/internal/xpointer-handler");
                        this.fXPtrProcessor = xPointerHandler4;
                        xPointerHandler4.setProperty("http://apache.org/xml/properties/internal/namespace-context", this.fNamespaceContext);
                        XPointerHandler xPointerHandler5 = (XPointerHandler) this.fXPtrProcessor;
                        if (this.fFixupBaseURIs) {
                            bool = Boolean.TRUE;
                        } else {
                            bool = Boolean.FALSE;
                        }
                        xPointerHandler5.setProperty(XINCLUDE_FIXUP_BASE_URIS, bool);
                        XPointerHandler xPointerHandler6 = (XPointerHandler) this.fXPtrProcessor;
                        if (this.fFixupLanguage) {
                            bool2 = Boolean.TRUE;
                        } else {
                            bool2 = Boolean.FALSE;
                        }
                        xPointerHandler6.setProperty(XINCLUDE_FIXUP_LANGUAGE, bool2);
                        xMLErrorReporter4 = this.fErrorReporter;
                        if (xMLErrorReporter4 != null) {
                            ((XPointerHandler) this.fXPtrProcessor).setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter4);
                        }
                        xPointerHandler4.setParent(this);
                        xPointerHandler4.setHref(str);
                        xPointerHandler4.setXIncludeLocator(this.fXIncludeLocator);
                        xPointerHandler4.setDocumentHandler(getDocumentHandler());
                        this.fXPointerChildConfig = this.fChildConfig;
                    } else {
                        XIncludeHandler xIncludeHandler2 = (XIncludeHandler) xMLParserConfiguration2.getProperty("http://apache.org/xml/properties/internal/xinclude-handler");
                        xIncludeHandler2.setParent(this);
                        xIncludeHandler2.setHref(str);
                        xIncludeHandler2.setXIncludeLocator(this.fXIncludeLocator);
                        xIncludeHandler2.setDocumentHandler(getDocumentHandler());
                        this.fXIncludeChildConfig = this.fChildConfig;
                    }
                }
                if (value3 != null) {
                    this.fChildConfig = this.fXPointerChildConfig;
                    try {
                        this.fXPtrProcessor.parseXPointer(value3);
                    } catch (XNIException e2) {
                        reportResourceError("XMLResourceError", new Object[]{str, e2.getMessage()});
                        return false;
                    }
                } else {
                    this.fChildConfig = this.fXIncludeChildConfig;
                }
                if (this.fNeedCopyFeatures) {
                    copyFeatures(this.fSettings, this.fChildConfig);
                }
                this.fNeedCopyFeatures = false;
                try {
                    try {
                        try {
                            this.fHasIncludeReportedContent = false;
                            this.fNamespaceContext.pushScope();
                            this.fChildConfig.parse(xMLInputSourceResolveEntity);
                            this.fXIncludeLocator.setLocator(this.fDocLocation);
                            xMLErrorReporter = this.fErrorReporter;
                            if (xMLErrorReporter != null) {
                                xMLErrorReporter.setDocumentLocator(this.fDocLocation);
                            }
                            if (value3 != null) {
                                xMLErrorReporter2 = this.fErrorReporter;
                                if (xMLErrorReporter2 != null) {
                                    locale = xMLErrorReporter2.getLocale();
                                } else {
                                    locale = null;
                                }
                                reportResourceError("XMLResourceError", new Object[]{str, this.fXIncludeMessageFormatter.formatMessage(locale, "XPointerResolutionUnsuccessful", null)});
                                this.fNamespaceContext.popScope();
                                return false;
                            }
                        } catch (Throwable th) {
                            this.fNamespaceContext.popScope();
                            throw th;
                        }
                    } catch (IOException e3) {
                        this.fXIncludeLocator.setLocator(this.fDocLocation);
                        XMLErrorReporter xMLErrorReporter6 = this.fErrorReporter;
                        if (xMLErrorReporter6 != null) {
                            xMLErrorReporter6.setDocumentLocator(this.fDocLocation);
                        }
                        if (this.fHasIncludeReportedContent) {
                            throw new XNIException(e3);
                        }
                        reportResourceError("XMLResourceError", new Object[]{str, e3.getMessage()}, e3);
                        this.fNamespaceContext.popScope();
                        return false;
                    }
                } catch (XNIException unused3) {
                    this.fXIncludeLocator.setLocator(this.fDocLocation);
                    XMLErrorReporter xMLErrorReporter7 = this.fErrorReporter;
                    if (xMLErrorReporter7 != null) {
                        xMLErrorReporter7.setDocumentLocator(this.fDocLocation);
                    }
                    reportFatalError("XMLParseError", new Object[]{str});
                }
                this.fNamespaceContext.popScope();
            } else if (str5.equals(XINCLUDE_PARSE_TEXT)) {
                xMLInputSourceResolveEntity.setEncoding(xMLAttributes.getValue(XINCLUDE_ATTR_ENCODING));
                try {
                    try {
                        try {
                            this.fHasIncludeReportedContent = false;
                            if (this.fIsXML11) {
                                xIncludeTextReader = this.fXInclude11TextReader;
                                if (xIncludeTextReader == null) {
                                    this.fXInclude11TextReader = new XInclude11TextReader(xMLInputSourceResolveEntity, this, this.fBufferSize);
                                } else {
                                    xIncludeTextReader.setInputSource(xMLInputSourceResolveEntity);
                                }
                                xIncludeTextReader2 = this.fXInclude11TextReader;
                            } else {
                                xIncludeTextReader3 = this.fXInclude10TextReader;
                                if (xIncludeTextReader3 == null) {
                                    this.fXInclude10TextReader = new XIncludeTextReader(xMLInputSourceResolveEntity, this, this.fBufferSize);
                                } else {
                                    xIncludeTextReader3.setInputSource(xMLInputSourceResolveEntity);
                                }
                                xIncludeTextReader2 = this.fXInclude10TextReader;
                            }
                            xIncludeTextReader4 = xIncludeTextReader2;
                            xIncludeTextReader4.setErrorReporter(this.fErrorReporter);
                            xIncludeTextReader4.parse();
                            if (xIncludeTextReader4 != null) {
                                try {
                                    xIncludeTextReader4.close();
                                } catch (IOException e4) {
                                    reportResourceError("TextResourceError", new Object[]{str, e4.getMessage()}, e4);
                                    return false;
                                }
                            }
                        } catch (IOException e5) {
                            if (this.fHasIncludeReportedContent) {
                                throw new XNIException(e5);
                            }
                            reportResourceError("TextResourceError", new Object[]{str, e5.getMessage()}, e5);
                            if (xIncludeTextReader4 != null) {
                                try {
                                    xIncludeTextReader4.close();
                                } catch (IOException e6) {
                                    reportResourceError("TextResourceError", new Object[]{str, e6.getMessage()}, e6);
                                }
                            }
                            return false;
                        }
                    } catch (MalformedByteSequenceException e7) {
                        this.fErrorReporter.reportError(e7.getDomain(), e7.getKey(), e7.getArguments(), (short) 2, (Exception) e7);
                        if (xIncludeTextReader4 != null) {
                            try {
                                xIncludeTextReader4.close();
                            } catch (IOException e8) {
                                reportResourceError("TextResourceError", new Object[]{str, e8.getMessage()}, e8);
                                return false;
                            }
                        }
                    } catch (CharConversionException e9) {
                        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[]) null, (short) 2, (Exception) e9);
                        if (xIncludeTextReader4 != null) {
                            try {
                                xIncludeTextReader4.close();
                            } catch (IOException e10) {
                                reportResourceError("TextResourceError", new Object[]{str, e10.getMessage()}, e10);
                                return false;
                            }
                        }
                    }
                } catch (Throwable th2) {
                    if (xIncludeTextReader4 != null) {
                        try {
                            xIncludeTextReader4.close();
                        } catch (IOException e11) {
                            reportResourceError("TextResourceError", new Object[]{str, e11.getMessage()}, e11);
                            return false;
                        }
                    }
                    throw th2;
                }
            } else {
                reportFatalError("InvalidParseValue", new Object[]{str5});
            }
            return true;
        }
        str = value;
        if (value4 != null || isValidInHTTPHeader(value4)) {
            str2 = value4;
        } else {
            reportFatalError("AcceptMalformed", null);
            str2 = null;
        }
        if (value5 != null || isValidInHTTPHeader(value5)) {
            str3 = value5;
        } else {
            reportFatalError("AcceptLanguageMalformed", null);
            str3 = null;
        }
        if (this.fEntityResolver != null) {
            xMLInputSourceResolveEntity = this.fEntityResolver.resolveEntity(new XMLResourceIdentifierImpl(null, str, this.fCurrentBaseURI.getExpandedSystemId(), XMLEntityManager.expandSystemId(str, this.fCurrentBaseURI.getExpandedSystemId(), false)));
            if (xMLInputSourceResolveEntity != null && !(xMLInputSourceResolveEntity instanceof HTTPInputSource) && ((str2 != null || str3 != null) && xMLInputSourceResolveEntity.getCharacterStream() == null && xMLInputSourceResolveEntity.getByteStream() == null)) {
                xMLInputSourceResolveEntity = createInputSource(xMLInputSourceResolveEntity.getPublicId(), xMLInputSourceResolveEntity.getSystemId(), xMLInputSourceResolveEntity.getBaseSystemId(), str2, str3);
            }
        } else {
            xMLInputSourceResolveEntity = null;
        }
        if (xMLInputSourceResolveEntity == null) {
            if (str2 == null || str3 != null) {
                xMLInputSourceResolveEntity = createInputSource(null, str, this.fCurrentBaseURI.getExpandedSystemId(), str2, str3);
            } else {
                xMLInputSourceResolveEntity = new XMLInputSource(null, str, this.fCurrentBaseURI.getExpandedSystemId());
            }
        }
        if (str5.equals(XINCLUDE_PARSE_XML)) {
            if ((value3 == null && this.fXPointerChildConfig == null) || (value3 == null && this.fXIncludeChildConfig == null)) {
                if (value3 != null) {
                    str4 = "org.apache.xerces.parsers.XPointerParserConfiguration";
                } else {
                    str4 = XINCLUDE_DEFAULT_CONFIGURATION;
                }
                xMLParserConfiguration = (XMLParserConfiguration) ObjectFactory.newInstance(str4, ObjectFactory.findClassLoader(), true);
                this.fChildConfig = xMLParserConfiguration;
                symbolTable = this.fSymbolTable;
                if (symbolTable != null) {
                    xMLParserConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
                }
                xMLErrorReporter3 = this.fErrorReporter;
                if (xMLErrorReporter3 != null) {
                    this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter3);
                }
                xMLEntityResolver = this.fEntityResolver;
                if (xMLEntityResolver != null) {
                    this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/entity-resolver", xMLEntityResolver);
                }
                this.fChildConfig.setProperty(SECURITY_MANAGER, this.fSecurityManager);
                this.fChildConfig.setProperty(BUFFER_SIZE, new Integer(this.fBufferSize));
                this.fNeedCopyFeatures = true;
                this.fChildConfig.setProperty("http://apache.org/xml/properties/internal/namespace-context", this.fNamespaceContext);
                this.fChildConfig.setFeature(XINCLUDE_FIXUP_BASE_URIS, this.fFixupBaseURIs);
                this.fChildConfig.setFeature(XINCLUDE_FIXUP_LANGUAGE, this.fFixupLanguage);
                xMLParserConfiguration2 = this.fChildConfig;
                if (value3 != null) {
                    XPointerHandler xPointerHandler7 = (XPointerHandler) xMLParserConfiguration2.getProperty("http://apache.org/xml/properties/internal/xpointer-handler");
                    this.fXPtrProcessor = xPointerHandler7;
                    xPointerHandler7.setProperty("http://apache.org/xml/properties/internal/namespace-context", this.fNamespaceContext);
                    XPointerHandler xPointerHandler8 = (XPointerHandler) this.fXPtrProcessor;
                    if (this.fFixupBaseURIs) {
                        bool = Boolean.TRUE;
                    } else {
                        bool = Boolean.FALSE;
                    }
                    xPointerHandler8.setProperty(XINCLUDE_FIXUP_BASE_URIS, bool);
                    XPointerHandler xPointerHandler9 = (XPointerHandler) this.fXPtrProcessor;
                    if (this.fFixupLanguage) {
                        bool2 = Boolean.TRUE;
                    } else {
                        bool2 = Boolean.FALSE;
                    }
                    xPointerHandler9.setProperty(XINCLUDE_FIXUP_LANGUAGE, bool2);
                    xMLErrorReporter4 = this.fErrorReporter;
                    if (xMLErrorReporter4 != null) {
                        ((XPointerHandler) this.fXPtrProcessor).setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter4);
                    }
                    xPointerHandler7.setParent(this);
                    xPointerHandler7.setHref(str);
                    xPointerHandler7.setXIncludeLocator(this.fXIncludeLocator);
                    xPointerHandler7.setDocumentHandler(getDocumentHandler());
                    this.fXPointerChildConfig = this.fChildConfig;
                } else {
                    XIncludeHandler xIncludeHandler3 = (XIncludeHandler) xMLParserConfiguration2.getProperty("http://apache.org/xml/properties/internal/xinclude-handler");
                    xIncludeHandler3.setParent(this);
                    xIncludeHandler3.setHref(str);
                    xIncludeHandler3.setXIncludeLocator(this.fXIncludeLocator);
                    xIncludeHandler3.setDocumentHandler(getDocumentHandler());
                    this.fXIncludeChildConfig = this.fChildConfig;
                }
            }
            if (value3 != null) {
                this.fChildConfig = this.fXPointerChildConfig;
                this.fXPtrProcessor.parseXPointer(value3);
            } else {
                this.fChildConfig = this.fXIncludeChildConfig;
            }
            if (this.fNeedCopyFeatures) {
                copyFeatures(this.fSettings, this.fChildConfig);
            }
            this.fNeedCopyFeatures = false;
            this.fHasIncludeReportedContent = false;
            this.fNamespaceContext.pushScope();
            this.fChildConfig.parse(xMLInputSourceResolveEntity);
            this.fXIncludeLocator.setLocator(this.fDocLocation);
            xMLErrorReporter = this.fErrorReporter;
            if (xMLErrorReporter != null) {
                xMLErrorReporter.setDocumentLocator(this.fDocLocation);
            }
            if (value3 != null && !this.fXPtrProcessor.isXPointerResolved()) {
                xMLErrorReporter2 = this.fErrorReporter;
                if (xMLErrorReporter2 != null) {
                    locale = xMLErrorReporter2.getLocale();
                } else {
                    locale = null;
                }
                reportResourceError("XMLResourceError", new Object[]{str, this.fXIncludeMessageFormatter.formatMessage(locale, "XPointerResolutionUnsuccessful", null)});
                this.fNamespaceContext.popScope();
                return false;
            }
            this.fNamespaceContext.popScope();
        } else if (str5.equals(XINCLUDE_PARSE_TEXT)) {
            xMLInputSourceResolveEntity.setEncoding(xMLAttributes.getValue(XINCLUDE_ATTR_ENCODING));
            this.fHasIncludeReportedContent = false;
            if (this.fIsXML11) {
                xIncludeTextReader3 = this.fXInclude10TextReader;
                if (xIncludeTextReader3 == null) {
                    this.fXInclude10TextReader = new XIncludeTextReader(xMLInputSourceResolveEntity, this, this.fBufferSize);
                } else {
                    xIncludeTextReader3.setInputSource(xMLInputSourceResolveEntity);
                }
                xIncludeTextReader2 = this.fXInclude10TextReader;
            } else {
                xIncludeTextReader = this.fXInclude11TextReader;
                if (xIncludeTextReader == null) {
                    this.fXInclude11TextReader = new XInclude11TextReader(xMLInputSourceResolveEntity, this, this.fBufferSize);
                } else {
                    xIncludeTextReader.setInputSource(xMLInputSourceResolveEntity);
                }
                xIncludeTextReader2 = this.fXInclude11TextReader;
            }
            xIncludeTextReader4 = xIncludeTextReader2;
            xIncludeTextReader4.setErrorReporter(this.fErrorReporter);
            xIncludeTextReader4.parse();
            if (xIncludeTextReader4 != null) {
                xIncludeTextReader4.close();
            }
        } else {
            reportFatalError("InvalidParseValue", new Object[]{str5});
        }
        return true;
    }

    protected boolean hasXIncludeNamespace(QName qName) {
        String str = qName.uri;
        String str2 = XINCLUDE_NS_URI;
        return str == str2 || this.fNamespaceContext.getURI(qName.prefix) == str2;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fDocumentHandler == null || getState() != 1 || this.fResultDepth == 0) {
            return;
        }
        this.fDocumentHandler.ignorableWhitespace(xMLString, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void ignoredCharacters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.ignoredCharacters(xMLString, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void internalEntityDecl(String str, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.internalEntityDecl(str, xMLString, xMLString2, augmentations);
        }
    }

    protected boolean isFallbackElement(QName qName) {
        return qName.localpart.equals(XINCLUDE_FALLBACK) && hasXIncludeNamespace(qName);
    }

    protected boolean isIncludeElement(QName qName) {
        return qName.localpart.equals(XINCLUDE_INCLUDE) && hasXIncludeNamespace(qName);
    }

    protected boolean isRootDocument() {
        return this.fParentXIncludeHandler == null;
    }

    protected boolean isTopLevelIncludedItem() {
        return isTopLevelIncludedItemViaInclude() || isTopLevelIncludedItemViaFallback();
    }

    protected boolean isTopLevelIncludedItemViaFallback() {
        return getSawFallback(this.fDepth - 1);
    }

    protected boolean isTopLevelIncludedItemViaInclude() {
        return this.fDepth == 1 && !isRootDocument();
    }

    protected Augmentations modifyAugmentations(Augmentations augmentations) {
        return modifyAugmentations(augmentations, false);
    }

    protected Augmentations modifyAugmentations(Augmentations augmentations, boolean z) {
        if (z || isTopLevelIncludedItem()) {
            if (augmentations == null) {
                augmentations = new AugmentationsImpl();
            }
            augmentations.putItem(XINCLUDE_INCLUDED, Boolean.TRUE);
        }
        return augmentations;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void notationDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        addNotation(str, xMLResourceIdentifier, augmentations);
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.notationDecl(str, xMLResourceIdentifier, augmentations);
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x009a A[PHI: r3 r5 r8
      0x009a: PHI (r3v11 org.apache.xerces.xni.QName) = (r3v10 org.apache.xerces.xni.QName), (r3v20 org.apache.xerces.xni.QName) binds: [B:34:0x0098, B:47:0x00ee] A[DONT_GENERATE, DONT_INLINE]
      0x009a: PHI (r5v5 java.lang.String) = (r5v4 java.lang.String), (r5v16 java.lang.String) binds: [B:34:0x0098, B:47:0x00ee] A[DONT_GENERATE, DONT_INLINE]
      0x009a: PHI (r8v8 org.apache.xerces.xni.XMLAttributes) = (r8v6 org.apache.xerces.xni.XMLAttributes), (r8v11 org.apache.xerces.xni.XMLAttributes) binds: [B:34:0x0098, B:47:0x00ee] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:36:0x009c A[PHI: r3 r5 r8
      0x009c: PHI (r3v15 org.apache.xerces.xni.QName) = (r3v10 org.apache.xerces.xni.QName), (r3v20 org.apache.xerces.xni.QName) binds: [B:34:0x0098, B:47:0x00ee] A[DONT_GENERATE, DONT_INLINE]
      0x009c: PHI (r5v7 java.lang.String) = (r5v4 java.lang.String), (r5v16 java.lang.String) binds: [B:34:0x0098, B:47:0x00ee] A[DONT_GENERATE, DONT_INLINE]
      0x009c: PHI (r8v10 org.apache.xerces.xni.XMLAttributes) = (r8v6 org.apache.xerces.xni.XMLAttributes), (r8v11 org.apache.xerces.xni.XMLAttributes) binds: [B:34:0x0098, B:47:0x00ee] A[DONT_GENERATE, DONT_INLINE]] */
    protected XMLAttributes processAttributes(XMLAttributes xMLAttributes) {
        QName qName;
        String str;
        String str2;
        String expandedSystemId;
        if (isTopLevelIncludedItem()) {
            if (this.fFixupBaseURIs && !sameBaseURIAsIncludeParent()) {
                if (xMLAttributes == null) {
                    xMLAttributes = new XMLAttributesImpl();
                }
                try {
                    expandedSystemId = getRelativeBaseURI();
                } catch (URI.MalformedURIException unused) {
                    expandedSystemId = this.fCurrentBaseURI.getExpandedSystemId();
                }
                xMLAttributes.setSpecified(xMLAttributes.addAttribute(XML_BASE_QNAME, XMLSymbols.fCDATASymbol, expandedSystemId), true);
            }
            if (this.fFixupLanguage && !sameLanguageAsIncludeParent()) {
                if (xMLAttributes == null) {
                    xMLAttributes = new XMLAttributesImpl();
                }
                xMLAttributes.setSpecified(xMLAttributes.addAttribute(XML_LANG_QNAME, XMLSymbols.fCDATASymbol, this.fCurrentLanguage), true);
            }
            Enumeration allPrefixes = this.fNamespaceContext.getAllPrefixes();
            while (allPrefixes.hasMoreElements()) {
                String str3 = (String) allPrefixes.nextElement();
                String uRIFromIncludeParent = this.fNamespaceContext.getURIFromIncludeParent(str3);
                String uri = this.fNamespaceContext.getURI(str3);
                if (uRIFromIncludeParent != uri && xMLAttributes != null) {
                    if (str3 == XMLSymbols.EMPTY_STRING) {
                        if (xMLAttributes.getValue(NamespaceContext.XMLNS_URI, XMLSymbols.PREFIX_XMLNS) == null) {
                            if (xMLAttributes == null) {
                                xMLAttributes = new XMLAttributesImpl();
                            }
                            qName = (QName) NEW_NS_ATTR_QNAME.clone();
                            qName.prefix = null;
                            qName.localpart = XMLSymbols.PREFIX_XMLNS;
                            qName.rawname = XMLSymbols.PREFIX_XMLNS;
                            str = XMLSymbols.fCDATASymbol;
                            if (uri != null) {
                                str2 = uri;
                            } else {
                                str2 = XMLSymbols.EMPTY_STRING;
                            }
                            xMLAttributes.setSpecified(xMLAttributes.addAttribute(qName, str, str2), true);
                            this.fNamespaceContext.declarePrefix(str3, uri);
                        }
                    } else if (xMLAttributes.getValue(NamespaceContext.XMLNS_URI, str3) == null) {
                        if (xMLAttributes == null) {
                            xMLAttributes = new XMLAttributesImpl();
                        }
                        qName = (QName) NEW_NS_ATTR_QNAME.clone();
                        qName.localpart = str3;
                        qName.rawname += str3;
                        SymbolTable symbolTable = this.fSymbolTable;
                        qName.rawname = symbolTable != null ? symbolTable.addSymbol(qName.rawname) : qName.rawname.intern();
                        str = XMLSymbols.fCDATASymbol;
                        if (uri != null) {
                            str2 = uri;
                        } else {
                            str2 = XMLSymbols.EMPTY_STRING;
                        }
                        xMLAttributes.setSpecified(xMLAttributes.addAttribute(qName, str, str2), true);
                        this.fNamespaceContext.declarePrefix(str3, uri);
                    }
                }
            }
        }
        if (xMLAttributes != null) {
            int length = xMLAttributes.getLength();
            for (int i = 0; i < length; i++) {
                String type = xMLAttributes.getType(i);
                String value = xMLAttributes.getValue(i);
                if (type == XMLSymbols.fENTITYSymbol) {
                    checkUnparsedEntity(value);
                }
                if (type == XMLSymbols.fENTITIESSymbol) {
                    StringTokenizer stringTokenizer = new StringTokenizer(value);
                    while (stringTokenizer.hasMoreTokens()) {
                        checkUnparsedEntity(stringTokenizer.nextToken());
                    }
                } else if (type == XMLSymbols.fNOTATIONSymbol) {
                    checkNotation(value);
                }
            }
        }
        return xMLAttributes;
    }

    protected void processXMLBaseAttributes(XMLAttributes xMLAttributes) {
        String value = xMLAttributes.getValue(NamespaceContext.XML_URI, ApkUtil.DEF_MODULE_NAME);
        if (value != null) {
            try {
                String strExpandSystemId = XMLEntityManager.expandSystemId(value, this.fCurrentBaseURI.getExpandedSystemId(), false);
                this.fCurrentBaseURI.setLiteralSystemId(value);
                XMLResourceIdentifier xMLResourceIdentifier = this.fCurrentBaseURI;
                xMLResourceIdentifier.setBaseSystemId(xMLResourceIdentifier.getExpandedSystemId());
                this.fCurrentBaseURI.setExpandedSystemId(strExpandSystemId);
                saveBaseURI();
            } catch (URI.MalformedURIException unused) {
            }
        }
    }

    protected void processXMLLangAttributes(XMLAttributes xMLAttributes) {
        String value = xMLAttributes.getValue(NamespaceContext.XML_URI, "lang");
        if (value != null) {
            this.fCurrentLanguage = value;
            saveLanguage(value);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fInDTD) {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.processingInstruction(str, xMLString, augmentations);
                return;
            }
            return;
        }
        if (this.fDocumentHandler == null || getState() != 1) {
            return;
        }
        this.fDepth++;
        this.fDocumentHandler.processingInstruction(str, xMLString, modifyAugmentations(augmentations));
        this.fDepth--;
    }

    protected void reportFatalError(String str) {
        reportFatalError(str, null);
    }

    protected void reportFatalError(String str, Object[] objArr) {
        reportFatalError(str, objArr, null);
    }

    protected void reportFatalError(String str, Object[] objArr, Exception exc) {
        reportError(str, objArr, (short) 2, exc);
    }

    protected void reportResourceError(String str) {
        reportResourceError(str, null);
    }

    protected void reportResourceError(String str, Object[] objArr) {
        reportResourceError(str, objArr, null);
    }

    protected void reportResourceError(String str, Object[] objArr, Exception exc) {
        reportError(str, objArr, (short) 0, exc);
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XNIException {
        this.fNamespaceContext = null;
        this.fDepth = 0;
        this.fResultDepth = isRootDocument() ? 0 : this.fParentXIncludeHandler.getResultDepth();
        this.fNotations.clear();
        this.fUnparsedEntities.clear();
        this.fParentRelativeURI = null;
        this.fIsXML11 = false;
        this.fInDTD = false;
        this.fSeenRootElement = false;
        this.fBaseURIScope.clear();
        this.fBaseURI.clear();
        this.fLiteralSystemID.clear();
        this.fExpandedSystemID.clear();
        this.fLanguageScope.clear();
        this.fLanguageStack.clear();
        int i = 0;
        while (true) {
            int[] iArr = this.fState;
            if (i >= iArr.length) {
                break;
            }
            iArr[i] = 1;
            i++;
        }
        int i2 = 0;
        while (true) {
            boolean[] zArr = this.fSawFallback;
            if (i2 >= zArr.length) {
                break;
            }
            zArr[i2] = false;
            i2++;
        }
        int i3 = 0;
        while (true) {
            boolean[] zArr2 = this.fSawInclude;
            if (i3 < zArr2.length) {
                zArr2[i3] = false;
                i3++;
            } else {
                try {
                    break;
                } catch (XMLConfigurationException unused) {
                }
            }
        }
        if (!xMLComponentManager.getFeature(PARSER_SETTINGS)) {
            return;
        }
        this.fNeedCopyFeatures = true;
        try {
            boolean feature = xMLComponentManager.getFeature(ALLOW_UE_AND_NOTATION_EVENTS);
            this.fSendUEAndNotationEvents = feature;
            XMLParserConfiguration xMLParserConfiguration = this.fChildConfig;
            if (xMLParserConfiguration != null) {
                xMLParserConfiguration.setFeature(ALLOW_UE_AND_NOTATION_EVENTS, feature);
            }
        } catch (XMLConfigurationException unused2) {
        }
        try {
            boolean feature2 = xMLComponentManager.getFeature(XINCLUDE_FIXUP_BASE_URIS);
            this.fFixupBaseURIs = feature2;
            XMLParserConfiguration xMLParserConfiguration2 = this.fChildConfig;
            if (xMLParserConfiguration2 != null) {
                xMLParserConfiguration2.setFeature(XINCLUDE_FIXUP_BASE_URIS, feature2);
            }
        } catch (XMLConfigurationException unused3) {
            this.fFixupBaseURIs = true;
        }
        try {
            boolean feature3 = xMLComponentManager.getFeature(XINCLUDE_FIXUP_LANGUAGE);
            this.fFixupLanguage = feature3;
            XMLParserConfiguration xMLParserConfiguration3 = this.fChildConfig;
            if (xMLParserConfiguration3 != null) {
                xMLParserConfiguration3.setFeature(XINCLUDE_FIXUP_LANGUAGE, feature3);
            }
        } catch (XMLConfigurationException unused4) {
            this.fFixupLanguage = true;
        }
        try {
            SymbolTable symbolTable = (SymbolTable) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
            if (symbolTable != null) {
                this.fSymbolTable = symbolTable;
                XMLParserConfiguration xMLParserConfiguration4 = this.fChildConfig;
                if (xMLParserConfiguration4 != null) {
                    xMLParserConfiguration4.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
                }
            }
        } catch (XMLConfigurationException unused5) {
            this.fSymbolTable = null;
        }
        try {
            XMLErrorReporter xMLErrorReporter = (XMLErrorReporter) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
            if (xMLErrorReporter != null) {
                setErrorReporter(xMLErrorReporter);
                XMLParserConfiguration xMLParserConfiguration5 = this.fChildConfig;
                if (xMLParserConfiguration5 != null) {
                    xMLParserConfiguration5.setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter);
                }
            }
        } catch (XMLConfigurationException unused6) {
            this.fErrorReporter = null;
        }
        try {
            XMLEntityResolver xMLEntityResolver = (XMLEntityResolver) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
            if (xMLEntityResolver != null) {
                this.fEntityResolver = xMLEntityResolver;
                XMLParserConfiguration xMLParserConfiguration6 = this.fChildConfig;
                if (xMLParserConfiguration6 != null) {
                    xMLParserConfiguration6.setProperty("http://apache.org/xml/properties/internal/entity-resolver", xMLEntityResolver);
                }
            }
        } catch (XMLConfigurationException unused7) {
            this.fEntityResolver = null;
        }
        try {
            SecurityManager securityManager = (SecurityManager) xMLComponentManager.getProperty(SECURITY_MANAGER);
            if (securityManager != null) {
                this.fSecurityManager = securityManager;
                XMLParserConfiguration xMLParserConfiguration7 = this.fChildConfig;
                if (xMLParserConfiguration7 != null) {
                    xMLParserConfiguration7.setProperty(SECURITY_MANAGER, securityManager);
                }
            }
        } catch (XMLConfigurationException unused8) {
            this.fSecurityManager = null;
        }
        try {
            Integer num = (Integer) xMLComponentManager.getProperty(BUFFER_SIZE);
            if (num == null || num.intValue() <= 0) {
                this.fBufferSize = ((Integer) getPropertyDefault(BUFFER_SIZE)).intValue();
            } else {
                this.fBufferSize = num.intValue();
                XMLParserConfiguration xMLParserConfiguration8 = this.fChildConfig;
                if (xMLParserConfiguration8 != null) {
                    xMLParserConfiguration8.setProperty(BUFFER_SIZE, num);
                }
            }
        } catch (XMLConfigurationException unused9) {
            this.fBufferSize = ((Integer) getPropertyDefault(BUFFER_SIZE)).intValue();
        }
        XIncludeTextReader xIncludeTextReader = this.fXInclude10TextReader;
        if (xIncludeTextReader != null) {
            xIncludeTextReader.setBufferSize(this.fBufferSize);
        }
        XIncludeTextReader xIncludeTextReader2 = this.fXInclude11TextReader;
        if (xIncludeTextReader2 != null) {
            xIncludeTextReader2.setBufferSize(this.fBufferSize);
        }
        ParserConfigurationSettings parserConfigurationSettings = new ParserConfigurationSettings();
        this.fSettings = parserConfigurationSettings;
        copyFeatures(xMLComponentManager, parserConfigurationSettings);
        try {
            if (xMLComponentManager.getFeature(SCHEMA_VALIDATION)) {
                this.fSettings.setFeature(SCHEMA_VALIDATION, false);
                if (Constants.NS_XMLSCHEMA.equals(xMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage"))) {
                    this.fSettings.setFeature(VALIDATION, false);
                } else if (xMLComponentManager.getFeature(VALIDATION)) {
                    this.fSettings.setFeature(DYNAMIC_VALIDATION, true);
                }
            }
        } catch (XMLConfigurationException unused10) {
        }
    }

    protected void restoreBaseURI() {
        this.fBaseURI.pop();
        this.fLiteralSystemID.pop();
        this.fExpandedSystemID.pop();
        this.fBaseURIScope.pop();
        this.fCurrentBaseURI.setBaseSystemId((String) this.fBaseURI.peek());
        this.fCurrentBaseURI.setLiteralSystemId((String) this.fLiteralSystemID.peek());
        this.fCurrentBaseURI.setExpandedSystemId((String) this.fExpandedSystemID.peek());
    }

    public String restoreLanguage() {
        this.fLanguageStack.pop();
        this.fLanguageScope.pop();
        return (String) this.fLanguageStack.peek();
    }

    protected boolean sameBaseURIAsIncludeParent() {
        String includeParentBaseURI = getIncludeParentBaseURI();
        return includeParentBaseURI != null && includeParentBaseURI.equals(this.fCurrentBaseURI.getExpandedSystemId());
    }

    protected boolean sameLanguageAsIncludeParent() {
        String includeParentLanguage = getIncludeParentLanguage();
        return includeParentLanguage != null && includeParentLanguage.equalsIgnoreCase(this.fCurrentLanguage);
    }

    protected void saveBaseURI() {
        this.fBaseURIScope.push(this.fDepth);
        this.fBaseURI.push(this.fCurrentBaseURI.getBaseSystemId());
        this.fLiteralSystemID.push(this.fCurrentBaseURI.getLiteralSystemId());
        this.fExpandedSystemID.push(this.fCurrentBaseURI.getExpandedSystemId());
    }

    protected void saveLanguage(String str) {
        this.fLanguageScope.push(this.fDepth);
        this.fLanguageStack.push(str);
    }

    protected boolean searchForRecursiveIncludes(String str) {
        if (str.equals(this.fCurrentBaseURI.getExpandedSystemId())) {
            return true;
        }
        XIncludeHandler xIncludeHandler = this.fParentXIncludeHandler;
        if (xIncludeHandler == null) {
            return false;
        }
        return xIncludeHandler.searchForRecursiveIncludes(str);
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDSource
    public void setDTDHandler(XMLDTDHandler xMLDTDHandler) {
        this.fDTDHandler = xMLDTDHandler;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void setDTDSource(XMLDTDSource xMLDTDSource) {
        this.fDTDSource = xMLDTDSource;
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentSource
    public void setDocumentHandler(XMLDocumentHandler xMLDocumentHandler) {
        if (this.fDocumentHandler != xMLDocumentHandler) {
            this.fDocumentHandler = xMLDocumentHandler;
            XMLParserConfiguration xMLParserConfiguration = this.fXIncludeChildConfig;
            if (xMLParserConfiguration != null) {
                xMLParserConfiguration.setDocumentHandler(xMLDocumentHandler);
            }
            XMLParserConfiguration xMLParserConfiguration2 = this.fXPointerChildConfig;
            if (xMLParserConfiguration2 != null) {
                xMLParserConfiguration2.setDocumentHandler(xMLDocumentHandler);
            }
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void setDocumentSource(XMLDocumentSource xMLDocumentSource) {
        this.fDocumentSource = xMLDocumentSource;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        if (str.equals(ALLOW_UE_AND_NOTATION_EVENTS)) {
            this.fSendUEAndNotationEvents = z;
        }
        ParserConfigurationSettings parserConfigurationSettings = this.fSettings;
        if (parserConfigurationSettings != null) {
            this.fNeedCopyFeatures = true;
            parserConfigurationSettings.setFeature(str, z);
        }
    }

    protected void setHref(String str) {
        this.fHrefFromParent = str;
    }

    protected void setParent(XIncludeHandler xIncludeHandler) {
        this.fParentXIncludeHandler = xIncludeHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (str.equals("http://apache.org/xml/properties/internal/symbol-table")) {
            this.fSymbolTable = (SymbolTable) obj;
            XMLParserConfiguration xMLParserConfiguration = this.fChildConfig;
            if (xMLParserConfiguration != null) {
                xMLParserConfiguration.setProperty(str, obj);
                return;
            }
            return;
        }
        if (str.equals("http://apache.org/xml/properties/internal/error-reporter")) {
            setErrorReporter((XMLErrorReporter) obj);
            XMLParserConfiguration xMLParserConfiguration2 = this.fChildConfig;
            if (xMLParserConfiguration2 != null) {
                xMLParserConfiguration2.setProperty(str, obj);
                return;
            }
            return;
        }
        if (str.equals("http://apache.org/xml/properties/internal/entity-resolver")) {
            this.fEntityResolver = (XMLEntityResolver) obj;
            XMLParserConfiguration xMLParserConfiguration3 = this.fChildConfig;
            if (xMLParserConfiguration3 != null) {
                xMLParserConfiguration3.setProperty(str, obj);
                return;
            }
            return;
        }
        if (str.equals(SECURITY_MANAGER)) {
            this.fSecurityManager = (SecurityManager) obj;
            XMLParserConfiguration xMLParserConfiguration4 = this.fChildConfig;
            if (xMLParserConfiguration4 != null) {
                xMLParserConfiguration4.setProperty(str, obj);
                return;
            }
            return;
        }
        if (str.equals(BUFFER_SIZE)) {
            Integer num = (Integer) obj;
            XMLParserConfiguration xMLParserConfiguration5 = this.fChildConfig;
            if (xMLParserConfiguration5 != null) {
                xMLParserConfiguration5.setProperty(str, obj);
            }
            if (num == null || num.intValue() <= 0) {
                return;
            }
            int iIntValue = num.intValue();
            this.fBufferSize = iIntValue;
            XIncludeTextReader xIncludeTextReader = this.fXInclude10TextReader;
            if (xIncludeTextReader != null) {
                xIncludeTextReader.setBufferSize(iIntValue);
            }
            XIncludeTextReader xIncludeTextReader2 = this.fXInclude11TextReader;
            if (xIncludeTextReader2 != null) {
                xIncludeTextReader2.setBufferSize(this.fBufferSize);
            }
        }
    }

    protected void setSawFallback(int i, boolean z) {
        boolean[] zArr = this.fSawFallback;
        if (i >= zArr.length) {
            boolean[] zArr2 = new boolean[i * 2];
            System.arraycopy(zArr, 0, zArr2, 0, zArr.length);
            this.fSawFallback = zArr2;
        }
        this.fSawFallback[i] = z;
    }

    protected void setSawInclude(int i, boolean z) {
        boolean[] zArr = this.fSawInclude;
        if (i >= zArr.length) {
            boolean[] zArr2 = new boolean[i * 2];
            System.arraycopy(zArr, 0, zArr2, 0, zArr.length);
            this.fSawInclude = zArr2;
        }
        this.fSawInclude[i] = z;
    }

    protected void setState(int i) {
        int i2 = this.fDepth;
        int[] iArr = this.fState;
        if (i2 >= iArr.length) {
            int[] iArr2 = new int[i2 * 2];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.fState = iArr2;
        }
        this.fState[this.fDepth] = i;
    }

    protected void setXIncludeLocator(XMLLocatorWrapper xMLLocatorWrapper) {
        this.fXIncludeLocator = xMLLocatorWrapper;
    }

    protected void setupCurrentBaseURI(XMLLocator xMLLocator) {
        XMLResourceIdentifier xMLResourceIdentifier;
        String literalSystemId;
        this.fCurrentBaseURI.setBaseSystemId(xMLLocator.getBaseSystemId());
        if (xMLLocator.getLiteralSystemId() != null) {
            xMLResourceIdentifier = this.fCurrentBaseURI;
            literalSystemId = xMLLocator.getLiteralSystemId();
        } else {
            xMLResourceIdentifier = this.fCurrentBaseURI;
            literalSystemId = this.fHrefFromParent;
        }
        xMLResourceIdentifier.setLiteralSystemId(literalSystemId);
        String expandedSystemId = xMLLocator.getExpandedSystemId();
        if (expandedSystemId == null) {
            try {
                expandedSystemId = XMLEntityManager.expandSystemId(this.fCurrentBaseURI.getLiteralSystemId(), this.fCurrentBaseURI.getBaseSystemId(), false);
                if (expandedSystemId == null) {
                    expandedSystemId = this.fCurrentBaseURI.getLiteralSystemId();
                }
            } catch (URI.MalformedURIException unused) {
                reportFatalError("ExpandedSystemId");
            }
        }
        this.fCurrentBaseURI.setExpandedSystemId(expandedSystemId);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startAttlist(String str, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.startAttlist(str, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
        if (this.fDocumentHandler == null || getState() != 1 || this.fResultDepth == 0) {
            return;
        }
        this.fDocumentHandler.startCDATA(augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startConditional(short s, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.startConditional(s, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startDTD(XMLLocator xMLLocator, Augmentations augmentations) throws XNIException {
        this.fInDTD = true;
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.startDTD(xMLLocator, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler;
        this.fErrorReporter.setDocumentLocator(xMLLocator);
        if (!(namespaceContext instanceof XIncludeNamespaceSupport)) {
            reportFatalError("IncompatibleNamespaceContext");
        }
        this.fNamespaceContext = (XIncludeNamespaceSupport) namespaceContext;
        this.fDocLocation = xMLLocator;
        this.fXIncludeLocator.setLocator(xMLLocator);
        setupCurrentBaseURI(xMLLocator);
        saveBaseURI();
        if (augmentations == null) {
            augmentations = new AugmentationsImpl();
        }
        augmentations.putItem(CURRENT_BASE_URI, this.fCurrentBaseURI);
        if (!isRootDocument()) {
            XIncludeHandler xIncludeHandler = this.fParentXIncludeHandler;
            xIncludeHandler.fHasIncludeReportedContent = true;
            if (xIncludeHandler.searchForRecursiveIncludes(this.fCurrentBaseURI.getExpandedSystemId())) {
                reportFatalError("RecursiveInclude", new Object[]{this.fCurrentBaseURI.getExpandedSystemId()});
            }
        }
        String str2 = XMLSymbols.EMPTY_STRING;
        this.fCurrentLanguage = str2;
        saveLanguage(str2);
        if (!isRootDocument() || (xMLDocumentHandler = this.fDocumentHandler) == null) {
            return;
        }
        xMLDocumentHandler.startDocument(this.fXIncludeLocator, str, namespaceContext, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        int i = this.fDepth;
        this.fDepth = i + 1;
        int state = getState(i);
        if (state == 3 && getState(this.fDepth - 2) == 3) {
            setState(2);
        } else {
            setState(state);
        }
        processXMLBaseAttributes(xMLAttributes);
        if (this.fFixupLanguage) {
            processXMLLangAttributes(xMLAttributes);
        }
        if (isIncludeElement(qName)) {
            if (handleIncludeElement(xMLAttributes)) {
                setState(2);
                return;
            } else {
                setState(3);
                return;
            }
        }
        if (isFallbackElement(qName)) {
            handleFallbackElement();
            return;
        }
        if (hasXIncludeNamespace(qName)) {
            if (getSawInclude(this.fDepth - 1)) {
                reportFatalError("IncludeChild", new Object[]{qName.rawname});
            }
            if (getSawFallback(this.fDepth - 1)) {
                reportFatalError("FallbackChild", new Object[]{qName.rawname});
            }
            if (getState() != 1) {
                return;
            }
            int i2 = this.fResultDepth;
            this.fResultDepth = i2 + 1;
            if (i2 == 0) {
                checkMultipleRootElements();
            }
            if (this.fDocumentHandler == null) {
                return;
            }
        } else {
            if (getState() != 1) {
                return;
            }
            int i3 = this.fResultDepth;
            this.fResultDepth = i3 + 1;
            if (i3 == 0) {
                checkMultipleRootElements();
            }
            if (this.fDocumentHandler == null) {
                return;
            }
        }
        Augmentations augmentationsModifyAugmentations = modifyAugmentations(augmentations);
        this.fDocumentHandler.startElement(qName, processAttributes(xMLAttributes), augmentationsModifyAugmentations);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startExternalSubset(XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.startExternalSubset(xMLResourceIdentifier, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        if (getState() == 1) {
            if (this.fResultDepth == 0) {
                if (augmentations == null || !Boolean.TRUE.equals(augmentations.getItem(Constants.ENTITY_SKIPPED))) {
                    return;
                }
                reportFatalError("UnexpandedEntityReferenceIllegal");
                return;
            }
            XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
            if (xMLDocumentHandler != null) {
                xMLDocumentHandler.startGeneralEntity(str, xMLResourceIdentifier, str2, augmentations);
            }
        }
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startParameterEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.startParameterEntity(str, xMLResourceIdentifier, str2, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
        if (this.fDocumentHandler == null || getState() != 1) {
            return;
        }
        this.fDocumentHandler.textDecl(str, str2, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void unparsedEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        addUnparsedEntity(str, xMLResourceIdentifier, str2, augmentations);
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.unparsedEntityDecl(str, xMLResourceIdentifier, str2, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler;
        this.fIsXML11 = CompilerOptions.VERSION_1_1.equals(str);
        if (!isRootDocument() || (xMLDocumentHandler = this.fDocumentHandler) == null) {
            return;
        }
        xMLDocumentHandler.xmlDecl(str, str2, str3, augmentations);
    }
}
