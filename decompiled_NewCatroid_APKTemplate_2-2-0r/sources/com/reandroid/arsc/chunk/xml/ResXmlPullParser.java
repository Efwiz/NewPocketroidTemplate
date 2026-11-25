package com.reandroid.arsc.chunk.xml;

import android.content.res.XmlResourceParser;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.coder.XmlSanitizer;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlPullParser implements XmlResourceParser {
    private DocumentLoadedListener documentLoadedListener;
    private PackageBlock mCurrentPackage;
    private ResXmlDocument mDocument;
    private boolean mDocumentCreatedHere;
    private final ParserEventList mEventList;
    private boolean mIsTagStared;
    private boolean processNamespaces;
    private boolean reportNamespaceAttrs;

    public interface DocumentLoadedListener {
        ResXmlDocument onDocumentLoaded(ResXmlDocument resXmlDocument);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getColumnNumber() {
        return 0;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public Object getProperty(String str) {
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isAttributeDefault(int i) {
        return false;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setProperty(String str, Object obj) throws XmlPullParserException {
    }

    public ResXmlPullParser(PackageBlock packageBlock) {
        this.mEventList = new ParserEventList();
        this.mCurrentPackage = packageBlock;
        this.processNamespaces = false;
        this.reportNamespaceAttrs = false;
    }

    public ResXmlPullParser() {
        this(null);
    }

    public PackageBlock getCurrentPackage() {
        return this.mCurrentPackage;
    }

    public void setCurrentPackage(PackageBlock packageBlock) {
        this.mCurrentPackage = packageBlock;
        ResXmlDocument resXmlDocument = this.mDocument;
        if (resXmlDocument != null) {
            resXmlDocument.setPackageBlock(packageBlock);
        }
    }

    public synchronized void setResXmlDocument(ResXmlDocument resXmlDocument) {
        closeDocument();
        this.mDocument = resXmlDocument;
        if (resXmlDocument.getPackageBlock() == null) {
            resXmlDocument.setPackageBlock(getCurrentPackage());
        }
        initDefaultFeatures();
        resXmlDocument.addEvents(this.mEventList);
    }

    public ResXmlDocument getResXmlDocument() {
        return this.mDocument;
    }

    public void closeDocument() {
        this.mEventList.clear();
        this.mIsTagStared = false;
        destroyDocument();
    }

    private void destroyDocument() {
        if (this.mDocumentCreatedHere) {
            this.mDocumentCreatedHere = false;
            ResXmlDocument resXmlDocument = this.mDocument;
            if (resXmlDocument == null) {
                return;
            }
            resXmlDocument.destroy();
            this.mDocument = null;
        }
    }

    @Override // android.content.res.XmlResourceParser, java.lang.AutoCloseable
    public void close() {
        closeDocument();
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public int getAttributeCount() {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement == null) {
            return 0;
        }
        int attributeCount = currentElement.getAttributeCount();
        return isCountNamespacesAsAttribute() ? attributeCount + currentElement.getNamespaceCount() : attributeCount;
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getAttributeName(int i) {
        if (isCountNamespacesAsAttribute() && i < getNamespaceCountInternal()) {
            return getNamespaceAttributeName(i);
        }
        return decodeAttributeName(getResXmlAttributeAt(i));
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getAttributeValue(int i) {
        if (isCountNamespacesAsAttribute() && i < getNamespaceCountInternal()) {
            return getNamespaceAttributeValue(i);
        }
        return decodeAttributeValue(getResXmlAttributeAt(i));
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getAttributeValue(String str, String str2) {
        return decodeAttributeValue(getAttribute(str, str2));
    }

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getPositionDescription() {
        StringBuilder sb = new StringBuilder(" Binary XML file line #");
        sb.append(this.mEventList.getLineNumber());
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement != null) {
            if (this.mEventList.getType() == 2) {
                sb.append(" START_TAG ");
            } else {
                sb.append(" END_TAG ");
            }
            sb.append('<');
            sb.append(currentElement.getName(true));
            sb.append('>');
        }
        return sb.toString();
    }

    @Override // android.util.AttributeSet
    public int getAttributeNameResource(int i) {
        ResXmlAttribute resXmlAttributeAt = getResXmlAttributeAt(i);
        if (resXmlAttributeAt != null) {
            return resXmlAttributeAt.getNameId();
        }
        return 0;
    }

    @Override // android.util.AttributeSet
    public boolean getAttributeBooleanValue(String str, String str2, boolean z) {
        ResXmlAttribute attribute = getAttribute(str, str2);
        if (attribute != null) {
            return getAttributeIntValue(attribute, 0) != 0;
        }
        return z;
    }

    @Override // android.util.AttributeSet
    public int getAttributeResourceValue(String str, String str2, int i) {
        ResXmlAttribute attribute = getAttribute(str, str2);
        return (attribute == null || attribute.getValueType() != ValueType.REFERENCE) ? i : attribute.getData();
    }

    @Override // android.util.AttributeSet
    public int getAttributeIntValue(String str, String str2, int i) {
        return getAttributeIntValue(getAttribute(str, str2), i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeUnsignedIntValue(String str, String str2, int i) {
        return getAttributeIntValue(getAttribute(str, str2), i);
    }

    @Override // android.util.AttributeSet
    public float getAttributeFloatValue(String str, String str2, float f) {
        ResXmlAttribute attribute = getAttribute(str, str2);
        return (attribute == null || attribute.getValueType() != ValueType.FLOAT) ? f : Float.intBitsToFloat(attribute.getData());
    }

    @Override // android.util.AttributeSet
    public int getAttributeListValue(int i, String[] strArr, int i2) {
        ResXmlAttribute resXmlAttributeAt = getResXmlAttributeAt(i);
        return (resXmlAttributeAt == null || resXmlAttributeAt.getValueType() != ValueType.STRING || strArr == null || strArr.length == 0) ? i2 : convertValueToList(resXmlAttributeAt.getValueAsString(), strArr, i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeListValue(String str, String str2, String[] strArr, int i) {
        ResXmlAttribute attribute = getAttribute(str, str2);
        return (attribute == null || attribute.getValueType() != ValueType.STRING || strArr == null || strArr.length == 0) ? i : convertValueToList(attribute.getValueAsString(), strArr, i);
    }

    @Override // android.util.AttributeSet
    public boolean getAttributeBooleanValue(int i, boolean z) {
        ResXmlAttribute resXmlAttributeAt = getResXmlAttributeAt(i);
        if (resXmlAttributeAt != null) {
            return getAttributeIntValue(resXmlAttributeAt, 0) != 0;
        }
        return z;
    }

    @Override // android.util.AttributeSet
    public int getAttributeResourceValue(int i, int i2) {
        ResXmlAttribute resXmlAttributeAt = getResXmlAttributeAt(i);
        return (resXmlAttributeAt == null || resXmlAttributeAt.getValueType() != ValueType.REFERENCE) ? i2 : resXmlAttributeAt.getData();
    }

    @Override // android.util.AttributeSet
    public int getAttributeIntValue(int i, int i2) {
        return getAttributeIntValue(getResXmlAttributeAt(i), i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeUnsignedIntValue(int i, int i2) {
        return getAttributeIntValue(getResXmlAttributeAt(i), i2);
    }

    @Override // android.util.AttributeSet
    public float getAttributeFloatValue(int i, float f) {
        ResXmlAttribute resXmlAttributeAt = getResXmlAttributeAt(i);
        return (resXmlAttributeAt == null || resXmlAttributeAt.getValueType() != ValueType.FLOAT) ? f : Float.intBitsToFloat(resXmlAttributeAt.getData());
    }

    @Override // android.util.AttributeSet
    public String getIdAttribute() {
        ResXmlAttribute idAttribute;
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement == null || (idAttribute = currentElement.getIdAttribute()) == null) {
            return null;
        }
        return idAttribute.getName();
    }

    @Override // android.util.AttributeSet
    public String getClassAttribute() {
        ResXmlAttribute classAttribute;
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement == null || (classAttribute = currentElement.getClassAttribute()) == null) {
            return null;
        }
        return classAttribute.getName();
    }

    @Override // android.util.AttributeSet
    public int getIdAttributeResourceValue(int i) {
        ResXmlAttribute idAttribute;
        ResXmlElement currentElement = getCurrentElement();
        return (currentElement == null || (idAttribute = currentElement.getIdAttribute()) == null) ? i : idAttribute.getNameId();
    }

    @Override // android.util.AttributeSet
    public int getStyleAttribute() {
        ResXmlAttribute styleAttribute;
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement == null || (styleAttribute = currentElement.getStyleAttribute()) == null) {
            return 0;
        }
        return styleAttribute.getNameId();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setFeature(String str, boolean z) throws XmlPullParserException {
        boolean z2;
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(str)) {
            z2 = this.processNamespaces != z;
            this.processNamespaces = z;
        } else if (XmlPullParser.FEATURE_REPORT_NAMESPACE_ATTRIBUTES.equals(str)) {
            z2 = this.reportNamespaceAttrs != z;
            this.reportNamespaceAttrs = z;
        } else {
            throw new XmlPullParserException("Unsupported feature: " + str);
        }
        if (z2 && this.mIsTagStared) {
            throw new XmlPullParserException("Feature changed during parsing: " + str + ", state=" + z);
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean getFeature(String str) {
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(str)) {
            return this.processNamespaces;
        }
        if (XmlPullParser.FEATURE_REPORT_NAMESPACE_ATTRIBUTES.equals(str)) {
            return this.reportNamespaceAttrs;
        }
        return false;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(Reader reader) throws XmlPullParserException {
        InputStream fromLock = getFromLock(reader);
        if (fromLock == null) {
            throw new XmlPullParserException("Can't parse binary xml from reader");
        }
        setInput(fromLock, null);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(InputStream inputStream, String str) throws XmlPullParserException {
        loadResXmlDocument(inputStream);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getInputEncoding() {
        return "UTF-8";
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getNamespaceCount(int i) throws XmlPullParserException {
        if (isCountNamespacesAsAttribute()) {
            return 0;
        }
        ResXmlElement currentElement = getCurrentElement();
        while (currentElement != null && currentElement.getDepth() > i) {
            currentElement = currentElement.getParentElement();
        }
        if (currentElement != null) {
            return currentElement.getNamespaceCount();
        }
        return 0;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespacePrefix(int i) throws XmlPullParserException {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement != null) {
            return currentElement.getNamespaceAt(i).getPrefix();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespaceUri(int i) throws XmlPullParserException {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement != null) {
            return currentElement.getNamespaceAt(i).getUri();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace(String str) {
        ResXmlNamespace namespaceByPrefix;
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement == null || (namespaceByPrefix = currentElement.getNamespaceByPrefix(str)) == null) {
            return null;
        }
        return namespaceByPrefix.getUri();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getDepth() {
        int type = this.mEventList.getType();
        if (type == 2 || type == 3 || type == 4) {
            return this.mEventList.getXmlNode().getDepth();
        }
        return 0;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getLineNumber() {
        return this.mEventList.getLineNumber();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isWhitespace() throws XmlPullParserException {
        String text = getText();
        return text == null || text.trim().length() == 0;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getText() {
        return this.mEventList.getText();
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
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement != null) {
            return currentElement.getUri();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getName() {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement != null) {
            return currentElement.getName();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getPrefix() {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement != null) {
            return currentElement.getPrefix();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isEmptyElementTag() throws XmlPullParserException {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement != null) {
            return currentElement.size() == 0 && currentElement.getAttributeCount() == 0;
        }
        return true;
    }

    @Override // android.content.res.XmlResourceParser, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    public String getAttributeNamespace(int i) {
        ResXmlAttribute resXmlAttributeAt;
        if (this.processNamespaces || (resXmlAttributeAt = getResXmlAttributeAt(i)) == null) {
            return null;
        }
        return resXmlAttributeAt.getUri();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributePrefix(int i) {
        ResXmlAttribute resXmlAttributeAt;
        if (this.processNamespaces || (resXmlAttributeAt = getResXmlAttributeAt(i)) == null) {
            return null;
        }
        return resXmlAttributeAt.getPrefix();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeType(int i) {
        return "CDATA";
    }

    private String decodeAttributeName(ResXmlAttribute resXmlAttribute) {
        if (resXmlAttribute != null) {
            return resXmlAttribute.decodeName(this.processNamespaces);
        }
        return null;
    }

    private String decodeAttributeValue(ResXmlAttribute resXmlAttribute) {
        if (resXmlAttribute == null) {
            return null;
        }
        String strDecodeValue = resXmlAttribute.decodeValue();
        return resXmlAttribute.getValueType() == ValueType.STRING ? XmlSanitizer.escapeSpecialCharacter(strDecodeValue) : strDecodeValue;
    }

    public ResXmlAttribute getResXmlAttributeAt(int i) {
        int realAttributeIndex = getRealAttributeIndex(i);
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement == null) {
            return null;
        }
        return currentElement.getAttributeAt(realAttributeIndex);
    }

    public ResXmlAttribute getAttribute(String str, String str2) {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement == null) {
            return null;
        }
        Iterator<ResXmlAttribute> attributes = currentElement.getAttributes();
        while (attributes.hasNext()) {
            ResXmlAttribute next = attributes.next();
            if (ObjectsUtil.equals(str, next.getUri()) && ObjectsUtil.equals(str2, next.getName())) {
                return next;
            }
        }
        return null;
    }

    public ResXmlElement getCurrentElement() {
        int type = this.mEventList.getType();
        if (type == 2 || type == 3) {
            return this.mEventList.getElement();
        }
        return null;
    }

    private int convertValueToList(String str, String[] strArr, int i) {
        if (!StringsUtil.isEmpty(str)) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (str.equals(strArr[i2])) {
                    return i2;
                }
            }
        }
        return i;
    }

    private int getAttributeIntValue(ResXmlAttribute resXmlAttribute, int i) {
        int type;
        return (resXmlAttribute == null || (type = resXmlAttribute.getType() & 255) <= 16 || type > 31) ? i : resXmlAttribute.getData();
    }

    private int getRealAttributeIndex(int i) {
        return isCountNamespacesAsAttribute() ? i - getNamespaceCountInternal() : i;
    }

    private int getNamespaceCountInternal() {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement != null) {
            return currentElement.getNamespaceCount();
        }
        return 0;
    }

    private boolean isCountNamespacesAsAttribute() {
        return this.processNamespaces & this.reportNamespaceAttrs;
    }

    private String getNamespaceAttributeName(int i) {
        return "xmlns:" + getCurrentElement().getNamespaceAt(i).getPrefix();
    }

    private String getNamespaceAttributeValue(int i) {
        return getCurrentElement().getNamespaceAt(i).getUri();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getEventType() throws XmlPullParserException {
        return this.mEventList.getType();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int next() throws XmlPullParserException, IOException {
        this.mEventList.next();
        int type = this.mEventList.getType();
        if (type == 2) {
            this.mIsTagStared = true;
        }
        return type;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextToken() throws XmlPullParserException, IOException {
        return next();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void require(int i, String str, String str2) throws XmlPullParserException, IOException {
        if (i != getEventType() || ((str != null && !str.equals(getNamespace())) || (str2 != null && !str2.equals(getName())))) {
            throw new XmlPullParserException("expected: " + TYPES[i] + " {" + str + "}" + str2, this, null);
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String nextText() throws XmlPullParserException, IOException {
        int eventType = getEventType();
        if (eventType != 2) {
            throw new XmlPullParserException("precondition: START_TAG", this, null);
        }
        while (eventType != 4 && eventType != 3 && eventType != 1) {
            eventType = next();
        }
        if (eventType == 4) {
            return getText();
        }
        return "";
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextTag() throws XmlPullParserException, IOException {
        if (getEventType() != 2) {
            throw new XmlPullParserException("precondition: START_TAG", this, null);
        }
        int next = next();
        while (next != 2 && next != 1) {
            next = next();
        }
        return next;
    }

    private static InputStream getFromLock(Reader reader) {
        try {
            Field declaredField = Reader.class.getDeclaredField("lock");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(reader);
            if (obj instanceof InputStream) {
                return (InputStream) obj;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public void setDocumentLoadedListener(DocumentLoadedListener documentLoadedListener) {
        this.documentLoadedListener = documentLoadedListener;
    }

    private void loadResXmlDocument(InputStream inputStream) throws XmlPullParserException {
        synchronized (this) {
            ResXmlDocument resXmlDocument = new ResXmlDocument();
            try {
                resXmlDocument.readBytes(inputStream);
                resXmlDocument.setPackageBlock(getCurrentPackage());
                DocumentLoadedListener documentLoadedListener = this.documentLoadedListener;
                if (documentLoadedListener != null) {
                    resXmlDocument = documentLoadedListener.onDocumentLoaded(resXmlDocument);
                }
                setResXmlDocument(resXmlDocument);
                this.mDocumentCreatedHere = true;
            } catch (IOException e) {
                XmlPullParserException xmlPullParserException = new XmlPullParserException(e.getMessage());
                xmlPullParserException.initCause(e);
                throw xmlPullParserException;
            }
        }
    }

    private void initDefaultFeatures() {
        this.processNamespaces = true;
        this.reportNamespaceAttrs = true;
    }
}
