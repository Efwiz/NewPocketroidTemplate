package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: Comment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0000X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/Comment;", "", "text", "", StageActivity.STYLE_IS_MULTI_LINE, "", "(Ljava/lang/String;Z)V", "()Z", ES6Iterator.NEXT_METHOD, "getNext", "()Lcom/google/gwt/dev/js/rhino/Comment;", "setNext", "(Lcom/google/gwt/dev/js/rhino/Comment;)V", "getText", "()Ljava/lang/String;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Comment {
    private final boolean isMultiLine;
    private Comment next;
    private final String text;

    public Comment(String text, boolean z) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
        this.isMultiLine = z;
    }

    public final String getText() {
        return this.text;
    }

    /* JADX INFO: renamed from: isMultiLine, reason: from getter */
    public final boolean getIsMultiLine() {
        return this.isMultiLine;
    }

    public final Comment getNext() {
        return this.next;
    }

    public final void setNext(Comment comment) {
        this.next = comment;
    }
}
