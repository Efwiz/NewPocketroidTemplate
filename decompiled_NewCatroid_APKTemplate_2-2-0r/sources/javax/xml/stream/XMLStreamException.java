package javax.xml.stream;

/* JADX INFO: loaded from: classes11.dex */
public class XMLStreamException extends Exception {
    private static final long serialVersionUID = 2018819321811497362L;
    protected Location location;
    protected Throwable nested;

    public XMLStreamException() {
    }

    public XMLStreamException(String str) {
        super(str);
    }

    public XMLStreamException(String str, Throwable th) {
        super(str);
        this.nested = th;
    }

    public XMLStreamException(String str, Location location) {
        super(str);
        this.location = location;
    }

    public XMLStreamException(String str, Location location, Throwable th) {
        super(str);
        this.location = location;
        this.nested = th;
    }

    public XMLStreamException(Throwable th) {
        this.nested = th;
    }

    public Location getLocation() {
        return this.location;
    }

    public Throwable getNestedException() {
        return this.nested;
    }
}
