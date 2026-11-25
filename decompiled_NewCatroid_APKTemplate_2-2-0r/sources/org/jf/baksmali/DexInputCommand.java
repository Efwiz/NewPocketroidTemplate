package org.jf.baksmali;

import com.android.dex.DexFormat;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.MultiDexContainer;
import org.jf.util.jcommander.Command;
import org.jf.util.jcommander.ExtendedParameter;

/* JADX INFO: loaded from: classes6.dex */
public abstract class DexInputCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    @ExtendedParameter(argumentNames = {"api"})
    @Parameter(description = "The numeric api level of the file being disassembled.", names = {"-a", "--api"})
    public int apiLevel;
    protected MultiDexContainer.DexEntry<? extends DexBackedDexFile> dexEntry;
    protected DexBackedDexFile dexFile;
    protected String inputEntry;
    protected File inputFile;

    @ExtendedParameter(argumentNames = {"file"})
    @Parameter(description = "A dex/apk/oat/odex file. For apk or oat files that contain multiple dex files, you can specify the specific entry to use as if the apk/oat file was a directory. e.g. \"app.apk/classes2.dex\". For more information, see \"baksmali help input\".")
    protected List<String> inputList;

    public DexInputCommand(List<JCommander> list) {
        super(list);
        this.apiLevel = -1;
        this.inputList = new ArrayList();
    }

    protected void loadDexFile(String str) {
        File file = new File(str);
        while (file != null && !file.exists()) {
            file = file.getParentFile();
        }
        boolean z = true;
        if (file == null || !file.exists() || file.isDirectory()) {
            System.err.println("Can't find file: " + str);
            System.exit(1);
        }
        this.inputFile = file;
        String strSubstring = file.getPath().length() < str.length() ? str.substring(file.getPath().length() + 1) : null;
        int i = this.apiLevel;
        Opcodes opcodesForApi = i != -1 ? Opcodes.forApi(i) : null;
        if (strSubstring != null && strSubstring.length() > 0) {
            if (strSubstring.length() > 2 && strSubstring.charAt(0) == '\"' && strSubstring.charAt(strSubstring.length() - 1) == '\"') {
                strSubstring = strSubstring.substring(1, strSubstring.length() - 1);
            } else {
                z = false;
            }
            this.inputEntry = strSubstring;
            try {
                MultiDexContainer.DexEntry<? extends DexBackedDexFile> dexEntryLoadDexEntry = DexFileFactory.loadDexEntry(file, strSubstring, z, opcodesForApi);
                this.dexEntry = dexEntryLoadDexEntry;
                this.dexFile = (DexBackedDexFile) dexEntryLoadDexEntry.getDexFile();
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            MultiDexContainer<? extends DexBackedDexFile> multiDexContainerLoadDexContainer = DexFileFactory.loadDexContainer(file, opcodesForApi);
            if (multiDexContainerLoadDexContainer.getDexEntryNames().size() == 1) {
                MultiDexContainer.DexEntry entry = multiDexContainerLoadDexContainer.getEntry(multiDexContainerLoadDexContainer.getDexEntryNames().get(0));
                this.dexEntry = entry;
                this.dexFile = (DexBackedDexFile) entry.getDexFile();
            } else {
                if (multiDexContainerLoadDexContainer.getDexEntryNames().size() > 1) {
                    MultiDexContainer.DexEntry entry2 = multiDexContainerLoadDexContainer.getEntry(DexFormat.DEX_IN_JAR_NAME);
                    this.dexEntry = entry2;
                    if (entry2 == null) {
                        this.dexEntry = multiDexContainerLoadDexContainer.getEntry(multiDexContainerLoadDexContainer.getDexEntryNames().get(0));
                    }
                    this.dexFile = (DexBackedDexFile) this.dexEntry.getDexFile();
                    return;
                }
                throw new RuntimeException(String.format("\"%s\" has no dex files", str));
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }
}
