package org.jetbrains.kotlin.fir.extensions;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.fir.SessionConfiguration;

/* JADX INFO: compiled from: FirExtensionRegistrar.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"registerExtensions", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;", "registeredExtensions", "Lorg/jetbrains/kotlin/fir/extensions/BunchOfRegisteredExtensions;", "entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirExtensionRegistrarKt {
    @SessionConfiguration
    public static final void registerExtensions(FirExtensionService firExtensionService, BunchOfRegisteredExtensions registeredExtensions) {
        Intrinsics.checkNotNullParameter(firExtensionService, "<this>");
        Intrinsics.checkNotNullParameter(registeredExtensions, "registeredExtensions");
        for (Map.Entry<KClass<? extends FirExtension>, List<FirExtension.Factory<FirExtension>>> entry : registeredExtensions.getExtensions().entrySet()) {
            firExtensionService.registerExtensions(entry.getKey(), entry.getValue());
        }
        for (FirExtensionSessionComponent firExtensionSessionComponent : FirExtensionSessionComponentKt.getExtensionSessionComponents(firExtensionService)) {
            firExtensionService.getSession().register(firExtensionSessionComponent.getComponentClass(), firExtensionSessionComponent);
        }
        FirRegisteredPluginAnnotationsKt.getRegisteredPluginAnnotations(firExtensionService.getSession()).initialize();
    }
}
