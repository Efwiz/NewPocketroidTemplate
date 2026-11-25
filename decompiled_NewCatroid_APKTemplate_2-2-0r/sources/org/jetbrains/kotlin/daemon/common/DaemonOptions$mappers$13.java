package org.jetbrains.kotlin.daemon.common;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DaemonOptions$mappers$13 extends FunctionReference implements Function1<String, Long> {
    public static final DaemonOptions$mappers$13 INSTANCE = new DaemonOptions$mappers$13();

    DaemonOptions$mappers$13() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "toLong(Ljava/lang/String;)J";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "toLong";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(StringsKt.class, "daemon-common");
    }

    @Override // kotlin.jvm.functions.Function1
    public final Long invoke(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Long.valueOf(Long.parseLong(p0));
    }
}
