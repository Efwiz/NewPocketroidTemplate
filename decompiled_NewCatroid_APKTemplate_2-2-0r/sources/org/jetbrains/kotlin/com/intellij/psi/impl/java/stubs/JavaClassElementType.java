package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.index.JavaStubIndexKeys;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiAnonymousClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiEnumConstantInitializerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.AnonymousClassElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.EnumConstantInitializerElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaClassElementType extends JavaStubElementType<PsiClassStub<?>, PsiClass> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
            case 7:
            case 11:
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
            case 6:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaClassElementType";
                break;
            case 8:
            case 9:
                objArr[0] = "dataStream";
                break;
            case 12:
                objArr[0] = "sink";
                break;
            default:
                objArr[0] = "id";
                break;
        }
        if (i == 6) {
            objArr[1] = "typeForClass";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaClassElementType";
        } else {
            objArr[1] = "deserialize";
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
            case 10:
                break;
            case 7:
            case 8:
                objArr[2] = "serialize";
                break;
            case 9:
                objArr[2] = "deserialize";
                break;
            case 11:
            case 12:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JavaClassElementType(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiClass createPsi(PsiClassStub psiClassStub) {
        if (psiClassStub == null) {
            $$$reportNull$$$0(1);
        }
        return getPsiFactory(psiClassStub).createClass(psiClassStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiClass createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (aSTNode instanceof EnumConstantInitializerElement) {
            return new PsiEnumConstantInitializerImpl(aSTNode);
        }
        if (aSTNode instanceof AnonymousClassElement) {
            return new PsiAnonymousClassImpl(aSTNode);
        }
        return new PsiClassImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiClassStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        String name2;
        boolean z;
        boolean z2;
        boolean z3;
        String str;
        boolean z4;
        LighterASTNode parent;
        String qualifiedName;
        if (lighterAST == null) {
            $$$reportNull$$$0(3);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(5);
        }
        String str2 = null;
        if (lighterASTNode.getTokenType() == JavaElementType.ANONYMOUS_CLASS) {
            name2 = null;
            z = true;
            z3 = true;
            z2 = false;
        } else if (lighterASTNode.getTokenType() == JavaElementType.ENUM_CONSTANT_INITIALIZER) {
            name2 = ((PsiClassStub) stubElement.getParentStub()).getName();
            z = true;
            z2 = true;
            z3 = true;
        } else {
            name2 = null;
            z = false;
            z2 = false;
            z3 = false;
        }
        String filteredString = name2;
        String strIntern = null;
        boolean zIsDeprecatedByDocComment = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        boolean zIsDeprecatedByAnnotation = false;
        boolean z8 = false;
        boolean z9 = false;
        for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (tokenType == JavaDocElementType.DOC_COMMENT) {
                zIsDeprecatedByDocComment = RecordUtil.isDeprecatedByDocComment(lighterAST, lighterASTNode2);
                z8 = true;
            } else if (tokenType == JavaElementType.MODIFIER_LIST) {
                zIsDeprecatedByAnnotation = RecordUtil.isDeprecatedByAnnotation(lighterAST, lighterASTNode2);
            } else if (tokenType == JavaTokenType.AT) {
                z7 = true;
            } else if (tokenType == JavaTokenType.INTERFACE_KEYWORD && !z) {
                z = true;
                z5 = true;
            } else if (tokenType == JavaTokenType.ENUM_KEYWORD && !z) {
                z = true;
                z6 = true;
            } else if (tokenType == JavaTokenType.RECORD_KEYWORD && !z) {
                z = true;
                z9 = true;
            } else if (tokenType == JavaTokenType.CLASS_KEYWORD) {
                z = true;
            } else if (!z3 && tokenType == JavaTokenType.IDENTIFIER) {
                strIntern = RecordUtil.intern(lighterAST.getCharTable(), lighterASTNode2);
            } else if (z3 && !z2 && tokenType == JavaElementType.JAVA_CODE_REFERENCE) {
                filteredString = LightTreeUtil.toFilteredString(lighterAST, lighterASTNode2, null);
            }
        }
        if (strIntern == null) {
            str = str2;
        } else if (stubElement instanceof PsiJavaFileStub) {
            String packageName = ((PsiJavaFileStub) stubElement).getPackageName();
            str = !packageName.isEmpty() ? packageName + '.' + strIntern : strIntern;
        } else {
            if ((stubElement instanceof PsiClassStub) && (qualifiedName = ((PsiClassStub) stubElement).getQualifiedName()) != null) {
                str2 = qualifiedName + '.' + strIntern;
            }
            str = str2;
        }
        if (z3 && (parent = lighterAST.getParent(lighterASTNode)) != null && parent.getTokenType() == JavaElementType.NEW_EXPRESSION) {
            z4 = LightTreeUtil.firstChildOfType(lighterAST, parent, JavaTokenType.DOT) != null;
        } else {
            z4 = false;
        }
        return new PsiClassStubImpl(typeForClass(z3, z2), stubElement, str, strIntern, filteredString, PsiClassStubImpl.packFlags(zIsDeprecatedByDocComment, z5, z6, z2, z3, z7, z4, zIsDeprecatedByAnnotation, false, false, z8, z9));
    }

    private static JavaClassElementType typeForClass(boolean z, boolean z2) {
        JavaClassElementType javaClassElementType;
        if (z2) {
            javaClassElementType = JavaStubElementTypes.ENUM_CONSTANT_INITIALIZER;
        } else {
            javaClassElementType = z ? JavaStubElementTypes.ANONYMOUS_CLASS : JavaStubElementTypes.CLASS;
        }
        if (javaClassElementType == null) {
            $$$reportNull$$$0(6);
        }
        return javaClassElementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiClassStub psiClassStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiClassStub == null) {
            $$$reportNull$$$0(7);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(8);
        }
        stubOutputStream.writeShort(((PsiClassStubImpl) psiClassStub).getFlags());
        if (!psiClassStub.isAnonymous()) {
            stubOutputStream.writeName(psiClassStub.getName());
            stubOutputStream.writeName(psiClassStub.getQualifiedName());
            stubOutputStream.writeName(psiClassStub.getSourceFileName());
            return;
        }
        stubOutputStream.writeName(psiClassStub.getBaseClassReferenceText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiClassStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(9);
        }
        short s = stubInputStream.readShort();
        boolean zIsAnonymous = PsiClassStubImpl.isAnonymous(s);
        JavaClassElementType javaClassElementTypeTypeForClass = typeForClass(zIsAnonymous, PsiClassStubImpl.isEnumConstInitializer(s));
        if (!zIsAnonymous) {
            String nameString = stubInputStream.readNameString();
            String nameString2 = stubInputStream.readNameString();
            String nameString3 = stubInputStream.readNameString();
            PsiClassStubImpl psiClassStubImpl = new PsiClassStubImpl(javaClassElementTypeTypeForClass, stubElement, nameString2, nameString, null, s);
            psiClassStubImpl.setSourceFileName(nameString3);
            return psiClassStubImpl;
        }
        return new PsiClassStubImpl(javaClassElementTypeTypeForClass, stubElement, null, null, stubInputStream.readNameString(), s);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiClassStub psiClassStub, IndexSink indexSink) {
        if (psiClassStub == null) {
            $$$reportNull$$$0(11);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(12);
        }
        if (psiClassStub.isAnonymous()) {
            String baseClassReferenceText = psiClassStub.getBaseClassReferenceText();
            if (baseClassReferenceText != null) {
                indexSink.occurrence(JavaStubIndexKeys.ANONYMOUS_BASEREF, PsiNameHelper.getShortClassName(baseClassReferenceText));
                return;
            }
            return;
        }
        String name2 = psiClassStub.getName();
        if (name2 != null && (!(psiClassStub instanceof PsiClassStubImpl) || !((PsiClassStubImpl) psiClassStub).isAnonymousInner())) {
            indexSink.occurrence(JavaStubIndexKeys.CLASS_SHORT_NAMES, name2);
        }
        String qualifiedName = psiClassStub.getQualifiedName();
        if (qualifiedName != null) {
            indexSink.occurrence(JavaStubIndexKeys.CLASS_FQN, Integer.valueOf(qualifiedName.hashCode()));
        }
    }
}
