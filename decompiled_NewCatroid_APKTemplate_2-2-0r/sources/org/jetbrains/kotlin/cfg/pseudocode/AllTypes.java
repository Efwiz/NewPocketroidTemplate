package org.jetbrains.kotlin.cfg.pseudocode;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: TypePredicate.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/AllTypes;", "Lorg/jetbrains/kotlin/cfg/pseudocode/TypePredicate;", "()V", "invoke", "", "typeToCheck", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)Ljava/lang/Boolean;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AllTypes implements TypePredicate {
    public static final AllTypes INSTANCE = new AllTypes();

    private AllTypes() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function1
    public Boolean invoke(KotlinType typeToCheck) {
        Intrinsics.checkNotNullParameter(typeToCheck, "typeToCheck");
        return true;
    }

    public String toString() {
        return "*";
    }
}
