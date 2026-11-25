package io.netty.handler.codec;

/* JADX INFO: loaded from: classes11.dex */
public class UnsupportedMessageTypeException extends CodecException {
    private static final long serialVersionUID = 2799598826487038726L;

    /* JADX WARN: Illegal instructions before constructor call */
    public UnsupportedMessageTypeException(Object obj, Class<?>... clsArr) {
        String name2;
        if (obj == null) {
            name2 = "null";
        } else {
            name2 = obj.getClass().getName();
        }
        super(message(name2, clsArr));
    }

    public UnsupportedMessageTypeException() {
    }

    public UnsupportedMessageTypeException(String str, Throwable th) {
        super(str, th);
    }

    public UnsupportedMessageTypeException(String str) {
        super(str);
    }

    public UnsupportedMessageTypeException(Throwable th) {
        super(th);
    }

    private static String message(String str, Class<?>... clsArr) {
        Class<?> cls;
        StringBuilder sb = new StringBuilder(str);
        if (clsArr != null && clsArr.length > 0) {
            sb.append(" (expected: ").append(clsArr[0].getName());
            for (int i = 1; i < clsArr.length && (cls = clsArr[i]) != null; i++) {
                sb.append(", ").append(cls.getName());
            }
            sb.append(')');
        }
        return sb.toString();
    }
}
