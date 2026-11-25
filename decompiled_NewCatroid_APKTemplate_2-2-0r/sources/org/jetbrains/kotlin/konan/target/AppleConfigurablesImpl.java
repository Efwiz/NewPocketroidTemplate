package org.jetbrains.kotlin.konan.target;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader;
import org.jetbrains.kotlin.konan.util.InternalServer;

/* JADX INFO: compiled from: Apple.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002:\u0001$B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0002R\u0014\u0010\u000b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0014\u0010\u0010\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\rR\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001c¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl;", "Lorg/jetbrains/kotlin/konan/target/AppleConfigurables;", "Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dependenciesDir", "", "(Lorg/jetbrains/kotlin/konan/target/KonanTarget;Ljava/util/Properties;Ljava/lang/String;)V", "absoluteAdditionalToolsDir", "getAbsoluteAdditionalToolsDir", "()Ljava/lang/String;", "absoluteTargetSysRoot", "getAbsoluteTargetSysRoot", "absoluteTargetToolchain", "getAbsoluteTargetToolchain", "dependencies", "", "getDependencies", "()Ljava/util/List;", "sdkDependency", "toolchainDependency", "xcodeAddonDependency", "xcodePartsProvider", "Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider;", "getXcodePartsProvider", "()Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider;", "xcodePartsProvider$delegate", "Lkotlin/Lazy;", "checkXcodeVersion", "", "minimalVersion", "Lorg/jetbrains/kotlin/konan/target/XcodeVersion;", "currentVersion", "XcodePartsProvider", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class AppleConfigurablesImpl extends KonanPropertiesLoader implements AppleConfigurables {
    private final String sdkDependency;
    private final String toolchainDependency;
    private final String xcodeAddonDependency;

    /* JADX INFO: renamed from: xcodePartsProvider$delegate, reason: from kotlin metadata */
    private final Lazy xcodePartsProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppleConfigurablesImpl(KonanTarget target, final Properties properties, String str) {
        super(target, properties, str, null, 8, null);
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(properties, "properties");
        String targetSysRoot = getTargetSysRoot();
        Intrinsics.checkNotNull(targetSysRoot);
        this.sdkDependency = targetSysRoot;
        String targetToolchain = getTargetToolchain();
        Intrinsics.checkNotNull(targetToolchain);
        this.toolchainDependency = targetToolchain;
        String additionalToolsDir = getAdditionalToolsDir();
        Intrinsics.checkNotNull(additionalToolsDir);
        this.xcodeAddonDependency = additionalToolsDir;
        this.xcodePartsProvider = LazyKt.lazy(new Function0<XcodePartsProvider>() { // from class: org.jetbrains.kotlin.konan.target.AppleConfigurablesImpl$xcodePartsProvider$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AppleConfigurablesImpl.XcodePartsProvider invoke() {
                String property;
                XcodeVersion xcodeVersion;
                if (InternalServer.INSTANCE.isAvailable()) {
                    return AppleConfigurablesImpl.XcodePartsProvider.InternalServer.INSTANCE;
                }
                Xcode xcodeFindCurrent = Xcode.INSTANCE.findCurrent();
                if (!Intrinsics.areEqual(properties.getProperty("ignoreXcodeVersionCheck"), "true") && (property = properties.getProperty("minimalXcodeVersion")) != null && (xcodeVersion = XcodeVersion.INSTANCE.parse(property)) != null) {
                    this.checkXcodeVersion(xcodeVersion, xcodeFindCurrent.getVersion());
                }
                return new AppleConfigurablesImpl.XcodePartsProvider.Local(xcodeFindCurrent);
            }
        });
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getAbsoluteTargetSysRoot() {
        XcodePartsProvider xcodePartsProvider = getXcodePartsProvider();
        if (xcodePartsProvider instanceof XcodePartsProvider.Local) {
            return ((XcodePartsProvider.Local) xcodePartsProvider).getXcode().pathToPlatformSdk(AppleKt.platformName(this));
        }
        if (Intrinsics.areEqual(xcodePartsProvider, XcodePartsProvider.InternalServer.INSTANCE)) {
            return absolute(this.sdkDependency);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public String getAbsoluteTargetToolchain() {
        XcodePartsProvider xcodePartsProvider = getXcodePartsProvider();
        if (xcodePartsProvider instanceof XcodePartsProvider.Local) {
            return ((XcodePartsProvider.Local) xcodePartsProvider).getXcode().getToolchain();
        }
        if (Intrinsics.areEqual(xcodePartsProvider, XcodePartsProvider.InternalServer.INSTANCE)) {
            return absolute(this.toolchainDependency);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.konan.target.AppleConfigurables
    public String getAbsoluteAdditionalToolsDir() {
        XcodePartsProvider xcodePartsProvider = getXcodePartsProvider();
        if (xcodePartsProvider instanceof XcodePartsProvider.Local) {
            return ((XcodePartsProvider.Local) xcodePartsProvider).getXcode().getAdditionalTools();
        }
        if (Intrinsics.areEqual(xcodePartsProvider, XcodePartsProvider.InternalServer.INSTANCE)) {
            return absolute(getAdditionalToolsDir());
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader
    public List<String> getDependencies() {
        List listListOf;
        List<String> dependencies = super.getDependencies();
        XcodePartsProvider xcodePartsProvider = getXcodePartsProvider();
        if (xcodePartsProvider instanceof XcodePartsProvider.Local) {
            listListOf = CollectionsKt.emptyList();
        } else {
            if (!Intrinsics.areEqual(xcodePartsProvider, XcodePartsProvider.InternalServer.INSTANCE)) {
                throw new NoWhenBranchMatchedException();
            }
            listListOf = CollectionsKt.listOf((Object[]) new String[]{this.sdkDependency, this.toolchainDependency, this.xcodeAddonDependency});
        }
        return CollectionsKt.plus((Collection) dependencies, (Iterable) listListOf);
    }

    private final XcodePartsProvider getXcodePartsProvider() {
        return (XcodePartsProvider) this.xcodePartsProvider.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkXcodeVersion(XcodeVersion minimalVersion, XcodeVersion currentVersion) {
        if (currentVersion.compareTo(minimalVersion) < 0) {
            throw new IllegalStateException(("Unsupported Xcode version " + currentVersion + ", minimal supported version is " + minimalVersion + '.').toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: Apple.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider;", "", "()V", "InternalServer", "Local", "Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider$InternalServer;", "Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider$Local;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    static abstract class XcodePartsProvider {
        public /* synthetic */ XcodePartsProvider(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: Apple.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider$Local;", "Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider;", "xcode", "Lorg/jetbrains/kotlin/konan/target/Xcode;", "(Lorg/jetbrains/kotlin/konan/target/Xcode;)V", "getXcode", "()Lorg/jetbrains/kotlin/konan/target/Xcode;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
        public static final class Local extends XcodePartsProvider {
            private final Xcode xcode;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Local(Xcode xcode) {
                super(null);
                Intrinsics.checkNotNullParameter(xcode, "xcode");
                this.xcode = xcode;
            }

            public final Xcode getXcode() {
                return this.xcode;
            }
        }

        private XcodePartsProvider() {
        }

        /* JADX INFO: compiled from: Apple.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider$InternalServer;", "Lorg/jetbrains/kotlin/konan/target/AppleConfigurablesImpl$XcodePartsProvider;", "()V", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
        public static final class InternalServer extends XcodePartsProvider {
            public static final InternalServer INSTANCE = new InternalServer();

            private InternalServer() {
                super(null);
            }
        }
    }
}
