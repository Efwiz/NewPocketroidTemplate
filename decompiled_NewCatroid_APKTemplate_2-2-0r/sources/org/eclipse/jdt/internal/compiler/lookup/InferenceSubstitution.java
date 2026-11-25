package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class InferenceSubstitution extends Scope.Substitutor implements Substitution {
    private LookupEnvironment environment;
    private InvocationSite site;
    private InferenceVariable[] variables;

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public boolean isRawSubstitution() {
        return false;
    }

    public InferenceSubstitution(LookupEnvironment lookupEnvironment, InferenceVariable[] inferenceVariableArr, InvocationSite invocationSite) {
        this.environment = lookupEnvironment;
        this.variables = inferenceVariableArr;
        this.site = invocationSite;
    }

    public InferenceSubstitution(InferenceContext18 inferenceContext18) {
        this(inferenceContext18.environment, inferenceContext18.inferenceVariables, inferenceContext18.currentInvocation);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope.Substitutor
    public TypeBinding substitute(Substitution substitution, TypeBinding typeBinding) {
        int i = 0;
        while (true) {
            InferenceVariable[] inferenceVariableArr = this.variables;
            if (i < inferenceVariableArr.length) {
                InferenceVariable inferenceVariable = inferenceVariableArr[i];
                if (this.site == inferenceVariable.site && TypeBinding.equalsEquals(getP(i), typeBinding)) {
                    return (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled && typeBinding.hasNullTypeAnnotations()) ? this.environment.createAnnotatedType(inferenceVariable.withoutToplevelNullAnnotation(), typeBinding.getTypeAnnotations()) : inferenceVariable;
                }
                i++;
            } else {
                return super.substitute(substitution, typeBinding);
            }
        }
    }

    protected TypeBinding getP(int i) {
        return this.variables[i].typeParameter;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public TypeBinding substitute(TypeVariableBinding typeVariableBinding) {
        ReferenceBinding referenceBinding = typeVariableBinding.superclass;
        ReferenceBinding[] referenceBindingArr = typeVariableBinding.superInterfaces;
        int i = 0;
        boolean z = false;
        while (true) {
            InferenceVariable[] inferenceVariableArr = this.variables;
            if (i >= inferenceVariableArr.length) {
                if (!z) {
                    return typeVariableBinding;
                }
                TypeVariableBinding typeVariableBinding2 = new TypeVariableBinding(typeVariableBinding.sourceName, typeVariableBinding.declaringElement, typeVariableBinding.rank, this.environment);
                typeVariableBinding2.superclass = referenceBinding;
                typeVariableBinding2.superInterfaces = referenceBindingArr;
                if (referenceBinding == null) {
                    referenceBinding = referenceBindingArr[0];
                }
                typeVariableBinding2.firstBound = referenceBinding;
                if (typeVariableBinding2.firstBound.hasNullTypeAnnotations()) {
                    typeVariableBinding2.tagBits |= 1048576;
                }
                return typeVariableBinding2;
            }
            InferenceVariable inferenceVariable = inferenceVariableArr[i];
            TypeBinding p = getP(i);
            if (TypeBinding.equalsEquals(p, typeVariableBinding)) {
                return inferenceVariable;
            }
            if (TypeBinding.equalsEquals(p, referenceBinding)) {
                referenceBinding = inferenceVariable;
            } else {
                if (referenceBindingArr != null) {
                    int length = referenceBindingArr.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        if (TypeBinding.equalsEquals(p, referenceBindingArr[i2])) {
                            if (referenceBindingArr == typeVariableBinding.superInterfaces) {
                                ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length];
                                System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, length);
                                referenceBindingArr = referenceBindingArr2;
                            }
                            referenceBindingArr[i2] = inferenceVariable;
                        }
                    }
                }
                i++;
            }
            z = true;
            i++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public LookupEnvironment environment() {
        return this.environment;
    }
}
