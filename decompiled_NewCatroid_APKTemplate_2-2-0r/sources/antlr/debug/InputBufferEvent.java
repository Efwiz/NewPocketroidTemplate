package antlr.debug;

/* JADX INFO: loaded from: classes.dex */
public class InputBufferEvent extends Event {
    public static final int CONSUME = 0;
    public static final int LA = 1;
    public static final int MARK = 2;
    public static final int REWIND = 3;
    char c;
    int lookaheadAmount;

    public InputBufferEvent(Object obj) {
        super(obj);
    }

    public InputBufferEvent(Object obj, int i, char c, int i2) {
        super(obj);
        setValues(i, c, i2);
    }

    public char getChar() {
        return this.c;
    }

    public int getLookaheadAmount() {
        return this.lookaheadAmount;
    }

    void setChar(char c) {
        this.c = c;
    }

    void setLookaheadAmount(int i) {
        this.lookaheadAmount = i;
    }

    void setValues(int i, char c, int i2) {
        super.setValues(i);
        setChar(c);
        setLookaheadAmount(i2);
    }

    @Override // java.util.EventObject
    public String toString() {
        return new StringBuffer("CharBufferEvent [").append(getType() == 0 ? "CONSUME, " : "LA, ").append(getChar()).append(",").append(getLookaheadAmount()).append("]").toString();
    }
}
