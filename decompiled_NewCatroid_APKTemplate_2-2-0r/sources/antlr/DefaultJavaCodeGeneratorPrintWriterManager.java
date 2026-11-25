package antlr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX INFO: loaded from: classes.dex */
public class DefaultJavaCodeGeneratorPrintWriterManager implements JavaCodeGeneratorPrintWriterManager {
    private String currentFileName;
    private PrintWriter currentOutput;
    private Grammar grammar;
    private PrintWriterWithSMAP smapOutput;
    private Map sourceMaps = new HashMap();
    private Tool tool;

    @Override // antlr.JavaCodeGeneratorPrintWriterManager
    public PrintWriter setupOutput(Tool tool, Grammar grammar) throws IOException {
        return setupOutput(tool, grammar, null);
    }

    @Override // antlr.JavaCodeGeneratorPrintWriterManager
    public PrintWriter setupOutput(Tool tool, String str) throws IOException {
        return setupOutput(tool, null, str);
    }

    public PrintWriter setupOutput(Tool tool, Grammar grammar, String str) throws IOException {
        this.tool = tool;
        this.grammar = grammar;
        if (str == null) {
            str = grammar.getClassName();
        }
        this.smapOutput = new PrintWriterWithSMAP(tool.openOutputFile(new StringBuffer().append(str).append(SuffixConstants.SUFFIX_STRING_java).toString()));
        this.currentFileName = new StringBuffer().append(str).append(SuffixConstants.SUFFIX_STRING_java).toString();
        PrintWriterWithSMAP printWriterWithSMAP = this.smapOutput;
        this.currentOutput = printWriterWithSMAP;
        return printWriterWithSMAP;
    }

    @Override // antlr.JavaCodeGeneratorPrintWriterManager
    public void startMapping(int i) {
        this.smapOutput.startMapping(i);
    }

    @Override // antlr.JavaCodeGeneratorPrintWriterManager
    public void startSingleSourceLineMapping(int i) {
        this.smapOutput.startSingleSourceLineMapping(i);
    }

    @Override // antlr.JavaCodeGeneratorPrintWriterManager
    public void endMapping() {
        this.smapOutput.endMapping();
    }

    @Override // antlr.JavaCodeGeneratorPrintWriterManager
    public void finishOutput() throws IOException {
        this.currentOutput.close();
        if (this.grammar != null) {
            PrintWriter printWriterOpenOutputFile = this.tool.openOutputFile(new StringBuffer().append(this.grammar.getClassName()).append(".smap").toString());
            String strReplace = this.grammar.getFilename().replace('\\', '/');
            int iLastIndexOf = strReplace.lastIndexOf(47);
            if (iLastIndexOf != -1) {
                strReplace = strReplace.substring(iLastIndexOf + 1);
            }
            this.smapOutput.dump(printWriterOpenOutputFile, this.grammar.getClassName(), strReplace);
            this.sourceMaps.put(this.currentFileName, this.smapOutput.getSourceMap());
        }
        this.currentOutput = null;
    }

    @Override // antlr.JavaCodeGeneratorPrintWriterManager
    public Map getSourceMaps() {
        return this.sourceMaps;
    }

    public int getCurrentOutputLine() {
        return this.smapOutput.getCurrentOutputLine();
    }
}
