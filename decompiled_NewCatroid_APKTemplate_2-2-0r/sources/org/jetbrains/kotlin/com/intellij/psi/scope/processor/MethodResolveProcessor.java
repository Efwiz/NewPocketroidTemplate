package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public class MethodResolveProcessor implements ElementClassHint, NameHint, PsiScopeProcessor {
    private final List<PsiMethod> myMethods;
    private final String myNameHint;

    /* JADX WARN: Code duplicated, block: B:10:0x001e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "state";
        } else if (i == 2) {
            objArr[0] = "hintKey";
        } else if (i == 3) {
            objArr[0] = Namer.METADATA_CLASS_KIND;
        } else if (i != 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "state";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/MethodResolveProcessor";
        if (i == 2) {
            objArr[2] = "getHint";
        } else if (i == 3) {
            objArr[2] = "shouldProcess";
        } else if (i != 4) {
            objArr[2] = "execute";
        } else {
            objArr[2] = "getName";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public MethodResolveProcessor() {
        this.myMethods = new ArrayList();
        this.myNameHint = null;
    }

    public MethodResolveProcessor(String str) {
        this.myMethods = new ArrayList();
        this.myNameHint = str;
    }

    public PsiMethod[] getMethods() {
        return (PsiMethod[]) this.myMethods.toArray(PsiMethod.EMPTY_ARRAY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement instanceof PsiMethod) {
            ContainerUtil.addIfNotNull(this.myMethods, (PsiMethod) psiElement);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(2);
        }
        if (key == ElementClassHint.KEY) {
            return this;
        }
        if (key != NameHint.KEY || this.myNameHint == null) {
            return null;
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint
    public boolean shouldProcess(ElementClassHint.DeclarationKind declarationKind) {
        if (declarationKind == null) {
            $$$reportNull$$$0(3);
        }
        return declarationKind == ElementClassHint.DeclarationKind.METHOD;
    }

    public static PsiMethod[] findMethod(PsiClass psiClass, String str) {
        MethodResolveProcessor methodResolveProcessor = new MethodResolveProcessor(str);
        psiClass.processDeclarations(methodResolveProcessor, ResolveState.initial(), null, psiClass);
        return methodResolveProcessor.getMethods();
    }

    public static PsiMethod[] getAllMethods(PsiClass psiClass) {
        MethodResolveProcessor methodResolveProcessor = new MethodResolveProcessor();
        psiClass.processDeclarations(methodResolveProcessor, ResolveState.initial(), null, psiClass);
        return methodResolveProcessor.getMethods();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.NameHint
    public String getName(ResolveState resolveState) {
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        return this.myNameHint;
    }
}
