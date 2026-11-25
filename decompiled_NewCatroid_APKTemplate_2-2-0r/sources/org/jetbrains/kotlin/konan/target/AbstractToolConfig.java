package org.jetbrains.kotlin.konan.target;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.konan.util.SubstitutionKt;

/* JADX INFO: compiled from: AbstractToolConfig.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\u0006\u0010\"\u001a\u00020#J\b\u0010$\u001a\u00020#H&J\u0006\u0010%\u001a\u00020#R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\u0013X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000fR\u0011\u0010\u001d\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/AbstractToolConfig;", "", "konanHome", "", "userProvidedTargetName", "propertyOverrides", "", "konanDataDir", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)V", "distribution", "Lorg/jetbrains/kotlin/konan/target/Distribution;", "host", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "libclang", "getLibclang", "()Ljava/lang/String;", "llvmHome", "getLlvmHome", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/konan/target/Platform;", "getPlatform", "()Lorg/jetbrains/kotlin/konan/target/Platform;", "platformManager", "Lorg/jetbrains/kotlin/konan/target/PlatformManager;", "substitutions", "getSubstitutions", "()Ljava/util/Map;", "sysRoot", "getSysRoot", "target", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetManager", "Lorg/jetbrains/kotlin/konan/target/TargetManager;", "downloadDependencies", "", "loadLibclang", "prepare", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class AbstractToolConfig {
    private final Distribution distribution;
    private final KonanTarget host;
    private final String libclang;
    private final String llvmHome;
    private final Platform platform;
    private final PlatformManager platformManager;
    private final Map<String, String> substitutions;
    private final String sysRoot;
    private final KonanTarget target;
    private final TargetManager targetManager;

    public abstract void loadLibclang();

    public AbstractToolConfig(String konanHome, String str, Map<String, String> propertyOverrides, String str2) {
        Intrinsics.checkNotNullParameter(konanHome, "konanHome");
        Intrinsics.checkNotNullParameter(propertyOverrides, "propertyOverrides");
        Distribution distribution = new Distribution(konanHome, false, null, propertyOverrides, str2, 6, null);
        this.distribution = distribution;
        PlatformManager platformManager = new PlatformManager(distribution, false, 2, null);
        this.platformManager = platformManager;
        TargetManager targetManager = platformManager.targetManager(str);
        this.targetManager = targetManager;
        KonanTarget host = HostManager.INSTANCE.getHost();
        this.host = host;
        KonanTarget target = targetManager.getTarget();
        this.target = target;
        Platform platform = platformManager.platform(target);
        this.platform = platform;
        this.substitutions = SubstitutionKt.defaultTargetSubstitutions(target);
        String absoluteLlvmHome = platform.getAbsoluteLlvmHome();
        this.llvmHome = absoluteLlvmHome;
        this.sysRoot = platform.getAbsoluteTargetSysRoot();
        this.libclang = Intrinsics.areEqual(host, KonanTarget.MINGW_X64.INSTANCE) ? absoluteLlvmHome + "/bin/libclang.dll" : absoluteLlvmHome + "/lib/" + System.mapLibraryName("clang");
    }

    public /* synthetic */ AbstractToolConfig(String str, String str2, Map map, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, map, (i & 8) != 0 ? null : str3);
    }

    public final KonanTarget getTarget() {
        return this.target;
    }

    protected final Platform getPlatform() {
        return this.platform;
    }

    public final Map<String, String> getSubstitutions() {
        return this.substitutions;
    }

    public final void downloadDependencies() {
        this.platform.downloadDependencies();
    }

    public final String getLlvmHome() {
        return this.llvmHome;
    }

    public final String getSysRoot() {
        return this.sysRoot;
    }

    public final String getLibclang() {
        return this.libclang;
    }

    public final void prepare() {
        downloadDependencies();
        loadLibclang();
    }
}
