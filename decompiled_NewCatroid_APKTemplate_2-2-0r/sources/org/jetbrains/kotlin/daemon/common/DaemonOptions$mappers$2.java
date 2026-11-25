package org.jetbrains.kotlin.daemon.common;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DaemonOptions$mappers$2 extends FunctionReference implements Function1<String, String> {
    public static final DaemonOptions$mappers$2 INSTANCE = new DaemonOptions$mappers$2();

    DaemonOptions$mappers$2() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "trimQuotes(Ljava/lang/String;)Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "trimQuotes";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(DaemonParamsKt.class, "daemon-common");
    }

    @Override // kotlin.jvm.functions.Function1
    public final String invoke(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return DaemonParamsKt.trimQuotes(p0);
    }
}
