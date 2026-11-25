package org.jetbrains.kotlin.fir.resolve.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;

/* JADX INFO: compiled from: ConeTypeVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeVariableForLambdaParameterType;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "name", "", "index", "", "(Ljava/lang/String;I)V", "getIndex", "()I", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeVariableForLambdaParameterType extends ConeTypeVariable {
    private final int index;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeTypeVariableForLambdaParameterType(String name2, int i) {
        super(name2, null, 2, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        this.index = i;
    }

    public final int getIndex() {
        return this.index;
    }
}
