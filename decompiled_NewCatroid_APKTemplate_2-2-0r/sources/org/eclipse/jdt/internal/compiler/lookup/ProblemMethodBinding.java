package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class ProblemMethodBinding extends MethodBinding {
    public MethodBinding closestMatch;
    public InferenceContext18 inferenceContext;
    private int problemReason;

    public ProblemMethodBinding(char[] cArr, TypeBinding[] typeBindingArr, int i) {
        this.selector = cArr;
        this.parameters = (typeBindingArr == null || typeBindingArr.length == 0) ? Binding.NO_PARAMETERS : typeBindingArr;
        this.problemReason = i;
        this.thrownExceptions = Binding.NO_EXCEPTIONS;
    }

    public ProblemMethodBinding(char[] cArr, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding, int i) {
        this.selector = cArr;
        this.parameters = (typeBindingArr == null || typeBindingArr.length == 0) ? Binding.NO_PARAMETERS : typeBindingArr;
        this.declaringClass = referenceBinding;
        this.problemReason = i;
        this.thrownExceptions = Binding.NO_EXCEPTIONS;
    }

    public ProblemMethodBinding(MethodBinding methodBinding, char[] cArr, TypeBinding[] typeBindingArr, int i) {
        this(cArr, typeBindingArr, i);
        this.closestMatch = methodBinding;
        if (methodBinding == null || i == 3) {
            return;
        }
        this.declaringClass = methodBinding.declaringClass;
        this.returnType = methodBinding.returnType;
        if (i == 23) {
            this.thrownExceptions = methodBinding.thrownExceptions;
            this.typeVariables = methodBinding.typeVariables;
            this.modifiers = methodBinding.modifiers;
            this.tagBits = methodBinding.tagBits;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    MethodBinding computeSubstitutedMethod(MethodBinding methodBinding, LookupEnvironment lookupEnvironment) {
        MethodBinding methodBinding2 = this.closestMatch;
        return methodBinding2 == null ? this : methodBinding2.computeSubstitutedMethod(methodBinding, lookupEnvironment);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding findOriginalInheritedMethod(MethodBinding methodBinding) {
        MethodBinding methodBinding2 = this.closestMatch;
        return methodBinding2 == null ? this : methodBinding2.findOriginalInheritedMethod(methodBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding genericMethod() {
        MethodBinding methodBinding = this.closestMatch;
        return methodBinding == null ? this : methodBinding.genericMethod();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding original() {
        MethodBinding methodBinding = this.closestMatch;
        return methodBinding == null ? this : methodBinding.original();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding shallowOriginal() {
        MethodBinding methodBinding = this.closestMatch;
        return methodBinding == null ? this : methodBinding.shallowOriginal();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding tiebreakMethod() {
        MethodBinding methodBinding = this.closestMatch;
        return methodBinding == null ? this : methodBinding.tiebreakMethod();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean hasSubstitutedParameters() {
        MethodBinding methodBinding = this.closestMatch;
        if (methodBinding != null) {
            return methodBinding.hasSubstitutedParameters();
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean isParameterizedGeneric() {
        return this.closestMatch instanceof ParameterizedGenericMethodBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int problemId() {
        return this.problemReason;
    }
}
