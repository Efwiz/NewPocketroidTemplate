package org.jetbrains.kotlin.backend.common.extensions;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
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
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: IrPluginContextImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001:\u0001YBO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J\u0010\u00106\u001a\u00020\u00112\u0006\u00107\u001a\u000208H\u0016J\u0012\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010;\u001a\u00020<H\u0016J\u0012\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010=\u001a\u00020>H\u0016J\u0016\u0010?\u001a\b\u0012\u0004\u0012\u00020A0@2\u0006\u0010;\u001a\u00020<H\u0016J\u0016\u0010?\u001a\b\u0012\u0004\u0012\u00020A0@2\u0006\u0010B\u001a\u00020>H\u0016J\u0016\u0010C\u001a\b\u0012\u0004\u0012\u00020D0@2\u0006\u0010E\u001a\u00020FH\u0016J\u0016\u0010C\u001a\b\u0012\u0004\u0012\u00020D0@2\u0006\u0010=\u001a\u00020>H\u0016J\u0016\u0010G\u001a\b\u0012\u0004\u0012\u00020H0@2\u0006\u0010E\u001a\u00020FH\u0016J\u0016\u0010G\u001a\b\u0012\u0004\u0012\u00020H0@2\u0006\u0010=\u001a\u00020>H\u0016J\"\u0010I\u001a\u0004\u0018\u00010J2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020N2\u0006\u0010'\u001a\u00020\u0003H\u0016J\u0012\u0010O\u001a\u0004\u0018\u00010P2\u0006\u0010;\u001a\u00020<H\u0016J\u0012\u0010O\u001a\u0004\u0018\u00010P2\u0006\u0010=\u001a\u00020>H\u0016J\u0012\u0010Q\u001a\u0004\u0018\u00010R2\u0006\u0010=\u001a\u00020>H\u0002J7\u0010S\u001a\u0004\u0018\u0001HT\"\b\b\u0000\u0010T*\u00020J2\u0006\u0010=\u001a\u00020>2\u0014\u0010U\u001a\u0010\u0012\u0004\u0012\u00020R\u0012\u0006\u0012\u0004\u0018\u0001HT0VH\u0002¢\u0006\u0002\u0010WJ:\u0010X\u001a\b\u0012\u0004\u0012\u0002HT0@\"\b\b\u0000\u0010T*\u00020J2\u0006\u0010=\u001a\u00020>2\u0018\u0010U\u001a\u0014\u0012\u0004\u0012\u00020R\u0012\n\u0012\b\u0012\u0004\u0012\u0002HT0@0VH\u0002R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\u0004\u001a\u00020\u00058\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010'\u001a\u00020\u0003X\u0096\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b(\u0010\u001e\u001a\u0004\b)\u0010*R\u0016\u0010+\u001a\u0004\u0018\u00010,X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010/\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContextImpl;", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "st", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "linker", "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer;", "diagnosticReporter", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer;Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;)V", "afterK2", "", "getAfterK2", "()Z", "annotationsRegistrar", "Lorg/jetbrains/kotlin/backend/common/extensions/IrAnnotationsFromPluginRegistrar;", "getAnnotationsRegistrar", "()Lorg/jetbrains/kotlin/backend/common/extensions/IrAnnotationsFromPluginRegistrar;", "getBindingContext$annotations", "()V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLinker", "()Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer;", "moduleDescriptor", "getModuleDescriptor$annotations", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "symbolTable", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "createDiagnosticReporter", "pluginId", "", "referenceClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "referenceConstructors", "", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "classFqn", "referenceFunctions", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "referenceProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "referenceTopLevel", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$TopLevelSymbolKind;", "referenceTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "resolveMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "resolveSymbol", ExifInterface.LATITUDE_SOUTH, "referencer", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/name/FqName;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "resolveSymbolCollection", "DummyIrAnnotationsFromPluginRegistrar", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrPluginContextImpl implements IrPluginContext {
    private final boolean afterK2;
    private final BindingContext bindingContext;
    private final IrMessageLogger diagnosticReporter;
    private final IrBuiltIns irBuiltIns;
    private final LanguageVersionSettings languageVersionSettings;
    private final IrDeserializer linker;
    private final ModuleDescriptor module;
    private final ModuleDescriptor moduleDescriptor;
    private final TargetPlatform platform;
    private final ReferenceSymbolTable st;
    private final ReferenceSymbolTable symbolTable;
    private final BuiltinSymbolsBase symbols;
    private final TypeTranslator typeTranslator;

    @Deprecated(level = DeprecationLevel.ERROR, message = "")
    public static /* synthetic */ void getBindingContext$annotations() {
    }

    public static /* synthetic */ void getModuleDescriptor$annotations() {
    }

    public IrPluginContextImpl(ModuleDescriptor module, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, ReferenceSymbolTable st, TypeTranslator typeTranslator, IrBuiltIns irBuiltIns, IrDeserializer linker, IrMessageLogger diagnosticReporter, BuiltinSymbolsBase symbols) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(st, "st");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(linker, "linker");
        Intrinsics.checkNotNullParameter(diagnosticReporter, "diagnosticReporter");
        Intrinsics.checkNotNullParameter(symbols, "symbols");
        this.module = module;
        this.bindingContext = bindingContext;
        this.languageVersionSettings = languageVersionSettings;
        this.st = st;
        this.typeTranslator = typeTranslator;
        this.irBuiltIns = irBuiltIns;
        this.linker = linker;
        this.diagnosticReporter = diagnosticReporter;
        this.symbols = symbols;
        this.platform = module.getPlatform();
        this.moduleDescriptor = module;
        this.symbolTable = st;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContext
    public IrFactory getIrFactory() {
        return IrPluginContext.DefaultImpls.getIrFactory(this);
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public BindingContext getBindingContext() {
        return this.bindingContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContextInterface
    public IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final IrDeserializer getLinker() {
        return this.linker;
    }

    public /* synthetic */ IrPluginContextImpl(ModuleDescriptor moduleDescriptor, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, ReferenceSymbolTable referenceSymbolTable, TypeTranslator typeTranslator, IrBuiltIns irBuiltIns, IrDeserializer irDeserializer, IrMessageLogger irMessageLogger, BuiltinSymbolsBase builtinSymbolsBase, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, bindingContext, languageVersionSettings, referenceSymbolTable, typeTranslator, irBuiltIns, irDeserializer, irMessageLogger, (i & 256) != 0 ? new BuiltinSymbolsBase(irBuiltIns, referenceSymbolTable) : builtinSymbolsBase);
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public BuiltinSymbolsBase getSymbols() {
        return this.symbols;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public boolean getAfterK2() {
        return this.afterK2;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public TargetPlatform getPlatform() {
        return this.platform;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public ReferenceSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public final IrAnnotationsFromPluginRegistrar getAnnotationsRegistrar() {
        return DummyIrAnnotationsFromPluginRegistrar.INSTANCE;
    }

    private final MemberScope resolveMemberScope(FqName fqName) {
        PackageViewDescriptor packageViewDescriptor = this.module.getPackage(fqName);
        if (fqName.isRoot() || !packageViewDescriptor.getFragments().isEmpty()) {
            return packageViewDescriptor.getMemberScope();
        }
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        MemberScope memberScopeResolveMemberScope = resolveMemberScope(fqNameParent);
        if (memberScopeResolveMemberScope == null) {
            return null;
        }
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        ClassifierDescriptor contributedClassifier = memberScopeResolveMemberScope.mo7465getContributedClassifier(nameShortName, NoLookupLocation.FROM_BACKEND);
        ClassDescriptor classDescriptor = contributedClassifier instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier : null;
        if (classDescriptor == null) {
            return null;
        }
        return classDescriptor.getUnsubstitutedMemberScope();
    }

    private final <S extends IrSymbol> S resolveSymbol(FqName fqName, Function1<? super MemberScope, ? extends S> referencer) {
        S sInvoke;
        MemberScope memberScopeResolveMemberScope = resolveMemberScope(fqName);
        if (memberScopeResolveMemberScope == null || (sInvoke = referencer.invoke(memberScopeResolveMemberScope)) == null) {
            return null;
        }
        if (sInvoke.isBound()) {
            return sInvoke;
        }
        this.linker.getDeclaration(sInvoke);
        this.linker.postProcess(false);
        return sInvoke;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrMessageLogger createDiagnosticReporter(final String pluginId) {
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        return new IrMessageLogger() { // from class: org.jetbrains.kotlin.backend.common.extensions.IrPluginContextImpl.createDiagnosticReporter.1
            @Override // org.jetbrains.kotlin.ir.util.IrMessageLogger
            public void report(IrMessageLogger.Severity severity, String message, IrMessageLogger.Location location) {
                Intrinsics.checkNotNullParameter(severity, "severity");
                Intrinsics.checkNotNullParameter(message, "message");
                IrPluginContextImpl.this.diagnosticReporter.report(severity, "[Plugin " + pluginId + "] " + message, location);
            }
        };
    }

    private final <S extends IrSymbol> Collection<S> resolveSymbolCollection(FqName fqName, Function1<? super MemberScope, ? extends Collection<? extends S>> referencer) {
        MemberScope memberScopeResolveMemberScope = resolveMemberScope(fqName);
        if (memberScopeResolveMemberScope == null) {
            return CollectionsKt.emptyList();
        }
        Collection<? extends S> collectionInvoke = referencer.invoke(memberScopeResolveMemberScope);
        Iterator<T> it = collectionInvoke.iterator();
        while (it.hasNext()) {
            IrSymbol irSymbol = (IrSymbol) it.next();
            if (!irSymbol.isBound()) {
                this.linker.getDeclaration(irSymbol);
            }
        }
        this.linker.postProcess(false);
        return collectionInvoke;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrClassSymbol referenceClass(final FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        fqName.isRoot();
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        return (IrClassSymbol) resolveSymbol(fqNameParent, new Function1<MemberScope, IrClassSymbol>() { // from class: org.jetbrains.kotlin.backend.common.extensions.IrPluginContextImpl.referenceClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClassSymbol invoke(MemberScope scope) {
                Intrinsics.checkNotNullParameter(scope, "scope");
                Name nameShortName = fqName.shortName();
                Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
                ClassifierDescriptor contributedClassifier = scope.mo7465getContributedClassifier(nameShortName, NoLookupLocation.FROM_BACKEND);
                ClassDescriptor classDescriptor = contributedClassifier instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier : null;
                if (classDescriptor != null) {
                    return this.st.getDescriptorExtension().referenceClass(classDescriptor);
                }
                return null;
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrTypeAliasSymbol referenceTypeAlias(final FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        fqName.isRoot();
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        return (IrTypeAliasSymbol) resolveSymbol(fqNameParent, new Function1<MemberScope, IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.backend.common.extensions.IrPluginContextImpl.referenceTypeAlias.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeAliasSymbol invoke(MemberScope scope) {
                Intrinsics.checkNotNullParameter(scope, "scope");
                Name nameShortName = fqName.shortName();
                Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
                ClassifierDescriptor contributedClassifier = scope.mo7465getContributedClassifier(nameShortName, NoLookupLocation.FROM_BACKEND);
                TypeAliasDescriptor typeAliasDescriptor = contributedClassifier instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) contributedClassifier : null;
                if (typeAliasDescriptor != null) {
                    return this.st.getDescriptorExtension().referenceTypeAlias(typeAliasDescriptor);
                }
                return null;
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrConstructorSymbol> referenceConstructors(FqName classFqn) {
        Intrinsics.checkNotNullParameter(classFqn, "classFqn");
        IrClassSymbol irClassSymbolReferenceClass = referenceClass(classFqn);
        if (irClassSymbolReferenceClass == null) {
            throw new IllegalStateException(("Cannot find class " + classFqn).toString());
        }
        List<IrDeclaration> declarations = irClassSymbolReferenceClass.getOwner().getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrConstructor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((IrConstructor) it.next()).getSymbol());
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrSimpleFunctionSymbol> referenceFunctions(final FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        fqName.isRoot();
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        return resolveSymbolCollection(fqNameParent, new Function1<MemberScope, Collection<? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.backend.common.extensions.IrPluginContextImpl.referenceFunctions.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<IrSimpleFunctionSymbol> invoke(MemberScope scope) {
                Intrinsics.checkNotNullParameter(scope, "scope");
                Name nameShortName = fqName.shortName();
                Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
                Collection<? extends SimpleFunctionDescriptor> contributedFunctions = scope.getContributedFunctions(nameShortName, NoLookupLocation.FROM_BACKEND);
                IrPluginContextImpl irPluginContextImpl = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedFunctions, 10));
                Iterator<T> it = contributedFunctions.iterator();
                while (it.hasNext()) {
                    arrayList.add(irPluginContextImpl.st.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) it.next()));
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrPropertySymbol> referenceProperties(final FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        fqName.isRoot();
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        return resolveSymbolCollection(fqNameParent, new Function1<MemberScope, Collection<? extends IrPropertySymbol>>() { // from class: org.jetbrains.kotlin.backend.common.extensions.IrPluginContextImpl.referenceProperties.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<IrPropertySymbol> invoke(MemberScope scope) {
                Intrinsics.checkNotNullParameter(scope, "scope");
                Name nameShortName = fqName.shortName();
                Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
                Collection<? extends PropertyDescriptor> contributedVariables = scope.getContributedVariables(nameShortName, NoLookupLocation.FROM_BACKEND);
                IrPluginContextImpl irPluginContextImpl = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedVariables, 10));
                Iterator<T> it = contributedVariables.iterator();
                while (it.hasNext()) {
                    arrayList.add(irPluginContextImpl.st.getDescriptorExtension().referenceProperty((PropertyDescriptor) it.next()));
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrClassSymbol referenceClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FqName fqNameAsSingleFqName = classId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        return referenceClass(fqNameAsSingleFqName);
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrTypeAliasSymbol referenceTypeAlias(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FqName fqNameAsSingleFqName = classId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        return referenceTypeAlias(fqNameAsSingleFqName);
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrConstructorSymbol> referenceConstructors(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FqName fqNameAsSingleFqName = classId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        return referenceConstructors(fqNameAsSingleFqName);
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrSimpleFunctionSymbol> referenceFunctions(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        return referenceFunctions(callableId.asSingleFqName());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public Collection<IrPropertySymbol> referenceProperties(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        return referenceProperties(callableId.asSingleFqName());
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
    public IrSymbol referenceTopLevel(IdSignature signature, IrDeserializer.TopLevelSymbolKind kind, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        IrDeserializer irDeserializer = this.linker;
        Name name2 = moduleDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        IrSymbol irSymbolResolveBySignatureInModule = irDeserializer.resolveBySignatureInModule(signature, kind, name2);
        this.linker.postProcess(false);
        return irSymbolResolveBySignatureInModule;
    }

    /* JADX INFO: compiled from: IrPluginContextImpl.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContextImpl$DummyIrAnnotationsFromPluginRegistrar;", "Lorg/jetbrains/kotlin/backend/common/extensions/IrAnnotationsFromPluginRegistrar;", "()V", "addMetadataVisibleAnnotationsToElement", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DummyIrAnnotationsFromPluginRegistrar extends IrAnnotationsFromPluginRegistrar {
        public static final DummyIrAnnotationsFromPluginRegistrar INSTANCE = new DummyIrAnnotationsFromPluginRegistrar();

        private DummyIrAnnotationsFromPluginRegistrar() {
        }

        @Override // org.jetbrains.kotlin.backend.common.extensions.IrAnnotationsFromPluginRegistrar
        public void addMetadataVisibleAnnotationsToElement(IrDeclaration declaration, List<? extends IrConstructorCall> annotations) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            declaration.setAnnotations(CollectionsKt.plus((Collection) declaration.getAnnotations(), (Iterable) annotations));
        }
    }
}
