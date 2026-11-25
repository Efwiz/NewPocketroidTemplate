package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: BinaryModuleData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/BinaryModuleData;", "", "regular", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "dependsOn", "friends", "(Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "getDependsOn", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getFriends", "getRegular", "Companion", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryModuleData {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirModuleData dependsOn;
    private final FirModuleData friends;
    private final FirModuleData regular;

    public BinaryModuleData(FirModuleData regular, FirModuleData dependsOn, FirModuleData friends) {
        Intrinsics.checkNotNullParameter(regular, "regular");
        Intrinsics.checkNotNullParameter(dependsOn, "dependsOn");
        Intrinsics.checkNotNullParameter(friends, "friends");
        this.regular = regular;
        this.dependsOn = dependsOn;
        this.friends = friends;
    }

    public final FirModuleData getRegular() {
        return this.regular;
    }

    public final FirModuleData getDependsOn() {
        return this.dependsOn;
    }

    public final FirModuleData getFriends() {
        return this.friends;
    }

    /* JADX INFO: compiled from: BinaryModuleData.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fJ\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/BinaryModuleData$Companion;", "", "()V", "createDependencyModuleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "capabilities", "Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;", "initialize", "Lorg/jetbrains/kotlin/fir/BinaryModuleData;", "mainModuleName", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ FirModuleData createDependencyModuleData$default(Companion companion, Name name2, TargetPlatform targetPlatform, PlatformDependentAnalyzerServices platformDependentAnalyzerServices, FirModuleCapabilities firModuleCapabilities, int i, Object obj) {
            if ((i & 8) != 0) {
                firModuleCapabilities = FirModuleCapabilities.INSTANCE.getEmpty();
            }
            return companion.createDependencyModuleData(name2, targetPlatform, platformDependentAnalyzerServices, firModuleCapabilities);
        }

        public final FirModuleData createDependencyModuleData(Name name2, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, FirModuleCapabilities capabilities) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(platform, "platform");
            Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
            Intrinsics.checkNotNullParameter(capabilities, "capabilities");
            return new FirModuleDataImpl(name2, CollectionsKt.emptyList(), CollectionsKt.emptyList(), CollectionsKt.emptyList(), platform, analyzerServices, capabilities);
        }

        private static final FirModuleData initialize$createData(TargetPlatform targetPlatform, PlatformDependentAnalyzerServices platformDependentAnalyzerServices, String str) {
            Companion companion = BinaryModuleData.INSTANCE;
            Name nameSpecial = Name.special(str);
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            return createDependencyModuleData$default(companion, nameSpecial, targetPlatform, platformDependentAnalyzerServices, null, 8, null);
        }

        public final BinaryModuleData initialize(Name mainModuleName, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices) {
            Intrinsics.checkNotNullParameter(mainModuleName, "mainModuleName");
            Intrinsics.checkNotNullParameter(platform, "platform");
            Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
            return new BinaryModuleData(initialize$createData(platform, analyzerServices, "<regular dependencies of " + mainModuleName + '>'), initialize$createData(platform, analyzerServices, "<dependsOn dependencies of " + mainModuleName), initialize$createData(platform, analyzerServices, "<friends dependencies of " + mainModuleName));
        }
    }
}
