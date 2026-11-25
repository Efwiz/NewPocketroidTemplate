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

/* JADX INFO: compiled from: FirTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Õ\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0003\b\u009d\u0001\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\nJ\u001d\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0012J\u001d\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u001d\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001eJ\u001d\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\"J\u001d\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020%2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010&J\u001d\u0010'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020)2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010*J\u001d\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020,2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010.J\u001d\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u0002012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00102J\u001d\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u0002052\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00106J\u001d\u00107\u001a\u00020\u00062\u0006\u00108\u001a\u0002092\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010:J\u001d\u0010;\u001a\u00020\u00062\u0006\u0010<\u001a\u00020=2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010>J\u001d\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020B2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010CJ\u001d\u0010D\u001a\u00020\u00062\u0006\u0010E\u001a\u00020F2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010GJ\u001d\u0010H\u001a\u00020\u00062\u0006\u0010I\u001a\u00020J2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010KJ\u001d\u0010L\u001a\u00020\u00062\u0006\u0010M\u001a\u00020N2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010OJ\u001d\u0010P\u001a\u00020\u00062\u0006\u0010Q\u001a\u00020R2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010SJ\u001d\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020U2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010WJ\u001d\u0010X\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020Z2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010[J\u001d\u0010\\\u001a\u00020]2\u0006\u0010^\u001a\u00020]2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010_J\u001d\u0010`\u001a\u00020\u00062\u0006\u0010a\u001a\u00020b2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010cJ\u001d\u0010d\u001a\u00020\u00062\u0006\u0010e\u001a\u00020f2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010gJ\u001d\u0010h\u001a\u00020\u00062\u0006\u0010i\u001a\u00020j2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010kJ\u001d\u0010l\u001a\u00020\u00062\u0006\u0010m\u001a\u00020n2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010oJ\u001d\u0010p\u001a\u00020\u00062\u0006\u0010q\u001a\u00020r2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010sJ\u001d\u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020u2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010wJ\u001d\u0010x\u001a\u00020\u00062\u0006\u0010y\u001a\u00020z2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010{J\u001d\u0010|\u001a\u00020\u00062\u0006\u0010}\u001a\u00020~2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u007fJ/\u0010\u0080\u0001\u001a\u00020\u0006\"\u0005\b\u0001\u0010\u0081\u00012\u000f\u0010\u0082\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0081\u00010\u0083\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0084\u0001J\"\u0010\u0085\u0001\u001a\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0089\u0001J!\u0010\u008a\u0001\u001a\u00020\u00062\b\u0010\u008b\u0001\u001a\u00030\u008c\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008d\u0001J\"\u0010\u008e\u0001\u001a\u00030\u008f\u00012\b\u0010\u0090\u0001\u001a\u00030\u008f\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0091\u0001J\"\u0010\u0092\u0001\u001a\u00030\u0093\u00012\b\u0010\u0094\u0001\u001a\u00030\u0093\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0095\u0001J!\u0010\u0096\u0001\u001a\u00020\u00062\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0099\u0001J\"\u0010\u009a\u0001\u001a\u00030\u009b\u00012\b\u0010\u009c\u0001\u001a\u00030\u009b\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u009d\u0001J\"\u0010\u009e\u0001\u001a\u00030\u009f\u00012\b\u0010 \u0001\u001a\u00030\u009f\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¡\u0001J\"\u0010¢\u0001\u001a\u00030£\u00012\b\u0010¤\u0001\u001a\u00030£\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¥\u0001J\"\u0010¦\u0001\u001a\u00030§\u00012\b\u0010¨\u0001\u001a\u00030§\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010©\u0001J!\u0010ª\u0001\u001a\u00020@2\b\u0010«\u0001\u001a\u00030¬\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u00ad\u0001J\"\u0010®\u0001\u001a\u00030¯\u00012\b\u0010°\u0001\u001a\u00030¯\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010±\u0001J\"\u0010²\u0001\u001a\u00030³\u00012\b\u0010´\u0001\u001a\u00030³\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010µ\u0001J\"\u0010¶\u0001\u001a\u00030·\u00012\b\u0010¸\u0001\u001a\u00030·\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¹\u0001J!\u0010º\u0001\u001a\u00020@2\b\u0010»\u0001\u001a\u00030¼\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010½\u0001J!\u0010¾\u0001\u001a\u00020\u00062\b\u0010¿\u0001\u001a\u00030À\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Á\u0001J!\u0010Â\u0001\u001a\u00020\u00062\b\u0010Ã\u0001\u001a\u00030Ä\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Å\u0001J\"\u0010Æ\u0001\u001a\u00030Ç\u00012\b\u0010È\u0001\u001a\u00030Ç\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010É\u0001J!\u0010Ê\u0001\u001a\u00020\u00062\b\u0010Ë\u0001\u001a\u00030Ì\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Í\u0001J\"\u0010Î\u0001\u001a\u00030Ï\u00012\b\u0010Ð\u0001\u001a\u00030Ñ\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ò\u0001J\"\u0010Ó\u0001\u001a\u00030£\u00012\b\u0010Ô\u0001\u001a\u00030Õ\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ö\u0001J-\u0010×\u0001\u001a\u0003HØ\u0001\"\t\b\u0001\u0010Ø\u0001*\u00020\u00032\b\u0010Ù\u0001\u001a\u0003HØ\u00012\u0006\u0010\t\u001a\u00028\u0000H&¢\u0006\u0003\u0010Ú\u0001J\"\u0010Û\u0001\u001a\u00030Ü\u00012\b\u0010Ý\u0001\u001a\u00030Ü\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Þ\u0001J!\u0010ß\u0001\u001a\u00020\u00062\b\u0010à\u0001\u001a\u00030á\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010â\u0001J!\u0010ã\u0001\u001a\u00020\u00062\b\u0010ä\u0001\u001a\u00030å\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010æ\u0001J!\u0010ç\u0001\u001a\u00020\u00062\b\u0010è\u0001\u001a\u00030é\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ê\u0001J!\u0010ë\u0001\u001a\u00020\u00062\b\u0010ì\u0001\u001a\u00030í\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010î\u0001J!\u0010ï\u0001\u001a\u00020\u00062\b\u0010ð\u0001\u001a\u00030ñ\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ò\u0001J!\u0010ó\u0001\u001a\u00020\u00062\b\u0010ô\u0001\u001a\u00030õ\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ö\u0001J!\u0010÷\u0001\u001a\u00020\u00062\b\u0010ø\u0001\u001a\u00030ù\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ú\u0001J\"\u0010û\u0001\u001a\u00030ü\u00012\b\u0010ý\u0001\u001a\u00030þ\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ÿ\u0001J!\u0010\u0080\u0002\u001a\u00020\u00062\b\u0010\u0081\u0002\u001a\u00030\u0082\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0083\u0002J!\u0010\u0084\u0002\u001a\u00020@2\b\u0010\u0085\u0002\u001a\u00030\u0086\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0087\u0002J!\u0010\u0088\u0002\u001a\u00020\u00062\b\u0010\u0089\u0002\u001a\u00030\u008a\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008b\u0002J!\u0010\u008c\u0002\u001a\u00020\u00062\b\u0010\u008d\u0002\u001a\u00030\u008e\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008f\u0002J\"\u0010\u0090\u0002\u001a\u00030Ï\u00012\b\u0010\u0091\u0002\u001a\u00030\u0092\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0093\u0002J!\u0010\u0094\u0002\u001a\u00020\u00062\b\u0010\u0095\u0002\u001a\u00030\u0096\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0097\u0002J!\u0010\u0098\u0002\u001a\u00020\u00062\b\u0010\u0099\u0002\u001a\u00030\u009a\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u009b\u0002J\"\u0010\u009c\u0002\u001a\u00030\u009d\u00022\b\u0010\u009e\u0002\u001a\u00030\u009d\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u009f\u0002J\"\u0010 \u0002\u001a\u00030¡\u00022\b\u0010¢\u0002\u001a\u00030¡\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010£\u0002J!\u0010¤\u0002\u001a\u00020@2\b\u0010¥\u0002\u001a\u00030¦\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010§\u0002J!\u0010¨\u0002\u001a\u00020\u00062\b\u0010©\u0002\u001a\u00030ª\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010«\u0002J!\u0010¬\u0002\u001a\u00020\u00062\b\u0010\u00ad\u0002\u001a\u00030®\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¯\u0002J\"\u0010°\u0002\u001a\u00030±\u00022\b\u0010²\u0002\u001a\u00030±\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010³\u0002J\"\u0010´\u0002\u001a\u00030Ï\u00012\b\u0010µ\u0002\u001a\u00030¶\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010·\u0002J!\u0010¸\u0002\u001a\u00020\u00062\b\u0010¹\u0002\u001a\u00030º\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010»\u0002J!\u0010¼\u0002\u001a\u00020\u00062\b\u0010½\u0002\u001a\u00030¾\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¿\u0002J\"\u0010À\u0002\u001a\u00030Ï\u00012\b\u0010Á\u0002\u001a\u00030Â\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ã\u0002J\"\u0010Ä\u0002\u001a\u00030ü\u00012\b\u0010Å\u0002\u001a\u00030ü\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Æ\u0002J!\u0010Ç\u0002\u001a\u00020\u00062\b\u0010È\u0002\u001a\u00030É\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ê\u0002J!\u0010Ë\u0002\u001a\u00020\u00062\b\u0010Ì\u0002\u001a\u00030Í\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Î\u0002J!\u0010Ï\u0002\u001a\u00020\u00062\b\u0010Ð\u0002\u001a\u00030Ñ\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ò\u0002J\"\u0010Ó\u0002\u001a\u00030Ï\u00012\b\u0010Ô\u0002\u001a\u00030Õ\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ö\u0002J4\u0010×\u0002\u001a\u00020\u0006\"\n\b\u0001\u0010Ø\u0001*\u00030Ø\u00022\u000f\u0010Ù\u0002\u001a\n\u0012\u0005\u0012\u0003HØ\u00010Ú\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Û\u0002J\"\u0010Ü\u0002\u001a\u00030Ý\u00022\b\u0010Þ\u0002\u001a\u00030Ý\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ß\u0002J!\u0010à\u0002\u001a\u00020\u00062\b\u0010á\u0002\u001a\u00030â\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ã\u0002J!\u0010ä\u0002\u001a\u00020\u00062\b\u0010å\u0002\u001a\u00030æ\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ç\u0002J!\u0010è\u0002\u001a\u00020\u00062\b\u0010é\u0002\u001a\u00030ê\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ë\u0002J\"\u0010ì\u0002\u001a\u00030\u009b\u00012\b\u0010í\u0002\u001a\u00030î\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ï\u0002J!\u0010ð\u0002\u001a\u00020\u00062\b\u0010ñ\u0002\u001a\u00030ò\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ó\u0002J!\u0010ô\u0002\u001a\u00020\u00062\b\u0010õ\u0002\u001a\u00030ö\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010÷\u0002J\"\u0010ø\u0002\u001a\u00030ù\u00022\b\u0010ú\u0002\u001a\u00030ù\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010û\u0002J!\u0010ü\u0002\u001a\u00020\u00062\b\u0010ý\u0002\u001a\u00030þ\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ÿ\u0002J!\u0010\u0080\u0003\u001a\u00020\u00062\b\u0010\u0081\u0003\u001a\u00030\u0082\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0083\u0003J!\u0010\u0084\u0003\u001a\u00020@2\b\u0010\u0085\u0003\u001a\u00030\u0086\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0087\u0003J!\u0010\u0088\u0003\u001a\u00020@2\b\u0010\u0089\u0003\u001a\u00030\u008a\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008b\u0003J\"\u0010\u008c\u0003\u001a\u00030\u0086\u00012\b\u0010\u008d\u0003\u001a\u00030\u008e\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008f\u0003J\"\u0010\u0090\u0003\u001a\u00030\u0091\u00032\b\u0010\u0092\u0003\u001a\u00030\u0091\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0093\u0003J\"\u0010\u0094\u0003\u001a\u00030\u0095\u00032\b\u0010\u0096\u0003\u001a\u00030\u0097\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0098\u0003J!\u0010\u0099\u0003\u001a\u00020\u00062\b\u0010\u009a\u0003\u001a\u00030\u009b\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u009c\u0003J!\u0010\u009d\u0003\u001a\u00020\u00062\b\u0010\u009e\u0003\u001a\u00030\u009f\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010 \u0003J!\u0010¡\u0003\u001a\u00020\u00062\b\u0010¢\u0003\u001a\u00030£\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¤\u0003J!\u0010¥\u0003\u001a\u00020\u00062\b\u0010¦\u0003\u001a\u00030§\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¨\u0003J!\u0010©\u0003\u001a\u00020\u00062\b\u0010ª\u0003\u001a\u00030«\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¬\u0003J\"\u0010\u00ad\u0003\u001a\u00030\u009b\u00012\b\u0010®\u0003\u001a\u00030¯\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010°\u0003J\"\u0010±\u0003\u001a\u00030²\u00032\b\u0010³\u0003\u001a\u00030²\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010´\u0003J \u0010µ\u0003\u001a\u00020@2\u0007\u0010¶\u0003\u001a\u00020@2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010·\u0003J!\u0010¸\u0003\u001a\u00020\u00062\b\u0010¹\u0003\u001a\u00030º\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010»\u0003J\"\u0010¼\u0003\u001a\u00030½\u00032\b\u0010¾\u0003\u001a\u00030½\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¿\u0003J!\u0010À\u0003\u001a\u00020@2\b\u0010Á\u0003\u001a\u00030Â\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ã\u0003J\"\u0010Ä\u0003\u001a\u00030\u009b\u00012\b\u0010Å\u0003\u001a\u00030Æ\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ç\u0003J\"\u0010È\u0003\u001a\u00030·\u00012\b\u0010É\u0003\u001a\u00030Ê\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ë\u0003J!\u0010Ì\u0003\u001a\u00020@2\b\u0010Í\u0003\u001a\u00030Î\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ï\u0003J\"\u0010Ð\u0003\u001a\u00030ü\u00012\b\u0010Ñ\u0003\u001a\u00030Ò\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ó\u0003J!\u0010Ô\u0003\u001a\u00020@2\b\u0010Õ\u0003\u001a\u00030Ö\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010×\u0003J!\u0010Ø\u0003\u001a\u00020\u00062\b\u0010Ù\u0003\u001a\u00030Ú\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Û\u0003J!\u0010Ü\u0003\u001a\u00020\u00062\b\u0010Ý\u0003\u001a\u00030Þ\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ß\u0003J\"\u0010à\u0003\u001a\u00030Ï\u00012\b\u0010á\u0003\u001a\u00030â\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ã\u0003J!\u0010ä\u0003\u001a\u00020\u00062\b\u0010å\u0003\u001a\u00030æ\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ç\u0003J!\u0010è\u0003\u001a\u00020\u00062\b\u0010é\u0003\u001a\u00030ê\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ë\u0003J\"\u0010ì\u0003\u001a\u00030í\u00032\b\u0010î\u0003\u001a\u00030í\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ï\u0003J!\u0010ð\u0003\u001a\u00020\u00062\b\u0010ñ\u0003\u001a\u00030ò\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ó\u0003J!\u0010ô\u0003\u001a\u00020\u00062\b\u0010õ\u0003\u001a\u00030ö\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010÷\u0003J!\u0010ø\u0003\u001a\u00020\u00062\b\u0010ù\u0003\u001a\u00030ú\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010û\u0003J\"\u0010ü\u0003\u001a\u00030\u0095\u00032\b\u0010ý\u0003\u001a\u00030þ\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ÿ\u0003J \u0010\u0080\u0004\u001a\u00020\u00062\u0007\u0010\u0081\u0004\u001a\u00020\u00062\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0082\u0004J!\u0010\u0083\u0004\u001a\u00020\u00062\b\u0010\u0084\u0004\u001a\u00030\u0085\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0086\u0004J!\u0010\u0087\u0004\u001a\u00020@2\b\u0010\u0088\u0004\u001a\u00030\u0089\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008a\u0004J\"\u0010\u008b\u0004\u001a\u00030Ø\u00022\b\u0010\u008c\u0004\u001a\u00030Ø\u00022\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u008d\u0004J!\u0010\u008e\u0004\u001a\u00020\u00062\b\u0010\u008f\u0004\u001a\u00030\u0090\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0091\u0004J!\u0010\u0092\u0004\u001a\u00020@2\b\u0010\u0093\u0004\u001a\u00030\u0094\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0095\u0004J!\u0010\u0096\u0004\u001a\u00020\u00062\b\u0010\u0097\u0004\u001a\u00030\u0098\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0099\u0004J!\u0010\u009a\u0004\u001a\u00020\u00062\b\u0010\u009b\u0004\u001a\u00030\u009c\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u009d\u0004J!\u0010\u009e\u0004\u001a\u00020\u00062\b\u0010\u009f\u0004\u001a\u00030 \u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¡\u0004J!\u0010¢\u0004\u001a\u00020\u00062\b\u0010£\u0004\u001a\u00030¤\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¥\u0004J\"\u0010¦\u0004\u001a\u00030\u0086\u00012\b\u0010§\u0004\u001a\u00030¨\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010©\u0004J\"\u0010ª\u0004\u001a\u00030\u0086\u00012\b\u0010«\u0004\u001a\u00030\u0086\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¬\u0004J\"\u0010\u00ad\u0004\u001a\u00030®\u00042\b\u0010¯\u0004\u001a\u00030®\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010°\u0004J\"\u0010±\u0004\u001a\u00030²\u00042\b\u0010³\u0004\u001a\u00030²\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010´\u0004J\"\u0010µ\u0004\u001a\u00030\u0095\u00032\b\u0010¶\u0004\u001a\u00030\u0095\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010·\u0004J\"\u0010¸\u0004\u001a\u00030\u0095\u00032\b\u0010¹\u0004\u001a\u00030º\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010»\u0004J\"\u0010¼\u0004\u001a\u00030Ï\u00012\b\u0010½\u0004\u001a\u00030Ï\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010¾\u0004J\"\u0010¿\u0004\u001a\u00030Ï\u00012\b\u0010À\u0004\u001a\u00030Á\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Â\u0004J\"\u0010Ã\u0004\u001a\u00030Ï\u00012\b\u0010Ä\u0004\u001a\u00030Å\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Æ\u0004J!\u0010Ç\u0004\u001a\u00020\u00062\b\u0010È\u0004\u001a\u00030É\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ê\u0004J!\u0010Ë\u0004\u001a\u00020\u00062\b\u0010Ì\u0004\u001a\u00030Í\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Î\u0004J!\u0010Ï\u0004\u001a\u00020\u00062\b\u0010Ð\u0004\u001a\u00030Ñ\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ò\u0004J!\u0010Ó\u0004\u001a\u00020\u00062\b\u0010Ô\u0004\u001a\u00030Õ\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ö\u0004J\"\u0010×\u0004\u001a\u00030Ø\u00042\b\u0010Ù\u0004\u001a\u00030Ø\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Ú\u0004J!\u0010Û\u0004\u001a\u00020\u00062\b\u0010Ü\u0004\u001a\u00030Ý\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010Þ\u0004J!\u0010ß\u0004\u001a\u00020\u00062\b\u0010à\u0004\u001a\u00030á\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010â\u0004J!\u0010ã\u0004\u001a\u00020\u00062\b\u0010ä\u0004\u001a\u00030å\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010æ\u0004J!\u0010ç\u0004\u001a\u00020\u00062\b\u0010è\u0004\u001a\u00030é\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ê\u0004J!\u0010ë\u0004\u001a\u00020\u00062\b\u0010ì\u0004\u001a\u00030í\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010î\u0004J!\u0010ï\u0004\u001a\u00020\u00062\b\u0010ð\u0004\u001a\u00030ñ\u00042\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010ò\u0004J\u001c\u0010ó\u0004\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\nJ\u001c\u0010ô\u0004\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\u000eJ\u001c\u0010õ\u0004\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\u0012J\u001c\u0010ö\u0004\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\u0016J\u001c\u0010÷\u0004\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\u001aJ\u001c\u0010ø\u0004\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\u001eJ\u001c\u0010ù\u0004\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\"J\u001c\u0010ú\u0004\u001a\u00020\u00062\u0006\u0010$\u001a\u00020%2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010&J\u001c\u0010û\u0004\u001a\u00020\u00062\u0006\u0010(\u001a\u00020)2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010*J\u001c\u0010ü\u0004\u001a\u00020,2\u0006\u0010-\u001a\u00020,2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010.J\u001c\u0010ý\u0004\u001a\u00020\u00062\u0006\u00100\u001a\u0002012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u00102J\u001c\u0010þ\u0004\u001a\u00020\u00062\u0006\u00104\u001a\u0002052\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u00106J\u001c\u0010ÿ\u0004\u001a\u00020\u00062\u0006\u00108\u001a\u0002092\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010:J\u001c\u0010\u0080\u0005\u001a\u00020\u00062\u0006\u0010<\u001a\u00020=2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010>J\u001c\u0010\u0081\u0005\u001a\u00020@2\u0006\u0010A\u001a\u00020B2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010CJ\u001c\u0010\u0082\u0005\u001a\u00020\u00062\u0006\u0010E\u001a\u00020F2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010GJ\u001c\u0010\u0083\u0005\u001a\u00020\u00062\u0006\u0010I\u001a\u00020J2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010KJ\u001c\u0010\u0084\u0005\u001a\u00020\u00062\u0006\u0010M\u001a\u00020N2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010OJ\u001c\u0010\u0085\u0005\u001a\u00020\u00062\u0006\u0010Q\u001a\u00020R2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010SJ\u001c\u0010\u0086\u0005\u001a\u00020U2\u0006\u0010V\u001a\u00020U2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010WJ\u001c\u0010\u0087\u0005\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020Z2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010[J\u001c\u0010\u0088\u0005\u001a\u00020]2\u0006\u0010^\u001a\u00020]2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010_J\u001c\u0010\u0089\u0005\u001a\u00020\u00062\u0006\u0010a\u001a\u00020b2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010cJ\u001c\u0010\u008a\u0005\u001a\u00020\u00062\u0006\u0010e\u001a\u00020f2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010gJ\u001c\u0010\u008b\u0005\u001a\u00020\u00062\u0006\u0010i\u001a\u00020j2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010kJ\u001c\u0010\u008c\u0005\u001a\u00020\u00062\u0006\u0010m\u001a\u00020n2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010oJ\u001c\u0010\u008d\u0005\u001a\u00020\u00062\u0006\u0010q\u001a\u00020r2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010sJ\u001c\u0010\u008e\u0005\u001a\u00020u2\u0006\u0010v\u001a\u00020u2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010wJ\u001c\u0010\u008f\u0005\u001a\u00020\u00062\u0006\u0010y\u001a\u00020z2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010{J\u001c\u0010\u0090\u0005\u001a\u00020\u00062\u0006\u0010}\u001a\u00020~2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0002\u0010\u007fJ-\u0010\u0091\u0005\u001a\u00020\u0006\"\u0005\b\u0001\u0010\u0081\u00012\u000f\u0010\u0082\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0081\u00010\u0083\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0084\u0001J \u0010\u0092\u0005\u001a\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0089\u0001J\u001f\u0010\u0093\u0005\u001a\u00020\u00062\b\u0010\u008b\u0001\u001a\u00030\u008c\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u008d\u0001J \u0010\u0094\u0005\u001a\u00030\u008f\u00012\b\u0010\u0090\u0001\u001a\u00030\u008f\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0091\u0001J \u0010\u0095\u0005\u001a\u00030\u0093\u00012\b\u0010\u0094\u0001\u001a\u00030\u0093\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0095\u0001J\u001f\u0010\u0096\u0005\u001a\u00020\u00062\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0099\u0001J \u0010\u0097\u0005\u001a\u00030\u009b\u00012\b\u0010\u009c\u0001\u001a\u00030\u009b\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u009d\u0001J \u0010\u0098\u0005\u001a\u00030\u009f\u00012\b\u0010 \u0001\u001a\u00030\u009f\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¡\u0001J \u0010\u0099\u0005\u001a\u00030£\u00012\b\u0010¤\u0001\u001a\u00030£\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¥\u0001J \u0010\u009a\u0005\u001a\u00030§\u00012\b\u0010¨\u0001\u001a\u00030§\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010©\u0001J\u001f\u0010\u009b\u0005\u001a\u00020@2\b\u0010«\u0001\u001a\u00030¬\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u00ad\u0001J \u0010\u009c\u0005\u001a\u00030¯\u00012\b\u0010°\u0001\u001a\u00030¯\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010±\u0001J \u0010\u009d\u0005\u001a\u00030³\u00012\b\u0010´\u0001\u001a\u00030³\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010µ\u0001J \u0010\u009e\u0005\u001a\u00030·\u00012\b\u0010¸\u0001\u001a\u00030·\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¹\u0001J\u001f\u0010\u009f\u0005\u001a\u00020@2\b\u0010»\u0001\u001a\u00030¼\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010½\u0001J\u001f\u0010 \u0005\u001a\u00020\u00062\b\u0010¿\u0001\u001a\u00030À\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Á\u0001J\u001f\u0010¡\u0005\u001a\u00020\u00062\b\u0010Ã\u0001\u001a\u00030Ä\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Å\u0001J \u0010¢\u0005\u001a\u00030Ç\u00012\b\u0010È\u0001\u001a\u00030Ç\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010É\u0001J\u001f\u0010£\u0005\u001a\u00020\u00062\b\u0010Ë\u0001\u001a\u00030Ì\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Í\u0001J \u0010¤\u0005\u001a\u00030Ï\u00012\b\u0010Ð\u0001\u001a\u00030Ñ\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ò\u0001J \u0010¥\u0005\u001a\u00030£\u00012\b\u0010Ô\u0001\u001a\u00030Õ\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ö\u0001J\u001e\u0010¦\u0005\u001a\u00020\u00032\u0007\u0010Ù\u0001\u001a\u00020\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ú\u0001J \u0010§\u0005\u001a\u00030Ü\u00012\b\u0010Ý\u0001\u001a\u00030Ü\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Þ\u0001J\u001f\u0010¨\u0005\u001a\u00020\u00062\b\u0010à\u0001\u001a\u00030á\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010â\u0001J\u001f\u0010©\u0005\u001a\u00020\u00062\b\u0010ä\u0001\u001a\u00030å\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010æ\u0001J\u001f\u0010ª\u0005\u001a\u00020\u00062\b\u0010è\u0001\u001a\u00030é\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ê\u0001J\u001f\u0010«\u0005\u001a\u00020\u00062\b\u0010ì\u0001\u001a\u00030í\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010î\u0001J\u001f\u0010¬\u0005\u001a\u00020\u00062\b\u0010ð\u0001\u001a\u00030ñ\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ò\u0001J\u001f\u0010\u00ad\u0005\u001a\u00020\u00062\b\u0010ô\u0001\u001a\u00030õ\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ö\u0001J\u001f\u0010®\u0005\u001a\u00020\u00062\b\u0010ø\u0001\u001a\u00030ù\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ú\u0001J \u0010¯\u0005\u001a\u00030ü\u00012\b\u0010ý\u0001\u001a\u00030þ\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ÿ\u0001J\u001f\u0010°\u0005\u001a\u00020\u00062\b\u0010\u0081\u0002\u001a\u00030\u0082\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0083\u0002J\u001f\u0010±\u0005\u001a\u00020@2\b\u0010\u0085\u0002\u001a\u00030\u0086\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0087\u0002J\u001f\u0010²\u0005\u001a\u00020\u00062\b\u0010\u0089\u0002\u001a\u00030\u008a\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u008b\u0002J\u001f\u0010³\u0005\u001a\u00020\u00062\b\u0010\u008d\u0002\u001a\u00030\u008e\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u008f\u0002J \u0010´\u0005\u001a\u00030Ï\u00012\b\u0010\u0091\u0002\u001a\u00030\u0092\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0093\u0002J\u001f\u0010µ\u0005\u001a\u00020\u00062\b\u0010\u0095\u0002\u001a\u00030\u0096\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0097\u0002J\u001f\u0010¶\u0005\u001a\u00020\u00062\b\u0010\u0099\u0002\u001a\u00030\u009a\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u009b\u0002J \u0010·\u0005\u001a\u00030\u009d\u00022\b\u0010\u009e\u0002\u001a\u00030\u009d\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u009f\u0002J \u0010¸\u0005\u001a\u00030¡\u00022\b\u0010¢\u0002\u001a\u00030¡\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010£\u0002J\u001f\u0010¹\u0005\u001a\u00020@2\b\u0010¥\u0002\u001a\u00030¦\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010§\u0002J\u001f\u0010º\u0005\u001a\u00020\u00062\b\u0010©\u0002\u001a\u00030ª\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010«\u0002J\u001f\u0010»\u0005\u001a\u00020\u00062\b\u0010\u00ad\u0002\u001a\u00030®\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¯\u0002J \u0010¼\u0005\u001a\u00030±\u00022\b\u0010²\u0002\u001a\u00030±\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010³\u0002J \u0010½\u0005\u001a\u00030Ï\u00012\b\u0010µ\u0002\u001a\u00030¶\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010·\u0002J\u001f\u0010¾\u0005\u001a\u00020\u00062\b\u0010¹\u0002\u001a\u00030º\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010»\u0002J\u001f\u0010¿\u0005\u001a\u00020\u00062\b\u0010½\u0002\u001a\u00030¾\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¿\u0002J \u0010À\u0005\u001a\u00030Ï\u00012\b\u0010Á\u0002\u001a\u00030Â\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ã\u0002J \u0010Á\u0005\u001a\u00030ü\u00012\b\u0010Å\u0002\u001a\u00030ü\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Æ\u0002J\u001f\u0010Â\u0005\u001a\u00020\u00062\b\u0010È\u0002\u001a\u00030É\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ê\u0002J\u001f\u0010Ã\u0005\u001a\u00020\u00062\b\u0010Ì\u0002\u001a\u00030Í\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Î\u0002J\u001f\u0010Ä\u0005\u001a\u00020\u00062\b\u0010Ð\u0002\u001a\u00030Ñ\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ò\u0002J \u0010Å\u0005\u001a\u00030Ï\u00012\b\u0010Ô\u0002\u001a\u00030Õ\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ö\u0002J2\u0010Æ\u0005\u001a\u00020\u0006\"\n\b\u0001\u0010Ø\u0001*\u00030Ø\u00022\u000f\u0010Ù\u0002\u001a\n\u0012\u0005\u0012\u0003HØ\u00010Ú\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Û\u0002J \u0010Ç\u0005\u001a\u00030Ý\u00022\b\u0010Þ\u0002\u001a\u00030Ý\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ß\u0002J\u001f\u0010È\u0005\u001a\u00020\u00062\b\u0010á\u0002\u001a\u00030â\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ã\u0002J\u001f\u0010É\u0005\u001a\u00020\u00062\b\u0010å\u0002\u001a\u00030æ\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ç\u0002J\u001f\u0010Ê\u0005\u001a\u00020\u00062\b\u0010é\u0002\u001a\u00030ê\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ë\u0002J \u0010Ë\u0005\u001a\u00030\u009b\u00012\b\u0010í\u0002\u001a\u00030î\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ï\u0002J\u001f\u0010Ì\u0005\u001a\u00020\u00062\b\u0010ñ\u0002\u001a\u00030ò\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ó\u0002J\u001f\u0010Í\u0005\u001a\u00020\u00062\b\u0010õ\u0002\u001a\u00030ö\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010÷\u0002J \u0010Î\u0005\u001a\u00030ù\u00022\b\u0010ú\u0002\u001a\u00030ù\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010û\u0002J\u001f\u0010Ï\u0005\u001a\u00020\u00062\b\u0010ý\u0002\u001a\u00030þ\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ÿ\u0002J\u001f\u0010Ð\u0005\u001a\u00020\u00062\b\u0010\u0081\u0003\u001a\u00030\u0082\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0083\u0003J\u001f\u0010Ñ\u0005\u001a\u00020@2\b\u0010\u0085\u0003\u001a\u00030\u0086\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0087\u0003J\u001f\u0010Ò\u0005\u001a\u00020@2\b\u0010\u0089\u0003\u001a\u00030\u008a\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u008b\u0003J \u0010Ó\u0005\u001a\u00030\u0086\u00012\b\u0010\u008d\u0003\u001a\u00030\u008e\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u008f\u0003J \u0010Ô\u0005\u001a\u00030\u0091\u00032\b\u0010\u0092\u0003\u001a\u00030\u0091\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0093\u0003J \u0010Õ\u0005\u001a\u00030\u0095\u00032\b\u0010\u0096\u0003\u001a\u00030\u0097\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0098\u0003J\u001f\u0010Ö\u0005\u001a\u00020\u00062\b\u0010\u009a\u0003\u001a\u00030\u009b\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u009c\u0003J\u001f\u0010×\u0005\u001a\u00020\u00062\b\u0010\u009e\u0003\u001a\u00030\u009f\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010 \u0003J\u001f\u0010Ø\u0005\u001a\u00020\u00062\b\u0010¢\u0003\u001a\u00030£\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¤\u0003J\u001f\u0010Ù\u0005\u001a\u00020\u00062\b\u0010¦\u0003\u001a\u00030§\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¨\u0003J\u001f\u0010Ú\u0005\u001a\u00020\u00062\b\u0010ª\u0003\u001a\u00030«\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¬\u0003J \u0010Û\u0005\u001a\u00030\u009b\u00012\b\u0010®\u0003\u001a\u00030¯\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010°\u0003J \u0010Ü\u0005\u001a\u00030²\u00032\b\u0010³\u0003\u001a\u00030²\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010´\u0003J\u001e\u0010Ý\u0005\u001a\u00020@2\u0007\u0010¶\u0003\u001a\u00020@2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010·\u0003J\u001f\u0010Þ\u0005\u001a\u00020\u00062\b\u0010¹\u0003\u001a\u00030º\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010»\u0003J \u0010ß\u0005\u001a\u00030½\u00032\b\u0010¾\u0003\u001a\u00030½\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¿\u0003J\u001f\u0010à\u0005\u001a\u00020@2\b\u0010Á\u0003\u001a\u00030Â\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ã\u0003J \u0010á\u0005\u001a\u00030\u009b\u00012\b\u0010Å\u0003\u001a\u00030Æ\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ç\u0003J \u0010â\u0005\u001a\u00030·\u00012\b\u0010É\u0003\u001a\u00030Ê\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ë\u0003J\u001f\u0010ã\u0005\u001a\u00020@2\b\u0010Í\u0003\u001a\u00030Î\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ï\u0003J \u0010ä\u0005\u001a\u00030ü\u00012\b\u0010Ñ\u0003\u001a\u00030Ò\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ó\u0003J\u001f\u0010å\u0005\u001a\u00020@2\b\u0010Õ\u0003\u001a\u00030Ö\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010×\u0003J\u001f\u0010æ\u0005\u001a\u00020\u00062\b\u0010Ù\u0003\u001a\u00030Ú\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Û\u0003J\u001f\u0010ç\u0005\u001a\u00020\u00062\b\u0010Ý\u0003\u001a\u00030Þ\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ß\u0003J \u0010è\u0005\u001a\u00030Ï\u00012\b\u0010á\u0003\u001a\u00030â\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ã\u0003J\u001f\u0010é\u0005\u001a\u00020\u00062\b\u0010å\u0003\u001a\u00030æ\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ç\u0003J\u001f\u0010ê\u0005\u001a\u00020\u00062\b\u0010é\u0003\u001a\u00030ê\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ë\u0003J \u0010ë\u0005\u001a\u00030í\u00032\b\u0010î\u0003\u001a\u00030í\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ï\u0003J\u001f\u0010ì\u0005\u001a\u00020\u00062\b\u0010ñ\u0003\u001a\u00030ò\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ó\u0003J\u001f\u0010í\u0005\u001a\u00020\u00062\b\u0010õ\u0003\u001a\u00030ö\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010÷\u0003J\u001f\u0010î\u0005\u001a\u00020\u00062\b\u0010ù\u0003\u001a\u00030ú\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010û\u0003J \u0010ï\u0005\u001a\u00030\u0095\u00032\b\u0010ý\u0003\u001a\u00030þ\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ÿ\u0003J\u001e\u0010ð\u0005\u001a\u00020\u00062\u0007\u0010\u0081\u0004\u001a\u00020\u00062\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0082\u0004J\u001f\u0010ñ\u0005\u001a\u00020\u00062\b\u0010\u0084\u0004\u001a\u00030\u0085\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0086\u0004J\u001f\u0010ò\u0005\u001a\u00020@2\b\u0010\u0088\u0004\u001a\u00030\u0089\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u008a\u0004J \u0010ó\u0005\u001a\u00030Ø\u00022\b\u0010\u008c\u0004\u001a\u00030Ø\u00022\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u008d\u0004J\u001f\u0010ô\u0005\u001a\u00020\u00062\b\u0010\u008f\u0004\u001a\u00030\u0090\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0091\u0004J\u001f\u0010õ\u0005\u001a\u00020@2\b\u0010\u0093\u0004\u001a\u00030\u0094\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0095\u0004J\u001f\u0010ö\u0005\u001a\u00020\u00062\b\u0010\u0097\u0004\u001a\u00030\u0098\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u0099\u0004J\u001f\u0010÷\u0005\u001a\u00020\u00062\b\u0010\u009b\u0004\u001a\u00030\u009c\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010\u009d\u0004J\u001f\u0010ø\u0005\u001a\u00020\u00062\b\u0010\u009f\u0004\u001a\u00030 \u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¡\u0004J\u001f\u0010ù\u0005\u001a\u00020\u00062\b\u0010£\u0004\u001a\u00030¤\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¥\u0004J \u0010ú\u0005\u001a\u00030\u0086\u00012\b\u0010§\u0004\u001a\u00030¨\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010©\u0004J \u0010û\u0005\u001a\u00030\u0086\u00012\b\u0010«\u0004\u001a\u00030\u0086\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¬\u0004J \u0010ü\u0005\u001a\u00030®\u00042\b\u0010¯\u0004\u001a\u00030®\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010°\u0004J \u0010ý\u0005\u001a\u00030²\u00042\b\u0010³\u0004\u001a\u00030²\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010´\u0004J \u0010þ\u0005\u001a\u00030\u0095\u00032\b\u0010¶\u0004\u001a\u00030\u0095\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010·\u0004J \u0010ÿ\u0005\u001a\u00030\u0095\u00032\b\u0010¹\u0004\u001a\u00030º\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010»\u0004J \u0010\u0080\u0006\u001a\u00030Ï\u00012\b\u0010½\u0004\u001a\u00030Ï\u00012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010¾\u0004J \u0010\u0081\u0006\u001a\u00030Ï\u00012\b\u0010À\u0004\u001a\u00030Á\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Â\u0004J \u0010\u0082\u0006\u001a\u00030Ï\u00012\b\u0010Ä\u0004\u001a\u00030Å\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Æ\u0004J\u001f\u0010\u0083\u0006\u001a\u00020\u00062\b\u0010È\u0004\u001a\u00030É\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ê\u0004J\u001f\u0010\u0084\u0006\u001a\u00020\u00062\b\u0010Ì\u0004\u001a\u00030Í\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Î\u0004J\u001f\u0010\u0085\u0006\u001a\u00020\u00062\b\u0010Ð\u0004\u001a\u00030Ñ\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ò\u0004J\u001f\u0010\u0086\u0006\u001a\u00020\u00062\b\u0010Ô\u0004\u001a\u00030Õ\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ö\u0004J \u0010\u0087\u0006\u001a\u00030Ø\u00042\b\u0010Ù\u0004\u001a\u00030Ø\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Ú\u0004J\u001f\u0010\u0088\u0006\u001a\u00020\u00062\b\u0010Ü\u0004\u001a\u00030Ý\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010Þ\u0004J\u001f\u0010\u0089\u0006\u001a\u00020\u00062\b\u0010à\u0004\u001a\u00030á\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010â\u0004J\u001f\u0010\u008a\u0006\u001a\u00020\u00062\b\u0010ä\u0004\u001a\u00030å\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010æ\u0004J\u001f\u0010\u008b\u0006\u001a\u00020\u00062\b\u0010è\u0004\u001a\u00030é\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ê\u0004J\u001f\u0010\u008c\u0006\u001a\u00020\u00062\b\u0010ì\u0004\u001a\u00030í\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010î\u0004J\u001f\u0010\u008d\u0006\u001a\u00020\u00062\b\u0010ð\u0004\u001a\u00030ñ\u00042\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0003\u0010ò\u0004¨\u0006\u008e\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "D", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "Lorg/jetbrains/kotlin/fir/FirElement;", "()V", "transformAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformAnnotationArgumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "annotationArgumentMapping", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "transformAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformAnnotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "annotationContainer", "(Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "transformAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformAnonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "anonymousInitializer", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "transformAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformArgumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "argumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "transformArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "(Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformAssignmentOperatorStatement", "assignmentOperatorStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformBackingField", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformBackingFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "backingFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirBackingFieldReference;", "(Lorg/jetbrains/kotlin/fir/references/FirBackingFieldReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformCall", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformCallableDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "callableDeclaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "transformCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformCatch", "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", PsiKeyword.CATCH, "(Lorg/jetbrains/kotlin/fir/expressions/FirCatch;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", "transformCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformClass", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformClassLikeDeclaration", "classLikeDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformClassReferenceExpression", "classReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformCodeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "codeFragment", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "transformComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformComponentCall", "componentCall", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformConstructedClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "constructedClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "transformConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformContextReceiver", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "contextReceiver", "(Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "transformContextReceiverArgumentListOwner", "Lorg/jetbrains/kotlin/fir/expressions/FirContextReceiverArgumentListOwner;", "contextReceiverArgumentListOwner", "(Lorg/jetbrains/kotlin/fir/expressions/FirContextReceiverArgumentListOwner;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirContextReceiverArgumentListOwner;", "transformContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "contractDescription", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "transformContractDescriptionOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "contractDescriptionOwner", "(Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "transformContractElementDeclaration", "Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;", "contractElementDeclaration", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;", "transformControlFlowGraphOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "controlFlowGraphOwner", "(Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "transformControlFlowGraphReference", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformDanglingModifierList", "Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", "danglingModifierList", "(Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", "transformDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "declaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "transformDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "declarationStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "transformDelegateFieldReference", "delegateFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;", "(Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformDiagnosticHolder", "Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "diagnosticHolder", "(Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "transformDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformDynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "dynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformEffectDeclaration", "effectDeclaration", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "(Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;", "transformElement", ExifInterface.LONGITUDE_EAST, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformElementWithResolveState", "Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;", "elementWithResolveState", "(Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;", "transformElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "(Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformEnumEntryDeserializedAccessExpression", "enumEntryDeserializedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorExpression", "errorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorFunction", "errorFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorImport", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "errorImport", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;", "(Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "transformErrorLoop", "errorLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorNamedReference", "errorNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirErrorNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirErrorNamedReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformErrorProperty", "errorProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorResolvedQualifier", "errorResolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorTypeRef", "errorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "(Lorg/jetbrains/kotlin/fir/declarations/FirField;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "transformFileAnnotationsContainer", "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "fileAnnotationsContainer", "(Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "transformFromMissingDependenciesNamedReference", "fromMissingDependenciesNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirFromMissingDependenciesNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirFromMissingDependenciesNamedReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformFunctionTypeParameter", "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "functionTypeParameter", "(Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "transformFunctionTypeRef", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformImplicitInvokeCall", "implicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformImplicitTypeRef", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformImport", PsiKeyword.IMPORT, "(Lorg/jetbrains/kotlin/fir/declarations/FirImport;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "transformInaccessibleReceiverExpression", "inaccessibleReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformIncrementDecrementExpression", "incrementDecrementExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformIntegerLiteralOperatorCall", "integerLiteralOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformIntersectionTypeRef", "intersectionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformJump", "Lorg/jetbrains/kotlin/fir/FirTargetElement;", "jump", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "(Lorg/jetbrains/kotlin/fir/expressions/FirJump;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformLabel", "Lorg/jetbrains/kotlin/fir/FirLabel;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "(Lorg/jetbrains/kotlin/fir/FirLabel;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirLabel;", "transformLambdaArgumentExpression", "lambdaArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformLazyBlock", "lazyBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformLazyExpression", "lazyExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformLegacyRawContractDescription", "legacyRawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "transformLoop", "loop", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLoop;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformLoopJump", "loopJump", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformMemberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "memberDeclaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "transformMultiDelegatedConstructorCall", "multiDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformNamedReference", "namedReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformNamedReferenceWithCandidateBase", "namedReferenceWithCandidateBase", "Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformOuterClassTypeParameterRef", "outerClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "transformPackageDirective", "Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "packageDirective", "(Lorg/jetbrains/kotlin/fir/FirPackageDirective;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "transformPlaceholderProjection", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "placeholderProjection", "Lorg/jetbrains/kotlin/fir/types/FirPlaceholderProjection;", "(Lorg/jetbrains/kotlin/fir/types/FirPlaceholderProjection;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "transformProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformQualifiedErrorAccessExpression", "qualifiedErrorAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedErrorAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedErrorAccessExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformRawContractDescription", "rawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "transformReceiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "receiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "transformReference", "reference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformResolvable", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "resolvable", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "transformResolvedCallableReference", "resolvedCallableReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformResolvedContractDescription", "resolvedContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "transformResolvedDeclarationStatus", "resolvedDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "transformResolvedErrorReference", "resolvedErrorReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformResolvedImport", "resolvedImport", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "transformResolvedNamedReference", "resolvedNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformResolvedQualifier", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformResolvedReifiedParameterReference", "resolvedReifiedParameterReference", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedReifiedParameterReference;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedReifiedParameterReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "script", "(Lorg/jetbrains/kotlin/fir/declarations/FirScript;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "transformSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformSpreadArgumentExpression", "spreadArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformStarProjection", "starProjection", "Lorg/jetbrains/kotlin/fir/types/FirStarProjection;", "(Lorg/jetbrains/kotlin/fir/types/FirStarProjection;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "transformStatement", "statement", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformSuperReference", "superReference", "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", "(Lorg/jetbrains/kotlin/fir/references/FirSuperReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformTargetElement", "targetElement", "(Lorg/jetbrains/kotlin/fir/FirTargetElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirTargetElement;", "transformThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformThisReference", "thisReference", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "(Lorg/jetbrains/kotlin/fir/references/FirThisReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "transformTypeParameterRef", "typeParameterRef", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "transformTypeParameterRefsOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "typeParameterRefsOwner", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "transformTypeParametersOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;", "typeParametersOwner", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;", "transformTypeProjection", "typeProjection", "(Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "transformTypeProjectionWithVariance", "typeProjectionWithVariance", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "transformTypeRef", "typeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformTypeRefWithNullability", "typeRefWithNullability", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformUserTypeRef", "userTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformVariable", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "(Lorg/jetbrains/kotlin/fir/declarations/FirVariable;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformWhenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "whenBranch", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "transformWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformWrappedArgumentExpression", "wrappedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformWrappedDelegateExpression", "wrappedDelegateExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformWrappedExpression", "wrappedExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWrappedExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "visitAnnotation", "visitAnnotationArgumentMapping", "visitAnnotationCall", "visitAnnotationContainer", "visitAnonymousFunction", "visitAnonymousFunctionExpression", "visitAnonymousInitializer", "visitAnonymousObject", "visitAnonymousObjectExpression", "visitArgumentList", "visitArrayLiteral", "visitAssignmentOperatorStatement", "visitAugmentedArraySetCall", "visitBackingField", "visitBackingFieldReference", "visitBinaryLogicExpression", "visitBlock", "visitBreakExpression", "visitCall", "visitCallableDeclaration", "visitCallableReferenceAccess", "visitCatch", "visitCheckNotNullCall", "visitCheckedSafeCallSubject", "visitClass", "visitClassLikeDeclaration", "visitClassReferenceExpression", "visitCodeFragment", "visitComparisonExpression", "visitComponentCall", "visitConstExpression", "visitConstructedClassTypeParameterRef", "visitConstructor", "visitContextReceiver", "visitContextReceiverArgumentListOwner", "visitContinueExpression", "visitContractDescription", "visitContractDescriptionOwner", "visitContractElementDeclaration", "visitControlFlowGraphOwner", "visitControlFlowGraphReference", "visitDanglingModifierList", "visitDeclaration", "visitDeclarationStatus", "visitDelegateFieldReference", "visitDelegatedConstructorCall", "visitDesugaredAssignmentValueReferenceExpression", "visitDiagnosticHolder", "visitDoWhileLoop", "visitDynamicTypeRef", "visitEffectDeclaration", "visitElement", "visitElementWithResolveState", "visitElvisExpression", "visitEnumEntry", "visitEnumEntryDeserializedAccessExpression", "visitEqualityOperatorCall", "visitErrorAnnotationCall", "visitErrorExpression", "visitErrorFunction", "visitErrorImport", "visitErrorLoop", "visitErrorNamedReference", "visitErrorProperty", "visitErrorResolvedQualifier", "visitErrorTypeRef", "visitExpression", "visitField", "visitFile", "visitFileAnnotationsContainer", "visitFromMissingDependenciesNamedReference", "visitFunction", "visitFunctionCall", "visitFunctionTypeParameter", "visitFunctionTypeRef", "visitGetClassCall", "visitImplicitInvokeCall", "visitImplicitTypeRef", "visitImport", "visitInaccessibleReceiverExpression", "visitIncrementDecrementExpression", "visitIntegerLiteralOperatorCall", "visitIntersectionTypeRef", "visitJump", "visitLabel", "visitLambdaArgumentExpression", "visitLazyBlock", "visitLazyExpression", "visitLegacyRawContractDescription", "visitLoop", "visitLoopJump", "visitMemberDeclaration", "visitMultiDelegatedConstructorCall", "visitNamedArgumentExpression", "visitNamedReference", "visitNamedReferenceWithCandidateBase", "visitOuterClassTypeParameterRef", "visitPackageDirective", "visitPlaceholderProjection", "visitProperty", "visitPropertyAccessExpression", "visitPropertyAccessor", "visitQualifiedAccessExpression", "visitQualifiedErrorAccessExpression", "visitRawContractDescription", "visitReceiverParameter", "visitReference", "visitRegularClass", "visitResolvable", "visitResolvedCallableReference", "visitResolvedContractDescription", "visitResolvedDeclarationStatus", "visitResolvedErrorReference", "visitResolvedImport", "visitResolvedNamedReference", "visitResolvedQualifier", "visitResolvedReifiedParameterReference", "visitResolvedTypeRef", "visitReturnExpression", "visitSafeCallExpression", "visitScript", "visitSimpleFunction", "visitSmartCastExpression", "visitSpreadArgumentExpression", "visitStarProjection", "visitStatement", "visitStringConcatenationCall", "visitSuperReference", "visitTargetElement", "visitThisReceiverExpression", "visitThisReference", "visitThrowExpression", "visitTryExpression", "visitTypeAlias", "visitTypeOperatorCall", "visitTypeParameter", "visitTypeParameterRef", "visitTypeParameterRefsOwner", "visitTypeParametersOwner", "visitTypeProjection", "visitTypeProjectionWithVariance", "visitTypeRef", "visitTypeRefWithNullability", "visitUserTypeRef", "visitValueParameter", "visitVarargArgumentsExpression", "visitVariable", "visitVariableAssignment", "visitWhenBranch", "visitWhenExpression", "visitWhenSubjectExpression", "visitWhileLoop", "visitWrappedArgumentExpression", "visitWrappedDelegateExpression", "visitWrappedExpression", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTransformer<D> extends FirVisitor<FirElement, D> {
    public abstract <E extends FirElement> E transformElement(E element, D data);

    public FirAnnotationContainer transformAnnotationContainer(FirAnnotationContainer annotationContainer, D data) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        return (FirAnnotationContainer) transformElement(annotationContainer, data);
    }

    public FirTypeRef transformTypeRef(FirTypeRef typeRef, D data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        return (FirTypeRef) transformElement(typeRef, data);
    }

    public FirReference transformReference(FirReference reference, D data) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        return (FirReference) transformElement(reference, data);
    }

    public FirLabel transformLabel(FirLabel label, D data) {
        Intrinsics.checkNotNullParameter(label, "label");
        return (FirLabel) transformElement(label, data);
    }

    public FirResolvable transformResolvable(FirResolvable resolvable, D data) {
        Intrinsics.checkNotNullParameter(resolvable, "resolvable");
        return (FirResolvable) transformElement(resolvable, data);
    }

    public FirTargetElement transformTargetElement(FirTargetElement targetElement, D data) {
        Intrinsics.checkNotNullParameter(targetElement, "targetElement");
        return (FirTargetElement) transformElement(targetElement, data);
    }

    public FirDeclarationStatus transformDeclarationStatus(FirDeclarationStatus declarationStatus, D data) {
        Intrinsics.checkNotNullParameter(declarationStatus, "declarationStatus");
        return (FirDeclarationStatus) transformElement(declarationStatus, data);
    }

    public FirDeclarationStatus transformResolvedDeclarationStatus(FirResolvedDeclarationStatus resolvedDeclarationStatus, D data) {
        Intrinsics.checkNotNullParameter(resolvedDeclarationStatus, "resolvedDeclarationStatus");
        return (FirDeclarationStatus) transformElement(resolvedDeclarationStatus, data);
    }

    public FirControlFlowGraphOwner transformControlFlowGraphOwner(FirControlFlowGraphOwner controlFlowGraphOwner, D data) {
        Intrinsics.checkNotNullParameter(controlFlowGraphOwner, "controlFlowGraphOwner");
        return (FirControlFlowGraphOwner) transformElement(controlFlowGraphOwner, data);
    }

    public FirStatement transformStatement(FirStatement statement, D data) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        return (FirStatement) transformElement(statement, data);
    }

    public FirStatement transformExpression(FirExpression expression, D data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (FirStatement) transformElement(expression, data);
    }

    public FirStatement transformLazyExpression(FirLazyExpression lazyExpression, D data) {
        Intrinsics.checkNotNullParameter(lazyExpression, "lazyExpression");
        return (FirStatement) transformElement(lazyExpression, data);
    }

    public FirContextReceiver transformContextReceiver(FirContextReceiver contextReceiver, D data) {
        Intrinsics.checkNotNullParameter(contextReceiver, "contextReceiver");
        return (FirContextReceiver) transformElement(contextReceiver, data);
    }

    public FirElementWithResolveState transformElementWithResolveState(FirElementWithResolveState elementWithResolveState, D data) {
        Intrinsics.checkNotNullParameter(elementWithResolveState, "elementWithResolveState");
        return (FirElementWithResolveState) transformElement(elementWithResolveState, data);
    }

    public FirFileAnnotationsContainer transformFileAnnotationsContainer(FirFileAnnotationsContainer fileAnnotationsContainer, D data) {
        Intrinsics.checkNotNullParameter(fileAnnotationsContainer, "fileAnnotationsContainer");
        return (FirFileAnnotationsContainer) transformElement(fileAnnotationsContainer, data);
    }

    public FirDeclaration transformDeclaration(FirDeclaration declaration, D data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return (FirDeclaration) transformElement(declaration, data);
    }

    public FirTypeParameterRefsOwner transformTypeParameterRefsOwner(FirTypeParameterRefsOwner typeParameterRefsOwner, D data) {
        Intrinsics.checkNotNullParameter(typeParameterRefsOwner, "typeParameterRefsOwner");
        return (FirTypeParameterRefsOwner) transformElement(typeParameterRefsOwner, data);
    }

    public FirTypeParametersOwner transformTypeParametersOwner(FirTypeParametersOwner typeParametersOwner, D data) {
        Intrinsics.checkNotNullParameter(typeParametersOwner, "typeParametersOwner");
        return (FirTypeParametersOwner) transformElement(typeParametersOwner, data);
    }

    public FirMemberDeclaration transformMemberDeclaration(FirMemberDeclaration memberDeclaration, D data) {
        Intrinsics.checkNotNullParameter(memberDeclaration, "memberDeclaration");
        return (FirMemberDeclaration) transformElement(memberDeclaration, data);
    }

    public FirAnonymousInitializer transformAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, D data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        return (FirAnonymousInitializer) transformElement(anonymousInitializer, data);
    }

    public FirCallableDeclaration transformCallableDeclaration(FirCallableDeclaration callableDeclaration, D data) {
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        return (FirCallableDeclaration) transformElement(callableDeclaration, data);
    }

    public FirTypeParameterRef transformTypeParameterRef(FirTypeParameterRef typeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(typeParameterRef, "typeParameterRef");
        return (FirTypeParameterRef) transformElement(typeParameterRef, data);
    }

    public FirTypeParameterRef transformTypeParameter(FirTypeParameter typeParameter, D data) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return (FirTypeParameterRef) transformElement(typeParameter, data);
    }

    public FirTypeParameterRef transformConstructedClassTypeParameterRef(FirConstructedClassTypeParameterRef constructedClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(constructedClassTypeParameterRef, "constructedClassTypeParameterRef");
        return (FirTypeParameterRef) transformElement(constructedClassTypeParameterRef, data);
    }

    public FirTypeParameterRef transformOuterClassTypeParameterRef(FirOuterClassTypeParameterRef outerClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(outerClassTypeParameterRef, "outerClassTypeParameterRef");
        return (FirTypeParameterRef) transformElement(outerClassTypeParameterRef, data);
    }

    public FirStatement transformVariable(FirVariable variable, D data) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return (FirStatement) transformElement(variable, data);
    }

    public FirStatement transformValueParameter(FirValueParameter valueParameter, D data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return (FirStatement) transformElement(valueParameter, data);
    }

    public FirReceiverParameter transformReceiverParameter(FirReceiverParameter receiverParameter, D data) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        return (FirReceiverParameter) transformElement(receiverParameter, data);
    }

    public FirStatement transformProperty(FirProperty property, D data) {
        Intrinsics.checkNotNullParameter(property, "property");
        return (FirStatement) transformElement(property, data);
    }

    public FirStatement transformField(FirField field, D data) {
        Intrinsics.checkNotNullParameter(field, "field");
        return (FirStatement) transformElement(field, data);
    }

    public FirStatement transformEnumEntry(FirEnumEntry enumEntry, D data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return (FirStatement) transformElement(enumEntry, data);
    }

    public FirFunctionTypeParameter transformFunctionTypeParameter(FirFunctionTypeParameter functionTypeParameter, D data) {
        Intrinsics.checkNotNullParameter(functionTypeParameter, "functionTypeParameter");
        return (FirFunctionTypeParameter) transformElement(functionTypeParameter, data);
    }

    public FirStatement transformClassLikeDeclaration(FirClassLikeDeclaration classLikeDeclaration, D data) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        return (FirStatement) transformElement(classLikeDeclaration, data);
    }

    public FirStatement transformClass(FirClass klass, D data) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return (FirStatement) transformElement(klass, data);
    }

    public FirStatement transformRegularClass(FirRegularClass regularClass, D data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        return (FirStatement) transformElement(regularClass, data);
    }

    public FirStatement transformTypeAlias(FirTypeAlias typeAlias, D data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        return (FirStatement) transformElement(typeAlias, data);
    }

    public FirStatement transformFunction(FirFunction function, D data) {
        Intrinsics.checkNotNullParameter(function, "function");
        return (FirStatement) transformElement(function, data);
    }

    public FirContractDescriptionOwner transformContractDescriptionOwner(FirContractDescriptionOwner contractDescriptionOwner, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionOwner, "contractDescriptionOwner");
        return (FirContractDescriptionOwner) transformElement(contractDescriptionOwner, data);
    }

    public FirStatement transformSimpleFunction(FirSimpleFunction simpleFunction, D data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        return (FirStatement) transformElement(simpleFunction, data);
    }

    public FirStatement transformPropertyAccessor(FirPropertyAccessor propertyAccessor, D data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        return (FirStatement) transformElement(propertyAccessor, data);
    }

    public FirStatement transformBackingField(FirBackingField backingField, D data) {
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        return (FirStatement) transformElement(backingField, data);
    }

    public FirStatement transformConstructor(FirConstructor constructor, D data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return (FirStatement) transformElement(constructor, data);
    }

    public FirFile transformFile(FirFile file, D data) {
        Intrinsics.checkNotNullParameter(file, "file");
        return (FirFile) transformElement(file, data);
    }

    public FirScript transformScript(FirScript script, D data) {
        Intrinsics.checkNotNullParameter(script, "script");
        return (FirScript) transformElement(script, data);
    }

    public FirCodeFragment transformCodeFragment(FirCodeFragment codeFragment, D data) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        return (FirCodeFragment) transformElement(codeFragment, data);
    }

    public FirPackageDirective transformPackageDirective(FirPackageDirective packageDirective, D data) {
        Intrinsics.checkNotNullParameter(packageDirective, "packageDirective");
        return (FirPackageDirective) transformElement(packageDirective, data);
    }

    public FirStatement transformAnonymousFunction(FirAnonymousFunction anonymousFunction, D data) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        return (FirStatement) transformElement(anonymousFunction, data);
    }

    public FirStatement transformAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, D data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        return (FirStatement) transformElement(anonymousFunctionExpression, data);
    }

    public FirStatement transformAnonymousObject(FirAnonymousObject anonymousObject, D data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        return (FirStatement) transformElement(anonymousObject, data);
    }

    public FirStatement transformAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, D data) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        return (FirStatement) transformElement(anonymousObjectExpression, data);
    }

    public FirDiagnosticHolder transformDiagnosticHolder(FirDiagnosticHolder diagnosticHolder, D data) {
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        return (FirDiagnosticHolder) transformElement(diagnosticHolder, data);
    }

    public FirImport transformImport(FirImport firImport, D data) {
        Intrinsics.checkNotNullParameter(firImport, "import");
        return (FirImport) transformElement(firImport, data);
    }

    public FirImport transformResolvedImport(FirResolvedImport resolvedImport, D data) {
        Intrinsics.checkNotNullParameter(resolvedImport, "resolvedImport");
        return (FirImport) transformElement(resolvedImport, data);
    }

    public FirImport transformErrorImport(FirErrorImport errorImport, D data) {
        Intrinsics.checkNotNullParameter(errorImport, "errorImport");
        return (FirImport) transformElement(errorImport, data);
    }

    public FirStatement transformLoop(FirLoop loop, D data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return (FirStatement) transformElement(loop, data);
    }

    public FirStatement transformErrorLoop(FirErrorLoop errorLoop, D data) {
        Intrinsics.checkNotNullParameter(errorLoop, "errorLoop");
        return (FirStatement) transformElement(errorLoop, data);
    }

    public FirStatement transformDoWhileLoop(FirDoWhileLoop doWhileLoop, D data) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        return (FirStatement) transformElement(doWhileLoop, data);
    }

    public FirStatement transformWhileLoop(FirWhileLoop whileLoop, D data) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        return (FirStatement) transformElement(whileLoop, data);
    }

    public FirStatement transformBlock(FirBlock block, D data) {
        Intrinsics.checkNotNullParameter(block, "block");
        return (FirStatement) transformElement(block, data);
    }

    public FirStatement transformLazyBlock(FirLazyBlock lazyBlock, D data) {
        Intrinsics.checkNotNullParameter(lazyBlock, "lazyBlock");
        return (FirStatement) transformElement(lazyBlock, data);
    }

    public FirStatement transformBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, D data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        return (FirStatement) transformElement(binaryLogicExpression, data);
    }

    public <E extends FirTargetElement> FirStatement transformJump(FirJump<E> jump, D data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return (FirStatement) transformElement(jump, data);
    }

    public FirStatement transformLoopJump(FirLoopJump loopJump, D data) {
        Intrinsics.checkNotNullParameter(loopJump, "loopJump");
        return (FirStatement) transformElement(loopJump, data);
    }

    public FirStatement transformBreakExpression(FirBreakExpression breakExpression, D data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        return (FirStatement) transformElement(breakExpression, data);
    }

    public FirStatement transformContinueExpression(FirContinueExpression continueExpression, D data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        return (FirStatement) transformElement(continueExpression, data);
    }

    public FirCatch transformCatch(FirCatch firCatch, D data) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        return (FirCatch) transformElement(firCatch, data);
    }

    public FirStatement transformTryExpression(FirTryExpression tryExpression, D data) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        return (FirStatement) transformElement(tryExpression, data);
    }

    public <T> FirStatement transformConstExpression(FirConstExpression<T> constExpression, D data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        return (FirStatement) transformElement(constExpression, data);
    }

    public FirTypeProjection transformTypeProjection(FirTypeProjection typeProjection, D data) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        return (FirTypeProjection) transformElement(typeProjection, data);
    }

    public FirTypeProjection transformStarProjection(FirStarProjection starProjection, D data) {
        Intrinsics.checkNotNullParameter(starProjection, "starProjection");
        return (FirTypeProjection) transformElement(starProjection, data);
    }

    public FirTypeProjection transformPlaceholderProjection(FirPlaceholderProjection placeholderProjection, D data) {
        Intrinsics.checkNotNullParameter(placeholderProjection, "placeholderProjection");
        return (FirTypeProjection) transformElement(placeholderProjection, data);
    }

    public FirTypeProjection transformTypeProjectionWithVariance(FirTypeProjectionWithVariance typeProjectionWithVariance, D data) {
        Intrinsics.checkNotNullParameter(typeProjectionWithVariance, "typeProjectionWithVariance");
        return (FirTypeProjection) transformElement(typeProjectionWithVariance, data);
    }

    public FirArgumentList transformArgumentList(FirArgumentList argumentList, D data) {
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        return (FirArgumentList) transformElement(argumentList, data);
    }

    public FirStatement transformCall(FirCall call, D data) {
        Intrinsics.checkNotNullParameter(call, "call");
        return (FirStatement) transformElement(call, data);
    }

    public FirStatement transformAnnotation(FirAnnotation annotation, D data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return (FirStatement) transformElement(annotation, data);
    }

    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, D data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        return (FirStatement) transformElement(annotationCall, data);
    }

    public FirAnnotationArgumentMapping transformAnnotationArgumentMapping(FirAnnotationArgumentMapping annotationArgumentMapping, D data) {
        Intrinsics.checkNotNullParameter(annotationArgumentMapping, "annotationArgumentMapping");
        return (FirAnnotationArgumentMapping) transformElement(annotationArgumentMapping, data);
    }

    public FirStatement transformErrorAnnotationCall(FirErrorAnnotationCall errorAnnotationCall, D data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        return (FirStatement) transformElement(errorAnnotationCall, data);
    }

    public FirStatement transformComparisonExpression(FirComparisonExpression comparisonExpression, D data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        return (FirStatement) transformElement(comparisonExpression, data);
    }

    public FirStatement transformTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        return (FirStatement) transformElement(typeOperatorCall, data);
    }

    public FirStatement transformAssignmentOperatorStatement(FirAssignmentOperatorStatement assignmentOperatorStatement, D data) {
        Intrinsics.checkNotNullParameter(assignmentOperatorStatement, "assignmentOperatorStatement");
        return (FirStatement) transformElement(assignmentOperatorStatement, data);
    }

    public FirStatement transformIncrementDecrementExpression(FirIncrementDecrementExpression incrementDecrementExpression, D data) {
        Intrinsics.checkNotNullParameter(incrementDecrementExpression, "incrementDecrementExpression");
        return (FirStatement) transformElement(incrementDecrementExpression, data);
    }

    public FirStatement transformEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        return (FirStatement) transformElement(equalityOperatorCall, data);
    }

    public FirStatement transformWhenExpression(FirWhenExpression whenExpression, D data) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        return (FirStatement) transformElement(whenExpression, data);
    }

    public FirWhenBranch transformWhenBranch(FirWhenBranch whenBranch, D data) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        return (FirWhenBranch) transformElement(whenBranch, data);
    }

    public FirContextReceiverArgumentListOwner transformContextReceiverArgumentListOwner(FirContextReceiverArgumentListOwner contextReceiverArgumentListOwner, D data) {
        Intrinsics.checkNotNullParameter(contextReceiverArgumentListOwner, "contextReceiverArgumentListOwner");
        return (FirContextReceiverArgumentListOwner) transformElement(contextReceiverArgumentListOwner, data);
    }

    public FirStatement transformCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, D data) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        return (FirStatement) transformElement(checkNotNullCall, data);
    }

    public FirStatement transformElvisExpression(FirElvisExpression elvisExpression, D data) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        return (FirStatement) transformElement(elvisExpression, data);
    }

    public FirStatement transformArrayLiteral(FirArrayLiteral arrayLiteral, D data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        return (FirStatement) transformElement(arrayLiteral, data);
    }

    public FirStatement transformAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall, D data) {
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        return (FirStatement) transformElement(augmentedArraySetCall, data);
    }

    public FirStatement transformClassReferenceExpression(FirClassReferenceExpression classReferenceExpression, D data) {
        Intrinsics.checkNotNullParameter(classReferenceExpression, "classReferenceExpression");
        return (FirStatement) transformElement(classReferenceExpression, data);
    }

    public FirStatement transformErrorExpression(FirErrorExpression errorExpression, D data) {
        Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
        return (FirStatement) transformElement(errorExpression, data);
    }

    public FirStatement transformErrorFunction(FirErrorFunction errorFunction, D data) {
        Intrinsics.checkNotNullParameter(errorFunction, "errorFunction");
        return (FirStatement) transformElement(errorFunction, data);
    }

    public FirStatement transformErrorProperty(FirErrorProperty errorProperty, D data) {
        Intrinsics.checkNotNullParameter(errorProperty, "errorProperty");
        return (FirStatement) transformElement(errorProperty, data);
    }

    public FirDanglingModifierList transformDanglingModifierList(FirDanglingModifierList danglingModifierList, D data) {
        Intrinsics.checkNotNullParameter(danglingModifierList, "danglingModifierList");
        return (FirDanglingModifierList) transformElement(danglingModifierList, data);
    }

    public FirStatement transformQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        return (FirStatement) transformElement(qualifiedAccessExpression, data);
    }

    public FirStatement transformQualifiedErrorAccessExpression(FirQualifiedErrorAccessExpression qualifiedErrorAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(qualifiedErrorAccessExpression, "qualifiedErrorAccessExpression");
        return (FirStatement) transformElement(qualifiedErrorAccessExpression, data);
    }

    public FirStatement transformPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        return (FirStatement) transformElement(propertyAccessExpression, data);
    }

    public FirStatement transformFunctionCall(FirFunctionCall functionCall, D data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        return (FirStatement) transformElement(functionCall, data);
    }

    public FirStatement transformIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall integerLiteralOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
        return (FirStatement) transformElement(integerLiteralOperatorCall, data);
    }

    public FirStatement transformImplicitInvokeCall(FirImplicitInvokeCall implicitInvokeCall, D data) {
        Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
        return (FirStatement) transformElement(implicitInvokeCall, data);
    }

    public FirStatement transformDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, D data) {
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        return (FirStatement) transformElement(delegatedConstructorCall, data);
    }

    public FirStatement transformMultiDelegatedConstructorCall(FirMultiDelegatedConstructorCall multiDelegatedConstructorCall, D data) {
        Intrinsics.checkNotNullParameter(multiDelegatedConstructorCall, "multiDelegatedConstructorCall");
        return (FirStatement) transformElement(multiDelegatedConstructorCall, data);
    }

    public FirStatement transformComponentCall(FirComponentCall componentCall, D data) {
        Intrinsics.checkNotNullParameter(componentCall, "componentCall");
        return (FirStatement) transformElement(componentCall, data);
    }

    public FirStatement transformCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, D data) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        return (FirStatement) transformElement(callableReferenceAccess, data);
    }

    public FirStatement transformThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, D data) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        return (FirStatement) transformElement(thisReceiverExpression, data);
    }

    public FirStatement transformInaccessibleReceiverExpression(FirInaccessibleReceiverExpression inaccessibleReceiverExpression, D data) {
        Intrinsics.checkNotNullParameter(inaccessibleReceiverExpression, "inaccessibleReceiverExpression");
        return (FirStatement) transformElement(inaccessibleReceiverExpression, data);
    }

    public FirStatement transformSmartCastExpression(FirSmartCastExpression smartCastExpression, D data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        return (FirStatement) transformElement(smartCastExpression, data);
    }

    public FirStatement transformSafeCallExpression(FirSafeCallExpression safeCallExpression, D data) {
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        return (FirStatement) transformElement(safeCallExpression, data);
    }

    public FirStatement transformCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject, D data) {
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        return (FirStatement) transformElement(checkedSafeCallSubject, data);
    }

    public FirStatement transformGetClassCall(FirGetClassCall getClassCall, D data) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        return (FirStatement) transformElement(getClassCall, data);
    }

    public FirStatement transformWrappedExpression(FirWrappedExpression wrappedExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedExpression, "wrappedExpression");
        return (FirStatement) transformElement(wrappedExpression, data);
    }

    public FirStatement transformWrappedArgumentExpression(FirWrappedArgumentExpression wrappedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        return (FirStatement) transformElement(wrappedArgumentExpression, data);
    }

    public FirStatement transformLambdaArgumentExpression(FirLambdaArgumentExpression lambdaArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
        return (FirStatement) transformElement(lambdaArgumentExpression, data);
    }

    public FirStatement transformSpreadArgumentExpression(FirSpreadArgumentExpression spreadArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
        return (FirStatement) transformElement(spreadArgumentExpression, data);
    }

    public FirStatement transformNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        return (FirStatement) transformElement(namedArgumentExpression, data);
    }

    public FirStatement transformVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, D data) {
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        return (FirStatement) transformElement(varargArgumentsExpression, data);
    }

    public FirStatement transformResolvedQualifier(FirResolvedQualifier resolvedQualifier, D data) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        return (FirStatement) transformElement(resolvedQualifier, data);
    }

    public FirStatement transformErrorResolvedQualifier(FirErrorResolvedQualifier errorResolvedQualifier, D data) {
        Intrinsics.checkNotNullParameter(errorResolvedQualifier, "errorResolvedQualifier");
        return (FirStatement) transformElement(errorResolvedQualifier, data);
    }

    public FirStatement transformResolvedReifiedParameterReference(FirResolvedReifiedParameterReference resolvedReifiedParameterReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedReifiedParameterReference, "resolvedReifiedParameterReference");
        return (FirStatement) transformElement(resolvedReifiedParameterReference, data);
    }

    public FirStatement transformReturnExpression(FirReturnExpression returnExpression, D data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        return (FirStatement) transformElement(returnExpression, data);
    }

    public FirStatement transformStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, D data) {
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        return (FirStatement) transformElement(stringConcatenationCall, data);
    }

    public FirStatement transformThrowExpression(FirThrowExpression throwExpression, D data) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        return (FirStatement) transformElement(throwExpression, data);
    }

    public FirStatement transformVariableAssignment(FirVariableAssignment variableAssignment, D data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        return (FirStatement) transformElement(variableAssignment, data);
    }

    public FirStatement transformWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression, D data) {
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        return (FirStatement) transformElement(whenSubjectExpression, data);
    }

    public FirStatement transformDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression, D data) {
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        return (FirStatement) transformElement(desugaredAssignmentValueReferenceExpression, data);
    }

    public FirStatement transformWrappedDelegateExpression(FirWrappedDelegateExpression wrappedDelegateExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedDelegateExpression, "wrappedDelegateExpression");
        return (FirStatement) transformElement(wrappedDelegateExpression, data);
    }

    public FirStatement transformEnumEntryDeserializedAccessExpression(FirEnumEntryDeserializedAccessExpression enumEntryDeserializedAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(enumEntryDeserializedAccessExpression, "enumEntryDeserializedAccessExpression");
        return (FirStatement) transformElement(enumEntryDeserializedAccessExpression, data);
    }

    public FirReference transformNamedReference(FirNamedReference namedReference, D data) {
        Intrinsics.checkNotNullParameter(namedReference, "namedReference");
        return (FirReference) transformElement(namedReference, data);
    }

    public FirReference transformNamedReferenceWithCandidateBase(FirNamedReferenceWithCandidateBase namedReferenceWithCandidateBase, D data) {
        Intrinsics.checkNotNullParameter(namedReferenceWithCandidateBase, "namedReferenceWithCandidateBase");
        return (FirReference) transformElement(namedReferenceWithCandidateBase, data);
    }

    public FirReference transformErrorNamedReference(FirErrorNamedReference errorNamedReference, D data) {
        Intrinsics.checkNotNullParameter(errorNamedReference, "errorNamedReference");
        return (FirReference) transformElement(errorNamedReference, data);
    }

    public FirReference transformFromMissingDependenciesNamedReference(FirFromMissingDependenciesNamedReference fromMissingDependenciesNamedReference, D data) {
        Intrinsics.checkNotNullParameter(fromMissingDependenciesNamedReference, "fromMissingDependenciesNamedReference");
        return (FirReference) transformElement(fromMissingDependenciesNamedReference, data);
    }

    public FirReference transformSuperReference(FirSuperReference superReference, D data) {
        Intrinsics.checkNotNullParameter(superReference, "superReference");
        return (FirReference) transformElement(superReference, data);
    }

    public FirReference transformThisReference(FirThisReference thisReference, D data) {
        Intrinsics.checkNotNullParameter(thisReference, "thisReference");
        return (FirReference) transformElement(thisReference, data);
    }

    public FirReference transformControlFlowGraphReference(FirControlFlowGraphReference controlFlowGraphReference, D data) {
        Intrinsics.checkNotNullParameter(controlFlowGraphReference, "controlFlowGraphReference");
        return (FirReference) transformElement(controlFlowGraphReference, data);
    }

    public FirReference transformResolvedNamedReference(FirResolvedNamedReference resolvedNamedReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedNamedReference, "resolvedNamedReference");
        return (FirReference) transformElement(resolvedNamedReference, data);
    }

    public FirReference transformResolvedErrorReference(FirResolvedErrorReference resolvedErrorReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedErrorReference, "resolvedErrorReference");
        return (FirReference) transformElement(resolvedErrorReference, data);
    }

    public FirReference transformDelegateFieldReference(FirDelegateFieldReference delegateFieldReference, D data) {
        Intrinsics.checkNotNullParameter(delegateFieldReference, "delegateFieldReference");
        return (FirReference) transformElement(delegateFieldReference, data);
    }

    public FirReference transformBackingFieldReference(FirBackingFieldReference backingFieldReference, D data) {
        Intrinsics.checkNotNullParameter(backingFieldReference, "backingFieldReference");
        return (FirReference) transformElement(backingFieldReference, data);
    }

    public FirReference transformResolvedCallableReference(FirResolvedCallableReference resolvedCallableReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedCallableReference, "resolvedCallableReference");
        return (FirReference) transformElement(resolvedCallableReference, data);
    }

    public FirTypeRef transformResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef, D data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        return (FirTypeRef) transformElement(resolvedTypeRef, data);
    }

    public FirTypeRef transformErrorTypeRef(FirErrorTypeRef errorTypeRef, D data) {
        Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
        return (FirTypeRef) transformElement(errorTypeRef, data);
    }

    public FirTypeRef transformTypeRefWithNullability(FirTypeRefWithNullability typeRefWithNullability, D data) {
        Intrinsics.checkNotNullParameter(typeRefWithNullability, "typeRefWithNullability");
        return (FirTypeRef) transformElement(typeRefWithNullability, data);
    }

    public FirTypeRef transformUserTypeRef(FirUserTypeRef userTypeRef, D data) {
        Intrinsics.checkNotNullParameter(userTypeRef, "userTypeRef");
        return (FirTypeRef) transformElement(userTypeRef, data);
    }

    public FirTypeRef transformDynamicTypeRef(FirDynamicTypeRef dynamicTypeRef, D data) {
        Intrinsics.checkNotNullParameter(dynamicTypeRef, "dynamicTypeRef");
        return (FirTypeRef) transformElement(dynamicTypeRef, data);
    }

    public FirTypeRef transformFunctionTypeRef(FirFunctionTypeRef functionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        return (FirTypeRef) transformElement(functionTypeRef, data);
    }

    public FirTypeRef transformIntersectionTypeRef(FirIntersectionTypeRef intersectionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(intersectionTypeRef, "intersectionTypeRef");
        return (FirTypeRef) transformElement(intersectionTypeRef, data);
    }

    public FirTypeRef transformImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, D data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        return (FirTypeRef) transformElement(implicitTypeRef, data);
    }

    public FirContractElementDeclaration transformContractElementDeclaration(FirContractElementDeclaration contractElementDeclaration, D data) {
        Intrinsics.checkNotNullParameter(contractElementDeclaration, "contractElementDeclaration");
        return (FirContractElementDeclaration) transformElement(contractElementDeclaration, data);
    }

    public FirContractElementDeclaration transformEffectDeclaration(FirEffectDeclaration effectDeclaration, D data) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        return (FirContractElementDeclaration) transformElement(effectDeclaration, data);
    }

    public FirContractDescription transformContractDescription(FirContractDescription contractDescription, D data) {
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        return (FirContractDescription) transformElement(contractDescription, data);
    }

    public FirContractDescription transformLegacyRawContractDescription(FirLegacyRawContractDescription legacyRawContractDescription, D data) {
        Intrinsics.checkNotNullParameter(legacyRawContractDescription, "legacyRawContractDescription");
        return (FirContractDescription) transformElement(legacyRawContractDescription, data);
    }

    public FirContractDescription transformRawContractDescription(FirRawContractDescription rawContractDescription, D data) {
        Intrinsics.checkNotNullParameter(rawContractDescription, "rawContractDescription");
        return (FirContractDescription) transformElement(rawContractDescription, data);
    }

    public FirContractDescription transformResolvedContractDescription(FirResolvedContractDescription resolvedContractDescription, D data) {
        Intrinsics.checkNotNullParameter(resolvedContractDescription, "resolvedContractDescription");
        return (FirContractDescription) transformElement(resolvedContractDescription, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitElement(FirElement element, D data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return transformElement(element, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnnotationContainer(FirAnnotationContainer annotationContainer, D data) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        return transformAnnotationContainer(annotationContainer, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeRef(FirTypeRef typeRef, D data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        return transformTypeRef(typeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitReference(FirReference reference, D data) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        return transformReference(reference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitLabel(FirLabel label, D data) {
        Intrinsics.checkNotNullParameter(label, "label");
        return transformLabel(label, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvable(FirResolvable resolvable, D data) {
        Intrinsics.checkNotNullParameter(resolvable, "resolvable");
        return transformResolvable(resolvable, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTargetElement(FirTargetElement targetElement, D data) {
        Intrinsics.checkNotNullParameter(targetElement, "targetElement");
        return transformTargetElement(targetElement, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDeclarationStatus(FirDeclarationStatus declarationStatus, D data) {
        Intrinsics.checkNotNullParameter(declarationStatus, "declarationStatus");
        return transformDeclarationStatus(declarationStatus, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedDeclarationStatus(FirResolvedDeclarationStatus resolvedDeclarationStatus, D data) {
        Intrinsics.checkNotNullParameter(resolvedDeclarationStatus, "resolvedDeclarationStatus");
        return transformResolvedDeclarationStatus(resolvedDeclarationStatus, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitControlFlowGraphOwner(FirControlFlowGraphOwner controlFlowGraphOwner, D data) {
        Intrinsics.checkNotNullParameter(controlFlowGraphOwner, "controlFlowGraphOwner");
        return transformControlFlowGraphOwner(controlFlowGraphOwner, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitStatement(FirStatement statement, D data) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        return transformStatement(statement, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitExpression(FirExpression expression, D data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return transformExpression(expression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitLazyExpression(FirLazyExpression lazyExpression, D data) {
        Intrinsics.checkNotNullParameter(lazyExpression, "lazyExpression");
        return transformLazyExpression(lazyExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitContextReceiver(FirContextReceiver contextReceiver, D data) {
        Intrinsics.checkNotNullParameter(contextReceiver, "contextReceiver");
        return transformContextReceiver(contextReceiver, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitElementWithResolveState(FirElementWithResolveState elementWithResolveState, D data) {
        Intrinsics.checkNotNullParameter(elementWithResolveState, "elementWithResolveState");
        return transformElementWithResolveState(elementWithResolveState, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitFileAnnotationsContainer(FirFileAnnotationsContainer fileAnnotationsContainer, D data) {
        Intrinsics.checkNotNullParameter(fileAnnotationsContainer, "fileAnnotationsContainer");
        return transformFileAnnotationsContainer(fileAnnotationsContainer, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDeclaration(FirDeclaration declaration, D data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return transformDeclaration(declaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeParameterRefsOwner(FirTypeParameterRefsOwner typeParameterRefsOwner, D data) {
        Intrinsics.checkNotNullParameter(typeParameterRefsOwner, "typeParameterRefsOwner");
        return transformTypeParameterRefsOwner(typeParameterRefsOwner, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeParametersOwner(FirTypeParametersOwner typeParametersOwner, D data) {
        Intrinsics.checkNotNullParameter(typeParametersOwner, "typeParametersOwner");
        return transformTypeParametersOwner(typeParametersOwner, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitMemberDeclaration(FirMemberDeclaration memberDeclaration, D data) {
        Intrinsics.checkNotNullParameter(memberDeclaration, "memberDeclaration");
        return transformMemberDeclaration(memberDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, D data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        return transformAnonymousInitializer(anonymousInitializer, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitCallableDeclaration(FirCallableDeclaration callableDeclaration, D data) {
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        return transformCallableDeclaration(callableDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeParameterRef(FirTypeParameterRef typeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(typeParameterRef, "typeParameterRef");
        return transformTypeParameterRef(typeParameterRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeParameter(FirTypeParameter typeParameter, D data) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return transformTypeParameter(typeParameter, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitConstructedClassTypeParameterRef(FirConstructedClassTypeParameterRef constructedClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(constructedClassTypeParameterRef, "constructedClassTypeParameterRef");
        return transformConstructedClassTypeParameterRef(constructedClassTypeParameterRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitOuterClassTypeParameterRef(FirOuterClassTypeParameterRef outerClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(outerClassTypeParameterRef, "outerClassTypeParameterRef");
        return transformOuterClassTypeParameterRef(outerClassTypeParameterRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitVariable(FirVariable variable, D data) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return transformVariable(variable, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitValueParameter(FirValueParameter valueParameter, D data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return transformValueParameter(valueParameter, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitReceiverParameter(FirReceiverParameter receiverParameter, D data) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        return transformReceiverParameter(receiverParameter, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitProperty(FirProperty property, D data) {
        Intrinsics.checkNotNullParameter(property, "property");
        return transformProperty(property, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitField(FirField field, D data) {
        Intrinsics.checkNotNullParameter(field, "field");
        return transformField(field, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitEnumEntry(FirEnumEntry enumEntry, D data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return transformEnumEntry(enumEntry, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitFunctionTypeParameter(FirFunctionTypeParameter functionTypeParameter, D data) {
        Intrinsics.checkNotNullParameter(functionTypeParameter, "functionTypeParameter");
        return transformFunctionTypeParameter(functionTypeParameter, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitClassLikeDeclaration(FirClassLikeDeclaration classLikeDeclaration, D data) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        return transformClassLikeDeclaration(classLikeDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitClass(FirClass klass, D data) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return transformClass(klass, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitRegularClass(FirRegularClass regularClass, D data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        return transformRegularClass(regularClass, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeAlias(FirTypeAlias typeAlias, D data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        return transformTypeAlias(typeAlias, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitFunction(FirFunction function, D data) {
        Intrinsics.checkNotNullParameter(function, "function");
        return transformFunction(function, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitContractDescriptionOwner(FirContractDescriptionOwner contractDescriptionOwner, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionOwner, "contractDescriptionOwner");
        return transformContractDescriptionOwner(contractDescriptionOwner, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitSimpleFunction(FirSimpleFunction simpleFunction, D data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        return transformSimpleFunction(simpleFunction, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitPropertyAccessor(FirPropertyAccessor propertyAccessor, D data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        return transformPropertyAccessor(propertyAccessor, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitBackingField(FirBackingField backingField, D data) {
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        return transformBackingField(backingField, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitConstructor(FirConstructor constructor, D data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return transformConstructor(constructor, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitFile(FirFile file, D data) {
        Intrinsics.checkNotNullParameter(file, "file");
        return transformFile(file, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitScript(FirScript script, D data) {
        Intrinsics.checkNotNullParameter(script, "script");
        return transformScript(script, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitCodeFragment(FirCodeFragment codeFragment, D data) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        return transformCodeFragment(codeFragment, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitPackageDirective(FirPackageDirective packageDirective, D data) {
        Intrinsics.checkNotNullParameter(packageDirective, "packageDirective");
        return transformPackageDirective(packageDirective, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnonymousFunction(FirAnonymousFunction anonymousFunction, D data) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        return transformAnonymousFunction(anonymousFunction, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, D data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        return transformAnonymousFunctionExpression(anonymousFunctionExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnonymousObject(FirAnonymousObject anonymousObject, D data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        return transformAnonymousObject(anonymousObject, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, D data) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        return transformAnonymousObjectExpression(anonymousObjectExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDiagnosticHolder(FirDiagnosticHolder diagnosticHolder, D data) {
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        return transformDiagnosticHolder(diagnosticHolder, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitImport(FirImport firImport, D data) {
        Intrinsics.checkNotNullParameter(firImport, "import");
        return transformImport(firImport, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedImport(FirResolvedImport resolvedImport, D data) {
        Intrinsics.checkNotNullParameter(resolvedImport, "resolvedImport");
        return transformResolvedImport(resolvedImport, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorImport(FirErrorImport errorImport, D data) {
        Intrinsics.checkNotNullParameter(errorImport, "errorImport");
        return transformErrorImport(errorImport, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitLoop(FirLoop loop, D data) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return transformLoop(loop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorLoop(FirErrorLoop errorLoop, D data) {
        Intrinsics.checkNotNullParameter(errorLoop, "errorLoop");
        return transformErrorLoop(errorLoop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDoWhileLoop(FirDoWhileLoop doWhileLoop, D data) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        return transformDoWhileLoop(doWhileLoop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitWhileLoop(FirWhileLoop whileLoop, D data) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        return transformWhileLoop(whileLoop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitBlock(FirBlock block, D data) {
        Intrinsics.checkNotNullParameter(block, "block");
        return transformBlock(block, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitLazyBlock(FirLazyBlock lazyBlock, D data) {
        Intrinsics.checkNotNullParameter(lazyBlock, "lazyBlock");
        return transformLazyBlock(lazyBlock, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, D data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        return transformBinaryLogicExpression(binaryLogicExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final <E extends FirTargetElement> FirElement visitJump(FirJump<E> jump, D data) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        return transformJump(jump, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitLoopJump(FirLoopJump loopJump, D data) {
        Intrinsics.checkNotNullParameter(loopJump, "loopJump");
        return transformLoopJump(loopJump, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitBreakExpression(FirBreakExpression breakExpression, D data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        return transformBreakExpression(breakExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitContinueExpression(FirContinueExpression continueExpression, D data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        return transformContinueExpression(continueExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitCatch(FirCatch firCatch, D data) {
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        return transformCatch(firCatch, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTryExpression(FirTryExpression tryExpression, D data) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        return transformTryExpression(tryExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final <T> FirElement visitConstExpression(FirConstExpression<T> constExpression, D data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        return transformConstExpression(constExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeProjection(FirTypeProjection typeProjection, D data) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        return transformTypeProjection(typeProjection, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitStarProjection(FirStarProjection starProjection, D data) {
        Intrinsics.checkNotNullParameter(starProjection, "starProjection");
        return transformStarProjection(starProjection, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitPlaceholderProjection(FirPlaceholderProjection placeholderProjection, D data) {
        Intrinsics.checkNotNullParameter(placeholderProjection, "placeholderProjection");
        return transformPlaceholderProjection(placeholderProjection, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeProjectionWithVariance(FirTypeProjectionWithVariance typeProjectionWithVariance, D data) {
        Intrinsics.checkNotNullParameter(typeProjectionWithVariance, "typeProjectionWithVariance");
        return transformTypeProjectionWithVariance(typeProjectionWithVariance, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitArgumentList(FirArgumentList argumentList, D data) {
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        return transformArgumentList(argumentList, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitCall(FirCall call, D data) {
        Intrinsics.checkNotNullParameter(call, "call");
        return transformCall(call, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnnotation(FirAnnotation annotation, D data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return transformAnnotation(annotation, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnnotationCall(FirAnnotationCall annotationCall, D data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        return transformAnnotationCall(annotationCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAnnotationArgumentMapping(FirAnnotationArgumentMapping annotationArgumentMapping, D data) {
        Intrinsics.checkNotNullParameter(annotationArgumentMapping, "annotationArgumentMapping");
        return transformAnnotationArgumentMapping(annotationArgumentMapping, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorAnnotationCall(FirErrorAnnotationCall errorAnnotationCall, D data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        return transformErrorAnnotationCall(errorAnnotationCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitComparisonExpression(FirComparisonExpression comparisonExpression, D data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        return transformComparisonExpression(comparisonExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        return transformTypeOperatorCall(typeOperatorCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAssignmentOperatorStatement(FirAssignmentOperatorStatement assignmentOperatorStatement, D data) {
        Intrinsics.checkNotNullParameter(assignmentOperatorStatement, "assignmentOperatorStatement");
        return transformAssignmentOperatorStatement(assignmentOperatorStatement, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitIncrementDecrementExpression(FirIncrementDecrementExpression incrementDecrementExpression, D data) {
        Intrinsics.checkNotNullParameter(incrementDecrementExpression, "incrementDecrementExpression");
        return transformIncrementDecrementExpression(incrementDecrementExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        return transformEqualityOperatorCall(equalityOperatorCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitWhenExpression(FirWhenExpression whenExpression, D data) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        return transformWhenExpression(whenExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitWhenBranch(FirWhenBranch whenBranch, D data) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        return transformWhenBranch(whenBranch, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitContextReceiverArgumentListOwner(FirContextReceiverArgumentListOwner contextReceiverArgumentListOwner, D data) {
        Intrinsics.checkNotNullParameter(contextReceiverArgumentListOwner, "contextReceiverArgumentListOwner");
        return transformContextReceiverArgumentListOwner(contextReceiverArgumentListOwner, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, D data) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        return transformCheckNotNullCall(checkNotNullCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitElvisExpression(FirElvisExpression elvisExpression, D data) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        return transformElvisExpression(elvisExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitArrayLiteral(FirArrayLiteral arrayLiteral, D data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        return transformArrayLiteral(arrayLiteral, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall, D data) {
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        return transformAugmentedArraySetCall(augmentedArraySetCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitClassReferenceExpression(FirClassReferenceExpression classReferenceExpression, D data) {
        Intrinsics.checkNotNullParameter(classReferenceExpression, "classReferenceExpression");
        return transformClassReferenceExpression(classReferenceExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorExpression(FirErrorExpression errorExpression, D data) {
        Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
        return transformErrorExpression(errorExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorFunction(FirErrorFunction errorFunction, D data) {
        Intrinsics.checkNotNullParameter(errorFunction, "errorFunction");
        return transformErrorFunction(errorFunction, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorProperty(FirErrorProperty errorProperty, D data) {
        Intrinsics.checkNotNullParameter(errorProperty, "errorProperty");
        return transformErrorProperty(errorProperty, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDanglingModifierList(FirDanglingModifierList danglingModifierList, D data) {
        Intrinsics.checkNotNullParameter(danglingModifierList, "danglingModifierList");
        return transformDanglingModifierList(danglingModifierList, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        return transformQualifiedAccessExpression(qualifiedAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitQualifiedErrorAccessExpression(FirQualifiedErrorAccessExpression qualifiedErrorAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(qualifiedErrorAccessExpression, "qualifiedErrorAccessExpression");
        return transformQualifiedErrorAccessExpression(qualifiedErrorAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        return transformPropertyAccessExpression(propertyAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitFunctionCall(FirFunctionCall functionCall, D data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        return transformFunctionCall(functionCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall integerLiteralOperatorCall, D data) {
        Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
        return transformIntegerLiteralOperatorCall(integerLiteralOperatorCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitImplicitInvokeCall(FirImplicitInvokeCall implicitInvokeCall, D data) {
        Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
        return transformImplicitInvokeCall(implicitInvokeCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, D data) {
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        return transformDelegatedConstructorCall(delegatedConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitMultiDelegatedConstructorCall(FirMultiDelegatedConstructorCall multiDelegatedConstructorCall, D data) {
        Intrinsics.checkNotNullParameter(multiDelegatedConstructorCall, "multiDelegatedConstructorCall");
        return transformMultiDelegatedConstructorCall(multiDelegatedConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitComponentCall(FirComponentCall componentCall, D data) {
        Intrinsics.checkNotNullParameter(componentCall, "componentCall");
        return transformComponentCall(componentCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, D data) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        return transformCallableReferenceAccess(callableReferenceAccess, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, D data) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        return transformThisReceiverExpression(thisReceiverExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitInaccessibleReceiverExpression(FirInaccessibleReceiverExpression inaccessibleReceiverExpression, D data) {
        Intrinsics.checkNotNullParameter(inaccessibleReceiverExpression, "inaccessibleReceiverExpression");
        return transformInaccessibleReceiverExpression(inaccessibleReceiverExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitSmartCastExpression(FirSmartCastExpression smartCastExpression, D data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        return transformSmartCastExpression(smartCastExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitSafeCallExpression(FirSafeCallExpression safeCallExpression, D data) {
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        return transformSafeCallExpression(safeCallExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject, D data) {
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        return transformCheckedSafeCallSubject(checkedSafeCallSubject, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitGetClassCall(FirGetClassCall getClassCall, D data) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        return transformGetClassCall(getClassCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitWrappedExpression(FirWrappedExpression wrappedExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedExpression, "wrappedExpression");
        return transformWrappedExpression(wrappedExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitWrappedArgumentExpression(FirWrappedArgumentExpression wrappedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        return transformWrappedArgumentExpression(wrappedArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitLambdaArgumentExpression(FirLambdaArgumentExpression lambdaArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
        return transformLambdaArgumentExpression(lambdaArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitSpreadArgumentExpression(FirSpreadArgumentExpression spreadArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
        return transformSpreadArgumentExpression(spreadArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        return transformNamedArgumentExpression(namedArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, D data) {
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        return transformVarargArgumentsExpression(varargArgumentsExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedQualifier(FirResolvedQualifier resolvedQualifier, D data) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        return transformResolvedQualifier(resolvedQualifier, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorResolvedQualifier(FirErrorResolvedQualifier errorResolvedQualifier, D data) {
        Intrinsics.checkNotNullParameter(errorResolvedQualifier, "errorResolvedQualifier");
        return transformErrorResolvedQualifier(errorResolvedQualifier, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedReifiedParameterReference(FirResolvedReifiedParameterReference resolvedReifiedParameterReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedReifiedParameterReference, "resolvedReifiedParameterReference");
        return transformResolvedReifiedParameterReference(resolvedReifiedParameterReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitReturnExpression(FirReturnExpression returnExpression, D data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        return transformReturnExpression(returnExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, D data) {
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        return transformStringConcatenationCall(stringConcatenationCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitThrowExpression(FirThrowExpression throwExpression, D data) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        return transformThrowExpression(throwExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitVariableAssignment(FirVariableAssignment variableAssignment, D data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        return transformVariableAssignment(variableAssignment, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression, D data) {
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        return transformWhenSubjectExpression(whenSubjectExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression, D data) {
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        return transformDesugaredAssignmentValueReferenceExpression(desugaredAssignmentValueReferenceExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitWrappedDelegateExpression(FirWrappedDelegateExpression wrappedDelegateExpression, D data) {
        Intrinsics.checkNotNullParameter(wrappedDelegateExpression, "wrappedDelegateExpression");
        return transformWrappedDelegateExpression(wrappedDelegateExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitEnumEntryDeserializedAccessExpression(FirEnumEntryDeserializedAccessExpression enumEntryDeserializedAccessExpression, D data) {
        Intrinsics.checkNotNullParameter(enumEntryDeserializedAccessExpression, "enumEntryDeserializedAccessExpression");
        return transformEnumEntryDeserializedAccessExpression(enumEntryDeserializedAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitNamedReference(FirNamedReference namedReference, D data) {
        Intrinsics.checkNotNullParameter(namedReference, "namedReference");
        return transformNamedReference(namedReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitNamedReferenceWithCandidateBase(FirNamedReferenceWithCandidateBase namedReferenceWithCandidateBase, D data) {
        Intrinsics.checkNotNullParameter(namedReferenceWithCandidateBase, "namedReferenceWithCandidateBase");
        return transformNamedReferenceWithCandidateBase(namedReferenceWithCandidateBase, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorNamedReference(FirErrorNamedReference errorNamedReference, D data) {
        Intrinsics.checkNotNullParameter(errorNamedReference, "errorNamedReference");
        return transformErrorNamedReference(errorNamedReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitFromMissingDependenciesNamedReference(FirFromMissingDependenciesNamedReference fromMissingDependenciesNamedReference, D data) {
        Intrinsics.checkNotNullParameter(fromMissingDependenciesNamedReference, "fromMissingDependenciesNamedReference");
        return transformFromMissingDependenciesNamedReference(fromMissingDependenciesNamedReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitSuperReference(FirSuperReference superReference, D data) {
        Intrinsics.checkNotNullParameter(superReference, "superReference");
        return transformSuperReference(superReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitThisReference(FirThisReference thisReference, D data) {
        Intrinsics.checkNotNullParameter(thisReference, "thisReference");
        return transformThisReference(thisReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitControlFlowGraphReference(FirControlFlowGraphReference controlFlowGraphReference, D data) {
        Intrinsics.checkNotNullParameter(controlFlowGraphReference, "controlFlowGraphReference");
        return transformControlFlowGraphReference(controlFlowGraphReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedNamedReference(FirResolvedNamedReference resolvedNamedReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedNamedReference, "resolvedNamedReference");
        return transformResolvedNamedReference(resolvedNamedReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedErrorReference(FirResolvedErrorReference resolvedErrorReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedErrorReference, "resolvedErrorReference");
        return transformResolvedErrorReference(resolvedErrorReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDelegateFieldReference(FirDelegateFieldReference delegateFieldReference, D data) {
        Intrinsics.checkNotNullParameter(delegateFieldReference, "delegateFieldReference");
        return transformDelegateFieldReference(delegateFieldReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitBackingFieldReference(FirBackingFieldReference backingFieldReference, D data) {
        Intrinsics.checkNotNullParameter(backingFieldReference, "backingFieldReference");
        return transformBackingFieldReference(backingFieldReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedCallableReference(FirResolvedCallableReference resolvedCallableReference, D data) {
        Intrinsics.checkNotNullParameter(resolvedCallableReference, "resolvedCallableReference");
        return transformResolvedCallableReference(resolvedCallableReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef, D data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        return transformResolvedTypeRef(resolvedTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitErrorTypeRef(FirErrorTypeRef errorTypeRef, D data) {
        Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
        return transformErrorTypeRef(errorTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitTypeRefWithNullability(FirTypeRefWithNullability typeRefWithNullability, D data) {
        Intrinsics.checkNotNullParameter(typeRefWithNullability, "typeRefWithNullability");
        return transformTypeRefWithNullability(typeRefWithNullability, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitUserTypeRef(FirUserTypeRef userTypeRef, D data) {
        Intrinsics.checkNotNullParameter(userTypeRef, "userTypeRef");
        return transformUserTypeRef(userTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitDynamicTypeRef(FirDynamicTypeRef dynamicTypeRef, D data) {
        Intrinsics.checkNotNullParameter(dynamicTypeRef, "dynamicTypeRef");
        return transformDynamicTypeRef(dynamicTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitFunctionTypeRef(FirFunctionTypeRef functionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        return transformFunctionTypeRef(functionTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitIntersectionTypeRef(FirIntersectionTypeRef intersectionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(intersectionTypeRef, "intersectionTypeRef");
        return transformIntersectionTypeRef(intersectionTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, D data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        return transformImplicitTypeRef(implicitTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitContractElementDeclaration(FirContractElementDeclaration contractElementDeclaration, D data) {
        Intrinsics.checkNotNullParameter(contractElementDeclaration, "contractElementDeclaration");
        return transformContractElementDeclaration(contractElementDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitEffectDeclaration(FirEffectDeclaration effectDeclaration, D data) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        return transformEffectDeclaration(effectDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitContractDescription(FirContractDescription contractDescription, D data) {
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        return transformContractDescription(contractDescription, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitLegacyRawContractDescription(FirLegacyRawContractDescription legacyRawContractDescription, D data) {
        Intrinsics.checkNotNullParameter(legacyRawContractDescription, "legacyRawContractDescription");
        return transformLegacyRawContractDescription(legacyRawContractDescription, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitRawContractDescription(FirRawContractDescription rawContractDescription, D data) {
        Intrinsics.checkNotNullParameter(rawContractDescription, "rawContractDescription");
        return transformRawContractDescription(rawContractDescription, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public final FirElement visitResolvedContractDescription(FirResolvedContractDescription resolvedContractDescription, D data) {
        Intrinsics.checkNotNullParameter(resolvedContractDescription, "resolvedContractDescription");
        return transformResolvedContractDescription(resolvedContractDescription, data);
    }
}
