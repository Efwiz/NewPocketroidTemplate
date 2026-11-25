package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiExtensibleClass extends PsiClass {
    List<PsiField> getOwnFields();

    List<PsiClass> getOwnInnerClasses();

    List<PsiMethod> getOwnMethods();
}
