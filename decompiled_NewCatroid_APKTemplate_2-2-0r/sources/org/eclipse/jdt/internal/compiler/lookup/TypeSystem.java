package org.eclipse.jdt.internal.compiler.lookup;

import java.util.HashMap;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.util.SimpleLookupTable;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class TypeSystem {
    LookupEnvironment environment;
    private int typeid;
    private SimpleLookupTable annotationTypes = new SimpleLookupTable(16);
    private TypeBinding[][] types = new TypeBinding[256][];
    protected HashedParameterizedTypes parameterizedTypes = new HashedParameterizedTypes();

    public TypeBinding getAnnotatedType(TypeBinding typeBinding, AnnotationBinding[][] annotationBindingArr) {
        return typeBinding;
    }

    public ReferenceBinding getMemberType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        return referenceBinding;
    }

    public boolean isAnnotatedTypeSystem() {
        return false;
    }

    public final class HashedParameterizedTypes {
        HashMap<ParameterizedTypeBinding, ParameterizedTypeBinding[]> hashedParameterizedTypes = new HashMap<>(256);

        public HashedParameterizedTypes() {
        }

        private final class InternalParameterizedTypeBinding extends ParameterizedTypeBinding {
            public InternalParameterizedTypeBinding(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2, LookupEnvironment lookupEnvironment) {
                super(referenceBinding, typeBindingArr, referenceBinding2, lookupEnvironment);
            }

            public boolean equals(Object obj) {
                ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) obj;
                return this.type == parameterizedTypeBinding.type && this.enclosingType == parameterizedTypeBinding.enclosingType && Util.effectivelyEqual(this.arguments, parameterizedTypeBinding.arguments);
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
            public int hashCode() {
                int i = 0;
                int iHashCode = this.type.hashCode() + ((this.enclosingType != null ? this.enclosingType.hashCode() : 0) * 13);
                int length = this.arguments == null ? 0 : this.arguments.length;
                while (i < length) {
                    int i2 = i + 1;
                    iHashCode += this.arguments[i].f626id * i2 * this.arguments[i].hashCode();
                    i = i2;
                }
                return iHashCode;
            }
        }

        ParameterizedTypeBinding get(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2, AnnotationBinding[] annotationBindingArr) {
            ReferenceBinding referenceBinding3;
            TypeBinding[] typeBindingArr2;
            ReferenceBinding referenceBinding4 = (ReferenceBinding) TypeSystem.this.getUnannotatedType(referenceBinding);
            int length = typeBindingArr == null ? 0 : typeBindingArr.length;
            TypeBinding[] typeBindingArr3 = typeBindingArr == null ? null : new TypeBinding[length];
            for (int i = 0; i < length; i++) {
                typeBindingArr3[i] = TypeSystem.this.getUnannotatedType(typeBindingArr[i]);
            }
            ReferenceBinding referenceBinding5 = referenceBinding2 == null ? null : (ReferenceBinding) TypeSystem.this.getUnannotatedType(referenceBinding2);
            InternalParameterizedTypeBinding internalParameterizedTypeBinding = new InternalParameterizedTypeBinding(referenceBinding4, typeBindingArr3, referenceBinding5, TypeSystem.this.environment);
            if (TypeSystem.this instanceof AnnotatableTypeSystem) {
                typeBindingArr2 = typeBindingArr;
                referenceBinding5 = referenceBinding2;
                referenceBinding3 = referenceBinding;
            } else {
                referenceBinding3 = referenceBinding4;
                typeBindingArr2 = typeBindingArr3;
            }
            ParameterizedTypeBinding[] parameterizedTypeBindingArr = this.hashedParameterizedTypes.get(internalParameterizedTypeBinding);
            int length2 = parameterizedTypeBindingArr == null ? 0 : parameterizedTypeBindingArr.length;
            for (int i2 = 0; i2 < length2; i2++) {
                ParameterizedTypeBinding parameterizedTypeBinding = parameterizedTypeBindingArr[i2];
                if (parameterizedTypeBinding.actualType() == referenceBinding3 && parameterizedTypeBinding.enclosingType() == referenceBinding5 && Util.effectivelyEqual(parameterizedTypeBinding.typeArguments(), typeBindingArr2)) {
                    if (Util.effectivelyEqual(annotationBindingArr, parameterizedTypeBinding.getTypeAnnotations())) {
                        return parameterizedTypeBinding;
                    }
                }
            }
            return null;
        }

        void put(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2, ParameterizedTypeBinding parameterizedTypeBinding) {
            ParameterizedTypeBinding[] parameterizedTypeBindingArr;
            ReferenceBinding referenceBinding3 = (ReferenceBinding) TypeSystem.this.getUnannotatedType(referenceBinding);
            int i = 0;
            int length = typeBindingArr == null ? 0 : typeBindingArr.length;
            TypeBinding[] typeBindingArr2 = typeBindingArr == null ? null : new TypeBinding[length];
            for (int i2 = 0; i2 < length; i2++) {
                typeBindingArr2[i2] = TypeSystem.this.getUnannotatedType(typeBindingArr[i2]);
            }
            InternalParameterizedTypeBinding internalParameterizedTypeBinding = new InternalParameterizedTypeBinding(referenceBinding3, typeBindingArr2, referenceBinding2 == null ? null : (ReferenceBinding) TypeSystem.this.getUnannotatedType(referenceBinding2), TypeSystem.this.environment);
            ParameterizedTypeBinding[] parameterizedTypeBindingArr2 = this.hashedParameterizedTypes.get(internalParameterizedTypeBinding);
            if (parameterizedTypeBindingArr2 == null) {
                parameterizedTypeBindingArr = new ParameterizedTypeBinding[1];
            } else {
                int length2 = parameterizedTypeBindingArr2.length;
                ParameterizedTypeBinding[] parameterizedTypeBindingArr3 = new ParameterizedTypeBinding[length2 + 1];
                System.arraycopy(parameterizedTypeBindingArr2, 0, parameterizedTypeBindingArr3, 0, length2);
                i = length2;
                parameterizedTypeBindingArr = parameterizedTypeBindingArr3;
            }
            parameterizedTypeBindingArr[i] = parameterizedTypeBinding;
            this.hashedParameterizedTypes.put(internalParameterizedTypeBinding, parameterizedTypeBindingArr);
        }
    }

    public TypeSystem(LookupEnvironment lookupEnvironment) {
        this.typeid = 128;
        this.environment = lookupEnvironment;
        this.typeid = 128;
    }

    public final TypeBinding getUnannotatedType(TypeBinding typeBinding) {
        UnresolvedReferenceBinding unresolvedReferenceBinding;
        TypeBinding typeBinding2 = null;
        if (typeBinding.isUnresolvedType()) {
            unresolvedReferenceBinding = (UnresolvedReferenceBinding) typeBinding;
            ReferenceBinding referenceBinding = unresolvedReferenceBinding.resolvedType;
            if (referenceBinding != null) {
                typeBinding = CharOperation.indexOf('$', typeBinding.sourceName()) > 0 ? this.environment.convertToRawType(referenceBinding, false) : referenceBinding;
            } else if (CharOperation.indexOf('$', typeBinding.sourceName()) > 0) {
                boolean z = this.environment.mayTolerateMissingType;
                this.environment.mayTolerateMissingType = true;
                try {
                    typeBinding = BinaryTypeBinding.resolveType(typeBinding, this.environment, true);
                    this.environment.mayTolerateMissingType = z;
                } catch (Throwable th) {
                    this.environment.mayTolerateMissingType = z;
                    throw th;
                }
            }
        } else {
            unresolvedReferenceBinding = null;
        }
        try {
            if (typeBinding.f626id == Integer.MAX_VALUE) {
                if (typeBinding.hasTypeAnnotations()) {
                    throw new IllegalStateException();
                }
                TypeBinding[][] typeBindingArr = this.types;
                int length = typeBindingArr.length;
                if (this.typeid == length) {
                    TypeBinding[][] typeBindingArr2 = new TypeBinding[length * 2][];
                    this.types = typeBindingArr2;
                    System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
                }
                TypeBinding[][] typeBindingArr3 = this.types;
                int i = this.typeid;
                this.typeid = i + 1;
                typeBinding.f626id = i;
                typeBindingArr3[i] = new TypeBinding[4];
            } else {
                if (this.types[typeBinding.f626id] != null) {
                    typeBinding2 = this.types[typeBinding.f626id][0];
                }
                if (typeBinding.hasTypeAnnotations() && typeBinding2 == null) {
                    throw new IllegalStateException();
                }
                if (typeBinding2 == null) {
                    this.types[typeBinding.f626id] = new TypeBinding[4];
                } else {
                    if (unresolvedReferenceBinding != null && unresolvedReferenceBinding.f626id == Integer.MAX_VALUE) {
                        unresolvedReferenceBinding.f626id = typeBinding.f626id;
                    }
                    return typeBinding2;
                }
            }
            if (unresolvedReferenceBinding != null && unresolvedReferenceBinding.f626id == Integer.MAX_VALUE) {
                unresolvedReferenceBinding.f626id = typeBinding.f626id;
            }
            this.types[typeBinding.f626id][0] = typeBinding;
            return typeBinding;
        } catch (Throwable th2) {
            if (unresolvedReferenceBinding != null && unresolvedReferenceBinding.f626id == Integer.MAX_VALUE) {
                unresolvedReferenceBinding.f626id = typeBinding.f626id;
            }
            throw th2;
        }
    }

    public void forceRegisterAsDerived(TypeBinding typeBinding) {
        TypeBinding[] typeBindingArr;
        int i = typeBinding.f626id;
        if (i != Integer.MAX_VALUE && (typeBindingArr = this.types[i]) != null) {
            TypeBinding typeBindingClone = typeBindingArr[0];
            if (typeBindingClone == typeBinding) {
                typeBindingClone = typeBinding.clone(null);
                typeBindingArr[0] = typeBindingClone;
            }
            cacheDerivedType(typeBindingClone, typeBinding);
            return;
        }
        throw new IllegalStateException("Type was not yet registered as expected: " + typeBinding);
    }

    public TypeBinding[] getAnnotatedTypes(TypeBinding typeBinding) {
        return Binding.NO_TYPES;
    }

    public ArrayBinding getArrayType(TypeBinding typeBinding, int i) {
        if (typeBinding instanceof ArrayBinding) {
            i += typeBinding.dimensions();
            typeBinding = typeBinding.leafComponentType();
        }
        TypeBinding unannotatedType = getUnannotatedType(typeBinding);
        TypeBinding[] typeBindingArr = this.types[unannotatedType.f626id];
        int length = typeBindingArr.length;
        int i2 = 0;
        while (i2 < length) {
            TypeBinding typeBinding2 = typeBindingArr[i2];
            if (typeBinding2 == null) {
                break;
            }
            if (typeBinding2.isArrayType() && !typeBinding2.hasTypeAnnotations() && typeBinding2.leafComponentType() == unannotatedType && typeBinding2.dimensions() == i) {
                return (ArrayBinding) typeBinding2;
            }
            i2++;
        }
        if (i2 == length) {
            TypeBinding[] typeBindingArr2 = new TypeBinding[length * 2];
            System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
            this.types[unannotatedType.f626id] = typeBindingArr2;
            typeBindingArr = typeBindingArr2;
        }
        ArrayBinding arrayBinding = new ArrayBinding(unannotatedType, i, this.environment);
        typeBindingArr[i2] = arrayBinding;
        TypeBinding[][] typeBindingArr3 = this.types;
        int length2 = typeBindingArr3.length;
        if (this.typeid == length2) {
            TypeBinding[][] typeBindingArr4 = new TypeBinding[length2 * 2][];
            this.types = typeBindingArr4;
            System.arraycopy(typeBindingArr3, 0, typeBindingArr4, 0, length2);
        }
        TypeBinding[][] typeBindingArr5 = this.types;
        int i3 = this.typeid;
        typeBindingArr5[i3] = new TypeBinding[1];
        this.typeid = i3 + 1;
        arrayBinding.f626id = i3;
        typeBindingArr5[i3][0] = arrayBinding;
        return arrayBinding;
    }

    public ArrayBinding getArrayType(TypeBinding typeBinding, int i, AnnotationBinding[] annotationBindingArr) {
        return getArrayType(typeBinding, i);
    }

    public ParameterizedTypeBinding getParameterizedType(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2) {
        ReferenceBinding referenceBinding3 = (ReferenceBinding) getUnannotatedType(referenceBinding);
        int length = typeBindingArr == null ? 0 : typeBindingArr.length;
        TypeBinding[] typeBindingArr2 = typeBindingArr == null ? null : new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            typeBindingArr2[i] = getUnannotatedType(typeBindingArr[i]);
        }
        ReferenceBinding referenceBinding4 = referenceBinding2 != null ? (ReferenceBinding) getUnannotatedType(referenceBinding2) : null;
        ParameterizedTypeBinding parameterizedTypeBinding = this.parameterizedTypes.get(referenceBinding3, typeBindingArr2, referenceBinding4, Binding.NO_ANNOTATIONS);
        if (parameterizedTypeBinding != null) {
            return parameterizedTypeBinding;
        }
        ParameterizedTypeBinding parameterizedTypeBinding2 = new ParameterizedTypeBinding(referenceBinding3, typeBindingArr2, referenceBinding4, this.environment);
        cacheDerivedType(referenceBinding3, parameterizedTypeBinding2);
        this.parameterizedTypes.put(referenceBinding, typeBindingArr, referenceBinding2, parameterizedTypeBinding2);
        TypeBinding[][] typeBindingArr3 = this.types;
        int length2 = typeBindingArr3.length;
        if (this.typeid == length2) {
            TypeBinding[][] typeBindingArr4 = new TypeBinding[length2 * 2][];
            this.types = typeBindingArr4;
            System.arraycopy(typeBindingArr3, 0, typeBindingArr4, 0, length2);
        }
        TypeBinding[][] typeBindingArr5 = this.types;
        int i2 = this.typeid;
        typeBindingArr5[i2] = new TypeBinding[1];
        this.typeid = i2 + 1;
        parameterizedTypeBinding2.f626id = i2;
        typeBindingArr5[i2][0] = parameterizedTypeBinding2;
        return parameterizedTypeBinding2;
    }

    public ParameterizedTypeBinding getParameterizedType(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2, AnnotationBinding[] annotationBindingArr) {
        return getParameterizedType(referenceBinding, typeBindingArr, referenceBinding2);
    }

    public RawTypeBinding getRawType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        ReferenceBinding referenceBinding3 = (ReferenceBinding) getUnannotatedType(referenceBinding);
        ReferenceBinding referenceBinding4 = referenceBinding2 == null ? null : (ReferenceBinding) getUnannotatedType(referenceBinding2);
        TypeBinding[] typeBindingArr = this.types[referenceBinding3.f626id];
        int length = typeBindingArr.length;
        int i = 0;
        while (i < length) {
            TypeBinding typeBinding = typeBindingArr[i];
            if (typeBinding == null) {
                break;
            }
            if (typeBinding.isRawType() && typeBinding.actualType() == referenceBinding3 && !typeBinding.hasTypeAnnotations() && typeBinding.enclosingType() == referenceBinding4) {
                return (RawTypeBinding) typeBinding;
            }
            i++;
        }
        if (i == length) {
            TypeBinding[] typeBindingArr2 = new TypeBinding[length * 2];
            System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
            this.types[referenceBinding3.f626id] = typeBindingArr2;
            typeBindingArr = typeBindingArr2;
        }
        RawTypeBinding rawTypeBinding = new RawTypeBinding(referenceBinding3, referenceBinding4, this.environment);
        typeBindingArr[i] = rawTypeBinding;
        TypeBinding[][] typeBindingArr3 = this.types;
        int length2 = typeBindingArr3.length;
        if (this.typeid == length2) {
            TypeBinding[][] typeBindingArr4 = new TypeBinding[length2 * 2][];
            this.types = typeBindingArr4;
            System.arraycopy(typeBindingArr3, 0, typeBindingArr4, 0, length2);
        }
        TypeBinding[][] typeBindingArr5 = this.types;
        int i2 = this.typeid;
        typeBindingArr5[i2] = new TypeBinding[1];
        this.typeid = i2 + 1;
        rawTypeBinding.f626id = i2;
        typeBindingArr5[i2][0] = rawTypeBinding;
        return rawTypeBinding;
    }

    public RawTypeBinding getRawType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, AnnotationBinding[] annotationBindingArr) {
        return getRawType(referenceBinding, referenceBinding2);
    }

    public WildcardBinding getWildcard(ReferenceBinding referenceBinding, int i, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i2) {
        TypeBinding[] typeBindingArr2;
        if (referenceBinding == null) {
            referenceBinding = ReferenceBinding.LUB_GENERIC;
        }
        ReferenceBinding referenceBinding2 = (ReferenceBinding) getUnannotatedType(referenceBinding);
        int length = typeBindingArr == null ? 0 : typeBindingArr.length;
        TypeBinding[] typeBindingArr3 = typeBindingArr == null ? null : new TypeBinding[length];
        for (int i3 = 0; i3 < length; i3++) {
            typeBindingArr3[i3] = getUnannotatedType(typeBindingArr[i3]);
        }
        TypeBinding unannotatedType = typeBinding == null ? null : getUnannotatedType(typeBinding);
        TypeBinding[] typeBindingArr4 = this.types[referenceBinding2.f626id];
        int length2 = typeBindingArr4.length;
        int i4 = 0;
        while (i4 < length2) {
            TypeBinding typeBinding2 = typeBindingArr4[i4];
            if (typeBinding2 == null) {
                break;
            }
            if (typeBinding2.isWildcard() && typeBinding2.actualType() == referenceBinding2 && !typeBinding2.hasTypeAnnotations() && typeBinding2.rank() == i && typeBinding2.boundKind() == i2 && typeBinding2.bound() == unannotatedType && Util.effectivelyEqual(typeBinding2.additionalBounds(), typeBindingArr3)) {
                return (WildcardBinding) typeBinding2;
            }
            i4++;
        }
        if (i4 == length2) {
            typeBindingArr2 = new TypeBinding[length2 * 2];
            System.arraycopy(typeBindingArr4, 0, typeBindingArr2, 0, length2);
            this.types[referenceBinding2.f626id] = typeBindingArr2;
        } else {
            typeBindingArr2 = typeBindingArr4;
        }
        WildcardBinding wildcardBinding = new WildcardBinding(referenceBinding2, i, unannotatedType, typeBindingArr3, i2, this.environment);
        typeBindingArr2[i4] = wildcardBinding;
        TypeBinding[][] typeBindingArr5 = this.types;
        int length3 = typeBindingArr5.length;
        if (this.typeid == length3) {
            TypeBinding[][] typeBindingArr6 = new TypeBinding[length3 * 2][];
            this.types = typeBindingArr6;
            System.arraycopy(typeBindingArr5, 0, typeBindingArr6, 0, length3);
        }
        TypeBinding[][] typeBindingArr7 = this.types;
        int i5 = this.typeid;
        typeBindingArr7[i5] = new TypeBinding[1];
        this.typeid = i5 + 1;
        wildcardBinding.f626id = i5;
        typeBindingArr7[i5][0] = wildcardBinding;
        return wildcardBinding;
    }

    public final CaptureBinding getCapturedWildcard(WildcardBinding wildcardBinding, ReferenceBinding referenceBinding, int i, int i2, ASTNode aSTNode, int i3) {
        WildcardBinding wildcardBinding2 = (WildcardBinding) getUnannotatedType(wildcardBinding);
        TypeBinding[] typeBindingArr = this.types[wildcardBinding2.f626id];
        int length = typeBindingArr.length;
        int i4 = length - 1;
        int i5 = length;
        while (i4 >= -1) {
            if (i4 != -1) {
                TypeBinding typeBinding = typeBindingArr[i4];
                if (typeBinding == null) {
                    i5 = i4;
                } else if (typeBinding.isCapture()) {
                    CaptureBinding captureBinding = (CaptureBinding) typeBinding;
                    if (captureBinding.cud == aSTNode) {
                        if (captureBinding.sourceType == referenceBinding && captureBinding.start == i) {
                            if (captureBinding.end == i2) {
                                return captureBinding;
                            }
                        }
                    }
                }
                i4--;
            }
            i4 = i5;
        }
        if (i4 == length) {
            TypeBinding[] typeBindingArr2 = new TypeBinding[length * 2];
            System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
            this.types[wildcardBinding2.f626id] = typeBindingArr2;
            typeBindingArr = typeBindingArr2;
        }
        CaptureBinding captureBinding2 = new CaptureBinding(wildcardBinding, referenceBinding, i, i2, aSTNode, i3);
        typeBindingArr[i4] = captureBinding2;
        return captureBinding2;
    }

    public WildcardBinding getWildcard(ReferenceBinding referenceBinding, int i, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i2, AnnotationBinding[] annotationBindingArr) {
        return getWildcard(referenceBinding, i, typeBinding, typeBindingArr, i2);
    }

    protected final TypeBinding[] getDerivedTypes(TypeBinding typeBinding) {
        return this.types[getUnannotatedType(typeBinding).f626id];
    }

    private TypeBinding cacheDerivedType(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (typeBinding == null || typeBinding2 == null || typeBinding.f626id == Integer.MAX_VALUE) {
            throw new IllegalStateException();
        }
        TypeBinding[] typeBindingArr = this.types[typeBinding.f626id];
        int length = typeBindingArr.length;
        int i = length / 2;
        int i2 = length;
        int i3 = 0;
        do {
            if (typeBindingArr[i] != null) {
                i3 = i + 1;
            } else {
                if (i == i3 || (i > 0 && typeBindingArr[i - 1] != null)) {
                    break;
                }
                i2 = i - 1;
            }
            i = (i3 + i2) / 2;
            if (i >= length) {
                break;
            }
        } while (i3 <= i2);
        if (i == length) {
            TypeBinding[] typeBindingArr2 = new TypeBinding[length * 2];
            System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
            this.types[typeBinding.f626id] = typeBindingArr2;
            typeBindingArr = typeBindingArr2;
        }
        typeBindingArr[i] = typeBinding2;
        return typeBinding2;
    }

    protected final TypeBinding cacheDerivedType(TypeBinding typeBinding, TypeBinding typeBinding2, TypeBinding typeBinding3) {
        cacheDerivedType(typeBinding, typeBinding3);
        if (typeBinding2.f626id != typeBinding.f626id) {
            cacheDerivedType(typeBinding2, typeBinding3);
        }
        return typeBinding3;
    }

    public final AnnotationBinding getAnnotationType(ReferenceBinding referenceBinding, boolean z) {
        AnnotationBinding unresolvedAnnotationBinding = (AnnotationBinding) this.annotationTypes.get(referenceBinding);
        if (unresolvedAnnotationBinding == null) {
            if (z) {
                unresolvedAnnotationBinding = new AnnotationBinding(referenceBinding, Binding.NO_ELEMENT_VALUE_PAIRS);
            } else {
                unresolvedAnnotationBinding = new UnresolvedAnnotationBinding(referenceBinding, Binding.NO_ELEMENT_VALUE_PAIRS, this.environment);
            }
            this.annotationTypes.put(referenceBinding, unresolvedAnnotationBinding);
        }
        if (z) {
            unresolvedAnnotationBinding.resolve();
        }
        return unresolvedAnnotationBinding;
    }

    public void reset() {
        this.annotationTypes = new SimpleLookupTable(16);
        this.typeid = 128;
        this.types = new TypeBinding[256][];
        this.parameterizedTypes = new HashedParameterizedTypes();
    }

    public void updateCaches(UnresolvedReferenceBinding unresolvedReferenceBinding, ReferenceBinding referenceBinding) {
        int i = unresolvedReferenceBinding.f626id;
        if (i != Integer.MAX_VALUE) {
            TypeBinding[] typeBindingArr = this.types[i];
            int length = typeBindingArr == null ? 0 : typeBindingArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                TypeBinding typeBinding = typeBindingArr[i2];
                if (typeBinding == null) {
                    break;
                }
                if (typeBinding == unresolvedReferenceBinding) {
                    referenceBinding.f626id = i;
                    typeBindingArr[i2] = referenceBinding;
                }
            }
        }
        if (this.annotationTypes.get(unresolvedReferenceBinding) != null) {
            Object[] objArr = this.annotationTypes.keyTable;
            int length2 = objArr.length;
            for (int i3 = 0; i3 < length2; i3++) {
                if (objArr[i3] == unresolvedReferenceBinding) {
                    objArr[i3] = referenceBinding;
                    return;
                }
            }
        }
    }

    public final TypeBinding getIntersectionType18(ReferenceBinding[] referenceBindingArr) {
        int length = referenceBindingArr == null ? 0 : referenceBindingArr.length;
        if (length == 0) {
            return null;
        }
        ReferenceBinding referenceBinding = referenceBindingArr[0];
        if (referenceBinding == null || length == 1) {
            return referenceBinding;
        }
        for (TypeBinding typeBinding : getDerivedTypes(referenceBinding)) {
            if (typeBinding == null) {
                break;
            }
            if (typeBinding.isIntersectionType18()) {
                ReferenceBinding[] intersectingTypes = typeBinding.getIntersectingTypes();
                if (intersectingTypes.length == length) {
                    for (int i = 0; i < length; i++) {
                        if (referenceBindingArr[i] == intersectingTypes[i]) {
                        }
                    }
                    return typeBinding;
                }
                continue;
            }
        }
        return cacheDerivedType(referenceBinding, new IntersectionTypeBinding18(referenceBindingArr, this.environment));
    }

    public void fixTypeVariableDeclaringElement(TypeVariableBinding typeVariableBinding, Binding binding) {
        TypeBinding[] typeBindingArr;
        int i = typeVariableBinding.f626id;
        if (i < this.typeid && (typeBindingArr = this.types[i]) != null) {
            for (TypeBinding typeBinding : typeBindingArr) {
                if (typeBinding instanceof TypeVariableBinding) {
                    ((TypeVariableBinding) typeBinding).declaringElement = binding;
                }
            }
            return;
        }
        typeVariableBinding.declaringElement = binding;
    }
}
