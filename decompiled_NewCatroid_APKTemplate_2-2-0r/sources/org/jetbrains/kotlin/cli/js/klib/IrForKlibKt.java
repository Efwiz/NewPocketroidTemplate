package org.jetbrains.kotlin.cli.js.klib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinkerImplKt;
import org.jetbrains.kotlin.backend.common.lower.ExpectDeclarationRemover;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideChecker;
import org.jetbrains.kotlin.backend.common.serialization.DescriptorByIdSignatureFinderImpl;
import org.jetbrains.kotlin.backend.common.serialization.ICData;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.Ir2DescriptorManglerAdapter;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.ir.backend.js.KotlinFileSerializedData;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsManglerDesc;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsManglerIr;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageConfigKt;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi2ir.Psi2IrConfiguration;
import org.jetbrains.kotlin.psi2ir.Psi2IrTranslator;
import org.jetbrains.kotlin.psi2ir.generators.DeclarationStubGeneratorImpl;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;

/* JADX INFO: compiled from: irForKlib.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000`\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u008e\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00072\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u00132\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u001c0\u001b¨\u0006\u001d"}, d2 = {"generateIrForKlibSerialization", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "analysisResult", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "sortedDependencies", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "icData", "Lorg/jetbrains/kotlin/ir/backend/js/KotlinFileSerializedData;", "expectDescriptorToSymbol", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "verifySignatures", "", "getDescriptorByLibrary", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "cli-js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrForKlibKt {
    public static final Pair<IrModuleFragment, IrPluginContext> generateIrForKlibSerialization(Project project, List<? extends KtFile> files, CompilerConfiguration configuration, AnalysisResult analysisResult, Collection<? extends KotlinLibrary> sortedDependencies, List<KotlinFileSerializedData> icData, Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol, IrFactory irFactory, boolean z, Function1<? super KotlinLibrary, ? extends ModuleDescriptor> getDescriptorByLibrary) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(analysisResult, "analysisResult");
        Intrinsics.checkNotNullParameter(sortedDependencies, "sortedDependencies");
        Intrinsics.checkNotNullParameter(icData, "icData");
        Intrinsics.checkNotNullParameter(expectDescriptorToSymbol, "expectDescriptorToSymbol");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(getDescriptorByLibrary, "getDescriptorByLibrary");
        ErrorTolerancePolicy errorTolerancePolicy = (ErrorTolerancePolicy) configuration.get(JSConfigurationKeys.ERROR_TOLERANCE_POLICY);
        if (errorTolerancePolicy == null) {
            errorTolerancePolicy = ErrorTolerancePolicy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNull(errorTolerancePolicy);
        IrMessageLogger.None none = (IrMessageLogger) configuration.get(IrMessageLogger.INSTANCE.getIR_MESSAGE_LOGGER());
        if (none == null) {
            none = IrMessageLogger.None.INSTANCE;
        }
        Intrinsics.checkNotNull(none);
        SymbolTable symbolTable = new SymbolTable(new IdSignatureDescriptor(JsManglerDesc.INSTANCE), irFactory, null, 4, null);
        GeneratorContext generatorContextCreateGeneratorContext$default = Psi2IrTranslator.createGeneratorContext$default(new Psi2IrTranslator(CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), new Psi2IrConfiguration(errorTolerancePolicy.getAllowErrors(), PartialLinkageConfigKt.getPartialLinkageConfig(configuration).isEnabled(), false, 4, null), new IrForKlibKt$generateIrForKlibSerialization$psi2Ir$1(none)), analysisResult.getModuleDescriptor(), analysisResult.getBindingContext(), symbolTable, null, null, 24, null);
        IrBuiltIns irBuiltIns = generatorContextCreateGeneratorContext$default.getIrBuiltIns();
        JsIrLinker.JsFePluginContext jsFePluginContext = new JsIrLinker.JsFePluginContext(generatorContextCreateGeneratorContext$default.getModuleDescriptor(), symbolTable, generatorContextCreateGeneratorContext$default.getTypeTranslator(), irBuiltIns);
        DeclarationStubGeneratorImpl declarationStubGeneratorImpl = new DeclarationStubGeneratorImpl(generatorContextCreateGeneratorContext$default.getModuleDescriptor(), symbolTable, irBuiltIns, new DescriptorByIdSignatureFinderImpl(generatorContextCreateGeneratorContext$default.getModuleDescriptor(), JsManglerDesc.INSTANCE, null, 4, null), null, 16, null);
        ModuleDescriptor moduleDescriptor = generatorContextCreateGeneratorContext$default.getModuleDescriptor();
        IrBuiltIns irBuiltIns2 = generatorContextCreateGeneratorContext$default.getIrBuiltIns();
        SymbolTable symbolTable2 = generatorContextCreateGeneratorContext$default.getSymbolTable();
        PartialLinkageSupportForLinker partialLinkageSupportForLinkerCreatePartialLinkageSupportForLinker = PartialLinkageSupportForLinkerImplKt.createPartialLinkageSupportForLinker(PartialLinkageConfigKt.getPartialLinkageConfig(configuration), errorTolerancePolicy.getAllowErrors(), generatorContextCreateGeneratorContext$default.getIrBuiltIns(), none);
        JsIrLinker.JsFePluginContext jsFePluginContext2 = jsFePluginContext;
        List<KotlinFileSerializedData> list = icData;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((KotlinFileSerializedData) it.next()).getIrData());
        }
        DeclarationStubGeneratorImpl declarationStubGeneratorImpl2 = declarationStubGeneratorImpl;
        JsIrLinker jsIrLinker = new JsIrLinker(moduleDescriptor, none, irBuiltIns2, symbolTable2, partialLinkageSupportForLinkerCreatePartialLinkageSupportForLinker, jsFePluginContext2, new ICData(arrayList, errorTolerancePolicy.getAllowErrors()), null, declarationStubGeneratorImpl2, 128, null);
        Collection<? extends KotlinLibrary> collection = sortedDependencies;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (KotlinLibrary kotlinLibrary : collection) {
            arrayList2.add(jsIrLinker.deserializeOnlyHeaderModule(getDescriptorByLibrary.invoke(kotlinLibrary), kotlinLibrary));
        }
        Pair<IrModuleFragment, IrPluginContext> pairGenerateModuleFragmentWithPlugins = KlibKt.generateModuleFragmentWithPlugins(generatorContextCreateGeneratorContext$default, project, files, jsIrLinker, none, expectDescriptorToSymbol, declarationStubGeneratorImpl2);
        IrModuleFragment irModuleFragmentComponent1 = pairGenerateModuleFragmentWithPlugins.component1();
        IrPluginContext irPluginContextComponent2 = pairGenerateModuleFragmentWithPlugins.component2();
        if (z) {
            IrVisitorsKt.acceptVoid(irModuleFragmentComponent1, new ManglerChecker(new KotlinMangler[]{JsManglerIr.INSTANCE, new Ir2DescriptorManglerAdapter(JsManglerDesc.INSTANCE)}, null, 2, null));
        }
        if (configuration.getBoolean(JSConfigurationKeys.FAKE_OVERRIDE_VALIDATOR)) {
            FakeOverrideChecker fakeOverrideChecker = new FakeOverrideChecker(JsManglerIr.INSTANCE, JsManglerDesc.INSTANCE);
            Iterator<T> it2 = jsIrLinker.getModules().iterator();
            while (it2.hasNext()) {
                fakeOverrideChecker.check((IrModuleFragment) it2.next());
            }
        }
        if (!Intrinsics.areEqual(configuration.get(CommonConfigurationKeys.EXPECT_ACTUAL_LINKER), (Object) true)) {
            irModuleFragmentComponent1.accept(new ExpectDeclarationRemover(generatorContextCreateGeneratorContext$default.getSymbolTable(), false), null);
        }
        return TuplesKt.to(irModuleFragmentComponent1, irPluginContextComponent2);
    }
}
