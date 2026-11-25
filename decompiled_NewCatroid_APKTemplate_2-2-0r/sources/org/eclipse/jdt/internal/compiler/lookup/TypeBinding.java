package org.eclipse.jdt.internal.compiler.lookup;

import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public abstract class TypeBinding extends Binding {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public int f626id;
    public long tagBits;
    protected AnnotationBinding[] typeAnnotations;
    public static final ReferenceBinding TYPE_USE_BINDING = new ReferenceBinding() { // from class: org.eclipse.jdt.internal.compiler.lookup.TypeBinding.1
        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
        public boolean hasTypeBit(int i) {
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
        public int kind() {
            return Binding.TYPE_USE;
        }

        {
            this.f626id = 0;
        }
    };
    public static final BaseTypeBinding INT = new BaseTypeBinding(10, TypeConstants.INT, new char[]{'I'});
    public static final BaseTypeBinding BYTE = new BaseTypeBinding(3, TypeConstants.BYTE, new char[]{Util.C_BYTE});
    public static final BaseTypeBinding SHORT = new BaseTypeBinding(4, TypeConstants.SHORT, new char[]{Util.C_SHORT});
    public static final BaseTypeBinding CHAR = new BaseTypeBinding(2, TypeConstants.CHAR, new char[]{Util.C_CHAR});
    public static final BaseTypeBinding LONG = new BaseTypeBinding(7, TypeConstants.LONG, new char[]{Util.C_LONG});
    public static final BaseTypeBinding FLOAT = new BaseTypeBinding(9, TypeConstants.FLOAT, new char[]{Util.C_FLOAT});
    public static final BaseTypeBinding DOUBLE = new BaseTypeBinding(8, TypeConstants.DOUBLE, new char[]{Util.C_DOUBLE});
    public static final BaseTypeBinding BOOLEAN = new BaseTypeBinding(5, TypeConstants.BOOLEAN, new char[]{'Z'});
    public static final NullTypeBinding NULL = new NullTypeBinding();
    public static final VoidTypeBinding VOID = new VoidTypeBinding();

    public boolean acceptsNonNullDefault() {
        return false;
    }

    public ReferenceBinding actualType() {
        return null;
    }

    TypeBinding[] additionalBounds() {
        return null;
    }

    TypeBinding bound() {
        return null;
    }

    int boundKind() {
        return -1;
    }

    public boolean canBeSeenBy(Scope scope) {
        return true;
    }

    public TypeBinding capture(Scope scope, int i, int i2) {
        return this;
    }

    public TypeBinding closestMatch() {
        return this;
    }

    void collectInferenceVariables(Set<InferenceVariable> set) {
    }

    public List<TypeBinding> collectMissingTypes(List<TypeBinding> list) {
        return list;
    }

    public void collectSubstitutes(Scope scope, TypeBinding typeBinding, InferenceContext inferenceContext, int i) {
    }

    public abstract char[] constantPoolName();

    public ReferenceBinding containerAnnotationType() {
        return null;
    }

    public int depth() {
        return 0;
    }

    public int dimensions() {
        return 0;
    }

    public MethodBinding enclosingMethod() {
        return null;
    }

    public ReferenceBinding enclosingType() {
        return null;
    }

    public boolean enterRecursiveFunction() {
        return true;
    }

    public TypeBinding erasure() {
        return this;
    }

    public void exitRecursiveFunction() {
    }

    public ReferenceBinding[] getIntersectingTypes() {
        return null;
    }

    public abstract PackageBinding getPackage();

    public MethodBinding getSingleAbstractMethod(Scope scope, boolean z) {
        return null;
    }

    public boolean hasTypeBit(int i) {
        return false;
    }

    void initializeForStaticImports() {
    }

    public boolean isBoundParameterizedType() {
        return false;
    }

    public boolean isCapture() {
        return false;
    }

    public boolean isClass() {
        return false;
    }

    public abstract boolean isCompatibleWith(TypeBinding typeBinding, Scope scope);

    public boolean isEnum() {
        return false;
    }

    public boolean isFreeTypeVariable() {
        return false;
    }

    public boolean isFunctionalInterface(Scope scope) {
        return false;
    }

    public boolean isFunctionalType() {
        return false;
    }

    public boolean isGenericType() {
        return false;
    }

    public boolean isInterface() {
        return false;
    }

    public boolean isIntersectionType() {
        return false;
    }

    public boolean isIntersectionType18() {
        return false;
    }

    public boolean isParameterizedType() {
        return false;
    }

    public boolean isPolyType() {
        return false;
    }

    public boolean isProperType(boolean z) {
        return true;
    }

    public boolean isRepeatableAnnotationType() {
        return false;
    }

    public boolean isStatic() {
        return false;
    }

    public boolean isThrowable() {
        return false;
    }

    public boolean isTypeVariable() {
        return false;
    }

    public boolean isUnboundWildcard() {
        return false;
    }

    public boolean isUncheckedException(boolean z) {
        return false;
    }

    public boolean isUnresolvedType() {
        return false;
    }

    public boolean isWildcard() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return 4;
    }

    public TypeBinding leafComponentType() {
        return this;
    }

    public TypeBinding prototype() {
        return null;
    }

    public abstract char[] qualifiedSourceName();

    int rank() {
        return -1;
    }

    public abstract char[] sourceName();

    TypeBinding substituteInferenceVariable(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        return this;
    }

    public ReferenceBinding superclass() {
        return null;
    }

    public void swapUnresolved(UnresolvedReferenceBinding unresolvedReferenceBinding, ReferenceBinding referenceBinding, LookupEnvironment lookupEnvironment) {
    }

    public SyntheticArgumentBinding[] syntheticOuterLocalVariables() {
        return null;
    }

    TypeBinding[] typeArguments() {
        return null;
    }

    public TypeBinding unannotated() {
        return this;
    }

    public TypeBinding uncapture(Scope scope) {
        return this;
    }

    public TypeBinding withoutToplevelNullAnnotation() {
        return this;
    }

    public TypeBinding() {
        this.f626id = Integer.MAX_VALUE;
        this.tagBits = 0L;
        this.typeAnnotations = Binding.NO_ANNOTATIONS;
    }

    public TypeBinding(TypeBinding typeBinding) {
        this.f626id = Integer.MAX_VALUE;
        this.tagBits = 0L;
        this.typeAnnotations = Binding.NO_ANNOTATIONS;
        this.f626id = typeBinding.f626id;
        this.tagBits = typeBinding.tagBits & (-108086391056891905L);
    }

    public static final TypeBinding wellKnownType(Scope scope, int i) {
        switch (i) {
            case 1:
                return scope.getJavaLangObject();
            case 2:
                return CHAR;
            case 3:
                return BYTE;
            case 4:
                return SHORT;
            case 5:
                return BOOLEAN;
            case 6:
            default:
                return null;
            case 7:
                return LONG;
            case 8:
                return DOUBLE;
            case 9:
                return FLOAT;
            case 10:
                return INT;
            case 11:
                return scope.getJavaLangString();
        }
    }

    public String annotatedDebugName() {
        ReferenceBinding referenceBindingEnclosingType = enclosingType();
        StringBuffer stringBuffer = new StringBuffer(16);
        if (referenceBindingEnclosingType != null) {
            stringBuffer.append(referenceBindingEnclosingType.annotatedDebugName());
            stringBuffer.append('.');
        }
        AnnotationBinding[] typeAnnotations = getTypeAnnotations();
        int length = typeAnnotations == null ? 0 : typeAnnotations.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append(typeAnnotations[i]);
            stringBuffer.append(StringUtil.SPACE);
        }
        stringBuffer.append(sourceName());
        return stringBuffer.toString();
    }

    public boolean canBeInstantiated() {
        return !isBaseType();
    }

    public TypeBinding clone(TypeBinding typeBinding) {
        throw new IllegalStateException("TypeBinding#clone() should have been overridden");
    }

    public String debugName() {
        return hasTypeAnnotations() ? annotatedDebugName() : new String(readableName());
    }

    public ReferenceBinding findSuperTypeOriginatingFrom(int i, boolean z) {
        ReferenceBinding[] referenceBindingArr;
        ReferenceBinding[] referenceBindingArr2;
        if (!(this instanceof ReferenceBinding)) {
            return null;
        }
        ReferenceBinding referenceBindingSuperclass = (ReferenceBinding) this;
        if (referenceBindingSuperclass.f626id == i || original().f626id == i) {
            return referenceBindingSuperclass;
        }
        if (z) {
            do {
                referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                if (referenceBindingSuperclass == null) {
                    return null;
                }
                if (referenceBindingSuperclass.f626id == i) {
                    return referenceBindingSuperclass;
                }
            } while (referenceBindingSuperclass.original().f626id != i);
            return referenceBindingSuperclass;
        }
        int length = 0;
        ReferenceBinding[] referenceBindingArr3 = null;
        do {
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
            if (referenceBindingArrSuperInterfaces != null && referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
                if (referenceBindingArr3 == null) {
                    length = referenceBindingArrSuperInterfaces.length;
                    referenceBindingArr3 = referenceBindingArrSuperInterfaces;
                } else {
                    int length2 = length + referenceBindingArrSuperInterfaces.length;
                    if (length2 >= referenceBindingArr3.length) {
                        referenceBindingArr2 = new ReferenceBinding[length2 + 5];
                        System.arraycopy(referenceBindingArr3, 0, referenceBindingArr2, 0, length);
                    } else {
                        referenceBindingArr2 = referenceBindingArr3;
                    }
                    int i2 = length;
                    for (ReferenceBinding referenceBinding : referenceBindingArrSuperInterfaces) {
                        int i3 = 0;
                        while (true) {
                            if (i3 < i2) {
                                if (equalsEquals(referenceBinding, referenceBindingArr2[i3])) {
                                    break;
                                }
                                i3++;
                            } else {
                                referenceBindingArr2[i2] = referenceBinding;
                                i2++;
                                break;
                            }
                        }
                    }
                    referenceBindingArr3 = referenceBindingArr2;
                    length = i2;
                }
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
        } while (referenceBindingSuperclass != null);
        for (int i4 = 0; i4 < length; i4++) {
            ReferenceBinding referenceBinding2 = referenceBindingArr3[i4];
            if (referenceBinding2.f626id == i || referenceBinding2.original().f626id == i) {
                return referenceBinding2;
            }
            ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding2.superInterfaces();
            if (referenceBindingArrSuperInterfaces2 != null && referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                int length3 = length + referenceBindingArrSuperInterfaces2.length;
                if (length3 >= referenceBindingArr3.length) {
                    referenceBindingArr = new ReferenceBinding[length3 + 5];
                    System.arraycopy(referenceBindingArr3, 0, referenceBindingArr, 0, length);
                } else {
                    referenceBindingArr = referenceBindingArr3;
                }
                int i5 = length;
                for (ReferenceBinding referenceBinding3 : referenceBindingArrSuperInterfaces2) {
                    int i6 = 0;
                    while (true) {
                        if (i6 < i5) {
                            if (equalsEquals(referenceBinding3, referenceBindingArr[i6])) {
                                break;
                            }
                            i6++;
                        } else {
                            referenceBindingArr[i5] = referenceBinding3;
                            i5++;
                            break;
                        }
                    }
                }
                referenceBindingArr3 = referenceBindingArr;
                length = i5;
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x011f A[LOOP:4: B:96:0x010c->B:101:0x011f, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:107:0x012c  */
    /* JADX WARN: Code duplicated, block: B:110:0x0135  */
    /* JADX WARN: Code duplicated, block: B:115:0x0146  */
    /* JADX WARN: Code duplicated, block: B:134:0x00cf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:135:0x00d6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:142:0x011c A[EDGE_INSN: B:142:0x011c->B:100:0x011c BREAK  A[LOOP:4: B:96:0x010c->B:101:0x011f], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:143:0x010e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:145:0x0134 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x0179 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:64:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:66:0x00b4 A[ADDED_TO_REGION, RETURN] */
    /* JADX WARN: Code duplicated, block: B:67:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:72:0x00c9 A[LOOP:1: B:72:0x00c9->B:137:?, LOOP_START, PHI: r1
      0x00c9: PHI (r1v11 org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding) = 
      (r1v5 org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)
      (r1v12 org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)
     binds: [B:71:0x00c7, B:137:?] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:75:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:78:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:81:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:84:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:87:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:88:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:90:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:91:0x0101  */
    /* JADX WARN: Code duplicated, block: B:95:0x0109  */
    /* JADX WARN: Code duplicated, block: B:98:0x0114  */
    public TypeBinding findSuperTypeOriginatingFrom(TypeBinding typeBinding) {
        TypeBinding typeBindingOriginal;
        ReferenceBinding referenceBindingSuperclass;
        ReferenceBinding[] referenceBindingArr;
        int length;
        ReferenceBinding[] referenceBindingArrSuperInterfaces;
        int i;
        ReferenceBinding referenceBinding;
        ReferenceBinding[] referenceBindingArrSuperInterfaces2;
        ReferenceBinding[] referenceBindingArr2;
        int length2;
        ReferenceBinding[] referenceBindingArr3;
        int i2;
        int i3;
        int i4;
        TypeBinding typeBindingFindSuperTypeOriginatingFrom;
        TypeBinding typeBindingFindSuperTypeOriginatingFrom2;
        if (equalsEquals(this, typeBinding)) {
            return this;
        }
        if (typeBinding == null) {
            return null;
        }
        int iKind = kind();
        if (iKind == 4) {
            typeBindingOriginal = typeBinding.original();
            if (!equalsEquals(this, typeBindingOriginal) || equalsEquals(original(), typeBindingOriginal)) {
                return this;
            }
            referenceBindingSuperclass = (ReferenceBinding) this;
            if (!typeBindingOriginal.isInterface()) {
                do {
                    referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                    if (referenceBindingSuperclass == null) {
                        return null;
                    }
                    if (equalsEquals(referenceBindingSuperclass, typeBindingOriginal)) {
                        return referenceBindingSuperclass;
                    }
                } while (!equalsEquals(referenceBindingSuperclass.original(), typeBindingOriginal));
                return referenceBindingSuperclass;
            }
            referenceBindingArr = null;
            length = 0;
            do {
                referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
                if (referenceBindingArrSuperInterfaces != null && referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
                    if (referenceBindingArr == null) {
                        length = referenceBindingArrSuperInterfaces.length;
                        referenceBindingArr = referenceBindingArrSuperInterfaces;
                    } else {
                        length2 = length + referenceBindingArrSuperInterfaces.length;
                        if (length2 >= referenceBindingArr.length) {
                            referenceBindingArr3 = new ReferenceBinding[length2 + 5];
                            System.arraycopy(referenceBindingArr, 0, referenceBindingArr3, 0, length);
                        } else {
                            referenceBindingArr3 = referenceBindingArr;
                        }
                        i3 = length;
                        for (ReferenceBinding referenceBinding2 : referenceBindingArrSuperInterfaces) {
                            i4 = 0;
                            while (true) {
                                if (i4 < i3) {
                                    if (equalsEquals(referenceBinding2, referenceBindingArr3[i4])) {
                                        break;
                                    }
                                    i4++;
                                } else {
                                    referenceBindingArr3[i3] = referenceBinding2;
                                    i3++;
                                    break;
                                }
                            }
                        }
                        referenceBindingArr = referenceBindingArr3;
                        length = i3;
                    }
                }
                referenceBindingSuperclass = referenceBindingSuperclass.superclass();
            } while (referenceBindingSuperclass != null);
            for (i = 0; i < length; i++) {
                referenceBinding = referenceBindingArr[i];
                if (!equalsEquals(referenceBinding, typeBindingOriginal) || equalsEquals(referenceBinding.original(), typeBindingOriginal)) {
                    return referenceBinding;
                }
                referenceBindingArrSuperInterfaces2 = referenceBinding.superInterfaces();
                if (referenceBindingArrSuperInterfaces2 == null && referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                    int length3 = length + referenceBindingArrSuperInterfaces2.length;
                    if (length3 >= referenceBindingArr.length) {
                        referenceBindingArr2 = new ReferenceBinding[length3 + 5];
                        System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, length);
                    } else {
                        referenceBindingArr2 = referenceBindingArr;
                    }
                    int i5 = length;
                    for (ReferenceBinding referenceBinding3 : referenceBindingArrSuperInterfaces2) {
                        int i6 = 0;
                        while (true) {
                            if (i6 < i5) {
                                if (equalsEquals(referenceBinding3, referenceBindingArr2[i6])) {
                                    break;
                                }
                                i6++;
                            } else {
                                referenceBindingArr2[i5] = referenceBinding3;
                                i5++;
                                break;
                            }
                        }
                    }
                    referenceBindingArr = referenceBindingArr2;
                    length = i5;
                }
            }
        } else {
            if (iKind == 68) {
                ArrayBinding arrayBinding = (ArrayBinding) this;
                int iDimensions = typeBinding.dimensions();
                if (arrayBinding.dimensions != iDimensions) {
                    int i7 = typeBinding.f626id;
                    if (i7 == 1 || i7 == 36 || i7 == 37) {
                        return typeBinding;
                    }
                    if (iDimensions >= arrayBinding.dimensions || typeBinding.leafComponentType().f626id != 1) {
                        return null;
                    }
                    return typeBinding;
                }
                if ((arrayBinding.leafComponentType instanceof ReferenceBinding) && (typeBindingFindSuperTypeOriginatingFrom = arrayBinding.leafComponentType.findSuperTypeOriginatingFrom(typeBinding.leafComponentType())) != null) {
                    return arrayBinding.environment().createArrayType(typeBindingFindSuperTypeOriginatingFrom, arrayBinding.dimensions);
                }
                return null;
            }
            if (iKind == 260 || iKind == 516 || iKind == 1028 || iKind == 2052) {
                typeBindingOriginal = typeBinding.original();
                if (!equalsEquals(this, typeBindingOriginal)) {
                    return this;
                }
                referenceBindingSuperclass = (ReferenceBinding) this;
                if (!typeBindingOriginal.isInterface()) {
                    do {
                        referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                        if (referenceBindingSuperclass == null) {
                            return null;
                        }
                        if (equalsEquals(referenceBindingSuperclass, typeBindingOriginal)) {
                            return referenceBindingSuperclass;
                        }
                    } while (!equalsEquals(referenceBindingSuperclass.original(), typeBindingOriginal));
                    return referenceBindingSuperclass;
                }
                referenceBindingArr = null;
                length = 0;
                do {
                    referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
                    if (referenceBindingArrSuperInterfaces != null) {
                        if (referenceBindingArr == null) {
                            length = referenceBindingArrSuperInterfaces.length;
                            referenceBindingArr = referenceBindingArrSuperInterfaces;
                        } else {
                            length2 = length + referenceBindingArrSuperInterfaces.length;
                            if (length2 >= referenceBindingArr.length) {
                                referenceBindingArr3 = new ReferenceBinding[length2 + 5];
                                System.arraycopy(referenceBindingArr, 0, referenceBindingArr3, 0, length);
                            } else {
                                referenceBindingArr3 = referenceBindingArr;
                            }
                            i3 = length;
                            while (i2 < r6) {
                                i4 = 0;
                                while (true) {
                                    if (i4 < i3) {
                                        if (equalsEquals(referenceBinding2, referenceBindingArr3[i4])) {
                                            break;
                                            break;
                                        }
                                        i4++;
                                    } else {
                                        referenceBindingArr3[i3] = referenceBinding2;
                                        i3++;
                                        break;
                                        break;
                                    }
                                }
                            }
                            referenceBindingArr = referenceBindingArr3;
                            length = i3;
                        }
                    }
                    referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                } while (referenceBindingSuperclass != null);
                while (i < length) {
                    referenceBinding = referenceBindingArr[i];
                    if (!equalsEquals(referenceBinding, typeBindingOriginal)) {
                        return referenceBinding;
                    }
                    referenceBindingArrSuperInterfaces2 = referenceBinding.superInterfaces();
                    if (referenceBindingArrSuperInterfaces2 == null) {
                    }
                }
            } else {
                if (iKind != 4100) {
                    if (iKind != 8196) {
                        if (iKind == 32772) {
                            for (ReferenceBinding referenceBinding4 : ((IntersectionTypeBinding18) this).getIntersectingTypes()) {
                                TypeBinding typeBindingFindSuperTypeOriginatingFrom3 = referenceBinding4.findSuperTypeOriginatingFrom(typeBinding);
                                if (typeBindingFindSuperTypeOriginatingFrom3 != null) {
                                    return typeBindingFindSuperTypeOriginatingFrom3;
                                }
                            }
                        }
                    }
                } else if (isCapture()) {
                    TypeBinding typeBinding2 = ((CaptureBinding) this).firstBound;
                    if ((typeBinding2 instanceof ArrayBinding) && (typeBindingFindSuperTypeOriginatingFrom2 = typeBinding2.findSuperTypeOriginatingFrom(typeBinding)) != null) {
                        return typeBindingFindSuperTypeOriginatingFrom2;
                    }
                }
                typeBindingOriginal = typeBinding.original();
                if (!equalsEquals(this, typeBindingOriginal)) {
                    return this;
                }
                referenceBindingSuperclass = (ReferenceBinding) this;
                if (!typeBindingOriginal.isInterface()) {
                    do {
                        referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                        if (referenceBindingSuperclass == null) {
                            return null;
                        }
                        if (equalsEquals(referenceBindingSuperclass, typeBindingOriginal)) {
                            return referenceBindingSuperclass;
                        }
                    } while (!equalsEquals(referenceBindingSuperclass.original(), typeBindingOriginal));
                    return referenceBindingSuperclass;
                }
                referenceBindingArr = null;
                length = 0;
                do {
                    referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
                    if (referenceBindingArrSuperInterfaces != null) {
                        if (referenceBindingArr == null) {
                            length = referenceBindingArrSuperInterfaces.length;
                            referenceBindingArr = referenceBindingArrSuperInterfaces;
                        } else {
                            length2 = length + referenceBindingArrSuperInterfaces.length;
                            if (length2 >= referenceBindingArr.length) {
                                referenceBindingArr3 = new ReferenceBinding[length2 + 5];
                                System.arraycopy(referenceBindingArr, 0, referenceBindingArr3, 0, length);
                            } else {
                                referenceBindingArr3 = referenceBindingArr;
                            }
                            i3 = length;
                            while (i2 < r6) {
                                i4 = 0;
                                while (true) {
                                    if (i4 < i3) {
                                        if (equalsEquals(referenceBinding2, referenceBindingArr3[i4])) {
                                            break;
                                            break;
                                        }
                                        i4++;
                                    } else {
                                        referenceBindingArr3[i3] = referenceBinding2;
                                        i3++;
                                        break;
                                        break;
                                    }
                                }
                            }
                            referenceBindingArr = referenceBindingArr3;
                            length = i3;
                        }
                    }
                    referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                } while (referenceBindingSuperclass != null);
                while (i < length) {
                    referenceBinding = referenceBindingArr[i];
                    if (!equalsEquals(referenceBinding, typeBindingOriginal)) {
                        return referenceBinding;
                    }
                    referenceBindingArrSuperInterfaces2 = referenceBinding.superInterfaces();
                    if (referenceBindingArrSuperInterfaces2 == null) {
                    }
                }
            }
        }
        return null;
    }

    public TypeBinding genericCast(TypeBinding typeBinding) {
        if (equalsEquals(this, typeBinding)) {
            return null;
        }
        TypeBinding typeBindingErasure = typeBinding.erasure();
        if (erasure().findSuperTypeOriginatingFrom(typeBindingErasure) != null) {
            return null;
        }
        return typeBindingErasure;
    }

    public char[] genericTypeSignature() {
        return signature();
    }

    public TypeBinding getErasureCompatibleType(TypeBinding typeBinding) {
        int iKind = kind();
        int i = 0;
        if (iKind == 4100) {
            TypeVariableBinding typeVariableBinding = (TypeVariableBinding) this;
            if (typeVariableBinding.erasure().findSuperTypeOriginatingFrom(typeBinding) != null) {
                return this;
            }
            if (typeVariableBinding.superclass != null && typeVariableBinding.superclass.findSuperTypeOriginatingFrom(typeBinding) != null) {
                return typeVariableBinding.superclass.getErasureCompatibleType(typeBinding);
            }
            int length = typeVariableBinding.superInterfaces.length;
            while (i < length) {
                ReferenceBinding referenceBinding = typeVariableBinding.superInterfaces[i];
                if (referenceBinding.findSuperTypeOriginatingFrom(typeBinding) != null) {
                    return referenceBinding.getErasureCompatibleType(typeBinding);
                }
                i++;
            }
            return this;
        }
        if (iKind != 8196) {
            return this;
        }
        WildcardBinding wildcardBinding = (WildcardBinding) this;
        if (wildcardBinding.erasure().findSuperTypeOriginatingFrom(typeBinding) != null) {
            return this;
        }
        if (wildcardBinding.superclass != null && wildcardBinding.superclass.findSuperTypeOriginatingFrom(typeBinding) != null) {
            return wildcardBinding.superclass.getErasureCompatibleType(typeBinding);
        }
        int length2 = wildcardBinding.superInterfaces.length;
        while (i < length2) {
            ReferenceBinding referenceBinding2 = wildcardBinding.superInterfaces[i];
            if (referenceBinding2.findSuperTypeOriginatingFrom(typeBinding) != null) {
                return referenceBinding2.getErasureCompatibleType(typeBinding);
            }
            i++;
        }
        return this;
    }

    public final boolean isAnonymousType() {
        return (this.tagBits & 32) != 0;
    }

    public final boolean isArrayType() {
        return (this.tagBits & 1) != 0;
    }

    public final boolean isBaseType() {
        return (this.tagBits & 2) != 0;
    }

    public final boolean isPrimitiveType() {
        int i;
        return ((this.tagBits & 2) == 0 || (i = this.f626id) == 6 || i == 12) ? false : true;
    }

    public final boolean isPrimitiveOrBoxedPrimitiveType() {
        if (isPrimitiveType()) {
            return true;
        }
        switch (this.f626id) {
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
                return true;
            default:
                return false;
        }
    }

    public boolean isBoxedPrimitiveType() {
        switch (this.f626id) {
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
                return true;
            default:
                return false;
        }
    }

    public boolean isCompatibleWith(TypeBinding typeBinding) {
        return isCompatibleWith(typeBinding, null);
    }

    public boolean isPotentiallyCompatibleWith(TypeBinding typeBinding, Scope scope) {
        return isCompatibleWith(typeBinding, scope);
    }

    public boolean isBoxingCompatibleWith(TypeBinding typeBinding, Scope scope) {
        if (typeBinding == null) {
            return false;
        }
        if (equalsEquals(this, typeBinding) || isCompatibleWith(typeBinding, scope)) {
            return true;
        }
        if (isBaseType() != typeBinding.isBaseType()) {
            TypeBinding typeBindingComputeBoxingType = scope.environment().computeBoxingType(this);
            if (equalsEquals(typeBindingComputeBoxingType, typeBinding) || typeBindingComputeBoxingType.isCompatibleWith(typeBinding, scope)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEquivalentTo(TypeBinding typeBinding) {
        if (equalsEquals(this, typeBinding)) {
            return true;
        }
        if (typeBinding == null) {
            return false;
        }
        int iKind = typeBinding.kind();
        if (iKind == 516 || iKind == 8196) {
            return ((WildcardBinding) typeBinding).boundCheck(this);
        }
        return false;
    }

    public final boolean isHierarchyInconsistent() {
        return (this.tagBits & 131072) != 0;
    }

    public final boolean isLocalType() {
        return (this.tagBits & 16) != 0;
    }

    public final boolean isMemberType() {
        return (this.tagBits & 8) != 0;
    }

    public final boolean isNestedType() {
        return (this.tagBits & 4) != 0;
    }

    public final boolean isNumericType() {
        switch (this.f626id) {
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
                return true;
            case 5:
            case 6:
            default:
                return false;
        }
    }

    public boolean hasNullTypeAnnotations() {
        return (this.tagBits & 1048576) != 0;
    }

    public final boolean isParameterizedTypeWithActualArguments() {
        return kind() == 260 && ((ParameterizedTypeBinding) this).arguments != null;
    }

    public boolean isParameterizedWithOwnVariables() {
        if (kind() != 260) {
            return false;
        }
        ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) this;
        if (parameterizedTypeBinding.arguments == null) {
            return false;
        }
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = erasure().typeVariables();
        int length = typeVariableBindingArrTypeVariables.length;
        for (int i = 0; i < length; i++) {
            if (notEquals(typeVariableBindingArrTypeVariables[i], parameterizedTypeBinding.arguments[i])) {
                return false;
            }
        }
        ReferenceBinding referenceBindingEnclosingType = parameterizedTypeBinding.enclosingType();
        return referenceBindingEnclosingType == null || !referenceBindingEnclosingType.erasure().isGenericType() || referenceBindingEnclosingType.isParameterizedWithOwnVariables();
    }

    private boolean isProvableDistinctSubType(TypeBinding typeBinding) {
        if (typeBinding.isInterface()) {
            if (isInterface()) {
                return false;
            }
            if (isArrayType() || (((this instanceof ReferenceBinding) && ((ReferenceBinding) this).isFinal()) || (isTypeVariable() && ((TypeVariableBinding) this).superclass().isFinal()))) {
                return !isCompatibleWith(typeBinding);
            }
            return false;
        }
        if (isInterface()) {
            if (typeBinding.isArrayType() || (((typeBinding instanceof ReferenceBinding) && ((ReferenceBinding) typeBinding).isFinal()) || (typeBinding.isTypeVariable() && ((TypeVariableBinding) typeBinding).superclass().isFinal()))) {
                return !isCompatibleWith(typeBinding);
            }
        } else if (!isTypeVariable() && !typeBinding.isTypeVariable()) {
            return !isCompatibleWith(typeBinding);
        }
        return false;
    }

    public boolean isProvablyDistinct(TypeBinding typeBinding) {
        ReferenceBinding referenceBindingEnclosingType;
        ReferenceBinding referenceBindingEnclosingType2;
        if (equalsEquals(this, typeBinding)) {
            return false;
        }
        if (typeBinding == null) {
            return true;
        }
        int iKind = kind();
        if (iKind == 4) {
            int iKind2 = typeBinding.kind();
            if (iKind2 == 260 || iKind2 == 1028) {
                return notEquals(this, typeBinding.erasure());
            }
        } else {
            if (iKind == 260) {
                ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) this;
                int iKind3 = typeBinding.kind();
                if (iKind3 == 4) {
                    return notEquals(erasure(), typeBinding);
                }
                if (iKind3 == 260) {
                    ParameterizedTypeBinding parameterizedTypeBinding2 = (ParameterizedTypeBinding) typeBinding;
                    if (notEquals(parameterizedTypeBinding.genericType(), parameterizedTypeBinding2.genericType())) {
                        return true;
                    }
                    if (!parameterizedTypeBinding.isStatic() && (referenceBindingEnclosingType = enclosingType()) != null) {
                        ReferenceBinding referenceBindingEnclosingType3 = parameterizedTypeBinding2.enclosingType();
                        if (referenceBindingEnclosingType3 == null) {
                            return true;
                        }
                        if ((referenceBindingEnclosingType3.tagBits & 1073741824) == 0) {
                            if (referenceBindingEnclosingType.isProvablyDistinct(referenceBindingEnclosingType3)) {
                                return true;
                            }
                        } else if (!referenceBindingEnclosingType.isEquivalentTo(parameterizedTypeBinding2.enclosingType())) {
                            return true;
                        }
                    }
                    int length = parameterizedTypeBinding.arguments == null ? 0 : parameterizedTypeBinding.arguments.length;
                    TypeBinding[] typeBindingArr = parameterizedTypeBinding2.arguments;
                    if ((typeBindingArr == null ? 0 : typeBindingArr.length) != length) {
                        return true;
                    }
                    for (int i = 0; i < length; i++) {
                        if (parameterizedTypeBinding.arguments[i].isProvablyDistinctTypeArgument(typeBindingArr[i], parameterizedTypeBinding, i)) {
                            return true;
                        }
                    }
                    return false;
                }
                if (iKind3 != 1028) {
                    if (iKind3 != 2052 || notEquals(parameterizedTypeBinding.genericType(), typeBinding)) {
                        return true;
                    }
                    if (!parameterizedTypeBinding.isStatic() && (referenceBindingEnclosingType2 = enclosingType()) != null) {
                        ReferenceBinding referenceBindingEnclosingType4 = typeBinding.enclosingType();
                        if (referenceBindingEnclosingType4 == null) {
                            return true;
                        }
                        if ((referenceBindingEnclosingType4.tagBits & 1073741824) == 0) {
                            if (notEquals(referenceBindingEnclosingType2, referenceBindingEnclosingType4)) {
                                return true;
                            }
                        } else if (!referenceBindingEnclosingType2.isEquivalentTo(typeBinding.enclosingType())) {
                            return true;
                        }
                    }
                    int length2 = parameterizedTypeBinding.arguments == null ? 0 : parameterizedTypeBinding.arguments.length;
                    TypeVariableBinding[] typeVariableBindingArrTypeVariables = typeBinding.typeVariables();
                    if ((typeVariableBindingArrTypeVariables == null ? 0 : typeVariableBindingArrTypeVariables.length) != length2) {
                        return true;
                    }
                    for (int i2 = 0; i2 < length2; i2++) {
                        if (parameterizedTypeBinding.arguments[i2].isProvablyDistinctTypeArgument(typeVariableBindingArrTypeVariables[i2], parameterizedTypeBinding, i2)) {
                            return true;
                        }
                    }
                    return false;
                }
                return notEquals(erasure(), typeBinding.erasure());
            }
            if (iKind == 1028) {
                int iKind4 = typeBinding.kind();
                if (iKind4 == 4 || iKind4 == 260 || iKind4 == 1028 || iKind4 == 2052) {
                    return notEquals(erasure(), typeBinding.erasure());
                }
                return true;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0112  */
    /* JADX WARN: Code duplicated, block: B:106:0x0120 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:107:0x0122 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:108:0x0124  */
    /* JADX WARN: Code duplicated, block: B:110:0x012a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:111:0x012c  */
    /* JADX WARN: Code duplicated, block: B:113:0x0132  */
    /* JADX WARN: Code duplicated, block: B:117:0x013a  */
    /* JADX WARN: Code duplicated, block: B:119:0x013f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:120:0x0141  */
    /* JADX WARN: Code duplicated, block: B:122:0x0147  */
    /* JADX WARN: Code duplicated, block: B:128:0x0155 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:129:0x0157  */
    /* JADX WARN: Code duplicated, block: B:131:0x015c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:41:0x0079  */
    /* JADX WARN: Code duplicated, block: B:48:0x0089 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:50:0x008c  */
    /* JADX WARN: Code duplicated, block: B:52:0x0095  */
    /* JADX WARN: Code duplicated, block: B:54:0x0099  */
    /* JADX WARN: Code duplicated, block: B:55:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:57:0x00a8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:58:0x00aa A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:60:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:61:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:62:0x00b7 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:63:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:65:0x00bc A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:66:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:68:0x00c7 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:70:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:72:0x00d0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:73:0x00d2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:75:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:76:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:77:0x00db A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:78:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:80:0x00e3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:81:0x00e5 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:82:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:83:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:85:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:87:0x00f3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:88:0x00f5 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:89:0x00f6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:90:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:92:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:98:0x010c  */
    private boolean isProvablyDistinctTypeArgument(TypeBinding typeBinding, ParameterizedTypeBinding parameterizedTypeBinding, int i) {
        TypeBinding typeBinding2;
        TypeBinding typeBinding3;
        int iKind;
        WildcardBinding wildcardBinding;
        int i2;
        TypeBinding typeBinding4;
        TypeBinding typeBinding5;
        TypeVariableBinding typeVariableBinding;
        TypeBinding typeBindingConvertEliminatingTypeVariables;
        int iKind2;
        WildcardBinding wildcardBinding2;
        int i3;
        CaptureBinding captureBinding;
        int i4;
        if (equalsEquals(this, typeBinding)) {
            return false;
        }
        ReferenceBinding referenceBindingGenericType = parameterizedTypeBinding.genericType();
        int iKind3 = kind();
        TypeBinding typeBinding6 = null;
        if (iKind3 == 516) {
            WildcardBinding wildcardBinding3 = (WildcardBinding) this;
            int i5 = wildcardBinding3.boundKind;
            if (i5 != 0) {
                if (i5 == 1) {
                    typeBinding2 = wildcardBinding3.bound;
                    typeBinding3 = null;
                    iKind = typeBinding.kind();
                    if (iKind == 516) {
                        wildcardBinding = (WildcardBinding) typeBinding;
                        i2 = wildcardBinding.boundKind;
                        if (i2 != 0) {
                            if (i2 == 1) {
                                typeBinding4 = wildcardBinding.bound;
                            } else if (i2 != 2) {
                                typeBinding4 = null;
                            } else {
                                typeBinding5 = wildcardBinding.bound;
                                TypeBinding typeBinding7 = typeBinding6;
                                typeBinding6 = typeBinding5;
                                typeBinding4 = typeBinding7;
                            }
                        }
                    } else if (iKind != 4100) {
                        typeBinding4 = null;
                    } else {
                        typeVariableBinding = (TypeVariableBinding) typeBinding;
                        if (typeVariableBinding.isCapture()) {
                            if (typeVariableBinding instanceof CaptureBinding18) {
                                CaptureBinding18 captureBinding18 = (CaptureBinding18) typeVariableBinding;
                                typeBinding6 = captureBinding18.firstBound;
                                typeBinding5 = captureBinding18.lowerBound;
                            } else {
                                captureBinding = (CaptureBinding) typeVariableBinding;
                                i4 = captureBinding.wildcard.boundKind;
                                if (i4 == 0) {
                                    return false;
                                }
                                if (i4 == 1) {
                                    typeBinding4 = captureBinding.wildcard.bound;
                                } else if (i4 != 2) {
                                    typeBinding4 = null;
                                } else {
                                    typeBinding5 = captureBinding.wildcard.bound;
                                }
                            }
                            TypeBinding typeBinding8 = typeBinding6;
                            typeBinding6 = typeBinding5;
                            typeBinding4 = typeBinding8;
                        } else {
                            if (typeVariableBinding.firstBound == null) {
                                return false;
                            }
                            typeBindingConvertEliminatingTypeVariables = Scope.convertEliminatingTypeVariables(typeVariableBinding, referenceBindingGenericType, i, null);
                            iKind2 = typeBindingConvertEliminatingTypeVariables.kind();
                            if (iKind2 != 516) {
                                wildcardBinding2 = (WildcardBinding) typeBindingConvertEliminatingTypeVariables;
                                i3 = wildcardBinding2.boundKind;
                                if (i3 == 0) {
                                    return false;
                                }
                                if (i3 == 1) {
                                    typeBinding4 = wildcardBinding2.bound;
                                } else if (i3 != 2) {
                                    typeBinding4 = null;
                                } else {
                                    typeBinding5 = wildcardBinding2.bound;
                                    TypeBinding typeBinding9 = typeBinding6;
                                    typeBinding6 = typeBinding5;
                                    typeBinding4 = typeBinding9;
                                }
                            } else {
                                wildcardBinding2 = (WildcardBinding) typeBindingConvertEliminatingTypeVariables;
                                i3 = wildcardBinding2.boundKind;
                                if (i3 == 0) {
                                    return false;
                                }
                                if (i3 == 1) {
                                    typeBinding4 = wildcardBinding2.bound;
                                } else if (i3 != 2) {
                                    typeBinding4 = null;
                                } else {
                                    typeBinding5 = wildcardBinding2.bound;
                                    TypeBinding typeBinding10 = typeBinding6;
                                    typeBinding6 = typeBinding5;
                                    typeBinding4 = typeBinding10;
                                }
                            }
                        }
                    }
                    if (typeBinding3 != null) {
                        if (typeBinding6 != null) {
                            return false;
                        }
                        if (typeBinding4 != null) {
                            if (typeBinding3.isTypeVariable()) {
                            }
                            return false;
                        }
                        if (typeBinding3.isTypeVariable()) {
                        }
                        return false;
                    }
                    if (typeBinding2 != null) {
                        if (typeBinding6 != null) {
                            return !typeBinding6.isCompatibleWith(typeBinding2);
                        }
                        if (typeBinding4 != null) {
                            if (typeBinding2.isProvableDistinctSubType(typeBinding4)) {
                            }
                        }
                        return typeBinding.isProvableDistinctSubType(typeBinding2);
                    }
                    if (typeBinding6 == null) {
                        if (typeBinding6.isTypeVariable()) {
                        }
                        return false;
                    }
                    if (typeBinding4 != null) {
                        return isProvableDistinctSubType(typeBinding4);
                    }
                    return true;
                }
                if (i5 != 2) {
                    typeBinding3 = null;
                    typeBinding2 = null;
                } else {
                    typeBinding3 = wildcardBinding3.bound;
                    typeBinding2 = null;
                }
                iKind = typeBinding.kind();
                if (iKind == 516) {
                    wildcardBinding = (WildcardBinding) typeBinding;
                    i2 = wildcardBinding.boundKind;
                    if (i2 != 0) {
                        if (i2 == 1) {
                            typeBinding4 = wildcardBinding.bound;
                        } else if (i2 != 2) {
                            typeBinding4 = null;
                        } else {
                            typeBinding5 = wildcardBinding.bound;
                            TypeBinding typeBinding11 = typeBinding6;
                            typeBinding6 = typeBinding5;
                            typeBinding4 = typeBinding11;
                        }
                    }
                } else if (iKind != 4100) {
                    typeBinding4 = null;
                } else {
                    typeVariableBinding = (TypeVariableBinding) typeBinding;
                    if (typeVariableBinding.isCapture()) {
                        if (typeVariableBinding instanceof CaptureBinding18) {
                            CaptureBinding18 captureBinding19 = (CaptureBinding18) typeVariableBinding;
                            typeBinding6 = captureBinding19.firstBound;
                            typeBinding5 = captureBinding19.lowerBound;
                        } else {
                            captureBinding = (CaptureBinding) typeVariableBinding;
                            i4 = captureBinding.wildcard.boundKind;
                            if (i4 == 0) {
                                return false;
                            }
                            if (i4 == 1) {
                                typeBinding4 = captureBinding.wildcard.bound;
                            } else if (i4 != 2) {
                                typeBinding4 = null;
                            } else {
                                typeBinding5 = captureBinding.wildcard.bound;
                            }
                        }
                        TypeBinding typeBinding12 = typeBinding6;
                        typeBinding6 = typeBinding5;
                        typeBinding4 = typeBinding12;
                    } else {
                        if (typeVariableBinding.firstBound == null) {
                            return false;
                        }
                        typeBindingConvertEliminatingTypeVariables = Scope.convertEliminatingTypeVariables(typeVariableBinding, referenceBindingGenericType, i, null);
                        iKind2 = typeBindingConvertEliminatingTypeVariables.kind();
                        if (iKind2 != 516) {
                            wildcardBinding2 = (WildcardBinding) typeBindingConvertEliminatingTypeVariables;
                            i3 = wildcardBinding2.boundKind;
                            if (i3 == 0) {
                                return false;
                            }
                            if (i3 == 1) {
                                typeBinding4 = wildcardBinding2.bound;
                            } else if (i3 != 2) {
                                typeBinding4 = null;
                            } else {
                                typeBinding5 = wildcardBinding2.bound;
                                TypeBinding typeBinding13 = typeBinding6;
                                typeBinding6 = typeBinding5;
                                typeBinding4 = typeBinding13;
                            }
                        } else {
                            wildcardBinding2 = (WildcardBinding) typeBindingConvertEliminatingTypeVariables;
                            i3 = wildcardBinding2.boundKind;
                            if (i3 == 0) {
                                return false;
                            }
                            if (i3 == 1) {
                                typeBinding4 = wildcardBinding2.bound;
                            } else if (i3 != 2) {
                                typeBinding4 = null;
                            } else {
                                typeBinding5 = wildcardBinding2.bound;
                                TypeBinding typeBinding14 = typeBinding6;
                                typeBinding6 = typeBinding5;
                                typeBinding4 = typeBinding14;
                            }
                        }
                    }
                }
                if (typeBinding3 != null) {
                    if (typeBinding6 != null) {
                        return false;
                    }
                    if (typeBinding4 != null) {
                        if (typeBinding3.isTypeVariable()) {
                        }
                        return false;
                    }
                    if (typeBinding3.isTypeVariable()) {
                    }
                    return false;
                }
                if (typeBinding2 != null) {
                    if (typeBinding6 != null) {
                        return !typeBinding6.isCompatibleWith(typeBinding2);
                    }
                    if (typeBinding4 != null) {
                        if (typeBinding2.isProvableDistinctSubType(typeBinding4)) {
                        }
                    }
                    return typeBinding.isProvableDistinctSubType(typeBinding2);
                }
                if (typeBinding6 == null) {
                    if (typeBinding6.isTypeVariable()) {
                    }
                    return false;
                }
                if (typeBinding4 != null) {
                    return isProvableDistinctSubType(typeBinding4);
                }
                return true;
            }
        } else {
            if (iKind3 != 4100) {
                typeBinding3 = null;
                typeBinding2 = null;
            } else {
                TypeVariableBinding typeVariableBinding2 = (TypeVariableBinding) this;
                if (typeVariableBinding2.isCapture()) {
                    if (typeVariableBinding2 instanceof CaptureBinding18) {
                        CaptureBinding18 captureBinding110 = (CaptureBinding18) typeVariableBinding2;
                        typeBinding2 = captureBinding110.firstBound;
                        typeBinding3 = captureBinding110.lowerBound;
                    } else {
                        CaptureBinding captureBinding2 = (CaptureBinding) typeVariableBinding2;
                        int i6 = captureBinding2.wildcard.boundKind;
                        if (i6 == 0) {
                            return false;
                        }
                        if (i6 == 1) {
                            typeBinding2 = captureBinding2.wildcard.bound;
                            typeBinding3 = null;
                        } else if (i6 != 2) {
                            typeBinding3 = null;
                            typeBinding2 = null;
                        } else {
                            typeBinding3 = captureBinding2.wildcard.bound;
                            typeBinding2 = null;
                        }
                    }
                } else {
                    if (typeVariableBinding2.firstBound == null) {
                        return false;
                    }
                    TypeBinding typeBindingConvertEliminatingTypeVariables2 = Scope.convertEliminatingTypeVariables(typeVariableBinding2, referenceBindingGenericType, i, null);
                    int iKind4 = typeBindingConvertEliminatingTypeVariables2.kind();
                    if (iKind4 == 516 || iKind4 == 8196) {
                        WildcardBinding wildcardBinding4 = (WildcardBinding) typeBindingConvertEliminatingTypeVariables2;
                        int i7 = wildcardBinding4.boundKind;
                        if (i7 == 0) {
                            return false;
                        }
                        if (i7 == 1) {
                            typeBinding2 = wildcardBinding4.bound;
                            typeBinding3 = null;
                        } else if (i7 == 2) {
                            typeBinding3 = wildcardBinding4.bound;
                            typeBinding2 = null;
                        }
                    }
                    typeBinding3 = null;
                    typeBinding2 = null;
                }
            }
            iKind = typeBinding.kind();
            if (iKind == 516) {
                wildcardBinding = (WildcardBinding) typeBinding;
                i2 = wildcardBinding.boundKind;
                if (i2 != 0) {
                    if (i2 == 1) {
                        typeBinding4 = wildcardBinding.bound;
                    } else if (i2 != 2) {
                        typeBinding4 = null;
                    } else {
                        typeBinding5 = wildcardBinding.bound;
                        TypeBinding typeBinding15 = typeBinding6;
                        typeBinding6 = typeBinding5;
                        typeBinding4 = typeBinding15;
                    }
                }
            } else if (iKind != 4100) {
                typeBinding4 = null;
            } else {
                typeVariableBinding = (TypeVariableBinding) typeBinding;
                if (typeVariableBinding.isCapture()) {
                    if (typeVariableBinding instanceof CaptureBinding18) {
                        CaptureBinding18 captureBinding111 = (CaptureBinding18) typeVariableBinding;
                        typeBinding6 = captureBinding111.firstBound;
                        typeBinding5 = captureBinding111.lowerBound;
                    } else {
                        captureBinding = (CaptureBinding) typeVariableBinding;
                        i4 = captureBinding.wildcard.boundKind;
                        if (i4 == 0) {
                            return false;
                        }
                        if (i4 == 1) {
                            typeBinding4 = captureBinding.wildcard.bound;
                        } else if (i4 != 2) {
                            typeBinding4 = null;
                        } else {
                            typeBinding5 = captureBinding.wildcard.bound;
                        }
                    }
                    TypeBinding typeBinding16 = typeBinding6;
                    typeBinding6 = typeBinding5;
                    typeBinding4 = typeBinding16;
                } else {
                    if (typeVariableBinding.firstBound == null) {
                        return false;
                    }
                    typeBindingConvertEliminatingTypeVariables = Scope.convertEliminatingTypeVariables(typeVariableBinding, referenceBindingGenericType, i, null);
                    iKind2 = typeBindingConvertEliminatingTypeVariables.kind();
                    if (iKind2 != 516 || iKind2 == 8196) {
                        wildcardBinding2 = (WildcardBinding) typeBindingConvertEliminatingTypeVariables;
                        i3 = wildcardBinding2.boundKind;
                        if (i3 == 0) {
                            return false;
                        }
                        if (i3 == 1) {
                            typeBinding4 = wildcardBinding2.bound;
                        } else if (i3 != 2) {
                            typeBinding4 = null;
                        } else {
                            typeBinding5 = wildcardBinding2.bound;
                            TypeBinding typeBinding17 = typeBinding6;
                            typeBinding6 = typeBinding5;
                            typeBinding4 = typeBinding17;
                        }
                    } else {
                        typeBinding4 = null;
                    }
                }
            }
            if (typeBinding3 != null) {
                if (typeBinding6 != null) {
                    return false;
                }
                if (typeBinding4 != null) {
                    if (!typeBinding3.isTypeVariable() || typeBinding4.isTypeVariable()) {
                        return false;
                    }
                    return !typeBinding3.isCompatibleWith(typeBinding4);
                }
                if (!typeBinding3.isTypeVariable() || typeBinding.isTypeVariable()) {
                    return false;
                }
                return !typeBinding3.isCompatibleWith(typeBinding);
            }
            if (typeBinding2 != null) {
                if (typeBinding6 != null) {
                    return !typeBinding6.isCompatibleWith(typeBinding2);
                }
                if (typeBinding4 != null) {
                    return !typeBinding2.isProvableDistinctSubType(typeBinding4) && typeBinding4.isProvableDistinctSubType(typeBinding2);
                }
                return typeBinding.isProvableDistinctSubType(typeBinding2);
            }
            if (typeBinding6 == null) {
                if (typeBinding4 != null) {
                    return isProvableDistinctSubType(typeBinding4);
                }
                return true;
            }
            if (!typeBinding6.isTypeVariable() || isTypeVariable()) {
                return false;
            }
            return !typeBinding6.isCompatibleWith(this);
        }
        return false;
    }

    public final boolean isRawType() {
        return kind() == 1028;
    }

    public boolean isReifiable() {
        MethodBinding methodBinding;
        TypeBinding typeBindingLeafComponentType = leafComponentType();
        if (!(typeBindingLeafComponentType instanceof ReferenceBinding)) {
            return true;
        }
        ReferenceBinding referenceBindingEnclosingType = (ReferenceBinding) typeBindingLeafComponentType;
        do {
            int iKind = referenceBindingEnclosingType.kind();
            if (iKind != 260) {
                if (iKind != 516) {
                    if (iKind == 1028) {
                        return true;
                    }
                    if (iKind == 2052 || iKind == 4100 || iKind == 8196) {
                    }
                }
                return false;
            }
            if (referenceBindingEnclosingType.isBoundParameterizedType()) {
                return false;
            }
            if (referenceBindingEnclosingType.isStatic()) {
                return true;
            }
            if (referenceBindingEnclosingType.isLocalType() && (methodBinding = ((LocalTypeBinding) referenceBindingEnclosingType.erasure()).enclosingMethod) != null && methodBinding.isStatic()) {
                return true;
            }
            referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
        } while (referenceBindingEnclosingType != null);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:71:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:73:0x00ee A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:74:0x00f0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:76:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:77:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:79:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:80:0x00fd  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public boolean isTypeArgumentContainedBy(TypeBinding typeBinding) {
        ReferenceBinding referenceBindingEnclosingType;
        WildcardBinding wildcardBinding;
        int i;
        TypeBinding typeBinding2;
        TypeBinding typeBindingUpperBound;
        TypeBinding[] typeBindingArrOtherUpperBounds;
        WildcardBinding wildcardBindingCreateWildcard;
        if (equalsEquals(this, typeBinding)) {
            return true;
        }
        int iKind = typeBinding.kind();
        if (iKind != 260) {
            TypeBinding typeBinding3 = null;
            TypeBinding[] typeBindingArr = null;
            typeBinding3 = null;
            if (iKind != 516) {
                if (iKind == 4100) {
                    if (!isParameterizedType() || !typeBinding.isCapture()) {
                        return false;
                    }
                    CaptureBinding captureBinding = (CaptureBinding) typeBinding;
                    if (captureBinding instanceof CaptureBinding18) {
                        CaptureBinding18 captureBinding18 = (CaptureBinding18) captureBinding;
                        if (captureBinding18.firstBound != null) {
                            if (captureBinding18.lowerBound != null) {
                                return false;
                            }
                            int length = captureBinding18.upperBounds.length;
                            if (length > 1) {
                                int i2 = length - 1;
                                typeBindingArr = new TypeBinding[i2];
                                System.arraycopy(captureBinding18.upperBounds, 1, typeBindingArr, 0, i2);
                            }
                            wildcardBindingCreateWildcard = captureBinding.environment.createWildcard(null, 0, captureBinding18.firstBound, typeBindingArr, 1);
                        } else {
                            if (captureBinding18.lowerBound == null) {
                                return false;
                            }
                            wildcardBindingCreateWildcard = captureBinding.environment.createWildcard(null, 0, captureBinding18.lowerBound, null, 2);
                        }
                    } else {
                        WildcardBinding wildcardBinding2 = captureBinding.wildcard;
                        int i3 = wildcardBinding2.boundKind;
                        if (i3 == 0) {
                            TypeVariableBinding typeVariableBinding = wildcardBinding2.genericType.typeVariables()[wildcardBinding2.rank];
                            typeBindingUpperBound = typeVariableBinding.upperBound();
                            typeBindingArrOtherUpperBounds = typeVariableBinding.boundsCount() > 1 ? typeVariableBinding.otherUpperBounds() : null;
                        } else if (i3 == 1) {
                            TypeBinding typeBinding4 = wildcardBinding2.bound;
                            typeBindingArrOtherUpperBounds = wildcardBinding2.otherBounds;
                            typeBindingUpperBound = typeBinding4;
                        } else {
                            if (i3 == 2) {
                                return false;
                            }
                            typeBindingUpperBound = null;
                            typeBindingArrOtherUpperBounds = null;
                        }
                        if (typeBindingUpperBound.f626id == 1 && typeBindingArrOtherUpperBounds == null) {
                            return false;
                        }
                        wildcardBindingCreateWildcard = captureBinding.environment.createWildcard(null, 0, typeBindingUpperBound, typeBindingArrOtherUpperBounds, 1);
                    }
                    return isTypeArgumentContainedBy(wildcardBindingCreateWildcard);
                }
                if (iKind != 8196) {
                    if (typeBinding.f626id == 1 && kind() == 516) {
                        WildcardBinding wildcardBinding3 = (WildcardBinding) this;
                        if (wildcardBinding3.boundKind == 2 && wildcardBinding3.bound.f626id == 1) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            int iKind2 = kind();
            if (iKind2 == 516) {
                wildcardBinding = (WildcardBinding) this;
                i = wildcardBinding.boundKind;
                typeBinding2 = wildcardBinding;
                if (i != 0) {
                    if (i != 1) {
                        if (i == 2) {
                            TypeBinding typeBinding5 = this;
                            typeBinding3 = typeBinding5;
                            typeBinding2 = typeBinding5;
                        } else {
                            TypeBinding typeBinding6 = wildcardBinding.bound;
                            typeBinding2 = wildcardBinding;
                            typeBinding3 = typeBinding6;
                        }
                    } else if (wildcardBinding.otherBounds == null) {
                        TypeBinding typeBinding7 = this;
                        typeBinding3 = typeBinding7;
                        typeBinding2 = typeBinding7;
                    } else {
                        typeBinding2 = wildcardBinding.bound;
                    }
                }
            } else {
                if (iKind2 != 4100) {
                    if (iKind2 == 8196) {
                        wildcardBinding = (WildcardBinding) this;
                        i = wildcardBinding.boundKind;
                        typeBinding2 = wildcardBinding;
                        if (i != 0) {
                            if (i != 1) {
                                if (i == 2) {
                                    TypeBinding typeBinding8 = wildcardBinding.bound;
                                    typeBinding2 = wildcardBinding;
                                    typeBinding3 = typeBinding8;
                                }
                            } else if (wildcardBinding.otherBounds == null) {
                                typeBinding2 = wildcardBinding.bound;
                            }
                        }
                    }
                } else if (isCapture()) {
                    CaptureBinding captureBinding2 = (CaptureBinding) this;
                    if (captureBinding2.lowerBound != null) {
                        TypeBinding typeBinding9 = captureBinding2.lowerBound;
                        typeBinding2 = this;
                        typeBinding3 = typeBinding9;
                    }
                }
                TypeBinding typeBinding10 = this;
                typeBinding3 = typeBinding10;
                typeBinding2 = typeBinding10;
            }
            WildcardBinding wildcardBinding4 = (WildcardBinding) typeBinding;
            if (wildcardBinding4.otherBounds != null) {
                return false;
            }
            TypeBinding typeBinding11 = wildcardBinding4.bound;
            int i4 = wildcardBinding4.boundKind;
            if (i4 == 1) {
                if (typeBinding11 instanceof IntersectionTypeBinding18) {
                    for (ReferenceBinding referenceBinding : ((IntersectionTypeBinding18) typeBinding11).intersectingTypes) {
                        if (equalsEquals(referenceBinding, this)) {
                            return true;
                        }
                    }
                }
                if (equalsEquals(typeBinding11, this)) {
                    return true;
                }
                if (typeBinding2 == null) {
                    return false;
                }
                TypeBinding typeBindingFindSuperTypeOriginatingFrom = typeBinding2.findSuperTypeOriginatingFrom(typeBinding11);
                if (typeBindingFindSuperTypeOriginatingFrom != null) {
                    TypeBinding typeBindingLeafComponentType = typeBindingFindSuperTypeOriginatingFrom.leafComponentType();
                    if (typeBindingLeafComponentType.isRawType()) {
                        return equalsEquals(typeBindingLeafComponentType, typeBinding11.leafComponentType());
                    }
                }
                return typeBinding2.isCompatibleWith(typeBinding11);
            }
            if (i4 != 2) {
                return true;
            }
            if (typeBinding11 instanceof IntersectionTypeBinding18) {
                for (ReferenceBinding referenceBinding2 : ((IntersectionTypeBinding18) typeBinding11).intersectingTypes) {
                    if (equalsEquals(referenceBinding2, this)) {
                        return true;
                    }
                }
            }
            if (equalsEquals(typeBinding11, this)) {
                return true;
            }
            if (typeBinding3 == null) {
                return false;
            }
            TypeBinding typeBindingFindSuperTypeOriginatingFrom2 = typeBinding11.findSuperTypeOriginatingFrom(typeBinding3);
            if (typeBindingFindSuperTypeOriginatingFrom2 != null) {
                TypeBinding typeBindingLeafComponentType2 = typeBindingFindSuperTypeOriginatingFrom2.leafComponentType();
                if (typeBindingLeafComponentType2.isRawType()) {
                    return equalsEquals(typeBindingLeafComponentType2, typeBinding3.leafComponentType());
                }
            }
            return typeBinding11.isCompatibleWith(typeBinding3);
        }
        if (!isParameterizedType()) {
            return false;
        }
        ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) this;
        ParameterizedTypeBinding parameterizedTypeBinding2 = (ParameterizedTypeBinding) typeBinding;
        if (notEquals(parameterizedTypeBinding.actualType(), parameterizedTypeBinding2.actualType())) {
            return false;
        }
        if (!parameterizedTypeBinding.isStatic() && (referenceBindingEnclosingType = enclosingType()) != null) {
            ReferenceBinding referenceBindingEnclosingType2 = parameterizedTypeBinding2.enclosingType();
            if (referenceBindingEnclosingType2 == null) {
                return false;
            }
            if ((referenceBindingEnclosingType2.tagBits & 1073741824) == 0) {
                if (notEquals(referenceBindingEnclosingType, referenceBindingEnclosingType2)) {
                    return false;
                }
            } else if (!referenceBindingEnclosingType.isEquivalentTo(parameterizedTypeBinding2.enclosingType())) {
                return false;
            }
        }
        int length2 = parameterizedTypeBinding.arguments == null ? 0 : parameterizedTypeBinding.arguments.length;
        TypeBinding[] typeBindingArr2 = parameterizedTypeBinding2.arguments;
        if ((typeBindingArr2 == null ? 0 : typeBindingArr2.length) != length2) {
            return false;
        }
        for (int i5 = 0; i5 < length2; i5++) {
            TypeBinding typeBinding12 = parameterizedTypeBinding.arguments[i5];
            TypeBinding typeBinding13 = typeBindingArr2[i5];
            if (!equalsEquals(typeBinding12, typeBinding13)) {
                int iKind3 = typeBinding12.kind();
                if (typeBinding13.kind() != iKind3) {
                    return false;
                }
                if (iKind3 != 260) {
                    if (iKind3 == 516 || iKind3 == 8196) {
                        WildcardBinding wildcardBinding5 = (WildcardBinding) typeBinding12;
                        WildcardBinding wildcardBinding6 = (WildcardBinding) typeBinding13;
                        int i6 = wildcardBinding5.boundKind;
                        if (i6 != 0) {
                            if (i6 == 1 && wildcardBinding6.boundKind == 0 && equalsEquals(wildcardBinding5.bound, wildcardBinding5.typeVariable().upperBound())) {
                            }
                        } else if (wildcardBinding6.boundKind != 1 || !equalsEquals(wildcardBinding6.bound, wildcardBinding6.typeVariable().upperBound())) {
                        }
                    }
                    return false;
                }
                if (!typeBinding12.isTypeArgumentContainedBy(typeBinding13)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean needsUncheckedConversion(TypeBinding typeBinding) {
        if (equalsEquals(this, typeBinding)) {
            return false;
        }
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        if (!(typeBindingLeafComponentType instanceof ReferenceBinding)) {
            return false;
        }
        TypeBinding typeBindingFindSuperTypeOriginatingFrom = leafComponentType().findSuperTypeOriginatingFrom(typeBindingLeafComponentType);
        if (!(typeBindingFindSuperTypeOriginatingFrom instanceof ReferenceBinding)) {
            return false;
        }
        ReferenceBinding referenceBindingEnclosingType = (ReferenceBinding) typeBindingFindSuperTypeOriginatingFrom;
        while (referenceBindingEnclosingType.isRawType()) {
            if (!typeBindingLeafComponentType.isBoundParameterizedType()) {
                if (referenceBindingEnclosingType.isStatic() || (referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType()) == null || (typeBindingLeafComponentType = typeBindingLeafComponentType.enclosingType()) == null) {
                    break;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public char[] nullAnnotatedReadableName(CompilerOptions compilerOptions, boolean z) {
        if (z) {
            return shortReadableName();
        }
        return readableName();
    }

    public TypeBinding original() {
        int iKind = kind();
        if (iKind == 68 || iKind == 260 || iKind == 1028) {
            return erasure().unannotated();
        }
        return unannotated();
    }

    public final boolean hasTypeAnnotations() {
        return (this.tagBits & 2097152) != 0;
    }

    public char[] qualifiedPackageName() {
        PackageBinding packageBinding = getPackage();
        if (packageBinding == null || packageBinding.compoundName == CharOperation.NO_CHAR_CHAR) {
            return CharOperation.NO_CHAR;
        }
        return packageBinding.readableName();
    }

    public final AnnotationBinding[] getTypeAnnotations() {
        return this.typeAnnotations;
    }

    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr, boolean z) {
        this.tagBits |= 2097152;
        if (annotationBindingArr == null || annotationBindingArr.length == 0) {
            return;
        }
        this.typeAnnotations = annotationBindingArr;
        if (z) {
            for (AnnotationBinding annotationBinding : annotationBindingArr) {
                if (annotationBinding != null) {
                    if (annotationBinding.type.hasNullBit(64)) {
                        this.tagBits |= 36028797020012544L;
                    } else if (annotationBinding.type.hasNullBit(32)) {
                        this.tagBits |= 72057594038976512L;
                    }
                }
            }
        }
    }

    public char[] signableName() {
        return readableName();
    }

    public char[] signature() {
        return constantPoolName();
    }

    public TypeVariableBinding[] typeVariables() {
        return Binding.NO_TYPE_VARIABLES;
    }

    public static boolean equalsEquals(TypeBinding typeBinding, TypeBinding typeBinding2) {
        int i;
        if (typeBinding == typeBinding2) {
            return true;
        }
        return (typeBinding == null || typeBinding2 == null || (i = typeBinding.f626id) == Integer.MAX_VALUE || i != typeBinding2.f626id) ? false : true;
    }

    public static boolean notEquals(TypeBinding typeBinding, TypeBinding typeBinding2) {
        int i;
        if (typeBinding == typeBinding2) {
            return false;
        }
        return typeBinding == null || typeBinding2 == null || (i = typeBinding.f626id) == Integer.MAX_VALUE || i != typeBinding2.f626id;
    }

    public boolean mentionsAny(TypeBinding[] typeBindingArr, int i) {
        for (int i2 = 0; i2 < typeBindingArr.length; i2++) {
            if (i2 != i && equalsEquals(typeBindingArr[i2], this)) {
                return true;
            }
        }
        return false;
    }

    public boolean sIsMoreSpecific(TypeBinding typeBinding, TypeBinding typeBinding2, Scope scope) {
        return typeBinding.isCompatibleWith(typeBinding2, scope) && !typeBinding.needsUncheckedConversion(typeBinding2);
    }

    public boolean isSubtypeOf(TypeBinding typeBinding) {
        return isCompatibleWith(typeBinding);
    }

    public MethodBinding[] getMethods(char[] cArr) {
        return Binding.NO_METHODS;
    }

    public ReferenceBinding[] superInterfaces() {
        return Binding.NO_SUPERINTERFACES;
    }

    public long updateTagBits() {
        return this.tagBits & 1048576;
    }
}
