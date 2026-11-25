package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ILightStubElementType<StubT extends StubElement<?>, PsiT extends PsiElement> extends IStubElementType<StubT, PsiT> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = Constants.TYPE_TREE;
        } else if (i == 2) {
            objArr[0] = "node";
        } else if (i != 3) {
            objArr[0] = "debugName";
        } else {
            objArr[0] = "parentStub";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/ILightStubElementType";
        if (i == 1 || i == 2 || i == 3) {
            objArr[2] = "shouldCreateStub";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract StubT createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement<?> stubElement);

    public boolean shouldCreateStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement<?> stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(1);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(3);
        }
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ILightStubElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }
}
