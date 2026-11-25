package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ControlFlowGraph.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeKind;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeKind;)V", "getKind", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeKind;", "getLabel", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Companion", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Edge {
    private final EdgeKind kind;
    private final EdgeLabel label;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Edge Normal_Forward = new Edge(NormalPath.INSTANCE, EdgeKind.Forward);
    private static final Edge Normal_DeadForward = new Edge(NormalPath.INSTANCE, EdgeKind.DeadForward);
    private static final Edge Normal_DfgForward = new Edge(NormalPath.INSTANCE, EdgeKind.DfgForward);
    private static final Edge Normal_CfgForward = new Edge(NormalPath.INSTANCE, EdgeKind.CfgForward);
    private static final Edge Normal_CfgBackward = new Edge(NormalPath.INSTANCE, EdgeKind.CfgBackward);
    private static final Edge Normal_DeadBackward = new Edge(NormalPath.INSTANCE, EdgeKind.DeadBackward);

    public static /* synthetic */ Edge copy$default(Edge edge, EdgeLabel edgeLabel, EdgeKind edgeKind, int i, Object obj) {
        if ((i & 1) != 0) {
            edgeLabel = edge.label;
        }
        if ((i & 2) != 0) {
            edgeKind = edge.kind;
        }
        return edge.copy(edgeLabel, edgeKind);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final EdgeLabel getLabel() {
        return this.label;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final EdgeKind getKind() {
        return this.kind;
    }

    public final Edge copy(EdgeLabel label, EdgeKind kind) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new Edge(label, kind);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Edge)) {
            return false;
        }
        Edge edge = (Edge) other;
        return Intrinsics.areEqual(this.label, edge.label) && this.kind == edge.kind;
    }

    public int hashCode() {
        return (this.label.hashCode() * 31) + this.kind.hashCode();
    }

    public String toString() {
        return "Edge(label=" + this.label + ", kind=" + this.kind + ')';
    }

    public Edge(EdgeLabel label, EdgeKind kind) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.label = label;
        this.kind = kind;
    }

    public final EdgeLabel getLabel() {
        return this.label;
    }

    public final EdgeKind getKind() {
        return this.kind;
    }

    /* JADX INFO: compiled from: ControlFlowGraph.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge$Companion;", "", "()V", "Normal_CfgBackward", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;", "Normal_CfgForward", "Normal_DeadBackward", "Normal_DeadForward", "Normal_DfgForward", "Normal_Forward", "getNormal_Forward", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeKind;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: ControlFlowGraph.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[EdgeKind.values().length];
                try {
                    iArr[EdgeKind.Forward.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[EdgeKind.DeadForward.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[EdgeKind.DfgForward.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[EdgeKind.CfgForward.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[EdgeKind.CfgBackward.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[EdgeKind.DeadBackward.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Edge getNormal_Forward() {
            return Edge.Normal_Forward;
        }

        public final Edge create(EdgeLabel label, EdgeKind kind) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(kind, "kind");
            if (Intrinsics.areEqual(label, NormalPath.INSTANCE)) {
                switch (WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                    case 1:
                        return getNormal_Forward();
                    case 2:
                        return Edge.Normal_DeadForward;
                    case 3:
                        return Edge.Normal_DfgForward;
                    case 4:
                        return Edge.Normal_CfgForward;
                    case 5:
                        return Edge.Normal_CfgBackward;
                    case 6:
                        return Edge.Normal_DeadBackward;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
            return new Edge(label, kind);
        }
    }
}
