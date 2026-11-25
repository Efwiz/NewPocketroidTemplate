package kotlin.script.experimental.jvm.compat;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.dependencies.ScriptContents;
import kotlin.script.dependencies.ScriptDependenciesResolver;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.dependencies.ScriptReport;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: diagnosticsUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u001a\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\t\u001a\u0012\u0010\n\u001a\u0004\u0018\u00010\t2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u001a\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r*\b\u0012\u0004\u0012\u00020\u00100\u000f\u001a\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\r*\b\u0012\u0004\u0012\u00020\u000e0\u000f¨\u0006\u0012"}, d2 = {"mapLegacyDiagnosticSeverity", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "severity", "Lkotlin/script/dependencies/ScriptDependenciesResolver$ReportSeverity;", "Lkotlin/script/experimental/dependencies/ScriptReport$Severity;", "mapLegacyScriptPosition", "Lkotlin/script/experimental/api/SourceCode$Location;", "pos", "Lkotlin/script/dependencies/ScriptContents$Position;", "Lkotlin/script/experimental/dependencies/ScriptReport$Position;", "mapToLegacyScriptReportPosition", "mapToLegacyScriptReportSeverity", "mapToDiagnostics", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "", "Lkotlin/script/experimental/dependencies/ScriptReport;", "mapToLegacyReports", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticsUtilKt {

    /* JADX INFO: compiled from: diagnosticsUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[ScriptDependenciesResolver.ReportSeverity.values().length];
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.FATAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ScriptDependenciesResolver.ReportSeverity.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ScriptReport.Severity.values().length];
            try {
                iArr2[ScriptReport.Severity.FATAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ScriptReport.Severity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[ScriptReport.Severity.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[ScriptReport.Severity.INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[ScriptReport.Severity.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[ScriptDiagnostic.Severity.values().length];
            try {
                iArr3[ScriptDiagnostic.Severity.FATAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr3[ScriptDiagnostic.Severity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr3[ScriptDiagnostic.Severity.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr3[ScriptDiagnostic.Severity.INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr3[ScriptDiagnostic.Severity.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError unused15) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    public static final ScriptDiagnostic.Severity mapLegacyDiagnosticSeverity(ScriptDependenciesResolver.ReportSeverity severity) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
        if (i == 1) {
            return ScriptDiagnostic.Severity.FATAL;
        }
        if (i == 2) {
            return ScriptDiagnostic.Severity.ERROR;
        }
        if (i == 3) {
            return ScriptDiagnostic.Severity.WARNING;
        }
        if (i == 4) {
            return ScriptDiagnostic.Severity.INFO;
        }
        if (i == 5) {
            return ScriptDiagnostic.Severity.DEBUG;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ScriptDiagnostic.Severity mapLegacyDiagnosticSeverity(ScriptReport.Severity severity) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        int i = WhenMappings.$EnumSwitchMapping$1[severity.ordinal()];
        if (i == 1) {
            return ScriptDiagnostic.Severity.FATAL;
        }
        if (i == 2) {
            return ScriptDiagnostic.Severity.ERROR;
        }
        if (i == 3) {
            return ScriptDiagnostic.Severity.WARNING;
        }
        if (i == 4) {
            return ScriptDiagnostic.Severity.INFO;
        }
        if (i == 5) {
            return ScriptDiagnostic.Severity.DEBUG;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ScriptReport.Severity mapToLegacyScriptReportSeverity(ScriptDiagnostic.Severity severity) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        int i = WhenMappings.$EnumSwitchMapping$2[severity.ordinal()];
        if (i == 1) {
            return ScriptReport.Severity.FATAL;
        }
        if (i == 2) {
            return ScriptReport.Severity.ERROR;
        }
        if (i == 3) {
            return ScriptReport.Severity.WARNING;
        }
        if (i == 4) {
            return ScriptReport.Severity.INFO;
        }
        if (i == 5) {
            return ScriptReport.Severity.DEBUG;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final SourceCode.Location mapLegacyScriptPosition(ScriptContents.Position position) {
        if (position != null) {
            return new SourceCode.Location(new SourceCode.Position(position.getLine(), position.getCol(), null, 4, null), null, 2, null);
        }
        return null;
    }

    public static final SourceCode.Location mapLegacyScriptPosition(ScriptReport.Position position) {
        SourceCode.Position position2 = null;
        if (position == null) {
            return null;
        }
        if (position.getEndLine() != null && position.getEndColumn() != null) {
            Integer endLine = position.getEndLine();
            Intrinsics.checkNotNull(endLine);
            int iIntValue = endLine.intValue();
            Integer endColumn = position.getEndColumn();
            Intrinsics.checkNotNull(endColumn);
            position2 = new SourceCode.Position(iIntValue, endColumn.intValue(), null, 4, null);
        }
        return new SourceCode.Location(new SourceCode.Position(position.getStartLine(), position.getStartColumn(), null, 4, null), position2);
    }

    public static final ScriptReport.Position mapToLegacyScriptReportPosition(SourceCode.Location location) {
        if (location == null) {
            return null;
        }
        int line = location.getStart().getLine();
        int col = location.getStart().getCol();
        SourceCode.Position end = location.getEnd();
        Integer numValueOf = end != null ? Integer.valueOf(end.getLine()) : null;
        SourceCode.Position end2 = location.getEnd();
        return new ScriptReport.Position(line, col, numValueOf, end2 != null ? Integer.valueOf(end2.getCol()) : null);
    }

    public static final List<ScriptDiagnostic> mapToDiagnostics(Iterable<ScriptReport> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (ScriptReport scriptReport : iterable) {
            arrayList.add(new ScriptDiagnostic(-1, scriptReport.getMessage(), mapLegacyDiagnosticSeverity(scriptReport.getSeverity()), null, mapLegacyScriptPosition(scriptReport.getPosition()), null, 32, null));
        }
        return arrayList;
    }

    public static final List<ScriptReport> mapToLegacyReports(Iterable<ScriptDiagnostic> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (ScriptDiagnostic scriptDiagnostic : iterable) {
            String message = scriptDiagnostic.getMessage();
            ScriptDiagnostic.Severity severity = scriptDiagnostic.getSeverity();
            SourceCode.Location location = scriptDiagnostic.getLocation();
            Throwable exception = scriptDiagnostic.getException();
            if (exception != null) {
                message = message + " (" + exception + ')';
            }
            arrayList.add(new ScriptReport(message, mapToLegacyScriptReportSeverity(severity), mapToLegacyScriptReportPosition(location)));
        }
        return arrayList;
    }
}
