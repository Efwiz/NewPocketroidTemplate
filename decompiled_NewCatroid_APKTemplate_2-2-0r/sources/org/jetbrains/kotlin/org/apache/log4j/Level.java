package org.jetbrains.kotlin.org.apache.log4j;

import com.google.android.gms.cast.MediaError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class Level extends Priority implements Serializable {
    static /* synthetic */ Class class$org$apache$log4j$Level = null;
    static final long serialVersionUID = 3491141966387921974L;
    public static final Level OFF = new Level(Integer.MAX_VALUE, "OFF", 0);
    public static final Level FATAL = new Level(50000, "FATAL", 0);
    public static final Level ERROR = new Level(40000, MediaError.ERROR_TYPE_ERROR, 3);
    public static final Level WARN = new Level(30000, "WARN", 4);
    public static final Level INFO = new Level(20000, "INFO", 6);
    public static final Level DEBUG = new Level(10000, "DEBUG", 7);
    public static final Level TRACE = new Level(5000, "TRACE", 7);
    public static final Level ALL = new Level(Integer.MIN_VALUE, "ALL", 7);

    protected Level(int i, String str, int i2) {
        super(i, str, i2);
    }

    public static Level toLevel(int i) {
        return toLevel(i, DEBUG);
    }

    public static Level toLevel(int i, Level level) {
        if (i == Integer.MIN_VALUE) {
            return ALL;
        }
        if (i == 5000) {
            return TRACE;
        }
        if (i == 10000) {
            return DEBUG;
        }
        if (i == 20000) {
            return INFO;
        }
        if (i == 30000) {
            return WARN;
        }
        if (i == 40000) {
            return ERROR;
        }
        if (i != 50000) {
            return i != Integer.MAX_VALUE ? level : OFF;
        }
        return FATAL;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.level = objectInputStream.readInt();
        this.syslogEquivalent = objectInputStream.readInt();
        this.levelStr = objectInputStream.readUTF();
        if (this.levelStr == null) {
            this.levelStr = "";
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.level);
        objectOutputStream.writeInt(this.syslogEquivalent);
        objectOutputStream.writeUTF(this.levelStr);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private Object readResolve() throws Throwable {
        Class<?> cls = getClass();
        Class<?> clsClass$ = class$org$apache$log4j$Level;
        if (clsClass$ == null) {
            clsClass$ = class$("org.jetbrains.kotlin.org.apache.log4j.Level");
            class$org$apache$log4j$Level = clsClass$;
        }
        return cls == clsClass$ ? toLevel(this.level) : this;
    }
}
