package org.jetbrains.kotlin.asJava.builder;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: LightElementOrigin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0001\nR\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/builder/LightElementOrigin;", "", "originKind", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "originalElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getOriginalElement", "()Lcom/intellij/psi/PsiElement;", "None", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LightElementOrigin {
    JvmDeclarationOriginKind getOriginKind();

    PsiElement getOriginalElement();

    /* JADX INFO: compiled from: LightElementOrigin.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/asJava/builder/LightElementOrigin$None;", "Lorg/jetbrains/kotlin/asJava/builder/LightElementOrigin;", "()V", "originKind", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "originalElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getOriginalElement", "()Lcom/intellij/psi/PsiElement;", "toString", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class None implements LightElementOrigin {
        public static final None INSTANCE = new None();

        @Override // org.jetbrains.kotlin.asJava.builder.LightElementOrigin
        public JvmDeclarationOriginKind getOriginKind() {
            return null;
        }

        @Override // org.jetbrains.kotlin.asJava.builder.LightElementOrigin
        public PsiElement getOriginalElement() {
            return null;
        }

        private None() {
        }

        public String toString() {
            return "NONE";
        }
    }
}
