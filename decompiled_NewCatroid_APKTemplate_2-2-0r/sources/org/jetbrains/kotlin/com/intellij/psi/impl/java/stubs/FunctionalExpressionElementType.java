package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FunctionalExpressionElementType<T extends PsiFunctionalExpression> extends JavaStubElementType<FunctionalExpressionStub<T>, T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "dataStream";
        } else if (i == 4) {
            objArr[0] = "sink";
        } else if (i == 5) {
            objArr[0] = Constants.TYPE_TREE;
        } else if (i == 6) {
            objArr[0] = "funExpr";
        } else if (i != 7) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "parentStub";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/FunctionalExpressionElementType";
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
            case 7:
                objArr[2] = "createStub";
                break;
            default:
                objArr[2] = "serialize";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract String getPresentableText(LighterAST lighterAST, LighterASTNode lighterASTNode);

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(FunctionalExpressionStub<T> functionalExpressionStub, IndexSink indexSink) {
        if (functionalExpressionStub == null) {
            $$$reportNull$$$0(3);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(4);
        }
    }

    public FunctionalExpressionElementType(String str) {
        super(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(FunctionalExpressionStub<T> functionalExpressionStub, StubOutputStream stubOutputStream) throws IOException {
        if (functionalExpressionStub == null) {
            $$$reportNull$$$0(0);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(1);
        }
        stubOutputStream.writeName(functionalExpressionStub.getPresentableText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public FunctionalExpressionStub<T> deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(2);
        }
        return new FunctionalExpressionStub<>(stubElement, this, stubInputStream.readNameString());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public FunctionalExpressionStub<T> createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(5);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(6);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(7);
        }
        return new FunctionalExpressionStub<>(stubElement, this, getPresentableText(lighterAST, lighterASTNode));
    }
}
