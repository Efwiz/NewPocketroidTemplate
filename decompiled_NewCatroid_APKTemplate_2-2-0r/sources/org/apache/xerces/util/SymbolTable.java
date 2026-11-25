package org.apache.xerces.util;

/* JADX INFO: loaded from: classes13.dex */
public class SymbolTable {
    protected static final int MAX_HASH_COLLISIONS = 40;
    protected static final int MULTIPLIERS_MASK = 31;
    protected static final int MULTIPLIERS_SIZE = 32;
    protected static final int TABLE_SIZE = 101;
    protected Entry[] fBuckets;
    protected final int fCollisionThreshold;
    protected transient int fCount;
    protected int[] fHashMultipliers;
    protected float fLoadFactor;
    protected int fTableSize;
    protected int fThreshold;

    protected static final class Entry {
        public final char[] characters;
        public Entry next;
        public final String symbol;

        public Entry(String str, Entry entry) {
            this.symbol = str.intern();
            char[] cArr = new char[str.length()];
            this.characters = cArr;
            str.getChars(0, cArr.length, cArr, 0);
            this.next = entry;
        }

        public Entry(char[] cArr, int i, int i2, Entry entry) {
            char[] cArr2 = new char[i2];
            this.characters = cArr2;
            System.arraycopy(cArr, i, cArr2, 0, i2);
            this.symbol = new String(cArr2).intern();
            this.next = entry;
        }
    }

    public SymbolTable() {
        this(101, 0.75f);
    }

    public SymbolTable(int i) {
        this(i, 0.75f);
    }

    public SymbolTable(int i, float f) {
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
        this.fBuckets = new Entry[i];
        this.fThreshold = (int) (i * f);
        this.fCollisionThreshold = (int) (f * 40.0f);
        this.fCount = 0;
    }

    private String addSymbol0(String str, int i, int i2) {
        if (this.fCount < this.fThreshold) {
            if (i2 >= this.fCollisionThreshold) {
                rebalance();
            }
            Entry entry = new Entry(str, this.fBuckets[i]);
            this.fBuckets[i] = entry;
            this.fCount++;
            return entry.symbol;
        }
        rehash();
        i = hash(str) % this.fTableSize;
        Entry entry2 = new Entry(str, this.fBuckets[i]);
        this.fBuckets[i] = entry2;
        this.fCount++;
        return entry2.symbol;
    }

    private String addSymbol0(char[] cArr, int i, int i2, int i3, int i4) {
        if (this.fCount < this.fThreshold) {
            if (i4 >= this.fCollisionThreshold) {
                rebalance();
            }
            Entry entry = new Entry(cArr, i, i2, this.fBuckets[i3]);
            this.fBuckets[i3] = entry;
            this.fCount++;
            return entry.symbol;
        }
        rehash();
        i3 = hash(cArr, i, i2) % this.fTableSize;
        Entry entry2 = new Entry(cArr, i, i2, this.fBuckets[i3]);
        this.fBuckets[i3] = entry2;
        this.fCount++;
        return entry2.symbol;
    }

    private int hash0(String str) {
        int length = str.length();
        int[] iArr = this.fHashMultipliers;
        int iCharAt = 0;
        for (int i = 0; i < length; i++) {
            iCharAt = (iCharAt * iArr[i & 31]) + str.charAt(i);
        }
        return Integer.MAX_VALUE & iCharAt;
    }

    private int hash0(char[] cArr, int i, int i2) {
        int[] iArr = this.fHashMultipliers;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 * iArr[i4 & 31]) + cArr[i + i4];
        }
        return Integer.MAX_VALUE & i3;
    }

    private void rehashCommon(int i) {
        Entry[] entryArr = this.fBuckets;
        int length = entryArr.length;
        Entry[] entryArr2 = new Entry[i];
        this.fThreshold = (int) (i * this.fLoadFactor);
        this.fBuckets = entryArr2;
        this.fTableSize = entryArr2.length;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return;
            }
            Entry entry = entryArr[i2];
            while (entry != null) {
                Entry entry2 = entry.next;
                int iHash = hash(entry.symbol) % i;
                entry.next = entryArr2[iHash];
                entryArr2[iHash] = entry;
                entry = entry2;
            }
            length = i2;
        }
    }

    public String addSymbol(String str) {
        int iHash = hash(str) % this.fTableSize;
        int i = 0;
        for (Entry entry = this.fBuckets[iHash]; entry != null; entry = entry.next) {
            if (entry.symbol.equals(str)) {
                return entry.symbol;
            }
            i++;
        }
        return addSymbol0(str, iHash, i);
    }

    public String addSymbol(char[] cArr, int i, int i2) {
        int iHash = hash(cArr, i, i2) % this.fTableSize;
        int i3 = 0;
        for (Entry entry = this.fBuckets[iHash]; entry != null; entry = entry.next) {
            if (i2 == entry.characters.length) {
                for (int i4 = 0; i4 < i2; i4++) {
                    if (cArr[i + i4] == entry.characters[i4]) {
                    }
                }
                return entry.symbol;
            }
            i3++;
        }
        return addSymbol0(cArr, i, i2, iHash, i3);
    }

    public boolean containsSymbol(String str) {
        int iHash = hash(str) % this.fTableSize;
        int length = str.length();
        Entry entry = this.fBuckets[iHash];
        while (true) {
            if (entry == null) {
                return false;
            }
            if (length == entry.characters.length) {
                for (int i = 0; i < length; i++) {
                    if (str.charAt(i) == entry.characters[i]) {
                    }
                }
                return true;
            }
            entry = entry.next;
        }
    }

    public boolean containsSymbol(char[] cArr, int i, int i2) {
        Entry entry = this.fBuckets[hash(cArr, i, i2) % this.fTableSize];
        while (true) {
            if (entry == null) {
                return false;
            }
            if (i2 == entry.characters.length) {
                for (int i3 = 0; i3 < i2; i3++) {
                    if (cArr[i + i3] == entry.characters[i3]) {
                    }
                }
                return true;
            }
            entry = entry.next;
        }
    }

    public int hash(String str) {
        return this.fHashMultipliers == null ? str.hashCode() & Integer.MAX_VALUE : hash0(str);
    }

    public int hash(char[] cArr, int i, int i2) {
        if (this.fHashMultipliers != null) {
            return hash0(cArr, i, i2);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 * 31) + cArr[i + i4];
        }
        return Integer.MAX_VALUE & i3;
    }

    protected void rebalance() {
        if (this.fHashMultipliers == null) {
            this.fHashMultipliers = new int[32];
        }
        PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
        rehashCommon(this.fBuckets.length);
    }

    protected void rehash() {
        rehashCommon((this.fBuckets.length * 2) + 1);
    }
}
