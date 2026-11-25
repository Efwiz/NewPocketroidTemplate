package org.jetbrains.kotlin.contracts.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ESEffect.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0000H&¢\u0006\u0002\u0010\u0006\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "", "()V", "isImplies", "", "other", "(Lorg/jetbrains/kotlin/contracts/model/ESEffect;)Ljava/lang/Boolean;", "Lorg/jetbrains/kotlin/contracts/model/ConditionalEffect;", "Lorg/jetbrains/kotlin/contracts/model/SimpleEffect;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ESEffect {
    public /* synthetic */ ESEffect(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Boolean isImplies(ESEffect other);

    private ESEffect() {
    }
}
