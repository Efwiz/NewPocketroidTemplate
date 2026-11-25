package org.jetbrains.kotlin.konan.target;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader;
import org.jetbrains.kotlin.konan.util.InternalServer;

/* JADX INFO: compiled from: Windows.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u000f\u001a\u00020\u00108VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R!\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u000eR\u001b\u0010\u0018\u001a\u00020\u00198VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0014\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u001e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u0014\u001a\u0004\b\u001f\u0010 ¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/konan/target/MingwConfigurablesImpl;", "Lorg/jetbrains/kotlin/konan/target/MingwConfigurables;", "Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dependenciesRoot", "", "(Lorg/jetbrains/kotlin/konan/target/KonanTarget;Ljava/util/Properties;Ljava/lang/String;)V", "dependencies", "", "getDependencies", "()Ljava/util/List;", "msvc", "Lorg/jetbrains/kotlin/konan/target/Msvc;", "getMsvc", "()Lorg/jetbrains/kotlin/konan/target/Msvc;", "msvc$delegate", "Lkotlin/Lazy;", "windowsHostDependencies", "getWindowsHostDependencies", "windowsHostDependencies$delegate", "windowsKit", "Lorg/jetbrains/kotlin/konan/target/WindowsKit;", "getWindowsKit", "()Lorg/jetbrains/kotlin/konan/target/WindowsKit;", "windowsKit$delegate", "windowsSdkPartsProvider", "Lorg/jetbrains/kotlin/konan/target/WindowsSdkPartsProvider;", "getWindowsSdkPartsProvider", "()Lorg/jetbrains/kotlin/konan/target/WindowsSdkPartsProvider;", "windowsSdkPartsProvider$delegate", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MingwConfigurablesImpl extends KonanPropertiesLoader implements MingwConfigurables {

    /* JADX INFO: renamed from: msvc$delegate, reason: from kotlin metadata */
    private final Lazy msvc;

    /* JADX INFO: renamed from: windowsHostDependencies$delegate, reason: from kotlin metadata */
    private final Lazy windowsHostDependencies;

    /* JADX INFO: renamed from: windowsKit$delegate, reason: from kotlin metadata */
    private final Lazy windowsKit;

    /* JADX INFO: renamed from: windowsSdkPartsProvider$delegate, reason: from kotlin metadata */
    private final Lazy windowsSdkPartsProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MingwConfigurablesImpl(KonanTarget target, Properties properties, String str) {
        super(target, properties, str, null, 8, null);
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(properties, "properties");
        this.windowsKit = LazyKt.lazy(new Function0<WindowsKit>() { // from class: org.jetbrains.kotlin.konan.target.MingwConfigurablesImpl$windowsKit$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final WindowsKit invoke() {
                WindowsSdkPartsProvider windowsSdkPartsProvider = this.this$0.getWindowsSdkPartsProvider();
                if (Intrinsics.areEqual(windowsSdkPartsProvider, WindowsSdkPartsProvider.InternalServer.INSTANCE)) {
                    MingwConfigurablesImpl mingwConfigurablesImpl = this.this$0;
                    Path path = Paths.get(mingwConfigurablesImpl.absolute(mingwConfigurablesImpl.getWindowsKitParts()), new String[0]);
                    Intrinsics.checkNotNullExpressionValue(path, "get(absolute(windowsKitParts))");
                    return WindowsKt.createCustomWindowsKitPath(path);
                }
                if (Intrinsics.areEqual(windowsSdkPartsProvider, WindowsSdkPartsProvider.Local.INSTANCE)) {
                    return WindowsKit.DefaultPath.INSTANCE;
                }
                throw new NoWhenBranchMatchedException();
            }
        });
        this.msvc = LazyKt.lazy(new Function0<Msvc>() { // from class: org.jetbrains.kotlin.konan.target.MingwConfigurablesImpl$msvc$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Msvc invoke() {
                WindowsSdkPartsProvider windowsSdkPartsProvider = this.this$0.getWindowsSdkPartsProvider();
                if (Intrinsics.areEqual(windowsSdkPartsProvider, WindowsSdkPartsProvider.InternalServer.INSTANCE)) {
                    MingwConfigurablesImpl mingwConfigurablesImpl = this.this$0;
                    Path path = Paths.get(mingwConfigurablesImpl.absolute(mingwConfigurablesImpl.getMsvcParts()), new String[0]);
                    Intrinsics.checkNotNullExpressionValue(path, "get(absolute(msvcParts))");
                    return WindowsKt.createCustomMsvcPath(path);
                }
                if (Intrinsics.areEqual(windowsSdkPartsProvider, WindowsSdkPartsProvider.Local.INSTANCE)) {
                    return Msvc.DefaultPath.INSTANCE;
                }
                throw new NoWhenBranchMatchedException();
            }
        });
        this.windowsSdkPartsProvider = LazyKt.lazy(new Function0<WindowsSdkPartsProvider>() { // from class: org.jetbrains.kotlin.konan.target.MingwConfigurablesImpl$windowsSdkPartsProvider$2
            @Override // kotlin.jvm.functions.Function0
            public final WindowsSdkPartsProvider invoke() {
                if (InternalServer.INSTANCE.isAvailable()) {
                    return WindowsSdkPartsProvider.InternalServer.INSTANCE;
                }
                return WindowsSdkPartsProvider.Local.INSTANCE;
            }
        });
        this.windowsHostDependencies = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.target.MingwConfigurablesImpl$windowsHostDependencies$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends String> invoke() {
                WindowsSdkPartsProvider windowsSdkPartsProvider = this.this$0.getWindowsSdkPartsProvider();
                if (Intrinsics.areEqual(windowsSdkPartsProvider, WindowsSdkPartsProvider.InternalServer.INSTANCE)) {
                    return CollectionsKt.listOf((Object[]) new String[]{this.this$0.getWindowsKitParts(), this.this$0.getMsvcParts()});
                }
                if (Intrinsics.areEqual(windowsSdkPartsProvider, WindowsSdkPartsProvider.Local.INSTANCE)) {
                    return CollectionsKt.emptyList();
                }
                throw new NoWhenBranchMatchedException();
            }
        });
    }

    @Override // org.jetbrains.kotlin.konan.target.MingwConfigurables
    public WindowsKit getWindowsKit() {
        return (WindowsKit) this.windowsKit.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.target.MingwConfigurables
    public Msvc getMsvc() {
        return (Msvc) this.msvc.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final WindowsSdkPartsProvider getWindowsSdkPartsProvider() {
        return (WindowsSdkPartsProvider) this.windowsSdkPartsProvider.getValue();
    }

    private final List<String> getWindowsHostDependencies() {
        return (List) this.windowsHostDependencies.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader
    public List<String> getDependencies() {
        List<String> listEmptyList;
        List<String> dependencies = super.getDependencies();
        if (HostManager.INSTANCE.getHostIsMingw()) {
            listEmptyList = getWindowsHostDependencies();
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        return CollectionsKt.plus((Collection) dependencies, (Iterable) listEmptyList);
    }
}
