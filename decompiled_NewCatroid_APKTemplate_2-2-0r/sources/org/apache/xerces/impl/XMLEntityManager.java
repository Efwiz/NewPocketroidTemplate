package org.apache.xerces.impl;

import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import org.apache.commons.lang3.CharEncoding;
import org.apache.xerces.impl.io.ASCIIReader;
import org.apache.xerces.impl.io.Latin1Reader;
import org.apache.xerces.impl.io.UCSReader;
import org.apache.xerces.impl.io.UTF16Reader;
import org.apache.xerces.impl.io.UTF8Reader;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.util.AugmentationsImpl;
import org.apache.xerces.util.EncodingMap;
import org.apache.xerces.util.HTTPInputSource;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLEntityDescriptionImpl;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.catrobat.catroid.BuildConfig;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class XMLEntityManager implements XMLComponent, XMLEntityResolver {
    private static final boolean DEBUG_BUFFER = false;
    private static final boolean DEBUG_ENCODINGS = false;
    private static final boolean DEBUG_ENTITIES = false;
    private static final boolean DEBUG_RESOLVER = false;
    public static final int DEFAULT_BUFFER_SIZE = 2048;
    public static final int DEFAULT_INTERNAL_BUFFER_SIZE = 512;
    public static final int DEFAULT_XMLDECL_BUFFER_SIZE = 64;
    protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static PrivilegedAction GET_USER_DIR_SYSTEM_PROPERTY = null;
    protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private static String gUserDir;
    private static URI gUserDirURI;
    protected boolean fAllowJavaEncodings;
    protected int fBufferSize;
    private final CharacterBufferPool fCharacterBufferPool;
    protected ScannedEntity fCurrentEntity;
    protected Hashtable fDeclaredEntities;
    protected final Hashtable fEntities;
    private final Augmentations fEntityAugs;
    protected int fEntityExpansionCount;
    protected int fEntityExpansionLimit;
    protected XMLEntityHandler fEntityHandler;
    protected XMLEntityResolver fEntityResolver;
    protected XMLEntityScanner fEntityScanner;
    protected final Stack fEntityStack;
    protected XMLErrorReporter fErrorReporter;
    protected boolean fExternalGeneralEntities;
    protected boolean fExternalParameterEntities;
    protected boolean fHasPEReferences;
    protected boolean fInExternalSubset;
    private final ByteBufferPool fLargeByteBufferPool;
    protected Stack fReaderStack;
    private final XMLResourceIdentifierImpl fResourceIdentifier;
    protected SecurityManager fSecurityManager;
    private final ByteBufferPool fSmallByteBufferPool;
    protected boolean fStandalone;
    protected boolean fStrictURI;
    protected SymbolTable fSymbolTable;
    private byte[] fTempByteBuffer;
    protected boolean fValidation;
    protected ValidationManager fValidationManager;
    protected boolean fWarnDuplicateEntityDef;
    protected XMLEntityScanner fXML10EntityScanner;
    protected XMLEntityScanner fXML11EntityScanner;
    protected static final String VALIDATION = "http://xml.org/sax/features/validation";
    protected static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    protected static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
    protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
    protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
    protected static final String STANDARD_URI_CONFORMANT = "http://apache.org/xml/features/standard-uri-conformant";
    private static final String[] RECOGNIZED_FEATURES = {VALIDATION, EXTERNAL_GENERAL_ENTITIES, EXTERNAL_PARAMETER_ENTITIES, ALLOW_JAVA_ENCODINGS, WARN_ON_DUPLICATE_ENTITYDEF, STANDARD_URI_CONFORMANT};
    private static final Boolean[] FEATURE_DEFAULTS = {null, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    protected static final String BUFFER_SIZE = "http://apache.org/xml/properties/input-buffer-size";
    protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private static final String[] RECOGNIZED_PROPERTIES = {"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-resolver", VALIDATION_MANAGER, BUFFER_SIZE, SECURITY_MANAGER};
    private static final Object[] PROPERTY_DEFAULTS = {null, null, null, null, new Integer(2048), null};
    private static final String XMLEntity = "[xml]".intern();
    private static final String DTDEntity = "[dtd]".intern();
    private static final boolean[] gNeedEscaping = new boolean[128];
    private static final char[] gAfterEscaping1 = new char[128];
    private static final char[] gAfterEscaping2 = new char[128];
    private static final char[] gHexChs = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'A', Util.C_BYTE, Util.C_CHAR, Util.C_DOUBLE, 'E', Util.C_FLOAT};

    private static final class ByteBufferPool {
        private static final int DEFAULT_POOL_SIZE = 3;
        private int fBufferSize;
        private byte[][] fByteBufferPool;
        private int fDepth;
        private int fPoolSize;

        public ByteBufferPool(int i) {
            this(3, i);
        }

        public ByteBufferPool(int i, int i2) {
            this.fPoolSize = i;
            this.fBufferSize = i2;
            this.fByteBufferPool = new byte[i][];
            this.fDepth = 0;
        }

        public byte[] getBuffer() {
            int i = this.fDepth;
            if (i <= 0) {
                return new byte[this.fBufferSize];
            }
            byte[][] bArr = this.fByteBufferPool;
            int i2 = i - 1;
            this.fDepth = i2;
            return bArr[i2];
        }

        public void returnBuffer(byte[] bArr) {
            int i = this.fDepth;
            byte[][] bArr2 = this.fByteBufferPool;
            if (i < bArr2.length) {
                this.fDepth = i + 1;
                bArr2[i] = bArr;
            }
        }

        public void setBufferSize(int i) {
            this.fBufferSize = i;
            this.fByteBufferPool = new byte[this.fPoolSize][];
            this.fDepth = 0;
        }
    }

    private static final class CharacterBuffer {
        private final char[] ch;
        private final boolean isExternal;

        public CharacterBuffer(boolean z, int i) {
            this.isExternal = z;
            this.ch = new char[i];
        }
    }

    private static final class CharacterBufferPool {
        private static final int DEFAULT_POOL_SIZE = 3;
        private CharacterBuffer[] fExternalBufferPool;
        private int fExternalBufferSize;
        private int fExternalTop;
        private CharacterBuffer[] fInternalBufferPool;
        private int fInternalBufferSize;
        private int fInternalTop;
        private int fPoolSize;

        public CharacterBufferPool(int i, int i2) {
            this(3, i, i2);
        }

        public CharacterBufferPool(int i, int i2, int i3) {
            this.fExternalBufferSize = i2;
            this.fInternalBufferSize = i3;
            this.fPoolSize = i;
            init();
        }

        private void init() {
            int i = this.fPoolSize;
            this.fInternalBufferPool = new CharacterBuffer[i];
            this.fExternalBufferPool = new CharacterBuffer[i];
            this.fInternalTop = -1;
            this.fExternalTop = -1;
        }

        public CharacterBuffer getBuffer(boolean z) {
            if (z) {
                int i = this.fExternalTop;
                if (i <= -1) {
                    return new CharacterBuffer(true, this.fExternalBufferSize);
                }
                CharacterBuffer[] characterBufferArr = this.fExternalBufferPool;
                this.fExternalTop = i - 1;
                return characterBufferArr[i];
            }
            int i2 = this.fInternalTop;
            if (i2 <= -1) {
                return new CharacterBuffer(false, this.fInternalBufferSize);
            }
            CharacterBuffer[] characterBufferArr2 = this.fInternalBufferPool;
            this.fInternalTop = i2 - 1;
            return characterBufferArr2[i2];
        }

        public void returnBuffer(CharacterBuffer characterBuffer) {
            if (characterBuffer.isExternal) {
                int i = this.fExternalTop;
                CharacterBuffer[] characterBufferArr = this.fExternalBufferPool;
                if (i < characterBufferArr.length - 1) {
                    int i2 = i + 1;
                    this.fExternalTop = i2;
                    characterBufferArr[i2] = characterBuffer;
                    return;
                }
                return;
            }
            int i3 = this.fInternalTop;
            CharacterBuffer[] characterBufferArr2 = this.fInternalBufferPool;
            if (i3 < characterBufferArr2.length - 1) {
                int i4 = i3 + 1;
                this.fInternalTop = i4;
                characterBufferArr2[i4] = characterBuffer;
            }
        }

        public void setExternalBufferSize(int i) {
            this.fExternalBufferSize = i;
            this.fExternalBufferPool = new CharacterBuffer[this.fPoolSize];
            this.fExternalTop = -1;
        }
    }

    private static class EncodingInfo {
        public final String autoDetectedEncoding;
        public final boolean hasBOM;
        public final Boolean isBigEndian;
        public final String readerEncoding;
        public static final EncodingInfo UTF_8 = new EncodingInfo("UTF-8", null, false);
        public static final EncodingInfo UTF_8_WITH_BOM = new EncodingInfo("UTF-8", null, true);
        public static final EncodingInfo UTF_16_BIG_ENDIAN = new EncodingInfo(CharEncoding.UTF_16BE, CharEncoding.UTF_16, Boolean.TRUE, false);
        public static final EncodingInfo UTF_16_BIG_ENDIAN_WITH_BOM = new EncodingInfo(CharEncoding.UTF_16BE, CharEncoding.UTF_16, Boolean.TRUE, true);
        public static final EncodingInfo UTF_16_LITTLE_ENDIAN = new EncodingInfo(CharEncoding.UTF_16LE, CharEncoding.UTF_16, Boolean.FALSE, false);
        public static final EncodingInfo UTF_16_LITTLE_ENDIAN_WITH_BOM = new EncodingInfo(CharEncoding.UTF_16LE, CharEncoding.UTF_16, Boolean.FALSE, true);
        public static final EncodingInfo UCS_4_BIG_ENDIAN = new EncodingInfo("ISO-10646-UCS-4", Boolean.TRUE, false);
        public static final EncodingInfo UCS_4_LITTLE_ENDIAN = new EncodingInfo("ISO-10646-UCS-4", Boolean.FALSE, false);
        public static final EncodingInfo UCS_4_UNUSUAL_BYTE_ORDER = new EncodingInfo("ISO-10646-UCS-4", null, false);
        public static final EncodingInfo EBCDIC = new EncodingInfo("CP037", null, false);

        private EncodingInfo(String str, Boolean bool, boolean z) {
            this(str, str, bool, z);
        }

        private EncodingInfo(String str, String str2, Boolean bool, boolean z) {
            this.autoDetectedEncoding = str;
            this.readerEncoding = str2;
            this.isBigEndian = bool;
            this.hasBOM = z;
        }
    }

    public static abstract class Entity {
        public boolean inExternalSubset;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f472name;

        public Entity() {
            clear();
        }

        public Entity(String str, boolean z) {
            this.f472name = str;
            this.inExternalSubset = z;
        }

        public void clear() {
            this.f472name = null;
            this.inExternalSubset = false;
        }

        public boolean isEntityDeclInExternalSubset() {
            return this.inExternalSubset;
        }

        public abstract boolean isExternal();

        public abstract boolean isUnparsed();

        public void setValues(Entity entity) {
            this.f472name = entity.f472name;
            this.inExternalSubset = entity.inExternalSubset;
        }
    }

    protected static class ExternalEntity extends Entity {
        public XMLResourceIdentifier entityLocation;
        public String notation;

        public ExternalEntity() {
            clear();
        }

        public ExternalEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, boolean z) {
            super(str, z);
            this.entityLocation = xMLResourceIdentifier;
            this.notation = str2;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public void clear() {
            super.clear();
            this.entityLocation = null;
            this.notation = null;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public final boolean isExternal() {
            return true;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public final boolean isUnparsed() {
            return this.notation != null;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public void setValues(Entity entity) {
            super.setValues(entity);
            this.entityLocation = null;
            this.notation = null;
        }

        public void setValues(ExternalEntity externalEntity) {
            super.setValues((Entity) externalEntity);
            this.entityLocation = externalEntity.entityLocation;
            this.notation = externalEntity.notation;
        }
    }

    protected static class InternalEntity extends Entity {
        public int paramEntityRefs;
        public String text;

        public InternalEntity() {
            clear();
        }

        public InternalEntity(String str, String str2, boolean z) {
            super(str, z);
            this.text = str2;
        }

        public InternalEntity(String str, String str2, boolean z, int i) {
            this(str, str2, z);
            this.paramEntityRefs = i;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public void clear() {
            super.clear();
            this.text = null;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public final boolean isExternal() {
            return false;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public final boolean isUnparsed() {
            return false;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public void setValues(Entity entity) {
            super.setValues(entity);
            this.text = null;
        }

        public void setValues(InternalEntity internalEntity) {
            super.setValues((Entity) internalEntity);
            this.text = internalEntity.text;
        }
    }

    protected final class RewindableInputStream extends InputStream {
        private InputStream fInputStream;
        private byte[] fData = new byte[64];
        private int fStartOffset = 0;
        private int fEndOffset = -1;
        private int fOffset = 0;
        private int fLength = 0;
        private int fMark = 0;

        public RewindableInputStream(InputStream inputStream) {
            this.fInputStream = inputStream;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            int i = this.fLength;
            int i2 = this.fOffset;
            int i3 = i - i2;
            if (i3 != 0) {
                return i3;
            }
            if (i2 == this.fEndOffset) {
                return -1;
            }
            if (XMLEntityManager.this.fCurrentEntity.mayReadChunks) {
                return this.fInputStream.available();
            }
            return 0;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            InputStream inputStream = this.fInputStream;
            if (inputStream != null) {
                inputStream.close();
                this.fInputStream = null;
            }
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            this.fMark = this.fOffset;
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return true;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            int i = this.fOffset;
            if (i < this.fLength) {
                byte[] bArr = this.fData;
                this.fOffset = i + 1;
                return bArr[i] & 255;
            }
            if (i == this.fEndOffset) {
                return -1;
            }
            return XMLEntityManager.this.fCurrentEntity.mayReadChunks ? this.fInputStream.read() : readAndBuffer();
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3 = this.fLength;
            int i4 = this.fOffset;
            int i5 = i3 - i4;
            if (i5 != 0) {
                if (i2 >= i5) {
                    i2 = i5;
                } else if (i2 <= 0) {
                    return 0;
                }
                if (bArr != null) {
                    System.arraycopy(this.fData, i4, bArr, i, i2);
                }
                this.fOffset += i2;
                return i2;
            }
            if (i4 == this.fEndOffset) {
                return -1;
            }
            if (XMLEntityManager.this.fCurrentEntity.mayReadChunks) {
                return this.fInputStream.read(bArr, i, i2);
            }
            int andBuffer = readAndBuffer();
            if (andBuffer == -1) {
                this.fEndOffset = this.fOffset;
                return -1;
            }
            bArr[i] = (byte) andBuffer;
            return 1;
        }

        public int readAndBuffer() throws IOException {
            int i = this.fOffset;
            byte[] bArr = this.fData;
            if (i == bArr.length) {
                byte[] bArr2 = new byte[i << 1];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                this.fData = bArr2;
            }
            int i2 = this.fInputStream.read();
            if (i2 == -1) {
                this.fEndOffset = this.fOffset;
                return -1;
            }
            byte[] bArr3 = this.fData;
            int i3 = this.fLength;
            this.fLength = i3 + 1;
            bArr3[i3] = (byte) i2;
            this.fOffset++;
            return i2 & 255;
        }

        @Override // java.io.InputStream
        public void reset() {
            this.fOffset = this.fMark;
        }

        public void rewind() {
            this.fOffset = this.fStartOffset;
        }

        public void setStartOffset(int i) {
            this.fStartOffset = i;
        }

        @Override // java.io.InputStream
        public long skip(long j) throws IOException {
            if (j <= 0) {
                return 0L;
            }
            int i = this.fLength;
            int i2 = this.fOffset;
            int i3 = i - i2;
            if (i3 == 0) {
                if (i2 == this.fEndOffset) {
                    return 0L;
                }
                return this.fInputStream.skip(j);
            }
            long j2 = i3;
            if (j <= j2) {
                this.fOffset = (int) (((long) i2) + j);
                return j;
            }
            int i4 = i2 + i3;
            this.fOffset = i4;
            if (i4 == this.fEndOffset) {
                return j2;
            }
            return this.fInputStream.skip(j - j2) + j2;
        }
    }

    public class ScannedEntity extends Entity {
        public int baseCharOffset;
        public char[] ch;
        public int columnNumber;
        public int count;
        public String encoding;
        public XMLResourceIdentifier entityLocation;
        boolean externallySpecifiedEncoding;
        private byte[] fByteBuffer;
        private CharacterBuffer fCharacterBuffer;
        public boolean isExternal;
        public int lineNumber;
        public boolean literal;
        public boolean mayReadChunks;
        public int position;
        public Reader reader;
        public int startPosition;
        public InputStream stream;
        public String xmlVersion;

        public ScannedEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, InputStream inputStream, Reader reader, byte[] bArr, String str2, boolean z, boolean z2, boolean z3) {
            super(str, XMLEntityManager.this.fInExternalSubset);
            this.lineNumber = 1;
            this.columnNumber = 1;
            this.externallySpecifiedEncoding = false;
            this.xmlVersion = BuildConfig.VERSION_NAME;
            this.ch = null;
            this.entityLocation = xMLResourceIdentifier;
            this.stream = inputStream;
            this.reader = reader;
            this.encoding = str2;
            this.literal = z;
            this.mayReadChunks = z2;
            this.isExternal = z3;
            CharacterBuffer buffer = XMLEntityManager.this.fCharacterBufferPool.getBuffer(z3);
            this.fCharacterBuffer = buffer;
            this.ch = buffer.ch;
            this.fByteBuffer = bArr;
        }

        public int getCharacterOffset() {
            for (int size = XMLEntityManager.this.fEntityStack.size() - 1; size >= 0; size--) {
                ScannedEntity scannedEntity = (ScannedEntity) XMLEntityManager.this.fEntityStack.elementAt(size);
                if (scannedEntity.isExternal()) {
                    return scannedEntity.baseCharOffset + (scannedEntity.position - scannedEntity.startPosition);
                }
            }
            return -1;
        }

        public int getColumnNumber() {
            for (int size = XMLEntityManager.this.fEntityStack.size() - 1; size >= 0; size--) {
                ScannedEntity scannedEntity = (ScannedEntity) XMLEntityManager.this.fEntityStack.elementAt(size);
                if (scannedEntity.isExternal()) {
                    return scannedEntity.columnNumber;
                }
            }
            return -1;
        }

        public String getEncoding() {
            for (int size = XMLEntityManager.this.fEntityStack.size() - 1; size >= 0; size--) {
                ScannedEntity scannedEntity = (ScannedEntity) XMLEntityManager.this.fEntityStack.elementAt(size);
                if (scannedEntity.isExternal()) {
                    return scannedEntity.encoding;
                }
            }
            return null;
        }

        public String getExpandedSystemId() {
            for (int size = XMLEntityManager.this.fEntityStack.size() - 1; size >= 0; size--) {
                ScannedEntity scannedEntity = (ScannedEntity) XMLEntityManager.this.fEntityStack.elementAt(size);
                XMLResourceIdentifier xMLResourceIdentifier = scannedEntity.entityLocation;
                if (xMLResourceIdentifier != null && xMLResourceIdentifier.getExpandedSystemId() != null) {
                    return scannedEntity.entityLocation.getExpandedSystemId();
                }
            }
            return null;
        }

        public int getLineNumber() {
            for (int size = XMLEntityManager.this.fEntityStack.size() - 1; size >= 0; size--) {
                ScannedEntity scannedEntity = (ScannedEntity) XMLEntityManager.this.fEntityStack.elementAt(size);
                if (scannedEntity.isExternal()) {
                    return scannedEntity.lineNumber;
                }
            }
            return -1;
        }

        public String getLiteralSystemId() {
            for (int size = XMLEntityManager.this.fEntityStack.size() - 1; size >= 0; size--) {
                ScannedEntity scannedEntity = (ScannedEntity) XMLEntityManager.this.fEntityStack.elementAt(size);
                XMLResourceIdentifier xMLResourceIdentifier = scannedEntity.entityLocation;
                if (xMLResourceIdentifier != null && xMLResourceIdentifier.getLiteralSystemId() != null) {
                    return scannedEntity.entityLocation.getLiteralSystemId();
                }
            }
            return null;
        }

        public String getXMLVersion() {
            for (int size = XMLEntityManager.this.fEntityStack.size() - 1; size >= 0; size--) {
                ScannedEntity scannedEntity = (ScannedEntity) XMLEntityManager.this.fEntityStack.elementAt(size);
                if (scannedEntity.isExternal()) {
                    return scannedEntity.xmlVersion;
                }
            }
            return null;
        }

        public boolean isEncodingExternallySpecified() {
            return this.externallySpecifiedEncoding;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public final boolean isExternal() {
            return this.isExternal;
        }

        @Override // org.apache.xerces.impl.XMLEntityManager.Entity
        public final boolean isUnparsed() {
            return false;
        }

        public void setEncodingExternallySpecified(boolean z) {
            this.externallySpecifiedEncoding = z;
        }

        public void setReader(InputStream inputStream, String str, Boolean bool) throws IOException {
            XMLEntityManager.this.fTempByteBuffer = this.fByteBuffer;
            this.reader = XMLEntityManager.this.createReader(inputStream, str, bool);
            this.fByteBuffer = XMLEntityManager.this.fTempByteBuffer;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer("name=\"");
            stringBuffer.append(this.f472name).append("\",ch=");
            stringBuffer.append(this.ch);
            stringBuffer.append(",position=").append(this.position);
            stringBuffer.append(",count=").append(this.count);
            stringBuffer.append(",baseCharOffset=").append(this.baseCharOffset);
            stringBuffer.append(",startPosition=").append(this.startPosition);
            return stringBuffer.toString();
        }
    }

    static {
        for (int i = 0; i <= 31; i++) {
            gNeedEscaping[i] = true;
            char[] cArr = gAfterEscaping1;
            char[] cArr2 = gHexChs;
            cArr[i] = cArr2[i >> 4];
            gAfterEscaping2[i] = cArr2[i & 15];
        }
        gNeedEscaping[127] = true;
        gAfterEscaping1[127] = '7';
        gAfterEscaping2[127] = Util.C_FLOAT;
        char[] cArr3 = {StringUtil.SPACE, '<', '>', '#', MangleConstant.PLATFORM_FUNCTION_MARKER, '\"', '{', '}', '|', '\\', Util.C_EXCEPTION_START, '~', '[', ']', '`'};
        for (int i2 = 0; i2 < 15; i2++) {
            char c = cArr3[i2];
            gNeedEscaping[c] = true;
            char[] cArr4 = gAfterEscaping1;
            char[] cArr5 = gHexChs;
            cArr4[c] = cArr5[c >> 4];
            gAfterEscaping2[c] = cArr5[c & 15];
        }
        GET_USER_DIR_SYSTEM_PROPERTY = new PrivilegedAction() { // from class: org.apache.xerces.impl.XMLEntityManager.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                return System.getProperty("user.dir");
            }
        };
    }

    public XMLEntityManager() {
        this(null);
    }

    public XMLEntityManager(XMLEntityManager xMLEntityManager) {
        this.fExternalGeneralEntities = true;
        this.fExternalParameterEntities = true;
        this.fBufferSize = 2048;
        this.fSecurityManager = null;
        this.fInExternalSubset = false;
        this.fEntityExpansionLimit = 0;
        this.fEntityExpansionCount = 0;
        this.fEntities = new Hashtable();
        this.fEntityStack = new Stack();
        this.fResourceIdentifier = new XMLResourceIdentifierImpl();
        this.fEntityAugs = new AugmentationsImpl();
        this.fSmallByteBufferPool = new ByteBufferPool(this.fBufferSize);
        this.fLargeByteBufferPool = new ByteBufferPool(this.fBufferSize << 1);
        this.fTempByteBuffer = null;
        this.fCharacterBufferPool = new CharacterBufferPool(this.fBufferSize, 512);
        this.fReaderStack = new Stack();
        this.fDeclaredEntities = xMLEntityManager != null ? xMLEntityManager.getDeclaredEntities() : null;
        setScannerVersion((short) 1);
    }

    public static void absolutizeAgainstUserDir(URI uri) throws URI.MalformedURIException {
        uri.absolutize(getUserDir());
    }

    private Reader createASCIIReader(InputStream inputStream) {
        if (this.fTempByteBuffer == null) {
            this.fTempByteBuffer = this.fSmallByteBufferPool.getBuffer();
        }
        return new ASCIIReader(inputStream, this.fTempByteBuffer, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }

    private Reader createLatin1Reader(InputStream inputStream) {
        if (this.fTempByteBuffer == null) {
            this.fTempByteBuffer = this.fSmallByteBufferPool.getBuffer();
        }
        return new Latin1Reader(inputStream, this.fTempByteBuffer);
    }

    public static OutputStream createOutputStream(String str) throws IOException {
        File parentFile;
        String strExpandSystemId = expandSystemId(str, null, true);
        if (strExpandSystemId != null) {
            str = strExpandSystemId;
        }
        URL url = new URL(str);
        String protocol = url.getProtocol();
        String host = url.getHost();
        if (protocol.equals("file") && (host == null || host.length() == 0 || host.equals("localhost"))) {
            File file = new File(getPathWithoutEscapes(url.getPath()));
            if (!file.exists() && (parentFile = file.getParentFile()) != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            return new FileOutputStream(file);
        }
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        uRLConnectionOpenConnection.setDoInput(false);
        uRLConnectionOpenConnection.setDoOutput(true);
        uRLConnectionOpenConnection.setUseCaches(false);
        if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) uRLConnectionOpenConnection).setRequestMethod("PUT");
        }
        return uRLConnectionOpenConnection.getOutputStream();
    }

    private Reader createUTF16Reader(InputStream inputStream, boolean z) {
        byte[] bArr = this.fTempByteBuffer;
        if (bArr == null) {
            this.fTempByteBuffer = this.fLargeByteBufferPool.getBuffer();
        } else if (bArr.length == this.fBufferSize) {
            this.fSmallByteBufferPool.returnBuffer(bArr);
            this.fTempByteBuffer = this.fLargeByteBufferPool.getBuffer();
        }
        return new UTF16Reader(inputStream, this.fTempByteBuffer, z, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }

    private Reader createUTF8Reader(InputStream inputStream) {
        if (this.fTempByteBuffer == null) {
            this.fTempByteBuffer = this.fSmallByteBufferPool.getBuffer();
        }
        return new UTF8Reader(inputStream, this.fTempByteBuffer, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0064 A[Catch: Exception -> 0x0072, TryCatch #1 {Exception -> 0x0072, blocks: (B:15:0x001d, B:17:0x0023, B:20:0x002a, B:28:0x0068, B:23:0x003a, B:25:0x0041, B:26:0x0056, B:27:0x0064), top: B:37:0x001d, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0074 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:32:0x0075  */
    public static String expandSystemId(String str, String str2, boolean z) throws URI.MalformedURIException {
        URI uri;
        URI uri2 = null;
        if (str == null) {
            return null;
        }
        if (z) {
            return expandSystemIdStrictOn(str, str2);
        }
        try {
            return expandSystemIdStrictOff(str, str2);
        } catch (URI.MalformedURIException unused) {
            if (str.length() == 0) {
                return str;
            }
            String strFixURI = fixURI(str);
            if (str2 != null) {
                try {
                    if (str2.length() == 0 || str2.equals(str)) {
                        uri = getUserDir();
                    } else {
                        try {
                            uri = new URI(fixURI(str2).trim());
                        } catch (URI.MalformedURIException unused2) {
                            uri = str2.indexOf(58) != -1 ? new URI("file", "", fixURI(str2).trim(), null, null) : new URI(getUserDir(), fixURI(str2));
                        }
                    }
                    uri2 = new URI(uri, strFixURI.trim());
                } catch (Exception unused3) {
                    if (uri2 == null) {
                        return str;
                    }
                    return uri2.toString();
                }
            } else {
                uri = getUserDir();
                uri2 = new URI(uri, strFixURI.trim());
            }
            if (uri2 == null) {
                return str;
            }
            return uri2.toString();
        }
    }

    private static String expandSystemIdStrictOff(String str, String str2) throws URI.MalformedURIException {
        URI userDir;
        URI uri = new URI(str, true);
        if (uri.isAbsoluteURI()) {
            if (uri.getScheme().length() > 1) {
                return str;
            }
            throw new URI.MalformedURIException();
        }
        if (str2 == null || str2.length() == 0) {
            userDir = getUserDir();
        } else {
            userDir = new URI(str2, true);
            if (!userDir.isAbsoluteURI()) {
                userDir.absolutize(getUserDir());
            }
        }
        uri.absolutize(userDir);
        return uri.toString();
    }

    private static String expandSystemIdStrictOn(String str, String str2) throws URI.MalformedURIException {
        URI userDir;
        URI uri = new URI(str, true);
        if (uri.isAbsoluteURI()) {
            return str;
        }
        if (str2 == null || str2.length() == 0) {
            userDir = getUserDir();
        } else {
            userDir = new URI(str2, true);
            if (!userDir.isAbsoluteURI()) {
                userDir.absolutize(getUserDir());
            }
        }
        uri.absolutize(userDir);
        return uri.toString();
    }

    protected static String fixURI(String str) {
        String strReplace = str.replace(File.separatorChar, '/');
        StringBuffer stringBuffer = null;
        if (strReplace.length() >= 2) {
            char cCharAt = strReplace.charAt(1);
            if (cCharAt == ':') {
                char upperCase = Character.toUpperCase(strReplace.charAt(0));
                if (upperCase >= 'A' && upperCase <= 'Z') {
                    StringBuffer stringBuffer2 = new StringBuffer(strReplace.length() + 8);
                    stringBuffer2.append("file:///");
                    stringBuffer = stringBuffer2;
                }
            } else if (cCharAt == '/' && strReplace.charAt(0) == '/') {
                stringBuffer = new StringBuffer(strReplace.length() + 5);
                stringBuffer.append("file:");
            }
        }
        int iIndexOf = strReplace.indexOf(32);
        if (iIndexOf >= 0) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer(strReplace.length());
            }
            for (int i = 0; i < iIndexOf; i++) {
                stringBuffer.append(strReplace.charAt(i));
            }
            stringBuffer.append("%20");
            for (int i2 = iIndexOf + 1; i2 < strReplace.length(); i2++) {
                if (strReplace.charAt(i2) == ' ') {
                    stringBuffer.append("%20");
                } else {
                    stringBuffer.append(strReplace.charAt(i2));
                }
            }
        } else {
            if (stringBuffer == null) {
                return strReplace;
            }
            stringBuffer.append(strReplace);
        }
        return stringBuffer.toString();
    }

    private static String getPathWithoutEscapes(String str) {
        if (str == null || str.length() == 0 || str.indexOf(37) == -1) {
            return str;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, "%");
        StringBuffer stringBuffer = new StringBuffer(str.length());
        int iCountTokens = stringTokenizer.countTokens();
        stringBuffer.append(stringTokenizer.nextToken());
        for (int i = 1; i < iCountTokens; i++) {
            String strNextToken = stringTokenizer.nextToken();
            stringBuffer.append((char) Integer.valueOf(strNextToken.substring(0, 2), 16).intValue());
            stringBuffer.append(strNextToken.substring(2));
        }
        return stringBuffer.toString();
    }

    private static synchronized URI getUserDir() throws URI.MalformedURIException {
        char c;
        char cCharAt;
        char upperCase;
        String str = "";
        try {
            str = (String) AccessController.doPrivileged(GET_USER_DIR_SYSTEM_PROPERTY);
        } catch (SecurityException unused) {
        }
        if (str.length() == 0) {
            return new URI("file", "", "", null, null);
        }
        if (gUserDirURI != null && str.equals(gUserDir)) {
            return gUserDirURI;
        }
        gUserDir = str;
        String strReplace = str.replace(File.separatorChar, '/');
        int length = strReplace.length();
        StringBuffer stringBuffer = new StringBuffer(length * 3);
        if (length >= 2 && strReplace.charAt(1) == ':' && (upperCase = Character.toUpperCase(strReplace.charAt(0))) >= 'A' && upperCase <= 'Z') {
            stringBuffer.append('/');
        }
        int i = 0;
        while (i < length && (cCharAt = strReplace.charAt(i)) < 128) {
            if (gNeedEscaping[cCharAt]) {
                stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                stringBuffer.append(gAfterEscaping1[cCharAt]);
                stringBuffer.append(gAfterEscaping2[cCharAt]);
            } else {
                stringBuffer.append(cCharAt);
            }
            i++;
        }
        if (i < length) {
            try {
                for (byte b : strReplace.substring(i).getBytes("UTF-8")) {
                    if (b < 0) {
                        int i2 = b + 256;
                        stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        char[] cArr = gHexChs;
                        stringBuffer.append(cArr[i2 >> 4]);
                        c = cArr[i2 & 15];
                    } else {
                        if (gNeedEscaping[b]) {
                            stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                            stringBuffer.append(gAfterEscaping1[b]);
                            c = gAfterEscaping2[b];
                        } else {
                            stringBuffer.append((char) b);
                        }
                    }
                    stringBuffer.append(c);
                }
            } catch (UnsupportedEncodingException unused2) {
                return new URI("file", "", strReplace, null, null);
            }
        }
        if (!strReplace.endsWith("/")) {
            stringBuffer.append('/');
        }
        URI uri = new URI("file", "", stringBuffer.toString(), null, null);
        gUserDirURI = uri;
        return uri;
    }

    static final void print(ScannedEntity scannedEntity) {
    }

    public void addExternalEntity(String str, String str2, String str3, String str4) throws IOException {
        ScannedEntity scannedEntity;
        if (this.fEntities.containsKey(str)) {
            if (this.fWarnDuplicateEntityDef) {
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[]{str}, (short) 0);
                return;
            }
            return;
        }
        if (str4 == null) {
            int size = this.fEntityStack.size();
            if (size == 0 && (scannedEntity = this.fCurrentEntity) != null && scannedEntity.entityLocation != null) {
                str4 = this.fCurrentEntity.entityLocation.getExpandedSystemId();
            }
            for (int i = size - 1; i >= 0; i--) {
                ScannedEntity scannedEntity2 = (ScannedEntity) this.fEntityStack.elementAt(i);
                if (scannedEntity2.entityLocation != null && scannedEntity2.entityLocation.getExpandedSystemId() != null) {
                    str4 = scannedEntity2.entityLocation.getExpandedSystemId();
                    break;
                }
            }
        }
        String str5 = str4;
        this.fEntities.put(str, new ExternalEntity(str, new XMLEntityDescriptionImpl(str, str2, str3, str5, expandSystemId(str3, str5, false)), null, this.fInExternalSubset));
    }

    public void addInternalEntity(String str, String str2) {
        addInternalEntity(str, str2, 0);
    }

    public void addInternalEntity(String str, String str2, int i) {
        if (!this.fEntities.containsKey(str)) {
            this.fEntities.put(str, new InternalEntity(str, str2, this.fInExternalSubset, i));
        } else if (this.fWarnDuplicateEntityDef) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[]{str}, (short) 0);
        }
    }

    public void addUnparsedEntity(String str, String str2, String str3, String str4, String str5) {
        if (!this.fEntities.containsKey(str)) {
            this.fEntities.put(str, new ExternalEntity(str, new XMLEntityDescriptionImpl(str, str2, str3, str4, null), str5, this.fInExternalSubset));
        } else if (this.fWarnDuplicateEntityDef) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[]{str}, (short) 0);
        }
    }

    public void closeReaders() {
        for (int size = this.fReaderStack.size() - 1; size >= 0; size--) {
            try {
                ((Reader) this.fReaderStack.pop()).close();
            } catch (IOException unused) {
            }
        }
    }

    protected Reader createReader(InputStream inputStream, String str, Boolean bool) throws IOException {
        if (str == "UTF-8" || str == null) {
            return createUTF8Reader(inputStream);
        }
        if (str == CharEncoding.UTF_16 && bool != null) {
            return createUTF16Reader(inputStream, bool.booleanValue());
        }
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        if (upperCase.equals("UTF-8")) {
            return createUTF8Reader(inputStream);
        }
        if (upperCase.equals(CharEncoding.UTF_16BE)) {
            return createUTF16Reader(inputStream, true);
        }
        if (upperCase.equals(CharEncoding.UTF_16LE)) {
            return createUTF16Reader(inputStream, false);
        }
        if (upperCase.equals("ISO-10646-UCS-4")) {
            if (bool != null) {
                return bool.booleanValue() ? new UCSReader(inputStream, (short) 8) : new UCSReader(inputStream, (short) 4);
            }
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported", new Object[]{str}, (short) 2);
        }
        if (upperCase.equals("ISO-10646-UCS-2")) {
            if (bool != null) {
                return bool.booleanValue() ? new UCSReader(inputStream, (short) 2) : new UCSReader(inputStream, (short) 1);
            }
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported", new Object[]{str}, (short) 2);
        }
        boolean zIsValidIANAEncoding = XMLChar.isValidIANAEncoding(str);
        boolean zIsValidJavaEncoding = XMLChar.isValidJavaEncoding(str);
        if (!zIsValidIANAEncoding || (this.fAllowJavaEncodings && !zIsValidJavaEncoding)) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid", new Object[]{str}, (short) 2);
            return createLatin1Reader(inputStream);
        }
        String iANA2JavaMapping = EncodingMap.getIANA2JavaMapping(upperCase);
        if (iANA2JavaMapping == null) {
            if (!this.fAllowJavaEncodings) {
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid", new Object[]{str}, (short) 2);
                return createLatin1Reader(inputStream);
            }
        } else {
            if (iANA2JavaMapping.equals("ASCII")) {
                return createASCIIReader(inputStream);
            }
            if (iANA2JavaMapping.equals("ISO8859_1")) {
                return createLatin1Reader(inputStream);
            }
            str = iANA2JavaMapping;
        }
        return new InputStreamReader(inputStream, str);
    }

    void endEntity() throws XNIException {
        XMLEntityHandler xMLEntityHandler = this.fEntityHandler;
        if (xMLEntityHandler != null) {
            xMLEntityHandler.endEntity(this.fCurrentEntity.f472name, null);
        }
        try {
            this.fCurrentEntity.reader.close();
        } catch (IOException unused) {
        }
        if (!this.fReaderStack.isEmpty()) {
            this.fReaderStack.pop();
        }
        this.fCharacterBufferPool.returnBuffer(this.fCurrentEntity.fCharacterBuffer);
        if (this.fCurrentEntity.fByteBuffer != null) {
            if (this.fCurrentEntity.fByteBuffer.length == this.fBufferSize) {
                this.fSmallByteBufferPool.returnBuffer(this.fCurrentEntity.fByteBuffer);
            } else {
                this.fLargeByteBufferPool.returnBuffer(this.fCurrentEntity.fByteBuffer);
            }
        }
        ScannedEntity scannedEntity = this.fEntityStack.size() > 0 ? (ScannedEntity) this.fEntityStack.pop() : null;
        this.fCurrentEntity = scannedEntity;
        this.fEntityScanner.setCurrentEntity(scannedEntity);
    }

    public void endExternalSubset() {
        this.fInExternalSubset = false;
    }

    public ScannedEntity getCurrentEntity() {
        return this.fCurrentEntity;
    }

    public XMLResourceIdentifier getCurrentResourceIdentifier() {
        return this.fResourceIdentifier;
    }

    Hashtable getDeclaredEntities() {
        return this.fEntities;
    }

    protected EncodingInfo getEncodingInfo(byte[] bArr, int i) {
        if (i < 2) {
            return EncodingInfo.UTF_8;
        }
        int i2 = bArr[0] & 255;
        int i3 = bArr[1] & 255;
        if (i2 == 254 && i3 == 255) {
            return EncodingInfo.UTF_16_BIG_ENDIAN_WITH_BOM;
        }
        if (i2 == 255 && i3 == 254) {
            return EncodingInfo.UTF_16_LITTLE_ENDIAN_WITH_BOM;
        }
        if (i < 3) {
            return EncodingInfo.UTF_8;
        }
        int i4 = bArr[2] & 255;
        if (i2 == 239 && i3 == 187 && i4 == 191) {
            return EncodingInfo.UTF_8_WITH_BOM;
        }
        if (i < 4) {
            return EncodingInfo.UTF_8;
        }
        int i5 = bArr[3] & 255;
        if (i2 == 0 && i3 == 0 && i4 == 0 && i5 == 60) {
            return EncodingInfo.UCS_4_BIG_ENDIAN;
        }
        if (i2 == 60 && i3 == 0 && i4 == 0 && i5 == 0) {
            return EncodingInfo.UCS_4_LITTLE_ENDIAN;
        }
        if (i2 == 0 && i3 == 0 && i4 == 60 && i5 == 0) {
            return EncodingInfo.UCS_4_UNUSUAL_BYTE_ORDER;
        }
        if (i2 == 0 && i3 == 60 && i4 == 0 && i5 == 0) {
            return EncodingInfo.UCS_4_UNUSUAL_BYTE_ORDER;
        }
        if (i2 == 0 && i3 == 60 && i4 == 0 && i5 == 63) {
            return EncodingInfo.UTF_16_BIG_ENDIAN;
        }
        if (i2 == 60 && i3 == 0 && i4 == 63 && i5 == 0) {
            return EncodingInfo.UTF_16_LITTLE_ENDIAN;
        }
        return (i2 == 76 && i3 == 111 && i4 == 167 && i5 == 148) ? EncodingInfo.EBCDIC : EncodingInfo.UTF_8;
    }

    public XMLEntityScanner getEntityScanner() {
        if (this.fEntityScanner == null) {
            if (this.fXML10EntityScanner == null) {
                this.fXML10EntityScanner = new XMLEntityScanner();
            }
            this.fXML10EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
            this.fEntityScanner = this.fXML10EntityScanner;
        }
        return this.fEntityScanner;
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

    public int getParamEntityRefCount(String str) {
        Entity entity;
        if (str == null || str.length() <= 0 || str.charAt(0) != '%' || (entity = (Entity) this.fEntities.get(str)) == null || entity.isExternal()) {
            return 0;
        }
        return ((InternalEntity) entity).paramEntityRefs;
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

    final boolean hasPEReferences() {
        return this.fHasPEReferences;
    }

    public boolean isDeclaredEntity(String str) {
        return ((Entity) this.fEntities.get(str)) != null;
    }

    public boolean isEntityDeclInExternalSubset(String str) {
        Entity entity = (Entity) this.fEntities.get(str);
        if (entity == null) {
            return false;
        }
        return entity.isEntityDeclInExternalSubset();
    }

    public boolean isExternalEntity(String str) {
        Entity entity = (Entity) this.fEntities.get(str);
        if (entity == null) {
            return false;
        }
        return entity.isExternal();
    }

    public boolean isStandalone() {
        return this.fStandalone;
    }

    public boolean isUnparsedEntity(String str) {
        Entity entity = (Entity) this.fEntities.get(str);
        if (entity == null) {
            return false;
        }
        return entity.isUnparsed();
    }

    final void notifyHasPEReferences() {
        this.fHasPEReferences = true;
    }

    public void reset() {
        SecurityManager securityManager = this.fSecurityManager;
        this.fEntityExpansionLimit = securityManager != null ? securityManager.getEntityExpansionLimit() : 0;
        this.fStandalone = false;
        this.fHasPEReferences = false;
        this.fEntities.clear();
        this.fEntityStack.removeAllElements();
        this.fEntityExpansionCount = 0;
        this.fCurrentEntity = null;
        XMLEntityScanner xMLEntityScanner = this.fXML10EntityScanner;
        if (xMLEntityScanner != null) {
            xMLEntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
        }
        XMLEntityScanner xMLEntityScanner2 = this.fXML11EntityScanner;
        if (xMLEntityScanner2 != null) {
            xMLEntityScanner2.reset(this.fSymbolTable, this, this.fErrorReporter);
        }
        Hashtable hashtable = this.fDeclaredEntities;
        if (hashtable != null) {
            for (Map.Entry entry : hashtable.entrySet()) {
                this.fEntities.put(entry.getKey(), entry.getValue());
            }
        }
        this.fEntityHandler = null;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XMLConfigurationException {
        boolean feature;
        try {
            feature = xMLComponentManager.getFeature(PARSER_SETTINGS);
        } catch (XMLConfigurationException unused) {
            feature = true;
        }
        if (!feature) {
            reset();
            return;
        }
        try {
            this.fValidation = xMLComponentManager.getFeature(VALIDATION);
        } catch (XMLConfigurationException unused2) {
            this.fValidation = false;
        }
        try {
            this.fExternalGeneralEntities = xMLComponentManager.getFeature(EXTERNAL_GENERAL_ENTITIES);
        } catch (XMLConfigurationException unused3) {
            this.fExternalGeneralEntities = true;
        }
        try {
            this.fExternalParameterEntities = xMLComponentManager.getFeature(EXTERNAL_PARAMETER_ENTITIES);
        } catch (XMLConfigurationException unused4) {
            this.fExternalParameterEntities = true;
        }
        try {
            this.fAllowJavaEncodings = xMLComponentManager.getFeature(ALLOW_JAVA_ENCODINGS);
        } catch (XMLConfigurationException unused5) {
            this.fAllowJavaEncodings = false;
        }
        try {
            this.fWarnDuplicateEntityDef = xMLComponentManager.getFeature(WARN_ON_DUPLICATE_ENTITYDEF);
        } catch (XMLConfigurationException unused6) {
            this.fWarnDuplicateEntityDef = false;
        }
        try {
            this.fStrictURI = xMLComponentManager.getFeature(STANDARD_URI_CONFORMANT);
        } catch (XMLConfigurationException unused7) {
            this.fStrictURI = false;
        }
        this.fSymbolTable = (SymbolTable) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fErrorReporter = (XMLErrorReporter) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        try {
            this.fEntityResolver = (XMLEntityResolver) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
        } catch (XMLConfigurationException unused8) {
            this.fEntityResolver = null;
        }
        try {
            this.fValidationManager = (ValidationManager) xMLComponentManager.getProperty(VALIDATION_MANAGER);
        } catch (XMLConfigurationException unused9) {
            this.fValidationManager = null;
        }
        try {
            this.fSecurityManager = (SecurityManager) xMLComponentManager.getProperty(SECURITY_MANAGER);
        } catch (XMLConfigurationException unused10) {
            this.fSecurityManager = null;
        }
        reset();
    }

    @Override // org.apache.xerces.xni.parser.XMLEntityResolver
    public XMLInputSource resolveEntity(XMLResourceIdentifier xMLResourceIdentifier) throws IOException, XNIException {
        ScannedEntity scannedEntity;
        XMLInputSource xMLInputSourceResolveEntity = null;
        if (xMLResourceIdentifier == null) {
            return null;
        }
        String publicId = xMLResourceIdentifier.getPublicId();
        String literalSystemId = xMLResourceIdentifier.getLiteralSystemId();
        String baseSystemId = xMLResourceIdentifier.getBaseSystemId();
        String expandedSystemId = xMLResourceIdentifier.getExpandedSystemId();
        boolean z = (baseSystemId != null || (scannedEntity = this.fCurrentEntity) == null || scannedEntity.entityLocation == null || (baseSystemId = this.fCurrentEntity.entityLocation.getExpandedSystemId()) == null) ? expandedSystemId == null : true;
        if (this.fEntityResolver != null) {
            if (z) {
                expandedSystemId = expandSystemId(literalSystemId, baseSystemId, false);
            }
            xMLResourceIdentifier.setBaseSystemId(baseSystemId);
            xMLResourceIdentifier.setExpandedSystemId(expandedSystemId);
            xMLInputSourceResolveEntity = this.fEntityResolver.resolveEntity(xMLResourceIdentifier);
        }
        return xMLInputSourceResolveEntity == null ? new XMLInputSource(publicId, literalSystemId, baseSystemId) : xMLInputSourceResolveEntity;
    }

    public void setEntityHandler(XMLEntityHandler xMLEntityHandler) {
        this.fEntityHandler = xMLEntityHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_FEATURE_PREFIX) && str.length() - Constants.XERCES_FEATURE_PREFIX.length() == Constants.ALLOW_JAVA_ENCODINGS_FEATURE.length() && str.endsWith(Constants.ALLOW_JAVA_ENCODINGS_FEATURE)) {
            this.fAllowJavaEncodings = z;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        Integer num;
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            int length = str.length() - Constants.XERCES_PROPERTY_PREFIX.length();
            if (length == Constants.SYMBOL_TABLE_PROPERTY.length() && str.endsWith(Constants.SYMBOL_TABLE_PROPERTY)) {
                this.fSymbolTable = (SymbolTable) obj;
                return;
            }
            if (length == Constants.ERROR_REPORTER_PROPERTY.length() && str.endsWith(Constants.ERROR_REPORTER_PROPERTY)) {
                this.fErrorReporter = (XMLErrorReporter) obj;
                return;
            }
            if (length == Constants.ENTITY_RESOLVER_PROPERTY.length() && str.endsWith(Constants.ENTITY_RESOLVER_PROPERTY)) {
                this.fEntityResolver = (XMLEntityResolver) obj;
                return;
            }
            if (length == Constants.BUFFER_SIZE_PROPERTY.length() && str.endsWith(Constants.BUFFER_SIZE_PROPERTY) && (num = (Integer) obj) != null && num.intValue() > 64) {
                int iIntValue = num.intValue();
                this.fBufferSize = iIntValue;
                this.fEntityScanner.setBufferSize(iIntValue);
                this.fSmallByteBufferPool.setBufferSize(this.fBufferSize);
                this.fLargeByteBufferPool.setBufferSize(this.fBufferSize << 1);
                this.fCharacterBufferPool.setExternalBufferSize(this.fBufferSize);
            }
            if (length == Constants.SECURITY_MANAGER_PROPERTY.length() && str.endsWith(Constants.SECURITY_MANAGER_PROPERTY)) {
                SecurityManager securityManager = (SecurityManager) obj;
                this.fSecurityManager = securityManager;
                this.fEntityExpansionLimit = securityManager != null ? securityManager.getEntityExpansionLimit() : 0;
            }
        }
    }

    public void setScannerVersion(short s) {
        XMLEntityScanner xMLEntityScanner;
        if (s == 1) {
            if (this.fXML10EntityScanner == null) {
                this.fXML10EntityScanner = new XMLEntityScanner();
            }
            this.fXML10EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
            xMLEntityScanner = this.fXML10EntityScanner;
        } else {
            if (this.fXML11EntityScanner == null) {
                this.fXML11EntityScanner = new XML11EntityScanner();
            }
            this.fXML11EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
            xMLEntityScanner = this.fXML11EntityScanner;
        }
        this.fEntityScanner = xMLEntityScanner;
        xMLEntityScanner.setCurrentEntity(this.fCurrentEntity);
    }

    public void setStandalone(boolean z) {
        this.fStandalone = z;
    }

    /* JADX WARN: Code duplicated, block: B:117:0x01ae  */
    /* JADX WARN: Code duplicated, block: B:126:0x01c2  */
    /* JADX WARN: Code duplicated, block: B:158:0x0214 A[PHI: r3
      0x0214: PHI (r3v3 java.lang.String) = (r3v2 java.lang.String), (r3v0 java.lang.String) binds: [B:130:0x01d1, B:37:0x00af] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:96:0x0173  */
    /* JADX WARN: Code duplicated, block: B:97:0x0175  */
    public String setupCurrentEntity(String str, XMLInputSource xMLInputSource, boolean z, boolean z2) throws IOException, XNIException {
        String str2;
        RewindableInputStream rewindableInputStream;
        String str3;
        Reader reader;
        int i;
        int i2;
        Boolean bool;
        Reader readerCreateReader;
        int i3;
        Boolean bool2;
        int i4;
        Boolean bool3;
        boolean followHTTPRedirects;
        String publicId = xMLInputSource.getPublicId();
        String systemId = xMLInputSource.getSystemId();
        String baseSystemId = xMLInputSource.getBaseSystemId();
        String encoding = xMLInputSource.getEncoding();
        int i5 = 0;
        boolean z3 = encoding != null;
        this.fTempByteBuffer = null;
        Reader characterStream = xMLInputSource.getCharacterStream();
        String strExpandSystemId = expandSystemId(systemId, baseSystemId, this.fStrictURI);
        String str4 = baseSystemId == null ? strExpandSystemId : baseSystemId;
        if (characterStream == null) {
            InputStream byteStream = xMLInputSource.getByteStream();
            if (byteStream == null) {
                URLConnection uRLConnectionOpenConnection = new URL(strExpandSystemId).openConnection();
                if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
                    if (xMLInputSource instanceof HTTPInputSource) {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                        HTTPInputSource hTTPInputSource = (HTTPInputSource) xMLInputSource;
                        Iterator hTTPRequestProperties = hTTPInputSource.getHTTPRequestProperties();
                        while (hTTPRequestProperties.hasNext()) {
                            Map.Entry entry = (Map.Entry) hTTPRequestProperties.next();
                            httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
                        }
                        followHTTPRedirects = hTTPInputSource.getFollowHTTPRedirects();
                        if (!followHTTPRedirects) {
                            httpURLConnection.setInstanceFollowRedirects(followHTTPRedirects);
                        }
                    } else {
                        followHTTPRedirects = true;
                    }
                    InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
                    if (followHTTPRedirects) {
                        String string = uRLConnectionOpenConnection.getURL().toString();
                        if (!string.equals(strExpandSystemId)) {
                            systemId = string;
                            strExpandSystemId = systemId;
                        }
                    }
                    byteStream = inputStream;
                } else {
                    byteStream = uRLConnectionOpenConnection.getInputStream();
                }
            }
            RewindableInputStream rewindableInputStream2 = new RewindableInputStream(byteStream);
            if (encoding == null) {
                byte[] bArr = new byte[4];
                while (i5 < 4) {
                    bArr[i5] = (byte) rewindableInputStream2.readAndBuffer();
                    i5++;
                }
                if (i5 == 4) {
                    EncodingInfo encodingInfo = getEncodingInfo(bArr, i5);
                    String str5 = encodingInfo.autoDetectedEncoding;
                    String str6 = encodingInfo.readerEncoding;
                    Boolean bool4 = encodingInfo.isBigEndian;
                    rewindableInputStream2.reset();
                    if (encodingInfo.hasBOM) {
                        if (str6 == "UTF-8") {
                            rewindableInputStream2.skip(3L);
                        } else if (str6 == CharEncoding.UTF_16) {
                            rewindableInputStream2.skip(2L);
                        }
                    }
                    readerCreateReader = createReader(rewindableInputStream2, str6, bool4);
                    encoding = str5;
                } else {
                    readerCreateReader = createReader(rewindableInputStream2, encoding, null);
                }
            } else {
                encoding = encoding.toUpperCase(Locale.ENGLISH);
                if (encoding.equals("UTF-8")) {
                    int[] iArr = new int[3];
                    int i6 = 0;
                    while (i6 < 3) {
                        int andBuffer = rewindableInputStream2.readAndBuffer();
                        iArr[i6] = andBuffer;
                        if (andBuffer == -1) {
                            break;
                        }
                        i6++;
                    }
                    if (i6 != 3 || iArr[0] != 239 || iArr[1] != 187 || iArr[2] != 191) {
                        rewindableInputStream2.reset();
                    }
                    readerCreateReader = createReader(rewindableInputStream2, "UTF-8", null);
                } else if (encoding.equals(CharEncoding.UTF_16)) {
                    int[] iArr2 = new int[4];
                    int i7 = 0;
                    for (int i8 = 4; i7 < i8; i8 = 4) {
                        int andBuffer2 = rewindableInputStream2.readAndBuffer();
                        iArr2[i7] = andBuffer2;
                        if (andBuffer2 == -1) {
                            break;
                        }
                        i7++;
                    }
                    rewindableInputStream2.reset();
                    if (i7 >= 2) {
                        int i9 = iArr2[0];
                        int i10 = iArr2[1];
                        if (i9 == 254 && i10 == 255) {
                            bool2 = Boolean.TRUE;
                        } else if (i9 == 255 && i10 == 254) {
                            bool2 = Boolean.FALSE;
                        } else if (i7 == 4) {
                            int i11 = iArr2[2];
                            int i12 = iArr2[3];
                            if (i9 == 0 && i10 == 60 && i11 == 0) {
                                i4 = 63;
                                bool3 = i12 == 63 ? Boolean.TRUE : null;
                                if (i9 != 60 && i10 == 0 && i11 == i4 && i12 == 0) {
                                    bool2 = Boolean.FALSE;
                                } else {
                                    bool2 = bool3;
                                }
                            } else {
                                i4 = 63;
                            }
                            if (i9 != 60) {
                                bool2 = bool3;
                            } else {
                                bool2 = bool3;
                            }
                        } else {
                            bool2 = null;
                        }
                        rewindableInputStream2.skip(2L);
                    } else {
                        bool2 = null;
                    }
                    readerCreateReader = createReader(rewindableInputStream2, CharEncoding.UTF_16, bool2);
                } else {
                    if (encoding.equals("ISO-10646-UCS-4")) {
                        int[] iArr3 = new int[4];
                        int i13 = 0;
                        while (i13 < 4) {
                            int andBuffer3 = rewindableInputStream2.readAndBuffer();
                            iArr3[i13] = andBuffer3;
                            if (andBuffer3 == -1) {
                                break;
                            }
                            i13++;
                        }
                        rewindableInputStream2.reset();
                        if (i13 == 4) {
                            int i14 = iArr3[0];
                            if (i14 == 0 && iArr3[1] == 0 && iArr3[2] == 0) {
                                i3 = 60;
                                if (iArr3[3] == 60) {
                                    bool = Boolean.TRUE;
                                }
                            } else {
                                i3 = 60;
                            }
                            if (i14 == i3 && iArr3[1] == 0 && iArr3[2] == 0 && iArr3[3] == 0) {
                                bool = Boolean.FALSE;
                            }
                        }
                        bool = null;
                    } else if (encoding.equals("ISO-10646-UCS-2")) {
                        int[] iArr4 = new int[4];
                        int i15 = 0;
                        while (i15 < 4) {
                            int andBuffer4 = rewindableInputStream2.readAndBuffer();
                            iArr4[i15] = andBuffer4;
                            if (andBuffer4 == -1) {
                                break;
                            }
                            i15++;
                        }
                        rewindableInputStream2.reset();
                        if (i15 == 4) {
                            int i16 = iArr4[0];
                            if (i16 == 0) {
                                i = 60;
                                if (iArr4[1] == 60 && iArr4[2] == 0) {
                                    i2 = 63;
                                    if (iArr4[3] == 63) {
                                        bool = Boolean.TRUE;
                                    }
                                }
                                if (i16 != i && iArr4[1] == 0 && iArr4[2] == i2 && iArr4[3] == 0) {
                                    bool = Boolean.FALSE;
                                }
                            } else {
                                i = 60;
                            }
                            i2 = 63;
                            if (i16 != i) {
                            }
                        }
                        bool = null;
                    } else {
                        readerCreateReader = createReader(rewindableInputStream2, encoding, null);
                    }
                    readerCreateReader = createReader(rewindableInputStream2, encoding, bool);
                }
            }
            rewindableInputStream = rewindableInputStream2;
            str2 = systemId;
            reader = readerCreateReader;
            str3 = encoding;
        } else {
            str2 = systemId;
            rewindableInputStream = null;
            str3 = encoding;
            reader = characterStream;
        }
        String str7 = strExpandSystemId;
        this.fReaderStack.push(reader);
        ScannedEntity scannedEntity = this.fCurrentEntity;
        if (scannedEntity != null) {
            this.fEntityStack.push(scannedEntity);
        }
        ScannedEntity scannedEntity2 = new ScannedEntity(str, new XMLResourceIdentifierImpl(publicId, str2, str4, str7), rewindableInputStream, reader, this.fTempByteBuffer, str3, z, false, z2);
        this.fCurrentEntity = scannedEntity2;
        scannedEntity2.setEncodingExternallySpecified(z3);
        this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
        this.fResourceIdentifier.setValues(publicId, str2, str4, str7);
        return str3;
    }

    public void startDTDEntity(XMLInputSource xMLInputSource) throws IOException, XNIException {
        startEntity(DTDEntity, xMLInputSource, false, true);
    }

    public void startDocumentEntity(XMLInputSource xMLInputSource) throws IOException, XNIException {
        startEntity(XMLEntity, xMLInputSource, false, true);
    }

    public void startEntity(String str, XMLInputSource xMLInputSource, boolean z, boolean z2) throws IOException, XNIException {
        String str2 = setupCurrentEntity(str, xMLInputSource, z, z2);
        if (this.fSecurityManager != null) {
            int paramEntityRefCount = this.fEntityExpansionCount + getParamEntityRefCount(str);
            this.fEntityExpansionCount = paramEntityRefCount + 1;
            if (paramEntityRefCount > this.fEntityExpansionLimit) {
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityExpansionLimitExceeded", new Object[]{new Integer(this.fEntityExpansionLimit)}, (short) 2);
                this.fEntityExpansionCount = 0;
            }
        }
        XMLEntityHandler xMLEntityHandler = this.fEntityHandler;
        if (xMLEntityHandler != null) {
            xMLEntityHandler.startEntity(str, this.fResourceIdentifier, str2, null);
        }
    }

    public void startEntity(String str, boolean z) throws IOException, XNIException {
        ValidationManager validationManager;
        Entity entity = (Entity) this.fEntities.get(str);
        if (entity == null) {
            if (this.fEntityHandler != null) {
                this.fResourceIdentifier.clear();
                this.fEntityAugs.removeAllItems();
                this.fEntityAugs.putItem(Constants.ENTITY_SKIPPED, Boolean.TRUE);
                this.fEntityHandler.startEntity(str, this.fResourceIdentifier, null, this.fEntityAugs);
                this.fEntityAugs.removeAllItems();
                this.fEntityAugs.putItem(Constants.ENTITY_SKIPPED, Boolean.TRUE);
                this.fEntityHandler.endEntity(str, this.fEntityAugs);
                return;
            }
            return;
        }
        boolean zIsExternal = entity.isExternal();
        if (zIsExternal && ((validationManager = this.fValidationManager) == null || !validationManager.isCachedDTD())) {
            boolean zIsUnparsed = entity.isUnparsed();
            boolean zStartsWith = str.startsWith("%");
            if (zIsUnparsed || ((!zStartsWith && !this.fExternalGeneralEntities) || (zStartsWith && !this.fExternalParameterEntities))) {
                if (this.fEntityHandler != null) {
                    this.fResourceIdentifier.clear();
                    ExternalEntity externalEntity = (ExternalEntity) entity;
                    String literalSystemId = externalEntity.entityLocation != null ? externalEntity.entityLocation.getLiteralSystemId() : null;
                    String baseSystemId = externalEntity.entityLocation != null ? externalEntity.entityLocation.getBaseSystemId() : null;
                    this.fResourceIdentifier.setValues(externalEntity.entityLocation != null ? externalEntity.entityLocation.getPublicId() : null, literalSystemId, baseSystemId, expandSystemId(literalSystemId, baseSystemId, false));
                    this.fEntityAugs.removeAllItems();
                    this.fEntityAugs.putItem(Constants.ENTITY_SKIPPED, Boolean.TRUE);
                    this.fEntityHandler.startEntity(str, this.fResourceIdentifier, null, this.fEntityAugs);
                    this.fEntityAugs.removeAllItems();
                    this.fEntityAugs.putItem(Constants.ENTITY_SKIPPED, Boolean.TRUE);
                    this.fEntityHandler.endEntity(str, this.fEntityAugs);
                    return;
                }
                return;
            }
        }
        int size = this.fEntityStack.size();
        int i = size;
        while (i >= 0) {
            if ((i == size ? this.fCurrentEntity : (Entity) this.fEntityStack.elementAt(i)).f472name == str) {
                StringBuffer stringBuffer = new StringBuffer(str);
                while (true) {
                    i++;
                    if (i >= size) {
                        break;
                    }
                    Entity entity2 = (Entity) this.fEntityStack.elementAt(i);
                    stringBuffer.append(" -> ");
                    stringBuffer.append(entity2.f472name);
                }
                stringBuffer.append(" -> ");
                stringBuffer.append(this.fCurrentEntity.f472name);
                stringBuffer.append(" -> ");
                stringBuffer.append(str);
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RecursiveReference", new Object[]{str, stringBuffer.toString()}, (short) 2);
                if (this.fEntityHandler != null) {
                    this.fResourceIdentifier.clear();
                    if (zIsExternal) {
                        ExternalEntity externalEntity2 = (ExternalEntity) entity;
                        String literalSystemId2 = externalEntity2.entityLocation != null ? externalEntity2.entityLocation.getLiteralSystemId() : null;
                        String baseSystemId2 = externalEntity2.entityLocation != null ? externalEntity2.entityLocation.getBaseSystemId() : null;
                        this.fResourceIdentifier.setValues(externalEntity2.entityLocation != null ? externalEntity2.entityLocation.getPublicId() : null, literalSystemId2, baseSystemId2, expandSystemId(literalSystemId2, baseSystemId2, false));
                    }
                    this.fEntityAugs.removeAllItems();
                    this.fEntityAugs.putItem(Constants.ENTITY_SKIPPED, Boolean.TRUE);
                    this.fEntityHandler.startEntity(str, this.fResourceIdentifier, null, this.fEntityAugs);
                    this.fEntityAugs.removeAllItems();
                    this.fEntityAugs.putItem(Constants.ENTITY_SKIPPED, Boolean.TRUE);
                    this.fEntityHandler.endEntity(str, this.fEntityAugs);
                    return;
                }
                return;
            }
            i--;
        }
        startEntity(str, zIsExternal ? resolveEntity(((ExternalEntity) entity).entityLocation) : new XMLInputSource((String) null, (String) null, (String) null, new StringReader(((InternalEntity) entity).text), (String) null), z, zIsExternal);
    }

    public void startExternalSubset() {
        this.fInExternalSubset = true;
    }
}
