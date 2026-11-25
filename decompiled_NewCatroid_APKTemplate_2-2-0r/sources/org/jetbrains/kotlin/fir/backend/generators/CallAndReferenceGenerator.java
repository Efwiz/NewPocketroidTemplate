package org.jetbrains.kotlin.fir.backend.generators;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrAnnotationsFromPluginRegistrar;
import org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns;
import org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrConversionScope;
import org.jetbrains.kotlin.fir.backend.Fir2IrConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisitor;
import org.jetbrains.kotlin.fir.backend.InjectedValue;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationCallBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirDelegateFieldReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.ImplicitIntegerCoercionKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.ArgumentsKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticFunctionSymbol;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.DeclarationApproximationUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorCallExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetEnumValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrLocalDelegatedPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: CallAndReferenceGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Î\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020fH\u0002J\u0010\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020iH\u0002J\"\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020n2\b\u0010o\u001a\u0004\u0018\u00010p2\u0006\u0010q\u001a\u00020rH\u0002J\u0015\u0010s\u001a\u00020l2\u0006\u0010t\u001a\u00020uH\u0000¢\u0006\u0002\bvJ!\u0010s\u001a\u0004\u0018\u00010l2\u0006\u0010t\u001a\u00020u2\b\u0010w\u001a\u0004\u0018\u00010xH\u0000¢\u0006\u0002\bvJA\u0010y\u001a\u00020l2\u0006\u0010z\u001a\u00020`2\u0006\u0010{\u001a\u00020|2\b\u0010}\u001a\u0004\u0018\u00010l2\n\b\u0002\u0010^\u001a\u0004\u0018\u00010_2\b\b\u0002\u0010~\u001a\u00020\u007f2\t\b\u0002\u0010\u0080\u0001\u001a\u00020\u007fJT\u0010\u0081\u0001\u001a\u00020l2\u0006\u0010z\u001a\u00020`2\b\u0010}\u001a\u0004\u0018\u00010l2\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\f\u0010\u0086\u0001\u001a\u0007\u0012\u0002\b\u00030\u0087\u00012\n\b\u0002\u0010^\u001a\u0004\u0018\u00010_2\t\b\u0002\u0010\u0080\u0001\u001a\u00020\u007fH\u0002J\"\u0010\u0088\u0001\u001a\u00020l2\u0006\u0010w\u001a\u00020x2\b\u0010}\u001a\u0004\u0018\u00010l2\u0007\u0010\u0089\u0001\u001a\u00020\u007fJ\u0011\u0010\u008a\u0001\u001a\u00020l2\b\u0010\u008b\u0001\u001a\u00030\u008c\u0001J\u001b\u0010\u008d\u0001\u001a\u00020l2\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\b\u0010}\u001a\u0004\u0018\u00010lJH\u0010\u0090\u0001\u001a\u00020l2\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\b\u0010}\u001a\u0004\u0018\u00010l2\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\f\u0010\u0086\u0001\u001a\u0007\u0012\u0002\b\u00030\u0087\u00012\u0007\u0010\u0091\u0001\u001a\u00020lH\u0002J=\u0010\u0092\u0001\u001a,\u0012\f\u0012\n\u0012\u0004\u0012\u00020p\u0018\u00010>\u0012\u0013\u0012\u0011\u0012\u0004\u0012\u00020n\u0012\u0004\u0012\u00020p\u0018\u00010\u0094\u0001\u0012\u0004\u0012\u00020r0\u0093\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0002J6\u0010\u0097\u0001\u001a\u00030\u0098\u00012\b\u0010\u0099\u0001\u001a\u00030\u009a\u00012\b\u0010\u009b\u0001\u001a\u00030\u009a\u00012\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\f\b\u0002\u0010\u0082\u0001\u001a\u0005\u0018\u00010\u0083\u0001H\u0002J%\u0010\u009c\u0001\u001a\u0004\u0018\u00010l2\b\u0010\u009d\u0001\u001a\u00030\u009e\u00012\b\u0010\u0084\u0001\u001a\u00030\u0085\u0001H\u0000¢\u0006\u0003\b\u009f\u0001J(\u0010 \u0001\u001a\u0004\u0018\u00010l2\b\u0010\u009d\u0001\u001a\u00030\u009e\u00012\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0007\u0010\u0091\u0001\u001a\u00020lH\u0002J(\u0010¡\u0001\u001a\u00020\u007f2\u000e\u0010¢\u0001\u001a\t\u0012\u0004\u0012\u00020p0£\u00012\r\u0010¤\u0001\u001a\b\u0012\u0004\u0012\u00020p0>H\u0002JS\u0010¥\u0001\u001a\u00020l*\u0007\u0012\u0002\b\u00030¦\u00012\u0014\u0010§\u0001\u001a\u000f\u0012\u0004\u0012\u00020n\u0012\u0004\u0012\u00020p0\u0094\u00012\r\u0010¤\u0001\u001a\b\u0012\u0004\u0012\u00020p0>2\u0006\u0010q\u001a\u00020r2\b\u0010¨\u0001\u001a\u00030\u009a\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0002J\u001f\u0010©\u0001\u001a\u00020l*\u00020l2\u0006\u0010m\u001a\u00020n2\b\u0010o\u001a\u0004\u0018\u00010pH\u0002J\u001f\u0010ª\u0001\u001a\u00020l*\u00020l2\n\u0010«\u0001\u001a\u0005\u0018\u00010¬\u0001H\u0000¢\u0006\u0003\b\u00ad\u0001J\u001f\u0010®\u0001\u001a\u00020l*\u00020l2\u0006\u0010m\u001a\u00020n2\b\u0010o\u001a\u0004\u0018\u00010pH\u0002J\u001f\u0010¯\u0001\u001a\u00020l*\u00020l2\u0006\u0010z\u001a\u00020`2\b\u0010}\u001a\u0004\u0018\u00010lH\u0002J1\u0010°\u0001\u001a\u00020l*\u00020l2\u0010\u0010±\u0001\u001a\u000b\u0012\u0005\u0012\u00030²\u0001\u0018\u00010>2\u0010\u0010³\u0001\u001a\u000b\u0012\u0005\u0012\u00030´\u0001\u0018\u00010>H\u0002J\u001c\u0010°\u0001\u001a\u00020l*\u00020l2\u0007\u0010µ\u0001\u001a\u00020`H\u0000¢\u0006\u0003\b¶\u0001J\u001c\u0010·\u0001\u001a\u0004\u0018\u00010r*\u00030¸\u00012\n\u0010¹\u0001\u001a\u0005\u0018\u00010º\u0001H\u0002J\u0019\u0010»\u0001\u001a\u0004\u0018\u00010l*\u00020`2\b\u0010}\u001a\u0004\u0018\u00010lH\u0002J\u0017\u0010¼\u0001\u001a\u00020l*\u00020`2\b\u0010}\u001a\u0004\u0018\u00010lH\u0002J\u0019\u0010½\u0001\u001a\u0004\u0018\u00010l*\u00020`2\b\u0010}\u001a\u0004\u0018\u00010lH\u0002J(\u0010¾\u0001\u001a\u0004\u0018\u00010l*\u00020`2\b\u0010}\u001a\u0004\u0018\u00010l2\u0007\u0010¿\u0001\u001a\u00020\u007fH\u0000¢\u0006\u0003\bÀ\u0001J\u0012\u0010Á\u0001\u001a\u00020\u007f*\u0007\u0012\u0002\b\u00030Â\u0001H\u0002J\u0011\u0010Ã\u0001\u001a\u0005\u0018\u00010Ä\u0001*\u00030Å\u0001H\u0002J\u001f\u0010Æ\u0001\u001a\u00030\u009a\u0001*\u0007\u0012\u0002\b\u00030¦\u00012\n\u0010«\u0001\u001a\u0005\u0018\u00010¬\u0001H\u0002J\u0010\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u0001*\u00020nH\u0002J\u0011\u0010É\u0001\u001a\u0005\u0018\u00010Ê\u0001*\u00030\u008c\u0001H\u0002J%\u0010Ë\u0001\u001a\t\u0012\u0005\u0012\u00030²\u00010>*\t\u0012\u0005\u0012\u00030²\u00010>2\b\u0010Ì\u0001\u001a\u00030Í\u0001H\u0002J\u000e\u0010Î\u0001\u001a\u00030\u0083\u0001*\u00020iH\u0002J\u000e\u0010Î\u0001\u001a\u00030\u0083\u0001*\u00020|H\u0002J\u001a\u0010Ï\u0001\u001a\u0005\u0018\u00010Ð\u0001*\u00020`2\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0000X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\u001eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010!\u001a\u00020\"X\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0012\u0010%\u001a\u00020&X\u0096\u0005¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0012\u0010)\u001a\u00020*X\u0096\u0005¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0012\u0010-\u001a\u00020.X\u0096\u0005¢\u0006\u0006\u001a\u0004\b/\u00100R\u0012\u00101\u001a\u000202X\u0096\u0005¢\u0006\u0006\u001a\u0004\b3\u00104R\u0012\u00105\u001a\u000206X\u0096\u0005¢\u0006\u0006\u001a\u0004\b7\u00108R\u0012\u00109\u001a\u00020:X\u0096\u0005¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0018\u0010=\u001a\b\u0012\u0004\u0012\u00020?0>X\u0096\u0005¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0012\u0010B\u001a\u00020CX\u0096\u0005¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0012\u0010F\u001a\u00020GX\u0096\u0005¢\u0006\u0006\u001a\u0004\bH\u0010IR\u0012\u0010J\u001a\u00020KX\u0096\u0005¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0012\u0010N\u001a\u00020OX\u0096\u0005¢\u0006\u0006\u001a\u0004\bP\u0010QR\u0012\u0010R\u001a\u00020SX\u0096\u0005¢\u0006\u0006\u001a\u0004\bT\u0010UR\u0012\u0010V\u001a\u00020WX\u0096\u0005¢\u0006\u0006\u001a\u0004\bX\u0010YR\u0012\u0010Z\u001a\u00020[X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\\\u0010]R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010^\u001a\u0004\u0018\u00010_*\u00020`8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\ba\u0010bR\u001a\u0010^\u001a\u0004\u0018\u00010_*\u00020c8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\ba\u0010d¨\u0006Ñ\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "visitor", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisitor;", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisitor;Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;)V", "adapterGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AdapterGenerator;", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "dynamicOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "getDynamicOperator", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "approximateFunctionReferenceParameterType", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "typeProjection", "approximateFunctionReferenceType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "kotlinType", "convertArgument", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "parameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "convertToGetObject", "qualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "convertToGetObject$fir2ir", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "convertToIrCall", "qualifiedAccess", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "explicitReceiverExpression", "variableAsFunctionMode", "", "noArguments", "convertToIrCallForDynamic", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "convertToIrCallableReference", "isDelegate", "convertToIrConstructorCall", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "convertToIrSetCall", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "convertToIrSetCallForDynamic", "assignedValue", "extractArgumentsMapping", "Lkotlin/Triple;", "", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "generateErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "startOffset", "", "endOffset", "injectGetValueCall", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "injectGetValueCall$fir2ir", "injectSetValueCall", "needArgumentReordering", "parametersInActualOrder", "", "valueParameters", "applyArgumentsWithReorderingIfNeeded", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "argumentMapping", "contextReceiverCount", "applyAssigningArrayElementsToVarargInNamedForm", "applyCallArguments", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "applyCallArguments$fir2ir", "applyImplicitIntegerCoercionIfNeeded", "applyReceivers", "applyTypeArguments", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "access", "applyTypeArguments$fir2ir", "buildSubstitutorByCalledFunction", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "findIrDispatchReceiver", "findIrDynamicReceiver", "findIrExtensionReceiver", "findIrReceiver", "isDispatch", "findIrReceiver$fir2ir", "isFunctionFromAny", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "overriddenBackingFieldOrNull", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "putContextReceiverArguments", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "toAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "toExpandedTypeArguments", "typeAliasSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "toIrType", "tryConvertToSamConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallAndReferenceGenerator implements Fir2IrComponents {
    private final AdapterGenerator adapterGenerator;
    private final Fir2IrComponents components;
    private final Fir2IrConversionScope conversionScope;
    private final Fir2IrVisitor visitor;

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public AnnotationGenerator getAnnotationGenerator() {
        return this.components.getAnnotationGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsFromPluginRegistrar() {
        return this.components.getAnnotationsFromPluginRegistrar();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrBuiltIns getBuiltIns() {
        return this.components.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public CallAndReferenceGenerator getCallGenerator() {
        return this.components.getCallGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrClassifierStorage getClassifierStorage() {
        return this.components.getClassifierStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConfiguration getConfiguration() {
        return this.components.getConfiguration();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConverter getConverter() {
        return this.components.getConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrDeclarationStorage getDeclarationStorage() {
        return this.components.getDeclarationStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public DelegatedMemberGenerator getDelegatedMemberGenerator() {
        return this.components.getDelegatedMemberGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrExtensions getExtensions() {
        return this.components.getExtensions();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FakeOverrideGenerator getFakeOverrideGenerator() {
        return this.components.getFakeOverrideGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrBuiltInsOverFir getIrBuiltIns() {
        return this.components.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrFactory getIrFactory() {
        return this.components.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public List<IrProvider> getIrProviders() {
        return this.components.getIrProviders();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrLock getLock() {
        return this.components.getLock();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public ScopeSession getScopeSession() {
        return this.components.getScopeSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirSession getSession() {
        return this.components.getSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirBasedSignatureComposer getSignatureComposer() {
        return this.components.getSignatureComposer();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public SymbolTable getSymbolTable() {
        return this.components.getSymbolTable();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrTypeConverter getTypeConverter() {
        return this.components.getTypeConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrVisibilityConverter getVisibilityConverter() {
        return this.components.getVisibilityConverter();
    }

    public CallAndReferenceGenerator(Fir2IrComponents components, Fir2IrVisitor visitor, Fir2IrConversionScope conversionScope) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        this.components = components;
        this.visitor = visitor;
        this.conversionScope = conversionScope;
        this.adapterGenerator = new AdapterGenerator(components, conversionScope);
    }

    private final IrType toIrType(FirTypeRef firTypeRef) {
        return getTypeConverter().toIrType(firTypeRef, this.conversionScope.defaultConversionTypeOrigin());
    }

    private final IrType toIrType(ConeKotlinType coneKotlinType) {
        return Fir2IrTypeConverter.toIrType$default(getTypeConverter(), coneKotlinType, this.conversionScope.defaultConversionTypeOrigin(), null, false, false, false, 30, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrExpression convertToIrCallableReference(FirCallableReferenceAccess callableReferenceAccess, IrExpression explicitReceiverExpression, boolean isDelegate) {
        int startOffset;
        Integer numStartOffsetSkippingComments;
        IrErrorCallExpressionImpl irErrorCallExpressionImpl;
        List<IrTypeParameter> typeParameters;
        IrSimpleFunction getter;
        IrSimpleFunction setter;
        List<IrTypeArgument> arguments;
        IrField backingField;
        List<IrTypeParameter> typeParameters2;
        IrSimpleFunction setter2;
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        IrType irType = toIrType(approximateFunctionReferenceType(FirTypeUtilsKt.getConeType(callableReferenceAccess.getTypeRef())));
        int size = 0;
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(callableReferenceAccess.getCalleeReference(), false, 1, null);
        if (Intrinsics.areEqual(resolvedCallableSymbol$default != null ? resolvedCallableSymbol$default.getOrigin() : null, FirDeclarationOrigin.SamConstructor.INSTANCE)) {
            AdapterGenerator adapterGenerator = this.adapterGenerator;
            Intrinsics.checkNotNull(resolvedCallableSymbol$default, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticFunctionSymbol");
            return adapterGenerator.generateFunInterfaceConstructorReference(callableReferenceAccess, (FirSyntheticFunctionSymbol) resolvedCallableSymbol$default, irType);
        }
        IrSymbol symbolForCall = ConversionUtilsKt.toSymbolForCall(this, callableReferenceAccess.getCalleeReference(), callableReferenceAccess.getDispatchReceiver(), this.conversionScope, callableReferenceAccess.getExplicitReceiver(), (112 & 16) != 0, (112 & 32) != 0 ? false : isDelegate, (112 & 64) != 0 ? false : true);
        KtSourceElement source = callableReferenceAccess.getSource();
        IrStatementOrigin.PROPERTY_REFERENCE_FOR_DELEGATE property_reference_for_delegate = Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE) ? IrStatementOrigin.PROPERTY_REFERENCE_FOR_DELEGATE.INSTANCE : null;
        FirCallableReferenceAccess firCallableReferenceAccess = callableReferenceAccess;
        FirCallableReferenceAccess firCallableReferenceAccess2 = firCallableReferenceAccess;
        FirReference calleeReference = firCallableReferenceAccess.getCalleeReference();
        int endOffset = -1;
        if (ConversionUtilsKt.isCompiledElement(UtilsKt.getPsi(firCallableReferenceAccess2))) {
            startOffset = -1;
        } else {
            KtSourceElement source2 = calleeReference.getSource();
            if (source2 == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source2)) == null) {
                KtSourceElement source3 = calleeReference.getSource();
                startOffset = source3 != null ? source3.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            KtSourceElement source4 = firCallableReferenceAccess2.getSource();
            if (source4 != null) {
                endOffset = source4.getEndOffset();
            }
        }
        if (symbolForCall instanceof IrPropertySymbol) {
            IrPropertySymbol irPropertySymbol = (IrPropertySymbol) symbolForCall;
            IrProperty owner = irPropertySymbol.getOwner();
            IrSimpleFunction getter2 = owner.getGetter();
            IrSimpleFunctionSymbol symbol = (!InferenceUtilsKt.isKMutableProperty(FirTypeUtilsKt.getConeType(callableReferenceAccess.getTypeRef()), getSession()) || (setter2 = owner.getSetter()) == null) ? null : setter2.getSymbol();
            IrFieldSymbol symbol2 = (getter2 == null && (backingField = owner.getBackingField()) != null) ? backingField.getSymbol() : null;
            if (getter2 != null && (typeParameters2 = getter2.getTypeParameters()) != null) {
                size = typeParameters2.size();
            }
            irErrorCallExpressionImpl = applyReceivers(applyTypeArguments$fir2ir(new IrPropertyReferenceImpl(startOffset, endOffset, irType, irPropertySymbol, size, symbol2, getter2 != null ? getter2.getSymbol() : null, symbol, property_reference_for_delegate), firCallableReferenceAccess), firCallableReferenceAccess, explicitReceiverExpression);
        } else if (symbolForCall instanceof IrLocalDelegatedPropertySymbol) {
            IrLocalDelegatedPropertySymbol irLocalDelegatedPropertySymbol = (IrLocalDelegatedPropertySymbol) symbolForCall;
            IrVariableSymbol symbol3 = irLocalDelegatedPropertySymbol.getOwner().getDelegate().getSymbol();
            IrSimpleFunctionSymbol symbol4 = irLocalDelegatedPropertySymbol.getOwner().getGetter().getSymbol();
            IrSimpleFunction setter3 = irLocalDelegatedPropertySymbol.getOwner().getSetter();
            irErrorCallExpressionImpl = new IrLocalDelegatedPropertyReferenceImpl(startOffset, endOffset, irType, irLocalDelegatedPropertySymbol, symbol3, symbol4, setter3 != null ? setter3.getSymbol() : null, property_reference_for_delegate);
        } else if (symbolForCall instanceof IrFieldSymbol) {
            IrFieldSymbol irFieldSymbol = (IrFieldSymbol) symbolForCall;
            IrField owner2 = irFieldSymbol.getOwner();
            IrPropertySymbol correspondingPropertySymbol = owner2.getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol == null) {
                FirNamedReference calleeReference2 = callableReferenceAccess.getCalleeReference();
                Intrinsics.checkNotNull(calleeReference2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.references.FirResolvedNamedReference");
                FirBasedSymbol<?> resolvedSymbol = ((FirResolvedNamedReference) calleeReference2).getResolvedSymbol();
                Intrinsics.checkNotNull(resolvedSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol");
                correspondingPropertySymbol = getDeclarationStorage().getOrCreateIrPropertyByPureField((FirField) ((FirFieldSymbol) resolvedSymbol).getFir(), owner2.getParent()).getSymbol();
            }
            IrSimpleType irSimpleType = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
            if (irSimpleType != null && (arguments = irSimpleType.getArguments()) != null) {
                size = arguments.size();
            }
            irErrorCallExpressionImpl = applyReceivers(new IrPropertyReferenceImpl(startOffset, endOffset, irType, correspondingPropertySymbol, size, irFieldSymbol, (owner2.getIsStatic() || (getter = correspondingPropertySymbol.getOwner().getGetter()) == null) ? null : getter.getSymbol(), (owner2.getIsStatic() || (setter = correspondingPropertySymbol.getOwner().getSetter()) == null) ? null : setter.getSymbol(), property_reference_for_delegate), firCallableReferenceAccess, explicitReceiverExpression);
        } else if (symbolForCall instanceof IrFunctionSymbol) {
            IrTypeUtilsKt.isFunctionTypeOrSubtype(irType);
            Intrinsics.checkNotNull(irType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            IrFunctionSymbol irFunctionSymbol = (IrFunctionSymbol) symbolForCall;
            IrFunction owner3 = irFunctionSymbol.getOwner();
            if (this.adapterGenerator.needToGenerateAdaptedCallableReference$fir2ir(callableReferenceAccess, (IrSimpleType) irType, owner3)) {
                AdapterGenerator adapterGenerator2 = this.adapterGenerator;
                IrType irType2 = toIrType(FirTypeUtilsKt.getConeType(callableReferenceAccess.getTypeRef()));
                Intrinsics.checkNotNull(irType2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                irErrorCallExpressionImpl = adapterGenerator2.generateAdaptedCallableReference$fir2ir(callableReferenceAccess, explicitReceiverExpression, irFunctionSymbol, (IrSimpleType) irType2);
            } else {
                IrDeclarationParent parent = owner3.getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                int size2 = owner3.getTypeParameters().size();
                if ((owner3 instanceof IrConstructor) && irClass != null && (typeParameters = irClass.getTypeParameters()) != null) {
                    size = typeParameters.size();
                }
                irErrorCallExpressionImpl = applyReceivers(applyTypeArguments$fir2ir(new IrFunctionReferenceImpl(startOffset, endOffset, irType, irFunctionSymbol, size2 + size, owner3.getValueParameters().size(), irFunctionSymbol, null, 128, null), firCallableReferenceAccess), firCallableReferenceAccess, explicitReceiverExpression);
            }
        } else {
            irErrorCallExpressionImpl = new IrErrorCallExpressionImpl(startOffset, endOffset, irType, "Unsupported callable reference: " + UtilsKt.render(callableReferenceAccess));
        }
        return irErrorCallExpressionImpl;
    }

    private final ConeKotlinType approximateFunctionReferenceType(ConeKotlinType kotlinType) {
        if (!FunctionalTypeUtilsKt.isReflectFunctionType(kotlinType, getSession()) || !(kotlinType instanceof ConeSimpleKotlinType)) {
            return kotlinType;
        }
        for (ConeTypeProjection coneTypeProjection : kotlinType.getTypeArguments()) {
            if (ConeTypeProjectionKt.getType(coneTypeProjection) instanceof ConeIntersectionType) {
                List listTake = ArraysKt.take(kotlinType.getTypeArguments(), kotlinType.getTypeArguments().length - 1);
                ConeTypeProjection coneTypeProjection2 = (ConeTypeProjection) ArraysKt.last(kotlinType.getTypeArguments());
                ConeClassLikeLookupTag lookupTag = ((ConeClassLikeType) kotlinType).getLookupTag();
                List list = listTake;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(approximateFunctionReferenceParameterType((ConeTypeProjection) it.next()));
                }
                return new ConeClassLikeTypeImpl(lookupTag, (ConeTypeProjection[]) CollectionsKt.plus((Collection<? extends ConeTypeProjection>) arrayList, coneTypeProjection2).toArray(new ConeTypeProjection[0]), ConeTypeUtilsKt.isNullable(kotlinType), kotlinType.getAttributes());
            }
        }
        return kotlinType;
    }

    private final ConeTypeProjection approximateFunctionReferenceParameterType(ConeTypeProjection typeProjection) {
        if (ConeTypeProjectionKt.isStarProjection(typeProjection)) {
            return typeProjection;
        }
        ConeIntersectionType coneIntersectionType = typeProjection instanceof ConeIntersectionType ? (ConeIntersectionType) typeProjection : null;
        if (coneIntersectionType == null) {
            return typeProjection;
        }
        ConeKotlinType alternativeType = coneIntersectionType.getAlternativeType();
        if (alternativeType == null) {
            KotlinTypeMarker kotlinTypeMarkerCommonSuperType = NewCommonSuperTypeCalculator.INSTANCE.commonSuperType(TypeComponentsKt.getTypeContext(getSession()), CollectionsKt.toList(coneIntersectionType.getIntersectedTypes()));
            ConeKotlinType coneKotlinType = kotlinTypeMarkerCommonSuperType instanceof ConeKotlinType ? (ConeKotlinType) kotlinTypeMarkerCommonSuperType : null;
            if (coneKotlinType == null) {
                return typeProjection;
            }
            alternativeType = coneKotlinType;
        }
        return ConeTypeUtilsKt.toTypeProjection(alternativeType, typeProjection.getKind());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrTypeOperatorCall tryConvertToSamConstructorCall(FirQualifiedAccessExpression firQualifiedAccessExpression, IrType irType) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        FirReference calleeReference = firQualifiedAccessExpression.getCalleeReference();
        FirResolvedNamedReference firResolvedNamedReference = calleeReference instanceof FirResolvedNamedReference ? (FirResolvedNamedReference) calleeReference : null;
        if (firResolvedNamedReference == null) {
            return null;
        }
        FirDeclaration fir = firResolvedNamedReference.getResolvedSymbol().getFir();
        if (!(firQualifiedAccessExpression instanceof FirFunctionCall) || !(fir instanceof FirSimpleFunction) || !Intrinsics.areEqual(fir.getOrigin(), FirDeclarationOrigin.SamConstructor.INSTANCE)) {
            return null;
        }
        FirQualifiedAccessExpression firQualifiedAccessExpression2 = firQualifiedAccessExpression;
        FirReference calleeReference2 = firQualifiedAccessExpression.getCalleeReference();
        if (ConversionUtilsKt.isCompiledElement(UtilsKt.getPsi(firQualifiedAccessExpression2))) {
            i = -1;
            endOffset = -1;
        } else {
            KtSourceElement source = calleeReference2.getSource();
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                KtSourceElement source2 = calleeReference2.getSource();
                startOffset = source2 != null ? source2.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            KtSourceElement source3 = firQualifiedAccessExpression2.getSource();
            i = startOffset;
            endOffset = source3 != null ? source3.getEndOffset() : -1;
        }
        return new IrTypeOperatorCallImpl(i, endOffset, irType, IrTypeOperator.SAM_CONVERSION, irType, Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, (FirExpression) CollectionsKt.first((List) ((FirCall) firQualifiedAccessExpression).getArgumentList().getArguments()), false, 2, null));
    }

    private final IrClassSymbol superQualifierSymbol(FirExpression firExpression) {
        if (!(firExpression instanceof FirQualifiedAccessExpression)) {
            return null;
        }
        FirReference calleeReference = ((FirQualifiedAccessExpression) firExpression).getCalleeReference();
        if (!(calleeReference instanceof FirSuperReference)) {
            return null;
        }
        FirTypeRef superTypeRef = ((FirSuperReference) calleeReference).getSuperTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = superTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) superTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType == null) {
            return null;
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, getSession(), null, 2, null).getLookupTag(), getSession());
        FirClassSymbol<?> firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
        if (firClassSymbol != null) {
            return getClassifierStorage().getIrClassSymbol(firClassSymbol);
        }
        return null;
    }

    private final IrDynamicOperator getDynamicOperator(Name name2) {
        if (Intrinsics.areEqual(name2, OperatorNameConventions.UNARY_PLUS)) {
            return IrDynamicOperator.UNARY_PLUS;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.UNARY_MINUS)) {
            return IrDynamicOperator.UNARY_MINUS;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.NOT)) {
            return IrDynamicOperator.EXCL;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.PLUS)) {
            return IrDynamicOperator.BINARY_PLUS;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.MINUS)) {
            return IrDynamicOperator.BINARY_MINUS;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.TIMES)) {
            return IrDynamicOperator.MUL;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.DIV)) {
            return IrDynamicOperator.DIV;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.REM)) {
            return IrDynamicOperator.MOD;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.AND)) {
            return IrDynamicOperator.ANDAND;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.OR)) {
            return IrDynamicOperator.OROR;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.EQUALS)) {
            return IrDynamicOperator.EQEQ;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.PLUS_ASSIGN)) {
            return IrDynamicOperator.PLUSEQ;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.MINUS_ASSIGN)) {
            return IrDynamicOperator.MINUSEQ;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.TIMES_ASSIGN)) {
            return IrDynamicOperator.MULEQ;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.DIV_ASSIGN)) {
            return IrDynamicOperator.DIVEQ;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.REM_ASSIGN)) {
            return IrDynamicOperator.MODEQ;
        }
        return null;
    }

    private final IrDynamicOperator getDynamicOperator(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        Name name2;
        KtSourceElement source = firQualifiedAccessExpression.getCalleeReference().getSource();
        KtSourceElementKind kind = source != null ? source.getKind() : null;
        if (kind instanceof KtFakeSourceElementKind.ArrayAccessNameReference) {
            FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(firQualifiedAccessExpression.getCalleeReference());
            name2 = resolved != null ? resolved.getName() : null;
            if (Intrinsics.areEqual(name2, OperatorNameConventions.SET)) {
                return IrDynamicOperator.EQ;
            }
            if (Intrinsics.areEqual(name2, OperatorNameConventions.GET)) {
                return IrDynamicOperator.ARRAY_ACCESS;
            }
            throw new IllegalStateException("Unexpected name".toString());
        }
        if (kind instanceof KtFakeSourceElementKind.DesugaredPrefixNameReference) {
            FirResolvedNamedReference resolved2 = FirReferenceUtilsKt.getResolved(firQualifiedAccessExpression.getCalleeReference());
            name2 = resolved2 != null ? resolved2.getName() : null;
            if (Intrinsics.areEqual(name2, OperatorNameConventions.INC)) {
                return IrDynamicOperator.PREFIX_INCREMENT;
            }
            if (Intrinsics.areEqual(name2, OperatorNameConventions.DEC)) {
                return IrDynamicOperator.PREFIX_DECREMENT;
            }
            throw new IllegalStateException("Unexpected name".toString());
        }
        if (!(kind instanceof KtFakeSourceElementKind.DesugaredPostfixNameReference)) {
            return null;
        }
        FirResolvedNamedReference resolved3 = FirReferenceUtilsKt.getResolved(firQualifiedAccessExpression.getCalleeReference());
        name2 = resolved3 != null ? resolved3.getName() : null;
        if (Intrinsics.areEqual(name2, OperatorNameConventions.INC)) {
            return IrDynamicOperator.POSTFIX_INCREMENT;
        }
        if (Intrinsics.areEqual(name2, OperatorNameConventions.DEC)) {
            return IrDynamicOperator.POSTFIX_DECREMENT;
        }
        throw new IllegalStateException("Unexpected name".toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpression convertToIrCallForDynamic(FirQualifiedAccessExpression qualifiedAccess, IrExpression explicitReceiverExpression, IrType type, FirReference calleeReference, FirBasedSymbol<?> symbol, IrDynamicOperator dynamicOperator, boolean noArguments) {
        int startOffset;
        int i;
        Integer numStartOffsetSkippingComments;
        IrErrorCallExpression irErrorCallExpressionGenerateErrorCallExpression;
        Name name2;
        Name name3;
        IrDynamicMemberExpressionImpl irDynamicMemberExpressionImplFindIrDynamicReceiver = findIrDynamicReceiver(qualifiedAccess, explicitReceiverExpression);
        FirQualifiedAccessExpression firQualifiedAccessExpression = qualifiedAccess;
        FirReference calleeReference2 = qualifiedAccess.getCalleeReference();
        int endOffset = -1;
        if (ConversionUtilsKt.isCompiledElement(UtilsKt.getPsi(firQualifiedAccessExpression))) {
            i = -1;
        } else {
            KtSourceElement source = calleeReference2.getSource();
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                KtSourceElement source2 = calleeReference2.getSource();
                startOffset = source2 != null ? source2.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            KtSourceElement source3 = firQualifiedAccessExpression.getSource();
            endOffset = source3 != null ? source3.getEndOffset() : -1;
            i = startOffset;
        }
        if (symbol instanceof FirFunctionSymbol) {
            FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(calleeReference);
            if (resolved == null || (name3 = resolved.getName()) == null) {
                throw new IllegalStateException("Must have a name".toString());
            }
            if (dynamicOperator == null && (dynamicOperator = getDynamicOperator(name3)) == null && (dynamicOperator = getDynamicOperator(qualifiedAccess)) == null) {
                dynamicOperator = IrDynamicOperator.INVOKE;
            }
            IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(i, endOffset, Intrinsics.areEqual(name3, OperatorNameConventions.COMPARE_TO) ? getTypeConverter().getIrBuiltIns().getBooleanType() : type, dynamicOperator);
            if (dynamicOperator == IrDynamicOperator.INVOKE && !(qualifiedAccess instanceof FirImplicitInvokeCall)) {
                String identifier = name3.getIdentifier();
                Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                irDynamicMemberExpressionImplFindIrDynamicReceiver = new IrDynamicMemberExpressionImpl(i, endOffset, type, identifier, irDynamicMemberExpressionImplFindIrDynamicReceiver);
            }
            irDynamicOperatorExpressionImpl.setReceiver(irDynamicMemberExpressionImplFindIrDynamicReceiver);
            irErrorCallExpressionGenerateErrorCallExpression = irDynamicOperatorExpressionImpl;
        } else if (symbol instanceof FirPropertySymbol) {
            FirResolvedNamedReference resolved2 = FirReferenceUtilsKt.getResolved(calleeReference);
            if (resolved2 == null || (name2 = resolved2.getName()) == null) {
                throw new IllegalStateException("There must be a name".toString());
            }
            String identifier2 = name2.getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier2, "getIdentifier(...)");
            irErrorCallExpressionGenerateErrorCallExpression = new IrDynamicMemberExpressionImpl(i, endOffset, type, identifier2, irDynamicMemberExpressionImplFindIrDynamicReceiver);
        } else {
            irErrorCallExpressionGenerateErrorCallExpression = generateErrorCallExpression(i, endOffset, calleeReference, type);
        }
        IrExpression irExpressionApplyTypeArguments$fir2ir = applyTypeArguments$fir2ir((IrExpression) irErrorCallExpressionGenerateErrorCallExpression, qualifiedAccess);
        FirCall firCall = null;
        FirCall firCall2 = qualifiedAccess instanceof FirCall ? (FirCall) qualifiedAccess : null;
        if (firCall2 != null && !noArguments) {
            firCall = firCall2;
        }
        return applyCallArguments$fir2ir(irExpressionApplyTypeArguments$fir2ir, firCall);
    }

    public final IrExpression injectGetValueCall$fir2ir(FirElement element, FirReference calleeReference) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        InjectedValue injectedValueMo6785findInjectedValue = getExtensions().mo6785findInjectedValue(calleeReference, this.conversionScope);
        if (injectedValueMo6785findInjectedValue == null) {
            return null;
        }
        KtSourceElement source = element.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            i = startOffset;
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        return new IrGetValueImpl(i, endOffset, toIrType(injectedValueMo6785findInjectedValue.getTypeRef()), injectedValueMo6785findInjectedValue.getIrParameterSymbol(), ConversionUtilsKt.statementOrigin(calleeReference));
    }

    public static /* synthetic */ IrExpression convertToIrCall$default(CallAndReferenceGenerator callAndReferenceGenerator, FirQualifiedAccessExpression firQualifiedAccessExpression, FirTypeRef firTypeRef, IrExpression irExpression, IrDynamicOperator irDynamicOperator, boolean z, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            irDynamicOperator = null;
        }
        return callAndReferenceGenerator.convertToIrCall(firQualifiedAccessExpression, firTypeRef, irExpression, irDynamicOperator, (i & 16) != 0 ? false : z, (i & 32) != 0 ? false : z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrExpression convertToIrCall(FirQualifiedAccessExpression qualifiedAccess, FirTypeRef typeRef, IrExpression explicitReceiverExpression, IrDynamicOperator dynamicOperator, boolean variableAsFunctionMode, boolean noArguments) {
        String name2;
        IrTypeOperatorCallImpl irTypeOperatorCallImpl;
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrErrorCallExpression irGetEnumValueImpl;
        IrStatementOrigin.VARIABLE_AS_FUNCTION variable_as_functionStatementOrigin;
        IrGetFieldImpl irGetFieldImpl;
        int size;
        int i;
        int startOffset2;
        int endOffset2;
        int i2;
        Integer numStartOffsetSkippingComments2;
        FirReceiverParameter receiverParameter;
        FirTypeRef typeRef2;
        IrType irType;
        ConeSimpleKotlinType dispatchReceiverType;
        IrType irType2;
        Intrinsics.checkNotNullParameter(qualifiedAccess, "qualifiedAccess");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        try {
            IrExpression irExpressionInjectGetValueCall$fir2ir = injectGetValueCall$fir2ir(qualifiedAccess, qualifiedAccess.getCalleeReference());
            if (irExpressionInjectGetValueCall$fir2ir != null) {
                return irExpressionInjectGetValueCall$fir2ir;
            }
            IrType irType3 = toIrType(typeRef);
            IrTypeOperatorCall irTypeOperatorCallTryConvertToSamConstructorCall = tryConvertToSamConstructorCall(qualifiedAccess, irType3);
            if (irTypeOperatorCallTryConvertToSamConstructorCall != null) {
                return irTypeOperatorCallTryConvertToSamConstructorCall;
            }
            FirExpression dispatchReceiver = qualifiedAccess.getDispatchReceiver();
            FirReference calleeReference = qualifiedAccess.getCalleeReference();
            FirBasedSymbol<?> resolvedBaseSymbol$default = FirReferenceUtilsKt.toResolvedBaseSymbol$default(calleeReference, false, 1, null);
            if (Intrinsics.areEqual(resolvedBaseSymbol$default != null ? resolvedBaseSymbol$default.getOrigin() : null, FirDeclarationOrigin.DynamicScope.INSTANCE)) {
                if (resolvedBaseSymbol$default != null) {
                    return convertToIrCallForDynamic(qualifiedAccess, explicitReceiverExpression, irType3, calleeReference, resolvedBaseSymbol$default, dynamicOperator, noArguments);
                }
                throw new IllegalStateException("Must have had a symbol".toString());
            }
            IrSymbol symbolForCall = ConversionUtilsKt.toSymbolForCall(this, calleeReference, dispatchReceiver, this.conversionScope, qualifiedAccess.getExplicitReceiver(), (112 & 16) != 0, (112 & 32) != 0 ? false : false, (112 & 64) != 0 ? false : false);
            if ((explicitReceiverExpression != null ? explicitReceiverExpression.getType() : null) instanceof IrDynamicType) {
                FirQualifiedAccessExpression firQualifiedAccessExpression = qualifiedAccess;
                FirReference calleeReference2 = qualifiedAccess.getCalleeReference();
                if (ConversionUtilsKt.isCompiledElement(UtilsKt.getPsi(firQualifiedAccessExpression))) {
                    i2 = -1;
                    endOffset2 = -1;
                } else {
                    KtSourceElement source = calleeReference2.getSource();
                    if (source == null || (numStartOffsetSkippingComments2 = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                        KtSourceElement source2 = calleeReference2.getSource();
                        startOffset2 = source2 != null ? source2.getStartOffset() : -1;
                    } else {
                        startOffset2 = numStartOffsetSkippingComments2.intValue();
                    }
                    KtSourceElement source3 = firQualifiedAccessExpression.getSource();
                    endOffset2 = source3 != null ? source3.getEndOffset() : -1;
                    i2 = startOffset2;
                }
                FirDeclaration fir = resolvedBaseSymbol$default != null ? resolvedBaseSymbol$default.getFir() : null;
                FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
                if (firCallableDeclaration != null && (dispatchReceiverType = firCallableDeclaration.getDispatchReceiverType()) != null && (irType2 = toIrType(dispatchReceiverType)) != null) {
                    irType = irType2;
                } else {
                    if (firCallableDeclaration == null || (receiverParameter = firCallableDeclaration.getReceiverParameter()) == null || (typeRef2 = receiverParameter.getTypeRef()) == null) {
                        throw new IllegalStateException("Couldn't get the proper receiver".toString());
                    }
                    irType = toIrType(typeRef2);
                }
                irTypeOperatorCallImpl = new IrTypeOperatorCallImpl(i2, endOffset2, irType, IrTypeOperator.IMPLICIT_DYNAMIC_CAST, irType, explicitReceiverExpression);
            } else {
                irTypeOperatorCallImpl = explicitReceiverExpression;
            }
            FirQualifiedAccessExpression firQualifiedAccessExpression2 = qualifiedAccess;
            FirReference calleeReference3 = qualifiedAccess.getCalleeReference();
            if (ConversionUtilsKt.isCompiledElement(UtilsKt.getPsi(firQualifiedAccessExpression2))) {
                startOffset = -1;
                endOffset = -1;
            } else {
                KtSourceElement source4 = calleeReference3.getSource();
                if (source4 == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source4)) == null) {
                    KtSourceElement source5 = calleeReference3.getSource();
                    startOffset = source5 != null ? source5.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                KtSourceElement source6 = firQualifiedAccessExpression2.getSource();
                endOffset = source6 != null ? source6.getEndOffset() : -1;
            }
            if ((calleeReference instanceof FirSuperReference) && !(dispatchReceiver instanceof FirNoReceiverExpression)) {
                irGetEnumValueImpl = Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, dispatchReceiver, false, 2, null);
            } else if (symbolForCall instanceof IrConstructorSymbol) {
                irGetEnumValueImpl = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, startOffset, endOffset, irType3, (IrConstructorSymbol) symbolForCall, null, 16, null);
            } else {
                if (symbolForCall instanceof IrSimpleFunctionSymbol) {
                    if (!(resolvedBaseSymbol$default instanceof FirCallableSymbol)) {
                        StringBuilder sb = new StringBuilder("Illegal symbol: ");
                        Intrinsics.checkNotNull(resolvedBaseSymbol$default);
                        throw new IllegalArgumentException(sb.append(Reflection.getOrCreateKotlinClass(resolvedBaseSymbol$default.getClass())).toString().toString());
                    }
                    FirCallableSymbol firCallableSymbol = (FirCallableSymbol) resolvedBaseSymbol$default;
                    if (firCallableSymbol instanceof FirSyntheticPropertySymbol) {
                        i = 0;
                    } else {
                        if (firCallableSymbol instanceof FirNamedFunctionSymbol) {
                            size = ((FirNamedFunctionSymbol) resolvedBaseSymbol$default).getValueParameterSymbols().size() + ((FirCallableSymbol) resolvedBaseSymbol$default).getResolvedContextReceivers().size();
                        } else {
                            if (!(firCallableSymbol instanceof FirFunctionSymbol)) {
                                throw new IllegalStateException(("Illegal symbol: " + Reflection.getOrCreateKotlinClass(resolvedBaseSymbol$default.getClass())).toString());
                            }
                            size = ((FirFunctionSymbol) resolvedBaseSymbol$default).getValueParameterSymbols().size();
                        }
                        i = size;
                    }
                    irGetFieldImpl = new IrCallImpl(startOffset, endOffset, irType3, (IrSimpleFunctionSymbol) symbolForCall, ((FirCallableSymbol) resolvedBaseSymbol$default).getTypeParameterSymbols().size(), i, ConversionUtilsKt.statementOrigin(calleeReference), superQualifierSymbol(dispatchReceiver));
                } else if (symbolForCall instanceof IrLocalDelegatedPropertySymbol) {
                    irGetFieldImpl = new IrCallImpl(startOffset, endOffset, irType3, ((IrLocalDelegatedPropertySymbol) symbolForCall).getOwner().getGetter().getSymbol(), ((IrLocalDelegatedPropertySymbol) symbolForCall).getOwner().getGetter().getTypeParameters().size(), 0, IrStatementOrigin.GET_LOCAL_PROPERTY.INSTANCE, superQualifierSymbol(dispatchReceiver));
                } else if (symbolForCall instanceof IrPropertySymbol) {
                    IrSimpleFunction getter = ((IrPropertySymbol) symbolForCall).getOwner().getGetter();
                    IrField backingField = ((IrPropertySymbol) symbolForCall).getOwner().getBackingField();
                    if (getter != null) {
                        irGetFieldImpl = new IrCallImpl(startOffset, endOffset, irType3, getter.getSymbol(), getter.getTypeParameters().size(), getter.getValueParameters().size(), IrStatementOrigin.GET_PROPERTY.INSTANCE, superQualifierSymbol(dispatchReceiver));
                    } else if (backingField != null) {
                        irGetFieldImpl = new IrGetFieldImpl(startOffset, endOffset, backingField.getSymbol(), irType3, null, superQualifierSymbol(dispatchReceiver), 16, null);
                    } else {
                        irGetEnumValueImpl = new IrErrorCallExpressionImpl(startOffset, endOffset, irType3, "No getter or backing field found for " + UtilsKt.render(calleeReference));
                    }
                } else if (symbolForCall instanceof IrFieldSymbol) {
                    if (this.visitor.get_annotationMode()) {
                        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null);
                        if (resolvedCallableSymbol$default == null) {
                            throw new IllegalStateException("should have resolvedSymbol".toString());
                        }
                        IrType irType4 = toIrType(resolvedCallableSymbol$default.getResolvedReturnTypeRef());
                        D fir2 = resolvedCallableSymbol$default.getFir();
                        Intrinsics.checkNotNull(fir2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirVariable");
                        FirExpression initializer = ((FirVariable) fir2).getInitializer();
                        FirConstExpression firConstExpression = initializer instanceof FirConstExpression ? (FirConstExpression) initializer : null;
                        if (firConstExpression == null) {
                            throw new IllegalStateException("should be FirConstExpression".toString());
                        }
                        irGetEnumValueImpl = ConversionUtilsKt.toIrConst(firConstExpression, irType4);
                    } else {
                        irGetEnumValueImpl = new IrGetFieldImpl(startOffset, endOffset, (IrFieldSymbol) symbolForCall, irType3, calleeReference instanceof FirDelegateFieldReference ? null : IrStatementOrigin.GET_PROPERTY.INSTANCE, superQualifierSymbol(dispatchReceiver));
                    }
                } else if (symbolForCall instanceof IrValueSymbol) {
                    IrType type = ((IrValueSymbol) symbolForCall).getOwner().getType();
                    IrValueSymbol irValueSymbol = (IrValueSymbol) symbolForCall;
                    if (variableAsFunctionMode) {
                        variable_as_functionStatementOrigin = IrStatementOrigin.VARIABLE_AS_FUNCTION.INSTANCE;
                    } else {
                        variable_as_functionStatementOrigin = ConversionUtilsKt.statementOrigin(calleeReference);
                    }
                    irGetEnumValueImpl = new IrGetValueImpl(startOffset, endOffset, type, irValueSymbol, variable_as_functionStatementOrigin);
                } else {
                    irGetEnumValueImpl = symbolForCall instanceof IrEnumEntrySymbol ? new IrGetEnumValueImpl(startOffset, endOffset, irType3, (IrEnumEntrySymbol) symbolForCall) : generateErrorCallExpression(startOffset, endOffset, calleeReference, irType3);
                }
                irGetEnumValueImpl = irGetFieldImpl;
            }
            return applyCallArguments$fir2ir(applyReceivers(applyTypeArguments$fir2ir((IrExpression) irGetEnumValueImpl, qualifiedAccess), qualifiedAccess, irTypeOperatorCallImpl), qualifiedAccess);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            StringBuilder sbAppend = new StringBuilder("Error while translating ").append(UtilsKt.render(qualifiedAccess)).append(" from file ");
            IrFile irFileContainingFileIfAny = this.conversionScope.containingFileIfAny();
            if (irFileContainingFileIfAny == null || (name2 = IrDeclarationsKt.getName(irFileContainingFileIfAny)) == null) {
                name2 = "???";
            }
            throw new IllegalStateException(sbAppend.append(name2).append(" to BE IR").toString(), th);
        }
    }

    private final IrExpression convertToIrSetCallForDynamic(FirVariableAssignment variableAssignment, IrExpression explicitReceiverExpression, IrType type, FirReference calleeReference, FirBasedSymbol<?> symbol, IrExpression assignedValue) {
        int startOffset;
        int i;
        Integer numStartOffsetSkippingComments;
        IrErrorCallExpression irErrorCallExpressionGenerateErrorCallExpression$default;
        Name name2;
        FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue = FirExpressionUtilKt.unwrapLValue(variableAssignment);
        if (firQualifiedAccessExpressionUnwrapLValue == null) {
            throw new IllegalStateException("Assignment has no lValue".toString());
        }
        IrExpression irExpressionFindIrDynamicReceiver = findIrDynamicReceiver(firQualifiedAccessExpressionUnwrapLValue, explicitReceiverExpression);
        KtSourceElement source = variableAssignment.getSource();
        KtSourceElement ktSourceElement = source;
        int endOffset = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        if (symbol instanceof FirPropertySymbol) {
            FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(calleeReference);
            if (resolved == null || (name2 = resolved.getName()) == null) {
                throw new IllegalStateException("There must be a name".toString());
            }
            IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(i, endOffset, type, IrDynamicOperator.EQ);
            String identifier = name2.getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            irDynamicOperatorExpressionImpl.setReceiver(new IrDynamicMemberExpressionImpl(i, endOffset, type, identifier, irExpressionFindIrDynamicReceiver));
            irDynamicOperatorExpressionImpl.getArguments().add(assignedValue);
            irErrorCallExpressionGenerateErrorCallExpression$default = irDynamicOperatorExpressionImpl;
        } else {
            irErrorCallExpressionGenerateErrorCallExpression$default = generateErrorCallExpression$default(this, i, endOffset, calleeReference, null, 8, null);
        }
        return (IrExpression) irErrorCallExpressionGenerateErrorCallExpression$default;
    }

    private final IrExpression findIrDynamicReceiver(FirQualifiedAccessExpression firQualifiedAccessExpression, IrExpression irExpression) {
        if (irExpression == null) {
            FirExpression dispatchReceiver = firQualifiedAccessExpression.getDispatchReceiver();
            FirThisReceiverExpression firThisReceiverExpression = dispatchReceiver instanceof FirThisReceiverExpression ? (FirThisReceiverExpression) dispatchReceiver : null;
            irExpression = firThisReceiverExpression != null ? Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, firThisReceiverExpression, false, 2, null) : null;
            if (irExpression == null) {
                throw new IllegalStateException("No receiver for dynamic call".toString());
            }
        }
        return irExpression;
    }

    private final IrExpression injectSetValueCall(FirElement element, FirReference calleeReference, IrExpression assignedValue) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        InjectedValue injectedValueMo6785findInjectedValue = getExtensions().mo6785findInjectedValue(calleeReference, this.conversionScope);
        if (injectedValueMo6785findInjectedValue == null) {
            return null;
        }
        KtSourceElement source = element.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            i = startOffset;
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        return new IrSetValueImpl(i, endOffset, getIrBuiltIns().getUnitType(), injectedValueMo6785findInjectedValue.getIrParameterSymbol(), assignedValue, ConversionUtilsKt.statementOrigin(calleeReference));
    }

    public final IrExpression convertToIrSetCall(FirVariableAssignment variableAssignment, IrExpression explicitReceiverExpression) {
        String name2;
        int startOffset;
        int i;
        int i2;
        Integer numStartOffsetSkippingComments;
        FirQualifiedAccessExpression firQualifiedAccessExpression;
        IrErrorCallExpression irErrorCallExpressionGenerateErrorCallExpression$default;
        FirQualifiedAccessExpression firQualifiedAccessExpression2;
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        try {
            IrType unitType = getIrBuiltIns().getUnitType();
            FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(variableAssignment);
            if (calleeReference == null) {
                throw new IllegalStateException("Reference not resolvable".toString());
            }
            IrExpression irExpressionConvertToIrExpression$fir2ir$default = Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, variableAssignment.getRValue(), false, 2, null);
            IrExpression irExpressionInjectSetValueCall = injectSetValueCall(variableAssignment, calleeReference, irExpressionConvertToIrExpression$fir2ir$default);
            if (irExpressionInjectSetValueCall != null) {
                return irExpressionInjectSetValueCall;
            }
            FirBasedSymbol<?> resolvedBaseSymbol$default = FirReferenceUtilsKt.toResolvedBaseSymbol$default(calleeReference, false, 1, null);
            if (Intrinsics.areEqual(resolvedBaseSymbol$default != null ? resolvedBaseSymbol$default.getOrigin() : null, FirDeclarationOrigin.DynamicScope.INSTANCE)) {
                if (resolvedBaseSymbol$default != null) {
                    return convertToIrSetCallForDynamic(variableAssignment, explicitReceiverExpression, unitType, calleeReference, resolvedBaseSymbol$default, irExpressionConvertToIrExpression$fir2ir$default);
                }
                throw new IllegalStateException("Must've had a symbol".toString());
            }
            IrSymbol symbolForCall = ConversionUtilsKt.toSymbolForCall(this, calleeReference, FirExpressionUtilKt.getDispatchReceiver(variableAssignment), this.conversionScope, FirExpressionUtilKt.getExplicitReceiver(variableAssignment), (112 & 16) != 0, (112 & 32) != 0 ? false : false, (112 & 64) != 0 ? false : false);
            IrStatementOrigin irAssignmentOrigin = ConversionUtilsKt.getIrAssignmentOrigin(variableAssignment);
            FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue = FirExpressionUtilKt.unwrapLValue(variableAssignment);
            if (firQualifiedAccessExpressionUnwrapLValue == null) {
                throw new IllegalStateException("Assignment lValue unwrapped to null".toString());
            }
            FirVariableAssignment firVariableAssignment = variableAssignment;
            if (ConversionUtilsKt.isCompiledElement(UtilsKt.getPsi(firVariableAssignment))) {
                i = -1;
                i2 = -1;
            } else {
                KtSourceElement source = calleeReference.getSource();
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    KtSourceElement source2 = calleeReference.getSource();
                    startOffset = source2 != null ? source2.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                KtSourceElement source3 = firVariableAssignment.getSource();
                int endOffset = source3 != null ? source3.getEndOffset() : -1;
                i = startOffset;
                i2 = endOffset;
            }
            if (symbolForCall instanceof IrFieldSymbol) {
                IrSetFieldImpl irSetFieldImpl = new IrSetFieldImpl(i, i2, (IrFieldSymbol) symbolForCall, unitType, irAssignmentOrigin, (IrClassSymbol) null, 32, (DefaultConstructorMarker) null);
                irSetFieldImpl.setValue(irExpressionConvertToIrExpression$fir2ir$default);
                irErrorCallExpressionGenerateErrorCallExpression$default = irSetFieldImpl;
            } else {
                if (symbolForCall instanceof IrLocalDelegatedPropertySymbol) {
                    IrSimpleFunction setter = ((IrLocalDelegatedPropertySymbol) symbolForCall).getOwner().getSetter();
                    if (setter != null) {
                        IrCallImpl irCallImpl = new IrCallImpl(i, i2, unitType, setter.getSymbol(), setter.getTypeParameters().size(), setter.getValueParameters().size(), irAssignmentOrigin, superQualifierSymbol(FirExpressionUtilKt.getDispatchReceiver(variableAssignment)));
                        putContextReceiverArguments(irCallImpl, firQualifiedAccessExpressionUnwrapLValue);
                        irCallImpl.putValueArgument(0, irExpressionConvertToIrExpression$fir2ir$default);
                        irErrorCallExpressionGenerateErrorCallExpression$default = irCallImpl;
                    } else {
                        firQualifiedAccessExpression2 = firQualifiedAccessExpressionUnwrapLValue;
                        irErrorCallExpressionGenerateErrorCallExpression$default = generateErrorCallExpression$default(this, i, i2, calleeReference, null, 8, null);
                        firQualifiedAccessExpression = firQualifiedAccessExpression2;
                    }
                } else if (symbolForCall instanceof IrPropertySymbol) {
                    IrProperty owner = ((IrPropertySymbol) symbolForCall).getOwner();
                    IrSimpleFunction setter2 = owner.getSetter();
                    IrField backingField = owner.getBackingField();
                    if (setter2 == null && backingField == null) {
                        backingField = overriddenBackingFieldOrNull(owner);
                    }
                    if (setter2 != null) {
                        firQualifiedAccessExpressionUnwrapLValue = firQualifiedAccessExpressionUnwrapLValue;
                        IrCallImpl irCallImpl2 = new IrCallImpl(i, i2, unitType, setter2.getSymbol(), setter2.getTypeParameters().size(), setter2.getValueParameters().size(), irAssignmentOrigin, superQualifierSymbol(FirExpressionUtilKt.getDispatchReceiver(variableAssignment)));
                        irCallImpl2.putValueArgument(putContextReceiverArguments(irCallImpl2, firQualifiedAccessExpressionUnwrapLValue), irExpressionConvertToIrExpression$fir2ir$default);
                        irErrorCallExpressionGenerateErrorCallExpression$default = irCallImpl2;
                    } else {
                        if (backingField != null) {
                            firQualifiedAccessExpression2 = firQualifiedAccessExpressionUnwrapLValue;
                            IrSetFieldImpl irSetFieldImpl2 = new IrSetFieldImpl(i, i2, backingField.getSymbol(), unitType, null, superQualifierSymbol(FirExpressionUtilKt.getDispatchReceiver(variableAssignment)));
                            irSetFieldImpl2.setValue(irExpressionConvertToIrExpression$fir2ir$default);
                            irErrorCallExpressionGenerateErrorCallExpression$default = irSetFieldImpl2;
                        } else {
                            firQualifiedAccessExpression2 = firQualifiedAccessExpressionUnwrapLValue;
                            irErrorCallExpressionGenerateErrorCallExpression$default = generateErrorCallExpression$default(this, i, i2, calleeReference, null, 8, null);
                        }
                        firQualifiedAccessExpression = firQualifiedAccessExpression2;
                    }
                } else if (symbolForCall instanceof IrSimpleFunctionSymbol) {
                    IrCallImpl irCallImpl3 = new IrCallImpl(i, i2, unitType, (IrSimpleFunctionSymbol) symbolForCall, ((IrSimpleFunctionSymbol) symbolForCall).getOwner().getTypeParameters().size(), 1, irAssignmentOrigin, null, 128, null);
                    irCallImpl3.putValueArgument(0, irExpressionConvertToIrExpression$fir2ir$default);
                    irErrorCallExpressionGenerateErrorCallExpression$default = irCallImpl3;
                    firQualifiedAccessExpression = firQualifiedAccessExpressionUnwrapLValue;
                } else {
                    firQualifiedAccessExpression = firQualifiedAccessExpressionUnwrapLValue;
                    if (symbolForCall instanceof IrVariableSymbol) {
                        irErrorCallExpressionGenerateErrorCallExpression$default = new IrSetValueImpl(i, i2, unitType, (IrValueSymbol) symbolForCall, irExpressionConvertToIrExpression$fir2ir$default, irAssignmentOrigin);
                    } else {
                        irErrorCallExpressionGenerateErrorCallExpression$default = generateErrorCallExpression$default(this, i, i2, calleeReference, null, 8, null);
                    }
                }
                return applyReceivers(applyTypeArguments$fir2ir((IrExpression) irErrorCallExpressionGenerateErrorCallExpression$default, firQualifiedAccessExpression), firQualifiedAccessExpression, explicitReceiverExpression);
            }
            firQualifiedAccessExpression = firQualifiedAccessExpressionUnwrapLValue;
            return applyReceivers(applyTypeArguments$fir2ir((IrExpression) irErrorCallExpressionGenerateErrorCallExpression$default, firQualifiedAccessExpression), firQualifiedAccessExpression, explicitReceiverExpression);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            StringBuilder sbAppend = new StringBuilder("Error while translating ").append(UtilsKt.render(variableAssignment)).append(" from file ");
            IrFile irFileContainingFileIfAny = this.conversionScope.containingFileIfAny();
            if (irFileContainingFileIfAny == null || (name2 = IrDeclarationsKt.getName(irFileContainingFileIfAny)) == null) {
                name2 = "???";
            }
            throw new IllegalStateException(sbAppend.append(name2).append(" to BE IR").toString(), th);
        }
    }

    private final IrField overriddenBackingFieldOrNull(IrProperty irProperty) {
        Iterator<T> it = irProperty.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            IrProperty owner = ((IrPropertySymbol) it.next()).getOwner();
            IrField backingField = owner.getBackingField();
            if (backingField == null) {
                backingField = overriddenBackingFieldOrNull(owner);
            }
            if (backingField != null) {
                return backingField;
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:71:0x016f A[Catch: all -> 0x017e, TryCatch #0 {all -> 0x017e, blocks: (B:69:0x0162, B:71:0x016f, B:73:0x0175), top: B:79:0x0162 }] */
    /* JADX WARN: Code duplicated, block: B:72:0x0174  */
    /* JADX WARN: Multi-variable type inference failed */
    public final IrExpression convertToIrConstructorCall(FirAnnotation annotation) {
        int startOffset;
        int i;
        int i2;
        Integer numStartOffsetSkippingComments;
        IrConstructorCallImpl irConstructorCallImpl;
        FirTypeScope firTypeScopeUnsubstitutedScope;
        Fir2IrVisitor fir2IrVisitor;
        boolean z;
        FirAnnotationCall annotationCall;
        List<FirTypeProjection> typeArguments;
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        FirTypeRef annotationTypeRef = annotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeLookupTagBasedType)) {
            type = null;
        }
        ConeLookupTagBasedType coneLookupTagBasedType = (ConeLookupTagBasedType) type;
        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = coneLookupTagBasedType != null ? TypeExpansionUtilsKt.fullyExpandedType((ConeSimpleKotlinType) coneLookupTagBasedType, getSession()) : null;
        ConeLookupTagBasedType coneLookupTagBasedType2 = coneSimpleKotlinTypeFullyExpandedType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneSimpleKotlinTypeFullyExpandedType : null;
        IrErrorType irType = coneLookupTagBasedType2 != null ? toIrType(coneLookupTagBasedType2) : null;
        IrClassifierSymbol classifierOrNull = irType != null ? IrTypesKt.getClassifierOrNull(irType) : null;
        FirAnnotation firAnnotation = annotation;
        KtSourceElement source = firAnnotation.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
            i2 = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            int endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
            i2 = endOffset;
        }
        try {
            if (!(classifierOrNull instanceof IrClassSymbol)) {
                if (irType == null) {
                    irType = ConversionUtilsKt.createErrorType();
                }
                irConstructorCallImpl = new IrErrorCallExpressionImpl(i, i2, irType, "Unresolved reference: " + UtilsKt.render(firAnnotation));
            } else {
                IrClass owner = ((IrClassSymbol) classifierOrNull).getOwner();
                FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(annotation);
                FirConstructorSymbol firConstructorSymbol = resolvedCallableSymbol instanceof FirConstructorSymbol ? (FirConstructorSymbol) resolvedCallableSymbol : null;
                if (firConstructorSymbol == null) {
                    FirClassifierSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneLookupTagBasedType2.getLookupTag(), getSession());
                    Object fir = symbol != null ? symbol.getFir() : null;
                    FirClass firClass = fir instanceof FirClass ? (FirClass) fir : null;
                    final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                    if (firClass != null && (firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(firClass, getSession(), getScopeSession(), true, (FirResolvePhase) null)) != null) {
                        firTypeScopeUnsubstitutedScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator$convertToIrConstructorCall$irConstructorCall$1$firConstructorSymbol$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol2) {
                                invoke2(firConstructorSymbol2);
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(FirConstructorSymbol it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                if (((FirConstructor) it.getFir()).getIsPrimary() && objectRef.element == null) {
                                    objectRef.element = it;
                                }
                            }
                        });
                    }
                    firConstructorSymbol = (FirConstructorSymbol) objectRef.element;
                    if (firConstructorSymbol == null) {
                        irConstructorCallImpl = new IrErrorCallExpressionImpl(i, i2, irType, "No annotation constructor found: " + owner.getName());
                    }
                    IrExpression irExpression = (IrExpression) irConstructorCallImpl;
                    fir2IrVisitor = this.visitor;
                    z = fir2IrVisitor._annotationMode;
                    fir2IrVisitor._annotationMode = true;
                    annotationCall = toAnnotationCall(annotation);
                    IrExpression irExpressionApplyCallArguments$fir2ir = applyCallArguments$fir2ir(irExpression, annotationCall);
                    if (annotationCall != null) {
                        typeArguments = annotationCall.getTypeArguments();
                    } else {
                        typeArguments = null;
                    }
                    return applyTypeArguments(irExpressionApplyCallArguments$fir2ir, typeArguments, null);
                }
                irConstructorCallImpl = new IrConstructorCallImpl(i, i2, irType, getDeclarationStorage().getIrConstructorSymbol(firConstructorSymbol), annotation.getTypeArguments().size(), 0, firConstructorSymbol.getValueParameterSymbols().size(), null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
                IrExpression irExpression2 = (IrExpression) irConstructorCallImpl;
                fir2IrVisitor = this.visitor;
                z = fir2IrVisitor._annotationMode;
                fir2IrVisitor._annotationMode = true;
                annotationCall = toAnnotationCall(annotation);
                IrExpression irExpressionApplyCallArguments$fir2ir2 = applyCallArguments$fir2ir(irExpression2, annotationCall);
                if (annotationCall != null) {
                    typeArguments = annotationCall.getTypeArguments();
                } else {
                    typeArguments = null;
                }
                return applyTypeArguments(irExpressionApplyCallArguments$fir2ir2, typeArguments, null);
            }
            annotationCall = toAnnotationCall(annotation);
            IrExpression irExpressionApplyCallArguments$fir2ir3 = applyCallArguments$fir2ir(irExpression2, annotationCall);
            if (annotationCall != null) {
                typeArguments = annotationCall.getTypeArguments();
            } else {
                typeArguments = null;
            }
            return applyTypeArguments(irExpressionApplyCallArguments$fir2ir3, typeArguments, null);
        } finally {
            fir2IrVisitor._annotationMode = z;
        }
        IrExpression irExpression3 = (IrExpression) irConstructorCallImpl;
        fir2IrVisitor = this.visitor;
        z = fir2IrVisitor._annotationMode;
        fir2IrVisitor._annotationMode = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirAnnotationCall toAnnotationCall(FirAnnotation firAnnotation) {
        FirConstructorSymbol firConstructorSymbol;
        if (firAnnotation instanceof FirAnnotationCall) {
            return (FirAnnotationCall) firAnnotation;
        }
        FirAnnotationCallBuilder firAnnotationCallBuilder = new FirAnnotationCallBuilder();
        firAnnotationCallBuilder.setUseSiteTarget(firAnnotation.getUseSiteTarget());
        firAnnotationCallBuilder.setAnnotationTypeRef(firAnnotation.getAnnotationTypeRef());
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firAnnotationCallBuilder.getAnnotationTypeRef()), getSession()), getSession());
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null || (firConstructorSymbol = (FirConstructorSymbol) CollectionsKt.firstOrNull((List) FirScopeKt.getDeclaredConstructors(FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) firRegularClassSymbol, getSession(), getScopeSession(), false, (FirResolvePhase) null)))) == null) {
            return null;
        }
        List<FirValueParameterSymbol> valueParameterSymbols = firConstructorSymbol.getValueParameterSymbols();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = valueParameterSymbols.iterator();
        while (it.hasNext()) {
            FirValueParameter firValueParameter = (FirValueParameter) ((FirValueParameterSymbol) it.next()).getFir();
            FirExpression firExpression = firAnnotation.getArgumentMapping().getMapping().get(firValueParameter.getName());
            Pair pair = firExpression == null ? null : TuplesKt.to(firExpression, firValueParameter);
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        firAnnotationCallBuilder.setArgumentList(FirArgumentUtilKt.buildResolvedArgumentList$default((LinkedHashMap) MapsKt.toMap(arrayList, new LinkedHashMap()), null, 2, null));
        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
        Name shortClassName = firRegularClassSymbol.getClassId().getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        firResolvedNamedReferenceBuilder.setName(shortClassName);
        firResolvedNamedReferenceBuilder.setResolvedSymbol(firConstructorSymbol);
        firAnnotationCallBuilder.setCalleeReference(firResolvedNamedReferenceBuilder.build());
        return firAnnotationCallBuilder.mo6809build();
    }

    public final IrExpression convertToGetObject$fir2ir(FirResolvedQualifier qualifier) {
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        IrExpression irExpressionConvertToGetObject$fir2ir = convertToGetObject$fir2ir(qualifier, null);
        Intrinsics.checkNotNull(irExpressionConvertToGetObject$fir2ir);
        return irExpressionConvertToGetObject$fir2ir;
    }

    public final IrExpression convertToGetObject$fir2ir(FirResolvedQualifier qualifier, FirCallableReferenceAccess callableReferenceAccess) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrErrorCallExpressionImpl irErrorCallExpressionImpl;
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(qualifier.getTypeRef());
        ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
        FirClassLikeSymbol<?> symbol = (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : LookupTagUtilsKt.toSymbol(lookupTag, getSession());
        if (callableReferenceAccess != null && !FirExpressionUtilKt.isBound(callableReferenceAccess)) {
            return null;
        }
        IrType irType = toIrType(qualifier.getTypeRef());
        FirResolvedQualifier firResolvedQualifier = qualifier;
        KtSourceElement source = firResolvedQualifier.getSource();
        KtSourceElement ktSourceElement = source;
        int i = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        if (symbol != null) {
            IrClassifierSymbol symbol$default = ConversionUtilsKt.toSymbol$default(this, symbol, null, null, 6, null);
            Intrinsics.checkNotNull(symbol$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
            irErrorCallExpressionImpl = new IrGetObjectValueImpl(i, endOffset, irType, (IrClassSymbol) symbol$default);
        } else {
            irErrorCallExpressionImpl = new IrErrorCallExpressionImpl(i, endOffset, irType, "Resolved qualifier " + UtilsKt.render(firResolvedQualifier) + " does not have correctly resolved type");
        }
        return (IrExpression) irErrorCallExpressionImpl;
    }

    private final ConeSubstitutor buildSubstitutorByCalledFunction(FirFunctionCall firFunctionCall, FirFunction firFunction) {
        if (firFunction == null) {
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i = 0;
        for (FirTypeParameterRef firTypeParameterRef : firFunction.getTypeParameters()) {
            int i2 = i + 1;
            Object orNull = CollectionsKt.getOrNull(firFunctionCall.getTypeArguments(), i);
            FirTypeProjectionWithVariance firTypeProjectionWithVariance = orNull instanceof FirTypeProjectionWithVariance ? (FirTypeProjectionWithVariance) orNull : null;
            if (firTypeProjectionWithVariance != null) {
                linkedHashMap.put(firTypeParameterRef.getSymbol(), FirTypeUtilsKt.getConeType(firTypeProjectionWithVariance.getTypeRef()));
            }
            i = i2;
        }
        return new ConeSubstitutorByMap(linkedHashMap, getSession());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Triple<List<FirValueParameter>, Map<FirExpression, FirValueParameter>, ConeSubstitutor> extractArgumentsMapping(FirCall call) {
        FirNamedReference calleeReference;
        ConeSubstitutor.Empty emptyBuildSubstitutorByCalledFunction;
        boolean z = call instanceof FirFunctionCall;
        if (z) {
            calleeReference = ((FirFunctionCall) call).getCalleeReference();
        } else if (call instanceof FirDelegatedConstructorCall) {
            calleeReference = ((FirDelegatedConstructorCall) call).getCalleeReference();
        } else {
            calleeReference = call instanceof FirAnnotationCall ? ((FirAnnotationCall) call).getCalleeReference() : null;
        }
        FirResolvedNamedReference firResolvedNamedReference = calleeReference instanceof FirResolvedNamedReference ? (FirResolvedNamedReference) calleeReference : null;
        FirBasedSymbol<?> resolvedSymbol = firResolvedNamedReference != null ? firResolvedNamedReference.getResolvedSymbol() : null;
        FirFunctionSymbol firFunctionSymbol = resolvedSymbol instanceof FirFunctionSymbol ? (FirFunctionSymbol) resolvedSymbol : null;
        FirFunction firFunction = firFunctionSymbol != null ? (FirFunction) firFunctionSymbol.getFir() : null;
        List<FirValueParameter> valueParameters = firFunction != null ? firFunction.getValueParameters() : null;
        FirArgumentList argumentList = call.getArgumentList();
        LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
        FirFunctionCall firFunctionCall = z ? (FirFunctionCall) call : null;
        if (firFunctionCall == null || (emptyBuildSubstitutorByCalledFunction = buildSubstitutorByCalledFunction(firFunctionCall, firFunction)) == null) {
            emptyBuildSubstitutorByCalledFunction = ConeSubstitutor.Empty.INSTANCE;
        }
        return new Triple<>(valueParameters, mapping, emptyBuildSubstitutorByCalledFunction);
    }

    public final IrExpression applyCallArguments$fir2ir(IrExpression irExpression, FirStatement firStatement) {
        FirValueParameter firValueParameter;
        Object next;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        FirCall firCall = firStatement instanceof FirCall ? (FirCall) firStatement : null;
        if (irExpression instanceof IrMemberAccessExpression) {
            IrMemberAccessExpression<?> irMemberAccessExpression = (IrMemberAccessExpression) irExpression;
            int iPutContextReceiverArguments = putContextReceiverArguments(irMemberAccessExpression, firStatement);
            if (firCall == null) {
                return irExpression;
            }
            int size = firCall.getArgumentList().getArguments().size();
            if (size <= irMemberAccessExpression.getValueArgumentsCount()) {
                Triple<List<FirValueParameter>, Map<FirExpression, FirValueParameter>, ConeSubstitutor> tripleExtractArgumentsMapping = extractArgumentsMapping(firCall);
                List<FirValueParameter> listComponent1 = tripleExtractArgumentsMapping.component1();
                Map<FirExpression, FirValueParameter> mapComponent2 = tripleExtractArgumentsMapping.component2();
                ConeSubstitutor coneSubstitutorComponent3 = tripleExtractArgumentsMapping.component3();
                if (mapComponent2 != null && ((this.visitor.get_annotationMode() || !mapComponent2.isEmpty()) && listComponent1 != null)) {
                    return applyArgumentsWithReorderingIfNeeded(irMemberAccessExpression, mapComponent2, listComponent1, coneSubstitutorComponent3, iPutContextReceiverArguments, firCall);
                }
                int iIntValue = 0;
                for (FirExpression firExpression : firCall.getArgumentList().getArguments()) {
                    int i = iIntValue + 1;
                    if (!(firExpression instanceof FirNamedArgumentExpression) || listComponent1 == null) {
                        firValueParameter = null;
                    } else {
                        Iterator<T> it = listComponent1.iterator();
                        do {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                        } while (!Intrinsics.areEqual(((FirValueParameter) next).getName(), ((FirNamedArgumentExpression) firExpression).getName()));
                        firValueParameter = (FirValueParameter) next;
                    }
                    if (firValueParameter == null) {
                        firValueParameter = listComponent1 != null ? listComponent1.get(iIntValue) : null;
                    }
                    IrExpression irExpressionConvertArgument = convertArgument(firExpression, firValueParameter, coneSubstitutorComponent3);
                    if (listComponent1 != null) {
                        Integer numValueOf = Integer.valueOf(CollectionsKt.indexOf((List<? extends FirValueParameter>) listComponent1, firValueParameter));
                        if (!Boolean.valueOf(numValueOf.intValue() >= 0).booleanValue()) {
                            numValueOf = null;
                        }
                        if (numValueOf != null) {
                            iIntValue = numValueOf.intValue();
                        }
                    }
                    irMemberAccessExpression.putValueArgument(iIntValue + iPutContextReceiverArguments, irExpressionConvertArgument);
                    iIntValue = i;
                }
                return irExpression;
            }
            IrErrorCallExpressionImpl irErrorCallExpressionImpl = new IrErrorCallExpressionImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), "Cannot bind " + size + " arguments to " + (irExpression instanceof IrCallImpl ? ((IrCallImpl) irExpression).getSymbol().getOwner().getName() : "???") + " call with " + irMemberAccessExpression.getValueArgumentsCount() + " parameters");
            Iterator<FirExpression> it2 = firCall.getArgumentList().getArguments().iterator();
            while (it2.hasNext()) {
                irErrorCallExpressionImpl.addArgument(Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, it2.next(), false, 2, null));
            }
            return irErrorCallExpressionImpl;
        }
        if (!(irExpression instanceof IrDynamicOperatorExpression)) {
            if (!(irExpression instanceof IrErrorCallExpressionImpl)) {
                return irExpression;
            }
            IrErrorCallExpressionImpl irErrorCallExpressionImpl2 = (IrErrorCallExpressionImpl) irExpression;
            List<FirExpression> arguments = firCall != null ? firCall.getArgumentList().getArguments() : null;
            if (arguments == null) {
                arguments = CollectionsKt.emptyList();
            }
            Iterator<FirExpression> it3 = arguments.iterator();
            while (it3.hasNext()) {
                irErrorCallExpressionImpl2.addArgument(Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, it3.next(), false, 2, null));
            }
            return irExpression;
        }
        IrDynamicOperatorExpression irDynamicOperatorExpression = (IrDynamicOperatorExpression) irExpression;
        if (firCall == null) {
            return irExpression;
        }
        Triple<List<FirValueParameter>, Map<FirExpression, FirValueParameter>, ConeSubstitutor> tripleExtractArgumentsMapping2 = extractArgumentsMapping(firCall);
        List<FirValueParameter> listComponent2 = tripleExtractArgumentsMapping2.component1();
        Map<FirExpression, FirValueParameter> mapComponent3 = tripleExtractArgumentsMapping2.component2();
        ConeSubstitutor coneSubstitutorComponent4 = tripleExtractArgumentsMapping2.component3();
        if (mapComponent3 == null) {
            return irExpression;
        }
        if ((!this.visitor.get_annotationMode() && mapComponent3.isEmpty()) || listComponent2 == null) {
            return irExpression;
        }
        Object objFirstOrNull = CollectionsKt.firstOrNull(mapComponent3.keySet());
        FirVarargArgumentsExpression firVarargArgumentsExpression = objFirstOrNull instanceof FirVarargArgumentsExpression ? (FirVarargArgumentsExpression) objFirstOrNull : null;
        if (firVarargArgumentsExpression == null) {
            throw new IllegalStateException("Dynamic call must have a single vararg argument".toString());
        }
        Iterator<FirExpression> it4 = firVarargArgumentsExpression.getArguments().iterator();
        while (it4.hasNext()) {
            irDynamicOperatorExpression.getArguments().add(convertArgument(it4.next(), null, coneSubstitutorComponent4));
        }
        return irExpression;
    }

    private final int putContextReceiverArguments(IrMemberAccessExpression<?> irMemberAccessExpression, FirStatement firStatement) {
        if (!(firStatement instanceof FirContextReceiverArgumentListOwner)) {
            return 0;
        }
        FirContextReceiverArgumentListOwner firContextReceiverArgumentListOwner = (FirContextReceiverArgumentListOwner) firStatement;
        int size = firContextReceiverArgumentListOwner.getContextReceiverArguments().size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                irMemberAccessExpression.putValueArgument(i, Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, firContextReceiverArgumentListOwner.getContextReceiverArguments().get(i), false, 2, null));
            }
        }
        return size;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpression applyArgumentsWithReorderingIfNeeded(IrMemberAccessExpression<?> irMemberAccessExpression, Map<FirExpression, ? extends FirValueParameter> map, List<? extends FirValueParameter> list, ConeSubstitutor coneSubstitutor, int i, FirCall firCall) {
        IrVarargImpl irVarargImpl;
        FirCallableSymbol resolvedCallableSymbol$default;
        Set<Map.Entry<FirExpression, ? extends FirValueParameter>> setEntrySet = map.entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            FirExpression firExpression = (FirExpression) entry.getKey();
            FirValueParameter firValueParameter = (FirValueParameter) entry.getValue();
            arrayList.add(TuplesKt.to(firValueParameter, convertArgument(firExpression, firValueParameter, coneSubstitutor)));
        }
        ArrayList<Pair> arrayList2 = arrayList;
        if (!this.visitor.get_annotationMode()) {
            ArrayList arrayList3 = arrayList2;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    if (!IrExpressionsKt.hasNoSideEffects((IrExpression) ((Pair) it2.next()).component2())) {
                        if (!needArgumentReordering(map.values(), list)) {
                            break;
                        }
                        IrBlockImpl irBlockImpl = new IrBlockImpl(irMemberAccessExpression.getStartOffset(), irMemberAccessExpression.getEndOffset(), irMemberAccessExpression.getType(), IrStatementOrigin.ARGUMENTS_REORDERING_FOR_CALL.INSTANCE);
                        IrExpression dispatchReceiver = irMemberAccessExpression.getDispatchReceiver();
                        irMemberAccessExpression.setDispatchReceiver(dispatchReceiver != null ? applyArgumentsWithReorderingIfNeeded$lambda$45$freeze(dispatchReceiver, this, irBlockImpl, "$this") : null);
                        IrExpression extensionReceiver = irMemberAccessExpression.getExtensionReceiver();
                        irMemberAccessExpression.setExtensionReceiver(extensionReceiver != null ? applyArgumentsWithReorderingIfNeeded$lambda$45$freeze(extensionReceiver, this, irBlockImpl, AsmUtil.RECEIVER_PARAMETER_NAME) : null);
                        for (Pair pair : arrayList2) {
                            FirValueParameter firValueParameter2 = (FirValueParameter) pair.component1();
                            IrExpression irExpression = (IrExpression) pair.component2();
                            int iIndexOf = list.indexOf(firValueParameter2) + i;
                            String strAsString = firValueParameter2.getName().asString();
                            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                            irMemberAccessExpression.putValueArgument(iIndexOf, applyArgumentsWithReorderingIfNeeded$lambda$45$freeze(irExpression, this, irBlockImpl, strAsString));
                        }
                        irBlockImpl.getStatements().add(irMemberAccessExpression);
                        return irBlockImpl;
                    }
                }
            }
        }
        for (Pair pair2 : arrayList2) {
            irMemberAccessExpression.putValueArgument(list.indexOf((FirValueParameter) pair2.component1()) + i, (IrExpression) pair2.component2());
        }
        if (this.visitor.get_annotationMode()) {
            FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(firCall);
            int i2 = 0;
            FirCallableDeclaration firCallableDeclaration = (calleeReference == null || (resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null)) == null) ? null : (FirCallableDeclaration) resolvedCallableSymbol$default.getFir();
            FirFunction firFunction = firCallableDeclaration instanceof FirFunction ? (FirFunction) firCallableDeclaration : null;
            for (FirValueParameter firValueParameter3 : list) {
                int i3 = i2 + 1;
                if (firValueParameter3.getIsVararg() && !map.containsValue(firValueParameter3)) {
                    if (firFunction == null || !DeclarationUtilsKt.itOrExpectHasDefaultParameterValue(firFunction, i2)) {
                        IrType irType = toIrType(firValueParameter3.getReturnTypeRef());
                        irVarargImpl = new IrVarargImpl(-1, -1, irType, org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.toArrayOrPrimitiveArrayType(irType, getIrBuiltIns()));
                    } else {
                        irVarargImpl = null;
                    }
                    irMemberAccessExpression.putValueArgument(i2, irVarargImpl);
                }
                i2 = i3;
            }
        }
        return irMemberAccessExpression;
    }

    private static final IrExpression applyArgumentsWithReorderingIfNeeded$lambda$45$freeze(IrExpression irExpression, CallAndReferenceGenerator callAndReferenceGenerator, IrBlockImpl irBlockImpl, String str) {
        if (IrExpressionsKt.isUnchanging(irExpression)) {
            return irExpression;
        }
        Pair<IrVariable, IrValueSymbol> pairCreateTemporaryVariable = ConversionUtilsKt.createTemporaryVariable(callAndReferenceGenerator, irExpression, callAndReferenceGenerator.conversionScope, str);
        IrVariable irVariableComponent1 = pairCreateTemporaryVariable.component1();
        IrValueSymbol irValueSymbolComponent2 = pairCreateTemporaryVariable.component2();
        irBlockImpl.getStatements().add(irVariableComponent1);
        return new IrGetValueImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irValueSymbolComponent2, null);
    }

    private final boolean needArgumentReordering(Collection<? extends FirValueParameter> parametersInActualOrder, List<? extends FirValueParameter> valueParameters) {
        Iterator<? extends FirValueParameter> it = parametersInActualOrder.iterator();
        int i = -1;
        while (it.hasNext()) {
            int iIndexOf = valueParameters.indexOf(it.next());
            if (iIndexOf < i) {
                return true;
            }
            i = iIndexOf;
        }
        return false;
    }

    private final IrExpression convertArgument(FirExpression argument, FirValueParameter parameter, ConeSubstitutor substitutor) {
        IrExpression irExpressionConvertToIrExpression$fir2ir$default = Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, argument, false, 2, null);
        if (parameter != null) {
            irExpressionConvertToIrExpression$fir2ir$default = this.visitor.getImplicitCastInserter().cast$fir2ir(irExpressionConvertToIrExpression$fir2ir$default, argument, argument.getTypeRef(), parameter.getReturnTypeRef());
        }
        AdapterGenerator adapterGenerator = this.adapterGenerator;
        if ((parameter != null ? parameter.getReturnTypeRef() : null) instanceof FirResolvedTypeRef) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(parameter.getReturnTypeRef());
            if (parameter.getIsVararg()) {
                coneType = ArrayUtilsKt.arrayElementType(coneType);
                Intrinsics.checkNotNull(coneType);
            }
            ConeKotlinType functionTypeForPossibleSamType$fir2ir = adapterGenerator.getFunctionTypeForPossibleSamType$fir2ir(coneType);
            if (functionTypeForPossibleSamType$fir2ir != null) {
                coneType = functionTypeForPossibleSamType$fir2ir;
            }
            irExpressionConvertToIrExpression$fir2ir$default = AdapterGenerator.applySamConversionIfNeeded$fir2ir$default(adapterGenerator, adapterGenerator.applySuspendConversionIfNeeded$fir2ir(irExpressionConvertToIrExpression$fir2ir$default, argument, coneType), argument, parameter, substitutor, false, 8, null);
        }
        return applyImplicitIntegerCoercionIfNeeded(applyAssigningArrayElementsToVarargInNamedForm(irExpressionConvertToIrExpression$fir2ir$default, argument, parameter), argument, parameter);
    }

    private final IrExpression applyAssigningArrayElementsToVarargInNamedForm(IrExpression irExpression, FirExpression firExpression, FirValueParameter firValueParameter) {
        if ((irExpression instanceof IrVarargImpl) && firValueParameter != null && firValueParameter.getIsVararg() && (firExpression instanceof FirVarargArgumentsExpression)) {
            FirVarargArgumentsExpression firVarargArgumentsExpression = (FirVarargArgumentsExpression) firExpression;
            List<FirExpression> arguments = firVarargArgumentsExpression.getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    if (((FirExpression) it.next()) instanceof FirNamedArgumentExpression) {
                        IrVarargImpl irVarargImpl = (IrVarargImpl) irExpression;
                        int i = 0;
                        for (Object obj : irVarargImpl.getElements()) {
                            int i2 = i + 1;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }
                            IrVarargElement irVarargElement = (IrVarargElement) obj;
                            if (!(irVarargElement instanceof IrSpreadElement) && (firVarargArgumentsExpression.getArguments().get(i) instanceof FirNamedArgumentExpression) && (irVarargElement instanceof IrExpression)) {
                                IrExpression irExpression2 = (IrExpression) irVarargElement;
                                if (IrTypePredicatesKt.isArray(irExpression2.getType())) {
                                    irVarargImpl.getElements().set(i, new IrSpreadElementImpl(irVarargElement.getStartOffset(), irVarargElement.getEndOffset(), irExpression2));
                                }
                            }
                            i = i2;
                        }
                        break;
                    }
                }
            }
        }
        return irExpression;
    }

    private final IrExpression applyImplicitIntegerCoercionIfNeeded(IrExpression irExpression, FirExpression firExpression, FirValueParameter firValueParameter) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        if (!FirLanguageSettingsComponentKt.getLanguageVersionSettings(getSession()).supportsFeature(LanguageFeature.ImplicitSignedToUnsignedIntegerConversion) || firValueParameter == null) {
            return irExpression;
        }
        FirValueParameter firValueParameter2 = firValueParameter;
        if (!ImplicitIntegerCoercionKt.isMarkedWithImplicitIntegerCoercion(firValueParameter2) || !ConeBuiltinTypeUtilsKt.isUnsignedTypeOrNullableUnsignedType(TypeExpansionUtilsKt.fullyExpandedType(ArgumentsKt.getExpectedType(firExpression, firValueParameter), getSession())) || !ImplicitIntegerCoercionKt.isMarkedWithImplicitIntegerCoercion(firValueParameter2)) {
            return irExpression;
        }
        int i = 0;
        if ((irExpression instanceof IrVarargImpl) && (firExpression instanceof FirVarargArgumentsExpression)) {
            IrVarargImpl irVarargImpl = (IrVarargImpl) irExpression;
            FqName classFqName = IrTypesKt.getClassFqName(irVarargImpl.getVarargElementType());
            if (classFqName == null) {
                return irExpression;
            }
            IrBuiltInsOverFir irBuiltIns = getIrBuiltIns();
            Name nameIdentifier = Name.identifier("to" + classFqName.shortName().asString());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            String strAsString = StandardNames.BUILT_INS_PACKAGE_NAME.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            Map<IrClassifierSymbol, IrSimpleFunctionSymbol> nonBuiltInFunctionsByExtensionReceiver = irBuiltIns.getNonBuiltInFunctionsByExtensionReceiver(nameIdentifier, strAsString);
            if (!nonBuiltInFunctionsByExtensionReceiver.isEmpty()) {
                for (Object obj : irVarargImpl.getElements()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrVarargElement irVarargElement = (IrVarargElement) obj;
                    FirVarargArgumentsExpression firVarargArgumentsExpression = (FirVarargArgumentsExpression) firExpression;
                    IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(toIrType(firVarargArgumentsExpression.getArguments().get(i).getTypeRef()));
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = classifierOrNull != null ? nonBuiltInFunctionsByExtensionReceiver.get(classifierOrNull) : null;
                    if (irSimpleFunctionSymbol2 != null && (irVarargElement instanceof IrExpression)) {
                        irVarargImpl.getElements().set(i, applyImplicitIntegerCoercionIfNeeded$applyToElement((IrExpression) irVarargElement, firVarargArgumentsExpression.getArguments().get(i), irSimpleFunctionSymbol2));
                    }
                    i = i2;
                }
            }
            return irExpression;
        }
        FqName classFqName2 = IrTypesKt.getClassFqName(toIrType(firValueParameter.getReturnTypeRef()));
        if (classFqName2 == null) {
            return irExpression;
        }
        IrBuiltInsOverFir irBuiltIns2 = getIrBuiltIns();
        Name nameIdentifier2 = Name.identifier("to" + classFqName2.shortName().asString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        String strAsString2 = StandardNames.BUILT_INS_PACKAGE_NAME.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> nonBuiltInFunctionsByExtensionReceiver2 = irBuiltIns2.getNonBuiltInFunctionsByExtensionReceiver(nameIdentifier2, strAsString2);
        IrClassifierSymbol classifierOrNull2 = IrTypesKt.getClassifierOrNull(toIrType(firExpression.getTypeRef()));
        return (classifierOrNull2 == null || (irSimpleFunctionSymbol = nonBuiltInFunctionsByExtensionReceiver2.get(classifierOrNull2)) == null) ? irExpression : applyImplicitIntegerCoercionIfNeeded$applyToElement(irExpression, firExpression, irSimpleFunctionSymbol);
    }

    private static final IrExpression applyImplicitIntegerCoercionIfNeeded$applyToElement(IrExpression irExpression, FirExpression firExpression, IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        FirReference calleeReference;
        FirCallableSymbol resolvedCallableSymbol$default;
        if (!ResolveUtilsKt.isIntegerLiteralOrOperatorCall(firExpression) && ((calleeReference = FirExpressionUtilKt.getCalleeReference(firExpression)) == null || (resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null)) == null || !resolvedCallableSymbol$default.getResolvedStatus().isConst() || !ImplicitIntegerCoercionKt.isMarkedWithImplicitIntegerCoercion((FirCallableSymbol<?>) resolvedCallableSymbol$default))) {
            return irExpression;
        }
        IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irSimpleFunctionSymbol.getOwner().getReturnType(), irSimpleFunctionSymbol, 0, 0, null, null, 192, null);
        irCallImpl.setExtensionReceiver(irExpression);
        return irCallImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrExpression applyTypeArguments$fir2ir(IrExpression irExpression, FirQualifiedAccessExpression access) {
        FirTypeAliasSymbol typeAliasForConstructor;
        List<FirTypeProjection> expandedTypeArguments;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(access, "access");
        FirReference calleeReference = access.getCalleeReference();
        List<FirTypeProjection> typeArguments = access.getTypeArguments();
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null);
        FirAnnotationContainer firAnnotationContainer = resolvedCallableSymbol$default != null ? (FirCallableDeclaration) resolvedCallableSymbol$default.getFir() : null;
        FirConstructor firConstructor = firAnnotationContainer instanceof FirConstructor ? (FirConstructor) firAnnotationContainer : null;
        if (firConstructor != null && (typeAliasForConstructor = org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt.getTypeAliasForConstructor(firConstructor)) != null && (expandedTypeArguments = toExpandedTypeArguments(typeArguments, typeAliasForConstructor)) != null) {
            typeArguments = expandedTypeArguments;
        }
        FirTypeParametersOwner firTypeParametersOwner = firAnnotationContainer instanceof FirTypeParametersOwner ? (FirTypeParametersOwner) firAnnotationContainer : null;
        return applyTypeArguments(irExpression, typeArguments, firTypeParametersOwner != null ? firTypeParametersOwner.getTypeParameters() : null);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x008f  */
    private final List<FirTypeProjection> toExpandedTypeArguments(List<? extends FirTypeProjection> list, FirTypeAliasSymbol firTypeAliasSymbol) {
        Variance variance;
        FirResolvedTypeRef firResolvedTypeRefBuild;
        FirTypeAliasSymbol firTypeAliasSymbol2 = firTypeAliasSymbol;
        List<? extends FirTypeProjection> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.toConeTypeProjection((FirTypeProjection) it.next()));
        }
        ConeTypeProjection[] typeArguments = TypeExpansionUtilsKt.fullyExpandedType$default(TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) firTypeAliasSymbol2, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]), false, (ConeAttributes) null, 4, (Object) null), getSession(), null, 2, null).getTypeArguments();
        ArrayList arrayList2 = new ArrayList(typeArguments.length);
        for (ConeTypeProjection coneTypeProjection : typeArguments) {
            FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
            if (coneTypeProjection instanceof ConeKotlinTypeProjectionIn) {
                variance = Variance.IN_VARIANCE;
            } else {
                variance = coneTypeProjection instanceof ConeKotlinTypeProjectionOut ? Variance.OUT_VARIANCE : Variance.INVARIANT;
            }
            firTypeProjectionWithVarianceBuilder.setVariance(variance);
            ConeKotlinType coneKotlinType = coneTypeProjection instanceof ConeKotlinType ? (ConeKotlinType) coneTypeProjection : null;
            if (coneKotlinType != null) {
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setType(coneKotlinType);
                firResolvedTypeRefBuild = firResolvedTypeRefBuilder.mo6809build();
                if (firResolvedTypeRefBuild == null) {
                    FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                    firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("Expansion contains unexpected type " + coneTypeProjection.getClass(), null, 2, null));
                    firResolvedTypeRefBuild = firErrorTypeRefBuilder.mo6809build();
                }
            } else {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder2.setDiagnostic(new ConeSimpleDiagnostic("Expansion contains unexpected type " + coneTypeProjection.getClass(), null, 2, null));
                firResolvedTypeRefBuild = firErrorTypeRefBuilder2.mo6809build();
            }
            firTypeProjectionWithVarianceBuilder.setTypeRef(firResolvedTypeRefBuild);
            arrayList2.add(firTypeProjectionWithVarianceBuilder.build());
        }
        return arrayList2;
    }

    private final IrExpression applyTypeArguments(IrExpression irExpression, List<? extends FirTypeProjection> list, List<? extends FirTypeParameter> list2) {
        IrType irType;
        if (!(irExpression instanceof IrMemberAccessExpression) || list == null) {
            return irExpression;
        }
        int size = list.size();
        IrMemberAccessExpression irMemberAccessExpression = (IrMemberAccessExpression) irExpression;
        if (size <= irMemberAccessExpression.getTypeArgumentsCount()) {
            int i = 0;
            for (FirTypeProjection firTypeProjection : list) {
                int i2 = i + 1;
                FirTypeParameter firTypeParameter = list2 != null ? list2.get(i) : null;
                Intrinsics.checkNotNull(firTypeProjection, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance");
                FirTypeRef typeRef = ((FirTypeProjectionWithVariance) firTypeProjection).getTypeRef();
                if (firTypeParameter != null && firTypeParameter.getIsReified()) {
                    irType = toIrType(DeclarationApproximationUtilsKt.approximateDeclarationType$default(typeRef, getSession(), null, false, false, false, 8, null));
                } else {
                    irType = toIrType(typeRef);
                }
                irMemberAccessExpression.putTypeArgument(i, irType);
                i = i2;
            }
            return irExpression;
        }
        return new IrErrorExpressionImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), "Cannot bind " + size + " type arguments to " + (irExpression instanceof IrCallImpl ? ((IrCallImpl) irExpression).getSymbol().getOwner().getName() : "???") + " call with " + irMemberAccessExpression.getTypeArgumentsCount() + " type parameters");
    }

    private final IrExpression findIrDispatchReceiver(FirQualifiedAccessExpression firQualifiedAccessExpression, IrExpression irExpression) {
        return findIrReceiver$fir2ir(firQualifiedAccessExpression, irExpression, true);
    }

    private final IrExpression findIrExtensionReceiver(FirQualifiedAccessExpression firQualifiedAccessExpression, IrExpression irExpression) {
        return findIrReceiver$fir2ir(firQualifiedAccessExpression, irExpression, false);
    }

    public final IrExpression findIrReceiver$fir2ir(FirQualifiedAccessExpression firQualifiedAccessExpression, IrExpression irExpression, boolean z) {
        Intrinsics.checkNotNullParameter(firQualifiedAccessExpression, "<this>");
        FirExpression dispatchReceiver = z ? firQualifiedAccessExpression.getDispatchReceiver() : firQualifiedAccessExpression.getExtensionReceiver();
        if (Intrinsics.areEqual(dispatchReceiver, firQualifiedAccessExpression.getExplicitReceiver())) {
            return irExpression;
        }
        if (dispatchReceiver instanceof FirNoReceiverExpression) {
            dispatchReceiver = null;
        }
        if (dispatchReceiver == null) {
            return irExpression;
        }
        IrExpression irExpressionConvertToIrReceiverExpression$fir2ir = this.visitor.convertToIrReceiverExpression$fir2ir(dispatchReceiver, firQualifiedAccessExpression.getCalleeReference(), firQualifiedAccessExpression instanceof FirCallableReferenceAccess ? (FirCallableReferenceAccess) firQualifiedAccessExpression : null);
        return irExpressionConvertToIrReceiverExpression$fir2ir != null ? irExpressionConvertToIrReceiverExpression$fir2ir : irExpression;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpression applyReceivers(IrExpression irExpression, FirQualifiedAccessExpression firQualifiedAccessExpression, IrExpression irExpression2) {
        FirTypeRef typeRef;
        IrExpression irExpressionCast$fir2ir;
        IrType type;
        IrClassSymbol classOrNull;
        IrClass owner;
        if (irExpression instanceof IrMemberAccessExpression) {
            FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(firQualifiedAccessExpression);
            IrExpression irExpression3 = null;
            if ((resolvedCallableSymbol != null ? resolvedCallableSymbol.getDispatchReceiverType() : null) != null) {
                IrTypeOperatorCallImpl irTypeOperatorCallImplFindIrDispatchReceiver = findIrDispatchReceiver(firQualifiedAccessExpression, irExpression2);
                IrMemberAccessExpression irMemberAccessExpression = (IrMemberAccessExpression) irExpression;
                if (isFunctionFromAny(resolvedCallableSymbol) && irTypeOperatorCallImplFindIrDispatchReceiver != null && (type = irTypeOperatorCallImplFindIrDispatchReceiver.getType()) != null && (classOrNull = IrTypesKt.getClassOrNull(type)) != null && (owner = classOrNull.getOwner()) != null && IrUtilsKt.isInterface(owner)) {
                    irTypeOperatorCallImplFindIrDispatchReceiver = new IrTypeOperatorCallImpl(irTypeOperatorCallImplFindIrDispatchReceiver.getStartOffset(), irTypeOperatorCallImplFindIrDispatchReceiver.getEndOffset(), getIrBuiltIns().getAnyType(), IrTypeOperator.IMPLICIT_CAST, getIrBuiltIns().getAnyType(), irTypeOperatorCallImplFindIrDispatchReceiver);
                }
                irMemberAccessExpression.setDispatchReceiver(irTypeOperatorCallImplFindIrDispatchReceiver);
            }
            if ((resolvedCallableSymbol != null ? resolvedCallableSymbol.getReceiverParameter() : null) != null && !(resolvedCallableSymbol instanceof FirConstructorSymbol)) {
                IrMemberAccessExpression irMemberAccessExpression2 = (IrMemberAccessExpression) irExpression;
                IrExpression irExpressionFindIrExtensionReceiver = findIrExtensionReceiver(firQualifiedAccessExpression, irExpression2);
                if (irExpressionFindIrExtensionReceiver != null) {
                    FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null);
                    if (resolvedCallableSymbol$default == null) {
                        throw new IllegalStateException(("Symbol for call " + UtilsKt.render(firQualifiedAccessExpression) + " not found").toString());
                    }
                    FirReceiverParameter receiverParameter = ((FirCallableDeclaration) resolvedCallableSymbol$default.getFir()).getReceiverParameter();
                    irExpression3 = (receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null || (irExpressionCast$fir2ir = this.visitor.getImplicitCastInserter().cast$fir2ir(irExpressionFindIrExtensionReceiver, firQualifiedAccessExpression.getExtensionReceiver(), firQualifiedAccessExpression.getExtensionReceiver().getTypeRef(), typeRef)) == null) ? irExpressionFindIrExtensionReceiver : irExpressionCast$fir2ir;
                }
                irMemberAccessExpression2.setExtensionReceiver(irExpression3);
            }
        } else if (irExpression instanceof IrFieldAccessExpression) {
            IrFieldAccessExpression irFieldAccessExpression = (IrFieldAccessExpression) irExpression;
            if (!irFieldAccessExpression.getSymbol().getOwner().getIsStatic()) {
                irFieldAccessExpression.setReceiver(findIrDispatchReceiver(firQualifiedAccessExpression, irExpression2));
            }
        }
        return irExpression;
    }

    private final boolean isFunctionFromAny(FirCallableSymbol<?> firCallableSymbol) {
        if (firCallableSymbol instanceof FirNamedFunctionSymbol) {
            return FirDeclarationUtilKt.isMethodOfAny((FirNamedFunctionSymbol) firCallableSymbol);
        }
        return false;
    }

    static /* synthetic */ IrErrorCallExpression generateErrorCallExpression$default(CallAndReferenceGenerator callAndReferenceGenerator, int i, int i2, FirReference firReference, IrType irType, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            irType = null;
        }
        return callAndReferenceGenerator.generateErrorCallExpression(i, i2, firReference, irType);
    }

    private final IrErrorCallExpression generateErrorCallExpression(int startOffset, int endOffset, FirReference calleeReference, IrType type) {
        if (type == null) {
            type = ConversionUtilsKt.createErrorType();
        }
        return new IrErrorCallExpressionImpl(startOffset, endOffset, type, "Unresolved reference: " + UtilsKt.render(calleeReference));
    }
}
