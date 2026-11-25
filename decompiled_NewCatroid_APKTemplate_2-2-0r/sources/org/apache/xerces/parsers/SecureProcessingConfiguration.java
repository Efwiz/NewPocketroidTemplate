package org.apache.xerces.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;
import org.apache.xerces.impl.XMLEntityDescription;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDFilter;
import org.apache.xerces.xni.parser.XMLDTDSource;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public final class SecureProcessingConfiguration extends XIncludeAwareParserConfiguration {
    private static final int ENTITY_EXPANSION_LIMIT_DEFAULT_VALUE = 64000;
    private static final String ENTITY_EXPANSION_LIMIT_PROPERTY_NAME = "jdk.xml.entityExpansionLimit";
    private static final String ENTITY_RESOLVER_PROPERTY = "http://apache.org/xml/properties/internal/entity-resolver";
    private static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    private static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
    private static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    private static final int MAX_GENERAL_ENTITY_SIZE_LIMIT_DEFAULT_VALUE = Integer.MAX_VALUE;
    private static final String MAX_GENERAL_ENTITY_SIZE_LIMIT_PROPERTY_NAME = "jdk.xml.maxGeneralEntitySizeLimit";
    private static final int MAX_OCCUR_LIMIT_DEFAULT_VALUE = 5000;
    private static final String MAX_OCCUR_LIMIT_PROPERTY_NAME = "jdk.xml.maxOccur";
    private static final int MAX_PARAMETER_ENTITY_SIZE_LIMIT_DEFAULT_VALUE = Integer.MAX_VALUE;
    private static final String MAX_PARAMETER_ENTITY_SIZE_LIMIT_PROPERTY_NAME = "jdk.xml.maxParameterEntitySizeLimit";
    private static final boolean RESOLVE_EXTERNAL_ENTITIES_DEFAULT_VALUE = true;
    private static final String RESOLVE_EXTERNAL_ENTITIES_PROPERTY_NAME = "jdk.xml.resolveExternalEntities";
    private static final int SECURITY_MANAGER_DEFAULT_ENTITY_EXPANSION_LIMIT = 100000;
    private static final int SECURITY_MANAGER_DEFAULT_MAX_OCCUR_NODE_LIMIT = 3000;
    private static final String SECURITY_MANAGER_PROPERTY = "http://apache.org/xml/properties/security-manager";
    private static final int TOTAL_ENTITY_SIZE_LIMIT_DEFAULT_VALUE = 50000000;
    private static final String TOTAL_ENTITY_SIZE_LIMIT_PROPERTY_NAME = "jdk.xml.totalEntitySizeLimit";
    protected final int ENTITY_EXPANSION_LIMIT_SYSTEM_VALUE;
    protected final int MAX_GENERAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE;
    protected final int MAX_OCCUR_LIMIT_SYSTEM_VALUE;
    protected final int MAX_PARAMETER_ENTITY_SIZE_LIMIT_SYSTEM_VALUE;
    protected final boolean RESOLVE_EXTERNAL_ENTITIES_SYSTEM_VALUE;
    protected final int TOTAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE;
    private final ExternalEntityMonitor fExternalEntityMonitor;
    private InternalEntityMonitor fInternalEntityMonitor;
    private final boolean fJavaSecurityManagerEnabled;
    private boolean fLimitSpecified;
    private SecurityManager fSecurityManager;
    private int fTotalEntitySize;
    private static final boolean DEBUG = isDebugEnabled();
    private static Properties jaxpProperties = null;
    private static long lastModified = -1;

    final class ExternalEntityMonitor implements XMLEntityResolver {
        private XMLEntityResolver fEntityResolver;

        final class InputStreamMonitor extends FilterInputStream {
            private final boolean isPE;
            private int size;

            protected InputStreamMonitor(InputStream inputStream, boolean z) {
                super(inputStream);
                this.size = 0;
                this.isPE = z;
            }

            @Override // java.io.FilterInputStream, java.io.InputStream
            public int read() throws IOException {
                int i = super.read();
                if (i != -1) {
                    this.size++;
                    SecureProcessingConfiguration.this.checkEntitySizeLimits(this.size, 1, this.isPE);
                }
                return i;
            }

            @Override // java.io.FilterInputStream, java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws IOException {
                int i3 = super.read(bArr, i, i2);
                if (i3 > 0) {
                    this.size += i3;
                    SecureProcessingConfiguration.this.checkEntitySizeLimits(this.size, i3, this.isPE);
                }
                return i3;
            }
        }

        final class ReaderMonitor extends FilterReader {
            private final boolean isPE;
            private int size;

            protected ReaderMonitor(Reader reader, boolean z) {
                super(reader);
                this.size = 0;
                this.isPE = z;
            }

            @Override // java.io.FilterReader, java.io.Reader
            public int read() throws IOException {
                int i = super.read();
                if (i != -1) {
                    this.size++;
                    SecureProcessingConfiguration.this.checkEntitySizeLimits(this.size, 1, this.isPE);
                }
                return i;
            }

            @Override // java.io.FilterReader, java.io.Reader
            public int read(char[] cArr, int i, int i2) throws IOException {
                int i3 = super.read(cArr, i, i2);
                if (i3 > 0) {
                    this.size += i3;
                    SecureProcessingConfiguration.this.checkEntitySizeLimits(this.size, i3, this.isPE);
                }
                return i3;
            }
        }

        ExternalEntityMonitor() {
        }

        public XMLEntityResolver getEntityResolver() {
            return this.fEntityResolver;
        }

        @Override // org.apache.xerces.xni.parser.XMLEntityResolver
        public XMLInputSource resolveEntity(XMLResourceIdentifier xMLResourceIdentifier) throws IOException, XNIException {
            XMLEntityResolver xMLEntityResolver = this.fEntityResolver;
            XMLInputSource xMLInputSourceResolveEntity = xMLEntityResolver != null ? xMLEntityResolver.resolveEntity(xMLResourceIdentifier) : null;
            if (SecureProcessingConfiguration.this.fSecurityManager != null && (xMLResourceIdentifier instanceof XMLEntityDescription)) {
                String entityName = ((XMLEntityDescription) xMLResourceIdentifier).getEntityName();
                boolean z = entityName != null && entityName.startsWith("%");
                if (xMLInputSourceResolveEntity == null) {
                    xMLInputSourceResolveEntity = new XMLInputSource(xMLResourceIdentifier.getPublicId(), xMLResourceIdentifier.getExpandedSystemId(), xMLResourceIdentifier.getBaseSystemId());
                }
                Reader characterStream = xMLInputSourceResolveEntity.getCharacterStream();
                if (characterStream != null) {
                    xMLInputSourceResolveEntity.setCharacterStream(new ReaderMonitor(characterStream, z));
                } else {
                    InputStream byteStream = xMLInputSourceResolveEntity.getByteStream();
                    if (byteStream != null) {
                        xMLInputSourceResolveEntity.setByteStream(new InputStreamMonitor(byteStream, z));
                    } else {
                        xMLInputSourceResolveEntity.setByteStream(new InputStreamMonitor(new URL(xMLResourceIdentifier.getExpandedSystemId()).openStream(), z));
                    }
                }
            }
            return xMLInputSourceResolveEntity;
        }

        public void setEntityResolver(XMLEntityResolver xMLEntityResolver) {
            this.fEntityResolver = xMLEntityResolver;
        }
    }

    final class InternalEntityMonitor implements XMLDTDFilter {
        private XMLDTDHandler fDTDHandler;
        private XMLDTDSource fDTDSource;

        public InternalEntityMonitor() {
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void attributeDecl(String str, String str2, String str3, String[] strArr, String str4, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.attributeDecl(str, str2, str3, strArr, str4, xMLString, xMLString2, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.comment(xMLString, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void elementDecl(String str, String str2, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.elementDecl(str, str2, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void endAttlist(Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.endAttlist(augmentations);
            }
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
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void endExternalSubset(Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.endExternalSubset(augmentations);
            }
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

        @Override // org.apache.xerces.xni.parser.XMLDTDSource
        public XMLDTDHandler getDTDHandler() {
            return this.fDTDHandler;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public XMLDTDSource getDTDSource() {
            return this.fDTDSource;
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
            SecureProcessingConfiguration.this.checkEntitySizeLimits(xMLString.length, xMLString.length, str != null && str.startsWith("%"));
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.internalEntityDecl(str, xMLString, xMLString2, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void notationDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.notationDecl(str, xMLResourceIdentifier, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.processingInstruction(str, xMLString, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.parser.XMLDTDSource
        public void setDTDHandler(XMLDTDHandler xMLDTDHandler) {
            this.fDTDHandler = xMLDTDHandler;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void setDTDSource(XMLDTDSource xMLDTDSource) {
            this.fDTDSource = xMLDTDSource;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void startAttlist(String str, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.startAttlist(str, augmentations);
            }
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
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.startDTD(xMLLocator, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void startExternalSubset(XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.startExternalSubset(xMLResourceIdentifier, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void startParameterEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.startParameterEntity(str, xMLResourceIdentifier, str2, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.textDecl(str, str2, augmentations);
            }
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void unparsedEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.unparsedEntityDecl(str, xMLResourceIdentifier, str2, augmentations);
            }
        }
    }

    public SecureProcessingConfiguration() {
        this(null, null, null);
    }

    public SecureProcessingConfiguration(SymbolTable symbolTable) {
        this(symbolTable, null, null);
    }

    public SecureProcessingConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this(symbolTable, xMLGrammarPool, null);
    }

    public SecureProcessingConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool, XMLComponentManager xMLComponentManager) {
        super(symbolTable, xMLGrammarPool, xMLComponentManager);
        this.fTotalEntitySize = 0;
        boolean z = System.getSecurityManager() != null;
        this.fJavaSecurityManagerEnabled = z;
        int propertyValue = getPropertyValue(ENTITY_EXPANSION_LIMIT_PROPERTY_NAME, ENTITY_EXPANSION_LIMIT_DEFAULT_VALUE);
        this.ENTITY_EXPANSION_LIMIT_SYSTEM_VALUE = propertyValue;
        int propertyValue2 = getPropertyValue(MAX_OCCUR_LIMIT_PROPERTY_NAME, 5000);
        this.MAX_OCCUR_LIMIT_SYSTEM_VALUE = propertyValue2;
        this.TOTAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE = getPropertyValue(TOTAL_ENTITY_SIZE_LIMIT_PROPERTY_NAME, TOTAL_ENTITY_SIZE_LIMIT_DEFAULT_VALUE);
        this.MAX_GENERAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE = getPropertyValue(MAX_GENERAL_ENTITY_SIZE_LIMIT_PROPERTY_NAME, Integer.MAX_VALUE);
        this.MAX_PARAMETER_ENTITY_SIZE_LIMIT_SYSTEM_VALUE = getPropertyValue(MAX_PARAMETER_ENTITY_SIZE_LIMIT_PROPERTY_NAME, Integer.MAX_VALUE);
        boolean propertyValue3 = getPropertyValue(RESOLVE_EXTERNAL_ENTITIES_PROPERTY_NAME, true);
        this.RESOLVE_EXTERNAL_ENTITIES_SYSTEM_VALUE = propertyValue3;
        if (z || this.fLimitSpecified) {
            if (!propertyValue3) {
                super.setFeature(EXTERNAL_GENERAL_ENTITIES, false);
                super.setFeature(EXTERNAL_PARAMETER_ENTITIES, false);
                super.setFeature(LOAD_EXTERNAL_DTD, false);
            }
            SecurityManager securityManager = new SecurityManager();
            this.fSecurityManager = securityManager;
            securityManager.setEntityExpansionLimit(propertyValue);
            this.fSecurityManager.setMaxOccurNodeLimit(propertyValue2);
            super.setProperty(SECURITY_MANAGER_PROPERTY, this.fSecurityManager);
        }
        ExternalEntityMonitor externalEntityMonitor = new ExternalEntityMonitor();
        this.fExternalEntityMonitor = externalEntityMonitor;
        super.setProperty("http://apache.org/xml/properties/internal/entity-resolver", externalEntityMonitor);
    }

    private void configurePipelineCommon(boolean z) {
        XMLDTDSource xMLDTDSource;
        XMLDTDHandler xMLDTDHandler;
        if (this.fSecurityManager != null) {
            this.fTotalEntitySize = 0;
            if (this.fInternalEntityMonitor == null) {
                this.fInternalEntityMonitor = new InternalEntityMonitor();
            }
            if (z) {
                xMLDTDSource = this.fDTDScanner;
                xMLDTDHandler = this.fDTDProcessor;
            } else {
                xMLDTDSource = this.fXML11DTDScanner;
                xMLDTDHandler = this.fXML11DTDProcessor;
            }
            xMLDTDSource.setDTDHandler(this.fInternalEntityMonitor);
            this.fInternalEntityMonitor.setDTDSource(xMLDTDSource);
            this.fInternalEntityMonitor.setDTDHandler(xMLDTDHandler);
            xMLDTDHandler.setDTDSource(this.fInternalEntityMonitor);
        }
    }

    private static void debugPrintln(String str) {
        if (DEBUG) {
            System.err.println("XERCES: " + str);
        }
    }

    /* JADX WARN: Code duplicated, block: B:54:0x00fb A[Catch: all -> 0x013c, DONT_GENERATE, TRY_ENTER, TRY_LEAVE, TryCatch #10 {all -> 0x013c, blocks: (B:46:0x00eb, B:54:0x00fb, B:53:0x00f8, B:71:0x0138, B:72:0x013b), top: B:97:0x00b3 }] */
    /* JADX WARN: Code duplicated, block: B:63:0x0110 A[Catch: all -> 0x00a2, ThreadDeath -> 0x00a5, VirtualMachineError -> 0x00a8, TryCatch #12 {ThreadDeath -> 0x00a5, VirtualMachineError -> 0x00a8, all -> 0x00a2, blocks: (B:19:0x006d, B:20:0x009d, B:31:0x00b3, B:55:0x00fc, B:57:0x0100, B:59:0x0106, B:61:0x010c, B:63:0x0110, B:64:0x012a, B:75:0x013e, B:29:0x00ac), top: B:93:0x006d }] */
    /* JADX WARN: Code duplicated, block: B:66:0x0132 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:67:0x0133 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:98:0x0138 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private int getPropertyValue(String str, int i) {
        File file;
        boolean fileExists;
        Properties properties;
        String property;
        int i2;
        FileInputStream fileInputStream;
        try {
            String systemProperty = SecuritySupport.getSystemProperty(str);
            if (systemProperty != null && systemProperty.length() > 0) {
                if (DEBUG) {
                    debugPrintln("found system property \"" + str + "\", value=" + systemProperty);
                }
                int i3 = Integer.parseInt(systemProperty);
                this.fLimitSpecified = true;
                if (i3 > 0) {
                    return i3;
                }
                return Integer.MAX_VALUE;
            }
        } catch (ThreadDeath e) {
            throw e;
        } catch (VirtualMachineError e2) {
            throw e2;
        } catch (Throwable th) {
            if (DEBUG) {
                debugPrintln(th.getClass().getName() + ": " + th.getMessage());
                th.printStackTrace();
            }
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                file = new File(SecuritySupport.getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties");
                try {
                    fileExists = SecuritySupport.getFileExists(file);
                } catch (SecurityException unused) {
                    lastModified = -1L;
                    jaxpProperties = null;
                    fileExists = false;
                }
            } catch (SecurityException unused2) {
                file = null;
            }
            try {
                try {
                    synchronized (SecureProcessingConfiguration.class) {
                        try {
                            long j = lastModified;
                            if (j >= 0) {
                                if (fileExists) {
                                    long lastModified2 = SecuritySupport.getLastModified(file);
                                    lastModified = lastModified2;
                                    if (j < lastModified2) {
                                        jaxpProperties = new Properties();
                                        fileInputStream = SecuritySupport.getFileInputStream(file);
                                        try {
                                            try {
                                                jaxpProperties.load(fileInputStream);
                                                fileInputStream2 = fileInputStream;
                                            } catch (Exception unused3) {
                                                lastModified = -1L;
                                                jaxpProperties = null;
                                                if (fileInputStream != null) {
                                                    fileInputStream.close();
                                                } else {
                                                    properties = jaxpProperties;
                                                    if (properties != null) {
                                                        if (DEBUG) {
                                                            debugPrintln("found \"" + str + "\" in jaxp.properties, value=" + property);
                                                        }
                                                        i2 = Integer.parseInt(property);
                                                        this.fLimitSpecified = true;
                                                        if (i2 > 0) {
                                                            return i2;
                                                        }
                                                        return Integer.MAX_VALUE;
                                                    }
                                                }
                                                return i;
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            fileInputStream2 = fileInputStream;
                                            if (fileInputStream2 != null) {
                                                try {
                                                    fileInputStream2.close();
                                                } catch (IOException unused4) {
                                                }
                                            }
                                            throw th;
                                        }
                                    }
                                }
                                if (!fileExists) {
                                    lastModified = -1L;
                                    jaxpProperties = null;
                                }
                            } else if (fileExists) {
                                lastModified = SecuritySupport.getLastModified(file);
                                jaxpProperties = new Properties();
                                fileInputStream = SecuritySupport.getFileInputStream(file);
                                jaxpProperties.load(fileInputStream);
                                fileInputStream2 = fileInputStream;
                            }
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                        } catch (Exception unused5) {
                            fileInputStream = null;
                        } catch (Throwable th3) {
                            th = th3;
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            throw th;
                        }
                        return i;
                    }
                } catch (Throwable th4) {
                    throw th4;
                }
            } catch (IOException unused6) {
            }
            properties = jaxpProperties;
            if (properties != null && (property = properties.getProperty(str)) != null && property.length() > 0) {
                if (DEBUG) {
                    debugPrintln("found \"" + str + "\" in jaxp.properties, value=" + property);
                }
                i2 = Integer.parseInt(property);
                this.fLimitSpecified = true;
                if (i2 > 0) {
                    return i2;
                }
                return Integer.MAX_VALUE;
            }
        } catch (ThreadDeath e3) {
            throw e3;
        } catch (VirtualMachineError e4) {
            throw e4;
        } catch (Throwable th5) {
            if (DEBUG) {
                debugPrintln(th5.getClass().getName() + ": " + th5.getMessage());
                th5.printStackTrace();
            }
        }
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:52:0x00f9 A[Catch: all -> 0x013b, DONT_GENERATE, TRY_ENTER, TRY_LEAVE, TryCatch #13 {all -> 0x013b, blocks: (B:44:0x00e9, B:52:0x00f9, B:51:0x00f6, B:67:0x0137, B:68:0x013a), top: B:87:0x00b1 }] */
    /* JADX WARN: Code duplicated, block: B:61:0x010e A[Catch: all -> 0x00a0, ThreadDeath -> 0x00a3, VirtualMachineError -> 0x00a6, TryCatch #12 {ThreadDeath -> 0x00a3, VirtualMachineError -> 0x00a6, all -> 0x00a0, blocks: (B:17:0x006b, B:18:0x009b, B:29:0x00b1, B:53:0x00fa, B:55:0x00fe, B:57:0x0104, B:59:0x010a, B:61:0x010e, B:62:0x0128, B:71:0x013d, B:27:0x00aa), top: B:94:0x006b }] */
    /* JADX WARN: Code duplicated, block: B:91:0x0137 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private boolean getPropertyValue(String str, boolean z) {
        File file;
        boolean fileExists;
        Properties properties;
        String property;
        FileInputStream fileInputStream;
        try {
            String systemProperty = SecuritySupport.getSystemProperty(str);
            if (systemProperty != null && systemProperty.length() > 0) {
                if (DEBUG) {
                    debugPrintln("found system property \"" + str + "\", value=" + systemProperty);
                }
                boolean zBooleanValue = Boolean.valueOf(systemProperty).booleanValue();
                this.fLimitSpecified = true;
                return zBooleanValue;
            }
        } catch (ThreadDeath e) {
            throw e;
        } catch (VirtualMachineError e2) {
            throw e2;
        } catch (Throwable th) {
            if (DEBUG) {
                debugPrintln(th.getClass().getName() + ": " + th.getMessage());
                th.printStackTrace();
            }
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                file = new File(SecuritySupport.getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties");
                try {
                    fileExists = SecuritySupport.getFileExists(file);
                } catch (SecurityException unused) {
                    lastModified = -1L;
                    jaxpProperties = null;
                    fileExists = false;
                }
            } catch (SecurityException unused2) {
                file = null;
            }
            try {
                try {
                    synchronized (SecureProcessingConfiguration.class) {
                        try {
                            long j = lastModified;
                            if (j >= 0) {
                                if (fileExists) {
                                    long lastModified2 = SecuritySupport.getLastModified(file);
                                    lastModified = lastModified2;
                                    if (j < lastModified2) {
                                        jaxpProperties = new Properties();
                                        fileInputStream = SecuritySupport.getFileInputStream(file);
                                        try {
                                            try {
                                                jaxpProperties.load(fileInputStream);
                                                fileInputStream2 = fileInputStream;
                                            } catch (Exception unused3) {
                                                lastModified = -1L;
                                                jaxpProperties = null;
                                                if (fileInputStream != null) {
                                                    fileInputStream.close();
                                                } else {
                                                    properties = jaxpProperties;
                                                    if (properties != null) {
                                                        if (DEBUG) {
                                                            debugPrintln("found \"" + str + "\" in jaxp.properties, value=" + property);
                                                        }
                                                        boolean zBooleanValue2 = Boolean.valueOf(property).booleanValue();
                                                        this.fLimitSpecified = true;
                                                        return zBooleanValue2;
                                                    }
                                                }
                                                return z;
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            fileInputStream2 = fileInputStream;
                                            if (fileInputStream2 != null) {
                                                try {
                                                    fileInputStream2.close();
                                                } catch (IOException unused4) {
                                                }
                                            }
                                            throw th;
                                        }
                                    }
                                }
                                if (!fileExists) {
                                    lastModified = -1L;
                                    jaxpProperties = null;
                                }
                            } else if (fileExists) {
                                lastModified = SecuritySupport.getLastModified(file);
                                jaxpProperties = new Properties();
                                fileInputStream = SecuritySupport.getFileInputStream(file);
                                jaxpProperties.load(fileInputStream);
                                fileInputStream2 = fileInputStream;
                            }
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                        } catch (Exception unused5) {
                            fileInputStream = null;
                        } catch (Throwable th3) {
                            th = th3;
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            throw th;
                        }
                        return z;
                    }
                } catch (IOException unused6) {
                }
                properties = jaxpProperties;
                if (properties != null && (property = properties.getProperty(str)) != null && property.length() > 0) {
                    if (DEBUG) {
                        debugPrintln("found \"" + str + "\" in jaxp.properties, value=" + property);
                    }
                    boolean zBooleanValue3 = Boolean.valueOf(property).booleanValue();
                    this.fLimitSpecified = true;
                    return zBooleanValue3;
                }
            } catch (Throwable th4) {
                throw th4;
            }
        } catch (ThreadDeath e3) {
            throw e3;
        } catch (VirtualMachineError e4) {
            throw e4;
        } catch (Throwable th5) {
            if (DEBUG) {
                debugPrintln(th5.getClass().getName() + ": " + th5.getMessage());
                th5.printStackTrace();
            }
        }
        return z;
    }

    private static boolean isDebugEnabled() {
        try {
            String systemProperty = SecuritySupport.getSystemProperty("xerces.debug");
            return (systemProperty == null || "false".equals(systemProperty)) ? false : true;
        } catch (SecurityException unused) {
            return false;
        }
    }

    protected void checkEntitySizeLimits(int i, int i2, boolean z) {
        int i3 = this.fTotalEntitySize + i2;
        this.fTotalEntitySize = i3;
        if (i3 > this.TOTAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "TotalEntitySizeLimitExceeded", new Object[]{new Integer(this.TOTAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE)}, (short) 2);
        }
        if (z) {
            if (i > this.MAX_PARAMETER_ENTITY_SIZE_LIMIT_SYSTEM_VALUE) {
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MaxParameterEntitySizeLimitExceeded", new Object[]{new Integer(this.MAX_PARAMETER_ENTITY_SIZE_LIMIT_SYSTEM_VALUE)}, (short) 2);
            }
        } else if (i > this.MAX_GENERAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MaxGeneralEntitySizeLimitExceeded", new Object[]{new Integer(this.MAX_GENERAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE)}, (short) 2);
        }
    }

    @Override // org.apache.xerces.parsers.XIncludeAwareParserConfiguration, org.apache.xerces.parsers.XML11Configuration
    protected void configurePipeline() {
        super.configurePipeline();
        configurePipelineCommon(true);
    }

    @Override // org.apache.xerces.parsers.XIncludeAwareParserConfiguration, org.apache.xerces.parsers.XML11Configuration
    protected void configureXML11Pipeline() {
        super.configureXML11Pipeline();
        configurePipelineCommon(false);
    }

    @Override // org.apache.xerces.parsers.XML11Configuration, org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLComponentManager, org.apache.xerces.xni.parser.XMLParserConfiguration
    public Object getProperty(String str) throws XMLConfigurationException {
        if (SECURITY_MANAGER_PROPERTY.equals(str)) {
            return this.fSecurityManager;
        }
        return "http://apache.org/xml/properties/internal/entity-resolver".equals(str) ? this.fExternalEntityMonitor : super.getProperty(str);
    }

    @Override // org.apache.xerces.parsers.XML11Configuration, org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (SECURITY_MANAGER_PROPERTY.equals(str)) {
            if (obj == null && this.fJavaSecurityManagerEnabled) {
                return;
            }
            SecurityManager securityManager = (SecurityManager) obj;
            this.fSecurityManager = securityManager;
            if (securityManager != null) {
                if (securityManager.getEntityExpansionLimit() == 100000) {
                    this.fSecurityManager.setEntityExpansionLimit(this.ENTITY_EXPANSION_LIMIT_SYSTEM_VALUE);
                }
                if (this.fSecurityManager.getMaxOccurNodeLimit() == 3000) {
                    this.fSecurityManager.setMaxOccurNodeLimit(this.MAX_OCCUR_LIMIT_SYSTEM_VALUE);
                }
            }
        } else if ("http://apache.org/xml/properties/internal/entity-resolver".equals(str)) {
            this.fExternalEntityMonitor.setEntityResolver((XMLEntityResolver) obj);
            return;
        }
        super.setProperty(str, obj);
    }
}
