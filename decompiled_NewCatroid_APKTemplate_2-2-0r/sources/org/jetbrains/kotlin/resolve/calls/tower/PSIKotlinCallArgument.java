package org.jetbrains.kotlin.resolve.calls.tower;

import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "()V", "dataFlowInfoAfterThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfoAfterThisArgument", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "dataFlowInfoBeforeThisArgument", "getDataFlowInfoBeforeThisArgument", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "getValueArgument", "()Lorg/jetbrains/kotlin/psi/ValueArgument;", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PSIKotlinCallArgument implements KotlinCallArgument {
    public abstract DataFlowInfo getDataFlowInfoAfterThisArgument();

    public abstract DataFlowInfo getDataFlowInfoBeforeThisArgument();

    public abstract ValueArgument getValueArgument();

    public String toString() {
        String text;
        String strReplace$default;
        KtExpression argumentExpression = getValueArgument().getArgumentExpression();
        return (argumentExpression == null || (text = argumentExpression.getText()) == null || (strReplace$default = StringsKt.replace$default(text, '\n', StringUtil.SPACE, false, 4, (Object) null)) == null) ? getValueArgument().toString() : strReplace$default;
    }
}
