package org.jetbrains.kotlin.load.java;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JavaTypeEnhancementState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "", "jsr305", "Lorg/jetbrains/kotlin/load/java/Jsr305Settings;", "getReportLevelForAnnotation", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/load/java/ReportLevel;", "(Lorg/jetbrains/kotlin/load/java/Jsr305Settings;Lkotlin/jvm/functions/Function1;)V", "disabledDefaultAnnotations", "", "getDisabledDefaultAnnotations", "()Z", "getGetReportLevelForAnnotation", "()Lkotlin/jvm/functions/Function1;", "getJsr305", "()Lorg/jetbrains/kotlin/load/java/Jsr305Settings;", "toString", "", "Companion", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeEnhancementState {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final JavaTypeEnhancementState DEFAULT = new JavaTypeEnhancementState(JavaNullabilityAnnotationSettingsKt.getDefaultJsr305Settings$default(null, 1, null), JavaTypeEnhancementState$Companion$DEFAULT$1.INSTANCE);
    private final boolean disabledDefaultAnnotations;
    private final Function1<FqName, ReportLevel> getReportLevelForAnnotation;
    private final Jsr305Settings jsr305;

    /* JADX WARN: Multi-variable type inference failed */
    public JavaTypeEnhancementState(Jsr305Settings jsr305, Function1<? super FqName, ? extends ReportLevel> getReportLevelForAnnotation) {
        Intrinsics.checkNotNullParameter(jsr305, "jsr305");
        Intrinsics.checkNotNullParameter(getReportLevelForAnnotation, "getReportLevelForAnnotation");
        this.jsr305 = jsr305;
        this.getReportLevelForAnnotation = getReportLevelForAnnotation;
        this.disabledDefaultAnnotations = jsr305.getIsDisabled() || getReportLevelForAnnotation.invoke(JavaNullabilityAnnotationSettingsKt.getJSPECIFY_ANNOTATIONS_PACKAGE()) == ReportLevel.IGNORE;
    }

    public final Jsr305Settings getJsr305() {
        return this.jsr305;
    }

    public final Function1<FqName, ReportLevel> getGetReportLevelForAnnotation() {
        return this.getReportLevelForAnnotation;
    }

    public final boolean getDisabledDefaultAnnotations() {
        return this.disabledDefaultAnnotations;
    }

    /* JADX INFO: compiled from: JavaTypeEnhancementState.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "getDEFAULT", "()Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaTypeEnhancementState getDEFAULT() {
            return JavaTypeEnhancementState.DEFAULT;
        }
    }

    public String toString() {
        return "JavaTypeEnhancementState(jsr305=" + this.jsr305 + ", getReportLevelForAnnotation=" + this.getReportLevelForAnnotation + ')';
    }
}
