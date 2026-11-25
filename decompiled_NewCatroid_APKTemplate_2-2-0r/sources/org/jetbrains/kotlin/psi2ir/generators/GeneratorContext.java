package org.jetbrains.kotlin.psi2ir.generators;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.SamTypeApproximator;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.util.ConstantValueGenerator;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi2ir.Psi2IrConfiguration;
import org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentContext;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: GeneratorContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001BS\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0002\u0010\u0014Be\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0002\u0010\u0019J\u000e\u0010@\u001a\u00020\u00002\u0006\u0010A\u001a\u00020BJ\u0012\u0010C\u001a\u00020D*\u00020\u00172\u0006\u0010E\u001a\u00020\u0018R\u0014\u0010\u001a\u001a\u00020\u001bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR \u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010$\u001a\u00020%¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u00104\u001a\u000205¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0011\u00108\u001a\u000209¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "configuration", "Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "extensions", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "fragmentContext", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentContext;", "(Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentContext;)V", "callToSubstitutedDescriptorMap", "", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Ljava/util/Map;Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentContext;)V", "additionalDescriptorStorage", "Lorg/jetbrains/kotlin/psi2ir/generators/DescriptorStorageForContextReceivers;", "getAdditionalDescriptorStorage$ir_psi2ir", "()Lorg/jetbrains/kotlin/psi2ir/generators/DescriptorStorageForContextReceivers;", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getCallToSubstitutedDescriptorMap$ir_psi2ir", "()Ljava/util/Map;", "getConfiguration", "()Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;", "constantValueGenerator", "Lorg/jetbrains/kotlin/ir/util/ConstantValueGenerator;", "getConstantValueGenerator", "()Lorg/jetbrains/kotlin/ir/util/ConstantValueGenerator;", "getExtensions", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;", "getFragmentContext$ir_psi2ir", "()Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentContext;", "setFragmentContext$ir_psi2ir", "(Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentContext;)V", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "getReflectionTypes", "()Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "samTypeApproximator", "Lorg/jetbrains/kotlin/backend/common/SamTypeApproximator;", "getSamTypeApproximator", "()Lorg/jetbrains/kotlin/backend/common/SamTypeApproximator;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "createFileScopeContext", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "commitSubstituted", "", "descriptor", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GeneratorContext implements IrGeneratorContext {
    private final DescriptorStorageForContextReceivers additionalDescriptorStorage;
    private final BindingContext bindingContext;
    private final Map<IrDeclarationReference, CallableDescriptor> callToSubstitutedDescriptorMap;
    private final Psi2IrConfiguration configuration;
    private final ConstantValueGenerator constantValueGenerator;
    private final GeneratorExtensions extensions;
    private FragmentContext fragmentContext;
    private final IrBuiltIns irBuiltIns;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor moduleDescriptor;
    private final ReflectionTypes reflectionTypes;
    private final SamTypeApproximator samTypeApproximator;
    private final SymbolTable symbolTable;
    private final TypeTranslator typeTranslator;

    private GeneratorContext(Psi2IrConfiguration psi2IrConfiguration, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, SymbolTable symbolTable, GeneratorExtensions generatorExtensions, TypeTranslator typeTranslator, IrBuiltIns irBuiltIns, Map<IrDeclarationReference, CallableDescriptor> map, FragmentContext fragmentContext) {
        this.configuration = psi2IrConfiguration;
        this.moduleDescriptor = moduleDescriptor;
        this.bindingContext = bindingContext;
        this.languageVersionSettings = languageVersionSettings;
        this.symbolTable = symbolTable;
        this.extensions = generatorExtensions;
        this.typeTranslator = typeTranslator;
        this.irBuiltIns = irBuiltIns;
        this.callToSubstitutedDescriptorMap = map;
        this.fragmentContext = fragmentContext;
        this.constantValueGenerator = typeTranslator.getConstantValueGenerator();
        StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
        Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
        this.reflectionTypes = new ReflectionTypes(moduleDescriptor, new NotFoundClasses(NO_LOCKS, moduleDescriptor));
        this.additionalDescriptorStorage = new DescriptorStorageForContextReceivers();
        this.samTypeApproximator = new SamTypeApproximator(moduleDescriptor.getBuiltIns(), languageVersionSettings);
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContext
    public IrFactory getIrFactory() {
        return IrGeneratorContext.DefaultImpls.getIrFactory(this);
    }

    public final Psi2IrConfiguration getConfiguration() {
        return this.configuration;
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final GeneratorExtensions getExtensions() {
        return this.extensions;
    }

    public final TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContextInterface
    public IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final Map<IrDeclarationReference, CallableDescriptor> getCallToSubstitutedDescriptorMap$ir_psi2ir() {
        return this.callToSubstitutedDescriptorMap;
    }

    /* JADX INFO: renamed from: getFragmentContext$ir_psi2ir, reason: from getter */
    public final FragmentContext getFragmentContext() {
        return this.fragmentContext;
    }

    public final void setFragmentContext$ir_psi2ir(FragmentContext fragmentContext) {
        this.fragmentContext = fragmentContext;
    }

    public /* synthetic */ GeneratorContext(Psi2IrConfiguration psi2IrConfiguration, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, SymbolTable symbolTable, GeneratorExtensions generatorExtensions, TypeTranslator typeTranslator, IrBuiltIns irBuiltIns, FragmentContext fragmentContext, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(psi2IrConfiguration, moduleDescriptor, bindingContext, languageVersionSettings, symbolTable, generatorExtensions, typeTranslator, irBuiltIns, (i & 256) != 0 ? null : fragmentContext);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GeneratorContext(Psi2IrConfiguration configuration, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, SymbolTable symbolTable, GeneratorExtensions extensions, TypeTranslator typeTranslator, IrBuiltIns irBuiltIns, FragmentContext fragmentContext) {
        this(configuration, moduleDescriptor, bindingContext, languageVersionSettings, symbolTable, extensions, typeTranslator, irBuiltIns, new LinkedHashMap(), fragmentContext);
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
    }

    public final ConstantValueGenerator getConstantValueGenerator() {
        return this.constantValueGenerator;
    }

    public final void commitSubstituted(IrDeclarationReference irDeclarationReference, CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(irDeclarationReference, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.callToSubstitutedDescriptorMap.put(irDeclarationReference, descriptor);
    }

    public final ReflectionTypes getReflectionTypes() {
        return this.reflectionTypes;
    }

    /* JADX INFO: renamed from: getAdditionalDescriptorStorage$ir_psi2ir, reason: from getter */
    public final DescriptorStorageForContextReceivers getAdditionalDescriptorStorage() {
        return this.additionalDescriptorStorage;
    }

    public final SamTypeApproximator getSamTypeApproximator() {
        return this.samTypeApproximator;
    }

    public final GeneratorContext createFileScopeContext(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        return new GeneratorContext(this.configuration, this.moduleDescriptor, this.bindingContext, this.languageVersionSettings, this.symbolTable, this.extensions, new TypeTranslatorImpl(this.symbolTable, this.languageVersionSettings, this.moduleDescriptor, null, false, this.extensions, ktFile, this.configuration.getSkipBodies(), 24, null), getIrBuiltIns(), this.callToSubstitutedDescriptorMap, this.fragmentContext);
    }
}
