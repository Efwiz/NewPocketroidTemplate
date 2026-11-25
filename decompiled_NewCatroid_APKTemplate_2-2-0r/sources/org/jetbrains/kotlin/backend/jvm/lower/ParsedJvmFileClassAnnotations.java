package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FileClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ParsedJvmFileClassAnnotations;", "", "jvmName", "", "jvmPackageName", "Lorg/jetbrains/kotlin/name/FqName;", "isMultifileClass", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/name/FqName;Z)V", "()Z", "getJvmName", "()Ljava/lang/String;", "getJvmPackageName", "()Lorg/jetbrains/kotlin/name/FqName;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParsedJvmFileClassAnnotations {
    private final boolean isMultifileClass;
    private final String jvmName;
    private final FqName jvmPackageName;

    public ParsedJvmFileClassAnnotations(String str, FqName fqName, boolean z) {
        this.jvmName = str;
        this.jvmPackageName = fqName;
        this.isMultifileClass = z;
    }

    public final String getJvmName() {
        return this.jvmName;
    }

    public final FqName getJvmPackageName() {
        return this.jvmPackageName;
    }

    /* JADX INFO: renamed from: isMultifileClass, reason: from getter */
    public final boolean getIsMultifileClass() {
        return this.isMultifileClass;
    }
}
