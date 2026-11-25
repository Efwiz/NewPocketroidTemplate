package org.jetbrains.kotlin.fir.pipeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizedResult;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizer;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext;
import org.jetbrains.kotlin.backend.jvm.JvmIrTypeSystemContext;
import org.jetbrains.kotlin.backend.jvm.serialization.JvmIdSignatureDescriptor;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.fir.backend.DescriptorSignatureComposerStub;
import org.jetbrains.kotlin.fir.backend.Fir2IrCommonMemberStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrPluginContext;
import org.jetbrains.kotlin.fir.backend.Fir2IrResult;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.FirMangler;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.backend.jvm.FirJvmKotlinMangler;
import org.jetbrains.kotlin.fir.backend.jvm.FirJvmVisibilityConverter;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmDescriptorMangler;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmIrMangler;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.util.IdSignatureComposer;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: convertToIr.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a \u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u001a \u0010\u0004\u001a\u00020\u0005*\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u001a\u007f\u0010\r\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u001f2\u0019\b\u0002\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00050\u001f¢\u0006\u0002\b$\u001a(\u0010%\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¨\u0006&"}, d2 = {"signatureComposerForJvmFir2Ir", "Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "generateSignatures", "", "applyIrGenerationExtensions", "", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "irModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "irGenerationExtensions", "", "Lorg/jetbrains/kotlin/backend/common/extensions/IrGenerationExtension;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "convertToIrAndActualize", "Lorg/jetbrains/kotlin/fir/pipeline/Fir2IrActualizedResult;", "Lorg/jetbrains/kotlin/fir/pipeline/FirResult;", "fir2IrExtensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fir2IrConfiguration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "irGeneratorExtensions", "signatureComposer", "irMangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "firMangler", "Lorg/jetbrains/kotlin/fir/backend/FirMangler;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "kotlinBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "actualizerTypeContextProvider", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "fir2IrResultPostCompute", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrResult;", "Lkotlin/ExtensionFunctionType;", "convertToIrAndActualizeForJvm", "entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConvertToIrKt {
    public static final Fir2IrActualizedResult convertToIrAndActualizeForJvm(FirResult firResult, Fir2IrExtensions fir2IrExtensions, Fir2IrConfiguration fir2IrConfiguration, Collection<? extends IrGenerationExtension> irGeneratorExtensions) {
        Intrinsics.checkNotNullParameter(firResult, "<this>");
        Intrinsics.checkNotNullParameter(fir2IrExtensions, "fir2IrExtensions");
        Intrinsics.checkNotNullParameter(fir2IrConfiguration, "fir2IrConfiguration");
        Intrinsics.checkNotNullParameter(irGeneratorExtensions, "irGeneratorExtensions");
        return convertToIrAndActualize$default(firResult, fir2IrExtensions, fir2IrConfiguration, irGeneratorExtensions, signatureComposerForJvmFir2Ir(fir2IrConfiguration.getLinkViaSignatures()), JvmIrMangler.INSTANCE, new FirJvmKotlinMangler(), FirJvmVisibilityConverter.INSTANCE, DefaultBuiltIns.INSTANCE.getInstance(), C45091.INSTANCE, null, 512, null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.pipeline.ConvertToIrKt$convertToIrAndActualizeForJvm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: convertToIr.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C45091 extends FunctionReference implements Function1<IrBuiltIns, JvmIrTypeSystemContext> {
        public static final C45091 INSTANCE = new C45091();

        C45091() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(JvmIrTypeSystemContext.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final JvmIrTypeSystemContext invoke(IrBuiltIns p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return new JvmIrTypeSystemContext(p0);
        }
    }

    public static final IdSignatureComposer signatureComposerForJvmFir2Ir(boolean z) {
        JvmDescriptorMangler jvmDescriptorMangler = new JvmDescriptorMangler(null);
        if (z) {
            return new JvmIdSignatureDescriptor(jvmDescriptorMangler);
        }
        return new DescriptorSignatureComposerStub(jvmDescriptorMangler);
    }

    public static /* synthetic */ Fir2IrActualizedResult convertToIrAndActualize$default(FirResult firResult, Fir2IrExtensions fir2IrExtensions, Fir2IrConfiguration fir2IrConfiguration, Collection collection, IdSignatureComposer idSignatureComposer, KotlinMangler.IrMangler irMangler, FirMangler firMangler, Fir2IrVisibilityConverter fir2IrVisibilityConverter, KotlinBuiltIns kotlinBuiltIns, Function1 function1, Function1 function2, int i, Object obj) {
        return convertToIrAndActualize(firResult, fir2IrExtensions, fir2IrConfiguration, collection, idSignatureComposer, irMangler, firMangler, fir2IrVisibilityConverter, kotlinBuiltIns, function1, (i & 512) != 0 ? new Function1<Fir2IrResult, Unit>() { // from class: org.jetbrains.kotlin.fir.pipeline.ConvertToIrKt.convertToIrAndActualize.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Fir2IrResult fir2IrResult) {
                Intrinsics.checkNotNullParameter(fir2IrResult, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Fir2IrResult fir2IrResult) {
                invoke2(fir2IrResult);
                return Unit.INSTANCE;
            }
        } : function2);
    }

    public static final Fir2IrActualizedResult convertToIrAndActualize(FirResult firResult, Fir2IrExtensions fir2IrExtensions, Fir2IrConfiguration fir2IrConfiguration, Collection<? extends IrGenerationExtension> irGeneratorExtensions, IdSignatureComposer signatureComposer, KotlinMangler.IrMangler irMangler, FirMangler firMangler, Fir2IrVisibilityConverter visibilityConverter, KotlinBuiltIns kotlinBuiltIns, Function1<? super IrBuiltIns, ? extends IrTypeSystemContext> actualizerTypeContextProvider, Function1<? super Fir2IrResult, Unit> fir2IrResultPostCompute) {
        Fir2IrResult fir2IrResultConvertToIr;
        Intrinsics.checkNotNullParameter(firResult, "<this>");
        Intrinsics.checkNotNullParameter(fir2IrExtensions, "fir2IrExtensions");
        Intrinsics.checkNotNullParameter(fir2IrConfiguration, "fir2IrConfiguration");
        Intrinsics.checkNotNullParameter(irGeneratorExtensions, "irGeneratorExtensions");
        Intrinsics.checkNotNullParameter(signatureComposer, "signatureComposer");
        Intrinsics.checkNotNullParameter(irMangler, "irMangler");
        Intrinsics.checkNotNullParameter(firMangler, "firMangler");
        Intrinsics.checkNotNullParameter(visibilityConverter, "visibilityConverter");
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "kotlinBuiltIns");
        Intrinsics.checkNotNullParameter(actualizerTypeContextProvider, "actualizerTypeContextProvider");
        Intrinsics.checkNotNullParameter(fir2IrResultPostCompute, "fir2IrResultPostCompute");
        Fir2IrCommonMemberStorage fir2IrCommonMemberStorage = new Fir2IrCommonMemberStorage(signatureComposer, firMangler);
        int size = firResult.getOutputs().size();
        if (size == 0) {
            throw new IllegalStateException("No modules found".toString());
        }
        IrActualizedResult irActualizedResultActualize = null;
        IrBuiltInsOverFir irBuiltIns = null;
        if (size == 1) {
            fir2IrResultConvertToIr = ((ModuleCompilerAnalyzedOutput) CollectionsKt.single((List) firResult.getOutputs())).convertToIr(fir2IrExtensions, fir2IrConfiguration, fir2IrCommonMemberStorage, null, irMangler, visibilityConverter, kotlinBuiltIns);
            fir2IrResultPostCompute.invoke(fir2IrResultConvertToIr);
        } else {
            ModuleCompilerAnalyzedOutput moduleCompilerAnalyzedOutput = (ModuleCompilerAnalyzedOutput) CollectionsKt.last((List) firResult.getOutputs());
            List listDropLast = CollectionsKt.dropLast(firResult.getOutputs(), 1);
            int i = 10;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
            Iterator it = listDropLast.iterator();
            while (it.hasNext()) {
                ArrayList arrayList2 = arrayList;
                int i2 = i;
                Fir2IrResult fir2IrResultConvertToIr2 = ((ModuleCompilerAnalyzedOutput) it.next()).convertToIr(fir2IrExtensions, fir2IrConfiguration, fir2IrCommonMemberStorage, irBuiltIns, irMangler, visibilityConverter, kotlinBuiltIns);
                fir2IrResultPostCompute.invoke(fir2IrResultConvertToIr2);
                if (irBuiltIns == null) {
                    irBuiltIns = fir2IrResultConvertToIr2.getComponents().getIrBuiltIns();
                }
                arrayList2.add(fir2IrResultConvertToIr2);
                arrayList = arrayList2;
                i = i2;
            }
            Intrinsics.checkNotNull(irBuiltIns);
            fir2IrResultConvertToIr = moduleCompilerAnalyzedOutput.convertToIr(fir2IrExtensions, fir2IrConfiguration, fir2IrCommonMemberStorage, irBuiltIns, irMangler, visibilityConverter, kotlinBuiltIns);
            fir2IrResultPostCompute.invoke(fir2IrResultConvertToIr);
            IrActualizer irActualizer = IrActualizer.INSTANCE;
            IrModuleFragment irModuleFragment = fir2IrResultConvertToIr.getIrModuleFragment();
            ArrayList arrayList3 = arrayList;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, i));
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                arrayList4.add(((Fir2IrResult) it2.next()).getIrModuleFragment());
            }
            irActualizedResultActualize = irActualizer.actualize(irModuleFragment, arrayList4, fir2IrConfiguration.getDiagnosticReporter(), actualizerTypeContextProvider.invoke(fir2IrResultConvertToIr.getIrModuleFragment().getIrBuiltins()), fir2IrConfiguration.getLanguageVersionSettings(), fir2IrConfiguration.getExpectActualTracker());
        }
        IrModuleFragment irModuleFragment2 = fir2IrResultConvertToIr.getIrModuleFragment();
        Fir2IrComponents components = fir2IrResultConvertToIr.getComponents();
        Fir2IrPluginContext pluginContext = fir2IrResultConvertToIr.getPluginContext();
        applyIrGenerationExtensions(components, irModuleFragment2, irGeneratorExtensions);
        return new Fir2IrActualizedResult(irModuleFragment2, components, pluginContext, irActualizedResultActualize);
    }

    public static final void applyIrGenerationExtensions(Fir2IrComponents fir2IrComponents, IrModuleFragment irModuleFragment, Collection<? extends IrGenerationExtension> irGenerationExtensions) {
        Intrinsics.checkNotNullParameter(fir2IrComponents, "<this>");
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        Intrinsics.checkNotNullParameter(irGenerationExtensions, "irGenerationExtensions");
        if (irGenerationExtensions.isEmpty()) {
            return;
        }
        applyIrGenerationExtensions(new Fir2IrPluginContext(fir2IrComponents, irModuleFragment.getDescriptor()), irModuleFragment, irGenerationExtensions);
    }

    public static final void applyIrGenerationExtensions(IrPluginContext irPluginContext, IrModuleFragment irModuleFragment, Collection<? extends IrGenerationExtension> irGenerationExtensions) {
        Intrinsics.checkNotNullParameter(irPluginContext, "<this>");
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        Intrinsics.checkNotNullParameter(irGenerationExtensions, "irGenerationExtensions");
        if (irGenerationExtensions.isEmpty()) {
            return;
        }
        Iterator<? extends IrGenerationExtension> it = irGenerationExtensions.iterator();
        while (it.hasNext()) {
            it.next().generate(irModuleFragment, irPluginContext);
        }
    }
}
