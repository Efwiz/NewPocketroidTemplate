package io.github.rosemoe.sora.lang.completion.snippet;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PlainPlaceholderElement.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\u0011"}, d2 = {"Lio/github/rosemoe/sora/lang/completion/snippet/PlainPlaceholderElement;", "Lio/github/rosemoe/sora/lang/completion/snippet/PlaceHolderElement;", "text", "", "(Ljava/lang/String;)V", "getText", "()Ljava/lang/String;", "setText", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PlainPlaceholderElement implements PlaceHolderElement {
    private String text;

    public static /* synthetic */ PlainPlaceholderElement copy$default(PlainPlaceholderElement plainPlaceholderElement, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = plainPlaceholderElement.text;
        }
        return plainPlaceholderElement.copy(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getText() {
        return this.text;
    }

    public final PlainPlaceholderElement copy(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return new PlainPlaceholderElement(text);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof PlainPlaceholderElement) && Intrinsics.areEqual(this.text, ((PlainPlaceholderElement) other).text);
    }

    public int hashCode() {
        return this.text.hashCode();
    }

    public String toString() {
        return "PlainPlaceholderElement(text=" + this.text + Constant.AFTER_QUTO;
    }

    public PlainPlaceholderElement(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
    }

    public final String getText() {
        return this.text;
    }

    public final void setText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.text = str;
    }
}
