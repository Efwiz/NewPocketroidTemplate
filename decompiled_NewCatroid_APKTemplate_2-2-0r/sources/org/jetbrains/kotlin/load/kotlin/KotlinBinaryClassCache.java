package org.jetbrains.kotlin.load.kotlin;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;

/* JADX INFO: compiled from: KotlinBinaryClassCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000)\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0004\u0018\u0000 \f2\u00020\u0001:\u0002\f\rB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0010\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinBinaryClassCache;", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "()V", ResponseCacheMiddleware.CACHE, "org/jetbrains/kotlin/load/kotlin/KotlinBinaryClassCache$cache$1", "Lorg/jetbrains/kotlin/load/kotlin/KotlinBinaryClassCache$cache$1;", "requestCaches", "Ljava/util/concurrent/CopyOnWriteArrayList;", "Ljava/lang/ref/WeakReference;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinBinaryClassCache$RequestCache;", "dispose", "", "Companion", "RequestCache", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinBinaryClassCache implements Disposable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CopyOnWriteArrayList<WeakReference<RequestCache>> requestCaches = new CopyOnWriteArrayList<>();
    private final KotlinBinaryClassCache$cache$1 cache = new ThreadLocal<RequestCache>() { // from class: org.jetbrains.kotlin.load.kotlin.KotlinBinaryClassCache$cache$1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public KotlinBinaryClassCache.RequestCache initialValue() {
            KotlinBinaryClassCache.RequestCache requestCache = new KotlinBinaryClassCache.RequestCache();
            this.this$0.requestCaches.add(new WeakReference(requestCache));
            return requestCache;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: KotlinBinaryClassCache.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0015\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\u00102\b\u0010\t\u001a\u0004\u0018\u00010\nR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinBinaryClassCache$RequestCache;", "", "()V", "modificationStamp", "", "getModificationStamp", "()J", "setModificationStamp", "(J)V", "result", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "getResult", "()Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "setResult", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;)V", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "setVirtualFile", "(Lcom/intellij/openapi/vfs/VirtualFile;)V", ResponseCacheMiddleware.CACHE, "file", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class RequestCache {
        private long modificationStamp;
        private KotlinClassFinder.Result result;
        private VirtualFile virtualFile;

        public final VirtualFile getVirtualFile() {
            return this.virtualFile;
        }

        public final void setVirtualFile(VirtualFile virtualFile) {
            this.virtualFile = virtualFile;
        }

        public final long getModificationStamp() {
            return this.modificationStamp;
        }

        public final void setModificationStamp(long j) {
            this.modificationStamp = j;
        }

        public final KotlinClassFinder.Result getResult() {
            return this.result;
        }

        public final void setResult(KotlinClassFinder.Result result) {
            this.result = result;
        }

        public final KotlinClassFinder.Result cache(VirtualFile file, KotlinClassFinder.Result result) {
            Intrinsics.checkNotNullParameter(file, "file");
            this.virtualFile = file;
            this.result = result;
            this.modificationStamp = file.getModificationStamp();
            return result;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        Iterator<WeakReference<RequestCache>> it = this.requestCaches.iterator();
        while (it.hasNext()) {
            RequestCache requestCache = it.next().get();
            if (requestCache != null) {
                requestCache.setResult(null);
                requestCache.setVirtualFile(null);
            }
        }
        this.requestCaches.clear();
        remove();
    }

    /* JADX INFO: compiled from: KotlinBinaryClassCache.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007J$\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinBinaryClassCache$Companion;", "", "()V", "getKotlinBinaryClassOrClassFileContent", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "fileContent", "", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @Deprecated(message = "Please pass jvmMetadataVersion explicitly", replaceWith = @ReplaceWith(expression = "getKotlinBinaryClassOrClassFileContent(file, JvmMetadataVersion.INSTANCE, fileContent = fileContent)", imports = {"org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion"}))
        public final KotlinClassFinder.Result getKotlinBinaryClassOrClassFileContent(VirtualFile file, byte[] fileContent) {
            Intrinsics.checkNotNullParameter(file, "file");
            return getKotlinBinaryClassOrClassFileContent(file, JvmMetadataVersion.INSTANCE, fileContent);
        }

        public static /* synthetic */ KotlinClassFinder.Result getKotlinBinaryClassOrClassFileContent$default(Companion companion, VirtualFile virtualFile, JvmMetadataVersion jvmMetadataVersion, byte[] bArr, int i, Object obj) {
            if ((i & 4) != 0) {
                bArr = null;
            }
            return companion.getKotlinBinaryClassOrClassFileContent(virtualFile, jvmMetadataVersion, bArr);
        }

        public final KotlinClassFinder.Result getKotlinBinaryClassOrClassFileContent(final VirtualFile file, final JvmMetadataVersion jvmMetadataVersion, final byte[] fileContent) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
            if ((!Intrinsics.areEqual(file.getExtension(), JavaClassFileType.INSTANCE.getDefaultExtension()) && file.getFileType() != JavaClassFileType.INSTANCE) || Intrinsics.areEqual(file.getName(), "module-info.class")) {
                return null;
            }
            KotlinBinaryClassCache kotlinBinaryClassCache = (KotlinBinaryClassCache) ApplicationManager.getApplication().getService(KotlinBinaryClassCache.class);
            Intrinsics.checkNotNull(kotlinBinaryClassCache);
            RequestCache requestCache = kotlinBinaryClassCache.cache.get();
            if (file.getModificationStamp() == requestCache.getModificationStamp() && Intrinsics.areEqual(file, requestCache.getVirtualFile())) {
                return requestCache.getResult();
            }
            return requestCache.cache(file, (KotlinClassFinder.Result) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.load.kotlin.KotlinBinaryClassCache$Companion$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return KotlinBinaryClassCache.Companion.getKotlinBinaryClassOrClassFileContent$lambda$0(file, jvmMetadataVersion, fileContent);
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final KotlinClassFinder.Result getKotlinBinaryClassOrClassFileContent$lambda$0(VirtualFile file, JvmMetadataVersion jvmMetadataVersion, byte[] bArr) {
            Intrinsics.checkNotNullParameter(file, "$file");
            Intrinsics.checkNotNullParameter(jvmMetadataVersion, "$jvmMetadataVersion");
            return VirtualFileKotlinClass.INSTANCE.create$frontend_common_jvm(file, jvmMetadataVersion, bArr);
        }
    }
}
