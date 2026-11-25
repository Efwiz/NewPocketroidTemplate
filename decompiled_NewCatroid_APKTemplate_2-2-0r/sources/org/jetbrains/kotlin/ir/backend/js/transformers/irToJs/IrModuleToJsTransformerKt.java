package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfo;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarationsKt;
import org.jetbrains.kotlin.ir.backend.js.export.TypeScriptFragment;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.js.backend.JsToStringGenerationVisitor;
import org.jetbrains.kotlin.js.backend.NoOpSourceLocationConsumer;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.config.SourceMapSourceEmbedding;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.sourceMap.SourceMap3Builder;
import org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer;
import org.jetbrains.kotlin.js.util.TextOutputImpl;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002\u001a\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0001\u001aJ\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001c\u001a\u00020\u00122\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u001aB\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020!2\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\""}, d2 = {"safeModuleName", "", "getSafeModuleName", "(Ljava/lang/String;)Ljava/lang/String;", "safeName", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getSafeName", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)Ljava/lang/String;", "generateMultiWrappedModuleBody", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;", "mainModuleName", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgram;", "sourceMapsInfo", "Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;", "relativeRequirePath", "", "outJsProgram", "generateProxyIrModuleWith", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "externalName", "generateSingleWrappedModuleBody", "moduleName", "fragments", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "generateCallToMain", "crossModuleReferences", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "generateWrappedModuleBody", "granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrModuleToJsTransformerKt {

    /* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JsGenerationGranularity.values().length];
            try {
                iArr[JsGenerationGranularity.WHOLE_PROGRAM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JsGenerationGranularity.PER_FILE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JsGenerationGranularity.PER_MODULE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final String getSafeModuleName(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (StringsKt.startsWith$default((CharSequence) str, '<', false, 2, (Object) null)) {
            str = str.substring(1);
            Intrinsics.checkNotNullExpressionValue(str, "substring(...)");
        }
        if (StringsKt.endsWith$default((CharSequence) str, '>', false, 2, (Object) null)) {
            str = str.substring(0, str.length() - 1);
            Intrinsics.checkNotNullExpressionValue(str, "substring(...)");
        }
        return NameTablesKt.sanitizeName("kotlin_" + str, false);
    }

    public static final String getSafeName(IrModuleFragment irModuleFragment) {
        Intrinsics.checkNotNullParameter(irModuleFragment, "<this>");
        String strAsString = irModuleFragment.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return getSafeModuleName(strAsString);
    }

    public static final JsIrModule generateProxyIrModuleWith(String safeName, String externalName) {
        Intrinsics.checkNotNullParameter(safeName, "safeName");
        Intrinsics.checkNotNullParameter(externalName, "externalName");
        return new JsIrModule(safeName, externalName, CollectionsKt.listOf(new JsIrProgramFragment(safeName, "<proxy-file>")), null, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CompilationOutputsBuilt generateWrappedModuleBody(JsGenerationGranularity jsGenerationGranularity, String str, ModuleKind moduleKind, JsIrProgram jsIrProgram, SourceMapsInfo sourceMapsInfo, boolean z, boolean z2) {
        int i = WhenMappings.$EnumSwitchMapping$0[jsGenerationGranularity.ordinal()];
        if (i == 1) {
            return generateSingleWrappedModuleBody$default(str, moduleKind, jsIrProgram.asFragments(), sourceMapsInfo, true, null, z2, 32, null);
        }
        if (i == 2 || i == 3) {
            return generateMultiWrappedModuleBody(str, moduleKind, jsIrProgram, sourceMapsInfo, z, z2);
        }
        throw new NoWhenBranchMatchedException();
    }

    private static final CompilationOutputsBuilt generateMultiWrappedModuleBody(String str, ModuleKind moduleKind, JsIrProgram jsIrProgram, SourceMapsInfo sourceMapsInfo, boolean z, boolean z2) {
        List mutableList = CollectionsKt.toMutableList((Collection) jsIrProgram.asCrossModuleDependencies(moduleKind, z));
        Pair pair = (Pair) CollectionsKt.removeLast(mutableList);
        JsIrModule jsIrModule = (JsIrModule) pair.component1();
        CompilationOutputsBuilt compilationOutputsBuiltGenerateSingleWrappedModuleBody = generateSingleWrappedModuleBody(str, moduleKind, jsIrModule.getFragments(), sourceMapsInfo, true, (CrossModuleReferences) pair.component2(), z2);
        List listCreateListBuilder = CollectionsKt.createListBuilder(mutableList.size());
        while (!mutableList.isEmpty()) {
            Pair pair2 = (Pair) CollectionsKt.removeFirst(mutableList);
            JsIrModule jsIrModule2 = (JsIrModule) pair2.component1();
            CrossModuleReferences crossModuleReferences = (CrossModuleReferences) pair2.component2();
            String externalModuleName = jsIrModule2.getExternalModuleName();
            listCreateListBuilder.add(TuplesKt.to(externalModuleName, generateSingleWrappedModuleBody(externalModuleName, moduleKind, jsIrModule2.getFragments(), sourceMapsInfo, false, crossModuleReferences, z2)));
        }
        compilationOutputsBuiltGenerateSingleWrappedModuleBody.setDependencies(CollectionsKt.build(listCreateListBuilder));
        return compilationOutputsBuiltGenerateSingleWrappedModuleBody;
    }

    public static /* synthetic */ CompilationOutputsBuilt generateSingleWrappedModuleBody$default(String str, ModuleKind moduleKind, List list, SourceMapsInfo sourceMapsInfo, boolean z, CrossModuleReferences crossModuleReferences, boolean z2, int i, Object obj) {
        if ((i & 32) != 0) {
            crossModuleReferences = CrossModuleReferences.INSTANCE.Empty(moduleKind);
        }
        CrossModuleReferences crossModuleReferences2 = crossModuleReferences;
        if ((i & 64) != 0) {
            z2 = true;
        }
        return generateSingleWrappedModuleBody(str, moduleKind, list, sourceMapsInfo, z, crossModuleReferences2, z2);
    }

    public static final CompilationOutputsBuilt generateSingleWrappedModuleBody(String moduleName, ModuleKind moduleKind, List<JsIrProgramFragment> fragments, SourceMapsInfo sourceMapsInfo, boolean z, CrossModuleReferences crossModuleReferences, boolean z2) {
        NoOpSourceLocationConsumer sourceMapBuilderConsumer;
        SourceMap3Builder sourceMap3Builder;
        String strJoinTypeScriptFragments;
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        Intrinsics.checkNotNullParameter(crossModuleReferences, "crossModuleReferences");
        JsProgram jsProgramMerge = new Merger(moduleName, moduleKind, fragments, crossModuleReferences, true, z).merge();
        ResolveTemporaryNamesKt.resolveTemporaryNames(jsProgramMerge);
        TextOutputImpl textOutputImpl = new TextOutputImpl();
        if (sourceMapsInfo != null) {
            String sourceMapPrefix = sourceMapsInfo.getSourceMapPrefix();
            sourceMap3Builder = new SourceMap3Builder(null, new AnonymousClass1(textOutputImpl), sourceMapPrefix);
            SourceFilePathResolver sourceFilePathResolverCreate = SourceFilePathResolver.INSTANCE.create(sourceMapsInfo.getSourceRoots(), sourceMapPrefix, sourceMapsInfo.getOutputDir());
            SourceMapSourceEmbedding sourceMapContentEmbedding = sourceMapsInfo.getSourceMapContentEmbedding();
            sourceMapBuilderConsumer = new SourceMapBuilderConsumer(new File("."), sourceMap3Builder, sourceFilePathResolverCreate, sourceMapContentEmbedding == SourceMapSourceEmbedding.ALWAYS, sourceMapContentEmbedding != SourceMapSourceEmbedding.NEVER);
        } else {
            sourceMapBuilderConsumer = NoOpSourceLocationConsumer.INSTANCE;
            sourceMap3Builder = null;
        }
        jsProgramMerge.accept(new JsToStringGenerationVisitor(textOutputImpl, sourceMapBuilderConsumer));
        String string = textOutputImpl.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String strBuild = sourceMap3Builder != null ? sourceMap3Builder.build() : null;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = fragments.iterator();
        while (it.hasNext()) {
            String dts = ((JsIrProgramFragment) it.next()).getDts();
            TypeScriptFragment typeScriptFragmentM7089boximpl = dts != null ? TypeScriptFragment.m7089boximpl(dts) : null;
            if (typeScriptFragmentM7089boximpl != null) {
                arrayList.add(typeScriptFragmentM7089boximpl);
            }
        }
        ArrayList arrayList2 = arrayList;
        TypeScriptFragment typeScriptFragmentM7089boximpl2 = (arrayList2.isEmpty() || (strJoinTypeScriptFragments = ExportModelToTsDeclarationsKt.joinTypeScriptFragments(arrayList2)) == null) ? null : TypeScriptFragment.m7089boximpl(strJoinTypeScriptFragments);
        String strM7095unboximpl = typeScriptFragmentM7089boximpl2 != null ? typeScriptFragmentM7089boximpl2.m7095unboximpl() : null;
        if (!z2) {
            jsProgramMerge = null;
        }
        return new CompilationOutputsBuilt(string, strBuild, strM7095unboximpl, jsProgramMerge, null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformerKt$generateSingleWrappedModuleBody$1, reason: invalid class name */
    /* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function0<Integer> {
        AnonymousClass1(Object obj) {
            super(0, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getColumn()I";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getColumn";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(TextOutputImpl.class);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Integer invoke() {
            return Integer.valueOf(((TextOutputImpl) this.receiver).getColumn());
        }
    }
}
