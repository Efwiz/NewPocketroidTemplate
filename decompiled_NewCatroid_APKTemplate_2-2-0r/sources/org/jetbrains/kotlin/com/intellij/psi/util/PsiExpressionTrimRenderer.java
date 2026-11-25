package org.jetbrains.kotlin.com.intellij.psi.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPostfixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.util.NotNullFunction;

/* JADX INFO: loaded from: classes3.dex */
public class PsiExpressionTrimRenderer extends JavaRecursiveElementWalkingVisitor {
    private final StringBuilder myBuf;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "expression", "org/jetbrains/kotlin/com/intellij/psi/util/PsiExpressionTrimRenderer", "render"));
    }

    public PsiExpressionTrimRenderer(StringBuilder sb) {
        this.myBuf = sb;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitExpression(PsiExpression psiExpression) {
        this.myBuf.append(psiExpression.getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitInstanceOfExpression(PsiInstanceOfExpression psiInstanceOfExpression) {
        psiInstanceOfExpression.getOperand().accept(this);
        this.myBuf.append(" ").append(PsiKeyword.INSTANCEOF).append(" ");
        PsiTypeElement checkType = psiInstanceOfExpression.getCheckType();
        if (checkType != null) {
            this.myBuf.append(checkType.getText());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitParenthesizedExpression(PsiParenthesizedExpression psiParenthesizedExpression) {
        this.myBuf.append("(");
        PsiExpression expression = psiParenthesizedExpression.getExpression();
        if (expression != null) {
            expression.accept(this);
        }
        this.myBuf.append(Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitTypeCastExpression(PsiTypeCastExpression psiTypeCastExpression) {
        PsiTypeElement castType = psiTypeCastExpression.getCastType();
        if (castType != null) {
            this.myBuf.append("(").append(castType.getText()).append(Constant.AFTER_QUTO);
        }
        PsiExpression operand = psiTypeCastExpression.getOperand();
        if (operand != null) {
            operand.accept(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitArrayAccessExpression(PsiArrayAccessExpression psiArrayAccessExpression) {
        psiArrayAccessExpression.getArrayExpression().accept(this);
        this.myBuf.append("[");
        PsiExpression indexExpression = psiArrayAccessExpression.getIndexExpression();
        if (indexExpression != null) {
            indexExpression.accept(this);
        }
        this.myBuf.append("]");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPrefixExpression(PsiPrefixExpression psiPrefixExpression) {
        this.myBuf.append(psiPrefixExpression.getOperationSign().getText());
        PsiExpression operand = psiPrefixExpression.getOperand();
        if (operand != null) {
            operand.accept(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPostfixExpression(PsiPostfixExpression psiPostfixExpression) {
        psiPostfixExpression.getOperand().accept(this);
        this.myBuf.append(psiPostfixExpression.getOperationSign().getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPolyadicExpression(PsiPolyadicExpression psiPolyadicExpression) {
        PsiExpression[] operands = psiPolyadicExpression.getOperands();
        for (int i = 0; i < operands.length; i++) {
            PsiExpression psiExpression = operands[i];
            if (i != 0) {
                this.myBuf.append(" ").append(psiPolyadicExpression.getTokenBeforeOperand(psiExpression).getText()).append(" ");
            }
            psiExpression.accept(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitLambdaExpression(PsiLambdaExpression psiLambdaExpression) {
        PsiParameterList parameterList = psiLambdaExpression.getParameterList();
        PsiParameter[] parameters = parameterList.getParameters();
        boolean zIsJavaToken = PsiUtil.isJavaToken(parameterList.getFirstChild(), JavaTokenType.LPARENTH);
        if (zIsJavaToken) {
            this.myBuf.append('(');
        }
        for (int i = 0; i < parameters.length; i++) {
            PsiParameter psiParameter = parameters[i];
            if (i != 0) {
                this.myBuf.append(", ");
            }
            this.myBuf.append(PsiFormatUtil.formatVariable(psiParameter, (psiParameter.getTypeElement() == null ? 0 : 2) | 1, PsiSubstitutor.EMPTY));
        }
        if (zIsJavaToken) {
            this.myBuf.append(')');
        }
        this.myBuf.append(" -> {...}");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitConditionalExpression(PsiConditionalExpression psiConditionalExpression) {
        psiConditionalExpression.getCondition().accept(this);
        this.myBuf.append(" ? ");
        PsiExpression thenExpression = psiConditionalExpression.getThenExpression();
        if (thenExpression != null) {
            thenExpression.accept(this);
        }
        this.myBuf.append(" : ");
        PsiExpression elseExpression = psiConditionalExpression.getElseExpression();
        if (elseExpression != null) {
            elseExpression.accept(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitAssignmentExpression(PsiAssignmentExpression psiAssignmentExpression) {
        psiAssignmentExpression.getLExpression().accept(this);
        this.myBuf.append(psiAssignmentExpression.getOperationSign().getText());
        PsiExpression rExpression = psiAssignmentExpression.getRExpression();
        if (rExpression != null) {
            rExpression.accept(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
        PsiExpression qualifierExpression = psiReferenceExpression.getQualifierExpression();
        if (qualifierExpression != null) {
            qualifierExpression.accept(this);
            this.myBuf.append(".");
        }
        this.myBuf.append(psiReferenceExpression.getCustomReferenceName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitMethodCallExpression(PsiMethodCallExpression psiMethodCallExpression) {
        psiMethodCallExpression.getMethodExpression().accept(this);
        psiMethodCallExpression.getArgumentList().accept(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitMethodReferenceExpression(PsiMethodReferenceExpression psiMethodReferenceExpression) {
        PsiElement qualifier = psiMethodReferenceExpression.getQualifier();
        if (qualifier != null) {
            qualifier.accept(this);
        }
        this.myBuf.append("::");
        this.myBuf.append(psiMethodReferenceExpression.getCustomReferenceName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitArrayInitializerExpression(PsiArrayInitializerExpression psiArrayInitializerExpression) {
        this.myBuf.append("{");
        PsiExpression[] initializers = psiArrayInitializerExpression.getInitializers();
        int length = initializers.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            PsiExpression psiExpression = initializers[i];
            if (!z) {
                this.myBuf.append(", ");
            }
            psiExpression.accept(this);
            i++;
            z = false;
        }
        this.myBuf.append("}");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitExpressionList(PsiExpressionList psiExpressionList) {
        if (psiExpressionList.getExpressions().length > 0) {
            this.myBuf.append("(...)");
        } else {
            this.myBuf.append("()");
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitNewExpression(PsiNewExpression psiNewExpression) {
        PsiAnonymousClass anonymousClass = psiNewExpression.getAnonymousClass();
        PsiExpressionList argumentList = psiNewExpression.getArgumentList();
        if (anonymousClass != null) {
            this.myBuf.append(PsiKeyword.NEW).append(" ").append(anonymousClass.getBaseClassType().getPresentableText());
            if (argumentList != null) {
                argumentList.accept(this);
            }
            this.myBuf.append(" {...}");
            return;
        }
        PsiJavaCodeReferenceElement classReference = psiNewExpression.getClassReference();
        if (classReference != null) {
            this.myBuf.append(PsiKeyword.NEW).append(" ").append(classReference.getText());
            PsiExpression[] arrayDimensions = psiNewExpression.getArrayDimensions();
            PsiType type = psiNewExpression.getType();
            int arrayDimensions2 = type != null ? type.getArrayDimensions() : arrayDimensions.length;
            if (arrayDimensions.length > 0) {
                this.myBuf.append("[");
            }
            int length = arrayDimensions.length;
            int i = 0;
            while (i < arrayDimensions2) {
                PsiExpression psiExpression = i < length ? arrayDimensions[i] : null;
                if (i > 0) {
                    this.myBuf.append("][");
                }
                if (psiExpression != null) {
                    psiExpression.accept(this);
                }
                i++;
            }
            if (arrayDimensions.length > 0) {
                this.myBuf.append("]");
            }
            if (argumentList != null) {
                argumentList.accept(this);
            }
            PsiArrayInitializerExpression arrayInitializer = psiNewExpression.getArrayInitializer();
            if (arrayInitializer != null) {
                arrayInitializer.accept(this);
                return;
            }
            return;
        }
        this.myBuf.append(psiNewExpression.getText());
    }

    public static class RenderFunction implements NotNullFunction<PsiExpression, String> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "psiExpression";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiExpressionTrimRenderer$RenderFunction";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiExpressionTrimRenderer$RenderFunction";
            } else {
                objArr[1] = "fun";
            }
            if (i != 1) {
                objArr[2] = "fun";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
        public String fun(PsiExpression psiExpression) {
            if (psiExpression == null) {
                $$$reportNull$$$0(0);
            }
            String strRender = PsiExpressionTrimRenderer.render(psiExpression);
            if (strRender == null) {
                $$$reportNull$$$0(1);
            }
            return strRender;
        }
    }

    public static String render(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        return render(psiExpression, 100);
    }

    public static String render(PsiExpression psiExpression, int i) {
        if (psiExpression == null) {
            $$$reportNull$$$0(1);
        }
        StringBuilder sb = new StringBuilder();
        psiExpression.accept(new PsiExpressionTrimRenderer(sb));
        String string = sb.toString();
        int iIndexOf = string.indexOf(10);
        if (iIndexOf == -1) {
            iIndexOf = Math.min(i, string.length());
        }
        String strSubstring = string.substring(0, iIndexOf);
        return strSubstring.length() != string.length() ? strSubstring + " ..." : strSubstring;
    }
}
