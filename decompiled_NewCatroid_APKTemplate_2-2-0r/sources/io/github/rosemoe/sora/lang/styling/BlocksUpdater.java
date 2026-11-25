package io.github.rosemoe.sora.lang.styling;

import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class BlocksUpdater {
    public static void update(List<CodeBlock> list, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        Iterator<CodeBlock> it = list.iterator();
        while (it.hasNext()) {
            CodeBlock next = it.next();
            if (next.startLine >= i) {
                next.startLine += i2;
            }
            if (next.endLine >= i) {
                next.endLine += i2;
            }
            if (next.startLine >= next.endLine) {
                it.remove();
            }
        }
    }
}
