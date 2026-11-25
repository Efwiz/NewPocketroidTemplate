package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;

/* JADX INFO: loaded from: classes14.dex */
public abstract class NameReference extends Reference implements InvocationSite {
    public TypeBinding actualReceiverType;
    public Binding binding;

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public InferenceContext18 freshInferenceContext(Scope scope) {
        return null;
    }

    public abstract char[][] getName();

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setFieldIndex(int i) {
    }

    public abstract String unboundReferenceErrorName();

    public NameReference() {
        this.bits |= 7;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FieldBinding fieldBinding() {
        return (FieldBinding) this.binding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FieldBinding lastFieldBinding() {
        if ((this.bits & 7) == 1) {
            return fieldBinding();
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isTypeAccess() {
        Binding binding = this.binding;
        return binding == null || (binding instanceof ReferenceBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean isTypeReference() {
        return this.binding instanceof ReferenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setActualReceiverType(ReferenceBinding referenceBinding) {
        if (referenceBinding == null) {
            return;
        }
        this.actualReceiverType = referenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setDepth(int i) {
        this.bits &= -8161;
        if (i > 0) {
            this.bits = ((i & 255) << 5) | this.bits;
        }
    }

    protected void checkEffectiveFinality(LocalVariableBinding localVariableBinding, Scope scope) {
        if ((this.bits & 524288) == 0 || localVariableBinding.isFinal() || localVariableBinding.isEffectivelyFinal()) {
            return;
        }
        scope.problemReporter().cannotReferToNonEffectivelyFinalOuterLocal(localVariableBinding, this);
        throw new AbortMethod(scope.referenceCompilationUnit().compilationResult, null);
    }
}
