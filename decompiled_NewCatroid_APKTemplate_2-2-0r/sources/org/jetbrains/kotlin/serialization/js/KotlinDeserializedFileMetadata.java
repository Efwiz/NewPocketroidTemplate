package org.jetbrains.kotlin.serialization.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KotlinFileMetadata.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinDeserializedFileMetadata;", "Lorg/jetbrains/kotlin/serialization/js/KotlinFileMetadata;", "packageFragment", "Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptPackageFragment;", "fileId", "", "(Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptPackageFragment;I)V", "getFileId", "()I", "getPackageFragment", "()Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptPackageFragment;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KotlinDeserializedFileMetadata extends KotlinFileMetadata {
    private final int fileId;
    private final KotlinJavascriptPackageFragment packageFragment;

    public static /* synthetic */ KotlinDeserializedFileMetadata copy$default(KotlinDeserializedFileMetadata kotlinDeserializedFileMetadata, KotlinJavascriptPackageFragment kotlinJavascriptPackageFragment, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            kotlinJavascriptPackageFragment = kotlinDeserializedFileMetadata.packageFragment;
        }
        if ((i2 & 2) != 0) {
            i = kotlinDeserializedFileMetadata.fileId;
        }
        return kotlinDeserializedFileMetadata.copy(kotlinJavascriptPackageFragment, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KotlinJavascriptPackageFragment getPackageFragment() {
        return this.packageFragment;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getFileId() {
        return this.fileId;
    }

    public final KotlinDeserializedFileMetadata copy(KotlinJavascriptPackageFragment packageFragment, int fileId) {
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        return new KotlinDeserializedFileMetadata(packageFragment, fileId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KotlinDeserializedFileMetadata)) {
            return false;
        }
        KotlinDeserializedFileMetadata kotlinDeserializedFileMetadata = (KotlinDeserializedFileMetadata) other;
        return Intrinsics.areEqual(this.packageFragment, kotlinDeserializedFileMetadata.packageFragment) && this.fileId == kotlinDeserializedFileMetadata.fileId;
    }

    public int hashCode() {
        return (this.packageFragment.hashCode() * 31) + Integer.hashCode(this.fileId);
    }

    public String toString() {
        return "KotlinDeserializedFileMetadata(packageFragment=" + this.packageFragment + ", fileId=" + this.fileId + ')';
    }

    public final KotlinJavascriptPackageFragment getPackageFragment() {
        return this.packageFragment;
    }

    public final int getFileId() {
        return this.fileId;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinDeserializedFileMetadata(KotlinJavascriptPackageFragment packageFragment, int i) {
        super(null);
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        this.packageFragment = packageFragment;
        this.fileId = i;
    }
}
