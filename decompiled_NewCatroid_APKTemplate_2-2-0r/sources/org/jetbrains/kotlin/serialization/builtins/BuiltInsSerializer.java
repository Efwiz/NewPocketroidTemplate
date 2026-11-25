package org.jetbrains.kotlin.serialization.builtins;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltInClassDescriptorFactory;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.config.ContentRootsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.cli.metadata.CommonAnalysisResult;
import org.jetbrains.kotlin.cli.metadata.MetadataSerializer;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: BuiltInsSerializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0014J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0014¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/serialization/builtins/BuiltInsSerializer;", "Lorg/jetbrains/kotlin/cli/metadata/MetadataSerializer;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "dependOnOldBuiltIns", "", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;Z)V", "createCloneable", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "createSerializerExtension", "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "serialize", "", "analysisResult", "Lorg/jetbrains/kotlin/cli/metadata/CommonAnalysisResult;", "destDir", "Ljava/io/File;", "Companion", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltInsSerializer extends MetadataSerializer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: BuiltInsSerializer.kt */
    @Metadata(d1 = {"\u0000=\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\b\u0003*\u0001\u0014\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jj\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\n\u001a\u00020\u000b26\u0010\f\u001a2\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00040\rJ\r\u0010\u0013\u001a\u00020\u0014H\u0002¢\u0006\u0002\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/serialization/builtins/BuiltInsSerializer$Companion;", "", "()V", "analyzeAndSerialize", "", "destDir", "Ljava/io/File;", "srcDirs", "", "extraClassPath", "dependOnOldBuiltIns", "", "onComplete", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "totalSize", "totalFiles", "createMessageCollector", "org/jetbrains/kotlin/serialization/builtins/BuiltInsSerializer$Companion$createMessageCollector$1", "()Lorg/jetbrains/kotlin/serialization/builtins/BuiltInsSerializer$Companion$createMessageCollector$1;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void analyzeAndSerialize(File destDir, List<? extends File> srcDirs, List<? extends File> extraClassPath, boolean dependOnOldBuiltIns, Function2<? super Integer, ? super Integer, Unit> onComplete) {
            Intrinsics.checkNotNullParameter(destDir, "destDir");
            Intrinsics.checkNotNullParameter(srcDirs, "srcDirs");
            Intrinsics.checkNotNullParameter(extraClassPath, "extraClassPath");
            Intrinsics.checkNotNullParameter(onComplete, "onComplete");
            Disposable disposableNewDisposable = Disposer.newDisposable();
            Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
            BuiltInsSerializer$Companion$createMessageCollector$1 builtInsSerializer$Companion$createMessageCollector$1CreateMessageCollector = createMessageCollector();
            CommonCompilerPerformanceManager commonCompilerPerformanceManager = new CommonCompilerPerformanceManager() { // from class: org.jetbrains.kotlin.serialization.builtins.BuiltInsSerializer$Companion$analyzeAndSerialize$performanceManager$1
            };
            try {
                CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
                compilerConfiguration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, builtInsSerializer$Companion$createMessageCollector$1CreateMessageCollector);
                List<? extends File> list = srcDirs;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(((File) it.next()).getPath());
                }
                ContentRootsKt.addKotlinSourceRoots(compilerConfiguration, arrayList);
                JvmContentRootsKt.addJvmClasspathRoots(compilerConfiguration, extraClassPath);
                JvmContentRootsKt.configureJdkClasspathRoots(compilerConfiguration);
                compilerConfiguration.put(CLIConfigurationKeys.METADATA_DESTINATION_DIRECTORY, destDir);
                compilerConfiguration.put(CommonConfigurationKeys.MODULE_NAME, "module for built-ins serialization");
                compilerConfiguration.put(CLIConfigurationKeys.PERF_MANAGER, commonCompilerPerformanceManager);
                BuiltInsSerializer builtInsSerializer = new BuiltInsSerializer(compilerConfiguration, KotlinCoreEnvironment.INSTANCE.createForProduction(disposableNewDisposable, compilerConfiguration, EnvironmentConfigFiles.JVM_CONFIG_FILES), dependOnOldBuiltIns);
                builtInsSerializer.analyzeAndSerialize();
                onComplete.invoke(Integer.valueOf(builtInsSerializer.getTotalSize()), Integer.valueOf(builtInsSerializer.getTotalFiles()));
            } finally {
                builtInsSerializer$Companion$createMessageCollector$1CreateMessageCollector.flush();
                Disposer.dispose(disposableNewDisposable);
            }
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [org.jetbrains.kotlin.serialization.builtins.BuiltInsSerializer$Companion$createMessageCollector$1] */
        private final BuiltInsSerializer$Companion$createMessageCollector$1 createMessageCollector() {
            final PrintingMessageCollector printingMessageCollector = new PrintingMessageCollector(System.err, MessageRenderer.PLAIN_RELATIVE_PATHS, false);
            return new GroupingMessageCollector(printingMessageCollector) { // from class: org.jetbrains.kotlin.serialization.builtins.BuiltInsSerializer$Companion$createMessageCollector$1
                {
                    super(printingMessageCollector, false);
                }

                @Override // org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector, org.jetbrains.kotlin.cli.common.messages.MessageCollector
                public void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) {
                    Intrinsics.checkNotNullParameter(severity, "severity");
                    Intrinsics.checkNotNullParameter(message, "message");
                    if (location == null) {
                        super.report(severity, message, location);
                    }
                }
            };
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuiltInsSerializer(CompilerConfiguration configuration, KotlinCoreEnvironment environment, boolean z) {
        super(configuration, environment, z, BuiltInsBinaryVersion.INSTANCE);
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(environment, "environment");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.metadata.MetadataSerializer, org.jetbrains.kotlin.cli.metadata.AbstractMetadataSerializer
    public void serialize(CommonAnalysisResult analysisResult, File destDir) throws IOException {
        Intrinsics.checkNotNullParameter(analysisResult, "analysisResult");
        Intrinsics.checkNotNullParameter(destDir, "destDir");
        List<KtFile> sourceFiles = getEnvironment().getSourceFiles();
        ModuleDescriptor moduleDescriptor = analysisResult.getModuleDescriptor();
        FilesKt.deleteRecursively(destDir);
        if (!destDir.mkdirs()) {
            throw new AssertionError("Could not make directories: " + destDir);
        }
        List<KtFile> list = sourceFiles;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((KtFile) it.next()).getPackageFqName());
        }
        Iterator it2 = CollectionsKt.toSet(arrayList).iterator();
        while (it2.hasNext()) {
            PackageViewDescriptor packageViewDescriptor = moduleDescriptor.getPackage((FqName) it2.next());
            BuiltInsSerializer builtInsSerializer = this;
            List listPlus = CollectionsKt.plus((Collection<? extends ClassDescriptor>) ResolutionScope.DefaultImpls.getContributedDescriptors$default(packageViewDescriptor.getMemberScope(), DescriptorKindFilter.CLASSIFIERS, null, 2, null), createCloneable(moduleDescriptor));
            List<PackageFragmentDescriptor> fragments = packageViewDescriptor.getFragments();
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it3 = fragments.iterator();
            while (it3.hasNext()) {
                Collection<DeclarationDescriptor> allDescriptors = DescriptorUtils.getAllDescriptors(((PackageFragmentDescriptor) it3.next()).getScope());
                Intrinsics.checkNotNullExpressionValue(allDescriptors, "getAllDescriptors(...)");
                CollectionsKt.addAll(arrayList2, allDescriptors);
            }
            new MetadataSerializer.PackageSerializer(builtInsSerializer, listPlus, arrayList2, packageViewDescriptor.getFqName(), new File(destDir, BuiltInSerializerProtocol.INSTANCE.getBuiltInsFilePath(packageViewDescriptor.getFqName())), CommonConfigurationKeysKt.getLanguageVersionSettings(getEnvironment().getConfiguration()), null, 32, null).run();
        }
    }

    @Override // org.jetbrains.kotlin.cli.metadata.MetadataSerializer
    protected KotlinSerializerExtensionBase createSerializerExtension() {
        return new BuiltInsSerializerExtension();
    }

    private final ClassDescriptor createCloneable(final ModuleDescriptor module) {
        StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
        Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
        JvmBuiltInClassDescriptorFactory jvmBuiltInClassDescriptorFactory = new JvmBuiltInClassDescriptorFactory(NO_LOCKS, module, new Function1<ModuleDescriptor, DeclarationDescriptor>() { // from class: org.jetbrains.kotlin.serialization.builtins.BuiltInsSerializer$createCloneable$factory$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final DeclarationDescriptor invoke(ModuleDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new EmptyPackageFragmentDescriptor(module, StandardNames.BUILT_INS_PACKAGE_FQ_NAME);
            }
        });
        ClassId classId = ClassId.topLevel(StandardNames.FqNames.cloneable.toSafe());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        ClassDescriptor classDescriptorCreateClass = jvmBuiltInClassDescriptorFactory.createClass(classId);
        if (classDescriptorCreateClass != null) {
            return classDescriptorCreateClass;
        }
        throw new IllegalStateException(("Could not create kotlin.Cloneable in " + module).toString());
    }
}
