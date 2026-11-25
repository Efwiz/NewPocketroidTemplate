package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class HierarchicalMethodSignatureImpl extends HierarchicalMethodSignature {
    private List<HierarchicalMethodSignature> myInaccessibleSupers;
    private List<HierarchicalMethodSignature> mySupers;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "superSignatureHierarchical";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/HierarchicalMethodSignatureImpl";
        } else {
            objArr[0] = "signature";
        }
        if (i == 2) {
            objArr[1] = "getSuperSignatures";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/HierarchicalMethodSignatureImpl";
        } else {
            objArr[1] = "getInaccessibleSuperSignatures";
        }
        if (i == 1) {
            objArr[2] = "addSuperSignature";
        } else if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HierarchicalMethodSignatureImpl(MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethod) {
        super(methodSignatureBackedByPsiMethod);
        if (methodSignatureBackedByPsiMethod == null) {
            $$$reportNull$$$0(0);
        }
    }

    public void addSuperSignature(HierarchicalMethodSignature hierarchicalMethodSignature) {
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(1);
        }
        PsiMethod method = hierarchicalMethodSignature.getMethod();
        PsiMethod method2 = getMethod();
        if (PsiUtil.isAccessible(method2.getProject(), method, method2, null)) {
            if (this.mySupers == null) {
                this.mySupers = new SmartList();
            }
            this.mySupers.add(hierarchicalMethodSignature);
        } else {
            if (this.myInaccessibleSupers == null) {
                this.myInaccessibleSupers = new SmartList();
            }
            this.myInaccessibleSupers.add(hierarchicalMethodSignature);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature
    public List<HierarchicalMethodSignature> getSuperSignatures() {
        List<HierarchicalMethodSignature> listEmptyList = this.mySupers;
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(2);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature
    public List<HierarchicalMethodSignature> getInaccessibleSuperSignatures() {
        List<HierarchicalMethodSignature> inaccessibleSuperSignatures = this.myInaccessibleSupers;
        if (inaccessibleSuperSignatures == null) {
            inaccessibleSuperSignatures = super.getInaccessibleSuperSignatures();
        }
        if (inaccessibleSuperSignatures == null) {
            $$$reportNull$$$0(3);
        }
        return inaccessibleSuperSignatures;
    }
}
