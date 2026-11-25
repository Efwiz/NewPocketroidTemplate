package org.mozilla.javascript.xmlimpl;

import java.io.Serializable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Ref;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.xml.XMLLib;
import org.mozilla.javascript.xml.XMLObject;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes6.dex */
public final class XMLLibImpl extends XMLLib implements Serializable {
    private static final long serialVersionUID = 1;
    private Scriptable globalScope;
    private Namespace namespacePrototype;
    private XmlProcessor options = new XmlProcessor();
    private QName qnamePrototype;
    private XMLList xmlListPrototype;
    private XML xmlPrototype;

    public static Node toDomNode(Object obj) {
        if (obj instanceof XML) {
            return ((XML) obj).toDomNode();
        }
        throw new IllegalArgumentException("xmlObject is not an XML object in JavaScript.");
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        XMLLibImpl xMLLibImpl = new XMLLibImpl(scriptable);
        if (xMLLibImpl.bindToScope(scriptable) == xMLLibImpl) {
            xMLLibImpl.exportToScope(z);
        }
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setIgnoreComments(boolean z) {
        this.options.setIgnoreComments(z);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setIgnoreWhitespace(boolean z) {
        this.options.setIgnoreWhitespace(z);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setIgnoreProcessingInstructions(boolean z) {
        this.options.setIgnoreProcessingInstructions(z);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setPrettyPrinting(boolean z) {
        this.options.setPrettyPrinting(z);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setPrettyIndent(int i) {
        this.options.setPrettyIndent(i);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isIgnoreComments() {
        return this.options.isIgnoreComments();
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isIgnoreProcessingInstructions() {
        return this.options.isIgnoreProcessingInstructions();
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isIgnoreWhitespace() {
        return this.options.isIgnoreWhitespace();
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isPrettyPrinting() {
        return this.options.isPrettyPrinting();
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public int getPrettyIndent() {
        return this.options.getPrettyIndent();
    }

    private XMLLibImpl(Scriptable scriptable) {
        this.globalScope = scriptable;
    }

    @Deprecated
    QName qnamePrototype() {
        return this.qnamePrototype;
    }

    @Deprecated
    Scriptable globalScope() {
        return this.globalScope;
    }

    XmlProcessor getProcessor() {
        return this.options;
    }

    private void exportToScope(boolean z) {
        this.xmlPrototype = newXML(XmlNode.createText(this.options, ""));
        this.xmlListPrototype = newXMLList();
        this.namespacePrototype = Namespace.create(this.globalScope, null, XmlNode.Namespace.GLOBAL);
        this.qnamePrototype = QName.create(this, this.globalScope, null, XmlNode.QName.create(XmlNode.Namespace.create(""), ""));
        this.xmlPrototype.exportAsJSClass(z);
        this.xmlListPrototype.exportAsJSClass(z);
        this.namespacePrototype.exportAsJSClass(z);
        this.qnamePrototype.exportAsJSClass(z);
    }

    @Deprecated
    XMLName toAttributeName(Context context, Object obj) {
        String string;
        if (obj instanceof XMLName) {
            return (XMLName) obj;
        }
        if (obj instanceof QName) {
            return XMLName.create(((QName) obj).getDelegate(), true, false);
        }
        if ((obj instanceof Boolean) || (obj instanceof Number) || obj == Undefined.instance || obj == null) {
            throw badXMLName(obj);
        }
        if (obj instanceof String) {
            string = (String) obj;
        } else {
            string = ScriptRuntime.toString(obj);
        }
        if (string != null && string.equals("*")) {
            string = null;
        }
        return XMLName.create(XmlNode.QName.create(XmlNode.Namespace.create(""), string), true, false);
    }

    private static RuntimeException badXMLName(Object obj) {
        String str;
        if (obj instanceof Number) {
            str = "Can not construct XML name from number: ";
        } else if (obj instanceof Boolean) {
            str = "Can not construct XML name from boolean: ";
        } else if (obj == Undefined.instance || obj == null) {
            str = "Can not construct XML name from ";
        } else {
            throw new IllegalArgumentException(obj.toString());
        }
        return ScriptRuntime.typeError(str + ScriptRuntime.toString(obj));
    }

    XMLName toXMLNameFromString(Context context, String str) {
        return XMLName.create(getDefaultNamespaceURI(context), str);
    }

    XMLName toXMLName(Context context, Object obj) {
        if (obj instanceof XMLName) {
            return (XMLName) obj;
        }
        if (obj instanceof QName) {
            QName qName = (QName) obj;
            return XMLName.formProperty(qName.uri(), qName.localName());
        }
        if (obj instanceof String) {
            return toXMLNameFromString(context, (String) obj);
        }
        if ((obj instanceof Boolean) || (obj instanceof Number) || obj == Undefined.instance || obj == null) {
            throw badXMLName(obj);
        }
        return toXMLNameFromString(context, ScriptRuntime.toString(obj));
    }

    XMLName toXMLNameOrIndex(Context context, Object obj) {
        XMLName xMLNameFormProperty;
        if (obj instanceof XMLName) {
            return (XMLName) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            long jTestUint32String = ScriptRuntime.testUint32String(str);
            if (jTestUint32String >= 0) {
                ScriptRuntime.storeUint32Result(context, jTestUint32String);
                return null;
            }
            xMLNameFormProperty = toXMLNameFromString(context, str);
            return xMLNameFormProperty;
        }
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            long j = (long) dDoubleValue;
            if (j == dDoubleValue && 0 <= j && j <= 4294967295L) {
                ScriptRuntime.storeUint32Result(context, j);
            } else {
                throw badXMLName(obj);
            }
        } else {
            if (obj instanceof QName) {
                QName qName = (QName) obj;
                String strUri = qName.uri();
                if (strUri != null && strUri.length() == 0) {
                    long jTestUint32String2 = ScriptRuntime.testUint32String(strUri);
                    if (jTestUint32String2 >= 0) {
                        ScriptRuntime.storeUint32Result(context, jTestUint32String2);
                    }
                }
                xMLNameFormProperty = XMLName.formProperty(strUri, qName.localName());
                return xMLNameFormProperty;
            }
            if ((obj instanceof Boolean) || obj == Undefined.instance || obj == null) {
                throw badXMLName(obj);
            }
            String string = ScriptRuntime.toString(obj);
            long jTestUint32String3 = ScriptRuntime.testUint32String(string);
            if (jTestUint32String3 >= 0) {
                ScriptRuntime.storeUint32Result(context, jTestUint32String3);
            } else {
                return toXMLNameFromString(context, string);
            }
        }
        return null;
    }

    Object addXMLObjects(Context context, XMLObject xMLObject, XMLObject xMLObject2) {
        XMLList xMLListNewXMLList = newXMLList();
        if (xMLObject instanceof XMLList) {
            XMLList xMLList = (XMLList) xMLObject;
            if (xMLList.length() == 1) {
                xMLListNewXMLList.addToList(xMLList.item(0));
            } else {
                xMLListNewXMLList = newXMLListFrom(xMLObject);
            }
        } else {
            xMLListNewXMLList.addToList(xMLObject);
        }
        if (xMLObject2 instanceof XMLList) {
            XMLList xMLList2 = (XMLList) xMLObject2;
            for (int i = 0; i < xMLList2.length(); i++) {
                xMLListNewXMLList.addToList(xMLList2.item(i));
            }
        } else if (xMLObject2 instanceof XML) {
            xMLListNewXMLList.addToList(xMLObject2);
        }
        return xMLListNewXMLList;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x001b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:18:? A[LOOP:0: B:3:0x0001->B:18:?, LOOP_END, SYNTHETIC] */
    private Ref xmlPrimaryReference(Context context, XMLName xMLName, Scriptable scriptable) {
        XMLObjectImpl xMLObjectImpl;
        XMLObjectImpl xMLObjectImpl2 = null;
        while (true) {
            if (scriptable instanceof XMLWithScope) {
                xMLObjectImpl = (XMLObjectImpl) scriptable.getPrototype();
                if (xMLObjectImpl.hasXMLProperty(xMLName)) {
                    break;
                }
                if (xMLObjectImpl2 == null) {
                    xMLObjectImpl2 = xMLObjectImpl;
                }
                scriptable = scriptable.getParentScope();
                if (scriptable == null) {
                    xMLObjectImpl = xMLObjectImpl2;
                    break;
                }
            } else {
                scriptable = scriptable.getParentScope();
                if (scriptable == null) {
                    xMLObjectImpl = xMLObjectImpl2;
                    break;
                }
            }
        }
        if (xMLObjectImpl != null) {
            xMLName.initXMLObject(xMLObjectImpl);
        }
        return xMLName;
    }

    Namespace castToNamespace(Context context, Object obj) {
        return this.namespacePrototype.castToNamespace(obj);
    }

    private String getDefaultNamespaceURI(Context context) {
        return getDefaultNamespace(context).uri();
    }

    Namespace newNamespace(String str) {
        return this.namespacePrototype.newNamespace(str);
    }

    Namespace getDefaultNamespace(Context context) {
        if (context == null && (context = Context.getCurrentContext()) == null) {
            return this.namespacePrototype;
        }
        Object objSearchDefaultNamespace = ScriptRuntime.searchDefaultNamespace(context);
        if (objSearchDefaultNamespace == null) {
            return this.namespacePrototype;
        }
        if (objSearchDefaultNamespace instanceof Namespace) {
            return (Namespace) objSearchDefaultNamespace;
        }
        return this.namespacePrototype;
    }

    Namespace[] createNamespaces(XmlNode.Namespace[] namespaceArr) {
        Namespace[] namespaceArr2 = new Namespace[namespaceArr.length];
        for (int i = 0; i < namespaceArr.length; i++) {
            namespaceArr2[i] = this.namespacePrototype.newNamespace(namespaceArr[i].getPrefix(), namespaceArr[i].getUri());
        }
        return namespaceArr2;
    }

    QName constructQName(Context context, Object obj, Object obj2) {
        return this.qnamePrototype.constructQName(this, context, obj, obj2);
    }

    QName newQName(String str, String str2, String str3) {
        return this.qnamePrototype.newQName(this, str, str2, str3);
    }

    QName constructQName(Context context, Object obj) {
        return this.qnamePrototype.constructQName(this, context, obj);
    }

    QName castToQName(Context context, Object obj) {
        return this.qnamePrototype.castToQName(this, context, obj);
    }

    QName newQName(XmlNode.QName qName) {
        return QName.create(this, this.globalScope, this.qnamePrototype, qName);
    }

    XML newXML(XmlNode xmlNode) {
        return new XML(this, this.globalScope, this.xmlPrototype, xmlNode);
    }

    final XML newXMLFromJs(Object obj) {
        String string;
        if (obj == null || obj == Undefined.instance) {
            string = "";
        } else if (obj instanceof XMLObjectImpl) {
            string = ((XMLObjectImpl) obj).toXMLString();
        } else {
            string = ScriptRuntime.toString(obj);
        }
        if (string.trim().startsWith("<>")) {
            throw ScriptRuntime.typeError("Invalid use of XML object anonymous tags <></>.");
        }
        if (string.indexOf("<") == -1) {
            return newXML(XmlNode.createText(this.options, string));
        }
        return parse(string);
    }

    private XML parse(String str) {
        try {
            return newXML(XmlNode.createElement(this.options, getDefaultNamespaceURI(Context.getCurrentContext()), str));
        } catch (SAXException e) {
            throw ScriptRuntime.typeError("Cannot parse XML: " + e.getMessage());
        }
    }

    final XML ecmaToXml(Object obj) {
        if (obj == null || obj == Undefined.instance) {
            throw ScriptRuntime.typeError("Cannot convert " + obj + " to XML");
        }
        if (obj instanceof XML) {
            return (XML) obj;
        }
        if (obj instanceof XMLList) {
            XMLList xMLList = (XMLList) obj;
            if (xMLList.getXML() != null) {
                return xMLList.getXML();
            }
            throw ScriptRuntime.typeError("Cannot convert list of >1 element to XML");
        }
        if (obj instanceof Wrapper) {
            obj = ((Wrapper) obj).unwrap();
        }
        if (obj instanceof Node) {
            return newXML(XmlNode.createElementFromNode((Node) obj));
        }
        String string = ScriptRuntime.toString(obj);
        if (string.length() > 0 && string.charAt(0) == '<') {
            return parse(string);
        }
        return newXML(XmlNode.createText(this.options, string));
    }

    final XML newTextElementXML(XmlNode xmlNode, XmlNode.QName qName, String str) {
        return newXML(XmlNode.newElementWithText(this.options, xmlNode, qName, str));
    }

    XMLList newXMLList() {
        return new XMLList(this, this.globalScope, this.xmlListPrototype);
    }

    final XMLList newXMLListFrom(Object obj) {
        XMLList xMLListNewXMLList = newXMLList();
        if (obj == null || (obj instanceof Undefined)) {
            return xMLListNewXMLList;
        }
        if (obj instanceof XML) {
            xMLListNewXMLList.getNodeList().add((XML) obj);
            return xMLListNewXMLList;
        }
        if (obj instanceof XMLList) {
            xMLListNewXMLList.getNodeList().add(((XMLList) obj).getNodeList());
            return xMLListNewXMLList;
        }
        String strTrim = ScriptRuntime.toString(obj).trim();
        if (!strTrim.startsWith("<>")) {
            strTrim = "<>" + strTrim + "</>";
        }
        String str = "<fragment>" + strTrim.substring(2);
        if (!str.endsWith("</>")) {
            throw ScriptRuntime.typeError("XML with anonymous tag missing end anonymous tag");
        }
        XMLList xMLListChildren = newXMLFromJs(str.substring(0, str.length() - 3) + "</fragment>").children();
        for (int i = 0; i < xMLListChildren.getNodeList().length(); i++) {
            xMLListNewXMLList.getNodeList().add((XML) xMLListChildren.item(i).copy());
        }
        return xMLListNewXMLList;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0028  */
    XmlNode.QName toNodeQName(Context context, Object obj, Object obj2) {
        String string;
        XmlNode.Namespace delegate;
        if (obj2 instanceof QName) {
            string = ((QName) obj2).localName();
        } else {
            string = ScriptRuntime.toString(obj2);
        }
        if (obj == Undefined.instance) {
            if ("*".equals(string)) {
                delegate = null;
            } else {
                delegate = getDefaultNamespace(context).getDelegate();
            }
        } else if (obj == null) {
            delegate = null;
        } else if (obj instanceof Namespace) {
            delegate = ((Namespace) obj).getDelegate();
        } else {
            delegate = this.namespacePrototype.constructNamespace(obj).getDelegate();
        }
        if (string != null && string.equals("*")) {
            string = null;
        }
        return XmlNode.QName.create(delegate, string);
    }

    XmlNode.QName toNodeQName(Context context, String str, boolean z) {
        XmlNode.Namespace delegate = getDefaultNamespace(context).getDelegate();
        if (str != null && str.equals("*")) {
            return XmlNode.QName.create(null, null);
        }
        if (z) {
            return XmlNode.QName.create(XmlNode.Namespace.GLOBAL, str);
        }
        return XmlNode.QName.create(delegate, str);
    }

    XmlNode.QName toNodeQName(Context context, Object obj, boolean z) {
        String string;
        if (obj instanceof XMLName) {
            return ((XMLName) obj).toQname();
        }
        if (obj instanceof QName) {
            return ((QName) obj).getDelegate();
        }
        if ((obj instanceof Boolean) || (obj instanceof Number) || obj == Undefined.instance || obj == null) {
            throw badXMLName(obj);
        }
        if (obj instanceof String) {
            string = (String) obj;
        } else {
            string = ScriptRuntime.toString(obj);
        }
        return toNodeQName(context, string, z);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isXMLName(Context context, Object obj) {
        return XMLName.accept(obj);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public Object toDefaultXmlNamespace(Context context, Object obj) {
        return this.namespacePrototype.constructNamespace(obj);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public String escapeTextValue(Object obj) {
        return this.options.escapeTextValue(obj);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public String escapeAttributeValue(Object obj) {
        return this.options.escapeAttributeValue(obj);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public Ref nameRef(Context context, Object obj, Scriptable scriptable, int i) {
        if ((i & 2) == 0) {
            throw Kit.codeBug();
        }
        return xmlPrimaryReference(context, toAttributeName(context, obj), scriptable);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public Ref nameRef(Context context, Object obj, Object obj2, Scriptable scriptable, int i) {
        XMLName xMLNameCreate = XMLName.create(toNodeQName(context, obj, obj2), false, false);
        if ((i & 2) != 0 && !xMLNameCreate.isAttributeName()) {
            xMLNameCreate.setAttributeName();
        }
        return xmlPrimaryReference(context, xMLNameCreate, scriptable);
    }
}
