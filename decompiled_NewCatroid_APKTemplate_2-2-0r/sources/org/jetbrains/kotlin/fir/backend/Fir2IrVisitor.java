package org.jetbrains.kotlin.fir.backend;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.ClassMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.backend.generators.OperatorExpressionGenerator;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.DestructuringDeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.utils.FirScriptUtilsKt;
import org.jetbrains.kotlin.fir.deserialization.FirEnumEntryDeserializerAccessUtilKt;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatusKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEnumEntryDeserializedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirInaccessibleReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirJump;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
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
import org.jetbrains.kotlin.fir.expressions.impl.FirContractCallBlock;
import org.jetbrains.kotlin.fir.expressions.impl.FirElseIfTrueCondition;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.expressions.impl.FirUnitExpression;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.CallableIdUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContextInterface;
import org.jetbrains.kotlin.ir.builders.PrimitivesKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBreakImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCatchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrContinueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorCallExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetClassImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrThrowImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrErrorClassImpl;
import org.jetbrains.kotlin.ir.types.impl.IrErrorTypeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: Fir2IrVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0086\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00022\u00020\u0005B\u0015\u0012\u0006\u0010\u0006\u001a\u00020\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001f\u0010v\u001a\u0002Hw\"\b\b\u0000\u0010w*\u00020x2\u0006\u0010y\u001a\u0002HwH\u0002¢\u0006\u0002\u0010zJ\u0018\u0010{\u001a\u00020\u00032\u0006\u0010|\u001a\u00020}2\u0006\u0010~\u001a\u00020\u000bH\u0002J\u0013\u0010\u007f\u001a\u00030\u0080\u00012\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001H\u0002J\u0014\u0010\u0083\u0001\u001a\u00030\u0084\u00012\b\u0010\u0085\u0001\u001a\u00030\u0086\u0001H\u0002J\u0014\u0010\u0087\u0001\u001a\u00030\u0080\u00012\b\u0010\u0088\u0001\u001a\u00030\u0089\u0001H\u0002J\u001a\u0010\u008a\u0001\u001a\u00030\u008b\u00012\b\u0010\u008c\u0001\u001a\u00030\u008d\u0001H\u0000¢\u0006\u0003\b\u008e\u0001J\u0014\u0010\u008f\u0001\u001a\u00030\u0080\u00012\b\u0010\u0088\u0001\u001a\u00030\u0089\u0001H\u0002J \u0010\u008f\u0001\u001a\u00030\u0080\u00012\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\n\u0010\u0090\u0001\u001a\u0005\u0018\u00010\u0091\u0001H\u0002J#\u0010\u0092\u0001\u001a\u00030\u0080\u00012\u0007\u0010\u0093\u0001\u001a\u00020q2\b\b\u0002\u0010~\u001a\u00020\u000bH\u0000¢\u0006\u0003\b\u0094\u0001J5\u0010\u0095\u0001\u001a\u0005\u0018\u00010\u0080\u00012\t\u0010\u0093\u0001\u001a\u0004\u0018\u00010q2\n\u0010\u0096\u0001\u001a\u0005\u0018\u00010\u0097\u00012\n\b\u0002\u0010|\u001a\u0004\u0018\u00010}H\u0000¢\u0006\u0003\b\u0098\u0001J\u0016\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u0089\u00012\b\u0010\u0088\u0001\u001a\u00030\u0089\u0001H\u0002JO\u0010\u009a\u0001\u001a\u00030\u0080\u00012\b\u0010\u009b\u0001\u001a\u00030\u009c\u00012\b\u0010\u009d\u0001\u001a\u00030\u009c\u00012\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010t2\n\u0010\u009f\u0001\u001a\u0005\u0018\u00010 \u00012\u000e\u0010¡\u0001\u001a\t\u0012\u0005\u0012\u00030¢\u00010H2\b\u0010£\u0001\u001a\u00030¤\u0001H\u0002J\u0016\u0010¥\u0001\u001a\u0005\u0018\u00010 \u00012\b\u0010¦\u0001\u001a\u00030§\u0001H\u0002J\u0013\u0010¨\u0001\u001a\u00020\u000b2\b\u0010©\u0001\u001a\u00030ª\u0001H\u0002J\u001e\u0010«\u0001\u001a\u00020\u00032\b\u0010¬\u0001\u001a\u00030\u00ad\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010¯\u0001\u001a\u00020\u00032\b\u0010°\u0001\u001a\u00030±\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010²\u0001\u001a\u00020\u00032\b\u0010³\u0001\u001a\u00030´\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010µ\u0001\u001a\u00020\u00032\b\u0010¶\u0001\u001a\u00030·\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010¸\u0001\u001a\u00020\u00032\b\u0010¹\u0001\u001a\u00030º\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010»\u0001\u001a\u00020\u00032\b\u0010¼\u0001\u001a\u00030½\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010¾\u0001\u001a\u00020\u00032\b\u0010¿\u0001\u001a\u00030À\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Á\u0001\u001a\u00020\u00032\b\u0010\u0085\u0001\u001a\u00030\u0086\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Â\u0001\u001a\u00020\u00032\b\u0010Ã\u0001\u001a\u00030Ä\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Å\u0001\u001a\u00020\u00032\b\u0010Æ\u0001\u001a\u00030Ç\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010È\u0001\u001a\u00020\u00032\b\u0010É\u0001\u001a\u00030Ê\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001c\u0010Ë\u0001\u001a\u00020\u00032\u0006\u0010|\u001a\u00020}2\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Ì\u0001\u001a\u00020\u00032\b\u0010Í\u0001\u001a\u00030Î\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Ï\u0001\u001a\u00020\u00032\b\u0010Ð\u0001\u001a\u00030Ñ\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Ò\u0001\u001a\u00020\u00032\b\u0010Ó\u0001\u001a\u00030Ô\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Õ\u0001\u001a\u00020\u00032\b\u0010Ö\u0001\u001a\u00030×\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Ø\u0001\u001a\u00020\u00032\b\u0010Ù\u0001\u001a\u00030Ú\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J*\u0010Û\u0001\u001a\u00020\u0003\"\u0004\b\u0000\u0010w2\u000e\u0010Ü\u0001\u001a\t\u0012\u0004\u0012\u0002Hw0Ý\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Þ\u0001\u001a\u00020\u00032\b\u0010ß\u0001\u001a\u00030à\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010á\u0001\u001a\u00020\u00032\b\u0010â\u0001\u001a\u00030ã\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010ä\u0001\u001a\u00020\u00032\b\u0010å\u0001\u001a\u00030æ\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010ç\u0001\u001a\u00020\u00032\b\u0010è\u0001\u001a\u00030é\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010ê\u0001\u001a\u00020\u00032\b\u0010ë\u0001\u001a\u00030ì\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010í\u0001\u001a\u00020\u00032\b\u0010î\u0001\u001a\u00030ï\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010ð\u0001\u001a\u00020\u00032\b\u0010ñ\u0001\u001a\u00030ò\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010ó\u0001\u001a\u00020\u00032\b\u0010ô\u0001\u001a\u00030õ\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010ö\u0001\u001a\u00020\u00032\b\u0010÷\u0001\u001a\u00030ø\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010ù\u0001\u001a\u00020\u00032\b\u0010ú\u0001\u001a\u00030û\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001f\u0010ü\u0001\u001a\u00030ý\u00012\b\u0010þ\u0001\u001a\u00030ÿ\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001f\u0010\u0080\u0002\u001a\u00030\u0081\u00022\b\u0010\u0082\u0002\u001a\u00030\u0083\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001f\u0010\u0084\u0002\u001a\u00030\u0080\u00012\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\u0085\u0002\u001a\u00020\u00032\b\u0010\u0086\u0002\u001a\u00030\u0087\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\u0088\u0002\u001a\u00020\u00032\b\u0010\u0089\u0002\u001a\u00030\u008a\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u0013\u0010\u008b\u0002\u001a\u00020\u00032\b\u0010\u008c\u0002\u001a\u00030\u008d\u0002H\u0002J\u001e\u0010\u008e\u0002\u001a\u00020\u00032\b\u0010\u008f\u0002\u001a\u00030\u008d\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\u0090\u0002\u001a\u00020\u00032\b\u0010\u0091\u0002\u001a\u00030\u0092\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\u0093\u0002\u001a\u00020\u00032\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\u0094\u0002\u001a\u00020\u00032\b\u0010\u0095\u0002\u001a\u00030\u0096\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\u0097\u0002\u001a\u00020\u00032\b\u0010\u0098\u0002\u001a\u00030\u0099\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\u009a\u0002\u001a\u00020\u00032\b\u0010\u009b\u0002\u001a\u00030\u009c\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\u009d\u0002\u001a\u00020\u00032\b\u0010\u009e\u0002\u001a\u00030\u009f\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010 \u0002\u001a\u00020\u00032\b\u0010¡\u0002\u001a\u00030¢\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010£\u0002\u001a\u00020\u00032\b\u0010¤\u0002\u001a\u00030¥\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010¦\u0002\u001a\u00020\u00032\b\u0010§\u0002\u001a\u00030¨\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010©\u0002\u001a\u00020\u00032\b\u0010ª\u0002\u001a\u00030«\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010¬\u0002\u001a\u00020\u00032\b\u0010\u00ad\u0002\u001a\u00030®\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010¯\u0002\u001a\u00020\u00032\b\u0010°\u0002\u001a\u00030±\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010²\u0002\u001a\u00020\u00032\b\u0010³\u0002\u001a\u00030´\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010µ\u0002\u001a\u00020\u00032\b\u0010¶\u0002\u001a\u00030·\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010¸\u0002\u001a\u00020\u00032\b\u0010¹\u0002\u001a\u00030º\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010»\u0002\u001a\u00020\u00032\b\u0010¼\u0002\u001a\u00030½\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010¾\u0002\u001a\u00020\u00032\b\u0010¿\u0002\u001a\u00030À\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Á\u0002\u001a\u00020\u00032\b\u0010¦\u0001\u001a\u00030§\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Â\u0002\u001a\u00020\u00032\b\u0010Ã\u0002\u001a\u00030Ä\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010Å\u0002\u001a\u00020\u00032\b\u0010Æ\u0002\u001a\u00030Ç\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010È\u0002\u001a\u00020\u00032\b\u0010É\u0002\u001a\u00030Ê\u00022\t\u0010®\u0001\u001a\u0004\u0018\u00010\u0004H\u0016J7\u0010Ë\u0002\u001a\u0002Hw\"\u0004\b\u0000\u0010w2\t\b\u0002\u0010Ì\u0002\u001a\u00020\u000b2\u000e\u0010\u008c\u0001\u001a\t\u0012\u0004\u0012\u0002Hw0Í\u0002H\u0080\bø\u0001\u0000¢\u0006\u0006\bÎ\u0002\u0010Ï\u0002J\u008b\u0001\u0010Ð\u0002\u001a\u00030\u0080\u0001*\t\u0012\u0004\u0012\u00020R0Ñ\u00022t\u0010Ò\u0002\u001ao\u0012\u0017\u0012\u00150\u009c\u0001¢\u0006\u000f\bÔ\u0002\u0012\n\bÕ\u0002\u0012\u0005\b\b(\u009b\u0001\u0012\u0017\u0012\u00150\u009c\u0001¢\u0006\u000f\bÔ\u0002\u0012\n\bÕ\u0002\u0012\u0005\b\b(\u009d\u0001\u0012\u0016\u0012\u00140S¢\u0006\u000f\bÔ\u0002\u0012\n\bÕ\u0002\u0012\u0005\b\b(Ö\u0002\u0012\u0019\u0012\u0017\u0018\u00010×\u0002¢\u0006\u000f\bÔ\u0002\u0012\n\bÕ\u0002\u0012\u0005\b\b(Ø\u0002\u0012\u0005\u0012\u00030Ù\u00020Ó\u0002H\u0002J5\u0010Ú\u0002\u001a\u00030\u0080\u0001*\t\u0012\u0005\u0012\u00030Û\u00020H2\n\u0010Ü\u0002\u001a\u0005\u0018\u00010Ý\u00022\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010t2\u0007\u0010Þ\u0002\u001a\u00020\u000bH\u0002J\u0018\u0010Ú\u0002\u001a\u00030\u0080\u0001*\u00030\u008d\u00012\u0007\u0010Þ\u0002\u001a\u00020\u000bH\u0002J,\u0010ß\u0002\u001a\u00030\u0080\u0001*\t\u0012\u0005\u0012\u00030Û\u00020H2\n\u0010Ü\u0002\u001a\u0005\u0018\u00010Ý\u00022\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010tH\u0002J\u001c\u0010ß\u0002\u001a\u00030\u0080\u0001*\u00030\u008d\u00012\u000b\b\u0002\u0010\u009e\u0001\u001a\u0004\u0018\u00010tH\u0002J\u000e\u0010à\u0002\u001a\u00030á\u0002*\u00020qH\u0002J$\u0010â\u0002\u001a\u0005\u0018\u0001Hã\u0002\"\u0007\b\u0000\u0010ã\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030HH\u0082\b¢\u0006\u0003\u0010ä\u0002J$\u0010å\u0002\u001a\u0005\u0018\u0001Hã\u0002\"\u0007\b\u0000\u0010ã\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030HH\u0082\b¢\u0006\u0003\u0010ä\u0002J \u0010æ\u0002\u001a\u0004\u0018\u00010t*\t\u0012\u0005\u0012\u00030Û\u00020H2\b\u0010ç\u0002\u001a\u00030\u009c\u0001H\u0002J,\u0010è\u0002\u001a\u00030\u0080\u0001*\u00030\u0080\u00012\u0007\u0010é\u0002\u001a\u00020q2\b\u0010ê\u0002\u001a\u00030ë\u00022\b\u0010ì\u0002\u001a\u00030ë\u0002H\u0002J(\u0010í\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010î\u00020H*\t\u0012\u0005\u0012\u00030Û\u00020H2\t\b\u0002\u0010ï\u0002\u001a\u00020\u000bH\u0002J\u0013\u0010ð\u0002\u001a\u0005\u0018\u00010ñ\u0002*\u0005\u0018\u00010ò\u0002H\u0002J\u000e\u0010ó\u0002\u001a\u00020x*\u00030ô\u0002H\u0002J\u000f\u0010õ\u0002\u001a\u00030\u0091\u0001*\u00030ö\u0002H\u0002J\u0011\u0010÷\u0002\u001a\u0005\u0018\u00010î\u0002*\u00030Û\u0002H\u0002J\u000f\u0010ø\u0002\u001a\u00030¤\u0001*\u00030ë\u0002H\u0002J\u0019\u0010ù\u0002\u001a\u00030¢\u0001*\u00030ú\u00022\b\u0010û\u0002\u001a\u00030ë\u0002H\u0002J\u0011\u0010ü\u0002\u001a\u0005\u0018\u00010\u0080\u0001*\u00030\u008d\u0001H\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0012\u0010\u001b\u001a\u00020\u001cX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0012\u0010\u001f\u001a\u00020 X\u0096\u0005¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u000e\u0010\u0006\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010#\u001a\u00020$X\u0096\u0005¢\u0006\u0006\u001a\u0004\b%\u0010&R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010'\u001a\u00020(X\u0096\u0005¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0012\u0010+\u001a\u00020,X\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0012\u0010/\u001a\u000200X\u0096\u0005¢\u0006\u0006\u001a\u0004\b1\u00102R\u0012\u00103\u001a\u000204X\u0096\u0005¢\u0006\u0006\u001a\u0004\b5\u00106R\u0012\u00107\u001a\u000208X\u0096\u0005¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0014\u0010;\u001a\u00020<X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0012\u0010?\u001a\u00020@X\u0096\u0005¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0012\u0010C\u001a\u00020DX\u0096\u0005¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0018\u0010G\u001a\b\u0012\u0004\u0012\u00020I0HX\u0096\u0005¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u0012\u0010L\u001a\u00020MX\u0096\u0005¢\u0006\u0006\u001a\u0004\bN\u0010OR\u001a\u0010P\u001a\u000e\u0012\u0004\u0012\u00020R\u0012\u0004\u0012\u00020S0QX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020UX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020WX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010X\u001a\u00020YX\u0096\u0005¢\u0006\u0006\u001a\u0004\bZ\u0010[R\u0012\u0010\\\u001a\u00020]X\u0096\u0005¢\u0006\u0006\u001a\u0004\b^\u0010_R\u0012\u0010`\u001a\u00020aX\u0096\u0005¢\u0006\u0006\u001a\u0004\bb\u0010cR\u0012\u0010d\u001a\u00020eX\u0096\u0005¢\u0006\u0006\u001a\u0004\bf\u0010gR\u0012\u0010h\u001a\u00020iX\u0096\u0005¢\u0006\u0006\u001a\u0004\bj\u0010kR\u0012\u0010l\u001a\u00020mX\u0096\u0005¢\u0006\u0006\u001a\u0004\bn\u0010oR\u0018\u0010p\u001a\u00020\u000b*\u00020q8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bp\u0010rR\u0018\u0010s\u001a\u00020\u000b*\u00020t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bs\u0010u\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006ý\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisitor;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "Lorg/jetbrains/kotlin/ir/IrElement;", "", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContextInterface;", "components", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;)V", "_annotationMode", "", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationMode", "getAnnotationMode", "()Z", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "implicitCastInserter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrImplicitCastInserter;", "getImplicitCastInserter$fir2ir", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrImplicitCastInserter;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "loopMap", "", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "memberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/ClassMemberGenerator;", "operatorGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/OperatorExpressionGenerator;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "isIncrementOrDecrementCall", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Z", "isLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)Z", "applyParentFromStackTo", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "convertCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "isDelegate", "convertQualifiedAccessExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "convertToArrayLiteral", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "convertToIrArrayAccessDynamicCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "convertToIrBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "convertToIrBlockBody$fir2ir", "convertToIrCall", "dynamicOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "convertToIrExpression", "expression", "convertToIrExpression$fir2ir", "convertToIrReceiverExpression", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "convertToIrReceiverExpression$fir2ir", "extractOperationFromDynamicSetCall", "generateWhen", "startOffset", "", "endOffset", "origin", "subjectVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "branches", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "resultType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "generateWhenSubjectVariable", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "shouldGenerateReceiverAsSingletonReference", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "visitAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "visitAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "visitAnonymousInitializer", "anonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "visitAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "visitArrayLiteral", "visitAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "visitBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "visitCallableReferenceAccess", "visitCatch", PsiKeyword.CATCH, "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", "visitCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "visitCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "visitCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "visitComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "visitConstExpression", "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "visitDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "visitDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "visitEnumEntryDeserializedAccessExpression", "enumEntryDeserializedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", "visitEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "visitErrorExpression", "errorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "visitFunctionCall", "visitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "visitInaccessibleReceiverExpression", "inaccessibleReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirInaccessibleReceiverExpression;", "visitLocalVariable", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitProperty", "property", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "visitQualifiedAccessExpression", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitResolvedQualifier", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "visitReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "visitSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "visitStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "visitThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "visitThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "visitTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "visitTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "visitVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "visitWhenExpression", "visitWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "visitWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "visitWrappedArgumentExpression", "wrappedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedArgumentExpression;", "withAnnotationMode", "enableAnnotationMode", "Lkotlin/Function0;", "withAnnotationMode$fir2ir", "(ZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "convertJumpWithOffsets", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "f", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "irLoop", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "convertToIrBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "forceUnitType", "convertToIrExpressionOrBlock", "convertToIrVarargElement", "Lorg/jetbrains/kotlin/ir/expressions/IrVarargElement;", "findFirst", "K", "(Ljava/util/List;)Ljava/lang/Object;", "findLast", "getStatementsOrigin", "index", "insertImplicitCast", "baseExpression", "valueType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "expectedType", "mapToIrStatements", "Lorg/jetbrains/kotlin/ir/IrStatement;", "recognizePostfixIncDec", "toIrClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "toIrDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "toIrDynamicOperator", "Lorg/jetbrains/kotlin/contracts/description/LogicOperationKind;", "toIrStatement", "toIrType", "toIrWhenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "whenExpressionType", "tryConvertDynamicIncrementOrDecrementToIr", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrVisitor extends FirDefaultVisitor<IrElement, Object> implements Fir2IrComponents, IrGeneratorContextInterface {
    private boolean _annotationMode;
    private final Fir2IrComponents components;
    private final Fir2IrConversionScope conversionScope;
    private final Fir2IrImplicitCastInserter implicitCastInserter;
    private final Map<FirLoop, IrLoop> loopMap;
    private final ClassMemberGenerator memberGenerator;
    private final OperatorExpressionGenerator operatorGenerator;

    /* JADX INFO: compiled from: Fir2IrVisitor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

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
            int[] iArr2 = new int[LogicOperationKind.values().length];
            try {
                iArr2[LogicOperationKind.AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[LogicOperationKind.OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

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

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContextInterface
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

    public Fir2IrVisitor(Fir2IrComponents components, Fir2IrConversionScope conversionScope) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        this.components = components;
        this.conversionScope = conversionScope;
        this.implicitCastInserter = new Fir2IrImplicitCastInserter(components);
        this.memberGenerator = new ClassMemberGenerator(components, this, conversionScope);
        this.operatorGenerator = new OperatorExpressionGenerator(components, this, conversionScope);
        this.loopMap = new LinkedHashMap();
    }

    /* JADX INFO: renamed from: getImplicitCastInserter$fir2ir, reason: from getter */
    public final Fir2IrImplicitCastInserter getImplicitCastInserter() {
        return this.implicitCastInserter;
    }

    /* JADX INFO: renamed from: getAnnotationMode, reason: from getter */
    public final boolean get_annotationMode() {
        return this._annotationMode;
    }

    private final IrType toIrType(FirTypeRef firTypeRef) {
        return Fir2IrTypeConverter.toIrType$default(getTypeConverter(), firTypeRef, null, 1, null);
    }

    private final <T extends IrDeclaration> T applyParentFromStackTo(T declaration) {
        return (T) this.conversionScope.applyParentFromStackTo(declaration);
    }

    public static /* synthetic */ Object withAnnotationMode$fir2ir$default(Fir2IrVisitor fir2IrVisitor, boolean z, Function0 block, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        Intrinsics.checkNotNullParameter(block, "block");
        boolean z2 = fir2IrVisitor._annotationMode;
        fir2IrVisitor._annotationMode = z;
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            fir2IrVisitor._annotationMode = z2;
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withAnnotationMode$fir2ir(boolean enableAnnotationMode, Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean z = this._annotationMode;
        this._annotationMode = enableAnnotationMode;
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            this._annotationMode = z;
            InlineMarker.finallyEnd(1);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitElement(FirElement element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new NotImplementedError("An operation is not implemented: " + ("Should not be here: " + Reflection.getOrCreateKotlinClass(element.getClass()) + StringUtil.SPACE + UtilsKt.render(element)));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrField visitField(FirField field, Object data) {
        Intrinsics.checkNotNullParameter(field, "field");
        FirSession session = getSession();
        try {
            if (field.getOrigin() instanceof FirDeclarationOrigin.Synthetic) {
                IrField cachedIrDelegateOrBackingField = getDeclarationStorage().getCachedIrDelegateOrBackingField(field);
                Intrinsics.checkNotNull(cachedIrDelegateOrBackingField);
                if (cachedIrDelegateOrBackingField.getCorrespondingPropertySymbol() == null) {
                    this.memberGenerator.convertFieldContent(cachedIrDelegateOrBackingField, field);
                }
                return cachedIrDelegateOrBackingField;
            }
            throw new AssertionError("Unexpected field: " + UtilsKt.render(field));
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(field, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrFile visitFile(FirFile file, Object data) {
        Intrinsics.checkNotNullParameter(file, "file");
        IrFile irFile = getDeclarationStorage().getIrFile(file);
        Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
        fir2IrConversionScope.getParentStack().add(irFile);
        try {
            IrFile irFile2 = irFile;
            Iterator<T> it = file.getDeclarations().iterator();
            while (it.hasNext()) {
                toIrDeclaration((FirDeclaration) it.next());
            }
            getAnnotationGenerator().generate(irFile2, file);
            irFile2.setMetadata(new FirMetadataSource.File(CollectionsKt.listOf(file)));
            Unit unit = Unit.INSTANCE;
            return irFile;
        } finally {
            fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
        }
    }

    private final IrDeclaration toIrDeclaration(FirDeclaration firDeclaration) {
        Object objAccept = firDeclaration.accept(this, null);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        return (IrDeclaration) objAccept;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitTypeAlias(FirTypeAlias typeAlias, Object data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        FirSession session = getSession();
        try {
            IrTypeAlias cachedTypeAlias$fir2ir = getClassifierStorage().getCachedTypeAlias$fir2ir(typeAlias);
            Intrinsics.checkNotNull(cachedTypeAlias$fir2ir);
            getAnnotationGenerator().generate(cachedTypeAlias$fir2ir, typeAlias);
            return cachedTypeAlias$fir2ir;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(typeAlias, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitEnumEntry(FirEnumEntry enumEntry, Object data) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        FirConstructor firConstructor;
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        FirSession session = getSession();
        try {
            IrEnumEntry cachedIrEnumEntry$fir2ir = getClassifierStorage().getCachedIrEnumEntry$fir2ir(enumEntry);
            Intrinsics.checkNotNull(cachedIrEnumEntry$fir2ir);
            getAnnotationGenerator().generate(cachedIrEnumEntry$fir2ir, enumEntry);
            IrClass correspondingClass = cachedIrEnumEntry$fir2ir.getCorrespondingClass();
            FirExpression initializer = enumEntry.getInitializer();
            IrType irType = toIrType(enumEntry.getReturnTypeRef());
            IrDeclarationParent parent = cachedIrEnumEntry$fir2ir.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (correspondingClass != null) {
                getDeclarationStorage().enterScope(cachedIrEnumEntry$fir2ir);
                getClassifierStorage().putEnumEntryClassInScope(enumEntry, correspondingClass);
                FirExpression initializer2 = enumEntry.getInitializer();
                Intrinsics.checkNotNull(initializer2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression");
                FirAnonymousObject anonymousObject = ((FirAnonymousObjectExpression) initializer2).getAnonymousObject();
                getConverter().processAnonymousObjectHeaders(anonymousObject, correspondingClass);
                getConverter().processClassMembers$fir2ir(anonymousObject, correspondingClass);
                getConverter().bindFakeOverridesInClass(correspondingClass);
                Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
                fir2IrConversionScope.getParentStack().add(correspondingClass);
                try {
                    IrClass irClass2 = correspondingClass;
                    this.memberGenerator.convertClassContent(correspondingClass, anonymousObject);
                    IrConstructor irConstructor = (IrConstructor) SequencesKt.first(IrUtilsKt.getConstructors(correspondingClass));
                    cachedIrEnumEntry$fir2ir.setInitializerExpression(IrFactoryHelpersKt.createExpressionBody(getIrFactory(), new IrEnumConstructorCallImpl(irClass2.getStartOffset(), irClass2.getEndOffset(), irType, irConstructor.getSymbol(), irConstructor.getTypeParameters().size(), irConstructor.getValueParameters().size())));
                    Unit unit = Unit.INSTANCE;
                    fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
                    getDeclarationStorage().leaveScope(cachedIrEnumEntry$fir2ir);
                } catch (Throwable th) {
                    fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
                    throw th;
                }
            } else if (initializer instanceof FirAnonymousObjectExpression) {
                FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny = DeclarationUtilsKt.primaryConstructorIfAny(((FirAnonymousObjectExpression) initializer).getAnonymousObject(), getSession());
                FirDelegatedConstructorCall delegatedConstructor = (firConstructorSymbolPrimaryConstructorIfAny == null || (firConstructor = (FirConstructor) firConstructorSymbolPrimaryConstructorIfAny.getFir()) == null) ? null : firConstructor.getDelegatedConstructor();
                if (delegatedConstructor != null) {
                    ClassMemberGenerator classMemberGenerator = this.memberGenerator;
                    cachedIrEnumEntry$fir2ir.setInitializerExpression(IrFactoryHelpersKt.createExpressionBody(classMemberGenerator.getIrFactory(), classMemberGenerator.toIrDelegatingConstructorCall$fir2ir(delegatedConstructor)));
                }
            } else if (irClass != null && initializer == null) {
                IrConstructor defaultConstructor = IrUtilsKt.getDefaultConstructor(irClass);
                if (defaultConstructor == null) {
                    throw new IllegalStateException("Assuming that default constructor should exist and be converted at this point".toString());
                }
                KtSourceElement source = enumEntry.getSource();
                if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                    i = -1;
                    endOffset = -1;
                } else {
                    if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                        startOffset = source != null ? source.getStartOffset() : -1;
                    } else {
                        startOffset = numStartOffsetSkippingComments.intValue();
                    }
                    i = startOffset;
                    endOffset = source != null ? source.getEndOffset() : -1;
                }
                cachedIrEnumEntry$fir2ir.setInitializerExpression(IrFactoryHelpersKt.createExpressionBody(getIrFactory(), new IrEnumConstructorCallImpl(i, endOffset, irType, defaultConstructor.getSymbol(), defaultConstructor.getTypeParameters().size(), defaultConstructor.getValueParameters().size())));
                IrEnumEntry irEnumEntry = cachedIrEnumEntry$fir2ir;
            }
            return cachedIrEnumEntry$fir2ir;
        } catch (Throwable th2) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(enumEntry, th2);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitRegularClass(FirRegularClass regularClass, Object data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        FirSession session = getSession();
        try {
            if (Intrinsics.areEqual(regularClass.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
                IrDeclarationParent irDeclarationParentParentFromStack = this.conversionScope.parentFromStack();
                IrClass cachedIrClass = getClassifierStorage().getCachedIrClass(regularClass);
                if (cachedIrClass != null) {
                    cachedIrClass.setParent(irDeclarationParentParentFromStack);
                } else {
                    cachedIrClass = null;
                }
                if (cachedIrClass != null) {
                    Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
                    fir2IrConversionScope.getParentStack().add(cachedIrClass);
                    try {
                        IrClass irClass = cachedIrClass;
                        this.memberGenerator.convertClassContent(cachedIrClass, regularClass);
                        Unit unit = Unit.INSTANCE;
                        return cachedIrClass;
                    } finally {
                        fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
                    }
                }
                getConverter().processLocalClassAndNestedClasses(regularClass, irDeclarationParentParentFromStack);
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(regularClass, th);
                throw null;
            }
            IrClass cachedIrClass2 = getClassifierStorage().getCachedIrClass(regularClass);
            Intrinsics.checkNotNull(cachedIrClass2);
            if (regularClass.getStatus().getModality() == Modality.SEALED) {
                cachedIrClass2.setSealedSubclasses(ConversionUtilsKt.getIrSymbolsForSealedSubclasses(this, regularClass));
            }
            Fir2IrConversionScope fir2IrConversionScope2 = this.conversionScope;
            fir2IrConversionScope2.getParentStack().add(cachedIrClass2);
            try {
                IrClass irClass2 = cachedIrClass2;
                this.memberGenerator.convertClassContent(cachedIrClass2, regularClass);
                Unit unit2 = Unit.INSTANCE;
                return cachedIrClass2;
            } finally {
                fir2IrConversionScope2.getParentStack().remove(fir2IrConversionScope2.getParentStack().size() - 1);
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(regularClass, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitScript(FirScript script, Object data) {
        IrStatement irStatement;
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        String strAsStringStripSpecialMarkers;
        Name nameIdentifier;
        int endOffset2;
        int i;
        Integer numStartOffsetSkippingComments2;
        Intrinsics.checkNotNullParameter(script, "script");
        IrScript cachedIrScript = getDeclarationStorage().getCachedIrScript(script);
        Intrinsics.checkNotNull(cachedIrScript);
        cachedIrScript.setParent(this.conversionScope.parentFromStack());
        IrScript irScript = cachedIrScript;
        getDeclarationStorage().enterScope(irScript);
        List<FirVariable> parameters = script.getParameters();
        List<? extends IrVariable> arrayList = new ArrayList<>(CollectionsKt.collectionSizeOrDefault(parameters, 10));
        Iterator<T> it = parameters.iterator();
        while (it.hasNext()) {
            arrayList.add(getDeclarationStorage().createIrVariable((FirVariable) it.next(), cachedIrScript, IrDeclarationOrigin.SCRIPT_CALL_PARAMETER.INSTANCE));
        }
        cachedIrScript.setExplicitCallParameters(arrayList);
        List<FirContextReceiver> contextReceivers = script.getContextReceivers();
        List<? extends IrValueParameter> arrayList2 = new ArrayList<>();
        Iterator<T> it2 = contextReceivers.iterator();
        int i2 = 0;
        while (true) {
            int i3 = 1;
            IrValueParameter irValueParameter = null;
            if (!it2.hasNext()) {
                break;
            }
            Object next = it2.next();
            int i4 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            FirContextReceiver firContextReceiver = (FirContextReceiver) next;
            Name customLabelName = firContextReceiver.getCustomLabelName();
            boolean zAreEqual = Intrinsics.areEqual(customLabelName != null ? customLabelName.asString() : null, FirScriptUtilsKt.SCRIPT_SPECIAL_NAME_STRING);
            if (zAreEqual) {
                nameIdentifier = SpecialNames.THIS;
            } else {
                StringBuilder sb = new StringBuilder();
                Name labelName = UtilsKt.getLabelName(firContextReceiver);
                if (labelName == null || (strAsStringStripSpecialMarkers = labelName.asStringStripSpecialMarkers()) == null) {
                    strAsStringStripSpecialMarkers = FirScriptUtilsKt.SCRIPT_RECEIVER_NAME_PREFIX;
                }
                nameIdentifier = Name.identifier(sb.append(strAsStringStripSpecialMarkers).append('_').append(i2).toString());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            }
            Name name2 = nameIdentifier;
            IrDeclarationOrigin irDeclarationOrigin = (IrDeclarationOriginImpl) (zAreEqual ? IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE : IrDeclarationOrigin.SCRIPT_IMPLICIT_RECEIVER.INSTANCE);
            KtSourceElement source = firContextReceiver.getSource();
            KtSourceElement ktSourceElement = source;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
                i = -1;
                endOffset2 = -1;
            } else {
                int startOffset2 = (source == null || (numStartOffsetSkippingComments2 = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) ? source != null ? source.getStartOffset() : -1 : numStartOffsetSkippingComments2.intValue();
                endOffset2 = source != null ? source.getEndOffset() : -1;
                i = startOffset2;
            }
            IrValueParameter irValueParameterCreateValueParameter = getIrFactory().createValueParameter(i, endOffset2, irDeclarationOrigin, name2, toIrType(firContextReceiver.getTypeRef()), false, new IrValueParameterSymbolImpl(null == true ? 1 : 0, i3, null == true ? 1 : 0), zAreEqual ? -1 : i2, (IrType) null, false, false, false);
            irValueParameterCreateValueParameter.setParent(cachedIrScript);
            IrValueParameter irValueParameter2 = irValueParameterCreateValueParameter;
            if (zAreEqual) {
                cachedIrScript.setThisReceiver(irValueParameter2);
                cachedIrScript.setBaseClass(irValueParameter2.getType());
            } else {
                irValueParameter = irValueParameter2;
            }
            if (irValueParameter != null) {
                arrayList2.add(irValueParameter);
            }
            i2 = i4;
        }
        cachedIrScript.setImplicitReceiversParameters(arrayList2);
        Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
        fir2IrConversionScope.getParentStack().add(cachedIrScript);
        try {
            IrScript irScript2 = cachedIrScript;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (FirStatement firStatement : script.getStatements()) {
                if (firStatement instanceof FirDeclaration) {
                    if ((firStatement instanceof FirProperty) && Intrinsics.areEqual(((FirDeclaration) firStatement).getOrigin(), FirDeclarationOrigin.ScriptCustomization.ResultProperty.INSTANCE)) {
                        FirTypeRef returnTypeRef = ((FirProperty) firStatement).getReturnTypeRef();
                        if (FirTypeUtilsKt.isUnit(returnTypeRef) || FirTypeUtilsKt.isNothing(returnTypeRef) || FirTypeUtilsKt.isNullableNothing(returnTypeRef)) {
                            FirStatement initializer = ((FirProperty) firStatement).getInitializer();
                            Intrinsics.checkNotNull(initializer);
                            irStatement = toIrStatement(initializer);
                        } else {
                            Object objAccept = firStatement.accept(this, null);
                            IrStatement irStatement2 = objAccept instanceof IrDeclaration ? (IrDeclaration) objAccept : null;
                            if (irStatement2 != null) {
                                IrProperty irProperty = irStatement2 instanceof IrProperty ? (IrProperty) irStatement2 : null;
                                cachedIrScript.setResultProperty(irProperty != null ? irProperty.getSymbol() : null);
                            } else {
                                irStatement2 = null;
                            }
                            irStatement = irStatement2;
                        }
                    } else if (!(firStatement instanceof FirVariable) || !Intrinsics.areEqual((Object) DestructuringDeclarationAttributesKt.isDestructuringDeclarationContainerVariable((FirVariable) firStatement), (Object) true)) {
                        if ((firStatement instanceof FirProperty) && DestructuringDeclarationAttributesKt.getDestructuringDeclarationContainerVariable((FirProperty) firStatement) != null) {
                            Object objAccept2 = firStatement.accept(this, null);
                            Intrinsics.checkNotNull(objAccept2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrProperty");
                            IrProperty irProperty2 = (IrProperty) objAccept2;
                            int startOffset3 = irProperty2.getStartOffset();
                            int endOffset3 = irProperty2.getEndOffset();
                            IrField backingField = irProperty2.getBackingField();
                            Intrinsics.checkNotNull(backingField);
                            IrSetFieldImpl irSetFieldImpl = new IrSetFieldImpl(startOffset3, endOffset3, backingField.getSymbol(), getIrBuiltIns().getUnitType(), null, null);
                            IrField backingField2 = irProperty2.getBackingField();
                            Intrinsics.checkNotNull(backingField2);
                            IrExpressionBody initializer2 = backingField2.getInitializer();
                            Intrinsics.checkNotNull(initializer2);
                            irSetFieldImpl.setValue(initializer2.getExpression());
                            irSetFieldImpl.setReceiver(null);
                            FirVariableSymbol<?> destructuringDeclarationContainerVariable = DestructuringDeclarationAttributesKt.getDestructuringDeclarationContainerVariable((FirProperty) firStatement);
                            Intrinsics.checkNotNull(destructuringDeclarationContainerVariable);
                            Object obj = linkedHashMap.get(destructuringDeclarationContainerVariable);
                            Intrinsics.checkNotNull(obj);
                            ((IrComposite) obj).getStatements().add(irSetFieldImpl);
                            IrField backingField3 = irProperty2.getBackingField();
                            Intrinsics.checkNotNull(backingField3);
                            backingField3.setInitializer(null);
                            irStatement = irProperty2;
                        } else if (firStatement instanceof FirClass) {
                            Object objAccept3 = firStatement.accept(this, null);
                            Intrinsics.checkNotNull(objAccept3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                            IrClass irClass = (IrClass) objAccept3;
                            getConverter().bindFakeOverridesInClass(irClass);
                            irStatement = irClass;
                        } else {
                            Object objAccept4 = firStatement.accept(this, null);
                            irStatement = objAccept4 instanceof IrDeclaration ? (IrDeclaration) objAccept4 : null;
                        }
                    } else {
                        KtSourceElement source2 = firStatement.getSource();
                        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source2))) {
                            endOffset = -1;
                            startOffset = -1;
                        } else {
                            startOffset = (source2 == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source2)) == null) ? source2 != null ? source2.getStartOffset() : -1 : numStartOffsetSkippingComments.intValue();
                            endOffset = source2 != null ? source2.getEndOffset() : -1;
                        }
                        IrCompositeImpl irCompositeImpl = new IrCompositeImpl(startOffset, endOffset, getIrBuiltIns().getUnitType(), IrStatementOrigin.DESTRUCTURING_DECLARATION.INSTANCE);
                        List<IrStatement> statements = irCompositeImpl.getStatements();
                        IrVariable irVariableCreateIrVariable$default = Fir2IrDeclarationStorage.createIrVariable$default(getDeclarationStorage(), (FirVariable) firStatement, this.conversionScope.parentFromStack(), null, 4, null);
                        FirStatement initializer3 = ((FirVariable) firStatement).getInitializer();
                        IrStatement irStatement3 = initializer3 != null ? toIrStatement(initializer3) : null;
                        irVariableCreateIrVariable$default.setInitializer(irStatement3 instanceof IrExpression ? (IrExpression) irStatement3 : null);
                        statements.add(irVariableCreateIrVariable$default);
                        linkedHashMap.put(((FirVariable) firStatement).getSymbol(), irCompositeImpl);
                        irStatement = irCompositeImpl;
                    }
                } else {
                    irStatement = toIrStatement(firStatement);
                }
                List<IrStatement> statements2 = cachedIrScript.getStatements();
                Intrinsics.checkNotNull(irStatement);
                statements2.add(irStatement);
            }
            Unit unit = Unit.INSTANCE;
            fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
            Iterator<Fir2IrScriptConfiguratorExtension> it3 = Fir2IrScriptConfiguratorExtensionKt.getFir2IrScriptConfigurators(FirExtensionServiceKt.getExtensionService(getSession())).iterator();
            while (it3.hasNext()) {
                it3.next().configure(cachedIrScript, script, new Function1<FirScriptSymbol, IrScriptSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrVisitor$visitScript$1$4$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrScriptSymbol invoke(FirScriptSymbol it4) {
                        Intrinsics.checkNotNullParameter(it4, "it");
                        IrScript cachedIrScript2 = this.this$0.getDeclarationStorage().getCachedIrScript(it4.getFir());
                        if (cachedIrScript2 != null) {
                            return cachedIrScript2.getSymbol();
                        }
                        return null;
                    }
                });
            }
            getDeclarationStorage().leaveScope(irScript);
            return cachedIrScript;
        } catch (Throwable th) {
            fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitCodeFragment(FirCodeFragment codeFragment, Object data) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        IrClass cachedIrCodeFragment = getClassifierStorage().getCachedIrCodeFragment(codeFragment);
        Intrinsics.checkNotNull(cachedIrCodeFragment);
        for (Object obj : cachedIrCodeFragment.getDeclarations()) {
            if (obj instanceof IrSimpleFunction) {
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj;
                IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                getDeclarationStorage().enterScope(irSimpleFunction2);
                Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
                fir2IrConversionScope.getParentStack().add(irSimpleFunction);
                try {
                    irSimpleFunction.setBody(IrFactoryHelpersKt.createExpressionBody(getIrFactory(), convertToIrBlock(codeFragment.getBlock(), false)));
                    Unit unit = Unit.INSTANCE;
                    fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
                    getDeclarationStorage().leaveScope(irSimpleFunction2);
                    return irSimpleFunction;
                } catch (Throwable th) {
                    fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
                    throw th;
                }
            }
        }
        throw new NoSuchElementException("No element of given type found");
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, Object data) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        return visitAnonymousObject(anonymousObjectExpression.getAnonymousObject(), data);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0025 A[Catch: all -> 0x00f9, TryCatch #1 {all -> 0x00f9, blocks: (B:3:0x000d, B:5:0x0020, B:8:0x0030, B:11:0x004e, B:24:0x00a1, B:15:0x0083, B:17:0x008c, B:22:0x009b, B:19:0x0093, B:27:0x00e8, B:28:0x00f8, B:7:0x0025, B:10:0x003f), top: B:34:0x000d, inners: #0 }] */
    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnonymousObject(FirAnonymousObject anonymousObject, Object data) {
        int startOffset;
        int i;
        int i2;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        FirSession session = getSession();
        try {
            IrDeclarationParent irDeclarationParentParentFromStack = this.conversionScope.parentFromStack();
            IrClass cachedIrClass = getClassifierStorage().getCachedIrClass(anonymousObject);
            if (cachedIrClass != null) {
                cachedIrClass.setParent(irDeclarationParentParentFromStack);
                if (cachedIrClass == null) {
                    cachedIrClass = getConverter().processLocalClassAndNestedClasses(anonymousObject, irDeclarationParentParentFromStack);
                }
            } else {
                cachedIrClass = getConverter().processLocalClassAndNestedClasses(anonymousObject, irDeclarationParentParentFromStack);
            }
            Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
            fir2IrConversionScope.getParentStack().add(cachedIrClass);
            try {
                IrClass irClass = cachedIrClass;
                this.memberGenerator.convertClassContent(cachedIrClass, anonymousObject);
                Unit unit = Unit.INSTANCE;
                fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
                IrValueParameter thisReceiver = cachedIrClass.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver);
                IrType type = thisReceiver.getType();
                KtSourceElement source = anonymousObject.getSource();
                if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                    i = -1;
                    i2 = -1;
                } else {
                    if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                        startOffset = source != null ? source.getStartOffset() : -1;
                    } else {
                        startOffset = numStartOffsetSkippingComments.intValue();
                    }
                    int endOffset = source != null ? source.getEndOffset() : -1;
                    i = startOffset;
                    i2 = endOffset;
                }
                return new IrBlockImpl(i, i2, type, IrStatementOrigin.OBJECT_LITERAL.INSTANCE, CollectionsKt.listOf((Object[]) new IrElement[]{cachedIrClass, IrConstructorCallImpl.INSTANCE.fromSymbolOwner(i, i2, type, ((IrConstructor) SequencesKt.first(IrUtilsKt.getConstructors(cachedIrClass))).getSymbol(), cachedIrClass.getTypeParameters().size(), IrStatementOrigin.OBJECT_LITERAL.INSTANCE)}));
            } catch (Throwable th) {
                fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
                throw th;
            }
        } catch (Throwable th2) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousObject, th2);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitConstructor(FirConstructor constructor, Object data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        FirSession session = getSession();
        try {
            IrConstructor cachedIrConstructor$default = Fir2IrDeclarationStorage.getCachedIrConstructor$default(getDeclarationStorage(), constructor, null, 2, null);
            Intrinsics.checkNotNull(cachedIrConstructor$default);
            Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
            fir2IrConversionScope.getFunctionStack().add(cachedIrConstructor$default);
            try {
                IrConstructor irConstructor = cachedIrConstructor$default;
                return (IrConstructor) this.memberGenerator.convertFunctionContent(cachedIrConstructor$default, constructor, this.conversionScope.containerFirClass());
            } finally {
                fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(constructor, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, Object data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        FirSession session = getSession();
        try {
            IrAnonymousInitializer cachedIrAnonymousInitializer = getDeclarationStorage().getCachedIrAnonymousInitializer(anonymousInitializer);
            Intrinsics.checkNotNull(cachedIrAnonymousInitializer);
            getDeclarationStorage().enterScope(cachedIrAnonymousInitializer);
            FirBlock body = anonymousInitializer.getBody();
            Intrinsics.checkNotNull(body);
            cachedIrAnonymousInitializer.setBody(convertToIrBlockBody$fir2ir(body));
            getDeclarationStorage().leaveScope(cachedIrAnonymousInitializer);
            return cachedIrAnonymousInitializer;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousInitializer, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitSimpleFunction(FirSimpleFunction simpleFunction, Object data) {
        IrSimpleFunction cachedIrFunction;
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        FirSession session = getSession();
        try {
            if (Intrinsics.areEqual(simpleFunction.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
                cachedIrFunction = Fir2IrDeclarationStorage.createIrFunction$default(getDeclarationStorage(), simpleFunction, this.conversionScope.parent(), null, IrDeclarationOrigin.LOCAL_FUNCTION.INSTANCE, true, null, 36, null);
            } else {
                cachedIrFunction = getDeclarationStorage().getCachedIrFunction(simpleFunction);
                Intrinsics.checkNotNull(cachedIrFunction);
            }
            Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
            fir2IrConversionScope.getFunctionStack().add(cachedIrFunction);
            try {
                IrSimpleFunction irSimpleFunction = cachedIrFunction;
                return (IrSimpleFunction) this.memberGenerator.convertFunctionContent(cachedIrFunction, simpleFunction, this.conversionScope.containerFirClass());
            } finally {
                fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(simpleFunction, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, Object data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        return visitAnonymousFunction(anonymousFunctionExpression.getAnonymousFunction(), data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnonymousFunction(FirAnonymousFunction anonymousFunction, Object data) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        FirSession session = getSession();
        try {
            KtSourceElement source = anonymousFunction.getSource();
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                i = -1;
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                i = startOffset;
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            IrSimpleFunction irSimpleFunctionCreateIrFunction$default = Fir2IrDeclarationStorage.createIrFunction$default(getDeclarationStorage(), anonymousFunction, this.conversionScope.parent(), null, IrDeclarationOrigin.LOCAL_FUNCTION.INSTANCE, true, null, 36, null);
            Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
            fir2IrConversionScope.getFunctionStack().add(irSimpleFunctionCreateIrFunction$default);
            try {
                IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateIrFunction$default;
                fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
                return new IrFunctionExpressionImpl(i, endOffset, toIrType(anonymousFunction.getTypeRef()), irSimpleFunctionCreateIrFunction$default, Intrinsics.areEqual(irSimpleFunctionCreateIrFunction$default.getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE) ? IrStatementOrigin.LAMBDA.INSTANCE : IrStatementOrigin.ANONYMOUS_FUNCTION.INSTANCE);
            } catch (Throwable th) {
                fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
                throw th;
            }
        } catch (Throwable th2) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousFunction, th2);
            throw null;
        }
    }

    private final IrElement visitLocalVariable(FirProperty variable) {
        IrDeclarationOrigin.FOR_LOOP_VARIABLE for_loop_variable;
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default;
        CallableId callableId;
        KtSourceElement source;
        FirSession session = getSession();
        try {
            variable.getIsLocal();
            FirExpression delegate = variable.getDelegate();
            int i = 1;
            if (delegate != null) {
                IrLocalDelegatedProperty irLocalDelegatedPropertyCreateIrLocalDelegatedProperty = getDeclarationStorage().createIrLocalDelegatedProperty(variable, this.conversionScope.parentFromStack());
                irLocalDelegatedPropertyCreateIrLocalDelegatedProperty.getDelegate().setInitializer(convertToIrExpression$fir2ir(delegate, true));
                Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
                IrSimpleFunction getter = irLocalDelegatedPropertyCreateIrLocalDelegatedProperty.getGetter();
                fir2IrConversionScope.getFunctionStack().add(getter);
                try {
                    IrSimpleFunction irSimpleFunction = getter;
                    fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
                    IrSimpleFunction setter = irLocalDelegatedPropertyCreateIrLocalDelegatedProperty.getSetter();
                    if (setter != null) {
                        Fir2IrConversionScope fir2IrConversionScope2 = this.conversionScope;
                        fir2IrConversionScope2.getFunctionStack().add(setter);
                        try {
                            IrSimpleFunction irSimpleFunction2 = setter;
                        } finally {
                            fir2IrConversionScope2.getFunctionStack().remove(fir2IrConversionScope2.getFunctionStack().size() - 1);
                        }
                    }
                    return irLocalDelegatedPropertyCreateIrLocalDelegatedProperty;
                } catch (Throwable th) {
                    fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
                    throw th;
                }
            }
            FirExpression initializer = variable.getInitializer();
            if (!(initializer instanceof FirFunctionCall) || (resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(((FirFunctionCall) initializer).getCalleeReference(), false, 1, null)) == null || (callableId = resolvedNamedFunctionSymbol$default.getCallableId()) == null || !CallableIdUtilsKt.isIteratorNext(callableId) || (source = variable.getSource()) == null || !Fir2IrVisitorKt.isChildOfForLoop(source)) {
                i = 0;
            }
            Fir2IrDeclarationStorage declarationStorage = getDeclarationStorage();
            FirProperty firProperty = variable;
            IrDeclarationParent irDeclarationParentParentFromStack = this.conversionScope.parentFromStack();
            if (i == 0) {
                for_loop_variable = null;
            } else if (variable.getName().isSpecial() && Intrinsics.areEqual(variable.getName(), SpecialNames.DESTRUCT)) {
                for_loop_variable = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
            } else {
                for_loop_variable = IrDeclarationOrigin.FOR_LOOP_VARIABLE.INSTANCE;
            }
            IrVariable irVariableCreateIrVariable = declarationStorage.createIrVariable(firProperty, irDeclarationParentParentFromStack, for_loop_variable);
            if (initializer != null) {
                irVariableCreateIrVariable.setInitializer(insertImplicitCast(convertToIrExpression$fir2ir$default(this, initializer, false, 2, null), initializer, initializer.getTypeRef(), variable.getReturnTypeRef()));
            }
            getAnnotationGenerator().generate(irVariableCreateIrVariable, variable);
            return irVariableCreateIrVariable;
        } catch (Throwable th2) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(variable, th2);
            throw null;
        }
    }

    private final IrExpression insertImplicitCast(IrExpression irExpression, FirExpression firExpression, FirTypeRef firTypeRef, FirTypeRef firTypeRef2) {
        return this.implicitCastInserter.cast$fir2ir(irExpression, firExpression, firTypeRef, firTypeRef2);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitProperty(FirProperty property, Object data) {
        Intrinsics.checkNotNullParameter(property, "property");
        FirSession session = getSession();
        try {
            if (property.getIsLocal()) {
                return visitLocalVariable(property);
            }
            IrProperty cachedIrProperty = getDeclarationStorage().getCachedIrProperty(property);
            if (cachedIrProperty == null) {
                return new IrErrorExpressionImpl(-1, -1, new IrErrorTypeImpl(null, CollectionsKt.emptyList(), Variance.INVARIANT, false, 8, null), "Stub for Enum.entries");
            }
            Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
            fir2IrConversionScope.getPropertyStack().add(TuplesKt.to(cachedIrProperty, property));
            try {
                return this.memberGenerator.convertPropertyContent(cachedIrProperty, property, this.conversionScope.containerFirClass());
            } finally {
                fir2IrConversionScope.getPropertyStack().remove(fir2IrConversionScope.getPropertyStack().size() - 1);
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(property, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitReturnExpression(FirReturnExpression returnExpression, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        DeclarationSymbolMarker symbol;
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        IrFunction irFunctionReturnTarget = this.conversionScope.returnTarget(returnExpression, getDeclarationStorage());
        KtSourceElement source = returnExpression.getSource();
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
        FirExpression result = returnExpression.getResult();
        KtSourceElement source2 = returnExpression.getSource();
        int i2 = (source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind.ImplicitReturn ? endOffset : i;
        IrType nothingType = getIrBuiltIns().getNothingType();
        if (irFunctionReturnTarget instanceof IrConstructor) {
            symbol = ((IrConstructor) irFunctionReturnTarget).getSymbol();
        } else {
            if (!(irFunctionReturnTarget instanceof IrSimpleFunction)) {
                throw new IllegalStateException(("Unknown return target: " + irFunctionReturnTarget).toString());
            }
            symbol = ((IrSimpleFunction) irFunctionReturnTarget).getSymbol();
        }
        return (IrElement) returnExpression.accept(this.implicitCastInserter, new IrReturnImpl(i2, endOffset, nothingType, (IrReturnTargetSymbol) symbol, convertToIrExpression$fir2ir$default(this, result, false, 2, null)));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitWrappedArgumentExpression(FirWrappedArgumentExpression wrappedArgumentExpression, Object data) {
        Intrinsics.checkNotNullParameter(wrappedArgumentExpression, "wrappedArgumentExpression");
        return convertToIrExpression$fir2ir$default(this, wrappedArgumentExpression.getExpression(), false, 2, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, Object data) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        KtSourceElement source = varargArgumentsExpression.getSource();
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
        IrType irType = toIrType(varargArgumentsExpression.getTypeRef());
        IrType irType2 = toIrType(varargArgumentsExpression.getVarargElementType());
        List<FirExpression> arguments = varargArgumentsExpression.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(convertToIrVarargElement((FirExpression) it.next()));
        }
        return new IrVarargImpl(i, endOffset, irType, irType2, arrayList);
    }

    private final IrVarargElement convertToIrVarargElement(FirExpression firExpression) {
        if ((firExpression instanceof FirSpreadArgumentExpression) || ((firExpression instanceof FirNamedArgumentExpression) && ((FirNamedArgumentExpression) firExpression).getIsSpread())) {
            KtSourceElement source = firExpression.getSource();
            int startOffset = source != null ? source.getStartOffset() : -1;
            KtSourceElement source2 = firExpression.getSource();
            return new IrSpreadElementImpl(startOffset, source2 != null ? source2.getEndOffset() : -1, convertToIrExpression$fir2ir$default(this, firExpression, false, 2, null));
        }
        return convertToIrExpression$fir2ir$default(this, firExpression, false, 2, null);
    }

    private final IrExpression convertToIrCall(FirFunctionCall functionCall, IrDynamicOperator dynamicOperator) {
        return CallAndReferenceGenerator.convertToIrCall$default(getCallGenerator(), functionCall, functionCall.getTypeRef(), convertToIrReceiverExpression$fir2ir$default(this, functionCall.getExplicitReceiver(), functionCall.getCalleeReference(), null, 4, null), dynamicOperator, false, false, 48, null);
    }

    private final IrExpression convertToIrArrayAccessDynamicCall(FirFunctionCall functionCall) {
        IrExpression irExpressionConvertToIrCall = convertToIrCall(functionCall, IrDynamicOperator.ARRAY_ACCESS);
        if (irExpressionConvertToIrCall instanceof IrDynamicOperatorExpression) {
            CollectionsKt.removeLast(((IrDynamicOperatorExpression) irExpressionConvertToIrCall).getArguments());
        }
        IrExpression irExpressionConvertToIrCall$default = CallAndReferenceGenerator.convertToIrCall$default(getCallGenerator(), functionCall, functionCall.getTypeRef(), irExpressionConvertToIrCall, IrDynamicOperator.EQ, false, false, 48, null);
        if (irExpressionConvertToIrCall$default instanceof IrDynamicOperatorExpression) {
            List<IrExpression> arguments = ((IrDynamicOperatorExpression) irExpressionConvertToIrCall$default).getArguments();
            arguments.set(0, arguments.get(CollectionsKt.getLastIndex(arguments)));
            while (arguments.size() > 1) {
                CollectionsKt.removeLast(arguments);
            }
        }
        return irExpressionConvertToIrCall$default;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrExpression visitFunctionCall(FirFunctionCall functionCall, Object data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        FirSession session = getSession();
        try {
            return convertToIrCall(functionCall);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(functionCall, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitSafeCallExpression(FirSafeCallExpression safeCallExpression, Object data) {
        IrExpression irExpressionConvertToIrExpression$fir2ir$default;
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        FirSession session = getSession();
        try {
            Pair<IrVariable, IrValueSymbol> pairCreateTemporaryVariableForSafeCallConstruction = ConversionUtilsKt.createTemporaryVariableForSafeCallConstruction(this.components, convertToIrExpression$fir2ir$default(this, safeCallExpression.getReceiver(), false, 2, null), this.conversionScope);
            IrVariable irVariableComponent1 = pairCreateTemporaryVariableForSafeCallConstruction.component1();
            IrValueSymbol irValueSymbolComponent2 = pairCreateTemporaryVariableForSafeCallConstruction.component2();
            Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
            if (irVariableComponent1 != null) {
                fir2IrConversionScope.getSafeCallSubjectVariableStack().add(irVariableComponent1);
            }
            try {
                FirStatement selector = safeCallExpression.getSelector();
                FirExpression firExpression = selector instanceof FirExpression ? (FirExpression) selector : null;
                if (firExpression == null || (irExpressionConvertToIrExpression$fir2ir$default = convertToIrExpression$fir2ir$default(this, firExpression, false, 2, null)) == null) {
                    Object objAccept = safeCallExpression.getSelector().accept(this, data);
                    Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    irExpressionConvertToIrExpression$fir2ir$default = (IrExpression) objAccept;
                }
                return ConversionUtilsKt.createSafeCallConstruction(this.components, irVariableComponent1, irValueSymbolComponent2, irExpressionConvertToIrExpression$fir2ir$default);
            } finally {
                if (irVariableComponent1 != null) {
                    fir2IrConversionScope.getSafeCallSubjectVariableStack().remove(fir2IrConversionScope.getSafeCallSubjectVariableStack().size() - 1);
                }
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(safeCallExpression, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject, Object data) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        IrVariable irVariableLastSafeCallSubject = this.conversionScope.lastSafeCallSubject();
        KtSourceElement source = checkedSafeCallSubject.getSource();
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
        return new IrGetValueImpl(i, endOffset, irVariableLastSafeCallSubject.getType(), irVariableLastSafeCallSubject.getSymbol(), null, 16, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnnotation(FirAnnotation annotation, Object data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return getCallGenerator().convertToIrConstructorCall(annotation);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnnotationCall(FirAnnotationCall annotationCall, Object data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        FirSession session = getSession();
        try {
            return getCallGenerator().convertToIrConstructorCall(annotationCall);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(annotationCall, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, Object data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        return convertQualifiedAccessExpression(qualifiedAccessExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, Object data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        return convertQualifiedAccessExpression(propertyAccessExpression);
    }

    private final IrExpression convertQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression) {
        FirSession session = getSession();
        try {
            return CallAndReferenceGenerator.convertToIrCall$default(getCallGenerator(), qualifiedAccessExpression, qualifiedAccessExpression.getTypeRef(), convertToIrReceiverExpression$fir2ir$default(this, qualifiedAccessExpression.getExplicitReceiver(), qualifiedAccessExpression.getCalleeReference(), null, 4, null), null, false, false, 56, null);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(qualifiedAccessExpression, th);
            throw null;
        }
    }

    private final boolean shouldGenerateReceiverAsSingletonReference(IrClass irClass) {
        IrDeclarationParent irDeclarationParentParent = this.conversionScope.parent();
        return (!IrUtilsKt.isObject(irClass) || Intrinsics.areEqual(irDeclarationParentParent, irClass) || (((irDeclarationParentParent instanceof IrFunction) || (irDeclarationParentParent instanceof IrProperty) || (irDeclarationParentParent instanceof IrField)) && Intrinsics.areEqual(((IrDeclaration) irDeclarationParentParent).getParent(), irClass))) ? false : true;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, Object data) {
        IrSimpleFunction irSimpleFunctionParentAccessorOfPropertyFromStack;
        IrValueParameter extensionReceiverParameter;
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Object next;
        int startOffset2;
        int i2;
        int endOffset2;
        Integer numStartOffsetSkippingComments2;
        IrClass owner;
        int startOffset3;
        int endOffset3;
        Integer numStartOffsetSkippingComments3;
        IrGetValueImpl irGetFieldImpl;
        int startOffset4;
        int endOffset4;
        Integer numStartOffsetSkippingComments4;
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        FirSession session = getSession();
        try {
            FirThisReference calleeReference = thisReceiverExpression.getCalleeReference();
            IrExpression irExpressionInjectGetValueCall$fir2ir = getCallGenerator().injectGetValueCall$fir2ir(thisReceiverExpression, calleeReference);
            if (irExpressionInjectGetValueCall$fir2ir != null) {
                return irExpressionInjectGetValueCall$fir2ir;
            }
            FirBasedSymbol<?> boundSymbol = calleeReference.getBoundSymbol();
            int i3 = -1;
            if (boundSymbol instanceof FirClassSymbol) {
                FirDeclaration fir = boundSymbol.getFir();
                Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirClass");
                FirClass firClass = (FirClass) fir;
                if (Intrinsics.areEqual(firClass.getOrigin(), FirDeclarationOrigin.Source.INSTANCE)) {
                    owner = getClassifierStorage().getCachedIrClass(firClass);
                    Intrinsics.checkNotNull(owner);
                } else {
                    owner = getClassifierStorage().getIrClassSymbol((FirClassSymbol) boundSymbol).getOwner();
                }
                if (!shouldGenerateReceiverAsSingletonReference(owner)) {
                    IrValueParameter irValueParameterDispatchReceiverParameter = this.conversionScope.dispatchReceiverParameter(owner);
                    if (irValueParameterDispatchReceiverParameter != null) {
                        KtSourceElement source = thisReceiverExpression.getSource();
                        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                            endOffset3 = -1;
                            startOffset3 = -1;
                        } else {
                            if (source == null || (numStartOffsetSkippingComments3 = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                                startOffset3 = source != null ? source.getStartOffset() : -1;
                            } else {
                                startOffset3 = numStartOffsetSkippingComments3.intValue();
                            }
                            endOffset3 = source != null ? source.getEndOffset() : -1;
                        }
                        IrGetValueImpl irGetValueImpl = new IrGetValueImpl(startOffset3, endOffset3, irValueParameterDispatchReceiverParameter.getType(), irValueParameterDispatchReceiverParameter.getSymbol(), null, 16, null);
                        if (calleeReference.getContextReceiverNumber() != -1) {
                            IrConstructor constructorForCurrentlyGeneratedDelegatedConstructor = this.conversionScope.getConstructorForCurrentlyGeneratedDelegatedConstructor(owner);
                            if (constructorForCurrentlyGeneratedDelegatedConstructor != null) {
                                IrValueParameter irValueParameter = constructorForCurrentlyGeneratedDelegatedConstructor.getValueParameters().get(calleeReference.getContextReceiverNumber());
                                irGetFieldImpl = new IrGetValueImpl(startOffset3, endOffset3, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null);
                            } else {
                                List<IrField> fieldsWithContextReceiversForClass = this.components.getClassifierStorage().getFieldsWithContextReceiversForClass(owner);
                                if (fieldsWithContextReceiversForClass == null) {
                                    throw new IllegalStateException(("Not defined context receivers for " + owner).toString());
                                }
                                irGetFieldImpl = new IrGetFieldImpl(startOffset3, endOffset3, fieldsWithContextReceiversForClass.get(calleeReference.getContextReceiverNumber()).getSymbol(), toIrType(thisReceiverExpression.getTypeRef()), irGetValueImpl, null, null, 96, null);
                            }
                        } else {
                            irGetFieldImpl = irGetValueImpl;
                        }
                        return irGetFieldImpl;
                    }
                } else {
                    KtSourceElement source2 = thisReceiverExpression.getSource();
                    if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source2))) {
                        endOffset4 = -1;
                    } else {
                        if (source2 == null || (numStartOffsetSkippingComments4 = ConversionUtilsKt.startOffsetSkippingComments(source2)) == null) {
                            startOffset4 = source2 != null ? source2.getStartOffset() : -1;
                        } else {
                            startOffset4 = numStartOffsetSkippingComments4.intValue();
                        }
                        endOffset4 = source2 != null ? source2.getEndOffset() : -1;
                        i3 = startOffset4;
                    }
                    return new IrGetObjectValueImpl(i3, endOffset4, IrUtilsKt.getDefaultType(owner), owner.getSymbol());
                }
            } else {
                if (boundSymbol instanceof FirScriptSymbol) {
                    FirScript fir2 = ((FirScriptSymbol) boundSymbol).getFir();
                    IrScript cachedIrScript = getDeclarationStorage().getCachedIrScript(fir2);
                    if (cachedIrScript == null) {
                        throw new IllegalStateException(("IrScript for " + fir2.getName() + " not found").toString());
                    }
                    Iterator<T> it = cachedIrScript.getImplicitReceiversParameters().iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (((IrValueParameter) next).getIndex() != calleeReference.getContextReceiverNumber());
                    IrValueParameter thisReceiver = (IrValueParameter) next;
                    if (thisReceiver == null) {
                        thisReceiver = cachedIrScript.getThisReceiver();
                    }
                    if (thisReceiver == null) {
                        throw new IllegalStateException("No script receiver found".toString());
                    }
                    KtSourceElement source3 = thisReceiverExpression.getSource();
                    if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source3))) {
                        i2 = -1;
                        endOffset2 = -1;
                    } else {
                        if (source3 == null || (numStartOffsetSkippingComments2 = ConversionUtilsKt.startOffsetSkippingComments(source3)) == null) {
                            startOffset2 = source3 != null ? source3.getStartOffset() : -1;
                        } else {
                            startOffset2 = numStartOffsetSkippingComments2.intValue();
                        }
                        i2 = startOffset2;
                        endOffset2 = source3 != null ? source3.getEndOffset() : -1;
                    }
                    return new IrGetValueImpl(i2, endOffset2, thisReceiver.getType(), thisReceiver.getSymbol(), null, 16, null);
                }
                if (boundSymbol instanceof FirCallableSymbol) {
                    FirCallableSymbol firCallableSymbol = (FirCallableSymbol) boundSymbol;
                    if (firCallableSymbol instanceof FirFunctionSymbol) {
                        irSimpleFunctionParentAccessorOfPropertyFromStack = Fir2IrDeclarationStorage.getIrFunctionSymbol$default(getDeclarationStorage(), (FirFunctionSymbol) boundSymbol, null, 2, null).getOwner();
                    } else if (firCallableSymbol instanceof FirPropertySymbol) {
                        IrSymbolOwner owner2 = Fir2IrDeclarationStorage.getIrPropertySymbol$default(getDeclarationStorage(), (FirPropertySymbol) boundSymbol, null, 2, null).getOwner();
                        IrProperty irProperty = owner2 instanceof IrProperty ? (IrProperty) owner2 : null;
                        irSimpleFunctionParentAccessorOfPropertyFromStack = irProperty != null ? this.conversionScope.parentAccessorOfPropertyFromStack(irProperty) : null;
                    } else {
                        irSimpleFunctionParentAccessorOfPropertyFromStack = null;
                    }
                    if (irSimpleFunctionParentAccessorOfPropertyFromStack == null) {
                        extensionReceiverParameter = null;
                    } else if (calleeReference.getContextReceiverNumber() != -1) {
                        extensionReceiverParameter = irSimpleFunctionParentAccessorOfPropertyFromStack.getValueParameters().get(calleeReference.getContextReceiverNumber());
                    } else {
                        extensionReceiverParameter = irSimpleFunctionParentAccessorOfPropertyFromStack.getExtensionReceiverParameter();
                    }
                    if (extensionReceiverParameter != null) {
                        KtSourceElement source4 = thisReceiverExpression.getSource();
                        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source4))) {
                            i = -1;
                            endOffset = -1;
                        } else {
                            if (source4 == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source4)) == null) {
                                startOffset = source4 != null ? source4.getStartOffset() : -1;
                            } else {
                                startOffset = numStartOffsetSkippingComments.intValue();
                            }
                            i = startOffset;
                            endOffset = source4 != null ? source4.getEndOffset() : -1;
                        }
                        return new IrGetValueImpl(i, endOffset, extensionReceiverParameter.getType(), extensionReceiverParameter.getSymbol(), null, 16, null);
                    }
                }
            }
            return visitQualifiedAccessExpression((FirQualifiedAccessExpression) thisReceiverExpression, data);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(thisReceiverExpression, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitInaccessibleReceiverExpression(FirInaccessibleReceiverExpression inaccessibleReceiverExpression, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(inaccessibleReceiverExpression, "inaccessibleReceiverExpression");
        KtSourceElement source = inaccessibleReceiverExpression.getSource();
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
        return new IrErrorExpressionImpl(i, endOffset, toIrType(inaccessibleReceiverExpression.getTypeRef()), "Receiver is inaccessible");
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitSmartCastExpression(FirSmartCastExpression smartCastExpression, Object data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        return this.implicitCastInserter.visitSmartCastExpression(smartCastExpression, (IrElement) convertToIrExpression$fir2ir$default(this, smartCastExpression.getOriginalExpression(), false, 2, null));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, Object data) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        FirSession session = getSession();
        try {
            return convertCallableReferenceAccess(callableReferenceAccess, false);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(callableReferenceAccess, th);
            throw null;
        }
    }

    private final IrElement convertCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, boolean isDelegate) {
        return getCallGenerator().convertToIrCallableReference(callableReferenceAccess, convertToIrReceiverExpression$fir2ir(callableReferenceAccess.getExplicitReceiver(), callableReferenceAccess.getCalleeReference(), callableReferenceAccess), isDelegate);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitVariableAssignment(FirVariableAssignment variableAssignment, Object data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        FirSession session = getSession();
        try {
            return getCallGenerator().convertToIrSetCall(variableAssignment, convertToIrReceiverExpression$fir2ir$default(this, FirExpressionUtilKt.getExplicitReceiver(variableAssignment), FirExpressionUtilKt.getCalleeReference(variableAssignment), null, 4, null));
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(variableAssignment, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression, Object data) {
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        return (IrElement) desugaredAssignmentValueReferenceExpression.getExpressionRef().getValue().accept(this, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public <T> IrElement visitConstExpression(FirConstExpression<T> constExpression, Object data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        return ConversionUtilsKt.toIrConst(constExpression, toIrType(constExpression.getTypeRef()));
    }

    private final IrStatement toIrStatement(FirStatement firStatement) {
        if (firStatement instanceof FirTypeAlias) {
            return null;
        }
        if (firStatement instanceof FirUnitExpression) {
            KtSourceElement source = firStatement.getSource();
            return (source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind.ImplicitUnit.IndexedAssignmentCoercion ? null : convertToIrExpression$fir2ir$default(this, (FirExpression) firStatement, false, 2, null);
        }
        if (firStatement instanceof FirContractCallBlock) {
            return null;
        }
        if (firStatement instanceof FirBlock) {
            return convertToIrExpression$fir2ir$default(this, (FirExpression) firStatement, false, 2, null);
        }
        Object objAccept = firStatement.accept(this, null);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
        return (IrStatement) objAccept;
    }

    public static /* synthetic */ IrExpression convertToIrExpression$fir2ir$default(Fir2IrVisitor fir2IrVisitor, FirExpression firExpression, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return fir2IrVisitor.convertToIrExpression$fir2ir(firExpression, z);
    }

    public final IrExpression convertToIrExpression$fir2ir(FirExpression expression, boolean isDelegate) {
        IrExpression irExpressionConvertCallableReferenceAccess;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof FirBlock) {
            FirBlock firBlock = (FirBlock) expression;
            KtSourceElement source = expression.getSource();
            irExpressionConvertCallableReferenceAccess = convertToIrExpressionOrBlock(firBlock, Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE) ? IrStatementOrigin.FOR_LOOP.INSTANCE : null);
        } else if (!(expression instanceof FirUnitExpression)) {
            FirExpression firExpressionUnwrapArgument = FirExpressionUtilKt.unwrapArgument(expression);
            if (firExpressionUnwrapArgument instanceof FirCallableReferenceAccess) {
                irExpressionConvertCallableReferenceAccess = convertCallableReferenceAccess((FirCallableReferenceAccess) firExpressionUnwrapArgument, isDelegate);
            } else {
                Object objAccept = expression.accept(this, null);
                Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                irExpressionConvertCallableReferenceAccess = (IrExpression) objAccept;
            }
        } else {
            KtSourceElement source2 = expression.getSource();
            KtSourceElement ktSourceElement = source2;
            int endOffset = -1;
            if (!ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
                if (source2 != null && (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) != null) {
                    numStartOffsetSkippingComments.intValue();
                } else if (source2 != null) {
                    source2.getStartOffset();
                }
                if (source2 != null) {
                    endOffset = source2.getEndOffset();
                }
            }
            irExpressionConvertCallableReferenceAccess = new IrGetObjectValueImpl(endOffset, endOffset, getIrBuiltIns().getUnitType(), getIrBuiltIns().getUnitClass());
        }
        Object objAccept2 = expression.accept(this.implicitCastInserter, irExpressionConvertCallableReferenceAccess);
        Intrinsics.checkNotNull(objAccept2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        return (IrExpression) objAccept2;
    }

    public static /* synthetic */ IrExpression convertToIrReceiverExpression$fir2ir$default(Fir2IrVisitor fir2IrVisitor, FirExpression firExpression, FirReference firReference, FirCallableReferenceAccess firCallableReferenceAccess, int i, Object obj) {
        if ((i & 4) != 0) {
            firCallableReferenceAccess = null;
        }
        return fir2IrVisitor.convertToIrReceiverExpression$fir2ir(firExpression, firReference, firCallableReferenceAccess);
    }

    /* JADX WARN: Code duplicated, block: B:41:0x007f  */
    /* JADX WARN: Code duplicated, block: B:43:0x0094  */
    public final IrExpression convertToIrReceiverExpression$fir2ir(FirExpression expression, FirReference calleeReference, FirCallableReferenceAccess callableReferenceAccess) {
        IrExpression irExpressionConvertToIrExpression$fir2ir$default;
        boolean z;
        CallableId callableId;
        if (expression == null) {
            return null;
        }
        if (expression instanceof FirResolvedQualifier) {
            irExpressionConvertToIrExpression$fir2ir$default = getCallGenerator().convertToGetObject$fir2ir((FirResolvedQualifier) expression, callableReferenceAccess);
        } else {
            if (!(expression instanceof FirFunctionCall ? true : expression instanceof FirThisReceiverExpression ? true : expression instanceof FirCallableReferenceAccess ? true : expression instanceof FirSmartCastExpression) && (expression instanceof FirQualifiedAccessExpression)) {
                FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) expression;
                if (firQualifiedAccessExpression.getExplicitReceiver() == null) {
                    if (calleeReference instanceof FirResolvedNamedReference) {
                        FirResolvedNamedReference firResolvedNamedReference = (FirResolvedNamedReference) calleeReference;
                        if (Intrinsics.areEqual(firResolvedNamedReference.getName(), OperatorNameConventions.INVOKE)) {
                            z = false;
                        } else {
                            FirBasedSymbol<?> resolvedSymbol = firResolvedNamedReference.getResolvedSymbol();
                            FirCallableSymbol firCallableSymbol = resolvedSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) resolvedSymbol : null;
                            if (Intrinsics.areEqual((firCallableSymbol == null || (callableId = firCallableSymbol.getCallableId()) == null) ? null : callableId.getCallableName(), OperatorNameConventions.INVOKE)) {
                                z = true;
                            } else {
                                z = false;
                            }
                        }
                    } else {
                        z = false;
                    }
                    irExpressionConvertToIrExpression$fir2ir$default = CallAndReferenceGenerator.convertToIrCall$default(getCallGenerator(), firQualifiedAccessExpression, firQualifiedAccessExpression.getTypeRef(), null, null, z, false, 40, null);
                } else {
                    irExpressionConvertToIrExpression$fir2ir$default = convertToIrExpression$fir2ir$default(this, expression, false, 2, null);
                }
            } else {
                irExpressionConvertToIrExpression$fir2ir$default = convertToIrExpression$fir2ir$default(this, expression, false, 2, null);
            }
        }
        if (irExpressionConvertToIrExpression$fir2ir$default != null) {
            return ((expression instanceof FirQualifiedAccessExpression) && (((FirQualifiedAccessExpression) expression).getCalleeReference() instanceof FirSuperReference)) ? irExpressionConvertToIrExpression$fir2ir$default : this.implicitCastInserter.implicitCastFromDispatchReceiver$fir2ir(irExpressionConvertToIrExpression$fir2ir$default, expression.getTypeRef(), calleeReference, this.conversionScope.defaultConversionTypeOrigin());
        }
        return null;
    }

    static /* synthetic */ List mapToIrStatements$default(Fir2IrVisitor fir2IrVisitor, List list, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return fir2IrVisitor.mapToIrStatements(list, z);
    }

    private final List<IrStatement> mapToIrStatements(List<? extends FirStatement> list, boolean z) {
        int i;
        IrBlockImpl irStatement;
        IrStatementOrigin statementsOrigin;
        ArrayList arrayList = new ArrayList(list.size());
        int i2 = 0;
        while (i2 < list.size()) {
            if (!z || (statementsOrigin = getStatementsOrigin(list, i2)) == null) {
                i = i2;
                irStatement = null;
            } else {
                i = i2 + 3;
                List<? extends FirStatement> listSubList = list.subList(i2, i);
                ArrayList arrayList2 = new ArrayList();
                Iterator<T> it = listSubList.iterator();
                while (it.hasNext()) {
                    IrStatement irStatement2 = toIrStatement((FirStatement) it.next());
                    if (irStatement2 != null) {
                        arrayList2.add(irStatement2);
                    }
                }
                ArrayList arrayList3 = arrayList2;
                int startOffset = ((IrStatement) arrayList3.get(0)).getStartOffset();
                int endOffset = ((IrStatement) arrayList3.get(2)).getEndOffset();
                Object obj = arrayList3.get(0);
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
                irStatement = new IrBlockImpl(startOffset, endOffset, ((IrVariable) obj).getType(), statementsOrigin, arrayList3);
            }
            if (irStatement == null) {
                irStatement = toIrStatement(list.get(i));
                i++;
            }
            arrayList.add(irStatement);
            i2 = i;
        }
        return arrayList;
    }

    private final IrStatementOrigin getStatementsOrigin(List<? extends FirStatement> list, int i) {
        FirStatement firStatement = (FirStatement) CollectionsKt.getOrNull(list, i + 1);
        if (firStatement instanceof FirVariableAssignment) {
            return ConversionUtilsKt.getIrPrefixPostfixOriginIfAny((FirVariableAssignment) firStatement);
        }
        return null;
    }

    public final IrBlockBody convertToIrBlockBody$fir2ir(FirBlock block) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        List listEmptyList;
        Intrinsics.checkNotNullParameter(block, "block");
        KtSourceElement source = block.getSource();
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
        List listMapToIrStatements$default = mapToIrStatements$default(this, block.getStatements(), false, 1, null);
        IrFactory irFactory = getIrFactory();
        if (!listMapToIrStatements$default.isEmpty()) {
            List listFilterNotNull = CollectionsKt.filterNotNull(listMapToIrStatements$default);
            listEmptyList = listFilterNotNull.isEmpty() ? null : listFilterNotNull;
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.listOf(new IrBlockImpl(i, endOffset, getIrBuiltIns().getUnitType(), null, CollectionsKt.emptyList()));
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        IrBlockBody irBlockBodyCreateBlockBody = IrFactoryHelpersKt.createBlockBody(irFactory, i, endOffset, (List<? extends IrStatement>) listEmptyList);
        this.implicitCastInserter.insertImplicitCasts$fir2ir(irBlockBodyCreateBlockBody);
        return irBlockBodyCreateBlockBody;
    }

    private final boolean isLoop(IrStatementOrigin irStatementOrigin) {
        return Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.DO_WHILE_LOOP.INSTANCE) || Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.WHILE_LOOP.INSTANCE) || Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.FOR_LOOP.INSTANCE);
    }

    private final FirFunctionCall extractOperationFromDynamicSetCall(FirFunctionCall functionCall) {
        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) functionCall.getArgumentList().getArguments());
        FirVarargArgumentsExpression firVarargArgumentsExpression = objFirstOrNull instanceof FirVarargArgumentsExpression ? (FirVarargArgumentsExpression) objFirstOrNull : null;
        List<FirExpression> arguments = firVarargArgumentsExpression != null ? firVarargArgumentsExpression.getArguments() : null;
        FirExpression firExpression = arguments != null ? (FirExpression) CollectionsKt.lastOrNull((List) arguments) : null;
        if (firExpression instanceof FirFunctionCall) {
            return (FirFunctionCall) firExpression;
        }
        return null;
    }

    private final boolean isIncrementOrDecrementCall(FirExpression firExpression) {
        FirNamedReference calleeReference;
        FirResolvedNamedReference resolved;
        Name name2 = null;
        FirFunctionCall firFunctionCall = firExpression instanceof FirFunctionCall ? (FirFunctionCall) firExpression : null;
        if (firFunctionCall != null && (calleeReference = firFunctionCall.getCalleeReference()) != null && (resolved = FirReferenceUtilsKt.getResolved(calleeReference)) != null) {
            name2 = resolved.getName();
        }
        return Intrinsics.areEqual(name2, OperatorNameConventions.INC) || Intrinsics.areEqual(name2, OperatorNameConventions.DEC);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpression tryConvertDynamicIncrementOrDecrementToIr(FirBlock firBlock) {
        Object next;
        FirExpression initializer;
        FirStatement firStatementPrevious;
        FirExpression initializer2;
        FirStatement firStatementPrevious2;
        Pair pair;
        FirStatement firStatementPrevious3;
        IrDynamicOperatorExpression irDynamicOperatorExpressionConvertToIrCall$default;
        FirVarargArgumentsExpression firVarargArgumentsExpression;
        List<FirExpression> arguments;
        FirExpression initializer3;
        FirReference calleeReference;
        FirPropertySymbol resolvedPropertySymbol$default;
        Set<FirExpression> setKeySet;
        FirExpression explicitReceiver;
        Iterator<T> it = firBlock.getStatements().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next instanceof FirProperty));
        if (!(next instanceof FirProperty)) {
            next = null;
        }
        FirProperty firProperty = (FirProperty) next;
        if (firProperty == null || (initializer = firProperty.getInitializer()) == null || !(FirTypeUtilsKt.getConeType(initializer.getTypeRef()) instanceof ConeDynamicType)) {
            return null;
        }
        List<FirStatement> statements = firBlock.getStatements();
        ListIterator<FirStatement> listIterator = statements.listIterator(statements.size());
        do {
            if (!listIterator.hasPrevious()) {
                firStatementPrevious = null;
                break;
            }
            firStatementPrevious = listIterator.previous();
        } while (!(firStatementPrevious instanceof FirProperty));
        if (!(firStatementPrevious instanceof FirProperty)) {
            firStatementPrevious = null;
        }
        FirProperty firProperty2 = (FirProperty) firStatementPrevious;
        if (firProperty2 == null || (initializer2 = firProperty2.getInitializer()) == null) {
            return null;
        }
        if (isIncrementOrDecrementCall(initializer2)) {
            FirFunctionCall firFunctionCall = initializer2 instanceof FirFunctionCall ? (FirFunctionCall) initializer2 : null;
            if (firFunctionCall == null || (explicitReceiver = firFunctionCall.getExplicitReceiver()) == null) {
                return null;
            }
            pair = TuplesKt.to(explicitReceiver, firFunctionCall);
        } else {
            List<FirStatement> statements2 = firBlock.getStatements();
            ListIterator<FirStatement> listIterator2 = statements2.listIterator(statements2.size());
            do {
                if (!listIterator2.hasPrevious()) {
                    firStatementPrevious2 = null;
                    break;
                }
                firStatementPrevious2 = listIterator2.previous();
            } while (!(firStatementPrevious2 instanceof FirVariableAssignment));
            if (!(firStatementPrevious2 instanceof FirVariableAssignment)) {
                firStatementPrevious2 = null;
            }
            FirVariableAssignment firVariableAssignment = (FirVariableAssignment) firStatementPrevious2;
            FirExpression rValue = firVariableAssignment != null ? firVariableAssignment.getRValue() : null;
            FirFunctionCall firFunctionCallExtractOperationFromDynamicSetCall = rValue instanceof FirFunctionCall ? (FirFunctionCall) rValue : null;
            if (firFunctionCallExtractOperationFromDynamicSetCall == null) {
                List<FirStatement> statements3 = firBlock.getStatements();
                ListIterator<FirStatement> listIterator3 = statements3.listIterator(statements3.size());
                do {
                    if (!listIterator3.hasPrevious()) {
                        firStatementPrevious3 = null;
                        break;
                    }
                    firStatementPrevious3 = listIterator3.previous();
                } while (!(firStatementPrevious3 instanceof FirFunctionCall));
                if (!(firStatementPrevious3 instanceof FirFunctionCall)) {
                    firStatementPrevious3 = null;
                }
                FirFunctionCall firFunctionCall2 = (FirFunctionCall) firStatementPrevious3;
                firFunctionCallExtractOperationFromDynamicSetCall = firFunctionCall2 != null ? extractOperationFromDynamicSetCall(firFunctionCall2) : null;
                if (firFunctionCallExtractOperationFromDynamicSetCall == null) {
                    return null;
                }
            }
            pair = TuplesKt.to(initializer2, firFunctionCallExtractOperationFromDynamicSetCall);
        }
        FirExpression firExpression = (FirExpression) pair.component1();
        FirFunctionCall firFunctionCall3 = (FirFunctionCall) pair.component2();
        if (Intrinsics.areEqual(firProperty.getName(), SpecialNames.ARRAY)) {
            FirFunctionCall firFunctionCall4 = firExpression instanceof FirFunctionCall ? (FirFunctionCall) firExpression : null;
            if (firFunctionCall4 == null) {
                return null;
            }
            FirArgumentList argumentList = firFunctionCall4.getArgumentList();
            LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
            if (mapping == null || (setKeySet = mapping.keySet()) == null) {
                firVarargArgumentsExpression = null;
            } else {
                ArrayList arrayList = new ArrayList();
                for (Object obj : setKeySet) {
                    if (obj instanceof FirVarargArgumentsExpression) {
                        arrayList.add(obj);
                    }
                }
                firVarargArgumentsExpression = (FirVarargArgumentsExpression) CollectionsKt.firstOrNull((List) arrayList);
            }
            IrExpression irExpressionConvertToIrCall$default = CallAndReferenceGenerator.convertToIrCall$default(getCallGenerator(), firFunctionCall4, firFunctionCall4.getTypeRef(), convertToIrReceiverExpression$fir2ir$default(this, initializer, firFunctionCall4.getCalleeReference(), null, 4, null), null, false, true, 24, null);
            Intrinsics.checkNotNull(irExpressionConvertToIrCall$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression");
            IrDynamicOperatorExpression irDynamicOperatorExpression = (IrDynamicOperatorExpression) irExpressionConvertToIrCall$default;
            if (firVarargArgumentsExpression != null && (arguments = firVarargArgumentsExpression.getArguments()) != null) {
                for (FirExpression firExpression2 : arguments) {
                    FirPropertyAccessExpression firPropertyAccessExpression = firExpression2 instanceof FirPropertyAccessExpression ? (FirPropertyAccessExpression) firExpression2 : null;
                    FirProperty firProperty3 = (firPropertyAccessExpression == null || (calleeReference = firPropertyAccessExpression.getCalleeReference()) == null || (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) == null) ? null : (FirProperty) resolvedPropertySymbol$default.getFir();
                    if (firProperty3 != null && (initializer3 = firProperty3.getInitializer()) != null) {
                        irDynamicOperatorExpression.getArguments().add(convertToIrExpression$fir2ir$default(this, initializer3, false, 2, null));
                    }
                }
            }
            irDynamicOperatorExpressionConvertToIrCall$default = irDynamicOperatorExpression;
        } else {
            FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
            if (firQualifiedAccessExpression == null) {
                return null;
            }
            irDynamicOperatorExpressionConvertToIrCall$default = CallAndReferenceGenerator.convertToIrCall$default(getCallGenerator(), firQualifiedAccessExpression, firQualifiedAccessExpression.getTypeRef(), convertToIrReceiverExpression$fir2ir$default(this, !Intrinsics.areEqual(initializer, firQualifiedAccessExpression) ? initializer : null, firQualifiedAccessExpression.getCalleeReference(), null, 4, null), null, false, false, 56, null);
        }
        return CallAndReferenceGenerator.convertToIrCall$default(getCallGenerator(), firFunctionCall3, firFunctionCall3.getTypeRef(), irDynamicOperatorExpressionConvertToIrCall$default, null, false, false, 56, null);
    }

    static /* synthetic */ IrExpression convertToIrExpressionOrBlock$default(Fir2IrVisitor fir2IrVisitor, FirBlock firBlock, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irStatementOrigin = null;
        }
        return fir2IrVisitor.convertToIrExpressionOrBlock(firBlock, irStatementOrigin);
    }

    private final IrExpression convertToIrExpressionOrBlock(FirBlock firBlock, IrStatementOrigin irStatementOrigin) {
        IrExpression irExpressionTryConvertDynamicIncrementOrDecrementToIr;
        KtSourceElement source = firBlock.getSource();
        return (!Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE) || (irExpressionTryConvertDynamicIncrementOrDecrementToIr = tryConvertDynamicIncrementOrDecrementToIr(firBlock)) == null) ? convertToIrExpressionOrBlock(firBlock.getStatements(), firBlock.getSource(), irStatementOrigin) : irExpressionTryConvertDynamicIncrementOrDecrementToIr;
    }

    private final IrExpression convertToIrBlock(FirBlock firBlock, boolean z) {
        return convertToIrBlock(firBlock.getStatements(), firBlock.getSource(), null, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0029, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r3 != null ? r3.getKind() : null, org.jetbrains.kotlin.KtFakeSourceElementKind.DesugaredForLoop.INSTANCE) == false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final org.jetbrains.kotlin.ir.expressions.IrExpression convertToIrExpressionOrBlock(java.util.List<? extends org.jetbrains.kotlin.fir.expressions.FirStatement> r7, org.jetbrains.kotlin.KtSourceElement r8, org.jetbrains.kotlin.ir.expressions.IrStatementOrigin r9) {
        /*
            r6 = this;
            int r0 = r7.size()
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L33
            java.lang.Object r0 = kotlin.collections.CollectionsKt.single(r7)
            org.jetbrains.kotlin.fir.expressions.FirStatement r0 = (org.jetbrains.kotlin.fir.expressions.FirStatement) r0
            boolean r3 = r0 instanceof org.jetbrains.kotlin.fir.expressions.FirExpression
            if (r3 == 0) goto L33
            boolean r3 = r0 instanceof org.jetbrains.kotlin.fir.expressions.FirBlock
            r4 = 0
            if (r3 == 0) goto L2b
            org.jetbrains.kotlin.KtSourceElement r3 = r0.getSource()
            if (r3 == 0) goto L22
            org.jetbrains.kotlin.KtSourceElementKind r3 = r3.getKind()
            goto L23
        L22:
            r3 = r4
        L23:
            org.jetbrains.kotlin.KtFakeSourceElementKind$DesugaredForLoop r5 = org.jetbrains.kotlin.KtFakeSourceElementKind.DesugaredForLoop.INSTANCE
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r5)
            if (r3 != 0) goto L33
        L2b:
            org.jetbrains.kotlin.fir.expressions.FirExpression r0 = (org.jetbrains.kotlin.fir.expressions.FirExpression) r0
            r7 = 2
            org.jetbrains.kotlin.ir.expressions.IrExpression r7 = convertToIrExpression$fir2ir$default(r6, r0, r1, r7, r4)
            return r7
        L33:
            if (r9 == 0) goto L3c
            boolean r0 = r6.isLoop(r9)
            if (r0 != r2) goto L3c
            r1 = r2
        L3c:
            org.jetbrains.kotlin.ir.expressions.IrExpression r7 = r6.convertToIrBlock(r7, r8, r9, r1)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.backend.Fir2IrVisitor.convertToIrExpressionOrBlock(java.util.List, org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.ir.expressions.IrStatementOrigin):org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00a4  */
    private final IrExpression convertToIrBlock(List<? extends FirStatement> list, KtSourceElement ktSourceElement, IrStatementOrigin irStatementOrigin, boolean z) {
        IrType unitType;
        FirTypeRef typeRef;
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrBlockImpl irBlockImpl;
        IrContainerExpression irCompositeImpl;
        if (z) {
            unitType = getIrBuiltIns().getUnitType();
        } else {
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) list);
            FirExpression firExpression = objLastOrNull instanceof FirExpression ? (FirExpression) objLastOrNull : null;
            if (firExpression == null || (typeRef = firExpression.getTypeRef()) == null || (unitType = toIrType(typeRef)) == null) {
                unitType = getIrBuiltIns().getUnitType();
            }
        }
        IrType irType = unitType;
        KtSourceElement ktSourceElement2 = ktSourceElement;
        int i = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement2))) {
            endOffset = -1;
        } else {
            if (ktSourceElement == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement2)) == null) {
                startOffset = ktSourceElement != null ? ktSourceElement.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = ktSourceElement != null ? ktSourceElement.getEndOffset() : -1;
            i = startOffset;
        }
        if (Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.DO_WHILE_LOOP.INSTANCE)) {
            irCompositeImpl = new IrCompositeImpl(i, endOffset, irType, irStatementOrigin, CollectionsKt.filterNotNull(mapToIrStatements(list, false)));
        } else {
            List listMapToIrStatements$default = mapToIrStatements$default(this, list, false, 1, null);
            IrStatement irStatement = (IrStatement) CollectionsKt.singleOrNull(listMapToIrStatements$default);
            if (irStatement instanceof IrBlock) {
                irBlockImpl = (IrBlock) irStatement;
                if (!Intrinsics.areEqual(irBlockImpl.getOrigin(), IrStatementOrigin.POSTFIX_INCR.INSTANCE) && !Intrinsics.areEqual(irBlockImpl.getOrigin(), IrStatementOrigin.POSTFIX_DECR.INSTANCE)) {
                    irBlockImpl = new IrBlockImpl(i, endOffset, irType, irStatementOrigin, CollectionsKt.filterNotNull(listMapToIrStatements$default));
                }
            } else {
                irBlockImpl = new IrBlockImpl(i, endOffset, irType, irStatementOrigin, CollectionsKt.filterNotNull(listMapToIrStatements$default));
            }
            irCompositeImpl = irBlockImpl;
        }
        return irCompositeImpl;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitErrorExpression(FirErrorExpression errorExpression, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
        KtSourceElement source = errorExpression.getSource();
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
        return new IrErrorExpressionImpl(i, endOffset, toIrType(errorExpression.getTypeRef()), errorExpression.getDiagnostic().getReason());
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitEnumEntryDeserializedAccessExpression(FirEnumEntryDeserializedAccessExpression enumEntryDeserializedAccessExpression, Object data) {
        Intrinsics.checkNotNullParameter(enumEntryDeserializedAccessExpression, "enumEntryDeserializedAccessExpression");
        return visitPropertyAccessExpression(FirEnumEntryDeserializerAccessUtilKt.toQualifiedPropertyAccessExpression(enumEntryDeserializedAccessExpression, getSession()), data);
    }

    private static final IrGetValue visitElvisExpression$lambda$89$irGetLhsValue(int i, int i2, IrVariable irVariable) {
        return new IrGetValueImpl(i, i2, irVariable.getType(), irVariable.getSymbol(), null, 16, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitWhenExpression(FirWhenExpression whenExpression, Object data) {
        int startOffset;
        Integer numStartOffsetSkippingComments;
        FirImplicitBuiltinTypeRef unitType;
        IrBlockImpl irBlockImplGenerateWhen;
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        IrVariable irVariableGenerateWhenSubjectVariable = generateWhenSubjectVariable(whenExpression);
        KtSourceElement source = whenExpression.getSource();
        IrStatementOrigin.EXCLEXCL exclexcl = null;
        IElementType elementType = source != null ? source.getElementType() : null;
        if (Intrinsics.areEqual(elementType, KtNodeTypes.WHEN)) {
            exclexcl = IrStatementOrigin.WHEN.INSTANCE;
        } else if (Intrinsics.areEqual(elementType, KtNodeTypes.IF)) {
            exclexcl = IrStatementOrigin.IF.INSTANCE;
        } else if (Intrinsics.areEqual(elementType, KtNodeTypes.BINARY_EXPRESSION)) {
            KtSourceElement source2 = whenExpression.getSource();
            IElementType operationToken = source2 != null ? Fir2IrVisitorKt.getOperationToken(source2) : null;
            if (Intrinsics.areEqual(operationToken, KtTokens.OROR)) {
                exclexcl = IrStatementOrigin.OROR.INSTANCE;
            } else if (Intrinsics.areEqual(operationToken, KtTokens.ANDAND)) {
                exclexcl = IrStatementOrigin.ANDAND.INSTANCE;
            }
        } else if (Intrinsics.areEqual(elementType, KtNodeTypes.POSTFIX_EXPRESSION)) {
            exclexcl = IrStatementOrigin.EXCLEXCL.INSTANCE;
        }
        Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
        if (irVariableGenerateWhenSubjectVariable != null) {
            fir2IrConversionScope.getWhenSubjectVariableStack().add(irVariableGenerateWhenSubjectVariable);
        }
        try {
            KtSourceElement source3 = whenExpression.getSource();
            int endOffset = -1;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source3))) {
                startOffset = -1;
            } else {
                if (source3 == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source3)) == null) {
                    startOffset = source3 != null ? source3.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                if (source3 != null) {
                    endOffset = source3.getEndOffset();
                }
            }
            if (whenExpression.getBranches().isEmpty()) {
                irBlockImplGenerateWhen = new IrBlockImpl(startOffset, endOffset, getIrBuiltIns().getUnitType(), exclexcl);
            } else {
                if (ExhaustivenessStatusKt.isProperlyExhaustive(whenExpression)) {
                    List<FirWhenBranch> branches = whenExpression.getBranches();
                    if (!(branches instanceof Collection) || !branches.isEmpty()) {
                        Iterator<T> it = branches.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                FirWhenBranch firWhenBranch = (FirWhenBranch) it.next();
                                if ((firWhenBranch.getCondition() instanceof FirElseIfTrueCondition) && firWhenBranch.getResult().getStatements().isEmpty()) {
                                    unitType = getSession().getBuiltinTypes().getUnitType();
                                }
                            }
                        }
                    }
                    unitType = whenExpression.getTypeRef();
                } else {
                    unitType = getSession().getBuiltinTypes().getUnitType();
                }
                List<FirWhenBranch> branches2 = whenExpression.getBranches();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it2 = branches2.iterator();
                while (it2.hasNext()) {
                    arrayList.add(toIrWhenBranch((FirWhenBranch) it2.next(), unitType));
                }
                ArrayList arrayList2 = arrayList;
                if (ExhaustivenessStatusKt.isProperlyExhaustive(whenExpression)) {
                    List<FirWhenBranch> branches3 = whenExpression.getBranches();
                    if (!(branches3 instanceof Collection) || !branches3.isEmpty()) {
                        Iterator<T> it3 = branches3.iterator();
                        do {
                            if (it3.hasNext()) {
                            }
                        } while (!(((FirWhenBranch) it3.next()).getCondition() instanceof FirElseIfTrueCondition));
                    }
                    arrayList2.add(new IrElseBranchImpl(IrConstImpl.INSTANCE.m7206boolean(startOffset, endOffset, getIrBuiltIns().getBooleanType(), true), new IrCallImpl(startOffset, endOffset, getIrBuiltIns().getNothingType(), getIrBuiltIns().getNoWhenBranchMatchedExceptionSymbol(), 0, 0, null, null, 192, null)));
                    break;
                }
                irBlockImplGenerateWhen = generateWhen(startOffset, endOffset, exclexcl, irVariableGenerateWhenSubjectVariable, arrayList2, toIrType(unitType));
            }
            IrExpression irExpression = irBlockImplGenerateWhen;
            if (irVariableGenerateWhenSubjectVariable != null) {
                fir2IrConversionScope.getWhenSubjectVariableStack().remove(fir2IrConversionScope.getWhenSubjectVariableStack().size() - 1);
            }
            whenExpression.accept(this.implicitCastInserter, irExpression);
            return irExpression;
        } catch (Throwable th) {
            if (irVariableGenerateWhenSubjectVariable != null) {
                fir2IrConversionScope.getWhenSubjectVariableStack().remove(fir2IrConversionScope.getWhenSubjectVariableStack().size() - 1);
            }
            throw th;
        }
    }

    private final IrExpression generateWhen(int startOffset, int endOffset, IrStatementOrigin origin, IrVariable subjectVariable, List<? extends IrBranch> branches, IrType resultType) {
        IrWhenImpl irWhenImpl = new IrWhenImpl(startOffset, endOffset, resultType, origin, branches);
        if (subjectVariable == null) {
            return irWhenImpl;
        }
        return new IrBlockImpl(startOffset, endOffset, irWhenImpl.getType(), origin, CollectionsKt.listOf((Object[]) new IrElement[]{subjectVariable, irWhenImpl}));
    }

    private final IrVariable generateWhenSubjectVariable(FirWhenExpression whenExpression) {
        FirVariable subjectVariable = whenExpression.getSubjectVariable();
        FirExpression subject = whenExpression.getSubject();
        if (subjectVariable != null) {
            Object objAccept = subjectVariable.accept(this, null);
            Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
            return (IrVariable) objAccept;
        }
        if (subject != null) {
            return (IrVariable) applyParentFromStackTo(getDeclarationStorage().declareTemporaryVariable(convertToIrExpression$fir2ir$default(this, subject, false, 2, null), "subject"));
        }
        return null;
    }

    private final IrBranch toIrWhenBranch(FirWhenBranch firWhenBranch, FirTypeRef firTypeRef) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrBranchImpl irBranchImpl;
        KtSourceElement source = firWhenBranch.getSource();
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
        FirExpression condition = firWhenBranch.getCondition();
        IrExpression irExpressionInsertImplicitCast = insertImplicitCast(convertToIrExpression$fir2ir$default(this, firWhenBranch.getResult(), false, 2, null), firWhenBranch.getResult(), firWhenBranch.getResult().getTypeRef(), firTypeRef);
        if (condition instanceof FirElseIfTrueCondition) {
            irBranchImpl = new IrElseBranchImpl(IrConstImpl.INSTANCE.m7206boolean(irExpressionInsertImplicitCast.getStartOffset(), irExpressionInsertImplicitCast.getEndOffset(), getIrBuiltIns().getBooleanType(), true), irExpressionInsertImplicitCast);
        } else {
            irBranchImpl = new IrBranchImpl(i, endOffset, convertToIrExpression$fir2ir$default(this, condition, false, 2, null), irExpressionInsertImplicitCast);
        }
        return (IrBranch) irBranchImpl;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression, Object data) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        IrVariable irVariableLastWhenSubject = this.conversionScope.lastWhenSubject();
        KtSourceElement source = whenSubjectExpression.getSource();
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
        return new IrGetValueImpl(i, endOffset, irVariableLastWhenSubject.getType(), irVariableLastWhenSubject.getSymbol(), null, 16, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitDoWhileLoop(FirDoWhileLoop doWhileLoop, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        KtSourceElement source = doWhileLoop.getSource();
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
        IrDoWhileLoopImpl irDoWhileLoopImpl = new IrDoWhileLoopImpl(i, endOffset, getIrBuiltIns().getUnitType(), IrStatementOrigin.DO_WHILE_LOOP.INSTANCE);
        this.loopMap.put(doWhileLoop, irDoWhileLoopImpl);
        FirLabel label = doWhileLoop.getLabel();
        irDoWhileLoopImpl.setLabel(label != null ? label.getName() : null);
        irDoWhileLoopImpl.setBody(convertToIrExpressionOrBlock(doWhileLoop.getBlock(), irDoWhileLoopImpl.getOrigin()));
        irDoWhileLoopImpl.setCondition(convertToIrExpression$fir2ir$default(this, doWhileLoop.getCondition(), false, 2, null));
        this.loopMap.remove(doWhileLoop);
        IrDoWhileLoopImpl irDoWhileLoopImpl2 = irDoWhileLoopImpl;
        doWhileLoop.accept(this.implicitCastInserter, irDoWhileLoopImpl2);
        IrBlockImpl irBlockImpl = new IrBlockImpl(irDoWhileLoopImpl2.getStartOffset(), irDoWhileLoopImpl2.getEndOffset(), getIrBuiltIns().getUnitType(), null, 8, null);
        irBlockImpl.getStatements().add(irDoWhileLoopImpl2);
        return irBlockImpl;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitWhileLoop(FirWhileLoop whileLoop, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrBlockImpl irBlockImplConvertToIrExpressionOrBlock$default;
        int startOffset2;
        int endOffset2;
        Integer numStartOffsetSkippingComments2;
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        KtSourceElement source = whileLoop.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            endOffset = -1;
            startOffset = -1;
        } else {
            startOffset = (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) ? source != null ? source.getStartOffset() : -1 : numStartOffsetSkippingComments.intValue();
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        KtSourceElement source2 = whileLoop.getSource();
        boolean zAreEqual = Intrinsics.areEqual(source2 != null ? source2.getElementType() : null, KtNodeTypes.FOR);
        IrStatementOriginImpl irStatementOriginImpl = zAreEqual ? IrStatementOrigin.FOR_LOOP_INNER_WHILE.INSTANCE : IrStatementOrigin.WHILE_LOOP.INSTANCE;
        FirBlock block = whileLoop.getBlock();
        IrStatementOriginImpl irStatementOriginImpl2 = irStatementOriginImpl;
        IrWhileLoopImpl irWhileLoopImpl = new IrWhileLoopImpl(startOffset, endOffset, getIrBuiltIns().getUnitType(), irStatementOriginImpl2);
        this.loopMap.put(whileLoop, irWhileLoopImpl);
        FirLabel label = whileLoop.getLabel();
        irWhileLoopImpl.setLabel(label != null ? label.getName() : null);
        int i = 0;
        irWhileLoopImpl.setCondition(convertToIrExpression$fir2ir$default(this, whileLoop.getCondition(), false, 2, null));
        if (!zAreEqual) {
            irBlockImplConvertToIrExpressionOrBlock$default = convertToIrExpressionOrBlock$default(this, block, null, 1, null);
        } else {
            KtSourceElement source3 = block.getSource();
            KtSourceElement ktSourceElement2 = source3;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement2))) {
                startOffset2 = -1;
                endOffset2 = -1;
            } else {
                startOffset2 = (source3 == null || (numStartOffsetSkippingComments2 = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement2)) == null) ? source3 != null ? source3.getStartOffset() : -1 : numStartOffsetSkippingComments2.intValue();
                endOffset2 = source3 != null ? source3.getEndOffset() : -1;
            }
            List<FirStatement> statements = block.getStatements();
            if (statements.isEmpty()) {
                throw new IllegalStateException("Unexpected shape of body of for loop".toString());
            }
            ArrayList arrayList = new ArrayList();
            for (FirStatement firStatement : statements) {
                if (i > 0 && (!(firStatement instanceof FirProperty) || !(((FirProperty) firStatement).getInitializer() instanceof FirComponentCall))) {
                    break;
                }
                IrStatement irStatement = toIrStatement(firStatement);
                if (irStatement != null) {
                    arrayList.add(irStatement);
                }
                i++;
            }
            irBlockImplConvertToIrExpressionOrBlock$default = new IrBlockImpl(startOffset2, endOffset2, getIrBuiltIns().getUnitType(), irStatementOriginImpl2, CollectionsKt.plus((Collection<? extends IrExpression>) arrayList, convertToIrExpressionOrBlock(CollectionsKt.drop(statements, i), block.getSource(), null)));
        }
        irWhileLoopImpl.setBody(irBlockImplConvertToIrExpressionOrBlock$default);
        this.loopMap.remove(whileLoop);
        IrWhileLoopImpl irWhileLoopImpl2 = irWhileLoopImpl;
        whileLoop.accept(this.implicitCastInserter, irWhileLoopImpl2);
        return irWhileLoopImpl2;
    }

    private final IrExpression convertJumpWithOffsets(FirJump<FirLoop> firJump, Function4<? super Integer, ? super Integer, ? super IrLoop, ? super String, ? extends IrBreakContinue> function4) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrBreakContinue irBreakContinueInvoke;
        FirJump<FirLoop> firJump2 = firJump;
        KtSourceElement source = firJump2.getSource();
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
        IrLoop irLoop = this.loopMap.get((FirLoop) firJump.getTarget().getLabeledElement());
        if (irLoop == null) {
            irBreakContinueInvoke = new IrErrorExpressionImpl(i, endOffset, getIrBuiltIns().getNothingType(), "Unbound loop: " + UtilsKt.render(firJump2));
        } else {
            Integer numValueOf = Integer.valueOf(i);
            Integer numValueOf2 = Integer.valueOf(endOffset);
            String label = irLoop.getLabel();
            if (firJump.getTarget().getLabelName() == null) {
                label = null;
            }
            irBreakContinueInvoke = function4.invoke(numValueOf, numValueOf2, irLoop, label);
        }
        return (IrExpression) irBreakContinueInvoke;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitBreakExpression(FirBreakExpression breakExpression, Object data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        return convertJumpWithOffsets(breakExpression, new Function4<Integer, Integer, IrLoop, String, IrBreakContinue>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrVisitor.visitBreakExpression.1
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ IrBreakContinue invoke(Integer num, Integer num2, IrLoop irLoop, String str) {
                return invoke(num.intValue(), num2.intValue(), irLoop, str);
            }

            public final IrBreakContinue invoke(int i, int i2, IrLoop irLoop, String str) {
                Intrinsics.checkNotNullParameter(irLoop, "irLoop");
                IrBreakImpl irBreakImpl = new IrBreakImpl(i, i2, Fir2IrVisitor.this.getIrBuiltIns().getNothingType(), irLoop);
                irBreakImpl.setLabel(str);
                return irBreakImpl;
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitContinueExpression(FirContinueExpression continueExpression, Object data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        return convertJumpWithOffsets(continueExpression, new Function4<Integer, Integer, IrLoop, String, IrBreakContinue>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrVisitor.visitContinueExpression.1
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ IrBreakContinue invoke(Integer num, Integer num2, IrLoop irLoop, String str) {
                return invoke(num.intValue(), num2.intValue(), irLoop, str);
            }

            public final IrBreakContinue invoke(int i, int i2, IrLoop irLoop, String str) {
                Intrinsics.checkNotNullParameter(irLoop, "irLoop");
                IrContinueImpl irContinueImpl = new IrContinueImpl(i, i2, Fir2IrVisitor.this.getIrBuiltIns().getNothingType(), irLoop);
                irContinueImpl.setLabel(str);
                return irContinueImpl;
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitThrowExpression(FirThrowExpression throwExpression, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        KtSourceElement source = throwExpression.getSource();
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
        return new IrThrowImpl(i, endOffset, getIrBuiltIns().getNothingType(), convertToIrExpression$fir2ir$default(this, throwExpression.getException(), false, 2, null));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitTryExpression(FirTryExpression tryExpression, Object data) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        KtSourceElement source = tryExpression.getSource();
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
        IrType irType = toIrType(tryExpression.getTypeRef());
        IrExpression irExpressionConvertToIrBlock = convertToIrBlock(tryExpression.getTryBlock(), false);
        List<FirCatch> catches = tryExpression.getCatches();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(catches, 10));
        Iterator<T> it = catches.iterator();
        while (it.hasNext()) {
            Object objAccept = ((FirCatch) it.next()).accept(this, data);
            Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCatch");
            arrayList.add((IrCatch) objAccept);
        }
        ArrayList arrayList2 = arrayList;
        FirBlock finallyBlock = tryExpression.getFinallyBlock();
        IrTryImpl irTryImpl = new IrTryImpl(i, endOffset, irType, irExpressionConvertToIrBlock, arrayList2, finallyBlock != null ? convertToIrBlock(finallyBlock, true) : null);
        tryExpression.accept(this.implicitCastInserter, irTryImpl);
        return irTryImpl;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitCatch(FirCatch firCatch, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(firCatch, "catch");
        KtSourceElement source = firCatch.getSource();
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
        return new IrCatchImpl(i, endOffset, getDeclarationStorage().createIrVariable(firCatch.getParameter(), this.conversionScope.parentFromStack(), IrDeclarationOrigin.CATCH_PARAMETER.INSTANCE), convertToIrBlock(firCatch.getBlock(), false));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitComparisonExpression(FirComparisonExpression comparisonExpression, Object data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        return this.operatorGenerator.convertComparisonExpression(comparisonExpression);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        FirSession session = getSession();
        Object obj = null;
        try {
            KtSourceElement source = stringConcatenationCall.getSource();
            int startOffset2 = -1;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                endOffset = -1;
                startOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            Iterator<FirExpression> it = stringConcatenationCall.getArgumentList().getArguments().iterator();
            int endOffset2 = -1;
            while (it.hasNext()) {
                try {
                    IrExpression irExpressionConvertToIrExpression$fir2ir$default = convertToIrExpression$fir2ir$default(this, it.next(), false, 2, obj);
                    if (irExpressionConvertToIrExpression$fir2ir$default instanceof IrConst) {
                        if (sb.length() == 0) {
                            startOffset2 = irExpressionConvertToIrExpression$fir2ir$default.getStartOffset();
                        }
                        sb.append(((IrConst) irExpressionConvertToIrExpression$fir2ir$default).getValue());
                        endOffset2 = irExpressionConvertToIrExpression$fir2ir$default.getEndOffset();
                    } else {
                        if (sb.length() > 0) {
                            IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
                            IrType stringType = getIrBuiltIns().getStringType();
                            String string = sb.toString();
                            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                            arrayList.add(companion.string(startOffset2, endOffset2, stringType, string));
                            StringsKt.clear(sb);
                        }
                        arrayList.add(irExpressionConvertToIrExpression$fir2ir$default);
                        obj = null;
                    }
                } catch (Throwable th) {
                    th = th;
                    UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(stringConcatenationCall, th);
                    throw null;
                }
            }
            if (sb.length() > 0) {
                IrConstImpl.Companion companion2 = IrConstImpl.INSTANCE;
                IrType stringType2 = getIrBuiltIns().getStringType();
                String string2 = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                arrayList.add(companion2.string(startOffset2, endOffset2, stringType2, string2));
            }
            return new IrStringConcatenationImpl(startOffset, endOffset, getIrBuiltIns().getStringType(), arrayList);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, Object data) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Pair pair;
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        KtSourceElement source = typeOperatorCall.getSource();
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
        IrType irType = toIrType(typeOperatorCall.getConversionTypeRef());
        int i2 = WhenMappings.$EnumSwitchMapping$0[typeOperatorCall.getOperation().ordinal()];
        if (i2 == 1) {
            pair = TuplesKt.to(getIrBuiltIns().getBooleanType(), IrTypeOperator.INSTANCEOF);
        } else if (i2 == 2) {
            pair = TuplesKt.to(getIrBuiltIns().getBooleanType(), IrTypeOperator.NOT_INSTANCEOF);
        } else if (i2 == 3) {
            pair = TuplesKt.to(irType, IrTypeOperator.CAST);
        } else {
            if (i2 != 4) {
                throw new NotImplementedError("An operation is not implemented: " + ("Should not be here: " + typeOperatorCall.getOperation() + " in type operator call"));
            }
            pair = TuplesKt.to(IrTypesKt.makeNullable(irType), IrTypeOperator.SAFE_CAST);
        }
        return new IrTypeOperatorCallImpl(i, endOffset, (IrType) pair.component1(), (IrTypeOperator) pair.component2(), irType, convertToIrExpression$fir2ir$default(this, (FirExpression) CollectionsKt.first((List) typeOperatorCall.getArgumentList().getArguments()), false, 2, null));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, Object data) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        FirSession session = getSession();
        try {
            return this.operatorGenerator.convertEqualityOperatorCall(equalityOperatorCall);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(equalityOperatorCall, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, Object data) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        FirSession session = getSession();
        try {
            KtSourceElement source = checkNotNullCall.getSource();
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                i = -1;
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                i = startOffset;
                endOffset = source != null ? source.getEndOffset() : -1;
            }
            IrCallImpl irCallImpl = new IrCallImpl(i, endOffset, toIrType(checkNotNullCall.getTypeRef()), getIrBuiltIns().getCheckNotNullSymbol(), 1, 1, IrStatementOrigin.EXCLEXCL.INSTANCE, null, 128, null);
            irCallImpl.putTypeArgument(0, IrTypesKt.makeNotNull(toIrType(((FirExpression) CollectionsKt.first((List) checkNotNullCall.getArgumentList().getArguments())).getTypeRef())));
            irCallImpl.putValueArgument(0, convertToIrExpression$fir2ir$default(this, (FirExpression) CollectionsKt.first((List) checkNotNullCall.getArgumentList().getArguments()), false, 2, null));
            return irCallImpl;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(checkNotNullCall, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitGetClassCall(FirGetClassCall getClassCall, Object data) {
        IrType irType;
        IrClassSymbol irTypeParameterSymbol;
        IrClassSymbol symbol;
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrClassSymbol irClassSymbol;
        int startOffset2;
        int endOffset2;
        Integer numStartOffsetSkippingComments2;
        IrGetClassImpl irGetClassImpl;
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        FirSession session = getSession();
        try {
            FirExpression argument = getClassCall.getArgument();
            IrType irType2 = toIrType(getClassCall.getTypeRef());
            if (argument instanceof FirClassReferenceExpression) {
                irType = toIrType(((FirClassReferenceExpression) argument).getClassTypeRef());
            } else {
                irType = toIrType(argument.getTypeRef());
            }
            IrType irType3 = irType;
            int i = -1;
            if (argument instanceof FirResolvedReifiedParameterReference) {
                irTypeParameterSymbol = getClassifierStorage().getIrTypeParameterSymbol(((FirResolvedReifiedParameterReference) argument).getSymbol(), ConversionTypeOrigin.DEFAULT);
            } else if (argument instanceof FirResolvedQualifier) {
                FirClassLikeSymbol<?> symbol2 = ((FirResolvedQualifier) argument).getSymbol();
                if (symbol2 instanceof FirClassSymbol) {
                    irClassSymbol = getClassifierStorage().getIrClassSymbol((FirClassSymbol) symbol2);
                } else if (symbol2 instanceof FirTypeAliasSymbol) {
                    irClassSymbol = toIrClassSymbol(TypeExpansionUtilsKt.fullyExpandedConeType((FirTypeAlias) ((FirTypeAliasSymbol) symbol2).getFir(), getSession()));
                } else {
                    KtSourceElement source = getClassCall.getSource();
                    if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                        endOffset = -1;
                    } else {
                        if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                            startOffset = source != null ? source.getStartOffset() : -1;
                        } else {
                            startOffset = numStartOffsetSkippingComments.intValue();
                        }
                        endOffset = source != null ? source.getEndOffset() : -1;
                        i = startOffset;
                    }
                    return new IrErrorCallExpressionImpl(i, endOffset, irType2, "Resolved qualifier " + UtilsKt.render(argument) + " does not have correct symbol");
                }
                irTypeParameterSymbol = irClassSymbol;
            } else if (argument instanceof FirClassReferenceExpression) {
                ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(((FirClassReferenceExpression) argument).getClassTypeRef()));
                ConeClassLikeType coneClassLikeType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType ? (ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
                if (coneClassLikeType == null || (symbol = toIrClassSymbol(coneClassLikeType)) == null) {
                    symbol = IrErrorClassImpl.INSTANCE.getSymbol();
                }
                irTypeParameterSymbol = symbol;
            } else {
                irTypeParameterSymbol = null;
            }
            KtSourceElement source2 = getClassCall.getSource();
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source2))) {
                startOffset2 = -1;
                endOffset2 = -1;
            } else {
                if (source2 == null || (numStartOffsetSkippingComments2 = ConversionUtilsKt.startOffsetSkippingComments(source2)) == null) {
                    startOffset2 = source2 != null ? source2.getStartOffset() : -1;
                } else {
                    startOffset2 = numStartOffsetSkippingComments2.intValue();
                }
                endOffset2 = source2 != null ? source2.getEndOffset() : -1;
            }
            if (irTypeParameterSymbol != null) {
                irGetClassImpl = new IrClassReferenceImpl(startOffset2, endOffset2, irType2, irTypeParameterSymbol, irType3);
            } else {
                irGetClassImpl = new IrGetClassImpl(startOffset2, endOffset2, irType2, convertToIrExpression$fir2ir$default(this, argument, false, 2, null));
            }
            return irGetClassImpl;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(getClassCall, th);
            throw null;
        }
    }

    private final IrClassSymbol toIrClassSymbol(ConeClassLikeType coneClassLikeType) {
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol = (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : LookupTagUtilsKt.toSymbol(lookupTag, getSession());
        FirClassSymbol<?> firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
        if (firClassSymbol != null) {
            return getClassifierStorage().getIrClassSymbol(firClassSymbol);
        }
        return null;
    }

    private final IrVararg convertToArrayLiteral(FirArrayLiteral arrayLiteral) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrErrorType irErrorTypeCreateErrorType;
        KtSourceElement source = arrayLiteral.getSource();
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
        IrType irType = toIrType(arrayLiteral.getTypeRef());
        if (arrayLiteral.getTypeRef() instanceof FirResolvedTypeRef) {
            irErrorTypeCreateErrorType = IrTypeUtilsKt.getArrayElementType(irType, getIrBuiltIns());
        } else {
            irErrorTypeCreateErrorType = ConversionUtilsKt.createErrorType();
        }
        IrType irType2 = irErrorTypeCreateErrorType;
        List<FirExpression> arguments = arrayLiteral.getArgumentList().getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(convertToIrVarargElement((FirExpression) it.next()));
        }
        return new IrVarargImpl(i, endOffset, irType, irType2, arrayList);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitArrayLiteral(FirArrayLiteral arrayLiteral, Object data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        FirSession session = getSession();
        try {
            return convertToArrayLiteral(arrayLiteral);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(arrayLiteral, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        FirSession session = getSession();
        try {
            KtSourceElement source = augmentedArraySetCall.getSource();
            int i = -1;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(source))) {
                endOffset = -1;
            } else {
                if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                    startOffset = source != null ? source.getStartOffset() : -1;
                } else {
                    startOffset = numStartOffsetSkippingComments.intValue();
                }
                endOffset = source != null ? source.getEndOffset() : -1;
                i = startOffset;
            }
            return new IrErrorCallExpressionImpl(i, endOffset, getIrBuiltIns().getUnitType(), "FirArraySetCall (resolve isn't supported yet)");
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(augmentedArraySetCall, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitResolvedQualifier(FirResolvedQualifier resolvedQualifier, Object data) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        return getCallGenerator().convertToGetObject$fir2ir(resolvedQualifier);
    }

    private final IrDynamicOperator toIrDynamicOperator(LogicOperationKind logicOperationKind) {
        int i = WhenMappings.$EnumSwitchMapping$1[logicOperationKind.ordinal()];
        if (i == 1) {
            return IrDynamicOperator.ANDAND;
        }
        if (i == 2) {
            return IrDynamicOperator.OROR;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, Object data) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        IrIfThenElseImpl irIfThenElseImpl;
        IrIfThenElseImpl irIfThenElseImpl2;
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        KtSourceElement source = binaryLogicExpression.getSource();
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
        Fir2IrVisitor fir2IrVisitor = this;
        Object objAccept = binaryLogicExpression.getLeftOperand().accept(fir2IrVisitor, data);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        IrExpression irExpression = (IrExpression) objAccept;
        Object objAccept2 = binaryLogicExpression.getRightOperand().accept(fir2IrVisitor, data);
        Intrinsics.checkNotNull(objAccept2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        IrExpression irExpression2 = (IrExpression) objAccept2;
        if (irExpression.getType() instanceof IrDynamicType) {
            IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(i, endOffset, getIrBuiltIns().getBooleanType(), toIrDynamicOperator(binaryLogicExpression.getKind()));
            irDynamicOperatorExpressionImpl.setReceiver(irExpression);
            irDynamicOperatorExpressionImpl.getArguments().add(irExpression2);
            irIfThenElseImpl2 = irDynamicOperatorExpressionImpl;
        } else {
            int i2 = WhenMappings.$EnumSwitchMapping$1[binaryLogicExpression.getKind().ordinal()];
            if (i2 == 1) {
                irIfThenElseImpl = new IrIfThenElseImpl(i, endOffset, getIrBuiltIns().getBooleanType(), IrStatementOrigin.ANDAND.INSTANCE);
                irIfThenElseImpl.getBranches().add(new IrBranchImpl(irExpression, irExpression2));
                Fir2IrVisitor fir2IrVisitor2 = this;
                irIfThenElseImpl.getBranches().add(PrimitivesKt.elseBranch(fir2IrVisitor2, PrimitivesKt.constFalse(fir2IrVisitor2, irExpression2.getStartOffset(), irExpression2.getEndOffset())));
            } else if (i2 == 2) {
                irIfThenElseImpl = new IrIfThenElseImpl(i, endOffset, getIrBuiltIns().getBooleanType(), IrStatementOrigin.OROR.INSTANCE);
                Fir2IrVisitor fir2IrVisitor3 = this;
                irIfThenElseImpl.getBranches().add(new IrBranchImpl(irExpression, PrimitivesKt.constTrue(fir2IrVisitor3, irExpression.getStartOffset(), irExpression.getEndOffset())));
                irIfThenElseImpl.getBranches().add(PrimitivesKt.elseBranch(fir2IrVisitor3, irExpression2));
            } else {
                throw new NoWhenBranchMatchedException();
            }
            irIfThenElseImpl2 = irIfThenElseImpl;
        }
        return irIfThenElseImpl2;
    }

    private final IrExpression convertToIrCall(FirFunctionCall functionCall) {
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(functionCall.getCalleeReference(), false, 1, null);
        if (Intrinsics.areEqual(resolvedNamedFunctionSymbol$default != null ? resolvedNamedFunctionSymbol$default.getOrigin() : null, FirDeclarationOrigin.DynamicScope.INSTANCE) && Intrinsics.areEqual(functionCall.getCalleeReference().getName(), OperatorNameConventions.SET)) {
            KtSourceElement source = functionCall.getCalleeReference().getSource();
            if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE)) {
                return convertToIrArrayAccessDynamicCall(functionCall);
            }
        }
        return convertToIrCall(functionCall, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitElvisExpression(FirElvisExpression elvisExpression, Object data) {
        int startOffset;
        Integer numStartOffsetSkippingComments;
        IrGetValue irGetValueImplicitCastOrExpression$fir2ir;
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setSource(elvisExpression.getSource());
        firPropertyBuilder.setModuleData(FirModuleDataKt.getModuleData(getSession()));
        firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firPropertyBuilder.setReturnTypeRef(elvisExpression.getLhs().getTypeRef());
        Name nameSpecial = Name.special("<elvis>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        firPropertyBuilder.setName(nameSpecial);
        firPropertyBuilder.setInitializer(elvisExpression.getLhs());
        firPropertyBuilder.setSymbol(new FirPropertySymbol(firPropertyBuilder.getName()));
        firPropertyBuilder.setVar(false);
        firPropertyBuilder.setLocal(true);
        firPropertyBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
        FirProperty firPropertyBuild = firPropertyBuilder.mo6809build();
        Object objAccept = firPropertyBuild.accept(this, null);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
        IrVariable irVariable = (IrVariable) objAccept;
        KtSourceElement source = elvisExpression.getSource();
        KtSourceElement ktSourceElement = source;
        int endOffset = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            startOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            if (source != null) {
                endOffset = source.getEndOffset();
            }
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firPropertyBuild.getReturnTypeRef());
        ConeKotlinType coneKotlinTypeWithNullability$default = TypeUtilsKt.withNullability$default(coneType, ConeNullability.NOT_NULL, TypeComponentsKt.getTypeContext(getSession()), null, false, 12, null);
        IrBranch[] irBranchArr = new IrBranch[2];
        irBranchArr[0] = new IrBranchImpl(startOffset, endOffset, PrimitivesKt.primitiveOp2(startOffset, endOffset, getIrBuiltIns().getEqeqSymbol(), getIrBuiltIns().getBooleanType(), IrStatementOrigin.EQEQ.INSTANCE, visitElvisExpression$lambda$89$irGetLhsValue(startOffset, endOffset, irVariable), IrConstImpl.INSTANCE.constNull(startOffset, endOffset, getIrBuiltIns().getNothingNType())), insertImplicitCast(convertToIrExpression$fir2ir$default(this, elvisExpression.getRhs(), false, 2, null), elvisExpression, elvisExpression.getRhs().getTypeRef(), elvisExpression.getTypeRef()));
        IrConstImpl<Boolean> irConstImplM7206boolean = IrConstImpl.INSTANCE.m7206boolean(startOffset, endOffset, getIrBuiltIns().getBooleanType(), true);
        if (Intrinsics.areEqual(coneKotlinTypeWithNullability$default, coneType)) {
            irGetValueImplicitCastOrExpression$fir2ir = visitElvisExpression$lambda$89$irGetLhsValue(startOffset, endOffset, irVariable);
        } else {
            irGetValueImplicitCastOrExpression$fir2ir = Fir2IrImplicitCastInserter.INSTANCE.implicitCastOrExpression$fir2ir(visitElvisExpression$lambda$89$irGetLhsValue(startOffset, endOffset, irVariable), toIrType(CopyUtilsKt.resolvedTypeFromPrototype$default(firPropertyBuild.getReturnTypeRef(), coneKotlinTypeWithNullability$default, null, 2, null)));
        }
        irBranchArr[1] = new IrElseBranchImpl(irConstImplM7206boolean, irGetValueImplicitCastOrExpression$fir2ir);
        return generateWhen(startOffset, endOffset, IrStatementOrigin.ELVIS.INSTANCE, irVariable, CollectionsKt.listOf((Object[]) irBranchArr), toIrType(elvisExpression.getTypeRef()));
    }
}
