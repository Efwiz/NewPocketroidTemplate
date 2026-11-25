package org.jetbrains.kotlin.asJava.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: LightElementOrigin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/asJava/builder/DefaultLightElementOrigin;", "Lorg/jetbrains/kotlin/asJava/builder/LightElementOrigin;", "originalElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)V", "originKind", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "getOriginalElement", "()Lcom/intellij/psi/PsiElement;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DefaultLightElementOrigin implements LightElementOrigin {
    private final PsiElement originalElement;

    public static /* synthetic */ DefaultLightElementOrigin copy$default(DefaultLightElementOrigin defaultLightElementOrigin, PsiElement psiElement, int i, Object obj) {
        if ((i & 1) != 0) {
            psiElement = defaultLightElementOrigin.originalElement;
        }
        return defaultLightElementOrigin.copy(psiElement);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final PsiElement getOriginalElement() {
        return this.originalElement;
    }

    public final DefaultLightElementOrigin copy(PsiElement originalElement) {
        return new DefaultLightElementOrigin(originalElement);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof DefaultLightElementOrigin) && Intrinsics.areEqual(this.originalElement, ((DefaultLightElementOrigin) other).originalElement);
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightElementOrigin
    public JvmDeclarationOriginKind getOriginKind() {
        return null;
    }

    public int hashCode() {
        PsiElement psiElement = this.originalElement;
        if (psiElement == null) {
            return 0;
        }
        return psiElement.hashCode();
    }

    public String toString() {
        return "DefaultLightElementOrigin(originalElement=" + this.originalElement + ')';
    }

    public DefaultLightElementOrigin(PsiElement psiElement) {
        this.originalElement = psiElement;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightElementOrigin
    public PsiElement getOriginalElement() {
        return this.originalElement;
    }
}
