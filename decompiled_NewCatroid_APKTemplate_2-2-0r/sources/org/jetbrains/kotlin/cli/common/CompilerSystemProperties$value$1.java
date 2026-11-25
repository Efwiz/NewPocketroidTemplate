package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: Properties.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CompilerSystemProperties$value$1 extends FunctionReference implements Function1<String, String> {
    public static final CompilerSystemProperties$value$1 INSTANCE = new CompilerSystemProperties$value$1();

    CompilerSystemProperties$value$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getProperty(Ljava/lang/String;)Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getProperty";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(System.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final String invoke(String str) {
        return System.getProperty(str);
    }
}
