package org.jetbrains.kotlin.fir.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeConflictingProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeApproximator;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttributeKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitAnyTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBooleanTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitIntTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNothingTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNullableAnyTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNullableNothingTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitStringTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUnitTypeRef;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrDynamicTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.CommonFlexibleTypeBoundsChecker;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: Fir2IrTypeConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000£\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0001h\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020tH\u0002J\u0014\u0010u\u001a\u0004\u0018\u00010\u001b2\b\u0010v\u001a\u0004\u0018\u00010\u001aH\u0002J\u0014\u0010w\u001a\u0004\u0018\u00010\u001b2\b\u0010v\u001a\u0004\u0018\u00010\u001aH\u0002J\f\u0010x\u001a\u00020y*\u00020zH\u0002J\u001a\u0010{\u001a\u00020y*\u00020r2\f\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00160}H\u0002JH\u0010~\u001a\u00020\u0017*\u00020r2\t\b\u0002\u0010\u007f\u001a\u00030\u0080\u00012\u0010\b\u0002\u0010\u0081\u0001\u001a\t\u0012\u0005\u0012\u00030\u0082\u00010M2\t\b\u0002\u0010\u0083\u0001\u001a\u00020y2\t\b\u0002\u0010\u0084\u0001\u001a\u00020y2\t\b\u0002\u0010\u0085\u0001\u001a\u00020yJ\u0016\u0010~\u001a\u00020\u0017*\u00030\u0086\u00012\t\b\u0002\u0010\u007f\u001a\u00030\u0080\u0001J\u0018\u0010\u0087\u0001\u001a\u00030\u0088\u0001*\u00030\u0089\u00012\u0007\u0010\u007f\u001a\u00030\u0080\u0001H\u0002R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R'\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u00198@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001dR'\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00170\u00198@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u001f\u001a\u0004\b!\u0010\u001dR\u0012\u0010#\u001a\u00020$X\u0096\u0005¢\u0006\u0006\u001a\u0004\b%\u0010&R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010'\u001a\u00020(X\u0096\u0005¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0012\u0010+\u001a\u00020,X\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0012\u0010/\u001a\u000200X\u0096\u0005¢\u0006\u0006\u001a\u0004\b1\u00102R\u0012\u00103\u001a\u000204X\u0096\u0005¢\u0006\u0006\u001a\u0004\b5\u00106R\u001b\u00107\u001a\u0002088BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b;\u0010\u001f\u001a\u0004\b9\u0010:R\u0012\u0010<\u001a\u00020=X\u0096\u0005¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0012\u0010@\u001a\u00020AX\u0096\u0005¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0012\u0010D\u001a\u00020EX\u0096\u0005¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0012\u0010H\u001a\u00020IX\u0096\u0005¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u0018\u0010L\u001a\b\u0012\u0004\u0012\u00020N0MX\u0096\u0005¢\u0006\u0006\u001a\u0004\bO\u0010PR\u0012\u0010Q\u001a\u00020RX\u0096\u0005¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0012\u0010U\u001a\u00020VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bW\u0010XR\u0012\u0010Y\u001a\u00020ZX\u0096\u0005¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u0012\u0010]\u001a\u00020^X\u0096\u0005¢\u0006\u0006\u001a\u0004\b_\u0010`R\u0012\u0010a\u001a\u00020bX\u0096\u0005¢\u0006\u0006\u001a\u0004\bc\u0010dR\u000e\u0010e\u001a\u00020fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010g\u001a\u00020hX\u0082\u0004¢\u0006\u0004\n\u0002\u0010iR\u0012\u0010j\u001a\u00020\u0000X\u0096\u0005¢\u0006\u0006\u001a\u0004\bk\u0010lR\u0012\u0010m\u001a\u00020nX\u0096\u0005¢\u0006\u0006\u001a\u0004\bo\u0010p¨\u0006\u008a\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "capturedTypeCache", "", "Lorg/jetbrains/kotlin/fir/types/ConeCapturedType;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "classIdToSymbolMap", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getClassIdToSymbolMap$fir2ir", "()Ljava/util/Map;", "classIdToSymbolMap$delegate", "Lkotlin/Lazy;", "classIdToTypeMap", "getClassIdToTypeMap$fir2ir", "classIdToTypeMap$delegate", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "errorTypeForCapturedTypeStub", "Lorg/jetbrains/kotlin/ir/types/IrErrorType;", "getErrorTypeForCapturedTypeStub", "()Lorg/jetbrains/kotlin/ir/types/IrErrorType;", "errorTypeForCapturedTypeStub$delegate", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeApproximator", "Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;", "typeApproximatorConfiguration", "org/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter$typeApproximatorConfiguration$1", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter$typeApproximatorConfiguration$1;", "typeConverter", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "approximateType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getArrayClassSymbol", "classId", "getBuiltInClassSymbol", "isMutabilityFlexible", "", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "isRecursive", "visited", "", "toIrType", "typeOrigin", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "hasFlexibleNullability", "hasFlexibleMutability", "addRawTypeAnnotation", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "toIrTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrTypeConverter implements Fir2IrComponents {
    private final Map<ConeCapturedType, IrType> capturedTypeCache;

    /* JADX INFO: renamed from: classIdToSymbolMap$delegate, reason: from kotlin metadata */
    private final Lazy classIdToSymbolMap;

    /* JADX INFO: renamed from: classIdToTypeMap$delegate, reason: from kotlin metadata */
    private final Lazy classIdToTypeMap;
    private final Fir2IrComponents components;

    /* JADX INFO: renamed from: errorTypeForCapturedTypeStub$delegate, reason: from kotlin metadata */
    private final Lazy errorTypeForCapturedTypeStub;
    private final ConeTypeApproximator typeApproximator;
    private final Fir2IrTypeConverter$typeApproximatorConfiguration$1 typeApproximatorConfiguration;

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

    /* JADX WARN: Type inference failed for: r3v13, types: [org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter$typeApproximatorConfiguration$1] */
    public Fir2IrTypeConverter(Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        this.classIdToSymbolMap = LazyKt.lazy(new Function0<Map<ClassId, ? extends IrClassSymbol>>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter$classIdToSymbolMap$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<ClassId, ? extends IrClassSymbol> invoke() {
                return MapsKt.mapOf(TuplesKt.to(StandardClassIds.INSTANCE.getNothing(), this.this$0.getIrBuiltIns().getNothingClass()), TuplesKt.to(StandardClassIds.INSTANCE.getAny(), this.this$0.getIrBuiltIns().getAnyClass()), TuplesKt.to(StandardClassIds.INSTANCE.getUnit(), this.this$0.getIrBuiltIns().getUnitClass()), TuplesKt.to(StandardClassIds.INSTANCE.getBoolean(), this.this$0.getIrBuiltIns().getBooleanClass()), TuplesKt.to(StandardClassIds.INSTANCE.getCharSequence(), this.this$0.getIrBuiltIns().getCharSequenceClass()), TuplesKt.to(StandardClassIds.INSTANCE.getString(), this.this$0.getIrBuiltIns().getStringClass()), TuplesKt.to(StandardClassIds.INSTANCE.getNumber(), this.this$0.getIrBuiltIns().getNumberClass()), TuplesKt.to(StandardClassIds.INSTANCE.getLong(), this.this$0.getIrBuiltIns().getLongClass()), TuplesKt.to(StandardClassIds.INSTANCE.getInt(), this.this$0.getIrBuiltIns().getIntClass()), TuplesKt.to(StandardClassIds.INSTANCE.getShort(), this.this$0.getIrBuiltIns().getShortClass()), TuplesKt.to(StandardClassIds.INSTANCE.getByte(), this.this$0.getIrBuiltIns().getByteClass()), TuplesKt.to(StandardClassIds.INSTANCE.getFloat(), this.this$0.getIrBuiltIns().getFloatClass()), TuplesKt.to(StandardClassIds.INSTANCE.getDouble(), this.this$0.getIrBuiltIns().getDoubleClass()), TuplesKt.to(StandardClassIds.INSTANCE.getChar(), this.this$0.getIrBuiltIns().getCharClass()), TuplesKt.to(StandardClassIds.INSTANCE.getArray(), this.this$0.getIrBuiltIns().getArrayClass()), TuplesKt.to(StandardClassIds.Annotations.INSTANCE.getIntrinsicConstEvaluation(), this.this$0.getIrBuiltIns().getIntrinsicConst()));
            }
        });
        this.classIdToTypeMap = LazyKt.lazy(new Function0<Map<ClassId, ? extends IrType>>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter$classIdToTypeMap$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<ClassId, ? extends IrType> invoke() {
                return MapsKt.mapOf(TuplesKt.to(StandardClassIds.INSTANCE.getNothing(), this.this$0.getIrBuiltIns().getNothingType()), TuplesKt.to(StandardClassIds.INSTANCE.getUnit(), this.this$0.getIrBuiltIns().getUnitType()), TuplesKt.to(StandardClassIds.INSTANCE.getBoolean(), this.this$0.getIrBuiltIns().getBooleanType()), TuplesKt.to(StandardClassIds.INSTANCE.getString(), this.this$0.getIrBuiltIns().getStringType()), TuplesKt.to(StandardClassIds.INSTANCE.getAny(), this.this$0.getIrBuiltIns().getAnyType()), TuplesKt.to(StandardClassIds.INSTANCE.getLong(), this.this$0.getIrBuiltIns().getLongType()), TuplesKt.to(StandardClassIds.INSTANCE.getInt(), this.this$0.getIrBuiltIns().getIntType()), TuplesKt.to(StandardClassIds.INSTANCE.getShort(), this.this$0.getIrBuiltIns().getShortType()), TuplesKt.to(StandardClassIds.INSTANCE.getByte(), this.this$0.getIrBuiltIns().getByteType()), TuplesKt.to(StandardClassIds.INSTANCE.getFloat(), this.this$0.getIrBuiltIns().getFloatType()), TuplesKt.to(StandardClassIds.INSTANCE.getDouble(), this.this$0.getIrBuiltIns().getDoubleType()), TuplesKt.to(StandardClassIds.INSTANCE.getChar(), this.this$0.getIrBuiltIns().getCharType()));
            }
        });
        this.capturedTypeCache = new LinkedHashMap();
        this.errorTypeForCapturedTypeStub = LazyKt.lazy(new Function0<IrErrorType>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter$errorTypeForCapturedTypeStub$2
            @Override // kotlin.jvm.functions.Function0
            public final IrErrorType invoke() {
                return ConversionUtilsKt.createErrorType();
            }
        });
        this.typeApproximator = new ConeTypeApproximator(TypeComponentsKt.getTypeContext(getSession()), FirLanguageSettingsComponentKt.getLanguageVersionSettings(getSession()));
        this.typeApproximatorConfiguration = new TypeApproximatorConfiguration.AllFlexibleSameValue() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter$typeApproximatorConfiguration$1
            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration.AllFlexibleSameValue
            public boolean getAllFlexible() {
                return true;
            }

            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
            public boolean getErrorType() {
                return true;
            }

            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
            public boolean getIntegerConstantOperatorType() {
                return true;
            }

            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
            public boolean getIntegerLiteralConstantType() {
                return true;
            }

            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
            public boolean getIntersectionTypesInContravariantPositions() {
                return true;
            }
        };
    }

    public final Map<ClassId, IrClassSymbol> getClassIdToSymbolMap$fir2ir() {
        return (Map) this.classIdToSymbolMap.getValue();
    }

    public final Map<ClassId, IrType> getClassIdToTypeMap$fir2ir() {
        return (Map) this.classIdToTypeMap.getValue();
    }

    private final IrErrorType getErrorTypeForCapturedTypeStub() {
        return (IrErrorType) this.errorTypeForCapturedTypeStub.getValue();
    }

    public static /* synthetic */ IrType toIrType$default(Fir2IrTypeConverter fir2IrTypeConverter, FirTypeRef firTypeRef, ConversionTypeOrigin conversionTypeOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return fir2IrTypeConverter.toIrType(firTypeRef, conversionTypeOrigin);
    }

    public final IrType toIrType(FirTypeRef firTypeRef, ConversionTypeOrigin typeOrigin) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        this.capturedTypeCache.clear();
        if (!(firTypeRef instanceof FirResolvedTypeRef)) {
            return ConversionUtilsKt.createErrorType();
        }
        if (!(firTypeRef instanceof FirImplicitBuiltinTypeRef)) {
            return toIrType$default(this, ((FirResolvedTypeRef) firTypeRef).getType(), typeOrigin, firTypeRef.getAnnotations(), false, false, false, 28, null);
        }
        if (firTypeRef instanceof FirImplicitNothingTypeRef) {
            return getIrBuiltIns().getNothingType();
        }
        if (firTypeRef instanceof FirImplicitUnitTypeRef) {
            return getIrBuiltIns().getUnitType();
        }
        if (firTypeRef instanceof FirImplicitBooleanTypeRef) {
            return getIrBuiltIns().getBooleanType();
        }
        if (firTypeRef instanceof FirImplicitStringTypeRef) {
            return getIrBuiltIns().getStringType();
        }
        if (firTypeRef instanceof FirImplicitAnyTypeRef) {
            return getIrBuiltIns().getAnyType();
        }
        if (firTypeRef instanceof FirImplicitIntTypeRef) {
            return getIrBuiltIns().getIntType();
        }
        if (firTypeRef instanceof FirImplicitNullableAnyTypeRef) {
            return getIrBuiltIns().getAnyNType();
        }
        return firTypeRef instanceof FirImplicitNullableNothingTypeRef ? getIrBuiltIns().getNothingNType() : toIrType$default(this, ((FirImplicitBuiltinTypeRef) firTypeRef).getType(), typeOrigin, firTypeRef.getAnnotations(), false, false, false, 28, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrType toIrType$default(Fir2IrTypeConverter fir2IrTypeConverter, ConeKotlinType coneKotlinType, ConversionTypeOrigin conversionTypeOrigin, List list, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 1) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        ConversionTypeOrigin conversionTypeOrigin2 = conversionTypeOrigin;
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        return fir2IrTypeConverter.toIrType(coneKotlinType, conversionTypeOrigin2, list, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3);
    }

    public final IrType toIrType(ConeKotlinType coneKotlinType, ConversionTypeOrigin typeOrigin, List<? extends FirAnnotation> annotations, boolean z, boolean z2, boolean z3) {
        ConeSimpleKotlinType coneSimpleKotlinTypeReplaceArguments;
        IrClassifierSymbol irClassifierSymbol;
        IrConstructorCall irConstructorCallFlexibleNullabilityAnnotationConstructorCall$fir2ir;
        IrConstructorCall irConstructorCallRawTypeAnnotationConstructorCall$fir2ir;
        IrConstructorCall irConstructorCallExtensionFunctionTypeAnnotationConstructorCall$fir2ir;
        IrConstructorCall irConstructorCallFlexibleMutabilityAnnotationConstructorCall$fir2ir;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        if (coneKotlinType instanceof ConeErrorType) {
            return ConversionUtilsKt.createErrorType();
        }
        Object obj = null;
        if (coneKotlinType instanceof ConeLookupTagBasedType) {
            final ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = arrayList;
            CollectionsKt.addAll(arrayList2, getAnnotationGenerator().toIrAnnotations(annotations));
            IrClassSymbol builtInClassSymbol = getBuiltInClassSymbol(ConeTypeUtilsKt.getClassId(coneKotlinType));
            if (builtInClassSymbol != null) {
                irClassifierSymbol = builtInClassSymbol;
            } else {
                ConeLookupTagBasedType coneLookupTagBasedType = (ConeLookupTagBasedType) coneKotlinType;
                FirClassifierSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneLookupTagBasedType.getLookupTag(), getSession());
                IrClassifierSymbol symbol2 = symbol != null ? ConversionUtilsKt.toSymbol(this, symbol, typeOrigin, new Function1<List<? extends FirAnnotation>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter$toIrType$irSymbol$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(List<? extends FirAnnotation> list) {
                        invoke2(list);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(List<? extends FirAnnotation> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        CollectionsKt.addAll(arrayList, this.getAnnotationGenerator().toIrAnnotations(it));
                    }
                }) : null;
                if (symbol2 == null) {
                    ConeClassifierLookupTag lookupTag = coneLookupTagBasedType.getLookupTag();
                    ConeClassLikeLookupTag coneClassLikeLookupTag = lookupTag instanceof ConeClassLikeLookupTag ? (ConeClassLikeLookupTag) lookupTag : null;
                    builtInClassSymbol = coneClassLikeLookupTag != null ? getClassifierStorage().getIrClassSymbolForNotFoundClass(coneClassLikeLookupTag) : null;
                    if (builtInClassSymbol == null) {
                        return ConversionUtilsKt.createErrorType();
                    }
                    irClassifierSymbol = builtInClassSymbol;
                } else {
                    irClassifierSymbol = symbol2;
                }
            }
            if (CompilerConeAttributesKt.getHasEnhancedNullability(coneKotlinType)) {
                IrConstructorCall irConstructorCallEnhancedNullabilityAnnotationConstructorCall$fir2ir = getBuiltIns().enhancedNullabilityAnnotationConstructorCall$fir2ir();
                if (irConstructorCallEnhancedNullabilityAnnotationConstructorCall$fir2ir != null) {
                    arrayList2.add(irConstructorCallEnhancedNullabilityAnnotationConstructorCall$fir2ir);
                }
            } else if (z && (irConstructorCallFlexibleNullabilityAnnotationConstructorCall$fir2ir = getBuiltIns().flexibleNullabilityAnnotationConstructorCall$fir2ir()) != null) {
                arrayList2.add(irConstructorCallFlexibleNullabilityAnnotationConstructorCall$fir2ir);
            }
            if (z2 && (irConstructorCallFlexibleMutabilityAnnotationConstructorCall$fir2ir = getBuiltIns().flexibleMutabilityAnnotationConstructorCall$fir2ir()) != null) {
                arrayList2.add(irConstructorCallFlexibleMutabilityAnnotationConstructorCall$fir2ir);
            }
            if (CompilerConeAttributesKt.isExtensionFunctionType(coneKotlinType) && FirAnnotationUtilsKt.getAnnotationsByClassId(annotations, StandardClassIds.Annotations.INSTANCE.getExtensionFunctionType(), getSession()).isEmpty() && (irConstructorCallExtensionFunctionTypeAnnotationConstructorCall$fir2ir = getBuiltIns().extensionFunctionTypeAnnotationConstructorCall$fir2ir()) != null) {
                arrayList2.add(irConstructorCallExtensionFunctionTypeAnnotationConstructorCall$fir2ir);
            }
            if (z3 && (irConstructorCallRawTypeAnnotationConstructorCall$fir2ir = getBuiltIns().rawTypeAnnotationConstructorCall$fir2ir()) != null) {
                arrayList2.add(irConstructorCallRawTypeAnnotationConstructorCall$fir2ir);
            }
            for (FirAnnotation firAnnotation : CustomAnnotationTypeAttributeKt.getCustomAnnotations(coneKotlinType.getAttributes())) {
                List<? extends FirAnnotation> list = annotations;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            FirTypeRef annotationTypeRef = ((FirAnnotation) it.next()).getAnnotationTypeRef();
                            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
                            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                            ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
                            FirTypeRef annotationTypeRef2 = firAnnotation.getAnnotationTypeRef();
                            FirResolvedTypeRef firResolvedTypeRef2 = annotationTypeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef2 : null;
                            ConeKotlinType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
                            if (Intrinsics.areEqual(coneClassLikeType, type2 instanceof ConeClassLikeType ? (ConeClassLikeType) type2 : null)) {
                            }
                        }
                    }
                }
                IrExpression irExpressionConvertToIrConstructorCall = getCallGenerator().convertToIrConstructorCall(firAnnotation);
                IrConstructorCall irConstructorCall = irExpressionConvertToIrConstructorCall instanceof IrConstructorCall ? (IrConstructorCall) irExpressionConvertToIrConstructorCall : null;
                if (irConstructorCall != null) {
                    arrayList2.add(irConstructorCall);
                }
            }
            ConeKotlinType coneKotlinTypeApproximateType = approximateType(TypeExpansionUtilsKt.fullyExpandedType((ConeSimpleKotlinType) coneKotlinType, getSession()));
            boolean zIsMarkedNullable = ConeTypeUtilsKt.isMarkedNullable(coneKotlinTypeApproximateType);
            ConeTypeProjection[] typeArguments = coneKotlinTypeApproximateType.getTypeArguments();
            ArrayList arrayList3 = new ArrayList(typeArguments.length);
            for (ConeTypeProjection coneTypeProjection : typeArguments) {
                arrayList3.add(toIrTypeArgument(coneTypeProjection, typeOrigin));
            }
            return new IrSimpleTypeImpl(irClassifierSymbol, zIsMarkedNullable, arrayList3, arrayList, (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
        }
        if (coneKotlinType instanceof ConeRawType) {
            ConeRawType coneRawType = (ConeRawType) coneKotlinType;
            return toIrType(coneRawType.getLowerBound(), typeOrigin, annotations, coneRawType.getLowerBound().getNullability() != coneRawType.getUpperBound().getNullability(), isMutabilityFlexible((ConeFlexibleType) coneKotlinType), true);
        }
        if (coneKotlinType instanceof ConeDynamicType) {
            return new IrDynamicTypeImpl(null, getAnnotationGenerator().toIrAnnotations(annotations), Variance.INVARIANT);
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            if (coneFlexibleType.getUpperBound() instanceof ConeClassLikeType) {
                ConeSimpleKotlinType upperBound = coneFlexibleType.getUpperBound();
                Intrinsics.checkNotNull(upperBound, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
                ConeClassLikeType coneClassLikeType2 = (ConeClassLikeType) upperBound;
                ConeSimpleKotlinType lowerBound = coneFlexibleType.getLowerBound();
                if ((lowerBound instanceof ConeClassLikeType) && Intrinsics.areEqual(((ConeClassLikeType) lowerBound).getLookupTag(), coneClassLikeType2.getLookupTag())) {
                    coneSimpleKotlinTypeReplaceArguments = typeContext.replaceArguments(lowerBound, typeContext.getArguments(coneClassLikeType2));
                } else {
                    coneSimpleKotlinTypeReplaceArguments = lowerBound;
                }
                Object objWithNullability = typeContext.withNullability(coneSimpleKotlinTypeReplaceArguments, ConeTypeUtilsKt.isNullable(coneClassLikeType2));
                Intrinsics.checkNotNull(objWithNullability, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                return toIrType$default(this, TypeUtilsKt.withAttributes((ConeKotlinType) objWithNullability, lowerBound.getAttributes()), typeOrigin, annotations, lowerBound.getNullability() != coneClassLikeType2.getNullability(), isMutabilityFlexible(coneFlexibleType), false, 16, null);
            }
            return toIrType$default(this, coneFlexibleType.getUpperBound(), typeOrigin, annotations, coneFlexibleType.getLowerBound().getNullability() != coneFlexibleType.getUpperBound().getNullability(), isMutabilityFlexible(coneFlexibleType), false, 16, null);
        }
        if (coneKotlinType instanceof ConeCapturedType) {
            IrType irType = this.capturedTypeCache.get(coneKotlinType);
            if (irType != null) {
                return irType;
            }
            this.capturedTypeCache.put(coneKotlinType, getErrorTypeForCapturedTypeStub());
            ConeCapturedType coneCapturedType = (ConeCapturedType) coneKotlinType;
            List<ConeKotlinType> supertypes = coneCapturedType.getConstructor().getSupertypes();
            Intrinsics.checkNotNull(supertypes);
            for (Object obj2 : supertypes) {
                ConeKotlinType coneKotlinType2 = (ConeKotlinType) obj2;
                ConeTypeProjection projection = coneCapturedType.getConstructor().getProjection();
                ConeKotlinTypeProjection coneKotlinTypeProjection = projection instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) projection : null;
                if (Intrinsics.areEqual(coneKotlinType2, coneKotlinTypeProjection != null ? coneKotlinTypeProjection.getType() : null)) {
                    obj = obj2;
                    break;
                }
            }
            ConeKotlinType coneKotlinType3 = (ConeKotlinType) obj;
            IrType irType$default = toIrType$default(this, coneKotlinType3 == null ? (ConeKotlinType) CollectionsKt.first((List) supertypes) : coneKotlinType3, typeOrigin, null, false, false, false, 30, null);
            this.capturedTypeCache.put(coneKotlinType, irType$default);
            return irType$default;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return IrTypesKt.makeNotNull(toIrType$default(this, ((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), typeOrigin, null, false, false, false, 30, null));
        }
        if (coneKotlinType instanceof ConeIntersectionType) {
            return toIrType$default(this, (ConeKotlinType) CollectionsKt.first(((ConeIntersectionType) coneKotlinType).getIntersectedTypes()), typeOrigin, null, false, false, false, 30, null);
        }
        if (!(coneKotlinType instanceof ConeStubType) && !(coneKotlinType instanceof ConeIntegerLiteralType)) {
            throw new NoWhenBranchMatchedException();
        }
        return ConversionUtilsKt.createErrorType();
    }

    private final boolean isMutabilityFlexible(ConeFlexibleType coneFlexibleType) {
        FqName fqNameAsSingleFqName;
        ClassId classId;
        FqName fqNameAsSingleFqName2;
        ClassId classId2 = ConeTypeUtilsKt.getClassId(coneFlexibleType.getLowerBound());
        if (classId2 == null || (fqNameAsSingleFqName = classId2.asSingleFqName()) == null || (classId = ConeTypeUtilsKt.getClassId(coneFlexibleType.getUpperBound())) == null || (fqNameAsSingleFqName2 = classId.asSingleFqName()) == null || Intrinsics.areEqual(fqNameAsSingleFqName, fqNameAsSingleFqName2)) {
            return false;
        }
        return Intrinsics.areEqual(CommonFlexibleTypeBoundsChecker.INSTANCE.getBaseBoundFqNameByMutability(fqNameAsSingleFqName), CommonFlexibleTypeBoundsChecker.INSTANCE.getBaseBoundFqNameByMutability(fqNameAsSingleFqName2));
    }

    private static final IrTypeProjection toIrTypeArgument$toIrTypeArgument(Fir2IrTypeConverter fir2IrTypeConverter, ConversionTypeOrigin conversionTypeOrigin, ConeKotlinType coneKotlinType, Variance variance) {
        return IrSimpleTypeImplKt.makeTypeProjection(toIrType$default(fir2IrTypeConverter, coneKotlinType, conversionTypeOrigin, null, false, false, false, 30, null), variance);
    }

    private final IrTypeArgument toIrTypeArgument(ConeTypeProjection coneTypeProjection, ConversionTypeOrigin conversionTypeOrigin) {
        if (Intrinsics.areEqual(coneTypeProjection, ConeStarProjection.INSTANCE)) {
            return IrStarProjectionImpl.INSTANCE;
        }
        if (coneTypeProjection instanceof ConeKotlinTypeProjectionIn) {
            return toIrTypeArgument$toIrTypeArgument(this, conversionTypeOrigin, ((ConeKotlinTypeProjectionIn) coneTypeProjection).getType(), Variance.IN_VARIANCE);
        }
        if (coneTypeProjection instanceof ConeKotlinTypeProjectionOut) {
            return toIrTypeArgument$toIrTypeArgument(this, conversionTypeOrigin, ((ConeKotlinTypeProjectionOut) coneTypeProjection).getType(), Variance.OUT_VARIANCE);
        }
        if (coneTypeProjection instanceof ConeKotlinTypeConflictingProjection) {
            return toIrTypeArgument$toIrTypeArgument(this, conversionTypeOrigin, ((ConeKotlinTypeConflictingProjection) coneTypeProjection).getType(), Variance.INVARIANT);
        }
        if (!(coneTypeProjection instanceof ConeKotlinType)) {
            throw new NoWhenBranchMatchedException();
        }
        if ((coneTypeProjection instanceof ConeCapturedType) && this.capturedTypeCache.containsKey(coneTypeProjection) && isRecursive((ConeKotlinType) coneTypeProjection, new LinkedHashSet())) {
            return IrStarProjectionImpl.INSTANCE;
        }
        return IrSimpleTypeImplKt.makeTypeProjection(toIrType$default(this, (ConeKotlinType) coneTypeProjection, conversionTypeOrigin, null, false, false, false, 30, null), Variance.INVARIANT);
    }

    private final boolean isRecursive(ConeKotlinType coneKotlinType, Set<ConeCapturedType> set) {
        boolean z;
        boolean zIsRecursive;
        if (coneKotlinType instanceof ConeLookupTagBasedType) {
            for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
                if (coneTypeProjection instanceof ConeKotlinType) {
                    zIsRecursive = isRecursive((ConeKotlinType) coneTypeProjection, set);
                } else if (coneTypeProjection instanceof ConeKotlinTypeProjectionIn) {
                    zIsRecursive = isRecursive(((ConeKotlinTypeProjectionIn) coneTypeProjection).getType(), set);
                } else {
                    zIsRecursive = coneTypeProjection instanceof ConeKotlinTypeProjectionOut ? isRecursive(((ConeKotlinTypeProjectionOut) coneTypeProjection).getType(), set) : false;
                }
                if (zIsRecursive) {
                    return true;
                }
            }
        } else if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            if (isRecursive(coneFlexibleType.getLowerBound(), set) || isRecursive(coneFlexibleType.getUpperBound(), set)) {
                return true;
            }
        } else if (coneKotlinType instanceof ConeCapturedType) {
            if (!set.add(coneKotlinType)) {
                return true;
            }
            List<ConeKotlinType> supertypes = ((ConeCapturedType) coneKotlinType).getConstructor().getSupertypes();
            if (supertypes != null) {
                List<ConeKotlinType> list = supertypes;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        if (isRecursive((ConeKotlinType) it.next(), set)) {
                            z = true;
                            break;
                        }
                    }
                } else {
                    z = false;
                    break;
                }
                if (z) {
                    return true;
                }
            }
        } else {
            if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
                return isRecursive(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), set);
            }
            if (coneKotlinType instanceof ConeIntersectionType) {
                Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
                if (!(intersectedTypes instanceof Collection) || !intersectedTypes.isEmpty()) {
                    Iterator<T> it2 = intersectedTypes.iterator();
                    while (it2.hasNext()) {
                        if (isRecursive((ConeKotlinType) it2.next(), set)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private final IrClassSymbol getArrayClassSymbol(ClassId classId) {
        ClassId classId2 = StandardClassIds.INSTANCE.getElementTypeByPrimitiveArrayType().get(classId);
        if (classId2 == null) {
            return null;
        }
        IrClassSymbol irClassSymbol = getIrBuiltIns().getPrimitiveArrayForType().get(getClassIdToTypeMap$fir2ir().get(classId2));
        if (irClassSymbol != null) {
            return irClassSymbol;
        }
        throw new IllegalStateException(("Strange primitiveId " + classId2 + " from array: " + classId).toString());
    }

    private final IrClassSymbol getBuiltInClassSymbol(ClassId classId) {
        IrClassSymbol irClassSymbol = getClassIdToSymbolMap$fir2ir().get(classId);
        return irClassSymbol == null ? getArrayClassSymbol(classId) : irClassSymbol;
    }

    private final ConeKotlinType approximateType(ConeSimpleKotlinType type) {
        if ((type instanceof ConeClassLikeType) && type.getTypeArguments().length == 0) {
            return type;
        }
        final ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
        ConeKotlinType coneKotlinTypeSubstituteOrSelf = new AbstractConeSubstitutor(typeContext) { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter$approximateType$substitutor$1
            {
                super(typeContext);
            }

            @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
            public ConeKotlinType substituteType(ConeKotlinType type2) {
                ConeKotlinType alternativeType;
                Intrinsics.checkNotNullParameter(type2, "type");
                if (!(type2 instanceof ConeIntersectionType) || (alternativeType = ((ConeIntersectionType) type2).getAlternativeType()) == null) {
                    return null;
                }
                return substituteOrSelf(alternativeType);
            }
        }.substituteOrSelf(type);
        ConeKotlinType coneKotlinTypeApproximateToSuperType = this.typeApproximator.approximateToSuperType(coneKotlinTypeSubstituteOrSelf, (TypeApproximatorConfiguration) this.typeApproximatorConfiguration);
        return coneKotlinTypeApproximateToSuperType == null ? coneKotlinTypeSubstituteOrSelf : coneKotlinTypeApproximateToSuperType;
    }
}
