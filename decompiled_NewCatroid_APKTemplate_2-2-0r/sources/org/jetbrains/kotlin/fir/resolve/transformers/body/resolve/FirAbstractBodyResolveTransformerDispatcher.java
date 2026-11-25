package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirTargetElement;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement;
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirIncrementDecrementExpression;
import org.jetbrains.kotlin.fir.expressions.FirJump;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedDelegateExpression;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponentsKt;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.dfa.DataFlowAnalyzerContext;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSpecificTypeResolverTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;
import org.jetbrains.kotlin.fir.resolve.transformers.ScopeClassDeclaration;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.ImportingScopesKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttributeKt;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: FirAbstractBodyResolveTransformerDispatcher.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ð\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010J\u0018\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0016J\u0018\u00107\u001a\u0002022\u0006\u00108\u001a\u0002092\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010:\u001a\u0002022\u0006\u0010;\u001a\u00020<2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010=\u001a\u0002022\u0006\u0010>\u001a\u00020?2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020A2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010C\u001a\u0002022\u0006\u0010D\u001a\u00020E2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010F\u001a\u0002022\u0006\u0010G\u001a\u00020H2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010I\u001a\u0002022\u0006\u0010J\u001a\u00020K2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010L\u001a\u0002022\u0006\u0010M\u001a\u00020N2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010O\u001a\u0002022\u0006\u0010P\u001a\u00020Q2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010R\u001a\u0002022\u0006\u0010S\u001a\u00020T2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010U\u001a\u0002022\u0006\u0010V\u001a\u00020W2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010X\u001a\u0002022\u0006\u0010Y\u001a\u00020Z2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010[\u001a\u0002022\u0006\u0010\\\u001a\u00020]2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020_2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010a\u001a\u0002022\u0006\u0010b\u001a\u00020c2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010d\u001a\u0002022\u0006\u0010e\u001a\u00020f2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020h2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010j\u001a\u0002022\u0006\u0010k\u001a\u00020l2\u0006\u00105\u001a\u000206H\u0016J$\u0010m\u001a\u000202\"\u0004\b\u0000\u0010n2\f\u0010o\u001a\b\u0012\u0004\u0012\u0002Hn0p2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020r2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020u2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010w\u001a\u00020u2\u0006\u0010v\u001a\u00020u2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020y2\u0006\u00105\u001a\u000206H\u0016J\u0018\u0010{\u001a\u0002022\u0006\u0010|\u001a\u00020}2\u0006\u00105\u001a\u000206H\u0016J\u0019\u0010~\u001a\u0002022\u0007\u0010\u007f\u001a\u00030\u0080\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010\u0081\u0001\u001a\u0002022\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0006\u00105\u001a\u000206H\u0016J.\u0010\u0084\u0001\u001a\u0003H\u0085\u0001\"\n\b\u0000\u0010\u0085\u0001*\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u0003H\u0085\u00012\u0006\u00105\u001a\u000206H\u0016¢\u0006\u0003\u0010\u0088\u0001J\u001b\u0010\u0089\u0001\u001a\u0002022\b\u0010\u008a\u0001\u001a\u00030\u008b\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010\u008c\u0001\u001a\u00030\u008d\u00012\b\u0010\u008e\u0001\u001a\u00030\u008d\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010\u008f\u0001\u001a\u0002022\b\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010\u0092\u0001\u001a\u0002022\b\u0010\u0093\u0001\u001a\u00030\u0094\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010\u0095\u0001\u001a\u0002022\b\u0010\u0096\u0001\u001a\u00030\u0097\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010\u0098\u0001\u001a\u00030\u0099\u00012\b\u0010\u009a\u0001\u001a\u00030\u0099\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010\u009b\u0001\u001a\u00030\u009c\u00012\b\u0010\u009d\u0001\u001a\u00030\u009c\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010\u009e\u0001\u001a\u0002022\b\u0010\u009f\u0001\u001a\u00030 \u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010¡\u0001\u001a\u0002022\b\u0010¢\u0001\u001a\u00030£\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010¤\u0001\u001a\u0002022\b\u0010¥\u0001\u001a\u00030¦\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010§\u0001\u001a\u00030¨\u00012\b\u0010©\u0001\u001a\u00030ª\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010«\u0001\u001a\u0002022\b\u0010¬\u0001\u001a\u00030\u00ad\u00012\u0006\u00105\u001a\u000206H\u0016J.\u0010®\u0001\u001a\u000202\"\n\b\u0000\u0010\u0085\u0001*\u00030¯\u00012\u000f\u0010°\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0085\u00010±\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010²\u0001\u001a\u00030³\u00012\b\u0010´\u0001\u001a\u00030³\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010µ\u0001\u001a\u0002022\b\u0010¶\u0001\u001a\u00030·\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010¸\u0001\u001a\u0002022\b\u0010¹\u0001\u001a\u00030º\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010»\u0001\u001a\u0002022\b\u0010¼\u0001\u001a\u00030½\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010¾\u0001\u001a\u0002022\b\u0010¿\u0001\u001a\u00030À\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010Á\u0001\u001a\u0002022\b\u0010Â\u0001\u001a\u00030Ã\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010Ä\u0001\u001a\u0002022\b\u0010Å\u0001\u001a\u00030Æ\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010Ç\u0001\u001a\u00030È\u00012\b\u0010É\u0001\u001a\u00030È\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010Ê\u0001\u001a\u00030Ë\u00012\b\u0010Ì\u0001\u001a\u00030Ë\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010Í\u0001\u001a\u0002022\b\u0010Î\u0001\u001a\u00030Ï\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010Ð\u0001\u001a\u0002022\b\u0010Ñ\u0001\u001a\u00030Ò\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010Ó\u0001\u001a\u0002022\b\u0010Ô\u0001\u001a\u00030Õ\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010Ö\u0001\u001a\u0002022\b\u0010×\u0001\u001a\u00030Ø\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010Ù\u0001\u001a\u0002022\b\u0010Ú\u0001\u001a\u00030Û\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010Ü\u0001\u001a\u00030Ý\u00012\b\u0010Þ\u0001\u001a\u00030Ý\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010ß\u0001\u001a\u0002022\b\u0010à\u0001\u001a\u00030á\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010â\u0001\u001a\u00030ã\u00012\b\u0010ä\u0001\u001a\u00030¨\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010å\u0001\u001a\u0002022\b\u0010æ\u0001\u001a\u00030ç\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010è\u0001\u001a\u0002022\b\u0010é\u0001\u001a\u00030ê\u00012\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010ë\u0001\u001a\u00030ì\u00012\b\u0010í\u0001\u001a\u00030ì\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010î\u0001\u001a\u0002022\b\u0010ï\u0001\u001a\u00030ð\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010ñ\u0001\u001a\u0002022\b\u0010ò\u0001\u001a\u00030ó\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010ô\u0001\u001a\u0002022\b\u0010õ\u0001\u001a\u00030ö\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010÷\u0001\u001a\u0002022\b\u0010ø\u0001\u001a\u00030ù\u00012\u0006\u00105\u001a\u000206H\u0016J\u001b\u0010ú\u0001\u001a\u0002022\b\u0010û\u0001\u001a\u00030ü\u00012\u0006\u00105\u001a\u000206H\u0016J4\u0010ý\u0001\u001a\u00030þ\u0001\"\u0005\b\u0000\u0010ÿ\u0001*\u00030\u0086\u00012\u000f\u0010\u0080\u0002\u001a\n\u0012\u0005\u0012\u0003Hÿ\u00010\u0081\u00022\u0007\u00105\u001a\u0003Hÿ\u0001¢\u0006\u0003\u0010\u0082\u0002R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R$\u0010\u0006\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u0007@PX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010&R\u0011\u0010+\u001a\u00020,¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b/\u00100¨\u0006\u0083\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "implicitTypeOnly", "", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "outerBodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "firResolveContextCollector", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;ZLorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;)V", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "context", "getContext", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "controlFlowStatementsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirControlFlowStatementsResolveTransformer;", "declarationsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "getDeclarationsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "expressionsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "getExpressionsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "getFirResolveContextCollector", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "<set-?>", "getImplicitTypeOnly", "()Z", "setImplicitTypeOnly$resolve", "(Z)V", "preserveCFGForClasses", "getPreserveCFGForClasses", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "getResolutionContext", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "getReturnTypeCalculator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "transformAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "transformAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "transformAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "transformAnonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "anonymousInitializer", "transformAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "transformAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "transformArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "transformAssignmentOperatorStatement", "assignmentOperatorStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "transformAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "transformBackingField", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "transformBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "transformBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "transformCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "transformCatch", "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", PsiKeyword.CATCH, "transformCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "transformCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "transformCodeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "codeFragment", "transformComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "transformConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "transformConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "transformDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "declaration", "transformDeclarationContent", "transformDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "declarationStatus", "transformDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "transformDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "transformDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "transformEnumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "enumEntry", "transformEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "transformErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "transformExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "transformField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "field", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "transformFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "transformFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "transformGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "transformImplicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "transformIncrementDecrementExpression", "incrementDecrementExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;", "transformJump", "Lorg/jetbrains/kotlin/fir/FirTargetElement;", "jump", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "transformProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "property", "transformPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "transformPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "transformQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "transformRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "transformSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "transformScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "script", "transformSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "simpleFunction", "transformSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "transformStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "transformThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "transformThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "transformTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "transformTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "typeAlias", "transformTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "transformTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "typeRef", "transformValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "transformVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "transformWhenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "whenBranch", "transformWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "transformWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "transformWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "transformWrappedArgumentExpression", "wrappedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;", "transformWrappedDelegateExpression", "wrappedDelegateExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;", "visitNoTransform", "", "D", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractBodyResolveTransformerDispatcher extends FirAbstractBodyResolveTransformer {
    private final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components;
    private final BodyResolveContext context;
    private final FirControlFlowStatementsResolveTransformer controlFlowStatementsTransformer;
    private final FirResolveContextCollector firResolveContextCollector;
    private boolean implicitTypeOnly;
    private final ResolutionContext resolutionContext;
    private final ReturnTypeCalculator returnTypeCalculator;

    public abstract FirDeclarationsResolveTransformer getDeclarationsTransformer();

    public abstract FirExpressionsResolveTransformer getExpressionsTransformer();

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public boolean getImplicitTypeOnly() {
        return this.implicitTypeOnly;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public void setImplicitTypeOnly$resolve(boolean z) {
        this.implicitTypeOnly = z;
    }

    public /* synthetic */ FirAbstractBodyResolveTransformerDispatcher(FirSession firSession, FirResolvePhase firResolvePhase, boolean z, ScopeSession scopeSession, ReturnTypeCalculator returnTypeCalculator, BodyResolveContext bodyResolveContext, FirResolveContextCollector firResolveContextCollector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firResolvePhase, z, scopeSession, (i & 16) != 0 ? ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getDefault() : returnTypeCalculator, (i & 32) != 0 ? null : bodyResolveContext, (i & 64) != 0 ? null : firResolveContextCollector);
    }

    public final ReturnTypeCalculator getReturnTypeCalculator() {
        return this.returnTypeCalculator;
    }

    public final FirResolveContextCollector getFirResolveContextCollector() {
        return this.firResolveContextCollector;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAbstractBodyResolveTransformerDispatcher(FirSession session, FirResolvePhase phase, boolean z, ScopeSession scopeSession, ReturnTypeCalculator returnTypeCalculator, BodyResolveContext bodyResolveContext, FirResolveContextCollector firResolveContextCollector) {
        super(phase);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(phase, "phase");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        this.implicitTypeOnly = z;
        this.returnTypeCalculator = returnTypeCalculator;
        this.firResolveContextCollector = firResolveContextCollector;
        bodyResolveContext = bodyResolveContext == null ? new BodyResolveContext(returnTypeCalculator, new DataFlowAnalyzerContext(session), null, null, 12, null) : bodyResolveContext;
        this.context = bodyResolveContext;
        FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents bodyResolveTransformerComponents = new FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents(session, scopeSession, this, bodyResolveContext);
        this.components = bodyResolveTransformerComponents;
        this.resolutionContext = new ResolutionContext(session, bodyResolveTransformerComponents, bodyResolveContext);
        this.controlFlowStatementsTransformer = new FirControlFlowStatementsResolveTransformer(this);
    }

    public boolean getPreserveCFGForClasses() {
        return !getImplicitTypeOnly();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public final BodyResolveContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents getComponents() {
        return this.components;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer
    public final ResolutionContext getResolutionContext() {
        return this.resolutionContext;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(data, "data");
        checkSessionConsistency(file);
        BodyResolveContext bodyResolveContext = this.context;
        FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents bodyResolveTransformerComponents = this.components;
        bodyResolveContext.clear();
        bodyResolveContext.setFile(file);
        List<FirScope> fileImportsScope = bodyResolveContext.getFileImportsScope();
        int size = fileImportsScope.size();
        int i = 0;
        try {
            FirTowerDataContext towerDataContext = bodyResolveContext.getTowerDataContext();
            try {
                List listCreateImportingScopes$default = ImportingScopesKt.createImportingScopes$default(file, bodyResolveTransformerComponents.getSession(), bodyResolveTransformerComponents.getScopeSession(), false, 8, null);
                CollectionsKt.addAll(bodyResolveContext.getFileImportsScope(), listCreateImportingScopes$default);
                List list = listCreateImportingScopes$default;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(ImplicitReceiverUtilsKt.asTowerDataElement((FirScope) it.next(), false));
                }
                bodyResolveContext.addNonLocalTowerDataElements(arrayList);
                try {
                    FirResolveContextCollector firResolveContextCollector = this.firResolveContextCollector;
                    if (firResolveContextCollector != null) {
                        firResolveContextCollector.addFileContext(file, this.context.getTowerDataContext());
                    }
                    FirDeclaration firDeclarationTransformDeclarationContent = transformDeclarationContent(file, data);
                    Intrinsics.checkNotNull(firDeclarationTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirFile");
                    FirFile firFile = (FirFile) firDeclarationTransformDeclarationContent;
                    bodyResolveContext.replaceTowerDataContext(towerDataContext);
                    int size2 = fileImportsScope.size() - size;
                    while (i < size2) {
                        fileImportsScope.remove(fileImportsScope.size() - 1);
                        i++;
                    }
                    return firFile;
                } catch (Throwable th) {
                    UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
                    throw null;
                }
            } catch (Throwable th2) {
                bodyResolveContext.replaceTowerDataContext(towerDataContext);
                throw th2;
            }
        } catch (Throwable th3) {
            int size3 = fileImportsScope.size() - size;
            while (i < size3) {
                fileImportsScope.remove(fileImportsScope.size() - 1);
                i++;
            }
            throw th3;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirScript transformScript(FirScript script, ResolutionMode data) {
        FirScript firScriptTransformScript;
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firScriptTransformScript = declarationsTransformer.transformScript(script, data)) == null) ? script : firScriptTransformScript;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirCodeFragment transformCodeFragment(FirCodeFragment codeFragment, ResolutionMode data) {
        FirCodeFragment firCodeFragmentTransformCodeFragment;
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firCodeFragmentTransformCodeFragment = declarationsTransformer.transformCodeFragment(codeFragment, data)) == null) ? codeFragment : firCodeFragmentTransformCodeFragment;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        E e = (E) element.transformChildren(this, data);
        Intrinsics.checkNotNull(e, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher.transformElement");
        return e;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirResolvedTypeRef transformTypeRef(FirTypeRef typeRef, final ResolutionMode data) {
        FirDeclaration firDeclarationPrevious;
        FirResolvedTypeRef firResolvedTypeRefTransformTypeRef;
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        if (typeRef instanceof FirResolvedTypeRef) {
            firResolvedTypeRefTransformTypeRef = (FirResolvedTypeRef) typeRef;
        } else {
            FirSpecificTypeResolverTransformer typeResolverTransformer = getComponents().getTypeResolverTransformer();
            FirFile file = this.context.getFile();
            FirFile firFile = typeResolverTransformer.currentFile;
            typeResolverTransformer.currentFile = file;
            try {
                List<FirScope> listCreateCurrentScopeList = BodyResolveComponentsKt.createCurrentScopeList(this.components);
                ArrayDeque<FirRegularClass> containingClassDeclarations = this.context.getContainingClassDeclarations();
                ArrayDeque<FirDeclaration> containers = this.context.getContainers();
                ListIterator<FirDeclaration> listIterator = containers.listIterator(containers.size());
                while (true) {
                    if (!listIterator.hasPrevious()) {
                        firDeclarationPrevious = null;
                        break;
                    }
                    firDeclarationPrevious = listIterator.previous();
                    FirDeclaration firDeclaration = firDeclarationPrevious;
                    if ((firDeclaration instanceof FirTypeParameterRefsOwner) && !(firDeclaration instanceof FirAnonymousFunction)) {
                        break;
                    }
                }
                firResolvedTypeRefTransformTypeRef = typeResolverTransformer.transformTypeRef(typeRef, new ScopeClassDeclaration(listCreateCurrentScopeList, containingClassDeclarations, firDeclarationPrevious, null, 8, null));
                typeResolverTransformer.currentFile = firFile;
            } catch (Throwable th) {
                typeResolverTransformer.currentFile = firFile;
                throw th;
            }
        }
        ConeTypeUtilsKt.forEachType(FirTypeUtilsKt.getConeType(firResolvedTypeRefTransformTypeRef), new Function1<ConeKotlinType, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher.transformTypeRef.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ConeKotlinType coneKotlinType) {
                invoke2(coneKotlinType);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                List<FirAnnotation> customAnnotations = CustomAnnotationTypeAttributeKt.getCustomAnnotations(it.getType().getAttributes());
                FirAbstractBodyResolveTransformerDispatcher firAbstractBodyResolveTransformerDispatcher = FirAbstractBodyResolveTransformerDispatcher.this;
                ResolutionMode resolutionMode = data;
                Iterator<T> it2 = customAnnotations.iterator();
                while (it2.hasNext()) {
                    ((FirAnnotation) it2.next()).accept(firAbstractBodyResolveTransformerDispatcher, resolutionMode);
                }
            }
        });
        return firResolvedTypeRefTransformTypeRef.transformAnnotations((FirTransformer<? super ResolutionMode>) this, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!(data instanceof ResolutionMode.WithExpectedType)) {
            return implicitTypeRef;
        }
        return ((ResolutionMode.WithExpectedType) data).getExpectedTypeRef();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformExpression(FirExpression expression, ResolutionMode data) {
        FirStatement firStatementTransformExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformExpression = expressionsTransformer.transformExpression(expression, data)) == null) ? expression : firStatementTransformExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWrappedArgumentExpression(FirWrappedArgumentExpression wrappedArgumentExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return (FirStatement) transformElement(wrappedArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, ResolutionMode data) {
        FirStatement firStatementTransformQualifiedAccessExpression;
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformQualifiedAccessExpression = expressionsTransformer.transformQualifiedAccessExpression(qualifiedAccessExpression, data)) == null) ? qualifiedAccessExpression : firStatementTransformQualifiedAccessExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, ResolutionMode data) {
        FirStatement firStatementTransformQualifiedAccessExpression;
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformQualifiedAccessExpression = expressionsTransformer.transformQualifiedAccessExpression((FirQualifiedAccessExpression) propertyAccessExpression, data)) == null) ? propertyAccessExpression : firStatementTransformQualifiedAccessExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformFunctionCall(FirFunctionCall functionCall, ResolutionMode data) {
        FirStatement firStatementTransformFunctionCall;
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformFunctionCall = expressionsTransformer.transformFunctionCall(functionCall, data)) == null) ? functionCall : firStatementTransformFunctionCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, ResolutionMode data) {
        FirStatement firStatementTransformStringConcatenationCall;
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformStringConcatenationCall = expressionsTransformer.transformStringConcatenationCall(stringConcatenationCall, data)) == null) ? stringConcatenationCall : firStatementTransformStringConcatenationCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, ResolutionMode data) {
        FirStatement firStatementTransformCallableReferenceAccess;
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformCallableReferenceAccess = expressionsTransformer.transformCallableReferenceAccess(callableReferenceAccess, data)) == null) ? callableReferenceAccess : firStatementTransformCallableReferenceAccess;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBlock(FirBlock block, ResolutionMode data) {
        FirStatement firStatementTransformBlock;
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformBlock = expressionsTransformer.transformBlock(block, data)) == null) ? block : firStatementTransformBlock;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, ResolutionMode data) {
        FirStatement firStatementTransformThisReceiverExpression;
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformThisReceiverExpression = expressionsTransformer.transformThisReceiverExpression(thisReceiverExpression, data)) == null) ? thisReceiverExpression : firStatementTransformThisReceiverExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformComparisonExpression(FirComparisonExpression comparisonExpression, ResolutionMode data) {
        FirStatement firStatementTransformComparisonExpression;
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformComparisonExpression = expressionsTransformer.transformComparisonExpression(comparisonExpression, data)) == null) ? comparisonExpression : firStatementTransformComparisonExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, ResolutionMode data) {
        FirStatement firStatementTransformTypeOperatorCall;
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformTypeOperatorCall = expressionsTransformer.transformTypeOperatorCall(typeOperatorCall, data)) == null) ? typeOperatorCall : firStatementTransformTypeOperatorCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAssignmentOperatorStatement(FirAssignmentOperatorStatement assignmentOperatorStatement, ResolutionMode data) {
        FirStatement firStatementTransformAssignmentOperatorStatement;
        Intrinsics.checkNotNullParameter(assignmentOperatorStatement, "assignmentOperatorStatement");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformAssignmentOperatorStatement = expressionsTransformer.transformAssignmentOperatorStatement(assignmentOperatorStatement, data)) == null) ? assignmentOperatorStatement : firStatementTransformAssignmentOperatorStatement;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformIncrementDecrementExpression(FirIncrementDecrementExpression incrementDecrementExpression, ResolutionMode data) {
        FirStatement firStatementTransformIncrementDecrementExpression;
        Intrinsics.checkNotNullParameter(incrementDecrementExpression, "incrementDecrementExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformIncrementDecrementExpression = expressionsTransformer.transformIncrementDecrementExpression(incrementDecrementExpression, data)) == null) ? incrementDecrementExpression : firStatementTransformIncrementDecrementExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, ResolutionMode data) {
        FirStatement firStatementTransformEqualityOperatorCall;
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformEqualityOperatorCall = expressionsTransformer.transformEqualityOperatorCall(equalityOperatorCall, data)) == null) ? equalityOperatorCall : firStatementTransformEqualityOperatorCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, ResolutionMode data) {
        FirStatement firStatementTransformCheckNotNullCall;
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformCheckNotNullCall = expressionsTransformer.transformCheckNotNullCall(checkNotNullCall, data)) == null) ? checkNotNullCall : firStatementTransformCheckNotNullCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, ResolutionMode data) {
        FirStatement firStatementTransformBinaryLogicExpression;
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformBinaryLogicExpression = expressionsTransformer.transformBinaryLogicExpression(binaryLogicExpression, data)) == null) ? binaryLogicExpression : firStatementTransformBinaryLogicExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression, ResolutionMode data) {
        FirStatement firStatementTransformDesugaredAssignmentValueReferenceExpression;
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformDesugaredAssignmentValueReferenceExpression = expressionsTransformer.transformDesugaredAssignmentValueReferenceExpression(desugaredAssignmentValueReferenceExpression, data)) == null) ? desugaredAssignmentValueReferenceExpression : firStatementTransformDesugaredAssignmentValueReferenceExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformVariableAssignment(FirVariableAssignment variableAssignment, ResolutionMode data) {
        FirStatement firStatementTransformVariableAssignment;
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformVariableAssignment = expressionsTransformer.transformVariableAssignment(variableAssignment, data)) == null) ? variableAssignment : firStatementTransformVariableAssignment;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformGetClassCall(FirGetClassCall getClassCall, ResolutionMode data) {
        FirStatement firStatementTransformGetClassCall;
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformGetClassCall = expressionsTransformer.transformGetClassCall(getClassCall, data)) == null) ? getClassCall : firStatementTransformGetClassCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWrappedDelegateExpression(FirWrappedDelegateExpression wrappedDelegateExpression, ResolutionMode data) {
        FirStatement firStatementTransformWrappedDelegateExpression;
        Intrinsics.checkNotNullParameter(wrappedDelegateExpression, "wrappedDelegateExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firStatementTransformWrappedDelegateExpression = declarationsTransformer.transformWrappedDelegateExpression(wrappedDelegateExpression, data)) == null) ? wrappedDelegateExpression : firStatementTransformWrappedDelegateExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <T> FirStatement transformConstExpression(FirConstExpression<T> constExpression, ResolutionMode data) {
        FirStatement firStatementTransformConstExpression;
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformConstExpression = expressionsTransformer.transformConstExpression((FirConstExpression) constExpression, data)) == null) ? constExpression : firStatementTransformConstExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotation(FirAnnotation annotation, ResolutionMode data) {
        FirStatement firStatementTransformAnnotation;
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformAnnotation = expressionsTransformer.transformAnnotation(annotation, data)) == null) ? annotation : firStatementTransformAnnotation;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, ResolutionMode data) {
        FirStatement firStatementTransformAnnotationCall;
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformAnnotationCall = expressionsTransformer.transformAnnotationCall(annotationCall, data)) == null) ? annotationCall : firStatementTransformAnnotationCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorAnnotationCall(FirErrorAnnotationCall errorAnnotationCall, ResolutionMode data) {
        FirStatement firStatementTransformErrorAnnotationCall;
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformErrorAnnotationCall = expressionsTransformer.transformErrorAnnotationCall(errorAnnotationCall, data)) == null) ? errorAnnotationCall : firStatementTransformErrorAnnotationCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, ResolutionMode data) {
        FirStatement firStatementTransformDelegatedConstructorCall;
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformDelegatedConstructorCall = expressionsTransformer.transformDelegatedConstructorCall(delegatedConstructorCall, data)) == null) ? delegatedConstructorCall : firStatementTransformDelegatedConstructorCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall, ResolutionMode data) {
        FirStatement firStatementTransformAugmentedArraySetCall;
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformAugmentedArraySetCall = expressionsTransformer.transformAugmentedArraySetCall(augmentedArraySetCall, data)) == null) ? augmentedArraySetCall : firStatementTransformAugmentedArraySetCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSafeCallExpression(FirSafeCallExpression safeCallExpression, ResolutionMode data) {
        FirStatement firStatementTransformSafeCallExpression;
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformSafeCallExpression = expressionsTransformer.transformSafeCallExpression(safeCallExpression, data)) == null) ? safeCallExpression : firStatementTransformSafeCallExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject, ResolutionMode data) {
        FirStatement firStatementTransformCheckedSafeCallSubject;
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformCheckedSafeCallSubject = expressionsTransformer.transformCheckedSafeCallSubject(checkedSafeCallSubject, data)) == null) ? checkedSafeCallSubject : firStatementTransformCheckedSafeCallSubject;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformArrayLiteral(FirArrayLiteral arrayLiteral, ResolutionMode data) {
        FirStatement firStatementTransformArrayLiteral;
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformArrayLiteral = expressionsTransformer.transformArrayLiteral(arrayLiteral, data)) == null) ? arrayLiteral : firStatementTransformArrayLiteral;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSmartCastExpression(FirSmartCastExpression smartCastExpression, ResolutionMode data) {
        FirStatement firStatementTransformSmartCastExpression;
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformSmartCastExpression = expressionsTransformer.transformSmartCastExpression(smartCastExpression, data)) == null) ? smartCastExpression : firStatementTransformSmartCastExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirDeclaration transformDeclaration(FirDeclaration declaration, ResolutionMode data) {
        FirDeclaration firDeclarationTransformDeclaration;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firDeclarationTransformDeclaration = declarationsTransformer.transformDeclaration(declaration, data)) == null) ? declaration : firDeclarationTransformDeclaration;
    }

    public FirDeclaration transformDeclarationContent(FirDeclaration declaration, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        return (FirDeclaration) transformElement(declaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirDeclarationStatus transformDeclarationStatus(FirDeclarationStatus declarationStatus, ResolutionMode data) {
        FirDeclarationStatus firDeclarationStatusTransformDeclarationStatus;
        Intrinsics.checkNotNullParameter(declarationStatus, "declarationStatus");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firDeclarationStatusTransformDeclarationStatus = declarationsTransformer.transformDeclarationStatus(declarationStatus, data)) == null) ? declarationStatus : firDeclarationStatusTransformDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirEnumEntry transformEnumEntry(FirEnumEntry enumEntry, ResolutionMode data) {
        FirEnumEntry firEnumEntryTransformEnumEntry;
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firEnumEntryTransformEnumEntry = declarationsTransformer.transformEnumEntry(enumEntry, data)) == null) ? enumEntry : firEnumEntryTransformEnumEntry;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirProperty transformProperty(FirProperty property, ResolutionMode data) {
        FirProperty firPropertyTransformProperty;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firPropertyTransformProperty = declarationsTransformer.transformProperty(property, data)) == null) ? property : firPropertyTransformProperty;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformPropertyAccessor(FirPropertyAccessor propertyAccessor, ResolutionMode data) {
        FirStatement firStatementTransformPropertyAccessor;
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firStatementTransformPropertyAccessor = declarationsTransformer.transformPropertyAccessor(propertyAccessor, data)) == null) ? propertyAccessor : firStatementTransformPropertyAccessor;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBackingField(FirBackingField backingField, ResolutionMode data) {
        FirStatement firStatementTransformBackingField;
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firStatementTransformBackingField = declarationsTransformer.transformBackingField(backingField, data)) == null) ? backingField : firStatementTransformBackingField;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirField transformField(FirField field, ResolutionMode data) {
        FirField firFieldTransformField;
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firFieldTransformField = declarationsTransformer.transformField(field, data)) == null) ? field : firFieldTransformField;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, ResolutionMode data) {
        FirStatement firStatementTransformRegularClass;
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firStatementTransformRegularClass = declarationsTransformer.transformRegularClass(regularClass, data)) == null) ? regularClass : firStatementTransformRegularClass;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousObject(FirAnonymousObject anonymousObject, ResolutionMode data) {
        FirStatement firStatementTransformAnonymousObject;
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firStatementTransformAnonymousObject = declarationsTransformer.transformAnonymousObject(anonymousObject, data)) == null) ? anonymousObject : firStatementTransformAnonymousObject;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, ResolutionMode data) {
        FirStatement firStatementTransformAnonymousObjectExpression;
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformAnonymousObjectExpression = expressionsTransformer.transformAnonymousObjectExpression(anonymousObjectExpression, data)) == null) ? anonymousObjectExpression : firStatementTransformAnonymousObjectExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirSimpleFunction transformSimpleFunction(FirSimpleFunction simpleFunction, ResolutionMode data) {
        FirSimpleFunction firSimpleFunctionTransformSimpleFunction;
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firSimpleFunctionTransformSimpleFunction = declarationsTransformer.transformSimpleFunction(simpleFunction, data)) == null) ? simpleFunction : firSimpleFunctionTransformSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformFunction(FirFunction function, ResolutionMode data) {
        FirStatement firStatementTransformFunction;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firStatementTransformFunction = declarationsTransformer.transformFunction(function, data)) == null) ? function : firStatementTransformFunction;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirConstructor transformConstructor(FirConstructor constructor, ResolutionMode data) {
        FirConstructor firConstructorTransformConstructor;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firConstructorTransformConstructor = declarationsTransformer.transformConstructor(constructor, data)) == null) ? constructor : firConstructorTransformConstructor;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirAnonymousInitializer transformAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, ResolutionMode data) {
        FirAnonymousInitializer firAnonymousInitializerTransformAnonymousInitializer;
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firAnonymousInitializerTransformAnonymousInitializer = declarationsTransformer.transformAnonymousInitializer(anonymousInitializer, data)) == null) ? anonymousInitializer : firAnonymousInitializerTransformAnonymousInitializer;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousFunction(FirAnonymousFunction anonymousFunction, ResolutionMode data) {
        FirStatement firStatementTransformAnonymousFunction;
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firStatementTransformAnonymousFunction = declarationsTransformer.transformAnonymousFunction(anonymousFunction, data)) == null) ? anonymousFunction : firStatementTransformAnonymousFunction;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, ResolutionMode data) {
        FirStatement firStatementTransformAnonymousFunctionExpression;
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer expressionsTransformer = getExpressionsTransformer();
        return (expressionsTransformer == null || (firStatementTransformAnonymousFunctionExpression = expressionsTransformer.transformAnonymousFunctionExpression(anonymousFunctionExpression, data)) == null) ? anonymousFunctionExpression : firStatementTransformAnonymousFunctionExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformValueParameter(FirValueParameter valueParameter, ResolutionMode data) {
        FirStatement firStatementTransformValueParameter;
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firStatementTransformValueParameter = declarationsTransformer.transformValueParameter(valueParameter, data)) == null) ? valueParameter : firStatementTransformValueParameter;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeAlias transformTypeAlias(FirTypeAlias typeAlias, ResolutionMode data) {
        FirTypeAlias firTypeAliasTransformTypeAlias;
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer declarationsTransformer = getDeclarationsTransformer();
        return (declarationsTransformer == null || (firTypeAliasTransformTypeAlias = declarationsTransformer.transformTypeAlias(typeAlias, data)) == null) ? typeAlias : firTypeAliasTransformTypeAlias;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWhileLoop(FirWhileLoop whileLoop, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformWhileLoop(whileLoop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformDoWhileLoop(FirDoWhileLoop doWhileLoop, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformDoWhileLoop(doWhileLoop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWhenExpression(FirWhenExpression whenExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformWhenExpression(whenExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirWhenBranch transformWhenBranch(FirWhenBranch whenBranch, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformWhenBranch(whenBranch, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformWhenSubjectExpression(whenSubjectExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTryExpression(FirTryExpression tryExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformTryExpression(tryExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirCatch transformCatch(FirCatch firCatch, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformCatch(firCatch, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirTargetElement> FirStatement transformJump(FirJump<E> jump, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformJump((FirJump) jump, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformReturnExpression(FirReturnExpression returnExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformReturnExpression(returnExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformThrowExpression(FirThrowExpression throwExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformThrowExpression(throwExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformElvisExpression(FirElvisExpression elvisExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.controlFlowStatementsTransformer.transformElvisExpression(elvisExpression, data);
    }

    public final <D> void visitNoTransform(FirElement firElement, FirTransformer<? super D> transformer, D d) {
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirElement firElementTransform = firElement.transform(transformer, d);
        if (firElementTransform != firElement) {
            throw new IllegalArgumentException(("become " + firElementTransform + ": `" + UtilsKt.render(firElementTransform) + "`, was " + firElement + ": `" + UtilsKt.render(firElement) + '`').toString());
        }
    }
}
