package org.jetbrains.kotlin.com.intellij.openapi.editor;

/* JADX INFO: loaded from: classes3.dex */
public final class ReadOnlyModificationException extends RuntimeException {
    private final Document myDocument;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = "document";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/ReadOnlyModificationException";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/ReadOnlyModificationException";
        } else {
            objArr[1] = "getDocument";
        }
        if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadOnlyModificationException(Document document, String str) {
        super(str);
        if (document == null) {
            $$$reportNull$$$0(1);
        }
        this.myDocument = document;
    }
}
