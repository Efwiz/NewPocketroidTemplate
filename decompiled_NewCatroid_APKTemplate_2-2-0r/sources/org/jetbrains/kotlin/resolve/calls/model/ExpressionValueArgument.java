package org.jetbrains.kotlin.resolve.calls.model;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;

/* JADX INFO: loaded from: classes6.dex */
public class ExpressionValueArgument implements ResolvedValueArgument {
    private final ValueArgument valueArgument;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/resolve/calls/model/ExpressionValueArgument", "getArguments"));
    }

    public ExpressionValueArgument(ValueArgument valueArgument) {
        this.valueArgument = valueArgument;
    }

    public ValueArgument getValueArgument() {
        return this.valueArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument
    public List<ValueArgument> getArguments() {
        ValueArgument valueArgument = this.valueArgument;
        if (valueArgument == null) {
            List<ValueArgument> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(0);
            }
            return listEmptyList;
        }
        List<ValueArgument> listSingletonList = Collections.singletonList(valueArgument);
        if (listSingletonList == null) {
            $$$reportNull$$$0(1);
        }
        return listSingletonList;
    }

    public String toString() {
        KtExpression argumentExpression = this.valueArgument.getArgumentExpression();
        return argumentExpression == null ? "no expression" : argumentExpression.getText();
    }
}
