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
/* synthetic */ class DaemonOptions$mappers$7 extends FunctionReference implements Function1<String, Integer> {
    public static final DaemonOptions$mappers$7 INSTANCE = new DaemonOptions$mappers$7();

    DaemonOptions$mappers$7() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "toInt(Ljava/lang/String;)I";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "toInt";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(StringsKt.class, "daemon-common");
    }

    @Override // kotlin.jvm.functions.Function1
    public final Integer invoke(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Integer.valueOf(Integer.parseInt(p0));
    }
}
