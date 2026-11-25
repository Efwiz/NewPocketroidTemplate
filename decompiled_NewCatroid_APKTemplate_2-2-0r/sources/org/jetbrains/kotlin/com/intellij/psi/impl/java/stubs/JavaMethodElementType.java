package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.ModifierFlags;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.RecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiMethodStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.index.JavaStubIndexKeys;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiAnnotationMethodImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.AnnotationMethodElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaMethodElementType extends JavaStubElementType<PsiMethodStub, PsiMethod> {
    /* JADX WARN: Code duplicated, block: B:29:0x0047  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 11 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 11 ? 3 : 2];
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
        } else if (i == 9) {
            objArr[0] = "sink";
        } else if (i != 11) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaMethodElementType";
        }
        if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaMethodElementType";
        } else {
            objArr[1] = "getVisibleTypeParameters";
        }
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
            case 10:
                objArr[2] = "getVisibleTypeParameters";
                break;
            case 11:
                break;
            case 12:
                objArr[2] = "isStatic";
                break;
            default:
                objArr[2] = "createPsi";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 11) {
            throw new IllegalStateException(str2);
        }
    }

    JavaMethodElementType(String str) {
        super(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiMethod createPsi(PsiMethodStub psiMethodStub) {
        if (psiMethodStub == null) {
            $$$reportNull$$$0(0);
        }
        return getPsiFactory(psiMethodStub).createMethod(psiMethodStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiMethod createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (aSTNode instanceof AnnotationMethodElement) {
            return new PsiAnnotationMethodImpl(aSTNode);
        }
        return new PsiMethodImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiMethodStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        String filteredString;
        LighterASTNode lighterASTNodeFirstChildOfType;
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(4);
        }
        Iterator<LighterASTNode> it = lighterAST.getChildren(lighterASTNode).iterator();
        String strIntern = null;
        boolean z = true;
        boolean z2 = false;
        boolean z3 = false;
        boolean zIsDeprecatedByDocComment = false;
        boolean zIsDeprecatedByAnnotation = false;
        boolean z4 = false;
        while (true) {
            if (!it.hasNext()) {
                filteredString = null;
                break;
            }
            LighterASTNode next = it.next();
            IElementType tokenType = next.getTokenType();
            if (tokenType == JavaDocElementType.DOC_COMMENT) {
                zIsDeprecatedByDocComment = RecordUtil.isDeprecatedByDocComment(lighterAST, next);
                z4 = true;
            } else if (tokenType == JavaElementType.MODIFIER_LIST) {
                zIsDeprecatedByAnnotation = RecordUtil.isDeprecatedByAnnotation(lighterAST, next);
            } else if (tokenType != JavaElementType.TYPE) {
                if (tokenType == JavaTokenType.IDENTIFIER) {
                    strIntern = RecordUtil.intern(lighterAST.getCharTable(), next);
                } else if (tokenType == JavaElementType.PARAMETER_LIST) {
                    List<LighterASTNode> childrenOfType = LightTreeUtil.getChildrenOfType(lighterAST, next, JavaElementType.PARAMETER);
                    if (!childrenOfType.isEmpty() && (lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, childrenOfType.get(childrenOfType.size() - 1), JavaElementType.TYPE)) != null) {
                        z3 = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNodeFirstChildOfType, JavaTokenType.ELLIPSIS) != null;
                    }
                } else if (tokenType != JavaTokenType.DEFAULT_KEYWORD) {
                    if (z2 && !ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET.contains(tokenType) && tokenType != JavaTokenType.SEMICOLON && tokenType != JavaElementType.CODE_BLOCK) {
                        filteredString = LightTreeUtil.toFilteredString(lighterAST, next, null);
                        break;
                    }
                } else {
                    z2 = true;
                }
            } else {
                z = false;
            }
        }
        return new PsiMethodStubImpl(stubElement, strIntern, z ? TypeInfo.createConstructorType() : TypeInfo.create(lighterAST, lighterASTNode, stubElement), PsiMethodStubImpl.packFlags(z, lighterASTNode.getTokenType() == JavaElementType.ANNOTATION_METHOD, z3, zIsDeprecatedByDocComment, zIsDeprecatedByAnnotation, z4), filteredString);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiMethodStub psiMethodStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiMethodStub == null) {
            $$$reportNull$$$0(5);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(6);
        }
        stubOutputStream.writeName(psiMethodStub.getName());
        TypeInfo.writeTYPE(stubOutputStream, psiMethodStub.getReturnTypeText());
        stubOutputStream.writeByte(((PsiMethodStubImpl) psiMethodStub).getFlags());
        if (psiMethodStub.isAnnotationMethod()) {
            stubOutputStream.writeName(psiMethodStub.getDefaultValueText());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiMethodStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(7);
        }
        String nameString = stubInputStream.readNameString();
        TypeInfo type = TypeInfo.readTYPE(stubInputStream);
        byte b = stubInputStream.readByte();
        return new PsiMethodStubImpl(stubElement, nameString, type, b, PsiMethodStubImpl.isAnnotationMethod(b) ? stubInputStream.readNameString() : null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiMethodStub psiMethodStub, IndexSink indexSink) {
        if (psiMethodStub == null) {
            $$$reportNull$$$0(8);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(9);
        }
        String name2 = psiMethodStub.getName();
        if (name2 != null) {
            indexSink.occurrence(JavaStubIndexKeys.METHODS, name2);
            if (RecordUtil.isStaticNonPrivateMember(psiMethodStub)) {
                indexSink.occurrence(JavaStubIndexKeys.JVM_STATIC_MEMBERS_NAMES, name2);
                indexSink.occurrence(JavaStubIndexKeys.JVM_STATIC_MEMBERS_TYPES, psiMethodStub.getReturnTypeText().getShortTypeText());
            }
        }
        Set<String> visibleTypeParameters = getVisibleTypeParameters(psiMethodStub);
        for (StubElement stubElement : psiMethodStub.getChildrenStubs()) {
            if (stubElement instanceof PsiParameterListStub) {
                for (StubElement stubElement2 : stubElement.getChildrenStubs()) {
                    if (stubElement2 instanceof PsiParameterStub) {
                        String shortClassName = PsiNameHelper.getShortClassName(((PsiParameterStub) stubElement2).getType().text);
                        if (!TypeConversionUtil.isPrimitive(shortClassName) && !TypeConversionUtil.isPrimitiveWrapper(shortClassName) && !visibleTypeParameters.contains(shortClassName)) {
                            indexSink.occurrence(JavaStubIndexKeys.METHOD_TYPES, shortClassName);
                        }
                    }
                }
                return;
            }
        }
    }

    private static Set<String> getVisibleTypeParameters(StubElement<?> stubElement) {
        if (stubElement == null) {
            $$$reportNull$$$0(10);
        }
        Set setEmptySet = null;
        while (stubElement != null) {
            Set<String> ownTypeParameterNames = getOwnTypeParameterNames(stubElement);
            if (!ownTypeParameterNames.isEmpty()) {
                if (setEmptySet == null) {
                    setEmptySet = new HashSet();
                }
                setEmptySet.addAll(ownTypeParameterNames);
            }
            if (isStatic(stubElement)) {
                break;
            }
            stubElement = stubElement.getParentStub();
        }
        if (setEmptySet == null) {
            setEmptySet = Collections.emptySet();
        }
        if (setEmptySet == null) {
            $$$reportNull$$$0(11);
        }
        return setEmptySet;
    }

    private static boolean isStatic(StubElement<?> stubElement) {
        PsiModifierListStub psiModifierListStub;
        if (stubElement == null) {
            $$$reportNull$$$0(12);
        }
        if (!(stubElement instanceof PsiMemberStub) || (psiModifierListStub = (PsiModifierListStub) stubElement.findChildStubByType(JavaStubElementTypes.MODIFIER_LIST)) == null) {
            return false;
        }
        return BitUtil.isSet(psiModifierListStub.getModifiersMask(), ModifierFlags.NAME_TO_MODIFIER_FLAG_MAP.getInt("static"));
    }

    private static Set<String> getOwnTypeParameterNames(StubElement<?> stubElement) {
        StubElement stubElementFindChildStubByType = stubElement.findChildStubByType(JavaStubElementTypes.TYPE_PARAMETER_LIST);
        if (stubElementFindChildStubByType == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = null;
        for (StubElement stubElement2 : stubElementFindChildStubByType.getChildrenStubs()) {
            if (stubElement2 instanceof PsiTypeParameterStub) {
                if (hashSet == null) {
                    hashSet = new HashSet();
                }
                hashSet.add(((PsiTypeParameterStub) stubElement2).getName());
            }
        }
        return hashSet == null ? Collections.emptySet() : hashSet;
    }
}
