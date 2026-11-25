package org.apache.xerces.impl.xs.opti;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.xml.XMLConstants;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLString;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes13.dex */
public class SchemaDOM extends DefaultDocument {
    static final int relationsColResizeFactor = 10;
    static final int relationsRowResizeFactor = 15;
    int currLoc;
    private StringBuffer fAnnotationBuffer = null;
    boolean hidden;
    boolean inCDATA;
    int nextFreeLoc;
    ElementImpl parent;
    NodeImpl[][] relations;

    public SchemaDOM() {
        reset();
    }

    private static String escapeAttValue(String str, int i) {
        String str2;
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        stringBuffer.append(str.substring(0, i));
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                str2 = "&quot;";
            } else if (cCharAt == '<') {
                str2 = "&lt;";
            } else if (cCharAt == '&') {
                str2 = "&amp;";
            } else if (cCharAt == '\t') {
                str2 = "&#x9;";
            } else if (cCharAt == '\n') {
                str2 = "&#xA;";
            } else {
                if (cCharAt == '\r') {
                    str2 = "&#xD;";
                } else {
                    stringBuffer.append(cCharAt);
                }
                i++;
            }
            stringBuffer.append(str2);
            i++;
        }
        return stringBuffer.toString();
    }

    public static void indent(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            System.out.print(StringUtil.SPACE);
        }
    }

    private static String processAttValue(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"' || cCharAt == '<' || cCharAt == '&' || cCharAt == '\t' || cCharAt == '\n' || cCharAt == '\r') {
                return escapeAttValue(str, i);
            }
        }
        return str;
    }

    private void processElement(QName qName, XMLAttributes xMLAttributes, ElementImpl elementImpl) {
        elementImpl.prefix = qName.prefix;
        elementImpl.localpart = qName.localpart;
        elementImpl.rawname = qName.rawname;
        elementImpl.uri = qName.uri;
        elementImpl.schemaDOM = this;
        Attr[] attrArr = new Attr[xMLAttributes.getLength()];
        for (int i = 0; i < xMLAttributes.getLength(); i++) {
            attrArr[i] = new AttrImpl(elementImpl, xMLAttributes.getPrefix(i), xMLAttributes.getLocalName(i), xMLAttributes.getQName(i), xMLAttributes.getURI(i), xMLAttributes.getValue(i));
        }
        elementImpl.attrs = attrArr;
        if (this.nextFreeLoc == this.relations.length) {
            resizeRelations();
        }
        NodeImpl[][] nodeImplArr = this.relations;
        NodeImpl nodeImpl = nodeImplArr[this.currLoc][0];
        ElementImpl elementImpl2 = this.parent;
        if (nodeImpl != elementImpl2) {
            int i2 = this.nextFreeLoc;
            nodeImplArr[i2][0] = elementImpl2;
            this.nextFreeLoc = i2 + 1;
            this.currLoc = i2;
        }
        int i3 = 1;
        while (true) {
            NodeImpl[][] nodeImplArr2 = this.relations;
            int i4 = this.currLoc;
            NodeImpl[] nodeImplArr3 = nodeImplArr2[i4];
            if (i3 >= nodeImplArr3.length) {
                resizeRelations(i4);
                break;
            } else if (nodeImplArr3[i3] == null) {
                break;
            } else {
                i3++;
            }
        }
        NodeImpl[][] nodeImplArr4 = this.relations;
        int i5 = this.currLoc;
        nodeImplArr4[i5][i3] = elementImpl;
        this.parent.parentRow = i5;
        elementImpl.row = this.currLoc;
        elementImpl.col = i3;
    }

    private void resizeRelations() {
        NodeImpl[][] nodeImplArr = this.relations;
        int length = nodeImplArr.length + 15;
        NodeImpl[][] nodeImplArr2 = new NodeImpl[length][];
        System.arraycopy(nodeImplArr, 0, nodeImplArr2, 0, nodeImplArr.length);
        for (int length2 = this.relations.length; length2 < length; length2++) {
            nodeImplArr2[length2] = new NodeImpl[10];
        }
        this.relations = nodeImplArr2;
    }

    private void resizeRelations(int i) {
        NodeImpl[] nodeImplArr = this.relations[i];
        NodeImpl[] nodeImplArr2 = new NodeImpl[nodeImplArr.length + 10];
        System.arraycopy(nodeImplArr, 0, nodeImplArr2, 0, nodeImplArr.length);
        this.relations[i] = nodeImplArr2;
    }

    public static void traverse(Node node, int i) {
        indent(i);
        System.out.print("<" + node.getNodeName());
        if (node.hasAttributes()) {
            NamedNodeMap attributes = node.getAttributes();
            for (int i2 = 0; i2 < attributes.getLength(); i2++) {
                System.out.print("  " + ((Attr) attributes.item(i2)).getName() + "=\"" + ((Attr) attributes.item(i2)).getValue() + "\"");
            }
        }
        if (!node.hasChildNodes()) {
            System.out.println("/>");
            return;
        }
        System.out.println(">");
        int i3 = i + 4;
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            traverse(firstChild, i3);
        }
        indent(i);
        System.out.println("</" + node.getNodeName() + ">");
    }

    void characters(XMLString xMLString) {
        String str;
        if (this.inCDATA) {
            this.fAnnotationBuffer.append(xMLString.ch, xMLString.offset, xMLString.length);
            return;
        }
        StringBuffer stringBuffer = this.fAnnotationBuffer;
        for (int i = xMLString.offset; i < xMLString.offset + xMLString.length; i++) {
            char c = xMLString.ch[i];
            if (c == '&') {
                str = "&amp;";
            } else if (c == '<') {
                str = "&lt;";
            } else if (c == '>') {
                str = "&gt;";
            } else {
                if (c == '\r') {
                    str = "&#xD;";
                } else {
                    stringBuffer.append(c);
                }
            }
            stringBuffer.append(str);
        }
    }

    void charactersRaw(String str) {
        this.fAnnotationBuffer.append(str);
    }

    void comment(XMLString xMLString) {
        this.fAnnotationBuffer.append("<!--");
        if (xMLString.length > 0) {
            this.fAnnotationBuffer.append(xMLString.ch, xMLString.offset, xMLString.length);
        }
        this.fAnnotationBuffer.append("-->");
    }

    public ElementImpl emptyElement(QName qName, XMLAttributes xMLAttributes, int i, int i2) {
        return emptyElement(qName, xMLAttributes, i, i2, -1);
    }

    public ElementImpl emptyElement(QName qName, XMLAttributes xMLAttributes, int i, int i2, int i3) {
        ElementImpl elementImpl = new ElementImpl(i, i2, i3);
        processElement(qName, xMLAttributes, elementImpl);
        return elementImpl;
    }

    void endAnnotation(QName qName, ElementImpl elementImpl) {
        this.fAnnotationBuffer.append("\n</").append(qName.rawname).append(">");
        elementImpl.fAnnotation = this.fAnnotationBuffer.toString();
        this.fAnnotationBuffer = null;
    }

    void endAnnotationCDATA() {
        this.fAnnotationBuffer.append("]]>");
        this.inCDATA = false;
    }

    void endAnnotationElement(String str) {
        this.fAnnotationBuffer.append("</").append(str).append(">");
    }

    void endAnnotationElement(QName qName) {
        endAnnotationElement(qName.rawname);
    }

    public void endElement() {
        int i = this.parent.row;
        this.currLoc = i;
        this.parent = (ElementImpl) this.relations[i][0];
    }

    void endSyntheticAnnotationElement(String str, boolean z) {
        if (!z) {
            this.fAnnotationBuffer.append("</").append(str).append(">");
            return;
        }
        this.fAnnotationBuffer.append("\n</").append(str).append(">");
        this.parent.fSyntheticAnnotation = this.fAnnotationBuffer.toString();
        this.fAnnotationBuffer = null;
    }

    void endSyntheticAnnotationElement(QName qName, boolean z) {
        endSyntheticAnnotationElement(qName.rawname, z);
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultDocument, org.w3c.dom.Document
    public Element getDocumentElement() {
        return (ElementImpl) this.relations[0][1];
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultDocument, org.w3c.dom.Document
    public DOMImplementation getImplementation() {
        return SchemaDOMImplementation.getDOMImplementation();
    }

    public void printDOM() {
    }

    void processingInstruction(String str, XMLString xMLString) {
        this.fAnnotationBuffer.append("<?").append(str);
        if (xMLString.length > 0) {
            this.fAnnotationBuffer.append(StringUtil.SPACE).append(xMLString.ch, xMLString.offset, xMLString.length);
        }
        this.fAnnotationBuffer.append("?>");
    }

    public void reset() {
        if (this.relations != null) {
            for (int i = 0; i < this.relations.length; i++) {
                int i2 = 0;
                while (true) {
                    NodeImpl[] nodeImplArr = this.relations[i];
                    if (i2 < nodeImplArr.length) {
                        nodeImplArr[i2] = null;
                        i2++;
                    }
                }
            }
        }
        this.relations = new NodeImpl[15][];
        ElementImpl elementImpl = new ElementImpl(0, 0, 0);
        this.parent = elementImpl;
        elementImpl.rawname = "DOCUMENT_NODE";
        this.currLoc = 0;
        this.nextFreeLoc = 1;
        this.inCDATA = false;
        for (int i3 = 0; i3 < 15; i3++) {
            this.relations[i3] = new NodeImpl[10];
        }
        this.relations[this.currLoc][0] = this.parent;
    }

    void startAnnotation(String str, XMLAttributes xMLAttributes, NamespaceContext namespaceContext) {
        if (this.fAnnotationBuffer == null) {
            this.fAnnotationBuffer = new StringBuffer(256);
        }
        this.fAnnotationBuffer.append("<").append(str).append(" ");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < xMLAttributes.getLength(); i++) {
            String value = xMLAttributes.getValue(i);
            String prefix = xMLAttributes.getPrefix(i);
            String qName = xMLAttributes.getQName(i);
            if (prefix == XMLSymbols.PREFIX_XMLNS || qName == XMLSymbols.PREFIX_XMLNS) {
                arrayList.add(prefix == XMLSymbols.PREFIX_XMLNS ? xMLAttributes.getLocalName(i) : XMLSymbols.EMPTY_STRING);
            }
            this.fAnnotationBuffer.append(qName).append("=\"").append(processAttValue(value)).append("\" ");
        }
        Enumeration allPrefixes = namespaceContext.getAllPrefixes();
        while (allPrefixes.hasMoreElements()) {
            String str2 = (String) allPrefixes.nextElement();
            String uri = namespaceContext.getURI(str2);
            if (uri == null) {
                uri = XMLSymbols.EMPTY_STRING;
            }
            if (!arrayList.contains(str2)) {
                (str2 == XMLSymbols.EMPTY_STRING ? this.fAnnotationBuffer.append(XMLConstants.XMLNS_ATTRIBUTE) : this.fAnnotationBuffer.append("xmlns:").append(str2)).append("=\"").append(processAttValue(uri)).append("\" ");
            }
        }
        this.fAnnotationBuffer.append(">\n");
    }

    void startAnnotation(QName qName, XMLAttributes xMLAttributes, NamespaceContext namespaceContext) {
        startAnnotation(qName.rawname, xMLAttributes, namespaceContext);
    }

    void startAnnotationCDATA() {
        this.inCDATA = true;
        this.fAnnotationBuffer.append("<![CDATA[");
    }

    void startAnnotationElement(String str, XMLAttributes xMLAttributes) {
        this.fAnnotationBuffer.append("<").append(str);
        for (int i = 0; i < xMLAttributes.getLength(); i++) {
            this.fAnnotationBuffer.append(" ").append(xMLAttributes.getQName(i)).append("=\"").append(processAttValue(xMLAttributes.getValue(i))).append("\"");
        }
        this.fAnnotationBuffer.append(">");
    }

    void startAnnotationElement(QName qName, XMLAttributes xMLAttributes) {
        startAnnotationElement(qName.rawname, xMLAttributes);
    }

    public ElementImpl startElement(QName qName, XMLAttributes xMLAttributes, int i, int i2) {
        return startElement(qName, xMLAttributes, i, i2, -1);
    }

    public ElementImpl startElement(QName qName, XMLAttributes xMLAttributes, int i, int i2, int i3) {
        ElementImpl elementImpl = new ElementImpl(i, i2, i3);
        processElement(qName, xMLAttributes, elementImpl);
        this.parent = elementImpl;
        return elementImpl;
    }
}
