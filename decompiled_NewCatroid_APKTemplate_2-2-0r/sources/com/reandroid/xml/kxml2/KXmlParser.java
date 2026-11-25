package com.reandroid.xml.kxml2;

import androidx.core.view.InputDeviceCompat;
import com.huawei.hms.framework.common.ContainerUtils;
import io.netty.util.internal.StringUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.lang3.CharEncoding;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes11.dex */
public class KXmlParser implements XmlPullParser, Closeable {
    private static final char[] ANY;
    private static final int ATTLISTDECL = 13;
    private static final char[] COMMENT_DOUBLE_DASH;
    private static final Map<String, String> DEFAULT_ENTITIES;
    private static final char[] DOUBLE_QUOTE;
    private static final int ELEMENTDECL = 11;
    private static final char[] EMPTY;
    private static final char[] END_CDATA;
    private static final char[] END_COMMENT;
    private static final char[] END_PROCESSING_INSTRUCTION;
    private static final int ENTITYDECL = 12;
    private static final String FEATURE_RELAXED = "http://xmlpull.org/v1/doc/features.html#relaxed";
    private static final char[] FIXED;
    private static final String ILLEGAL_TYPE = "Wrong event type";
    private static final char[] IMPLIED;
    private static final char[] NDATA;
    private static final char[] NOTATION;
    private static final int NOTATIONDECL = 14;
    private static final int PARAMETER_ENTITY_REF = 15;
    private static final String PROPERTY_LOCATION = "http://xmlpull.org/v1/doc/properties.html#location";
    private static final String PROPERTY_XMLDECL_STANDALONE = "http://xmlpull.org/v1/doc/properties.html#xmldecl-standalone";
    private static final String PROPERTY_XMLDECL_VERSION = "http://xmlpull.org/v1/doc/properties.html#xmldecl-version";
    private static final char[] PUBLIC;
    private static final char[] REQUIRED;
    private static final char[] SINGLE_QUOTE;
    private static final char[] START_ATTLIST;
    private static final char[] START_CDATA;
    private static final char[] START_COMMENT;
    private static final char[] START_DOCTYPE;
    private static final char[] START_ELEMENT;
    private static final char[] START_ENTITY;
    private static final char[] START_NOTATION;
    private static final char[] START_PROCESSING_INSTRUCTION;
    private static final char[] SYSTEM;
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    private static final int XML_DECLARATION = 998;
    private int attributeCount;
    private StringBuilder bufferCapture;
    private int bufferStartColumn;
    private int bufferStartLine;
    private Map<String, Map<String, String>> defaultAttributes;
    private boolean degenerated;
    private int depth;
    private Map<String, char[]> documentEntities;
    private String encoding;
    private String error;
    private boolean isWhitespace;
    private boolean keepNamespaceAttributes;
    private String location;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f260name;
    private String namespace;
    private ContentSource nextContentSource;
    private boolean parsedTopLevelStartTag;
    private String prefix;
    private boolean processDocDecl;
    private boolean processNsp;
    private String publicId;
    private Reader reader;
    private boolean relaxed;
    private String rootElementName;
    private Boolean standalone;
    private String systemId;
    private String text;
    private int type;
    private boolean unresolved;
    private String version;
    private String[] elementStack = new String[16];
    private String[] nspStack = new String[8];
    private int[] nspCounts = new int[4];
    private char[] buffer = new char[8192];
    private int position = 0;
    private int limit = 0;
    private String[] attributes = new String[16];

    enum ValueContext {
        ATTRIBUTE,
        TEXT,
        ENTITY_DECLARATION
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isAttributeDefault(int i) {
        return false;
    }

    static {
        HashMap map = new HashMap();
        DEFAULT_ENTITIES = map;
        map.put("lt", "<");
        map.put("gt", ">");
        map.put("amp", ContainerUtils.FIELD_DELIMITER);
        map.put("apos", FormatHelper.QUOTE);
        map.put("quot", "\"");
        START_COMMENT = new char[]{'<', '!', '-', '-'};
        END_COMMENT = new char[]{'-', '-', '>'};
        COMMENT_DOUBLE_DASH = new char[]{'-', '-'};
        START_CDATA = new char[]{'<', '!', '[', Util.C_CHAR, Util.C_DOUBLE, 'A', Util.C_TYPE_VARIABLE, 'A', '['};
        END_CDATA = new char[]{']', ']', '>'};
        START_PROCESSING_INSTRUCTION = new char[]{'<', MangleConstant.Q_MARK};
        END_PROCESSING_INSTRUCTION = new char[]{MangleConstant.Q_MARK, '>'};
        START_DOCTYPE = new char[]{'<', '!', Util.C_DOUBLE, 'O', Util.C_CHAR, Util.C_TYPE_VARIABLE, 'Y', 'P', 'E'};
        SYSTEM = new char[]{Util.C_SHORT, 'Y', Util.C_SHORT, Util.C_TYPE_VARIABLE, 'E', 'M'};
        PUBLIC = new char[]{'P', Matrix.MATRIX_TYPE_RANDOM_UT, Util.C_BYTE, 'L', 'I', Util.C_CHAR};
        START_ELEMENT = new char[]{'<', '!', 'E', 'L', 'E', 'M', 'E', 'N', Util.C_TYPE_VARIABLE};
        START_ATTLIST = new char[]{'<', '!', 'A', Util.C_TYPE_VARIABLE, Util.C_TYPE_VARIABLE, 'L', 'I', Util.C_SHORT, Util.C_TYPE_VARIABLE};
        START_ENTITY = new char[]{'<', '!', 'E', 'N', Util.C_TYPE_VARIABLE, 'I', Util.C_TYPE_VARIABLE, 'Y'};
        START_NOTATION = new char[]{'<', '!', 'N', 'O', Util.C_TYPE_VARIABLE, 'A', Util.C_TYPE_VARIABLE, 'I', 'O', 'N'};
        EMPTY = new char[]{'E', 'M', 'P', Util.C_TYPE_VARIABLE, 'Y'};
        ANY = new char[]{'A', 'N', 'Y'};
        NDATA = new char[]{'N', Util.C_DOUBLE, 'A', Util.C_TYPE_VARIABLE, 'A'};
        NOTATION = new char[]{'N', 'O', Util.C_TYPE_VARIABLE, 'A', Util.C_TYPE_VARIABLE, 'I', 'O', 'N'};
        REQUIRED = new char[]{Matrix.MATRIX_TYPE_RANDOM_REGULAR, 'E', Util.C_UNRESOLVED, Matrix.MATRIX_TYPE_RANDOM_UT, 'I', Matrix.MATRIX_TYPE_RANDOM_REGULAR, 'E', Util.C_DOUBLE};
        IMPLIED = new char[]{'I', 'M', 'P', 'L', 'I', 'E', Util.C_DOUBLE};
        FIXED = new char[]{Util.C_FLOAT, 'I', 'X', 'E', Util.C_DOUBLE};
        SINGLE_QUOTE = new char[]{'\''};
        DOUBLE_QUOTE = new char[]{'\"'};
    }

    public void keepNamespaceAttributes() {
        this.keepNamespaceAttributes = true;
    }

    private boolean adjustNsp() throws XmlPullParserException {
        int i;
        String strSubstring;
        int i2 = 0;
        boolean z = false;
        while (true) {
            i = this.attributeCount;
            if (i2 >= (i << 2)) {
                break;
            }
            String str = this.attributes[i2 + 2];
            int iIndexOf = str.indexOf(58);
            if (iIndexOf != -1) {
                String strSubstring2 = str.substring(0, iIndexOf);
                strSubstring = str.substring(iIndexOf + 1);
                str = strSubstring2;
            } else {
                if (str.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
                    strSubstring = null;
                }
                i2 += 4;
            }
            if (str.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
                int[] iArr = this.nspCounts;
                int i3 = this.depth;
                int i4 = iArr[i3];
                iArr[i3] = i4 + 1;
                int i5 = i4 << 1;
                String[] strArrEnsureCapacity = ensureCapacity(this.nspStack, i5 + 2);
                this.nspStack = strArrEnsureCapacity;
                strArrEnsureCapacity[i5] = strSubstring;
                String[] strArr = this.attributes;
                int i6 = i2 + 3;
                strArrEnsureCapacity[i5 + 1] = strArr[i6];
                if (strSubstring != null && strArr[i6].isEmpty()) {
                    checkRelaxed("illegal empty namespace");
                }
                if (this.keepNamespaceAttributes) {
                    this.attributes[i2] = XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
                    z = true;
                } else {
                    String[] strArr2 = this.attributes;
                    int i7 = this.attributeCount - 1;
                    this.attributeCount = i7;
                    System.arraycopy(strArr2, i2 + 4, strArr2, i2, (i7 << 2) - i2);
                    i2 -= 4;
                }
            } else {
                z = true;
            }
            i2 += 4;
        }
        if (z) {
            for (int i8 = (i << 2) - 4; i8 >= 0; i8 -= 4) {
                int i9 = i8 + 2;
                String str2 = this.attributes[i9];
                int iIndexOf2 = str2.indexOf(58);
                if (iIndexOf2 == 0 && !this.relaxed) {
                    throw new RuntimeException("illegal attribute name: " + str2 + " at " + this);
                }
                if (iIndexOf2 != -1) {
                    String strSubstring3 = str2.substring(0, iIndexOf2);
                    String strSubstring4 = str2.substring(iIndexOf2 + 1);
                    String namespace = getNamespace(strSubstring3);
                    if (namespace == null && !this.relaxed) {
                        throw new RuntimeException("Undefined Prefix: " + strSubstring3 + " in " + this);
                    }
                    String[] strArr3 = this.attributes;
                    strArr3[i8] = namespace;
                    strArr3[i8 + 1] = strSubstring3;
                    strArr3[i9] = strSubstring4;
                }
            }
        }
        int iIndexOf3 = this.f260name.indexOf(58);
        if (iIndexOf3 == 0) {
            checkRelaxed("illegal tag name: " + this.f260name);
        }
        if (iIndexOf3 != -1) {
            this.prefix = this.f260name.substring(0, iIndexOf3);
            this.f260name = this.f260name.substring(iIndexOf3 + 1);
        }
        String namespace2 = getNamespace(this.prefix);
        this.namespace = namespace2;
        if (namespace2 == null) {
            if (this.prefix != null) {
                checkRelaxed("undefined prefix: " + this.prefix);
            }
            this.namespace = "";
        }
        return z;
    }

    private String[] ensureCapacity(String[] strArr, int i) {
        if (strArr.length >= i) {
            return strArr;
        }
        String[] strArr2 = new String[i + 16];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        return strArr2;
    }

    private void checkRelaxed(String str) throws XmlPullParserException {
        if (!this.relaxed) {
            throw new XmlPullParserException(str, this, null);
        }
        if (this.error == null) {
            this.error = "Error: " + str;
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int next() throws XmlPullParserException, IOException {
        return next(false);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextToken() throws XmlPullParserException, IOException {
        return next(true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:39:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:46:0x00be  */
    /* JADX WARN: Code duplicated, block: B:57:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:59:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:62:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:84:0x00d1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:85:0x00ed A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    private int next(boolean z) throws XmlPullParserException, IOException {
        int iPeekType;
        String str;
        int i;
        if (this.reader == null) {
            throw new XmlPullParserException("setInput() must be called first.", this, null);
        }
        if (this.type == 3) {
            this.depth--;
        }
        if (this.degenerated) {
            this.degenerated = false;
            this.type = 3;
            return 3;
        }
        String str2 = this.error;
        if (str2 != null) {
            if (z) {
                this.text = str2;
                this.type = 9;
                this.error = null;
                return 9;
            }
            this.error = null;
        }
        int iPeekType2 = peekType(false);
        this.type = iPeekType2;
        if (iPeekType2 == XML_DECLARATION) {
            readXmlDeclaration();
            this.type = peekType(false);
        }
        this.text = null;
        this.isWhitespace = true;
        this.prefix = null;
        this.f260name = null;
        this.namespace = null;
        this.attributeCount = -1;
        boolean z2 = !z;
        while (true) {
            int i2 = this.type;
            switch (i2) {
                case 1:
                    return i2;
                case 2:
                    parseStartTag(false, z2);
                    return this.type;
                case 3:
                    readEndTag();
                    return this.type;
                case 4:
                    this.text = readValue('<', !z, z2, ValueContext.TEXT);
                    if (this.depth == 0 && this.isWhitespace) {
                        this.type = 7;
                    }
                    if (this.depth != 0 && ((i = this.type) == 6 || i == 4 || i == 5)) {
                        throw new XmlPullParserException("Unexpected token", this, null);
                    }
                    if (z) {
                        return this.type;
                    }
                    if (this.type == 7) {
                        this.text = null;
                    }
                    iPeekType = peekType(false);
                    str = this.text;
                    if (str == null && !str.isEmpty() && iPeekType < 4) {
                        this.type = 4;
                        return 4;
                    }
                    this.type = iPeekType;
                    break;
                    break;
                case 5:
                    read(START_CDATA);
                    this.text = readUntil(END_CDATA, true);
                    if (this.depth != 0) {
                    }
                    if (z) {
                        return this.type;
                    }
                    if (this.type == 7) {
                        this.text = null;
                    }
                    iPeekType = peekType(false);
                    str = this.text;
                    if (str == null) {
                    }
                    this.type = iPeekType;
                    break;
                    break;
                case 6:
                    if (z) {
                        StringBuilder sb = new StringBuilder();
                        readEntity(sb, true, z2, ValueContext.TEXT);
                        this.text = sb.toString();
                    } else {
                        this.text = readValue('<', !z, z2, ValueContext.TEXT);
                        if (this.depth == 0) {
                            this.type = 7;
                        }
                    }
                    if (this.depth != 0) {
                    }
                    if (z) {
                        return this.type;
                    }
                    if (this.type == 7) {
                        this.text = null;
                    }
                    iPeekType = peekType(false);
                    str = this.text;
                    if (str == null) {
                    }
                    this.type = iPeekType;
                    break;
                    break;
                case 7:
                default:
                    throw new XmlPullParserException("Unexpected token", this, null);
                case 8:
                    read(START_PROCESSING_INSTRUCTION);
                    String until = readUntil(END_PROCESSING_INSTRUCTION, z);
                    if (z) {
                        this.text = until;
                    }
                    if (this.depth != 0) {
                    }
                    if (z) {
                        return this.type;
                    }
                    if (this.type == 7) {
                        this.text = null;
                    }
                    iPeekType = peekType(false);
                    str = this.text;
                    if (str == null) {
                    }
                    this.type = iPeekType;
                    break;
                    break;
                case 9:
                    String comment = readComment(z);
                    if (z) {
                        this.text = comment;
                    }
                    if (this.depth != 0) {
                    }
                    if (z) {
                        return this.type;
                    }
                    if (this.type == 7) {
                        this.text = null;
                    }
                    iPeekType = peekType(false);
                    str = this.text;
                    if (str == null) {
                    }
                    this.type = iPeekType;
                    break;
                    break;
                case 10:
                    readDoctype(z);
                    if (this.parsedTopLevelStartTag) {
                        throw new XmlPullParserException("Unexpected token", this, null);
                    }
                    if (this.depth != 0) {
                    }
                    if (z) {
                        return this.type;
                    }
                    if (this.type == 7) {
                        this.text = null;
                    }
                    iPeekType = peekType(false);
                    str = this.text;
                    if (str == null) {
                    }
                    this.type = iPeekType;
                    break;
                    break;
            }
        }
    }

    private String readUntil(char[] cArr, boolean z) throws XmlPullParserException, IOException {
        StringBuilder sb;
        int i;
        int i2 = this.position;
        if (!z || this.text == null) {
            sb = null;
        } else {
            sb = new StringBuilder();
            sb.append(this.text);
        }
        while (true) {
            int i3 = this.position;
            if (cArr.length + i3 > this.limit) {
                if (i2 < i3 && z) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(this.buffer, i2, this.position - i2);
                }
                if (!fillBuffer(cArr.length)) {
                    checkRelaxed(UNEXPECTED_EOF);
                    this.type = 9;
                    return null;
                }
                i2 = this.position;
            }
            int i4 = 0;
            while (true) {
                if (i4 < cArr.length) {
                    char[] cArr2 = this.buffer;
                    i = this.position;
                    if (cArr2[i + i4] != cArr[i4]) {
                        break;
                    }
                    i4++;
                } else {
                    int i5 = this.position;
                    this.position = cArr.length + i5;
                    if (!z) {
                        return null;
                    }
                    if (sb == null) {
                        return new String(this.buffer, i2, i5 - i2);
                    }
                    sb.append(this.buffer, i2, i5 - i2);
                    return sb.toString();
                }
            }
            this.position = i + 1;
        }
    }

    private void readXmlDeclaration() throws XmlPullParserException, IOException {
        if (this.bufferStartLine != 0 || this.bufferStartColumn != 0 || this.position != 0) {
            checkRelaxed("processing instructions must not start with xml");
        }
        read(START_PROCESSING_INSTRUCTION);
        parseStartTag(true, true);
        int i = 2;
        if (this.attributeCount < 1 || !"version".equals(this.attributes[2])) {
            checkRelaxed("version expected");
        }
        String[] strArr = this.attributes;
        this.version = strArr[3];
        if (1 >= this.attributeCount || !"encoding".equals(strArr[6])) {
            i = 1;
        } else {
            this.encoding = this.attributes[7];
        }
        if (i < this.attributeCount) {
            int i2 = i * 4;
            if (OutputKeys.STANDALONE.equals(this.attributes[i2 + 2])) {
                String str = this.attributes[i2 + 3];
                if ("yes".equals(str)) {
                    this.standalone = Boolean.TRUE;
                } else if ("no".equals(str)) {
                    this.standalone = Boolean.FALSE;
                } else {
                    checkRelaxed("illegal standalone value: " + str);
                }
                i++;
            }
        }
        if (i != this.attributeCount) {
            checkRelaxed("unexpected attributes in XML declaration");
        }
        this.isWhitespace = true;
        this.text = null;
    }

    private String readComment(boolean z) throws XmlPullParserException, IOException {
        read(START_COMMENT);
        if (this.relaxed) {
            return readUntil(END_COMMENT, z);
        }
        String until = readUntil(COMMENT_DOUBLE_DASH, z);
        if (peekCharacter() != 62) {
            throw new XmlPullParserException("Comments may not contain --", this, null);
        }
        this.position++;
        return until;
    }

    private void readDoctype(boolean z) throws XmlPullParserException, IOException {
        int i;
        read(START_DOCTYPE);
        if (z) {
            this.bufferCapture = new StringBuilder();
            i = this.position;
        } else {
            i = -1;
        }
        try {
            skip();
            this.rootElementName = readName();
            readExternalId(true, true);
            skip();
            if (peekCharacter() == 91) {
                readInternalSubset();
            }
            skip();
            if (z) {
                this.bufferCapture.append(this.buffer, 0, this.position);
                this.bufferCapture.delete(0, i);
                this.text = this.bufferCapture.toString();
                this.bufferCapture = null;
            }
            read('>');
            skip();
        } catch (Throwable th) {
            if (z) {
                this.bufferCapture.append(this.buffer, 0, this.position);
                this.bufferCapture.delete(0, i);
                this.text = this.bufferCapture.toString();
                this.bufferCapture = null;
            }
            throw th;
        }
    }

    private boolean readExternalId(boolean z, boolean z2) throws XmlPullParserException, IOException {
        int iPeekCharacter;
        skip();
        int iPeekCharacter2 = peekCharacter();
        if (iPeekCharacter2 == 83) {
            read(SYSTEM);
        } else {
            if (iPeekCharacter2 != 80) {
                return false;
            }
            read(PUBLIC);
            skip();
            if (z2) {
                this.publicId = readQuotedId(true);
            } else {
                readQuotedId(false);
            }
        }
        skip();
        if (!z && (iPeekCharacter = peekCharacter()) != 34 && iPeekCharacter != 39) {
            return true;
        }
        if (z2) {
            this.systemId = readQuotedId(true);
        } else {
            readQuotedId(false);
        }
        return true;
    }

    private String readQuotedId(boolean z) throws XmlPullParserException, IOException {
        char[] cArr;
        int iPeekCharacter = peekCharacter();
        if (iPeekCharacter == 34) {
            cArr = DOUBLE_QUOTE;
        } else if (iPeekCharacter == 39) {
            cArr = SINGLE_QUOTE;
        } else {
            throw new XmlPullParserException("Expected a quoted string", this, null);
        }
        this.position++;
        return readUntil(cArr, z);
    }

    private void readInternalSubset() throws XmlPullParserException, IOException {
        read('[');
        while (true) {
            skip();
            if (peekCharacter() == 93) {
                this.position++;
                return;
            }
            switch (peekType(true)) {
                case 8:
                    read(START_PROCESSING_INSTRUCTION);
                    readUntil(END_PROCESSING_INSTRUCTION, false);
                    break;
                case 9:
                    readComment(false);
                    break;
                case 10:
                default:
                    throw new XmlPullParserException("Unexpected token", this, null);
                case 11:
                    readElementDeclaration();
                    break;
                case 12:
                    readEntityDeclaration();
                    break;
                case 13:
                    readAttributeListDeclaration();
                    break;
                case 14:
                    readNotationDeclaration();
                    break;
                case 15:
                    throw new XmlPullParserException("Parameter entity references are not supported", this, null);
            }
        }
    }

    private void readElementDeclaration() throws XmlPullParserException, IOException {
        read(START_ELEMENT);
        skip();
        readName();
        readContentSpec();
        skip();
        read('>');
    }

    private void readContentSpec() throws XmlPullParserException, IOException {
        skip();
        int iPeekCharacter = peekCharacter();
        int i = 0;
        if (iPeekCharacter != 40) {
            char[] cArr = EMPTY;
            if (iPeekCharacter == cArr[0]) {
                read(cArr);
                return;
            }
            char[] cArr2 = ANY;
            if (iPeekCharacter == cArr2[0]) {
                read(cArr2);
                return;
            }
            throw new XmlPullParserException("Expected element content spec", this, null);
        }
        do {
            if (iPeekCharacter == 40) {
                i++;
            } else if (iPeekCharacter == 41) {
                i--;
            } else if (iPeekCharacter == -1) {
                throw new XmlPullParserException("Unterminated element content spec", this, null);
            }
            this.position++;
            iPeekCharacter = peekCharacter();
        } while (i > 0);
        if (iPeekCharacter == 42 || iPeekCharacter == 63 || iPeekCharacter == 43) {
            this.position++;
        }
    }

    private void readAttributeListDeclaration() throws XmlPullParserException, IOException {
        read(START_ATTLIST);
        skip();
        String name2 = readName();
        while (true) {
            skip();
            if (peekCharacter() == 62) {
                this.position++;
                return;
            }
            String name3 = readName();
            skip();
            if (this.position + 1 >= this.limit && !fillBuffer(2)) {
                throw new XmlPullParserException("Malformed attribute list", this, null);
            }
            char[] cArr = this.buffer;
            int i = this.position;
            char c = cArr[i];
            char[] cArr2 = NOTATION;
            if (c == cArr2[0] && cArr[i + 1] == cArr2[1]) {
                read(cArr2);
                skip();
            }
            if (peekCharacter() == 40) {
                this.position++;
                while (true) {
                    skip();
                    readName();
                    skip();
                    int iPeekCharacter = peekCharacter();
                    if (iPeekCharacter == 41) {
                        this.position++;
                        break;
                    } else if (iPeekCharacter == 124) {
                        this.position++;
                    } else {
                        throw new XmlPullParserException("Malformed attribute type", this, null);
                    }
                }
            } else {
                readName();
            }
            skip();
            int iPeekCharacter2 = peekCharacter();
            if (iPeekCharacter2 == 35) {
                this.position++;
                int iPeekCharacter3 = peekCharacter();
                if (iPeekCharacter3 == 82) {
                    read(REQUIRED);
                } else if (iPeekCharacter3 == 73) {
                    read(IMPLIED);
                } else if (iPeekCharacter3 == 70) {
                    read(FIXED);
                } else {
                    throw new XmlPullParserException("Malformed attribute type", this, null);
                }
                skip();
                iPeekCharacter2 = peekCharacter();
            }
            if (iPeekCharacter2 == 34 || iPeekCharacter2 == 39) {
                this.position++;
                String value = readValue((char) iPeekCharacter2, true, true, ValueContext.ATTRIBUTE);
                if (peekCharacter() == iPeekCharacter2) {
                    this.position++;
                }
                defineAttributeDefault(name2, name3, value);
            }
        }
    }

    private void defineAttributeDefault(String str, String str2, String str3) {
        if (this.defaultAttributes == null) {
            this.defaultAttributes = new HashMap();
        }
        Map<String, String> map = this.defaultAttributes.get(str);
        if (map == null) {
            map = new HashMap<>();
            this.defaultAttributes.put(str, map);
        }
        map.put(str2, str3);
    }

    private void readEntityDeclaration() throws XmlPullParserException, IOException {
        boolean z;
        String value;
        read(START_ENTITY);
        skip();
        if (peekCharacter() == 37) {
            this.position++;
            skip();
            z = false;
        } else {
            z = true;
        }
        String name2 = readName();
        skip();
        int iPeekCharacter = peekCharacter();
        if (iPeekCharacter == 34 || iPeekCharacter == 39) {
            this.position++;
            value = readValue((char) iPeekCharacter, true, false, ValueContext.ENTITY_DECLARATION);
            if (peekCharacter() == iPeekCharacter) {
                this.position++;
            }
        } else if (readExternalId(true, false)) {
            skip();
            int iPeekCharacter2 = peekCharacter();
            char[] cArr = NDATA;
            if (iPeekCharacter2 == cArr[0]) {
                read(cArr);
                skip();
                readName();
            }
            value = "";
        } else {
            throw new XmlPullParserException("Expected entity value or external ID", this, null);
        }
        if (z && this.processDocDecl) {
            if (this.documentEntities == null) {
                this.documentEntities = new HashMap();
            }
            this.documentEntities.put(name2, value.toCharArray());
        }
        skip();
        read('>');
    }

    private void readNotationDeclaration() throws XmlPullParserException, IOException {
        read(START_NOTATION);
        skip();
        readName();
        if (!readExternalId(false, false)) {
            throw new XmlPullParserException("Expected external ID or public ID for notation", this, null);
        }
        skip();
        read('>');
    }

    private void readEndTag() throws XmlPullParserException, IOException {
        read('<');
        read('/');
        this.f260name = readName();
        skip();
        read('>');
        int i = this.depth;
        int i2 = (i - 1) * 4;
        if (i == 0) {
            checkRelaxed("read end tag " + this.f260name + " with no tags open");
            this.type = 9;
            return;
        }
        int i3 = i2 + 3;
        if (this.f260name.equals(this.elementStack[i3])) {
            String[] strArr = this.elementStack;
            this.namespace = strArr[i2];
            this.prefix = strArr[i2 + 1];
            this.f260name = strArr[i2 + 2];
            return;
        }
        if (!this.relaxed) {
            throw new XmlPullParserException("expected: /" + this.elementStack[i3] + " read: " + this.f260name, this, null);
        }
    }

    private int peekType(boolean z) throws XmlPullParserException, IOException {
        if (this.position >= this.limit && !fillBuffer(1)) {
            return 1;
        }
        char[] cArr = this.buffer;
        int i = this.position;
        char c = cArr[i];
        if (c == '%') {
            return z ? 15 : 4;
        }
        if (c == '&') {
            return 6;
        }
        if (c != '<') {
            return 4;
        }
        if (i + 3 >= this.limit && !fillBuffer(4)) {
            throw new XmlPullParserException("Dangling <", this, null);
        }
        char[] cArr2 = this.buffer;
        int i2 = this.position;
        char c2 = cArr2[i2 + 1];
        if (c2 != '!') {
            if (c2 == '/') {
                return 3;
            }
            if (c2 != '?') {
                return 2;
            }
            if (i2 + 5 < this.limit || fillBuffer(6)) {
                char[] cArr3 = this.buffer;
                int i3 = this.position;
                if ((cArr3[i3 + 2] == 'x' || cArr3[i3 + 2] == 'X') && ((cArr3[i3 + 3] == 'm' || cArr3[i3 + 3] == 'M') && ((cArr3[i3 + 4] == 'l' || cArr3[i3 + 4] == 'L') && cArr3[i3 + 5] == ' '))) {
                    return XML_DECLARATION;
                }
            }
            return 8;
        }
        char c3 = cArr2[i2 + 2];
        if (c3 == '-') {
            return 9;
        }
        if (c3 == 'A') {
            return 13;
        }
        if (c3 == 'N') {
            return 14;
        }
        if (c3 == '[') {
            return 5;
        }
        if (c3 == 'D') {
            return 10;
        }
        if (c3 == 'E') {
            char c4 = cArr2[i2 + 3];
            if (c4 == 'L') {
                return 11;
            }
            if (c4 == 'N') {
                return 12;
            }
        }
        throw new XmlPullParserException("Unexpected <!", this, null);
    }

    private void parseStartTag(boolean z, boolean z2) throws XmlPullParserException, IOException {
        Map<String, String> map;
        if (!z) {
            read('<');
        }
        this.f260name = readName();
        this.attributeCount = 0;
        while (true) {
            skip();
            if (this.position >= this.limit && !fillBuffer(1)) {
                checkRelaxed(UNEXPECTED_EOF);
                return;
            }
            char[] cArr = this.buffer;
            int i = this.position;
            char c = cArr[i];
            if (!z) {
                if (c == '/') {
                    this.degenerated = true;
                    this.position = i + 1;
                    skip();
                    read('>');
                    break;
                }
                if (c == '>') {
                    this.position = i + 1;
                    break;
                }
            } else if (c == '?') {
                this.position = i + 1;
                read('>');
                return;
            }
            String name2 = readName();
            int i2 = this.attributeCount;
            this.attributeCount = i2 + 1;
            int i3 = i2 * 4;
            String[] strArrEnsureCapacity = ensureCapacity(this.attributes, i3 + 4);
            this.attributes = strArrEnsureCapacity;
            strArrEnsureCapacity[i3] = "";
            strArrEnsureCapacity[i3 + 1] = null;
            strArrEnsureCapacity[i3 + 2] = name2;
            skip();
            if (this.position >= this.limit && !fillBuffer(1)) {
                checkRelaxed(UNEXPECTED_EOF);
                return;
            }
            char[] cArr2 = this.buffer;
            int i4 = this.position;
            if (cArr2[i4] == '=') {
                this.position = i4 + 1;
                skip();
                if (this.position >= this.limit && !fillBuffer(1)) {
                    checkRelaxed(UNEXPECTED_EOF);
                    return;
                }
                char[] cArr3 = this.buffer;
                int i5 = this.position;
                char c2 = cArr3[i5];
                if (c2 == '\'' || c2 == '\"') {
                    this.position = i5 + 1;
                } else {
                    if (!this.relaxed) {
                        throw new XmlPullParserException("attr value delimiter missing!", this, null);
                    }
                    c2 = ' ';
                }
                this.attributes[i3 + 3] = readValue(c2, true, z2, ValueContext.ATTRIBUTE);
                if (c2 != ' ' && peekCharacter() == c2) {
                    this.position++;
                }
            } else if (this.relaxed) {
                this.attributes[i3 + 3] = name2;
            } else {
                checkRelaxed("Attr.value missing f. " + name2);
                this.attributes[i3 + 3] = name2;
            }
        }
        int i6 = this.depth;
        int i7 = i6 + 1;
        this.depth = i7;
        int i8 = i6 * 4;
        if (i7 == 1) {
            this.parsedTopLevelStartTag = true;
        }
        String[] strArrEnsureCapacity2 = ensureCapacity(this.elementStack, i8 + 4);
        this.elementStack = strArrEnsureCapacity2;
        strArrEnsureCapacity2[i8 + 3] = this.f260name;
        int i9 = this.depth;
        int[] iArr = this.nspCounts;
        if (i9 >= iArr.length) {
            int[] iArr2 = new int[i9 + 4];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.nspCounts = iArr2;
        }
        int[] iArr3 = this.nspCounts;
        int i10 = this.depth;
        iArr3[i10] = iArr3[i10 - 1];
        if (this.processNsp) {
            adjustNsp();
        } else {
            this.namespace = "";
        }
        Map<String, Map<String, String>> map2 = this.defaultAttributes;
        if (map2 != null && (map = map2.get(this.f260name)) != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (getAttributeValue(null, entry.getKey()) == null) {
                    int i11 = this.attributeCount;
                    this.attributeCount = i11 + 1;
                    int i12 = i11 * 4;
                    String[] strArrEnsureCapacity3 = ensureCapacity(this.attributes, i12 + 4);
                    this.attributes = strArrEnsureCapacity3;
                    strArrEnsureCapacity3[i12] = "";
                    strArrEnsureCapacity3[i12 + 1] = null;
                    strArrEnsureCapacity3[i12 + 2] = entry.getKey();
                    this.attributes[i12 + 3] = entry.getValue();
                }
            }
        }
        String[] strArr = this.elementStack;
        strArr[i8] = this.namespace;
        strArr[i8 + 1] = this.prefix;
        strArr[i8 + 2] = this.f260name;
    }

    private void readEntity(StringBuilder sb, boolean z, boolean z2, ValueContext valueContext) throws XmlPullParserException, IOException {
        int i;
        char[] cArr;
        int length = sb.length();
        char[] cArr2 = this.buffer;
        int i2 = this.position;
        this.position = i2 + 1;
        if (cArr2[i2] != '&') {
            throw new AssertionError();
        }
        sb.append(Typography.amp);
        while (true) {
            int iPeekCharacter = peekCharacter();
            if (iPeekCharacter == 59) {
                sb.append(';');
                this.position++;
                String strSubstring = sb.substring(length + 1, sb.length() - 1);
                if (z) {
                    this.f260name = strSubstring;
                }
                if (strSubstring.startsWith("#")) {
                    try {
                        if (strSubstring.startsWith("#x")) {
                            i = Integer.parseInt(strSubstring.substring(2), 16);
                        } else {
                            i = Integer.parseInt(strSubstring.substring(1));
                        }
                        sb.delete(length, sb.length());
                        sb.appendCodePoint(i);
                        this.unresolved = false;
                        return;
                    } catch (NumberFormatException unused) {
                        throw new XmlPullParserException("Invalid character reference: &" + strSubstring);
                    } catch (IllegalArgumentException unused2) {
                        throw new XmlPullParserException("Invalid character reference: &" + strSubstring);
                    }
                }
                if (valueContext == ValueContext.ENTITY_DECLARATION) {
                    return;
                }
                String str = DEFAULT_ENTITIES.get(strSubstring);
                if (str != null) {
                    sb.delete(length, sb.length());
                    this.unresolved = false;
                    sb.append(str);
                    return;
                }
                Map<String, char[]> map = this.documentEntities;
                if (map != null && (cArr = map.get(strSubstring)) != null) {
                    sb.delete(length, sb.length());
                    this.unresolved = false;
                    if (this.processDocDecl) {
                        pushContentSource(cArr);
                        return;
                    } else {
                        sb.append(cArr);
                        return;
                    }
                }
                if (this.systemId != null) {
                    sb.delete(length, sb.length());
                    return;
                }
                this.unresolved = true;
                if (z2) {
                    checkRelaxed("unresolved: &" + strSubstring + ";");
                    return;
                }
                return;
            }
            if (iPeekCharacter >= 128 || ((iPeekCharacter >= 48 && iPeekCharacter <= 57) || ((iPeekCharacter >= 97 && iPeekCharacter <= 122) || ((iPeekCharacter >= 65 && iPeekCharacter <= 90) || iPeekCharacter == 95 || iPeekCharacter == 45 || iPeekCharacter == 35)))) {
                this.position++;
                sb.append((char) iPeekCharacter);
            } else {
                if (!this.relaxed) {
                    throw new XmlPullParserException("unterminated entity ref", this, null);
                }
                return;
            }
        }
    }

    private String readValue(char c, boolean z, boolean z2, ValueContext valueContext) throws XmlPullParserException, IOException {
        StringBuilder sb;
        int i = this.position;
        if (valueContext != ValueContext.TEXT || this.text == null) {
            sb = null;
        } else {
            sb = new StringBuilder();
            sb.append(this.text);
        }
        while (true) {
            int i2 = this.position;
            if (i2 >= this.limit) {
                if (i < i2) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(this.buffer, i, this.position - i);
                }
                if (!fillBuffer(1)) {
                    return sb != null ? sb.toString() : "";
                }
                i = this.position;
            }
            char[] cArr = this.buffer;
            int i3 = this.position;
            char c2 = cArr[i3];
            if (c2 == c || ((c == ' ' && (c2 <= ' ' || c2 == '>')) || (c2 == '&' && !z))) {
                if (sb == null) {
                    return new String(this.buffer, i, this.position - i);
                }
                sb.append(cArr, i, i3 - i);
                return sb.toString();
            }
            if (c2 != '\r' && ((c2 != '\n' || valueContext != ValueContext.ATTRIBUTE) && c2 != '&' && c2 != '<' && ((c2 != ']' || valueContext != ValueContext.TEXT) && (c2 != '%' || valueContext != ValueContext.ENTITY_DECLARATION)))) {
                this.isWhitespace = (c2 <= ' ') & this.isWhitespace;
                this.position++;
            } else {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(this.buffer, i, this.position - i);
                if (c2 == '\r') {
                    if (this.position + 1 < this.limit || fillBuffer(2)) {
                        char[] cArr2 = this.buffer;
                        int i4 = this.position;
                        if (cArr2[i4 + 1] == '\n') {
                            this.position = i4 + 1;
                        }
                    }
                    c2 = valueContext == ValueContext.ATTRIBUTE ? StringUtil.SPACE : '\n';
                } else if (c2 == '\n') {
                    c2 = StringUtil.SPACE;
                } else if (c2 == '&') {
                    this.isWhitespace = false;
                    readEntity(sb, false, z2, valueContext);
                    i = this.position;
                } else if (c2 == '<') {
                    if (valueContext == ValueContext.ATTRIBUTE) {
                        checkRelaxed("Illegal: \"<\" inside attribute value");
                    }
                    this.isWhitespace = false;
                } else {
                    if (c2 != ']') {
                        if (c2 == '%') {
                            throw new XmlPullParserException("This parser doesn't support parameter entities", this, null);
                        }
                        throw new AssertionError();
                    }
                    if (this.position + 2 < this.limit || fillBuffer(3)) {
                        char[] cArr3 = this.buffer;
                        int i5 = this.position;
                        if (cArr3[i5 + 1] == ']' && cArr3[i5 + 2] == '>') {
                            checkRelaxed("Illegal: \"]]>\" outside CDATA section");
                        }
                    }
                    this.isWhitespace = false;
                }
                this.position++;
                sb.append(c2);
                i = this.position;
            }
        }
    }

    private void read(char c) throws XmlPullParserException, IOException {
        int iPeekCharacter = peekCharacter();
        if (iPeekCharacter != c) {
            checkRelaxed("expected: '" + c + "' actual: '" + ((char) iPeekCharacter) + FormatHelper.QUOTE);
            if (iPeekCharacter == -1) {
                return;
            }
        }
        this.position++;
    }

    private void read(char[] cArr) throws XmlPullParserException, IOException {
        if (this.position + cArr.length > this.limit && !fillBuffer(cArr.length)) {
            checkRelaxed("expected: '" + new String(cArr) + "' but was EOF");
            return;
        }
        for (int i = 0; i < cArr.length; i++) {
            if (this.buffer[this.position + i] != cArr[i]) {
                checkRelaxed("expected: \"" + new String(cArr) + "\" but was \"" + new String(this.buffer, this.position, cArr.length) + "...\"");
            }
        }
        this.position += cArr.length;
    }

    private int peekCharacter() throws XmlPullParserException, IOException {
        if (this.position < this.limit || fillBuffer(1)) {
            return this.buffer[this.position];
        }
        return -1;
    }

    private boolean fillBuffer(int i) throws XmlPullParserException, IOException {
        int i2;
        int i3;
        while (this.nextContentSource != null) {
            if (this.position < this.limit) {
                throw new XmlPullParserException("Unbalanced entity!", this, null);
            }
            popContentSource();
            if (this.limit - this.position >= i) {
                return true;
            }
        }
        int i4 = 0;
        while (true) {
            i2 = this.position;
            if (i4 >= i2) {
                break;
            }
            if (this.buffer[i4] == '\n') {
                this.bufferStartLine++;
                this.bufferStartColumn = 0;
            } else {
                this.bufferStartColumn++;
            }
            i4++;
        }
        StringBuilder sb = this.bufferCapture;
        if (sb != null) {
            sb.append(this.buffer, 0, i2);
        }
        int i5 = this.limit;
        int i6 = this.position;
        if (i5 != i6) {
            int i7 = i5 - i6;
            this.limit = i7;
            char[] cArr = this.buffer;
            System.arraycopy(cArr, i6, cArr, 0, i7);
        } else {
            this.limit = 0;
        }
        this.position = 0;
        do {
            Reader reader = this.reader;
            char[] cArr2 = this.buffer;
            int i8 = this.limit;
            int i9 = reader.read(cArr2, i8, cArr2.length - i8);
            if (i9 == -1) {
                return false;
            }
            i3 = this.limit + i9;
            this.limit = i3;
        } while (i3 < i);
        return true;
    }

    private String readName() throws XmlPullParserException, IOException {
        if (this.position >= this.limit && !fillBuffer(1)) {
            checkRelaxed("name expected");
            return "";
        }
        int i = this.position;
        char c = this.buffer[i];
        if ((c >= 'a' && c <= 'z') || ((c >= 'A' && c <= 'Z') || c == '_' || c == ':' || c >= 192 || this.relaxed)) {
            this.position = i + 1;
            StringBuilder sb = null;
            while (true) {
                if (this.position >= this.limit) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(this.buffer, i, this.position - i);
                    if (!fillBuffer(1)) {
                        return sb.toString();
                    }
                    i = this.position;
                }
                char[] cArr = this.buffer;
                int i2 = this.position;
                char c2 = cArr[i2];
                if ((c2 < 'a' || c2 > 'z') && ((c2 < 'A' || c2 > 'Z') && !((c2 >= '0' && c2 <= '9') || c2 == '_' || c2 == '-' || c2 == ':' || c2 == '.' || c2 >= 183))) {
                    if (sb == null) {
                        return new String(this.buffer, i, this.position - i);
                    }
                    sb.append(cArr, i, i2 - i);
                    return sb.toString();
                }
                this.position = i2 + 1;
            }
        } else {
            checkRelaxed("name expected");
            return "";
        }
    }

    private void skip() throws XmlPullParserException, IOException {
        while (true) {
            if (this.position >= this.limit && !fillBuffer(1)) {
                return;
            }
            char[] cArr = this.buffer;
            int i = this.position;
            if (cArr[i] > ' ') {
                return;
            } else {
                this.position = i + 1;
            }
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(Reader reader) throws XmlPullParserException {
        this.reader = reader;
        this.type = 0;
        this.parsedTopLevelStartTag = false;
        this.f260name = null;
        this.namespace = null;
        this.degenerated = false;
        this.attributeCount = -1;
        this.encoding = null;
        this.version = null;
        this.standalone = null;
        if (reader == null) {
            return;
        }
        this.position = 0;
        this.limit = 0;
        this.bufferStartLine = 0;
        this.bufferStartColumn = 0;
        this.depth = 0;
        this.documentEntities = null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(InputStream inputStream, String str) throws XmlPullParserException {
        int i;
        this.position = 0;
        this.limit = 0;
        boolean z = str == null;
        if (inputStream == null) {
            throw new IllegalArgumentException("is == null");
        }
        String str2 = "UTF-8";
        if (z) {
            int i2 = 0;
            while (this.limit < 4 && (i = inputStream.read()) != -1) {
                try {
                    i2 = (i2 << 8) | i;
                    char[] cArr = this.buffer;
                    int i3 = this.limit;
                    this.limit = i3 + 1;
                    cArr[i3] = (char) i;
                } catch (Exception e) {
                    throw new XmlPullParserException("Invalid stream or encoding: " + e, this, e);
                }
            }
            if (this.limit == 4) {
                switch (i2) {
                    case -131072:
                        this.limit = 0;
                        str = "UTF-32LE";
                        break;
                    case 60:
                        this.buffer[0] = '<';
                        this.limit = 1;
                        str = "UTF-32BE";
                        break;
                    case 65279:
                        this.limit = 0;
                        str = "UTF-32BE";
                        break;
                    case 3932223:
                        char[] cArr2 = this.buffer;
                        cArr2[0] = '<';
                        cArr2[1] = MangleConstant.Q_MARK;
                        this.limit = 2;
                        str = CharEncoding.UTF_16BE;
                        break;
                    case 1006632960:
                        this.buffer[0] = '<';
                        this.limit = 1;
                        str = "UTF-32LE";
                        break;
                    case 1006649088:
                        char[] cArr3 = this.buffer;
                        cArr3[0] = '<';
                        cArr3[1] = MangleConstant.Q_MARK;
                        this.limit = 2;
                        str = CharEncoding.UTF_16LE;
                        break;
                    case 1010792557:
                        while (true) {
                            int i4 = inputStream.read();
                            if (i4 == -1) {
                                break;
                            } else {
                                char[] cArr4 = this.buffer;
                                int i5 = this.limit;
                                this.limit = i5 + 1;
                                cArr4[i5] = (char) i4;
                                if (i4 == 62) {
                                    String str3 = new String(this.buffer, 0, this.limit);
                                    int iIndexOf = str3.indexOf("encoding");
                                    if (iIndexOf != -1) {
                                        while (str3.charAt(iIndexOf) != '\"' && str3.charAt(iIndexOf) != '\'') {
                                            iIndexOf++;
                                        }
                                        int i6 = iIndexOf + 1;
                                        str = str3.substring(i6, str3.indexOf(str3.charAt(iIndexOf), i6));
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        int i7 = (-65536) & i2;
                        if (i7 == -16842752) {
                            char[] cArr5 = this.buffer;
                            cArr5[0] = (char) ((cArr5[2] << '\b') | cArr5[3]);
                            this.limit = 1;
                            str = CharEncoding.UTF_16BE;
                        } else if (i7 == -131072) {
                            char[] cArr6 = this.buffer;
                            cArr6[0] = (char) ((cArr6[3] << '\b') | cArr6[2]);
                            this.limit = 1;
                            str = CharEncoding.UTF_16LE;
                        } else if ((i2 & InputDeviceCompat.SOURCE_ANY) == -272908544) {
                            char[] cArr7 = this.buffer;
                            cArr7[0] = cArr7[3];
                            this.limit = 1;
                            str = "UTF-8";
                        }
                        break;
                }
            }
        }
        if (str != null) {
            str2 = str;
        }
        int i8 = this.limit;
        setInput(new InputStreamReader(inputStream, str2));
        this.encoding = str2;
        this.limit = i8;
        if (z || peekCharacter() != 65279) {
            return;
        }
        int i9 = this.limit - 1;
        this.limit = i9;
        char[] cArr8 = this.buffer;
        System.arraycopy(cArr8, 1, cArr8, 0, i9);
    }

    public void close() throws IOException {
        Reader reader = this.reader;
        if (reader != null) {
            reader.close();
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean getFeature(String str) {
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(str)) {
            return this.processNsp;
        }
        if (FEATURE_RELAXED.equals(str)) {
            return this.relaxed;
        }
        if (XmlPullParser.FEATURE_PROCESS_DOCDECL.equals(str)) {
            return this.processDocDecl;
        }
        return false;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getInputEncoding() {
        return this.encoding;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
        if (this.processDocDecl) {
            throw new IllegalStateException("Entity replacement text may not be defined with DOCTYPE processing enabled.");
        }
        if (this.reader == null) {
            throw new IllegalStateException("Entity replacement text must be defined after setInput()");
        }
        if (this.documentEntities == null) {
            this.documentEntities = new HashMap();
        }
        this.documentEntities.put(str, str2.toCharArray());
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public Object getProperty(String str) {
        if (str.equals(PROPERTY_XMLDECL_VERSION)) {
            return this.version;
        }
        if (str.equals(PROPERTY_XMLDECL_STANDALONE)) {
            return this.standalone;
        }
        if (!str.equals(PROPERTY_LOCATION)) {
            return null;
        }
        String str2 = this.location;
        return str2 != null ? str2 : this.reader.toString();
    }

    public String getRootElementName() {
        return this.rootElementName;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public String getPublicId() {
        return this.publicId;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getNamespaceCount(int i) {
        if (i > this.depth) {
            throw new IndexOutOfBoundsException();
        }
        return this.nspCounts[i];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespacePrefix(int i) {
        return this.nspStack[i * 2];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespaceUri(int i) {
        return this.nspStack[(i * 2) + 1];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace(String str) {
        if ("xml".equals(str)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if (XMLConstants.XMLNS_ATTRIBUTE.equals(str)) {
            return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
        }
        for (int namespaceCount = (getNamespaceCount(this.depth) << 1) - 2; namespaceCount >= 0; namespaceCount -= 2) {
            if (str == null) {
                String[] strArr = this.nspStack;
                if (strArr[namespaceCount] == null) {
                    return strArr[namespaceCount + 1];
                }
            } else if (str.equals(this.nspStack[namespaceCount])) {
                return this.nspStack[namespaceCount + 1];
            }
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getDepth() {
        return this.depth;
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getPositionDescription() {
        StringBuilder sb = new StringBuilder(this.type < TYPES.length ? TYPES[this.type] : "unknown");
        sb.append(StringUtil.SPACE);
        int i = this.type;
        if (i == 2 || i == 3) {
            if (this.degenerated) {
                sb.append("(empty) ");
            }
            sb.append('<');
            if (this.type == 3) {
                sb.append('/');
            }
            if (this.prefix != null) {
                sb.append("{" + this.namespace + "}" + this.prefix + ":");
            }
            sb.append(this.f260name);
            int i2 = this.attributeCount * 4;
            for (int i3 = 0; i3 < i2; i3 += 4) {
                sb.append(StringUtil.SPACE);
                int i4 = i3 + 1;
                if (this.attributes[i4] != null) {
                    sb.append("{" + this.attributes[i3] + "}" + this.attributes[i4] + ":");
                }
                sb.append(this.attributes[i3 + 2] + "='" + this.attributes[i3 + 3] + FormatHelper.QUOTE);
            }
            sb.append('>');
        } else if (i != 7) {
            if (i != 4) {
                sb.append(getText());
            } else if (this.isWhitespace) {
                sb.append("(whitespace)");
            } else {
                String text = getText();
                if (text.length() > 16) {
                    text = text.substring(0, 16) + "...";
                }
                sb.append(text);
            }
        }
        sb.append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + getLineNumber() + ":" + getColumnNumber());
        if (this.location != null) {
            sb.append(" in ");
            sb.append(this.location);
        } else if (this.reader != null) {
            sb.append(" in ");
            sb.append(this.reader.toString());
        }
        return sb.toString();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getLineNumber() {
        int i = this.bufferStartLine;
        for (int i2 = 0; i2 < this.position; i2++) {
            if (this.buffer[i2] == '\n') {
                i++;
            }
        }
        return i + 1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getColumnNumber() {
        int i = this.bufferStartColumn;
        for (int i2 = 0; i2 < this.position; i2++) {
            i = this.buffer[i2] == '\n' ? 0 : i + 1;
        }
        return i + 1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isWhitespace() throws XmlPullParserException {
        int i = this.type;
        if (i != 4 && i != 7 && i != 5) {
            throw new XmlPullParserException(ILLEGAL_TYPE, this, null);
        }
        return this.isWhitespace;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getText() {
        int i = this.type;
        if (i < 4) {
            return null;
        }
        if (i == 6 && this.unresolved) {
            return null;
        }
        String str = this.text;
        return str == null ? "" : str;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public char[] getTextCharacters(int[] iArr) {
        String text = getText();
        if (text == null) {
            iArr[0] = -1;
            iArr[1] = -1;
            return null;
        }
        char[] charArray = text.toCharArray();
        iArr[0] = 0;
        iArr[1] = charArray.length;
        return charArray;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace() {
        return this.namespace;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getName() {
        return this.f260name;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getPrefix() {
        return this.prefix;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isEmptyElementTag() throws XmlPullParserException {
        if (this.type != 2) {
            throw new XmlPullParserException(ILLEGAL_TYPE, this, null);
        }
        return this.degenerated;
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public int getAttributeCount() {
        return this.attributeCount;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeType(int i) {
        return "CDATA";
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getAttributeNamespace(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[i * 4];
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getAttributeName(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(i * 4) + 2];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributePrefix(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(i * 4) + 1];
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getAttributeValue(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(i * 4) + 3];
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getAttributeValue(String str, String str2) {
        for (int i = (this.attributeCount * 4) - 4; i >= 0; i -= 4) {
            if (this.attributes[i + 2].equals(str2) && (str == null || this.attributes[i].equals(str))) {
                return this.attributes[i + 3];
            }
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getEventType() throws XmlPullParserException {
        return this.type;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextTag() throws XmlPullParserException, IOException {
        next();
        if (this.type == 4 && this.isWhitespace) {
            next();
        }
        int i = this.type;
        if (i == 3 || i == 2) {
            return i;
        }
        throw new XmlPullParserException("unexpected type", this, null);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void require(int i, String str, String str2) throws XmlPullParserException, IOException {
        if (i != this.type || ((str != null && !str.equals(getNamespace())) || (str2 != null && !str2.equals(getName())))) {
            throw new XmlPullParserException("expected: " + TYPES[i] + " {" + str + "}" + str2, this, null);
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String nextText() throws XmlPullParserException, IOException {
        String text;
        if (this.type != 2) {
            throw new XmlPullParserException("precondition: START_TAG", this, null);
        }
        next();
        if (this.type == 4) {
            text = getText();
            next();
        } else {
            text = "";
        }
        if (this.type == 3) {
            return text;
        }
        throw new XmlPullParserException("END_TAG expected", this, null);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setFeature(String str, boolean z) throws XmlPullParserException {
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(str)) {
            this.processNsp = z;
        } else if (XmlPullParser.FEATURE_PROCESS_DOCDECL.equals(str)) {
            this.processDocDecl = z;
        } else {
            if (FEATURE_RELAXED.equals(str)) {
                this.relaxed = z;
                return;
            }
            throw new XmlPullParserException("unsupported feature: " + str, this, null);
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setProperty(String str, Object obj) throws XmlPullParserException {
        if (str.equals(PROPERTY_LOCATION)) {
            this.location = String.valueOf(obj);
            return;
        }
        throw new XmlPullParserException("unsupported property: " + str);
    }

    static class ContentSource {
        private final char[] buffer;
        private final int limit;
        private final ContentSource next;
        private final int position;

        ContentSource(ContentSource contentSource, char[] cArr, int i, int i2) {
            this.next = contentSource;
            this.buffer = cArr;
            this.position = i;
            this.limit = i2;
        }
    }

    private void pushContentSource(char[] cArr) {
        this.nextContentSource = new ContentSource(this.nextContentSource, this.buffer, this.position, this.limit);
        this.buffer = cArr;
        this.position = 0;
        this.limit = cArr.length;
    }

    private void popContentSource() {
        this.buffer = this.nextContentSource.buffer;
        this.position = this.nextContentSource.position;
        this.limit = this.nextContentSource.limit;
        this.nextContentSource = this.nextContentSource.next;
    }
}
