package io.github.rosemoe.sora.data;

import io.github.rosemoe.sora.lang.styling.CodeBlock;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class ObjectAllocator {
    private static final int RECYCLE_LIMIT = 8192;
    private static List<CodeBlock> codeBlocks;
    private static List<CodeBlock> tempArray;

    public static void recycleBlockLines(List<CodeBlock> list) {
        if (list == null) {
            return;
        }
        List<CodeBlock> list2 = codeBlocks;
        if (list2 == null) {
            codeBlocks = list;
            return;
        }
        int size = list2.size();
        int size2 = list.size();
        while (size2 > 0 && size < 8192) {
            size++;
            size2--;
            CodeBlock codeBlock = list.get(size2);
            codeBlock.clear();
            codeBlocks.add(codeBlock);
        }
        list.clear();
        synchronized (ObjectAllocator.class) {
            tempArray = list;
        }
    }

    public static List<CodeBlock> obtainList() {
        List<CodeBlock> list;
        synchronized (ObjectAllocator.class) {
            list = tempArray;
            tempArray = null;
        }
        return list == null ? new ArrayList(128) : list;
    }

    public static CodeBlock obtainBlockLine() {
        List<CodeBlock> list = codeBlocks;
        if (list == null || list.isEmpty()) {
            return new CodeBlock();
        }
        List<CodeBlock> list2 = codeBlocks;
        return list2.remove(list2.size() - 1);
    }
}
