package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import com.beust.jcommander.Parameters;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaStubElementType<StubT extends StubElement<?>, PsiT extends PsiElement> extends ILightStubElementType<StubT, PsiT> implements ICompositeElementType {
    private final boolean myLeftBound;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaStubElementType";
        } else if (i != 3) {
            objArr[0] = "debugName";
        } else {
            objArr[0] = "psi";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaStubElementType";
        } else {
            objArr[1] = "getExternalId";
        }
        if (i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "createStub";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract PsiT createPsi(ASTNode aSTNode);

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected JavaStubElementType(String str) {
        this(str, false);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected JavaStubElementType(String str, boolean z) {
        super(str, JavaLanguage.INSTANCE);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myLeftBound = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public String getExternalId() {
        String str = "java." + toString();
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    protected StubPsiFactory getPsiFactory(StubT stubt) {
        return getFileStub(stubt).getPsiFactory();
    }

    public boolean isCompiled(StubT stubt) {
        return getFileStub(stubt).isCompiled();
    }

    private PsiJavaFileStub getFileStub(StubT stubt) {
        while (!(stubt instanceof PsiFileStub)) {
            stubt = (StubT) stubt.getParentStub();
        }
        return (PsiJavaFileStub) stubt;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public final StubT createStub(PsiT psit, StubElement<? extends PsiElement> stubElement) {
        if (psit == null) {
            $$$reportNull$$$0(3);
        }
        throw new UnsupportedOperationException("Should not be called. Element=" + psit + "; class" + psit.getClass() + "; file=" + (psit.isValid() ? psit.getContainingFile() : Parameters.DEFAULT_OPTION_PREFIXES));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
    public boolean isLeftBound() {
        return this.myLeftBound;
    }
}
