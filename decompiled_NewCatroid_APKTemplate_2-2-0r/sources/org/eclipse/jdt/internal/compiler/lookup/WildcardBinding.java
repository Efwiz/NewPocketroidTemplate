package org.eclipse.jdt.internal.compiler.lookup;

import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.Set;
import kotlin.text.Typography;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Wildcard;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes14.dex */
public class WildcardBinding extends ReferenceBinding {
    public TypeBinding bound;
    public int boundKind;
    LookupEnvironment environment;
    char[] genericSignature;
    public ReferenceBinding genericType;
    boolean inRecursiveFunction = false;
    public TypeBinding[] otherBounds;
    public int rank;
    ReferenceBinding[] superInterfaces;
    ReferenceBinding superclass;
    TypeVariableBinding typeVariable;

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean acceptsNonNullDefault() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean canBeInstantiated() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isWildcard() {
        return true;
    }

    public WildcardBinding(ReferenceBinding referenceBinding, int i, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i2, LookupEnvironment lookupEnvironment) {
        this.rank = i;
        this.boundKind = i2;
        this.modifiers = CompilerOptions.ShouldImplementHashcode;
        this.environment = lookupEnvironment;
        initialize(referenceBinding, typeBinding, typeBindingArr);
        if (referenceBinding instanceof UnresolvedReferenceBinding) {
            ((UnresolvedReferenceBinding) referenceBinding).addWrapper(this, lookupEnvironment);
        }
        if (typeBinding instanceof UnresolvedReferenceBinding) {
            ((UnresolvedReferenceBinding) typeBinding).addWrapper(this, lookupEnvironment);
        }
        this.tagBits |= TagBits.HasUnresolvedTypeVariables;
        this.typeBits = 134217728;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding bound() {
        return this.bound;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    int boundKind() {
        return this.boundKind;
    }

    public TypeBinding allBounds() {
        TypeBinding[] typeBindingArr = this.otherBounds;
        if (typeBindingArr == null || typeBindingArr.length == 0) {
            return this.bound;
        }
        ReferenceBinding[] referenceBindingArr = new ReferenceBinding[typeBindingArr.length + 1];
        try {
            referenceBindingArr[0] = (ReferenceBinding) this.bound;
            System.arraycopy(typeBindingArr, 0, referenceBindingArr, 1, typeBindingArr.length);
            return this.environment.createIntersectionType18(referenceBindingArr);
        } catch (ArrayStoreException unused) {
            return this.bound;
        } catch (ClassCastException unused2) {
            return this.bound;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr, boolean z) {
        this.tagBits |= 2097152;
        if (annotationBindingArr != null && annotationBindingArr.length != 0) {
            this.typeAnnotations = annotationBindingArr;
        }
        if (z) {
            evaluateNullAnnotations(null, null);
        }
    }

    public void evaluateNullAnnotations(Scope scope, Wildcard wildcard) {
        TypeVariableBinding typeVariableBindingTypeVariable;
        long jDetermineNullBitsFromDeclaration = determineNullBitsFromDeclaration(scope, wildcard);
        if (jDetermineNullBitsFromDeclaration == 0 && (typeVariableBindingTypeVariable = typeVariable()) != null) {
            long j = typeVariableBindingTypeVariable.tagBits & TagBits.AnnotationNullMASK;
            if (j != 0) {
                jDetermineNullBitsFromDeclaration = j;
            }
        }
        if (jDetermineNullBitsFromDeclaration != 0) {
            this.tagBits = jDetermineNullBitsFromDeclaration | (this.tagBits & (-108086391056891905L)) | 1048576;
        }
    }

    public long determineNullBitsFromDeclaration(Scope scope, Wildcard wildcard) {
        long j;
        long j2;
        TypeBinding[] typeBindingArr;
        Annotation annotationFindAnnotation;
        Annotation annotationFindAnnotation2;
        AnnotationBinding[] annotationBindingArr = this.typeAnnotations;
        if (annotationBindingArr != null) {
            j = 0;
            for (AnnotationBinding annotationBinding : annotationBindingArr) {
                if (annotationBinding != null) {
                    if (annotationBinding.type.hasNullBit(64)) {
                        if ((j & 72057594037927936L) == 0) {
                            j |= 36028797018963968L;
                        } else if (wildcard != null && (annotationFindAnnotation2 = wildcard.findAnnotation(36028797018963968L)) != null) {
                            scope.problemReporter().contradictoryNullAnnotations(annotationFindAnnotation2);
                        }
                    } else if (annotationBinding.type.hasNullBit(32)) {
                        if ((j & 36028797018963968L) == 0) {
                            j |= 72057594037927936L;
                        } else if (wildcard != null && (annotationFindAnnotation = wildcard.findAnnotation(72057594037927936L)) != null) {
                            scope.problemReporter().contradictoryNullAnnotations(annotationFindAnnotation);
                        }
                    }
                }
            }
        } else {
            j = 0;
        }
        TypeBinding typeBinding = this.bound;
        if (typeBinding != null && typeBinding.isValidBinding()) {
            long j3 = this.bound.tagBits;
            long j4 = TagBits.AnnotationNullMASK & j3;
            if (j4 != 0) {
                if (this.boundKind != 2) {
                    if ((j3 & 72057594037927936L) == 0) {
                        j2 = j;
                    } else if (j == 0) {
                        j2 = 72057594037927936L;
                    } else {
                        if (wildcard != null && (j & 36028797018963968L) != 0) {
                            Annotation annotationFindAnnotation3 = wildcard.bound.findAnnotation(j4);
                            if (annotationFindAnnotation3 == null) {
                                TypeBinding typeBindingWithoutToplevelNullAnnotation = this.bound.withoutToplevelNullAnnotation();
                                this.bound = typeBindingWithoutToplevelNullAnnotation;
                                wildcard.bound.resolvedType = typeBindingWithoutToplevelNullAnnotation;
                            } else {
                                scope.problemReporter().contradictoryNullAnnotationsOnBounds(annotationFindAnnotation3, j);
                            }
                        }
                        j2 = j;
                    }
                    if (j2 != 0 || (typeBindingArr = this.otherBounds) == null) {
                        return j2;
                    }
                    int length = typeBindingArr.length;
                    for (int i = 0; i < length; i++) {
                        if ((this.otherBounds[i].tagBits & 72057594037927936L) != 0) {
                            return 72057594037927936L;
                        }
                    }
                    return j2;
                }
                if ((j3 & 36028797018963968L) != 0) {
                    if (j == 0) {
                        return 36028797018963968L;
                    }
                    if (wildcard != null && (j & 72057594037927936L) != 0) {
                        Annotation annotationFindAnnotation4 = wildcard.bound.findAnnotation(j4);
                        if (annotationFindAnnotation4 == null) {
                            TypeBinding typeBindingWithoutToplevelNullAnnotation2 = this.bound.withoutToplevelNullAnnotation();
                            this.bound = typeBindingWithoutToplevelNullAnnotation2;
                            wildcard.bound.resolvedType = typeBindingWithoutToplevelNullAnnotation2;
                        } else {
                            scope.problemReporter().contradictoryNullAnnotationsOnBounds(annotationFindAnnotation4, j);
                        }
                    }
                }
            }
        }
        return j;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding actualType() {
        return this.genericType;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding[] additionalBounds() {
        return this.otherBounds;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return this.otherBounds == null ? 516 : 8196;
    }

    public boolean boundCheck(TypeBinding typeBinding) {
        int i = this.boundKind;
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            if (!typeBinding.isCompatibleWith(this.bound)) {
                return false;
            }
            TypeBinding[] typeBindingArr = this.otherBounds;
            int length = typeBindingArr == null ? 0 : typeBindingArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (!typeBinding.isCompatibleWith(this.otherBounds[i2])) {
                    return false;
                }
            }
            return true;
        }
        return typeBinding.isCompatibleWith(this.bound);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public List<TypeBinding> collectMissingTypes(List<TypeBinding> list) {
        return (this.tagBits & 128) != 0 ? this.bound.collectMissingTypes(list) : list;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void collectSubstitutes(Scope scope, TypeBinding typeBinding, InferenceContext inferenceContext, int i) {
        if ((this.tagBits & TagBits.HasTypeVariable) == 0 || typeBinding == TypeBinding.NULL || typeBinding.kind() == 65540) {
            return;
        }
        if (typeBinding.isCapture()) {
            typeBinding = ((CaptureBinding) typeBinding).wildcard;
        }
        int i2 = 0;
        if (i == 0) {
            int i3 = this.boundKind;
            if (i3 == 1) {
                int iKind = typeBinding.kind();
                if (iKind != 516) {
                    if (iKind != 8196) {
                        return;
                    }
                    WildcardBinding wildcardBinding = (WildcardBinding) typeBinding;
                    this.bound.collectSubstitutes(scope, wildcardBinding.bound, inferenceContext, 0);
                    TypeBinding[] typeBindingArr = wildcardBinding.otherBounds;
                    int length = typeBindingArr == null ? 0 : typeBindingArr.length;
                    for (int i4 = 0; i4 < length; i4++) {
                        this.bound.collectSubstitutes(scope, wildcardBinding.otherBounds[i4], inferenceContext, 0);
                    }
                    return;
                }
                WildcardBinding wildcardBinding2 = (WildcardBinding) typeBinding;
                if (wildcardBinding2.boundKind != 1) {
                    return;
                }
                this.bound.collectSubstitutes(scope, wildcardBinding2.bound, inferenceContext, 0);
                TypeBinding[] typeBindingArr2 = wildcardBinding2.otherBounds;
                int length2 = typeBindingArr2 == null ? 0 : typeBindingArr2.length;
                for (int i5 = 0; i5 < length2; i5++) {
                    this.bound.collectSubstitutes(scope, wildcardBinding2.otherBounds[i5], inferenceContext, 0);
                }
                return;
            }
            if (i3 == 2 && typeBinding.kind() == 516) {
                WildcardBinding wildcardBinding3 = (WildcardBinding) typeBinding;
                if (wildcardBinding3.boundKind != 2) {
                    return;
                }
                this.bound.collectSubstitutes(scope, wildcardBinding3.bound, inferenceContext, 0);
                TypeBinding[] typeBindingArr3 = wildcardBinding3.otherBounds;
                int length3 = typeBindingArr3 == null ? 0 : typeBindingArr3.length;
                for (int i6 = 0; i6 < length3; i6++) {
                    this.bound.collectSubstitutes(scope, wildcardBinding3.otherBounds[i6], inferenceContext, 0);
                }
                return;
            }
            return;
        }
        if (i == 1) {
            int i7 = this.boundKind;
            if (i7 == 1) {
                int iKind2 = typeBinding.kind();
                if (iKind2 == 516) {
                    WildcardBinding wildcardBinding4 = (WildcardBinding) typeBinding;
                    if (wildcardBinding4.boundKind != 1) {
                        return;
                    }
                    this.bound.collectSubstitutes(scope, wildcardBinding4.bound, inferenceContext, 1);
                    return;
                }
                if (iKind2 == 8196) {
                    WildcardBinding wildcardBinding5 = (WildcardBinding) typeBinding;
                    this.bound.collectSubstitutes(scope, wildcardBinding5.bound, inferenceContext, 1);
                    int length4 = wildcardBinding5.otherBounds.length;
                    while (i2 < length4) {
                        this.bound.collectSubstitutes(scope, wildcardBinding5.otherBounds[i2], inferenceContext, 1);
                        i2++;
                    }
                    return;
                }
                this.bound.collectSubstitutes(scope, typeBinding, inferenceContext, 1);
                return;
            }
            if (i7 != 2) {
                return;
            }
            int iKind3 = typeBinding.kind();
            if (iKind3 != 516) {
                if (iKind3 != 8196) {
                    this.bound.collectSubstitutes(scope, typeBinding, inferenceContext, 2);
                    return;
                }
                return;
            }
            WildcardBinding wildcardBinding6 = (WildcardBinding) typeBinding;
            if (wildcardBinding6.boundKind != 2) {
                return;
            }
            this.bound.collectSubstitutes(scope, wildcardBinding6.bound, inferenceContext, 2);
            TypeBinding[] typeBindingArr4 = wildcardBinding6.otherBounds;
            int length5 = typeBindingArr4 == null ? 0 : typeBindingArr4.length;
            while (i2 < length5) {
                this.bound.collectSubstitutes(scope, wildcardBinding6.otherBounds[i2], inferenceContext, 2);
                i2++;
            }
            return;
        }
        if (i != 2) {
            return;
        }
        int i8 = this.boundKind;
        if (i8 == 1) {
            int iKind4 = typeBinding.kind();
            if (iKind4 != 516) {
                if (iKind4 != 8196) {
                    return;
                }
                WildcardBinding wildcardBinding7 = (WildcardBinding) typeBinding;
                this.bound.collectSubstitutes(scope, wildcardBinding7.bound, inferenceContext, 2);
                TypeBinding[] typeBindingArr5 = wildcardBinding7.otherBounds;
                int length6 = typeBindingArr5 == null ? 0 : typeBindingArr5.length;
                while (i2 < length6) {
                    this.bound.collectSubstitutes(scope, wildcardBinding7.otherBounds[i2], inferenceContext, 2);
                    i2++;
                }
                return;
            }
            WildcardBinding wildcardBinding8 = (WildcardBinding) typeBinding;
            if (wildcardBinding8.boundKind != 1) {
                return;
            }
            this.bound.collectSubstitutes(scope, wildcardBinding8.bound, inferenceContext, 2);
            TypeBinding[] typeBindingArr6 = wildcardBinding8.otherBounds;
            int length7 = typeBindingArr6 == null ? 0 : typeBindingArr6.length;
            while (i2 < length7) {
                this.bound.collectSubstitutes(scope, wildcardBinding8.otherBounds[i2], inferenceContext, 2);
                i2++;
            }
            return;
        }
        if (i8 == 2 && typeBinding.kind() == 516) {
            WildcardBinding wildcardBinding9 = (WildcardBinding) typeBinding;
            if (wildcardBinding9.boundKind != 2) {
                return;
            }
            this.bound.collectSubstitutes(scope, wildcardBinding9.bound, inferenceContext, 2);
            TypeBinding[] typeBindingArr7 = wildcardBinding9.otherBounds;
            int length8 = typeBindingArr7 == null ? 0 : typeBindingArr7.length;
            while (i2 < length8) {
                this.bound.collectSubstitutes(scope, wildcardBinding9.otherBounds[i2], inferenceContext, 2);
                i2++;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        char[] cArrConcat;
        char[] cArrComputeUniqueKey = this.genericType.computeUniqueKey(false);
        char[] charArray = (String.valueOf('{') + String.valueOf(this.rank) + '}').toCharArray();
        int i = this.boundKind;
        if (i == 0) {
            cArrConcat = TypeConstants.WILDCARD_STAR;
        } else if (i == 1) {
            cArrConcat = CharOperation.concat(TypeConstants.WILDCARD_PLUS, this.bound.computeUniqueKey(false));
        } else {
            cArrConcat = CharOperation.concat(TypeConstants.WILDCARD_MINUS, this.bound.computeUniqueKey(false));
        }
        return CharOperation.concat(cArrComputeUniqueKey, charArray, cArrConcat);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        return erasure().constantPoolName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new WildcardBinding(this.genericType, this.rank, this.bound, this.otherBounds, this.boundKind, this.environment);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String annotatedDebugName() {
        StringBuffer stringBuffer = new StringBuffer(16);
        AnnotationBinding[] typeAnnotations = getTypeAnnotations();
        int length = typeAnnotations == null ? 0 : typeAnnotations.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append(typeAnnotations[i]);
            stringBuffer.append(StringUtil.SPACE);
        }
        int i2 = this.boundKind;
        if (i2 == 0) {
            return stringBuffer.append(TypeConstants.WILDCARD_NAME).toString();
        }
        if (i2 == 1) {
            if (this.otherBounds == null) {
                return stringBuffer.append(CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.annotatedDebugName().toCharArray())).toString();
            }
            stringBuffer.append(this.bound.annotatedDebugName());
            int length2 = this.otherBounds.length;
            for (int i3 = 0; i3 < length2; i3++) {
                stringBuffer.append(" & ").append(this.otherBounds[i3].annotatedDebugName());
            }
            return stringBuffer.toString();
        }
        return stringBuffer.append(CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.annotatedDebugName().toCharArray())).toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        return toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding erasure() {
        if (this.otherBounds == null) {
            if (this.boundKind == 1) {
                return this.bound.erasure();
            }
            TypeVariableBinding typeVariableBindingTypeVariable = typeVariable();
            if (typeVariableBindingTypeVariable != null) {
                return typeVariableBindingTypeVariable.erasure();
            }
            return this.genericType;
        }
        if (this.bound.f626id == 1) {
            return this.otherBounds[0].erasure();
        }
        return this.bound.erasure();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        if (this.genericSignature == null) {
            int i = this.boundKind;
            if (i == 0) {
                this.genericSignature = TypeConstants.WILDCARD_STAR;
            } else if (i == 1) {
                this.genericSignature = CharOperation.concat(TypeConstants.WILDCARD_PLUS, this.bound.genericTypeSignature());
            } else {
                this.genericSignature = CharOperation.concat(TypeConstants.WILDCARD_MINUS, this.bound.genericTypeSignature());
            }
        }
        return this.genericSignature;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public int hashCode() {
        return this.genericType.hashCode();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean hasTypeBit(int i) {
        if (this.typeBits == 134217728) {
            this.typeBits = 0;
            ReferenceBinding referenceBinding = this.superclass;
            if (referenceBinding != null && referenceBinding.hasTypeBit(-134217729)) {
                this.typeBits |= this.superclass.typeBits & 19;
            }
            ReferenceBinding[] referenceBindingArr = this.superInterfaces;
            if (referenceBindingArr != null) {
                int length = referenceBindingArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    if (this.superInterfaces[i2].hasTypeBit(-134217729)) {
                        this.typeBits |= this.superInterfaces[i2].typeBits & 19;
                    }
                }
            }
        }
        return (i & this.typeBits) != 0;
    }

    void initialize(ReferenceBinding referenceBinding, TypeBinding typeBinding, TypeBinding[] typeBindingArr) {
        this.genericType = referenceBinding;
        this.bound = typeBinding;
        this.otherBounds = typeBindingArr;
        if (referenceBinding != null) {
            this.fPackage = referenceBinding.getPackage();
        }
        if (typeBinding != null) {
            this.tagBits = (typeBinding.tagBits & 2305843009751615616L) | this.tagBits;
        }
        if (typeBindingArr != null) {
            for (TypeBinding typeBinding2 : typeBindingArr) {
                this.tagBits |= typeBinding2.tagBits & 2305843009214744576L;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean isSuperclassOf(ReferenceBinding referenceBinding) {
        if (this.boundKind == 2) {
            TypeBinding typeBinding = this.bound;
            if (typeBinding instanceof ReferenceBinding) {
                return ((ReferenceBinding) typeBinding).isSuperclassOf(referenceBinding);
            }
            if (referenceBinding.f626id == 1) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isIntersectionType() {
        return this.otherBounds != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] getIntersectingTypes() {
        if (isIntersectionType()) {
            TypeBinding[] typeBindingArr = this.otherBounds;
            ReferenceBinding[] referenceBindingArr = new ReferenceBinding[typeBindingArr.length + 1];
            try {
                referenceBindingArr[0] = (ReferenceBinding) this.bound;
                System.arraycopy(typeBindingArr, 0, referenceBindingArr, 1, typeBindingArr.length);
                return referenceBindingArr;
            } catch (ArrayStoreException | ClassCastException unused) {
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean isHierarchyConnected() {
        return (this.superclass == null || this.superInterfaces == null) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean enterRecursiveFunction() {
        if (this.inRecursiveFunction) {
            return false;
        }
        this.inRecursiveFunction = true;
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void exitRecursiveFunction() {
        this.inRecursiveFunction = false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProperType(boolean z) {
        if (this.inRecursiveFunction) {
            return true;
        }
        this.inRecursiveFunction = true;
        try {
            TypeBinding typeBinding = this.bound;
            if (typeBinding != null && !typeBinding.isProperType(z)) {
                return false;
            }
            ReferenceBinding referenceBinding = this.superclass;
            if (referenceBinding != null && !referenceBinding.isProperType(z)) {
                return false;
            }
            ReferenceBinding[] referenceBindingArr = this.superInterfaces;
            if (referenceBindingArr != null) {
                int length = referenceBindingArr.length;
                for (int i = 0; i < length; i++) {
                    if (!this.superInterfaces[i].isProperType(z)) {
                        return false;
                    }
                }
            }
            return true;
        } finally {
            this.inRecursiveFunction = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding substituteInferenceVariable(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        TypeBinding typeBinding2;
        boolean zNotEquals;
        TypeBinding typeBinding3 = this.bound;
        if (typeBinding3 != null) {
            TypeBinding typeBindingSubstituteInferenceVariable = typeBinding3.substituteInferenceVariable(inferenceVariable, typeBinding);
            zNotEquals = TypeBinding.notEquals(typeBindingSubstituteInferenceVariable, this.bound);
            typeBinding2 = typeBindingSubstituteInferenceVariable;
        } else {
            typeBinding2 = typeBinding3;
            zNotEquals = false;
        }
        TypeBinding[] typeBindingArr = this.otherBounds;
        TypeBinding[] typeBindingArr2 = null;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            if (zNotEquals) {
                typeBindingArr2 = new ReferenceBinding[length];
                System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
            }
            for (int i = 0; i < length; i++) {
                TypeBinding typeBinding4 = this.otherBounds[i];
                if (typeBinding4 != null) {
                    TypeBinding typeBindingSubstituteInferenceVariable2 = typeBinding4.substituteInferenceVariable(inferenceVariable, typeBinding);
                    if (TypeBinding.notEquals(typeBindingSubstituteInferenceVariable2, this.otherBounds[i])) {
                        if (typeBindingArr2 == null) {
                            ReferenceBinding[] referenceBindingArr = new ReferenceBinding[length];
                            System.arraycopy(this.otherBounds, 0, referenceBindingArr, 0, length);
                            typeBindingArr2 = referenceBindingArr;
                        }
                        typeBindingArr2[i] = typeBindingSubstituteInferenceVariable2;
                    }
                }
            }
        }
        TypeBinding[] typeBindingArr3 = typeBindingArr2;
        return zNotEquals | (typeBindingArr3 != null) ? this.environment.createWildcard(this.genericType, this.rank, typeBinding2, typeBindingArr3, this.boundKind) : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isUnboundWildcard() {
        return this.boundKind == 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    int rank() {
        return this.rank;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        int i = this.boundKind;
        if (i == 0) {
            return TypeConstants.WILDCARD_NAME;
        }
        if (i == 1) {
            if (this.otherBounds == null) {
                return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.readableName());
            }
            StringBuffer stringBuffer = new StringBuffer(10);
            stringBuffer.append(this.bound.readableName());
            int length = this.otherBounds.length;
            for (int i2 = 0; i2 < length; i2++) {
                stringBuffer.append(Typography.amp).append(this.otherBounds[i2].readableName());
            }
            int length2 = stringBuffer.length();
            char[] cArr = new char[length2];
            stringBuffer.getChars(0, length2, cArr, 0);
            return cArr;
        }
        return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.readableName());
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] nullAnnotatedReadableName(CompilerOptions compilerOptions, boolean z) {
        StringBuffer stringBuffer = new StringBuffer(10);
        appendNullAnnotation(stringBuffer, compilerOptions);
        int i = this.boundKind;
        if (i == 0) {
            stringBuffer.append(TypeConstants.WILDCARD_NAME);
        } else if (i == 1) {
            if (this.otherBounds == null) {
                stringBuffer.append(TypeConstants.WILDCARD_NAME).append(TypeConstants.WILDCARD_EXTENDS);
                stringBuffer.append(this.bound.nullAnnotatedReadableName(compilerOptions, z));
            } else {
                stringBuffer.append(this.bound.nullAnnotatedReadableName(compilerOptions, z));
                int length = this.otherBounds.length;
                for (int i2 = 0; i2 < length; i2++) {
                    stringBuffer.append(Typography.amp).append(this.otherBounds[i2].nullAnnotatedReadableName(compilerOptions, z));
                }
            }
        } else {
            stringBuffer.append(TypeConstants.WILDCARD_NAME).append(TypeConstants.WILDCARD_SUPER).append(this.bound.nullAnnotatedReadableName(compilerOptions, z));
        }
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    ReferenceBinding resolve() {
        if ((this.tagBits & TagBits.HasUnresolvedTypeVariables) == 0) {
            return this;
        }
        this.tagBits &= -16777217;
        BinaryTypeBinding.resolveType(this.genericType, this.environment, false);
        int i = this.boundKind;
        if (i == 1) {
            TypeBinding typeBindingResolveType = BinaryTypeBinding.resolveType(this.bound, this.environment, true);
            this.bound = typeBindingResolveType;
            this.tagBits |= (typeBindingResolveType.tagBits & 2048) | 2305843009213693952L;
            TypeBinding[] typeBindingArr = this.otherBounds;
            int length = typeBindingArr == null ? 0 : typeBindingArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                TypeBinding typeBindingResolveType2 = BinaryTypeBinding.resolveType(this.otherBounds[i2], this.environment, true);
                this.otherBounds[i2] = typeBindingResolveType2;
                this.tagBits |= (typeBindingResolveType2.tagBits & 2048) | 2305843009213693952L;
            }
        } else if (i == 2) {
            TypeBinding typeBindingResolveType3 = BinaryTypeBinding.resolveType(this.bound, this.environment, true);
            this.bound = typeBindingResolveType3;
            this.tagBits |= 2305843009213693952L | (2048 & typeBindingResolveType3.tagBits);
        }
        if (this.environment.usesNullTypeAnnotations()) {
            evaluateNullAnnotations(null, null);
        }
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        int i = this.boundKind;
        if (i == 0) {
            return TypeConstants.WILDCARD_NAME;
        }
        if (i == 1) {
            if (this.otherBounds == null) {
                return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.shortReadableName());
            }
            StringBuffer stringBuffer = new StringBuffer(10);
            stringBuffer.append(this.bound.shortReadableName());
            int length = this.otherBounds.length;
            for (int i2 = 0; i2 < length; i2++) {
                stringBuffer.append(Typography.amp).append(this.otherBounds[i2].shortReadableName());
            }
            int length2 = stringBuffer.length();
            char[] cArr = new char[length2];
            stringBuffer.getChars(0, length2, cArr, 0);
            return cArr;
        }
        return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.shortReadableName());
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] signature() {
        if (this.signature == null) {
            if (this.boundKind == 1) {
                return this.bound.signature();
            }
            return typeVariable().signature();
        }
        return this.signature;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        int i = this.boundKind;
        if (i == 0) {
            return TypeConstants.WILDCARD_NAME;
        }
        if (i == 1) {
            return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.sourceName());
        }
        return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.sourceName());
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding superclass() {
        TypeBinding typeBinding;
        ReferenceBinding resolvedType;
        if (this.superclass == null) {
            if (this.boundKind == 1 && !this.bound.isInterface()) {
                typeBinding = this.bound;
            } else {
                TypeVariableBinding typeVariableBindingTypeVariable = typeVariable();
                typeBinding = typeVariableBindingTypeVariable != null ? typeVariableBindingTypeVariable.firstBound : null;
            }
            if ((typeBinding instanceof ReferenceBinding) && !typeBinding.isInterface()) {
                resolvedType = (ReferenceBinding) typeBinding;
            } else {
                resolvedType = this.environment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null);
            }
            this.superclass = resolvedType;
        }
        return this.superclass;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] superInterfaces() {
        if (this.superInterfaces == null) {
            if (typeVariable() != null) {
                this.superInterfaces = this.typeVariable.superInterfaces();
            } else {
                this.superInterfaces = Binding.NO_SUPERINTERFACES;
            }
            if (this.boundKind == 1) {
                if (this.bound.isInterface()) {
                    ReferenceBinding[] referenceBindingArr = this.superInterfaces;
                    int length = referenceBindingArr.length;
                    ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length + 1];
                    this.superInterfaces = referenceBindingArr2;
                    System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 1, length);
                    this.superInterfaces[0] = (ReferenceBinding) this.bound;
                }
                TypeBinding[] typeBindingArr = this.otherBounds;
                if (typeBindingArr != null) {
                    ReferenceBinding[] referenceBindingArr3 = this.superInterfaces;
                    int length2 = referenceBindingArr3.length;
                    int length3 = typeBindingArr.length;
                    ReferenceBinding[] referenceBindingArr4 = new ReferenceBinding[length2 + length3];
                    this.superInterfaces = referenceBindingArr4;
                    System.arraycopy(referenceBindingArr3, 0, referenceBindingArr4, 0, length2);
                    for (int i = 0; i < length3; i++) {
                        this.superInterfaces[length2 + i] = (ReferenceBinding) this.otherBounds[i];
                    }
                }
            }
        }
        return this.superInterfaces;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void swapUnresolved(UnresolvedReferenceBinding unresolvedReferenceBinding, ReferenceBinding referenceBinding, LookupEnvironment lookupEnvironment) {
        boolean z;
        if (this.genericType == unresolvedReferenceBinding) {
            this.genericType = referenceBinding;
            z = true;
        } else {
            z = false;
        }
        if (this.bound == unresolvedReferenceBinding) {
            this.bound = lookupEnvironment.convertUnresolvedBinaryToRawType(referenceBinding);
            z = true;
        }
        TypeBinding[] typeBindingArr = this.otherBounds;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            for (int i = 0; i < length; i++) {
                TypeBinding[] typeBindingArr2 = this.otherBounds;
                if (typeBindingArr2[i] == unresolvedReferenceBinding) {
                    typeBindingArr2[i] = lookupEnvironment.convertUnresolvedBinaryToRawType(referenceBinding);
                    z = true;
                }
            }
        }
        if (z) {
            initialize(this.genericType, this.bound, this.otherBounds);
        }
    }

    public String toString() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        int i = this.boundKind;
        if (i == 0) {
            return new String(TypeConstants.WILDCARD_NAME);
        }
        if (i == 1) {
            if (this.otherBounds == null) {
                return new String(CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.debugName().toCharArray()));
            }
            StringBuffer stringBuffer = new StringBuffer(this.bound.debugName());
            int length = this.otherBounds.length;
            for (int i2 = 0; i2 < length; i2++) {
                stringBuffer.append(Typography.amp).append(this.otherBounds[i2].debugName());
            }
            return stringBuffer.toString();
        }
        return new String(CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.debugName().toCharArray()));
    }

    public TypeVariableBinding typeVariable() {
        if (this.typeVariable == null) {
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = this.genericType.typeVariables();
            int i = this.rank;
            if (i < typeVariableBindingArrTypeVariables.length) {
                this.typeVariable = typeVariableBindingArrTypeVariables[i];
            }
        }
        return this.typeVariable;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding unannotated() {
        return hasTypeAnnotations() ? this.environment.getUnannotatedType(this) : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding withoutToplevelNullAnnotation() {
        if (!hasNullTypeAnnotations()) {
            return this;
        }
        return this.environment.createWildcard(this.genericType, this.rank, this.bound, this.otherBounds, this.boundKind, this.environment.filterNullTypeAnnotations(getTypeAnnotations()));
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding uncapture(Scope scope) {
        int length;
        TypeBinding[] typeBindingArr;
        if ((this.tagBits & 2305843009213693952L) == 0) {
            return this;
        }
        TypeBinding typeBinding = this.bound;
        TypeBinding typeBindingUncapture = typeBinding != null ? typeBinding.uncapture(scope) : null;
        TypeBinding[] typeBindingArr2 = this.otherBounds;
        if (typeBindingArr2 == null) {
            typeBindingArr = null;
            length = 0;
        } else {
            length = typeBindingArr2.length;
            typeBindingArr = new TypeBinding[length];
        }
        for (int i = 0; i < length; i++) {
            TypeBinding typeBinding2 = this.otherBounds[i];
            typeBindingArr[i] = typeBinding2 == null ? null : typeBinding2.uncapture(scope);
        }
        return scope.environment().createWildcard(this.genericType, this.rank, typeBindingUncapture, typeBindingArr, this.boundKind, getTypeAnnotations());
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    void collectInferenceVariables(Set<InferenceVariable> set) {
        TypeBinding typeBinding = this.bound;
        if (typeBinding != null) {
            typeBinding.collectInferenceVariables(set);
        }
        TypeBinding[] typeBindingArr = this.otherBounds;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            for (int i = 0; i < length; i++) {
                this.otherBounds[i].collectInferenceVariables(set);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean mentionsAny(TypeBinding[] typeBindingArr, int i) {
        if (this.inRecursiveFunction) {
            return false;
        }
        this.inRecursiveFunction = true;
        try {
            if (super.mentionsAny(typeBindingArr, i)) {
                return true;
            }
            TypeBinding typeBinding = this.bound;
            if (typeBinding != null && typeBinding.mentionsAny(typeBindingArr, -1)) {
                return true;
            }
            TypeBinding[] typeBindingArr2 = this.otherBounds;
            if (typeBindingArr2 != null) {
                int length = typeBindingArr2.length;
                for (int i2 = 0; i2 < length; i2++) {
                    if (this.otherBounds[i2].mentionsAny(typeBindingArr, -1)) {
                        return true;
                    }
                }
            }
            return false;
        } finally {
            this.inRecursiveFunction = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public long updateTagBits() {
        if (!this.inRecursiveFunction) {
            this.inRecursiveFunction = true;
            try {
                if (this.bound != null) {
                    this.tagBits |= this.bound.updateTagBits();
                }
                TypeBinding[] typeBindingArr = this.otherBounds;
                if (typeBindingArr != null) {
                    int length = typeBindingArr.length;
                    for (int i = 0; i < length; i++) {
                        this.tagBits |= this.otherBounds[i].updateTagBits();
                    }
                }
            } finally {
                this.inRecursiveFunction = false;
            }
        }
        return super.updateTagBits();
    }
}
