package org.jf.smali;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.extra.DexMarker;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.FileDataStore;
import org.jf.util.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
public class Smali {
    public static boolean assemble(SmaliOptions smaliOptions, String... strArr) throws IOException {
        return assemble(smaliOptions, (List<String>) Arrays.asList(strArr));
    }

    public static boolean assemble(SmaliOptions smaliOptions, List<String> list) throws IOException {
        DexBuilder dexBuilder = new DexBuilder(Opcodes.forApi(smaliOptions.apiLevel));
        if (smaliOptions.markersListFile != null) {
            dexBuilder.addMarkers(DexMarker.readMarkers(new File(smaliOptions.markersListFile)).iterator());
        }
        boolean z = false;
        for (String str : list) {
            File file = new File(str);
            if (!file.exists()) {
                throw new IOException("Cannot find file or directory \"" + str + "\"");
            }
            if (assemble(dexBuilder, file, smaliOptions)) {
                z = true;
            }
        }
        if (!z) {
            return false;
        }
        dexBuilder.writeTo(new FileDataStore(new File(smaliOptions.outputDexFile)));
        return true;
    }

    private static boolean assemble(DexBuilder dexBuilder, File file, SmaliOptions smaliOptions) throws IOException {
        if (file.isFile()) {
            if (file.getName().endsWith(".smali")) {
                return assembleSmaliFile(file, dexBuilder, smaliOptions);
            }
            return true;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return false;
        }
        boolean z = false;
        for (File file2 : fileArrListFiles) {
            if (!assemble(dexBuilder, file2, smaliOptions)) {
                z = true;
            }
        }
        return !z;
    }

    public static boolean printTokens(SmaliOptions smaliOptions, List<String> list) throws IOException {
        TreeSet treeSet = new TreeSet();
        for (String str : list) {
            File file = new File(str);
            if (!file.exists()) {
                throw new IllegalArgumentException("Cannot find file or directory \"" + str + "\"");
            }
            if (file.isDirectory()) {
                getSmaliFilesInDir(file, treeSet);
            } else if (file.isFile()) {
                treeSet.add(file);
            }
        }
        Iterator it = treeSet.iterator();
        boolean z = false;
        while (it.hasNext()) {
            try {
                z |= !printTokensForSingleFile((File) it.next(), smaliOptions);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return !z;
    }

    private static void getSmaliFilesInDir(File file, Set<File> set) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    getSmaliFilesInDir(file2, set);
                } else if (file2.getName().endsWith(".smali")) {
                    set.add(file2);
                }
            }
        }
    }

    private static boolean assembleSmaliFile(File file, DexBuilder dexBuilder, SmaliOptions smaliOptions) throws Throwable {
        String str;
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                smaliFlexLexer smaliflexlexer = new smaliFlexLexer(new InputStreamReader(fileInputStream2, "UTF-8"), smaliOptions.apiLevel);
                smaliflexlexer.setSourceFile(file);
                CommonTokenStream commonTokenStream = new CommonTokenStream(smaliflexlexer);
                if (smaliOptions.printTokens) {
                    commonTokenStream.getTokens();
                    for (int i = 0; i < commonTokenStream.size(); i++) {
                        Token token = commonTokenStream.get(i);
                        if (token.getChannel() != 99) {
                            if (token.getType() == -1) {
                                str = "EOF";
                            } else {
                                str = smaliParser.tokenNames[token.getType()];
                            }
                            System.out.println(str + ": " + token.getText());
                        }
                    }
                    System.out.flush();
                }
                smaliParser smaliparser = new smaliParser(commonTokenStream);
                smaliparser.setVerboseErrors(smaliOptions.verboseErrors);
                smaliparser.setAllowOdex(smaliOptions.allowOdexOpcodes);
                smaliparser.setApiLevel(smaliOptions.apiLevel);
                try {
                    smaliParser.smali_file_return smali_file_returnVarSmali_file = smaliparser.smali_file();
                    if (smaliparser.getNumberOfSyntaxErrors() <= 0 && smaliflexlexer.getNumberOfSyntaxErrors() <= 0) {
                        CommonTree tree = smali_file_returnVarSmali_file.getTree();
                        CommonTreeNodeStream commonTreeNodeStream = new CommonTreeNodeStream(tree);
                        commonTreeNodeStream.setTokenStream(commonTokenStream);
                        if (smaliOptions.printTokens) {
                            System.out.println(tree.toStringTree());
                        }
                        smaliTreeWalker smalitreewalker = new smaliTreeWalker(commonTreeNodeStream);
                        smalitreewalker.setApiLevel(smaliOptions.apiLevel);
                        smalitreewalker.setVerboseErrors(smaliOptions.verboseErrors);
                        smalitreewalker.setDexBuilder(dexBuilder);
                        try {
                            smalitreewalker.smali_file();
                            boolean z = smalitreewalker.getNumberOfSyntaxErrors() == 0;
                            fileInputStream2.close();
                            return z;
                        } catch (RecognitionException e) {
                            throw new IOException(e);
                        }
                    }
                    fileInputStream2.close();
                    return false;
                } catch (RecognitionException e2) {
                    throw new IOException(e2);
                }
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static boolean printTokensForSingleFile(File file, SmaliOptions smaliOptions) throws Exception {
        String str;
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                smaliFlexLexer smaliflexlexer = new smaliFlexLexer(new InputStreamReader(fileInputStream2, "UTF-8"), smaliOptions.apiLevel);
                smaliflexlexer.setSourceFile(file);
                CommonTokenStream commonTokenStream = new CommonTokenStream(smaliflexlexer);
                commonTokenStream.fill();
                for (int i = 0; i < commonTokenStream.size(); i++) {
                    Token token = commonTokenStream.get(i);
                    if (token.getChannel() != 99) {
                        if (token.getType() == -1) {
                            str = "EOF";
                        } else {
                            str = smaliParser.tokenNames[token.getType()];
                        }
                        System.out.println(str + "(\"" + StringUtils.escapeString(token.getText()) + "\")");
                    }
                }
                System.out.flush();
                boolean z = smaliflexlexer.getNumberOfSyntaxErrors() == 0;
                fileInputStream2.close();
                return z;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
