package org.jetbrains.kotlin.cli.js.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.js.util.TextOutputImpl;

/* JADX INFO: compiled from: JSStdlibLinker.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JSStdlibLinker$mergeStdlibParts$sourceMapBuilder$1 extends FunctionReference implements Function0<Integer> {
    JSStdlibLinker$mergeStdlibParts$sourceMapBuilder$1(Object obj) {
        super(0, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getColumn()I";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getColumn";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(TextOutputImpl.class);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    public final Integer invoke() {
        return Integer.valueOf(((TextOutputImpl) this.receiver).getColumn());
    }
}
