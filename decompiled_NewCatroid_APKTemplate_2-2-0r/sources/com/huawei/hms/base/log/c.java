package com.huawei.hms.base.log;

import android.os.Process;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import io.netty.util.internal.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* JADX INFO: compiled from: LogRecord.java */
/* JADX INFO: loaded from: classes10.dex */
public class c {
    public String b;
    public String c;
    public int d;
    public String g;
    public int h;
    public int i;
    public int j;
    public final StringBuilder a = new StringBuilder();
    public long e = 0;
    public long f = 0;

    public c(int i, String str, int i2, String str2) {
        this.c = "HMS";
        this.j = i;
        this.b = str;
        this.d = i2;
        if (str2 != null) {
            this.c = str2;
        }
        b();
    }

    public static String a(int i) {
        if (i == 3) {
            return "D";
        }
        if (i == 4) {
            return "I";
        }
        if (i != 5) {
            return i != 6 ? String.valueOf(i) : ExifInterface.LONGITUDE_EAST;
        }
        return ExifInterface.LONGITUDE_WEST;
    }

    public final c b() {
        this.e = System.currentTimeMillis();
        Thread threadCurrentThread = Thread.currentThread();
        this.f = threadCurrentThread.getId();
        this.h = Process.myPid();
        StackTraceElement[] stackTrace = threadCurrentThread.getStackTrace();
        int length = stackTrace.length;
        int i = this.j;
        if (length > i) {
            StackTraceElement stackTraceElement = stackTrace[i];
            this.g = stackTraceElement.getFileName();
            this.i = stackTraceElement.getLineNumber();
        }
        return this;
    }

    public String c() {
        StringBuilder sb = new StringBuilder();
        b(sb);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        b(sb);
        a(sb);
        return sb.toString();
    }

    public <T> c a(T t) {
        this.a.append(t);
        return this;
    }

    public c a(Throwable th) {
        a('\n').a(Log.getStackTraceString(th));
        return this;
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        a(sb);
        return sb.toString();
    }

    public final StringBuilder a(StringBuilder sb) {
        sb.append(StringUtil.SPACE).append(this.a.toString());
        return sb;
    }

    public final StringBuilder b(StringBuilder sb) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        sb.append('[');
        sb.append(simpleDateFormat.format(Long.valueOf(this.e)));
        sb.append(StringUtil.SPACE).append(a(this.d)).append('/').append(this.c).append('/').append(this.b);
        sb.append(StringUtil.SPACE).append(this.h).append(':').append(this.f);
        sb.append(StringUtil.SPACE).append(this.g).append(':').append(this.i);
        sb.append(']');
        return sb;
    }
}
