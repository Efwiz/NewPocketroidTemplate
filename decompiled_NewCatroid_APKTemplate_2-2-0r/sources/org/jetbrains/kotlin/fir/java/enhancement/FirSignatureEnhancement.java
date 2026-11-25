package org.jetbrains.kotlin.fir.java.enhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.OperatorFunctionChecks;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPrimaryConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyBuilderKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.java.FirJavaTypeConversionMode;
import org.jetbrains.kotlin.fir.java.JavaTypeConversionKt;
import org.jetbrains.kotlin.fir.java.JavaTypeParameterStack;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaClass;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaConstructor;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaField;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaFieldBuilder;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaMethod;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameter;
import org.jetbrains.kotlin.fir.java.symbols.FirJavaOverriddenSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.types.jvm.FirJavaTypeRef;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.load.java.AnnotationQualifierApplicabilityType;
import org.jetbrains.kotlin.load.java.FakePureImplementationsProvider;
import org.jetbrains.kotlin.load.java.JavaTypeQualifiersByElementType;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts;
import org.jetbrains.kotlin.load.java.typeEnhancement.JavaTypeQualifiers;
import org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifier;
import org.jetbrains.kotlin.load.java.typeEnhancement.PredefinedEnhancementInfoKt;
import org.jetbrains.kotlin.load.java.typeEnhancement.PredefinedFunctionEnhancementInfo;
import org.jetbrains.kotlin.load.java.typeEnhancement.TypeEnhancementInfo;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.PrivateForInline;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: SignatureEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000þ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001lB4\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u001d\u0010\u0006\u001a\u0019\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0007¢\u0006\u0002\b\u000b¢\u0006\u0002\u0010\fJ'\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u001e2\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030\u001e2\b\u0010 \u001a\u0004\u0018\u00010!H\u0001¢\u0006\u0002\b\"J%\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030#2\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010 \u001a\u00020!H\u0001¢\u0006\u0002\b\"J6\u0010$\u001a\u0006\u0012\u0002\b\u00030\u001e2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\b\u0010 \u001a\u0004\u0018\u00010!2\u000e\u0010)\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010\tH\u0002J(\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\u00100\u001a\u0004\u0018\u00010\u000eH\u0002J2\u00101\u001a\u00020,2\u0006\u0010\u0002\u001a\u00020\n2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\u00100\u001a\u0004\u0018\u00010\u000e2\b\u00102\u001a\u0004\u0018\u000103H\u0002J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000205H\u0002J\u001a\u00107\u001a\b\u0012\u0004\u0012\u0002050\t2\f\u00108\u001a\b\u0012\u0004\u0012\u0002050\tJ \u00109\u001a\u00020,2\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u0002052\u0006\u0010=\u001a\u00020>H\u0002J(\u0010?\u001a\u00020@2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020*0\t2\u0012\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002050\t0\tJ\u0016\u0010C\u001a\b\u0012\u0004\u0012\u00020*0\t2\u0006\u0010%\u001a\u00020&H\u0002JJ\u0010D\u001a\u00020,2\u0006\u0010-\u001a\u00020&2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010E\u001a\u00020>2\b\u00100\u001a\u0004\u0018\u00010\u000e2\b\u00102\u001a\u0004\u0018\u0001032\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020IH\u0002J \u0010J\u001a\u0006\u0012\u0002\b\u00030\u001e2\n\u0010K\u001a\u0006\u0012\u0002\b\u00030\u001e2\b\u0010 \u001a\u0004\u0018\u00010!J\u001e\u0010L\u001a\u0006\u0012\u0002\b\u00030#2\n\u0010M\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010 \u001a\u00020!J\u0012\u0010N\u001a\u0004\u0018\u00010O2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J2\u0010P\u001a \u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002050\t0\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0\t0Q2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020*0\tJ*\u0010R\u001a\u00020@2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020*0\t2\u0012\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002050\t0\tH\u0002J\u0010\u0010S\u001a\u00020@2\u0006\u0010K\u001a\u00020&H\u0002J\u000e\u0010T\u001a\u0004\u0018\u00010\u000e*\u00020UH\u0002JX\u0010\u001d\u001a\u00020,*\u00020\n2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\u0010V\u001a\u0004\u0018\u00010W2\u0006\u0010X\u001a\u00020>2\b\u0010Y\u001a\u0004\u0018\u00010\u000e2\u0006\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020]2\b\u0010^\u001a\u0004\u0018\u00010_2\u0006\u0010`\u001a\u00020>H\u0002J6\u0010\u001d\u001a\u00020,*\u00020a2\u0006\u0010b\u001a\u0002052\f\u0010c\u001a\b\u0012\u0004\u0012\u0002050\t2\u0006\u0010d\u001a\u00020e2\n\b\u0002\u0010^\u001a\u0004\u0018\u00010_H\u0002JH\u0010f\u001a\u00020,*\u00020&2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\u0010g\u001a\u0004\u0018\u00010W2\b\u00100\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\\\u001a\u00020]2\b\u0010^\u001a\u0004\u0018\u00010_2\u0006\u0010`\u001a\u00020>H\u0002J-\u0010h\u001a\u00020@*\b\u0012\u0004\u0012\u00020*0\t2\u0018\u0010i\u001a\u0014\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u0002050jH\u0082\bJ\u0014\u0010k\u001a\u00020O*\u0002052\u0006\u0010d\u001a\u00020eH\u0002R\u001d\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR%\u0010\u0006\u001a\u0019\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0007¢\u0006\u0002\b\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006m"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement;", "", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "overridden", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/jvm/functions/Function1;)V", "contextQualifiers", "Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "getContextQualifiers", "()Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "contextQualifiers$delegate", "Lkotlin/Lazy;", "enhancementsCache", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirEnhancedSymbolsStorage$EnhancementSymbolsCache;", "javaTypeParameterStack", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "typeQualifierResolver", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirAnnotationTypeQualifierResolver;", "enhance", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "original", "name", "Lorg/jetbrains/kotlin/name/Name;", "enhance$java", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "enhanceMethod", "firMethod", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "methodId", "Lorg/jetbrains/kotlin/name/CallableId;", "enhancedTypeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "enhanceReceiverType", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "ownerFunction", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaMethod;", "overriddenMembers", "defaultQualifiers", "enhanceReturnType", "predefinedEnhancementInfo", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/PredefinedFunctionEnhancementInfo;", "enhanceSuperType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", ModuleXmlParser.TYPE, "enhanceSuperTypes", "unenhnancedSuperTypes", "enhanceTypeParameterBound", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "bound", "forceOnlyHeadTypeConstructor", "", "enhanceTypeParameterBoundsAfterFirstRound", "", "typeParameters", "initialBounds", "enhanceTypeParameterBoundsForMethod", "enhanceValueParameterType", "hasReceiver", "ownerParameter", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaValueParameter;", "index", "", "enhancedFunction", "function", "enhancedProperty", "property", "getPurelyImplementedSupertype", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "performFirstRoundOfBoundsResolution", "Lkotlin/Pair;", "performSecondRoundOfBoundsResolution", "updateIsOperatorFlagIfNeeded", "computeDefaultQualifiers", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "typeContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "isCovariant", "containerQualifiers", "containerApplicabilityType", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "typeInSignature", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature;", "predefined", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/TypeEnhancementInfo;", "forAnnotationMember", "Lorg/jetbrains/kotlin/fir/java/enhancement/EnhancementSignatureParts;", "typeRef", "typeRefsFromOverridden", "mode", "Lorg/jetbrains/kotlin/fir/java/FirJavaTypeConversionMode;", "enhanceValueParameter", "parameterContainer", "replaceBounds", "block", "Lkotlin/Function2;", "toConeKotlinType", "TypeInSignature", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSignatureEnhancement {

    /* JADX INFO: renamed from: contextQualifiers$delegate, reason: from kotlin metadata */
    private final Lazy contextQualifiers;
    private final FirEnhancedSymbolsStorage.EnhancementSymbolsCache enhancementsCache;
    private final JavaTypeParameterStack javaTypeParameterStack;
    private final Function1<FirSimpleFunction, List<FirCallableDeclaration>> overridden;
    private final FirRegularClass owner;
    private final FirSession session;
    private final FirAnnotationTypeQualifierResolver typeQualifierResolver;

    /* JADX WARN: Multi-variable type inference failed */
    public FirSignatureEnhancement(FirRegularClass owner, FirSession session, Function1<? super FirSimpleFunction, ? extends List<? extends FirCallableDeclaration>> overridden) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        this.owner = owner;
        this.session = session;
        this.overridden = overridden;
        this.javaTypeParameterStack = owner instanceof FirJavaClass ? ((FirJavaClass) owner).getJavaTypeParameterStack() : JavaTypeParameterStack.INSTANCE.getEMPTY();
        this.typeQualifierResolver = FirAnnotationTypeQualifierResolverKt.getJavaAnnotationTypeQualifierResolver(session);
        this.contextQualifiers = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JavaTypeQualifiersByElementType>() { // from class: org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement$contextQualifiers$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JavaTypeQualifiersByElementType invoke() {
                return this.this$0.typeQualifierResolver.extractDefaultQualifiers(this.this$0.owner);
            }
        });
        this.enhancementsCache = (FirEnhancedSymbolsStorage.EnhancementSymbolsCache) SignatureEnhancementKt.getEnhancedSymbolStorage(session).getCacheByOwner().getValue(owner.getSymbol(), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirModuleData getModuleData() {
        return this.owner.getModuleData();
    }

    private final JavaTypeQualifiersByElementType getContextQualifiers() {
        return (JavaTypeQualifiersByElementType) this.contextQualifiers.getValue();
    }

    public final FirFunctionSymbol<?> enhancedFunction(FirFunctionSymbol<?> function, Name name2) {
        Intrinsics.checkNotNullParameter(function, "function");
        return this.enhancementsCache.getEnhancedFunctions().getValue(function, TuplesKt.to(this, name2));
    }

    public final FirVariableSymbol<?> enhancedProperty(FirVariableSymbol<?> property, Name name2) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.enhancementsCache.getEnhancedVariables().getValue(property, TuplesKt.to(this, name2));
    }

    private final JavaTypeQualifiersByElementType computeDefaultQualifiers(FirDeclaration firDeclaration) {
        return this.typeQualifierResolver.extractAndMergeDefaultQualifiers(getContextQualifiers(), firDeclaration.getAnnotations());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @PrivateForInline
    public final FirVariableSymbol<?> enhance$java(FirVariableSymbol<?> original, final Name name2) {
        FirNamedFunctionSymbol symbol;
        FirFunctionSymbol<?> symbol2;
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(name2, "name");
        final FirVariable firVariable = (FirVariable) original.getFir();
        if (firVariable instanceof FirEnumEntry) {
            if (!(firVariable.getReturnTypeRef() instanceof FirJavaTypeRef)) {
                return original;
            }
            FirResolvedTypeRef firResolvedTypeRefEnhanceReturnType = enhanceReturnType(firVariable, CollectionsKt.emptyList(), computeDefaultQualifiers(firVariable), new PredefinedFunctionEnhancementInfo(new TypeEnhancementInfo((Pair<Integer, JavaTypeQualifiers>[]) new Pair[]{TuplesKt.to(0, new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, null, false, false, 8, null))}), CollectionsKt.emptyList()));
            FirEnumEntrySymbol symbol3 = ((FirEnumEntry) firVariable).getSymbol();
            FirResolvedTypeRef firResolvedTypeRef = firResolvedTypeRefEnhanceReturnType;
            ((FirEnumEntry) symbol3.getFir()).replaceReturnTypeRef(firResolvedTypeRef);
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(this.session);
            if (lookupTracker != null) {
                FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRef, ((FirEnumEntry) symbol3.getFir()).getSource(), null);
            }
            return symbol3;
        }
        if (firVariable instanceof FirField) {
            if (!(firVariable.getReturnTypeRef() instanceof FirJavaTypeRef)) {
                return original;
            }
            FirResolvedTypeRef firResolvedTypeRefEnhanceReturnType2 = enhanceReturnType(firVariable, CollectionsKt.emptyList(), computeDefaultQualifiers(firVariable), null);
            ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(firResolvedTypeRefEnhanceReturnType2.getType());
            if ((ConeBuiltinTypeUtilsKt.isString(coneSimpleKotlinTypeLowerBoundIfFlexible) || ConeBuiltinTypeUtilsKt.isInt(coneSimpleKotlinTypeLowerBoundIfFlexible)) && firVariable.getStatus().isStatic() && firVariable.getInitializer() != null) {
                firResolvedTypeRefEnhanceReturnType2 = TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRefEnhanceReturnType2, TypeUtilsKt.withNullability$default(firResolvedTypeRefEnhanceReturnType2.getType(), ConeNullability.NOT_NULL, TypeComponentsKt.getTypeContext(this.session), null, false, 12, null), null, 2, null);
            }
            FirFieldSymbol firFieldSymbol = new FirFieldSymbol(original.getCallableId());
            FirJavaFieldBuilder firJavaFieldBuilder = new FirJavaFieldBuilder();
            firJavaFieldBuilder.setSource(firVariable.getSource());
            firJavaFieldBuilder.setModuleData(getModuleData());
            firJavaFieldBuilder.setSymbol(firFieldSymbol);
            firJavaFieldBuilder.setName(name2);
            firJavaFieldBuilder.setReturnTypeRef(firResolvedTypeRefEnhanceReturnType2);
            firJavaFieldBuilder.setFromSource(original.getOrigin().getFromSource());
            FirVariable firVariable2 = firVariable;
            firJavaFieldBuilder.setVisibility(firVariable2.getStatus().getVisibility());
            firJavaFieldBuilder.setModality(firVariable2.getStatus().getModality());
            firJavaFieldBuilder.setVar(firVariable.getIsVar());
            firJavaFieldBuilder.setStatic(firVariable2.getStatus().isStatic());
            firJavaFieldBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement$enhance$2$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends FirAnnotation> invoke() {
                    return firVariable.getAnnotations();
                }
            });
            firJavaFieldBuilder.setStatus(firVariable.getStatus());
            if (firVariable instanceof FirJavaField) {
                firJavaFieldBuilder.setLazyInitializer(((FirJavaField) firVariable).getLazyInitializer$java());
            } else {
                firJavaFieldBuilder.setInitializer(firVariable.getInitializer());
            }
            firJavaFieldBuilder.setDispatchReceiverType(firVariable.getDispatchReceiverType());
            firJavaFieldBuilder.setAttributes(firVariable.getAttributes().copy());
            firJavaFieldBuilder.mo6809build();
            return firFieldSymbol;
        }
        if (firVariable instanceof FirSyntheticProperty) {
            FirSyntheticProperty firSyntheticProperty = (FirSyntheticProperty) firVariable;
            final FirSyntheticPropertySymbol symbol4 = firSyntheticProperty.getSymbol();
            FirSimpleFunction delegate = firSyntheticProperty.getGetter().getDelegate();
            boolean z = delegate instanceof FirJavaMethod;
            if (z) {
                symbol = enhanceMethod(delegate, delegate.getSymbol().getCallableId(), delegate.getName(), null);
            } else {
                symbol = delegate.getSymbol();
            }
            FirSyntheticPropertyAccessor setter = firSyntheticProperty.getSetter();
            FirSimpleFunction delegate2 = setter != null ? setter.getDelegate() : null;
            boolean z2 = delegate2 instanceof FirJavaMethod;
            if (z2) {
                FirSimpleFunction firSimpleFunction = delegate2;
                FirJavaMethod firJavaMethod = (FirJavaMethod) delegate2;
                symbol2 = enhanceMethod(firSimpleFunction, firJavaMethod.getSymbol().getCallableId(), firJavaMethod.getName(), null);
            } else {
                symbol2 = delegate2 != null ? delegate2.getSymbol() : null;
            }
            if (!z && !z2) {
                return original;
            }
            final FirFunctionSymbol<?> firFunctionSymbol = symbol;
            final FirFunctionSymbol<?> firFunctionSymbol2 = symbol2;
            return FirSyntheticPropertyBuilderKt.buildSyntheticProperty(new Function1<FirSyntheticPropertyBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement.enhance.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSyntheticPropertyBuilder firSyntheticPropertyBuilder) {
                    invoke2(firSyntheticPropertyBuilder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSyntheticPropertyBuilder buildSyntheticProperty) {
                    Intrinsics.checkNotNullParameter(buildSyntheticProperty, "$this$buildSyntheticProperty");
                    buildSyntheticProperty.setModuleData(FirSignatureEnhancement.this.getModuleData());
                    buildSyntheticProperty.setName(name2);
                    buildSyntheticProperty.setSymbol(new FirJavaOverriddenSyntheticPropertySymbol(symbol4.getCallableId(), symbol4.getGetterId()));
                    FirFunction fir = firFunctionSymbol.getFir();
                    Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirSimpleFunction");
                    buildSyntheticProperty.setDelegateGetter((FirSimpleFunction) fir);
                    FirFunctionSymbol<? extends FirFunction> firFunctionSymbol3 = firFunctionSymbol2;
                    buildSyntheticProperty.setDelegateSetter((FirSimpleFunction) (firFunctionSymbol3 != null ? firFunctionSymbol3.getFir() : null));
                    buildSyntheticProperty.setStatus(firVariable.getStatus());
                    buildSyntheticProperty.setDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProviderFromAccessors(FirSignatureEnhancement.this.session, buildSyntheticProperty.getDelegateGetter(), buildSyntheticProperty.getDelegateSetter()));
                }
            }).getSymbol();
        }
        if (original instanceof FirPropertySymbol) {
            return original;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Can't make enhancement for " + original.getClass(), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "firElement", firVariable);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    @PrivateForInline
    public final FirFunctionSymbol<?> enhance$java(FirFunctionSymbol<?> original, Name name2) {
        Intrinsics.checkNotNullParameter(original, "original");
        FirFunction firFunction = (FirFunction) original.getFir();
        if (!(firFunction instanceof FirJavaMethod) && !(firFunction instanceof FirJavaConstructor)) {
            return original;
        }
        return enhanceMethod(firFunction, original.getCallableId(), name2, enhanceTypeParameterBoundsForMethod(firFunction));
    }

    private final FirFunctionSymbol<?> enhanceMethod(FirFunction firMethod, CallableId methodId, Name name2, List<? extends FirTypeParameterRef> enhancedTypeParameters) {
        boolean z;
        FirResolvedTypeRef returnTypeRef;
        boolean zAreEqual;
        ConeSubstitutorByMap coneSubstitutorByMap;
        FirResolvedTypeRef firResolvedTypeRefWithReplacedConeType$default;
        FirReceiverParameter firReceiverParameterMo6810build;
        FirSimpleFunctionBuilder firSimpleFunctionBuilder;
        FirFunctionSymbol<?> firFunctionSymbol;
        FirConstructorBuilder firConstructorBuilder;
        FirResolvedDeclarationStatusImpl firDeclarationStatusImpl;
        PredefinedFunctionEnhancementInfo predefinedFunctionEnhancementInfo = PredefinedEnhancementInfoKt.getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE().get(SignatureBuildingComponents.INSTANCE.signature(this.owner.getSymbol().getClassId(), SignatureUtilsKt.computeJvmDescriptor$default(firMethod, null, false, new Function1<FirTypeRef, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement$enhanceMethod$predefinedEnhancementInfo$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(FirTypeRef it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(it, this.this$0.session, this.this$0.javaTypeParameterStack, null, 4, null);
            }
        }, 3, null)));
        if (predefinedFunctionEnhancementInfo != null) {
            predefinedFunctionEnhancementInfo.getParametersInfo().size();
            firMethod.getValueParameters().size();
            Unit unit = Unit.INSTANCE;
            Unit unit2 = Unit.INSTANCE;
        }
        JavaTypeQualifiersByElementType javaTypeQualifiersByElementTypeComputeDefaultQualifiers = computeDefaultQualifiers(firMethod);
        FirSimpleFunction firSimpleFunction = firMethod instanceof FirSimpleFunction ? (FirSimpleFunction) firMethod : null;
        List<FirCallableDeclaration> listInvoke = firSimpleFunction != null ? this.overridden.invoke(firSimpleFunction) : null;
        if (listInvoke == null) {
            listInvoke = CollectionsKt.emptyList();
        }
        List<FirCallableDeclaration> list = listInvoke;
        List<FirCallableDeclaration> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (((FirCallableDeclaration) it.next()).getReceiverParameter() != null) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        boolean z2 = firMethod instanceof FirJavaMethod;
        FirResolvedTypeRef firResolvedTypeRefEnhanceReceiverType = (z2 && z) ? enhanceReceiverType((FirJavaMethod) firMethod, list, javaTypeQualifiersByElementTypeComputeDefaultQualifiers) : null;
        if (z2) {
            returnTypeRef = enhanceReturnType(firMethod, list, javaTypeQualifiersByElementTypeComputeDefaultQualifiers, predefinedFunctionEnhancementInfo);
        } else {
            returnTypeRef = firMethod.getReturnTypeRef();
        }
        FirTypeRef firTypeRef = returnTypeRef;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (FirValueParameter firValueParameter : firMethod.getValueParameters()) {
            int i2 = i + 1;
            if (!z || i != 0) {
                ArrayList arrayList2 = arrayList;
                Intrinsics.checkNotNull(firValueParameter, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameter");
                FirJavaValueParameter firJavaValueParameter = (FirJavaValueParameter) firValueParameter;
                if (z) {
                    i--;
                }
                arrayList2.add(enhanceValueParameterType(firMethod, list, z, javaTypeQualifiersByElementTypeComputeDefaultQualifiers, predefinedFunctionEnhancementInfo, firJavaValueParameter, i));
            }
            firTypeRef = firTypeRef;
            i = i2;
            z2 = z2;
        }
        FirTypeRef firTypeRef2 = firTypeRef;
        boolean z3 = z2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (firMethod instanceof FirJavaConstructor) {
            FirConstructorSymbol firConstructorSymbol = new FirConstructorSymbol(methodId);
            FirJavaConstructor firJavaConstructor = (FirJavaConstructor) firMethod;
            if (firJavaConstructor.getIsPrimary()) {
                FirPrimaryConstructorBuilder firPrimaryConstructorBuilder = new FirPrimaryConstructorBuilder();
                firPrimaryConstructorBuilder.setReturnTypeRef(firTypeRef2);
                FirDeclarationStatus status = firJavaConstructor.getStatus();
                FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
                if (firResolvedDeclarationStatus != null) {
                    firDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(firResolvedDeclarationStatus.getVisibility(), Modality.FINAL, firResolvedDeclarationStatus.getEffectiveVisibility());
                } else {
                    firDeclarationStatusImpl = new FirDeclarationStatusImpl(firMethod.getStatus().getVisibility(), Modality.FINAL);
                }
                FirFunction firFunction = firMethod;
                firDeclarationStatusImpl.setInner(firFunction.getStatus().isInner());
                firDeclarationStatusImpl.setHasStableParameterNames(firFunction.getStatus().getHasStableParameterNames());
                firPrimaryConstructorBuilder.setStatus(firDeclarationStatusImpl);
                firPrimaryConstructorBuilder.setSymbol(firConstructorSymbol);
                firPrimaryConstructorBuilder.setDispatchReceiverType(firMethod.getDispatchReceiverType());
                firPrimaryConstructorBuilder.setAttributes(firMethod.getAttributes().copy());
                firConstructorBuilder = firPrimaryConstructorBuilder;
            } else {
                FirConstructorBuilder firConstructorBuilder2 = new FirConstructorBuilder();
                firConstructorBuilder2.setReturnTypeRef(firTypeRef2);
                firConstructorBuilder2.setStatus(firJavaConstructor.getStatus());
                firConstructorBuilder2.setSymbol(firConstructorSymbol);
                firConstructorBuilder2.setDispatchReceiverType(firMethod.getDispatchReceiverType());
                firConstructorBuilder2.setAttributes(firMethod.getAttributes().copy());
                firConstructorBuilder = firConstructorBuilder2;
            }
            firConstructorBuilder.setSource(firMethod.getSource());
            firConstructorBuilder.setModuleData(getModuleData());
            firConstructorBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            firConstructorBuilder.setOrigin(FirDeclarationOrigin.Enhancement.INSTANCE);
            CollectionsKt.addAll(firConstructorBuilder.getTypeParameters(), enhancedTypeParameters == null ? firJavaConstructor.getTypeParameters() : enhancedTypeParameters);
            firSimpleFunctionBuilder = firConstructorBuilder;
            coneSubstitutorByMap = null;
            zAreEqual = false;
            firFunctionSymbol = firConstructorSymbol;
        } else if (z3) {
            zAreEqual = Intrinsics.areEqual((Object) ClassMembersKt.isJavaRecordComponent(firMethod), (Object) true);
            FirSimpleFunctionBuilder firSimpleFunctionBuilder2 = new FirSimpleFunctionBuilder();
            firSimpleFunctionBuilder2.setSource(firMethod.getSource());
            firSimpleFunctionBuilder2.setModuleData(getModuleData());
            firSimpleFunctionBuilder2.setOrigin(FirDeclarationOrigin.Enhancement.INSTANCE);
            Intrinsics.checkNotNull(name2);
            firSimpleFunctionBuilder2.setName(name2);
            FirJavaMethod firJavaMethod = (FirJavaMethod) firMethod;
            firSimpleFunctionBuilder2.setStatus(firJavaMethod.getStatus());
            FirNamedFunctionSymbol firNamedFunctionSymbol = new FirNamedFunctionSymbol(methodId);
            firSimpleFunctionBuilder2.setSymbol(firNamedFunctionSymbol);
            firSimpleFunctionBuilder2.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            List<FirTypeParameter> typeParameters = firSimpleFunctionBuilder2.getTypeParameters();
            List<? extends FirTypeParameterRef> typeParameters2 = enhancedTypeParameters == null ? firJavaMethod.getTypeParameters() : enhancedTypeParameters;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
            for (FirTypeParameterRef firTypeParameterRef : typeParameters2) {
                if (!(firTypeParameterRef instanceof FirTypeParameter)) {
                    throw new IllegalArgumentException(("Unexpected type parameter type: " + Reflection.getOrCreateKotlinClass(firTypeParameterRef.getClass()).getSimpleName()).toString());
                }
                FirTypeParameter firTypeParameter = (FirTypeParameter) firTypeParameterRef;
                FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
                firTypeParameterBuilder.setSource(firTypeParameter.getSource());
                firTypeParameterBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firTypeParameter));
                firTypeParameterBuilder.setModuleData(firTypeParameter.getModuleData());
                firTypeParameterBuilder.setOrigin(firTypeParameter.getOrigin());
                firTypeParameterBuilder.setAttributes(firTypeParameter.getAttributes().copy());
                firTypeParameterBuilder.setName(firTypeParameter.getName());
                firTypeParameterBuilder.setSymbol(firTypeParameter.getSymbol());
                firTypeParameterBuilder.setContainingDeclarationSymbol(firTypeParameter.getContainingDeclarationSymbol());
                firTypeParameterBuilder.setVariance(firTypeParameter.getVariance());
                firTypeParameterBuilder.setReified(firTypeParameter.getIsReified());
                firTypeParameterBuilder.getBounds().addAll(firTypeParameter.getBounds());
                firTypeParameterBuilder.getAnnotations().addAll(firTypeParameter.getAnnotations());
                firTypeParameterBuilder.setOrigin(FirDeclarationOrigin.Enhancement.INSTANCE);
                firTypeParameterBuilder.setSymbol(new FirTypeParameterSymbol());
                firTypeParameterBuilder.setContainingDeclarationSymbol(firNamedFunctionSymbol);
                FirTypeParameter firTypeParameterMo6810build = firTypeParameterBuilder.mo6809build();
                linkedHashMap.put(firTypeParameterRef.getSymbol(), new ConeTypeParameterTypeImpl(firTypeParameterMo6810build.getSymbol().getLookupTag(), false, null, 4, null));
                arrayList3.add(firTypeParameterMo6810build);
            }
            CollectionsKt.addAll(typeParameters, arrayList3);
            coneSubstitutorByMap = !linkedHashMap.isEmpty() ? new ConeSubstitutorByMap(linkedHashMap, this.session) : null;
            firSimpleFunctionBuilder2.setReturnTypeRef(TypeUtilsKt.withReplacedConeType$default(firTypeRef2, coneSubstitutorByMap != null ? coneSubstitutorByMap.substituteOrNull(FirTypeUtilsKt.getConeType(firTypeRef2)) : null, null, 2, null));
            if (firResolvedTypeRefEnhanceReceiverType != null) {
                firResolvedTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRefEnhanceReceiverType, coneSubstitutorByMap != null ? coneSubstitutorByMap.substituteOrNull(FirTypeUtilsKt.getConeType(firTypeRef2)) : null, null, 2, null);
            } else {
                firResolvedTypeRefWithReplacedConeType$default = null;
            }
            if (firResolvedTypeRefWithReplacedConeType$default != null) {
                FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
                firReceiverParameterBuilder.setTypeRef(firResolvedTypeRefWithReplacedConeType$default);
                CollectionsKt.addAll(firReceiverParameterBuilder.getAnnotations(), ((FirValueParameter) CollectionsKt.first((List) firJavaMethod.getValueParameters())).getAnnotations());
                KtSourceElement source = firResolvedTypeRefWithReplacedConeType$default.getSource();
                firReceiverParameterBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ReceiverFromType.INSTANCE, 0, 0, 6, null) : null);
                firReceiverParameterMo6810build = firReceiverParameterBuilder.mo6809build();
            } else {
                firReceiverParameterMo6810build = null;
            }
            firSimpleFunctionBuilder2.setReceiverParameter(firReceiverParameterMo6810build);
            for (FirTypeParameter firTypeParameter2 : firSimpleFunctionBuilder2.getTypeParameters()) {
                List<FirTypeRef> bounds = firTypeParameter2.getBounds();
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds, 10));
                for (FirTypeRef firTypeRef3 : bounds) {
                    arrayList4.add(TypeUtilsKt.withReplacedConeType$default(firTypeRef3, coneSubstitutorByMap != null ? coneSubstitutorByMap.substituteOrNull(FirTypeUtilsKt.getConeType(firTypeRef3)) : null, null, 2, null));
                }
                firTypeParameter2.replaceBounds(arrayList4);
            }
            firSimpleFunctionBuilder2.setDispatchReceiverType(firMethod.getDispatchReceiverType());
            firSimpleFunctionBuilder2.setAttributes(firMethod.getAttributes().copy());
            firSimpleFunctionBuilder = firSimpleFunctionBuilder2;
            firFunctionSymbol = firNamedFunctionSymbol;
        } else {
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unknown Java method to enhance: " + firMethod.getClass(), null);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "firMethod", firMethod);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
        List<FirValueParameter> valueParameters = firMethod.getValueParameters();
        Iterator<T> it2 = valueParameters.iterator();
        ArrayList arrayList5 = arrayList;
        Iterator it3 = arrayList5.iterator();
        ArrayList arrayList6 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(valueParameters, 10), CollectionsKt.collectionSizeOrDefault(arrayList5, 10)));
        while (it2.hasNext() && it3.hasNext()) {
            Object next = it2.next();
            FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) it3.next();
            FirValueParameter firValueParameter2 = (FirValueParameter) next;
            FirExpression defaultValue = firValueParameter2.getDefaultValue();
            if (defaultValue != null) {
                defaultValue.replaceTypeRef(firResolvedTypeRef);
                Unit unit3 = Unit.INSTANCE;
            }
            FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
            firValueParameterBuilder.setSource(firValueParameter2.getSource());
            firValueParameterBuilder.setContainingFunctionSymbol(firFunctionSymbol);
            firValueParameterBuilder.setModuleData(getModuleData());
            firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Enhancement.INSTANCE);
            FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRef;
            firValueParameterBuilder.setReturnTypeRef(TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRef2, coneSubstitutorByMap != null ? coneSubstitutorByMap.substituteOrNull(FirTypeUtilsKt.getConeType(firResolvedTypeRef2)) : null, null, 2, null));
            firValueParameterBuilder.setName(firValueParameter2.getName());
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameterBuilder.getName()));
            firValueParameterBuilder.setDefaultValue(firValueParameter2.getDefaultValue());
            firValueParameterBuilder.setCrossinline(firValueParameter2.getIsCrossinline());
            firValueParameterBuilder.setNoinline(firValueParameter2.getIsNoinline());
            firValueParameterBuilder.setVararg(firValueParameter2.getIsVararg());
            firValueParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            CollectionsKt.addAll(firValueParameterBuilder.getAnnotations(), firValueParameter2.getAnnotations());
            arrayList6.add(firValueParameterBuilder.mo6809build());
        }
        CollectionsKt.addAll(firSimpleFunctionBuilder.getValueParameters(), arrayList6);
        CollectionsKt.addAll(firSimpleFunctionBuilder.getAnnotations(), firMethod.getAnnotations());
        firSimpleFunctionBuilder.setDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProviderFromAnnotations$default(firSimpleFunctionBuilder.getAnnotations(), this.session, true, null, 4, null));
        FirFunction firFunctionMo6810build = firSimpleFunctionBuilder.mo6809build();
        if (zAreEqual) {
            ClassMembersKt.setJavaRecordComponent(firFunctionMo6810build, true);
        }
        updateIsOperatorFlagIfNeeded(firFunctionMo6810build);
        return firFunctionMo6810build.getSymbol();
    }

    private final void updateIsOperatorFlagIfNeeded(FirFunction function) {
        if ((function instanceof FirSimpleFunction) && OperatorFunctionChecks.INSTANCE.isOperator((FirSimpleFunction) function, this.session, null).getIsSuccess()) {
            FirDeclarationStatus status = function.getStatus();
            function.replaceStatus(UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : null, (2097147 & 2) != 0 ? status.getModality() : null, (2097147 & 4) != 0 ? status.isExpect() : false, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : true, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false));
        }
    }

    public final Pair<List<List<FirTypeRef>>, List<FirTypeParameterRef>> performFirstRoundOfBoundsResolution(List<? extends FirTypeParameterRef> typeParameters) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList(typeParameters.size());
        for (FirTypeParameter firTypeParameterMo6810build : typeParameters) {
            ArrayList arrayList3 = arrayList2;
            if (firTypeParameterMo6810build instanceof FirTypeParameter) {
                FirTypeParameter firTypeParameter = (FirTypeParameter) firTypeParameterMo6810build;
                arrayList.add(CollectionsKt.toList(firTypeParameter.getBounds()));
                FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
                firTypeParameterBuilder.setSource(firTypeParameter.getSource());
                firTypeParameterBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firTypeParameter));
                firTypeParameterBuilder.setModuleData(firTypeParameter.getModuleData());
                firTypeParameterBuilder.setOrigin(firTypeParameter.getOrigin());
                firTypeParameterBuilder.setAttributes(firTypeParameter.getAttributes().copy());
                firTypeParameterBuilder.setName(firTypeParameter.getName());
                firTypeParameterBuilder.setSymbol(firTypeParameter.getSymbol());
                firTypeParameterBuilder.setContainingDeclarationSymbol(firTypeParameter.getContainingDeclarationSymbol());
                firTypeParameterBuilder.setVariance(firTypeParameter.getVariance());
                firTypeParameterBuilder.setReified(firTypeParameter.getIsReified());
                firTypeParameterBuilder.getBounds().addAll(firTypeParameter.getBounds());
                firTypeParameterBuilder.getAnnotations().addAll(firTypeParameter.getAnnotations());
                firTypeParameterBuilder.getBounds().clear();
                List<FirTypeRef> bounds = firTypeParameter.getBounds();
                List<FirTypeRef> bounds2 = firTypeParameterBuilder.getBounds();
                Iterator<T> it = bounds.iterator();
                while (it.hasNext()) {
                    bounds2.add(JavaTypeConversionKt.resolveIfJavaType((FirTypeRef) it.next(), this.session, this.javaTypeParameterStack, FirJavaTypeConversionMode.TYPE_PARAMETER_BOUND_FIRST_ROUND));
                }
                firTypeParameterMo6810build = firTypeParameterBuilder.mo6809build();
            }
            arrayList3.add(firTypeParameterMo6810build);
        }
        return TuplesKt.to(arrayList, arrayList2);
    }

    private final void performSecondRoundOfBoundsResolution(List<? extends FirTypeParameterRef> typeParameters, List<? extends List<? extends FirTypeRef>> initialBounds) {
        int i = 0;
        for (FirTypeParameterRef firTypeParameterRef : typeParameters) {
            if (firTypeParameterRef instanceof FirTypeParameter) {
                FirTypeParameter firTypeParameter = (FirTypeParameter) firTypeParameterRef;
                List<? extends FirTypeRef> list = initialBounds.get(i);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(JavaTypeConversionKt.resolveIfJavaType((FirTypeRef) it.next(), this.session, this.javaTypeParameterStack, FirJavaTypeConversionMode.TYPE_PARAMETER_BOUND_AFTER_FIRST_ROUND));
                }
                firTypeParameter.replaceBounds(arrayList);
                i++;
            }
        }
    }

    public final void enhanceTypeParameterBoundsAfterFirstRound(List<? extends FirTypeParameterRef> typeParameters, List<? extends List<? extends FirTypeRef>> initialBounds) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(initialBounds, "initialBounds");
        performSecondRoundOfBoundsResolution(typeParameters, initialBounds);
        for (FirTypeParameterRef firTypeParameterRef : typeParameters) {
            if (firTypeParameterRef instanceof FirTypeParameter) {
                FirTypeParameter firTypeParameter = (FirTypeParameter) firTypeParameterRef;
                List<FirTypeRef> bounds = firTypeParameter.getBounds();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds, 10));
                Iterator<T> it = bounds.iterator();
                while (it.hasNext()) {
                    arrayList.add(enhanceTypeParameterBound(firTypeParameter, (FirTypeRef) it.next(), true));
                }
                firTypeParameter.replaceBounds(arrayList);
            }
        }
        for (FirTypeParameterRef firTypeParameterRef2 : typeParameters) {
            if (firTypeParameterRef2 instanceof FirTypeParameter) {
                FirTypeParameter firTypeParameter2 = (FirTypeParameter) firTypeParameterRef2;
                List<FirTypeRef> bounds2 = firTypeParameter2.getBounds();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds2, 10));
                Iterator<T> it2 = bounds2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(enhanceTypeParameterBound(firTypeParameter2, (FirTypeRef) it2.next(), false));
                }
                firTypeParameter2.replaceBounds(arrayList2);
            }
        }
    }

    private final List<FirTypeParameterRef> enhanceTypeParameterBoundsForMethod(FirFunction firMethod) {
        Pair<List<List<FirTypeRef>>, List<FirTypeParameterRef>> pairPerformFirstRoundOfBoundsResolution = performFirstRoundOfBoundsResolution(firMethod.getTypeParameters());
        List<List<FirTypeRef>> listComponent1 = pairPerformFirstRoundOfBoundsResolution.component1();
        List<FirTypeParameterRef> listComponent2 = pairPerformFirstRoundOfBoundsResolution.component2();
        enhanceTypeParameterBoundsAfterFirstRound(listComponent2, listComponent1);
        return listComponent2;
    }

    private final FirResolvedTypeRef enhanceTypeParameterBound(FirTypeParameter typeParameter, FirTypeRef bound, boolean forceOnlyHeadTypeConstructor) {
        return enhance$default(this, new EnhancementSignatureParts(this.session, this.typeQualifierResolver, typeParameter, false, forceOnlyHeadTypeConstructor, AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS, getContextQualifiers()), bound, CollectionsKt.emptyList(), FirJavaTypeConversionMode.TYPE_PARAMETER_BOUND_AFTER_FIRST_ROUND, null, 8, null);
    }

    public final List<FirTypeRef> enhanceSuperTypes(List<? extends FirTypeRef> unenhnancedSuperTypes) {
        Intrinsics.checkNotNullParameter(unenhnancedSuperTypes, "unenhnancedSuperTypes");
        ConeKotlinType purelyImplementedSupertype = getPurelyImplementedSupertype(getModuleData().getSession());
        ClassId classId = purelyImplementedSupertype != null ? ConeTypeUtilsKt.getClassId(purelyImplementedSupertype) : null;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator<T> it = unenhnancedSuperTypes.iterator();
        while (it.hasNext()) {
            FirTypeRef firTypeRefEnhanceSuperType = enhanceSuperType((FirTypeRef) it.next());
            if (classId != null && Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firTypeRefEnhanceSuperType)), classId)) {
                firTypeRefEnhanceSuperType = null;
            }
            if (firTypeRefEnhanceSuperType != null) {
                listCreateListBuilder.add(firTypeRefEnhanceSuperType);
            }
        }
        if (purelyImplementedSupertype != null) {
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(purelyImplementedSupertype);
            listCreateListBuilder.add(firResolvedTypeRefBuilder.mo6809build());
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00a1  */
    private final ConeKotlinType getPurelyImplementedSupertype(FirSession session) {
        Object next;
        ClassId classId;
        ClassId purelyImplementedInterface;
        ArrayList arrayList;
        ClassId classId2;
        ConeClassLikeLookupTag lookupTag;
        Iterator<T> it = this.owner.getAnnotations().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            FirTypeRef annotationTypeRef = ((FirAnnotation) next).getAnnotationTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
            classId2 = (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : lookupTag.getClassId();
        } while (!Intrinsics.areEqual(classId2 != null ? classId2.asSingleFqName() : null, JvmAnnotationNames.PURELY_IMPLEMENTS_ANNOTATION));
        FirAnnotation firAnnotation = (FirAnnotation) next;
        if (firAnnotation != null) {
            Object objFirstOrNull = CollectionsKt.firstOrNull(firAnnotation.getArgumentMapping().getMapping().values());
            FirConstExpression firConstExpression = objFirstOrNull instanceof FirConstExpression ? (FirConstExpression) objFirstOrNull : null;
            if (firConstExpression != null) {
                Object value = firConstExpression.getValue();
                String str = value instanceof String ? (String) value : null;
                if (str == null) {
                    classId = null;
                } else {
                    if (StringsKt.isBlank(str) || !FqNamesUtilKt.isValidJavaFqName(str)) {
                        str = null;
                    }
                    if (str != null) {
                        classId = ClassId.topLevel(new FqName(str));
                    } else {
                        classId = null;
                    }
                }
            } else {
                classId = null;
            }
        } else {
            classId = null;
        }
        if (classId == null) {
            purelyImplementedInterface = FakePureImplementationsProvider.INSTANCE.getPurelyImplementedInterface(this.owner.getSymbol().getClassId());
            if (purelyImplementedInterface == null) {
                return null;
            }
        } else {
            purelyImplementedInterface = classId;
        }
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(purelyImplementedInterface);
        if (classLikeSymbolByClassId == null) {
            return null;
        }
        List<FirTypeParameterSymbol> typeParameterSymbols = classLikeSymbolByClassId.getTypeParameterSymbols();
        List<FirTypeParameterRef> typeParameters = this.owner.getTypeParameters();
        int size = typeParameterSymbols.size();
        int size2 = typeParameters.size();
        if (size2 == size) {
            List<FirTypeParameterRef> list = typeParameters;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                arrayList2.add(new ConeTypeParameterTypeImpl(new ConeTypeParameterLookupTag(((FirTypeParameterRef) it2.next()).getSymbol()), false, null, 4, null));
            }
            arrayList = arrayList2;
        } else {
            if (size2 != 1 || size <= 1 || classId != null) {
                return null;
            }
            ConeTypeParameterTypeImpl coneTypeParameterTypeImpl = new ConeTypeParameterTypeImpl(new ConeTypeParameterLookupTag(((FirTypeParameterRef) CollectionsKt.first((List) typeParameters)).getSymbol()), false, null, 4, null);
            IntRange intRange = new IntRange(1, size);
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
            Iterator<Integer> it3 = intRange.iterator();
            while (it3.hasNext()) {
                ((IntIterator) it3).nextInt();
                arrayList3.add(coneTypeParameterTypeImpl);
            }
            arrayList = arrayList3;
        }
        return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(purelyImplementedInterface), (ConeTypeProjection[]) arrayList.toArray(new ConeTypeParameterTypeImpl[0]), false, null, 8, null);
    }

    private final FirTypeRef enhanceSuperType(FirTypeRef type) {
        return enhance$default(this, new EnhancementSignatureParts(this.session, this.typeQualifierResolver, null, false, false, AnnotationQualifierApplicabilityType.TYPE_USE, getContextQualifiers()), type, CollectionsKt.emptyList(), FirJavaTypeConversionMode.SUPERTYPE, null, 8, null);
    }

    private final FirResolvedTypeRef enhanceReceiverType(FirJavaMethod ownerFunction, List<? extends FirCallableDeclaration> overriddenMembers, JavaTypeQualifiersByElementType defaultQualifiers) {
        return enhanceValueParameter(ownerFunction, overriddenMembers, ownerFunction, defaultQualifiers, TypeInSignature.Receiver.INSTANCE, null, false);
    }

    private final FirResolvedTypeRef enhanceValueParameterType(FirFunction ownerFunction, List<? extends FirCallableDeclaration> overriddenMembers, boolean hasReceiver, JavaTypeQualifiersByElementType defaultQualifiers, PredefinedFunctionEnhancementInfo predefinedEnhancementInfo, FirJavaValueParameter ownerParameter, int index) {
        List<TypeEnhancementInfo> parametersInfo;
        return enhanceValueParameter(ownerFunction, overriddenMembers, ownerParameter, defaultQualifiers, new TypeInSignature.ValueParameter(hasReceiver, index), (predefinedEnhancementInfo == null || (parametersInfo = predefinedEnhancementInfo.getParametersInfo()) == null) ? null : (TypeEnhancementInfo) CollectionsKt.getOrNull(parametersInfo, index), this.owner.getClassKind() == ClassKind.ANNOTATION_CLASS);
    }

    private final FirResolvedTypeRef enhanceReturnType(FirCallableDeclaration owner, List<? extends FirCallableDeclaration> overriddenMembers, JavaTypeQualifiersByElementType defaultQualifiers, PredefinedFunctionEnhancementInfo predefinedEnhancementInfo) {
        AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType;
        if (owner instanceof FirJavaField) {
            annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.FIELD;
        } else {
            annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.METHOD_RETURN_TYPE;
        }
        return enhance(owner, overriddenMembers, owner, true, defaultQualifiers, annotationQualifierApplicabilityType, TypeInSignature.Return.INSTANCE, predefinedEnhancementInfo != null ? predefinedEnhancementInfo.getReturnTypeInfo() : null, this.owner.getClassKind() == ClassKind.ANNOTATION_CLASS);
    }

    /* JADX INFO: compiled from: SignatureEnhancement.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0003\u0007\b\tB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&\u0082\u0001\u0003\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature;", "", "()V", "getTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Receiver", "Return", "ValueParameter", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature$Receiver;", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature$Return;", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature$ValueParameter;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class TypeInSignature {
        public /* synthetic */ TypeInSignature(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract FirTypeRef getTypeRef(FirCallableDeclaration member);

        private TypeInSignature() {
        }

        /* JADX INFO: compiled from: SignatureEnhancement.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature$Return;", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature;", "()V", "getTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Return extends TypeInSignature {
            public static final Return INSTANCE = new Return();

            private Return() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement.TypeInSignature
            public FirTypeRef getTypeRef(FirCallableDeclaration member) {
                Intrinsics.checkNotNullParameter(member, "member");
                return member.getReturnTypeRef();
            }
        }

        /* JADX INFO: compiled from: SignatureEnhancement.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature$Receiver;", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature;", "()V", "getTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Receiver extends TypeInSignature {
            public static final Receiver INSTANCE = new Receiver();

            private Receiver() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement.TypeInSignature
            public FirTypeRef getTypeRef(FirCallableDeclaration member) {
                Intrinsics.checkNotNullParameter(member, "member");
                if (member instanceof FirJavaMethod) {
                    return ((FirJavaMethod) member).getValueParameters().get(0).getReturnTypeRef();
                }
                FirReceiverParameter receiverParameter = member.getReceiverParameter();
                FirTypeRef typeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
                Intrinsics.checkNotNull(typeRef);
                return typeRef;
            }
        }

        /* JADX INFO: compiled from: SignatureEnhancement.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature$ValueParameter;", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement$TypeInSignature;", "hasReceiver", "", "index", "", "(ZI)V", "getHasReceiver", "()Z", "getIndex", "()I", "getTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ValueParameter extends TypeInSignature {
            private final boolean hasReceiver;
            private final int index;

            public ValueParameter(boolean z, int i) {
                super(null);
                this.hasReceiver = z;
                this.index = i;
            }

            public final boolean getHasReceiver() {
                return this.hasReceiver;
            }

            public final int getIndex() {
                return this.index;
            }

            @Override // org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement.TypeInSignature
            public FirTypeRef getTypeRef(FirCallableDeclaration member) {
                Intrinsics.checkNotNullParameter(member, "member");
                if (this.hasReceiver && (member instanceof FirJavaMethod)) {
                    return ((FirJavaMethod) member).getValueParameters().get(this.index + 1).getReturnTypeRef();
                }
                return ((FirFunction) member).getValueParameters().get(this.index).getReturnTypeRef();
            }
        }
    }

    private final FirResolvedTypeRef enhanceValueParameter(FirFunction firFunction, List<? extends FirCallableDeclaration> list, FirAnnotationContainer firAnnotationContainer, JavaTypeQualifiersByElementType javaTypeQualifiersByElementType, TypeInSignature typeInSignature, TypeEnhancementInfo typeEnhancementInfo, boolean z) {
        JavaTypeQualifiersByElementType javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers;
        return enhance(firFunction, list, firAnnotationContainer == null ? firFunction : firAnnotationContainer, false, (firAnnotationContainer == null || (javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers = this.typeQualifierResolver.extractAndMergeDefaultQualifiers(javaTypeQualifiersByElementType, firAnnotationContainer.getAnnotations())) == null) ? javaTypeQualifiersByElementType : javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers, AnnotationQualifierApplicabilityType.VALUE_PARAMETER, typeInSignature, typeEnhancementInfo, z);
    }

    private final FirResolvedTypeRef enhance(FirCallableDeclaration firCallableDeclaration, List<? extends FirCallableDeclaration> list, FirAnnotationContainer firAnnotationContainer, boolean z, JavaTypeQualifiersByElementType javaTypeQualifiersByElementType, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, TypeInSignature typeInSignature, TypeEnhancementInfo typeEnhancementInfo, boolean z2) {
        FirTypeRef typeRef = typeInSignature.getTypeRef(firCallableDeclaration);
        List<? extends FirCallableDeclaration> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(typeInSignature.getTypeRef((FirCallableDeclaration) it.next()));
        }
        return enhance(new EnhancementSignatureParts(this.session, this.typeQualifierResolver, firAnnotationContainer, z, false, annotationQualifierApplicabilityType, javaTypeQualifiersByElementType), typeRef, arrayList, z2 ? FirJavaTypeConversionMode.ANNOTATION_MEMBER : FirJavaTypeConversionMode.DEFAULT, typeEnhancementInfo);
    }

    static /* synthetic */ FirResolvedTypeRef enhance$default(FirSignatureEnhancement firSignatureEnhancement, EnhancementSignatureParts enhancementSignatureParts, FirTypeRef firTypeRef, List list, FirJavaTypeConversionMode firJavaTypeConversionMode, TypeEnhancementInfo typeEnhancementInfo, int i, Object obj) {
        if ((i & 8) != 0) {
            typeEnhancementInfo = null;
        }
        return firSignatureEnhancement.enhance(enhancementSignatureParts, firTypeRef, list, firJavaTypeConversionMode, typeEnhancementInfo);
    }

    private final FirResolvedTypeRef enhance(EnhancementSignatureParts enhancementSignatureParts, FirTypeRef firTypeRef, List<? extends FirTypeRef> list, FirJavaTypeConversionMode firJavaTypeConversionMode, TypeEnhancementInfo typeEnhancementInfo) {
        ConeKotlinType coneKotlinType = toConeKotlinType(firTypeRef, firJavaTypeConversionMode);
        List<? extends FirTypeRef> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(toConeKotlinType((FirTypeRef) it.next(), firJavaTypeConversionMode));
        }
        Function1 function1ComputeIndexedQualifiers$default = AbstractSignatureParts.computeIndexedQualifiers$default(enhancementSignatureParts, coneKotlinType, arrayList, typeEnhancementInfo, false, 4, null);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        ConeKotlinType coneKotlinTypeEnhance = JavaTypeUtilsKt.enhance(coneKotlinType, this.session, function1ComputeIndexedQualifiers$default);
        if (coneKotlinTypeEnhance != null) {
            coneKotlinType = coneKotlinTypeEnhance;
        }
        firResolvedTypeRefBuilder.setType(coneKotlinType);
        CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), firTypeRef.getAnnotations());
        firResolvedTypeRefBuilder.setSource(firTypeRef.getSource());
        return firResolvedTypeRefBuilder.mo6809build();
    }

    private final ConeKotlinType toConeKotlinType(FirTypeRef firTypeRef, FirJavaTypeConversionMode firJavaTypeConversionMode) {
        return JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible(firTypeRef, this.session, this.javaTypeParameterStack, firJavaTypeConversionMode);
    }
}
