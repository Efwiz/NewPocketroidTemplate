package antlr.build;

import java.io.File;
import java.io.FilenameFilter;

/* JADX INFO: loaded from: classes.dex */
public class ANTLR {
    public static String compiler = "javac";
    public static String jarName = "antlr.jar";
    public static String root = ".";
    public static String[] srcdir = {"antlr", "antlr/actions/cpp", "antlr/actions/java", "antlr/actions/csharp", "antlr/collections", "antlr/collections/impl", "antlr/debug", "antlr/ASdebug", "antlr/debug/misc", "antlr/preprocessor"};

    public ANTLR() {
        compiler = System.getProperty("antlr.build.compiler", compiler);
        root = System.getProperty("antlr.build.root", root);
    }

    public String getName() {
        return "ANTLR";
    }

    public void build(Tool tool) {
        if (rootIsValidANTLRDir(tool)) {
            tool.antlr(new StringBuffer().append(root).append("/antlr/antlr.g").toString());
            tool.antlr(new StringBuffer().append(root).append("/antlr/tokdef.g").toString());
            tool.antlr(new StringBuffer().append(root).append("/antlr/preprocessor/preproc.g").toString());
            tool.antlr(new StringBuffer().append(root).append("/antlr/actions/java/action.g").toString());
            tool.antlr(new StringBuffer().append(root).append("/antlr/actions/cpp/action.g").toString());
            tool.antlr(new StringBuffer().append(root).append("/antlr/actions/csharp/action.g").toString());
            for (int i = 0; i < srcdir.length; i++) {
                tool.system(new StringBuffer().append(compiler).append(" -d ").append(root).append(" ").append(root).append("/").append(srcdir[i]).append("/*.java").toString());
            }
        }
    }

    public void jar(Tool tool) {
        if (rootIsValidANTLRDir(tool)) {
            StringBuffer stringBuffer = new StringBuffer(2000);
            stringBuffer.append(new StringBuffer("jar cvf ").append(root).append("/").append(jarName).toString());
            for (int i = 0; i < srcdir.length; i++) {
                stringBuffer.append(new StringBuffer(" ").append(root).append("/").append(srcdir[i]).append("/*.class").toString());
            }
            tool.system(stringBuffer.toString());
        }
    }

    protected boolean rootIsValidANTLRDir(Tool tool) {
        if (root == null) {
            return false;
        }
        File file = new File(root);
        if (!file.exists()) {
            tool.error(new StringBuffer("Property antlr.build.root==").append(root).append(" does not exist").toString());
            return false;
        }
        if (!file.isDirectory()) {
            tool.error(new StringBuffer("Property antlr.build.root==").append(root).append(" is not a directory").toString());
            return false;
        }
        String[] list = file.list(new FilenameFilter() { // from class: antlr.build.ANTLR.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str) {
                return file2.isDirectory() && str.equals("antlr");
            }
        });
        if (list == null || list.length == 0) {
            tool.error(new StringBuffer("Property antlr.build.root==").append(root).append(" does not appear to be a valid ANTLR project root (no antlr subdir)").toString());
            return false;
        }
        String[] list2 = new File(new StringBuffer().append(root).append("/antlr").toString()).list();
        if (list2 != null && list2.length != 0) {
            return true;
        }
        tool.error(new StringBuffer("Property antlr.build.root==").append(root).append(" does not appear to be a valid ANTLR project root (no .java files in antlr subdir").toString());
        return false;
    }
}
