package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: IncrementalJvmCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class IncrementalJvmCache$ConstantsMap$dumpValue$1 extends FunctionReference implements Function1<Long, String> {
    public static final IncrementalJvmCache$ConstantsMap$dumpValue$1 INSTANCE = new IncrementalJvmCache$ConstantsMap$dumpValue$1();

    IncrementalJvmCache$ConstantsMap$dumpValue$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "toString()Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "toString";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Long.TYPE);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ String invoke(Long l) {
        return invoke(l.longValue());
    }

    public final String invoke(long j) {
        return String.valueOf(j);
    }
}
