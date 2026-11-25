package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ImportUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/PackageResolutionResult;", "", "()V", "Error", "PackageOrClass", "Lorg/jetbrains/kotlin/fir/resolve/transformers/PackageResolutionResult$Error;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/PackageResolutionResult$PackageOrClass;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PackageResolutionResult {
    public /* synthetic */ PackageResolutionResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PackageResolutionResult() {
    }

    /* JADX INFO: compiled from: ImportUtils.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006HÆ\u0003J/\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/PackageResolutionResult$PackageOrClass;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/PackageResolutionResult;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "relativeClassFqName", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)V", "getClassSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getRelativeClassFqName", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class PackageOrClass extends PackageResolutionResult {
        private final FirClassLikeSymbol<?> classSymbol;
        private final FqName packageFqName;
        private final FqName relativeClassFqName;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ PackageOrClass copy$default(PackageOrClass packageOrClass, FqName fqName, FqName fqName2, FirClassLikeSymbol firClassLikeSymbol, int i, Object obj) {
            if ((i & 1) != 0) {
                fqName = packageOrClass.packageFqName;
            }
            if ((i & 2) != 0) {
                fqName2 = packageOrClass.relativeClassFqName;
            }
            if ((i & 4) != 0) {
                firClassLikeSymbol = packageOrClass.classSymbol;
            }
            return packageOrClass.copy(fqName, fqName2, firClassLikeSymbol);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FqName getPackageFqName() {
            return this.packageFqName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final FqName getRelativeClassFqName() {
            return this.relativeClassFqName;
        }

        public final FirClassLikeSymbol<?> component3() {
            return this.classSymbol;
        }

        public final PackageOrClass copy(FqName packageFqName, FqName relativeClassFqName, FirClassLikeSymbol<?> classSymbol) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            return new PackageOrClass(packageFqName, relativeClassFqName, classSymbol);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PackageOrClass)) {
                return false;
            }
            PackageOrClass packageOrClass = (PackageOrClass) other;
            return Intrinsics.areEqual(this.packageFqName, packageOrClass.packageFqName) && Intrinsics.areEqual(this.relativeClassFqName, packageOrClass.relativeClassFqName) && Intrinsics.areEqual(this.classSymbol, packageOrClass.classSymbol);
        }

        public int hashCode() {
            int iHashCode = this.packageFqName.hashCode() * 31;
            FqName fqName = this.relativeClassFqName;
            int iHashCode2 = (iHashCode + (fqName == null ? 0 : fqName.hashCode())) * 31;
            FirClassLikeSymbol<?> firClassLikeSymbol = this.classSymbol;
            return iHashCode2 + (firClassLikeSymbol != null ? firClassLikeSymbol.hashCode() : 0);
        }

        public String toString() {
            return "PackageOrClass(packageFqName=" + this.packageFqName + ", relativeClassFqName=" + this.relativeClassFqName + ", classSymbol=" + this.classSymbol + ')';
        }

        public final FirClassLikeSymbol<?> getClassSymbol() {
            return this.classSymbol;
        }

        public final FqName getPackageFqName() {
            return this.packageFqName;
        }

        public final FqName getRelativeClassFqName() {
            return this.relativeClassFqName;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PackageOrClass(FqName packageFqName, FqName fqName, FirClassLikeSymbol<?> firClassLikeSymbol) {
            super(null);
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            this.packageFqName = packageFqName;
            this.relativeClassFqName = fqName;
            this.classSymbol = firClassLikeSymbol;
        }
    }

    /* JADX INFO: compiled from: ImportUtils.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/PackageResolutionResult$Error;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/PackageResolutionResult;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "(Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Error extends PackageResolutionResult {
        private final ConeDiagnostic diagnostic;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Error(ConeDiagnostic diagnostic) {
            super(null);
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            this.diagnostic = diagnostic;
        }

        public final ConeDiagnostic getDiagnostic() {
            return this.diagnostic;
        }
    }
}
