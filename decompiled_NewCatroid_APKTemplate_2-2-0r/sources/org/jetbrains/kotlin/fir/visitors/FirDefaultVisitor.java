package org.jetbrains.kotlin.fir.visitors;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirTargetElement;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructedClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirOuterClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement;
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirEnumEntryDeserializedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirIncrementDecrementExpression;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirJump;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirLazyBlock;
import org.jetbrains.kotlin.fir.expressions.FirLazyExpression;
import org.jetbrains.kotlin.fir.expressions.FirLoopJump;
import org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirResolvedReifiedParameterReference;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedDelegateExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedExpression;
import org.jetbrains.kotlin.fir.references.FirBackingFieldReference;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.references.FirDelegateFieldReference;
import org.jetbrains.kotlin.fir.references.FirFromMissingDependenciesNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReferenceWithCandidateBase;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.types.FirDynamicTypeRef;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirIntersectionTypeRef;
import org.jetbrains.kotlin.fir.types.FirPlaceholderProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;

/* JADX INFO: compiled from: FirDefaultVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Æ\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00002\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\tJ\u001d\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\rJ\u001d\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0011J\u001d\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0015J\u001d\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010!J\u001d\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010%J\u001d\u0010&\u001a\u00028\u00002\u0006\u0010'\u001a\u00020(2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010)J\u001d\u0010*\u001a\u00028\u00002\u0006\u0010+\u001a\u00020,2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010-J\u001d\u0010.\u001a\u00028\u00002\u0006\u0010/\u001a\u0002002\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00101J\u001d\u00102\u001a\u00028\u00002\u0006\u00103\u001a\u0002042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00105J\u001d\u00106\u001a\u00028\u00002\u0006\u00107\u001a\u0002082\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00109J\u001d\u0010:\u001a\u00028\u00002\u0006\u0010;\u001a\u00020<2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010=J\u001d\u0010>\u001a\u00028\u00002\u0006\u0010?\u001a\u00020@2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010AJ\u001d\u0010B\u001a\u00028\u00002\u0006\u0010C\u001a\u00020D2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010EJ\u001d\u0010F\u001a\u00028\u00002\u0006\u0010G\u001a\u00020H2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010IJ\u001d\u0010J\u001a\u00028\u00002\u0006\u0010K\u001a\u00020L2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010MJ)\u0010N\u001a\u00028\u0000\"\u0004\b\u0002\u0010O2\f\u0010P\u001a\b\u0012\u0004\u0012\u0002HO0Q2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010RJ\u001d\u0010S\u001a\u00028\u00002\u0006\u0010T\u001a\u00020U2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010VJ\u001d\u0010W\u001a\u00028\u00002\u0006\u0010X\u001a\u00020Y2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010ZJ\u001d\u0010[\u001a\u00028\u00002\u0006\u0010\\\u001a\u00020]2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010^J\u001d\u0010_\u001a\u00028\u00002\u0006\u0010`\u001a\u00020a2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010bJ\u001d\u0010c\u001a\u00028\u00002\u0006\u0010d\u001a\u00020e2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010fJ\u001d\u0010g\u001a\u00028\u00002\u0006\u0010h\u001a\u00020i2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010jJ\u001d\u0010k\u001a\u00028\u00002\u0006\u0010l\u001a\u00020m2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010nJ\u001d\u0010o\u001a\u00028\u00002\u0006\u0010p\u001a\u00020q2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010rJ\u001d\u0010s\u001a\u00028\u00002\u0006\u0010t\u001a\u00020u2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010vJ\u001d\u0010w\u001a\u00028\u00002\u0006\u0010x\u001a\u00020y2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010zJ\u001d\u0010{\u001a\u00028\u00002\u0006\u0010|\u001a\u00020}2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010~J \u0010\u007f\u001a\u00028\u00002\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0082\u0001J!\u0010\u0083\u0001\u001a\u00028\u00002\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0086\u0001J!\u0010\u0087\u0001\u001a\u00028\u00002\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008a\u0001J!\u0010\u008b\u0001\u001a\u00028\u00002\b\u0010\u008c\u0001\u001a\u00030\u008d\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008e\u0001J!\u0010\u008f\u0001\u001a\u00028\u00002\b\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0092\u0001J!\u0010\u0093\u0001\u001a\u00028\u00002\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0096\u0001J!\u0010\u0097\u0001\u001a\u00028\u00002\b\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009a\u0001J!\u0010\u009b\u0001\u001a\u00028\u00002\b\u0010\u009c\u0001\u001a\u00030\u009d\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009e\u0001J4\u0010\u009f\u0001\u001a\u00028\u0000\"\n\b\u0002\u0010 \u0001*\u00030¡\u00012\u000f\u0010¢\u0001\u001a\n\u0012\u0005\u0012\u0003H \u00010£\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¤\u0001J!\u0010¥\u0001\u001a\u00028\u00002\b\u0010¦\u0001\u001a\u00030§\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¨\u0001J!\u0010©\u0001\u001a\u00028\u00002\b\u0010ª\u0001\u001a\u00030«\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¬\u0001J!\u0010\u00ad\u0001\u001a\u00028\u00002\b\u0010®\u0001\u001a\u00030¯\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010°\u0001J!\u0010±\u0001\u001a\u00028\u00002\b\u0010²\u0001\u001a\u00030³\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010´\u0001J!\u0010µ\u0001\u001a\u00028\u00002\b\u0010¶\u0001\u001a\u00030·\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¸\u0001J!\u0010¹\u0001\u001a\u00028\u00002\b\u0010º\u0001\u001a\u00030»\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¼\u0001J!\u0010½\u0001\u001a\u00028\u00002\b\u0010¾\u0001\u001a\u00030¿\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010À\u0001J!\u0010Á\u0001\u001a\u00028\u00002\b\u0010Â\u0001\u001a\u00030Ã\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ä\u0001J!\u0010Å\u0001\u001a\u00028\u00002\b\u0010Æ\u0001\u001a\u00030Ç\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010È\u0001J!\u0010É\u0001\u001a\u00028\u00002\b\u0010Ê\u0001\u001a\u00030Ë\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ì\u0001J!\u0010Í\u0001\u001a\u00028\u00002\b\u0010Î\u0001\u001a\u00030Ï\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ð\u0001J!\u0010Ñ\u0001\u001a\u00028\u00002\b\u0010Ò\u0001\u001a\u00030Ó\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ô\u0001J!\u0010Õ\u0001\u001a\u00028\u00002\b\u0010Ö\u0001\u001a\u00030×\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ø\u0001J!\u0010Ù\u0001\u001a\u00028\u00002\b\u0010Ú\u0001\u001a\u00030Û\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ü\u0001J!\u0010Ý\u0001\u001a\u00028\u00002\b\u0010Þ\u0001\u001a\u00030ß\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010à\u0001J!\u0010á\u0001\u001a\u00028\u00002\b\u0010â\u0001\u001a\u00030ã\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ä\u0001J!\u0010å\u0001\u001a\u00028\u00002\b\u0010æ\u0001\u001a\u00030ç\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010è\u0001J!\u0010é\u0001\u001a\u00028\u00002\b\u0010ê\u0001\u001a\u00030ë\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ì\u0001J!\u0010í\u0001\u001a\u00028\u00002\b\u0010î\u0001\u001a\u00030ï\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ð\u0001J!\u0010ñ\u0001\u001a\u00028\u00002\b\u0010ò\u0001\u001a\u00030ó\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ô\u0001J!\u0010õ\u0001\u001a\u00028\u00002\b\u0010ö\u0001\u001a\u00030÷\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ø\u0001J!\u0010ù\u0001\u001a\u00028\u00002\b\u0010ú\u0001\u001a\u00030û\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ü\u0001J!\u0010ý\u0001\u001a\u00028\u00002\b\u0010þ\u0001\u001a\u00030ÿ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0080\u0002J!\u0010\u0081\u0002\u001a\u00028\u00002\b\u0010\u0082\u0002\u001a\u00030\u0083\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0084\u0002J!\u0010\u0085\u0002\u001a\u00028\u00002\b\u0010\u0086\u0002\u001a\u00030\u0087\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0088\u0002J!\u0010\u0089\u0002\u001a\u00028\u00002\b\u0010\u008a\u0002\u001a\u00030\u008b\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008c\u0002J!\u0010\u008d\u0002\u001a\u00028\u00002\b\u0010\u008e\u0002\u001a\u00030\u008f\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0090\u0002J!\u0010\u0091\u0002\u001a\u00028\u00002\b\u0010\u0092\u0002\u001a\u00030\u0093\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0094\u0002J!\u0010\u0095\u0002\u001a\u00028\u00002\b\u0010\u0096\u0002\u001a\u00030\u0097\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0098\u0002J!\u0010\u0099\u0002\u001a\u00028\u00002\b\u0010\u009a\u0002\u001a\u00030\u009b\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009c\u0002J!\u0010\u009d\u0002\u001a\u00028\u00002\b\u0010\u009e\u0002\u001a\u00030\u009f\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010 \u0002J!\u0010¡\u0002\u001a\u00028\u00002\b\u0010¢\u0002\u001a\u00030£\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¤\u0002J!\u0010¥\u0002\u001a\u00028\u00002\b\u0010¦\u0002\u001a\u00030§\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¨\u0002J!\u0010©\u0002\u001a\u00028\u00002\b\u0010ª\u0002\u001a\u00030«\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¬\u0002J!\u0010\u00ad\u0002\u001a\u00028\u00002\b\u0010®\u0002\u001a\u00030¯\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010°\u0002J!\u0010±\u0002\u001a\u00028\u00002\b\u0010²\u0002\u001a\u00030³\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010´\u0002J!\u0010µ\u0002\u001a\u00028\u00002\b\u0010¶\u0002\u001a\u00030·\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¸\u0002J!\u0010¹\u0002\u001a\u00028\u00002\b\u0010º\u0002\u001a\u00030»\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¼\u0002J!\u0010½\u0002\u001a\u00028\u00002\b\u0010¾\u0002\u001a\u00030¿\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010À\u0002J!\u0010Á\u0002\u001a\u00028\u00002\b\u0010Â\u0002\u001a\u00030Ã\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ä\u0002J!\u0010Å\u0002\u001a\u00028\u00002\b\u0010Æ\u0002\u001a\u00030Ç\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010È\u0002J!\u0010É\u0002\u001a\u00028\u00002\b\u0010Ê\u0002\u001a\u00030Ë\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ì\u0002J!\u0010Í\u0002\u001a\u00028\u00002\b\u0010Î\u0002\u001a\u00030Ï\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ð\u0002J!\u0010Ñ\u0002\u001a\u00028\u00002\b\u0010Ò\u0002\u001a\u00030Ó\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ô\u0002J!\u0010Õ\u0002\u001a\u00028\u00002\b\u0010Ö\u0002\u001a\u00030×\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ø\u0002J!\u0010Ù\u0002\u001a\u00028\u00002\b\u0010Ú\u0002\u001a\u00030Û\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ü\u0002J!\u0010Ý\u0002\u001a\u00028\u00002\b\u0010Þ\u0002\u001a\u00030ß\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010à\u0002¨\u0006á\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "R", "D", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "()V", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitAssignmentOperatorStatement", "assignmentOperatorStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;Ljava/lang/Object;)Ljava/lang/Object;", "visitAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitBackingFieldReference", "backingFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirBackingFieldReference;", "(Lorg/jetbrains/kotlin/fir/references/FirBackingFieldReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;Ljava/lang/Object;)Ljava/lang/Object;", "visitBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitCall", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitCallableDeclaration", "callableDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;Ljava/lang/Object;)Ljava/lang/Object;", "visitCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;Ljava/lang/Object;)Ljava/lang/Object;", "visitClassReferenceExpression", "classReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;Ljava/lang/Object;)Ljava/lang/Object;", "visitComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitComponentCall", "componentCall", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstructedClassTypeParameterRef", "constructedClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitControlFlowGraphReference", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitDelegateFieldReference", "delegateFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;", "(Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;Ljava/lang/Object;)Ljava/lang/Object;", "visitDynamicTypeRef", "dynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitEffectDeclaration", "effectDeclaration", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "(Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "(Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnumEntryDeserializedAccessExpression", "enumEntryDeserializedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/lang/Object;)Ljava/lang/Object;", "visitFromMissingDependenciesNamedReference", "fromMissingDependenciesNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirFromMissingDependenciesNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirFromMissingDependenciesNamedReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionTypeRef", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitImplicitInvokeCall", "implicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitImplicitTypeRef", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitIncrementDecrementExpression", "incrementDecrementExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitIntegerLiteralOperatorCall", "integerLiteralOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitIntersectionTypeRef", "intersectionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitJump", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirTargetElement;", "jump", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "(Lorg/jetbrains/kotlin/fir/expressions/FirJump;Ljava/lang/Object;)Ljava/lang/Object;", "visitLambdaArgumentExpression", "lambdaArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitLazyBlock", "lazyBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;Ljava/lang/Object;)Ljava/lang/Object;", "visitLazyExpression", "lazyExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitLegacyRawContractDescription", "legacyRawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoopJump", "loopJump", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;Ljava/lang/Object;)Ljava/lang/Object;", "visitMultiDelegatedConstructorCall", "multiDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitNamedReference", "namedReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitNamedReferenceWithCandidateBase", "namedReferenceWithCandidateBase", "Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;Ljava/lang/Object;)Ljava/lang/Object;", "visitOuterClassTypeParameterRef", "outerClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitPlaceholderProjection", "placeholderProjection", "Lorg/jetbrains/kotlin/fir/types/FirPlaceholderProjection;", "(Lorg/jetbrains/kotlin/fir/types/FirPlaceholderProjection;Ljava/lang/Object;)Ljava/lang/Object;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitRawContractDescription", "rawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;Ljava/lang/Object;)Ljava/lang/Object;", "visitReceiverParameter", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;Ljava/lang/Object;)Ljava/lang/Object;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedCallableReference", "resolvedCallableReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedContractDescription", "resolvedContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedDeclarationStatus", "resolvedDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedErrorReference", "resolvedErrorReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedImport", "resolvedImport", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedNamedReference", "resolvedNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedQualifier", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedReifiedParameterReference", "resolvedReifiedParameterReference", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedReifiedParameterReference;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedReifiedParameterReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "(Lorg/jetbrains/kotlin/fir/declarations/FirScript;Ljava/lang/Object;)Ljava/lang/Object;", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitSpreadArgumentExpression", "spreadArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitStarProjection", "starProjection", "Lorg/jetbrains/kotlin/fir/types/FirStarProjection;", "(Lorg/jetbrains/kotlin/fir/types/FirStarProjection;Ljava/lang/Object;)Ljava/lang/Object;", "visitStatement", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Ljava/lang/Object;)Ljava/lang/Object;", "visitSuperReference", "superReference", "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", "(Lorg/jetbrains/kotlin/fir/references/FirSuperReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitThisReference", "thisReference", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "(Lorg/jetbrains/kotlin/fir/references/FirThisReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeParametersOwner", "typeParametersOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeProjectionWithVariance", "typeProjectionWithVariance", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeRefWithNullability", "typeRefWithNullability", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;Ljava/lang/Object;)Ljava/lang/Object;", "visitUserTypeRef", "userTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;Ljava/lang/Object;)Ljava/lang/Object;", "visitWrappedArgumentExpression", "wrappedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitWrappedDelegateExpression", "wrappedDelegateExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitWrappedExpression", "wrappedExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedExpression;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDefaultVisitor<R, D> extends FirVisitor<R, D> {
    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitTypeRef(FirTypeRef typeRef, D data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        return visitAnnotationContainer(typeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedDeclarationStatus(FirResolvedDeclarationStatus resolvedDeclarationStatus, D data) {
        Intrinsics.checkNotNullParameter(resolvedDeclarationStatus, "resolvedDeclarationStatus");
        return visitDeclarationStatus(resolvedDeclarationStatus, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitStatement(FirStatement statement, D data) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        return visitAnnotationContainer(statement, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitExpression(FirExpression expression, D data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitStatement(expression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitLazyExpression(FirLazyExpression lazyExpression, D data) {
        Intrinsics.checkNotNullParameter(lazyExpression, "lazyExpression");
        return visitExpression(lazyExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitTypeParametersOwner(FirTypeParametersOwner typeParametersOwner, D data) {
        Intrinsics.checkNotNullParameter(typeParametersOwner, "typeParametersOwner");
        return visitTypeParameterRefsOwner(typeParametersOwner, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitCallableDeclaration(FirCallableDeclaration callableDeclaration, D data) {
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        return visitMemberDeclaration(callableDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitConstructedClassTypeParameterRef(FirConstructedClassTypeParameterRef constructedClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(constructedClassTypeParameterRef, "constructedClassTypeParameterRef");
        return visitTypeParameterRef(constructedClassTypeParameterRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitOuterClassTypeParameterRef(FirOuterClassTypeParameterRef outerClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(outerClassTypeParameterRef, "outerClassTypeParameterRef");
        return visitTypeParameterRef(outerClassTypeParameterRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitReceiverParameter(FirReceiverParameter receiverParameter, D data) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        return visitAnnotationContainer(receiverParameter, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitEnumEntry(FirEnumEntry enumEntry, D data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return visitVariable(enumEntry, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitRegularClass(FirRegularClass regularClass, D data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        return visitClass(regularClass, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitFile(FirFile file, D data) {
        Intrinsics.checkNotNullParameter(file, "file");
        return visitDeclaration(file, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitScript(FirScript script, D data) {
        Intrinsics.checkNotNullParameter(script, "script");
        return visitDeclaration(script, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitCodeFragment(FirCodeFragment codeFragment, D data) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        return visitDeclaration(codeFragment, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, D data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        return visitExpression(anonymousFunctionExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitAnonymousObject(FirAnonymousObject anonymousObject, D data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        return visitClass(anonymousObject, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, D data) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        return visitExpression(anonymousObjectExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedImport(FirResolvedImport resolvedImport, D data) {
        Intrinsics.checkNotNullParameter(resolvedImport, "resolvedImport");
        return visitImport(resolvedImport, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitDoWhileLoop(FirDoWhileLoop doWhileLoop, D data) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        return visitLoop(doWhileLoop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitWhileLoop(FirWhileLoop whileLoop, D data) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        return visitLoop(whileLoop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitBlock(FirBlock block, D data) {
        Intrinsics.checkNotNullParameter(block, "block");
        return visitExpression(block, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitLazyBlock(FirLazyBlock lazyBlock, D data) {
        Intrinsics.checkNotNullParameter(lazyBlock, "lazyBlock");
        return visitBlock(lazyBlock, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, D data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        return visitExpression(binaryLogicExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public <E extends FirTargetElement> R visitJump(FirJump<E> jump, D data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return visitExpression(jump, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitLoopJump(FirLoopJump loopJump, D data) {
        Intrinsics.checkNotNullParameter(loopJump, "loopJump");
        return visitJump(loopJump, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitBreakExpression(FirBreakExpression breakExpression, D data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        return visitLoopJump(breakExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitContinueExpression(FirContinueExpression continueExpression, D data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        return visitLoopJump(continueExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public <T> R visitConstExpression(FirConstExpression<T> constExpression, D data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        return visitExpression(constExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitStarProjection(FirStarProjection starProjection, D data) {
        Intrinsics.checkNotNullParameter(starProjection, "starProjection");
        return visitTypeProjection(starProjection, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitPlaceholderProjection(FirPlaceholderProjection placeholderProjection, D data) {
        Intrinsics.checkNotNullParameter(placeholderProjection, "placeholderProjection");
        return visitTypeProjection(placeholderProjection, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitTypeProjectionWithVariance(FirTypeProjectionWithVariance typeProjectionWithVariance, D data) {
        Intrinsics.checkNotNullParameter(typeProjectionWithVariance, "typeProjectionWithVariance");
        return visitTypeProjection(typeProjectionWithVariance, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitCall(FirCall call, D data) {
        Intrinsics.checkNotNullParameter(call, "call");
        return visitStatement(call, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitAnnotation(FirAnnotation annotation, D data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return visitExpression(annotation, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitComparisonExpression(FirComparisonExpression comparisonExpression, D data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        return visitExpression(comparisonExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitAssignmentOperatorStatement(FirAssignmentOperatorStatement assignmentOperatorStatement, D data) {
        Intrinsics.checkNotNullParameter(assignmentOperatorStatement, "assignmentOperatorStatement");
        return visitStatement(assignmentOperatorStatement, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitIncrementDecrementExpression(FirIncrementDecrementExpression incrementDecrementExpression, D data) {
        Intrinsics.checkNotNullParameter(incrementDecrementExpression, "incrementDecrementExpression");
        return visitExpression(incrementDecrementExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall, D data) {
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        return visitStatement(augmentedArraySetCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitClassReferenceExpression(FirClassReferenceExpression classReferenceExpression, D data) {
        Intrinsics.checkNotNullParameter(classReferenceExpression, "classReferenceExpression");
        return visitExpression(classReferenceExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        return visitQualifiedAccessExpression(propertyAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall integerLiteralOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
        return visitFunctionCall(integerLiteralOperatorCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitImplicitInvokeCall(FirImplicitInvokeCall implicitInvokeCall, D data) {
        Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
        return visitFunctionCall(implicitInvokeCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitMultiDelegatedConstructorCall(FirMultiDelegatedConstructorCall multiDelegatedConstructorCall, D data) {
        Intrinsics.checkNotNullParameter(multiDelegatedConstructorCall, "multiDelegatedConstructorCall");
        return visitDelegatedConstructorCall(multiDelegatedConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitComponentCall(FirComponentCall componentCall, D data) {
        Intrinsics.checkNotNullParameter(componentCall, "componentCall");
        return visitFunctionCall(componentCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, D data) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        return visitQualifiedAccessExpression(callableReferenceAccess, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, D data) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        return visitQualifiedAccessExpression(thisReceiverExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitSmartCastExpression(FirSmartCastExpression smartCastExpression, D data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        return visitExpression(smartCastExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitSafeCallExpression(FirSafeCallExpression safeCallExpression, D data) {
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        return visitExpression(safeCallExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject, D data) {
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        return visitExpression(checkedSafeCallSubject, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitWrappedExpression(FirWrappedExpression wrappedExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedExpression, "wrappedExpression");
        return visitExpression(wrappedExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitWrappedArgumentExpression(FirWrappedArgumentExpression wrappedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        return visitWrappedExpression(wrappedArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitLambdaArgumentExpression(FirLambdaArgumentExpression lambdaArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
        return visitWrappedArgumentExpression(lambdaArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitSpreadArgumentExpression(FirSpreadArgumentExpression spreadArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
        return visitWrappedArgumentExpression(spreadArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        return visitWrappedArgumentExpression(namedArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, D data) {
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        return visitExpression(varargArgumentsExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedQualifier(FirResolvedQualifier resolvedQualifier, D data) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        return visitExpression(resolvedQualifier, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedReifiedParameterReference(FirResolvedReifiedParameterReference resolvedReifiedParameterReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedReifiedParameterReference, "resolvedReifiedParameterReference");
        return visitExpression(resolvedReifiedParameterReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitReturnExpression(FirReturnExpression returnExpression, D data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        return visitJump(returnExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitThrowExpression(FirThrowExpression throwExpression, D data) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        return visitExpression(throwExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitVariableAssignment(FirVariableAssignment variableAssignment, D data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        return visitStatement(variableAssignment, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression, D data) {
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        return visitExpression(whenSubjectExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression, D data) {
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        return visitExpression(desugaredAssignmentValueReferenceExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitWrappedDelegateExpression(FirWrappedDelegateExpression wrappedDelegateExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedDelegateExpression, "wrappedDelegateExpression");
        return visitWrappedExpression(wrappedDelegateExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitEnumEntryDeserializedAccessExpression(FirEnumEntryDeserializedAccessExpression enumEntryDeserializedAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(enumEntryDeserializedAccessExpression, "enumEntryDeserializedAccessExpression");
        return visitExpression(enumEntryDeserializedAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitNamedReference(FirNamedReference namedReference, D data) {
        Intrinsics.checkNotNullParameter(namedReference, "namedReference");
        return visitReference(namedReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitNamedReferenceWithCandidateBase(FirNamedReferenceWithCandidateBase namedReferenceWithCandidateBase, D data) {
        Intrinsics.checkNotNullParameter(namedReferenceWithCandidateBase, "namedReferenceWithCandidateBase");
        return visitNamedReference(namedReferenceWithCandidateBase, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitFromMissingDependenciesNamedReference(FirFromMissingDependenciesNamedReference fromMissingDependenciesNamedReference, D data) {
        Intrinsics.checkNotNullParameter(fromMissingDependenciesNamedReference, "fromMissingDependenciesNamedReference");
        return visitNamedReference(fromMissingDependenciesNamedReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitSuperReference(FirSuperReference superReference, D data) {
        Intrinsics.checkNotNullParameter(superReference, "superReference");
        return visitReference(superReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitThisReference(FirThisReference thisReference, D data) {
        Intrinsics.checkNotNullParameter(thisReference, "thisReference");
        return visitReference(thisReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitControlFlowGraphReference(FirControlFlowGraphReference controlFlowGraphReference, D data) {
        Intrinsics.checkNotNullParameter(controlFlowGraphReference, "controlFlowGraphReference");
        return visitReference(controlFlowGraphReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedNamedReference(FirResolvedNamedReference resolvedNamedReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedNamedReference, "resolvedNamedReference");
        return visitNamedReference(resolvedNamedReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedErrorReference(FirResolvedErrorReference resolvedErrorReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedErrorReference, "resolvedErrorReference");
        return visitResolvedNamedReference(resolvedErrorReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitDelegateFieldReference(FirDelegateFieldReference delegateFieldReference, D data) {
        Intrinsics.checkNotNullParameter(delegateFieldReference, "delegateFieldReference");
        return visitResolvedNamedReference(delegateFieldReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitBackingFieldReference(FirBackingFieldReference backingFieldReference, D data) {
        Intrinsics.checkNotNullParameter(backingFieldReference, "backingFieldReference");
        return visitResolvedNamedReference(backingFieldReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedCallableReference(FirResolvedCallableReference resolvedCallableReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedCallableReference, "resolvedCallableReference");
        return visitResolvedNamedReference(resolvedCallableReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef, D data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        return visitTypeRef(resolvedTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitTypeRefWithNullability(FirTypeRefWithNullability typeRefWithNullability, D data) {
        Intrinsics.checkNotNullParameter(typeRefWithNullability, "typeRefWithNullability");
        return visitTypeRef(typeRefWithNullability, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitUserTypeRef(FirUserTypeRef userTypeRef, D data) {
        Intrinsics.checkNotNullParameter(userTypeRef, "userTypeRef");
        return visitTypeRefWithNullability(userTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitDynamicTypeRef(FirDynamicTypeRef dynamicTypeRef, D data) {
        Intrinsics.checkNotNullParameter(dynamicTypeRef, "dynamicTypeRef");
        return visitTypeRefWithNullability(dynamicTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitFunctionTypeRef(FirFunctionTypeRef functionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        return visitTypeRefWithNullability(functionTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitIntersectionTypeRef(FirIntersectionTypeRef intersectionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(intersectionTypeRef, "intersectionTypeRef");
        return visitTypeRefWithNullability(intersectionTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, D data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        return visitTypeRef(implicitTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitEffectDeclaration(FirEffectDeclaration effectDeclaration, D data) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        return visitContractElementDeclaration(effectDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitLegacyRawContractDescription(FirLegacyRawContractDescription legacyRawContractDescription, D data) {
        Intrinsics.checkNotNullParameter(legacyRawContractDescription, "legacyRawContractDescription");
        return visitContractDescription(legacyRawContractDescription, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitRawContractDescription(FirRawContractDescription rawContractDescription, D data) {
        Intrinsics.checkNotNullParameter(rawContractDescription, "rawContractDescription");
        return visitContractDescription(rawContractDescription, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public R visitResolvedContractDescription(FirResolvedContractDescription resolvedContractDescription, D data) {
        Intrinsics.checkNotNullParameter(resolvedContractDescription, "resolvedContractDescription");
        return visitContractDescription(resolvedContractDescription, data);
    }
}
