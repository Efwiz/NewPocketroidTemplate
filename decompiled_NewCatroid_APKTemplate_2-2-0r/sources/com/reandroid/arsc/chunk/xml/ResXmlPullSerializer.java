package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.model.ResourceLibrary;
import com.reandroid.xml.XMLUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlPullSerializer implements XmlSerializer {
    private ResXmlElement mCurrentElement;
    private PackageBlock mCurrentPackage;
    private StringBuilder mCurrentText;
    private ResXmlDocument mDocument;
    private boolean mEndDocument;
    private boolean mValidateValues;

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) throws IllegalStateException, IOException, IllegalArgumentException {
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) throws IllegalStateException, IOException, IllegalArgumentException {
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        return false;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        return null;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) throws IllegalStateException, IOException, IllegalArgumentException {
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) throws IllegalStateException, IOException, IllegalArgumentException {
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z) throws IllegalStateException, IllegalArgumentException {
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) throws IllegalStateException, IllegalArgumentException {
    }

    public ResXmlDocument getResultDocument() {
        ResXmlDocument resXmlDocument = this.mDocument;
        if (resXmlDocument != null) {
            resXmlDocument.refreshFull();
        }
        return resXmlDocument;
    }

    public void setValidateValues(boolean z) {
        this.mValidateValues = z;
    }

    public boolean isValidateValues() {
        return this.mValidateValues;
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

    public void setDocument(ResXmlDocument resXmlDocument) {
        this.mDocument = resXmlDocument;
        if (resXmlDocument == null) {
            return;
        }
        PackageBlock packageBlock = resXmlDocument.getPackageBlock();
        if (packageBlock == null) {
            resXmlDocument.setPackageBlock(getCurrentPackage());
        } else if (getCurrentPackage() == null) {
            setCurrentPackage(packageBlock);
        }
    }

    private ResXmlDocument getCurrentDocument() {
        ResXmlDocument resXmlDocument = this.mDocument;
        if (this.mEndDocument) {
            this.mCurrentElement = null;
            this.mEndDocument = false;
            resXmlDocument = null;
        }
        if (resXmlDocument == null) {
            resXmlDocument = new ResXmlDocument();
            this.mCurrentElement = null;
            this.mDocument = resXmlDocument;
        }
        if (resXmlDocument.getPackageBlock() == null) {
            resXmlDocument.setPackageBlock(getCurrentPackage());
        }
        return resXmlDocument;
    }

    private ResXmlElement getCurrentElement() {
        ResXmlElement resXmlElementCreateRootElement = this.mCurrentElement;
        if (resXmlElementCreateRootElement == null) {
            ResXmlDocument currentDocument = getCurrentDocument();
            ResXmlElement documentElement = currentDocument.getDocumentElement();
            resXmlElementCreateRootElement = documentElement == null ? currentDocument.createRootElement(null) : documentElement;
            this.mCurrentElement = resXmlElementCreateRootElement;
        }
        return resXmlElementCreateRootElement;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new IllegalArgumentException("Can not set OutputStream");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new IllegalArgumentException("Can not set OutputStream");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.mCurrentElement != null) {
            this.mEndDocument = true;
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IllegalStateException, IOException, IllegalArgumentException {
        this.mEndDocument = true;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        ResXmlElement currentElement = getCurrentElement();
        if (currentElement == null) {
            return;
        }
        currentElement.getOrCreateNamespace(str2, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z) throws IllegalArgumentException {
        ResXmlElement resXmlElement;
        String str2;
        if (str == null || (resXmlElement = this.mCurrentElement) == null) {
            return null;
        }
        ResXmlNamespace namespaceByUri = resXmlElement.getNamespaceByUri(str);
        if (namespaceByUri == null && z) {
            if (str.equals(ResourceLibrary.URI_ANDROID)) {
                str2 = ResourceLibrary.PREFIX_ANDROID;
            } else {
                str2 = ResourceLibrary.PREFIX_APP;
            }
            namespaceByUri = resXmlElement.getOrCreateNamespace(str, str2);
        }
        if (namespaceByUri == null) {
            return null;
        }
        return namespaceByUri.getPrefix();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        ResXmlElement resXmlElement = this.mCurrentElement;
        if (resXmlElement != null) {
            return resXmlElement.getDepth();
        }
        return 0;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        ResXmlElement resXmlElement = this.mCurrentElement;
        if (resXmlElement != null) {
            return resXmlElement.getUri();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        ResXmlElement resXmlElement = this.mCurrentElement;
        if (resXmlElement != null) {
            return resXmlElement.getName();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public ResXmlPullSerializer startTag(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        String prefix;
        flushText();
        ResXmlElement currentElement = getCurrentElement();
        int iIndexOf = str2.indexOf(58);
        if (iIndexOf > 0) {
            prefix = str2.substring(0, iIndexOf);
            str2 = str2.substring(iIndexOf + 1);
        } else {
            ResXmlNamespace namespaceByUri = currentElement.getNamespaceByUri(str);
            prefix = namespaceByUri != null ? namespaceByUri.getPrefix() : null;
        }
        if (currentElement.getName() == null) {
            currentElement.setName(str2);
        } else {
            currentElement = currentElement.createChildElement(str2);
            this.mCurrentElement = currentElement;
        }
        currentElement.setNamespace(str, prefix);
        this.mCurrentElement = currentElement;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public ResXmlPullSerializer attribute(String str, String str2, String str3) throws IllegalStateException, IOException, IllegalArgumentException {
        String strSubstring;
        ResXmlStartNamespace startNamespaceByUri;
        ResXmlElement resXmlElement = this.mCurrentElement;
        int iIndexOf = str2.indexOf(58);
        if (iIndexOf > 0) {
            strSubstring = str2.substring(0, iIndexOf);
            str2 = str2.substring(iIndexOf + 1);
        } else {
            strSubstring = null;
        }
        resXmlElement.newAttribute().encode(isValidateValues(), str, (strSubstring != null || (startNamespaceByUri = resXmlElement.getStartNamespaceByUri(str)) == null) ? strSubstring : startNamespaceByUri.getPrefix(), str2, str3);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public ResXmlPullSerializer endTag(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        flushText();
        this.mCurrentElement.calculateAttributesOrder();
        this.mCurrentElement = this.mCurrentElement.getParentElement();
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public ResXmlPullSerializer text(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        appendText(str);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public ResXmlPullSerializer text(char[] cArr, int i, int i2) throws IllegalStateException, IOException, IllegalArgumentException {
        return text(new String(cArr, i, i2));
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        appendText(XMLUtil.decodeEntityRef(str));
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        getCurrentElement().setComment(str);
    }

    private void flushText() {
        StringBuilder sb = this.mCurrentText;
        if (sb == null) {
            return;
        }
        String string = sb.toString();
        this.mCurrentText = null;
        if (isIndent(string)) {
            return;
        }
        getCurrentElement().addResXmlText(string);
    }

    private void appendText(String str) {
        if (str == null) {
            return;
        }
        StringBuilder sb = this.mCurrentText;
        if (sb == null) {
            sb = new StringBuilder();
            this.mCurrentText = sb;
        }
        sb.append(str);
    }

    private static boolean isIndent(String str) {
        if (str.length() == 0) {
            return true;
        }
        char[] charArray = str.toCharArray();
        if (charArray[0] != '\n') {
            return false;
        }
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[i] != ' ') {
                return false;
            }
        }
        return true;
    }
}
