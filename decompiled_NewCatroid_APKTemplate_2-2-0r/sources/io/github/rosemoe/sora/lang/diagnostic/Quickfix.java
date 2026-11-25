package io.github.rosemoe.sora.lang.diagnostic;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Quickfix.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB%\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000bJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lio/github/rosemoe/sora/lang/diagnostic/Quickfix;", "", "titleRes", "", "documentVersion", "", "fixAction", "Ljava/lang/Runnable;", "(IJLjava/lang/Runnable;)V", "title", "", "(Ljava/lang/CharSequence;JLjava/lang/Runnable;)V", "getDocumentVersion", "()J", "resourceId", "executeQuickfix", "", "resolveTitle", "context", "Landroid/content/Context;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class Quickfix {
    private final long documentVersion;
    private final Runnable fixAction;
    private int resourceId;
    private final CharSequence title;

    public Quickfix(CharSequence charSequence, long j, Runnable runnable) {
        this.title = charSequence;
        this.documentVersion = j;
        this.fixAction = runnable;
    }

    public /* synthetic */ Quickfix(CharSequence charSequence, long j, Runnable runnable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, (i & 2) != 0 ? 0L : j, (i & 4) != 0 ? null : runnable);
    }

    public final long getDocumentVersion() {
        return this.documentVersion;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Quickfix(int i, long j, Runnable fixAction) {
        this((CharSequence) null, j, fixAction);
        Intrinsics.checkNotNullParameter(fixAction, "fixAction");
        this.resourceId = i;
    }

    public /* synthetic */ Quickfix(int i, long j, Runnable runnable, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? 0L : j, runnable);
    }

    public CharSequence resolveTitle(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        CharSequence charSequence = this.title;
        if (charSequence != null) {
            return charSequence;
        }
        String string = context.getString(this.resourceId);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    public void executeQuickfix() {
        Runnable runnable = this.fixAction;
        if (runnable != null) {
            runnable.run();
        }
    }
}
