package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DifferenceCalculator$calcDifferenceForMembers$newMap$1$3 extends FunctionReference implements Function1<Integer, ProtoBuf.Type> {
    DifferenceCalculator$calcDifferenceForMembers$newMap$1$3(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "newGetTypeById(I)Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "newGetTypeById";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ProtoCompareGenerated.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ ProtoBuf.Type invoke(Integer num) {
        return invoke(num.intValue());
    }

    public final ProtoBuf.Type invoke(int i) {
        return ((ProtoCompareGenerated) this.receiver).newGetTypeById(i);
    }
}
