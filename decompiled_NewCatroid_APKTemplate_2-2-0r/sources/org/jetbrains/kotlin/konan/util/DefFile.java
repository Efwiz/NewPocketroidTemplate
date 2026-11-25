package org.jetbrains.kotlin.konan.util;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.library.KonanLibraryKt;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: DefFile.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001:\u0001 B1\b\u0012\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u001e\u0010\u0004\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005¢\u0006\u0002\u0010\tB%\b\u0016\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\f¢\u0006\u0002\u0010\rB-\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0006\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\u0012R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0010\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001b\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001d¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DefFile;", "", "file0", "Ljava/io/File;", "triple", "Lkotlin/Triple;", "Ljava/util/Properties;", "", "", "(Ljava/io/File;Lkotlin/Triple;)V", "file", "substitutions", "", "(Ljava/io/File;Ljava/util/Map;)V", "config", "Lorg/jetbrains/kotlin/konan/util/DefFile$DefFileConfig;", "manifestAddendProperties", "defHeaderLines", "(Ljava/io/File;Lorg/jetbrains/kotlin/konan/util/DefFile$DefFileConfig;Ljava/util/Properties;Ljava/util/List;)V", "getConfig", "()Lorg/jetbrains/kotlin/konan/util/DefFile$DefFileConfig;", "getDefHeaderLines", "()Ljava/util/List;", "getFile", "()Ljava/io/File;", "getManifestAddendProperties", "()Ljava/util/Properties;", "name", "getName", "()Ljava/lang/String;", "name$delegate", "Lkotlin/Lazy;", "DefFileConfig", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefFile {
    private final DefFileConfig config;
    private final List<String> defHeaderLines;
    private final File file;
    private final Properties manifestAddendProperties;

    /* JADX INFO: renamed from: name$delegate, reason: from kotlin metadata */
    private final Lazy name;

    public DefFile(File file, DefFileConfig config, Properties manifestAddendProperties, List<String> defHeaderLines) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(manifestAddendProperties, "manifestAddendProperties");
        Intrinsics.checkNotNullParameter(defHeaderLines, "defHeaderLines");
        this.file = file;
        this.config = config;
        this.manifestAddendProperties = manifestAddendProperties;
        this.defHeaderLines = defHeaderLines;
        this.name = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$name$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                String nameWithoutExtension;
                File file2 = this.this$0.getFile();
                return (file2 == null || (nameWithoutExtension = FilesKt.getNameWithoutExtension(file2)) == null) ? "" : nameWithoutExtension;
            }
        });
    }

    public final DefFileConfig getConfig() {
        return this.config;
    }

    public final List<String> getDefHeaderLines() {
        return this.defHeaderLines;
    }

    public final File getFile() {
        return this.file;
    }

    public final Properties getManifestAddendProperties() {
        return this.manifestAddendProperties;
    }

    private DefFile(File file, Triple<? extends Properties, ? extends Properties, ? extends List<String>> triple) {
        this(file, new DefFileConfig(triple.getFirst()), triple.getSecond(), triple.getThird());
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DefFile(File file, Map<String, String> substitutions) {
        this(file, (Triple<? extends Properties, ? extends Properties, ? extends List<String>>) DefFileKt.parseDefFile(file, substitutions));
        Intrinsics.checkNotNullParameter(substitutions, "substitutions");
    }

    public final String getName() {
        return (String) this.name.getValue();
    }

    /* JADX INFO: compiled from: DefFile.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\bK\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\r\u0010\tR!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000b\u001a\u0004\b\u0010\u0010\tR\u001b\u0010\u0012\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000b\u001a\u0004\b\u0014\u0010\u0015R!\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000b\u001a\u0004\b\u0018\u0010\tR\u001b\u0010\u001a\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u000b\u001a\u0004\b\u001b\u0010\u0015R!\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u000b\u001a\u0004\b\u001e\u0010\tR\u001b\u0010 \u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u000b\u001a\u0004\b!\u0010\u0015R!\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u000b\u001a\u0004\b$\u0010\tR!\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\u000b\u001a\u0004\b'\u0010\tR!\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u000b\u001a\u0004\b*\u0010\tR#\u0010,\u001a\n -*\u0004\u0018\u00010\u00070\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\u000b\u001a\u0004\b.\u0010/R!\u00101\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b3\u0010\u000b\u001a\u0004\b2\u0010\tR!\u00104\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b6\u0010\u000b\u001a\u0004\b5\u0010\tR#\u00107\u001a\n -*\u0004\u0018\u00010\u00070\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b9\u0010\u000b\u001a\u0004\b8\u0010/R!\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b<\u0010\u000b\u001a\u0004\b;\u0010\tR#\u0010=\u001a\n -*\u0004\u0018\u00010\u00070\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b?\u0010\u000b\u001a\u0004\b>\u0010/R!\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bB\u0010\u000b\u001a\u0004\bA\u0010\tR!\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bE\u0010\u000b\u001a\u0004\bD\u0010\tR!\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bH\u0010\u000b\u001a\u0004\bG\u0010\tR!\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bK\u0010\u000b\u001a\u0004\bJ\u0010\tR!\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bN\u0010\u000b\u001a\u0004\bM\u0010\tR#\u0010O\u001a\n -*\u0004\u0018\u00010\u00070\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bQ\u0010\u000b\u001a\u0004\bP\u0010/R#\u0010R\u001a\n -*\u0004\u0018\u00010\u00070\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bT\u0010\u000b\u001a\u0004\bS\u0010/R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bW\u0010\u000b\u001a\u0004\bV\u0010\tR!\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bZ\u0010\u000b\u001a\u0004\bY\u0010\tR#\u0010[\u001a\n -*\u0004\u0018\u00010\u00070\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b]\u0010\u000b\u001a\u0004\b\\\u0010/¨\u0006^"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DefFile$DefFileConfig;", "", "properties", "Ljava/util/Properties;", "(Ljava/util/Properties;)V", "allowedOverloadsForCFunctions", "", "", "getAllowedOverloadsForCFunctions", "()Ljava/util/List;", "allowedOverloadsForCFunctions$delegate", "Lkotlin/Lazy;", "compilerOpts", "getCompilerOpts", "compilerOpts$delegate", KotlinLibraryKt.KLIB_PROPERTY_DEPENDS, "getDepends", "depends$delegate", "disableDesignatedInitializerChecks", "", "getDisableDesignatedInitializerChecks", "()Z", "disableDesignatedInitializerChecks$delegate", "entryPoints", "getEntryPoints", "entryPoints$delegate", "excludeDependentModules", "getExcludeDependentModules", "excludeDependentModules$delegate", "excludeFilter", "getExcludeFilter", "excludeFilter$delegate", "excludeSystemLibs", "getExcludeSystemLibs", "excludeSystemLibs$delegate", "excludedFunctions", "getExcludedFunctions", "excludedFunctions$delegate", "excludedMacros", "getExcludedMacros", "excludedMacros$delegate", KotlinLibraryKt.KLIB_PROPERTY_EXPORT_FORWARD_DECLARATIONS, "getExportForwardDeclarations", "exportForwardDeclarations$delegate", "foreignExceptionMode", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getForeignExceptionMode", "()Ljava/lang/String;", "foreignExceptionMode$delegate", "headerFilter", "getHeaderFilter", "headerFilter$delegate", "headers", "getHeaders", "headers$delegate", "language", "getLanguage", "language$delegate", "libraryPaths", "getLibraryPaths", "libraryPaths$delegate", "linker", "getLinker", "linker$delegate", KonanLibraryKt.KLIB_PROPERTY_LINKED_OPTS, "getLinkerOpts", "linkerOpts$delegate", "modules", "getModules", "modules$delegate", "noStringConversion", "getNoStringConversion", "noStringConversion$delegate", "nonStrictEnums", "getNonStrictEnums", "nonStrictEnums$delegate", "objcClassesIncludingCategories", "getObjcClassesIncludingCategories", "objcClassesIncludingCategories$delegate", "packageName", "getPackageName", "packageName$delegate", "pluginName", "getPluginName", "pluginName$delegate", "staticLibraries", "getStaticLibraries", "staticLibraries$delegate", "strictEnums", "getStrictEnums", "strictEnums$delegate", "userSetupHint", "getUserSetupHint", "userSetupHint$delegate", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class DefFileConfig {

        /* JADX INFO: renamed from: allowedOverloadsForCFunctions$delegate, reason: from kotlin metadata */
        private final Lazy allowedOverloadsForCFunctions;

        /* JADX INFO: renamed from: compilerOpts$delegate, reason: from kotlin metadata */
        private final Lazy compilerOpts;

        /* JADX INFO: renamed from: depends$delegate, reason: from kotlin metadata */
        private final Lazy depends;

        /* JADX INFO: renamed from: disableDesignatedInitializerChecks$delegate, reason: from kotlin metadata */
        private final Lazy disableDesignatedInitializerChecks;

        /* JADX INFO: renamed from: entryPoints$delegate, reason: from kotlin metadata */
        private final Lazy entryPoints;

        /* JADX INFO: renamed from: excludeDependentModules$delegate, reason: from kotlin metadata */
        private final Lazy excludeDependentModules;

        /* JADX INFO: renamed from: excludeFilter$delegate, reason: from kotlin metadata */
        private final Lazy excludeFilter;

        /* JADX INFO: renamed from: excludeSystemLibs$delegate, reason: from kotlin metadata */
        private final Lazy excludeSystemLibs;

        /* JADX INFO: renamed from: excludedFunctions$delegate, reason: from kotlin metadata */
        private final Lazy excludedFunctions;

        /* JADX INFO: renamed from: excludedMacros$delegate, reason: from kotlin metadata */
        private final Lazy excludedMacros;

        /* JADX INFO: renamed from: exportForwardDeclarations$delegate, reason: from kotlin metadata */
        private final Lazy exportForwardDeclarations;

        /* JADX INFO: renamed from: foreignExceptionMode$delegate, reason: from kotlin metadata */
        private final Lazy foreignExceptionMode;

        /* JADX INFO: renamed from: headerFilter$delegate, reason: from kotlin metadata */
        private final Lazy headerFilter;

        /* JADX INFO: renamed from: headers$delegate, reason: from kotlin metadata */
        private final Lazy headers;

        /* JADX INFO: renamed from: language$delegate, reason: from kotlin metadata */
        private final Lazy language;

        /* JADX INFO: renamed from: libraryPaths$delegate, reason: from kotlin metadata */
        private final Lazy libraryPaths;

        /* JADX INFO: renamed from: linker$delegate, reason: from kotlin metadata */
        private final Lazy linker;

        /* JADX INFO: renamed from: linkerOpts$delegate, reason: from kotlin metadata */
        private final Lazy linkerOpts;

        /* JADX INFO: renamed from: modules$delegate, reason: from kotlin metadata */
        private final Lazy modules;

        /* JADX INFO: renamed from: noStringConversion$delegate, reason: from kotlin metadata */
        private final Lazy noStringConversion;

        /* JADX INFO: renamed from: nonStrictEnums$delegate, reason: from kotlin metadata */
        private final Lazy nonStrictEnums;

        /* JADX INFO: renamed from: objcClassesIncludingCategories$delegate, reason: from kotlin metadata */
        private final Lazy objcClassesIncludingCategories;

        /* JADX INFO: renamed from: packageName$delegate, reason: from kotlin metadata */
        private final Lazy packageName;

        /* JADX INFO: renamed from: pluginName$delegate, reason: from kotlin metadata */
        private final Lazy pluginName;
        private final Properties properties;

        /* JADX INFO: renamed from: staticLibraries$delegate, reason: from kotlin metadata */
        private final Lazy staticLibraries;

        /* JADX INFO: renamed from: strictEnums$delegate, reason: from kotlin metadata */
        private final Lazy strictEnums;

        /* JADX INFO: renamed from: userSetupHint$delegate, reason: from kotlin metadata */
        private final Lazy userSetupHint;

        public DefFileConfig(Properties properties) {
            Intrinsics.checkNotNullParameter(properties, "properties");
            this.properties = properties;
            this.headers = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$headers$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "headers");
                }
            });
            this.modules = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$modules$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "modules");
                }
            });
            this.language = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$language$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return this.this$0.properties.getProperty("language");
                }
            });
            this.compilerOpts = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$compilerOpts$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "compilerOpts");
                }
            });
            this.excludeSystemLibs = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$excludeSystemLibs$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Boolean invoke() {
                    String property = this.this$0.properties.getProperty("excludeSystemLibs");
                    return Boolean.valueOf(property != null ? Boolean.parseBoolean(property) : false);
                }
            });
            this.excludeDependentModules = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$excludeDependentModules$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Boolean invoke() {
                    String property = this.this$0.properties.getProperty("excludeDependentModules");
                    return Boolean.valueOf(property != null ? Boolean.parseBoolean(property) : false);
                }
            });
            this.entryPoints = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$entryPoints$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "entryPoint");
                }
            });
            this.linkerOpts = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$linkerOpts$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, KonanLibraryKt.KLIB_PROPERTY_LINKED_OPTS);
                }
            });
            this.linker = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$linker$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return this.this$0.properties.getProperty("linker", "clang");
                }
            });
            this.excludedFunctions = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$excludedFunctions$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "excludedFunctions");
                }
            });
            this.excludedMacros = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$excludedMacros$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "excludedMacros");
                }
            });
            this.staticLibraries = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$staticLibraries$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "staticLibraries");
                }
            });
            this.libraryPaths = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$libraryPaths$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "libraryPaths");
                }
            });
            this.packageName = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$packageName$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return this.this$0.properties.getProperty("package");
                }
            });
            this.headerFilter = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$headerFilter$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "headerFilter");
                }
            });
            this.excludeFilter = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$excludeFilter$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "excludeFilter");
                }
            });
            this.strictEnums = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$strictEnums$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "strictEnums");
                }
            });
            this.nonStrictEnums = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$nonStrictEnums$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "nonStrictEnums");
                }
            });
            this.noStringConversion = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$noStringConversion$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "noStringConversion");
                }
            });
            this.depends = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$depends$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, KotlinLibraryKt.KLIB_PROPERTY_DEPENDS);
                }
            });
            this.exportForwardDeclarations = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$exportForwardDeclarations$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, KotlinLibraryKt.KLIB_PROPERTY_EXPORT_FORWARD_DECLARATIONS);
                }
            });
            this.allowedOverloadsForCFunctions = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$allowedOverloadsForCFunctions$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "allowedOverloadsForCFunctions");
                }
            });
            this.disableDesignatedInitializerChecks = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$disableDesignatedInitializerChecks$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Boolean invoke() {
                    String property = this.this$0.properties.getProperty("disableDesignatedInitializerChecks");
                    return Boolean.valueOf(property != null ? Boolean.parseBoolean(property) : false);
                }
            });
            this.foreignExceptionMode = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$foreignExceptionMode$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return this.this$0.properties.getProperty("foreignExceptionMode");
                }
            });
            this.pluginName = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$pluginName$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return this.this$0.properties.getProperty("plugin");
                }
            });
            this.objcClassesIncludingCategories = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$objcClassesIncludingCategories$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends String> invoke() {
                    return DefFileKt.getSpaceSeparated(this.this$0.properties, "objcClassesIncludingCategories");
                }
            });
            this.userSetupHint = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.util.DefFile$DefFileConfig$userSetupHint$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return this.this$0.properties.getProperty("userSetupHint");
                }
            });
        }

        public final List<String> getHeaders() {
            return (List) this.headers.getValue();
        }

        public final List<String> getModules() {
            return (List) this.modules.getValue();
        }

        public final String getLanguage() {
            return (String) this.language.getValue();
        }

        public final List<String> getCompilerOpts() {
            return (List) this.compilerOpts.getValue();
        }

        public final boolean getExcludeSystemLibs() {
            return ((Boolean) this.excludeSystemLibs.getValue()).booleanValue();
        }

        public final boolean getExcludeDependentModules() {
            return ((Boolean) this.excludeDependentModules.getValue()).booleanValue();
        }

        public final List<String> getEntryPoints() {
            return (List) this.entryPoints.getValue();
        }

        public final List<String> getLinkerOpts() {
            return (List) this.linkerOpts.getValue();
        }

        public final String getLinker() {
            return (String) this.linker.getValue();
        }

        public final List<String> getExcludedFunctions() {
            return (List) this.excludedFunctions.getValue();
        }

        public final List<String> getExcludedMacros() {
            return (List) this.excludedMacros.getValue();
        }

        public final List<String> getStaticLibraries() {
            return (List) this.staticLibraries.getValue();
        }

        public final List<String> getLibraryPaths() {
            return (List) this.libraryPaths.getValue();
        }

        public final String getPackageName() {
            return (String) this.packageName.getValue();
        }

        public final List<String> getHeaderFilter() {
            return (List) this.headerFilter.getValue();
        }

        public final List<String> getExcludeFilter() {
            return (List) this.excludeFilter.getValue();
        }

        public final List<String> getStrictEnums() {
            return (List) this.strictEnums.getValue();
        }

        public final List<String> getNonStrictEnums() {
            return (List) this.nonStrictEnums.getValue();
        }

        public final List<String> getNoStringConversion() {
            return (List) this.noStringConversion.getValue();
        }

        public final List<String> getDepends() {
            return (List) this.depends.getValue();
        }

        public final List<String> getExportForwardDeclarations() {
            return (List) this.exportForwardDeclarations.getValue();
        }

        public final List<String> getAllowedOverloadsForCFunctions() {
            return (List) this.allowedOverloadsForCFunctions.getValue();
        }

        public final boolean getDisableDesignatedInitializerChecks() {
            return ((Boolean) this.disableDesignatedInitializerChecks.getValue()).booleanValue();
        }

        public final String getForeignExceptionMode() {
            return (String) this.foreignExceptionMode.getValue();
        }

        public final String getPluginName() {
            return (String) this.pluginName.getValue();
        }

        public final List<String> getObjcClassesIncludingCategories() {
            return (List) this.objcClassesIncludingCategories.getValue();
        }

        public final String getUserSetupHint() {
            return (String) this.userSetupHint.getValue();
        }
    }
}
