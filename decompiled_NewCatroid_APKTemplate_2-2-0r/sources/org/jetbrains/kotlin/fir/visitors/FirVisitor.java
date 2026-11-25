package org.jetbrains.kotlin.fir.visitors;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirElementWithResolveState;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirPackageDirective;
import org.jetbrains.kotlin.fir.FirTargetElement;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirContractElementDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructedClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.fir.declarations.FirErrorImport;
import org.jetbrains.kotlin.fir.declarations.FirErrorProperty;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirOuterClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement;
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEnumEntryDeserializedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirErrorLoop;
import org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirInaccessibleReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirIncrementDecrementExpression;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirJump;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirLazyBlock;
import org.jetbrains.kotlin.fir.expressions.FirLazyExpression;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirLoopJump;
import org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedErrorAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirResolvedReifiedParameterReference;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedDelegateExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedExpression;
import org.jetbrains.kotlin.fir.references.FirBackingFieldReference;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.references.FirDelegateFieldReference;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirFromMissingDependenciesNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReferenceWithCandidateBase;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.types.FirDynamicTypeRef;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirIntersectionTypeRef;
import org.jetbrains.kotlin.fir.types.FirPlaceholderProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: FirVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000æ\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00002\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\tJ\u001d\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\rJ\u001d\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0011J\u001d\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0015J\u001d\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010!J\u001d\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010%J\u001d\u0010&\u001a\u00028\u00002\u0006\u0010'\u001a\u00020(2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010)J\u001d\u0010*\u001a\u00028\u00002\u0006\u0010+\u001a\u00020,2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010-J\u001d\u0010.\u001a\u00028\u00002\u0006\u0010/\u001a\u0002002\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00101J\u001d\u00102\u001a\u00028\u00002\u0006\u00103\u001a\u0002042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00105J\u001d\u00106\u001a\u00028\u00002\u0006\u00107\u001a\u0002082\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00109J\u001d\u0010:\u001a\u00028\u00002\u0006\u0010;\u001a\u00020<2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010=J\u001d\u0010>\u001a\u00028\u00002\u0006\u0010?\u001a\u00020@2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010AJ\u001d\u0010B\u001a\u00028\u00002\u0006\u0010C\u001a\u00020D2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010EJ\u001d\u0010F\u001a\u00028\u00002\u0006\u0010G\u001a\u00020H2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010IJ\u001d\u0010J\u001a\u00028\u00002\u0006\u0010K\u001a\u00020L2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010MJ\u001d\u0010N\u001a\u00028\u00002\u0006\u0010O\u001a\u00020P2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010QJ\u001d\u0010R\u001a\u00028\u00002\u0006\u0010S\u001a\u00020T2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010UJ\u001d\u0010V\u001a\u00028\u00002\u0006\u0010W\u001a\u00020X2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010YJ\u001d\u0010Z\u001a\u00028\u00002\u0006\u0010[\u001a\u00020\\2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010]J\u001d\u0010^\u001a\u00028\u00002\u0006\u0010_\u001a\u00020`2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010aJ\u001d\u0010b\u001a\u00028\u00002\u0006\u0010c\u001a\u00020d2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010eJ\u001d\u0010f\u001a\u00028\u00002\u0006\u0010g\u001a\u00020h2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010iJ\u001d\u0010j\u001a\u00028\u00002\u0006\u0010k\u001a\u00020l2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010mJ\u001d\u0010n\u001a\u00028\u00002\u0006\u0010o\u001a\u00020p2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010qJ\u001d\u0010r\u001a\u00028\u00002\u0006\u0010s\u001a\u00020t2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010uJ\u001d\u0010v\u001a\u00028\u00002\u0006\u0010w\u001a\u00020x2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010yJ\u001d\u0010z\u001a\u00028\u00002\u0006\u0010{\u001a\u00020|2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010}J,\u0010~\u001a\u00028\u0000\"\u0004\b\u0002\u0010\u007f2\u000e\u0010\u0080\u0001\u001a\t\u0012\u0004\u0012\u0002H\u007f0\u0081\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0082\u0001J!\u0010\u0083\u0001\u001a\u00028\u00002\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0086\u0001J!\u0010\u0087\u0001\u001a\u00028\u00002\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008a\u0001J!\u0010\u008b\u0001\u001a\u00028\u00002\b\u0010\u008c\u0001\u001a\u00030\u008d\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008e\u0001J!\u0010\u008f\u0001\u001a\u00028\u00002\b\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0092\u0001J!\u0010\u0093\u0001\u001a\u00028\u00002\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0096\u0001J!\u0010\u0097\u0001\u001a\u00028\u00002\b\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009a\u0001J!\u0010\u009b\u0001\u001a\u00028\u00002\b\u0010\u009c\u0001\u001a\u00030\u009d\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009e\u0001J!\u0010\u009f\u0001\u001a\u00028\u00002\b\u0010 \u0001\u001a\u00030¡\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¢\u0001J!\u0010£\u0001\u001a\u00028\u00002\b\u0010¤\u0001\u001a\u00030¥\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¦\u0001J!\u0010§\u0001\u001a\u00028\u00002\b\u0010¨\u0001\u001a\u00030©\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ª\u0001J!\u0010«\u0001\u001a\u00028\u00002\b\u0010¬\u0001\u001a\u00030\u00ad\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010®\u0001J!\u0010¯\u0001\u001a\u00028\u00002\b\u0010°\u0001\u001a\u00030±\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010²\u0001J!\u0010³\u0001\u001a\u00028\u00002\b\u0010´\u0001\u001a\u00030µ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¶\u0001J!\u0010·\u0001\u001a\u00028\u00002\b\u0010¸\u0001\u001a\u00030¹\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010º\u0001J!\u0010»\u0001\u001a\u00028\u00002\b\u0010¼\u0001\u001a\u00030½\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¾\u0001J!\u0010¿\u0001\u001a\u00028\u00002\b\u0010À\u0001\u001a\u00030Á\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Â\u0001J!\u0010Ã\u0001\u001a\u00028\u00002\b\u0010Ä\u0001\u001a\u00030Å\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Æ\u0001J!\u0010Ç\u0001\u001a\u00028\u00002\b\u0010È\u0001\u001a\u00030É\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ê\u0001J!\u0010Ë\u0001\u001a\u00028\u00002\b\u0010Ì\u0001\u001a\u00030Í\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Î\u0001J!\u0010Ï\u0001\u001a\u00028\u00002\b\u0010Ð\u0001\u001a\u00030Ñ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ò\u0001J!\u0010Ó\u0001\u001a\u00028\u00002\b\u0010Ô\u0001\u001a\u00030Õ\u00012\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0003\u0010Ö\u0001J!\u0010×\u0001\u001a\u00028\u00002\b\u0010Ø\u0001\u001a\u00030Ù\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ú\u0001J!\u0010Û\u0001\u001a\u00028\u00002\b\u0010Ü\u0001\u001a\u00030Ý\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Þ\u0001J!\u0010ß\u0001\u001a\u00028\u00002\b\u0010à\u0001\u001a\u00030á\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010â\u0001J!\u0010ã\u0001\u001a\u00028\u00002\b\u0010ä\u0001\u001a\u00030å\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010æ\u0001J!\u0010ç\u0001\u001a\u00028\u00002\b\u0010è\u0001\u001a\u00030é\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ê\u0001J!\u0010ë\u0001\u001a\u00028\u00002\b\u0010ì\u0001\u001a\u00030í\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010î\u0001J!\u0010ï\u0001\u001a\u00028\u00002\b\u0010ð\u0001\u001a\u00030ñ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ò\u0001J!\u0010ó\u0001\u001a\u00028\u00002\b\u0010ô\u0001\u001a\u00030õ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ö\u0001J!\u0010÷\u0001\u001a\u00028\u00002\b\u0010ø\u0001\u001a\u00030ù\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ú\u0001J!\u0010û\u0001\u001a\u00028\u00002\b\u0010ü\u0001\u001a\u00030ý\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010þ\u0001J!\u0010ÿ\u0001\u001a\u00028\u00002\b\u0010\u0080\u0002\u001a\u00030\u0081\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0082\u0002J!\u0010\u0083\u0002\u001a\u00028\u00002\b\u0010\u0084\u0002\u001a\u00030\u0085\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0086\u0002J!\u0010\u0087\u0002\u001a\u00028\u00002\b\u0010\u0088\u0002\u001a\u00030\u0089\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008a\u0002J!\u0010\u008b\u0002\u001a\u00028\u00002\b\u0010\u008c\u0002\u001a\u00030\u008d\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008e\u0002J!\u0010\u008f\u0002\u001a\u00028\u00002\b\u0010\u0090\u0002\u001a\u00030\u0091\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0092\u0002J!\u0010\u0093\u0002\u001a\u00028\u00002\b\u0010\u0094\u0002\u001a\u00030\u0095\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0096\u0002J!\u0010\u0097\u0002\u001a\u00028\u00002\b\u0010\u0098\u0002\u001a\u00030\u0099\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009a\u0002J!\u0010\u009b\u0002\u001a\u00028\u00002\b\u0010\u009c\u0002\u001a\u00030\u009d\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009e\u0002J!\u0010\u009f\u0002\u001a\u00028\u00002\b\u0010 \u0002\u001a\u00030¡\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¢\u0002J!\u0010£\u0002\u001a\u00028\u00002\b\u0010¤\u0002\u001a\u00030¥\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¦\u0002J!\u0010§\u0002\u001a\u00028\u00002\b\u0010¨\u0002\u001a\u00030©\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ª\u0002J!\u0010«\u0002\u001a\u00028\u00002\b\u0010¬\u0002\u001a\u00030\u00ad\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010®\u0002J!\u0010¯\u0002\u001a\u00028\u00002\b\u0010°\u0002\u001a\u00030±\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010²\u0002J!\u0010³\u0002\u001a\u00028\u00002\b\u0010´\u0002\u001a\u00030µ\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¶\u0002J!\u0010·\u0002\u001a\u00028\u00002\b\u0010¸\u0002\u001a\u00030¹\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010º\u0002J!\u0010»\u0002\u001a\u00028\u00002\b\u0010¼\u0002\u001a\u00030½\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¾\u0002J!\u0010¿\u0002\u001a\u00028\u00002\b\u0010À\u0002\u001a\u00030Á\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Â\u0002J!\u0010Ã\u0002\u001a\u00028\u00002\b\u0010Ä\u0002\u001a\u00030Å\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Æ\u0002J!\u0010Ç\u0002\u001a\u00028\u00002\b\u0010È\u0002\u001a\u00030É\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ê\u0002J!\u0010Ë\u0002\u001a\u00028\u00002\b\u0010Ì\u0002\u001a\u00030Í\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Î\u0002J!\u0010Ï\u0002\u001a\u00028\u00002\b\u0010Ð\u0002\u001a\u00030Ñ\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ò\u0002J4\u0010Ó\u0002\u001a\u00028\u0000\"\n\b\u0002\u0010Ô\u0002*\u00030Õ\u00022\u000f\u0010Ö\u0002\u001a\n\u0012\u0005\u0012\u0003HÔ\u00020×\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ø\u0002J!\u0010Ù\u0002\u001a\u00028\u00002\b\u0010Ú\u0002\u001a\u00030Û\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ü\u0002J!\u0010Ý\u0002\u001a\u00028\u00002\b\u0010Þ\u0002\u001a\u00030ß\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010à\u0002J!\u0010á\u0002\u001a\u00028\u00002\b\u0010â\u0002\u001a\u00030ã\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ä\u0002J!\u0010å\u0002\u001a\u00028\u00002\b\u0010æ\u0002\u001a\u00030ç\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010è\u0002J!\u0010é\u0002\u001a\u00028\u00002\b\u0010ê\u0002\u001a\u00030ë\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ì\u0002J!\u0010í\u0002\u001a\u00028\u00002\b\u0010î\u0002\u001a\u00030ï\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ð\u0002J!\u0010ñ\u0002\u001a\u00028\u00002\b\u0010ò\u0002\u001a\u00030ó\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ô\u0002J!\u0010õ\u0002\u001a\u00028\u00002\b\u0010ö\u0002\u001a\u00030÷\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ø\u0002J!\u0010ù\u0002\u001a\u00028\u00002\b\u0010ú\u0002\u001a\u00030û\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ü\u0002J!\u0010ý\u0002\u001a\u00028\u00002\b\u0010þ\u0002\u001a\u00030ÿ\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0080\u0003J!\u0010\u0081\u0003\u001a\u00028\u00002\b\u0010\u0082\u0003\u001a\u00030\u0083\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0084\u0003J!\u0010\u0085\u0003\u001a\u00028\u00002\b\u0010\u0086\u0003\u001a\u00030\u0087\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0088\u0003J!\u0010\u0089\u0003\u001a\u00028\u00002\b\u0010\u008a\u0003\u001a\u00030\u008b\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008c\u0003J!\u0010\u008d\u0003\u001a\u00028\u00002\b\u0010\u008e\u0003\u001a\u00030\u008f\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0090\u0003J!\u0010\u0091\u0003\u001a\u00028\u00002\b\u0010\u0092\u0003\u001a\u00030\u0093\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0094\u0003J!\u0010\u0095\u0003\u001a\u00028\u00002\b\u0010\u0096\u0003\u001a\u00030\u0097\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0098\u0003J!\u0010\u0099\u0003\u001a\u00028\u00002\b\u0010\u009a\u0003\u001a\u00030\u009b\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009c\u0003J!\u0010\u009d\u0003\u001a\u00028\u00002\b\u0010\u009e\u0003\u001a\u00030\u009f\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010 \u0003J!\u0010¡\u0003\u001a\u00028\u00002\b\u0010¢\u0003\u001a\u00030£\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¤\u0003J!\u0010¥\u0003\u001a\u00028\u00002\b\u0010¦\u0003\u001a\u00030§\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¨\u0003J!\u0010©\u0003\u001a\u00028\u00002\b\u0010ª\u0003\u001a\u00030«\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¬\u0003J!\u0010\u00ad\u0003\u001a\u00028\u00002\b\u0010®\u0003\u001a\u00030¯\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010°\u0003J!\u0010±\u0003\u001a\u00028\u00002\b\u0010²\u0003\u001a\u00030³\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010´\u0003J!\u0010µ\u0003\u001a\u00028\u00002\b\u0010¶\u0003\u001a\u00030·\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¸\u0003J!\u0010¹\u0003\u001a\u00028\u00002\b\u0010º\u0003\u001a\u00030»\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¼\u0003J!\u0010½\u0003\u001a\u00028\u00002\b\u0010¾\u0003\u001a\u00030¿\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010À\u0003J!\u0010Á\u0003\u001a\u00028\u00002\b\u0010Â\u0003\u001a\u00030Ã\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ä\u0003J!\u0010Å\u0003\u001a\u00028\u00002\b\u0010Æ\u0003\u001a\u00030Ç\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010È\u0003J!\u0010É\u0003\u001a\u00028\u00002\b\u0010Ê\u0003\u001a\u00030Ë\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ì\u0003J!\u0010Í\u0003\u001a\u00028\u00002\b\u0010Î\u0003\u001a\u00030Ï\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ð\u0003J!\u0010Ñ\u0003\u001a\u00028\u00002\b\u0010Ò\u0003\u001a\u00030Ó\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ô\u0003J!\u0010Õ\u0003\u001a\u00028\u00002\b\u0010Ö\u0003\u001a\u00030×\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ø\u0003J!\u0010Ù\u0003\u001a\u00028\u00002\b\u0010Ú\u0003\u001a\u00030Û\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ü\u0003J!\u0010Ý\u0003\u001a\u00028\u00002\b\u0010Þ\u0003\u001a\u00030ß\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010à\u0003J!\u0010á\u0003\u001a\u00028\u00002\b\u0010â\u0003\u001a\u00030ã\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ä\u0003J!\u0010å\u0003\u001a\u00028\u00002\b\u0010æ\u0003\u001a\u00030ç\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010è\u0003J!\u0010é\u0003\u001a\u00028\u00002\b\u0010ê\u0003\u001a\u00030ë\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ì\u0003J!\u0010í\u0003\u001a\u00028\u00002\b\u0010î\u0003\u001a\u00030ï\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ð\u0003J!\u0010ñ\u0003\u001a\u00028\u00002\b\u0010ò\u0003\u001a\u00030ó\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ô\u0003J!\u0010õ\u0003\u001a\u00028\u00002\b\u0010ö\u0003\u001a\u00030÷\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ø\u0003J!\u0010ù\u0003\u001a\u00028\u00002\b\u0010ú\u0003\u001a\u00030û\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ü\u0003J!\u0010ý\u0003\u001a\u00028\u00002\b\u0010þ\u0003\u001a\u00030ÿ\u00032\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0080\u0004J!\u0010\u0081\u0004\u001a\u00028\u00002\b\u0010\u0082\u0004\u001a\u00030\u0083\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0084\u0004J!\u0010\u0085\u0004\u001a\u00028\u00002\b\u0010\u0086\u0004\u001a\u00030\u0087\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0088\u0004J!\u0010\u0089\u0004\u001a\u00028\u00002\b\u0010\u008a\u0004\u001a\u00030Õ\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008b\u0004J!\u0010\u008c\u0004\u001a\u00028\u00002\b\u0010\u008d\u0004\u001a\u00030\u008e\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008f\u0004J!\u0010\u0090\u0004\u001a\u00028\u00002\b\u0010\u0091\u0004\u001a\u00030\u0092\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0093\u0004J!\u0010\u0094\u0004\u001a\u00028\u00002\b\u0010\u0095\u0004\u001a\u00030\u0096\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0097\u0004J!\u0010\u0098\u0004\u001a\u00028\u00002\b\u0010\u0099\u0004\u001a\u00030\u009a\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009b\u0004J!\u0010\u009c\u0004\u001a\u00028\u00002\b\u0010\u009d\u0004\u001a\u00030\u009e\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009f\u0004J!\u0010 \u0004\u001a\u00028\u00002\b\u0010¡\u0004\u001a\u00030¢\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010£\u0004J!\u0010¤\u0004\u001a\u00028\u00002\b\u0010¥\u0004\u001a\u00030¦\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010§\u0004J!\u0010¨\u0004\u001a\u00028\u00002\b\u0010©\u0004\u001a\u00030ª\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010«\u0004J!\u0010¬\u0004\u001a\u00028\u00002\b\u0010\u00ad\u0004\u001a\u00030®\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¯\u0004J!\u0010°\u0004\u001a\u00028\u00002\b\u0010±\u0004\u001a\u00030²\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010³\u0004J!\u0010´\u0004\u001a\u00028\u00002\b\u0010µ\u0004\u001a\u00030¶\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010·\u0004J!\u0010¸\u0004\u001a\u00028\u00002\b\u0010¹\u0004\u001a\u00030º\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010»\u0004J!\u0010¼\u0004\u001a\u00028\u00002\b\u0010½\u0004\u001a\u00030¾\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¿\u0004J!\u0010À\u0004\u001a\u00028\u00002\b\u0010Á\u0004\u001a\u00030Â\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ã\u0004J!\u0010Ä\u0004\u001a\u00028\u00002\b\u0010Å\u0004\u001a\u00030Æ\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ç\u0004J!\u0010È\u0004\u001a\u00028\u00002\b\u0010É\u0004\u001a\u00030Ê\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ë\u0004J!\u0010Ì\u0004\u001a\u00028\u00002\b\u0010Í\u0004\u001a\u00030Î\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ï\u0004J!\u0010Ð\u0004\u001a\u00028\u00002\b\u0010Ñ\u0004\u001a\u00030Ò\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ó\u0004J!\u0010Ô\u0004\u001a\u00028\u00002\b\u0010Õ\u0004\u001a\u00030Ö\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010×\u0004J!\u0010Ø\u0004\u001a\u00028\u00002\b\u0010Ù\u0004\u001a\u00030Ú\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Û\u0004J!\u0010Ü\u0004\u001a\u00028\u00002\b\u0010Ý\u0004\u001a\u00030Þ\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ß\u0004J!\u0010à\u0004\u001a\u00028\u00002\b\u0010á\u0004\u001a\u00030â\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ã\u0004J!\u0010ä\u0004\u001a\u00028\u00002\b\u0010å\u0004\u001a\u00030æ\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ç\u0004J!\u0010è\u0004\u001a\u00028\u00002\b\u0010é\u0004\u001a\u00030ê\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ë\u0004J!\u0010ì\u0004\u001a\u00028\u00002\b\u0010í\u0004\u001a\u00030î\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ï\u0004J!\u0010ð\u0004\u001a\u00028\u00002\b\u0010ñ\u0004\u001a\u00030ò\u00042\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ó\u0004¨\u0006ô\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "R", "D", "", "()V", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnnotationArgumentMapping", "annotationArgumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnnotationContainer", "annotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "(Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousInitializer", "anonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitArgumentList", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;Ljava/lang/Object;)Ljava/lang/Object;", "visitArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "(Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;Ljava/lang/Object;)Ljava/lang/Object;", "visitAssignmentOperatorStatement", "assignmentOperatorStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;Ljava/lang/Object;)Ljava/lang/Object;", "visitAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitBackingField", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;Ljava/lang/Object;)Ljava/lang/Object;", "visitBackingFieldReference", "backingFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirBackingFieldReference;", "(Lorg/jetbrains/kotlin/fir/references/FirBackingFieldReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;Ljava/lang/Object;)Ljava/lang/Object;", "visitBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitCall", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitCallableDeclaration", "callableDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;Ljava/lang/Object;)Ljava/lang/Object;", "visitCatch", PsiKeyword.CATCH, "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCatch;Ljava/lang/Object;)Ljava/lang/Object;", "visitCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;Ljava/lang/Object;)Ljava/lang/Object;", "visitClass", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Ljava/lang/Object;)Ljava/lang/Object;", "visitClassLikeDeclaration", "classLikeDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitClassReferenceExpression", "classReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;Ljava/lang/Object;)Ljava/lang/Object;", "visitComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitComponentCall", "componentCall", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstructedClassTypeParameterRef", "constructedClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Ljava/lang/Object;)Ljava/lang/Object;", "visitContextReceiver", "contextReceiver", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "(Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;Ljava/lang/Object;)Ljava/lang/Object;", "visitContextReceiverArgumentListOwner", "contextReceiverArgumentListOwner", "Lorg/jetbrains/kotlin/fir/expressions/FirContextReceiverArgumentListOwner;", "(Lorg/jetbrains/kotlin/fir/expressions/FirContextReceiverArgumentListOwner;Ljava/lang/Object;)Ljava/lang/Object;", "visitContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitContractDescription", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;Ljava/lang/Object;)Ljava/lang/Object;", "visitContractDescriptionOwner", "contractDescriptionOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "(Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;Ljava/lang/Object;)Ljava/lang/Object;", "visitContractElementDeclaration", "contractElementDeclaration", "Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitControlFlowGraphOwner", "controlFlowGraphOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "(Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;Ljava/lang/Object;)Ljava/lang/Object;", "visitControlFlowGraphReference", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitDanglingModifierList", "danglingModifierList", "Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;Ljava/lang/Object;)Ljava/lang/Object;", "visitDeclaration", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitDeclarationStatus", "declarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Ljava/lang/Object;)Ljava/lang/Object;", "visitDelegateFieldReference", "delegateFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;", "(Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitDiagnosticHolder", "diagnosticHolder", "Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "(Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;Ljava/lang/Object;)Ljava/lang/Object;", "visitDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;Ljava/lang/Object;)Ljava/lang/Object;", "visitDynamicTypeRef", "dynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitEffectDeclaration", "effectDeclaration", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "(Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Ljava/lang/Object;", "visitElementWithResolveState", "elementWithResolveState", "Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;", "(Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;Ljava/lang/Object;)Ljava/lang/Object;", "visitElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "(Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnumEntryDeserializedAccessExpression", "enumEntryDeserializedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorExpression", "errorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorFunction", "errorFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorImport", "errorImport", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;", "(Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorLoop", "errorLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorNamedReference", "errorNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirErrorNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirErrorNamedReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorProperty", "errorProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorResolvedQualifier", "errorResolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorTypeRef", "errorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "(Lorg/jetbrains/kotlin/fir/declarations/FirField;Ljava/lang/Object;)Ljava/lang/Object;", "visitFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/lang/Object;)Ljava/lang/Object;", "visitFileAnnotationsContainer", "fileAnnotationsContainer", "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "(Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;Ljava/lang/Object;)Ljava/lang/Object;", "visitFromMissingDependenciesNamedReference", "fromMissingDependenciesNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirFromMissingDependenciesNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirFromMissingDependenciesNamedReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionTypeParameter", "functionTypeParameter", "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "(Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionTypeRef", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitImplicitInvokeCall", "implicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitImplicitTypeRef", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitImport", PsiKeyword.IMPORT, "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "(Lorg/jetbrains/kotlin/fir/declarations/FirImport;Ljava/lang/Object;)Ljava/lang/Object;", "visitInaccessibleReceiverExpression", "inaccessibleReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitIncrementDecrementExpression", "incrementDecrementExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitIntegerLiteralOperatorCall", "integerLiteralOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitIntersectionTypeRef", "intersectionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitJump", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirTargetElement;", "jump", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "(Lorg/jetbrains/kotlin/fir/expressions/FirJump;Ljava/lang/Object;)Ljava/lang/Object;", "visitLabel", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "(Lorg/jetbrains/kotlin/fir/FirLabel;Ljava/lang/Object;)Ljava/lang/Object;", "visitLambdaArgumentExpression", "lambdaArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitLazyBlock", "lazyBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;Ljava/lang/Object;)Ljava/lang/Object;", "visitLazyExpression", "lazyExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitLegacyRawContractDescription", "legacyRawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoop", "loop", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLoop;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoopJump", "loopJump", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;Ljava/lang/Object;)Ljava/lang/Object;", "visitMemberDeclaration", "memberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitMultiDelegatedConstructorCall", "multiDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitNamedReference", "namedReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitNamedReferenceWithCandidateBase", "namedReferenceWithCandidateBase", "Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;Ljava/lang/Object;)Ljava/lang/Object;", "visitOuterClassTypeParameterRef", "outerClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitPackageDirective", "packageDirective", "Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "(Lorg/jetbrains/kotlin/fir/FirPackageDirective;Ljava/lang/Object;)Ljava/lang/Object;", "visitPlaceholderProjection", "placeholderProjection", "Lorg/jetbrains/kotlin/fir/types/FirPlaceholderProjection;", "(Lorg/jetbrains/kotlin/fir/types/FirPlaceholderProjection;Ljava/lang/Object;)Ljava/lang/Object;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Ljava/lang/Object;)Ljava/lang/Object;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;Ljava/lang/Object;)Ljava/lang/Object;", "visitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitQualifiedErrorAccessExpression", "qualifiedErrorAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedErrorAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedErrorAccessExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitRawContractDescription", "rawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;Ljava/lang/Object;)Ljava/lang/Object;", "visitReceiverParameter", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;Ljava/lang/Object;)Ljava/lang/Object;", "visitReference", "reference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "(Lorg/jetbrains/kotlin/fir/references/FirReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvable", "resolvable", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedCallableReference", "resolvedCallableReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedContractDescription", "resolvedContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedDeclarationStatus", "resolvedDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedErrorReference", "resolvedErrorReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedImport", "resolvedImport", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedNamedReference", "resolvedNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedQualifier", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedReifiedParameterReference", "resolvedReifiedParameterReference", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedReifiedParameterReference;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedReifiedParameterReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "(Lorg/jetbrains/kotlin/fir/declarations/FirScript;Ljava/lang/Object;)Ljava/lang/Object;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Ljava/lang/Object;)Ljava/lang/Object;", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitSpreadArgumentExpression", "spreadArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitStarProjection", "starProjection", "Lorg/jetbrains/kotlin/fir/types/FirStarProjection;", "(Lorg/jetbrains/kotlin/fir/types/FirStarProjection;Ljava/lang/Object;)Ljava/lang/Object;", "visitStatement", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Ljava/lang/Object;)Ljava/lang/Object;", "visitStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitSuperReference", "superReference", "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", "(Lorg/jetbrains/kotlin/fir/references/FirSuperReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitTargetElement", "targetElement", "(Lorg/jetbrains/kotlin/fir/FirTargetElement;Ljava/lang/Object;)Ljava/lang/Object;", "visitThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitThisReference", "thisReference", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "(Lorg/jetbrains/kotlin/fir/references/FirThisReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeParameterRef", "typeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeParameterRefsOwner", "typeParameterRefsOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeParametersOwner", "typeParametersOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeProjection", "typeProjection", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeProjectionWithVariance", "typeProjectionWithVariance", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeRefWithNullability", "typeRefWithNullability", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;Ljava/lang/Object;)Ljava/lang/Object;", "visitUserTypeRef", "userTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;Ljava/lang/Object;)Ljava/lang/Object;", "visitValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;Ljava/lang/Object;)Ljava/lang/Object;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitVariable", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "(Lorg/jetbrains/kotlin/fir/declarations/FirVariable;Ljava/lang/Object;)Ljava/lang/Object;", "visitVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenBranch", "whenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;Ljava/lang/Object;)Ljava/lang/Object;", "visitWrappedArgumentExpression", "wrappedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitWrappedDelegateExpression", "wrappedDelegateExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitWrappedExpression", "wrappedExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedExpression;Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirVisitor<R, D> {
    public abstract R visitElement(FirElement element, D data);

    public R visitAnnotationContainer(FirAnnotationContainer annotationContainer, D data) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        return visitElement(annotationContainer, data);
    }

    public R visitTypeRef(FirTypeRef typeRef, D data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        return visitElement(typeRef, data);
    }

    public R visitReference(FirReference reference, D data) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        return visitElement(reference, data);
    }

    public R visitLabel(FirLabel label, D data) {
        Intrinsics.checkNotNullParameter(label, "label");
        return visitElement(label, data);
    }

    public R visitResolvable(FirResolvable resolvable, D data) {
        Intrinsics.checkNotNullParameter(resolvable, "resolvable");
        return visitElement(resolvable, data);
    }

    public R visitTargetElement(FirTargetElement targetElement, D data) {
        Intrinsics.checkNotNullParameter(targetElement, "targetElement");
        return visitElement(targetElement, data);
    }

    public R visitDeclarationStatus(FirDeclarationStatus declarationStatus, D data) {
        Intrinsics.checkNotNullParameter(declarationStatus, "declarationStatus");
        return visitElement(declarationStatus, data);
    }

    public R visitResolvedDeclarationStatus(FirResolvedDeclarationStatus resolvedDeclarationStatus, D data) {
        Intrinsics.checkNotNullParameter(resolvedDeclarationStatus, "resolvedDeclarationStatus");
        return visitElement(resolvedDeclarationStatus, data);
    }

    public R visitControlFlowGraphOwner(FirControlFlowGraphOwner controlFlowGraphOwner, D data) {
        Intrinsics.checkNotNullParameter(controlFlowGraphOwner, "controlFlowGraphOwner");
        return visitElement(controlFlowGraphOwner, data);
    }

    public R visitStatement(FirStatement statement, D data) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        return visitElement(statement, data);
    }

    public R visitExpression(FirExpression expression, D data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return visitElement(expression, data);
    }

    public R visitLazyExpression(FirLazyExpression lazyExpression, D data) {
        Intrinsics.checkNotNullParameter(lazyExpression, "lazyExpression");
        return visitElement(lazyExpression, data);
    }

    public R visitContextReceiver(FirContextReceiver contextReceiver, D data) {
        Intrinsics.checkNotNullParameter(contextReceiver, "contextReceiver");
        return visitElement(contextReceiver, data);
    }

    public R visitElementWithResolveState(FirElementWithResolveState elementWithResolveState, D data) {
        Intrinsics.checkNotNullParameter(elementWithResolveState, "elementWithResolveState");
        return visitElement(elementWithResolveState, data);
    }

    public R visitFileAnnotationsContainer(FirFileAnnotationsContainer fileAnnotationsContainer, D data) {
        Intrinsics.checkNotNullParameter(fileAnnotationsContainer, "fileAnnotationsContainer");
        return visitElement(fileAnnotationsContainer, data);
    }

    public R visitDeclaration(FirDeclaration declaration, D data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return visitElement(declaration, data);
    }

    public R visitTypeParameterRefsOwner(FirTypeParameterRefsOwner typeParameterRefsOwner, D data) {
        Intrinsics.checkNotNullParameter(typeParameterRefsOwner, "typeParameterRefsOwner");
        return visitElement(typeParameterRefsOwner, data);
    }

    public R visitTypeParametersOwner(FirTypeParametersOwner typeParametersOwner, D data) {
        Intrinsics.checkNotNullParameter(typeParametersOwner, "typeParametersOwner");
        return visitElement(typeParametersOwner, data);
    }

    public R visitMemberDeclaration(FirMemberDeclaration memberDeclaration, D data) {
        Intrinsics.checkNotNullParameter(memberDeclaration, "memberDeclaration");
        return visitElement(memberDeclaration, data);
    }

    public R visitAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, D data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        return visitElement(anonymousInitializer, data);
    }

    public R visitCallableDeclaration(FirCallableDeclaration callableDeclaration, D data) {
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        return visitElement(callableDeclaration, data);
    }

    public R visitTypeParameterRef(FirTypeParameterRef typeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(typeParameterRef, "typeParameterRef");
        return visitElement(typeParameterRef, data);
    }

    public R visitTypeParameter(FirTypeParameter typeParameter, D data) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return visitElement(typeParameter, data);
    }

    public R visitConstructedClassTypeParameterRef(FirConstructedClassTypeParameterRef constructedClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(constructedClassTypeParameterRef, "constructedClassTypeParameterRef");
        return visitElement(constructedClassTypeParameterRef, data);
    }

    public R visitOuterClassTypeParameterRef(FirOuterClassTypeParameterRef outerClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(outerClassTypeParameterRef, "outerClassTypeParameterRef");
        return visitElement(outerClassTypeParameterRef, data);
    }

    public R visitVariable(FirVariable variable, D data) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return visitElement(variable, data);
    }

    public R visitValueParameter(FirValueParameter valueParameter, D data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return visitElement(valueParameter, data);
    }

    public R visitReceiverParameter(FirReceiverParameter receiverParameter, D data) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        return visitElement(receiverParameter, data);
    }

    public R visitProperty(FirProperty property, D data) {
        Intrinsics.checkNotNullParameter(property, "property");
        return visitElement(property, data);
    }

    public R visitField(FirField field, D data) {
        Intrinsics.checkNotNullParameter(field, "field");
        return visitElement(field, data);
    }

    public R visitEnumEntry(FirEnumEntry enumEntry, D data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return visitElement(enumEntry, data);
    }

    public R visitFunctionTypeParameter(FirFunctionTypeParameter functionTypeParameter, D data) {
        Intrinsics.checkNotNullParameter(functionTypeParameter, "functionTypeParameter");
        return visitElement(functionTypeParameter, data);
    }

    public R visitClassLikeDeclaration(FirClassLikeDeclaration classLikeDeclaration, D data) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        return visitElement(classLikeDeclaration, data);
    }

    public R visitClass(FirClass klass, D data) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return visitElement(klass, data);
    }

    public R visitRegularClass(FirRegularClass regularClass, D data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        return visitElement(regularClass, data);
    }

    public R visitTypeAlias(FirTypeAlias typeAlias, D data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        return visitElement(typeAlias, data);
    }

    public R visitFunction(FirFunction function, D data) {
        Intrinsics.checkNotNullParameter(function, "function");
        return visitElement(function, data);
    }

    public R visitContractDescriptionOwner(FirContractDescriptionOwner contractDescriptionOwner, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionOwner, "contractDescriptionOwner");
        return visitElement(contractDescriptionOwner, data);
    }

    public R visitSimpleFunction(FirSimpleFunction simpleFunction, D data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        return visitElement(simpleFunction, data);
    }

    public R visitPropertyAccessor(FirPropertyAccessor propertyAccessor, D data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        return visitElement(propertyAccessor, data);
    }

    public R visitBackingField(FirBackingField backingField, D data) {
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        return visitElement(backingField, data);
    }

    public R visitConstructor(FirConstructor constructor, D data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return visitElement(constructor, data);
    }

    public R visitFile(FirFile file, D data) {
        Intrinsics.checkNotNullParameter(file, "file");
        return visitElement(file, data);
    }

    public R visitScript(FirScript script, D data) {
        Intrinsics.checkNotNullParameter(script, "script");
        return visitElement(script, data);
    }

    public R visitCodeFragment(FirCodeFragment codeFragment, D data) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        return visitElement(codeFragment, data);
    }

    public R visitPackageDirective(FirPackageDirective packageDirective, D data) {
        Intrinsics.checkNotNullParameter(packageDirective, "packageDirective");
        return visitElement(packageDirective, data);
    }

    public R visitAnonymousFunction(FirAnonymousFunction anonymousFunction, D data) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        return visitElement(anonymousFunction, data);
    }

    public R visitAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, D data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        return visitElement(anonymousFunctionExpression, data);
    }

    public R visitAnonymousObject(FirAnonymousObject anonymousObject, D data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        return visitElement(anonymousObject, data);
    }

    public R visitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, D data) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        return visitElement(anonymousObjectExpression, data);
    }

    public R visitDiagnosticHolder(FirDiagnosticHolder diagnosticHolder, D data) {
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        return visitElement(diagnosticHolder, data);
    }

    public R visitImport(FirImport firImport, D data) {
        Intrinsics.checkNotNullParameter(firImport, "import");
        return visitElement(firImport, data);
    }

    public R visitResolvedImport(FirResolvedImport resolvedImport, D data) {
        Intrinsics.checkNotNullParameter(resolvedImport, "resolvedImport");
        return visitElement(resolvedImport, data);
    }

    public R visitErrorImport(FirErrorImport errorImport, D data) {
        Intrinsics.checkNotNullParameter(errorImport, "errorImport");
        return visitElement(errorImport, data);
    }

    public R visitLoop(FirLoop loop, D data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return visitElement(loop, data);
    }

    public R visitErrorLoop(FirErrorLoop errorLoop, D data) {
        Intrinsics.checkNotNullParameter(errorLoop, "errorLoop");
        return visitElement(errorLoop, data);
    }

    public R visitDoWhileLoop(FirDoWhileLoop doWhileLoop, D data) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        return visitElement(doWhileLoop, data);
    }

    public R visitWhileLoop(FirWhileLoop whileLoop, D data) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        return visitElement(whileLoop, data);
    }

    public R visitBlock(FirBlock block, D data) {
        Intrinsics.checkNotNullParameter(block, "block");
        return visitElement(block, data);
    }

    public R visitLazyBlock(FirLazyBlock lazyBlock, D data) {
        Intrinsics.checkNotNullParameter(lazyBlock, "lazyBlock");
        return visitElement(lazyBlock, data);
    }

    public R visitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, D data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        return visitElement(binaryLogicExpression, data);
    }

    public <E extends FirTargetElement> R visitJump(FirJump<E> jump, D data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return visitElement(jump, data);
    }

    public R visitLoopJump(FirLoopJump loopJump, D data) {
        Intrinsics.checkNotNullParameter(loopJump, "loopJump");
        return visitElement(loopJump, data);
    }

    public R visitBreakExpression(FirBreakExpression breakExpression, D data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        return visitElement(breakExpression, data);
    }

    public R visitContinueExpression(FirContinueExpression continueExpression, D data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        return visitElement(continueExpression, data);
    }

    public R visitCatch(FirCatch firCatch, D data) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        return visitElement(firCatch, data);
    }

    public R visitTryExpression(FirTryExpression tryExpression, D data) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        return visitElement(tryExpression, data);
    }

    public <T> R visitConstExpression(FirConstExpression<T> constExpression, D data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        return visitElement(constExpression, data);
    }

    public R visitTypeProjection(FirTypeProjection typeProjection, D data) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        return visitElement(typeProjection, data);
    }

    public R visitStarProjection(FirStarProjection starProjection, D data) {
        Intrinsics.checkNotNullParameter(starProjection, "starProjection");
        return visitElement(starProjection, data);
    }

    public R visitPlaceholderProjection(FirPlaceholderProjection placeholderProjection, D data) {
        Intrinsics.checkNotNullParameter(placeholderProjection, "placeholderProjection");
        return visitElement(placeholderProjection, data);
    }

    public R visitTypeProjectionWithVariance(FirTypeProjectionWithVariance typeProjectionWithVariance, D data) {
        Intrinsics.checkNotNullParameter(typeProjectionWithVariance, "typeProjectionWithVariance");
        return visitElement(typeProjectionWithVariance, data);
    }

    public R visitArgumentList(FirArgumentList argumentList, D data) {
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        return visitElement(argumentList, data);
    }

    public R visitCall(FirCall call, D data) {
        Intrinsics.checkNotNullParameter(call, "call");
        return visitElement(call, data);
    }

    public R visitAnnotation(FirAnnotation annotation, D data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return visitElement(annotation, data);
    }

    public R visitAnnotationCall(FirAnnotationCall annotationCall, D data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        return visitElement(annotationCall, data);
    }

    public R visitAnnotationArgumentMapping(FirAnnotationArgumentMapping annotationArgumentMapping, D data) {
        Intrinsics.checkNotNullParameter(annotationArgumentMapping, "annotationArgumentMapping");
        return visitElement(annotationArgumentMapping, data);
    }

    public R visitErrorAnnotationCall(FirErrorAnnotationCall errorAnnotationCall, D data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        return visitElement(errorAnnotationCall, data);
    }

    public R visitComparisonExpression(FirComparisonExpression comparisonExpression, D data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        return visitElement(comparisonExpression, data);
    }

    public R visitTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        return visitElement(typeOperatorCall, data);
    }

    public R visitAssignmentOperatorStatement(FirAssignmentOperatorStatement assignmentOperatorStatement, D data) {
        Intrinsics.checkNotNullParameter(assignmentOperatorStatement, "assignmentOperatorStatement");
        return visitElement(assignmentOperatorStatement, data);
    }

    public R visitIncrementDecrementExpression(FirIncrementDecrementExpression incrementDecrementExpression, D data) {
        Intrinsics.checkNotNullParameter(incrementDecrementExpression, "incrementDecrementExpression");
        return visitElement(incrementDecrementExpression, data);
    }

    public R visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        return visitElement(equalityOperatorCall, data);
    }

    public R visitWhenExpression(FirWhenExpression whenExpression, D data) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        return visitElement(whenExpression, data);
    }

    public R visitWhenBranch(FirWhenBranch whenBranch, D data) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        return visitElement(whenBranch, data);
    }

    public R visitContextReceiverArgumentListOwner(FirContextReceiverArgumentListOwner contextReceiverArgumentListOwner, D data) {
        Intrinsics.checkNotNullParameter(contextReceiverArgumentListOwner, "contextReceiverArgumentListOwner");
        return visitElement(contextReceiverArgumentListOwner, data);
    }

    public R visitCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, D data) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        return visitElement(checkNotNullCall, data);
    }

    public R visitElvisExpression(FirElvisExpression elvisExpression, D data) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        return visitElement(elvisExpression, data);
    }

    public R visitArrayLiteral(FirArrayLiteral arrayLiteral, D data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        return visitElement(arrayLiteral, data);
    }

    public R visitAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall, D data) {
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        return visitElement(augmentedArraySetCall, data);
    }

    public R visitClassReferenceExpression(FirClassReferenceExpression classReferenceExpression, D data) {
        Intrinsics.checkNotNullParameter(classReferenceExpression, "classReferenceExpression");
        return visitElement(classReferenceExpression, data);
    }

    public R visitErrorExpression(FirErrorExpression errorExpression, D data) {
        Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
        return visitElement(errorExpression, data);
    }

    public R visitErrorFunction(FirErrorFunction errorFunction, D data) {
        Intrinsics.checkNotNullParameter(errorFunction, "errorFunction");
        return visitElement(errorFunction, data);
    }

    public R visitErrorProperty(FirErrorProperty errorProperty, D data) {
        Intrinsics.checkNotNullParameter(errorProperty, "errorProperty");
        return visitElement(errorProperty, data);
    }

    public R visitDanglingModifierList(FirDanglingModifierList danglingModifierList, D data) {
        Intrinsics.checkNotNullParameter(danglingModifierList, "danglingModifierList");
        return visitElement(danglingModifierList, data);
    }

    public R visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        return visitElement(qualifiedAccessExpression, data);
    }

    public R visitQualifiedErrorAccessExpression(FirQualifiedErrorAccessExpression qualifiedErrorAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(qualifiedErrorAccessExpression, "qualifiedErrorAccessExpression");
        return visitElement(qualifiedErrorAccessExpression, data);
    }

    public R visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        return visitElement(propertyAccessExpression, data);
    }

    public R visitFunctionCall(FirFunctionCall functionCall, D data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        return visitElement(functionCall, data);
    }

    public R visitIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall integerLiteralOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
        return visitElement(integerLiteralOperatorCall, data);
    }

    public R visitImplicitInvokeCall(FirImplicitInvokeCall implicitInvokeCall, D data) {
        Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
        return visitElement(implicitInvokeCall, data);
    }

    public R visitDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, D data) {
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        return visitElement(delegatedConstructorCall, data);
    }

    public R visitMultiDelegatedConstructorCall(FirMultiDelegatedConstructorCall multiDelegatedConstructorCall, D data) {
        Intrinsics.checkNotNullParameter(multiDelegatedConstructorCall, "multiDelegatedConstructorCall");
        return visitElement(multiDelegatedConstructorCall, data);
    }

    public R visitComponentCall(FirComponentCall componentCall, D data) {
        Intrinsics.checkNotNullParameter(componentCall, "componentCall");
        return visitElement(componentCall, data);
    }

    public R visitCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, D data) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        return visitElement(callableReferenceAccess, data);
    }

    public R visitThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, D data) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        return visitElement(thisReceiverExpression, data);
    }

    public R visitInaccessibleReceiverExpression(FirInaccessibleReceiverExpression inaccessibleReceiverExpression, D data) {
        Intrinsics.checkNotNullParameter(inaccessibleReceiverExpression, "inaccessibleReceiverExpression");
        return visitElement(inaccessibleReceiverExpression, data);
    }

    public R visitSmartCastExpression(FirSmartCastExpression smartCastExpression, D data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        return visitElement(smartCastExpression, data);
    }

    public R visitSafeCallExpression(FirSafeCallExpression safeCallExpression, D data) {
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        return visitElement(safeCallExpression, data);
    }

    public R visitCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject, D data) {
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        return visitElement(checkedSafeCallSubject, data);
    }

    public R visitGetClassCall(FirGetClassCall getClassCall, D data) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        return visitElement(getClassCall, data);
    }

    public R visitWrappedExpression(FirWrappedExpression wrappedExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedExpression, "wrappedExpression");
        return visitElement(wrappedExpression, data);
    }

    public R visitWrappedArgumentExpression(FirWrappedArgumentExpression wrappedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        return visitElement(wrappedArgumentExpression, data);
    }

    public R visitLambdaArgumentExpression(FirLambdaArgumentExpression lambdaArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
        return visitElement(lambdaArgumentExpression, data);
    }

    public R visitSpreadArgumentExpression(FirSpreadArgumentExpression spreadArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
        return visitElement(spreadArgumentExpression, data);
    }

    public R visitNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        return visitElement(namedArgumentExpression, data);
    }

    public R visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, D data) {
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        return visitElement(varargArgumentsExpression, data);
    }

    public R visitResolvedQualifier(FirResolvedQualifier resolvedQualifier, D data) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        return visitElement(resolvedQualifier, data);
    }

    public R visitErrorResolvedQualifier(FirErrorResolvedQualifier errorResolvedQualifier, D data) {
        Intrinsics.checkNotNullParameter(errorResolvedQualifier, "errorResolvedQualifier");
        return visitElement(errorResolvedQualifier, data);
    }

    public R visitResolvedReifiedParameterReference(FirResolvedReifiedParameterReference resolvedReifiedParameterReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedReifiedParameterReference, "resolvedReifiedParameterReference");
        return visitElement(resolvedReifiedParameterReference, data);
    }

    public R visitReturnExpression(FirReturnExpression returnExpression, D data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        return visitElement(returnExpression, data);
    }

    public R visitStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, D data) {
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        return visitElement(stringConcatenationCall, data);
    }

    public R visitThrowExpression(FirThrowExpression throwExpression, D data) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        return visitElement(throwExpression, data);
    }

    public R visitVariableAssignment(FirVariableAssignment variableAssignment, D data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        return visitElement(variableAssignment, data);
    }

    public R visitWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression, D data) {
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        return visitElement(whenSubjectExpression, data);
    }

    public R visitDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression, D data) {
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        return visitElement(desugaredAssignmentValueReferenceExpression, data);
    }

    public R visitWrappedDelegateExpression(FirWrappedDelegateExpression wrappedDelegateExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedDelegateExpression, "wrappedDelegateExpression");
        return visitElement(wrappedDelegateExpression, data);
    }

    public R visitEnumEntryDeserializedAccessExpression(FirEnumEntryDeserializedAccessExpression enumEntryDeserializedAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(enumEntryDeserializedAccessExpression, "enumEntryDeserializedAccessExpression");
        return visitElement(enumEntryDeserializedAccessExpression, data);
    }

    public R visitNamedReference(FirNamedReference namedReference, D data) {
        Intrinsics.checkNotNullParameter(namedReference, "namedReference");
        return visitElement(namedReference, data);
    }

    public R visitNamedReferenceWithCandidateBase(FirNamedReferenceWithCandidateBase namedReferenceWithCandidateBase, D data) {
        Intrinsics.checkNotNullParameter(namedReferenceWithCandidateBase, "namedReferenceWithCandidateBase");
        return visitElement(namedReferenceWithCandidateBase, data);
    }

    public R visitErrorNamedReference(FirErrorNamedReference errorNamedReference, D data) {
        Intrinsics.checkNotNullParameter(errorNamedReference, "errorNamedReference");
        return visitElement(errorNamedReference, data);
    }

    public R visitFromMissingDependenciesNamedReference(FirFromMissingDependenciesNamedReference fromMissingDependenciesNamedReference, D data) {
        Intrinsics.checkNotNullParameter(fromMissingDependenciesNamedReference, "fromMissingDependenciesNamedReference");
        return visitElement(fromMissingDependenciesNamedReference, data);
    }

    public R visitSuperReference(FirSuperReference superReference, D data) {
        Intrinsics.checkNotNullParameter(superReference, "superReference");
        return visitElement(superReference, data);
    }

    public R visitThisReference(FirThisReference thisReference, D data) {
        Intrinsics.checkNotNullParameter(thisReference, "thisReference");
        return visitElement(thisReference, data);
    }

    public R visitControlFlowGraphReference(FirControlFlowGraphReference controlFlowGraphReference, D data) {
        Intrinsics.checkNotNullParameter(controlFlowGraphReference, "controlFlowGraphReference");
        return visitElement(controlFlowGraphReference, data);
    }

    public R visitResolvedNamedReference(FirResolvedNamedReference resolvedNamedReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedNamedReference, "resolvedNamedReference");
        return visitElement(resolvedNamedReference, data);
    }

    public R visitResolvedErrorReference(FirResolvedErrorReference resolvedErrorReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedErrorReference, "resolvedErrorReference");
        return visitElement(resolvedErrorReference, data);
    }

    public R visitDelegateFieldReference(FirDelegateFieldReference delegateFieldReference, D data) {
        Intrinsics.checkNotNullParameter(delegateFieldReference, "delegateFieldReference");
        return visitElement(delegateFieldReference, data);
    }

    public R visitBackingFieldReference(FirBackingFieldReference backingFieldReference, D data) {
        Intrinsics.checkNotNullParameter(backingFieldReference, "backingFieldReference");
        return visitElement(backingFieldReference, data);
    }

    public R visitResolvedCallableReference(FirResolvedCallableReference resolvedCallableReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedCallableReference, "resolvedCallableReference");
        return visitElement(resolvedCallableReference, data);
    }

    public R visitResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef, D data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        return visitElement(resolvedTypeRef, data);
    }

    public R visitErrorTypeRef(FirErrorTypeRef errorTypeRef, D data) {
        Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
        return visitElement(errorTypeRef, data);
    }

    public R visitTypeRefWithNullability(FirTypeRefWithNullability typeRefWithNullability, D data) {
        Intrinsics.checkNotNullParameter(typeRefWithNullability, "typeRefWithNullability");
        return visitElement(typeRefWithNullability, data);
    }

    public R visitUserTypeRef(FirUserTypeRef userTypeRef, D data) {
        Intrinsics.checkNotNullParameter(userTypeRef, "userTypeRef");
        return visitElement(userTypeRef, data);
    }

    public R visitDynamicTypeRef(FirDynamicTypeRef dynamicTypeRef, D data) {
        Intrinsics.checkNotNullParameter(dynamicTypeRef, "dynamicTypeRef");
        return visitElement(dynamicTypeRef, data);
    }

    public R visitFunctionTypeRef(FirFunctionTypeRef functionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        return visitElement(functionTypeRef, data);
    }

    public R visitIntersectionTypeRef(FirIntersectionTypeRef intersectionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(intersectionTypeRef, "intersectionTypeRef");
        return visitElement(intersectionTypeRef, data);
    }

    public R visitImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, D data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        return visitElement(implicitTypeRef, data);
    }

    public R visitContractElementDeclaration(FirContractElementDeclaration contractElementDeclaration, D data) {
        Intrinsics.checkNotNullParameter(contractElementDeclaration, "contractElementDeclaration");
        return visitElement(contractElementDeclaration, data);
    }

    public R visitEffectDeclaration(FirEffectDeclaration effectDeclaration, D data) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        return visitElement(effectDeclaration, data);
    }

    public R visitContractDescription(FirContractDescription contractDescription, D data) {
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        return visitElement(contractDescription, data);
    }

    public R visitLegacyRawContractDescription(FirLegacyRawContractDescription legacyRawContractDescription, D data) {
        Intrinsics.checkNotNullParameter(legacyRawContractDescription, "legacyRawContractDescription");
        return visitElement(legacyRawContractDescription, data);
    }

    public R visitRawContractDescription(FirRawContractDescription rawContractDescription, D data) {
        Intrinsics.checkNotNullParameter(rawContractDescription, "rawContractDescription");
        return visitElement(rawContractDescription, data);
    }

    public R visitResolvedContractDescription(FirResolvedContractDescription resolvedContractDescription, D data) {
        Intrinsics.checkNotNullParameter(resolvedContractDescription, "resolvedContractDescription");
        return visitElement(resolvedContractDescription, data);
    }
}
