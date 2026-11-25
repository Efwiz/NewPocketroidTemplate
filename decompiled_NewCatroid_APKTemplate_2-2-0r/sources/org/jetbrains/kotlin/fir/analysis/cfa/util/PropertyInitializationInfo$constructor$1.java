package org.jetbrains.kotlin.fir.analysis.cfa.util;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: PropertyInitializationInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class PropertyInitializationInfo$constructor$1 extends FunctionReference implements Function1<PersistentMap<FirPropertySymbol, ? extends EventOccurrencesRange>, PropertyInitializationInfo> {
    public static final PropertyInitializationInfo$constructor$1 INSTANCE = new PropertyInitializationInfo$constructor$1();

    PropertyInitializationInfo$constructor$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lkotlinx/collections/immutable/PersistentMap;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(PropertyInitializationInfo.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final PropertyInitializationInfo invoke(PersistentMap<FirPropertySymbol, ? extends EventOccurrencesRange> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new PropertyInitializationInfo(p0);
    }
}
