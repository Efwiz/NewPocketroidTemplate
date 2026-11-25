package antlr.ASdebug;

import antlr.Token;

/* JADX INFO: loaded from: classes.dex */
public interface IASDebugStream {
    String getEntireText();

    TokenOffsetInfo getOffsetInfo(Token token);
}
