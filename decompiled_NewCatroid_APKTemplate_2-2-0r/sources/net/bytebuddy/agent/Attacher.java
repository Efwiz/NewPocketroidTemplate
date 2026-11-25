package net.bytebuddy.agent;

import io.netty.util.internal.StringUtil;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.agent.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public class Attacher {
    public static final String DUMP_PROPERTY = "net.bytebuddy.agent.attacher.dump";

    private Attacher() {
        throw new UnsupportedOperationException("This class is a utility class and not supposed to be instantiated");
    }

    public static void main(String[] strArr) {
        String string;
        try {
            if (strArr.length < 5 || strArr[4].length() == 0) {
                string = null;
            } else {
                StringBuilder sb = new StringBuilder(strArr[4].substring(1));
                for (int i = 5; i < strArr.length; i++) {
                    sb.append(StringUtil.SPACE).append(strArr[i]);
                }
                string = sb.toString();
            }
            install(Class.forName(strArr[0]), strArr[1], strArr[2], Boolean.parseBoolean(strArr[3]), string);
        } catch (Throwable th) {
            try {
                String property = System.getProperty(DUMP_PROPERTY);
                if (property != null && property.length() > 0) {
                    PrintStream printStream = new PrintStream((OutputStream) new FileOutputStream(property, true), false, "UTF-8");
                    try {
                        th.printStackTrace(printStream);
                        printStream.close();
                    } catch (Throwable th2) {
                        printStream.close();
                        throw th2;
                    }
                }
            } catch (Throwable unused) {
            }
            System.exit(1);
        }
    }

    protected static void install(Class<?> cls, String str, String str2, boolean z, @MaybeNull String str3) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object objInvoke = cls.getMethod("attach", String.class).invoke(null, str);
        try {
            cls.getMethod(z ? "loadAgentPath" : "loadAgent", String.class, String.class).invoke(objInvoke, str2, str3);
            cls.getMethod("detach", new Class[0]).invoke(objInvoke, new Object[0]);
        } catch (Throwable th) {
            cls.getMethod("detach", new Class[0]).invoke(objInvoke, new Object[0]);
            throw th;
        }
    }
}
