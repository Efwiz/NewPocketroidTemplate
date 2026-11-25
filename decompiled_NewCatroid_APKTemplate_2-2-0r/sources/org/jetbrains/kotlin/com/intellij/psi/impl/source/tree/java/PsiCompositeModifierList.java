package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiCompositeModifierList extends LightModifierList {
    private final List<? extends PsiModifierList> mySublists;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "qualifiedName";
        } else if (i == 2 || i == 3) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCompositeModifierList";
        }
        if (i == 1 || i == 2 || i == 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCompositeModifierList";
        } else {
            objArr[1] = "getAnnotations";
        }
        if (i == 1) {
            objArr[2] = "findAnnotation";
        } else if (i == 2) {
            objArr[2] = "hasModifierProperty";
        } else if (i == 3) {
            objArr[2] = "hasExplicitModifier";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiCompositeModifierList(PsiManager psiManager, List<? extends PsiModifierList> list) {
        super(psiManager);
        this.mySublists = list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getAnnotations() {
        ArrayList arrayList = new ArrayList();
        Iterator<? extends PsiModifierList> it = this.mySublists.iterator();
        while (it.hasNext()) {
            ContainerUtil.addAll(arrayList, it.next().getAnnotations());
        }
        PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) arrayList.toArray(PsiAnnotation.EMPTY_ARRAY);
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiAnnotationArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        Iterator<? extends PsiModifierList> it = this.mySublists.iterator();
        while (it.hasNext()) {
            PsiAnnotation psiAnnotationFindAnnotation = it.next().findAnnotation(str);
            if (psiAnnotationFindAnnotation != null) {
                return psiAnnotationFindAnnotation;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        Iterator<? extends PsiModifierList> it = this.mySublists.iterator();
        while (it.hasNext()) {
            if (it.next().hasModifierProperty(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasExplicitModifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        Iterator<? extends PsiModifierList> it = this.mySublists.iterator();
        while (it.hasNext()) {
            if (it.next().hasExplicitModifier(str)) {
                return true;
            }
        }
        return false;
    }
}
