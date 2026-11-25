package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.bytebuddy.asm.Advice;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;
import org.mozilla.javascript.annotations.JSSetter;
import org.mozilla.javascript.annotations.JSStaticFunction;
import org.mozilla.javascript.debug.DebuggableObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ScriptableObject implements Scriptable, SymbolScriptable, Serializable, DebuggableObject, ConstProperties {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONST = 13;
    public static final int DONTENUM = 2;
    public static final int EMPTY = 0;
    private static final Method GET_ARRAY_LENGTH;
    private static final Comparator<Object> KEY_COMPARATOR;
    public static final int PERMANENT = 4;
    public static final int READONLY = 1;
    public static final int UNINITIALIZED_CONST = 8;
    private static final long serialVersionUID = 2829861078851942586L;
    private volatile Map<Object, Object> associatedValues;
    private transient ExternalArrayData externalData;
    private boolean isExtensible;
    private boolean isSealed;
    private Scriptable parentScopeObject;
    private Scriptable prototypeObject;
    private transient SlotMapContainer slotMap;

    enum SlotAccess {
        QUERY,
        MODIFY,
        MODIFY_CONST,
        MODIFY_GETTER_SETTER,
        CONVERT_ACCESSOR_TO_DATA
    }

    public boolean avoidObjectDetection() {
        return false;
    }

    @Override // org.mozilla.javascript.Scriptable
    public abstract String getClassName();

    static {
        try {
            GET_ARRAY_LENGTH = ScriptableObject.class.getMethod("getExternalArrayLength", new Class[0]);
            KEY_COMPARATOR = new KeyComparator();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static class Slot implements Serializable {
        private static final long serialVersionUID = -6090581677123995491L;
        private short attributes;
        int indexOrHash;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        Object f1064name;
        transient Slot next;
        transient Slot orderedNext;
        Object value;

        Slot(Object obj, int i, int i2) {
            this.f1064name = obj;
            this.indexOrHash = i;
            this.attributes = (short) i2;
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            Object obj = this.f1064name;
            if (obj != null) {
                this.indexOrHash = obj.hashCode();
            }
        }

        boolean setValue(Object obj, Scriptable scriptable, Scriptable scriptable2) {
            if ((this.attributes & 1) != 0) {
                if (Context.getContext().isStrictMode()) {
                    throw ScriptRuntime.typeError1("msg.modify.readonly", this.f1064name);
                }
                return true;
            }
            if (scriptable != scriptable2) {
                return false;
            }
            this.value = obj;
            return true;
        }

        Object getValue(Scriptable scriptable) {
            return this.value;
        }

        int getAttributes() {
            return this.attributes;
        }

        synchronized void setAttributes(int i) {
            ScriptableObject.checkValidAttributes(i);
            this.attributes = (short) i;
        }

        ScriptableObject getPropertyDescriptor(Context context, Scriptable scriptable) {
            return ScriptableObject.buildDataDescriptor(scriptable, this.value, this.attributes);
        }
    }

    protected static ScriptableObject buildDataDescriptor(Scriptable scriptable, Object obj, int i) {
        NativeObject nativeObject = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(nativeObject, scriptable, TopLevel.Builtins.Object);
        nativeObject.defineProperty("value", obj, 0);
        nativeObject.defineProperty("writable", Boolean.valueOf((i & 1) == 0), 0);
        nativeObject.defineProperty("enumerable", Boolean.valueOf((i & 2) == 0), 0);
        nativeObject.defineProperty("configurable", Boolean.valueOf((i & 4) == 0), 0);
        return nativeObject;
    }

    static final class GetterSlot extends Slot {
        private static final long serialVersionUID = -4900574849788797588L;
        Object getter;
        Object setter;

        GetterSlot(Object obj, int i, int i2) {
            super(obj, i, i2);
        }

        @Override // org.mozilla.javascript.ScriptableObject.Slot
        ScriptableObject getPropertyDescriptor(Context context, Scriptable scriptable) {
            int attributes = getAttributes();
            NativeObject nativeObject = new NativeObject();
            ScriptRuntime.setBuiltinProtoAndParent(nativeObject, scriptable, TopLevel.Builtins.Object);
            nativeObject.defineProperty("enumerable", Boolean.valueOf((attributes & 2) == 0), 0);
            nativeObject.defineProperty("configurable", Boolean.valueOf((attributes & 4) == 0), 0);
            if (this.getter == null && this.setter == null) {
                nativeObject.defineProperty("writable", Boolean.valueOf((attributes & 1) == 0), 0);
            }
            String string = this.f1064name == null ? "f" : this.f1064name.toString();
            Object obj = this.getter;
            if (obj != null) {
                if (obj instanceof MemberBox) {
                    nativeObject.defineProperty("get", new FunctionObject(string, ((MemberBox) this.getter).member(), scriptable), 0);
                } else if (obj instanceof Member) {
                    nativeObject.defineProperty("get", new FunctionObject(string, (Member) this.getter, scriptable), 0);
                } else {
                    nativeObject.defineProperty("get", obj, 0);
                }
            }
            Object obj2 = this.setter;
            if (obj2 != null) {
                if (obj2 instanceof MemberBox) {
                    nativeObject.defineProperty(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, new FunctionObject(string, ((MemberBox) this.setter).member(), scriptable), 0);
                } else if (obj2 instanceof Member) {
                    nativeObject.defineProperty(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, new FunctionObject(string, (Member) this.setter, scriptable), 0);
                } else {
                    nativeObject.defineProperty(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, obj2, 0);
                }
            }
            return nativeObject;
        }

        @Override // org.mozilla.javascript.ScriptableObject.Slot
        boolean setValue(Object obj, Scriptable scriptable, Scriptable scriptable2) {
            Object obj2;
            Object[] objArr;
            String str;
            if (this.setter == null) {
                if (this.getter != null) {
                    Context context = Context.getContext();
                    if (!context.isStrictMode() && !context.hasFeature(11)) {
                        return true;
                    }
                    if (this.f1064name == null) {
                        str = "";
                    } else {
                        str = "[" + scriptable2.getClassName() + "]." + this.f1064name;
                    }
                    throw ScriptRuntime.typeError2("msg.set.prop.no.setter", str, Context.toString(obj));
                }
                return super.setValue(obj, scriptable, scriptable2);
            }
            Context context2 = Context.getContext();
            Object obj3 = this.setter;
            if (obj3 instanceof MemberBox) {
                MemberBox memberBox = (MemberBox) obj3;
                Class<?>[] clsArr = memberBox.argTypes;
                Object objConvertArg = FunctionObject.convertArg(context2, scriptable2, obj, FunctionObject.getTypeTag(clsArr[clsArr.length - 1]));
                if (memberBox.delegateTo == null) {
                    objArr = new Object[]{objConvertArg};
                    obj2 = scriptable2;
                } else {
                    Object[] objArr2 = {scriptable2, objConvertArg};
                    obj2 = memberBox.delegateTo;
                    objArr = objArr2;
                }
                memberBox.invoke(obj2, objArr);
            } else if (obj3 instanceof Function) {
                Function function = (Function) obj3;
                function.call(context2, function.getParentScope(), scriptable2, new Object[]{obj});
            }
            return true;
        }

        @Override // org.mozilla.javascript.ScriptableObject.Slot
        Object getValue(Scriptable scriptable) {
            Object obj;
            Object[] objArr;
            Object obj2 = this.getter;
            if (obj2 != null) {
                if (obj2 instanceof MemberBox) {
                    MemberBox memberBox = (MemberBox) obj2;
                    if (memberBox.delegateTo == null) {
                        objArr = ScriptRuntime.emptyArgs;
                        obj = scriptable;
                    } else {
                        Object[] objArr2 = {scriptable};
                        obj = memberBox.delegateTo;
                        objArr = objArr2;
                    }
                    return memberBox.invoke(obj, objArr);
                }
                if (obj2 instanceof Function) {
                    Function function = (Function) obj2;
                    return function.call(Context.getContext(), function.getParentScope(), scriptable, ScriptRuntime.emptyArgs);
                }
            }
            Object obj3 = this.value;
            if (!(obj3 instanceof LazilyLoadedCtor)) {
                return obj3;
            }
            LazilyLoadedCtor lazilyLoadedCtor = (LazilyLoadedCtor) obj3;
            try {
                lazilyLoadedCtor.init();
                return lazilyLoadedCtor.getValue();
            } finally {
                this.value = lazilyLoadedCtor.getValue();
            }
        }
    }

    static void checkValidAttributes(int i) {
        if ((i & (-16)) != 0) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
    }

    private static SlotMapContainer createSlotMap(int i) {
        Context currentContext = Context.getCurrentContext();
        if (currentContext != null && currentContext.hasFeature(17)) {
            return new ThreadSafeSlotMapContainer(i);
        }
        return new SlotMapContainer(i);
    }

    public ScriptableObject() {
        this.isExtensible = true;
        this.isSealed = false;
        this.slotMap = createSlotMap(0);
    }

    public ScriptableObject(Scriptable scriptable, Scriptable scriptable2) {
        this.isExtensible = true;
        this.isSealed = false;
        if (scriptable == null) {
            throw new IllegalArgumentException();
        }
        this.parentScopeObject = scriptable;
        this.prototypeObject = scriptable2;
        this.slotMap = createSlotMap(0);
    }

    public String getTypeOf() {
        return avoidObjectDetection() ? "undefined" : "object";
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean has(String str, Scriptable scriptable) {
        return this.slotMap.query(str, 0) != null;
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean has(int i, Scriptable scriptable) {
        ExternalArrayData externalArrayData = this.externalData;
        if (externalArrayData != null) {
            return i < externalArrayData.getArrayLength();
        }
        return this.slotMap.query(null, i) != null;
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        return this.slotMap.query(symbol, 0) != null;
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object get(String str, Scriptable scriptable) {
        Slot slotQuery = this.slotMap.query(str, 0);
        if (slotQuery == null) {
            return Scriptable.NOT_FOUND;
        }
        return slotQuery.getValue(scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object get(int i, Scriptable scriptable) {
        ExternalArrayData externalArrayData = this.externalData;
        if (externalArrayData != null) {
            if (i < externalArrayData.getArrayLength()) {
                return this.externalData.getArrayElement(i);
            }
            return Scriptable.NOT_FOUND;
        }
        Slot slotQuery = this.slotMap.query(null, i);
        if (slotQuery == null) {
            return Scriptable.NOT_FOUND;
        }
        return slotQuery.getValue(scriptable);
    }

    public Object get(Symbol symbol, Scriptable scriptable) {
        Slot slotQuery = this.slotMap.query(symbol, 0);
        if (slotQuery == null) {
            return Scriptable.NOT_FOUND;
        }
        return slotQuery.getValue(scriptable);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void put(String str, Scriptable scriptable, Object obj) {
        if (putImpl(str, 0, scriptable, obj)) {
            return;
        }
        if (scriptable == this) {
            throw Kit.codeBug();
        }
        scriptable.put(str, scriptable, obj);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void put(int i, Scriptable scriptable, Object obj) {
        ExternalArrayData externalArrayData = this.externalData;
        if (externalArrayData != null) {
            if (i < externalArrayData.getArrayLength()) {
                this.externalData.setArrayElement(i, obj);
                return;
            }
            throw new JavaScriptException(ScriptRuntime.newNativeError(Context.getCurrentContext(), this, TopLevel.NativeErrors.RangeError, new Object[]{"External array index out of bounds "}), null, 0);
        }
        if (putImpl(null, i, scriptable, obj)) {
            return;
        }
        if (scriptable == this) {
            throw Kit.codeBug();
        }
        scriptable.put(i, scriptable, obj);
    }

    public void put(Symbol symbol, Scriptable scriptable, Object obj) {
        if (putImpl(symbol, 0, scriptable, obj)) {
            return;
        }
        if (scriptable == this) {
            throw Kit.codeBug();
        }
        ensureSymbolScriptable(scriptable).put(symbol, scriptable, obj);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void delete(String str) {
        checkNotSealed(str, 0);
        this.slotMap.remove(str, 0);
    }

    @Override // org.mozilla.javascript.Scriptable
    public void delete(int i) {
        checkNotSealed(null, i);
        this.slotMap.remove(null, i);
    }

    public void delete(Symbol symbol) {
        checkNotSealed(symbol, 0);
        this.slotMap.remove(symbol, 0);
    }

    @Override // org.mozilla.javascript.ConstProperties
    public void putConst(String str, Scriptable scriptable, Object obj) {
        if (putConstImpl(str, 0, scriptable, obj, 1)) {
            return;
        }
        if (scriptable == this) {
            throw Kit.codeBug();
        }
        if (scriptable instanceof ConstProperties) {
            ((ConstProperties) scriptable).putConst(str, scriptable, obj);
        } else {
            scriptable.put(str, scriptable, obj);
        }
    }

    @Override // org.mozilla.javascript.ConstProperties
    public void defineConst(String str, Scriptable scriptable) {
        if (putConstImpl(str, 0, scriptable, Undefined.instance, 8)) {
            return;
        }
        if (scriptable == this) {
            throw Kit.codeBug();
        }
        if (scriptable instanceof ConstProperties) {
            ((ConstProperties) scriptable).defineConst(str, scriptable);
        }
    }

    @Override // org.mozilla.javascript.ConstProperties
    public boolean isConst(String str) {
        Slot slotQuery = this.slotMap.query(str, 0);
        return slotQuery != null && (slotQuery.getAttributes() & 5) == 5;
    }

    @Deprecated
    public final int getAttributes(String str, Scriptable scriptable) {
        return getAttributes(str);
    }

    @Deprecated
    public final int getAttributes(int i, Scriptable scriptable) {
        return getAttributes(i);
    }

    @Deprecated
    public final void setAttributes(String str, Scriptable scriptable, int i) {
        setAttributes(str, i);
    }

    @Deprecated
    public void setAttributes(int i, Scriptable scriptable, int i2) {
        setAttributes(i, i2);
    }

    public int getAttributes(String str) {
        return findAttributeSlot(str, 0, SlotAccess.QUERY).getAttributes();
    }

    public int getAttributes(int i) {
        return findAttributeSlot(null, i, SlotAccess.QUERY).getAttributes();
    }

    public int getAttributes(Symbol symbol) {
        return findAttributeSlot(symbol, SlotAccess.QUERY).getAttributes();
    }

    public void setAttributes(String str, int i) {
        checkNotSealed(str, 0);
        findAttributeSlot(str, 0, SlotAccess.MODIFY).setAttributes(i);
    }

    public void setAttributes(int i, int i2) {
        checkNotSealed(null, i);
        findAttributeSlot(null, i, SlotAccess.MODIFY).setAttributes(i2);
    }

    public void setAttributes(Symbol symbol, int i) {
        checkNotSealed(symbol, 0);
        findAttributeSlot(symbol, SlotAccess.MODIFY).setAttributes(i);
    }

    public void setGetterOrSetter(String str, int i, Callable callable, boolean z) {
        setGetterOrSetter(str, i, callable, z, false);
    }

    private void setGetterOrSetter(String str, int i, Callable callable, boolean z, boolean z2) {
        GetterSlot getterSlot;
        if (str != null && i != 0) {
            throw new IllegalArgumentException(str);
        }
        if (!z2) {
            checkNotSealed(str, i);
        }
        if (isExtensible()) {
            getterSlot = (GetterSlot) this.slotMap.get(str, i, SlotAccess.MODIFY_GETTER_SETTER);
        } else {
            Slot slotQuery = this.slotMap.query(str, i);
            if (!(slotQuery instanceof GetterSlot)) {
                return;
            } else {
                getterSlot = (GetterSlot) slotQuery;
            }
        }
        if (!z2 && (getterSlot.getAttributes() & 1) != 0) {
            throw Context.reportRuntimeError1("msg.modify.readonly", str);
        }
        if (z) {
            getterSlot.setter = callable;
        } else {
            getterSlot.getter = callable;
        }
        getterSlot.value = Undefined.instance;
    }

    public Object getGetterOrSetter(String str, int i, boolean z) {
        if (str != null && i != 0) {
            throw new IllegalArgumentException(str);
        }
        Slot slotQuery = this.slotMap.query(str, i);
        if (slotQuery == null) {
            return null;
        }
        if (slotQuery instanceof GetterSlot) {
            GetterSlot getterSlot = (GetterSlot) slotQuery;
            Object obj = z ? getterSlot.setter : getterSlot.getter;
            return obj != null ? obj : Undefined.instance;
        }
        return Undefined.instance;
    }

    protected boolean isGetterOrSetter(String str, int i, boolean z) {
        Slot slotQuery = this.slotMap.query(str, i);
        if (!(slotQuery instanceof GetterSlot)) {
            return false;
        }
        if (!z || ((GetterSlot) slotQuery).setter == null) {
            return (z || ((GetterSlot) slotQuery).getter == null) ? false : true;
        }
        return true;
    }

    void addLazilyInitializedValue(String str, int i, LazilyLoadedCtor lazilyLoadedCtor, int i2) {
        if (str != null && i != 0) {
            throw new IllegalArgumentException(str);
        }
        checkNotSealed(str, i);
        GetterSlot getterSlot = (GetterSlot) this.slotMap.get(str, i, SlotAccess.MODIFY_GETTER_SETTER);
        getterSlot.setAttributes(i2);
        getterSlot.getter = null;
        getterSlot.setter = null;
        getterSlot.value = lazilyLoadedCtor;
    }

    public void setExternalArrayData(ExternalArrayData externalArrayData) {
        this.externalData = externalArrayData;
        if (externalArrayData == null) {
            delete("length");
        } else {
            defineProperty("length", null, GET_ARRAY_LENGTH, null, 3);
        }
    }

    public ExternalArrayData getExternalArrayData() {
        return this.externalData;
    }

    public Object getExternalArrayLength() {
        ExternalArrayData externalArrayData = this.externalData;
        return Integer.valueOf(externalArrayData == null ? 0 : externalArrayData.getArrayLength());
    }

    @Override // org.mozilla.javascript.Scriptable
    public Scriptable getPrototype() {
        return this.prototypeObject;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void setPrototype(Scriptable scriptable) {
        if (!isExtensible() && Context.getContext().getLanguageVersion() >= 180) {
            throw ScriptRuntime.typeError0("msg.not.extensible");
        }
        this.prototypeObject = scriptable;
    }

    @Override // org.mozilla.javascript.Scriptable
    public Scriptable getParentScope() {
        return this.parentScopeObject;
    }

    @Override // org.mozilla.javascript.Scriptable
    public void setParentScope(Scriptable scriptable) {
        this.parentScopeObject = scriptable;
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object[] getIds() {
        return getIds(false, false);
    }

    @Override // org.mozilla.javascript.debug.DebuggableObject
    public Object[] getAllIds() {
        return getIds(true, false);
    }

    @Override // org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        return getDefaultValue(this, cls);
    }

    public static Object getDefaultValue(Scriptable scriptable, Class<?> cls) {
        String str;
        Context context = null;
        for (int i = 0; i < 2; i++) {
            boolean z = true;
            if (cls != ScriptRuntime.StringClass ? i != 1 : i != 0) {
                z = false;
            }
            if (z) {
                str = "toString";
            } else {
                str = "valueOf";
            }
            Object property = getProperty(scriptable, str);
            if (property instanceof Function) {
                Function function = (Function) property;
                if (context == null) {
                    context = Context.getContext();
                }
                Object objCall = function.call(context, function.getParentScope(), scriptable, ScriptRuntime.emptyArgs);
                if (objCall != null) {
                    if (!(objCall instanceof Scriptable)) {
                        return objCall;
                    }
                    if (cls != ScriptRuntime.ScriptableClass && cls != ScriptRuntime.FunctionClass) {
                        if (z && (objCall instanceof Wrapper)) {
                            objCall = ((Wrapper) objCall).unwrap();
                            if (objCall instanceof String) {
                            }
                        }
                    }
                    return objCall;
                }
                continue;
            }
        }
        throw ScriptRuntime.typeError1("msg.default.value", cls == null ? "undefined" : cls.getName());
    }

    @Override // org.mozilla.javascript.Scriptable
    public boolean hasInstance(Scriptable scriptable) {
        return ScriptRuntime.jsDelegatesTo(scriptable, this);
    }

    protected Object equivalentValues(Object obj) {
        return this == obj ? Boolean.TRUE : Scriptable.NOT_FOUND;
    }

    public static <T extends Scriptable> void defineClass(Scriptable scriptable, Class<T> cls) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        defineClass(scriptable, cls, false, false);
    }

    public static <T extends Scriptable> void defineClass(Scriptable scriptable, Class<T> cls, boolean z) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        defineClass(scriptable, cls, z, false);
    }

    public static <T extends Scriptable> String defineClass(Scriptable scriptable, Class<T> cls, boolean z, boolean z2) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        BaseFunction baseFunctionBuildClassCtor = buildClassCtor(scriptable, cls, z, z2);
        if (baseFunctionBuildClassCtor == null) {
            return null;
        }
        String className = baseFunctionBuildClassCtor.getClassPrototype().getClassName();
        defineProperty(scriptable, className, baseFunctionBuildClassCtor, 2);
        return className;
    }

    /* JADX WARN: Code duplicated, block: B:58:0x00e6  */
    static <T extends Scriptable> BaseFunction buildClassCtor(Scriptable scriptable, Class<T> cls, boolean z, boolean z2) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor<?> constructor;
        Scriptable objectPrototype;
        HashSet hashSet;
        Annotation annotation;
        String str;
        String str2;
        char c;
        Constructor<?> constructor2;
        Method methodFindSingleMethod;
        String strDefineClass;
        BaseFunction baseFunction;
        Object prototypeProperty;
        Method[] methodList = FunctionObject.getMethodList(cls);
        int i = 0;
        while (true) {
            int i2 = 3;
            Method method = null;
            if (i < methodList.length) {
                Method method2 = methodList[i];
                if (method2.getName().equals(ConfigConstants.CONFIG_INIT_SECTION)) {
                    Class<?>[] parameterTypes = method2.getParameterTypes();
                    if (parameterTypes.length == 3 && parameterTypes[0] == ScriptRuntime.ContextClass && parameterTypes[1] == ScriptRuntime.ScriptableClass && parameterTypes[2] == Boolean.TYPE && Modifier.isStatic(method2.getModifiers())) {
                        method2.invoke(null, Context.getContext(), scriptable, z ? Boolean.TRUE : Boolean.FALSE);
                        return null;
                    }
                    if (parameterTypes.length == 1 && parameterTypes[0] == ScriptRuntime.ScriptableClass && Modifier.isStatic(method2.getModifiers())) {
                        method2.invoke(null, scriptable);
                        return null;
                    }
                }
                i++;
            } else {
                Constructor<?>[] constructors = cls.getConstructors();
                int i3 = 0;
                while (true) {
                    if (i3 >= constructors.length) {
                        constructor = null;
                        break;
                    }
                    if (constructors[i3].getParameterTypes().length == 0) {
                        constructor = constructors[i3];
                        break;
                    }
                    i3++;
                }
                if (constructor == null) {
                    throw Context.reportRuntimeError1("msg.zero.arg.ctor", cls.getName());
                }
                Scriptable scriptable2 = (Scriptable) constructor.newInstance(ScriptRuntime.emptyArgs);
                String className = scriptable2.getClassName();
                Object property = getProperty(getTopLevelScope(scriptable), className);
                if ((property instanceof BaseFunction) && (prototypeProperty = (baseFunction = (BaseFunction) property).getPrototypeProperty()) != null) {
                    if (cls.equals(prototypeProperty.getClass())) {
                        return baseFunction;
                    }
                }
                if (z2) {
                    Class<? super T> superclass = cls.getSuperclass();
                    if (!ScriptRuntime.ScriptableClass.isAssignableFrom(superclass) || Modifier.isAbstract(superclass.getModifiers()) || (strDefineClass = defineClass(scriptable, extendsScriptable(superclass), z, z2)) == null) {
                        objectPrototype = null;
                    } else {
                        objectPrototype = getClassPrototype(scriptable, strDefineClass);
                    }
                } else {
                    objectPrototype = null;
                }
                if (objectPrototype == null) {
                    objectPrototype = getObjectPrototype(scriptable);
                }
                scriptable2.setPrototype(objectPrototype);
                Member memberFindAnnotatedMember = findAnnotatedMember(methodList, JSConstructor.class);
                Member memberFindAnnotatedMember2 = memberFindAnnotatedMember;
                if (memberFindAnnotatedMember == null) {
                    memberFindAnnotatedMember2 = findAnnotatedMember(constructors, JSConstructor.class);
                }
                String str3 = "jsConstructor";
                Member member = memberFindAnnotatedMember2;
                if (memberFindAnnotatedMember2 == null) {
                    methodFindSingleMethod = FunctionObject.findSingleMethod(methodList, "jsConstructor");
                }
                if (member == null) {
                    if (constructors.length == 1) {
                        constructor2 = constructors[0];
                    } else if (constructors.length == 2) {
                        if (constructors[0].getParameterTypes().length == 0) {
                            member = methodFindSingleMethod;
                            member = member;
                            member = constructors[1];
                        } else if (constructors[1].getParameterTypes().length == 0) {
                            member = methodFindSingleMethod;
                            member = member;
                            member = member;
                            member = constructors[0];
                        }
                    }
                    if (member == null) {
                        member = methodFindSingleMethod;
                        member = constructor2;
                        throw Context.reportRuntimeError1("msg.ctor.multiple.parms", cls.getName());
                    }
                }
                member = methodFindSingleMethod;
                member = constructor2;
                member = methodFindSingleMethod;
                FunctionObject functionObject = new FunctionObject(className, member, scriptable);
                if (functionObject.isVarArgsMethod()) {
                    throw Context.reportRuntimeError1("msg.varargs.ctor", member.getName());
                }
                functionObject.initAsConstructor(scriptable, scriptable2);
                HashSet hashSet2 = new HashSet();
                HashSet hashSet3 = new HashSet();
                int length = methodList.length;
                int i4 = 0;
                while (i4 < length) {
                    Method method3 = methodList[i4];
                    if (method3 == member) {
                        hashSet = hashSet2;
                    } else {
                        String name2 = method3.getName();
                        if (name2.equals("finishInit")) {
                            Class<?>[] parameterTypes2 = method3.getParameterTypes();
                            hashSet = hashSet2;
                            if (parameterTypes2.length == i2 && parameterTypes2[0] == ScriptRuntime.ScriptableClass && parameterTypes2[1] == FunctionObject.class && parameterTypes2[2] == ScriptRuntime.ScriptableClass && Modifier.isStatic(method3.getModifiers())) {
                                method = method3;
                            }
                        } else {
                            hashSet = hashSet2;
                        }
                        if (name2.indexOf(36) == -1 && !name2.equals(str3)) {
                            if (method3.isAnnotationPresent(JSFunction.class)) {
                                annotation = method3.getAnnotation(JSFunction.class);
                            } else if (method3.isAnnotationPresent(JSStaticFunction.class)) {
                                annotation = method3.getAnnotation(JSStaticFunction.class);
                            } else if (method3.isAnnotationPresent(JSGetter.class)) {
                                annotation = method3.getAnnotation(JSGetter.class);
                            } else {
                                if (!method3.isAnnotationPresent(JSSetter.class)) {
                                    annotation = null;
                                }
                                hashSet3 = hashSet3;
                                length = length;
                                c = 2;
                            }
                            str = str3;
                            if (annotation == null) {
                                str2 = "jsFunction_";
                                if (!name2.startsWith("jsFunction_")) {
                                    if (name2.startsWith("jsStaticFunction_")) {
                                        str2 = "jsStaticFunction_";
                                    } else if (name2.startsWith("jsGet_")) {
                                        str2 = "jsGet_";
                                    } else {
                                        hashSet3 = hashSet3;
                                        length = length;
                                        c = 2;
                                    }
                                }
                            } else {
                                str2 = null;
                            }
                            boolean z3 = (annotation instanceof JSStaticFunction) || str2 == "jsStaticFunction_";
                            HashSet hashSet4 = z3 ? hashSet : hashSet3;
                            String propertyName = getPropertyName(name2, str2, annotation);
                            if (hashSet4.contains(propertyName)) {
                                throw Context.reportRuntimeError2("duplicate.defineClass.name", name2, propertyName);
                            }
                            hashSet4.add(propertyName);
                            if ((annotation instanceof JSGetter) || str2 == "jsGet_") {
                                c = 2;
                                if (!(scriptable2 instanceof ScriptableObject)) {
                                    throw Context.reportRuntimeError2("msg.extend.scriptable", scriptable2.getClass().toString(), propertyName);
                                }
                                Method methodFindSetterMethod = findSetterMethod(methodList, propertyName, "jsSet_");
                                ((ScriptableObject) scriptable2).defineProperty(propertyName, null, method3, methodFindSetterMethod, (methodFindSetterMethod != null ? 0 : 1) | 6);
                            } else {
                                if (z3 && !Modifier.isStatic(method3.getModifiers())) {
                                    throw Context.reportRuntimeError("jsStaticFunction must be used with static method.");
                                }
                                FunctionObject functionObject2 = new FunctionObject(propertyName, method3, scriptable2);
                                if (functionObject2.isVarArgsConstructor()) {
                                    throw Context.reportRuntimeError1("msg.varargs.fun", member.getName());
                                }
                                c = 2;
                                defineProperty(z3 ? functionObject : scriptable2, propertyName, functionObject2, 2);
                                if (z) {
                                    functionObject2.sealObject();
                                }
                            }
                        }
                        i4++;
                        hashSet2 = hashSet;
                        str3 = str;
                        hashSet3 = hashSet3;
                        length = length;
                        i2 = 3;
                    }
                    str = str3;
                    hashSet3 = hashSet3;
                    length = length;
                    c = 2;
                    i4++;
                    hashSet2 = hashSet;
                    str3 = str;
                    hashSet3 = hashSet3;
                    length = length;
                    i2 = 3;
                }
                if (method != null) {
                    method.invoke(null, scriptable, functionObject, scriptable2);
                }
                if (z) {
                    functionObject.sealObject();
                    if (scriptable2 instanceof ScriptableObject) {
                        ((ScriptableObject) scriptable2).sealObject();
                    }
                }
                return functionObject;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Member findAnnotatedMember(AccessibleObject[] accessibleObjectArr, Class<? extends Annotation> cls) {
        for (Method method : accessibleObjectArr) {
            if (method.isAnnotationPresent(cls)) {
                return method;
            }
        }
        return null;
    }

    private static Method findSetterMethod(Method[] methodArr, String str, String str2) {
        String str3 = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX + Character.toUpperCase(str.charAt(0)) + str.substring(1);
        for (Method method : methodArr) {
            JSSetter jSSetter = (JSSetter) method.getAnnotation(JSSetter.class);
            if (jSSetter != null && (str.equals(jSSetter.value()) || ("".equals(jSSetter.value()) && str3.equals(method.getName())))) {
                return method;
            }
        }
        String str4 = str2 + str;
        for (Method method2 : methodArr) {
            if (str4.equals(method2.getName())) {
                return method2;
            }
        }
        return null;
    }

    private static String getPropertyName(String str, String str2, Annotation annotation) {
        String strValue;
        if (str2 != null) {
            return str.substring(str2.length());
        }
        if (annotation instanceof JSGetter) {
            strValue = ((JSGetter) annotation).value();
            if ((strValue == null || strValue.length() == 0) && str.length() > 3 && str.startsWith("get")) {
                strValue = str.substring(3);
                if (Character.isUpperCase(strValue.charAt(0))) {
                    if (strValue.length() == 1) {
                        strValue = strValue.toLowerCase();
                    } else if (!Character.isUpperCase(strValue.charAt(1))) {
                        strValue = Character.toLowerCase(strValue.charAt(0)) + strValue.substring(1);
                    }
                }
            }
        } else if (annotation instanceof JSFunction) {
            strValue = ((JSFunction) annotation).value();
        } else {
            strValue = annotation instanceof JSStaticFunction ? ((JSStaticFunction) annotation).value() : null;
        }
        return (strValue == null || strValue.length() == 0) ? str : strValue;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T extends Scriptable> Class<T> extendsScriptable(Class<?> cls) {
        if (ScriptRuntime.ScriptableClass.isAssignableFrom(cls)) {
            return cls;
        }
        return null;
    }

    public void defineProperty(String str, Object obj, int i) {
        checkNotSealed(str, 0);
        put(str, this, obj);
        setAttributes(str, i);
    }

    public void defineProperty(Symbol symbol, Object obj, int i) {
        checkNotSealed(symbol, 0);
        put(symbol, this, obj);
        setAttributes(symbol, i);
    }

    public static void defineProperty(Scriptable scriptable, String str, Object obj, int i) {
        if (!(scriptable instanceof ScriptableObject)) {
            scriptable.put(str, scriptable, obj);
        } else {
            ((ScriptableObject) scriptable).defineProperty(str, obj, i);
        }
    }

    public static void defineConstProperty(Scriptable scriptable, String str) {
        if (scriptable instanceof ConstProperties) {
            ((ConstProperties) scriptable).defineConst(str, scriptable);
        } else {
            defineProperty(scriptable, str, Undefined.instance, 13);
        }
    }

    public void defineProperty(String str, Class<?> cls, int i) {
        int length = str.length();
        if (length == 0) {
            throw new IllegalArgumentException();
        }
        char[] cArr = new char[length + 3];
        str.getChars(0, length, cArr, 3);
        cArr[3] = Character.toUpperCase(cArr[3]);
        cArr[0] = 'g';
        cArr[1] = 'e';
        cArr[2] = Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL;
        String str2 = new String(cArr);
        cArr[0] = Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL;
        String str3 = new String(cArr);
        Method[] methodList = FunctionObject.getMethodList(cls);
        Method methodFindSingleMethod = FunctionObject.findSingleMethod(methodList, str2);
        Method methodFindSingleMethod2 = FunctionObject.findSingleMethod(methodList, str3);
        if (methodFindSingleMethod2 == null) {
            i |= 1;
        }
        int i2 = i;
        if (methodFindSingleMethod2 == null) {
            methodFindSingleMethod2 = null;
        }
        defineProperty(str, null, methodFindSingleMethod, methodFindSingleMethod2, i2);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002d  */
    public void defineProperty(String str, Object obj, Method method, Method method2, int i) {
        MemberBox memberBox;
        boolean z;
        String str2;
        boolean z2;
        String str3;
        Class<?> cls;
        MemberBox memberBox2 = null;
        str = null;
        String str4 = null;
        if (method != null) {
            memberBox = new MemberBox(method);
            if (!Modifier.isStatic(method.getModifiers())) {
                boolean z3 = obj != null;
                memberBox.delegateTo = obj;
                z2 = z3;
            } else {
                memberBox.delegateTo = Void.TYPE;
                z2 = true;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                if (z2) {
                    str3 = "msg.obj.getter.parms";
                } else {
                    str3 = null;
                }
            } else if (parameterTypes.length == 1 && (((cls = parameterTypes[0]) == ScriptRuntime.ScriptableClass || cls == ScriptRuntime.ScriptableObjectClass) && z2)) {
                str3 = null;
            } else {
                str3 = "msg.bad.getter.parms";
            }
            if (str3 != null) {
                throw Context.reportRuntimeError1(str3, method.toString());
            }
        } else {
            memberBox = null;
        }
        if (method2 != null) {
            if (method2.getReturnType() != Void.TYPE) {
                throw Context.reportRuntimeError1("msg.setter.return", method2.toString());
            }
            MemberBox memberBox3 = new MemberBox(method2);
            if (!Modifier.isStatic(method2.getModifiers())) {
                boolean z4 = obj != null;
                memberBox3.delegateTo = obj;
                z = z4;
            } else {
                memberBox3.delegateTo = Void.TYPE;
                z = true;
            }
            Class<?>[] parameterTypes2 = method2.getParameterTypes();
            if (parameterTypes2.length == 1) {
                if (z) {
                    str4 = "msg.setter2.expected";
                }
            } else if (parameterTypes2.length == 2) {
                Class<?> cls2 = parameterTypes2[0];
                if (cls2 != ScriptRuntime.ScriptableClass && cls2 != ScriptRuntime.ScriptableObjectClass) {
                    str2 = "msg.setter2.parms";
                } else if (!z) {
                    str2 = "msg.setter1.parms";
                }
                str4 = str2;
            } else {
                str4 = "msg.setter.parms";
            }
            if (str4 != null) {
                throw Context.reportRuntimeError1(str4, method2.toString());
            }
            memberBox2 = memberBox3;
        }
        GetterSlot getterSlot = (GetterSlot) this.slotMap.get(str, 0, SlotAccess.MODIFY_GETTER_SETTER);
        getterSlot.setAttributes(i);
        getterSlot.getter = memberBox;
        getterSlot.setter = memberBox2;
    }

    public void defineOwnProperties(Context context, ScriptableObject scriptableObject) {
        Object[] ids = scriptableObject.getIds(false, true);
        ScriptableObject[] scriptableObjectArr = new ScriptableObject[ids.length];
        int length = ids.length;
        for (int i = 0; i < length; i++) {
            ScriptableObject scriptableObjectEnsureScriptableObject = ensureScriptableObject(ScriptRuntime.getObjectElem((Scriptable) scriptableObject, ids[i], context));
            checkPropertyDefinition(scriptableObjectEnsureScriptableObject);
            scriptableObjectArr[i] = scriptableObjectEnsureScriptableObject;
        }
        int length2 = ids.length;
        for (int i2 = 0; i2 < length2; i2++) {
            defineOwnProperty(context, ids[i2], scriptableObjectArr[i2]);
        }
    }

    public void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject) {
        checkPropertyDefinition(scriptableObject);
        defineOwnProperty(context, obj, scriptableObject, true);
    }

    protected void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject, boolean z) {
        int iApplyDescriptorToAttributeBitset;
        Slot slot = getSlot(context, obj, SlotAccess.QUERY);
        boolean z2 = slot == null;
        if (z) {
            checkPropertyChange(obj, slot == null ? null : slot.getPropertyDescriptor(context, this), scriptableObject);
        }
        boolean zIsAccessorDescriptor = isAccessorDescriptor(scriptableObject);
        if (slot == null) {
            slot = getSlot(context, obj, zIsAccessorDescriptor ? SlotAccess.MODIFY_GETTER_SETTER : SlotAccess.MODIFY);
            iApplyDescriptorToAttributeBitset = applyDescriptorToAttributeBitset(7, scriptableObject);
        } else {
            iApplyDescriptorToAttributeBitset = applyDescriptorToAttributeBitset(slot.getAttributes(), scriptableObject);
        }
        if (zIsAccessorDescriptor) {
            if (!(slot instanceof GetterSlot)) {
                slot = getSlot(context, obj, SlotAccess.MODIFY_GETTER_SETTER);
            }
            GetterSlot getterSlot = (GetterSlot) slot;
            Object property = getProperty(scriptableObject, "get");
            if (property != NOT_FOUND) {
                getterSlot.getter = property;
            }
            Object property2 = getProperty(scriptableObject, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
            if (property2 != NOT_FOUND) {
                getterSlot.setter = property2;
            }
            getterSlot.value = Undefined.instance;
            getterSlot.setAttributes(iApplyDescriptorToAttributeBitset);
            return;
        }
        if ((slot instanceof GetterSlot) && isDataDescriptor(scriptableObject)) {
            slot = getSlot(context, obj, SlotAccess.CONVERT_ACCESSOR_TO_DATA);
        }
        Object property3 = getProperty(scriptableObject, "value");
        if (property3 != NOT_FOUND) {
            slot.value = property3;
        } else if (z2) {
            slot.value = Undefined.instance;
        }
        slot.setAttributes(iApplyDescriptorToAttributeBitset);
    }

    protected void checkPropertyDefinition(ScriptableObject scriptableObject) {
        Object property = getProperty(scriptableObject, "get");
        if (property != NOT_FOUND && property != Undefined.instance && !(property instanceof Callable)) {
            throw ScriptRuntime.notFunctionError(property);
        }
        Object property2 = getProperty(scriptableObject, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
        if (property2 != NOT_FOUND && property2 != Undefined.instance && !(property2 instanceof Callable)) {
            throw ScriptRuntime.notFunctionError(property2);
        }
        if (isDataDescriptor(scriptableObject) && isAccessorDescriptor(scriptableObject)) {
            throw ScriptRuntime.typeError0("msg.both.data.and.accessor.desc");
        }
    }

    protected void checkPropertyChange(Object obj, ScriptableObject scriptableObject, ScriptableObject scriptableObject2) {
        if (scriptableObject == null) {
            if (!isExtensible()) {
                throw ScriptRuntime.typeError0("msg.not.extensible");
            }
            return;
        }
        if (isFalse(scriptableObject.get("configurable", scriptableObject))) {
            if (isTrue(getProperty(scriptableObject2, "configurable"))) {
                throw ScriptRuntime.typeError1("msg.change.configurable.false.to.true", obj);
            }
            if (isTrue(scriptableObject.get("enumerable", scriptableObject)) != isTrue(getProperty(scriptableObject2, "enumerable"))) {
                throw ScriptRuntime.typeError1("msg.change.enumerable.with.configurable.false", obj);
            }
            boolean zIsDataDescriptor = isDataDescriptor(scriptableObject2);
            boolean zIsAccessorDescriptor = isAccessorDescriptor(scriptableObject2);
            if (zIsDataDescriptor || zIsAccessorDescriptor) {
                if (zIsDataDescriptor && isDataDescriptor(scriptableObject)) {
                    if (isFalse(scriptableObject.get("writable", scriptableObject))) {
                        if (isTrue(getProperty(scriptableObject2, "writable"))) {
                            throw ScriptRuntime.typeError1("msg.change.writable.false.to.true.with.configurable.false", obj);
                        }
                        if (!sameValue(getProperty(scriptableObject2, "value"), scriptableObject.get("value", scriptableObject))) {
                            throw ScriptRuntime.typeError1("msg.change.value.with.writable.false", obj);
                        }
                        return;
                    }
                    return;
                }
                if (zIsAccessorDescriptor && isAccessorDescriptor(scriptableObject)) {
                    if (!sameValue(getProperty(scriptableObject2, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX), scriptableObject.get(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, scriptableObject))) {
                        throw ScriptRuntime.typeError1("msg.change.setter.with.configurable.false", obj);
                    }
                    if (!sameValue(getProperty(scriptableObject2, "get"), scriptableObject.get("get", scriptableObject))) {
                        throw ScriptRuntime.typeError1("msg.change.getter.with.configurable.false", obj);
                    }
                    return;
                }
                if (isDataDescriptor(scriptableObject)) {
                    throw ScriptRuntime.typeError1("msg.change.property.data.to.accessor.with.configurable.false", obj);
                }
                throw ScriptRuntime.typeError1("msg.change.property.accessor.to.data.with.configurable.false", obj);
            }
        }
    }

    protected static boolean isTrue(Object obj) {
        return obj != NOT_FOUND && ScriptRuntime.toBoolean(obj);
    }

    protected static boolean isFalse(Object obj) {
        return !isTrue(obj);
    }

    protected boolean sameValue(Object obj, Object obj2) {
        if (obj == NOT_FOUND) {
            return true;
        }
        if (obj2 == NOT_FOUND) {
            obj2 = Undefined.instance;
        }
        if ((obj2 instanceof Number) && (obj instanceof Number)) {
            double dDoubleValue = ((Number) obj2).doubleValue();
            double dDoubleValue2 = ((Number) obj).doubleValue();
            if (Double.isNaN(dDoubleValue) && Double.isNaN(dDoubleValue2)) {
                return true;
            }
            if (dDoubleValue == 0.0d && Double.doubleToLongBits(dDoubleValue) != Double.doubleToLongBits(dDoubleValue2)) {
                return false;
            }
        }
        return ScriptRuntime.shallowEq(obj2, obj);
    }

    protected int applyDescriptorToAttributeBitset(int i, ScriptableObject scriptableObject) {
        Object property = getProperty(scriptableObject, "enumerable");
        if (property != NOT_FOUND) {
            i = ScriptRuntime.toBoolean(property) ? i & (-3) : i | 2;
        }
        Object property2 = getProperty(scriptableObject, "writable");
        if (property2 != NOT_FOUND) {
            i = ScriptRuntime.toBoolean(property2) ? i & (-2) : i | 1;
        }
        Object property3 = getProperty(scriptableObject, "configurable");
        if (property3 != NOT_FOUND) {
            return ScriptRuntime.toBoolean(property3) ? i & (-5) : i | 4;
        }
        return i;
    }

    protected boolean isDataDescriptor(ScriptableObject scriptableObject) {
        return hasProperty(scriptableObject, "value") || hasProperty(scriptableObject, "writable");
    }

    protected boolean isAccessorDescriptor(ScriptableObject scriptableObject) {
        return hasProperty(scriptableObject, "get") || hasProperty(scriptableObject, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
    }

    protected boolean isGenericDescriptor(ScriptableObject scriptableObject) {
        return (isDataDescriptor(scriptableObject) || isAccessorDescriptor(scriptableObject)) ? false : true;
    }

    protected static Scriptable ensureScriptable(Object obj) {
        if (!(obj instanceof Scriptable)) {
            throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
        }
        return (Scriptable) obj;
    }

    protected static SymbolScriptable ensureSymbolScriptable(Object obj) {
        if (!(obj instanceof SymbolScriptable)) {
            throw ScriptRuntime.typeError1("msg.object.not.symbolscriptable", ScriptRuntime.typeof(obj));
        }
        return (SymbolScriptable) obj;
    }

    protected static ScriptableObject ensureScriptableObject(Object obj) {
        if (!(obj instanceof ScriptableObject)) {
            throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
        }
        return (ScriptableObject) obj;
    }

    public void defineFunctionProperties(String[] strArr, Class<?> cls, int i) {
        Method[] methodList = FunctionObject.getMethodList(cls);
        for (String str : strArr) {
            Method methodFindSingleMethod = FunctionObject.findSingleMethod(methodList, str);
            if (methodFindSingleMethod == null) {
                throw Context.reportRuntimeError2("msg.method.not.found", str, cls.getName());
            }
            defineProperty(str, new FunctionObject(str, methodFindSingleMethod, this), i);
        }
    }

    public static Scriptable getObjectPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Object);
    }

    public static Scriptable getFunctionPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Function);
    }

    public static Scriptable getGeneratorFunctionPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.GeneratorFunction);
    }

    public static Scriptable getArrayPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Array);
    }

    public static Scriptable getClassPrototype(Scriptable scriptable, String str) {
        Object prototypeProperty;
        Object property = getProperty(getTopLevelScope(scriptable), str);
        if (property instanceof BaseFunction) {
            prototypeProperty = ((BaseFunction) property).getPrototypeProperty();
        } else {
            if (property instanceof Scriptable) {
                Scriptable scriptable2 = (Scriptable) property;
                prototypeProperty = scriptable2.get("prototype", scriptable2);
            }
            return null;
        }
        if (prototypeProperty instanceof Scriptable) {
            return (Scriptable) prototypeProperty;
        }
        return null;
    }

    public static Scriptable getTopLevelScope(Scriptable scriptable) {
        while (true) {
            Scriptable parentScope = scriptable.getParentScope();
            if (parentScope == null) {
                return scriptable;
            }
            scriptable = parentScope;
        }
    }

    public boolean isExtensible() {
        return this.isExtensible;
    }

    public void preventExtensions() {
        this.isExtensible = false;
    }

    public void sealObject() {
        if (this.isSealed) {
            return;
        }
        long lock = this.slotMap.readLock();
        try {
            for (Slot slot : this.slotMap) {
                Object obj = slot.value;
                if (obj instanceof LazilyLoadedCtor) {
                    LazilyLoadedCtor lazilyLoadedCtor = (LazilyLoadedCtor) obj;
                    try {
                        lazilyLoadedCtor.init();
                        slot.value = lazilyLoadedCtor.getValue();
                    } catch (Throwable th) {
                        slot.value = lazilyLoadedCtor.getValue();
                        throw th;
                    }
                }
            }
            this.isSealed = true;
            this.slotMap.unlockRead(lock);
        } catch (Throwable th2) {
            this.slotMap.unlockRead(lock);
            throw th2;
        }
    }

    public final boolean isSealed() {
        return this.isSealed;
    }

    private void checkNotSealed(Object obj, int i) {
        if (isSealed()) {
            throw Context.reportRuntimeError1("msg.modify.sealed", obj != null ? obj.toString() : Integer.toString(i));
        }
    }

    public static Object getProperty(Scriptable scriptable, String str) {
        Object obj;
        Scriptable prototype = scriptable;
        do {
            obj = prototype.get(str, scriptable);
            if (obj != Scriptable.NOT_FOUND) {
                break;
            }
            prototype = prototype.getPrototype();
        } while (prototype != null);
        return obj;
    }

    public static Object getProperty(Scriptable scriptable, Symbol symbol) {
        Object obj;
        Scriptable prototype = scriptable;
        do {
            obj = ensureSymbolScriptable(prototype).get(symbol, scriptable);
            if (obj != Scriptable.NOT_FOUND) {
                break;
            }
            prototype = prototype.getPrototype();
        } while (prototype != null);
        return obj;
    }

    public static <T> T getTypedProperty(Scriptable scriptable, int i, Class<T> cls) {
        Object property = getProperty(scriptable, i);
        if (property == Scriptable.NOT_FOUND) {
            property = null;
        }
        return cls.cast(Context.jsToJava(property, cls));
    }

    public static Object getProperty(Scriptable scriptable, int i) {
        Object obj;
        Scriptable prototype = scriptable;
        do {
            obj = prototype.get(i, scriptable);
            if (obj != Scriptable.NOT_FOUND) {
                break;
            }
            prototype = prototype.getPrototype();
        } while (prototype != null);
        return obj;
    }

    public static <T> T getTypedProperty(Scriptable scriptable, String str, Class<T> cls) {
        Object property = getProperty(scriptable, str);
        if (property == Scriptable.NOT_FOUND) {
            property = null;
        }
        return cls.cast(Context.jsToJava(property, cls));
    }

    public static boolean hasProperty(Scriptable scriptable, String str) {
        return getBase(scriptable, str) != null;
    }

    public static void redefineProperty(Scriptable scriptable, String str, boolean z) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            return;
        }
        if ((base instanceof ConstProperties) && ((ConstProperties) base).isConst(str)) {
            throw ScriptRuntime.typeError1("msg.const.redecl", str);
        }
        if (z) {
            throw ScriptRuntime.typeError1("msg.var.redecl", str);
        }
    }

    public static boolean hasProperty(Scriptable scriptable, int i) {
        return getBase(scriptable, i) != null;
    }

    public static boolean hasProperty(Scriptable scriptable, Symbol symbol) {
        return getBase(scriptable, symbol) != null;
    }

    public static void putProperty(Scriptable scriptable, String str, Object obj) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            base = scriptable;
        }
        base.put(str, scriptable, obj);
    }

    public static void putProperty(Scriptable scriptable, Symbol symbol, Object obj) {
        Scriptable base = getBase(scriptable, symbol);
        if (base == null) {
            base = scriptable;
        }
        ensureSymbolScriptable(base).put(symbol, scriptable, obj);
    }

    public static void putConstProperty(Scriptable scriptable, String str, Object obj) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            base = scriptable;
        }
        if (base instanceof ConstProperties) {
            ((ConstProperties) base).putConst(str, scriptable, obj);
        }
    }

    public static void putProperty(Scriptable scriptable, int i, Object obj) {
        Scriptable base = getBase(scriptable, i);
        if (base == null) {
            base = scriptable;
        }
        base.put(i, scriptable, obj);
    }

    public static boolean deleteProperty(Scriptable scriptable, String str) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            return true;
        }
        base.delete(str);
        return !base.has(str, scriptable);
    }

    public static boolean deleteProperty(Scriptable scriptable, int i) {
        Scriptable base = getBase(scriptable, i);
        if (base == null) {
            return true;
        }
        base.delete(i);
        return !base.has(i, scriptable);
    }

    public static Object[] getPropertyIds(Scriptable scriptable) {
        if (scriptable == null) {
            return ScriptRuntime.emptyArgs;
        }
        Object[] ids = scriptable.getIds();
        ObjToIntMap objToIntMap = null;
        while (true) {
            scriptable = scriptable.getPrototype();
            if (scriptable == null) {
                break;
            }
            Object[] ids2 = scriptable.getIds();
            if (ids2.length != 0) {
                if (objToIntMap == null) {
                    if (ids.length == 0) {
                        ids = ids2;
                    } else {
                        objToIntMap = new ObjToIntMap(ids.length + ids2.length);
                        for (int i = 0; i != ids.length; i++) {
                            objToIntMap.intern(ids[i]);
                        }
                        ids = null;
                    }
                }
                for (int i2 = 0; i2 != ids2.length; i2++) {
                    objToIntMap.intern(ids2[i2]);
                }
            }
        }
        return objToIntMap != null ? objToIntMap.getKeys() : ids;
    }

    public static Object callMethod(Scriptable scriptable, String str, Object[] objArr) {
        return callMethod(null, scriptable, str, objArr);
    }

    public static Object callMethod(Context context, Scriptable scriptable, String str, Object[] objArr) {
        Object property = getProperty(scriptable, str);
        if (!(property instanceof Function)) {
            throw ScriptRuntime.notFunctionError(scriptable, str);
        }
        Function function = (Function) property;
        Scriptable topLevelScope = getTopLevelScope(scriptable);
        if (context != null) {
            return function.call(context, topLevelScope, scriptable, objArr);
        }
        return Context.call(null, function, topLevelScope, scriptable, objArr);
    }

    private static Scriptable getBase(Scriptable scriptable, String str) {
        while (!scriptable.has(str, scriptable) && (scriptable = scriptable.getPrototype()) != null) {
        }
        return scriptable;
    }

    private static Scriptable getBase(Scriptable scriptable, int i) {
        while (!scriptable.has(i, scriptable) && (scriptable = scriptable.getPrototype()) != null) {
        }
        return scriptable;
    }

    private static Scriptable getBase(Scriptable scriptable, Symbol symbol) {
        while (!ensureSymbolScriptable(scriptable).has(symbol, scriptable) && (scriptable = scriptable.getPrototype()) != null) {
        }
        return scriptable;
    }

    public final Object getAssociatedValue(Object obj) {
        Map<Object, Object> map = this.associatedValues;
        if (map == null) {
            return null;
        }
        return map.get(obj);
    }

    public static Object getTopScopeValue(Scriptable scriptable, Object obj) {
        Object associatedValue;
        Scriptable topLevelScope = getTopLevelScope(scriptable);
        do {
            if ((topLevelScope instanceof ScriptableObject) && (associatedValue = ((ScriptableObject) topLevelScope).getAssociatedValue(obj)) != null) {
                return associatedValue;
            }
            topLevelScope = topLevelScope.getPrototype();
        } while (topLevelScope != null);
        return null;
    }

    public final synchronized Object associateValue(Object obj, Object obj2) {
        Map map;
        try {
            if (obj2 == null) {
                throw new IllegalArgumentException();
            }
            map = this.associatedValues;
            if (map == null) {
                map = new HashMap();
                this.associatedValues = map;
            }
        } catch (Throwable th) {
            throw th;
        }
        return Kit.initHash(map, obj, obj2);
    }

    private boolean putImpl(Object obj, int i, Scriptable scriptable, Object obj2) {
        Slot slotQuery;
        if (this != scriptable) {
            slotQuery = this.slotMap.query(obj, i);
            if (!this.isExtensible && ((slotQuery == null || (!(slotQuery instanceof GetterSlot) && (slotQuery.getAttributes() & 1) != 0)) && Context.getContext().isStrictMode())) {
                throw ScriptRuntime.typeError0("msg.not.extensible");
            }
            if (slotQuery == null) {
                return false;
            }
        } else if (!this.isExtensible) {
            slotQuery = this.slotMap.query(obj, i);
            if ((slotQuery == null || !((slotQuery instanceof GetterSlot) || (slotQuery.getAttributes() & 1) == 0)) && Context.getContext().isStrictMode()) {
                throw ScriptRuntime.typeError0("msg.not.extensible");
            }
            if (slotQuery == null) {
                return true;
            }
        } else {
            if (this.isSealed) {
                checkNotSealed(obj, i);
            }
            slotQuery = this.slotMap.get(obj, i, SlotAccess.MODIFY);
        }
        return slotQuery.setValue(obj2, this, scriptable);
    }

    private boolean putConstImpl(String str, int i, Scriptable scriptable, Object obj, int i2) {
        Slot slotQuery;
        if (!this.isExtensible && Context.getContext().isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.not.extensible");
        }
        if (this != scriptable) {
            slotQuery = this.slotMap.query(str, i);
            if (slotQuery == null) {
                return false;
            }
        } else if (!isExtensible()) {
            slotQuery = this.slotMap.query(str, i);
            if (slotQuery == null) {
                return true;
            }
        } else {
            checkNotSealed(str, i);
            Slot slot = this.slotMap.get(str, i, SlotAccess.MODIFY_CONST);
            int attributes = slot.getAttributes();
            if ((attributes & 1) == 0) {
                throw Context.reportRuntimeError1("msg.var.redecl", str);
            }
            if ((attributes & 8) != 0) {
                slot.value = obj;
                if (i2 != 8) {
                    slot.setAttributes(attributes & (-9));
                }
            }
            return true;
        }
        return slotQuery.setValue(obj, this, scriptable);
    }

    private Slot findAttributeSlot(String str, int i, SlotAccess slotAccess) {
        Slot slot = this.slotMap.get(str, i, slotAccess);
        if (slot != null) {
            return slot;
        }
        if (str == null) {
            str = Integer.toString(i);
        }
        throw Context.reportRuntimeError1("msg.prop.not.found", str);
    }

    private Slot findAttributeSlot(Symbol symbol, SlotAccess slotAccess) {
        Slot slot = this.slotMap.get(symbol, 0, slotAccess);
        if (slot != null) {
            return slot;
        }
        throw Context.reportRuntimeError1("msg.prop.not.found", symbol);
    }

    Object[] getIds(boolean z, boolean z2) {
        Object[] objArr;
        ExternalArrayData externalArrayData = this.externalData;
        int arrayLength = externalArrayData == null ? 0 : externalArrayData.getArrayLength();
        if (arrayLength == 0) {
            objArr = ScriptRuntime.emptyArgs;
        } else {
            objArr = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                objArr[i] = Integer.valueOf(i);
            }
        }
        if (this.slotMap.isEmpty()) {
            return objArr;
        }
        long lock = this.slotMap.readLock();
        try {
            int i2 = arrayLength;
            for (Slot slot : this.slotMap) {
                if (z || (slot.getAttributes() & 2) == 0) {
                    if (z2 || !(slot.f1064name instanceof Symbol)) {
                        if (i2 == arrayLength) {
                            Object[] objArr2 = new Object[this.slotMap.dirtySize() + arrayLength];
                            if (objArr != null) {
                                System.arraycopy(objArr, 0, objArr2, 0, arrayLength);
                            }
                            objArr = objArr2;
                        }
                        int i3 = i2 + 1;
                        objArr[i2] = slot.f1064name != null ? slot.f1064name : Integer.valueOf(slot.indexOrHash);
                        i2 = i3;
                    }
                }
            }
            this.slotMap.unlockRead(lock);
            if (i2 != objArr.length + arrayLength) {
                Object[] objArr3 = new Object[i2];
                System.arraycopy(objArr, 0, objArr3, 0, i2);
                objArr = objArr3;
            }
            Context currentContext = Context.getCurrentContext();
            if (currentContext != null && currentContext.hasFeature(16)) {
                Arrays.sort(objArr, KEY_COMPARATOR);
            }
            return objArr;
        } catch (Throwable th) {
            this.slotMap.unlockRead(lock);
            throw th;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        long lock = this.slotMap.readLock();
        try {
            int iDirtySize = this.slotMap.dirtySize();
            if (iDirtySize == 0) {
                objectOutputStream.writeInt(0);
            } else {
                objectOutputStream.writeInt(iDirtySize);
                Iterator<Slot> it = this.slotMap.iterator();
                while (it.hasNext()) {
                    objectOutputStream.writeObject(it.next());
                }
            }
        } finally {
            this.slotMap.unlockRead(lock);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        this.slotMap = createSlotMap(i);
        for (int i2 = 0; i2 < i; i2++) {
            this.slotMap.addSlot((Slot) objectInputStream.readObject());
        }
    }

    protected ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        Slot slot = getSlot(context, obj, SlotAccess.QUERY);
        if (slot == null) {
            return null;
        }
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        return slot.getPropertyDescriptor(context, parentScope);
    }

    protected Slot getSlot(Context context, Object obj, SlotAccess slotAccess) {
        if (obj instanceof Symbol) {
            return this.slotMap.get(obj, 0, slotAccess);
        }
        ScriptRuntime.StringIdOrIndex stringIdOrIndex = ScriptRuntime.toStringIdOrIndex(context, obj);
        if (stringIdOrIndex.stringId == null) {
            return this.slotMap.get(null, stringIdOrIndex.index, slotAccess);
        }
        return this.slotMap.get(stringIdOrIndex.stringId, 0, slotAccess);
    }

    public int size() {
        return this.slotMap.size();
    }

    public boolean isEmpty() {
        return this.slotMap.isEmpty();
    }

    public Object get(Object obj) {
        Object obj2;
        if (obj instanceof String) {
            obj2 = get((String) obj, this);
        } else if (obj instanceof Symbol) {
            obj2 = get((Symbol) obj, this);
        } else {
            obj2 = obj instanceof Number ? get(((Number) obj).intValue(), this) : null;
        }
        if (obj2 == Scriptable.NOT_FOUND || obj2 == Undefined.instance) {
            return null;
        }
        return obj2 instanceof Wrapper ? ((Wrapper) obj2).unwrap() : obj2;
    }

    public static final class KeyComparator implements Comparator<Object>, Serializable {
        private static final long serialVersionUID = 6411335891523988149L;

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            int iIntValue;
            int iIntValue2;
            if (!(obj instanceof Integer)) {
                return obj2 instanceof Integer ? 1 : 0;
            }
            if (!(obj2 instanceof Integer) || (iIntValue = ((Integer) obj).intValue()) < (iIntValue2 = ((Integer) obj2).intValue())) {
                return -1;
            }
            return iIntValue > iIntValue2 ? 1 : 0;
        }
    }
}
