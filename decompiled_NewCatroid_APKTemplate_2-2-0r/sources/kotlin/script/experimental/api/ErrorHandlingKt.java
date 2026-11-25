package kotlin.script.experimental.api;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: errorHandling.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0005\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\r\u001aB\u0010\u000e\u001a\u00020\u0004*\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u001a4\u0010\u000e\u001a\u00020\u0004*\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u001a,\u0010\u0015\u001a\u00020\u0004*\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u001a\u001e\u0010\u0015\u001a\u00020\u0004*\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a+\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0017\"\u0004\b\u0000\u0010\u0018*\u0002H\u00182\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\r¢\u0006\u0002\u0010\u0019\u001a.\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001c0\r0\u001b\"\u0004\b\u0000\u0010\u001c*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001c0\u001d0\u001b0\u001d\u001aN\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\r0\u001b\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u001c0\u001d2\u001e\u0010\u001f\u001a\u001a\u0012\u0004\u0012\u0002H\u001c\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180!0\u001b0 H\u0086\bø\u0001\u0000\u001a\n\u0010\"\u001a\u00020#*\u00020\u0004\u001aJ\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\r0\u001b\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u001c0\u001d2\u001a\u0010\u001f\u001a\u0016\u0012\u0004\u0012\u0002H\u001c\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00180\u001b0 H\u0086\bø\u0001\u0000\u001aH\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\r0\u001b\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u001c0\u001d2\u0018\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u0002H\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u001b0 H\u0086\bø\u0001\u0000\u001an\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0\r0\u001b\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010(\"\u0004\b\u0002\u0010'*\b\u0012\u0004\u0012\u0002H\u001c0\u001d2\u0018\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u0002H\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u0002H(0\u001b0 2\u001e\u0010)\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0+\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u00020,0*H\u0086\bø\u0001\u0000\u001a<\u0010-\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001b\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180\u001b2\u0018\u0010\u001f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u001b\u0012\u0004\u0012\u00020,0 H\u0086\bø\u0001\u0000\u001aB\u0010.\u001a\b\u0012\u0004\u0012\u0002H'0\u001b\"\u0004\b\u0000\u0010(\"\u0004\b\u0001\u0010'*\b\u0012\u0004\u0012\u0002H(0\u001b2\u0018\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u0002H(\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0\u001b0 H\u0086\bø\u0001\u0000\u001a-\u0010/\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001b\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u00020\u00040\r2\f\u00100\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0086\u0002\u001a5\u00101\u001a\u0002H\u0018\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180\u001b2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002020 H\u0086\bø\u0001\u0000¢\u0006\u0002\u00103\u001a\u001d\u00104\u001a\u0004\u0018\u0001H\u0018\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180\u001b¢\u0006\u0002\u00105\u001a\u001b\u00106\u001a\u0002H\u0018\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180\u001b¢\u0006\u0002\u00105\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00067"}, d2 = {"makeFailureResult", "Lkotlin/script/experimental/api/ResultWithDiagnostics$Failure;", "reports", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "([Lkotlin/script/experimental/api/ScriptDiagnostic;)Lkotlin/script/experimental/api/ResultWithDiagnostics$Failure;", CrashHianalyticsData.MESSAGE, "", "path", FirebaseAnalytics.Param.LOCATION, "Lkotlin/script/experimental/api/SourceCode$Location;", "locationWithId", "Lkotlin/script/experimental/api/SourceCode$LocationWithId;", "", "asDiagnostics", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "customMessage", "severity", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "asErrorDiagnostics", "asSuccess", "Lkotlin/script/experimental/api/ResultWithDiagnostics$Success;", "R", "(Ljava/lang/Object;Ljava/util/List;)Lkotlin/script/experimental/api/ResultWithDiagnostics$Success;", "asSuccessIfAny", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", ExifInterface.GPS_DIRECTION_TRUE, "", "flatMapSuccess", "body", "Lkotlin/Function1;", "", "isError", "", "mapNotNullSuccess", "mapSuccess", "mapSuccessImpl", "R2", "R1", "updateResults", "Lkotlin/Function2;", "", "", "onFailure", "onSuccess", "plus", "result", "valueOr", "", "(Lkotlin/script/experimental/api/ResultWithDiagnostics;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "valueOrNull", "(Lkotlin/script/experimental/api/ResultWithDiagnostics;)Ljava/lang/Object;", "valueOrThrow", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ErrorHandlingKt {
    public static final boolean isError(ScriptDiagnostic scriptDiagnostic) {
        Intrinsics.checkNotNullParameter(scriptDiagnostic, "<this>");
        return (scriptDiagnostic.getSeverity() == ScriptDiagnostic.Severity.ERROR || scriptDiagnostic.getSeverity() == ScriptDiagnostic.Severity.FATAL) && (scriptDiagnostic.getCode() == -2 || scriptDiagnostic.getCode() == -1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <R1, R2> ResultWithDiagnostics<R2> onSuccess(ResultWithDiagnostics<? extends R1> resultWithDiagnostics, Function1<? super R1, ? extends ResultWithDiagnostics<? extends R2>> body) {
        Intrinsics.checkNotNullParameter(resultWithDiagnostics, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
            return plus(resultWithDiagnostics.getReports(), body.invoke((Object) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue()));
        }
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Failure) {
            return resultWithDiagnostics;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <T, R1, R2> ResultWithDiagnostics<List<R2>> mapSuccessImpl(Iterable<? extends T> iterable, Function1<? super T, ? extends ResultWithDiagnostics<? extends R1>> body, Function2<? super List<R2>, ? super R1, Unit> updateResults) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(updateResults, "updateResults");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            ResultWithDiagnostics<? extends R1> resultWithDiagnosticsInvoke = body.invoke(it.next());
            arrayList.addAll(resultWithDiagnosticsInvoke.getReports());
            if (resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Success) {
                updateResults.invoke(arrayList2, (Object) ((ResultWithDiagnostics.Success) resultWithDiagnosticsInvoke).getValue());
            } else {
                return new ResultWithDiagnostics.Failure(arrayList);
            }
        }
        return asSuccess(arrayList2, arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <R> ResultWithDiagnostics<R> onFailure(ResultWithDiagnostics<? extends R> resultWithDiagnostics, Function1<? super ResultWithDiagnostics<? extends R>, Unit> body) {
        Intrinsics.checkNotNullParameter(resultWithDiagnostics, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Failure) {
            body.invoke(resultWithDiagnostics);
        }
        return resultWithDiagnostics;
    }

    public static final <R> ResultWithDiagnostics<R> plus(List<ScriptDiagnostic> list, ResultWithDiagnostics<? extends R> result) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(result, "result");
        if (result instanceof ResultWithDiagnostics.Success) {
            return new ResultWithDiagnostics.Success(((ResultWithDiagnostics.Success) result).getValue(), CollectionsKt.plus((Collection) list, (Iterable) result.getReports()));
        }
        if (result instanceof ResultWithDiagnostics.Failure) {
            return new ResultWithDiagnostics.Failure((List<ScriptDiagnostic>) CollectionsKt.plus((Collection) list, (Iterable) result.getReports()));
        }
        throw new NoWhenBranchMatchedException();
    }

    public static /* synthetic */ ResultWithDiagnostics.Success asSuccess$default(Object obj, List list, int i, Object obj2) {
        if ((i & 1) != 0) {
            list = CollectionsKt.emptyList();
        }
        return asSuccess(obj, list);
    }

    public static final <R> ResultWithDiagnostics.Success<R> asSuccess(R r, List<ScriptDiagnostic> reports) {
        Intrinsics.checkNotNullParameter(reports, "reports");
        return new ResultWithDiagnostics.Success<>(r, reports);
    }

    public static final ResultWithDiagnostics.Failure makeFailureResult(List<ScriptDiagnostic> reports) {
        Intrinsics.checkNotNullParameter(reports, "reports");
        return new ResultWithDiagnostics.Failure(reports);
    }

    public static final ResultWithDiagnostics.Failure makeFailureResult(ScriptDiagnostic... reports) {
        Intrinsics.checkNotNullParameter(reports, "reports");
        return new ResultWithDiagnostics.Failure((List<ScriptDiagnostic>) ArraysKt.asList(reports));
    }

    public static /* synthetic */ ResultWithDiagnostics.Failure makeFailureResult$default(String str, String str2, SourceCode.Location location, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            location = null;
        }
        return makeFailureResult(str, str2, location);
    }

    public static final ResultWithDiagnostics.Failure makeFailureResult(String message, String str, SourceCode.Location location) {
        Intrinsics.checkNotNullParameter(message, "message");
        return new ResultWithDiagnostics.Failure(asErrorDiagnostics(message, -1, str, location));
    }

    public static final ResultWithDiagnostics.Failure makeFailureResult(String message, SourceCode.LocationWithId locationWithId) {
        Intrinsics.checkNotNullParameter(message, "message");
        return new ResultWithDiagnostics.Failure(asErrorDiagnostics(message, -1, locationWithId));
    }

    public static /* synthetic */ ScriptDiagnostic asDiagnostics$default(Throwable th, int i, String str, String str2, SourceCode.Location location, ScriptDiagnostic.Severity severity, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        String str3 = (i2 & 2) != 0 ? null : str;
        String str4 = (i2 & 4) != 0 ? null : str2;
        SourceCode.Location location2 = (i2 & 8) == 0 ? location : null;
        if ((i2 & 16) != 0) {
            severity = ScriptDiagnostic.Severity.ERROR;
        }
        return asDiagnostics(th, i, str3, str4, location2, severity);
    }

    public static final ScriptDiagnostic asDiagnostics(Throwable th, int i, String str, String str2, SourceCode.Location location, ScriptDiagnostic.Severity severity) {
        Intrinsics.checkNotNullParameter(th, "<this>");
        Intrinsics.checkNotNullParameter(severity, "severity");
        if (str == null && (str = th.getMessage()) == null) {
            str = String.valueOf(th);
        }
        return new ScriptDiagnostic(i, str, severity, str2, location, th);
    }

    public static /* synthetic */ ScriptDiagnostic asDiagnostics$default(Throwable th, int i, String str, SourceCode.LocationWithId locationWithId, ScriptDiagnostic.Severity severity, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        if ((i2 & 2) != 0) {
            str = null;
        }
        if ((i2 & 8) != 0) {
            severity = ScriptDiagnostic.Severity.ERROR;
        }
        return asDiagnostics(th, i, str, locationWithId, severity);
    }

    public static final ScriptDiagnostic asDiagnostics(Throwable th, int i, String str, SourceCode.LocationWithId locationWithId, ScriptDiagnostic.Severity severity) {
        Intrinsics.checkNotNullParameter(th, "<this>");
        Intrinsics.checkNotNullParameter(severity, "severity");
        if (str == null && (str = th.getMessage()) == null) {
            str = String.valueOf(th);
        }
        return new ScriptDiagnostic(i, str, severity, locationWithId, th);
    }

    public static /* synthetic */ ScriptDiagnostic asErrorDiagnostics$default(String str, int i, String str2, SourceCode.Location location, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        if ((i2 & 2) != 0) {
            str2 = null;
        }
        if ((i2 & 4) != 0) {
            location = null;
        }
        return asErrorDiagnostics(str, i, str2, location);
    }

    public static final ScriptDiagnostic asErrorDiagnostics(String str, int i, String str2, SourceCode.Location location) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return new ScriptDiagnostic(i, str, ScriptDiagnostic.Severity.ERROR, str2, location, null, 32, null);
    }

    public static /* synthetic */ ScriptDiagnostic asErrorDiagnostics$default(String str, int i, SourceCode.LocationWithId locationWithId, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        return asErrorDiagnostics(str, i, locationWithId);
    }

    public static final ScriptDiagnostic asErrorDiagnostics(String str, int i, SourceCode.LocationWithId locationWithId) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return new ScriptDiagnostic(i, str, ScriptDiagnostic.Severity.ERROR, locationWithId, null, 16, null);
    }

    public static final <R> R valueOrNull(ResultWithDiagnostics<? extends R> resultWithDiagnostics) {
        Intrinsics.checkNotNullParameter(resultWithDiagnostics, "<this>");
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
            return (R) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue();
        }
        return null;
    }

    public static final <R> R valueOr(ResultWithDiagnostics<? extends R> resultWithDiagnostics, Function1 body) {
        Intrinsics.checkNotNullParameter(resultWithDiagnostics, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
            return (R) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue();
        }
        if (!(resultWithDiagnostics instanceof ResultWithDiagnostics.Failure)) {
            throw new NoWhenBranchMatchedException();
        }
        body.invoke(resultWithDiagnostics);
        throw null;
    }

    public static final <T> ResultWithDiagnostics<List<T>> asSuccessIfAny(Iterable<? extends ResultWithDiagnostics<? extends Iterable<? extends T>>> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        IterableResultsCollector iterableResultsCollector = new IterableResultsCollector();
        Iterator<? extends ResultWithDiagnostics<? extends Iterable<? extends T>>> it = iterable.iterator();
        while (it.hasNext()) {
            iterableResultsCollector.add(it.next());
        }
        return iterableResultsCollector.getResult();
    }

    public static final <T, R> ResultWithDiagnostics<List<R>> mapSuccess(Iterable<? extends T> iterable, Function1<? super T, ? extends ResultWithDiagnostics<? extends R>> body) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            ResultWithDiagnostics<? extends R> resultWithDiagnosticsInvoke = body.invoke(it.next());
            arrayList.addAll(resultWithDiagnosticsInvoke.getReports());
            if (resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Success) {
                arrayList2.add(((ResultWithDiagnostics.Success) resultWithDiagnosticsInvoke).getValue());
            } else {
                return new ResultWithDiagnostics.Failure(arrayList);
            }
        }
        return asSuccess(arrayList2, arrayList);
    }

    public static final <T, R> ResultWithDiagnostics<List<R>> mapNotNullSuccess(Iterable<? extends T> iterable, Function1<? super T, ? extends ResultWithDiagnostics<? extends R>> body) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            ResultWithDiagnostics<? extends R> resultWithDiagnosticsInvoke = body.invoke(it.next());
            arrayList.addAll(resultWithDiagnosticsInvoke.getReports());
            if (resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Success) {
                Object value = ((ResultWithDiagnostics.Success) resultWithDiagnosticsInvoke).getValue();
                ArrayList arrayList3 = arrayList2;
                if (value != null) {
                    arrayList3.add(value);
                }
            } else {
                return new ResultWithDiagnostics.Failure(arrayList);
            }
        }
        return asSuccess(arrayList2, arrayList);
    }

    public static final <T, R> ResultWithDiagnostics<List<R>> flatMapSuccess(Iterable<? extends T> iterable, Function1<? super T, ? extends ResultWithDiagnostics<? extends Collection<? extends R>>> body) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            ResultWithDiagnostics<? extends Collection<? extends R>> resultWithDiagnosticsInvoke = body.invoke(it.next());
            arrayList.addAll(resultWithDiagnosticsInvoke.getReports());
            if (resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Success) {
                arrayList2.addAll((Collection) ((ResultWithDiagnostics.Success) resultWithDiagnosticsInvoke).getValue());
            } else {
                return new ResultWithDiagnostics.Failure(arrayList);
            }
        }
        return asSuccess(arrayList2, arrayList);
    }

    public static final <R> R valueOrThrow(ResultWithDiagnostics<? extends R> resultWithDiagnostics) {
        Object next;
        Intrinsics.checkNotNullParameter(resultWithDiagnostics, "<this>");
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
            return (R) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue();
        }
        if (!(resultWithDiagnostics instanceof ResultWithDiagnostics.Failure)) {
            throw new NoWhenBranchMatchedException();
        }
        String strJoinToString$default = CollectionsKt.joinToString$default(resultWithDiagnostics.getReports(), "\n", null, null, 0, null, new Function1<ScriptDiagnostic, CharSequence>() { // from class: kotlin.script.experimental.api.ErrorHandlingKt$valueOrThrow$1$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ScriptDiagnostic it) {
                String string;
                Intrinsics.checkNotNullParameter(it, "it");
                Throwable exception = it.getException();
                return (exception == null || (string = exception.toString()) == null) ? it.getMessage() : string;
            }
        }, 30, null);
        Iterator<T> it = resultWithDiagnostics.getReports().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((ScriptDiagnostic) next).getException() == null);
        ScriptDiagnostic scriptDiagnostic = (ScriptDiagnostic) next;
        throw new RuntimeException(strJoinToString$default, scriptDiagnostic != null ? scriptDiagnostic.getException() : null);
    }
}
