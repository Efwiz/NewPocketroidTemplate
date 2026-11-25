package com.reandroid.xml;

import com.reandroid.common.Namespace;
import com.reandroid.xml.base.Attribute;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class XMLAttribute extends XMLNode implements Attribute {
    private String mName;
    private XMLNamespace mNamespace;
    private String mValue;

    public XMLAttribute() {
    }

    public XMLAttribute(String str, String str2) {
        this();
        this.mName = str;
        this.mValue = str2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.xml.XMLNode
    public XMLAttribute newCopy(XMLNode xMLNode) {
        XMLAttribute xMLAttribute = new XMLAttribute();
        xMLAttribute.setParentNode(xMLNode);
        xMLAttribute.setName(getUri(), getPrefix(), getName(false));
        if (xMLNode instanceof XMLElement) {
            ((XMLElement) xMLNode).addAttribute(xMLAttribute);
        }
        return xMLAttribute;
    }

    @Override // com.reandroid.xml.XMLNode, com.reandroid.xml.base.Node, com.reandroid.xml.base.Attribute
    public XMLElement getParentNode() {
        return (XMLElement) super.getParentNode();
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
        return getName(false);
    }

    @Override // com.reandroid.xml.base.NamedNode
    public String getName(boolean z) {
        String prefix;
        String str = this.mName;
        return (!z || str == null || (prefix = getPrefix()) == null) ? str : prefix + ":" + str;
    }

    @Override // com.reandroid.xml.base.Attribute, com.reandroid.xml.base.NamedNode
    public XMLNamespace getNamespace() {
        return this.mNamespace;
    }

    @Override // com.reandroid.xml.base.Attribute, com.reandroid.xml.base.NamedNode
    public void setNamespace(Namespace namespace) {
        this.mNamespace = (XMLNamespace) namespace;
    }

    public void setNamespace(String str, String str2) {
        XMLElement parentNode = getParentNode();
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent element is null");
        }
        setNamespace(parentNode.getOrCreateXMLNamespace(str, str2));
    }

    @Override // com.reandroid.xml.base.NamedNode
    public String getPrefix() {
        XMLNamespace namespace = getNamespace();
        if (namespace != null) {
            return namespace.getPrefix();
        }
        String str = this.mName;
        int iIndexOf = str.indexOf(58);
        if (iIndexOf > 0) {
            return str.substring(0, iIndexOf);
        }
        return null;
    }

    @Override // com.reandroid.xml.base.Attribute
    public String getValueAsString() {
        return getValueAsString(false);
    }

    public String getValueAsString(boolean z) {
        String str = this.mValue;
        if (str == null) {
            str = "";
            this.mValue = "";
        }
        return z ? XMLUtil.escapeXmlChars(str) : str;
    }

    XMLAttribute set(String str, String str2) {
        this.mName = str;
        this.mValue = str2;
        return this;
    }

    @Override // com.reandroid.xml.base.NamedNode
    public void setName(String str) {
        setName(null, null, str);
    }

    public void setName(String str, String str2) {
        setName(str, null, str2);
    }

    public void setName(String str, String str2, String str3) {
        this.mName = XMLUtil.splitName(str3);
        if (XMLUtil.isEmpty(str2)) {
            str2 = XMLUtil.splitPrefix(str3);
        }
        XMLNamespace xMLNamespaceByPrefix = null;
        if (XMLUtil.isEmpty(str)) {
            str = null;
        }
        XMLElement parentNode = getParentNode();
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent element is null");
        }
        if (str != null && str2 != null) {
            xMLNamespaceByPrefix = parentNode.getOrCreateXMLNamespace(str, str2);
        } else if (str != null) {
            xMLNamespaceByPrefix = parentNode.getXMLNamespaceByUri(str);
            if (xMLNamespaceByPrefix == null) {
                throw new IllegalArgumentException("Namespace not found for uri: " + str);
            }
        } else if (str2 != null && (xMLNamespaceByPrefix = parentNode.getXMLNamespaceByPrefix(str2)) == null) {
            throw new IllegalArgumentException("Namespace not found for prefix: " + str2);
        }
        if (xMLNamespaceByPrefix != null) {
            setNamespace(xMLNamespaceByPrefix);
        }
        this.mName = str3;
    }

    public void setValue(String str) {
        this.mValue = str;
    }

    public void setPrefix(String str) {
        if (Objects.equals(str, getPrefix())) {
            return;
        }
        XMLElement parentNode = getParentNode();
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent element is null");
        }
        setNamespace(parentNode.getXMLNamespaceByPrefix(str));
    }

    @Override // com.reandroid.xml.base.XmlSerializable
    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.attribute(getUri(), getName(), getValueAsString(false));
    }

    @Override // com.reandroid.xml.XMLNode
    void write(Appendable appendable, boolean z, boolean z2) throws IOException {
        appendable.append(getName(true));
        appendable.append('=');
        if (z) {
            appendable.append('\"');
        }
        appendable.append(getValueAsString(z2));
        if (z) {
            appendable.append('\"');
        }
    }

    @Override // com.reandroid.xml.XMLNode
    int appendDebugText(Appendable appendable, int i, int i2) throws IOException {
        if (i2 >= i) {
            return i2;
        }
        String name2 = getName(true);
        if (name2 == null) {
            name2 = "null";
        }
        appendable.append(name2);
        int length = i2 + name2.length();
        appendable.append('=');
        appendable.append('\"');
        String strEscapeXmlChars = XMLUtil.escapeXmlChars(getValueAsString());
        String str = strEscapeXmlChars != null ? strEscapeXmlChars : "null";
        appendable.append(str);
        appendable.append('\"');
        return length + str.length() + 3;
    }

    public int hashCode() {
        String name2 = getName(false);
        if (name2 == null) {
            name2 = "";
        }
        return name2.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof XMLAttribute) {
            return Objects.equals(getName(false), ((XMLAttribute) obj).getName(false));
        }
        return false;
    }
}
