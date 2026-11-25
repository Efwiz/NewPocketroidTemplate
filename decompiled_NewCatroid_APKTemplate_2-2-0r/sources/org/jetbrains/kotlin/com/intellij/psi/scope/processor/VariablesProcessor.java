package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VariablesProcessor implements ElementClassHint, PsiScopeProcessor {
    private final List<PsiVariable> myResultList;
    private boolean myStaticScopeFlag;
    private final boolean myStaticSensitiveFlag;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "pe";
        } else if (i == 2) {
            objArr[0] = "state";
        } else if (i == 3) {
            objArr[0] = "event";
        } else if (i != 4) {
            objArr[0] = Namer.METADATA_CLASS_KIND;
        } else {
            objArr[0] = "hintKey";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/VariablesProcessor";
        if (i == 1 || i == 2) {
            objArr[2] = "execute";
        } else if (i == 3) {
            objArr[2] = "handleEvent";
        } else if (i != 4) {
            objArr[2] = "shouldProcess";
        } else {
            objArr[2] = "getHint";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract boolean check(PsiVariable psiVariable, ResolveState resolveState);

    public VariablesProcessor(boolean z) {
        this(z, new SmartList());
    }

    public VariablesProcessor(boolean z, List<PsiVariable> list) {
        this.myStaticSensitiveFlag = z;
        this.myResultList = list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint
    public boolean shouldProcess(ElementClassHint.DeclarationKind declarationKind) {
        if (declarationKind == null) {
            $$$reportNull$$$0(0);
        }
        return declarationKind == ElementClassHint.DeclarationKind.VARIABLE || declarationKind == ElementClassHint.DeclarationKind.FIELD || declarationKind == ElementClassHint.DeclarationKind.ENUM_CONST;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement instanceof PsiVariable) {
            PsiVariable psiVariable = (PsiVariable) psiElement;
            if ((!this.myStaticSensitiveFlag || !this.myStaticScopeFlag || psiVariable.hasModifierProperty("static")) && check(psiVariable, resolveState)) {
                this.myResultList.add(psiVariable);
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public final void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(3);
        }
        if (JavaScopeProcessorEvent.isEnteringStaticScope(event, obj)) {
            this.myStaticScopeFlag = true;
        }
    }

    public int size() {
        return this.myResultList.size();
    }

    public PsiVariable getResult(int i) {
        return this.myResultList.get(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(4);
        }
        if (key == ElementClassHint.KEY) {
            return this;
        }
        return null;
    }
}
