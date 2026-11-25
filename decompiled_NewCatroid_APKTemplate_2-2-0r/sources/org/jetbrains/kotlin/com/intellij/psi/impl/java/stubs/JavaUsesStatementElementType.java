package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiUsesStatementStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiUsesStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public class JavaUsesStatementElementType extends JavaStubElementType<PsiUsesStatementStub, PsiUsesStatement> {
    /* JADX WARN: Code duplicated, block: B:19:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2) {
            objArr[0] = Constants.TYPE_TREE;
        } else if (i == 3) {
            objArr[0] = "node";
        } else if (i == 4) {
            objArr[0] = "parentStub";
        } else if (i == 6 || i == 7) {
            objArr[0] = "dataStream";
        } else if (i != 9) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "sink";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaUsesStatementElementType";
        switch (i) {
            case 2:
            case 3:
            case 4:
                objArr[2] = "createStub";
                break;
            case 5:
            case 6:
                objArr[2] = "serialize";
                break;
            case 7:
                objArr[2] = "deserialize";
                break;
            case 8:
            case 9:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "createPsi";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiUsesStatementStub psiUsesStatementStub, IndexSink indexSink) {
        if (psiUsesStatementStub == null) {
            $$$reportNull$$$0(8);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(9);
        }
    }

    public JavaUsesStatementElementType() {
        super("USES_STATEMENT");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new CompositeElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiUsesStatement createPsi(PsiUsesStatementStub psiUsesStatementStub) {
        if (psiUsesStatementStub == null) {
            $$$reportNull$$$0(0);
        }
        return getPsiFactory(psiUsesStatementStub).createUsesStatement(psiUsesStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiUsesStatement createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiUsesStatementImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiUsesStatementStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(4);
        }
        LighterASTNode lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNode, JavaElementType.JAVA_CODE_REFERENCE);
        return new PsiUsesStatementStubImpl(stubElement, lighterASTNodeFirstChildOfType != null ? JavaSourceUtil.getReferenceText(lighterAST, lighterASTNodeFirstChildOfType) : null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiUsesStatementStub psiUsesStatementStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiUsesStatementStub == null) {
            $$$reportNull$$$0(5);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(6);
        }
        stubOutputStream.writeName(psiUsesStatementStub.getClassName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiUsesStatementStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(7);
        }
        return new PsiUsesStatementStubImpl(stubElement, stubInputStream.readNameString());
    }
}
