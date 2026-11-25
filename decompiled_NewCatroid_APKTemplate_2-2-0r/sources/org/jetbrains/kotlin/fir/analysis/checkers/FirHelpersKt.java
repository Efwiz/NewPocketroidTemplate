package org.jetbrains.kotlin.fir.analysis.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.DelegateFieldsMapKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.impl.FirEmptyExpressionBlock;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ScopesKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousInitializerSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralConstantType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeConflictingProjection;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeIntersector;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.AnnotationTargetList;
import org.jetbrains.kotlin.resolve.AnnotationTargetLists;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ò\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\t2\u0006\u00108\u001a\u000209H\u0000\u001a@\u0010:\u001a\u0002042\u0006\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010>2\u0006\u0010?\u001a\u0002062\u0006\u00107\u001a\u00020\t2\u0006\u0010@\u001a\u00020\r2\u0006\u00108\u001a\u0002092\u0006\u0010A\u001a\u00020\u0011\u001a\u0018\u0010B\u001a\n\u0012\u0004\u0012\u00020C\u0018\u00010.2\b\u0010D\u001a\u0004\u0018\u00010E\u001a\u0012\u0010F\u001a\u0004\u0018\u00010G2\u0006\u0010@\u001a\u00020GH\u0002\u001a\u000e\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020K\u001a\u001e\u0010L\u001a\u00020\u00112\u0006\u00107\u001a\u00020M2\u0006\u0010N\u001a\u00020<2\u0006\u0010O\u001a\u00020<\u001a\u0014\u0010P\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010Q\u001a\u00020\b\u001a\u0018\u0010R\u001a\u0010\u0012\u0004\u0012\u00020E\u0012\u0006\u0012\u0004\u0018\u00010T0S*\u00020U\u001a\u0014\u0010V\u001a\b\u0012\u0004\u0012\u00020X0W*\u0004\u0018\u00010/H\u0002\u001a\u0012\u0010V\u001a\b\u0012\u0004\u0012\u00020X0W*\u0004\u0018\u00010<\u001a \u0010Y\u001a\b\u0012\u0004\u0012\u00020$0.*\u00020$2\u0006\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020]\u001a\u0018\u0010Y\u001a\b\u0012\u0004\u0012\u00020$0.*\u00020$2\u0006\u00107\u001a\u00020\t\u001a\n\u0010^\u001a\u00020\u0011*\u00020_\u001a\f\u0010`\u001a\u0004\u0018\u00010U*\u00020\t\u001a\u0014\u0010a\u001a\u0004\u0018\u00010E*\u00020U2\u0006\u00107\u001a\u00020\t\u001a \u0010b\u001a\u0004\u0018\u000106*\u0006\u0012\u0002\b\u0003002\u0006\u0010c\u001a\u00020\u00012\u0006\u0010Z\u001a\u00020[\u001a \u0010d\u001a\u0004\u0018\u00010e*\u0006\u0012\u0002\b\u0003002\u0006\u0010c\u001a\u00020\u00012\u0006\u0010Z\u001a\u00020[\u001a\u0018\u0010f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004*\u00020\b2\u0006\u0010Z\u001a\u00020[\u001a\u001c\u0010f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004*\u0006\u0012\u0002\b\u0003002\u0006\u0010Z\u001a\u00020[\u001a\u001c\u0010g\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004*\u0006\u0012\u0002\b\u00030h2\u0006\u0010Z\u001a\u00020[\u001a\"\u0010i\u001a\u00020j*\u0006\u0012\u0002\b\u00030k2\u0006\u0010l\u001a\u00020m2\n\u0010n\u001a\u0006\u0012\u0002\b\u00030h\u001a\u0012\u0010o\u001a\u00020<*\u00020<2\u0006\u0010Z\u001a\u00020[\u001a\u001a\u0010p\u001a\u00020\u0011*\u00020\b2\u0006\u0010c\u001a\u00020\u00012\u0006\u0010Z\u001a\u00020[\u001a\u001e\u0010p\u001a\u00020\u0011*\u0006\u0012\u0002\b\u0003002\u0006\u0010c\u001a\u00020\u00012\u0006\u0010Z\u001a\u00020[\u001a\f\u0010q\u001a\u00020\u0011*\u00020\bH\u0002\u001a\u000e\u0010r\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030s\u001a\u0012\u0010t\u001a\u00020\u0011*\u00020u2\u0006\u0010Z\u001a\u00020[\u001a\u0012\u0010v\u001a\u00020\u0011*\u00020w2\u0006\u00107\u001a\u00020\t\u001a\u0012\u0010x\u001a\u00020\u0011*\u00020<2\u0006\u0010Z\u001a\u00020[\u001a4\u0010y\u001a\u00020\u0011*\u00020<2\u0016\u0010z\u001a\u0012\u0012\u0004\u0012\u00020<0{j\b\u0012\u0004\u0012\u00020<`|2\u0006\u0010Z\u001a\u00020[2\u0006\u0010}\u001a\u00020\u0011H\u0002\u001a\u0012\u0010y\u001a\u00020\u0011*\u00020<2\u0006\u0010Z\u001a\u00020[\u001a\u0012\u0010~\u001a\u00020\u0011*\u00020<2\u0006\u0010Z\u001a\u00020[\u001a\u0012\u0010\u007f\u001a\u00020\u0011*\u00020<2\u0006\u0010Z\u001a\u00020[\u001a$\u0010\u0080\u0001\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030h2\u000b\u0010\u0081\u0001\u001a\u0006\u0012\u0002\b\u00030h2\u0006\u0010Z\u001a\u00020[\u001a!\u0010\u0080\u0001\u001a\u00020\u0011*\u00030\u0082\u00012\u0007\u00107\u001a\u00030\u0083\u00012\n\u0010\u0084\u0001\u001a\u0005\u0018\u00010\u0082\u0001\u001a\u0013\u0010\u0085\u0001\u001a\u00020\u0011*\u00020<2\u0006\u0010Z\u001a\u00020[\u001a\u0014\u0010\u0086\u0001\u001a\u00020\u0011*\u00020\u001f2\u0007\u0010\u0087\u0001\u001a\u00020U\u001a\u001b\u0010\u0086\u0001\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030k2\n\u0010n\u001a\u0006\u0012\u0002\b\u00030h\u001a\u0013\u0010\u0088\u0001\u001a\u00020<*\u00020<2\u0006\u0010Z\u001a\u00020[\u001a\u000e\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u0001*\u00020U\u001a\u001d\u0010\u008b\u0001\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004*\u0006\u0012\u0002\b\u00030\u00042\u0006\u00107\u001a\u00020\t\u001a*\u0010\u008c\u0001\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030s0.*\u00020$2\u000b\u0010\u008d\u0001\u001a\u0006\u0012\u0002\b\u00030h2\u0006\u00107\u001a\u00020\t\u001a\u001a\u0010\u008e\u0001\u001a\t\u0012\u0005\u0012\u00030\u008a\u00010W*\u00020u2\u0006\u00107\u001a\u00020\t\u001a\u0016\u0010\u008f\u0001\u001a\u00020\u0011*\u00030\u0090\u00012\u0006\u0010l\u001a\u00020mH\u0002\u001a\u0019\u0010\u0091\u0001\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004*\u00020E2\u0006\u0010Z\u001a\u00020[\u001a\u0016\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u0001*\u00020E2\u0006\u0010Z\u001a\u00020[\u001a\u0014\u0010\u0094\u0001\u001a\u00030\u0095\u0001*\u00020U2\u0006\u00107\u001a\u00020\t\u001a\u0018\u0010\u0094\u0001\u001a\u00030\u0095\u0001*\u0006\u0012\u0002\b\u00030h2\u0006\u00107\u001a\u00020\t\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001b\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u0006\u0012\u0002\b\u00030\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\"\u0017\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u001a\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\r8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\"\u0015\u0010\u0010\u001a\u00020\u0011*\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\"\u0015\u0010\u0015\u001a\u00020\u0011*\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017\"\u0015\u0010\u0018\u001a\u00020\u0011*\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u001a\"\u0015\u0010\u001b\u001a\u00020\u0011*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001d\"\u0018\u0010\u001e\u001a\u00020\u0011*\u00020\u001f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010 \"\u0015\u0010!\u001a\u00020\u0011*\u00020\t8F¢\u0006\u0006\u001a\u0004\b!\u0010\"\"\u0018\u0010#\u001a\u00020\u0011*\u00020$8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&\"\u0018\u0010'\u001a\u00020\u0011*\u00020$8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b(\u0010&\"\u0018\u0010)\u001a\u00020\u0011*\u00020$8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b*\u0010&\"\u0018\u0010+\u001a\u00020\u0011*\u00020$8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b,\u0010&\"!\u0010-\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.*\u0006\u0012\u0002\b\u0003008F¢\u0006\u0006\u001a\u0004\b1\u00102*\u000f\b\u0002\u0010\u0096\u0001\"\u00030\u0097\u00012\u00030\u0097\u0001¨\u0006\u0098\u0001"}, d2 = {"INLINE_ONLY_ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getClassKind", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)Lorg/jetbrains/kotlin/descriptors/ClassKind;", "closestNonLocal", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "getClosestNonLocal", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "defaultValueForParameter", "Lorg/jetbrains/kotlin/KtSourceElement;", "getDefaultValueForParameter", "(Lorg/jetbrains/kotlin/KtSourceElement;)Lorg/jetbrains/kotlin/KtSourceElement;", "hasValOrVar", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getHasValOrVar", "(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;)Z", "isConflictingOrNotInvariant", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;)Z", "isDelegated", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)Z", "isIterator", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)Z", "isMember", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Z", "isTopLevel", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)Z", "matchesDataClassSyntheticMemberSignatures", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getMatchesDataClassSyntheticMemberSignatures", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)Z", "matchesEqualsSignature", "getMatchesEqualsSignature", "matchesHashCodeSignature", "getMatchesHashCodeSignature", "matchesToStringSignature", "getMatchesToStringSignature", "typeParameterSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getTypeParameterSymbols", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Ljava/util/List;", "checkCondition", "", "condition", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "context", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkTypeMismatch", "lValueOriginalType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "assignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "rValue", FirebaseAnalytics.Param.SOURCE, "isInitializer", "extractArgumentsTypeRefAndSource", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirTypeRefSource;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "findDefaultValue", "Lorg/jetbrains/kotlin/KtLightSourceElement;", "getActualTargetList", "Lorg/jetbrains/kotlin/resolve/AnnotationTargetList;", "container", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "isSubtypeForTypeMismatch", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "subtype", "supertype", "closestNonLocalWith", "declaration", "collectSupertypesWithDelegates", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "collectUpperBounds", "", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "directOverriddenFunctions", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "explicitReceiverIsNotSuperReference", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "findClosestClassOrObject", "findNonInterfaceSupertype", "getAnnotationFirstArgument", "classId", "getAnnotationStringParameter", "", "getContainingClassSymbol", "getContainingDeclarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "getImplementationStatus", "Lorg/jetbrains/kotlin/util/ImplementationStatus;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "sessionHolder", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "parentClassSymbol", "getInlineClassUnderlyingType", "hasAnnotationOrInsideAnnotatedClass", "hasBody", "isFunctionForExpectTypeFromCastFeature", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "isInlineOnly", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "isLhsOfAssignment", "Lorg/jetbrains/kotlin/fir/FirElement;", "isRecursiveSingleFieldValueClassType", "isRecursiveValueClassType", "visited", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "onlyInline", "isSingleFieldValueClass", "isSubtypeOfThrowable", "isSupertypeOf", "other", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", ModuleXmlParser.TYPE, "isValueClass", "isVisibleInClass", "parentClass", "leastUpperBound", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "outerClassSymbol", "overriddenFunctions", "containingClass", "redundantModalities", "subjectToManyNotImplemented", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirIntersectionCallableSymbol;", "toClassLikeSymbol", "toRegularClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "unsubstitutedScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "TargetLists", "Lorg/jetbrains/kotlin/resolve/AnnotationTargetLists;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirHelpersKt {
    private static final ClassId INLINE_ONLY_ANNOTATION_CLASS_ID;

    /* JADX INFO: compiled from: FirHelpers.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.internal.InlineOnly"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        INLINE_ONLY_ANNOTATION_CLASS_ID = classId;
    }

    public static final FirTypeScope unsubstitutedScope(FirClass firClass, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return FirKotlinScopeProviderKt.unsubstitutedScope(firClass, context.getSessionHolder().getSession(), context.getSessionHolder().getScopeSession(), false, FirResolvePhase.STATUS);
    }

    public static final FirTypeScope unsubstitutedScope(FirClassSymbol<?> firClassSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return FirKotlinScopeProviderKt.unsubstitutedScope(firClassSymbol, context.getSessionHolder().getSession(), context.getSessionHolder().getScopeSession(), false, FirResolvePhase.STATUS);
    }

    private static final boolean isSupertypeOf$isSupertypeOf(FirClassSymbol<?> firClassSymbol, FirSession firSession, FirClassSymbol<?> firClassSymbol2, Set<FirClassSymbol<?>> set) {
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        Iterator<FirResolvedTypeRef> it = firClassSymbol2.getResolvedSuperTypeRefs().iterator();
        while (it.hasNext()) {
            FirClassLikeSymbol<?> classLikeSymbol = toClassLikeSymbol(it.next(), firSession);
            if (classLikeSymbol != null && (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(classLikeSymbol, firSession)) != null && !set.contains(firRegularClassSymbolFullyExpandedClass)) {
                set.add(firRegularClassSymbolFullyExpandedClass);
                if (Intrinsics.areEqual(firRegularClassSymbolFullyExpandedClass, firClassSymbol) || isSupertypeOf$isSupertypeOf(firClassSymbol, firSession, firRegularClassSymbolFullyExpandedClass, set)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean isSupertypeOf(FirClassSymbol<?> firClassSymbol, FirClassSymbol<?> other, FirSession session) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(session, "session");
        return isSupertypeOf$isSupertypeOf(firClassSymbol, session, other, new LinkedHashSet());
    }

    public static final boolean isValueClass(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneKotlinType, session);
        return regularClassSymbol != null && regularClassSymbol.getRawStatus().isInline();
    }

    public static final boolean isSingleFieldValueClass(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(session);
        return isRecursiveSingleFieldValueClassType(coneKotlinType, session) || typeContext.isInlineClass(typeContext.typeConstructor(coneKotlinType));
    }

    public static final boolean isRecursiveSingleFieldValueClassType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return isRecursiveValueClassType(coneKotlinType, new HashSet(), session, true);
    }

    public static final boolean isRecursiveValueClassType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return isRecursiveValueClassType(coneKotlinType, new HashSet(), session, false);
    }

    private static final boolean isRecursiveValueClassType(ConeKotlinType coneKotlinType, HashSet<ConeKotlinType> hashSet, FirSession firSession, boolean z) {
        boolean z2;
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneKotlinType, firSession);
        if (regularClassSymbol == null) {
            return false;
        }
        Object obj = null;
        if (!DeclarationUtilsKt.isInlineOrValueClass(regularClassSymbol)) {
            regularClassSymbol = null;
        }
        if (regularClassSymbol == null) {
            return false;
        }
        for (Object obj2 : regularClassSymbol.getDeclarationSymbols()) {
            FirBasedSymbol firBasedSymbol = (FirBasedSymbol) obj2;
            if ((firBasedSymbol instanceof FirConstructorSymbol) && ((FirConstructorSymbol) firBasedSymbol).isPrimary()) {
                obj = obj2;
                break;
            }
        }
        FirConstructorSymbol firConstructorSymbol = (FirConstructorSymbol) obj;
        if (firConstructorSymbol == null) {
            return false;
        }
        if (firConstructorSymbol.getValueParameterSymbols().size() > 1 && z) {
            return false;
        }
        if (hashSet.add(coneKotlinType)) {
            List<FirValueParameterSymbol> valueParameterSymbols = firConstructorSymbol.getValueParameterSymbols();
            if (!(valueParameterSymbols instanceof Collection) || !valueParameterSymbols.isEmpty()) {
                Iterator<T> it = valueParameterSymbols.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    if (isRecursiveValueClassType(FirTypeUtilsKt.getConeType(((FirValueParameterSymbol) it.next()).getResolvedReturnTypeRef()), hashSet, firSession, z)) {
                        z2 = true;
                        break;
                    }
                }
            } else {
                z2 = false;
                break;
            }
            hashSet.remove(coneKotlinType);
            if (!z2) {
                return false;
            }
        }
        return true;
    }

    public static final FirRegularClassSymbol toRegularClassSymbol(FirTypeRef firTypeRef, FirSession session) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(firTypeRef), session);
    }

    public static final FirClassLikeSymbol<?> getContainingClassSymbol(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firBasedSymbol instanceof FirCallableSymbol) {
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) firBasedSymbol);
            if (coneClassLikeLookupTagContainingClassLookupTag != null) {
                return LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag, session);
            }
            return null;
        }
        if (firBasedSymbol instanceof FirClassLikeSymbol) {
            ConeClassLikeLookupTag containingClassLookupTag = ClassMembersKt.getContainingClassLookupTag((FirClassLikeSymbol) firBasedSymbol);
            if (containingClassLookupTag != null) {
                return LookupTagUtilsKt.toSymbol(containingClassLookupTag, session);
            }
            return null;
        }
        if (!(firBasedSymbol instanceof FirAnonymousInitializerSymbol)) {
            return null;
        }
        ConeSimpleKotlinType dispatchReceiverType = ((FirAnonymousInitializerSymbol) firBasedSymbol).getDispatchReceiverType();
        FirClassifierSymbol<?> symbol = dispatchReceiverType != null ? TypeUtilsKt.toSymbol(dispatchReceiverType, session) : null;
        if (symbol instanceof FirClassLikeSymbol) {
            return (FirClassLikeSymbol) symbol;
        }
        return null;
    }

    public static final FirClassLikeSymbol<?> getContainingClassSymbol(FirDeclaration firDeclaration, FirSession session) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return getContainingClassSymbol(firDeclaration.getSymbol(), session);
    }

    public static final FirClassLikeSymbol<?> outerClassSymbol(FirClassLikeSymbol<?> firClassLikeSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (firClassLikeSymbol instanceof FirClassSymbol) {
            return getContainingDeclarationSymbol((FirClassSymbol) firClassLikeSymbol, context.getSession());
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirClassLikeSymbol<?> getContainingDeclarationSymbol(FirClassSymbol<?> firClassSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firClassSymbol.getClassId().isLocal()) {
            ConeClassLikeLookupTag containingClassForLocalAttr = ClassMembersKt.getContainingClassForLocalAttr((FirRegularClass) ((FirRegularClassSymbol) firClassSymbol).getFir());
            return containingClassForLocalAttr != null ? LookupTagUtilsKt.toFirRegularClassSymbol(containingClassForLocalAttr, session) : null;
        }
        FqName fqNameParent = firClassSymbol.getClassId().getRelativeClassName().parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        if (fqNameParent.isRoot()) {
            return null;
        }
        return FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(new ClassId(firClassSymbol.getClassId().getPackageFqName(), fqNameParent, false));
    }

    public static final FirClass findClosestClassOrObject(CheckerContext checkerContext) {
        Intrinsics.checkNotNullParameter(checkerContext, "<this>");
        for (FirDeclaration firDeclaration : CollectionsKt.asReversed(checkerContext.getContainingDeclarations())) {
            if ((firDeclaration instanceof FirRegularClass) || (firDeclaration instanceof FirAnonymousObject)) {
                return (FirClass) firDeclaration;
            }
        }
        return null;
    }

    public static final List<FirFunctionSymbol<?>> overriddenFunctions(FirNamedFunctionSymbol firNamedFunctionSymbol, FirClassSymbol<?> containingClass, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(context, "context");
        return ScopesKt.overriddenFunctions(firNamedFunctionSymbol, containingClass, context.getSession(), context.getScopeSession());
    }

    public static final Map<FirTypeRef, FirFieldSymbol> collectSupertypesWithDelegates(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Map<Integer, FirFieldSymbol> delegateFieldsMap = DelegateFieldsMapKt.getDelegateFieldsMap(firClass);
        if (delegateFieldsMap == null) {
            delegateFieldsMap = MapsKt.emptyMap();
        }
        List<FirTypeRef> superTypeRefs = firClass.getSuperTypeRefs();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypeRefs, 10));
        int i = 0;
        for (Object obj : superTypeRefs) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(TuplesKt.to((FirTypeRef) obj, delegateFieldsMap.get(Integer.valueOf(i))));
            i = i2;
        }
        return MapsKt.toMap(arrayList);
    }

    public static final Modality modality(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        if (!(firClass instanceof FirRegularClass)) {
            return Modality.FINAL;
        }
        return firClass.getStatus().getModality();
    }

    public static final Set<Modality> redundantModalities(FirMemberDeclaration firMemberDeclaration, CheckerContext context) {
        Modality modality;
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (firMemberDeclaration instanceof FirRegularClass) {
            return WhenMappings.$EnumSwitchMapping$0[((FirRegularClass) firMemberDeclaration).getClassKind().ordinal()] == 1 ? SetsKt.setOf((Object[]) new Modality[]{Modality.ABSTRACT, Modality.OPEN}) : SetsKt.setOf(Modality.FINAL);
        }
        FirClass firClassFindClosestClassOrObject = findClosestClassOrObject(context);
        if (firClassFindClosestClassOrObject == null) {
            return SetsKt.setOf(Modality.FINAL);
        }
        if (firMemberDeclaration.getStatus().isOverride() && (modality = firClassFindClosestClassOrObject.getStatus().getModality()) != null && modality != Modality.FINAL) {
            return SetsKt.setOf(Modality.OPEN);
        }
        if (firClassFindClosestClassOrObject.getClassKind() == ClassKind.INTERFACE) {
            return hasBody(firMemberDeclaration) ? SetsKt.setOf(Modality.OPEN) : SetsKt.setOf((Object[]) new Modality[]{Modality.ABSTRACT, Modality.OPEN});
        }
        return SetsKt.setOf(Modality.FINAL);
    }

    private static final boolean hasBody(FirDeclaration firDeclaration) {
        if (firDeclaration instanceof FirSimpleFunction) {
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firDeclaration;
            if (firSimpleFunction.getBody() == null || (firSimpleFunction.getBody() instanceof FirEmptyExpressionBlock)) {
                return false;
            }
        } else {
            if (!(firDeclaration instanceof FirProperty)) {
                return false;
            }
            FirProperty firProperty = (FirProperty) firDeclaration;
            FirPropertyAccessor setter = firProperty.getSetter();
            FirBlock body = setter != null ? setter.getBody() : null;
            if (body == null ? true : body instanceof FirEmptyExpressionBlock) {
                FirPropertyAccessor getter = firProperty.getGetter();
                FirBlock body2 = getter != null ? getter.getBody() : null;
                if (body2 == null ? true : body2 instanceof FirEmptyExpressionBlock) {
                    return false;
                }
            }
        }
        return true;
    }

    public static final FirTypeRef findNonInterfaceSupertype(FirClass firClass, CheckerContext context) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Iterator<FirTypeRef> it = firClass.getSuperTypeRefs().iterator();
        while (true) {
            if (!it.hasNext()) {
                return null;
            }
            FirTypeRef next = it.next();
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(next);
            ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(lookupTag, context.getSession());
                FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
                if (firClassSymbol != null && firClassSymbol.getClassKind() != ClassKind.INTERFACE) {
                    return next;
                }
            }
        }
    }

    public static final boolean isIterator(FirFunctionCall firFunctionCall) {
        Intrinsics.checkNotNullParameter(firFunctionCall, "<this>");
        return Intrinsics.areEqual(firFunctionCall.getCalleeReference().getName(), SpecialNames.ITERATOR);
    }

    public static final boolean isSubtypeOfThrowable(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return isSupertypeOf(session.getBuiltinTypes().getThrowableType().getType(), TypeComponentsKt.getTypeContext(session), TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, session));
    }

    public static final boolean getHasValOrVar(FirValueParameter firValueParameter) {
        Intrinsics.checkNotNullParameter(firValueParameter, "<this>");
        KtSourceElement source = firValueParameter.getSource();
        if (source == null) {
            return false;
        }
        TokenSet VAL_VAR_TOKEN_SET = KtParameter.VAL_VAR_TOKEN_SET;
        Intrinsics.checkNotNullExpressionValue(VAL_VAR_TOKEN_SET, "VAL_VAR_TOKEN_SET");
        return FirSourceUtilsKt.getChild$default(source, VAL_VAR_TOKEN_SET, 0, 0, false, 14, (Object) null) != null;
    }

    public static final boolean isSupertypeOf(KotlinTypeMarker kotlinTypeMarker, TypeCheckerProviderContext context, KotlinTypeMarker kotlinTypeMarker2) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return kotlinTypeMarker2 != null && AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, context, kotlinTypeMarker2, kotlinTypeMarker, false, 8, (Object) null);
    }

    public static final boolean isSubtypeForTypeMismatch(ConeInferenceContext context, ConeKotlinType subtype, ConeKotlinType supertype) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) context, (KotlinTypeMarker) TypeExpansionUtilsKt.fullyExpandedType(subtype, context.get$session()), (KotlinTypeMarker) TypeExpansionUtilsKt.fullyExpandedType(supertype, context.get$session()), false, 8, (Object) null);
    }

    public static final boolean isVisibleInClass(FirCallableDeclaration firCallableDeclaration, FirClass parentClass) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(parentClass, "parentClass");
        return isVisibleInClass(firCallableDeclaration.getSymbol(), parentClass.getSymbol());
    }

    public static final boolean isVisibleInClass(FirCallableSymbol<?> firCallableSymbol, FirClassSymbol<?> parentClassSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        Intrinsics.checkNotNullParameter(parentClassSymbol, "parentClassSymbol");
        FqName packageFqName = parentClassSymbol.getClassId().getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        if (Intrinsics.areEqual(firCallableSymbol.getResolvedStatus().getVisibility(), Visibilities.Private.INSTANCE) || !firCallableSymbol.getResolvedStatus().getVisibility().visibleFromPackage(packageFqName, firCallableSymbol.getCallableId().getPackageName())) {
            return false;
        }
        if (Intrinsics.areEqual(firCallableSymbol.getResolvedStatus().getVisibility(), Visibilities.Internal.INSTANCE)) {
            return Intrinsics.areEqual(firCallableSymbol.getModuleData(), parentClassSymbol.getModuleData()) && !firCallableSymbol.getModuleData().getFriendDependencies().contains(parentClassSymbol.getModuleData());
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0167, code lost:
    
        if (org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.isJavaOrEnhancement(r1) == true) goto L102;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final org.jetbrains.kotlin.util.ImplementationStatus getImplementationStatus(org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<?> r17, org.jetbrains.kotlin.fir.resolve.SessionHolder r18, org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol<?> r19) {
        /*
            Method dump skipped, instruction units count: 429
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt.getImplementationStatus(org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol, org.jetbrains.kotlin.fir.resolve.SessionHolder, org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol):org.jetbrains.kotlin.util.ImplementationStatus");
    }

    private static final boolean subjectToManyNotImplemented(FirIntersectionCallableSymbol firIntersectionCallableSymbol, SessionHolder sessionHolder) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (FirCallableSymbol<?> firCallableSymbol : firIntersectionCallableSymbol.getIntersections()) {
            FirClassLikeSymbol<?> containingClassSymbol = getContainingClassSymbol(firCallableSymbol, sessionHolder.getSession());
            FirRegularClassSymbol firRegularClassSymbol = containingClassSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) containingClassSymbol : null;
            boolean z = (firRegularClassSymbol != null ? firRegularClassSymbol.getClassKind() : null) == ClassKind.INTERFACE;
            if (firCallableSymbol.getResolvedStatus().getModality() != Modality.ABSTRACT) {
                if (z) {
                    i3++;
                } else {
                    i2++;
                }
            } else if (z) {
                i++;
            }
            if (i2 + i3 > 1) {
                return true;
            }
            if (i3 > 0 && i > 0) {
                return true;
            }
        }
        return false;
    }

    private static final boolean getMatchesDataClassSyntheticMemberSignatures(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Name callableName = firNamedFunctionSymbol.getCallableId().getCallableName();
        return (Intrinsics.areEqual(callableName, OperatorNameConventions.EQUALS) && getMatchesEqualsSignature(firNamedFunctionSymbol)) || (Intrinsics.areEqual(callableName, StandardNames.HASHCODE_NAME) && getMatchesHashCodeSignature(firNamedFunctionSymbol)) || (Intrinsics.areEqual(callableName, OperatorNameConventions.TO_STRING) && getMatchesToStringSignature(firNamedFunctionSymbol));
    }

    private static final boolean getMatchesEqualsSignature(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        List<FirValueParameterSymbol> valueParameterSymbols = firNamedFunctionSymbol.getValueParameterSymbols();
        return valueParameterSymbols.size() == 1 && ConeBuiltinTypeUtilsKt.isNullableAny(FirTypeUtilsKt.getConeType(valueParameterSymbols.get(0).getResolvedReturnTypeRef()));
    }

    private static final boolean getMatchesHashCodeSignature(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        return firNamedFunctionSymbol.getValueParameterSymbols().isEmpty();
    }

    private static final boolean getMatchesToStringSignature(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        return firNamedFunctionSymbol.getValueParameterSymbols().isEmpty();
    }

    public static final boolean isDelegated(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<this>");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return StringsKt.startsWith$default(strAsString, JvmAbi.DELEGATE_SUPER_FIELD_PREFIX, false, 2, (Object) null);
    }

    public static final boolean isConflictingOrNotInvariant(ConeTypeProjection coneTypeProjection) {
        Intrinsics.checkNotNullParameter(coneTypeProjection, "<this>");
        return coneTypeProjection.getKind() != ProjectionKind.INVARIANT || (coneTypeProjection instanceof ConeKotlinTypeConflictingProjection);
    }

    public static final void checkTypeMismatch(ConeKotlinType lValueOriginalType, FirVariableAssignment firVariableAssignment, FirExpression rValue, CheckerContext context, KtSourceElement source, DiagnosticReporter reporter, boolean z) {
        ConeKotlinType coneKotlinType;
        ConeKotlinType coneKotlinType2;
        KtSourceElement source2;
        ProjectionKind kind;
        FirReference calleeReference;
        Intrinsics.checkNotNullParameter(lValueOriginalType, "lValueOriginalType");
        Intrinsics.checkNotNullParameter(rValue, "rValue");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(rValue.getTypeRef());
        if ((source.getKind() instanceof KtFakeSourceElementKind.DesugaredIncrementOrDecrement) && !ConeTypeUtilsKt.isNullable(lValueOriginalType) && ConeTypeUtilsKt.isNullable(coneType)) {
            coneKotlinType2 = coneType;
            coneKotlinType = lValueOriginalType;
        } else {
            coneKotlinType = coneType;
            coneKotlinType2 = lValueOriginalType;
        }
        if (isSubtypeForTypeMismatch(TypeComponentsKt.getTypeContext(context.getSession()), coneKotlinType, coneKotlinType2)) {
            return;
        }
        KtSourceElementKind kind2 = null;
        FirCallableSymbol resolvedCallableSymbol$default = (firVariableAssignment == null || (calleeReference = FirExpressionUtilKt.getCalleeReference(firVariableAssignment)) == null) ? null : FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null);
        FirPropertySymbol firPropertySymbol = resolvedCallableSymbol$default instanceof FirPropertySymbol ? (FirPropertySymbol) resolvedCallableSymbol$default : null;
        if (firPropertySymbol != null && (coneKotlinType2 instanceof ConeCapturedType) && ((kind = ((ConeCapturedType) coneKotlinType2).getConstructor().getProjection().getKind()) == ProjectionKind.STAR || kind == ProjectionKind.OUT)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firVariableAssignment.getSource(), FirErrors.INSTANCE.getSETTER_PROJECTED_OUT(), firPropertySymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        if (FirTypeUtilsKt.isNullLiteral(rValue) && coneKotlinType2.getNullability() == ConeNullability.NOT_NULL) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, rValue.getSource(), FirErrors.INSTANCE.getNULL_FOR_NONNULL_TYPE(), coneKotlinType2, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        if (z) {
            KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) source, (KtDiagnosticFactory3<ConeKotlinType, ConeKotlinType, Boolean>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getINITIALIZER_TYPE_MISMATCH()), coneKotlinType2, coneKotlinType, Boolean.valueOf(InferenceUtilsKt.isTypeMismatchDueToNullability(TypeComponentsKt.getTypeContext(context.getSession()), coneKotlinType, coneKotlinType2)), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
            return;
        }
        if (!(source.getKind() instanceof KtFakeSourceElementKind.DesugaredIncrementOrDecrement)) {
            if (firVariableAssignment != null && (source2 = firVariableAssignment.getSource()) != null) {
                kind2 = source2.getKind();
            }
            if (!(kind2 instanceof KtFakeSourceElementKind.DesugaredIncrementOrDecrement)) {
                KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) source, (KtDiagnosticFactory3<ConeKotlinType, ConeKotlinType, Boolean>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getASSIGNMENT_TYPE_MISMATCH()), coneKotlinType2, coneKotlinType, Boolean.valueOf(InferenceUtilsKt.isTypeMismatchDueToNullability(TypeComponentsKt.getTypeContext(context.getSession()), coneKotlinType, coneKotlinType2)), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
                return;
            }
        }
        if (!ConeTypeUtilsKt.isNullable(coneKotlinType2) && ConeTypeUtilsKt.isNullable(coneKotlinType)) {
            ConeKotlinType coneKotlinType3 = coneKotlinType;
            coneKotlinType = coneKotlinType2;
            coneKotlinType2 = coneKotlinType3;
        }
        if (ConeBuiltinTypeUtilsKt.isUnit(coneKotlinType)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getINC_DEC_SHOULD_NOT_RETURN_UNIT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        } else {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getRESULT_TYPE_MISMATCH(), coneKotlinType2, coneKotlinType, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }

    public static final void checkCondition(FirExpression condition, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(condition.getTypeRef()));
        if ((coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeErrorType) || TypeUtilsKt.isSubtypeOf(coneSimpleKotlinTypeLowerBoundIfFlexible, TypeComponentsKt.getTypeContext(context.getSession()), context.getSession().getBuiltinTypes().getBooleanType().getType())) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, condition.getSource(), FirErrors.INSTANCE.getCONDITION_TYPE_MISMATCH(), coneSimpleKotlinTypeLowerBoundIfFlexible, Boolean.valueOf(ConeBuiltinTypeUtilsKt.isNullableBoolean(coneSimpleKotlinTypeLowerBoundIfFlexible)), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    public static final List<FirTypeRefSource> extractArgumentsTypeRefAndSource(FirTypeRef firTypeRef) {
        if (!(firTypeRef instanceof FirResolvedTypeRef)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        FirTypeRef delegatedTypeRef = ((FirResolvedTypeRef) firTypeRef).getDelegatedTypeRef();
        if (delegatedTypeRef instanceof FirUserTypeRef) {
            List<FirQualifierPart> qualifier = ((FirUserTypeRef) delegatedTypeRef).getQualifier();
            int size = qualifier.size();
            while (true) {
                size--;
                if (-1 >= size) {
                    break;
                }
                for (FirTypeProjection firTypeProjection : qualifier.get(size).getTypeArgumentList().getTypeArguments()) {
                    FirTypeProjectionWithVariance firTypeProjectionWithVariance = firTypeProjection instanceof FirTypeProjectionWithVariance ? (FirTypeProjectionWithVariance) firTypeProjection : null;
                    arrayList.add(new FirTypeRefSource(firTypeProjectionWithVariance != null ? firTypeProjectionWithVariance.getTypeRef() : null, firTypeProjection.getSource()));
                }
            }
        } else {
            if (!(delegatedTypeRef instanceof FirFunctionTypeRef)) {
                return null;
            }
            FirFunctionTypeRef firFunctionTypeRef = (FirFunctionTypeRef) delegatedTypeRef;
            List<FirFunctionTypeParameter> parameters = firFunctionTypeRef.getParameters();
            FirTypeRef receiverTypeRef = firFunctionTypeRef.getReceiverTypeRef();
            if (receiverTypeRef != null) {
                arrayList.add(new FirTypeRefSource(receiverTypeRef, receiverTypeRef.getSource()));
            }
            Iterator<FirFunctionTypeParameter> it = parameters.iterator();
            while (it.hasNext()) {
                FirTypeRef returnTypeRef = it.next().getReturnTypeRef();
                arrayList.add(new FirTypeRefSource(returnTypeRef, returnTypeRef.getSource()));
            }
            FirTypeRef returnTypeRef2 = firFunctionTypeRef.getReturnTypeRef();
            arrayList.add(new FirTypeRefSource(returnTypeRef2, returnTypeRef2.getSource()));
        }
        return arrayList;
    }

    public static final ClassKind getClassKind(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        FirClassSymbol firClassSymbol = firClassLikeSymbol instanceof FirClassSymbol ? (FirClassSymbol) firClassLikeSymbol : null;
        if (firClassSymbol != null) {
            return firClassSymbol.getClassKind();
        }
        return null;
    }

    public static final List<FirTypeParameterSymbol> getTypeParameterSymbols(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        if (firBasedSymbol instanceof FirCallableSymbol) {
            return ((FirCallableSymbol) firBasedSymbol).getTypeParameterSymbols();
        }
        if (firBasedSymbol instanceof FirClassLikeSymbol) {
            return ((FirClassLikeSymbol) firBasedSymbol).getTypeParameterSymbols();
        }
        return null;
    }

    public static final boolean isFunctionForExpectTypeFromCastFeature(FirFunctionSymbol<?> firFunctionSymbol) {
        FirResolvedTypeRef resolvedReceiverTypeRef;
        Intrinsics.checkNotNullParameter(firFunctionSymbol, "<this>");
        FirTypeParameterSymbol firTypeParameterSymbol = (FirTypeParameterSymbol) CollectionsKt.singleOrNull((List) firFunctionSymbol.getTypeParameterSymbols());
        if (firTypeParameterSymbol == null) {
            return false;
        }
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(firFunctionSymbol.getResolvedReturnTypeRef()));
        ConeTypeParameterType coneTypeParameterType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeTypeParameterType ? (ConeTypeParameterType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
        if (!Intrinsics.areEqual(coneTypeParameterType != null ? coneTypeParameterType.getLookupTag() : null, firTypeParameterSymbol.getLookupTag())) {
            return false;
        }
        List<FirValueParameterSymbol> valueParameterSymbols = firFunctionSymbol.getValueParameterSymbols();
        if ((valueParameterSymbols instanceof Collection) && valueParameterSymbols.isEmpty()) {
            resolvedReceiverTypeRef = firFunctionSymbol.getResolvedReceiverTypeRef();
            if (resolvedReceiverTypeRef != null) {
            }
            return true;
        }
        Iterator<T> it = valueParameterSymbols.iterator();
        while (it.hasNext()) {
            if (isFunctionForExpectTypeFromCastFeature$isBadType(((FirValueParameterSymbol) it.next()).getResolvedReturnTypeRef(), firTypeParameterSymbol)) {
            }
        }
        resolvedReceiverTypeRef = firFunctionSymbol.getResolvedReceiverTypeRef();
        if (resolvedReceiverTypeRef != null || !isFunctionForExpectTypeFromCastFeature$isBadType(resolvedReceiverTypeRef, firTypeParameterSymbol)) {
            return true;
        }
        return false;
    }

    private static final boolean isFunctionForExpectTypeFromCastFeature$isBadType(FirTypeRef firTypeRef, final FirTypeParameterSymbol firTypeParameterSymbol) {
        return ConeTypeUtilsKt.contains(FirTypeUtilsKt.getConeType(firTypeRef), new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt$isFunctionForExpectTypeFromCastFeature$isBadType$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(it);
                ConeTypeParameterType coneTypeParameterType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeTypeParameterType ? (ConeTypeParameterType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
                return Boolean.valueOf(Intrinsics.areEqual(coneTypeParameterType != null ? coneTypeParameterType.getLookupTag() : null, firTypeParameterSymbol.getLookupTag()));
            }
        });
    }

    private static final boolean isMember(FirCallableDeclaration firCallableDeclaration) {
        return firCallableDeclaration.getDispatchReceiverType() != null;
    }

    public static final AnnotationTargetList getActualTargetList(FirAnnotationContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        if ((container instanceof FirBackingField) && !DeclarationAttributesKt.getHasBackingField(((FirBackingField) container).getPropertySymbol())) {
            container = ClassMembersKt.getPropertyIfBackingField((FirCallableDeclaration) container);
        }
        if (container instanceof FirRegularClass) {
            FirRegularClass firRegularClass = (FirRegularClass) container;
            return new AnnotationTargetList(KotlinTarget.INSTANCE.classActualTargets(firRegularClass.getClassKind(), ((FirMemberDeclaration) container).getStatus().isInner(), firRegularClass.getStatus().isCompanion(), ((FirClassLikeDeclaration) container).getSymbol().getClassId().isLocal()), null, null, 6, null);
        }
        if (container instanceof FirEnumEntry) {
            return new AnnotationTargetList(KotlinTarget.INSTANCE.classActualTargets(ClassKind.ENUM_ENTRY, ((FirMemberDeclaration) container).getStatus().isInner(), false, false), null, null, 6, null);
        }
        if (container instanceof FirProperty) {
            FirProperty firProperty = (FirProperty) container;
            if (firProperty.isLocal()) {
                KtSourceElement source = container.getSource();
                if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DesugaredComponentFunctionCall.INSTANCE)) {
                    return AnnotationTargetLists.INSTANCE.getT_DESTRUCTURING_DECLARATION();
                }
                return AnnotationTargetLists.INSTANCE.getT_LOCAL_VARIABLE();
            }
            if (!isMember((FirCallableDeclaration) container)) {
                return AnnotationTargetLists.INSTANCE.T_TOP_LEVEL_PROPERTY(DeclarationAttributesKt.getHasBackingField(firProperty), firProperty.getDelegate() != null);
            }
            KtSourceElement source2 = container.getSource();
            if (Intrinsics.areEqual(source2 != null ? source2.getKind() : null, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE)) {
                return AnnotationTargetLists.INSTANCE.getT_VALUE_PARAMETER_WITH_VAL();
            }
            return AnnotationTargetLists.INSTANCE.T_MEMBER_PROPERTY(DeclarationAttributesKt.getHasBackingField(firProperty), firProperty.getDelegate() != null);
        }
        if (container instanceof FirValueParameter) {
            return getHasValOrVar((FirValueParameter) container) ? AnnotationTargetLists.INSTANCE.getT_VALUE_PARAMETER_WITH_VAL() : AnnotationTargetLists.INSTANCE.getT_VALUE_PARAMETER_WITHOUT_VAL();
        }
        if (container instanceof FirConstructor) {
            return AnnotationTargetLists.INSTANCE.getT_CONSTRUCTOR();
        }
        if (container instanceof FirAnonymousFunction) {
            return AnnotationTargetLists.INSTANCE.getT_FUNCTION_EXPRESSION();
        }
        if (!(container instanceof FirSimpleFunction)) {
            if (container instanceof FirTypeAlias) {
                return AnnotationTargetLists.INSTANCE.getT_TYPEALIAS();
            }
            if (container instanceof FirPropertyAccessor) {
                return ((FirPropertyAccessor) container).getIsGetter() ? AnnotationTargetLists.INSTANCE.getT_PROPERTY_GETTER() : AnnotationTargetLists.INSTANCE.getT_PROPERTY_SETTER();
            }
            if (container instanceof FirBackingField) {
                return AnnotationTargetLists.INSTANCE.getT_BACKING_FIELD();
            }
            if (container instanceof FirFile) {
                return AnnotationTargetLists.INSTANCE.getT_FILE();
            }
            if (container instanceof FirTypeParameter) {
                return AnnotationTargetLists.INSTANCE.getT_TYPE_PARAMETER();
            }
            if (container instanceof FirReceiverParameter) {
                return AnnotationTargetLists.INSTANCE.getT_TYPE_REFERENCE();
            }
            if (container instanceof FirAnonymousInitializer) {
                return AnnotationTargetLists.INSTANCE.getT_INITIALIZER();
            }
            if (container instanceof FirAnonymousObject) {
                KtSourceElement source3 = container.getSource();
                if (Intrinsics.areEqual(source3 != null ? source3.getKind() : null, KtFakeSourceElementKind.EnumInitializer.INSTANCE)) {
                    return new AnnotationTargetList(KotlinTarget.INSTANCE.classActualTargets(ClassKind.ENUM_ENTRY, false, false, false), null, null, 6, null);
                }
                return AnnotationTargetLists.INSTANCE.getT_OBJECT_LITERAL();
            }
            return AnnotationTargetLists.INSTANCE.getEMPTY();
        }
        if (Intrinsics.areEqual(((FirSimpleFunction) container).getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
            return AnnotationTargetLists.INSTANCE.getT_LOCAL_FUNCTION();
        }
        return isMember((FirCallableDeclaration) container) ? AnnotationTargetLists.INSTANCE.getT_MEMBER_FUNCTION() : AnnotationTargetLists.INSTANCE.getT_TOP_LEVEL_FUNCTION();
    }

    public static final boolean explicitReceiverIsNotSuperReference(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        Intrinsics.checkNotNullParameter(firQualifiedAccessExpression, "<this>");
        FirExpression explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver();
        FirQualifiedAccessExpression firQualifiedAccessExpression2 = explicitReceiver instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) explicitReceiver : null;
        return !((firQualifiedAccessExpression2 != null ? firQualifiedAccessExpression2.getCalleeReference() : null) instanceof FirSuperReference);
    }

    public static final KtSourceElement getDefaultValueForParameter(KtSourceElement ktSourceElement) {
        KtExpression defaultValue;
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        if (ktSourceElement instanceof KtPsiSourceElement) {
            PsiElement psi = ((KtPsiSourceElement) ktSourceElement).getPsi();
            KtFakeSourceElement ktFakeSourceElement = null;
            KtParameter ktParameter = psi instanceof KtParameter ? (KtParameter) psi : null;
            if (ktParameter != null && (defaultValue = ktParameter.getDefaultValue()) != null) {
                KtExpression ktExpression = defaultValue;
                Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                if (obj instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement = new KtRealPsiSourceElement(ktExpression);
                } else {
                    if (!(obj instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement = new KtFakeSourceElement(ktExpression, (KtFakeSourceElementKind) obj);
                }
            }
            return ktFakeSourceElement;
        }
        if (ktSourceElement instanceof KtLightSourceElement) {
            return findDefaultValue((KtLightSourceElement) ktSourceElement);
        }
        throw new NoWhenBranchMatchedException();
    }

    private static final KtLightSourceElement findDefaultValue(KtLightSourceElement ktLightSourceElement) {
        LighterASTNode lighterASTNode;
        int startOffset = ktLightSourceElement.getStartOffset();
        Iterator<LighterASTNode> it = LightTreeUtilsKt.getChildren(ktLightSourceElement.getLighterASTNode(), ktLightSourceElement.getTreeStructure()).iterator();
        int endOffset = startOffset;
        while (true) {
            if (!it.hasNext()) {
                lighterASTNode = null;
                break;
            }
            LighterASTNode next = it.next();
            if (LightTreePositioningStrategiesKt.isExpression(next)) {
                lighterASTNode = next;
                break;
            }
            endOffset += next.getEndOffset() - next.getStartOffset();
        }
        if (lighterASTNode == null) {
            return null;
        }
        return new KtLightSourceElement(lighterASTNode, endOffset, endOffset + lighterASTNode.getTextLength(), ktLightSourceElement.getTreeStructure(), KtRealSourceElementKind.INSTANCE);
    }

    public static final ConeKotlinType getInlineClassUnderlyingType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!isSingleFieldValueClass(coneKotlinType, session)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneKotlinType, session);
        Intrinsics.checkNotNull(regularClassSymbol);
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorSymbol = org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationUtilsKt.primaryConstructorSymbol(regularClassSymbol);
        Intrinsics.checkNotNull(firConstructorSymbolPrimaryConstructorSymbol);
        return FirTypeUtilsKt.getConeType(firConstructorSymbolPrimaryConstructorSymbol.getValueParameterSymbols().get(0).getResolvedReturnTypeRef());
    }

    public static final List<FirNamedFunctionSymbol> directOverriddenFunctions(FirNamedFunctionSymbol firNamedFunctionSymbol, FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirClassLikeSymbol<?> containingClassSymbol = getContainingClassSymbol(firNamedFunctionSymbol, session);
        FirClassSymbol firClassSymbol = containingClassSymbol instanceof FirClassSymbol ? (FirClassSymbol) containingClassSymbol : null;
        if (firClassSymbol == null) {
            return CollectionsKt.emptyList();
        }
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) firClassSymbol, session, scopeSession, false, FirResolvePhase.STATUS);
        firTypeScopeUnsubstitutedScope.processFunctionsByName(firNamedFunctionSymbol.getName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt.directOverriddenFunctions.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol2) {
                invoke2(firNamedFunctionSymbol2);
                return Unit.INSTANCE;
            }
        });
        return FirTypeScopeKt.getDirectOverriddenFunctions(firTypeScopeUnsubstitutedScope, firNamedFunctionSymbol, true);
    }

    public static final List<FirNamedFunctionSymbol> directOverriddenFunctions(FirNamedFunctionSymbol firNamedFunctionSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return directOverriddenFunctions(firNamedFunctionSymbol, context.getSession(), context.getSessionHolder().getScopeSession());
    }

    public static final FirDeclaration getClosestNonLocal(CheckerContext checkerContext) {
        Intrinsics.checkNotNullParameter(checkerContext, "<this>");
        List<FirDeclaration> containingDeclarations = checkerContext.getContainingDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : containingDeclarations) {
            if (!FirDeclarationUtilKt.isNonLocal((FirDeclaration) obj)) {
                break;
            }
            arrayList.add(obj);
        }
        return (FirDeclaration) CollectionsKt.lastOrNull((List) arrayList);
    }

    public static final FirDeclaration closestNonLocalWith(CheckerContext checkerContext, FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(checkerContext, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        List listPlus = CollectionsKt.plus((Collection<? extends FirDeclaration>) checkerContext.getContainingDeclarations(), declaration);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listPlus) {
            if (!FirDeclarationUtilKt.isNonLocal((FirDeclaration) obj)) {
                break;
            }
            arrayList.add(obj);
        }
        return (FirDeclaration) CollectionsKt.lastOrNull((List) arrayList);
    }

    public static final boolean isTopLevel(CheckerContext checkerContext) {
        Intrinsics.checkNotNullParameter(checkerContext, "<this>");
        FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) checkerContext.getContainingDeclarations());
        return (firDeclaration instanceof FirFile) || (firDeclaration instanceof FirScript);
    }

    public static final boolean hasAnnotationOrInsideAnnotatedClass(FirBasedSymbol<?> firBasedSymbol, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        if (FirAnnotationUtilsKt.hasAnnotation(firBasedSymbol, classId, session)) {
            return true;
        }
        FirClassLikeSymbol<?> containingClassSymbol = getContainingClassSymbol(firBasedSymbol, session);
        if (containingClassSymbol == null) {
            return false;
        }
        return hasAnnotationOrInsideAnnotatedClass(containingClassSymbol, classId, session);
    }

    public static final boolean hasAnnotationOrInsideAnnotatedClass(FirDeclaration firDeclaration, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return hasAnnotationOrInsideAnnotatedClass(firDeclaration.getSymbol(), classId, session);
    }

    public static final FirExpression getAnnotationFirstArgument(FirBasedSymbol<?> firBasedSymbol, ClassId classId, FirSession session) {
        FirAnnotationArgumentMapping argumentMapping;
        Map<Name, FirExpression> mapping;
        Collection<FirExpression> collectionValues;
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firBasedSymbol, classId, session);
        FirAnnotationCall firAnnotationCall = annotationByClassId instanceof FirAnnotationCall ? (FirAnnotationCall) annotationByClassId : null;
        if (firAnnotationCall == null || (argumentMapping = firAnnotationCall.getArgumentMapping()) == null || (mapping = argumentMapping.getMapping()) == null || (collectionValues = mapping.values()) == null) {
            return null;
        }
        return (FirExpression) CollectionsKt.firstOrNull(collectionValues);
    }

    public static final String getAnnotationStringParameter(FirBasedSymbol<?> firBasedSymbol, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        FirExpression annotationFirstArgument = getAnnotationFirstArgument(firBasedSymbol, classId, session);
        FirConstExpression firConstExpression = annotationFirstArgument instanceof FirConstExpression ? (FirConstExpression) annotationFirstArgument : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public static final boolean isLhsOfAssignment(FirElement firElement, CheckerContext context) {
        FirStatement firStatementPrevious;
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(firElement instanceof FirQualifiedAccessExpression)) {
            return false;
        }
        List<FirStatement> callsOrAssignments = context.getCallsOrAssignments();
        ListIterator<FirStatement> listIterator = callsOrAssignments.listIterator(callsOrAssignments.size());
        do {
            if (!listIterator.hasPrevious()) {
                firStatementPrevious = null;
                break;
            }
            firStatementPrevious = listIterator.previous();
        } while (Intrinsics.areEqual(firStatementPrevious, firElement));
        FirStatement firStatement = firStatementPrevious;
        return firStatement != null && (firStatement instanceof FirVariableAssignment) && Intrinsics.areEqual(((FirVariableAssignment) firStatement).getLValue(), firElement);
    }

    public static final Set<ConeClassLikeType> collectUpperBounds(ConeKotlinType coneKotlinType) {
        Set<ConeClassLikeType> set;
        if (coneKotlinType == null) {
            return SetsKt.emptySet();
        }
        if (coneKotlinType instanceof ConeErrorType) {
            return SetsKt.emptySet();
        }
        if (coneKotlinType instanceof ConeLookupTagBasedType) {
            if (coneKotlinType instanceof ConeClassLikeType) {
                return SetsKt.setOf(coneKotlinType);
            }
            if (coneKotlinType instanceof ConeTypeVariableType) {
                TypeParameterMarker originalTypeParameter = ((ConeTypeVariableType) coneKotlinType).getLookupTag().getOriginalTypeParameter();
                ConeTypeParameterLookupTag coneTypeParameterLookupTag = originalTypeParameter instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) originalTypeParameter : null;
                return collectUpperBounds(coneTypeParameterLookupTag != null ? coneTypeParameterLookupTag.getTypeParameterSymbol() : null);
            }
            if (coneKotlinType instanceof ConeTypeParameterType) {
                return collectUpperBounds(((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol());
            }
            throw new IllegalStateException("missing branch for " + coneKotlinType.getClass().getName());
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return collectUpperBounds(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal());
        }
        if (!(coneKotlinType instanceof ConeIntersectionType)) {
            if (coneKotlinType instanceof ConeFlexibleType) {
                return collectUpperBounds(((ConeFlexibleType) coneKotlinType).getUpperBound());
            }
            if (!(coneKotlinType instanceof ConeCapturedType)) {
                if (coneKotlinType instanceof ConeIntegerConstantOperatorType) {
                    return SetsKt.setOf(ConeIntegerLiteralType.getApproximatedType$default((ConeIntegerLiteralType) coneKotlinType, null, 1, null));
                }
                if (coneKotlinType instanceof ConeStubType ? true : coneKotlinType instanceof ConeIntegerLiteralConstantType) {
                    throw new IllegalStateException(coneKotlinType + " should not reach here");
                }
                throw new NoWhenBranchMatchedException();
            }
            List<ConeKotlinType> supertypes = ((ConeCapturedType) coneKotlinType).getConstructor().getSupertypes();
            if (supertypes != null) {
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = supertypes.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, collectUpperBounds((ConeKotlinType) it.next()));
                }
                set = CollectionsKt.toSet(arrayList);
            } else {
                set = null;
            }
            return set == null ? SetsKt.emptySet() : set;
        }
        Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = intersectedTypes.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, collectUpperBounds((ConeKotlinType) it2.next()));
        }
        return CollectionsKt.toSet(arrayList2);
    }

    private static final Set<ConeClassLikeType> collectUpperBounds(FirTypeParameterSymbol firTypeParameterSymbol) {
        if (firTypeParameterSymbol == null) {
            return SetsKt.emptySet();
        }
        List<FirResolvedTypeRef> resolvedBounds = firTypeParameterSymbol.getResolvedBounds();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = resolvedBounds.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, collectUpperBounds(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next())));
        }
        return CollectionsKt.toSet(arrayList);
    }

    public static final ConeKotlinType leastUpperBound(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Set<ConeClassLikeType> setCollectUpperBounds = collectUpperBounds(coneKotlinType);
        if (setCollectUpperBounds.isEmpty()) {
            setCollectUpperBounds = null;
        }
        return setCollectUpperBounds == null ? session.getBuiltinTypes().getNullableAnyType().getType() : ConeTypeIntersector.INSTANCE.intersectTypes(TypeComponentsKt.getTypeContext(session), setCollectUpperBounds);
    }

    public static final FirClassLikeSymbol<?> toClassLikeSymbol(FirTypeRef firTypeRef, FirSession session) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType != null) {
            return TypeUtilsKt.toSymbol(coneClassLikeType, session);
        }
        return null;
    }

    public static final boolean isInlineOnly(FirMemberDeclaration firMemberDeclaration, FirSession session) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return firMemberDeclaration.getStatus().isInline() && FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) firMemberDeclaration, INLINE_ONLY_ANNOTATION_CLASS_ID, session);
    }
}
