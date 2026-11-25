package org.jf.baksmali;

import com.beust.jcommander.Parameter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jf.dexlib2.VersionMap;
import org.jf.dexlib2.analysis.ClassPath;
import org.jf.dexlib2.analysis.ClassPathResolver;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.OatFile;
import org.jf.dexlib2.iface.MultiDexContainer;
import org.jf.util.collection.EmptyList;
import org.jf.util.jcommander.ColonParameterSplitter;
import org.jf.util.jcommander.ExtendedParameter;

/* JADX INFO: loaded from: classes6.dex */
public class AnalysisArguments {

    @ExtendedParameter(argumentNames = {ModuleXmlParser.CLASSPATH})
    @Parameter(description = "A colon separated list of the files to include in the bootclasspath when analyzing the dex file. If not specified, baksmali will attempt to choose an appropriate default. When analyzing oat files, this can simply be the path to the device's boot.oat file. A single empty string can be used to specify that an empty bootclasspath should be used. (e.g. --bootclasspath \"\") See baksmali help classpath for more information.", names = {"-b", "--bootclasspath", "--bcp"}, splitter = ColonParameterSplitter.class)
    public List<String> bootClassPath = null;

    @ExtendedParameter(argumentNames = {ModuleXmlParser.CLASSPATH})
    @Parameter(description = "A colon separated list of additional files to include in the classpath when analyzing the dex file. These will be added to the classpath after any bootclasspath entries.", names = {"-c", "--classpath", "--cp"}, splitter = ColonParameterSplitter.class)
    public List<String> classPath = new ArrayList();

    @ExtendedParameter(argumentNames = {"dir"})
    @Parameter(description = "A directory to search for classpath files. This option can be used multiple times to specify multiple directories to search. They will be searched in the order they are provided.", names = {"-d", "--classpath-dir", "--cpd", "--dir"})
    public List<String> classPathDirectories = null;

    public static class CheckPackagePrivateArgument {

        @Parameter(description = "Use the package-private access check when calculating vtable indexes. This is enabled by default for oat files. For odex files, this is only needed for odexes from 4.2.0. It was reverted in 4.2.1.", names = {"--check-package-private-access", "--package-private", "--checkpp", "--pp"})
        public boolean checkPackagePrivateAccess = false;
    }

    public ClassPath loadClassPathForDexFile(File file, MultiDexContainer.DexEntry<? extends DexBackedDexFile> dexEntry, boolean z) throws IOException {
        return loadClassPathForDexFile(file, dexEntry, z, -2);
    }

    public ClassPath loadClassPathForDexFile(File file, MultiDexContainer.DexEntry<? extends DexBackedDexFile> dexEntry, boolean z, int i) throws IOException {
        ClassPathResolver classPathResolver;
        Object container = dexEntry.getContainer();
        if (i != -2) {
            z = true;
        } else if (container instanceof OatFile) {
            i = ((OatFile) container).getOatVersion();
            z = true;
        } else {
            i = VersionMap.mapApiToArtVersion(((DexBackedDexFile) dexEntry.getDexFile()).getOpcodes().api);
        }
        List<String> list = this.classPathDirectories;
        if (list == null || list.size() == 0) {
            ArrayList arrayList = new ArrayList(1);
            this.classPathDirectories = arrayList;
            arrayList.add(file.getPath());
        }
        ArrayList arrayList2 = new ArrayList();
        List<String> list2 = this.classPathDirectories;
        if (list2 != null) {
            for (String str : list2) {
                File file2 = new File(str);
                if (!file2.exists()) {
                    System.err.println(String.format("Warning: directory %s does not exist. Ignoring.", str));
                } else if (!file2.isDirectory()) {
                    System.err.println(String.format("Warning: %s is not a directory. Ignoring.", str));
                } else {
                    arrayList2.add(str);
                }
            }
        }
        List<String> list3 = this.bootClassPath;
        if (list3 == null) {
            classPathResolver = new ClassPathResolver(arrayList2, this.classPath, dexEntry);
        } else if (list3.size() == 1 && this.bootClassPath.get(0).length() == 0) {
            classPathResolver = new ClassPathResolver(EmptyList.of(), EmptyList.of(), this.classPath, dexEntry);
        } else {
            classPathResolver = new ClassPathResolver(arrayList2, this.bootClassPath, this.classPath, dexEntry);
        }
        if (i == 0 && (container instanceof OatFile)) {
            i = ((OatFile) container).getOatVersion();
        }
        return new ClassPath(classPathResolver.getResolvedClassProviders(), z, i);
    }
}
