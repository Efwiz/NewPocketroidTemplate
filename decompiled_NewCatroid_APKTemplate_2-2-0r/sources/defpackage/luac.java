package defpackage;

import com.beust.jcommander.Parameters;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Print;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.compiler.DumpState;
import org.luaj.vm2.lib.jse.JsePlatform;

/* JADX INFO: loaded from: classes6.dex */
public class luac {
    private static final String usage = "usage: java -cp luaj-jse.jar luac [options] [filenames].\nAvailable options are:\n  -        process stdin\n  -l       list\n  -o name  output to file 'name' (default is \"luac.out\")\n  -p       parse only\n  -s       strip debug information\n  -e       little endian format for numbers\n  -i<n>    number format 'n', (n=0,1 or 4, default=0)\n  -v       show version information\n  -c enc  \tuse the supplied encoding 'enc' for input files\n  --       stop handling options\n";
    private static final String version = "Luaj-jse 3.0.1Copyright (C) 2009 luaj.org";
    private String encoding;
    private boolean list;
    private boolean littleendian;
    private int numberformat;
    private String output;
    private boolean parseonly;
    private boolean processing;
    private boolean stripdebug;
    private boolean versioninfo;

    private luac(String[] strArr) throws IOException {
        this.list = false;
        this.output = "luac.out";
        this.parseonly = false;
        this.stripdebug = false;
        this.littleendian = false;
        this.numberformat = 0;
        this.versioninfo = false;
        this.processing = true;
        this.encoding = null;
        int i = 0;
        while (i < strArr.length) {
            try {
                if (this.processing && strArr[i].startsWith(Parameters.DEFAULT_OPTION_PREFIXES) && strArr[i].length() > 1) {
                    char cCharAt = strArr[i].charAt(1);
                    if (cCharAt == '-') {
                        if (strArr[i].length() > 2) {
                            usageExit();
                        }
                        this.processing = false;
                    } else if (cCharAt == 'c') {
                        i++;
                        if (i >= strArr.length) {
                            usageExit();
                        }
                        this.encoding = strArr[i];
                    } else if (cCharAt == 'e') {
                        this.littleendian = true;
                    } else if (cCharAt == 'i') {
                        if (strArr[i].length() <= 2) {
                            usageExit();
                        }
                        this.numberformat = Integer.parseInt(strArr[i].substring(2));
                    } else if (cCharAt == 'l') {
                        this.list = true;
                    } else if (cCharAt == 's') {
                        this.stripdebug = true;
                    } else if (cCharAt == 'v') {
                        this.versioninfo = true;
                    } else if (cCharAt == 'o') {
                        i++;
                        if (i >= strArr.length) {
                            usageExit();
                        }
                        this.output = strArr[i];
                    } else if (cCharAt != 'p') {
                        usageExit();
                    } else {
                        this.parseonly = true;
                    }
                }
                i++;
            } catch (IOException e) {
                System.err.println(e.toString());
                System.exit(-2);
                return;
            }
        }
        if (this.versioninfo) {
            System.out.println(version);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(this.output);
        try {
            Globals globalsStandardGlobals = JsePlatform.standardGlobals();
            this.processing = true;
            int i2 = 0;
            while (i2 < strArr.length) {
                if (!this.processing || !strArr[i2].startsWith(Parameters.DEFAULT_OPTION_PREFIXES)) {
                    String str = strArr[i2];
                    processScript(globalsStandardGlobals, new FileInputStream(strArr[i2]), str.substring(0, str.length() - 4), fileOutputStream);
                } else if (strArr[i2].length() <= 1) {
                    processScript(globalsStandardGlobals, System.in, "=stdin", fileOutputStream);
                } else {
                    char cCharAt2 = strArr[i2].charAt(1);
                    if (cCharAt2 == '-') {
                        this.processing = false;
                    } else if (cCharAt2 == 'c' || cCharAt2 == 'o') {
                        i2++;
                    }
                }
                i2++;
            }
        } finally {
            fileOutputStream.close();
        }
    }

    public static void main(String[] strArr) throws IOException {
        new luac(strArr);
    }

    private void processScript(Globals globals, InputStream inputStream, String str, OutputStream outputStream) throws Throwable {
        try {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                try {
                    Prototype prototypeCompilePrototype = this.encoding != null ? globals.compilePrototype(new InputStreamReader(bufferedInputStream, this.encoding), str) : globals.compilePrototype(bufferedInputStream, str);
                    if (this.list) {
                        Print.printCode(prototypeCompilePrototype);
                    }
                    if (!this.parseonly) {
                        DumpState.dump(prototypeCompilePrototype, outputStream, this.stripdebug, this.numberformat, this.littleendian);
                    }
                    bufferedInputStream.close();
                } catch (Exception e) {
                    e = e;
                    inputStream = bufferedInputStream;
                    e.printStackTrace(System.err);
                    inputStream.close();
                } catch (Throwable th) {
                    th = th;
                    inputStream = bufferedInputStream;
                    inputStream.close();
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void usageExit() {
        System.out.println(usage);
        System.exit(-1);
    }
}
