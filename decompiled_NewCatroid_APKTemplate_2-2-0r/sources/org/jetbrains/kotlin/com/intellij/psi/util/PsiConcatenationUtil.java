package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiConcatenationUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:19:0x002a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "expression";
        } else if (i == 3) {
            objArr[0] = ParameterDescription.NAME_PREFIX;
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiConcatenationUtil";
        } else {
            objArr[0] = "expression";
        }
        if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiConcatenationUtil";
        } else {
            objArr[1] = "formatString";
        }
        if (i == 2) {
            objArr[2] = "addFormatParameter";
        } else if (i == 3) {
            objArr[2] = "getBoxedArgument";
        } else if (i == 4) {
            objArr[2] = "unwrapExpression";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static String buildUnescapedFormatString(PsiExpression psiExpression, boolean z, List<? super PsiExpression> list) {
        StringBuilder sb = new StringBuilder();
        buildFormatString(psiExpression, sb, list, z, false);
        return sb.toString();
    }

    private static void buildFormatString(PsiExpression psiExpression, StringBuilder sb, List<? super PsiExpression> list, boolean z, boolean z2) {
        if (psiExpression instanceof PsiLiteralExpression) {
            String strValueOf = String.valueOf(((PsiLiteralExpression) psiExpression).getValue());
            if (z2) {
                strValueOf = StringUtil.escapeStringCharacters(strValueOf);
            }
            sb.append(formatString(strValueOf, z));
            return;
        }
        if (psiExpression instanceof PsiPolyadicExpression) {
            PsiType type = psiExpression.getType();
            if (type != null && type.equalsToText(CommonClassNames.JAVA_LANG_STRING)) {
                PsiPolyadicExpression psiPolyadicExpression = (PsiPolyadicExpression) psiExpression;
                PsiExpression[] operands = psiPolyadicExpression.getOperands();
                PsiType type2 = operands[0].getType();
                PsiType type3 = operands[1].getType();
                boolean z3 = (type2 != null && type2.equalsToText(CommonClassNames.JAVA_LANG_STRING)) || (type3 != null && type3.equalsToText(CommonClassNames.JAVA_LANG_STRING));
                if (z3) {
                    buildFormatString(operands[0], sb, list, z, z2);
                }
                for (int i = 1; i < operands.length; i++) {
                    PsiExpression psiExpression2 = operands[i];
                    PsiType type4 = psiExpression2.getType();
                    type2 = TypeConversionUtil.calcTypeForBinaryExpression(type2, type4, psiPolyadicExpression.getOperationTokenType(), true);
                    if (type2 != null && type2.equalsToText(CommonClassNames.JAVA_LANG_STRING) && !z3) {
                        PsiElement tokenBeforeOperand = psiPolyadicExpression.getTokenBeforeOperand(psiExpression2);
                        if (tokenBeforeOperand.getPrevSibling() instanceof PsiWhiteSpace) {
                            tokenBeforeOperand = tokenBeforeOperand.getPrevSibling();
                        }
                        addFormatParameter(JavaPsiFacade.getElementFactory(psiPolyadicExpression.getProject()).createExpressionFromText(psiPolyadicExpression.getText().substring(0, tokenBeforeOperand.getStartOffsetInParent()), (PsiElement) psiPolyadicExpression), sb, list, z);
                        z3 = true;
                    }
                    if (z3) {
                        if (type4 != null && (type4.equalsToText(CommonClassNames.JAVA_LANG_STRING) || PsiType.CHAR.equals(type4))) {
                            buildFormatString(psiExpression2, sb, list, z, z2);
                        } else {
                            addFormatParameter(psiExpression2, sb, list, z);
                        }
                    }
                }
                return;
            }
            addFormatParameter(psiExpression, sb, list, z);
            return;
        }
        addFormatParameter(psiExpression, sb, list, z);
    }

    public static String formatString(String str, boolean z) {
        if (z) {
            String strReplace = str.replace("%", "%%").replace("\\'", FormatHelper.QUOTE);
            if (strReplace == null) {
                $$$reportNull$$$0(0);
            }
            return strReplace;
        }
        String strReplaceAll = str.replace(FormatHelper.QUOTE, "''").replaceAll("([{}]+)", "'$1'");
        if (strReplaceAll == null) {
            $$$reportNull$$$0(1);
        }
        return strReplaceAll;
    }

    private static void addFormatParameter(PsiExpression psiExpression, StringBuilder sb, List<? super PsiExpression> list, boolean z) {
        if (psiExpression == null) {
            $$$reportNull$$$0(2);
        }
        PsiType type = psiExpression.getType();
        if (!z) {
            sb.append("{").append(list.size()).append("}");
        } else if (type != null && (type.equalsToText("long") || type.equalsToText("int") || type.equalsToText(CommonClassNames.JAVA_LANG_LONG) || type.equalsToText(CommonClassNames.JAVA_LANG_INTEGER))) {
            sb.append("%d");
        } else {
            sb.append("%s");
        }
        list.add(getBoxedArgument(psiExpression));
    }

    private static PsiExpression getBoxedArgument(PsiExpression psiExpression) {
        String boxedTypeName;
        if (psiExpression == null) {
            $$$reportNull$$$0(3);
        }
        PsiExpression psiExpression2 = (PsiExpression) ObjectUtils.coalesce(unwrapExpression(psiExpression), psiExpression);
        if (PsiUtil.isLanguageLevel5OrHigher(psiExpression2)) {
            return psiExpression2;
        }
        PsiType type = psiExpression2.getType();
        if (!(type instanceof PsiPrimitiveType) || type.equals(PsiType.NULL) || (boxedTypeName = ((PsiPrimitiveType) type).getBoxedTypeName()) == null) {
            return psiExpression2;
        }
        GlobalSearchScope resolveScope = psiExpression2.getResolveScope();
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiExpression2.getProject());
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceElementByFQClassName = elementFactory.createReferenceElementByFQClassName(boxedTypeName, resolveScope);
        PsiNewExpression psiNewExpression = (PsiNewExpression) elementFactory.createExpressionFromText("new A(b)", (PsiElement) null);
        psiNewExpression.getClassReference().replace(psiJavaCodeReferenceElementCreateReferenceElementByFQClassName);
        psiNewExpression.getArgumentList().getExpressions()[0].replace(psiExpression2);
        return psiNewExpression;
    }

    private static PsiExpression unwrapExpression(PsiExpression psiExpression) {
        PsiType type;
        int typeRank;
        int typeRank2;
        if (psiExpression == null) {
            $$$reportNull$$$0(4);
        }
        while (true) {
            if (psiExpression instanceof PsiParenthesizedExpression) {
                psiExpression = ((PsiParenthesizedExpression) psiExpression).getExpression();
            } else {
                if (!(psiExpression instanceof PsiTypeCastExpression)) {
                    return psiExpression;
                }
                PsiTypeCastExpression psiTypeCastExpression = (PsiTypeCastExpression) psiExpression;
                PsiType type2 = psiTypeCastExpression.getType();
                PsiExpression operand = psiTypeCastExpression.getOperand();
                if (operand == null) {
                    return psiExpression;
                }
                if (TypeConversionUtil.isNumericType(type2) && ((type = operand.getType()) == null || (typeRank = TypeConversionUtil.getTypeRank(type2)) < (typeRank2 = TypeConversionUtil.getTypeRank(type)) || (typeRank == 3 && typeRank2 != typeRank))) {
                    return psiExpression;
                }
                psiExpression = operand;
            }
        }
    }
}
