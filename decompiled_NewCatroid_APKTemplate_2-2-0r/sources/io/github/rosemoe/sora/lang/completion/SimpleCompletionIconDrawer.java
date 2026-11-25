package io.github.rosemoe.sora.lang.completion;

import android.graphics.drawable.Drawable;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SimpleCompletionIconDrawer.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Lio/github/rosemoe/sora/lang/completion/SimpleCompletionIconDrawer;", "", "()V", "draw", "Landroid/graphics/drawable/Drawable;", Namer.METADATA_CLASS_KIND, "Lio/github/rosemoe/sora/lang/completion/CompletionItemKind;", "circle", "", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleCompletionIconDrawer {
    public static final SimpleCompletionIconDrawer INSTANCE = new SimpleCompletionIconDrawer();

    @JvmStatic
    public static final Drawable draw(CompletionItemKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return draw$default(kind, false, 2, null);
    }

    private SimpleCompletionIconDrawer() {
    }

    public static /* synthetic */ Drawable draw$default(CompletionItemKind completionItemKind, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return draw(completionItemKind, z);
    }

    @JvmStatic
    public static final Drawable draw(CompletionItemKind kind, boolean circle) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new CircleDrawable(kind, circle);
    }
}
