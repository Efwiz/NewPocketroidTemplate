package org.jetbrains.kotlin.backend.common.extensions;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.linkage.IrDeserializer;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
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

/* JADX INFO: compiled from: IrPluginContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H&J\u0012\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u000201H&J\u0012\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00102\u001a\u000203H'J\u0016\u00104\u001a\b\u0012\u0004\u0012\u000206052\u0006\u00100\u001a\u000201H&J\u0016\u00104\u001a\b\u0012\u0004\u0012\u000206052\u0006\u00107\u001a\u000203H'J\u0016\u00108\u001a\b\u0012\u0004\u0012\u000209052\u0006\u0010:\u001a\u00020;H&J\u0016\u00108\u001a\b\u0012\u0004\u0012\u000209052\u0006\u00102\u001a\u000203H'J\u0016\u0010<\u001a\b\u0012\u0004\u0012\u00020=052\u0006\u0010:\u001a\u00020;H&J\u0016\u0010<\u001a\b\u0012\u0004\u0012\u00020=052\u0006\u00102\u001a\u000203H'J\"\u0010>\u001a\u0004\u0018\u00010?2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010\u0014\u001a\u00020\u0015H&J\u0012\u0010D\u001a\u0004\u0018\u00010E2\u0006\u00100\u001a\u000201H&J\u0012\u0010D\u001a\u0004\u0018\u00010E2\u0006\u00102\u001a\u000203H'R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000b8&X§\u0004¢\u0006\f\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u00158&X§\u0004¢\u0006\f\u0012\u0004\b\u0016\u0010\r\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0012\u0010!\u001a\u00020\"X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&8&X§\u0004¢\u0006\f\u0012\u0004\b'\u0010\r\u001a\u0004\b(\u0010)ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006FÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "afterK2", "", "getAfterK2", "()Z", "annotationsRegistrar", "Lorg/jetbrains/kotlin/backend/common/extensions/IrAnnotationsFromPluginRegistrar;", "getAnnotationsRegistrar", "()Lorg/jetbrains/kotlin/backend/common/extensions/IrAnnotationsFromPluginRegistrar;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getBindingContext$annotations", "()V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleDescriptor$annotations", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getTypeTranslator$annotations", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "createDiagnosticReporter", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "pluginId", "", "referenceClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "referenceConstructors", "", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "classFqn", "referenceFunctions", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "referenceProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "referenceTopLevel", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$TopLevelSymbolKind;", "referenceTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrPluginContext extends IrGeneratorContext {
    @ObsoleteDescriptorBasedAPI
    @FirIncompatiblePluginAPI
    static /* synthetic */ void getBindingContext$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    static /* synthetic */ void getModuleDescriptor$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    @FirIncompatiblePluginAPI
    static /* synthetic */ void getTypeTranslator$annotations() {
    }

    IrMessageLogger createDiagnosticReporter(String pluginId);

    boolean getAfterK2();

    IrAnnotationsFromPluginRegistrar getAnnotationsRegistrar();

    BindingContext getBindingContext();

    LanguageVersionSettings getLanguageVersionSettings();

    ModuleDescriptor getModuleDescriptor();

    TargetPlatform getPlatform();

    ReferenceSymbolTable getSymbolTable();

    BuiltinSymbolsBase getSymbols();

    TypeTranslator getTypeTranslator();

    IrClassSymbol referenceClass(ClassId classId);

    @FirIncompatiblePluginAPI(hint = "Use classId overload instead")
    IrClassSymbol referenceClass(FqName fqName);

    Collection<IrConstructorSymbol> referenceConstructors(ClassId classId);

    @FirIncompatiblePluginAPI(hint = "Use classId overload instead")
    Collection<IrConstructorSymbol> referenceConstructors(FqName classFqn);

    Collection<IrSimpleFunctionSymbol> referenceFunctions(CallableId callableId);

    @FirIncompatiblePluginAPI(hint = "Use callableId overload instead")
    Collection<IrSimpleFunctionSymbol> referenceFunctions(FqName fqName);

    Collection<IrPropertySymbol> referenceProperties(CallableId callableId);

    @FirIncompatiblePluginAPI(hint = "Use callableId overload instead")
    Collection<IrPropertySymbol> referenceProperties(FqName fqName);

    IrSymbol referenceTopLevel(IdSignature signature, IrDeserializer.TopLevelSymbolKind kind, ModuleDescriptor moduleDescriptor);

    IrTypeAliasSymbol referenceTypeAlias(ClassId classId);

    @FirIncompatiblePluginAPI(hint = "Use classId overload instead")
    IrTypeAliasSymbol referenceTypeAlias(FqName fqName);

    /* JADX INFO: compiled from: IrPluginContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static IrFactory getIrFactory(IrPluginContext irPluginContext) {
            return IrGeneratorContext.DefaultImpls.getIrFactory(irPluginContext);
        }
    }
}
