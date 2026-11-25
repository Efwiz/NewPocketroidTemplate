package org.jetbrains.kotlin.konan.target;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty0;
import kotlin.text.StringsKt;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;
import org.jetbrains.kotlin.konan.util.DependencyDirectories;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: Distribution.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 R2\u00020\u0001:\u0002RSBI\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nB\u000f\b\u0002\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0014\u0010B\u001a\b\u0012\u0004\u0012\u00020C0$2\u0006\u0010D\u001a\u00020\u0003J\u000e\u0010E\u001a\u00020\u00032\u0006\u0010F\u001a\u00020GJ\u000e\u0010H\u001a\u00020\u00032\u0006\u0010F\u001a\u00020GJ\u000e\u0010I\u001a\u00020\u00032\u0006\u0010F\u001a\u00020GJ\u000e\u0010J\u001a\u00020\u00032\u0006\u0010F\u001a\u00020GJ\u0016\u0010K\u001a\b\u0012\u0004\u0012\u00020C0$2\u0006\u0010D\u001a\u00020\u0003H\u0002J\u001e\u0010L\u001a\b\u0012\u0004\u0012\u00020C0$2\u0006\u0010M\u001a\u00020\u00032\u0006\u0010D\u001a\u00020\u0003H\u0002J\u000e\u0010N\u001a\u00020\u00032\u0006\u0010F\u001a\u00020GJ\u0016\u0010O\u001a\b\u0012\u0004\u0012\u00020C0$2\u0006\u0010D\u001a\u00020\u0003H\u0002J\b\u0010P\u001a\u00020QH\u0002R\u001d\u0010\u000e\u001a\u0004\u0018\u00010\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0013\u001a\n \u0014*\u0004\u0018\u00010\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u001b\u0010\u0016\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0012\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u001a\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u001d\u0010\t\u001a\u0004\u0018\u00010\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001c\u0010\u0010R\u001b\u0010\u0002\u001a\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u001e\u001a\u0004\b\u001f\u0010\u0010R\u0011\u0010!\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0010R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00030$¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020(¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010+\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0010R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\u001e\u001a\u0004\b-\u0010\u0018R\u001f\u0010/\u001a\u000600j\u0002`18FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\u0012\u001a\u0004\b2\u00103R)\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b7\u0010\u001e\u001a\u0004\b5\u00106R\u001d\u0010\u0006\u001a\u0004\u0018\u00010\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b9\u0010\u001e\u001a\u0004\b8\u0010\u0010R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010:\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\u0010R\u0011\u0010<\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b=\u0010\u0010R\u0011\u0010>\u001a\u00020?¢\u0006\b\n\u0000\u001a\u0004\b@\u0010A¨\u0006T"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Distribution;", "Ljava/io/Serializable;", "konanHome", "", "onlyDefaultProfiles", "", "runtimeFileOverride", "propertyOverrides", "", "konanDataDir", "(Ljava/lang/String;ZLjava/lang/String;Ljava/util/Map;Ljava/lang/String;)V", "serialized", "Lorg/jetbrains/kotlin/konan/target/Distribution$Serialized;", "(Lorg/jetbrains/kotlin/konan/target/Distribution$Serialized;)V", "compilerVersion", "getCompilerVersion", "()Ljava/lang/String;", "compilerVersion$delegate", "Lkotlin/Lazy;", "dependenciesDir", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getDependenciesDir", "experimentalEnabled", "getExperimentalEnabled", "()Z", "experimentalEnabled$delegate", "klib", "getKlib", "getKonanDataDir", "konanDataDir$delegate", "Lkotlin/reflect/KProperty0;", "getKonanHome", "konanHome$delegate", "konanSubdir", "getKonanSubdir", "launcherFiles", "", "getLauncherFiles", "()Ljava/util/List;", "localKonanDir", "Ljava/io/File;", "getLocalKonanDir", "()Ljava/io/File;", "mainPropertyFileName", "getMainPropertyFileName", "getOnlyDefaultProfiles", "onlyDefaultProfiles$delegate", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "getProperties", "()Ljava/util/Properties;", "properties$delegate", "getPropertyOverrides", "()Ljava/util/Map;", "propertyOverrides$delegate", "getRuntimeFileOverride", "runtimeFileOverride$delegate", "stdlib", "getStdlib", "stdlibDefaultComponent", "getStdlibDefaultComponent", "subTargetProvider", "Lorg/jetbrains/kotlin/konan/target/SubTargetProvider;", "getSubTargetProvider", "()Lorg/jetbrains/kotlin/konan/target/SubTargetProvider;", "additionalPropertyFiles", "Lorg/jetbrains/kotlin/konan/file/File;", "genericName", "compilerInterface", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "defaultNatives", "platformDefs", "platformLibs", "preconfiguredPropertyFiles", "propertyFilesFromConfigDir", "configDir", "runtime", "userPropertyFiles", "writeReplace", "", "Companion", "Serialized", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class Distribution implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: compilerVersion$delegate, reason: from kotlin metadata */
    private final Lazy compilerVersion;
    private final String dependenciesDir;

    /* JADX INFO: renamed from: experimentalEnabled$delegate, reason: from kotlin metadata */
    private final Lazy experimentalEnabled;
    private final String klib;

    /* JADX INFO: renamed from: konanDataDir$delegate, reason: from kotlin metadata */
    private final KProperty0 konanDataDir;

    /* JADX INFO: renamed from: konanHome$delegate, reason: from kotlin metadata */
    private final KProperty0 konanHome;
    private final String konanSubdir;
    private final List<String> launcherFiles;
    private final File localKonanDir;
    private final String mainPropertyFileName;

    /* JADX INFO: renamed from: onlyDefaultProfiles$delegate, reason: from kotlin metadata */
    private final KProperty0 onlyDefaultProfiles;

    /* JADX INFO: renamed from: properties$delegate, reason: from kotlin metadata */
    private final Lazy properties;

    /* JADX INFO: renamed from: propertyOverrides$delegate, reason: from kotlin metadata */
    private final KProperty0 propertyOverrides;

    /* JADX INFO: renamed from: runtimeFileOverride$delegate, reason: from kotlin metadata */
    private final KProperty0 runtimeFileOverride;
    private final Serialized serialized;
    private final String stdlib;
    private final String stdlibDefaultComponent;
    private final SubTargetProvider subTargetProvider;

    public /* synthetic */ Distribution(Serialized serialized, DefaultConstructorMarker defaultConstructorMarker) {
        this(serialized);
    }

    private Distribution(final Serialized serialized) {
        this.serialized = serialized;
        this.konanHome = new PropertyReference0(serialized) { // from class: org.jetbrains.kotlin.konan.target.Distribution$konanHome$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getKonanHome()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((Distribution.Serialized) this.receiver).getKonanHome();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "konanHome";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(Distribution.Serialized.class);
            }
        };
        this.onlyDefaultProfiles = new PropertyReference0(serialized) { // from class: org.jetbrains.kotlin.konan.target.Distribution$onlyDefaultProfiles$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getOnlyDefaultProfiles()Z";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return Boolean.valueOf(((Distribution.Serialized) this.receiver).getOnlyDefaultProfiles());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "onlyDefaultProfiles";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(Distribution.Serialized.class);
            }
        };
        this.runtimeFileOverride = new PropertyReference0(serialized) { // from class: org.jetbrains.kotlin.konan.target.Distribution$runtimeFileOverride$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getRuntimeFileOverride()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((Distribution.Serialized) this.receiver).getRuntimeFileOverride();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "runtimeFileOverride";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(Distribution.Serialized.class);
            }
        };
        this.propertyOverrides = new PropertyReference0(serialized) { // from class: org.jetbrains.kotlin.konan.target.Distribution$propertyOverrides$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getPropertyOverrides()Ljava/util/Map;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((Distribution.Serialized) this.receiver).getPropertyOverrides();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "propertyOverrides";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(Distribution.Serialized.class);
            }
        };
        this.konanDataDir = new PropertyReference0(serialized) { // from class: org.jetbrains.kotlin.konan.target.Distribution$konanDataDir$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getKonanDataDir()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((Distribution.Serialized) this.receiver).getKonanDataDir();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "konanDataDir";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(Distribution.Serialized.class);
            }
        };
        this.localKonanDir = DependencyDirectories.INSTANCE.getLocalKonanDir(getKonanDataDir());
        String str = getKonanHome() + "/konan";
        this.konanSubdir = str;
        this.mainPropertyFileName = str + "/konan.properties";
        this.experimentalEnabled = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.konan.target.Distribution$experimentalEnabled$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(new org.jetbrains.kotlin.konan.file.File(this.this$0.getKonanSubdir() + "/experimentalTargetsEnabled").getExists());
            }
        });
        this.properties = LazyKt.lazy(new Function0<Properties>() { // from class: org.jetbrains.kotlin.konan.target.Distribution$properties$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Properties invoke() {
                Properties properties = new Properties();
                invoke$loadPropertiesSafely(properties, new org.jetbrains.kotlin.konan.file.File(this.this$0.getMainPropertyFileName()));
                List<String> knownTargetTemplates = HostManager.Companion.getKnownTargetTemplates();
                Distribution distribution = this.this$0;
                Iterator<T> it = knownTargetTemplates.iterator();
                while (it.hasNext()) {
                    Iterator<T> it2 = distribution.additionalPropertyFiles((String) it.next()).iterator();
                    while (it2.hasNext()) {
                        invoke$loadPropertiesSafely(properties, (org.jetbrains.kotlin.konan.file.File) it2.next());
                    }
                }
                if (this.this$0.getOnlyDefaultProfiles()) {
                    PropertiesKt.keepOnlyDefaultProfiles(properties);
                }
                Map propertyOverrides = this.this$0.getPropertyOverrides();
                if (propertyOverrides != null) {
                    properties.putAll(propertyOverrides);
                }
                return properties;
            }

            private static final void invoke$loadPropertiesSafely(Properties properties, org.jetbrains.kotlin.konan.file.File file) {
                if (file.isFile()) {
                    properties.putAll(PropertiesKt.loadProperties(file));
                }
            }
        });
        this.compilerVersion = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.Distribution$compilerVersion$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                Distribution.Companion companion = Distribution.INSTANCE;
                Object obj = this.this$0.getProperties().get("compilerVersion");
                return companion.getCompilerVersion(obj != null ? obj.toString() : null, this.this$0.getKonanHome());
            }
        });
        String str2 = getKonanHome() + "/klib";
        this.klib = str2;
        String str3 = str2 + "/common/stdlib";
        this.stdlib = str3;
        this.stdlibDefaultComponent = str3 + "/default";
        this.launcherFiles = CollectionsKt.listOf("launcher.bc");
        this.dependenciesDir = DependencyDirectories.INSTANCE.getDependenciesRoot(getKonanDataDir()).getAbsolutePath();
        this.subTargetProvider = new SubTargetProvider() { // from class: org.jetbrains.kotlin.konan.target.Distribution$subTargetProvider$1
            @Override // org.jetbrains.kotlin.konan.target.SubTargetProvider
            public List<String> availableSubTarget(String genericName) {
                Intrinsics.checkNotNullParameter(genericName, "genericName");
                List<org.jetbrains.kotlin.konan.file.File> listAdditionalPropertyFiles = this.this$0.additionalPropertyFiles(genericName);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAdditionalPropertyFiles, 10));
                Iterator<T> it = listAdditionalPropertyFiles.iterator();
                while (it.hasNext()) {
                    arrayList.add(((org.jetbrains.kotlin.konan.file.File) it.next()).getName());
                }
                return arrayList;
            }
        };
    }

    public /* synthetic */ Distribution(String str, boolean z, String str2, Map map, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : map, (i & 16) != 0 ? null : str3);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Distribution(String konanHome, boolean z, String str, Map<String, String> map, String str2) {
        this(new Serialized(konanHome, z, str, map, str2));
        Intrinsics.checkNotNullParameter(konanHome, "konanHome");
    }

    public final String getKonanHome() {
        return (String) this.konanHome.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getOnlyDefaultProfiles() {
        return ((Boolean) this.onlyDefaultProfiles.get()).booleanValue();
    }

    private final String getRuntimeFileOverride() {
        return (String) this.runtimeFileOverride.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<String, String> getPropertyOverrides() {
        return (Map) this.propertyOverrides.get();
    }

    private final String getKonanDataDir() {
        return (String) this.konanDataDir.get();
    }

    public final File getLocalKonanDir() {
        return this.localKonanDir;
    }

    public final String getKonanSubdir() {
        return this.konanSubdir;
    }

    public final String getMainPropertyFileName() {
        return this.mainPropertyFileName;
    }

    public final boolean getExperimentalEnabled() {
        return ((Boolean) this.experimentalEnabled.getValue()).booleanValue();
    }

    private final List<org.jetbrains.kotlin.konan.file.File> propertyFilesFromConfigDir(String configDir, String genericName) {
        org.jetbrains.kotlin.konan.file.File file = new org.jetbrains.kotlin.konan.file.File(configDir, "platforms/" + genericName);
        if (file.isDirectory()) {
            return file.getListFiles();
        }
        return CollectionsKt.emptyList();
    }

    private final List<org.jetbrains.kotlin.konan.file.File> preconfiguredPropertyFiles(String genericName) {
        return propertyFilesFromConfigDir(this.konanSubdir, genericName);
    }

    private final List<org.jetbrains.kotlin.konan.file.File> userPropertyFiles(String genericName) {
        String absolutePath = this.localKonanDir.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "localKonanDir.absolutePath");
        return propertyFilesFromConfigDir(absolutePath, genericName);
    }

    public final List<org.jetbrains.kotlin.konan.file.File> additionalPropertyFiles(String genericName) {
        Intrinsics.checkNotNullParameter(genericName, "genericName");
        return CollectionsKt.plus((Collection) preconfiguredPropertyFiles(genericName), (Iterable) userPropertyFiles(genericName));
    }

    public final Properties getProperties() {
        return (Properties) this.properties.getValue();
    }

    public final String getCompilerVersion() {
        return (String) this.compilerVersion.getValue();
    }

    public final String getKlib() {
        return this.klib;
    }

    public final String getStdlib() {
        return this.stdlib;
    }

    public final String getStdlibDefaultComponent() {
        return this.stdlibDefaultComponent;
    }

    public final String defaultNatives(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return getKonanHome() + "/konan/targets/" + target.getVisibleName() + "/native";
    }

    public final String runtime(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        String runtimeFileOverride = getRuntimeFileOverride();
        return runtimeFileOverride == null ? this.stdlibDefaultComponent + "/targets/" + target.getVisibleName() + "/native/runtime.bc" : runtimeFileOverride;
    }

    public final String compilerInterface(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        String runtimeFileOverride = getRuntimeFileOverride();
        return runtimeFileOverride == null ? this.stdlibDefaultComponent + "/targets/" + target.getVisibleName() + "/native/compiler_interface.bc" : runtimeFileOverride;
    }

    public final String platformDefs(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return getKonanHome() + "/konan/platformDef/" + target.getVisibleName();
    }

    public final String platformLibs(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return this.klib + "/platform/" + target.getVisibleName();
    }

    public final List<String> getLauncherFiles() {
        return this.launcherFiles;
    }

    public final String getDependenciesDir() {
        return this.dependenciesDir;
    }

    public final SubTargetProvider getSubTargetProvider() {
        return this.subTargetProvider;
    }

    /* JADX INFO: compiled from: Distribution.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Distribution$Companion;", "", "()V", "getBundleVersion", "", "konanHome", "getCompilerVersion", "propertyVersion", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final String getBundleVersion(String konanHome) {
            String str = konanHome;
            if (!StringsKt.contains$default((CharSequence) str, (CharSequence) ServerAuthenticationConstants.TOKEN_CODE_INVALID, false, 2, (Object) null)) {
                return null;
            }
            String strSubstring = konanHome.substring(StringsKt.lastIndexOf$default((CharSequence) str, ServerAuthenticationConstants.TOKEN_CODE_INVALID, 0, false, 6, (Object) null) + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return strSubstring;
        }

        public final String getCompilerVersion(String propertyVersion, String konanHome) {
            Intrinsics.checkNotNullParameter(konanHome, "konanHome");
            return propertyVersion == null ? getBundleVersion(konanHome) : propertyVersion;
        }
    }

    private final Object writeReplace() {
        return this.serialized;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: Distribution.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0012\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0082\b\u0018\u0000  2\u00020\u0001:\u0001 B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0017\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bHÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003JM\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bHÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\b\u0010\u001e\u001a\u00020\u001bH\u0002J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001f\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Distribution$Serialized;", "Ljava/io/Serializable;", "konanHome", "", "onlyDefaultProfiles", "", "runtimeFileOverride", "propertyOverrides", "", "konanDataDir", "(Ljava/lang/String;ZLjava/lang/String;Ljava/util/Map;Ljava/lang/String;)V", "getKonanDataDir", "()Ljava/lang/String;", "getKonanHome", "getOnlyDefaultProfiles", "()Z", "getPropertyOverrides", "()Ljava/util/Map;", "getRuntimeFileOverride", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "readResolve", "toString", "Companion", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    static final /* data */ class Serialized implements Serializable {
        private static final long serialVersionUID = 0;
        private final String konanDataDir;
        private final String konanHome;
        private final boolean onlyDefaultProfiles;
        private final Map<String, String> propertyOverrides;
        private final String runtimeFileOverride;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Serialized copy$default(Serialized serialized, String str, boolean z, String str2, Map map, String str3, int i, Object obj) {
            if ((i & 1) != 0) {
                str = serialized.konanHome;
            }
            if ((i & 2) != 0) {
                z = serialized.onlyDefaultProfiles;
            }
            boolean z2 = z;
            if ((i & 4) != 0) {
                str2 = serialized.runtimeFileOverride;
            }
            String str4 = str2;
            if ((i & 8) != 0) {
                map = serialized.propertyOverrides;
            }
            Map map2 = map;
            if ((i & 16) != 0) {
                str3 = serialized.konanDataDir;
            }
            return serialized.copy(str, z2, str4, map2, str3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getKonanHome() {
            return this.konanHome;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getOnlyDefaultProfiles() {
            return this.onlyDefaultProfiles;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getRuntimeFileOverride() {
            return this.runtimeFileOverride;
        }

        public final Map<String, String> component4() {
            return this.propertyOverrides;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final String getKonanDataDir() {
            return this.konanDataDir;
        }

        public final Serialized copy(String konanHome, boolean onlyDefaultProfiles, String runtimeFileOverride, Map<String, String> propertyOverrides, String konanDataDir) {
            Intrinsics.checkNotNullParameter(konanHome, "konanHome");
            return new Serialized(konanHome, onlyDefaultProfiles, runtimeFileOverride, propertyOverrides, konanDataDir);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Serialized)) {
                return false;
            }
            Serialized serialized = (Serialized) other;
            return Intrinsics.areEqual(this.konanHome, serialized.konanHome) && this.onlyDefaultProfiles == serialized.onlyDefaultProfiles && Intrinsics.areEqual(this.runtimeFileOverride, serialized.runtimeFileOverride) && Intrinsics.areEqual(this.propertyOverrides, serialized.propertyOverrides) && Intrinsics.areEqual(this.konanDataDir, serialized.konanDataDir);
        }

        public int hashCode() {
            int iHashCode = ((this.konanHome.hashCode() * 31) + Boolean.hashCode(this.onlyDefaultProfiles)) * 31;
            String str = this.runtimeFileOverride;
            int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
            Map<String, String> map = this.propertyOverrides;
            int iHashCode3 = (iHashCode2 + (map == null ? 0 : map.hashCode())) * 31;
            String str2 = this.konanDataDir;
            return iHashCode3 + (str2 != null ? str2.hashCode() : 0);
        }

        public String toString() {
            return "Serialized(konanHome=" + this.konanHome + ", onlyDefaultProfiles=" + this.onlyDefaultProfiles + ", runtimeFileOverride=" + this.runtimeFileOverride + ", propertyOverrides=" + this.propertyOverrides + ", konanDataDir=" + this.konanDataDir + ')';
        }

        public Serialized(String konanHome, boolean z, String str, Map<String, String> map, String str2) {
            Intrinsics.checkNotNullParameter(konanHome, "konanHome");
            this.konanHome = konanHome;
            this.onlyDefaultProfiles = z;
            this.runtimeFileOverride = str;
            this.propertyOverrides = map;
            this.konanDataDir = str2;
        }

        public final String getKonanHome() {
            return this.konanHome;
        }

        public final boolean getOnlyDefaultProfiles() {
            return this.onlyDefaultProfiles;
        }

        public final String getRuntimeFileOverride() {
            return this.runtimeFileOverride;
        }

        public final Map<String, String> getPropertyOverrides() {
            return this.propertyOverrides;
        }

        public final String getKonanDataDir() {
            return this.konanDataDir;
        }

        private final Object readResolve() {
            return new Distribution(this, null);
        }
    }
}
