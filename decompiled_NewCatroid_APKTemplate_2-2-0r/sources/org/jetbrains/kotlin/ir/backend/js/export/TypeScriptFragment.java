package org.jetbrains.kotlin.ir.backend.js.export;

import com.reandroid.apkeditor.Options;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ExportModelToTsDeclarations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\b\u0087@\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\r\u001a\u00020\u000eHÖ\u0001¢\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u0003HÖ\u0001¢\u0006\u0004\b\u0012\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "", Options.TYPE_RAW, "", "constructor-impl", "(Ljava/lang/String;)Ljava/lang/String;", "getRaw", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(Ljava/lang/String;Ljava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(Ljava/lang/String;)I", "toString", "toString-impl", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class TypeScriptFragment {
    private final String raw;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TypeScriptFragment m7089boximpl(String str) {
        return new TypeScriptFragment(str);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static String m7090constructorimpl(String raw) {
        Intrinsics.checkNotNullParameter(raw, "raw");
        return raw;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m7091equalsimpl(String str, Object obj) {
        return (obj instanceof TypeScriptFragment) && Intrinsics.areEqual(str, ((TypeScriptFragment) obj).m7095unboximpl());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m7092equalsimpl0(String str, String str2) {
        return Intrinsics.areEqual(str, str2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m7093hashCodeimpl(String str) {
        return str.hashCode();
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m7094toStringimpl(String str) {
        return "TypeScriptFragment(raw=" + str + ')';
    }

    public boolean equals(Object obj) {
        return m7091equalsimpl(this.raw, obj);
    }

    public int hashCode() {
        return m7093hashCodeimpl(this.raw);
    }

    public String toString() {
        return m7094toStringimpl(this.raw);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ String m7095unboximpl() {
        return this.raw;
    }

    private /* synthetic */ TypeScriptFragment(String str) {
        this.raw = str;
    }

    public final String getRaw() {
        return this.raw;
    }
}
