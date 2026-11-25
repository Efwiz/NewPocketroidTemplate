package org.jetbrains.kotlin.cli.js.dce;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.cli.common.CLITool;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.cli.common.arguments.K2JSDceArguments;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.js.dce.Context;
import org.jetbrains.kotlin.js.dce.DCELogLevel;
import org.jetbrains.kotlin.js.dce.DeadCodeElimination;
import org.jetbrains.kotlin.js.dce.DeadCodeEliminationResult;
import org.jetbrains.kotlin.js.dce.DeadCodeEliminationStatus;
import org.jetbrains.kotlin.js.dce.InputFile;
import org.jetbrains.kotlin.js.dce.InputResource;
import org.jetbrains.kotlin.js.dce.PrintTreeKt;
import org.jetbrains.kotlin.js.dce.UtilKt;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMap;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapSourceReplacementException;
import org.jetbrains.kotlin.js.sourceMap.RelativePathCalculator;
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadataUtils;

/* JADX INFO: compiled from: K2JSDce.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 &2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001&B\u0005¢\u0006\u0002\u0010\u0003J&\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u001e\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J \u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0019\u001a\u00020\u0002H\u0016J \u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0002H\u0014J\b\u0010\u001f\u001a\u00020\nH\u0016J\u0010\u0010 \u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u0018\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bH\u0002J&\u0010#\u001a\u00020\u001b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010$\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\nH\u0002J\f\u0010%\u001a\u00020\n*\u00020\nH\u0002¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/dce/K2JSDce;", "Lorg/jetbrains/kotlin/cli/common/CLITool;", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSDceArguments;", "()V", "collectInputFiles", "", "Lorg/jetbrains/kotlin/js/dce/InputFile;", "baseDir", "Ljava/io/File;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "collectInputFilesFromDirectory", "path", "collectInputFilesFromZip", "copyFiles", "", "files", "overwriteOnlyOlderFiles", "", "copyResource", "resource", "Lorg/jetbrains/kotlin/js/dce/InputResource;", "targetFile", "createArguments", "execImpl", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "services", "Lorg/jetbrains/kotlin/config/Services;", "arguments", "executableScriptFileName", "getModuleNameFromPath", "mapSourcePaths", "inputFile", "performDce", "singleInputFile", "metaJs", "Companion", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2JSDce extends CLITool<K2JSDceArguments> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final void main(String[] strArr) {
        INSTANCE.main(strArr);
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public K2JSDceArguments createArguments() {
        return new K2JSDceArguments();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public ExitCode execImpl(MessageCollector messageCollector, Services services, K2JSDceArguments arguments) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        String outputDirectory = arguments.getOutputDirectory();
        if (outputDirectory == null) {
            outputDirectory = "min";
        }
        File file = new File(outputDirectory);
        List<String> freeArgs = arguments.getFreeArgs();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = freeArgs.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, collectInputFiles(file, (String) it.next(), messageCollector));
        }
        ArrayList arrayList2 = arrayList;
        if (messageCollector.getHasErrors()) {
            return ExitCode.COMPILATION_ERROR;
        }
        if (arrayList2.isEmpty() && !arguments.getVersion()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "no source files", null, 4, null);
            return ExitCode.COMPILATION_ERROR;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (InputFile inputFile : arrayList2) {
            InputFile inputFile2 = (InputFile) linkedHashMap.get(inputFile.getOutputPath());
            if (inputFile2 != null) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "duplicate target file will be created for '" + inputFile.getResource().getName() + "' and '" + inputFile2.getResource().getName() + '\'', null, 4, null);
                return ExitCode.COMPILATION_ERROR;
            }
            linkedHashMap.put(inputFile.getOutputPath(), inputFile);
            if (new File(inputFile.getOutputPath()).isDirectory()) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "cannot open output file '" + inputFile.getOutputPath() + "': it is a directory", null, 4, null);
                return ExitCode.COMPILATION_ERROR;
            }
        }
        if (!arguments.getDevMode()) {
            return performDce(arrayList2, arguments, messageCollector);
        }
        String devModeOverwritingStrategy = arguments.getDevModeOverwritingStrategy();
        if (devModeOverwritingStrategy == null) {
            devModeOverwritingStrategy = System.getProperty("kotlin.js.dce.devmode.overwriting.strategy", DevModeOverwritingStrategies.OLDER);
        }
        copyFiles(arrayList2, Intrinsics.areEqual(devModeOverwritingStrategy, DevModeOverwritingStrategies.OLDER));
        return ExitCode.OK;
    }

    private final ExitCode performDce(List<InputFile> files, K2JSDceArguments arguments, final MessageCollector messageCollector) {
        String[] declarationsToKeep = arguments.getDeclarationsToKeep();
        if (declarationsToKeep == null) {
            declarationsToKeep = new String[0];
        }
        DeadCodeEliminationResult deadCodeEliminationResultRun = DeadCodeElimination.INSTANCE.run(files, ArraysKt.toSet(declarationsToKeep), arguments.getPrintReachabilityInfo(), new Function2<DCELogLevel, String, Unit>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce$performDce$logConsumer$1

            /* JADX INFO: compiled from: K2JSDce.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[DCELogLevel.values().length];
                    try {
                        iArr[DCELogLevel.ERROR.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[DCELogLevel.WARN.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[DCELogLevel.INFO.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(DCELogLevel dCELogLevel, String str) {
                invoke2(dCELogLevel, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DCELogLevel level, String message) {
                CompilerMessageSeverity compilerMessageSeverity;
                Intrinsics.checkNotNullParameter(level, "level");
                Intrinsics.checkNotNullParameter(message, "message");
                int i = WhenMappings.$EnumSwitchMapping$0[level.ordinal()];
                if (i == 1) {
                    compilerMessageSeverity = CompilerMessageSeverity.ERROR;
                } else if (i == 2) {
                    compilerMessageSeverity = CompilerMessageSeverity.WARNING;
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    compilerMessageSeverity = CompilerMessageSeverity.LOGGING;
                }
                MessageCollector.report$default(messageCollector, compilerMessageSeverity, message, null, 4, null);
            }
        });
        if (deadCodeEliminationResultRun.getStatus() == DeadCodeEliminationStatus.FAILED) {
            return ExitCode.COMPILATION_ERROR;
        }
        if (arguments.getPrintReachabilityInfo()) {
            final CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.INFO;
            MessageCollector.report$default(messageCollector, compilerMessageSeverity, "", null, 4, null);
            Iterable<Context.Node> reachableNodes = deadCodeEliminationResultRun.getReachableNodes();
            Context context = deadCodeEliminationResultRun.getContext();
            Intrinsics.checkNotNull(context);
            Iterator<Context.Node> it = UtilKt.extractReachableRoots(reachableNodes, context).iterator();
            while (it.hasNext()) {
                PrintTreeKt.printTree(it.next(), (Function1<? super String, Unit>) new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce.performDce.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str) {
                        invoke2(str);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(String it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        MessageCollector.report$default(messageCollector, compilerMessageSeverity, it2, null, 4, null);
                    }
                }, false, true);
            }
        }
        return ExitCode.OK;
    }

    private final void copyFiles(List<InputFile> files, boolean overwriteOnlyOlderFiles) {
        for (InputFile inputFile : files) {
            copyResource(inputFile.getResource(), new File(inputFile.getOutputPath()), overwriteOnlyOlderFiles);
            InputResource sourceMapResource = inputFile.getSourceMapResource();
            if (sourceMapResource != null) {
                File file = new File(inputFile.getOutputPath() + ".map");
                File file2 = new File(sourceMapResource.getName());
                if (!file2.exists() || !mapSourcePaths(file2, file)) {
                    copyResource(sourceMapResource, file, overwriteOnlyOlderFiles);
                }
            }
        }
    }

    private final void copyResource(InputResource resource, File targetFile, boolean overwriteOnlyOlderFiles) {
        if (overwriteOnlyOlderFiles && targetFile.exists() && resource.getLastModified().invoke().longValue() < targetFile.lastModified()) {
            return;
        }
        targetFile.getParentFile().mkdirs();
        InputStream inputStreamInvoke = resource.getReader().invoke();
        try {
            InputStream inputStream = inputStreamInvoke;
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            try {
                ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                CloseableKt.closeFinally(fileOutputStream, null);
                CloseableKt.closeFinally(inputStreamInvoke, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(inputStreamInvoke, th3);
                throw th4;
            }
        }
    }

    private final boolean mapSourcePaths(final File inputFile, final File targetFile) throws IOException {
        boolean zMapSources;
        StringWriter stringWriter = new StringWriter();
        File parentFile = targetFile.getParentFile();
        Intrinsics.checkNotNullExpressionValue(parentFile, "getParentFile(...)");
        final RelativePathCalculator relativePathCalculator = new RelativePathCalculator(parentFile);
        try {
            zMapSources = SourceMap.INSTANCE.mapSources(FilesKt.readText$default(inputFile, null, 1, null), stringWriter, new Function1<String, String>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce$mapSourcePaths$sourceMapChanged$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String strCalculateRelativePathTo = relativePathCalculator.calculateRelativePathTo(new File(inputFile.getParentFile(), it));
                    return (strCalculateRelativePathTo == null || !new File(targetFile.getParentFile(), strCalculateRelativePathTo).exists()) ? it : strCalculateRelativePathTo;
                }
            });
        } catch (SourceMapSourceReplacementException unused) {
            zMapSources = false;
        }
        if (!zMapSources) {
            return false;
        }
        targetFile.getParentFile().mkdirs();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8");
        try {
            outputStreamWriter.write(stringWriter.toString());
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(outputStreamWriter, null);
            return true;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(outputStreamWriter, th);
                throw th2;
            }
        }
    }

    private final List<InputFile> collectInputFiles(File baseDir, String fileName, MessageCollector messageCollector) {
        File file = new File(fileName);
        if (file.isDirectory()) {
            return collectInputFilesFromDirectory(baseDir, fileName);
        }
        if (file.isFile()) {
            if (StringsKt.endsWith$default(fileName, ".js", false, 2, (Object) null)) {
                return CollectionsKt.listOf(singleInputFile(baseDir, fileName));
            }
            if (StringsKt.endsWith$default(fileName, Constants.ZIP_EXTENSION, false, 2, (Object) null) || StringsKt.endsWith$default(fileName, ".jar", false, 2, (Object) null)) {
                return collectInputFilesFromZip(baseDir, fileName);
            }
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.WARNING, "invalid file name '" + file.getAbsolutePath() + "'; must end either with '.js', '.zip' or '.jar'", null, 4, null);
            return CollectionsKt.emptyList();
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "source file or directory not found: " + fileName, null, 4, null);
        return CollectionsKt.emptyList();
    }

    private final InputFile singleInputFile(File baseDir, String path) {
        String moduleNameFromPath = getModuleNameFromPath(path);
        String str = path + ".map";
        if (!new File(str).exists()) {
            str = null;
        }
        InputResource inputResourceFile = InputResource.INSTANCE.file(path);
        InputResource inputResourceFile2 = str != null ? InputResource.INSTANCE.file(str) : null;
        String absolutePath = new File(baseDir, moduleNameFromPath + ".js").getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        return new InputFile(inputResourceFile, inputResourceFile2, absolutePath, moduleNameFromPath);
    }

    private final List<InputFile> collectInputFilesFromZip(final File baseDir, final String path) {
        ZipFile zipFile = new ZipFile(path);
        try {
            final ZipFile zipFile2 = zipFile;
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile2.entries();
            Intrinsics.checkNotNullExpressionValue(enumerationEntries, "entries(...)");
            List<InputFile> list = SequencesKt.toList(SequencesKt.map(SequencesKt.distinctBy(SequencesKt.filter(SequencesKt.filter(SequencesKt.filter(SequencesKt.asSequence(CollectionsKt.iterator(enumerationEntries)), new Function1<ZipEntry, Boolean>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce$collectInputFilesFromZip$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ZipEntry zipEntry) {
                    return Boolean.valueOf(!zipEntry.isDirectory());
                }
            }), new Function1<ZipEntry, Boolean>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce$collectInputFilesFromZip$1$2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ZipEntry zipEntry) {
                    String name2 = zipEntry.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    return Boolean.valueOf(StringsKt.endsWith$default(name2, ".js", false, 2, (Object) null));
                }
            }), new Function1<ZipEntry, Boolean>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce$collectInputFilesFromZip$1$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ZipEntry zipEntry) {
                    ZipFile zipFile3 = zipFile2;
                    K2JSDce k2JSDce = this;
                    String name2 = zipEntry.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    return Boolean.valueOf(zipFile3.getEntry(k2JSDce.metaJs(name2)) != null);
                }
            }), new Function1<ZipEntry, String>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce$collectInputFilesFromZip$1$4
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(ZipEntry zipEntry) {
                    return zipEntry.getName();
                }
            }), new Function1<ZipEntry, InputFile>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce$collectInputFilesFromZip$1$5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final InputFile invoke(ZipEntry zipEntry) {
                    K2JSDce k2JSDce = this.this$0;
                    String name2 = zipEntry.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    String moduleNameFromPath = k2JSDce.getModuleNameFromPath(name2);
                    String str = zipEntry.getName() + ".map";
                    InputResource inputResourceZipFile = null;
                    if (zipFile2.getEntry(str) == null) {
                        str = null;
                    }
                    InputResource.Companion companion = InputResource.INSTANCE;
                    String str2 = path;
                    String name3 = zipEntry.getName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    InputResource inputResourceZipFile2 = companion.zipFile(str2, name3);
                    if (str != null) {
                        inputResourceZipFile = InputResource.INSTANCE.zipFile(path, str);
                    }
                    String absolutePath = new File(baseDir, moduleNameFromPath + ".js").getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                    return new InputFile(inputResourceZipFile2, inputResourceZipFile, absolutePath, moduleNameFromPath);
                }
            }));
            CloseableKt.closeFinally(zipFile, null);
            return list;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(zipFile, th);
                throw th2;
            }
        }
    }

    private final List<InputFile> collectInputFilesFromDirectory(final File baseDir, String path) {
        return SequencesKt.toList(SequencesKt.map(SequencesKt.filter(SequencesKt.filter(SequencesKt.filter(FilesKt.walkTopDown(new File(path)), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce.collectInputFilesFromDirectory.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!it.isDirectory());
            }
        }), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce.collectInputFilesFromDirectory.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String name2 = it.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return Boolean.valueOf(StringsKt.endsWith$default(name2, ".js", false, 2, (Object) null));
            }
        }), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce.collectInputFilesFromDirectory.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                K2JSDce k2JSDce = K2JSDce.this;
                String path2 = it.getPath();
                Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
                return Boolean.valueOf(new File(k2JSDce.metaJs(path2)).exists());
            }
        }), new Function1<File, InputFile>() { // from class: org.jetbrains.kotlin.cli.js.dce.K2JSDce.collectInputFilesFromDirectory.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final InputFile invoke(File entry) {
                Intrinsics.checkNotNullParameter(entry, "entry");
                K2JSDce k2JSDce = K2JSDce.this;
                String name2 = entry.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                String moduleNameFromPath = k2JSDce.getModuleNameFromPath(name2);
                String str = entry.getPath() + ".map";
                if (!new File(str).exists()) {
                    str = null;
                }
                InputResource.Companion companion = InputResource.INSTANCE;
                String path2 = entry.getPath();
                Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
                InputResource inputResourceFile = companion.file(path2);
                InputResource inputResourceFile2 = str != null ? InputResource.INSTANCE.file(str) : null;
                String absolutePath = new File(baseDir, moduleNameFromPath + ".js").getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                return new InputFile(inputResourceFile, inputResourceFile2, absolutePath, moduleNameFromPath);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String metaJs(String str) {
        return StringsKt.removeSuffix(str, (CharSequence) ".js") + KotlinJavascriptMetadataUtils.META_JS_SUFFIX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getModuleNameFromPath(String path) {
        String str = path;
        int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) str, '.', 0, false, 6, (Object) null);
        int iMax = Math.max(StringsKt.lastIndexOf$default((CharSequence) str, '/', 0, false, 6, (Object) null), StringsKt.lastIndexOf$default((CharSequence) str, '\\', 0, false, 6, (Object) null)) + 1;
        if (iLastIndexOf$default < 0) {
            iLastIndexOf$default = path.length();
        }
        String strSubstring = path.substring(iMax, iLastIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    @Override // org.jetbrains.kotlin.cli.common.CLITool
    public String executableScriptFileName() {
        return "kotlin-dce-js";
    }

    /* JADX INFO: compiled from: K2JSDce.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/dce/K2JSDce$Companion;", "", "()V", "main", "", "args", "", "", "([Ljava/lang/String;)V", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void main(String[] args) {
            Intrinsics.checkNotNullParameter(args, "args");
            CLITool.INSTANCE.doMain(new K2JSDce(), args);
        }
    }
}
