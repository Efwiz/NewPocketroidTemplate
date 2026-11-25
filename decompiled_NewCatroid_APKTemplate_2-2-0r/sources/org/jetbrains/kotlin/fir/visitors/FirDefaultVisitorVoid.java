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

/* JADX INFO: compiled from: FirDefaultVisitorVoid.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Æ\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u00042\u0006\u0010,\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u00042\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020\u00042\u0006\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u00020\u00042\u0006\u00105\u001a\u000206H\u0016J\u0010\u00107\u001a\u00020\u00042\u0006\u00108\u001a\u000209H\u0016J\u001c\u0010:\u001a\u00020\u0004\"\u0004\b\u0000\u0010;2\f\u0010<\u001a\b\u0012\u0004\u0012\u0002H;0=H\u0016J\u0010\u0010>\u001a\u00020\u00042\u0006\u0010?\u001a\u00020@H\u0016J\u0010\u0010A\u001a\u00020\u00042\u0006\u0010B\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u00020\u00042\u0006\u0010E\u001a\u00020FH\u0016J\u0010\u0010G\u001a\u00020\u00042\u0006\u0010H\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020\u00042\u0006\u0010K\u001a\u00020LH\u0016J\u0010\u0010M\u001a\u00020\u00042\u0006\u0010N\u001a\u00020OH\u0016J\u0010\u0010P\u001a\u00020\u00042\u0006\u0010Q\u001a\u00020RH\u0016J\u0010\u0010S\u001a\u00020\u00042\u0006\u0010T\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u00020\u00042\u0006\u0010W\u001a\u00020XH\u0016J\u0010\u0010Y\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020[H\u0016J\u0010\u0010\\\u001a\u00020\u00042\u0006\u0010]\u001a\u00020^H\u0016J\u0010\u0010_\u001a\u00020\u00042\u0006\u0010`\u001a\u00020aH\u0016J\u0010\u0010b\u001a\u00020\u00042\u0006\u0010c\u001a\u00020dH\u0016J\u0010\u0010e\u001a\u00020\u00042\u0006\u0010f\u001a\u00020gH\u0016J\u0010\u0010h\u001a\u00020\u00042\u0006\u0010i\u001a\u00020jH\u0016J\u0010\u0010k\u001a\u00020\u00042\u0006\u0010l\u001a\u00020mH\u0016J\u0010\u0010n\u001a\u00020\u00042\u0006\u0010o\u001a\u00020pH\u0016J\u0010\u0010q\u001a\u00020\u00042\u0006\u0010r\u001a\u00020sH\u0016J\u0010\u0010t\u001a\u00020\u00042\u0006\u0010u\u001a\u00020vH\u0016J \u0010w\u001a\u00020\u0004\"\b\b\u0000\u0010x*\u00020y2\f\u0010z\u001a\b\u0012\u0004\u0012\u0002Hx0{H\u0016J\u0010\u0010|\u001a\u00020\u00042\u0006\u0010}\u001a\u00020~H\u0016J\u0012\u0010\u007f\u001a\u00020\u00042\b\u0010\u0080\u0001\u001a\u00030\u0081\u0001H\u0016J\u0013\u0010\u0082\u0001\u001a\u00020\u00042\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0016J\u0013\u0010\u0085\u0001\u001a\u00020\u00042\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001H\u0016J\u0013\u0010\u0088\u0001\u001a\u00020\u00042\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001H\u0016J\u0013\u0010\u008b\u0001\u001a\u00020\u00042\b\u0010\u008c\u0001\u001a\u00030\u008d\u0001H\u0016J\u0013\u0010\u008e\u0001\u001a\u00020\u00042\b\u0010\u008f\u0001\u001a\u00030\u0090\u0001H\u0016J\u0013\u0010\u0091\u0001\u001a\u00020\u00042\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001H\u0016J\u0013\u0010\u0094\u0001\u001a\u00020\u00042\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0016J\u0013\u0010\u0097\u0001\u001a\u00020\u00042\b\u0010\u0098\u0001\u001a\u00030\u0099\u0001H\u0016J\u0013\u0010\u009a\u0001\u001a\u00020\u00042\b\u0010\u009b\u0001\u001a\u00030\u009c\u0001H\u0016J\u0013\u0010\u009d\u0001\u001a\u00020\u00042\b\u0010\u009e\u0001\u001a\u00030\u009f\u0001H\u0016J\u0013\u0010 \u0001\u001a\u00020\u00042\b\u0010¡\u0001\u001a\u00030¢\u0001H\u0016J\u0013\u0010£\u0001\u001a\u00020\u00042\b\u0010¤\u0001\u001a\u00030¥\u0001H\u0016J\u0013\u0010¦\u0001\u001a\u00020\u00042\b\u0010§\u0001\u001a\u00030¨\u0001H\u0016J\u0013\u0010©\u0001\u001a\u00020\u00042\b\u0010ª\u0001\u001a\u00030«\u0001H\u0016J\u0013\u0010¬\u0001\u001a\u00020\u00042\b\u0010\u00ad\u0001\u001a\u00030®\u0001H\u0016J\u0013\u0010¯\u0001\u001a\u00020\u00042\b\u0010°\u0001\u001a\u00030±\u0001H\u0016J\u0013\u0010²\u0001\u001a\u00020\u00042\b\u0010³\u0001\u001a\u00030´\u0001H\u0016J\u0013\u0010µ\u0001\u001a\u00020\u00042\b\u0010¶\u0001\u001a\u00030·\u0001H\u0016J\u0013\u0010¸\u0001\u001a\u00020\u00042\b\u0010¹\u0001\u001a\u00030º\u0001H\u0016J\u0013\u0010»\u0001\u001a\u00020\u00042\b\u0010¼\u0001\u001a\u00030½\u0001H\u0016J\u0013\u0010¾\u0001\u001a\u00020\u00042\b\u0010¿\u0001\u001a\u00030À\u0001H\u0016J\u0013\u0010Á\u0001\u001a\u00020\u00042\b\u0010Â\u0001\u001a\u00030Ã\u0001H\u0016J\u0013\u0010Ä\u0001\u001a\u00020\u00042\b\u0010Å\u0001\u001a\u00030Æ\u0001H\u0016J\u0013\u0010Ç\u0001\u001a\u00020\u00042\b\u0010È\u0001\u001a\u00030É\u0001H\u0016J\u0013\u0010Ê\u0001\u001a\u00020\u00042\b\u0010Ë\u0001\u001a\u00030Ì\u0001H\u0016J\u0013\u0010Í\u0001\u001a\u00020\u00042\b\u0010Î\u0001\u001a\u00030Ï\u0001H\u0016J\u0013\u0010Ð\u0001\u001a\u00020\u00042\b\u0010Ñ\u0001\u001a\u00030Ò\u0001H\u0016J\u0013\u0010Ó\u0001\u001a\u00020\u00042\b\u0010Ô\u0001\u001a\u00030Õ\u0001H\u0016J\u0013\u0010Ö\u0001\u001a\u00020\u00042\b\u0010×\u0001\u001a\u00030Ø\u0001H\u0016J\u0013\u0010Ù\u0001\u001a\u00020\u00042\b\u0010Ú\u0001\u001a\u00030Û\u0001H\u0016J\u0013\u0010Ü\u0001\u001a\u00020\u00042\b\u0010Ý\u0001\u001a\u00030Þ\u0001H\u0016J\u0013\u0010ß\u0001\u001a\u00020\u00042\b\u0010à\u0001\u001a\u00030á\u0001H\u0016J\u0013\u0010â\u0001\u001a\u00020\u00042\b\u0010ã\u0001\u001a\u00030ä\u0001H\u0016J\u0013\u0010å\u0001\u001a\u00020\u00042\b\u0010æ\u0001\u001a\u00030ç\u0001H\u0016J\u0013\u0010è\u0001\u001a\u00020\u00042\b\u0010é\u0001\u001a\u00030ê\u0001H\u0016J\u0013\u0010ë\u0001\u001a\u00020\u00042\b\u0010ì\u0001\u001a\u00030í\u0001H\u0016J\u0013\u0010î\u0001\u001a\u00020\u00042\b\u0010ï\u0001\u001a\u00030ð\u0001H\u0016J\u0013\u0010ñ\u0001\u001a\u00020\u00042\b\u0010ò\u0001\u001a\u00030ó\u0001H\u0016J\u0013\u0010ô\u0001\u001a\u00020\u00042\b\u0010õ\u0001\u001a\u00030ö\u0001H\u0016J\u0013\u0010÷\u0001\u001a\u00020\u00042\b\u0010ø\u0001\u001a\u00030ù\u0001H\u0016J\u0013\u0010ú\u0001\u001a\u00020\u00042\b\u0010û\u0001\u001a\u00030ü\u0001H\u0016J\u0013\u0010ý\u0001\u001a\u00020\u00042\b\u0010þ\u0001\u001a\u00030ÿ\u0001H\u0016J\u0013\u0010\u0080\u0002\u001a\u00020\u00042\b\u0010\u0081\u0002\u001a\u00030\u0082\u0002H\u0016J\u0013\u0010\u0083\u0002\u001a\u00020\u00042\b\u0010\u0084\u0002\u001a\u00030\u0085\u0002H\u0016J\u0013\u0010\u0086\u0002\u001a\u00020\u00042\b\u0010\u0087\u0002\u001a\u00030\u0088\u0002H\u0016¨\u0006\u0089\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitorVoid;", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitorVoid;", "()V", "visitAnnotation", "", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "visitAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "visitAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "visitAssignmentOperatorStatement", "assignmentOperatorStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "visitAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "visitBackingFieldReference", "backingFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirBackingFieldReference;", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "visitBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "visitBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "visitCall", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "visitCallableDeclaration", "callableDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "visitCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "visitCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "visitClassReferenceExpression", "classReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", "visitCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "visitComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "visitComponentCall", "componentCall", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "visitConstructedClassTypeParameterRef", "constructedClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;", "visitContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "visitControlFlowGraphReference", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "visitDelegateFieldReference", "delegateFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;", "visitDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "visitDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "visitDynamicTypeRef", "dynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;", "visitEffectDeclaration", "effectDeclaration", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "visitEnumEntryDeserializedAccessExpression", "enumEntryDeserializedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", "visitExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "visitFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "visitFromMissingDependenciesNamedReference", "fromMissingDependenciesNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirFromMissingDependenciesNamedReference;", "visitFunctionTypeRef", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "visitImplicitInvokeCall", "implicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "visitImplicitTypeRef", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "visitIncrementDecrementExpression", "incrementDecrementExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;", "visitIntegerLiteralOperatorCall", "integerLiteralOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "visitIntersectionTypeRef", "intersectionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;", "visitJump", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirTargetElement;", "jump", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "visitLambdaArgumentExpression", "lambdaArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;", "visitLazyBlock", "lazyBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;", "visitLazyExpression", "lazyExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;", "visitLegacyRawContractDescription", "legacyRawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", "visitLoopJump", "loopJump", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "visitMultiDelegatedConstructorCall", "multiDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "visitNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "visitNamedReference", "namedReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "visitNamedReferenceWithCandidateBase", "namedReferenceWithCandidateBase", "Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;", "visitOuterClassTypeParameterRef", "outerClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;", "visitPlaceholderProjection", "placeholderProjection", "Lorg/jetbrains/kotlin/fir/types/FirPlaceholderProjection;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "visitRawContractDescription", "rawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;", "visitReceiverParameter", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitResolvedCallableReference", "resolvedCallableReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;", "visitResolvedContractDescription", "resolvedContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", "visitResolvedDeclarationStatus", "resolvedDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "visitResolvedErrorReference", "resolvedErrorReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;", "visitResolvedImport", "resolvedImport", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "visitResolvedNamedReference", "resolvedNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "visitResolvedQualifier", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "visitResolvedReifiedParameterReference", "resolvedReifiedParameterReference", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedReifiedParameterReference;", "visitResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "visitReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "visitSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "visitSpreadArgumentExpression", "spreadArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;", "visitStarProjection", "starProjection", "Lorg/jetbrains/kotlin/fir/types/FirStarProjection;", "visitStatement", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "visitSuperReference", "superReference", "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", "visitThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "visitThisReference", "thisReference", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "visitThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "visitTypeParametersOwner", "typeParametersOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;", "visitTypeProjectionWithVariance", "typeProjectionWithVariance", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;", "visitTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visitTypeRefWithNullability", "typeRefWithNullability", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", "visitUserTypeRef", "userTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "visitVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "visitWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "visitWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "visitWrappedArgumentExpression", "wrappedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;", "visitWrappedDelegateExpression", "wrappedDelegateExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;", "visitWrappedExpression", "wrappedExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDefaultVisitorVoid extends FirVisitorVoid {
    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitTypeRef(FirTypeRef typeRef) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        visitAnnotationContainer(typeRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedDeclarationStatus(FirResolvedDeclarationStatus resolvedDeclarationStatus) {
        Intrinsics.checkNotNullParameter(resolvedDeclarationStatus, "resolvedDeclarationStatus");
        visitDeclarationStatus(resolvedDeclarationStatus);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitStatement(FirStatement statement) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        visitAnnotationContainer(statement);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitExpression(FirExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        visitStatement(expression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitLazyExpression(FirLazyExpression lazyExpression) {
        Intrinsics.checkNotNullParameter(lazyExpression, "lazyExpression");
        visitExpression(lazyExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitTypeParametersOwner(FirTypeParametersOwner typeParametersOwner) {
        Intrinsics.checkNotNullParameter(typeParametersOwner, "typeParametersOwner");
        visitTypeParameterRefsOwner(typeParametersOwner);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitCallableDeclaration(FirCallableDeclaration callableDeclaration) {
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        visitMemberDeclaration(callableDeclaration);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitConstructedClassTypeParameterRef(FirConstructedClassTypeParameterRef constructedClassTypeParameterRef) {
        Intrinsics.checkNotNullParameter(constructedClassTypeParameterRef, "constructedClassTypeParameterRef");
        visitTypeParameterRef(constructedClassTypeParameterRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitOuterClassTypeParameterRef(FirOuterClassTypeParameterRef outerClassTypeParameterRef) {
        Intrinsics.checkNotNullParameter(outerClassTypeParameterRef, "outerClassTypeParameterRef");
        visitTypeParameterRef(outerClassTypeParameterRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitReceiverParameter(FirReceiverParameter receiverParameter) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        visitAnnotationContainer(receiverParameter);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitEnumEntry(FirEnumEntry enumEntry) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        visitVariable(enumEntry);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitRegularClass(FirRegularClass regularClass) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        visitClass(regularClass);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        visitDeclaration(file);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitScript(FirScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        visitDeclaration(script);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitCodeFragment(FirCodeFragment codeFragment) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        visitDeclaration(codeFragment);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        visitExpression(anonymousFunctionExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitAnonymousObject(FirAnonymousObject anonymousObject) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        visitClass(anonymousObject);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        visitExpression(anonymousObjectExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedImport(FirResolvedImport resolvedImport) {
        Intrinsics.checkNotNullParameter(resolvedImport, "resolvedImport");
        visitImport(resolvedImport);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitDoWhileLoop(FirDoWhileLoop doWhileLoop) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        visitLoop(doWhileLoop);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitWhileLoop(FirWhileLoop whileLoop) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        visitLoop(whileLoop);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitBlock(FirBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        visitExpression(block);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitLazyBlock(FirLazyBlock lazyBlock) {
        Intrinsics.checkNotNullParameter(lazyBlock, "lazyBlock");
        visitBlock(lazyBlock);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        visitExpression(binaryLogicExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public <E extends FirTargetElement> void visitJump(FirJump<E> jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        visitExpression(jump);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitLoopJump(FirLoopJump loopJump) {
        Intrinsics.checkNotNullParameter(loopJump, "loopJump");
        visitJump(loopJump);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitBreakExpression(FirBreakExpression breakExpression) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        visitLoopJump(breakExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitContinueExpression(FirContinueExpression continueExpression) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        visitLoopJump(continueExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public <T> void visitConstExpression(FirConstExpression<T> constExpression) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        visitExpression(constExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitStarProjection(FirStarProjection starProjection) {
        Intrinsics.checkNotNullParameter(starProjection, "starProjection");
        visitTypeProjection(starProjection);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitPlaceholderProjection(FirPlaceholderProjection placeholderProjection) {
        Intrinsics.checkNotNullParameter(placeholderProjection, "placeholderProjection");
        visitTypeProjection(placeholderProjection);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitTypeProjectionWithVariance(FirTypeProjectionWithVariance typeProjectionWithVariance) {
        Intrinsics.checkNotNullParameter(typeProjectionWithVariance, "typeProjectionWithVariance");
        visitTypeProjection(typeProjectionWithVariance);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitCall(FirCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        visitStatement(call);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitAnnotation(FirAnnotation annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        visitExpression(annotation);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitComparisonExpression(FirComparisonExpression comparisonExpression) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        visitExpression(comparisonExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitAssignmentOperatorStatement(FirAssignmentOperatorStatement assignmentOperatorStatement) {
        Intrinsics.checkNotNullParameter(assignmentOperatorStatement, "assignmentOperatorStatement");
        visitStatement(assignmentOperatorStatement);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitIncrementDecrementExpression(FirIncrementDecrementExpression incrementDecrementExpression) {
        Intrinsics.checkNotNullParameter(incrementDecrementExpression, "incrementDecrementExpression");
        visitExpression(incrementDecrementExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall) {
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        visitStatement(augmentedArraySetCall);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitClassReferenceExpression(FirClassReferenceExpression classReferenceExpression) {
        Intrinsics.checkNotNullParameter(classReferenceExpression, "classReferenceExpression");
        visitExpression(classReferenceExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        visitQualifiedAccessExpression(propertyAccessExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall integerLiteralOperatorCall) {
        Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
        visitFunctionCall(integerLiteralOperatorCall);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitImplicitInvokeCall(FirImplicitInvokeCall implicitInvokeCall) {
        Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
        visitFunctionCall(implicitInvokeCall);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitMultiDelegatedConstructorCall(FirMultiDelegatedConstructorCall multiDelegatedConstructorCall) {
        Intrinsics.checkNotNullParameter(multiDelegatedConstructorCall, "multiDelegatedConstructorCall");
        visitDelegatedConstructorCall(multiDelegatedConstructorCall);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitComponentCall(FirComponentCall componentCall) {
        Intrinsics.checkNotNullParameter(componentCall, "componentCall");
        visitFunctionCall(componentCall);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        visitQualifiedAccessExpression(callableReferenceAccess);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        visitQualifiedAccessExpression(thisReceiverExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitSmartCastExpression(FirSmartCastExpression smartCastExpression) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        visitExpression(smartCastExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitSafeCallExpression(FirSafeCallExpression safeCallExpression) {
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        visitExpression(safeCallExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject) {
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        visitExpression(checkedSafeCallSubject);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitWrappedExpression(FirWrappedExpression wrappedExpression) {
        Intrinsics.checkNotNullParameter(wrappedExpression, "wrappedExpression");
        visitExpression(wrappedExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitWrappedArgumentExpression(FirWrappedArgumentExpression wrappedArgumentExpression) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        visitWrappedExpression(wrappedArgumentExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitLambdaArgumentExpression(FirLambdaArgumentExpression lambdaArgumentExpression) {
        Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
        visitWrappedArgumentExpression(lambdaArgumentExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitSpreadArgumentExpression(FirSpreadArgumentExpression spreadArgumentExpression) {
        Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
        visitWrappedArgumentExpression(spreadArgumentExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        visitWrappedArgumentExpression(namedArgumentExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression) {
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        visitExpression(varargArgumentsExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedQualifier(FirResolvedQualifier resolvedQualifier) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        visitExpression(resolvedQualifier);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedReifiedParameterReference(FirResolvedReifiedParameterReference resolvedReifiedParameterReference) {
        Intrinsics.checkNotNullParameter(resolvedReifiedParameterReference, "resolvedReifiedParameterReference");
        visitExpression(resolvedReifiedParameterReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitReturnExpression(FirReturnExpression returnExpression) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        visitJump(returnExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitThrowExpression(FirThrowExpression throwExpression) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        visitExpression(throwExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitVariableAssignment(FirVariableAssignment variableAssignment) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        visitStatement(variableAssignment);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression) {
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        visitExpression(whenSubjectExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression) {
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        visitExpression(desugaredAssignmentValueReferenceExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitWrappedDelegateExpression(FirWrappedDelegateExpression wrappedDelegateExpression) {
        Intrinsics.checkNotNullParameter(wrappedDelegateExpression, "wrappedDelegateExpression");
        visitWrappedExpression(wrappedDelegateExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitEnumEntryDeserializedAccessExpression(FirEnumEntryDeserializedAccessExpression enumEntryDeserializedAccessExpression) {
        Intrinsics.checkNotNullParameter(enumEntryDeserializedAccessExpression, "enumEntryDeserializedAccessExpression");
        visitExpression(enumEntryDeserializedAccessExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitNamedReference(FirNamedReference namedReference) {
        Intrinsics.checkNotNullParameter(namedReference, "namedReference");
        visitReference(namedReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitNamedReferenceWithCandidateBase(FirNamedReferenceWithCandidateBase namedReferenceWithCandidateBase) {
        Intrinsics.checkNotNullParameter(namedReferenceWithCandidateBase, "namedReferenceWithCandidateBase");
        visitNamedReference(namedReferenceWithCandidateBase);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitFromMissingDependenciesNamedReference(FirFromMissingDependenciesNamedReference fromMissingDependenciesNamedReference) {
        Intrinsics.checkNotNullParameter(fromMissingDependenciesNamedReference, "fromMissingDependenciesNamedReference");
        visitNamedReference(fromMissingDependenciesNamedReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitSuperReference(FirSuperReference superReference) {
        Intrinsics.checkNotNullParameter(superReference, "superReference");
        visitReference(superReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitThisReference(FirThisReference thisReference) {
        Intrinsics.checkNotNullParameter(thisReference, "thisReference");
        visitReference(thisReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitControlFlowGraphReference(FirControlFlowGraphReference controlFlowGraphReference) {
        Intrinsics.checkNotNullParameter(controlFlowGraphReference, "controlFlowGraphReference");
        visitReference(controlFlowGraphReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedNamedReference(FirResolvedNamedReference resolvedNamedReference) {
        Intrinsics.checkNotNullParameter(resolvedNamedReference, "resolvedNamedReference");
        visitNamedReference(resolvedNamedReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedErrorReference(FirResolvedErrorReference resolvedErrorReference) {
        Intrinsics.checkNotNullParameter(resolvedErrorReference, "resolvedErrorReference");
        visitResolvedNamedReference(resolvedErrorReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitDelegateFieldReference(FirDelegateFieldReference delegateFieldReference) {
        Intrinsics.checkNotNullParameter(delegateFieldReference, "delegateFieldReference");
        visitResolvedNamedReference(delegateFieldReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitBackingFieldReference(FirBackingFieldReference backingFieldReference) {
        Intrinsics.checkNotNullParameter(backingFieldReference, "backingFieldReference");
        visitResolvedNamedReference(backingFieldReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedCallableReference(FirResolvedCallableReference resolvedCallableReference) {
        Intrinsics.checkNotNullParameter(resolvedCallableReference, "resolvedCallableReference");
        visitResolvedNamedReference(resolvedCallableReference);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        visitTypeRef(resolvedTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitTypeRefWithNullability(FirTypeRefWithNullability typeRefWithNullability) {
        Intrinsics.checkNotNullParameter(typeRefWithNullability, "typeRefWithNullability");
        visitTypeRef(typeRefWithNullability);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitUserTypeRef(FirUserTypeRef userTypeRef) {
        Intrinsics.checkNotNullParameter(userTypeRef, "userTypeRef");
        visitTypeRefWithNullability(userTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitDynamicTypeRef(FirDynamicTypeRef dynamicTypeRef) {
        Intrinsics.checkNotNullParameter(dynamicTypeRef, "dynamicTypeRef");
        visitTypeRefWithNullability(dynamicTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitFunctionTypeRef(FirFunctionTypeRef functionTypeRef) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        visitTypeRefWithNullability(functionTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitIntersectionTypeRef(FirIntersectionTypeRef intersectionTypeRef) {
        Intrinsics.checkNotNullParameter(intersectionTypeRef, "intersectionTypeRef");
        visitTypeRefWithNullability(intersectionTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitImplicitTypeRef(FirImplicitTypeRef implicitTypeRef) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        visitTypeRef(implicitTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitEffectDeclaration(FirEffectDeclaration effectDeclaration) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        visitContractElementDeclaration(effectDeclaration);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitLegacyRawContractDescription(FirLegacyRawContractDescription legacyRawContractDescription) {
        Intrinsics.checkNotNullParameter(legacyRawContractDescription, "legacyRawContractDescription");
        visitContractDescription(legacyRawContractDescription);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitRawContractDescription(FirRawContractDescription rawContractDescription) {
        Intrinsics.checkNotNullParameter(rawContractDescription, "rawContractDescription");
        visitContractDescription(rawContractDescription);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
    public void visitResolvedContractDescription(FirResolvedContractDescription resolvedContractDescription) {
        Intrinsics.checkNotNullParameter(resolvedContractDescription, "resolvedContractDescription");
        visitContractDescription(resolvedContractDescription);
    }
}
