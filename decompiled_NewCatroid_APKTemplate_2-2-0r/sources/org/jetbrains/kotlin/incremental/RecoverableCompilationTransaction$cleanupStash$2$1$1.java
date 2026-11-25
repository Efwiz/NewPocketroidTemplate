package org.jetbrains.kotlin.incremental;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: CompilationTransaction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class RecoverableCompilationTransaction$cleanupStash$2$1$1 extends FunctionReference implements Function1<Path, Unit> {
    public static final RecoverableCompilationTransaction$cleanupStash$2$1$1 INSTANCE = new RecoverableCompilationTransaction$cleanupStash$2$1$1();

    RecoverableCompilationTransaction$cleanupStash$2$1$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "delete(Ljava/nio/file/Path;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "delete";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Files.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Path path) throws IOException {
        invoke2(path);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(Path path) throws IOException {
        Files.delete(path);
    }
}
