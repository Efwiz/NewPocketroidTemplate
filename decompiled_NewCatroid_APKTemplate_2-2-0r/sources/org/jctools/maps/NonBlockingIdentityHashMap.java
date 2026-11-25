package org.jctools.maps;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.google.android.gms.internal.cast.zzrj$$ExternalSyntheticBackportWithForwarding0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.jctools.util.RangeUtil;
import org.jctools.util.UnsafeAccess;

/* JADX INFO: loaded from: classes14.dex */
public class NonBlockingIdentityHashMap<TypeK, TypeV> extends AbstractMap<TypeK, TypeV> implements ConcurrentMap<TypeK, TypeV>, Cloneable, Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MIN_SIZE = 8;
    private static final int MIN_SIZE_LOG = 3;
    private static final int REPROBE_LIMIT = 10;
    private static final Prime TOMBPRIME;
    private static final Object TOMBSTONE;
    private static final long serialVersionUID = 1234123412341234123L;
    private transient Object[] _kvs;
    private transient long _last_resize_milli;
    private transient ConcurrentAutoTable _reprobes;
    private static final int _Obase = UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class);
    private static final int _Oscale = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
    private static final long _kvs_offset = UnsafeAccess.fieldOffset(NonBlockingHashMap.class, "_kvs");
    private static final Object NO_MATCH_OLD = new Object();
    private static final Object MATCH_ANY = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    public static final int reprobe_limit(int i) {
        return (i >> 2) + 10;
    }

    protected void rehash() {
    }

    static {
        Object obj = new Object();
        TOMBSTONE = obj;
        TOMBPRIME = new Prime(obj);
    }

    private static long rawIndex(Object[] objArr, int i) {
        return _Obase + (i * _Oscale);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean CAS_kvs(Object[] objArr, Object[] objArr2) {
        return zzrj$$ExternalSyntheticBackportWithForwarding0.m(UnsafeAccess.UNSAFE, this, _kvs_offset, objArr, objArr2);
    }

    private static final class Prime {
        final Object _V;

        Prime(Object obj) {
            this._V = obj;
        }

        static Object unbox(Object obj) {
            return obj instanceof Prime ? ((Prime) obj)._V : obj;
        }
    }

    private static final int hash(Object obj) {
        return System.identityHashCode(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CHM chm(Object[] objArr) {
        return (CHM) objArr[0];
    }

    private static final int[] hashes(Object[] objArr) {
        return (int[]) objArr[1];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int len(Object[] objArr) {
        return (objArr.length - 2) >> 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object key(Object[] objArr, int i) {
        return objArr[(i << 1) + 2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object val(Object[] objArr, int i) {
        return objArr[(i << 1) + 3];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean CAS_key(Object[] objArr, int i, Object obj, Object obj2) {
        return zzrj$$ExternalSyntheticBackportWithForwarding0.m(UnsafeAccess.UNSAFE, objArr, rawIndex(objArr, (i << 1) + 2), obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean CAS_val(Object[] objArr, int i, Object obj, Object obj2) {
        return zzrj$$ExternalSyntheticBackportWithForwarding0.m(UnsafeAccess.UNSAFE, objArr, rawIndex(objArr, (i << 1) + 3), obj, obj2);
    }

    public final void print() {
        System.out.println("=========");
        print2(this._kvs);
        System.out.println("=========");
    }

    private final void print(Object[] objArr) {
        for (int i = 0; i < len(objArr); i++) {
            Object objKey = key(objArr, i);
            if (objKey != null) {
                Object obj = TOMBSTONE;
                String string = objKey == obj ? "XXX" : objKey.toString();
                Object objVal = val(objArr, i);
                Object objUnbox = Prime.unbox(objVal);
                System.out.println("" + i + " (" + string + "," + (objVal == objUnbox ? "" : "prime_") + (objUnbox == obj ? "tombstone" : objUnbox.toString()) + Constant.AFTER_QUTO);
            }
        }
        Object[] objArr2 = chm(objArr)._newkvs;
        if (objArr2 != null) {
            System.out.println("----");
            print(objArr2);
        }
    }

    private final void print2(Object[] objArr) {
        Object obj;
        for (int i = 0; i < len(objArr); i++) {
            Object objKey = key(objArr, i);
            Object objVal = val(objArr, i);
            Object objUnbox = Prime.unbox(objVal);
            if (objKey != null && objKey != (obj = TOMBSTONE) && objVal != null && objUnbox != obj) {
                System.out.println("" + i + " (" + objKey + "," + (objVal == objUnbox ? "" : "prime_") + objVal + Constant.AFTER_QUTO);
            }
        }
        Object[] objArr2 = chm(objArr)._newkvs;
        if (objArr2 != null) {
            System.out.println("----");
            print2(objArr2);
        }
    }

    public long reprobes() {
        long j = this._reprobes.get();
        this._reprobes = new ConcurrentAutoTable();
        return j;
    }

    public NonBlockingIdentityHashMap() {
        this(8);
    }

    public NonBlockingIdentityHashMap(int i) {
        this._reprobes = new ConcurrentAutoTable();
        initialize(i);
    }

    private final void initialize(int i) {
        RangeUtil.checkPositiveOrZero(i, "initial_sz");
        if (i > 1048576) {
            i = 1048576;
        }
        int i2 = 3;
        while (true) {
            int i3 = 1 << i2;
            if (i3 >= (i << 2)) {
                Object[] objArr = new Object[(i3 << 1) + 2];
                this._kvs = objArr;
                objArr[0] = new CHM(new ConcurrentAutoTable());
                this._kvs[1] = new int[i3];
                this._last_resize_milli = System.currentTimeMillis();
                return;
            }
            i2++;
        }
    }

    protected final void initialize() {
        initialize(8);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return chm(this._kvs).size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    public boolean contains(Object obj) {
        return containsValue(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public TypeV put(TypeK typek, TypeV typev) {
        return putIfMatch(typek, typev, NO_MATCH_OLD);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public TypeV putIfAbsent(TypeK typek, TypeV typev) {
        return putIfMatch(typek, typev, TOMBSTONE);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public TypeV remove(Object obj) {
        return putIfMatch(obj, TOMBSTONE, NO_MATCH_OLD);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean remove(Object obj, Object obj2) {
        return putIfMatch(obj, TOMBSTONE, obj2) == obj2;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public TypeV replace(TypeK typek, TypeV typev) {
        return putIfMatch(typek, typev, MATCH_ANY);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean replace(TypeK typek, TypeV typev, TypeV typev2) {
        return putIfMatch(typek, typev2, typev) == typev;
    }

    private final TypeV putIfMatch(Object obj, Object obj2, Object obj3) {
        if (obj3 == null || obj2 == null) {
            throw null;
        }
        TypeV typev = (TypeV) putIfMatch(this, this._kvs, obj, obj2, obj3);
        if (typev == TOMBSTONE) {
            return null;
        }
        return typev;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends TypeK, ? extends TypeV> map) {
        for (Map.Entry<? extends TypeK, ? extends TypeV> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        while (!CAS_kvs(this._kvs, new NonBlockingIdentityHashMap(8)._kvs)) {
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object obj) {
        obj.getClass();
        for (TypeV typev : values()) {
            if (typev == obj || typev.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap
    public Object clone() {
        try {
            NonBlockingIdentityHashMap nonBlockingIdentityHashMap = (NonBlockingIdentityHashMap) super.clone();
            nonBlockingIdentityHashMap.clear();
            for (TypeK typek : keySet()) {
                nonBlockingIdentityHashMap.put(typek, get(typek));
            }
            return nonBlockingIdentityHashMap;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.AbstractMap
    public String toString() {
        Iterator<Map.Entry<TypeK, TypeV>> it = entrySet().iterator();
        if (!it.hasNext()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        while (true) {
            Map.Entry<TypeK, TypeV> next = it.next();
            Object key = next.getKey();
            Object value = next.getValue();
            if (key == this) {
                key = "(this Map)";
            }
            sb.append(key);
            sb.append('=');
            if (value == this) {
                value = "(this Map)";
            }
            sb.append(value);
            if (!it.hasNext()) {
                return sb.append('}').toString();
            }
            sb.append(", ");
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public TypeV get(Object obj) {
        return (TypeV) get_impl(this, this._kvs, obj);
    }

    private static final Object get_impl(NonBlockingIdentityHashMap nonBlockingIdentityHashMap, Object[] objArr, Object obj) {
        Object[] objArr2;
        int iHash = hash(obj);
        int iLen = len(objArr);
        CHM chm = chm(objArr);
        int i = iLen - 1;
        int i2 = iHash & i;
        int i3 = 0;
        while (true) {
            Object objKey = key(objArr, i2);
            Object objVal = val(objArr, i2);
            if (objKey == null) {
                return null;
            }
            objArr2 = chm._newkvs;
            if (objKey == obj) {
                if (!(objVal instanceof Prime)) {
                    if (objVal == TOMBSTONE) {
                        return null;
                    }
                    return objVal;
                }
                return get_impl(nonBlockingIdentityHashMap, chm.copy_slot_and_check(nonBlockingIdentityHashMap, objArr, i2, obj), obj);
            }
            i3++;
            if (i3 >= reprobe_limit(iLen) || obj == TOMBSTONE) {
                break;
            }
            i2 = (i2 + 1) & i;
        }
        if (objArr2 == null) {
            return null;
        }
        return get_impl(nonBlockingIdentityHashMap, nonBlockingIdentityHashMap.help_copy(objArr2), obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:63:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:71:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:78:0x0039 A[EDGE_INSN: B:78:0x0039->B:14:0x0039 BREAK  A[LOOP:0: B:3:0x0012->B:68:0x00c8], SYNTHETIC] */
    public static final Object putIfMatch(NonBlockingIdentityHashMap nonBlockingIdentityHashMap, Object[] objArr, Object obj, Object obj2, Object obj3) {
        Object objVal;
        Object[] objArrResize;
        Object obj4;
        int iHash = hash(obj);
        int iLen = len(objArr);
        CHM chm = chm(objArr);
        int i = iLen - 1;
        int i2 = iHash & i;
        int i3 = 0;
        Object[] objArrResize2 = null;
        while (true) {
            objVal = val(objArr, i2);
            Object objKey = key(objArr, i2);
            if (objKey == null) {
                if (obj2 == TOMBSTONE) {
                    return obj2;
                }
                if (!CAS_key(objArr, i2, null, obj)) {
                    objKey = key(objArr, i2);
                    objArrResize2 = chm._newkvs;
                    if (objKey == obj) {
                        break;
                        break;
                    }
                    i3++;
                    if (i3 < reprobe_limit(iLen)) {
                    }
                    objArrResize = chm.resize(nonBlockingIdentityHashMap, objArr);
                    if (obj3 != null) {
                        nonBlockingIdentityHashMap.help_copy(objArrResize);
                    }
                    return putIfMatch(nonBlockingIdentityHashMap, objArrResize, obj, obj2, obj3);
                }
                chm._slots.add(1L);
                break;
            }
            objArrResize2 = chm._newkvs;
            if (objKey == obj) {
                break;
            }
            i3++;
            if (i3 < reprobe_limit(iLen) || obj == TOMBSTONE) {
                objArrResize = chm.resize(nonBlockingIdentityHashMap, objArr);
                if (obj3 != null) {
                    nonBlockingIdentityHashMap.help_copy(objArrResize);
                }
                return putIfMatch(nonBlockingIdentityHashMap, objArrResize, obj, obj2, obj3);
            }
            i2 = (i2 + 1) & i;
        }
        if (obj2 == objVal) {
            return objVal;
        }
        if (objArrResize2 == null && ((objVal == null && chm.tableFull(i3, iLen)) || (objVal instanceof Prime))) {
            objArrResize2 = chm.resize(nonBlockingIdentityHashMap, objArr);
        }
        if (objArrResize2 == null) {
            if (obj3 != NO_MATCH_OLD && objVal != obj3 && ((obj3 != MATCH_ANY || objVal == TOMBSTONE || objVal == null) && ((objVal != null || obj3 != TOMBSTONE) && (obj3 == null || !obj3.equals(objVal))))) {
                return objVal;
            }
            if (!CAS_val(objArr, i2, objVal, obj2)) {
                objVal = val(objArr, i2);
                if (objVal instanceof Prime) {
                    return putIfMatch(nonBlockingIdentityHashMap, chm.copy_slot_and_check(nonBlockingIdentityHashMap, objArr, i2, obj3), obj, obj2, obj3);
                }
            } else if (obj3 != null) {
                if ((objVal == null || objVal == TOMBSTONE) && obj2 != TOMBSTONE) {
                    chm._size.add(1L);
                }
                if (objVal != null && objVal != (obj4 = TOMBSTONE) && obj2 == obj4) {
                    chm._size.add(-1L);
                }
            }
            return (objVal != null || obj3 == null) ? objVal : TOMBSTONE;
        }
        return putIfMatch(nonBlockingIdentityHashMap, chm.copy_slot_and_check(nonBlockingIdentityHashMap, objArr, i2, obj3), obj, obj2, obj3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object[] help_copy(Object[] objArr) {
        Object[] objArr2 = this._kvs;
        CHM chm = chm(objArr2);
        if (chm._newkvs == null) {
            return objArr;
        }
        chm.help_copy_impl(this, objArr2, false);
        return objArr;
    }

    private static final class CHM<TypeK, TypeV> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        volatile Object[] _newkvs;
        volatile long _resizers;
        private final ConcurrentAutoTable _size;
        private static final AtomicLongFieldUpdater<CHM> _resizerUpdater = AtomicLongFieldUpdater.newUpdater(CHM.class, "_resizers");
        private static final AtomicLongFieldUpdater<CHM> _copyIdxUpdater = AtomicLongFieldUpdater.newUpdater(CHM.class, "_copyIdx");
        private static final AtomicLongFieldUpdater<CHM> _copyDoneUpdater = AtomicLongFieldUpdater.newUpdater(CHM.class, "_copyDone");
        private final AtomicReferenceFieldUpdater<CHM, Object[]> _newkvsUpdater = AtomicReferenceFieldUpdater.newUpdater(CHM.class, Object[].class, "_newkvs");
        volatile long _copyIdx = 0;
        volatile long _copyDone = 0;
        private final ConcurrentAutoTable _slots = new ConcurrentAutoTable();

        public int size() {
            return (int) this._size.get();
        }

        public int slots() {
            return (int) this._slots.get();
        }

        boolean CAS_newkvs(Object[] objArr) {
            while (this._newkvs == null) {
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(this._newkvsUpdater, this, null, objArr)) {
                    return true;
                }
            }
            return false;
        }

        CHM(ConcurrentAutoTable concurrentAutoTable) {
            this._size = concurrentAutoTable;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean tableFull(int i, int i2) {
            return i >= 10 && this._slots.estimate_get() >= ((long) NonBlockingIdentityHashMap.reprobe_limit(i2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object[] resize(NonBlockingIdentityHashMap nonBlockingIdentityHashMap, Object[] objArr) {
            int i;
            int i2;
            Object[] objArr2 = this._newkvs;
            if (objArr2 != null) {
                return objArr2;
            }
            int iLen = NonBlockingIdentityHashMap.len(objArr);
            int size = size();
            if (size >= (iLen >> 2)) {
                i = iLen << 1;
                if (size >= (iLen >> 1)) {
                    i = iLen << 2;
                }
            } else {
                i = size;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (i <= iLen && jCurrentTimeMillis <= nonBlockingIdentityHashMap._last_resize_milli + 10000 && this._slots.estimate_get() >= (size << 1)) {
                i = iLen << 1;
            }
            if (i >= iLen) {
                iLen = i;
            }
            int i3 = 3;
            while (true) {
                i2 = 1 << i3;
                if (i2 >= iLen) {
                    break;
                }
                i3++;
            }
            long j = this._resizers;
            while (!_resizerUpdater.compareAndSet(this, j, j + 1)) {
                j = this._resizers;
            }
            int i4 = i2 << 1;
            int i5 = ((i4 + 4) << 3) >> 20;
            if (j >= 2 && i5 > 0) {
                Object[] objArr3 = this._newkvs;
                if (objArr3 != null) {
                    return objArr3;
                }
                try {
                    Thread.sleep(i5 * 8);
                } catch (Exception unused) {
                }
            }
            Object[] objArr4 = this._newkvs;
            if (objArr4 != null) {
                return objArr4;
            }
            Object[] objArr5 = new Object[i4 + 2];
            objArr5[0] = new CHM(this._size);
            objArr5[1] = new int[i2];
            if (this._newkvs != null) {
                return this._newkvs;
            }
            if (CAS_newkvs(objArr5)) {
                nonBlockingIdentityHashMap.rehash();
                return objArr5;
            }
            return this._newkvs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void help_copy_impl(NonBlockingIdentityHashMap nonBlockingIdentityHashMap, Object[] objArr, boolean z) {
            int i;
            int i2;
            Object[] objArr2 = this._newkvs;
            int iLen = NonBlockingIdentityHashMap.len(objArr);
            int iMin = Math.min(iLen, 1024);
            int i3 = -1;
            int i4 = -9999;
            int i5 = -1;
            while (this._copyDone < iLen) {
                if (i5 == i3) {
                    int i6 = (int) this._copyIdx;
                    while (true) {
                        i = iLen << 1;
                        if (i6 >= i) {
                            i2 = i5;
                            break;
                        }
                        i2 = i5;
                        if (_copyIdxUpdater.compareAndSet(this, i6, i6 + iMin)) {
                            break;
                        }
                        i6 = (int) this._copyIdx;
                        i5 = i2;
                    }
                    i4 = i6;
                    i5 = i6 >= i ? i4 : i2;
                }
                int i7 = 0;
                for (int i8 = 0; i8 < iMin; i8++) {
                    if (copy_slot(nonBlockingIdentityHashMap, (i4 + i8) & (iLen - 1), objArr, objArr2)) {
                        i7++;
                    }
                }
                if (i7 > 0) {
                    copy_check_and_promote(nonBlockingIdentityHashMap, objArr, i7);
                }
                i4 += iMin;
                if (!z && i5 == -1) {
                    return;
                } else {
                    i3 = -1;
                }
            }
            copy_check_and_promote(nonBlockingIdentityHashMap, objArr, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object[] copy_slot_and_check(NonBlockingIdentityHashMap nonBlockingIdentityHashMap, Object[] objArr, int i, Object obj) {
            Object[] objArr2 = this._newkvs;
            if (copy_slot(nonBlockingIdentityHashMap, i, objArr, this._newkvs)) {
                copy_check_and_promote(nonBlockingIdentityHashMap, objArr, 1);
            }
            return obj == null ? objArr2 : nonBlockingIdentityHashMap.help_copy(objArr2);
        }

        private final void copy_check_and_promote(NonBlockingIdentityHashMap nonBlockingIdentityHashMap, Object[] objArr, int i) {
            int iLen = NonBlockingIdentityHashMap.len(objArr);
            long j = this._copyDone;
            if (i > 0) {
                while (!_copyDoneUpdater.compareAndSet(this, j, j + ((long) i))) {
                    j = this._copyDone;
                }
            }
            if (j + ((long) i) == iLen && nonBlockingIdentityHashMap._kvs == objArr && nonBlockingIdentityHashMap.CAS_kvs(objArr, this._newkvs)) {
                nonBlockingIdentityHashMap._last_resize_milli = System.currentTimeMillis();
            }
        }

        private boolean copy_slot(NonBlockingIdentityHashMap nonBlockingIdentityHashMap, int i, Object[] objArr, Object[] objArr2) {
            Object objKey;
            while (true) {
                objKey = NonBlockingIdentityHashMap.key(objArr, i);
                if (objKey != null) {
                    break;
                }
                NonBlockingIdentityHashMap.CAS_key(objArr, i, null, NonBlockingIdentityHashMap.TOMBSTONE);
            }
            Object objVal = NonBlockingIdentityHashMap.val(objArr, i);
            while (true) {
                if (objVal instanceof Prime) {
                    break;
                }
                Prime prime = (objVal == null || objVal == NonBlockingIdentityHashMap.TOMBSTONE) ? NonBlockingIdentityHashMap.TOMBPRIME : new Prime(objVal);
                if (NonBlockingIdentityHashMap.CAS_val(objArr, i, objVal, prime)) {
                    if (prime != NonBlockingIdentityHashMap.TOMBPRIME) {
                        objVal = prime;
                        break;
                    }
                    return true;
                }
                objVal = NonBlockingIdentityHashMap.val(objArr, i);
            }
            if (objVal == NonBlockingIdentityHashMap.TOMBPRIME) {
                return false;
            }
            boolean z = NonBlockingIdentityHashMap.putIfMatch(nonBlockingIdentityHashMap, objArr2, objKey, ((Prime) objVal)._V, null) == null;
            while (!NonBlockingIdentityHashMap.CAS_val(objArr, i, objVal, NonBlockingIdentityHashMap.TOMBPRIME)) {
                objVal = NonBlockingIdentityHashMap.val(objArr, i);
            }
            return z;
        }
    }

    private class SnapshotV implements Iterator<TypeV>, Enumeration<TypeV> {
        private int _idx;
        private Object _nextK;
        private TypeV _nextV;
        private Object _prevK;
        private TypeV _prevV;
        final Object[] _sskvs;

        public SnapshotV() {
            while (true) {
                Object[] objArr = NonBlockingIdentityHashMap.this._kvs;
                CHM chm = NonBlockingIdentityHashMap.chm(objArr);
                if (chm._newkvs != null) {
                    chm.help_copy_impl(NonBlockingIdentityHashMap.this, objArr, true);
                } else {
                    this._sskvs = objArr;
                    next();
                    return;
                }
            }
        }

        int length() {
            return NonBlockingIdentityHashMap.len(this._sskvs);
        }

        Object key(int i) {
            return NonBlockingIdentityHashMap.key(this._sskvs, i);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this._nextV != null;
        }

        @Override // java.util.Iterator
        public TypeV next() {
            if (this._idx != 0 && this._nextV == null) {
                throw new NoSuchElementException();
            }
            this._prevK = this._nextK;
            this._prevV = this._nextV;
            this._nextV = null;
            while (this._idx < length()) {
                int i = this._idx;
                this._idx = i + 1;
                Object objKey = key(i);
                this._nextK = objKey;
                if (objKey != null && objKey != NonBlockingIdentityHashMap.TOMBSTONE) {
                    TypeV typev = (TypeV) NonBlockingIdentityHashMap.this.get(this._nextK);
                    this._nextV = typev;
                    if (typev != null) {
                        break;
                    }
                }
            }
            return this._prevV;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this._prevV != null) {
                NonBlockingIdentityHashMap.putIfMatch(NonBlockingIdentityHashMap.this, this._sskvs, this._prevK, NonBlockingIdentityHashMap.TOMBSTONE, this._prevV);
                this._prevV = null;
                return;
            }
            throw new IllegalStateException();
        }

        @Override // java.util.Enumeration
        public TypeV nextElement() {
            return (TypeV) next();
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return hasNext();
        }
    }

    public Enumeration<TypeV> elements() {
        return new SnapshotV();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<TypeV> values() {
        return new AbstractCollection<TypeV>() { // from class: org.jctools.maps.NonBlockingIdentityHashMap.1
            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                NonBlockingIdentityHashMap.this.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return NonBlockingIdentityHashMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean contains(Object obj) {
                return NonBlockingIdentityHashMap.this.containsValue(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<TypeV> iterator() {
                return new SnapshotV();
            }
        };
    }

    private class SnapshotK implements Iterator<TypeK>, Enumeration<TypeK> {
        final NonBlockingIdentityHashMap<TypeK, TypeV>.SnapshotV _ss;

        public SnapshotK() {
            this._ss = new SnapshotV();
        }

        @Override // java.util.Iterator
        public void remove() {
            this._ss.remove();
        }

        @Override // java.util.Iterator
        public TypeK next() {
            this._ss.next();
            return (TypeK) ((SnapshotV) this._ss)._prevK;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this._ss.hasNext();
        }

        @Override // java.util.Enumeration
        public TypeK nextElement() {
            return (TypeK) next();
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return hasNext();
        }
    }

    public Enumeration<TypeK> keys() {
        return new SnapshotK();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<TypeK> keySet() {
        return new AbstractSet<TypeK>() { // from class: org.jctools.maps.NonBlockingIdentityHashMap.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                NonBlockingIdentityHashMap.this.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return NonBlockingIdentityHashMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return NonBlockingIdentityHashMap.this.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                return NonBlockingIdentityHashMap.this.remove(obj) != null;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<TypeK> iterator() {
                return new SnapshotK();
            }
        };
    }

    private class NBHMEntry extends AbstractEntry<TypeK, TypeV> {
        NBHMEntry(TypeK typek, TypeV typev) {
            super(typek, typev);
        }

        @Override // java.util.Map.Entry
        public TypeV setValue(TypeV typev) {
            typev.getClass();
            this._val = typev;
            return (TypeV) NonBlockingIdentityHashMap.this.put(this._key, typev);
        }
    }

    private class SnapshotE implements Iterator<Map.Entry<TypeK, TypeV>> {
        final NonBlockingIdentityHashMap<TypeK, TypeV>.SnapshotV _ss;

        public SnapshotE() {
            this._ss = new SnapshotV();
        }

        @Override // java.util.Iterator
        public void remove() {
            this._ss.remove();
        }

        @Override // java.util.Iterator
        public Map.Entry<TypeK, TypeV> next() {
            this._ss.next();
            return new NBHMEntry(((SnapshotV) this._ss)._prevK, ((SnapshotV) this._ss)._prevV);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this._ss.hasNext();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<TypeK, TypeV>> entrySet() {
        return new AbstractSet<Map.Entry<TypeK, TypeV>>() { // from class: org.jctools.maps.NonBlockingIdentityHashMap.3
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                NonBlockingIdentityHashMap.this.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return NonBlockingIdentityHashMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return NonBlockingIdentityHashMap.this.remove(entry.getKey(), entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return NonBlockingIdentityHashMap.this.get(entry.getKey()).equals(entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<TypeK, TypeV>> iterator() {
                return new SnapshotE();
            }
        };
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (TypeK typek : keySet()) {
            TypeV typev = get(typek);
            objectOutputStream.writeObject(typek);
            objectOutputStream.writeObject(typev);
        }
        objectOutputStream.writeObject(null);
        objectOutputStream.writeObject(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        initialize(8);
        while (true) {
            Object object = objectInputStream.readObject();
            Object object2 = objectInputStream.readObject();
            if (object == null) {
                return;
            } else {
                put(object, object2);
            }
        }
    }
}
