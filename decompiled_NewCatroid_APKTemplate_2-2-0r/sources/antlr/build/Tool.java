package antlr.build;

import antlr.Utils;
import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes.dex */
public class Tool {
    static /* synthetic */ Class class$antlr$build$Tool;
    public String os;

    public Tool() {
        this.os = null;
        this.os = System.getProperty(SystemProperties.OS_NAME);
    }

    public static void main(String[] strArr) {
        if (strArr.length != 1) {
            System.err.println("usage: java antlr.build.Tool action");
        } else {
            new Tool().perform("antlr.build.ANTLR", strArr[0]);
        }
    }

    public void perform(String str, String str2) {
        Class cls;
        Object objCreateInstanceOf;
        if (str == null || str2 == null) {
            error("missing app or action");
            return;
        }
        Class cls2 = null;
        try {
            objCreateInstanceOf = Utils.createInstanceOf(str);
        } catch (Exception e) {
            try {
                Class clsLoadClass = !str.startsWith("antlr.build.") ? Utils.loadClass(new StringBuffer("antlr.build.").append(str).toString()) : null;
                try {
                    error(new StringBuffer("no such application ").append(str).toString(), e);
                    cls2 = clsLoadClass;
                    objCreateInstanceOf = null;
                } catch (Exception e2) {
                    cls = clsLoadClass;
                    e = e2;
                    error(new StringBuffer("no such application ").append(str).toString(), e);
                    objCreateInstanceOf = null;
                    cls2 = cls;
                }
            } catch (Exception e3) {
                e = e3;
                cls = null;
            }
        }
        if (cls2 == null || objCreateInstanceOf == null) {
            return;
        }
        try {
            Class<?>[] clsArr = new Class[1];
            Class<?> clsClass$ = class$antlr$build$Tool;
            if (clsClass$ == null) {
                clsClass$ = class$("antlr.build.Tool");
                class$antlr$build$Tool = clsClass$;
            }
            clsArr[0] = clsClass$;
            cls2.getMethod(str2, clsArr).invoke(objCreateInstanceOf, this);
        } catch (Exception e4) {
            error(new StringBuffer("no such action for application ").append(str).toString(), e4);
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public void system(String str) {
        Process processExec;
        Runtime runtime = Runtime.getRuntime();
        try {
            log(str);
            if (!this.os.startsWith("Windows")) {
                processExec = runtime.exec(new String[]{"sh", "-c", str});
            } else {
                processExec = runtime.exec(str);
            }
            StreamScarfer streamScarfer = new StreamScarfer(processExec.getErrorStream(), "stderr", this);
            StreamScarfer streamScarfer2 = new StreamScarfer(processExec.getInputStream(), "stdout", this);
            streamScarfer.start();
            streamScarfer2.start();
            processExec.waitFor();
        } catch (Exception e) {
            error(new StringBuffer("cannot exec ").append(str).toString(), e);
        }
    }

    public void antlr(String str) {
        String parent = null;
        try {
            parent = new File(str).getParent();
            if (parent != null) {
                parent = new File(parent).getCanonicalPath();
            }
        } catch (IOException unused) {
            error(new StringBuffer("Invalid grammar file: ").append(str).toString());
        }
        if (parent != null) {
            log(new StringBuffer("java antlr.Tool -o ").append(parent).append(" ").append(str).toString());
            new antlr.Tool().doEverything(new String[]{"-o", parent, str});
        }
    }

    public void stdout(String str) {
        System.out.println(str);
    }

    public void stderr(String str) {
        System.err.println(str);
    }

    public void error(String str) {
        System.err.println(new StringBuffer("antlr.build.Tool: ").append(str).toString());
    }

    public void log(String str) {
        System.out.println(new StringBuffer("executing: ").append(str).toString());
    }

    public void error(String str, Exception exc) {
        System.err.println(new StringBuffer("antlr.build.Tool: ").append(str).toString());
        exc.printStackTrace(System.err);
    }
}
