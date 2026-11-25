package org.jetbrains.kotlin.resolve.lazy.data;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclarationContainer;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterList;

/* JADX INFO: loaded from: classes6.dex */
public interface KtClassLikeInfo extends KtDeclarationContainer {
    ClassKind getClassKind();

    List<KtObjectDeclaration> getCompanionObjects();

    FqName getContainingPackageFqName();

    /* JADX INFO: renamed from: getCorrespondingClassOrObject */
    KtClassOrObject mo7451getCorrespondingClassOrObject();

    List<KtAnnotationEntry> getDanglingAnnotations();

    /* JADX INFO: renamed from: getModifierList */
    KtModifierList mo7452getModifierList();

    List<? extends KtParameter> getPrimaryConstructorParameters();

    /* JADX INFO: renamed from: getScopeAnchor */
    PsiElement getScript();

    /* JADX INFO: renamed from: getTypeParameterList */
    KtTypeParameterList mo7453getTypeParameterList();
}
