package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: KtClass.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002¨\u0006\u0005"}, d2 = {"createPrimaryConstructorIfAbsent", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "Lorg/jetbrains/kotlin/psi/KtClass;", "createPrimaryConstructorParameterListIfAbsent", "Lorg/jetbrains/kotlin/psi/KtParameterList;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtClassKt {
    public static final KtPrimaryConstructor createPrimaryConstructorIfAbsent(KtClass ktClass) {
        Intrinsics.checkNotNullParameter(ktClass, "<this>");
        KtPrimaryConstructor primaryConstructor = ktClass.getPrimaryConstructor();
        if (primaryConstructor != null) {
            return primaryConstructor;
        }
        KtTypeParameterList typeParameterList = ktClass.mo7395getTypeParameterList();
        if (typeParameterList == null) {
            typeParameterList = ktClass.mo7392getNameIdentifier();
        }
        if (typeParameterList == null) {
            typeParameterList = ktClass.getLastChild();
        }
        Project project = ktClass.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        PsiElement psiElementAddAfter = ktClass.addAfter(KtPsiFactory.createPrimaryConstructor$default(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null), null, 1, null), typeParameterList);
        Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtPrimaryConstructor");
        return (KtPrimaryConstructor) psiElementAddAfter;
    }

    public static final KtParameterList createPrimaryConstructorParameterListIfAbsent(KtClass ktClass) {
        Intrinsics.checkNotNullParameter(ktClass, "<this>");
        KtPrimaryConstructor ktPrimaryConstructorCreatePrimaryConstructorIfAbsent = createPrimaryConstructorIfAbsent(ktClass);
        KtParameterList valueParameterList = ktPrimaryConstructorCreatePrimaryConstructorIfAbsent.getValueParameterList();
        if (valueParameterList != null) {
            return valueParameterList;
        }
        Project project = ktClass.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        PsiElement psiElementAdd = ktPrimaryConstructorCreatePrimaryConstructorIfAbsent.add(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createParameterList("()"));
        Intrinsics.checkNotNull(psiElementAdd, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtParameterList");
        return (KtParameterList) psiElementAdd;
    }
}
