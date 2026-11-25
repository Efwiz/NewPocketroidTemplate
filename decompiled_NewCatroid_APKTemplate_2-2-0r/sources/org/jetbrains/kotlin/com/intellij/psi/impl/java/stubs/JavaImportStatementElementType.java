package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiImportStatementStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportStaticStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ImportStaticStatementElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaImportStatementElementType extends JavaStubElementType<PsiImportStatementStub, PsiImportStatementBase> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 6:
            case 9:
                objArr[0] = "stub";
                break;
            case 2:
            case 4:
                objArr[0] = "node";
                break;
            case 3:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 5:
                objArr[0] = "parentStub";
                break;
            case 7:
            case 8:
                objArr[0] = "dataStream";
                break;
            case 10:
                objArr[0] = "sink";
                break;
            default:
                objArr[0] = "id";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaImportStatementElementType";
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "createPsi";
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "createStub";
                break;
            case 6:
            case 7:
                objArr[2] = "serialize";
                break;
            case 8:
                objArr[2] = "deserialize";
                break;
            case 9:
            case 10:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiImportStatementStub psiImportStatementStub, IndexSink indexSink) {
        if (psiImportStatementStub == null) {
            $$$reportNull$$$0(9);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(10);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaImportStatementElementType(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiImportStatementBase createPsi(PsiImportStatementStub psiImportStatementStub) {
        if (psiImportStatementStub == null) {
            $$$reportNull$$$0(1);
        }
        return getPsiFactory(psiImportStatementStub).createImportStatement(psiImportStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiImportStatementBase createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (aSTNode instanceof ImportStaticStatementElement) {
            return new PsiImportStaticStatementImpl(aSTNode);
        }
        return new PsiImportStatementImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiImportStatementStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(3);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(5);
        }
        String referenceText = null;
        boolean z = false;
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (tokenType == JavaElementType.JAVA_CODE_REFERENCE || tokenType == JavaElementType.IMPORT_STATIC_REFERENCE) {
                referenceText = JavaSourceUtil.getReferenceText(lighterAST, lighterASTNode2);
            } else if (tokenType == JavaTokenType.DOT) {
                z = true;
            }
        }
        return new PsiImportStatementStubImpl(stubElement, referenceText, PsiImportStatementStubImpl.packFlags(z, lighterASTNode.getTokenType() == JavaElementType.IMPORT_STATIC_STATEMENT));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiImportStatementStub psiImportStatementStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiImportStatementStub == null) {
            $$$reportNull$$$0(6);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(7);
        }
        stubOutputStream.writeByte(((PsiImportStatementStubImpl) psiImportStatementStub).getFlags());
        stubOutputStream.writeName(psiImportStatementStub.getImportReferenceText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiImportStatementStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(8);
        }
        return new PsiImportStatementStubImpl(stubElement, stubInputStream.readNameString(), stubInputStream.readByte());
    }
}
