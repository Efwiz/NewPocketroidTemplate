package org.jetbrains.kotlin.com.intellij.psi.util;

/* JADX INFO: loaded from: classes3.dex */
public enum ClassKind {
    CLASS(JavaElementKind.CLASS),
    INTERFACE(JavaElementKind.INTERFACE),
    ENUM(JavaElementKind.ENUM),
    ANNOTATION(JavaElementKind.ANNOTATION);

    private final JavaElementKind myElementKind;

    ClassKind(JavaElementKind javaElementKind) {
        this.myElementKind = javaElementKind;
    }

    public JavaElementKind getElementKind() {
        return this.myElementKind;
    }
}
