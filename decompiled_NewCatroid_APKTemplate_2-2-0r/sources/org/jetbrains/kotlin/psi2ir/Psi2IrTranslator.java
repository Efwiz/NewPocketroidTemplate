package org.jetbrains.kotlin.psi2ir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.linkage.IrDeserializer;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions;
import org.jetbrains.kotlin.psi2ir.generators.ModuleGenerator;
import org.jetbrains.kotlin.psi2ir.generators.TypeTranslatorImpl;
import org.jetbrains.kotlin.psi2ir.generators.fragments.EvaluatorFragmentInfo;
import org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentContext;
import org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentModuleGenerator;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: Psi2IrTranslator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\b¢\u0006\u0002\u0010\fJ\u000e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0013J4\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\t2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 J\\\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00172\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020+0%2\u0016\b\u0002\u0010,\u001a\u0010\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020/\u0018\u00010-2\n\b\u0002\u00100\u001a\u0004\u0018\u000101J\u001a\u0010\u0007\u001a\u00020\u000b*\u00020\u00172\f\u00102\u001a\b\u0012\u0004\u0012\u00020\n03H\u0002R \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/Psi2IrTranslator;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "configuration", "Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;)V", "checkNoUnboundSymbols", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "", "", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;Lkotlin/jvm/functions/Function2;)V", "getConfiguration", "()Lorg/jetbrains/kotlin/psi2ir/Psi2IrConfiguration;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "postprocessingSteps", "Lorg/jetbrains/kotlin/utils/SmartList;", "Lorg/jetbrains/kotlin/psi2ir/Psi2IrPostprocessingStep;", "addPostprocessingStep", "step", "createGeneratorContext", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "symbolTable", "extensions", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;", "fragmentContext", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentContext;", "generateModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "context", "ktFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "linkerExtensions", "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$IrLinkerExtension;", "expectDescriptorToSymbol", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "fragmentInfo", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;", "whenDetected", "Lkotlin/Function0;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Psi2IrTranslator {
    private final Function2<SymbolTable, String, Unit> checkNoUnboundSymbols;
    private final Psi2IrConfiguration configuration;
    private final LanguageVersionSettings languageVersionSettings;
    private final SmartList<Psi2IrPostprocessingStep> postprocessingSteps;

    /* JADX WARN: Multi-variable type inference failed */
    public Psi2IrTranslator(LanguageVersionSettings languageVersionSettings, Psi2IrConfiguration configuration, Function2<? super SymbolTable, ? super String, Unit> checkNoUnboundSymbols) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(checkNoUnboundSymbols, "checkNoUnboundSymbols");
        this.languageVersionSettings = languageVersionSettings;
        this.configuration = configuration;
        this.checkNoUnboundSymbols = checkNoUnboundSymbols;
        this.postprocessingSteps = new SmartList<>();
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final Psi2IrConfiguration getConfiguration() {
        return this.configuration;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated(level = DeprecationLevel.ERROR, message = "Only for backward compatibility with older versions of IDE")
    public Psi2IrTranslator(LanguageVersionSettings languageVersionSettings, Psi2IrConfiguration configuration) {
        this(languageVersionSettings, configuration, new Function2<SymbolTable, String, Unit>() { // from class: org.jetbrains.kotlin.psi2ir.Psi2IrTranslator.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SymbolTable symbolTable, String str) {
                Intrinsics.checkNotNullParameter(symbolTable, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(str, "<anonymous parameter 1>");
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(SymbolTable symbolTable, String str) {
                invoke2(symbolTable, str);
                return Unit.INSTANCE;
            }
        });
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
    }

    public final void addPostprocessingStep(Psi2IrPostprocessingStep step) {
        Intrinsics.checkNotNullParameter(step, "step");
        this.postprocessingSteps.add(step);
    }

    public static /* synthetic */ GeneratorContext createGeneratorContext$default(Psi2IrTranslator psi2IrTranslator, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, SymbolTable symbolTable, GeneratorExtensions generatorExtensions, FragmentContext fragmentContext, int i, Object obj) {
        if ((i & 8) != 0) {
            generatorExtensions = new GeneratorExtensions();
        }
        GeneratorExtensions generatorExtensions2 = generatorExtensions;
        if ((i & 16) != 0) {
            fragmentContext = null;
        }
        return psi2IrTranslator.createGeneratorContext(moduleDescriptor, bindingContext, symbolTable, generatorExtensions2, fragmentContext);
    }

    public final GeneratorContext createGeneratorContext(ModuleDescriptor moduleDescriptor, BindingContext bindingContext, SymbolTable symbolTable, GeneratorExtensions extensions, FragmentContext fragmentContext) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        TypeTranslatorImpl typeTranslatorImpl = new TypeTranslatorImpl(symbolTable, this.languageVersionSettings, moduleDescriptor, null, false, extensions, null, this.configuration.getSkipBodies(), 88, null);
        return new GeneratorContext(this.configuration, moduleDescriptor, bindingContext, this.languageVersionSettings, symbolTable, extensions, typeTranslatorImpl, new IrBuiltInsOverDescriptors(moduleDescriptor.getBuiltIns(), typeTranslatorImpl, symbolTable), fragmentContext);
    }

    public final IrModuleFragment generateModuleFragment(GeneratorContext context, Collection<? extends KtFile> ktFiles, List<? extends IrProvider> irProviders, Collection<? extends IrDeserializer.IrLinkerExtension> linkerExtensions, Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol, EvaluatorFragmentInfo fragmentInfo) {
        FragmentModuleGenerator moduleGenerator;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        Intrinsics.checkNotNullParameter(irProviders, "irProviders");
        Intrinsics.checkNotNullParameter(linkerExtensions, "linkerExtensions");
        if (fragmentInfo != null) {
            moduleGenerator = new FragmentModuleGenerator(context, fragmentInfo);
        } else {
            moduleGenerator = new ModuleGenerator(context, expectDescriptorToSymbol);
        }
        final IrModuleFragment irModuleFragmentGenerateModuleFragment = moduleGenerator.generateModuleFragment(ktFiles);
        ArrayList arrayList = new ArrayList();
        for (Object obj : irProviders) {
            if (obj instanceof IrDeserializer) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ((IrDeserializer) it.next()).init(irModuleFragmentGenerateModuleFragment, linkerExtensions);
        }
        moduleGenerator.generateUnboundSymbolsAsDependencies(irProviders);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            ((IrDeserializer) it2.next()).postProcess(true);
        }
        checkNoUnboundSymbols(context, new Function0<String>() { // from class: org.jetbrains.kotlin.psi2ir.Psi2IrTranslator.generateModuleFragment.3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "after generation of IR module " + irModuleFragmentGenerateModuleFragment.getName().asString();
            }
        });
        Iterator<Psi2IrPostprocessingStep> it3 = this.postprocessingSteps.iterator();
        while (it3.hasNext()) {
            it3.next().invoke(irModuleFragmentGenerateModuleFragment);
        }
        moduleGenerator.generateUnboundSymbolsAsDependencies(irProviders);
        Iterator it4 = arrayList2.iterator();
        while (it4.hasNext()) {
            ((IrDeserializer) it4.next()).postProcess(true);
        }
        checkNoUnboundSymbols(context, new Function0<String>() { // from class: org.jetbrains.kotlin.psi2ir.Psi2IrTranslator.generateModuleFragment.6
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "after applying all post-processing steps for the generated IR module " + irModuleFragmentGenerateModuleFragment.getName().asString();
            }
        });
        return irModuleFragmentGenerateModuleFragment;
    }

    private final void checkNoUnboundSymbols(GeneratorContext generatorContext, Function0<String> function0) {
        if (generatorContext.getConfiguration().getPartialLinkageEnabled()) {
            return;
        }
        this.checkNoUnboundSymbols.invoke(generatorContext.getSymbolTable(), function0.invoke());
    }
}
