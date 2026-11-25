package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: klib.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class KlibKt$JsFactories$1 extends FunctionReference implements Function1<StorageManager, KlibKt.AnonymousClass1> {
    public static final KlibKt$JsFactories$1 INSTANCE = new KlibKt$JsFactories$1();

    KlibKt$JsFactories$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "createBuiltIns(Lorg/jetbrains/kotlin/storage/StorageManager;)Lorg/jetbrains/kotlin/ir/backend/js/KlibKt$createBuiltIns$1;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "createBuiltIns";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(KlibKt.class, "ir.serialization.js");
    }

    @Override // kotlin.jvm.functions.Function1
    public final KlibKt.AnonymousClass1 invoke(StorageManager p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return KlibKt.createBuiltIns(p0);
    }
}
