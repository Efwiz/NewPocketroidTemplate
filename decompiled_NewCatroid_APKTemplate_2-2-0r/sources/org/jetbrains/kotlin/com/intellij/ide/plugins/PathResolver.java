package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.reandroid.apk.ApkUtil;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader;

/* JADX INFO: compiled from: PathBasedJdomXIncluder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J2\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\rH&J*\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\r2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J,\u0010\u0011\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PathResolver;", "", "isFlat", "", "()Z", "loadXIncludeReference", "readInto", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor;", "readContext", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ReadModuleContext;", "dataLoader", "Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", ApkUtil.DEF_MODULE_NAME, "", "relativePath", "resolveModuleFile", "path", "resolvePath", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public interface PathResolver {

    /* JADX INFO: compiled from: PathBasedJdomXIncluder.kt */
    @Metadata(k = 3, mv = {1, 5, 1})
    public static final class DefaultImpls {
        public static boolean isFlat(PathResolver pathResolver) {
            return false;
        }
    }

    boolean isFlat();

    boolean loadXIncludeReference(RawPluginDescriptor readInto, ReadModuleContext readContext, DataLoader dataLoader, String base, String relativePath);

    RawPluginDescriptor resolveModuleFile(ReadModuleContext readContext, DataLoader dataLoader, String path, RawPluginDescriptor readInto);

    RawPluginDescriptor resolvePath(ReadModuleContext readContext, DataLoader dataLoader, String relativePath, RawPluginDescriptor readInto);
}
