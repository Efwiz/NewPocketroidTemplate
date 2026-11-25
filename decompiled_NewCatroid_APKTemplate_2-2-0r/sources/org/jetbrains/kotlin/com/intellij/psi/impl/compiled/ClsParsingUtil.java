package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import com.google.firebase.analytics.FirebaseAnalytics;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaLexer;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParser;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiBinaryExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaDummyElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.lang.JavaVersion;

/* JADX INFO: loaded from: classes3.dex */
public final class ClsParsingUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ClsParsingUtil.class);
    private static final JavaParserUtil.ParserWrapper ANNOTATION_VALUE = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsParsingUtil$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
        public final void parse(PsiBuilder psiBuilder) {
            JavaParser.INSTANCE.getDeclarationParser().parseAnnotationValue(psiBuilder);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "manager";
                break;
            case 2:
            case 5:
            case 7:
            case 9:
                objArr[0] = "parent";
                break;
            case 3:
                objArr[0] = "text";
                break;
            case 6:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
                objArr[0] = "expr";
                break;
            case 10:
                objArr[0] = "identifier";
                break;
            case 11:
                objArr[0] = FirebaseAnalytics.Param.LEVEL;
                break;
            default:
                objArr[0] = "exprText";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsParsingUtil";
        switch (i) {
            case 3:
            case 4:
            case 5:
                objArr[2] = "createMemberValueFromText";
                break;
            case 6:
            case 7:
                objArr[2] = "getMemberValue";
                break;
            case 8:
            case 9:
                objArr[2] = "psiToClsExpression";
                break;
            case 10:
            case 11:
                objArr[2] = "isJavaIdentifier";
                break;
            default:
                objArr[2] = "createExpressionFromText";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean isPreviewLevel(int i) {
        return i == 65535;
    }

    private ClsParsingUtil() {
    }

    public static PsiExpression createExpressionFromText(String str, PsiManager psiManager, ClsElementImpl clsElementImpl) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        if (clsElementImpl == null) {
            $$$reportNull$$$0(2);
        }
        try {
            return psiToClsExpression(JavaPsiFacade.getInstance(psiManager.getProject()).getParserFacade().createExpressionFromText(str, null), clsElementImpl);
        } catch (IncorrectOperationException e) {
            LOG.error((Throwable) e);
            return null;
        }
    }

    public static PsiAnnotationMemberValue createMemberValueFromText(String str, PsiManager psiManager, ClsElementImpl clsElementImpl) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(4);
        }
        if (clsElementImpl == null) {
            $$$reportNull$$$0(5);
        }
        PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(DummyHolderFactory.createHolder(psiManager, new JavaDummyElement(str, ANNOTATION_VALUE, PsiUtil.getLanguageLevel(clsElementImpl)), (PsiElement) null).getTreeElement().getFirstChildNode());
        if (!(psiElementTreeElementToPsi instanceof PsiAnnotationMemberValue)) {
            LOG.error("Could not parse initializer:'" + str + FormatHelper.QUOTE);
            return null;
        }
        return getMemberValue(psiElementTreeElementToPsi, clsElementImpl);
    }

    public static PsiAnnotationMemberValue getMemberValue(PsiElement psiElement, ClsElementImpl clsElementImpl) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        if (clsElementImpl == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElement instanceof PsiExpression) {
            return psiToClsExpression((PsiExpression) psiElement, clsElementImpl);
        }
        if (psiElement instanceof PsiArrayInitializerMemberValue) {
            PsiAnnotationMemberValue[] initializers = ((PsiArrayInitializerMemberValue) psiElement).getInitializers();
            PsiAnnotationMemberValue[] psiAnnotationMemberValueArr = new PsiAnnotationMemberValue[initializers.length];
            ClsArrayInitializerMemberValueImpl clsArrayInitializerMemberValueImpl = new ClsArrayInitializerMemberValueImpl(clsElementImpl, psiAnnotationMemberValueArr);
            for (int i = 0; i < initializers.length; i++) {
                psiAnnotationMemberValueArr[i] = getMemberValue(initializers[i], clsArrayInitializerMemberValueImpl);
            }
            return clsArrayInitializerMemberValueImpl;
        }
        if (psiElement instanceof PsiAnnotation) {
            final PsiAnnotation psiAnnotation = (PsiAnnotation) psiElement;
            final String text = psiAnnotation.getNameReferenceElement().getText();
            return new ClsAnnotationValueImpl(clsElementImpl) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsParsingUtil.1
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnnotationValueImpl
                protected ClsJavaCodeReferenceElementImpl createReference() {
                    return new ClsJavaCodeReferenceElementImpl(this, text);
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsAnnotationValueImpl
                protected ClsAnnotationParameterListImpl createParameterList() {
                    return new ClsAnnotationParameterListImpl(this, psiAnnotation.getParameterList().getAttributes());
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation
                public PsiAnnotationOwner getOwner() {
                    return (PsiAnnotationOwner) getMyParent();
                }
            };
        }
        LOG.error("Unexpected source element for annotation member value: " + psiElement);
        return null;
    }

    static PsiExpression psiToClsExpression(PsiExpression psiExpression, ClsElementImpl clsElementImpl) {
        if (psiExpression == null) {
            $$$reportNull$$$0(8);
        }
        if (clsElementImpl == null) {
            $$$reportNull$$$0(9);
        }
        boolean z = false;
        if (psiExpression instanceof PsiLiteralExpression) {
            PsiFile containingFile = clsElementImpl.getContainingFile();
            if ((containingFile instanceof ClsFileImpl) && ((ClsFileImpl) containingFile).isForDecompiling()) {
                z = true;
            }
            return new ClsLiteralExpressionImpl(clsElementImpl, psiExpression.getText(), z ? PsiType.NULL : psiExpression.getType(), z ? null : ((PsiLiteralExpression) psiExpression).getValue());
        }
        if (psiExpression instanceof PsiPrefixExpression) {
            PsiPrefixExpression psiPrefixExpression = (PsiPrefixExpression) psiExpression;
            PsiJavaToken operationSign = psiPrefixExpression.getOperationSign();
            PsiExpression operand = psiPrefixExpression.getOperand();
            if (operand == null) {
                LOG.error("Invalid prefix expression: " + psiExpression + " [" + psiExpression.getText() + "]");
                return null;
            }
            return new ClsPrefixExpressionImpl(clsElementImpl, operationSign, operand);
        }
        if (psiExpression instanceof PsiClassObjectAccessExpression) {
            String text = psiExpression.getText();
            if (StringUtil.endsWith(text, ".class")) {
                return new ClsClassObjectAccessExpressionImpl(clsElementImpl, text.substring(0, text.length() - 6));
            }
        }
        if (psiExpression instanceof PsiReferenceExpression) {
            return new ClsReferenceExpressionImpl(clsElementImpl, (PsiReferenceExpression) psiExpression);
        }
        if (psiExpression instanceof PsiBinaryExpression) {
            PsiBinaryExpression psiBinaryExpression = (PsiBinaryExpression) psiExpression;
            PsiJavaToken operationSign2 = psiBinaryExpression.getOperationSign();
            PsiExpression lOperand = psiBinaryExpression.getLOperand();
            PsiExpression rOperand = psiBinaryExpression.getROperand();
            if (rOperand == null) {
                LOG.error("Invalid binary expression: " + psiExpression + " [" + psiExpression.getText() + "]");
                return null;
            }
            return new ClsBinaryExpressionImpl(clsElementImpl, operationSign2, lOperand, rOperand);
        }
        PsiFile containingFile2 = clsElementImpl.getContainingFile();
        if ((containingFile2 instanceof ClsFileImpl) && ((ClsFileImpl) containingFile2).isForDecompiling()) {
            return new ClsLiteralExpressionImpl(clsElementImpl, psiExpression.getText(), PsiType.NULL, null);
        }
        Object objComputeConstantExpression = JavaPsiFacade.getInstance(psiExpression.getProject()).getConstantEvaluationHelper().computeConstantExpression(psiExpression);
        if (objComputeConstantExpression != null) {
            return new ClsLiteralExpressionImpl(clsElementImpl, psiExpression.getText(), psiExpression.getType(), objComputeConstantExpression);
        }
        LOG.error("Unable to compute expression value: " + psiExpression + " [" + psiExpression.getText() + "]");
        return null;
    }

    public static boolean isJavaIdentifier(String str, LanguageLevel languageLevel) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(11);
        }
        return StringUtil.isJavaIdentifier(str) && !JavaLexer.isKeyword(str, languageLevel);
    }

    public static JavaSdkVersion getJdkVersionByBytecode(int i) {
        if (i >= 44) {
            return JavaSdkVersion.fromJavaVersion(JavaVersion.compose(i - 44));
        }
        return null;
    }
}
