package org.jetbrains.kotlin.js.translate.utils;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;

/* JADX INFO: loaded from: classes5.dex */
public final class PsiUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:10:0x0017  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 5 && i != 7 && i != 11 && i != 12) {
            switch (i) {
                case 14:
                case 15:
                case 16:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 5 && i != 7 && i != 11 && i != 12) {
            switch (i) {
                case 14:
                case 15:
                case 16:
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
            case 3:
            case 5:
            case 7:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/js/translate/utils/PsiUtils";
                break;
            case 4:
            case 6:
            case 8:
            case 13:
            default:
                objArr[0] = "expression";
                break;
            case 9:
                objArr[0] = "binaryExpression";
                break;
            case 10:
                objArr[0] = "classDeclaration";
                break;
        }
        if (i == 3) {
            objArr[1] = "getSelector";
        } else if (i == 5) {
            objArr[1] = "getOperationToken";
        } else if (i == 7) {
            objArr[1] = "getBaseExpression";
        } else if (i != 11 && i != 12) {
            switch (i) {
                case 14:
                    objArr[1] = "getLoopRange";
                    break;
                case 15:
                case 16:
                    objArr[1] = "getFunctionDescriptor";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/js/translate/utils/PsiUtils";
                    break;
            }
        } else {
            objArr[1] = "getPrimaryConstructorParameters";
        }
        switch (i) {
            case 1:
                objArr[2] = "getSelectorAsSimpleName";
                break;
            case 2:
                objArr[2] = "getSelector";
                break;
            case 3:
            case 5:
            case 7:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
                break;
            case 4:
                objArr[2] = "getOperationToken";
                break;
            case 6:
                objArr[2] = "getBaseExpression";
                break;
            case 8:
                objArr[2] = "isPrefix";
                break;
            case 9:
                objArr[2] = "isNegatedOperation";
                break;
            case 10:
                objArr[2] = "getPrimaryConstructorParameters";
                break;
            case 13:
                objArr[2] = "getLoopRange";
                break;
            default:
                objArr[2] = "getSimpleName";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 7 && i != 11 && i != 12) {
            switch (i) {
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private PsiUtils() {
    }

    public static KtSimpleNameExpression getSimpleName(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (ktExpression instanceof KtSimpleNameExpression) {
            return (KtSimpleNameExpression) ktExpression;
        }
        if (ktExpression instanceof KtQualifiedExpression) {
            return getSelectorAsSimpleName((KtQualifiedExpression) ktExpression);
        }
        return null;
    }

    public static KtSimpleNameExpression getSelectorAsSimpleName(KtQualifiedExpression ktQualifiedExpression) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(1);
        }
        KtExpression selector = getSelector(ktQualifiedExpression);
        if (selector instanceof KtSimpleNameExpression) {
            return (KtSimpleNameExpression) selector;
        }
        return null;
    }

    public static KtExpression getSelector(KtQualifiedExpression ktQualifiedExpression) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(2);
        }
        KtExpression selectorExpression = ktQualifiedExpression.getSelectorExpression();
        if (selectorExpression == null) {
            $$$reportNull$$$0(3);
        }
        return selectorExpression;
    }

    public static KtToken getOperationToken(KtOperationExpression ktOperationExpression) {
        if (ktOperationExpression == null) {
            $$$reportNull$$$0(4);
        }
        KtToken ktToken = (KtToken) ktOperationExpression.getOperationReference().getReferencedNameElementType();
        if (ktToken == null) {
            $$$reportNull$$$0(5);
        }
        return ktToken;
    }

    public static KtExpression getBaseExpression(KtUnaryExpression ktUnaryExpression) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(6);
        }
        KtExpression baseExpression = ktUnaryExpression.getBaseExpression();
        if (baseExpression == null) {
            $$$reportNull$$$0(7);
        }
        return baseExpression;
    }

    public static boolean isPrefix(KtUnaryExpression ktUnaryExpression) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(8);
        }
        return ktUnaryExpression instanceof KtPrefixExpression;
    }

    public static boolean isAssignment(KtToken ktToken) {
        return ktToken == KtTokens.EQ;
    }

    public static boolean isNegatedOperation(KtBinaryExpression ktBinaryExpression) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(9);
        }
        return ktBinaryExpression.getOperationToken() == KtTokens.EXCLEQ || KtPsiUtil.isNotInOperation(ktBinaryExpression);
    }

    public static List<KtParameter> getPrimaryConstructorParameters(KtPureClassOrObject ktPureClassOrObject) {
        if (ktPureClassOrObject == null) {
            $$$reportNull$$$0(10);
        }
        if (ktPureClassOrObject instanceof KtClass) {
            List<KtParameter> primaryConstructorParameters = ktPureClassOrObject.getPrimaryConstructorParameters();
            if (primaryConstructorParameters == null) {
                $$$reportNull$$$0(11);
            }
            return primaryConstructorParameters;
        }
        List<KtParameter> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(12);
        }
        return listEmptyList;
    }

    public static KtExpression getLoopRange(KtForExpression ktForExpression) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(13);
        }
        KtExpression loopRange = ktForExpression.getLoopRange();
        if (loopRange == null) {
            $$$reportNull$$$0(14);
        }
        return loopRange;
    }

    public static CallableDescriptor getFunctionDescriptor(ResolvedCall<?> resolvedCall) {
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            CallableDescriptor callableDescriptor = ((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall().get$suspendPropertyDescriptor();
            if (callableDescriptor == null) {
                $$$reportNull$$$0(15);
            }
            return callableDescriptor;
        }
        CallableDescriptor callableDescriptor2 = resolvedCall.get$suspendPropertyDescriptor();
        if (callableDescriptor2 == null) {
            $$$reportNull$$$0(16);
        }
        return callableDescriptor2;
    }
}
