package org.jetbrains.kotlin.analyzer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: ModuleInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\bf\u0018\u0000 \"2\u00020\u0001:\u0002\"#J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00000\u0010H&J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00000!H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R&\u0010\u0006\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00000\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006$À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "capabilities", "", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "getCapabilities", "()Ljava/util/Map;", "displayedName", "", "getDisplayedName", "()Ljava/lang/String;", "expectedBy", "", "getExpectedBy", "()Ljava/util/List;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "stableName", "getStableName", "dependencies", "dependencyOnBuiltIns", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo$DependencyOnBuiltIns;", "modulesWhoseInternalsAreVisible", "", "Companion", "DependencyOnBuiltIns", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ModuleInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: ModuleInfo.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ModuleInfo$DependencyOnBuiltIns;", "", "(Ljava/lang/String;I)V", "NONE", "AFTER_SDK", "LAST", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum DependencyOnBuiltIns {
        NONE,
        AFTER_SDK,
        LAST;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<DependencyOnBuiltIns> getEntries() {
            return $ENTRIES;
        }
    }

    List<ModuleInfo> dependencies();

    PlatformDependentAnalyzerServices getAnalyzerServices();

    Name getName();

    TargetPlatform getPlatform();

    default Name getStableName() {
        return null;
    }

    default String getDisplayedName() {
        String strAsString = getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    default List<ModuleInfo> getExpectedBy() {
        return CollectionsKt.emptyList();
    }

    default Collection<ModuleInfo> modulesWhoseInternalsAreVisible() {
        return CollectionsKt.emptyList();
    }

    default Map<ModuleCapability<?>, Object> getCapabilities() {
        return MapsKt.mapOf(TuplesKt.to(INSTANCE.getCapability(), this));
    }

    default DependencyOnBuiltIns dependencyOnBuiltIns() {
        return getAnalyzerServices().dependencyOnBuiltIns();
    }

    /* JADX INFO: compiled from: ModuleInfo.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ModuleInfo$Companion;", "", "()V", "Capability", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "getCapability", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ModuleCapability<ModuleInfo> Capability = new ModuleCapability<>("ModuleInfo");

        private Companion() {
        }

        public final ModuleCapability<ModuleInfo> getCapability() {
            return Capability;
        }
    }
}
