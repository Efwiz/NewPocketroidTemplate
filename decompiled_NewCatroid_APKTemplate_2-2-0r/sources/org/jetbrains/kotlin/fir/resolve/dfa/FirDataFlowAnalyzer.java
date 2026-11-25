package org.jetbrains.kotlin.fir.resolve.dfa;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRangeKt;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhaseKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirJump;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.FirCodeFragmentContext;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.AbstractBinaryExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.AlternateFlowEndMarker;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.AlternateFlowStartMarker;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.AnonymousFunctionExpressionNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.AnonymousObjectExpressionExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ClassEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ClassExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CodeFragmentExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphBuilder;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.Edge;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ElvisExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ElvisLhsExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ElvisLhsIsNotNullNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ElvisRhsEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EnterDefaultArgumentsNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EnterValueParameterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EqualityOperatorCallNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ExitDefaultArgumentsNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ExitSafeCallNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ExitValueParameterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FieldInitializerEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FieldInitializerExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FirAnonymousFunctionReturnExpressionInfo;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.InitBlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LocalFunctionDeclarationNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopBlockEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopBlockExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopConditionEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopConditionExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.MergePostponedLambdaExitsNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.NormalPath;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.PostponedLambdaExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.PropertyInitializerEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.PropertyInitializerExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ScriptExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.SplitPostponedLambdasNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.TryExpressionEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.TryMainBlockEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.UncaughtExceptionPath;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenBranchConditionExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenBranchResultEnterNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenExitNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.WhenSyntheticElseBranchNode;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.InvocationKindTransformerKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDataFlowAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000È\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\b&\u0018\u0000 \u009b\u00022\u00020\u0001:\u0004\u009b\u0002\u009c\u0002B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0002J\u0006\u0010+\u001a\u00020&J\u000e\u0010,\u001a\u00020&2\u0006\u0010-\u001a\u00020.J\u000e\u0010/\u001a\u00020&2\u0006\u00100\u001a\u000201J\u000e\u00102\u001a\u00020&2\u0006\u00103\u001a\u000204J\u001c\u00105\u001a\u00020&2\u0006\u00106\u001a\u0002072\f\u00108\u001a\b\u0012\u0004\u0012\u00020:09J\u000e\u0010;\u001a\u00020&2\u0006\u0010<\u001a\u00020=J\u0006\u0010>\u001a\u00020&J\u0016\u0010?\u001a\u00020&2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020 J\u000e\u0010C\u001a\u00020&2\u0006\u0010D\u001a\u00020EJ\u0006\u0010F\u001a\u00020&J\u0006\u0010G\u001a\u00020&J\u000e\u0010H\u001a\u00020&2\u0006\u0010I\u001a\u00020JJ\u000e\u0010K\u001a\u00020&2\u0006\u0010I\u001a\u00020JJ\u000e\u0010L\u001a\u00020&2\u0006\u0010M\u001a\u00020NJ\u000e\u0010O\u001a\u00020&2\u0006\u0010P\u001a\u00020QJ\u0006\u0010R\u001a\u00020&J\u000e\u0010S\u001a\u00020&2\u0006\u0010T\u001a\u00020UJ\u000e\u0010V\u001a\u00020&2\u0006\u0010W\u001a\u00020XJ\u0012\u0010Y\u001a\u00020&2\n\u0010Z\u001a\u0006\u0012\u0002\b\u00030[J\u000e\u0010\\\u001a\u00020&2\u0006\u0010]\u001a\u00020^J\u0018\u0010_\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010`\u001a\u000207H\u0002J\u000e\u0010a\u001a\u00020&2\u0006\u0010b\u001a\u00020cJ\u000e\u0010d\u001a\u00020&2\u0006\u0010e\u001a\u00020fJ\u0006\u0010g\u001a\u00020&J\u000e\u0010h\u001a\u00020&2\u0006\u0010i\u001a\u00020jJ\u000e\u0010k\u001a\u00020&2\u0006\u0010l\u001a\u00020mJ\u000e\u0010n\u001a\u00020&2\u0006\u0010o\u001a\u00020pJ\u000e\u0010q\u001a\u00020&2\u0006\u0010r\u001a\u00020sJ\u000e\u0010t\u001a\u00020&2\u0006\u0010I\u001a\u00020JJ\u0006\u0010u\u001a\u00020&J\u000e\u0010v\u001a\u00020&2\u0006\u0010w\u001a\u00020xJ\u000e\u0010y\u001a\u00020&2\u0006\u00100\u001a\u000201J\u000e\u0010z\u001a\u00020&2\u0006\u00103\u001a\u000204J\u0018\u0010{\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010|\u001a\u00020}H\u0002J\u0006\u0010~\u001a\u00020&J\u0010\u0010\u007f\u001a\u00020&2\b\u0010\u0080\u0001\u001a\u00030\u0081\u0001J\u000f\u0010\u0082\u0001\u001a\u00020&2\u0006\u0010<\u001a\u00020=J\u001a\u0010\u0083\u0001\u001a\u00020&2\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0007\u0010\u0086\u0001\u001a\u00020 J\n\u0010\u0087\u0001\u001a\u0005\u0018\u00010\u0088\u0001J\b\u0010\u0089\u0001\u001a\u00030\u0088\u0001J\u0011\u0010\u008a\u0001\u001a\u00020&2\b\u0010\u008b\u0001\u001a\u00030\u008c\u0001J\u0015\u0010\u008d\u0001\u001a\u00020&2\f\u0010\u008e\u0001\u001a\u0007\u0012\u0002\b\u00030\u008f\u0001J\u0007\u0010\u0090\u0001\u001a\u00020&J\u0010\u0010\u0091\u0001\u001a\u00020&2\u0007\u0010\u0092\u0001\u001a\u00020:J\u0019\u0010\u0093\u0001\u001a\u00020&2\u0007\u00106\u001a\u00030\u0094\u00012\u0007\u0010\u0086\u0001\u001a\u00020 J\u000f\u0010\u0095\u0001\u001a\u00020&2\u0006\u0010I\u001a\u00020JJ!\u0010\u0096\u0001\u001a\u00020&2\u0006\u0010M\u001a\u00020N2\u0007\u0010\u0097\u0001\u001a\u00020 2\u0007\u0010\u0086\u0001\u001a\u00020 J\u000f\u0010\u0098\u0001\u001a\u00020&2\u0006\u0010M\u001a\u00020NJ\u0011\u0010\u0099\u0001\u001a\u00020&2\b\u0010\u009a\u0001\u001a\u00030\u009b\u0001J\u0012\u0010\u009c\u0001\u001a\u0005\u0018\u00010\u0088\u00012\u0006\u0010P\u001a\u00020QJ\u0007\u0010\u009d\u0001\u001a\u00020&J\u0012\u0010\u009e\u0001\u001a\u0005\u0018\u00010\u009f\u00012\u0006\u0010T\u001a\u00020UJ\u0019\u0010 \u0001\u001a\u00020&2\u0007\u0010¡\u0001\u001a\u00020}2\u0007\u0010\u0086\u0001\u001a\u00020 J\u0011\u0010¢\u0001\u001a\u00020&2\b\u0010£\u0001\u001a\u00030¤\u0001J\b\u0010¥\u0001\u001a\u00030\u0088\u0001J\u0013\u0010¦\u0001\u001a\u00020&2\n\u0010Z\u001a\u0006\u0012\u0002\b\u00030[J\u000f\u0010§\u0001\u001a\u00020&2\u0006\u00100\u001a\u000201J\u0019\u0010¨\u0001\u001a\u00020&2\u0007\u0010©\u0001\u001a\u00020^2\u0007\u0010ª\u0001\u001a\u00020 J\u0012\u0010«\u0001\u001a\u0005\u0018\u00010\u0088\u00012\u0006\u0010]\u001a\u00020^J\u0011\u0010¬\u0001\u001a\u00020&2\b\u0010\u00ad\u0001\u001a\u00030®\u0001J\u0019\u0010¯\u0001\u001a\n\u0012\u0004\u0012\u00020!\u0018\u0001092\u0006\u0010`\u001a\u000207H\u0002J\u0011\u0010°\u0001\u001a\u00020&2\b\u0010±\u0001\u001a\u00030²\u0001J\u000f\u0010³\u0001\u001a\u00020&2\u0006\u0010b\u001a\u00020cJ\b\u0010´\u0001\u001a\u00030\u0088\u0001J\u0011\u0010µ\u0001\u001a\u00020&2\b\u0010¶\u0001\u001a\u00030·\u0001J\u0010\u0010¸\u0001\u001a\u00020&2\u0007\u00106\u001a\u00030¹\u0001J\u0011\u0010º\u0001\u001a\u00020&2\b\u0010»\u0001\u001a\u00030¼\u0001J\u0010\u0010½\u0001\u001a\u00020&2\u0007\u0010\u0086\u0001\u001a\u00020 J\u0007\u0010¾\u0001\u001a\u00020&J\u000f\u0010¿\u0001\u001a\u00020&2\u0006\u0010)\u001a\u00020*J\u0012\u0010À\u0001\u001a\u0005\u0018\u00010\u0088\u00012\u0006\u0010l\u001a\u00020mJ\u0011\u0010Á\u0001\u001a\u00020&2\b\u0010Â\u0001\u001a\u00030Ã\u0001J6\u0010Ä\u0001\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0007\u0010Å\u0001\u001a\u00020:2\u0006\u0010]\u001a\u00020^2\t\u0010Æ\u0001\u001a\u0004\u0018\u00010:2\u0007\u0010Ç\u0001\u001a\u00020 H\u0002J\u000f\u0010È\u0001\u001a\u00020&2\u0006\u0010o\u001a\u00020pJ\u000f\u0010É\u0001\u001a\u00020&2\u0006\u0010o\u001a\u00020pJ\u0018\u0010Ê\u0001\u001a\u00020&2\u0006\u0010r\u001a\u00020s2\u0007\u0010\u0086\u0001\u001a\u00020 J\u0010\u0010Ë\u0001\u001a\u00020&2\u0007\u0010|\u001a\u00030Ì\u0001J\u000f\u0010Í\u0001\u001a\u00020&2\u0006\u0010I\u001a\u00020JJ\u000f\u0010Î\u0001\u001a\u00020&2\u0006\u0010I\u001a\u00020JJ)\u0010Ï\u0001\u001a\u001a\u0012\u0005\u0012\u00030Ñ\u0001\u0012\f\u0012\n\u0012\u0005\u0012\u00030Ó\u00010Ò\u0001\u0018\u00010Ð\u00012\u0006\u0010|\u001a\u00020:H\u0016J\u0013\u0010Ô\u0001\u001a\u00020 2\b\u0010Õ\u0001\u001a\u00030Ó\u0001H\u0002J\u000f\u0010Ö\u0001\u001a\u00020 2\u0006\u0010|\u001a\u00020:J\u001a\u0010×\u0001\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0007\u0010Ø\u0001\u001a\u000207H\u0002J5\u0010Ù\u0001\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010|\u001a\u00020:2\u0007\u0010Ú\u0001\u001a\u00020:2\u0007\u0010Û\u0001\u001a\u00020:2\b\u0010Ü\u0001\u001a\u00030Ý\u0001H\u0002J9\u0010Þ\u0001\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010|\u001a\u00020:2\u0007\u0010ß\u0001\u001a\u00020:2\f\u0010à\u0001\u001a\u0007\u0012\u0002\b\u00030\u008f\u00012\u0007\u0010á\u0001\u001a\u00020 H\u0002J+\u0010â\u0001\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010|\u001a\u00020:2\u0007\u0010ß\u0001\u001a\u00020:2\u0007\u0010á\u0001\u001a\u00020 H\u0002J%\u0010ã\u0001\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\b\u0010ä\u0001\u001a\u00030å\u00012\b\u0010æ\u0001\u001a\u00030ç\u0001H\u0002J/\u0010è\u0001\u001a\u00020&2\b\u0010é\u0001\u001a\u00030ê\u00012\u0006\u0010'\u001a\u00020(2\b\u0010ä\u0001\u001a\u00030å\u00012\b\u0010ë\u0001\u001a\u00030ì\u0001H\u0002J#\u0010í\u0001\u001a\u00020&2\f\u0010î\u0001\u001a\u0007\u0012\u0002\b\u00030ï\u00012\n\u0010ð\u0001\u001a\u0005\u0018\u00010ñ\u0001H$J\t\u0010ò\u0001\u001a\u00020&H\u0002J\u0018\u0010ó\u0001\u001a\n\u0012\u0005\u0012\u00030õ\u00010ô\u00012\u0007\u0010T\u001a\u00030ö\u0001J\u001a\u0010÷\u0001\u001a\f\u0012\u0005\u0012\u00030õ\u0001\u0018\u00010ô\u00012\u0007\u0010T\u001a\u00030ö\u0001J\u001f\u0010ø\u0001\u001a\u00020&2\t\u0010ù\u0001\u001a\u0004\u0018\u00010\f2\t\u0010ú\u0001\u001a\u0004\u0018\u00010\fH\u0002J \u0010û\u0001\u001a\u00020&*\u00020(2\b\u0010ü\u0001\u001a\u00030ý\u00012\u0007\u0010ù\u0001\u001a\u00020\fH\u0002J3\u0010û\u0001\u001a\u00020&*\u00020(2\b\u0010ü\u0001\u001a\u00030ý\u00012\u001a\u0010þ\u0001\u001a\u0015\u0012\u0004\u0012\u00020!\u0012\u0005\u0012\u00030ñ\u00010ÿ\u0001j\u0003`\u0080\u0002H\u0002J)\u0010\u0081\u0002\u001a\u00020&*\u00020(2\u001a\u0010þ\u0001\u001a\u0015\u0012\u0004\u0012\u00020!\u0012\u0005\u0012\u00030ñ\u00010ÿ\u0001j\u0003`\u0080\u0002H\u0002J\u0016\u0010\u0082\u0002\u001a\u00020&*\u00020(2\u0007\u0010`\u001a\u00030\u0083\u0002H\u0002J\u0017\u0010\u0084\u0002\u001a\u00020&*\u00020(2\b\u0010ð\u0001\u001a\u00030ñ\u0001H\u0002J9\u0010\u0085\u0002\u001a\u00020(*\u0007\u0012\u0002\b\u00030\u0086\u00022\b\u0010é\u0001\u001a\u00030ê\u00012\u001b\u0010\u0087\u0002\u001a\u0016\u0012\u0005\u0012\u00030ê\u0001\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020&0\u0088\u0002H\u0002J\u0016\u0010\u0089\u0002\u001a\u00020&*\u00020(2\u0007\u0010`\u001a\u00030ý\u0001H\u0002J\u001d\u0010\u008a\u0002\u001a\u00030\u008b\u0002*\u0007\u0012\u0002\b\u00030\u0086\u00022\b\u0010é\u0001\u001a\u00030ê\u0001H\u0002J%\u0010\u008c\u0002\u001a\u00020 *\u0007\u0012\u0002\b\u00030\u008d\u00022\b\u0010\u008e\u0002\u001a\u00030\u008f\u00022\u0007\u0010\u0090\u0002\u001a\u00020 H\u0002J\u0016\u0010\u0091\u0002\u001a\u00020 *\u00020!2\u0007\u0010\u0092\u0002\u001a\u00020:H\u0002J\u0014\u0010\u0093\u0002\u001a\u00020&*\t\u0012\u0004\u0012\u0002010\u0094\u0002H\u0002J1\u0010\u0095\u0002\u001a\u00020&*\u0007\u0012\u0002\b\u00030\u0086\u00022\u001d\b\u0002\u0010\u0087\u0002\u001a\u0016\u0012\u0005\u0012\u00030ê\u0001\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020&0\u0088\u0002H\u0002J\u0012\u0010\u0096\u0002\u001a\u00020&*\u0007\u0012\u0002\b\u00030\u0086\u0002H\u0002J)\u0010\u0097\u0002\u001a\u000f\u0012\b\b\u0001\u0012\u0004\u0018\u00010:\u0018\u00010\u0098\u0002*\u0002072\u0007\u0010\u0099\u0002\u001a\u00020UH\u0002¢\u0006\u0003\u0010\u009a\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0016\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\u0017X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\u00020 *\u00020!8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0018\u0010$\u001a\u00020 *\u00020!8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b$\u0010#¨\u0006\u009d\u0002²\u0006\f\u0010\u009e\u0002\u001a\u00030\u009f\u0002X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "context", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowAnalyzerContext;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowAnalyzerContext;)V", "any", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "currentReceiverState", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Flow;", "graphBuilder", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphBuilder;", "getGraphBuilder", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphBuilder;", "logicSystem", "Lorg/jetbrains/kotlin/fir/resolve/dfa/LogicSystem;", "getLogicSystem", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/LogicSystem;", "nullableNothing", "receiverStack", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "getReceiverStack", "()Ljava/lang/Iterable;", "variableStorage", "Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorageImpl;", "getVariableStorage", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorageImpl;", "hasLocalStability", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "getHasLocalStability", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;)Z", "isStable", "addTypeOperatorStatements", "", "flow", "Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableFlow;", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "enterAnnotation", "enterAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "enterBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "enterBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "enterCallArguments", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "enterCatchClause", PsiKeyword.CATCH, "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", "enterCheckNotNullCall", "enterClass", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "buildGraph", "enterCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "enterContractDescription", "enterDelegateExpression", "enterDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "enterDoWhileLoopCondition", "enterElvis", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "enterField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "enterFinallyBlock", "enterFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "enterInitBlock", "initBlock", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "enterJump", "jump", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "enterProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "enterRepeatableStatement", "statement", "enterSafeCallAfterNullCheck", "safeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "enterScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "enterStringConcatenationCall", "enterTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "enterValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "enterWhenBranchCondition", "whenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "enterWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "enterWhileLoop", "exitAnnotation", "exitAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "exitBinaryLogicExpression", "exitBlock", "exitBooleanNot", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "exitCallArguments", "exitCallableReference", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "exitCatchClause", "exitCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "callCompleted", "exitClass", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "exitCodeFragment", "exitComparisonExpressionCall", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "exitConstExpression", "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "exitContractDescription", "exitDelegateExpression", "fir", "exitDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "exitDoWhileLoop", "exitElvis", "isLhsNotNull", "exitElvisLhs", "exitEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "exitField", "exitFinallyBlock", "exitFunction", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "exitFunctionCall", "functionCall", "exitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "exitInitBlock", "exitJump", "exitLeftBinaryLogicExpressionArgument", "exitLocalVariableDeclaration", "variable", "hadExplicitType", "exitProperty", "exitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "exitRepeatableStatement", "exitResolvedQualifierNode", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "exitSafeCall", "exitScript", "exitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "exitStringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "exitThrowExceptionNode", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "exitTryExpression", "exitTryMainBlock", "exitTypeOperatorCall", "exitValueParameter", "exitVariableAssignment", "assignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "exitVariableInitialization", "initializer", "assignmentLhs", "hasExplicitType", "exitWhenBranchCondition", "exitWhenBranchResult", "exitWhenExpression", "exitWhenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "exitWhileLoop", "exitWhileLoopCondition", "getTypeUsingSmartcastInfo", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PropertyStability;", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "hasOverriddenEquals", ModuleXmlParser.TYPE, "isAccessToUnstableLocalVariable", "processConditionalContract", "qualifiedAccess", "processEq", "leftOperand", "rightOperand", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "processEqConst", "operand", PsiKeyword.CONST, "isEq", "processEqNull", "processLoopExit", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;", "conditionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionExitNode;", "processWhileLoopExit", "path", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FlowPath;", "conditionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionEnterNode;", "receiverUpdated", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "resetReceivers", "returnExpressionsOfAnonymousFunction", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FirAnonymousFunctionReturnExpressionInfo;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "returnExpressionsOfAnonymousFunctionOrNull", "updateAllReceivers", TypedValues.TransitionType.S_FROM, "to", "addAllConditionally", "condition", "Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;", "statements", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatements;", "addAllStatements", "addImplication", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Implication;", "addTypeStatement", "buildIncomingFlow", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "builder", "Lkotlin/Function2;", "commitOperationStatement", "getFlow", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;", "hasEqualsOverride", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkModality", "isStableOrLocalStableAccess", "access", "mergeBinaryLogicOperatorFlow", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AbstractBinaryExitNode;", "mergeIncomingFlow", "mergeWhenBranchEntryFlow", "orderedArguments", "", "callee", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)[Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Companion", "FlowPathOperation", "resolve", "elvisVariable", "Lorg/jetbrains/kotlin/fir/resolve/dfa/SyntheticVariable;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDataFlowAnalyzer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ConeClassLikeType any;
    private final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components;
    private final DataFlowAnalyzerContext context;
    private Flow currentReceiverState;
    private final ConeClassLikeType nullableNothing;

    /* JADX INFO: compiled from: FirDataFlowAnalyzer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer$FlowPathOperation;", "", "(Ljava/lang/String;I)V", "PROPAGATE", "ADDITIONAL", "DIVERGE", "TERMINATE", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum FlowPathOperation {
        PROPAGATE,
        ADDITIONAL,
        DIVERGE,
        TERMINATE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<FlowPathOperation> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: FirDataFlowAnalyzer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.IS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.NOT_IS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirOperation.AS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirOperation.SAFE_AS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected abstract LogicSystem getLogicSystem();

    protected abstract Iterable<ImplicitReceiverValue<?>> getReceiverStack();

    protected abstract void receiverUpdated(FirBasedSymbol<?> symbol, TypeStatement info);

    public FirDataFlowAnalyzer(FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components, DataFlowAnalyzerContext context) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(context, "context");
        this.components = components;
        this.context = context;
        this.any = components.getSession().getBuiltinTypes().getAnyType().getType();
        this.nullableNothing = components.getSession().getBuiltinTypes().getNullableNothingType().getType();
    }

    protected final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents getComponents() {
        return this.components;
    }

    /* JADX INFO: compiled from: FirDataFlowAnalyzer.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer$Companion;", "", "()V", "createFirDataFlowAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "dataFlowAnalyzerContext", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowAnalyzerContext;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirDataFlowAnalyzer createFirDataFlowAnalyzer(FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components, DataFlowAnalyzerContext dataFlowAnalyzerContext) {
            Intrinsics.checkNotNullParameter(components, "components");
            Intrinsics.checkNotNullParameter(dataFlowAnalyzerContext, "dataFlowAnalyzerContext");
            return new FirDataFlowAnalyzer$Companion$createFirDataFlowAnalyzer$1(components, dataFlowAnalyzerContext);
        }
    }

    private final ControlFlowGraphBuilder getGraphBuilder() {
        return this.context.getGraphBuilder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VariableStorageImpl getVariableStorage() {
        return this.context.getVariableStorage();
    }

    public final boolean isAccessToUnstableLocalVariable(FirExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return this.context.getVariableAssignmentAnalyzer().isAccessToUnstableLocalVariable(expression);
    }

    public Pair<PropertyStability, List<ConeKotlinType>> getTypeUsingSmartcastInfo(FirExpression expression) {
        TypeStatement typeStatement;
        Set<ConeKotlinType> exactType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        PersistentFlow flow = getGraphBuilder().getLastNode().getFlow();
        RealVariable realVariableWithoutUnwrappingAlias = getVariableStorage().getRealVariableWithoutUnwrappingAlias(flow, expression);
        if (realVariableWithoutUnwrappingAlias != null && (typeStatement = flow.getTypeStatement(realVariableWithoutUnwrappingAlias)) != null && (exactType = typeStatement.getExactType()) != null) {
            Set<ConeKotlinType> set = exactType;
            if (set.isEmpty()) {
                set = null;
            }
            Set<ConeKotlinType> set2 = set;
            if (set2 != null) {
                return TuplesKt.to(realVariableWithoutUnwrappingAlias.getStability(), CollectionsKt.toMutableList((Collection) set2));
            }
        }
        return null;
    }

    public final Collection<FirAnonymousFunctionReturnExpressionInfo> returnExpressionsOfAnonymousFunctionOrNull(FirAnonymousFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return getGraphBuilder().returnExpressionsOfAnonymousFunction(function);
    }

    public final Collection<FirAnonymousFunctionReturnExpressionInfo> returnExpressionsOfAnonymousFunction(FirAnonymousFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        Collection<FirAnonymousFunctionReturnExpressionInfo> collectionReturnExpressionsOfAnonymousFunctionOrNull = returnExpressionsOfAnonymousFunctionOrNull(function);
        if (collectionReturnExpressionsOfAnonymousFunctionOrNull != null) {
            return collectionReturnExpressionsOfAnonymousFunctionOrNull;
        }
        throw new IllegalStateException(("anonymous function " + UtilsKt.render(function) + " not analyzed").toString());
    }

    public final void enterFunction(final FirFunction function) {
        Pair<LocalFunctionDeclarationNode, FunctionEnterNode> pairEnterFunction;
        Intrinsics.checkNotNullParameter(function, "function");
        if (function instanceof FirDefaultPropertyAccessor) {
            return;
        }
        if (function instanceof FirAnonymousFunction) {
            pairEnterFunction = TuplesKt.to(null, getGraphBuilder().enterAnonymousFunction((FirAnonymousFunction) function));
        } else {
            pairEnterFunction = getGraphBuilder().enterFunction(function);
        }
        LocalFunctionDeclarationNode localFunctionDeclarationNodeComponent1 = pairEnterFunction.component1();
        FunctionEnterNode functionEnterNodeComponent2 = pairEnterFunction.component2();
        if (localFunctionDeclarationNodeComponent1 != null) {
            mergeIncomingFlow$default(this, localFunctionDeclarationNodeComponent1, null, 1, null);
        }
        mergeIncomingFlow(functionEnterNodeComponent2, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.enterFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                EventOccurrencesRange invocationKind;
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirFunction firFunction = function;
                if ((firFunction instanceof FirAnonymousFunction) && (invocationKind = ((FirAnonymousFunction) firFunction).getInvocationKind()) != null && EventOccurrencesRangeKt.canBeRevisited(invocationKind)) {
                    this.enterRepeatableStatement(flow, function);
                }
            }
        });
        this.context.getVariableAssignmentAnalyzer().enterFunction(function);
    }

    public final FirControlFlowGraphReference exitFunction(FirFunction function) {
        EventOccurrencesRange invocationKind;
        Intrinsics.checkNotNullParameter(function, "function");
        if (function instanceof FirDefaultPropertyAccessor) {
            return null;
        }
        this.context.getVariableAssignmentAnalyzer().exitFunction();
        boolean z = function instanceof FirAnonymousFunction;
        if (z && (invocationKind = ((FirAnonymousFunction) function).getInvocationKind()) != null && EventOccurrencesRangeKt.canBeRevisited(invocationKind)) {
            exitRepeatableStatement(function);
        }
        if (z) {
            Triple<FunctionExitNode, PostponedLambdaExitNode, ControlFlowGraph> tripleExitAnonymousFunction = getGraphBuilder().exitAnonymousFunction((FirAnonymousFunction) function);
            FunctionExitNode functionExitNodeComponent1 = tripleExitAnonymousFunction.component1();
            PostponedLambdaExitNode postponedLambdaExitNodeComponent2 = tripleExitAnonymousFunction.component2();
            ControlFlowGraph controlFlowGraphComponent3 = tripleExitAnonymousFunction.component3();
            mergeIncomingFlow$default(this, functionExitNodeComponent1, null, 1, null);
            if (postponedLambdaExitNodeComponent2 != null) {
                mergeIncomingFlow$default(this, postponedLambdaExitNodeComponent2, null, 1, null);
            }
            resetReceivers();
            return new FirControlFlowGraphReferenceImpl(controlFlowGraphComponent3, null, 2, null);
        }
        Pair<FunctionExitNode, ControlFlowGraph> pairExitFunction = getGraphBuilder().exitFunction(function);
        FunctionExitNode functionExitNodeComponent2 = pairExitFunction.component1();
        ControlFlowGraph controlFlowGraphComponent2 = pairExitFunction.component2();
        mergeIncomingFlow$default(this, functionExitNodeComponent2, null, 1, null);
        if (!getGraphBuilder().isTopLevel()) {
            Iterator<FirValueParameter> it = function.getValueParameters().iterator();
            while (it.hasNext()) {
                getVariableStorage().removeRealVariable(it.next().getSymbol());
            }
        }
        DataFlowInfo dataFlowInfo = new DataFlowInfo(getVariableStorage());
        if (getGraphBuilder().isTopLevel()) {
            this.context.reset();
        } else {
            resetReceivers();
        }
        return new FirControlFlowGraphReferenceImpl(controlFlowGraphComponent2, dataFlowInfo);
    }

    public final void enterAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        AnonymousFunctionExpressionNode anonymousFunctionExpressionNodeEnterAnonymousFunctionExpression = getGraphBuilder().enterAnonymousFunctionExpression(anonymousFunctionExpression);
        if (anonymousFunctionExpressionNodeEnterAnonymousFunctionExpression != null) {
            mergeIncomingFlow$default(this, anonymousFunctionExpressionNodeEnterAnonymousFunctionExpression, null, 1, null);
        }
    }

    public final void enterClass(FirClass klass, boolean buildGraph) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Pair<CFGNode<?>, ClassEnterNode> pairEnterClass = getGraphBuilder().enterClass(klass, buildGraph);
        CFGNode<?> cFGNodeComponent1 = pairEnterClass.component1();
        ClassEnterNode classEnterNodeComponent2 = pairEnterClass.component2();
        if (cFGNodeComponent1 != null) {
            mergeIncomingFlow$default(this, cFGNodeComponent1, null, 1, null);
        }
        if (classEnterNodeComponent2 != null) {
            mergeIncomingFlow$default(this, classEnterNodeComponent2, null, 1, null);
        }
        this.context.getVariableAssignmentAnalyzer().enterClass(klass);
    }

    public final ControlFlowGraph exitClass() {
        this.context.getVariableAssignmentAnalyzer().exitClass();
        Pair<ClassExitNode, ControlFlowGraph> pairExitClass = getGraphBuilder().exitClass();
        ClassExitNode classExitNodeComponent1 = pairExitClass.component1();
        ControlFlowGraph controlFlowGraphComponent2 = pairExitClass.component2();
        if (classExitNodeComponent1 != null) {
            mergeIncomingFlow$default(this, classExitNodeComponent1, null, 1, null);
        } else {
            resetReceivers();
        }
        return controlFlowGraphComponent2;
    }

    public final void exitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        AnonymousObjectExpressionExitNode anonymousObjectExpressionExitNodeExitAnonymousObjectExpression = getGraphBuilder().exitAnonymousObjectExpression(anonymousObjectExpression);
        if (anonymousObjectExpressionExitNodeExitAnonymousObjectExpression != null) {
            mergeIncomingFlow$default(this, anonymousObjectExpressionExitNodeExitAnonymousObjectExpression, null, 1, null);
        }
    }

    public final void enterScript(FirScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        mergeIncomingFlow$default(this, getGraphBuilder().enterScript(script), null, 1, null);
    }

    public final ControlFlowGraph exitScript() {
        Pair<ScriptExitNode, ControlFlowGraph> pairExitScript = getGraphBuilder().exitScript();
        ScriptExitNode scriptExitNodeComponent1 = pairExitScript.component1();
        ControlFlowGraph controlFlowGraphComponent2 = pairExitScript.component2();
        mergeIncomingFlow$default(this, scriptExitNodeComponent1, null, 1, null);
        return controlFlowGraphComponent2;
    }

    public final void enterCodeFragment(final FirCodeFragment codeFragment) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        mergeIncomingFlow(getGraphBuilder().enterCodeFragment(codeFragment), new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.enterCodeFragment.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirCodeFragmentContext codeFragmentContext = DeclarationUtilsKt.getCodeFragmentContext(codeFragment);
                Map<FirBasedSymbol<?>, Set<ConeKotlinType>> variables = codeFragmentContext != null ? codeFragmentContext.getVariables() : null;
                if (variables == null) {
                    variables = MapsKt.emptyMap();
                }
                for (Map.Entry<FirBasedSymbol<?>, Set<ConeKotlinType>> entry : variables.entrySet()) {
                    FirBasedSymbol<?> key = entry.getKey();
                    Set<ConeKotlinType> value = entry.getValue();
                    DataFlowVariable orCreateIfReal = this.getVariableStorage().getOrCreateIfReal(flow, key.getFir());
                    RealVariable realVariable = orCreateIfReal instanceof RealVariable ? (RealVariable) orCreateIfReal : null;
                    if (realVariable != null) {
                        this.addTypeStatement(flow, new PersistentTypeStatement(realVariable, ExtensionsKt.toPersistentSet(value)));
                    }
                }
            }
        });
    }

    public final ControlFlowGraph exitCodeFragment() {
        Pair<CodeFragmentExitNode, ControlFlowGraph> pairExitCodeFragment = getGraphBuilder().exitCodeFragment();
        CodeFragmentExitNode codeFragmentExitNodeComponent1 = pairExitCodeFragment.component1();
        ControlFlowGraph controlFlowGraphComponent2 = pairExitCodeFragment.component2();
        mergeIncomingFlow$default(this, codeFragmentExitNodeComponent1, null, 1, null);
        return controlFlowGraphComponent2;
    }

    public final void enterValueParameter(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Pair<EnterValueParameterNode, EnterDefaultArgumentsNode> pairEnterValueParameter = getGraphBuilder().enterValueParameter(valueParameter);
        if (pairEnterValueParameter == null) {
            return;
        }
        EnterValueParameterNode enterValueParameterNodeComponent1 = pairEnterValueParameter.component1();
        EnterDefaultArgumentsNode enterDefaultArgumentsNodeComponent2 = pairEnterValueParameter.component2();
        mergeIncomingFlow$default(this, enterValueParameterNodeComponent1, null, 1, null);
        mergeIncomingFlow$default(this, enterDefaultArgumentsNodeComponent2, null, 1, null);
    }

    public final ControlFlowGraph exitValueParameter(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Triple<ExitDefaultArgumentsNode, ExitValueParameterNode, ControlFlowGraph> tripleExitValueParameter = getGraphBuilder().exitValueParameter(valueParameter);
        if (tripleExitValueParameter == null) {
            return null;
        }
        ExitDefaultArgumentsNode exitDefaultArgumentsNodeComponent1 = tripleExitValueParameter.component1();
        ExitValueParameterNode exitValueParameterNodeComponent2 = tripleExitValueParameter.component2();
        ControlFlowGraph controlFlowGraphComponent3 = tripleExitValueParameter.component3();
        mergeIncomingFlow$default(this, exitDefaultArgumentsNodeComponent1, null, 1, null);
        mergeIncomingFlow$default(this, exitValueParameterNodeComponent2, null, 1, null);
        return controlFlowGraphComponent3;
    }

    public final void enterProperty(FirProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        PropertyInitializerEnterNode propertyInitializerEnterNodeEnterProperty = getGraphBuilder().enterProperty(property);
        if (propertyInitializerEnterNodeEnterProperty != null) {
            mergeIncomingFlow$default(this, propertyInitializerEnterNodeEnterProperty, null, 1, null);
        }
    }

    public final ControlFlowGraph exitProperty(FirProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        Pair<PropertyInitializerExitNode, ControlFlowGraph> pairExitProperty = getGraphBuilder().exitProperty(property);
        if (pairExitProperty == null) {
            return null;
        }
        PropertyInitializerExitNode propertyInitializerExitNodeComponent1 = pairExitProperty.component1();
        ControlFlowGraph controlFlowGraphComponent2 = pairExitProperty.component2();
        mergeIncomingFlow$default(this, propertyInitializerExitNodeComponent1, null, 1, null);
        return controlFlowGraphComponent2;
    }

    public final void enterField(FirField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        FieldInitializerEnterNode fieldInitializerEnterNodeEnterField = getGraphBuilder().enterField(field);
        if (fieldInitializerEnterNodeEnterField != null) {
            mergeIncomingFlow$default(this, fieldInitializerEnterNodeEnterField, null, 1, null);
        }
    }

    public final ControlFlowGraph exitField(FirField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        Pair<FieldInitializerExitNode, ControlFlowGraph> pairExitField = getGraphBuilder().exitField(field);
        if (pairExitField == null) {
            return null;
        }
        FieldInitializerExitNode fieldInitializerExitNodeComponent1 = pairExitField.component1();
        ControlFlowGraph controlFlowGraphComponent2 = pairExitField.component2();
        mergeIncomingFlow$default(this, fieldInitializerExitNodeComponent1, null, 1, null);
        return controlFlowGraphComponent2;
    }

    public final void enterDelegateExpression() {
        getGraphBuilder().enterDelegateExpression();
    }

    public final void exitDelegateExpression(FirExpression fir) {
        Intrinsics.checkNotNullParameter(fir, "fir");
        mergeIncomingFlow$default(this, getGraphBuilder().exitDelegateExpression(fir), null, 1, null);
    }

    public final void enterBlock(FirBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        mergeIncomingFlow$default(this, getGraphBuilder().enterBlock(block), null, 1, null);
    }

    public final void exitBlock(FirBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        mergeIncomingFlow$default(this, getGraphBuilder().exitBlock(block), null, 1, null);
    }

    public final void exitTypeOperatorCall(final FirTypeOperatorCall typeOperatorCall) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        mergeIncomingFlow(getGraphBuilder().exitTypeOperatorCall(typeOperatorCall), new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitTypeOperatorCall.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                if (FirOperation.INSTANCE.getTYPES().contains(typeOperatorCall.getOperation())) {
                    this.addTypeOperatorStatements(flow, typeOperatorCall);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addTypeOperatorStatements(MutableFlow flow, FirTypeOperatorCall typeOperatorCall) {
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(typeOperatorCall.getConversionTypeRef());
        FirTypeOperatorCall firTypeOperatorCall = typeOperatorCall;
        DataFlowVariable orCreateIfReal = getVariableStorage().getOrCreateIfReal(flow, (FirExpression) CollectionsKt.first((List) firTypeOperatorCall.getArgumentList().getArguments()));
        if (orCreateIfReal == null) {
            return;
        }
        FirOperation operation = typeOperatorCall.getOperation();
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i == 1 || i == 2) {
            boolean z = operation == FirOperation.IS;
            if (Intrinsics.areEqual(coneType, this.nullableNothing)) {
                processEqNull(flow, typeOperatorCall, (FirExpression) CollectionsKt.first((List) firTypeOperatorCall.getArgumentList().getArguments()), z);
                return;
            }
            if (Intrinsics.areEqual(coneType, this.any)) {
                processEqNull(flow, typeOperatorCall, (FirExpression) CollectionsKt.first((List) firTypeOperatorCall.getArgumentList().getArguments()), !z);
                return;
            }
            SyntheticVariable syntheticVariableCreateSynthetic = getVariableStorage().createSynthetic(typeOperatorCall);
            if (ModelKt.isReal(orCreateIfReal)) {
                addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, z), ModelKt.typeEq((RealVariable) orCreateIfReal, coneType)));
            }
            if (!FirTypeUtilsKt.getCanBeNull(coneType)) {
                addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, z), ModelKt.notEq(orCreateIfReal, null)));
                return;
            } else {
                if (ConeTypeUtilsKt.isMarkedNullable(coneType)) {
                    addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, !z), ModelKt.notEq(orCreateIfReal, null)));
                    return;
                }
                return;
            }
        }
        if (i != 3) {
            if (i == 4) {
                SyntheticVariable syntheticVariableCreateSynthetic2 = getVariableStorage().createSynthetic(typeOperatorCall);
                addImplication(flow, ModelKt.implies(ModelKt.notEq(syntheticVariableCreateSynthetic2, null), ModelKt.notEq(orCreateIfReal, null)));
                if (ModelKt.isReal(orCreateIfReal)) {
                    addImplication(flow, ModelKt.implies(ModelKt.notEq(syntheticVariableCreateSynthetic2, null), ModelKt.typeEq((RealVariable) orCreateIfReal, coneType)));
                    return;
                }
                return;
            }
            throw new IllegalStateException();
        }
        if (ModelKt.isReal(orCreateIfReal)) {
            addTypeStatement(flow, ModelKt.typeEq((RealVariable) orCreateIfReal, coneType));
        }
        if (!FirTypeUtilsKt.getCanBeNull(coneType)) {
            commitOperationStatement(flow, ModelKt.notEq(orCreateIfReal, null));
            return;
        }
        SyntheticVariable syntheticVariableCreateSynthetic3 = getVariableStorage().createSynthetic(typeOperatorCall);
        addImplication(flow, ModelKt.implies(ModelKt.notEq(syntheticVariableCreateSynthetic3, null), ModelKt.notEq(orCreateIfReal, null)));
        addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic3, (Void) null), ModelKt.eq(orCreateIfReal, (Void) null)));
    }

    public final void exitComparisonExpressionCall(FirComparisonExpression comparisonExpression) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        mergeIncomingFlow$default(this, getGraphBuilder().exitComparisonExpression(comparisonExpression), null, 1, null);
    }

    public final void exitEqualityOperatorCall(final FirEqualityOperatorCall equalityOperatorCall) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        EqualityOperatorCallNode equalityOperatorCallNodeExitEqualityOperatorCall = getGraphBuilder().exitEqualityOperatorCall(equalityOperatorCall);
        final FirOperation operation = equalityOperatorCall.getOperation();
        FirEqualityOperatorCall firEqualityOperatorCall = equalityOperatorCall;
        final FirExpression firExpression = firEqualityOperatorCall.getArgumentList().getArguments().get(0);
        final FirExpression firExpression2 = firEqualityOperatorCall.getArgumentList().getArguments().get(1);
        FirExpression subject = firExpression instanceof FirWhenSubjectExpression ? ((FirWhenExpression) ((FirWhenSubjectExpression) firExpression).getWhenRef().getValue()).getSubject() : firExpression;
        FirConstExpression firConstExpression = subject instanceof FirConstExpression ? (FirConstExpression) subject : null;
        FirConstExpression firConstExpression2 = firExpression2 instanceof FirConstExpression ? (FirConstExpression) firExpression2 : null;
        boolean zAreEqual = Intrinsics.areEqual(firConstExpression != null ? firConstExpression.getKind() : null, ConstantValueKind.Null.INSTANCE);
        boolean zAreEqual2 = Intrinsics.areEqual(firConstExpression2 != null ? firConstExpression2.getKind() : null, ConstantValueKind.Null.INSTANCE);
        final FirConstExpression firConstExpression3 = firConstExpression;
        final FirConstExpression firConstExpression4 = firConstExpression2;
        final boolean z = zAreEqual || (ConeBuiltinTypeUtilsKt.isNullableNothing(UtilKt.getConeType(firExpression)) && !zAreEqual2);
        final boolean z2 = zAreEqual2 || (ConeBuiltinTypeUtilsKt.isNullableNothing(UtilKt.getConeType(firExpression2)) && !zAreEqual);
        mergeIncomingFlow(equalityOperatorCallNodeExitEqualityOperatorCall, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitEqualityOperatorCall.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirConstExpression<?> firConstExpression5 = firConstExpression3;
                if (firConstExpression5 == null || firConstExpression4 == null) {
                    if (z) {
                        this.processEqNull(flow, equalityOperatorCall, firExpression2, UtilKt.isEq(operation));
                        return;
                    }
                    if (z2) {
                        this.processEqNull(flow, equalityOperatorCall, firExpression, UtilKt.isEq(operation));
                        return;
                    }
                    if (firConstExpression5 != null) {
                        this.processEqConst(flow, equalityOperatorCall, firExpression2, firConstExpression5, UtilKt.isEq(operation));
                        return;
                    }
                    FirConstExpression<?> firConstExpression6 = firConstExpression4;
                    if (firConstExpression6 != null) {
                        this.processEqConst(flow, equalityOperatorCall, firExpression, firConstExpression6, UtilKt.isEq(operation));
                    } else {
                        this.processEq(flow, equalityOperatorCall, firExpression, firExpression2, operation);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processEqConst(MutableFlow flow, FirExpression expression, FirExpression operand, FirConstExpression<?> firConstExpression, boolean isEq) {
        if (Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.Null.INSTANCE)) {
            processEqNull(flow, expression, operand, isEq);
            return;
        }
        DataFlowVariable orCreateIfReal = getVariableStorage().getOrCreateIfReal(flow, operand);
        if (orCreateIfReal == null) {
            return;
        }
        SyntheticVariable syntheticVariableCreateSynthetic = getVariableStorage().createSynthetic(expression);
        if (Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.Boolean.INSTANCE) && ConeBuiltinTypeUtilsKt.isBooleanOrNullableBoolean(UtilKt.getConeType(operand))) {
            Object value = firConstExpression.getValue();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
            boolean zBooleanValue = ((Boolean) value).booleanValue();
            SyntheticVariable syntheticVariable = syntheticVariableCreateSynthetic;
            addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariable, isEq), ModelKt.eq(orCreateIfReal, zBooleanValue)));
            if (ConeBuiltinTypeUtilsKt.isBoolean(UtilKt.getConeType(operand))) {
                addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariable, !isEq), ModelKt.eq(orCreateIfReal, !zBooleanValue)));
                return;
            }
            return;
        }
        addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, isEq), ModelKt.notEq(orCreateIfReal, null)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processEqNull(MutableFlow flow, FirExpression expression, FirExpression operand, boolean isEq) {
        DataFlowVariable orCreateIfReal = getVariableStorage().getOrCreateIfReal(flow, operand);
        if (orCreateIfReal == null) {
            return;
        }
        SyntheticVariable syntheticVariableCreateSynthetic = getVariableStorage().createSynthetic(expression);
        addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, isEq), ModelKt.eq(orCreateIfReal, (Void) null)));
        addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, !isEq), ModelKt.notEq(orCreateIfReal, null)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processEq(MutableFlow flow, FirExpression expression, FirExpression leftOperand, FirExpression rightOperand, FirOperation operation) {
        boolean zIsEq = UtilKt.isEq(operation);
        ConeKotlinType coneType = UtilKt.getConeType(leftOperand);
        ConeKotlinType coneType2 = UtilKt.getConeType(rightOperand);
        boolean zIsMarkedNullable = ConeTypeUtilsKt.isMarkedNullable(coneType);
        boolean zIsMarkedNullable2 = ConeTypeUtilsKt.isMarkedNullable(coneType2);
        if (zIsMarkedNullable && zIsMarkedNullable2) {
            return;
        }
        MutableFlow mutableFlow = flow;
        DataFlowVariable orCreateIfReal = getVariableStorage().getOrCreateIfReal(mutableFlow, leftOperand);
        DataFlowVariable orCreateIfReal2 = getVariableStorage().getOrCreateIfReal(mutableFlow, rightOperand);
        if (orCreateIfReal == null && orCreateIfReal2 == null) {
            return;
        }
        SyntheticVariable syntheticVariableCreateSynthetic = getVariableStorage().createSynthetic(expression);
        if (zIsMarkedNullable || zIsMarkedNullable2) {
            DataFlowVariable dataFlowVariable = zIsMarkedNullable ? orCreateIfReal : orCreateIfReal2;
            if (dataFlowVariable != null) {
                addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, zIsEq), ModelKt.notEq(dataFlowVariable, null)));
            }
        }
        boolean z = orCreateIfReal instanceof RealVariable;
        if (z || (orCreateIfReal2 instanceof RealVariable)) {
            if ((operation == FirOperation.EQ || operation == FirOperation.NOT_EQ) && hasOverriddenEquals(coneType)) {
                return;
            }
            if (z) {
                addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, zIsEq), ModelKt.typeEq((RealVariable) orCreateIfReal, coneType2)));
            }
            if (orCreateIfReal2 instanceof RealVariable) {
                addImplication(flow, ModelKt.implies(ModelKt.eq(syntheticVariableCreateSynthetic, zIsEq), ModelKt.typeEq((RealVariable) orCreateIfReal2, coneType)));
            }
        }
    }

    private final boolean hasOverriddenEquals(ConeKotlinType type) {
        FirSession session = this.components.getSession();
        List<FirClassSymbol<?>> listCollectSymbolsForType = SupertypeUtilsKt.collectSymbolsForType(type, session);
        List<FirClassSymbol<?>> list = listCollectSymbolsForType;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (hasEqualsOverride((FirClassSymbol) it.next(), session, true)) {
                    return true;
                }
            }
        }
        List listLookupSuperTypes$default = SupertypeUtilsKt.lookupSuperTypes$default((List) listCollectSymbolsForType, false, true, session, false, (SupertypeSupplier) null, 32, (Object) null);
        ArrayList arrayList = new ArrayList();
        Iterator it2 = listLookupSuperTypes$default.iterator();
        while (it2.hasNext()) {
            FirClassLikeSymbol<?> symbol = TypeUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) it2.next(), session, null, 2, null), session);
            FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
            if (firRegularClassSymbol != null) {
                arrayList.add(firRegularClassSymbol);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                if (hasEqualsOverride((FirRegularClassSymbol) it3.next(), session, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean hasEqualsOverride(FirClassSymbol<?> firClassSymbol, FirSession firSession, boolean z) {
        FirResolvedDeclarationStatus resolvedStatus = firClassSymbol.getResolvedStatus();
        if ((z && resolvedStatus.getModality() != Modality.FINAL) || resolvedStatus.isExpect()) {
            return true;
        }
        ClassId classId = firClassSymbol.getClassId();
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getAny()) ? true : Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getString())) {
            return false;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getFloat()) ? true : Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getDouble())) {
            return true;
        }
        List<FirNamedFunctionSymbol> functions = FirScopeKt.getFunctions(FirKotlinScopeProviderKt.unsubstitutedScope(firClassSymbol, firSession, this.components.getScopeSession(), false, FirResolvePhase.STATUS), OperatorNameConventions.EQUALS);
        if (!(functions instanceof Collection) || !functions.isEmpty()) {
            for (FirNamedFunctionSymbol firNamedFunctionSymbol : functions) {
                FirNamedFunctionSymbol firNamedFunctionSymbol2 = firNamedFunctionSymbol;
                if (!ClassMembersKt.isSubstitutionOrIntersectionOverride(firNamedFunctionSymbol2) && org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.isEquals((FirSimpleFunction) firNamedFunctionSymbol.getFir(), firSession) && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firNamedFunctionSymbol2), firClassSymbol.getLookupTag())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void enterJump(FirJump<?> jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        getGraphBuilder().enterJump(jump);
    }

    public final void exitJump(FirJump<?> jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        mergeIncomingFlow$default(this, getGraphBuilder().exitJump(jump), null, 1, null);
    }

    public final void enterCheckNotNullCall() {
        getGraphBuilder().enterCall();
    }

    public final void exitCheckNotNullCall(final FirCheckNotNullCall checkNotNullCall, boolean callCompleted) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        mergeIncomingFlow(getGraphBuilder().exitCheckNotNullCall(checkNotNullCall, callCompleted), new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitCheckNotNullCall.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                DataFlowVariable orCreateIfReal = FirDataFlowAnalyzer.this.getVariableStorage().getOrCreateIfReal(flow, (FirExpression) CollectionsKt.first((List) checkNotNullCall.getArgumentList().getArguments()));
                if (orCreateIfReal == null) {
                    return;
                }
                FirDataFlowAnalyzer.this.commitOperationStatement(flow, ModelKt.notEq(orCreateIfReal, null));
            }
        });
    }

    public final void enterWhenExpression(FirWhenExpression whenExpression) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        mergeIncomingFlow$default(this, getGraphBuilder().enterWhenExpression(whenExpression), null, 1, null);
    }

    public final void enterWhenBranchCondition(FirWhenBranch whenBranch) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        mergeWhenBranchEntryFlow(getGraphBuilder().enterWhenBranchCondition(whenBranch));
    }

    private final void mergeWhenBranchEntryFlow(final CFGNode<?> cFGNode) {
        mergeIncomingFlow(cFGNode, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.mergeWhenBranchEntryFlow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                DataFlowVariable dataFlowVariable;
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) cFGNode.getPreviousNodes());
                WhenBranchConditionExitNode whenBranchConditionExitNode = objSingleOrNull instanceof WhenBranchConditionExitNode ? (WhenBranchConditionExitNode) objSingleOrNull : null;
                if (whenBranchConditionExitNode == null) {
                    return;
                }
                FirExpression condition = whenBranchConditionExitNode.getFir().getCondition();
                if (ConeBuiltinTypeUtilsKt.isBoolean(UtilKt.getConeType(condition)) && (dataFlowVariable = this.getVariableStorage().get(flow, condition)) != null) {
                    this.commitOperationStatement(flow, ModelKt.eq(dataFlowVariable, false));
                }
            }
        });
    }

    public final void exitWhenBranchCondition(final FirWhenBranch whenBranch) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        Pair<WhenBranchConditionExitNode, WhenBranchResultEnterNode> pairExitWhenBranchCondition = getGraphBuilder().exitWhenBranchCondition(whenBranch);
        WhenBranchConditionExitNode whenBranchConditionExitNodeComponent1 = pairExitWhenBranchCondition.component1();
        WhenBranchResultEnterNode whenBranchResultEnterNodeComponent2 = pairExitWhenBranchCondition.component2();
        mergeIncomingFlow$default(this, whenBranchConditionExitNodeComponent1, null, 1, null);
        mergeIncomingFlow(whenBranchResultEnterNodeComponent2, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitWhenBranchCondition.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                DataFlowVariable dataFlowVariable;
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                if (!ConeBuiltinTypeUtilsKt.isBoolean(UtilKt.getConeType(whenBranch.getCondition())) || (dataFlowVariable = this.getVariableStorage().get(flow, whenBranch.getCondition())) == null) {
                    return;
                }
                this.commitOperationStatement(flow, ModelKt.eq(dataFlowVariable, true));
            }
        });
    }

    public final void exitWhenBranchResult(FirWhenBranch whenBranch) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        mergeIncomingFlow$default(this, getGraphBuilder().exitWhenBranchResult(whenBranch), null, 1, null);
    }

    public final void exitWhenExpression(FirWhenExpression whenExpression, boolean callCompleted) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Pair<WhenExitNode, WhenSyntheticElseBranchNode> pairExitWhenExpression = getGraphBuilder().exitWhenExpression(whenExpression, callCompleted);
        WhenExitNode whenExitNodeComponent1 = pairExitWhenExpression.component1();
        WhenSyntheticElseBranchNode whenSyntheticElseBranchNodeComponent2 = pairExitWhenExpression.component2();
        if (whenSyntheticElseBranchNodeComponent2 != null) {
            mergeWhenBranchEntryFlow(whenSyntheticElseBranchNodeComponent2);
        }
        mergeIncomingFlow$default(this, whenExitNodeComponent1, null, 1, null);
    }

    public final void exitWhenSubjectExpression(FirWhenSubjectExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        mergeIncomingFlow$default(this, getGraphBuilder().exitWhenSubjectExpression(expression), null, 1, null);
    }

    public final void enterWhileLoop(final FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Pair<LoopEnterNode, LoopConditionEnterNode> pairEnterWhileLoop = getGraphBuilder().enterWhileLoop(loop);
        LoopEnterNode loopEnterNodeComponent1 = pairEnterWhileLoop.component1();
        LoopConditionEnterNode loopConditionEnterNodeComponent2 = pairEnterWhileLoop.component2();
        mergeIncomingFlow$default(this, loopEnterNodeComponent1, null, 1, null);
        mergeIncomingFlow(loopConditionEnterNodeComponent2, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.enterWhileLoop.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirDataFlowAnalyzer.this.enterRepeatableStatement(flow, loop);
            }
        });
    }

    public final void exitWhileLoopCondition(final FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Pair<LoopConditionExitNode, LoopBlockEnterNode> pairExitWhileLoopCondition = getGraphBuilder().exitWhileLoopCondition(loop);
        LoopConditionExitNode loopConditionExitNodeComponent1 = pairExitWhileLoopCondition.component1();
        LoopBlockEnterNode loopBlockEnterNodeComponent2 = pairExitWhileLoopCondition.component2();
        mergeIncomingFlow$default(this, loopConditionExitNodeComponent1, null, 1, null);
        mergeIncomingFlow(loopBlockEnterNodeComponent2, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitWhileLoopCondition.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                DataFlowVariable dataFlowVariable;
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                if (!ConeBuiltinTypeUtilsKt.isBoolean(UtilKt.getConeType(loop.getCondition())) || (dataFlowVariable = this.getVariableStorage().get(flow, loop.getCondition())) == null) {
                    return;
                }
                this.commitOperationStatement(flow, ModelKt.eq(dataFlowVariable, true));
            }
        });
    }

    public final void exitWhileLoop(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Triple<LoopConditionEnterNode, LoopBlockExitNode, LoopExitNode> tripleExitWhileLoop = getGraphBuilder().exitWhileLoop(loop);
        final LoopConditionEnterNode loopConditionEnterNodeComponent1 = tripleExitWhileLoop.component1();
        LoopBlockExitNode loopBlockExitNodeComponent2 = tripleExitWhileLoop.component2();
        final LoopExitNode loopExitNodeComponent3 = tripleExitWhileLoop.component3();
        mergeIncomingFlow$default(this, loopBlockExitNodeComponent2, null, 1, null);
        mergeIncomingFlow(loopExitNodeComponent3, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitWhileLoop.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath path, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirDataFlowAnalyzer.this.processWhileLoopExit(path, flow, loopExitNodeComponent3, loopConditionEnterNodeComponent1);
                FirDataFlowAnalyzer firDataFlowAnalyzer = FirDataFlowAnalyzer.this;
                LoopExitNode loopExitNode = loopExitNodeComponent3;
                CFGNode<?> firstPreviousNode = CFGNodeKt.getFirstPreviousNode(loopExitNode);
                Intrinsics.checkNotNull(firstPreviousNode, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.LoopConditionExitNode");
                firDataFlowAnalyzer.processLoopExit(flow, loopExitNode, (LoopConditionExitNode) firstPreviousNode);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processWhileLoopExit(final FlowPath path, MutableFlow flow, LoopExitNode node, LoopConditionEnterNode conditionEnterNode) {
        List<RealVariable> listExitRepeatableStatement = exitRepeatableStatement(node.getFir());
        List<RealVariable> list = listExitRepeatableStatement;
        if (list == null || list.isEmpty()) {
            return;
        }
        LoopConditionEnterNode loopConditionEnterNode = conditionEnterNode;
        PersistentFlow flow2 = getFlow(loopConditionEnterNode, path);
        List list2 = SequencesKt.toList(SequencesKt.map(CFGNodeKt.getPreviousLiveNodes(loopConditionEnterNode), new Function1<CFGNode<?>, PersistentFlow>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer$processWhileLoopExit$loopEnterAndContinueFlows$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PersistentFlow invoke(CFGNode<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getFlow(it, path);
            }
        }));
        List list3 = SequencesKt.toList(SequencesKt.map(CFGNodeKt.getPreviousLiveNodes(node), new Function1<CFGNode<?>, PersistentFlow>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer$processWhileLoopExit$conditionExitAndBreakFlows$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PersistentFlow invoke(CFGNode<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getFlow(it, path);
            }
        }));
        for (RealVariable realVariable : listExitRepeatableStatement) {
            LogicSystem logicSystem = getLogicSystem();
            List list4 = list2;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            Iterator it = list4.iterator();
            while (true) {
                if (it.hasNext()) {
                    TypeStatement typeStatement = ((PersistentFlow) it.next()).getTypeStatement(realVariable);
                    if (typeStatement == null) {
                        break;
                    } else {
                        arrayList.add(typeStatement);
                    }
                } else {
                    TypeStatement typeStatementOr = logicSystem.or(arrayList);
                    if (typeStatementOr == null) {
                        break;
                    }
                    if (!typeStatementOr.isNotEmpty()) {
                        typeStatementOr = null;
                    }
                    if (typeStatementOr == null) {
                        break;
                    }
                    LogicSystem logicSystem2 = getLogicSystem();
                    List list5 = list3;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
                    Iterator it2 = list5.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            TypeStatement typeStatementOr2 = logicSystem2.or(arrayList2);
                            if (typeStatementOr2 != null) {
                                addTypeStatement(flow, typeStatementOr2);
                                break;
                            }
                            break;
                        }
                        PersistentFlow persistentFlow = (PersistentFlow) it2.next();
                        TypeStatement typeStatement2 = persistentFlow.getTypeStatement(realVariable);
                        if (!getLogicSystem().isSameValueIn(flow2, persistentFlow, realVariable)) {
                            if (typeStatement2 == null) {
                                break;
                            }
                        } else {
                            typeStatement2 = getLogicSystem().and(typeStatement2, typeStatementOr);
                        }
                        arrayList2.add(typeStatement2);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processLoopExit(MutableFlow flow, LoopExitNode node, LoopConditionExitNode conditionExitNode) {
        int i;
        DataFlowVariable dataFlowVariable;
        if (conditionExitNode.getIsDead()) {
            return;
        }
        List<CFGNode<?>> previousNodes = node.getPreviousNodes();
        if ((previousNodes instanceof Collection) && previousNodes.isEmpty()) {
            i = 0;
        } else {
            Iterator<T> it = previousNodes.iterator();
            i = 0;
            while (it.hasNext()) {
                if (!((CFGNode) it.next()).getIsDead() && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        if (i <= 1 && ConeBuiltinTypeUtilsKt.isBoolean(UtilKt.getConeType(conditionExitNode.getFir())) && (dataFlowVariable = getVariableStorage().get(flow, conditionExitNode.getFir())) != null) {
            commitOperationStatement(flow, ModelKt.eq(dataFlowVariable, false));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void enterRepeatableStatement(MutableFlow flow, FirStatement statement) {
        Set<Name> setEnterCapturingStatement = this.context.getPreliminaryLoopVisitor().enterCapturingStatement(statement);
        if (setEnterCapturingStatement.isEmpty()) {
            return;
        }
        Collection<RealVariable> collectionValues = getVariableStorage().getRealVariables().values();
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionValues) {
            Identifier identifier = ((RealVariable) obj).getIdentifier();
            FirBasedSymbol<?> symbol = identifier.getSymbol();
            if (identifier.getDispatchReceiver() == null && identifier.getExtensionReceiver() == null && (symbol instanceof FirPropertySymbol)) {
                FirPropertySymbol firPropertySymbol = (FirPropertySymbol) symbol;
                if (firPropertySymbol.isVar() && setEnterCapturingStatement.contains(firPropertySymbol.getName())) {
                    arrayList.add(obj);
                }
            }
        }
        ArrayList arrayList2 = arrayList;
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            getLogicSystem().recordNewAssignment(flow, (RealVariable) it.next(), this.context.newAssignmentIndex());
        }
        this.context.getVariablesClearedBeforeLoop().push(arrayList2);
    }

    private final List<RealVariable> exitRepeatableStatement(FirStatement statement) {
        if (this.context.getPreliminaryLoopVisitor().exitCapturingStatement(statement).isEmpty()) {
            return null;
        }
        return this.context.getVariablesClearedBeforeLoop().pop();
    }

    public final void enterDoWhileLoop(final FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Pair<LoopEnterNode, LoopBlockEnterNode> pairEnterDoWhileLoop = getGraphBuilder().enterDoWhileLoop(loop);
        LoopEnterNode loopEnterNodeComponent1 = pairEnterDoWhileLoop.component1();
        LoopBlockEnterNode loopBlockEnterNodeComponent2 = pairEnterDoWhileLoop.component2();
        mergeIncomingFlow(loopEnterNodeComponent1, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.enterDoWhileLoop.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirDataFlowAnalyzer.this.enterRepeatableStatement(flow, loop);
            }
        });
        mergeIncomingFlow$default(this, loopBlockEnterNodeComponent2, null, 1, null);
    }

    public final void enterDoWhileLoopCondition(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Pair<LoopBlockExitNode, LoopConditionEnterNode> pairEnterDoWhileLoopCondition = getGraphBuilder().enterDoWhileLoopCondition(loop);
        LoopBlockExitNode loopBlockExitNodeComponent1 = pairEnterDoWhileLoopCondition.component1();
        LoopConditionEnterNode loopConditionEnterNodeComponent2 = pairEnterDoWhileLoopCondition.component2();
        mergeIncomingFlow$default(this, loopBlockExitNodeComponent1, null, 1, null);
        mergeIncomingFlow$default(this, loopConditionEnterNodeComponent2, null, 1, null);
    }

    public final void exitDoWhileLoop(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Pair<LoopConditionExitNode, LoopExitNode> pairExitDoWhileLoop = getGraphBuilder().exitDoWhileLoop(loop);
        final LoopConditionExitNode loopConditionExitNodeComponent1 = pairExitDoWhileLoop.component1();
        final LoopExitNode loopExitNodeComponent2 = pairExitDoWhileLoop.component2();
        mergeIncomingFlow$default(this, loopConditionExitNodeComponent1, null, 1, null);
        mergeIncomingFlow(loopExitNodeComponent2, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitDoWhileLoop.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirDataFlowAnalyzer.this.processLoopExit(flow, loopExitNodeComponent2, loopConditionExitNodeComponent1);
            }
        });
        exitRepeatableStatement(loop);
    }

    public final void enterTryExpression(FirTryExpression tryExpression) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        Pair<TryExpressionEnterNode, TryMainBlockEnterNode> pairEnterTryExpression = getGraphBuilder().enterTryExpression(tryExpression);
        TryExpressionEnterNode tryExpressionEnterNodeComponent1 = pairEnterTryExpression.component1();
        TryMainBlockEnterNode tryMainBlockEnterNodeComponent2 = pairEnterTryExpression.component2();
        mergeIncomingFlow$default(this, tryExpressionEnterNodeComponent1, null, 1, null);
        mergeIncomingFlow$default(this, tryMainBlockEnterNodeComponent2, null, 1, null);
    }

    public final void exitTryMainBlock() {
        mergeIncomingFlow$default(this, getGraphBuilder().exitTryMainBlock(), null, 1, null);
    }

    public final void enterCatchClause(FirCatch firCatch) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        mergeIncomingFlow$default(this, getGraphBuilder().enterCatchClause(firCatch), null, 1, null);
    }

    public final void exitCatchClause(FirCatch firCatch) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        mergeIncomingFlow$default(this, getGraphBuilder().exitCatchClause(firCatch), null, 1, null);
    }

    public final void enterFinallyBlock() {
        mergeIncomingFlow$default(this, getGraphBuilder().enterFinallyBlock(), null, 1, null);
    }

    public final void exitFinallyBlock() {
        mergeIncomingFlow$default(this, getGraphBuilder().exitFinallyBlock(), null, 1, null);
    }

    public final void exitTryExpression(boolean callCompleted) {
        mergeIncomingFlow$default(this, getGraphBuilder().exitTryExpression(callCompleted), null, 1, null);
    }

    public final void exitQualifiedAccessExpression(final FirQualifiedAccessExpression qualifiedAccessExpression) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        mergeIncomingFlow(getGraphBuilder().exitQualifiedAccessExpression(qualifiedAccessExpression), new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitQualifiedAccessExpression.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirDataFlowAnalyzer.this.processConditionalContract(flow, qualifiedAccessExpression);
            }
        });
    }

    public final void exitSmartCastExpression(FirSmartCastExpression smartCastExpression) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        mergeIncomingFlow$default(this, getGraphBuilder().exitSmartCastExpression(smartCastExpression), null, 1, null);
    }

    public final void enterSafeCallAfterNullCheck(final FirSafeCallExpression safeCall) {
        Intrinsics.checkNotNullParameter(safeCall, "safeCall");
        mergeIncomingFlow(getGraphBuilder().enterSafeCall(safeCall), new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.enterSafeCallAfterNullCheck.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                DataFlowVariable orCreateIfReal = FirDataFlowAnalyzer.this.getVariableStorage().getOrCreateIfReal(flow, safeCall.getReceiver());
                if (orCreateIfReal == null) {
                    return;
                }
                FirDataFlowAnalyzer.this.commitOperationStatement(flow, ModelKt.notEq(orCreateIfReal, null));
            }
        });
    }

    public final void exitSafeCall(final FirSafeCallExpression safeCall) {
        Intrinsics.checkNotNullParameter(safeCall, "safeCall");
        final ExitSafeCallNode exitSafeCallNodeExitSafeCall = getGraphBuilder().exitSafeCall();
        mergeIncomingFlow(exitSafeCallNodeExitSafeCall, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitSafeCall.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath path, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(flow, "flow");
                if (exitSafeCallNodeExitSafeCall.getPreviousNodes().size() < 2) {
                    return;
                }
                this.addAllConditionally(flow, ModelKt.notEq(this.getVariableStorage().getOrCreate(flow, safeCall), null), this.getFlow(CFGNodeKt.getLastPreviousNode(exitSafeCallNodeExitSafeCall), path));
            }
        });
    }

    public final void exitResolvedQualifierNode(FirResolvedQualifier resolvedQualifier) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        mergeIncomingFlow$default(this, getGraphBuilder().exitResolvedQualifierNode(resolvedQualifier), null, 1, null);
    }

    public final void enterCallArguments(FirStatement call, List<? extends FirExpression> arguments) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            FirAnonymousFunction firAnonymousFunctionUnwrapAnonymousFunctionExpression = InvocationKindTransformerKt.unwrapAnonymousFunctionExpression((FirExpression) it.next());
            if (firAnonymousFunctionUnwrapAnonymousFunctionExpression != null) {
                arrayList.add(firAnonymousFunctionUnwrapAnonymousFunctionExpression);
            }
        }
        ArrayList arrayList2 = arrayList;
        this.context.getVariableAssignmentAnalyzer().enterFunctionCall(arrayList2);
        getGraphBuilder().enterCall();
        getGraphBuilder().enterCallArguments(call, arrayList2);
    }

    public final void exitCallArguments() {
        SplitPostponedLambdasNode splitPostponedLambdasNodeExitCallArguments = getGraphBuilder().exitCallArguments();
        if (splitPostponedLambdasNodeExitCallArguments != null) {
            mergeIncomingFlow$default(this, splitPostponedLambdasNodeExitCallArguments, null, 1, null);
        }
    }

    public final void exitFunctionCall(final FirFunctionCall functionCall, boolean callCompleted) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        this.context.getVariableAssignmentAnalyzer().exitFunctionCall(callCompleted);
        mergeIncomingFlow(getGraphBuilder().exitFunctionCall(functionCall, callCompleted), new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitFunctionCall.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirDataFlowAnalyzer.this.processConditionalContract(flow, functionCall);
            }
        });
    }

    public final void exitDelegatedConstructorCall(FirDelegatedConstructorCall call, boolean callCompleted) {
        Intrinsics.checkNotNullParameter(call, "call");
        this.context.getVariableAssignmentAnalyzer().exitFunctionCall(callCompleted);
        mergeIncomingFlow$default(this, getGraphBuilder().exitDelegatedConstructorCall(call, callCompleted), null, 1, null);
    }

    public final void enterStringConcatenationCall() {
        getGraphBuilder().enterCall();
    }

    public final void exitStringConcatenationCall(FirStringConcatenationCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        mergeIncomingFlow$default(this, getGraphBuilder().exitStringConcatenationCall(call), null, 1, null);
    }

    private static final FirExpression orderedArguments$firstReceiver(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        FirExpression extensionReceiver = firQualifiedAccessExpression.getExtensionReceiver();
        if (Intrinsics.areEqual(extensionReceiver, FirNoReceiverExpression.INSTANCE)) {
            extensionReceiver = null;
        }
        if (extensionReceiver != null) {
            return extensionReceiver;
        }
        FirExpression dispatchReceiver = firQualifiedAccessExpression.getDispatchReceiver();
        if (Intrinsics.areEqual(dispatchReceiver, FirNoReceiverExpression.INSTANCE)) {
            return null;
        }
        return dispatchReceiver;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0027  */
    private final FirExpression[] orderedArguments(FirStatement firStatement, FirFunction firFunction) {
        FirExpression firExpressionOrderedArguments$firstReceiver;
        boolean z = firStatement instanceof FirQualifiedAccessExpression;
        if (z) {
            firExpressionOrderedArguments$firstReceiver = orderedArguments$firstReceiver((FirQualifiedAccessExpression) firStatement);
        } else if (firStatement instanceof FirVariableAssignment) {
            FirExpression lValue = ((FirVariableAssignment) firStatement).getLValue();
            FirQualifiedAccessExpression firQualifiedAccessExpression = lValue instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) lValue : null;
            if (firQualifiedAccessExpression != null) {
                firExpressionOrderedArguments$firstReceiver = orderedArguments$firstReceiver(firQualifiedAccessExpression);
            } else {
                firExpressionOrderedArguments$firstReceiver = null;
            }
        } else {
            firExpressionOrderedArguments$firstReceiver = null;
        }
        int i = 0;
        if (!(firStatement instanceof FirFunctionCall)) {
            if (z) {
                return new FirExpression[]{firExpressionOrderedArguments$firstReceiver};
            }
            if (firStatement instanceof FirVariableAssignment) {
                return new FirExpression[]{firExpressionOrderedArguments$firstReceiver, ((FirVariableAssignment) firStatement).getRValue()};
            }
            return null;
        }
        FirArgumentList argumentList = ((FirCall) firStatement).getArgumentList();
        LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
        if (mapping == null) {
            return null;
        }
        Set<Map.Entry<FirExpression, FirValueParameter>> setEntrySet = mapping.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        Set<Map.Entry<FirExpression, FirValueParameter>> set = setEntrySet;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object value = entry.getValue();
            Object key = entry.getKey();
            Intrinsics.checkNotNullExpressionValue(key, "<get-key>(...)");
            Pair pair = TuplesKt.to(value, FirExpressionUtilKt.unwrapArgument((FirExpression) key));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        int size = firFunction.getValueParameters().size() + 1;
        FirExpression[] firExpressionArr = new FirExpression[size];
        while (i < size) {
            firExpressionArr[i] = i > 0 ? (FirExpression) linkedHashMap.get(firFunction.getValueParameters().get(i - 1)) : firExpressionOrderedArguments$firstReceiver;
            i++;
        }
        return firExpressionArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void processConditionalContract(final MutableFlow flow, FirStatement qualifiedAccess) {
        FirPropertyAccessor setter;
        FirPropertySymbol resolvedPropertySymbol$default;
        FirProperty firProperty;
        FirProperty firProperty2;
        FirFunctionSymbol<? extends FirFunction> symbol;
        FirExpression[] firExpressionArrOrderedArguments;
        ConeSubstitutor.Empty emptyChain;
        if (FirResolvePhaseKt.isBodyResolve(this.components.getTransformer().getBaseTransformerPhase())) {
            if (qualifiedAccess instanceof FirFunctionCall) {
                FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol((FirExpression) qualifiedAccess);
                FirCallableDeclaration firCallableDeclaration = resolvedCallableSymbol != null ? (FirCallableDeclaration) resolvedCallableSymbol.getFir() : null;
                setter = firCallableDeclaration instanceof FirSimpleFunction ? (FirSimpleFunction) firCallableDeclaration : null;
            } else if (qualifiedAccess instanceof FirQualifiedAccessExpression) {
                FirPropertySymbol resolvedPropertySymbol$default2 = FirReferenceUtilsKt.toResolvedPropertySymbol$default(((FirQualifiedAccessExpression) qualifiedAccess).getCalleeReference(), false, 1, null);
                setter = (resolvedPropertySymbol$default2 == null || (firProperty2 = (FirProperty) resolvedPropertySymbol$default2.getFir()) == null) ? null : firProperty2.getGetter();
            } else if (qualifiedAccess instanceof FirVariableAssignment) {
                FirReference calleeReference = FirExpressionUtilKt.getCalleeReference((FirVariableAssignment) qualifiedAccess);
                setter = (calleeReference == null || (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) == null || (firProperty = (FirProperty) resolvedPropertySymbol$default.getFir()) == null) ? null : firProperty.getSetter();
            } else {
                setter = null;
            }
            if (setter == null) {
                return;
            }
            FirFunction firFunction = (FirFunction) setter;
            if (Intrinsics.areEqual(firFunction.getSymbol().getCallableId(), StandardClassIds.Callables.INSTANCE.getNot())) {
                Intrinsics.checkNotNull(qualifiedAccess, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirFunctionCall");
                exitBooleanNot(flow, (FirFunctionCall) qualifiedAccess);
                return;
            }
            FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) setter;
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration2) || (firCallableDeclaration2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration2) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration2) : null;
            }
            if (originalForSubstitutionOverrideAttr == null || (symbol = ((FirFunction) originalForSubstitutionOverrideAttr).getSymbol()) == null) {
                symbol = firFunction.getSymbol();
            }
            FirResolvedContractDescription resolvedContractDescription = symbol.getResolvedContractDescription();
            if (resolvedContractDescription == null) {
                return;
            }
            List<FirEffectDeclaration> effects = resolvedContractDescription.getEffects();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = effects.iterator();
            while (it.hasNext()) {
                KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> effect = ((FirEffectDeclaration) it.next()).getEffect();
                KtConditionalEffectDeclaration ktConditionalEffectDeclaration = effect instanceof KtConditionalEffectDeclaration ? (KtConditionalEffectDeclaration) effect : null;
                if (ktConditionalEffectDeclaration != null) {
                    arrayList.add(ktConditionalEffectDeclaration);
                }
            }
            ArrayList<KtConditionalEffectDeclaration> arrayList2 = arrayList;
            if (arrayList2.isEmpty() || (firExpressionArrOrderedArguments = orderedArguments(qualifiedAccess, firFunction)) == null) {
                return;
            }
            int length = firExpressionArrOrderedArguments.length;
            DataFlowVariable[] dataFlowVariableArr = new DataFlowVariable[length];
            for (int i = 0; i < length; i++) {
                FirExpression firExpression = firExpressionArrOrderedArguments[i];
                dataFlowVariableArr[i] = firExpression != null ? getVariableStorage().getOrCreateIfReal(flow, firExpression) : null;
            }
            for (int i2 = 0; i2 < length; i2++) {
                if (dataFlowVariableArr[i2] != null) {
                    List<FirTypeParameter> typeParameters = ((FirTypeParametersOwner) setter).getTypeParameters();
                    if (!typeParameters.isEmpty() && (qualifiedAccess instanceof FirQualifiedAccessExpression)) {
                        List<Pair> listZip = CollectionsKt.zip(typeParameters, ((FirQualifiedAccessExpression) qualifiedAccess).getTypeArguments());
                        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
                        for (Pair pair : listZip) {
                            arrayList3.add(TuplesKt.to(((FirTypeParameter) pair.component1()).getSymbol(), ConeTypeProjectionKt.getType(FirTypeUtilsKt.toConeTypeProjection((FirTypeProjection) pair.component2()))));
                        }
                        ArrayList arrayList4 = new ArrayList();
                        for (Object obj : arrayList3) {
                            if (((Pair) obj).getSecond() != null) {
                                arrayList4.add(obj);
                            }
                        }
                        Map map = MapsKt.toMap(arrayList4);
                        Intrinsics.checkNotNull(map, "null cannot be cast to non-null type kotlin.collections.Map<org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol, org.jetbrains.kotlin.fir.types.ConeKotlinType>");
                        emptyChain = new ConeSubstitutorByMap(map, this.components.getSession());
                    } else {
                        emptyChain = ConeSubstitutor.Empty.INSTANCE;
                    }
                    if (originalForSubstitutionOverrideAttr != null) {
                        List<FirTypeParameterSymbol> typeParameterSymbols = ((FirFunction) originalForSubstitutionOverrideAttr).getSymbol().getTypeParameterSymbols();
                        List<FirTypeParameter> list = typeParameters;
                        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                        Iterator<T> it2 = list.iterator();
                        while (it2.hasNext()) {
                            arrayList5.add(FirNestedClassifierScopeKt.toConeType(((FirTypeParameter) it2.next()).getSymbol()));
                        }
                        emptyChain = SubstitutorsKt.chain(new ConeSubstitutorByMap(MapsKt.toMap(CollectionsKt.zip(typeParameterSymbols, arrayList5)), this.components.getSession()), emptyChain);
                    }
                    for (KtConditionalEffectDeclaration ktConditionalEffectDeclaration2 : arrayList2) {
                        KtEffectDeclaration effect2 = ktConditionalEffectDeclaration2.getEffect();
                        KtReturnsEffectDeclaration ktReturnsEffectDeclaration = effect2 instanceof KtReturnsEffectDeclaration ? (KtReturnsEffectDeclaration) effect2 : null;
                        if (ktReturnsEffectDeclaration != null) {
                            final Operation operation = ContractsKt.toOperation(ktReturnsEffectDeclaration.getValue());
                            Map<RealVariable, TypeStatement> mapApproveContractStatement = ContractsKt.approveContractStatement(getLogicSystem(), ktConditionalEffectDeclaration2.getCondition(), dataFlowVariableArr, emptyChain, new Function1<OperationStatement, Map<RealVariable, ? extends TypeStatement>>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer$processConditionalContract$statements$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Map<RealVariable, TypeStatement> invoke(OperationStatement it3) {
                                    Intrinsics.checkNotNullParameter(it3, "it");
                                    return this.this$0.getLogicSystem().approveOperationStatement(flow, it3, operation == null);
                                }
                            });
                            if (mapApproveContractStatement != null) {
                                if (operation == null) {
                                    addAllStatements(flow, mapApproveContractStatement);
                                } else {
                                    addAllConditionally(flow, new OperationStatement(getVariableStorage().getOrCreate(flow, qualifiedAccess), operation), mapApproveContractStatement);
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    public final void exitConstExpression(FirConstExpression<?> constExpression) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        if (constExpression.getTypeRef() instanceof FirResolvedTypeRef) {
            return;
        }
        mergeIncomingFlow$default(this, getGraphBuilder().exitConstExpression(constExpression), null, 1, null);
    }

    public final void exitLocalVariableDeclaration(final FirProperty variable, final boolean hadExplicitType) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        mergeIncomingFlow(getGraphBuilder().exitVariableDeclaration(variable), new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitLocalVariableDeclaration.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirExpression initializer = variable.getInitializer();
                if (initializer == null) {
                    return;
                }
                this.exitVariableInitialization(flow, initializer, variable, null, hadExplicitType);
            }
        });
    }

    public final void exitVariableAssignment(final FirVariableAssignment assignment) {
        Intrinsics.checkNotNullParameter(assignment, "assignment");
        mergeIncomingFlow(getGraphBuilder().exitVariableAssignment(assignment), new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitVariableAssignment.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                FirPropertySymbol resolvedPropertySymbol$default;
                FirProperty firProperty;
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(assignment);
                if (calleeReference == null || (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) == null || (firProperty = (FirProperty) resolvedPropertySymbol$default.getFir()) == null) {
                    return;
                }
                if (firProperty.getIsLocal() || firProperty.getIsVal()) {
                    this.exitVariableInitialization(flow, assignment.getRValue(), firProperty, assignment.getLValue(), false);
                } else {
                    RealVariable realVariableWithoutUnwrappingAlias = this.getVariableStorage().getRealVariableWithoutUnwrappingAlias(flow, assignment);
                    if (realVariableWithoutUnwrappingAlias != null) {
                        this.getLogicSystem().recordNewAssignment(flow, realVariableWithoutUnwrappingAlias, this.context.newAssignmentIndex());
                    }
                }
                this.processConditionalContract(flow, assignment);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void exitVariableInitialization(MutableFlow flow, FirExpression initializer, FirProperty property, FirExpression assignmentLhs, boolean hasExplicitType) {
        MutableFlow mutableFlow = flow;
        RealVariable orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization = getVariableStorage().getOrCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization(mutableFlow, property.getSymbol(), assignmentLhs != null ? assignmentLhs : property);
        boolean z = assignmentLhs != null;
        if (z) {
            getLogicSystem().recordNewAssignment(flow, orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization, this.context.newAssignmentIndex());
        }
        DataFlowVariable orCreateIfReal = getVariableStorage().getOrCreateIfReal(mutableFlow, initializer);
        if (orCreateIfReal instanceof RealVariable) {
            RealVariable realVariable = (RealVariable) orCreateIfReal;
            boolean zIsStableOrLocalStableAccess = isStableOrLocalStableAccess(realVariable, initializer);
            if (!hasExplicitType && zIsStableOrLocalStableAccess && (getHasLocalStability(orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization) || isStable(orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization))) {
                getLogicSystem().addLocalVariableAlias(flow, orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization, realVariable);
            } else {
                LogicSystem.translateVariableFromConditionInStatements$default(getLogicSystem(), flow, orCreateIfReal, orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization, null, 8, null);
            }
        } else if (orCreateIfReal != null && isStable(orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization)) {
            LogicSystem.translateVariableFromConditionInStatements$default(getLogicSystem(), flow, orCreateIfReal, orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization, null, 8, null);
        }
        if (z) {
            addTypeStatement(flow, ModelKt.typeEq(flow.unwrapVariable(orCreateRealVariableWithoutUnwrappingAliasForPropertyInitialization), FirTypeUtilsKt.getConeType(initializer.getTypeRef())));
        }
    }

    private final boolean isStable(RealVariable realVariable) {
        return realVariable.getStability() == PropertyStability.STABLE_VALUE;
    }

    private final boolean getHasLocalStability(RealVariable realVariable) {
        return realVariable.getStability() == PropertyStability.LOCAL_VAR;
    }

    private final boolean isStableOrLocalStableAccess(RealVariable realVariable, FirExpression firExpression) {
        return isStable(realVariable) || (getHasLocalStability(realVariable) && !isAccessToUnstableLocalVariable(firExpression));
    }

    public final void exitThrowExceptionNode(FirThrowExpression throwExpression) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        mergeIncomingFlow$default(this, getGraphBuilder().exitThrowExceptionNode(throwExpression), null, 1, null);
    }

    public final void enterBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        mergeIncomingFlow$default(this, getGraphBuilder().enterBinaryLogicExpression(binaryLogicExpression), null, 1, null);
    }

    public final void exitLeftBinaryLogicExpressionArgument(final FirBinaryLogicExpression binaryLogicExpression) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        Pair<CFGNode<FirBinaryLogicExpression>, CFGNode<FirBinaryLogicExpression>> pairExitLeftBinaryLogicExpressionArgument = getGraphBuilder().exitLeftBinaryLogicExpressionArgument(binaryLogicExpression);
        CFGNode<FirBinaryLogicExpression> cFGNodeComponent1 = pairExitLeftBinaryLogicExpressionArgument.component1();
        CFGNode<FirBinaryLogicExpression> cFGNodeComponent2 = pairExitLeftBinaryLogicExpressionArgument.component2();
        mergeIncomingFlow$default(this, cFGNodeComponent1, null, 1, null);
        mergeIncomingFlow(cFGNodeComponent2, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitLeftBinaryLogicExpressionArgument.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath flowPath, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(flow, "flow");
                DataFlowVariable dataFlowVariable = FirDataFlowAnalyzer.this.getVariableStorage().get(flow, binaryLogicExpression.getLeftOperand());
                if (dataFlowVariable == null) {
                    return;
                }
                FirDataFlowAnalyzer.this.commitOperationStatement(flow, ModelKt.eq(dataFlowVariable, binaryLogicExpression.getKind() == LogicOperationKind.AND));
            }
        });
    }

    public final void exitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        mergeBinaryLogicOperatorFlow(getGraphBuilder().exitBinaryLogicExpression(binaryLogicExpression));
    }

    private final void mergeBinaryLogicOperatorFlow(final AbstractBinaryExitNode<FirBinaryLogicExpression> abstractBinaryExitNode) {
        mergeIncomingFlow(abstractBinaryExitNode, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.mergeBinaryLogicOperatorFlow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath path, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(flow, "flow");
                boolean z = false;
                boolean z2 = abstractBinaryExitNode.getFir().getKind() == LogicOperationKind.AND;
                PersistentFlow flow2 = this.getFlow(abstractBinaryExitNode.getLeftOperandNode(), path);
                PersistentFlow flow3 = this.getFlow(abstractBinaryExitNode.getRightOperandNode(), path);
                DataFlowVariable dataFlowVariable = this.getVariableStorage().get(flow2, abstractBinaryExitNode.getFir().getLeftOperand());
                boolean z3 = dataFlowVariable != null && ConeBuiltinTypeUtilsKt.isBoolean(UtilKt.getConeType(abstractBinaryExitNode.getFir().getLeftOperand()));
                if (!abstractBinaryExitNode.getLeftOperandNode().getIsDead() && abstractBinaryExitNode.getRightOperandNode().getIsDead()) {
                    if (z3) {
                        FirDataFlowAnalyzer firDataFlowAnalyzer = this;
                        Intrinsics.checkNotNull(dataFlowVariable);
                        firDataFlowAnalyzer.commitOperationStatement(flow, ModelKt.eq(dataFlowVariable, !z2));
                        return;
                    }
                    return;
                }
                PersistentFlow persistentFlow = flow3;
                DataFlowVariable dataFlowVariable2 = this.getVariableStorage().get(persistentFlow, abstractBinaryExitNode.getFir().getRightOperand());
                if (dataFlowVariable2 != null && ConeBuiltinTypeUtilsKt.isBoolean(UtilKt.getConeType(abstractBinaryExitNode.getFir().getRightOperand()))) {
                    z = true;
                }
                SyntheticVariable syntheticVariableCreateSynthetic = this.getVariableStorage().createSynthetic(abstractBinaryExitNode.getFir());
                OperationStatement operationStatementEq = ModelKt.eq(syntheticVariableCreateSynthetic, z2);
                this.addAllConditionally(flow, operationStatementEq, persistentFlow);
                if (z) {
                    FirDataFlowAnalyzer firDataFlowAnalyzer2 = this;
                    LogicSystem logicSystem = firDataFlowAnalyzer2.getLogicSystem();
                    Intrinsics.checkNotNull(dataFlowVariable2);
                    firDataFlowAnalyzer2.addAllConditionally(flow, operationStatementEq, logicSystem.approveOperationStatement(flow3, ModelKt.eq(dataFlowVariable2, z2)));
                }
                if (z3 && z) {
                    FirDataFlowAnalyzer firDataFlowAnalyzer3 = this;
                    OperationStatement operationStatementEq2 = ModelKt.eq(syntheticVariableCreateSynthetic, !z2);
                    LogicSystem logicSystem2 = this.getLogicSystem();
                    LogicSystem logicSystem3 = this.getLogicSystem();
                    Intrinsics.checkNotNull(dataFlowVariable);
                    Map<RealVariable, TypeStatement> mapApproveOperationStatement = logicSystem3.approveOperationStatement(flow2, ModelKt.eq(dataFlowVariable, !z2));
                    LogicSystem logicSystem4 = this.getLogicSystem();
                    Intrinsics.checkNotNull(dataFlowVariable2);
                    firDataFlowAnalyzer3.addAllConditionally(flow, operationStatementEq2, logicSystem2.orForTypeStatements(mapApproveOperationStatement, logicSystem4.approveOperationStatement(flow3, ModelKt.eq(dataFlowVariable2, !z2))));
                }
            }
        });
    }

    private final void exitBooleanNot(MutableFlow flow, FirFunctionCall expression) {
        DataFlowVariable dataFlowVariable = getVariableStorage().get(flow, expression.getDispatchReceiver());
        if (dataFlowVariable == null) {
            return;
        }
        final SyntheticVariable syntheticVariableCreateSynthetic = getVariableStorage().createSynthetic(expression);
        getLogicSystem().translateVariableFromConditionInStatements(flow, dataFlowVariable, syntheticVariableCreateSynthetic, new Function1<Implication, Implication>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitBooleanNot.1

            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer$exitBooleanNot$1$WhenMappings */
            /* JADX INFO: compiled from: FirDataFlowAnalyzer.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[Operation.values().length];
                    try {
                        iArr[Operation.EqTrue.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[Operation.EqFalse.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Implication invoke(Implication it) {
                Intrinsics.checkNotNullParameter(it, "it");
                int i = WhenMappings.$EnumSwitchMapping$0[it.getCondition().getOperation().ordinal()];
                if (i == 1) {
                    return ModelKt.implies(ModelKt.eq((DataFlowVariable) syntheticVariableCreateSynthetic, false), it.getEffect());
                }
                if (i != 2) {
                    return null;
                }
                return ModelKt.implies(ModelKt.eq((DataFlowVariable) syntheticVariableCreateSynthetic, true), it.getEffect());
            }
        });
    }

    public final void enterAnnotation() {
        mergeIncomingFlow$default(this, getGraphBuilder().enterFakeExpression(), null, 1, null);
    }

    public final void exitAnnotation() {
        getGraphBuilder().exitFakeExpression();
    }

    public final void enterInitBlock(FirAnonymousInitializer initBlock) {
        Intrinsics.checkNotNullParameter(initBlock, "initBlock");
        mergeIncomingFlow$default(this, getGraphBuilder().enterInitBlock(initBlock), null, 1, null);
    }

    public final ControlFlowGraph exitInitBlock() {
        Pair<InitBlockExitNode, ControlFlowGraph> pairExitInitBlock = getGraphBuilder().exitInitBlock();
        InitBlockExitNode initBlockExitNodeComponent1 = pairExitInitBlock.component1();
        ControlFlowGraph controlFlowGraphComponent2 = pairExitInitBlock.component2();
        mergeIncomingFlow$default(this, initBlockExitNodeComponent1, null, 1, null);
        return controlFlowGraphComponent2;
    }

    public final void enterContractDescription() {
        mergeIncomingFlow$default(this, getGraphBuilder().enterFakeExpression(), null, 1, null);
    }

    public final void exitContractDescription() {
        getGraphBuilder().exitFakeExpression();
    }

    public final void enterElvis(FirElvisExpression elvisExpression) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        getGraphBuilder().enterElvis(elvisExpression);
    }

    public final void exitElvisLhs(final FirElvisExpression elvisExpression) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        Triple<ElvisLhsExitNode, ElvisLhsIsNotNullNode, ElvisRhsEnterNode> tripleExitElvisLhs = getGraphBuilder().exitElvisLhs(elvisExpression);
        final ElvisLhsExitNode elvisLhsExitNodeComponent1 = tripleExitElvisLhs.component1();
        ElvisLhsIsNotNullNode elvisLhsIsNotNullNodeComponent2 = tripleExitElvisLhs.component2();
        ElvisRhsEnterNode elvisRhsEnterNodeComponent3 = tripleExitElvisLhs.component3();
        mergeIncomingFlow$default(this, elvisLhsExitNodeComponent1, null, 1, null);
        mergeIncomingFlow(elvisLhsIsNotNullNodeComponent2, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitElvisLhs.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath path, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(flow, "flow");
                DataFlowVariable dataFlowVariableExitElvisLhs$getLhsVariable = FirDataFlowAnalyzer.exitElvisLhs$getLhsVariable(FirDataFlowAnalyzer.this, elvisLhsExitNodeComponent1, elvisExpression, path);
                if (dataFlowVariableExitElvisLhs$getLhsVariable != null) {
                    FirDataFlowAnalyzer.this.commitOperationStatement(flow, ModelKt.notEq(dataFlowVariableExitElvisLhs$getLhsVariable, null));
                }
            }
        });
        mergeIncomingFlow(elvisRhsEnterNodeComponent3, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitElvisLhs.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath path, MutableFlow flow) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(flow, "flow");
                DataFlowVariable dataFlowVariableExitElvisLhs$getLhsVariable = FirDataFlowAnalyzer.exitElvisLhs$getLhsVariable(FirDataFlowAnalyzer.this, elvisLhsExitNodeComponent1, elvisExpression, path);
                if (dataFlowVariableExitElvisLhs$getLhsVariable != null) {
                    FirDataFlowAnalyzer.this.commitOperationStatement(flow, ModelKt.eq(dataFlowVariableExitElvisLhs$getLhsVariable, (Void) null));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DataFlowVariable exitElvisLhs$getLhsVariable(FirDataFlowAnalyzer firDataFlowAnalyzer, ElvisLhsExitNode elvisLhsExitNode, FirElvisExpression firElvisExpression, FlowPath flowPath) {
        return firDataFlowAnalyzer.getVariableStorage().getOrCreateIfReal(firDataFlowAnalyzer.getFlow(elvisLhsExitNode, flowPath), firElvisExpression.getLhs());
    }

    public final void exitElvis(final FirElvisExpression elvisExpression, final boolean isLhsNotNull, boolean callCompleted) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        final ElvisExitNode elvisExitNodeExitElvis = getGraphBuilder().exitElvis(isLhsNotNull, callCompleted);
        mergeIncomingFlow(elvisExitNodeExitElvis, new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.exitElvis.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                invoke2(flowPath, mutableFlow);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FlowPath path, MutableFlow flow) {
                DataFlowVariable orCreateIfReal;
                DataFlowVariable orCreateIfReal2;
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(flow, "flow");
                if (isLhsNotNull) {
                    return;
                }
                final FirDataFlowAnalyzer firDataFlowAnalyzer = this;
                final FirElvisExpression firElvisExpression = elvisExpression;
                Lazy lazy = LazyKt.lazy(new Function0<SyntheticVariable>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer$exitElvis$1$elvisVariable$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final SyntheticVariable invoke() {
                        return firDataFlowAnalyzer.getVariableStorage().createSynthetic(firElvisExpression);
                    }
                });
                if (FirTypeUtilsKt.isNullableNothing(elvisExpression.getRhs().getTypeRef()) && (orCreateIfReal2 = this.getVariableStorage().getOrCreateIfReal(flow, elvisExpression.getLhs())) != null) {
                    this.addImplication(flow, ModelKt.implies(ModelKt.notEq(invoke$lambda$0(lazy), null), ModelKt.notEq(orCreateIfReal2, null)));
                }
                if (FirTypeUtilsKt.isNullableNothing(elvisExpression.getLhs().getTypeRef()) && (orCreateIfReal = this.getVariableStorage().getOrCreateIfReal(flow, elvisExpression.getRhs())) != null) {
                    this.addImplication(flow, ModelKt.implies(ModelKt.notEq(invoke$lambda$0(lazy), null), ModelKt.notEq(orCreateIfReal, null)));
                }
                FirExpression rhs = elvisExpression.getRhs();
                FirConstExpression firConstExpression = rhs instanceof FirConstExpression ? (FirConstExpression) rhs : null;
                Object value = firConstExpression != null ? firConstExpression.getValue() : null;
                Boolean bool = value instanceof Boolean ? (Boolean) value : null;
                if (bool != null) {
                    this.addAllConditionally(flow, ModelKt.eq(invoke$lambda$0(lazy), !bool.booleanValue()), this.getFlow(CFGNodeKt.getFirstPreviousNode(elvisExitNodeExitElvis), path));
                }
            }

            private static final SyntheticVariable invoke$lambda$0(Lazy<SyntheticVariable> lazy) {
                return lazy.getValue();
            }
        });
    }

    public final void exitCallableReference(FirCallableReferenceAccess callableReferenceAccess) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        mergeIncomingFlow$default(this, getGraphBuilder().exitCallableReference(callableReferenceAccess), null, 1, null);
    }

    public final void exitGetClassCall(FirGetClassCall getClassCall) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        mergeIncomingFlow$default(this, getGraphBuilder().exitGetClassCall(getClassCall), null, 1, null);
    }

    private final MutableFlow buildIncomingFlow(final CFGNode<?> cFGNode, final FlowPath flowPath, Function2<? super FlowPath, ? super MutableFlow, Unit> function2) {
        List list = SequencesKt.toList(SequencesKt.mapNotNull(CFGNodeKt.getPreviousDfaNodes(cFGNode), new Function1<Pair<? extends Edge, ? extends CFGNode<?>>, PersistentFlow>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer$buildIncomingFlow$previousFlows$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ PersistentFlow invoke(Pair<? extends Edge, ? extends CFGNode<?>> pair) {
                return invoke2((Pair<Edge, ? extends CFGNode<?>>) pair);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final PersistentFlow invoke2(Pair<Edge, ? extends CFGNode<?>> pair) {
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                Edge edgeComponent1 = pair.component1();
                CFGNode<?> cFGNodeComponent2 = pair.component2();
                if (cFGNode instanceof AlternateFlowStartMarker) {
                    FlowPath flowPath2 = flowPath;
                    if (flowPath2 instanceof FlowPath.CfgEdge) {
                        if (Intrinsics.areEqual(((FlowPath.CfgEdge) flowPath2).getFir(), cFGNode.getFir()) && !Intrinsics.areEqual(edgeComponent1.getLabel(), ((FlowPath.CfgEdge) flowPath).getLabel())) {
                            return null;
                        }
                        if (!Intrinsics.areEqual(((FlowPath.CfgEdge) flowPath).getFir(), cFGNode.getFir()) && !Intrinsics.areEqual(edgeComponent1.getLabel(), NormalPath.INSTANCE)) {
                            return null;
                        }
                    }
                }
                if ((cFGNodeComponent2 instanceof MergePostponedLambdaExitsNode) && !((MergePostponedLambdaExitsNode) cFGNodeComponent2).get_flowInitialized()) {
                    FirDataFlowAnalyzer.mergeIncomingFlow$default(this, cFGNodeComponent2, null, 1, null);
                }
                if (Intrinsics.areEqual(flowPath, FlowPath.Default.INSTANCE)) {
                    if (cFGNodeComponent2 instanceof AlternateFlowEndMarker) {
                        PersistentFlow alternateFlow = cFGNodeComponent2.getAlternateFlow(new FlowPath.CfgEdge(edgeComponent1.getLabel(), cFGNodeComponent2.getFir()));
                        return alternateFlow == null ? cFGNodeComponent2.getFlow() : alternateFlow;
                    }
                    return cFGNodeComponent2.getFlow();
                }
                PersistentFlow alternateFlow2 = cFGNodeComponent2.getAlternateFlow(flowPath);
                return alternateFlow2 == null ? cFGNodeComponent2.getFlow() : alternateFlow2;
            }
        }));
        MutableFlow mutableFlowJoinFlow = getLogicSystem().joinFlow(list, cFGNode.isUnion());
        if (Intrinsics.areEqual(flowPath, FlowPath.Default.INSTANCE) && Intrinsics.areEqual(getGraphBuilder().getLastNodeOrNull(), cFGNode)) {
            if (this.currentReceiverState == null || !Intrinsics.areEqual(CollectionsKt.singleOrNull(list), this.currentReceiverState)) {
                updateAllReceivers(this.currentReceiverState, mutableFlowJoinFlow);
            }
            this.currentReceiverState = mutableFlowJoinFlow;
        }
        function2.invoke(flowPath, mutableFlowJoinFlow);
        return mutableFlowJoinFlow;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void mergeIncomingFlow$default(FirDataFlowAnalyzer firDataFlowAnalyzer, CFGNode cFGNode, Function2 function2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: mergeIncomingFlow");
        }
        if ((i & 1) != 0) {
            function2 = new Function2<FlowPath, MutableFlow, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer.mergeIncomingFlow.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FlowPath flowPath, MutableFlow mutableFlow) {
                    Intrinsics.checkNotNullParameter(flowPath, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(mutableFlow, "<anonymous parameter 1>");
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(FlowPath flowPath, MutableFlow mutableFlow) {
                    invoke2(flowPath, mutableFlow);
                    return Unit.INSTANCE;
                }
            };
        }
        firDataFlowAnalyzer.mergeIncomingFlow(cFGNode, function2);
    }

    private final void mergeIncomingFlow(CFGNode<?> cFGNode, Function2<? super FlowPath, ? super MutableFlow, Unit> function2) {
        ArrayList arrayListEmptyList;
        MutableFlow mutableFlowBuildIncomingFlow = buildIncomingFlow(cFGNode, FlowPath.Default.INSTANCE, function2);
        PersistentFlow persistentFlowFreeze = mutableFlowBuildIncomingFlow.freeze();
        cFGNode.setFlow(persistentFlowFreeze);
        if (this.currentReceiverState == mutableFlowBuildIncomingFlow) {
            this.currentReceiverState = persistentFlowFreeze;
        }
        Sequence<Pair<Edge, CFGNode<?>>> previousDfaNodes = CFGNodeKt.getPreviousDfaNodes(cFGNode);
        LinkedHashSet<FlowPath> linkedHashSet = new LinkedHashSet();
        Iterator<Pair<Edge, CFGNode<?>>> it = previousDfaNodes.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Pair<Edge, CFGNode<?>> next = it.next();
            Edge edgeComponent1 = next.component1();
            CFGNode<?> cFGNodeComponent2 = next.component2();
            if (!(cFGNodeComponent2 instanceof AlternateFlowEndMarker)) {
                Set<FlowPath> alternateFlowPaths = Intrinsics.areEqual(edgeComponent1.getLabel(), NormalPath.INSTANCE) ? cFGNodeComponent2.getAlternateFlowPaths() : null;
                arrayListEmptyList = alternateFlowPaths != null ? alternateFlowPaths : CollectionsKt.emptyList();
            } else {
                Set<FlowPath> alternateFlowPaths2 = cFGNodeComponent2.getAlternateFlowPaths();
                ArrayList arrayList = new ArrayList();
                for (Object obj : alternateFlowPaths2) {
                    FlowPath flowPath = (FlowPath) obj;
                    if (!(flowPath instanceof FlowPath.CfgEdge) || !Intrinsics.areEqual(((FlowPath.CfgEdge) flowPath).getFir(), cFGNodeComponent2.getFir())) {
                        arrayList.add(obj);
                    }
                }
                arrayListEmptyList = arrayList;
            }
            CollectionsKt.addAll(linkedHashSet, arrayListEmptyList);
        }
        for (FlowPath flowPath2 : linkedHashSet) {
            cFGNode.addAlternateFlow(flowPath2, buildIncomingFlow(cFGNode, flowPath2, function2).freeze());
        }
        if (cFGNode instanceof AlternateFlowStartMarker) {
            Sequence<Pair<Edge, CFGNode<?>>> previousDfaNodes2 = CFGNodeKt.getPreviousDfaNodes(cFGNode);
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            Iterator<Pair<Edge, CFGNode<?>>> it2 = previousDfaNodes2.iterator();
            while (it2.hasNext()) {
                EdgeLabel label = it2.next().component1().getLabel();
                if (Intrinsics.areEqual(label, UncaughtExceptionPath.INSTANCE)) {
                    label = null;
                }
                if (label != null) {
                    linkedHashSet2.add(label);
                }
            }
            LinkedHashSet linkedHashSet3 = linkedHashSet2;
            ArrayList<FlowPath.CfgEdge> arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(linkedHashSet3, 10));
            Iterator it3 = linkedHashSet3.iterator();
            while (it3.hasNext()) {
                arrayList2.add(new FlowPath.CfgEdge((EdgeLabel) it3.next(), cFGNode.getFir()));
            }
            for (FlowPath.CfgEdge cfgEdge : arrayList2) {
                cFGNode.addAlternateFlow(cfgEdge, buildIncomingFlow(cFGNode, cfgEdge, function2).freeze());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PersistentFlow getFlow(CFGNode<?> cFGNode, FlowPath flowPath) {
        if (Intrinsics.areEqual(flowPath, FlowPath.Default.INSTANCE)) {
            return cFGNode.getFlow();
        }
        PersistentFlow alternateFlow = cFGNode.getAlternateFlow(flowPath);
        if (alternateFlow != null) {
            return alternateFlow;
        }
        throw new IllegalStateException(("no alternate flow for " + flowPath).toString());
    }

    private final void resetReceivers() {
        CFGNode<?> lastNodeOrNull = getGraphBuilder().getLastNodeOrNull();
        PersistentFlow flow = lastNodeOrNull != null ? lastNodeOrNull.getFlow() : null;
        updateAllReceivers(this.currentReceiverState, flow);
        this.currentReceiverState = flow;
    }

    private final void updateAllReceivers(Flow from, Flow to) {
        for (ImplicitReceiverValue<?> implicitReceiverValue : getReceiverStack()) {
            RealVariable localVariable = getVariableStorage().getLocalVariable(implicitReceiverValue.getBoundSymbol());
            if (localVariable != null) {
                TypeStatement typeStatement = to != null ? to.getTypeStatement(localVariable) : null;
                if (!Intrinsics.areEqual(typeStatement, from != null ? from.getTypeStatement(localVariable) : null)) {
                    receiverUpdated(implicitReceiverValue.getBoundSymbol(), typeStatement);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addImplication(MutableFlow mutableFlow, Implication implication) {
        getLogicSystem().addImplication(mutableFlow, implication);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addTypeStatement(MutableFlow mutableFlow, TypeStatement typeStatement) {
        TypeStatement typeStatementAddTypeStatement = getLogicSystem().addTypeStatement(mutableFlow, typeStatement);
        if (typeStatementAddTypeStatement != null && typeStatementAddTypeStatement.getVariable().getIsThisReference() && mutableFlow == this.currentReceiverState) {
            receiverUpdated(typeStatementAddTypeStatement.getVariable().getIdentifier().getSymbol(), typeStatementAddTypeStatement);
        }
    }

    private final void addAllStatements(MutableFlow mutableFlow, Map<RealVariable, ? extends TypeStatement> map) {
        Iterator<T> it = map.values().iterator();
        while (it.hasNext()) {
            addTypeStatement(mutableFlow, (TypeStatement) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addAllConditionally(MutableFlow mutableFlow, OperationStatement operationStatement, Map<RealVariable, ? extends TypeStatement> map) {
        Iterator<T> it = map.values().iterator();
        while (it.hasNext()) {
            addImplication(mutableFlow, ModelKt.implies(operationStatement, (TypeStatement) it.next()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addAllConditionally(MutableFlow mutableFlow, OperationStatement operationStatement, Flow flow) {
        TypeStatement typeStatement;
        for (RealVariable realVariable : flow.getKnownVariables()) {
            if (Intrinsics.areEqual(mutableFlow.unwrapVariable(realVariable), realVariable) && (typeStatement = flow.getTypeStatement(realVariable)) != null) {
                addImplication(mutableFlow, ModelKt.implies(operationStatement, typeStatement));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void commitOperationStatement(MutableFlow mutableFlow, OperationStatement operationStatement) {
        addAllStatements(mutableFlow, getLogicSystem().approveOperationStatement(mutableFlow, operationStatement, true));
    }
}
