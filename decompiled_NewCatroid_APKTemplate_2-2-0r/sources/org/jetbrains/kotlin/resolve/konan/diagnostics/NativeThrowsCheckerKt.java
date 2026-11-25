package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: compiled from: NativeThrowsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002¨\u0006\u0005"}, d2 = {"hasUnresolvedArgumentsRecursive", "", "Lorg/jetbrains/kotlin/psi/Call;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "frontend.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NativeThrowsCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasUnresolvedArgumentsRecursive(Call call, BindingContext bindingContext) {
        Call call2;
        if (CallUtilKt.hasUnresolvedArguments(call, bindingContext, StatementFilter.NONE)) {
            return true;
        }
        List<? extends ValueArgument> valueArguments = call.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        List<? extends ValueArgument> list = valueArguments;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KtExpression argumentExpression = ((ValueArgument) it.next()).getArgumentExpression();
                if (argumentExpression != null && (call2 = CallUtilKt.getCall(argumentExpression, bindingContext)) != null && hasUnresolvedArgumentsRecursive(call2, bindingContext)) {
                    return true;
                }
            }
        }
        return false;
    }
}
