package org.jetbrains.kotlin.fir.backend;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizedResult;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategyKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.builder.FirFileAnnotationsContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirPackageDirectiveBuilder;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueClassRepresentationKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt;
import org.jetbrains.kotlin.fir.declarations.builder.FirFileBuilder;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtensionKt;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProviderKt;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.references.impl.FirPropertyFromParameterResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.CallableIdUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirSimpleSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirDelegateFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFileSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrErrorTypeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ConversionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Â\u0003\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\b\u0010\u000e\u001a\u00020\u000fH\u0000\u001a\u0012\u0010\u0010\u001a\u00020\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002\u001a\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0018\u0010\u0018\u001a\u00020\u0019*\u0004\u0018\u00010\u001a2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0019\u001a\u001a\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001d*\u00020\u00172\u0006\u0010\u001f\u001a\u00020 \u001a\u0010\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"*\u00020$\u001aY\u0010%\u001a\u0002H&\"\b\b\u0000\u0010&*\u00020'*\u0004\u0018\u00010(26\u0010)\u001a2\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(.\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(/\u0012\u0004\u0012\u0002H&0*H\u0080\bø\u0001\u0000¢\u0006\u0002\u00100\u001aW\u0010%\u001a\u0002H&\"\b\b\u0000\u0010&*\u00020'*\u00020126\u0010)\u001a2\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(.\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(/\u0012\u0004\u0012\u0002H&0*H\u0080\bø\u0001\u0000¢\u0006\u0002\u00102\u001aW\u0010%\u001a\u0002H&\"\b\b\u0000\u0010&*\u00020'*\u00020326\u0010)\u001a2\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(.\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(/\u0012\u0004\u0012\u0002H&0*H\u0080\bø\u0001\u0000¢\u0006\u0002\u00104\u001a_\u0010%\u001a\u0002H&\"\b\b\u0000\u0010&*\u00020'*\u0002052\u0006\u00106\u001a\u00020726\u0010)\u001a2\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(.\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(/\u0012\u0004\u0012\u0002H&0*H\u0080\bø\u0001\u0000¢\u0006\u0002\u00108\u001aW\u0010%\u001a\u0002H&\"\b\b\u0000\u0010&*\u00020'*\u00020926\u0010)\u001a2\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(.\u0012\u0013\u0012\u00110+¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(/\u0012\u0004\u0012\u0002H&0*H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010:\u001a\u0010\u0010;\u001a\b\u0012\u0004\u0012\u00020<0\"*\u00020=\u001a\"\u0010>\u001a\u00020?*\u00020\u00172\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020?\u001a2\u0010E\u001a\u000e\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u00020C0\u0002*\u00020\u00172\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020H2\n\b\u0002\u0010I\u001a\u0004\u0018\u00010J\u001a&\u0010K\u001a\u000e\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u00020C0\u0002*\u00020\u00172\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020H\u001aO\u0010L\u001a\u00020M*\u00020N2\u0006\u0010O\u001a\u00020\f2\u0006\u0010P\u001a\u00020\u00192\b\b\u0002\u0010.\u001a\u00020+2\b\b\u0002\u0010/\u001a\u00020+2\b\b\u0002\u0010-\u001a\u00020\u00032\n\b\u0002\u0010Q\u001a\u0004\u0018\u00010RH\u0000R\u00020\u0017¢\u0006\u0002\u0010S\u001a\u0014\u0010T\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010U*\u0004\u0018\u00010V\u001a+\u0010W\u001a\b\u0012\u0004\u0012\u00020X0\"*\u00020Y2\u0006\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020\u000bH\u0000R\u00020\u0017¢\u0006\u0002\u0010]\u001a#\u0010^\u001a\b\u0012\u0004\u0012\u00020X0\"*\u00020_2\u0006\u0010Z\u001a\u00020[H\u0000R\u00020\u0017¢\u0006\u0002\u0010`\u001a#\u0010a\u001a\b\u0012\u0004\u0012\u00020b0\"*\u00020Y2\u0006\u0010Z\u001a\u00020[H\u0000R\u00020\u0017¢\u0006\u0002\u0010c\u001a\u000e\u0010d\u001a\u0004\u0018\u00010\u0003*\u00020eH\u0002\u001a\n\u0010f\u001a\u00020\u0006*\u00020e\u001a\u0012\u0010g\u001a\u0006\u0012\u0002\b\u00030h*\u0006\u0012\u0002\b\u00030i\u001a\f\u0010j\u001a\u0004\u0018\u00010\u0006*\u00020e\u001a\u0019\u0010k\u001a\b\u0012\u0004\u0012\u00020l0\"*\u00020 R\u00020\u0017¢\u0006\u0002\u0010m\u001a\u001d\u0010n\u001a\n\u0012\u0004\u0012\u00020o\u0018\u00010U*\u00020[H\u0002R\u00020\u0017¢\u0006\u0002\u0010p\u001a\u0012\u0010q\u001a\u00020\u0019*\u00020[2\u0006\u0010r\u001a\u00020s\u001a1\u0010t\u001a\u00020u*\u00020_2\u0006\u0010Z\u001a\u00020[2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020x\u0012\u0004\u0012\u00020u0wH\u0000R\u00020\u0017¢\u0006\u0002\u0010y\u001a.\u0010z\u001a\u00020{*\u00020|2\u0006\u0010}\u001a\u00020x2\u0006\u0010Z\u001a\u00020[2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020x\u0012\u0004\u0012\u00020{0w\u001a0\u0010~\u001a\u00020{*\u00020|2\u0007\u0010\u007f\u001a\u00030\u0080\u00012\u0006\u0010Z\u001a\u00020[2\u0013\u0010v\u001a\u000f\u0012\u0005\u0012\u00030\u0080\u0001\u0012\u0004\u0012\u00020{0w\u001a:\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020b0\"*\u00020Y2\u0006\u0010Z\u001a\u00020[2\u0013\u0010v\u001a\u000f\u0012\u0005\u0012\u00030\u0080\u0001\u0012\u0004\u0012\u00020u0wH\u0000R\u00020\u0017¢\u0006\u0003\u0010\u0082\u0001\u001a\u0016\u0010\u0083\u0001\u001a\u0004\u0018\u00010+*\u0005\u0018\u00010\u0084\u0001¢\u0006\u0003\u0010\u0085\u0001\u001a\u000f\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u0006*\u000207H\u0000\u001a'\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u0002H&0\u0088\u0001\"\u0004\b\u0000\u0010&*\b\u0012\u0004\u0012\u0002H&0i2\u0007\u0010\u0089\u0001\u001a\u00020\f\u001a\u0016\u0010\u008a\u0001\u001a\u0006\u0012\u0002\b\u00030h*\u0007\u0012\u0002\b\u00030\u008b\u0001H\u0002\u001a\u001a\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001*\u00030\u008e\u00012\u0007\u0010\u008f\u0001\u001a\u00020=H\u0002\u001aG\u0010\u0090\u0001\u001a\u00030\u0091\u0001*\u0007\u0012\u0002\b\u00030\u0092\u00012\n\b\u0002\u0010\u0093\u0001\u001a\u00030\u0094\u00012\u001e\b\u0002\u0010\u0095\u0001\u001a\u0017\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030\u0096\u00010\"\u0012\u0004\u0012\u00020u\u0018\u00010wR\u00020\u0017¢\u0006\u0003\u0010\u0097\u0001\u001aT\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001*\u0002072\u0007\u0010\u009a\u0001\u001a\u00020\u00152\u0006\u0010G\u001a\u00020H2\b\u0010Q\u001a\u0004\u0018\u00010\u00152\t\b\u0002\u0010\u009b\u0001\u001a\u00020\u000b2\t\b\u0002\u0010\u009c\u0001\u001a\u00020\u000b2\t\b\u0002\u0010\u009d\u0001\u001a\u00020\u000bR\u00020\u0017¢\u0006\u0003\u0010\u009e\u0001\u001aS\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001*\u0007\u0012\u0002\b\u00030\u009f\u00012\u0007\u0010\u009a\u0001\u001a\u00020\u00152\u0007\u0010\u009b\u0001\u001a\u00020\u000b2\n\b\u0002\u0010Q\u001a\u0004\u0018\u00010\u00152\t\b\u0002\u0010\u009c\u0001\u001a\u00020\u000b2\t\b\u0002\u0010\u009d\u0001\u001a\u00020\u000bH\u0002R\u00020\u0017¢\u0006\u0003\u0010 \u0001\u001aS\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001*\u0007\u0012\u0002\b\u00030¡\u00012\u0007\u0010\u009a\u0001\u001a\u00020\u00152\u0007\u0010\u009b\u0001\u001a\u00020\u000b2\n\b\u0002\u0010Q\u001a\u0004\u0018\u00010\u00152\t\b\u0002\u0010\u009c\u0001\u001a\u00020\u000b2\t\b\u0002\u0010\u009d\u0001\u001a\u00020\u000bH\u0002R\u00020\u0017¢\u0006\u0003\u0010¢\u0001\u001a0\u0010£\u0001\u001a\u0007\u0012\u0002\b\u00030¡\u0001*\u0007\u0012\u0002\b\u00030¡\u00012\f\b\u0002\u0010¤\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0080\u0010R\u00020\u0017¢\u0006\u0003\u0010¥\u0001\u001a\u0018\u0010¦\u0001\u001a\u0007\u0012\u0002\b\u00030¡\u0001*\u0007\u0012\u0002\b\u00030¡\u0001H\u0080\u0010\"*\u0010\u0000\u001a\u001e\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002j\u0002`\u0005\u0012\u0004\u0012\u00020\u00060\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\n\u001a\u00020\u000b*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\n\u0010\r*%\b\u0002\u0010§\u0001\"\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006¨\u0001"}, d2 = {"PREFIX_POSTFIX_ORIGIN_MAP", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "Lorg/jetbrains/kotlin/fir/backend/NameWithElementType;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "nameToOperationConventionOrigin", "", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "isSamType", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "createErrorType", "Lorg/jetbrains/kotlin/ir/types/IrErrorType;", "isCompiledElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "asCompileTimeIrInitializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "computeIrOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "predefinedOrigin", "computeValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "contextReceiversForFunctionOrContainingProperty", "", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "convertWithOffsets", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/KtSourceElement;", "f", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "startOffset", "endOffset", "(Lorg/jetbrains/kotlin/KtSourceElement;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Lorg/jetbrains/kotlin/fir/references/FirReference;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/ir/IrElement;", "createFilesWithGeneratedDeclarations", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/FirSession;", "createSafeCallConstruction", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "receiverVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "receiverVariableSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "expressionOnNotNull", "createTemporaryVariable", "receiverExpression", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "nameHint", "", "createTemporaryVariableForSafeCallConstruction", "declareThisReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "thisType", "thisOrigin", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;IILorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "extractFirDeclarations", "", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;", "generateOverriddenAccessorSymbols", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "containingClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "isGetter", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/fir/declarations/FirClass;Z)Ljava/util/List;", "generateOverriddenFunctionSymbols", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Ljava/util/List;", "generateOverriddenPropertySymbols", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Ljava/util/List;", "getCallableNameFromIntClassIfAny", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "getIrAssignmentOrigin", "getIrConstKind", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "getIrPrefixPostfixOriginIfAny", "getIrSymbolsForSealedSubclasses", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Ljava/util/List;", "getSuperTypesAsIrClasses", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Ljava/util/Set;", "irOrigin", "firProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirProvider;", "processOverriddenFunctionSymbols", "", "processor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lkotlin/jvm/functions/Function1;)V", "processOverriddenFunctionsFromSuperClasses", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "functionSymbol", "processOverriddenPropertiesFromSuperClasses", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "processOverriddenPropertySymbols", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "startOffsetSkippingComments", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "(Lorg/jetbrains/kotlin/AbstractKtSourceElement;)Ljava/lang/Integer;", "statementOrigin", "toIrConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "irType", "toIrConstKind", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "toLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "session", "toSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "typeOrigin", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "handleAnnotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "toSymbolForCall", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "dispatchReceiver", "preferGetter", "isDelegate", "isReference", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/references/FirReference;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;ZZZ)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;ZLorg/jetbrains/kotlin/fir/expressions/FirExpression;ZZ)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;ZLorg/jetbrains/kotlin/fir/expressions/FirExpression;ZZ)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "unwrapCallRepresentative", "owner", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "unwrapSubstitutionAndIntersectionOverrides", "NameWithElementType", "fir2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConversionUtilsKt {
    private static final Map<Name, IrStatementOriginImpl> nameToOperationConventionOrigin = MapsKt.mutableMapOf(TuplesKt.to(OperatorNameConventions.PLUS, IrStatementOrigin.PLUS.INSTANCE), TuplesKt.to(OperatorNameConventions.MINUS, IrStatementOrigin.MINUS.INSTANCE), TuplesKt.to(OperatorNameConventions.TIMES, IrStatementOrigin.MUL.INSTANCE), TuplesKt.to(OperatorNameConventions.DIV, IrStatementOrigin.DIV.INSTANCE), TuplesKt.to(OperatorNameConventions.MOD, IrStatementOrigin.PERC.INSTANCE), TuplesKt.to(OperatorNameConventions.REM, IrStatementOrigin.PERC.INSTANCE), TuplesKt.to(OperatorNameConventions.RANGE_TO, IrStatementOrigin.RANGE.INSTANCE), TuplesKt.to(OperatorNameConventions.RANGE_UNTIL, IrStatementOrigin.RANGE_UNTIL.INSTANCE), TuplesKt.to(OperatorNameConventions.CONTAINS, IrStatementOrigin.IN.INSTANCE));
    private static final Map<Pair<Name, IElementType>, IrStatementOrigin> PREFIX_POSTFIX_ORIGIN_MAP = MapsKt.hashMapOf(TuplesKt.to(TuplesKt.to(OperatorNameConventions.INC, KtNodeTypes.PREFIX_EXPRESSION), IrStatementOrigin.PREFIX_INCR.INSTANCE), TuplesKt.to(TuplesKt.to(OperatorNameConventions.INC, KtNodeTypes.POSTFIX_EXPRESSION), IrStatementOrigin.POSTFIX_INCR.INSTANCE), TuplesKt.to(TuplesKt.to(OperatorNameConventions.DEC, KtNodeTypes.PREFIX_EXPRESSION), IrStatementOrigin.PREFIX_DECR.INSTANCE), TuplesKt.to(TuplesKt.to(OperatorNameConventions.DEC, KtNodeTypes.POSTFIX_EXPRESSION), IrStatementOrigin.POSTFIX_DECR.INSTANCE));

    public static final Integer startOffsetSkippingComments(AbstractKtSourceElement abstractKtSourceElement) {
        if (abstractKtSourceElement instanceof KtPsiSourceElement) {
            return Integer.valueOf(PsiUtilsKt.getStartOffsetSkippingComments(((KtPsiSourceElement) abstractKtSourceElement).getPsi()));
        }
        if (abstractKtSourceElement instanceof KtLightSourceElement) {
            return Integer.valueOf(LightTreePositioningStrategyKt.getStartOffsetSkippingComments((KtLightSourceElement) abstractKtSourceElement));
        }
        return null;
    }

    public static final <T extends IrElement> T convertWithOffsets(FirElement firElement, Function2<? super Integer, ? super Integer, ? extends T> f) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        KtSourceElement source = firElement.getSource();
        KtSourceElement ktSourceElement = source;
        int i = -1;
        if (isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        return f.invoke(Integer.valueOf(i), Integer.valueOf(endOffset));
    }

    public static final <T extends IrElement> T convertWithOffsets(KtSourceElement ktSourceElement, Function2<? super Integer, ? super Integer, ? extends T> f) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(f, "f");
        KtSourceElement ktSourceElement2 = ktSourceElement;
        int i = -1;
        if (isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement2))) {
            endOffset = -1;
        } else {
            if (ktSourceElement == null || (numStartOffsetSkippingComments = startOffsetSkippingComments(ktSourceElement2)) == null) {
                startOffset = ktSourceElement != null ? ktSourceElement.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = ktSourceElement != null ? ktSourceElement.getEndOffset() : -1;
            i = startOffset;
        }
        return f.invoke(Integer.valueOf(i), Integer.valueOf(endOffset));
    }

    public static final <T extends IrElement> T convertWithOffsets(FirQualifiedAccessExpression firQualifiedAccessExpression, Function2<? super Integer, ? super Integer, ? extends T> f) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(firQualifiedAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        FirQualifiedAccessExpression firQualifiedAccessExpression2 = firQualifiedAccessExpression;
        FirReference calleeReference = firQualifiedAccessExpression.getCalleeReference();
        int i = -1;
        if (isCompiledElement(UtilsKt.getPsi(firQualifiedAccessExpression2))) {
            endOffset = -1;
        } else {
            KtSourceElement source = calleeReference.getSource();
            if (source == null || (numStartOffsetSkippingComments = startOffsetSkippingComments(source)) == null) {
                KtSourceElement source2 = calleeReference.getSource();
                startOffset = source2 != null ? source2.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            KtSourceElement source3 = firQualifiedAccessExpression2.getSource();
            i = startOffset;
            endOffset = source3 != null ? source3.getEndOffset() : -1;
        }
        return f.invoke(Integer.valueOf(i), Integer.valueOf(endOffset));
    }

    public static final <T extends IrElement> T convertWithOffsets(FirThisReceiverExpression firThisReceiverExpression, Function2<? super Integer, ? super Integer, ? extends T> f) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(firThisReceiverExpression, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        KtSourceElement source = firThisReceiverExpression.getSource();
        KtSourceElement ktSourceElement = source;
        int i = -1;
        if (isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        return f.invoke(Integer.valueOf(i), Integer.valueOf(endOffset));
    }

    public static final <T extends IrElement> T convertWithOffsets(FirStatement firStatement, FirReference calleeReference, Function2<? super Integer, ? super Integer, ? extends T> f) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(firStatement, "<this>");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        Intrinsics.checkNotNullParameter(f, "f");
        int i = -1;
        if (isCompiledElement(UtilsKt.getPsi(firStatement))) {
            endOffset = -1;
        } else {
            KtSourceElement source = calleeReference.getSource();
            if (source == null || (numStartOffsetSkippingComments = startOffsetSkippingComments(source)) == null) {
                KtSourceElement source2 = calleeReference.getSource();
                startOffset = source2 != null ? source2.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            KtSourceElement source3 = firStatement.getSource();
            endOffset = source3 != null ? source3.getEndOffset() : -1;
            i = startOffset;
        }
        return f.invoke(Integer.valueOf(i), Integer.valueOf(endOffset));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isCompiledElement(PsiElement psiElement) {
        if (psiElement == null) {
            return false;
        }
        if (psiElement instanceof PsiCompiledElement) {
            return true;
        }
        PsiFile containingFile = psiElement.getContainingFile();
        return !(containingFile instanceof KtFile) || ((KtFile) containingFile).getIsCompiled();
    }

    public static final IrErrorType createErrorType() {
        return new IrErrorTypeImpl(null, CollectionsKt.emptyList(), Variance.INVARIANT, false, 8, null);
    }

    public static /* synthetic */ IrClassifierSymbol toSymbol$default(Fir2IrComponents fir2IrComponents, FirClassifierSymbol firClassifierSymbol, ConversionTypeOrigin conversionTypeOrigin, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        if ((i & 4) != 0) {
            function1 = null;
        }
        return toSymbol(fir2IrComponents, firClassifierSymbol, conversionTypeOrigin, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrClassifierSymbol toSymbol(Fir2IrComponents context_receiver_0, FirClassifierSymbol<?> firClassifierSymbol, ConversionTypeOrigin typeOrigin, Function1<? super List<? extends FirAnnotation>, Unit> function1) {
        IrClassifierSymbol symbol;
        Intrinsics.checkNotNullParameter(firClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        if (firClassifierSymbol instanceof FirTypeParameterSymbol) {
            return context_receiver_0.getClassifierStorage().getIrTypeParameterSymbol((FirTypeParameterSymbol) firClassifierSymbol, typeOrigin);
        }
        if (firClassifierSymbol instanceof FirTypeAliasSymbol) {
            if (function1 != null) {
                function1.invoke(((FirTypeAlias) ((FirTypeAliasSymbol) firClassifierSymbol).getFir()).getExpandedTypeRef().getAnnotations());
            }
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(((FirTypeAlias) ((FirTypeAliasSymbol) firClassifierSymbol).getFir()).getExpandedTypeRef());
            Intrinsics.checkNotNull(coneType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) coneType;
            FirClassLikeSymbol<?> symbol2 = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), context_receiver_0.getSession());
            return (symbol2 == null || (symbol = toSymbol(context_receiver_0, symbol2, typeOrigin, function1)) == null) ? context_receiver_0.getClassifierStorage().getIrClassSymbolForNotFoundClass(coneClassLikeType.getLookupTag()) : symbol;
        }
        if (firClassifierSymbol instanceof FirClassSymbol) {
            return context_receiver_0.getClassifierStorage().getIrClassSymbol((FirClassSymbol) firClassifierSymbol);
        }
        throw new IllegalStateException(("Unknown symbol: " + firClassifierSymbol).toString());
    }

    private static final IrSymbol toSymbolForCall(Fir2IrComponents fir2IrComponents, FirBasedSymbol<?> firBasedSymbol, FirExpression firExpression, boolean z, FirExpression firExpression2, boolean z2, boolean z3) {
        if (firBasedSymbol instanceof FirCallableSymbol) {
            return toSymbolForCall(fir2IrComponents, (FirCallableSymbol<?>) unwrapCallRepresentative$default(fir2IrComponents, (FirCallableSymbol) firBasedSymbol, null, 2, null), firExpression, z, firExpression2, z2, z3);
        }
        if (firBasedSymbol instanceof FirClassifierSymbol) {
            return toSymbol$default(fir2IrComponents, (FirClassifierSymbol) firBasedSymbol, null, null, 6, null);
        }
        throw new IllegalStateException(("Unknown symbol: " + firBasedSymbol).toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrSymbol toSymbolForCall(Fir2IrComponents context_receiver_0, FirReference firReference, FirExpression dispatchReceiver, Fir2IrConversionScope conversionScope, FirExpression firExpression, boolean z, boolean z2, boolean z3) {
        IrValueParameter thisReceiver;
        IrSimpleFunction irSimpleFunctionParentAccessorOfPropertyFromStack;
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        Object symbol = null;
        if (firReference instanceof FirResolvedNamedReference) {
            FirCallableSymbol<? extends FirCallableDeclaration> resolvedSymbol = ((FirResolvedNamedReference) firReference).getResolvedSymbol();
            if ((resolvedSymbol instanceof FirCallableSymbol) && Intrinsics.areEqual(resolvedSymbol.getOrigin(), FirDeclarationOrigin.SubstitutionOverride.CallSite.INSTANCE)) {
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) ((FirCallableSymbol) resolvedSymbol).getFir();
                while (Intrinsics.areEqual(firCallableDeclaration.getOrigin(), FirDeclarationOrigin.SubstitutionOverride.CallSite.INSTANCE)) {
                    FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                    if (originalForSubstitutionOverrideAttr == null) {
                        break;
                    }
                    firCallableDeclaration = originalForSubstitutionOverrideAttr;
                }
                resolvedSymbol = firCallableDeclaration.getSymbol();
            }
            return toSymbolForCall(context_receiver_0, resolvedSymbol, dispatchReceiver, z, firExpression, z2, z3);
        }
        if (!(firReference instanceof FirThisReference)) {
            return null;
        }
        FirBasedSymbol<?> boundSymbol = ((FirThisReference) firReference).getBoundSymbol();
        if (boundSymbol instanceof FirClassSymbol) {
            IrValueParameter thisReceiver2 = context_receiver_0.getClassifierStorage().getIrClassSymbol((FirClassSymbol) boundSymbol).getOwner().getThisReceiver();
            symbol = (DeclarationSymbolMarker) (thisReceiver2 != null ? thisReceiver2.getSymbol() : null);
        } else if (boundSymbol instanceof FirFunctionSymbol) {
            IrValueParameter extensionReceiverParameter = Fir2IrDeclarationStorage.getIrFunctionSymbol$default(context_receiver_0.getDeclarationStorage(), (FirFunctionSymbol) boundSymbol, null, 2, null).getOwner().getExtensionReceiverParameter();
            symbol = (DeclarationSymbolMarker) (extensionReceiverParameter != null ? extensionReceiverParameter.getSymbol() : null);
        } else if (boundSymbol instanceof FirPropertySymbol) {
            IrSymbolOwner owner = Fir2IrDeclarationStorage.getIrPropertySymbol$default(context_receiver_0.getDeclarationStorage(), (FirPropertySymbol) boundSymbol, null, 2, null).getOwner();
            IrProperty irProperty = owner instanceof IrProperty ? (IrProperty) owner : null;
            if (irProperty != null && (irSimpleFunctionParentAccessorOfPropertyFromStack = conversionScope.parentAccessorOfPropertyFromStack(irProperty)) != null) {
                symbol = irSimpleFunctionParentAccessorOfPropertyFromStack.getSymbol();
            }
            symbol = (DeclarationSymbolMarker) symbol;
        } else if (boundSymbol instanceof FirScriptSymbol) {
            IrScript cachedIrScript = context_receiver_0.getDeclarationStorage().getCachedIrScript(((FirScriptSymbol) boundSymbol).getFir());
            if (cachedIrScript != null && (thisReceiver = cachedIrScript.getThisReceiver()) != null) {
                symbol = thisReceiver.getSymbol();
            }
            symbol = (DeclarationSymbolMarker) symbol;
        }
        return (IrSymbol) symbol;
    }

    private static final ConeClassLikeLookupTag toLookupTag(FirResolvedQualifier firResolvedQualifier, FirSession firSession) {
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        FirClassLikeSymbol<?> symbol = firResolvedQualifier.getSymbol();
        if (symbol instanceof FirClassSymbol) {
            return symbol.getLookupTag();
        }
        if (!(symbol instanceof FirTypeAliasSymbol) || (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(symbol, firSession)) == null) {
            return null;
        }
        return firRegularClassSymbolFullyExpandedClass.getLookupTag();
    }

    static /* synthetic */ IrSymbol toSymbolForCall$default(Fir2IrComponents fir2IrComponents, FirCallableSymbol firCallableSymbol, FirExpression firExpression, boolean z, FirExpression firExpression2, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 8) != 0) {
            firExpression2 = null;
        }
        return toSymbolForCall(fir2IrComponents, (FirCallableSymbol<?>) firCallableSymbol, firExpression, z, firExpression2, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? false : z3);
    }

    public static final IrConstKind<?> getIrConstKind(FirConstExpression<?> firConstExpression) {
        Intrinsics.checkNotNullParameter(firConstExpression, "<this>");
        ConstantValueKind<?> kind = firConstExpression.getKind();
        if (!(Intrinsics.areEqual(kind, ConstantValueKind.IntegerLiteral.INSTANCE) ? true : Intrinsics.areEqual(kind, ConstantValueKind.UnsignedIntegerLiteral.INSTANCE))) {
            return toIrConstKind(firConstExpression.getKind());
        }
        ConeKotlinType type = ((FirResolvedTypeRef) firConstExpression.getTypeRef()).getType();
        if (type != null) {
            ConstantValueKind<?> constKind = FirTypeUtilsKt.toConstKind(ConeIntegerLiteralType.getApproximatedType$default((ConeIntegerLiteralType) type, null, 1, null));
            Intrinsics.checkNotNull(constKind);
            return toIrConstKind(constKind);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType");
    }

    public static final <T> IrConst<T> toIrConst(FirConstExpression<T> firConstExpression, IrType irType) {
        int startOffset;
        int i;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Object objValueOf;
        Intrinsics.checkNotNullParameter(firConstExpression, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        KtSourceElement source = firConstExpression.getSource();
        KtSourceElement ktSourceElement = source;
        if (isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            i = -1;
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            i = startOffset;
            endOffset = source != null ? source.getEndOffset() : -1;
        }
        IrConstKind<?> irConstKind = getIrConstKind(firConstExpression);
        Intrinsics.checkNotNull(irConstKind, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstKind<T of org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.toIrConst$lambda$3>");
        T value = firConstExpression.getValue();
        Object obj = null;
        Long l = value instanceof Long ? (Long) value : null;
        if (l != null) {
            long jLongValue = l.longValue();
            if (Intrinsics.areEqual(irConstKind, IrConstKind.Byte.INSTANCE)) {
                objValueOf = Byte.valueOf((byte) jLongValue);
            } else if (Intrinsics.areEqual(irConstKind, IrConstKind.Short.INSTANCE)) {
                objValueOf = Short.valueOf((short) jLongValue);
            } else if (Intrinsics.areEqual(irConstKind, IrConstKind.Int.INSTANCE)) {
                objValueOf = Integer.valueOf((int) jLongValue);
            } else if (Intrinsics.areEqual(irConstKind, IrConstKind.Float.INSTANCE)) {
                objValueOf = Float.valueOf(jLongValue);
            } else {
                objValueOf = Intrinsics.areEqual(irConstKind, IrConstKind.Double.INSTANCE) ? Double.valueOf(jLongValue) : Long.valueOf(jLongValue);
            }
            obj = objValueOf;
        }
        return new IrConstImpl(i, endOffset, IrTypesKt.removeAnnotations(irType), irConstKind, obj == null ? firConstExpression.getValue() : obj);
    }

    private static final IrConstKind<?> toIrConstKind(ConstantValueKind<?> constantValueKind) {
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Null.INSTANCE)) {
            return IrConstKind.Null.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Boolean.INSTANCE)) {
            return IrConstKind.Boolean.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Char.INSTANCE)) {
            return IrConstKind.Char.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Byte.INSTANCE)) {
            return IrConstKind.Byte.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Short.INSTANCE)) {
            return IrConstKind.Short.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Int.INSTANCE)) {
            return IrConstKind.Int.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Long.INSTANCE)) {
            return IrConstKind.Long.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedByte.INSTANCE)) {
            return IrConstKind.Byte.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedShort.INSTANCE)) {
            return IrConstKind.Short.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedInt.INSTANCE)) {
            return IrConstKind.Int.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedLong.INSTANCE)) {
            return IrConstKind.Long.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.String.INSTANCE)) {
            return IrConstKind.String.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Float.INSTANCE)) {
            return IrConstKind.Float.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Double.INSTANCE)) {
            return IrConstKind.Double.INSTANCE;
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.IntegerLiteral.INSTANCE) ? true : Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedIntegerLiteral.INSTANCE)) {
            throw new IllegalArgumentException();
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Error.INSTANCE)) {
            throw new IllegalArgumentException();
        }
        throw new NoWhenBranchMatchedException();
    }

    public static /* synthetic */ FirCallableSymbol unwrapCallRepresentative$default(Fir2IrComponents fir2IrComponents, FirCallableSymbol firCallableSymbol, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
        if ((i & 2) != 0) {
            coneClassLikeLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) firCallableSymbol);
        }
        return unwrapCallRepresentative(fir2IrComponents, firCallableSymbol, coneClassLikeLookupTag);
    }

    public static final FirCallableSymbol<?> unwrapCallRepresentative(Fir2IrComponents context_receiver_0, FirCallableSymbol<?> firCallableSymbol, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        FirCallableDeclaration originalForIntersectionOverrideAttr;
        FirConstructor originalConstructorIfTypeAlias;
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        while (true) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol.getFir();
            if ((firCallableDeclaration instanceof FirConstructor) && (originalConstructorIfTypeAlias = org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt.getOriginalConstructorIfTypeAlias((FirConstructor) firCallableDeclaration)) != null) {
                firCallableSymbol = originalConstructorIfTypeAlias.getSymbol();
            } else {
                if (ClassMembersKt.isIntersectionOverride(firCallableDeclaration)) {
                    if (!(firCallableDeclaration.getDispatchReceiverType() instanceof ConeIntersectionType)) {
                        return firCallableSymbol;
                    }
                    originalForIntersectionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                    Intrinsics.checkNotNull(originalForIntersectionOverrideAttr);
                    firCallableSymbol = originalForIntersectionOverrideAttr.getSymbol();
                } else {
                    originalForIntersectionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                    if (originalForIntersectionOverrideAttr == null || !Intrinsics.areEqual(ClassMembersKt.containingClassLookupTag(originalForIntersectionOverrideAttr), coneClassLikeLookupTag)) {
                        return firCallableSymbol;
                    }
                    firCallableSymbol = originalForIntersectionOverrideAttr.getSymbol();
                }
            }
        }
    }

    public static final void processOverriddenFunctionSymbols(final Fir2IrComponents context_receiver_0, final FirSimpleFunction firSimpleFunction, FirClass containingClass, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(containingClass, context_receiver_0.getSession(), context_receiver_0.getScopeSession(), true, (FirResolvePhase) null);
        firTypeScopeUnsubstitutedScope.processFunctionsByName(firSimpleFunction.getName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.processOverriddenFunctionSymbols.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }
        });
        processOverriddenFunctionsFromSuperClasses(firTypeScopeUnsubstitutedScope, firSimpleFunction.getSymbol(), containingClass, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.processOverriddenFunctionSymbols.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirNamedFunctionSymbol overriddenSymbol) {
                Intrinsics.checkNotNullParameter(overriddenSymbol, "overriddenSymbol");
                if (!FirVisibilityCheckerKt.getVisibilityChecker(context_receiver_0.getSession()).isVisibleForOverriding((FirMemberDeclaration) firSimpleFunction.getSymbol().getFir(), (FirMemberDeclaration) overriddenSymbol.getFir())) {
                    return ProcessorAction.NEXT;
                }
                processor.invoke(overriddenSymbol);
                return ProcessorAction.NEXT;
            }
        });
    }

    public static final List<IrSimpleFunctionSymbol> generateOverriddenFunctionSymbols(final Fir2IrComponents context_receiver_0, final FirSimpleFunction firSimpleFunction, FirClass containingClass) {
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        final Set<IrClass> superTypesAsIrClasses = getSuperTypesAsIrClasses(context_receiver_0, containingClass);
        if (superTypesAsIrClasses == null) {
            return CollectionsKt.emptyList();
        }
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        processOverriddenFunctionSymbols(context_receiver_0, firSimpleFunction, containingClass, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.generateOverriddenFunctionSymbols.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : context_receiver_0.getFakeOverrideGenerator().getOverriddenSymbolsInSupertypes$fir2ir(it, superTypesAsIrClasses)) {
                    Intrinsics.areEqual(irSimpleFunctionSymbol, firSimpleFunction.getSymbol());
                    linkedHashSet.add(irSimpleFunctionSymbol);
                }
            }
        });
        return CollectionsKt.toList(linkedHashSet);
    }

    public static final ProcessorAction processOverriddenFunctionsFromSuperClasses(FirTypeScope firTypeScope, FirNamedFunctionSymbol functionSymbol, final FirClass containingClass, final Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return firTypeScope.processDirectOverriddenFunctionsWithBaseScope(functionSymbol, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.processOverriddenFunctionsFromSuperClasses.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirNamedFunctionSymbol overridden, FirTypeScope firTypeScope2) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                Intrinsics.checkNotNullParameter(firTypeScope2, "<anonymous parameter 1>");
                if (ClassMembersKt.isSubstitutionOverride((FirCallableDeclaration) overridden.getFir())) {
                    FirNamedFunctionSymbol firNamedFunctionSymbol = overridden;
                    if (Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firNamedFunctionSymbol), containingClass.getSymbol().getLookupTag())) {
                        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        FirNamedFunctionSymbol firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
                        Intrinsics.checkNotNull(firNamedFunctionSymbol2);
                        overridden = firNamedFunctionSymbol2;
                    }
                }
                return processor.invoke(overridden);
            }
        });
    }

    public static final ProcessorAction processOverriddenPropertiesFromSuperClasses(FirTypeScope firTypeScope, FirPropertySymbol propertySymbol, final FirClass containingClass, final Function1<? super FirPropertySymbol, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(firTypeScope, "<this>");
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return firTypeScope.processDirectOverriddenPropertiesWithBaseScope(propertySymbol, new Function2<FirPropertySymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.processOverriddenPropertiesFromSuperClasses.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirPropertySymbol overridden, FirTypeScope firTypeScope2) {
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                Intrinsics.checkNotNullParameter(firTypeScope2, "<anonymous parameter 1>");
                if (ClassMembersKt.isSubstitutionOverride((FirCallableDeclaration) overridden.getFir())) {
                    FirPropertySymbol firPropertySymbol = overridden;
                    if (Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firPropertySymbol), containingClass.getSymbol().getLookupTag())) {
                        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firPropertySymbol.getFir();
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        FirPropertySymbol firPropertySymbol2 = (FirPropertySymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
                        Intrinsics.checkNotNull(firPropertySymbol2);
                        overridden = firPropertySymbol2;
                    }
                }
                return processor.invoke(overridden);
            }
        });
    }

    private static final Set<IrClass> getSuperTypesAsIrClasses(Fir2IrComponents fir2IrComponents, FirClass firClass) {
        IrClass owner = fir2IrComponents.getDeclarationStorage().getClassifierStorage().getIrClassSymbol(firClass.getSymbol()).getOwner();
        IrClass irClass = owner instanceof IrClass ? owner : null;
        if (irClass == null) {
            return null;
        }
        List<IrType> superTypes = irClass.getSuperTypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull((IrType) it.next());
            IrSymbolOwner owner2 = classifierOrNull != null ? classifierOrNull.getOwner() : null;
            IrClass irClass2 = owner2 instanceof IrClass ? (IrClass) owner2 : null;
            if (irClass2 != null) {
                arrayList.add(irClass2);
            }
        }
        return CollectionsKt.toSet(arrayList);
    }

    public static final List<IrPropertySymbol> processOverriddenPropertySymbols(final Fir2IrComponents context_receiver_0, final FirProperty firProperty, FirClass containingClass, final Function1<? super FirPropertySymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(containingClass, context_receiver_0.getSession(), context_receiver_0.getScopeSession(), true, (FirResolvePhase) null);
        firTypeScopeUnsubstitutedScope.processPropertiesByName(firProperty.getName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.processOverriddenPropertySymbols.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }
        });
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        processOverriddenPropertiesFromSuperClasses(firTypeScopeUnsubstitutedScope, firProperty.getSymbol(), containingClass, new Function1<FirPropertySymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.processOverriddenPropertySymbols.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirPropertySymbol overriddenSymbol) {
                Intrinsics.checkNotNullParameter(overriddenSymbol, "overriddenSymbol");
                if (!FirVisibilityCheckerKt.getVisibilityChecker(context_receiver_0.getSession()).isVisibleForOverriding((FirMemberDeclaration) firProperty.getSymbol().getFir(), (FirMemberDeclaration) overriddenSymbol.getFir())) {
                    return ProcessorAction.NEXT;
                }
                processor.invoke(overriddenSymbol);
                return ProcessorAction.NEXT;
            }
        });
        return CollectionsKt.toList(linkedHashSet);
    }

    public static final List<IrPropertySymbol> generateOverriddenPropertySymbols(final Fir2IrComponents context_receiver_0, final FirProperty firProperty, FirClass containingClass) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        final Set<IrClass> superTypesAsIrClasses = getSuperTypesAsIrClasses(context_receiver_0, containingClass);
        if (superTypesAsIrClasses == null) {
            return CollectionsKt.emptyList();
        }
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        processOverriddenPropertySymbols(context_receiver_0, firProperty, containingClass, new Function1<FirPropertySymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.generateOverriddenPropertySymbols.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirPropertySymbol firPropertySymbol) {
                invoke2(firPropertySymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirPropertySymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                for (IrPropertySymbol irPropertySymbol : context_receiver_0.getFakeOverrideGenerator().getOverriddenSymbolsInSupertypes$fir2ir(it, superTypesAsIrClasses)) {
                    Intrinsics.areEqual(irPropertySymbol, firProperty.getSymbol());
                    linkedHashSet.add(irPropertySymbol);
                }
            }
        });
        return CollectionsKt.toList(linkedHashSet);
    }

    public static final List<IrSimpleFunctionSymbol> generateOverriddenAccessorSymbols(final Fir2IrComponents context_receiver_0, final FirProperty firProperty, FirClass containingClass, final boolean z) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(containingClass, context_receiver_0.getSession(), context_receiver_0.getScopeSession(), true, (FirResolvePhase) null);
        firTypeScopeUnsubstitutedScope.processPropertiesByName(firProperty.getName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.generateOverriddenAccessorSymbols.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }
        });
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        final Set<IrClass> superTypesAsIrClasses = getSuperTypesAsIrClasses(context_receiver_0, containingClass);
        if (superTypesAsIrClasses == null) {
            return CollectionsKt.emptyList();
        }
        processOverriddenPropertiesFromSuperClasses(firTypeScopeUnsubstitutedScope, firProperty.getSymbol(), containingClass, new Function1<FirPropertySymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.generateOverriddenAccessorSymbols.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirPropertySymbol overriddenSymbol) {
                Intrinsics.checkNotNullParameter(overriddenSymbol, "overriddenSymbol");
                if (!FirVisibilityCheckerKt.getVisibilityChecker(context_receiver_0.getSession()).isVisibleForOverriding((FirMemberDeclaration) firProperty.getSymbol().getFir(), (FirMemberDeclaration) overriddenSymbol.getFir())) {
                    return ProcessorAction.NEXT;
                }
                for (IrPropertySymbol irPropertySymbol : context_receiver_0.getFakeOverrideGenerator().getOverriddenSymbolsInSupertypes$fir2ir(overriddenSymbol, superTypesAsIrClasses)) {
                    IrSimpleFunctionSymbol symbol = null;
                    if (z) {
                        IrSimpleFunction getter = irPropertySymbol.getOwner().getGetter();
                        if (getter != null) {
                            symbol = getter.getSymbol();
                        }
                    } else {
                        IrSimpleFunction setter = irPropertySymbol.getOwner().getSetter();
                        if (setter != null) {
                            symbol = setter.getSymbol();
                        }
                    }
                    if (symbol != null) {
                        Intrinsics.areEqual(symbol, firProperty.getSymbol());
                        linkedHashSet.add(symbol);
                    }
                }
                return ProcessorAction.NEXT;
            }
        });
        return CollectionsKt.toList(linkedHashSet);
    }

    public static final IrStatementOrigin statementOrigin(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        if (firReference instanceof FirPropertyFromParameterResolvedNamedReference) {
            return IrStatementOrigin.INITIALIZE_PROPERTY_FROM_PARAMETER.INSTANCE;
        }
        IrStatementOrigin.COMPONENT_N component_nWithIndex = null;
        if (!(firReference instanceof FirResolvedNamedReference)) {
            return null;
        }
        FirResolvedNamedReference firResolvedNamedReference = (FirResolvedNamedReference) firReference;
        FirBasedSymbol<?> resolvedSymbol = firResolvedNamedReference.getResolvedSymbol();
        if (resolvedSymbol instanceof FirSyntheticPropertySymbol) {
            component_nWithIndex = IrStatementOrigin.GET_PROPERTY.INSTANCE;
        } else if (resolvedSymbol instanceof FirNamedFunctionSymbol) {
            FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) resolvedSymbol;
            if (CallableIdUtilsKt.isInvoke(firNamedFunctionSymbol.getCallableId())) {
                component_nWithIndex = IrStatementOrigin.INVOKE.INSTANCE;
            } else {
                KtSourceElement source = firReference.getSource();
                if (Intrinsics.areEqual(source != null ? source.getElementType() : null, KtNodeTypes.FOR) && CallableIdUtilsKt.isIteratorNext(firNamedFunctionSymbol.getCallableId())) {
                    component_nWithIndex = IrStatementOrigin.FOR_LOOP_NEXT.INSTANCE;
                } else {
                    KtSourceElement source2 = firReference.getSource();
                    if (Intrinsics.areEqual(source2 != null ? source2.getElementType() : null, KtNodeTypes.FOR) && CallableIdUtilsKt.isIteratorHasNext(firNamedFunctionSymbol.getCallableId())) {
                        component_nWithIndex = IrStatementOrigin.FOR_LOOP_HAS_NEXT.INSTANCE;
                    } else {
                        KtSourceElement source3 = firReference.getSource();
                        if (Intrinsics.areEqual(source3 != null ? source3.getElementType() : null, KtNodeTypes.FOR) && CallableIdUtilsKt.isIterator(firNamedFunctionSymbol.getCallableId())) {
                            component_nWithIndex = IrStatementOrigin.FOR_LOOP_ITERATOR.INSTANCE;
                        } else {
                            KtSourceElement source4 = firReference.getSource();
                            if (Intrinsics.areEqual(source4 != null ? source4.getElementType() : null, KtNodeTypes.OPERATION_REFERENCE)) {
                                component_nWithIndex = nameToOperationConventionOrigin.get(firNamedFunctionSymbol.getCallableId().getCallableName());
                            } else {
                                KtSourceElement source5 = firReference.getSource();
                                if ((source5 != null ? source5.getKind() : null) instanceof KtFakeSourceElementKind.DesugaredComponentFunctionCall) {
                                    IrStatementOrigin.COMPONENT_N.Companion companion = IrStatementOrigin.COMPONENT_N.INSTANCE;
                                    String strAsString = firResolvedNamedReference.getName().asString();
                                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                                    component_nWithIndex = companion.withIndex(Integer.parseInt(StringsKt.removePrefix(strAsString, (CharSequence) StandardNames.DATA_CLASS_COMPONENT_PREFIX)));
                                }
                            }
                        }
                    }
                }
            }
        }
        return component_nWithIndex;
    }

    public static /* synthetic */ IrValueParameter declareThisReceiverParameter$default(Fir2IrComponents fir2IrComponents, IrDeclarationParent irDeclarationParent, IrType irType, IrDeclarationOrigin irDeclarationOrigin, int i, int i2, Name name2, FirReceiverParameter firReceiverParameter, int i3, Object obj) {
        return declareThisReceiverParameter(fir2IrComponents, irDeclarationParent, irType, irDeclarationOrigin, (i3 & 8) != 0 ? irDeclarationParent.getStartOffset() : i, (i3 & 16) != 0 ? irDeclarationParent.getEndOffset() : i2, (i3 & 32) != 0 ? SpecialNames.THIS : name2, (i3 & 64) != 0 ? null : firReceiverParameter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrValueParameter declareThisReceiverParameter(Fir2IrComponents context_receiver_0, IrDeclarationParent irDeclarationParent, IrType thisType, IrDeclarationOrigin thisOrigin, int i, int i2, Name name2, FirReceiverParameter firReceiverParameter) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisType, "thisType");
        Intrinsics.checkNotNullParameter(thisOrigin, "thisOrigin");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrValueParameter irValueParameterCreateValueParameter = context_receiver_0.getSymbolTable().getIrFactory().createValueParameter(i, i2, thisOrigin, name2, thisType, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, 0 == true ? 1 : 0), -1, (IrType) null, false, false, false);
        irValueParameterCreateValueParameter.setParent(irDeclarationParent);
        if (firReceiverParameter != null) {
            context_receiver_0.getAnnotationGenerator().generate(irValueParameterCreateValueParameter, firReceiverParameter);
        }
        return irValueParameterCreateValueParameter;
    }

    public static final IrDeclarationOrigin irOrigin(FirClass firClass, FirProvider firProvider) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(firProvider, "firProvider");
        if (firProvider.getFirClassifierContainerFileIfAny(firClass.getSymbol()) != null) {
            return IrDeclarationOrigin.DEFINED.INSTANCE;
        }
        if (firClass.getOrigin() instanceof FirDeclarationOrigin.Java) {
            return IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE;
        }
        FirDeclarationOrigin origin = firClass.getOrigin();
        return origin instanceof FirDeclarationOrigin.Plugin ? new IrDeclarationOrigin.GeneratedByPlugin(((FirDeclarationOrigin.Plugin) origin).getKey()) : IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE;
    }

    public static final boolean isSamType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        if (classOrNull == null || classOrNull.getOwner().getKind() != ClassKind.INTERFACE) {
            return false;
        }
        Iterator<IrSimpleFunctionSymbol> it = IrUtilsKt.getFunctions(classOrNull).iterator();
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        boolean z = false;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                break;
            }
            IrSimpleFunctionSymbol next = it.next();
            if (next.getOwner().getModality() == Modality.ABSTRACT) {
                if (z) {
                    break;
                }
                irSimpleFunctionSymbol2 = next;
                z = true;
            }
        }
        return irSimpleFunctionSymbol != null;
    }

    public static final IrExpression createSafeCallConstruction(Fir2IrComponents fir2IrComponents, IrVariable receiverVariable, IrValueSymbol receiverVariableSymbol, IrExpression expressionOnNotNull) {
        Intrinsics.checkNotNullParameter(fir2IrComponents, "<this>");
        Intrinsics.checkNotNullParameter(receiverVariable, "receiverVariable");
        Intrinsics.checkNotNullParameter(receiverVariableSymbol, "receiverVariableSymbol");
        Intrinsics.checkNotNullParameter(expressionOnNotNull, "expressionOnNotNull");
        int startOffset = expressionOnNotNull.getStartOffset();
        int endOffset = expressionOnNotNull.getEndOffset();
        IrType irTypeMakeNullable = IrTypesKt.makeNullable(expressionOnNotNull.getType());
        IrBlockImpl irBlockImpl = new IrBlockImpl(startOffset, endOffset, irTypeMakeNullable, IrStatementOrigin.SAFE_CALL.INSTANCE);
        irBlockImpl.getStatements().add(receiverVariable);
        List<IrStatement> statements = irBlockImpl.getStatements();
        IrWhenImpl irWhenImpl = new IrWhenImpl(startOffset, endOffset, irTypeMakeNullable, null, 8, null);
        IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, fir2IrComponents.getIrBuiltIns().getBooleanType(), fir2IrComponents.getIrBuiltIns().getEqeqSymbol(), 0, 2, IrStatementOrigin.EQEQ.INSTANCE, null, 128, null);
        irCallImpl.putValueArgument(0, new IrGetValueImpl(startOffset, endOffset, receiverVariableSymbol, null, 8, null));
        irCallImpl.putValueArgument(1, IrConstImpl.INSTANCE.constNull(startOffset, endOffset, fir2IrComponents.getIrBuiltIns().getNothingNType()));
        irWhenImpl.getBranches().add(new IrBranchImpl(irCallImpl, IrConstImpl.INSTANCE.constNull(startOffset, endOffset, fir2IrComponents.getIrBuiltIns().getNothingNType())));
        irWhenImpl.getBranches().add(new IrElseBranchImpl(IrConstImpl.INSTANCE.m7206boolean(startOffset, endOffset, fir2IrComponents.getIrBuiltIns().getBooleanType(), true), expressionOnNotNull));
        statements.add(irWhenImpl);
        return irBlockImpl;
    }

    public static /* synthetic */ Pair createTemporaryVariable$default(Fir2IrComponents fir2IrComponents, IrExpression irExpression, Fir2IrConversionScope fir2IrConversionScope, String str, int i, Object obj) {
        if ((i & 4) != 0) {
            str = null;
        }
        return createTemporaryVariable(fir2IrComponents, irExpression, fir2IrConversionScope, str);
    }

    public static final Pair<IrVariable, IrValueSymbol> createTemporaryVariable(Fir2IrComponents fir2IrComponents, IrExpression receiverExpression, Fir2IrConversionScope conversionScope, String str) {
        Intrinsics.checkNotNullParameter(fir2IrComponents, "<this>");
        Intrinsics.checkNotNullParameter(receiverExpression, "receiverExpression");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        IrVariable irVariableDeclareTemporaryVariable = fir2IrComponents.getDeclarationStorage().declareTemporaryVariable(receiverExpression, str);
        irVariableDeclareTemporaryVariable.setParent(conversionScope.parentFromStack());
        return new Pair<>(irVariableDeclareTemporaryVariable, irVariableDeclareTemporaryVariable.getSymbol());
    }

    public static final Pair<IrVariable, IrValueSymbol> createTemporaryVariableForSafeCallConstruction(Fir2IrComponents fir2IrComponents, IrExpression receiverExpression, Fir2IrConversionScope conversionScope) {
        Intrinsics.checkNotNullParameter(fir2IrComponents, "<this>");
        Intrinsics.checkNotNullParameter(receiverExpression, "receiverExpression");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        return createTemporaryVariable(fir2IrComponents, receiverExpression, conversionScope, "safe_receiver");
    }

    public static final ValueClassRepresentation<IrSimpleType> computeValueClassRepresentation(final Fir2IrComponents fir2IrComponents, final FirRegularClass klass) {
        Intrinsics.checkNotNullParameter(fir2IrComponents, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        if ((FirValueClassRepresentationKt.getValueClassRepresentation(klass) != null) != klass.getStatus().isInline()) {
            throw new IllegalArgumentException(("Value class has no representation: " + UtilsKt.render(klass)).toString());
        }
        ValueClassRepresentation<ConeSimpleKotlinType> valueClassRepresentation = FirValueClassRepresentationKt.getValueClassRepresentation(klass);
        if (valueClassRepresentation != null) {
            return valueClassRepresentation.mapUnderlyingType(new Function1<ConeSimpleKotlinType, IrSimpleType>() { // from class: org.jetbrains.kotlin.fir.backend.ConversionUtilsKt.computeValueClassRepresentation.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrSimpleType invoke(ConeSimpleKotlinType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Fir2IrTypeConverter typeConverter = fir2IrComponents.getTypeConverter();
                    FirRegularClass firRegularClass = klass;
                    IrType irType$default = Fir2IrTypeConverter.toIrType$default(typeConverter, it, null, null, false, false, false, 31, null);
                    IrSimpleType irSimpleType = irType$default instanceof IrSimpleType ? (IrSimpleType) irType$default : null;
                    if (irSimpleType != null) {
                        return irSimpleType;
                    }
                    throw new IllegalStateException(("Value class underlying type is not a simple type: " + UtilsKt.render(firRegularClass)).toString());
                }
            });
        }
        return null;
    }

    public static final List<IrClassSymbol> getIrSymbolsForSealedSubclasses(Fir2IrComponents context_receiver_0, FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(context_receiver_0.getSession());
        List<ClassId> sealedClassInheritors = SealedClassInheritorsKt.getSealedClassInheritors(firRegularClass, context_receiver_0.getSession());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = sealedClassInheritors.iterator();
        while (it.hasNext()) {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = symbolProvider.getClassLikeSymbolByClassId((ClassId) it.next());
            IrClassifierSymbol symbol$default = classLikeSymbolByClassId != null ? toSymbol$default(context_receiver_0, classLikeSymbolByClassId, null, null, 6, null) : null;
            if (symbol$default != null) {
                arrayList.add(symbol$default);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof IrClassSymbol) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<FirFile> createFilesWithGeneratedDeclarations(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        FirSwitchableExtensionDeclarationsSymbolProvider generatedDeclarationsSymbolProvider = FirSwitchableExtensionDeclarationsSymbolProviderKt.getGeneratedDeclarationsSymbolProvider(firSession);
        if (generatedDeclarationsSymbolProvider == null) {
            return CollectionsKt.emptyList();
        }
        List<FirDeclarationGenerationExtension> declarationGenerators = FirDeclarationGenerationExtensionKt.getDeclarationGenerators(FirExtensionServiceKt.getExtensionService(firSession));
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = declarationGenerators.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((FirDeclarationGenerationExtension) it.next()).getTopLevelClassIdsCache().getValue());
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : arrayList) {
            FqName packageFqName = ((ClassId) obj).getPackageFqName();
            Object obj2 = linkedHashMap.get(packageFqName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(packageFqName, obj2);
            }
            ((List) obj2).add(obj);
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = declarationGenerators.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, ((FirDeclarationGenerationExtension) it2.next()).getTopLevelCallableIdsCache().getValue());
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj3 : arrayList2) {
            FqName packageName = ((CallableId) obj3).getPackageName();
            Object obj4 = linkedHashMap2.get(packageName);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap2.put(packageName, obj4);
            }
            ((List) obj4).add(obj3);
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        for (FqName fqName : SetsKt.plus(linkedHashMap.keySet(), (Iterable) linkedHashMap2.keySet())) {
            List list = listCreateListBuilder;
            FirFileBuilder firFileBuilder = new FirFileBuilder();
            firFileBuilder.setSymbol(new FirFileSymbol());
            FirFileAnnotationsContainerBuilder firFileAnnotationsContainerBuilder = new FirFileAnnotationsContainerBuilder();
            firFileAnnotationsContainerBuilder.setModuleData(FirModuleDataKt.getModuleData(firSession));
            firFileAnnotationsContainerBuilder.setContainingFileSymbol(firFileBuilder.getSymbol());
            firFileBuilder.setAnnotationsContainer(firFileAnnotationsContainerBuilder.mo6809build());
            firFileBuilder.setOrigin(FirDeclarationOrigin.Synthetic.PluginFile.INSTANCE);
            firFileBuilder.setModuleData(FirModuleDataKt.getModuleData(firSession));
            FirPackageDirectiveBuilder firPackageDirectiveBuilder = new FirPackageDirectiveBuilder();
            Intrinsics.checkNotNull(fqName);
            firPackageDirectiveBuilder.setPackageFqName(fqName);
            firFileBuilder.setPackageDirective(firPackageDirectiveBuilder.build());
            firFileBuilder.setName("__GENERATED DECLARATIONS__.kt");
            List<FirDeclaration> declarations = firFileBuilder.getDeclarations();
            Iterable iterable = (Iterable) linkedHashMap2.getOrDefault(fqName, CollectionsKt.emptyList());
            ArrayList arrayList3 = new ArrayList();
            Iterator it3 = iterable.iterator();
            while (it3.hasNext()) {
                CollectionsKt.addAll(arrayList3, generatedDeclarationsSymbolProvider.getTopLevelCallableSymbols(fqName, ((CallableId) it3.next()).getCallableName()));
            }
            ArrayList arrayList4 = arrayList3;
            ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
            Iterator it4 = arrayList4.iterator();
            while (it4.hasNext()) {
                arrayList5.add((FirCallableDeclaration) ((FirCallableSymbol) it4.next()).getFir());
            }
            CollectionsKt.addAll(declarations, arrayList5);
            List<FirDeclaration> declarations2 = firFileBuilder.getDeclarations();
            Iterable iterable2 = (Iterable) linkedHashMap.getOrDefault(fqName, CollectionsKt.emptyList());
            ArrayList arrayList6 = new ArrayList();
            Iterator it5 = iterable2.iterator();
            while (it5.hasNext()) {
                FirClassLikeSymbol<?> classLikeSymbolByClassId = generatedDeclarationsSymbolProvider.getClassLikeSymbolByClassId((ClassId) it5.next());
                FirClassLikeDeclaration firClassLikeDeclaration = classLikeSymbolByClassId != null ? (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir() : null;
                if (firClassLikeDeclaration != null) {
                    arrayList6.add(firClassLikeDeclaration);
                }
            }
            CollectionsKt.addAll(declarations2, arrayList6);
            list.add(firFileBuilder.mo6809build());
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    public static /* synthetic */ IrDeclarationOrigin computeIrOrigin$default(FirDeclaration firDeclaration, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irDeclarationOrigin = null;
        }
        return computeIrOrigin(firDeclaration, irDeclarationOrigin);
    }

    public static final IrDeclarationOrigin computeIrOrigin(FirDeclaration firDeclaration, IrDeclarationOrigin irDeclarationOrigin) {
        Name name2;
        IrDeclarationOrigin.DESTRUCTURED_OBJECT_PARAMETER destructured_object_parameter;
        if (irDeclarationOrigin != null) {
            return irDeclarationOrigin;
        }
        IrDeclarationOrigin irDeclarationOrigin2 = null;
        FirDeclarationOrigin origin = firDeclaration != null ? firDeclaration.getOrigin() : null;
        FirDeclarationOrigin.Plugin plugin = origin instanceof FirDeclarationOrigin.Plugin ? (FirDeclarationOrigin.Plugin) origin : null;
        IrDeclarationOrigin.GeneratedByPlugin generatedByPlugin = plugin != null ? new IrDeclarationOrigin.GeneratedByPlugin(plugin.getKey()) : null;
        if (generatedByPlugin != null) {
            return generatedByPlugin;
        }
        FirValueParameter firValueParameter = firDeclaration instanceof FirValueParameter ? (FirValueParameter) firDeclaration : null;
        if (firValueParameter != null && (name2 = firValueParameter.getName()) != null) {
            if (Intrinsics.areEqual(name2, SpecialNames.UNDERSCORE_FOR_UNUSED_VAR)) {
                destructured_object_parameter = IrDeclarationOrigin.UNDERSCORE_PARAMETER.INSTANCE;
            } else if (Intrinsics.areEqual(name2, SpecialNames.DESTRUCT)) {
                destructured_object_parameter = IrDeclarationOrigin.DESTRUCTURED_OBJECT_PARAMETER.INSTANCE;
            }
            irDeclarationOrigin2 = destructured_object_parameter;
        }
        if (irDeclarationOrigin2 != null) {
            return irDeclarationOrigin2;
        }
        return IrDeclarationOrigin.DEFINED.INSTANCE;
    }

    public static final IrStatementOrigin getIrAssignmentOrigin(FirVariableAssignment firVariableAssignment) {
        Intrinsics.checkNotNullParameter(firVariableAssignment, "<this>");
        Name callableNameFromIntClassIfAny = getCallableNameFromIntClassIfAny(firVariableAssignment);
        if (callableNameFromIntClassIfAny == null) {
            return IrStatementOrigin.EQ.INSTANCE;
        }
        Map<Pair<Name, IElementType>, IrStatementOrigin> map = PREFIX_POSTFIX_ORIGIN_MAP;
        KtSourceElement source = firVariableAssignment.getSource();
        IrStatementOrigin irStatementOrigin = map.get(TuplesKt.to(callableNameFromIntClassIfAny, source != null ? source.getElementType() : null));
        if (irStatementOrigin != null) {
            return irStatementOrigin;
        }
        FirExpression rValue = firVariableAssignment.getRValue();
        Intrinsics.checkNotNull(rValue, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirFunctionCall");
        KtSourceElement source2 = ((FirFunctionCall) rValue).getSource();
        KtSourceElementKind kind = source2 != null ? source2.getKind() : null;
        if (Intrinsics.areEqual(kind, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE) || Intrinsics.areEqual(kind, KtFakeSourceElementKind.DesugaredCompoundAssignment.INSTANCE)) {
            if (Intrinsics.areEqual(callableNameFromIntClassIfAny, OperatorNameConventions.PLUS)) {
                return IrStatementOrigin.PLUSEQ.INSTANCE;
            }
            if (Intrinsics.areEqual(callableNameFromIntClassIfAny, OperatorNameConventions.MINUS)) {
                return IrStatementOrigin.MINUSEQ.INSTANCE;
            }
        }
        return IrStatementOrigin.EQ.INSTANCE;
    }

    public static final IrStatementOrigin getIrPrefixPostfixOriginIfAny(FirVariableAssignment firVariableAssignment) {
        Intrinsics.checkNotNullParameter(firVariableAssignment, "<this>");
        Name callableNameFromIntClassIfAny = getCallableNameFromIntClassIfAny(firVariableAssignment);
        if (callableNameFromIntClassIfAny == null) {
            return null;
        }
        Map<Pair<Name, IElementType>, IrStatementOrigin> map = PREFIX_POSTFIX_ORIGIN_MAP;
        KtSourceElement source = firVariableAssignment.getSource();
        return map.get(TuplesKt.to(callableNameFromIntClassIfAny, source != null ? source.getElementType() : null));
    }

    private static final Name getCallableNameFromIntClassIfAny(FirVariableAssignment firVariableAssignment) {
        FirCallableSymbol resolvedCallableSymbol$default;
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(firVariableAssignment);
        if (calleeReference != null && (resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null)) != null) {
            FirExpression rValue = firVariableAssignment.getRValue();
            if ((rValue instanceof FirFunctionCall) && resolvedCallableSymbol$default.getCallableId().isLocal()) {
                FirCallableSymbol resolvedCallableSymbol$default2 = FirReferenceUtilsKt.toResolvedCallableSymbol$default(((FirFunctionCall) rValue).getCalleeReference(), false, 1, null);
                CallableId callableId = resolvedCallableSymbol$default2 != null ? resolvedCallableSymbol$default2.getCallableId() : null;
                if (Intrinsics.areEqual(callableId != null ? callableId.getClassId() : null, StandardClassIds.INSTANCE.getInt())) {
                    return callableId.getCallableName();
                }
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<FirContextReceiver> contextReceiversForFunctionOrContainingProperty(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        if (firCallableDeclaration instanceof FirPropertyAccessor) {
            return ((FirProperty) ((FirPropertyAccessor) firCallableDeclaration).getPropertySymbol().getFir()).getContextReceivers();
        }
        return firCallableDeclaration.getContextReceivers();
    }

    public static final Set<FirDeclaration> extractFirDeclarations(IrActualizedResult irActualizedResult) {
        List<IrDeclaration> actualizedExpectDeclarations;
        if (irActualizedResult == null || (actualizedExpectDeclarations = irActualizedResult.getActualizedExpectDeclarations()) == null) {
            return null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (IrDeclaration irDeclaration : actualizedExpectDeclarations) {
            Intrinsics.checkNotNull(irDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner");
            MetadataSource metadata = ((IrMetadataSourceOwner) irDeclaration).getMetadata();
            Intrinsics.checkNotNull(metadata, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.backend.FirMetadataSource");
            FirDeclaration fir = ((FirMetadataSource) metadata).getFir();
            if (fir != null) {
                linkedHashSet.add(fir);
            }
        }
        return linkedHashSet;
    }

    public static final IrExpressionBody asCompileTimeIrInitializer(FirExpression firExpression, Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(components, "components");
        IrElement irElement = (IrElement) firExpression.accept(new Fir2IrVisitor(components, new Fir2IrConversionScope()), null);
        if (irElement instanceof IrExpressionBody) {
            return (IrExpressionBody) irElement;
        }
        if (irElement instanceof IrExpression) {
            return IrFactoryHelpersKt.createExpressionBody(components.getIrFactory(), (IrExpression) irElement);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x006a  */
    /* JADX WARN: Multi-variable type inference failed */
    private static final IrSymbol toSymbolForCall(Fir2IrComponents fir2IrComponents, FirCallableSymbol<?> firCallableSymbol, FirExpression firExpression, boolean z, FirExpression firExpression2, boolean z2, boolean z3) {
        ConeClassLikeLookupTag lookupTag;
        FirSimpleFunction delegate;
        FirNamedFunctionSymbol symbol;
        IrSymbol symbolForCall$default;
        if (firCallableSymbol.getRawStatus().isStatic()) {
            FirResolvedQualifier firResolvedQualifier = firExpression instanceof FirResolvedQualifier ? (FirResolvedQualifier) firExpression : null;
            if (firResolvedQualifier != null) {
                lookupTag = toLookupTag(firResolvedQualifier, fir2IrComponents.getSession());
            } else {
                lookupTag = null;
            }
        } else if (!(firExpression instanceof FirNoReceiverExpression)) {
            ConeSimpleKotlinType coneType = FirTypeUtilsKt.getConeType(firExpression.getTypeRef());
            ConeSimpleKotlinType dispatchReceiverType = firCallableSymbol.getDispatchReceiverType();
            if ((coneType instanceof ConeDynamicType) && dispatchReceiverType != null) {
                coneType = dispatchReceiverType;
            }
            lookupTag = LookupTagUtilsKt.findClassRepresentation(coneType, coneType, fir2IrComponents.getDeclarationStorage().getSession());
        } else if (z3 && ((FirCallableDeclaration) firCallableSymbol.getFir()).getReceiverParameter() == null) {
            FirResolvedQualifier firResolvedQualifier2 = firExpression2 instanceof FirResolvedQualifier ? (FirResolvedQualifier) firExpression2 : null;
            if (firResolvedQualifier2 != null) {
                lookupTag = toLookupTag(firResolvedQualifier2, fir2IrComponents.getSession());
            } else {
                lookupTag = null;
            }
        } else {
            lookupTag = null;
        }
        if (!(firCallableSymbol instanceof FirSimpleSyntheticPropertySymbol)) {
            if (firCallableSymbol instanceof FirFunctionSymbol) {
                return fir2IrComponents.getDeclarationStorage().getIrFunctionSymbol((FirFunctionSymbol) firCallableSymbol, lookupTag);
            }
            if (firCallableSymbol instanceof FirPropertySymbol) {
                return fir2IrComponents.getDeclarationStorage().getIrPropertySymbol((FirPropertySymbol) firCallableSymbol, lookupTag);
            }
            if (firCallableSymbol instanceof FirFieldSymbol) {
                return fir2IrComponents.getDeclarationStorage().getIrFieldSymbol((FirFieldSymbol) firCallableSymbol, lookupTag);
            }
            if (firCallableSymbol instanceof FirBackingFieldSymbol) {
                return fir2IrComponents.getDeclarationStorage().getIrBackingFieldSymbol((FirBackingFieldSymbol) firCallableSymbol);
            }
            if (firCallableSymbol instanceof FirDelegateFieldSymbol) {
                return fir2IrComponents.getDeclarationStorage().getIrDelegateFieldSymbol((FirVariableSymbol) firCallableSymbol);
            }
            if (firCallableSymbol instanceof FirVariableSymbol) {
                return fir2IrComponents.getDeclarationStorage().getIrValueSymbol((FirVariableSymbol) firCallableSymbol);
            }
            return null;
        }
        if (z2) {
            return Fir2IrDeclarationStorage.getIrPropertySymbol$default(fir2IrComponents.getDeclarationStorage(), (FirPropertySymbol) firCallableSymbol, null, 2, null);
        }
        D fir = ((FirSimpleSyntheticPropertySymbol) firCallableSymbol).getFir();
        FirSyntheticProperty firSyntheticProperty = fir instanceof FirSyntheticProperty ? (FirSyntheticProperty) fir : null;
        if (firSyntheticProperty != null) {
            if (z3) {
                symbolForCall$default = fir2IrComponents.getDeclarationStorage().getIrPropertySymbol((FirPropertySymbol) firCallableSymbol, lookupTag);
            } else {
                if (z) {
                    symbol = firSyntheticProperty.getGetter().getDelegate().getSymbol();
                } else {
                    FirSyntheticPropertyAccessor setter = firSyntheticProperty.getSetter();
                    if (setter == null || (delegate = setter.getDelegate()) == null || (symbol = delegate.getSymbol()) == null) {
                        throw new AssertionError("Written synthetic property must have a setter");
                    }
                }
                symbolForCall$default = toSymbolForCall$default(fir2IrComponents, unwrapCallRepresentative$default(fir2IrComponents, symbol, null, 2, null), firExpression, z, null, false, false, 40, null);
            }
            if (symbolForCall$default != null) {
                return symbolForCall$default;
            }
        }
        return Fir2IrDeclarationStorage.getIrPropertySymbol$default(fir2IrComponents.getDeclarationStorage(), (FirPropertySymbol) firCallableSymbol, null, 2, null);
    }

    public static final FirCallableSymbol<?> unwrapSubstitutionAndIntersectionOverrides(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        while (true) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol.getFir();
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            FirCallableSymbol<? extends FirCallableDeclaration> symbol = originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null;
            if (symbol == null || Intrinsics.areEqual(symbol, firCallableSymbol)) {
                FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) firCallableSymbol.getFir();
                FirCallableDeclaration originalForIntersectionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration2) : null;
                symbol = originalForIntersectionOverrideAttr != null ? originalForIntersectionOverrideAttr.getSymbol() : null;
                if (symbol == null) {
                    return firCallableSymbol;
                }
            }
            firCallableSymbol = symbol;
        }
    }
}
