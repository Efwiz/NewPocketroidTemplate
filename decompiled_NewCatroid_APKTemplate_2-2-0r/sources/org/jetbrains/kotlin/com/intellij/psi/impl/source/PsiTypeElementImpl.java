package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.codeInsight.daemon.impl.analysis.JavaGenericsUtil;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.RuntimeExceptionWithAttachments;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTypeElementImpl extends CompositePsiElement implements PsiTypeElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:15:0x002b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 7 && i != 10 && i != 13 && i != 14 && i != 18 && i != 19) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                case 25:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 7 && i != 10 && i != 13 && i != 14 && i != 18 && i != 19) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                case 25:
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
                objArr[0] = "visitor";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 10:
            case 13:
            case 14:
            case 18:
            case 19:
            case 22:
            case 23:
            case 24:
            case 25:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiTypeElementImpl";
                break;
            case 6:
            case 8:
            case 11:
                objArr[0] = "ref";
                break;
            case 9:
                objArr[0] = "rootType";
                break;
            case 12:
                objArr[0] = "annotations";
                break;
            case 15:
                objArr[0] = "processor";
                break;
            case 16:
                objArr[0] = "state";
                break;
            case 17:
                objArr[0] = "place";
                break;
            case 20:
            case 21:
                objArr[0] = "qualifiedName";
                break;
            case 26:
                objArr[0] = "newElement";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 2) {
            objArr[1] = "getType";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "calculateType";
        } else if (i == 7) {
            objArr[1] = "getReferenceComputable";
        } else if (i == 10) {
            objArr[1] = "allReferencesInside";
        } else if (i == 13) {
            objArr[1] = "createProvider";
        } else if (i == 14) {
            objArr[1] = "collectTypes";
        } else if (i == 18) {
            objArr[1] = "getAnnotations";
        } else if (i != 19) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                case 25:
                    objArr[1] = "addAnnotation";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiTypeElementImpl";
                    break;
            }
        } else {
            objArr[1] = "getApplicableAnnotations";
        }
        switch (i) {
            case 1:
                objArr[2] = "accept";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 10:
            case 13:
            case 14:
            case 18:
            case 19:
            case 22:
            case 23:
            case 24:
            case 25:
                break;
            case 6:
                objArr[2] = "getReferenceComputable";
                break;
            case 8:
                objArr[2] = "getRootTypeElement";
                break;
            case 9:
                objArr[2] = "allReferencesInside";
                break;
            case 11:
                objArr[2] = "computeFromTypeOwner";
                break;
            case 12:
                objArr[2] = "createProvider";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "processDeclarations";
                break;
            case 20:
                objArr[2] = "findAnnotation";
                break;
            case 21:
                objArr[2] = "addAnnotation";
                break;
            case 26:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 7 && i != 10 && i != 13 && i != 14 && i != 18 && i != 19) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                case 25:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public PsiTypeElementImpl() {
        this(JavaElementType.TYPE);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiTypeElementImpl(IElementType iElementType) {
        super(iElementType);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement
    public PsiType getType() {
        PsiType psiType = (PsiType) CachedValuesManager.getProjectPsiDependentCache(this, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6659x1ee1e54b((PsiTypeElementImpl) obj);
            }
        });
        if (psiType == null) {
            $$$reportNull$$$0(2);
        }
        return psiType;
    }

    /* JADX INFO: renamed from: lambda$getType$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiTypeElementImpl, reason: not valid java name */
    /* synthetic */ PsiType m6659x1ee1e54b(PsiTypeElementImpl psiTypeElementImpl) {
        return calculateType();
    }

    private PsiType calculateType() {
        PsiWildcardType psiWildcardTypeCreateUnbounded;
        PsiType inferredType = PsiAugmentProvider.getInferredType(this);
        if (inferredType != null) {
            if (inferredType == null) {
                $$$reportNull$$$0(3);
            }
            return inferredType;
        }
        SmartList smartList = new SmartList();
        SmartList smartList2 = new SmartList();
        PsiElement parent = getMyParent();
        boolean z = false;
        PsiType psiTypeApplyAnnotations = null;
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (!(firstChild instanceof PsiComment) && !(firstChild instanceof PsiWhiteSpace)) {
                if (firstChild instanceof PsiAnnotation) {
                    smartList.add((PsiAnnotation) firstChild);
                } else if (firstChild instanceof PsiTypeElement) {
                    if (firstChild instanceof PsiDiamondTypeElementImpl) {
                        psiTypeApplyAnnotations = new PsiDiamondTypeImpl(getManager(), this);
                        break;
                    }
                    psiTypeApplyAnnotations = ((PsiTypeElement) firstChild).getType();
                } else if (PsiUtil.isJavaToken(firstChild, ElementType.PRIMITIVE_TYPE_BIT_SET)) {
                    String text = firstChild.getText();
                    psiTypeApplyAnnotations = smartList.isEmpty() ? PsiJavaParserFacadeImpl.getPrimitiveType(text) : new PsiPrimitiveType(text, createProvider(smartList));
                } else if (PsiUtil.isJavaToken(firstChild, JavaTokenType.VAR_KEYWORD)) {
                    psiTypeApplyAnnotations = inferVarType(parent);
                } else if (firstChild instanceof PsiJavaCodeReferenceElement) {
                    psiTypeApplyAnnotations = new PsiClassReferenceType(getReferenceComputable((PsiJavaCodeReferenceElement) firstChild), (LanguageLevel) null, createProvider(smartList));
                } else if (PsiUtil.isJavaToken(firstChild, JavaTokenType.LBRACKET)) {
                    smartList2.add(createProvider(smartList));
                    smartList = new SmartList();
                } else if (PsiUtil.isJavaToken(firstChild, JavaTokenType.ELLIPSIS)) {
                    smartList2.add(createProvider(smartList));
                    smartList = new SmartList();
                    z = true;
                }
                if (PsiUtil.isJavaToken(firstChild, JavaTokenType.QUEST) || ((firstChild instanceof ASTNode) && ((ASTNode) firstChild).getElementType() == JavaElementType.DUMMY_ELEMENT && "any".equals(firstChild.getText()))) {
                    PsiElement psiElementSkipWhitespacesAndCommentsForward = PsiTreeUtil.skipWhitespacesAndCommentsForward(firstChild);
                    PsiElement psiElementSkipWhitespacesAndCommentsForward2 = PsiTreeUtil.skipWhitespacesAndCommentsForward(psiElementSkipWhitespacesAndCommentsForward);
                    if (PsiUtil.isJavaToken(psiElementSkipWhitespacesAndCommentsForward, JavaTokenType.EXTENDS_KEYWORD) && (psiElementSkipWhitespacesAndCommentsForward2 instanceof PsiTypeElement)) {
                        psiWildcardTypeCreateUnbounded = PsiWildcardType.createExtends(getManager(), ((PsiTypeElement) psiElementSkipWhitespacesAndCommentsForward2).getType());
                    } else if (PsiUtil.isJavaToken(psiElementSkipWhitespacesAndCommentsForward, JavaTokenType.SUPER_KEYWORD) && (psiElementSkipWhitespacesAndCommentsForward2 instanceof PsiTypeElement)) {
                        psiWildcardTypeCreateUnbounded = PsiWildcardType.createSuper(getManager(), ((PsiTypeElement) psiElementSkipWhitespacesAndCommentsForward2).getType());
                    } else {
                        psiWildcardTypeCreateUnbounded = PsiWildcardType.createUnbounded(getManager());
                    }
                    psiTypeApplyAnnotations = psiWildcardTypeCreateUnbounded.annotate(createProvider(smartList));
                    break;
                }
                if (PsiUtil.isJavaToken(firstChild, JavaTokenType.AND)) {
                    List<PsiType> listCollectTypes = collectTypes();
                    psiTypeApplyAnnotations = PsiIntersectionType.createIntersection(false, (PsiType[]) listCollectTypes.toArray(PsiType.createArray(listCollectTypes.size())));
                    break;
                }
                if (PsiUtil.isJavaToken(firstChild, JavaTokenType.OR)) {
                    psiTypeApplyAnnotations = PsiDisjunctionType.createDisjunction(collectTypes(), getManager());
                    break;
                }
            }
        }
        if (psiTypeApplyAnnotations == null) {
            PsiPrimitiveType psiPrimitiveType = PsiType.NULL;
            if (psiPrimitiveType == null) {
                $$$reportNull$$$0(4);
            }
            return psiPrimitiveType;
        }
        if (!smartList2.isEmpty()) {
            psiTypeApplyAnnotations = createArray(psiTypeApplyAnnotations, smartList2, z);
        }
        if (parent instanceof PsiModifierListOwner) {
            psiTypeApplyAnnotations = JavaSharedImplUtil.applyAnnotations(psiTypeApplyAnnotations, ((PsiModifierListOwner) parent).mo6095getModifierList());
        }
        if (psiTypeApplyAnnotations == null) {
            $$$reportNull$$$0(5);
        }
        return psiTypeApplyAnnotations;
    }

    private static PsiType createArray(PsiType psiType, List<TypeAnnotationProvider> list, boolean z) {
        int size = list.size() - 1;
        while (size >= 0) {
            TypeAnnotationProvider typeAnnotationProvider = list.get(size);
            psiType = (z && size == 0) ? new PsiEllipsisType(psiType, typeAnnotationProvider) : new PsiArrayType(psiType, typeAnnotationProvider);
            size--;
        }
        list.clear();
        return psiType;
    }

    private PsiType inferVarType(PsiElement psiElement) {
        if (psiElement instanceof PsiParameter) {
            PsiParameter psiParameter = (PsiParameter) psiElement;
            PsiElement method = psiParameter.getMethod();
            if (method instanceof PsiForeachStatement) {
                PsiExpression iteratedValue = ((PsiForeachStatement) method).getIteratedValue();
                if (iteratedValue != null) {
                    return JavaGenericsUtil.getCollectionItemType(iteratedValue);
                }
                return null;
            }
            if (method instanceof PsiLambdaExpression) {
                return psiParameter.getType();
            }
        } else {
            for (PsiElement nextSibling = this; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
                if (nextSibling instanceof PsiExpression) {
                    if (nextSibling instanceof PsiArrayInitializerExpression) {
                        return null;
                    }
                    final PsiExpression psiExpression = (PsiExpression) nextSibling;
                    PsiType psiType = (PsiType) RecursionManager.doPreventingRecursion(psiExpression, true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl$$ExternalSyntheticLambda2
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                        public final Object compute() {
                            return psiExpression.getType();
                        }
                    });
                    if (psiType == null) {
                        return null;
                    }
                    return JavaVarTypeUtil.getUpwardProjection(psiType);
                }
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement
    public boolean isInferredType() {
        return PsiUtil.isJavaToken(getFirstChild(), JavaTokenType.VAR_KEYWORD) || PsiAugmentProvider.isInferredType(this);
    }

    private static ClassReferencePointer getReferenceComputable(final PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(6);
        }
        PsiTypeElement rootTypeElement = getRootTypeElement(psiJavaCodeReferenceElement);
        if (rootTypeElement != null) {
            PsiElement parent = rootTypeElement.getMyParent();
            if ((parent instanceof PsiMethod) || (parent instanceof PsiVariable)) {
                JBIterable<PsiJavaCodeReferenceElement> jBIterableAllReferencesInside = allReferencesInside(rootTypeElement);
                Objects.requireNonNull(psiJavaCodeReferenceElement);
                int iIndexOf = jBIterableAllReferencesInside.indexOf(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return psiJavaCodeReferenceElement.equals((PsiJavaCodeReferenceElement) obj);
                    }
                });
                if (iIndexOf < 0) {
                    throw new AssertionError(rootTypeElement.getClass());
                }
                return computeFromTypeOwner(parent, iIndexOf, new WeakReference(psiJavaCodeReferenceElement));
            }
        }
        ClassReferencePointer classReferencePointerConstant = ClassReferencePointer.constant(psiJavaCodeReferenceElement);
        if (classReferencePointerConstant == null) {
            $$$reportNull$$$0(7);
        }
        return classReferencePointerConstant;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiTypeElement getRootTypeElement(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(8);
        }
        return (PsiTypeElement) ObjectUtils.tryCast(SyntaxTraverser.psiApi().parents(psiJavaCodeReferenceElement.getMyParent()).takeWhile(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiTypeElementImpl.lambda$getRootTypeElement$2((PsiElement) obj);
            }
        }).last(), PsiTypeElement.class);
    }

    static /* synthetic */ boolean lambda$getRootTypeElement$2(PsiElement psiElement) {
        return (psiElement instanceof PsiTypeElement) || (psiElement instanceof PsiReferenceParameterList) || (psiElement instanceof PsiJavaCodeReferenceElement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JBIterable<PsiJavaCodeReferenceElement> allReferencesInside(PsiTypeElement psiTypeElement) {
        if (psiTypeElement == null) {
            $$$reportNull$$$0(9);
        }
        JBIterable jBIterableFilter = SyntaxTraverser.psiTraverser(psiTypeElement).filter(PsiJavaCodeReferenceElement.class);
        if (jBIterableFilter == null) {
            $$$reportNull$$$0(10);
        }
        return jBIterableFilter;
    }

    private static ClassReferencePointer computeFromTypeOwner(PsiElement psiElement, int i, WeakReference<PsiJavaCodeReferenceElement> weakReference) {
        if (weakReference == null) {
            $$$reportNull$$$0(11);
        }
        return new ClassReferencePointer(weakReference, i, psiElement) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl.1
            volatile WeakReference<PsiJavaCodeReferenceElement> myCache;
            final /* synthetic */ int val$index;
            final /* synthetic */ PsiElement val$parent;
            final /* synthetic */ WeakReference val$ref;

            private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiTypeElementImpl$1", "retrieveNonNullReference"));
            }

            {
                this.val$ref = weakReference;
                this.val$index = i;
                this.val$parent = psiElement;
                this.myCache = weakReference;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassReferencePointer
            public PsiJavaCodeReferenceElement retrieveReference() {
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElementFindReferenceByIndex = this.myCache.get();
                if (psiJavaCodeReferenceElementFindReferenceByIndex == null) {
                    PsiType psiTypeCalcTypeByParent = calcTypeByParent();
                    if (psiTypeCalcTypeByParent instanceof PsiClassReferenceType) {
                        psiJavaCodeReferenceElementFindReferenceByIndex = findReferenceByIndex((PsiClassReferenceType) psiTypeCalcTypeByParent);
                    }
                    this.myCache = new WeakReference<>(psiJavaCodeReferenceElementFindReferenceByIndex);
                }
                return psiJavaCodeReferenceElementFindReferenceByIndex;
            }

            private PsiJavaCodeReferenceElement findReferenceByIndex(PsiClassReferenceType psiClassReferenceType) {
                PsiTypeElement rootTypeElement = PsiTypeElementImpl.getRootTypeElement(psiClassReferenceType.getReference());
                if (rootTypeElement == null) {
                    return null;
                }
                return (PsiJavaCodeReferenceElement) PsiTypeElementImpl.allReferencesInside(rootTypeElement).get(this.val$index);
            }

            private PsiType calcTypeByParent() {
                if (!this.val$parent.isValid()) {
                    return null;
                }
                PsiElement psiElement2 = this.val$parent;
                PsiType returnType = psiElement2 instanceof PsiMethod ? ((PsiMethod) psiElement2).getReturnType() : ((PsiVariable) psiElement2).getType();
                return returnType instanceof PsiArrayType ? returnType.getDeepComponentType() : returnType;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassReferencePointer
            public PsiJavaCodeReferenceElement retrieveNonNullReference() {
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElementRetrieveReference = retrieveReference();
                if (psiJavaCodeReferenceElementRetrieveReference == null) {
                    PsiType psiTypeCalcTypeByParent = calcTypeByParent();
                    if (!(psiTypeCalcTypeByParent instanceof PsiClassReferenceType)) {
                        PsiUtilCore.ensureValid(this.val$parent);
                        throw new IllegalStateException("No reference type for " + this.val$parent.getClass() + "; type: " + (psiTypeCalcTypeByParent != null ? psiTypeCalcTypeByParent.getClass() : "null"));
                    }
                    PsiJavaCodeReferenceElement psiJavaCodeReferenceElementFindReferenceByIndex = findReferenceByIndex((PsiClassReferenceType) psiTypeCalcTypeByParent);
                    if (psiJavaCodeReferenceElementFindReferenceByIndex == null) {
                        PsiUtilCore.ensureValid(this.val$parent);
                        throw new RuntimeExceptionWithAttachments("Can't retrieve reference by index " + this.val$index + " for " + this.val$parent.getClass() + "; type: " + psiTypeCalcTypeByParent.getClass(), new Attachment("memberType.txt", psiTypeCalcTypeByParent.getCanonicalText()));
                    }
                    psiJavaCodeReferenceElementRetrieveReference = psiJavaCodeReferenceElementFindReferenceByIndex;
                }
                if (psiJavaCodeReferenceElementRetrieveReference == null) {
                    $$$reportNull$$$0(0);
                }
                return psiJavaCodeReferenceElementRetrieveReference;
            }

            public String toString() {
                String str = "Type element reference of " + this.val$parent.getClass() + " #" + this.val$parent.getClass().getSimpleName() + ", index=" + this.val$index;
                return (this.val$parent.isValid() ? new StringBuilder().append(str).append(" #").append(this.val$parent.getLanguage()) : new StringBuilder().append(str).append(", invalid")).toString();
            }
        };
    }

    private static TypeAnnotationProvider createProvider(List<PsiAnnotation> list) {
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        TypeAnnotationProvider typeAnnotationProviderCreate = TypeAnnotationProvider.Static.create((PsiAnnotation[]) ContainerUtil.copyAndClear(list, PsiAnnotation.ARRAY_FACTORY, true));
        if (typeAnnotationProviderCreate == null) {
            $$$reportNull$$$0(13);
        }
        return typeAnnotationProviderCreate;
    }

    private List<PsiType> collectTypes() {
        List<PsiType> map = ContainerUtil.map((Collection) PsiTreeUtil.getChildrenOfTypeAsList(this, PsiTypeElement.class), new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiTypeElement) obj).getType();
            }
        });
        if (map == null) {
            $$$reportNull$$$0(14);
        }
        return map;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement
    public PsiJavaCodeReferenceElement getInnermostComponentReferenceElement() {
        TreeElement firstChildNode = getFirstChildNode();
        if (firstChildNode == null) {
            return null;
        }
        if (firstChildNode.getElementType() == JavaElementType.TYPE) {
            return ((PsiTypeElement) SourceTreeToPsiMap.treeToPsiNotNull(firstChildNode)).getInnermostComponentReferenceElement();
        }
        return getReferenceElement();
    }

    private PsiJavaCodeReferenceElement getReferenceElement() {
        ASTNode aSTNodeFindChildByType = findChildByType(JavaElementType.JAVA_CODE_REFERENCE);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return (PsiJavaCodeReferenceElement) SourceTreeToPsiMap.treeElementToPsi(aSTNodeFindChildByType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(15);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(16);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(17);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = getType().getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(18);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] annotations = getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(19);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        return PsiImplUtil.findAnnotation(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        PsiModifierList psiModifierListMo6095getModifierList;
        PsiIdentifier psiIdentifier;
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        PsiAnnotation psiAnnotationCreateAnnotationFromText = JavaPsiFacade.getElementFactory(getProject()).createAnnotationFromText(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + str, this);
        PsiElement firstChild = getFirstChild();
        for (PsiElement lastChild = getLastChild(); lastChild != firstChild; lastChild = lastChild.getPrevSibling()) {
            if (PsiUtil.isJavaToken(lastChild, JavaTokenType.LBRACKET) || PsiUtil.isJavaToken(lastChild, JavaTokenType.ELLIPSIS)) {
                PsiAnnotation psiAnnotation = (PsiAnnotation) addBefore(psiAnnotationCreateAnnotationFromText, lastChild);
                if (psiAnnotation == null) {
                    $$$reportNull$$$0(22);
                }
                return psiAnnotation;
            }
        }
        if ((firstChild instanceof PsiJavaCodeReferenceElement) && (psiIdentifier = (PsiIdentifier) PsiTreeUtil.getChildOfType(firstChild, PsiIdentifier.class)) != null && psiIdentifier != firstChild.getFirstChild()) {
            PsiAnnotation psiAnnotation2 = (PsiAnnotation) firstChild.addBefore(psiAnnotationCreateAnnotationFromText, psiIdentifier);
            if (psiAnnotation2 == null) {
                $$$reportNull$$$0(23);
            }
            return psiAnnotation2;
        }
        PsiElement parent = getMyParent();
        while ((parent instanceof PsiTypeElement) && (((PsiTypeElement) parent).getType() instanceof PsiArrayType)) {
            parent = parent.getMyParent();
        }
        if ((parent instanceof PsiModifierListOwner) && (psiModifierListMo6095getModifierList = ((PsiModifierListOwner) parent).mo6095getModifierList()) != null) {
            PsiTypeParameterList psiTypeParameterListMo6093getTypeParameterList = parent instanceof PsiTypeParameterListOwner ? ((PsiTypeParameterListOwner) parent).mo6093getTypeParameterList() : null;
            if (psiTypeParameterListMo6093getTypeParameterList == null || psiTypeParameterListMo6093getTypeParameterList.textMatches("")) {
                PsiAnnotation psiAnnotation3 = (PsiAnnotation) psiModifierListMo6095getModifierList.add(psiAnnotationCreateAnnotationFromText);
                if (psiAnnotation3 == null) {
                    $$$reportNull$$$0(24);
                }
                return psiAnnotation3;
            }
        }
        PsiAnnotation psiAnnotation4 = (PsiAnnotation) addBefore(psiAnnotationCreateAnnotationFromText, firstChild);
        if (psiAnnotation4 == null) {
            $$$reportNull$$$0(25);
        }
        return psiAnnotation4;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(26);
        }
        if (!(psiElement instanceof PsiTypeElement) || !((PsiTypeElement) psiElement).isInferredType()) {
            PsiImplUtil.markTypeAnnotations(this);
        }
        PsiElement psiElementReplace = super.replace(psiElement);
        if (psiElementReplace instanceof PsiTypeElement) {
            PsiImplUtil.deleteTypeAnnotations((PsiTypeElement) psiElementReplace);
        }
        return psiElementReplace;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement
    public boolean acceptsAnnotations() {
        if (isInferredType()) {
            return false;
        }
        PsiType type = getType();
        return (PsiType.VOID.equals(type) || PsiType.NULL.equals(type)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeElement:" + getText();
    }
}
