package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiRecordHeaderStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiRecordHeaderImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.RecordHeaderElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public class JavaRecordHeaderElementType extends JavaStubElementType<PsiRecordHeaderStub, PsiRecordHeader> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "dataStream";
                break;
            case 3:
            case 5:
            default:
                objArr[0] = "stub";
                break;
            case 4:
                objArr[0] = "sink";
                break;
            case 6:
            case 8:
                objArr[0] = "node";
                break;
            case 7:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 9:
                objArr[0] = "parentStub";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaRecordHeaderElementType";
        switch (i) {
            case 2:
                objArr[2] = "deserialize";
                break;
            case 3:
            case 4:
                objArr[2] = "indexStub";
                break;
            case 5:
            case 6:
                objArr[2] = "createPsi";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "createStub";
                break;
            default:
                objArr[2] = "serialize";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiRecordHeaderStub psiRecordHeaderStub, IndexSink indexSink) {
        if (psiRecordHeaderStub == null) {
            $$$reportNull$$$0(3);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(4);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiRecordHeaderStub psiRecordHeaderStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiRecordHeaderStub == null) {
            $$$reportNull$$$0(0);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(1);
        }
    }

    public JavaRecordHeaderElementType() {
        super("RECORD_HEADER");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new RecordHeaderElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiRecordHeaderStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(2);
        }
        return new PsiRecordHeaderStubImpl(stubElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiRecordHeader createPsi(PsiRecordHeaderStub psiRecordHeaderStub) {
        if (psiRecordHeaderStub == null) {
            $$$reportNull$$$0(5);
        }
        return getPsiFactory(psiRecordHeaderStub).createRecordHeader(psiRecordHeaderStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiRecordHeader createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        return new PsiRecordHeaderImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiRecordHeaderStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(7);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(8);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(9);
        }
        return new PsiRecordHeaderStubImpl(stubElement);
    }
}
