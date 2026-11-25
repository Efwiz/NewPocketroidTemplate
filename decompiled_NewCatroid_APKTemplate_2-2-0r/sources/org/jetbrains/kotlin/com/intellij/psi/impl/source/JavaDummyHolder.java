package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportHolder;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class JavaDummyHolder extends DummyHolder implements PsiImportHolder {
    private static final Map<String, PsiClass> EMPTY = Collections.emptyMap();
    private InjectedDeclarations myInjectedDeclarations;
    private Map<String, PsiClass> myPseudoImports;

    @FunctionalInterface
    public interface InjectedDeclarations {
        boolean process(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 6:
                objArr[0] = "aClass";
                break;
            case 7:
                objArr[0] = "injectedDeclarations";
                break;
            case 8:
                objArr[0] = "processor";
                break;
            case 9:
                objArr[0] = "state";
                break;
            case 10:
                objArr[0] = "place";
                break;
            case 11:
                objArr[0] = "originalFile";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaDummyHolder";
        switch (i) {
            case 6:
                objArr[2] = "importClass";
                break;
            case 7:
                objArr[2] = "setInjectedDeclarations";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "processDeclarations";
                break;
            case 11:
                objArr[2] = "setOriginalFile";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JavaDummyHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement) {
        super(psiManager, treeElement, psiElement, null, null, language(psiElement, JavaLanguage.INSTANCE));
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        this.myPseudoImports = EMPTY;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JavaDummyHolder(PsiManager psiManager, CharTable charTable, boolean z) {
        super(psiManager, null, null, charTable, Boolean.valueOf(z), JavaLanguage.INSTANCE);
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        this.myPseudoImports = EMPTY;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JavaDummyHolder(PsiManager psiManager, PsiElement psiElement) {
        super(psiManager, null, psiElement, null, null, language(psiElement, JavaLanguage.INSTANCE));
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
        this.myPseudoImports = EMPTY;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JavaDummyHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement, CharTable charTable) {
        super(psiManager, treeElement, psiElement, charTable, null, language(psiElement, JavaLanguage.INSTANCE));
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        this.myPseudoImports = EMPTY;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JavaDummyHolder(PsiManager psiManager, PsiElement psiElement, CharTable charTable) {
        super(psiManager, null, psiElement, charTable, null, language(psiElement, JavaLanguage.INSTANCE));
        if (psiManager == null) {
            $$$reportNull$$$0(4);
        }
        this.myPseudoImports = EMPTY;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JavaDummyHolder(PsiManager psiManager, CharTable charTable) {
        super(psiManager, null, null, charTable, null, JavaLanguage.INSTANCE);
        if (psiManager == null) {
            $$$reportNull$$$0(5);
        }
        this.myPseudoImports = EMPTY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportHolder
    public boolean importClass(PsiClass psiClass) {
        PsiClass psiClassResolveReferencedClass;
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        PsiElement context = getContext();
        String name2 = psiClass.getName();
        if (context != null && name2 != null && (psiClassResolveReferencedClass = JavaPsiFacade.getInstance(getProject()).getResolveHelper().resolveReferencedClass(name2, context)) != null) {
            return getManager().areElementsEquivalent(psiClass, psiClassResolveReferencedClass);
        }
        if (this.myPseudoImports.containsKey(name2)) {
            return true;
        }
        if (this.myPseudoImports == EMPTY) {
            this.myPseudoImports = new LinkedHashMap();
        }
        this.myPseudoImports.put(name2, psiClass);
        this.myManager.beforeChange(false);
        return true;
    }

    public void setInjectedDeclarations(InjectedDeclarations injectedDeclarations) {
        if (injectedDeclarations == null) {
            $$$reportNull$$$0(7);
        }
        this.myInjectedDeclarations = injectedDeclarations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(8);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(10);
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) {
            NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
            String name2 = nameHint != null ? nameHint.getName(resolveState) : null;
            if (name2 != null) {
                PsiClass psiClass = this.myPseudoImports.get(name2);
                if (psiClass != null && !psiScopeProcessor.execute(psiClass, resolveState)) {
                    return false;
                }
            } else {
                Iterator<PsiClass> it = this.myPseudoImports.values().iterator();
                while (it.hasNext()) {
                    if (!psiScopeProcessor.execute(it.next(), resolveState)) {
                        return false;
                    }
                }
            }
            if (getContext() == null) {
                return JavaResolveUtil.processImplicitlyImportedPackages(psiScopeProcessor, resolveState, psiElement2, getManager());
            }
        }
        InjectedDeclarations injectedDeclarations = this.myInjectedDeclarations;
        if (injectedDeclarations != null) {
            return injectedDeclarations.process(psiScopeProcessor, resolveState, psiElement, psiElement2);
        }
        return true;
    }

    public boolean isSamePackage(PsiElement psiElement) {
        PsiElement context = getContext();
        if (psiElement instanceof DummyHolder) {
            PsiElement context2 = psiElement.getContext();
            if (context == null || context2 == null) {
                return context == context2;
            }
            return JavaPsiFacade.getInstance(context.getProject()).arePackagesTheSame(context, context2);
        }
        if (!(psiElement instanceof PsiJavaFile)) {
            return false;
        }
        if (context != null) {
            return JavaPsiFacade.getInstance(context.getProject()).arePackagesTheSame(context, psiElement);
        }
        return ((PsiJavaFile) psiElement).getPackageName().isEmpty();
    }

    public boolean isInPackage(PsiPackage psiPackage) {
        PsiElement context = getContext();
        if (context != null) {
            return JavaPsiFacade.getInstance(context.getProject()).isInPackage(context, psiPackage);
        }
        return psiPackage == null || psiPackage.getQualifiedName().isEmpty();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl
    public void setOriginalFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(11);
        }
        super.setOriginalFile(psiFile);
        putUserData(PsiUtil.FILE_LANGUAGE_LEVEL_KEY, PsiUtil.getLanguageLevel(psiFile));
    }
}
