package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.resolve.dfa.FlowPath;
import org.jetbrains.kotlin.fir.resolve.dfa.PersistentFlow;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: CFGNode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0092\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 J*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003:\u0001JB\u0017\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u00107\u001a\u0002H8\"\u0004\b\u0001\u00108\"\u0004\b\u0002\u001092\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H90;2\u0006\u0010<\u001a\u0002H9H&¢\u0006\u0002\u0010=J\u000e\u00107\u001a\u00020>2\u0006\u0010:\u001a\u00020?J\u0018\u0010@\u001a\u00020>2\u0006\u0010A\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\fH\u0017J\u0012\u0010B\u001a\u00020\u00112\n\u0010C\u001a\u0006\u0012\u0002\b\u00030\u0000J\u0012\u0010D\u001a\u00020\u00112\n\u0010C\u001a\u0006\u0012\u0002\b\u00030\u0000J\u0012\u0010E\u001a\u0004\u0018\u00010\f2\u0006\u0010A\u001a\u00020\u000bH\u0016J\u001c\u0010F\u001a\u00020>2\n\u0010G\u001a\u0006\u0012\u0002\b\u00030\u00002\u0006\u0010H\u001a\u00020\u0011H\u0002J\b\u0010I\u001a\u00020>H\u0007R\u001c\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0010\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0000\u0012\u0004\u0012\u00020\u0011\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0012\u0010\u001b\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR$\u0010\u001f\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\f8V@WX\u0096\u000e¢\u0006\f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001b\u0010$\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000%8F¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0017\u0010(\u001a\u00020\u0007¢\u0006\u000e\n\u0000\u0012\u0004\b)\u0010*\u001a\u0004\b+\u0010,R$\u0010.\u001a\u00020\u00182\u0006\u0010-\u001a\u00020\u0018@DX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u001a\"\u0004\b/\u00100R\u0014\u00101\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b2\u0010,R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u001b\u00105\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000%8F¢\u0006\u0006\u001a\u0004\b6\u0010'\u0082\u0001_KLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007f\u0080\u0001\u0081\u0001\u0082\u0001\u0083\u0001\u0084\u0001\u0085\u0001\u0086\u0001\u0087\u0001\u0088\u0001\u0089\u0001\u008a\u0001\u008b\u0001\u008c\u0001\u008d\u0001\u008e\u0001\u008f\u0001\u0090\u0001\u0091\u0001\u0092\u0001\u0093\u0001\u0094\u0001¨\u0006\u0095\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "", "owner", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", FirebaseAnalytics.Param.LEVEL, "", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;I)V", "_alternateFlows", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;", "_flow", "_followingNodes", "", "_incomingEdges", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/Edge;", "_previousNodes", "alternateFlowPaths", "", "getAlternateFlowPaths", "()Ljava/util/Set;", "canThrow", "", "getCanThrow", "()Z", "fir", "getFir", "()Lorg/jetbrains/kotlin/fir/FirElement;", "value", "flow", "getFlow", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;", "setFlow", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;)V", "followingNodes", "", "getFollowingNodes", "()Ljava/util/List;", "id", "getId$annotations", "()V", "getId", "()I", "<set-?>", "isDead", "setDead", "(Z)V", "isUnion", "getLevel", "getOwner", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "previousNodes", "getPreviousNodes", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitorVoid;", "addAlternateFlow", "path", "edgeFrom", "other", "edgeTo", "getAlternateFlow", "insertIncomingEdge", TypedValues.TransitionType.S_FROM, "edge", "updateDeadStatus", "Companion", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AbstractBinaryExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterRightOperandNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitLeftOperandNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterRightOperandNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitLeftOperandNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNodeWithSubgraphs;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CallableReferenceNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CheckNotNullCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ComparisonExpressionNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ConstExpressionNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegateExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegatedConstructorCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsIsNotNullNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisRhsEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterDefaultArgumentsNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterSafeCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitDefaultArgumentsNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitSafeCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitValueParameterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FakeExpressionEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GetClassCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/JumpNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/MergePostponedLambdaExitsNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PostponedLambdaExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/QualifiedAccessNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ResolvedQualifierNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SmartCastExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StringConcatenationCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StubNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ThrowExceptionNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TypeOperatorCallNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSubjectExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSyntheticElseBranchNode;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CFGNode<E extends FirElement> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private Map<FlowPath, PersistentFlow> _alternateFlows;
    private PersistentFlow _flow;
    private final List<CFGNode<?>> _followingNodes;
    private Map<CFGNode<?>, Edge> _incomingEdges;
    private final List<CFGNode<?>> _previousNodes;
    private final int id;
    private boolean isDead;
    private final int level;
    private final ControlFlowGraph owner;

    public /* synthetic */ CFGNode(ControlFlowGraph controlFlowGraph, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(controlFlowGraph, i);
    }

    public static /* synthetic */ void getId$annotations() {
    }

    public abstract <R, D> R accept(ControlFlowGraphVisitor<? extends R, ? super D> visitor, D data);

    public boolean getCanThrow() {
        return false;
    }

    public abstract E getFir();

    public boolean isUnion() {
        return false;
    }

    private CFGNode(ControlFlowGraph controlFlowGraph, int i) {
        this.owner = controlFlowGraph;
        this.level = i;
        int nodeCount = controlFlowGraph.getNodeCount();
        controlFlowGraph.setNodeCount(nodeCount + 1);
        this.id = nodeCount;
        this._previousNodes = new SmartList();
        this._followingNodes = new SmartList();
    }

    public final int getLevel() {
        return this.level;
    }

    public final ControlFlowGraph getOwner() {
        return this.owner;
    }

    public final int getId() {
        return this.id;
    }

    /* JADX INFO: compiled from: CFGNode.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007J(\u0010\u000e\u001a\u00020\u000b2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0014\u0010\u000f\u001a\u00020\u00042\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0007J\u0014\u0010\u0010\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode$Companion;", "", "()V", "addEdge", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "to", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeKind;", "propagateDeadness", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "killEdge", "removeAllIncomingEdges", "removeAllOutgoingEdges", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void addEdge$default(Companion companion, CFGNode cFGNode, CFGNode cFGNode2, EdgeKind edgeKind, boolean z, EdgeLabel edgeLabel, int i, Object obj) {
            if ((i & 16) != 0) {
                edgeLabel = NormalPath.INSTANCE;
            }
            companion.addEdge(cFGNode, cFGNode2, edgeKind, z, edgeLabel);
        }

        @CfgInternals
        public final void addEdge(CFGNode<?> from, CFGNode<?> to, EdgeKind kind, boolean propagateDeadness, EdgeLabel label) {
            Intrinsics.checkNotNullParameter(from, "from");
            Intrinsics.checkNotNullParameter(to, "to");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(label, "label");
            ((CFGNode) from)._followingNodes.add(to);
            ((CFGNode) to)._previousNodes.add(from);
            if (kind != EdgeKind.Forward || !Intrinsics.areEqual(label, NormalPath.INSTANCE)) {
                to.insertIncomingEdge(from, Edge.INSTANCE.create(label, kind));
            }
            if (propagateDeadness && kind == EdgeKind.DeadForward) {
                to.setDead(true);
            }
        }

        @CfgInternals
        public final boolean killEdge(CFGNode<?> from, CFGNode<?> to, boolean propagateDeadness) {
            Intrinsics.checkNotNullParameter(from, "from");
            Intrinsics.checkNotNullParameter(to, "to");
            Edge edgeEdgeFrom = to.edgeFrom(from);
            if (edgeEdgeFrom.getKind().getIsDead()) {
                return false;
            }
            to.insertIncomingEdge(from, Edge.INSTANCE.create(edgeEdgeFrom.getLabel(), edgeEdgeFrom.getKind().getIsBack() ? EdgeKind.DeadBackward : EdgeKind.DeadForward));
            if (propagateDeadness) {
                to.setDead(true);
            }
            return true;
        }

        @CfgInternals
        public final void removeAllOutgoingEdges(CFGNode<?> from) {
            Intrinsics.checkNotNullParameter(from, "from");
            for (CFGNode cFGNode : ((CFGNode) from)._followingNodes) {
                cFGNode._previousNodes.remove(from);
                Map map = cFGNode._incomingEdges;
                if (map != null) {
                }
            }
            ((CFGNode) from)._followingNodes.clear();
        }

        @CfgInternals
        public final void removeAllIncomingEdges(CFGNode<?> to) {
            Intrinsics.checkNotNullParameter(to, "to");
            Iterator it = ((CFGNode) to)._previousNodes.iterator();
            while (it.hasNext()) {
                ((CFGNode) it.next())._followingNodes.remove(to);
            }
            ((CFGNode) to)._previousNodes.clear();
            Map map = ((CFGNode) to)._incomingEdges;
            if (map != null) {
                map.clear();
            }
        }
    }

    public final List<CFGNode<?>> getPreviousNodes() {
        return this._previousNodes;
    }

    public final List<CFGNode<?>> getFollowingNodes() {
        return this._followingNodes;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void insertIncomingEdge(CFGNode<?> from, Edge edge) {
        Map<CFGNode<?>, Edge> map = this._incomingEdges;
        if (map != null) {
            map.put(from, edge);
        } else {
            this._incomingEdges = MapsKt.mutableMapOf(TuplesKt.to(from, edge));
        }
    }

    public final Edge edgeFrom(CFGNode<?> other) {
        Edge edge;
        Intrinsics.checkNotNullParameter(other, "other");
        Map<CFGNode<?>, Edge> map = this._incomingEdges;
        return (map == null || (edge = map.get(other)) == null) ? Edge.INSTANCE.getNormal_Forward() : edge;
    }

    public final Edge edgeTo(CFGNode<?> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return other.edgeFrom(this);
    }

    /* JADX INFO: renamed from: isDead, reason: from getter */
    public final boolean getIsDead() {
        return this.isDead;
    }

    protected final void setDead(boolean z) {
        this.isDead = z;
    }

    public PersistentFlow getFlow() {
        PersistentFlow persistentFlow = this._flow;
        if (persistentFlow != null) {
            return persistentFlow;
        }
        throw new IllegalStateException("flow for " + this + " not initialized - traversing nodes in wrong order?");
    }

    @CfgInternals
    public void setFlow(PersistentFlow value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this._flow = value;
    }

    public Set<FlowPath> getAlternateFlowPaths() {
        Set<FlowPath> setKeySet;
        Map<FlowPath, PersistentFlow> map = this._alternateFlows;
        return (map == null || (setKeySet = map.keySet()) == null) ? SetsKt.emptySet() : setKeySet;
    }

    public PersistentFlow getAlternateFlow(FlowPath path) {
        Intrinsics.checkNotNullParameter(path, "path");
        Map<FlowPath, PersistentFlow> map = this._alternateFlows;
        if (map != null) {
            return map.get(path);
        }
        return null;
    }

    @CfgInternals
    public void addAlternateFlow(FlowPath path, PersistentFlow flow) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(flow, "flow");
        FlowPath.Default r0 = FlowPath.Default.INSTANCE;
        Map<FlowPath, PersistentFlow> map = this._alternateFlows;
        if (map != null) {
            map.get(path);
        }
        LinkedHashMap linkedHashMap = this._alternateFlows;
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap();
            this._alternateFlows = linkedHashMap;
        }
        linkedHashMap.put(path, flow);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0086  */
    @CfgInternals
    public final void updateDeadStatus() {
        boolean z = true;
        if (isUnion()) {
            Map<CFGNode<?>, Edge> map = this._incomingEdges;
            if (map == null) {
                z = false;
                break;
            }
            Collection<Edge> collectionValues = map.values();
            if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
                Iterator<T> it = collectionValues.iterator();
                while (it.hasNext()) {
                    if (((Edge) it.next()).getKind().getIsDead()) {
                    }
                }
                z = false;
                break;
            }
            z = false;
            break;
        }
        Map<CFGNode<?>, Edge> map2 = this._incomingEdges;
        if (map2 == null || map2.size() != getPreviousNodes().size()) {
            z = false;
            break;
        }
        Collection<Edge> collectionValues2 = map2.values();
        if (!(collectionValues2 instanceof Collection) || !collectionValues2.isEmpty()) {
            for (Edge edge : collectionValues2) {
                if (!edge.getKind().getIsDead() && edge.getKind().getUsedInCfa()) {
                    z = false;
                    break;
                }
            }
        }
        this.isDead = z;
    }

    public final void accept(ControlFlowGraphVisitorVoid visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        accept(visitor, null);
    }
}
