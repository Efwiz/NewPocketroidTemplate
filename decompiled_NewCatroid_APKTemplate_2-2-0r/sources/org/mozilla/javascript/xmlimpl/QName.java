package org.mozilla.javascript.xmlimpl;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/* JADX INFO: loaded from: classes6.dex */
final class QName extends IdScriptableObject {
    private static final int Id_constructor = 1;
    private static final int Id_localName = 1;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int Id_uri = 2;
    private static final int MAX_INSTANCE_ID = 2;
    private static final int MAX_PROTOTYPE_ID = 3;
    private static final Object QNAME_TAG = SchemaSymbols.ATTVAL_QNAME;
    static final long serialVersionUID = 416745167693026750L;
    private XmlNode.QName delegate;
    private XMLLibImpl lib;
    private QName prototype;

    private QName() {
    }

    static QName create(XMLLibImpl xMLLibImpl, Scriptable scriptable, QName qName, XmlNode.QName qName2) {
        QName qName3 = new QName();
        qName3.lib = xMLLibImpl;
        qName3.setParentScope(scriptable);
        qName3.prototype = qName;
        qName3.setPrototype(qName);
        qName3.delegate = qName2;
        return qName3;
    }

    void exportAsJSClass(boolean z) {
        exportAsJSClass(3, getParentScope(), z);
    }

    public String toString() {
        if (this.delegate.getNamespace() == null) {
            return "*::" + localName();
        }
        if (this.delegate.getNamespace().isGlobal()) {
            return localName();
        }
        return uri() + "::" + localName();
    }

    public String localName() {
        return this.delegate.getLocalName() == null ? "*" : this.delegate.getLocalName();
    }

    String prefix() {
        if (this.delegate.getNamespace() == null) {
            return null;
        }
        return this.delegate.getNamespace().getPrefix();
    }

    String uri() {
        if (this.delegate.getNamespace() == null) {
            return null;
        }
        return this.delegate.getNamespace().getUri();
    }

    @Deprecated
    final XmlNode.QName toNodeQname() {
        return this.delegate;
    }

    final XmlNode.QName getDelegate() {
        return this.delegate;
    }

    public boolean equals(Object obj) {
        if (obj instanceof QName) {
            return equals((QName) obj);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override // org.mozilla.javascript.ScriptableObject
    protected Object equivalentValues(Object obj) {
        if (obj instanceof QName) {
            return equals((QName) obj) ? Boolean.TRUE : Boolean.FALSE;
        }
        return Scriptable.NOT_FOUND;
    }

    private boolean equals(QName qName) {
        return this.delegate.equals(qName.delegate);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return SchemaSymbols.ATTVAL_QNAME;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        return toString();
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return super.getMaxInstanceId() + 2;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        String str2;
        int i;
        int length = str.length();
        if (length == 3) {
            str2 = "uri";
            i = 2;
        } else if (length == 9) {
            str2 = "localName";
            i = 1;
        } else {
            str2 = null;
            i = 0;
        }
        int i2 = (str2 == null || str2 == str || str2.equals(str)) ? i : 0;
        if (i2 == 0) {
            return super.findInstanceIdInfo(str);
        }
        if (i2 != 1 && i2 != 2) {
            throw new IllegalStateException();
        }
        return instanceIdInfo(5, super.getMaxInstanceId() + i2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i) {
        int maxInstanceId = i - super.getMaxInstanceId();
        if (maxInstanceId == 1) {
            return "localName";
        }
        if (maxInstanceId == 2) {
            return "uri";
        }
        return super.getInstanceIdName(i);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i) {
        int maxInstanceId = i - super.getMaxInstanceId();
        if (maxInstanceId == 1) {
            return localName();
        }
        if (maxInstanceId == 2) {
            return uri();
        }
        return super.getInstanceIdValue(i);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0025  */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i;
        int length = str.length();
        if (length == 8) {
            i = 3;
            char cCharAt = str.charAt(3);
            if (cCharAt == 'o') {
                str2 = "toSource";
            } else if (cCharAt == 't') {
                str2 = "toString";
                i = 2;
            } else {
                str2 = null;
                i = 0;
            }
        } else if (length == 11) {
            str2 = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
            i = 1;
        } else {
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        String str;
        int i2;
        if (i != 1) {
            i2 = 0;
            if (i == 2) {
                str = "toString";
            } else if (i == 3) {
                str = "toSource";
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
        } else {
            str = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
            i2 = 2;
        }
        initPrototypeMethod(QNAME_TAG, i, str, i2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(QNAME_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == 1) {
            return jsConstructor(context, scriptable2 == null, objArr);
        }
        if (iMethodId == 2) {
            return realThis(scriptable2, idFunctionObject).toString();
        }
        if (iMethodId == 3) {
            return realThis(scriptable2, idFunctionObject).js_toSource();
        }
        throw new IllegalArgumentException(String.valueOf(iMethodId));
    }

    private QName realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (!(scriptable instanceof QName)) {
            throw incompatibleCallError(idFunctionObject);
        }
        return (QName) scriptable;
    }

    QName newQName(XMLLibImpl xMLLibImpl, String str, String str2, String str3) {
        XmlNode.Namespace namespaceCreate;
        QName qName = this.prototype;
        if (qName == null) {
            qName = this;
        }
        if (str3 != null) {
            namespaceCreate = XmlNode.Namespace.create(str3, str);
        } else {
            namespaceCreate = str != null ? XmlNode.Namespace.create(str) : null;
        }
        if (str2 != null && str2.equals("*")) {
            str2 = null;
        }
        return create(xMLLibImpl, getParentScope(), qName, XmlNode.QName.create(namespaceCreate, str2));
    }

    QName constructQName(XMLLibImpl xMLLibImpl, Context context, Object obj, Object obj2) {
        String string;
        Namespace namespaceNewNamespace;
        String strPrefix;
        if (obj2 instanceof QName) {
            if (obj == Undefined.instance) {
                return (QName) obj2;
            }
            ((QName) obj2).localName();
        }
        if (obj2 == Undefined.instance) {
            string = "";
        } else {
            string = ScriptRuntime.toString(obj2);
        }
        String strUri = null;
        if (obj == Undefined.instance) {
            obj = "*".equals(string) ? null : xMLLibImpl.getDefaultNamespace(context);
        }
        if (obj == null) {
            namespaceNewNamespace = null;
        } else if (obj instanceof Namespace) {
            namespaceNewNamespace = (Namespace) obj;
        } else {
            namespaceNewNamespace = xMLLibImpl.newNamespace(ScriptRuntime.toString(obj));
        }
        if (obj == null) {
            strPrefix = null;
        } else {
            strUri = namespaceNewNamespace.uri();
            strPrefix = namespaceNewNamespace.prefix();
        }
        return newQName(xMLLibImpl, strUri, string, strPrefix);
    }

    QName constructQName(XMLLibImpl xMLLibImpl, Context context, Object obj) {
        return constructQName(xMLLibImpl, context, Undefined.instance, obj);
    }

    QName castToQName(XMLLibImpl xMLLibImpl, Context context, Object obj) {
        if (obj instanceof QName) {
            return (QName) obj;
        }
        return constructQName(xMLLibImpl, context, obj);
    }

    private Object jsConstructor(Context context, boolean z, Object[] objArr) {
        if (!z && objArr.length == 1) {
            return castToQName(this.lib, context, objArr[0]);
        }
        if (objArr.length == 0) {
            return constructQName(this.lib, context, Undefined.instance);
        }
        if (objArr.length == 1) {
            return constructQName(this.lib, context, objArr[0]);
        }
        return constructQName(this.lib, context, objArr[0], objArr[1]);
    }

    private String js_toSource() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        toSourceImpl(uri(), localName(), prefix(), sb);
        sb.append(')');
        return sb.toString();
    }

    private static void toSourceImpl(String str, String str2, String str3, StringBuilder sb) {
        sb.append("new QName(");
        if (str == null && str3 == null) {
            if (!"*".equals(str2)) {
                sb.append("null, ");
            }
        } else if (str != null) {
            Namespace.toSourceImpl(str3, str, sb);
            sb.append(", ");
        }
        sb.append('\'');
        sb.append(ScriptRuntime.escapeString(str2, '\''));
        sb.append("')");
    }
}
