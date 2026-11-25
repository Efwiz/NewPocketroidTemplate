package org.jetbrains.kotlin.js.translate.reference;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;

/* JADX INFO: loaded from: classes5.dex */
public class ArrayAccessTranslator extends AbstractTranslator implements AccessTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KtArrayAccessExpression expression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 11 || i == 8 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 11 || i == 8 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "context";
                break;
            case 2:
            default:
                objArr[0] = "expression";
                break;
            case 4:
            case 8:
            case 9:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/ArrayAccessTranslator";
                break;
            case 5:
            case 7:
                objArr[0] = "arrayExpression";
                break;
            case 6:
                objArr[0] = "setTo";
                break;
            case 10:
                objArr[0] = "toSetTo";
                break;
        }
        if (i == 4) {
            objArr[1] = "translateAsGet";
        } else if (i == 11) {
            objArr[1] = "contextWithValueParameterAliasInArrayGetAccess";
        } else if (i == 8) {
            objArr[1] = "translateAsMethodCall";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/ArrayAccessTranslator";
        } else {
            objArr[1] = "getArrayExpression";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
            case 8:
            case 9:
            case 11:
                break;
            case 5:
                objArr[2] = "translateAsGet";
                break;
            case 6:
                objArr[2] = "translateAsSet";
                break;
            case 7:
                objArr[2] = "translateAsMethodCall";
                break;
            case 10:
                objArr[2] = "contextWithValueParameterAliasInArrayGetAccess";
                break;
            default:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 11 && i != 8 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static ArrayAccessTranslator newInstance(KtArrayAccessExpression ktArrayAccessExpression, TranslationContext translationContext) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        return new ArrayAccessTranslator(ktArrayAccessExpression, translationContext);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private ArrayAccessTranslator(KtArrayAccessExpression ktArrayAccessExpression, TranslationContext translationContext) {
        super(translationContext);
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        this.expression = ktArrayAccessExpression;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsGet() {
        JsExpression jsExpressionTranslateAsGet = translateAsGet(getArrayExpression());
        if (jsExpressionTranslateAsGet == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpressionTranslateAsGet;
    }

    protected JsExpression translateAsGet(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(5);
        }
        return translateAsMethodCall(jsExpression, null);
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsSet(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(6);
        }
        return translateAsMethodCall(getArrayExpression(), jsExpression);
    }

    private JsExpression translateAsMethodCall(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(7);
        }
        boolean z = jsExpression2 == null;
        TranslationContext translationContextContext = context();
        ResolvedCall<FunctionDescriptor> resolvedCallForArrayAccess = BindingUtils.getResolvedCallForArrayAccess(bindingContext(), this.expression, z);
        if (!z) {
            translationContextContext = contextWithValueParameterAliasInArrayGetAccess(jsExpression2);
        }
        JsExpression jsExpressionTranslate = CallTranslator.translate(translationContextContext, resolvedCallForArrayAccess, jsExpression);
        if (jsExpressionTranslate == null) {
            $$$reportNull$$$0(8);
        }
        return jsExpressionTranslate;
    }

    protected JsExpression getArrayExpression() {
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(this.expression.getArrayExpression(), context());
        if (jsExpressionTranslateAsExpression == null) {
            $$$reportNull$$$0(9);
        }
        return jsExpressionTranslateAsExpression;
    }

    private TranslationContext contextWithValueParameterAliasInArrayGetAccess(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(10);
        }
        ResolvedCall<FunctionDescriptor> resolvedCallForArrayAccess = BindingUtils.getResolvedCallForArrayAccess(bindingContext(), this.expression, false);
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCallForArrayAccess.getValueArgumentsByIndex();
        if (valueArgumentsByIndex == null) {
            throw new IllegalStateException("Failed to arrange value arguments by index: " + resolvedCallForArrayAccess.getResultingDescriptor());
        }
        TranslationContext translationContextInnerContextWithAliasesForExpressions = context().innerContextWithAliasesForExpressions(Collections.singletonMap(((ExpressionValueArgument) valueArgumentsByIndex.get(valueArgumentsByIndex.size() - 1)).getValueArgument().getArgumentExpression(), jsExpression));
        if (translationContextInnerContextWithAliasesForExpressions == null) {
            $$$reportNull$$$0(11);
        }
        return translationContextInnerContextWithAliasesForExpressions;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public AccessTranslator getCached() {
        HashMap map = new HashMap();
        JsExpression jsExpressionCacheExpressionIfNeeded = context().cacheExpressionIfNeeded(getArrayExpression());
        map.put(this.expression.getArrayExpression(), jsExpressionCacheExpressionIfNeeded);
        for (KtExpression ktExpression : this.expression.getIndexExpressions()) {
            map.put(ktExpression, context().cacheExpressionIfNeeded(Translation.translateAsExpression(ktExpression, context())));
        }
        return new CachedArrayAccessTranslator(this.expression, context().innerContextWithAliasesForExpressions(map), jsExpressionCacheExpressionIfNeeded);
    }

    private static class CachedArrayAccessTranslator extends ArrayAccessTranslator {
        private final JsExpression arrayExpression;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i == 2) {
                objArr[0] = "arrayExpression";
            } else if (i == 3 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/ArrayAccessTranslator$CachedArrayAccessTranslator";
            } else {
                objArr[0] = "expression";
            }
            if (i == 3) {
                objArr[1] = "getArrayExpression";
            } else if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/reference/ArrayAccessTranslator$CachedArrayAccessTranslator";
            } else {
                objArr[1] = "getCached";
            }
            if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.js.translate.reference.ArrayAccessTranslator, org.jetbrains.kotlin.js.translate.reference.AccessTranslator
        public AccessTranslator getCached() {
            return this;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected CachedArrayAccessTranslator(KtArrayAccessExpression ktArrayAccessExpression, TranslationContext translationContext, JsExpression jsExpression) {
            super(ktArrayAccessExpression, translationContext);
            if (ktArrayAccessExpression == null) {
                $$$reportNull$$$0(0);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(1);
            }
            if (jsExpression == null) {
                $$$reportNull$$$0(2);
            }
            this.arrayExpression = jsExpression;
        }

        @Override // org.jetbrains.kotlin.js.translate.reference.ArrayAccessTranslator
        protected JsExpression getArrayExpression() {
            JsExpression jsExpression = this.arrayExpression;
            if (jsExpression == null) {
                $$$reportNull$$$0(3);
            }
            return jsExpression;
        }
    }
}
