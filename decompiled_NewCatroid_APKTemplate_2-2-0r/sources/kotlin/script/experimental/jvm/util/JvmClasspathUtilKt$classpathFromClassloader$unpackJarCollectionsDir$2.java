package kotlin.script.experimental.jvm.util;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: jvmClasspathUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Ljava/io/File;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class JvmClasspathUtilKt$classpathFromClassloader$unpackJarCollectionsDir$2 extends Lambda implements Function0<File> {
    public static final JvmClasspathUtilKt$classpathFromClassloader$unpackJarCollectionsDir$2 INSTANCE = new JvmClasspathUtilKt$classpathFromClassloader$unpackJarCollectionsDir$2();

    JvmClasspathUtilKt$classpathFromClassloader$unpackJarCollectionsDir$2() {
        super(0);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    public final File invoke() throws IOException {
        final File canonicalFile = File.createTempFile("unpackedJarCollections", null).getCanonicalFile();
        canonicalFile.delete();
        canonicalFile.mkdir();
        canonicalFile.setReadable(false, false);
        canonicalFile.setWritable(false, false);
        canonicalFile.setExecutable(false, false);
        canonicalFile.setReadable(true, true);
        canonicalFile.setWritable(true, true);
        canonicalFile.setExecutable(true, true);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt$classpathFromClassloader$unpackJarCollectionsDir$2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                JvmClasspathUtilKt$classpathFromClassloader$unpackJarCollectionsDir$2.invoke$lambda$1$lambda$0(canonicalFile);
            }
        }));
        return canonicalFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$1$lambda$0(File file) {
        Intrinsics.checkNotNull(file);
        FilesKt.deleteRecursively(file);
    }
}
