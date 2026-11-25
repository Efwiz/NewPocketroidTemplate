package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.ElementValuePair;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationMirrorImpl implements AnnotationMirror, InvocationHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final AnnotationBinding _binding;
    public final BaseProcessingEnvImpl _env;

    AnnotationMirrorImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, AnnotationBinding annotationBinding) {
        this._env = baseProcessingEnvImpl;
        this._binding = annotationBinding;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AnnotationMirrorImpl)) {
            if (obj == null) {
                return false;
            }
            return obj.equals(this);
        }
        AnnotationBinding annotationBinding = this._binding;
        if (annotationBinding == null) {
            return ((AnnotationMirrorImpl) obj)._binding == null;
        }
        return equals(annotationBinding, ((AnnotationMirrorImpl) obj)._binding);
    }

    private static boolean equals(AnnotationBinding annotationBinding, AnnotationBinding annotationBinding2) {
        if (annotationBinding.getAnnotationType() != annotationBinding2.getAnnotationType()) {
            return false;
        }
        ElementValuePair[] elementValuePairs = annotationBinding.getElementValuePairs();
        ElementValuePair[] elementValuePairs2 = annotationBinding2.getElementValuePairs();
        int length = elementValuePairs.length;
        if (length != elementValuePairs2.length) {
            return false;
        }
        for (ElementValuePair elementValuePair : elementValuePairs) {
            for (int i = 0; i < length; i++) {
                ElementValuePair elementValuePair2 = elementValuePairs2[i];
                if (elementValuePair.binding == elementValuePair2.binding) {
                    if (elementValuePair.value == null) {
                        if (elementValuePair2.value != null) {
                            return false;
                        }
                    } else {
                        if (elementValuePair2.value == null) {
                            return false;
                        }
                        if ((elementValuePair2.value instanceof Object[]) && (elementValuePair.value instanceof Object[])) {
                            if (!Arrays.equals((Object[]) elementValuePair.value, (Object[]) elementValuePair2.value)) {
                                return false;
                            }
                        } else if (!elementValuePair2.value.equals(elementValuePair.value)) {
                            return false;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public DeclaredType getAnnotationType() {
        return this._env.getFactory().newTypeMirror(this._binding.getAnnotationType());
    }

    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValues() {
        AnnotationBinding annotationBinding = this._binding;
        if (annotationBinding == null) {
            return Collections.emptyMap();
        }
        ElementValuePair[] elementValuePairs = annotationBinding.getElementValuePairs();
        LinkedHashMap linkedHashMap = new LinkedHashMap(elementValuePairs.length);
        for (ElementValuePair elementValuePair : elementValuePairs) {
            MethodBinding methodBinding = elementValuePair.getMethodBinding();
            if (methodBinding != null) {
                linkedHashMap.put(new ExecutableElementImpl(this._env, methodBinding), new AnnotationMemberValue(this._env, elementValuePair.getValue(), methodBinding));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValuesWithDefaults() {
        AnnotationBinding annotationBinding = this._binding;
        if (annotationBinding == null) {
            return Collections.emptyMap();
        }
        ElementValuePair[] elementValuePairs = annotationBinding.getElementValuePairs();
        ReferenceBinding annotationType = this._binding.getAnnotationType();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (MethodBinding methodBinding : annotationType.methods()) {
            int i = 0;
            while (true) {
                if (i < elementValuePairs.length) {
                    MethodBinding methodBinding2 = elementValuePairs[i].getMethodBinding();
                    if (methodBinding == methodBinding2) {
                        linkedHashMap.put(new ExecutableElementImpl(this._env, methodBinding2), new AnnotationMemberValue(this._env, elementValuePairs[i].getValue(), methodBinding2));
                        break;
                    }
                    i++;
                } else {
                    Object defaultValue = methodBinding.getDefaultValue();
                    if (defaultValue == null) {
                        break;
                    }
                    linkedHashMap.put(new ExecutableElementImpl(this._env, methodBinding), new AnnotationMemberValue(this._env, defaultValue, methodBinding));
                    break;
                }
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    public int hashCode() {
        AnnotationBinding annotationBinding = this._binding;
        return annotationBinding == null ? this._env.hashCode() : annotationBinding.hashCode();
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        Object defaultValue = null;
        if (this._binding == null) {
            return null;
        }
        String name2 = method.getName();
        boolean z = true;
        if (objArr == null || objArr.length == 0) {
            if (name2.equals("hashCode")) {
                return Integer.valueOf(hashCode());
            }
            if (name2.equals("toString")) {
                return toString();
            }
            if (name2.equals("annotationType")) {
                return obj.getClass().getInterfaces()[0];
            }
        } else if (objArr.length == 1 && name2.equals(Namer.EQUALS_METHOD_NAME)) {
            return Boolean.valueOf(equals(objArr[0]));
        }
        if (objArr != null && objArr.length != 0) {
            throw new NoSuchMethodException("method " + method.getName() + formatArgs(objArr) + " does not exist on annotation " + toString());
        }
        MethodBinding methodBinding = getMethodBinding(name2);
        if (methodBinding == null) {
            throw new NoSuchMethodException("method " + method.getName() + "() does not exist on annotation" + toString());
        }
        ElementValuePair[] elementValuePairs = this._binding.getElementValuePairs();
        int length = elementValuePairs.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            ElementValuePair elementValuePair = elementValuePairs[i];
            if (name2.equals(new String(elementValuePair.getName()))) {
                defaultValue = elementValuePair.getValue();
                break;
            }
            i++;
        }
        if (!z) {
            defaultValue = methodBinding.getDefaultValue();
        }
        return getReflectionValue(defaultValue, methodBinding.returnType, method.getReturnType());
    }

    public String toString() {
        DeclaredType annotationType = getAnnotationType();
        StringBuilder sb = new StringBuilder(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
        sb.append(annotationType.toString());
        Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = getElementValues();
        if (!elementValues.isEmpty()) {
            sb.append('(');
            boolean z = true;
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                if (!z) {
                    sb.append(", ");
                }
                sb.append((CharSequence) entry.getKey().getSimpleName());
                sb.append(" = ");
                sb.append(entry.getValue().toString());
                z = false;
            }
            sb.append(')');
        }
        return sb.toString();
    }

    private String formatArgs(Object[] objArr) {
        StringBuilder sb = new StringBuilder((objArr.length * 8) + 2);
        sb.append('(');
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(objArr[i].getClass().getName());
        }
        sb.append(')');
        return sb.toString();
    }

    private MethodBinding getMethodBinding(String str) {
        for (MethodBinding methodBinding : this._binding.getAnnotationType().getMethods(str.toCharArray())) {
            if (methodBinding.parameters.length == 0) {
                return methodBinding;
            }
        }
        return null;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.lang.model.type.MirroredTypeException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.lang.model.type.MirroredTypesException */
    private Object getReflectionValue(Object obj, TypeBinding typeBinding, Class<?> cls) throws MirroredTypesException, MirroredTypeException {
        if (cls == null) {
            return null;
        }
        if (obj == null) {
            return Factory.getMatchingDummyValue(cls);
        }
        if (cls.isArray()) {
            if (Class.class.equals(cls.getComponentType())) {
                if (!typeBinding.isArrayType() || !(obj instanceof Object[]) || ((ArrayBinding) typeBinding).leafComponentType.erasure().f626id != 16) {
                    return null;
                }
                Object[] objArr = (Object[]) obj;
                ArrayList arrayList = new ArrayList(objArr.length);
                for (int i = 0; i < objArr.length; i++) {
                    if (objArr[i] instanceof TypeBinding) {
                        arrayList.add(this._env.getFactory().newTypeMirror((TypeBinding) objArr[i]));
                    }
                }
                throw new MirroredTypesException(arrayList);
            }
            return convertJDTArrayToReflectionArray(obj, typeBinding, cls);
        }
        if (Class.class.equals(cls)) {
            if (obj instanceof TypeBinding) {
                throw new MirroredTypeException(this._env.getFactory().newTypeMirror((TypeBinding) obj));
            }
            return null;
        }
        return convertJDTValueToReflectionType(obj, typeBinding, cls);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0068  */
    private Object convertJDTArrayToReflectionArray(Object obj, TypeBinding typeBinding, Class<?> cls) {
        Object[] objArr;
        Object obj2;
        if (!typeBinding.isArrayType()) {
            return null;
        }
        if (obj != null && !(obj instanceof Object[])) {
            objArr = (Object[]) Array.newInstance(obj.getClass(), 1);
            objArr[0] = obj;
        } else {
            objArr = (Object[]) obj;
        }
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        Class<?> componentType = cls.getComponentType();
        int length = objArr.length;
        Object objNewInstance = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Object obj3 = objArr[i];
            if (componentType.isPrimitive() || String.class.equals(componentType)) {
                if (obj3 instanceof Constant) {
                    if (Boolean.TYPE.equals(componentType)) {
                        Array.setBoolean(objNewInstance, i, ((Constant) obj3).booleanValue());
                    } else if (Byte.TYPE.equals(componentType)) {
                        Array.setByte(objNewInstance, i, ((Constant) obj3).byteValue());
                    } else if (Character.TYPE.equals(componentType)) {
                        Array.setChar(objNewInstance, i, ((Constant) obj3).charValue());
                    } else if (Double.TYPE.equals(componentType)) {
                        Array.setDouble(objNewInstance, i, ((Constant) obj3).doubleValue());
                    } else if (Float.TYPE.equals(componentType)) {
                        Array.setFloat(objNewInstance, i, ((Constant) obj3).floatValue());
                    } else if (Integer.TYPE.equals(componentType)) {
                        Array.setInt(objNewInstance, i, ((Constant) obj3).intValue());
                    } else if (Long.TYPE.equals(componentType)) {
                        Array.setLong(objNewInstance, i, ((Constant) obj3).longValue());
                    } else if (Short.TYPE.equals(componentType)) {
                        Array.setShort(objNewInstance, i, ((Constant) obj3).shortValue());
                    } else if (String.class.equals(componentType)) {
                        Array.set(objNewInstance, i, ((Constant) obj3).stringValue());
                    }
                } else {
                    Factory.setArrayMatchingDummyValue(objNewInstance, i, componentType);
                }
            } else if (componentType.isEnum()) {
                if (typeBindingLeafComponentType != null && typeBindingLeafComponentType.isEnum() && (obj3 instanceof FieldBinding)) {
                    try {
                        Field field = componentType.getField(new String(((FieldBinding) obj3).f628name));
                        if (field != null) {
                            obj2 = field.get(null);
                        } else {
                            obj2 = null;
                        }
                    } catch (IllegalAccessException | NoSuchFieldException unused) {
                    }
                } else {
                    obj2 = null;
                }
                Array.set(objNewInstance, i, obj2);
            } else if (componentType.isAnnotation()) {
                Array.set(objNewInstance, i, (typeBindingLeafComponentType.isAnnotationType() && (obj3 instanceof AnnotationBinding)) ? Proxy.newProxyInstance(componentType.getClassLoader(), new Class[]{componentType}, (AnnotationMirrorImpl) this._env.getFactory().newAnnotationMirror((AnnotationBinding) obj3)) : null);
            } else {
                Array.set(objNewInstance, i, null);
            }
        }
        return objNewInstance;
    }

    private Object convertJDTValueToReflectionType(Object obj, TypeBinding typeBinding, Class<?> cls) {
        if (cls.isPrimitive() || String.class.equals(cls)) {
            if (obj instanceof Constant) {
                if (Boolean.TYPE.equals(cls)) {
                    return Boolean.valueOf(((Constant) obj).booleanValue());
                }
                if (Byte.TYPE.equals(cls)) {
                    return Byte.valueOf(((Constant) obj).byteValue());
                }
                if (Character.TYPE.equals(cls)) {
                    return Character.valueOf(((Constant) obj).charValue());
                }
                if (Double.TYPE.equals(cls)) {
                    return Double.valueOf(((Constant) obj).doubleValue());
                }
                if (Float.TYPE.equals(cls)) {
                    return Float.valueOf(((Constant) obj).floatValue());
                }
                if (Integer.TYPE.equals(cls)) {
                    return Integer.valueOf(((Constant) obj).intValue());
                }
                if (Long.TYPE.equals(cls)) {
                    return Long.valueOf(((Constant) obj).longValue());
                }
                if (Short.TYPE.equals(cls)) {
                    return Short.valueOf(((Constant) obj).shortValue());
                }
                if (String.class.equals(cls)) {
                    return ((Constant) obj).stringValue();
                }
            }
            return Factory.getMatchingDummyValue(cls);
        }
        Object obj2 = null;
        if (cls.isEnum()) {
            if (typeBinding != null && typeBinding.isEnum() && (obj instanceof FieldBinding)) {
                try {
                    Field field = cls.getField(new String(((FieldBinding) obj).f628name));
                    if (field != null) {
                        obj2 = field.get(null);
                    }
                } catch (IllegalAccessException | NoSuchFieldException unused) {
                }
            }
            return obj2 == null ? Factory.getMatchingDummyValue(cls) : obj2;
        }
        if (cls.isAnnotation()) {
            if (typeBinding.isAnnotationType() && (obj instanceof AnnotationBinding)) {
                return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, (AnnotationMirrorImpl) this._env.getFactory().newAnnotationMirror((AnnotationBinding) obj));
            }
            return null;
        }
        return Factory.getMatchingDummyValue(cls);
    }
}
