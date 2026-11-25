package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0087@\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u000f\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\u0004\u0010\bJ\u001a\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u0011HÖ\u0001¢\u0006\u0004\b\u0012\u0010\u0013J\u0015\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017¢\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u001a\u001a\u00020\u0007H\u0016¢\u0006\u0004\b\u001b\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0088\u0001\u0006¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "", "lib", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "constructor-impl", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Ljava/lang/String;", "path", "", "(Ljava/lang/String;)Ljava/lang/String;", "getPath", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(Ljava/lang/String;Ljava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(Ljava/lang/String;)I", "toProtoStream", "", "out", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "toProtoStream-impl", "(Ljava/lang/String;Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;)V", "toString", "toString-impl", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class KotlinLibraryFile {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String path;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ KotlinLibraryFile m7149boximpl(String str) {
        return new KotlinLibraryFile(str);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static String m7150constructorimpl(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return path;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m7152equalsimpl(String str, Object obj) {
        return (obj instanceof KotlinLibraryFile) && Intrinsics.areEqual(str, ((KotlinLibraryFile) obj).m7157unboximpl());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m7153equalsimpl0(String str, String str2) {
        return Intrinsics.areEqual(str, str2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m7154hashCodeimpl(String str) {
        return str.hashCode();
    }

    public boolean equals(Object obj) {
        return m7152equalsimpl(this.path, obj);
    }

    public int hashCode() {
        return m7154hashCodeimpl(this.path);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ String m7157unboximpl() {
        return this.path;
    }

    private /* synthetic */ KotlinLibraryFile(String str) {
        this.path = str;
    }

    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static String m7151constructorimpl(KotlinLibrary lib) {
        Intrinsics.checkNotNullParameter(lib, "lib");
        return m7150constructorimpl(lib.getLibraryFile().getCanonicalPath());
    }

    /* JADX INFO: renamed from: toProtoStream-impl, reason: not valid java name */
    public static final void m7155toProtoStreamimpl(String str, CodedOutputStream out) throws IOException {
        Intrinsics.checkNotNullParameter(out, "out");
        out.writeStringNoTag(str);
    }

    /* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile$Companion;", "", "()V", "fromProtoStream", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "input", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "fromProtoStream-BZAKev0", "(Lorg/jetbrains/kotlin/protobuf/CodedInputStream;)Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: fromProtoStream-BZAKev0, reason: not valid java name */
        public final String m7158fromProtoStreamBZAKev0(CodedInputStream input) throws IOException {
            Intrinsics.checkNotNullParameter(input, "input");
            String string = input.readString();
            Intrinsics.checkNotNullExpressionValue(string, "readString(...)");
            return KotlinLibraryFile.m7150constructorimpl(string);
        }
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m7156toStringimpl(String str) {
        String name2 = new File(str).getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    public String toString() {
        return m7156toStringimpl(this.path);
    }
}
