package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\n\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/FunctionExpression;", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "contextReceiversTypes", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "getContextReceiversTypes", "()[Lorg/jetbrains/kotlin/types/UnwrappedType;", "parametersTypes", "getParametersTypes", "receiverType", "getReceiverType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "returnType", "getReturnType", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FunctionExpression extends LambdaKotlinCallArgument {
    UnwrappedType[] getContextReceiversTypes();

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument
    UnwrappedType[] getParametersTypes();

    UnwrappedType getReceiverType();

    UnwrappedType getReturnType();
}
