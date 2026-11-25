package org.jetbrains.kotlin.fir.lightTree.converter;

import androidx.exifinterface.media.ExifInterface;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.ElementTypeUtils;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.EnumClassUtilsKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;
import org.jetbrains.kotlin.fir.FirFunctionTarget;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirPackageDirective;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder;
import org.jetbrains.kotlin.fir.builder.Context;
import org.jetbrains.kotlin.fir.builder.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.builder.FirFileAnnotationsContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirFunctionTypeParameterBuilder;
import org.jetbrains.kotlin.fir.builder.FirPackageDirectiveBuilder;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.builder.FirRawContractDescriptionBuilder;
import org.jetbrains.kotlin.fir.declarations.DelegateFieldsMapKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousInitializerBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousObjectBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirBackingFieldBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirDanglingModifierListBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirDefaultSetterValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirEnumEntryBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirErrorConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFileBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirImportBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPrimaryConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyAccessorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeAliasBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.utils.DanglingTypeConstraint;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationBuildingUtilsKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeDanglingModifierOnTopLevel;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnonymousFunctionExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnonymousObjectExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBlockBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirDelegatedConstructorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirEmptyExpressionBlockBuilderKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorAnnotationCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirMultiDelegatedConstructorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWrappedDelegateExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.lightTree.fir.ClassWrapper;
import org.jetbrains.kotlin.fir.lightTree.fir.DelegatedConstructorWrapper;
import org.jetbrains.kotlin.fir.lightTree.fir.DestructuringDeclaration;
import org.jetbrains.kotlin.fir.lightTree.fir.PrimaryConstructor;
import org.jetbrains.kotlin.fir.lightTree.fir.TypeConstraint;
import org.jetbrains.kotlin.fir.lightTree.fir.ValueParameter;
import org.jetbrains.kotlin.fir.lightTree.fir.modifier.Modifier;
import org.jetbrains.kotlin.fir.lightTree.fir.modifier.TypeParameterModifier;
import org.jetbrains.kotlin.fir.lightTree.fir.modifier.TypeProjectionModifier;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitSuperReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitThisReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirSimpleNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirDanglingModifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFileSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeArgumentList;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirDynamicTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirFunctionTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirIntersectionTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirPlaceholderProjectionBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirStarProjectionBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirUserTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.types.impl.FirQualifierPartImpl;
import org.jetbrains.kotlin.fir.types.impl.FirTypeArgumentListImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: LightTreeRawFirDeclarationBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000à\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0002ã\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\bJ&\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\b2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\bH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\bH\u0002J\u0010\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\bJ\u000e\u0010%\u001a\u00020#2\u0006\u0010$\u001a\u00020\bJ\u000e\u0010&\u001a\u00020'2\u0006\u0010$\u001a\u00020\bJ\u0010\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020\bH\u0002J\u001e\u0010*\u001a\b\u0012\u0004\u0012\u00020 0\u00142\u0006\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020-H\u0002J\u0018\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\b2\u0006\u0010,\u001a\u00020-H\u0002J\"\u00101\u001a\u0014\u0012\u0004\u0012\u000203\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\u0014022\u0006\u00105\u001a\u00020\bH\u0002J\u0016\u00106\u001a\b\u0012\u0004\u0012\u0002070\u00142\u0006\u00108\u001a\u00020\bH\u0002J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020\bH\u0002J\u0015\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\bH\u0000¢\u0006\u0002\b?J\u0012\u0010@\u001a\u0004\u0018\u00010A2\u0006\u0010B\u001a\u00020\bH\u0002J\u0018\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\b2\u0006\u0010,\u001a\u00020-H\u0002J,\u0010F\u001a\u0002032\u0006\u0010G\u001a\u00020\b2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u00020K0I2\u0006\u0010L\u001a\u00020JH\u0002J\u001e\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020\b2\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020SJ\u0018\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020\b2\u0006\u0010W\u001a\u00020XH\u0002J4\u0010Y\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010#\u0012\u0006\u0012\u0004\u0018\u00010Z022\b\u0010[\u001a\u0004\u0018\u00010\b2\b\u0010\\\u001a\u0004\u0018\u00010\b2\u0006\u0010]\u001a\u00020^H\u0002J\u000e\u0010_\u001a\u00020`2\u0006\u0010a\u001a\u00020\bJ,\u0010b\u001a\u0002032\u0006\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020\b2\b\b\u0002\u0010f\u001a\u00020^2\b\b\u0002\u0010g\u001a\u00020^H\u0002J\u0016\u0010h\u001a\b\u0012\u0004\u0012\u00020i0\u00142\u0006\u0010j\u001a\u00020\bH\u0002J0\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020\b2\u0006\u0010n\u001a\u0002032\u0006\u0010o\u001a\u00020p2\u0006\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020tH\u0002J\u001e\u0010u\u001a\u0010\u0012\u0004\u0012\u00020v\u0012\u0004\u0012\u00020d\u0018\u0001022\u0006\u0010w\u001a\u00020\bH\u0002J\u0010\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020\bH\u0002J\u0016\u0010{\u001a\b\u0012\u0004\u0012\u00020y0\u00142\u0006\u0010|\u001a\u00020\bH\u0002J\u0016\u0010}\u001a\b\u0012\u0004\u0012\u0002040\u00142\u0006\u0010~\u001a\u00020\bH\u0002J!\u0010\u007f\u001a\u0002032\u0006\u0010c\u001a\u00020d2\u0007\u0010\u0080\u0001\u001a\u00020\b2\u0006\u0010f\u001a\u00020^H\u0002J\u001d\u0010\u0081\u0001\u001a\u00020t2\u0007\u0010\u0082\u0001\u001a\u00020\b2\t\b\u0002\u0010\u0083\u0001\u001a\u00020^H\u0002J4\u0010\u0084\u0001\u001a\u0002032\u0006\u0010c\u001a\u00020d2\u0007\u0010\u0085\u0001\u001a\u00020\b2\u000e\u0010\u0086\u0001\u001a\t\u0012\u0004\u0012\u00020t0\u0087\u00012\b\b\u0002\u0010f\u001a\u00020^H\u0002J\u0011\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0007\u0010\u008a\u0001\u001a\u00020\bJ\u0013\u0010\u008b\u0001\u001a\u00030\u008c\u00012\u0007\u0010\u008d\u0001\u001a\u00020\bH\u0002J]\u0010\u008e\u0001\u001a\u0005\u0018\u00010\u008f\u00012\u0006\u0010)\u001a\u00020\b2\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010\b2\t\u0010\u0091\u0001\u001a\u0004\u0018\u00010d2\u0006\u0010,\u001a\u00020-2\n\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u00012\t\b\u0002\u0010\u0094\u0001\u001a\u00020^2\u0007\u0010\u0095\u0001\u001a\u00020^2\t\b\u0002\u0010\u0096\u0001\u001a\u00020^H\u0002J\u001c\u0010\u0097\u0001\u001a\u00020 2\u0007\u0010\u0098\u0001\u001a\u00020\b2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-J\u0012\u0010\u0099\u0001\u001a\u0002032\u0007\u0010\u009a\u0001\u001a\u00020\bH\u0002J\u001b\u0010\u009b\u0001\u001a\u00030\u009c\u00012\u0007\u0010\u009d\u0001\u001a\u00020\b2\u0006\u0010,\u001a\u00020-H\u0002J9\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0007\u0010 \u0001\u001a\u00020\b2\f\u0010¡\u0001\u001a\u0007\u0012\u0002\b\u00030¢\u00012\u0006\u0010n\u001a\u0002032\u000e\u0010£\u0001\u001a\t\u0012\u0005\u0012\u00030¤\u00010\u0014H\u0002J\u0010\u0010¥\u0001\u001a\u0002032\u0007\u0010¦\u0001\u001a\u00020\bJ\u0012\u0010§\u0001\u001a\u00020 2\u0007\u0010¨\u0001\u001a\u00020\bH\u0002J\u0013\u0010©\u0001\u001a\u00030ª\u00012\u0007\u0010\u0082\u0001\u001a\u00020\bH\u0002J \u0010«\u0001\u001a\t\u0012\u0005\u0012\u00030¬\u00010\u00142\u0007\u0010\u00ad\u0001\u001a\u00020\b2\u0007\u0010®\u0001\u001a\u00020^J\u0013\u0010¯\u0001\u001a\u00030°\u00012\u0007\u0010±\u0001\u001a\u00020\bH\u0002J\u0019\u0010²\u0001\u001a\t\u0012\u0005\u0012\u00030°\u00010\u00142\u0007\u0010³\u0001\u001a\u00020\bH\u0002J\u0012\u0010´\u0001\u001a\u00020t2\u0007\u0010\u0082\u0001\u001a\u00020\bH\u0002J1\u0010µ\u0001\u001a\u00030¶\u00012\u0007\u0010·\u0001\u001a\u00020\b2\u000e\u0010³\u0001\u001a\t\u0012\u0005\u0012\u00030°\u00010\u00142\f\u0010¸\u0001\u001a\u0007\u0012\u0002\b\u00030¹\u0001H\u0002J\u0013\u0010º\u0001\u001a\u00030»\u00012\u0007\u0010\u0082\u0001\u001a\u00020\bH\u0002J7\u0010¼\u0001\u001a\t\u0012\u0005\u0012\u00030¶\u00010\u00142\u0007\u0010½\u0001\u001a\u00020\b2\u000e\u0010³\u0001\u001a\t\u0012\u0005\u0012\u00030°\u00010\u00142\f\u0010¾\u0001\u001a\u0007\u0012\u0002\b\u00030¹\u0001H\u0002J\u001c\u0010¿\u0001\u001a\u00030¬\u00012\u0007\u0010À\u0001\u001a\u00020\b2\u0007\u0010®\u0001\u001a\u00020^H\u0002J$\u0010Á\u0001\u001a\u0002032\u0006\u0010c\u001a\u00020d2\u0007\u0010Â\u0001\u001a\u00020\b2\b\b\u0002\u0010f\u001a\u00020^H\u0002J=\u0010Ã\u0001\u001a\u00030Ä\u00012\u0007\u0010Å\u0001\u001a\u00020\b2\u000e\u0010¡\u0001\u001a\t\u0012\u0002\b\u0003\u0018\u00010¢\u00012\b\u0010Æ\u0001\u001a\u00030Ç\u00012\u0010\b\u0002\u0010£\u0001\u001a\t\u0012\u0005\u0012\u00030¤\u00010\u0014JA\u0010È\u0001\u001a\t\u0012\u0005\u0012\u00030Ä\u00010\u00142\u0007\u0010É\u0001\u001a\u00020\b2\f\u0010¡\u0001\u001a\u0007\u0012\u0002\b\u00030¢\u00012\b\u0010Æ\u0001\u001a\u00030Ç\u00012\u0010\b\u0002\u0010£\u0001\u001a\t\u0012\u0005\u0012\u00030¤\u00010\u0014J#\u0010Ê\u0001\u001a\u00030Ë\u00012\u0007\u0010Ì\u0001\u001a\u00020\b2\u000e\u0010Í\u0001\u001a\t\u0012\u0004\u0012\u0002040\u0087\u0001H\u0002JJ\u0010Î\u0001\u001a\u00030Ë\u0001\"\u000e\b\u0000\u0010Ï\u0001*\u00020 *\u00030Ð\u00012\u000e\u0010Ñ\u0001\u001a\t\u0012\u0005\u0012\u00030¶\u00010\u00142\u000e\u0010³\u0001\u001a\t\u0012\u0005\u0012\u00030°\u00010\u00142\b\u0010Ò\u0001\u001a\u0003HÏ\u0001H\u0002¢\u0006\u0003\u0010Ó\u0001J\u0012\u0010Ô\u0001\u001a\u00020Z2\u0007\u0010Ì\u0001\u001a\u00020\bH\u0002J$\u0010Õ\u0001\u001a\u00030Ö\u00012\u0007\u0010×\u0001\u001a\u00020p2\u0007\u0010\u0082\u0001\u001a\u00020t2\u0006\u0010s\u001a\u00020tH\u0002J\u001d\u0010Ø\u0001\u001a\u00030Ë\u0001*\t\u0012\u0004\u0012\u00020v0\u0087\u00012\u0006\u0010\\\u001a\u00020\bH\u0002JJ\u0010Ù\u0001\u001a\u00030Ú\u0001*\u0004\u0018\u00010\b2\u0006\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020t2\u0007\u0010Û\u0001\u001a\u0002032\u0007\u0010Ü\u0001\u001a\u00020^2\r\u0010Ý\u0001\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0007\u0010\u0098\u0001\u001a\u00020\bH\u0002J\u0014\u0010Þ\u0001\u001a\u00020^*\t\u0012\u0004\u0012\u00020t0ß\u0001H\u0002J\r\u0010à\u0001\u001a\u00020^*\u00020\bH\u0002J\u0010\u0010á\u0001\u001a\u0005\u0018\u00010â\u0001*\u00020-H\u0002R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006ä\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirDeclarationBuilder;", "Lorg/jetbrains/kotlin/fir/lightTree/converter/AbstractLightTreeRawFirBuilder;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "baseScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "context", "Lorg/jetbrains/kotlin/fir/builder/Context;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;Lcom/intellij/util/diff/FlyweightCapableTreeStructure;Lorg/jetbrains/kotlin/fir/builder/Context;)V", "getBaseScopeProvider$light_tree2fir", "()Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "expressionConverter", "Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirExpressionBuilder;", "buildErrorTopLevelDeclarationForDanglingModifierList", "Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", "node", "convertAnnotation", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "annotationNode", "convertAnnotationEntry", "unescapedAnnotation", "defaultAnnotationUseSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "convertAnnotationTarget", "annotationUseSiteTarget", "convertAnonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "anonymousInitializer", "convertBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "block", "convertBlockExpression", "convertBlockExpressionWithoutBuilding", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirBlockBuilder;", "convertClass", "classNode", "convertClassBody", "classBody", "classWrapper", "Lorg/jetbrains/kotlin/fir/lightTree/fir/ClassWrapper;", "convertConstructorDelegationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "constructorDelegationCall", "convertConstructorInvocation", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "constructorInvocation", "convertContextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "container", "convertDelegationSpecifiers", "Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirDeclarationBuilder$DelegationSpecifiers;", "delegationSpecifiers", "convertDestructingDeclaration", "Lorg/jetbrains/kotlin/fir/lightTree/fir/DestructuringDeclaration;", "destructingDeclaration", "convertDestructingDeclaration$light_tree2fir", "convertDestructingDeclarationEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "entry", "convertEnumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "enumEntry", "convertExplicitDelegation", "explicitDelegation", "delegateFieldsMap", "", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "index", "convertFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "sourceFile", "Lorg/jetbrains/kotlin/KtSourceFile;", "linesMapping", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "convertFileAnnotationsContainer", "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "fileAnnotationList", "fileSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "convertFunctionBody", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "blockNode", "expression", "allowLegacyContractDescription", "", "convertFunctionDeclaration", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "functionDeclaration", "convertFunctionType", "typeRefSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "functionType", "isNullable", "isSuspend", "convertFunctionTypeParameters", "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "parameters", "convertGetterOrSetter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getterOrSetter", "propertyTypeRef", "propertyVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "propertyModifiers", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;", "convertImportAlias", "", "importAlias", "convertImportDirective", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "importDirective", "convertImportDirectives", "importList", "convertInitializerList", "initializerList", "convertIntersectionType", "intersectionType", "convertModifierList", "modifiers", "isInClass", "convertNullableType", "nullableType", "allTypeModifiers", "", "convertObjectLiteral", "Lorg/jetbrains/kotlin/fir/FirElement;", "objectLiteral", "convertPackageDirective", "Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "packageNode", "convertPrimaryConstructor", "Lorg/jetbrains/kotlin/fir/lightTree/fir/PrimaryConstructor;", "primaryConstructor", "selfTypeSource", "delegatedConstructorSource", "Lorg/jetbrains/kotlin/KtLightSourceElement;", "isEnumEntry", "containingClassIsExpectClass", "isImplicitlyActual", "convertPropertyDeclaration", "property", "convertReceiverType", "receiverType", "convertSecondaryConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "secondaryConstructor", "convertSetterParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "setterParameter", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "additionalAnnotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "convertType", ModuleXmlParser.TYPE, "convertTypeAlias", "typeAlias", "convertTypeArgumentModifierList", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/TypeProjectionModifier;", "convertTypeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "typeArguments", "allowedUnderscoredTypeArgument", "convertTypeConstraint", "Lorg/jetbrains/kotlin/fir/lightTree/fir/TypeConstraint;", "typeConstraint", "convertTypeConstraints", "typeConstraints", "convertTypeModifierList", "convertTypeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "typeParameter", "containingSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "convertTypeParameterModifiers", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/TypeParameterModifier;", "convertTypeParameters", "typeParameterList", "containingDeclarationSymbol", "convertTypeProjection", "typeProjection", "convertUserType", "userType", "convertValueParameter", "Lorg/jetbrains/kotlin/fir/lightTree/fir/ValueParameter;", "valueParameter", "valueParameterDeclaration", "Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder$ValueParameterDeclaration;", "convertValueParameters", "valueParameters", "extractRawEffects", "", "rawContractDescription", FirebaseAnalytics.Param.DESTINATION, "fillDanglingConstraintsTo", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "typeParameters", "to", "(Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)V", "obtainContractDescription", "obtainPropertyComponentStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "componentVisibility", "collectSegments", "convertBackingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "propertyReturnType", "isVar", "annotationsFromProperty", "hasSuspend", "", "hasValueParameters", "obtainDispatchReceiverForConstructor", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "DelegationSpecifiers", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LightTreeRawFirDeclarationBuilder extends AbstractLightTreeRawFirBuilder {
    private final FirScopeProvider baseScopeProvider;
    private final LightTreeRawFirExpressionBuilder expressionConverter;

    /* JADX INFO: renamed from: getBaseScopeProvider$light_tree2fir, reason: from getter */
    public final FirScopeProvider getBaseScopeProvider() {
        return this.baseScopeProvider;
    }

    public /* synthetic */ LightTreeRawFirDeclarationBuilder(FirSession firSession, FirScopeProvider firScopeProvider, FlyweightCapableTreeStructure flyweightCapableTreeStructure, Context context, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firScopeProvider, flyweightCapableTreeStructure, (i & 8) != 0 ? new Context() : context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightTreeRawFirDeclarationBuilder(FirSession session, FirScopeProvider baseScopeProvider, FlyweightCapableTreeStructure<LighterASTNode> tree, Context<LighterASTNode> context) {
        super(session, tree, context);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(baseScopeProvider, "baseScopeProvider");
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(context, "context");
        this.baseScopeProvider = baseScopeProvider;
        this.expressionConverter = new LightTreeRawFirExpressionBuilder(session, tree, this, context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v38, types: [T, org.jetbrains.kotlin.fir.FirPackageDirective] */
    public final FirFile convertFile(LighterASTNode file, KtSourceFile sourceFile, KtSourceFileLinesMapping linesMapping) throws Exception {
        KtToken[] ktTokenArr;
        int i;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(linesMapping, "linesMapping");
        if (!Intrinsics.areEqual(file.getTokenType(), KtNodeTypes.KT_FILE)) {
            throw new Exception();
        }
        FirFileSymbol firFileSymbol = new FirFileSymbol();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Context<LighterASTNode> context = getContext();
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        context.setPackageFqName(ROOT);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        int i2 = 0;
        KtToken[] ktTokenArr2 = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(file);
        int length = childrenAsArray.length;
        FirFileAnnotationsContainer firFileAnnotationsContainerConvertFileAnnotationsContainer = null;
        while (i2 < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i2];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            LighterASTNode[] lighterASTNodeArr = childrenAsArray;
            if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr2, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                ktTokenArr = ktTokenArr2;
                i = length;
            } else {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.FILE_ANNOTATION_LIST)) {
                    firFileAnnotationsContainerConvertFileAnnotationsContainer = convertFileAnnotationsContainer(lighterASTNode, firFileSymbol);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.PACKAGE_DIRECTIVE)) {
                    ?? ConvertPackageDirective = convertPackageDirective(lighterASTNode);
                    getContext().setPackageFqName(ConvertPackageDirective.getPackageFqName());
                    objectRef.element = ConvertPackageDirective;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.IMPORT_LIST)) {
                    CollectionsKt.addAll(arrayList, convertImportDirectives(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CLASS)) {
                    arrayList2.add(convertClass(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.FUN)) {
                    FirAnnotationContainer firAnnotationContainerConvertFunctionDeclaration = convertFunctionDeclaration(lighterASTNode);
                    Intrinsics.checkNotNull(firAnnotationContainerConvertFunctionDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclaration");
                    arrayList2.add((FirDeclaration) firAnnotationContainerConvertFunctionDeclaration);
                } else {
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.PROPERTY)) {
                        ktTokenArr = ktTokenArr2;
                        arrayList2.add(convertPropertyDeclaration$default(this, lighterASTNode, null, 2, null));
                    } else {
                        ktTokenArr = ktTokenArr2;
                        if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPEALIAS)) {
                            arrayList2.add(convertTypeAlias(lighterASTNode));
                        } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.OBJECT_DECLARATION)) {
                            arrayList2.add(convertClass(lighterASTNode));
                        } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.DESTRUCTURING_DECLARATION)) {
                            i = length;
                            arrayList2.add(buildErrorTopLevelDestructuringDeclaration(AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null)));
                        } else {
                            i = length;
                            if (!Intrinsics.areEqual(tokenType2, KtNodeTypes.SCRIPT) && Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                                arrayList3.add(lighterASTNode);
                            }
                        }
                    }
                    i = length;
                }
                ktTokenArr = ktTokenArr2;
                i = length;
            }
            i2++;
            childrenAsArray = lighterASTNodeArr;
            ktTokenArr2 = ktTokenArr;
            length = i;
        }
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList2.add(buildErrorTopLevelDeclarationForDanglingModifierList((LighterASTNode) it.next()));
        }
        FirFileBuilder firFileBuilder = new FirFileBuilder();
        firFileBuilder.setSymbol(firFileSymbol);
        firFileBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, file, null, 1, null));
        firFileBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firFileBuilder.setModuleData(getBaseModuleData());
        firFileBuilder.setName(sourceFile.getName());
        firFileBuilder.setSourceFile(sourceFile);
        firFileBuilder.setSourceFileLinesMapping(linesMapping);
        FirPackageDirective firPackageDirectiveBuild = (FirPackageDirective) objectRef.element;
        if (firPackageDirectiveBuild == null) {
            FirPackageDirectiveBuilder firPackageDirectiveBuilder = new FirPackageDirectiveBuilder();
            firPackageDirectiveBuilder.setPackageFqName(getContext().getPackageFqName());
            firPackageDirectiveBuild = firPackageDirectiveBuilder.build();
        }
        firFileBuilder.setPackageDirective(firPackageDirectiveBuild);
        firFileBuilder.setAnnotationsContainer(firFileAnnotationsContainerConvertFileAnnotationsContainer);
        CollectionsKt.addAll(firFileBuilder.getImports(), arrayList);
        CollectionsKt.addAll(firFileBuilder.getDeclarations(), arrayList2);
        return firFileBuilder.mo6809build();
    }

    public final FirBlock convertBlockExpression(LighterASTNode block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return convertBlockExpressionWithoutBuilding(block).mo6809build();
    }

    /* JADX WARN: Code duplicated, block: B:65:0x016e  */
    public final FirBlockBuilder convertBlockExpressionWithoutBuilding(LighterASTNode block) throws Throwable {
        boolean z;
        LighterASTNode lighterASTNode;
        Intrinsics.checkNotNullParameter(block, "block");
        LighterASTNode[] childrenAsArray = getChildrenAsArray(block);
        ArrayList<FirStatement> arrayList = new ArrayList();
        int length = childrenAsArray.length;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CLASS) ? true : Intrinsics.areEqual(tokenType2, KtNodeTypes.OBJECT_DECLARATION)) {
                    FirAnnotationContainer firAnnotationContainerConvertClass = convertClass(lighterASTNode);
                    Intrinsics.checkNotNull(firAnnotationContainerConvertClass, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
                    arrayList.add((FirStatement) firAnnotationContainerConvertClass);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.FUN)) {
                    arrayList.add(convertFunctionDeclaration(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.PROPERTY)) {
                    FirAnnotationContainer firAnnotationContainerConvertPropertyDeclaration$default = convertPropertyDeclaration$default(this, lighterASTNode, null, 2, null);
                    Intrinsics.checkNotNull(firAnnotationContainerConvertPropertyDeclaration$default, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
                    arrayList.add((FirStatement) firAnnotationContainerConvertPropertyDeclaration$default);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.DESTRUCTURING_DECLARATION)) {
                    arrayList.add(convertDestructingDeclaration$light_tree2fir(lighterASTNode).toFirDestructingDeclaration(getBaseModuleData()));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPEALIAS)) {
                    FirAnnotationContainer firAnnotationContainerConvertTypeAlias = convertTypeAlias(lighterASTNode);
                    Intrinsics.checkNotNull(firAnnotationContainerConvertTypeAlias, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
                    arrayList.add((FirStatement) firAnnotationContainerConvertTypeAlias);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CLASS_INITIALIZER)) {
                    FirAnnotationContainer firAnnotationContainerConvertAnonymousInitializer = convertAnonymousInitializer(lighterASTNode);
                    Intrinsics.checkNotNull(firAnnotationContainerConvertAnonymousInitializer, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
                    arrayList.add((FirStatement) firAnnotationContainerConvertAnonymousInitializer);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    ArrayList arrayList2 = arrayList;
                    LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this.expressionConverter;
                    FirElement firElementConvertExpression = lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode, "");
                    FirAnnotationContainer firAnnotationContainer = (FirStatement) (!(firElementConvertExpression instanceof FirStatement) ? null : firElementConvertExpression);
                    if (firAnnotationContainer == null) {
                        FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firAnnotationContainerBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
                        }
                        firAnnotationContainer = (FirStatement) firAnnotationContainerBuildErrorExpression;
                    }
                    arrayList2.add(firAnnotationContainer);
                } else {
                    continue;
                }
            }
        }
        FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
        firBlockBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, block, null, 1, null));
        for (FirStatement firStatement : arrayList) {
            boolean z2 = firStatement instanceof FirBlock;
            if (z2) {
                KtSourceElement source = firStatement.getSource();
                if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE)) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                z = false;
            }
            if (!z2 || z || !firStatement.getAnnotations().isEmpty()) {
                firBlockBuilder.getStatements().add(firStatement);
            } else {
                CollectionsKt.addAll(firBlockBuilder.getStatements(), ((FirBlock) firStatement).getStatements());
            }
        }
        return firBlockBuilder;
    }

    private final FirPackageDirective convertPackageDirective(LighterASTNode packageNode) {
        LighterASTNode lighterASTNode;
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        int i = 0;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(packageNode);
        int length = childrenAsArray.length;
        while (true) {
            if (i >= length || (lighterASTNode = childrenAsArray[i]) == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.DOT_QUALIFIED_EXPRESSION) ? true : Intrinsics.areEqual(tokenType2, KtNodeTypes.REFERENCE_EXPRESSION)) {
                    ROOT = new FqName(ConverterUtilKt.getAsStringWithoutBacktick(lighterASTNode));
                }
            }
            i++;
        }
        FirPackageDirectiveBuilder firPackageDirectiveBuilder = new FirPackageDirectiveBuilder();
        firPackageDirectiveBuilder.setPackageFqName(ROOT);
        firPackageDirectiveBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, packageNode, null, 1, null));
        return firPackageDirectiveBuilder.build();
    }

    private final Pair<String, KtSourceElement> convertImportAlias(LighterASTNode importAlias) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(importAlias);
        int length = childrenAsArray.length;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtTokens.IDENTIFIER)) {
                return new Pair<>(getAsText(lighterASTNode), AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null));
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1, types: [T, org.jetbrains.kotlin.name.FqName] */
    private final FirImport convertImportDirective(LighterASTNode importDirective) {
        LighterASTNode lighterASTNode;
        Pair<String, KtSourceElement> pairConvertImportAlias;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(importDirective);
        int length = childrenAsArray.length;
        boolean z = false;
        String first = null;
        KtSourceElement second = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.REFERENCE_EXPRESSION) ? true : Intrinsics.areEqual(tokenType2, KtNodeTypes.DOT_QUALIFIED_EXPRESSION)) {
                    ArrayList arrayList = new ArrayList();
                    collectSegments(arrayList, lighterASTNode);
                    objectRef.element = new FqName(CollectionsKt.joinToString$default(arrayList, ".", null, null, 0, null, null, 62, null));
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.MUL)) {
                    z = true;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.IMPORT_ALIAS) && (pairConvertImportAlias = convertImportAlias(lighterASTNode)) != null) {
                    first = pairConvertImportAlias.getFirst();
                    second = pairConvertImportAlias.getSecond();
                }
            }
        }
        FirImportBuilder firImportBuilder = new FirImportBuilder();
        firImportBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, importDirective, null, 1, null));
        firImportBuilder.setImportedFqName((FqName) objectRef.element);
        firImportBuilder.setAllUnder(z);
        String str = first;
        firImportBuilder.setAliasName(str != null ? Name.identifier(str) : null);
        firImportBuilder.setAliasSource(second);
        return firImportBuilder.build();
    }

    private final void collectSegments(List<String> list, LighterASTNode lighterASTNode) {
        IElementType tokenType = lighterASTNode.getTokenType();
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.REFERENCE_EXPRESSION)) {
            list.add(ConverterUtilKt.getAsStringWithoutBacktick(lighterASTNode));
            return;
        }
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.DOT_QUALIFIED_EXPRESSION)) {
            KtToken[] ktTokenArr = new KtToken[0];
            for (LighterASTNode lighterASTNode2 : getChildrenAsArray(lighterASTNode)) {
                if (lighterASTNode2 == null) {
                    return;
                }
                IElementType tokenType2 = lighterASTNode2.getTokenType();
                if (!KtTokens.COMMENTS.contains(tokenType2) && !Intrinsics.areEqual(tokenType2, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType2, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType2) && !Intrinsics.areEqual(tokenType2, TokenType.ERROR_ELEMENT)) {
                    collectSegments(list, lighterASTNode2);
                }
            }
        }
    }

    private final List<FirImport> convertImportDirectives(LighterASTNode importList) {
        LighterASTNode[] childrenAsArray = getChildrenAsArray(importList);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.IMPORT_DIRECTIVE)) {
                arrayList.add(convertImportDirective(lighterASTNode));
            }
        }
        return arrayList;
    }

    static /* synthetic */ Modifier convertModifierList$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, LighterASTNode lighterASTNode, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return lightTreeRawFirDeclarationBuilder.convertModifierList(lighterASTNode, z);
    }

    private final Modifier convertModifierList(LighterASTNode modifiers, boolean isInClass) {
        Modifier modifier = new Modifier(0L, 1, null);
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(modifiers)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION)) {
                    CollectionsKt.addAll(modifier.getAnnotations(), convertAnnotation(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_ENTRY)) {
                    modifier.getAnnotations().add(convertAnnotationEntry$default(this, lighterASTNode, null, null, 6, null));
                } else if (tokenType2 instanceof KtModifierKeywordToken) {
                    modifier.addModifier(lighterASTNode, isInClass);
                }
            }
        }
        return modifier;
    }

    private final Modifier convertTypeModifierList(LighterASTNode modifiers) {
        Modifier modifier = new Modifier(0L, 1, null);
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(modifiers)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION)) {
                    CollectionsKt.addAll(modifier.getAnnotations(), convertAnnotation(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_ENTRY)) {
                    modifier.getAnnotations().add(convertAnnotationEntry$default(this, lighterASTNode, null, null, 6, null));
                } else if (tokenType2 instanceof KtModifierKeywordToken) {
                    Modifier.addModifier$default(modifier, lighterASTNode, false, 2, null);
                }
            }
        }
        return modifier;
    }

    private final TypeProjectionModifier convertTypeArgumentModifierList(LighterASTNode modifiers) {
        TypeProjectionModifier typeProjectionModifier = new TypeProjectionModifier(null, 0L, 3, null);
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(modifiers)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION)) {
                    CollectionsKt.addAll(typeProjectionModifier.getAnnotations(), convertAnnotation(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_ENTRY)) {
                    typeProjectionModifier.getAnnotations().add(convertAnnotationEntry$default(this, lighterASTNode, null, null, 6, null));
                } else if (tokenType2 instanceof KtModifierKeywordToken) {
                    Modifier.addModifier$default(typeProjectionModifier, lighterASTNode, false, 2, null);
                }
            }
        }
        return typeProjectionModifier;
    }

    private final TypeParameterModifier convertTypeParameterModifiers(LighterASTNode modifiers) {
        TypeParameterModifier typeParameterModifier = new TypeParameterModifier(0L, 1, null);
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(modifiers)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION)) {
                    CollectionsKt.addAll(typeParameterModifier.getAnnotations(), convertAnnotation(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_ENTRY)) {
                    typeParameterModifier.getAnnotations().add(convertAnnotationEntry$default(this, lighterASTNode, null, null, 6, null));
                } else if (tokenType2 instanceof KtModifierKeywordToken) {
                    Modifier.addModifier$default(typeParameterModifier, lighterASTNode, false, 2, null);
                }
            }
        }
        return typeParameterModifier;
    }

    public final List<FirAnnotationCall> convertAnnotation(LighterASTNode annotationNode) {
        Intrinsics.checkNotNullParameter(annotationNode, "annotationNode");
        LighterASTNode[] childrenAsArray = getChildrenAsArray(annotationNode);
        ArrayList arrayList = new ArrayList();
        AnnotationUseSiteTarget annotationUseSiteTargetConvertAnnotationTarget = null;
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_TARGET)) {
                    annotationUseSiteTargetConvertAnnotationTarget = convertAnnotationTarget(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_ENTRY)) {
                    arrayList.add(convertAnnotationEntry$default(this, lighterASTNode, annotationUseSiteTargetConvertAnnotationTarget, null, 4, null));
                }
            }
        }
        return arrayList;
    }

    private final AnnotationUseSiteTarget convertAnnotationTarget(LighterASTNode annotationUseSiteTarget) {
        KtToken[] ktTokenArr = new KtToken[0];
        AnnotationUseSiteTarget annotationUseSiteTarget2 = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(annotationUseSiteTarget)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtTokens.FIELD_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.FIELD;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.FILE_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.FILE;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.PROPERTY_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.PROPERTY;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.GET_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.PROPERTY_GETTER;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.SET_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.PROPERTY_SETTER;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.RECEIVER_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.RECEIVER;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.PARAM_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.SETPARAM_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.SETTER_PARAMETER;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.DELEGATE_KEYWORD)) {
                    annotationUseSiteTarget2 = AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD;
                }
            }
        }
        if (annotationUseSiteTarget2 != null) {
            return annotationUseSiteTarget2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("annotationTarget");
        return null;
    }

    public static /* synthetic */ FirAnnotationCall convertAnnotationEntry$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, LighterASTNode lighterASTNode, AnnotationUseSiteTarget annotationUseSiteTarget, ConeDiagnostic coneDiagnostic, int i, Object obj) {
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        if ((i & 4) != 0) {
            coneDiagnostic = null;
        }
        return lightTreeRawFirDeclarationBuilder.convertAnnotationEntry(lighterASTNode, annotationUseSiteTarget, coneDiagnostic);
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00e1  */
    public final FirAnnotationCall convertAnnotationEntry(LighterASTNode unescapedAnnotation, AnnotationUseSiteTarget defaultAnnotationUseSiteTarget, ConeDiagnostic diagnostic) {
        Pair<FirTypeRef, List<FirExpression>> pair;
        Name nameSpecial;
        KtLightSourceElement ktLightSourceElement;
        List<FirTypeProjection> listEmptyList;
        FirTypeArgumentList typeArgumentList;
        List<FirTypeProjection> listEmptyList2;
        FirTypeArgumentList typeArgumentList2;
        List<FirQualifierPart> qualifier;
        Intrinsics.checkNotNullParameter(unescapedAnnotation, "unescapedAnnotation");
        KtToken[] ktTokenArr = new KtToken[0];
        Pair<FirTypeRef, List<FirExpression>> pairConvertConstructorInvocation = null;
        AnnotationUseSiteTarget annotationUseSiteTargetConvertAnnotationTarget = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(unescapedAnnotation)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_TARGET)) {
                    annotationUseSiteTargetConvertAnnotationTarget = convertAnnotationTarget(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONSTRUCTOR_CALLEE)) {
                    pairConvertConstructorInvocation = convertConstructorInvocation(unescapedAnnotation);
                }
            }
        }
        if (pairConvertConstructorInvocation == null) {
            Intrinsics.throwUninitializedPropertyAccessException("constructorCalleePair");
            pair = null;
        } else {
            pair = pairConvertConstructorInvocation;
        }
        FirTypeRef first = pair.getFirst();
        FirUserTypeRef firUserTypeRef = first instanceof FirUserTypeRef ? (FirUserTypeRef) first : null;
        FirQualifierPart firQualifierPart = (firUserTypeRef == null || (qualifier = firUserTypeRef.getQualifier()) == null) ? null : (FirQualifierPart) CollectionsKt.last((List) qualifier);
        if (firQualifierPart == null || (nameSpecial = firQualifierPart.getName()) == null) {
            nameSpecial = Name.special("<no-annotation-name>");
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        }
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        IElementType CONSTRUCTOR_CALLEE = KtNodeTypes.CONSTRUCTOR_CALLEE;
        Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR_CALLEE, "CONSTRUCTOR_CALLEE");
        LighterASTNode childNodeByType = getChildNodeByType(unescapedAnnotation, CONSTRUCTOR_CALLEE);
        if (childNodeByType != null) {
            IElementType TYPE_REFERENCE = KtNodeTypes.TYPE_REFERENCE;
            Intrinsics.checkNotNullExpressionValue(TYPE_REFERENCE, "TYPE_REFERENCE");
            LighterASTNode childNodeByType2 = getChildNodeByType(childNodeByType, TYPE_REFERENCE);
            if (childNodeByType2 != null) {
                IElementType USER_TYPE = KtNodeTypes.USER_TYPE;
                Intrinsics.checkNotNullExpressionValue(USER_TYPE, "USER_TYPE");
                LighterASTNode childNodeByType3 = getChildNodeByType(childNodeByType2, USER_TYPE);
                if (childNodeByType3 != null) {
                    IElementType REFERENCE_EXPRESSION = KtNodeTypes.REFERENCE_EXPRESSION;
                    Intrinsics.checkNotNullExpressionValue(REFERENCE_EXPRESSION, "REFERENCE_EXPRESSION");
                    LighterASTNode childNodeByType4 = getChildNodeByType(childNodeByType3, REFERENCE_EXPRESSION);
                    if (childNodeByType4 != null) {
                        ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, childNodeByType4, null, 1, null);
                    } else {
                        ktLightSourceElement = null;
                    }
                } else {
                    ktLightSourceElement = null;
                }
            } else {
                ktLightSourceElement = null;
            }
        } else {
            ktLightSourceElement = null;
        }
        firSimpleNamedReferenceBuilder.setSource(ktLightSourceElement);
        firSimpleNamedReferenceBuilder.setName(nameSpecial);
        FirNamedReference firNamedReferenceBuild = firSimpleNamedReferenceBuilder.build();
        if (diagnostic == null) {
            FirAnnotationCallBuilder firAnnotationCallBuilder = new FirAnnotationCallBuilder();
            firAnnotationCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, unescapedAnnotation, null, 1, null));
            if (annotationUseSiteTargetConvertAnnotationTarget != null) {
                defaultAnnotationUseSiteTarget = annotationUseSiteTargetConvertAnnotationTarget;
            }
            firAnnotationCallBuilder.setUseSiteTarget(defaultAnnotationUseSiteTarget);
            firAnnotationCallBuilder.setAnnotationTypeRef(pairConvertConstructorInvocation.getFirst());
            firAnnotationCallBuilder.setCalleeReference(firNamedReferenceBuild);
            ConverterUtilKt.extractArgumentsFrom(firAnnotationCallBuilder, pairConvertConstructorInvocation.getSecond());
            List<FirTypeProjection> typeArguments = firAnnotationCallBuilder.getTypeArguments();
            if (firQualifierPart == null || (typeArgumentList2 = firQualifierPart.getTypeArgumentList()) == null || (listEmptyList2 = typeArgumentList2.getTypeArguments()) == null) {
                listEmptyList2 = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(typeArguments, listEmptyList2);
            return firAnnotationCallBuilder.mo6809build();
        }
        FirErrorAnnotationCallBuilder firErrorAnnotationCallBuilder = new FirErrorAnnotationCallBuilder();
        firErrorAnnotationCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, unescapedAnnotation, null, 1, null));
        if (annotationUseSiteTargetConvertAnnotationTarget != null) {
            defaultAnnotationUseSiteTarget = annotationUseSiteTargetConvertAnnotationTarget;
        }
        firErrorAnnotationCallBuilder.setUseSiteTarget(defaultAnnotationUseSiteTarget);
        firErrorAnnotationCallBuilder.setAnnotationTypeRef(pairConvertConstructorInvocation.getFirst());
        firErrorAnnotationCallBuilder.setDiagnostic(diagnostic);
        firErrorAnnotationCallBuilder.setCalleeReference(firNamedReferenceBuild);
        ConverterUtilKt.extractArgumentsFrom(firErrorAnnotationCallBuilder, pairConvertConstructorInvocation.getSecond());
        List<FirTypeProjection> typeArguments2 = firErrorAnnotationCallBuilder.getTypeArguments();
        if (firQualifierPart == null || (typeArgumentList = firQualifierPart.getTypeArgumentList()) == null || (listEmptyList = typeArgumentList.getTypeArguments()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        CollectionsKt.addAll(typeArguments2, listEmptyList);
        return firErrorAnnotationCallBuilder.mo6809build();
    }

    private final boolean hasValueParameters(LighterASTNode lighterASTNode) {
        IElementType VALUE_PARAMETER_LIST = KtNodeTypes.VALUE_PARAMETER_LIST;
        Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER_LIST, "VALUE_PARAMETER_LIST");
        List<LighterASTNode> childNodesByType = getChildNodesByType(lighterASTNode, VALUE_PARAMETER_LIST);
        if (!childNodesByType.isEmpty()) {
            LighterASTNode lighterASTNode2 = (LighterASTNode) CollectionsKt.first((List) childNodesByType);
            IElementType VALUE_PARAMETER = KtNodeTypes.VALUE_PARAMETER;
            Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER, "VALUE_PARAMETER");
            if (!getChildNodesByType(lighterASTNode2, VALUE_PARAMETER).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:306:0x07ed  */
    /* JADX WARN: Code duplicated, block: B:308:0x07fb  */
    /* JADX WARN: Code duplicated, block: B:311:0x0837  */
    /* JADX WARN: Code duplicated, block: B:345:0x019e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:346:0x019e A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x017d  */
    /* JADX WARN: Code duplicated, block: B:93:0x0185 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:94:0x0187  */
    /* JADX WARN: Code duplicated, block: B:95:0x018c  */
    /* JADX WARN: Code duplicated, block: B:99:0x0196  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16, types: [T, org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef] */
    /* JADX WARN: Type inference failed for: r0v62, types: [T, org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef] */
    /* JADX WARN: Type inference failed for: r0v66, types: [T, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef] */
    private final FirDeclaration convertClass(LighterASTNode classNode) throws Throwable {
        boolean z;
        int i;
        boolean z2;
        String str;
        String str2;
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder;
        boolean z3;
        ArrayList arrayList;
        Ref.ObjectRef objectRef;
        boolean z4;
        boolean z5;
        List<DelegatedConstructorWrapper> listEmptyList;
        FirRegularClassBuilder firRegularClassBuilder;
        Map<Integer, FirFieldSymbol> delegateFieldsMap;
        Map<Integer, FirFieldSymbol> delegateFieldsMap2;
        Collection<FirFieldSymbol> collectionValues;
        List<DelegatedConstructorWrapper> superTypeCalls;
        DelegatedConstructorWrapper delegatedConstructorWrapper;
        List<DelegatedConstructorWrapper> superTypeCalls2;
        DelegatedConstructorWrapper delegatedConstructorWrapper2;
        IElementType tokenType;
        LighterASTNode parent;
        Modifier modifier = new Modifier(0L, 1, null);
        ClassKind classKind = ClassKind.CLASS;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(classNode);
        int length = childrenAsArray.length;
        Modifier modifierConvertModifierList = modifier;
        int i2 = 0;
        LighterASTNode lighterASTNode = null;
        String asText = null;
        LighterASTNode lighterASTNode2 = null;
        LighterASTNode lighterASTNode3 = null;
        LighterASTNode lighterASTNode4 = null;
        while (i2 < length) {
            LighterASTNode lighterASTNode5 = childrenAsArray[i2];
            if (lighterASTNode5 == null) {
                break;
            }
            IElementType tokenType2 = lighterASTNode5.getTokenType();
            LighterASTNode[] lighterASTNodeArr = childrenAsArray;
            if (!KtTokens.COMMENTS.contains(tokenType2) && !Intrinsics.areEqual(tokenType2, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType2, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType2) && !Intrinsics.areEqual(tokenType2, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType3 = lighterASTNode5.getTokenType();
                if (Intrinsics.areEqual(tokenType3, KtNodeTypes.MODIFIER_LIST)) {
                    modifierConvertModifierList = convertModifierList(lighterASTNode5, true);
                } else if (Intrinsics.areEqual(tokenType3, KtTokens.CLASS_KEYWORD)) {
                    classKind = ClassKind.CLASS;
                } else if (Intrinsics.areEqual(tokenType3, KtTokens.INTERFACE_KEYWORD)) {
                    classKind = ClassKind.INTERFACE;
                } else if (Intrinsics.areEqual(tokenType3, KtTokens.OBJECT_KEYWORD)) {
                    classKind = ClassKind.OBJECT;
                } else if (Intrinsics.areEqual(tokenType3, KtTokens.IDENTIFIER)) {
                    asText = getAsText(lighterASTNode5);
                } else if (Intrinsics.areEqual(tokenType3, KtNodeTypes.TYPE_PARAMETER_LIST)) {
                    lighterASTNode = lighterASTNode5;
                } else if (Intrinsics.areEqual(tokenType3, KtNodeTypes.PRIMARY_CONSTRUCTOR)) {
                    lighterASTNode3 = lighterASTNode5;
                } else if (Intrinsics.areEqual(tokenType3, KtNodeTypes.SUPER_TYPE_LIST)) {
                    lighterASTNode2 = lighterASTNode5;
                } else if (Intrinsics.areEqual(tokenType3, KtNodeTypes.TYPE_CONSTRAINT_LIST)) {
                    CollectionsKt.addAll(arrayList3, convertTypeConstraints(lighterASTNode5));
                } else if (Intrinsics.areEqual(tokenType3, KtNodeTypes.CLASS_BODY)) {
                    lighterASTNode4 = lighterASTNode5;
                }
            }
            i2++;
            childrenAsArray = lighterASTNodeArr;
        }
        if (classKind == ClassKind.CLASS) {
            if (modifierConvertModifierList.isEnum()) {
                classKind = ClassKind.ENUM_CLASS;
            } else if (modifierConvertModifierList.isAnnotation()) {
                classKind = ClassKind.ANNOTATION_CLASS;
            }
        }
        Name nameNameAsSafeName = ConverterUtilKt.nameAsSafeName(asText, modifierConvertModifierList.isCompanion() ? "Companion" : "");
        LighterASTNode parent2 = classNode;
        while (true) {
            if (parent2 == null) {
                z = false;
                break;
            }
            IElementType tokenType4 = parent2.getTokenType();
            parent2 = getParent(parent2);
            IElementType tokenType5 = parent2 != null ? parent2.getTokenType() : null;
            if (Intrinsics.areEqual(tokenType4, KtNodeTypes.PROPERTY) || Intrinsics.areEqual(tokenType4, KtNodeTypes.FUN)) {
                LighterASTNode parent3 = parent2 != null ? getParent(parent2) : null;
                if (!Intrinsics.areEqual(tokenType5, KtNodeTypes.KT_FILE)) {
                    if (Intrinsics.areEqual(tokenType5, KtNodeTypes.CLASS_BODY)) {
                        if (Intrinsics.areEqual(parent3 != null ? parent3.getTokenType() : null, KtNodeTypes.OBJECT_DECLARATION)) {
                            if (Intrinsics.areEqual((parent3 == null || (parent = getParent(parent3)) == null) ? null : parent.getTokenType(), KtNodeTypes.OBJECT_LITERAL)) {
                                if (!Intrinsics.areEqual(tokenType5, KtNodeTypes.BLOCK)) {
                                    if (parent3 != null) {
                                        tokenType = parent3.getTokenType();
                                    } else {
                                        tokenType = null;
                                    }
                                    if (Intrinsics.areEqual(tokenType, KtNodeTypes.SCRIPT)) {
                                        if (Intrinsics.areEqual(tokenType5, KtNodeTypes.ENUM_ENTRY)) {
                                        }
                                    }
                                } else if (Intrinsics.areEqual(tokenType5, KtNodeTypes.ENUM_ENTRY)) {
                                }
                            }
                        }
                    } else if (!Intrinsics.areEqual(tokenType5, KtNodeTypes.BLOCK)) {
                        if (parent3 != null) {
                            tokenType = parent3.getTokenType();
                        } else {
                            tokenType = null;
                        }
                        if (Intrinsics.areEqual(tokenType, KtNodeTypes.SCRIPT)) {
                            if (Intrinsics.areEqual(tokenType5, KtNodeTypes.ENUM_ENTRY)) {
                            }
                        }
                    } else if (Intrinsics.areEqual(tokenType5, KtNodeTypes.ENUM_ENTRY) && !Intrinsics.areEqual(tokenType4, KtNodeTypes.BLOCK)) {
                    }
                }
            } else if (Intrinsics.areEqual(tokenType5, KtNodeTypes.ENUM_ENTRY)) {
            }
            z = true;
            break;
        }
        boolean z6 = modifierConvertModifierList.hasExpect() || getContext().getContainerIsExpect();
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder2 = this;
        Context<LighterASTNode> context = lightTreeRawFirDeclarationBuilder2.getContext();
        FqName fqNameChild = lightTreeRawFirDeclarationBuilder2.getContext().getClassName().child(nameNameAsSafeName);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        context.setClassName(fqNameChild);
        boolean forcedLocalContext = lightTreeRawFirDeclarationBuilder2.getContext().getForcedLocalContext();
        lightTreeRawFirDeclarationBuilder2.getContext().setForcedLocalContext(z || lightTreeRawFirDeclarationBuilder2.getContext().getForcedLocalContext());
        boolean containerIsExpect = lightTreeRawFirDeclarationBuilder2.getContext().getContainerIsExpect();
        lightTreeRawFirDeclarationBuilder2.getContext().setContainerIsExpect(containerIsExpect || z6);
        int size = lightTreeRawFirDeclarationBuilder2.getContext().getDispatchReceiverTypesStack().size();
        try {
            str2 = "Wrong number of ";
            try {
                FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(z ? Visibilities.Local.INSTANCE : modifierConvertModifierList.getVisibility(), modifierConvertModifierList.getModality(true));
                firDeclarationStatusImpl.setExpect(z6);
                firDeclarationStatusImpl.setActual(modifierConvertModifierList.hasActual());
                firDeclarationStatusImpl.setInner(modifierConvertModifierList.isInner());
                firDeclarationStatusImpl.setCompanion(modifierConvertModifierList.isCompanion() && classKind == ClassKind.OBJECT);
                firDeclarationStatusImpl.setData(modifierConvertModifierList.isDataClass());
                firDeclarationStatusImpl.setInline(modifierConvertModifierList.isInlineClass());
                firDeclarationStatusImpl.setFun(modifierConvertModifierList.isFunctionalInterface());
                firDeclarationStatusImpl.setExternal(modifierConvertModifierList.hasExternal());
                FirRegularClassSymbol firRegularClassSymbol = new FirRegularClassSymbol(getContext().getCurrentClassId());
                if (lighterASTNode != null) {
                    CollectionsKt.addAll(arrayList2, convertTypeParameters(lighterASTNode, arrayList3, firRegularClassSymbol));
                    Unit unit = Unit.INSTANCE;
                    Unit unit2 = Unit.INSTANCE;
                }
                LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder3 = this;
                lightTreeRawFirDeclarationBuilder3.addCapturedTypeParameters(firDeclarationStatusImpl.isInner() || z, AbstractRawFirBuilder.toFirSourceElement$default(this, classNode, null, 1, null), arrayList2);
                try {
                    Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                    FirRegularClassBuilder firRegularClassBuilder2 = new FirRegularClassBuilder();
                    firRegularClassBuilder2.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, classNode, null, 1, null));
                    firRegularClassBuilder2.setModuleData(getBaseModuleData());
                    firRegularClassBuilder2.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                    firRegularClassBuilder2.setName(nameNameAsSafeName);
                    firRegularClassBuilder2.setStatus(firDeclarationStatusImpl);
                    firRegularClassBuilder2.setClassKind(classKind);
                    firRegularClassBuilder2.setScopeProvider(this.baseScopeProvider);
                    firRegularClassBuilder2.setSymbol(firRegularClassSymbol);
                    CollectionsKt.addAll(firRegularClassBuilder2.getAnnotations(), modifierConvertModifierList.getAnnotations());
                    CollectionsKt.addAll(firRegularClassBuilder2.getTypeParameters(), arrayList2);
                    getContext().appendOuterTypeParameters(true, firRegularClassBuilder2.getTypeParameters());
                    FirResolvedTypeRef delegatedSelfType = toDelegatedSelfType(classNode, firRegularClassBuilder2);
                    registerSelfType(delegatedSelfType);
                    LighterASTNode lighterASTNode6 = lighterASTNode2;
                    DelegationSpecifiers delegationSpecifiersConvertDelegationSpecifiers = lighterASTNode6 != null ? convertDelegationSpecifiers(lighterASTNode6) : null;
                    Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
                    objectRef3.element = (delegationSpecifiersConvertDelegationSpecifiers == null || (superTypeCalls2 = delegationSpecifiersConvertDelegationSpecifiers.getSuperTypeCalls()) == null || (delegatedConstructorWrapper2 = (DelegatedConstructorWrapper) CollectionsKt.lastOrNull((List) superTypeCalls2)) == null) ? 0 : delegatedConstructorWrapper2.getDelegatedSuperTypeRef();
                    KtLightSourceElement source = (delegationSpecifiersConvertDelegationSpecifiers == null || (superTypeCalls = delegationSpecifiersConvertDelegationSpecifiers.getSuperTypeCalls()) == null || (delegatedConstructorWrapper = (DelegatedConstructorWrapper) CollectionsKt.lastOrNull((List) superTypeCalls)) == null) ? null : delegatedConstructorWrapper.getSource();
                    ArrayList arrayList4 = new ArrayList();
                    if (delegationSpecifiersConvertDelegationSpecifiers != null) {
                        CollectionsKt.addAll(arrayList4, delegationSpecifiersConvertDelegationSpecifiers.getSuperTypesRef());
                        Unit unit3 = Unit.INSTANCE;
                        Unit unit4 = Unit.INSTANCE;
                    }
                    if (modifierConvertModifierList.isEnum() && classKind == ClassKind.ENUM_CLASS && source == null) {
                        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                        arrayList = arrayList2;
                        objectRef = objectRef2;
                        z4 = false;
                        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(getImplicitEnumType().getType().getLookupTag(), new ConeKotlinType[]{delegatedSelfType.getType()}, false, null, 8, null));
                        objectRef3.element = firResolvedTypeRefBuilder.mo6809build();
                        arrayList4.add(objectRef3.element);
                    } else {
                        arrayList = arrayList2;
                        objectRef = objectRef2;
                        z4 = false;
                        if (modifierConvertModifierList.isAnnotation() && classKind == ClassKind.ANNOTATION_CLASS) {
                            arrayList4.add(getImplicitAnnotationType());
                            objectRef3.element = getImplicitAnyType();
                        }
                    }
                    if (arrayList4.isEmpty()) {
                        arrayList4.add(getImplicitAnyType());
                        objectRef3.element = getImplicitAnyType();
                        Unit unit5 = Unit.INSTANCE;
                    }
                    CollectionsKt.addAll(firRegularClassBuilder2.getSuperTypeRefs(), arrayList4);
                    IElementType SECONDARY_CONSTRUCTOR = KtNodeTypes.SECONDARY_CONSTRUCTOR;
                    Intrinsics.checkNotNullExpressionValue(SECONDARY_CONSTRUCTOR, "SECONDARY_CONSTRUCTOR");
                    LighterASTNode lighterASTNode7 = lighterASTNode4;
                    List<LighterASTNode> childNodesByType = getChildNodesByType(lighterASTNode7, SECONDARY_CONSTRUCTOR);
                    FirRegularClassBuilder firRegularClassBuilder3 = firRegularClassBuilder2;
                    boolean z7 = !childNodesByType.isEmpty();
                    LighterASTNode lighterASTNode8 = lighterASTNode3;
                    if (lighterASTNode8 != null) {
                        z5 = !hasValueParameters(lighterASTNode8) ? true : z4;
                    } else if (!childNodesByType.isEmpty()) {
                        List<LighterASTNode> list = childNodesByType;
                        if (!(list instanceof Collection) || !list.isEmpty()) {
                            Iterator<T> it = list.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (!hasValueParameters((LighterASTNode) it.next())) {
                                    }
                                }
                            }
                        }
                    }
                    FirResolvedTypeRef firResolvedTypeRef = delegatedSelfType;
                    FirImplicitTypeRefImplWithoutSource firImplicitTypeRefImplWithoutSource = (FirTypeRef) objectRef3.element;
                    if (firImplicitTypeRefImplWithoutSource == null) {
                        firImplicitTypeRefImplWithoutSource = FirImplicitTypeRefImplWithoutSource.INSTANCE;
                    }
                    FirTypeRef firTypeRef = firImplicitTypeRefImplWithoutSource;
                    if (delegationSpecifiersConvertDelegationSpecifiers == null || (listEmptyList = delegationSpecifiersConvertDelegationSpecifiers.getSuperTypeCalls()) == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    ClassWrapper classWrapper = new ClassWrapper(modifierConvertModifierList, classKind, firRegularClassBuilder3, z7, z5, firResolvedTypeRef, firTypeRef, listEmptyList);
                    KtSourceElement source2 = delegatedSelfType.getSource();
                    boolean zIsExpect = firDeclarationStatusImpl.isExpect();
                    if (firDeclarationStatusImpl.isActual() && (firDeclarationStatusImpl.isInline() || classKind == ClassKind.ANNOTATION_CLASS)) {
                        z4 = true;
                    }
                    Modifier modifier2 = modifierConvertModifierList;
                    ArrayList arrayList5 = arrayList;
                    lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder2;
                    Ref.ObjectRef objectRef4 = objectRef;
                    try {
                        PrimaryConstructor primaryConstructorConvertPrimaryConstructor$default = convertPrimaryConstructor$default(this, classNode, lighterASTNode8, source2, classWrapper, source, false, zIsExpect, z4, 32, null);
                        final FirConstructor firConstructor = primaryConstructorConvertPrimaryConstructor$default != null ? primaryConstructorConvertPrimaryConstructor$default.getFirConstructor() : null;
                        if (firConstructor != null) {
                            firRegularClassBuilder2.getDeclarations().add(firConstructor);
                            Unit unit6 = Unit.INSTANCE;
                            Unit unit7 = Unit.INSTANCE;
                        }
                        if (delegationSpecifiersConvertDelegationSpecifiers != null && (delegateFieldsMap2 = delegationSpecifiersConvertDelegationSpecifiers.getDelegateFieldsMap()) != null && (collectionValues = delegateFieldsMap2.values()) != null) {
                            List<FirDeclaration> declarations = firRegularClassBuilder2.getDeclarations();
                            Iterator<T> it2 = collectionValues.iterator();
                            while (it2.hasNext()) {
                                declarations.add((FirField) ((FirFieldSymbol) it2.next()).getFir());
                            }
                            List<FirDeclaration> list2 = declarations;
                        }
                        objectRef4.element = (delegationSpecifiersConvertDelegationSpecifiers == null || (delegateFieldsMap = delegationSpecifiersConvertDelegationSpecifiers.getDelegateFieldsMap()) == null || !Boolean.valueOf(delegateFieldsMap.isEmpty() ^ true).booleanValue()) ? 0 : delegateFieldsMap;
                        ArrayList arrayList6 = new ArrayList();
                        if (lighterASTNode8 == null || firConstructor == null) {
                            firRegularClassBuilder = firRegularClassBuilder2;
                        } else {
                            ArrayList arrayList7 = arrayList6;
                            List<ValueParameter> valueParameters = primaryConstructorConvertPrimaryConstructor$default.getValueParameters();
                            ArrayList arrayList8 = new ArrayList();
                            for (Object obj : valueParameters) {
                                if (((ValueParameter) obj).hasValOrVar()) {
                                    arrayList8.add(obj);
                                }
                            }
                            ArrayList<ValueParameter> arrayList9 = arrayList8;
                            ArrayList arrayList10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList9, 10));
                            for (ValueParameter valueParameter : arrayList9) {
                                arrayList10.add(valueParameter.toFirPropertyFromPrimaryConstructor(getBaseModuleData(), callableIdForName(valueParameter.getFirValueParameter().getName()), classWrapper.hasExpect(), currentDispatchReceiverType(), getContext()));
                            }
                            CollectionsKt.addAll(arrayList7, arrayList10);
                            firRegularClassBuilder = firRegularClassBuilder2;
                            FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder, arrayList6);
                        }
                        if (lighterASTNode7 != null) {
                            FirDeclarationBuildingUtilsKt.addDeclarations(firRegularClassBuilder, convertClassBody(lighterASTNode7, classWrapper));
                            Unit unit8 = Unit.INSTANCE;
                            Unit unit9 = Unit.INSTANCE;
                        }
                        if (modifier2.isDataClass() && firConstructor != null) {
                            ArrayList<FirProperty> arrayList11 = arrayList6;
                            ArrayList arrayList12 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList11, 10));
                            for (FirProperty firProperty : arrayList11) {
                                KtSourceElement source3 = firProperty.getSource();
                                Intrinsics.checkNotNull(source3);
                                arrayList12.add(TuplesKt.to(source3.getLighterASTNode(), firProperty));
                            }
                            new AbstractRawFirBuilder.DataClassMembersGenerator(this, classNode, firRegularClassBuilder, arrayList12, getContext().getPackageFqName(), getContext().getClassName(), new Function1<KtFakeSourceElementKind, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirDeclarationBuilder$convertClass$2$2$1$10
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final FirTypeRef invoke(KtFakeSourceElementKind it3) {
                                    Intrinsics.checkNotNullParameter(it3, "it");
                                    return firConstructor.getReturnTypeRef();
                                }
                            }, new Function2<FirProperty, KtFakeSourceElementKind, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirDeclarationBuilder$convertClass$2$2$1$11
                                @Override // kotlin.jvm.functions.Function2
                                public final FirTypeRef invoke(FirProperty property, KtFakeSourceElementKind ktFakeSourceElementKind) {
                                    Intrinsics.checkNotNullParameter(property, "property");
                                    Intrinsics.checkNotNullParameter(ktFakeSourceElementKind, "<anonymous parameter 1>");
                                    return property.getReturnTypeRef();
                                }
                            }, new Function2<FirValueParameterBuilder, LighterASTNode, Unit>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirDeclarationBuilder$convertClass$2$2$1$12
                                {
                                    super(2);
                                }

                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(FirValueParameterBuilder $receiver, LighterASTNode valueParam) {
                                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                                    Intrinsics.checkNotNullParameter(valueParam, "valueParam");
                                    LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder4 = this.this$0;
                                    KtToken[] ktTokenArr2 = new KtToken[0];
                                    for (LighterASTNode lighterASTNode9 : lightTreeRawFirDeclarationBuilder4.getChildrenAsArray(valueParam)) {
                                        if (lighterASTNode9 == null) {
                                            return;
                                        }
                                        IElementType tokenType6 = lighterASTNode9.getTokenType();
                                        if (!KtTokens.COMMENTS.contains(tokenType6) && !Intrinsics.areEqual(tokenType6, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType6, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr2, tokenType6) && !Intrinsics.areEqual(tokenType6, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode9.getTokenType(), KtNodeTypes.MODIFIER_LIST)) {
                                            List<FirAnnotationCall> annotations = LightTreeRawFirDeclarationBuilder.convertModifierList$default(lightTreeRawFirDeclarationBuilder4, lighterASTNode9, false, 2, null).getAnnotations();
                                            Collection annotations2 = $receiver.getAnnotations();
                                            for (Object obj2 : annotations) {
                                                if (ConversionUtilsKt.appliesToPrimaryConstructorParameter(((FirAnnotationCall) obj2).getUseSiteTarget())) {
                                                    annotations2.add(obj2);
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public /* bridge */ /* synthetic */ Unit invoke(FirValueParameterBuilder firValueParameterBuilder, LighterASTNode lighterASTNode9) {
                                    invoke2(firValueParameterBuilder, lighterASTNode9);
                                    return Unit.INSTANCE;
                                }
                            }).generate();
                        }
                        if (modifier2.isEnum()) {
                            EnumClassUtilsKt.generateValuesFunction$default(firRegularClassBuilder, getBaseModuleData(), getContext().getPackageFqName(), getContext().getClassName(), modifier2.hasExpect(), null, 16, null);
                            EnumClassUtilsKt.generateValueOfFunction$default(firRegularClassBuilder, getBaseModuleData(), getContext().getPackageFqName(), getContext().getClassName(), modifier2.hasExpect(), null, 16, null);
                            EnumClassUtilsKt.generateEntriesGetter$default(firRegularClassBuilder, getBaseModuleData(), getContext().getPackageFqName(), getContext().getClassName(), modifier2.hasExpect(), null, 16, null);
                        }
                        initCompanionObjectSymbolAttr(firRegularClassBuilder);
                        firRegularClassBuilder.getContextReceivers().addAll(convertContextReceivers(classNode));
                        FirRegularClass firRegularClassMo6809build = firRegularClassBuilder.mo6809build();
                        DelegateFieldsMapKt.setDelegateFieldsMap(firRegularClassMo6809build, (Map) objectRef4.element);
                        try {
                            lightTreeRawFirDeclarationBuilder3.getContext().popFirTypeParameters();
                            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                                throw new IllegalArgumentException((str2 + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                            }
                            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > size) {
                                lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                            }
                            Context<LighterASTNode> context2 = lightTreeRawFirDeclarationBuilder.getContext();
                            FqName fqNameParent = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                            context2.setClassName(fqNameParent);
                            lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(forcedLocalContext);
                            lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
                            LighterASTNode parent4 = getParent(classNode);
                            if (Intrinsics.areEqual(parent4 != null ? getElementType(parent4) : null, KtStubElementTypes.CLASS_BODY)) {
                                initContainingClassForLocalAttr(firRegularClassMo6809build);
                            }
                            FirRegularClass firRegularClass = firRegularClassMo6809build;
                            fillDanglingConstraintsTo(arrayList5, arrayList3, firRegularClass);
                            return firRegularClass;
                        } catch (Throwable th) {
                            th = th;
                            str = "parent(...)";
                            str2 = str2;
                            i = size;
                            z3 = containerIsExpect;
                            z2 = forcedLocalContext;
                            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() <= i + 1) {
                                throw new IllegalArgumentException((str2 + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                            }
                            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i) {
                                lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                            }
                            Context<LighterASTNode> context3 = lightTreeRawFirDeclarationBuilder.getContext();
                            FqName fqNameParent2 = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                            Intrinsics.checkNotNullExpressionValue(fqNameParent2, str);
                            context3.setClassName(fqNameParent2);
                            lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(z2);
                            lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(z3);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        str = "parent(...)";
                        i = size;
                        z3 = containerIsExpect;
                        z2 = forcedLocalContext;
                        try {
                            lightTreeRawFirDeclarationBuilder3.getContext().popFirTypeParameters();
                            throw th;
                        } catch (Throwable th3) {
                            th = th3;
                            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() <= i + 1) {
                                throw new IllegalArgumentException((str2 + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                            }
                            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i) {
                                lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                            }
                            Context<LighterASTNode> context4 = lightTreeRawFirDeclarationBuilder.getContext();
                            FqName fqNameParent3 = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                            Intrinsics.checkNotNullExpressionValue(fqNameParent3, str);
                            context4.setClassName(fqNameParent3);
                            lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(z2);
                            lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(z3);
                            throw th;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    str = "parent(...)";
                    lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder2;
                    z3 = containerIsExpect;
                    i = size;
                    z2 = forcedLocalContext;
                }
            } catch (Throwable th5) {
                th = th5;
                str = "parent(...)";
                lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder2;
                z3 = containerIsExpect;
                i = size;
                z2 = forcedLocalContext;
                str2 = str2;
            }
        } catch (Throwable th6) {
            th = th6;
            i = size;
            z2 = forcedLocalContext;
            str = "parent(...)";
            str2 = "Wrong number of ";
            lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder2;
            z3 = containerIsExpect;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v57, types: [T, java.util.List] */
    public final FirElement convertObjectLiteral(LighterASTNode objectLiteral) throws Throwable {
        int i;
        boolean z;
        boolean z2;
        String str;
        int i2;
        KtToken[] ktTokenArr;
        Intrinsics.checkNotNullParameter(objectLiteral, "objectLiteral");
        Name name2 = SpecialNames.ANONYMOUS;
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder = this;
        Context<LighterASTNode> context = lightTreeRawFirDeclarationBuilder.getContext();
        FqName fqNameChild = lightTreeRawFirDeclarationBuilder.getContext().getClassName().child(name2);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        context.setClassName(fqNameChild);
        boolean forcedLocalContext = lightTreeRawFirDeclarationBuilder.getContext().getForcedLocalContext();
        lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(lightTreeRawFirDeclarationBuilder.getContext().getForcedLocalContext());
        boolean containerIsExpect = lightTreeRawFirDeclarationBuilder.getContext().getContainerIsExpect();
        lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
        int size = lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size();
        try {
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            FirAnonymousObjectExpressionBuilder firAnonymousObjectExpressionBuilder = new FirAnonymousObjectExpressionBuilder();
            firAnonymousObjectExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, objectLiteral, null, 1, null));
            FirAnonymousObjectBuilder firAnonymousObjectBuilder = new FirAnonymousObjectBuilder();
            IElementType OBJECT_DECLARATION = KtNodeTypes.OBJECT_DECLARATION;
            Intrinsics.checkNotNullExpressionValue(OBJECT_DECLARATION, "OBJECT_DECLARATION");
            LighterASTNode lighterASTNode = (LighterASTNode) CollectionsKt.first((List) getChildNodesByType(objectLiteral, OBJECT_DECLARATION));
            firAnonymousObjectBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null));
            firAnonymousObjectBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firAnonymousObjectBuilder.setModuleData(getBaseModuleData());
            firAnonymousObjectBuilder.setClassKind(ClassKind.CLASS);
            firAnonymousObjectBuilder.setScopeProvider(this.baseScopeProvider);
            firAnonymousObjectBuilder.setSymbol(new FirAnonymousObjectSymbol());
            firAnonymousObjectBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
            getContext().appendOuterTypeParameters(false, firAnonymousObjectBuilder.getTypeParameters());
            FirResolvedTypeRef delegatedSelfType = toDelegatedSelfType(objectLiteral, firAnonymousObjectBuilder);
            registerSelfType(delegatedSelfType);
            Modifier modifier = new Modifier(0L, 1, null);
            ArrayList arrayList = new ArrayList();
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            KtToken[] ktTokenArr2 = new KtToken[0];
            LighterASTNode[] childrenAsArray = getChildrenAsArray(lighterASTNode);
            int length = childrenAsArray.length;
            Modifier modifierConvertModifierList$default = modifier;
            int i3 = 0;
            LighterASTNode lighterASTNode2 = null;
            FirTypeRef implicitAnyType = null;
            List<DelegatedConstructorWrapper> superTypeCalls = null;
            LighterASTNode lighterASTNode3 = null;
            KtLightSourceElement ktLightSourceElement = null;
            while (i3 < length) {
                int i4 = length;
                try {
                    LighterASTNode lighterASTNode4 = childrenAsArray[i3];
                    if (lighterASTNode4 == null) {
                        break;
                    }
                    LighterASTNode[] lighterASTNodeArr = childrenAsArray;
                    IElementType tokenType = lighterASTNode4.getTokenType();
                    i2 = size;
                    try {
                        if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr2, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                            ktTokenArr = ktTokenArr2;
                        } else {
                            IElementType tokenType2 = lighterASTNode4.getTokenType();
                            if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                                ktTokenArr = ktTokenArr2;
                                modifierConvertModifierList$default = convertModifierList$default(this, lighterASTNode4, false, 2, null);
                            } else {
                                ktTokenArr = ktTokenArr2;
                                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.PRIMARY_CONSTRUCTOR)) {
                                    lighterASTNode3 = lighterASTNode4;
                                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.SUPER_TYPE_LIST)) {
                                    DelegationSpecifiers delegationSpecifiersConvertDelegationSpecifiers = convertDelegationSpecifiers(lighterASTNode4);
                                    DelegatedConstructorWrapper delegatedConstructorWrapper = (DelegatedConstructorWrapper) CollectionsKt.lastOrNull((List) delegationSpecifiersConvertDelegationSpecifiers.getSuperTypeCalls());
                                    FirTypeRef delegatedSuperTypeRef = delegatedConstructorWrapper != null ? delegatedConstructorWrapper.getDelegatedSuperTypeRef() : null;
                                    CollectionsKt.addAll(arrayList, delegationSpecifiersConvertDelegationSpecifiers.getSuperTypesRef());
                                    DelegatedConstructorWrapper delegatedConstructorWrapper2 = (DelegatedConstructorWrapper) CollectionsKt.lastOrNull((List) delegationSpecifiersConvertDelegationSpecifiers.getSuperTypeCalls());
                                    KtLightSourceElement source = delegatedConstructorWrapper2 != null ? delegatedConstructorWrapper2.getSource() : null;
                                    Collection<FirFieldSymbol> collectionValues = delegationSpecifiersConvertDelegationSpecifiers.getDelegateFieldsMap().values();
                                    implicitAnyType = delegatedSuperTypeRef;
                                    KtLightSourceElement ktLightSourceElement2 = source;
                                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
                                    Iterator<T> it = collectionValues.iterator();
                                    while (it.hasNext()) {
                                        arrayList2.add((FirField) ((FirFieldSymbol) it.next()).getFir());
                                    }
                                    objectRef2.element = arrayList2;
                                    Map<Integer, FirFieldSymbol> delegateFieldsMap = delegationSpecifiersConvertDelegationSpecifiers.getDelegateFieldsMap();
                                    objectRef.element = Boolean.valueOf(delegateFieldsMap.isEmpty() ^ true).booleanValue() ? delegateFieldsMap : 0;
                                    ktLightSourceElement = ktLightSourceElement2;
                                    superTypeCalls = delegationSpecifiersConvertDelegationSpecifiers.getSuperTypeCalls();
                                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CLASS_BODY)) {
                                    lighterASTNode2 = lighterASTNode4;
                                }
                            }
                        }
                        i3++;
                        childrenAsArray = lighterASTNodeArr;
                        ktTokenArr2 = ktTokenArr;
                        length = i4;
                        size = i2;
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    i2 = size;
                    z = containerIsExpect;
                    str = "Wrong number of ";
                    z2 = forcedLocalContext;
                    i = i2;
                }
                th = th;
                z = containerIsExpect;
                str = "Wrong number of ";
                z2 = forcedLocalContext;
                i = i2;
                if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i + 1) {
                    throw new IllegalArgumentException((str + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                }
                if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i) {
                    lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                }
                Context<LighterASTNode> context2 = lightTreeRawFirDeclarationBuilder.getContext();
                FqName fqNameParent = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                context2.setClassName(fqNameParent);
                lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(z2);
                lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(z);
                throw th;
            }
            i2 = size;
            if (arrayList.isEmpty()) {
                arrayList.add(getImplicitAnyType());
                implicitAnyType = getImplicitAnyType();
            }
            FirTypeRef firTypeRef = implicitAnyType;
            FirTypeRef firTypeRef2 = implicitAnyType == null ? FirImplicitTypeRefImplWithoutSource.INSTANCE : implicitAnyType;
            CollectionsKt.addAll(firAnonymousObjectBuilder.getAnnotations(), modifierConvertModifierList$default.getAnnotations());
            CollectionsKt.addAll(firAnonymousObjectBuilder.getSuperTypeRefs(), arrayList);
            ClassKind classKind = ClassKind.OBJECT;
            FirAnonymousObjectBuilder firAnonymousObjectBuilder2 = firAnonymousObjectBuilder;
            IElementType SECONDARY_CONSTRUCTOR = KtNodeTypes.SECONDARY_CONSTRUCTOR;
            Intrinsics.checkNotNullExpressionValue(SECONDARY_CONSTRUCTOR, "SECONDARY_CONSTRUCTOR");
            ClassWrapper classWrapper = new ClassWrapper(modifierConvertModifierList$default, classKind, firAnonymousObjectBuilder2, !getChildNodesByType(lighterASTNode2, SECONDARY_CONSTRUCTOR).isEmpty(), false, delegatedSelfType, firTypeRef2, superTypeCalls == null ? CollectionsKt.emptyList() : superTypeCalls);
            LighterASTNode lighterASTNode5 = lighterASTNode2;
            try {
                PrimaryConstructor primaryConstructorConvertPrimaryConstructor$default = convertPrimaryConstructor$default(this, lighterASTNode, lighterASTNode3, delegatedSelfType.getSource(), classWrapper, ktLightSourceElement, false, false, false, 160, null);
                if (primaryConstructorConvertPrimaryConstructor$default != null) {
                    firAnonymousObjectBuilder.getDeclarations().add(primaryConstructorConvertPrimaryConstructor$default.getFirConstructor());
                }
                List list = (List) objectRef2.element;
                if (list != null) {
                    CollectionsKt.addAll(firAnonymousObjectBuilder.getDeclarations(), list);
                }
                if (lighterASTNode5 != null) {
                    CollectionsKt.addAll(firAnonymousObjectBuilder.getDeclarations(), convertClassBody(lighterASTNode5, classWrapper));
                }
                FirAnonymousObject firAnonymousObjectMo6809build = firAnonymousObjectBuilder.mo6809build();
                DelegateFieldsMapKt.setDelegateFieldsMap(firAnonymousObjectMo6809build, (Map) objectRef.element);
                firAnonymousObjectExpressionBuilder.setAnonymousObject(firAnonymousObjectMo6809build);
                FirAnonymousObjectExpression firAnonymousObjectExpressionMo6809build = firAnonymousObjectExpressionBuilder.mo6809build();
                if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2 + 1) {
                    throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                }
                if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2) {
                    lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                }
                Context<LighterASTNode> context3 = lightTreeRawFirDeclarationBuilder.getContext();
                FqName fqNameParent2 = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
                context3.setClassName(fqNameParent2);
                lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(forcedLocalContext);
                lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
                return firAnonymousObjectExpressionMo6809build;
            } catch (Throwable th3) {
                th = th3;
                str = "Wrong number of ";
                z2 = forcedLocalContext;
                i = i2;
                z = containerIsExpect;
            }
        } catch (Throwable th4) {
            th = th4;
            i = size;
            z = containerIsExpect;
            z2 = forcedLocalContext;
            str = "Wrong number of ";
        }
    }

    /* JADX WARN: Code duplicated, block: B:101:0x047b  */
    /* JADX WARN: Code duplicated, block: B:105:0x04d7  */
    /* JADX WARN: Code duplicated, block: B:122:0x0534  */
    /* JADX WARN: Code duplicated, block: B:124:0x0542  */
    /* JADX WARN: Code duplicated, block: B:127:0x057e  */
    /* JADX WARN: Code duplicated, block: B:136:0x025a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:0x014d  */
    /* JADX WARN: Code duplicated, block: B:65:0x0275  */
    /* JADX WARN: Code duplicated, block: B:68:0x02a4 A[Catch: all -> 0x0502, TRY_LEAVE, TryCatch #0 {all -> 0x0502, blocks: (B:61:0x0263, B:66:0x027a, B:68:0x02a4), top: B:129:0x0263 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x02af  */
    /* JADX WARN: Code duplicated, block: B:74:0x02dc A[Catch: all -> 0x04fa, TryCatch #3 {all -> 0x04fa, blocks: (B:72:0x02d6, B:74:0x02dc, B:76:0x02ef, B:78:0x034a, B:80:0x035a, B:82:0x0368, B:83:0x037f, B:84:0x03a5, B:85:0x03cb, B:87:0x03cd, B:89:0x03dd, B:91:0x03eb, B:92:0x0402, B:93:0x0426, B:94:0x0427, B:95:0x044d, B:96:0x044e, B:77:0x0337), top: B:135:0x02d6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:76:0x02ef A[Catch: all -> 0x04fa, TRY_LEAVE, TryCatch #3 {all -> 0x04fa, blocks: (B:72:0x02d6, B:74:0x02dc, B:76:0x02ef, B:78:0x034a, B:80:0x035a, B:82:0x0368, B:83:0x037f, B:84:0x03a5, B:85:0x03cb, B:87:0x03cd, B:89:0x03dd, B:91:0x03eb, B:92:0x0402, B:93:0x0426, B:94:0x0427, B:95:0x044d, B:96:0x044e, B:77:0x0337), top: B:135:0x02d6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:80:0x035a A[Catch: all -> 0x04fa, TryCatch #3 {all -> 0x04fa, blocks: (B:72:0x02d6, B:74:0x02dc, B:76:0x02ef, B:78:0x034a, B:80:0x035a, B:82:0x0368, B:83:0x037f, B:84:0x03a5, B:85:0x03cb, B:87:0x03cd, B:89:0x03dd, B:91:0x03eb, B:92:0x0402, B:93:0x0426, B:94:0x0427, B:95:0x044d, B:96:0x044e, B:77:0x0337), top: B:135:0x02d6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:82:0x0368 A[Catch: all -> 0x04fa, TryCatch #3 {all -> 0x04fa, blocks: (B:72:0x02d6, B:74:0x02dc, B:76:0x02ef, B:78:0x034a, B:80:0x035a, B:82:0x0368, B:83:0x037f, B:84:0x03a5, B:85:0x03cb, B:87:0x03cd, B:89:0x03dd, B:91:0x03eb, B:92:0x0402, B:93:0x0426, B:94:0x0427, B:95:0x044d, B:96:0x044e, B:77:0x0337), top: B:135:0x02d6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:84:0x03a5 A[Catch: all -> 0x04fa, TryCatch #3 {all -> 0x04fa, blocks: (B:72:0x02d6, B:74:0x02dc, B:76:0x02ef, B:78:0x034a, B:80:0x035a, B:82:0x0368, B:83:0x037f, B:84:0x03a5, B:85:0x03cb, B:87:0x03cd, B:89:0x03dd, B:91:0x03eb, B:92:0x0402, B:93:0x0426, B:94:0x0427, B:95:0x044d, B:96:0x044e, B:77:0x0337), top: B:135:0x02d6, inners: #5 }] */
    /* JADX WARN: Code duplicated, block: B:99:0x046d  */
    private final FirEnumEntry convertEnumEntry(LighterASTNode enumEntry, ClassWrapper classWrapper) throws Throwable {
        int i;
        String str;
        boolean forcedLocalContext;
        boolean containerIsExpect;
        int size;
        int i2;
        boolean z;
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder;
        FirAnonymousObjectBuilder firAnonymousObjectBuilder;
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder2;
        KtLightSourceElement ktLightSourceElement;
        ClassWrapper classWrapper2;
        KtLightSourceElement ktLightSourceElement2;
        LighterASTNode lighterASTNode;
        PrimaryConstructor primaryConstructorConvertPrimaryConstructor$default;
        FirAnonymousObjectExpression firAnonymousObjectExpressionMo6809build;
        FirEnumEntryBuilder firEnumEntryBuilder;
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder3;
        boolean forcedLocalContext2;
        boolean containerIsExpect2;
        int size2;
        Modifier modifier = new Modifier(0L, 1, null);
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        Modifier modifierConvertModifierList$default = modifier;
        String asText = null;
        LighterASTNode lighterASTNode2 = null;
        LighterASTNode lighterASTNode3 = null;
        for (LighterASTNode lighterASTNode4 : getChildrenAsArray(enumEntry)) {
            if (lighterASTNode4 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode4.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode4.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifierConvertModifierList$default = convertModifierList$default(this, lighterASTNode4, false, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.IDENTIFIER)) {
                    asText = getAsText(lighterASTNode4);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.INITIALIZER_LIST)) {
                    CollectionsKt.addAll(arrayList, convertInitializerList(lighterASTNode4));
                    IElementType SUPER_TYPE_CALL_ENTRY = KtNodeTypes.SUPER_TYPE_CALL_ENTRY;
                    Intrinsics.checkNotNullExpressionValue(SUPER_TYPE_CALL_ENTRY, "SUPER_TYPE_CALL_ENTRY");
                    LighterASTNode childNodeByType = getChildNodeByType(lighterASTNode4, SUPER_TYPE_CALL_ENTRY);
                    if (childNodeByType != null) {
                        Unit unit = Unit.INSTANCE;
                        Unit unit2 = Unit.INSTANCE;
                        lighterASTNode2 = childNodeByType;
                    }
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CLASS_BODY)) {
                    lighterASTNode3 = lighterASTNode4;
                }
            }
        }
        if (asText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("identifier");
            i = 1;
            str = null;
            asText = null;
        } else {
            i = 1;
            str = null;
        }
        Name nameNameAsSafeName$default = ConverterUtilKt.nameAsSafeName$default(asText, str, i, str);
        boolean z2 = classWrapper.hasExpect() || getContext().getContainerIsExpect();
        FirEnumEntryBuilder firEnumEntryBuilder2 = new FirEnumEntryBuilder();
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder4 = this;
        firEnumEntryBuilder2.setSource(AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirDeclarationBuilder4, enumEntry, null, 1, null));
        firEnumEntryBuilder2.setModuleData(getBaseModuleData());
        firEnumEntryBuilder2.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firEnumEntryBuilder2.setReturnTypeRef(classWrapper.getDelegatedSelfTypeRef());
        firEnumEntryBuilder2.setName(nameNameAsSafeName$default);
        firEnumEntryBuilder2.setSymbol(new FirEnumEntrySymbol(new CallableId(getContext().getCurrentClassId(), nameNameAsSafeName$default)));
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL);
        firDeclarationStatusImpl.setStatic(true);
        firDeclarationStatusImpl.setExpect(z2);
        firEnumEntryBuilder2.setStatus(firDeclarationStatusImpl);
        if (classWrapper.getHasDefaultConstructor()) {
            IElementType INITIALIZER_LIST = KtNodeTypes.INITIALIZER_LIST;
            Intrinsics.checkNotNullExpressionValue(INITIALIZER_LIST, "INITIALIZER_LIST");
            if (getChildNodeByType(enumEntry, INITIALIZER_LIST) == null && modifierConvertModifierList$default.getAnnotations().isEmpty() && lighterASTNode3 == null) {
                firEnumEntryBuilder = firEnumEntryBuilder2;
            } else {
                CollectionsKt.addAll(firEnumEntryBuilder2.getAnnotations(), modifierConvertModifierList$default.getAnnotations());
                Context<LighterASTNode> context = lightTreeRawFirDeclarationBuilder4.getContext();
                FqName fqNameChild = lightTreeRawFirDeclarationBuilder4.getContext().getClassName().child(nameNameAsSafeName$default);
                Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
                context.setClassName(fqNameChild);
                forcedLocalContext = lightTreeRawFirDeclarationBuilder4.getContext().getForcedLocalContext();
                lightTreeRawFirDeclarationBuilder4.getContext().setForcedLocalContext(lightTreeRawFirDeclarationBuilder4.getContext().getForcedLocalContext());
                containerIsExpect = lightTreeRawFirDeclarationBuilder4.getContext().getContainerIsExpect();
                lightTreeRawFirDeclarationBuilder4.getContext().setContainerIsExpect(containerIsExpect);
                size = lightTreeRawFirDeclarationBuilder4.getContext().getDispatchReceiverTypesStack().size();
                try {
                    FirAnonymousObjectExpressionBuilder firAnonymousObjectExpressionBuilder = new FirAnonymousObjectExpressionBuilder();
                    KtLightSourceElement firSourceElement = toFirSourceElement(enumEntry, (KtFakeSourceElementKind) KtFakeSourceElementKind.EnumInitializer.INSTANCE);
                    i2 = size;
                    try {
                        firAnonymousObjectExpressionBuilder.setSource(firSourceElement);
                        firAnonymousObjectBuilder = new FirAnonymousObjectBuilder();
                        firAnonymousObjectBuilder.setSource(firSourceElement);
                        firAnonymousObjectBuilder.setModuleData(getBaseModuleData());
                        firAnonymousObjectBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                        firAnonymousObjectBuilder.setClassKind(ClassKind.ENUM_ENTRY);
                        firAnonymousObjectBuilder.setScopeProvider(this.baseScopeProvider);
                        firAnonymousObjectBuilder.setSymbol(new FirAnonymousObjectSymbol());
                        containerIsExpect = containerIsExpect;
                        try {
                            firAnonymousObjectBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
                            ClassKind classKind = ClassKind.ENUM_ENTRY;
                            FirAnonymousObjectBuilder firAnonymousObjectBuilder2 = firAnonymousObjectBuilder;
                            IElementType SECONDARY_CONSTRUCTOR = KtNodeTypes.SECONDARY_CONSTRUCTOR;
                            Intrinsics.checkNotNullExpressionValue(SECONDARY_CONSTRUCTOR, "SECONDARY_CONSTRUCTOR");
                            boolean z3 = !getChildNodesByType(lighterASTNode3, SECONDARY_CONSTRUCTOR).isEmpty();
                            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                            try {
                                firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(firAnonymousObjectBuilder.getSymbol().getLookupTag(), new ConeTypeProjection[0], false, null, 8, null));
                                Unit unit3 = Unit.INSTANCE;
                                FirResolvedTypeRef firResolvedTypeRefMo6809build = firResolvedTypeRefBuilder.mo6809build();
                                registerSelfType(firResolvedTypeRefMo6809build);
                                Unit unit4 = Unit.INSTANCE;
                                FirResolvedTypeRef firResolvedTypeRef = firResolvedTypeRefMo6809build;
                                FirTypeRef delegatedSelfTypeRef = classWrapper.getDelegatedSelfTypeRef();
                                FirTypeRef delegatedSelfTypeRef2 = classWrapper.getDelegatedSelfTypeRef();
                                if (lighterASTNode2 != null) {
                                    try {
                                        lightTreeRawFirDeclarationBuilder2 = lightTreeRawFirDeclarationBuilder4;
                                        try {
                                            ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode2, null, 1, null);
                                        } catch (Throwable th) {
                                            th = th;
                                            lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder2;
                                            z = forcedLocalContext;
                                            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2 + 1) {
                                                throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                                            }
                                            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2) {
                                                lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                                            }
                                            Context<LighterASTNode> context2 = lightTreeRawFirDeclarationBuilder.getContext();
                                            FqName fqNameParent = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                                            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                                            context2.setClassName(fqNameParent);
                                            lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(z);
                                            lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
                                            throw th;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        lightTreeRawFirDeclarationBuilder2 = lightTreeRawFirDeclarationBuilder4;
                                        lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder2;
                                        z = forcedLocalContext;
                                        if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2 + 1) {
                                            throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                                        }
                                        if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2) {
                                            lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                                        }
                                        Context<LighterASTNode> context3 = lightTreeRawFirDeclarationBuilder.getContext();
                                        FqName fqNameParent2 = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                                        Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
                                        context3.setClassName(fqNameParent2);
                                        lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(z);
                                        lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
                                        throw th;
                                    }
                                } else {
                                    lightTreeRawFirDeclarationBuilder2 = lightTreeRawFirDeclarationBuilder4;
                                    ktLightSourceElement = null;
                                }
                                classWrapper2 = new ClassWrapper(modifierConvertModifierList$default, classKind, firAnonymousObjectBuilder2, z3, false, firResolvedTypeRef, delegatedSelfTypeRef, CollectionsKt.listOf(new DelegatedConstructorWrapper(delegatedSelfTypeRef2, arrayList, ktLightSourceElement)));
                                firAnonymousObjectBuilder.getSuperTypeRefs().add(classWrapper2.getDelegatedSuperTypeRef());
                                KtSourceElement firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(this, enumEntry, null, 1, null);
                                if (lighterASTNode2 != null) {
                                    ktLightSourceElement2 = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode2, null, 1, null);
                                } else {
                                    ktLightSourceElement2 = null;
                                }
                                lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder2;
                                lighterASTNode = lighterASTNode3;
                                try {
                                    primaryConstructorConvertPrimaryConstructor$default = convertPrimaryConstructor$default(this, enumEntry, null, firSourceElement$default, classWrapper2, ktLightSourceElement2, true, z2, false, 128, null);
                                    if (primaryConstructorConvertPrimaryConstructor$default != null) {
                                        firAnonymousObjectBuilder.getDeclarations().add(primaryConstructorConvertPrimaryConstructor$default.getFirConstructor());
                                        Unit unit5 = Unit.INSTANCE;
                                        Unit unit6 = Unit.INSTANCE;
                                    }
                                    if (lighterASTNode != null) {
                                        Name name2 = SpecialNames.ANONYMOUS;
                                        lightTreeRawFirDeclarationBuilder3 = this;
                                        Context<LighterASTNode> context4 = lightTreeRawFirDeclarationBuilder3.getContext();
                                        FqName fqNameChild2 = lightTreeRawFirDeclarationBuilder3.getContext().getClassName().child(name2);
                                        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
                                        context4.setClassName(fqNameChild2);
                                        forcedLocalContext2 = lightTreeRawFirDeclarationBuilder3.getContext().getForcedLocalContext();
                                        lightTreeRawFirDeclarationBuilder3.getContext().setForcedLocalContext(true);
                                        containerIsExpect2 = lightTreeRawFirDeclarationBuilder3.getContext().getContainerIsExpect();
                                        lightTreeRawFirDeclarationBuilder3.getContext().setContainerIsExpect(containerIsExpect2);
                                        size2 = lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size();
                                        try {
                                            CollectionsKt.addAll(firAnonymousObjectBuilder.getDeclarations(), convertClassBody(lighterASTNode, classWrapper2));
                                            Unit unit7 = Unit.INSTANCE;
                                            if (lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size() <= size2 + 1) {
                                                throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size()).toString());
                                            }
                                            if (lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size() > size2) {
                                                lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack()));
                                            }
                                            Context<LighterASTNode> context5 = lightTreeRawFirDeclarationBuilder3.getContext();
                                            FqName fqNameParent3 = lightTreeRawFirDeclarationBuilder3.getContext().getClassName().parent();
                                            Intrinsics.checkNotNullExpressionValue(fqNameParent3, "parent(...)");
                                            context5.setClassName(fqNameParent3);
                                            lightTreeRawFirDeclarationBuilder3.getContext().setForcedLocalContext(forcedLocalContext2);
                                            lightTreeRawFirDeclarationBuilder3.getContext().setContainerIsExpect(containerIsExpect2);
                                        } catch (Throwable th3) {
                                            if (lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size() > size2 + 1) {
                                                throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size()).toString());
                                            }
                                            if (lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size() > size2) {
                                                lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack()));
                                            }
                                            Context<LighterASTNode> context6 = lightTreeRawFirDeclarationBuilder3.getContext();
                                            FqName fqNameParent4 = lightTreeRawFirDeclarationBuilder3.getContext().getClassName().parent();
                                            Intrinsics.checkNotNullExpressionValue(fqNameParent4, "parent(...)");
                                            context6.setClassName(fqNameParent4);
                                            lightTreeRawFirDeclarationBuilder3.getContext().setForcedLocalContext(forcedLocalContext2);
                                            lightTreeRawFirDeclarationBuilder3.getContext().setContainerIsExpect(containerIsExpect2);
                                            throw th3;
                                        }
                                    }
                                    firAnonymousObjectExpressionBuilder.setAnonymousObject(firAnonymousObjectBuilder.mo6809build());
                                    firAnonymousObjectExpressionMo6809build = firAnonymousObjectExpressionBuilder.mo6809build();
                                    if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() <= i2 + 1) {
                                        throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                                    }
                                    if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2) {
                                        lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                                    }
                                    Context<LighterASTNode> context7 = lightTreeRawFirDeclarationBuilder.getContext();
                                    FqName fqNameParent5 = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                                    Intrinsics.checkNotNullExpressionValue(fqNameParent5, "parent(...)");
                                    context7.setClassName(fqNameParent5);
                                    lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(forcedLocalContext);
                                    lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
                                    firEnumEntryBuilder = firEnumEntryBuilder2;
                                    firEnumEntryBuilder.setInitializer(firAnonymousObjectExpressionMo6809build);
                                } catch (Throwable th4) {
                                    th = th4;
                                    i2 = i2;
                                    containerIsExpect = containerIsExpect;
                                    z = forcedLocalContext;
                                    if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2 + 1) {
                                        throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                                    }
                                    if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2) {
                                        lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                                    }
                                    Context<LighterASTNode> context8 = lightTreeRawFirDeclarationBuilder.getContext();
                                    FqName fqNameParent6 = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                                    Intrinsics.checkNotNullExpressionValue(fqNameParent6, "parent(...)");
                                    context8.setClassName(fqNameParent6);
                                    lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(z);
                                    lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
                                    throw th;
                                }
                            } catch (Throwable th5) {
                                th = th5;
                                lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder4;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            z = forcedLocalContext;
                            lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder4;
                            i2 = i2;
                            containerIsExpect = containerIsExpect;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        z = forcedLocalContext;
                        lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder4;
                        i2 = i2;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    i2 = size;
                    z = forcedLocalContext;
                    lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder4;
                }
            }
        } else {
            CollectionsKt.addAll(firEnumEntryBuilder2.getAnnotations(), modifierConvertModifierList$default.getAnnotations());
            Context<LighterASTNode> context9 = lightTreeRawFirDeclarationBuilder4.getContext();
            FqName fqNameChild3 = lightTreeRawFirDeclarationBuilder4.getContext().getClassName().child(nameNameAsSafeName$default);
            Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
            context9.setClassName(fqNameChild3);
            forcedLocalContext = lightTreeRawFirDeclarationBuilder4.getContext().getForcedLocalContext();
            lightTreeRawFirDeclarationBuilder4.getContext().setForcedLocalContext(lightTreeRawFirDeclarationBuilder4.getContext().getForcedLocalContext());
            containerIsExpect = lightTreeRawFirDeclarationBuilder4.getContext().getContainerIsExpect();
            lightTreeRawFirDeclarationBuilder4.getContext().setContainerIsExpect(containerIsExpect);
            size = lightTreeRawFirDeclarationBuilder4.getContext().getDispatchReceiverTypesStack().size();
            FirAnonymousObjectExpressionBuilder firAnonymousObjectExpressionBuilder2 = new FirAnonymousObjectExpressionBuilder();
            KtLightSourceElement firSourceElement2 = toFirSourceElement(enumEntry, (KtFakeSourceElementKind) KtFakeSourceElementKind.EnumInitializer.INSTANCE);
            i2 = size;
            firAnonymousObjectExpressionBuilder2.setSource(firSourceElement2);
            firAnonymousObjectBuilder = new FirAnonymousObjectBuilder();
            firAnonymousObjectBuilder.setSource(firSourceElement2);
            firAnonymousObjectBuilder.setModuleData(getBaseModuleData());
            firAnonymousObjectBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firAnonymousObjectBuilder.setClassKind(ClassKind.ENUM_ENTRY);
            firAnonymousObjectBuilder.setScopeProvider(this.baseScopeProvider);
            firAnonymousObjectBuilder.setSymbol(new FirAnonymousObjectSymbol());
            containerIsExpect = containerIsExpect;
            firAnonymousObjectBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
            ClassKind classKind2 = ClassKind.ENUM_ENTRY;
            FirAnonymousObjectBuilder firAnonymousObjectBuilder3 = firAnonymousObjectBuilder;
            IElementType SECONDARY_CONSTRUCTOR2 = KtNodeTypes.SECONDARY_CONSTRUCTOR;
            Intrinsics.checkNotNullExpressionValue(SECONDARY_CONSTRUCTOR2, "SECONDARY_CONSTRUCTOR");
            boolean z4 = !getChildNodesByType(lighterASTNode3, SECONDARY_CONSTRUCTOR2).isEmpty();
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder2.setType(new ConeClassLikeTypeImpl(firAnonymousObjectBuilder.getSymbol().getLookupTag(), new ConeTypeProjection[0], false, null, 8, null));
            Unit unit8 = Unit.INSTANCE;
            FirResolvedTypeRef firResolvedTypeRefMo6809build2 = firResolvedTypeRefBuilder2.mo6809build();
            registerSelfType(firResolvedTypeRefMo6809build2);
            Unit unit9 = Unit.INSTANCE;
            FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRefMo6809build2;
            FirTypeRef delegatedSelfTypeRef3 = classWrapper.getDelegatedSelfTypeRef();
            FirTypeRef delegatedSelfTypeRef4 = classWrapper.getDelegatedSelfTypeRef();
            if (lighterASTNode2 != null) {
                lightTreeRawFirDeclarationBuilder2 = lightTreeRawFirDeclarationBuilder4;
                ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode2, null, 1, null);
            } else {
                lightTreeRawFirDeclarationBuilder2 = lightTreeRawFirDeclarationBuilder4;
                ktLightSourceElement = null;
            }
            classWrapper2 = new ClassWrapper(modifierConvertModifierList$default, classKind2, firAnonymousObjectBuilder3, z4, false, firResolvedTypeRef2, delegatedSelfTypeRef3, CollectionsKt.listOf(new DelegatedConstructorWrapper(delegatedSelfTypeRef4, arrayList, ktLightSourceElement)));
            firAnonymousObjectBuilder.getSuperTypeRefs().add(classWrapper2.getDelegatedSuperTypeRef());
            KtSourceElement firSourceElement$default2 = AbstractRawFirBuilder.toFirSourceElement$default(this, enumEntry, null, 1, null);
            if (lighterASTNode2 != null) {
                ktLightSourceElement2 = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode2, null, 1, null);
            } else {
                ktLightSourceElement2 = null;
            }
            lightTreeRawFirDeclarationBuilder = lightTreeRawFirDeclarationBuilder2;
            lighterASTNode = lighterASTNode3;
            primaryConstructorConvertPrimaryConstructor$default = convertPrimaryConstructor$default(this, enumEntry, null, firSourceElement$default2, classWrapper2, ktLightSourceElement2, true, z2, false, 128, null);
            if (primaryConstructorConvertPrimaryConstructor$default != null) {
                firAnonymousObjectBuilder.getDeclarations().add(primaryConstructorConvertPrimaryConstructor$default.getFirConstructor());
                Unit unit10 = Unit.INSTANCE;
                Unit unit11 = Unit.INSTANCE;
            }
            if (lighterASTNode != null) {
                Name name3 = SpecialNames.ANONYMOUS;
                lightTreeRawFirDeclarationBuilder3 = this;
                Context<LighterASTNode> context10 = lightTreeRawFirDeclarationBuilder3.getContext();
                FqName fqNameChild4 = lightTreeRawFirDeclarationBuilder3.getContext().getClassName().child(name3);
                Intrinsics.checkNotNullExpressionValue(fqNameChild4, "child(...)");
                context10.setClassName(fqNameChild4);
                forcedLocalContext2 = lightTreeRawFirDeclarationBuilder3.getContext().getForcedLocalContext();
                lightTreeRawFirDeclarationBuilder3.getContext().setForcedLocalContext(true);
                containerIsExpect2 = lightTreeRawFirDeclarationBuilder3.getContext().getContainerIsExpect();
                lightTreeRawFirDeclarationBuilder3.getContext().setContainerIsExpect(containerIsExpect2);
                size2 = lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size();
                CollectionsKt.addAll(firAnonymousObjectBuilder.getDeclarations(), convertClassBody(lighterASTNode, classWrapper2));
                Unit unit12 = Unit.INSTANCE;
                if (lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size() <= size2 + 1) {
                    throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size()).toString());
                }
                if (lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().size() > size2) {
                    lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder3.getContext().getDispatchReceiverTypesStack()));
                }
                Context<LighterASTNode> context11 = lightTreeRawFirDeclarationBuilder3.getContext();
                FqName fqNameParent7 = lightTreeRawFirDeclarationBuilder3.getContext().getClassName().parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent7, "parent(...)");
                context11.setClassName(fqNameParent7);
                lightTreeRawFirDeclarationBuilder3.getContext().setForcedLocalContext(forcedLocalContext2);
                lightTreeRawFirDeclarationBuilder3.getContext().setContainerIsExpect(containerIsExpect2);
            }
            firAnonymousObjectExpressionBuilder2.setAnonymousObject(firAnonymousObjectBuilder.mo6809build());
            firAnonymousObjectExpressionMo6809build = firAnonymousObjectExpressionBuilder2.mo6809build();
            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() <= i2 + 1) {
                throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
            }
            if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > i2) {
                lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
            }
            Context<LighterASTNode> context12 = lightTreeRawFirDeclarationBuilder.getContext();
            FqName fqNameParent8 = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent8, "parent(...)");
            context12.setClassName(fqNameParent8);
            lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(forcedLocalContext);
            lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
            firEnumEntryBuilder = firEnumEntryBuilder2;
            firEnumEntryBuilder.setInitializer(firAnonymousObjectExpressionMo6809build);
        }
        FirEnumEntry firEnumEntryMo6809build = firEnumEntryBuilder.mo6809build();
        ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = currentDispatchReceiverType();
        Intrinsics.checkNotNull(coneClassLikeTypeCurrentDispatchReceiverType);
        ClassMembersKt.setContainingClassForStaticMemberAttr(firEnumEntryMo6809build, coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag());
        return firEnumEntryMo6809build;
    }

    private final List<FirExpression> convertInitializerList(LighterASTNode initializerList) {
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(initializerList)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.SUPER_TYPE_CALL_ENTRY)) {
                CollectionsKt.addAll(arrayList, convertConstructorInvocation(lighterASTNode).getSecond());
            }
        }
        return arrayList;
    }

    private final List<FirDeclaration> convertClassBody(LighterASTNode classBody, ClassWrapper classWrapper) {
        ArrayList arrayList = new ArrayList();
        LighterASTNode[] childrenAsArray = getChildrenAsArray(classBody);
        ArrayList arrayList2 = new ArrayList();
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ENUM_ENTRY)) {
                    arrayList2.add(convertEnumEntry(lighterASTNode, classWrapper));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CLASS)) {
                    arrayList2.add(convertClass(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.FUN)) {
                    FirAnnotationContainer firAnnotationContainerConvertFunctionDeclaration = convertFunctionDeclaration(lighterASTNode);
                    Intrinsics.checkNotNull(firAnnotationContainerConvertFunctionDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclaration");
                    arrayList2.add((FirDeclaration) firAnnotationContainerConvertFunctionDeclaration);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.PROPERTY)) {
                    arrayList2.add(convertPropertyDeclaration(lighterASTNode, classWrapper));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPEALIAS)) {
                    arrayList2.add(convertTypeAlias(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.OBJECT_DECLARATION)) {
                    arrayList2.add(convertClass(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CLASS_INITIALIZER)) {
                    arrayList2.add(convertAnonymousInitializer(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.SECONDARY_CONSTRUCTOR)) {
                    arrayList2.add(convertSecondaryConstructor(lighterASTNode, classWrapper));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    arrayList.add(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.DESTRUCTURING_DECLARATION)) {
                    arrayList2.add(buildErrorTopLevelDestructuringDeclaration(AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null)));
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2 = CollectionsKt.plus((Collection<? extends FirDanglingModifierList>) arrayList2, buildErrorTopLevelDeclarationForDanglingModifierList((LighterASTNode) it.next()));
        }
        return arrayList2;
    }

    static /* synthetic */ PrimaryConstructor convertPrimaryConstructor$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2, KtSourceElement ktSourceElement, ClassWrapper classWrapper, KtLightSourceElement ktLightSourceElement, boolean z, boolean z2, boolean z3, int i, Object obj) {
        return lightTreeRawFirDeclarationBuilder.convertPrimaryConstructor(lighterASTNode, lighterASTNode2, ktSourceElement, classWrapper, ktLightSourceElement, (i & 32) != 0 ? false : z, z2, (i & 128) != 0 ? false : z3);
    }

    private static final boolean convertPrimaryConstructor$isEnumRelated(ClassKind classKind) {
        return classKind == ClassKind.ENUM_CLASS || classKind == ClassKind.ENUM_ENTRY;
    }

    /* JADX WARN: Code duplicated, block: B:71:0x0186  */
    private final PrimaryConstructor convertPrimaryConstructor(LighterASTNode classNode, LighterASTNode primaryConstructor, KtSourceElement selfTypeSource, ClassWrapper classWrapper, KtLightSourceElement delegatedConstructorSource, boolean isEnumEntry, boolean containingClassIsExpectClass, boolean isImplicitlyActual) {
        Modifier modifierConvertModifierList$default;
        FirMultiDelegatedConstructorCall firMultiDelegatedConstructorCallMo6809build;
        List<FirExpression> listEmptyList;
        Visibility visibility;
        KtLightSourceElement ktLightSourceElementFakeElement$default;
        KtLightSourceElement ktLightSourceElement;
        boolean z = false;
        boolean z2 = primaryConstructor == null && !((classWrapper.isEnumEntry() || !classWrapper.getHasSecondaryConstructor()) && !classWrapper.isInterface() && (!containingClassIsExpectClass || convertPrimaryConstructor$isEnumRelated(classWrapper.getClassBuilder().getClassKind())));
        if (z2 && classWrapper.getDelegatedSuperCalls().isEmpty()) {
            return null;
        }
        FirConstructorSymbol firConstructorSymbol = new FirConstructorSymbol(callableIdForClassConstructor());
        Modifier modifier = new Modifier(0L, 1, null);
        ArrayList arrayList = new ArrayList();
        if (primaryConstructor != null) {
            KtToken[] ktTokenArr = new KtToken[0];
            LighterASTNode[] childrenAsArray = getChildrenAsArray(primaryConstructor);
            int length = childrenAsArray.length;
            modifierConvertModifierList$default = modifier;
            int i = 0;
            while (i < length) {
                LighterASTNode lighterASTNode = childrenAsArray[i];
                if (lighterASTNode == null) {
                    break;
                }
                IElementType tokenType = lighterASTNode.getTokenType();
                if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                    IElementType tokenType2 = lighterASTNode.getTokenType();
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                        modifierConvertModifierList$default = convertModifierList$default(this, lighterASTNode, z, 2, null);
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER_LIST)) {
                        CollectionsKt.addAll(arrayList, convertValueParameters$default(this, lighterASTNode, firConstructorSymbol, AbstractRawFirBuilder.ValueParameterDeclaration.PRIMARY_CONSTRUCTOR, null, 8, null));
                    }
                }
                i++;
                childrenAsArray = childrenAsArray;
                length = length;
                z = false;
            }
        } else {
            modifierConvertModifierList$default = modifier;
        }
        Visibility visibilityDefaultConstructorVisibility = classWrapper.defaultConstructorVisibility();
        if (containingClassIsExpectClass) {
            firMultiDelegatedConstructorCallMo6809build = null;
        } else if (classWrapper.getDelegatedSuperCalls().size() <= 1) {
            FirTypeRef delegatedSuperTypeRef = classWrapper.getDelegatedSuperTypeRef();
            DelegatedConstructorWrapper delegatedConstructorWrapper = (DelegatedConstructorWrapper) CollectionsKt.lastOrNull((List) classWrapper.getDelegatedSuperCalls());
            if (delegatedConstructorWrapper == null || (listEmptyList = delegatedConstructorWrapper.getArguments()) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            firMultiDelegatedConstructorCallMo6809build = convertPrimaryConstructor$lambda$87$createDelegatedConstructorCall(selfTypeSource, isEnumEntry, classWrapper, this, delegatedConstructorSource, delegatedSuperTypeRef, listEmptyList);
        } else {
            FirMultiDelegatedConstructorCallBuilder firMultiDelegatedConstructorCallBuilder = new FirMultiDelegatedConstructorCallBuilder();
            List<DelegatedConstructorWrapper> delegatedSuperCalls = classWrapper.getDelegatedSuperCalls();
            List<FirDelegatedConstructorCall> delegatedConstructorCalls = firMultiDelegatedConstructorCallBuilder.getDelegatedConstructorCalls();
            for (DelegatedConstructorWrapper delegatedConstructorWrapper2 : delegatedSuperCalls) {
                List<FirDelegatedConstructorCall> list = delegatedConstructorCalls;
                list.add(convertPrimaryConstructor$lambda$87$createDelegatedConstructorCall(selfTypeSource, isEnumEntry, classWrapper, this, delegatedConstructorWrapper2.getSource(), delegatedConstructorWrapper2.getDelegatedSuperTypeRef(), delegatedConstructorWrapper2.component2()));
                delegatedConstructorCalls = list;
            }
            firMultiDelegatedConstructorCallMo6809build = firMultiDelegatedConstructorCallBuilder.mo6809build();
        }
        if (primaryConstructor != null) {
            visibility = modifierConvertModifierList$default.getVisibility();
            if (Intrinsics.areEqual(visibility, Visibilities.Unknown.INSTANCE)) {
                visibility = null;
            }
        } else {
            visibility = null;
        }
        if (visibility != null) {
            visibilityDefaultConstructorVisibility = visibility;
        }
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibilityDefaultConstructorVisibility, Modality.FINAL);
        firDeclarationStatusImpl.setExpect(modifierConvertModifierList$default.hasExpect() || getContext().getContainerIsExpect());
        firDeclarationStatusImpl.setActual(modifierConvertModifierList$default.hasActual() || isImplicitlyActual);
        firDeclarationStatusImpl.setInner(classWrapper.isInner());
        firDeclarationStatusImpl.setFromSealedClass(classWrapper.isSealed() && visibility != Visibilities.Private.INSTANCE);
        firDeclarationStatusImpl.setFromEnumClass(classWrapper.isEnum());
        FirAbstractConstructorBuilder firErrorConstructorBuilder = z2 ? new FirErrorConstructorBuilder() : new FirPrimaryConstructorBuilder();
        if (primaryConstructor == null || (ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, primaryConstructor, null, 1, null)) == null) {
            ktLightSourceElementFakeElement$default = selfTypeSource != null ? KtSourceElementKt.fakeElement$default(selfTypeSource, KtFakeSourceElementKind.ImplicitConstructor.INSTANCE, 0, 0, 6, null) : null;
        } else {
            ktLightSourceElementFakeElement$default = ktLightSourceElement;
        }
        firErrorConstructorBuilder.setSource(ktLightSourceElementFakeElement$default);
        firErrorConstructorBuilder.setModuleData(getBaseModuleData());
        firErrorConstructorBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firErrorConstructorBuilder.setReturnTypeRef(classWrapper.getDelegatedSelfTypeRef());
        firErrorConstructorBuilder.setDispatchReceiverType(obtainDispatchReceiverForConstructor(classWrapper));
        firErrorConstructorBuilder.setStatus(firDeclarationStatusImpl);
        firErrorConstructorBuilder.setSymbol(firConstructorSymbol);
        CollectionsKt.addAll(firErrorConstructorBuilder.getAnnotations(), modifierConvertModifierList$default.getAnnotations());
        CollectionsKt.addAll(firErrorConstructorBuilder.getTypeParameters(), constructorTypeParametersFromConstructedClass(classWrapper.getClassBuilder().getTypeParameters()));
        List<FirValueParameter> valueParameters = firErrorConstructorBuilder.getValueParameters();
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((ValueParameter) it.next()).getFirValueParameter());
        }
        CollectionsKt.addAll(valueParameters, arrayList3);
        firErrorConstructorBuilder.setDelegatedConstructor(firMultiDelegatedConstructorCallMo6809build);
        firErrorConstructorBuilder.setBody(null);
        firErrorConstructorBuilder.getContextReceivers().addAll(convertContextReceivers(classNode));
        FirConstructor firConstructorMo6809build = firErrorConstructorBuilder.mo6809build();
        ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = currentDispatchReceiverType();
        Intrinsics.checkNotNull(coneClassLikeTypeCurrentDispatchReceiverType);
        ClassMembersKt.setContainingClassForStaticMemberAttr(firConstructorMo6809build, coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag());
        return new PrimaryConstructor(firConstructorMo6809build, arrayList);
    }

    private final FirDeclaration convertAnonymousInitializer(LighterASTNode anonymousInitializer) {
        KtToken[] ktTokenArr = new KtToken[0];
        FirBlock firBlockBuildEmptyExpressionBlock = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(anonymousInitializer)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.BLOCK)) {
                firBlockBuildEmptyExpressionBlock = convertBlock(lighterASTNode);
            }
        }
        FirAnonymousInitializerBuilder firAnonymousInitializerBuilder = new FirAnonymousInitializerBuilder();
        firAnonymousInitializerBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, anonymousInitializer, null, 1, null));
        firAnonymousInitializerBuilder.setModuleData(getBaseModuleData());
        firAnonymousInitializerBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        if (firBlockBuildEmptyExpressionBlock == null) {
            firBlockBuildEmptyExpressionBlock = FirEmptyExpressionBlockBuilderKt.buildEmptyExpressionBlock();
        }
        firAnonymousInitializerBuilder.setBody(firBlockBuildEmptyExpressionBlock);
        firAnonymousInitializerBuilder.setDispatchReceiverType((ConeClassLikeType) CollectionsKt.lastOrNull((List) getContext().getDispatchReceiverTypesStack()));
        return firAnonymousInitializerBuilder.mo6809build();
    }

    private final FirConstructor convertSecondaryConstructor(LighterASTNode secondaryConstructor, ClassWrapper classWrapper) {
        LighterASTNode lighterASTNode;
        FirDelegatedConstructorCall firDelegatedConstructorCall;
        int i;
        int i2;
        LighterASTNode[] lighterASTNodeArr;
        Modifier modifier = new Modifier(0L, 1, null);
        ArrayList arrayList = new ArrayList();
        FirConstructorSymbol firConstructorSymbol = new FirConstructorSymbol(callableIdForClassConstructor());
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(secondaryConstructor);
        int length = childrenAsArray.length;
        Modifier modifierConvertModifierList$default = modifier;
        LighterASTNode lighterASTNode2 = null;
        FirDelegatedConstructorCall firDelegatedConstructorCallConvertConstructorDelegationCall = null;
        int i3 = 0;
        while (i3 < length) {
            LighterASTNode lighterASTNode3 = childrenAsArray[i3];
            if (lighterASTNode3 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode3.getTokenType();
            if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                lighterASTNode = lighterASTNode2;
                firDelegatedConstructorCall = firDelegatedConstructorCallConvertConstructorDelegationCall;
                i = i3;
                i2 = length;
                lighterASTNodeArr = childrenAsArray;
            } else {
                IElementType tokenType2 = lighterASTNode3.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifierConvertModifierList$default = convertModifierList$default(this, lighterASTNode3, false, 2, null);
                    i = i3;
                    i2 = length;
                    lighterASTNodeArr = childrenAsArray;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER_LIST)) {
                    lighterASTNode = lighterASTNode2;
                    firDelegatedConstructorCall = firDelegatedConstructorCallConvertConstructorDelegationCall;
                    i = i3;
                    i2 = length;
                    lighterASTNodeArr = childrenAsArray;
                    CollectionsKt.addAll(arrayList, convertValueParameters$default(this, lighterASTNode3, firConstructorSymbol, AbstractRawFirBuilder.ValueParameterDeclaration.FUNCTION, null, 8, null));
                } else {
                    lighterASTNode = lighterASTNode2;
                    firDelegatedConstructorCall = firDelegatedConstructorCallConvertConstructorDelegationCall;
                    i = i3;
                    i2 = length;
                    lighterASTNodeArr = childrenAsArray;
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONSTRUCTOR_DELEGATION_CALL)) {
                        firDelegatedConstructorCallConvertConstructorDelegationCall = convertConstructorDelegationCall(lighterASTNode3, classWrapper);
                        lighterASTNode2 = lighterASTNode;
                    } else {
                        if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                            lighterASTNode2 = lighterASTNode3;
                        }
                        firDelegatedConstructorCallConvertConstructorDelegationCall = firDelegatedConstructorCall;
                    }
                }
                i3 = i + 1;
                length = i2;
                childrenAsArray = lighterASTNodeArr;
            }
            lighterASTNode2 = lighterASTNode;
            firDelegatedConstructorCallConvertConstructorDelegationCall = firDelegatedConstructorCall;
            i3 = i + 1;
            length = i2;
            childrenAsArray = lighterASTNodeArr;
        }
        LighterASTNode lighterASTNode4 = lighterASTNode2;
        FirDelegatedConstructorCall firDelegatedConstructorCall2 = firDelegatedConstructorCallConvertConstructorDelegationCall;
        FirTypeRef delegatedSelfTypeRef = classWrapper.getDelegatedSelfTypeRef();
        Visibility visibility = modifierConvertModifierList$default.getVisibility();
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, Modality.FINAL);
        firDeclarationStatusImpl.setExpect(modifierConvertModifierList$default.hasExpect() || getContext().getContainerIsExpect());
        firDeclarationStatusImpl.setActual(modifierConvertModifierList$default.hasActual());
        firDeclarationStatusImpl.setInner(classWrapper.isInner());
        firDeclarationStatusImpl.setFromSealedClass(classWrapper.isSealed() && visibility != Visibilities.Private.INSTANCE);
        firDeclarationStatusImpl.setFromEnumClass(classWrapper.isEnum());
        FirFunctionTarget firFunctionTarget = new FirFunctionTarget(null, false);
        FirConstructorBuilder firConstructorBuilder = new FirConstructorBuilder();
        firConstructorBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, secondaryConstructor, null, 1, null));
        firConstructorBuilder.setModuleData(getBaseModuleData());
        firConstructorBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firConstructorBuilder.setReturnTypeRef(delegatedSelfTypeRef);
        firConstructorBuilder.setDispatchReceiverType(obtainDispatchReceiverForConstructor(classWrapper));
        firConstructorBuilder.setStatus(firDeclarationStatusImpl);
        firConstructorBuilder.setSymbol(firConstructorSymbol);
        firConstructorBuilder.setDelegatedConstructor(firDelegatedConstructorCall2);
        getContext().getFirFunctionTargets().add(firFunctionTarget);
        CollectionsKt.addAll(firConstructorBuilder.getAnnotations(), modifierConvertModifierList$default.getAnnotations());
        CollectionsKt.addAll(firConstructorBuilder.getTypeParameters(), constructorTypeParametersFromConstructedClass(classWrapper.getClassBuilder().getTypeParameters()));
        List<FirValueParameter> valueParameters = firConstructorBuilder.getValueParameters();
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((ValueParameter) it.next()).getFirValueParameter());
        }
        CollectionsKt.addAll(valueParameters, arrayList3);
        Pair<FirBlock, FirContractDescription> pairConvertFunctionBody = convertFunctionBody(lighterASTNode4, null, true);
        FirBlock firBlockComponent1 = pairConvertFunctionBody.component1();
        FirContractDescription firContractDescriptionComponent2 = pairConvertFunctionBody.component2();
        firConstructorBuilder.setBody(firBlockComponent1);
        if (firContractDescriptionComponent2 != null) {
            firConstructorBuilder.setContractDescription(firContractDescriptionComponent2);
        }
        removeLast(getContext().getFirFunctionTargets());
        List<FirContextReceiver> contextReceivers = firConstructorBuilder.getContextReceivers();
        LighterASTNode parent = getParent(secondaryConstructor);
        Intrinsics.checkNotNull(parent);
        LighterASTNode parent2 = getParent(parent);
        Intrinsics.checkNotNull(parent2);
        contextReceivers.addAll(convertContextReceivers(parent2));
        FirConstructor firConstructorMo6809build = firConstructorBuilder.mo6809build();
        ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = currentDispatchReceiverType();
        Intrinsics.checkNotNull(coneClassLikeTypeCurrentDispatchReceiverType);
        ClassMembersKt.setContainingClassForStaticMemberAttr(firConstructorMo6809build, coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag());
        firFunctionTarget.bind(firConstructorMo6809build);
        return firConstructorMo6809build;
    }

    private final ConeClassLikeType obtainDispatchReceiverForConstructor(ClassWrapper classWrapper) {
        if (classWrapper.isInner()) {
            return dispatchReceiverForInnerClassConstructor();
        }
        return null;
    }

    private final FirDelegatedConstructorCall convertConstructorDelegationCall(LighterASTNode constructorDelegationCall, ClassWrapper classWrapper) {
        FirTypeRef delegatedSuperTypeRef;
        KtSourceElement firSourceElement$default;
        FirSuperReference firSuperReferenceBuild;
        KtLightSourceElement firSourceElement;
        LighterASTNode lighterASTNode;
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(constructorDelegationCall);
        int length = childrenAsArray.length;
        boolean z = false;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONSTRUCTOR_DELEGATION_REFERENCE)) {
                    if (Intrinsics.areEqual(getAsText(lighterASTNode), "this")) {
                        z = true;
                    }
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_ARGUMENT_LIST)) {
                    CollectionsKt.addAll(arrayList, this.expressionConverter.convertValueArguments(lighterASTNode));
                }
            }
        }
        boolean z2 = constructorDelegationCall.getTextLength() == 0;
        if (z) {
            delegatedSuperTypeRef = classWrapper.getDelegatedSelfTypeRef();
        } else {
            delegatedSuperTypeRef = classWrapper.getDelegatedSuperTypeRef();
        }
        FirDelegatedConstructorCallBuilder firDelegatedConstructorCallBuilder = new FirDelegatedConstructorCallBuilder();
        KtLightSourceElement ktLightSourceElementFakeElement$default = null;
        if (z2) {
            firSourceElement$default = KtSourceElementKt.fakeElement$default(AbstractRawFirBuilder.toFirSourceElement$default(this, constructorDelegationCall, null, 1, null), KtFakeSourceElementKind.ImplicitConstructor.INSTANCE, 0, 0, 6, null);
        } else {
            firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(this, constructorDelegationCall, null, 1, null);
        }
        firDelegatedConstructorCallBuilder.setSource(firSourceElement$default);
        firDelegatedConstructorCallBuilder.setConstructedTypeRef(UtilsKt.copyWithNewSourceKind(delegatedSuperTypeRef, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE));
        firDelegatedConstructorCallBuilder.setThis(z);
        KtFakeSourceElementKind ktFakeSourceElementKind = z2 ? KtFakeSourceElementKind.ImplicitConstructor.INSTANCE : KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE;
        IElementType CONSTRUCTOR_DELEGATION_REFERENCE = KtNodeTypes.CONSTRUCTOR_DELEGATION_REFERENCE;
        Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR_DELEGATION_REFERENCE, "CONSTRUCTOR_DELEGATION_REFERENCE");
        LighterASTNode childNodeByType = getChildNodeByType(constructorDelegationCall, CONSTRUCTOR_DELEGATION_REFERENCE);
        if (childNodeByType != null && (firSourceElement = toFirSourceElement(childNodeByType, ktFakeSourceElementKind)) != null) {
            ktLightSourceElementFakeElement$default = firSourceElement;
        } else {
            KtSourceElement source = firDelegatedConstructorCallBuilder.getSource();
            if (source != null) {
                ktLightSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(source, ktFakeSourceElementKind, 0, 0, 6, null);
            }
        }
        if (z) {
            FirExplicitThisReferenceBuilder firExplicitThisReferenceBuilder = new FirExplicitThisReferenceBuilder();
            firExplicitThisReferenceBuilder.setSource(ktLightSourceElementFakeElement$default);
            firSuperReferenceBuild = firExplicitThisReferenceBuilder.build();
        } else {
            FirExplicitSuperReferenceBuilder firExplicitSuperReferenceBuilder = new FirExplicitSuperReferenceBuilder();
            firExplicitSuperReferenceBuilder.setSource(ktLightSourceElementFakeElement$default);
            firExplicitSuperReferenceBuilder.setSuperTypeRef(firDelegatedConstructorCallBuilder.getConstructedTypeRef());
            firSuperReferenceBuild = firExplicitSuperReferenceBuilder.build();
        }
        firDelegatedConstructorCallBuilder.setCalleeReference(firSuperReferenceBuild);
        ConverterUtilKt.extractArgumentsFrom(firDelegatedConstructorCallBuilder, arrayList);
        return firDelegatedConstructorCallBuilder.mo6809build();
    }

    private final FirDeclaration convertTypeAlias(LighterASTNode typeAlias) throws Throwable {
        String str;
        boolean z;
        boolean z2;
        boolean z3;
        FirTypeRef firTypeRef;
        int i = 1;
        Modifier modifier = new Modifier(0L, 1, null);
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(typeAlias);
        int length = childrenAsArray.length;
        int i2 = 0;
        String asText = null;
        FirTypeRef firTypeRefConvertType = null;
        while (i2 < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i2];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifier = convertModifierList$default(this, lighterASTNode, false, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.IDENTIFIER)) {
                    asText = getAsText(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = convertType(lighterASTNode);
                }
            }
            i2++;
            i = 1;
        }
        Name nameNameAsSafeName$default = ConverterUtilKt.nameAsSafeName$default(asText, null, i, null);
        boolean z4 = modifier.hasExpect() || getContext().getContainerIsExpect();
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder = this;
        Context<LighterASTNode> context = lightTreeRawFirDeclarationBuilder.getContext();
        FqName fqNameChild = lightTreeRawFirDeclarationBuilder.getContext().getClassName().child(nameNameAsSafeName$default);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        context.setClassName(fqNameChild);
        boolean forcedLocalContext = lightTreeRawFirDeclarationBuilder.getContext().getForcedLocalContext();
        lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(lightTreeRawFirDeclarationBuilder.getContext().getForcedLocalContext());
        boolean containerIsExpect = lightTreeRawFirDeclarationBuilder.getContext().getContainerIsExpect();
        lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect || z4);
        int size = lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size();
        try {
            FirTypeAliasSymbol firTypeAliasSymbol = new FirTypeAliasSymbol(getContext().getCurrentClassId());
            ArrayList arrayList = new ArrayList();
            try {
                KtToken[] ktTokenArr2 = new KtToken[0];
                LighterASTNode[] childrenAsArray2 = getChildrenAsArray(typeAlias);
                int length2 = childrenAsArray2.length;
                int i3 = 0;
                while (i3 < length2) {
                    int i4 = length2;
                    try {
                        LighterASTNode lighterASTNode2 = childrenAsArray2[i3];
                        if (lighterASTNode2 == null) {
                            break;
                        }
                        LighterASTNode[] lighterASTNodeArr = childrenAsArray2;
                        IElementType tokenType3 = lighterASTNode2.getTokenType();
                        z3 = forcedLocalContext;
                        try {
                            if (!KtTokens.COMMENTS.contains(tokenType3) && !Intrinsics.areEqual(tokenType3, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType3, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr2, tokenType3) && !Intrinsics.areEqual(tokenType3, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode2.getTokenType(), KtNodeTypes.TYPE_PARAMETER_LIST)) {
                                CollectionsKt.addAll(arrayList, convertTypeParameters(lighterASTNode2, CollectionsKt.emptyList(), firTypeAliasSymbol));
                            }
                            i3++;
                            length2 = i4;
                            childrenAsArray2 = lighterASTNodeArr;
                            forcedLocalContext = z3;
                            ktTokenArr2 = ktTokenArr2;
                        } catch (Throwable th) {
                            th = th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        z3 = forcedLocalContext;
                    }
                    th = th;
                    str = "Wrong number of ";
                    z2 = containerIsExpect;
                    z = z3;
                    if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                        throw new IllegalArgumentException((str + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                    }
                    if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > size) {
                        lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                    }
                    Context<LighterASTNode> context2 = lightTreeRawFirDeclarationBuilder.getContext();
                    FqName fqNameParent = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                    Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                    context2.setClassName(fqNameParent);
                    lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(z);
                    lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(z2);
                    throw th;
                }
                z3 = forcedLocalContext;
                FirTypeAliasBuilder firTypeAliasBuilder = new FirTypeAliasBuilder();
                firTypeAliasBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, typeAlias, null, 1, null));
                firTypeAliasBuilder.setModuleData(getBaseModuleData());
                firTypeAliasBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firTypeAliasBuilder.setName(nameNameAsSafeName$default);
                FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(modifier.getVisibility(), Modality.FINAL);
                firDeclarationStatusImpl.setExpect(z4);
                firDeclarationStatusImpl.setActual(modifier.hasActual());
                firTypeAliasBuilder.setStatus(firDeclarationStatusImpl);
                firTypeAliasBuilder.setSymbol(firTypeAliasSymbol);
                if (firTypeRefConvertType == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("firType");
                    firTypeRef = null;
                } else {
                    firTypeRef = firTypeRefConvertType;
                }
                firTypeAliasBuilder.setExpandedTypeRef(firTypeRef);
                CollectionsKt.addAll(firTypeAliasBuilder.getAnnotations(), modifier.getAnnotations());
                CollectionsKt.addAll(firTypeAliasBuilder.getTypeParameters(), arrayList);
                FirTypeAlias firTypeAliasMo6809build = firTypeAliasBuilder.mo6809build();
                if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                    throw new IllegalArgumentException(("Wrong number of " + lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                }
                if (lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().size() > size) {
                    lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(lightTreeRawFirDeclarationBuilder.getContext().getDispatchReceiverTypesStack()));
                }
                Context<LighterASTNode> context3 = lightTreeRawFirDeclarationBuilder.getContext();
                FqName fqNameParent2 = lightTreeRawFirDeclarationBuilder.getContext().getClassName().parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
                context3.setClassName(fqNameParent2);
                lightTreeRawFirDeclarationBuilder.getContext().setForcedLocalContext(z3);
                lightTreeRawFirDeclarationBuilder.getContext().setContainerIsExpect(containerIsExpect);
                return firTypeAliasMo6809build;
            } catch (Throwable th3) {
                th = th3;
                z = forcedLocalContext;
                z2 = containerIsExpect;
                str = "Wrong number of ";
            }
        } catch (Throwable th4) {
            th = th4;
            str = "Wrong number of ";
            z = forcedLocalContext;
            z2 = containerIsExpect;
        }
    }

    public static /* synthetic */ FirDeclaration convertPropertyDeclaration$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, LighterASTNode lighterASTNode, ClassWrapper classWrapper, int i, Object obj) {
        if ((i & 2) != 0) {
            classWrapper = null;
        }
        return lightTreeRawFirDeclarationBuilder.convertPropertyDeclaration(lighterASTNode, classWrapper);
    }

    /* JADX WARN: Code duplicated, block: B:80:0x01df  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, org.jetbrains.kotlin.fir.lightTree.fir.modifier.Modifier] */
    /* JADX WARN: Type inference failed for: r2v118, types: [T, org.jetbrains.kotlin.fir.lightTree.fir.modifier.Modifier] */
    public final FirDeclaration convertPropertyDeclaration(LighterASTNode property, ClassWrapper classWrapper) {
        boolean z;
        LighterASTNode lighterASTNode;
        KtLightSourceElement ktLightSourceElement;
        KtLightSourceElement ktLightSourceElement2;
        FirPropertyBuilder firPropertyBuilder;
        List<? extends FirAnnotationCall> listEmptyList;
        boolean z2;
        FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder;
        Object next;
        LighterASTNode lighterASTNode2;
        Object next2;
        FirClassBuilder classBuilder;
        FirDefaultPropertySetter firDefaultPropertySetter;
        KtLightSourceElement ktLightSourceElement3;
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder2;
        FirClassBuilder classBuilder2;
        ConeSimpleDiagnostic coneSimpleDiagnostic2;
        LighterASTNode lighterASTNode3;
        KtFakeSourceElementKind ktFakeSourceElementKind;
        int i;
        LighterASTNode lighterASTNode4;
        Intrinsics.checkNotNullParameter(property, "property");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        String str = null;
        objectRef.element = new Modifier(0L, 1, null);
        ArrayList arrayList = new ArrayList();
        FirImplicitTypeRefImplWithoutSource implicitType = getImplicitType();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(property);
        int length = childrenAsArray.length;
        FirTypeRef firTypeRefConvertType = implicitType;
        int i2 = 0;
        boolean z3 = false;
        boolean z4 = false;
        LighterASTNode lighterASTNode5 = null;
        String asText = null;
        LighterASTNode lighterASTNode6 = null;
        FirTypeRef firTypeRefConvertType2 = null;
        Object obj = null;
        LighterASTNode lighterASTNode7 = null;
        while (i2 < length && (lighterASTNode4 = childrenAsArray[i2]) != null) {
            IElementType tokenType = lighterASTNode4.getTokenType();
            LighterASTNode[] lighterASTNodeArr = childrenAsArray;
            if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                arrayList3 = arrayList3;
            } else {
                IElementType tokenType2 = lighterASTNode4.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    objectRef.element = convertModifierList$default(this, lighterASTNode4, false, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.IDENTIFIER)) {
                    asText = getAsText(lighterASTNode4);
                } else {
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_PARAMETER_LIST)) {
                        lighterASTNode7 = lighterASTNode4;
                    } else if (Intrinsics.areEqual(tokenType2, KtTokens.COLON)) {
                        ktTokenArr = ktTokenArr;
                        length = length;
                        arrayList3 = arrayList3;
                        z4 = true;
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                        if (z4) {
                            firTypeRefConvertType = convertType(lighterASTNode4);
                        } else {
                            firTypeRefConvertType2 = convertType(lighterASTNode4);
                        }
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_CONSTRAINT_LIST)) {
                        CollectionsKt.addAll(arrayList2, convertTypeConstraints(lighterASTNode4));
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.PROPERTY_DELEGATE)) {
                        lighterASTNode6 = lighterASTNode4;
                    } else if (Intrinsics.areEqual(tokenType2, KtTokens.VAR_KEYWORD)) {
                        ktTokenArr = ktTokenArr;
                        length = length;
                        arrayList3 = arrayList3;
                        z3 = true;
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.PROPERTY_ACCESSOR)) {
                        arrayList3.add(lighterASTNode4);
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BACKING_FIELD)) {
                        lighterASTNode5 = lighterASTNode4;
                    } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode4)) {
                        getContext().getCalleeNamesForLambda().add(null);
                        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this.expressionConverter;
                        ktTokenArr = ktTokenArr;
                        FirElement firElementConvertExpression = lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode4, "Should have initializer");
                        length = length;
                        FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                        if (firErrorExpression == null) {
                            FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode4, null, 1, null), new ConeSimpleDiagnostic("Should have initializer", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                            if (firErrorExpressionBuildErrorExpression == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                            }
                            firErrorExpression = firErrorExpressionBuildErrorExpression;
                        }
                        removeLast(getContext().getCalleeNamesForLambda());
                        obj = firErrorExpression;
                    }
                    arrayList3 = arrayList3;
                }
                arrayList3 = arrayList3;
            }
            i2++;
            childrenAsArray = lighterASTNodeArr;
            ktTokenArr = ktTokenArr;
            length = length;
            arrayList3 = arrayList3;
            str = null;
        }
        ArrayList arrayList4 = arrayList3;
        String str2 = str;
        Name nameNameAsSafeName$default = ConverterUtilKt.nameAsSafeName$default(asText, str2, 1, str2);
        LighterASTNode parent = getParent(property);
        if (Intrinsics.areEqual(parent != null ? parent.getTokenType() : null, KtNodeTypes.KT_FILE)) {
            z = false;
        } else if (Intrinsics.areEqual(parent != null ? parent.getTokenType() : null, KtNodeTypes.CLASS_BODY)) {
            z = false;
        } else {
            z = true;
        }
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder = this;
        KtLightSourceElement ktLightSourceElement4 = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirDeclarationBuilder, property, null, 1, null);
        FirPropertyBuilder firPropertyBuilder2 = new FirPropertyBuilder();
        KtLightSourceElement ktLightSourceElement5 = ktLightSourceElement4;
        firPropertyBuilder2.setSource(ktLightSourceElement5);
        firPropertyBuilder2.setModuleData(getBaseModuleData());
        firPropertyBuilder2.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firPropertyBuilder2.setReturnTypeRef(firTypeRefConvertType);
        firPropertyBuilder2.setName(nameNameAsSafeName$default);
        boolean z5 = z3;
        firPropertyBuilder2.setVar(z5);
        firPropertyBuilder2.setReceiverParameter(firTypeRefConvertType2 != null ? ConversionUtilsKt.convertToReceiverParameter(firTypeRefConvertType2) : null);
        firPropertyBuilder2.setInitializer((FirExpression) obj);
        LighterASTNode lighterASTNode8 = lighterASTNode6;
        if (lighterASTNode8 != null) {
            LighterASTNode expressionInParentheses = getExpressionInParentheses(lighterASTNode8);
            if (expressionInParentheses == null) {
                lighterASTNode3 = lighterASTNode8;
                lighterASTNode = lighterASTNode3;
                i = 1;
                ktFakeSourceElementKind = null;
            } else {
                lighterASTNode3 = expressionInParentheses;
                ktFakeSourceElementKind = null;
                lighterASTNode = lighterASTNode8;
                i = 1;
            }
            ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirDeclarationBuilder, lighterASTNode3, ktFakeSourceElementKind, i, ktFakeSourceElementKind);
        } else {
            lighterASTNode = lighterASTNode8;
            z5 = z5;
            ktLightSourceElement = null;
        }
        firPropertyBuilder2.setSymbol(z ? new FirPropertySymbol(nameNameAsSafeName$default) : new FirPropertySymbol(callableIdForName(nameNameAsSafeName$default)));
        LighterASTNode lighterASTNode9 = lighterASTNode7;
        if (lighterASTNode9 != null) {
            CollectionsKt.addAll(arrayList, convertTypeParameters(lighterASTNode9, arrayList2, firPropertyBuilder2.getSymbol()));
            Unit unit = Unit.INSTANCE;
            Unit unit2 = Unit.INSTANCE;
        }
        FirPropertySymbol symbol = firPropertyBuilder2.getSymbol();
        Modifier modifier = (Modifier) objectRef.element;
        if (z) {
            ktLightSourceElement2 = ktLightSourceElement5;
            firPropertyBuilder = firPropertyBuilder2;
            listEmptyList = CollectionsKt.emptyList();
        } else {
            List<FirAnnotationCall> annotations = ((Modifier) objectRef.element).getAnnotations();
            ArrayList arrayList5 = new ArrayList();
            Iterator it = annotations.iterator();
            while (it.hasNext()) {
                KtLightSourceElement ktLightSourceElement6 = ktLightSourceElement5;
                Object next3 = it.next();
                FirAnnotationCall firAnnotationCall = (FirAnnotationCall) next3;
                Iterator it2 = it;
                FirPropertyBuilder firPropertyBuilder3 = firPropertyBuilder2;
                if (firAnnotationCall.getUseSiteTarget() == AnnotationUseSiteTarget.FIELD || firAnnotationCall.getUseSiteTarget() == AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD) {
                    arrayList5.add(next3);
                }
                ktLightSourceElement5 = ktLightSourceElement6;
                it = it2;
                firPropertyBuilder2 = firPropertyBuilder3;
            }
            ktLightSourceElement2 = ktLightSourceElement5;
            firPropertyBuilder = firPropertyBuilder2;
            listEmptyList = arrayList5;
        }
        LighterASTNode lighterASTNode10 = lighterASTNode;
        FirTypeRef firTypeRef = firTypeRefConvertType;
        FirPropertyBuilder firPropertyBuilder4 = firPropertyBuilder;
        firPropertyBuilder4.setBackingField(convertBackingField(lighterASTNode5, symbol, modifier, firTypeRefConvertType, z5, listEmptyList, property));
        if (z) {
            firPropertyBuilder4.setLocal(true);
            if (lighterASTNode10 != null) {
                FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder3 = new FirWrappedDelegateExpressionBuilder();
                firWrappedDelegateExpressionBuilder3.setSource(ktLightSourceElement);
                LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder2 = this.expressionConverter;
                FirElement firElementConvertExpression2 = lighterASTNode10 != null ? lightTreeRawFirExpressionBuilder2.convertExpression(lighterASTNode10, "Incorrect delegate expression") : null;
                FirAnnotationContainer firAnnotationContainer = (FirExpression) (!(firElementConvertExpression2 instanceof FirExpression) ? null : firElementConvertExpression2);
                if (firAnnotationContainer == null) {
                    KtLightSourceElement ktLightSourceElement7 = lighterASTNode10 != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder2, lighterASTNode10, null, 1, null) : null;
                    if (lighterASTNode10 == null) {
                        coneSimpleDiagnostic2 = new ConeSyntaxDiagnostic("Incorrect delegate expression");
                    } else {
                        coneSimpleDiagnostic2 = new ConeSimpleDiagnostic("Incorrect delegate expression", DiagnosticKind.ExpressionExpected);
                    }
                    FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement7, coneSimpleDiagnostic2, firElementConvertExpression2);
                    if (firAnnotationContainerBuildErrorExpression == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                    }
                    firAnnotationContainer = (FirExpression) firAnnotationContainerBuildErrorExpression;
                }
                firWrappedDelegateExpressionBuilder3.setExpression((FirExpression) firAnnotationContainer);
                firWrappedDelegateExpressionBuilder2 = firWrappedDelegateExpressionBuilder3;
            } else {
                firWrappedDelegateExpressionBuilder2 = null;
            }
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL);
            firDeclarationStatusImpl.setLateInit(((Modifier) objectRef.element).hasLateinit());
            firPropertyBuilder4.setStatus(firDeclarationStatusImpl);
            CollectionsKt.addAll(firPropertyBuilder4.getTypeParameters(), arrayList);
            ConversionUtilsKt.generateAccessorsByDelegate$default(firPropertyBuilder4, firWrappedDelegateExpressionBuilder2, getBaseModuleData(), (classWrapper == null || (classBuilder2 = classWrapper.getClassBuilder()) == null) ? null : ConversionUtilsKt.getOwnerRegularOrAnonymousObjectSymbol(classBuilder2), getContext(), false, null, 32, null);
        } else {
            firPropertyBuilder4.setLocal(false);
            firPropertyBuilder4.setDispatchReceiverType(currentDispatchReceiverType());
            lightTreeRawFirDeclarationBuilder.addCapturedTypeParameters(true, ktLightSourceElement2, arrayList);
            try {
                CollectionsKt.addAll(firPropertyBuilder4.getTypeParameters(), arrayList);
                if (lighterASTNode10 != null) {
                    FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder4 = new FirWrappedDelegateExpressionBuilder();
                    firWrappedDelegateExpressionBuilder4.setSource(ktLightSourceElement);
                    LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder3 = this.expressionConverter;
                    FirElement firElementConvertExpression3 = lighterASTNode10 != null ? lightTreeRawFirExpressionBuilder3.convertExpression(lighterASTNode10, "Should have delegate") : null;
                    FirAnnotationContainer firAnnotationContainer2 = (FirExpression) (!(firElementConvertExpression3 instanceof FirExpression) ? null : firElementConvertExpression3);
                    if (firAnnotationContainer2 == null) {
                        if (lighterASTNode10 != null) {
                            z2 = true;
                            ktLightSourceElement3 = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder3, lighterASTNode10, null, 1, null);
                        } else {
                            z2 = true;
                            ktLightSourceElement3 = null;
                        }
                        KtLightSourceElement ktLightSourceElement8 = ktLightSourceElement3;
                        if (lighterASTNode10 == null) {
                            coneSimpleDiagnostic = new ConeSyntaxDiagnostic("Should have delegate");
                        } else {
                            coneSimpleDiagnostic = new ConeSimpleDiagnostic("Should have delegate", DiagnosticKind.ExpressionExpected);
                        }
                        FirAnnotationContainer firAnnotationContainerBuildErrorExpression2 = FirExpressionUtilKt.buildErrorExpression(ktLightSourceElement8, coneSimpleDiagnostic, firElementConvertExpression3);
                        if (firAnnotationContainerBuildErrorExpression2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firAnnotationContainer2 = (FirExpression) firAnnotationContainerBuildErrorExpression2;
                    } else {
                        z2 = true;
                    }
                    firWrappedDelegateExpressionBuilder4.setExpression((FirExpression) firAnnotationContainer2);
                    firWrappedDelegateExpressionBuilder = firWrappedDelegateExpressionBuilder4;
                } else {
                    z2 = true;
                    firWrappedDelegateExpressionBuilder = null;
                }
                Visibility visibility = ((Modifier) objectRef.element).getVisibility();
                ArrayList arrayList6 = arrayList4;
                ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList6, 10));
                Iterator it3 = arrayList6.iterator();
                while (it3.hasNext()) {
                    FirTypeRef firTypeRef2 = firTypeRef;
                    arrayList7.add(convertGetterOrSetter((LighterASTNode) it3.next(), firTypeRef2, visibility, firPropertyBuilder4.getSymbol(), (Modifier) objectRef.element));
                    firTypeRef = firTypeRef2;
                }
                FirTypeRef firTypeRef3 = firTypeRef;
                ArrayList arrayList8 = arrayList7;
                Iterator it4 = arrayList8.iterator();
                do {
                    if (!it4.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it4.next();
                } while (!((FirPropertyAccessor) next).getIsGetter());
                FirDefaultPropertyGetter firDefaultPropertyGetter = (FirPropertyAccessor) next;
                if (firDefaultPropertyGetter == null) {
                    lighterASTNode2 = property;
                    FirDefaultPropertyGetter firDefaultPropertyGetter2 = new FirDefaultPropertyGetter(toFirSourceElement(lighterASTNode2, (KtFakeSourceElementKind) KtFakeSourceElementKind.DefaultAccessor.INSTANCE), firPropertyBuilder4.getModuleData(), FirDeclarationOrigin.Source.INSTANCE, UtilsKt.copyWithNewSourceKind(firTypeRef3, KtFakeSourceElementKind.DefaultAccessor.INSTANCE), visibility, firPropertyBuilder4.getSymbol(), null, null, null, null, XKeySym.XK_Amacron, null);
                    firDefaultPropertyGetter2.setStatus(convertPropertyDeclaration$lambda$129$lambda$127$defaultAccessorStatus(visibility, objectRef));
                    firDefaultPropertyGetter2.replaceAnnotations(ConversionUtilsKt.filterUseSiteTarget(((Modifier) objectRef.element).getAnnotations(), AnnotationUseSiteTarget.PROPERTY_GETTER));
                    initContainingClassAttr(firDefaultPropertyGetter2);
                    firDefaultPropertyGetter = firDefaultPropertyGetter2;
                } else {
                    lighterASTNode2 = property;
                }
                firPropertyBuilder4.setGetter(firDefaultPropertyGetter);
                Iterator it5 = arrayList8.iterator();
                do {
                    if (!it5.hasNext()) {
                        next2 = null;
                        break;
                    }
                    next2 = it5.next();
                } while (!((FirPropertyAccessor) next2).isSetter());
                FirDefaultPropertySetter firDefaultPropertySetter2 = (FirPropertyAccessor) next2;
                if (firDefaultPropertySetter2 == null) {
                    if (z5) {
                        firDefaultPropertySetter = new FirDefaultPropertySetter(toFirSourceElement(lighterASTNode2, (KtFakeSourceElementKind) KtFakeSourceElementKind.DefaultAccessor.INSTANCE), firPropertyBuilder4.getModuleData(), FirDeclarationOrigin.Source.INSTANCE, UtilsKt.copyWithNewSourceKind(firTypeRef3, KtFakeSourceElementKind.DefaultAccessor.INSTANCE), visibility, firPropertyBuilder4.getSymbol(), null, null, null, ConversionUtilsKt.filterUseSiteTarget(((Modifier) objectRef.element).getAnnotations(), AnnotationUseSiteTarget.SETTER_PARAMETER), null, 1472, null);
                        firDefaultPropertySetter.setStatus(convertPropertyDeclaration$lambda$129$lambda$127$defaultAccessorStatus(visibility, objectRef));
                        firDefaultPropertySetter.replaceAnnotations(ConversionUtilsKt.filterUseSiteTarget(((Modifier) objectRef.element).getAnnotations(), AnnotationUseSiteTarget.PROPERTY_SETTER));
                        initContainingClassAttr(firDefaultPropertySetter);
                    } else {
                        firDefaultPropertySetter = null;
                    }
                    firDefaultPropertySetter2 = firDefaultPropertySetter;
                }
                firPropertyBuilder4.setSetter(firDefaultPropertySetter2);
                FirDeclarationStatusImpl firDeclarationStatusImpl2 = new FirDeclarationStatusImpl(visibility, ((Modifier) objectRef.element).getModality(false));
                firDeclarationStatusImpl2.setExpect((((Modifier) objectRef.element).hasExpect() || getContext().getContainerIsExpect()) ? z2 : false);
                firDeclarationStatusImpl2.setActual(((Modifier) objectRef.element).hasActual());
                firDeclarationStatusImpl2.setOverride(((Modifier) objectRef.element).hasOverride());
                firDeclarationStatusImpl2.setConst(((Modifier) objectRef.element).isConst());
                firDeclarationStatusImpl2.setLateInit(((Modifier) objectRef.element).hasLateinit());
                firDeclarationStatusImpl2.setExternal(((Modifier) objectRef.element).hasExternal());
                firPropertyBuilder4.setStatus(firDeclarationStatusImpl2);
                ConversionUtilsKt.generateAccessorsByDelegate$default(firPropertyBuilder4, firWrappedDelegateExpressionBuilder, getBaseModuleData(), (classWrapper == null || (classBuilder = classWrapper.getClassBuilder()) == null) ? null : ConversionUtilsKt.getOwnerRegularOrAnonymousObjectSymbol(classBuilder), getContext(), firTypeRefConvertType2 != null ? z2 : false, null, 32, null);
                Unit unit3 = Unit.INSTANCE;
                lightTreeRawFirDeclarationBuilder.getContext().popFirTypeParameters();
            } catch (Throwable th) {
                lightTreeRawFirDeclarationBuilder.getContext().popFirTypeParameters();
                throw th;
            }
        }
        List<FirAnnotation> annotations2 = firPropertyBuilder4.getAnnotations();
        ArrayList annotations3 = ((Modifier) objectRef.element).getAnnotations();
        if (!z) {
            ArrayList arrayList9 = new ArrayList();
            for (Object obj2 : annotations3) {
                FirAnnotationCall firAnnotationCall2 = (FirAnnotationCall) obj2;
                if (firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.FIELD && firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD && firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.PROPERTY_GETTER && (!z5 || (firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.SETTER_PARAMETER && firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.PROPERTY_SETTER))) {
                    arrayList9.add(obj2);
                }
            }
            annotations3 = arrayList9;
        }
        CollectionsKt.addAll(annotations2, annotations3);
        firPropertyBuilder4.getContextReceivers().addAll(convertContextReceivers(property));
        FirProperty firPropertyMo6809build = firPropertyBuilder4.mo6809build();
        fillDanglingConstraintsTo(arrayList, arrayList2, firPropertyMo6809build);
        return firPropertyMo6809build;
    }

    private static final FirDeclarationStatusImpl convertPropertyDeclaration$lambda$129$lambda$127$defaultAccessorStatus(Visibility visibility, Ref.ObjectRef<Modifier> objectRef) {
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, null);
        firDeclarationStatusImpl.setInline(objectRef.element.hasInline());
        firDeclarationStatusImpl.setExternal(objectRef.element.hasExternal());
        return firDeclarationStatusImpl;
    }

    public final DestructuringDeclaration convertDestructingDeclaration$light_tree2fir(LighterASTNode destructingDeclaration) {
        LighterASTNode lighterASTNode;
        Intrinsics.checkNotNullParameter(destructingDeclaration, "destructingDeclaration");
        Modifier modifier = new Modifier(0L, 1, null);
        ArrayList arrayList = new ArrayList();
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, destructingDeclaration, null, 1, null);
        boolean z = false;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(destructingDeclaration);
        int length = childrenAsArray.length;
        Modifier modifierConvertModifierList$default = modifier;
        FirExpression firExpression = null;
        boolean z2 = false;
        int i = 0;
        while (i < length && (lighterASTNode = childrenAsArray[i]) != null) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifierConvertModifierList$default = convertModifierList$default(this, lighterASTNode, z, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.VAR_KEYWORD)) {
                    z2 = true;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.DESTRUCTURING_DECLARATION_ENTRY)) {
                    arrayList.add(convertDestructingDeclarationEntry(lighterASTNode));
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this.expressionConverter;
                    FirElement firElementConvertExpression = lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode, "Initializer required for destructuring declaration");
                    FirExpression firExpression2 = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firExpression2 == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Initializer required for destructuring declaration", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firExpression2 = firErrorExpressionBuildErrorExpression;
                    }
                    firExpression = firExpression2;
                }
            }
            i++;
            z = false;
        }
        FirExpression firExpression3 = firExpression;
        return new DestructuringDeclaration(z2, arrayList, firExpression3 == null ? FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("Initializer required for destructuring declaration"), null, 4, null) : firExpression3, ktLightSourceElement, modifierConvertModifierList$default);
    }

    private final FirVariable convertDestructingDeclarationEntry(LighterASTNode entry) {
        Modifier modifier = new Modifier(0L, 1, null);
        KtToken[] ktTokenArr = new KtToken[0];
        String asText = null;
        FirImplicitTypeRefImplWithoutSource implicitType = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(entry)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifier = convertModifierList$default(this, lighterASTNode, false, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.IDENTIFIER)) {
                    asText = getAsText(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    implicitType = convertType(lighterASTNode);
                }
            }
        }
        if (Intrinsics.areEqual(asText, "_")) {
            return null;
        }
        Name nameNameAsSafeName$default = ConverterUtilKt.nameAsSafeName$default(asText, null, 1, null);
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, entry, null, 1, null));
        firPropertyBuilder.setModuleData(getBaseModuleData());
        firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        if (implicitType == null) {
            implicitType = getImplicitType();
        }
        firPropertyBuilder.setReturnTypeRef(implicitType);
        firPropertyBuilder.setName(nameNameAsSafeName$default);
        firPropertyBuilder.setVar(false);
        firPropertyBuilder.setSymbol(new FirPropertySymbol(nameNameAsSafeName$default));
        firPropertyBuilder.setLocal(true);
        firPropertyBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
        CollectionsKt.addAll(firPropertyBuilder.getAnnotations(), modifier.getAnnotations());
        return firPropertyBuilder.mo6809build();
    }

    private final FirPropertyAccessor convertGetterOrSetter(LighterASTNode getterOrSetter, FirTypeRef propertyTypeRef, Visibility propertyVisibility, FirPropertySymbol propertySymbol, Modifier propertyModifiers) {
        AnnotationUseSiteTarget annotationUseSiteTarget;
        Modifier modifier = new Modifier(0L, 1, null);
        FirImplicitBuiltinTypeRef firImplicitBuiltinTypeRefCopyWithNewSourceKind = UtilsKt.copyWithNewSourceKind(propertyTypeRef, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE);
        FirPropertyAccessorSymbol firPropertyAccessorSymbol = new FirPropertyAccessorSymbol();
        FirDefaultSetterValueParameterBuilder firDefaultSetterValueParameterBuilder = new FirDefaultSetterValueParameterBuilder();
        firDefaultSetterValueParameterBuilder.setModuleData(getBaseModuleData());
        FirPropertyAccessorSymbol firPropertyAccessorSymbol2 = firPropertyAccessorSymbol;
        firDefaultSetterValueParameterBuilder.setContainingFunctionSymbol(firPropertyAccessorSymbol2);
        firDefaultSetterValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firDefaultSetterValueParameterBuilder.setReturnTypeRef(firImplicitBuiltinTypeRefCopyWithNewSourceKind);
        firDefaultSetterValueParameterBuilder.setSymbol(new FirValueParameterSymbol(StandardNames.DEFAULT_VALUE_PARAMETER));
        FirValueParameter firValueParameterMo6809build = firDefaultSetterValueParameterBuilder.mo6809build();
        KtToken[] ktTokenArr = new KtToken[0];
        boolean z = true;
        LighterASTNode lighterASTNode = null;
        FirTypeRef firTypeRefConvertType = null;
        FirContractDescription second = null;
        LighterASTNode lighterASTNode2 = null;
        for (LighterASTNode lighterASTNode3 : getChildrenAsArray(getterOrSetter)) {
            if (lighterASTNode3 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode3.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                if (Intrinsics.areEqual(getAsText(lighterASTNode3), FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) {
                    z = false;
                }
                IElementType tokenType2 = lighterASTNode3.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtTokens.SET_KEYWORD)) {
                    z = false;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifier = convertModifierList$default(this, lighterASTNode3, false, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = convertType(lighterASTNode3);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER_LIST)) {
                    firValueParameterMo6809build = convertSetterParameter(lighterASTNode3, firPropertyAccessorSymbol2, firImplicitBuiltinTypeRefCopyWithNewSourceKind, ConversionUtilsKt.filterUseSiteTarget(propertyModifiers.getAnnotations(), AnnotationUseSiteTarget.SETTER_PARAMETER));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONTRACT_EFFECT_LIST)) {
                    second = obtainContractDescription(lighterASTNode3);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                    lighterASTNode = lighterASTNode3;
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode3)) {
                    lighterASTNode2 = lighterASTNode3;
                }
            }
        }
        Visibility visibility = modifier.getVisibility();
        Visibility visibility2 = Intrinsics.areEqual(visibility, Visibilities.Unknown.INSTANCE) ? propertyVisibility : visibility;
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility2, modifier.getModality(false));
        firDeclarationStatusImpl.setInline(propertyModifiers.hasInline() || modifier.hasInline());
        firDeclarationStatusImpl.setExternal(propertyModifiers.hasExternal() || modifier.hasExternal());
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, getterOrSetter, null, 1, null);
        List<FirAnnotationCall> annotations = propertyModifiers.getAnnotations();
        if (z) {
            annotationUseSiteTarget = AnnotationUseSiteTarget.PROPERTY_GETTER;
        } else {
            annotationUseSiteTarget = AnnotationUseSiteTarget.PROPERTY_SETTER;
        }
        List<FirAnnotationCall> listFilterUseSiteTarget = ConversionUtilsKt.filterUseSiteTarget(annotations, annotationUseSiteTarget);
        LighterASTNode lighterASTNode4 = lighterASTNode2;
        if (lighterASTNode == null && lighterASTNode4 == null) {
            FirDefaultPropertyAccessor firDefaultPropertyAccessorCreateGetterOrSetter = FirDefaultPropertyAccessor.INSTANCE.createGetterOrSetter(ktLightSourceElement, getBaseModuleData(), FirDeclarationOrigin.Source.INSTANCE, firImplicitBuiltinTypeRefCopyWithNewSourceKind, visibility2, propertySymbol, z, (128 & 128) != 0 ? CollectionsKt.emptyList() : null);
            firDefaultPropertyAccessorCreateGetterOrSetter.replaceAnnotations(CollectionsKt.plus((Collection) modifier.getAnnotations(), (Iterable) listFilterUseSiteTarget));
            firDefaultPropertyAccessorCreateGetterOrSetter.setStatus(firDeclarationStatusImpl);
            initContainingClassAttr(firDefaultPropertyAccessorCreateGetterOrSetter);
            return firDefaultPropertyAccessorCreateGetterOrSetter;
        }
        FirFunctionTarget firFunctionTarget = new FirFunctionTarget(null, false);
        FirPropertyAccessorBuilder firPropertyAccessorBuilder = new FirPropertyAccessorBuilder();
        firPropertyAccessorBuilder.setSource(ktLightSourceElement);
        firPropertyAccessorBuilder.setModuleData(getBaseModuleData());
        firPropertyAccessorBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        if (firTypeRefConvertType != null) {
            firImplicitBuiltinTypeRefCopyWithNewSourceKind = firTypeRefConvertType;
        } else if (!z) {
            firImplicitBuiltinTypeRefCopyWithNewSourceKind = getImplicitUnitType();
        }
        firPropertyAccessorBuilder.setReturnTypeRef(firImplicitBuiltinTypeRefCopyWithNewSourceKind);
        firPropertyAccessorBuilder.setSymbol(firPropertyAccessorSymbol);
        firPropertyAccessorBuilder.setGetter(z);
        firPropertyAccessorBuilder.setStatus(firDeclarationStatusImpl);
        getContext().getFirFunctionTargets().add(firFunctionTarget);
        CollectionsKt.addAll(firPropertyAccessorBuilder.getAnnotations(), listFilterUseSiteTarget);
        CollectionsKt.addAll(firPropertyAccessorBuilder.getAnnotations(), modifier.getAnnotations());
        if (!z) {
            firPropertyAccessorBuilder.getValueParameters().add(firValueParameterMo6809build);
        }
        Pair<FirBlock, FirContractDescription> pairConvertFunctionBody = convertFunctionBody(lighterASTNode, lighterASTNode4, second == null);
        firPropertyAccessorBuilder.setBody(pairConvertFunctionBody.getFirst());
        if (second == null) {
            second = pairConvertFunctionBody.getSecond();
        }
        FirContractDescription firContractDescription = second;
        if (firContractDescription != null) {
            firPropertyAccessorBuilder.setContractDescription(firContractDescription);
        }
        removeLast(getContext().getFirFunctionTargets());
        firPropertyAccessorBuilder.setPropertySymbol(propertySymbol);
        FirPropertyAccessor firPropertyAccessorMo6809build = firPropertyAccessorBuilder.mo6809build();
        firFunctionTarget.bind(firPropertyAccessorMo6809build);
        initContainingClassAttr(firPropertyAccessorMo6809build);
        return firPropertyAccessorMo6809build;
    }

    private final FirBackingField convertBackingField(LighterASTNode lighterASTNode, FirPropertySymbol firPropertySymbol, Modifier modifier, FirTypeRef firTypeRef, boolean z, List<? extends FirAnnotationCall> list, LighterASTNode lighterASTNode2) {
        Object obj;
        Modifier modifier2 = new Modifier(0L, 1, null);
        FirTypeRef implicitType = getImplicitType();
        if (lighterASTNode != null) {
            boolean z2 = false;
            KtToken[] ktTokenArr = new KtToken[0];
            LighterASTNode[] childrenAsArray = getChildrenAsArray(lighterASTNode);
            int length = childrenAsArray.length;
            obj = null;
            int i = 0;
            while (i < length) {
                LighterASTNode lighterASTNode3 = childrenAsArray[i];
                if (lighterASTNode3 == null) {
                    break;
                }
                IElementType tokenType = lighterASTNode3.getTokenType();
                if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                    if (Intrinsics.areEqual(lighterASTNode3.getTokenType(), KtNodeTypes.MODIFIER_LIST)) {
                        modifier2 = convertModifierList$default(this, lighterASTNode3, z2, 2, null);
                    } else if (Intrinsics.areEqual(lighterASTNode3.getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                        implicitType = convertType(lighterASTNode3);
                    } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode3)) {
                        LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this.expressionConverter;
                        FirElement firElementConvertExpression = lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode3, "Should have initializer");
                        FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                        if (firErrorExpression == null) {
                            FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode3, null, 1, null), new ConeSimpleDiagnostic("Should have initializer", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                            if (firErrorExpressionBuildErrorExpression == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                            }
                            firErrorExpression = firErrorExpressionBuildErrorExpression;
                        }
                        obj = firErrorExpression;
                    } else {
                        continue;
                    }
                }
                i++;
                z2 = false;
            }
        } else {
            obj = null;
        }
        Visibilities.Private visibility = modifier2.getVisibility();
        if (Intrinsics.areEqual(visibility, Visibilities.Unknown.INSTANCE)) {
            visibility = Visibilities.Private.INSTANCE;
        }
        FirDeclarationStatus firDeclarationStatusObtainPropertyComponentStatus = obtainPropertyComponentStatus(visibility, modifier2, modifier);
        KtLightSourceElement ktLightSourceElement = lighterASTNode != null ? (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null) : null;
        if (lighterASTNode == null) {
            return new FirDefaultPropertyBackingField(getBaseModuleData(), FirDeclarationOrigin.Source.INSTANCE, toFirSourceElement(lighterASTNode2, (KtFakeSourceElementKind) KtFakeSourceElementKind.DefaultAccessor.INSTANCE), CollectionsKt.toMutableList((Collection) list), UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.DefaultAccessor.INSTANCE), z, firPropertySymbol, firDeclarationStatusObtainPropertyComponentStatus, null, 256, null);
        }
        FirBackingFieldBuilder firBackingFieldBuilder = new FirBackingFieldBuilder();
        firBackingFieldBuilder.setSource(ktLightSourceElement);
        firBackingFieldBuilder.setModuleData(getBaseModuleData());
        firBackingFieldBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firBackingFieldBuilder.setReturnTypeRef(implicitType);
        firBackingFieldBuilder.setName(StandardNames.BACKING_FIELD);
        firBackingFieldBuilder.setSymbol(new FirBackingFieldSymbol(new CallableId(firBackingFieldBuilder.getName(), (FqName) null, 2, (DefaultConstructorMarker) null)));
        firBackingFieldBuilder.setStatus(firDeclarationStatusObtainPropertyComponentStatus);
        CollectionsKt.addAll(firBackingFieldBuilder.getAnnotations(), modifier2.getAnnotations());
        CollectionsKt.addAll(firBackingFieldBuilder.getAnnotations(), list);
        firBackingFieldBuilder.setPropertySymbol(firPropertySymbol);
        firBackingFieldBuilder.setInitializer((FirExpression) obj);
        firBackingFieldBuilder.setVar(z);
        firBackingFieldBuilder.setVal(true ^ z);
        return firBackingFieldBuilder.mo6809build();
    }

    private final FirDeclarationStatus obtainPropertyComponentStatus(Visibility componentVisibility, Modifier modifiers, Modifier propertyModifiers) {
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(componentVisibility, modifiers.getModality(false));
        firDeclarationStatusImpl.setInline(propertyModifiers.hasInline() || modifiers.hasInline());
        firDeclarationStatusImpl.setExternal(propertyModifiers.hasExternal() || modifiers.hasExternal());
        firDeclarationStatusImpl.setLateInit(modifiers.hasLateinit());
        return firDeclarationStatusImpl;
    }

    private final void extractRawEffects(LighterASTNode rawContractDescription, List<FirExpression> destination) {
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(rawContractDescription)) {
            if (lighterASTNode == null) {
                return;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.CONTRACT_EFFECT)) {
                LighterASTNode firstChild = getFirstChild(lighterASTNode);
                if (firstChild != null) {
                    FirElement firElementConvertExpression = this.expressionConverter.convertExpression(firstChild, "The contract effect is not an expression");
                    Intrinsics.checkNotNull(firElementConvertExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                    destination.add((FirExpression) firElementConvertExpression);
                } else {
                    destination.add(FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSimpleDiagnostic("The contract effect is not an expression", DiagnosticKind.ExpressionExpected), null, 4, null));
                }
            }
        }
    }

    private final FirValueParameter convertSetterParameter(LighterASTNode setterParameter, FirFunctionSymbol<?> functionSymbol, FirTypeRef propertyTypeRef, List<? extends FirAnnotation> additionalAnnotations) {
        boolean z = true;
        FirValueParameter firValueParameter = null;
        Modifier modifier = new Modifier(0L, 1, null);
        KtToken[] ktTokenArr = new KtToken[0];
        Modifier modifierConvertModifierList$default = modifier;
        FirValueParameter firValueParameter2 = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(setterParameter)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifierConvertModifierList$default = convertModifierList$default(this, lighterASTNode, false, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER)) {
                    firValueParameter2 = convertValueParameter$default(this, lighterASTNode, functionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration.SETTER, null, 8, null).getFirValueParameter();
                }
            }
        }
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        if (firValueParameter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("firValueParameter");
        } else {
            firValueParameter = firValueParameter2;
        }
        firValueParameterBuilder.setSource(firValueParameter.getSource());
        firValueParameterBuilder.setContainingFunctionSymbol(functionSymbol);
        firValueParameterBuilder.setModuleData(getBaseModuleData());
        firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firValueParameterBuilder.setReturnTypeRef(Intrinsics.areEqual(firValueParameter2.getReturnTypeRef(), getImplicitType()) ? propertyTypeRef : firValueParameter2.getReturnTypeRef());
        firValueParameterBuilder.setName(firValueParameter2.getName());
        firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameter2.getName()));
        firValueParameterBuilder.setDefaultValue(firValueParameter2.getDefaultValue());
        firValueParameterBuilder.setCrossinline(modifierConvertModifierList$default.hasCrossinline() || firValueParameter2.getIsCrossinline());
        firValueParameterBuilder.setNoinline(modifierConvertModifierList$default.hasNoinline() || firValueParameter2.getIsNoinline());
        if (!modifierConvertModifierList$default.hasVararg() && !firValueParameter2.getIsVararg()) {
            z = false;
        }
        firValueParameterBuilder.setVararg(z);
        CollectionsKt.addAll(firValueParameterBuilder.getAnnotations(), firValueParameter2.getAnnotations());
        CollectionsKt.addAll(firValueParameterBuilder.getAnnotations(), additionalAnnotations);
        return firValueParameterBuilder.mo6809build();
    }

    /* JADX WARN: Code duplicated, block: B:73:0x0141  */
    public final FirStatement convertFunctionDeclaration(LighterASTNode functionDeclaration) {
        boolean z;
        String identifier;
        FirFunctionBuilder firFunctionBuilder;
        FirAnonymousFunctionSymbol firAnonymousFunctionSymbol;
        FirFunctionTarget firFunctionTarget;
        List<FirTypeParameter> listEmptyList;
        String identifier2;
        FirImplicitBuiltinTypeRef implicitUnitType;
        Intrinsics.checkNotNullParameter(functionDeclaration, "functionDeclaration");
        Modifier modifier = new Modifier(0L, 1, null);
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode lighterASTNode = null;
        FirTypeRef firTypeRef = null;
        LighterASTNode lighterASTNode2 = null;
        String asText = null;
        FirTypeRef firTypeRef2 = null;
        LighterASTNode lighterASTNode3 = null;
        LighterASTNode lighterASTNode4 = null;
        FirContractDescription second = null;
        boolean z2 = false;
        boolean z3 = false;
        for (LighterASTNode lighterASTNode5 : getChildrenAsArray(functionDeclaration)) {
            if (lighterASTNode5 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode5.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode5.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifier = convertModifierList$default(this, lighterASTNode5, false, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.IDENTIFIER)) {
                    asText = getAsText(lighterASTNode5);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_PARAMETER_LIST)) {
                    lighterASTNode3 = lighterASTNode5;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER_LIST)) {
                    lighterASTNode = lighterASTNode5;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.COLON)) {
                    z3 = true;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    FirTypeRef firTypeRefConvertType = convertType(lighterASTNode5);
                    if (z3) {
                        firTypeRef = firTypeRefConvertType;
                    } else {
                        firTypeRef2 = firTypeRefConvertType;
                    }
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_CONSTRAINT_LIST)) {
                    CollectionsKt.addAll(arrayList, convertTypeConstraints(lighterASTNode5));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONTRACT_EFFECT_LIST)) {
                    second = obtainContractDescription(lighterASTNode5);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.BLOCK)) {
                    lighterASTNode2 = lighterASTNode5;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.EQ)) {
                    z2 = true;
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode5)) {
                    lighterASTNode4 = lighterASTNode5;
                }
            }
        }
        if (firTypeRef == null) {
            if (lighterASTNode2 != null || !z2) {
                implicitUnitType = getImplicitUnitType();
            } else {
                implicitUnitType = getImplicitType();
            }
            firTypeRef = implicitUnitType;
        }
        LighterASTNode parent = getParent(functionDeclaration);
        if (Intrinsics.areEqual(parent != null ? parent.getTokenType() : null, KtNodeTypes.KT_FILE)) {
            z = false;
        } else if (Intrinsics.areEqual(parent != null ? parent.getTokenType() : null, KtNodeTypes.CLASS_BODY)) {
            z = false;
        } else {
            z = true;
        }
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder = this;
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirDeclarationBuilder, functionDeclaration, null, 1, null);
        boolean z4 = asText == null && z;
        if (z4) {
            firAnonymousFunctionSymbol = new FirAnonymousFunctionSymbol();
            FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
            firAnonymousFunctionBuilder.setSource(ktLightSourceElement);
            firAnonymousFunctionBuilder.setReceiverParameter(firTypeRef2 != null ? ConversionUtilsKt.convertToReceiverParameter(firTypeRef2) : null);
            firAnonymousFunctionBuilder.setSymbol(firAnonymousFunctionSymbol);
            firAnonymousFunctionBuilder.setLambda(false);
            firAnonymousFunctionBuilder.setHasExplicitParameterList(true);
            firAnonymousFunctionBuilder.setLabel(getContext().getLastLabel(functionDeclaration));
            FirLabel label = firAnonymousFunctionBuilder.getLabel();
            if (label == null || (identifier2 = label.getName()) == null) {
                Name name2 = (Name) CollectionsKt.lastOrNull((List) getContext().getCalleeNamesForLambda());
                identifier2 = name2 != null ? name2.getIdentifier() : null;
            }
            FirFunctionTarget firFunctionTarget2 = new FirFunctionTarget(identifier2, false);
            if (modifier.hasSuspend()) {
                firAnonymousFunctionBuilder.setStatus(FirResolvedDeclarationStatusImpl.INSTANCE.getDEFAULT_STATUS_FOR_SUSPEND_FUNCTION_EXPRESSION());
            }
            firFunctionBuilder = firAnonymousFunctionBuilder;
            firFunctionTarget = firFunctionTarget2;
        } else {
            Name nameNameAsSafeName$default = ConverterUtilKt.nameAsSafeName$default(asText, null, 1, null);
            FirLabel lastLabel = getContext().getLastLabel(functionDeclaration);
            if (lastLabel == null || (identifier = lastLabel.getName()) == null) {
                identifier = !nameNameAsSafeName$default.isSpecial() ? nameNameAsSafeName$default.getIdentifier() : null;
            }
            FirFunctionTarget firFunctionTarget3 = new FirFunctionTarget(identifier, false);
            FirNamedFunctionSymbol firNamedFunctionSymbol = new FirNamedFunctionSymbol(callableIdForName(nameNameAsSafeName$default));
            FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
            firSimpleFunctionBuilder.setSource(ktLightSourceElement);
            firSimpleFunctionBuilder.setReceiverParameter(firTypeRef2 != null ? ConversionUtilsKt.convertToReceiverParameter(firTypeRef2) : null);
            firSimpleFunctionBuilder.setName(nameNameAsSafeName$default);
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(z ? Visibilities.Local.INSTANCE : modifier.getVisibility(), modifier.getModality(false));
            firDeclarationStatusImpl.setExpect(modifier.hasExpect() || getContext().getContainerIsExpect());
            firDeclarationStatusImpl.setActual(modifier.hasActual());
            firDeclarationStatusImpl.setOverride(modifier.hasOverride());
            firDeclarationStatusImpl.setOperator(modifier.hasOperator());
            firDeclarationStatusImpl.setInfix(modifier.hasInfix());
            firDeclarationStatusImpl.setInline(modifier.hasInline());
            firDeclarationStatusImpl.setTailRec(modifier.hasTailrec());
            firDeclarationStatusImpl.setExternal(modifier.hasExternal());
            firDeclarationStatusImpl.setSuspend(modifier.hasSuspend());
            firSimpleFunctionBuilder.setStatus(firDeclarationStatusImpl);
            firSimpleFunctionBuilder.setSymbol(firNamedFunctionSymbol);
            firSimpleFunctionBuilder.setDispatchReceiverType(!z ? currentDispatchReceiverType() : null);
            firSimpleFunctionBuilder.getContextReceivers().addAll(convertContextReceivers(functionDeclaration));
            firFunctionBuilder = firSimpleFunctionBuilder;
            firAnonymousFunctionSymbol = firNamedFunctionSymbol;
            firFunctionTarget = firFunctionTarget3;
        }
        ArrayList arrayList2 = new ArrayList();
        LighterASTNode lighterASTNode6 = lighterASTNode3;
        if (lighterASTNode6 != null) {
            CollectionsKt.addAll(arrayList2, convertTypeParameters(lighterASTNode6, arrayList, firAnonymousFunctionSymbol));
        }
        firFunctionBuilder.setModuleData(getBaseModuleData());
        firFunctionBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        Intrinsics.checkNotNull(firTypeRef);
        firFunctionBuilder.setReturnTypeRef(firTypeRef);
        getContext().getFirFunctionTargets().add(firFunctionTarget);
        CollectionsKt.addAll(firFunctionBuilder.getAnnotations(), modifier.getAnnotations());
        if (firFunctionBuilder instanceof FirSimpleFunctionBuilder) {
            FirSimpleFunctionBuilder firSimpleFunctionBuilder2 = (FirSimpleFunctionBuilder) firFunctionBuilder;
            CollectionsKt.addAll(firSimpleFunctionBuilder2.getTypeParameters(), arrayList2);
            listEmptyList = firSimpleFunctionBuilder2.getTypeParameters();
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        KtLightSourceElement ktLightSourceElement2 = ktLightSourceElement;
        lightTreeRawFirDeclarationBuilder.addCapturedTypeParameters(true, ktLightSourceElement2, listEmptyList);
        if (lighterASTNode != null) {
            try {
                List<FirValueParameter> valueParameters = firFunctionBuilder.getValueParameters();
                List listConvertValueParameters$default = convertValueParameters$default(this, lighterASTNode, firAnonymousFunctionSymbol, z4 ? AbstractRawFirBuilder.ValueParameterDeclaration.LAMBDA : AbstractRawFirBuilder.ValueParameterDeclaration.FUNCTION, null, 8, null);
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listConvertValueParameters$default, 10));
                Iterator it = listConvertValueParameters$default.iterator();
                while (it.hasNext()) {
                    arrayList3.add(((ValueParameter) it.next()).getFirValueParameter());
                }
                CollectionsKt.addAll(valueParameters, arrayList3);
            } catch (Throwable th) {
                lightTreeRawFirDeclarationBuilder.getContext().popFirTypeParameters();
                throw th;
            }
        }
        Pair<FirBlock, FirContractDescription> pairConvertFunctionBody = convertFunctionBody(lighterASTNode2, lighterASTNode4, second == null);
        firFunctionBuilder.setBody(pairConvertFunctionBody.getFirst());
        if (second == null) {
            second = pairConvertFunctionBody.getSecond();
        }
        FirContractDescription firContractDescription = second;
        if (firContractDescription != null) {
            if (firFunctionBuilder instanceof FirSimpleFunctionBuilder) {
                ((FirSimpleFunctionBuilder) firFunctionBuilder).setContractDescription(firContractDescription);
            } else if (firFunctionBuilder instanceof FirAnonymousFunctionBuilder) {
                ((FirAnonymousFunctionBuilder) firFunctionBuilder).setContractDescription(firContractDescription);
            }
            Unit unit = Unit.INSTANCE;
        }
        lightTreeRawFirDeclarationBuilder.getContext().popFirTypeParameters();
        removeLast(getContext().getFirFunctionTargets());
        FirFunction firFunctionMo6809build = firFunctionBuilder.mo6809build();
        firFunctionTarget.bind(firFunctionMo6809build);
        if (firFunctionMo6809build instanceof FirSimpleFunction) {
            fillDanglingConstraintsTo(arrayList2, arrayList, firFunctionMo6809build);
        }
        if (!(firFunctionMo6809build instanceof FirAnonymousFunction)) {
            return firFunctionMo6809build;
        }
        FirAnonymousFunctionExpressionBuilder firAnonymousFunctionExpressionBuilder = new FirAnonymousFunctionExpressionBuilder();
        firAnonymousFunctionExpressionBuilder.setSource(ktLightSourceElement2);
        firAnonymousFunctionExpressionBuilder.setAnonymousFunction((FirAnonymousFunction) firFunctionMo6809build);
        return firAnonymousFunctionExpressionBuilder.mo6809build();
    }

    private final Pair<FirBlock, FirContractDescription> convertFunctionBody(LighterASTNode blockNode, LighterASTNode expression, boolean allowLegacyContractDescription) {
        if (blockNode != null) {
            FirBlock firBlockConvertBlock = convertBlock(blockNode);
            return TuplesKt.to(firBlockConvertBlock, allowLegacyContractDescription ? ConversionUtilsKt.processLegacyContractDescription(firBlockConvertBlock) : null);
        }
        if (expression != null) {
            LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder = this;
            LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this.expressionConverter;
            FirElement firElementConvertExpression = lightTreeRawFirExpressionBuilder.convertExpression(expression, "Function has no body (but should)");
            FirAnnotationContainer firAnnotationContainer = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
            if (firAnnotationContainer == null) {
                FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, expression, null, 1, null), new ConeSimpleDiagnostic("Function has no body (but should)", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                if (firAnnotationContainerBuildErrorExpression == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                }
                firAnnotationContainer = (FirExpression) firAnnotationContainerBuildErrorExpression;
            }
            return TuplesKt.to(new FirSingleExpressionBlock(AbstractRawFirBuilder.toReturn$default(lightTreeRawFirDeclarationBuilder, (FirExpression) firAnnotationContainer, null, null, false, 7, null)), null);
        }
        return TuplesKt.to(null, null);
    }

    public final FirBlock convertBlock(LighterASTNode block) {
        if (block == null) {
            return FirEmptyExpressionBlockBuilderKt.buildEmptyExpressionBlock();
        }
        if (!Intrinsics.areEqual(block.getTokenType(), KtNodeTypes.BLOCK)) {
            LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this.expressionConverter;
            FirElement firElementConvertExpression = lightTreeRawFirExpressionBuilder.convertExpression(block, "");
            FirAnnotationContainer firAnnotationContainer = (FirStatement) (!(firElementConvertExpression instanceof FirStatement) ? null : firElementConvertExpression);
            if (firAnnotationContainer == null) {
                FirAnnotationContainer firAnnotationContainerBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, block, null, 1, null), new ConeSimpleDiagnostic("", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                if (firAnnotationContainerBuildErrorExpression == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
                }
                firAnnotationContainer = (FirStatement) firAnnotationContainerBuildErrorExpression;
            }
            return new FirSingleExpressionBlock((FirStatement) firAnnotationContainer);
        }
        return convertBlockExpression(block);
    }

    /* JADX INFO: compiled from: LightTreeRawFirDeclarationBuilder.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B5\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\u0010\u000bJ\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J\u0015\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bHÆ\u0003J?\u0010\u0014\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bHÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\tHÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/converter/LightTreeRawFirDeclarationBuilder$DelegationSpecifiers;", "", "superTypeCalls", "", "Lorg/jetbrains/kotlin/fir/lightTree/fir/DelegatedConstructorWrapper;", "superTypesRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "delegateFieldsMap", "", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "(Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V", "getDelegateFieldsMap", "()Ljava/util/Map;", "getSuperTypeCalls", "()Ljava/util/List;", "getSuperTypesRef", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class DelegationSpecifiers {
        private final Map<Integer, FirFieldSymbol> delegateFieldsMap;
        private final List<DelegatedConstructorWrapper> superTypeCalls;
        private final List<FirTypeRef> superTypesRef;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DelegationSpecifiers copy$default(DelegationSpecifiers delegationSpecifiers, List list, List list2, Map map, int i, Object obj) {
            if ((i & 1) != 0) {
                list = delegationSpecifiers.superTypeCalls;
            }
            if ((i & 2) != 0) {
                list2 = delegationSpecifiers.superTypesRef;
            }
            if ((i & 4) != 0) {
                map = delegationSpecifiers.delegateFieldsMap;
            }
            return delegationSpecifiers.copy(list, list2, map);
        }

        public final List<DelegatedConstructorWrapper> component1() {
            return this.superTypeCalls;
        }

        public final List<FirTypeRef> component2() {
            return this.superTypesRef;
        }

        public final Map<Integer, FirFieldSymbol> component3() {
            return this.delegateFieldsMap;
        }

        public final DelegationSpecifiers copy(List<DelegatedConstructorWrapper> superTypeCalls, List<? extends FirTypeRef> superTypesRef, Map<Integer, ? extends FirFieldSymbol> delegateFieldsMap) {
            Intrinsics.checkNotNullParameter(superTypeCalls, "superTypeCalls");
            Intrinsics.checkNotNullParameter(superTypesRef, "superTypesRef");
            Intrinsics.checkNotNullParameter(delegateFieldsMap, "delegateFieldsMap");
            return new DelegationSpecifiers(superTypeCalls, superTypesRef, delegateFieldsMap);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DelegationSpecifiers)) {
                return false;
            }
            DelegationSpecifiers delegationSpecifiers = (DelegationSpecifiers) other;
            return Intrinsics.areEqual(this.superTypeCalls, delegationSpecifiers.superTypeCalls) && Intrinsics.areEqual(this.superTypesRef, delegationSpecifiers.superTypesRef) && Intrinsics.areEqual(this.delegateFieldsMap, delegationSpecifiers.delegateFieldsMap);
        }

        public int hashCode() {
            return (((this.superTypeCalls.hashCode() * 31) + this.superTypesRef.hashCode()) * 31) + this.delegateFieldsMap.hashCode();
        }

        public String toString() {
            return "DelegationSpecifiers(superTypeCalls=" + this.superTypeCalls + ", superTypesRef=" + this.superTypesRef + ", delegateFieldsMap=" + this.delegateFieldsMap + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DelegationSpecifiers(List<DelegatedConstructorWrapper> superTypeCalls, List<? extends FirTypeRef> superTypesRef, Map<Integer, ? extends FirFieldSymbol> delegateFieldsMap) {
            Intrinsics.checkNotNullParameter(superTypeCalls, "superTypeCalls");
            Intrinsics.checkNotNullParameter(superTypesRef, "superTypesRef");
            Intrinsics.checkNotNullParameter(delegateFieldsMap, "delegateFieldsMap");
            this.superTypeCalls = superTypeCalls;
            this.superTypesRef = superTypesRef;
            this.delegateFieldsMap = delegateFieldsMap;
        }

        public final List<DelegatedConstructorWrapper> getSuperTypeCalls() {
            return this.superTypeCalls;
        }

        public final List<FirTypeRef> getSuperTypesRef() {
            return this.superTypesRef;
        }

        public final Map<Integer, FirFieldSymbol> getDelegateFieldsMap() {
            return this.delegateFieldsMap;
        }
    }

    private final DelegationSpecifiers convertDelegationSpecifiers(LighterASTNode delegationSpecifiers) {
        LighterASTNode[] lighterASTNodeArr;
        KtToken[] ktTokenArr;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i = 0;
        KtToken[] ktTokenArr2 = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(delegationSpecifiers);
        int length = childrenAsArray.length;
        int i2 = 0;
        while (i < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr2, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                lighterASTNodeArr = childrenAsArray;
                ktTokenArr = ktTokenArr2;
            } else {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.SUPER_TYPE_ENTRY)) {
                    arrayList.add(convertType(lighterASTNode));
                    i2++;
                    lighterASTNodeArr = childrenAsArray;
                    ktTokenArr = ktTokenArr2;
                } else {
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.SUPER_TYPE_CALL_ENTRY)) {
                        Pair<FirTypeRef, List<FirExpression>> pairConvertConstructorInvocation = convertConstructorInvocation(lighterASTNode);
                        lighterASTNodeArr = childrenAsArray;
                        ktTokenArr = ktTokenArr2;
                        arrayList2.add(new DelegatedConstructorWrapper(pairConvertConstructorInvocation.getFirst(), pairConvertConstructorInvocation.getSecond(), (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null)));
                        arrayList.add(pairConvertConstructorInvocation.getFirst());
                    } else {
                        lighterASTNodeArr = childrenAsArray;
                        ktTokenArr = ktTokenArr2;
                        if (Intrinsics.areEqual(tokenType2, KtNodeTypes.DELEGATED_SUPER_TYPE_ENTRY)) {
                            arrayList.add(convertExplicitDelegation(lighterASTNode, linkedHashMap, i2));
                        }
                    }
                    i2++;
                }
            }
            i++;
            childrenAsArray = lighterASTNodeArr;
            ktTokenArr2 = ktTokenArr;
        }
        return new DelegationSpecifiers(arrayList2, arrayList, linkedHashMap);
    }

    private final Pair<FirTypeRef, List<FirExpression>> convertConstructorInvocation(LighterASTNode constructorInvocation) {
        FirTypeRef implicitType = getImplicitType();
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(constructorInvocation)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONSTRUCTOR_CALLEE)) {
                    implicitType = convertType(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_ARGUMENT_LIST)) {
                    CollectionsKt.addAll(arrayList, this.expressionConverter.convertValueArguments(lighterASTNode));
                }
            }
        }
        return new Pair<>(implicitType, arrayList);
    }

    private final FirTypeRef convertExplicitDelegation(LighterASTNode explicitDelegation, Map<Integer, FirFieldSymbol> delegateFieldsMap, int index) {
        LighterASTNode lighterASTNode;
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(explicitDelegation);
        int length = childrenAsArray.length;
        FirTypeRef firTypeRef = null;
        FirTypeRef firTypeRefConvertType = null;
        Object obj = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                if (Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = convertType(lighterASTNode);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this.expressionConverter;
                    FirElement firElementConvertExpression = lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode, "Should have delegate");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Should have delegate", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                } else {
                    continue;
                }
            }
        }
        FirErrorExpression firErrorExpressionBuildErrorExpression$default = (FirExpression) obj;
        if (firErrorExpressionBuildErrorExpression$default == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(AbstractRawFirBuilder.toFirSourceElement$default(this, explicitDelegation, null, 1, null), new ConeSyntaxDiagnostic("Should have delegate"), null, 4, null);
        }
        Integer numValueOf = Integer.valueOf(index);
        FirFieldBuilder firFieldBuilder = new FirFieldBuilder();
        firFieldBuilder.setSource(KtSourceElementKt.fakeElement$default(AbstractRawFirBuilder.toFirSourceElement$default(this, explicitDelegation, null, 1, null), KtFakeSourceElementKind.ClassDelegationField.INSTANCE, 0, 0, 6, null));
        firFieldBuilder.setModuleData(getBaseModuleData());
        firFieldBuilder.setOrigin(FirDeclarationOrigin.Synthetic.DelegateField.INSTANCE);
        firFieldBuilder.setName(NameUtils.delegateFieldName(delegateFieldsMap.size()));
        if (firTypeRefConvertType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("firTypeRef");
        } else {
            firTypeRef = firTypeRefConvertType;
        }
        firFieldBuilder.setReturnTypeRef(firTypeRef);
        firFieldBuilder.setSymbol(new FirFieldSymbol(new CallableId(getContext().getCurrentClassId(), firFieldBuilder.getName())));
        firFieldBuilder.setVar(false);
        firFieldBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Private.INSTANCE, Modality.FINAL));
        firFieldBuilder.setInitializer(firErrorExpressionBuildErrorExpression$default);
        firFieldBuilder.setDispatchReceiverType(currentDispatchReceiverType());
        Unit unit = Unit.INSTANCE;
        delegateFieldsMap.put(numValueOf, firFieldBuilder.mo6809build().getSymbol());
        return firTypeRefConvertType;
    }

    private final List<FirTypeParameter> convertTypeParameters(LighterASTNode typeParameterList, List<TypeConstraint> typeConstraints, FirBasedSymbol<?> containingDeclarationSymbol) {
        LighterASTNode[] childrenAsArray = getChildrenAsArray(typeParameterList);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.TYPE_PARAMETER)) {
                arrayList.add(convertTypeParameter(lighterASTNode, typeConstraints, containingDeclarationSymbol));
            }
        }
        return arrayList;
    }

    private final List<TypeConstraint> convertTypeConstraints(LighterASTNode typeConstraints) {
        LighterASTNode[] childrenAsArray = getChildrenAsArray(typeConstraints);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.TYPE_CONSTRAINT)) {
                arrayList.add(convertTypeConstraint(lighterASTNode));
            }
        }
        return arrayList;
    }

    private final TypeConstraint convertTypeConstraint(LighterASTNode typeConstraint) {
        LighterASTNode lighterASTNode;
        ConeSimpleDiagnostic coneSimpleDiagnostic = new ConeSimpleDiagnostic("Type parameter annotations are not allowed inside where clauses", DiagnosticKind.AnnotationNotAllowed);
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        String asText = null;
        FirTypeRef firTypeRefConvertType = null;
        LighterASTNode lighterASTNode2 = null;
        for (LighterASTNode lighterASTNode3 : getChildrenAsArray(typeConstraint)) {
            if (lighterASTNode3 == null) {
                break;
            }
            IElementType tokenType = lighterASTNode3.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode3.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_ENTRY)) {
                    arrayList.add(convertAnnotationEntry$default(this, lighterASTNode3, null, coneSimpleDiagnostic, 2, null));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.REFERENCE_EXPRESSION)) {
                    asText = getAsText(lighterASTNode3);
                    lighterASTNode2 = lighterASTNode3;
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = convertType(lighterASTNode3);
                }
            }
        }
        if (asText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("identifier");
            asText = null;
        }
        if (firTypeRefConvertType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("firType");
            firTypeRefConvertType = null;
        }
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder = this;
        if (lighterASTNode2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("referenceExpression");
            lighterASTNode = null;
        } else {
            lighterASTNode = lighterASTNode2;
        }
        return new TypeConstraint(arrayList, asText, firTypeRefConvertType, AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirDeclarationBuilder, lighterASTNode, null, 1, null));
    }

    private final FirTypeParameter convertTypeParameter(LighterASTNode typeParameter, List<TypeConstraint> typeConstraints, FirBasedSymbol<?> containingSymbol) {
        TypeParameterModifier typeParameterModifier = new TypeParameterModifier(0L, 1, null);
        KtToken[] ktTokenArr = new KtToken[0];
        String asText = null;
        FirTypeRef firTypeRefConvertType = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(typeParameter)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    typeParameterModifier = convertTypeParameterModifiers(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.IDENTIFIER)) {
                    asText = getAsText(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = convertType(lighterASTNode);
                }
            }
        }
        FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
        firTypeParameterBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, typeParameter, null, 1, null));
        firTypeParameterBuilder.setModuleData(getBaseModuleData());
        firTypeParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firTypeParameterBuilder.setName(ConverterUtilKt.nameAsSafeName$default(asText, null, 1, null));
        firTypeParameterBuilder.setSymbol(new FirTypeParameterSymbol());
        firTypeParameterBuilder.setContainingDeclarationSymbol(containingSymbol);
        firTypeParameterBuilder.setVariance(typeParameterModifier.getVariance());
        firTypeParameterBuilder.setReified(typeParameterModifier.hasReified());
        CollectionsKt.addAll(firTypeParameterBuilder.getAnnotations(), typeParameterModifier.getAnnotations());
        if (firTypeRefConvertType != null) {
            firTypeParameterBuilder.getBounds().add(firTypeRefConvertType);
        }
        for (TypeConstraint typeConstraint : typeConstraints) {
            if (Intrinsics.areEqual(typeConstraint.getIdentifier(), asText)) {
                firTypeParameterBuilder.getBounds().add(typeConstraint.getFirTypeRef());
                CollectionsKt.addAll(firTypeParameterBuilder.getAnnotations(), typeConstraint.getAnnotations());
            }
        }
        FirDeclarationBuildingUtilsKt.addDefaultBoundIfNecessary(firTypeParameterBuilder);
        return firTypeParameterBuilder.mo6809build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [T, org.jetbrains.kotlin.fir.types.FirErrorTypeRef] */
    /* JADX WARN: Type inference failed for: r0v17, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    /* JADX WARN: Type inference failed for: r0v19, types: [T, org.jetbrains.kotlin.fir.types.FirDynamicTypeRef] */
    /* JADX WARN: Type inference failed for: r0v23, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    /* JADX WARN: Type inference failed for: r0v25, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    /* JADX WARN: Type inference failed for: r0v27, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    /* JADX WARN: Type inference failed for: r0v30, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    public final FirTypeRef convertType(LighterASTNode type) {
        int i;
        Intrinsics.checkNotNullParameter(type, "type");
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, type, null, 1, null);
        ArrayList arrayList = new ArrayList();
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(type);
        int length = childrenAsArray.length;
        int i2 = 0;
        while (i2 < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i2];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                i = i2;
            } else {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    objectRef.element = convertType(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    arrayList.add(convertTypeModifierList(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.USER_TYPE)) {
                    objectRef.element = convertUserType$default(this, ktLightSourceElement, lighterASTNode, false, 4, null);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.NULLABLE_TYPE)) {
                    i = i2;
                    objectRef.element = convertNullableType$default(this, ktLightSourceElement, lighterASTNode, arrayList, false, 8, null);
                } else {
                    i = i2;
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.FUNCTION_TYPE)) {
                        objectRef.element = convertFunctionType$default(this, ktLightSourceElement, lighterASTNode, false, hasSuspend(arrayList), 4, null);
                    } else if (!Intrinsics.areEqual(tokenType2, KtNodeTypes.DYNAMIC_TYPE)) {
                        if (Intrinsics.areEqual(tokenType2, KtNodeTypes.INTERSECTION_TYPE)) {
                            objectRef.element = convertIntersectionType(ktLightSourceElement, lighterASTNode, false);
                        } else {
                            if (Intrinsics.areEqual(tokenType2, KtNodeTypes.CONTEXT_RECEIVER_LIST) ? true : Intrinsics.areEqual(tokenType2, TokenType.ERROR_ELEMENT)) {
                                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                                firErrorTypeRefBuilder.setSource(ktLightSourceElement);
                                firErrorTypeRefBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Unwrapped type is null"));
                                objectRef.element = firErrorTypeRefBuilder.mo6809build();
                            }
                        }
                    } else {
                        FirDynamicTypeRefBuilder firDynamicTypeRefBuilder = new FirDynamicTypeRefBuilder();
                        firDynamicTypeRefBuilder.setSource(ktLightSourceElement);
                        firDynamicTypeRefBuilder.setMarkedNullable(false);
                        objectRef.element = firDynamicTypeRefBuilder.mo6809build();
                    }
                }
                i = i2;
            }
            i2 = i + 1;
        }
        FirErrorTypeRef firErrorTypeRefMo6809build = (FirTypeRef) objectRef.element;
        if (firErrorTypeRefMo6809build == null) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder2.setSource(ktLightSourceElement);
            firErrorTypeRefBuilder2.setDiagnostic(new ConeSyntaxDiagnostic("Incomplete code"));
            firErrorTypeRefMo6809build = firErrorTypeRefBuilder2.mo6809build();
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            firErrorTypeRefMo6809build.replaceAnnotations(UtilsKt.smartPlus(firErrorTypeRefMo6809build.getAnnotations(), ((Modifier) it.next()).getAnnotations()));
        }
        return firErrorTypeRefMo6809build;
    }

    private final boolean hasSuspend(Collection<? extends Modifier> collection) {
        Collection<? extends Modifier> collection2 = collection;
        if ((collection2 instanceof Collection) && collection2.isEmpty()) {
            return false;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (((Modifier) it.next()).hasSuspend()) {
                return true;
            }
        }
        return false;
    }

    private final FirTypeRef convertIntersectionType(KtSourceElement typeRefSource, LighterASTNode intersectionType, boolean isNullable) {
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(intersectionType)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && !Intrinsics.areEqual(lighterASTNode.getTokenType(), KtTokens.AND)) {
                arrayList.add(convertType(lighterASTNode));
            }
        }
        if (arrayList.size() != 2) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setSource(typeRefSource);
            firErrorTypeRefBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Wrong code"));
            return firErrorTypeRefBuilder.mo6809build();
        }
        FirIntersectionTypeRefBuilder firIntersectionTypeRefBuilder = new FirIntersectionTypeRefBuilder();
        firIntersectionTypeRefBuilder.setSource(typeRefSource);
        firIntersectionTypeRefBuilder.setMarkedNullable(isNullable);
        Object obj = arrayList.get(0);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        firIntersectionTypeRefBuilder.setLeftType((FirTypeRef) obj);
        Object obj2 = arrayList.get(1);
        Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
        firIntersectionTypeRefBuilder.setRightType((FirTypeRef) obj2);
        return firIntersectionTypeRefBuilder.mo6809build();
    }

    private final FirTypeRef convertReceiverType(LighterASTNode receiverType) throws Exception {
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(receiverType)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                return convertType(lighterASTNode);
            }
        }
        throw new Exception();
    }

    static /* synthetic */ FirTypeRef convertNullableType$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, KtSourceElement ktSourceElement, LighterASTNode lighterASTNode, List list, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return lightTreeRawFirDeclarationBuilder.convertNullableType(ktSourceElement, lighterASTNode, list, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v12, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    /* JADX WARN: Type inference failed for: r4v13, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    /* JADX WARN: Type inference failed for: r4v4, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    /* JADX WARN: Type inference failed for: r4v6, types: [T, org.jetbrains.kotlin.fir.types.FirDynamicTypeRef] */
    /* JADX WARN: Type inference failed for: r4v8, types: [T, org.jetbrains.kotlin.fir.types.FirTypeRef] */
    private final FirTypeRef convertNullableType(KtSourceElement typeRefSource, LighterASTNode nullableType, List<Modifier> allTypeModifiers, boolean isNullable) {
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        KtToken[] ktTokenArr = new KtToken[0];
        for (LighterASTNode lighterASTNode : getChildrenAsArray(nullableType)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    allTypeModifiers.add(convertTypeModifierList(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.USER_TYPE)) {
                    objectRef.element = convertUserType(typeRefSource, lighterASTNode, isNullable);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.FUNCTION_TYPE)) {
                    objectRef.element = convertFunctionType(typeRefSource, lighterASTNode, isNullable, hasSuspend(allTypeModifiers));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.NULLABLE_TYPE)) {
                    objectRef.element = convertNullableType$default(this, typeRefSource, lighterASTNode, allTypeModifiers, false, 8, null);
                } else if (!Intrinsics.areEqual(tokenType2, KtNodeTypes.DYNAMIC_TYPE)) {
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.INTERSECTION_TYPE)) {
                        objectRef.element = convertIntersectionType(typeRefSource, lighterASTNode, isNullable);
                    }
                } else {
                    FirDynamicTypeRefBuilder firDynamicTypeRefBuilder = new FirDynamicTypeRefBuilder();
                    firDynamicTypeRefBuilder.setSource(typeRefSource);
                    firDynamicTypeRefBuilder.setMarkedNullable(true);
                    objectRef.element = firDynamicTypeRefBuilder.mo6809build();
                }
            }
        }
        if (objectRef.element != 0) {
            return (FirTypeRef) objectRef.element;
        }
        Intrinsics.throwUninitializedPropertyAccessException("firType");
        return null;
    }

    static /* synthetic */ FirTypeRef convertUserType$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, KtSourceElement ktSourceElement, LighterASTNode lighterASTNode, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return lightTreeRawFirDeclarationBuilder.convertUserType(ktSourceElement, lighterASTNode, z);
    }

    private final FirTypeRef convertUserType(KtSourceElement typeRefSource, LighterASTNode userType, boolean isNullable) {
        List<FirQualifierPart> qualifier;
        LighterASTNode lighterASTNode;
        KtSourceElement ktSourceElement;
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(userType);
        int length = childrenAsArray.length;
        FirUserTypeRef firUserTypeRef = null;
        KtSourceElement firSourceElement$default = null;
        String asText = null;
        KtSourceElement ktSourceElement2 = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                ktSourceElement = firSourceElement$default;
            } else {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.USER_TYPE)) {
                    ktSourceElement = firSourceElement$default;
                    FirTypeRef firTypeRefConvertUserType$default = convertUserType$default(this, typeRefSource, lighterASTNode, false, 4, null);
                    firUserTypeRef = firTypeRefConvertUserType$default instanceof FirUserTypeRef ? (FirUserTypeRef) firTypeRefConvertUserType$default : null;
                } else {
                    ktSourceElement = firSourceElement$default;
                    if (Intrinsics.areEqual(tokenType2, KtNodeTypes.REFERENCE_EXPRESSION)) {
                        firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null);
                        asText = getAsText(lighterASTNode);
                    } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_ARGUMENT_LIST)) {
                        KtSourceElement firSourceElement$default2 = AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null);
                        CollectionsKt.addAll(arrayList, convertTypeArguments(lighterASTNode, false));
                        firSourceElement$default = ktSourceElement;
                        ktSourceElement2 = firSourceElement$default2;
                    }
                }
            }
            firSourceElement$default = ktSourceElement;
        }
        KtSourceElement ktSourceElement3 = firSourceElement$default;
        if (asText != null) {
            Intrinsics.checkNotNull(ktSourceElement3);
            Name nameNameAsSafeName$default = ConverterUtilKt.nameAsSafeName$default(asText, null, 1, null);
            FirTypeArgumentListImpl firTypeArgumentListImpl = new FirTypeArgumentListImpl(ktSourceElement2 == null ? typeRefSource : ktSourceElement2);
            CollectionsKt.addAll(firTypeArgumentListImpl.getTypeArguments(), arrayList);
            Unit unit = Unit.INSTANCE;
            FirQualifierPartImpl firQualifierPartImpl = new FirQualifierPartImpl(ktSourceElement3, nameNameAsSafeName$default, firTypeArgumentListImpl);
            FirUserTypeRefBuilder firUserTypeRefBuilder = new FirUserTypeRefBuilder();
            firUserTypeRefBuilder.setSource(typeRefSource);
            firUserTypeRefBuilder.setMarkedNullable(isNullable);
            firUserTypeRefBuilder.getQualifier().add(firQualifierPartImpl);
            if (firUserTypeRef != null && (qualifier = firUserTypeRef.getQualifier()) != null) {
                firUserTypeRefBuilder.getQualifier().addAll(0, qualifier);
            }
            return firUserTypeRefBuilder.mo6809build();
        }
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setSource(typeRefSource);
        firErrorTypeRefBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Incomplete user type"));
        if (firUserTypeRef != null && !firUserTypeRef.getQualifier().isEmpty()) {
            FirUserTypeRefBuilder firUserTypeRefBuilder2 = new FirUserTypeRefBuilder();
            firUserTypeRefBuilder2.setSource(((FirQualifierPart) CollectionsKt.last((List) firUserTypeRef.getQualifier())).getSource());
            firUserTypeRefBuilder2.setMarkedNullable(false);
            firUserTypeRefBuilder2.getQualifier().addAll(firUserTypeRef.getQualifier());
            firErrorTypeRefBuilder.setPartiallyResolvedTypeRef(firUserTypeRefBuilder2.mo6809build());
        }
        return firErrorTypeRefBuilder.mo6809build();
    }

    public final List<FirTypeProjection> convertTypeArguments(LighterASTNode typeArguments, boolean allowedUnderscoredTypeArgument) {
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        LighterASTNode[] childrenAsArray = getChildrenAsArray(typeArguments);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.TYPE_PROJECTION)) {
                arrayList.add(convertTypeProjection(lighterASTNode, allowedUnderscoredTypeArgument));
            }
        }
        return arrayList;
    }

    private final FirTypeProjection convertTypeProjection(LighterASTNode typeProjection, boolean allowedUnderscoredTypeArgument) {
        FirTypeRef firTypeRef;
        LighterASTNode lighterASTNode;
        TypeProjectionModifier typeProjectionModifier = new TypeProjectionModifier(null, 0L, 3, null);
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(typeProjection);
        int length = childrenAsArray.length;
        FirTypeRef firTypeRef2 = null;
        boolean z = false;
        FirTypeRef firTypeRefConvertType = null;
        for (int i = 0; i < length && (lighterASTNode = childrenAsArray[i]) != null; i++) {
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    typeProjectionModifier = convertTypeArgumentModifierList(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = convertType(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.MUL)) {
                    z = true;
                }
            }
        }
        if (z) {
            FirStarProjectionBuilder firStarProjectionBuilder = new FirStarProjectionBuilder();
            firStarProjectionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, typeProjection, null, 1, null));
            return firStarProjectionBuilder.build();
        }
        if (allowedUnderscoredTypeArgument) {
            if (firTypeRefConvertType == null) {
                Intrinsics.throwUninitializedPropertyAccessException("firType");
                firTypeRef = null;
            } else {
                firTypeRef = firTypeRefConvertType;
            }
            FirUserTypeRef firUserTypeRef = firTypeRef instanceof FirUserTypeRef ? (FirUserTypeRef) firTypeRef : null;
            if (firUserTypeRef != null && ConverterUtilKt.isUnderscored(firUserTypeRef)) {
                FirPlaceholderProjectionBuilder firPlaceholderProjectionBuilder = new FirPlaceholderProjectionBuilder();
                firPlaceholderProjectionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, typeProjection, null, 1, null));
                return firPlaceholderProjectionBuilder.build();
            }
        }
        FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
        firTypeProjectionWithVarianceBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, typeProjection, null, 1, null));
        if (firTypeRefConvertType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("firType");
        } else {
            firTypeRef2 = firTypeRefConvertType;
        }
        firTypeProjectionWithVarianceBuilder.setTypeRef(firTypeRef2);
        firTypeProjectionWithVarianceBuilder.setVariance(typeProjectionModifier.getVariance());
        return firTypeProjectionWithVarianceBuilder.build();
    }

    static /* synthetic */ FirTypeRef convertFunctionType$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, KtSourceElement ktSourceElement, LighterASTNode lighterASTNode, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        return lightTreeRawFirDeclarationBuilder.convertFunctionType(ktSourceElement, lighterASTNode, z, z2);
    }

    private final FirTypeRef convertFunctionType(KtSourceElement typeRefSource, LighterASTNode functionType, boolean isNullable, boolean isSuspend) throws Exception {
        ArrayList arrayList = new ArrayList();
        KtToken[] ktTokenArr = new KtToken[0];
        ArrayList arrayListEmptyList = null;
        FirTypeRef firTypeRefConvertReceiverType = null;
        FirTypeRef firTypeRefConvertType = null;
        for (LighterASTNode lighterASTNode : getChildrenAsArray(functionType)) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.FUNCTION_TYPE_RECEIVER)) {
                    firTypeRefConvertReceiverType = convertReceiverType(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.VALUE_PARAMETER_LIST)) {
                    CollectionsKt.addAll(arrayList, convertFunctionTypeParameters(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = convertType(lighterASTNode);
                }
            }
        }
        FirFunctionTypeRefBuilder firFunctionTypeRefBuilder = new FirFunctionTypeRefBuilder();
        firFunctionTypeRefBuilder.setSource(typeRefSource);
        firFunctionTypeRefBuilder.setMarkedNullable(isNullable);
        firFunctionTypeRefBuilder.setReceiverTypeRef(firTypeRefConvertReceiverType);
        if (firTypeRefConvertType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("returnTypeReference");
            firTypeRefConvertType = null;
        }
        firFunctionTypeRefBuilder.setReturnTypeRef(firTypeRefConvertType);
        CollectionsKt.addAll(firFunctionTypeRefBuilder.getParameters(), arrayList);
        firFunctionTypeRefBuilder.setSuspend(isSuspend);
        List<FirTypeRef> contextReceiverTypeRefs = firFunctionTypeRefBuilder.getContextReceiverTypeRefs();
        IElementType CONTEXT_RECEIVER_LIST = KtNodeTypes.CONTEXT_RECEIVER_LIST;
        Intrinsics.checkNotNullExpressionValue(CONTEXT_RECEIVER_LIST, "CONTEXT_RECEIVER_LIST");
        LighterASTNode childNodeByType = getChildNodeByType(functionType, CONTEXT_RECEIVER_LIST);
        if (childNodeByType != null) {
            IElementType CONTEXT_RECEIVER = KtNodeTypes.CONTEXT_RECEIVER;
            Intrinsics.checkNotNullExpressionValue(CONTEXT_RECEIVER, "CONTEXT_RECEIVER");
            List<LighterASTNode> childNodesByType = getChildNodesByType(childNodeByType, CONTEXT_RECEIVER);
            if (childNodesByType != null) {
                ArrayList arrayList2 = new ArrayList();
                for (LighterASTNode lighterASTNode2 : childNodesByType) {
                    IElementType TYPE_REFERENCE = KtNodeTypes.TYPE_REFERENCE;
                    Intrinsics.checkNotNullExpressionValue(TYPE_REFERENCE, "TYPE_REFERENCE");
                    LighterASTNode childNodeByType2 = getChildNodeByType(lighterASTNode2, TYPE_REFERENCE);
                    FirTypeRef firTypeRefConvertType2 = childNodeByType2 != null ? convertType(childNodeByType2) : null;
                    if (firTypeRefConvertType2 != null) {
                        arrayList2.add(firTypeRefConvertType2);
                    }
                }
                arrayListEmptyList = arrayList2;
            }
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        contextReceiverTypeRefs.addAll(arrayListEmptyList);
        return firFunctionTypeRefBuilder.mo6809build();
    }

    private final List<FirFunctionTypeParameter> convertFunctionTypeParameters(LighterASTNode parameters) {
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder = this;
        LighterASTNode[] childrenAsArray = lightTreeRawFirDeclarationBuilder.getChildrenAsArray(parameters);
        ArrayList arrayList = new ArrayList();
        int length = childrenAsArray.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i2];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.VALUE_PARAMETER)) {
                KtToken[] ktTokenArr = new KtToken[i];
                LighterASTNode[] childrenAsArray2 = lightTreeRawFirDeclarationBuilder.getChildrenAsArray(lighterASTNode);
                int length2 = childrenAsArray2.length;
                Name nameNameAsSafeName$default = null;
                FirErrorTypeRef firErrorTypeRefCreateNoTypeForParameterTypeRef = null;
                for (int i3 = i; i3 < length2; i3++) {
                    LighterASTNode lighterASTNode2 = childrenAsArray2[i3];
                    if (lighterASTNode2 == null) {
                        break;
                    }
                    IElementType tokenType2 = lighterASTNode2.getTokenType();
                    if (!KtTokens.COMMENTS.contains(tokenType2) && !Intrinsics.areEqual(tokenType2, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType2, KtTokens.SEMICOLON) && !ArraysKt.contains(ktTokenArr, tokenType2) && !Intrinsics.areEqual(tokenType2, TokenType.ERROR_ELEMENT)) {
                        IElementType tokenType3 = lighterASTNode2.getTokenType();
                        if (Intrinsics.areEqual(tokenType3, KtTokens.IDENTIFIER)) {
                            nameNameAsSafeName$default = ConverterUtilKt.nameAsSafeName$default(getAsText(lighterASTNode2), null, 1, null);
                        } else if (Intrinsics.areEqual(tokenType3, KtNodeTypes.TYPE_REFERENCE)) {
                            firErrorTypeRefCreateNoTypeForParameterTypeRef = convertType(lighterASTNode2);
                        }
                    }
                }
                ArrayList arrayList2 = arrayList;
                FirFunctionTypeParameterBuilder firFunctionTypeParameterBuilder = new FirFunctionTypeParameterBuilder();
                firFunctionTypeParameterBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null));
                firFunctionTypeParameterBuilder.setName(nameNameAsSafeName$default);
                if (firErrorTypeRefCreateNoTypeForParameterTypeRef == null) {
                    firErrorTypeRefCreateNoTypeForParameterTypeRef = createNoTypeForParameterTypeRef();
                }
                firFunctionTypeParameterBuilder.setReturnTypeRef(firErrorTypeRefCreateNoTypeForParameterTypeRef);
                arrayList2.add(firFunctionTypeParameterBuilder.build());
            }
            i2++;
            i = 0;
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ List convertValueParameters$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, LighterASTNode lighterASTNode, FirFunctionSymbol firFunctionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, List list, int i, Object obj) {
        if ((i & 8) != 0) {
            list = CollectionsKt.emptyList();
        }
        return lightTreeRawFirDeclarationBuilder.convertValueParameters(lighterASTNode, firFunctionSymbol, valueParameterDeclaration, list);
    }

    public final List<ValueParameter> convertValueParameters(LighterASTNode valueParameters, FirFunctionSymbol<?> functionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, List<? extends FirAnnotation> additionalAnnotations) {
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(valueParameterDeclaration, "valueParameterDeclaration");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        LighterASTNode[] childrenAsArray = getChildrenAsArray(valueParameters);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT) && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.VALUE_PARAMETER)) {
                arrayList.add(convertValueParameter(lighterASTNode, functionSymbol, valueParameterDeclaration, additionalAnnotations));
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ValueParameter convertValueParameter$default(LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, LighterASTNode lighterASTNode, FirFunctionSymbol firFunctionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, List list, int i, Object obj) {
        if ((i & 8) != 0) {
            list = CollectionsKt.emptyList();
        }
        return lightTreeRawFirDeclarationBuilder.convertValueParameter(lighterASTNode, firFunctionSymbol, valueParameterDeclaration, list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v10, types: [T, java.lang.String] */
    public final ValueParameter convertValueParameter(LighterASTNode valueParameter, FirFunctionSymbol<?> functionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, List<? extends FirAnnotation> additionalAnnotations) {
        FirTypeRef firTypeRefCreateNoTypeForParameterTypeRef;
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(valueParameterDeclaration, "valueParameterDeclaration");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        Modifier modifier = new Modifier(0L, 1, null);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        KtToken[] ktTokenArr = new KtToken[0];
        LighterASTNode[] childrenAsArray = getChildrenAsArray(valueParameter);
        int length = childrenAsArray.length;
        Modifier modifierConvertModifierList$default = modifier;
        FirTypeRef firTypeRefConvertType = null;
        DestructuringDeclaration destructuringDeclarationConvertDestructingDeclaration$light_tree2fir = null;
        Object obj = null;
        boolean z = false;
        boolean z2 = false;
        int i = 0;
        while (i < length) {
            LighterASTNode lighterASTNode = childrenAsArray[i];
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (KtTokens.COMMENTS.contains(tokenType) || Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) || Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) || ArraysKt.contains(ktTokenArr, tokenType) || Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                length = length;
            } else {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.MODIFIER_LIST)) {
                    modifierConvertModifierList$default = convertModifierList$default(this, lighterASTNode, false, 2, null);
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.VAL_KEYWORD)) {
                    childrenAsArray = childrenAsArray;
                    length = length;
                    z = true;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.VAR_KEYWORD)) {
                    childrenAsArray = childrenAsArray;
                    length = length;
                    z2 = true;
                } else if (Intrinsics.areEqual(tokenType2, KtTokens.IDENTIFIER)) {
                    objectRef.element = getAsText(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.TYPE_REFERENCE)) {
                    firTypeRefConvertType = convertType(lighterASTNode);
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.DESTRUCTURING_DECLARATION)) {
                    destructuringDeclarationConvertDestructingDeclaration$light_tree2fir = convertDestructingDeclaration$light_tree2fir(lighterASTNode);
                } else if (ElementTypeUtils.INSTANCE.isExpression(lighterASTNode)) {
                    LightTreeRawFirExpressionBuilder lightTreeRawFirExpressionBuilder = this.expressionConverter;
                    childrenAsArray = childrenAsArray;
                    FirElement firElementConvertExpression = lightTreeRawFirExpressionBuilder.convertExpression(lighterASTNode, "Should have default value");
                    FirErrorExpression firErrorExpression = (FirExpression) (!(firElementConvertExpression instanceof FirExpression) ? null : firElementConvertExpression);
                    if (firErrorExpression == null) {
                        FirErrorExpression firErrorExpressionBuildErrorExpression = FirExpressionUtilKt.buildErrorExpression((KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(lightTreeRawFirExpressionBuilder, lighterASTNode, null, 1, null), new ConeSimpleDiagnostic("Should have default value", DiagnosticKind.ExpressionExpected), firElementConvertExpression);
                        if (firErrorExpressionBuildErrorExpression == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        }
                        firErrorExpression = firErrorExpressionBuildErrorExpression;
                    }
                    obj = firErrorExpression;
                }
                length = length;
            }
            i++;
            childrenAsArray = childrenAsArray;
            ktTokenArr = ktTokenArr;
            length = length;
        }
        Name nameConvertValueParameterName = convertValueParameterName(ConverterUtilKt.nameAsSafeName$default((String) objectRef.element, null, 1, null), valueParameterDeclaration, new Function0<String>() { // from class: org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirDeclarationBuilder$convertValueParameter$name$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return objectRef.element;
            }
        });
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(this, valueParameter, null, 1, null);
        if (firTypeRefConvertType == null) {
            firTypeRefCreateNoTypeForParameterTypeRef = valueParameterDeclaration.getShouldExplicitParameterTypeBePresent() ? createNoTypeForParameterTypeRef() : getImplicitType();
        } else {
            firTypeRefCreateNoTypeForParameterTypeRef = firTypeRefConvertType;
        }
        return new ValueParameter(z, z2, modifierConvertModifierList$default, firTypeRefCreateNoTypeForParameterTypeRef, ktLightSourceElement, getBaseModuleData(), valueParameterDeclaration == AbstractRawFirBuilder.ValueParameterDeclaration.PRIMARY_CONSTRUCTOR, additionalAnnotations, nameConvertValueParameterName, (FirExpression) obj, functionSymbol, destructuringDeclarationConvertDestructingDeclaration$light_tree2fir);
    }

    private final <T extends FirDeclaration & FirTypeParameterRefsOwner> void fillDanglingConstraintsTo(List<? extends FirTypeParameter> typeParameters, List<TypeConstraint> typeConstraints, T to) {
        List<? extends FirTypeParameter> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameter) it.next()).getName());
        }
        Set set = CollectionsKt.toSet(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (TypeConstraint typeConstraint : typeConstraints) {
            Name nameNameAsSafeName$default = ConverterUtilKt.nameAsSafeName$default(typeConstraint.getIdentifier(), null, 1, null);
            DanglingTypeConstraint danglingTypeConstraint = set.contains(nameNameAsSafeName$default) ? null : new DanglingTypeConstraint(nameNameAsSafeName$default, typeConstraint.getSource());
            if (danglingTypeConstraint != null) {
                arrayList2.add(danglingTypeConstraint);
            }
        }
        ArrayList arrayList3 = arrayList2;
        if (arrayList3.isEmpty()) {
            return;
        }
        DeclarationAttributesKt.setDanglingTypeConstraints(to, arrayList3);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x007c  */
    /* JADX WARN: Code duplicated, block: B:6:0x001a  */
    private final List<FirContextReceiver> convertContextReceivers(LighterASTNode container) {
        List<LighterASTNode> listEmptyList;
        Name referencedNameAsName;
        IElementType CONTEXT_RECEIVER_LIST = KtNodeTypes.CONTEXT_RECEIVER_LIST;
        Intrinsics.checkNotNullExpressionValue(CONTEXT_RECEIVER_LIST, "CONTEXT_RECEIVER_LIST");
        LighterASTNode childNodeByType = getChildNodeByType(container, CONTEXT_RECEIVER_LIST);
        if (childNodeByType != null) {
            IElementType CONTEXT_RECEIVER = KtNodeTypes.CONTEXT_RECEIVER;
            Intrinsics.checkNotNullExpressionValue(CONTEXT_RECEIVER, "CONTEXT_RECEIVER");
            listEmptyList = getChildNodesByType(childNodeByType, CONTEXT_RECEIVER);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        List<LighterASTNode> list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (LighterASTNode lighterASTNode : list) {
            FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
            Name referencedNameAsName2 = null;
            firContextReceiverBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, lighterASTNode, null, 1, null));
            IElementType LABEL_QUALIFIER = KtNodeTypes.LABEL_QUALIFIER;
            Intrinsics.checkNotNullExpressionValue(LABEL_QUALIFIER, "LABEL_QUALIFIER");
            LighterASTNode childNodeByType2 = getChildNodeByType(lighterASTNode, LABEL_QUALIFIER);
            if (childNodeByType2 != null) {
                IElementType LABEL = KtNodeTypes.LABEL;
                Intrinsics.checkNotNullExpressionValue(LABEL, "LABEL");
                LighterASTNode childNodeByType3 = getChildNodeByType(childNodeByType2, LABEL);
                if (childNodeByType3 != null) {
                    KtToken IDENTIFIER = KtTokens.IDENTIFIER;
                    Intrinsics.checkNotNullExpressionValue(IDENTIFIER, "IDENTIFIER");
                    LighterASTNode childNodeByType4 = getChildNodeByType(childNodeByType3, (IElementType) IDENTIFIER);
                    if (childNodeByType4 != null) {
                        referencedNameAsName = getReferencedNameAsName(childNodeByType4);
                    } else {
                        referencedNameAsName = null;
                    }
                } else {
                    referencedNameAsName = null;
                }
            } else {
                referencedNameAsName = null;
            }
            firContextReceiverBuilder.setCustomLabelName(referencedNameAsName);
            IElementType TYPE_REFERENCE = KtNodeTypes.TYPE_REFERENCE;
            Intrinsics.checkNotNullExpressionValue(TYPE_REFERENCE, "TYPE_REFERENCE");
            LighterASTNode childNodeByType5 = getChildNodeByType(lighterASTNode, TYPE_REFERENCE);
            if (childNodeByType5 != null) {
                IElementType USER_TYPE = KtNodeTypes.USER_TYPE;
                Intrinsics.checkNotNullExpressionValue(USER_TYPE, "USER_TYPE");
                LighterASTNode childNodeByType6 = getChildNodeByType(childNodeByType5, USER_TYPE);
                if (childNodeByType6 != null) {
                    IElementType REFERENCE_EXPRESSION = KtNodeTypes.REFERENCE_EXPRESSION;
                    Intrinsics.checkNotNullExpressionValue(REFERENCE_EXPRESSION, "REFERENCE_EXPRESSION");
                    LighterASTNode childNodeByType7 = getChildNodeByType(childNodeByType6, REFERENCE_EXPRESSION);
                    if (childNodeByType7 != null) {
                        referencedNameAsName2 = getReferencedNameAsName(childNodeByType7);
                    }
                }
            }
            firContextReceiverBuilder.setLabelNameFromTypeRef(referencedNameAsName2);
            if (childNodeByType5 != null) {
                firContextReceiverBuilder.setTypeRef(convertType(childNodeByType5));
            }
            arrayList.add(firContextReceiverBuilder.build());
        }
        return arrayList;
    }

    private final FirFileAnnotationsContainer convertFileAnnotationsContainer(LighterASTNode fileAnnotationList, FirFileSymbol fileSymbol) {
        FirFileAnnotationsContainerBuilder firFileAnnotationsContainerBuilder = new FirFileAnnotationsContainerBuilder();
        firFileAnnotationsContainerBuilder.setModuleData(getBaseModuleData());
        firFileAnnotationsContainerBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, fileAnnotationList, null, 1, null));
        firFileAnnotationsContainerBuilder.setContainingFileSymbol(fileSymbol);
        List<FirAnnotation> annotations = firFileAnnotationsContainerBuilder.getAnnotations();
        LighterASTNode[] childrenAsArray = getChildrenAsArray(fileAnnotationList);
        ArrayList arrayList = new ArrayList();
        for (LighterASTNode lighterASTNode : childrenAsArray) {
            if (lighterASTNode == null) {
                break;
            }
            IElementType tokenType = lighterASTNode.getTokenType();
            if (!KtTokens.COMMENTS.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtTokens.WHITE_SPACE) && !Intrinsics.areEqual(tokenType, KtTokens.SEMICOLON) && !Intrinsics.areEqual(tokenType, TokenType.ERROR_ELEMENT)) {
                IElementType tokenType2 = lighterASTNode.getTokenType();
                if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION)) {
                    CollectionsKt.addAll(arrayList, convertAnnotation(lighterASTNode));
                } else if (Intrinsics.areEqual(tokenType2, KtNodeTypes.ANNOTATION_ENTRY)) {
                    arrayList.add(convertAnnotationEntry$default(this, lighterASTNode, null, null, 6, null));
                }
            }
        }
        CollectionsKt.addAll(annotations, arrayList);
        if (firFileAnnotationsContainerBuilder.getAnnotations().isEmpty()) {
            firFileAnnotationsContainerBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        }
        return firFileAnnotationsContainerBuilder.mo6809build();
    }

    private final FirDanglingModifierList buildErrorTopLevelDeclarationForDanglingModifierList(LighterASTNode node) {
        FirDanglingModifierListBuilder firDanglingModifierListBuilder = new FirDanglingModifierListBuilder();
        firDanglingModifierListBuilder.setSource(toFirSourceElement(node, (KtFakeSourceElementKind) KtFakeSourceElementKind.DanglingModifierList.INSTANCE));
        firDanglingModifierListBuilder.setModuleData(getBaseModuleData());
        firDanglingModifierListBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firDanglingModifierListBuilder.setDiagnostic(ConeDanglingModifierOnTopLevel.INSTANCE);
        firDanglingModifierListBuilder.setSymbol(new FirDanglingModifierSymbol());
        CollectionsKt.addAll(firDanglingModifierListBuilder.getAnnotations(), convertModifierList$default(this, node, false, 2, null).getAnnotations());
        return firDanglingModifierListBuilder.mo6809build();
    }

    private final FirContractDescription obtainContractDescription(LighterASTNode rawContractDescription) {
        FirRawContractDescriptionBuilder firRawContractDescriptionBuilder = new FirRawContractDescriptionBuilder();
        firRawContractDescriptionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(this, rawContractDescription, null, 1, null));
        extractRawEffects(rawContractDescription, firRawContractDescriptionBuilder.getRawEffects());
        return firRawContractDescriptionBuilder.build();
    }

    /* JADX WARN: Code duplicated, block: B:27:0x008a  */
    private static final FirDelegatedConstructorCall convertPrimaryConstructor$lambda$87$createDelegatedConstructorCall(KtSourceElement ktSourceElement, boolean z, ClassWrapper classWrapper, LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder, KtLightSourceElement ktLightSourceElement, FirTypeRef firTypeRef, List<? extends FirExpression> list) {
        KtLightSourceElement ktLightSourceElementFakeElement$default;
        LighterASTNode lighterASTNode;
        KtLightSourceElement firSourceElement;
        KtSourceElement ktSourceElementFakeElement$default;
        FirDelegatedConstructorCallBuilder firDelegatedConstructorCallBuilder = new FirDelegatedConstructorCallBuilder();
        KtLightSourceElement source = null;
        if (ktLightSourceElement != null) {
            ktLightSourceElementFakeElement$default = ktLightSourceElement;
        } else {
            ktLightSourceElementFakeElement$default = ktSourceElement != null ? KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE, 0, 0, 6, null) : null;
        }
        firDelegatedConstructorCallBuilder.setSource(ktLightSourceElementFakeElement$default);
        firDelegatedConstructorCallBuilder.setConstructedTypeRef(UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE));
        firDelegatedConstructorCallBuilder.setThis(false);
        FirExplicitSuperReferenceBuilder firExplicitSuperReferenceBuilder = new FirExplicitSuperReferenceBuilder();
        if (!z) {
            KtSourceElement source2 = classWrapper.getDelegatedSuperTypeRef().getSource();
            if (source2 == null || (ktSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE, 0, 0, 6, null)) == null) {
                KtSourceElement source3 = firDelegatedConstructorCallBuilder.getSource();
                if (source3 != null) {
                    source = KtSourceElementKt.fakeElement$default(source3, KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE, 0, 0, 6, null);
                }
            } else {
                source = ktSourceElementFakeElement$default;
            }
        } else if (ktLightSourceElement != null && (lighterASTNode = ktLightSourceElement.getLighterASTNode()) != null) {
            IElementType CONSTRUCTOR_CALLEE = KtNodeTypes.CONSTRUCTOR_CALLEE;
            Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR_CALLEE, "CONSTRUCTOR_CALLEE");
            LighterASTNode childNodeByType = lightTreeRawFirDeclarationBuilder.getChildNodeByType(lighterASTNode, CONSTRUCTOR_CALLEE);
            if (childNodeByType != null && (firSourceElement = lightTreeRawFirDeclarationBuilder.toFirSourceElement(childNodeByType, (KtFakeSourceElementKind) KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE)) != null) {
                source = firSourceElement;
            } else {
                source = firDelegatedConstructorCallBuilder.getSource();
            }
        } else {
            source = firDelegatedConstructorCallBuilder.getSource();
        }
        firExplicitSuperReferenceBuilder.setSource(source);
        firExplicitSuperReferenceBuilder.setSuperTypeRef(firDelegatedConstructorCallBuilder.getConstructedTypeRef());
        firDelegatedConstructorCallBuilder.setCalleeReference(firExplicitSuperReferenceBuilder.build());
        ConverterUtilKt.extractArgumentsFrom(firDelegatedConstructorCallBuilder, list);
        return firDelegatedConstructorCallBuilder.mo6809build();
    }
}
