package antlr.debug;

/* JADX INFO: loaded from: classes.dex */
public class NewLineEvent extends Event {
    private int line;

    public NewLineEvent(Object obj) {
        super(obj);
    }

    public NewLineEvent(Object obj, int i) {
        super(obj);
        setValues(i);
    }

    public int getLine() {
        return this.line;
    }

    void setLine(int i) {
        this.line = i;
    }

    @Override // antlr.debug.Event
    void setValues(int i) {
        setLine(i);
    }

    @Override // java.util.EventObject
    public String toString() {
        return new StringBuffer("NewLineEvent [").append(this.line).append("]").toString();
    }
}
