package org.jetbrains.kotlin.psi;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: KtClassOrObject.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\u0003\"\u001f\u0010\u0000\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\b"}, d2 = {"allConstructors", "", "Lorg/jetbrains/kotlin/psi/KtConstructor;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getAllConstructors", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;)Ljava/util/List;", "getOrCreateBody", "Lorg/jetbrains/kotlin/psi/KtClassBody;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtClassOrObjectKt {
    public static final KtClassBody getOrCreateBody(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        KtClassBody body = ktClassOrObject.getBody();
        if (body != null) {
            return body;
        }
        Project project = ktClassOrObject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtClassBody ktClassBodyCreateEmptyClassBody = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createEmptyClassBody();
        if (!(ktClassOrObject instanceof KtEnumEntry)) {
            PsiElement psiElementAdd = ktClassOrObject.add(ktClassBodyCreateEmptyClassBody);
            Intrinsics.checkNotNull(psiElementAdd, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassBody");
            return (KtClassBody) psiElementAdd;
        }
        KtClassBody ktClassBody = ktClassBodyCreateEmptyClassBody;
        KtEnumEntry ktEnumEntry = (KtEnumEntry) ktClassOrObject;
        KtInitializerList initializerList = ktEnumEntry.getInitializerList();
        PsiElement psiElementAddAfter = ktClassOrObject.addAfter(ktClassBody, initializerList != null ? initializerList : ktEnumEntry.mo7392getNameIdentifier());
        Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassBody");
        return (KtClassBody) psiElementAddAfter;
    }

    public static final List<KtConstructor<?>> getAllConstructors(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        return CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(ktClassOrObject.getPrimaryConstructor()), (Iterable) ktClassOrObject.getSecondaryConstructors());
    }
}
