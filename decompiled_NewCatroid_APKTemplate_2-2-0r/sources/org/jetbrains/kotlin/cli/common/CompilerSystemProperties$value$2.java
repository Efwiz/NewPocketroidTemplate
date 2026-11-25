package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: Properties.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CompilerSystemProperties$value$2 extends FunctionReference implements Function2<String, String, String> {
    public static final CompilerSystemProperties$value$2 INSTANCE = new CompilerSystemProperties$value$2();

    CompilerSystemProperties$value$2() {
        super(2);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "setProperty";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(System.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final String invoke(String str, String str2) {
        return System.setProperty(str, str2);
    }
}
