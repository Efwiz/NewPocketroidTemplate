package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.codeInsight.javadoc.JavaDocUtil;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;
import org.jetbrains.kotlin.com.intellij.patterns.PsiJavaElementPattern;
import org.jetbrains.kotlin.com.intellij.patterns.PsiJavaPatterns;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotatedJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade;
import org.jetbrains.kotlin.com.intellij.psi.filters.AndFilter;
import org.jetbrains.kotlin.com.intellij.psi.filters.ConstructorFilter;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.filters.NotFilter;
import org.jetbrains.kotlin.com.intellij.psi.filters.OrFilter;
import org.jetbrains.kotlin.com.intellij.psi.filters.element.ModifierFilter;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ClassResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveClassUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.VariableResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ICodeFragmentElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassFilter;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaCodeReferenceElementImpl extends CompositePsiElement implements PsiAnnotatedJavaCodeReferenceElement, SourceJavaCodeReference {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiJavaCodeReferenceElementImpl.class);
    private volatile String myCachedNormalizedText;
    private volatile String myCachedQName;
    private final int myHC;
    private volatile Kind myKindWhenDummy;

    public enum Kind {
        CLASS_NAME_KIND,
        PACKAGE_NAME_KIND,
        CLASS_OR_PACKAGE_NAME_KIND,
        CLASS_FQ_NAME_KIND,
        CLASS_FQ_OR_PACKAGE_NAME_KIND,
        CLASS_IN_QUALIFIED_NEW_KIND
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 39:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 60:
            case 61:
            case 62:
            case 67:
            case 68:
            case 69:
            case 72:
            case 76:
            case 77:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 28:
            case 31:
            case 32:
            case 38:
            case 40:
            case 41:
            case 58:
            case 59:
            case 63:
            case 64:
            case 65:
            case 66:
            case 70:
            case 71:
            case 73:
            case 74:
            case 75:
            case 78:
            case 80:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 39:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 60:
            case 61:
            case 62:
            case 67:
            case 68:
            case 69:
            case 72:
            case 76:
            case 77:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                i2 = 2;
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 28:
            case 31:
            case 32:
            case 38:
            case 40:
            case 41:
            case 58:
            case 59:
            case 63:
            case 64:
            case 65:
            case 66:
            case 70:
            case 71:
            case 73:
            case 74:
            case 75:
            case 78:
            case 80:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 21:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 2:
            case 24:
            case 41:
            case 66:
            case 70:
            case 75:
                objArr[0] = "containingFile";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 39:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 60:
            case 61:
            case 62:
            case 67:
            case 68:
            case 69:
            case 72:
            case 76:
            case 77:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaCodeReferenceElementImpl";
                break;
            case 22:
            case 23:
                objArr[0] = "child";
                break;
            case 28:
                objArr[0] = "refElement";
                break;
            case 31:
                objArr[0] = "qualifiedName";
                break;
            case 32:
                objArr[0] = "referenceElement";
                break;
            case 38:
                objArr[0] = "psiClass";
                break;
            case 40:
            case 64:
            default:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 58:
                objArr[0] = "newElementName";
                break;
            case 59:
            case 63:
            case 73:
            case 74:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 65:
                objArr[0] = "aClass";
                break;
            case 71:
                objArr[0] = "aPackage";
                break;
            case 78:
                objArr[0] = "targetClass";
                break;
            case 80:
                objArr[0] = "processor";
                break;
            case 86:
                objArr[0] = "visitor";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[1] = "getKindEnum";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 28:
            case 31:
            case 32:
            case 38:
            case 40:
            case 41:
            case 58:
            case 59:
            case 63:
            case 64:
            case 65:
            case 66:
            case 70:
            case 71:
            case 73:
            case 74:
            case 75:
            case 78:
            case 80:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaCodeReferenceElementImpl";
                break;
            case 25:
            case 26:
            case 27:
                objArr[1] = "getCanonicalText";
                break;
            case 29:
            case 30:
                objArr[1] = "calcRangeInElement";
                break;
            case 33:
            case 34:
            case 35:
                objArr[1] = "tryClassResult";
                break;
            case 36:
                objArr[1] = "advancedResolve";
                break;
            case 37:
                objArr[1] = "multiResolve";
                break;
            case 39:
                objArr[1] = "updateSubstitutor";
                break;
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                objArr[1] = "resolve";
                break;
            case 60:
            case 61:
            case 62:
                objArr[1] = "bindToElement";
                break;
            case 67:
            case 68:
                objArr[1] = "bindToClass";
                break;
            case 69:
                objArr[1] = "getAnnotations";
                break;
            case 72:
                objArr[1] = "bindToPackage";
                break;
            case 76:
                objArr[1] = "getNormalizedText";
                break;
            case 77:
                objArr[1] = "getClassNameText";
                break;
            case 79:
                objArr[1] = "getVariants";
                break;
            case 81:
            case 82:
                objArr[1] = "getUnfinishedMethodTypeParameters";
                break;
            case 83:
            case 84:
                objArr[1] = "getTypeParameters";
                break;
            case 85:
                objArr[1] = "getElement";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "isDummy";
                break;
            case 2:
                objArr[2] = "getKindEnum";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 39:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 60:
            case 61:
            case 62:
            case 67:
            case 68:
            case 69:
            case 72:
            case 76:
            case 77:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                break;
            case 21:
                objArr[2] = "isCodeFragmentType";
                break;
            case 22:
                objArr[2] = "deleteChildInternal";
                break;
            case 23:
                objArr[2] = "getChildRole";
                break;
            case 24:
                objArr[2] = "getCanonicalText";
                break;
            case 28:
                objArr[2] = "calcRangeInElement";
                break;
            case 31:
            case 32:
                objArr[2] = "tryClassResult";
                break;
            case 38:
                objArr[2] = "updateSubstitutor";
                break;
            case 40:
            case 41:
                objArr[2] = "resolve";
                break;
            case 58:
                objArr[2] = "handleElementRename";
                break;
            case 59:
                objArr[2] = "bindToElement";
                break;
            case 63:
            case 64:
                objArr[2] = "cannotBindError";
                break;
            case 65:
            case 66:
                objArr[2] = "bindToClass";
                break;
            case 70:
                objArr[2] = "isFullyQualified";
                break;
            case 71:
                objArr[2] = "bindToPackage";
                break;
            case 73:
            case 74:
            case 75:
                objArr[2] = "isReferenceTo";
                break;
            case 78:
                objArr[2] = "fullyQualify";
                break;
            case 80:
                objArr[2] = "processVariants";
                break;
            case 86:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "setKindWhenDummy";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 39:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 60:
            case 61:
            case 62:
            case 67:
            case 68:
            case 69:
            case 72:
            case 76:
            case 77:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                throw new IllegalStateException(str2);
            case 21:
            case 22:
            case 23:
            case 24:
            case 28:
            case 31:
            case 32:
            case 38:
            case 40:
            case 41:
            case 58:
            case 59:
            case 63:
            case 64:
            case 65:
            case 66:
            case 70:
            case 71:
            case 73:
            case 74:
            case 75:
            case 78:
            case 80:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public final PsiElement getPsiElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    public PsiJavaCodeReferenceElementImpl() {
        super(JavaElementType.JAVA_CODE_REFERENCE);
        this.myKindWhenDummy = Kind.CLASS_NAME_KIND;
        int i = ourHC;
        ourHC = i + 1;
        this.myHC = i;
    }

    public final int hashCode() {
        return this.myHC;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        ASTNode referenceNameNode = getReferenceNameNode();
        return referenceNameNode != null ? referenceNameNode.getStartOffset() : super.getTextOffset();
    }

    public void setKindWhenDummy(Kind kind) {
        if (kind == null) {
            $$$reportNull$$$0(0);
        }
        IElementType elementType = getTreeParent().getElementType();
        LOG.assertTrue(isDummy(elementType), elementType);
        this.myKindWhenDummy = kind;
    }

    private static boolean isDummy(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(1);
        }
        return iElementType == TokenType.DUMMY_HOLDER || iElementType == JavaElementType.DUMMY_ELEMENT;
    }

    public Kind getKindEnum(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (!psiFile.isValid()) {
            PsiUtilCore.ensureValid(this);
        }
        CompositeElement treeParent = getTreeParent();
        IElementType elementType = treeParent.getElementType();
        if (isDummy(elementType)) {
            Kind kind = this.myKindWhenDummy;
            if (kind == null) {
                $$$reportNull$$$0(3);
            }
            return kind;
        }
        if (elementType == JavaElementType.TYPE) {
            Kind kind2 = treeParent.getTreeParent().getPsi() instanceof PsiTypeCodeFragment ? Kind.CLASS_OR_PACKAGE_NAME_KIND : Kind.CLASS_NAME_KIND;
            if (kind2 == null) {
                $$$reportNull$$$0(4);
            }
            return kind2;
        }
        if (elementType == JavaElementType.EXTENDS_LIST || elementType == JavaElementType.IMPLEMENTS_LIST || elementType == JavaElementType.PERMITS_LIST || elementType == JavaElementType.EXTENDS_BOUND_LIST || elementType == JavaElementType.THROWS_LIST || elementType == JavaElementType.THIS_EXPRESSION || elementType == JavaElementType.SUPER_EXPRESSION || elementType == JavaDocElementType.DOC_METHOD_OR_FIELD_REF || elementType == JavaDocElementType.DOC_TAG_VALUE_ELEMENT || elementType == JavaElementType.REFERENCE_PARAMETER_LIST || elementType == JavaElementType.ANNOTATION || elementType == JavaElementType.USES_STATEMENT || elementType == JavaElementType.PROVIDES_STATEMENT || elementType == JavaElementType.PROVIDES_WITH_LIST) {
            Kind kind3 = isQualified() ? Kind.CLASS_OR_PACKAGE_NAME_KIND : Kind.CLASS_NAME_KIND;
            if (kind3 == null) {
                $$$reportNull$$$0(5);
            }
            return kind3;
        }
        if (elementType == JavaElementType.NEW_EXPRESSION) {
            Kind kind4 = treeParent.findChildByRole(54) != null ? Kind.CLASS_IN_QUALIFIED_NEW_KIND : Kind.CLASS_NAME_KIND;
            if (kind4 == null) {
                $$$reportNull$$$0(6);
            }
            return kind4;
        }
        if (elementType == JavaElementType.ANONYMOUS_CLASS) {
            if (treeParent.getChildRole(this) == 78) {
                CompositeElement treeParent2 = treeParent.getTreeParent();
                IElementType elementType2 = treeParent2.getElementType();
                LOG.assertTrue(elementType2 == JavaElementType.NEW_EXPRESSION, elementType2);
                Kind kind5 = treeParent2.findChildByRole(54) != null ? Kind.CLASS_IN_QUALIFIED_NEW_KIND : Kind.CLASS_NAME_KIND;
                if (kind5 == null) {
                    $$$reportNull$$$0(7);
                }
                return kind5;
            }
            Kind kind6 = Kind.CLASS_OR_PACKAGE_NAME_KIND;
            if (kind6 == null) {
                $$$reportNull$$$0(8);
            }
            return kind6;
        }
        if (elementType == JavaElementType.PACKAGE_STATEMENT || elementType == JavaElementType.EXPORTS_STATEMENT || elementType == JavaElementType.OPENS_STATEMENT) {
            Kind kind7 = Kind.PACKAGE_NAME_KIND;
            if (kind7 == null) {
                $$$reportNull$$$0(9);
            }
            return kind7;
        }
        if (elementType == JavaElementType.IMPORT_STATEMENT) {
            PsiElement psi = treeParent.getPsi();
            if (psi instanceof PsiImportStatement) {
                Kind kind8 = ((PsiImportStatement) psi).isOnDemand() ? Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND : Kind.CLASS_FQ_NAME_KIND;
                if (kind8 == null) {
                    $$$reportNull$$$0(10);
                }
                return kind8;
            }
        }
        if (elementType == JavaElementType.IMPORT_STATIC_STATEMENT) {
            Kind kind9 = Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND;
            if (kind9 == null) {
                $$$reportNull$$$0(11);
            }
            return kind9;
        }
        if (elementType == JavaElementType.JAVA_CODE_REFERENCE) {
            Kind kindEnum = ((PsiJavaCodeReferenceElementImpl) treeParent).getKindEnum(psiFile);
            if (kindEnum == Kind.CLASS_NAME_KIND) {
                Kind kind10 = Kind.CLASS_OR_PACKAGE_NAME_KIND;
                if (kind10 == null) {
                    $$$reportNull$$$0(12);
                }
                return kind10;
            }
            if (kindEnum != Kind.CLASS_FQ_NAME_KIND) {
                if (kindEnum == null) {
                    $$$reportNull$$$0(14);
                }
                return kindEnum;
            }
            Kind kind11 = Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND;
            if (kind11 == null) {
                $$$reportNull$$$0(13);
            }
            return kind11;
        }
        if (elementType == JavaElementType.CLASS || elementType == JavaElementType.PARAMETER_LIST || elementType == TokenType.ERROR_ELEMENT) {
            Kind kind12 = Kind.CLASS_OR_PACKAGE_NAME_KIND;
            if (kind12 == null) {
                $$$reportNull$$$0(15);
            }
            return kind12;
        }
        if (elementType == JavaElementType.IMPORT_STATIC_REFERENCE) {
            Kind kind13 = Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND;
            if (kind13 == null) {
                $$$reportNull$$$0(16);
            }
            return kind13;
        }
        if (elementType == JavaDocElementType.DOC_TAG || elementType == JavaDocElementType.DOC_INLINE_TAG || elementType == JavaDocElementType.DOC_REFERENCE_HOLDER || elementType == JavaDocElementType.DOC_TYPE_HOLDER) {
            if (JavaDocUtil.isInsidePackageInfo((PsiDocComment) PsiTreeUtil.getParentOfType(this, PsiDocComment.class))) {
                Kind kind14 = Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND;
                if (kind14 == null) {
                    $$$reportNull$$$0(17);
                }
                return kind14;
            }
            Kind kind15 = Kind.CLASS_OR_PACKAGE_NAME_KIND;
            if (kind15 == null) {
                $$$reportNull$$$0(18);
            }
            return kind15;
        }
        if (isCodeFragmentType(elementType)) {
            Kind kind16 = ((PsiJavaCodeReferenceCodeFragment) treeParent.getPsi()).isClassesAccepted() ? Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND : Kind.PACKAGE_NAME_KIND;
            if (kind16 == null) {
                $$$reportNull$$$0(19);
            }
            return kind16;
        }
        diagnoseUnknownParent();
        Kind kind17 = Kind.CLASS_NAME_KIND;
        if (kind17 == null) {
            $$$reportNull$$$0(20);
        }
        return kind17;
    }

    private void diagnoseUnknownParent() {
        CompositeElement treeParent = getTreeParent();
        StringBuilder sbAppend = new StringBuilder("Unknown parent for java code reference: '").append(treeParent).append("'; Type: ").append(treeParent.getElementType()).append(";\n Class: ").append(treeParent.getClass()).append('\n');
        while (treeParent != null && (treeParent.getPsi() instanceof PsiExpression)) {
            treeParent = treeParent.getTreeParent();
            sbAppend.append(" Parent: '").append(treeParent).append("'; \n");
        }
        if (treeParent != null) {
            sbAppend.append(DebugUtil.treeToString(treeParent, true));
        }
        LOG.error(sbAppend.toString());
    }

    private static boolean isCodeFragmentType(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(21);
        }
        return iElementType == TokenType.CODE_FRAGMENT || (iElementType instanceof ICodeFragmentElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(22);
        }
        if (getChildRole(aSTNode) == 54) {
            deleteChildRange(aSTNode.getPsi(), findChildByType(JavaTokenType.DOT, aSTNode).getPsi());
            ASTNode aSTNodeFindChildByRole = findChildByRole(53);
            PsiElement prevSibling = aSTNodeFindChildByRole.getPsi().getPrevSibling();
            if (prevSibling != null) {
                PsiModifierList psiModifierListFindNeighbourModifierList = PsiImplUtil.findNeighbourModifierList(this);
                if (psiModifierListFindNeighbourModifierList != null) {
                    psiModifierListFindNeighbourModifierList.addRange(getFirstChild(), prevSibling);
                } else {
                    getMyParent().addRangeBefore(getFirstChild(), prevSibling, this);
                }
                if (aSTNodeFindChildByRole != getFirstChild()) {
                    deleteChildRange(getFirstChild(), aSTNodeFindChildByRole.getPsi().getPrevSibling());
                    return;
                }
                return;
            }
            return;
        }
        if (aSTNode.getElementType() == JavaElementType.REFERENCE_PARAMETER_LIST) {
            replaceChildInternal(aSTNode, PsiReferenceExpressionImpl.createEmptyRefParameterList(getProject()));
        } else {
            super.deleteChildInternal(aSTNode);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public final ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i), Integer.valueOf(i));
        if (i != 246) {
            switch (i) {
                case 53:
                    return TreeUtil.findChildBackward(this, JavaTokenType.IDENTIFIER);
                case 54:
                    return findChildByType(JavaElementType.JAVA_CODE_REFERENCE);
                case 55:
                    return findChildByType(JavaTokenType.DOT);
                default:
                    return null;
            }
        }
        TreeElement lastChildNode = getLastChildNode();
        if (lastChildNode.getElementType() == JavaElementType.REFERENCE_PARAMETER_LIST) {
            return lastChildNode;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public final int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(23);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.REFERENCE_PARAMETER_LIST) {
            return 246;
        }
        if (elementType == JavaElementType.JAVA_CODE_REFERENCE) {
            return 54;
        }
        if (elementType == JavaTokenType.DOT) {
            return 55;
        }
        return elementType == JavaTokenType.IDENTIFIER ? 53 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        return getCanonicalText(false, null, getContainingFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotatedJavaCodeReferenceElement
    public String getCanonicalText(boolean z, PsiAnnotation[] psiAnnotationArr) {
        return getCanonicalText(z, psiAnnotationArr, getContainingFile());
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind;

        static {
            int[] iArr = new int[Kind.values().length];
            $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind = iArr;
            try {
                iArr[Kind.CLASS_NAME_KIND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[Kind.CLASS_OR_PACKAGE_NAME_KIND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[Kind.CLASS_IN_QUALIFIED_NEW_KIND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[Kind.PACKAGE_NAME_KIND.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[Kind.CLASS_FQ_NAME_KIND.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private String getCanonicalText(boolean z, PsiAnnotation[] psiAnnotationArr, PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(24);
        }
        Kind kindEnum = getKindEnum(psiFile);
        String packageName = null;
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[kindEnum.ordinal()]) {
            case 1:
            case 2:
            case 3:
                JavaResolveResult[] javaResolveResultArrMultiResolveImpl = PsiImplUtil.multiResolveImpl(psiFile.getProject(), psiFile, this, false, OurGenericsResolver.INSTANCE);
                PsiElement element = javaResolveResultArrMultiResolveImpl.length == 1 ? javaResolveResultArrMultiResolveImpl[0].getElement() : null;
                if (element instanceof PsiClass) {
                    StringBuilder sb = new StringBuilder();
                    PsiClass psiClass = (PsiClass) element;
                    PsiElement qualifier = getQualifier();
                    if (qualifier instanceof PsiJavaCodeReferenceElementImpl) {
                        packageName = ((PsiJavaCodeReferenceElementImpl) qualifier).getCanonicalText(z, psiAnnotationArr, psiFile);
                        psiAnnotationArr = null;
                    } else {
                        String qualifiedName = psiClass.getQualifiedName();
                        if (qualifiedName != null) {
                            packageName = StringUtil.getPackageName(qualifiedName);
                        }
                    }
                    if (!StringUtil.isEmpty(packageName)) {
                        sb.append(packageName);
                        sb.append('.');
                    }
                    if (z) {
                        PsiNameHelper.appendAnnotations(sb, (List<? extends PsiAnnotation>) (psiAnnotationArr != null ? Arrays.asList(psiAnnotationArr) : getAnnotations()), true);
                    }
                    sb.append(psiClass.getMyName());
                    PsiNameHelper.appendTypeArgs(sb, getTypeParameters(), true, z);
                    String string = sb.toString();
                    if (string == null) {
                        $$$reportNull$$$0(25);
                    }
                    return string;
                }
                if (element instanceof PsiPackage) {
                    String qualifiedName2 = ((PsiPackage) element).getQualifiedName();
                    if (qualifiedName2 == null) {
                        $$$reportNull$$$0(26);
                    }
                    return qualifiedName2;
                }
                LOG.assertTrue(element == null, element);
                return getNormalizedText();
            case 4:
            case 5:
            case 6:
                return getNormalizedText();
            default:
                LOG.error(kindEnum);
                $$$reportNull$$$0(27);
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public final PsiElement resolve() {
        return advancedResolve(false).getElement();
    }

    public static TextRange calcRangeInElement(CompositePsiElement compositePsiElement) {
        if (compositePsiElement == null) {
            $$$reportNull$$$0(28);
        }
        TreeElement treeElement = (TreeElement) compositePsiElement.findChildByRole(53);
        if (treeElement == null) {
            TreeElement treeElement2 = (TreeElement) compositePsiElement.findChildByRole(55);
            if (treeElement2 == null) {
                throw new IllegalStateException(compositePsiElement.toString());
            }
            TextRange textRangeFrom = TextRange.from(treeElement2.getStartOffsetInParent() + treeElement2.getTextLength(), 0);
            if (textRangeFrom == null) {
                $$$reportNull$$$0(29);
            }
            return textRangeFrom;
        }
        TextRange textRangeFrom2 = TextRange.from(treeElement.getStartOffsetInParent(), treeElement.getTextLength());
        if (textRangeFrom2 == null) {
            $$$reportNull$$$0(30);
        }
        return textRangeFrom2;
    }

    private static final class OurGenericsResolver implements ResolveCache.PolyVariantContextResolver<PsiJavaReference> {
        private static final OurGenericsResolver INSTANCE = new OurGenericsResolver();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "containingFile";
            } else if (i != 2) {
                objArr[0] = "ref";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaCodeReferenceElementImpl$OurGenericsResolver";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaCodeReferenceElementImpl$OurGenericsResolver";
            } else {
                objArr[1] = "resolve";
            }
            if (i != 2) {
                objArr[2] = "resolve";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        private OurGenericsResolver() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.PolyVariantContextResolver
        public ResolveResult[] resolve(PsiJavaReference psiJavaReference, PsiFile psiFile, boolean z) {
            if (psiJavaReference == null) {
                $$$reportNull$$$0(0);
            }
            if (psiFile == null) {
                $$$reportNull$$$0(1);
            }
            PsiJavaCodeReferenceElementImpl psiJavaCodeReferenceElementImpl = (PsiJavaCodeReferenceElementImpl) psiJavaReference;
            Kind kindEnum = psiJavaCodeReferenceElementImpl.getKindEnum(psiFile);
            JavaResolveResult[] javaResolveResultArrResolve = psiJavaCodeReferenceElementImpl.resolve(kindEnum, psiFile);
            if (z && javaResolveResultArrResolve.length == 0 && kindEnum != Kind.CLASS_FQ_NAME_KIND && kindEnum != Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND) {
                VariableResolverProcessor variableResolverProcessor = new VariableResolverProcessor(psiJavaCodeReferenceElementImpl, psiFile);
                PsiScopesUtil.resolveAndWalk(variableResolverProcessor, psiJavaCodeReferenceElementImpl, null, true);
                javaResolveResultArrResolve = variableResolverProcessor.getResult();
                if (javaResolveResultArrResolve.length == 0 && kindEnum == Kind.CLASS_NAME_KIND) {
                    javaResolveResultArrResolve = psiJavaCodeReferenceElementImpl.resolve(Kind.PACKAGE_NAME_KIND, psiFile);
                }
            }
            if (javaResolveResultArrResolve.length == 0 && (kindEnum == Kind.CLASS_OR_PACKAGE_NAME_KIND || kindEnum == Kind.CLASS_NAME_KIND)) {
                javaResolveResultArrResolve = PsiJavaCodeReferenceElementImpl.tryClassResult(psiJavaCodeReferenceElementImpl.getClassNameText(), psiJavaCodeReferenceElementImpl);
            }
            JavaResolveUtil.substituteResults(psiJavaCodeReferenceElementImpl, javaResolveResultArrResolve);
            if (javaResolveResultArrResolve == null) {
                $$$reportNull$$$0(2);
            }
            return javaResolveResultArrResolve;
        }
    }

    public static JavaResolveResult[] tryClassResult(String str, PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        if (str == null) {
            $$$reportNull$$$0(31);
        }
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(32);
        }
        PsiElement qualifier = psiJavaCodeReferenceElement.getQualifier();
        Project project = psiJavaCodeReferenceElement.getProject();
        if (qualifier instanceof PsiJavaCodeReferenceElement) {
            if (ResolveClassUtil.resolveClass((PsiJavaCodeReferenceElement) qualifier, psiJavaCodeReferenceElement.getContainingFile()) != null) {
                JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr == null) {
                    $$$reportNull$$$0(33);
                }
                return javaResolveResultArr;
            }
            PsiClass psiClassFindClass = JavaPsiFacade.getInstance(project).findClass(str, psiJavaCodeReferenceElement.getResolveScope());
            if (psiClassFindClass != null) {
                return new JavaResolveResult[]{new CandidateInfo((PsiElement) psiClassFindClass, PsiSubstitutor.EMPTY, (PsiElement) psiJavaCodeReferenceElement, false)};
            }
        }
        JavaResolveResult[] javaResolveResultArr2 = JavaResolveResult.EMPTY_ARRAY;
        if (javaResolveResultArr2 == null) {
            $$$reportNull$$$0(35);
        }
        return javaResolveResultArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean z) {
        JavaResolveResult[] javaResolveResultArrMultiResolve = multiResolve(z);
        JavaResolveResult javaResolveResult = javaResolveResultArrMultiResolve.length == 1 ? javaResolveResultArrMultiResolve[0] : JavaResolveResult.EMPTY;
        if (javaResolveResult == null) {
            $$$reportNull$$$0(36);
        }
        return javaResolveResult;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        JavaResolveResult[] javaResolveResultArrMultiResolveImpl = PsiImplUtil.multiResolveImpl(this, z, OurGenericsResolver.INSTANCE);
        if (javaResolveResultArrMultiResolveImpl == null) {
            $$$reportNull$$$0(37);
        }
        return javaResolveResultArrMultiResolveImpl;
    }

    private PsiSubstitutor updateSubstitutor(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(38);
        }
        PsiSubstitutor psiSubstitutorPutAll = PsiSubstitutor.EMPTY.putAll(psiClass, getTypeParameters());
        if (psiSubstitutorPutAll == null) {
            $$$reportNull$$$0(39);
        }
        return psiSubstitutorPutAll;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JavaResolveResult[] resolve(Kind kind, PsiFile psiFile) {
        JavaResolveResult[] result;
        JavaResolveResult[] javaResolveResultArrResolve;
        PsiClass psiClassFindClass;
        if (kind == null) {
            $$$reportNull$$$0(40);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(41);
        }
        ProgressManager.checkCanceled();
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[kind.ordinal()]) {
            case 1:
                PsiElement referenceNameElement = getReferenceNameElement();
                if (!(referenceNameElement instanceof PsiIdentifier)) {
                    JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
                    if (javaResolveResultArr == null) {
                        $$$reportNull$$$0(51);
                    }
                    return javaResolveResultArr;
                }
                ClassResolverProcessor classResolverProcessor = new ClassResolverProcessor(referenceNameElement.getText(), this, psiFile);
                PsiScopesUtil.resolveAndWalk(classResolverProcessor, this, null);
                result = classResolverProcessor.getResult();
                if (result == null) {
                    $$$reportNull$$$0(52);
                }
                return result;
            case 2:
            case 6:
                Kind kind2 = kind == Kind.CLASS_OR_PACKAGE_NAME_KIND ? Kind.CLASS_NAME_KIND : Kind.CLASS_FQ_NAME_KIND;
                PsiImportStatement psiImportStatement = (PsiImportStatement) PsiTreeUtil.getParentOfType(this, PsiImportStatement.class);
                if (psiImportStatement != null && (!psiImportStatement.isOnDemand() || !isQualified())) {
                    javaResolveResultArrResolve = resolve(Kind.PACKAGE_NAME_KIND, psiFile);
                    if (javaResolveResultArrResolve.length == 0) {
                        javaResolveResultArrResolve = resolve(kind2, psiFile);
                    }
                } else {
                    javaResolveResultArrResolve = resolve(kind2, psiFile);
                    if (javaResolveResultArrResolve.length == 1 && !javaResolveResultArrResolve[0].isAccessible()) {
                        JavaResolveResult[] javaResolveResultArrResolve2 = resolve(Kind.PACKAGE_NAME_KIND, psiFile);
                        if (javaResolveResultArrResolve2.length != 0) {
                            javaResolveResultArrResolve = javaResolveResultArrResolve2;
                        }
                    } else if (javaResolveResultArrResolve.length == 0) {
                        javaResolveResultArrResolve = resolve(Kind.PACKAGE_NAME_KIND, psiFile);
                    }
                }
                if (javaResolveResultArrResolve == null) {
                    $$$reportNull$$$0(56);
                }
                return javaResolveResultArrResolve;
            case 3:
                PsiElement parent = getMyParent();
                if (parent instanceof JavaDummyHolder) {
                    parent = parent.getContext();
                }
                if (parent instanceof PsiAnonymousClass) {
                    parent = parent.getMyParent();
                }
                if (parent instanceof PsiNewExpression) {
                    PsiExpression qualifier = ((PsiNewExpression) parent).getQualifier();
                    LOG.assertTrue(qualifier != null);
                    PsiType type = qualifier.getType();
                    if (type == null) {
                        JavaResolveResult[] javaResolveResultArr2 = JavaResolveResult.EMPTY_ARRAY;
                        if (javaResolveResultArr2 == null) {
                            $$$reportNull$$$0(46);
                        }
                        return javaResolveResultArr2;
                    }
                    if (!(type instanceof PsiClassType)) {
                        JavaResolveResult[] javaResolveResultArr3 = JavaResolveResult.EMPTY_ARRAY;
                        if (javaResolveResultArr3 == null) {
                            $$$reportNull$$$0(47);
                        }
                        return javaResolveResultArr3;
                    }
                    PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(type);
                    PsiElement element = classResolveResultResolveGenericsClassInType.getElement();
                    if (element == null) {
                        JavaResolveResult[] javaResolveResultArr4 = JavaResolveResult.EMPTY_ARRAY;
                        if (javaResolveResultArr4 == null) {
                            $$$reportNull$$$0(48);
                        }
                        return javaResolveResultArr4;
                    }
                    PsiElement referenceNameElement2 = getReferenceNameElement();
                    if (!(referenceNameElement2 instanceof PsiIdentifier)) {
                        JavaResolveResult[] javaResolveResultArr5 = JavaResolveResult.EMPTY_ARRAY;
                        if (javaResolveResultArr5 == null) {
                            $$$reportNull$$$0(49);
                        }
                        return javaResolveResultArr5;
                    }
                    ClassResolverProcessor classResolverProcessor2 = new ClassResolverProcessor(referenceNameElement2.getText(), this, psiFile);
                    element.processDeclarations(classResolverProcessor2, ResolveState.initial().put(PsiSubstitutor.KEY, classResolveResultResolveGenericsClassInType.getSubstitutor()), this, this);
                    JavaResolveResult[] result2 = classResolverProcessor2.getResult();
                    if (result2 == null) {
                        $$$reportNull$$$0(50);
                    }
                    return result2;
                }
                if (parent instanceof PsiJavaCodeReferenceElement) {
                    JavaResolveResult[] javaResolveResultArr6 = JavaResolveResult.EMPTY_ARRAY;
                    if (javaResolveResultArr6 == null) {
                        $$$reportNull$$$0(44);
                    }
                    return javaResolveResultArr6;
                }
                LOG.error("Invalid java reference: " + parent);
                JavaResolveResult[] javaResolveResultArr7 = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr7 == null) {
                    $$$reportNull$$$0(45);
                }
                return javaResolveResultArr7;
            case 4:
                String normalizedText = getNormalizedText();
                Project project = getManager().getProject();
                PsiPackage psiPackageFindPackage = JavaPsiFacade.getInstance(project).findPackage(normalizedText);
                if (psiPackageFindPackage != null && psiPackageFindPackage.isValid()) {
                    return new JavaResolveResult[]{new CandidateInfo((PsiElement) psiPackageFindPackage, PsiSubstitutor.EMPTY, (PsiElement) this, false)};
                }
                if (JavaPsiFacade.getInstance(project).isPartOfPackagePrefix(normalizedText)) {
                    JavaResolveResult[] javaResolveResultArr8 = CandidateInfo.RESOLVE_RESULT_FOR_PACKAGE_PREFIX_PACKAGE;
                    if (javaResolveResultArr8 == null) {
                        $$$reportNull$$$0(54);
                    }
                    return javaResolveResultArr8;
                }
                JavaResolveResult[] javaResolveResultArr9 = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr9 == null) {
                    $$$reportNull$$$0(55);
                }
                return javaResolveResultArr9;
            case 5:
                String normalizedText2 = getNormalizedText();
                if (!StringUtil.isEmptyOrSpaces(normalizedText2) && (psiClassFindClass = JavaPsiFacade.getInstance(psiFile.getProject()).findClass(normalizedText2, getResolveScope())) != null) {
                    return new JavaResolveResult[]{new CandidateInfo((PsiElement) psiClassFindClass, updateSubstitutor(psiClassFindClass), (PsiElement) this, false)};
                }
                JavaResolveResult[] javaResolveResultArr10 = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr10 == null) {
                    $$$reportNull$$$0(43);
                }
                return javaResolveResultArr10;
            default:
                LOG.error(this);
                result = JavaResolveResult.EMPTY_ARRAY;
                if (result == null) {
                    $$$reportNull$$$0(57);
                }
                return result;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public final PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(58);
        }
        PsiElement referenceNameElement = getReferenceNameElement();
        if (referenceNameElement == null) {
            throw new IncorrectOperationException();
        }
        referenceNameElement.replace(JavaPsiFacade.getElementFactory(getProject()).createIdentifier(str));
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(59);
        }
        PsiFile containingFile = getContainingFile();
        CheckUtil.checkWritable(containingFile);
        if (isReferenceTo(psiElement)) {
            return this;
        }
        Kind kindEnum = getKindEnum(containingFile);
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[kindEnum.ordinal()]) {
            case 1:
            case 5:
                if (!(psiElement instanceof PsiClass)) {
                    throw cannotBindError(psiElement, kindEnum);
                }
                return bindToClass((PsiClass) psiElement, containingFile);
            case 2:
            case 6:
                if (psiElement instanceof PsiClass) {
                    return bindToClass((PsiClass) psiElement, containingFile);
                }
                if (psiElement instanceof PsiPackage) {
                    return bindToPackage((PsiPackage) psiElement);
                }
                throw cannotBindError(psiElement, kindEnum);
            case 3:
                if (psiElement instanceof PsiClass) {
                    PsiClass psiClass = (PsiClass) psiElement;
                    String name2 = psiClass.getMyName();
                    if (name2 == null) {
                        throw new IncorrectOperationException(psiClass.toString());
                    }
                    PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceFromText = JavaPsiFacade.getInstance(containingFile.getProject()).getParserFacade().createReferenceFromText(name2, getMyParent());
                    getTreeParent().replaceChildInternal(this, (TreeElement) psiJavaCodeReferenceElementCreateReferenceFromText.getNode());
                    if (psiJavaCodeReferenceElementCreateReferenceFromText == null) {
                        $$$reportNull$$$0(61);
                    }
                    return psiJavaCodeReferenceElementCreateReferenceFromText;
                }
                throw cannotBindError(psiElement, kindEnum);
            case 4:
                if (!(psiElement instanceof PsiPackage)) {
                    throw cannotBindError(psiElement, kindEnum);
                }
                return bindToPackage((PsiPackage) psiElement);
            default:
                LOG.error(kindEnum);
                $$$reportNull$$$0(62);
                return null;
        }
    }

    private static IncorrectOperationException cannotBindError(PsiElement psiElement, Kind kind) {
        if (psiElement == null) {
            $$$reportNull$$$0(63);
        }
        if (kind == null) {
            $$$reportNull$$$0(64);
        }
        return new IncorrectOperationException("Cannot bind to " + psiElement + " of kind: " + kind);
    }

    private PsiElement bindToClass(PsiClass psiClass, PsiFile psiFile) throws IncorrectOperationException {
        PsiReferenceParameterList parameterList;
        if (psiClass == null) {
            $$$reportNull$$$0(65);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(66);
        }
        String qualifiedName = psiClass.getQualifiedName();
        Project project = psiFile.getProject();
        boolean z = JavaFileCodeStyleFacade.forContext(psiFile).useFQClassNames() && isFullyQualified(psiFile);
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        if (qualifiedName == null) {
            qualifiedName = psiClass.getMyName();
            if (!getManager().areElementsEquivalent(javaPsiFacade.getResolveHelper().resolveReferencedClass(qualifiedName, this), psiClass)) {
                throw cannotBindError(psiClass, getKindEnum(psiFile));
            }
        } else {
            if (javaPsiFacade.findClass(qualifiedName, getResolveScope()) == null && !z) {
                return this;
            }
            if (javaPsiFacade.getResolveHelper().resolveReferencedClass(qualifiedName, this) == null && javaPsiFacade.getResolveHelper().resolveReferencedClass(StringUtil.getPackageName(qualifiedName), this) != null) {
                qualifiedName = psiClass.getMyName();
            }
        }
        StringBuilder sb = new StringBuilder(qualifiedName);
        PsiReferenceParameterList parameterList2 = getParameterList();
        if (parameterList2 != null) {
            PsiElement referenceNameElement = getReferenceNameElement();
            while (referenceNameElement != parameterList2) {
                referenceNameElement = referenceNameElement.getNextSibling();
                sb.append(referenceNameElement.getText());
            }
        }
        PsiElement qualifier = getQualifier();
        PsiReferenceParameterList parameterList3 = qualifier instanceof PsiJavaCodeReferenceElement ? ((PsiJavaCodeReferenceElement) qualifier).getParameterList() : null;
        try {
            PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceFromText = javaPsiFacade.getParserFacade().createReferenceFromText(sb.toString(), getMyParent());
            List childrenOfTypeAsList = PsiTreeUtil.getChildrenOfTypeAsList(this, PsiAnnotation.class);
            if (!childrenOfTypeAsList.isEmpty()) {
                psiJavaCodeReferenceElementCreateReferenceFromText.addRangeBefore((PsiElement) childrenOfTypeAsList.get(0), (PsiElement) childrenOfTypeAsList.get(childrenOfTypeAsList.size() - 1), psiJavaCodeReferenceElementCreateReferenceFromText.getReferenceNameElement());
            }
            PsiReferenceParameterList parameterList4 = psiJavaCodeReferenceElementCreateReferenceFromText.getParameterList();
            if (parameterList2 != null && parameterList4 != null) {
                parameterList4.replace(parameterList2);
            }
            if (parameterList3 != null) {
                PsiElement qualifier2 = psiJavaCodeReferenceElementCreateReferenceFromText.getQualifier();
                if ((qualifier2 instanceof PsiJavaCodeReferenceElement) && (parameterList = ((PsiJavaCodeReferenceElement) qualifier2).getParameterList()) != null) {
                    parameterList.replace(parameterList3);
                }
            }
            getTreeParent().replaceChildInternal(this, (TreeElement) psiJavaCodeReferenceElementCreateReferenceFromText.getNode());
            if (!z) {
                psiJavaCodeReferenceElementCreateReferenceFromText = (PsiJavaCodeReferenceElement) JavaCodeStyleManager.getInstance(project).shortenClassReferences(psiJavaCodeReferenceElementCreateReferenceFromText, 8192);
            }
            if (psiJavaCodeReferenceElementCreateReferenceFromText == null) {
                $$$reportNull$$$0(68);
            }
            return psiJavaCodeReferenceElementCreateReferenceFromText;
        } catch (IncorrectOperationException e) {
            throw new IncorrectOperationException(e.getMessage() + " [qname=" + qualifiedName + " class=" + psiClass + ";" + psiClass.getClass().getName() + "]");
        }
    }

    private List<PsiAnnotation> getAnnotations() {
        PsiModifierList psiModifierListFindNeighbourModifierList;
        List<PsiAnnotation> childrenOfTypeAsList = PsiTreeUtil.getChildrenOfTypeAsList(this, PsiAnnotation.class);
        if (!isQualified() && (psiModifierListFindNeighbourModifierList = PsiImplUtil.findNeighbourModifierList(this)) != null) {
            ArrayList arrayList = new ArrayList(childrenOfTypeAsList);
            PsiImplUtil.collectTypeUseAnnotations(psiModifierListFindNeighbourModifierList, arrayList);
            childrenOfTypeAsList = arrayList;
        }
        if (childrenOfTypeAsList == null) {
            $$$reportNull$$$0(69);
        }
        return childrenOfTypeAsList;
    }

    private boolean isFullyQualified(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(70);
        }
        Kind kindEnum = getKindEnum(psiFile);
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[kindEnum.ordinal()]) {
            case 1:
            case 3:
                break;
            case 2:
                if (resolve() instanceof PsiPackage) {
                    return true;
                }
                break;
            default:
                LOG.error(kindEnum);
            case 4:
            case 5:
            case 6:
                return true;
        }
        ASTNode aSTNodeFindChildByRole = findChildByRole(54);
        if (aSTNodeFindChildByRole == null) {
            return false;
        }
        IElementType elementType = aSTNodeFindChildByRole.getElementType();
        LOG.assertTrue(elementType == JavaElementType.JAVA_CODE_REFERENCE, elementType);
        if (((PsiJavaCodeReferenceElement) SourceTreeToPsiMap.treeToPsiNotNull(aSTNodeFindChildByRole)).resolve() instanceof PsiPackage) {
            return true;
        }
        return ((PsiJavaCodeReferenceElementImpl) SourceTreeToPsiMap.treeToPsiNotNull(aSTNodeFindChildByRole)).isFullyQualified(psiFile);
    }

    private PsiElement bindToPackage(PsiPackage psiPackage) throws IncorrectOperationException {
        if (psiPackage == null) {
            $$$reportNull$$$0(71);
        }
        String qualifiedName = psiPackage.getQualifiedName();
        if (qualifiedName.isEmpty()) {
            throw new IncorrectOperationException("Cannot bind to default package: " + psiPackage);
        }
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceFromText = JavaPsiFacade.getInstance(getProject()).getParserFacade().createReferenceFromText(qualifiedName, getMyParent());
        getTreeParent().replaceChildInternal(this, (TreeElement) psiJavaCodeReferenceElementCreateReferenceFromText.getNode());
        if (psiJavaCodeReferenceElementCreateReferenceFromText == null) {
            $$$reportNull$$$0(72);
        }
        return psiJavaCodeReferenceElementCreateReferenceFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(73);
        }
        return isReferenceTo(psiElement, getContainingFile());
    }

    private boolean isReferenceTo(PsiElement psiElement, PsiFile psiFile) {
        ASTNode referenceNameNode;
        PsiElement referenceNameElement;
        String name2;
        PsiClass psiClass;
        String qualifiedName;
        if (psiElement == null) {
            $$$reportNull$$$0(74);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(75);
        }
        Kind kindEnum = getKindEnum(psiFile);
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[kindEnum.ordinal()]) {
            case 1:
            case 3:
                if (!(psiElement instanceof PsiClass) || (referenceNameNode = getReferenceNameNode()) == null || referenceNameNode.getElementType() != JavaTokenType.IDENTIFIER) {
                    return false;
                }
                String name3 = ((PsiClass) psiElement).getMyName();
                return name3 != null && referenceNameNode.getText().equals(name3) && psiFile.getManager().areElementsEquivalent(resolve(), psiElement);
            case 2:
                if (psiElement instanceof PsiPackage) {
                    return ((PsiPackage) psiElement).getQualifiedName().equals(getCanonicalText(false, null, psiFile));
                }
                if (!(psiElement instanceof PsiClass) || (referenceNameElement = getReferenceNameElement()) == null || (name2 = ((PsiClass) psiElement).getMyName()) == null) {
                    return false;
                }
                return referenceNameElement.textMatches(name2) && psiFile.getManager().areElementsEquivalent(resolve(), psiElement);
            case 4:
                if (psiElement instanceof PsiPackage) {
                    return ((PsiPackage) psiElement).getQualifiedName().equals(getCanonicalText(false, null, psiFile));
                }
                return false;
            case 5:
                if (!(psiElement instanceof PsiClass)) {
                    return false;
                }
                String qualifiedName2 = ((PsiClass) psiElement).getQualifiedName();
                return qualifiedName2 != null && qualifiedName2.equals(getCanonicalText(false, null, psiFile));
            case 6:
                if ((psiElement instanceof PsiClass) && (qualifiedName = (psiClass = (PsiClass) psiElement).getQualifiedName()) != null && qualifiedName.equals(getCanonicalText(false, null, psiFile))) {
                    return !PsiUtil.isFromDefaultPackage(psiClass) || PsiTreeUtil.getParentOfType(this, PsiImportStatementBase.class) == null;
                }
                if (psiElement instanceof PsiPackage) {
                    return ((PsiPackage) psiElement).getQualifiedName().equals(getCanonicalText(false, null, psiFile));
                }
                return false;
            default:
                LOG.error(kindEnum);
                return true;
        }
    }

    private String getNormalizedText() {
        String referenceText = this.myCachedNormalizedText;
        if (referenceText == null) {
            referenceText = JavaSourceUtil.getReferenceText(this);
            this.myCachedNormalizedText = referenceText;
        }
        if (referenceText == null) {
            $$$reportNull$$$0(76);
        }
        return referenceText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceJavaCodeReference
    public String getClassNameText() {
        String qualifiedClassName = this.myCachedQName;
        if (qualifiedClassName == null) {
            qualifiedClassName = PsiNameHelper.getQualifiedClassName(getNormalizedText(), false);
            this.myCachedQName = qualifiedClassName;
        }
        if (qualifiedClassName == null) {
            $$$reportNull$$$0(77);
        }
        return qualifiedClassName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceJavaCodeReference
    public void fullyQualify(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(78);
        }
        Kind kindEnum = getKindEnum(getContainingFile());
        if (kindEnum != Kind.CLASS_NAME_KIND && kindEnum != Kind.CLASS_OR_PACKAGE_NAME_KIND && kindEnum != Kind.CLASS_IN_QUALIFIED_NEW_KIND) {
            LOG.error("Wrong kind " + kindEnum);
        } else {
            JavaSourceUtil.fullyQualifyReference(this, psiClass);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public boolean isQualified() {
        return getQualifier() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public PsiElement getQualifier() {
        return SourceTreeToPsiMap.treeElementToPsi(findChildByRole(54));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        this.myCachedQName = null;
        this.myCachedNormalizedText = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        ElementFilter orFilter;
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[getKindEnum(getContainingFile()).ordinal()]) {
            case 1:
            case 3:
                orFilter = ElementClassFilter.CLASS;
                break;
            case 2:
                orFilter = new OrFilter(ElementClassFilter.CLASS, ElementClassFilter.PACKAGE);
                break;
            case 4:
                orFilter = ElementClassFilter.PACKAGE;
                break;
            case 5:
            case 6:
                orFilter = !isQualified() ? ElementClassFilter.PACKAGE : new OrFilter(ElementClassFilter.CLASS, ElementClassFilter.PACKAGE);
                break;
            default:
                throw new RuntimeException("Unknown reference type");
        }
        Object[] referenceVariantsByFilter = PsiImplUtil.getReferenceVariantsByFilter(this, orFilter);
        if (referenceVariantsByFilter == null) {
            $$$reportNull$$$0(79);
        }
        return referenceVariantsByFilter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor psiScopeProcessor) {
        PsiClass psiClassResolveClassInType;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(80);
        }
        ArrayList arrayList = new ArrayList();
        if (isInCode() && !(getMyParent() instanceof PsiImportStatement) && !(getMyParent() instanceof PsiReferenceList)) {
            arrayList.add(new AndFilter(ElementClassFilter.METHOD, new NotFilter(new ConstructorFilter())));
            arrayList.add(ElementClassFilter.VARIABLE);
        }
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[getKindEnum(getContainingFile()).ordinal()]) {
            case 1:
                arrayList.add(ElementClassFilter.CLASS);
                if (isQualified() || PsiTreeUtil.getParentOfType(this, PsiJavaModule.class) != null) {
                    arrayList.add(ElementClassFilter.PACKAGE);
                }
                break;
            case 2:
                arrayList.add(ElementClassFilter.CLASS);
                arrayList.add(ElementClassFilter.PACKAGE);
                break;
            case 3:
                PsiElement parent = getMyParent();
                if (!(parent instanceof PsiNewExpression) || (psiClassResolveClassInType = PsiUtil.resolveClassInType(((PsiNewExpression) parent).getQualifier().getType())) == null) {
                    return;
                }
                psiClassResolveClassInType.processDeclarations(new FilterScopeProcessor(new AndFilter(ElementClassFilter.CLASS, new ModifierFilter("static", false)), psiScopeProcessor), ResolveState.initial(), null, this);
                return;
            case 4:
                arrayList.add(ElementClassFilter.PACKAGE);
                break;
            case 5:
            case 6:
                arrayList.add(ElementClassFilter.PACKAGE);
                if (isQualified() || isCodeFragmentType(getTreeParent().getElementType())) {
                    arrayList.add(ElementClassFilter.CLASS);
                }
                break;
            default:
                throw new RuntimeException("Unknown reference type");
        }
        FilterScopeProcessor filterScopeProcessor = new FilterScopeProcessor(new OrFilter((ElementFilter[]) arrayList.toArray(ElementFilter.EMPTY_ARRAY)), psiScopeProcessor);
        for (PsiTypeParameter psiTypeParameter : getUnfinishedMethodTypeParameters()) {
            if (!filterScopeProcessor.execute(psiTypeParameter, ResolveState.initial())) {
                return;
            }
        }
        PsiScopesUtil.resolveAndWalk(filterScopeProcessor, this, null, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [org.jetbrains.kotlin.com.intellij.patterns.ElementPattern, org.jetbrains.kotlin.com.intellij.patterns.PsiElementPattern] */
    private PsiTypeParameter[] getUnfinishedMethodTypeParameters() {
        ProcessingContext processingContext = new ProcessingContext();
        if (((PsiJavaElementPattern.Capture) PsiJavaPatterns.psiElement().inside(PsiJavaPatterns.psiElement(PsiTypeElement.class).afterLeaf(((PsiJavaElementPattern.Capture) PsiJavaPatterns.psiElement().withText(">")).withParent((ElementPattern) ((PsiJavaElementPattern.Capture) PsiJavaPatterns.psiElement(PsiTypeParameterList.class).withParent(PsiErrorElement.class)).save("typeParameterList"))))).accepts(this, processingContext)) {
            PsiTypeParameterList psiTypeParameterList = (PsiTypeParameterList) processingContext.get("typeParameterList");
            PsiElement lightParent = psiTypeParameterList.getMyParent().getMyParent();
            if (lightParent instanceof PsiField) {
                lightParent = lightParent.getMyParent();
            }
            if (lightParent instanceof PsiClass) {
                PsiTypeParameter[] typeParameters = psiTypeParameterList.getTypeParameters();
                if (typeParameters == null) {
                    $$$reportNull$$$0(81);
                }
                return typeParameters;
            }
        }
        PsiTypeParameter[] psiTypeParameterArr = PsiTypeParameter.EMPTY_ARRAY;
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(82);
        }
        return psiTypeParameterArr;
    }

    private boolean isInCode() {
        if (!isCodeFragmentType(getTreeParent().getElementType()) && !(getMyParent() instanceof PsiAnnotation)) {
            for (PsiElement parent = getMyParent(); parent != null; parent = parent.getMyParent()) {
                if ((parent instanceof PsiCodeBlock) || (parent instanceof PsiLocalVariable)) {
                    return true;
                }
                if ((parent instanceof PsiClass) || (parent instanceof PsiCatchSection)) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiElement getReferenceNameElement() {
        return SourceTreeToPsiMap.treeElementToPsi(getReferenceNameNode());
    }

    private ASTNode getReferenceNameNode() {
        return findChildByRole(53);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiReferenceParameterList getParameterList() {
        return (PsiReferenceParameterList) findChildByRoleAsPsiElement(246);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        Kind kindEnum = getKindEnum(getContainingFile());
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$impl$source$PsiJavaCodeReferenceElementImpl$Kind[kindEnum.ordinal()]) {
            case 1:
            case 2:
            case 3:
                PsiElement psiElementResolve = resolve();
                if (psiElementResolve instanceof PsiClass) {
                    PsiClass psiClass = (PsiClass) psiElementResolve;
                    String qualifiedName = psiClass.getQualifiedName();
                    return qualifiedName == null ? psiClass.getMyName() : qualifiedName;
                }
                if (psiElementResolve instanceof PsiPackage) {
                    return ((PsiPackage) psiElementResolve).getQualifiedName();
                }
                LOG.assertTrue(psiElementResolve == null, psiElementResolve);
                return getClassNameText();
            case 4:
            case 5:
            case 6:
                return getNormalizedText();
            default:
                LOG.error(kindEnum);
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    /* JADX INFO: renamed from: getReferenceName */
    public String getCustomReferenceName() {
        ASTNode referenceNameNode = getReferenceNameNode();
        if (referenceNameNode == null) {
            return null;
        }
        return referenceNameNode.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public final TextRange getRangeInElement() {
        return calcRangeInElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiType[] getTypeParameters() {
        PsiReferenceParameterList parameterList = getParameterList();
        if (parameterList == null) {
            PsiType[] psiTypeArr = PsiType.EMPTY_ARRAY;
            if (psiTypeArr == null) {
                $$$reportNull$$$0(83);
            }
            return psiTypeArr;
        }
        PsiType[] typeArguments = parameterList.getTypeArguments();
        if (typeArguments == null) {
            $$$reportNull$$$0(84);
        }
        return typeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(86);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitReferenceElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final String toString() {
        return "PsiJavaCodeReferenceElement:" + getText();
    }
}
