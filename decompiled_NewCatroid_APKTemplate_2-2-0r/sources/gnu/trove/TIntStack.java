package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TIntStack {
    public static final int DEFAULT_CAPACITY = 4;
    protected TIntArrayList _list;

    public TIntStack() {
        this(4);
    }

    public TIntStack(TIntStack tIntStack) {
        this._list = new TIntArrayList(tIntStack._list.toNativeArray());
    }

    public TIntStack(int i) {
        this._list = new TIntArrayList(i);
    }

    public void push(int i) {
        this._list.add(i);
    }

    public int pop() {
        TIntArrayList tIntArrayList = this._list;
        return tIntArrayList.remove(tIntArrayList.size() - 1);
    }

    public int peek() {
        TIntArrayList tIntArrayList = this._list;
        return tIntArrayList.get(tIntArrayList.size() - 1);
    }

    public int size() {
        return this._list.size();
    }

    public void clear() {
        this._list.clear(4);
    }

    public void reset() {
        this._list.reset();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TIntStack) {
            return this._list.equals(((TIntStack) obj)._list);
        }
        return false;
    }

    public int hashCode() {
        return this._list.hashCode();
    }
}
