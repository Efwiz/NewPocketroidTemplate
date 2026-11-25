package org.antlr.runtime.tree;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.TokenStream;

/* JADX INFO: loaded from: classes12.dex */
public interface TreeNodeStream extends IntStream {
    Object LT(int i);

    Object get(int i);

    TokenStream getTokenStream();

    TreeAdaptor getTreeAdaptor();

    Object getTreeSource();

    void replaceChildren(Object obj, int i, int i2, Object obj2);

    void reset();

    void setUniqueNavigationNodes(boolean z);

    String toString(Object obj, Object obj2);
}
