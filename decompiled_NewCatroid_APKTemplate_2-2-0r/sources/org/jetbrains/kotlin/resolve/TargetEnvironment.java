package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.resolve.lazy.BasicAbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.CompilerLocalDescriptorResolver;

/* JADX INFO: compiled from: TargetEnvironment.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \n2\u00020\u0001:\u0001\nB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "", "name", "", "(Ljava/lang/String;)V", "configure", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "toString", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TargetEnvironment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String name;

    public abstract void configure(StorageComponentContainer container);

    public TargetEnvironment(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
    }

    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getName() {
        return this.name;
    }

    /* JADX INFO: compiled from: TargetEnvironment.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/TargetEnvironment$Companion;", "", "()V", "configureCompilerEnvironment", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void configureCompilerEnvironment(StorageComponentContainer container) {
            Intrinsics.checkNotNullParameter(container, "container");
            DslKt.useInstance(container, BodyResolveCache.ThrowException.INSTANCE);
            ContainerKt.registerSingleton(container, CompilerLocalDescriptorResolver.class);
            ContainerKt.registerSingleton(container, BasicAbsentDescriptorHandler.class);
            ContainerKt.registerSingleton(container, MainFunctionDetector.Factory.Ordinary.class);
        }
    }
}
