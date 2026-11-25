package org.jetbrains.kotlin.incremental;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ChangesEither.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangesEither;", "", "()V", "Known", "Unknown", "Lorg/jetbrains/kotlin/incremental/ChangesEither$Known;", "Lorg/jetbrains/kotlin/incremental/ChangesEither$Unknown;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ChangesEither {
    public /* synthetic */ ChangesEither(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ChangesEither() {
    }

    /* JADX INFO: compiled from: ChangesEither.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B%\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangesEither$Known;", "Lorg/jetbrains/kotlin/incremental/ChangesEither;", "lookupSymbols", "", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "fqNames", "Lorg/jetbrains/kotlin/name/FqName;", "(Ljava/util/Collection;Ljava/util/Collection;)V", "getFqNames", "()Ljava/util/Collection;", "getLookupSymbols", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Known extends ChangesEither {
        private final Collection<FqName> fqNames;
        private final Collection<LookupSymbol> lookupSymbols;

        /* JADX WARN: Multi-variable type inference failed */
        public Known() {
            this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }

        public /* synthetic */ Known(List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? CollectionsKt.emptyList() : list, (i & 2) != 0 ? CollectionsKt.emptyList() : list2);
        }

        public final Collection<LookupSymbol> getLookupSymbols() {
            return this.lookupSymbols;
        }

        public final Collection<FqName> getFqNames() {
            return this.fqNames;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Known(Collection<LookupSymbol> lookupSymbols, Collection<FqName> fqNames) {
            super(null);
            Intrinsics.checkNotNullParameter(lookupSymbols, "lookupSymbols");
            Intrinsics.checkNotNullParameter(fqNames, "fqNames");
            this.lookupSymbols = lookupSymbols;
            this.fqNames = fqNames;
        }
    }

    /* JADX INFO: compiled from: ChangesEither.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangesEither$Unknown;", "Lorg/jetbrains/kotlin/incremental/ChangesEither;", "reason", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;)V", "getReason", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Unknown extends ChangesEither {
        private final BuildAttribute reason;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Unknown(BuildAttribute reason) {
            super(null);
            Intrinsics.checkNotNullParameter(reason, "reason");
            this.reason = reason;
        }

        public final BuildAttribute getReason() {
            return this.reason;
        }
    }
}
