package org.apache.xerces.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: classes13.dex */
public class SoftReferenceSymbolTable extends SymbolTable {
    protected SREntry[] fBuckets;
    private final ReferenceQueue fReferenceQueue;

    protected static final class SREntry extends SoftReference {
        public int bucket;
        public SREntry next;
        public SREntry prev;

        public SREntry(String str, SREntry sREntry, int i, ReferenceQueue referenceQueue) {
            super(new SREntryData(str), referenceQueue);
            initialize(sREntry, i);
        }

        public SREntry(String str, char[] cArr, int i, int i2, SREntry sREntry, int i3, ReferenceQueue referenceQueue) {
            super(new SREntryData(str, cArr, i, i2), referenceQueue);
            initialize(sREntry, i3);
        }

        private void initialize(SREntry sREntry, int i) {
            this.next = sREntry;
            if (sREntry != null) {
                sREntry.prev = this;
            }
            this.prev = null;
            this.bucket = i;
        }
    }

    protected static final class SREntryData {
        public final char[] characters;
        public final String symbol;

        public SREntryData(String str) {
            this.symbol = str;
            char[] cArr = new char[str.length()];
            this.characters = cArr;
            str.getChars(0, cArr.length, cArr, 0);
        }

        public SREntryData(String str, char[] cArr, int i, int i2) {
            this.symbol = str;
            char[] cArr2 = new char[i2];
            this.characters = cArr2;
            System.arraycopy(cArr, i, cArr2, 0, i2);
        }
    }

    public SoftReferenceSymbolTable() {
        this(101, 0.75f);
    }

    public SoftReferenceSymbolTable(int i) {
        this(i, 0.75f);
    }

    public SoftReferenceSymbolTable(int i, float f) {
        super(1, f);
        this.fBuckets = null;
        if (i < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        }
        if (f <= 0.0f || Float.isNaN(f)) {
            throw new IllegalArgumentException("Illegal Load: " + f);
        }
        i = i == 0 ? 1 : i;
        this.fLoadFactor = f;
        this.fTableSize = i;
        this.fBuckets = new SREntry[this.fTableSize];
        this.fThreshold = (int) (this.fTableSize * f);
        this.fCount = 0;
        this.fReferenceQueue = new ReferenceQueue();
    }

    private String addSymbol0(String str, int i, int i2) {
        if (this.fCount < this.fThreshold) {
            if (i2 >= this.fCollisionThreshold) {
                rebalance();
            }
            String strIntern = str.intern();
            this.fBuckets[i] = new SREntry(strIntern, this.fBuckets[i], i, this.fReferenceQueue);
            this.fCount++;
            return strIntern;
        }
        rehash();
        i = hash(str) % this.fTableSize;
        String strIntern2 = str.intern();
        this.fBuckets[i] = new SREntry(strIntern2, this.fBuckets[i], i, this.fReferenceQueue);
        this.fCount++;
        return strIntern2;
    }

    private String addSymbol0(char[] cArr, int i, int i2, int i3, int i4) {
        if (this.fCount < this.fThreshold) {
            if (i4 >= this.fCollisionThreshold) {
                rebalance();
            }
            String strIntern = new String(cArr, i, i2).intern();
            this.fBuckets[i3] = new SREntry(strIntern, cArr, i, i2, this.fBuckets[i3], i3, this.fReferenceQueue);
            this.fCount++;
            return strIntern;
        }
        rehash();
        i3 = hash(cArr, i, i2) % this.fTableSize;
        String strIntern2 = new String(cArr, i, i2).intern();
        this.fBuckets[i3] = new SREntry(strIntern2, cArr, i, i2, this.fBuckets[i3], i3, this.fReferenceQueue);
        this.fCount++;
        return strIntern2;
    }

    private void clean() {
        SREntry sREntry = (SREntry) this.fReferenceQueue.poll();
        if (sREntry != null) {
            do {
                removeEntry(sREntry);
                sREntry = (SREntry) this.fReferenceQueue.poll();
            } while (sREntry != null);
            if (this.fCount < (this.fThreshold >> 2)) {
                compact();
            }
        }
    }

    private void rehashCommon(int i) {
        SREntry[] sREntryArr = this.fBuckets;
        int length = sREntryArr.length;
        SREntry[] sREntryArr2 = new SREntry[i];
        this.fThreshold = (int) (i * this.fLoadFactor);
        this.fBuckets = sREntryArr2;
        this.fTableSize = sREntryArr2.length;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return;
            }
            SREntry sREntry = sREntryArr[i2];
            while (sREntry != null) {
                SREntry sREntry2 = sREntry.next;
                SREntryData sREntryData = (SREntryData) sREntry.get();
                if (sREntryData != null) {
                    int iHash = hash(sREntryData.symbol) % i;
                    SREntry sREntry3 = sREntryArr2[iHash];
                    if (sREntry3 != null) {
                        sREntry3.prev = sREntry;
                    }
                    sREntry.bucket = iHash;
                    sREntry.next = sREntryArr2[iHash];
                    sREntryArr2[iHash] = sREntry;
                } else {
                    sREntry.bucket = -1;
                    sREntry.next = null;
                    this.fCount--;
                }
                sREntry.prev = null;
                sREntry = sREntry2;
            }
            length = i2;
        }
    }

    private void removeEntry(SREntry sREntry) {
        int i = sREntry.bucket;
        if (i >= 0) {
            if (sREntry.next != null) {
                sREntry.next.prev = sREntry.prev;
            }
            if (sREntry.prev != null) {
                sREntry.prev.next = sREntry.next;
            } else {
                this.fBuckets[i] = sREntry.next;
            }
            this.fCount--;
        }
    }

    @Override // org.apache.xerces.util.SymbolTable
    public String addSymbol(String str) {
        clean();
        int iHash = hash(str) % this.fTableSize;
        int i = 0;
        for (SREntry sREntry = this.fBuckets[iHash]; sREntry != null; sREntry = sREntry.next) {
            SREntryData sREntryData = (SREntryData) sREntry.get();
            if (sREntryData != null) {
                if (sREntryData.symbol.equals(str)) {
                    return sREntryData.symbol;
                }
                i++;
            }
        }
        return addSymbol0(str, iHash, i);
    }

    @Override // org.apache.xerces.util.SymbolTable
    public String addSymbol(char[] cArr, int i, int i2) {
        clean();
        int iHash = hash(cArr, i, i2) % this.fTableSize;
        int i3 = 0;
        for (SREntry sREntry = this.fBuckets[iHash]; sREntry != null; sREntry = sREntry.next) {
            SREntryData sREntryData = (SREntryData) sREntry.get();
            if (sREntryData != null) {
                if (i2 == sREntryData.characters.length) {
                    for (int i4 = 0; i4 < i2; i4++) {
                        if (cArr[i + i4] == sREntryData.characters[i4]) {
                        }
                    }
                    return sREntryData.symbol;
                }
                i3++;
            }
        }
        return addSymbol0(cArr, i, i2, iHash, i3);
    }

    protected void compact() {
        rehashCommon((((int) (this.fCount / this.fLoadFactor)) * 2) + 1);
    }

    @Override // org.apache.xerces.util.SymbolTable
    public boolean containsSymbol(String str) {
        int iHash = hash(str) % this.fTableSize;
        int length = str.length();
        SREntry sREntry = this.fBuckets[iHash];
        while (true) {
            if (sREntry == null) {
                return false;
            }
            SREntryData sREntryData = (SREntryData) sREntry.get();
            if (sREntryData != null && length == sREntryData.characters.length) {
                for (int i = 0; i < length; i++) {
                    if (str.charAt(i) == sREntryData.characters[i]) {
                    }
                }
                return true;
            }
            sREntry = sREntry.next;
        }
    }

    @Override // org.apache.xerces.util.SymbolTable
    public boolean containsSymbol(char[] cArr, int i, int i2) {
        SREntry sREntry = this.fBuckets[hash(cArr, i, i2) % this.fTableSize];
        while (true) {
            if (sREntry == null) {
                return false;
            }
            SREntryData sREntryData = (SREntryData) sREntry.get();
            if (sREntryData != null && i2 == sREntryData.characters.length) {
                for (int i3 = 0; i3 < i2; i3++) {
                    if (cArr[i + i3] == sREntryData.characters[i3]) {
                    }
                }
                return true;
            }
            sREntry = sREntry.next;
        }
    }

    @Override // org.apache.xerces.util.SymbolTable
    protected void rebalance() {
        if (this.fHashMultipliers == null) {
            this.fHashMultipliers = new int[32];
        }
        PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
        rehashCommon(this.fBuckets.length);
    }

    @Override // org.apache.xerces.util.SymbolTable
    protected void rehash() {
        rehashCommon((this.fBuckets.length * 2) + 1);
    }
}
