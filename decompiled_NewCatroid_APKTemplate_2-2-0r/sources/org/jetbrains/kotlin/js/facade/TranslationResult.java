package org.jetbrains.kotlin.js.facade;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.backend.common.output.OutputFileCollection;
import org.jetbrains.kotlin.backend.common.output.SimpleOutputBinaryFile;
import org.jetbrains.kotlin.backend.common.output.SimpleOutputFile;
import org.jetbrains.kotlin.backend.common.output.SimpleOutputFileCollection;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.backend.JsToStringGenerationVisitor;
import org.jetbrains.kotlin.js.backend.NoOpSourceLocationConsumer;
import org.jetbrains.kotlin.js.backend.SourceLocationConsumer;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.config.SourceMapSourceEmbedding;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.sourceMap.SourceMap3Builder;
import org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer;
import org.jetbrains.kotlin.js.sourceMap.UtilsKt;
import org.jetbrains.kotlin.js.util.TextOutput;
import org.jetbrains.kotlin.js.util.TextOutputImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.serialization.js.JsModuleDescriptor;
import org.jetbrains.kotlin.serialization.js.JsSerializerProtocol;
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.utils.JsMetadataVersion;
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadataUtils;

/* JADX INFO: compiled from: TranslationResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/TranslationResult;", "", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "(Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;)V", "getDiagnostics", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "Fail", "Success", "SuccessBase", "SuccessNoCode", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TranslationResult {
    private final Diagnostics diagnostics;

    /* JADX INFO: compiled from: TranslationResult.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/TranslationResult$Fail;", "Lorg/jetbrains/kotlin/js/facade/TranslationResult;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "(Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;)V", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Fail extends TranslationResult {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Fail(Diagnostics diagnostics) {
            super(diagnostics);
            Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        }
    }

    protected TranslationResult(Diagnostics diagnostics) {
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        this.diagnostics = diagnostics;
    }

    public final Diagnostics getDiagnostics() {
        return this.diagnostics;
    }

    /* JADX INFO: compiled from: TranslationResult.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010¢\u0006\u0002\u0010\u0013J$\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020 2\b\u0010%\u001a\u0004\u0018\u00010 2\b\u0010&\u001a\u0004\u0018\u00010 H&J\u0018\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u0012H\u0002J\u0016\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u00052\u0006\u0010$\u001a\u00020 H\u0004R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/TranslationResult$SuccessBase;", "Lorg/jetbrains/kotlin/js/facade/TranslationResult;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "importedModules", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "packageMetadata", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "(Lorg/jetbrains/kotlin/js/config/JsConfig;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;Ljava/util/List;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Ljava/util/Map;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getConfig", "()Lorg/jetbrains/kotlin/js/config/JsConfig;", "getFiles", "()Ljava/util/List;", "getImportedModules", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getPackageMetadata", "()Ljava/util/Map;", "sourceFiles", "Ljava/io/File;", "getSourceFiles", "getOutputFiles", "Lorg/jetbrains/kotlin/backend/common/output/OutputFileCollection;", "outputFile", "outputPrefixFile", "outputPostfixFile", "kjsmFileForPackage", "Lorg/jetbrains/kotlin/backend/common/output/SimpleOutputBinaryFile;", "packageFqName", "bytes", "metadataFiles", "Lorg/jetbrains/kotlin/backend/common/output/OutputFile;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class SuccessBase extends TranslationResult {
        private final BindingContext bindingContext;
        private final JsConfig config;
        private final List<KtFile> files;
        private final List<String> importedModules;
        private final ModuleDescriptor moduleDescriptor;
        private final Map<FqName, byte[]> packageMetadata;
        private final List<File> sourceFiles;

        public abstract OutputFileCollection getOutputFiles(File outputFile, File outputPrefixFile, File outputPostfixFile);

        protected final JsConfig getConfig() {
            return this.config;
        }

        protected final List<KtFile> getFiles() {
            return this.files;
        }

        protected final List<String> getImportedModules() {
            return this.importedModules;
        }

        public final ModuleDescriptor getModuleDescriptor() {
            return this.moduleDescriptor;
        }

        public final BindingContext getBindingContext() {
            return this.bindingContext;
        }

        public final Map<FqName, byte[]> getPackageMetadata() {
            return this.packageMetadata;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public SuccessBase(JsConfig config, List<? extends KtFile> files, Diagnostics diagnostics, List<String> importedModules, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, Map<FqName, byte[]> packageMetadata) {
            File fileVirtualToIoFile;
            super(diagnostics);
            Intrinsics.checkNotNullParameter(config, "config");
            Intrinsics.checkNotNullParameter(files, "files");
            Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
            Intrinsics.checkNotNullParameter(importedModules, "importedModules");
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(packageMetadata, "packageMetadata");
            this.config = config;
            this.files = files;
            this.importedModules = importedModules;
            this.moduleDescriptor = moduleDescriptor;
            this.bindingContext = bindingContext;
            this.packageMetadata = packageMetadata;
            List<? extends KtFile> list = files;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (KtFile ktFile : list) {
                VirtualFile virtualFile = ktFile.getOriginalFile().getVirtualFile();
                if (virtualFile == null) {
                    fileVirtualToIoFile = new File(ktFile.get$name());
                } else {
                    fileVirtualToIoFile = VfsUtilCore.virtualToIoFile(virtualFile);
                    Intrinsics.checkNotNullExpressionValue(fileVirtualToIoFile, "virtualToIoFile(...)");
                }
                arrayList.add(fileVirtualToIoFile);
            }
            this.sourceFiles = arrayList;
        }

        protected final List<File> getSourceFiles() {
            return this.sourceFiles;
        }

        protected final List<OutputFile> metadataFiles(File outputFile) {
            Intrinsics.checkNotNullParameter(outputFile, "outputFile");
            if (this.config.getConfiguration().getBoolean(JSConfigurationKeys.META_INFO)) {
                KotlinJavascriptMetadataUtils kotlinJavascriptMetadataUtils = KotlinJavascriptMetadataUtils.INSTANCE;
                String name2 = outputFile.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                String strReplaceSuffix = kotlinJavascriptMetadataUtils.replaceSuffix(name2);
                String moduleId = this.config.getModuleId();
                ModuleDescriptor moduleDescriptor = this.moduleDescriptor;
                ModuleKind moduleKind = this.config.getModuleKind();
                List<String> list = this.importedModules;
                Intrinsics.checkNotNull(moduleId);
                Intrinsics.checkNotNull(moduleKind);
                JsModuleDescriptor jsModuleDescriptor = new JsModuleDescriptor(moduleId, moduleKind, list, moduleDescriptor);
                Map<FqName, byte[]> map = this.packageMetadata;
                CompilerConfiguration configuration = this.config.getConfiguration();
                Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
                LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
                Object obj = this.config.getConfiguration().get(CommonConfigurationKeys.METADATA_VERSION);
                JsMetadataVersion jsMetadataVersion = obj instanceof JsMetadataVersion ? (JsMetadataVersion) obj : null;
                if (jsMetadataVersion == null) {
                    jsMetadataVersion = JsMetadataVersion.INSTANCE;
                }
                KotlinJavascriptSerializationUtil.SerializedMetadata serializedMetadata = new KotlinJavascriptSerializationUtil.SerializedMetadata(map, jsModuleDescriptor, languageVersionSettings, jsMetadataVersion);
                List listListOf = CollectionsKt.listOf(new SimpleOutputFile(new ArrayList(this.sourceFiles), strReplaceSuffix, serializedMetadata.asString()));
                List<KotlinJavascriptSerializationUtil.SerializedMetadata.SerializedPackage> listSerializedPackages = serializedMetadata.serializedPackages();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSerializedPackages, 10));
                for (KotlinJavascriptSerializationUtil.SerializedMetadata.SerializedPackage serializedPackage : listSerializedPackages) {
                    arrayList.add(kjsmFileForPackage(serializedPackage.getFqName(), serializedPackage.getBytes()));
                }
                return CollectionsKt.plus((Collection) listListOf, (Iterable) arrayList);
            }
            return CollectionsKt.emptyList();
        }

        private final SimpleOutputBinaryFile kjsmFileForPackage(FqName packageFqName, byte[] bytes) {
            List listEmptyList = (Collection) this.bindingContext.get(BindingContext.PACKAGE_TO_FILES, packageFqName);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            Collection collection = listEmptyList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(VfsUtilCore.virtualToIoFile(((KtFile) it.next()).getVirtualFile()));
            }
            return new SimpleOutputBinaryFile(arrayList, this.config.getModuleId() + '/' + JsSerializerProtocol.INSTANCE.getKjsmFilePath(packageFqName), bytes);
        }
    }

    /* JADX INFO: compiled from: TranslationResult.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B]\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\u0010\u0015J\u0006\u0010\u0018\u001a\u00020\fJ\u001a\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J$\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010!2\b\u0010#\u001a\u0004\u0018\u00010!H\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/TranslationResult$Success;", "Lorg/jetbrains/kotlin/js/facade/TranslationResult$SuccessBase;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "importedModules", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "packageMetadata", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "(Lorg/jetbrains/kotlin/js/config/JsConfig;Ljava/util/List;Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;Ljava/util/List;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Ljava/util/Map;)V", "getProgram", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "getCode", "", "output", "Lorg/jetbrains/kotlin/js/util/TextOutput;", "sourceLocationConsumer", "Lorg/jetbrains/kotlin/js/backend/SourceLocationConsumer;", "getOutputFiles", "Lorg/jetbrains/kotlin/backend/common/output/OutputFileCollection;", "outputFile", "Ljava/io/File;", "outputPrefixFile", "outputPostfixFile", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Success extends SuccessBase {
        private final JsProgram program;

        public final JsProgram getProgram() {
            return this.program;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Success(JsConfig config, List<? extends KtFile> files, JsProgram program, Diagnostics diagnostics, List<String> importedModules, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, Map<FqName, byte[]> packageMetadata) {
            super(config, files, diagnostics, importedModules, moduleDescriptor, bindingContext, packageMetadata);
            Intrinsics.checkNotNullParameter(config, "config");
            Intrinsics.checkNotNullParameter(files, "files");
            Intrinsics.checkNotNullParameter(program, "program");
            Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
            Intrinsics.checkNotNullParameter(importedModules, "importedModules");
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(packageMetadata, "packageMetadata");
            this.program = program;
        }

        public final String getCode() {
            TextOutputImpl textOutputImpl = new TextOutputImpl();
            getCode(textOutputImpl, null);
            String string = textOutputImpl.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        @Override // org.jetbrains.kotlin.js.facade.TranslationResult.SuccessBase
        public OutputFileCollection getOutputFiles(File outputFile, File outputPrefixFile, File outputPostfixFile) {
            SourceMapBuilderConsumer sourceMapBuilderConsumer;
            String text$default;
            String text$default2;
            Intrinsics.checkNotNullParameter(outputFile, "outputFile");
            TextOutputImpl textOutputImpl = new TextOutputImpl();
            TranslationResult$Success$getOutputFiles$sourceMapBuilder$1 translationResult$Success$getOutputFiles$sourceMapBuilder$1 = new TranslationResult$Success$getOutputFiles$sourceMapBuilder$1(textOutputImpl);
            String sourceMapPrefix = getConfig().getSourceMapPrefix();
            Intrinsics.checkNotNullExpressionValue(sourceMapPrefix, "getSourceMapPrefix(...)");
            SourceMap3Builder sourceMap3Builder = new SourceMap3Builder(outputFile, translationResult$Success$getOutputFiles$sourceMapBuilder$1, sourceMapPrefix);
            if (getConfig().getConfiguration().getBoolean(JSConfigurationKeys.SOURCE_MAP)) {
                SourceMapSourceEmbedding sourceMapContentEmbedding = getConfig().getSourceMapContentEmbedding();
                Intrinsics.checkNotNullExpressionValue(sourceMapContentEmbedding, "getSourceMapContentEmbedding(...)");
                sourceMapBuilderConsumer = new SourceMapBuilderConsumer(new File("."), sourceMap3Builder, SourceFilePathResolver.INSTANCE.create(getConfig()), sourceMapContentEmbedding == SourceMapSourceEmbedding.ALWAYS, sourceMapContentEmbedding != SourceMapSourceEmbedding.NEVER);
            } else {
                sourceMapBuilderConsumer = null;
            }
            TextOutputImpl textOutputImpl2 = textOutputImpl;
            getCode(textOutputImpl2, sourceMapBuilderConsumer);
            if (sourceMapBuilderConsumer != null) {
                UtilsKt.addSourceMappingURL(textOutputImpl2, outputFile);
            }
            String string = textOutputImpl.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            String str = "";
            if (outputPrefixFile == null || (text$default = FilesKt.readText$default(outputPrefixFile, null, 1, null)) == null) {
                text$default = "";
            }
            if (outputPostfixFile != null && (text$default2 = FilesKt.readText$default(outputPostfixFile, null, 1, null)) != null) {
                str = text$default2;
            }
            List<File> sourceFiles = getSourceFiles();
            String name2 = outputFile.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            ArrayList arrayListArrayListOf = CollectionsKt.arrayListOf(new SimpleOutputFile(sourceFiles, name2, text$default + string + str));
            CollectionsKt.addAll(arrayListArrayListOf, metadataFiles(outputFile));
            if (sourceMapBuilderConsumer != null) {
                sourceMap3Builder.skipLinesAtBeginning(StringUtil.getLineBreakCount(text$default));
                List<File> sourceFiles2 = getSourceFiles();
                String name3 = sourceMap3Builder.getOutFile().getName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                arrayListArrayListOf.add(new SimpleOutputFile(sourceFiles2, name3, sourceMap3Builder.build()));
                UtilsKt.addSourceMappingURL(textOutputImpl2, outputFile);
            }
            return new SimpleOutputFileCollection(arrayListArrayListOf);
        }

        private final void getCode(TextOutput output, SourceLocationConsumer sourceLocationConsumer) {
            JsProgram jsProgram = this.program;
            if (sourceLocationConsumer == null) {
                sourceLocationConsumer = NoOpSourceLocationConsumer.INSTANCE;
            }
            jsProgram.accept(new JsToStringGenerationVisitor(output, sourceLocationConsumer));
        }
    }

    /* JADX INFO: compiled from: TranslationResult.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010¢\u0006\u0002\u0010\u0013J$\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u0017H\u0016¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/TranslationResult$SuccessNoCode;", "Lorg/jetbrains/kotlin/js/facade/TranslationResult$SuccessBase;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "importedModules", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "packageMetadata", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "(Lorg/jetbrains/kotlin/js/config/JsConfig;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;Ljava/util/List;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Ljava/util/Map;)V", "getOutputFiles", "Lorg/jetbrains/kotlin/backend/common/output/OutputFileCollection;", "outputFile", "Ljava/io/File;", "outputPrefixFile", "outputPostfixFile", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SuccessNoCode extends SuccessBase {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SuccessNoCode(JsConfig config, List<? extends KtFile> files, Diagnostics diagnostics, List<String> importedModules, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, Map<FqName, byte[]> packageMetadata) {
            super(config, files, diagnostics, importedModules, moduleDescriptor, bindingContext, packageMetadata);
            Intrinsics.checkNotNullParameter(config, "config");
            Intrinsics.checkNotNullParameter(files, "files");
            Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
            Intrinsics.checkNotNullParameter(importedModules, "importedModules");
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(packageMetadata, "packageMetadata");
        }

        @Override // org.jetbrains.kotlin.js.facade.TranslationResult.SuccessBase
        public OutputFileCollection getOutputFiles(File outputFile, File outputPrefixFile, File outputPostfixFile) {
            Intrinsics.checkNotNullParameter(outputFile, "outputFile");
            return new SimpleOutputFileCollection(metadataFiles(outputFile));
        }
    }
}
