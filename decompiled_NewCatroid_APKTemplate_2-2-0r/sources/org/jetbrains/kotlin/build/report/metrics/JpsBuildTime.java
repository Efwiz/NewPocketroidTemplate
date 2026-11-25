package org.jetbrains.kotlin.build.report.metrics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: BuildTime.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u0000 \u000e2\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001\u000eB\u001b\b\u0002\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u0005H\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\u0002H\u0016J\b\u0010\f\u001a\u00020\u0005H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/JpsBuildTime;", "", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "parent", "readableString", "", "(Ljava/lang/String;ILorg/jetbrains/kotlin/build/report/metrics/JpsBuildTime;Ljava/lang/String;)V", "children", "", "getAllMetrics", "getName", "getParent", "getReadableString", "JPS_ITERATION", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum JpsBuildTime implements BuildTime {
    JPS_ITERATION(null, "Jps iteration", 1, null);

    public static final long serialVersionUID = 1;
    private final JpsBuildTime parent;
    private final String readableString;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<Map<JpsBuildTime, List<JpsBuildTime>>> children$delegate = LazyKt.lazy(new Function0<Map<JpsBuildTime, ? extends List<? extends JpsBuildTime>>>() { // from class: org.jetbrains.kotlin.build.report.metrics.JpsBuildTime$Companion$children$2
        @Override // kotlin.jvm.functions.Function0
        public final Map<JpsBuildTime, ? extends List<? extends JpsBuildTime>> invoke() {
            EnumEntries<JpsBuildTime> entries = JpsBuildTime.getEntries();
            ArrayList arrayList = new ArrayList();
            for (JpsBuildTime jpsBuildTime : entries) {
                if (jpsBuildTime.parent != null) {
                    arrayList.add(jpsBuildTime);
                }
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : arrayList) {
                JpsBuildTime jpsBuildTime2 = ((JpsBuildTime) obj).parent;
                Object obj2 = linkedHashMap.get(jpsBuildTime2);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(jpsBuildTime2, obj2);
                }
                ((List) obj2).add(obj);
            }
            return linkedHashMap;
        }
    });

    public static EnumEntries<JpsBuildTime> getEntries() {
        return $ENTRIES;
    }

    JpsBuildTime(JpsBuildTime jpsBuildTime, String str) {
        this.parent = jpsBuildTime;
        this.readableString = str;
    }

    /* synthetic */ JpsBuildTime(JpsBuildTime jpsBuildTime, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : jpsBuildTime, str);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public String getReadableString() {
        return this.readableString;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public BuildTime getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public List<BuildTime> getAllMetrics() {
        return getEntries();
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public List<BuildTime> children() {
        return INSTANCE.getChildren().get(this);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public String getName() {
        return name();
    }

    /* JADX INFO: compiled from: BuildTime.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R/\u0010\u0003\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/JpsBuildTime$Companion;", "", "()V", "children", "", "Lorg/jetbrains/kotlin/build/report/metrics/JpsBuildTime;", "", "getChildren", "()Ljava/util/Map;", "children$delegate", "Lkotlin/Lazy;", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<JpsBuildTime, List<JpsBuildTime>> getChildren() {
            return (Map) JpsBuildTime.children$delegate.getValue();
        }
    }
}
