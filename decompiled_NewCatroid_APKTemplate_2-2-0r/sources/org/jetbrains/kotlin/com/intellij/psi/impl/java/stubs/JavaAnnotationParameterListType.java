package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiAnnotationParameterListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.AnnotationParamListElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiAnnotationParamListImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public class JavaAnnotationParameterListType extends JavaStubElementType<PsiAnnotationParameterListStub, PsiAnnotationParameterList> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 2:
            default:
                objArr[0] = "node";
                break;
            case 3:
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
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaAnnotationParameterListType";
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
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiAnnotationParameterListStub psiAnnotationParameterListStub, IndexSink indexSink) {
        if (psiAnnotationParameterListStub == null) {
            $$$reportNull$$$0(8);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(9);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiAnnotationParameterListStub psiAnnotationParameterListStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiAnnotationParameterListStub == null) {
            $$$reportNull$$$0(5);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(6);
        }
    }

    protected JavaAnnotationParameterListType() {
        super("ANNOTATION_PARAMETER_LIST", true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiAnnotationParameterList createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        return new PsiAnnotationParamListImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new AnnotationParamListElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiAnnotationParameterListStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(1);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(3);
        }
        return new PsiAnnotationParameterListStubImpl(stubElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiAnnotationParameterList createPsi(PsiAnnotationParameterListStub psiAnnotationParameterListStub) {
        if (psiAnnotationParameterListStub == null) {
            $$$reportNull$$$0(4);
        }
        return getPsiFactory(psiAnnotationParameterListStub).createAnnotationParameterList(psiAnnotationParameterListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiAnnotationParameterListStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(7);
        }
        return new PsiAnnotationParameterListStubImpl(stubElement);
    }
}
