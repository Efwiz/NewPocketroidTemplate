package io.github.rosemoe.sora.lang.completion;

import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;

/* JADX INFO: loaded from: classes11.dex */
public class CompletionHelper {

    public interface PrefixChecker {
        boolean check(char c);
    }

    public static String computePrefix(ContentReference contentReference, CharPosition charPosition, PrefixChecker prefixChecker) {
        int i = charPosition.column;
        String line = contentReference.getLine(charPosition.line);
        while (i > 0 && prefixChecker.check(line.charAt(i - 1))) {
            i--;
        }
        return line.substring(i, charPosition.column);
    }

    public static boolean checkCancelled() {
        Thread threadCurrentThread = Thread.currentThread();
        if (threadCurrentThread instanceof EditorAutoCompletion.CompletionThread) {
            return ((EditorAutoCompletion.CompletionThread) threadCurrentThread).isCancelled();
        }
        return false;
    }
}
