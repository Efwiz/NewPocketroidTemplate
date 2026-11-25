package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubSerializer;

/* JADX INFO: loaded from: classes3.dex */
public abstract class StubFileElementType<T extends PsiFileStub> extends IFileElementType implements StubSerializer<T> {
    public static final String DEFAULT_EXTERNAL_ID = "psi.file";

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/com/intellij/psi/tree/StubFileElementType", "<init>"));
    }

    public StubFileElementType(Language language) {
        super(language);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StubFileElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }
}
