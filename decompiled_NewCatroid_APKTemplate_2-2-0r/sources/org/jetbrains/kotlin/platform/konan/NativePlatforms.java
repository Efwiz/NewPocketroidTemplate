package org.jetbrains.kotlin.platform.konan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.TargetPlatformKt;
import org.jetbrains.kotlin.resolve.konan.platform.KonanPlatform;

/* JADX INFO: compiled from: NativePlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\nJ\u0014\u0010\u0012\u001a\u00020\u00052\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J\u0014\u0010\u0016\u001a\u00020\u00052\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u0014R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\f0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/platform/konan/NativePlatforms;", "", "()V", "allNativePlatforms", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getAllNativePlatforms", "()Ljava/util/List;", "predefinedNativeTargetToNativePlatform", "", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "predefinedNativeTargetToSimpleNativePlatform", "Lorg/jetbrains/kotlin/platform/konan/NativePlatformWithTarget;", "unspecifiedNativePlatform", "getUnspecifiedNativePlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "nativePlatformBySingleTarget", "target", "nativePlatformByTargetNames", "targets", "", "", "nativePlatformByTargets", "CompatNativePlatform", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativePlatforms {
    public static final NativePlatforms INSTANCE = new NativePlatforms();
    private static final List<TargetPlatform> allNativePlatforms;
    private static final Map<KonanTarget, TargetPlatform> predefinedNativeTargetToNativePlatform;
    private static final Map<KonanTarget, NativePlatformWithTarget> predefinedNativeTargetToSimpleNativePlatform;

    private NativePlatforms() {
    }

    static {
        Collection<KonanTarget> collectionValues = KonanTarget.INSTANCE.getPredefinedTargets().values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(collectionValues, 10)), 16));
        for (Object obj : collectionValues) {
            linkedHashMap.put(obj, new NativePlatformWithTarget((KonanTarget) obj));
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        predefinedNativeTargetToSimpleNativePlatform = linkedHashMap2;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(MapsKt.mapCapacity(linkedHashMap2.size()));
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            linkedHashMap3.put(entry.getKey(), TargetPlatformKt.toTargetPlatform((NativePlatformWithTarget) entry.getValue()));
        }
        predefinedNativeTargetToNativePlatform = linkedHashMap3;
        allNativePlatforms = CollectionsKt.plus((Collection) CollectionsKt.listOf(INSTANCE.getUnspecifiedNativePlatform()), (Iterable) linkedHashMap3.values());
    }

    public final TargetPlatform getUnspecifiedNativePlatform() {
        return CompatNativePlatform.INSTANCE;
    }

    public final List<TargetPlatform> getAllNativePlatforms() {
        return allNativePlatforms;
    }

    public final TargetPlatform nativePlatformBySingleTarget(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        TargetPlatform targetPlatform = predefinedNativeTargetToNativePlatform.get(target);
        return targetPlatform == null ? getUnspecifiedNativePlatform() : targetPlatform;
    }

    public final TargetPlatform nativePlatformByTargets(Collection<? extends KonanTarget> targets) {
        Intrinsics.checkNotNullParameter(targets, "targets");
        HashSet hashSet = new HashSet();
        Iterator<T> it = targets.iterator();
        while (it.hasNext()) {
            NativePlatformWithTarget nativePlatformWithTarget = predefinedNativeTargetToSimpleNativePlatform.get((KonanTarget) it.next());
            if (nativePlatformWithTarget != null) {
                hashSet.add(nativePlatformWithTarget);
            }
        }
        HashSet hashSet2 = hashSet;
        int size = hashSet2.size();
        if (size == 0) {
            return getUnspecifiedNativePlatform();
        }
        if (size == 1) {
            return nativePlatformBySingleTarget(((NativePlatformWithTarget) CollectionsKt.first(hashSet2)).getTarget());
        }
        return new TargetPlatform(hashSet2);
    }

    public final TargetPlatform nativePlatformByTargetNames(Collection<String> targets) {
        Intrinsics.checkNotNullParameter(targets, "targets");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = targets.iterator();
        while (it.hasNext()) {
            KonanTarget konanTarget = KonanTarget.INSTANCE.getPredefinedTargets().get((String) it.next());
            if (konanTarget != null) {
                arrayList.add(konanTarget);
            }
        }
        return nativePlatformByTargets(arrayList);
    }

    /* JADX INFO: compiled from: NativePlatform.kt */
    @Deprecated(level = DeprecationLevel.ERROR, message = "Should be accessed only by compatibility layer, other clients should use 'unspecifiedNativePlatform'")
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/platform/konan/NativePlatforms$CompatNativePlatform;", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "Lorg/jetbrains/kotlin/resolve/konan/platform/KonanPlatform;", "()V", "platformName", "", "getPlatformName", "()Ljava/lang/String;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CompatNativePlatform extends TargetPlatform implements KonanPlatform {
        public static final CompatNativePlatform INSTANCE = new CompatNativePlatform();

        private CompatNativePlatform() {
            super(SetsKt.setOf(NativePlatformUnspecifiedTarget.INSTANCE));
        }

        @Override // org.jetbrains.kotlin.resolve.TargetPlatform
        public String getPlatformName() {
            return "Native";
        }
    }
}
