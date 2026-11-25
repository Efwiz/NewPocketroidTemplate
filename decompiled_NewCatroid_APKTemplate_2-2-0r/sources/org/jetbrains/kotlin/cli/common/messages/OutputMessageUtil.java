package org.jetbrains.kotlin.cli.common.messages;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public class OutputMessageUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/cli/common/messages/OutputMessageUtil";
        } else if (i != 3) {
            objArr[0] = "e";
        } else {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        }
        if (i == 1) {
            objArr[1] = "renderException";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/cli/common/messages/OutputMessageUtil";
        } else {
            objArr[1] = "formatOutputMessage";
        }
        if (i != 1 && i != 2) {
            if (i != 3) {
                objArr[2] = "renderException";
            } else {
                objArr[2] = "parseOutputMessage";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static String renderException(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(0);
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String string = stringWriter.toString();
        if (string == null) {
            $$$reportNull$$$0(1);
        }
        return string;
    }

    public static String formatOutputMessage(Collection<File> collection, File file) {
        String str = "Output:\n" + file.getPath() + "\nSources:\n" + StringUtil.join(collection, "\n");
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    public static Output parseOutputMessage(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        String[] strArrSplit = str.split("\n");
        if (strArrSplit.length <= 2 || !"Output:".equals(strArrSplit[0])) {
            return null;
        }
        if ("Sources:".equals(strArrSplit[1])) {
            return new Output(parseSourceFiles(strArrSplit, 2), null);
        }
        File file = new File(strArrSplit[1]);
        if ("Sources:".equals(strArrSplit[2])) {
            return new Output(parseSourceFiles(strArrSplit, 3), file);
        }
        return null;
    }

    private static Collection<File> parseSourceFiles(String[] strArr, int i) {
        ArrayList arrayList = new ArrayList();
        while (i < strArr.length) {
            arrayList.add(new File(strArr[i]));
            i++;
        }
        return arrayList;
    }

    public static class Output implements Serializable {
        static final long serialVersionUID = 0;
        public final File outputFile;
        public final Collection<File> sourceFiles;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "sourceFiles", "org/jetbrains/kotlin/cli/common/messages/OutputMessageUtil$Output", "<init>"));
        }

        public Output(Collection<File> collection, File file) {
            if (collection == null) {
                $$$reportNull$$$0(0);
            }
            this.sourceFiles = collection;
            this.outputFile = file;
        }
    }
}
