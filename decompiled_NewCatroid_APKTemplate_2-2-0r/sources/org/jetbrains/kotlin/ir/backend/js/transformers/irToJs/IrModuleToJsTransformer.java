package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.serialization.FunctionalInterfacesUtilsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.ir.backend.js.CompilerResult;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.OptimizationsKt;
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfo;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGenerator;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelToJsStatements;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarationsKt;
import org.jetbrains.kotlin.ir.backend.js.export.ExportedDeclaration;
import org.jetbrains.kotlin.ir.backend.js.export.ExportedModule;
import org.jetbrains.kotlin.ir.backend.js.export.TypeScriptFragment;
import org.jetbrains.kotlin.ir.backend.js.lower.JsCodeOutliningLowering;
import org.jetbrains.kotlin.ir.backend.js.lower.MoveBodilessDeclarationsToSeparatePlaceKt;
import org.jetbrains.kotlin.ir.backend.js.lower.StaticMembersLowering;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsMainFunctionDetector;
import org.jetbrains.kotlin.ir.backend.js.utils.JsMainFunctionDetectorKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsStaticContext;
import org.jetbrains.kotlin.ir.backend.js.utils.Keeper;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTable;
import org.jetbrains.kotlin.ir.declarations.IdSignatureRetriever;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureRenderer;
import org.jetbrains.kotlin.ir.util.IdSignatureRendererKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0002WXB=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00060\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0 H\u0002J\u0016\u0010!\u001a\u00020\"2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0 H\u0002J\u001e\u0010#\u001a\u00020$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00052\u0006\u0010&\u001a\u00020'H\u0002J\u001e\u0010(\u001a\u00020$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00052\u0006\u0010&\u001a\u00020'H\u0002J&\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00052\u0006\u0010+\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020.H\u0002J*\u0010/\u001a\u0002002\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0 2\f\u00101\u001a\b\u0012\u0004\u0012\u00020'022\u0006\u00103\u001a\u00020\u000bJ\u001e\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00052\u0006\u00106\u001a\u0002072\u0006\u0010&\u001a\u00020'H\u0002J4\u00108\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002050\u0005090\u00052\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\t0;J\u001c\u0010>\u001a\u00020?2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0 2\u0006\u0010&\u001a\u00020'J\u001e\u0010@\u001a\u00020?2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00052\u0006\u0010&\u001a\u00020'H\u0002J\"\u0010A\u001a\u00020\"*\u0002052\f\u0010B\u001a\b\u0012\u0004\u0012\u00020C022\u0006\u00106\u001a\u000207H\u0002J\"\u0010D\u001a\u00020\"*\u0002052\f\u0010B\u001a\b\u0012\u0004\u0012\u00020C022\u0006\u0010E\u001a\u00020FH\u0002J\"\u0010G\u001a\u00020\"*\u0002052\f\u0010B\u001a\b\u0012\u0004\u0012\u00020C022\u0006\u0010E\u001a\u00020FH\u0002J\u001c\u0010H\u001a\u0004\u0018\u00010\u0006*\b\u0012\u0004\u0012\u00020C022\u0006\u0010I\u001a\u00020CH\u0002J\f\u0010J\u001a\u00020\u000b*\u00020<H\u0002J \u0010K\u001a\b\u0012\u0004\u0012\u0002070\u0005*\u00020L2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020<0;H\u0002J$\u0010N\u001a\u0004\u0018\u000105*\u0002072\u0006\u0010&\u001a\u00020'2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020C0PH\u0002J\u0014\u0010Q\u001a\u00020R*\u0002072\u0006\u0010S\u001a\u000205H\u0002J\u001c\u0010T\u001a\u00020R*\u0002072\u0006\u0010U\u001a\u00020\u001e2\u0006\u0010S\u001a\u000205H\u0002J\f\u0010V\u001a\u00020R*\u00020\u001eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000RL\u0010\u0016\u001a@\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00060\u0006\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00060\u0006 \u0018*\u001e\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00060\u0006\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00060\u00060\b0\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Y"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrModuleToJsTransformer;", "", "backendContext", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "mainArguments", "", "", "moduleToName", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "removeUnusedAssociatedObjects", "", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;Ljava/util/List;Ljava/util/Map;Z)V", "generateFilePaths", "generateRegionComments", "isEsModules", "mainModuleName", "moduleFragmentToNameMapper", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/ModuleFragmentToExternalName;", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "optimizeGeneratedJs", "pathPrefixMap", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "shouldGeneratePolyfills", "shouldGenerateTypeScriptDefinitions", "sourceMapInfo", "Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;", "associateIrAndExport", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrModuleToJsTransformer$IrAndExportedDeclarations;", "modules", "", "doStaticMembersLowering", "", "generateJsIrProgramPerFile", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgram;", "exportData", "mode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/TranslationMode;", "generateJsIrProgramPerModule", "generateMainArguments", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "generateArgv", "generateContinuation", "staticContext", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;", "generateModule", "Lorg/jetbrains/kotlin/ir/backend/js/CompilerResult;", "modes", "", "relativeRequirePath", "generateProgramFragment", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "fileExports", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrModuleToJsTransformer$IrFileExports;", "makeIrFragmentsGenerators", "Lkotlin/Function0;", "dirtyFiles", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "allModules", "makeJsCodeGenerator", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsCodeGenerator;", "makeJsCodeGeneratorFromIr", "computeAndSaveDefinitions", "definitions", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "computeAndSaveImports", "nameGenerator", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsNameLinkingNamer;", "computeAndSaveNameBindings", "computeTag", "declaration", "couldBeSkipped", "generateExportWithExternals", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportModelGenerator;", "irFiles", "generateProgramFragmentForExport", "nameScope", "Lorg/jetbrains/kotlin/ir/backend/js/utils/NameTable;", "toJsIrModule", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "programFragment", "toJsIrModuleForExport", "module", "toJsIrProxyModule", "IrAndExportedDeclarations", "IrFileExports", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrModuleToJsTransformer {
    private final JsIrBackendContext backendContext;
    private final boolean generateFilePaths;
    private final boolean generateRegionComments;
    private final boolean isEsModules;
    private final List<String> mainArguments;
    private final String mainModuleName;
    private final ModuleFragmentToExternalName moduleFragmentToNameMapper;
    private final ModuleKind moduleKind;
    private final boolean optimizeGeneratedJs;
    private final Map<String, String> pathPrefixMap;
    private final boolean removeUnusedAssociatedObjects;
    private final boolean shouldGeneratePolyfills;
    private final boolean shouldGenerateTypeScriptDefinitions;
    private final SourceMapsInfo sourceMapInfo;

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
                iArr[JsGenerationGranularity.PER_MODULE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JsGenerationGranularity.PER_FILE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public IrModuleToJsTransformer(JsIrBackendContext backendContext, List<String> list, Map<IrModuleFragment, String> moduleToName, boolean z) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(moduleToName, "moduleToName");
        this.backendContext = backendContext;
        this.mainArguments = list;
        this.removeUnusedAssociatedObjects = z;
        this.shouldGeneratePolyfills = backendContext.getConfiguration().getBoolean(JSConfigurationKeys.GENERATE_POLYFILLS);
        this.generateRegionComments = backendContext.getConfiguration().getBoolean(JSConfigurationKeys.GENERATE_REGION_COMMENTS);
        this.shouldGenerateTypeScriptDefinitions = backendContext.getConfiguration().getBoolean(JSConfigurationKeys.GENERATE_DTS);
        Object obj = backendContext.getConfiguration().get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        this.mainModuleName = (String) obj;
        Object obj2 = backendContext.getConfiguration().get(JSConfigurationKeys.MODULE_KIND);
        Intrinsics.checkNotNull(obj2);
        ModuleKind moduleKind = (ModuleKind) obj2;
        this.moduleKind = moduleKind;
        this.isEsModules = moduleKind == ModuleKind.ES;
        this.sourceMapInfo = SourceMapsInfo.INSTANCE.from(backendContext.getConfiguration());
        this.moduleFragmentToNameMapper = new ModuleFragmentToExternalName(moduleToName);
        this.generateFilePaths = backendContext.getConfiguration().getBoolean(JSConfigurationKeys.GENERATE_COMMENTS_WITH_FILE_PATH);
        Map<String, String> map = backendContext.getConfiguration().getMap(JSConfigurationKeys.FILE_PATHS_PREFIX_MAP);
        Intrinsics.checkNotNullExpressionValue(map, "getMap(...)");
        this.pathPrefixMap = map;
        Object obj3 = backendContext.getConfiguration().get(JSConfigurationKeys.OPTIMIZE_GENERATED_JS, true);
        Intrinsics.checkNotNullExpressionValue(obj3, "get(...)");
        this.optimizeGeneratedJs = ((Boolean) obj3).booleanValue();
    }

    public /* synthetic */ IrModuleToJsTransformer(JsIrBackendContext jsIrBackendContext, List list, Map map, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsIrBackendContext, list, (i & 4) != 0 ? MapsKt.emptyMap() : map, (i & 8) != 0 ? true : z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0002\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u001b\u0010\u0007\u001a\u0004\u0018\u00010\bø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrModuleToJsTransformer$IrFileExports;", "", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", PsiKeyword.EXPORTS, "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "tsDeclarations", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "generatedFrom", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;Ljava/util/List;Ljava/lang/String;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getExports", "()Ljava/util/List;", "getFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getGeneratedFrom", "getTsDeclarations-J7-OZfo", "()Ljava/lang/String;", "Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class IrFileExports {
        private final List<ExportedDeclaration> exports;
        private final IrFile file;
        private final IrFile generatedFrom;
        private final String tsDeclarations;

        public /* synthetic */ IrFileExports(IrFile irFile, List list, String str, IrFile irFile2, DefaultConstructorMarker defaultConstructorMarker) {
            this(irFile, list, str, irFile2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private IrFileExports(IrFile file, List<? extends ExportedDeclaration> exports, String str, IrFile irFile) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(exports, "exports");
            this.file = file;
            this.exports = exports;
            this.tsDeclarations = str;
            this.generatedFrom = irFile;
        }

        public /* synthetic */ IrFileExports(IrFile irFile, List list, String str, IrFile irFile2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(irFile, list, str, (i & 8) != 0 ? null : irFile2, null);
        }

        public final IrFile getFile() {
            return this.file;
        }

        public final List<ExportedDeclaration> getExports() {
            return this.exports;
        }

        /* JADX INFO: renamed from: getTsDeclarations-J7-OZfo, reason: not valid java name and from getter */
        public final String getTsDeclarations() {
            return this.tsDeclarations;
        }

        public final IrFile getGeneratedFrom() {
            return this.generatedFrom;
        }
    }

    /* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrModuleToJsTransformer$IrAndExportedDeclarations;", "", "fragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "files", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrModuleToJsTransformer$IrFileExports;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Ljava/util/List;)V", "getFiles", "()Ljava/util/List;", "getFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IrAndExportedDeclarations {
        private final List<IrFileExports> files;
        private final IrModuleFragment fragment;

        public IrAndExportedDeclarations(IrModuleFragment fragment, List<IrFileExports> files) {
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            Intrinsics.checkNotNullParameter(files, "files");
            this.fragment = fragment;
            this.files = files;
        }

        public final List<IrFileExports> getFiles() {
            return this.files;
        }

        public final IrModuleFragment getFragment() {
            return this.fragment;
        }
    }

    private final List<IrAndExportedDeclarations> associateIrAndExport(Iterable<? extends IrModuleFragment> modules) {
        ExportModelGenerator exportModelGenerator = new ExportModelGenerator(this.backendContext, !this.isEsModules);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(modules, 10));
        for (IrModuleFragment irModuleFragment : modules) {
            arrayList.add(new IrAndExportedDeclarations(irModuleFragment, generateExportWithExternals(exportModelGenerator, irModuleFragment.getFiles())));
        }
        return arrayList;
    }

    public final CompilerResult generateModule(Iterable<? extends IrModuleFragment> modules, Set<? extends TranslationMode> modes, boolean relativeRequirePath) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(modes, "modes");
        List<IrAndExportedDeclarations> listAssociateIrAndExport = associateIrAndExport(modules);
        doStaticMembersLowering(modules);
        EnumMap enumMap = new EnumMap(TranslationMode.class);
        Set<? extends TranslationMode> set = modes;
        ArrayList<TranslationMode> arrayList = new ArrayList();
        for (Object obj : set) {
            if (!((TranslationMode) obj).getProduction()) {
                arrayList.add(obj);
            }
        }
        for (TranslationMode translationMode : arrayList) {
            enumMap.put(translationMode, makeJsCodeGeneratorFromIr(listAssociateIrAndExport, translationMode).generateJsCode(relativeRequirePath, true));
        }
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                if (((TranslationMode) it.next()).getProduction()) {
                    OptimizationsKt.optimizeProgramByIr(modules, this.backendContext, this.removeUnusedAssociatedObjects);
                    break;
                }
            }
        }
        ArrayList<TranslationMode> arrayList2 = new ArrayList();
        for (Object obj2 : set) {
            if (((TranslationMode) obj2).getProduction()) {
                arrayList2.add(obj2);
            }
        }
        for (TranslationMode translationMode2 : arrayList2) {
            enumMap.put(translationMode2, makeJsCodeGeneratorFromIr(listAssociateIrAndExport, translationMode2).generateJsCode(relativeRequirePath, true));
        }
        return new CompilerResult(enumMap);
    }

    public final JsCodeGenerator makeJsCodeGenerator(Iterable<? extends IrModuleFragment> modules, TranslationMode mode) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(mode, "mode");
        List<IrAndExportedDeclarations> listAssociateIrAndExport = associateIrAndExport(modules);
        doStaticMembersLowering(modules);
        if (mode.getProduction()) {
            OptimizationsKt.optimizeProgramByIr(modules, this.backendContext, this.removeUnusedAssociatedObjects);
        }
        return makeJsCodeGeneratorFromIr(listAssociateIrAndExport, mode);
    }

    public final List<Function0<List<JsIrProgramFragment>>> makeIrFragmentsGenerators(Collection<? extends IrFile> dirtyFiles, Collection<? extends IrModuleFragment> allModules) {
        Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
        Intrinsics.checkNotNullParameter(allModules, "allModules");
        Set<IrFile> setKeySet = this.backendContext.getMapping().getChunkToOriginalFile().keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        List<IrFileExports> listGenerateExportWithExternals = generateExportWithExternals(new ExportModelGenerator(this.backendContext, !this.isEsModules), CollectionsKt.plus((Collection) dirtyFiles, (Iterable) setKeySet));
        final TranslationMode translationModeFromFlags = TranslationMode.INSTANCE.fromFlags(false, this.backendContext.getGranularity(), false);
        doStaticMembersLowering(allModules);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : listGenerateExportWithExternals) {
            IrFileExports irFileExports = (IrFileExports) obj;
            IrFile generatedFrom = irFileExports.getGeneratedFrom();
            if (generatedFrom == null) {
                generatedFrom = irFileExports.getFile();
            }
            Object obj2 = linkedHashMap.get(generatedFrom);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(generatedFrom, obj2);
            }
            ((List) obj2).add(obj);
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        for (final Map.Entry entry : linkedHashMap.entrySet()) {
            arrayList.add(new Function0<List<? extends JsIrProgramFragment>>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformer$makeIrFragmentsGenerators$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends JsIrProgramFragment> invoke() {
                    List<IrModuleToJsTransformer.IrFileExports> value = entry.getValue();
                    IrModuleToJsTransformer irModuleToJsTransformer = this;
                    TranslationMode translationMode = translationModeFromFlags;
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<T> it = value.iterator();
                    while (it.hasNext()) {
                        CollectionsKt.addAll(arrayList2, irModuleToJsTransformer.generateProgramFragment((IrModuleToJsTransformer.IrFileExports) it.next(), translationMode));
                    }
                    return arrayList2;
                }
            });
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x007a  */
    private final List<IrFileExports> generateExportWithExternals(ExportModelGenerator exportModelGenerator, Collection<? extends IrFile> collection) {
        List<ExportedDeclaration> listEmptyList;
        TypeScriptFragment typeScriptFragmentM7089boximpl;
        String typeScriptFragment;
        Collection<? extends IrFile> collection2 = collection;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        for (IrFile irFile : collection2) {
            List<ExportedDeclaration> listGenerateExport = exportModelGenerator.generateExport(irFile);
            IrFile irFile2 = this.backendContext.getExternalPackageFragment().get(irFile.getSymbol());
            if (irFile2 == null || (listEmptyList = exportModelGenerator.generateExport(irFile2)) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            List listPlus = CollectionsKt.plus((Collection) listEmptyList, (Iterable) listGenerateExport);
            if (this.shouldGenerateTypeScriptDefinitions) {
                List list = listPlus;
                TypeScriptFragment typeScriptFragmentM7089boximpl2 = (list.isEmpty() || (typeScriptFragment = ExportModelToTsDeclarationsKt.toTypeScriptFragment(list, this.moduleKind)) == null) ? null : TypeScriptFragment.m7089boximpl(typeScriptFragment);
                String strM7095unboximpl = typeScriptFragmentM7089boximpl2 != null ? typeScriptFragmentM7089boximpl2.m7095unboximpl() : null;
                if (strM7095unboximpl != null) {
                    typeScriptFragmentM7089boximpl = TypeScriptFragment.m7089boximpl(strM7095unboximpl);
                } else {
                    typeScriptFragmentM7089boximpl = null;
                }
            } else {
                typeScriptFragmentM7089boximpl = null;
            }
            arrayList.add(new IrFileExports(irFile, listPlus, typeScriptFragmentM7089boximpl != null ? typeScriptFragmentM7089boximpl.m7095unboximpl() : null, exportModelGenerator.getContext().getMapping().getChunkToOriginalFile().get(irFile), null));
        }
        return arrayList;
    }

    private final JsCodeGenerator makeJsCodeGeneratorFromIr(List<IrAndExportedDeclarations> exportData, TranslationMode mode) {
        JsIrProgram jsIrProgramGenerateJsIrProgramPerModule;
        if (mode.getMinimizedMemberNames()) {
            this.backendContext.getFieldDataCache().clear();
            this.backendContext.getMinimizedNameGenerator().clear();
        }
        int i = WhenMappings.$EnumSwitchMapping$0[mode.getGranularity().ordinal()];
        if (i == 1 || i == 2) {
            jsIrProgramGenerateJsIrProgramPerModule = generateJsIrProgramPerModule(exportData, mode);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            jsIrProgramGenerateJsIrProgramPerModule = generateJsIrProgramPerFile(exportData, mode);
        }
        return new JsCodeGenerator(jsIrProgramGenerateJsIrProgramPerModule, mode.getGranularity(), this.mainModuleName, this.moduleKind, this.sourceMapInfo);
    }

    private final JsIrProgram generateJsIrProgramPerModule(List<IrAndExportedDeclarations> exportData, TranslationMode mode) {
        IrAndExportedDeclarations irAndExportedDeclarations = (IrAndExportedDeclarations) CollectionsKt.last((List) exportData);
        List<IrAndExportedDeclarations> list = exportData;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (IrAndExportedDeclarations irAndExportedDeclarations2 : list) {
            String safeName = IrModuleToJsTransformerKt.getSafeName(irAndExportedDeclarations2.getFragment());
            String externalNameFor = this.moduleFragmentToNameMapper.getExternalNameFor(irAndExportedDeclarations2.getFragment());
            List<IrFileExports> files = irAndExportedDeclarations2.getFiles();
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = files.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList2, generateProgramFragment((IrFileExports) it.next(), mode));
            }
            ArrayList arrayList3 = arrayList2;
            String safeName2 = IrModuleToJsTransformerKt.getSafeName(irAndExportedDeclarations.getFragment());
            if (this.isEsModules || Intrinsics.areEqual(irAndExportedDeclarations2, irAndExportedDeclarations)) {
                safeName2 = null;
            }
            arrayList.add(new JsIrModule(safeName, externalNameFor, arrayList3, safeName2));
        }
        return new JsIrProgram(arrayList);
    }

    private final JsIrProgram generateJsIrProgramPerFile(List<IrAndExportedDeclarations> exportData, TranslationMode mode) {
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        for (IrAndExportedDeclarations irAndExportedDeclarations : exportData) {
            boolean z = false;
            for (IrFileExports irFileExports : irAndExportedDeclarations.getFiles()) {
                if (!couldBeSkipped(irFileExports.getFile())) {
                    List<JsIrProgramFragment> listGenerateProgramFragment = generateProgramFragment(irFileExports, mode);
                    listCreateListBuilder.add(toJsIrModule(irFileExports, JsIrProgramFragmentKt.getMainFragment(listGenerateProgramFragment)));
                    JsIrProgramFragment exportFragment = JsIrProgramFragmentKt.getExportFragment(listGenerateProgramFragment);
                    if (exportFragment != null) {
                        listCreateListBuilder.add(toJsIrModuleForExport(irFileExports, irAndExportedDeclarations, exportFragment));
                        z = true;
                    }
                }
            }
            if (z) {
                listCreateListBuilder.add(toJsIrProxyModule(irAndExportedDeclarations));
            }
        }
        return new JsIrProgram(CollectionsKt.build(listCreateListBuilder));
    }

    private final JsIrModule toJsIrModule(IrFileExports irFileExports, JsIrProgramFragment jsIrProgramFragment) {
        return new JsIrModule(this.moduleFragmentToNameMapper.getSafeNameFor(irFileExports.getFile()), this.moduleFragmentToNameMapper.getExternalNameFor(irFileExports.getFile()), CollectionsKt.listOf(jsIrProgramFragment), null, 8, null);
    }

    private final JsIrModule toJsIrModuleForExport(IrFileExports irFileExports, IrAndExportedDeclarations irAndExportedDeclarations, JsIrProgramFragment jsIrProgramFragment) {
        return new JsIrModule(this.moduleFragmentToNameMapper.getSafeNameExporterFor(irFileExports.getFile()), this.moduleFragmentToNameMapper.getExternalNameForExporterFile(irFileExports.getFile()), CollectionsKt.listOf(jsIrProgramFragment), IrModuleToJsTransformerKt.getSafeName(irAndExportedDeclarations.getFragment()));
    }

    private final JsIrModule toJsIrProxyModule(IrAndExportedDeclarations irAndExportedDeclarations) {
        return IrModuleToJsTransformerKt.generateProxyIrModuleWith(IrModuleToJsTransformerKt.getSafeName(irAndExportedDeclarations.getFragment()), this.moduleFragmentToNameMapper.getExternalNameFor(irAndExportedDeclarations.getFragment()));
    }

    private final JsIrProgramFragment generateProgramFragmentForExport(IrFileExports irFileExports, TranslationMode translationMode, NameTable<IrDeclaration> nameTable) {
        if (irFileExports.getExports().isEmpty()) {
            return null;
        }
        final NameTable nameTable2 = new NameTable(nameTable, null, null, 6, null);
        JsNameLinkingNamer jsNameLinkingNamer = new JsNameLinkingNamer(this.backendContext, translationMode.getMinimizedMemberNames(), this.isEsModules);
        JsName jsNameMakeInternalModuleName = this.isEsModules ? null : ReservedJsNames.INSTANCE.makeInternalModuleName();
        JsStaticContext jsStaticContext = new JsStaticContext(this.backendContext, jsNameLinkingNamer, nameTable, translationMode);
        String strAsString = irFileExports.getFile().getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        JsIrProgramFragment jsIrProgramFragment = new JsIrProgramFragment("", strAsString);
        jsIrProgramFragment.m7200setDtsY7E3pzw(irFileExports.getTsDeclarations());
        List<JsStatement> statements = jsIrProgramFragment.getExports().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        CollectionsKt.addAll(statements, new ExportModelToJsStatements(jsStaticContext, this.backendContext.getEs6mode(), new Function1<String, String>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformer$generateProgramFragmentForExport$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return nameTable2.declareFreshName(it, it);
            }
        }).generateModuleExport(new ExportedModule(this.mainModuleName, this.moduleKind, irFileExports.getExports()), jsNameMakeInternalModuleName, this.isEsModules));
        computeAndSaveNameBindings(jsIrProgramFragment, SetsKt.emptySet(), jsNameLinkingNamer);
        return jsIrProgramFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<JsIrProgramFragment> generateProgramFragment(IrFileExports fileExports, TranslationMode mode) {
        IrSimpleFunction mainFunctionOrNull;
        String str;
        Object obj;
        NameTable<IrDeclaration> nameTable = new NameTable<>(null, null, null, 7, null);
        JsNameLinkingNamer jsNameLinkingNamer = new JsNameLinkingNamer(this.backendContext, mode.getMinimizedMemberNames(), this.isEsModules);
        JsStaticContext jsStaticContext = new JsStaticContext(this.backendContext, jsNameLinkingNamer, nameTable, mode);
        String jsFileName = AnnotationUtilsKt.getJsFileName(fileExports.getFile());
        if (jsFileName == null) {
            jsFileName = IrJsUtilsKt.getNameWithoutExtension(fileExports.getFile());
        }
        String strAsString = fileExports.getFile().getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        JsIrProgramFragment jsIrProgramFragment = new JsIrProgramFragment(jsFileName, strAsString);
        if (this.shouldGeneratePolyfills) {
            List<JsStatement> statements = jsIrProgramFragment.getPolyfills().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            CollectionsKt.addAll(statements, this.backendContext.getPolyfills().getAllPolyfillsFor(fileExports.getFile()));
        }
        List<JsStatement> statements2 = jsIrProgramFragment.getDeclarations().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        List<JsStatement> statements3 = ((JsBlock) fileExports.getFile().accept(new IrFileToJsTransformer(true), jsStaticContext)).getStatements();
        Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
        JsIrProgramFragment jsIrProgramFragmentGenerateProgramFragmentForExport = generateProgramFragmentForExport(fileExports, mode, nameTable);
        List<JsStatement> list = statements3;
        if (!list.isEmpty()) {
            boolean z = this.generateRegionComments;
            if (!z) {
                str = "";
            } else {
                str = "region ";
            }
            if (z || this.generateFilePaths) {
                String path = IrDeclarationsKt.getPath(fileExports.getFile());
                Iterator it = this.pathPrefixMap.entrySet().iterator();
                while (true) {
                    obj = null;
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    Iterator it2 = it;
                    String str2 = (String) ((Map.Entry) next).getKey();
                    Intrinsics.checkNotNull(str2);
                    if (StringsKt.startsWith$default(path, str2, false, 2, (Object) null)) {
                        obj = next;
                        break;
                    }
                    it = it2;
                }
                Map.Entry entry = (Map.Entry) obj;
                if (entry != null) {
                    String str3 = (String) entry.getKey();
                    StringBuilder sbAppend = new StringBuilder().append((String) entry.getValue());
                    String strSubstring = path.substring(str3.length());
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    String string = sbAppend.append(strSubstring).toString();
                    if (string != null) {
                        path = string;
                    }
                }
                str = str + "file: " + path;
            }
            if (str.length() > 0) {
                statements2.add(new JsSingleLineComment(str));
            }
            statements2.addAll(list);
            if (this.generateRegionComments) {
                statements2.add(new JsSingleLineComment("endregion"));
            }
        }
        Iterator<T> it3 = jsStaticContext.getClassModels().entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it3.next();
            IrClassSymbol irClassSymbol = (IrClassSymbol) entry2.getKey();
            JsIrClassModel jsIrClassModel = (JsIrClassModel) entry2.getValue();
            Map<JsName, JsIrIcClassModel> classes = jsIrProgramFragment.getClasses();
            JsName nameForClass = jsNameLinkingNamer.getNameForClass(irClassSymbol.getOwner());
            List<IrClassSymbol> superClasses = jsIrClassModel.getSuperClasses();
            ArrayList arrayList = new ArrayList(superClasses.size());
            Iterator<T> it4 = superClasses.iterator();
            while (it4.hasNext()) {
                arrayList.add(jsStaticContext.getNameForClass(((IrClassSymbol) it4.next()).getOwner()));
            }
            JsIrIcClassModel jsIrIcClassModel = new JsIrIcClassModel(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
            List<JsStatement> statements4 = jsIrIcClassModel.getPreDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
            List<JsStatement> statements5 = jsIrClassModel.getPreDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements5, "getStatements(...)");
            CollectionsKt.addAll(statements4, statements5);
            List<JsStatement> statements6 = jsIrIcClassModel.getPostDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements6, "getStatements(...)");
            List<JsStatement> statements7 = jsIrClassModel.getPostDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements7, "getStatements(...)");
            CollectionsKt.addAll(statements6, statements7);
            classes.put(nameForClass, jsIrIcClassModel);
        }
        List<JsStatement> statements8 = jsIrProgramFragment.getInitializers().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements8, "getStatements(...)");
        List<JsStatement> statements9 = jsStaticContext.getInitializerBlock().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements9, "getStatements(...)");
        CollectionsKt.addAll(statements8, statements9);
        if (this.mainArguments != null && (mainFunctionOrNull = new JsMainFunctionDetector(this.backendContext).getMainFunctionOrNull(fileExports.getFile())) != null) {
            JsName nameForStaticFunction = jsStaticContext.getNameForStaticFunction(mainFunctionOrNull);
            IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.firstOrNull((List) mainFunctionOrNull.getValueParameters());
            jsIrProgramFragment.setMainFunction(new JsInvocation(nameForStaticFunction.makeRef(), generateMainArguments(irValueParameter != null ? JsMainFunctionDetectorKt.isStringArrayParameter(irValueParameter) : false, JsMainFunctionDetectorKt.isLoweredSuspendFunction(mainFunctionOrNull, this.backendContext), jsStaticContext)).makeStmt());
        }
        IrSimpleFunction irSimpleFunction = this.backendContext.getTestFunsPerFile().get(fileExports.getFile());
        if (irSimpleFunction != null) {
            jsIrProgramFragment.setTestFunInvocation(new JsInvocation(jsStaticContext.getNameForStaticFunction(irSimpleFunction).makeRef(), new JsExpression[0]).makeStmt());
            IrSimpleFunctionSymbol suiteFun = this.backendContext.getSuiteFun();
            Intrinsics.checkNotNull(suiteFun);
            jsIrProgramFragment.setSuiteFn(jsStaticContext.getNameForStaticFunction(suiteFun.getOwner()));
        }
        CollectionsKt.addAll(jsIrProgramFragment.getImportedModules(), jsNameLinkingNamer.getImportedModules());
        Set<? extends IrDeclaration> set = CollectionsKt.toSet(fileExports.getFile().getDeclarations());
        computeAndSaveNameBindings(jsIrProgramFragment, set, jsNameLinkingNamer);
        computeAndSaveImports(jsIrProgramFragment, set, jsNameLinkingNamer);
        computeAndSaveDefinitions(jsIrProgramFragment, set, fileExports);
        if (this.optimizeGeneratedJs) {
            OptimizationsKt.optimizeFragmentByJsAst(jsIrProgramFragment);
        }
        return CollectionsKt.listOfNotNull((Object[]) new JsIrProgramFragment[]{jsIrProgramFragment, jsIrProgramFragmentGenerateProgramFragmentForExport});
    }

    private final String computeTag(Set<? extends IrDeclaration> set, IrDeclaration irDeclaration) {
        IrFactory irFactory = this.backendContext.getIrFactory();
        Intrinsics.checkNotNull(irFactory, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IdSignatureRetriever");
        IdSignature idSignatureDeclarationSignature = ((IdSignatureRetriever) irFactory).declarationSignature(irDeclaration);
        String strRender = idSignatureDeclarationSignature != null ? IdSignatureRendererKt.render(idSignatureDeclarationSignature, IdSignatureRenderer.INSTANCE.getLEGACY()) : null;
        if (strRender != null || set.contains(irDeclaration)) {
            return strRender;
        }
        throw new IllegalStateException(("signature for " + RenderIrElementKt.render$default(irDeclaration, (DumpIrTreeOptions) null, 1, (Object) null) + " not found").toString());
    }

    private final void computeAndSaveNameBindings(JsIrProgramFragment jsIrProgramFragment, Set<? extends IrDeclaration> set, JsNameLinkingNamer jsNameLinkingNamer) {
        Iterator<T> it = jsNameLinkingNamer.getNameMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            IrDeclaration irDeclaration = (IrDeclaration) entry.getKey();
            JsName jsName = (JsName) entry.getValue();
            String strComputeTag = computeTag(set, irDeclaration);
            if (strComputeTag != null) {
                jsIrProgramFragment.getNameBindings().put(strComputeTag, jsName);
                if (MoveBodilessDeclarationsToSeparatePlaceKt.isBuiltInClass(irDeclaration) || FunctionalInterfacesUtilsKt.checkIsFunctionInterface(irDeclaration.getSymbol().getSignature())) {
                    jsIrProgramFragment.getOptionalCrossModuleImports().add(strComputeTag);
                }
            }
        }
    }

    private final void computeAndSaveImports(JsIrProgramFragment jsIrProgramFragment, Set<? extends IrDeclaration> set, JsNameLinkingNamer jsNameLinkingNamer) {
        Iterator<T> it = jsNameLinkingNamer.getImports().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            IrDeclaration irDeclaration = (IrDeclaration) entry.getKey();
            JsStatement jsStatement = (JsStatement) entry.getValue();
            String strComputeTag = computeTag(set, irDeclaration);
            if (strComputeTag == null) {
                throw new IllegalStateException(("No tag for imported declaration " + RenderIrElementKt.render$default(irDeclaration, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            jsIrProgramFragment.getImports().put(strComputeTag, jsStatement);
            jsIrProgramFragment.getOptionalCrossModuleImports().add(strComputeTag);
        }
    }

    private final void computeAndSaveDefinitions(JsIrProgramFragment jsIrProgramFragment, Set<? extends IrDeclaration> set, IrFileExports irFileExports) {
        for (IrDeclaration irDeclaration : irFileExports.getFile().getDeclarations()) {
            String strComputeTag = computeTag(set, irDeclaration);
            if (strComputeTag != null) {
                jsIrProgramFragment.getDefinitions().add(strComputeTag);
            }
            if (irDeclaration instanceof IrClass) {
                IrClass irClass = (IrClass) irDeclaration;
                if (IrUtilsKt.isInterface(irClass)) {
                    Iterator<T> it = irClass.getDeclarations().iterator();
                    while (it.hasNext()) {
                        String strComputeTag2 = computeTag(set, (IrDeclaration) it.next());
                        if (strComputeTag2 != null) {
                            jsIrProgramFragment.getDefinitions().add(strComputeTag2);
                        }
                    }
                }
            }
        }
    }

    private final List<JsExpression> generateMainArguments(boolean generateArgv, boolean generateContinuation, JsStaticContext staticContext) {
        JsArrayLiteral jsArrayLiteral;
        List<String> list = this.mainArguments;
        Intrinsics.checkNotNull(list);
        JsInvocation jsInvocation = null;
        if (generateArgv) {
            List<String> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(new JsStringLiteral((String) it.next()));
            }
            jsArrayLiteral = new JsArrayLiteral(arrayList);
        } else {
            jsArrayLiteral = null;
        }
        if (generateContinuation) {
            IrSimpleFunction getter = this.backendContext.getCoroutineEmptyContinuation().getOwner().getGetter();
            Intrinsics.checkNotNull(getter);
            jsInvocation = new JsInvocation(staticContext.getNameForStaticFunction(getter).makeRef(), new JsExpression[0]);
        }
        return CollectionsKt.listOfNotNull((Object[]) new JsExpression[]{jsArrayLiteral, jsInvocation});
    }

    private final boolean couldBeSkipped(IrFile irFile) {
        List<IrDeclaration> declarations = irFile.getDeclarations();
        if ((declarations instanceof Collection) && declarations.isEmpty()) {
            return true;
        }
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            if (!Intrinsics.areEqual(((IrDeclaration) it.next()).getOrigin(), JsCodeOutliningLowering.INSTANCE.getOUTLINED_JS_CODE_ORIGIN())) {
                return false;
            }
        }
        return true;
    }

    private final void doStaticMembersLowering(Iterable<? extends IrModuleFragment> modules) {
        Iterator<? extends IrModuleFragment> it = modules.iterator();
        while (it.hasNext()) {
            Iterator<T> it2 = it.next().getFiles().iterator();
            while (it2.hasNext()) {
                ((IrFile) it2.next()).accept(this.backendContext.getKeeper(), new Keeper.KeepData(false, false));
            }
        }
        Iterator<? extends IrModuleFragment> it3 = modules.iterator();
        while (it3.hasNext()) {
            Iterator<T> it4 = it3.next().getFiles().iterator();
            while (it4.hasNext()) {
                new StaticMembersLowering(this.backendContext).lower((IrFile) it4.next());
            }
        }
    }
}
