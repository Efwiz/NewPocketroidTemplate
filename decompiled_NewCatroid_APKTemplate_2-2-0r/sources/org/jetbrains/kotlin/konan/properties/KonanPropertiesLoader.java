package org.jetbrains.kotlin.konan.properties;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.konan.target.Configurables;
import org.jetbrains.kotlin.konan.target.HostManager;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.konan.util.DependencyProcessor;

/* JADX INFO: compiled from: KonanProperties.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\u0012\u0010\u001c\u001a\u00020\b2\b\u0010\u001d\u001a\u0004\u0018\u00010\bH\u0016J\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0002J\b\u0010\u001f\u001a\u00020 H\u0016J0\u0010!\u001a\u0004\u0018\u00010\b2\u0006\u0010\"\u001a\u00020\b2\u000e\u0010#\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\b0\u0016H\u0002J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010'\u001a\u00020\bH\u0016J\u0012\u0010(\u001a\u0004\u0018\u00010\b2\u0006\u0010'\u001a\u00020\bH\u0016J\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010'\u001a\u00020\bH\u0016J\u0012\u0010*\u001a\u0004\u0018\u00010\b2\u0006\u0010'\u001a\u00020\bH\u0016J\u0016\u0010+\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010'\u001a\u00020\bH\u0016J\u0012\u0010,\u001a\u0004\u0018\u00010\b2\u0006\u0010'\u001a\u00020\bH\u0016R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dependenciesRoot", "", "host", "(Lorg/jetbrains/kotlin/konan/target/KonanTarget;Ljava/util/Properties;Ljava/lang/String;Lorg/jetbrains/kotlin/konan/target/KonanTarget;)V", "dependencies", "", "getDependencies", "()Ljava/util/List;", "dependencyProcessor", "Lorg/jetbrains/kotlin/konan/util/DependencyProcessor;", "getDependencyProcessor", "()Lorg/jetbrains/kotlin/konan/util/DependencyProcessor;", "dependencyProcessor$delegate", "Lkotlin/Lazy;", "predefinedLibffiVersions", "", "predefinedLlvmDistributions", "getProperties", "()Ljava/util/Properties;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "absolute", "value", "compilerDependencies", "downloadDependencies", "", "getPredefinedDependencyOrNull", "dependencyName", "dependencyAccessor", "Lkotlin/Function0;", "predefinedDependencies", "hostList", ASTExpr.DEFAULT_MAP_KEY_NAME, "hostString", "hostTargetList", "hostTargetString", "targetList", "targetString", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class KonanPropertiesLoader implements Configurables {
    private final String dependenciesRoot;

    /* JADX INFO: renamed from: dependencyProcessor$delegate, reason: from kotlin metadata */
    private final Lazy dependencyProcessor;
    private final KonanTarget host;
    private final Set<String> predefinedLibffiVersions;
    private final Set<String> predefinedLlvmDistributions;
    private final Properties properties;
    private final KonanTarget target;

    public KonanPropertiesLoader(KonanTarget target, Properties properties, String str, KonanTarget host) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(host, "host");
        this.target = target;
        this.properties = properties;
        this.dependenciesRoot = str;
        this.host = host;
        this.predefinedLlvmDistributions = CollectionsKt.toSet(PropertiesKt.propertyList$default(properties, "predefinedLlvmDistributions", null, false, 6, null));
        this.predefinedLibffiVersions = CollectionsKt.toSet(PropertiesKt.propertyList$default(properties, "predefinedLibffiVersions", null, false, 6, null));
        this.dependencyProcessor = LazyKt.lazy(new Function0<DependencyProcessor>() { // from class: org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader$dependencyProcessor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DependencyProcessor invoke() {
                if (this.this$0.dependenciesRoot == null) {
                    return null;
                }
                KonanPropertiesLoader konanPropertiesLoader = this.this$0;
                return new DependencyProcessor(new File(konanPropertiesLoader.dependenciesRoot), konanPropertiesLoader, null, false, KonanPropertiesKt.defaultArchiveTypeByHost(konanPropertiesLoader.host), new Function3<String, Long, Long, Unit>() { // from class: org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader$dependencyProcessor$2$1$1
                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(String str2, Long l, Long l2) {
                        invoke(str2, l.longValue(), l2.longValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(String url, long j, long j2) {
                        Intrinsics.checkNotNullParameter(url, "url");
                        System.out.print((Object) ("\n(KonanProperties) Downloading dependency: " + url + " (" + j + '/' + j2 + "). "));
                    }
                }, 12, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.konan.target.Configurables
    public KonanTarget getTarget() {
        return this.target;
    }

    public final Properties getProperties() {
        return this.properties;
    }

    public /* synthetic */ KonanPropertiesLoader(KonanTarget konanTarget, Properties properties, String str, KonanTarget konanTarget2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(konanTarget, properties, str, (i & 8) != 0 ? HostManager.INSTANCE.getHost() : konanTarget2);
    }

    private final String getPredefinedDependencyOrNull(String dependencyName, Function0<String> dependencyAccessor, Set<String> predefinedDependencies) {
        String strInvoke = dependencyAccessor.invoke();
        if (strInvoke == null) {
            throw new IllegalStateException(("Undefined " + dependencyName + '!').toString());
        }
        if (predefinedDependencies.contains(strInvoke)) {
            return strInvoke;
        }
        return null;
    }

    private final List<String> compilerDependencies() {
        return CollectionsKt.listOfNotNull((Object[]) new String[]{getPredefinedDependencyOrNull("LLVM home", new PropertyReference0(this) { // from class: org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader.compilerDependencies.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getLlvmHome()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((KonanPropertiesLoader) this.receiver).getLlvmHome();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "llvmHome";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(KonanPropertiesLoader.class);
            }
        }, this.predefinedLlvmDistributions), getPredefinedDependencyOrNull("libffi version", new PropertyReference0(this) { // from class: org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader.compilerDependencies.2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getLibffiDir()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((KonanPropertiesLoader) this.receiver).getLibffiDir();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "libffiDir";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(KonanPropertiesLoader.class);
            }
        }, this.predefinedLibffiVersions)});
    }

    public List<String> getDependencies() {
        return CollectionsKt.plus((Collection) hostTargetList("dependencies"), (Iterable) compilerDependencies());
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public void downloadDependencies() {
        DependencyProcessor dependencyProcessor = getDependencyProcessor();
        Intrinsics.checkNotNull(dependencyProcessor);
        dependencyProcessor.run();
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String targetString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return TargetPropertiesKt.targetString(this.properties, key, getTarget());
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> targetList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return TargetPropertiesKt.targetList(this.properties, key, getTarget());
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String hostString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return TargetPropertiesKt.hostString(this.properties, key, this.host);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> hostList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return TargetPropertiesKt.hostList(this.properties, key, this.host);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String hostTargetString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return TargetPropertiesKt.hostTargetString(this.properties, key, getTarget(), this.host);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public List<String> hostTargetList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return TargetPropertiesKt.hostTargetList(this.properties, key, getTarget(), this.host);
    }

    @Override // org.jetbrains.kotlin.konan.properties.TargetableExternalStorage
    public String absolute(String value) {
        DependencyProcessor dependencyProcessor = getDependencyProcessor();
        Intrinsics.checkNotNull(dependencyProcessor);
        Intrinsics.checkNotNull(value);
        String absolutePath = dependencyProcessor.resolve(value).getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "dependencyProcessor!!.re…lve(value!!).absolutePath");
        return absolutePath;
    }

    private final DependencyProcessor getDependencyProcessor() {
        return (DependencyProcessor) this.dependencyProcessor.getValue();
    }
}
