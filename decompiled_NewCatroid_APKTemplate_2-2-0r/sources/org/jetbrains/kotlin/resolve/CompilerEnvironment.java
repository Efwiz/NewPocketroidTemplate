package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;

/* JADX INFO: compiled from: CompilerEnvironment.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CompilerEnvironment;", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "()V", "configure", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilerEnvironment extends TargetEnvironment {
    public static final CompilerEnvironment INSTANCE = new CompilerEnvironment();

    private CompilerEnvironment() {
        super("Compiler");
    }

    @Override // org.jetbrains.kotlin.resolve.TargetEnvironment
    public void configure(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        TargetEnvironment.INSTANCE.configureCompilerEnvironment(container);
        DslKt.useInstance(container, ControlFlowInformationProviderImpl.Factory.INSTANCE);
    }
}
