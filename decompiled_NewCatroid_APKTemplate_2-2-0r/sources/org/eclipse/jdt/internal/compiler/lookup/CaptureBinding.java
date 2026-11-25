package org.eclipse.jdt.internal.compiler.lookup;

import io.netty.util.internal.StringUtil;
import kotlin.text.Typography;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes14.dex */
public class CaptureBinding extends TypeVariableBinding {
    public int captureID;
    public ASTNode cud;
    public int end;
    public TypeBinding lowerBound;
    TypeBinding pendingSubstitute;
    public ReferenceBinding sourceType;
    public int start;
    public WildcardBinding wildcard;

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isCapture() {
        return true;
    }

    public CaptureBinding(WildcardBinding wildcardBinding, ReferenceBinding referenceBinding, int i, int i2, ASTNode aSTNode, int i3) {
        super(TypeConstants.WILDCARD_CAPTURE_NAME_PREFIX, wildcardBinding.environment);
        this.wildcard = wildcardBinding;
        this.modifiers = CompilerOptions.ShouldImplementHashcode;
        this.fPackage = wildcardBinding.fPackage;
        this.sourceType = referenceBinding;
        this.start = i;
        this.end = i2;
        this.captureID = i3;
        this.tagBits |= 2305843009213693952L;
        this.cud = aSTNode;
        if (wildcardBinding.hasTypeAnnotations()) {
            CaptureBinding captureBinding = (CaptureBinding) clone(null);
            captureBinding.wildcard = (WildcardBinding) this.wildcard.unannotated();
            this.environment.getUnannotatedType(captureBinding);
            this.f626id = captureBinding.f626id;
            this.environment.typeSystem.cacheDerivedType(this, captureBinding, this);
            super.setTypeAnnotations(wildcardBinding.getTypeAnnotations(), wildcardBinding.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled);
            if (wildcardBinding.hasNullTypeAnnotations()) {
                this.tagBits |= 1048576;
                return;
            }
            return;
        }
        computeId(this.environment);
    }

    protected CaptureBinding(ReferenceBinding referenceBinding, char[] cArr, int i, int i2, int i3, LookupEnvironment lookupEnvironment) {
        super(cArr, null, 0, lookupEnvironment);
        this.modifiers = CompilerOptions.ShouldImplementHashcode;
        this.sourceType = referenceBinding;
        this.start = i;
        this.end = i2;
        this.captureID = i3;
    }

    public CaptureBinding(CaptureBinding captureBinding) {
        super(captureBinding);
        this.wildcard = captureBinding.wildcard;
        this.sourceType = captureBinding.sourceType;
        this.start = captureBinding.start;
        this.end = captureBinding.end;
        this.captureID = captureBinding.captureID;
        this.lowerBound = captureBinding.lowerBound;
        this.tagBits |= captureBinding.tagBits & 2305843009213693952L;
        this.cud = captureBinding.cud;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new CaptureBinding(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(this.sourceType.computeUniqueKey(false));
            stringBuffer.append(Typography.amp);
        }
        stringBuffer.append(TypeConstants.WILDCARD_CAPTURE);
        stringBuffer.append(this.wildcard.computeUniqueKey(false));
        stringBuffer.append(this.end);
        stringBuffer.append(';');
        int length = stringBuffer.length();
        char[] cArr = new char[length];
        stringBuffer.getChars(0, length, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        if (this.wildcard != null) {
            StringBuffer stringBuffer = new StringBuffer(10);
            AnnotationBinding[] typeAnnotations = getTypeAnnotations();
            int length = typeAnnotations == null ? 0 : typeAnnotations.length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append(typeAnnotations[i]);
                stringBuffer.append(StringUtil.SPACE);
            }
            stringBuffer.append(TypeConstants.WILDCARD_CAPTURE_NAME_PREFIX).append(this.captureID).append(TypeConstants.WILDCARD_CAPTURE_NAME_SUFFIX).append(this.wildcard.debugName());
            return stringBuffer.toString();
        }
        return super.debugName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        if (this.genericTypeSignature == null) {
            this.genericTypeSignature = CharOperation.concat(TypeConstants.WILDCARD_CAPTURE, this.wildcard.genericTypeSignature());
        }
        return this.genericTypeSignature;
    }

    public void initializeBounds(Scope scope, ParameterizedTypeBinding parameterizedTypeBinding) {
        TypeVariableBinding typeVariableBindingTypeVariable = this.wildcard.typeVariable();
        if (typeVariableBindingTypeVariable == null) {
            TypeBinding typeBinding = this.wildcard.bound;
            int i = this.wildcard.boundKind;
            if (i == 0) {
                setSuperClass(scope.getJavaLangObject());
                setSuperInterfaces(Binding.NO_SUPERINTERFACES);
                this.tagBits &= -536870913;
                return;
            }
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                setSuperClass(scope.getJavaLangObject());
                setSuperInterfaces(Binding.NO_SUPERINTERFACES);
                this.lowerBound = this.wildcard.bound;
                if ((typeBinding.tagBits & TagBits.HasTypeVariable) == 0) {
                    this.tagBits &= -536870913;
                    return;
                }
                return;
            }
            TypeBinding typeBindingCapture = typeBinding.capture(scope, this.start, this.end);
            if (typeBinding.isInterface()) {
                setSuperClass(scope.getJavaLangObject());
                setSuperInterfaces(new ReferenceBinding[]{(ReferenceBinding) typeBindingCapture});
            } else {
                if (typeBindingCapture.isArrayType() || TypeBinding.equalsEquals(typeBindingCapture, this)) {
                    setSuperClass(scope.getJavaLangObject());
                } else {
                    setSuperClass((ReferenceBinding) typeBindingCapture);
                }
                setSuperInterfaces(Binding.NO_SUPERINTERFACES);
            }
            setFirstBound(typeBindingCapture);
            if ((typeBindingCapture.tagBits & TagBits.HasTypeVariable) == 0) {
                this.tagBits &= -536870913;
                return;
            }
            return;
        }
        ReferenceBinding referenceBinding = typeVariableBindingTypeVariable.superclass;
        ReferenceBinding referenceBinding2 = (ReferenceBinding) Scope.substitute(parameterizedTypeBinding, referenceBinding);
        if (!TypeBinding.equalsEquals(referenceBinding2, this)) {
            referenceBinding = referenceBinding2;
        }
        ReferenceBinding[] referenceBindingArrSuperInterfaces = typeVariableBindingTypeVariable.superInterfaces();
        ReferenceBinding[] referenceBindingArrSubstitute = Scope.substitute((Substitution) parameterizedTypeBinding, referenceBindingArrSuperInterfaces);
        if (referenceBindingArrSubstitute != referenceBindingArrSuperInterfaces) {
            int length = referenceBindingArrSubstitute.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (TypeBinding.equalsEquals(referenceBindingArrSubstitute[i2], this)) {
                    referenceBindingArrSubstitute[i2] = referenceBindingArrSuperInterfaces[i2];
                }
            }
        }
        TypeBinding typeBinding2 = this.wildcard.bound;
        int i3 = this.wildcard.boundKind;
        if (i3 == 0) {
            setSuperClass(referenceBinding);
            setSuperInterfaces(referenceBindingArrSubstitute);
            this.tagBits &= -536870913;
        } else if (i3 == 1) {
            TypeBinding typeBindingCapture2 = typeBinding2.capture(scope, this.start, this.end);
            if (typeBinding2.isInterface()) {
                setSuperClass(referenceBinding);
                if (referenceBindingArrSubstitute == Binding.NO_SUPERINTERFACES) {
                    setSuperInterfaces(new ReferenceBinding[]{(ReferenceBinding) typeBindingCapture2});
                } else {
                    int length2 = referenceBindingArrSubstitute.length;
                    ReferenceBinding[] referenceBindingArr = new ReferenceBinding[length2 + 1];
                    System.arraycopy(referenceBindingArrSubstitute, 0, referenceBindingArr, 1, length2);
                    referenceBindingArr[0] = (ReferenceBinding) typeBindingCapture2;
                    setSuperInterfaces(Scope.greaterLowerBound(referenceBindingArr));
                }
            } else {
                if (typeBindingCapture2.isArrayType() || TypeBinding.equalsEquals(typeBindingCapture2, this)) {
                    setSuperClass(referenceBinding);
                } else {
                    setSuperClass((ReferenceBinding) typeBindingCapture2);
                    if (this.superclass.isSuperclassOf(referenceBinding)) {
                        setSuperClass(referenceBinding);
                    }
                }
                setSuperInterfaces(referenceBindingArrSubstitute);
            }
            setFirstBound(typeBindingCapture2);
            if ((typeBindingCapture2.tagBits & TagBits.HasTypeVariable) == 0) {
                this.tagBits &= -536870913;
            }
        } else if (i3 == 2) {
            setSuperClass(referenceBinding);
            if (TypeBinding.equalsEquals(typeVariableBindingTypeVariable.firstBound, referenceBinding) || TypeBinding.equalsEquals(typeBinding2, referenceBinding)) {
                setFirstBound(referenceBinding);
            }
            setSuperInterfaces(referenceBindingArrSubstitute);
            this.lowerBound = typeBinding2;
            if ((typeBinding2.tagBits & TagBits.HasTypeVariable) == 0) {
                this.tagBits &= -536870913;
            }
        }
        if (scope.environment().usesNullTypeAnnotations()) {
            evaluateNullAnnotations(scope, null);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isEquivalentTo(TypeBinding typeBinding) {
        if (equalsEquals(this, typeBinding)) {
            return true;
        }
        if (typeBinding == null) {
            return false;
        }
        if (this.firstBound != null && this.firstBound.isArrayType() && this.firstBound.isCompatibleWith(typeBinding)) {
            return true;
        }
        int iKind = typeBinding.kind();
        if (iKind == 516 || iKind == 8196) {
            return ((WildcardBinding) typeBinding).boundCheck(this);
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProperType(boolean z) {
        TypeBinding typeBinding = this.lowerBound;
        if (typeBinding != null && !typeBinding.isProperType(z)) {
            return false;
        }
        WildcardBinding wildcardBinding = this.wildcard;
        if (wildcardBinding == null || wildcardBinding.isProperType(z)) {
            return super.isProperType(z);
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        if (this.wildcard != null) {
            StringBuffer stringBuffer = new StringBuffer(10);
            stringBuffer.append(TypeConstants.WILDCARD_CAPTURE_NAME_PREFIX).append(this.captureID).append(TypeConstants.WILDCARD_CAPTURE_NAME_SUFFIX).append(this.wildcard.readableName());
            int length = stringBuffer.length();
            char[] cArr = new char[length];
            stringBuffer.getChars(0, length, cArr, 0);
            return cArr;
        }
        return super.readableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] signableName() {
        if (this.wildcard != null) {
            StringBuffer stringBuffer = new StringBuffer(10);
            stringBuffer.append(TypeConstants.WILDCARD_CAPTURE_SIGNABLE_NAME_SUFFIX).append(this.wildcard.readableName());
            int length = stringBuffer.length();
            char[] cArr = new char[length];
            stringBuffer.getChars(0, length, cArr, 0);
            return cArr;
        }
        return super.readableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        if (this.wildcard != null) {
            StringBuffer stringBuffer = new StringBuffer(10);
            stringBuffer.append(TypeConstants.WILDCARD_CAPTURE_NAME_PREFIX).append(this.captureID).append(TypeConstants.WILDCARD_CAPTURE_NAME_SUFFIX).append(this.wildcard.shortReadableName());
            int length = stringBuffer.length();
            char[] cArr = new char[length];
            stringBuffer.getChars(0, length, cArr, 0);
            return cArr;
        }
        return super.shortReadableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] nullAnnotatedReadableName(CompilerOptions compilerOptions, boolean z) {
        StringBuffer stringBuffer = new StringBuffer(10);
        appendNullAnnotation(stringBuffer, compilerOptions);
        stringBuffer.append(sourceName());
        if (!this.inRecursiveFunction) {
            this.inRecursiveFunction = true;
            try {
                if (this.wildcard != null) {
                    stringBuffer.append("of ");
                    stringBuffer.append(this.wildcard.withoutToplevelNullAnnotation().nullAnnotatedReadableName(compilerOptions, z));
                } else if (this.lowerBound != null) {
                    stringBuffer.append(" super ");
                    stringBuffer.append(this.lowerBound.nullAnnotatedReadableName(compilerOptions, z));
                } else if (this.firstBound != null) {
                    stringBuffer.append(" extends ");
                    stringBuffer.append(this.firstBound.nullAnnotatedReadableName(compilerOptions, z));
                    if (otherUpperBounds() != NO_TYPES) {
                        stringBuffer.append(" & ...");
                    }
                }
                this.inRecursiveFunction = false;
            } catch (Throwable th) {
                this.inRecursiveFunction = false;
                throw th;
            }
        }
        int length = stringBuffer.length();
        char[] cArr = new char[length];
        stringBuffer.getChars(0, length, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding withoutToplevelNullAnnotation() {
        WildcardBinding wildcardBinding;
        if (!hasNullTypeAnnotations()) {
            return this;
        }
        WildcardBinding wildcardBinding2 = this.wildcard;
        if (wildcardBinding2 != null && wildcardBinding2.hasNullTypeAnnotations() && (wildcardBinding = (WildcardBinding) this.wildcard.withoutToplevelNullAnnotation()) != this.wildcard) {
            CaptureBinding captureBinding = (CaptureBinding) this.environment.getUnannotatedType(this).clone(null);
            if (wildcardBinding.hasTypeAnnotations()) {
                captureBinding.tagBits |= 2097152;
            }
            captureBinding.wildcard = wildcardBinding;
            captureBinding.superclass = this.superclass;
            captureBinding.superInterfaces = this.superInterfaces;
            return this.environment.createAnnotatedType(captureBinding, this.environment.filterNullTypeAnnotations(this.typeAnnotations));
        }
        return super.withoutToplevelNullAnnotation();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding substituteInferenceVariable(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        TypeBinding typeBinding2 = this.pendingSubstitute;
        if (typeBinding2 != null) {
            return typeBinding2;
        }
        try {
            TypeBinding typeBindingSubstituteInferenceVariable = this.wildcard.substituteInferenceVariable(inferenceVariable, typeBinding);
            if (typeBindingSubstituteInferenceVariable == this.wildcard) {
                return this;
            }
            CaptureBinding captureBinding = (CaptureBinding) clone(enclosingType());
            captureBinding.wildcard = (WildcardBinding) typeBindingSubstituteInferenceVariable;
            this.pendingSubstitute = captureBinding;
            TypeBinding typeBinding3 = this.lowerBound;
            if (typeBinding3 != null) {
                captureBinding.lowerBound = typeBinding3.substituteInferenceVariable(inferenceVariable, typeBinding);
            }
            if (this.firstBound != null) {
                captureBinding.firstBound = this.firstBound.substituteInferenceVariable(inferenceVariable, typeBinding);
            }
            if (this.superclass != null) {
                captureBinding.superclass = (ReferenceBinding) this.superclass.substituteInferenceVariable(inferenceVariable, typeBinding);
            }
            if (this.superInterfaces != null) {
                int length = this.superInterfaces.length;
                captureBinding.superInterfaces = new ReferenceBinding[length];
                for (int i = 0; i < length; i++) {
                    captureBinding.superInterfaces[i] = (ReferenceBinding) this.superInterfaces[i].substituteInferenceVariable(inferenceVariable, typeBinding);
                }
            }
            return captureBinding;
        } finally {
            this.pendingSubstitute = null;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr, boolean z) {
        WildcardBinding wildcardBinding;
        super.setTypeAnnotations(annotationBindingArr, z);
        if (annotationBindingArr == Binding.NO_ANNOTATIONS || (wildcardBinding = this.wildcard) == null) {
            return;
        }
        this.wildcard = (WildcardBinding) wildcardBinding.environment.createAnnotatedType(this.wildcard, annotationBindingArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding uncapture(Scope scope) {
        return this.wildcard;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding
    protected TypeBinding[] getDerivedTypesForDeferredInitialization() {
        TypeBinding[] derivedTypes = this.environment.typeSystem.getDerivedTypes(this);
        if (derivedTypes.length <= 0) {
            return derivedTypes;
        }
        int i = 0;
        for (int i2 = 0; i2 < derivedTypes.length; i2++) {
            TypeBinding typeBinding = derivedTypes[i2];
            if (typeBinding != null && typeBinding.f626id == this.f626id) {
                derivedTypes[i] = derivedTypes[i2];
                i++;
            }
        }
        if (i >= derivedTypes.length) {
            return derivedTypes;
        }
        TypeBinding[] typeBindingArr = new TypeBinding[i];
        System.arraycopy(derivedTypes, 0, typeBindingArr, 0, i);
        return typeBindingArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding
    public String toString() {
        if (this.wildcard != null) {
            StringBuffer stringBuffer = new StringBuffer(10);
            AnnotationBinding[] typeAnnotations = getTypeAnnotations();
            int length = typeAnnotations == null ? 0 : typeAnnotations.length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append(typeAnnotations[i]);
                stringBuffer.append(StringUtil.SPACE);
            }
            stringBuffer.append(TypeConstants.WILDCARD_CAPTURE_NAME_PREFIX).append(this.captureID).append(TypeConstants.WILDCARD_CAPTURE_NAME_SUFFIX).append(this.wildcard);
            return stringBuffer.toString();
        }
        return super.toString();
    }
}
