package defpackage;

import com.beust.jcommander.Parameters;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import net.bytebuddy.description.method.ParameterDescription;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Print;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.luajc.LuaJC;

/* JADX INFO: loaded from: classes5.dex */
public class lua {
    private static String encoding = null;
    private static Globals globals = null;
    private static boolean print = false;
    private static final String usage = "usage: java -cp luaj-jse.jar lua [options] [script [args]].\nAvailable options are:\n  -e stat  execute string 'stat'\n  -l name  require library 'name'\n  -i       enter interactive mode after executing 'script'\n  -v       show version information\n  -b      \tuse luajc bytecode-to-bytecode compiler (requires bcel on class path)\n  -n      \tnodebug - do not load debug library by default\n  -p      \tprint the prototype\n  -c enc  \tuse the supplied encoding 'enc' for input files\n  --       stop handling options\n  -        execute stdin and stop handling options";
    private static final String version = "Luaj-jse 3.0.1 Copyright (c) 2012 Luaj.org.org";

    private static void interactiveMode() throws Throwable {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("> ");
            System.out.flush();
            String line = bufferedReader.readLine();
            if (line == null) {
                return;
            } else {
                processScript(new ByteArrayInputStream(line.getBytes()), "=stdin", null, 0);
            }
        }
    }

    private static void loadLibrary(String str) throws IOException {
        LuaString luaStringValueOf = LuaValue.valueOf(str);
        try {
            globals.get("require").call(luaStringValueOf);
        } catch (Exception e) {
            try {
                ((LuaValue) Class.forName(str).newInstance()).call(luaStringValueOf, globals);
            } catch (Exception e2) {
                throw new IOException(new StringBuffer("loadLibrary(").append(str).append(") failed: ").append(e).append(",").append(e2).toString());
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:89:0x010f A[Catch: IOException -> 0x0125, TryCatch #0 {IOException -> 0x0125, blocks: (B:7:0x000e, B:11:0x001d, B:14:0x0027, B:17:0x0031, B:32:0x0053, B:56:0x0098, B:33:0x0057, B:35:0x005c, B:36:0x005f, B:39:0x0068, B:41:0x006d, B:43:0x0072, B:47:0x007d, B:46:0x0078, B:49:0x0085, B:52:0x008b, B:54:0x0094, B:58:0x009d, B:60:0x00a6, B:62:0x00af, B:64:0x00b3, B:66:0x00b8, B:70:0x00c1, B:72:0x00cf, B:75:0x00d4, B:78:0x00dd, B:80:0x00e5, B:81:0x00e9, B:82:0x00ed, B:91:0x0113, B:88:0x00fc, B:89:0x010f, B:92:0x0115, B:94:0x0121, B:61:0x00ab), top: B:99:0x000e }] */
    public static void main(String[] strArr) throws Throwable {
        InputStream fileInputStream;
        String str;
        boolean z = strArr.length == 0;
        Vector vector = null;
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = true;
        while (i < strArr.length && z5 && strArr[i].startsWith(Parameters.DEFAULT_OPTION_PREFIXES) && strArr[i].length() > 1) {
            try {
                char cCharAt = strArr[i].charAt(1);
                if (cCharAt == '-') {
                    if (strArr[i].length() > 2) {
                        usageExit();
                    }
                    z5 = false;
                } else if (cCharAt == 'e') {
                    i++;
                    if (i >= strArr.length) {
                        usageExit();
                    }
                } else if (cCharAt == 'i') {
                    z = true;
                } else if (cCharAt == 'l') {
                    i++;
                    if (i >= strArr.length) {
                        usageExit();
                    }
                    if (vector == null) {
                        vector = new Vector();
                    }
                    vector.addElement(strArr[i]);
                } else if (cCharAt == 'n') {
                    z3 = true;
                } else if (cCharAt == 'p') {
                    print = true;
                } else if (cCharAt == 'v') {
                    z2 = true;
                } else if (cCharAt == 'b') {
                    z4 = true;
                } else if (cCharAt != 'c') {
                    usageExit();
                } else {
                    i++;
                    if (i >= strArr.length) {
                        usageExit();
                    }
                    encoding = strArr[i];
                }
                i++;
            } catch (IOException e) {
                System.err.println(e.toString());
                System.exit(-2);
                return;
            }
        }
        if (z2) {
            System.out.println(version);
        }
        Globals globalsStandardGlobals = z3 ? JsePlatform.standardGlobals() : JsePlatform.debugGlobals();
        globals = globalsStandardGlobals;
        if (z4) {
            LuaJC.install(globalsStandardGlobals);
        }
        int size = vector != null ? vector.size() : 0;
        for (int i2 = 0; i2 < size; i2++) {
            loadLibrary((String) vector.elementAt(i2));
        }
        int i3 = 0;
        boolean z6 = true;
        while (i3 < strArr.length) {
            if (!z6 || !strArr[i3].startsWith(Parameters.DEFAULT_OPTION_PREFIXES)) {
                fileInputStream = new FileInputStream(strArr[i3]);
                str = strArr[i3];
            } else if (Parameters.DEFAULT_OPTION_PREFIXES.equals(strArr[i3])) {
                fileInputStream = System.in;
                str = "=stdin";
            } else {
                char cCharAt2 = strArr[i3].charAt(1);
                if (cCharAt2 == '-') {
                    z6 = false;
                } else if (cCharAt2 == 'c') {
                    i3++;
                } else if (cCharAt2 == 'e') {
                    i3++;
                    processScript(new ByteArrayInputStream(strArr[i3].getBytes()), "string", strArr, i3);
                } else if (cCharAt2 == 'l') {
                    i3++;
                }
                i3++;
            }
            processScript(fileInputStream, str, strArr, i3);
        }
        if (z) {
            interactiveMode();
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private static void processScript(InputStream inputStream, String str, String[] strArr, int i) throws Throwable {
        InputStream bufferedInputStream;
        Throwable th;
        LuaValue luaValueLoad;
        try {
            try {
                bufferedInputStream = new BufferedInputStream(inputStream);
                try {
                    if (encoding != null) {
                        luaValueLoad = globals.load(new InputStreamReader(bufferedInputStream, encoding), str);
                    } else {
                        Globals globals2 = globals;
                        luaValueLoad = globals2.load(bufferedInputStream, str, "bt", globals2);
                    }
                    bufferedInputStream.close();
                    if (print && luaValueLoad.isclosure()) {
                        Print.print(luaValueLoad.checkclosure().p);
                    }
                    luaValueLoad.invoke(setGlobalArg(str, strArr, i, globals));
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream.close();
                    throw th;
                }
            } catch (Throwable th3) {
                bufferedInputStream = inputStream;
                th = th3;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static Varargs setGlobalArg(String str, String[] strArr, int i, LuaValue luaValue) {
        if (strArr == null) {
            return LuaValue.NONE;
        }
        LuaTable luaTableTableOf = LuaValue.tableOf();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            luaTableTableOf.set(i2 - i, LuaValue.valueOf(strArr[i2]));
        }
        luaTableTableOf.set(0, LuaValue.valueOf(str));
        luaTableTableOf.set(-1, LuaValue.valueOf("luaj"));
        luaValue.set(ParameterDescription.NAME_PREFIX, luaTableTableOf);
        return luaTableTableOf.unpack();
    }

    private static void usageExit() {
        System.out.println(usage);
        System.exit(-1);
    }
}
