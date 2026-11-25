package org.eclipse.jdt.internal.compiler.lookup;

import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class TypeVariableBinding extends ReferenceBinding {
    public Binding declaringElement;
    LookupEnvironment environment;
    public TypeBinding firstBound;
    public char[] genericTypeSignature;
    boolean inRecursiveFunction;
    public int rank;
    public ReferenceBinding[] superInterfaces;
    public ReferenceBinding superclass;

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean acceptsNonNullDefault() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean canBeInstantiated() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isTypeVariable() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return 4100;
    }

    public TypeVariableBinding(char[] cArr, Binding binding, int i, LookupEnvironment lookupEnvironment) {
        this.inRecursiveFunction = false;
        this.sourceName = cArr;
        this.declaringElement = binding;
        this.rank = i;
        this.modifiers = CompilerOptions.ShouldImplementHashcode;
        this.tagBits |= TagBits.HasTypeVariable;
        this.environment = lookupEnvironment;
        this.typeBits = 134217728;
        computeId(lookupEnvironment);
    }

    protected TypeVariableBinding(char[] cArr, LookupEnvironment lookupEnvironment) {
        this.inRecursiveFunction = false;
        this.sourceName = cArr;
        this.modifiers = CompilerOptions.ShouldImplementHashcode;
        this.tagBits |= TagBits.HasTypeVariable;
        this.environment = lookupEnvironment;
        this.typeBits = 134217728;
    }

    public TypeVariableBinding(TypeVariableBinding typeVariableBinding) {
        super(typeVariableBinding);
        this.inRecursiveFunction = false;
        this.declaringElement = typeVariableBinding.declaringElement;
        this.rank = typeVariableBinding.rank;
        this.firstBound = typeVariableBinding.firstBound;
        this.superclass = typeVariableBinding.superclass;
        ReferenceBinding[] referenceBindingArr = typeVariableBinding.superInterfaces;
        if (referenceBindingArr != null) {
            int length = referenceBindingArr.length;
            if (length > 0) {
                ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length];
                this.superInterfaces = referenceBindingArr2;
                System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, length);
            } else {
                this.superInterfaces = Binding.NO_SUPERINTERFACES;
            }
        }
        this.genericTypeSignature = typeVariableBinding.genericTypeSignature;
        this.environment = typeVariableBinding.environment;
        typeVariableBinding.tagBits |= 8388608;
        this.tagBits &= -8388609;
    }

    public TypeConstants.BoundCheckStatus boundCheck(Substitution substitution, TypeBinding typeBinding, Scope scope, ASTNode aSTNode) {
        TypeConstants.BoundCheckStatus boundCheckStatusInternalBoundCheck = internalBoundCheck(substitution, typeBinding, scope, aSTNode);
        if (boundCheckStatusInternalBoundCheck == TypeConstants.BoundCheckStatus.MISMATCH && (typeBinding instanceof TypeVariableBinding) && scope != null) {
            TypeBinding typeBinding2 = ((TypeVariableBinding) typeBinding).firstBound;
            if (typeBinding2 instanceof ParameterizedTypeBinding) {
                return boundCheckStatusInternalBoundCheck.betterOf(boundCheck(substitution, typeBinding2.capture(scope, -1, -1), scope, aSTNode));
            }
        }
        return boundCheckStatusInternalBoundCheck;
    }

    /* JADX WARN: Code duplicated, block: B:141:0x01c6  */
    /* JADX WARN: Code duplicated, block: B:180:0x0240  */
    /* JADX WARN: Code duplicated, block: B:81:0x0110  */
    private TypeConstants.BoundCheckStatus internalBoundCheck(Substitution substitution, TypeBinding typeBinding, Scope scope, ASTNode aSTNode) {
        boolean z;
        boolean z2;
        if (typeBinding == TypeBinding.NULL || TypeBinding.equalsEquals(typeBinding, this)) {
            return TypeConstants.BoundCheckStatus.OK;
        }
        boolean z3 = true;
        boolean z4 = substitution != null;
        if (!(typeBinding instanceof ReferenceBinding) && !typeBinding.isArrayType()) {
            return TypeConstants.BoundCheckStatus.MISMATCH;
        }
        if (this.superclass == null) {
            return TypeConstants.BoundCheckStatus.OK;
        }
        TypeConstants.BoundCheckStatus boundCheckStatusNullBoundCheck = TypeConstants.BoundCheckStatus.OK;
        boolean zUsesNullTypeAnnotations = scope.environment().usesNullTypeAnnotations();
        if (typeBinding.kind() == 516) {
            WildcardBinding wildcardBinding = (WildcardBinding) typeBinding;
            int i = wildcardBinding.boundKind;
            if (i != 0) {
                if (i == 1) {
                    TypeBinding typeBinding2 = wildcardBinding.bound;
                    boolean zEqualsEquals = TypeBinding.equalsEquals(typeBinding2, this);
                    boolean zIsArrayType = typeBinding2.isArrayType();
                    if (!typeBinding2.isInterface()) {
                        TypeBinding typeBindingSubstitute = this.superclass;
                        if (z4) {
                            typeBindingSubstitute = Scope.substitute(substitution, typeBindingSubstitute);
                        }
                        TypeBinding typeBinding3 = typeBindingSubstitute;
                        if (!zEqualsEquals && typeBinding3.f626id != 1) {
                            if (zIsArrayType) {
                                if (!typeBinding2.isCompatibleWith(typeBinding3, scope)) {
                                    return TypeConstants.BoundCheckStatus.MISMATCH;
                                }
                            } else {
                                TypeBinding typeBindingFindSuperTypeOriginatingFrom = typeBinding2.findSuperTypeOriginatingFrom(typeBinding3);
                                if (typeBindingFindSuperTypeOriginatingFrom != null) {
                                    if (typeBinding3.isProvablyDistinct(typeBindingFindSuperTypeOriginatingFrom)) {
                                        return TypeConstants.BoundCheckStatus.MISMATCH;
                                    }
                                } else {
                                    TypeBinding typeBindingFindSuperTypeOriginatingFrom2 = typeBinding3.findSuperTypeOriginatingFrom(typeBinding2);
                                    if (typeBindingFindSuperTypeOriginatingFrom2 != null) {
                                        if (typeBindingFindSuperTypeOriginatingFrom2.isProvablyDistinct(typeBinding2)) {
                                            return TypeConstants.BoundCheckStatus.MISMATCH;
                                        }
                                    } else if (denotesRelevantSuperClass(typeBinding2) && denotesRelevantSuperClass(typeBinding3)) {
                                        return TypeConstants.BoundCheckStatus.MISMATCH;
                                    }
                                }
                            }
                        }
                        if (zUsesNullTypeAnnotations && typeBinding.hasNullTypeAnnotations()) {
                            boundCheckStatusNullBoundCheck = nullBoundCheck(scope, typeBinding, typeBinding3, substitution, aSTNode, boundCheckStatusNullBoundCheck);
                        }
                    }
                    if (!zIsArrayType && !((ReferenceBinding) typeBinding2).isFinal()) {
                        z3 = false;
                    }
                    int i2 = 0;
                    for (int length = this.superInterfaces.length; i2 < length; length = length) {
                        TypeBinding[] typeBindingArr = this.superInterfaces;
                        TypeBinding typeBindingSubstitute2 = z4 ? Scope.substitute(substitution, typeBindingArr[i2]) : typeBindingArr[i2];
                        if (!zEqualsEquals) {
                            if (zIsArrayType) {
                                if (!typeBinding2.isCompatibleWith(typeBindingSubstitute2, scope)) {
                                    return TypeConstants.BoundCheckStatus.MISMATCH;
                                }
                            } else {
                                TypeBinding typeBindingFindSuperTypeOriginatingFrom3 = typeBinding2.findSuperTypeOriginatingFrom(typeBindingSubstitute2);
                                if (typeBindingFindSuperTypeOriginatingFrom3 != null) {
                                    if (typeBindingSubstitute2.isProvablyDistinct(typeBindingFindSuperTypeOriginatingFrom3)) {
                                        return TypeConstants.BoundCheckStatus.MISMATCH;
                                    }
                                } else if (z3) {
                                    return TypeConstants.BoundCheckStatus.MISMATCH;
                                }
                            }
                        }
                        if (zUsesNullTypeAnnotations && typeBinding.hasNullTypeAnnotations()) {
                            boundCheckStatusNullBoundCheck = nullBoundCheck(scope, typeBinding, typeBindingSubstitute2, substitution, aSTNode, boundCheckStatusNullBoundCheck);
                        }
                        i2++;
                    }
                    if (boundCheckStatusNullBoundCheck != null) {
                        return boundCheckStatusNullBoundCheck;
                    }
                } else if (i == 2) {
                    if (wildcardBinding.bound.isTypeVariable() && ((TypeVariableBinding) wildcardBinding.bound).superclass.f626id == 1) {
                        return nullBoundCheck(scope, typeBinding, null, substitution, aSTNode, boundCheckStatusNullBoundCheck);
                    }
                    TypeBinding typeBindingCreateAnnotatedType = wildcardBinding.bound;
                    if (zUsesNullTypeAnnotations && this.environment.containsNullTypeAnnotation(wildcardBinding.typeAnnotations)) {
                        typeBindingCreateAnnotatedType = this.environment.createAnnotatedType(typeBindingCreateAnnotatedType.withoutToplevelNullAnnotation(), wildcardBinding.getTypeAnnotations());
                    }
                    TypeConstants.BoundCheckStatus boundCheckStatusBoundCheck = boundCheck(substitution, typeBindingCreateAnnotatedType, scope, null);
                    if (boundCheckStatusBoundCheck == TypeConstants.BoundCheckStatus.NULL_PROBLEM && aSTNode != null) {
                        scope.problemReporter().nullityMismatchTypeArgument(this, wildcardBinding, aSTNode);
                    }
                    return boundCheckStatusBoundCheck;
                }
            } else if (zUsesNullTypeAnnotations && typeBinding.hasNullTypeAnnotations()) {
                return nullBoundCheck(scope, typeBinding, null, substitution, aSTNode, boundCheckStatusNullBoundCheck);
            }
            return TypeConstants.BoundCheckStatus.OK;
        }
        if (this.superclass.f626id != 1) {
            TypeBinding typeBindingSubstitute3 = this.superclass;
            if (z4) {
                typeBindingSubstitute3 = Scope.substitute(substitution, typeBindingSubstitute3);
            }
            TypeBinding typeBinding4 = typeBindingSubstitute3;
            if (!TypeBinding.notEquals(typeBinding4, typeBinding)) {
                z = false;
            } else {
                if (!typeBinding.isCompatibleWith(typeBinding4, scope)) {
                    return TypeConstants.BoundCheckStatus.MISMATCH;
                }
                TypeBinding typeBindingFindSuperTypeOriginatingFrom4 = typeBinding.findSuperTypeOriginatingFrom(typeBinding4);
                if (typeBindingFindSuperTypeOriginatingFrom4 != null && typeBindingFindSuperTypeOriginatingFrom4.isRawType() && typeBinding4.isBoundParameterizedType()) {
                    z = true;
                } else {
                    z = false;
                }
            }
            if (zUsesNullTypeAnnotations) {
                boundCheckStatusNullBoundCheck = nullBoundCheck(scope, typeBinding, typeBinding4, substitution, aSTNode, boundCheckStatusNullBoundCheck);
            }
        } else {
            z = false;
        }
        boolean z5 = z;
        int i3 = 0;
        for (int length2 = this.superInterfaces.length; i3 < length2; length2 = length2) {
            TypeBinding[] typeBindingArr2 = this.superInterfaces;
            TypeBinding typeBindingSubstitute4 = z4 ? Scope.substitute(substitution, typeBindingArr2[i3]) : typeBindingArr2[i3];
            if (!TypeBinding.notEquals(typeBindingSubstitute4, typeBinding)) {
                z2 = z5;
            } else {
                if (!typeBinding.isCompatibleWith(typeBindingSubstitute4, scope)) {
                    return TypeConstants.BoundCheckStatus.MISMATCH;
                }
                TypeBinding typeBindingFindSuperTypeOriginatingFrom5 = typeBinding.findSuperTypeOriginatingFrom(typeBindingSubstitute4);
                if (typeBindingFindSuperTypeOriginatingFrom5 != null && typeBindingFindSuperTypeOriginatingFrom5.isRawType() && typeBindingSubstitute4.isBoundParameterizedType()) {
                    z2 = true;
                } else {
                    z2 = z5;
                }
            }
            if (zUsesNullTypeAnnotations) {
                boundCheckStatusNullBoundCheck = nullBoundCheck(scope, typeBinding, typeBindingSubstitute4, substitution, aSTNode, boundCheckStatusNullBoundCheck);
            }
            i3++;
            z5 = z2;
        }
        if (zUsesNullTypeAnnotations && boundCheckStatusNullBoundCheck != TypeConstants.BoundCheckStatus.NULL_PROBLEM) {
            long j = this.tagBits & TagBits.AnnotationNullMASK;
            if (j != 0 && j != (TagBits.AnnotationNullMASK & typeBinding.tagBits)) {
                if (aSTNode != null) {
                    scope.problemReporter().nullityMismatchTypeArgument(this, typeBinding, aSTNode);
                }
                boundCheckStatusNullBoundCheck = TypeConstants.BoundCheckStatus.NULL_PROBLEM;
            }
        }
        if (z5) {
            return TypeConstants.BoundCheckStatus.UNCHECKED;
        }
        return boundCheckStatusNullBoundCheck != null ? boundCheckStatusNullBoundCheck : TypeConstants.BoundCheckStatus.OK;
    }

    private TypeConstants.BoundCheckStatus nullBoundCheck(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2, Substitution substitution, ASTNode aSTNode, TypeConstants.BoundCheckStatus boundCheckStatus) {
        if (!NullAnnotationMatching.analyse(this, typeBinding, typeBinding2, substitution, -1, null, NullAnnotationMatching.CheckMode.BOUND_CHECK).isAnyMismatch()) {
            return boundCheckStatus;
        }
        if (aSTNode != null) {
            scope.problemReporter().nullityMismatchTypeArgument(this, typeBinding, aSTNode);
        }
        return TypeConstants.BoundCheckStatus.NULL_PROBLEM;
    }

    boolean denotesRelevantSuperClass(TypeBinding typeBinding) {
        if (!typeBinding.isTypeVariable() && !typeBinding.isInterface() && typeBinding.f626id != 1) {
            return true;
        }
        ReferenceBinding referenceBindingSuperclass = typeBinding.superclass();
        return (referenceBindingSuperclass == null || referenceBindingSuperclass.f626id == 1 || referenceBindingSuperclass.isTypeVariable()) ? false : true;
    }

    public int boundsCount() {
        TypeBinding typeBinding = this.firstBound;
        if (typeBinding == null) {
            return 0;
        }
        if (typeBinding.isInterface()) {
            return this.superInterfaces.length;
        }
        return this.superInterfaces.length + 1;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void collectSubstitutes(Scope scope, TypeBinding typeBinding, InferenceContext inferenceContext, int i) {
        TypeBinding typeBindingComputeBoxingType;
        int i2;
        if (this.declaringElement != inferenceContext.genericMethod) {
            return;
        }
        int iKind = typeBinding.kind();
        if (iKind != 132) {
            if (iKind == 516 || iKind == 65540) {
                return;
            }
        } else if (typeBinding == TypeBinding.NULL || (typeBindingComputeBoxingType = scope.environment().computeBoxingType(typeBinding)) == typeBinding) {
            return;
        } else {
            typeBinding = typeBindingComputeBoxingType;
        }
        if (i != 0) {
            i2 = 1;
            if (i == 1) {
                i2 = 2;
            }
        } else {
            i2 = 0;
        }
        inferenceContext.recordSubstitute(this, typeBinding, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        Binding binding = this.declaringElement;
        if (!z && binding.kind() == 8) {
            MethodBinding methodBinding = (MethodBinding) binding;
            ReferenceBinding referenceBinding = methodBinding.declaringClass;
            stringBuffer.append(referenceBinding.computeUniqueKey(false));
            stringBuffer.append(':');
            MethodBinding[] methodBindingArrMethods = referenceBinding.methods();
            if (methodBindingArrMethods != null) {
                int length = methodBindingArrMethods.length;
                for (int i = 0; i < length; i++) {
                    if (methodBindingArrMethods[i] == methodBinding) {
                        stringBuffer.append(i);
                        break;
                    }
                }
            }
        } else {
            stringBuffer.append(binding.computeUniqueKey(false));
            stringBuffer.append(':');
        }
        stringBuffer.append(genericTypeSignature());
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        TypeBinding typeBinding = this.firstBound;
        if (typeBinding != null) {
            return typeBinding.constantPoolName();
        }
        return this.superclass.constantPoolName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new TypeVariableBinding(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String annotatedDebugName() {
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append(super.annotatedDebugName());
        if (!this.inRecursiveFunction) {
            this.inRecursiveFunction = true;
            try {
                ReferenceBinding referenceBinding = this.superclass;
                if (referenceBinding != null && TypeBinding.equalsEquals(this.firstBound, referenceBinding)) {
                    stringBuffer.append(" extends ").append(this.superclass.annotatedDebugName());
                }
                ReferenceBinding[] referenceBindingArr = this.superInterfaces;
                if (referenceBindingArr != null && referenceBindingArr != Binding.NO_SUPERINTERFACES) {
                    if (TypeBinding.notEquals(this.firstBound, this.superclass)) {
                        stringBuffer.append(" extends ");
                    }
                    int length = this.superInterfaces.length;
                    for (int i = 0; i < length; i++) {
                        if (i > 0 || TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
                            stringBuffer.append(" & ");
                        }
                        stringBuffer.append(this.superInterfaces[i].annotatedDebugName());
                    }
                }
            } finally {
                this.inRecursiveFunction = false;
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        if (hasTypeAnnotations()) {
            return super.annotatedDebugName();
        }
        return new String(this.sourceName);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding erasure() {
        TypeBinding typeBinding = this.firstBound;
        if (typeBinding != null) {
            return typeBinding.erasure();
        }
        return this.superclass;
    }

    public char[] genericSignature() {
        ReferenceBinding referenceBinding;
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append(this.sourceName).append(':');
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        int length = referenceBindingArr == null ? 0 : referenceBindingArr.length;
        if ((length == 0 || TypeBinding.equalsEquals(this.firstBound, this.superclass)) && (referenceBinding = this.superclass) != null) {
            stringBuffer.append(referenceBinding.genericTypeSignature());
        }
        for (int i = 0; i < length; i++) {
            stringBuffer.append(':').append(this.superInterfaces[i].genericTypeSignature());
        }
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        char[] cArr = this.genericTypeSignature;
        if (cArr != null) {
            return cArr;
        }
        char[] cArrConcat = CharOperation.concat(Util.C_TYPE_VARIABLE, this.sourceName, ';');
        this.genericTypeSignature = cArrConcat;
        return cArrConcat;
    }

    TypeBound[] getTypeBounds(InferenceVariable inferenceVariable, InferenceSubstitution inferenceSubstitution) {
        int i;
        int iBoundsCount = boundsCount();
        if (iBoundsCount == 0) {
            return NO_TYPE_BOUNDS;
        }
        TypeBound[] typeBoundArr = new TypeBound[iBoundsCount];
        int i2 = 0;
        if (this.firstBound.isInterface()) {
            i = 0;
        } else {
            typeBoundArr[0] = TypeBound.createBoundOrDependency(inferenceSubstitution, this.firstBound, inferenceVariable);
            i = 1;
        }
        while (true) {
            ReferenceBinding[] referenceBindingArr = this.superInterfaces;
            if (i2 >= referenceBindingArr.length) {
                return typeBoundArr;
            }
            typeBoundArr[i] = TypeBound.createBoundOrDependency(inferenceSubstitution, referenceBindingArr[i2], inferenceVariable);
            i2++;
            i++;
        }
    }

    boolean hasOnlyRawBounds() {
        ReferenceBinding referenceBinding = this.superclass;
        if (referenceBinding != null && TypeBinding.equalsEquals(this.firstBound, referenceBinding) && !this.superclass.isRawType()) {
            return false;
        }
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        if (referenceBindingArr == null) {
            return true;
        }
        int length = referenceBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (!this.superInterfaces[i].isRawType()) {
                return false;
            }
        }
        return true;
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

    public boolean isErasureBoundTo(TypeBinding typeBinding) {
        if (TypeBinding.equalsEquals(this.superclass.erasure(), typeBinding)) {
            return true;
        }
        int length = this.superInterfaces.length;
        for (int i = 0; i < length; i++) {
            if (TypeBinding.equalsEquals(this.superInterfaces[i].erasure(), typeBinding)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean isHierarchyConnected() {
        return (this.modifiers & 33554432) == 0;
    }

    public boolean isInterchangeableWith(TypeVariableBinding typeVariableBinding, Substitution substitution) {
        if (TypeBinding.equalsEquals(this, typeVariableBinding)) {
            return true;
        }
        int length = this.superInterfaces.length;
        if (length != typeVariableBinding.superInterfaces.length || TypeBinding.notEquals(this.superclass, Scope.substitute(substitution, typeVariableBinding.superclass))) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            TypeBinding typeBindingSubstitute = Scope.substitute(substitution, typeVariableBinding.superInterfaces[i]);
            for (int i2 = 0; i2 < length; i2++) {
                if (TypeBinding.equalsEquals(typeBindingSubstitute, this.superInterfaces[i2])) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isSubtypeOf(TypeBinding typeBinding) {
        if (isSubTypeOfRTL(typeBinding)) {
            return true;
        }
        TypeBinding typeBinding2 = this.firstBound;
        if (typeBinding2 != null && typeBinding2.isSubtypeOf(typeBinding)) {
            return true;
        }
        ReferenceBinding referenceBinding = this.superclass;
        if (referenceBinding != null && referenceBinding.isSubtypeOf(typeBinding)) {
            return true;
        }
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        if (referenceBindingArr != null) {
            int length = referenceBindingArr.length;
            for (int i = 0; i < length; i++) {
                if (this.superInterfaces[i].isSubtypeOf(typeBinding)) {
                    return true;
                }
            }
        }
        return typeBinding.f626id == 1;
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
        boolean zNotEquals;
        if (this.inRecursiveFunction) {
            return this;
        }
        this.inRecursiveFunction = true;
        try {
            ReferenceBinding referenceBinding = this.superclass;
            if (referenceBinding != null) {
                referenceBinding = (ReferenceBinding) referenceBinding.substituteInferenceVariable(inferenceVariable, typeBinding);
                zNotEquals = TypeBinding.notEquals(referenceBinding, this.superclass);
            } else {
                zNotEquals = false;
            }
            ReferenceBinding[] referenceBindingArr = this.superInterfaces;
            ReferenceBinding[] referenceBindingArr2 = null;
            if (referenceBindingArr != null) {
                int length = referenceBindingArr.length;
                if (zNotEquals) {
                    referenceBindingArr2 = new ReferenceBinding[length];
                    System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, length);
                }
                for (int i = 0; i < length; i++) {
                    ReferenceBinding referenceBinding2 = this.superInterfaces[i];
                    if (referenceBinding2 != null) {
                        ReferenceBinding referenceBinding3 = (ReferenceBinding) referenceBinding2.substituteInferenceVariable(inferenceVariable, typeBinding);
                        if (TypeBinding.notEquals(referenceBinding3, this.superInterfaces[i])) {
                            if (referenceBindingArr2 == null) {
                                referenceBindingArr2 = new ReferenceBinding[length];
                                System.arraycopy(this.superInterfaces, 0, referenceBindingArr2, 0, length);
                            }
                            referenceBindingArr2[i] = referenceBinding3;
                            zNotEquals = true;
                        }
                    }
                }
            }
            if (!zNotEquals) {
                return this;
            }
            TypeVariableBinding typeVariableBinding = new TypeVariableBinding(this.sourceName, this.declaringElement, this.rank, this.environment);
            typeVariableBinding.superclass = referenceBinding;
            typeVariableBinding.superInterfaces = referenceBindingArr2;
            typeVariableBinding.tagBits = this.tagBits;
            return typeVariableBinding;
        } finally {
            this.inRecursiveFunction = false;
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
            ReferenceBinding referenceBinding = this.superclass;
            if (referenceBinding != null && referenceBinding.mentionsAny(typeBindingArr, i)) {
                return true;
            }
            if (this.superInterfaces != null) {
                int i2 = 0;
                while (true) {
                    ReferenceBinding[] referenceBindingArr = this.superInterfaces;
                    if (i2 >= referenceBindingArr.length) {
                        break;
                    }
                    if (referenceBindingArr[i2].mentionsAny(typeBindingArr, i)) {
                        return true;
                    }
                    i2++;
                }
            }
            return false;
        } finally {
            this.inRecursiveFunction = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    void collectInferenceVariables(Set<InferenceVariable> set) {
        if (this.inRecursiveFunction) {
            return;
        }
        this.inRecursiveFunction = true;
        try {
            ReferenceBinding referenceBinding = this.superclass;
            if (referenceBinding != null) {
                referenceBinding.collectInferenceVariables(set);
            }
            if (this.superInterfaces != null) {
                int i = 0;
                while (true) {
                    ReferenceBinding[] referenceBindingArr = this.superInterfaces;
                    if (i >= referenceBindingArr.length) {
                        break;
                    }
                    referenceBindingArr[i].collectInferenceVariables(set);
                    i++;
                }
            }
        } finally {
            this.inRecursiveFunction = false;
        }
    }

    public TypeBinding[] otherUpperBounds() {
        TypeBinding typeBinding = this.firstBound;
        if (typeBinding == null) {
            return Binding.NO_TYPES;
        }
        if (TypeBinding.equalsEquals(typeBinding, this.superclass)) {
            return this.superInterfaces;
        }
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        int length = referenceBindingArr.length - 1;
        if (length > 0) {
            TypeBinding[] typeBindingArr = new TypeBinding[length];
            System.arraycopy(referenceBindingArr, 1, typeBindingArr, 0, length);
            return typeBindingArr;
        }
        return Binding.NO_TYPES;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        return this.sourceName;
    }

    ReferenceBinding resolve() {
        long j;
        TypeBinding typeBinding;
        if ((this.modifiers & 33554432) == 0) {
            return this;
        }
        long j2 = this.tagBits & TagBits.AnnotationNullMASK;
        ReferenceBinding referenceBinding = this.superclass;
        boolean z = true;
        long j3 = 0;
        if (referenceBinding != null) {
            ReferenceBinding referenceBinding2 = (ReferenceBinding) BinaryTypeBinding.resolveType(referenceBinding, this.environment, true);
            this.tagBits = (referenceBinding2.tagBits & 2048) | this.tagBits;
            long j4 = referenceBinding2.tagBits;
            long j5 = j4 & TagBits.AnnotationNullMASK;
            if (j5 != 0 && j2 == 0 && (j4 & 72057594037927936L) != 0) {
                j2 = j5;
            }
            setSuperClass(referenceBinding2);
        }
        TypeBinding[] typeBindingArr = this.superInterfaces;
        int length = typeBindingArr.length;
        if (length != 0) {
            typeBinding = typeBindingArr[0];
            while (true) {
                length--;
                if (length < 0) {
                    break;
                }
                ReferenceBinding referenceBinding3 = (ReferenceBinding) BinaryTypeBinding.resolveType(typeBindingArr[length], this.environment, z);
                this.tagBits = (referenceBinding3.tagBits & 2048) | this.tagBits;
                long j6 = referenceBinding3.tagBits;
                long j7 = j6 & TagBits.AnnotationNullMASK;
                if (j7 != 0 && j2 == 0) {
                    if ((j6 & 72057594037927936L) != 0) {
                        j2 = j7;
                    }
                }
                typeBindingArr[length] = referenceBinding3;
                j3 = 0;
                z = true;
            }
            j = j3;
        } else {
            j = 0;
            typeBinding = null;
        }
        if (j2 != j) {
            this.tagBits = j2 | 1048576 | this.tagBits;
        }
        TypeBinding typeBinding2 = this.firstBound;
        if (typeBinding2 != null) {
            if (TypeBinding.equalsEquals(typeBinding2, referenceBinding)) {
                setFirstBound(this.superclass);
            } else if (TypeBinding.equalsEquals(this.firstBound, typeBinding)) {
                setFirstBound(typeBindingArr[0]);
            }
        }
        this.modifiers &= -33554433;
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr, boolean z) {
        if (getClass() == TypeVariableBinding.class) {
            this.environment.typeSystem.forceRegisterAsDerived(this);
        } else {
            this.environment.getUnannotatedType(this);
        }
        super.setTypeAnnotations(annotationBindingArr, z);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        return readableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding superclass() {
        return this.superclass;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] superInterfaces() {
        return this.superInterfaces;
    }

    public String toString() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append('<').append(this.sourceName);
        ReferenceBinding referenceBinding = this.superclass;
        if (referenceBinding != null && TypeBinding.equalsEquals(this.firstBound, referenceBinding)) {
            stringBuffer.append(" extends ").append(this.superclass.debugName());
        }
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        if (referenceBindingArr != null && referenceBindingArr != Binding.NO_SUPERINTERFACES) {
            if (TypeBinding.notEquals(this.firstBound, this.superclass)) {
                stringBuffer.append(" extends ");
            }
            int length = this.superInterfaces.length;
            for (int i = 0; i < length; i++) {
                if (i > 0 || TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
                    stringBuffer.append(" & ");
                }
                stringBuffer.append(this.superInterfaces[i].debugName());
            }
        }
        stringBuffer.append('>');
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] nullAnnotatedReadableName(CompilerOptions compilerOptions, boolean z) {
        StringBuffer stringBuffer = new StringBuffer(10);
        appendNullAnnotation(stringBuffer, compilerOptions);
        stringBuffer.append(sourceName());
        if (!this.inRecursiveFunction) {
            this.inRecursiveFunction = true;
            try {
                ReferenceBinding referenceBinding = this.superclass;
                if (referenceBinding != null && TypeBinding.equalsEquals(this.firstBound, referenceBinding)) {
                    stringBuffer.append(" extends ").append(this.superclass.nullAnnotatedReadableName(compilerOptions, z));
                }
                ReferenceBinding[] referenceBindingArr = this.superInterfaces;
                if (referenceBindingArr != null && referenceBindingArr != Binding.NO_SUPERINTERFACES) {
                    if (TypeBinding.notEquals(this.firstBound, this.superclass)) {
                        stringBuffer.append(" extends ");
                    }
                    int length = this.superInterfaces.length;
                    for (int i = 0; i < length; i++) {
                        if (i > 0 || TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
                            stringBuffer.append(" & ");
                        }
                        stringBuffer.append(this.superInterfaces[i].nullAnnotatedReadableName(compilerOptions, z));
                    }
                }
                this.inRecursiveFunction = false;
            } catch (Throwable th) {
                this.inRecursiveFunction = false;
                throw th;
            }
        }
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    protected void appendNullAnnotation(StringBuffer stringBuffer, CompilerOptions compilerOptions) {
        TypeVariableBinding[] typeVariableBindingArrTypeVariables;
        TypeVariableBinding typeVariableBinding;
        int length = stringBuffer.length();
        super.appendNullAnnotation(stringBuffer, compilerOptions);
        if (length == stringBuffer.length() && hasNullTypeAnnotations()) {
            Binding binding = this.declaringElement;
            if (binding instanceof ReferenceBinding) {
                typeVariableBindingArrTypeVariables = ((ReferenceBinding) binding).typeVariables();
            } else {
                typeVariableBindingArrTypeVariables = binding instanceof MethodBinding ? ((MethodBinding) binding).typeVariables() : null;
            }
            if (typeVariableBindingArrTypeVariables != null) {
                int length2 = typeVariableBindingArrTypeVariables.length;
                int i = this.rank;
                if (length2 <= i || (typeVariableBinding = typeVariableBindingArrTypeVariables[i]) == this) {
                    return;
                }
                typeVariableBinding.appendNullAnnotation(stringBuffer, compilerOptions);
            }
        }
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
        TypeBinding unannotatedType = this.environment.getUnannotatedType(this);
        AnnotationBinding[] annotationBindingArrFilterNullTypeAnnotations = this.environment.filterNullTypeAnnotations(this.typeAnnotations);
        return annotationBindingArrFilterNullTypeAnnotations.length > 0 ? this.environment.createAnnotatedType(unannotatedType, annotationBindingArrFilterNullTypeAnnotations) : unannotatedType;
    }

    public TypeBinding upperBound() {
        TypeBinding typeBinding = this.firstBound;
        return typeBinding != null ? typeBinding : this.superclass;
    }

    public void evaluateNullAnnotations(Scope scope, TypeParameter typeParameter) {
        int length;
        long jValidNullTagBits = NullAnnotationMatching.validNullTagBits(this.tagBits);
        TypeBinding typeBinding = this.firstBound;
        if (typeBinding != null && typeBinding.isValidBinding()) {
            long jValidNullTagBits2 = NullAnnotationMatching.validNullTagBits(this.firstBound.tagBits);
            if (jValidNullTagBits2 != 0) {
                if (jValidNullTagBits == 0) {
                    if ((jValidNullTagBits2 & 72057594037927936L) != 0) {
                        jValidNullTagBits = jValidNullTagBits2;
                    }
                } else if (jValidNullTagBits2 != jValidNullTagBits && typeParameter != null) {
                    this.firstBound = nullMismatchOnBound(typeParameter, this.firstBound, jValidNullTagBits2, jValidNullTagBits, scope);
                }
            }
        }
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        if (referenceBindingArr != null && (length = referenceBindingArr.length) != 0) {
            while (true) {
                int i = length - 1;
                if (i < 0) {
                    break;
                }
                ReferenceBinding referenceBinding = referenceBindingArr[i];
                long jValidNullTagBits3 = NullAnnotationMatching.validNullTagBits(referenceBinding.tagBits);
                if (jValidNullTagBits3 != 0) {
                    if (jValidNullTagBits == 0) {
                        if ((jValidNullTagBits3 & 72057594037927936L) != 0) {
                            jValidNullTagBits = jValidNullTagBits3;
                        }
                    } else if (jValidNullTagBits3 != jValidNullTagBits && typeParameter != null) {
                        referenceBindingArr[i] = (ReferenceBinding) nullMismatchOnBound(typeParameter, referenceBinding, jValidNullTagBits3, jValidNullTagBits, scope);
                    }
                }
                length = i;
            }
        }
        if (jValidNullTagBits != 0) {
            this.tagBits |= 1048576 | jValidNullTagBits;
        }
    }

    private TypeBinding nullMismatchOnBound(TypeParameter typeParameter, TypeBinding typeBinding, long j, long j2, Scope scope) {
        Annotation annotationFindAnnotation = findBound(typeBinding, typeParameter).findAnnotation(j);
        if (annotationFindAnnotation != null) {
            scope.problemReporter().contradictoryNullAnnotationsOnBounds(annotationFindAnnotation, j2);
            this.tagBits &= -108086391056891905L;
            return typeBinding;
        }
        return typeBinding.withoutToplevelNullAnnotation();
    }

    private TypeReference findBound(TypeBinding typeBinding, TypeParameter typeParameter) {
        if (typeParameter.type != null && TypeBinding.equalsEquals(typeParameter.type.resolvedType, typeBinding)) {
            return typeParameter.type;
        }
        TypeReference[] typeReferenceArr = typeParameter.bounds;
        if (typeReferenceArr == null) {
            return null;
        }
        for (int i = 0; i < typeReferenceArr.length; i++) {
            if (TypeBinding.equalsEquals(typeReferenceArr[i].resolvedType, typeBinding)) {
                return typeReferenceArr[i];
            }
        }
        return null;
    }

    public TypeBinding setFirstBound(TypeBinding typeBinding) {
        this.firstBound = typeBinding;
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] derivedTypesForDeferredInitialization = getDerivedTypesForDeferredInitialization();
            int length = derivedTypesForDeferredInitialization == null ? 0 : derivedTypesForDeferredInitialization.length;
            for (int i = 0; i < length; i++) {
                TypeVariableBinding typeVariableBinding = (TypeVariableBinding) derivedTypesForDeferredInitialization[i];
                if (typeVariableBinding.firstBound == null) {
                    typeVariableBinding.firstBound = typeBinding;
                }
            }
        }
        if (typeBinding != null && typeBinding.hasNullTypeAnnotations()) {
            this.tagBits |= 1048576;
        }
        return typeBinding;
    }

    public ReferenceBinding setSuperClass(ReferenceBinding referenceBinding) {
        this.superclass = referenceBinding;
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] derivedTypesForDeferredInitialization = getDerivedTypesForDeferredInitialization();
            int length = derivedTypesForDeferredInitialization == null ? 0 : derivedTypesForDeferredInitialization.length;
            for (int i = 0; i < length; i++) {
                TypeVariableBinding typeVariableBinding = (TypeVariableBinding) derivedTypesForDeferredInitialization[i];
                if (typeVariableBinding.superclass == null) {
                    typeVariableBinding.superclass = referenceBinding;
                }
            }
        }
        return referenceBinding;
    }

    public ReferenceBinding[] setSuperInterfaces(ReferenceBinding[] referenceBindingArr) {
        this.superInterfaces = referenceBindingArr;
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] derivedTypesForDeferredInitialization = getDerivedTypesForDeferredInitialization();
            int length = derivedTypesForDeferredInitialization == null ? 0 : derivedTypesForDeferredInitialization.length;
            for (int i = 0; i < length; i++) {
                TypeVariableBinding typeVariableBinding = (TypeVariableBinding) derivedTypesForDeferredInitialization[i];
                if (typeVariableBinding.superInterfaces == null) {
                    typeVariableBinding.superInterfaces = referenceBindingArr;
                }
            }
        }
        return referenceBindingArr;
    }

    protected TypeBinding[] getDerivedTypesForDeferredInitialization() {
        return this.environment.getAnnotatedTypes(this);
    }

    public TypeBinding combineTypeAnnotations(TypeBinding typeBinding) {
        if (!hasTypeAnnotations()) {
            return typeBinding;
        }
        if (hasRelevantTypeUseNullAnnotations()) {
            typeBinding = typeBinding.withoutToplevelNullAnnotation();
        }
        return this.typeAnnotations != Binding.NO_ANNOTATIONS ? this.environment.createAnnotatedType(typeBinding, this.typeAnnotations) : typeBinding;
    }

    private boolean hasRelevantTypeUseNullAnnotations() {
        TypeVariableBinding[] typeVariableBindingArrTypeVariables;
        Binding binding = this.declaringElement;
        if (binding instanceof ReferenceBinding) {
            typeVariableBindingArrTypeVariables = ((ReferenceBinding) binding).original().typeVariables();
        } else if (binding instanceof MethodBinding) {
            typeVariableBindingArrTypeVariables = ((MethodBinding) binding).original().typeVariables;
        } else {
            throw new IllegalStateException("Unexpected declaring element:" + String.valueOf(this.declaringElement.readableName()));
        }
        return ((this.tagBits & TagBits.AnnotationNullMASK) & (~(TagBits.AnnotationNullMASK & typeVariableBindingArrTypeVariables[this.rank].tagBits))) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public long updateTagBits() {
        if (!this.inRecursiveFunction) {
            this.inRecursiveFunction = true;
            try {
                if (this.superclass != null) {
                    this.tagBits |= this.superclass.updateTagBits();
                }
                ReferenceBinding[] referenceBindingArr = this.superInterfaces;
                if (referenceBindingArr != null) {
                    for (ReferenceBinding referenceBinding : referenceBindingArr) {
                        this.tagBits |= referenceBinding.updateTagBits();
                    }
                }
            } finally {
                this.inRecursiveFunction = false;
            }
        }
        return super.updateTagBits();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isFreeTypeVariable() {
        return this.environment.usesNullTypeAnnotations() && this.environment.globalOptions.pessimisticNullAnalysisForFreeTypeVariablesEnabled && (this.tagBits & TagBits.AnnotationNullMASK) == 0;
    }
}
