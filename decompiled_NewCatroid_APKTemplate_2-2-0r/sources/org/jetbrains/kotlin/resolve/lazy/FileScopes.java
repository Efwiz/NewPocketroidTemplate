package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: compiled from: FileScopeFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopes;", "", "lexicalScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "importingScope", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "importForceResolver", "Lorg/jetbrains/kotlin/resolve/lazy/ImportForceResolver;", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;Lorg/jetbrains/kotlin/resolve/lazy/ImportForceResolver;)V", "getImportForceResolver", "()Lorg/jetbrains/kotlin/resolve/lazy/ImportForceResolver;", "getImportingScope", "()Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "getLexicalScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FileScopes {
    private final ImportForceResolver importForceResolver;
    private final ImportingScope importingScope;
    private final LexicalScope lexicalScope;

    public static /* synthetic */ FileScopes copy$default(FileScopes fileScopes, LexicalScope lexicalScope, ImportingScope importingScope, ImportForceResolver importForceResolver, int i, Object obj) {
        if ((i & 1) != 0) {
            lexicalScope = fileScopes.lexicalScope;
        }
        if ((i & 2) != 0) {
            importingScope = fileScopes.importingScope;
        }
        if ((i & 4) != 0) {
            importForceResolver = fileScopes.importForceResolver;
        }
        return fileScopes.copy(lexicalScope, importingScope, importForceResolver);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final LexicalScope getLexicalScope() {
        return this.lexicalScope;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ImportingScope getImportingScope() {
        return this.importingScope;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ImportForceResolver getImportForceResolver() {
        return this.importForceResolver;
    }

    public final FileScopes copy(LexicalScope lexicalScope, ImportingScope importingScope, ImportForceResolver importForceResolver) {
        Intrinsics.checkNotNullParameter(lexicalScope, "lexicalScope");
        Intrinsics.checkNotNullParameter(importingScope, "importingScope");
        Intrinsics.checkNotNullParameter(importForceResolver, "importForceResolver");
        return new FileScopes(lexicalScope, importingScope, importForceResolver);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FileScopes)) {
            return false;
        }
        FileScopes fileScopes = (FileScopes) other;
        return Intrinsics.areEqual(this.lexicalScope, fileScopes.lexicalScope) && Intrinsics.areEqual(this.importingScope, fileScopes.importingScope) && Intrinsics.areEqual(this.importForceResolver, fileScopes.importForceResolver);
    }

    public int hashCode() {
        return (((this.lexicalScope.hashCode() * 31) + this.importingScope.hashCode()) * 31) + this.importForceResolver.hashCode();
    }

    public String toString() {
        return "FileScopes(lexicalScope=" + this.lexicalScope + ", importingScope=" + this.importingScope + ", importForceResolver=" + this.importForceResolver + ')';
    }

    public FileScopes(LexicalScope lexicalScope, ImportingScope importingScope, ImportForceResolver importForceResolver) {
        Intrinsics.checkNotNullParameter(lexicalScope, "lexicalScope");
        Intrinsics.checkNotNullParameter(importingScope, "importingScope");
        Intrinsics.checkNotNullParameter(importForceResolver, "importForceResolver");
        this.lexicalScope = lexicalScope;
        this.importingScope = importingScope;
        this.importForceResolver = importForceResolver;
    }

    public final ImportForceResolver getImportForceResolver() {
        return this.importForceResolver;
    }

    public final ImportingScope getImportingScope() {
        return this.importingScope;
    }

    public final LexicalScope getLexicalScope() {
        return this.lexicalScope;
    }
}
