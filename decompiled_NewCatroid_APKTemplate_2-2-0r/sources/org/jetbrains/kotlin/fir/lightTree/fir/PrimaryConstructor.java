package org.jetbrains.kotlin.fir.lightTree.fir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PrimaryConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/PrimaryConstructor;", "", "firConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "valueParameters", "", "Lorg/jetbrains/kotlin/fir/lightTree/fir/ValueParameter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Ljava/util/List;)V", "getFirConstructor", "()Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "getValueParameters", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PrimaryConstructor {
    private final FirConstructor firConstructor;
    private final List<ValueParameter> valueParameters;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PrimaryConstructor copy$default(PrimaryConstructor primaryConstructor, FirConstructor firConstructor, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            firConstructor = primaryConstructor.firConstructor;
        }
        if ((i & 2) != 0) {
            list = primaryConstructor.valueParameters;
        }
        return primaryConstructor.copy(firConstructor, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirConstructor getFirConstructor() {
        return this.firConstructor;
    }

    public final List<ValueParameter> component2() {
        return this.valueParameters;
    }

    public final PrimaryConstructor copy(FirConstructor firConstructor, List<ValueParameter> valueParameters) {
        Intrinsics.checkNotNullParameter(firConstructor, "firConstructor");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        return new PrimaryConstructor(firConstructor, valueParameters);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PrimaryConstructor)) {
            return false;
        }
        PrimaryConstructor primaryConstructor = (PrimaryConstructor) other;
        return Intrinsics.areEqual(this.firConstructor, primaryConstructor.firConstructor) && Intrinsics.areEqual(this.valueParameters, primaryConstructor.valueParameters);
    }

    public int hashCode() {
        return (this.firConstructor.hashCode() * 31) + this.valueParameters.hashCode();
    }

    public String toString() {
        return "PrimaryConstructor(firConstructor=" + this.firConstructor + ", valueParameters=" + this.valueParameters + ')';
    }

    public PrimaryConstructor(FirConstructor firConstructor, List<ValueParameter> valueParameters) {
        Intrinsics.checkNotNullParameter(firConstructor, "firConstructor");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        this.firConstructor = firConstructor;
        this.valueParameters = valueParameters;
    }

    public final FirConstructor getFirConstructor() {
        return this.firConstructor;
    }

    public final List<ValueParameter> getValueParameters() {
        return this.valueParameters;
    }
}
