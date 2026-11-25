package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Lexer {
    private static final Logger LOG = Logger.getInstance((Class<?>) Lexer.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "buf";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lexer/Lexer";
        } else {
            objArr[0] = "buffer";
        }
        if (i == 3) {
            objArr[1] = "getTokenSequence";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lexer/Lexer";
        } else {
            objArr[1] = "getTokenText";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "start";
        } else if (i != 3 && i != 4) {
            objArr[2] = "startMeasured";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract void advance();

    public abstract int getBufferEnd();

    public abstract CharSequence getBufferSequence();

    public abstract LexerPosition getCurrentPosition();

    public abstract int getState();

    public abstract int getTokenEnd();

    public abstract int getTokenStart();

    public abstract IElementType getTokenType();

    public abstract void restore(LexerPosition lexerPosition);

    public abstract void start(CharSequence charSequence, int i, int i2, int i3);

    private void startMeasured(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        Logger logger = LOG;
        if (!logger.isDebugEnabled()) {
            start(charSequence, i, i2, i3);
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        start(charSequence, i, i2, i3);
        long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
        if (jCurrentTimeMillis2 > 500) {
            logger.debug("Starting lexer took: ", Long.valueOf(jCurrentTimeMillis2), "; at ", Integer.valueOf(i), " - ", Integer.valueOf(i2), "; state: ", Integer.valueOf(i3), "; text: ", StringUtil.shortenTextWithEllipsis(charSequence.toString(), 1024, 500));
        }
    }

    public final void start(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        startMeasured(charSequence, i, i2, 0);
    }

    public final void start(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        startMeasured(charSequence, 0, charSequence.length(), 0);
    }

    public CharSequence getTokenSequence() {
        CharSequence charSequenceSubSequence = getBufferSequence().subSequence(getTokenStart(), getTokenEnd());
        if (charSequenceSubSequence == null) {
            $$$reportNull$$$0(3);
        }
        return charSequenceSubSequence;
    }

    public String getTokenText() {
        String string = getTokenSequence().toString();
        if (string == null) {
            $$$reportNull$$$0(4);
        }
        return string;
    }
}
