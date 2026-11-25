package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import java.util.Iterator;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiFieldStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.index.JavaStubIndexKeys;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiEnumConstantImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFieldImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.EnumConstantElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaFieldStubElementType extends JavaStubElementType<PsiFieldStub, PsiField> {
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
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaFieldStubElementType";
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaFieldStubElementType(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiField createPsi(PsiFieldStub psiFieldStub) {
        if (psiFieldStub == null) {
            $$$reportNull$$$0(1);
        }
        return getPsiFactory(psiFieldStub).createField(psiFieldStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiField createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (aSTNode instanceof EnumConstantElement) {
            return new PsiEnumConstantImpl(aSTNode);
        }
        return new PsiFieldImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiFieldStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        String strEncodeInitializer;
        if (lighterAST == null) {
            $$$reportNull$$$0(3);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(5);
        }
        TypeInfo typeInfoCreate = TypeInfo.create(lighterAST, lighterASTNode, stubElement);
        Iterator<LighterASTNode> it = lighterAST.getChildren(lighterASTNode).iterator();
        String strIntern = null;
        boolean z = false;
        boolean zIsDeprecatedByDocComment = false;
        boolean zIsDeprecatedByAnnotation = false;
        boolean z2 = false;
        while (true) {
            if (!it.hasNext()) {
                strEncodeInitializer = null;
                break;
            }
            LighterASTNode next = it.next();
            IElementType tokenType = next.getTokenType();
            if (tokenType == JavaDocElementType.DOC_COMMENT) {
                zIsDeprecatedByDocComment = RecordUtil.isDeprecatedByDocComment(lighterAST, next);
                z2 = true;
            } else if (tokenType == JavaElementType.MODIFIER_LIST) {
                zIsDeprecatedByAnnotation = RecordUtil.isDeprecatedByAnnotation(lighterAST, next);
            } else if (tokenType == JavaTokenType.IDENTIFIER) {
                strIntern = RecordUtil.intern(lighterAST.getCharTable(), next);
            } else if (tokenType != JavaTokenType.EQ) {
                if (z && !ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET.contains(tokenType) && tokenType != JavaTokenType.SEMICOLON) {
                    strEncodeInitializer = encodeInitializer(lighterAST, next);
                    break;
                }
            } else {
                z = true;
            }
        }
        return new PsiFieldStubImpl(stubElement, strIntern, typeInfoCreate, strEncodeInitializer, PsiFieldStubImpl.packFlags(lighterASTNode.getTokenType() == JavaElementType.ENUM_CONSTANT, zIsDeprecatedByDocComment, zIsDeprecatedByAnnotation, z2));
    }

    private static String encodeInitializer(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        IElementType tokenType = lighterASTNode.getTokenType();
        if (tokenType == JavaElementType.NEW_EXPRESSION || tokenType == JavaElementType.METHOD_CALL_EXPRESSION) {
            return PsiFieldStub.INITIALIZER_NOT_STORED;
        }
        if (lighterASTNode.getEndOffset() - lighterASTNode.getStartOffset() > 1000) {
            return PsiFieldStub.INITIALIZER_TOO_LONG;
        }
        return LightTreeUtil.toFilteredString(lighterAST, lighterASTNode, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiFieldStub psiFieldStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiFieldStub == null) {
            $$$reportNull$$$0(6);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(7);
        }
        stubOutputStream.writeName(psiFieldStub.getName());
        TypeInfo.writeTYPE(stubOutputStream, psiFieldStub.getType());
        stubOutputStream.writeName(psiFieldStub.getInitializerText());
        stubOutputStream.writeByte(((PsiFieldStubImpl) psiFieldStub).getFlags());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiFieldStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(8);
        }
        return new PsiFieldStubImpl(stubElement, stubInputStream.readNameString(), TypeInfo.readTYPE(stubInputStream), stubInputStream.readNameString(), stubInputStream.readByte());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiFieldStub psiFieldStub, IndexSink indexSink) {
        if (psiFieldStub == null) {
            $$$reportNull$$$0(9);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(10);
        }
        String name2 = psiFieldStub.getName();
        if (name2 != null) {
            indexSink.occurrence(JavaStubIndexKeys.FIELDS, name2);
            if (RecordUtil.isStaticNonPrivateMember(psiFieldStub)) {
                indexSink.occurrence(JavaStubIndexKeys.JVM_STATIC_MEMBERS_NAMES, name2);
                indexSink.occurrence(JavaStubIndexKeys.JVM_STATIC_MEMBERS_TYPES, psiFieldStub.getType().getShortTypeText());
            }
        }
    }
}
