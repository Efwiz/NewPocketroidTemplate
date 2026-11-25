package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiModifierListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiModifierListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ModifierListElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes3.dex */
public class JavaModifierListElementType extends JavaStubElementType<PsiModifierListStub, PsiModifierList> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
            case 8:
                objArr[0] = "node";
                break;
            case 2:
            case 7:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 4:
            case 9:
                objArr[0] = "parentStub";
                break;
            case 5:
            case 11:
            default:
                objArr[0] = "stub";
                break;
            case 6:
            case 10:
                objArr[0] = "dataStream";
                break;
            case 12:
                objArr[0] = "sink";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaModifierListElementType";
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
            case 8:
            case 9:
                objArr[2] = "shouldCreateStub";
                break;
            case 10:
                objArr[2] = "deserialize";
                break;
            case 11:
            case 12:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "createPsi";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiModifierListStub psiModifierListStub, IndexSink indexSink) {
        if (psiModifierListStub == null) {
            $$$reportNull$$$0(11);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(12);
        }
    }

    JavaModifierListElementType() {
        super("MODIFIER_LIST");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new ModifierListElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiModifierList createPsi(PsiModifierListStub psiModifierListStub) {
        if (psiModifierListStub == null) {
            $$$reportNull$$$0(0);
        }
        return getPsiFactory(psiModifierListStub).createModifierList(psiModifierListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiModifierList createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiModifierListImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiModifierListStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(4);
        }
        return new PsiModifierListStubImpl(stubElement, RecordUtil.packModifierList(lighterAST, lighterASTNode));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiModifierListStub psiModifierListStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiModifierListStub == null) {
            $$$reportNull$$$0(5);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(6);
        }
        stubOutputStream.writeVarInt(psiModifierListStub.getModifiersMask());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public boolean shouldCreateStub(ASTNode aSTNode) {
        return shouldCreateStub(aSTNode.getTreeParent().getElementType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public boolean shouldCreateStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(7);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(8);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(9);
        }
        LighterASTNode parent = lighterAST.getParent(lighterASTNode);
        return shouldCreateStub(parent != null ? parent.getTokenType() : null);
    }

    private static boolean shouldCreateStub(IElementType iElementType) {
        return iElementType instanceof IStubElementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiModifierListStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(10);
        }
        return new PsiModifierListStubImpl(stubElement, stubInputStream.readVarInt());
    }
}
