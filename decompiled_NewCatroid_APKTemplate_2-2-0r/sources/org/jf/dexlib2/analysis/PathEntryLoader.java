package org.jf.dexlib2.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.OatFile;
import org.jf.dexlib2.iface.MultiDexContainer;

/* JADX INFO: loaded from: classes6.dex */
public class PathEntryLoader {
    Opcodes opcodes;
    final Set<File> loadedFiles = new HashSet();
    final List<ClassProvider> classProviders = new ArrayList(1);

    public Opcodes getOpcodes() {
        return this.opcodes;
    }

    public List<ClassProvider> getClassProviders() {
        return this.classProviders;
    }

    public PathEntryLoader(Opcodes opcodes) {
        this.opcodes = opcodes;
    }

    public List<ClassProvider> getResolvedClassProviders() {
        return this.classProviders;
    }

    public void loadEntry(File file, boolean z) throws IOException, NoDexException {
        if (this.loadedFiles.contains(file)) {
            return;
        }
        try {
            MultiDexContainer<? extends DexBackedDexFile> multiDexContainerLoadDexContainer = DexFileFactory.loadDexContainer(file, this.opcodes);
            List<String> dexEntryNames = multiDexContainerLoadDexContainer.getDexEntryNames();
            if (dexEntryNames.isEmpty()) {
                throw new NoDexException("%s contains no dex file", file);
            }
            this.loadedFiles.add(file);
            Iterator<String> it = dexEntryNames.iterator();
            while (it.hasNext()) {
                this.classProviders.add(new DexClassProvider(multiDexContainerLoadDexContainer.getEntry(it.next()).getDexFile()));
            }
            if (z && (multiDexContainerLoadDexContainer instanceof OatFile)) {
                List<String> bootClassPath = ((OatFile) multiDexContainerLoadDexContainer).getBootClassPath();
                if (bootClassPath.isEmpty()) {
                    return;
                }
                try {
                    loadOatDependencies(file.getParentFile(), bootClassPath);
                } catch (ClassPathResolver.NotFoundException e) {
                    throw new ClassPathResolver.ResolveException(e, "Error while loading oat file %s", file);
                } catch (NoDexException e2) {
                    throw new ClassPathResolver.ResolveException(e2, "Error while loading dependencies for oat file %s", file);
                }
            }
        } catch (DexFileFactory.UnsupportedFileTypeException e3) {
            throw new ClassPathResolver.ResolveException(e3);
        }
    }

    private void loadOatDependencies(File file, List<String> list) throws ClassPathResolver.NotFoundException, IOException, NoDexException {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String filenameForOatDependency = getFilenameForOatDependency(it.next());
            File file2 = new File(file, filenameForOatDependency);
            if (!file2.exists()) {
                throw new ClassPathResolver.NotFoundException("Cannot find dependency %s in %s", filenameForOatDependency, file);
            }
            loadEntry(file2, false);
        }
    }

    private String getFilenameForOatDependency(String str) {
        String strSubstring = str.substring(str.lastIndexOf(47) + 1);
        return strSubstring.endsWith(".art") ? strSubstring.substring(0, strSubstring.length() - 4) + ".oat" : strSubstring;
    }

    static class NoDexException extends Exception {
        public NoDexException(String str, Object... objArr) {
            super(String.format(str, objArr));
        }
    }
}
