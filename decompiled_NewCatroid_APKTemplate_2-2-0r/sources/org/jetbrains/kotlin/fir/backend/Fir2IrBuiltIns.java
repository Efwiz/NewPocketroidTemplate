package org.jetbrains.kotlin.fir.backend;

import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: Fir2IrBuiltIns.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000à\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000f\u0010q\u001a\u0004\u0018\u00010rH\u0000¢\u0006\u0002\bsJ\u000f\u0010t\u001a\u0004\u0018\u00010rH\u0000¢\u0006\u0002\buJ\u000f\u0010v\u001a\u0004\u0018\u00010rH\u0000¢\u0006\u0002\bwJ\u000f\u0010x\u001a\u0004\u0018\u00010rH\u0000¢\u0006\u0002\byJ\u000f\u0010z\u001a\u0004\u0018\u00010rH\u0000¢\u0006\u0002\b{J\u0012\u0010|\u001a\u0004\u0018\u0001002\u0006\u0010}\u001a\u00020~H\u0002J\u0013\u0010\u007f\u001a\u0004\u0018\u00010*2\u0007\u0010\u0080\u0001\u001a\u00020~H\u0002J\u001d\u0010\u0081\u0001\u001a\u0005\u0018\u00010\u0082\u0001*\u00020*2\u000b\b\u0002\u0010\u0083\u0001\u001a\u0004\u0018\u000100H\u0002R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u0000X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00020\u001aX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0012\u0010!\u001a\u00020\"X\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0012\u0010%\u001a\u00020&X\u0096\u0005¢\u0006\u0006\u001a\u0004\b'\u0010(R\u001d\u0010)\u001a\u0004\u0018\u00010*8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b+\u0010,R\u001d\u0010/\u001a\u0004\u0018\u0001008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b3\u0010.\u001a\u0004\b1\u00102R\u001d\u00104\u001a\u0004\u0018\u00010*8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b6\u0010.\u001a\u0004\b5\u0010,R\u0012\u00107\u001a\u000208X\u0096\u0005¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0012\u0010;\u001a\u00020<X\u0096\u0005¢\u0006\u0006\u001a\u0004\b=\u0010>R\u001d\u0010?\u001a\u0004\u0018\u00010*8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bA\u0010.\u001a\u0004\b@\u0010,R\u001d\u0010B\u001a\u0004\u0018\u00010*8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bD\u0010.\u001a\u0004\bC\u0010,R\u0012\u0010E\u001a\u00020FX\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0012\u0010I\u001a\u00020JX\u0096\u0005¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0018\u0010M\u001a\b\u0012\u0004\u0012\u00020O0NX\u0096\u0005¢\u0006\u0006\u001a\u0004\bP\u0010QR\u0012\u0010R\u001a\u00020SX\u0096\u0005¢\u0006\u0006\u001a\u0004\bT\u0010UR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010V\u001a\u0004\u0018\u00010*8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bX\u0010.\u001a\u0004\bW\u0010,R\u0012\u0010Y\u001a\u00020ZX\u0096\u0005¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u0012\u0010]\u001a\u00020^X\u0096\u0005¢\u0006\u0006\u001a\u0004\b_\u0010`R\u0012\u0010a\u001a\u00020bX\u0096\u0005¢\u0006\u0006\u001a\u0004\bc\u0010dR\u0012\u0010e\u001a\u00020fX\u0096\u0005¢\u0006\u0006\u001a\u0004\bg\u0010hR\u0012\u0010i\u001a\u00020jX\u0096\u0005¢\u0006\u0006\u001a\u0004\bk\u0010lR\u0012\u0010m\u001a\u00020nX\u0096\u0005¢\u0006\u0006\u001a\u0004\bo\u0010p¨\u0006\u0084\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", Constants.EXTRA_PROVIDER, "Lorg/jetbrains/kotlin/fir/backend/Fir2IrSpecialSymbolProvider;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrSpecialSymbolProvider;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "enhancedNullabilityAnnotationIrSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getEnhancedNullabilityAnnotationIrSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "enhancedNullabilityAnnotationIrSymbol$delegate", "Lkotlin/Lazy;", "extensionFunctionTypeAnnotationFirSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getExtensionFunctionTypeAnnotationFirSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "extensionFunctionTypeAnnotationFirSymbol$delegate", "extensionFunctionTypeAnnotationSymbol", "getExtensionFunctionTypeAnnotationSymbol", "extensionFunctionTypeAnnotationSymbol$delegate", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "flexibleMutabilityAnnotationSymbol", "getFlexibleMutabilityAnnotationSymbol", "flexibleMutabilityAnnotationSymbol$delegate", "flexibleNullabilityAnnotationSymbol", "getFlexibleNullabilityAnnotationSymbol", "flexibleNullabilityAnnotationSymbol$delegate", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "rawTypeAnnotationSymbol", "getRawTypeAnnotationSymbol", "rawTypeAnnotationSymbol$delegate", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "enhancedNullabilityAnnotationConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "enhancedNullabilityAnnotationConstructorCall$fir2ir", "extensionFunctionTypeAnnotationConstructorCall", "extensionFunctionTypeAnnotationConstructorCall$fir2ir", "flexibleMutabilityAnnotationConstructorCall", "flexibleMutabilityAnnotationConstructorCall$fir2ir", "flexibleNullabilityAnnotationConstructorCall", "flexibleNullabilityAnnotationConstructorCall$fir2ir", "rawTypeAnnotationConstructorCall", "rawTypeAnnotationConstructorCall$fir2ir", "regularAnnotationFirSymbolById", "id", "Lorg/jetbrains/kotlin/name/ClassId;", "specialAnnotationIrSymbolById", "classId", "toConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstructorCallImpl;", "firSymbol", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrBuiltIns implements Fir2IrComponents {
    private final Fir2IrComponents components;

    /* JADX INFO: renamed from: enhancedNullabilityAnnotationIrSymbol$delegate, reason: from kotlin metadata */
    private final Lazy enhancedNullabilityAnnotationIrSymbol;

    /* JADX INFO: renamed from: extensionFunctionTypeAnnotationFirSymbol$delegate, reason: from kotlin metadata */
    private final Lazy extensionFunctionTypeAnnotationFirSymbol;

    /* JADX INFO: renamed from: extensionFunctionTypeAnnotationSymbol$delegate, reason: from kotlin metadata */
    private final Lazy extensionFunctionTypeAnnotationSymbol;

    /* JADX INFO: renamed from: flexibleMutabilityAnnotationSymbol$delegate, reason: from kotlin metadata */
    private final Lazy flexibleMutabilityAnnotationSymbol;

    /* JADX INFO: renamed from: flexibleNullabilityAnnotationSymbol$delegate, reason: from kotlin metadata */
    private final Lazy flexibleNullabilityAnnotationSymbol;
    private final Fir2IrSpecialSymbolProvider provider;

    /* JADX INFO: renamed from: rawTypeAnnotationSymbol$delegate, reason: from kotlin metadata */
    private final Lazy rawTypeAnnotationSymbol;

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

    public Fir2IrBuiltIns(Fir2IrComponents components, Fir2IrSpecialSymbolProvider provider) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(provider, "provider");
        this.components = components;
        this.provider = provider;
        provider.initComponents(components);
        this.enhancedNullabilityAnnotationIrSymbol = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns$enhancedNullabilityAnnotationIrSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.specialAnnotationIrSymbolById(StandardClassIds.Annotations.INSTANCE.getEnhancedNullability());
            }
        });
        this.flexibleNullabilityAnnotationSymbol = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns$flexibleNullabilityAnnotationSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.specialAnnotationIrSymbolById(StandardClassIds.Annotations.INSTANCE.getFlexibleNullability());
            }
        });
        this.flexibleMutabilityAnnotationSymbol = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns$flexibleMutabilityAnnotationSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.specialAnnotationIrSymbolById(StandardClassIds.Annotations.INSTANCE.getFlexibleMutability());
            }
        });
        this.rawTypeAnnotationSymbol = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns$rawTypeAnnotationSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.specialAnnotationIrSymbolById(StandardClassIds.Annotations.INSTANCE.getRawTypeAnnotation());
            }
        });
        this.extensionFunctionTypeAnnotationFirSymbol = LazyKt.lazy(new Function0<FirRegularClassSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns$extensionFunctionTypeAnnotationFirSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirRegularClassSymbol invoke() {
                return this.this$0.regularAnnotationFirSymbolById(StandardClassIds.Annotations.INSTANCE.getExtensionFunctionType());
            }
        });
        this.extensionFunctionTypeAnnotationSymbol = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns$extensionFunctionTypeAnnotationSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                FirRegularClassSymbol extensionFunctionTypeAnnotationFirSymbol = this.this$0.getExtensionFunctionTypeAnnotationFirSymbol();
                IrClassifierSymbol symbol$default = extensionFunctionTypeAnnotationFirSymbol != null ? ConversionUtilsKt.toSymbol$default(this.this$0, extensionFunctionTypeAnnotationFirSymbol, ConversionTypeOrigin.DEFAULT, null, 4, null) : null;
                if (symbol$default instanceof IrClassSymbol) {
                    return (IrClassSymbol) symbol$default;
                }
                return null;
            }
        });
    }

    private final IrClassSymbol getEnhancedNullabilityAnnotationIrSymbol() {
        return (IrClassSymbol) this.enhancedNullabilityAnnotationIrSymbol.getValue();
    }

    public final IrConstructorCall enhancedNullabilityAnnotationConstructorCall$fir2ir() {
        IrClassSymbol enhancedNullabilityAnnotationIrSymbol = getEnhancedNullabilityAnnotationIrSymbol();
        return enhancedNullabilityAnnotationIrSymbol != null ? toConstructorCall$default(this, enhancedNullabilityAnnotationIrSymbol, null, 1, null) : null;
    }

    private final IrClassSymbol getFlexibleNullabilityAnnotationSymbol() {
        return (IrClassSymbol) this.flexibleNullabilityAnnotationSymbol.getValue();
    }

    public final IrConstructorCall flexibleNullabilityAnnotationConstructorCall$fir2ir() {
        IrClassSymbol flexibleNullabilityAnnotationSymbol = getFlexibleNullabilityAnnotationSymbol();
        return flexibleNullabilityAnnotationSymbol != null ? toConstructorCall$default(this, flexibleNullabilityAnnotationSymbol, null, 1, null) : null;
    }

    private final IrClassSymbol getFlexibleMutabilityAnnotationSymbol() {
        return (IrClassSymbol) this.flexibleMutabilityAnnotationSymbol.getValue();
    }

    public final IrConstructorCall flexibleMutabilityAnnotationConstructorCall$fir2ir() {
        IrClassSymbol flexibleMutabilityAnnotationSymbol = getFlexibleMutabilityAnnotationSymbol();
        return flexibleMutabilityAnnotationSymbol != null ? toConstructorCall$default(this, flexibleMutabilityAnnotationSymbol, null, 1, null) : null;
    }

    private final IrClassSymbol getRawTypeAnnotationSymbol() {
        return (IrClassSymbol) this.rawTypeAnnotationSymbol.getValue();
    }

    public final IrConstructorCall rawTypeAnnotationConstructorCall$fir2ir() {
        IrClassSymbol rawTypeAnnotationSymbol = getRawTypeAnnotationSymbol();
        return rawTypeAnnotationSymbol != null ? toConstructorCall$default(this, rawTypeAnnotationSymbol, null, 1, null) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirRegularClassSymbol getExtensionFunctionTypeAnnotationFirSymbol() {
        return (FirRegularClassSymbol) this.extensionFunctionTypeAnnotationFirSymbol.getValue();
    }

    private final IrClassSymbol getExtensionFunctionTypeAnnotationSymbol() {
        return (IrClassSymbol) this.extensionFunctionTypeAnnotationSymbol.getValue();
    }

    public final IrConstructorCall extensionFunctionTypeAnnotationConstructorCall$fir2ir() {
        IrConstructorCallImpl constructorCall;
        IrClassSymbol extensionFunctionTypeAnnotationSymbol = getExtensionFunctionTypeAnnotationSymbol();
        if (extensionFunctionTypeAnnotationSymbol != null) {
            FirRegularClassSymbol extensionFunctionTypeAnnotationFirSymbol = getExtensionFunctionTypeAnnotationFirSymbol();
            Intrinsics.checkNotNull(extensionFunctionTypeAnnotationFirSymbol);
            constructorCall = toConstructorCall(extensionFunctionTypeAnnotationSymbol, extensionFunctionTypeAnnotationFirSymbol);
        } else {
            constructorCall = null;
        }
        return constructorCall;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClassSymbol specialAnnotationIrSymbolById(ClassId classId) {
        return this.provider.getClassSymbolById(classId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirRegularClassSymbol regularAnnotationFirSymbolById(ClassId id2) {
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(getSession()).getClassLikeSymbolByClassId(id2);
        if (classLikeSymbolByClassId instanceof FirRegularClassSymbol) {
            return (FirRegularClassSymbol) classLikeSymbolByClassId;
        }
        return null;
    }

    static /* synthetic */ IrConstructorCallImpl toConstructorCall$default(Fir2IrBuiltIns fir2IrBuiltIns, IrClassSymbol irClassSymbol, FirRegularClassSymbol firRegularClassSymbol, int i, Object obj) {
        if ((i & 1) != 0) {
            firRegularClassSymbol = null;
        }
        return fir2IrBuiltIns.toConstructorCall(irClassSymbol, firRegularClassSymbol);
    }

    private final IrConstructorCallImpl toConstructorCall(IrClassSymbol irClassSymbol, FirRegularClassSymbol firRegularClassSymbol) {
        IrConstructorSymbol irConstructorSymbol;
        if (firRegularClassSymbol != null) {
            FirConstructorSymbol firConstructorSymbol = (FirConstructorSymbol) CollectionsKt.singleOrNull((List) FirScopeKt.getDeclaredConstructors(FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) firRegularClassSymbol, getSession(), getScopeSession(), true, (FirResolvePhase) null)));
            if (firConstructorSymbol == null) {
                return null;
            }
            irConstructorSymbol = getDeclarationStorage().getIrConstructorSymbol(firConstructorSymbol);
        } else {
            for (Object obj : irClassSymbol.getOwner().getDeclarations()) {
                if (obj instanceof IrConstructor) {
                    irConstructorSymbol = ((IrConstructor) obj).getSymbol();
                }
            }
            throw new NoSuchElementException("No element of given type found");
        }
        return IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, IrTypesKt.getDefaultType(irClassSymbol), irConstructorSymbol, null, 4, null);
    }
}
