package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.Collections;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class IStubElementType<StubT extends StubElement<?>, PsiT extends PsiElement> extends IElementType implements StubSerializer<StubT> {
    private static volatile boolean ourInitializedStubs;
    private static volatile Set<String> ourLazyExternalIds = Collections.emptySet();
    private static final Logger LOG = Logger.getInstance((Class<?>) IStubElementType.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "aClass";
        } else if (i != 2) {
            objArr[0] = "debugName";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/IStubElementType";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/IStubElementType";
        } else {
            objArr[1] = "loadRegisteredStubElementTypes";
        }
        if (i == 1) {
            objArr[2] = "checkNotInstantiatedTooLate";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract PsiT createPsi(StubT stubt);

    public abstract StubT createStub(PsiT psit, StubElement<? extends PsiElement> stubElement);

    public boolean shouldCreateStub(ASTNode aSTNode) {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IStubElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (isLazilyRegistered()) {
            return;
        }
        checkNotInstantiatedTooLate(getClass());
    }

    public static void checkNotInstantiatedTooLate(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        if (ourInitializedStubs) {
            LOG.error("All stub element types should be created before index initialization is complete.\nPlease add the " + cls + " containing stub element type constants to \"stubElementTypeHolder\" extension.\nRegistered extensions: " + StubElementTypeHolderEP.EP_NAME.getExtensionList());
        }
    }

    private boolean isLazilyRegistered() {
        try {
            return ourLazyExternalIds.contains(getExternalId());
        } catch (Throwable unused) {
            return false;
        }
    }
}
