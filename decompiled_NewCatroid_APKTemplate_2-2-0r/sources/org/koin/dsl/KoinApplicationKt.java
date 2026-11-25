package org.koin.dsl;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.koin.core.KoinApplication;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/koin-core.jar:org/koin/dsl/KoinApplicationKt.class
  classes6.dex
 */
/* JADX INFO: compiled from: KoinApplication.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0000\u001a\u00020\u00012\u001b\u0010\u0002\u001a\u0017\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u0005¢\u0006\u0002\b\u0006*,\u0010\u0007\"\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\b\u00062\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\b\u0006¨\u0006\b"}, d2 = {"koinApplication", "Lorg/koin/core/KoinApplication;", "appDeclaration", "Lkotlin/Function1;", "", "Lorg/koin/dsl/KoinAppDeclaration;", "Lkotlin/ExtensionFunctionType;", "KoinAppDeclaration", "koin-core"}, k = 2, mv = {1, 1, 16})
public final class KoinApplicationKt {
    public static final KoinApplication koinApplication(Function1<? super KoinApplication, Unit> appDeclaration) {
        Intrinsics.checkParameterIsNotNull(appDeclaration, "appDeclaration");
        KoinApplication koinApplicationInit = KoinApplication.INSTANCE.init();
        appDeclaration.invoke(koinApplicationInit);
        koinApplicationInit.getKoin().createContextIfNeeded$koin_core();
        return koinApplicationInit;
    }
}
