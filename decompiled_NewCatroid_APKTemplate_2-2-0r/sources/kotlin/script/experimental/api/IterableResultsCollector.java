package kotlin.script.experimental.api;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: errorHandling.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t0\bJ\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0006J\u0013\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00028\u0000¢\u0006\u0002\u0010\u0012J\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00140\bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t0\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lkotlin/script/experimental/api/IterableResultsCollector;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "diagnostics", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "failureResults", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "", CompoundEntry.NAME_values, ProductAction.ACTION_ADD, "", "result", "addDiagnostic", "diagnostic", "addValue", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getResult", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IterableResultsCollector<T> {
    private final List<ScriptDiagnostic> diagnostics = new ArrayList();
    private final List<ResultWithDiagnostics<Iterable<T>>> failureResults = new ArrayList();
    private final List<T> values = new ArrayList();

    public final void add(ResultWithDiagnostics<? extends Iterable<? extends T>> result) {
        Intrinsics.checkNotNullParameter(result, "result");
        if (result instanceof ResultWithDiagnostics.Success) {
            this.diagnostics.addAll(result.getReports());
            CollectionsKt.addAll(this.values, (Iterable) ((ResultWithDiagnostics.Success) result).getValue());
        } else {
            this.failureResults.add(result);
        }
    }

    public final void addValue(T value) {
        this.values.add(value);
    }

    public final void addDiagnostic(ScriptDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.diagnostics.add(diagnostic);
    }

    public final ResultWithDiagnostics<List<T>> getResult() {
        if (this.values.isEmpty()) {
            List<ScriptDiagnostic> list = this.diagnostics;
            List<ResultWithDiagnostics<Iterable<T>>> list2 = this.failureResults;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, ((ResultWithDiagnostics) it.next()).getReports());
            }
            return new ResultWithDiagnostics.Failure((List<ScriptDiagnostic>) CollectionsKt.plus((Collection) list, (Iterable) arrayList));
        }
        return new ResultWithDiagnostics.Success(this.values, this.diagnostics);
    }
}
