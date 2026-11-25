package org.jf.smali;

import java.io.IOException;
import java.io.StringReader;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.util.collection.Iterables;

/* JADX INFO: loaded from: classes6.dex */
public class SmaliTestUtils {
    public static DexBackedClassDef compileSmali(String str) throws RecognitionException, IOException {
        return compileSmali(str, 15);
    }

    public static DexBackedClassDef compileSmali(String str, int i) throws RecognitionException, IOException {
        DexBuilder dexBuilder = new DexBuilder(Opcodes.forApi(i));
        smaliFlexLexer smaliflexlexer = new smaliFlexLexer(new StringReader(str), i);
        CommonTokenStream commonTokenStream = new CommonTokenStream(smaliflexlexer);
        smaliParser smaliparser = new smaliParser(commonTokenStream);
        smaliparser.setVerboseErrors(true);
        smaliparser.setAllowOdex(false);
        smaliparser.setApiLevel(i);
        smaliParser.smali_file_return smali_file_returnVarSmali_file = smaliparser.smali_file();
        if (smaliparser.getNumberOfSyntaxErrors() > 0 || smaliflexlexer.getNumberOfSyntaxErrors() > 0) {
            throw new RuntimeException("Error occurred while compiling text");
        }
        CommonTreeNodeStream commonTreeNodeStream = new CommonTreeNodeStream(smali_file_returnVarSmali_file.getTree());
        commonTreeNodeStream.setTokenStream(commonTokenStream);
        smaliTreeWalker smalitreewalker = new smaliTreeWalker(commonTreeNodeStream);
        smalitreewalker.setApiLevel(i);
        smalitreewalker.setVerboseErrors(true);
        smalitreewalker.setDexBuilder(dexBuilder);
        smalitreewalker.smali_file();
        if (smalitreewalker.getNumberOfSyntaxErrors() > 0) {
            throw new RuntimeException("Error occurred while compiling text");
        }
        MemoryDataStore memoryDataStore = new MemoryDataStore();
        dexBuilder.writeTo(memoryDataStore);
        return (DexBackedClassDef) Iterables.getFirst(new DexBackedDexFile(Opcodes.forApi(i), memoryDataStore.getBuffer()).getClasses(), null);
    }
}
