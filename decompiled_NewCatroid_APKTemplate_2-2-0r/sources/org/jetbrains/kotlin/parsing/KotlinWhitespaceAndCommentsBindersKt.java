package org.jetbrains.kotlin.parsing;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder;

/* JADX INFO: compiled from: KotlinWhitespaceAndCommentsBinders.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u0010\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0003\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0004\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"PRECEDING_ALL_BINDER", "Lorg/jetbrains/kotlin/com/intellij/lang/WhitespacesAndCommentsBinder;", "PRECEDING_ALL_COMMENTS_BINDER", "TRAILING_ALL_BINDER", "TRAILING_ALL_COMMENTS_BINDER", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinWhitespaceAndCommentsBindersKt {
    public static final WhitespacesAndCommentsBinder PRECEDING_ALL_COMMENTS_BINDER = new AllCommentsBinder(false);
    public static final WhitespacesAndCommentsBinder TRAILING_ALL_COMMENTS_BINDER = new AllCommentsBinder(true);
    public static final WhitespacesAndCommentsBinder PRECEDING_ALL_BINDER = new BindAll(false);
    public static final WhitespacesAndCommentsBinder TRAILING_ALL_BINDER = new BindAll(true);
}
