package org.jetbrains.kotlin.js.translate.expression;

import java.util.ArrayList;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.reference.CallExpressionTranslator;
import org.jetbrains.kotlin.js.translate.utils.InlineUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: loaded from: classes5.dex */
public class DestructuringDeclarationTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KtDestructuringDeclaration multiDeclaration;
    private final JsExpression multiObjectExpr;

    /* JADX WARN: Code duplicated, block: B:18:0x0028  */
    /* JADX WARN: Code duplicated, block: B:19:0x002d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "multiObjectExpr";
        } else if (i == 2) {
            objArr[0] = "context";
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/expression/DestructuringDeclarationTranslator";
        } else if (i == 5) {
            objArr[0] = "multiObjectExpr";
        } else if (i != 6) {
            objArr[0] = "multiDeclaration";
        } else {
            objArr[0] = "context";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/expression/DestructuringDeclarationTranslator";
        } else {
            objArr[1] = "translate";
        }
        if (i != 3) {
            if (i == 4 || i == 5 || i == 6) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "translate";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    public static JsVars translate(KtDestructuringDeclaration ktDestructuringDeclaration, JsExpression jsExpression, TranslationContext translationContext) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(0);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        JsVars jsVarsTranslate = new DestructuringDeclarationTranslator(ktDestructuringDeclaration, jsExpression, translationContext).translate();
        if (jsVarsTranslate == null) {
            $$$reportNull$$$0(3);
        }
        return jsVarsTranslate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private DestructuringDeclarationTranslator(KtDestructuringDeclaration ktDestructuringDeclaration, JsExpression jsExpression, TranslationContext translationContext) {
        super(translationContext);
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(4);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(5);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(6);
        }
        this.multiDeclaration = ktDestructuringDeclaration;
        this.multiObjectExpr = jsExpression;
    }

    private JsVars translate() {
        ArrayList arrayList = new ArrayList();
        for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : this.multiDeclaration.getEntries()) {
            VariableDescriptor variableDescriptor = (VariableDescriptor) BindingContextUtils.getNotNull(context().bindingContext(), BindingContext.VARIABLE, ktDestructuringDeclarationEntry);
            if (!variableDescriptor.getShortName().isSpecial()) {
                ResolvedCall resolvedCall = (ResolvedCall) context().bindingContext().get(BindingContext.COMPONENT_RESOLVED_CALL, ktDestructuringDeclarationEntry);
                JsExpression jsExpressionTranslate = CallTranslator.translate(context(), resolvedCall, this.multiObjectExpr);
                if (CallExpressionTranslator.shouldBeInlined((FunctionDescriptor) resolvedCall.get$suspendPropertyDescriptor(), context())) {
                    InlineUtils.setInlineCallMetadata(jsExpressionTranslate, ktDestructuringDeclarationEntry, (ResolvedCall<?>) resolvedCall, context());
                }
                JsExpression jsExpressionCoerce = TranslationUtils.coerce(context(), jsExpressionTranslate, variableDescriptor.getType());
                JsName nameForDescriptor = context().getNameForDescriptor(variableDescriptor);
                if (BindingContextUtils.isBoxedLocalCapturedInClosure(context().bindingContext(), variableDescriptor)) {
                    jsExpressionCoerce = JsAstUtils.wrapValue(Namer.getCapturedVarAccessor(nameForDescriptor.makeRef()), jsExpressionCoerce);
                }
                JsVars.JsVar jsVar = new JsVars.JsVar(nameForDescriptor, jsExpressionCoerce);
                jsVar.setSource(ktDestructuringDeclarationEntry);
                arrayList.add(jsVar);
            }
        }
        JsVars jsVars = new JsVars(arrayList, true);
        jsVars.setSource(this.multiDeclaration);
        return jsVars;
    }
}
