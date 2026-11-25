package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: KtLightElements.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u00022\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u00052\u00020\u0006J\b\u0010\u000b\u001a\u00020\fH&R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightMember;", "D", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMember;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightDeclaration;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameIdentifierOwner;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiDocCommentOwner;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "getLightMemberOrigin", "()Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "getContainingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtLightMember<D extends PsiMember> extends PsiDocCommentOwner, PsiMember, PsiNameIdentifierOwner, KtLightDeclaration<KtDeclaration, D> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    KtLightClass mo6092getContainingClass();

    LightMemberOrigin getLightMemberOrigin();
}
