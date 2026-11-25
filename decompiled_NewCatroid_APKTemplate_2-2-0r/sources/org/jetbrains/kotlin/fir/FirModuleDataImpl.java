package org.jetbrains.kotlin.fir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: FirModuleData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirModuleDataImpl;", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "dependencies", "", "dependsOnDependencies", "friendDependencies", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "capabilities", "Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;", "(Lorg/jetbrains/kotlin/name/Name;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/platform/TargetPlatform;Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;)V", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getCapabilities", "()Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;", "getDependencies", "()Ljava/util/List;", "getDependsOnDependencies", "getFriendDependencies", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirModuleDataImpl extends FirModuleData {
    private final PlatformDependentAnalyzerServices analyzerServices;
    private final FirModuleCapabilities capabilities;
    private final List<FirModuleData> dependencies;
    private final List<FirModuleData> dependsOnDependencies;
    private final List<FirModuleData> friendDependencies;
    private final Name name;
    private final TargetPlatform platform;

    @Override // org.jetbrains.kotlin.fir.FirModuleData
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.FirModuleData
    public List<FirModuleData> getDependencies() {
        return this.dependencies;
    }

    @Override // org.jetbrains.kotlin.fir.FirModuleData
    public List<FirModuleData> getDependsOnDependencies() {
        return this.dependsOnDependencies;
    }

    @Override // org.jetbrains.kotlin.fir.FirModuleData
    public List<FirModuleData> getFriendDependencies() {
        return this.friendDependencies;
    }

    @Override // org.jetbrains.kotlin.fir.FirModuleData
    public TargetPlatform getPlatform() {
        return this.platform;
    }

    @Override // org.jetbrains.kotlin.fir.FirModuleData
    public PlatformDependentAnalyzerServices getAnalyzerServices() {
        return this.analyzerServices;
    }

    public /* synthetic */ FirModuleDataImpl(Name name2, List list, List list2, List list3, TargetPlatform targetPlatform, PlatformDependentAnalyzerServices platformDependentAnalyzerServices, FirModuleCapabilities firModuleCapabilities, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2, list, list2, list3, targetPlatform, platformDependentAnalyzerServices, (i & 64) != 0 ? FirModuleCapabilities.INSTANCE.getEmpty() : firModuleCapabilities);
    }

    @Override // org.jetbrains.kotlin.fir.FirModuleData
    public FirModuleCapabilities getCapabilities() {
        return this.capabilities;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FirModuleDataImpl(Name name2, List<? extends FirModuleData> dependencies, List<? extends FirModuleData> dependsOnDependencies, List<? extends FirModuleData> friendDependencies, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, FirModuleCapabilities capabilities) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        Intrinsics.checkNotNullParameter(dependsOnDependencies, "dependsOnDependencies");
        Intrinsics.checkNotNullParameter(friendDependencies, "friendDependencies");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(capabilities, "capabilities");
        this.name = name2;
        this.dependencies = dependencies;
        this.dependsOnDependencies = dependsOnDependencies;
        this.friendDependencies = friendDependencies;
        this.platform = platform;
        this.analyzerServices = analyzerServices;
        this.capabilities = capabilities;
    }
}
