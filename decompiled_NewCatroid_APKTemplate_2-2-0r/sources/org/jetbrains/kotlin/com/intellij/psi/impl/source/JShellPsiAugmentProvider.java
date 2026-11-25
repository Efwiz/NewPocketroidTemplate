package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Set;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiJShellRootClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class JShellPsiAugmentProvider extends PsiAugmentProvider {
    private static final Set<String> JSHELL_FIELD_MODIFIERS = ContainerUtil.immutableSet("public", "static");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "modifiers";
        } else if (i != 2) {
            objArr[0] = "modifierList";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JShellPsiAugmentProvider";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JShellPsiAugmentProvider";
        } else {
            objArr[1] = "transformModifiers";
        }
        if (i != 2) {
            objArr[2] = "transformModifiers";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider
    protected Set<String> transformModifiers(PsiModifierList psiModifierList, Set<String> set) {
        if (psiModifierList == null) {
            $$$reportNull$$$0(0);
        }
        if (set == null) {
            $$$reportNull$$$0(1);
        }
        if (isInsideJShellField(psiModifierList)) {
            set = JSHELL_FIELD_MODIFIERS;
        }
        if (set == null) {
            $$$reportNull$$$0(2);
        }
        return set;
    }

    private static boolean isInsideJShellField(PsiElement psiElement) {
        PsiElement lightParent = psiElement.getLightParent();
        return (lightParent instanceof PsiField) && (lightParent.getLightParent() instanceof PsiJShellRootClass);
    }
}
