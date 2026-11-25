package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.Arrays;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.PsiPolyVariantCachingReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiNewExpressionImpl extends ExpressionPsiElement implements PsiNewExpression {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiNewExpressionImpl.class);
    private static final TokenSet CLASS_REF = TokenSet.create(JavaElementType.JAVA_CODE_REFERENCE, JavaElementType.ANONYMOUS_CLASS);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNewExpressionImpl";
                break;
            case 6:
            case 7:
                objArr[0] = "child";
                break;
            case 8:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "annotation";
                break;
        }
        if (i == 1 || i == 2) {
            objArr[1] = "getArrayDimensions";
        } else if (i == 3) {
            objArr[1] = "resolveMethodGenerics";
        } else if (i == 4) {
            objArr[1] = "getTypeArgumentList";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNewExpressionImpl";
        } else {
            objArr[1] = "getTypeArguments";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                break;
            case 6:
                objArr[2] = "deleteChildInternal";
                break;
            case 7:
                objArr[2] = "getChildRole";
                break;
            case 8:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "getOwner";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiNewExpressionImpl() {
        super(JavaElementType.NEW_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return doGetType(null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression
    public PsiType getOwner(PsiAnnotation psiAnnotation) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(0);
        }
        return doGetType(psiAnnotation);
    }

    private PsiType doGetType(PsiAnnotation psiAnnotation) {
        SmartList smartList = new SmartList();
        boolean z = false;
        PsiType psiTypeAnnotate = null;
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            IElementType elementType = firstChildNode.getElementType();
            if (elementType == JavaElementType.ANNOTATION) {
                PsiAnnotation psiAnnotation2 = (PsiAnnotation) firstChildNode.getPsi();
                smartList.add(psiAnnotation2);
                if (psiAnnotation2 == psiAnnotation) {
                    z = true;
                }
            } else if (elementType == JavaElementType.JAVA_CODE_REFERENCE) {
                psiTypeAnnotate = new PsiClassReferenceType((PsiJavaCodeReferenceElement) firstChildNode.getPsi(), null);
                if (z) {
                    return psiTypeAnnotate;
                }
            } else if (ElementType.PRIMITIVE_TYPE_BIT_SET.contains(elementType)) {
                psiTypeAnnotate = JavaPsiFacade.getElementFactory(getProject()).createPrimitiveTypeFromText(firstChildNode.getText()).annotate(TypeAnnotationProvider.Static.create((PsiAnnotation[]) ContainerUtil.copyAndClear(smartList, PsiAnnotation.ARRAY_FACTORY, true)));
                if (z) {
                    return psiTypeAnnotate;
                }
            } else if (elementType == JavaTokenType.LBRACKET) {
                psiTypeAnnotate = psiTypeAnnotate.createArrayType().annotate(TypeAnnotationProvider.Static.create((PsiAnnotation[]) ContainerUtil.copyAndClear(smartList, PsiAnnotation.ARRAY_FACTORY, true)));
                if (z) {
                    return psiTypeAnnotate;
                }
            } else if (elementType == JavaElementType.ANONYMOUS_CLASS) {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
                PsiClass psiClass = (PsiClass) firstChildNode.getPsi();
                psiTypeAnnotate = elementFactory.createType(psiClass, psiClass instanceof PsiTypeParameter ? PsiSubstitutor.EMPTY : elementFactory.createRawSubstitutor(psiClass), PsiUtil.getLanguageLevel(psiClass)).annotate(TypeAnnotationProvider.Static.create((PsiAnnotation[]) ContainerUtil.copyAndClear(smartList, PsiAnnotation.ARRAY_FACTORY, true)));
                if (z) {
                    return psiTypeAnnotate;
                }
            } else {
                continue;
            }
        }
        if (z) {
            return null;
        }
        return psiTypeAnnotate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiExpressionList getArgumentList() {
        PsiExpressionList psiExpressionList = (PsiExpressionList) findChildByRoleAsPsiElement(73);
        if (psiExpressionList != null) {
            return psiExpressionList;
        }
        CompositeElement compositeElement = (CompositeElement) SourceTreeToPsiMap.psiElementToTree(findChildByRoleAsPsiElement(70));
        if (compositeElement != null) {
            return (PsiExpressionList) compositeElement.findChildByRoleAsPsiElement(73);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression
    public PsiExpression[] getArrayDimensions() {
        PsiExpression[] psiExpressionArr = (PsiExpression[]) getChildrenAsPsiElements(ElementType.ARRAY_DIMENSION_BIT_SET, PsiExpression.ARRAY_FACTORY);
        PsiExpression qualifier = getQualifier();
        if (qualifier == null || !ElementType.ARRAY_DIMENSION_BIT_SET.contains(qualifier.getNode().getElementType())) {
            if (psiExpressionArr == null) {
                $$$reportNull$$$0(1);
            }
            return psiExpressionArr;
        }
        LOG.assertTrue(psiExpressionArr[0] == qualifier);
        PsiExpression[] psiExpressionArr2 = (PsiExpression[]) Arrays.copyOfRange(psiExpressionArr, 1, psiExpressionArr.length);
        if (psiExpressionArr2 == null) {
            $$$reportNull$$$0(2);
        }
        return psiExpressionArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression
    public PsiArrayInitializerExpression getArrayInitializer() {
        return (PsiArrayInitializerExpression) findChildByRoleAsPsiElement(77);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiMethod resolveMethod() {
        return resolveConstructor();
    }

    public PsiPolyVariantCachingReference getConstructorFakeReference() {
        return (PsiPolyVariantCachingReference) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiNewExpressionImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6686x81d3f17b();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getConstructorFakeReference$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiNewExpressionImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6686x81d3f17b() {
        return new CachedValueProvider.Result(new PsiPolyVariantCachingReference() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiNewExpressionImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
                switch (i) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNewExpressionImpl$1";
                        break;
                    case 6:
                        objArr[0] = "newElementName";
                        break;
                    case 7:
                        objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                        break;
                    default:
                        objArr[0] = "containingFile";
                        break;
                }
                if (i == 1 || i == 2 || i == 3) {
                    objArr[1] = "resolveInner";
                } else if (i == 4) {
                    objArr[1] = "getElement";
                } else if (i != 5) {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiNewExpressionImpl$1";
                } else {
                    objArr[1] = "getRangeInElement";
                }
                switch (i) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        break;
                    case 6:
                        objArr[2] = "handleElementRename";
                        break;
                    case 7:
                        objArr[2] = "bindToElement";
                        break;
                    default:
                        objArr[2] = "resolveInner";
                        break;
                }
                String str2 = String.format(str, objArr);
                if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
                    throw new IllegalArgumentException(str2);
                }
                throw new IllegalStateException(str2);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public PsiElement bindToElement(PsiElement psiElement) {
                if (psiElement != null) {
                    return null;
                }
                $$$reportNull$$$0(7);
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public PsiElement handleElementRename(String str) {
                if (str != null) {
                    return null;
                }
                $$$reportNull$$$0(6);
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.PsiPolyVariantCachingReference
            public JavaResolveResult[] resolveInner(boolean z, PsiFile psiFile) {
                if (psiFile == null) {
                    $$$reportNull$$$0(0);
                }
                ASTNode aSTNodeFindChildByRole = PsiNewExpressionImpl.this.findChildByRole(71);
                if (aSTNodeFindChildByRole != null) {
                    ASTNode aSTNodeSkipWhitespaceAndComments = PsiImplUtil.skipWhitespaceAndComments(aSTNodeFindChildByRole.getTreeNext());
                    if (aSTNodeSkipWhitespaceAndComments != null && aSTNodeSkipWhitespaceAndComments.getElementType() == JavaElementType.EXPRESSION_LIST) {
                        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(psiFile.getProject());
                        JavaResolveResult[] javaResolveResultArrMultiResolveConstructor = javaPsiFacade.getResolveHelper().multiResolveConstructor(javaPsiFacade.getElementFactory().createType((PsiJavaCodeReferenceElement) SourceTreeToPsiMap.treeElementToPsi(aSTNodeFindChildByRole)), (PsiExpressionList) SourceTreeToPsiMap.treeElementToPsi(aSTNodeSkipWhitespaceAndComments), PsiNewExpressionImpl.this);
                        if (javaResolveResultArrMultiResolveConstructor == null) {
                            $$$reportNull$$$0(1);
                        }
                        return javaResolveResultArrMultiResolveConstructor;
                    }
                } else {
                    ASTNode aSTNodeFindChildByType = PsiNewExpressionImpl.this.findChildByType(JavaElementType.ANONYMOUS_CLASS);
                    if (aSTNodeFindChildByType != null) {
                        JavaPsiFacade javaPsiFacade2 = JavaPsiFacade.getInstance(psiFile.getProject());
                        PsiAnonymousClass psiAnonymousClass = (PsiAnonymousClass) SourceTreeToPsiMap.treeElementToPsi(aSTNodeFindChildByType);
                        JavaResolveResult[] javaResolveResultArrMultiResolveConstructor2 = javaPsiFacade2.getResolveHelper().multiResolveConstructor(psiAnonymousClass.getBaseClassType(), (PsiExpressionList) SourceTreeToPsiMap.treeElementToPsi(aSTNodeFindChildByType.findChildByType(JavaElementType.EXPRESSION_LIST)), psiAnonymousClass);
                        if (javaResolveResultArrMultiResolveConstructor2 == null) {
                            $$$reportNull$$$0(2);
                        }
                        return javaResolveResultArrMultiResolveConstructor2;
                    }
                }
                JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr == null) {
                    $$$reportNull$$$0(3);
                }
                return javaResolveResultArr;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            /* JADX INFO: renamed from: getElement */
            public PsiElement getPsiElement() {
                PsiNewExpressionImpl psiNewExpressionImpl = PsiNewExpressionImpl.this;
                if (psiNewExpressionImpl == null) {
                    $$$reportNull$$$0(4);
                }
                return psiNewExpressionImpl;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public TextRange getRangeInElement() {
                $$$reportNull$$$0(5);
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public String getCanonicalText() {
                throw new UnsupportedOperationException();
            }

            public int hashCode() {
                return getPsiElement().hashCode();
            }

            public boolean equals(Object obj) {
                return (obj instanceof PsiPolyVariantCachingReference) && getPsiElement() == ((PsiReference) obj).getPsiElement();
            }
        }, PsiModificationTracker.MODIFICATION_COUNT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public JavaResolveResult resolveMethodGenerics() {
        ResolveResult[] resolveResultArrMultiResolve = getConstructorFakeReference().multiResolve(false);
        JavaResolveResult javaResolveResult = resolveResultArrMultiResolve.length == 1 ? (JavaResolveResult) resolveResultArrMultiResolve[0] : JavaResolveResult.EMPTY;
        if (javaResolveResult == null) {
            $$$reportNull$$$0(3);
        }
        return javaResolveResult;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression
    public PsiExpression getQualifier() {
        return (PsiExpression) findChildByRoleAsPsiElement(54);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression
    public PsiReferenceParameterList getTypeArgumentList() {
        PsiReferenceParameterList psiReferenceParameterList = (PsiReferenceParameterList) findChildByRoleAsPsiElement(246);
        if (psiReferenceParameterList == null) {
            $$$reportNull$$$0(4);
        }
        return psiReferenceParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression
    public PsiType[] getTypeArguments() {
        PsiType[] typeArguments = getTypeArgumentList().getTypeArguments();
        if (typeArguments == null) {
            $$$reportNull$$$0(5);
        }
        return typeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstructorCall
    public PsiMethod resolveConstructor() {
        return (PsiMethod) resolveMethodGenerics().getElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression
    public PsiJavaCodeReferenceElement getClassReference() {
        return (PsiJavaCodeReferenceElement) findChildByRoleAsPsiElement(71);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression
    public PsiAnonymousClass getAnonymousClass() {
        ASTNode aSTNodeFindChildByType = findChildByType(JavaElementType.ANONYMOUS_CLASS);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return (PsiAnonymousClass) SourceTreeToPsiMap.treeElementToPsi(aSTNodeFindChildByType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression
    public PsiJavaCodeReferenceElement getClassOrAnonymousClassReference() {
        ASTNode aSTNodeFindChildByType = findChildByType(CLASS_REF);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return aSTNodeFindChildByType instanceof PsiJavaCodeReferenceElement ? (PsiJavaCodeReferenceElement) aSTNodeFindChildByType : ((PsiAnonymousClass) aSTNodeFindChildByType.getPsi()).getBaseClassReference();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        if (getChildRole(aSTNode) == 54) {
            ASTNode aSTNodeFindChildByRole = findChildByRole(55);
            super.deleteChildInternal(aSTNode);
            deleteChildInternal(aSTNodeFindChildByRole);
            return;
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 54) {
            TreeElement firstChildNode = getFirstChildNode();
            if (firstChildNode == null || firstChildNode.getElementType() == JavaTokenType.NEW_KEYWORD) {
                return null;
            }
            return firstChildNode;
        }
        if (i == 55) {
            return findChildByType(JavaTokenType.DOT);
        }
        if (i == 77) {
            if (getLastChildNode().getElementType() == JavaElementType.ARRAY_INITIALIZER_EXPRESSION) {
                return getLastChildNode();
            }
            return null;
        }
        if (i == 246) {
            return findChildByType(JavaElementType.REFERENCE_PARAMETER_LIST);
        }
        switch (i) {
            case 69:
                return findChildByType(JavaTokenType.NEW_KEYWORD);
            case 70:
                return findChildByType(JavaElementType.ANONYMOUS_CLASS);
            case 71:
                return findChildByType(JavaElementType.JAVA_CODE_REFERENCE);
            case 72:
                return findChildByType(ElementType.PRIMITIVE_TYPE_BIT_SET);
            case 73:
                return findChildByType(JavaElementType.EXPRESSION_LIST);
            case 74:
                return findChildByType(JavaTokenType.LBRACKET);
            case 75:
                return findChildByType(JavaTokenType.RBRACKET);
            default:
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(7);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.REFERENCE_PARAMETER_LIST) {
            return 246;
        }
        if (elementType == JavaTokenType.NEW_KEYWORD) {
            return 69;
        }
        if (elementType == JavaTokenType.DOT) {
            return 55;
        }
        if (elementType == JavaElementType.JAVA_CODE_REFERENCE) {
            return 71;
        }
        if (elementType == JavaElementType.EXPRESSION_LIST) {
            return 73;
        }
        if (elementType == JavaTokenType.LBRACKET) {
            return 74;
        }
        if (elementType == JavaTokenType.RBRACKET) {
            return 75;
        }
        if (elementType == JavaElementType.ARRAY_INITIALIZER_EXPRESSION) {
            if (aSTNode == getLastChildNode()) {
                return 77;
            }
            return aSTNode == getFirstChildNode() ? 54 : 76;
        }
        if (elementType == JavaElementType.ANONYMOUS_CLASS) {
            return 70;
        }
        if (ElementType.PRIMITIVE_TYPE_BIT_SET.contains(aSTNode.getElementType())) {
            return 72;
        }
        if (ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return aSTNode == getFirstChildNode() ? 54 : 76;
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitNewExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiNewExpression:" + getText();
    }
}
