package org.apache.xerces.util;

import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

/* JADX INFO: loaded from: classes13.dex */
public class XMLGrammarPoolImpl implements XMLGrammarPool {
    private static final boolean DEBUG = false;
    protected static final int TABLE_SIZE = 11;
    protected Entry[] fGrammars;
    protected int fGrammarCount = 0;
    protected boolean fPoolIsLocked = false;

    protected static final class Entry {
        public XMLGrammarDescription desc;
        public Grammar grammar;
        public int hash;
        public Entry next;

        protected Entry(int i, XMLGrammarDescription xMLGrammarDescription, Grammar grammar, Entry entry) {
            this.hash = i;
            this.desc = xMLGrammarDescription;
            this.grammar = grammar;
            this.next = entry;
        }

        protected void clear() {
            this.desc = null;
            this.grammar = null;
            Entry entry = this.next;
            if (entry != null) {
                entry.clear();
                this.next = null;
            }
        }
    }

    public XMLGrammarPoolImpl() {
        this.fGrammars = null;
        this.fGrammars = new Entry[11];
    }

    public XMLGrammarPoolImpl(int i) {
        this.fGrammars = null;
        this.fGrammars = new Entry[i];
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
    public void cacheGrammars(String str, Grammar[] grammarArr) {
        if (this.fPoolIsLocked) {
            return;
        }
        for (Grammar grammar : grammarArr) {
            putGrammar(grammar);
        }
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
    public void clear() {
        int i = 0;
        while (true) {
            Entry[] entryArr = this.fGrammars;
            if (i >= entryArr.length) {
                this.fGrammarCount = 0;
                return;
            }
            Entry entry = entryArr[i];
            if (entry != null) {
                entry.clear();
                this.fGrammars[i] = null;
            }
            i++;
        }
    }

    public boolean containsGrammar(XMLGrammarDescription xMLGrammarDescription) {
        synchronized (this.fGrammars) {
            int iHashCode = hashCode(xMLGrammarDescription);
            Entry[] entryArr = this.fGrammars;
            for (Entry entry = entryArr[(Integer.MAX_VALUE & iHashCode) % entryArr.length]; entry != null; entry = entry.next) {
                if (entry.hash == iHashCode && equals(entry.desc, xMLGrammarDescription)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean equals(XMLGrammarDescription xMLGrammarDescription, XMLGrammarDescription xMLGrammarDescription2) {
        return xMLGrammarDescription.equals(xMLGrammarDescription2);
    }

    public Grammar getGrammar(XMLGrammarDescription xMLGrammarDescription) {
        synchronized (this.fGrammars) {
            int iHashCode = hashCode(xMLGrammarDescription);
            Entry[] entryArr = this.fGrammars;
            for (Entry entry = entryArr[(Integer.MAX_VALUE & iHashCode) % entryArr.length]; entry != null; entry = entry.next) {
                if (entry.hash == iHashCode && equals(entry.desc, xMLGrammarDescription)) {
                    return entry.grammar;
                }
            }
            return null;
        }
    }

    public int hashCode(XMLGrammarDescription xMLGrammarDescription) {
        return xMLGrammarDescription.hashCode();
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
    public void lockPool() {
        this.fPoolIsLocked = true;
    }

    public void putGrammar(Grammar grammar) {
        if (this.fPoolIsLocked) {
            return;
        }
        synchronized (this.fGrammars) {
            XMLGrammarDescription grammarDescription = grammar.getGrammarDescription();
            int iHashCode = hashCode(grammarDescription);
            Entry[] entryArr = this.fGrammars;
            int length = (Integer.MAX_VALUE & iHashCode) % entryArr.length;
            for (Entry entry = entryArr[length]; entry != null; entry = entry.next) {
                if (entry.hash == iHashCode && equals(entry.desc, grammarDescription)) {
                    entry.grammar = grammar;
                    return;
                }
            }
            this.fGrammars[length] = new Entry(iHashCode, grammarDescription, grammar, this.fGrammars[length]);
            this.fGrammarCount++;
        }
    }

    public Grammar removeGrammar(XMLGrammarDescription xMLGrammarDescription) {
        synchronized (this.fGrammars) {
            int iHashCode = hashCode(xMLGrammarDescription);
            Entry[] entryArr = this.fGrammars;
            int length = (Integer.MAX_VALUE & iHashCode) % entryArr.length;
            Entry entry = null;
            for (Entry entry2 = entryArr[length]; entry2 != null; entry2 = entry2.next) {
                if (entry2.hash == iHashCode && equals(entry2.desc, xMLGrammarDescription)) {
                    if (entry != null) {
                        entry.next = entry2.next;
                    } else {
                        this.fGrammars[length] = entry2.next;
                    }
                    Grammar grammar = entry2.grammar;
                    entry2.grammar = null;
                    this.fGrammarCount--;
                    return grammar;
                }
                entry = entry2;
            }
            return null;
        }
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
    public Grammar retrieveGrammar(XMLGrammarDescription xMLGrammarDescription) {
        return getGrammar(xMLGrammarDescription);
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
    public Grammar[] retrieveInitialGrammarSet(String str) {
        Grammar[] grammarArr;
        synchronized (this.fGrammars) {
            int length = this.fGrammars.length;
            Grammar[] grammarArr2 = new Grammar[this.fGrammarCount];
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                for (Entry entry = this.fGrammars[i2]; entry != null; entry = entry.next) {
                    if (entry.desc.getGrammarType().equals(str)) {
                        grammarArr2[i] = entry.grammar;
                        i++;
                    }
                }
            }
            grammarArr = new Grammar[i];
            System.arraycopy(grammarArr2, 0, grammarArr, 0, i);
        }
        return grammarArr;
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
    public void unlockPool() {
        this.fPoolIsLocked = false;
    }
}
