package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import java.util.Collection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiPackageAccessibilityStatementStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PackageAccessibilityStatementElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiPackageAccessibilityStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class JavaPackageAccessibilityStatementElementType extends JavaStubElementType<PsiPackageAccessibilityStatementStub, PsiPackageAccessibilityStatement> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 12 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 12 || i == 13) ? 2 : 3];
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
            case 11:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaPackageAccessibilityStatementElementType";
                break;
            default:
                objArr[0] = "debugName";
                break;
        }
        if (i == 12 || i == 13) {
            objArr[1] = "typeToRole";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaPackageAccessibilityStatementElementType";
        }
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
            case 11:
                objArr[2] = "typeToRole";
                break;
            case 12:
            case 13:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 12 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub, IndexSink indexSink) {
        if (psiPackageAccessibilityStatementStub == null) {
            $$$reportNull$$$0(9);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(10);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaPackageAccessibilityStatementElementType(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiPackageAccessibilityStatement createPsi(PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub) {
        if (psiPackageAccessibilityStatementStub == null) {
            $$$reportNull$$$0(1);
        }
        return getPsiFactory(psiPackageAccessibilityStatementStub).createPackageAccessibilityStatement(psiPackageAccessibilityStatementStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiPackageAccessibilityStatement createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        return new PsiPackageAccessibilityStatementImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
    public ASTNode createCompositeNode() {
        return new PackageAccessibilityStatementElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiPackageAccessibilityStatementStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(3);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(5);
        }
        SmartList smartList = new SmartList();
        String referenceText = null;
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (tokenType == JavaElementType.JAVA_CODE_REFERENCE) {
                referenceText = JavaSourceUtil.getReferenceText(lighterAST, lighterASTNode2);
            } else if (tokenType == JavaElementType.MODULE_REFERENCE) {
                smartList.add(JavaSourceUtil.getReferenceText(lighterAST, lighterASTNode2));
            }
        }
        return new PsiPackageAccessibilityStatementStubImpl(stubElement, this, referenceText, smartList);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiPackageAccessibilityStatementStub == null) {
            $$$reportNull$$$0(6);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(7);
        }
        stubOutputStream.writeName(psiPackageAccessibilityStatementStub.getPackageName());
        stubOutputStream.writeUTFFast(StringUtil.join((Collection<String>) psiPackageAccessibilityStatementStub.getTargets(), "/"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiPackageAccessibilityStatementStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(8);
        }
        return new PsiPackageAccessibilityStatementStubImpl(stubElement, this, stubInputStream.readNameString(), StringUtil.split(stubInputStream.readUTFFast(), "/"));
    }

    public static PsiPackageAccessibilityStatement.Role typeToRole(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(11);
        }
        if (iElementType == JavaElementType.EXPORTS_STATEMENT) {
            PsiPackageAccessibilityStatement.Role role = PsiPackageAccessibilityStatement.Role.EXPORTS;
            if (role == null) {
                $$$reportNull$$$0(12);
            }
            return role;
        }
        if (iElementType != JavaElementType.OPENS_STATEMENT) {
            throw new IllegalArgumentException("Unknown type: " + iElementType);
        }
        PsiPackageAccessibilityStatement.Role role2 = PsiPackageAccessibilityStatement.Role.OPENS;
        if (role2 == null) {
            $$$reportNull$$$0(13);
        }
        return role2;
    }
}
