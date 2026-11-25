package org.jetbrains.kotlin.config;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.ImportTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: CommonConfigurationKeys.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\t0\t0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u000b0\u000b0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u000e0\u000e0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00100\u00100\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0012\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00130\u00130\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00160\u00160\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R*\u0010\u0018\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u001a \u0006*\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00190\u00190\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001d\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u001e0\u001e0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001f\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010 0 0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\"\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010#0#0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010$\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010&\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010'\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010(\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/config/CommonConfigurationKeys;", "", "()V", "ALLOW_ANY_SCRIPTS_IN_SOURCE_ROOTS", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "DISABLE_INLINE", "ENUM_WHEN_TRACKER", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "EVALUATED_CONST_TRACKER", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "EXPECT_ACTUAL_LINKER", "EXPECT_ACTUAL_TRACKER", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "HMPP_MODULE_STRUCTURE", "Lorg/jetbrains/kotlin/config/HmppCliModuleStructure;", "IGNORE_CONST_OPTIMIZATION_ERRORS", "IMPORT_TRACKER", "Lorg/jetbrains/kotlin/incremental/components/ImportTracker;", "INCREMENTAL_COMPILATION", "INLINE_CONST_TRACKER", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "KLIB_NORMALIZE_ABSOLUTE_PATH", "KLIB_RELATIVE_PATH_BASES", "", "", "LANGUAGE_VERSION_SETTINGS", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "LOOKUP_TRACKER", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "METADATA_VERSION", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "MODULE_NAME", "PARALLEL_BACKEND_THREADS", "", "PRODUCE_KLIB_SIGNATURES_CLASH_CHECKS", "REPORT_OUTPUT_FILES", "USE_FIR", "USE_FIR_EXTENDED_CHECKERS", "USE_LIGHT_TREE", "config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonConfigurationKeys {
    public static final CompilerConfigurationKey<Boolean> ALLOW_ANY_SCRIPTS_IN_SOURCE_ROOTS;
    public static final CompilerConfigurationKey<EnumWhenTracker> ENUM_WHEN_TRACKER;
    public static final CompilerConfigurationKey<EvaluatedConstTracker> EVALUATED_CONST_TRACKER;
    public static final CompilerConfigurationKey<Boolean> EXPECT_ACTUAL_LINKER;
    public static final CompilerConfigurationKey<ExpectActualTracker> EXPECT_ACTUAL_TRACKER;
    public static final CompilerConfigurationKey<HmppCliModuleStructure> HMPP_MODULE_STRUCTURE;
    public static final CompilerConfigurationKey<Boolean> IGNORE_CONST_OPTIMIZATION_ERRORS;
    public static final CompilerConfigurationKey<ImportTracker> IMPORT_TRACKER;
    public static final CompilerConfigurationKey<Boolean> INCREMENTAL_COMPILATION;
    public static final CompilerConfigurationKey<InlineConstTracker> INLINE_CONST_TRACKER;
    public static final CompilerConfigurationKey<Boolean> KLIB_NORMALIZE_ABSOLUTE_PATH;
    public static final CompilerConfigurationKey<Collection<String>> KLIB_RELATIVE_PATH_BASES;
    public static final CompilerConfigurationKey<LookupTracker> LOOKUP_TRACKER;
    public static final CompilerConfigurationKey<BinaryVersion> METADATA_VERSION;
    public static final CompilerConfigurationKey<Integer> PARALLEL_BACKEND_THREADS;
    public static final CompilerConfigurationKey<Boolean> PRODUCE_KLIB_SIGNATURES_CLASH_CHECKS;
    public static final CompilerConfigurationKey<Boolean> USE_FIR;
    public static final CompilerConfigurationKey<Boolean> USE_FIR_EXTENDED_CHECKERS;
    public static final CompilerConfigurationKey<Boolean> USE_LIGHT_TREE;
    public static final CommonConfigurationKeys INSTANCE = new CommonConfigurationKeys();
    public static final CompilerConfigurationKey<LanguageVersionSettings> LANGUAGE_VERSION_SETTINGS = new CompilerConfigurationKey<>("language version settings");
    public static final CompilerConfigurationKey<Boolean> DISABLE_INLINE = new CompilerConfigurationKey<>("disable inline");
    public static final CompilerConfigurationKey<String> MODULE_NAME = new CompilerConfigurationKey<>("module name");
    public static final CompilerConfigurationKey<Boolean> REPORT_OUTPUT_FILES = new CompilerConfigurationKey<>("report output files");

    private CommonConfigurationKeys() {
    }

    static {
        CompilerConfigurationKey<LookupTracker> compilerConfigurationKeyCreate = CompilerConfigurationKey.create("lookup tracker");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate, "create(...)");
        LOOKUP_TRACKER = compilerConfigurationKeyCreate;
        CompilerConfigurationKey<ExpectActualTracker> compilerConfigurationKeyCreate2 = CompilerConfigurationKey.create("expect actual tracker");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate2, "create(...)");
        EXPECT_ACTUAL_TRACKER = compilerConfigurationKeyCreate2;
        CompilerConfigurationKey<InlineConstTracker> compilerConfigurationKeyCreate3 = CompilerConfigurationKey.create("inline constant tracker");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate3, "create(...)");
        INLINE_CONST_TRACKER = compilerConfigurationKeyCreate3;
        CompilerConfigurationKey<EnumWhenTracker> compilerConfigurationKeyCreate4 = CompilerConfigurationKey.create("enum when tracker");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate4, "create(...)");
        ENUM_WHEN_TRACKER = compilerConfigurationKeyCreate4;
        CompilerConfigurationKey<ImportTracker> compilerConfigurationKeyCreate5 = CompilerConfigurationKey.create("import tracker");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate5, "create(...)");
        IMPORT_TRACKER = compilerConfigurationKeyCreate5;
        CompilerConfigurationKey<BinaryVersion> compilerConfigurationKeyCreate6 = CompilerConfigurationKey.create("metadata version");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate6, "create(...)");
        METADATA_VERSION = compilerConfigurationKeyCreate6;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate7 = CompilerConfigurationKey.create("front-end IR");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate7, "create(...)");
        USE_FIR = compilerConfigurationKeyCreate7;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate8 = CompilerConfigurationKey.create("light tree");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate8, "create(...)");
        USE_LIGHT_TREE = compilerConfigurationKeyCreate8;
        CompilerConfigurationKey<HmppCliModuleStructure> compilerConfigurationKeyCreate9 = CompilerConfigurationKey.create("HMPP module structure");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate9, "create(...)");
        HMPP_MODULE_STRUCTURE = compilerConfigurationKeyCreate9;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate10 = CompilerConfigurationKey.create("Experimental expect/actual linker");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate10, "create(...)");
        EXPECT_ACTUAL_LINKER = compilerConfigurationKeyCreate10;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate11 = CompilerConfigurationKey.create("fir extended checkers");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate11, "create(...)");
        USE_FIR_EXTENDED_CHECKERS = compilerConfigurationKeyCreate11;
        CompilerConfigurationKey<Integer> compilerConfigurationKeyCreate12 = CompilerConfigurationKey.create("When using the IR backend, run lowerings by file in N parallel threads");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate12, "create(...)");
        PARALLEL_BACKEND_THREADS = compilerConfigurationKeyCreate12;
        CompilerConfigurationKey<Collection<String>> compilerConfigurationKeyCreate13 = CompilerConfigurationKey.create("Provides a path from which relative paths in klib are being computed");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate13, "create(...)");
        KLIB_RELATIVE_PATH_BASES = compilerConfigurationKeyCreate13;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate14 = CompilerConfigurationKey.create("Normalize absolute paths in klib (replace file separator with '/')");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate14, "create(...)");
        KLIB_NORMALIZE_ABSOLUTE_PATH = compilerConfigurationKeyCreate14;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate15 = CompilerConfigurationKey.create("Turn on the checks on uniqueness of signatures");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate15, "create(...)");
        PRODUCE_KLIB_SIGNATURES_CLASH_CHECKS = compilerConfigurationKeyCreate15;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate16 = CompilerConfigurationKey.create("Enable incremental compilation");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate16, "create(...)");
        INCREMENTAL_COMPILATION = compilerConfigurationKeyCreate16;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate17 = CompilerConfigurationKey.create("Allow to compile any scripts along with regular Kotlin sources");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate17, "create(...)");
        ALLOW_ANY_SCRIPTS_IN_SOURCE_ROOTS = compilerConfigurationKeyCreate17;
        CompilerConfigurationKey<Boolean> compilerConfigurationKeyCreate18 = CompilerConfigurationKey.create("Ignore errors from IrConstTransformer");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate18, "create(...)");
        IGNORE_CONST_OPTIMIZATION_ERRORS = compilerConfigurationKeyCreate18;
        CompilerConfigurationKey<EvaluatedConstTracker> compilerConfigurationKeyCreate19 = CompilerConfigurationKey.create("Keeps track of all evaluated by IrInterpreter constants");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate19, "create(...)");
        EVALUATED_CONST_TRACKER = compilerConfigurationKeyCreate19;
    }
}
