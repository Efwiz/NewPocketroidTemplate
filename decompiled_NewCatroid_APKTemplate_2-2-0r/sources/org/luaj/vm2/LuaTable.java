package org.luaj.vm2;

import java.lang.ref.WeakReference;
import java.util.Vector;

/* JADX INFO: loaded from: classes6.dex */
public class LuaTable extends LuaValue implements Metatable {
    private static final int MIN_HASH_CAPACITY = 2;
    private static final LuaString N = valueOf("n");
    private static final Slot[] NOBUCKETS = new Slot[0];
    protected LuaValue[] array;
    protected Slot[] hash;
    protected int hashEntries;
    protected Metatable m_metatable;

    private static class DeadSlot implements Slot {
        private final Object key;
        private Slot next;

        private DeadSlot(LuaValue luaValue, Slot slot) {
            this.key = LuaTable.isLargeKey(luaValue) ? new WeakReference(luaValue) : luaValue;
            this.next = slot;
        }

        private LuaValue key() {
            Object obj = this.key;
            if (obj instanceof WeakReference) {
                obj = ((WeakReference) obj).get();
            }
            return (LuaValue) obj;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot add(Slot slot) {
            Slot slot2 = this.next;
            return slot2 != null ? slot2.add(slot) : slot;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public int arraykey(int i) {
            return -1;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public StrongSlot find(LuaValue luaValue) {
            return null;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public StrongSlot first() {
            return null;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public boolean keyeq(LuaValue luaValue) {
            LuaValue luaValueKey = key();
            return luaValueKey != null && luaValue.raweq(luaValueKey);
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public int keyindex(int i) {
            return 0;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot relink(Slot slot) {
            return slot;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot remove(StrongSlot strongSlot) {
            if (key() == null) {
                return this.next;
            }
            this.next = this.next.remove(strongSlot);
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot rest() {
            return this.next;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot set(StrongSlot strongSlot, LuaValue luaValue) {
            Slot slot = this.next;
            Slot slot2 = slot != null ? slot.set(strongSlot, luaValue) : null;
            if (key() == null) {
                return slot2;
            }
            this.next = slot2;
            return this;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer("<dead");
            LuaValue luaValueKey = key();
            if (luaValueKey != null) {
                stringBuffer.append(": ");
                stringBuffer.append(luaValueKey.toString());
            }
            stringBuffer.append('>');
            if (this.next != null) {
                stringBuffer.append("; ");
                stringBuffer.append(this.next.toString());
            }
            return stringBuffer.toString();
        }
    }

    static abstract class Entry extends Varargs implements StrongSlot {
        Entry() {
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot add(Slot slot) {
            return new LinkSlot(this, slot);
        }

        @Override // org.luaj.vm2.Varargs
        public LuaValue arg(int i) {
            if (i != 1) {
                return i != 2 ? LuaValue.NIL : value();
            }
            return key();
        }

        @Override // org.luaj.vm2.Varargs
        public LuaValue arg1() {
            return key();
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public int arraykey(int i) {
            return 0;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public StrongSlot find(LuaValue luaValue) {
            if (keyeq(luaValue)) {
                return this;
            }
            return null;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public StrongSlot first() {
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.StrongSlot
        public abstract LuaValue key();

        @Override // org.luaj.vm2.LuaTable.Slot
        public abstract boolean keyeq(LuaValue luaValue);

        @Override // org.luaj.vm2.LuaTable.Slot
        public abstract int keyindex(int i);

        @Override // org.luaj.vm2.Varargs
        public int narg() {
            return 2;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot relink(Slot slot) {
            return slot != null ? new LinkSlot(this, slot) : this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot remove(StrongSlot strongSlot) {
            return new DeadSlot(key(), null);
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot rest() {
            return null;
        }

        abstract Entry set(LuaValue luaValue);

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot set(StrongSlot strongSlot, LuaValue luaValue) {
            return set(luaValue);
        }

        @Override // org.luaj.vm2.Varargs
        public Varargs subargs(int i) {
            if (i != 1) {
                return i != 2 ? LuaValue.NONE : value();
            }
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.StrongSlot
        public Varargs toVarargs() {
            return LuaValue.varargsOf(key(), value());
        }

        @Override // org.luaj.vm2.LuaTable.StrongSlot
        public abstract LuaValue value();
    }

    private static class IntKeyEntry extends Entry {
        private final int key;
        private LuaValue value;

        IntKeyEntry(int i, LuaValue luaValue) {
            this.key = i;
            this.value = luaValue;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.Slot
        public int arraykey(int i) {
            int i2 = this.key;
            if (i2 < 1 || i2 > i) {
                return 0;
            }
            return i2;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.StrongSlot
        public LuaValue key() {
            return LuaValue.valueOf(this.key);
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.Slot
        public boolean keyeq(LuaValue luaValue) {
            return luaValue.raweq(this.key);
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.Slot
        public int keyindex(int i) {
            return LuaTable.hashmod(LuaInteger.hashCode(this.key), i);
        }

        @Override // org.luaj.vm2.LuaTable.Entry
        public Entry set(LuaValue luaValue) {
            this.value = luaValue;
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.StrongSlot
        public LuaValue value() {
            return this.value;
        }
    }

    private static class LinkSlot implements StrongSlot {
        private Entry entry;
        private Slot next;

        LinkSlot(Entry entry, Slot slot) {
            this.entry = entry;
            this.next = slot;
        }

        private Slot setnext(Slot slot) {
            if (slot == null) {
                return this.entry;
            }
            this.next = slot;
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot add(Slot slot) {
            return setnext(this.next.add(slot));
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public int arraykey(int i) {
            return this.entry.arraykey(i);
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public StrongSlot find(LuaValue luaValue) {
            if (this.entry.keyeq(luaValue)) {
                return this;
            }
            return null;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public StrongSlot first() {
            return this.entry;
        }

        @Override // org.luaj.vm2.LuaTable.StrongSlot
        public LuaValue key() {
            return this.entry.key();
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public boolean keyeq(LuaValue luaValue) {
            return this.entry.keyeq(luaValue);
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public int keyindex(int i) {
            return this.entry.keyindex(i);
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot relink(Slot slot) {
            return slot != null ? new LinkSlot(this.entry, slot) : this.entry;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot remove(StrongSlot strongSlot) {
            if (this == strongSlot) {
                return new DeadSlot(key(), this.next);
            }
            this.next = this.next.remove(strongSlot);
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot rest() {
            return this.next;
        }

        @Override // org.luaj.vm2.LuaTable.Slot
        public Slot set(StrongSlot strongSlot, LuaValue luaValue) {
            if (strongSlot != this) {
                return setnext(this.next.set(strongSlot, luaValue));
            }
            this.entry = this.entry.set(luaValue);
            return this;
        }

        public String toString() {
            return new StringBuffer().append(this.entry).append("; ").append(this.next).toString();
        }

        @Override // org.luaj.vm2.LuaTable.StrongSlot
        public Varargs toVarargs() {
            return this.entry.toVarargs();
        }

        @Override // org.luaj.vm2.LuaTable.StrongSlot
        public LuaValue value() {
            return this.entry.value();
        }
    }

    static class NormalEntry extends Entry {
        private final LuaValue key;
        private LuaValue value;

        NormalEntry(LuaValue luaValue, LuaValue luaValue2) {
            this.key = luaValue;
            this.value = luaValue2;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.StrongSlot
        public LuaValue key() {
            return this.key;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.Slot
        public boolean keyeq(LuaValue luaValue) {
            return luaValue.raweq(this.key);
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.Slot
        public int keyindex(int i) {
            return LuaTable.hashSlot(this.key, i);
        }

        @Override // org.luaj.vm2.LuaTable.Entry
        public Entry set(LuaValue luaValue) {
            this.value = luaValue;
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.StrongSlot
        public Varargs toVarargs() {
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.StrongSlot
        public LuaValue value() {
            return this.value;
        }
    }

    private static class NumberValueEntry extends Entry {
        private final LuaValue key;
        private double value;

        NumberValueEntry(LuaValue luaValue, double d) {
            this.key = luaValue;
            this.value = d;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.StrongSlot
        public LuaValue key() {
            return this.key;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.Slot
        public boolean keyeq(LuaValue luaValue) {
            return luaValue.raweq(this.key);
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.Slot
        public int keyindex(int i) {
            return LuaTable.hashSlot(this.key, i);
        }

        @Override // org.luaj.vm2.LuaTable.Entry
        public Entry set(LuaValue luaValue) {
            LuaValue luaValue2 = luaValue.tonumber();
            if (luaValue2.isnil()) {
                return new NormalEntry(this.key, luaValue);
            }
            this.value = luaValue2.todouble();
            return this;
        }

        @Override // org.luaj.vm2.LuaTable.Entry, org.luaj.vm2.LuaTable.StrongSlot
        public LuaValue value() {
            return LuaValue.valueOf(this.value);
        }
    }

    interface Slot {
        Slot add(Slot slot);

        int arraykey(int i);

        StrongSlot find(LuaValue luaValue);

        StrongSlot first();

        boolean keyeq(LuaValue luaValue);

        int keyindex(int i);

        Slot relink(Slot slot);

        Slot remove(StrongSlot strongSlot);

        Slot rest();

        Slot set(StrongSlot strongSlot, LuaValue luaValue);
    }

    interface StrongSlot extends Slot {
        LuaValue key();

        Varargs toVarargs();

        LuaValue value();
    }

    public LuaTable() {
        this.array = NOVALS;
        this.hash = NOBUCKETS;
    }

    public LuaTable(int i, int i2) {
        presize(i, i2);
    }

    public LuaTable(Varargs varargs) {
        this(varargs, 1);
    }

    public LuaTable(Varargs varargs, int i) {
        int i2 = i - 1;
        int iMax = Math.max(varargs.narg() - i2, 0);
        presize(iMax, 1);
        set(N, valueOf(iMax));
        for (int i3 = 1; i3 <= iMax; i3++) {
            set(i3, varargs.arg(i3 + i2));
        }
    }

    public LuaTable(LuaValue[] luaValueArr, LuaValue[] luaValueArr2, Varargs varargs) {
        int length = luaValueArr != null ? luaValueArr.length : 0;
        int length2 = luaValueArr2 != null ? luaValueArr2.length : 0;
        presize((varargs != null ? varargs.narg() : 0) + length2, length >> 1);
        int i = 0;
        while (i < length2) {
            int i2 = i + 1;
            rawset(i2, luaValueArr2[i]);
            i = i2;
        }
        if (varargs != null) {
            int iNarg = varargs.narg();
            for (int i3 = 1; i3 <= iNarg; i3++) {
                rawset(length2 + i3, varargs.arg(i3));
            }
        }
        for (int i4 = 0; i4 < length; i4 += 2) {
            int i5 = i4 + 1;
            if (!luaValueArr[i5].isnil()) {
                rawset(luaValueArr[i4], luaValueArr[i5]);
            }
        }
    }

    private boolean arrayset(int i, LuaValue luaValue) {
        if (i <= 0) {
            return false;
        }
        LuaValue[] luaValueArr = this.array;
        if (i > luaValueArr.length) {
            return false;
        }
        int i2 = i - 1;
        if (luaValue.isnil()) {
            luaValue = null;
        } else {
            Metatable metatable = this.m_metatable;
            if (metatable != null) {
                luaValue = metatable.wrap(luaValue);
            }
        }
        luaValueArr[i2] = luaValue;
        return true;
    }

    private boolean checkLoadFactor() {
        return this.hashEntries >= this.hash.length;
    }

    private boolean compare(int i, int i2, LuaValue luaValue) {
        LuaValue luaValueArrayget;
        LuaValue luaValueArrayget2;
        Metatable metatable = this.m_metatable;
        if (metatable == null) {
            LuaValue[] luaValueArr = this.array;
            luaValueArrayget = luaValueArr[i];
            luaValueArrayget2 = luaValueArr[i2];
        } else {
            luaValueArrayget = metatable.arrayget(this.array, i);
            luaValueArrayget2 = this.m_metatable.arrayget(this.array, i2);
        }
        if (luaValueArrayget == null || luaValueArrayget2 == null) {
            return false;
        }
        return !luaValue.isnil() ? luaValue.call(luaValueArrayget, luaValueArrayget2).toboolean() : luaValueArrayget.lt_b(luaValueArrayget2);
    }

    private int countHashKeys() {
        int i = 0;
        int i2 = 0;
        while (true) {
            Slot[] slotArr = this.hash;
            if (i >= slotArr.length) {
                return i2;
            }
            for (Slot slotRest = slotArr[i]; slotRest != null; slotRest = slotRest.rest()) {
                if (slotRest.first() != null) {
                    i2++;
                }
            }
            i++;
        }
    }

    private int countIntKeys(int[] iArr) {
        int i = 0;
        int i2 = 0;
        int i3 = 1;
        for (int i4 = 0; i4 < 31; i4++) {
            LuaValue[] luaValueArr = this.array;
            if (i3 > luaValueArr.length) {
                break;
            }
            int iMin = Math.min(luaValueArr.length, 1 << i4);
            int i5 = 0;
            while (i3 <= iMin) {
                int i6 = i3 + 1;
                if (this.array[i3 - 1] != null) {
                    i5++;
                }
                i3 = i6;
            }
            iArr[i4] = i5;
            i2 += i5;
        }
        while (true) {
            Slot[] slotArr = this.hash;
            if (i >= slotArr.length) {
                return i2;
            }
            for (Slot slotRest = slotArr[i]; slotRest != null; slotRest = slotRest.rest()) {
                int iArraykey = slotRest.arraykey(Integer.MAX_VALUE);
                if (iArraykey > 0) {
                    int iLog2 = log2(iArraykey);
                    iArr[iLog2] = iArr[iLog2] + 1;
                    i2++;
                }
            }
            i++;
        }
    }

    protected static Entry defaultEntry(LuaValue luaValue, LuaValue luaValue2) {
        if (luaValue.isinttype()) {
            return new IntKeyEntry(luaValue.toint(), luaValue2);
        }
        return luaValue2.type() == 3 ? new NumberValueEntry(luaValue, luaValue2.todouble()) : new NormalEntry(luaValue, luaValue2);
    }

    private void dropWeakArrayValues() {
        int i = 0;
        while (true) {
            LuaValue[] luaValueArr = this.array;
            if (i >= luaValueArr.length) {
                return;
            }
            this.m_metatable.arrayget(luaValueArr, i);
            i++;
        }
    }

    private void hashRemove(LuaValue luaValue) {
        if (this.hash.length > 0) {
            int iHashSlot = hashSlot(luaValue);
            for (Slot slotRest = this.hash[iHashSlot]; slotRest != null; slotRest = slotRest.rest()) {
                StrongSlot strongSlotFind = slotRest.find(luaValue);
                if (strongSlotFind != null) {
                    Slot[] slotArr = this.hash;
                    slotArr[iHashSlot] = slotArr[iHashSlot].remove(strongSlotFind);
                    this.hashEntries--;
                    return;
                }
            }
        }
    }

    private int hashSlot(LuaValue luaValue) {
        return hashSlot(luaValue, this.hash.length - 1);
    }

    public static int hashSlot(LuaValue luaValue, int i) {
        int iType = luaValue.type();
        return (iType == 2 || iType == 3 || iType == 5 || iType == 7 || iType == 8) ? hashmod(luaValue.hashCode(), i) : hashpow2(luaValue.hashCode(), i);
    }

    public static int hashmod(int i, int i2) {
        return (i & Integer.MAX_VALUE) % i2;
    }

    public static int hashpow2(int i, int i2) {
        return i & i2;
    }

    private void heapSort(int i, LuaValue luaValue) {
        heapify(i, luaValue);
        int i2 = i - 1;
        while (i2 > 0) {
            swap(i2, 0);
            i2--;
            siftDown(0, i2, luaValue);
        }
    }

    private void heapify(int i, LuaValue luaValue) {
        for (int i2 = (i / 2) - 1; i2 >= 0; i2--) {
            siftDown(i2, i - 1, luaValue);
        }
    }

    protected static boolean isLargeKey(LuaValue luaValue) {
        int iType = luaValue.type();
        if (iType == 1 || iType == 3) {
            return false;
        }
        return iType != 4 || luaValue.rawlen() > 32;
    }

    static int log2(int i) {
        int i2;
        int i3 = i - 1;
        if (i3 < 0) {
            return Integer.MIN_VALUE;
        }
        if (((-65536) & i3) != 0) {
            i3 >>>= 16;
            i2 = 16;
        } else {
            i2 = 0;
        }
        if ((65280 & i3) != 0) {
            i2 += 8;
            i3 >>>= 8;
        }
        if ((i3 & 240) != 0) {
            i2 += 4;
            i3 >>>= 4;
        }
        switch (i3) {
            case 0:
                return 0;
            case 1:
                return i2 + 1;
            case 2:
            case 3:
                return i2 + 2;
            case 4:
            case 5:
            case 6:
            case 7:
                return i2 + 3;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return i2 + 4;
            default:
                return i2;
        }
    }

    private void rehash(int i) {
        LuaValue[] luaValueArr;
        Slot[] slotArr;
        int i2;
        Slot slotDefaultEntry;
        Metatable metatable = this.m_metatable;
        if (metatable != null && (metatable.useWeakKeys() || this.m_metatable.useWeakValues())) {
            this.hashEntries = countHashKeys();
            if (this.m_metatable.useWeakValues()) {
                dropWeakArrayValues();
            }
        }
        int[] iArr = new int[32];
        int iCountIntKeys = countIntKeys(iArr);
        if (i > 0) {
            iCountIntKeys++;
            int iLog2 = log2(i);
            iArr[iLog2] = iArr[iLog2] + 1;
        }
        int i3 = iArr[0];
        int i4 = 1;
        int i5 = 0;
        while (true) {
            if (i4 >= 32) {
                break;
            }
            i3 += iArr[i4];
            int i6 = 1 << i4;
            if (iCountIntKeys * 2 < i6) {
                break;
            }
            if (i3 >= (1 << (i4 - 1))) {
                i5 = i6;
            }
            i4++;
        }
        LuaValue[] luaValueArr2 = this.array;
        Slot[] slotArr2 = this.hash;
        int i7 = (i <= 0 || i > i5) ? 0 : -1;
        if (i5 != luaValueArr2.length) {
            luaValueArr = new LuaValue[i5];
            if (i5 > luaValueArr2.length) {
                int iLog3 = log2(i5) + 1;
                for (int iLog4 = log2(luaValueArr2.length + 1); iLog4 < iLog3; iLog4++) {
                    i7 += iArr[iLog4];
                }
            } else if (luaValueArr2.length > i5) {
                int iLog5 = log2(luaValueArr2.length) + 1;
                for (int iLog6 = log2(i5 + 1); iLog6 < iLog5; iLog6++) {
                    i7 -= iArr[iLog6];
                }
            }
            System.arraycopy(luaValueArr2, 0, luaValueArr, 0, Math.min(luaValueArr2.length, i5));
        } else {
            luaValueArr = luaValueArr2;
        }
        int i8 = (this.hashEntries - i7) + ((i < 0 || i > i5) ? 1 : 0);
        if (i8 > 0) {
            int iLog7 = i8 >= 2 ? 1 << log2(i8) : 2;
            i2 = iLog7 - 1;
            slotArr = new Slot[iLog7];
        } else {
            slotArr = NOBUCKETS;
            i2 = 0;
        }
        for (Slot slotRest : slotArr2) {
            for (; slotRest != null; slotRest = slotRest.rest()) {
                int iArraykey = slotRest.arraykey(i5);
                if (iArraykey > 0) {
                    StrongSlot strongSlotFirst = slotRest.first();
                    if (strongSlotFirst != null) {
                        luaValueArr[iArraykey - 1] = strongSlotFirst.value();
                    }
                } else {
                    int iKeyindex = slotRest.keyindex(i2);
                    slotArr[iKeyindex] = slotRest.relink(slotArr[iKeyindex]);
                }
            }
        }
        while (i5 < luaValueArr2.length) {
            int i9 = i5 + 1;
            LuaValue luaValue = luaValueArr2[i5];
            if (luaValue != null) {
                int iHashmod = hashmod(LuaInteger.hashCode(i9), i2);
                Metatable metatable2 = this.m_metatable;
                if (metatable2 != null) {
                    slotDefaultEntry = metatable2.entry(valueOf(i9), luaValue);
                    if (slotDefaultEntry == null) {
                    }
                } else {
                    slotDefaultEntry = defaultEntry(valueOf(i9), luaValue);
                }
                Slot slot = slotArr[iHashmod];
                if (slot != null) {
                    slotDefaultEntry = slot.add(slotDefaultEntry);
                }
                slotArr[iHashmod] = slotDefaultEntry;
            }
            i5 = i9;
        }
        this.hash = slotArr;
        this.array = luaValueArr;
        this.hashEntries -= i7;
    }

    private static LuaValue[] resize(LuaValue[] luaValueArr, int i) {
        LuaValue[] luaValueArr2 = new LuaValue[i];
        System.arraycopy(luaValueArr, 0, luaValueArr2, 0, luaValueArr.length);
        return luaValueArr2;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0011  */
    private void siftDown(int i, int i2, LuaValue luaValue) {
        int i3;
        while (true) {
            int i4 = i * 2;
            int i5 = i4 + 1;
            if (i5 > i2) {
                return;
            }
            if (i5 < i2) {
                i3 = i4 + 2;
                if (!compare(i5, i3, luaValue)) {
                    i3 = i5;
                }
            } else {
                i3 = i5;
            }
            if (!compare(i, i3, luaValue)) {
                return;
            }
            swap(i, i3);
            i = i3;
        }
    }

    private void swap(int i, int i2) {
        LuaValue[] luaValueArr = this.array;
        LuaValue luaValue = luaValueArr[i];
        luaValueArr[i] = luaValueArr[i2];
        luaValueArr[i2] = luaValue;
    }

    @Override // org.luaj.vm2.Metatable
    public LuaValue arrayget(LuaValue[] luaValueArr, int i) {
        return luaValueArr[i];
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaTable checktable() {
        return this;
    }

    public LuaValue concat(LuaString luaString, int i, int i2) {
        Buffer buffer = new Buffer();
        if (i <= i2) {
            while (true) {
                buffer.append(get(i).checkstring());
                i++;
                if (i > i2) {
                    break;
                }
                buffer.append(luaString);
            }
        }
        return buffer.tostring();
    }

    @Override // org.luaj.vm2.Metatable
    public Slot entry(LuaValue luaValue, LuaValue luaValue2) {
        return defaultEntry(luaValue, luaValue2);
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue eq(LuaValue luaValue) {
        return eq_b(luaValue) ? TRUE : FALSE;
    }

    @Override // org.luaj.vm2.LuaValue
    public boolean eq_b(LuaValue luaValue) {
        if (this == luaValue) {
            return true;
        }
        if (this.m_metatable == null || !luaValue.istable()) {
            return false;
        }
        LuaValue luaValue2 = luaValue.getmetatable();
        return luaValue2 != null && LuaValue.eqmtcall(this, this.m_metatable.toLuaValue(), luaValue, luaValue2);
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue get(int i) {
        LuaValue luaValueRawget = rawget(i);
        return (!luaValueRawget.isnil() || this.m_metatable == null) ? luaValueRawget : gettable(this, valueOf(i));
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue get(LuaValue luaValue) {
        LuaValue luaValueRawget = rawget(luaValue);
        return (!luaValueRawget.isnil() || this.m_metatable == null) ? luaValueRawget : gettable(this, luaValue);
    }

    protected int getArrayLength() {
        return this.array.length;
    }

    protected int getHashLength() {
        return this.hash.length;
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue getmetatable() {
        Metatable metatable = this.m_metatable;
        if (metatable != null) {
            return metatable.toLuaValue();
        }
        return null;
    }

    protected LuaValue hashget(LuaValue luaValue) {
        if (this.hashEntries > 0) {
            for (Slot slotRest = this.hash[hashSlot(luaValue)]; slotRest != null; slotRest = slotRest.rest()) {
                StrongSlot strongSlotFind = slotRest.find(luaValue);
                if (strongSlotFind != null) {
                    return strongSlotFind.value();
                }
            }
        }
        return NIL;
    }

    public void hashset(LuaValue luaValue, LuaValue luaValue2) {
        int iHashSlot;
        if (luaValue2.isnil()) {
            hashRemove(luaValue);
            return;
        }
        if (this.hash.length > 0) {
            iHashSlot = hashSlot(luaValue);
            for (Slot slotRest = this.hash[iHashSlot]; slotRest != null; slotRest = slotRest.rest()) {
                StrongSlot strongSlotFind = slotRest.find(luaValue);
                if (strongSlotFind != null) {
                    Slot[] slotArr = this.hash;
                    slotArr[iHashSlot] = slotArr[iHashSlot].set(strongSlotFind, luaValue2);
                    return;
                }
            }
        } else {
            iHashSlot = 0;
        }
        if (checkLoadFactor()) {
            if (!luaValue.isinttype() || luaValue.toint() <= 0) {
                rehash(-1);
            } else {
                rehash(luaValue.toint());
                if (arrayset(luaValue.toint(), luaValue2)) {
                    return;
                }
            }
            iHashSlot = hashSlot(luaValue);
        }
        Metatable metatable = this.m_metatable;
        Slot slotEntry = metatable != null ? metatable.entry(luaValue, luaValue2) : defaultEntry(luaValue, luaValue2);
        Slot[] slotArr2 = this.hash;
        Slot slot = slotArr2[iHashSlot];
        if (slot != null) {
            slotEntry = slot.add(slotEntry);
        }
        slotArr2[iHashSlot] = slotEntry;
        this.hashEntries++;
    }

    @Override // org.luaj.vm2.LuaValue
    public Varargs inext(LuaValue luaValue) {
        int iCheckint = luaValue.checkint() + 1;
        LuaValue luaValueRawget = rawget(iCheckint);
        return luaValueRawget.isnil() ? NONE : varargsOf(LuaInteger.valueOf(iCheckint), luaValueRawget);
    }

    public void insert(int i, LuaValue luaValue) {
        if (i == 0) {
            i = rawlen() + 1;
        }
        while (!luaValue.isnil()) {
            LuaValue luaValueRawget = rawget(i);
            rawset(i, luaValue);
            luaValue = luaValueRawget;
            i++;
        }
    }

    @Override // org.luaj.vm2.LuaValue
    public boolean istable() {
        return true;
    }

    public int keyCount() {
        LuaValue luaValueArg1 = LuaValue.NIL;
        int i = 0;
        while (true) {
            luaValueArg1 = next(luaValueArg1).arg1();
            if (luaValueArg1.isnil()) {
                return i;
            }
            i++;
        }
    }

    public LuaValue[] keys() {
        Vector vector = new Vector();
        LuaValue luaValueArg1 = LuaValue.NIL;
        while (true) {
            luaValueArg1 = next(luaValueArg1).arg1();
            if (luaValueArg1.isnil()) {
                LuaValue[] luaValueArr = new LuaValue[vector.size()];
                vector.copyInto(luaValueArr);
                return luaValueArr;
            }
            vector.addElement(luaValueArg1);
        }
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue len() {
        LuaValue luaValueMetatag = metatag(LEN);
        return luaValueMetatag.toboolean() ? luaValueMetatag.call(this) : LuaInteger.valueOf(rawlen());
    }

    @Override // org.luaj.vm2.LuaValue
    public int length() {
        return this.m_metatable != null ? len().toint() : rawlen();
    }

    @Override // org.luaj.vm2.LuaValue
    public Varargs next(LuaValue luaValue) {
        int i;
        int length = 0;
        if (!luaValue.isnil()) {
            if (!luaValue.isinttype() || (i = luaValue.toint()) <= 0 || i > this.array.length) {
                if (this.hash.length == 0) {
                    error("invalid key to 'next'");
                }
                int iHashSlot = hashSlot(luaValue);
                for (Slot slotRest = this.hash[iHashSlot]; slotRest != null; slotRest = slotRest.rest()) {
                    if (length != 0) {
                        StrongSlot strongSlotFirst = slotRest.first();
                        if (strongSlotFirst != null) {
                            return strongSlotFirst.toVarargs();
                        }
                    } else if (slotRest.keyeq(luaValue)) {
                        length = 1;
                    }
                }
                if (length == 0) {
                    error("invalid key to 'next'");
                }
                length = iHashSlot + this.array.length + 1;
            } else {
                length = i;
            }
        }
        while (true) {
            LuaValue[] luaValueArr = this.array;
            if (length < luaValueArr.length) {
                LuaValue luaValueArrayget = luaValueArr[length];
                if (luaValueArrayget != null) {
                    Metatable metatable = this.m_metatable;
                    if (metatable != null) {
                        luaValueArrayget = metatable.arrayget(luaValueArr, length);
                    }
                    if (luaValueArrayget != null) {
                        return varargsOf(LuaInteger.valueOf(length + 1), luaValueArrayget);
                    }
                }
                length++;
            } else {
                int length2 = length - luaValueArr.length;
                while (true) {
                    Slot[] slotArr = this.hash;
                    if (length2 >= slotArr.length) {
                        return NIL;
                    }
                    for (Slot slotRest2 = slotArr[length2]; slotRest2 != null; slotRest2 = slotRest2.rest()) {
                        StrongSlot strongSlotFirst2 = slotRest2.first();
                        if (strongSlotFirst2 != null) {
                            return strongSlotFirst2.toVarargs();
                        }
                    }
                    length2++;
                }
            }
        }
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaTable opttable(LuaTable luaTable) {
        return this;
    }

    @Override // org.luaj.vm2.LuaValue
    public void presize(int i) {
        LuaValue[] luaValueArr = this.array;
        if (i > luaValueArr.length) {
            this.array = resize(luaValueArr, 1 << log2(i));
        }
    }

    public void presize(int i, int i2) {
        if (i2 > 0 && i2 < 2) {
            i2 = 2;
        }
        this.array = i > 0 ? new LuaValue[1 << log2(i)] : NOVALS;
        this.hash = i2 > 0 ? new Slot[1 << log2(i2)] : NOBUCKETS;
        this.hashEntries = 0;
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue rawget(int i) {
        if (i > 0) {
            LuaValue[] luaValueArr = this.array;
            if (i <= luaValueArr.length) {
                Metatable metatable = this.m_metatable;
                int i2 = i - 1;
                LuaValue luaValueArrayget = metatable == null ? luaValueArr[i2] : metatable.arrayget(luaValueArr, i2);
                return luaValueArrayget != null ? luaValueArrayget : NIL;
            }
        }
        return hashget(LuaInteger.valueOf(i));
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue rawget(LuaValue luaValue) {
        int i;
        if (luaValue.isinttype() && (i = luaValue.toint()) > 0) {
            LuaValue[] luaValueArr = this.array;
            if (i <= luaValueArr.length) {
                Metatable metatable = this.m_metatable;
                int i2 = i - 1;
                LuaValue luaValueArrayget = metatable == null ? luaValueArr[i2] : metatable.arrayget(luaValueArr, i2);
                return luaValueArrayget != null ? luaValueArrayget : NIL;
            }
        }
        return hashget(luaValue);
    }

    @Override // org.luaj.vm2.LuaValue
    public int rawlen() {
        int arrayLength = getArrayLength();
        int hashLength = arrayLength + 1;
        int i = 0;
        while (!rawget(hashLength).isnil()) {
            i = hashLength;
            hashLength = getHashLength() + arrayLength + 1 + hashLength;
        }
        while (hashLength > i + 1) {
            int i2 = (hashLength + i) / 2;
            if (rawget(i2).isnil()) {
                hashLength = i2;
            } else {
                i = i2;
            }
        }
        return i;
    }

    @Override // org.luaj.vm2.LuaValue
    public void rawset(int i, LuaValue luaValue) {
        if (arrayset(i, luaValue)) {
            return;
        }
        hashset(LuaInteger.valueOf(i), luaValue);
    }

    @Override // org.luaj.vm2.LuaValue
    public void rawset(LuaValue luaValue, LuaValue luaValue2) {
        if (luaValue.isinttype() && arrayset(luaValue.toint(), luaValue2)) {
            return;
        }
        hashset(luaValue, luaValue2);
    }

    public LuaValue remove(int i) {
        int iRawlen = rawlen();
        if (i == 0) {
            i = iRawlen;
        } else if (i > iRawlen) {
            return NONE;
        }
        LuaValue luaValueRawget = rawget(i);
        LuaValue luaValue = luaValueRawget;
        while (!luaValue.isnil()) {
            int i2 = i + 1;
            LuaValue luaValueRawget2 = rawget(i2);
            rawset(i, luaValueRawget2);
            i = i2;
            luaValue = luaValueRawget2;
        }
        return luaValueRawget.isnil() ? NONE : luaValueRawget;
    }

    @Override // org.luaj.vm2.LuaValue
    public void set(int i, LuaValue luaValue) {
        if (this.m_metatable != null && rawget(i).isnil() && settable(this, LuaInteger.valueOf(i), luaValue)) {
            return;
        }
        rawset(i, luaValue);
    }

    @Override // org.luaj.vm2.LuaValue
    public void set(LuaValue luaValue, LuaValue luaValue2) {
        if (!luaValue.isvalidkey() && !metatag(NEWINDEX).isfunction()) {
            typerror("table index");
        }
        if (this.m_metatable != null && rawget(luaValue).isnil() && settable(this, luaValue, luaValue2)) {
            return;
        }
        rawset(luaValue, luaValue2);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x003d  */
    @Override // org.luaj.vm2.LuaValue
    public LuaValue setmetatable(LuaValue luaValue) {
        Metatable metatable = this.m_metatable;
        boolean z = metatable != null && metatable.useWeakKeys();
        Metatable metatable2 = this.m_metatable;
        boolean z2 = metatable2 != null && metatable2.useWeakValues();
        Metatable metatableMetatableOf = metatableOf(luaValue);
        this.m_metatable = metatableMetatableOf;
        if (z != (metatableMetatableOf != null && metatableMetatableOf.useWeakKeys())) {
            rehash(0);
        } else {
            Metatable metatable3 = this.m_metatable;
            if (z2 != (metatable3 != null && metatable3.useWeakValues())) {
                rehash(0);
            }
        }
        return this;
    }

    public void sort(LuaValue luaValue) {
        Metatable metatable = this.m_metatable;
        if (metatable != null && metatable.useWeakValues()) {
            dropWeakArrayValues();
        }
        int length = this.array.length;
        while (length > 0 && this.array[length - 1] == null) {
            length--;
        }
        if (length > 1) {
            heapSort(length, luaValue);
        }
    }

    @Override // org.luaj.vm2.Metatable
    public LuaValue toLuaValue() {
        return this;
    }

    @Override // org.luaj.vm2.LuaValue
    public int type() {
        return 5;
    }

    @Override // org.luaj.vm2.LuaValue
    public String typename() {
        return "table";
    }

    public Varargs unpack() {
        return unpack(1, rawlen());
    }

    public Varargs unpack(int i) {
        return unpack(i, rawlen());
    }

    public Varargs unpack(int i, int i2) {
        int i3 = (i2 + 1) - i;
        if (i3 == 0) {
            return NONE;
        }
        if (i3 == 1) {
            return get(i);
        }
        if (i3 == 2) {
            return varargsOf(get(i), get(i + 1));
        }
        if (i3 < 0) {
            return NONE;
        }
        LuaValue[] luaValueArr = new LuaValue[i3];
        while (true) {
            i3--;
            if (i3 < 0) {
                return varargsOf(luaValueArr);
            }
            luaValueArr[i3] = get(i + i3);
        }
    }

    @Override // org.luaj.vm2.Metatable
    public boolean useWeakKeys() {
        return false;
    }

    @Override // org.luaj.vm2.Metatable
    public boolean useWeakValues() {
        return false;
    }

    @Override // org.luaj.vm2.Metatable
    public LuaValue wrap(LuaValue luaValue) {
        return luaValue;
    }
}
