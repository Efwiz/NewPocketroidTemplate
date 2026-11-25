package javaslang;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: classes11.dex */
public final class Tuple2<T1, T2> implements Serializable, Comparable<Tuple2<T1, T2>>, Tuple {
    private static final long serialVersionUID = 1;
    public final T1 _1;
    public final T2 _2;

    public Tuple2(T1 t1, T2 t2) {
        this._1 = t1;
        this._2 = t2;
    }

    private static <U1 extends Comparable<? super U1>, U2 extends Comparable<? super U2>> int compareTo(Tuple2<?, ?> tuple2, Tuple2<?, ?> tuple3) {
        int iCompareTo = ((Comparable) tuple2._1).compareTo(tuple3._1);
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        int iCompareTo2 = ((Comparable) tuple2._2).compareTo(tuple3._2);
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        return 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(Tuple2<T1, T2> tuple2) {
        return compareTo(this, tuple2);
    }

    public T1 _1() {
        return this._1;
    }

    public T2 _2() {
        return this._2;
    }

    public <U1, U2> Tuple2<U1, U2> map(BiFunction<? super T1, ? super T2, Tuple2<U1, U2>> biFunction) {
        Objects.requireNonNull(biFunction, "mapper is null");
        return biFunction.apply(this._1, this._2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tuple2)) {
            return false;
        }
        Tuple2 tuple2 = (Tuple2) obj;
        return Objects.equals(this._1, tuple2._1) && Objects.equals(this._2, tuple2._2);
    }

    public int hashCode() {
        return Objects.hash(this._1, this._2);
    }

    public String toString() {
        return String.format("(%s, %s)", this._1, this._2);
    }
}
