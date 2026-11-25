package org.jetbrains.kotlin.js.translate.intrinsic.functions.basic;

import java.util.List;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: loaded from: classes5.dex */
public final class BuiltInPropertyIntrinsic extends FunctionIntrinsicWithReceiverComputed {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String propertyName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "arguments";
        } else if (i == 2) {
            objArr[0] = "context";
        } else if (i != 3) {
            objArr[0] = "propertyName";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/basic/BuiltInPropertyIntrinsic";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/basic/BuiltInPropertyIntrinsic";
        } else {
            objArr[1] = "apply";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "apply";
        } else if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    public BuiltInPropertyIntrinsic(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.propertyName = str;
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
    public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(this.propertyName, jsExpression);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(3);
        }
        return jsNameRefPureFqn;
    }
}
