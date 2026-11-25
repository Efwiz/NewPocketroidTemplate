package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.IOException;
import java.util.List;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassReferenceListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.index.JavaStubIndexKeys;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiReferenceListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaClassReferenceListElementType extends JavaStubElementType<PsiClassReferenceListStub, PsiReferenceList> {
    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    /* JADX WARN: Code duplicated, block: B:6:0x000a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 8) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 8) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 9:
            case 12:
                objArr[0] = "stub";
                break;
            case 2:
            case 4:
            case 7:
                objArr[0] = "node";
                break;
            case 3:
            case 6:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 5:
                objArr[0] = "parentStub";
                break;
            case 8:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaClassReferenceListElementType";
                break;
            case 10:
            case 11:
                objArr[0] = "dataStream";
                break;
            case 13:
                objArr[0] = "sink";
                break;
            case 14:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            default:
                objArr[0] = "id";
                break;
        }
        if (i != 8) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    objArr[1] = "elementTypeToRole";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaClassReferenceListElementType";
                    break;
            }
        } else {
            objArr[1] = "getTexts";
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
                objArr[2] = "getTexts";
                break;
            case 8:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                break;
            case 9:
            case 10:
                objArr[2] = "serialize";
                break;
            case 11:
                objArr[2] = "deserialize";
                break;
            case 12:
            case 13:
                objArr[2] = "indexStub";
                break;
            case 14:
                objArr[2] = "elementTypeToRole";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaClassReferenceListElementType(String str) {
        super(str, true);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiReferenceList createPsi(PsiClassReferenceListStub psiClassReferenceListStub) {
        if (psiClassReferenceListStub == null) {
            $$$reportNull$$$0(1);
        }
        return getPsiFactory(psiClassReferenceListStub).createClassReferenceList(psiClassReferenceListStub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType
    public PsiReferenceList createPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        return new PsiReferenceListImpl(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ILightStubElementType
    public PsiClassReferenceListStub createStub(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement stubElement) {
        if (lighterAST == null) {
            $$$reportNull$$$0(3);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (stubElement == null) {
            $$$reportNull$$$0(5);
        }
        return new PsiClassReferenceListStubImpl((JavaClassReferenceListElementType) lighterASTNode.getTokenType(), stubElement, getTexts(lighterAST, lighterASTNode));
    }

    private static String[] getTexts(LighterAST lighterAST, LighterASTNode lighterASTNode) {
        if (lighterAST == null) {
            $$$reportNull$$$0(6);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(7);
        }
        List<LighterASTNode> childrenOfType = LightTreeUtil.getChildrenOfType(lighterAST, lighterASTNode, JavaElementType.JAVA_CODE_REFERENCE);
        String[] strArrNewStringArray = ArrayUtil.newStringArray(childrenOfType.size());
        for (int i = 0; i < childrenOfType.size(); i++) {
            strArrNewStringArray[i] = LightTreeUtil.toFilteredString(lighterAST, childrenOfType.get(i), null);
        }
        if (strArrNewStringArray == null) {
            $$$reportNull$$$0(8);
        }
        return strArrNewStringArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(PsiClassReferenceListStub psiClassReferenceListStub, StubOutputStream stubOutputStream) throws IOException {
        if (psiClassReferenceListStub == null) {
            $$$reportNull$$$0(9);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(10);
        }
        TypeInfo[] types = psiClassReferenceListStub.getTypes();
        boolean zExists = ContainerUtil.exists(types, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaClassReferenceListElementType$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return JavaClassReferenceListElementType.lambda$serialize$0((TypeInfo) obj);
            }
        });
        int length = types.length;
        if (zExists) {
            length = -length;
        }
        stubOutputStream.writeVarInt(length);
        for (TypeInfo typeInfo : types) {
            stubOutputStream.writeName(typeInfo.text);
        }
        if (zExists) {
            for (TypeInfo typeInfo2 : types) {
                TypeAnnotationContainer.writeTypeAnnotations(stubOutputStream, typeInfo2.getTypeAnnotations());
            }
        }
    }

    static /* synthetic */ boolean lambda$serialize$0(TypeInfo typeInfo) {
        return !typeInfo.getTypeAnnotations().isEmpty();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public PsiClassReferenceListStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(11);
        }
        int varInt = stubInputStream.readVarInt();
        if (varInt == 0) {
            return new PsiClassReferenceListStubImpl(this, stubElement, TypeInfo.EMPTY_ARRAY);
        }
        boolean z = varInt < 0;
        int iAbs = Math.abs(varInt);
        TypeInfo[] typeInfoArr = new TypeInfo[iAbs];
        for (int i = 0; i < iAbs; i++) {
            typeInfoArr[i] = new TypeInfo(stubInputStream.readNameString());
        }
        if (z) {
            for (int i2 = 0; i2 < iAbs; i2++) {
                typeInfoArr[i2].setTypeAnnotations(TypeAnnotationContainer.readTypeAnnotations(stubInputStream));
            }
        }
        return new PsiClassReferenceListStubImpl(this, stubElement, typeInfoArr);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiClassReferenceListStub psiClassReferenceListStub, IndexSink indexSink) {
        if (psiClassReferenceListStub == null) {
            $$$reportNull$$$0(12);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(13);
        }
        PsiReferenceList.Role role = psiClassReferenceListStub.getRole();
        if (role == PsiReferenceList.Role.EXTENDS_LIST || role == PsiReferenceList.Role.IMPLEMENTS_LIST) {
            for (String str : psiClassReferenceListStub.getReferencedNames()) {
                String shortClassName = PsiNameHelper.getShortClassName(str);
                if (!StringUtil.isEmptyOrSpaces(shortClassName)) {
                    indexSink.occurrence(JavaStubIndexKeys.SUPER_CLASSES, shortClassName);
                }
            }
            if (role == PsiReferenceList.Role.EXTENDS_LIST) {
                StubElement parentStub = psiClassReferenceListStub.getParentStub();
                if (parentStub instanceof PsiClassStub) {
                    PsiClassStub psiClassStub = (PsiClassStub) parentStub;
                    if (psiClassStub.isEnum()) {
                        indexSink.occurrence(JavaStubIndexKeys.SUPER_CLASSES, "Enum");
                    }
                    if (psiClassStub.isAnnotationType()) {
                        indexSink.occurrence(JavaStubIndexKeys.SUPER_CLASSES, "Annotation");
                    }
                }
            }
        }
    }

    public static PsiReferenceList.Role elementTypeToRole(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(14);
        }
        if (iElementType == JavaStubElementTypes.EXTENDS_BOUND_LIST) {
            PsiReferenceList.Role role = PsiReferenceList.Role.EXTENDS_BOUNDS_LIST;
            if (role == null) {
                $$$reportNull$$$0(15);
            }
            return role;
        }
        if (iElementType == JavaStubElementTypes.EXTENDS_LIST) {
            PsiReferenceList.Role role2 = PsiReferenceList.Role.EXTENDS_LIST;
            if (role2 == null) {
                $$$reportNull$$$0(16);
            }
            return role2;
        }
        if (iElementType == JavaStubElementTypes.IMPLEMENTS_LIST) {
            PsiReferenceList.Role role3 = PsiReferenceList.Role.IMPLEMENTS_LIST;
            if (role3 == null) {
                $$$reportNull$$$0(17);
            }
            return role3;
        }
        if (iElementType == JavaStubElementTypes.THROWS_LIST) {
            PsiReferenceList.Role role4 = PsiReferenceList.Role.THROWS_LIST;
            if (role4 == null) {
                $$$reportNull$$$0(18);
            }
            return role4;
        }
        if (iElementType == JavaStubElementTypes.PROVIDES_WITH_LIST) {
            PsiReferenceList.Role role5 = PsiReferenceList.Role.PROVIDES_WITH_LIST;
            if (role5 == null) {
                $$$reportNull$$$0(19);
            }
            return role5;
        }
        if (iElementType != JavaStubElementTypes.PERMITS_LIST) {
            throw new RuntimeException("Unknown element type: " + iElementType);
        }
        PsiReferenceList.Role role6 = PsiReferenceList.Role.PERMITS_LIST;
        if (role6 == null) {
            $$$reportNull$$$0(20);
        }
        return role6;
    }
}
