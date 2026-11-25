package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.psi.KtParameter;

/* JADX INFO: compiled from: KtLightElements.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0002R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightParameter;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightDeclaration;", "Lorg/jetbrains/kotlin/psi/KtParameter;", "method", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "getMethod", "()Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtLightParameter extends PsiParameter, KtLightDeclaration<KtParameter, PsiParameter> {
    KtLightMethod getMethod();
}
