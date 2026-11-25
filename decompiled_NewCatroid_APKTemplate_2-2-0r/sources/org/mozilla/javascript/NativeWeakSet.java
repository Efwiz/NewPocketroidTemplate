package org.mozilla.javascript;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.WeakHashMap;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes4.dex */
public class NativeWeakSet extends IdScriptableObject {
    private static final int Id_add = 2;
    private static final int Id_constructor = 1;
    private static final int Id_delete = 3;
    private static final int Id_has = 4;
    private static final Object MAP_TAG = "WeakSet";
    private static final int MAX_PROTOTYPE_ID = 5;
    private static final int SymbolId_toStringTag = 5;
    private static final long serialVersionUID = 2065753364224029534L;
    private boolean instanceOfWeakSet = false;
    private transient WeakHashMap<Scriptable, Boolean> map = new WeakHashMap<>();

    static void init(Scriptable scriptable, boolean z) {
        new NativeWeakSet().exportAsJSClass(5, scriptable, z);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "WeakSet";
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(MAP_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId != 1) {
            if (iMethodId == 2) {
                return realThis(scriptable2, idFunctionObject).js_add(objArr.length > 0 ? objArr[0] : Undefined.instance);
            }
            if (iMethodId == 3) {
                return realThis(scriptable2, idFunctionObject).js_delete(objArr.length > 0 ? objArr[0] : Undefined.instance);
            }
            if (iMethodId == 4) {
                return realThis(scriptable2, idFunctionObject).js_has(objArr.length > 0 ? objArr[0] : Undefined.instance);
            }
            throw new IllegalArgumentException("WeakMap.prototype has no method: " + idFunctionObject.getFunctionName());
        }
        if (scriptable2 == null) {
            NativeWeakSet nativeWeakSet = new NativeWeakSet();
            nativeWeakSet.instanceOfWeakSet = true;
            if (objArr.length > 0) {
                NativeSet.loadFromIterable(context, scriptable, nativeWeakSet, objArr[0]);
            }
            return nativeWeakSet;
        }
        throw ScriptRuntime.typeError1("msg.no.new", "WeakSet");
    }

    private Object js_add(Object obj) {
        if (!ScriptRuntime.isObject(obj)) {
            throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
        }
        this.map.put((Scriptable) obj, Boolean.TRUE);
        return this;
    }

    private Object js_delete(Object obj) {
        if (ScriptRuntime.isObject(obj)) {
            return Boolean.valueOf(this.map.remove(obj) != null);
        }
        return Boolean.FALSE;
    }

    private Object js_has(Object obj) {
        if (!ScriptRuntime.isObject(obj)) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(this.map.containsKey(obj));
    }

    private static NativeWeakSet realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable == null) {
            throw incompatibleCallError(idFunctionObject);
        }
        try {
            NativeWeakSet nativeWeakSet = (NativeWeakSet) scriptable;
            if (nativeWeakSet.instanceOfWeakSet) {
                return nativeWeakSet;
            }
            throw incompatibleCallError(idFunctionObject);
        } catch (ClassCastException unused) {
            throw incompatibleCallError(idFunctionObject);
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        String str;
        if (i == 5) {
            initPrototypeValue(5, SymbolKey.TO_STRING_TAG, getClassName(), 3);
            return;
        }
        int i2 = 1;
        if (i == 1) {
            i2 = 0;
            str = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
        } else if (i == 2) {
            str = ProductAction.ACTION_ADD;
        } else if (i == 3) {
            str = "delete";
        } else if (i == 4) {
            str = "has";
        } else {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        initPrototypeMethod(MAP_TAG, i, str, (String) null, i2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(Symbol symbol) {
        return SymbolKey.TO_STRING_TAG.equals(symbol) ? 5 : 0;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int length = str.length();
        int i = 1;
        if (length == 3) {
            char cCharAt = str.charAt(0);
            if (cCharAt == 'a') {
                if (str.charAt(2) == 'd' && str.charAt(1) == 'd') {
                    return 2;
                }
            } else if (cCharAt == 'h' && str.charAt(2) == 's' && str.charAt(1) == 'a') {
                return 4;
            }
        } else {
            if (length == 6) {
                str2 = "delete";
                i = 3;
            } else if (length == 11) {
                str2 = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
            }
            if (str2 != null || str2 == str || str2.equals(str)) {
                return i;
            }
            return 0;
        }
        str2 = null;
        i = 0;
        if (str2 != null) {
        }
        return i;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.map = new WeakHashMap<>();
    }
}
