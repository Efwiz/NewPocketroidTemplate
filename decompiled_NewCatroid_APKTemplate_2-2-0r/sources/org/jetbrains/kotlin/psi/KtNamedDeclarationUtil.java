package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes6.dex */
public final class KtNamedDeclarationUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "namedDeclaration";
        objArr[1] = "org/jetbrains/kotlin/psi/KtNamedDeclarationUtil";
        if (i == 1) {
            objArr[2] = "getFQName";
        } else if (i != 2) {
            objArr[2] = "getUnsafeFQName";
        } else {
            objArr[2] = "getParentFqName";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static FqNameUnsafe getUnsafeFQName(KtNamedDeclaration ktNamedDeclaration) {
        if (ktNamedDeclaration == null) {
            $$$reportNull$$$0(0);
        }
        FqName fqNameMo7391getFqName = ktNamedDeclaration.mo7391getFqName();
        if (fqNameMo7391getFqName != null) {
            return fqNameMo7391getFqName.toUnsafe();
        }
        return null;
    }

    static FqName getFQName(KtNamedDeclaration ktNamedDeclaration) {
        FqName parentFqName;
        if (ktNamedDeclaration == null) {
            $$$reportNull$$$0(1);
        }
        Name nameAsName = ktNamedDeclaration.getNameAsName();
        if (nameAsName == null || (parentFqName = getParentFqName(ktNamedDeclaration)) == null) {
            return null;
        }
        return parentFqName.child(nameAsName);
    }

    public static FqName getParentFqName(KtNamedDeclaration ktNamedDeclaration) {
        if (ktNamedDeclaration == null) {
            $$$reportNull$$$0(2);
        }
        PsiElement parent = ktNamedDeclaration.getMyParent();
        if (parent instanceof KtClassBody) {
            parent = parent.getMyParent();
        }
        if (parent instanceof KtFile) {
            return ((KtFile) parent).getPackageFqName();
        }
        if ((parent instanceof KtNamedFunction) || (parent instanceof KtClass)) {
            return getFQName((KtNamedDeclaration) parent);
        }
        if (ktNamedDeclaration instanceof KtParameter) {
            KtClassOrObject classIfParameterIsProperty = KtPsiUtil.getClassIfParameterIsProperty((KtParameter) ktNamedDeclaration);
            if (classIfParameterIsProperty != null) {
                return getFQName(classIfParameterIsProperty);
            }
            return null;
        }
        if (parent instanceof KtObjectDeclaration) {
            return getFQName((KtNamedDeclaration) parent);
        }
        if (!(parent instanceof KtBlockExpression)) {
            return null;
        }
        PsiElement lightOwner = parent.getMyParent();
        if (lightOwner instanceof KtScript) {
            return ((KtScript) lightOwner).mo7391getFqName();
        }
        return null;
    }

    private KtNamedDeclarationUtil() {
    }
}
