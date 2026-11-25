package org.jetbrains.kotlin.fir.backend.generators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.ConversionTypeOrigin;
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
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.FirSamResolver;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.calls.ArgumentsKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirFakeArgumentForCallableReference;
import org.jetbrains.kotlin.fir.resolve.calls.ResolvedCallArgument;
import org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: AdapterGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0099\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001R\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J \u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020a2\u0006\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020dH\u0002J(\u0010f\u001a\u00020a2\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020h2\u0006\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020mH\u0002JD\u0010n\u001a\u00020a2\u0006\u0010o\u001a\u00020p2\u0006\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020t2\u0006\u0010j\u001a\u00020k2\u0006\u0010u\u001a\u00020v2\b\u0010w\u001a\u0004\u0018\u00010a2\b\u0010x\u001a\u0004\u0018\u00010aH\u0002J0\u0010y\u001a\u00020z2\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020h2\u0006\u0010{\u001a\u00020v2\u0006\u0010|\u001a\u00020}2\u0006\u0010l\u001a\u00020mH\u0002JL\u0010~\u001a\u00020z2\u0006\u0010o\u001a\u00020p2\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020h2\u0006\u0010q\u001a\u00020r2\u0006\u0010\u007f\u001a\u00020k2\u0006\u0010{\u001a\u00020v2\b\u0010w\u001a\u0004\u0018\u00010a2\b\u0010x\u001a\u0004\u0018\u00010aH\u0002J7\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0006\u0010j\u001a\u00020k2\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0007\u0010\u0084\u0001\u001a\u00020h2\u0006\u0010{\u001a\u00020}2\b\u0010\u0085\u0001\u001a\u00030\u0086\u0001H\u0002J\u001e\u0010\u0087\u0001\u001a\u0004\u0018\u00010m2\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0007\u0010b\u001a\u00030\u008a\u0001H\u0002J2\u0010\u008b\u0001\u001a\u00020a2\u0006\u0010o\u001a\u00020p2\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010a2\u0006\u0010s\u001a\u00020t2\u0006\u0010{\u001a\u00020vH\u0000¢\u0006\u0003\b\u008d\u0001J0\u0010\u008e\u0001\u001a\u00020z2\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020h2\f\u0010\u008f\u0001\u001a\u0007\u0012\u0002\b\u00030\u0090\u00012\u0007\u0010\u0091\u0001\u001a\u00020}H\u0002J'\u0010\u0092\u0001\u001a\u00020a2\u0007\u0010\u0093\u0001\u001a\u00020p2\f\u0010\u008f\u0001\u001a\u0007\u0012\u0002\b\u00030\u0090\u00012\u0007\u0010\u0091\u0001\u001a\u00020}J\u001a\u0010\u0094\u0001\u001a\u0004\u0018\u00010d2\u0007\u0010\u0095\u0001\u001a\u00020dH\u0000¢\u0006\u0003\b\u0096\u0001J\u0012\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010o\u001a\u00020pH\u0002J\u001b\u0010\u0099\u0001\u001a\u00030\u0098\u00012\u0006\u0010{\u001a\u00020v2\u0007\u0010\u009a\u0001\u001a\u00020kH\u0002J\u001d\u0010\u009b\u0001\u001a\u00030\u0098\u00012\u0007\u0010b\u001a\u00030\u008a\u00012\b\u0010\u009c\u0001\u001a\u00030\u009d\u0001H\u0002J\u001b\u0010\u009e\u0001\u001a\u00030\u0098\u00012\u0006\u0010{\u001a\u00020v2\u0007\u0010\u009a\u0001\u001a\u00020kH\u0002J)\u0010\u009f\u0001\u001a\u00030\u0098\u00012\u0006\u0010o\u001a\u00020p2\u0006\u0010{\u001a\u00020v2\u0007\u0010\u009a\u0001\u001a\u00020kH\u0000¢\u0006\u0003\b \u0001J:\u0010¡\u0001\u001a\u00020a*\u00030¢\u00012\u0007\u0010b\u001a\u00030\u008a\u00012!\u0010£\u0001\u001a\u001c\u0012\u0004\u0012\u00020a\u0012\u0005\u0012\u00030\u008a\u0001\u0012\u0004\u0012\u00020a0¤\u0001¢\u0006\u0003\b¥\u0001H\u0002J>\u0010¦\u0001\u001a\u00020a*\u00020a2\u0007\u0010b\u001a\u00030\u008a\u00012\n\u0010\u009c\u0001\u001a\u0005\u0018\u00010\u009d\u00012\b\u0010§\u0001\u001a\u00030¨\u00012\n\b\u0002\u0010©\u0001\u001a\u00030\u0098\u0001H\u0000¢\u0006\u0003\bª\u0001J%\u0010«\u0001\u001a\u00020a*\u00020a2\u0007\u0010b\u001a\u00030\u008a\u00012\u0007\u0010\u0095\u0001\u001a\u00020dH\u0000¢\u0006\u0003\b¬\u0001J$\u0010\u00ad\u0001\u001a\u0004\u0018\u00010a*\u00020p2\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010a2\b\u0010®\u0001\u001a\u00030\u0098\u0001H\u0002J\u0016\u0010¯\u0001\u001a\u00020}*\u00020v2\u0007\u0010\u0084\u0001\u001a\u00020hH\u0002J\u000e\u0010°\u0001\u001a\u00030\u0098\u0001*\u00020dH\u0002J\u000f\u0010±\u0001\u001a\u0004\u0018\u00010d*\u00020dH\u0002J\u0011\u0010±\u0001\u001a\u0005\u0018\u00010²\u0001*\u00030²\u0001H\u0002J\u001f\u0010³\u0001\u001a\u00020d*\u00030´\u00012\u000f\u0010µ\u0001\u001a\n\u0012\u0005\u0012\u00030·\u00010¶\u0001H\u0002J\u001f\u0010¸\u0001\u001a\u00030¹\u0001*\u00030º\u00012\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020hH\u0002J\u0019\u0010»\u0001\u001a\u00020}*\u00020d2\n\b\u0002\u0010¼\u0001\u001a\u00030½\u0001H\u0002R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001a\u001a\u00020\u001bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001e\u001a\u00020\u001fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020#X\u0096\u0005¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X\u0096\u0005¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0012\u0010*\u001a\u00020+X\u0096\u0005¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u00109R\u0018\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;X\u0096\u0005¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0012\u0010?\u001a\u00020@X\u0096\u0005¢\u0006\u0006\u001a\u0004\bA\u0010BR\u000e\u0010C\u001a\u00020DX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010E\u001a\u00020FX\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0012\u0010I\u001a\u00020JX\u0096\u0005¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0012\u0010M\u001a\u00020NX\u0096\u0005¢\u0006\u0006\u001a\u0004\bO\u0010PR\u0010\u0010Q\u001a\u00020RX\u0082\u0004¢\u0006\u0004\n\u0002\u0010SR\u0012\u0010T\u001a\u00020UX\u0096\u0005¢\u0006\u0006\u001a\u0004\bV\u0010WR\u0012\u0010X\u001a\u00020YX\u0096\u0005¢\u0006\u0006\u001a\u0004\bZ\u0010[R\u0012\u0010\\\u001a\u00020]X\u0096\u0005¢\u0006\u0006\u001a\u0004\b^\u0010_¨\u0006¾\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/AdapterGenerator;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "samResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "starProjectionApproximator", "org/jetbrains/kotlin/fir/backend/generators/AdapterGenerator$starProjectionApproximator$1", "Lorg/jetbrains/kotlin/fir/backend/generators/AdapterGenerator$starProjectionApproximator$1;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "castArgumentToFunctionalInterfaceForSamType", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "argument", "argumentConeType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "samType", "createAdapteeCallForArgument", "startOffset", "", "endOffset", "adapterFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "invokeSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "createAdapteeCallForCallableReference", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "firAdaptee", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "adapteeSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "adaptedType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "boundDispatchReceiver", "boundExtensionReceiver", "createAdapterFunctionForArgument", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", ModuleXmlParser.TYPE, "argumentType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "createAdapterFunctionForCallableReference", "adaptee", "createAdapterParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "name", "Lorg/jetbrains/kotlin/name/Name;", "index", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "findInvokeSymbol", "expectedFunctionalType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "generateAdaptedCallableReference", "explicitReceiverExpression", "generateAdaptedCallableReference$fir2ir", "generateFunInterfaceConstructorAdapter", "callableSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "irReferenceType", "generateFunInterfaceConstructorReference", "callableReference", "getFunctionTypeForPossibleSamType", "parameterType", "getFunctionTypeForPossibleSamType$fir2ir", "hasVarargOrDefaultArguments", "", "needCoercionToUnit", "function", "needSamConversion", "parameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "needSuspendConversion", "needToGenerateAdaptedCallableReference", "needToGenerateAdaptedCallableReference$fir2ir", "applyConversionOnVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "conversion", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "applySamConversionIfNeeded", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "shouldUnwrapVarargType", "applySamConversionIfNeeded$fir2ir", "applySuspendConversionIfNeeded", "applySuspendConversionIfNeeded$fir2ir", "findBoundReceiver", "isDispatch", "getArgumentTypeAt", "isTypeParameterBased", "removeExternalProjections", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "starProjectionTypeRepresentation", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "containingParameterSet", "", "Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "toIrGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "toIrType", "typeOrigin", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AdapterGenerator implements Fir2IrComponents {
    private final Fir2IrComponents components;
    private final Fir2IrConversionScope conversionScope;
    private final FirSamResolver samResolver;
    private final AdapterGenerator$starProjectionApproximator$1 starProjectionApproximator;

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

    /* JADX WARN: Type inference failed for: r9v1, types: [org.jetbrains.kotlin.fir.backend.generators.AdapterGenerator$starProjectionApproximator$1] */
    public AdapterGenerator(Fir2IrComponents components, Fir2IrConversionScope conversionScope) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        this.components = components;
        this.conversionScope = conversionScope;
        this.samResolver = new FirSamResolver(getSession(), getScopeSession(), null, 4, null);
        final ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
        this.starProjectionApproximator = new AbstractConeSubstitutor(typeContext) { // from class: org.jetbrains.kotlin.fir.backend.generators.AdapterGenerator$starProjectionApproximator$1
            {
                super(typeContext);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
            public ConeKotlinType substituteType(ConeKotlinType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                if (type instanceof ConeClassLikeType) {
                    for (ConeTypeProjection coneTypeProjection : type.getTypeArguments()) {
                        if (Intrinsics.areEqual(coneTypeProjection, ConeStarProjection.INSTANCE)) {
                            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), this.this$0.getSession());
                            FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
                            FirClassLikeDeclaration firClassLikeDeclaration2 = firClassLikeDeclaration instanceof FirTypeParameterRefsOwner ? firClassLikeDeclaration : null;
                            if (firClassLikeDeclaration2 == null) {
                                return null;
                            }
                            List<FirTypeParameterRef> typeParameters = firClassLikeDeclaration2.getTypeParameters();
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
                            Iterator<T> it = typeParameters.iterator();
                            while (it.hasNext()) {
                                arrayList.add((FirTypeParameter) ((FirTypeParameterRef) it.next()).getSymbol().getFir());
                            }
                            ArrayList arrayList2 = arrayList;
                            if (arrayList2.size() != type.getTypeArguments().length) {
                                return null;
                            }
                            List<Pair> listZip = CollectionsKt.zip(arrayList2, type.getTypeArguments());
                            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
                            for (Pair pair : listZip) {
                                FirTypeParameter firTypeParameter = (FirTypeParameter) pair.component1();
                                ConeKotlinType coneType = (ConeTypeProjection) pair.component2();
                                if (Intrinsics.areEqual(coneType, ConeStarProjection.INSTANCE)) {
                                    coneType = FirTypeUtilsKt.getConeType((FirTypeRef) CollectionsKt.first((List) firTypeParameter.getBounds()));
                                }
                                arrayList3.add(coneType);
                            }
                            return ConeTypeUtilsKt.withArguments(coneClassLikeType, (ConeTypeProjection[]) arrayList3.toArray(new ConeTypeProjection[0]));
                        }
                    }
                }
                return null;
            }
        };
    }

    static /* synthetic */ IrType toIrType$default(AdapterGenerator adapterGenerator, ConeKotlinType coneKotlinType, ConversionTypeOrigin conversionTypeOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return adapterGenerator.toIrType(coneKotlinType, conversionTypeOrigin);
    }

    private final IrType toIrType(ConeKotlinType coneKotlinType, ConversionTypeOrigin conversionTypeOrigin) {
        return Fir2IrTypeConverter.toIrType$default(getTypeConverter(), coneKotlinType, conversionTypeOrigin, null, false, false, false, 30, null);
    }

    public final boolean needToGenerateAdaptedCallableReference$fir2ir(FirCallableReferenceAccess callableReferenceAccess, IrSimpleType type, IrFunction function) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(function, "function");
        return needSuspendConversion(type, function) || needCoercionToUnit(type, function) || hasVarargOrDefaultArguments(callableReferenceAccess);
    }

    private final boolean needSuspendConversion(IrSimpleType type, IrFunction function) {
        return IrTypeUtilsKt.isSuspendFunction(type) && !AdditionalIrUtilsKt.isSuspend(function);
    }

    private final boolean needCoercionToUnit(IrSimpleType type, IrFunction function) {
        IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.last((List) type.getArguments()));
        IrType returnType = function.getReturnType();
        return (typeOrNull == null || !IrTypePredicatesKt.isUnit(typeOrNull) || IrTypePredicatesKt.isUnit(returnType) || IrTypeUtilsKt.isTypeParameter(returnType)) ? false : true;
    }

    private final boolean hasVarargOrDefaultArguments(FirCallableReferenceAccess callableReferenceAccess) {
        FirNamedReference calleeReference = callableReferenceAccess.getCalleeReference();
        FirResolvedCallableReference firResolvedCallableReference = calleeReference instanceof FirResolvedCallableReference ? (FirResolvedCallableReference) calleeReference : null;
        if (firResolvedCallableReference == null) {
            return false;
        }
        Map<FirValueParameter, ResolvedCallArgument> mappedArguments = firResolvedCallableReference.getMappedArguments();
        if (mappedArguments.isEmpty()) {
            return false;
        }
        Iterator<Map.Entry<FirValueParameter, ResolvedCallArgument>> it = mappedArguments.entrySet().iterator();
        while (it.hasNext()) {
            ResolvedCallArgument value = it.next().getValue();
            if ((value instanceof ResolvedCallArgument.VarargArgument) || (value instanceof ResolvedCallArgument.DefaultArgument)) {
                return true;
            }
        }
        return false;
    }

    public final IrExpression generateAdaptedCallableReference$fir2ir(FirCallableReferenceAccess callableReferenceAccess, IrExpression explicitReceiverExpression, IrFunctionSymbol adapteeSymbol, IrSimpleType type) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        FirBasedSymbol<?> resolvedSymbol;
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        Intrinsics.checkNotNullParameter(adapteeSymbol, "adapteeSymbol");
        Intrinsics.checkNotNullParameter(type, "type");
        FirResolvedNamedReference resolvedCallableReference = FirExpressionUtilKt.toResolvedCallableReference(callableReferenceAccess);
        FirDeclaration fir = (resolvedCallableReference == null || (resolvedSymbol = resolvedCallableReference.getResolvedSymbol()) == null) ? null : resolvedSymbol.getFir();
        FirFunction firFunction = fir instanceof FirFunction ? (FirFunction) fir : null;
        IrFunction owner = adapteeSymbol.getOwner();
        final IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.last((List) type.getArguments()));
        FirCallableReferenceAccess firCallableReferenceAccess = callableReferenceAccess;
        FirCallableReferenceAccess firCallableReferenceAccess2 = firCallableReferenceAccess;
        FirReference calleeReference = firCallableReferenceAccess.getCalleeReference();
        if (ConversionUtilsKt.isCompiledElement(UtilsKt.getPsi(firCallableReferenceAccess2))) {
            endOffset = -1;
            i = -1;
        } else {
            KtSourceElement source = calleeReference.getSource();
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                KtSourceElement source2 = calleeReference.getSource();
                startOffset = source2 != null ? source2.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            KtSourceElement source3 = firCallableReferenceAccess2.getSource();
            i = startOffset;
            endOffset = source3 != null ? source3.getEndOffset() : -1;
        }
        IrExpression irExpressionFindBoundReceiver = findBoundReceiver(callableReferenceAccess, explicitReceiverExpression, true);
        IrExpression irExpressionFindBoundReceiver2 = findBoundReceiver(callableReferenceAccess, explicitReceiverExpression, false);
        Intrinsics.checkNotNull(firFunction);
        final int i2 = endOffset;
        final int i3 = i;
        final IrSimpleFunction irSimpleFunctionCreateAdapterFunctionForCallableReference = createAdapterFunctionForCallableReference(callableReferenceAccess, i, endOffset, firFunction, owner, type, irExpressionFindBoundReceiver, irExpressionFindBoundReceiver2);
        final IrExpression irExpressionCreateAdapteeCallForCallableReference = createAdapteeCallForCallableReference(callableReferenceAccess, firFunction, adapteeSymbol, irSimpleFunctionCreateAdapterFunctionForCallableReference, type, irExpressionFindBoundReceiver, irExpressionFindBoundReceiver2);
        irSimpleFunctionCreateAdapterFunctionForCallableReference.setBody(IrFactoryHelpersKt.createBlockBody(getIrFactory(), i3, i2, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.AdapterGenerator$generateAdaptedCallableReference$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                IrType irType = typeOrNull;
                if (irType != null && IrTypePredicatesKt.isUnit(irType)) {
                    createBlockBody.getStatements().add(irExpressionCreateAdapteeCallForCallableReference);
                } else {
                    createBlockBody.getStatements().add(new IrReturnImpl(i3, i2, this.getIrBuiltIns().getNothingType(), irSimpleFunctionCreateAdapterFunctionForCallableReference.getSymbol(), irExpressionCreateAdapteeCallForCallableReference));
                }
            }
        }));
        if (!irSimpleFunctionCreateAdapterFunctionForCallableReference.getTypeParameters().isEmpty()) {
            StringBuilder sbAppend = new StringBuilder("Internal error: function adapter ").append(irSimpleFunctionCreateAdapterFunctionForCallableReference.getSymbol()).append(" has unexpected type parameters: ");
            List<IrTypeParameter> typeParameters = irSimpleFunctionCreateAdapterFunctionForCallableReference.getTypeParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(((IrTypeParameter) it.next()).getSymbol());
            }
            throw new IllegalArgumentException(sbAppend.append(arrayList).append("\nThey should already be used to determine exact return type and value parameters types").toString().toString());
        }
        IrSimpleType irSimpleType = type;
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(i3, i2, irSimpleType, irSimpleFunctionCreateAdapterFunctionForCallableReference.getSymbol(), 0, irSimpleFunctionCreateAdapterFunctionForCallableReference.getValueParameters().size(), null, IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE);
        irFunctionReferenceImpl.setExtensionReceiver(irExpressionFindBoundReceiver == null ? irExpressionFindBoundReceiver2 : irExpressionFindBoundReceiver);
        irFunctionReferenceImpl.setReflectionTarget(owner.getSymbol());
        IrBlockImpl irBlockImpl = new IrBlockImpl(i3, i2, irSimpleType, IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE);
        irBlockImpl.getStatements().add(irSimpleFunctionCreateAdapterFunctionForCallableReference);
        irBlockImpl.getStatements().add(irFunctionReferenceImpl);
        return irBlockImpl;
    }

    private final IrExpression findBoundReceiver(FirCallableReferenceAccess firCallableReferenceAccess, IrExpression irExpression, boolean z) {
        if ((z ? firCallableReferenceAccess.getDispatchReceiver() : firCallableReferenceAccess.getExtensionReceiver()) instanceof FirNoReceiverExpression) {
            return null;
        }
        return getCallGenerator().findIrReceiver$fir2ir(firCallableReferenceAccess, irExpression, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrSimpleFunction createAdapterFunctionForCallableReference(FirCallableReferenceAccess callableReferenceAccess, int startOffset, int endOffset, FirFunction firAdaptee, IrFunction adaptee, IrSimpleType type, IrExpression boundDispatchReceiver, IrExpression boundExtensionReceiver) {
        IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.last((List) type.getArguments()));
        Intrinsics.checkNotNull(typeOrNull);
        int i = 1;
        List listDropLast = CollectionsKt.dropLast(type.getArguments(), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            IrType typeOrNull2 = IrTypesKt.getTypeOrNull((IrTypeArgument) it.next());
            Intrinsics.checkNotNull(typeOrNull2);
            arrayList.add(typeOrNull2);
        }
        ArrayList arrayList2 = arrayList;
        Intrinsics.checkNotNull(firAdaptee, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration");
        FirFunction firFunction = firAdaptee;
        IrFactory irFactory = getIrFactory();
        IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE adapter_for_callable_reference = IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE.INSTANCE;
        Name name2 = adaptee.getName();
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        int i2 = 0;
        IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, startOffset, endOffset, adapter_for_callable_reference, name2, LOCAL, firFunction.getStatus().isInline(), false, typeOrNull, Modality.FINAL, new IrSimpleFunctionSymbolImpl(null, i, 0 == true ? 1 : 0), false, firFunction.getStatus().isSuspend() || IrTypeUtilsKt.isSuspendFunction(type), firFunction.getStatus().isOperator(), firFunction.getStatus().isInfix(), false, null, false, 98304, null);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
        getSymbolTable().enterScope(irSimpleFunction);
        irSimpleFunctionCreateSimpleFunction$default.setDispatchReceiverParameter(null);
        IrExpression irExpression = boundDispatchReceiver == null ? boundExtensionReceiver : boundDispatchReceiver;
        if (irExpression == null) {
            irSimpleFunctionCreateSimpleFunction$default.setExtensionReceiverParameter(null);
        } else {
            if (boundDispatchReceiver != null && boundExtensionReceiver != null) {
                throw new IllegalStateException(("Bound callable references can't have both receivers: " + UtilsKt.render(callableReferenceAccess)).toString());
            }
            Name nameIdentifier = Name.identifier("receiver");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irSimpleFunctionCreateSimpleFunction$default.setExtensionReceiverParameter(createAdapterParameter(irSimpleFunctionCreateSimpleFunction$default, nameIdentifier, -1, irExpression.getType(), IrDeclarationOrigin.ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE.INSTANCE));
        }
        List<IrValueParameter> valueParameters = irSimpleFunctionCreateSimpleFunction$default.getValueParameters();
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it2 = arrayList3.iterator();
        while (true) {
            int i3 = i2;
            if (!it2.hasNext()) {
                irSimpleFunctionCreateSimpleFunction$default.setValueParameters(CollectionsKt.plus((Collection) valueParameters, (Iterable) arrayList4));
                getSymbolTable().leaveScope(irSimpleFunction);
                IrDeclarationParent irDeclarationParentParent = this.conversionScope.parent();
                Intrinsics.checkNotNull(irDeclarationParentParent);
                irSimpleFunctionCreateSimpleFunction$default.setParent(irDeclarationParentParent);
                return irSimpleFunctionCreateSimpleFunction$default;
            }
            Object next = it2.next();
            i2 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Name nameIdentifier2 = Name.identifier("p" + i3);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            arrayList4.add(createAdapterParameter(irSimpleFunctionCreateSimpleFunction$default, nameIdentifier2, i3, (IrType) next, IrDeclarationOrigin.ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE.INSTANCE));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrValueParameter createAdapterParameter(IrFunction adapterFunction, Name name2, int index, IrType type, IrDeclarationOrigin origin) {
        IrValueParameter irValueParameterCreateValueParameter = getIrFactory().createValueParameter(adapterFunction.getStartOffset(), adapterFunction.getEndOffset(), origin, name2, type, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, 0 == true ? 1 : 0), index, (IrType) null, false, false, false);
        irValueParameterCreateValueParameter.setParent(adapterFunction);
        return irValueParameterCreateValueParameter;
    }

    private final IrGetValue toIrGetValue(IrValueDeclaration irValueDeclaration, int i, int i2) {
        return new IrGetValueImpl(i, i2, irValueDeclaration.getType(), irValueDeclaration.getSymbol(), null, 16, null);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00e6  */
    private final IrExpression createAdapteeCallForCallableReference(FirCallableReferenceAccess callableReferenceAccess, FirFunction firAdaptee, IrFunctionSymbol adapteeSymbol, IrFunction adapterFunction, IrSimpleType adaptedType, IrExpression boundDispatchReceiver, IrExpression boundExtensionReceiver) {
        IrCallImpl irCallImpl;
        boolean z;
        IrType varargElementType;
        IrVarargImpl irVarargImpl;
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag;
        FirRegularClassSymbol firRegularClassSymbol;
        IrFunction owner = adapteeSymbol.getOwner();
        int startOffset = owner.getStartOffset();
        int endOffset = owner.getEndOffset();
        IrType returnType = owner.getReturnType();
        if (adapteeSymbol instanceof IrConstructorSymbol) {
            irCallImpl = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, startOffset, endOffset, returnType, (IrConstructorSymbol) adapteeSymbol, null, 16, null);
        } else if (adapteeSymbol instanceof IrSimpleFunctionSymbol) {
            irCallImpl = new IrCallImpl(startOffset, endOffset, returnType, (IrSimpleFunctionSymbol) adapteeSymbol, callableReferenceAccess.getTypeArguments().size(), owner.getValueParameters().size(), null, null);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        Ref.IntRef intRef = new Ref.IntRef();
        Ref.IntRef intRef2 = new Ref.IntRef();
        if (boundDispatchReceiver != null || boundExtensionReceiver != null) {
            z = true;
            IrValueParameter extensionReceiverParameter = adapterFunction.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            IrGetValueImpl irGetValueImpl = new IrGetValueImpl(startOffset, endOffset, extensionReceiverParameter.getSymbol(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE);
            if (boundDispatchReceiver != null) {
                irCallImpl.setDispatchReceiver(irGetValueImpl);
            } else {
                irCallImpl.setExtensionReceiver(irGetValueImpl);
            }
        } else if (!(callableReferenceAccess.getExplicitReceiver() instanceof FirResolvedQualifier) || ((firAdaptee instanceof FirConstructor) && ((coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firAdaptee)) == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, getSession())) == null || !firRegularClassSymbol.getRawStatus().isInner()))) {
            z = true;
        } else {
            FirFunction firFunction = firAdaptee instanceof FirMemberDeclaration ? firAdaptee : null;
            if (firFunction == null || !firFunction.getStatus().isStatic()) {
                IrValueParameter irValueParameter = adapterFunction.getValueParameters().get(0);
                z = true;
                IrGetValueImpl irGetValueImpl2 = new IrGetValueImpl(startOffset, endOffset, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null);
                if (owner.getExtensionReceiverParameter() != null) {
                    irCallImpl.setExtensionReceiver(irGetValueImpl2);
                } else {
                    irCallImpl.setDispatchReceiver(irGetValueImpl2);
                }
                intRef2.element++;
            } else {
                z = true;
            }
        }
        FirNamedReference calleeReference = callableReferenceAccess.getCalleeReference();
        FirResolvedCallableReference firResolvedCallableReference = calleeReference instanceof FirResolvedCallableReference ? (FirResolvedCallableReference) calleeReference : null;
        Map<FirValueParameter, ResolvedCallArgument> mappedArguments = firResolvedCallableReference != null ? firResolvedCallableReference.getMappedArguments() : null;
        int i = 0;
        for (Object obj : CollectionsKt.zip(owner.getValueParameters(), firAdaptee.getValueParameters())) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pair pair = (Pair) obj;
            IrValueParameter irValueParameter2 = (IrValueParameter) pair.component1();
            ResolvedCallArgument resolvedCallArgument = mappedArguments != null ? mappedArguments.get((FirValueParameter) pair.component2()) : null;
            if (resolvedCallArgument instanceof ResolvedCallArgument.VarargArgument) {
                ResolvedCallArgument.VarargArgument varargArgument = (ResolvedCallArgument.VarargArgument) resolvedCallArgument;
                if (varargArgument.getArguments().isEmpty()) {
                    irVarargImpl = null;
                } else {
                    IrSimpleTypeImpl type = irValueParameter2.getType();
                    IrType varargElementType2 = irValueParameter2.getVarargElementType();
                    if (varargElementType2 != null && IrTypeUtilsKt.isTypeParameter(varargElementType2) == z && (type instanceof IrSimpleType) && !IrTypeUtilsKt.isPrimitiveArray(type)) {
                        varargElementType = getArgumentTypeAt(adaptedType, i);
                        IrSimpleType irSimpleType = (IrSimpleType) type;
                        type = new IrSimpleTypeImpl(irSimpleType.getClassifier(), irSimpleType.getNullability(), (List<? extends IrTypeArgument>) CollectionsKt.listOf(IrSimpleTypeImplKt.makeTypeProjection(varargElementType, Variance.OUT_VARIANCE)), type.getAnnotations(), irSimpleType.getAbbreviation());
                    } else {
                        varargElementType = irValueParameter2.getVarargElementType();
                        Intrinsics.checkNotNull(varargElementType);
                    }
                    IrVarargImpl irVarargImpl2 = new IrVarargImpl(startOffset, endOffset, type, varargElementType);
                    Iterator<FirExpression> it = varargArgument.getArguments().iterator();
                    while (it.hasNext()) {
                        IrExpressionsKt.addElement(irVarargImpl2, createAdapteeCallForCallableReference$buildIrGetValueArgument(intRef, this, adapterFunction, intRef2, startOffset, endOffset, it.next()));
                    }
                    irVarargImpl = irVarargImpl2;
                }
                irCallImpl.putValueArgument(i, irVarargImpl);
            } else if (Intrinsics.areEqual(resolvedCallArgument, ResolvedCallArgument.DefaultArgument.INSTANCE)) {
                irCallImpl.putValueArgument(i, null);
            } else if (resolvedCallArgument instanceof ResolvedCallArgument.SimpleArgument) {
                IrGetValue irGetValueCreateAdapteeCallForCallableReference$buildIrGetValueArgument = createAdapteeCallForCallableReference$buildIrGetValueArgument(intRef, this, adapterFunction, intRef2, startOffset, endOffset, ((ResolvedCallArgument.SimpleArgument) resolvedCallArgument).getCallArgument());
                if (AdditionalIrUtilsKt.isVararg(irValueParameter2)) {
                    IrType type2 = irValueParameter2.getType();
                    IrType varargElementType3 = irValueParameter2.getVarargElementType();
                    Intrinsics.checkNotNull(varargElementType3);
                    irCallImpl.putValueArgument(i, new IrVarargImpl(startOffset, endOffset, type2, varargElementType3, CollectionsKt.listOf(new IrSpreadElementImpl(startOffset, endOffset, irGetValueCreateAdapteeCallForCallableReference$buildIrGetValueArgument))));
                } else {
                    irCallImpl.putValueArgument(i, irGetValueCreateAdapteeCallForCallableReference$buildIrGetValueArgument);
                }
            }
            i = i2;
            z = true;
        }
        return getCallGenerator().applyTypeArguments$fir2ir(irCallImpl, callableReferenceAccess);
    }

    private static final IrGetValue createAdapteeCallForCallableReference$buildIrGetValueArgument(Ref.IntRef intRef, AdapterGenerator adapterGenerator, IrFunction irFunction, Ref.IntRef intRef2, int i, int i2, FirExpression firExpression) {
        FirFakeArgumentForCallableReference firFakeArgumentForCallableReference = firExpression instanceof FirFakeArgumentForCallableReference ? (FirFakeArgumentForCallableReference) firExpression : null;
        int index = firFakeArgumentForCallableReference != null ? firFakeArgumentForCallableReference.getIndex() : intRef.element;
        intRef.element++;
        return adapterGenerator.toIrGetValue(irFunction.getValueParameters().get(index + intRef2.element), i, i2);
    }

    public static /* synthetic */ IrExpression applySamConversionIfNeeded$fir2ir$default(AdapterGenerator adapterGenerator, IrExpression irExpression, FirExpression firExpression, FirValueParameter firValueParameter, ConeSubstitutor coneSubstitutor, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return adapterGenerator.applySamConversionIfNeeded$fir2ir(irExpression, firExpression, firValueParameter, coneSubstitutor, z);
    }

    public final IrExpression applySamConversionIfNeeded$fir2ir(IrExpression irExpression, FirExpression argument, final FirValueParameter firValueParameter, final ConeSubstitutor substitutor, boolean z) {
        ConeKotlinType coneKotlinTypeArrayElementType;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        if (firValueParameter == null) {
            return irExpression;
        }
        if (irExpression instanceof IrVararg) {
            return applyConversionOnVararg((IrVararg) irExpression, argument, new Function2<IrExpression, FirExpression, IrExpression>() { // from class: org.jetbrains.kotlin.fir.backend.generators.AdapterGenerator$applySamConversionIfNeeded$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final IrExpression invoke(IrExpression applyConversionOnVararg, FirExpression firVarargArgument) {
                    Intrinsics.checkNotNullParameter(applyConversionOnVararg, "$this$applyConversionOnVararg");
                    Intrinsics.checkNotNullParameter(firVarargArgument, "firVarargArgument");
                    return this.this$0.applySamConversionIfNeeded$fir2ir(applyConversionOnVararg, firVarargArgument, firValueParameter, substitutor, true);
                }
            });
        }
        if (!needSamConversion(argument, firValueParameter)) {
            return irExpression;
        }
        ConeKotlinType coneKotlinTypeSubstituteOrSelf = substituteOrSelf(substitutor.substituteOrSelf(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef())));
        if (z && (coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(coneKotlinTypeSubstituteOrSelf)) != null) {
            coneKotlinTypeSubstituteOrSelf = coneKotlinTypeArrayElementType;
        }
        ConeKotlinType coneKotlinTypeRemoveExternalProjections = removeExternalProjections(coneKotlinTypeSubstituteOrSelf);
        if (coneKotlinTypeRemoveExternalProjections != null) {
            coneKotlinTypeSubstituteOrSelf = coneKotlinTypeRemoveExternalProjections;
        }
        IrType irType = toIrType(coneKotlinTypeSubstituteOrSelf, ConversionTypeOrigin.DEFAULT);
        return !ConversionUtilsKt.isSamType(irType) ? irExpression : new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, IrTypeOperator.SAM_CONVERSION, irType, castArgumentToFunctionalInterfaceForSamType(irExpression, FirTypeUtilsKt.getConeType(argument.getTypeRef()), coneKotlinTypeSubstituteOrSelf));
    }

    private final IrExpression castArgumentToFunctionalInterfaceForSamType(IrExpression argument, ConeKotlinType argumentConeType, ConeKotlinType samType) {
        ConeKotlinType functionTypeForPossibleSamType$fir2ir = getFunctionTypeForPossibleSamType$fir2ir(samType);
        return (functionTypeForPossibleSamType$fir2ir == null || TypeUtilsKt.isSubtypeOf$default(argumentConeType, functionTypeForPossibleSamType$fir2ir, getSession(), false, 4, null)) ? argument : IrExpressionsKt.implicitCastTo(argument, toIrType$default(this, functionTypeForPossibleSamType$fir2ir, null, 1, null));
    }

    private final ConeKotlinType removeExternalProjections(ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeSimpleKotlinType) {
            return removeExternalProjections((ConeSimpleKotlinType) coneKotlinType);
        }
        if (!(coneKotlinType instanceof ConeFlexibleType)) {
            throw new NoWhenBranchMatchedException();
        }
        ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
        ConeSimpleKotlinType coneSimpleKotlinTypeRemoveExternalProjections = removeExternalProjections(coneFlexibleType.getLowerBound());
        if (coneSimpleKotlinTypeRemoveExternalProjections == null) {
            coneSimpleKotlinTypeRemoveExternalProjections = coneFlexibleType.getLowerBound();
        }
        ConeSimpleKotlinType coneSimpleKotlinTypeRemoveExternalProjections2 = removeExternalProjections(coneFlexibleType.getUpperBound());
        if (coneSimpleKotlinTypeRemoveExternalProjections2 == null) {
            coneSimpleKotlinTypeRemoveExternalProjections2 = coneFlexibleType.getUpperBound();
        }
        return new ConeFlexibleType(coneSimpleKotlinTypeRemoveExternalProjections, coneSimpleKotlinTypeRemoveExternalProjections2);
    }

    private final ConeSimpleKotlinType removeExternalProjections(ConeSimpleKotlinType coneSimpleKotlinType) {
        ConeKotlinType type;
        SimpleTypeMarker simpleTypeMarkerUpperBoundIfFlexible;
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
        List<ConeTypeParameterLookupTag> parameters = typeContext.getParameters(typeContext.typeConstructor((SimpleTypeMarker) coneSimpleKotlinType));
        Set<ConeTypeParameterLookupTag> set = CollectionsKt.toSet(parameters);
        List<TypeArgumentMarker> arguments = typeContext.getArguments(coneSimpleKotlinType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        int i = 0;
        for (Object obj : arguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeArgumentMarker typeArgumentMarker = (TypeArgumentMarker) obj;
            ConeTypeParameterLookupTag coneTypeParameterLookupTag = (ConeTypeParameterLookupTag) CollectionsKt.getOrNull(parameters, i);
            if (coneTypeParameterLookupTag == null) {
                return null;
            }
            if (typeContext.getVariance(typeArgumentMarker) == TypeVariance.IN) {
                KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) CollectionsKt.singleOrNull((List) typeContext.getUpperBounds(coneTypeParameterLookupTag));
                if (kotlinTypeMarker != null && (simpleTypeMarkerUpperBoundIfFlexible = typeContext.upperBoundIfFlexible(kotlinTypeMarker)) != null) {
                    type = simpleTypeMarkerUpperBoundIfFlexible;
                    if (!typeContext.isNullableAny(type)) {
                    }
                }
                return null;
            }
            type = !typeContext.isStarProjection(typeArgumentMarker) ? typeContext.getType(typeArgumentMarker) : starProjectionTypeRepresentation(coneTypeParameterLookupTag.getTypeParameterSymbol(), set);
            arrayList.add(type);
            i = i2;
        }
        return (ConeSimpleKotlinType) TypeUtilsKt.withArguments(coneSimpleKotlinType, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]));
    }

    private final ConeKotlinType starProjectionTypeRepresentation(FirTypeParameterSymbol firTypeParameterSymbol, final Set<ConeTypeParameterLookupTag> set) {
        final ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
        return new AbstractConeSubstitutor(set, typeContext) { // from class: org.jetbrains.kotlin.fir.backend.generators.AdapterGenerator$starProjectionTypeRepresentation$substitutor$1
            final /* synthetic */ Set<ConeTypeParameterLookupTag> $containingParameterSet;

            @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
            public ConeKotlinType substituteType(ConeKotlinType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return null;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(typeContext);
            }

            @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
            public ConeTypeProjection substituteArgument(ConeTypeProjection projection, int index) {
                Intrinsics.checkNotNullParameter(projection, "projection");
                return ((projection instanceof ConeTypeParameterType) && this.$containingParameterSet.contains(((ConeTypeParameterType) projection).getLookupTag())) ? ConeStarProjection.INSTANCE : super.substituteArgument(projection, index);
            }
        }.substituteOrSelf(((FirResolvedTypeRef) CollectionsKt.first((List) firTypeParameterSymbol.getResolvedBounds())).getType());
    }

    private final IrExpression applyConversionOnVararg(IrVararg irVararg, FirExpression firExpression, final Function2<? super IrExpression, ? super FirExpression, ? extends IrExpression> function2) {
        if (firExpression instanceof FirVarargArgumentsExpression) {
            FirVarargArgumentsExpression firVarargArgumentsExpression = (FirVarargArgumentsExpression) firExpression;
            if (firVarargArgumentsExpression.getArguments().size() == irVararg.getElements().size()) {
                final Map map = MapsKt.toMap(CollectionsKt.zip(irVararg.getElements(), firVarargArgumentsExpression.getArguments()));
                irVararg.getElements().replaceAll(new UnaryOperator() { // from class: org.jetbrains.kotlin.fir.backend.generators.AdapterGenerator$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return AdapterGenerator.applyConversionOnVararg$lambda$17(map, function2, (IrVarargElement) obj);
                    }
                });
                return irVararg;
            }
        }
        return irVararg;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrVarargElement applyConversionOnVararg$lambda$17(Map argumentMapping, Function2 conversion, IrVarargElement irVarargElement) {
        Intrinsics.checkNotNullParameter(argumentMapping, "$argumentMapping");
        Intrinsics.checkNotNullParameter(conversion, "$conversion");
        Intrinsics.checkNotNullParameter(irVarargElement, "irVarargElement");
        if (!(irVarargElement instanceof IrExpression)) {
            return irVarargElement;
        }
        FirExpression firExpression = (FirExpression) argumentMapping.get(irVarargElement);
        if (firExpression == null) {
            throw new IllegalStateException(("Can't find the original FirExpression for " + RenderIrElementKt.render$default(irVarargElement, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return (IrVarargElement) conversion.invoke(irVarargElement, firExpression);
    }

    private final boolean needSamConversion(FirExpression argument, FirValueParameter parameter) {
        if ((argument.getTypeRef() instanceof FirResolvedTypeRef) && !AbstractTypeChecker.INSTANCE.isSubtypeOf(TypeComponentsKt.getTypeContext(getSession()).newTypeCheckerState(false, true), (KotlinTypeMarker) FirTypeUtilsKt.getConeType(argument.getTypeRef()), (KotlinTypeMarker) FirTypeUtilsKt.getConeType(parameter.getReturnTypeRef()), true)) {
            ConeKotlinType expectedType = ArgumentsKt.getExpectedType(argument, parameter);
            if (!isTypeParameterBased(expectedType) && !FunctionalTypeUtilsKt.isSomeFunctionType(expectedType, getSession())) {
                return ArgumentsKt.isFunctional(argument, getSession(), getScopeSession(), getFunctionTypeForPossibleSamType$fir2ir(FirTypeUtilsKt.getConeType(parameter.getReturnTypeRef())), ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getDefault());
            }
        }
        return false;
    }

    private final boolean isTypeParameterBased(ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeTypeParameterType) {
            return true;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return isTypeParameterBased(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal());
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            return isTypeParameterBased(((ConeFlexibleType) coneKotlinType).getLowerBound());
        }
        return false;
    }

    public final ConeKotlinType getFunctionTypeForPossibleSamType$fir2ir(ConeKotlinType parameterType) {
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        return this.samResolver.getFunctionTypeForPossibleSamType(parameterType);
    }

    public final IrExpression applySuspendConversionIfNeeded$fir2ir(IrExpression irExpression, FirExpression argument, final ConeKotlinType parameterType) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        if (((irExpression instanceof IrBlock) && Intrinsics.areEqual(((IrBlock) irExpression).getOrigin(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE)) || !FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(parameterType, getSession())) {
            return irExpression;
        }
        ConeClassLikeType coneClassLikeTypeCustomFunctionTypeToSimpleFunctionType = FunctionalTypeUtilsKt.customFunctionTypeToSimpleFunctionType(parameterType, getSession());
        if (irExpression instanceof IrVararg) {
            return applyConversionOnVararg((IrVararg) irExpression, argument, new Function2<IrExpression, FirExpression, IrExpression>() { // from class: org.jetbrains.kotlin.fir.backend.generators.AdapterGenerator$applySuspendConversionIfNeeded$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final IrExpression invoke(IrExpression applyConversionOnVararg, FirExpression firVarargArgument) {
                    Intrinsics.checkNotNullParameter(applyConversionOnVararg, "$this$applyConversionOnVararg");
                    Intrinsics.checkNotNullParameter(firVarargArgument, "firVarargArgument");
                    return this.this$0.applySuspendConversionIfNeeded$fir2ir(applyConversionOnVararg, firVarargArgument, parameterType);
                }
            });
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbolFindInvokeSymbol = findInvokeSymbol(coneClassLikeTypeCustomFunctionTypeToSimpleFunctionType, argument);
        if (irSimpleFunctionSymbolFindInvokeSymbol == null) {
            return irExpression;
        }
        IrType irType$default = toIrType$default(this, parameterType, null, 1, null);
        Intrinsics.checkNotNull(irType$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrSimpleType irSimpleType = (IrSimpleType) irType$default;
        KtSourceElement source = argument.getSource();
        KtSourceElement ktSourceElement = source;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            endOffset = -1;
            i = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            i = startOffset;
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        IrSimpleFunction irSimpleFunctionCreateAdapterFunctionForArgument = createAdapterFunctionForArgument(i, endOffset, irSimpleType, irExpression.getType(), irSimpleFunctionSymbolFindInvokeSymbol);
        IrSimpleType irSimpleType2 = irSimpleType;
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(i, endOffset, irSimpleType2, irSimpleFunctionCreateAdapterFunctionForArgument.getSymbol(), irSimpleFunctionCreateAdapterFunctionForArgument.getTypeParameters().size(), irSimpleFunctionCreateAdapterFunctionForArgument.getValueParameters().size(), null, IrStatementOrigin.SUSPEND_CONVERSION.INSTANCE);
        IrBlockImpl irBlockImpl = new IrBlockImpl(i, endOffset, irSimpleType2, IrStatementOrigin.SUSPEND_CONVERSION.INSTANCE);
        irBlockImpl.getStatements().add(irSimpleFunctionCreateAdapterFunctionForArgument);
        List<IrStatement> statements = irBlockImpl.getStatements();
        irFunctionReferenceImpl.setExtensionReceiver(irExpression);
        statements.add(irFunctionReferenceImpl);
        return irBlockImpl;
    }

    private final IrSimpleFunctionSymbol findInvokeSymbol(ConeClassLikeType expectedFunctionalType, FirExpression argument) {
        FirNamedFunctionSymbol firNamedFunctionSymbolFindContributedInvokeSymbol;
        ConeKotlinType coneKotlinTypeFindSubtypeOfBasicFunctionType = FunctionalTypeUtilsKt.findSubtypeOfBasicFunctionType(FirTypeUtilsKt.getConeType(argument.getTypeRef()), getSession(), expectedFunctionalType);
        if (coneKotlinTypeFindSubtypeOfBasicFunctionType == null) {
            return null;
        }
        if (FunctionalTypeUtilsKt.isSomeFunctionType(coneKotlinTypeFindSubtypeOfBasicFunctionType, getSession())) {
            ConeClassLikeType coneClassLikeType = coneKotlinTypeFindSubtypeOfBasicFunctionType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinTypeFindSubtypeOfBasicFunctionType : null;
            firNamedFunctionSymbolFindContributedInvokeSymbol = coneClassLikeType != null ? FunctionalTypeUtilsKt.findBaseInvokeSymbol(coneClassLikeType, getSession(), getScopeSession()) : null;
        } else {
            firNamedFunctionSymbolFindContributedInvokeSymbol = FunctionalTypeUtilsKt.findContributedInvokeSymbol(coneKotlinTypeFindSubtypeOfBasicFunctionType, getSession(), getScopeSession(), expectedFunctionalType, true);
        }
        if (firNamedFunctionSymbolFindContributedInvokeSymbol == null) {
            return null;
        }
        IrFunctionSymbol irFunctionSymbol$default = Fir2IrDeclarationStorage.getIrFunctionSymbol$default(getDeclarationStorage(), firNamedFunctionSymbolFindContributedInvokeSymbol, null, 2, null);
        if (irFunctionSymbol$default instanceof IrSimpleFunctionSymbol) {
            return (IrSimpleFunctionSymbol) irFunctionSymbol$default;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrSimpleFunction createAdapterFunctionForArgument(final int startOffset, final int endOffset, IrSimpleType type, IrType argumentType, final IrSimpleFunctionSymbol invokeSymbol) {
        final IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.last((List) type.getArguments()));
        Intrinsics.checkNotNull(typeOrNull);
        int i = 1;
        List listDropLast = CollectionsKt.dropLast(type.getArguments(), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            IrType typeOrNull2 = IrTypesKt.getTypeOrNull((IrTypeArgument) it.next());
            Intrinsics.checkNotNull(typeOrNull2);
            arrayList.add(typeOrNull2);
        }
        ArrayList arrayList2 = arrayList;
        IrFactory irFactory = getIrFactory();
        IrDeclarationOrigin.ADAPTER_FOR_SUSPEND_CONVERSION adapter_for_suspend_conversion = IrDeclarationOrigin.ADAPTER_FOR_SUSPEND_CONVERSION.INSTANCE;
        Name nameIdentifier = Name.identifier("suspendConversion");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        final IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, startOffset, endOffset, adapter_for_suspend_conversion, nameIdentifier, LOCAL, false, false, typeOrNull, Modality.FINAL, new IrSimpleFunctionSymbolImpl(null, i, 0 == true ? 1 : 0), false, true, false, false, false, null, false, 98304, null);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
        getSymbolTable().enterScope(irSimpleFunction);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionCreateSimpleFunction$default;
        Name nameIdentifier2 = Name.identifier("callee");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irSimpleFunctionCreateSimpleFunction$default.setExtensionReceiverParameter(createAdapterParameter(irSimpleFunction2, nameIdentifier2, -1, argumentType, IrDeclarationOrigin.ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION.INSTANCE));
        List<IrValueParameter> valueParameters = irSimpleFunctionCreateSimpleFunction$default.getValueParameters();
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        int i2 = 0;
        for (Object obj : arrayList3) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Name nameIdentifier3 = Name.identifier("p" + i2);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
            arrayList4.add(createAdapterParameter(irSimpleFunction2, nameIdentifier3, i2, (IrType) obj, IrDeclarationOrigin.ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION.INSTANCE));
            i2 = i3;
        }
        irSimpleFunctionCreateSimpleFunction$default.setValueParameters(CollectionsKt.plus((Collection) valueParameters, (Iterable) arrayList4));
        irSimpleFunctionCreateSimpleFunction$default.setBody(IrFactoryHelpersKt.createBlockBody(getIrFactory(), startOffset, endOffset, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.AdapterGenerator$createAdapterFunctionForArgument$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                IrExpression irExpressionCreateAdapteeCallForArgument = this.this$0.createAdapteeCallForArgument(startOffset, endOffset, irSimpleFunctionCreateSimpleFunction$default, invokeSymbol);
                if (IrTypePredicatesKt.isUnit(typeOrNull)) {
                    createBlockBody.getStatements().add(irExpressionCreateAdapteeCallForArgument);
                } else {
                    createBlockBody.getStatements().add(new IrReturnImpl(startOffset, endOffset, this.this$0.getIrBuiltIns().getNothingType(), irSimpleFunctionCreateSimpleFunction$default.getSymbol(), irExpressionCreateAdapteeCallForArgument));
                }
            }
        }));
        getSymbolTable().leaveScope(irSimpleFunction);
        IrDeclarationParent irDeclarationParentParent = this.conversionScope.parent();
        Intrinsics.checkNotNull(irDeclarationParentParent);
        irSimpleFunctionCreateSimpleFunction$default.setParent(irDeclarationParentParent);
        return irSimpleFunctionCreateSimpleFunction$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression createAdapteeCallForArgument(int startOffset, int endOffset, IrFunction adapterFunction, IrSimpleFunctionSymbol invokeSymbol) {
        IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, adapterFunction.getReturnType(), invokeSymbol, 0, adapterFunction.getValueParameters().size(), null, null, 192, null);
        IrValueParameter extensionReceiverParameter = adapterFunction.getExtensionReceiverParameter();
        Intrinsics.checkNotNull(extensionReceiverParameter);
        irCallImpl.setDispatchReceiver(toIrGetValue(extensionReceiverParameter, startOffset, endOffset));
        for (IrValueParameter irValueParameter : adapterFunction.getValueParameters()) {
            irCallImpl.putValueArgument(irValueParameter.getIndex(), toIrGetValue(irValueParameter, startOffset, endOffset));
        }
        return irCallImpl;
    }

    public final IrExpression generateFunInterfaceConstructorReference(FirCallableReferenceAccess callableReference, FirFunctionSymbol<?> callableSymbol, IrType irReferenceType) {
        int startOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(callableReference, "callableReference");
        Intrinsics.checkNotNullParameter(callableSymbol, "callableSymbol");
        Intrinsics.checkNotNullParameter(irReferenceType, "irReferenceType");
        FirCallableReferenceAccess firCallableReferenceAccess = callableReference;
        FirCallableReferenceAccess firCallableReferenceAccess2 = firCallableReferenceAccess;
        FirReference calleeReference = firCallableReferenceAccess.getCalleeReference();
        int endOffset = -1;
        if (ConversionUtilsKt.isCompiledElement(UtilsKt.getPsi(firCallableReferenceAccess2))) {
            startOffset = -1;
        } else {
            KtSourceElement source = calleeReference.getSource();
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                KtSourceElement source2 = calleeReference.getSource();
                startOffset = source2 != null ? source2.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            KtSourceElement source3 = firCallableReferenceAccess2.getSource();
            if (source3 != null) {
                endOffset = source3.getEndOffset();
            }
        }
        IrSimpleFunction irSimpleFunctionGenerateFunInterfaceConstructorAdapter = generateFunInterfaceConstructorAdapter(startOffset, endOffset, callableSymbol, irReferenceType);
        int i = endOffset;
        return new IrBlockImpl(startOffset, i, irReferenceType, IrStatementOrigin.FUN_INTERFACE_CONSTRUCTOR_REFERENCE.INSTANCE, CollectionsKt.listOf((Object[]) new IrElement[]{irSimpleFunctionGenerateFunInterfaceConstructorAdapter, new IrFunctionReferenceImpl(startOffset, i, irReferenceType, irSimpleFunctionGenerateFunInterfaceConstructorAdapter.getSymbol(), irSimpleFunctionGenerateFunInterfaceConstructorAdapter.getTypeParameters().size(), irSimpleFunctionGenerateFunInterfaceConstructorAdapter.getValueParameters().size(), irSimpleFunctionGenerateFunInterfaceConstructorAdapter.getSymbol(), IrStatementOrigin.FUN_INTERFACE_CONSTRUCTOR_REFERENCE.INSTANCE)}));
    }

    private final IrType getArgumentTypeAt(IrSimpleType irSimpleType, int i) {
        IrTypeArgument irTypeArgument = irSimpleType.getArguments().get(i);
        IrTypeProjection irTypeProjection = irTypeArgument instanceof IrTypeProjection ? (IrTypeProjection) irTypeArgument : null;
        if (irTypeProjection == null) {
            throw new AssertionError("Type projection expected at argument " + i + ": " + RenderIrElementKt.render$default(irSimpleType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        return irTypeProjection.getType();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrSimpleFunction generateFunInterfaceConstructorAdapter(int startOffset, int endOffset, FirFunctionSymbol<?> callableSymbol, IrType irReferenceType) {
        FunctionDescriptor functionDescriptor = null;
        Object[] objArr = 0;
        IrSimpleType irSimpleType = irReferenceType instanceof IrSimpleType ? (IrSimpleType) irReferenceType : null;
        int i = 1;
        if (irSimpleType == null) {
            throw new AssertionError("Class type expected: " + RenderIrElementKt.render$default(irReferenceType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        IrType argumentTypeAt = getArgumentTypeAt(irSimpleType, 1);
        IrType argumentTypeAt2 = getArgumentTypeAt(irSimpleType, 0);
        FirValueParameterSymbol firValueParameterSymbol = (FirValueParameterSymbol) CollectionsKt.singleOrNull((List) callableSymbol.getValueParameterSymbols());
        if (firValueParameterSymbol == null) {
            throw new AssertionError("Single value parameter expected: " + callableSymbol.getValueParameterSymbols());
        }
        IrFactory irFactory = getIrFactory();
        IrDeclarationOrigin.ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR adapter_for_fun_interface_constructor = IrDeclarationOrigin.ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR.INSTANCE;
        Name name2 = callableSymbol.getName();
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, startOffset, endOffset, adapter_for_fun_interface_constructor, name2, LOCAL, false, false, argumentTypeAt, Modality.FINAL, new IrSimpleFunctionSymbolImpl(functionDescriptor, i, objArr == true ? 1 : 0), false, false, false, false, false, null, false, 98304, null);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
        getSymbolTable().enterScope(irSimpleFunction);
        irSimpleFunctionCreateSimpleFunction$default.setDispatchReceiverParameter(null);
        irSimpleFunctionCreateSimpleFunction$default.setExtensionReceiverParameter(null);
        IrValueParameter irValueParameterCreateAdapterParameter = createAdapterParameter(irSimpleFunctionCreateSimpleFunction$default, firValueParameterSymbol.getName(), 0, argumentTypeAt2, IrDeclarationOrigin.ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE.INSTANCE);
        irSimpleFunctionCreateSimpleFunction$default.setValueParameters(CollectionsKt.listOf(irValueParameterCreateAdapterParameter));
        IrFactory irFactory2 = getIrFactory();
        IrType nothingType = this.components.getIrBuiltIns().getNothingType();
        IrSimpleFunctionSymbol symbol = irSimpleFunctionCreateSimpleFunction$default.getSymbol();
        IrTypeOperator irTypeOperator = IrTypeOperator.SAM_CONVERSION;
        IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, argumentTypeAt2, getIrBuiltIns().getCheckNotNullSymbol(), 1, 1, IrStatementOrigin.EXCLEXCL.INSTANCE, null, 128, null);
        irCallImpl.putTypeArgument(0, argumentTypeAt2);
        irCallImpl.putValueArgument(0, new IrGetValueImpl(startOffset, endOffset, irValueParameterCreateAdapterParameter.getSymbol(), null, 8, null));
        Unit unit = Unit.INSTANCE;
        irSimpleFunctionCreateSimpleFunction$default.setBody(IrFactoryHelpersKt.createBlockBody(irFactory2, startOffset, endOffset, (List<? extends IrStatement>) CollectionsKt.listOf(new IrReturnImpl(startOffset, endOffset, nothingType, symbol, new IrTypeOperatorCallImpl(startOffset, endOffset, argumentTypeAt, irTypeOperator, argumentTypeAt, irCallImpl)))));
        getSymbolTable().leaveScope(irSimpleFunction);
        IrDeclarationParent irDeclarationParentParent = this.conversionScope.parent();
        Intrinsics.checkNotNull(irDeclarationParentParent);
        irSimpleFunctionCreateSimpleFunction$default.setParent(irDeclarationParentParent);
        return irSimpleFunctionCreateSimpleFunction$default;
    }
}
