package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleSettingsFacade;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceJavaCodeReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ClassResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.VariableResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.DelegatingScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.MethodProcessorSetupFailedException;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.NullableFunction;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiReferenceExpressionImpl extends ExpressionPsiElement implements PsiReferenceExpression, SourceJavaCodeReference {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiReferenceExpressionImpl.class);
    private static final Function<PsiReferenceExpressionImpl, PsiType> TYPE_EVALUATOR = new TypeEvaluator();
    private volatile String myCachedNormalizedText;
    private volatile String myCachedQName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 20:
            case 22:
            case 23:
            case 24:
            case 27:
            case 31:
            case 33:
            case 35:
            case 36:
            case 41:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            case 11:
            case 14:
            case 18:
            case 19:
            case 21:
            case 25:
            case 26:
            case 28:
            case 29:
            case 30:
            case 32:
            case 34:
            case 37:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 47:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 20:
            case 22:
            case 23:
            case 24:
            case 27:
            case 31:
            case 33:
            case 35:
            case 36:
            case 41:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
                i2 = 2;
                break;
            case 6:
            case 11:
            case 14:
            case 18:
            case 19:
            case 21:
            case 25:
            case 26:
            case 28:
            case 29:
            case 30:
            case 32:
            case 34:
            case 37:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 47:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = "staticName";
                break;
            case 3:
            case 4:
                objArr[0] = "importList";
                break;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 20:
            case 22:
            case 23:
            case 24:
            case 27:
            case 31:
            case 33:
            case 35:
            case 36:
            case 41:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceExpressionImpl";
                break;
            case 6:
            case 11:
            case 14:
            case 19:
            case 21:
                objArr[0] = "containingFile";
                break;
            case 18:
                objArr[0] = "classNameElement";
                break;
            case 25:
            case 37:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 26:
                objArr[0] = "processor";
                break;
            case 28:
                objArr[0] = "method";
                break;
            case 29:
                objArr[0] = "ref";
                break;
            case 30:
                objArr[0] = "aClass";
                break;
            case 32:
            case 34:
                objArr[0] = "newElementName";
                break;
            case 38:
                objArr[0] = "classRef";
                break;
            case 39:
            case 42:
                objArr[0] = "child";
                break;
            case 40:
                objArr[0] = "project";
                break;
            case 43:
                objArr[0] = "visitor";
                break;
            case 47:
                objArr[0] = "targetClass";
                break;
            default:
                objArr[0] = "qualifierClass";
                break;
        }
        switch (i) {
            case 5:
                objArr[1] = "getImportsFromClass";
                break;
            case 6:
            case 11:
            case 14:
            case 18:
            case 19:
            case 21:
            case 25:
            case 26:
            case 28:
            case 29:
            case 30:
            case 32:
            case 34:
            case 37:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 47:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceExpressionImpl";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[1] = "resolve";
                break;
            case 12:
            case 13:
                objArr[1] = "resolveToMethod";
                break;
            case 15:
            case 16:
            case 17:
                objArr[1] = "resolveToPackage";
                break;
            case 20:
                objArr[1] = "resolveToClass";
                break;
            case 22:
                objArr[1] = "resolveToVariable";
                break;
            case 23:
                objArr[1] = "multiResolve";
                break;
            case 24:
                objArr[1] = "getCanonicalText";
                break;
            case 27:
                objArr[1] = "advancedResolve";
                break;
            case 31:
                objArr[1] = "getTypeParameters";
                break;
            case 33:
                objArr[1] = "handleElementRename";
                break;
            case 35:
            case 36:
                objArr[1] = "renameDirectly";
                break;
            case 41:
                objArr[1] = "createEmptyRefParameterList";
                break;
            case 44:
                objArr[1] = "getElement";
                break;
            case 45:
                objArr[1] = "getRangeInElement";
                break;
            case 46:
                objArr[1] = "getClassNameText";
                break;
            case 48:
                objArr[1] = "getQualifiedName";
                break;
            case 49:
                objArr[1] = "getCachedNormalizedText";
                break;
        }
        switch (i) {
            case 4:
                objArr[2] = "getImportsFromClass";
                break;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 20:
            case 22:
            case 23:
            case 24:
            case 27:
            case 31:
            case 33:
            case 35:
            case 36:
            case 41:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
                break;
            case 6:
                objArr[2] = "resolve";
                break;
            case 11:
                objArr[2] = "resolveToMethod";
                break;
            case 14:
                objArr[2] = "resolveToPackage";
                break;
            case 18:
            case 19:
                objArr[2] = "resolveToClass";
                break;
            case 21:
                objArr[2] = "resolveToVariable";
                break;
            case 25:
                objArr[2] = "isReferenceTo";
                break;
            case 26:
                objArr[2] = "processVariants";
                break;
            case 28:
            case 29:
                objArr[2] = "hasValidQualifier";
                break;
            case 30:
                objArr[2] = "seemsScrambledByStructure";
                break;
            case 32:
                objArr[2] = "handleElementRename";
                break;
            case 34:
                objArr[2] = "renameDirectly";
                break;
            case 37:
                objArr[2] = "bindToElement";
                break;
            case 38:
                objArr[2] = "isFullyQualified";
                break;
            case 39:
                objArr[2] = "deleteChildInternal";
                break;
            case 40:
                objArr[2] = "createEmptyRefParameterList";
                break;
            case 42:
                objArr[2] = "getChildRole";
                break;
            case 43:
                objArr[2] = "accept";
                break;
            case 47:
                objArr[2] = "fullyQualify";
                break;
            default:
                objArr[2] = "bindToElementViaStaticImport";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 20:
            case 22:
            case 23:
            case 24:
            case 27:
            case 31:
            case 33:
            case 35:
            case 36:
            case 41:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
                throw new IllegalStateException(str2);
            case 6:
            case 11:
            case 14:
            case 18:
            case 19:
            case 21:
            case 25:
            case 26:
            case 28:
            case 29:
            case 30:
            case 32:
            case 34:
            case 37:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 47:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    public PsiReferenceExpressionImpl() {
        super(JavaElementType.REFERENCE_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public PsiExpression getQualifierExpression() {
        return (PsiExpression) findChildByRoleAsPsiElement(54);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public PsiElement bindToElementViaStaticImport(PsiClass psiClass) throws IncorrectOperationException {
        PsiImportList importList;
        boolean z = false;
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        if (psiClass.getQualifiedName() == null) {
            throw new IncorrectOperationException();
        }
        if (getQualifierExpression() != null) {
            throw new IncorrectOperationException("Reference is qualified: " + getText());
        }
        if (!getIsPhysical()) {
            return this;
        }
        String customReferenceName = getCustomReferenceName();
        PsiFile containingFile = getContainingFile();
        if (containingFile instanceof PsiJavaFile) {
            importList = ((PsiJavaFile) containingFile).getImportList();
            PsiImportStatementBase psiImportStatementBaseFindSingleImportStatement = importList.findSingleImportStatement(customReferenceName);
            z = psiImportStatementBaseFindSingleImportStatement == null;
            if ((psiImportStatementBaseFindSingleImportStatement instanceof PsiImportStaticStatement) && (psiClass.getQualifiedName() + "." + customReferenceName).equals(psiImportStatementBaseFindSingleImportStatement.getImportReference().getQualifiedName())) {
                return this;
            }
        } else {
            importList = null;
        }
        if (z) {
            bindToElementViaStaticImport(psiClass, customReferenceName, importList);
        } else {
            PsiManagerEx manager = getManager();
            PsiReferenceExpression psiReferenceExpressionCreateReferenceExpression = JavaPsiFacade.getElementFactory(manager.getProject()).createReferenceExpression(psiClass);
            LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.DOT, ".", 0, 1, SharedImplUtil.findCharTableByTree(this), manager);
            addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, SourceTreeToPsiMap.psiElementToTree(getParameterList()), Boolean.TRUE);
            addBefore(psiReferenceExpressionCreateReferenceExpression, SourceTreeToPsiMap.treeElementToPsi(leafElementCreateSingleLeafElement));
        }
        return this;
    }

    public static void bindToElementViaStaticImport(PsiClass psiClass, String str, PsiImportList psiImportList) throws IncorrectOperationException {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (psiImportList == null) {
            $$$reportNull$$$0(3);
        }
        String qualifiedName = psiClass.getQualifiedName();
        List<PsiJavaCodeReferenceElement> importsFromClass = getImportsFromClass(psiImportList, qualifiedName);
        JavaFileCodeStyleFacade javaFileCodeStyleFacadeForContext = JavaFileCodeStyleFacade.forContext(psiImportList.getContainingFile());
        if ((!javaFileCodeStyleFacadeForContext.isToImportOnDemand(qualifiedName) && importsFromClass.size() + 1 < javaFileCodeStyleFacadeForContext.getNamesCountToUseImportOnDemand()) || JavaCodeStyleManager.getInstance(psiClass.getProject()).hasConflictingOnDemandImport((PsiJavaFile) psiImportList.getContainingFile(), psiClass, str)) {
            psiImportList.add(JavaPsiFacade.getElementFactory(psiClass.getProject()).createImportStaticStatement(psiClass, str));
            return;
        }
        Iterator<PsiJavaCodeReferenceElement> it = importsFromClass.iterator();
        while (it.hasNext()) {
            PsiImportStaticStatement psiImportStaticStatement = (PsiImportStaticStatement) PsiTreeUtil.getParentOfType(it.next(), PsiImportStaticStatement.class);
            if (psiImportStaticStatement != null) {
                psiImportStaticStatement.delete();
            }
        }
        psiImportList.add(JavaPsiFacade.getElementFactory(psiClass.getProject()).createImportStaticStatement(psiClass, "*"));
    }

    private static List<PsiJavaCodeReferenceElement> getImportsFromClass(PsiImportList psiImportList, String str) {
        if (psiImportList == null) {
            $$$reportNull$$$0(4);
        }
        ArrayList arrayList = new ArrayList();
        for (PsiImportStaticStatement psiImportStaticStatement : psiImportList.getImportStaticStatements()) {
            PsiClass psiClassResolveTargetClass = psiImportStaticStatement.resolveTargetClass();
            if (psiClassResolveTargetClass != null && Comparing.strEqual(psiClassResolveTargetClass.getQualifiedName(), str)) {
                arrayList.add(psiImportStaticStatement.getImportReference());
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression
    public void setQualifierExpression(PsiExpression psiExpression) throws IncorrectOperationException {
        PsiExpression qualifierExpression = getQualifierExpression();
        if (psiExpression == null) {
            if (qualifierExpression != null) {
                deleteChildInternal(qualifierExpression.getNode());
                return;
            }
            return;
        }
        if (qualifierExpression == null) {
            CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
            TreeElement treeElementAddInternal = (TreeElement) findChildByRole(55);
            if (treeElementAddInternal == null) {
                LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(JavaTokenType.DOT, ".", 0, 1, charTableFindCharTableByTree, getManager());
                treeElementAddInternal = addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, getFirstChildNode(), Boolean.TRUE);
            }
            addBefore(psiExpression, treeElementAddInternal.getPsi());
        }
        getQualifierExpression().replace(psiExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public PsiElement getQualifier() {
        return getQualifierExpression();
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        this.myCachedQName = null;
        this.myCachedNormalizedText = null;
        super.clearCaches();
    }

    public static final class OurGenericsResolver implements ResolveCache.PolyVariantContextResolver<PsiJavaReference> {
        public static final OurGenericsResolver INSTANCE = new OurGenericsResolver();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 5 || i == 6) ? 2 : 3];
            switch (i) {
                case 1:
                case 4:
                    objArr[0] = "containingFile";
                    break;
                case 2:
                case 5:
                case 6:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceExpressionImpl$OurGenericsResolver";
                    break;
                case 3:
                    objArr[0] = "expression";
                    break;
                default:
                    objArr[0] = "ref";
                    break;
            }
            if (i == 2) {
                objArr[1] = "resolve";
            } else if (i == 5 || i == 6) {
                objArr[1] = "resolveAllQualifiers";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceExpressionImpl$OurGenericsResolver";
            }
            if (i != 2) {
                if (i == 3 || i == 4) {
                    objArr[2] = "resolveAllQualifiers";
                } else if (i != 5 && i != 6) {
                    objArr[2] = "resolve";
                }
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 5 && i != 6) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.PolyVariantContextResolver
        public ResolveResult[] resolve(PsiJavaReference psiJavaReference, PsiFile psiFile, boolean z) {
            if (psiJavaReference == null) {
                $$$reportNull$$$0(0);
            }
            if (psiFile == null) {
                $$$reportNull$$$0(1);
            }
            PsiReferenceExpressionImpl psiReferenceExpressionImpl = (PsiReferenceExpressionImpl) psiJavaReference;
            CompositeElement treeParent = psiReferenceExpressionImpl.getTreeParent();
            IElementType elementType = treeParent == null ? null : treeParent.getElementType();
            List<ResolveResult[]> listResolveAllQualifiers = resolveAllQualifiers(psiReferenceExpressionImpl, psiFile);
            JavaResolveResult[] javaResolveResultArrResolve = psiReferenceExpressionImpl.resolve(elementType, psiFile);
            if (javaResolveResultArrResolve.length == 0 && z && elementType != JavaElementType.REFERENCE_EXPRESSION) {
                javaResolveResultArrResolve = psiReferenceExpressionImpl.resolve(JavaElementType.REFERENCE_EXPRESSION, psiFile);
            }
            JavaResolveUtil.substituteResults(psiReferenceExpressionImpl, javaResolveResultArrResolve);
            ObjectUtils.reachabilityFence(listResolveAllQualifiers);
            if (javaResolveResultArrResolve == null) {
                $$$reportNull$$$0(2);
            }
            return javaResolveResultArrResolve;
        }

        private static List<ResolveResult[]> resolveAllQualifiers(PsiReferenceExpressionImpl psiReferenceExpressionImpl, final PsiFile psiFile) {
            if (psiReferenceExpressionImpl == null) {
                $$$reportNull$$$0(3);
            }
            if (psiFile == null) {
                $$$reportNull$$$0(4);
            }
            PsiElement qualifier = psiReferenceExpressionImpl.getQualifier();
            if (qualifier == null) {
                List<ResolveResult[]> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(5);
                }
                return listEmptyList;
            }
            final SmartList smartList = new SmartList();
            final ResolveCache resolveCache = ResolveCache.getInstance(psiFile.getProject());
            final boolean zIsPhysical = psiFile.getIsPhysical();
            qualifier.accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl.OurGenericsResolver.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceExpressionImpl$OurGenericsResolver$1", "elementFinished"));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                public void visitClass(PsiClass psiClass) {
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                public void visitExpressionList(PsiExpressionList psiExpressionList) {
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                public void visitLambdaExpression(PsiLambdaExpression psiLambdaExpression) {
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
                    if ((psiReferenceExpression instanceof PsiReferenceExpressionImpl) && resolveCache.getCachedResults(psiReferenceExpression, zIsPhysical, false, true) == null) {
                        visitElement(psiReferenceExpression);
                    }
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor
                protected void elementFinished(PsiElement psiElement) {
                    if (psiElement == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (psiElement instanceof PsiReferenceExpressionImpl) {
                        smartList.add(resolveCache.resolveWithCaching((PsiReferenceExpressionImpl) psiElement, (ResolveCache.PolyVariantContextResolver<PsiReferenceExpressionImpl>) OurGenericsResolver.INSTANCE, true, false, psiFile));
                    }
                }
            });
            return smartList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JavaResolveResult[] resolve(IElementType iElementType, PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(6);
        }
        if (iElementType == JavaElementType.REFERENCE_EXPRESSION) {
            JavaResolveResult[] javaResolveResultArrResolveToVariable = resolveToVariable(psiFile);
            if (javaResolveResultArrResolveToVariable.length == 1 && javaResolveResultArrResolveToVariable[0].isAccessible()) {
                if (javaResolveResultArrResolveToVariable == null) {
                    $$$reportNull$$$0(7);
                }
                return javaResolveResultArrResolveToVariable;
            }
            if (javaResolveResultArrResolveToVariable.length <= 0) {
                javaResolveResultArrResolveToVariable = null;
            }
            PsiElement referenceNameElement = getReferenceNameElement();
            if (!(referenceNameElement instanceof PsiIdentifier)) {
                JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr == null) {
                    $$$reportNull$$$0(8);
                }
                return javaResolveResultArr;
            }
            JavaResolveResult[] javaResolveResultArrResolveToClass = resolveToClass(referenceNameElement, psiFile);
            if (javaResolveResultArrResolveToClass.length == 1 && !javaResolveResultArrResolveToClass[0].isAccessible()) {
                JavaResolveResult[] javaResolveResultArrResolveToPackage = resolveToPackage(psiFile);
                if (javaResolveResultArrResolveToPackage.length != 0) {
                    javaResolveResultArrResolveToClass = javaResolveResultArrResolveToPackage;
                }
            } else if (javaResolveResultArrResolveToClass.length == 0) {
                javaResolveResultArrResolveToClass = resolveToPackage(psiFile);
            }
            if (javaResolveResultArrResolveToClass.length == 0 && javaResolveResultArrResolveToVariable == null) {
                javaResolveResultArrResolveToClass = PsiJavaCodeReferenceElementImpl.tryClassResult(getCachedNormalizedText(), this);
            }
            if (javaResolveResultArrResolveToClass.length != 0 || javaResolveResultArrResolveToVariable == null) {
                javaResolveResultArrResolveToVariable = javaResolveResultArrResolveToClass;
            }
            if (javaResolveResultArrResolveToVariable == null) {
                $$$reportNull$$$0(9);
            }
            return javaResolveResultArrResolveToVariable;
        }
        if (iElementType == JavaElementType.METHOD_CALL_EXPRESSION) {
            return resolveToMethod(psiFile);
        }
        if (iElementType == JavaElementType.METHOD_REF_EXPRESSION) {
            if (((PsiMethodReferenceExpression) getLightParent()).isConstructor()) {
                PsiElement referenceNameElement2 = getReferenceNameElement();
                if (referenceNameElement2 == null) {
                    JavaResolveResult[] javaResolveResultArr2 = JavaResolveResult.EMPTY_ARRAY;
                    if (javaResolveResultArr2 == null) {
                        $$$reportNull$$$0(10);
                    }
                    return javaResolveResultArr2;
                }
                return resolveToClass(referenceNameElement2, psiFile);
            }
            return resolve(JavaElementType.REFERENCE_EXPRESSION, psiFile);
        }
        return resolveToVariable(psiFile);
    }

    private JavaResolveResult[] resolveToMethod(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(11);
        }
        PsiMethodCallExpression psiMethodCallExpression = (PsiMethodCallExpression) getLightParent();
        MethodResolverProcessor methodResolverProcessor = new MethodResolverProcessor(psiMethodCallExpression, psiFile);
        try {
            PsiScopesUtil.setupAndRunProcessor(methodResolverProcessor, psiMethodCallExpression, false);
            JavaResolveResult[] result = methodResolverProcessor.getResult();
            if (result == null) {
                $$$reportNull$$$0(13);
            }
            return result;
        } catch (MethodProcessorSetupFailedException unused) {
            JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(12);
            }
            return javaResolveResultArr;
        }
    }

    private JavaResolveResult[] resolveToPackage(PsiFile psiFile) {
        JavaResolveResult[] javaResolveResultArr;
        if (psiFile == null) {
            $$$reportNull$$$0(14);
        }
        String cachedNormalizedText = getCachedNormalizedText();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(psiFile.getProject());
        PsiPackage psiPackageFindPackage = javaPsiFacade.findPackage(cachedNormalizedText);
        if (psiPackageFindPackage == null) {
            if (javaPsiFacade.isPartOfPackagePrefix(cachedNormalizedText)) {
                javaResolveResultArr = CandidateInfo.RESOLVE_RESULT_FOR_PACKAGE_PREFIX_PACKAGE;
            } else {
                javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
            }
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(15);
            }
            return javaResolveResultArr;
        }
        PsiExpression qualifierExpression = getQualifierExpression();
        if ((qualifierExpression instanceof PsiReferenceExpression) && !(((PsiReferenceExpression) qualifierExpression).resolve() instanceof PsiPackage)) {
            JavaResolveResult[] javaResolveResultArr2 = JavaResolveResult.EMPTY_ARRAY;
            if (javaResolveResultArr2 == null) {
                $$$reportNull$$$0(16);
            }
            return javaResolveResultArr2;
        }
        return new JavaResolveResult[]{new CandidateInfo((PsiElement) psiPackageFindPackage, PsiSubstitutor.EMPTY, (PsiElement) this, false)};
    }

    private JavaResolveResult[] resolveToClass(PsiElement psiElement, PsiFile psiFile) {
        if (psiElement == null) {
            $$$reportNull$$$0(18);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(19);
        }
        ClassResolverProcessor classResolverProcessor = new ClassResolverProcessor(psiElement.getText(), this, psiFile);
        PsiScopesUtil.resolveAndWalk(classResolverProcessor, this, null);
        JavaResolveResult[] result = classResolverProcessor.getResult();
        if (result == null) {
            $$$reportNull$$$0(20);
        }
        return result;
    }

    private JavaResolveResult[] resolveToVariable(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(21);
        }
        VariableResolverProcessor variableResolverProcessor = new VariableResolverProcessor(this, psiFile);
        PsiScopesUtil.resolveAndWalk(variableResolverProcessor, this, null);
        JavaResolveResult[] result = variableResolverProcessor.getResult();
        if (result == null) {
            $$$reportNull$$$0(22);
        }
        return result;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        JavaResolveResult[] javaResolveResultArrMultiResolveImpl = PsiImplUtil.multiResolveImpl(this, z, OurGenericsResolver.INSTANCE);
        if (javaResolveResultArrMultiResolveImpl == null) {
            $$$reportNull$$$0(23);
        }
        return javaResolveResultArrMultiResolveImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String qualifiedName;
        PsiElement psiElementResolve = resolve();
        if (!(psiElementResolve instanceof PsiClass) || (qualifiedName = ((PsiClass) psiElementResolve).getQualifiedName()) == null) {
            return getCachedNormalizedText();
        }
        if (qualifiedName == null) {
            $$$reportNull$$$0(24);
        }
        return qualifiedName;
    }

    private static class TypeEvaluator implements NullableFunction<PsiReferenceExpressionImpl, PsiType> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "expr", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceExpressionImpl$TypeEvaluator", "fun"));
        }

        private TypeEvaluator() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public PsiType fun(PsiReferenceExpressionImpl psiReferenceExpressionImpl) throws Exception {
            ASTNode aSTNodeFindChildByRole;
            if (psiReferenceExpressionImpl == null) {
                $$$reportNull$$$0(0);
            }
            PsiFile containingFile = psiReferenceExpressionImpl.getContainingFile();
            ResolveResult[] resolveResultArrResolveWithCaching = ResolveCache.getInstance(containingFile.getProject()).resolveWithCaching(psiReferenceExpressionImpl, (ResolveCache.PolyVariantContextResolver<PsiReferenceExpressionImpl>) OurGenericsResolver.INSTANCE, true, false, containingFile);
            PsiClass containingClass = null;
            JavaResolveResult javaResolveResult = resolveResultArrResolveWithCaching.length == 1 ? (JavaResolveResult) resolveResultArrResolveWithCaching[0] : null;
            PsiElement element = javaResolveResult == null ? null : javaResolveResult.getElement();
            if (element == null) {
                ASTNode aSTNodeFindChildByRole2 = psiReferenceExpressionImpl.findChildByRole(53);
                if (aSTNodeFindChildByRole2 != null && "length".equals(aSTNodeFindChildByRole2.getText()) && (aSTNodeFindChildByRole = psiReferenceExpressionImpl.findChildByRole(54)) != null && ElementType.EXPRESSION_BIT_SET.contains(aSTNodeFindChildByRole.getElementType()) && (((PsiExpression) SourceTreeToPsiMap.treeToPsiNotNull(aSTNodeFindChildByRole)).getType() instanceof PsiArrayType)) {
                    return PsiType.INT;
                }
                return null;
            }
            if (!(element instanceof PsiVariable)) {
                return null;
            }
            PsiType type = ((PsiVariable) element).getType();
            if (type instanceof PsiEllipsisType) {
                type = ((PsiEllipsisType) type).toArrayType();
            }
            if (!type.isValid()) {
                PsiUtil.ensureValidType(type, "invalid type of " + element + " of class " + element.getClass() + ", valid=" + element.isValid());
            }
            if (element instanceof PsiField) {
                PsiField psiField = (PsiField) element;
                if (!psiField.hasModifierProperty("static")) {
                    containingClass = psiField.getContainingClass();
                }
            }
            LanguageLevel languageLevel = PsiUtil.getLanguageLevel(containingFile);
            if (type instanceof PsiClassType) {
                type = ((PsiClassType) type).setLanguageLevel(languageLevel);
            }
            if (languageLevel.isAtLeast(LanguageLevel.JDK_1_5)) {
                PsiSubstitutor substitutor = javaResolveResult.getSubstitutor();
                if (containingClass == null || !PsiUtil.isRawSubstitutor(containingClass, substitutor)) {
                    PsiType psiTypeSubstitute = substitutor.substitute(type);
                    PsiUtil.ensureValidType(psiTypeSubstitute);
                    PsiType psiTypeNormalizeWildcardTypeByPosition = PsiImplUtil.normalizeWildcardTypeByPosition(psiTypeSubstitute, psiReferenceExpressionImpl);
                    PsiUtil.ensureValidType(psiTypeNormalizeWildcardTypeByPosition);
                    return PsiClassImplUtil.correctType(psiTypeNormalizeWildcardTypeByPosition, psiReferenceExpressionImpl.getResolveScope());
                }
            }
            return PsiClassImplUtil.correctType(TypeConversionUtil.erasure(type), psiReferenceExpressionImpl.getResolveScope());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiElement parent = getLightParent();
        if (parent instanceof PsiMethodCallExpression) {
            return ((PsiMethodCallExpression) parent).getType();
        }
        return JavaResolveCache.getInstance(getProject()).getType(this, TYPE_EVALUATOR);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        String name2;
        if (psiElement == null) {
            $$$reportNull$$$0(25);
        }
        IElementType elementType = getLastChildNode().getElementType();
        boolean z = psiElement instanceof PsiMethod;
        if (elementType == JavaTokenType.IDENTIFIER) {
            if (!(psiElement instanceof PsiPackage) && (!(psiElement instanceof PsiNamedElement) || (name2 = ((PsiNamedElement) psiElement).get$name()) == null || !name2.equals(getLastChildNode().getText()))) {
                return false;
            }
        } else if ((elementType == JavaTokenType.SUPER_KEYWORD || elementType == JavaTokenType.THIS_KEYWORD) && (!z || !((PsiMethod) psiElement).isConstructor())) {
            return false;
        }
        if ((getLightParent() instanceof PsiMethodCallExpression) != z) {
            return false;
        }
        return psiElement.getManager().areElementsEquivalent(psiElement, advancedResolve(true).getElement());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(26);
        }
        PsiScopesUtil.resolveAndWalk(new DelegatingScopeProcessor(psiScopeProcessor) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl.1
            private PsiElement myResolveContext;
            private final Set<String> myVarNames = new HashSet();

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "state";
                } else if (i == 4) {
                    objArr[0] = "method";
                } else if (i != 5) {
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                } else {
                    objArr[0] = "event";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiReferenceExpressionImpl$1";
                if (i == 2) {
                    objArr[2] = "shouldProcess";
                } else if (i == 3) {
                    objArr[2] = "ensureNonShadowedVariable";
                } else if (i == 4) {
                    objArr[2] = "shouldProcessMethod";
                } else if (i != 5) {
                    objArr[2] = "execute";
                } else {
                    objArr[2] = "handleEvent";
                }
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
                return !shouldProcess(psiElement) || super.execute(psiElement, resolveState);
            }

            private boolean shouldProcess(PsiElement psiElement) {
                if (psiElement == null) {
                    $$$reportNull$$$0(2);
                }
                if (psiElement instanceof PsiVariable) {
                    return ensureNonShadowedVariable((PsiVariable) psiElement);
                }
                if (psiElement instanceof PsiClass) {
                    return !PsiReferenceExpressionImpl.seemsScrambled((PsiClass) psiElement);
                }
                if (psiElement instanceof PsiPackage) {
                    return PsiReferenceExpressionImpl.this.isQualified();
                }
                if (psiElement instanceof PsiMethod) {
                    return shouldProcessMethod((PsiMethod) psiElement);
                }
                return false;
            }

            private boolean ensureNonShadowedVariable(PsiVariable psiVariable) {
                if (psiVariable == null) {
                    $$$reportNull$$$0(3);
                }
                if (PsiUtil.isJvmLocalVariable(psiVariable) && this.myVarNames.contains(psiVariable.get$name())) {
                    return false;
                }
                this.myVarNames.add(psiVariable.get$name());
                return true;
            }

            private boolean shouldProcessMethod(PsiMethod psiMethod) {
                if (psiMethod == null) {
                    $$$reportNull$$$0(4);
                }
                return !psiMethod.isConstructor() && PsiReferenceExpressionImpl.hasValidQualifier(psiMethod, PsiReferenceExpressionImpl.this, this.myResolveContext);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.scope.DelegatingScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
            public void handleEvent(PsiScopeProcessor.Event event, Object obj) {
                if (event == null) {
                    $$$reportNull$$$0(5);
                }
                if (event == JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT) {
                    this.myResolveContext = (PsiElement) obj;
                }
                super.handleEvent(event, obj);
            }
        }, this, null, true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        JavaResolveResult[] javaResolveResultArrMultiResolve = multiResolve(z);
        JavaResolveResult javaResolveResult = javaResolveResultArrMultiResolve.length == 1 ? javaResolveResultArrMultiResolve[0] : JavaResolveResult.EMPTY;
        if (javaResolveResult == null) {
            $$$reportNull$$$0(27);
        }
        return javaResolveResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasValidQualifier(PsiMethod psiMethod, PsiReferenceExpression psiReferenceExpression, PsiElement psiElement) {
        if (psiMethod == null) {
            $$$reportNull$$$0(28);
        }
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(29);
        }
        PsiClass containingClass = psiMethod.getContainingClass();
        if (containingClass == null || !containingClass.isInterface() || !psiMethod.hasModifierProperty("static")) {
            return true;
        }
        if (!PsiUtil.getLanguageLevel(psiReferenceExpression).isAtLeast(LanguageLevel.JDK_1_8)) {
            return false;
        }
        PsiExpression qualifierExpression = psiReferenceExpression.getQualifierExpression();
        if (qualifierExpression == null && ((psiElement instanceof PsiImportStaticStatement) || PsiTreeUtil.isAncestor(containingClass, psiReferenceExpression, true))) {
            return true;
        }
        if (qualifierExpression instanceof PsiReferenceExpression) {
            PsiElement psiElementResolve = ((PsiReferenceExpression) qualifierExpression).resolve();
            if (containingClass.getManager().areElementsEquivalent(psiElementResolve, containingClass)) {
                return true;
            }
            if (psiElementResolve instanceof PsiTypeParameter) {
                HashSet hashSet = new HashSet();
                for (PsiClassType psiClassType : ((PsiTypeParameter) psiElementResolve).getExtendsListTypes()) {
                    PsiClass psiClassResolve = psiClassType.resolve();
                    if (psiClassResolve != null) {
                        hashSet.add(psiClassResolve);
                    }
                }
                if (hashSet.size() == 1 && hashSet.contains(containingClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean seemsScrambled(PsiClass psiClass) {
        return (psiClass instanceof PsiCompiledElement) && seemsScrambledByStructure(psiClass);
    }

    public static boolean seemsScrambledByStructure(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(30);
        }
        PsiClass psiClassMo6092getContainingClass = psiClass.getContainingClass();
        if ((psiClassMo6092getContainingClass != null && !seemsScrambledByStructure(psiClassMo6092getContainingClass)) || !seemsScrambled(psiClass.get$name())) {
            return false;
        }
        List listFilter = ContainerUtil.filter(psiClass.getMethods(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiReferenceExpressionImpl.lambda$seemsScrambledByStructure$0((PsiMethod) obj);
            }
        });
        return !listFilter.isEmpty() && ContainerUtil.and(listFilter, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiReferenceExpressionImpl.seemsScrambled(((PsiMethod) obj).get$name());
            }
        });
    }

    static /* synthetic */ boolean lambda$seemsScrambledByStructure$0(PsiMethod psiMethod) {
        return !psiMethod.hasModifierProperty("private");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean seemsScrambled(String str) {
        return (str == null || str.isEmpty() || str.length() > 2) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiElement getReferenceNameElement() {
        return findChildByRoleAsPsiElement(53);
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
            $$$reportNull$$$0(31);
        }
        return typeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        ASTNode aSTNodeFindChildByRole = findChildByRole(53);
        return aSTNodeFindChildByRole == null ? super.getTextOffset() : aSTNodeFindChildByRole.getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        if (getQualifierExpression() != null) {
            return renameDirectly(str);
        }
        JavaResolveResult javaResolveResultAdvancedResolve = advancedResolve(false);
        if (javaResolveResultAdvancedResolve.getElement() == null) {
            return renameDirectly(str);
        }
        PsiElement currentFileResolveScope = javaResolveResultAdvancedResolve.getCurrentFileResolveScope();
        if (currentFileResolveScope instanceof PsiImportStaticStatement) {
            PsiImportStaticStatement psiImportStaticStatement = (PsiImportStaticStatement) currentFileResolveScope;
            if (!psiImportStaticStatement.isOnDemand()) {
                LOG.assertTrue(psiImportStaticStatement.getReferenceName() != null);
                if (getManager().areElementsEquivalent(psiImportStaticStatement.getImportReference().resolve(), javaResolveResultAdvancedResolve.getElement())) {
                    return renameDirectly(str);
                }
                PsiClass psiClassResolveTargetClass = psiImportStaticStatement.resolveTargetClass();
                if (psiClassResolveTargetClass == null) {
                    return renameDirectly(str);
                }
                PsiReferenceExpression psiReferenceExpression = (PsiReferenceExpression) replace((PsiReferenceExpression) JavaPsiFacade.getElementFactory(getProject()).createExpressionFromText("X." + str, (PsiElement) this));
                ((PsiReferenceExpression) psiReferenceExpression.getQualifierExpression()).bindToElement(psiClassResolveTargetClass);
                if (psiReferenceExpression == null) {
                    $$$reportNull$$$0(33);
                }
                return psiReferenceExpression;
            }
        }
        return renameDirectly(str);
    }

    private PsiElement renameDirectly(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        PsiElement psiElementFindChildByRoleAsPsiElement = findChildByRoleAsPsiElement(53);
        if (psiElementFindChildByRoleAsPsiElement == null) {
            throw new IncorrectOperationException();
        }
        String text = psiElementFindChildByRoleAsPsiElement.getText();
        if (!"this".equals(text) && !PsiKeyword.SUPER.equals(text) && !str.equals(text)) {
            psiElementFindChildByRoleAsPsiElement.replace(JavaPsiFacade.getElementFactory(getProject()).createIdentifier(str));
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(37);
        }
        CheckUtil.checkWritable(this);
        if (isReferenceTo(psiElement)) {
            return this;
        }
        PsiManagerEx manager = getManager();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(getProject());
        PsiJavaParserFacade parserFacade = javaPsiFacade.getParserFacade();
        if (psiElement instanceof PsiClass) {
            boolean z = JavaCodeStyleSettingsFacade.getInstance(getProject()).useFQClassNames() && isFullyQualified(this);
            PsiClass psiClass = (PsiClass) psiElement;
            String qualifiedName = psiClass.getQualifiedName();
            if (qualifiedName == null) {
                qualifiedName = psiClass.get$name();
            } else {
                if (JavaPsiFacade.getInstance(manager.getProject()).findClass(qualifiedName, getResolveScope()) == null && !z) {
                    return this;
                }
                if (javaPsiFacade.getResolveHelper().resolveReferencedClass(qualifiedName, this) == null && javaPsiFacade.getResolveHelper().resolveReferencedClass(StringUtil.getPackageName(qualifiedName), this) != null) {
                    qualifiedName = psiClass.get$name();
                }
            }
            PsiExpression psiExpressionCreateExpressionFromText = parserFacade.createExpressionFromText(qualifiedName, this);
            getTreeParent().replaceChildInternal(this, (TreeElement) psiExpressionCreateExpressionFromText.getNode());
            return !z ? (PsiExpression) JavaCodeStyleManager.getInstance(manager.getProject()).shortenClassReferences(psiExpressionCreateExpressionFromText, 8192) : psiExpressionCreateExpressionFromText;
        }
        if (psiElement instanceof PsiPackage) {
            String qualifiedName2 = ((PsiPackage) psiElement).getQualifiedName();
            if (qualifiedName2.isEmpty()) {
                throw new IncorrectOperationException();
            }
            PsiExpression psiExpressionCreateExpressionFromText2 = parserFacade.createExpressionFromText(qualifiedName2, this);
            getTreeParent().replaceChildInternal(this, (TreeElement) psiExpressionCreateExpressionFromText2.getNode());
            return psiExpressionCreateExpressionFromText2;
        }
        if ((psiElement instanceof PsiField) || (psiElement instanceof PsiMethod)) {
            PsiMember psiMember = (PsiMember) psiElement;
            if (psiMember.hasModifierProperty("static")) {
                if (!getIsPhysical()) {
                    return this;
                }
                PsiClass psiClassMo6092getContainingClass = psiMember.getContainingClass();
                if (psiClassMo6092getContainingClass == null) {
                    throw new IncorrectOperationException();
                }
                PsiExpression psiExpressionCreateExpressionFromText3 = parserFacade.createExpressionFromText(psiClassMo6092getContainingClass.getQualifiedName() + "." + psiMember.get$name(), this);
                getTreeParent().replaceChildInternal(this, (TreeElement) psiExpressionCreateExpressionFromText3.getNode());
                return psiExpressionCreateExpressionFromText3;
            }
        }
        throw new IncorrectOperationException(psiElement.toString());
    }

    private static boolean isFullyQualified(CompositeElement compositeElement) {
        if (compositeElement == null) {
            $$$reportNull$$$0(38);
        }
        while (true) {
            ASTNode aSTNodeFindChildByRole = compositeElement.findChildByRole(54);
            if (aSTNodeFindChildByRole == null || aSTNodeFindChildByRole.getElementType() != JavaElementType.REFERENCE_EXPRESSION) {
                return false;
            }
            if (((PsiReference) aSTNodeFindChildByRole).resolve() instanceof PsiPackage) {
                return true;
            }
            compositeElement = (CompositeElement) aSTNodeFindChildByRole;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(39);
        }
        if (getChildRole(aSTNode) == 54) {
            deleteChildRange(aSTNode.getPsi(), findChildByType(JavaTokenType.DOT, aSTNode).getPsi());
            TreeElement firstChildNode = getFirstChildNode();
            if (getChildRole(firstChildNode) == 246 && firstChildNode.getFirstChildNode() == null) {
                ASTNode treeNext = firstChildNode.getTreeNext();
                if (PsiImplUtil.isWhitespaceOrComment(treeNext)) {
                    CodeEditUtil.removeChildren(this, treeNext, PsiImplUtil.skipWhitespaceAndComments(treeNext).getTreePrev());
                    return;
                }
                return;
            }
            return;
        }
        if (aSTNode.getElementType() == JavaElementType.REFERENCE_PARAMETER_LIST) {
            replaceChildInternal(aSTNode, createEmptyRefParameterList(getProject()));
        } else {
            super.deleteChildInternal(aSTNode);
        }
    }

    public static TreeElement createEmptyRefParameterList(Project project) {
        if (project == null) {
            $$$reportNull$$$0(40);
        }
        TreeElement treeElement = (TreeElement) ((PsiReferenceParameterList) Objects.requireNonNull(JavaPsiFacade.getElementFactory(project).createReferenceFromText("foo", null).getParameterList())).getNode();
        if (treeElement == null) {
            $$$reportNull$$$0(41);
        }
        return treeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i != 246) {
            switch (i) {
                case 53:
                    TreeElement lastChildNode = getLastChildNode();
                    return (lastChildNode == null || getChildRole(lastChildNode) == i) ? lastChildNode : findChildByType(JavaTokenType.IDENTIFIER);
                case 54:
                    TreeElement firstChildNode = getFirstChildNode();
                    if (firstChildNode == null || getChildRole(firstChildNode) != 54) {
                        return null;
                    }
                    return firstChildNode;
                case 55:
                    return findChildByType(JavaTokenType.DOT);
                default:
                    return null;
            }
        }
        return findChildByType(JavaElementType.REFERENCE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(42);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.DOT) {
            return 55;
        }
        if (elementType == JavaElementType.REFERENCE_PARAMETER_LIST) {
            return 246;
        }
        if (elementType == JavaTokenType.IDENTIFIER || elementType == JavaTokenType.THIS_KEYWORD || elementType == JavaTokenType.SUPER_KEYWORD) {
            return 53;
        }
        return ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 54 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        if (getReferenceNameElement() != null) {
            return this;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(43);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        TextRange textRangeCalcRangeInElement = PsiJavaCodeReferenceElementImpl.calcRangeInElement(this);
        if (textRangeCalcRangeInElement == null) {
            $$$reportNull$$$0(45);
        }
        return textRangeCalcRangeInElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return advancedResolve(false).getElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceJavaCodeReference
    public String getClassNameText() {
        String qualifiedClassName = this.myCachedQName;
        if (qualifiedClassName == null) {
            qualifiedClassName = PsiNameHelper.getQualifiedClassName(getCachedNormalizedText(), false);
            this.myCachedQName = qualifiedClassName;
        }
        if (qualifiedClassName == null) {
            $$$reportNull$$$0(46);
        }
        return qualifiedClassName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceJavaCodeReference
    public void fullyQualify(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(47);
        }
        JavaSourceUtil.fullyQualifyReference(this, psiClass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public boolean isQualified() {
        return getChildRole(getFirstChildNode()) == 54;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        String canonicalText = getCanonicalText();
        if (canonicalText == null) {
            $$$reportNull$$$0(48);
        }
        return canonicalText;
    }

    private String getCachedNormalizedText() {
        String referenceText = this.myCachedNormalizedText;
        if (referenceText == null) {
            referenceText = JavaSourceUtil.getReferenceText(this);
            this.myCachedNormalizedText = referenceText;
        }
        if (referenceText == null) {
            $$$reportNull$$$0(49);
        }
        return referenceText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiReferenceExpression:" + getText();
    }
}
