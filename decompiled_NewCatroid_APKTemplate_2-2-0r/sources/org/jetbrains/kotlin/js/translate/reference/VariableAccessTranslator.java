package org.jetbrains.kotlin.js.translate.reference;

import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.utils.InlineUtils;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;

/* JADX INFO: loaded from: classes5.dex */
public class VariableAccessTranslator extends AbstractTranslator implements AccessTranslator {
    private final JsExpression receiver;
    private final KtReferenceExpression referenceExpression;
    private final ResolvedCall<? extends VariableDescriptor> resolvedCall;

    /* JADX WARN: Code duplicated, block: B:23:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 7) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "referenceExpression";
        } else if (i == 4) {
            objArr[0] = "resolvedCall";
        } else if (i == 5) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/reference/VariableAccessTranslator";
        } else if (i == 6) {
            objArr[0] = "setTo";
        } else if (i != 7) {
            objArr[0] = "context";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/translate/reference/VariableAccessTranslator";
        }
        if (i == 5) {
            objArr[1] = "translateAsGet";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/VariableAccessTranslator";
        } else {
            objArr[1] = "translateAsSet";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
                objArr[2] = "<init>";
                break;
            case 5:
            case 7:
                break;
            case 6:
                objArr[2] = "translateAsSet";
                break;
            default:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static VariableAccessTranslator newInstance(TranslationContext translationContext, KtReferenceExpression ktReferenceExpression, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(0);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(1);
        }
        ResolvedCall variableResolvedCallWithAssert = CallUtilKt.getVariableResolvedCallWithAssert(ktReferenceExpression, translationContext.bindingContext());
        if (variableResolvedCallWithAssert instanceof VariableAsFunctionResolvedCall) {
            variableResolvedCallWithAssert = ((VariableAsFunctionResolvedCall) variableResolvedCallWithAssert).getVariableCall();
        }
        return new VariableAccessTranslator(translationContext, ktReferenceExpression, variableResolvedCallWithAssert, jsExpression);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private VariableAccessTranslator(TranslationContext translationContext, KtReferenceExpression ktReferenceExpression, ResolvedCall<? extends VariableDescriptor> resolvedCall, JsExpression jsExpression) {
        super(translationContext);
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(3);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(4);
        }
        this.referenceExpression = ktReferenceExpression;
        this.receiver = jsExpression;
        this.resolvedCall = resolvedCall;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsGet() {
        JsExpression jsExpressionTranslateGet = CallTranslator.INSTANCE.translateGet(context(), this.resolvedCall, this.receiver);
        CallableDescriptor original = ((VariableDescriptor) this.resolvedCall.getResultingDescriptor()).getOriginal();
        if (original instanceof PropertyDescriptor) {
            PropertyGetterDescriptor getter = ((PropertyDescriptor) original).getGetter();
            if (InlineUtil.isInline(getter)) {
                if (jsExpressionTranslateGet instanceof JsNameRef) {
                    InlineUtils.setInlineCallMetadata((JsNameRef) jsExpressionTranslateGet, (KtElement) this.referenceExpression, (CallableDescriptor) getter, context());
                } else {
                    InlineUtils.setInlineCallMetadata(jsExpressionTranslateGet, this.referenceExpression, getter, context());
                }
            }
        } else if (original instanceof LocalVariableDescriptor) {
            LocalVariableDescriptor localVariableDescriptor = (LocalVariableDescriptor) original;
            if (localVariableDescriptor.isLateInit()) {
                return new JsConditional(new JsBinaryOperation(JsBinaryOperator.EQ, jsExpressionTranslateGet, new JsNullLiteral()), new JsInvocation(context().getReferenceToIntrinsic(Namer.THROW_UNINITIALIZED_PROPERTY_ACCESS_EXCEPTION), new JsStringLiteral(localVariableDescriptor.getShortName().asString())), jsExpressionTranslateGet);
            }
        }
        if (jsExpressionTranslateGet == null) {
            $$$reportNull$$$0(5);
        }
        return jsExpressionTranslateGet;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0054  */
    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsSet(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(6);
        }
        JsExpression jsExpressionTranslateSet = CallTranslator.INSTANCE.translateSet(context(), this.resolvedCall, jsExpression, this.receiver);
        CallableDescriptor original = ((VariableDescriptor) this.resolvedCall.getResultingDescriptor()).getOriginal();
        if (original instanceof PropertyDescriptor) {
            PropertySetterDescriptor setter = ((PropertyDescriptor) original).getSetter();
            if (InlineUtil.isInline(setter)) {
                if (jsExpressionTranslateSet instanceof JsBinaryOperation) {
                    JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) jsExpressionTranslateSet;
                    if (jsBinaryOperation.getOperator().isAssignment()) {
                        InlineUtils.setInlineCallMetadata((JsNameRef) jsBinaryOperation.getArg1(), (KtElement) this.referenceExpression, (CallableDescriptor) setter, context());
                    } else {
                        InlineUtils.setInlineCallMetadata(jsExpressionTranslateSet, this.referenceExpression, setter, context());
                    }
                } else {
                    InlineUtils.setInlineCallMetadata(jsExpressionTranslateSet, this.referenceExpression, setter, context());
                }
            }
        }
        if (jsExpressionTranslateSet == null) {
            $$$reportNull$$$0(7);
        }
        return jsExpressionTranslateSet;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public AccessTranslator getCached() {
        return new CachedVariableAccessTranslator(context(), this.referenceExpression, this.resolvedCall, this.receiver != null ? context().cacheExpressionIfNeeded(this.receiver) : null);
    }

    private static class CachedVariableAccessTranslator extends VariableAccessTranslator implements AccessTranslator {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "referenceExpression";
            } else if (i == 2) {
                objArr[0] = "resolvedCall";
            } else if (i != 3) {
                objArr[0] = "context";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/VariableAccessTranslator$CachedVariableAccessTranslator";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/reference/VariableAccessTranslator$CachedVariableAccessTranslator";
            } else {
                objArr[1] = "getCached";
            }
            if (i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.js.translate.reference.VariableAccessTranslator, org.jetbrains.kotlin.js.translate.reference.AccessTranslator
        public AccessTranslator getCached() {
            return this;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CachedVariableAccessTranslator(TranslationContext translationContext, KtReferenceExpression ktReferenceExpression, ResolvedCall<? extends VariableDescriptor> resolvedCall, JsExpression jsExpression) {
            super(translationContext, ktReferenceExpression, resolvedCall, jsExpression);
            if (translationContext == null) {
                $$$reportNull$$$0(0);
            }
            if (ktReferenceExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (resolvedCall == null) {
                $$$reportNull$$$0(2);
            }
        }
    }
}
