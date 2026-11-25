package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&\u0082\u0001\u0003\u0007\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "", "()V", "toKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "Lorg/jetbrains/kotlin/contracts/model/structure/ESBooleanType;", "Lorg/jetbrains/kotlin/contracts/model/structure/ESBuiltInType;", "Lorg/jetbrains/kotlin/contracts/model/structure/ESKotlinType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ESType {
    public /* synthetic */ ESType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract KotlinType toKotlinType(KotlinBuiltIns builtIns);

    private ESType() {
    }
}
