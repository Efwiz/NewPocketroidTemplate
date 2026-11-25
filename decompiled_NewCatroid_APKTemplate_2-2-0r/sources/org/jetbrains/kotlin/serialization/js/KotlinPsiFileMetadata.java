package org.jetbrains.kotlin.serialization.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KotlinFileMetadata.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinPsiFileMetadata;", "Lorg/jetbrains/kotlin/serialization/js/KotlinFileMetadata;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lorg/jetbrains/kotlin/psi/KtFile;)V", "getKtFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KotlinPsiFileMetadata extends KotlinFileMetadata {
    private final KtFile ktFile;

    public static /* synthetic */ KotlinPsiFileMetadata copy$default(KotlinPsiFileMetadata kotlinPsiFileMetadata, KtFile ktFile, int i, Object obj) {
        if ((i & 1) != 0) {
            ktFile = kotlinPsiFileMetadata.ktFile;
        }
        return kotlinPsiFileMetadata.copy(ktFile);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KtFile getKtFile() {
        return this.ktFile;
    }

    public final KotlinPsiFileMetadata copy(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        return new KotlinPsiFileMetadata(ktFile);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof KotlinPsiFileMetadata) && Intrinsics.areEqual(this.ktFile, ((KotlinPsiFileMetadata) other).ktFile);
    }

    public int hashCode() {
        return this.ktFile.hashCode();
    }

    public String toString() {
        return "KotlinPsiFileMetadata(ktFile=" + this.ktFile + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinPsiFileMetadata(KtFile ktFile) {
        super(null);
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        this.ktFile = ktFile;
    }

    public final KtFile getKtFile() {
        return this.ktFile;
    }
}
