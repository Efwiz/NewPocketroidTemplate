package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.util.Iterator;
import java.util.List;
import org.jf.baksmali.formatter.BaksmaliFormatter;
import org.jf.dexlib2.iface.reference.Reference;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ListReferencesCommand extends DexInputCommand {

    @Parameter(description = "Show usage information", help = true, names = {"-h", "-?", "--help"})
    private boolean help;
    private final int referenceType;

    public ListReferencesCommand(List<JCommander> list, int i) {
        super(list);
        this.referenceType = i;
    }

    @Override // org.jf.util.jcommander.Command
    public void run() {
        if (this.help || this.inputList == null || this.inputList.isEmpty()) {
            usage();
            return;
        }
        if (this.inputList.size() > 1) {
            System.err.println("Too many files specified");
            usage();
            return;
        }
        loadDexFile(this.inputList.get(0));
        BaksmaliFormatter baksmaliFormatter = new BaksmaliFormatter();
        Iterator<? extends Reference> it = this.dexFile.getReferences(this.referenceType).iterator();
        while (it.hasNext()) {
            System.out.println(baksmaliFormatter.getReference(it.next()));
        }
    }
}
