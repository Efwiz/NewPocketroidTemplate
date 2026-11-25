package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotatableTypeSystem extends TypeSystem {
    private boolean isAnnotationBasedNullAnalysisEnabled;

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public boolean isAnnotatedTypeSystem() {
        return true;
    }

    public AnnotatableTypeSystem(LookupEnvironment lookupEnvironment) {
        super(lookupEnvironment);
        this.environment = lookupEnvironment;
        this.isAnnotationBasedNullAnalysisEnabled = lookupEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public TypeBinding[] getAnnotatedTypes(TypeBinding typeBinding) {
        TypeBinding[] derivedTypes = getDerivedTypes(typeBinding);
        int length = derivedTypes.length;
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        int i = 0;
        for (TypeBinding typeBinding2 : derivedTypes) {
            if (typeBinding2 == null) {
                break;
            }
            if (typeBinding2.hasTypeAnnotations() && typeBinding2.f626id == typeBinding.f626id) {
                typeBindingArr[i] = typeBinding2;
                i++;
            }
        }
        if (i == length) {
            return typeBindingArr;
        }
        TypeBinding[] typeBindingArr2 = new TypeBinding[i];
        System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, i);
        return typeBindingArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public ArrayBinding getArrayType(TypeBinding typeBinding, int i, AnnotationBinding[] annotationBindingArr) {
        if (typeBinding instanceof ArrayBinding) {
            i += typeBinding.dimensions();
            AnnotationBinding[] typeAnnotations = typeBinding.getTypeAnnotations();
            typeBinding = typeBinding.leafComponentType();
            AnnotationBinding[] annotationBindingArr2 = new AnnotationBinding[typeAnnotations.length + annotationBindingArr.length + 1];
            System.arraycopy(annotationBindingArr, 0, annotationBindingArr2, 0, annotationBindingArr.length);
            System.arraycopy(typeAnnotations, 0, annotationBindingArr2, annotationBindingArr.length + 1, typeAnnotations.length);
            annotationBindingArr = annotationBindingArr2;
        }
        ArrayBinding arrayType = null;
        for (TypeBinding typeBinding2 : getDerivedTypes(typeBinding)) {
            if (typeBinding2 == null) {
                break;
            }
            if (typeBinding2.isArrayType() && typeBinding2.dimensions() == i && typeBinding2.leafComponentType() == typeBinding) {
                if (Util.effectivelyEqual(typeBinding2.getTypeAnnotations(), annotationBindingArr)) {
                    return (ArrayBinding) typeBinding2;
                }
                if (!typeBinding2.hasTypeAnnotations()) {
                    arrayType = (ArrayBinding) typeBinding2;
                }
            }
        }
        if (arrayType == null) {
            arrayType = super.getArrayType(typeBinding, i);
        }
        if (!haveTypeAnnotations(typeBinding, annotationBindingArr)) {
            return arrayType;
        }
        ArrayBinding arrayBinding = new ArrayBinding(typeBinding, i, this.environment);
        arrayBinding.f626id = arrayType.f626id;
        arrayBinding.setTypeAnnotations(annotationBindingArr, this.isAnnotationBasedNullAnalysisEnabled);
        return (ArrayBinding) cacheDerivedType(typeBinding, arrayType, arrayBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public ArrayBinding getArrayType(TypeBinding typeBinding, int i) {
        return getArrayType(typeBinding, i, Binding.NO_ANNOTATIONS);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public ReferenceBinding getMemberType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        if (!haveTypeAnnotations(referenceBinding, referenceBinding2)) {
            return super.getMemberType(referenceBinding, referenceBinding2);
        }
        return (ReferenceBinding) getAnnotatedType(referenceBinding, referenceBinding2, referenceBinding.getTypeAnnotations());
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public ParameterizedTypeBinding getParameterizedType(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2, AnnotationBinding[] annotationBindingArr) {
        if (referenceBinding.hasTypeAnnotations()) {
            throw new IllegalStateException();
        }
        ParameterizedTypeBinding parameterizedTypeBinding = this.parameterizedTypes.get(referenceBinding, typeBindingArr, referenceBinding2, annotationBindingArr);
        if (parameterizedTypeBinding != null) {
            return parameterizedTypeBinding;
        }
        ParameterizedTypeBinding parameterizedType = super.getParameterizedType(referenceBinding, typeBindingArr, referenceBinding2);
        if (!haveTypeAnnotations(referenceBinding, referenceBinding2, typeBindingArr, annotationBindingArr)) {
            return parameterizedType;
        }
        ParameterizedTypeBinding parameterizedTypeBinding2 = new ParameterizedTypeBinding(referenceBinding, typeBindingArr, referenceBinding2, this.environment);
        parameterizedTypeBinding2.f626id = parameterizedType.f626id;
        parameterizedTypeBinding2.setTypeAnnotations(annotationBindingArr, this.isAnnotationBasedNullAnalysisEnabled);
        this.parameterizedTypes.put(referenceBinding, typeBindingArr, referenceBinding2, parameterizedTypeBinding2);
        return (ParameterizedTypeBinding) cacheDerivedType(referenceBinding, parameterizedType, parameterizedTypeBinding2);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public ParameterizedTypeBinding getParameterizedType(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2) {
        return getParameterizedType(referenceBinding, typeBindingArr, referenceBinding2, Binding.NO_ANNOTATIONS);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public RawTypeBinding getRawType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, AnnotationBinding[] annotationBindingArr) {
        if (referenceBinding.hasTypeAnnotations()) {
            throw new IllegalStateException();
        }
        RawTypeBinding rawType = null;
        for (TypeBinding typeBinding : getDerivedTypes(referenceBinding)) {
            if (typeBinding == null) {
                break;
            }
            if (typeBinding.isRawType() && typeBinding.actualType() == referenceBinding && typeBinding.enclosingType() == referenceBinding2) {
                if (Util.effectivelyEqual(typeBinding.getTypeAnnotations(), annotationBindingArr)) {
                    return (RawTypeBinding) typeBinding;
                }
                if (!typeBinding.hasTypeAnnotations()) {
                    rawType = (RawTypeBinding) typeBinding;
                }
            }
        }
        if (rawType == null) {
            rawType = super.getRawType(referenceBinding, referenceBinding2);
        }
        if (!haveTypeAnnotations(referenceBinding, referenceBinding2, null, annotationBindingArr)) {
            return rawType;
        }
        RawTypeBinding rawTypeBinding = new RawTypeBinding(referenceBinding, referenceBinding2, this.environment);
        rawTypeBinding.f626id = rawType.f626id;
        rawTypeBinding.setTypeAnnotations(annotationBindingArr, this.isAnnotationBasedNullAnalysisEnabled);
        return (RawTypeBinding) cacheDerivedType(referenceBinding, rawType, rawTypeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public RawTypeBinding getRawType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        return getRawType(referenceBinding, referenceBinding2, Binding.NO_ANNOTATIONS);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public WildcardBinding getWildcard(ReferenceBinding referenceBinding, int i, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i2, AnnotationBinding[] annotationBindingArr) {
        if (referenceBinding == null) {
            referenceBinding = ReferenceBinding.LUB_GENERIC;
        }
        if (referenceBinding.hasTypeAnnotations()) {
            throw new IllegalStateException();
        }
        WildcardBinding wildcard = null;
        for (TypeBinding typeBinding2 : getDerivedTypes(referenceBinding)) {
            if (typeBinding2 == null) {
                break;
            }
            if (typeBinding2.isWildcard() && typeBinding2.actualType() == referenceBinding && typeBinding2.rank() == i && typeBinding2.boundKind() == i2 && typeBinding2.bound() == typeBinding && Util.effectivelyEqual(typeBinding2.additionalBounds(), typeBindingArr)) {
                if (Util.effectivelyEqual(typeBinding2.getTypeAnnotations(), annotationBindingArr)) {
                    return (WildcardBinding) typeBinding2;
                }
                if (!typeBinding2.hasTypeAnnotations()) {
                    wildcard = (WildcardBinding) typeBinding2;
                }
            }
        }
        if (wildcard == null) {
            wildcard = super.getWildcard(referenceBinding, i, typeBinding, typeBindingArr, i2);
        }
        WildcardBinding wildcardBinding = wildcard;
        if (!haveTypeAnnotations(referenceBinding, typeBinding, typeBindingArr, annotationBindingArr)) {
            return wildcardBinding;
        }
        WildcardBinding wildcardBinding2 = new WildcardBinding(referenceBinding, i, typeBinding, typeBindingArr, i2, this.environment);
        wildcardBinding2.f626id = wildcardBinding.f626id;
        wildcardBinding2.setTypeAnnotations(annotationBindingArr, this.isAnnotationBasedNullAnalysisEnabled);
        return (WildcardBinding) cacheDerivedType(referenceBinding, wildcardBinding, wildcardBinding2);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public WildcardBinding getWildcard(ReferenceBinding referenceBinding, int i, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i2) {
        return getWildcard(referenceBinding, i, typeBinding, typeBindingArr, i2, Binding.NO_ANNOTATIONS);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeSystem
    public TypeBinding getAnnotatedType(TypeBinding typeBinding, AnnotationBinding[][] annotationBindingArr) {
        if (typeBinding == null || !typeBinding.isValidBinding() || annotationBindingArr == null || annotationBindingArr.length == 0) {
            return typeBinding;
        }
        int iKind = typeBinding.kind();
        if (iKind != 4) {
            if (iKind == 68) {
                ArrayBinding arrayBinding = (ArrayBinding) typeBinding;
                return getArrayType(arrayBinding.leafComponentType, arrayBinding.dimensions, flattenedAnnotations(annotationBindingArr));
            }
            if (iKind != 132 && iKind != 260 && iKind != 516 && iKind != 1028 && iKind != 2052 && iKind != 4100 && iKind != 8196 && iKind != 32772) {
                throw new IllegalStateException();
            }
        }
        if (typeBinding.isUnresolvedType() && CharOperation.indexOf('$', typeBinding.sourceName()) > 0) {
            typeBinding = BinaryTypeBinding.resolveType(typeBinding, this.environment, true);
        }
        TypeBinding typeBinding2 = typeBinding;
        int iDepth = typeBinding2.depth();
        int i = iDepth + 1;
        TypeBinding[] typeBindingArr = new TypeBinding[i];
        typeBindingArr[iDepth] = typeBinding2;
        for (ReferenceBinding referenceBindingEnclosingType = typeBinding2.enclosingType(); referenceBindingEnclosingType != null; referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType()) {
            iDepth--;
            typeBindingArr[iDepth] = referenceBindingEnclosingType;
        }
        int length = annotationBindingArr.length;
        int i2 = i - length;
        int i3 = 0;
        while (i3 < length) {
            AnnotationBinding[] annotationBindingArr2 = annotationBindingArr[i3];
            if (annotationBindingArr2 != null && annotationBindingArr2.length > 0) {
                break;
            }
            i3++;
            i2++;
        }
        if (i3 == length || i2 < 0) {
            return typeBinding2;
        }
        TypeBinding typeBinding3 = null;
        TypeBinding annotatedType = i2 == 0 ? null : typeBindingArr[i2 - 1];
        while (i3 < length) {
            TypeBinding typeBinding4 = typeBindingArr[i2];
            AnnotationBinding[] typeAnnotations = annotationBindingArr[i3];
            if (typeAnnotations == null || typeAnnotations.length <= 0) {
                typeAnnotations = typeBinding4.getTypeAnnotations();
            }
            annotatedType = getAnnotatedType(typeBinding4, annotatedType, typeAnnotations);
            i3++;
            i2++;
            typeBinding3 = annotatedType;
        }
        return typeBinding3;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x012b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:76:0x0119  */
    /* JADX WARN: Code duplicated, block: B:79:0x0124  */
    /* JADX WARN: Code duplicated, block: B:81:0x012a  */
    /* JADX WARN: Code duplicated, block: B:85:0x0123 A[SYNTHETIC] */
    private TypeBinding getAnnotatedType(TypeBinding typeBinding, TypeBinding typeBinding2, AnnotationBinding[] annotationBindingArr) {
        TypeBinding typeBindingLeafComponentType;
        TypeBinding typeBinding3;
        if (typeBinding.kind() == 260) {
            return getParameterizedType(typeBinding.actualType(), typeBinding.typeArguments(), (ReferenceBinding) typeBinding2, annotationBindingArr);
        }
        TypeBinding[] derivedTypes = getDerivedTypes(typeBinding);
        int length = derivedTypes.length;
        TypeBinding unannotatedType = null;
        for (int i = 0; i < length && (typeBinding3 = derivedTypes[i]) != null; i++) {
            if (typeBinding3.enclosingType() == typeBinding2 && Util.effectivelyEqual(typeBinding3.typeArguments(), typeBinding.typeArguments())) {
                int iKind = typeBinding.kind();
                if (iKind != 68) {
                    if (iKind == 516) {
                        if (typeBinding3.isWildcard() && typeBinding3.actualType() == typeBinding.actualType() && typeBinding3.rank() == typeBinding.rank() && typeBinding3.boundKind() == typeBinding.boundKind() && typeBinding3.bound() == typeBinding.bound() && Util.effectivelyEqual(typeBinding3.additionalBounds(), typeBinding.additionalBounds())) {
                            if (Util.effectivelyEqual(typeBinding3.getTypeAnnotations(), annotationBindingArr)) {
                                return typeBinding3;
                            }
                            if (!typeBinding3.hasTypeAnnotations()) {
                                unannotatedType = typeBinding3;
                            }
                        }
                    } else if (iKind != 1028) {
                        if (iKind == 8196) {
                            if (typeBinding3.isWildcard()) {
                                continue;
                            }
                        } else {
                            int iKind2 = typeBinding3.kind();
                            if (iKind2 != 68 && iKind2 != 516 && iKind2 != 1028 && iKind2 != 8196 && iKind2 != 32772) {
                                if (Util.effectivelyEqual(typeBinding3.getTypeAnnotations(), annotationBindingArr)) {
                                    return typeBinding3;
                                }
                                if (!typeBinding3.hasTypeAnnotations()) {
                                    unannotatedType = typeBinding3;
                                }
                            }
                        }
                    } else if (typeBinding3.isRawType() && typeBinding3.actualType() == typeBinding.actualType()) {
                        if (Util.effectivelyEqual(typeBinding3.getTypeAnnotations(), annotationBindingArr)) {
                            return typeBinding3;
                        }
                        if (!typeBinding3.hasTypeAnnotations()) {
                            unannotatedType = typeBinding3;
                        }
                    }
                } else if (typeBinding3.isArrayType() && typeBinding3.dimensions() == typeBinding.dimensions() && typeBinding3.leafComponentType() == typeBinding.leafComponentType()) {
                    if (Util.effectivelyEqual(typeBinding3.getTypeAnnotations(), annotationBindingArr)) {
                        return typeBinding3;
                    }
                    if (!typeBinding3.hasTypeAnnotations()) {
                        unannotatedType = typeBinding3;
                    }
                }
            }
        }
        if (unannotatedType == null) {
            unannotatedType = getUnannotatedType(typeBinding);
        }
        if (!haveTypeAnnotations(typeBinding, typeBinding2, null, annotationBindingArr)) {
            return unannotatedType;
        }
        TypeBinding typeBindingClone = typeBinding.clone(typeBinding2);
        typeBindingClone.f626id = unannotatedType.f626id;
        typeBindingClone.setTypeAnnotations(annotationBindingArr, this.isAnnotationBasedNullAnalysisEnabled);
        if (this.isAnnotationBasedNullAnalysisEnabled && (typeBindingClone.tagBits & TagBits.AnnotationNullMASK) == 0) {
            typeBindingClone.tagBits |= TagBits.AnnotationNullMASK & typeBinding.tagBits;
        }
        int iKind3 = typeBinding.kind();
        if (iKind3 == 68) {
            typeBindingLeafComponentType = typeBinding.leafComponentType();
        } else {
            typeBindingLeafComponentType = (iKind3 == 516 || iKind3 == 1028) ? typeBinding.actualType() : unannotatedType;
        }
        return cacheDerivedType(typeBindingLeafComponentType, unannotatedType, typeBindingClone);
    }

    private boolean haveTypeAnnotations(TypeBinding typeBinding, TypeBinding typeBinding2, TypeBinding[] typeBindingArr, AnnotationBinding[] annotationBindingArr) {
        if (typeBinding != null && typeBinding.hasTypeAnnotations()) {
            return true;
        }
        if (typeBinding2 != null && typeBinding2.hasTypeAnnotations()) {
            return true;
        }
        int length = annotationBindingArr == null ? 0 : annotationBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (annotationBindingArr[i] != null) {
                return true;
            }
        }
        int length2 = typeBindingArr == null ? 0 : typeBindingArr.length;
        for (int i2 = 0; i2 < length2; i2++) {
            if (typeBindingArr[i2].hasTypeAnnotations()) {
                return true;
            }
        }
        return false;
    }

    private boolean haveTypeAnnotations(TypeBinding typeBinding, AnnotationBinding[] annotationBindingArr) {
        return haveTypeAnnotations(typeBinding, null, null, annotationBindingArr);
    }

    private boolean haveTypeAnnotations(TypeBinding typeBinding, TypeBinding typeBinding2) {
        return haveTypeAnnotations(typeBinding, typeBinding2, null, null);
    }

    static AnnotationBinding[] flattenedAnnotations(AnnotationBinding[][] annotationBindingArr) {
        if (annotationBindingArr == null || annotationBindingArr.length == 0) {
            return Binding.NO_ANNOTATIONS;
        }
        int length = annotationBindingArr.length;
        int length2 = length;
        for (int i = 0; i < length; i++) {
            AnnotationBinding[] annotationBindingArr2 = annotationBindingArr[i];
            length2 += annotationBindingArr2 == null ? 0 : annotationBindingArr2.length;
        }
        if (length2 == 0) {
            return Binding.NO_ANNOTATIONS;
        }
        AnnotationBinding[] annotationBindingArr3 = new AnnotationBinding[length2];
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            AnnotationBinding[] annotationBindingArr4 = annotationBindingArr[i2];
            int length3 = annotationBindingArr4 == null ? 0 : annotationBindingArr4.length;
            if (length3 > 0) {
                System.arraycopy(annotationBindingArr4, 0, annotationBindingArr3, i3, length3);
                i3 += length3;
            }
            annotationBindingArr3[i3] = null;
            i2++;
            i3++;
        }
        if (i3 == length2) {
            return annotationBindingArr3;
        }
        throw new IllegalStateException();
    }
}
