package org.jetbrains.kotlin.resolve.calls.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;

/* JADX INFO: loaded from: classes6.dex */
public class VarargValueArgument implements ResolvedValueArgument {
    private final List<ValueArgument> arguments;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "argument";
        } else if (i != 2) {
            objArr[0] = "arguments";
        } else {
            objArr[0] = "org/jetbrains/kotlin/resolve/calls/model/VarargValueArgument";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/model/VarargValueArgument";
        } else {
            objArr[1] = "getArguments";
        }
        if (i == 1) {
            objArr[2] = "addArgument";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public VarargValueArgument() {
        this.arguments = new ArrayList();
    }

    public VarargValueArgument(List<? extends ValueArgument> list) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        this.arguments = new ArrayList(list);
    }

    public void addArgument(ValueArgument valueArgument) {
        if (valueArgument == null) {
            $$$reportNull$$$0(1);
        }
        this.arguments.add(valueArgument);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument
    public List<ValueArgument> getArguments() {
        List<ValueArgument> list = this.arguments;
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        return list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("vararg:{");
        Iterator<ValueArgument> it = this.arguments.iterator();
        while (it.hasNext()) {
            KtExpression argumentExpression = it.next().getArgumentExpression();
            sb.append(argumentExpression == null ? "no expression" : argumentExpression.getText());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append("}").toString();
    }
}
