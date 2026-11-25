package org.eclipse.jdt.internal.compiler.lookup;

import kotlin.text.Typography;
import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class CaptureBinding18 extends CaptureBinding {
    private char[] originalName;
    private CaptureBinding18 prototype;
    int recursionLevel;
    TypeBinding[] upperBounds;

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding
    public void initializeBounds(Scope scope, ParameterizedTypeBinding parameterizedTypeBinding) {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding uncapture(Scope scope) {
        return this;
    }

    public CaptureBinding18(ReferenceBinding referenceBinding, char[] cArr, char[] cArr2, int i, int i2, int i3, LookupEnvironment lookupEnvironment) {
        super(referenceBinding, cArr, i, i2, i3, lookupEnvironment);
        this.recursionLevel = 0;
        this.originalName = cArr2;
        this.prototype = this;
    }

    private CaptureBinding18(CaptureBinding18 captureBinding18) {
        super(captureBinding18);
        this.recursionLevel = 0;
        this.sourceName = CharOperation.append(captureBinding18.sourceName, '\'');
        this.originalName = captureBinding18.originalName;
        this.upperBounds = captureBinding18.upperBounds;
        this.prototype = captureBinding18.prototype;
    }

    public boolean setUpperBounds(TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding) {
        this.upperBounds = typeBindingArr;
        if (typeBindingArr.length > 0) {
            this.firstBound = typeBindingArr[0];
        }
        if (!isConsistentIntersection(typeBindingArr)) {
            return false;
        }
        int i = 0;
        for (TypeBinding typeBinding : typeBindingArr) {
            if (typeBinding instanceof ReferenceBinding) {
                if (this.superclass == null && typeBinding.isClass()) {
                    this.superclass = (ReferenceBinding) typeBinding;
                } else if (typeBinding.isInterface()) {
                    i++;
                }
            } else if (TypeBinding.equalsEquals(typeBinding.leafComponentType(), this)) {
                return false;
            }
        }
        this.superInterfaces = new ReferenceBinding[i];
        int i2 = 0;
        for (TypeBinding typeBinding2 : typeBindingArr) {
            if (typeBinding2.isInterface()) {
                this.superInterfaces[i2] = (ReferenceBinding) typeBinding2;
                i2++;
            }
        }
        if (this.superclass != null) {
            return true;
        }
        this.superclass = referenceBinding;
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new CaptureBinding18(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding[] getMethods(char[] cArr) {
        TypeBinding[] typeBindingArr = this.upperBounds;
        if (typeBindingArr.length == 1) {
            TypeBinding typeBinding = typeBindingArr[0];
            if (typeBinding instanceof ReferenceBinding) {
                return ((ReferenceBinding) typeBinding).getMethods(cArr);
            }
        }
        return super.getMethods(cArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding erasure() {
        TypeBinding[] typeBindingArr = this.upperBounds;
        if (typeBindingArr != null && typeBindingArr.length > 1) {
            ReferenceBinding[] referenceBindingArr = new ReferenceBinding[typeBindingArr.length];
            int i = 0;
            boolean z = false;
            while (true) {
                TypeBinding[] typeBindingArr2 = this.upperBounds;
                if (i >= typeBindingArr2.length) {
                    break;
                }
                ReferenceBinding referenceBinding = (ReferenceBinding) typeBindingArr2[i].erasure();
                referenceBindingArr[i] = referenceBinding;
                if (i > 0 && TypeBinding.notEquals(referenceBindingArr[0], referenceBinding)) {
                    z = true;
                }
                i++;
            }
            if (!z) {
                return referenceBindingArr[0];
            }
            return this.environment.createIntersectionType18(referenceBindingArr);
        }
        return super.erasure();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isEquivalentTo(TypeBinding typeBinding) {
        if (equalsEquals(this, typeBinding)) {
            return true;
        }
        if (typeBinding == null || this.upperBounds == null) {
            return false;
        }
        int i = 0;
        while (true) {
            TypeBinding[] typeBindingArr = this.upperBounds;
            if (i >= typeBindingArr.length) {
                return true;
            }
            TypeBinding typeBinding2 = typeBindingArr[i];
            if (typeBinding2 != null && typeBinding2.isArrayType()) {
                if (!typeBinding2.isCompatibleWith(typeBinding)) {
                    return false;
                }
            } else {
                int iKind = typeBinding.kind();
                if ((iKind == 516 || iKind == 8196) && !((WildcardBinding) typeBinding).boundCheck(typeBinding2)) {
                    return false;
                }
            }
            i++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003f  */
    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        ReferenceBinding[] referenceBindingArr;
        if (TypeBinding.equalsEquals(this, typeBinding) || this.inRecursiveFunction) {
            return true;
        }
        this.inRecursiveFunction = true;
        try {
            TypeBinding[] typeBindingArr = this.upperBounds;
            if (typeBindingArr != null) {
                int length = typeBindingArr.length;
                int iKind = typeBinding.kind();
                if (iKind == 8196 && typeBinding.boundKind() == 1) {
                    TypeBinding typeBindingAllBounds = ((WildcardBinding) typeBinding).allBounds();
                    if (typeBindingAllBounds instanceof IntersectionTypeBinding18) {
                        referenceBindingArr = ((IntersectionTypeBinding18) typeBindingAllBounds).intersectingTypes;
                    } else {
                        referenceBindingArr = null;
                    }
                } else if (iKind == 32772) {
                    referenceBindingArr = ((IntersectionTypeBinding18) typeBinding).intersectingTypes;
                } else {
                    referenceBindingArr = null;
                }
                if (referenceBindingArr != null) {
                    int length2 = referenceBindingArr.length;
                    TypeBinding[] typeBindingArr2 = new TypeBinding[length2];
                    System.arraycopy(referenceBindingArr, 0, typeBindingArr2, 0, length2);
                    int i = length2;
                    for (int i2 = 0; i2 < length; i2++) {
                        TypeBinding typeBinding2 = this.upperBounds[i2];
                        for (int i3 = 0; i3 < length2; i3++) {
                            TypeBinding typeBinding3 = typeBindingArr2[i3];
                            if (typeBinding3 != null && typeBinding2.isCompatibleWith(typeBinding3, scope)) {
                                typeBindingArr2[i3] = null;
                                i--;
                                if (i != 0) {
                                    break;
                                }
                                this.inRecursiveFunction = false;
                                return true;
                            }
                        }
                    }
                    this.inRecursiveFunction = false;
                    return false;
                }
                for (int i4 = 0; i4 < length; i4++) {
                    if (this.upperBounds[i4].isCompatibleWith(typeBinding, scope)) {
                        this.inRecursiveFunction = false;
                        return true;
                    }
                }
            }
            this.inRecursiveFunction = false;
            return false;
        } catch (Throwable th) {
            this.inRecursiveFunction = false;
            throw th;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding findSuperTypeOriginatingFrom(TypeBinding typeBinding) {
        TypeBinding[] typeBindingArr = this.upperBounds;
        if (typeBindingArr != null && typeBindingArr.length > 1) {
            int i = 0;
            while (true) {
                TypeBinding[] typeBindingArr2 = this.upperBounds;
                if (i >= typeBindingArr2.length) {
                    break;
                }
                TypeBinding typeBindingFindSuperTypeOriginatingFrom = typeBindingArr2[i].findSuperTypeOriginatingFrom(typeBinding);
                if (typeBindingFindSuperTypeOriginatingFrom != null) {
                    return typeBindingFindSuperTypeOriginatingFrom;
                }
                i++;
            }
        }
        return super.findSuperTypeOriginatingFrom(typeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding substituteInferenceVariable(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        boolean zNotEquals;
        ReferenceBinding[] referenceBindingArr;
        TypeBinding[] typeBindingArr;
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
            TypeBinding typeBindingSubstituteInferenceVariable = null;
            if (this.superInterfaces != null) {
                int length = this.superInterfaces.length;
                if (zNotEquals) {
                    referenceBindingArr = new ReferenceBinding[length];
                    System.arraycopy(this.superInterfaces, 0, referenceBindingArr, 0, length);
                } else {
                    referenceBindingArr = null;
                }
                for (int i = 0; i < length; i++) {
                    ReferenceBinding referenceBinding2 = this.superInterfaces[i];
                    if (referenceBinding2 != null) {
                        ReferenceBinding referenceBinding3 = (ReferenceBinding) referenceBinding2.substituteInferenceVariable(inferenceVariable, typeBinding);
                        if (TypeBinding.notEquals(referenceBinding3, this.superInterfaces[i])) {
                            if (referenceBindingArr == null) {
                                referenceBindingArr = new ReferenceBinding[length];
                                System.arraycopy(this.superInterfaces, 0, referenceBindingArr, 0, length);
                            }
                            referenceBindingArr[i] = referenceBinding3;
                            zNotEquals = true;
                        }
                    }
                }
            } else {
                referenceBindingArr = null;
            }
            TypeBinding[] typeBindingArr2 = this.upperBounds;
            if (typeBindingArr2 != null) {
                int length2 = typeBindingArr2.length;
                if (zNotEquals) {
                    typeBindingArr = new TypeBinding[length2];
                    System.arraycopy(typeBindingArr2, 0, typeBindingArr, 0, length2);
                } else {
                    typeBindingArr = null;
                }
                for (int i2 = 0; i2 < length2; i2++) {
                    TypeBinding typeBinding2 = this.upperBounds[i2];
                    if (typeBinding2 != null) {
                        TypeBinding typeBindingSubstituteInferenceVariable2 = typeBinding2.substituteInferenceVariable(inferenceVariable, typeBinding);
                        if (TypeBinding.notEquals(typeBindingSubstituteInferenceVariable2, this.upperBounds[i2])) {
                            if (typeBindingArr == null) {
                                typeBindingArr = new TypeBinding[length2];
                                System.arraycopy(this.upperBounds, 0, typeBindingArr, 0, length2);
                            }
                            typeBindingArr[i2] = typeBindingSubstituteInferenceVariable2;
                            zNotEquals = true;
                        }
                    }
                }
            } else {
                typeBindingArr = null;
            }
            if (this.firstBound != null) {
                typeBindingSubstituteInferenceVariable = this.firstBound.substituteInferenceVariable(inferenceVariable, typeBinding);
                zNotEquals |= TypeBinding.notEquals(this.firstBound, typeBindingSubstituteInferenceVariable);
            }
            if (!zNotEquals) {
                return this;
            }
            final CaptureBinding18 captureBinding18 = (CaptureBinding18) clone(enclosingType());
            captureBinding18.tagBits = this.tagBits;
            Substitution substitution = new Substitution() { // from class: org.eclipse.jdt.internal.compiler.lookup.CaptureBinding18.1
                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public boolean isRawSubstitution() {
                    return false;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public TypeBinding substitute(TypeVariableBinding typeVariableBinding) {
                    return typeVariableBinding == CaptureBinding18.this ? captureBinding18 : typeVariableBinding;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public LookupEnvironment environment() {
                    return CaptureBinding18.this.environment;
                }
            };
            if (typeBindingSubstituteInferenceVariable != null) {
                captureBinding18.firstBound = Scope.substitute(substitution, typeBindingSubstituteInferenceVariable);
            }
            captureBinding18.superclass = (ReferenceBinding) Scope.substitute(substitution, referenceBinding);
            captureBinding18.superInterfaces = Scope.substitute(substitution, referenceBindingArr);
            captureBinding18.upperBounds = Scope.substitute(substitution, typeBindingArr);
            return captureBinding18;
        } finally {
            this.inRecursiveFunction = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProperType(boolean z) {
        if (!z) {
            return false;
        }
        if (this.inRecursiveFunction) {
            return true;
        }
        this.inRecursiveFunction = true;
        try {
            if (this.lowerBound != null && !this.lowerBound.isProperType(z)) {
                return false;
            }
            if (this.upperBounds != null) {
                int i = 0;
                while (true) {
                    TypeBinding[] typeBindingArr = this.upperBounds;
                    if (i >= typeBindingArr.length) {
                        break;
                    }
                    if (!typeBindingArr[i].isProperType(z)) {
                        return false;
                    }
                    i++;
                }
            }
            return true;
        } finally {
            this.inRecursiveFunction = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        char[] cArrConcat;
        if (this.genericTypeSignature == null) {
            try {
                CaptureBinding18 captureBinding18 = this.prototype;
                int i = captureBinding18.recursionLevel;
                captureBinding18.recursionLevel = i + 1;
                if (i > 0 || this.firstBound == null) {
                    cArrConcat = TypeConstants.WILDCARD_STAR;
                } else if (this.upperBounds != null) {
                    cArrConcat = CharOperation.concat(TypeConstants.WILDCARD_PLUS, this.firstBound.genericTypeSignature());
                } else if (this.lowerBound != null) {
                    cArrConcat = CharOperation.concat(TypeConstants.WILDCARD_MINUS, this.lowerBound.genericTypeSignature());
                } else {
                    cArrConcat = TypeConstants.WILDCARD_STAR;
                }
                this.genericTypeSignature = CharOperation.concat(TypeConstants.WILDCARD_CAPTURE, cArrConcat);
            } finally {
                this.prototype.recursionLevel--;
            }
        }
        return this.genericTypeSignature;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        if (this.lowerBound == null && this.firstBound != null) {
            CaptureBinding18 captureBinding18 = this.prototype;
            int i = captureBinding18.recursionLevel;
            if (i < 2) {
                try {
                    captureBinding18.recursionLevel = i + 1;
                    TypeBinding[] typeBindingArr = this.upperBounds;
                    if (typeBindingArr != null && typeBindingArr.length > 1) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(this.upperBounds[0].readableName());
                        for (int i2 = 1; i2 < this.upperBounds.length; i2++) {
                            stringBuffer.append(Typography.amp).append(this.upperBounds[i2].readableName());
                        }
                        int length = stringBuffer.length();
                        char[] cArr = new char[length];
                        stringBuffer.getChars(0, length, cArr, 0);
                        return cArr;
                    }
                    return this.firstBound.readableName();
                } finally {
                    this.prototype.recursionLevel--;
                }
            }
            return this.originalName;
        }
        return super.readableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        if (this.lowerBound == null && this.firstBound != null) {
            CaptureBinding18 captureBinding18 = this.prototype;
            int i = captureBinding18.recursionLevel;
            if (i < 2) {
                try {
                    captureBinding18.recursionLevel = i + 1;
                    TypeBinding[] typeBindingArr = this.upperBounds;
                    if (typeBindingArr != null && typeBindingArr.length > 1) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(this.upperBounds[0].shortReadableName());
                        for (int i2 = 1; i2 < this.upperBounds.length; i2++) {
                            stringBuffer.append(Typography.amp).append(this.upperBounds[i2].shortReadableName());
                        }
                        int length = stringBuffer.length();
                        char[] cArr = new char[length];
                        stringBuffer.getChars(0, length, cArr, 0);
                        return cArr;
                    }
                    return this.firstBound.shortReadableName();
                } finally {
                    this.prototype.recursionLevel--;
                }
            }
            return this.originalName;
        }
        return super.shortReadableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.CaptureBinding, org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(TypeConstants.CAPTURE18);
        stringBuffer.append('{').append(this.end).append('#').append(this.captureID).append("};");
        int length = stringBuffer.length();
        char[] cArr = new char[length];
        stringBuffer.getChars(0, length, cArr, 0);
        return cArr;
    }
}
