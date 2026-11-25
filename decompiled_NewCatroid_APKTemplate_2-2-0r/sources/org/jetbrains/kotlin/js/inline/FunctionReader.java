package org.jetbrains.kotlin.js.inline;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.HashMultimap;
import org.jetbrains.kotlin.com.google.gwt.dev.js.ThrowExceptionOnErrorReporter;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUCache;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsRootScope;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FixForwardNameReferencesKt;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.parser.OffsetToSourceMapping;
import org.jetbrains.kotlin.js.parser.ParserUtilsKt;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMap;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapError;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapLocationRemapper;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapParseResult;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapParser;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapSuccess;
import org.jetbrains.kotlin.js.sourceMap.RelativePathCalculator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.expression.InlineMetadata;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.utils.JsLibrary;
import org.jetbrains.kotlin.utils.JsLibraryUtils;

/* JADX INFO: compiled from: FunctionReader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r*\u0002\n+\u0018\u00002\u00020\u0001:\u0002;<B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0086\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J.\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010'\u001a\u00020\u00102\u0006\u0010(\u001a\u00020)H\u0002J\u001d\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020-H\u0002¢\u0006\u0002\u0010.J\u001e\u0010/\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u0010\u0018\u0001002\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u001a\u00101\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020\u0010H\u0002J\u0018\u00102\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\u0010H\u0002J\u0010\u00104\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u000eH\u0002J(\u00105\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00106\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020\u00102\u0006\u00107\u001a\u00020\u001eH\u0002J\u0018\u00108\u001a\u00020-2\u0006\u0010,\u001a\u00020\u000e2\u0006\u00109\u001a\u00020-H\u0002J\f\u0010:\u001a\u00020\u001a*\u00020\u001aH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR[\u0010\f\u001aB\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u000e0\u000e\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u00100\u0010 \u000f* \u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u000e0\u000e\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u00100\u0010\u0018\u00010\r0\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/js/inline/FunctionReader;", "", "reporter", "Lorg/jetbrains/kotlin/js/config/JsConfig$Reporter;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/js/config/JsConfig$Reporter;Lorg/jetbrains/kotlin/js/config/JsConfig;Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "functionCache", "org/jetbrains/kotlin/js/inline/FunctionReader$functionCache$1", "Lorg/jetbrains/kotlin/js/inline/FunctionReader$functionCache$1;", "moduleNameToInfo", "Lorg/jetbrains/kotlin/com/google/common/collect/HashMultimap;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/js/inline/FunctionReader$ModuleInfo;", "getModuleNameToInfo", "()Lcom/google/common/collect/HashMultimap;", "moduleNameToInfo$delegate", "Lkotlin/Lazy;", "relativePathCalculator", "Lorg/jetbrains/kotlin/js/sourceMap/RelativePathCalculator;", "shouldRemapPathToRelativeForm", "", "get", "Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "callsiteFragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "markDefaultParams", "", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "markSpecialFunctions", "allDefinedNames", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", CompilerOptions.INFO, "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", TypedValues.CycleType.S_WAVE_OFFSET, "org/jetbrains/kotlin/js/inline/FunctionReader$offset$1", "text", "", "(Ljava/lang/String;I)Lorg/jetbrains/kotlin/js/inline/FunctionReader$offset$1;", "readFunction", "Lkotlin/Pair;", "readFunctionFromSource", "remapPath", "path", "removeRedundantPathPrefix", "renameModules", "fn", "fragment", "rewindToIdentifierStart", "index", "deepCopy", "ModuleInfo", "NotFoundMarker", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionReader {
    private final BindingContext bindingContext;
    private final JsConfig config;
    private final FunctionReader$functionCache$1 functionCache;

    /* JADX INFO: renamed from: moduleNameToInfo$delegate, reason: from kotlin metadata */
    private final Lazy moduleNameToInfo;
    private final RelativePathCalculator relativePathCalculator;
    private final JsConfig.Reporter reporter;
    private final boolean shouldRemapPathToRelativeForm;

    /* JADX WARN: Type inference failed for: r2v8, types: [org.jetbrains.kotlin.js.inline.FunctionReader$functionCache$1] */
    public FunctionReader(JsConfig.Reporter reporter, JsConfig config, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        this.reporter = reporter;
        this.config = config;
        this.bindingContext = bindingContext;
        this.moduleNameToInfo = LazyKt.lazy(new Function0<HashMultimap<String, ModuleInfo>>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$moduleNameToInfo$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final HashMultimap<String, FunctionReader.ModuleInfo> invoke() {
                final HashMultimap<String, FunctionReader.ModuleInfo> hashMultimapCreate = HashMultimap.create();
                List<String> libraries = this.this$0.config.getLibraries();
                Intrinsics.checkNotNullExpressionValue(libraries, "getLibraries(...)");
                List<String> list = libraries;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(new File((String) it.next()));
                }
                final FunctionReader functionReader = this.this$0;
                JsLibraryUtils.traverseJsLibraries(arrayList, new Function1<JsLibrary, Unit>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$moduleNameToInfo$2.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(JsLibrary jsLibrary) {
                        invoke2(jsLibrary);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(JsLibrary jsLibrary) {
                        Intrinsics.checkNotNullParameter(jsLibrary, "<name for destructuring parameter 0>");
                        final String content = jsLibrary.getContent();
                        final String path = jsLibrary.getPath();
                        final String sourceMapContent = jsLibrary.getSourceMapContent();
                        File file = jsLibrary.getFile();
                        int i = 0;
                        while (true) {
                            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) content, FunctionReaderKt.DEFINE_MODULE_FIND_PATTERN, i, false, 4, (Object) null);
                            if (iIndexOf$default < 0) {
                                return;
                            }
                            int i2 = iIndexOf$default + 1;
                            Matcher matcher = FunctionReaderKt.DEFINE_MODULE_PATTERN.matcher(functionReader.offset(content, functionReader.rewindToIdentifierStart(content, iIndexOf$default)));
                            if (matcher.lookingAt()) {
                                String strGroup = matcher.group(3);
                                String strGroup2 = matcher.group(4);
                                final String strGroup3 = matcher.group(1);
                                Function0<Map<String, SpecialFunction>> function0 = new Function0<Map<String, SpecialFunction>>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$moduleNameToInfo$2$2$specialFunctionsProvider$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final Map<String, SpecialFunction> invoke() {
                                        Matcher matcher2 = FunctionReaderKt.SPECIAL_FUNCTION_PATTERN.matcher(content);
                                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                                        while (matcher2.find()) {
                                            if (Intrinsics.areEqual(matcher2.group(2), strGroup3)) {
                                                String strGroup4 = matcher2.group(1);
                                                Intrinsics.checkNotNullExpressionValue(strGroup4, "group(...)");
                                                Object obj = FunctionReaderKt.specialFunctionsByName.get(matcher2.group(3));
                                                Intrinsics.checkNotNull(obj);
                                                linkedHashMap.put(strGroup4, obj);
                                            }
                                        }
                                        return linkedHashMap;
                                    }
                                };
                                final FunctionReader functionReader2 = functionReader;
                                Function0<SourceMap> function1 = new Function0<SourceMap>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$moduleNameToInfo$2$2$sourceMapProvider$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final SourceMap invoke() throws IOException {
                                        String str = sourceMapContent;
                                        if (str == null) {
                                            return null;
                                        }
                                        FunctionReader functionReader3 = functionReader2;
                                        String str2 = path;
                                        SourceMapParseResult sourceMapParseResult = SourceMapParser.INSTANCE.parse(str);
                                        if (sourceMapParseResult instanceof SourceMapSuccess) {
                                            return ((SourceMapSuccess) sourceMapParseResult).getValue();
                                        }
                                        if (sourceMapParseResult instanceof SourceMapError) {
                                            functionReader3.reporter.warning("Error parsing source map file for " + str2 + ": " + ((SourceMapError) sourceMapParseResult).getMessage());
                                            return null;
                                        }
                                        throw new NoWhenBranchMatchedException();
                                    }
                                };
                                Intrinsics.checkNotNull(strGroup2);
                                Intrinsics.checkNotNull(strGroup3);
                                hashMultimapCreate.put(strGroup, new FunctionReader.ModuleInfo(path, content, strGroup2, strGroup3, function0, new Function0<OffsetToSourceMapping>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$moduleNameToInfo$2$2$moduleInfo$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final OffsetToSourceMapping invoke() {
                                        return new OffsetToSourceMapping(content);
                                    }
                                }, function1, file != null ? file.getParentFile() : null));
                            }
                            i = i2;
                            content = content;
                        }
                    }
                });
                return hashMultimapCreate;
            }
        });
        this.shouldRemapPathToRelativeForm = config.shouldGenerateRelativePathsInSourceMap();
        File file = (File) config.getConfiguration().get(JSConfigurationKeys.OUTPUT_DIR);
        this.relativePathCalculator = file != null ? new RelativePathCalculator(file) : null;
        this.functionCache = new SLRUCache<CallableDescriptor, Object>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$functionCache$1
            {
                super(50, 50);
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.SLRUCache
            public Object createValue(CallableDescriptor key) {
                Intrinsics.checkNotNullParameter(key, "key");
                Pair function = this.this$0.readFunction(key);
                return function == null ? FunctionReader.NotFoundMarker.INSTANCE : function;
            }
        };
    }

    /* JADX INFO: compiled from: FunctionReader.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001Bg\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n0\t0\b\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\b\u0012\u000e\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\b\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u001b\u0010\u0017\u001a\u00020\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001d\u0010\u001e\u001a\u0004\u0018\u00010\u000e8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u001b\u001a\u0004\b\u001f\u0010 R'\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n0\t8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u001b\u001a\u0004\b#\u0010$R\u001d\u0010&\u001a\u0004\u0018\u00010'8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u001b\u001a\u0004\b(\u0010)¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/FunctionReader$ModuleInfo;", "", "filePath", "", "fileContent", "moduleVariable", "kotlinVariable", "specialFunctionsProvider", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "offsetToSourceMappingProvider", "Lorg/jetbrains/kotlin/js/parser/OffsetToSourceMapping;", "sourceMapProvider", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap;", ModuleXmlParser.OUTPUT_DIR, "Ljava/io/File;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Ljava/io/File;)V", "getFileContent", "()Ljava/lang/String;", "getFilePath", "getKotlinVariable", "getModuleVariable", "offsetToSourceMapping", "getOffsetToSourceMapping", "()Lorg/jetbrains/kotlin/js/parser/OffsetToSourceMapping;", "offsetToSourceMapping$delegate", "Lkotlin/Lazy;", "getOutputDir", "()Ljava/io/File;", "sourceMap", "getSourceMap", "()Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap;", "sourceMap$delegate", "specialFunctions", "getSpecialFunctions", "()Ljava/util/Map;", "specialFunctions$delegate", "wrapFunctionRegex", "Ljava/util/regex/Pattern;", "getWrapFunctionRegex", "()Ljava/util/regex/Pattern;", "wrapFunctionRegex$delegate", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ModuleInfo {
        private final String fileContent;
        private final String filePath;
        private final String kotlinVariable;
        private final String moduleVariable;

        /* JADX INFO: renamed from: offsetToSourceMapping$delegate, reason: from kotlin metadata */
        private final Lazy offsetToSourceMapping;
        private final File outputDir;

        /* JADX INFO: renamed from: sourceMap$delegate, reason: from kotlin metadata */
        private final Lazy sourceMap;

        /* JADX INFO: renamed from: specialFunctions$delegate, reason: from kotlin metadata */
        private final Lazy specialFunctions;

        /* JADX INFO: renamed from: wrapFunctionRegex$delegate, reason: from kotlin metadata */
        private final Lazy wrapFunctionRegex;

        public ModuleInfo(String filePath, String fileContent, String moduleVariable, String kotlinVariable, Function0<? extends Map<String, ? extends SpecialFunction>> specialFunctionsProvider, Function0<OffsetToSourceMapping> offsetToSourceMappingProvider, Function0<SourceMap> sourceMapProvider, File file) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            Intrinsics.checkNotNullParameter(fileContent, "fileContent");
            Intrinsics.checkNotNullParameter(moduleVariable, "moduleVariable");
            Intrinsics.checkNotNullParameter(kotlinVariable, "kotlinVariable");
            Intrinsics.checkNotNullParameter(specialFunctionsProvider, "specialFunctionsProvider");
            Intrinsics.checkNotNullParameter(offsetToSourceMappingProvider, "offsetToSourceMappingProvider");
            Intrinsics.checkNotNullParameter(sourceMapProvider, "sourceMapProvider");
            this.filePath = filePath;
            this.fileContent = fileContent;
            this.moduleVariable = moduleVariable;
            this.kotlinVariable = kotlinVariable;
            this.outputDir = file;
            this.specialFunctions = LazyKt.lazy(specialFunctionsProvider);
            this.offsetToSourceMapping = LazyKt.lazy(offsetToSourceMappingProvider);
            this.sourceMap = LazyKt.lazy(sourceMapProvider);
            this.wrapFunctionRegex = LazyKt.lazy(new Function0<Pattern>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$ModuleInfo$wrapFunctionRegex$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Pattern invoke() {
                    String str;
                    Iterator<T> it = this.this$0.getSpecialFunctions().entrySet().iterator();
                    boolean z = false;
                    Object obj = null;
                    while (true) {
                        if (!it.hasNext()) {
                            if (!z) {
                                break;
                            }
                            break;
                        }
                        Object next = it.next();
                        if (((SpecialFunction) ((Map.Entry) next).getValue()) == SpecialFunction.WRAP_FUNCTION) {
                            if (!z) {
                                z = true;
                                obj = next;
                            }
                        }
                        obj = null;
                        break;
                    }
                    Map.Entry entry = (Map.Entry) obj;
                    if (entry == null || (str = (String) entry.getKey()) == null) {
                        return null;
                    }
                    return new Regex("\\s*" + str + "\\s*\\(\\s*").getNativePattern();
                }
            });
        }

        public final String getFilePath() {
            return this.filePath;
        }

        public final String getFileContent() {
            return this.fileContent;
        }

        public final String getModuleVariable() {
            return this.moduleVariable;
        }

        public final String getKotlinVariable() {
            return this.kotlinVariable;
        }

        public final File getOutputDir() {
            return this.outputDir;
        }

        public final Map<String, SpecialFunction> getSpecialFunctions() {
            return (Map) this.specialFunctions.getValue();
        }

        public final OffsetToSourceMapping getOffsetToSourceMapping() {
            return (OffsetToSourceMapping) this.offsetToSourceMapping.getValue();
        }

        public final SourceMap getSourceMap() {
            return (SourceMap) this.sourceMap.getValue();
        }

        public final Pattern getWrapFunctionRegex() {
            return (Pattern) this.wrapFunctionRegex.getValue();
        }
    }

    private final HashMultimap<String, ModuleInfo> getModuleNameToInfo() {
        return (HashMultimap) this.moduleNameToInfo.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int rewindToIdentifierStart(String text, int index) {
        while (index > 0 && Character.isJavaIdentifierPart(text.charAt(index - 1))) {
            index--;
        }
        return index;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.jetbrains.kotlin.js.inline.FunctionReader$offset$1] */
    public final AnonymousClass1 offset(final String text, final int offset) {
        return new CharSequence() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader.offset.1
            @Override // java.lang.CharSequence
            public final /* bridge */ char charAt(int i) {
                return get(i);
            }

            @Override // java.lang.CharSequence
            public final /* bridge */ int length() {
                return getLength();
            }

            public int getLength() {
                return text.length() - offset;
            }

            public char get(int index) {
                return text.charAt(index + offset);
            }

            @Override // java.lang.CharSequence
            public CharSequence subSequence(int startIndex, int endIndex) {
                String str = text;
                int i = offset;
                return str.subSequence(startIndex + i, endIndex + i);
            }

            @Override // java.lang.CharSequence
            public String toString() {
                String strSubstring = text.substring(offset);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                return strSubstring;
            }
        };
    }

    /* JADX INFO: compiled from: FunctionReader.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/FunctionReader$NotFoundMarker;", "", "()V", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotFoundMarker {
        public static final NotFoundMarker INSTANCE = new NotFoundMarker();

        private NotFoundMarker() {
        }
    }

    public final FunctionWithWrapper get(CallableDescriptor descriptor, JsProgramFragment callsiteFragment) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(callsiteFragment, "callsiteFragment");
        Object obj = get(descriptor);
        if (obj == NotFoundMarker.INSTANCE) {
            return null;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Pair<*, *>");
        Pair pair = (Pair) obj;
        Object objComponent1 = pair.component1();
        Object objComponent2 = pair.component2();
        Intrinsics.checkNotNull(objComponent1, "null cannot be cast to non-null type org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper");
        FunctionWithWrapper functionWithWrapperDeepCopy = deepCopy((FunctionWithWrapper) objComponent1);
        Intrinsics.checkNotNull(objComponent2, "null cannot be cast to non-null type org.jetbrains.kotlin.js.inline.FunctionReader.ModuleInfo");
        return renameModules(descriptor, functionWithWrapperDeepCopy, (ModuleInfo) objComponent2, callsiteFragment);
    }

    private final FunctionWithWrapper deepCopy(FunctionWithWrapper functionWithWrapper) {
        if (functionWithWrapper.getWrapperBody() == null) {
            JsFunction jsFunctionDeepCopy = functionWithWrapper.getFunction().deepCopy();
            Intrinsics.checkNotNullExpressionValue(jsFunctionDeepCopy, "deepCopy(...)");
            return new FunctionWithWrapper(jsFunctionDeepCopy, null);
        }
        JsBlock jsBlockDeepCopy = functionWithWrapper.getWrapperBody().deepCopy();
        Intrinsics.checkNotNullExpressionValue(jsBlockDeepCopy, "deepCopy(...)");
        List<JsStatement> statements = jsBlockDeepCopy.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        Object objLast = CollectionsKt.last((List<? extends Object>) statements);
        Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsReturn");
        JsExpression expression = ((JsReturn) objLast).getExpression();
        Intrinsics.checkNotNull(expression, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsFunction");
        return new FunctionWithWrapper((JsFunction) expression, jsBlockDeepCopy);
    }

    private final FunctionWithWrapper renameModules(CallableDescriptor descriptor, FunctionWithWrapper fn, ModuleInfo info, JsProgramFragment fragment) {
        JsNameRef jsNameRefMakeRef;
        ArrayList<JsStatement> arrayList;
        List<JsStatement> statements;
        String functionTag = Namer.getFunctionTag(descriptor, this.config, this.bindingContext);
        Intrinsics.checkNotNullExpressionValue(functionTag, "getFunctionTag(...)");
        JsExpression jsExpression = fragment.getInlineModuleMap().get(functionTag);
        if (jsExpression == null || (jsNameRefMakeRef = jsExpression.deepCopy()) == null) {
            jsNameRefMakeRef = fragment.getScope().declareName("_").makeRef();
        }
        Intrinsics.checkNotNull(jsNameRefMakeRef);
        Set<JsName> setCollectDefinedNamesInAllScopes = CollectUtilsKt.collectDefinedNamesInAllScopes(fn.getFunction());
        HashMap mapHashMapOf = MapsKt.hashMapOf(TuplesKt.to(info.getModuleVariable(), jsNameRefMakeRef), TuplesKt.to(info.getKotlinVariable(), Namer.kotlinObject()));
        FunctionReaderKt.replaceExternalNames(fn.getFunction(), mapHashMapOf, setCollectDefinedNamesInAllScopes);
        JsBlock wrapperBody = fn.getWrapperBody();
        if (wrapperBody == null || (statements = wrapperBody.getStatements()) == null) {
            arrayList = null;
        } else {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : statements) {
                if (!(((JsStatement) obj) instanceof JsReturn)) {
                    arrayList2.add(obj);
                }
            }
            arrayList = arrayList2;
        }
        if (arrayList != null) {
            for (JsStatement jsStatement : arrayList) {
                Intrinsics.checkNotNull(jsStatement);
                FunctionReaderKt.replaceExternalNames(jsStatement, mapHashMapOf, setCollectDefinedNamesInAllScopes);
            }
        }
        return fn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<FunctionWithWrapper, ModuleInfo> readFunction(CallableDescriptor descriptor) {
        String moduleName = JsDescriptorUtils.getModuleName(descriptor);
        Intrinsics.checkNotNullExpressionValue(moduleName, "getModuleName(...)");
        if (!getModuleNameToInfo().keys().contains(moduleName)) {
            return null;
        }
        for (ModuleInfo moduleInfo : getModuleNameToInfo().get((Object) moduleName)) {
            Intrinsics.checkNotNull(moduleInfo);
            FunctionWithWrapper functionFromSource = readFunctionFromSource(descriptor, moduleInfo);
            if (functionFromSource != null) {
                return TuplesKt.to(functionFromSource, moduleInfo);
            }
        }
        return null;
    }

    private final FunctionWithWrapper readFunctionFromSource(CallableDescriptor descriptor, final ModuleInfo info) {
        FunctionWithWrapper functionWithWrapper;
        List<JsStatement> statements;
        String fileContent = info.getFileContent();
        String functionTag = Namer.getFunctionTag(descriptor, this.config, this.bindingContext);
        Intrinsics.checkNotNullExpressionValue(functionTag, "getFunctionTag(...)");
        String str = fileContent;
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, functionTag, 0, false, 6, (Object) null);
        if (iIndexOf$default < 0 && Intrinsics.areEqual(functionTag, "kotlin.untypedCharArrayF")) {
            functionTag = "kotlin.charArrayF";
            iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, "kotlin.charArrayF", 0, false, 6, (Object) null);
        }
        ArrayList<JsStatement> arrayList = null;
        if (iIndexOf$default < 0) {
            return null;
        }
        int length = iIndexOf$default + functionTag.length() + 1;
        while (length < fileContent.length() && FunctionReaderKt.isWhitespaceOrComma(fileContent.charAt(length))) {
            length++;
        }
        ShallowSubSequence shallowSubSequence = new ShallowSubSequence(str, length, fileContent.length());
        Pattern wrapFunctionRegex = info.getWrapFunctionRegex();
        Matcher matcher = wrapFunctionRegex != null ? wrapFunctionRegex.matcher(shallowSubSequence) : null;
        boolean z = matcher != null && matcher.lookingAt();
        if (z) {
            Intrinsics.checkNotNull(matcher);
            length += matcher.end();
        }
        CodePosition codePosition = info.getOffsetToSourceMapping().get(length);
        JsRootScope jsRootScope = new JsRootScope(new JsProgram());
        JsFunction function = ParserUtilsKt.parseFunction(fileContent, info.getFilePath(), codePosition, length, ThrowExceptionOnErrorReporter.INSTANCE, jsRootScope);
        if (function == null) {
            return null;
        }
        FixForwardNameReferencesKt.fixForwardNameReferences(function);
        if (z) {
            functionWithWrapper = InlineMetadata.INSTANCE.decomposeWrapper(function);
            if (functionWithWrapper == null) {
                return null;
            }
        } else {
            functionWithWrapper = new FunctionWithWrapper(function, null);
        }
        JsFunction function2 = functionWithWrapper.getFunction();
        JsBlock wrapperBody = functionWithWrapper.getWrapperBody();
        if (wrapperBody != null && (statements = wrapperBody.getStatements()) != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : statements) {
                if (!(((JsStatement) obj) instanceof JsReturn)) {
                    arrayList2.add(obj);
                }
            }
            arrayList = arrayList2;
        }
        SourceMap sourceMap = info.getSourceMap();
        if (sourceMap != null) {
            SourceMapLocationRemapper sourceMapLocationRemapper = new SourceMapLocationRemapper(sourceMap, new Function1<String, String>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$readFunctionFromSource$remapper$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    FunctionReader functionReader = this.this$0;
                    return functionReader.remapPath(functionReader.removeRedundantPathPrefix(it), info);
                }
            });
            sourceMapLocationRemapper.remap(function2);
            if (arrayList != null) {
                for (JsStatement jsStatement : arrayList) {
                    Intrinsics.checkNotNull(jsStatement);
                    sourceMapLocationRemapper.remap(jsStatement);
                }
            }
        }
        Set<JsName> setCollectDefinedNamesInAllScopes = CollectUtilsKt.collectDefinedNamesInAllScopes(function2);
        FunctionReaderKt.markInlineArguments(function2, descriptor);
        markDefaultParams(function2);
        markSpecialFunctions(function2, setCollectDefinedNamesInAllScopes, info, jsRootScope);
        final Set set = SequencesKt.toSet(SequencesKt.flatMap(CollectionsKt.asSequence(arrayList == null ? CollectionsKt.emptyList() : arrayList), new Function1<JsStatement, Sequence<? extends JsName>>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader$readFunctionFromSource$namesWithoutSideEffects$1
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<JsName> invoke(JsStatement jsStatement2) {
                Intrinsics.checkNotNull(jsStatement2);
                return CollectionsKt.asSequence(CollectUtilsKt.collectDefinedNames(jsStatement2));
            }
        }));
        function2.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader.readFunctionFromSource.2
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                if (CollectionsKt.contains(set, nameRef.getName()) && nameRef.getQualifier() == null) {
                    MetadataProperties.setSideEffects(nameRef, SideEffectKind.PURE);
                }
                super.visitNameRef(nameRef);
            }
        });
        if (arrayList != null) {
            for (JsStatement jsStatement2 : arrayList) {
                if (jsStatement2 instanceof JsVars) {
                    JsVars jsVars = (JsVars) jsStatement2;
                    if (jsVars.getVars().size() == 1) {
                        JsVars.JsVar jsVar = jsVars.getVars().get(0);
                        Intrinsics.checkNotNullExpressionValue(jsVar, "get(...)");
                        if (CollectUtilsKt.extractImportTag(jsVar) != null) {
                            JsName name2 = jsVars.getVars().get(0).getName();
                            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                            MetadataProperties.setImported(name2, true);
                        }
                    }
                }
            }
        }
        return new FunctionWithWrapper(function2, wrapperBody);
    }

    private final void markSpecialFunctions(JsFunction function, Set<? extends JsName> allDefinedNames, final ModuleInfo info, final JsScope scope) {
        for (JsName jsName : SetsKt.minus((Set) CollectUtilsKt.collectReferencedNames(function), (Iterable) allDefinedNames)) {
            SpecialFunction specialFunction = info.getSpecialFunctions().get(jsName.getIdent());
            if (specialFunction != null) {
                MetadataProperties.setSpecialFunction(jsName, specialFunction);
            }
        }
        function.getBody().accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.FunctionReader.markSpecialFunctions.2
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                super.visitNameRef(nameRef);
                markQualifiedSpecialFunction(nameRef);
            }

            private final void markQualifiedSpecialFunction(JsNameRef nameRef) {
                SpecialFunction specialFunction2;
                JsExpression qualifier = nameRef.getQualifier();
                JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
                if (jsNameRef != null && Intrinsics.areEqual(jsNameRef.getIdent(), info.getKotlinVariable()) && jsNameRef.getQualifier() == null) {
                    JsName name2 = nameRef.getName();
                    if ((name2 != null ? MetadataProperties.getSpecialFunction(name2) : null) == null && (specialFunction2 = (SpecialFunction) FunctionReaderKt.specialFunctionsByName.get(nameRef.getIdent())) != null) {
                        if (nameRef.getName() == null) {
                            nameRef.setName(scope.declareName(nameRef.getIdent()));
                        }
                        JsName name3 = nameRef.getName();
                        Intrinsics.checkNotNull(name3);
                        MetadataProperties.setSpecialFunction(name3, specialFunction2);
                    }
                }
            }
        });
    }

    private final void markDefaultParams(JsFunction function) {
        JsParameter jsParameter;
        Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable;
        List<JsParameter> parameters = function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list = parameters;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (JsParameter jsParameter2 : list) {
            Pair pair = TuplesKt.to(jsParameter2.getName(), jsParameter2);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        for (JsStatement jsStatement : function.getBody().getStatements()) {
            if (!(jsStatement instanceof JsIf)) {
                return;
            }
            JsIf jsIf = (JsIf) jsStatement;
            if (jsIf.getElseStatement() != null) {
                return;
            }
            JsStatement thenStatement = jsIf.getThenStatement();
            JsExpressionStatement jsExpressionStatement = thenStatement instanceof JsExpressionStatement ? (JsExpressionStatement) thenStatement : null;
            if (jsExpressionStatement == null) {
                return;
            }
            JsExpression ifExpression = jsIf.getIfExpression();
            JsBinaryOperation jsBinaryOperation = ifExpression instanceof JsBinaryOperation ? (JsBinaryOperation) ifExpression : null;
            if (jsBinaryOperation == null || jsBinaryOperation.getOperator() != JsBinaryOperator.REF_EQ) {
                return;
            }
            JsExpression arg1 = jsBinaryOperation.getArg1();
            JsNameRef jsNameRef = arg1 instanceof JsNameRef ? (JsNameRef) arg1 : null;
            if (jsNameRef == null || (jsParameter = (JsParameter) linkedHashMap.get(jsNameRef.getName())) == null || jsNameRef.getQualifier() != null) {
                return;
            }
            JsExpression arg2 = jsBinaryOperation.getArg2();
            JsPrefixOperation jsPrefixOperation = arg2 instanceof JsPrefixOperation ? (JsPrefixOperation) arg2 : null;
            if ((jsPrefixOperation != null ? jsPrefixOperation.getOperator() : null) != JsUnaryOperator.VOID || (pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(jsExpressionStatement.getExpression())) == null || !Intrinsics.areEqual(pairDecomposeAssignmentToVariable.component1(), jsNameRef.getName())) {
                return;
            } else {
                MetadataProperties.setHasDefaultValue(jsParameter, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String removeRedundantPathPrefix(String path) {
        int i = 0;
        while (true) {
            int i2 = i + 2;
            if (i2 > path.length()) {
                break;
            }
            String strSubstring = path.substring(i, i2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            if (!Intrinsics.areEqual(strSubstring, "./")) {
                break;
            }
            i = i2;
            while (i < path.length() && path.charAt(i) == '/') {
                i++;
            }
        }
        String strSubstring2 = path.substring(i);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        return strSubstring2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String remapPath(String path, ModuleInfo info) {
        File outputDir;
        RelativePathCalculator relativePathCalculator;
        String strCalculateRelativePathTo;
        return (!this.shouldRemapPathToRelativeForm || (outputDir = info.getOutputDir()) == null || (relativePathCalculator = this.relativePathCalculator) == null || (strCalculateRelativePathTo = relativePathCalculator.calculateRelativePathTo(new File(outputDir, path))) == null) ? path : strCalculateRelativePathTo;
    }
}
