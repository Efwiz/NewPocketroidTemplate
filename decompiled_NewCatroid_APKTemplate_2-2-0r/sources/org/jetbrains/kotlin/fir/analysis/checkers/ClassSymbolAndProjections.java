package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirInconsistentTypeParameterHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ClassSymbolAndProjections;", "", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "projections", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Ljava/util/List;)V", "getClassSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getProjections", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class ClassSymbolAndProjections {
    private final FirRegularClassSymbol classSymbol;
    private final List<ConeKotlinType> projections;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ClassSymbolAndProjections copy$default(ClassSymbolAndProjections classSymbolAndProjections, FirRegularClassSymbol firRegularClassSymbol, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            firRegularClassSymbol = classSymbolAndProjections.classSymbol;
        }
        if ((i & 2) != 0) {
            list = classSymbolAndProjections.projections;
        }
        return classSymbolAndProjections.copy(firRegularClassSymbol, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirRegularClassSymbol getClassSymbol() {
        return this.classSymbol;
    }

    public final List<ConeKotlinType> component2() {
        return this.projections;
    }

    public final ClassSymbolAndProjections copy(FirRegularClassSymbol classSymbol, List<ConeKotlinType> projections) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        Intrinsics.checkNotNullParameter(projections, "projections");
        return new ClassSymbolAndProjections(classSymbol, projections);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassSymbolAndProjections)) {
            return false;
        }
        ClassSymbolAndProjections classSymbolAndProjections = (ClassSymbolAndProjections) other;
        return Intrinsics.areEqual(this.classSymbol, classSymbolAndProjections.classSymbol) && Intrinsics.areEqual(this.projections, classSymbolAndProjections.projections);
    }

    public int hashCode() {
        return (this.classSymbol.hashCode() * 31) + this.projections.hashCode();
    }

    public String toString() {
        return "ClassSymbolAndProjections(classSymbol=" + this.classSymbol + ", projections=" + this.projections + ')';
    }

    public ClassSymbolAndProjections(FirRegularClassSymbol classSymbol, List<ConeKotlinType> projections) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        Intrinsics.checkNotNullParameter(projections, "projections");
        this.classSymbol = classSymbol;
        this.projections = projections;
    }

    public final FirRegularClassSymbol getClassSymbol() {
        return this.classSymbol;
    }

    public final List<ConeKotlinType> getProjections() {
        return this.projections;
    }
}
