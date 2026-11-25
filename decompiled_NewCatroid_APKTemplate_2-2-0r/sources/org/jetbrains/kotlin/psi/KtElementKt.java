package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: KtElement.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getModificationStamp", "", "Lorg/jetbrains/kotlin/psi/KtElement;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtElementKt {
    public static final long getModificationStamp(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (ktElement instanceof KtFile) {
            return ((KtFile) ktElement).getModificationStamp();
        }
        if (ktElement instanceof KtDeclarationStub) {
            return ((KtDeclarationStub) ktElement).getModificationStamp();
        }
        if (ktElement instanceof KtSuperTypeList) {
            return ((KtSuperTypeList) ktElement).getModificationStamp();
        }
        if (ktElement instanceof KtScriptInitializer) {
            return ((KtScriptInitializer) ktElement).getModificationStamp();
        }
        PsiElement parent = ktElement.getMyParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElement");
        return getModificationStamp((KtElement) parent);
    }
}
