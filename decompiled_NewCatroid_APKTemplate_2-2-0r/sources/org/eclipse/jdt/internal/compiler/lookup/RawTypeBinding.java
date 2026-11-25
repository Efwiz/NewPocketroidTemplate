package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class RawTypeBinding extends ParameterizedTypeBinding {
    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ParameterizedTypeBinding capture(Scope scope, int i, int i2) {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isParameterizedType() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return 1028;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean mentionsAny(TypeBinding[] typeBindingArr, int i) {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding substituteInferenceVariable(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding uncapture(Scope scope) {
        return this;
    }

    public RawTypeBinding(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, LookupEnvironment lookupEnvironment) {
        super(referenceBinding, null, referenceBinding2, lookupEnvironment);
        this.tagBits &= -129;
        if ((referenceBinding.tagBits & 128) != 0) {
            if (referenceBinding instanceof MissingTypeBinding) {
                this.tagBits |= 128;
            } else if ((referenceBinding instanceof ParameterizedTypeBinding) && (((ParameterizedTypeBinding) referenceBinding).genericType() instanceof MissingTypeBinding)) {
                this.tagBits |= 128;
            }
        }
        if (referenceBinding2 != null && (referenceBinding2.tagBits & 128) != 0) {
            if (referenceBinding2 instanceof MissingTypeBinding) {
                this.tagBits |= 128;
            } else if ((referenceBinding2 instanceof ParameterizedTypeBinding) && (((ParameterizedTypeBinding) referenceBinding2).genericType() instanceof MissingTypeBinding)) {
                this.tagBits |= 128;
            }
        }
        if (referenceBinding2 == null || (referenceBinding2.modifiers & 1073741824) == 0) {
            this.modifiers &= -1073741825;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (isMemberType() && enclosingType().isParameterizedType()) {
            char[] cArrComputeUniqueKey = enclosingType().computeUniqueKey(false);
            stringBuffer.append(cArrComputeUniqueKey, 0, cArrComputeUniqueKey.length - 1);
            stringBuffer.append('.').append(sourceName()).append("<>;");
        } else {
            stringBuffer.append(genericType().computeUniqueKey(false));
            stringBuffer.insert(stringBuffer.length() - 1, "<>");
        }
        int length = stringBuffer.length();
        char[] cArr = new char[length];
        stringBuffer.getChars(0, length, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new RawTypeBinding(actualType(), (ReferenceBinding) typeBinding, this.environment);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding withoutToplevelNullAnnotation() {
        if (!hasNullTypeAnnotations()) {
            return this;
        }
        return this.environment.createRawType((ReferenceBinding) this.environment.getUnannotatedType(genericType()), enclosingType(), this.environment.filterNullTypeAnnotations(this.typeAnnotations));
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding
    public ParameterizedMethodBinding createParameterizedMethod(MethodBinding methodBinding) {
        if (methodBinding.typeVariables == Binding.NO_TYPE_VARIABLES || methodBinding.isStatic()) {
            return super.createParameterizedMethod(methodBinding);
        }
        return this.environment.createParameterizedGenericMethod(methodBinding, this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append(actualType().sourceName()).append("#RAW");
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String annotatedDebugName() {
        StringBuffer stringBuffer = new StringBuffer(super.annotatedDebugName());
        stringBuffer.append("#RAW");
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        if (this.genericTypeSignature == null) {
            if ((this.modifiers & 1073741824) == 0) {
                this.genericTypeSignature = genericType().signature();
            } else {
                StringBuffer stringBuffer = new StringBuffer(10);
                if (isMemberType()) {
                    ReferenceBinding referenceBindingEnclosingType = enclosingType();
                    char[] cArrGenericTypeSignature = referenceBindingEnclosingType.genericTypeSignature();
                    stringBuffer.append(cArrGenericTypeSignature, 0, cArrGenericTypeSignature.length - 1);
                    if ((1073741824 & referenceBindingEnclosingType.modifiers) != 0) {
                        stringBuffer.append('.');
                    } else {
                        stringBuffer.append('$');
                    }
                    stringBuffer.append(sourceName());
                } else {
                    char[] cArrSignature = genericType().signature();
                    stringBuffer.append(cArrSignature, 0, cArrSignature.length - 1);
                }
                stringBuffer.append(';');
                int length = stringBuffer.length();
                this.genericTypeSignature = new char[length];
                stringBuffer.getChars(0, length, this.genericTypeSignature, 0);
            }
        }
        return this.genericTypeSignature;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isEquivalentTo(TypeBinding typeBinding) {
        if (equalsEquals(this, typeBinding) || equalsEquals(erasure(), typeBinding)) {
            return true;
        }
        if (typeBinding == null) {
            return false;
        }
        int iKind = typeBinding.kind();
        if (iKind != 260) {
            if (iKind != 516) {
                if (iKind != 1028 && iKind != 2052) {
                    if (iKind != 8196) {
                        return false;
                    }
                }
            }
            return ((WildcardBinding) typeBinding).boundCheck(this);
        }
        return TypeBinding.equalsEquals(erasure(), typeBinding.erasure());
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProvablyDistinct(TypeBinding typeBinding) {
        if (TypeBinding.equalsEquals(this, typeBinding) || TypeBinding.equalsEquals(erasure(), typeBinding)) {
            return false;
        }
        if (typeBinding == null) {
            return true;
        }
        int iKind = typeBinding.kind();
        if (iKind == 260 || iKind == 1028 || iKind == 2052) {
            return TypeBinding.notEquals(erasure(), typeBinding.erasure());
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProperType(boolean z) {
        ReferenceBinding referenceBindingActualType = actualType();
        return referenceBindingActualType != null && referenceBindingActualType.isProperType(z);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding
    protected void initializeArguments() {
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = genericType().typeVariables();
        int length = typeVariableBindingArrTypeVariables.length;
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            typeBindingArr[i] = this.environment.convertToRawType(typeVariableBindingArrTypeVariables[i].erasure(), false);
        }
        this.arguments = typeBindingArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding getSingleAbstractMethod(Scope scope, boolean z) {
        int i = !z ? 1 : 0;
        if (this.singleAbstractMethod != null) {
            if (this.singleAbstractMethod[i] != null) {
                return this.singleAbstractMethod[i];
            }
        } else {
            this.singleAbstractMethod = new MethodBinding[2];
        }
        ReferenceBinding referenceBindingGenericType = genericType();
        MethodBinding singleAbstractMethod = referenceBindingGenericType.getSingleAbstractMethod(scope, z);
        if (singleAbstractMethod == null || !singleAbstractMethod.isValidBinding()) {
            this.singleAbstractMethod[i] = singleAbstractMethod;
            return singleAbstractMethod;
        }
        MethodBinding[] methods = ((ReferenceBinding) ((ReferenceBinding) scope.environment().convertToRawType(referenceBindingGenericType, true)).findSuperTypeOriginatingFrom(singleAbstractMethod.declaringClass)).getMethods(singleAbstractMethod.selector);
        for (MethodBinding methodBinding : methods) {
            if (methodBinding.isAbstract() && !methodBinding.redeclaresPublicObjectMethod(scope)) {
                this.singleAbstractMethod[i] = methodBinding;
                break;
            }
        }
        return this.singleAbstractMethod[i];
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        if (isMemberType()) {
            return CharOperation.concat(enclosingType().readableName(), this.sourceName, '.');
        }
        return CharOperation.concatWith(actualType().compoundName, '.');
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        if (isMemberType()) {
            return CharOperation.concat(enclosingType().shortReadableName(), this.sourceName, '.');
        }
        return actualType().sourceName;
    }
}
