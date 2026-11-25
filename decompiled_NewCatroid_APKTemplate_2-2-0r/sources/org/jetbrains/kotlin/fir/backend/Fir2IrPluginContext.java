package org.jetbrains.kotlin.fir.backend;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.extensions.FirIncompatiblePluginAPI;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext;
import org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.linkage.IrDeserializer;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: Fir2IrPluginContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000î\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 d2\u00020\u0001:\u0001dB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u0016J\u009c\u0001\u00109\u001a\b\u0012\u0004\u0012\u0002H;0:\"\f\b\u0000\u0010<*\u0006\u0012\u0002\b\u00030=\"\b\b\u0001\u0010>*\u00020?\"\n\b\u0002\u0010;\u0018\u0001*\u0002H>2\b\u0010@\u001a\u0004\u0018\u00010A2\u001d\u0010B\u001a\u0019\u0012\u0004\u0012\u00020D\u0012\n\u0012\b\u0012\u0004\u0012\u0002H<0:0C¢\u0006\u0002\bE2\u001d\u0010F\u001a\u0019\u0012\u0004\u0012\u00020%\u0012\n\u0012\b\u0012\u0004\u0012\u0002H<0:0C¢\u0006\u0002\bE2\u001f\u0010G\u001a\u001b\u0012\u0004\u0012\u00020I\u0012\u0004\u0012\u0002H<\u0012\u0006\u0012\u0004\u0018\u0001H>0H¢\u0006\u0002\bEH\u0082\bJ\u0012\u0010J\u001a\u0004\u0018\u00010K2\u0006\u0010@\u001a\u00020AH\u0016J\u0012\u0010J\u001a\u0004\u0018\u00010K2\u0006\u0010L\u001a\u00020MH\u0017JL\u0010N\u001a\u0004\u0018\u0001H;\"\u0004\b\u0000\u0010;2\u0006\u0010O\u001a\u00020A2\u0018\u0010P\u001a\u0014\u0012\u0004\u0012\u00020A\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010Q0C2\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020S\u0012\u0004\u0012\u0002H;0CH\u0082\b¢\u0006\u0002\u0010TJ\u0016\u0010U\u001a\b\u0012\u0004\u0012\u00020V0:2\u0006\u0010@\u001a\u00020AH\u0016J\u0016\u0010U\u001a\b\u0012\u0004\u0012\u00020V0:2\u0006\u0010W\u001a\u00020MH\u0017J\u0016\u0010X\u001a\b\u0012\u0004\u0012\u00020Y0:2\u0006\u0010Z\u001a\u00020[H\u0016J\u0016\u0010X\u001a\b\u0012\u0004\u0012\u00020Y0:2\u0006\u0010L\u001a\u00020MH\u0017J\u0016\u0010\\\u001a\b\u0012\u0004\u0012\u00020]0:2\u0006\u0010Z\u001a\u00020[H\u0016J\u0016\u0010\\\u001a\b\u0012\u0004\u0012\u00020]0:2\u0006\u0010L\u001a\u00020MH\u0017J\"\u0010^\u001a\u0004\u0018\u00010?2\u0006\u0010_\u001a\u00020S2\u0006\u0010`\u001a\u00020a2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0012\u0010b\u001a\u0004\u0018\u00010c2\u0006\u0010@\u001a\u00020AH\u0016J\u0012\u0010b\u001a\u0004\u0018\u00010c2\u0006\u0010L\u001a\u00020MH\u0017R\u0014\u0010\u0007\u001a\u00020\bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u00108VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\u0004\u001a\u00020\u00058\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001d\u0010\u0012\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020%8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\u00020-X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u001a\u00100\u001a\u0002018VX\u0097\u0004¢\u0006\f\u0012\u0004\b2\u0010\u0012\u001a\u0004\b3\u00104¨\u0006e"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrPluginContext;", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "afterK2", "", "getAfterK2", "()Z", "annotationsRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getBindingContext$annotations", "()V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModuleDescriptor$annotations", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getTypeTranslator$annotations", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "createDiagnosticReporter", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "pluginId", "", "referenceCallableSymbols", "", "R", "F", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getCallablesFromScope", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "Lkotlin/ExtensionFunctionType;", "getCallablesFromProvider", "irExtractor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "referenceClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "referenceClassLikeSymbol", "id", "firSymbolExtractor", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "irSymbolExtractor", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "(Lorg/jetbrains/kotlin/name/ClassId;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "referenceConstructors", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "classFqn", "referenceFunctions", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "referenceProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "referenceTopLevel", "signature", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$TopLevelSymbolKind;", "referenceTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "Companion", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrPluginContext implements IrPluginContext {
    private final boolean afterK2;
    private final Fir2IrComponents components;
    private final ModuleDescriptor moduleDescriptor;
    private final BuiltinSymbolsBase symbols;

    @ObsoleteDescriptorBasedAPI
    @FirIncompatiblePluginAPI
    public static /* synthetic */ void getBindingContext$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getModuleDescriptor$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    @FirIncompatiblePluginAPI
    public static /* synthetic */ void getTypeTranslator$annotations() {
    }

    public Fir2IrPluginContext(Fir2IrComponents components, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.components = components;
        this.moduleDescriptor = moduleDescriptor;
        this.afterK2 = true;
        this.symbols = new BuiltinSymbolsBase(getIrBuiltIns(), getSymbolTable());
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContext
    public IrFactory getIrFactory() {
        return IrPluginContext.DefaultImpls.getIrFactory(this);
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public BindingContext getBindingContext() {
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public TypeTranslator getTypeTranslator() {
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public boolean getAfterK2() {
        return this.afterK2;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public LanguageVersionSettings getLanguageVersionSettings() {
        return FirLanguageSettingsComponentKt.getLanguageVersionSettings(this.components.getSession());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public TargetPlatform getPlatform() {
        return FirModuleDataKt.getModuleData(this.components.getSession()).getPlatform();
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public ReferenceSymbolTable getSymbolTable() {
        return this.components.getSymbolTable();
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public BuiltinSymbolsBase getSymbols() {
        return this.symbols;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContextInterface
    public IrBuiltIns getIrBuiltIns() {
        return this.components.getIrBuiltIns();
    }

    private final FirSymbolProvider getSymbolProvider() {
        return FirSymbolProviderKt.getSymbolProvider(this.components.getSession());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsRegistrar() {
        return this.components.getAnnotationsFromPluginRegistrar();
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrClassSymbol referenceClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirClassLikeSymbol<?> classLikeSymbolByClassId = getSymbolProvider().getClassLikeSymbolByClassId(classId);
        FirClassSymbol<?> firClassSymbol = classLikeSymbolByClassId instanceof FirClassSymbol ? (FirClassSymbol) classLikeSymbolByClassId : null;
        if (firClassSymbol == null) {
            return null;
        }
        return this.components.getClassifierStorage().getIrClassSymbol(firClassSymbol);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrTypeAliasSymbol referenceTypeAlias(ClassId classId) {
        IdSignature idSignatureComposeSignature;
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirSymbolProvider symbolProvider = getSymbolProvider();
        ReferenceSymbolTable symbolTable = getSymbolTable();
        FirClassLikeSymbol<?> classLikeSymbolByClassId = symbolProvider.getClassLikeSymbolByClassId(classId);
        if (classLikeSymbolByClassId == null || (idSignatureComposeSignature = this.components.getSignatureComposer().composeSignature((FirClassLikeDeclaration) classLikeSymbolByClassId.getFir())) == null) {
            return null;
        }
        return symbolTable.referenceTypeAlias(idSignatureComposeSignature);
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrSimpleFunctionSymbol> referenceFunctions(CallableId callableId) {
        List<FirNamedFunctionSymbol> topLevelFunctionSymbols;
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        ClassId classId = callableId.getClassId();
        if (classId == null) {
            topLevelFunctionSymbols = getSymbolProvider().getTopLevelFunctionSymbols(callableId.getPackageName(), callableId.getCallableName());
        } else {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = getSymbolProvider().getClassLikeSymbolByClassId(classId);
            if (classLikeSymbolByClassId != null && (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(classLikeSymbolByClassId, this.components.getSession())) != null) {
                topLevelFunctionSymbols = FirScopeKt.getFunctions(FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) firRegularClassSymbolFullyExpandedClass, this.components.getSession(), this.components.getScopeSession(), true, (FirResolvePhase) null), callableId.getCallableName());
            } else {
                return CollectionsKt.emptyList();
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = topLevelFunctionSymbols.iterator();
        while (it.hasNext()) {
            IrFunctionSymbol irFunctionSymbol$default = Fir2IrDeclarationStorage.getIrFunctionSymbol$default(this.components.getDeclarationStorage(), (FirFunctionSymbol) ((FirCallableSymbol) it.next()), null, 2, null);
            if (irFunctionSymbol$default != null) {
                arrayList.add(irFunctionSymbol$default);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof IrSimpleFunctionSymbol) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrPropertySymbol> referenceProperties(CallableId callableId) {
        List<FirPropertySymbol> topLevelPropertySymbols;
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        ClassId classId = callableId.getClassId();
        if (classId == null) {
            topLevelPropertySymbols = getSymbolProvider().getTopLevelPropertySymbols(callableId.getPackageName(), callableId.getCallableName());
        } else {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = getSymbolProvider().getClassLikeSymbolByClassId(classId);
            if (classLikeSymbolByClassId != null && (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(classLikeSymbolByClassId, this.components.getSession())) != null) {
                List<FirVariableSymbol<?>> properties = FirScopeKt.getProperties(FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) firRegularClassSymbolFullyExpandedClass, this.components.getSession(), this.components.getScopeSession(), true, (FirResolvePhase) null), callableId.getCallableName());
                ArrayList arrayList = new ArrayList();
                for (Object obj : properties) {
                    if (obj instanceof FirPropertySymbol) {
                        arrayList.add(obj);
                    }
                }
                topLevelPropertySymbols = arrayList;
            } else {
                return CollectionsKt.emptyList();
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = topLevelPropertySymbols.iterator();
        while (it.hasNext()) {
            IrSymbol irPropertySymbol$default = Fir2IrDeclarationStorage.getIrPropertySymbol$default(this.components.getDeclarationStorage(), (FirPropertySymbol) ((FirCallableSymbol) it.next()), null, 2, null);
            if (irPropertySymbol$default != null) {
                arrayList2.add(irPropertySymbol$default);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (obj2 instanceof IrPropertySymbol) {
                arrayList3.add(obj2);
            }
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrMessageLogger createDiagnosticReporter(String pluginId) {
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    @FirIncompatiblePluginAPI
    public IrClassSymbol referenceClass(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    @FirIncompatiblePluginAPI
    public IrTypeAliasSymbol referenceTypeAlias(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    @FirIncompatiblePluginAPI
    public Collection<IrConstructorSymbol> referenceConstructors(FqName classFqn) {
        Intrinsics.checkNotNullParameter(classFqn, "classFqn");
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    @FirIncompatiblePluginAPI
    public Collection<IrSimpleFunctionSymbol> referenceFunctions(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    @FirIncompatiblePluginAPI
    public Collection<IrPropertySymbol> referenceProperties(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrSymbol referenceTopLevel(IdSignature signature, IrDeserializer.TopLevelSymbolKind kind, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        throw new IllegalStateException("This API is not supported for K2".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrConstructorSymbol> referenceConstructors(ClassId classId) {
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirClassLikeSymbol<?> classLikeSymbolByClassId = getSymbolProvider().getClassLikeSymbolByClassId(classId);
        if (classLikeSymbolByClassId != null && (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(classLikeSymbolByClassId, this.components.getSession())) != null) {
            List<FirConstructorSymbol> declaredConstructors = FirScopeKt.getDeclaredConstructors(FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) firRegularClassSymbolFullyExpandedClass, this.components.getSession(), this.components.getScopeSession(), true, (FirResolvePhase) null));
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = declaredConstructors.iterator();
            while (it.hasNext()) {
                IrConstructorSymbol irConstructorSymbol = this.components.getDeclarationStorage().getIrConstructorSymbol((FirConstructorSymbol) ((FirCallableSymbol) it.next()));
                if (irConstructorSymbol != null) {
                    arrayList.add(irConstructorSymbol);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                if (obj instanceof IrConstructorSymbol) {
                    arrayList2.add(obj);
                }
            }
            return arrayList2;
        }
        return CollectionsKt.emptyList();
    }
}
