package org.jetbrains.kotlin.js.translate.operation;

import java.util.Map;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableBiMap;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes5.dex */
public final class OperatorTable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Map<KtToken, JsBinaryOperator> binaryOperatorsMap = ImmutableBiMap.builder().put(KtTokens.PLUS, JsBinaryOperator.ADD).put(KtTokens.MINUS, JsBinaryOperator.SUB).put(KtTokens.MUL, JsBinaryOperator.MUL).put(KtTokens.DIV, JsBinaryOperator.DIV).put(KtTokens.EQ, JsBinaryOperator.ASG).put(KtTokens.GT, JsBinaryOperator.GT).put(KtTokens.GTEQ, JsBinaryOperator.GTE).put(KtTokens.LT, JsBinaryOperator.LT).put(KtTokens.LTEQ, JsBinaryOperator.LTE).put(KtTokens.ANDAND, JsBinaryOperator.AND).put(KtTokens.OROR, JsBinaryOperator.OR).put(KtTokens.PERC, JsBinaryOperator.MOD).put(KtTokens.PLUSEQ, JsBinaryOperator.ASG_ADD).put(KtTokens.MINUSEQ, JsBinaryOperator.ASG_SUB).put(KtTokens.DIVEQ, JsBinaryOperator.ASG_DIV).put(KtTokens.MULTEQ, JsBinaryOperator.ASG_MUL).put(KtTokens.PERCEQ, JsBinaryOperator.ASG_MOD).put(KtTokens.IN_KEYWORD, JsBinaryOperator.INOP).put(KtTokens.EQEQEQ, JsBinaryOperator.REF_EQ).put(KtTokens.EXCLEQEQEQ, JsBinaryOperator.REF_NEQ).build();
    private static final ImmutableBiMap<KtToken, JsUnaryOperator> unaryOperatorsMap = ImmutableBiMap.builder().put(KtTokens.PLUSPLUS, JsUnaryOperator.INC).put(KtTokens.MINUSMINUS, JsUnaryOperator.DEC).put(KtTokens.EXCL, JsUnaryOperator.NOT).put(KtTokens.MINUS, JsUnaryOperator.NEG).put(KtTokens.PLUS, JsUnaryOperator.POS).build();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5) ? 2 : 3];
        if (i == 3 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/operation/OperatorTable";
        } else {
            objArr[0] = "token";
        }
        if (i == 3) {
            objArr[1] = "getBinaryOperator";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/OperatorTable";
        } else {
            objArr[1] = "getUnaryOperator";
        }
        if (i == 1) {
            objArr[2] = "hasCorrespondingBinaryOperator";
        } else if (i == 2) {
            objArr[2] = "getBinaryOperator";
        } else if (i != 3) {
            if (i == 4) {
                objArr[2] = "getUnaryOperator";
            } else if (i != 5) {
                objArr[2] = "hasCorrespondingOperator";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private OperatorTable() {
    }

    public static boolean hasCorrespondingOperator(KtToken ktToken) {
        if (ktToken == null) {
            $$$reportNull$$$0(0);
        }
        return binaryOperatorsMap.containsKey(ktToken) || unaryOperatorsMap.containsKey(ktToken);
    }

    public static boolean hasCorrespondingBinaryOperator(KtToken ktToken) {
        if (ktToken == null) {
            $$$reportNull$$$0(1);
        }
        return binaryOperatorsMap.containsKey(ktToken);
    }

    public static JsBinaryOperator getBinaryOperator(KtToken ktToken) {
        if (ktToken == null) {
            $$$reportNull$$$0(2);
        }
        JsBinaryOperator jsBinaryOperator = binaryOperatorsMap.get(ktToken);
        if (jsBinaryOperator == null) {
            $$$reportNull$$$0(3);
        }
        return jsBinaryOperator;
    }

    public static JsUnaryOperator getUnaryOperator(KtToken ktToken) {
        if (ktToken == null) {
            $$$reportNull$$$0(4);
        }
        JsUnaryOperator jsUnaryOperator = unaryOperatorsMap.get(ktToken);
        if (jsUnaryOperator == null) {
            $$$reportNull$$$0(5);
        }
        return jsUnaryOperator;
    }
}
