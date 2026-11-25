package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirSinceKotlinHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility;", "", "()V", "Accessible", "NotAccessible", "NotAccessibleButWasExperimental", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility$Accessible;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility$NotAccessible;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility$NotAccessibleButWasExperimental;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSinceKotlinAccessibility {
    public /* synthetic */ FirSinceKotlinAccessibility(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: FirSinceKotlinHelpers.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility$Accessible;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility;", "()V", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Accessible extends FirSinceKotlinAccessibility {
        public static final Accessible INSTANCE = new Accessible();

        private Accessible() {
            super(null);
        }
    }

    private FirSinceKotlinAccessibility() {
    }

    /* JADX INFO: compiled from: FirSinceKotlinHelpers.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility$NotAccessibleButWasExperimental;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility;", "version", "Lorg/jetbrains/kotlin/config/ApiVersion;", "markerClasses", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "(Lorg/jetbrains/kotlin/config/ApiVersion;Ljava/util/List;)V", "getMarkerClasses", "()Ljava/util/List;", "getVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NotAccessibleButWasExperimental extends FirSinceKotlinAccessibility {
        private final List<FirRegularClassSymbol> markerClasses;
        private final ApiVersion version;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ NotAccessibleButWasExperimental copy$default(NotAccessibleButWasExperimental notAccessibleButWasExperimental, ApiVersion apiVersion, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                apiVersion = notAccessibleButWasExperimental.version;
            }
            if ((i & 2) != 0) {
                list = notAccessibleButWasExperimental.markerClasses;
            }
            return notAccessibleButWasExperimental.copy(apiVersion, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ApiVersion getVersion() {
            return this.version;
        }

        public final List<FirRegularClassSymbol> component2() {
            return this.markerClasses;
        }

        public final NotAccessibleButWasExperimental copy(ApiVersion version, List<FirRegularClassSymbol> markerClasses) {
            Intrinsics.checkNotNullParameter(version, "version");
            Intrinsics.checkNotNullParameter(markerClasses, "markerClasses");
            return new NotAccessibleButWasExperimental(version, markerClasses);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof NotAccessibleButWasExperimental)) {
                return false;
            }
            NotAccessibleButWasExperimental notAccessibleButWasExperimental = (NotAccessibleButWasExperimental) other;
            return Intrinsics.areEqual(this.version, notAccessibleButWasExperimental.version) && Intrinsics.areEqual(this.markerClasses, notAccessibleButWasExperimental.markerClasses);
        }

        public int hashCode() {
            return (this.version.hashCode() * 31) + this.markerClasses.hashCode();
        }

        public String toString() {
            return "NotAccessibleButWasExperimental(version=" + this.version + ", markerClasses=" + this.markerClasses + ')';
        }

        public final ApiVersion getVersion() {
            return this.version;
        }

        public final List<FirRegularClassSymbol> getMarkerClasses() {
            return this.markerClasses;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotAccessibleButWasExperimental(ApiVersion version, List<FirRegularClassSymbol> markerClasses) {
            super(null);
            Intrinsics.checkNotNullParameter(version, "version");
            Intrinsics.checkNotNullParameter(markerClasses, "markerClasses");
            this.version = version;
            this.markerClasses = markerClasses;
        }
    }

    /* JADX INFO: compiled from: FirSinceKotlinHelpers.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility$NotAccessible;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirSinceKotlinAccessibility;", "version", "Lorg/jetbrains/kotlin/config/ApiVersion;", "(Lorg/jetbrains/kotlin/config/ApiVersion;)V", "getVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NotAccessible extends FirSinceKotlinAccessibility {
        private final ApiVersion version;

        public static /* synthetic */ NotAccessible copy$default(NotAccessible notAccessible, ApiVersion apiVersion, int i, Object obj) {
            if ((i & 1) != 0) {
                apiVersion = notAccessible.version;
            }
            return notAccessible.copy(apiVersion);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ApiVersion getVersion() {
            return this.version;
        }

        public final NotAccessible copy(ApiVersion version) {
            Intrinsics.checkNotNullParameter(version, "version");
            return new NotAccessible(version);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof NotAccessible) && Intrinsics.areEqual(this.version, ((NotAccessible) other).version);
        }

        public int hashCode() {
            return this.version.hashCode();
        }

        public String toString() {
            return "NotAccessible(version=" + this.version + ')';
        }

        public final ApiVersion getVersion() {
            return this.version;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotAccessible(ApiVersion version) {
            super(null);
            Intrinsics.checkNotNullParameter(version, "version");
            this.version = version;
        }
    }
}
