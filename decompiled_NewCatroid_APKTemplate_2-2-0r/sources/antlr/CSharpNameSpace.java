package antlr;

import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class CSharpNameSpace extends NameSpace {
    public CSharpNameSpace(String str) {
        super(str);
    }

    @Override // antlr.NameSpace
    void emitDeclarations(PrintWriter printWriter) {
        printWriter.println(new StringBuffer("namespace ").append(getName()).toString());
        printWriter.println("{");
    }

    @Override // antlr.NameSpace
    void emitClosures(PrintWriter printWriter) {
        printWriter.println("}");
    }
}
