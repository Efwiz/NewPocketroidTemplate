package org.jetbrains.kotlin.incremental;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$5 extends FunctionReference implements Function1<ProtoBuf.Class, List<ProtoBuf.EnumEntry>> {
    public static final DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$5 INSTANCE = new DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$5();

    DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$5() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getEnumEntryList()Ljava/util/List;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getEnumEntryList";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ProtoBuf.Class.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final List<ProtoBuf.EnumEntry> invoke(ProtoBuf.Class p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return p0.getEnumEntryList();
    }
}
