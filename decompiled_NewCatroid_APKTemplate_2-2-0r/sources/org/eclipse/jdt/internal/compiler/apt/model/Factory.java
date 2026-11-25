package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.ElementValuePair;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;

/* JADX INFO: loaded from: classes14.dex */
public class Factory {
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind;
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Byte DUMMY_BYTE = (byte) 0;
    public static final Character DUMMY_CHAR = Character.valueOf(ExternalAnnotationProvider.NULLABLE);
    public static final Double DUMMY_DOUBLE = Double.valueOf(0.0d);
    public static final Float DUMMY_FLOAT = Float.valueOf(0.0f);
    public static final Integer DUMMY_INTEGER = 0;
    public static final Long DUMMY_LONG = 0L;
    public static final Short DUMMY_SHORT = 0;
    public static List<? extends AnnotationMirror> EMPTY_ANNOTATION_MIRRORS = Collections.emptyList();
    private final BaseProcessingEnvImpl _env;

    static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind() {
        int[] iArr = $SWITCH_TABLE$javax$lang$model$element$ElementKind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[ElementKind.values().length];
        try {
            iArr2[ElementKind.ANNOTATION_TYPE.ordinal()] = 4;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[ElementKind.CLASS.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[ElementKind.CONSTRUCTOR.ordinal()] = 12;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[ElementKind.ENUM.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[ElementKind.ENUM_CONSTANT.ordinal()] = 6;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[ElementKind.EXCEPTION_PARAMETER.ordinal()] = 10;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[ElementKind.FIELD.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[ElementKind.INSTANCE_INIT.ordinal()] = 14;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[ElementKind.INTERFACE.ordinal()] = 5;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[ElementKind.LOCAL_VARIABLE.ordinal()] = 9;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[ElementKind.METHOD.ordinal()] = 11;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[ElementKind.OTHER.ordinal()] = 16;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            iArr2[ElementKind.PACKAGE.ordinal()] = 1;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            iArr2[ElementKind.PARAMETER.ordinal()] = 8;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            iArr2[ElementKind.RESOURCE_VARIABLE.ordinal()] = 17;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            iArr2[ElementKind.STATIC_INIT.ordinal()] = 13;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            iArr2[ElementKind.TYPE_PARAMETER.ordinal()] = 15;
        } catch (NoSuchFieldError unused17) {
        }
        $SWITCH_TABLE$javax$lang$model$element$ElementKind = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind() {
        int[] iArr = $SWITCH_TABLE$javax$lang$model$type$TypeKind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[TypeKind.values().length];
        try {
            iArr2[TypeKind.ARRAY.ordinal()] = 12;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[TypeKind.BOOLEAN.ordinal()] = 1;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[TypeKind.BYTE.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[TypeKind.CHAR.ordinal()] = 6;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[TypeKind.DECLARED.ordinal()] = 13;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[TypeKind.DOUBLE.ordinal()] = 8;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[TypeKind.ERROR.ordinal()] = 14;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[TypeKind.EXECUTABLE.ordinal()] = 18;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[TypeKind.FLOAT.ordinal()] = 7;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[TypeKind.INT.ordinal()] = 4;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[TypeKind.LONG.ordinal()] = 5;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[TypeKind.NONE.ordinal()] = 10;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            iArr2[TypeKind.NULL.ordinal()] = 11;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            iArr2[TypeKind.OTHER.ordinal()] = 19;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            iArr2[TypeKind.PACKAGE.ordinal()] = 17;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            iArr2[TypeKind.SHORT.ordinal()] = 3;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            iArr2[TypeKind.TYPEVAR.ordinal()] = 15;
        } catch (NoSuchFieldError unused17) {
        }
        try {
            iArr2[TypeKind.UNION.ordinal()] = 20;
        } catch (NoSuchFieldError unused18) {
        }
        try {
            iArr2[TypeKind.VOID.ordinal()] = 9;
        } catch (NoSuchFieldError unused19) {
        }
        try {
            iArr2[TypeKind.WILDCARD.ordinal()] = 16;
        } catch (NoSuchFieldError unused20) {
        }
        $SWITCH_TABLE$javax$lang$model$type$TypeKind = iArr2;
        return iArr2;
    }

    public Factory(BaseProcessingEnvImpl baseProcessingEnvImpl) {
        this._env = baseProcessingEnvImpl;
    }

    public List<? extends AnnotationMirror> getAnnotationMirrors(AnnotationBinding[] annotationBindingArr) {
        if (annotationBindingArr == null || annotationBindingArr.length == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(annotationBindingArr.length);
        for (AnnotationBinding annotationBinding : annotationBindingArr) {
            if (annotationBinding != null) {
                arrayList.add(newAnnotationMirror(annotationBinding));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public <A extends Annotation> A[] getAnnotationsByType(AnnotationBinding[] annotationBindingArr, Class<A> cls) {
        A[] aArr = (A[]) getAnnotations(annotationBindingArr, cls, false);
        return aArr == null ? (A[]) ((Annotation[]) Array.newInstance((Class<?>) cls, 0)) : aArr;
    }

    public <A extends Annotation> A getAnnotation(AnnotationBinding[] annotationBindingArr, Class<A> cls) {
        Annotation[] annotations = getAnnotations(annotationBindingArr, cls, true);
        if (annotations == null) {
            return null;
        }
        return (A) annotations[0];
    }

    private <A extends Annotation> A[] getAnnotations(AnnotationBinding[] annotationBindingArr, Class<A> cls, boolean z) {
        String name2;
        AnnotationMirrorImpl annotationMirrorImplCreateAnnotationMirror;
        if (annotationBindingArr == null || annotationBindingArr.length == 0 || cls == null || (name2 = cls.getName()) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(annotationBindingArr.length);
        for (AnnotationBinding annotationBinding : annotationBindingArr) {
            if (annotationBinding != null && (annotationMirrorImplCreateAnnotationMirror = createAnnotationMirror(name2, annotationBinding)) != null) {
                arrayList.add((Annotation) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, annotationMirrorImplCreateAnnotationMirror));
                if (z) {
                    break;
                }
            }
        }
        Annotation[] annotationArr = (Annotation[]) Array.newInstance((Class<?>) cls, arrayList.size());
        if (arrayList.size() > 0) {
            return (A[]) ((Annotation[]) arrayList.toArray(annotationArr));
        }
        return null;
    }

    private AnnotationMirrorImpl createAnnotationMirror(String str, AnnotationBinding annotationBinding) {
        char[] cArrConcatWith;
        ReferenceBinding annotationType = annotationBinding.getAnnotationType();
        if (annotationType == null || !annotationType.isAnnotationType()) {
            return null;
        }
        if (annotationType.isMemberType()) {
            str = str.replace('$', '.');
            cArrConcatWith = CharOperation.concatWith(annotationType.enclosingType().compoundName, annotationType.sourceName, '.');
            CharOperation.replace(cArrConcatWith, '$', '.');
        } else {
            cArrConcatWith = CharOperation.concatWith(annotationType.compoundName, '.');
        }
        if (str.equals(new String(cArrConcatWith))) {
            return (AnnotationMirrorImpl) this._env.getFactory().newAnnotationMirror(annotationBinding);
        }
        return null;
    }

    private static void appendModifier(Set<Modifier> set, int i, int i2, Modifier modifier) {
        if ((i & i2) != 0) {
            set.add(modifier);
        }
    }

    private static void decodeModifiers(Set<Modifier> set, int i, int[] iArr) {
        if (iArr == null) {
            return;
        }
        for (int i2 : iArr) {
            if (i2 == 1) {
                appendModifier(set, i, i2, Modifier.PUBLIC);
            } else if (i2 == 2) {
                appendModifier(set, i, i2, Modifier.PRIVATE);
            } else if (i2 == 4) {
                appendModifier(set, i, i2, Modifier.PROTECTED);
            } else if (i2 == 8) {
                appendModifier(set, i, i2, Modifier.STATIC);
            } else if (i2 == 16) {
                appendModifier(set, i, i2, Modifier.FINAL);
            } else if (i2 == 32) {
                appendModifier(set, i, i2, Modifier.SYNCHRONIZED);
            } else if (i2 == 64) {
                appendModifier(set, i, i2, Modifier.VOLATILE);
            } else if (i2 == 128) {
                appendModifier(set, i, i2, Modifier.TRANSIENT);
            } else if (i2 == 256) {
                appendModifier(set, i, i2, Modifier.NATIVE);
            } else if (i2 == 1024) {
                appendModifier(set, i, i2, Modifier.ABSTRACT);
            } else if (i2 == 2048) {
                appendModifier(set, i, i2, Modifier.STRICTFP);
            } else if (i2 == 65536) {
                try {
                    appendModifier(set, i, i2, Modifier.valueOf("DEFAULT"));
                } catch (IllegalArgumentException unused) {
                }
            }
        }
    }

    public static Object getMatchingDummyValue(Class<?> cls) {
        if (!cls.isPrimitive()) {
            return null;
        }
        if (cls == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (cls == Byte.TYPE) {
            return DUMMY_BYTE;
        }
        if (cls == Character.TYPE) {
            return DUMMY_CHAR;
        }
        if (cls == Double.TYPE) {
            return DUMMY_DOUBLE;
        }
        if (cls == Float.TYPE) {
            return DUMMY_FLOAT;
        }
        if (cls == Integer.TYPE) {
            return DUMMY_INTEGER;
        }
        if (cls == Long.TYPE) {
            return DUMMY_LONG;
        }
        if (cls == Short.TYPE) {
            return DUMMY_SHORT;
        }
        return DUMMY_INTEGER;
    }

    public TypeMirror getReceiverType(MethodBinding methodBinding) {
        if (methodBinding != null) {
            if (methodBinding.receiver != null) {
                return this._env.getFactory().newTypeMirror(methodBinding.receiver);
            }
            if (methodBinding.declaringClass != null && !methodBinding.isStatic() && (!methodBinding.isConstructor() || methodBinding.declaringClass.isMemberType())) {
                return this._env.getFactory().newTypeMirror(methodBinding.declaringClass);
            }
        }
        return NoTypeImpl.NO_TYPE_NONE;
    }

    public static Set<Modifier> getModifiers(int i, ElementKind elementKind) {
        return getModifiers(i, elementKind, false);
    }

    public static Set<Modifier> getModifiers(int i, ElementKind elementKind, boolean z) {
        EnumSet enumSetNoneOf = EnumSet.noneOf(Modifier.class);
        int i2 = $SWITCH_TABLE$javax$lang$model$element$ElementKind()[elementKind.ordinal()];
        if (i2 == 11 || i2 == 12) {
            decodeModifiers(enumSetNoneOf, i, new int[]{1, 4, 2, 1024, 8, 16, 32, 256, 2048, 65536});
        } else {
            switch (i2) {
                case 2:
                    if (z) {
                        decodeModifiers(enumSetNoneOf, i, new int[]{1, 4, 16, 2, 1024, 8, 2048});
                    } else {
                        decodeModifiers(enumSetNoneOf, i, new int[]{1, 4, 16, 2, 8, 2048});
                    }
                    break;
                case 3:
                case 4:
                case 5:
                    decodeModifiers(enumSetNoneOf, i, new int[]{1, 4, 1024, 16, 2, 8, 2048});
                    break;
                case 6:
                case 7:
                    decodeModifiers(enumSetNoneOf, i, new int[]{1, 4, 2, 8, 16, 128, 64});
                    break;
            }
        }
        return Collections.unmodifiableSet(enumSetNoneOf);
    }

    public AnnotationMirror newAnnotationMirror(AnnotationBinding annotationBinding) {
        return new AnnotationMirrorImpl(this._env, annotationBinding);
    }

    public Element newElement(Binding binding, ElementKind elementKind) {
        if (binding == null) {
            return null;
        }
        int iKind = binding.kind();
        if (iKind == 1 || iKind == 2 || iKind == 3) {
            return new VariableElementImpl(this._env, (VariableBinding) binding);
        }
        if (iKind != 4) {
            switch (iKind) {
                case 8:
                    return new ExecutableElementImpl(this._env, (MethodBinding) binding);
                case 16:
                    return new PackageElementImpl(this._env, (PackageBinding) binding);
                case 32:
                case 68:
                case 132:
                case 516:
                case 8196:
                    throw new UnsupportedOperationException("NYI: binding type " + binding.kind());
                case 260:
                case 1028:
                    return new TypeElementImpl(this._env, ((ParameterizedTypeBinding) binding).genericType(), elementKind);
                case Binding.GENERIC_TYPE /* 2052 */:
                    break;
                case 4100:
                    return new TypeParameterElementImpl(this._env, (TypeVariableBinding) binding);
                default:
                    return null;
            }
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) binding;
        if ((referenceBinding.tagBits & 128) != 0) {
            return new ErrorTypeElement(this._env, referenceBinding);
        }
        if (CharOperation.equals(referenceBinding.sourceName, TypeConstants.PACKAGE_INFO_NAME)) {
            return new PackageElementImpl(this._env, referenceBinding.fPackage);
        }
        return new TypeElementImpl(this._env, referenceBinding, elementKind);
    }

    public Element newElement(Binding binding) {
        return newElement(binding, null);
    }

    public PackageElement newPackageElement(PackageBinding packageBinding) {
        return new PackageElementImpl(this._env, packageBinding);
    }

    public NullType getNullType() {
        return NoTypeImpl.NULL_TYPE;
    }

    public NoType getNoType(TypeKind typeKind) {
        int i = $SWITCH_TABLE$javax$lang$model$type$TypeKind()[typeKind.ordinal()];
        if (i == 9) {
            return NoTypeImpl.NO_TYPE_VOID;
        }
        if (i == 10) {
            return NoTypeImpl.NO_TYPE_NONE;
        }
        if (i == 17) {
            return NoTypeImpl.NO_TYPE_PACKAGE;
        }
        throw new IllegalArgumentException();
    }

    public PrimitiveTypeImpl getPrimitiveType(TypeKind typeKind) {
        switch ($SWITCH_TABLE$javax$lang$model$type$TypeKind()[typeKind.ordinal()]) {
            case 1:
                return PrimitiveTypeImpl.BOOLEAN;
            case 2:
                return PrimitiveTypeImpl.BYTE;
            case 3:
                return PrimitiveTypeImpl.SHORT;
            case 4:
                return PrimitiveTypeImpl.INT;
            case 5:
                return PrimitiveTypeImpl.LONG;
            case 6:
                return PrimitiveTypeImpl.CHAR;
            case 7:
                return PrimitiveTypeImpl.FLOAT;
            case 8:
                return PrimitiveTypeImpl.DOUBLE;
            default:
                throw new IllegalArgumentException();
        }
    }

    public PrimitiveTypeImpl getPrimitiveType(BaseTypeBinding baseTypeBinding) {
        AnnotationBinding[] typeAnnotations = baseTypeBinding.getTypeAnnotations();
        if (typeAnnotations == null || typeAnnotations.length == 0) {
            return getPrimitiveType(PrimitiveTypeImpl.getKind(baseTypeBinding));
        }
        return new PrimitiveTypeImpl(this._env, baseTypeBinding);
    }

    public TypeMirror newTypeMirror(Binding binding) {
        int iKind = binding.kind();
        if (iKind == 1 || iKind == 2 || iKind == 3) {
            return newTypeMirror(((VariableBinding) binding).type);
        }
        if (iKind != 4) {
            switch (iKind) {
                case 8:
                    return new ExecutableTypeImpl(this._env, (MethodBinding) binding);
                case 16:
                    return getNoType(TypeKind.PACKAGE);
                case 32:
                    throw new UnsupportedOperationException("NYI: import type " + binding.kind());
                case 68:
                    return new ArrayTypeImpl(this._env, (ArrayBinding) binding);
                case 132:
                    BaseTypeBinding baseTypeBinding = (BaseTypeBinding) binding;
                    int i = baseTypeBinding.f626id;
                    if (i == 6) {
                        return getNoType(TypeKind.VOID);
                    }
                    if (i == 12) {
                        return getNullType();
                    }
                    return getPrimitiveType(baseTypeBinding);
                case 260:
                case 1028:
                case Binding.GENERIC_TYPE /* 2052 */:
                    break;
                case 516:
                case 8196:
                    return new WildcardTypeImpl(this._env, (WildcardBinding) binding);
                case 4100:
                    return new TypeVariableImpl(this._env, (TypeVariableBinding) binding);
                default:
                    return null;
            }
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) binding;
        if ((referenceBinding.tagBits & 128) != 0) {
            return getErrorType(referenceBinding);
        }
        return new DeclaredTypeImpl(this._env, referenceBinding);
    }

    public TypeParameterElement newTypeParameterElement(TypeVariableBinding typeVariableBinding, Element element) {
        return new TypeParameterElementImpl(this._env, typeVariableBinding, element);
    }

    public ErrorType getErrorType(ReferenceBinding referenceBinding) {
        return new ErrorTypeImpl(this._env, referenceBinding);
    }

    public static Object performNecessaryPrimitiveTypeConversion(Class<?> cls, Object obj, boolean z) {
        if (obj == null) {
            if (z) {
                return getMatchingDummyValue(cls);
            }
            return null;
        }
        String name2 = cls.getName();
        char cCharAt = name2.charAt(0);
        int length = name2.length();
        if (obj instanceof Byte) {
            byte bByteValue = ((Byte) obj).byteValue();
            if (cCharAt == 'f') {
                return new Float(bByteValue);
            }
            if (cCharAt == 'i') {
                return Integer.valueOf(bByteValue);
            }
            if (cCharAt == 'l') {
                return Long.valueOf(bByteValue);
            }
            if (cCharAt != 's') {
                switch (cCharAt) {
                    case 'b':
                        return (length != 4 && z) ? Boolean.FALSE : obj;
                    case 'c':
                        return Character.valueOf((char) bByteValue);
                    case 'd':
                        return new Double(bByteValue);
                    default:
                        throw new IllegalStateException("unknown type " + cCharAt);
                }
            }
            return Short.valueOf(bByteValue);
        }
        if (obj instanceof Short) {
            short sShortValue = ((Short) obj).shortValue();
            if (cCharAt == 'f') {
                return new Float(sShortValue);
            }
            if (cCharAt == 'i') {
                return Integer.valueOf(sShortValue);
            }
            if (cCharAt == 'l') {
                return Long.valueOf(sShortValue);
            }
            if (cCharAt == 's') {
                return obj;
            }
            switch (cCharAt) {
                case 'b':
                    if (length == 4) {
                        return Byte.valueOf((byte) sShortValue);
                    }
                    return z ? Boolean.FALSE : obj;
                case 'c':
                    return Character.valueOf((char) sShortValue);
                case 'd':
                    return new Double(sShortValue);
                default:
                    throw new IllegalStateException("unknown type " + cCharAt);
            }
        }
        if (obj instanceof Character) {
            char cCharValue = ((Character) obj).charValue();
            if (cCharAt == 'f') {
                return new Float(cCharValue);
            }
            if (cCharAt == 'i') {
                return Integer.valueOf(cCharValue);
            }
            if (cCharAt == 'l') {
                return Long.valueOf(cCharValue);
            }
            if (cCharAt != 's') {
                switch (cCharAt) {
                    case 'b':
                        if (length == 4) {
                            return Byte.valueOf((byte) cCharValue);
                        }
                        return z ? Boolean.FALSE : obj;
                    case 'c':
                        return obj;
                    case 'd':
                        return new Double(cCharValue);
                    default:
                        throw new IllegalStateException("unknown type " + cCharAt);
                }
            }
            return Short.valueOf((short) cCharValue);
        }
        if (obj instanceof Integer) {
            int iIntValue = ((Integer) obj).intValue();
            if (cCharAt == 'f') {
                return new Float(iIntValue);
            }
            if (cCharAt == 'i') {
                return obj;
            }
            if (cCharAt == 'l') {
                return Long.valueOf(iIntValue);
            }
            if (cCharAt != 's') {
                switch (cCharAt) {
                    case 'b':
                        if (length == 4) {
                            return Byte.valueOf((byte) iIntValue);
                        }
                        return z ? Boolean.FALSE : obj;
                    case 'c':
                        return Character.valueOf((char) iIntValue);
                    case 'd':
                        return new Double(iIntValue);
                    default:
                        throw new IllegalStateException("unknown type " + cCharAt);
                }
            }
            return Short.valueOf((short) iIntValue);
        }
        if (obj instanceof Long) {
            long jLongValue = ((Long) obj).longValue();
            if (cCharAt == 'f') {
                return new Float(jLongValue);
            }
            if (cCharAt != 'i') {
                if (cCharAt == 'l') {
                    return obj;
                }
                if (cCharAt != 's') {
                    switch (cCharAt) {
                        case 'b':
                        case 'c':
                            break;
                        case 'd':
                            return new Double(jLongValue);
                        default:
                            throw new IllegalStateException("unknown type " + cCharAt);
                    }
                }
            }
            return z ? getMatchingDummyValue(cls) : obj;
        }
        if (obj instanceof Float) {
            float fFloatValue = ((Float) obj).floatValue();
            if (cCharAt == 'f') {
                return obj;
            }
            if (cCharAt != 'i' && cCharAt != 'l' && cCharAt != 's') {
                switch (cCharAt) {
                    case 'b':
                    case 'c':
                        break;
                    case 'd':
                        return new Double(fFloatValue);
                    default:
                        throw new IllegalStateException("unknown type " + cCharAt);
                }
            }
            return z ? getMatchingDummyValue(cls) : obj;
        }
        if (obj instanceof Double) {
            return (cCharAt != 'd' && z) ? getMatchingDummyValue(cls) : obj;
        }
        if (obj instanceof Boolean) {
            return (!(cCharAt == 'b' && length == 7) && z) ? getMatchingDummyValue(cls) : obj;
        }
        return z ? getMatchingDummyValue(cls) : obj;
    }

    public static void setArrayMatchingDummyValue(Object obj, int i, Class<?> cls) {
        if (Boolean.TYPE.equals(cls)) {
            Array.setBoolean(obj, i, false);
            return;
        }
        if (Byte.TYPE.equals(cls)) {
            Array.setByte(obj, i, DUMMY_BYTE.byteValue());
            return;
        }
        if (Character.TYPE.equals(cls)) {
            Array.setChar(obj, i, DUMMY_CHAR.charValue());
            return;
        }
        if (Double.TYPE.equals(cls)) {
            Array.setDouble(obj, i, DUMMY_DOUBLE.doubleValue());
            return;
        }
        if (Float.TYPE.equals(cls)) {
            Array.setFloat(obj, i, DUMMY_FLOAT.floatValue());
            return;
        }
        if (Integer.TYPE.equals(cls)) {
            Array.setInt(obj, i, DUMMY_INTEGER.intValue());
            return;
        }
        if (Long.TYPE.equals(cls)) {
            Array.setLong(obj, i, DUMMY_LONG.longValue());
        } else if (Short.TYPE.equals(cls)) {
            Array.setShort(obj, i, DUMMY_SHORT.shortValue());
        } else {
            Array.set(obj, i, null);
        }
    }

    public static AnnotationBinding[] getPackedAnnotationBindings(AnnotationBinding[] annotationBindingArr) {
        ReferenceBinding referenceBindingContainerAnnotationType;
        MethodBinding[] methods;
        int length = annotationBindingArr == null ? 0 : annotationBindingArr.length;
        if (length == 0) {
            return annotationBindingArr;
        }
        AnnotationBinding[] annotationBindingArr2 = annotationBindingArr;
        for (int i = 0; i < length; i++) {
            AnnotationBinding annotationBinding = annotationBindingArr2[i];
            if (annotationBinding != null) {
                ReferenceBinding annotationType = annotationBinding.getAnnotationType();
                if (annotationType.isRepeatableAnnotationType() && (referenceBindingContainerAnnotationType = annotationType.containerAnnotationType()) != null && (methods = referenceBindingContainerAnnotationType.getMethods(TypeConstants.VALUE)) != null && methods.length == 1) {
                    MethodBinding methodBinding = methods[0];
                    if (methodBinding.returnType != null && methodBinding.returnType.dimensions() == 1 && !TypeBinding.notEquals(methodBinding.returnType.leafComponentType(), annotationType)) {
                        ArrayList arrayList = null;
                        for (int i2 = i + 1; i2 < length; i2++) {
                            AnnotationBinding annotationBinding2 = annotationBindingArr2[i2];
                            if (annotationBinding2 != null && annotationBinding2.getAnnotationType() == annotationType) {
                                if (annotationBindingArr2 == annotationBindingArr) {
                                    AnnotationBinding[] annotationBindingArr3 = new AnnotationBinding[length];
                                    System.arraycopy(annotationBindingArr2, 0, annotationBindingArr3, 0, length);
                                    annotationBindingArr2 = annotationBindingArr3;
                                }
                                annotationBindingArr2[i2] = null;
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                    arrayList.add(annotationBinding);
                                }
                                arrayList.add(annotationBinding2);
                            }
                        }
                        if (arrayList != null) {
                            annotationBindingArr2[i] = new AnnotationBinding(referenceBindingContainerAnnotationType, new ElementValuePair[]{new ElementValuePair(TypeConstants.VALUE, arrayList.toArray(), methodBinding)});
                        }
                    }
                }
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (annotationBindingArr2[i4] != null) {
                i3++;
            }
        }
        if (annotationBindingArr2 == annotationBindingArr && i3 == length) {
            return annotationBindingArr;
        }
        AnnotationBinding[] annotationBindingArr4 = new AnnotationBinding[i3];
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            AnnotationBinding annotationBinding3 = annotationBindingArr2[i6];
            if (annotationBinding3 != null) {
                annotationBindingArr4[i5] = annotationBinding3;
                i5++;
            }
        }
        return annotationBindingArr4;
    }

    public static AnnotationBinding[] getUnpackedAnnotationBindings(AnnotationBinding[] annotationBindingArr) {
        TypeBinding typeBindingLeafComponentType;
        int length = annotationBindingArr == null ? 0 : annotationBindingArr.length;
        if (length == 0) {
            return annotationBindingArr;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < length; i++) {
            AnnotationBinding annotationBinding = annotationBindingArr[i];
            if (annotationBinding != null) {
                arrayList.add(annotationBinding);
                ReferenceBinding annotationType = annotationBinding.getAnnotationType();
                MethodBinding[] methods = annotationType.getMethods(TypeConstants.VALUE);
                if (methods != null && methods.length == 1) {
                    MethodBinding methodBinding = methods[0];
                    if (methodBinding.returnType.dimensions() == 1 && (typeBindingLeafComponentType = methodBinding.returnType.leafComponentType()) != null && typeBindingLeafComponentType.isAnnotationType() && typeBindingLeafComponentType.isRepeatableAnnotationType() && typeBindingLeafComponentType.containerAnnotationType() == annotationType) {
                        for (ElementValuePair elementValuePair : annotationBinding.getElementValuePairs()) {
                            if (CharOperation.equals(elementValuePair.getName(), TypeConstants.VALUE)) {
                                for (Object obj : (Object[]) elementValuePair.getValue()) {
                                    arrayList.add((AnnotationBinding) obj);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return (AnnotationBinding[]) arrayList.toArray(new AnnotationBinding[arrayList.size()]);
    }
}
