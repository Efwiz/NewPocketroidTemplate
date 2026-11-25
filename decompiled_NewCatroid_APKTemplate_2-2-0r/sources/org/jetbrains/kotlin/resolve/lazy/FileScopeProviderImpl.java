package org.jetbrains.kotlin.resolve.lazy;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: FileScopeProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProviderImpl;", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider;", "fileScopeFactory", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/storage/StorageManager;)V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopes;", "getFileScopes", "file", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileScopeProviderImpl implements FileScopeProvider {
    private final BindingTrace bindingTrace;
    private final MemoizedFunctionToNotNull<KtFile, FileScopes> cache;
    private final FileScopeFactory fileScopeFactory;
    private final StorageManager storageManager;

    public FileScopeProviderImpl(FileScopeFactory fileScopeFactory, BindingTrace bindingTrace, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(fileScopeFactory, "fileScopeFactory");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.fileScopeFactory = fileScopeFactory;
        this.bindingTrace = bindingTrace;
        this.storageManager = storageManager;
        this.cache = storageManager.createMemoizedFunction(new Function1<KtFile, FileScopes>() { // from class: org.jetbrains.kotlin.resolve.lazy.FileScopeProviderImpl$cache$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FileScopes invoke(KtFile file) {
                FileScopes fileScopesCreateScopesForFile$default;
                FileScopesCustomizer fileScopesCustomizer;
                Intrinsics.checkNotNullParameter(file, "file");
                KtFile ktFile = (KtFile) file.getOriginalFile();
                if (ktFile == null || (fileScopesCustomizer = FileScopeProviderKt.getFileScopesCustomizer(ktFile)) == null || (fileScopesCreateScopesForFile$default = fileScopesCustomizer.createFileScopes(this.this$0.fileScopeFactory)) == null) {
                    fileScopesCreateScopesForFile$default = FileScopeFactory.createScopesForFile$default(this.this$0.fileScopeFactory, file, null, false, 6, null);
                }
                BindingContextUtilsKt.recordScope(this.this$0.bindingTrace, fileScopesCreateScopesForFile$default.getLexicalScope(), file);
                return fileScopesCreateScopesForFile$default;
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.FileScopeProvider
    /* JADX INFO: renamed from: getFileScopes */
    public FileScopes mo7444getFileScopes(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return this.cache.invoke(file);
    }
}
