package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.build.GeneratedFile;
import org.jetbrains.kotlin.build.SourcesUtilsKt;
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.cli.common.CodeAnalysisMeasurement;
import org.jetbrains.kotlin.cli.common.CodeGenerationMeasurement;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.CompilerInitializationMeasurement;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.PerformanceMeasurement;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.compilerRunner.MessageCollectorToOutputItemsCollectorAdapter;
import org.jetbrains.kotlin.compilerRunner.OutputItemsCollectorImpl;
import org.jetbrains.kotlin.compilerRunner.SimpleOutputItem;
import org.jetbrains.kotlin.compilerRunner.SimpleOutputItemKt;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.incremental.IncrementalCachesManager;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.parsing.ParseFileUtilsKt;
import org.jetbrains.kotlin.incremental.storage.FileLocations;
import org.jetbrains.kotlin.incremental.storage.FileToAbsolutePathConverter;
import org.jetbrains.kotlin.incremental.storage.FileToPathConverter;
import org.jetbrains.kotlin.incremental.util.BufferingMessageCollector;
import org.jetbrains.kotlin.incremental.util.ExceptionLocation;
import org.jetbrains.kotlin.incremental.util.MessageCollectorUtilKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.CompilationCanceledStatus;
import org.jetbrains.kotlin.util.UtilKt;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;

/* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009a\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000 \u0096\u0001*\b\b\u0000\u0010\u0001*\u00020\u0002*\f\b\u0001\u0010\u0003*\u0006\u0012\u0002\b\u00030\u00042\u00020\u0005:\n\u0095\u0001\u0096\u0001\u0097\u0001\u0098\u0001\u0099\u0001Ba\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0012¢\u0006\u0002\u0010\u0015J1\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00070+2\u0006\u0010,\u001a\u00028\u00012\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u001d2\u0006\u0010/\u001a\u000200H\u0014¢\u0006\u0002\u00101J\u000e\u00102\u001a\b\u0012\u0004\u0012\u0002030+H\u0014JA\u00104\u001a\u0002052\u0006\u0010,\u001a\u00028\u00012\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00028\u00002\u0006\u00109\u001a\u00020:2\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020=0<H$¢\u0006\u0002\u0010>J\u0016\u0010?\u001a\u00020@2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0010H\u0002J\b\u0010A\u001a\u00020@H\u0002J?\u0010B\u001a\u00020C2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00070\u001d2\u0006\u00108\u001a\u00028\u00002\u0006\u00109\u001a\u00020:2\b\u00106\u001a\u0004\u0018\u00010E2\n\b\u0002\u0010F\u001a\u0004\u0018\u00010G¢\u0006\u0002\u0010HJM\u0010I\u001a\u00020C2\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u0002052\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00070\u001d2\u0006\u00108\u001a\u00028\u00002\u0006\u0010,\u001a\u00028\u00012\b\u0010M\u001a\u0004\u0018\u00010N2\u0006\u00109\u001a\u00020:H\u0002¢\u0006\u0002\u0010OJE\u0010P\u001a\u00020C2\u0006\u0010Q\u001a\u00020R2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00070\u001d2\u0006\u00108\u001a\u00028\u00002\b\u0010F\u001a\u0004\u0018\u00010G2\u0006\u0010S\u001a\u00020\u00122\u0006\u00109\u001a\u00020:H\u0002¢\u0006\u0002\u0010TJ\u001d\u0010U\u001a\u00028\u00012\u0006\u0010J\u001a\u00020K2\u0006\u00108\u001a\u00028\u0000H$¢\u0006\u0002\u0010VJ\u001a\u0010W\u001a\u00020K2\b\u0010F\u001a\u0004\u0018\u00010G2\u0006\u0010X\u001a\u00020YH\u0002J\b\u0010Z\u001a\u00020[H\u0002J\u0015\u0010\\\u001a\u00020\u00072\u0006\u00108\u001a\u00028\u0000H$¢\u0006\u0002\u0010]JM\u0010^\u001a\u00020C2\u0006\u0010J\u001a\u00020K2\u0006\u0010,\u001a\u00028\u00012\u0006\u0010L\u001a\u0002052\f\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00070\u001d2\u0006\u00108\u001a\u00028\u00002\b\u0010M\u001a\u0004\u0018\u00010N2\u0006\u0010`\u001a\u00020:H\u0002¢\u0006\u0002\u0010aJ-\u0010b\u001a\u0002072\b\u00106\u001a\u0004\u0018\u0001072\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00070\u001d2\u0006\u0010,\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010cJ!\u0010d\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020=0<2\u0006\u00108\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010eJ\b\u0010f\u001a\u00020gH\u0016J\u001c\u0010h\u001a\u00020i2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u00106\u001a\u000207H\u0004J\u0010\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020mH\u0002J\u0018\u0010n\u001a\u00020@2\u0006\u0010o\u001a\u00020p2\u0006\u00106\u001a\u000207H\u0004JC\u0010q\u001a\u00020r2\u0006\u00108\u001a\u00028\u00002\u0006\u0010s\u001a\u00020g2\u0006\u0010t\u001a\u00020u2\u0006\u0010,\u001a\u00028\u00012\f\u0010v\u001a\b\u0012\u0004\u0012\u00020\u00070w2\u0006\u0010x\u001a\u00020\u0012H\u0014¢\u0006\u0002\u0010yJ%\u0010z\u001a\u00020@2\u0006\u0010L\u001a\u0002052\u0006\u0010{\u001a\u00020C2\u0006\u0010,\u001a\u00028\u0001H\u0014¢\u0006\u0002\u0010|J\u001d\u0010}\u001a\u00020@2\u0006\u0010L\u001a\u0002052\u0006\u00108\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010~J+\u0010\u007f\u001a\u00020@2\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u0002052\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0007\u0010\u0082\u0001\u001a\u00020iH\u0002J\u0013\u0010\u0083\u0001\u001a\u00020@2\b\u0010\u0084\u0001\u001a\u00030\u0085\u0001H\u0004Jh\u0010\u0086\u0001\u001a\u0015\u0012\u0004\u0012\u00020C\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00100\u0087\u00012\r\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u00020\u00070\u001d2\u0006\u00108\u001a\u00028\u00002\u0006\u0010,\u001a\u00028\u00012\u0006\u0010/\u001a\u0002002\u0006\u00109\u001a\u00020:2\r\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u00020\u00070\u001d2\u0006\u0010x\u001a\u00020\u0012H$¢\u0006\u0003\u0010\u008a\u0001J\u0017\u0010\u008b\u0001\u001a\u00020\u00122\u0006\u0010,\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008c\u0001J7\u0010\u008d\u0001\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020=0<2\u0006\u00108\u001a\u00028\u00002\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bH\u0014¢\u0006\u0003\u0010\u008e\u0001JA\u0010\u008f\u0001\u001a\u00020k2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00070\u001d2\b\u00106\u001a\u0004\u0018\u00010E2\u0006\u00108\u001a\u00028\u00002\b\u0010F\u001a\u0004\u0018\u00010G2\u0006\u00109\u001a\u00020:H\u0002¢\u0006\u0003\u0010\u0090\u0001J7\u0010\u0091\u0001\u001a\u00020@2\u0006\u0010/\u001a\u0002002\u0006\u0010,\u001a\u00028\u00012\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u001d2\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001H$¢\u0006\u0003\u0010\u0094\u0001R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u001dX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0018R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0012\u0010$\u001a\u00020\u0012X¤\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0012\u0010'\u001a\u00020\u0012X¤\u0004¢\u0006\u0006\u001a\u0004\b(\u0010&R\u0014\u0010\u0011\u001a\u00020\u0012X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010&R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u009a\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner;", "Args", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "CacheManager", "Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;", "", "workingDir", "Ljava/io/File;", "cacheDirName", "", "reporter", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "buildHistoryFile", "outputDirs", "", "withAbiSnapshot", "", "preciseCompilationResultsBackup", "keepIncrementalCompilationCachesInMemory", "(Ljava/io/File;Ljava/lang/String;Lorg/jetbrains/kotlin/build/report/BuildReporter;Ljava/io/File;Ljava/util/Collection;ZZZ)V", "abiSnapshotFile", "getBuildHistoryFile", "()Ljava/io/File;", "cacheDirectory", "getCacheDirectory", "dirtySourcesSinceLastTimeFile", "kotlinSourceFilesExtensions", "", "getKotlinSourceFilesExtensions", "()Ljava/util/List;", "lastBuildInfoFile", "getLastBuildInfoFile", "getReporter", "()Lorg/jetbrains/kotlin/build/report/BuildReporter;", "shouldStoreFullFqNamesInLookupCache", "getShouldStoreFullFqNamesInLookupCache", "()Z", "shouldTrackChangesInLookupCache", "getShouldTrackChangesInLookupCache", "getWithAbiSnapshot", "additionalDirtyFiles", "", "caches", "generatedFiles", "Lorg/jetbrains/kotlin/build/GeneratedFile;", "services", "Lorg/jetbrains/kotlin/config/Services;", "(Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;Ljava/util/List;Lorg/jetbrains/kotlin/config/Services;)Ljava/lang/Iterable;", "additionalDirtyLookupSymbols", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "calculateSourcesToCompile", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;", "changedFiles", "Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "args", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "classpathAbiSnapshots", "", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "(Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Ljava/util/Map;)Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;", "cleanOrCreateDirectories", "", "collectMetrics", "compile", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "allSourceFiles", "Lorg/jetbrains/kotlin/incremental/ChangedFiles;", "fileLocations", "Lorg/jetbrains/kotlin/incremental/storage/FileLocations;", "(Ljava/util/List;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/incremental/ChangedFiles;Lorg/jetbrains/kotlin/incremental/storage/FileLocations;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "compileImpl", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "compilationMode", "abiSnapshotData", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$AbiSnapshotData;", "(Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;Ljava/util/List;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$AbiSnapshotData;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "compileNonIncrementally", "rebuildReason", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "trackChangedFiles", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;Ljava/util/List;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/incremental/storage/FileLocations;ZLorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "createCacheManager", "(Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;)Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;", "createIncrementalCompilationContext", "transaction", "Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "createTransaction", "Lorg/jetbrains/kotlin/incremental/BaseCompilationTransaction;", "destinationDir", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;)Ljava/io/File;", "doCompile", "allKotlinSources", "originalMessageCollector", "(Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;Ljava/util/List;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$AbiSnapshotData;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "getChangedFiles", "(Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;Ljava/util/List;Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;)Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "getClasspathAbiSnapshot", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;)Ljava/util/Map;", "getLookupTrackerDelegate", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "getRemovedClassesChanges", "Lorg/jetbrains/kotlin/incremental/DirtyData;", "incrementalCompilationExceptionTransformer", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult;", "t", "", "initDirtyFiles", "dirtyFiles", "Lorg/jetbrains/kotlin/incremental/DirtyFilesContainer;", "makeServices", "Lorg/jetbrains/kotlin/config/Services$Builder;", "lookupTracker", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "dirtySources", "", "isIncremental", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;Ljava/util/Set;Z)Lorg/jetbrains/kotlin/config/Services$Builder;", "performWorkAfterCompilation", "exitCode", "(Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;Lorg/jetbrains/kotlin/cli/common/ExitCode;Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;)V", "performWorkBeforeCompilation", "(Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;)V", "processChangesAfterBuild", "currentBuildInfo", "Lorg/jetbrains/kotlin/incremental/BuildInfo;", "dirtyData", "reportPerformanceData", "defaultPerformanceManager", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "runCompiler", "Lkotlin/Pair;", "sourcesToCompile", "allSources", "(Ljava/util/List;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;Lorg/jetbrains/kotlin/config/Services;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Ljava/util/List;Z)Lkotlin/Pair;", "runWithNoDirtyKotlinSources", "(Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;)Z", "setupJarDependencies", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/build/report/BuildReporter;)Ljava/util/Map;", "tryCompileIncrementally", "(Ljava/util/List;Lorg/jetbrains/kotlin/incremental/ChangedFiles;Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;Lorg/jetbrains/kotlin/incremental/storage/FileLocations;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult;", "updateCaches", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "(Lorg/jetbrains/kotlin/config/Services;Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;Ljava/util/List;Lorg/jetbrains/kotlin/incremental/ChangesCollector;)V", "AbiSnapshotData", "Companion", "CompilationMode", "EmptyCompilationCanceledStatus", "ICResult", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IncrementalCompilerRunner<Args extends CommonCompilerArguments, CacheManager extends IncrementalCachesManager<?>> {
    public static final String ABI_SNAPSHOT_FILE_NAME = "abi-snapshot.bin";
    public static final String DIRTY_SOURCES_FILE_NAME = "dirty-sources.txt";
    public static final String LAST_BUILD_INFO_FILE_NAME = "last-build.bin";
    private final File abiSnapshotFile;
    private final File buildHistoryFile;
    private final File cacheDirectory;
    private final File dirtySourcesSinceLastTimeFile;
    private final boolean keepIncrementalCompilationCachesInMemory;
    private final List<String> kotlinSourceFilesExtensions;
    private final File lastBuildInfoFile;
    private final Collection<File> outputDirs;
    private final boolean preciseCompilationResultsBackup;
    private final BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter;
    private final boolean withAbiSnapshot;
    private final File workingDir;

    protected abstract CompilationMode calculateSourcesToCompile(CacheManager caches, ChangedFiles.Known changedFiles, Args args, MessageCollector messageCollector, Map<String, ? extends AbiSnapshot> classpathAbiSnapshots);

    protected abstract CacheManager createCacheManager(IncrementalCompilationContext icContext, Args args);

    protected abstract File destinationDir(Args args);

    protected abstract boolean getShouldStoreFullFqNamesInLookupCache();

    protected abstract boolean getShouldTrackChangesInLookupCache();

    protected void performWorkBeforeCompilation(CompilationMode compilationMode, Args args) {
        Intrinsics.checkNotNullParameter(compilationMode, "compilationMode");
        Intrinsics.checkNotNullParameter(args, "args");
    }

    protected abstract Pair<ExitCode, Collection<File>> runCompiler(List<? extends File> sourcesToCompile, Args args, CacheManager caches, Services services, MessageCollector messageCollector, List<? extends File> allSources, boolean isIncremental);

    public boolean runWithNoDirtyKotlinSources(CacheManager caches) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        return false;
    }

    protected abstract void updateCaches(Services services, CacheManager caches, List<? extends GeneratedFile> generatedFiles, ChangesCollector changesCollector);

    /* JADX WARN: Multi-variable type inference failed */
    public IncrementalCompilerRunner(File workingDir, String cacheDirName, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter, File file, Collection<? extends File> collection, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(workingDir, "workingDir");
        Intrinsics.checkNotNullParameter(cacheDirName, "cacheDirName");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        this.workingDir = workingDir;
        this.reporter = reporter;
        this.buildHistoryFile = file;
        this.outputDirs = collection;
        this.withAbiSnapshot = z;
        this.preciseCompilationResultsBackup = z2;
        this.keepIncrementalCompilationCachesInMemory = z3;
        this.cacheDirectory = new File(workingDir, cacheDirName);
        this.dirtySourcesSinceLastTimeFile = new File(workingDir, DIRTY_SOURCES_FILE_NAME);
        this.lastBuildInfoFile = new File(workingDir, LAST_BUILD_INFO_FILE_NAME);
        this.abiSnapshotFile = new File(workingDir, ABI_SNAPSHOT_FILE_NAME);
        this.kotlinSourceFilesExtensions = SourcesUtilsKt.getDEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS();
    }

    public /* synthetic */ IncrementalCompilerRunner(File file, String str, BuildReporter buildReporter, File file2, Collection collection, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, str, buildReporter, file2, collection, (i & 32) != 0 ? false : z, (i & 64) != 0 ? false : z2, (i & 128) != 0 ? false : z3);
    }

    protected final BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> getReporter() {
        return this.reporter;
    }

    protected final File getBuildHistoryFile() {
        return this.buildHistoryFile;
    }

    protected final boolean getWithAbiSnapshot() {
        return this.withAbiSnapshot;
    }

    protected final File getCacheDirectory() {
        return this.cacheDirectory;
    }

    protected final File getLastBuildInfoFile() {
        return this.lastBuildInfoFile;
    }

    protected List<String> getKotlinSourceFilesExtensions() {
        return this.kotlinSourceFilesExtensions;
    }

    private final IncrementalCompilationContext createIncrementalCompilationContext(FileLocations fileLocations, CompilationTransaction transaction) {
        FileToPathConverter relocatablePathConverterForSourceFiles;
        FileToPathConverter relocatablePathConverterForOutputFiles;
        if (fileLocations == null || (relocatablePathConverterForSourceFiles = fileLocations.getRelocatablePathConverterForSourceFiles()) == null) {
            relocatablePathConverterForSourceFiles = FileToAbsolutePathConverter.INSTANCE;
        }
        FileToPathConverter fileToPathConverter = relocatablePathConverterForSourceFiles;
        if (fileLocations == null || (relocatablePathConverterForOutputFiles = fileLocations.getRelocatablePathConverterForOutputFiles()) == null) {
            relocatablePathConverterForOutputFiles = FileToAbsolutePathConverter.INSTANCE;
        }
        return new IncrementalCompilationContext(fileToPathConverter, relocatablePathConverterForOutputFiles, getShouldStoreFullFqNamesInLookupCache(), transaction, this.reporter, getShouldTrackChangesInLookupCache(), this.keepIncrementalCompilationCachesInMemory);
    }

    public static /* synthetic */ ExitCode compile$default(IncrementalCompilerRunner incrementalCompilerRunner, List list, CommonCompilerArguments commonCompilerArguments, MessageCollector messageCollector, ChangedFiles changedFiles, FileLocations fileLocations, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: compile");
        }
        if ((i & 16) != 0) {
            fileLocations = null;
        }
        return incrementalCompilerRunner.compile(list, commonCompilerArguments, messageCollector, changedFiles, fileLocations);
    }

    public final ExitCode compile(List<? extends File> allSourceFiles, Args args, MessageCollector messageCollector, ChangedFiles changedFiles, FileLocations fileLocations) {
        ExitCode exitCodeCompileNonIncrementally;
        Intrinsics.checkNotNullParameter(allSourceFiles, "allSourceFiles");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.INCREMENTAL_COMPILATION_DAEMON;
        buildReporter.startMeasure(gradleBuildTime);
        try {
            final ICResult iCResultTryCompileIncrementally = tryCompileIncrementally(allSourceFiles, changedFiles, args, fileLocations, messageCollector);
            if (iCResultTryCompileIncrementally instanceof ICResult.Completed) {
                ICReporterKt.debug(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalCompilerRunner$compile$1$1
                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Incremental compilation completed";
                    }
                });
                exitCodeCompileNonIncrementally = ((ICResult.Completed) iCResultTryCompileIncrementally).getExitCode();
            } else if (iCResultTryCompileIncrementally instanceof ICResult.RequiresRebuild) {
                ICReporterKt.info(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalCompilerRunner$compile$1$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Non-incremental compilation will be performed: " + ((IncrementalCompilerRunner.ICResult.RequiresRebuild) iCResultTryCompileIncrementally).getReason();
                    }
                });
                this.reporter.addAttribute(((ICResult.RequiresRebuild) iCResultTryCompileIncrementally).getReason());
                exitCodeCompileNonIncrementally = compileNonIncrementally(((ICResult.RequiresRebuild) iCResultTryCompileIncrementally).getReason(), allSourceFiles, args, fileLocations, changedFiles == null, messageCollector);
            } else if (iCResultTryCompileIncrementally instanceof ICResult.Failed) {
                MessageCollectorUtilKt.reportException(messageCollector, ((ICResult.Failed) iCResultTryCompileIncrementally).getCause(), ExceptionLocation.INCREMENTAL_COMPILATION);
                ICReporterKt.warn(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalCompilerRunner$compile$1$3
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return StringsKt.trimMargin$default("\n                    |Incremental compilation was attempted but failed:\n                    |    " + ((IncrementalCompilerRunner.ICResult.Failed) iCResultTryCompileIncrementally).getReason().getReadableString() + ": " + UtilKt.removeSuffixIfPresent(ExceptionsKt.stackTraceToString(((IncrementalCompilerRunner.ICResult.Failed) iCResultTryCompileIncrementally).getCause()), "\n") + "\n                    |    Falling back to non-incremental compilation (reason = " + ((IncrementalCompilerRunner.ICResult.Failed) iCResultTryCompileIncrementally).getReason() + ")\n                    |    To help us fix this issue, please file a bug at https://youtrack.jetbrains.com/issues/KT with the above stack trace.\n                    |    (Be sure to search for the above exception in existing issues first to avoid filing duplicated bugs.)             \n                    ", null, 1, null);
                    }
                });
                this.reporter.addAttribute(((ICResult.Failed) iCResultTryCompileIncrementally).getReason());
                exitCodeCompileNonIncrementally = compileNonIncrementally(((ICResult.Failed) iCResultTryCompileIncrementally).getReason(), allSourceFiles, args, fileLocations, changedFiles == null, messageCollector);
            } else {
                throw new NoWhenBranchMatchedException();
            }
            buildReporter.endMeasure(gradleBuildTime);
            return exitCodeCompileNonIncrementally;
        } catch (Throwable th) {
            buildReporter.endMeasure(gradleBuildTime);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\br\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult;", "", "Completed", "Failed", "RequiresRebuild", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult$Completed;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult$Failed;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult$RequiresRebuild;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    interface ICResult {

        /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult$Completed;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult;", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "(Lorg/jetbrains/kotlin/cli/common/ExitCode;)V", "getExitCode", "()Lorg/jetbrains/kotlin/cli/common/ExitCode;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Completed implements ICResult {
            private final ExitCode exitCode;

            public Completed(ExitCode exitCode) {
                Intrinsics.checkNotNullParameter(exitCode, "exitCode");
                this.exitCode = exitCode;
            }

            public final ExitCode getExitCode() {
                return this.exitCode;
            }
        }

        /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult$RequiresRebuild;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult;", "reason", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;)V", "getReason", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class RequiresRebuild implements ICResult {
            private final BuildAttribute reason;

            public RequiresRebuild(BuildAttribute reason) {
                Intrinsics.checkNotNullParameter(reason, "reason");
                this.reason = reason;
            }

            public final BuildAttribute getReason() {
                return this.reason;
            }
        }

        /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult$Failed;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$ICResult;", "reason", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "cause", "", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;Ljava/lang/Throwable;)V", "getCause", "()Ljava/lang/Throwable;", "getReason", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Failed implements ICResult {
            private final Throwable cause;
            private final BuildAttribute reason;

            public Failed(BuildAttribute reason, Throwable cause) {
                Intrinsics.checkNotNullParameter(reason, "reason");
                Intrinsics.checkNotNullParameter(cause, "cause");
                this.reason = reason;
                this.cause = cause;
            }

            public final Throwable getCause() {
                return this.cause;
            }

            public final BuildAttribute getReason() {
                return this.reason;
            }
        }
    }

    private final ICResult incrementalCompilationExceptionTransformer(Throwable t) throws Throwable {
        if (t instanceof CachesManagerCloseException) {
            return new ICResult.Failed(BuildAttribute.IC_FAILED_TO_CLOSE_CACHES, t);
        }
        throw t;
    }

    private final ICResult tryCompileIncrementally(List<? extends File> allSourceFiles, ChangedFiles changedFiles, Args args, FileLocations fileLocations, MessageCollector messageCollector) throws Throwable {
        Object objM2898constructorimpl;
        if (changedFiles instanceof ChangedFiles.Unknown) {
            return new ICResult.RequiresRebuild(BuildAttribute.UNKNOWN_CHANGES_IN_GRADLE_INPUTS);
        }
        BaseCompilationTransaction baseCompilationTransactionCreateTransaction = createTransaction();
        try {
            Result.Companion companion = Result.INSTANCE;
            BaseCompilationTransaction baseCompilationTransaction = baseCompilationTransactionCreateTransaction;
            boolean z = false;
            try {
                try {
                    try {
                        IncrementalCompilationContext incrementalCompilationContextCreateIncrementalCompilationContext = createIncrementalCompilationContext(fileLocations, baseCompilationTransactionCreateTransaction);
                        IncrementalCachesManager incrementalCachesManagerCreateCacheManager = createCacheManager(incrementalCompilationContextCreateIncrementalCompilationContext, args);
                        baseCompilationTransactionCreateTransaction.setCachesManager(incrementalCachesManagerCreateCacheManager);
                        ICResult iCResultTryCompileIncrementally$lambda$6$compile = tryCompileIncrementally$lambda$6$compile(this, changedFiles, allSourceFiles, incrementalCachesManagerCreateCacheManager, args, incrementalCompilationContextCreateIncrementalCompilationContext, messageCollector);
                        if ((iCResultTryCompileIncrementally$lambda$6$compile instanceof ICResult.Completed) && ((ICResult.Completed) iCResultTryCompileIncrementally$lambda$6$compile).getExitCode() == ExitCode.OK) {
                            baseCompilationTransactionCreateTransaction.markAsSuccessful();
                        }
                        baseCompilationTransaction.close();
                        objM2898constructorimpl = Result.m2898constructorimpl(iCResultTryCompileIncrementally$lambda$6$compile);
                        Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(objM2898constructorimpl);
                        if (thM2901exceptionOrNullimpl != null) {
                            Result.Companion companion2 = Result.INSTANCE;
                            objM2898constructorimpl = Result.m2898constructorimpl(incrementalCompilationExceptionTransformer(thM2901exceptionOrNullimpl));
                        }
                        ResultKt.throwOnFailure(objM2898constructorimpl);
                        return (ICResult) objM2898constructorimpl;
                    } catch (Throwable th) {
                        baseCompilationTransactionCreateTransaction.setExecutionThrowable(th);
                        throw th;
                    }
                } catch (Exception e) {
                    z = true;
                    try {
                        baseCompilationTransaction.close();
                    } catch (Exception unused) {
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                if (!z) {
                    baseCompilationTransaction.close();
                }
                throw th2;
            }
        } catch (Throwable th3) {
            Result.Companion companion3 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th3));
        }
    }

    private static final <Args extends CommonCompilerArguments, CacheManager extends IncrementalCachesManager<?>> ICResult tryCompileIncrementally$lambda$6$compile(final IncrementalCompilerRunner<Args, CacheManager> incrementalCompilerRunner, ChangedFiles changedFiles, List<? extends File> list, CacheManager cachemanager, Args args, IncrementalCompilationContext incrementalCompilationContext, MessageCollector messageCollector) {
        Map<String, AbiSnapshot> mapEmptyMap;
        try {
            ChangedFiles.Known changedFiles2 = incrementalCompilerRunner.getChangedFiles((ChangedFiles.Known) changedFiles, list, cachemanager);
            AbiSnapshotData abiSnapshotData = null;
            Map<String, AbiSnapshot> classpathAbiSnapshot = ((IncrementalCompilerRunner) incrementalCompilerRunner).withAbiSnapshot ? incrementalCompilerRunner.getClasspathAbiSnapshot(args) : null;
            try {
                BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = ((IncrementalCompilerRunner) incrementalCompilerRunner).reporter;
                GradleBuildTime gradleBuildTime = GradleBuildTime.IC_CALCULATE_INITIAL_DIRTY_SET;
                buildReporter.startMeasure(gradleBuildTime);
                if (classpathAbiSnapshot == null) {
                    try {
                        mapEmptyMap = MapsKt.emptyMap();
                    } catch (Throwable th) {
                        buildReporter.endMeasure(gradleBuildTime);
                        throw th;
                    }
                } else {
                    mapEmptyMap = classpathAbiSnapshot;
                }
                CompilationMode compilationModeCalculateSourcesToCompile = incrementalCompilerRunner.calculateSourcesToCompile(cachemanager, changedFiles2, args, messageCollector, mapEmptyMap);
                buildReporter.endMeasure(gradleBuildTime);
                if (compilationModeCalculateSourcesToCompile instanceof CompilationMode.Rebuild) {
                    return new ICResult.RequiresRebuild(((CompilationMode.Rebuild) compilationModeCalculateSourcesToCompile).getReason());
                }
                if (((IncrementalCompilerRunner) incrementalCompilerRunner).withAbiSnapshot) {
                    if (!((IncrementalCompilerRunner) incrementalCompilerRunner).abiSnapshotFile.exists()) {
                        ICReporterKt.debug(((IncrementalCompilerRunner) incrementalCompilerRunner).reporter, new Function0<String>(incrementalCompilerRunner) { // from class: org.jetbrains.kotlin.incremental.IncrementalCompilerRunner$tryCompileIncrementally$2$compile$abiSnapshotData$1
                            final /* synthetic */ IncrementalCompilerRunner<Args, CacheManager> this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                                this.this$0 = incrementalCompilerRunner;
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final String invoke() {
                                return "Jar snapshot file does not exist: " + ((IncrementalCompilerRunner) this.this$0).abiSnapshotFile.getPath();
                            }
                        });
                        return new ICResult.RequiresRebuild(BuildAttribute.NO_ABI_SNAPSHOT);
                    }
                    ICReporterKt.info(((IncrementalCompilerRunner) incrementalCompilerRunner).reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalCompilerRunner$tryCompileIncrementally$2$compile$abiSnapshotData$2
                        @Override // kotlin.jvm.functions.Function0
                        public final String invoke() {
                            return "Incremental compilation with ABI snapshot enabled";
                        }
                    });
                    AbiSnapshot abiSnapshot = AbiSnapshotImpl.INSTANCE.read(((IncrementalCompilerRunner) incrementalCompilerRunner).abiSnapshotFile);
                    Intrinsics.checkNotNull(classpathAbiSnapshot);
                    abiSnapshotData = new AbiSnapshotData(abiSnapshot, classpathAbiSnapshot);
                }
                try {
                    Intrinsics.checkNotNull(compilationModeCalculateSourcesToCompile, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.CompilationMode.Incremental");
                    return new ICResult.Completed(incrementalCompilerRunner.compileImpl(incrementalCompilationContext, (CompilationMode.Incremental) compilationModeCalculateSourcesToCompile, list, args, cachemanager, abiSnapshotData, messageCollector));
                } catch (Throwable th2) {
                    return new ICResult.Failed(BuildAttribute.IC_FAILED_TO_COMPILE_INCREMENTALLY, th2);
                }
            } catch (Throwable th3) {
                return new ICResult.Failed(BuildAttribute.IC_FAILED_TO_COMPUTE_FILES_TO_RECOMPILE, th3);
            }
        } catch (Throwable th4) {
            return new ICResult.Failed(BuildAttribute.IC_FAILED_TO_GET_CHANGED_FILES, th4);
        }
    }

    private final ExitCode compileNonIncrementally(BuildAttribute rebuildReason, List<? extends File> allSourceFiles, Args args, FileLocations fileLocations, boolean trackChangedFiles, MessageCollector messageCollector) {
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.CLEAR_OUTPUT_ON_REBUILD;
        buildReporter.startMeasure(gradleBuildTime);
        try {
            Set of = SetsKt.setOf((Object[]) new File[]{destinationDir(args), this.workingDir});
            final Set set = this.outputDirs;
            if (set != null) {
                if (!set.containsAll(of)) {
                    throw new IllegalStateException(("outputDirs is missing classesDir and workingDir: " + set).toString());
                }
                if (set == null) {
                    set = of;
                }
            } else {
                set = of;
            }
            ICReporterKt.debug(this.reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalCompilerRunner$compileNonIncrementally$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Cleaning " + set.size() + " output directories";
                }
            });
            cleanOrCreateDirectories(set);
            Unit unit = Unit.INSTANCE;
            buildReporter.endMeasure(gradleBuildTime);
            IncrementalCompilationContext incrementalCompilationContextCreateIncrementalCompilationContext = createIncrementalCompilationContext(fileLocations, new NonRecoverableCompilationTransaction());
            IncrementalCachesManager incrementalCachesManagerCreateCacheManager = createCacheManager(incrementalCompilationContextCreateIncrementalCompilationContext, args);
            try {
                IncrementalCachesManager incrementalCachesManager = incrementalCachesManagerCreateCacheManager;
                if (trackChangedFiles) {
                    incrementalCachesManager.getInputsCache().getSourceSnapshotMap().compareAndUpdate(allSourceFiles);
                }
                ExitCode exitCodeCompileImpl = compileImpl(incrementalCompilationContextCreateIncrementalCompilationContext, new CompilationMode.Rebuild(rebuildReason), allSourceFiles, args, incrementalCachesManager, this.withAbiSnapshot ? new AbiSnapshotData(new AbiSnapshotImpl(new LinkedHashMap()), getClasspathAbiSnapshot(args)) : null, messageCollector);
                CloseableKt.closeFinally(incrementalCachesManagerCreateCacheManager, null);
                return exitCodeCompileImpl;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(incrementalCachesManagerCreateCacheManager, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            buildReporter.endMeasure(gradleBuildTime);
            throw th3;
        }
    }

    /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0007R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$AbiSnapshotData;", "", "snapshot", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "classpathAbiSnapshot", "", "", "(Lorg/jetbrains/kotlin/incremental/AbiSnapshot;Ljava/util/Map;)V", "getClasspathAbiSnapshot", "()Ljava/util/Map;", "getSnapshot", "()Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class AbiSnapshotData {
        private final Map<String, AbiSnapshot> classpathAbiSnapshot;
        private final AbiSnapshot snapshot;

        /* JADX WARN: Multi-variable type inference failed */
        public AbiSnapshotData(AbiSnapshot snapshot, Map<String, ? extends AbiSnapshot> classpathAbiSnapshot) {
            Intrinsics.checkNotNullParameter(snapshot, "snapshot");
            Intrinsics.checkNotNullParameter(classpathAbiSnapshot, "classpathAbiSnapshot");
            this.snapshot = snapshot;
            this.classpathAbiSnapshot = classpathAbiSnapshot;
        }

        public final Map<String, AbiSnapshot> getClasspathAbiSnapshot() {
            return this.classpathAbiSnapshot;
        }

        public final AbiSnapshot getSnapshot() {
            return this.snapshot;
        }
    }

    private final Map<String, AbiSnapshot> getClasspathAbiSnapshot(Args args) {
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.SET_UP_ABI_SNAPSHOTS;
        buildReporter.startMeasure(gradleBuildTime);
        try {
            return setupJarDependencies(args, this.reporter);
        } finally {
            buildReporter.endMeasure(gradleBuildTime);
        }
    }

    private final void cleanOrCreateDirectories(Collection<? extends File> outputDirs) throws IOException {
        for (File file : CollectionsKt.toSet(outputDirs)) {
            if (file.isDirectory()) {
                FileUtilsKt.deleteDirectoryContents(file);
            } else if (file.isFile()) {
                String str = "Expected a directory but found a regular file: " + file.getPath();
            } else {
                FileUtilsKt.createDirectory(file);
            }
        }
    }

    private final ChangedFiles.Known getChangedFiles(ChangedFiles.Known changedFiles, List<? extends File> allSourceFiles, CacheManager caches) {
        if (changedFiles == null) {
            return caches.getInputsCache().getSourceSnapshotMap().compareAndUpdate(allSourceFiles);
        }
        if (!changedFiles.getForDependencies()) {
            return changedFiles;
        }
        ChangedFiles.Known knownCompareAndUpdate = caches.getInputsCache().getSourceSnapshotMap().compareAndUpdate(allSourceFiles);
        return new ChangedFiles.Known(CollectionsKt.plus((Collection) changedFiles.getModified(), (Iterable) knownCompareAndUpdate.getModified()), CollectionsKt.plus((Collection) changedFiles.getRemoved(), (Iterable) knownCompareAndUpdate.getRemoved()), false, 4, null);
    }

    protected Map<String, AbiSnapshot> setupJarDependencies(Args args, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter) {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        return MapsKt.emptyMap();
    }

    protected final void initDirtyFiles(DirtyFilesContainer dirtyFiles, ChangedFiles.Known changedFiles) {
        Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
        Intrinsics.checkNotNullParameter(changedFiles, "changedFiles");
        dirtyFiles.add(changedFiles.getModified(), "was modified since last time");
        dirtyFiles.add(changedFiles.getRemoved(), "was removed since last time");
        if (this.dirtySourcesSinceLastTimeFile.exists()) {
            List lines$default = FilesKt.readLines$default(this.dirtySourcesSinceLastTimeFile, null, 1, null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(lines$default, 10));
            Iterator it = lines$default.iterator();
            while (it.hasNext()) {
                arrayList.add(new File((String) it.next()));
            }
            dirtyFiles.add(arrayList, "was not compiled last time");
        }
    }

    /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b4\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;", "", "()V", "Incremental", "Rebuild", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode$Incremental;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode$Rebuild;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static abstract class CompilationMode {
        public /* synthetic */ CompilationMode(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode$Incremental;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;", "dirtyFiles", "Lorg/jetbrains/kotlin/incremental/DirtyFilesContainer;", "(Lorg/jetbrains/kotlin/incremental/DirtyFilesContainer;)V", "getDirtyFiles", "()Lorg/jetbrains/kotlin/incremental/DirtyFilesContainer;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Incremental extends CompilationMode {
            private final DirtyFilesContainer dirtyFiles;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Incremental(DirtyFilesContainer dirtyFiles) {
                super(null);
                Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
                this.dirtyFiles = dirtyFiles;
            }

            public final DirtyFilesContainer getDirtyFiles() {
                return this.dirtyFiles;
            }
        }

        private CompilationMode() {
        }

        /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode$Rebuild;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;", "reason", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;)V", "getReason", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Rebuild extends CompilationMode {
            private final BuildAttribute reason;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Rebuild(BuildAttribute reason) {
                super(null);
                Intrinsics.checkNotNullParameter(reason, "reason");
                this.reason = reason;
            }

            public final BuildAttribute getReason() {
                return this.reason;
            }
        }
    }

    protected Iterable<File> additionalDirtyFiles(CacheManager caches, List<? extends GeneratedFile> generatedFiles, Services services) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(generatedFiles, "generatedFiles");
        Intrinsics.checkNotNullParameter(services, "services");
        return CollectionsKt.emptyList();
    }

    protected Iterable<LookupSymbol> additionalDirtyLookupSymbols() {
        return CollectionsKt.emptyList();
    }

    protected Services.Builder makeServices(Args args, LookupTracker lookupTracker, ExpectActualTracker expectActualTracker, CacheManager caches, Set<? extends File> dirtySources, boolean isIncremental) {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(dirtySources, "dirtySources");
        Services.Builder builder = new Services.Builder();
        builder.register(LookupTracker.class, lookupTracker);
        builder.register(ExpectActualTracker.class, expectActualTracker);
        builder.register(CompilationCanceledStatus.class, EmptyCompilationCanceledStatus.INSTANCE);
        return builder;
    }

    private final ExitCode compileImpl(IncrementalCompilationContext icContext, CompilationMode compilationMode, List<? extends File> allSourceFiles, Args args, CacheManager caches, AbiSnapshotData abiSnapshotData, MessageCollector messageCollector) throws Throwable {
        performWorkBeforeCompilation(compilationMode, args);
        ArrayList arrayList = new ArrayList();
        for (Object obj : allSourceFiles) {
            if (FileUtilsKt.isKotlinFile((File) obj, getKotlinSourceFilesExtensions())) {
                arrayList.add(obj);
            }
        }
        ExitCode exitCodeDoCompile = doCompile(icContext, caches, compilationMode, arrayList, args, abiSnapshotData, messageCollector);
        performWorkAfterCompilation(compilationMode, exitCodeDoCompile, caches);
        return exitCodeDoCompile;
    }

    private final BaseCompilationTransaction createTransaction() throws IOException {
        if (this.preciseCompilationResultsBackup) {
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
            Path pathCreateTempDirectory = Files.createTempDirectory("kotlin-backups", new FileAttribute[0]);
            Intrinsics.checkNotNullExpressionValue(pathCreateTempDirectory, "createTempDirectory(...)");
            return new RecoverableCompilationTransaction(buildReporter, pathCreateTempDirectory);
        }
        return new NonRecoverableCompilationTransaction();
    }

    protected void performWorkAfterCompilation(CompilationMode compilationMode, ExitCode exitCode, CacheManager caches) {
        Intrinsics.checkNotNullParameter(compilationMode, "compilationMode");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
        Intrinsics.checkNotNullParameter(caches, "caches");
        collectMetrics();
    }

    private final void collectMetrics() {
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.CALCULATE_OUTPUT_SIZE;
        buildReporter.startMeasure(gradleBuildTime);
        try {
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter2 = this.reporter;
            GradleBuildPerformanceMetric gradleBuildPerformanceMetric = GradleBuildPerformanceMetric.SNAPSHOT_SIZE;
            File file = this.buildHistoryFile;
            long length = 0;
            buildReporter2.addMetric(gradleBuildPerformanceMetric, (file != null ? file.length() : 0L) + this.lastBuildInfoFile.length() + this.abiSnapshotFile.length());
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter3 = this.reporter;
            GradleBuildPerformanceMetric gradleBuildPerformanceMetric2 = GradleBuildPerformanceMetric.CACHE_DIRECTORY_SIZE;
            Iterator<File> it = FilesKt.walk$default(this.cacheDirectory, null, 1, null).iterator();
            while (it.hasNext()) {
                length += it.next().length();
            }
            buildReporter3.addMetric(gradleBuildPerformanceMetric2, length);
            Unit unit = Unit.INSTANCE;
        } finally {
            buildReporter.endMeasure(gradleBuildTime);
        }
    }

    private final ExitCode doCompile(IncrementalCompilationContext icContext, CacheManager caches, CompilationMode compilationMode, List<? extends File> allKotlinSources, Args args, AbiSnapshotData abiSnapshotData, MessageCollector originalMessageCollector) throws Throwable {
        LinkedHashSet<File> linkedHashSet;
        Map<String, AbiSnapshot> mapEmptyMap;
        JvmMetadataVersion metadataVersion;
        BuildInfo buildInfo;
        GradleBuildTime gradleBuildTime;
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter;
        CacheManager cachemanager;
        Services services;
        boolean z;
        CompilationMode compilationMode2;
        HashSet hashSet;
        HashSet hashSet2;
        ExitCode exitCode;
        JvmMetadataVersion jvmMetadataVersion;
        ExitCode exitCode2;
        HashSet hashSet3;
        HashSet hashSet4;
        IncrementalCompilationContext incrementalCompilationContext;
        BuildInfo buildInfo2;
        CacheManager cachemanager2 = caches;
        CompilationMode compilationMode3 = compilationMode;
        List<? extends File> list = allKotlinSources;
        boolean z2 = compilationMode3 instanceof CompilationMode.Incremental;
        if (z2) {
            linkedHashSet = ((CompilationMode.Incremental) compilationMode3).getDirtyFiles().toMutableLinkedSet();
        } else {
            if (!(compilationMode3 instanceof CompilationMode.Rebuild)) {
                throw new NoWhenBranchMatchedException();
            }
            linkedHashSet = new LinkedHashSet<>(list);
        }
        LinkedHashSet<File> linkedHashSet2 = linkedHashSet;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (abiSnapshotData == null || (mapEmptyMap = abiSnapshotData.getClasspathAbiSnapshot()) == null) {
            mapEmptyMap = MapsKt.emptyMap();
        }
        BuildInfo buildInfo3 = new BuildInfo(jCurrentTimeMillis, mapEmptyMap);
        HashSet hashSet5 = new HashSet();
        HashSet hashSet6 = new HashSet();
        HashSet hashSet7 = new HashSet();
        CompilationTransaction transaction = icContext.getTransaction();
        ExitCode exitCode3 = ExitCode.OK;
        LanguageVersion languageVersionFromVersionString = LanguageVersion.INSTANCE.fromVersionString(args.getLanguageVersion());
        if (languageVersionFromVersionString == null || (metadataVersion = MetadataHelpersKt.toMetadataVersion(languageVersionFromVersionString)) == null) {
            metadataVersion = JvmMetadataVersion.INSTANCE;
        }
        JvmMetadataVersion jvmMetadataVersion2 = metadataVersion;
        while (true) {
            LinkedHashSet<File> linkedHashSet3 = linkedHashSet2;
            if (!CollectionsKt.any(linkedHashSet3) && !runWithNoDirtyKotlinSources(cachemanager2)) {
                exitCode = exitCode3;
                hashSet = hashSet6;
                hashSet2 = hashSet5;
                compilationMode2 = compilationMode3;
                z = z2;
                buildInfo = buildInfo3;
                break;
            }
            LinkedHashSet<File> linkedHashSet4 = linkedHashSet2;
            linkedHashSet2.addAll(caches.getPlatformCache().getComplementaryFilesRecursive(linkedHashSet4));
            caches.getPlatformCache().markDirty((Collection<? extends File>) linkedHashSet4);
            caches.getInputsCache().removeOutputForSourceFiles(linkedHashSet3);
            LookupTrackerImpl lookupTrackerImpl = new LookupTrackerImpl(getLookupTrackerDelegate());
            ExpectActualTrackerImpl expectActualTrackerImpl = new ExpectActualTrackerImpl();
            JvmMetadataVersion jvmMetadataVersion3 = jvmMetadataVersion2;
            ArrayList arrayList = new ArrayList();
            CompilationTransaction compilationTransaction = transaction;
            ArrayList arrayList2 = new ArrayList();
            for (File file : linkedHashSet3) {
                HashSet hashSet8 = hashSet7;
                HashSet hashSet9 = hashSet6;
                File file2 = file;
                if (file2.exists() && list.contains(file2)) {
                    arrayList.add(file);
                } else {
                    arrayList2.add(file);
                }
                hashSet7 = hashSet8;
                hashSet6 = hashSet9;
            }
            HashSet hashSet10 = hashSet7;
            HashSet hashSet11 = hashSet6;
            Pair pair = new Pair(arrayList, arrayList2);
            List<? extends File> list2 = (List) pair.component1();
            List list3 = (List) pair.component2();
            buildInfo = buildInfo3;
            Services servicesBuild = makeServices(args, lookupTrackerImpl, expectActualTrackerImpl, caches, CollectionsKt.toSet(linkedHashSet3), z2).build();
            args.setReportOutputFiles(true);
            OutputItemsCollectorImpl outputItemsCollectorImpl = new OutputItemsCollectorImpl();
            TransactionOutputsRegistrar transactionOutputsRegistrar = new TransactionOutputsRegistrar(compilationTransaction, outputItemsCollectorImpl);
            BufferingMessageCollector bufferingMessageCollector = new BufferingMessageCollector();
            MessageCollectorToOutputItemsCollectorAdapter messageCollectorToOutputItemsCollectorAdapter = new MessageCollectorToOutputItemsCollectorAdapter(bufferingMessageCollector, transactionOutputsRegistrar);
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter2 = this.reporter;
            GradleBuildTime gradleBuildTime2 = GradleBuildTime.COMPILATION_ROUND;
            buildReporter2.startMeasure(gradleBuildTime2);
            try {
                boolean z3 = z2;
                buildReporter = buildReporter2;
                HashSet hashSet12 = hashSet5;
                try {
                    Pair<ExitCode, Collection<File>> pairRunCompiler = runCompiler(list2, args, caches, servicesBuild, messageCollectorToOutputItemsCollectorAdapter, allKotlinSources, compilationMode3 instanceof CompilationMode.Incremental);
                    buildReporter.endMeasure(gradleBuildTime2);
                    ExitCode exitCodeComponent1 = pairRunCompiler.component1();
                    Collection<File> collectionComponent2 = pairRunCompiler.component2();
                    linkedHashSet2.addAll(collectionComponent2);
                    hashSet10.addAll(linkedHashSet4);
                    String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
                    Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
                    String strJoinToString$default = CollectionsKt.joinToString$default(hashSet10, property, null, null, 0, null, new Function1<File, CharSequence>() { // from class: org.jetbrains.kotlin.incremental.IncrementalCompilerRunner$doCompile$text$1
                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(File it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            String absolutePath = FilesKt.normalize(it).getAbsolutePath();
                            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                            return absolutePath;
                        }
                    }, 30, null);
                    Path path = this.dirtySourcesSinceLastTimeFile.toPath();
                    Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
                    CompilationTransactionKt.writeText(compilationTransaction, path, strJoinToString$default);
                    List<SimpleOutputItem> outputs = outputItemsCollectorImpl.getOutputs();
                    Intrinsics.checkNotNullExpressionValue(outputs, "getOutputs(...)");
                    List<SimpleOutputItem> list4 = outputs;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
                    for (SimpleOutputItem simpleOutputItem : list4) {
                        Intrinsics.checkNotNull(simpleOutputItem);
                        arrayList3.add(SimpleOutputItemKt.toGeneratedFile(simpleOutputItem, jvmMetadataVersion3));
                    }
                    ArrayList arrayList4 = arrayList3;
                    if (z3) {
                        HashSet hashSet13 = CollectionsKt.toHashSet(linkedHashSet3);
                        cachemanager = caches;
                        services = servicesBuild;
                        Iterable<File> iterableAdditionalDirtyFiles = additionalDirtyFiles(cachemanager, arrayList4, services);
                        ArrayList arrayList5 = new ArrayList();
                        for (File file3 : iterableAdditionalDirtyFiles) {
                            if (!hashSet13.contains(file3)) {
                                arrayList5.add(file3);
                            }
                        }
                        ArrayList arrayList6 = arrayList5;
                        if (!arrayList6.isEmpty()) {
                            linkedHashSet2.addAll(arrayList6);
                            Iterator it = arrayList4.iterator();
                            while (it.hasNext()) {
                                Path path2 = ((GeneratedFile) it.next()).getOutputFile().toPath();
                                Intrinsics.checkNotNullExpressionValue(path2, "toPath(...)");
                                compilationTransaction.deleteFile(path2);
                            }
                            jvmMetadataVersion = jvmMetadataVersion3;
                            exitCode2 = exitCodeComponent1;
                            hashSet4 = hashSet11;
                            z = z3;
                            hashSet3 = hashSet12;
                            compilationMode2 = compilationMode;
                        }
                        compilationMode3 = compilationMode2;
                        hashSet6 = hashSet4;
                        cachemanager2 = cachemanager;
                        z2 = z;
                        hashSet7 = hashSet10;
                        transaction = compilationTransaction;
                        jvmMetadataVersion2 = jvmMetadataVersion;
                        buildInfo3 = buildInfo;
                        list = allKotlinSources;
                        hashSet5 = hashSet3;
                        exitCode3 = exitCode2;
                    } else {
                        cachemanager = caches;
                        services = servicesBuild;
                    }
                    z = z3;
                    this.reporter.reportCompileIteration(z, collectionComponent2, exitCodeComponent1);
                    bufferingMessageCollector.flush(originalMessageCollector);
                    if (exitCodeComponent1 == ExitCode.OK) {
                        Path path3 = this.dirtySourcesSinceLastTimeFile.toPath();
                        Intrinsics.checkNotNullExpressionValue(path3, "toPath(...)");
                        compilationTransaction.deleteFile(path3);
                        ChangesCollector changesCollector = new ChangesCollector();
                        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter3 = this.reporter;
                        GradleBuildTime gradleBuildTime3 = GradleBuildTime.IC_UPDATE_CACHES;
                        buildReporter3.startMeasure(gradleBuildTime3);
                        try {
                            jvmMetadataVersion = jvmMetadataVersion3;
                            caches.getPlatformCache().updateComplementaryFiles(linkedHashSet2, expectActualTrackerImpl);
                            caches.getInputsCache().registerOutputForSourceFiles(arrayList4);
                            BuildUtilKt.update(caches.getLookupCache(), lookupTrackerImpl, list2, list3);
                            updateCaches(services, cachemanager, arrayList4, changesCollector);
                            Unit unit = Unit.INSTANCE;
                            buildReporter3.endMeasure(gradleBuildTime3);
                            compilationMode2 = compilationMode;
                            if (compilationMode2 instanceof CompilationMode.Rebuild) {
                                if (this.withAbiSnapshot) {
                                    Intrinsics.checkNotNull(abiSnapshotData);
                                    abiSnapshotData.getSnapshot().getProtos().putAll(changesCollector.protoDataChanges());
                                }
                            } else {
                                DirtyData changedAndImpactedSymbols = BuildUtilKt.getChangedAndImpactedSymbols(changesCollector, CollectionsKt.listOf(caches.getPlatformCache()), this.reporter);
                                Collection<LookupSymbol> collectionComponent1 = changedAndImpactedSymbols.component1();
                                Collection<FqName> collectionComponent3 = changedAndImpactedSymbols.component2();
                                Collection<FqName> collectionComponent4 = changedAndImpactedSymbols.component3();
                                HashSet hashSet14 = CollectionsKt.toHashSet(list2);
                                Set setMapClassesFqNamesToFiles$default = BuildUtilKt.mapClassesFqNamesToFiles$default(CollectionsKt.listOf(caches.getPlatformCache()), collectionComponent4, this.reporter, null, 8, null);
                                linkedHashSet2.clear();
                                exitCode2 = exitCodeComponent1;
                                HashSet hashSet15 = hashSet14;
                                linkedHashSet2.addAll(BuildUtilKt.mapLookupSymbolsToFiles(caches.getLookupCache(), collectionComponent1, this.reporter, hashSet15));
                                linkedHashSet2.addAll(BuildUtilKt.mapClassesFqNamesToFiles(CollectionsKt.listOf(caches.getPlatformCache()), collectionComponent3, this.reporter, hashSet15));
                                Set set = setMapClassesFqNamesToFiles$default;
                                if (!hashSet14.containsAll(set)) {
                                    linkedHashSet2.addAll(set);
                                }
                                hashSet3 = hashSet12;
                                hashSet3.addAll(collectionComponent1);
                                hashSet4 = hashSet11;
                                hashSet4.addAll(collectionComponent3);
                                if (this.withAbiSnapshot) {
                                    for (FqName fqName : changesCollector.protoDataRemoved()) {
                                        Intrinsics.checkNotNull(abiSnapshotData);
                                        abiSnapshotData.getSnapshot().getProtos().remove(fqName);
                                    }
                                    Intrinsics.checkNotNull(abiSnapshotData);
                                    abiSnapshotData.getSnapshot().getProtos().putAll(changesCollector.protoDataChanges());
                                }
                                compilationMode3 = compilationMode2;
                                hashSet6 = hashSet4;
                                cachemanager2 = cachemanager;
                                z2 = z;
                                hashSet7 = hashSet10;
                                transaction = compilationTransaction;
                                jvmMetadataVersion2 = jvmMetadataVersion;
                                buildInfo3 = buildInfo;
                                list = allKotlinSources;
                                hashSet5 = hashSet3;
                                exitCode3 = exitCode2;
                            }
                        } catch (Throwable th) {
                            buildReporter3.endMeasure(gradleBuildTime3);
                            throw th;
                        }
                    } else {
                        compilationMode2 = compilationMode;
                    }
                    hashSet = hashSet11;
                    hashSet2 = hashSet12;
                    exitCode = exitCodeComponent1;
                    break;
                } catch (Throwable th2) {
                    th = th2;
                    gradleBuildTime = gradleBuildTime2;
                    buildReporter.endMeasure(gradleBuildTime);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                gradleBuildTime = gradleBuildTime2;
                buildReporter = buildReporter2;
            }
        }
        if (exitCode == ExitCode.OK) {
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter4 = this.reporter;
            GradleBuildTime gradleBuildTime4 = GradleBuildTime.STORE_BUILD_INFO;
            buildReporter4.startMeasure(gradleBuildTime4);
            try {
                incrementalCompilationContext = icContext;
                buildInfo2 = buildInfo;
                BuildInfo.INSTANCE.write(incrementalCompilationContext, buildInfo2, this.lastBuildInfoFile);
                if (this.withAbiSnapshot) {
                    AbiSnapshotImpl.Companion companion = AbiSnapshotImpl.INSTANCE;
                    Intrinsics.checkNotNull(abiSnapshotData);
                    companion.write(incrementalCompilationContext, abiSnapshotData.getSnapshot(), this.abiSnapshotFile);
                }
                Unit unit2 = Unit.INSTANCE;
                buildReporter4.endMeasure(gradleBuildTime4);
            } catch (Throwable th4) {
                buildReporter4.endMeasure(gradleBuildTime4);
                throw th4;
            }
        } else {
            incrementalCompilationContext = icContext;
            buildInfo2 = buildInfo;
        }
        if (exitCode == ExitCode.OK && z) {
            CollectionsKt.addAll(hashSet2, additionalDirtyLookupSymbols());
        }
        processChangesAfterBuild(incrementalCompilationContext, compilationMode2, buildInfo2, new DirtyData(hashSet2, hashSet, null, 4, null));
        return exitCode;
    }

    public LookupTracker getLookupTrackerDelegate() {
        return LookupTracker.DO_NOTHING.INSTANCE;
    }

    protected final DirtyData getRemovedClassesChanges(IncrementalCachesManager<?> caches, ChangedFiles.Known changedFiles) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(changedFiles, "changedFiles");
        HashSet hashSet = new HashSet();
        List<File> modified = changedFiles.getModified();
        HashSet hashSet2 = new HashSet();
        for (Object obj : modified) {
            if (FileUtilsKt.isKotlinFile((File) obj, getKotlinSourceFilesExtensions())) {
                hashSet2.add(obj);
            }
        }
        HashSet hashSet3 = hashSet2;
        List<File> removed = changedFiles.getRemoved();
        HashSet hashSet4 = new HashSet();
        for (Object obj2 : removed) {
            if (FileUtilsKt.isKotlinFile((File) obj2, getKotlinSourceFilesExtensions())) {
                hashSet4.add(obj2);
            }
        }
        HashSet hashSet5 = hashSet3;
        Set<String> setClassesFqNames = ParseFileUtilsKt.classesFqNames(hashSet5);
        Collection<FqName> collectionClassesFqNamesBySources = caches.getPlatformCache().classesFqNamesBySources(SetsKt.plus((Set) hashSet5, (Iterable) hashSet4));
        ArrayList<String> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionClassesFqNamesBySources, 10));
        Iterator<T> it = collectionClassesFqNamesBySources.iterator();
        while (it.hasNext()) {
            arrayList.add(((FqName) it.next()).asString());
        }
        for (String str : arrayList) {
            if (!setClassesFqNames.contains(str)) {
                hashSet.add(str);
            }
        }
        ChangesCollector changesCollector = new ChangesCollector();
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            changesCollector.collectSignature(new FqName((String) it2.next()), true);
        }
        return BuildUtilKt.getChangedAndImpactedSymbols(changesCollector, CollectionsKt.listOf(caches.getPlatformCache()), this.reporter);
    }

    private final void processChangesAfterBuild(IncrementalCompilationContext icContext, CompilationMode compilationMode, BuildInfo currentBuildInfo, DirtyData dirtyData) {
        List<BuildDifference> listEmptyList;
        BuildDifference buildDifference;
        if (this.buildHistoryFile == null) {
            return;
        }
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter = this.reporter;
        GradleBuildTime gradleBuildTime = GradleBuildTime.IC_WRITE_HISTORY_FILE;
        buildReporter.startMeasure(gradleBuildTime);
        try {
            BuildDiffsStorage fromFile = BuildDiffsStorage.INSTANCE.readFromFile(this.buildHistoryFile, this.reporter);
            if (fromFile == null || (listEmptyList = fromFile.getBuildDiffs()) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            if (compilationMode instanceof CompilationMode.Incremental) {
                buildDifference = new BuildDifference(currentBuildInfo.getStartTS(), true, dirtyData);
            } else {
                buildDifference = new BuildDifference(currentBuildInfo.getStartTS(), false, new DirtyData(null, null, null, 7, null));
            }
            BuildDiffsStorage.INSTANCE.writeToFile(icContext, this.buildHistoryFile, new BuildDiffsStorage(CollectionsKt.plus((Collection<? extends BuildDifference>) listEmptyList, buildDifference)));
            Unit unit = Unit.INSTANCE;
        } finally {
            buildReporter.endMeasure(gradleBuildTime);
        }
    }

    /* JADX INFO: compiled from: IncrementalCompilerRunner.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$EmptyCompilationCanceledStatus;", "Lorg/jetbrains/kotlin/progress/CompilationCanceledStatus;", "()V", "checkCanceled", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class EmptyCompilationCanceledStatus implements CompilationCanceledStatus {
        public static final EmptyCompilationCanceledStatus INSTANCE = new EmptyCompilationCanceledStatus();

        @Override // org.jetbrains.kotlin.progress.CompilationCanceledStatus
        public void checkCanceled() {
        }

        private EmptyCompilationCanceledStatus() {
        }
    }

    protected final void reportPerformanceData(CommonCompilerPerformanceManager defaultPerformanceManager) {
        Intrinsics.checkNotNullParameter(defaultPerformanceManager, "defaultPerformanceManager");
        for (PerformanceMeasurement performanceMeasurement : defaultPerformanceManager.getMeasurementResults()) {
            if (performanceMeasurement instanceof CompilerInitializationMeasurement) {
                this.reporter.addTimeMetricMs(GradleBuildTime.COMPILER_INITIALIZATION, ((CompilerInitializationMeasurement) performanceMeasurement).getMilliseconds());
            } else if (performanceMeasurement instanceof CodeAnalysisMeasurement) {
                CodeAnalysisMeasurement codeAnalysisMeasurement = (CodeAnalysisMeasurement) performanceMeasurement;
                this.reporter.addTimeMetricMs(GradleBuildTime.CODE_ANALYSIS, codeAnalysisMeasurement.getMilliseconds());
                Integer lines = codeAnalysisMeasurement.getLines();
                if (lines != null) {
                    int iIntValue = lines.intValue();
                    this.reporter.addMetric(GradleBuildPerformanceMetric.ANALYZED_LINES_NUMBER, iIntValue);
                    if (codeAnalysisMeasurement.getMilliseconds() > 0) {
                        this.reporter.addMetric(GradleBuildPerformanceMetric.ANALYSIS_LPS, ((long) (iIntValue * 1000)) / codeAnalysisMeasurement.getMilliseconds());
                    }
                }
            } else if (performanceMeasurement instanceof CodeGenerationMeasurement) {
                CodeGenerationMeasurement codeGenerationMeasurement = (CodeGenerationMeasurement) performanceMeasurement;
                this.reporter.addTimeMetricMs(GradleBuildTime.CODE_GENERATION, codeGenerationMeasurement.getMilliseconds());
                Integer lines2 = codeGenerationMeasurement.getLines();
                if (lines2 != null) {
                    int iIntValue2 = lines2.intValue();
                    this.reporter.addMetric(GradleBuildPerformanceMetric.CODE_GENERATED_LINES_NUMBER, iIntValue2);
                    if (codeGenerationMeasurement.getMilliseconds() > 0) {
                        this.reporter.addMetric(GradleBuildPerformanceMetric.CODE_GENERATION_LPS, ((long) (iIntValue2 * 1000)) / codeGenerationMeasurement.getMilliseconds());
                    }
                }
            }
        }
    }
}
