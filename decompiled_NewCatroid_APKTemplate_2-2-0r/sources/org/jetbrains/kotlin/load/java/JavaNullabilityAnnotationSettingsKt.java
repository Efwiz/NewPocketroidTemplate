package org.jetbrains.kotlin.load.java;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JavaNullabilityAnnotationSettings.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0010\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a\u001a\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001c\u001a\u000e\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0001\u001a&\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000b2\b\b\u0002\u0010\u0019\u001a\u00020\u001a\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u0011\u0010\u0006\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0003\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\"\u0019\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011\"\u0011\u0010\u0013\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0003\"\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"CHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE", "Lorg/jetbrains/kotlin/name/FqName;", "getCHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE", "()Lorg/jetbrains/kotlin/name/FqName;", "JSPECIFY_ANNOTATIONS_PACKAGE", "getJSPECIFY_ANNOTATIONS_PACKAGE", "JSPECIFY_OLD_ANNOTATIONS_PACKAGE", "getJSPECIFY_OLD_ANNOTATIONS_PACKAGE", "JSR_305_DEFAULT_SETTINGS", "Lorg/jetbrains/kotlin/load/java/JavaNullabilityAnnotationsStatus;", "NULLABILITY_ANNOTATION_SETTINGS", "Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates;", "getNULLABILITY_ANNOTATION_SETTINGS", "()Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates;", "RXJAVA3_ANNOTATIONS", "", "getRXJAVA3_ANNOTATIONS", "()[Lorg/jetbrains/kotlin/name/FqName;", "[Lorg/jetbrains/kotlin/name/FqName;", "RXJAVA3_ANNOTATIONS_PACKAGE", "getRXJAVA3_ANNOTATIONS_PACKAGE", "RXJAVA3_ANNOTATIONS_PACKAGE_NAME", "", "getDefaultJsr305Settings", "Lorg/jetbrains/kotlin/load/java/Jsr305Settings;", "configuredKotlinVersion", "Lkotlin/KotlinVersion;", "getDefaultMigrationJsr305ReportLevelForGivenGlobal", "Lorg/jetbrains/kotlin/load/java/ReportLevel;", "globalReportLevel", "getDefaultReportLevelForAnnotation", "annotationFqName", "getReportLevelForAnnotation", "annotation", "configuredReportLevels", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaNullabilityAnnotationSettingsKt {
    private static final FqName CHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE;
    private static final FqName JSPECIFY_ANNOTATIONS_PACKAGE;
    private static final FqName JSPECIFY_OLD_ANNOTATIONS_PACKAGE;
    private static final JavaNullabilityAnnotationsStatus JSR_305_DEFAULT_SETTINGS;
    private static final NullabilityAnnotationStates<JavaNullabilityAnnotationsStatus> NULLABILITY_ANNOTATION_SETTINGS;
    private static final FqName[] RXJAVA3_ANNOTATIONS;
    private static final FqName RXJAVA3_ANNOTATIONS_PACKAGE;
    private static final String RXJAVA3_ANNOTATIONS_PACKAGE_NAME;

    static {
        FqName fqName = new FqName("org.jspecify.nullness");
        JSPECIFY_OLD_ANNOTATIONS_PACKAGE = fqName;
        FqName fqName2 = new FqName("org.jspecify.annotations");
        JSPECIFY_ANNOTATIONS_PACKAGE = fqName2;
        FqName fqName3 = new FqName("io.reactivex.rxjava3.annotations");
        RXJAVA3_ANNOTATIONS_PACKAGE = fqName3;
        FqName fqName4 = new FqName("org.checkerframework.checker.nullness.compatqual");
        CHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE = fqName4;
        String strAsString = fqName3.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        RXJAVA3_ANNOTATIONS_PACKAGE_NAME = strAsString;
        RXJAVA3_ANNOTATIONS = new FqName[]{new FqName(strAsString + ".Nullable"), new FqName(strAsString + ".NonNull")};
        NULLABILITY_ANNOTATION_SETTINGS = new NullabilityAnnotationStatesImpl(MapsKt.mapOf(TuplesKt.to(new FqName("org.jetbrains.annotations"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("androidx.annotation"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("android.support.annotation"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("android.annotation"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("com.android.annotations"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("org.eclipse.jdt.annotation"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("org.checkerframework.checker.nullness.qual"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(fqName4, JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("javax.annotation"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("edu.umd.cs.findbugs.annotations"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("io.reactivex.annotations"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(new FqName("androidx.annotation.RecentlyNullable"), new JavaNullabilityAnnotationsStatus(ReportLevel.WARN, null, null, 4, null)), TuplesKt.to(new FqName("androidx.annotation.RecentlyNonNull"), new JavaNullabilityAnnotationsStatus(ReportLevel.WARN, null, null, 4, null)), TuplesKt.to(new FqName("lombok"), JavaNullabilityAnnotationsStatus.INSTANCE.getDEFAULT()), TuplesKt.to(fqName, new JavaNullabilityAnnotationsStatus(ReportLevel.WARN, new KotlinVersion(2, 0), ReportLevel.STRICT)), TuplesKt.to(fqName2, new JavaNullabilityAnnotationsStatus(ReportLevel.WARN, new KotlinVersion(2, 0), ReportLevel.STRICT)), TuplesKt.to(fqName3, new JavaNullabilityAnnotationsStatus(ReportLevel.WARN, new KotlinVersion(1, 8), ReportLevel.STRICT))));
        JSR_305_DEFAULT_SETTINGS = new JavaNullabilityAnnotationsStatus(ReportLevel.WARN, null, null, 4, null);
    }

    public static final FqName getJSPECIFY_OLD_ANNOTATIONS_PACKAGE() {
        return JSPECIFY_OLD_ANNOTATIONS_PACKAGE;
    }

    public static final FqName getJSPECIFY_ANNOTATIONS_PACKAGE() {
        return JSPECIFY_ANNOTATIONS_PACKAGE;
    }

    public static final FqName getRXJAVA3_ANNOTATIONS_PACKAGE() {
        return RXJAVA3_ANNOTATIONS_PACKAGE;
    }

    public static final FqName getCHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE() {
        return CHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE;
    }

    public static final FqName[] getRXJAVA3_ANNOTATIONS() {
        return RXJAVA3_ANNOTATIONS;
    }

    public static final NullabilityAnnotationStates<JavaNullabilityAnnotationsStatus> getNULLABILITY_ANNOTATION_SETTINGS() {
        return NULLABILITY_ANNOTATION_SETTINGS;
    }

    public static /* synthetic */ Jsr305Settings getDefaultJsr305Settings$default(KotlinVersion kotlinVersion, int i, Object obj) {
        if ((i & 1) != 0) {
            kotlinVersion = KotlinVersion.CURRENT;
        }
        return getDefaultJsr305Settings(kotlinVersion);
    }

    public static final Jsr305Settings getDefaultJsr305Settings(KotlinVersion configuredKotlinVersion) {
        ReportLevel reportLevelBefore;
        Intrinsics.checkNotNullParameter(configuredKotlinVersion, "configuredKotlinVersion");
        JavaNullabilityAnnotationsStatus javaNullabilityAnnotationsStatus = JSR_305_DEFAULT_SETTINGS;
        if (javaNullabilityAnnotationsStatus.getSinceVersion() != null && javaNullabilityAnnotationsStatus.getSinceVersion().compareTo(configuredKotlinVersion) <= 0) {
            reportLevelBefore = javaNullabilityAnnotationsStatus.getReportLevelAfter();
        } else {
            reportLevelBefore = javaNullabilityAnnotationsStatus.getReportLevelBefore();
        }
        ReportLevel reportLevel = reportLevelBefore;
        return new Jsr305Settings(reportLevel, getDefaultMigrationJsr305ReportLevelForGivenGlobal(reportLevel), null, 4, null);
    }

    public static final ReportLevel getDefaultMigrationJsr305ReportLevelForGivenGlobal(ReportLevel globalReportLevel) {
        Intrinsics.checkNotNullParameter(globalReportLevel, "globalReportLevel");
        if (globalReportLevel == ReportLevel.WARN) {
            return null;
        }
        return globalReportLevel;
    }

    public static final ReportLevel getDefaultReportLevelForAnnotation(FqName annotationFqName) {
        Intrinsics.checkNotNullParameter(annotationFqName, "annotationFqName");
        return getReportLevelForAnnotation$default(annotationFqName, NullabilityAnnotationStates.INSTANCE.getEMPTY(), null, 4, null);
    }

    public static /* synthetic */ ReportLevel getReportLevelForAnnotation$default(FqName fqName, NullabilityAnnotationStates nullabilityAnnotationStates, KotlinVersion kotlinVersion, int i, Object obj) {
        if ((i & 4) != 0) {
            kotlinVersion = new KotlinVersion(1, 7, 20);
        }
        return getReportLevelForAnnotation(fqName, nullabilityAnnotationStates, kotlinVersion);
    }

    public static final ReportLevel getReportLevelForAnnotation(FqName annotation, NullabilityAnnotationStates<? extends ReportLevel> configuredReportLevels, KotlinVersion configuredKotlinVersion) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(configuredReportLevels, "configuredReportLevels");
        Intrinsics.checkNotNullParameter(configuredKotlinVersion, "configuredKotlinVersion");
        ReportLevel reportLevel = configuredReportLevels.get(annotation);
        if (reportLevel != null) {
            return reportLevel;
        }
        JavaNullabilityAnnotationsStatus javaNullabilityAnnotationsStatus = NULLABILITY_ANNOTATION_SETTINGS.get(annotation);
        if (javaNullabilityAnnotationsStatus == null) {
            return ReportLevel.IGNORE;
        }
        if (javaNullabilityAnnotationsStatus.getSinceVersion() != null && javaNullabilityAnnotationsStatus.getSinceVersion().compareTo(configuredKotlinVersion) <= 0) {
            return javaNullabilityAnnotationsStatus.getReportLevelAfter();
        }
        return javaNullabilityAnnotationsStatus.getReportLevelBefore();
    }
}
