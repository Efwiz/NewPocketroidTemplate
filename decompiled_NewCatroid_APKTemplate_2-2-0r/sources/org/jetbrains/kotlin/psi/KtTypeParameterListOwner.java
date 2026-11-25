package org.jetbrains.kotlin.psi;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public interface KtTypeParameterListOwner extends KtNamedDeclaration {
    /* JADX INFO: renamed from: getTypeConstraintList */
    KtTypeConstraintList mo7394getTypeConstraintList();

    List<KtTypeConstraint> getTypeConstraints();

    /* JADX INFO: renamed from: getTypeParameterList */
    KtTypeParameterList mo7395getTypeParameterList();

    List<KtTypeParameter> getTypeParameters();
}
