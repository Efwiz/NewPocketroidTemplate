package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class VariablesNotProcessor extends VariablesProcessor {
    private final PsiVariable myVariable;

    public VariablesNotProcessor(PsiVariable psiVariable, boolean z, List<PsiVariable> list) {
        super(z, list);
        this.myVariable = psiVariable;
    }

    public VariablesNotProcessor(PsiVariable psiVariable, boolean z) {
        this(psiVariable, z, new SmartList());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.VariablesProcessor
    protected boolean check(PsiVariable psiVariable, ResolveState resolveState) {
        String name2 = psiVariable.getName();
        return (name2 == null || !name2.equals(this.myVariable.getName()) || psiVariable.equals(this.myVariable)) ? false : true;
    }
}
