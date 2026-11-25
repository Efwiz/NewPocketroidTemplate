package kotlin.script.experimental.api;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: errorHandling.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\t\nB\u0007\b\u0004¢\u0006\u0002\u0010\u0003R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0001\u0002\u000b\f¨\u0006\r"}, d2 = {"Lkotlin/script/experimental/api/ResultWithDiagnostics;", "R", "", "()V", "reports", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "getReports", "()Ljava/util/List;", "Failure", "Success", "Lkotlin/script/experimental/api/ResultWithDiagnostics$Failure;", "Lkotlin/script/experimental/api/ResultWithDiagnostics$Success;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResultWithDiagnostics<R> {
    public /* synthetic */ ResultWithDiagnostics(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract List<ScriptDiagnostic> getReports();

    private ResultWithDiagnostics() {
    }

    /* JADX INFO: compiled from: errorHandling.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u000e\u0010\r\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J.\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001¢\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u0019"}, d2 = {"Lkotlin/script/experimental/api/ResultWithDiagnostics$Success;", "R", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "value", "reports", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "(Ljava/lang/Object;Ljava/util/List;)V", "getReports", "()Ljava/util/List;", "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Ljava/util/List;)Lkotlin/script/experimental/api/ResultWithDiagnostics$Success;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Success<R> extends ResultWithDiagnostics<R> {
        private final List<ScriptDiagnostic> reports;
        private final R value;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Success copy$default(Success success, Object obj, List list, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = success.value;
            }
            if ((i & 2) != 0) {
                list = success.reports;
            }
            return success.copy(obj, list);
        }

        public final R component1() {
            return this.value;
        }

        public final List<ScriptDiagnostic> component2() {
            return this.reports;
        }

        public final Success<R> copy(R value, List<ScriptDiagnostic> reports) {
            Intrinsics.checkNotNullParameter(reports, "reports");
            return new Success<>(value, reports);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Success)) {
                return false;
            }
            Success success = (Success) other;
            return Intrinsics.areEqual(this.value, success.value) && Intrinsics.areEqual(this.reports, success.reports);
        }

        public int hashCode() {
            R r = this.value;
            return ((r == null ? 0 : r.hashCode()) * 31) + this.reports.hashCode();
        }

        public String toString() {
            return "Success(value=" + this.value + ", reports=" + this.reports + ')';
        }

        public final R getValue() {
            return this.value;
        }

        public /* synthetic */ Success(Object obj, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(obj, (i & 2) != 0 ? CollectionsKt.emptyList() : list);
        }

        @Override // kotlin.script.experimental.api.ResultWithDiagnostics
        public List<ScriptDiagnostic> getReports() {
            return this.reports;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Success(R r, List<ScriptDiagnostic> reports) {
            super(null);
            Intrinsics.checkNotNullParameter(reports, "reports");
            this.value = r;
            this.reports = reports;
        }
    }

    /* JADX INFO: compiled from: errorHandling.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001b\b\u0016\u0012\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0003J\u0019\u0010\f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lkotlin/script/experimental/api/ResultWithDiagnostics$Failure;", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "", "reports", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "([Lkotlin/script/experimental/api/ScriptDiagnostic;)V", "", "(Ljava/util/List;)V", "getReports", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Failure extends ResultWithDiagnostics {
        private final List<ScriptDiagnostic> reports;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Failure copy$default(Failure failure, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                list = failure.reports;
            }
            return failure.copy(list);
        }

        public final List<ScriptDiagnostic> component1() {
            return this.reports;
        }

        public final Failure copy(List<ScriptDiagnostic> reports) {
            Intrinsics.checkNotNullParameter(reports, "reports");
            return new Failure(reports);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Failure) && Intrinsics.areEqual(this.reports, ((Failure) other).reports);
        }

        public int hashCode() {
            return this.reports.hashCode();
        }

        public String toString() {
            return "Failure(reports=" + this.reports + ')';
        }

        @Override // kotlin.script.experimental.api.ResultWithDiagnostics
        public List<ScriptDiagnostic> getReports() {
            return this.reports;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Failure(List<ScriptDiagnostic> reports) {
            super(null);
            Intrinsics.checkNotNullParameter(reports, "reports");
            this.reports = reports;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Failure(ScriptDiagnostic... reports) {
            this((List<ScriptDiagnostic>) ArraysKt.asList(reports));
            Intrinsics.checkNotNullParameter(reports, "reports");
        }
    }
}
