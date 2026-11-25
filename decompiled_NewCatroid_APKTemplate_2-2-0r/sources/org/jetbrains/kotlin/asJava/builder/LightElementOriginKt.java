package org.jetbrains.kotlin.asJava.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;

/* JADX INFO: compiled from: LightElementOrigin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0004¨\u0006\u0005"}, d2 = {"toLightClassOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightElementOrigin;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "toLightMemberOrigin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "light-classes"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightElementOriginKt {
    public static final LightElementOrigin toLightMemberOrigin(JvmDeclarationOrigin jvmDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(jvmDeclarationOrigin, "<this>");
        PsiElement element = jvmDeclarationOrigin.getElement();
        if (element instanceof KtAnnotationEntry) {
            return new DefaultLightElementOrigin(element);
        }
        return element instanceof KtDeclaration ? new LightMemberOriginForDeclaration((KtDeclaration) element, jvmDeclarationOrigin.getOriginKind(), jvmDeclarationOrigin.getParametersForJvmOverload(), null, 8, null) : LightElementOrigin.None.INSTANCE;
    }

    public static final LightElementOrigin toLightClassOrigin(PsiElement psiElement) {
        return psiElement != null ? new DefaultLightElementOrigin(psiElement) : LightElementOrigin.None.INSTANCE;
    }
}
