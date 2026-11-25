package org.jetbrains.kotlin.utils;

import java.io.File;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinPathsFromHomeDir.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000f\u001a\u00020\tH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/utils/KotlinPathsFromHomeDir;", "Lorg/jetbrains/kotlin/utils/KotlinPathsFromBaseDirectory;", "homePath", "Ljava/io/File;", "(Ljava/io/File;)V", "getHomePath", "()Ljava/io/File;", "libsWithSources", "", "Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;", "getLibsWithSources", "()Ljava/util/Set;", "libsWithSources$delegate", "Lkotlin/Lazy;", "sourcesJar", "jar", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinPathsFromHomeDir extends KotlinPathsFromBaseDirectory {
    private final File homePath;

    /* JADX INFO: renamed from: libsWithSources$delegate, reason: from kotlin metadata */
    private final Lazy libsWithSources;

    @Override // org.jetbrains.kotlin.utils.KotlinPathsFromBaseDirectory, org.jetbrains.kotlin.utils.KotlinPaths
    public File getHomePath() {
        return this.homePath;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinPathsFromHomeDir(File homePath) {
        super(new File(homePath, "lib"));
        Intrinsics.checkNotNullParameter(homePath, "homePath");
        this.homePath = homePath;
        this.libsWithSources = LazyKt.lazy(new Function0<Set<? extends KotlinPaths.Jar>>() { // from class: org.jetbrains.kotlin.utils.KotlinPathsFromHomeDir$libsWithSources$2
            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends KotlinPaths.Jar> invoke() {
                return SetsKt.setOf((Object[]) new KotlinPaths.Jar[]{KotlinPaths.Jar.StdLib, KotlinPaths.Jar.JsStdLib});
            }
        });
    }

    public final Set<KotlinPaths.Jar> getLibsWithSources() {
        return (Set) this.libsWithSources.getValue();
    }

    @Override // org.jetbrains.kotlin.utils.KotlinPathsFromBaseDirectory, org.jetbrains.kotlin.utils.KotlinPaths
    public File sourcesJar(KotlinPaths.Jar jar) {
        Intrinsics.checkNotNullParameter(jar, "jar");
        if (getLibsWithSources().contains(jar)) {
            return super.sourcesJar(jar);
        }
        return null;
    }
}
