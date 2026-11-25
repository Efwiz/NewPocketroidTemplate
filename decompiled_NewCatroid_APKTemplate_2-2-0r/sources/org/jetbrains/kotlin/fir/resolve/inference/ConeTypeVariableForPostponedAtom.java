package org.jetbrains.kotlin.fir.resolve.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;

/* JADX INFO: compiled from: ConeTypeVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeVariableForPostponedAtom;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "name", "", "(Ljava/lang/String;)V", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeVariableForPostponedAtom extends ConeTypeVariable {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeTypeVariableForPostponedAtom(String name2) {
        super(name2, null, 2, null);
        Intrinsics.checkNotNullParameter(name2, "name");
    }
}
