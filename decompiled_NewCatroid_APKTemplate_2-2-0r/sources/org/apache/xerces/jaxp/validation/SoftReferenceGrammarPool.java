package org.apache.xerces.jaxp.validation;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.grammars.XMLSchemaDescription;

/* JADX INFO: loaded from: classes13.dex */
final class SoftReferenceGrammarPool implements XMLGrammarPool {
    protected static final int TABLE_SIZE = 11;
    protected static final Grammar[] ZERO_LENGTH_GRAMMAR_ARRAY = new Grammar[0];
    protected Entry[] fGrammars;
    protected int fGrammarCount = 0;
    protected final ReferenceQueue fReferenceQueue = new ReferenceQueue();
    protected boolean fPoolIsLocked = false;

    static final class Entry {
        public int bucket;
        public XMLGrammarDescription desc;
        public SoftGrammarReference grammar;
        public int hash;
        public Entry next;
        public Entry prev;

        protected Entry(int i, int i2, XMLGrammarDescription xMLGrammarDescription, Grammar grammar, Entry entry, ReferenceQueue referenceQueue) {
            this.hash = i;
            this.bucket = i2;
            this.prev = null;
            this.next = entry;
            if (entry != null) {
                entry.prev = this;
            }
            this.desc = xMLGrammarDescription;
            this.grammar = new SoftGrammarReference(this, grammar, referenceQueue);
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

    static final class SoftGrammarReference extends SoftReference {
        public Entry entry;

        protected SoftGrammarReference(Entry entry, Grammar grammar, ReferenceQueue referenceQueue) {
            super(grammar, referenceQueue);
            this.entry = entry;
        }
    }

    public SoftReferenceGrammarPool() {
        this.fGrammars = null;
        this.fGrammars = new Entry[11];
    }

    public SoftReferenceGrammarPool(int i) {
        this.fGrammars = null;
        this.fGrammars = new Entry[i];
    }

    private void clean() {
        while (true) {
            Reference referencePoll = this.fReferenceQueue.poll();
            if (referencePoll == null) {
                return;
            }
            Entry entry = ((SoftGrammarReference) referencePoll).entry;
            if (entry != null) {
                removeEntry(entry);
            }
        }
    }

    private Grammar removeEntry(Entry entry) {
        if (entry.prev != null) {
            entry.prev.next = entry.next;
        } else {
            this.fGrammars[entry.bucket] = entry.next;
        }
        if (entry.next != null) {
            entry.next.prev = entry.prev;
        }
        this.fGrammarCount--;
        entry.grammar.entry = null;
        return (Grammar) entry.grammar.get();
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
            clean();
            int iHashCode = hashCode(xMLGrammarDescription);
            Entry[] entryArr = this.fGrammars;
            for (Entry entry = entryArr[(Integer.MAX_VALUE & iHashCode) % entryArr.length]; entry != null; entry = entry.next) {
                if (((Grammar) entry.grammar.get()) == null) {
                    removeEntry(entry);
                } else if (entry.hash == iHashCode && equals(entry.desc, xMLGrammarDescription)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean equals(XMLGrammarDescription xMLGrammarDescription, XMLGrammarDescription xMLGrammarDescription2) {
        if (!(xMLGrammarDescription instanceof XMLSchemaDescription)) {
            return xMLGrammarDescription.equals(xMLGrammarDescription2);
        }
        if (!(xMLGrammarDescription2 instanceof XMLSchemaDescription)) {
            return false;
        }
        XMLSchemaDescription xMLSchemaDescription = (XMLSchemaDescription) xMLGrammarDescription;
        XMLSchemaDescription xMLSchemaDescription2 = (XMLSchemaDescription) xMLGrammarDescription2;
        String targetNamespace = xMLSchemaDescription.getTargetNamespace();
        if (targetNamespace != null) {
            if (!targetNamespace.equals(xMLSchemaDescription2.getTargetNamespace())) {
                return false;
            }
        } else if (xMLSchemaDescription2.getTargetNamespace() != null) {
            return false;
        }
        String expandedSystemId = xMLSchemaDescription.getExpandedSystemId();
        if (expandedSystemId != null) {
            return expandedSystemId.equals(xMLSchemaDescription2.getExpandedSystemId());
        }
        return xMLSchemaDescription2.getExpandedSystemId() == null;
    }

    public Grammar getGrammar(XMLGrammarDescription xMLGrammarDescription) {
        synchronized (this.fGrammars) {
            clean();
            int iHashCode = hashCode(xMLGrammarDescription);
            Entry[] entryArr = this.fGrammars;
            for (Entry entry = entryArr[(Integer.MAX_VALUE & iHashCode) % entryArr.length]; entry != null; entry = entry.next) {
                Grammar grammar = (Grammar) entry.grammar.get();
                if (grammar == null) {
                    removeEntry(entry);
                } else if (entry.hash == iHashCode && equals(entry.desc, xMLGrammarDescription)) {
                    return grammar;
                }
            }
            return null;
        }
    }

    public int hashCode(XMLGrammarDescription xMLGrammarDescription) {
        if (!(xMLGrammarDescription instanceof XMLSchemaDescription)) {
            return xMLGrammarDescription.hashCode();
        }
        XMLSchemaDescription xMLSchemaDescription = (XMLSchemaDescription) xMLGrammarDescription;
        String targetNamespace = xMLSchemaDescription.getTargetNamespace();
        String expandedSystemId = xMLSchemaDescription.getExpandedSystemId();
        return (targetNamespace != null ? targetNamespace.hashCode() : 0) ^ (expandedSystemId != null ? expandedSystemId.hashCode() : 0);
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
            clean();
            XMLGrammarDescription grammarDescription = grammar.getGrammarDescription();
            int iHashCode = hashCode(grammarDescription);
            Entry[] entryArr = this.fGrammars;
            int length = (Integer.MAX_VALUE & iHashCode) % entryArr.length;
            for (Entry entry = entryArr[length]; entry != null; entry = entry.next) {
                if (entry.hash == iHashCode && equals(entry.desc, grammarDescription)) {
                    if (entry.grammar.get() != grammar) {
                        entry.grammar = new SoftGrammarReference(entry, grammar, this.fReferenceQueue);
                    }
                    return;
                }
            }
            this.fGrammars[length] = new Entry(iHashCode, length, grammarDescription, grammar, this.fGrammars[length], this.fReferenceQueue);
            this.fGrammarCount++;
        }
    }

    public Grammar removeGrammar(XMLGrammarDescription xMLGrammarDescription) {
        synchronized (this.fGrammars) {
            clean();
            int iHashCode = hashCode(xMLGrammarDescription);
            Entry[] entryArr = this.fGrammars;
            for (Entry entry = entryArr[(Integer.MAX_VALUE & iHashCode) % entryArr.length]; entry != null; entry = entry.next) {
                if (entry.hash == iHashCode && equals(entry.desc, xMLGrammarDescription)) {
                    return removeEntry(entry);
                }
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
            clean();
            grammarArr = ZERO_LENGTH_GRAMMAR_ARRAY;
        }
        return grammarArr;
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
    public void unlockPool() {
        this.fPoolIsLocked = false;
    }
}
