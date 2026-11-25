package com.reandroid.apkeditor.smali;

import com.android.dx.cf.attrib.AttSourceDebugExtension;
import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.DexDecoder;
import com.reandroid.apk.DexFileInputSource;
import com.reandroid.apkeditor.APKEditor;
import com.reandroid.apkeditor.decompile.DecompileOptions;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.dex.common.AnnotationVisibility;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.model.DexDirectory;
import com.reandroid.dex.model.DexFile;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.SmaliWriterSetting;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayCollection;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import org.jf.baksmali.Baksmali;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;

/* JADX INFO: loaded from: classes10.dex */
public class SmaliDecompiler implements DexDecoder {
    private APKLogger apkLogger;
    private final DecompileOptions decompileOptions;
    private ResourceComment mComment;
    private final TableBlock tableBlock;

    public SmaliDecompiler(TableBlock tableBlock, DecompileOptions decompileOptions) {
        this.tableBlock = tableBlock;
        this.decompileOptions = decompileOptions;
    }

    @Deprecated
    public SmaliDecompiler(TableBlock tableBlock) {
        this(tableBlock, new DecompileOptions());
    }

    @Override // com.reandroid.apk.DexDecoder
    public void decodeDex(DexFileInputSource dexFileInputSource, File file) throws IOException {
        logMessage("Baksmali: " + dexFileInputSource.getAlias());
        if (APKEditor.isExperimental()) {
            disassembleDexFileExperimental(dexFileInputSource, file);
        } else {
            disassembleJesusFreke(dexFileInputSource, file);
        }
        writeDexCache(dexFileInputSource, file);
    }

    @Override // com.reandroid.apk.DexDecoder
    public void decodeDex(ApkModule apkModule, File file) throws IOException {
        if (!APKEditor.isExperimental()) {
            super.decodeDex(apkModule, file);
            return;
        }
        DexDirectory dexDirectoryFromZip = (DexDirectory) apkModule.getTag(DexDirectory.class);
        if (dexDirectoryFromZip == null) {
            if (!canLoadFullDex(apkModule)) {
                super.decodeDex(apkModule, file);
                return;
            } else {
                logMessage("Loading full dex ...");
                dexDirectoryFromZip = DexDirectory.fromZip(apkModule.getZipEntryMap());
            }
        }
        if (this.decompileOptions.noDexDebug) {
            logMessage("Clean debug info ...");
            clearDebug_DirtyMethod(dexDirectoryFromZip);
            dexDirectoryFromZip.refresh();
        }
        logMessage("Dumping smali ...");
        File smaliRoot = toSmaliRoot(file);
        SmaliWriterSetting smaliWriterSetting = new SmaliWriterSetting();
        smaliWriterSetting.setResourceIdComment(this.tableBlock.pickOne());
        smaliWriterSetting.addClassComments(dexDirectoryFromZip);
        smaliWriterSetting.addMethodComments(dexDirectoryFromZip);
        SmaliWriter smaliWriter = new SmaliWriter();
        smaliWriter.setWriterSetting(smaliWriterSetting);
        logMessage("Baksmali ...");
        dexDirectoryFromZip.writeSmali(smaliWriter, smaliRoot);
        dexDirectoryFromZip.close();
        Iterator<DexFileInputSource> it = apkModule.listDexFiles().iterator();
        while (it.hasNext()) {
            writeDexCache(it.next(), file);
        }
    }

    private boolean canLoadFullDex(ApkModule apkModule) {
        int size = apkModule.listDexFiles().size();
        logMessage("Total dex files: " + size);
        if (size <= 5) {
            return true;
        }
        logMessage("Huge classes your memory might not handle it, decoding separately without advanced features. You can disable this restrictions by increasing \"CLASSES_LIMIT\" variable here on source code");
        return false;
    }

    private void disassembleJesusFreke(DexFileInputSource dexFileInputSource, File file) throws IOException {
        File outDir = toOutDir(dexFileInputSource, file);
        BaksmaliOptions baksmaliOptions = new BaksmaliOptions();
        baksmaliOptions.localsDirective = true;
        baksmaliOptions.sequentialLabels = true;
        baksmaliOptions.skipDuplicateLineNumbers = true;
        baksmaliOptions.debugInfo = !this.decompileOptions.noDexDebug;
        baksmaliOptions.dumpMarkers = this.decompileOptions.dexMarkers;
        baksmaliOptions.setCommentProvider(getComment());
        Baksmali.disassembleDexFile(getInputDexFile(dexFileInputSource, baksmaliOptions), outDir, 1, baksmaliOptions);
    }

    private void disassembleDexFileExperimental(DexFileInputSource dexFileInputSource, File file) throws IOException {
        DexFile dexFile = DexFile.read(dexFileInputSource.openStream());
        dexFile.setSimpleName(dexFileInputSource.getAlias());
        if (this.decompileOptions.noDexDebug) {
            logMessage("Clean debug info ...");
            clearDebug_DirtyMethod(dexFile);
            dexFile.refresh();
        }
        SmaliWriterSetting smaliWriterSetting = new SmaliWriterSetting();
        smaliWriterSetting.setResourceIdComment(this.tableBlock.pickOne());
        smaliWriterSetting.addClassComments(dexFile);
        smaliWriterSetting.addMethodComments(dexFile);
        SmaliWriter smaliWriter = new SmaliWriter();
        smaliWriter.setWriterSetting(smaliWriterSetting);
        dexFile.writeSmali(smaliWriter, toSmaliRoot(file));
        dexFile.close();
    }

    private void clearDebug_DirtyMethod(DexClassRepository dexClassRepository) {
        dexClassRepository.clearDebug();
        HashSet hashSet = new HashSet();
        Iterator items = dexClassRepository.getItems(SectionType.ANNOTATION_ITEM);
        while (items.hasNext()) {
            AnnotationItem annotationItem = (AnnotationItem) items.next();
            TypeKey typeKey = annotationItem.getTypeKey();
            if (annotationItem.getVisibility() == AnnotationVisibility.BUILD || removeAnnotation(typeKey)) {
                hashSet.add(typeKey);
            }
        }
        ArrayCollection<TypeKey> arrayCollection = new ArrayCollection(hashSet);
        arrayCollection.sort(CompareUtil.getComparableComparator());
        for (TypeKey typeKey2 : arrayCollection) {
            logMessage("   Removed @: " + typeKey2);
            dexClassRepository.removeAnnotations(typeKey2);
        }
    }

    private boolean removeAnnotation(TypeKey typeKey) {
        String typeName = typeKey.getTypeName();
        if (typeName.startsWith("Lkotlin") || typeName.startsWith("Ljava/") || typeName.startsWith("Ljavax/") || typeName.contains(AttSourceDebugExtension.ATTRIBUTE_NAME)) {
            return true;
        }
        return typeName.contains("Null");
    }

    private void writeDexCache(DexFileInputSource dexFileInputSource, File file) throws IOException {
        dexFileInputSource.write(new File(new File(file, ".cache"), dexFileInputSource.getAlias()));
    }

    private File toOutDir(DexFileInputSource dexFileInputSource, File file) {
        int dexNumber = dexFileInputSource.getDexNumber();
        String str = "classes";
        if (dexNumber != 0) {
            str = "classes" + dexNumber;
        }
        File file2 = new File(toSmaliRoot(file), str);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        return file2;
    }

    private File toSmaliRoot(File file) {
        return new File(file, DexDecoder.SMALI_DIRECTORY_NAME);
    }

    private DexBackedDexFile getInputDexFile(DexFileInputSource dexFileInputSource, BaksmaliOptions baksmaliOptions) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        dexFileInputSource.write(byteArrayOutputStream);
        byteArrayOutputStream.close();
        return new DexBackedDexFile(Opcodes.forApi(baksmaliOptions.apiLevel), byteArrayOutputStream.toByteArray());
    }

    public ResourceComment getComment() {
        if (this.mComment == null && this.tableBlock != null) {
            this.mComment = new ResourceComment(this.tableBlock);
        }
        return this.mComment;
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    private void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }
}
