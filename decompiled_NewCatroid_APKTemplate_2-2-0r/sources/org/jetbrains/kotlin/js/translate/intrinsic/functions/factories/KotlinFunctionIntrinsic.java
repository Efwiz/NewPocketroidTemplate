package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;

/* JADX INFO: loaded from: classes5.dex */
public class KotlinFunctionIntrinsic extends FunctionIntrinsicWithReceiverComputed {
    private final JsExpression[] additionalArguments;
    private final String functionName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "arguments";
        } else if (i != 2) {
            objArr[0] = "functionName";
        } else {
            objArr[0] = "context";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/KotlinFunctionIntrinsic";
        if (i == 1 || i == 2) {
            objArr[2] = "apply";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public KotlinFunctionIntrinsic(String str, JsExpression... jsExpressionArr) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.functionName = str;
        this.additionalArguments = jsExpressionArr;
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
    public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(this.functionName, Namer.kotlinObject());
        if (this.additionalArguments.length > 0) {
            ArrayList arrayList = new ArrayList(list);
            for (JsExpression jsExpression2 : this.additionalArguments) {
                arrayList.add(jsExpression2.deepCopy());
            }
            list = arrayList;
        }
        if (jsExpression != null) {
            list = TranslationUtils.generateInvocationArguments(jsExpression, list);
        }
        return new JsInvocation(jsNameRefPureFqn, list);
    }
}
