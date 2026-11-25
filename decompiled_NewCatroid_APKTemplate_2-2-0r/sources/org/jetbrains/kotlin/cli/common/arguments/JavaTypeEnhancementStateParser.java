package org.jetbrains.kotlin.cli.common.arguments;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.load.java.JavaNullabilityAnnotationSettingsKt;
import org.jetbrains.kotlin.load.java.JavaTypeEnhancementState;
import org.jetbrains.kotlin.load.java.Jsr305Settings;
import org.jetbrains.kotlin.load.java.NullabilityAnnotationStates;
import org.jetbrains.kotlin.load.java.NullabilityAnnotationStatesImpl;
import org.jetbrains.kotlin.load.java.ReportLevel;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;

/* JADX INFO: compiled from: JavaTypeEnhancementStateParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 $2\u00020\u0001:\u0001$B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J?\u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n¢\u0006\u0002\u0010\u000fJ&\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00112\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000bH\u0002J \u0010\u0016\u001a\u00020\u00132\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u0018H\u0002J\u001d\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nH\u0002¢\u0006\u0002\u0010\u001cJ#\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\u00182\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nH\u0002¢\u0006\u0002\u0010\u001eJ\u001e\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00112\u0006\u0010\u0014\u001a\u00020\u000bH\u0002J \u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000bH\u0002J\u0018\u0010#\u001a\u00020 2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/JavaTypeEnhancementStateParser;", "", "collector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "kotlinVersion", "Lkotlin/KotlinVersion;", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lkotlin/KotlinVersion;)V", "parse", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "jsr305Args", "", "", "supportCompatqualCheckerFrameworkAnnotations", "jspecifyState", "nullabilityAnnotations", "([Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "parseAnnotationWithReportLevel", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/load/java/ReportLevel;", "item", "sourceCompilerOption", "parseJspecifyReportLevel", "nullabilityAnnotationReportLevels", "Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates;", "parseJsr305State", "Lorg/jetbrains/kotlin/load/java/Jsr305Settings;", "args", "([Ljava/lang/String;)Lorg/jetbrains/kotlin/load/java/Jsr305Settings;", "parseNullabilityAnnotationReportLevels", "([Ljava/lang/String;)Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates;", "reportDuplicateAnnotation", "", "first", "second", "reportUnrecognizedReportLevel", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeEnhancementStateParser {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final JavaTypeEnhancementStateParser DEFAULT = new JavaTypeEnhancementStateParser(MessageCollector.INSTANCE.getNONE(), new KotlinVersion(1, 7, 20));
    private final MessageCollector collector;
    private final KotlinVersion kotlinVersion;

    public JavaTypeEnhancementStateParser(MessageCollector collector, KotlinVersion kotlinVersion) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(kotlinVersion, "kotlinVersion");
        this.collector = collector;
        this.kotlinVersion = kotlinVersion;
    }

    public final JavaTypeEnhancementState parse(String[] jsr305Args, String supportCompatqualCheckerFrameworkAnnotations, String jspecifyState, String[] nullabilityAnnotations) {
        final ReportLevel reportLevelForAnnotation;
        final NullabilityAnnotationStates<ReportLevel> nullabilityAnnotationReportLevels = parseNullabilityAnnotationReportLevels(nullabilityAnnotations);
        if (Intrinsics.areEqual(supportCompatqualCheckerFrameworkAnnotations, CommonCompilerArguments.ENABLE)) {
            reportLevelForAnnotation = ReportLevel.STRICT;
        } else if (Intrinsics.areEqual(supportCompatqualCheckerFrameworkAnnotations, "disable")) {
            reportLevelForAnnotation = ReportLevel.IGNORE;
        } else if (supportCompatqualCheckerFrameworkAnnotations == null) {
            reportLevelForAnnotation = JavaNullabilityAnnotationSettingsKt.getReportLevelForAnnotation(JavaNullabilityAnnotationSettingsKt.getCHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE(), nullabilityAnnotationReportLevels, this.kotlinVersion);
        } else {
            MessageCollector.report$default(this.collector, CompilerMessageSeverity.ERROR, "Unrecognized -Xsupport-compatqual-checker-framework-annotations option: " + supportCompatqualCheckerFrameworkAnnotations + ". Possible values are 'enable'/'disable'", null, 4, null);
            reportLevelForAnnotation = JavaNullabilityAnnotationSettingsKt.getReportLevelForAnnotation(JavaNullabilityAnnotationSettingsKt.getCHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE(), nullabilityAnnotationReportLevels, this.kotlinVersion);
        }
        Jsr305Settings jsr305State = parseJsr305State(jsr305Args);
        final ReportLevel jspecifyReportLevel = parseJspecifyReportLevel(jspecifyState, nullabilityAnnotationReportLevels);
        return new JavaTypeEnhancementState(jsr305State, new Function1<FqName, ReportLevel>() { // from class: org.jetbrains.kotlin.cli.common.arguments.JavaTypeEnhancementStateParser.parse.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ReportLevel invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (!FqNamesUtilKt.isSubpackageOf(it, JavaNullabilityAnnotationSettingsKt.getJSPECIFY_OLD_ANNOTATIONS_PACKAGE()) && !FqNamesUtilKt.isSubpackageOf(it, JavaNullabilityAnnotationSettingsKt.getJSPECIFY_ANNOTATIONS_PACKAGE())) {
                    return FqNamesUtilKt.isSubpackageOf(it, JavaNullabilityAnnotationSettingsKt.getCHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE()) ? reportLevelForAnnotation : JavaNullabilityAnnotationSettingsKt.getReportLevelForAnnotation(it, nullabilityAnnotationReportLevels, this.kotlinVersion);
                }
                return jspecifyReportLevel;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<FqName, ReportLevel> parseNullabilityAnnotationReportLevels(String item) {
        if (!StringsKt.startsWith$default(item, PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT, false, 2, (Object) null)) {
            reportUnrecognizedReportLevel(item, "-Xnullability-annotations");
            return null;
        }
        Pair<FqName, ReportLevel> annotationWithReportLevel = parseAnnotationWithReportLevel(item, "-Xnullability-annotations");
        if (annotationWithReportLevel == null) {
            return null;
        }
        return TuplesKt.to(annotationWithReportLevel.component1(), annotationWithReportLevel.component2());
    }

    private final NullabilityAnnotationStates<ReportLevel> parseNullabilityAnnotationReportLevels(String[] nullabilityAnnotations) {
        if (nullabilityAnnotations == null || nullabilityAnnotations.length == 0) {
            return NullabilityAnnotationStates.INSTANCE.getEMPTY();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = ArrayIteratorKt.iterator(nullabilityAnnotations);
        while (it.hasNext()) {
            String str = (String) it.next();
            Pair<FqName, ReportLevel> nullabilityAnnotationReportLevels = parseNullabilityAnnotationReportLevels(str);
            if (nullabilityAnnotationReportLevels != null) {
                FqName fqNameComponent1 = nullabilityAnnotationReportLevels.component1();
                ReportLevel reportLevelComponent2 = nullabilityAnnotationReportLevels.component2();
                ReportLevel reportLevel = (ReportLevel) linkedHashMap.get(fqNameComponent1);
                if (reportLevel == null) {
                    linkedHashMap.put(fqNameComponent1, reportLevelComponent2);
                } else if (reportLevel != reportLevelComponent2) {
                    reportDuplicateAnnotation(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + fqNameComponent1 + ':' + reportLevel.getDescription(), str, "-Xnullability-annotations");
                }
            }
        }
        return new NullabilityAnnotationStatesImpl(linkedHashMap);
    }

    private final ReportLevel parseJspecifyReportLevel(String jspecifyState, NullabilityAnnotationStates<? extends ReportLevel> nullabilityAnnotationReportLevels) {
        if (jspecifyState == null) {
            return JavaNullabilityAnnotationSettingsKt.getReportLevelForAnnotation(JavaNullabilityAnnotationSettingsKt.getJSPECIFY_ANNOTATIONS_PACKAGE(), nullabilityAnnotationReportLevels, this.kotlinVersion);
        }
        ReportLevel reportLevelFindByDescription = ReportLevel.INSTANCE.findByDescription(jspecifyState);
        if (reportLevelFindByDescription != null) {
            return reportLevelFindByDescription;
        }
        MessageCollector.report$default(this.collector, CompilerMessageSeverity.ERROR, "Unrecognized -Xjspecify-annotations option: " + jspecifyState + ". Possible values are 'disable'/'warn'/'strict'", null, 4, null);
        return JavaNullabilityAnnotationSettingsKt.getReportLevelForAnnotation(JavaNullabilityAnnotationSettingsKt.getJSPECIFY_ANNOTATIONS_PACKAGE(), nullabilityAnnotationReportLevels, this.kotlinVersion);
    }

    private final Jsr305Settings parseJsr305State(String[] args) {
        ReportLevel globalLevel;
        ReportLevel defaultMigrationJsr305ReportLevelForGivenGlobal;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Jsr305Settings defaultJsr305Settings = JavaNullabilityAnnotationSettingsKt.getDefaultJsr305Settings(this.kotlinVersion);
        if (args != null) {
            globalLevel = null;
            defaultMigrationJsr305ReportLevelForGivenGlobal = null;
            for (String str : args) {
                if (StringsKt.startsWith$default(str, PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT, false, 2, (Object) null)) {
                    Pair<FqName, ReportLevel> annotationWithReportLevel = parseAnnotationWithReportLevel(str, "-Xjsr305");
                    if (annotationWithReportLevel != null) {
                        FqName fqNameComponent1 = annotationWithReportLevel.component1();
                        ReportLevel reportLevelComponent2 = annotationWithReportLevel.component2();
                        ReportLevel reportLevel = linkedHashMap.get(fqNameComponent1);
                        if (reportLevel == null) {
                            linkedHashMap.put(fqNameComponent1, reportLevelComponent2);
                        } else if (reportLevel != reportLevelComponent2) {
                            reportDuplicateAnnotation(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + fqNameComponent1 + ':' + reportLevel.getDescription(), str, "-Xjsr305");
                        }
                    }
                } else if (StringsKt.startsWith$default(str, "under-migration", false, 2, (Object) null)) {
                    ReportLevel jsr305State$parseJsr305UnderMigration = parseJsr305State$parseJsr305UnderMigration(this, "-Xjsr305", str);
                    if (defaultMigrationJsr305ReportLevelForGivenGlobal == null) {
                        defaultMigrationJsr305ReportLevelForGivenGlobal = jsr305State$parseJsr305UnderMigration;
                    } else if (defaultMigrationJsr305ReportLevelForGivenGlobal != jsr305State$parseJsr305UnderMigration) {
                        reportDuplicateAnnotation("under-migration:" + defaultMigrationJsr305ReportLevelForGivenGlobal.getDescription(), str, "-Xjsr305");
                    }
                } else if (Intrinsics.areEqual(str, CommonCompilerArguments.ENABLE)) {
                    MessageCollector.report$default(this.collector, CompilerMessageSeverity.STRONG_WARNING, "Option 'enable' for -Xjsr305 flag is deprecated. Please use 'strict' instead", null, 4, null);
                    if (globalLevel == null) {
                        globalLevel = ReportLevel.STRICT;
                    }
                } else if (globalLevel == null) {
                    globalLevel = ReportLevel.INSTANCE.findByDescription(str);
                } else if (!Intrinsics.areEqual(globalLevel.getDescription(), str)) {
                    reportDuplicateAnnotation(globalLevel.getDescription(), str, "-Xjsr305");
                }
            }
        } else {
            globalLevel = null;
            defaultMigrationJsr305ReportLevelForGivenGlobal = null;
        }
        if (globalLevel == null) {
            globalLevel = defaultJsr305Settings.getGlobalLevel();
        }
        if (defaultMigrationJsr305ReportLevelForGivenGlobal == null) {
            defaultMigrationJsr305ReportLevelForGivenGlobal = JavaNullabilityAnnotationSettingsKt.getDefaultMigrationJsr305ReportLevelForGivenGlobal(globalLevel);
        }
        if (linkedHashMap.isEmpty()) {
            linkedHashMap = null;
        }
        if (linkedHashMap == null) {
            linkedHashMap = defaultJsr305Settings.getUserDefinedLevelForSpecificAnnotation();
        }
        return new Jsr305Settings(globalLevel, defaultMigrationJsr305ReportLevelForGivenGlobal, linkedHashMap);
    }

    private static final ReportLevel parseJsr305State$parseJsr305UnderMigration(JavaTypeEnhancementStateParser javaTypeEnhancementStateParser, String str, String str2) {
        List listSplit$default = StringsKt.split$default((CharSequence) str2, new String[]{":"}, false, 0, 6, (Object) null);
        if (listSplit$default.size() != 2) {
            listSplit$default = null;
        }
        ReportLevel reportLevelFindByDescription = ReportLevel.INSTANCE.findByDescription(listSplit$default != null ? (String) listSplit$default.get(1) : null);
        if (reportLevelFindByDescription != null) {
            return reportLevelFindByDescription;
        }
        javaTypeEnhancementStateParser.reportUnrecognizedReportLevel(str2, str);
        Unit unit = Unit.INSTANCE;
        return null;
    }

    private final void reportUnrecognizedReportLevel(String item, String sourceCompilerOption) {
        MessageCollector.report$default(this.collector, CompilerMessageSeverity.ERROR, "Unrecognized " + sourceCompilerOption + " value: " + item, null, 4, null);
    }

    private final void reportDuplicateAnnotation(String first, String second, String sourceCompilerOption) {
        MessageCollector.report$default(this.collector, CompilerMessageSeverity.ERROR, "Conflict duplicating " + sourceCompilerOption + " value: " + first + ", " + second, null, 4, null);
    }

    private final Pair<FqName, ReportLevel> parseAnnotationWithReportLevel(String item, String sourceCompilerOption) {
        String strSubstring = item.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        List listSplit$default = StringsKt.split$default((CharSequence) strSubstring, new String[]{":"}, false, 0, 6, (Object) null);
        if (listSplit$default.size() != 2) {
            listSplit$default = null;
        }
        if (listSplit$default == null) {
            reportUnrecognizedReportLevel(item, sourceCompilerOption);
            return null;
        }
        String str = (String) listSplit$default.get(0);
        ReportLevel reportLevelFindByDescription = ReportLevel.INSTANCE.findByDescription((String) listSplit$default.get(1));
        if (reportLevelFindByDescription == null) {
            reportUnrecognizedReportLevel(item, sourceCompilerOption);
            return null;
        }
        return TuplesKt.to(new FqName(str), reportLevelFindByDescription);
    }

    /* JADX INFO: compiled from: JavaTypeEnhancementStateParser.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b2\u0006\u0010\u000b\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/JavaTypeEnhancementStateParser$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/cli/common/arguments/JavaTypeEnhancementStateParser;", "NULLABILITY_ANNOTATIONS_COMPILER_OPTION", "", "parsePlainNullabilityAnnotationReportLevels", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/load/java/ReportLevel;", "nullabilityAnnotations", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Pair<FqName, ReportLevel> parsePlainNullabilityAnnotationReportLevels(String nullabilityAnnotations) {
            Intrinsics.checkNotNullParameter(nullabilityAnnotations, "nullabilityAnnotations");
            Pair<FqName, ReportLevel> nullabilityAnnotationReportLevels = JavaTypeEnhancementStateParser.DEFAULT.parseNullabilityAnnotationReportLevels(nullabilityAnnotations);
            Intrinsics.checkNotNull(nullabilityAnnotationReportLevels);
            return nullabilityAnnotationReportLevels;
        }
    }
}
