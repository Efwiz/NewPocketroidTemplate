package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiLiteralStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public class JavaLiteralExpressionElementType extends JavaStubElementType<PsiLiteralStub, PsiLiteralExpression> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 10:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 2:
            case 11:
            default:
                objArr[0] = "node";
                break;
            case 3:
            case 12:
                objArr[0] = "parentStub";
                break;
            case 4:
            case 5:
            case 8:
                objArr[0] = "stub";
                break;
            case 6:
            case 7:
                objArr[0] = "dataStream";
                break;
            case 9:
                objArr[0] = "sink";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaLiteralExpressionElementType";
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "createStub";
                break;
            case 4:
            default:
                objArr[2] = "createPsi";
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
            case 10:
            case 11:
            case 12:
                objArr[2] = "shouldCreateStub";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiLiteralStub psiLiteralStub, IndexSink indexSink) {
        if (psiLiteralStub == null) {
            $$$reportNull$$$0(8);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(9);
        }
    }

    public JavaLiteralExpressionElementType() {
        super("LITERAL_EXPRESSION");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiLiteralExpression createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        return new PsiLiteralExpressionImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiLiteralStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(1);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(3);
        }
        return new PsiLiteralStub(stubElement, RecordUtil.intern(lighterAST.getCharTable(), lighterAST.getChildren(lighterASTNode).get(0)));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiLiteralExpression createPsi(PsiLiteralStub psiLiteralStub) {
        if (psiLiteralStub == null) {
            $$$reportNull$$$0(4);
        }
        return new PsiLiteralExpressionImpl(psiLiteralStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiLiteralStub psiLiteralStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiLiteralStub == null) {
            $$$reportNull$$$0(5);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(6);
        }
        stubOutputStream.writeUTFFast(psiLiteralStub.getLiteralText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiLiteralStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(7);
        }
        return new PsiLiteralStub(stubElement, stubInputStream.readUTFFast());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new CompositeElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public boolean shouldCreateStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(10);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(11);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(12);
        }
        LighterASTNode parent = lighterAST.getParent(lighterASTNode);
        return parent != null && parent.getTokenType() == JavaStubElementTypes.NAME_VALUE_PAIR;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public boolean shouldCreateStub(ASTNode aSTNode) {
        return aSTNode.getTreeParent().getElementType() == JavaStubElementTypes.NAME_VALUE_PAIR;
    }
}
