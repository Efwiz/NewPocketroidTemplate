package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: compiled from: FileScopeProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = FileScopeProviderImpl.class)
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\nJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider;", "", "getFileResolutionScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "getFileScopes", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopes;", "getImportResolver", "Lorg/jetbrains/kotlin/resolve/lazy/ImportForceResolver;", "ThrowException", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FileScopeProvider {
    /* JADX INFO: renamed from: getFileScopes */
    FileScopes mo7444getFileScopes(KtFile file);

    default LexicalScope getFileResolutionScope(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return mo7444getFileScopes(file).getLexicalScope();
    }

    default ImportForceResolver getImportResolver(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return mo7444getFileScopes(file).getImportForceResolver();
    }

    /* JADX INFO: compiled from: FileScopeProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider$ThrowException;", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider;", "()V", "getFileScopes", "", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ThrowException implements FileScopeProvider {
        public static final ThrowException INSTANCE = new ThrowException();

        private ThrowException() {
        }

        @Override // org.jetbrains.kotlin.resolve.lazy.FileScopeProvider
        /* JADX INFO: renamed from: getFileScopes, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ FileScopes mo7444getFileScopes(KtFile ktFile) {
            return (FileScopes) getFileScopes(ktFile);
        }

        public Void getFileScopes(KtFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            throw new UnsupportedOperationException("Should not be called");
        }
    }
}
