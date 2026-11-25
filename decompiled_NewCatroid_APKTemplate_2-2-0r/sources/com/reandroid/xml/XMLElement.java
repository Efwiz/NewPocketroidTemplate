package com.reandroid.xml;

import com.reandroid.common.Namespace;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.IndexIterator;
import com.reandroid.utils.collection.SizedSupplier;
import com.reandroid.xml.base.Attribute;
import com.reandroid.xml.base.Comment;
import com.reandroid.xml.base.Element;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class XMLElement extends XMLNodeTree implements Element<XMLNode> {
    private static final ArrayCollection<XMLAttribute> EMPTY_ATTRIBUTES = ArrayCollection.empty();
    private static final ArrayCollection<XMLNamespace> EMPTY_NAMESPACES = ArrayCollection.empty();
    private ArrayCollection<XMLAttribute> mAttributes;
    private String mName;
    private XMLNamespace mNamespace;
    private ArrayCollection<XMLNamespace> mNamespaceList;

    @Override // com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public /* bridge */ /* synthetic */ Comment newComment() {
        return super.newComment();
    }

    public XMLElement() {
        this.mAttributes = EMPTY_ATTRIBUTES;
        this.mNamespaceList = EMPTY_NAMESPACES;
    }

    public XMLElement(String str) {
        this();
        setName(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.xml.XMLNode
    public XMLElement newCopy(XMLNode xMLNode) {
        XMLElement xMLElementNewElement = newElement();
        if (xMLNode instanceof XMLNodeTree) {
            ((XMLNodeTree) xMLNode).add((XMLNode) xMLElementNewElement);
        }
        int namespaceCount = getNamespaceCount();
        for (int i = 0; i < namespaceCount; i++) {
            getNamespaceAt(i).newCopy(xMLElementNewElement);
        }
        setName(getUri(), getPrefix(), getName(false));
        int attributeCount = getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            xMLElementNewElement.addAttribute(getAttributeAt(i2).newCopy((XMLNode) xMLElementNewElement));
        }
        Iterator<XMLNode> it = iterator();
        while (it.hasNext()) {
            it.next().newCopy(xMLElementNewElement);
        }
        return xMLElementNewElement;
    }

    public void addText(String str) {
        XMLText xMLTextNewText = newText();
        xMLTextNewText.setText(str);
        add((XMLNode) xMLTextNewText);
    }

    public XMLAttribute getAttributeAt(int i) {
        return this.mAttributes.get(i);
    }

    public Iterator<? extends XMLAttribute> getAttributes() {
        return new IndexIterator(new SizedSupplier<XMLAttribute>() { // from class: com.reandroid.xml.XMLElement.1
            @Override // com.reandroid.utils.collection.SizedItem
            public int size() {
                return XMLElement.this.getAttributeCount();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.reandroid.utils.collection.SizedSupplier
            public XMLAttribute get(int i) {
                return XMLElement.this.getAttributeAt(i);
            }
        });
    }

    @Override // com.reandroid.xml.base.NamedNode
    public String getUri() {
        XMLNamespace namespace = getNamespace();
        if (namespace != null) {
            return namespace.getUri();
        }
        return null;
    }

    @Override // com.reandroid.xml.base.NamedNode
    public String getPrefix() {
        XMLNamespace namespace = getNamespace();
        if (namespace != null) {
            return namespace.getPrefix();
        }
        return null;
    }

    @Override // com.reandroid.xml.base.NamedNode
    public XMLNamespace getNamespace() {
        return this.mNamespace;
    }

    public void setNamespace(XMLNamespace xMLNamespace) {
        this.mNamespace = xMLNamespace;
    }

    @Override // com.reandroid.xml.base.NamedNode
    public void setNamespace(Namespace namespace) {
        if (namespace == null) {
            setNamespace(null, null);
        } else {
            setNamespace(namespace.getUri(), namespace.getPrefix());
        }
    }

    public void setNamespace(String str, String str2) {
        setNamespace(getOrCreateXMLNamespace(str, str2));
    }

    @Override // com.reandroid.xml.base.Element
    public int getNamespaceCount() {
        return this.mNamespaceList.size();
    }

    @Override // com.reandroid.xml.base.Element
    public XMLNamespace getNamespaceAt(int i) {
        return this.mNamespaceList.get(i);
    }

    @Override // com.reandroid.xml.base.Element
    public Iterator<? extends XMLNamespace> getNamespaces() {
        return this.mNamespaceList.iterator();
    }

    @Override // com.reandroid.xml.base.NodeFactory
    public XMLNamespace newNamespace(String str, String str2) {
        return new XMLNamespace(str, str2);
    }

    @Override // com.reandroid.xml.base.NodeFactory
    public XMLAttribute newAttribute() {
        return new XMLAttribute();
    }

    @Override // com.reandroid.xml.base.Element, com.reandroid.xml.base.NodeFactory
    public XMLElement newElement() {
        return new XMLElement();
    }

    @Override // com.reandroid.xml.base.NodeFactory
    public XMLText newText() {
        return super.newText();
    }

    public void addNamespace(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        addNamespace(newNamespace(str, str2));
    }

    public void addNamespace(XMLNamespace xMLNamespace) {
        if (xMLNamespace == null || this.mNamespaceList.contains(xMLNamespace)) {
            return;
        }
        ArrayCollection<XMLNamespace> arrayCollection = this.mNamespaceList;
        if (arrayCollection == EMPTY_NAMESPACES) {
            this.mNamespaceList = new ArrayCollection<>();
        } else if (arrayCollection.contains(xMLNamespace)) {
            return;
        }
        this.mNamespaceList.add(xMLNamespace);
    }

    public XMLNamespace getOrCreateXMLNamespace(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        XMLNamespace xMLNamespace = getXMLNamespace(str, str2);
        if (xMLNamespace != null) {
            return xMLNamespace;
        }
        XMLNamespace xMLNamespaceNewNamespace = newNamespace(str, str2);
        getRootElement().addNamespace(xMLNamespaceNewNamespace);
        return xMLNamespaceNewNamespace;
    }

    public XMLNamespace getXMLNamespace(String str, String str2) {
        if (str != null && str2 != null) {
            int namespaceCount = getNamespaceCount();
            for (int i = 0; i < namespaceCount; i++) {
                XMLNamespace namespaceAt = getNamespaceAt(i);
                if (namespaceAt.isEqual(str, str2)) {
                    return namespaceAt;
                }
            }
            XMLElement parentElement = getParentElement();
            if (parentElement != null) {
                return parentElement.getXMLNamespace(str, str2);
            }
        }
        return null;
    }

    public XMLNamespace getXMLNamespaceByUri(String str) {
        if (str == null) {
            return null;
        }
        int namespaceCount = getNamespaceCount();
        for (int i = 0; i < namespaceCount; i++) {
            XMLNamespace namespaceAt = getNamespaceAt(i);
            if (str.equals(namespaceAt.getUri())) {
                return namespaceAt;
            }
        }
        XMLElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.getXMLNamespaceByUri(str);
        }
        return null;
    }

    public XMLNamespace getXMLNamespaceByPrefix(String str) {
        if (str == null) {
            return null;
        }
        int namespaceCount = getNamespaceCount();
        for (int i = 0; i < namespaceCount; i++) {
            XMLNamespace namespaceAt = getNamespaceAt(i);
            if (str.equals(namespaceAt.getPrefix())) {
                return namespaceAt;
            }
        }
        XMLElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.getXMLNamespaceByPrefix(str);
        }
        return null;
    }

    public Collection<XMLAttribute> listAttributes() {
        return this.mAttributes;
    }

    public int getChildElementsCount() {
        return super.countNodesWithType(XMLElement.class);
    }

    public List<XMLElement> getChildElementList() {
        return CollectionUtil.toList(iterator(XMLElement.class));
    }

    public XMLElement getElement(String str) {
        return (XMLElement) CollectionUtil.getFirst(getElements(str));
    }

    public Iterator<? extends XMLElement> getElements() {
        return iterator(XMLElement.class);
    }

    public Iterator<XMLElement> getElements(Predicate<XMLElement> predicate) {
        return iterator(XMLElement.class, predicate);
    }

    public Iterator<XMLElement> getElements(final String str) {
        return getElements(new Predicate() { // from class: com.reandroid.xml.XMLElement$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((XMLElement) obj).equalsName(str);
            }
        });
    }

    @Override // com.reandroid.xml.base.Element
    public int getAttributeCount() {
        return this.mAttributes.size();
    }

    public String getAttributeValue(String str) {
        XMLAttribute attribute = getAttribute(str);
        if (attribute != null) {
            return attribute.getValueAsString();
        }
        return null;
    }

    public XMLAttribute getAttribute(String str) {
        if (str == null) {
            return null;
        }
        int attributeCount = getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            XMLAttribute attributeAt = getAttributeAt(i);
            if (attributeAt.equalsName(str)) {
                return attributeAt;
            }
        }
        return null;
    }

    public void clearAttributes() {
        if (this.mAttributes.size() == 0) {
            return;
        }
        for (int i = 0; i < this.mAttributes.size(); i++) {
            this.mAttributes.get(i).setParentNode(null);
        }
        this.mAttributes.clear();
        this.mAttributes.trimToSize();
    }

    public XMLAttribute removeAttribute(String str) {
        XMLAttribute attribute = getAttribute(str);
        if (attribute != null) {
            attribute.setParentNode(null);
        }
        return attribute;
    }

    public XMLAttribute removeAttribute(XMLAttribute xMLAttribute) {
        if (this.mAttributes.remove(xMLAttribute) && xMLAttribute != null) {
            xMLAttribute.setParentNode(null);
        }
        return xMLAttribute;
    }

    public XMLAttribute removeAttributeAt(int i) {
        XMLAttribute xMLAttributeRemove = this.mAttributes.remove(i);
        if (xMLAttributeRemove != null) {
            xMLAttributeRemove.setParentNode(null);
        }
        return xMLAttributeRemove;
    }

    public XMLAttribute setAttribute(String str, String str2) {
        if (XMLUtil.isEmpty(str)) {
            return null;
        }
        XMLAttribute attribute = getAttribute(str);
        if (attribute == null) {
            if (XMLNamespace.looksNamespace(str, str2)) {
                addNamespace(newNamespace(str2, XMLUtil.splitName(str)));
            } else {
                addAttribute(newAttribute().set(str, str2));
            }
        } else {
            attribute.setValue(str2);
        }
        return attribute;
    }

    public void addAttribute(String str, String str2) {
        if (XMLUtil.isEmpty(str)) {
            return;
        }
        if (XMLNamespace.looksNamespace(str, str2)) {
            addNamespace(newNamespace(str2, XMLUtil.splitName(str)));
        } else {
            addAttribute(newAttribute().set(str, str2));
        }
    }

    public void addAttribute(String str, String str2, String str3, String str4) {
        if (XMLUtil.isEmpty(str3)) {
            return;
        }
        if (XMLNamespace.looksNamespace(str3, str4)) {
            addNamespace(newNamespace(str4, XMLUtil.splitName(str3)));
            return;
        }
        XMLAttribute xMLAttribute = new XMLAttribute();
        addAttribute(xMLAttribute);
        xMLAttribute.setName(str, str2, str3);
        xMLAttribute.setValue(str4);
    }

    @Override // com.reandroid.xml.base.Element
    public void addAttribute(Attribute attribute) {
        if (attribute == null) {
            return;
        }
        XMLAttribute xMLAttribute = (XMLAttribute) attribute;
        if (this.mAttributes == EMPTY_ATTRIBUTES) {
            this.mAttributes = new ArrayCollection<>();
        }
        this.mAttributes.add(xMLAttribute);
        xMLAttribute.setParentNode(this);
    }

    @Override // com.reandroid.xml.base.Element
    public void addAttribute(int i, Attribute attribute) {
        if (attribute == null) {
            return;
        }
        XMLAttribute xMLAttribute = (XMLAttribute) attribute;
        if (this.mAttributes == EMPTY_ATTRIBUTES) {
            this.mAttributes = new ArrayCollection<>();
        }
        this.mAttributes.add(i, xMLAttribute);
        xMLAttribute.setParentNode(this);
    }

    public XMLElement getParentElement() {
        XMLNode parentNode = getParentNode();
        if (parentNode instanceof XMLElement) {
            return (XMLElement) parentNode;
        }
        return null;
    }

    public XMLElement getRootElement() {
        XMLElement parentElement = getParentElement();
        return parentElement != null ? parentElement.getRootElement() : this;
    }

    public XMLDocument getParentDocument() {
        XMLNode parentNode = getRootElement().getParentNode();
        if (parentNode instanceof XMLDocument) {
            return (XMLDocument) parentNode;
        }
        return null;
    }

    public int getDepth() {
        int i = 1;
        for (XMLElement parentElement = getParentElement(); parentElement != null; parentElement = parentElement.getParentElement()) {
            i++;
        }
        return i;
    }

    public boolean equalsName(String str) {
        if (str == null) {
            return getName() == null;
        }
        String strSplitPrefix = XMLUtil.splitPrefix(str);
        if (strSplitPrefix == null || strSplitPrefix.equals(getPrefix())) {
            return str.equals(getName());
        }
        return false;
    }

    @Override // com.reandroid.xml.base.NamedNode
    public String getName() {
        return this.mName;
    }

    @Override // com.reandroid.xml.base.NamedNode
    public String getName(boolean z) {
        String prefix;
        String name2 = getName();
        return (z && (prefix = getPrefix()) != null) ? prefix + ":" + name2 : name2;
    }

    @Override // com.reandroid.xml.base.NamedNode
    public void setName(String str) {
        setName(null, null, str);
    }

    public void setName(String str, String str2) {
        setName(str, null, str2);
    }

    public void setName(String str, String str2, String str3) {
        XMLNamespace xMLNamespaceByPrefix;
        this.mName = XMLUtil.splitName(str3);
        if (str2 == null) {
            str2 = XMLUtil.splitPrefix(str3);
        }
        if (XMLUtil.isEmpty(str)) {
            str = null;
        }
        if (str == null && str2 == null) {
            return;
        }
        if (str == null) {
            xMLNamespaceByPrefix = getXMLNamespaceByPrefix(str2);
            if (xMLNamespaceByPrefix == null) {
                throw new IllegalArgumentException("Namespace not found for prefix: " + str2);
            }
        } else {
            XMLNamespace xMLNamespaceByUri = getXMLNamespaceByUri(str);
            if (xMLNamespaceByUri == null) {
                throw new IllegalArgumentException("Namespace not found for uri: " + str);
            }
            xMLNamespaceByPrefix = xMLNamespaceByUri;
        }
        setNamespace(xMLNamespaceByPrefix);
    }

    public String getTextContent() {
        return getTextContent(false);
    }

    public String getTextContent(boolean z) {
        StringWriter stringWriter = new StringWriter();
        try {
            Iterator<XMLNode> it = iterator();
            while (it.hasNext()) {
                it.next().write(stringWriter, true, z);
            }
            stringWriter.flush();
            stringWriter.close();
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public boolean hasChildElements() {
        return !CollectionUtil.isEmpty(getElements());
    }

    public boolean hasTextNode() {
        return !CollectionUtil.isEmpty((Iterator<?>) iterator(XMLText.class));
    }

    public boolean hasAttribute(String str) {
        return getAttribute(str) != null;
    }

    public void setTextContent(String str, boolean z) {
        super.clear();
        if (z) {
            str = XMLUtil.escapeXmlChars(str);
        }
        addText(str);
    }

    @Override // com.reandroid.xml.XMLNodeTree
    void startSerialize(XmlSerializer xmlSerializer) throws IOException {
        int namespaceCount = getNamespaceCount();
        for (int i = 0; i < namespaceCount; i++) {
            XMLNamespace namespaceAt = getNamespaceAt(i);
            xmlSerializer.setPrefix(namespaceAt.getPrefix(), namespaceAt.getUri());
        }
        xmlSerializer.startTag(getUri(), getName(false));
        int attributeCount = getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            getAttributeAt(i2).serialize(xmlSerializer);
        }
    }

    @Override // com.reandroid.xml.XMLNodeTree
    void endSerialize(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.endTag(getUri(), getName(false));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.xml.XMLNode, com.reandroid.xml.base.XmlReader
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        XMLComment xMLCommentNewComment;
        if (xmlPullParser.getEventType() != 2) {
            throw new XmlPullParserException("Not START_TAG event");
        }
        parseNamespaces(xmlPullParser);
        setName(xmlPullParser.getNamespace(), xmlPullParser.getPrefix(), xmlPullParser.getName());
        parseAttributes(xmlPullParser);
        int next = xmlPullParser.next();
        XMLText xMLTextNewText = null;
        while (next != 3 && next != 1) {
            if (next == 2) {
                XMLElement xMLElementNewElement = newElement();
                add((XMLNode) xMLElementNewElement);
                xMLElementNewElement.parse(xmlPullParser);
            } else {
                if (XMLText.isTextEvent(next)) {
                    if (xMLTextNewText == null) {
                        xMLTextNewText = newText();
                    }
                    xMLTextNewText.parse(xmlPullParser);
                    if (!xMLTextNewText.isIndent()) {
                        add((XMLNode) xMLTextNewText);
                    }
                } else if (next == 9 && (xMLCommentNewComment = newComment()) != null) {
                    add((XMLNode) xMLCommentNewComment);
                    xMLCommentNewComment.parse(xmlPullParser);
                } else {
                    xmlPullParser.next();
                }
                next = xmlPullParser.getEventType();
            }
            xMLTextNewText = null;
            next = xmlPullParser.getEventType();
        }
        if (xmlPullParser.getEventType() == 3) {
            xmlPullParser.next();
        }
    }

    private void parseNamespaces(XmlPullParser xmlPullParser) throws XmlPullParserException {
        int namespaceCount = xmlPullParser.getNamespaceCount(getDepth());
        for (int i = 0; i < namespaceCount; i++) {
            addNamespace(xmlPullParser.getNamespaceUri(i), xmlPullParser.getNamespacePrefix(i));
        }
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            String attributeValue = xmlPullParser.getAttributeValue(i2);
            if (XMLNamespace.looksNamespace(attributeName, attributeValue)) {
                addNamespace(attributeValue, XMLUtil.splitName(attributeName));
            }
        }
    }

    public void parseAttributes(XmlPullParser xmlPullParser) {
        boolean feature = xmlPullParser.getFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES);
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if (!XMLNamespace.looksNamespace(attributeName, attributeValue)) {
                String attributeNamespace = xmlPullParser.getAttributeNamespace(i);
                String attributePrefix = xmlPullParser.getAttributePrefix(i);
                if (feature) {
                    attributeName = XMLUtil.splitName(attributeName);
                }
                addAttribute(attributeNamespace, attributePrefix, attributeName, attributeValue);
            }
        }
    }

    @Override // com.reandroid.xml.XMLNode
    void write(Appendable appendable, boolean z, boolean z2) throws IOException {
        appendable.append('<');
        appendable.append(getName());
        appendAttributes(appendable, z, z2);
        Iterator<XMLNode> it = iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            if (!z3) {
                appendable.append(">");
            }
            it.next().write(appendable, z, z2);
            z3 = true;
        }
        if (z3) {
            appendable.append("</");
            appendable.append(getName());
            appendable.append('>');
            return;
        }
        appendable.append("/>");
    }

    void appendAttributes(Appendable appendable, boolean z, boolean z2) throws IOException {
        char c = z ? StringUtil.SPACE : ';';
        Iterator<? extends XMLAttribute> attributes = getAttributes();
        while (attributes.hasNext()) {
            appendable.append(c);
            attributes.next().write(appendable, z, z2);
        }
    }

    @Override // com.reandroid.xml.XMLNode
    int appendDebugText(Appendable appendable, int i, int i2) throws IOException {
        if (i2 >= i) {
            return i2;
        }
        appendable.append('<');
        int i3 = i2 + 1;
        String name2 = getName(true);
        if (name2 == null) {
            name2 = "null";
        }
        appendable.append(name2);
        int length = i3 + name2.length();
        Iterator<? extends XMLAttribute> attributes = getAttributes();
        while (attributes.hasNext() && length < i) {
            appendable.append(StringUtil.SPACE);
            length = attributes.next().appendDebugText(appendable, i, length + 1);
        }
        Iterator<XMLNode> it = iterator();
        boolean z = false;
        while (it.hasNext() && length < i) {
            if (!z) {
                appendable.append("/>");
                length += 2;
            }
            length = it.next().appendDebugText(appendable, i, length);
            z = true;
        }
        if (z) {
            appendable.append("</");
            appendable.append(name2);
            int length2 = length + name2.length();
            appendable.append('>');
            return length2 + 3;
        }
        appendable.append("/>");
        return length + 2;
    }

    public static XMLElement parseElement(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        XMLElement xMLElement = new XMLElement();
        xMLElement.parse(xmlPullParser);
        return xMLElement;
    }
}
