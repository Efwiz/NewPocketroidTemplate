package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.ArrayList;
import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.FunctionalExpressionStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.FunctionalInterfaceParameterizationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassFilter;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.DuplicateConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiMethodReferenceExpressionImpl extends JavaStubPsiElement<FunctionalExpressionStub<PsiMethodReferenceExpression>> implements PsiMethodReferenceExpression {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiMethodReferenceExpressionImpl.class);
    private static final MethodReferenceResolver RESOLVER = new MethodReferenceResolver();

    /* JADX WARN: Code duplicated, block: B:8:0x000f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 4 && i != 5) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 4 && i != 5) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
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
                objArr[0] = "node";
                break;
            case 2:
                objArr[0] = "processor";
                break;
            case 3:
            case 4:
            case 5:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiMethodReferenceExpressionImpl";
                break;
            case 6:
            case 8:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 7:
                objArr[0] = "visitor";
                break;
            case 9:
                objArr[0] = "newElementName";
                break;
            case 10:
                objArr[0] = "qualifierClass";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 3) {
            objArr[1] = "multiResolve";
        } else if (i == 4) {
            objArr[1] = "getRangeInElement";
        } else if (i != 5) {
            switch (i) {
                case 11:
                    objArr[1] = "getElement";
                    break;
                case 12:
                    objArr[1] = "getVariants";
                    break;
                case 13:
                    objArr[1] = "advancedResolve";
                    break;
                case 14:
                    objArr[1] = "getTypeParameters";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiMethodReferenceExpressionImpl";
                    break;
            }
        } else {
            objArr[1] = "getCanonicalText";
        }
        switch (i) {
            case 2:
                objArr[2] = "processVariants";
                break;
            case 3:
            case 4:
            case 5:
            case 11:
            case 12:
            case 13:
            case 14:
                break;
            case 6:
                objArr[2] = "isReferenceTo";
                break;
            case 7:
                objArr[2] = "accept";
                break;
            case 8:
                objArr[2] = "bindToElement";
                break;
            case 9:
                objArr[2] = "handleElementRename";
                break;
            case 10:
                objArr[2] = "bindToElementViaStaticImport";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiMethodReferenceExpressionImpl(FunctionalExpressionStub<PsiMethodReferenceExpression> functionalExpressionStub) {
        super(functionalExpressionStub, JavaStubElementTypes.METHOD_REF_EXPRESSION);
        if (functionalExpressionStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiMethodReferenceExpressionImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression
    public PsiTypeElement getQualifierType() {
        PsiElement qualifier = getQualifier();
        if (qualifier instanceof PsiTypeElement) {
            return (PsiTypeElement) qualifier;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    public PsiType getFunctionalInterfaceType() {
        return getGroundTargetType(LambdaUtil.getFunctionalInterfaceType(this, true));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression
    public boolean isExact() {
        return getPotentiallyApplicableMember() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression, org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    public boolean isPotentiallyCompatible(PsiType psiType) {
        PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(psiType);
        if (functionalInterfaceMethod == null) {
            return false;
        }
        final MethodReferenceResolver methodReferenceResolver = new MethodReferenceResolver() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodReferenceExpressionImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "referenceExpression";
                } else {
                    objArr[0] = "qualifierResolveResult";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiMethodReferenceExpressionImpl$1";
                objArr[2] = "createResolver";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodReferenceResolver
            protected PsiConflictResolver createResolver(PsiMethodReferenceExpressionImpl psiMethodReferenceExpressionImpl, PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult, PsiMethod psiMethod, MethodSignature methodSignature) {
                if (psiMethodReferenceExpressionImpl == null) {
                    $$$reportNull$$$0(0);
                }
                if (qualifierResolveResult == null) {
                    $$$reportNull$$$0(1);
                }
                return DuplicateConflictResolver.INSTANCE;
            }
        };
        ResolveResult[] resolveResultArr = (ResolveResult[]) LambdaUtil.performWithTargetType(this, psiType, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodReferenceExpressionImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6685x99b97e22(methodReferenceResolver);
            }
        });
        PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult = PsiMethodReferenceUtil.getQualifierResolveResult(this);
        int parametersCount = functionalInterfaceMethod.getParameterList().getParametersCount();
        for (ResolveResult resolveResult : resolveResultArr) {
            PsiElement element = resolveResult.getElement();
            if (element instanceof PsiMethod) {
                PsiMethod psiMethod = (PsiMethod) element;
                boolean zHasModifierProperty = psiMethod.hasModifierProperty("static");
                int parametersCount2 = psiMethod.getParameterList().getParametersCount();
                if (qualifierResolveResult.isReferenceTypeQualified() && (getReferenceNameElement() instanceof PsiIdentifier)) {
                    int i = !zHasModifierProperty ? 1 : 0;
                    if (parametersCount2 == parametersCount - i) {
                        return true;
                    }
                    if (psiMethod.isVarArgs() && (functionalInterfaceMethod.isVarArgs() || parametersCount >= (parametersCount2 + i) - 1)) {
                        return true;
                    }
                } else if (zHasModifierProperty) {
                    continue;
                } else {
                    if (parametersCount2 == parametersCount) {
                        return true;
                    }
                    if (psiMethod.isVarArgs() && (functionalInterfaceMethod.isVarArgs() || parametersCount >= parametersCount2 - 1)) {
                        return true;
                    }
                }
            } else if (element instanceof PsiClass) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: lambda$isPotentiallyCompatible$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiMethodReferenceExpressionImpl, reason: not valid java name */
    /* synthetic */ JavaResolveResult[] m6685x99b97e22(MethodReferenceResolver methodReferenceResolver) {
        return methodReferenceResolver.resolve(this, getContainingFile(), false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    public PsiType getGroundTargetType(PsiType psiType) {
        return FunctionalInterfaceParameterizationUtil.getGroundTargetType(psiType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression
    public PsiMember getPotentiallyApplicableMember() {
        return (PsiMember) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodReferenceExpressionImpl$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6683x9093532e();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getPotentiallyApplicableMember$1$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiMethodReferenceExpressionImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6683x9093532e() {
        return CachedValueProvider.Result.create(getPotentiallyApplicableMemberInternal(), PsiModificationTracker.MODIFICATION_COUNT, this);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private PsiMember getPotentiallyApplicableMemberInternal() {
        PsiMethod[] constructors;
        PsiElement referenceNameElement = getReferenceNameElement();
        PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult = PsiMethodReferenceUtil.getQualifierResolveResult(this);
        PsiClass containingClass = qualifierResolveResult.getContainingClass();
        PsiMethod psiMethod = null;
        if (containingClass == null) {
            return null;
        }
        PsiElement qualifier = getQualifier();
        if (qualifier instanceof PsiReferenceExpression) {
            PsiElement psiElementResolve = ((PsiReferenceExpression) qualifier).resolve();
            if ((psiElementResolve instanceof PsiClass) && ((PsiClass) psiElementResolve).hasTypeParameters()) {
                return null;
            }
        }
        if (referenceNameElement instanceof PsiIdentifier) {
            String text = referenceNameElement.getText();
            constructors = containingClass.findMethodsByName(text, true);
            if (constructors.length == 0) {
                return null;
            }
            if (constructors.length > 1) {
                ArrayList arrayList = new ArrayList();
                for (HierarchicalMethodSignature hierarchicalMethodSignature : containingClass.getVisibleSignatures()) {
                    if (text.equals(hierarchicalMethodSignature.getName())) {
                        arrayList.add(hierarchicalMethodSignature.getMethod());
                    }
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                constructors = (PsiMethod[]) arrayList.toArray(PsiMethod.EMPTY_ARRAY);
            }
        } else if (isConstructor()) {
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
            PsiClass arrayClass = elementFactory.getArrayClass(PsiUtil.getLanguageLevel(this));
            if (arrayClass == containingClass) {
                PsiTypeParameter[] typeParameters = arrayClass.getTypeParameters();
                if (typeParameters.length != 1) {
                    return null;
                }
                PsiType psiTypeSubstitute = qualifierResolveResult.getSubstitutor().substitute(typeParameters[0]);
                LOG.assertTrue(psiTypeSubstitute != null, qualifierResolveResult.getSubstitutor());
                return elementFactory.createMethodFromText("public " + psiTypeSubstitute.createArrayType().getCanonicalText() + " __array__(int i) {return null;}", this);
            }
            constructors = containingClass.getConstructors();
        } else {
            constructors = null;
        }
        if (constructors == null) {
            return null;
        }
        if (constructors.length > 0) {
            PsiMethod psiMethod2 = null;
            for (PsiMethod psiMethod3 : constructors) {
                if (PsiUtil.isAccessible(psiMethod3, this, null)) {
                    if (psiMethod2 != null) {
                        return null;
                    }
                    psiMethod2 = psiMethod3;
                }
            }
            if (psiMethod2 == null || psiMethod2.isVarArgs()) {
                return null;
            }
            if (psiMethod2.getTypeParameters().length > 0) {
                PsiReferenceParameterList parameterList = getParameterList();
                if (parameterList == null || parameterList.getTypeParameterElements().length <= 0) {
                    return null;
                }
                return psiMethod2;
            }
            psiMethod = psiMethod2;
        }
        return psiMethod == null ? containingClass : psiMethod;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public PsiExpression getQualifierExpression() {
        PsiElement qualifier = getQualifier();
        if (qualifier instanceof PsiExpression) {
            return (PsiExpression) qualifier;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        return new PsiMethodReferenceType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiElement getReferenceNameElement() {
        PsiElement lastChild = getLastChild();
        if ((lastChild instanceof PsiIdentifier) || PsiUtil.isJavaToken(lastChild, JavaTokenType.NEW_KEYWORD)) {
            return lastChild;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(2);
        }
        PsiScopesUtil.resolveAndWalk(new FilterScopeProcessor(ElementClassFilter.METHOD, psiScopeProcessor), this, null, true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public void setQualifierExpression(PsiExpression psiExpression) throws IncorrectOperationException {
        if (psiExpression == null) {
            LOG.error("Forbidden null qualifier");
            return;
        }
        PsiExpression qualifierExpression = getQualifierExpression();
        if (qualifierExpression != null) {
            qualifierExpression.replace(psiExpression);
            return;
        }
        PsiElement qualifier = getQualifier();
        if (qualifier != null) {
            qualifier.replace(psiExpression);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        JavaResolveResult[] javaResolveResultArrMultiResolveImpl = PsiImplUtil.multiResolveImpl(this, z, RESOLVER);
        if (javaResolveResultArrMultiResolveImpl == null) {
            $$$reportNull$$$0(3);
        }
        return javaResolveResultArrMultiResolveImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public PsiElement getQualifier() {
        PsiElement firstChild = getFirstChild();
        if ((firstChild instanceof PsiExpression) || (firstChild instanceof PsiTypeElement)) {
            return firstChild;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        PsiElement referenceNameElement = getReferenceNameElement();
        if (referenceNameElement != null) {
            int startOffsetInParent = referenceNameElement.getStartOffsetInParent();
            return new TextRange(startOffsetInParent, referenceNameElement.getTextLength() + startOffsetInParent);
        }
        PsiElement psiElementFindChildByType = findChildByType(JavaTokenType.DOUBLE_COLON);
        if (psiElementFindChildByType != null) {
            int startOffsetInParent2 = psiElementFindChildByType.getStartOffsetInParent();
            return new TextRange(startOffsetInParent2, psiElementFindChildByType.getTextLength() + startOffsetInParent2);
        }
        LOG.error(getText());
        $$$reportNull$$$0(4);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String text = getText();
        if (text == null) {
            $$$reportNull$$$0(5);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        if (!(psiElement instanceof PsiMethod)) {
            return false;
        }
        PsiMethod psiMethod = (PsiMethod) psiElement;
        PsiElement referenceNameElement = getReferenceNameElement();
        if (referenceNameElement instanceof PsiIdentifier) {
            if (!referenceNameElement.getText().equals(psiMethod.getName())) {
                return false;
            }
        } else if (PsiUtil.isJavaToken(referenceNameElement, JavaTokenType.NEW_KEYWORD) && !psiMethod.isConstructor()) {
            return false;
        }
        return psiElement.getManager().areElementsEquivalent(psiElement, resolve());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitMethodReferenceExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        CheckUtil.checkWritable(this);
        if (isReferenceTo(psiElement) || !getIsPhysical()) {
            return this;
        }
        if (psiElement instanceof PsiMethod) {
            return handleElementRename(((PsiMethod) psiElement).getName());
        }
        if (psiElement instanceof PsiClass) {
            return this;
        }
        throw new IncorrectOperationException(psiElement.toString());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        PsiElement psiElementFindChildByType = findChildByType(JavaTokenType.IDENTIFIER);
        if (psiElementFindChildByType == null) {
            psiElementFindChildByType = findChildByType(JavaElementType.REFERENCE_EXPRESSION);
        }
        if (psiElementFindChildByType == null) {
            throw new IncorrectOperationException();
        }
        String text = psiElementFindChildByType.getText();
        if (!"this".equals(text) && !PsiKeyword.SUPER.equals(text) && !PsiKeyword.NEW.equals(text) && !Comparing.strEqual(text, str)) {
            psiElementFindChildByType.replace(JavaPsiFacade.getElementFactory(getProject()).createIdentifier(str));
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression
    public boolean isConstructor() {
        PsiElement referenceNameElement = getReferenceNameElement();
        return (referenceNameElement instanceof PsiKeyword) && PsiKeyword.NEW.equals(referenceNameElement.getText());
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiMethodReferenceExpression";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression, org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    public boolean isAcceptable(PsiType psiType, PsiMethod psiMethod) {
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
                if (isAcceptable(psiType2)) {
                    return true;
                }
            }
            return false;
        }
        PsiExpressionList psiExpressionList = (PsiExpressionList) PsiTreeUtil.getParentOfType(this, PsiExpressionList.class);
        boolean zIsExact = isExact();
        if (psiMethod != null && MethodCandidateInfo.isOverloadCheck(psiExpressionList) && ((zIsExact && !InferenceSession.isPertinentToApplicability(this, psiMethod)) || LambdaUtil.isPotentiallyCompatibleWithTypeParameter(this, psiExpressionList, psiMethod))) {
            return true;
        }
        PsiType groundTargetType = getGroundTargetType(psiType);
        if (!isPotentiallyCompatible(groundTargetType)) {
            return false;
        }
        if (MethodCandidateInfo.isOverloadCheck(psiExpressionList) && !zIsExact) {
            return true;
        }
        JavaResolveResult javaResolveResult = (JavaResolveResult) LambdaUtil.performWithTargetType(this, groundTargetType, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodReferenceExpressionImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6684x62a5eccf();
            }
        });
        if ((!(javaResolveResult instanceof MethodCandidateInfo) || ((MethodCandidateInfo) javaResolveResult).isApplicable()) && javaResolveResult.getElement() != null) {
            return PsiMethodReferenceUtil.isReturnTypeCompatible(this, javaResolveResult, groundTargetType);
        }
        return false;
    }

    /* JADX INFO: renamed from: lambda$isAcceptable$2$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiMethodReferenceExpressionImpl, reason: not valid java name */
    /* synthetic */ JavaResolveResult m6684x62a5eccf() {
        return advancedResolve(false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
    public Icon getIcon(int i) {
        return AllIcons.Nodes.MethodReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public PsiElement bindToElementViaStaticImport(PsiClass psiClass) throws IncorrectOperationException {
        if (psiClass == null) {
            $$$reportNull$$$0(10);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return advancedResolve(false).getElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        Object[] objArr = ArrayUtilRt.EMPTY_OBJECT_ARRAY;
        if (objArr == null) {
            $$$reportNull$$$0(12);
        }
        return objArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        JavaResolveResult[] javaResolveResultArrMultiResolve = multiResolve(z);
        JavaResolveResult javaResolveResult = javaResolveResultArrMultiResolve.length == 1 ? javaResolveResultArrMultiResolve[0] : JavaResolveResult.EMPTY;
        if (javaResolveResult == null) {
            $$$reportNull$$$0(13);
        }
        return javaResolveResult;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    /* JADX INFO: renamed from: getReferenceName */
    public String getCustomReferenceName() {
        PsiElement referenceNameElement = getReferenceNameElement();
        if (referenceNameElement != null) {
            return referenceNameElement.getText();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiReferenceParameterList getParameterList() {
        return (PsiReferenceParameterList) PsiTreeUtil.getChildOfType(this, PsiReferenceParameterList.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiType[] getTypeParameters() {
        PsiReferenceParameterList parameterList = getParameterList();
        PsiType[] typeArguments = parameterList != null ? parameterList.getTypeArguments() : PsiType.EMPTY_ARRAY;
        if (typeArguments == null) {
            $$$reportNull$$$0(14);
        }
        return typeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public boolean isQualified() {
        return getQualifier() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        return getCanonicalText();
    }
}
