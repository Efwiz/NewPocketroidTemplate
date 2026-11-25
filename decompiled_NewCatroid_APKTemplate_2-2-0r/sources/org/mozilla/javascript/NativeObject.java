package org.mozilla.javascript;

import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes4.dex */
public class NativeObject extends IdScriptableObject implements Map {
    private static final int ConstructorId_assign = -15;
    private static final int ConstructorId_create = -9;
    private static final int ConstructorId_defineProperties = -8;
    private static final int ConstructorId_defineProperty = -5;
    private static final int ConstructorId_freeze = -13;
    private static final int ConstructorId_getOwnPropertyDescriptor = -4;
    private static final int ConstructorId_getOwnPropertyNames = -3;
    private static final int ConstructorId_getOwnPropertySymbols = -14;
    private static final int ConstructorId_getPrototypeOf = -1;
    private static final int ConstructorId_is = -16;
    private static final int ConstructorId_isExtensible = -6;
    private static final int ConstructorId_isFrozen = -11;
    private static final int ConstructorId_isSealed = -10;
    private static final int ConstructorId_keys = -2;
    private static final int ConstructorId_preventExtensions = -7;
    private static final int ConstructorId_seal = -12;
    private static final int ConstructorId_setPrototypeOf = -17;
    private static final int Id___defineGetter__ = 9;
    private static final int Id___defineSetter__ = 10;
    private static final int Id___lookupGetter__ = 11;
    private static final int Id___lookupSetter__ = 12;
    private static final int Id_constructor = 1;
    private static final int Id_hasOwnProperty = 5;
    private static final int Id_isPrototypeOf = 7;
    private static final int Id_propertyIsEnumerable = 6;
    private static final int Id_toLocaleString = 3;
    private static final int Id_toSource = 8;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 4;
    private static final int MAX_PROTOTYPE_ID = 12;
    private static final Object OBJECT_TAG = CommonClassNames.JAVA_LANG_OBJECT_SHORT;
    private static final long serialVersionUID = -6345305608474346996L;

    static void init(Scriptable scriptable, boolean z) {
        new NativeObject().exportAsJSClass(12, scriptable, z);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CommonClassNames.JAVA_LANG_OBJECT_SHORT;
    }

    public String toString() {
        return ScriptRuntime.defaultObjectToString(this);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        Object obj = OBJECT_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -1, "getPrototypeOf", 1);
        if (Context.getCurrentContext().version >= 200) {
            addIdFunctionProperty(idFunctionObject, obj, ConstructorId_setPrototypeOf, "setPrototypeOf", 2);
        }
        addIdFunctionProperty(idFunctionObject, obj, -2, UserMetadata.KEYDATA_FILENAME, 1);
        addIdFunctionProperty(idFunctionObject, obj, -3, "getOwnPropertyNames", 1);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_getOwnPropertySymbols, "getOwnPropertySymbols", 1);
        addIdFunctionProperty(idFunctionObject, obj, -4, "getOwnPropertyDescriptor", 2);
        addIdFunctionProperty(idFunctionObject, obj, -5, "defineProperty", 3);
        addIdFunctionProperty(idFunctionObject, obj, -6, "isExtensible", 1);
        addIdFunctionProperty(idFunctionObject, obj, -7, "preventExtensions", 1);
        addIdFunctionProperty(idFunctionObject, obj, -8, "defineProperties", 2);
        addIdFunctionProperty(idFunctionObject, obj, -9, CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, 2);
        addIdFunctionProperty(idFunctionObject, obj, -10, "isSealed", 1);
        addIdFunctionProperty(idFunctionObject, obj, -11, "isFrozen", 1);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_seal, "seal", 1);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_freeze, "freeze", 1);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_assign, "assign", 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_is, LanguageCodeUtil.IS, 2);
        super.fillConstructorProperties(idFunctionObject);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        String str;
        String str2;
        int i2 = 1;
        switch (i) {
            case 1:
                str = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 2:
                str = "toString";
                i2 = 0;
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 3:
                str = "toLocaleString";
                i2 = 0;
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 4:
                str = "valueOf";
                i2 = 0;
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 5:
                str = "hasOwnProperty";
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 6:
                str = "propertyIsEnumerable";
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 7:
                str = "isPrototypeOf";
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 8:
                str = "toSource";
                i2 = 0;
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 9:
                str2 = "__defineGetter__";
                i2 = 2;
                str = str2;
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 10:
                str2 = "__defineSetter__";
                i2 = 2;
                str = str2;
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 11:
                str = "__lookupGetter__";
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            case 12:
                str = "__lookupSetter__";
                initPrototypeMethod(OBJECT_TAG, i, str, i2);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i));
        }
    }

    /* JADX WARN: Code duplicated, block: B:133:0x01b0 A[PHI: r0
      0x01b0: PHI (r0v184 boolean) = (r0v179 boolean), (r0v179 boolean), (r0v186 boolean), (r0v186 boolean) binds: [B:126:0x019c, B:128:0x01a0, B:119:0x0182, B:121:0x0186] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x016e, code lost:
    
        if ((((org.mozilla.javascript.ScriptableObject) r20).getAttributes(r0) & 2) == 0) goto L113;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v38, types: [java.lang.Object, org.mozilla.javascript.ScriptableObject] */
    /* JADX WARN: Type inference failed for: r0v43, types: [java.lang.Object, org.mozilla.javascript.ScriptableObject] */
    /* JADX WARN: Type inference failed for: r16v0, types: [org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.NativeObject] */
    /* JADX WARN: Type inference failed for: r2v16, types: [org.mozilla.javascript.ScriptRuntime$StringIdOrIndex] */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19, types: [org.mozilla.javascript.ScriptRuntime$StringIdOrIndex] */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v43 */
    /* JADX WARN: Type inference failed for: r2v45 */
    /* JADX WARN: Type inference failed for: r2v46 */
    /* JADX WARN: Type inference failed for: r7v6, types: [org.mozilla.javascript.Scriptable, org.mozilla.javascript.ScriptableObject] */
    /* JADX WARN: Type inference failed for: r7v7, types: [org.mozilla.javascript.Scriptable, org.mozilla.javascript.ScriptableObject] */
    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object execIdCall(org.mozilla.javascript.IdFunctionObject r17, org.mozilla.javascript.Context r18, org.mozilla.javascript.Scriptable r19, org.mozilla.javascript.Scriptable r20, java.lang.Object[] r21) {
        /*
            Method dump skipped, instruction units count: 1672
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeObject.execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    private static Scriptable getCompatibleObject(Context context, Scriptable scriptable, Object obj) {
        if (context.getLanguageVersion() >= 200) {
            return ensureScriptable(ScriptRuntime.toObject(context, scriptable, obj));
        }
        return ensureScriptable(obj);
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        if (obj instanceof String) {
            return has((String) obj, this);
        }
        if (obj instanceof Number) {
            return has(((Number) obj).intValue(), this);
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        for (Object obj2 : values()) {
            if (obj == obj2) {
                return true;
            }
            if (obj != null && obj.equals(obj2)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public Object remove(Object obj) {
        Object obj2 = get(obj);
        if (obj instanceof String) {
            delete((String) obj);
        } else if (obj instanceof Number) {
            delete(((Number) obj).intValue());
        }
        return obj2;
    }

    @Override // java.util.Map
    public Set<Object> keySet() {
        return new KeySet();
    }

    @Override // java.util.Map
    public Collection<Object> values() {
        return new ValueCollection();
    }

    @Override // java.util.Map
    public Set<Map.Entry<Object, Object>> entrySet() {
        return new EntrySet();
    }

    @Override // java.util.Map
    public Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException();
    }

    class EntrySet extends AbstractSet<Map.Entry<Object, Object>> {
        EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<Object, Object>> iterator() {
            return new Iterator<Map.Entry<Object, Object>>() { // from class: org.mozilla.javascript.NativeObject.EntrySet.1
                Object[] ids;
                Object key = null;
                int index = 0;

                {
                    this.ids = NativeObject.this.getIds();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                @Override // java.util.Iterator
                public Map.Entry<Object, Object> next() {
                    Object[] objArr = this.ids;
                    int i = this.index;
                    this.index = i + 1;
                    final Object obj = objArr[i];
                    this.key = obj;
                    final Object obj2 = NativeObject.this.get(this.key);
                    return new Map.Entry<Object, Object>() { // from class: org.mozilla.javascript.NativeObject.EntrySet.1.1
                        @Override // java.util.Map.Entry
                        public Object getKey() {
                            return obj;
                        }

                        @Override // java.util.Map.Entry
                        public Object getValue() {
                            return obj2;
                        }

                        @Override // java.util.Map.Entry
                        public Object setValue(Object obj3) {
                            throw new UnsupportedOperationException();
                        }

                        @Override // java.util.Map.Entry
                        public boolean equals(Object obj3) {
                            if (!(obj3 instanceof Map.Entry)) {
                                return false;
                            }
                            Map.Entry entry = (Map.Entry) obj3;
                            Object obj4 = obj;
                            if (obj4 == null) {
                                if (entry.getKey() != null) {
                                    return false;
                                }
                            } else if (!obj4.equals(entry.getKey())) {
                                return false;
                            }
                            Object obj5 = obj2;
                            if (obj5 == null) {
                                if (entry.getValue() != null) {
                                    return false;
                                }
                            } else if (!obj5.equals(entry.getValue())) {
                                return false;
                            }
                            return true;
                        }

                        @Override // java.util.Map.Entry
                        public int hashCode() {
                            Object obj3 = obj;
                            int iHashCode = obj3 == null ? 0 : obj3.hashCode();
                            Object obj4 = obj2;
                            return iHashCode ^ (obj4 != null ? obj4.hashCode() : 0);
                        }

                        public String toString() {
                            return obj + ContainerUtils.KEY_VALUE_DELIMITER + obj2;
                        }
                    };
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.key == null) {
                        throw new IllegalStateException();
                    }
                    NativeObject.this.remove(this.key);
                    this.key = null;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return NativeObject.this.size();
        }
    }

    class KeySet extends AbstractSet<Object> {
        KeySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return NativeObject.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Object> iterator() {
            return new Iterator<Object>() { // from class: org.mozilla.javascript.NativeObject.KeySet.1
                Object[] ids;
                int index = 0;
                Object key;

                {
                    this.ids = NativeObject.this.getIds();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                @Override // java.util.Iterator
                public Object next() {
                    try {
                        Object[] objArr = this.ids;
                        int i = this.index;
                        this.index = i + 1;
                        Object obj = objArr[i];
                        this.key = obj;
                        return obj;
                    } catch (ArrayIndexOutOfBoundsException unused) {
                        this.key = null;
                        throw new NoSuchElementException();
                    }
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.key == null) {
                        throw new IllegalStateException();
                    }
                    NativeObject.this.remove(this.key);
                    this.key = null;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return NativeObject.this.size();
        }
    }

    class ValueCollection extends AbstractCollection<Object> {
        ValueCollection() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Object> iterator() {
            return new Iterator<Object>() { // from class: org.mozilla.javascript.NativeObject.ValueCollection.1
                Object[] ids;
                int index = 0;
                Object key;

                {
                    this.ids = NativeObject.this.getIds();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                @Override // java.util.Iterator
                public Object next() {
                    NativeObject nativeObject = NativeObject.this;
                    Object[] objArr = this.ids;
                    int i = this.index;
                    this.index = i + 1;
                    Object obj = objArr[i];
                    this.key = obj;
                    return nativeObject.get(obj);
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.key == null) {
                        throw new IllegalStateException();
                    }
                    NativeObject.this.remove(this.key);
                    this.key = null;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return NativeObject.this.size();
        }
    }

    /* JADX WARN: Code duplicated, block: B:44:0x0089  */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int length = str.length();
        int i = 7;
        if (length == 7) {
            str2 = "valueOf";
            i = 4;
        } else if (length == 8) {
            char cCharAt = str.charAt(3);
            if (cCharAt == 'o') {
                str2 = "toSource";
                i = 8;
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
        } else if (length == 16) {
            char cCharAt2 = str.charAt(2);
            if (cCharAt2 == 'd') {
                char cCharAt3 = str.charAt(8);
                if (cCharAt3 == 'G') {
                    str2 = "__defineGetter__";
                    i = 9;
                } else if (cCharAt3 == 'S') {
                    str2 = "__defineSetter__";
                    i = 10;
                } else {
                    str2 = null;
                    i = 0;
                }
            } else if (cCharAt2 != 'l') {
                str2 = null;
                i = 0;
            } else {
                char cCharAt4 = str.charAt(8);
                if (cCharAt4 == 'G') {
                    str2 = "__lookupGetter__";
                    i = 11;
                } else if (cCharAt4 == 'S') {
                    str2 = "__lookupSetter__";
                    i = 12;
                } else {
                    str2 = null;
                    i = 0;
                }
            }
        } else if (length == 20) {
            str2 = "propertyIsEnumerable";
            i = 6;
        } else if (length == 13) {
            str2 = "isPrototypeOf";
        } else if (length == 14) {
            char cCharAt5 = str.charAt(0);
            if (cCharAt5 == 'h') {
                str2 = "hasOwnProperty";
                i = 5;
            } else if (cCharAt5 == 't') {
                str2 = "toLocaleString";
                i = 3;
            } else {
                str2 = null;
                i = 0;
            }
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
