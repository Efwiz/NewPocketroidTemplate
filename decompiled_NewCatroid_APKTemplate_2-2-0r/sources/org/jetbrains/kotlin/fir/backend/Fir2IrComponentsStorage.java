package org.jetbrains.kotlin.fir.backend;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: Fir2IrComponentsStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u001eX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020$X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020*X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u001a\u00101\u001a\u000202X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001a\u00107\u001a\u000208X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010=\u001a\u00020>X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u001a\u0010E\u001a\u00020FX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u001a\u0010K\u001a\u00020LX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010RR \u0010S\u001a\b\u0012\u0004\u0012\u00020U0TX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u0014\u0010Z\u001a\u00020[8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\\\u0010]R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b^\u0010_R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010aR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bb\u0010cR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bd\u0010eR\u001a\u0010f\u001a\u00020gX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u001a\u0010l\u001a\u00020mX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010o\"\u0004\bp\u0010q¨\u0006r"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponentsStorage;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "setAnnotationGenerator", "(Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;)V", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "setAnnotationsFromPluginRegistrar", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;)V", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "setBuiltIns", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;)V", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "setCallGenerator", "(Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;)V", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "setClassifierStorage", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;)V", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "setConverter", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;)V", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "setDeclarationStorage", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;)V", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "setDelegatedMemberGenerator", "(Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;)V", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "setFakeOverrideGenerator", "(Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;)V", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "setIrBuiltIns", "(Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;)V", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "setIrProviders", "(Ljava/util/List;)V", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "setTypeConverter", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;)V", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "setVisibilityConverter", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;)V", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrComponentsStorage implements Fir2IrComponents {
    public AnnotationGenerator annotationGenerator;
    public Fir2IrAnnotationsFromPluginRegistrar annotationsFromPluginRegistrar;
    public Fir2IrBuiltIns builtIns;
    public CallAndReferenceGenerator callGenerator;
    public Fir2IrClassifierStorage classifierStorage;
    private final Fir2IrConfiguration configuration;
    public Fir2IrConverter converter;
    public Fir2IrDeclarationStorage declarationStorage;
    public DelegatedMemberGenerator delegatedMemberGenerator;
    private final Fir2IrExtensions extensions;
    public FakeOverrideGenerator fakeOverrideGenerator;
    public IrBuiltInsOverFir irBuiltIns;
    private final IrFactory irFactory;
    public List<? extends IrProvider> irProviders;
    private final ScopeSession scopeSession;
    private final FirSession session;
    private final FirBasedSignatureComposer signatureComposer;
    private final SymbolTable symbolTable;
    public Fir2IrTypeConverter typeConverter;
    public Fir2IrVisibilityConverter visibilityConverter;

    public Fir2IrComponentsStorage(FirSession session, ScopeSession scopeSession, SymbolTable symbolTable, IrFactory irFactory, FirBasedSignatureComposer signatureComposer, Fir2IrExtensions extensions, Fir2IrConfiguration configuration) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(signatureComposer, "signatureComposer");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.session = session;
        this.scopeSession = scopeSession;
        this.symbolTable = symbolTable;
        this.irFactory = irFactory;
        this.signatureComposer = signatureComposer;
        this.extensions = extensions;
        this.configuration = configuration;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrFactory getIrFactory() {
        return this.irFactory;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirBasedSignatureComposer getSignatureComposer() {
        return this.signatureComposer;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrExtensions getExtensions() {
        return this.extensions;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConverter getConverter() {
        Fir2IrConverter fir2IrConverter = this.converter;
        if (fir2IrConverter != null) {
            return fir2IrConverter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("converter");
        return null;
    }

    public void setConverter(Fir2IrConverter fir2IrConverter) {
        Intrinsics.checkNotNullParameter(fir2IrConverter, "<set-?>");
        this.converter = fir2IrConverter;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrClassifierStorage getClassifierStorage() {
        Fir2IrClassifierStorage fir2IrClassifierStorage = this.classifierStorage;
        if (fir2IrClassifierStorage != null) {
            return fir2IrClassifierStorage;
        }
        Intrinsics.throwUninitializedPropertyAccessException("classifierStorage");
        return null;
    }

    public void setClassifierStorage(Fir2IrClassifierStorage fir2IrClassifierStorage) {
        Intrinsics.checkNotNullParameter(fir2IrClassifierStorage, "<set-?>");
        this.classifierStorage = fir2IrClassifierStorage;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrDeclarationStorage getDeclarationStorage() {
        Fir2IrDeclarationStorage fir2IrDeclarationStorage = this.declarationStorage;
        if (fir2IrDeclarationStorage != null) {
            return fir2IrDeclarationStorage;
        }
        Intrinsics.throwUninitializedPropertyAccessException("declarationStorage");
        return null;
    }

    public void setDeclarationStorage(Fir2IrDeclarationStorage fir2IrDeclarationStorage) {
        Intrinsics.checkNotNullParameter(fir2IrDeclarationStorage, "<set-?>");
        this.declarationStorage = fir2IrDeclarationStorage;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrBuiltInsOverFir getIrBuiltIns() {
        IrBuiltInsOverFir irBuiltInsOverFir = this.irBuiltIns;
        if (irBuiltInsOverFir != null) {
            return irBuiltInsOverFir;
        }
        Intrinsics.throwUninitializedPropertyAccessException("irBuiltIns");
        return null;
    }

    public void setIrBuiltIns(IrBuiltInsOverFir irBuiltInsOverFir) {
        Intrinsics.checkNotNullParameter(irBuiltInsOverFir, "<set-?>");
        this.irBuiltIns = irBuiltInsOverFir;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrBuiltIns getBuiltIns() {
        Fir2IrBuiltIns fir2IrBuiltIns = this.builtIns;
        if (fir2IrBuiltIns != null) {
            return fir2IrBuiltIns;
        }
        Intrinsics.throwUninitializedPropertyAccessException("builtIns");
        return null;
    }

    public void setBuiltIns(Fir2IrBuiltIns fir2IrBuiltIns) {
        Intrinsics.checkNotNullParameter(fir2IrBuiltIns, "<set-?>");
        this.builtIns = fir2IrBuiltIns;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public List<IrProvider> getIrProviders() {
        List list = this.irProviders;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("irProviders");
        return null;
    }

    public void setIrProviders(List<? extends IrProvider> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.irProviders = list;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrTypeConverter getTypeConverter() {
        Fir2IrTypeConverter fir2IrTypeConverter = this.typeConverter;
        if (fir2IrTypeConverter != null) {
            return fir2IrTypeConverter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeConverter");
        return null;
    }

    public void setTypeConverter(Fir2IrTypeConverter fir2IrTypeConverter) {
        Intrinsics.checkNotNullParameter(fir2IrTypeConverter, "<set-?>");
        this.typeConverter = fir2IrTypeConverter;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrVisibilityConverter getVisibilityConverter() {
        Fir2IrVisibilityConverter fir2IrVisibilityConverter = this.visibilityConverter;
        if (fir2IrVisibilityConverter != null) {
            return fir2IrVisibilityConverter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("visibilityConverter");
        return null;
    }

    public void setVisibilityConverter(Fir2IrVisibilityConverter fir2IrVisibilityConverter) {
        Intrinsics.checkNotNullParameter(fir2IrVisibilityConverter, "<set-?>");
        this.visibilityConverter = fir2IrVisibilityConverter;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public AnnotationGenerator getAnnotationGenerator() {
        AnnotationGenerator annotationGenerator = this.annotationGenerator;
        if (annotationGenerator != null) {
            return annotationGenerator;
        }
        Intrinsics.throwUninitializedPropertyAccessException("annotationGenerator");
        return null;
    }

    public void setAnnotationGenerator(AnnotationGenerator annotationGenerator) {
        Intrinsics.checkNotNullParameter(annotationGenerator, "<set-?>");
        this.annotationGenerator = annotationGenerator;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public CallAndReferenceGenerator getCallGenerator() {
        CallAndReferenceGenerator callAndReferenceGenerator = this.callGenerator;
        if (callAndReferenceGenerator != null) {
            return callAndReferenceGenerator;
        }
        Intrinsics.throwUninitializedPropertyAccessException("callGenerator");
        return null;
    }

    public void setCallGenerator(CallAndReferenceGenerator callAndReferenceGenerator) {
        Intrinsics.checkNotNullParameter(callAndReferenceGenerator, "<set-?>");
        this.callGenerator = callAndReferenceGenerator;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FakeOverrideGenerator getFakeOverrideGenerator() {
        FakeOverrideGenerator fakeOverrideGenerator = this.fakeOverrideGenerator;
        if (fakeOverrideGenerator != null) {
            return fakeOverrideGenerator;
        }
        Intrinsics.throwUninitializedPropertyAccessException("fakeOverrideGenerator");
        return null;
    }

    public void setFakeOverrideGenerator(FakeOverrideGenerator fakeOverrideGenerator) {
        Intrinsics.checkNotNullParameter(fakeOverrideGenerator, "<set-?>");
        this.fakeOverrideGenerator = fakeOverrideGenerator;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public DelegatedMemberGenerator getDelegatedMemberGenerator() {
        DelegatedMemberGenerator delegatedMemberGenerator = this.delegatedMemberGenerator;
        if (delegatedMemberGenerator != null) {
            return delegatedMemberGenerator;
        }
        Intrinsics.throwUninitializedPropertyAccessException("delegatedMemberGenerator");
        return null;
    }

    public void setDelegatedMemberGenerator(DelegatedMemberGenerator delegatedMemberGenerator) {
        Intrinsics.checkNotNullParameter(delegatedMemberGenerator, "<set-?>");
        this.delegatedMemberGenerator = delegatedMemberGenerator;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsFromPluginRegistrar() {
        Fir2IrAnnotationsFromPluginRegistrar fir2IrAnnotationsFromPluginRegistrar = this.annotationsFromPluginRegistrar;
        if (fir2IrAnnotationsFromPluginRegistrar != null) {
            return fir2IrAnnotationsFromPluginRegistrar;
        }
        Intrinsics.throwUninitializedPropertyAccessException("annotationsFromPluginRegistrar");
        return null;
    }

    public void setAnnotationsFromPluginRegistrar(Fir2IrAnnotationsFromPluginRegistrar fir2IrAnnotationsFromPluginRegistrar) {
        Intrinsics.checkNotNullParameter(fir2IrAnnotationsFromPluginRegistrar, "<set-?>");
        this.annotationsFromPluginRegistrar = fir2IrAnnotationsFromPluginRegistrar;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrLock getLock() {
        return getSymbolTable().getLock();
    }
}
