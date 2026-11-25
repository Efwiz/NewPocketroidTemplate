package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRangeKt;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatusKt;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirJump;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
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
import org.jetbrains.kotlin.fir.expressions.builder.FirUnitExpressionBuilder;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;
import org.jetbrains.kotlin.fir.resolve.dfa.Stack;
import org.jetbrains.kotlin.fir.resolve.dfa.StackKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.util.ListMultimap;
import org.jetbrains.kotlin.fir.util.MultimapKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ControlFlowGraphBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000æ\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J4\u0010L\u001a\u00020M2\n\u0010N\u001a\u0006\u0012\u0002\b\u00030\u00182\n\u0010O\u001a\u0006\u0012\u0002\b\u00030\u00182\b\b\u0002\u0010P\u001a\u00020\u00072\b\b\u0002\u0010Q\u001a\u00020RH\u0002JH\u0010S\u001a\u00020M2\n\u0010N\u001a\u0006\u0012\u0002\b\u00030\u00182\n\u0010O\u001a\u0006\u0012\u0002\b\u00030\u00182\b\b\u0002\u0010T\u001a\u00020\u00072\b\b\u0002\u0010P\u001a\u00020\u00072\b\b\u0002\u0010U\u001a\u00020\u00192\b\b\u0002\u0010Q\u001a\u00020RH\u0002J \u0010V\u001a\u00020M\"\b\b\u0000\u0010W*\u00020X2\f\u0010Y\u001a\b\u0012\u0004\u0012\u0002HW0\u0018H\u0002J*\u0010Z\u001a\u00020M2\n\u0010N\u001a\u0006\u0012\u0002\b\u00030\u00182\n\u0010O\u001a\u0006\u0012\u0002\b\u00030\u00182\b\b\u0002\u0010Q\u001a\u00020RH\u0002J\u0014\u0010[\u001a\u00020M2\n\u0010\\\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0002J\u001e\u0010]\u001a\u00020M2\n\u0010\\\u001a\u0006\u0012\u0002\b\u00030\u00182\b\b\u0002\u0010P\u001a\u00020\u0007H\u0002J\u0014\u0010^\u001a\u00020M2\n\u0010\\\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0002J\u0010\u0010_\u001a\u00020\u00072\u0006\u0010\\\u001a\u00020:H\u0002J\u000e\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020cJ\u0010\u0010d\u001a\u0004\u0018\u00010e2\u0006\u0010f\u001a\u00020gJ\u0014\u0010h\u001a\b\u0012\u0004\u0012\u00020i0\u00182\u0006\u0010j\u001a\u00020iJ\u000e\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020nJ\u0006\u0010o\u001a\u00020MJ\u001c\u0010p\u001a\u00020M2\u0006\u0010q\u001a\u00020r2\f\u0010s\u001a\b\u0012\u0004\u0012\u00020c0\rJ\u000e\u0010t\u001a\u00020\u000b2\u0006\u0010u\u001a\u00020vJ*\u0010w\u001a\u0016\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0018\u0012\u0006\u0012\u0004\u0018\u00010x0\u00172\u0006\u0010y\u001a\u00020z2\u0006\u0010{\u001a\u00020\u0007J\u000e\u0010|\u001a\u00020}2\u0006\u0010~\u001a\u00020\u007fJ\u0007\u0010\u0080\u0001\u001a\u00020MJ\u001e\u0010\u0081\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0082\u0001\u0012\u0005\u0012\u00030\u0083\u00010\u00172\u0007\u0010\u0084\u0001\u001a\u000201J\u001d\u0010\u0085\u0001\u001a\u000f\u0012\u0005\u0012\u00030\u0086\u0001\u0012\u0004\u0012\u0002020\u00172\u0007\u0010\u0084\u0001\u001a\u000201J\u0011\u0010\u0087\u0001\u001a\u00020M2\b\u0010\u0088\u0001\u001a\u00030\u0089\u0001J\b\u0010\u008a\u0001\u001a\u00030\u008b\u0001J\u0014\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u00012\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001J\u0007\u0010\u0090\u0001\u001a\u00020\"J \u0010\u0091\u0001\u001a\u0011\u0012\u0007\u0012\u0005\u0018\u00010\u0092\u0001\u0012\u0004\u0012\u00020a0\u00172\b\u0010\u0093\u0001\u001a\u00030\u0094\u0001J\u0091\u0001\u0010\u0095\u0001\u001a\u0003H\u0096\u0001\"\u0005\b\u0000\u0010\u0097\u0001\"\t\b\u0001\u0010W*\u0003H\u0097\u0001\"\u0015\b\u0002\u0010\u0096\u0001*\t\u0012\u0005\u0012\u0003H\u0097\u00010\u0018*\u00030\u0098\u0001\"\u0015\b\u0003\u0010\u0099\u0001*\t\u0012\u0005\u0012\u0003H\u0097\u00010\u0018*\u00030\u009a\u00012\u0006\u0010q\u001a\u0002HW2\b\u0010\u009b\u0001\u001a\u00030\u009c\u00012\b\u0010\u009d\u0001\u001a\u00030\u009e\u00012\"\u0010\u009f\u0001\u001a\u001d\u0012\u0004\u0012\u0002HW\u0012\u0012\u0012\u0010\u0012\u0005\u0012\u0003H\u0096\u0001\u0012\u0005\u0012\u0003H\u0099\u00010\u00170 \u0001H\u0082\b¢\u0006\u0003\u0010¡\u0001J\u0012\u0010¢\u0001\u001a\u00030£\u00012\b\u0010¤\u0001\u001a\u00030¥\u0001J\u0015\u0010¦\u0001\u001a\u00020M2\f\u0010§\u0001\u001a\u0007\u0012\u0002\b\u00030¨\u0001J\u0014\u0010©\u0001\u001a\u0005\u0018\u00010ª\u00012\b\u0010«\u0001\u001a\u00030¬\u0001J\u0012\u0010\u00ad\u0001\u001a\u00030®\u00012\b\u0010¯\u0001\u001a\u00030°\u0001J\u0012\u0010±\u0001\u001a\u00030²\u00012\b\u0010³\u0001\u001a\u00030´\u0001J\u001f\u0010µ\u0001\u001a\u0010\u0012\u0005\u0012\u00030¶\u0001\u0012\u0005\u0012\u00030·\u00010\u00172\b\u0010¸\u0001\u001a\u00030¹\u0001J!\u0010º\u0001\u001a\u0012\u0012\u0005\u0012\u00030»\u0001\u0012\u0005\u0012\u00030¼\u0001\u0018\u00010\u00172\b\u0010½\u0001\u001a\u00030¾\u0001J\u0012\u0010¿\u0001\u001a\u00030À\u00012\b\u0010Á\u0001\u001a\u00030Â\u0001J\u0012\u0010Ã\u0001\u001a\u00030Ä\u00012\b\u0010Å\u0001\u001a\u00030Æ\u0001J\u001d\u0010Ç\u0001\u001a\u000f\u0012\u0005\u0012\u00030\u0082\u0001\u0012\u0004\u0012\u0002020\u00172\u0007\u0010\u0084\u0001\u001a\u000201J$\u0010È\u0001\u001a\u0017\u0012\u0004\u0012\u00020 \u0012\u0006\u0012\u0004\u0018\u00010<\u0012\u0004\u0012\u00020\u000f0É\u00012\u0006\u0010b\u001a\u00020cJ\u0014\u0010Ê\u0001\u001a\u0005\u0018\u00010Ë\u00012\b\u0010Ì\u0001\u001a\u00030Í\u0001J\u0016\u0010Î\u0001\u001a\t\u0012\u0004\u0012\u00020i0Ï\u00012\u0006\u0010j\u001a\u00020iJ\u0013\u0010Ð\u0001\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010m\u001a\u00020nJ\t\u0010Ñ\u0001\u001a\u0004\u0018\u00010\u0005J\u0012\u0010Ò\u0001\u001a\u00030Ó\u00012\b\u0010Ô\u0001\u001a\u00030Õ\u0001J\u0010\u0010Ö\u0001\u001a\u00030×\u00012\u0006\u0010u\u001a\u00020vJ\u001b\u0010Ø\u0001\u001a\u00030Ù\u00012\b\u0010Ú\u0001\u001a\u00030Û\u00012\u0007\u0010Ü\u0001\u001a\u00020\u0007J\u0018\u0010Ý\u0001\u001a\u0013\u0012\u0007\u0012\u0005\u0018\u00010Þ\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0017J\u0014\u0010ß\u0001\u001a\u000f\u0012\u0005\u0012\u00030à\u0001\u0012\u0004\u0012\u00020\u000f0\u0017J\u0012\u0010á\u0001\u001a\u00030â\u00012\b\u0010ã\u0001\u001a\u00030ä\u0001J\u0016\u0010å\u0001\u001a\u00030æ\u00012\f\u0010ç\u0001\u001a\u0007\u0012\u0002\b\u00030è\u0001J\u0010\u0010é\u0001\u001a\u00030ê\u00012\u0006\u0010q\u001a\u00020FJ\u001b\u0010ë\u0001\u001a\u00030ì\u00012\b\u0010í\u0001\u001a\u00030î\u00012\u0007\u0010Ü\u0001\u001a\u00020\u0007J\u001d\u0010ï\u0001\u001a\u000f\u0012\u0005\u0012\u00030ð\u0001\u0012\u0004\u0012\u0002040\u00172\u0007\u0010\u0084\u0001\u001a\u000201J\u0019\u0010ñ\u0001\u001a\u00020\u001b2\u0007\u0010ò\u0001\u001a\u00020\u00072\u0007\u0010Ü\u0001\u001a\u00020\u0007J&\u0010ó\u0001\u001a\u0017\u0012\u0005\u0012\u00030ô\u0001\u0012\u0005\u0012\u00030õ\u0001\u0012\u0004\u0012\u00020\u00130É\u00012\b\u0010\u0088\u0001\u001a\u00030\u0089\u0001J\u0012\u0010ö\u0001\u001a\u00030÷\u00012\b\u0010ø\u0001\u001a\u00030ù\u0001J\u0007\u0010ú\u0001\u001a\u00020MJ \u0010û\u0001\u001a\u0011\u0012\u0005\u0012\u00030ü\u0001\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u00172\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001J\b\u0010ý\u0001\u001a\u00030þ\u0001J\u001d\u0010ÿ\u0001\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u000f0\u00172\b\u0010\u0093\u0001\u001a\u00030\u0094\u0001J\u001a\u0010\u0080\u0002\u001a\u00020:2\b\u0010\u0081\u0002\u001a\u00030\u0082\u00022\u0007\u0010Ü\u0001\u001a\u00020\u0007J\u0012\u0010\u0083\u0002\u001a\u00030\u0084\u00022\b\u0010\u0085\u0002\u001a\u00030\u0086\u0002J-\u0010\u0087\u0002\u001a\u000f\u0012\u0005\u0012\u0003H\u0099\u0001\u0012\u0004\u0012\u00020\u000f0\u0017\"\u0014\b\u0000\u0010\u0099\u0001\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0018*\u00030\u009a\u0001H\u0082\bJ\u0014\u0010\u0088\u0002\u001a\u000f\u0012\u0005\u0012\u00030\u0089\u0002\u0012\u0004\u0012\u00020\u000f0\u0017J\u0015\u0010\u008a\u0002\u001a\u0002072\f\u0010§\u0001\u001a\u0007\u0012\u0002\b\u00030¨\u0001J'\u0010\u008b\u0002\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020i0\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020i0\u00180\u00172\u0006\u0010j\u001a\u00020iJ \u0010\u008c\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u008d\u0002\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u00172\b\u0010«\u0001\u001a\u00030¬\u0001J\u0012\u0010\u008e\u0002\u001a\u00030\u008f\u00022\b\u0010\u0090\u0002\u001a\u00030\u0091\u0002J\u0012\u0010\u0092\u0002\u001a\u00030\u0093\u00022\b\u0010\u0094\u0002\u001a\u00030\u0095\u0002J\u0007\u0010\u0096\u0002\u001a\u00020\u001dJ\u0014\u0010\u0097\u0002\u001a\u000f\u0012\u0005\u0012\u00030\u0098\u0002\u0012\u0004\u0012\u00020\u000f0\u0017J\u0012\u0010\u0099\u0002\u001a\u00030\u009a\u00022\b\u0010\u009b\u0002\u001a\u00030\u009c\u0002J\u0012\u0010\u009d\u0002\u001a\u00030\u009e\u00022\b\u0010í\u0001\u001a\u00030\u009f\u0002J\u0012\u0010 \u0002\u001a\u00030¡\u00022\b\u0010¢\u0002\u001a\u00030£\u0002J\u0010\u0010¤\u0002\u001a\u00020?2\u0007\u0010Ü\u0001\u001a\u00020\u0007J\b\u0010¥\u0002\u001a\u00030¦\u0002J\u0012\u0010§\u0002\u001a\u00030¨\u00022\b\u0010©\u0002\u001a\u00030ª\u0002J(\u0010«\u0002\u001a\u0019\u0012\u0005\u0012\u00030¬\u0002\u0012\u0005\u0012\u00030\u00ad\u0002\u0012\u0004\u0012\u00020\u000f\u0018\u00010É\u00012\b\u0010½\u0001\u001a\u00030¾\u0001J\u0012\u0010®\u0002\u001a\u00030¯\u00022\b\u0010°\u0002\u001a\u00030±\u0002J\u0012\u0010²\u0002\u001a\u00030³\u00022\b\u0010´\u0002\u001a\u00030¬\u0001J\u001f\u0010µ\u0002\u001a\u0010\u0012\u0005\u0012\u00030¶\u0002\u0012\u0005\u0012\u00030·\u00020\u00172\b\u0010Á\u0001\u001a\u00030Â\u0001J\u0012\u0010¸\u0002\u001a\u00030¹\u00022\b\u0010Á\u0001\u001a\u00030Â\u0001J)\u0010º\u0002\u001a\u0011\u0012\u0004\u0012\u00020A\u0012\u0007\u0012\u0005\u0018\u00010»\u00020\u00172\b\u0010Å\u0001\u001a\u00030Æ\u00012\u0007\u0010Ü\u0001\u001a\u00020\u0007J\u0012\u0010¼\u0002\u001a\u00030½\u00022\b\u0010¾\u0002\u001a\u00030¿\u0002J$\u0010À\u0002\u001a\u0016\u0012\u0004\u0012\u000202\u0012\u0005\u0012\u00030\u0086\u0001\u0012\u0004\u0012\u0002040É\u00012\u0007\u0010\u0084\u0001\u001a\u000201J\u001e\u0010Á\u0002\u001a\u0010\u0012\u0005\u0012\u00030ð\u0001\u0012\u0005\u0012\u00030\u0083\u00010\u00172\u0007\u0010\u0084\u0001\u001a\u000201J\t\u0010Â\u0002\u001a\u00020-H\u0002J\u001e\u0010Ã\u0002\u001a\u00020M2\n\u0010\\\u001a\u0006\u0012\u0002\b\u00030\u00182\u0007\u0010Ü\u0001\u001a\u00020\u0007H\u0002J\u001f\u0010Ä\u0002\u001a\u00020M2\n\u0010O\u001a\u0006\u0012\u0002\b\u00030\u00182\b\b\u0002\u0010U\u001a\u00020\u0019H\u0002J\t\u0010Å\u0002\u001a\u00020\u000fH\u0002J\u0015\u0010Æ\u0002\u001a\u00020M2\n\u0010\\\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0002J\u0007\u0010Ç\u0002\u001a\u00020MJ\u001a\u0010È\u0002\u001a\f\u0012\u0005\u0012\u00030Ê\u0002\u0018\u00010É\u00022\u0007\u0010\u0093\u0001\u001a\u00020cJ\t\u0010Ë\u0002\u001a\u00020MH\u0002J\u001e\u0010Ì\u0002\u001a\u00020M2\n\u0010\\\u001a\u0006\u0012\u0002\b\u00030\u00182\u0007\u0010Ü\u0001\u001a\u00020\u0007H\u0002J>\u0010Í\u0002\u001a\u00020M\"\u0011\b\u0000\u0010\u0097\u0001*\u0006\u0012\u0002\b\u00030\u0018*\u00020R*\u0006\u0012\u0002\b\u00030\u00182\u000f\u0010\u009f\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0097\u00010Î\u00022\u0007\u0010Ï\u0002\u001a\u00020-H\u0002J\u000f\u0010Ð\u0002\u001a\u0004\u0018\u00010X*\u00020zH\u0002J$\u0010Ñ\u0002\u001a\u00020M*\u00020z2\u0014\u0010m\u001a\u0010\u0012\u0005\u0012\u00030Ò\u0002\u0012\u0004\u0012\u00020M0 \u0001H\u0082\bR\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u0014\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0014\u0012\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0018\u0012\u0004\u0012\u00020\u00190\u00170\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001e\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001f\u0012\u0004\u0012\u00020 0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010%\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b%\u0010\tR\u0015\u0010&\u001a\u0006\u0012\u0002\b\u00030\u00188F¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0017\u0010)\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00188F¢\u0006\u0006\u001a\u0004\b*\u0010(R\u0018\u0010+\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010,\u001a\u00020-8F¢\u0006\u0006\u001a\u0004\b.\u0010/R\u001a\u00100\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u0002020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00103\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u0002040\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u00105\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0018\u0012\u0004\u0012\u00020706X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020:090\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R0\u0010;\u001a$\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001f\u0012\u0016\u0012\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0018\u0012\u0006\u0012\u0004\u0018\u00010<0\u00170\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010=\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0018\u0012\u0004\u0012\u00020\u00190\u0017090\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010>\u001a\b\u0012\u0004\u0012\u00020?0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010@\u001a\b\u0012\u0004\u0012\u00020A0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010B\u001a\u00020\u0007*\u00020\"8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bC\u0010DR\u001a\u0010E\u001a\u0004\u0018\u00010\u0007*\u00020F8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bG\u0010HR\u001c\u0010I\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010K¨\u0006Ó\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphBuilder;", "", "()V", "argumentListSplitNodes", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SplitPostponedLambdasNode;", "bodyBuildingMode", "", "getBodyBuildingMode", "()Z", "catchBlocksInProgress", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseEnterNode;", "catchNodes", "", "currentGraph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "getCurrentGraph", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "elvisRhsEnterNodes", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisRhsEnterNode;", "enterToLocalClassesMembers", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeKind;", "exitElvisExpressionNodes", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisExitNode;", "exitSafeCallNodes", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitSafeCallNode;", "exitTargetsForReturn", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;", "finallyBlocksInProgress", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockEnterNode;", "finallyEnterNodes", "graphs", "isTopLevel", "lastNode", "getLastNode", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "lastNodeOrNull", "getLastNodeOrNull", "lastNodes", "levelCounter", "", "getLevelCounter", "()I", "loopConditionEnterNodes", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionEnterNode;", "loopExitNodes", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;", "nonDirectJumps", "Lorg/jetbrains/kotlin/fir/util/ListMultimap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/JumpNode;", "notCompletedFunctionCalls", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "postponedAnonymousFunctionNodes", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PostponedLambdaExitNode;", "postponedLambdaExits", "tryExitNodes", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionExitNode;", "whenExitNodes", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenExitNode;", "allNormalInputsAreDead", "getAllNormalInputsAreDead", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockEnterNode;)Z", "booleanConstValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getBooleanConstValue", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Ljava/lang/Boolean;", "returnPathIsBackwards", "getReturnPathIsBackwards", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;)Z", "addBackEdge", "", TypedValues.TransitionType.S_FROM, "to", "isDead", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "addEdge", "propagateDeadness", "preferredKind", "addEdgeIfLocalClassMember", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "enterNode", "addEdgeToSubGraph", "addExceptionEdgesFrom", "node", "addNewSimpleNode", "addNonSuccessfullyTerminatingNode", "completeFunctionCall", "enterAnonymousFunction", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionEnterNode;", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "enterAnonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousFunctionExpressionNode;", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "enterBinaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "binaryLogicExpression", "enterBlock", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockEnterNode;", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "enterCall", "enterCallArguments", "fir", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "anonymousFunctions", "enterCatchClause", PsiKeyword.CATCH, "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", "enterClass", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassEnterNode;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "buildGraph", "enterCodeFragment", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentEnterNode;", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "enterDelegateExpression", "enterDoWhileLoop", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockEnterNode;", "loop", "enterDoWhileLoopCondition", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockExitNode;", "enterElvis", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "enterFakeExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FakeExpressionEnterNode;", "enterField", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerEnterNode;", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "enterFinallyBlock", "enterFunction", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalFunctionDeclarationNode;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "enterGraph", "EnterNode", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GraphEnterNodeMarker;", "ExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GraphExitNodeMarker;", "name", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph$Kind;", "nodes", "Lkotlin/Function1;", "(Ljava/lang/Object;Ljava/lang/String;Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph$Kind;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "enterInitBlock", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockEnterNode;", "initBlock", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "enterJump", "jump", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "enterProperty", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerEnterNode;", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "enterSafeCall", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterSafeCallNode;", "safeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "enterScript", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptEnterNode;", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "enterTryExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionEnterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockEnterNode;", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "enterValueParameter", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterValueParameterNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterDefaultArgumentsNode;", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "enterWhenBranchCondition", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionEnterNode;", "whenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "enterWhenExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenEnterNode;", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "enterWhileLoop", "exitAnonymousFunction", "Lkotlin/Triple;", "exitAnonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectExpressionExitNode;", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "exitBinaryLogicExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AbstractBinaryExitNode;", "exitBlock", "exitCallArguments", "exitCallableReference", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CallableReferenceNode;", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "exitCatchClause", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseExitNode;", "exitCheckNotNullCall", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CheckNotNullCallNode;", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "callCompleted", "exitClass", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassExitNode;", "exitCodeFragment", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentExitNode;", "exitComparisonExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ComparisonExpressionNode;", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "exitConstExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ConstExpressionNode;", "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "exitDelegateExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegateExpressionExitNode;", "exitDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegatedConstructorCallNode;", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "exitDoWhileLoop", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionExitNode;", "exitElvis", "lhsIsNotNull", "exitElvisLhs", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsIsNotNullNode;", "exitEqualityOperatorCall", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EqualityOperatorCallNode;", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "exitFakeExpression", "exitField", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerExitNode;", "exitFinallyBlock", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockExitNode;", "exitFunction", "exitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "exitGetClassCall", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GetClassCallNode;", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "exitGraph", "exitInitBlock", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockExitNode;", "exitJump", "exitLeftBinaryLogicExpressionArgument", "exitProperty", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerExitNode;", "exitQualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/QualifiedAccessNode;", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "exitResolvedQualifierNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ResolvedQualifierNode;", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "exitSafeCall", "exitScript", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptExitNode;", "exitSmartCastExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SmartCastExpressionExitNode;", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "exitStringConcatenationCall", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StringConcatenationCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "exitThrowExceptionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ThrowExceptionNode;", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "exitTryExpression", "exitTryMainBlock", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockExitNode;", "exitTypeOperatorCall", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TypeOperatorCallNode;", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "exitValueParameter", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitDefaultArgumentsNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitValueParameterNode;", "exitVariableAssignment", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "assignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "exitVariableDeclaration", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "variable", "exitWhenBranchCondition", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionExitNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultEnterNode;", "exitWhenBranchResult", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultExitNode;", "exitWhenExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSyntheticElseBranchNode;", "exitWhenSubjectExpression", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSubjectExpressionExitNode;", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "exitWhileLoop", "exitWhileLoopCondition", "levelOfNextExceptionCatchingGraph", "mergeDataFlowFromPostponedLambdas", "popAndAddEdge", "popGraph", "propagateDeadnessForward", "reset", "returnExpressionsOfAnonymousFunction", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FirAnonymousFunctionReturnExpressionInfo;", "splitDataFlowForPostponedLambdas", "unifyDataFlowFromPostponedLambdas", "addReturnEdges", "", "minLevel", "firstInPlaceInitializedMember", "forEachGraphOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowGraphBuilder {
    private final Stack<ControlFlowGraph> graphs = StackKt.stackOf(new ControlFlowGraph[0]);
    private final Stack<CFGNode<?>> lastNodes = StackKt.stackOf(new CFGNode[0]);
    private final Map<FirFunctionSymbol<?>, FunctionExitNode> exitTargetsForReturn = new LinkedHashMap();
    private final Map<FirBasedSymbol<?>, Pair<CFGNode<?>, EdgeKind>> enterToLocalClassesMembers = new LinkedHashMap();
    private final ListMultimap<CFGNode<?>, JumpNode> nonDirectJumps = MultimapKt.listMultimapOf();
    private final Stack<SplitPostponedLambdasNode> argumentListSplitNodes = StackKt.stackOf(new SplitPostponedLambdasNode[0]);
    private final Map<FirFunctionSymbol<?>, Pair<CFGNode<?>, PostponedLambdaExitNode>> postponedAnonymousFunctionNodes = new LinkedHashMap();
    private final Stack<List<Pair<CFGNode<?>, EdgeKind>>> postponedLambdaExits = StackKt.stackOf(new List[0]);
    private final Map<FirLoop, LoopConditionEnterNode> loopConditionEnterNodes = new LinkedHashMap();
    private final Map<FirLoop, LoopExitNode> loopExitNodes = new LinkedHashMap();
    private final Stack<WhenExitNode> whenExitNodes = StackKt.stackOf(new WhenExitNode[0]);
    private final Stack<TryExpressionExitNode> tryExitNodes = StackKt.stackOf(new TryExpressionExitNode[0]);
    private final Stack<List<CatchClauseEnterNode>> catchNodes = StackKt.stackOf(new List[0]);
    private final Stack<CatchClauseEnterNode> catchBlocksInProgress = StackKt.stackOf(new CatchClauseEnterNode[0]);
    private final Stack<FinallyBlockEnterNode> finallyEnterNodes = StackKt.stackOf(new FinallyBlockEnterNode[0]);
    private final Stack<FinallyBlockEnterNode> finallyBlocksInProgress = StackKt.stackOf(new FinallyBlockEnterNode[0]);
    private final Stack<ExitSafeCallNode> exitSafeCallNodes = StackKt.stackOf(new ExitSafeCallNode[0]);
    private final Stack<ElvisExitNode> exitElvisExpressionNodes = StackKt.stackOf(new ElvisExitNode[0]);
    private final Stack<ElvisRhsEnterNode> elvisRhsEnterNodes = StackKt.stackOf(new ElvisRhsEnterNode[0]);
    private final Stack<List<FunctionCallNode>> notCompletedFunctionCalls = StackKt.stackOf(new List[0]);

    /* JADX INFO: compiled from: ControlFlowGraphBuilder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LogicOperationKind.values().length];
            try {
                iArr[LogicOperationKind.AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LogicOperationKind.OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final boolean isTopLevel() {
        return StackKt.isEmpty(this.graphs);
    }

    public final ControlFlowGraph getCurrentGraph() {
        return this.graphs.top();
    }

    private final boolean getBodyBuildingMode() {
        return StackKt.isNotEmpty(this.graphs) && getCurrentGraph().getKind() != ControlFlowGraph.Kind.Class;
    }

    public final int getLevelCounter() {
        return this.graphs.getSize() + this.tryExitNodes.getSize();
    }

    public final CFGNode<?> getLastNode() {
        return this.lastNodes.top();
    }

    public final CFGNode<?> getLastNodeOrNull() {
        return (CFGNode) StackKt.topOrNull(this.lastNodes);
    }

    public final Collection<FirAnonymousFunctionReturnExpressionInfo> returnExpressionsOfAnonymousFunction(FirAnonymousFunction function) {
        ControlFlowGraph controlFlowGraph;
        CFGNode<?> exitNode;
        Intrinsics.checkNotNullParameter(function, "function");
        FirControlFlowGraphReference controlFlowGraphReference = function.getControlFlowGraphReference();
        if (controlFlowGraphReference == null || (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) == null || (exitNode = controlFlowGraph.getExitNode()) == null) {
            return null;
        }
        List<CFGNode<?>> previousNodes = exitNode.getPreviousNodes();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (CFGNode<?> cFGNode : previousNodes) {
            Edge edgeEdgeFrom = exitNode.edgeFrom(cFGNode);
            if (!edgeEdgeFrom.getKind().getUsedInCfa() || !Intrinsics.areEqual(edgeEdgeFrom.getLabel(), NormalPath.INSTANCE)) {
                cFGNode = null;
            }
            FirAnonymousFunctionReturnExpressionInfo firAnonymousFunctionReturnExpressionInfoReturnExpressionsOfAnonymousFunction$returnExpression = cFGNode != null ? returnExpressionsOfAnonymousFunction$returnExpression(cFGNode, function) : null;
            if (firAnonymousFunctionReturnExpressionInfoReturnExpressionsOfAnonymousFunction$returnExpression != null) {
                linkedHashSet.add(firAnonymousFunctionReturnExpressionInfoReturnExpressionsOfAnonymousFunction$returnExpression);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        Iterator it = this.nonDirectJumps.get(exitNode).iterator();
        while (it.hasNext()) {
            FirAnonymousFunctionReturnExpressionInfo firAnonymousFunctionReturnExpressionInfoReturnExpressionsOfAnonymousFunction$returnExpression2 = returnExpressionsOfAnonymousFunction$returnExpression((JumpNode) it.next(), function);
            if (firAnonymousFunctionReturnExpressionInfoReturnExpressionsOfAnonymousFunction$returnExpression2 != null) {
                linkedHashSet2.add(firAnonymousFunctionReturnExpressionInfoReturnExpressionsOfAnonymousFunction$returnExpression2);
            }
        }
        return linkedHashSet2;
    }

    private static final FirAnonymousFunctionReturnExpressionInfo returnExpressionsOfAnonymousFunction$returnExpression(CFGNode<?> cFGNode, FirAnonymousFunction firAnonymousFunction) {
        FirExpression result;
        KtSourceElement source;
        if (cFGNode instanceof BlockExitNode) {
            if (firAnonymousFunction.getIsLambda()) {
                BlockExitNode blockExitNode = (BlockExitNode) cFGNode;
                FirStatement firStatement = (FirStatement) CollectionsKt.lastOrNull((List) blockExitNode.getFir().getStatements());
                if (firStatement instanceof FirReturnExpression) {
                    FirReturnExpression firReturnExpression = (FirReturnExpression) firStatement;
                    if (Intrinsics.areEqual(((FirFunction) firReturnExpression.getTarget().getLabeledElement()).getSymbol(), firAnonymousFunction.getSymbol())) {
                        KtSourceElement source2 = firReturnExpression.getSource();
                        if (!Intrinsics.areEqual(source2 != null ? source2.getKind() : null, KtFakeSourceElementKind.ImplicitReturn.FromLastStatement.INSTANCE)) {
                            return null;
                        }
                    }
                }
                FirExpression firExpressionMo6809build = firStatement instanceof FirExpression ? (FirExpression) firStatement : null;
                if (firExpressionMo6809build == null) {
                    FirUnitExpressionBuilder firUnitExpressionBuilder = new FirUnitExpressionBuilder();
                    FirStatement firStatement2 = (FirStatement) CollectionsKt.lastOrNull((List) blockExitNode.getFir().getStatements());
                    if (firStatement2 == null || (source = firStatement2.getSource()) == null) {
                        source = blockExitNode.getFir().getSource();
                    }
                    firUnitExpressionBuilder.setSource(source);
                    firExpressionMo6809build = firUnitExpressionBuilder.mo6809build();
                }
                return new FirAnonymousFunctionReturnExpressionInfo(firExpressionMo6809build, false);
            }
            FirStub firStub = FirStub.INSTANCE;
            List<CFGNode<?>> previousNodes = cFGNode.getPreviousNodes();
            if (!(previousNodes instanceof Collection) || !previousNodes.isEmpty()) {
                Iterator<T> it = previousNodes.iterator();
                while (it.hasNext()) {
                    if (!(((CFGNode) it.next()) instanceof StubNode)) {
                        firStub = null;
                        break;
                    }
                }
            }
            if (firStub != null) {
                return new FirAnonymousFunctionReturnExpressionInfo(firStub, false);
            }
            return null;
        }
        if (!(cFGNode instanceof JumpNode)) {
            return null;
        }
        FirJump<?> fir = ((JumpNode) cFGNode).getFir();
        FirReturnExpression firReturnExpression2 = fir instanceof FirReturnExpression ? (FirReturnExpression) fir : null;
        if (firReturnExpression2 == null) {
            return null;
        }
        if (!Intrinsics.areEqual(((FirFunction) firReturnExpression2.getTarget().getLabeledElement()).getSymbol(), firAnonymousFunction.getSymbol())) {
            firReturnExpression2 = null;
        }
        if (firReturnExpression2 == null || (result = firReturnExpression2.getResult()) == null) {
            return null;
        }
        return new FirAnonymousFunctionReturnExpressionInfo(result, true);
    }

    private final ControlFlowGraph popGraph() {
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        controlFlowGraphPop.complete();
        return controlFlowGraphPop;
    }

    public final Pair<LocalFunctionDeclarationNode, FunctionEnterNode> enterFunction(FirFunction function) {
        String strAsString;
        LocalFunctionDeclarationNode localFunctionDeclarationNodeCreateLocalFunctionDeclarationNode;
        Intrinsics.checkNotNullParameter(function, "function");
        if (function instanceof FirAnonymousFunction) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        boolean z = function instanceof FirSimpleFunction;
        if (z) {
            strAsString = ((FirSimpleFunction) function).getName().asString();
        } else if (function instanceof FirPropertyAccessor) {
            strAsString = ((FirPropertyAccessor) function).getIsGetter() ? "<getter>" : "<setter>";
        } else {
            if (!(function instanceof FirConstructor)) {
                throw new IllegalArgumentException("Unknown function: " + UtilsKt.render(function));
            }
            strAsString = "<init>";
        }
        Intrinsics.checkNotNull(strAsString);
        if (z && Intrinsics.areEqual(((FirSimpleFunction) function).getStatus().getVisibility(), Visibilities.Local.INSTANCE) && getBodyBuildingMode()) {
            localFunctionDeclarationNodeCreateLocalFunctionDeclarationNode = ControlFlowGraphNodeBuilderKt.createLocalFunctionDeclarationNode(this, function);
            addNewSimpleNode$default(this, localFunctionDeclarationNodeCreateLocalFunctionDeclarationNode, false, 2, null);
        } else {
            localFunctionDeclarationNodeCreateLocalFunctionDeclarationNode = null;
        }
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(function instanceof FirDeclaration ? function : null, strAsString, localFunctionDeclarationNodeCreateLocalFunctionDeclarationNode != null ? ControlFlowGraph.Kind.LocalFunction : ControlFlowGraph.Kind.Function);
        this.graphs.push(controlFlowGraph);
        FunctionEnterNode functionEnterNodeCreateFunctionEnterNode = ControlFlowGraphNodeBuilderKt.createFunctionEnterNode(this, function);
        FunctionExitNode functionExitNodeCreateFunctionExitNode = ControlFlowGraphNodeBuilderKt.createFunctionExitNode(this, function);
        this.exitTargetsForReturn.put(function.getSymbol(), functionExitNodeCreateFunctionExitNode);
        Pair pair = TuplesKt.to(functionEnterNodeCreateFunctionEnterNode, functionExitNodeCreateFunctionExitNode);
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        FunctionEnterNode functionEnterNode = (FunctionEnterNode) cFGNode;
        if (localFunctionDeclarationNodeCreateLocalFunctionDeclarationNode != null) {
            addEdge$default(this, localFunctionDeclarationNodeCreateLocalFunctionDeclarationNode, functionEnterNode, false, false, null, null, 60, null);
        } else {
            addEdgeIfLocalClassMember(functionEnterNode);
        }
        return new Pair<>(localFunctionDeclarationNodeCreateLocalFunctionDeclarationNode, functionEnterNode);
    }

    public final Pair<FunctionExitNode, ControlFlowGraph> exitFunction(FirFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        if (function instanceof FirAnonymousFunction) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.exitTargetsForReturn.remove(function.getSymbol());
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        CFGNode<?> exitNode = controlFlowGraphPop.getExitNode();
        if (exitNode == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionExitNode");
        }
        FunctionExitNode functionExitNode = (FunctionExitNode) exitNode;
        popAndAddEdge$default(this, functionExitNode, null, 2, null);
        if (functionExitNode.getPreviousNodes().size() > 1) {
            functionExitNode.updateDeadStatus();
        }
        controlFlowGraphPop.complete();
        return TuplesKt.to(functionExitNode, controlFlowGraphPop);
    }

    public final AnonymousFunctionExpressionNode enterAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression) {
        CFGNode<?> first;
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        FirAnonymousFunctionSymbol symbol = anonymousFunctionExpression.getAnonymousFunction().getSymbol();
        Pair<CFGNode<?>, PostponedLambdaExitNode> pair = this.postponedAnonymousFunctionNodes.get(symbol);
        if (pair == null || (first = pair.getFirst()) == null) {
            AnonymousFunctionExpressionNode anonymousFunctionExpressionNodeCreateAnonymousFunctionExpressionNode = ControlFlowGraphNodeBuilderKt.createAnonymousFunctionExpressionNode(this, anonymousFunctionExpression);
            addNewSimpleNode$default(this, anonymousFunctionExpressionNodeCreateAnonymousFunctionExpressionNode, false, 2, null);
            this.postponedAnonymousFunctionNodes.put(symbol, TuplesKt.to(anonymousFunctionExpressionNodeCreateAnonymousFunctionExpressionNode, null));
            return anonymousFunctionExpressionNodeCreateAnonymousFunctionExpressionNode;
        }
        PostponedLambdaExitNode postponedLambdaExitNodeCreatePostponedLambdaExitNode = ControlFlowGraphNodeBuilderKt.createPostponedLambdaExitNode(this, anonymousFunctionExpression);
        addEdge$default(this, first, postponedLambdaExitNodeCreatePostponedLambdaExitNode, false, false, null, null, 60, null);
        this.postponedAnonymousFunctionNodes.put(symbol, TuplesKt.to(first, postponedLambdaExitNodeCreatePostponedLambdaExitNode));
        this.postponedLambdaExits.top().add(TuplesKt.to(postponedLambdaExitNodeCreatePostponedLambdaExitNode, EdgeKind.Forward));
        return null;
    }

    public final FunctionEnterNode enterAnonymousFunction(FirAnonymousFunction anonymousFunction) {
        ControlFlowGraph.Kind kind;
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        if (EventOccurrencesRangeKt.isInPlace(anonymousFunction.getInvocationKind())) {
            kind = ControlFlowGraph.Kind.AnonymousFunctionCalledInPlace;
        } else {
            kind = ControlFlowGraph.Kind.AnonymousFunction;
        }
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(anonymousFunction instanceof FirDeclaration ? anonymousFunction : null, SpecialNames.ANONYMOUS_STRING, kind);
        this.graphs.push(controlFlowGraph);
        FirAnonymousFunction firAnonymousFunction = anonymousFunction;
        FunctionEnterNode functionEnterNodeCreateFunctionEnterNode = ControlFlowGraphNodeBuilderKt.createFunctionEnterNode(this, firAnonymousFunction);
        FunctionExitNode functionExitNodeCreateFunctionExitNode = ControlFlowGraphNodeBuilderKt.createFunctionExitNode(this, firAnonymousFunction);
        this.exitTargetsForReturn.put(anonymousFunction.getSymbol(), functionExitNodeCreateFunctionExitNode);
        Pair pair = TuplesKt.to(functionEnterNodeCreateFunctionEnterNode, functionExitNodeCreateFunctionExitNode);
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        FunctionEnterNode functionEnterNode = (FunctionEnterNode) cFGNode;
        addEdge$default(this, (CFGNode) ((Pair) MapsKt.getValue(this.postponedAnonymousFunctionNodes, anonymousFunction.getSymbol())).getFirst(), functionEnterNode, false, false, null, null, 60, null);
        return functionEnterNode;
    }

    public final Triple<FunctionExitNode, PostponedLambdaExitNode, ControlFlowGraph> exitAnonymousFunction(FirAnonymousFunction anonymousFunction) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        this.exitTargetsForReturn.remove(anonymousFunction.getSymbol());
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        CFGNode<?> exitNode = controlFlowGraphPop.getExitNode();
        if (exitNode == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionExitNode");
        }
        FunctionExitNode functionExitNode = (FunctionExitNode) exitNode;
        popAndAddEdge$default(this, functionExitNode, null, 2, null);
        if (functionExitNode.getPreviousNodes().size() > 1) {
            functionExitNode.updateDeadStatus();
        }
        controlFlowGraphPop.complete();
        Pair pair = TuplesKt.to(functionExitNode, controlFlowGraphPop);
        FunctionExitNode functionExitNode2 = (FunctionExitNode) pair.component1();
        ControlFlowGraph controlFlowGraph = (ControlFlowGraph) pair.component2();
        Pair<CFGNode<?>, PostponedLambdaExitNode> pairRemove = this.postponedAnonymousFunctionNodes.remove(anonymousFunction.getSymbol());
        Intrinsics.checkNotNull(pairRemove);
        Pair<CFGNode<?>, PostponedLambdaExitNode> pair2 = pairRemove;
        CFGNode<?> cFGNodeComponent1 = pair2.component1();
        PostponedLambdaExitNode postponedLambdaExitNodeComponent2 = pair2.component2();
        EventOccurrencesRange invocationKind = anonymousFunction.getInvocationKind();
        if (postponedLambdaExitNodeComponent2 == null) {
            return new Triple<>(functionExitNode2, null, controlFlowGraph);
        }
        boolean z = invocationKind != null && EventOccurrencesRangeKt.isDefinitelyVisited(invocationKind);
        if (z || cFGNodeComponent1.getIsDead()) {
            CFGNode.INSTANCE.killEdge(cFGNodeComponent1, postponedLambdaExitNodeComponent2, !z);
        }
        if (invocationKind != null && EventOccurrencesRangeKt.canBeVisited(invocationKind)) {
            PostponedLambdaExitNode postponedLambdaExitNode = postponedLambdaExitNodeComponent2;
            addEdge$default(this, functionExitNode2, postponedLambdaExitNode, z, false, null, null, 56, null);
            if (EventOccurrencesRangeKt.canBeRevisited(invocationKind)) {
                addBackEdge$default(this, postponedLambdaExitNode, cFGNodeComponent1, false, null, 12, null);
            }
        }
        return new Triple<>(functionExitNode2, postponedLambdaExitNodeComponent2, controlFlowGraph);
    }

    private final void splitDataFlowForPostponedLambdas() {
        this.postponedLambdaExits.push(new ArrayList());
    }

    private final void unifyDataFlowFromPostponedLambdas(CFGNode<?> node, boolean callCompleted) {
        List<Pair<CFGNode<?>, EdgeKind>> listPop = this.postponedLambdaExits.pop();
        if (listPop.isEmpty()) {
            return;
        }
        Object obj = StackKt.topOrNull(this.postponedLambdaExits);
        if (callCompleted) {
            obj = null;
        }
        List list = (List) obj;
        if (list != null) {
            for (Pair<CFGNode<?>, EdgeKind> pair : listPop) {
                CFGNode<?> cFGNodeComponent1 = pair.component1();
                if (pair.component2().getUsedInCfa()) {
                    addEdge$default(this, cFGNodeComponent1, node, false, false, EdgeKind.CfgForward, null, 44, null);
                }
                list.add(TuplesKt.to(cFGNodeComponent1, EdgeKind.DfgForward));
            }
            return;
        }
        for (Pair<CFGNode<?>, EdgeKind> pair2 : listPop) {
            CFGNode<?> cFGNodeComponent2 = pair2.component1();
            EdgeKind edgeKindComponent2 = pair2.component2();
            if (edgeKindComponent2.getUsedInCfa() || !cFGNodeComponent2.getIsDead()) {
                addEdge$default(this, cFGNodeComponent2, node, false, false, edgeKindComponent2, null, 44, null);
            }
        }
    }

    private final void mergeDataFlowFromPostponedLambdas(CFGNode<?> node, boolean callCompleted) {
        List<Pair<CFGNode<?>, EdgeKind>> listPop = this.postponedLambdaExits.pop();
        if (listPop.isEmpty()) {
            return;
        }
        Object obj = StackKt.topOrNull(this.postponedLambdaExits);
        if (callCompleted) {
            obj = null;
        }
        List list = (List) obj;
        if (list != null) {
            node.updateDeadStatus();
            List list2 = list;
            MergePostponedLambdaExitsNode mergePostponedLambdaExitsNodeCreateMergePostponedLambdaExitsNode = ControlFlowGraphNodeBuilderKt.createMergePostponedLambdaExitsNode(this, node.getFir());
            MergePostponedLambdaExitsNode mergePostponedLambdaExitsNode = mergePostponedLambdaExitsNodeCreateMergePostponedLambdaExitsNode;
            addEdge$default(this, node, mergePostponedLambdaExitsNode, false, false, null, null, 60, null);
            for (Pair<CFGNode<?>, EdgeKind> pair : listPop) {
                CFGNode<?> cFGNodeComponent1 = pair.component1();
                if (pair.component2().getUsedInCfa()) {
                    addEdge$default(this, cFGNodeComponent1, node, false, false, EdgeKind.CfgForward, null, 40, null);
                }
                addEdge$default(this, cFGNodeComponent1, mergePostponedLambdaExitsNode, false, false, EdgeKind.DfgForward, null, 40, null);
            }
            list2.add(TuplesKt.to(mergePostponedLambdaExitsNodeCreateMergePostponedLambdaExitsNode, EdgeKind.DfgForward));
            return;
        }
        for (Pair<CFGNode<?>, EdgeKind> pair2 : listPop) {
            addEdge$default(this, pair2.component1(), node, false, false, pair2.component2(), null, 40, null);
        }
    }

    private final FirDeclaration firstInPlaceInitializedMember(FirClass firClass) {
        Object next;
        Iterator<T> it = firClass.getDeclarations().iterator();
        while (it.hasNext()) {
            next = it.next();
            FirAnnotationContainer firAnnotationContainer = (FirDeclaration) next;
            if ((firAnnotationContainer instanceof FirControlFlowGraphOwner) && !(firAnnotationContainer instanceof FirConstructor) && ControlFlowGraphBuilderKt.isUsedInControlFlowGraphBuilderForClass((FirControlFlowGraphOwner) firAnnotationContainer)) {
                return (FirDeclaration) next;
            }
        }
        next = null;
        return (FirDeclaration) next;
    }

    private final <E extends FirDeclaration> void addEdgeIfLocalClassMember(CFGNode<? extends E> enterNode) {
        Pair<CFGNode<?>, EdgeKind> pairRemove = this.enterToLocalClassesMembers.remove(((FirDeclaration) enterNode.getFir()).getSymbol());
        if (pairRemove == null) {
            return;
        }
        addEdge$default(this, pairRemove.component1(), enterNode, false, false, pairRemove.component2(), null, 44, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair<CFGNode<?>, ClassEnterNode> enterClass(FirClass klass, boolean buildGraph) {
        LocalClassExitNode localClassExitNodeCreateLocalClassExitNode;
        String strAsString;
        FirDelegatedConstructorCall delegatedConstructor;
        FirDelegatedConstructorCall delegatedConstructor2;
        FirDelegatedConstructorCall delegatedConstructor3;
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (!buildGraph) {
            this.graphs.push(new ControlFlowGraph(null, "<discarded class graph>", ControlFlowGraph.Kind.Class));
            return TuplesKt.to(null, null);
        }
        boolean z = klass instanceof FirAnonymousObject;
        if (!z || klass.getClassKind() == ClassKind.ENUM_ENTRY) {
            localClassExitNodeCreateLocalClassExitNode = ((klass instanceof FirRegularClass) && klass.getSymbol().getClassId().isLocal() && getBodyBuildingMode()) ? ControlFlowGraphNodeBuilderKt.createLocalClassExitNode(this, (FirRegularClass) klass) : null;
        } else {
            localClassExitNodeCreateLocalClassExitNode = ControlFlowGraphNodeBuilderKt.createAnonymousObjectEnterNode(this, (FirAnonymousObject) klass);
        }
        if (localClassExitNodeCreateLocalClassExitNode != null) {
            addNewSimpleNode$default(this, localClassExitNodeCreateLocalClassExitNode, false, 2, null);
        } else {
            localClassExitNodeCreateLocalClassExitNode = null;
        }
        if (z) {
            strAsString = "<anonymous object>";
        } else {
            if (!(klass instanceof FirRegularClass)) {
                throw new IllegalArgumentException("Unknown class kind: " + Reflection.getOrCreateKotlinClass(klass.getClass()));
            }
            strAsString = ((FirRegularClass) klass).getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(klass instanceof FirDeclaration ? klass : null, strAsString, ControlFlowGraph.Kind.Class);
        this.graphs.push(controlFlowGraph);
        Pair pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createClassEnterNode(this, klass), ControlFlowGraphNodeBuilderKt.createClassExitNode(this, klass));
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        ClassEnterNode classEnterNode = (ClassEnterNode) cFGNode;
        if (localClassExitNodeCreateLocalClassExitNode != null) {
            addEdge$default(this, localClassExitNodeCreateLocalClassExitNode, classEnterNode, false, false, null, null, 60, null);
        } else {
            addEdgeIfLocalClassMember(classEnterNode);
        }
        if (!classEnterNode.getPreviousNodes().isEmpty()) {
            FirDeclaration firDeclarationFirstInPlaceInitializedMember = firstInPlaceInitializedMember(klass);
            for (FirAnnotationContainer firAnnotationContainer : klass.getDeclarations()) {
                if (firAnnotationContainer instanceof FirControlFlowGraphOwner) {
                    FirControlFlowGraphOwner firControlFlowGraphOwner = (FirControlFlowGraphOwner) firAnnotationContainer;
                    if (ControlFlowGraphBuilderKt.getMemberShouldHaveGraph(firControlFlowGraphOwner)) {
                        this.enterToLocalClassesMembers.put(((FirDeclaration) firControlFlowGraphOwner).getSymbol(), TuplesKt.to(classEnterNode, (Intrinsics.areEqual(firDeclarationFirstInPlaceInitializedMember, firControlFlowGraphOwner) || ((firControlFlowGraphOwner instanceof FirConstructor) && (firDeclarationFirstInPlaceInitializedMember == null || ((delegatedConstructor3 = ((FirConstructor) firControlFlowGraphOwner).getDelegatedConstructor()) != null && delegatedConstructor3.getIsThis())))) ? EdgeKind.Forward : EdgeKind.DfgForward));
                    }
                }
                if (firAnnotationContainer instanceof FirProperty) {
                    FirProperty firProperty = (FirProperty) firAnnotationContainer;
                    FirPropertyAccessor getter = firProperty.getGetter();
                    if (getter != null) {
                        FirPropertyAccessor firPropertyAccessor = getter;
                        EdgeKind edgeKind = (Intrinsics.areEqual(firDeclarationFirstInPlaceInitializedMember, firPropertyAccessor) || ((firPropertyAccessor instanceof FirConstructor) && (firDeclarationFirstInPlaceInitializedMember == null || ((delegatedConstructor2 = ((FirConstructor) firPropertyAccessor).getDelegatedConstructor()) != null && delegatedConstructor2.getIsThis())))) ? EdgeKind.Forward : EdgeKind.DfgForward;
                        Map<FirBasedSymbol<?>, Pair<CFGNode<?>, EdgeKind>> map = this.enterToLocalClassesMembers;
                        Intrinsics.checkNotNull(firPropertyAccessor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclaration");
                        map.put(firPropertyAccessor.getSymbol(), TuplesKt.to(classEnterNode, edgeKind));
                    }
                    FirPropertyAccessor setter = firProperty.getSetter();
                    if (setter != null) {
                        FirPropertyAccessor firPropertyAccessor2 = setter;
                        EdgeKind edgeKind2 = (Intrinsics.areEqual(firDeclarationFirstInPlaceInitializedMember, firPropertyAccessor2) || ((firPropertyAccessor2 instanceof FirConstructor) && (firDeclarationFirstInPlaceInitializedMember == null || ((delegatedConstructor = ((FirConstructor) firPropertyAccessor2).getDelegatedConstructor()) != null && delegatedConstructor.getIsThis())))) ? EdgeKind.Forward : EdgeKind.DfgForward;
                        Map<FirBasedSymbol<?>, Pair<CFGNode<?>, EdgeKind>> map2 = this.enterToLocalClassesMembers;
                        Intrinsics.checkNotNull(firPropertyAccessor2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclaration");
                        map2.put(firPropertyAccessor2.getSymbol(), TuplesKt.to(classEnterNode, edgeKind2));
                    }
                }
            }
        }
        return TuplesKt.to(localClassExitNodeCreateLocalClassExitNode, classEnterNode);
    }

    public final Pair<ClassExitNode, ControlFlowGraph> exitClass() {
        LinkedHashMap linkedHashMap;
        LinkedHashMap linkedHashMap2;
        CFGNode<?> enterNode;
        FirPropertyAccessor firPropertyAccessor;
        FirControlFlowGraphReference controlFlowGraphReference;
        ControlFlowGraph controlFlowGraph;
        FirPropertyAccessor firPropertyAccessor2;
        FirControlFlowGraphReference controlFlowGraphReference2;
        ControlFlowGraph controlFlowGraph2;
        FirControlFlowGraphReference controlFlowGraphReference3;
        ControlFlowGraph controlFlowGraph3;
        getCurrentGraph().getKind();
        ControlFlowGraph.Kind kind = ControlFlowGraph.Kind.Class;
        if (getCurrentGraph().getDeclaration() == null) {
            this.graphs.pop();
            return TuplesKt.to(null, null);
        }
        CFGNode<?> cFGNodePop = this.lastNodes.pop();
        Intrinsics.checkNotNull(cFGNodePop, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.ClassEnterNode");
        ClassEnterNode classEnterNode = (ClassEnterNode) cFGNodePop;
        CFGNode<?> exitNode = getCurrentGraph().getExitNode();
        Intrinsics.checkNotNull(exitNode, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.ClassExitNode");
        ClassExitNode classExitNode = (ClassExitNode) exitNode;
        FirClass fir = classEnterNode.getFir();
        Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner");
        if (fir.getControlFlowGraphReference() != null) {
            this.graphs.pop();
            return TuplesKt.to(null, null);
        }
        boolean zIsLocal = fir.getSymbol().getClassId().isLocal();
        ArrayList<ControlFlowGraph> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        Iterator<FirDeclaration> it = fir.getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            FirAnnotationContainer firAnnotationContainer = (FirDeclaration) it.next();
            if (firAnnotationContainer instanceof FirControlFlowGraphOwner) {
                FirControlFlowGraphOwner firControlFlowGraphOwner = (FirControlFlowGraphOwner) firAnnotationContainer;
                if (ControlFlowGraphBuilderKt.getMemberShouldHaveGraph(firControlFlowGraphOwner) && (controlFlowGraphReference3 = firControlFlowGraphOwner.getControlFlowGraphReference()) != null && (controlFlowGraph3 = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference3)) != null) {
                    if (firControlFlowGraphOwner instanceof FirConstructor) {
                        linkedHashMap3.put(firControlFlowGraphOwner, controlFlowGraph3);
                    } else if (!(firControlFlowGraphOwner instanceof FirPropertyAccessor ? true : firControlFlowGraphOwner instanceof FirFunction ? true : firControlFlowGraphOwner instanceof FirClass)) {
                        arrayList.add(controlFlowGraph3);
                    } else if (zIsLocal) {
                        arrayList2.add(controlFlowGraph3);
                    }
                }
            }
            if (firAnnotationContainer instanceof FirProperty) {
                FirProperty firProperty = (FirProperty) firAnnotationContainer;
                FirPropertyAccessor getter = firProperty.getGetter();
                if (getter != null && (controlFlowGraphReference2 = (firPropertyAccessor2 = getter).getControlFlowGraphReference()) != null && (controlFlowGraph2 = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference2)) != null) {
                    if (firPropertyAccessor2 instanceof FirConstructor) {
                        linkedHashMap3.put(firPropertyAccessor2, controlFlowGraph2);
                    } else if (!(firPropertyAccessor2 instanceof FirPropertyAccessor ? true : firPropertyAccessor2 instanceof FirFunction ? true : firPropertyAccessor2 instanceof FirClass)) {
                        arrayList.add(controlFlowGraph2);
                    } else if (zIsLocal) {
                        arrayList2.add(controlFlowGraph2);
                    }
                }
                FirPropertyAccessor setter = firProperty.getSetter();
                if (setter != null && (controlFlowGraphReference = (firPropertyAccessor = setter).getControlFlowGraphReference()) != null && (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) != null) {
                    if (firPropertyAccessor instanceof FirConstructor) {
                        linkedHashMap3.put(firPropertyAccessor, controlFlowGraph);
                    } else if (!(firPropertyAccessor instanceof FirPropertyAccessor ? true : firPropertyAccessor instanceof FirFunction ? true : firPropertyAccessor instanceof FirClass)) {
                        arrayList.add(controlFlowGraph);
                    } else if (zIsLocal) {
                        arrayList2.add(controlFlowGraph);
                    }
                }
            }
        }
        CFGNode<?> exitNode2 = classEnterNode;
        for (ControlFlowGraph controlFlowGraph4 : arrayList) {
            if (exitNode2 != classEnterNode || exitNode2.getPreviousNodes().isEmpty()) {
                addEdgeToSubGraph$default(this, exitNode2, controlFlowGraph4.getEnterNode(), null, 4, null);
            }
            exitNode2 = controlFlowGraph4.getExitNode();
        }
        if (classExitNode.isUnion()) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                addEdge$default(this, ((ControlFlowGraph) it2.next()).getExitNode(), classExitNode, false, false, EdgeKind.DfgForward, null, 44, null);
                exitNode2 = exitNode2;
                linkedHashMap3 = linkedHashMap3;
            }
        }
        CFGNode<?> cFGNode = exitNode2;
        LinkedHashMap linkedHashMap4 = linkedHashMap3;
        LinkedHashMap linkedHashMap5 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap4.entrySet()) {
            FirConstructor firConstructor = (FirConstructor) entry.getKey();
            ControlFlowGraph controlFlowGraph5 = (ControlFlowGraph) entry.getValue();
            LinkedHashMap linkedHashMap6 = linkedHashMap4;
            ControlFlowGraph controlFlowGraph6 = (ControlFlowGraph) linkedHashMap6.get(exitClass$parentConstructor(firConstructor, linkedHashMap5));
            if (controlFlowGraph6 != null) {
                ControlFlowGraph controlFlowGraph7 = (ControlFlowGraph) CollectionsKt.firstOrNull((List) arrayList);
                if (controlFlowGraph7 == null || (enterNode = controlFlowGraph7.getEnterNode()) == null) {
                    enterNode = controlFlowGraph6.getEnterNode();
                }
                CFGNode<?> cFGNode2 = enterNode;
                CFGNode<?> exitNode3 = controlFlowGraph6.getExitNode();
                boolean z = false;
                Object obj = null;
                for (Object obj2 : controlFlowGraph5.getNodes()) {
                    if (((CFGNode) obj2) instanceof DelegatedConstructorCallNode) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        obj = obj2;
                        z = true;
                    }
                }
                if (z) {
                    CFGNode<?> cFGNode3 = (CFGNode) obj;
                    CFGNode<?> exitNode4 = controlFlowGraph5.getExitNode();
                    Intrinsics.checkNotNull(exitNode4, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionExitNode");
                    FunctionExitNode functionExitNode = (FunctionExitNode) exitNode4;
                    List<CFGNode> list = CollectionsKt.toList(cFGNode3.getFollowingNodes());
                    CFGNode.INSTANCE.removeAllOutgoingEdges(cFGNode3);
                    if (!zIsLocal) {
                        addEdgeToSubGraph$default(this, classEnterNode, controlFlowGraph5.getEnterNode(), null, 4, null);
                    }
                    FunctionExitNode functionExitNode2 = functionExitNode;
                    addEdgeToSubGraph(cFGNode3, cFGNode2, functionExitNode2);
                    for (CFGNode cFGNode4 : list) {
                        FunctionExitNode functionExitNode3 = functionExitNode2;
                        LinkedHashMap linkedHashMap7 = linkedHashMap5;
                        CFGNode<?> cFGNode5 = cFGNode3;
                        addEdge$default(this, exitNode3, cFGNode4, false, false, EdgeKind.CfgForward, functionExitNode3, 12, null);
                        addEdge$default(this, cFGNode5, cFGNode4, false, false, EdgeKind.DfgForward, null, 44, null);
                        cFGNode3 = cFGNode5;
                        functionExitNode2 = functionExitNode3;
                        linkedHashMap6 = linkedHashMap6;
                        linkedHashMap5 = linkedHashMap7;
                    }
                    linkedHashMap = linkedHashMap6;
                    linkedHashMap2 = linkedHashMap5;
                } else {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
            } else {
                linkedHashMap = linkedHashMap6;
                linkedHashMap2 = linkedHashMap5;
                if (cFGNode != classEnterNode || cFGNode.getPreviousNodes().isEmpty()) {
                    addEdgeToSubGraph$default(this, cFGNode, controlFlowGraph5.getEnterNode(), null, 4, null);
                }
            }
            addEdge$default(this, controlFlowGraph5.getExitNode(), classExitNode, false, false, classExitNode.isUnion() ? EdgeKind.Forward : EdgeKind.CfgForward, null, 44, null);
            linkedHashMap5 = linkedHashMap2;
            linkedHashMap4 = linkedHashMap;
        }
        LinkedHashMap linkedHashMap8 = linkedHashMap4;
        if (linkedHashMap8.isEmpty()) {
            addEdge$default(this, classEnterNode, classExitNode, false, false, EdgeKind.CfgForward, null, 44, null);
        } else {
            addEdge$default(this, classEnterNode, classExitNode, false, false, EdgeKind.DeadForward, null, 40, null);
        }
        Iterator<ControlFlowGraph> it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            addEdgeToSubGraph$default(this, classExitNode, it3.next().getEnterNode(), null, 4, null);
        }
        classEnterNode.setSubGraphs(CollectionsKt.plus((Collection) arrayList, (Iterable) linkedHashMap8.values()));
        classExitNode.setSubGraphs(arrayList2);
        return TuplesKt.to(classExitNode.isUnion() ? classExitNode : null, popGraph());
    }

    public final AnonymousObjectExpressionExitNode exitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression) {
        ControlFlowGraph controlFlowGraph;
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        FirAnonymousObject anonymousObject = anonymousObjectExpression.getAnonymousObject();
        if (anonymousObject.getClassKind() == ClassKind.ENUM_ENTRY) {
            return null;
        }
        AnonymousObjectExpressionExitNode anonymousObjectExpressionExitNodeCreateAnonymousObjectExpressionExitNode = ControlFlowGraphNodeBuilderKt.createAnonymousObjectExpressionExitNode(this, anonymousObjectExpression);
        FirControlFlowGraphReference controlFlowGraphReference = anonymousObject.getControlFlowGraphReference();
        CFGNode<?> exitNode = (controlFlowGraphReference == null || (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) == null) ? null : controlFlowGraph.getExitNode();
        if (exitNode != null && (getLastNode() instanceof AnonymousObjectEnterNode)) {
            AnonymousObjectExpressionExitNode anonymousObjectExpressionExitNode = anonymousObjectExpressionExitNodeCreateAnonymousObjectExpressionExitNode;
            addEdge$default(this, exitNode, anonymousObjectExpressionExitNode, false, false, null, null, 60, null);
            addEdge$default(this, this.lastNodes.pop(), anonymousObjectExpressionExitNode, false, false, EdgeKind.DeadForward, null, 40, null);
            this.lastNodes.push(anonymousObjectExpressionExitNodeCreateAnonymousObjectExpressionExitNode);
        } else {
            addNewSimpleNode$default(this, anonymousObjectExpressionExitNodeCreateAnonymousObjectExpressionExitNode, false, 2, null);
        }
        return anonymousObjectExpressionExitNodeCreateAnonymousObjectExpressionExitNode;
    }

    public final ScriptEnterNode enterScript(FirScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(script instanceof FirDeclaration ? script : null, "SCRIPT_GRAPH", ControlFlowGraph.Kind.Function);
        this.graphs.push(controlFlowGraph);
        Pair pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createScriptEnterNode(this, script), ControlFlowGraphNodeBuilderKt.createScriptExitNode(this, script));
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        return (ScriptEnterNode) cFGNode;
    }

    public final CodeFragmentEnterNode enterCodeFragment(FirCodeFragment codeFragment) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(codeFragment instanceof FirDeclaration ? codeFragment : null, "CODE_FRAGMENT_GRAPH", ControlFlowGraph.Kind.Function);
        this.graphs.push(controlFlowGraph);
        Pair pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createCodeFragmentEnterNode(this, codeFragment), ControlFlowGraphNodeBuilderKt.createCodeFragmentExitNode(this, codeFragment));
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        return (CodeFragmentEnterNode) cFGNode;
    }

    public final Pair<EnterValueParameterNode, EnterDefaultArgumentsNode> enterValueParameter(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        if (valueParameter.getDefaultValue() == null) {
            return null;
        }
        EnterValueParameterNode enterValueParameterNodeCreateEnterValueParameterNode = ControlFlowGraphNodeBuilderKt.createEnterValueParameterNode(this, valueParameter);
        EnterValueParameterNode enterValueParameterNode = enterValueParameterNodeCreateEnterValueParameterNode;
        addNewSimpleNode$default(this, enterValueParameterNode, false, 2, null);
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(valueParameter instanceof FirDeclaration ? valueParameter : null, "default value of " + valueParameter.getName(), ControlFlowGraph.Kind.DefaultArgument);
        this.graphs.push(controlFlowGraph);
        Pair pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createEnterDefaultArgumentsNode(this, valueParameter), ControlFlowGraphNodeBuilderKt.createExitDefaultArgumentsNode(this, valueParameter));
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        EnterDefaultArgumentsNode enterDefaultArgumentsNode = (EnterDefaultArgumentsNode) cFGNode;
        addEdge$default(this, enterValueParameterNode, enterDefaultArgumentsNode, false, false, null, null, 60, null);
        return TuplesKt.to(enterValueParameterNodeCreateEnterValueParameterNode, enterDefaultArgumentsNode);
    }

    public final Triple<ExitDefaultArgumentsNode, ExitValueParameterNode, ControlFlowGraph> exitValueParameter(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        if (valueParameter.getDefaultValue() == null) {
            return null;
        }
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        CFGNode<?> exitNode = controlFlowGraphPop.getExitNode();
        if (exitNode == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.ExitDefaultArgumentsNode");
        }
        ExitDefaultArgumentsNode exitDefaultArgumentsNode = (ExitDefaultArgumentsNode) exitNode;
        popAndAddEdge$default(this, exitDefaultArgumentsNode, null, 2, null);
        if (exitDefaultArgumentsNode.getPreviousNodes().size() > 1) {
            exitDefaultArgumentsNode.updateDeadStatus();
        }
        controlFlowGraphPop.complete();
        Pair pair = TuplesKt.to(exitDefaultArgumentsNode, controlFlowGraphPop);
        ExitDefaultArgumentsNode exitDefaultArgumentsNode2 = (ExitDefaultArgumentsNode) pair.component1();
        ControlFlowGraph controlFlowGraph = (ControlFlowGraph) pair.component2();
        ExitValueParameterNode exitValueParameterNodeCreateExitValueParameterNode = ControlFlowGraphNodeBuilderKt.createExitValueParameterNode(this, valueParameter);
        ExitValueParameterNode exitValueParameterNode = exitValueParameterNodeCreateExitValueParameterNode;
        addNewSimpleNode$default(this, exitValueParameterNode, false, 2, null);
        addEdge$default(this, exitDefaultArgumentsNode2, exitValueParameterNode, false, false, null, null, 56, null);
        return new Triple<>(exitDefaultArgumentsNode2, exitValueParameterNodeCreateExitValueParameterNode, controlFlowGraph);
    }

    public final BlockEnterNode enterBlock(FirBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        BlockEnterNode blockEnterNodeCreateBlockEnterNode = ControlFlowGraphNodeBuilderKt.createBlockEnterNode(this, block);
        addNewSimpleNode$default(this, blockEnterNodeCreateBlockEnterNode, false, 2, null);
        return blockEnterNodeCreateBlockEnterNode;
    }

    public final CFGNode<?> exitBlock(FirBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        BlockExitNode blockExitNodeCreateBlockExitNode = ControlFlowGraphNodeBuilderKt.createBlockExitNode(this, block);
        addNewSimpleNode$default(this, blockExitNodeCreateBlockExitNode, false, 2, null);
        return blockExitNodeCreateBlockExitNode;
    }

    public final PropertyInitializerEnterNode enterProperty(FirProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        if (!ControlFlowGraphBuilderKt.getMemberShouldHaveGraph(property)) {
            return null;
        }
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(property instanceof FirDeclaration ? property : null, "val " + property.getName(), ControlFlowGraph.Kind.PropertyInitializer);
        this.graphs.push(controlFlowGraph);
        Pair pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createPropertyInitializerEnterNode(this, property), ControlFlowGraphNodeBuilderKt.createPropertyInitializerExitNode(this, property));
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        PropertyInitializerEnterNode propertyInitializerEnterNode = (PropertyInitializerEnterNode) cFGNode;
        addEdgeIfLocalClassMember(propertyInitializerEnterNode);
        return propertyInitializerEnterNode;
    }

    public final Pair<PropertyInitializerExitNode, ControlFlowGraph> exitProperty(FirProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        if (!ControlFlowGraphBuilderKt.getMemberShouldHaveGraph(property)) {
            return null;
        }
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        CFGNode<?> exitNode = controlFlowGraphPop.getExitNode();
        if (exitNode == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.PropertyInitializerExitNode");
        }
        PropertyInitializerExitNode propertyInitializerExitNode = (PropertyInitializerExitNode) exitNode;
        popAndAddEdge$default(this, propertyInitializerExitNode, null, 2, null);
        if (propertyInitializerExitNode.getPreviousNodes().size() > 1) {
            propertyInitializerExitNode.updateDeadStatus();
        }
        controlFlowGraphPop.complete();
        return TuplesKt.to(propertyInitializerExitNode, controlFlowGraphPop);
    }

    public final FieldInitializerEnterNode enterField(FirField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        if (!ControlFlowGraphBuilderKt.getMemberShouldHaveGraph(field)) {
            return null;
        }
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(field instanceof FirDeclaration ? field : null, "val " + field.getName(), ControlFlowGraph.Kind.FieldInitializer);
        this.graphs.push(controlFlowGraph);
        Pair pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createFieldInitializerEnterNode(this, field), ControlFlowGraphNodeBuilderKt.createFieldInitializerExitNode(this, field));
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        FieldInitializerEnterNode fieldInitializerEnterNode = (FieldInitializerEnterNode) cFGNode;
        addEdgeIfLocalClassMember(fieldInitializerEnterNode);
        return fieldInitializerEnterNode;
    }

    public final Pair<FieldInitializerExitNode, ControlFlowGraph> exitField(FirField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        if (!ControlFlowGraphBuilderKt.getMemberShouldHaveGraph(field)) {
            return null;
        }
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        CFGNode<?> exitNode = controlFlowGraphPop.getExitNode();
        if (exitNode == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.FieldInitializerExitNode");
        }
        FieldInitializerExitNode fieldInitializerExitNode = (FieldInitializerExitNode) exitNode;
        popAndAddEdge$default(this, fieldInitializerExitNode, null, 2, null);
        if (fieldInitializerExitNode.getPreviousNodes().size() > 1) {
            fieldInitializerExitNode.updateDeadStatus();
        }
        controlFlowGraphPop.complete();
        return TuplesKt.to(fieldInitializerExitNode, controlFlowGraphPop);
    }

    public final void enterDelegateExpression() {
        splitDataFlowForPostponedLambdas();
    }

    public final DelegateExpressionExitNode exitDelegateExpression(FirExpression fir) {
        Intrinsics.checkNotNullParameter(fir, "fir");
        DelegateExpressionExitNode delegateExpressionExitNodeCreateDelegateExpressionExitNode = ControlFlowGraphNodeBuilderKt.createDelegateExpressionExitNode(this, fir);
        DelegateExpressionExitNode delegateExpressionExitNode = delegateExpressionExitNodeCreateDelegateExpressionExitNode;
        unifyDataFlowFromPostponedLambdas(delegateExpressionExitNode, true);
        addNewSimpleNode$default(this, delegateExpressionExitNode, false, 2, null);
        return delegateExpressionExitNodeCreateDelegateExpressionExitNode;
    }

    public final TypeOperatorCallNode exitTypeOperatorCall(FirTypeOperatorCall typeOperatorCall) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        TypeOperatorCallNode typeOperatorCallNodeCreateTypeOperatorCallNode = ControlFlowGraphNodeBuilderKt.createTypeOperatorCallNode(this, typeOperatorCall);
        addNewSimpleNode$default(this, typeOperatorCallNodeCreateTypeOperatorCallNode, false, 2, null);
        return typeOperatorCallNodeCreateTypeOperatorCallNode;
    }

    public final ComparisonExpressionNode exitComparisonExpression(FirComparisonExpression comparisonExpression) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        ComparisonExpressionNode comparisonExpressionNodeCreateComparisonExpressionNode = ControlFlowGraphNodeBuilderKt.createComparisonExpressionNode(this, comparisonExpression);
        addNewSimpleNode$default(this, comparisonExpressionNodeCreateComparisonExpressionNode, false, 2, null);
        return comparisonExpressionNodeCreateComparisonExpressionNode;
    }

    public final EqualityOperatorCallNode exitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        EqualityOperatorCallNode equalityOperatorCallNodeCreateEqualityOperatorCallNode = ControlFlowGraphNodeBuilderKt.createEqualityOperatorCallNode(this, equalityOperatorCall);
        addNewSimpleNode$default(this, equalityOperatorCallNodeCreateEqualityOperatorCallNode, false, 2, null);
        return equalityOperatorCallNodeCreateEqualityOperatorCallNode;
    }

    public final void enterJump(FirJump<?> jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        if ((jump instanceof FirReturnExpression) && (((FirReturnExpression) jump).getTarget().getLabeledElement() instanceof FirAnonymousFunction)) {
            splitDataFlowForPostponedLambdas();
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final JumpNode exitJump(FirJump<?> jump) {
        EdgeLabel edgeLabel;
        Intrinsics.checkNotNullParameter(jump, "jump");
        JumpNode jumpNodeCreateJumpNode = ControlFlowGraphNodeBuilderKt.createJumpNode(this, jump);
        JumpNode jumpNode = jumpNodeCreateJumpNode;
        addNonSuccessfullyTerminatingNode(jumpNode);
        boolean z = jump instanceof FirReturnExpression;
        if (z && (((FirReturnExpression) jump).getTarget().getLabeledElement() instanceof FirAnonymousFunction)) {
            this.postponedLambdaExits.pop();
        }
        if (z) {
            edgeLabel = this.exitTargetsForReturn.get(((FirFunction) ((FirReturnExpression) jump).getTarget().getLabeledElement()).getSymbol());
        } else if (jump instanceof FirContinueExpression) {
            edgeLabel = this.loopConditionEnterNodes.get(((FirContinueExpression) jump).getTarget().getLabeledElement());
        } else {
            if (!(jump instanceof FirBreakExpression)) {
                throw new IllegalArgumentException("Unknown jump type: " + UtilsKt.render(jump));
            }
            edgeLabel = this.loopExitNodes.get(((FirBreakExpression) jump).getTarget().getLabeledElement());
        }
        if (edgeLabel == null) {
            return jumpNodeCreateJumpNode;
        }
        FinallyBlockEnterNode finallyBlockEnterNode = (FinallyBlockEnterNode) StackKt.topOrNull(this.finallyEnterNodes);
        if (finallyBlockEnterNode == null || finallyBlockEnterNode.getLevel() <= ((CFGNode) edgeLabel).getLevel()) {
            finallyBlockEnterNode = null;
        }
        if (finallyBlockEnterNode != null) {
            addEdge$default(this, jumpNode, finallyBlockEnterNode, false, false, null, edgeLabel, 24, null);
            this.nonDirectJumps.put(edgeLabel, jumpNodeCreateJumpNode);
        } else {
            CFGNode<?> cFGNode = (CFGNode) edgeLabel;
            if (getReturnPathIsBackwards(cFGNode)) {
                addBackEdge$default(this, jumpNode, cFGNode, false, null, 12, null);
            } else {
                addEdge$default(this, jumpNode, cFGNode, false, false, null, null, 56, null);
            }
        }
        return jumpNodeCreateJumpNode;
    }

    private final boolean getReturnPathIsBackwards(CFGNode<?> cFGNode) {
        if (cFGNode instanceof LoopConditionEnterNode) {
            if (((LoopConditionEnterNode) cFGNode).getLoop() instanceof FirDoWhileLoop) {
                List<CFGNode<?>> previousNodes = cFGNode.getPreviousNodes();
                if (!(previousNodes instanceof Collection) || !previousNodes.isEmpty()) {
                    Iterator<T> it = previousNodes.iterator();
                    while (it.hasNext()) {
                        if (((CFGNode) it.next()) instanceof LoopBlockExitNode) {
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public final WhenEnterNode enterWhenExpression(FirWhenExpression whenExpression) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        WhenEnterNode whenEnterNodeCreateWhenEnterNode = ControlFlowGraphNodeBuilderKt.createWhenEnterNode(this, whenExpression);
        addNewSimpleNode$default(this, whenEnterNodeCreateWhenEnterNode, false, 2, null);
        this.whenExitNodes.push(ControlFlowGraphNodeBuilderKt.createWhenExitNode(this, whenExpression));
        this.notCompletedFunctionCalls.push(new ArrayList());
        splitDataFlowForPostponedLambdas();
        return whenEnterNodeCreateWhenEnterNode;
    }

    public final WhenSubjectExpressionExitNode exitWhenSubjectExpression(FirWhenSubjectExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        WhenSubjectExpressionExitNode whenSubjectExpressionExitNodeCreateWhenSubjectExpressionExitNode = ControlFlowGraphNodeBuilderKt.createWhenSubjectExpressionExitNode(this, expression);
        addNewSimpleNode$default(this, whenSubjectExpressionExitNodeCreateWhenSubjectExpressionExitNode, false, 2, null);
        return whenSubjectExpressionExitNodeCreateWhenSubjectExpressionExitNode;
    }

    public final WhenBranchConditionEnterNode enterWhenBranchCondition(FirWhenBranch whenBranch) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        WhenBranchConditionEnterNode whenBranchConditionEnterNodeCreateWhenBranchConditionEnterNode = ControlFlowGraphNodeBuilderKt.createWhenBranchConditionEnterNode(this, whenBranch);
        addNewSimpleNode$default(this, whenBranchConditionEnterNodeCreateWhenBranchConditionEnterNode, false, 2, null);
        return whenBranchConditionEnterNodeCreateWhenBranchConditionEnterNode;
    }

    public final Pair<WhenBranchConditionExitNode, WhenBranchResultEnterNode> exitWhenBranchCondition(FirWhenBranch whenBranch) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        WhenBranchConditionExitNode whenBranchConditionExitNodeCreateWhenBranchConditionExitNode = ControlFlowGraphNodeBuilderKt.createWhenBranchConditionExitNode(this, whenBranch);
        addNewSimpleNode$default(this, whenBranchConditionExitNodeCreateWhenBranchConditionExitNode, false, 2, null);
        this.lastNodes.push(whenBranchConditionExitNodeCreateWhenBranchConditionExitNode);
        WhenBranchResultEnterNode whenBranchResultEnterNodeCreateWhenBranchResultEnterNode = ControlFlowGraphNodeBuilderKt.createWhenBranchResultEnterNode(this, whenBranch);
        addNewSimpleNode$default(this, whenBranchResultEnterNodeCreateWhenBranchResultEnterNode, false, 2, null);
        return TuplesKt.to(whenBranchConditionExitNodeCreateWhenBranchConditionExitNode, whenBranchResultEnterNodeCreateWhenBranchResultEnterNode);
    }

    public final WhenBranchResultExitNode exitWhenBranchResult(FirWhenBranch whenBranch) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        WhenBranchResultExitNode whenBranchResultExitNodeCreateWhenBranchResultExitNode = ControlFlowGraphNodeBuilderKt.createWhenBranchResultExitNode(this, whenBranch);
        WhenBranchResultExitNode whenBranchResultExitNode = whenBranchResultExitNodeCreateWhenBranchResultExitNode;
        popAndAddEdge$default(this, whenBranchResultExitNode, null, 2, null);
        addEdge$default(this, whenBranchResultExitNode, this.whenExitNodes.top(), false, false, null, null, 56, null);
        return whenBranchResultExitNodeCreateWhenBranchResultExitNode;
    }

    public final Pair<WhenExitNode, WhenSyntheticElseBranchNode> exitWhenExpression(FirWhenExpression whenExpression, boolean callCompleted) {
        WhenSyntheticElseBranchNode whenSyntheticElseBranchNodeCreateWhenSyntheticElseBranchNode;
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        WhenExitNode whenExitNodePop = this.whenExitNodes.pop();
        Iterator<T> it = this.notCompletedFunctionCalls.pop().iterator();
        while (it.hasNext()) {
            completeFunctionCall((FunctionCallNode) it.next());
        }
        CFGNode<?> cFGNodePop = this.lastNodes.pop();
        if (ExhaustivenessStatusKt.isProperlyExhaustive(whenExpression)) {
            whenSyntheticElseBranchNodeCreateWhenSyntheticElseBranchNode = null;
        } else {
            whenSyntheticElseBranchNodeCreateWhenSyntheticElseBranchNode = ControlFlowGraphNodeBuilderKt.createWhenSyntheticElseBranchNode(this, whenExpression);
            WhenSyntheticElseBranchNode whenSyntheticElseBranchNode = whenSyntheticElseBranchNodeCreateWhenSyntheticElseBranchNode;
            addEdge$default(this, cFGNodePop, whenSyntheticElseBranchNode, false, false, null, null, 60, null);
            addEdge$default(this, whenSyntheticElseBranchNode, whenExitNodePop, false, false, null, null, 60, null);
        }
        mergeDataFlowFromPostponedLambdas(whenExitNodePop, callCompleted);
        whenExitNodePop.updateDeadStatus();
        this.lastNodes.push(whenExitNodePop);
        return TuplesKt.to(whenExitNodePop, whenSyntheticElseBranchNodeCreateWhenSyntheticElseBranchNode);
    }

    public final Pair<LoopEnterNode, LoopConditionEnterNode> enterWhileLoop(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        LoopEnterNode loopEnterNodeCreateLoopEnterNode = ControlFlowGraphNodeBuilderKt.createLoopEnterNode(this, loop);
        addNewSimpleNode$default(this, loopEnterNodeCreateLoopEnterNode, false, 2, null);
        this.loopExitNodes.put(loop, ControlFlowGraphNodeBuilderKt.createLoopExitNode(this, loop));
        LoopConditionEnterNode loopConditionEnterNodeCreateLoopConditionEnterNode = ControlFlowGraphNodeBuilderKt.createLoopConditionEnterNode(this, loop.getCondition(), loop);
        addNewSimpleNode$default(this, loopConditionEnterNodeCreateLoopConditionEnterNode, false, 2, null);
        this.loopConditionEnterNodes.put(loop, loopConditionEnterNodeCreateLoopConditionEnterNode);
        return TuplesKt.to(loopEnterNodeCreateLoopEnterNode, loopConditionEnterNodeCreateLoopConditionEnterNode);
    }

    public final Pair<LoopConditionExitNode, LoopBlockEnterNode> exitWhileLoopCondition(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        LoopConditionExitNode loopConditionExitNodeCreateLoopConditionExitNode = ControlFlowGraphNodeBuilderKt.createLoopConditionExitNode(this, loop.getCondition());
        LoopConditionExitNode loopConditionExitNode = loopConditionExitNodeCreateLoopConditionExitNode;
        addNewSimpleNode$default(this, loopConditionExitNode, false, 2, null);
        Boolean booleanConstValue = getBooleanConstValue(loop.getCondition());
        addEdge$default(this, loopConditionExitNode, (CFGNode) MapsKt.getValue(this.loopExitNodes, loop), false, Intrinsics.areEqual((Object) booleanConstValue, (Object) true), null, null, 48, null);
        LoopBlockEnterNode loopBlockEnterNodeCreateLoopBlockEnterNode = ControlFlowGraphNodeBuilderKt.createLoopBlockEnterNode(this, loop);
        addNewSimpleNode(loopBlockEnterNodeCreateLoopBlockEnterNode, Intrinsics.areEqual((Object) booleanConstValue, (Object) false));
        return TuplesKt.to(loopConditionExitNodeCreateLoopConditionExitNode, loopBlockEnterNodeCreateLoopBlockEnterNode);
    }

    public final Triple<LoopConditionEnterNode, LoopBlockExitNode, LoopExitNode> exitWhileLoop(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        LoopBlockExitNode loopBlockExitNodeCreateLoopBlockExitNode = ControlFlowGraphNodeBuilderKt.createLoopBlockExitNode(this, loop);
        LoopBlockExitNode loopBlockExitNode = loopBlockExitNodeCreateLoopBlockExitNode;
        popAndAddEdge$default(this, loopBlockExitNode, null, 2, null);
        LoopConditionEnterNode loopConditionEnterNodeRemove = this.loopConditionEnterNodes.remove(loop);
        Intrinsics.checkNotNull(loopConditionEnterNodeRemove);
        LoopConditionEnterNode loopConditionEnterNode = loopConditionEnterNodeRemove;
        addBackEdge$default(this, loopBlockExitNode, loopConditionEnterNode, false, null, 12, null);
        LoopExitNode loopExitNodeRemove = this.loopExitNodes.remove(loop);
        Intrinsics.checkNotNull(loopExitNodeRemove);
        LoopExitNode loopExitNode = loopExitNodeRemove;
        loopExitNode.updateDeadStatus();
        this.lastNodes.push(loopExitNode);
        return new Triple<>(loopConditionEnterNode, loopBlockExitNodeCreateLoopBlockExitNode, loopExitNode);
    }

    public final Pair<LoopEnterNode, LoopBlockEnterNode> enterDoWhileLoop(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        LoopEnterNode loopEnterNodeCreateLoopEnterNode = ControlFlowGraphNodeBuilderKt.createLoopEnterNode(this, loop);
        addNewSimpleNode$default(this, loopEnterNodeCreateLoopEnterNode, false, 2, null);
        this.loopExitNodes.put(loop, ControlFlowGraphNodeBuilderKt.createLoopExitNode(this, loop));
        LoopBlockEnterNode loopBlockEnterNodeCreateLoopBlockEnterNode = ControlFlowGraphNodeBuilderKt.createLoopBlockEnterNode(this, loop);
        addNewSimpleNode$default(this, loopBlockEnterNodeCreateLoopBlockEnterNode, false, 2, null);
        this.lastNodes.push(loopBlockEnterNodeCreateLoopBlockEnterNode);
        this.loopConditionEnterNodes.put(loop, ControlFlowGraphNodeBuilderKt.createLoopConditionEnterNode(this, loop.getCondition(), loop));
        return TuplesKt.to(loopEnterNodeCreateLoopEnterNode, loopBlockEnterNodeCreateLoopBlockEnterNode);
    }

    public final Pair<LoopBlockExitNode, LoopConditionEnterNode> enterDoWhileLoopCondition(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        LoopBlockExitNode loopBlockExitNodeCreateLoopBlockExitNode = ControlFlowGraphNodeBuilderKt.createLoopBlockExitNode(this, loop);
        addNewSimpleNode$default(this, loopBlockExitNodeCreateLoopBlockExitNode, false, 2, null);
        LoopConditionEnterNode loopConditionEnterNode = (LoopConditionEnterNode) MapsKt.getValue(this.loopConditionEnterNodes, loop);
        addNewSimpleNode$default(this, loopConditionEnterNode, false, 2, null);
        loopConditionEnterNode.updateDeadStatus();
        return TuplesKt.to(loopBlockExitNodeCreateLoopBlockExitNode, loopConditionEnterNode);
    }

    public final Pair<LoopConditionExitNode, LoopExitNode> exitDoWhileLoop(FirLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        this.loopConditionEnterNodes.remove(loop);
        LoopConditionExitNode loopConditionExitNodeCreateLoopConditionExitNode = ControlFlowGraphNodeBuilderKt.createLoopConditionExitNode(this, loop.getCondition());
        Boolean booleanConstValue = getBooleanConstValue(loop.getCondition());
        LoopConditionExitNode loopConditionExitNode = loopConditionExitNodeCreateLoopConditionExitNode;
        popAndAddEdge$default(this, loopConditionExitNode, null, 2, null);
        CFGNode<?> cFGNodePop = this.lastNodes.pop();
        if (!(cFGNodePop instanceof LoopBlockEnterNode)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        addBackEdge$default(this, loopConditionExitNode, cFGNodePop, Intrinsics.areEqual((Object) booleanConstValue, (Object) false), null, 8, null);
        LoopExitNode loopExitNodeRemove = this.loopExitNodes.remove(loop);
        Intrinsics.checkNotNull(loopExitNodeRemove);
        LoopExitNode loopExitNode = loopExitNodeRemove;
        addEdge$default(this, loopConditionExitNode, loopExitNode, false, Intrinsics.areEqual((Object) booleanConstValue, (Object) true), null, null, 48, null);
        loopExitNode.updateDeadStatus();
        this.lastNodes.push(loopExitNode);
        return TuplesKt.to(loopConditionExitNodeCreateLoopConditionExitNode, loopExitNode);
    }

    public final CFGNode<FirBinaryLogicExpression> enterBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression) {
        CFGNode<FirBinaryLogicExpression> cFGNodeCreateBinaryAndEnterNode;
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        int i = WhenMappings.$EnumSwitchMapping$0[binaryLogicExpression.getKind().ordinal()];
        if (i == 1) {
            cFGNodeCreateBinaryAndEnterNode = ControlFlowGraphNodeBuilderKt.createBinaryAndEnterNode(this, binaryLogicExpression);
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            cFGNodeCreateBinaryAndEnterNode = ControlFlowGraphNodeBuilderKt.createBinaryOrEnterNode(this, binaryLogicExpression);
        }
        addNewSimpleNode$default(this, cFGNodeCreateBinaryAndEnterNode, false, 2, null);
        return cFGNodeCreateBinaryAndEnterNode;
    }

    public final Pair<CFGNode<FirBinaryLogicExpression>, CFGNode<FirBinaryLogicExpression>> exitLeftBinaryLogicExpressionArgument(FirBinaryLogicExpression binaryLogicExpression) {
        Pair pair;
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        int i = WhenMappings.$EnumSwitchMapping$0[binaryLogicExpression.getKind().ordinal()];
        if (i == 1) {
            pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createBinaryAndExitLeftOperandNode(this, binaryLogicExpression), ControlFlowGraphNodeBuilderKt.createBinaryAndEnterRightOperandNode(this, binaryLogicExpression));
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createBinaryOrExitLeftOperandNode(this, binaryLogicExpression), ControlFlowGraphNodeBuilderKt.createBinaryOrEnterRightOperandNode(this, binaryLogicExpression));
        }
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        addNewSimpleNode$default(this, cFGNode, false, 2, null);
        this.lastNodes.push(cFGNode);
        addNewSimpleNode(cFGNode2, Intrinsics.areEqual(getBooleanConstValue(binaryLogicExpression.getLeftOperand()), Boolean.valueOf(binaryLogicExpression.getKind() != LogicOperationKind.AND)));
        return TuplesKt.to(cFGNode, cFGNode2);
    }

    public final AbstractBinaryExitNode<FirBinaryLogicExpression> exitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression) {
        AbstractBinaryExitNode<FirBinaryLogicExpression> abstractBinaryExitNodeCreateBinaryAndExitNode;
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        int i = WhenMappings.$EnumSwitchMapping$0[binaryLogicExpression.getKind().ordinal()];
        if (i == 1) {
            abstractBinaryExitNodeCreateBinaryAndExitNode = ControlFlowGraphNodeBuilderKt.createBinaryAndExitNode(this, binaryLogicExpression);
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            abstractBinaryExitNodeCreateBinaryAndExitNode = ControlFlowGraphNodeBuilderKt.createBinaryOrExitNode(this, binaryLogicExpression);
        }
        CFGNode<?> cFGNodePop = this.lastNodes.pop();
        CFGNode<?> cFGNodePop2 = this.lastNodes.pop();
        boolean zAreEqual = Intrinsics.areEqual(getBooleanConstValue(binaryLogicExpression.getLeftOperand()), Boolean.valueOf(binaryLogicExpression.getKind() == LogicOperationKind.AND));
        AbstractBinaryExitNode<FirBinaryLogicExpression> abstractBinaryExitNode = abstractBinaryExitNodeCreateBinaryAndExitNode;
        addEdge$default(this, cFGNodePop2, abstractBinaryExitNode, !zAreEqual, zAreEqual, null, null, 48, null);
        addEdge$default(this, cFGNodePop, abstractBinaryExitNode, zAreEqual, false, null, null, 56, null);
        this.lastNodes.push(abstractBinaryExitNodeCreateBinaryAndExitNode);
        return abstractBinaryExitNodeCreateBinaryAndExitNode;
    }

    private final Boolean getBooleanConstValue(FirExpression firExpression) {
        FirConstExpression firConstExpression = firExpression instanceof FirConstExpression ? (FirConstExpression) firExpression : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }

    public final Pair<TryExpressionEnterNode, TryMainBlockEnterNode> enterTryExpression(FirTryExpression tryExpression) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        TryExpressionEnterNode tryExpressionEnterNodeCreateTryExpressionEnterNode = ControlFlowGraphNodeBuilderKt.createTryExpressionEnterNode(this, tryExpression);
        TryExpressionEnterNode tryExpressionEnterNode = tryExpressionEnterNodeCreateTryExpressionEnterNode;
        addNewSimpleNode$default(this, tryExpressionEnterNode, false, 2, null);
        this.tryExitNodes.push(ControlFlowGraphNodeBuilderKt.createTryExpressionExitNode(this, tryExpression));
        TryMainBlockEnterNode tryMainBlockEnterNodeCreateTryMainBlockEnterNode = ControlFlowGraphNodeBuilderKt.createTryMainBlockEnterNode(this, tryExpression);
        addNewSimpleNode$default(this, tryMainBlockEnterNodeCreateTryMainBlockEnterNode, false, 2, null);
        Stack<List<CatchClauseEnterNode>> stack = this.catchNodes;
        List<FirCatch> catches = tryExpression.getCatches();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(catches, 10));
        Iterator<T> it = catches.iterator();
        while (it.hasNext()) {
            arrayList.add(ControlFlowGraphNodeBuilderKt.createCatchClauseEnterNode(this, (FirCatch) it.next()));
        }
        stack.push(arrayList);
        if (tryExpression.getFinallyBlock() != null) {
            this.finallyEnterNodes.push(ControlFlowGraphNodeBuilderKt.createFinallyBlockEnterNode(this, tryExpression));
        }
        Iterator<CatchClauseEnterNode> it2 = this.catchNodes.top().iterator();
        while (it2.hasNext()) {
            addEdge$default(this, tryExpressionEnterNode, it2.next(), false, false, null, null, 60, null);
        }
        if (tryExpression.getFinallyBlock() != null) {
            addEdge$default(this, tryExpressionEnterNode, this.finallyEnterNodes.top(), false, false, null, UncaughtExceptionPath.INSTANCE, 28, null);
        }
        this.notCompletedFunctionCalls.push(new ArrayList());
        splitDataFlowForPostponedLambdas();
        return TuplesKt.to(tryExpressionEnterNodeCreateTryExpressionEnterNode, tryMainBlockEnterNodeCreateTryMainBlockEnterNode);
    }

    public final TryMainBlockExitNode exitTryMainBlock() {
        CFGNode pVar = (TryExpressionExitNode) this.tryExitNodes.top();
        TryMainBlockExitNode tryMainBlockExitNodeCreateTryMainBlockExitNode = ControlFlowGraphNodeBuilderKt.createTryMainBlockExitNode(this, pVar.getFir());
        TryMainBlockExitNode tryMainBlockExitNode = tryMainBlockExitNodeCreateTryMainBlockExitNode;
        popAndAddEdge$default(this, tryMainBlockExitNode, null, 2, null);
        if (tryMainBlockExitNodeCreateTryMainBlockExitNode.getFir().getFinallyBlock() != null) {
            pVar = this.finallyEnterNodes.top();
        }
        addEdge$default(this, tryMainBlockExitNode, pVar, false, false, null, null, 56, null);
        for (CatchClauseEnterNode catchClauseEnterNode : CollectionsKt.asReversed(this.catchNodes.pop())) {
            this.catchBlocksInProgress.push(catchClauseEnterNode);
            addEdge$default(this, tryMainBlockExitNode, catchClauseEnterNode, false, false, null, null, 56, null);
        }
        return tryMainBlockExitNodeCreateTryMainBlockExitNode;
    }

    public final CatchClauseEnterNode enterCatchClause(FirCatch firCatch) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        CatchClauseEnterNode catchClauseEnterNodePop = this.catchBlocksInProgress.pop();
        Intrinsics.areEqual(catchClauseEnterNodePop.getFir(), firCatch);
        if (this.tryExitNodes.top().getFir().getFinallyBlock() != null) {
            addEdge$default(this, catchClauseEnterNodePop, this.finallyEnterNodes.top(), false, false, null, UncaughtExceptionPath.INSTANCE, 24, null);
        }
        this.lastNodes.push(catchClauseEnterNodePop);
        return catchClauseEnterNodePop;
    }

    public final CatchClauseExitNode exitCatchClause(FirCatch firCatch) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        CFGNode pVar = (TryExpressionExitNode) this.tryExitNodes.top();
        CatchClauseExitNode catchClauseExitNodeCreateCatchClauseExitNode = ControlFlowGraphNodeBuilderKt.createCatchClauseExitNode(this, firCatch);
        CatchClauseExitNode catchClauseExitNode = catchClauseExitNodeCreateCatchClauseExitNode;
        popAndAddEdge$default(this, catchClauseExitNode, null, 2, null);
        if (pVar.getFir().getFinallyBlock() != null) {
            pVar = this.finallyEnterNodes.top();
        }
        addEdge$default(this, catchClauseExitNode, pVar, false, false, null, null, 56, null);
        return catchClauseExitNodeCreateCatchClauseExitNode;
    }

    public final FinallyBlockEnterNode enterFinallyBlock() {
        FinallyBlockEnterNode finallyBlockEnterNodePop = this.finallyEnterNodes.pop();
        this.lastNodes.push(finallyBlockEnterNodePop);
        this.finallyBlocksInProgress.push(finallyBlockEnterNodePop);
        return finallyBlockEnterNodePop;
    }

    public final FinallyBlockExitNode exitFinallyBlock() {
        FinallyBlockEnterNode pVar = this.finallyBlocksInProgress.top();
        TryExpressionExitNode pVar2 = this.tryExitNodes.top();
        FinallyBlockExitNode finallyBlockExitNodeCreateFinallyBlockExitNode = ControlFlowGraphNodeBuilderKt.createFinallyBlockExitNode(this, pVar.getFir());
        FinallyBlockExitNode finallyBlockExitNode = finallyBlockExitNodeCreateFinallyBlockExitNode;
        FinallyBlockEnterNode finallyBlockEnterNode = null;
        popAndAddEdge$default(this, finallyBlockExitNode, null, 2, null);
        addEdge$default(this, finallyBlockExitNode, pVar2, false, getAllNormalInputsAreDead(pVar), null, null, 52, null);
        int iLevelOfNextExceptionCatchingGraph = levelOfNextExceptionCatchingGraph();
        FinallyBlockEnterNode finallyBlockEnterNode2 = (FinallyBlockEnterNode) StackKt.topOrNull(this.finallyEnterNodes);
        if (finallyBlockEnterNode2 != null && finallyBlockEnterNode2.getLevel() > iLevelOfNextExceptionCatchingGraph) {
            finallyBlockEnterNode = finallyBlockEnterNode2;
        }
        if (finallyBlockEnterNode != null) {
            addEdge$default(this, finallyBlockExitNode, finallyBlockEnterNode, false, false, null, UncaughtExceptionPath.INSTANCE, 24, null);
        }
        List<CFGNode<?>> previousNodes = pVar.getPreviousNodes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(previousNodes, 10));
        Iterator<T> it = previousNodes.iterator();
        while (it.hasNext()) {
            arrayList.add(((CFGNode) it.next()).edgeTo(pVar).getLabel());
        }
        Set set = CollectionsKt.toSet(arrayList);
        if (finallyBlockEnterNode != null) {
            iLevelOfNextExceptionCatchingGraph = finallyBlockEnterNode.getLevel();
        }
        Collection<FunctionExitNode> collectionValues = this.exitTargetsForReturn.values();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : collectionValues) {
            if (set.contains((FunctionExitNode) obj)) {
                arrayList2.add(obj);
            }
        }
        addReturnEdges(finallyBlockExitNode, arrayList2, iLevelOfNextExceptionCatchingGraph);
        Collection<LoopConditionEnterNode> collectionValues2 = this.loopConditionEnterNodes.values();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : collectionValues2) {
            if (set.contains((LoopConditionEnterNode) obj2)) {
                arrayList3.add(obj2);
            }
        }
        addReturnEdges(finallyBlockExitNode, arrayList3, iLevelOfNextExceptionCatchingGraph);
        Collection<LoopExitNode> collectionValues3 = this.loopExitNodes.values();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : collectionValues3) {
            if (set.contains((LoopExitNode) obj3)) {
                arrayList4.add(obj3);
            }
        }
        addReturnEdges(finallyBlockExitNode, arrayList4, iLevelOfNextExceptionCatchingGraph);
        return finallyBlockExitNodeCreateFinallyBlockExitNode;
    }

    private final boolean getAllNormalInputsAreDead(FinallyBlockEnterNode finallyBlockEnterNode) {
        List<CFGNode<?>> previousNodes = finallyBlockEnterNode.getPreviousNodes();
        if ((previousNodes instanceof Collection) && previousNodes.isEmpty()) {
            return true;
        }
        Iterator<T> it = previousNodes.iterator();
        while (it.hasNext()) {
            Edge edgeEdgeFrom = finallyBlockEnterNode.edgeFrom((CFGNode) it.next());
            if (!edgeEdgeFrom.getKind().getIsDead() && Intrinsics.areEqual(edgeEdgeFrom.getLabel(), NormalPath.INSTANCE)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final <T extends CFGNode<?> & EdgeLabel> void addReturnEdges(CFGNode<?> cFGNode, Iterable<? extends T> iterable, int i) {
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            CFGNode cFGNode2 = (CFGNode) it.next();
            if (cFGNode2.getLevel() >= i && this.nonDirectJumps.contains(cFGNode2)) {
                if (getReturnPathIsBackwards(cFGNode2)) {
                    addBackEdge$default(this, cFGNode, cFGNode2, false, (EdgeLabel) cFGNode2, 4, null);
                } else {
                    addEdge$default(this, cFGNode, cFGNode2, false, false, null, (EdgeLabel) cFGNode2, 24, null);
                }
            }
        }
    }

    public final TryExpressionExitNode exitTryExpression(boolean callCompleted) {
        boolean z;
        Iterator<T> it = this.notCompletedFunctionCalls.pop().iterator();
        loop0: while (true) {
            z = false;
            while (true) {
                if (!it.hasNext()) {
                    break loop0;
                }
                if (completeFunctionCall((FunctionCallNode) it.next()) || z) {
                    z = true;
                }
            }
        }
        TryExpressionExitNode tryExpressionExitNodePop = this.tryExitNodes.pop();
        if (tryExpressionExitNodePop.getFir().getFinallyBlock() != null) {
            FinallyBlockEnterNode finallyBlockEnterNodePop = this.finallyBlocksInProgress.pop();
            if (z && getAllNormalInputsAreDead(finallyBlockEnterNodePop)) {
                CFGNode cFGNode = (CFGNode) CollectionsKt.single((List) tryExpressionExitNodePop.getPreviousNodes());
                boolean z2 = cFGNode instanceof FinallyBlockExitNode;
                TryExpressionExitNode tryExpressionExitNode = tryExpressionExitNodePop;
                CFGNode.INSTANCE.removeAllIncomingEdges(tryExpressionExitNode);
                addEdge$default(this, cFGNode, tryExpressionExitNode, false, true, null, null, 52, null);
            }
        }
        mergeDataFlowFromPostponedLambdas(tryExpressionExitNodePop, callCompleted);
        tryExpressionExitNodePop.updateDeadStatus();
        this.lastNodes.push(tryExpressionExitNodePop);
        return tryExpressionExitNodePop;
    }

    private final void addExceptionEdgesFrom(CFGNode<?> node) {
        if (node.getCanThrow()) {
            List list = (List) StackKt.topOrNull(this.catchNodes);
            List list2 = list;
            if (list2 != null && !list2.isEmpty() && ((CatchClauseEnterNode) CollectionsKt.first(list)).getLevel() > levelOfNextExceptionCatchingGraph()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    addEdge$default(this, node, (CatchClauseEnterNode) it.next(), false, false, null, null, 56, null);
                }
            }
            FinallyBlockEnterNode finallyBlockEnterNode = (FinallyBlockEnterNode) StackKt.topOrNull(this.finallyEnterNodes);
            if (finallyBlockEnterNode == null || finallyBlockEnterNode.getLevel() <= levelOfNextExceptionCatchingGraph()) {
                return;
            }
            addEdge$default(this, node, finallyBlockEnterNode, false, false, null, UncaughtExceptionPath.INSTANCE, 24, null);
        }
    }

    private final int levelOfNextExceptionCatchingGraph() {
        for (ControlFlowGraph controlFlowGraph : this.graphs.all()) {
            if (controlFlowGraph.getKind() != ControlFlowGraph.Kind.AnonymousFunctionCalledInPlace) {
                return controlFlowGraph.getExitNode().getLevel();
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private final boolean completeFunctionCall(FunctionCallNode node) {
        if (!FirTypeUtilsKt.isNothing(node.getFir().getTypeRef())) {
            return false;
        }
        StubNode stubNode = new StubNode(node.getOwner(), node.getLevel());
        List<CFGNode<?>> followingNodes = node.getFollowingNodes();
        ArrayList<Pair> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(followingNodes, 10));
        for (CFGNode<?> cFGNode : followingNodes) {
            arrayList.add(TuplesKt.to(cFGNode, node.edgeTo(cFGNode)));
        }
        FunctionCallNode functionCallNode = node;
        CFGNode.INSTANCE.removeAllOutgoingEdges(functionCallNode);
        StubNode stubNode2 = stubNode;
        CFGNode.Companion.addEdge$default(CFGNode.INSTANCE, functionCallNode, stubNode2, EdgeKind.DeadForward, false, null, 16, null);
        for (Pair pair : arrayList) {
            CFGNode<?> cFGNode2 = (CFGNode) pair.component1();
            Edge edge = (Edge) pair.component2();
            CFGNode.INSTANCE.addEdge(stubNode2, cFGNode2, edge.getKind().getIsBack() ? EdgeKind.DeadBackward : EdgeKind.DeadForward, false, edge.getLabel());
            cFGNode2.updateDeadStatus();
            propagateDeadnessForward(cFGNode2);
        }
        return true;
    }

    public final QualifiedAccessNode exitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        boolean zIsNothing = FirTypeUtilsKt.isNothing(qualifiedAccessExpression.getTypeRef());
        QualifiedAccessNode qualifiedAccessNodeCreateQualifiedAccessNode = ControlFlowGraphNodeBuilderKt.createQualifiedAccessNode(this, qualifiedAccessExpression);
        if (zIsNothing) {
            addNonSuccessfullyTerminatingNode(qualifiedAccessNodeCreateQualifiedAccessNode);
        } else {
            addNewSimpleNode$default(this, qualifiedAccessNodeCreateQualifiedAccessNode, false, 2, null);
        }
        return qualifiedAccessNodeCreateQualifiedAccessNode;
    }

    public final SmartCastExpressionExitNode exitSmartCastExpression(FirSmartCastExpression smartCastExpression) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        boolean zIsNothing = FirTypeUtilsKt.isNothing(smartCastExpression.getTypeRef());
        SmartCastExpressionExitNode smartCastExpressionExitNodeCreateSmartCastExitNode = ControlFlowGraphNodeBuilderKt.createSmartCastExitNode(this, smartCastExpression);
        if (zIsNothing) {
            addNonSuccessfullyTerminatingNode(smartCastExpressionExitNodeCreateSmartCastExitNode);
        } else {
            addNewSimpleNode$default(this, smartCastExpressionExitNodeCreateSmartCastExitNode, false, 2, null);
        }
        return smartCastExpressionExitNodeCreateSmartCastExitNode;
    }

    public final ResolvedQualifierNode exitResolvedQualifierNode(FirResolvedQualifier resolvedQualifier) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        ResolvedQualifierNode resolvedQualifierNodeCreateResolvedQualifierNode = ControlFlowGraphNodeBuilderKt.createResolvedQualifierNode(this, resolvedQualifier);
        addNewSimpleNode$default(this, resolvedQualifierNodeCreateResolvedQualifierNode, false, 2, null);
        return resolvedQualifierNodeCreateResolvedQualifierNode;
    }

    public final void enterCall() {
        splitDataFlowForPostponedLambdas();
    }

    public final void enterCallArguments(FirStatement fir, List<? extends FirAnonymousFunction> anonymousFunctions) {
        Intrinsics.checkNotNullParameter(fir, "fir");
        Intrinsics.checkNotNullParameter(anonymousFunctions, "anonymousFunctions");
        if (anonymousFunctions.isEmpty()) {
            this.argumentListSplitNodes.push(null);
            return;
        }
        SplitPostponedLambdasNode splitPostponedLambdasNodeCreateSplitPostponedLambdasNode = ControlFlowGraphNodeBuilderKt.createSplitPostponedLambdasNode(this, fir, anonymousFunctions);
        Map<FirFunctionSymbol<?>, Pair<CFGNode<?>, PostponedLambdaExitNode>> map = this.postponedAnonymousFunctionNodes;
        Iterator<T> it = anonymousFunctions.iterator();
        while (it.hasNext()) {
            Pair pair = TuplesKt.to(((FirAnonymousFunction) it.next()).getSymbol(), TuplesKt.to(splitPostponedLambdasNodeCreateSplitPostponedLambdasNode, null));
            map.put((FirFunctionSymbol<?>) pair.getFirst(), (Pair<CFGNode<?>, PostponedLambdaExitNode>) pair.getSecond());
        }
        this.argumentListSplitNodes.push(splitPostponedLambdasNodeCreateSplitPostponedLambdasNode);
    }

    public final SplitPostponedLambdasNode exitCallArguments() {
        SplitPostponedLambdasNode splitPostponedLambdasNodePop = this.argumentListSplitNodes.pop();
        if (splitPostponedLambdasNodePop == null) {
            return null;
        }
        addNewSimpleNode$default(this, splitPostponedLambdasNodePop, false, 2, null);
        return splitPostponedLambdasNodePop;
    }

    public final FunctionCallNode exitFunctionCall(FirFunctionCall functionCall, boolean callCompleted) {
        List list;
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        boolean zIsNothing = FirTypeUtilsKt.isNothing(functionCall.getTypeRef());
        FunctionCallNode functionCallNodeCreateFunctionCallNode = ControlFlowGraphNodeBuilderKt.createFunctionCallNode(this, functionCall);
        FunctionCallNode functionCallNode = functionCallNodeCreateFunctionCallNode;
        unifyDataFlowFromPostponedLambdas(functionCallNode, callCompleted);
        if (zIsNothing) {
            addNonSuccessfullyTerminatingNode(functionCallNode);
        } else {
            addNewSimpleNode$default(this, functionCallNode, false, 2, null);
        }
        if (!zIsNothing && !callCompleted && (list = (List) StackKt.topOrNull(this.notCompletedFunctionCalls)) != null) {
            list.add(functionCallNodeCreateFunctionCallNode);
        }
        return functionCallNodeCreateFunctionCallNode;
    }

    public final DelegatedConstructorCallNode exitDelegatedConstructorCall(FirDelegatedConstructorCall call, boolean callCompleted) {
        Intrinsics.checkNotNullParameter(call, "call");
        DelegatedConstructorCallNode delegatedConstructorCallNodeCreateDelegatedConstructorCallNode = ControlFlowGraphNodeBuilderKt.createDelegatedConstructorCallNode(this, call);
        DelegatedConstructorCallNode delegatedConstructorCallNode = delegatedConstructorCallNodeCreateDelegatedConstructorCallNode;
        unifyDataFlowFromPostponedLambdas(delegatedConstructorCallNode, callCompleted);
        addNewSimpleNode$default(this, delegatedConstructorCallNode, false, 2, null);
        return delegatedConstructorCallNodeCreateDelegatedConstructorCallNode;
    }

    public final StringConcatenationCallNode exitStringConcatenationCall(FirStringConcatenationCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        StringConcatenationCallNode stringConcatenationCallNodeCreateStringConcatenationCallNode = ControlFlowGraphNodeBuilderKt.createStringConcatenationCallNode(this, call);
        StringConcatenationCallNode stringConcatenationCallNode = stringConcatenationCallNodeCreateStringConcatenationCallNode;
        unifyDataFlowFromPostponedLambdas(stringConcatenationCallNode, true);
        addNewSimpleNode$default(this, stringConcatenationCallNode, false, 2, null);
        return stringConcatenationCallNodeCreateStringConcatenationCallNode;
    }

    public final ConstExpressionNode exitConstExpression(FirConstExpression<?> constExpression) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        ConstExpressionNode constExpressionNodeCreateConstExpressionNode = ControlFlowGraphNodeBuilderKt.createConstExpressionNode(this, constExpression);
        addNewSimpleNode$default(this, constExpressionNodeCreateConstExpressionNode, false, 2, null);
        return constExpressionNodeCreateConstExpressionNode;
    }

    public final VariableDeclarationNode exitVariableDeclaration(FirProperty variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        VariableDeclarationNode variableDeclarationNodeCreateVariableDeclarationNode = ControlFlowGraphNodeBuilderKt.createVariableDeclarationNode(this, variable);
        addNewSimpleNode$default(this, variableDeclarationNodeCreateVariableDeclarationNode, false, 2, null);
        return variableDeclarationNodeCreateVariableDeclarationNode;
    }

    public final VariableAssignmentNode exitVariableAssignment(FirVariableAssignment assignment) {
        Intrinsics.checkNotNullParameter(assignment, "assignment");
        VariableAssignmentNode variableAssignmentNodeCreateVariableAssignmentNode = ControlFlowGraphNodeBuilderKt.createVariableAssignmentNode(this, assignment);
        addNewSimpleNode$default(this, variableAssignmentNodeCreateVariableAssignmentNode, false, 2, null);
        return variableAssignmentNodeCreateVariableAssignmentNode;
    }

    public final ThrowExceptionNode exitThrowExceptionNode(FirThrowExpression throwExpression) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        ThrowExceptionNode throwExceptionNodeCreateThrowExceptionNode = ControlFlowGraphNodeBuilderKt.createThrowExceptionNode(this, throwExpression);
        addNonSuccessfullyTerminatingNode(throwExceptionNodeCreateThrowExceptionNode);
        return throwExceptionNodeCreateThrowExceptionNode;
    }

    public final CheckNotNullCallNode exitCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, boolean callCompleted) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        CheckNotNullCallNode checkNotNullCallNodeCreateCheckNotNullCallNode = ControlFlowGraphNodeBuilderKt.createCheckNotNullCallNode(this, checkNotNullCall);
        CheckNotNullCallNode checkNotNullCallNode = checkNotNullCallNodeCreateCheckNotNullCallNode;
        unifyDataFlowFromPostponedLambdas(checkNotNullCallNode, callCompleted);
        if (FirTypeUtilsKt.isNothing(checkNotNullCall.getTypeRef())) {
            addNonSuccessfullyTerminatingNode(checkNotNullCallNode);
        } else {
            addNewSimpleNode$default(this, checkNotNullCallNode, false, 2, null);
        }
        return checkNotNullCallNodeCreateCheckNotNullCallNode;
    }

    public final FakeExpressionEnterNode enterFakeExpression() {
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(null, "<compile-time expression graph>", ControlFlowGraph.Kind.FakeCall);
        this.graphs.push(controlFlowGraph);
        Pair pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createFakeExpressionEnterNode(this), ControlFlowGraphNodeBuilderKt.createFakeExpressionEnterNode(this));
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        return (FakeExpressionEnterNode) cFGNode;
    }

    public final void exitFakeExpression() {
        this.lastNodes.pop();
        this.graphs.pop().getKind();
        ControlFlowGraph.Kind kind = ControlFlowGraph.Kind.FakeCall;
    }

    public final CallableReferenceNode exitCallableReference(FirCallableReferenceAccess callableReferenceAccess) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        CallableReferenceNode callableReferenceNodeCreateCallableReferenceNode = ControlFlowGraphNodeBuilderKt.createCallableReferenceNode(this, callableReferenceAccess);
        addNewSimpleNode$default(this, callableReferenceNodeCreateCallableReferenceNode, false, 2, null);
        return callableReferenceNodeCreateCallableReferenceNode;
    }

    public final GetClassCallNode exitGetClassCall(FirGetClassCall getClassCall) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        GetClassCallNode getClassCallNodeCreateGetClassCallNode = ControlFlowGraphNodeBuilderKt.createGetClassCallNode(this, getClassCall);
        addNewSimpleNode$default(this, getClassCallNodeCreateGetClassCallNode, false, 2, null);
        return getClassCallNodeCreateGetClassCallNode;
    }

    public final InitBlockEnterNode enterInitBlock(FirAnonymousInitializer initBlock) {
        Intrinsics.checkNotNullParameter(initBlock, "initBlock");
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(initBlock instanceof FirDeclaration ? initBlock : null, "init block", ControlFlowGraph.Kind.ClassInitializer);
        this.graphs.push(controlFlowGraph);
        Pair pair = TuplesKt.to(ControlFlowGraphNodeBuilderKt.createInitBlockEnterNode(this, initBlock), ControlFlowGraphNodeBuilderKt.createInitBlockExitNode(this, initBlock));
        CFGNode<?> cFGNode = (CFGNode) pair.component1();
        CFGNode<?> cFGNode2 = (CFGNode) pair.component2();
        controlFlowGraph.setEnterNode(cFGNode);
        controlFlowGraph.setExitNode(cFGNode2);
        this.lastNodes.push(cFGNode);
        InitBlockEnterNode initBlockEnterNode = (InitBlockEnterNode) cFGNode;
        addEdgeIfLocalClassMember(initBlockEnterNode);
        return initBlockEnterNode;
    }

    public final EnterSafeCallNode enterSafeCall(FirSafeCallExpression safeCall) {
        FirElvisExpression fir;
        Intrinsics.checkNotNullParameter(safeCall, "safeCall");
        EnterSafeCallNode enterSafeCallNodeCreateEnterSafeCallNode = ControlFlowGraphNodeBuilderKt.createEnterSafeCallNode(this, safeCall);
        ExitSafeCallNode exitSafeCallNodeCreateExitSafeCallNode = ControlFlowGraphNodeBuilderKt.createExitSafeCallNode(this, safeCall);
        this.exitSafeCallNodes.push(exitSafeCallNodeCreateExitSafeCallNode);
        CFGNode<?> cFGNodePop = this.lastNodes.pop();
        if (cFGNodePop instanceof ExitSafeCallNode) {
            addEdge$default(this, CFGNodeKt.getLastPreviousNode(cFGNodePop), enterSafeCallNodeCreateEnterSafeCallNode, false, false, null, null, 60, null);
        } else {
            addEdge$default(this, cFGNodePop, enterSafeCallNodeCreateEnterSafeCallNode, false, false, null, null, 60, null);
        }
        ElvisRhsEnterNode elvisRhsEnterNode = (ElvisRhsEnterNode) StackKt.topOrNull(this.elvisRhsEnterNodes);
        if (((elvisRhsEnterNode == null || (fir = elvisRhsEnterNode.getFir()) == null) ? null : fir.getLhs()) == safeCall) {
            addEdge$default(this, cFGNodePop, elvisRhsEnterNode, false, false, null, null, 60, null);
        } else {
            addEdge$default(this, cFGNodePop, exitSafeCallNodeCreateExitSafeCallNode, false, false, null, null, 60, null);
        }
        this.lastNodes.push(enterSafeCallNodeCreateEnterSafeCallNode);
        splitDataFlowForPostponedLambdas();
        return enterSafeCallNodeCreateEnterSafeCallNode;
    }

    public final ExitSafeCallNode exitSafeCall() {
        ExitSafeCallNode exitSafeCallNodePop = this.exitSafeCallNodes.pop();
        ExitSafeCallNode exitSafeCallNode = exitSafeCallNodePop;
        addNewSimpleNode$default(this, exitSafeCallNode, false, 2, null);
        mergeDataFlowFromPostponedLambdas(exitSafeCallNode, false);
        exitSafeCallNodePop.updateDeadStatus();
        return exitSafeCallNodePop;
    }

    public final void enterElvis(FirElvisExpression elvisExpression) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        this.elvisRhsEnterNodes.push(ControlFlowGraphNodeBuilderKt.createElvisRhsEnterNode(this, elvisExpression));
        splitDataFlowForPostponedLambdas();
    }

    public final Triple<ElvisLhsExitNode, ElvisLhsIsNotNullNode, ElvisRhsEnterNode> exitElvisLhs(FirElvisExpression elvisExpression) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        ElvisExitNode elvisExitNodeCreateElvisExitNode = ControlFlowGraphNodeBuilderKt.createElvisExitNode(this, elvisExpression);
        this.exitElvisExpressionNodes.push(elvisExitNodeCreateElvisExitNode);
        ElvisLhsExitNode elvisLhsExitNodeCreateElvisLhsExitNode = ControlFlowGraphNodeBuilderKt.createElvisLhsExitNode(this, elvisExpression);
        ElvisLhsExitNode elvisLhsExitNode = elvisLhsExitNodeCreateElvisLhsExitNode;
        popAndAddEdge$default(this, elvisLhsExitNode, null, 2, null);
        ElvisLhsIsNotNullNode elvisLhsIsNotNullNodeCreateElvisLhsIsNotNullNode = ControlFlowGraphNodeBuilderKt.createElvisLhsIsNotNullNode(this, elvisExpression);
        FirTypeRef typeRef = elvisExpression.getLhs().getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
        ElvisLhsIsNotNullNode elvisLhsIsNotNullNode = elvisLhsIsNotNullNodeCreateElvisLhsIsNotNullNode;
        addEdge$default(this, elvisLhsExitNode, elvisLhsIsNotNullNode, false, coneKotlinType != null && ConeBuiltinTypeUtilsKt.isNullableNothing(coneKotlinType), null, null, 52, null);
        addEdge$default(this, elvisLhsIsNotNullNode, elvisExitNodeCreateElvisExitNode, false, false, null, null, 56, null);
        ElvisRhsEnterNode elvisRhsEnterNodePop = this.elvisRhsEnterNodes.pop();
        addEdge$default(this, elvisLhsExitNode, elvisRhsEnterNodePop, elvisRhsEnterNodePop.getPreviousNodes().isEmpty(), false, null, null, 56, null);
        this.lastNodes.push(elvisRhsEnterNodePop);
        return new Triple<>(elvisLhsExitNodeCreateElvisLhsExitNode, elvisLhsIsNotNullNodeCreateElvisLhsIsNotNullNode, elvisRhsEnterNodePop);
    }

    public final ElvisExitNode exitElvis(boolean lhsIsNotNull, boolean callCompleted) {
        ElvisExitNode elvisExitNodePop = this.exitElvisExpressionNodes.pop();
        ElvisExitNode elvisExitNode = elvisExitNodePop;
        addNewSimpleNode(elvisExitNode, lhsIsNotNull);
        mergeDataFlowFromPostponedLambdas(elvisExitNode, callCompleted);
        elvisExitNodePop.updateDeadStatus();
        return elvisExitNodePop;
    }

    public final void reset() {
        this.enterToLocalClassesMembers.clear();
        this.postponedLambdaExits.reset();
        this.lastNodes.reset();
    }

    static /* synthetic */ void addNewSimpleNode$default(ControlFlowGraphBuilder controlFlowGraphBuilder, CFGNode cFGNode, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        controlFlowGraphBuilder.addNewSimpleNode(cFGNode, z);
    }

    private final void addNewSimpleNode(CFGNode<?> node, boolean isDead) {
        addEdge$default(this, this.lastNodes.pop(), node, false, false, isDead ? EdgeKind.DeadForward : EdgeKind.Forward, null, 44, null);
        this.lastNodes.push(node);
        addExceptionEdgesFrom(node);
    }

    private final void addNonSuccessfullyTerminatingNode(CFGNode<?> node) {
        popAndAddEdge$default(this, node, null, 2, null);
        StubNode stubNodeCreateStubNode = ControlFlowGraphNodeBuilderKt.createStubNode(this);
        addEdge$default(this, node, stubNodeCreateStubNode, false, false, null, null, 60, null);
        this.lastNodes.push(stubNodeCreateStubNode);
        addExceptionEdgesFrom(node);
    }

    static /* synthetic */ void popAndAddEdge$default(ControlFlowGraphBuilder controlFlowGraphBuilder, CFGNode cFGNode, EdgeKind edgeKind, int i, Object obj) {
        if ((i & 2) != 0) {
            edgeKind = EdgeKind.Forward;
        }
        controlFlowGraphBuilder.popAndAddEdge(cFGNode, edgeKind);
    }

    private final void popAndAddEdge(CFGNode<?> to, EdgeKind preferredKind) {
        addEdge$default(this, this.lastNodes.pop(), to, false, false, preferredKind, null, 44, null);
    }

    static /* synthetic */ void addEdge$default(ControlFlowGraphBuilder controlFlowGraphBuilder, CFGNode cFGNode, CFGNode cFGNode2, boolean z, boolean z2, EdgeKind edgeKind, EdgeLabel edgeLabel, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        boolean z3 = z;
        if ((i & 8) != 0) {
            z2 = false;
        }
        boolean z4 = z2;
        if ((i & 16) != 0) {
            edgeKind = EdgeKind.Forward;
        }
        EdgeKind edgeKind2 = edgeKind;
        if ((i & 32) != 0) {
            edgeLabel = NormalPath.INSTANCE;
        }
        controlFlowGraphBuilder.addEdge(cFGNode, cFGNode2, z3, z4, edgeKind2, edgeLabel);
    }

    private final void addEdge(CFGNode<?> from, CFGNode<?> to, boolean propagateDeadness, boolean isDead, EdgeKind preferredKind, EdgeLabel label) {
        if (isDead || from.getIsDead() || to.getIsDead()) {
            preferredKind = preferredKind.getIsBack() ? EdgeKind.DeadBackward : EdgeKind.DeadForward;
        }
        CFGNode.INSTANCE.addEdge(from, to, preferredKind, propagateDeadness, label);
    }

    static /* synthetic */ void addEdgeToSubGraph$default(ControlFlowGraphBuilder controlFlowGraphBuilder, CFGNode cFGNode, CFGNode cFGNode2, EdgeLabel edgeLabel, int i, Object obj) {
        if ((i & 4) != 0) {
            edgeLabel = NormalPath.INSTANCE;
        }
        controlFlowGraphBuilder.addEdgeToSubGraph(cFGNode, cFGNode2, edgeLabel);
    }

    private final void addEdgeToSubGraph(CFGNode<?> from, CFGNode<?> to, EdgeLabel label) {
        boolean isDead = to.getIsDead();
        boolean z = isDead || from.getIsDead();
        CFGNode.INSTANCE.addEdge(from, to, z ? EdgeKind.DeadForward : EdgeKind.CfgForward, true, label);
        if (!z || isDead) {
            return;
        }
        propagateDeadnessForward(to);
    }

    static /* synthetic */ void addBackEdge$default(ControlFlowGraphBuilder controlFlowGraphBuilder, CFGNode cFGNode, CFGNode cFGNode2, boolean z, EdgeLabel edgeLabel, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            edgeLabel = NormalPath.INSTANCE;
        }
        controlFlowGraphBuilder.addBackEdge(cFGNode, cFGNode2, z, edgeLabel);
    }

    private final void addBackEdge(CFGNode<?> from, CFGNode<?> to, boolean isDead, EdgeLabel label) {
        CFGNode.INSTANCE.addEdge(from, to, (isDead || from.getIsDead() || to.getIsDead()) ? EdgeKind.DeadBackward : EdgeKind.CfgBackward, false, label);
    }

    private final void propagateDeadnessForward(CFGNode<?> node) {
        if (node.getIsDead()) {
            for (CFGNode<?> cFGNode : node.getFollowingNodes()) {
                EdgeKind kind = node.edgeTo(cFGNode).getKind();
                if (CFGNode.INSTANCE.killEdge(node, cFGNode, false) && !kind.getIsBack() && kind.getUsedInCfa()) {
                    cFGNode.updateDeadStatus();
                    propagateDeadnessForward(cFGNode);
                }
            }
        }
    }

    public final Pair<ScriptExitNode, ControlFlowGraph> exitScript() {
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        CFGNode<?> exitNode = controlFlowGraphPop.getExitNode();
        if (exitNode == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.ScriptExitNode");
        }
        ScriptExitNode scriptExitNode = (ScriptExitNode) exitNode;
        popAndAddEdge$default(this, scriptExitNode, null, 2, null);
        if (scriptExitNode.getPreviousNodes().size() > 1) {
            scriptExitNode.updateDeadStatus();
        }
        controlFlowGraphPop.complete();
        return TuplesKt.to(scriptExitNode, controlFlowGraphPop);
    }

    public final Pair<CodeFragmentExitNode, ControlFlowGraph> exitCodeFragment() {
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        CFGNode<?> exitNode = controlFlowGraphPop.getExitNode();
        if (exitNode == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.CodeFragmentExitNode");
        }
        CodeFragmentExitNode codeFragmentExitNode = (CodeFragmentExitNode) exitNode;
        popAndAddEdge$default(this, codeFragmentExitNode, null, 2, null);
        if (codeFragmentExitNode.getPreviousNodes().size() > 1) {
            codeFragmentExitNode.updateDeadStatus();
        }
        controlFlowGraphPop.complete();
        return TuplesKt.to(codeFragmentExitNode, controlFlowGraphPop);
    }

    public final Pair<InitBlockExitNode, ControlFlowGraph> exitInitBlock() {
        ControlFlowGraph controlFlowGraphPop = this.graphs.pop();
        CFGNode<?> exitNode = controlFlowGraphPop.getExitNode();
        if (exitNode == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.dfa.cfg.InitBlockExitNode");
        }
        InitBlockExitNode initBlockExitNode = (InitBlockExitNode) exitNode;
        popAndAddEdge$default(this, initBlockExitNode, null, 2, null);
        if (initBlockExitNode.getPreviousNodes().size() > 1) {
            initBlockExitNode.updateDeadStatus();
        }
        controlFlowGraphPop.complete();
        return TuplesKt.to(initBlockExitNode, controlFlowGraphPop);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final FirConstructor exitClass$parentConstructor(FirConstructor firConstructor, final Map<FirConstructor, FirConstructor> map) {
        FirConstructor firConstructor2;
        FirReference calleeReference;
        FirConstructorSymbol resolvedConstructorSymbol;
        FirConstructor firConstructor3;
        if (!map.containsKey(firConstructor)) {
            firConstructor2 = null;
            map.put(firConstructor, null);
            FirDelegatedConstructorCall delegatedConstructor = firConstructor.getDelegatedConstructor();
            if (delegatedConstructor != null) {
                if (!delegatedConstructor.getIsThis()) {
                    delegatedConstructor = null;
                }
                if (delegatedConstructor != null && (calleeReference = delegatedConstructor.getCalleeReference()) != null && (resolvedConstructorSymbol = FirReferenceUtilsKt.toResolvedConstructorSymbol(calleeReference, true)) != null && (firConstructor3 = (FirConstructor) resolvedConstructorSymbol.getFir()) != null && !SequencesKt.contains(SequencesKt.generateSequence(firConstructor3, new Function1<FirConstructor, FirConstructor>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphBuilder$exitClass$parentConstructor$1$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final FirConstructor invoke(FirConstructor it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return ControlFlowGraphBuilder.exitClass$parentConstructor(it, map);
                    }
                }), firConstructor)) {
                    firConstructor2 = firConstructor3;
                }
            }
            map.put(firConstructor, firConstructor2);
        } else {
            firConstructor2 = map.get(firConstructor);
        }
        return firConstructor2;
    }
}
