package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaModuleReferenceImpl extends PsiReferenceBase.Poly<PsiJavaModuleReferenceElement> implements PsiJavaModuleReference {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaModuleReferenceImpl";
        } else if (i != 3) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "newName";
        }
        if (i == 1) {
            objArr[1] = "getCanonicalText";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaModuleReferenceImpl";
        } else {
            objArr[1] = "multiResolve";
        }
        if (i != 1 && i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "handleElementRename";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiJavaModuleReferenceImpl(PsiJavaModuleReferenceElement psiJavaModuleReferenceElement) {
        super(psiJavaModuleReferenceElement, new TextRange(0, psiJavaModuleReferenceElement.getTextLength()), false);
        if (psiJavaModuleReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String referenceText = ((PsiJavaModuleReferenceElement) getPsiElement()).getReferenceText();
        if (referenceText == null) {
            $$$reportNull$$$0(1);
        }
        return referenceText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase.Poly, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiJavaModule resolve() {
        return (PsiJavaModule) super.resolve();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public ResolveResult[] multiResolve(boolean z) {
        ResolveResult[] resolveResultArrResolveWithCaching = ResolveCache.getInstance(getProject()).resolveWithCaching(this, (ResolveCache.PolyVariantResolver<PsiJavaModuleReferenceImpl>) Resolver.INSTANCE, false, z);
        if (resolveResultArrResolveWithCaching == null) {
            $$$reportNull$$$0(2);
        }
        return resolveResultArrResolveWithCaching;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        PsiJavaModuleReferenceElement psiJavaModuleReferenceElement = (PsiJavaModuleReferenceElement) getPsiElement();
        if (psiJavaModuleReferenceElement instanceof PsiCompiledElement) {
            throw new IncorrectOperationException(JavaPsiBundle.message("psi.error.attempt.to.edit.class.file", psiJavaModuleReferenceElement.getContainingFile()));
        }
        return psiJavaModuleReferenceElement.replace(PsiElementFactory.getInstance(psiJavaModuleReferenceElement.getProject()).createModuleReferenceFromText(str, null));
    }

    private Project getProject() {
        return ((PsiJavaModuleReferenceElement) getPsiElement()).getProject();
    }

    private static class Resolver implements ResolveCache.PolyVariantResolver<PsiJavaModuleReferenceImpl> {
        private static final ResolveCache.PolyVariantResolver<PsiJavaModuleReferenceImpl> INSTANCE = new Resolver();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
            if (i == 1 || i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaModuleReferenceImpl$Resolver";
            } else {
                objArr[0] = "reference";
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[1] = "resolve";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaModuleReferenceImpl$Resolver";
            }
            if (i != 1 && i != 2 && i != 3) {
                objArr[2] = "resolve";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        private Resolver() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.PolyVariantResolver
        public ResolveResult[] resolve(PsiJavaModuleReferenceImpl psiJavaModuleReferenceImpl, boolean z) {
            PsiJavaModule moduleDeclaration;
            int i = 0;
            if (psiJavaModuleReferenceImpl == null) {
                $$$reportNull$$$0(0);
            }
            PsiJavaModuleReferenceElement psiJavaModuleReferenceElement = (PsiJavaModuleReferenceElement) psiJavaModuleReferenceImpl.getPsiElement();
            PsiFile containingFile = psiJavaModuleReferenceElement.getContainingFile();
            String canonicalText = psiJavaModuleReferenceImpl.getCanonicalText();
            boolean z2 = true;
            if ((containingFile instanceof PsiJavaFile) && (moduleDeclaration = ((PsiJavaFile) containingFile).getModuleDeclaration()) != null && moduleDeclaration.getName().equals(canonicalText)) {
                return new ResolveResult[]{new PsiElementResolveResult(moduleDeclaration)};
            }
            if (!z && !(psiJavaModuleReferenceElement.getLightParent() instanceof PsiPackageAccessibilityStatement)) {
                z2 = false;
            }
            Project project = containingFile.getProject();
            Collection<PsiJavaModule> collectionFindModules = JavaPsiFacade.getInstance(project).findModules(canonicalText, z2 ? GlobalSearchScope.allScope(project) : containingFile.getResolveScope());
            if (!collectionFindModules.isEmpty()) {
                ResolveResult[] resolveResultArr = new ResolveResult[collectionFindModules.size()];
                Iterator<PsiJavaModule> it = collectionFindModules.iterator();
                while (it.hasNext()) {
                    resolveResultArr[i] = new PsiElementResolveResult(it.next());
                    i++;
                }
                return resolveResultArr;
            }
            ResolveResult[] resolveResultArr2 = ResolveResult.EMPTY_ARRAY;
            if (resolveResultArr2 == null) {
                $$$reportNull$$$0(3);
            }
            return resolveResultArr2;
        }
    }
}
