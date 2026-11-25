package javaslang;

import java.io.Serializable;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public final class Tuple3<T1, T2, T3> implements Serializable, Comparable<Tuple3<T1, T2, T3>>, Tuple {
    private static final long serialVersionUID = 1;
    public final T1 _1;
    public final T2 _2;
    public final T3 _3;

    public Tuple3(T1 t1, T2 t2, T3 t3) {
        this._1 = t1;
        this._2 = t2;
        this._3 = t3;
    }

    private static <U1 extends Comparable<? super U1>, U2 extends Comparable<? super U2>, U3 extends Comparable<? super U3>> int compareTo(Tuple3<?, ?, ?> tuple3, Tuple3<?, ?, ?> tuple4) {
        int iCompareTo = ((Comparable) tuple3._1).compareTo(tuple4._1);
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        int iCompareTo2 = ((Comparable) tuple3._2).compareTo(tuple4._2);
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        int iCompareTo3 = ((Comparable) tuple3._3).compareTo(tuple4._3);
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        return 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(Tuple3<T1, T2, T3> tuple3) {
        return compareTo(this, tuple3);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tuple3)) {
            return false;
        }
        Tuple3 tuple3 = (Tuple3) obj;
        return Objects.equals(this._1, tuple3._1) && Objects.equals(this._2, tuple3._2) && Objects.equals(this._3, tuple3._3);
    }

    public int hashCode() {
        return Objects.hash(this._1, this._2, this._3);
    }

    public String toString() {
        return String.format("(%s, %s, %s)", this._1, this._2, this._3);
    }
}
