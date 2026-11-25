package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibrary;

/* JADX INFO: compiled from: DeserializedSourceFile.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/DeserializedSourceFile;", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "name_", "", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/library/KotlinLibrary;)V", "getLibrary", "()Lorg/jetbrains/kotlin/library/KotlinLibrary;", "getName_", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getName", "hashCode", "", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializedSourceFile implements SourceFile {
    private final KotlinLibrary library;
    private final String name_;

    public DeserializedSourceFile(String name_, KotlinLibrary library) {
        Intrinsics.checkNotNullParameter(name_, "name_");
        Intrinsics.checkNotNullParameter(library, "library");
        this.name_ = name_;
        this.library = library;
    }

    public final KotlinLibrary getLibrary() {
        return this.library;
    }

    public final String getName_() {
        return this.name_;
    }

    @Override // org.jetbrains.kotlin.descriptors.SourceFile
    /* JADX INFO: renamed from: getName, reason: from getter */
    public String getName_() {
        return this.name_;
    }

    public boolean equals(Object other) {
        if (other instanceof DeserializedSourceFile) {
            DeserializedSourceFile deserializedSourceFile = (DeserializedSourceFile) other;
            if (Intrinsics.areEqual(this.library, deserializedSourceFile.library) && Intrinsics.areEqual(getName_(), deserializedSourceFile.getName_())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = this.library.hashCode();
        String name_ = getName_();
        return iHashCode ^ (name_ != null ? name_.hashCode() : 0);
    }
}
