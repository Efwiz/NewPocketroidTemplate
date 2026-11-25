package org.jetbrains.kotlin.konan.target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty0;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: Platform.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u0001!B%\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\bB\u0019\b\u0016\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u000bB\u000f\b\u0002\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0019J\u000e\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u0019J\b\u0010\u001f\u001a\u00020 H\u0002R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00140\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/konan/target/PlatformManager;", "Lorg/jetbrains/kotlin/konan/target/HostManager;", "Ljava/io/Serializable;", "konanHome", "", "experimental", "", "konanDataDir", "(Ljava/lang/String;ZLjava/lang/String;)V", "distribution", "Lorg/jetbrains/kotlin/konan/target/Distribution;", "(Lorg/jetbrains/kotlin/konan/target/Distribution;Z)V", "serialized", "Lorg/jetbrains/kotlin/konan/target/PlatformManager$Serialized;", "(Lorg/jetbrains/kotlin/konan/target/PlatformManager$Serialized;)V", "getDistribution", "()Lorg/jetbrains/kotlin/konan/target/Distribution;", "distribution$delegate", "Lkotlin/reflect/KProperty0;", "hostPlatform", "Lorg/jetbrains/kotlin/konan/target/Platform;", "getHostPlatform", "()Lorg/jetbrains/kotlin/konan/target/Platform;", "loaders", "", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "platforms", "loader", "target", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "writeReplace", "", "Serialized", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PlatformManager extends HostManager implements Serializable {

    /* JADX INFO: renamed from: distribution$delegate, reason: from kotlin metadata */
    private final KProperty0 distribution;
    private final Platform hostPlatform;
    private final Map<KonanTarget, Configurables> loaders;
    private final Map<KonanTarget, Platform> platforms;
    private final Serialized serialized;

    public /* synthetic */ PlatformManager(Serialized serialized, DefaultConstructorMarker defaultConstructorMarker) {
        this(serialized);
    }

    private PlatformManager(final Serialized serialized) {
        super(serialized.getDistribution(), serialized.getExperimental());
        this.serialized = serialized;
        this.distribution = new PropertyReference0(serialized) { // from class: org.jetbrains.kotlin.konan.target.PlatformManager$distribution$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getDistribution()Lorg/jetbrains/kotlin/konan/target/Distribution;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((PlatformManager.Serialized) this.receiver).getDistribution();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "distribution";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(PlatformManager.Serialized.class);
            }
        };
        List<KonanTarget> enabled = getEnabled();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(enabled, 10));
        for (KonanTarget konanTarget : enabled) {
            arrayList.add(TuplesKt.to(konanTarget, ConfigurablesImplKt.loadConfigurables(konanTarget, getDistribution().getProperties(), getDistribution().getDependenciesDir())));
        }
        Map<KonanTarget, Configurables> map = MapsKt.toMap(arrayList);
        this.loaders = map;
        ArrayList arrayList2 = new ArrayList(map.size());
        for (Map.Entry<KonanTarget, Configurables> entry : map.entrySet()) {
            arrayList2.add(TuplesKt.to(entry.getKey(), new Platform(entry.getValue())));
        }
        Map<KonanTarget, Platform> map2 = MapsKt.toMap(arrayList2);
        this.platforms = map2;
        this.hostPlatform = (Platform) MapsKt.getValue(map2, HostManager.INSTANCE.getHost());
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PlatformManager(String konanHome, boolean z, String str) {
        this(new Distribution(konanHome, false, null, null, str, 14, null), z);
        Intrinsics.checkNotNullParameter(konanHome, "konanHome");
    }

    public /* synthetic */ PlatformManager(String str, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PlatformManager(Distribution distribution, boolean z) {
        this(new Serialized(distribution, z));
        Intrinsics.checkNotNullParameter(distribution, "distribution");
    }

    public /* synthetic */ PlatformManager(Distribution distribution, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(distribution, (i & 2) != 0 ? false : z);
    }

    private final Distribution getDistribution() {
        return (Distribution) this.distribution.get();
    }

    public final Platform platform(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return (Platform) MapsKt.getValue(this.platforms, target);
    }

    public final Platform getHostPlatform() {
        return this.hostPlatform;
    }

    public final Configurables loader(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return (Configurables) MapsKt.getValue(this.loaders, target);
    }

    private final Object writeReplace() {
        return this.serialized;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: Platform.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0010H\u0002J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/PlatformManager$Serialized;", "Ljava/io/Serializable;", "distribution", "Lorg/jetbrains/kotlin/konan/target/Distribution;", "experimental", "", "(Lorg/jetbrains/kotlin/konan/target/Distribution;Z)V", "getDistribution", "()Lorg/jetbrains/kotlin/konan/target/Distribution;", "getExperimental", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "readResolve", "toString", "", "Companion", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    static final /* data */ class Serialized implements Serializable {
        private static final long serialVersionUID = 0;
        private final Distribution distribution;
        private final boolean experimental;

        public static /* synthetic */ Serialized copy$default(Serialized serialized, Distribution distribution, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                distribution = serialized.distribution;
            }
            if ((i & 2) != 0) {
                z = serialized.experimental;
            }
            return serialized.copy(distribution, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Distribution getDistribution() {
            return this.distribution;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getExperimental() {
            return this.experimental;
        }

        public final Serialized copy(Distribution distribution, boolean experimental) {
            Intrinsics.checkNotNullParameter(distribution, "distribution");
            return new Serialized(distribution, experimental);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Serialized)) {
                return false;
            }
            Serialized serialized = (Serialized) other;
            return Intrinsics.areEqual(this.distribution, serialized.distribution) && this.experimental == serialized.experimental;
        }

        public int hashCode() {
            return (this.distribution.hashCode() * 31) + Boolean.hashCode(this.experimental);
        }

        public String toString() {
            return "Serialized(distribution=" + this.distribution + ", experimental=" + this.experimental + ')';
        }

        public Serialized(Distribution distribution, boolean z) {
            Intrinsics.checkNotNullParameter(distribution, "distribution");
            this.distribution = distribution;
            this.experimental = z;
        }

        public final Distribution getDistribution() {
            return this.distribution;
        }

        public final boolean getExperimental() {
            return this.experimental;
        }

        private final Object readResolve() {
            return new PlatformManager(this, (DefaultConstructorMarker) null);
        }
    }
}
