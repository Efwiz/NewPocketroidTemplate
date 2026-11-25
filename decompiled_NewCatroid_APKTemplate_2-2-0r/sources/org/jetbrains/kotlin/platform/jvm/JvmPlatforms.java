package org.jetbrains.kotlin.platform.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.TargetPlatformKt;

/* JADX INFO: compiled from: JvmPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001cB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u000f\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0011\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0013\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\fR\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00070\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/platform/jvm/JvmPlatforms;", "", "()V", "UNSPECIFIED_SIMPLE_JVM_PLATFORM", "Lorg/jetbrains/kotlin/platform/jvm/JdkPlatform;", "allJvmPlatforms", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getAllJvmPlatforms", "()Ljava/util/List;", "defaultJvmPlatform", "getDefaultJvmPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "jvm11", "getJvm11", "jvm17", "getJvm17", "jvm6", "getJvm6", "jvm8", "getJvm8", "jvmTargetToJdkPlatform", "", "Lorg/jetbrains/kotlin/config/JvmTarget;", "unspecifiedJvmPlatform", "getUnspecifiedJvmPlatform", "jvmPlatformByTargetVersion", "targetVersion", "CompatJvmPlatform", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPlatforms {
    public static final JvmPlatforms INSTANCE = new JvmPlatforms();
    private static final JdkPlatform UNSPECIFIED_SIMPLE_JVM_PLATFORM = new JdkPlatform(JvmTarget.DEFAULT);
    private static final List<TargetPlatform> allJvmPlatforms;
    private static final TargetPlatform defaultJvmPlatform;
    private static final TargetPlatform jvm11;
    private static final TargetPlatform jvm17;
    private static final TargetPlatform jvm6;
    private static final TargetPlatform jvm8;
    private static final Map<JvmTarget, TargetPlatform> jvmTargetToJdkPlatform;

    private JvmPlatforms() {
    }

    static {
        JvmTarget[] jvmTargetArrValues = JvmTarget.values();
        ArrayList arrayList = new ArrayList(jvmTargetArrValues.length);
        for (JvmTarget jvmTarget : jvmTargetArrValues) {
            arrayList.add(TuplesKt.to(jvmTarget, TargetPlatformKt.toTargetPlatform(new JdkPlatform(jvmTarget))));
        }
        Map<JvmTarget, TargetPlatform> map = MapsKt.toMap(arrayList);
        jvmTargetToJdkPlatform = map;
        TargetPlatform targetPlatform = map.get(JvmTarget.DEFAULT);
        Intrinsics.checkNotNull(targetPlatform);
        defaultJvmPlatform = targetPlatform;
        TargetPlatform targetPlatform2 = map.get(JvmTarget.JVM_1_6);
        Intrinsics.checkNotNull(targetPlatform2);
        jvm6 = targetPlatform2;
        TargetPlatform targetPlatform3 = map.get(JvmTarget.JVM_1_8);
        Intrinsics.checkNotNull(targetPlatform3);
        jvm8 = targetPlatform3;
        TargetPlatform targetPlatform4 = map.get(JvmTarget.JVM_11);
        Intrinsics.checkNotNull(targetPlatform4);
        jvm11 = targetPlatform4;
        TargetPlatform targetPlatform5 = map.get(JvmTarget.JVM_17);
        Intrinsics.checkNotNull(targetPlatform5);
        jvm17 = targetPlatform5;
        allJvmPlatforms = CollectionsKt.toList(map.values());
    }

    public final TargetPlatform getUnspecifiedJvmPlatform() {
        return CompatJvmPlatform.INSTANCE;
    }

    public final TargetPlatform getDefaultJvmPlatform() {
        return defaultJvmPlatform;
    }

    public final TargetPlatform getJvm6() {
        return jvm6;
    }

    public final TargetPlatform getJvm8() {
        return jvm8;
    }

    public final TargetPlatform getJvm11() {
        return jvm11;
    }

    public final TargetPlatform getJvm17() {
        return jvm17;
    }

    public final TargetPlatform jvmPlatformByTargetVersion(JvmTarget targetVersion) {
        Intrinsics.checkNotNullParameter(targetVersion, "targetVersion");
        TargetPlatform targetPlatform = jvmTargetToJdkPlatform.get(targetVersion);
        Intrinsics.checkNotNull(targetPlatform);
        return targetPlatform;
    }

    public final List<TargetPlatform> getAllJvmPlatforms() {
        return allJvmPlatforms;
    }

    /* JADX INFO: compiled from: JvmPlatform.kt */
    @Deprecated(level = DeprecationLevel.ERROR, message = "Should be accessed only by compatibility layer, other clients should use 'unspecifiedJvmPlatform'")
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/platform/jvm/JvmPlatforms$CompatJvmPlatform;", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "Lorg/jetbrains/kotlin/resolve/jvm/platform/JvmPlatform;", "()V", "platformName", "", "getPlatformName", "()Ljava/lang/String;", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CompatJvmPlatform extends TargetPlatform implements org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatform {
        public static final CompatJvmPlatform INSTANCE = new CompatJvmPlatform();

        private CompatJvmPlatform() {
            super(SetsKt.setOf(JvmPlatforms.UNSPECIFIED_SIMPLE_JVM_PLATFORM));
        }

        @Override // org.jetbrains.kotlin.resolve.TargetPlatform
        public String getPlatformName() {
            return "JVM";
        }
    }
}
