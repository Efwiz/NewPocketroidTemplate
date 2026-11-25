package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FlowPath.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath;", "", "()V", "CfgEdge", "Default", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath$CfgEdge;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath$Default;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FlowPath {
    public /* synthetic */ FlowPath(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private FlowPath() {
    }

    /* JADX INFO: compiled from: FlowPath.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath$Default;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Default extends FlowPath {
        public static final Default INSTANCE = new Default();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Default)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return 1833072865;
        }

        public String toString() {
            return "Default";
        }

        private Default() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: FlowPath.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath$CfgEdge;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "fir", "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;Lorg/jetbrains/kotlin/fir/FirElement;)V", "getFir", "()Lorg/jetbrains/kotlin/fir/FirElement;", "getLabel", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class CfgEdge extends FlowPath {
        private final FirElement fir;
        private final EdgeLabel label;

        public static /* synthetic */ CfgEdge copy$default(CfgEdge cfgEdge, EdgeLabel edgeLabel, FirElement firElement, int i, Object obj) {
            if ((i & 1) != 0) {
                edgeLabel = cfgEdge.label;
            }
            if ((i & 2) != 0) {
                firElement = cfgEdge.fir;
            }
            return cfgEdge.copy(edgeLabel, firElement);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final EdgeLabel getLabel() {
            return this.label;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final FirElement getFir() {
            return this.fir;
        }

        public final CfgEdge copy(EdgeLabel label, FirElement fir) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(fir, "fir");
            return new CfgEdge(label, fir);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CfgEdge)) {
                return false;
            }
            CfgEdge cfgEdge = (CfgEdge) other;
            return Intrinsics.areEqual(this.label, cfgEdge.label) && Intrinsics.areEqual(this.fir, cfgEdge.fir);
        }

        public int hashCode() {
            return (this.label.hashCode() * 31) + this.fir.hashCode();
        }

        public String toString() {
            return "CfgEdge(label=" + this.label + ", fir=" + this.fir + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CfgEdge(EdgeLabel label, FirElement fir) {
            super(null);
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(fir, "fir");
            this.label = label;
            this.fir = fir;
        }

        public final FirElement getFir() {
            return this.fir;
        }

        public final EdgeLabel getLabel() {
            return this.label;
        }
    }
}
