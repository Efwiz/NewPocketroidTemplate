package org.jetbrains.kotlin.daemon.common;

import com.google.firebase.database.core.ServerValues;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.bouncycastle.cms.CMSAttributeTableGenerator;

/* JADX INFO: compiled from: ClientUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a\u001c\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\n\u001a\u001c\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u0001\u001a(\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00012\b\b\u0002\u0010\u0013\u001a\u00020\u0001\u001a-\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0012\u001a\u00020\u00032\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00190\u0017H\u0082\b\u001a\\\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\n2\u001a\b\u0002\u0010!\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\"0\u00172\u001a\b\u0002\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00190\u0017\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\u00020\u0003X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"LOG_PREFIX_ASSUMING_OTHER_DAEMONS_HAVE", "", "MAX_PORT_NUMBER", "", "getMAX_PORT_NUMBER", "()I", "ORPHANED_RUN_FILE_AGE_THRESHOLD_MS", "", "validFlagFileKeywordChars", "makeAutodeletingFlagFile", "Ljava/io/File;", "keyword", "baseDir", "makePortFromRunFilenameExtractor", "Lkotlin/Function1;", CMSAttributeTableGenerator.DIGEST, "makeRunFilenameString", ServerValues.NAME_OP_TIMESTAMP, "port", "escapeSequence", "tryConnectToDaemon", "Lorg/jetbrains/kotlin/daemon/common/CompileService;", "report", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/daemon/common/DaemonReportCategory;", "", "walkDaemons", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/daemon/common/DaemonWithMetadata;", "registryDir", "compilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "fileToCompareTimestamp", "filter", "", "daemon-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClientUtilsKt {
    public static final String LOG_PREFIX_ASSUMING_OTHER_DAEMONS_HAVE = "Assuming other daemons have";
    private static final int MAX_PORT_NUMBER = 65535;
    private static final long ORPHANED_RUN_FILE_AGE_THRESHOLD_MS = 1000000;
    private static final String validFlagFileKeywordChars = "abcdefghijklmnopqrstuvwxyz0123456789-_";

    public static final int getMAX_PORT_NUMBER() {
        return MAX_PORT_NUMBER;
    }

    public static /* synthetic */ String makeRunFilenameString$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 8) != 0) {
            str4 = "";
        }
        return makeRunFilenameString(str, str2, str3, str4);
    }

    public static final String makeRunFilenameString(String timestamp, String digest, String port, String escapeSequence) {
        Intrinsics.checkNotNullParameter(timestamp, "timestamp");
        Intrinsics.checkNotNullParameter(digest, "digest");
        Intrinsics.checkNotNullParameter(port, "port");
        Intrinsics.checkNotNullParameter(escapeSequence, "escapeSequence");
        return "kotlin-daemon" + escapeSequence + '.' + timestamp + escapeSequence + '.' + digest + escapeSequence + '.' + port + escapeSequence + ".run";
    }

    public static final Function1<String, Integer> makePortFromRunFilenameExtractor(String digest) {
        Intrinsics.checkNotNullParameter(digest, "digest");
        final Regex regex = new Regex(makeRunFilenameString("[0-9TZ:\\.\\+-]+", digest, "(\\d+)", "\\"));
        return new Function1<String, Integer>() { // from class: org.jetbrains.kotlin.daemon.common.ClientUtilsKt.makePortFromRunFilenameExtractor.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(String it) {
                MatchGroupCollection groups;
                MatchGroup matchGroup;
                String value;
                Intrinsics.checkNotNullParameter(it, "it");
                MatchResult matchResultFind$default = Regex.find$default(regex, it, 0, 2, null);
                if (matchResultFind$default == null || (groups = matchResultFind$default.getGroups()) == null || (matchGroup = groups.get(1)) == null || (value = matchGroup.getValue()) == null) {
                    return null;
                }
                return Integer.valueOf(Integer.parseInt(value));
            }
        };
    }

    public static /* synthetic */ Sequence walkDaemons$default(File file, CompilerId compilerId, File file2, Function2 function2, Function2 function3, int i, Object obj) {
        if ((i & 8) != 0) {
            function2 = new Function2<File, Integer, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.ClientUtilsKt.walkDaemons.1
                public final Boolean invoke(File file3, int i2) {
                    Intrinsics.checkNotNullParameter(file3, "<anonymous parameter 0>");
                    return true;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Boolean invoke(File file3, Integer num) {
                    return invoke(file3, num.intValue());
                }
            };
        }
        if ((i & 16) != 0) {
            function3 = new Function2<DaemonReportCategory, String, Unit>() { // from class: org.jetbrains.kotlin.daemon.common.ClientUtilsKt.walkDaemons.2
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(DaemonReportCategory daemonReportCategory, String str) {
                    Intrinsics.checkNotNullParameter(daemonReportCategory, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(str, "<anonymous parameter 1>");
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(DaemonReportCategory daemonReportCategory, String str) {
                    invoke2(daemonReportCategory, str);
                    return Unit.INSTANCE;
                }
            };
        }
        return walkDaemons(file, compilerId, file2, function2, function3);
    }

    public static final Sequence<DaemonWithMetadata> walkDaemons(File registryDir, CompilerId compilerId, final File fileToCompareTimestamp, final Function2<? super File, ? super Integer, Boolean> filter, final Function2<? super DaemonReportCategory, ? super String, Unit> report) {
        Intrinsics.checkNotNullParameter(registryDir, "registryDir");
        Intrinsics.checkNotNullParameter(compilerId, "compilerId");
        Intrinsics.checkNotNullParameter(fileToCompareTimestamp, "fileToCompareTimestamp");
        Intrinsics.checkNotNullParameter(filter, "filter");
        Intrinsics.checkNotNullParameter(report, "report");
        final Function1<String, Integer> function1MakePortFromRunFilenameExtractor = makePortFromRunFilenameExtractor(compilerId.digest());
        return SequencesKt.mapNotNull(SequencesKt.filter(SequencesKt.map(FilesKt.walk$default(registryDir, null, 1, null), new Function1<File, Pair<? extends File, ? extends Integer>>() { // from class: org.jetbrains.kotlin.daemon.common.ClientUtilsKt.walkDaemons.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Pair<File, Integer> invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Function1<String, Integer> function1 = function1MakePortFromRunFilenameExtractor;
                String name2 = it.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return new Pair<>(it, function1.invoke(name2));
            }
        }), new Function1<Pair<? extends File, ? extends Integer>, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.ClientUtilsKt.walkDaemons.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(Pair<? extends File, Integer> pair) {
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                File fileComponent1 = pair.component1();
                Integer numComponent2 = pair.component2();
                return Boolean.valueOf(numComponent2 != null && filter.invoke(fileComponent1, numComponent2).booleanValue());
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Pair<? extends File, ? extends Integer> pair) {
                return invoke2((Pair<? extends File, Integer>) pair);
            }
        }), new Function1<Pair<? extends File, ? extends Integer>, DaemonWithMetadata>() { // from class: org.jetbrains.kotlin.daemon.common.ClientUtilsKt.walkDaemons.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ DaemonWithMetadata invoke(Pair<? extends File, ? extends Integer> pair) {
                return invoke2((Pair<? extends File, Integer>) pair);
            }

            /* JADX WARN: Code duplicated, block: B:25:0x00c8  */
            /* JADX WARN: Code duplicated, block: B:27:0x00d5  */
            /* JADX WARN: Code duplicated, block: B:28:0x00fe  */
            /* JADX WARN: Code duplicated, block: B:30:0x012c  */
            /* JADX WARN: Code duplicated, block: B:37:0x014e A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:40:? A[RETURN, SYNTHETIC] */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final DaemonWithMetadata invoke2(Pair<? extends File, Integer> pair) {
                String message;
                CompileService compileService;
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                File fileComponent1 = pair.component1();
                Integer numComponent2 = pair.component2();
                ClientUtilsKt.getMAX_PORT_NUMBER();
                Intrinsics.checkNotNull(numComponent2);
                numComponent2.intValue();
                long jLastModified = fileToCompareTimestamp.lastModified() - fileComponent1.lastModified();
                report.invoke(DaemonReportCategory.DEBUG, "found daemon on port " + numComponent2 + " (" + jLastModified + " ms old), trying to connect");
                int iIntValue = numComponent2.intValue();
                Function2<DaemonReportCategory, String, Unit> function2 = report;
                try {
                    Registry registry = LocateRegistry.getRegistry(LoopbackNetworkInterface.INSTANCE.getLoopbackInetAddressName(), iIntValue, LoopbackNetworkInterface.INSTANCE.getClientLoopbackSocketFactory());
                    Remote remoteLookup = registry != null ? registry.lookup(DaemonParamsKt.COMPILER_SERVICE_RMI_NAME) : null;
                    if (remoteLookup == null) {
                        function2.invoke(DaemonReportCategory.INFO, "daemon not found");
                    } else {
                        if (remoteLookup instanceof CompileService) {
                            compileService = (CompileService) remoteLookup;
                        } else {
                            function2.invoke(DaemonReportCategory.INFO, "Unable to cast compiler service, actual class received: " + remoteLookup.getClass().getName());
                        }
                        if (compileService == null) {
                            if (jLastModified - 1000000 <= 0) {
                                report.invoke(DaemonReportCategory.DEBUG, "found fresh run file '" + fileComponent1.getAbsolutePath() + "' (" + jLastModified + " ms old), but no daemon, ignoring it");
                            } else {
                                report.invoke(DaemonReportCategory.DEBUG, "found seemingly orphaned run file '" + fileComponent1.getAbsolutePath() + "' (" + jLastModified + " ms old), deleting it");
                                if (!fileComponent1.delete()) {
                                    report.invoke(DaemonReportCategory.INFO, "WARNING: unable to delete seemingly orphaned file '" + fileComponent1.getAbsolutePath() + "', cleanup recommended");
                                }
                            }
                        }
                        if (compileService == null) {
                            return null;
                        }
                        try {
                            return new DaemonWithMetadata(compileService, fileComponent1, compileService.getDaemonJVMOptions().get());
                        } catch (Exception e) {
                            report.invoke(DaemonReportCategory.INFO, "ERROR: unable to retrieve daemon JVM options, assuming daemon is dead: " + e.getMessage());
                            return null;
                        }
                    }
                } catch (Throwable th) {
                    DaemonReportCategory daemonReportCategory = DaemonReportCategory.INFO;
                    StringBuilder sb = new StringBuilder("cannot connect to registry: ");
                    Throwable cause = th.getCause();
                    if ((cause == null || (message = cause.getMessage()) == null) && (message = th.getMessage()) == null) {
                        message = "unknown error";
                    }
                    function2.invoke(daemonReportCategory, sb.append(message).toString());
                }
                compileService = null;
                if (compileService == null) {
                    if (jLastModified - 1000000 <= 0) {
                        report.invoke(DaemonReportCategory.DEBUG, "found fresh run file '" + fileComponent1.getAbsolutePath() + "' (" + jLastModified + " ms old), but no daemon, ignoring it");
                    } else {
                        report.invoke(DaemonReportCategory.DEBUG, "found seemingly orphaned run file '" + fileComponent1.getAbsolutePath() + "' (" + jLastModified + " ms old), deleting it");
                        if (!fileComponent1.delete()) {
                            report.invoke(DaemonReportCategory.INFO, "WARNING: unable to delete seemingly orphaned file '" + fileComponent1.getAbsolutePath() + "', cleanup recommended");
                        }
                    }
                }
                if (compileService == null) {
                    return new DaemonWithMetadata(compileService, fileComponent1, compileService.getDaemonJVMOptions().get());
                }
                return null;
            }
        });
    }

    private static final CompileService tryConnectToDaemon(int i, Function2<? super DaemonReportCategory, ? super String, Unit> function2) {
        String message;
        try {
            Registry registry = LocateRegistry.getRegistry(LoopbackNetworkInterface.INSTANCE.getLoopbackInetAddressName(), i, LoopbackNetworkInterface.INSTANCE.getClientLoopbackSocketFactory());
            Remote remoteLookup = registry != null ? registry.lookup(DaemonParamsKt.COMPILER_SERVICE_RMI_NAME) : null;
            if (remoteLookup == null) {
                function2.invoke(DaemonReportCategory.INFO, "daemon not found");
            } else {
                if (remoteLookup instanceof CompileService) {
                    return (CompileService) remoteLookup;
                }
                function2.invoke(DaemonReportCategory.INFO, "Unable to cast compiler service, actual class received: " + remoteLookup.getClass().getName());
            }
        } catch (Throwable th) {
            DaemonReportCategory daemonReportCategory = DaemonReportCategory.INFO;
            StringBuilder sb = new StringBuilder("cannot connect to registry: ");
            Throwable cause = th.getCause();
            if ((cause == null || (message = cause.getMessage()) == null) && (message = th.getMessage()) == null) {
                message = "unknown error";
            }
            function2.invoke(daemonReportCategory, sb.append(message).toString());
        }
        return null;
    }

    public static /* synthetic */ File makeAutodeletingFlagFile$default(String str, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "compiler-client";
        }
        if ((i & 2) != 0) {
            file = null;
        }
        return makeAutodeletingFlagFile(str, file);
    }

    public static final File makeAutodeletingFlagFile(String keyword, File file) throws IOException {
        Intrinsics.checkNotNullParameter(keyword, "keyword");
        StringBuilder sb = new StringBuilder("kotlin-");
        String str = keyword;
        StringBuilder sb2 = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (StringsKt.contains$default((CharSequence) validFlagFileKeywordChars, Character.toLowerCase(cCharAt), false, 2, (Object) null)) {
                sb2.append(cCharAt);
            }
        }
        String string = sb2.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String string2 = sb.append(string).append('-').toString();
        File file2 = (file == null || !file.isDirectory()) ? Files.createTempFile(string2, "-is-running", new FileAttribute[0]).toFile() : Files.createTempFile(file.toPath(), string2, "-is-running", new FileAttribute[0]).toFile();
        file2.deleteOnExit();
        Intrinsics.checkNotNull(file2);
        return file2;
    }
}
