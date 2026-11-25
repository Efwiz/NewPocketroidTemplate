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
/* synthetic */ class DifferenceCalculatorForClass$difference$unused$1 extends FunctionReference implements Function1<ProtoBuf.Class, List<ProtoBuf.Function>> {
    public static final DifferenceCalculatorForClass$difference$unused$1 INSTANCE = new DifferenceCalculatorForClass$difference$unused$1();

    DifferenceCalculatorForClass$difference$unused$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getFunctionList()Ljava/util/List;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getFunctionList";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ProtoBuf.Class.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final List<ProtoBuf.Function> invoke(ProtoBuf.Class p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return p0.getFunctionList();
    }
}
