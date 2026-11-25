package org.jetbrains.kotlin.incremental;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: CompilerRunnerUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CompilerRunnerUtils$makeJvmIncrementally$files$1 extends FunctionReference implements Function1<File, Boolean> {
    public static final CompilerRunnerUtils$makeJvmIncrementally$files$1 INSTANCE = new CompilerRunnerUtils$makeJvmIncrementally$files$1();

    CompilerRunnerUtils$makeJvmIncrementally$files$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "isFile()Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "isFile";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(File.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(File p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(p0.isFile());
    }
}
