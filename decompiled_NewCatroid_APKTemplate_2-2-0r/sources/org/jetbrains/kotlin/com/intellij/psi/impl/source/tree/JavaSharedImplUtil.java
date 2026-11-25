package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.GeneratedMarkerVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.AnnotationElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaSharedImplUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaSharedImplUtil.class);
    private static final TokenSet BRACKETS = TokenSet.create(JavaTokenType.LBRACKET, JavaTokenType.RBRACKET);

    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 7 && i != 15) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 7 && i != 15) {
            switch (i) {
                case 9:
                case 10:
                case 11:
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
            case 3:
            case 4:
                objArr[0] = "anchor";
                break;
            case 2:
            default:
                objArr[0] = "typeElement";
                break;
            case 5:
                objArr[0] = "owner";
                break;
            case 6:
                objArr[0] = "typeInfo";
                break;
            case 7:
            case 9:
            case 10:
            case 11:
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaSharedImplUtil";
                break;
            case 8:
            case 12:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 13:
                objArr[0] = "modifierList";
                break;
            case 14:
                objArr[0] = "annotations";
                break;
            case 16:
            case 17:
                objArr[0] = "variable";
                break;
        }
        if (i == 7) {
            objArr[1] = "createTypeFromStub";
        } else if (i != 15) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                    objArr[1] = "applyAnnotations";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaSharedImplUtil";
                    break;
            }
        } else {
            objArr[1] = "annotate";
        }
        switch (i) {
            case 4:
                objArr[2] = "collectAnnotations";
                break;
            case 5:
            case 6:
                objArr[2] = "createTypeFromStub";
                break;
            case 7:
            case 9:
            case 10:
            case 11:
            case 15:
                break;
            case 8:
                objArr[2] = "applyAnnotations";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "annotate";
                break;
            case 16:
                objArr[2] = "normalizeBrackets";
                break;
            case 17:
                objArr[2] = "setInitializer";
                break;
            default:
                objArr[2] = "getType";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 15) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private JavaSharedImplUtil() {
    }

    public static PsiType getType(PsiTypeElement psiTypeElement, PsiElement psiElement) {
        if (psiTypeElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return getType(psiTypeElement, psiElement, null);
    }

    public static PsiType getType(PsiTypeElement psiTypeElement, PsiElement psiElement, PsiAnnotation psiAnnotation) {
        if (psiTypeElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        PsiType type = psiTypeElement.getType();
        List<PsiAnnotation[]> listCollectAnnotations = collectAnnotations(psiElement, psiAnnotation);
        if (listCollectAnnotations == null) {
            return null;
        }
        Iterator<PsiAnnotation[]> it = listCollectAnnotations.iterator();
        while (it.hasNext()) {
            type = type.createArrayType().annotate(TypeAnnotationProvider.Static.create(it.next()));
        }
        return type;
    }

    private static List<PsiAnnotation[]> collectAnnotations(PsiElement psiElement, PsiAnnotation psiAnnotation) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        SmartList smartList = new SmartList();
        boolean z = psiAnnotation == null;
        boolean z2 = false;
        SmartList smartList2 = null;
        for (PsiElement nextSibling = psiElement.getNextSibling(); nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
            if (!(nextSibling instanceof PsiComment) && !(nextSibling instanceof PsiWhiteSpace)) {
                if (nextSibling instanceof PsiAnnotation) {
                    if (smartList2 == null) {
                        smartList2 = new SmartList();
                    }
                    smartList2.add((PsiAnnotation) nextSibling);
                    if (nextSibling == psiAnnotation) {
                        z = true;
                        z2 = true;
                    }
                } else if (PsiUtil.isJavaToken(nextSibling, JavaTokenType.LBRACKET)) {
                    smartList.add(0, smartList2 == null ? PsiAnnotation.EMPTY_ARRAY : (PsiAnnotation[]) ContainerUtil.toArray(smartList2, PsiAnnotation.ARRAY_FACTORY));
                    if (z2) {
                        return smartList;
                    }
                    smartList2 = null;
                } else if (!PsiUtil.isJavaToken(nextSibling, JavaTokenType.RBRACKET)) {
                    break;
                }
            }
        }
        if (!z || z2) {
            return null;
        }
        return smartList;
    }

    public static PsiType createTypeFromStub(PsiModifierListOwner psiModifierListOwner, TypeInfo typeInfo) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(5);
        }
        if (typeInfo == null) {
            $$$reportNull$$$0(6);
        }
        PsiType psiTypeApplyTo = typeInfo.getTypeAnnotations().applyTo(applyAnnotations(JavaPsiFacade.getInstance(psiModifierListOwner.getProject()).getParserFacade().createTypeFromText(TypeInfo.createTypeText(typeInfo), psiModifierListOwner), psiModifierListOwner.mo6095getModifierList()), psiModifierListOwner);
        if (psiTypeApplyTo == null) {
            $$$reportNull$$$0(7);
        }
        return psiTypeApplyTo;
    }

    public static PsiType applyAnnotations(PsiType psiType, PsiModifierList psiModifierList) {
        if (psiType == null) {
            $$$reportNull$$$0(8);
        }
        if (psiModifierList != null) {
            PsiAnnotation[] annotations = psiModifierList.getAnnotations();
            if (annotations.length > 0) {
                if (psiType instanceof PsiArrayType) {
                    Stack stack = new Stack();
                    do {
                        PsiArrayType psiArrayType = (PsiArrayType) psiType;
                        stack.push(psiArrayType);
                        psiType = psiArrayType.getComponentType();
                    } while (psiType instanceof PsiArrayType);
                    PsiType psiTypeAnnotate = annotate(psiType, psiModifierList, annotations);
                    while (!stack.isEmpty()) {
                        PsiArrayType psiArrayType2 = (PsiArrayType) stack.pop();
                        psiTypeAnnotate = psiArrayType2 instanceof PsiEllipsisType ? new PsiEllipsisType(psiTypeAnnotate, psiArrayType2.getAnnotations()) : new PsiArrayType(psiTypeAnnotate, psiArrayType2.getAnnotations());
                    }
                    if (psiTypeAnnotate == null) {
                        $$$reportNull$$$0(9);
                    }
                    return psiTypeAnnotate;
                }
                if (psiType instanceof PsiDisjunctionType) {
                    PsiDisjunctionType psiDisjunctionType = (PsiDisjunctionType) psiType;
                    ArrayList arrayList = new ArrayList(psiDisjunctionType.getDisjunctions());
                    arrayList.set(0, annotate((PsiType) arrayList.get(0), psiModifierList, annotations));
                    PsiDisjunctionType psiDisjunctionTypeNewDisjunctionType = psiDisjunctionType.newDisjunctionType(arrayList);
                    if (psiDisjunctionTypeNewDisjunctionType == null) {
                        $$$reportNull$$$0(10);
                    }
                    return psiDisjunctionTypeNewDisjunctionType;
                }
                return annotate(psiType, psiModifierList, annotations);
            }
        }
        if (psiType == null) {
            $$$reportNull$$$0(11);
        }
        return psiType;
    }

    private static PsiType annotate(PsiType psiType, PsiModifierList psiModifierList, PsiAnnotation[] psiAnnotationArr) {
        if (psiType == null) {
            $$$reportNull$$$0(12);
        }
        if (psiModifierList == null) {
            $$$reportNull$$$0(13);
        }
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(14);
        }
        PsiType psiTypeAnnotate = psiType.annotate(new FilteringTypeAnnotationProvider(psiAnnotationArr, psiModifierList.getMyParent() instanceof PsiMethod ? psiType.getAnnotationProvider() : TypeAnnotationProvider.EMPTY));
        if (psiTypeAnnotate == null) {
            $$$reportNull$$$0(15);
        }
        return psiTypeAnnotate;
    }

    public static void normalizeBrackets(PsiVariable psiVariable) {
        if (psiVariable == null) {
            $$$reportNull$$$0(16);
        }
        CompositeElement compositeElement = (CompositeElement) psiVariable.getNode();
        PsiTypeElement typeElement = psiVariable.getTypeElement();
        PsiIdentifier psiIdentifierMo7392getNameIdentifier = psiVariable.mo7392getNameIdentifier();
        LOG.assertTrue((typeElement == null || psiIdentifierMo7392getNameIdentifier == null) ? false : true);
        ASTNode node = typeElement.getNode();
        ASTNode node2 = psiIdentifierMo7392getNameIdentifier.getNode();
        MultiMap multiMap = new MultiMap();
        ASTNode aSTNode = null;
        ASTNode aSTNodeSkipWhitespaceAndComments = node2;
        int i = 0;
        ASTNode aSTNodeSkipWhitespaceAndComments2 = null;
        while (aSTNodeSkipWhitespaceAndComments != null) {
            aSTNodeSkipWhitespaceAndComments = PsiImplUtil.skipWhitespaceAndComments(aSTNodeSkipWhitespaceAndComments.getTreeNext());
            if (aSTNodeSkipWhitespaceAndComments instanceof AnnotationElement) {
                multiMap.putValue(Integer.valueOf(i), (AnnotationElement) aSTNodeSkipWhitespaceAndComments);
            } else {
                if (aSTNodeSkipWhitespaceAndComments == null || aSTNodeSkipWhitespaceAndComments.getElementType() != JavaTokenType.LBRACKET) {
                    break;
                }
                if (aSTNode == null) {
                    aSTNode = aSTNodeSkipWhitespaceAndComments;
                }
                i++;
                aSTNodeSkipWhitespaceAndComments2 = PsiImplUtil.skipWhitespaceAndComments(aSTNodeSkipWhitespaceAndComments.getTreeNext());
                if (aSTNodeSkipWhitespaceAndComments2 == null || aSTNodeSkipWhitespaceAndComments2.getElementType() != JavaTokenType.RBRACKET) {
                    aSTNodeSkipWhitespaceAndComments2 = aSTNodeSkipWhitespaceAndComments;
                    break;
                }
                aSTNodeSkipWhitespaceAndComments = aSTNodeSkipWhitespaceAndComments2;
            }
        }
        if (aSTNode != null) {
            ASTNode aSTNodeSkipWhitespaceAndComments3 = PsiImplUtil.skipWhitespaceAndComments(node2.getTreeNext());
            while (aSTNodeSkipWhitespaceAndComments3 != null) {
                ASTNode aSTNodeSkipWhitespaceAndComments4 = PsiImplUtil.skipWhitespaceAndComments(aSTNodeSkipWhitespaceAndComments3.getTreeNext());
                CodeEditUtil.removeChild(compositeElement, aSTNodeSkipWhitespaceAndComments3);
                if (aSTNodeSkipWhitespaceAndComments3 == aSTNodeSkipWhitespaceAndComments2) {
                    break;
                } else {
                    aSTNodeSkipWhitespaceAndComments3 = aSTNodeSkipWhitespaceAndComments4;
                }
            }
            final CompositeElement compositeElement2 = (CompositeElement) node.clone();
            if (!(typeElement.getType() instanceof PsiArrayType)) {
                CompositeElement compositeElementComposite = ASTFactory.composite(JavaElementType.TYPE);
                compositeElementComposite.rawAddChildren(compositeElement2);
                compositeElement2 = compositeElementComposite;
            }
            for (int i2 = 0; i2 < i; i2++) {
                Collection collection = multiMap.get(Integer.valueOf(i2));
                Objects.requireNonNull(compositeElement2);
                collection.forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        compositeElement2.rawAddChildren((AnnotationElement) obj);
                    }
                });
                compositeElement2.rawAddChildren(ASTFactory.leaf(JavaTokenType.LBRACKET, "["));
                compositeElement2.rawAddChildren(ASTFactory.leaf(JavaTokenType.RBRACKET, "]"));
            }
            compositeElement2.acceptTree(new GeneratedMarkerVisitor());
            compositeElement2.putUserData(CharTable.CHAR_TABLE_KEY, SharedImplUtil.findCharTableByTree(node));
            CodeEditUtil.replaceChild(compositeElement, node, compositeElement2);
        }
    }

    public static void setInitializer(PsiVariable psiVariable, PsiExpression psiExpression) throws IncorrectOperationException {
        if (psiVariable == null) {
            $$$reportNull$$$0(17);
        }
        PsiExpression initializer = psiVariable.getInitializer();
        if (initializer != null) {
            initializer.delete();
        }
        if (psiExpression == null) {
            return;
        }
        CompositeElement compositeElement = (CompositeElement) psiVariable.getNode();
        ASTNode aSTNodeFindChildByRole = compositeElement.findChildByRole(20);
        if (aSTNodeFindChildByRole == null) {
            LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.EQ, ContainerUtils.KEY_VALUE_DELIMITER, 0, 1, SharedImplUtil.findCharTableByTree(compositeElement), psiVariable.getManager());
            compositeElement.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, PsiImplUtil.skipWhitespaceCommentsAndTokens(psiVariable.mo7392getNameIdentifier().getNode().getTreeNext(), BRACKETS), Boolean.TRUE);
            aSTNodeFindChildByRole = compositeElement.findChildByRole(20);
        }
        psiVariable.addAfter(psiExpression, aSTNodeFindChildByRole.getPsi());
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FilteringTypeAnnotationProvider implements TypeAnnotationProvider {
        private volatile PsiAnnotation[] myCache;
        private final PsiAnnotation[] myCandidates;
        private final TypeAnnotationProvider myOriginalProvider;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "candidates";
            } else if (i != 2) {
                objArr[0] = "originalProvider";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaSharedImplUtil$FilteringTypeAnnotationProvider";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaSharedImplUtil$FilteringTypeAnnotationProvider";
            } else {
                objArr[1] = "getAnnotations";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        private FilteringTypeAnnotationProvider(PsiAnnotation[] psiAnnotationArr, TypeAnnotationProvider typeAnnotationProvider) {
            if (typeAnnotationProvider == null) {
                $$$reportNull$$$0(0);
            }
            if (psiAnnotationArr == null) {
                $$$reportNull$$$0(1);
            }
            this.myCandidates = psiAnnotationArr;
            this.myOriginalProvider = typeAnnotationProvider;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
        public PsiAnnotation[] getAnnotations() {
            PsiAnnotation[] psiAnnotationArr = this.myCache;
            if (psiAnnotationArr == null) {
                List list = JBIterable.of((Object[]) this.myCandidates).filter(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil$FilteringTypeAnnotationProvider$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return JavaSharedImplUtil.FilteringTypeAnnotationProvider.lambda$getAnnotations$0((PsiAnnotation) obj);
                    }
                }).append((Object[]) this.myOriginalProvider.getAnnotations()).toList();
                psiAnnotationArr = list.isEmpty() ? PsiAnnotation.EMPTY_ARRAY : (PsiAnnotation[]) list.toArray(PsiAnnotation.EMPTY_ARRAY);
                this.myCache = psiAnnotationArr;
            }
            if (psiAnnotationArr == null) {
                $$$reportNull$$$0(2);
            }
            return psiAnnotationArr;
        }

        static /* synthetic */ boolean lambda$getAnnotations$0(PsiAnnotation psiAnnotation) {
            return !psiAnnotation.isValid() || AnnotationTargetUtil.isTypeAnnotation(psiAnnotation);
        }
    }
}
