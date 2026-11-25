package antlr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface JavaCodeGeneratorPrintWriterManager {
    void endMapping();

    void finishOutput() throws IOException;

    Map getSourceMaps();

    PrintWriter setupOutput(Tool tool, Grammar grammar) throws IOException;

    PrintWriter setupOutput(Tool tool, String str) throws IOException;

    void startMapping(int i);

    void startSingleSourceLineMapping(int i);
}
