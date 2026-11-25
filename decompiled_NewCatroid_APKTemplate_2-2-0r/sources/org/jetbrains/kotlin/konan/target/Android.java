package org.jetbrains.kotlin.konan.target;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClangArgs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Android;", "", "()V", "API", "", "architectureMap", "", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "architectureDirForTarget", "target", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class Android {
    public static final String API = "21";
    public static final Android INSTANCE = new Android();
    private static final Map<KonanTarget, String> architectureMap = MapsKt.mapOf(TuplesKt.to(KonanTarget.ANDROID_X86.INSTANCE, "x86"), TuplesKt.to(KonanTarget.ANDROID_X64.INSTANCE, "x86_64"), TuplesKt.to(KonanTarget.ANDROID_ARM32.INSTANCE, "arm"), TuplesKt.to(KonanTarget.ANDROID_ARM64.INSTANCE, "arm64"));

    private Android() {
    }

    public final String architectureDirForTarget(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return "android-21/arch-" + ((String) MapsKt.getValue(architectureMap, target));
    }
}
