package org.mozilla.javascript.xmlimpl;

import java.util.ArrayList;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.xml.XMLObject;

/* JADX INFO: loaded from: classes6.dex */
class XMLList extends XMLObjectImpl implements Function {
    static final long serialVersionUID = -4543618751670781135L;
    private XmlNode.InternalList _annos;
    private XMLObjectImpl targetObject;
    private XmlNode.QName targetProperty;

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    Object valueOf() {
        return this;
    }

    XMLList(XMLLibImpl xMLLibImpl, Scriptable scriptable, XMLObject xMLObject) {
        super(xMLLibImpl, scriptable, xMLObject);
        this.targetObject = null;
        this.targetProperty = null;
        this._annos = new XmlNode.InternalList();
    }

    XmlNode.InternalList getNodeList() {
        return this._annos;
    }

    void setTargets(XMLObjectImpl xMLObjectImpl, XmlNode.QName qName) {
        this.targetObject = xMLObjectImpl;
        this.targetProperty = qName;
    }

    private XML getXmlFromAnnotation(int i) {
        return getXML(this._annos, i);
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XML getXML() {
        if (length() == 1) {
            return getXmlFromAnnotation(0);
        }
        return null;
    }

    private void internalRemoveFromList(int i) {
        this._annos.remove(i);
    }

    void replace(int i, XML xml) {
        if (i < length()) {
            XmlNode.InternalList internalList = new XmlNode.InternalList();
            internalList.add(this._annos, 0, i);
            internalList.add(xml);
            internalList.add(this._annos, i + 1, length());
            this._annos = internalList;
        }
    }

    private void insert(int i, XML xml) {
        if (i < length()) {
            XmlNode.InternalList internalList = new XmlNode.InternalList();
            internalList.add(this._annos, 0, i);
            internalList.add(xml);
            internalList.add(this._annos, i, length());
            this._annos = internalList;
        }
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "XMLList";
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object get(int i, Scriptable scriptable) {
        if (i >= 0 && i < length()) {
            return getXmlFromAnnotation(i);
        }
        return Scriptable.NOT_FOUND;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean hasXMLProperty(XMLName xMLName) {
        return getPropertyList(xMLName).length() > 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean has(int i, Scriptable scriptable) {
        return i >= 0 && i < length();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    void putXMLProperty(XMLName xMLName, Object obj) {
        XmlNode.QName qName;
        if (obj == null) {
            obj = "null";
        } else if (obj instanceof Undefined) {
            obj = "undefined";
        }
        if (length() > 1) {
            throw ScriptRuntime.typeError("Assignment to lists with more than one item is not supported");
        }
        if (length() == 0) {
            if (this.targetObject != null && (qName = this.targetProperty) != null && qName.getLocalName() != null && this.targetProperty.getLocalName().length() > 0) {
                addToList(newTextElementXML(null, this.targetProperty, null));
                if (xMLName.isAttributeName()) {
                    setAttribute(xMLName, obj);
                } else {
                    item(0).putXMLProperty(xMLName, obj);
                    replace(0, item(0));
                }
                this.targetObject.putXMLProperty(XMLName.formProperty(this.targetProperty.getNamespace().getUri(), this.targetProperty.getLocalName()), this);
                replace(0, this.targetObject.getXML().getLastXmlChild());
                return;
            }
            throw ScriptRuntime.typeError("Assignment to empty XMLList without targets not supported");
        }
        if (xMLName.isAttributeName()) {
            setAttribute(xMLName, obj);
        } else {
            item(0).putXMLProperty(xMLName, obj);
            replace(0, item(0));
        }
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    Object getXMLProperty(XMLName xMLName) {
        return getPropertyList(xMLName);
    }

    private void replaceNode(XML xml, XML xml2) {
        xml.replaceWith(xml2);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(int i, Scriptable scriptable, Object obj) {
        Object objNewXMLFromJs;
        Object objParent;
        XMLObjectImpl xMLObjectImpl;
        Object obj2 = Undefined.instance;
        if (obj == null) {
            obj = "null";
        } else if (obj instanceof Undefined) {
            obj = "undefined";
        }
        if (obj instanceof XMLObject) {
            objNewXMLFromJs = (XMLObject) obj;
        } else if (this.targetProperty == null) {
            objNewXMLFromJs = newXMLFromJs(obj.toString());
        } else {
            XMLObjectImpl xMLObjectImplItem = item(i);
            if (xMLObjectImplItem == null) {
                XML xmlItem = item(0);
                if (xmlItem == null) {
                    xMLObjectImplItem = newTextElementXML(null, this.targetProperty, null);
                } else {
                    xMLObjectImplItem = xmlItem.copy();
                }
            }
            ((XML) xMLObjectImplItem).setChildren(obj);
            objNewXMLFromJs = xMLObjectImplItem;
        }
        if (i < length()) {
            objParent = item(i).parent();
        } else {
            objParent = (length() != 0 || (xMLObjectImpl = this.targetObject) == null) ? parent() : xMLObjectImpl.getXML();
        }
        if (objParent instanceof XML) {
            XML xml = (XML) objParent;
            if (i < length()) {
                XML xmlFromAnnotation = getXmlFromAnnotation(i);
                if (objNewXMLFromJs instanceof XML) {
                    replaceNode(xmlFromAnnotation, (XML) objNewXMLFromJs);
                    replace(i, xmlFromAnnotation);
                    return;
                }
                if (objNewXMLFromJs instanceof XMLList) {
                    XMLList xMLList = (XMLList) objNewXMLFromJs;
                    if (xMLList.length() > 0) {
                        int iChildIndex = xmlFromAnnotation.childIndex();
                        replaceNode(xmlFromAnnotation, xMLList.item(0));
                        replace(i, xMLList.item(0));
                        for (int i2 = 1; i2 < xMLList.length(); i2++) {
                            xml.insertChildAfter(xml.getXmlChild(iChildIndex), xMLList.item(i2));
                            iChildIndex++;
                            insert(i + i2, xMLList.item(i2));
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            xml.appendChild(objNewXMLFromJs);
            addToList(xml.getLastXmlChild());
            return;
        }
        if (i < length()) {
            XML xml2 = getXML(this._annos, i);
            if (objNewXMLFromJs instanceof XML) {
                replaceNode(xml2, (XML) objNewXMLFromJs);
                replace(i, xml2);
                return;
            } else {
                if (objNewXMLFromJs instanceof XMLList) {
                    XMLList xMLList2 = (XMLList) objNewXMLFromJs;
                    if (xMLList2.length() > 0) {
                        replaceNode(xml2, xMLList2.item(0));
                        replace(i, xMLList2.item(0));
                        for (int i3 = 1; i3 < xMLList2.length(); i3++) {
                            insert(i + i3, xMLList2.item(i3));
                        }
                        return;
                    }
                    return;
                }
                return;
            }
        }
        addToList(objNewXMLFromJs);
    }

    private XML getXML(XmlNode.InternalList internalList, int i) {
        if (i < 0 || i >= length()) {
            return null;
        }
        return xmlFromNode(internalList.item(i));
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    void deleteXMLProperty(XMLName xMLName) {
        for (int i = 0; i < length(); i++) {
            XML xmlFromAnnotation = getXmlFromAnnotation(i);
            if (xmlFromAnnotation.isElement()) {
                xmlFromAnnotation.deleteXMLProperty(xMLName);
            }
        }
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void delete(int i) {
        if (i < 0 || i >= length()) {
            return;
        }
        getXmlFromAnnotation(i).remove();
        internalRemoveFromList(i);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object[] getIds() {
        if (isPrototype()) {
            return new Object[0];
        }
        int length = length();
        Object[] objArr = new Object[length];
        for (int i = 0; i < length; i++) {
            objArr[i] = Integer.valueOf(i);
        }
        return objArr;
    }

    public Object[] getIdsForDebug() {
        return getIds();
    }

    void remove() {
        for (int length = length() - 1; length >= 0; length--) {
            XML xmlFromAnnotation = getXmlFromAnnotation(length);
            if (xmlFromAnnotation != null) {
                xmlFromAnnotation.remove();
                internalRemoveFromList(length);
            }
        }
    }

    XML item(int i) {
        return this._annos != null ? getXmlFromAnnotation(i) : createEmptyXML();
    }

    private void setAttribute(XMLName xMLName, Object obj) {
        for (int i = 0; i < length(); i++) {
            getXmlFromAnnotation(i).setAttribute(xMLName, obj);
        }
    }

    void addToList(Object obj) {
        this._annos.addToList(obj);
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList child(int i) {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i2 = 0; i2 < length(); i2++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i2).child(i));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList child(XMLName xMLName) {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i = 0; i < length(); i++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i).child(xMLName));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    void addMatches(XMLList xMLList, XMLName xMLName) {
        for (int i = 0; i < length(); i++) {
            getXmlFromAnnotation(i).addMatches(xMLList, xMLName);
        }
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList children() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < length(); i++) {
            XML xmlFromAnnotation = getXmlFromAnnotation(i);
            if (xmlFromAnnotation != null) {
                XMLList xMLListChildren = xmlFromAnnotation.children();
                int length = xMLListChildren.length();
                for (int i2 = 0; i2 < length; i2++) {
                    arrayList.add(xMLListChildren.item(i2));
                }
            }
        }
        XMLList xMLListNewXMLList = newXMLList();
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            xMLListNewXMLList.addToList(arrayList.get(i3));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList comments() {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i = 0; i < length(); i++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i).comments());
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList elements(XMLName xMLName) {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i = 0; i < length(); i++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i).elements(xMLName));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean contains(Object obj) {
        for (int i = 0; i < length(); i++) {
            if (getXmlFromAnnotation(i).equivalentXml(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLObjectImpl copy() {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i = 0; i < length(); i++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i).copy());
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean hasOwnProperty(XMLName xMLName) {
        if (isPrototype()) {
            return findPrototypeId(xMLName.localName()) != 0;
        }
        return getPropertyList(xMLName).length() > 0;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean hasComplexContent() {
        int length = length();
        if (length == 0) {
            return false;
        }
        if (length == 1) {
            return getXmlFromAnnotation(0).hasComplexContent();
        }
        for (int i = 0; i < length; i++) {
            if (getXmlFromAnnotation(i).isElement()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean hasSimpleContent() {
        if (length() == 0) {
            return true;
        }
        if (length() == 1) {
            return getXmlFromAnnotation(0).hasSimpleContent();
        }
        for (int i = 0; i < length(); i++) {
            if (getXmlFromAnnotation(i).isElement()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    int length() {
        XmlNode.InternalList internalList = this._annos;
        if (internalList != null) {
            return internalList.length();
        }
        return 0;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    void normalize() {
        for (int i = 0; i < length(); i++) {
            getXmlFromAnnotation(i).normalize();
        }
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    Object parent() {
        if (length() == 0) {
            return Undefined.instance;
        }
        XML xml = null;
        for (int i = 0; i < length(); i++) {
            Object objParent = getXmlFromAnnotation(i).parent();
            if (!(objParent instanceof XML)) {
                return Undefined.instance;
            }
            XML xml2 = (XML) objParent;
            if (i == 0) {
                xml = xml2;
            } else if (!xml.is(xml2)) {
                return Undefined.instance;
            }
        }
        return xml;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList processingInstructions(XMLName xMLName) {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i = 0; i < length(); i++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i).processingInstructions(xMLName));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean propertyIsEnumerable(Object obj) {
        long jTestUint32String;
        if (obj instanceof Integer) {
            jTestUint32String = ((Integer) obj).intValue();
        } else if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            long j = (long) dDoubleValue;
            if (j != dDoubleValue) {
                return false;
            }
            if (j == 0 && 1.0d / dDoubleValue < 0.0d) {
                return false;
            }
            jTestUint32String = j;
        } else {
            jTestUint32String = ScriptRuntime.testUint32String(ScriptRuntime.toString(obj));
        }
        return 0 <= jTestUint32String && jTestUint32String < ((long) length());
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList text() {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i = 0; i < length(); i++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i).text());
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    public String toString() {
        if (hasSimpleContent()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length(); i++) {
                XML xmlFromAnnotation = getXmlFromAnnotation(i);
                if (!xmlFromAnnotation.isComment() && !xmlFromAnnotation.isProcessingInstruction()) {
                    sb.append(xmlFromAnnotation.toString());
                }
            }
            return sb.toString();
        }
        return toXMLString();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    String toSource(int i) {
        return toXMLString();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    String toXMLString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length(); i++) {
            if (getProcessor().isPrettyPrinting() && i != 0) {
                sb.append('\n');
            }
            sb.append(getXmlFromAnnotation(i).toXMLString());
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean equivalentXml(Object obj) {
        if ((obj instanceof Undefined) && length() == 0) {
            return true;
        }
        if (length() == 1) {
            return getXmlFromAnnotation(0).equivalentXml(obj);
        }
        if (obj instanceof XMLList) {
            XMLList xMLList = (XMLList) obj;
            if (xMLList.length() == length()) {
                for (int i = 0; i < length(); i++) {
                    if (getXmlFromAnnotation(i).equivalentXml(xMLList.getXmlFromAnnotation(i))) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    private XMLList getPropertyList(XMLName xMLName) {
        XMLList xMLListNewXMLList = newXMLList();
        xMLListNewXMLList.setTargets(this, (xMLName.isDescendants() || xMLName.isAttributeName()) ? null : xMLName.toQname());
        for (int i = 0; i < length(); i++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i).getPropertyList(xMLName));
        }
        return xMLListNewXMLList;
    }

    private Object applyOrCall(boolean z, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        String str = z ? "apply" : "call";
        if (!(scriptable2 instanceof XMLList) || ((XMLList) scriptable2).targetProperty == null) {
            throw ScriptRuntime.typeError1("msg.isnt.function", str);
        }
        return ScriptRuntime.applyOrCall(z, context, scriptable, scriptable2, objArr);
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    protected Object jsConstructor(Context context, boolean z, Object[] objArr) {
        if (objArr.length == 0) {
            return newXMLList();
        }
        Object obj = objArr[0];
        return (z || !(obj instanceof XMLList)) ? newXMLListFrom(obj) : obj;
    }

    @Override // org.mozilla.javascript.xml.XMLObject
    public Scriptable getExtraMethodSource(Context context) {
        if (length() == 1) {
            return getXmlFromAnnotation(0);
        }
        return null;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:15:0x0022
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:140)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:590)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:82)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    @Override // org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public java.lang.Object call(org.mozilla.javascript.Context r9, org.mozilla.javascript.Scriptable r10, org.mozilla.javascript.Scriptable r11, java.lang.Object[] r12) {
        /*
            r8 = this;
            org.mozilla.javascript.xmlimpl.XmlNode$QName r0 = r8.targetProperty
            if (r0 == 0) goto L5f
            java.lang.String r0 = r0.getLocalName()
            java.lang.String r1 = "apply"
            boolean r3 = r0.equals(r1)
            if (r3 != 0) goto L55
            java.lang.String r1 = "call"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L19
            goto L55
        L19:
            boolean r1 = r11 instanceof org.mozilla.javascript.xml.XMLObject
            if (r1 == 0) goto L4e
            r1 = 0
        L1e:
            r2 = r1
        L1f:
            r1 = r11
        L20:
            boolean r3 = r11 instanceof org.mozilla.javascript.xml.XMLObject
            if (r3 == 0) goto L3e
            org.mozilla.javascript.xml.XMLObject r11 = (org.mozilla.javascript.xml.XMLObject) r11
            java.lang.Object r2 = r11.getFunctionProperty(r9, r0)
            java.lang.Object r3 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r2 == r3) goto L2f
            goto L3e
        L2f:
            org.mozilla.javascript.Scriptable r11 = r11.getExtraMethodSource(r9)
            if (r11 == 0) goto L20
            boolean r1 = r11 instanceof org.mozilla.javascript.xml.XMLObject
            if (r1 != 0) goto L1f
            java.lang.Object r1 = org.mozilla.javascript.ScriptableObject.getProperty(r11, r0)
            goto L1e
        L3e:
            boolean r11 = r2 instanceof org.mozilla.javascript.Callable
            if (r11 == 0) goto L49
            org.mozilla.javascript.Callable r2 = (org.mozilla.javascript.Callable) r2
            java.lang.Object r9 = r2.call(r9, r10, r1, r12)
            return r9
        L49:
            java.lang.RuntimeException r9 = org.mozilla.javascript.ScriptRuntime.notFunctionError(r1, r2, r0)
            throw r9
        L4e:
            java.lang.String r9 = "msg.incompat.call"
            org.mozilla.javascript.EcmaError r9 = org.mozilla.javascript.ScriptRuntime.typeError1(r9, r0)
            throw r9
        L55:
            r2 = r8
            r4 = r9
            r5 = r10
            r6 = r11
            r7 = r12
            java.lang.Object r9 = r2.applyOrCall(r3, r4, r5, r6, r7)
            return r9
        L5f:
            java.lang.RuntimeException r9 = org.mozilla.javascript.ScriptRuntime.notFunctionError(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.xmlimpl.XMLList.call(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    @Override // org.mozilla.javascript.Function
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        throw ScriptRuntime.typeError1("msg.not.ctor", "XMLList");
    }
}
