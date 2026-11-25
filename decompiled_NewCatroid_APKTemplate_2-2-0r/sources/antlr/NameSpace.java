package antlr;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class NameSpace {
    private String _name;
    private Vector names = new Vector();

    public NameSpace(String str) {
        this._name = new String(str);
        parse(str);
    }

    public String getName() {
        return this._name;
    }

    protected void parse(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "::");
        while (stringTokenizer.hasMoreTokens()) {
            this.names.addElement(stringTokenizer.nextToken());
        }
    }

    void emitDeclarations(PrintWriter printWriter) {
        Enumeration enumerationElements = this.names.elements();
        while (enumerationElements.hasMoreElements()) {
            printWriter.println(new StringBuffer("ANTLR_BEGIN_NAMESPACE(").append((String) enumerationElements.nextElement()).append(Constant.AFTER_QUTO).toString());
        }
    }

    void emitClosures(PrintWriter printWriter) {
        for (int i = 0; i < this.names.size(); i++) {
            printWriter.println("ANTLR_END_NAMESPACE");
        }
    }
}
