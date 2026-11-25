package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: KtLightElements.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMethod;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMember;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "isDelegated", "", "()Z", "isMangled", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtLightMethod extends PsiAnnotationMethod, KtLightMember<PsiMethod> {
    boolean isMangled();

    default boolean isDelegated() {
        LightMemberOrigin lightMemberOrigin = getLightMemberOrigin();
        if ((lightMemberOrigin != null ? lightMemberOrigin.getOriginKind() : null) != JvmDeclarationOriginKind.DELEGATION) {
            LightMemberOrigin lightMemberOrigin2 = getLightMemberOrigin();
            if ((lightMemberOrigin2 != null ? lightMemberOrigin2.getOriginKind() : null) != JvmDeclarationOriginKind.CLASS_MEMBER_DELEGATION_TO_DEFAULT_IMPL) {
                return false;
            }
        }
        return true;
    }
}
