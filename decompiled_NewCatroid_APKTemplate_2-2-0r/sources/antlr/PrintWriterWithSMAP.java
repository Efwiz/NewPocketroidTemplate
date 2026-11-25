package antlr;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.codegen.inline.SMAP;

/* JADX INFO: loaded from: classes.dex */
public class PrintWriterWithSMAP extends PrintWriter {
    private boolean anythingWrittenSinceMapping;
    private int currentOutputLine;
    private int currentSourceLine;
    private boolean lastPrintCharacterWasCR;
    private boolean mapLines;
    private boolean mapSingleSourceLine;
    private Map sourceMap;

    public PrintWriterWithSMAP(OutputStream outputStream) {
        super(outputStream);
        this.currentOutputLine = 1;
        this.currentSourceLine = 0;
        this.sourceMap = new HashMap();
        this.lastPrintCharacterWasCR = false;
        this.mapLines = false;
        this.mapSingleSourceLine = false;
        this.anythingWrittenSinceMapping = false;
    }

    public PrintWriterWithSMAP(OutputStream outputStream, boolean z) {
        super(outputStream, z);
        this.currentOutputLine = 1;
        this.currentSourceLine = 0;
        this.sourceMap = new HashMap();
        this.lastPrintCharacterWasCR = false;
        this.mapLines = false;
        this.mapSingleSourceLine = false;
        this.anythingWrittenSinceMapping = false;
    }

    public PrintWriterWithSMAP(Writer writer) {
        super(writer);
        this.currentOutputLine = 1;
        this.currentSourceLine = 0;
        this.sourceMap = new HashMap();
        this.lastPrintCharacterWasCR = false;
        this.mapLines = false;
        this.mapSingleSourceLine = false;
        this.anythingWrittenSinceMapping = false;
    }

    public PrintWriterWithSMAP(Writer writer, boolean z) {
        super(writer, z);
        this.currentOutputLine = 1;
        this.currentSourceLine = 0;
        this.sourceMap = new HashMap();
        this.lastPrintCharacterWasCR = false;
        this.mapLines = false;
        this.mapSingleSourceLine = false;
        this.anythingWrittenSinceMapping = false;
    }

    public void startMapping(int i) {
        this.mapLines = true;
        if (i != -888) {
            this.currentSourceLine = i;
        }
    }

    public void startSingleSourceLineMapping(int i) {
        this.mapSingleSourceLine = true;
        this.mapLines = true;
        if (i != -888) {
            this.currentSourceLine = i;
        }
    }

    public void endMapping() {
        mapLine(false);
        this.mapLines = false;
        this.mapSingleSourceLine = false;
    }

    protected void mapLine(boolean z) {
        if (this.mapLines && this.anythingWrittenSinceMapping) {
            Integer num = new Integer(this.currentSourceLine);
            Integer num2 = new Integer(this.currentOutputLine);
            List arrayList = (List) this.sourceMap.get(num);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.sourceMap.put(num, arrayList);
            }
            if (!arrayList.contains(num2)) {
                arrayList.add(num2);
            }
        }
        if (z) {
            this.currentOutputLine++;
        }
        if (!this.mapSingleSourceLine) {
            this.currentSourceLine++;
        }
        this.anythingWrittenSinceMapping = false;
    }

    public void dump(PrintWriter printWriter, String str, String str2) {
        printWriter.println("SMAP");
        printWriter.println(new StringBuffer().append(str).append(SuffixConstants.SUFFIX_STRING_java).toString());
        printWriter.println("G");
        printWriter.println("*S G");
        printWriter.println(SMAP.FILE_SECTION);
        printWriter.println(new StringBuffer("+ 0 ").append(str2).toString());
        printWriter.println(str2);
        printWriter.println(SMAP.LINE_SECTION);
        ArrayList<Integer> arrayList = new ArrayList(this.sourceMap.keySet());
        Collections.sort(arrayList);
        for (Integer num : arrayList) {
            Iterator it = ((List) this.sourceMap.get(num)).iterator();
            while (it.hasNext()) {
                printWriter.println(new StringBuffer().append(num).append(":").append((Integer) it.next()).toString());
            }
        }
        printWriter.println(SMAP.END);
        printWriter.close();
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        int i3 = i + i2;
        for (int i4 = i; i4 < i3; i4++) {
            checkChar(cArr[i4]);
        }
        super.write(cArr, i, i2);
    }

    public void checkChar(int i) {
        if ((this.lastPrintCharacterWasCR && i != 10) || i == 10) {
            mapLine(true);
        } else if (!Character.isWhitespace((char) i)) {
            this.anythingWrittenSinceMapping = true;
        }
        this.lastPrintCharacterWasCR = i == 13;
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(int i) {
        checkChar(i);
        super.write(i);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str, int i, int i2) {
        int i3 = i + i2;
        for (int i4 = i; i4 < i3; i4++) {
            checkChar(str.charAt(i4));
        }
        super.write(str, i, i2);
    }

    @Override // java.io.PrintWriter
    public void println() {
        mapLine(true);
        super.println();
        this.lastPrintCharacterWasCR = false;
    }

    public Map getSourceMap() {
        return this.sourceMap;
    }

    public int getCurrentOutputLine() {
        return this.currentOutputLine;
    }
}
