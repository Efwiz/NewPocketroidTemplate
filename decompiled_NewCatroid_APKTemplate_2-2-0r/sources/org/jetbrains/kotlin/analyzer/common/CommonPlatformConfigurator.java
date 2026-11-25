package org.jetbrains.kotlin.analyzer.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBase;

/* JADX INFO: compiled from: CommonPlatform.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonPlatformConfigurator;", "Lorg/jetbrains/kotlin/resolve/PlatformConfiguratorBase;", "()V", "configureModuleComponents", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CommonPlatformConfigurator extends PlatformConfiguratorBase {
    public static final CommonPlatformConfigurator INSTANCE = new CommonPlatformConfigurator();

    @Override // org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleComponents(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
    }

    private CommonPlatformConfigurator() {
        super(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 32767, null);
    }
}
