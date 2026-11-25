package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.util.containers.MostlySingularMultiMap;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public class SymbolCollectingProcessor implements ElementClassHint, PsiScopeProcessor {
    private PsiElement myCurrentFileContext;
    private final MostlySingularMultiMap<String, ResultWithContext> myResult = new MostlySingularMultiMap<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "event";
        } else if (i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 3) {
            objArr[0] = "state";
        } else if (i != 4) {
            objArr[0] = "hintKey";
        } else {
            objArr[0] = Namer.METADATA_CLASS_KIND;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/SymbolCollectingProcessor";
        if (i == 1) {
            objArr[2] = "handleEvent";
        } else if (i == 2 || i == 3) {
            objArr[2] = "execute";
        } else if (i != 4) {
            objArr[2] = "getHint";
        } else {
            objArr[2] = "shouldProcess";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(0);
        }
        if (key == ElementClassHint.KEY) {
            return this;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(1);
        }
        if (event == JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT) {
            this.myCurrentFileContext = (PsiElement) obj;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        PsiNamedElement psiNamedElement;
        String name2;
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(3);
        }
        if (!(psiElement instanceof PsiNamedElement) || !psiElement.isValid() || (name2 = (psiNamedElement = (PsiNamedElement) psiElement).getName()) == null) {
            return true;
        }
        this.myResult.add(name2, new ResultWithContext(psiNamedElement, this.myCurrentFileContext));
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint
    public boolean shouldProcess(ElementClassHint.DeclarationKind declarationKind) {
        if (declarationKind == null) {
            $$$reportNull$$$0(4);
        }
        return declarationKind == ElementClassHint.DeclarationKind.CLASS || declarationKind == ElementClassHint.DeclarationKind.PACKAGE || declarationKind == ElementClassHint.DeclarationKind.METHOD || declarationKind == ElementClassHint.DeclarationKind.FIELD;
    }

    public MostlySingularMultiMap<String, ResultWithContext> getResults() {
        return this.myResult;
    }

    public static class ResultWithContext {
        private final PsiNamedElement myElement;
        private final PsiElement myFileContext;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/SymbolCollectingProcessor$ResultWithContext";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/SymbolCollectingProcessor$ResultWithContext";
            } else {
                objArr[1] = "getElement";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        public ResultWithContext(PsiNamedElement psiNamedElement, PsiElement psiElement) {
            if (psiNamedElement == null) {
                $$$reportNull$$$0(0);
            }
            this.myElement = psiNamedElement;
            this.myFileContext = psiElement;
        }

        public PsiNamedElement getElement() {
            PsiNamedElement psiNamedElement = this.myElement;
            if (psiNamedElement == null) {
                $$$reportNull$$$0(1);
            }
            return psiNamedElement;
        }

        public PsiElement getFileContext() {
            return this.myFileContext;
        }

        public String toString() {
            return this.myElement.toString();
        }
    }
}
