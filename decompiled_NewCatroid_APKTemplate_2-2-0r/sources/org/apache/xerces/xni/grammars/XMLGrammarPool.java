package org.apache.xerces.xni.grammars;

/* JADX INFO: loaded from: classes13.dex */
public interface XMLGrammarPool {
    void cacheGrammars(String str, Grammar[] grammarArr);

    void clear();

    void lockPool();

    Grammar retrieveGrammar(XMLGrammarDescription xMLGrammarDescription);

    Grammar[] retrieveInitialGrammarSet(String str);

    void unlockPool();
}
