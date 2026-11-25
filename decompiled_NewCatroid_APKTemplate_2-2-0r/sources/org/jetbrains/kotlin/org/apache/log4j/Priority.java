package org.jetbrains.kotlin.org.apache.log4j;

import com.google.android.gms.cast.MediaError;

/* JADX INFO: loaded from: classes4.dex */
public class Priority {
    transient int level;
    transient String levelStr;
    transient int syslogEquivalent;
    public static final Priority FATAL = new Level(50000, "FATAL", 0);
    public static final Priority ERROR = new Level(40000, MediaError.ERROR_TYPE_ERROR, 3);
    public static final Priority WARN = new Level(30000, "WARN", 4);
    public static final Priority INFO = new Level(20000, "INFO", 6);
    public static final Priority DEBUG = new Level(10000, "DEBUG", 7);

    protected Priority() {
        this.level = 10000;
        this.levelStr = "DEBUG";
        this.syslogEquivalent = 7;
    }

    protected Priority(int i, String str, int i2) {
        this.level = i;
        this.levelStr = str;
        this.syslogEquivalent = i2;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Priority) && this.level == ((Priority) obj).level;
    }

    public final String toString() {
        return this.levelStr;
    }
}
