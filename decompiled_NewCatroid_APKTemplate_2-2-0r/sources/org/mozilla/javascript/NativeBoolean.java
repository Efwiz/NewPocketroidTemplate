package org.mozilla.javascript;

import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes4.dex */
final class NativeBoolean extends IdScriptableObject {
    private static final Object BOOLEAN_TAG = "Boolean";
    private static final int Id_constructor = 1;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 4;
    private static final int MAX_PROTOTYPE_ID = 4;
    private static final long serialVersionUID = -3716996899943880933L;
    private boolean booleanValue;

    static void init(Scriptable scriptable, boolean z) {
        new NativeBoolean(false).exportAsJSClass(4, scriptable, z);
    }

    NativeBoolean(boolean z) {
        this.booleanValue = z;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Boolean";
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        if (cls == ScriptRuntime.BooleanClass) {
            return ScriptRuntime.wrapBoolean(this.booleanValue);
        }
        return super.getDefaultValue(cls);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        int i2;
        String str;
        if (i != 1) {
            i2 = 0;
            if (i == 2) {
                str = "toString";
            } else if (i == 3) {
                str = "toSource";
            } else if (i == 4) {
                str = "valueOf";
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
        } else {
            i2 = 1;
            str = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
        }
        initPrototypeMethod(BOOLEAN_TAG, i, str, i2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(BOOLEAN_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == 1) {
            boolean z = false;
            if (objArr.length != 0) {
                Object obj = objArr[0];
                if (!(obj instanceof ScriptableObject) || !((ScriptableObject) obj).avoidObjectDetection()) {
                    z = ScriptRuntime.toBoolean(objArr[0]);
                }
            }
            if (scriptable2 == null) {
                return new NativeBoolean(z);
            }
            return ScriptRuntime.wrapBoolean(z);
        }
        if (!(scriptable2 instanceof NativeBoolean)) {
            throw incompatibleCallError(idFunctionObject);
        }
        boolean z2 = ((NativeBoolean) scriptable2).booleanValue;
        if (iMethodId == 2) {
            return z2 ? "true" : "false";
        }
        if (iMethodId == 3) {
            return z2 ? "(new Boolean(true))" : "(new Boolean(false))";
        }
        if (iMethodId == 4) {
            return ScriptRuntime.wrapBoolean(z2);
        }
        throw new IllegalArgumentException(String.valueOf(iMethodId));
    }

    /* JADX WARN: Code duplicated, block: B:16:0x002c  */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i;
        int length = str.length();
        if (length == 7) {
            str2 = "valueOf";
            i = 4;
        } else if (length == 8) {
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
}
