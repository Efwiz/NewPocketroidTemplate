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
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: BuildPerformanceMetric.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000e\b\u0086\u0081\u0002\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001\u0017B#\b\u0002\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\nH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\nH\u0016J\b\u0010\f\u001a\u00020\u0005H\u0016J\n\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0016J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\b\u0010\u000f\u001a\u00020\u0007H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/JpsBuildPerformanceMetric;", "", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "parent", "readableString", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/build/report/metrics/ValueType;", "(Ljava/lang/String;ILorg/jetbrains/kotlin/build/report/metrics/JpsBuildPerformanceMetric;Ljava/lang/String;Lorg/jetbrains/kotlin/build/report/metrics/ValueType;)V", "children", "", "getAllMetrics", "getName", "getParent", "getReadableString", "getType", "DAEMON_GC_TIME", "DAEMON_GC_COUNT", "COMPILE_ITERATION", "ANALYZED_LINES_NUMBER", "CODE_GENERATED_LINES_NUMBER", "ANALYSIS_LPS", "CODE_GENERATION_LPS", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum JpsBuildPerformanceMetric implements BuildPerformanceMetric {
    DAEMON_GC_TIME(null, "Time spent in GC", ValueType.NANOSECONDS, 1, null),
    DAEMON_GC_COUNT(null, "Count of GC", ValueType.NUMBER, 1, null),
    COMPILE_ITERATION(null, "Total compiler iteration", ValueType.NUMBER),
    ANALYZED_LINES_NUMBER(COMPILE_ITERATION, "Number of lines analyzed", ValueType.NUMBER),
    CODE_GENERATED_LINES_NUMBER(COMPILE_ITERATION, "Number of lines for code generation", ValueType.NUMBER),
    ANALYSIS_LPS(COMPILE_ITERATION, "Analysis lines per second", ValueType.NUMBER),
    CODE_GENERATION_LPS(COMPILE_ITERATION, "Code generation lines per second", ValueType.NUMBER);

    public static final long serialVersionUID = 1;
    private final JpsBuildPerformanceMetric parent;
    private final String readableString;
    private final ValueType type;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<Map<JpsBuildPerformanceMetric, List<JpsBuildPerformanceMetric>>> children$delegate = LazyKt.lazy(new Function0<Map<JpsBuildPerformanceMetric, ? extends List<? extends JpsBuildPerformanceMetric>>>() { // from class: org.jetbrains.kotlin.build.report.metrics.JpsBuildPerformanceMetric$Companion$children$2
        @Override // kotlin.jvm.functions.Function0
        public final Map<JpsBuildPerformanceMetric, ? extends List<? extends JpsBuildPerformanceMetric>> invoke() {
            EnumEntries<JpsBuildPerformanceMetric> entries = JpsBuildPerformanceMetric.getEntries();
            ArrayList arrayList = new ArrayList();
            for (JpsBuildPerformanceMetric jpsBuildPerformanceMetric : entries) {
                if (jpsBuildPerformanceMetric.parent != null) {
                    arrayList.add(jpsBuildPerformanceMetric);
                }
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : arrayList) {
                JpsBuildPerformanceMetric jpsBuildPerformanceMetric2 = ((JpsBuildPerformanceMetric) obj).parent;
                Object obj2 = linkedHashMap.get(jpsBuildPerformanceMetric2);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(jpsBuildPerformanceMetric2, obj2);
                }
                ((List) obj2).add(obj);
            }
            return linkedHashMap;
        }
    });

    public static EnumEntries<JpsBuildPerformanceMetric> getEntries() {
        return $ENTRIES;
    }

    JpsBuildPerformanceMetric(JpsBuildPerformanceMetric jpsBuildPerformanceMetric, String str, ValueType valueType) {
        this.parent = jpsBuildPerformanceMetric;
        this.readableString = str;
        this.type = valueType;
    }

    /* synthetic */ JpsBuildPerformanceMetric(JpsBuildPerformanceMetric jpsBuildPerformanceMetric, String str, ValueType valueType, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : jpsBuildPerformanceMetric, str, valueType);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public String getReadableString() {
        return this.readableString;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric
    public ValueType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public BuildPerformanceMetric getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric, org.jetbrains.kotlin.build.report.metrics.BuildTime
    public List<BuildPerformanceMetric> children() {
        return INSTANCE.getChildren().get(this);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    public String getName() {
        return name();
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric, org.jetbrains.kotlin.build.report.metrics.BuildTime
    public List<BuildPerformanceMetric> getAllMetrics() {
        return getEntries();
    }

    /* JADX INFO: compiled from: BuildPerformanceMetric.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R/\u0010\u0003\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/JpsBuildPerformanceMetric$Companion;", "", "()V", "children", "", "Lorg/jetbrains/kotlin/build/report/metrics/JpsBuildPerformanceMetric;", "", "getChildren", "()Ljava/util/Map;", "children$delegate", "Lkotlin/Lazy;", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<JpsBuildPerformanceMetric, List<JpsBuildPerformanceMetric>> getChildren() {
            return (Map) JpsBuildPerformanceMetric.children$delegate.getValue();
        }
    }
}
