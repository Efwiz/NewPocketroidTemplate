package org.jetbrains.kotlin.konan.target;

import com.google.android.gms.cast.CredentialsData;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.ClassFileVersion;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: compiled from: HostManager.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0002\b\u0016\u0018\u0000 42\u00020\u0001:\u00014B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0019\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u000e\u0010)\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\fJ\u000e\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020&J\u000e\u0010-\u001a\u00020\f2\u0006\u0010,\u001a\u00020&J\u0012\u0010.\u001a\u00020/2\n\b\u0002\u00100\u001a\u0004\u0018\u00010&J\u001a\u00101\u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\f\u00102\u001a\b\u0012\u0004\u0012\u00020&03R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u000f8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R-\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R!\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\f0\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0019\u001a\u0004\b\u001b\u0010\u0013R \u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\f0\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0019\u001a\u0004\b\u001f\u0010\u0013R \u0010!\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\"\u001a\b\u0012\u0004\u0012\u00020\f0\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0019\u001a\u0004\b#\u0010\u0013R\u001d\u0010%\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\f0\u0015¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0017R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/HostManager;", "", "distribution", "Lorg/jetbrains/kotlin/konan/target/Distribution;", "experimental", "", "(Lorg/jetbrains/kotlin/konan/target/Distribution;Z)V", "subTargetProvider", "Lorg/jetbrains/kotlin/konan/target/SubTargetProvider;", "(Lorg/jetbrains/kotlin/konan/target/SubTargetProvider;Z)V", "appleTargets", "", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "commonTargets", "configurableSubtargets", "", "Lorg/jetbrains/kotlin/konan/target/KonanTarget$ZEPHYR;", CompilerOptions.ENABLED, "getEnabled", "()Ljava/util/List;", "enabledByHost", "", "getEnabledByHost", "()Ljava/util/Map;", "enabledByHost$delegate", "Lkotlin/Lazy;", "enabledExperimental", "getEnabledExperimental", "enabledExperimental$delegate", "enabledExperimentalByHost", "enabledRegular", "getEnabledRegular", "enabledRegular$delegate", "enabledRegularByHost", "targetValues", "getTargetValues", "targetValues$delegate", "targets", "", "getTargets", "zephyrSubtargets", "isEnabled", "target", "known", "name", "targetByName", "targetManager", "Lorg/jetbrains/kotlin/konan/target/TargetManager;", "userRequest", "toKonanTargets", "names", "", "Companion", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class HostManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final List<String> defaultJvmArgs;
    private static final KonanTarget host;
    private static final boolean hostIsLinux;
    private static final boolean hostIsMac;
    private static final boolean hostIsMingw;
    private static final Map<Pair<String, String>, KonanTarget> hostMapping;
    private static final List<String> knownTargetTemplates;
    private static final List<String> regularJvmArgs;
    private static final Map<String, String> targetAliasResolutions;
    private static final Lazy<Map<String, List<String>>> targetAliases$delegate;
    private final Set<KonanTarget> appleTargets;
    private final Set<KonanTarget> commonTargets;
    private final List<KonanTarget.ZEPHYR> configurableSubtargets;

    /* JADX INFO: renamed from: enabledByHost$delegate, reason: from kotlin metadata */
    private final Lazy enabledByHost;

    /* JADX INFO: renamed from: enabledExperimental$delegate, reason: from kotlin metadata */
    private final Lazy enabledExperimental;
    private final Map<KonanTarget, Set<KonanTarget>> enabledExperimentalByHost;

    /* JADX INFO: renamed from: enabledRegular$delegate, reason: from kotlin metadata */
    private final Lazy enabledRegular;
    private final Map<KonanTarget, Set<KonanTarget>> enabledRegularByHost;
    private final boolean experimental;

    /* JADX INFO: renamed from: targetValues$delegate, reason: from kotlin metadata */
    private final Lazy targetValues;
    private final Map<String, KonanTarget> targets;
    private final List<KonanTarget.ZEPHYR> zephyrSubtargets;

    /* JADX WARN: Multi-variable type inference failed */
    public HostManager() {
        this((SubTargetProvider) null, false, 3, (DefaultConstructorMarker) (0 == true ? 1 : 0));
    }

    public static final String getHostName() {
        return INSTANCE.getHostName();
    }

    @JvmStatic
    public static final String platformName() {
        return INSTANCE.platformName();
    }

    @JvmStatic
    public static final String simpleOsName() {
        return INSTANCE.simpleOsName();
    }

    public HostManager(SubTargetProvider subTargetProvider, boolean z) {
        Intrinsics.checkNotNullParameter(subTargetProvider, "subTargetProvider");
        this.experimental = z;
        List<String> listAvailableSubTarget = subTargetProvider.availableSubTarget("zephyr");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAvailableSubTarget, 10));
        Iterator<T> it = listAvailableSubTarget.iterator();
        while (it.hasNext()) {
            arrayList.add(new KonanTarget.ZEPHYR((String) it.next(), null, 2, null));
        }
        ArrayList arrayList2 = arrayList;
        this.zephyrSubtargets = arrayList2;
        this.configurableSubtargets = arrayList2;
        this.targetValues = LazyKt.lazy(new Function0<List<? extends KonanTarget>>() { // from class: org.jetbrains.kotlin.konan.target.HostManager$targetValues$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KonanTarget> invoke() {
                return CollectionsKt.plus((Collection) KonanTarget.INSTANCE.getPredefinedTargets().values(), (Iterable) this.this$0.configurableSubtargets);
            }
        });
        List<KonanTarget> targetValues = getTargetValues();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(targetValues, 10)), 16));
        for (Object obj : targetValues) {
            linkedHashMap.put(((KonanTarget) obj).getVisibleName(), obj);
        }
        this.targets = linkedHashMap;
        Set<KonanTarget> of = SetsKt.setOf((Object[]) new KonanTarget[]{KonanTarget.LINUX_X64.INSTANCE, KonanTarget.LINUX_ARM32_HFP.INSTANCE, KonanTarget.LINUX_ARM64.INSTANCE, KonanTarget.LINUX_MIPS32.INSTANCE, KonanTarget.LINUX_MIPSEL32.INSTANCE, KonanTarget.MINGW_X86.INSTANCE, KonanTarget.MINGW_X64.INSTANCE, KonanTarget.ANDROID_X86.INSTANCE, KonanTarget.ANDROID_X64.INSTANCE, KonanTarget.ANDROID_ARM32.INSTANCE, KonanTarget.ANDROID_ARM64.INSTANCE, KonanTarget.WASM32.INSTANCE});
        this.commonTargets = of;
        Set<KonanTarget> of2 = SetsKt.setOf((Object[]) new KonanTarget[]{KonanTarget.MACOS_X64.INSTANCE, KonanTarget.MACOS_ARM64.INSTANCE, KonanTarget.IOS_ARM32.INSTANCE, KonanTarget.IOS_ARM64.INSTANCE, KonanTarget.IOS_X64.INSTANCE, KonanTarget.IOS_SIMULATOR_ARM64.INSTANCE, KonanTarget.WATCHOS_ARM32.INSTANCE, KonanTarget.WATCHOS_ARM64.INSTANCE, KonanTarget.WATCHOS_X86.INSTANCE, KonanTarget.WATCHOS_X64.INSTANCE, KonanTarget.WATCHOS_SIMULATOR_ARM64.INSTANCE, KonanTarget.WATCHOS_DEVICE_ARM64.INSTANCE, KonanTarget.TVOS_ARM64.INSTANCE, KonanTarget.TVOS_X64.INSTANCE, KonanTarget.TVOS_SIMULATOR_ARM64.INSTANCE});
        this.appleTargets = of2;
        this.enabledRegularByHost = MapsKt.mapOf(TuplesKt.to(KonanTarget.LINUX_X64.INSTANCE, of), TuplesKt.to(KonanTarget.MINGW_X64.INSTANCE, of), TuplesKt.to(KonanTarget.MACOS_X64.INSTANCE, SetsKt.plus((Set) of, (Iterable) of2)), TuplesKt.to(KonanTarget.MACOS_ARM64.INSTANCE, SetsKt.plus((Set) of, (Iterable) of2)));
        this.enabledExperimentalByHost = MapsKt.mapOf(TuplesKt.to(KonanTarget.LINUX_X64.INSTANCE, CollectionsKt.toSet(this.zephyrSubtargets)), TuplesKt.to(KonanTarget.MACOS_X64.INSTANCE, CollectionsKt.toSet(this.zephyrSubtargets)), TuplesKt.to(KonanTarget.MINGW_X64.INSTANCE, CollectionsKt.toSet(this.zephyrSubtargets)), TuplesKt.to(KonanTarget.MACOS_ARM64.INSTANCE, SetsKt.emptySet()));
        this.enabledByHost = LazyKt.lazy(new Function0<Map<KonanTarget, ? extends Set<? extends KonanTarget>>>() { // from class: org.jetbrains.kotlin.konan.target.HostManager$enabledByHost$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KonanTarget, ? extends Set<? extends KonanTarget>> invoke() {
                Map mutableMap = MapsKt.toMutableMap(this.this$0.enabledRegularByHost);
                if (this.this$0.experimental) {
                    for (Map.Entry entry : this.this$0.enabledExperimentalByHost.entrySet()) {
                        mutableMap.merge((KonanTarget) entry.getKey(), (Set) entry.getValue(), new BiFunction() { // from class: org.jetbrains.kotlin.konan.target.HostManager$enabledByHost$2$1$1
                            @Override // java.util.function.BiFunction
                            public final Set<KonanTarget> apply(Set<? extends KonanTarget> old, Set<? extends KonanTarget> set) {
                                Intrinsics.checkNotNullParameter(old, "old");
                                Intrinsics.checkNotNullParameter(set, "new");
                                return SetsKt.plus((Set) old, (Iterable) set);
                            }
                        });
                    }
                }
                return MapsKt.toMap(mutableMap);
            }
        });
        this.enabledRegular = LazyKt.lazy(new Function0<List<? extends KonanTarget>>() { // from class: org.jetbrains.kotlin.konan.target.HostManager$enabledRegular$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KonanTarget> invoke() throws TargetSupportException {
                List<? extends KonanTarget> list;
                Set set = (Set) this.this$0.enabledRegularByHost.get(HostManager.INSTANCE.getHost());
                if (set == null || (list = CollectionsKt.toList(set)) == null) {
                    throw new TargetSupportException("Unknown host platform: " + HostManager.INSTANCE.getHost(), null, 2, null);
                }
                return list;
            }
        });
        this.enabledExperimental = LazyKt.lazy(new Function0<List<? extends KonanTarget>>() { // from class: org.jetbrains.kotlin.konan.target.HostManager$enabledExperimental$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KonanTarget> invoke() throws TargetSupportException {
                List<? extends KonanTarget> list;
                Set set = (Set) this.this$0.enabledExperimentalByHost.get(HostManager.INSTANCE.getHost());
                if (set == null || (list = CollectionsKt.toList(set)) == null) {
                    throw new TargetSupportException("Unknown host platform: " + HostManager.INSTANCE.getHost(), null, 2, null);
                }
                return list;
            }
        });
    }

    public /* synthetic */ HostManager(SubTargetProvider.NoSubTargets noSubTargets, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? SubTargetProvider.NoSubTargets.INSTANCE : noSubTargets, (i & 2) != 0 ? false : z);
    }

    public /* synthetic */ HostManager(Distribution distribution, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(distribution, (i & 2) != 0 ? false : z);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public HostManager(Distribution distribution, boolean z) {
        this(distribution.getSubTargetProvider(), z || distribution.getExperimentalEnabled());
        Intrinsics.checkNotNullParameter(distribution, "distribution");
    }

    public static /* synthetic */ TargetManager targetManager$default(HostManager hostManager, String str, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: targetManager");
        }
        if ((i & 1) != 0) {
            str = null;
        }
        return hostManager.targetManager(str);
    }

    public final TargetManager targetManager(String userRequest) {
        return new TargetManagerImpl(userRequest, this);
    }

    public final List<KonanTarget> getTargetValues() {
        return (List) this.targetValues.getValue();
    }

    public final Map<String, KonanTarget> getTargets() {
        return this.targets;
    }

    public final String known(String name2) throws TargetSupportException {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.targets.get(name2) != null) {
            return name2;
        }
        throw new TargetSupportException("Unknown target: " + name2 + ". Use -list_targets to see the list of available targets", null, 2, null);
    }

    public final KonanTarget targetByName(String name2) throws TargetSupportException {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (Intrinsics.areEqual(name2, "host")) {
            return host;
        }
        KonanTarget konanTarget = this.targets.get(INSTANCE.resolveAlias(name2));
        if (konanTarget != null) {
            return konanTarget;
        }
        throw new TargetSupportException("Unknown target name: " + name2, null, 2, null);
    }

    public final Map<KonanTarget, Set<KonanTarget>> getEnabledByHost() {
        return (Map) this.enabledByHost.getValue();
    }

    private final List<KonanTarget> getEnabledRegular() {
        return (List) this.enabledRegular.getValue();
    }

    private final List<KonanTarget> getEnabledExperimental() {
        return (List) this.enabledExperimental.getValue();
    }

    public final List<KonanTarget> getEnabled() {
        return this.experimental ? CollectionsKt.plus((Collection) getEnabledRegular(), (Iterable) getEnabledExperimental()) : getEnabledRegular();
    }

    public final boolean isEnabled(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return getEnabled().contains(target);
    }

    /* JADX INFO: compiled from: HostManager.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u001f\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\u00052\b\u0010)\u001a\u0004\u0018\u00010\u0005H\u0002J\u0006\u0010*\u001a\u00020\u0005J\b\u0010+\u001a\u0004\u0018\u00010\u0005J\u0006\u0010,\u001a\u00020\u0005J\b\u0010-\u001a\u00020\u0005H\u0007J\b\u0010.\u001a\u00020\u0005H\u0007J\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u00100\u001a\u00020\u0005J\b\u00101\u001a\u00020\u0005H\u0007J\u000e\u00102\u001a\u00020\u00052\u0006\u00103\u001a\u00020\u0005J\b\u00104\u001a\u00020\u0005H\u0007R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0012\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR&\u0010\u0014\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0016\u0012\u0004\u0012\u00020\t0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0018\u0010\u0002\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001aR\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0007R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0007R\u001a\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\"\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b#\u0010$¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/HostManager$Companion;", "", "()V", "defaultJvmArgs", "", "", "getDefaultJvmArgs", "()Ljava/util/List;", "host", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getHost", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "hostIsLinux", "", "getHostIsLinux", "()Z", "hostIsMac", "getHostIsMac", "hostIsMingw", "getHostIsMingw", "hostMapping", "", "Lkotlin/Pair;", "hostName", "getHostName$annotations", "getHostName", "()Ljava/lang/String;", "jniHostPlatformIncludeDir", "getJniHostPlatformIncludeDir", "knownTargetTemplates", "getKnownTargetTemplates", "regularJvmArgs", "getRegularJvmArgs", "targetAliasResolutions", "targetAliases", "getTargetAliases", "()Ljava/util/Map;", "targetAliases$delegate", "Lkotlin/Lazy;", "determineHost", "os", "arch", "hostArch", "hostArchOrNull", "hostOs", "host_arch", "host_os", "listAliases", "target", "platformName", "resolveAlias", "request", "simpleOsName", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getHostName$annotations() {
        }

        private Companion() {
        }

        @Deprecated(message = "Use `hostOs` instead", replaceWith = @ReplaceWith(expression = "HostManager.hostOs()", imports = {}))
        public final String host_os() {
            return hostOs();
        }

        public final String hostOs() throws TargetSupportException {
            String javaOsName = System.getProperty(SystemProperties.OS_NAME);
            if (Intrinsics.areEqual(javaOsName, "Mac OS X")) {
                return "osx";
            }
            if (Intrinsics.areEqual(javaOsName, "Linux")) {
                return "linux";
            }
            Intrinsics.checkNotNullExpressionValue(javaOsName, "javaOsName");
            if (StringsKt.startsWith$default(javaOsName, "Windows", false, 2, (Object) null)) {
                return "windows";
            }
            throw new TargetSupportException("Unknown operating system: " + javaOsName, null, 2, null);
        }

        @JvmStatic
        public final String simpleOsName() throws TargetSupportException {
            String strHostOs = hostOs();
            return Intrinsics.areEqual(strHostOs, "osx") ? "macos" : strHostOs;
        }

        @JvmStatic
        public final String platformName() throws TargetSupportException {
            String strHostOs = hostOs();
            String strHostArch = hostArch();
            return Intrinsics.areEqual(strHostOs, "osx") ? "macos-" + strHostArch : strHostOs + '-' + strHostArch;
        }

        public final String getJniHostPlatformIncludeDir() throws TargetSupportException {
            KonanTarget host = getHost();
            if (Intrinsics.areEqual(host, KonanTarget.MACOS_X64.INSTANCE) ? true : Intrinsics.areEqual(host, KonanTarget.MACOS_ARM64.INSTANCE)) {
                return "darwin";
            }
            if (Intrinsics.areEqual(host, KonanTarget.LINUX_X64.INSTANCE)) {
                return "linux";
            }
            if (Intrinsics.areEqual(host, KonanTarget.MINGW_X64.INSTANCE)) {
                return "win32";
            }
            throw new TargetSupportException("Unknown host: " + getHost() + '.', null, 2, null);
        }

        @Deprecated(message = "Use `hostArch` instead", replaceWith = @ReplaceWith(expression = "HostManager.hostArch()", imports = {}))
        public final String host_arch() {
            return hostArch();
        }

        public final String hostArch() throws TargetSupportException {
            String strHostArchOrNull = hostArchOrNull();
            if (strHostArchOrNull != null) {
                return strHostArchOrNull;
            }
            throw new TargetSupportException("Unknown hardware platform: " + System.getProperty(SystemProperties.OS_ARCH), null, 2, null);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0031, code lost:
        
            if (r0.equals("aarch64") == false) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:
        
            return "aarch64";
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x001a, code lost:
        
            if (r0.equals("arm64") == false) goto L20;
         */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.String hostArchOrNull() {
            /*
                r4 = this;
                java.lang.String r0 = "os.arch"
                java.lang.String r0 = java.lang.System.getProperty(r0)
                if (r0 == 0) goto L36
                int r1 = r0.hashCode()
                java.lang.String r2 = "x86_64"
                java.lang.String r3 = "aarch64"
                switch(r1) {
                    case -1221096139: goto L2d;
                    case -806050265: goto L26;
                    case 92926582: goto L1d;
                    case 93084186: goto L14;
                    default: goto L13;
                }
            L13:
                goto L36
            L14:
                java.lang.String r1 = "arm64"
                boolean r0 = r0.equals(r1)
                if (r0 != 0) goto L34
                goto L36
            L1d:
                java.lang.String r1 = "amd64"
                boolean r0 = r0.equals(r1)
                if (r0 != 0) goto L37
                goto L36
            L26:
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L37
                goto L36
            L2d:
                boolean r0 = r0.equals(r3)
                if (r0 != 0) goto L34
                goto L36
            L34:
                r2 = r3
                goto L37
            L36:
                r2 = 0
            L37:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.konan.target.HostManager.Companion.hostArchOrNull():java.lang.String");
        }

        public final KonanTarget getHost() {
            return HostManager.host;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final KonanTarget determineHost(String os, String arch) throws TargetSupportException {
            Object next;
            KonanTarget konanTarget = (KonanTarget) HostManager.hostMapping.get(TuplesKt.to(os, arch));
            if (konanTarget != null) {
                return konanTarget;
            }
            Iterator it = HostManager.hostMapping.entrySet().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((Pair) ((Map.Entry) next).getKey()).getFirst(), os));
            Map.Entry entry = (Map.Entry) next;
            if (entry != null) {
                return (KonanTarget) entry.getValue();
            }
            throw new TargetSupportException("Unknown host target: " + os + StringUtil.SPACE + arch, null, 2, null);
        }

        public final List<String> getDefaultJvmArgs() {
            return HostManager.defaultJvmArgs;
        }

        public final List<String> getRegularJvmArgs() {
            return HostManager.regularJvmArgs;
        }

        public final boolean getHostIsMac() {
            return HostManager.hostIsMac;
        }

        public final boolean getHostIsLinux() {
            return HostManager.hostIsLinux;
        }

        public final boolean getHostIsMingw() {
            return HostManager.hostIsMingw;
        }

        public final String getHostName() {
            return getHost().getName();
        }

        public final List<String> getKnownTargetTemplates() {
            return HostManager.knownTargetTemplates;
        }

        private final Map<String, List<String>> getTargetAliases() {
            return (Map) HostManager.targetAliases$delegate.getValue();
        }

        public final String resolveAlias(String request) {
            Intrinsics.checkNotNullParameter(request, "request");
            String str = (String) HostManager.targetAliasResolutions.get(request);
            return str == null ? request : str;
        }

        public final List<String> listAliases(String target) {
            Intrinsics.checkNotNullParameter(target, "target");
            List<String> list = getTargetAliases().get(target);
            return list == null ? CollectionsKt.emptyList() : list;
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        hostMapping = MapsKt.mapOf(TuplesKt.to(new Pair("osx", "x86_64"), KonanTarget.MACOS_X64.INSTANCE), TuplesKt.to(new Pair("osx", "aarch64"), KonanTarget.MACOS_ARM64.INSTANCE), TuplesKt.to(new Pair("linux", "x86_64"), KonanTarget.LINUX_X64.INSTANCE), TuplesKt.to(new Pair("windows", "x86_64"), KonanTarget.MINGW_X64.INSTANCE));
        KonanTarget konanTargetDetermineHost = companion.determineHost(companion.hostOs(), companion.hostArchOrNull());
        host = konanTargetDetermineHost;
        List<String> listListOf = CollectionsKt.listOf((Object[]) new String[]{"-XX:TieredStopAtLevel=1", ClassFileVersion.VersionLocator.EARLY_ACCESS, "-Dfile.encoding=UTF-8"});
        defaultJvmArgs = listListOf;
        regularJvmArgs = CollectionsKt.plus((Collection<? extends String>) listListOf, "-Xmx3G");
        hostIsMac = konanTargetDetermineHost.getFamily() == Family.OSX;
        hostIsLinux = konanTargetDetermineHost.getFamily() == Family.LINUX;
        hostIsMingw = konanTargetDetermineHost.getFamily() == Family.MINGW;
        knownTargetTemplates = CollectionsKt.listOf("zephyr");
        targetAliasResolutions = MapsKt.mapOf(TuplesKt.to("linux", "linux_x64"), TuplesKt.to("macbook", "macos_x64"), TuplesKt.to("macos", "macos_x64"), TuplesKt.to("imac", "macos_x64"), TuplesKt.to("raspberrypi", "linux_arm32_hfp"), TuplesKt.to("iphone32", "ios_arm32"), TuplesKt.to("iphone", "ios_arm64"), TuplesKt.to("ipad", "ios_arm64"), TuplesKt.to(CredentialsData.CREDENTIALS_TYPE_IOS, "ios_arm64"), TuplesKt.to("iphone_sim", "ios_x64"), TuplesKt.to("mingw", "mingw_x64"));
        targetAliases$delegate = LazyKt.lazy(new Function0<Map<String, List<String>>>() { // from class: org.jetbrains.kotlin.konan.target.HostManager$Companion$targetAliases$2
            @Override // kotlin.jvm.functions.Function0
            public final Map<String, List<String>> invoke() {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : HostManager.targetAliasResolutions.entrySet()) {
                    Object value = entry.getValue();
                    Object obj = linkedHashMap.get(value);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        linkedHashMap.put(value, obj);
                    }
                    ((List) obj).add(entry.getKey());
                }
                return linkedHashMap;
            }
        });
    }

    public final List<KonanTarget> toKonanTargets(Iterable<String> names) {
        Intrinsics.checkNotNullParameter(names, "names");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(names, 10));
        for (String str : names) {
            arrayList.add(Intrinsics.areEqual(str, "host") ? host : (KonanTarget) MapsKt.getValue(this.targets, known(INSTANCE.resolveAlias(str))));
        }
        return arrayList;
    }
}
