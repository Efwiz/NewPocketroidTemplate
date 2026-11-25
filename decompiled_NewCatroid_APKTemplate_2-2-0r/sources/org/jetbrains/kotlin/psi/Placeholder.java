package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/psi/Placeholder;", "", "range", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "text", "", "(Lcom/intellij/openapi/util/TextRange;Ljava/lang/String;)V", "getRange", "()Lcom/intellij/openapi/util/TextRange;", "getText", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class Placeholder {
    private final TextRange range;
    private final String text;

    public static /* synthetic */ Placeholder copy$default(Placeholder placeholder, TextRange textRange, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            textRange = placeholder.range;
        }
        if ((i & 2) != 0) {
            str = placeholder.text;
        }
        return placeholder.copy(textRange, str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final TextRange getRange() {
        return this.range;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getText() {
        return this.text;
    }

    public final Placeholder copy(TextRange range, String text) {
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(text, "text");
        return new Placeholder(range, text);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Placeholder)) {
            return false;
        }
        Placeholder placeholder = (Placeholder) other;
        return Intrinsics.areEqual(this.range, placeholder.range) && Intrinsics.areEqual(this.text, placeholder.text);
    }

    public int hashCode() {
        return (this.range.hashCode() * 31) + this.text.hashCode();
    }

    public String toString() {
        return "Placeholder(range=" + this.range + ", text=" + this.text + ')';
    }

    public Placeholder(TextRange range, String text) {
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(text, "text");
        this.range = range;
        this.text = text;
    }

    public final TextRange getRange() {
        return this.range;
    }

    public final String getText() {
        return this.text;
    }
}
