package org.jetbrains.kotlin.com.intellij.lang.jvm;

/* JADX INFO: loaded from: classes3.dex */
public interface JvmTypeParameter extends JvmTypeDeclaration {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/lang/jvm/JvmTypeParameter", "accept"));
    }

    default <T> T accept(JvmElementVisitor<T> jvmElementVisitor) {
        if (jvmElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        return jvmElementVisitor.visitTypeParameter(this);
    }
}
