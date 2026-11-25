package org.jetbrains.kotlin.js.config;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.js.resolve.JsPlatformAnalyzerServices;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.serialization.js.JsModuleDescriptor;
import org.jetbrains.kotlin.serialization.js.KotlinJavaScriptLibraryParts;
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptPackageFragmentProviderKt;
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.utils.JsMetadataVersion;
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadata;
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadataUtils;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsConfig {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String UNKNOWN_EXTERNAL_MODULE_NAME = "<unknown>";
    private final CompilerConfiguration configuration;
    private final IdentityHashMap<KotlinJavascriptMetadata, ModuleDescriptorImpl> factoryMap;
    private List<ModuleDescriptorImpl> friendModuleDescriptors;
    private final List<KotlinJavascriptMetadata> friends;
    private boolean initialized;
    private final Set<String> librariesToSkip;
    private final List<KotlinJavascriptMetadata> metadata;
    private final List<JsModuleDescriptor<KotlinJavaScriptLibraryParts>> metadataCache;
    private List<ModuleDescriptorImpl> moduleDescriptors;
    private final Project project;
    private final LockBasedStorageManager storageManager;
    public final TargetEnvironment targetEnvironment;
    public static final List<String> JS_STDLIB = Collections.singletonList(PathUtil.getKotlinPathsForDistDirectory().getJsStdLibJarPath().getAbsolutePath());
    public static final List<String> JS_KOTLIN_TEST = Collections.singletonList(PathUtil.getKotlinPathsForDistDirectory().getJsKotlinTestJarPath().getAbsolutePath());

    public static abstract class Reporter {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = CrashHianalyticsData.MESSAGE;
            objArr[1] = "org/jetbrains/kotlin/js/config/JsConfig$Reporter";
            if (i != 1) {
                objArr[2] = "error";
            } else {
                objArr[2] = CompilerOptions.WARNING;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public void error(String str) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
        }

        public void warning(String str) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 21:
            case 22:
            case 23:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 21:
            case 22:
            case 23:
                i2 = 2;
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "configuration";
                break;
            case 2:
            case 5:
                objArr[0] = "targetEnvironment";
                break;
            case 3:
            default:
                objArr[0] = "project";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 21:
            case 22:
            case 23:
                objArr[0] = "org/jetbrains/kotlin/js/config/JsConfig";
                break;
            case 17:
                objArr[0] = "expected";
                break;
            case 18:
            case 20:
                objArr[0] = "report";
                break;
            case 19:
                objArr[0] = "libraries";
                break;
        }
        switch (i) {
            case 6:
                objArr[1] = "getConfiguration";
                break;
            case 7:
                objArr[1] = "getProject";
                break;
            case 8:
                objArr[1] = "getModuleId";
                break;
            case 9:
                objArr[1] = "getModuleKind";
                break;
            case 10:
                objArr[1] = "getLibraries";
                break;
            case 11:
                objArr[1] = "getSourceMapPrefix";
                break;
            case 12:
                objArr[1] = "getSourceMapRoots";
                break;
            case 13:
                objArr[1] = "getSourceMapContentEmbedding";
                break;
            case 14:
            case 15:
                objArr[1] = "getFriends";
                break;
            case 16:
                objArr[1] = "getLanguageVersionSettings";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            default:
                objArr[1] = "org/jetbrains/kotlin/js/config/JsConfig";
                break;
            case 21:
                objArr[1] = "getModuleDescriptors";
                break;
            case 22:
                objArr[1] = "createModuleDescriptors";
                break;
            case 23:
                objArr[1] = "getFriendModuleDescriptors";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 21:
            case 22:
            case 23:
                break;
            case 17:
                objArr[2] = "isAtLeast";
                break;
            case 18:
            case 19:
            case 20:
                objArr[2] = "checkLibFilesAndReportErrors";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 21:
            case 22:
            case 23:
                throw new IllegalStateException(str2);
            case 17:
            case 18:
            case 19:
            case 20:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsConfig(Project project, CompilerConfiguration compilerConfiguration, TargetEnvironment targetEnvironment) {
        this(project, compilerConfiguration, targetEnvironment, null, null);
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (compilerConfiguration == null) {
            $$$reportNull$$$0(1);
        }
        if (targetEnvironment == null) {
            $$$reportNull$$$0(2);
        }
    }

    public JsConfig(Project project, CompilerConfiguration compilerConfiguration, TargetEnvironment targetEnvironment, List<JsModuleDescriptor<KotlinJavaScriptLibraryParts>> list, Set<String> set) {
        if (project == null) {
            $$$reportNull$$$0(3);
        }
        if (compilerConfiguration == null) {
            $$$reportNull$$$0(4);
        }
        if (targetEnvironment == null) {
            $$$reportNull$$$0(5);
        }
        this.storageManager = new LockBasedStorageManager("JsConfig");
        this.metadata = new SmartList();
        this.friends = new SmartList();
        this.initialized = false;
        this.factoryMap = new IdentityHashMap<>();
        this.project = project;
        this.configuration = compilerConfiguration.copy();
        this.targetEnvironment = targetEnvironment;
        this.metadataCache = list;
        this.librariesToSkip = set;
    }

    public CompilerConfiguration getConfiguration() {
        CompilerConfiguration compilerConfiguration = this.configuration;
        if (compilerConfiguration == null) {
            $$$reportNull$$$0(6);
        }
        return compilerConfiguration;
    }

    public Project getProject() {
        Project project = this.project;
        if (project == null) {
            $$$reportNull$$$0(7);
        }
        return project;
    }

    public String getModuleId() {
        String str = (String) this.configuration.getNotNull(CommonConfigurationKeys.MODULE_NAME);
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return str;
    }

    public ModuleKind getModuleKind() {
        ModuleKind moduleKind = (ModuleKind) this.configuration.get(JSConfigurationKeys.MODULE_KIND, ModuleKind.PLAIN);
        if (moduleKind == null) {
            $$$reportNull$$$0(9);
        }
        return moduleKind;
    }

    public List<String> getLibraries() {
        List<String> list = getConfiguration().getList(JSConfigurationKeys.LIBRARIES);
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        return list;
    }

    public String getSourceMapPrefix() {
        String str = (String) this.configuration.get(JSConfigurationKeys.SOURCE_MAP_PREFIX, "");
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return str;
    }

    public List<String> getSourceMapRoots() {
        List<String> list = (List) this.configuration.get(JSConfigurationKeys.SOURCE_MAP_SOURCE_ROOTS, Collections.emptyList());
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        return list;
    }

    public boolean shouldGenerateRelativePathsInSourceMap() {
        return getSourceMapPrefix().isEmpty() && getSourceMapRoots().isEmpty();
    }

    public SourceMapSourceEmbedding getSourceMapContentEmbedding() {
        SourceMapSourceEmbedding sourceMapSourceEmbedding = (SourceMapSourceEmbedding) this.configuration.get(JSConfigurationKeys.SOURCE_MAP_EMBED_SOURCES, SourceMapSourceEmbedding.INLINING);
        if (sourceMapSourceEmbedding == null) {
            $$$reportNull$$$0(13);
        }
        return sourceMapSourceEmbedding;
    }

    public List<String> getFriends() {
        if (getConfiguration().getBoolean(JSConfigurationKeys.FRIEND_PATHS_DISABLED)) {
            List<String> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(14);
            }
            return listEmptyList;
        }
        List<String> list = getConfiguration().getList(JSConfigurationKeys.FRIEND_PATHS);
        if (list == null) {
            $$$reportNull$$$0(15);
        }
        return list;
    }

    public LanguageVersionSettings getLanguageVersionSettings() {
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(this.configuration);
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(16);
        }
        return languageVersionSettings;
    }

    public boolean isAtLeast(LanguageVersion languageVersion) {
        if (languageVersion == null) {
            $$$reportNull$$$0(17);
        }
        LanguageVersion languageVersion2 = CommonConfigurationKeysKt.getLanguageVersionSettings(this.configuration).getLanguageVersion();
        return languageVersion2.getMajor() > languageVersion.getMajor() || (languageVersion2.getMajor() == languageVersion.getMajor() && languageVersion2.getMinor() >= languageVersion.getMinor());
    }

    public boolean checkLibFilesAndReportErrors(Reporter reporter) {
        if (reporter == null) {
            $$$reportNull$$$0(18);
        }
        return checkLibFilesAndReportErrors(getLibraries(), reporter);
    }

    private boolean checkLibFilesAndReportErrors(Collection<String> collection, Reporter reporter) {
        if (collection == null) {
            $$$reportNull$$$0(19);
        }
        if (reporter == null) {
            $$$reportNull$$$0(20);
        }
        if (collection.isEmpty()) {
            return false;
        }
        HashSet hashSet = new HashSet();
        boolean zBooleanValue = ((Boolean) getLanguageVersionSettings().getFlag(AnalysisFlags.getSkipMetadataVersionCheck())).booleanValue();
        for (String str : collection) {
            Set<String> set = this.librariesToSkip;
            if (set == null || !set.contains(str)) {
                if (!new File(str).exists()) {
                    reporter.error("Path '" + str + "' does not exist");
                    return true;
                }
                List<KotlinJavascriptMetadata> listLoadMetadata = KotlinJavascriptMetadataUtils.loadMetadata(str);
                if (listLoadMetadata.isEmpty()) {
                    reporter.warning(FormatHelper.QUOTE + str + "' is not a valid Kotlin Javascript library");
                } else {
                    LinkedHashSet<String> linkedHashSet = new LinkedHashSet();
                    for (KotlinJavascriptMetadata kotlinJavascriptMetadata : listLoadMetadata) {
                        if (!kotlinJavascriptMetadata.getVersion().isCompatibleWithCurrentCompilerVersion() && !zBooleanValue) {
                            reporter.error("File '" + str + "' was compiled with an incompatible version of Kotlin. The binary version of its metadata is " + kotlinJavascriptMetadata.getVersion() + ", expected version is " + JsMetadataVersion.INSTANCE);
                            return true;
                        }
                        linkedHashSet.add(kotlinJavascriptMetadata.getModuleName());
                    }
                    for (String str2 : linkedHashSet) {
                        if (!hashSet.add(str2)) {
                            reporter.warning("Module \"" + str2 + "\" is defined in more than one file");
                        }
                    }
                    if (hashSet.contains(getModuleId())) {
                        reporter.warning("Module \"" + getModuleId() + "\" depends on module with the same name");
                    }
                    HashSet hashSet2 = new HashSet(getFriends());
                    this.metadata.addAll(listLoadMetadata);
                    if (hashSet2.contains(str)) {
                        this.friends.addAll(listLoadMetadata);
                    }
                }
            }
        }
        this.initialized = true;
        return false;
    }

    public List<ModuleDescriptorImpl> getModuleDescriptors() {
        init();
        List<ModuleDescriptorImpl> list = this.moduleDescriptors;
        if (list == null) {
            $$$reportNull$$$0(21);
        }
        return list;
    }

    private List<ModuleDescriptorImpl> createModuleDescriptors() {
        SmartList smartList = new SmartList();
        Iterator<KotlinJavascriptMetadata> it = this.metadata.iterator();
        while (it.hasNext()) {
            smartList.add(createModuleDescriptor(it.next()));
        }
        if (this.metadataCache != null) {
            LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(this.configuration);
            for (JsModuleDescriptor<KotlinJavaScriptLibraryParts> jsModuleDescriptor : this.metadataCache) {
                ModuleDescriptorImpl moduleDescriptorImpl = new ModuleDescriptorImpl(Name.special("<" + jsModuleDescriptor.getName() + ">"), this.storageManager, JsPlatformAnalyzerServices.INSTANCE.getBuiltIns());
                KotlinJavaScriptLibraryParts data = jsModuleDescriptor.getData();
                moduleDescriptorImpl.initialize(KotlinJavascriptPackageFragmentProviderKt.createKotlinJavascriptPackageFragmentProvider(this.storageManager, moduleDescriptorImpl, data.getHeader(), data.getBody(), data.getMetadataVersion(), new CompilerDeserializationConfiguration(languageVersionSettings), LookupTracker.DO_NOTHING.INSTANCE));
                smartList.add(moduleDescriptorImpl);
            }
        }
        Iterator<E> it2 = smartList.iterator();
        while (it2.hasNext()) {
            setDependencies((ModuleDescriptorImpl) it2.next(), smartList);
        }
        List<ModuleDescriptorImpl> listUnmodifiableList = Collections.unmodifiableList(smartList);
        if (listUnmodifiableList == null) {
            $$$reportNull$$$0(22);
        }
        return listUnmodifiableList;
    }

    public List<ModuleDescriptorImpl> getFriendModuleDescriptors() {
        init();
        List<ModuleDescriptorImpl> list = this.friendModuleDescriptors;
        if (list == null) {
            $$$reportNull$$$0(23);
        }
        return list;
    }

    public void init() {
        if (!this.initialized) {
            checkLibFilesAndReportErrors(new Reporter() { // from class: org.jetbrains.kotlin.js.config.JsConfig.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CrashHianalyticsData.MESSAGE, "org/jetbrains/kotlin/js/config/JsConfig$1", "error"));
                }

                @Override // org.jetbrains.kotlin.js.config.JsConfig.Reporter
                public void error(String str) {
                    if (str == null) {
                        $$$reportNull$$$0(0);
                    }
                    throw new IllegalStateException(str);
                }
            });
        }
        if (this.moduleDescriptors == null) {
            this.moduleDescriptors = createModuleDescriptors();
        }
        if (this.friendModuleDescriptors == null) {
            this.friendModuleDescriptors = CollectionsKt.map(this.friends, new Function1() { // from class: org.jetbrains.kotlin.js.config.JsConfig$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f$0.createModuleDescriptor((KotlinJavascriptMetadata) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ModuleDescriptorImpl createModuleDescriptor(KotlinJavascriptMetadata kotlinJavascriptMetadata) {
        return this.factoryMap.computeIfAbsent(kotlinJavascriptMetadata, new Function() { // from class: org.jetbrains.kotlin.js.config.JsConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7278xe1f2971a((KotlinJavascriptMetadata) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$createModuleDescriptor$0$org-jetbrains-kotlin-js-config-JsConfig, reason: not valid java name */
    /* synthetic */ ModuleDescriptorImpl m7278xe1f2971a(KotlinJavascriptMetadata kotlinJavascriptMetadata) {
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(this.configuration);
        ModuleDescriptorImpl moduleDescriptorImpl = new ModuleDescriptorImpl(Name.special("<" + kotlinJavascriptMetadata.getModuleName() + ">"), this.storageManager, JsPlatformAnalyzerServices.INSTANCE.getBuiltIns());
        LookupTracker lookupTracker = (LookupTracker) this.configuration.get(CommonConfigurationKeys.LOOKUP_TRACKER, LookupTracker.DO_NOTHING.INSTANCE);
        KotlinJavaScriptLibraryParts moduleAsProto = KotlinJavascriptSerializationUtil.readModuleAsProto(kotlinJavascriptMetadata.getBody(), kotlinJavascriptMetadata.getVersion());
        moduleDescriptorImpl.initialize(KotlinJavascriptPackageFragmentProviderKt.createKotlinJavascriptPackageFragmentProvider(this.storageManager, moduleDescriptorImpl, moduleAsProto.getHeader(), moduleAsProto.getBody(), kotlinJavascriptMetadata.getVersion(), new CompilerDeserializationConfiguration(languageVersionSettings), lookupTracker));
        return moduleDescriptorImpl;
    }

    private static void setDependencies(ModuleDescriptorImpl moduleDescriptorImpl, List<ModuleDescriptorImpl> list) {
        moduleDescriptorImpl.setDependencies(CollectionsKt.plus((Collection<? extends ModuleDescriptorImpl>) list, JsPlatformAnalyzerServices.INSTANCE.getBuiltIns().getBuiltInsModule()));
    }
}
