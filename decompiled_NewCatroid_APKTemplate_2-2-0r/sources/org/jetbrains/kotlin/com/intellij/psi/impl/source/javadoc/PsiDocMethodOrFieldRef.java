package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.scope.DelegatingScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassFilter;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDocMethodOrFieldRef extends CompositePsiElement implements Constants, PsiDocTagValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "scope";
        } else if (i == 2) {
            objArr[0] = "allMethods";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocMethodOrFieldRef";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 3) {
            objArr[1] = "findMethods";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocMethodOrFieldRef";
        } else {
            objArr[1] = "getAllMethods";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "findMethods";
        } else if (i != 3 && i != 4) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiDocMethodOrFieldRef() {
        super(DOC_METHOD_OR_FIELD_REF);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDocTagValue(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        PsiClass scope = getScope();
        PsiElement nameElement = getNameElement();
        if (scope == null || nameElement == null) {
            return new MyReference(PsiElement.EMPTY_ARRAY);
        }
        PsiReference referenceInScope = getReferenceInScope(scope, nameElement);
        if (referenceInScope != null) {
            return referenceInScope;
        }
        for (PsiClass psiClassMo6092getContainingClass = scope.getContainingClass(); psiClassMo6092getContainingClass != null; psiClassMo6092getContainingClass = psiClassMo6092getContainingClass.getContainingClass()) {
            PsiReference referenceInScope2 = getReferenceInScope(psiClassMo6092getContainingClass, nameElement);
            if (referenceInScope2 != null) {
                return referenceInScope2;
            }
        }
        return new MyReference(PsiElement.EMPTY_ARRAY);
    }

    private PsiReference getReferenceInScope(PsiClass psiClass, PsiElement psiElement) {
        MethodSignature methodSignatureCreateMethodSignature;
        PsiField psiFieldFindFieldByName;
        final String text = psiElement.getText();
        String[] signature = getSignature();
        if (signature == null && (psiFieldFindFieldByName = psiClass.findFieldByName(text, true)) != null) {
            return new MyReference(new PsiElement[]{psiFieldFindFieldByName});
        }
        if (signature != null) {
            ArrayList arrayList = new ArrayList(signature.length);
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiElement.getProject());
            for (String str : signature) {
                try {
                    arrayList.add(elementFactory.createTypeFromText(str, psiElement));
                } catch (IncorrectOperationException unused) {
                    arrayList.add(PsiType.NULL);
                }
            }
            methodSignatureCreateMethodSignature = MethodSignatureUtil.createMethodSignature(text, (PsiType[]) arrayList.toArray(PsiType.createArray(arrayList.size())), PsiTypeParameter.EMPTY_ARRAY, PsiSubstitutor.EMPTY, text.equals(psiClass.get$name()));
        } else {
            methodSignatureCreateMethodSignature = null;
        }
        PsiMethod[] psiMethodArrFindMethods = findMethods(methodSignatureCreateMethodSignature, psiClass, text, getAllMethods(psiClass, this));
        if (psiMethodArrFindMethods.length == 0) {
            return null;
        }
        return new MyReference(psiMethodArrFindMethods) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "processor", "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocMethodOrFieldRef$1", "processVariants"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef.MyReference, org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
            public void processVariants(PsiScopeProcessor psiScopeProcessor) {
                if (psiScopeProcessor == null) {
                    $$$reportNull$$$0(0);
                }
                super.processVariants(new DelegatingScopeProcessor(psiScopeProcessor) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef.1.1
                    private static /* synthetic */ void $$$reportNull$$$0(int i) {
                        Object[] objArr = new Object[3];
                        if (i != 1) {
                            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                        } else {
                            objArr[0] = "state";
                        }
                        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocMethodOrFieldRef$1$1";
                        objArr[2] = "execute";
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.DelegatingScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
                    public boolean execute(PsiElement psiElement2, ResolveState resolveState) {
                        if (psiElement2 == null) {
                            $$$reportNull$$$0(0);
                        }
                        if (resolveState == null) {
                            $$$reportNull$$$0(1);
                        }
                        if ((psiElement2 instanceof PsiMethod) && text.equals(((PsiMethod) psiElement2).get$name())) {
                            return super.execute(psiElement2, resolveState);
                        }
                        return true;
                    }
                });
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        PsiElement nameElement = getNameElement();
        return nameElement != null ? nameElement.getTextRange().getStartOffset() : getTextRange().getEndOffset();
    }

    public PsiElement getNameElement() {
        ASTNode aSTNodeFindChildByType = findChildByType(DOC_TAG_VALUE_TOKEN);
        if (aSTNodeFindChildByType != null) {
            return SourceTreeToPsiMap.treeToPsiNotNull(aSTNodeFindChildByType);
        }
        return null;
    }

    public String[] getSignature() {
        PsiElement nameElement = getNameElement();
        if (nameElement == null) {
            return null;
        }
        PsiElement nextSibling = nameElement.getNextSibling();
        while (nextSibling != null && !(nextSibling instanceof PsiDocTagValue)) {
            nextSibling = nextSibling.getNextSibling();
        }
        if (nextSibling == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (PsiElement firstChild = nextSibling.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild.getNode().getElementType() == DOC_TYPE_HOLDER) {
                for (String str : firstChild.getText().split("[, ]")) {
                    if (!str.isEmpty()) {
                        arrayList.add(str);
                    }
                }
            }
        }
        return ArrayUtilRt.toStringArray(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PsiClass getScope() {
        if (getFirstChildNode().getElementType() == JavaDocElementType.DOC_REFERENCE_HOLDER) {
            PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(getFirstChildNode().getFirstChildNode());
            if (psiElementTreeElementToPsi instanceof PsiJavaCodeReferenceElement) {
                PsiElement psiElementResolve = ((PsiJavaCodeReferenceElement) psiElementTreeElementToPsi).resolve();
                if (psiElementResolve instanceof PsiClass) {
                    return (PsiClass) psiElementResolve;
                }
                return null;
            }
            if (psiElementTreeElementToPsi instanceof PsiKeyword) {
                PsiKeyword psiKeyword = (PsiKeyword) psiElementTreeElementToPsi;
                if (psiKeyword.getTokenType().equals(THIS_KEYWORD)) {
                    return JavaResolveUtil.getContextClass(this);
                }
                if (psiKeyword.getTokenType().equals(SUPER_KEYWORD)) {
                    PsiClass contextClass = JavaResolveUtil.getContextClass(this);
                    if (contextClass != null) {
                        return contextClass.getSuperClass();
                    }
                    return null;
                }
            }
        }
        return JavaResolveUtil.getContextClass(this);
    }

    public static PsiMethod[] findMethods(MethodSignature methodSignature, PsiClass psiClass, String str, PsiMethod[] psiMethodArr) {
        PsiSubstitutor superClassSubstitutor;
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        if (psiMethodArr == null) {
            $$$reportNull$$$0(2);
        }
        PsiClass superClass = psiClass.getSuperClass();
        if (superClass == null) {
            superClassSubstitutor = PsiSubstitutor.EMPTY;
        } else {
            superClassSubstitutor = TypeConversionUtil.getSuperClassSubstitutor(superClass, psiClass, PsiSubstitutor.EMPTY);
        }
        ArrayList<PsiMethod> arrayList = new ArrayList(Arrays.asList(psiMethodArr));
        final HashSet hashSet = new HashSet();
        for (PsiMethod psiMethod : arrayList) {
            if (!hashSet.contains(psiMethod)) {
                if (!psiMethod.get$name().equals(str) || (methodSignature != null && !MethodSignatureUtil.areSignaturesErasureEqual(psiMethod.getSignature(superClassSubstitutor), methodSignature))) {
                    hashSet.add(psiMethod);
                }
                PsiSuperMethodImplUtil.getHierarchicalMethodSignature(psiMethod).getSuperSignatures().forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        hashSet.add(((HierarchicalMethodSignature) obj).getMethod());
                    }
                });
            }
        }
        arrayList.removeAll(hashSet);
        PsiMethod[] psiMethodArr2 = (PsiMethod[]) arrayList.toArray(PsiMethod.EMPTY_ARRAY);
        if (psiMethodArr2 == null) {
            $$$reportNull$$$0(3);
        }
        return psiMethodArr2;
    }

    public static PsiMethod[] getAllMethods(PsiElement psiElement, PsiElement psiElement2) {
        SmartList smartList = new SmartList();
        psiElement.processDeclarations(new FilterScopeProcessor(ElementClassFilter.METHOD, smartList), ResolveState.initial(), null, psiElement2);
        PsiMethod[] psiMethodArr = (PsiMethod[]) smartList.toArray(PsiMethod.EMPTY_ARRAY);
        if (psiMethodArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiMethodArr;
    }

    public class MyReference implements PsiJavaReference {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final PsiElement[] myReferredElements;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 6) ? 2 : 3];
            switch (i) {
                case 1:
                case 2:
                case 6:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocMethodOrFieldRef$MyReference";
                    break;
                case 3:
                    objArr[0] = "newElementName";
                    break;
                case 4:
                case 5:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
                default:
                    objArr[0] = "processor";
                    break;
            }
            if (i == 1) {
                objArr[1] = "multiResolve";
            } else if (i == 2) {
                objArr[1] = "getCanonicalText";
            } else if (i != 6) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocMethodOrFieldRef$MyReference";
            } else {
                objArr[1] = "getElement";
            }
            switch (i) {
                case 1:
                case 2:
                case 6:
                    break;
                case 3:
                    objArr[2] = "handleElementRename";
                    break;
                case 4:
                    objArr[2] = "bindToElement";
                    break;
                case 5:
                    objArr[2] = "isReferenceTo";
                    break;
                default:
                    objArr[2] = "processVariants";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 6) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public boolean isSoft() {
            return false;
        }

        public MyReference(PsiElement[] psiElementArr) {
            this.myReferredElements = psiElementArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement resolve() {
            PsiElement[] psiElementArr = this.myReferredElements;
            if (psiElementArr.length == 1) {
                return psiElementArr[0];
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
        public void processVariants(PsiScopeProcessor psiScopeProcessor) {
            if (psiScopeProcessor == null) {
                $$$reportNull$$$0(0);
            }
            for (PsiClass scope = PsiDocMethodOrFieldRef.this.getScope(); scope != null && scope.processDeclarations(new DelegatingScopeProcessor(psiScopeProcessor) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef.MyReference.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i != 1) {
                        objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    } else {
                        objArr[0] = "state";
                    }
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocMethodOrFieldRef$MyReference$1";
                    objArr[2] = "execute";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.scope.DelegatingScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
                public boolean execute(PsiElement psiElement, ResolveState resolveState) {
                    if (psiElement == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (resolveState == null) {
                        $$$reportNull$$$0(1);
                    }
                    if ((psiElement instanceof PsiMethod) || (psiElement instanceof PsiField)) {
                        return super.execute(psiElement, resolveState);
                    }
                    return true;
                }
            }, ResolveState.initial(), null, PsiDocMethodOrFieldRef.this); scope = scope.getContainingClass()) {
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
        public JavaResolveResult advancedResolve(boolean z) {
            return this.myReferredElements.length != 1 ? JavaResolveResult.EMPTY : new CandidateInfo(this.myReferredElements[0], PsiSubstitutor.EMPTY);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
        public JavaResolveResult[] multiResolve(boolean z) {
            JavaResolveResult[] javaResolveResultArr = (JavaResolveResult[]) Arrays.stream(this.myReferredElements).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef$MyReference$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PsiDocMethodOrFieldRef.MyReference.lambda$multiResolve$0((PsiElement) obj);
                }
            }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef$MyReference$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return PsiDocMethodOrFieldRef.MyReference.lambda$multiResolve$1(i);
                }
            });
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(1);
            }
            return javaResolveResultArr;
        }

        static /* synthetic */ CandidateInfo lambda$multiResolve$0(PsiElement psiElement) {
            return new CandidateInfo(psiElement, PsiSubstitutor.EMPTY);
        }

        static /* synthetic */ JavaResolveResult[] lambda$multiResolve$1(int i) {
            return new JavaResolveResult[i];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement[] getVariants() {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public String getCanonicalText() {
            String text = PsiDocMethodOrFieldRef.this.getNameElement().getText();
            if (text == null) {
                $$$reportNull$$$0(2);
            }
            return text;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement handleElementRename(String str) throws IncorrectOperationException {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            PsiElement nameElement = PsiDocMethodOrFieldRef.this.getNameElement();
            TreeElement treeElementPsiToTreeNotNull = SourceTreeToPsiMap.psiToTreeNotNull(nameElement);
            LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaDocTokenType.DOC_TAG_VALUE_TOKEN, str, SharedImplUtil.findCharTableByTree(treeElementPsiToTreeNotNull), PsiDocMethodOrFieldRef.this.getManager());
            ((CompositeElement) treeElementPsiToTreeNotNull.getTreeParent()).replaceChildInternal(SourceTreeToPsiMap.psiToTreeNotNull(nameElement), leafElementCreateSingleLeafElement);
            return SourceTreeToPsiMap.treeToPsiNotNull(leafElementCreateSingleLeafElement);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
            PsiClass containingClass;
            String str;
            PsiMethod psiMethod;
            boolean z;
            if (psiElement == null) {
                $$$reportNull$$$0(4);
            }
            if (isReferenceTo(psiElement)) {
                return PsiDocMethodOrFieldRef.this;
            }
            String text = PsiDocMethodOrFieldRef.this.getNameElement().getText();
            if (psiElement instanceof PsiMethod) {
                psiMethod = (PsiMethod) psiElement;
                z = PsiDocMethodOrFieldRef.this.getSignature() != null;
                containingClass = psiMethod.getContainingClass();
                str = psiMethod.get$name();
            } else if (psiElement instanceof PsiField) {
                PsiField psiField = (PsiField) psiElement;
                containingClass = psiField.getContainingClass();
                str = psiField.get$name();
                psiMethod = null;
                z = false;
            } else {
                throw new IncorrectOperationException();
            }
            PsiElement firstChild = PsiDocMethodOrFieldRef.this.getFirstChild();
            if (containingClass != null && firstChild != null && firstChild.getNode().getElementType() == JavaDocElementType.DOC_REFERENCE_HOLDER) {
                PsiElement firstChild2 = firstChild.getFirstChild();
                if (firstChild2 instanceof PsiJavaCodeReferenceElement) {
                    ((PsiJavaCodeReferenceElement) firstChild2).bindToElement(containingClass);
                }
            } else if (containingClass != null && !PsiTreeUtil.isAncestor(containingClass, PsiDocMethodOrFieldRef.this, true)) {
                PsiDocMethodOrFieldRef.this.addAfter(((PsiDocMethodOrFieldRef) Objects.requireNonNull((PsiDocMethodOrFieldRef) PsiTreeUtil.findChildOfType(JavaPsiFacade.getElementFactory(containingClass.getProject()).createDocCommentFromText("/**{@link " + containingClass.getQualifiedName() + "#" + str + "}*/"), PsiDocMethodOrFieldRef.class))).getFirstChild(), null);
            }
            if (z || !text.equals(str)) {
                String text2 = PsiDocMethodOrFieldRef.this.getText();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("/** @see ");
                if (text.equals(str)) {
                    stringBuffer.append((CharSequence) text2, 0, text2.indexOf(40));
                } else {
                    int iIndexOf = text2.indexOf(35);
                    if (iIndexOf >= 0) {
                        stringBuffer.append((CharSequence) text2, 0, iIndexOf + 1);
                    }
                    stringBuffer.append(str);
                }
                if (z) {
                    stringBuffer.append('(');
                    stringBuffer.append(StringUtil.join(psiMethod.getParameterList().getParameters(), new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocMethodOrFieldRef$MyReference$$ExternalSyntheticLambda2
                        @Override // org.jetbrains.kotlin.com.intellij.util.Function
                        public final Object fun(Object obj) {
                            return TypeConversionUtil.erasure(((PsiParameter) obj).getType()).getCanonicalText();
                        }
                    }, ","));
                    stringBuffer.append(')');
                }
                stringBuffer.append("*/");
                return bindToText(containingClass, stringBuffer);
            }
            return PsiDocMethodOrFieldRef.this;
        }

        public PsiElement bindToText(PsiClass psiClass, StringBuffer stringBuffer) {
            return PsiDocMethodOrFieldRef.this.replace(PsiTreeUtil.getChildOfType(PsiTreeUtil.getChildOfType(JavaPsiFacade.getElementFactory(psiClass.getProject()).createCommentFromText(stringBuffer.toString(), null), PsiDocTag.class), PsiDocMethodOrFieldRef.class));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public boolean isReferenceTo(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(5);
            }
            PsiManagerEx manager = PsiDocMethodOrFieldRef.this.getManager();
            for (PsiElement psiElement2 : this.myReferredElements) {
                if (manager.areElementsEquivalent(psiElement, psiElement2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public TextRange getRangeInElement() {
            ASTNode aSTNodeFindChildByType = PsiDocMethodOrFieldRef.this.findChildByType(JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN);
            if (aSTNodeFindChildByType == null) {
                return new TextRange(0, PsiDocMethodOrFieldRef.this.getTextLength());
            }
            PsiElement nextSibling = SourceTreeToPsiMap.treeToPsiNotNull(aSTNodeFindChildByType).getNextSibling();
            if (nextSibling != null) {
                return new TextRange(nextSibling.getTextRange().getStartOffset() - PsiDocMethodOrFieldRef.this.getTextRange().getStartOffset(), nextSibling.getTextRange().getEndOffset() - PsiDocMethodOrFieldRef.this.getTextRange().getStartOffset());
            }
            return new TextRange(PsiDocMethodOrFieldRef.this.getTextLength(), PsiDocMethodOrFieldRef.this.getTextLength());
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        /* JADX INFO: renamed from: getElement */
        public PsiElement getPsiElement() {
            PsiDocMethodOrFieldRef psiDocMethodOrFieldRef = PsiDocMethodOrFieldRef.this;
            if (psiDocMethodOrFieldRef == null) {
                $$$reportNull$$$0(6);
            }
            return psiDocMethodOrFieldRef;
        }
    }
}
