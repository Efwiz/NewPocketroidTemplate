package org.jetbrains.kotlin.library.impl;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.IrKotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0010\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0018\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0010\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000fH\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0016J\u0018\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0018\u0010 \u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0010\u0010!\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u000fH\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0018\u0010#\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0010\u0010$\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0018\u0010%\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0010\u0010&\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fH\u0016R!\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00130\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrPerFileLibraryImpl;", "Lorg/jetbrains/kotlin/library/impl/IrLibraryImpl;", "_access", "Lorg/jetbrains/kotlin/library/impl/IrLibraryAccess;", "Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "(Lorg/jetbrains/kotlin/library/impl/IrLibraryAccess;)V", "directories", "", "Lorg/jetbrains/kotlin/konan/file/File;", "getDirectories", "()Ljava/util/List;", "directories$delegate", "Lkotlin/Lazy;", "fileToBodyMap", "", "", "Lorg/jetbrains/kotlin/library/impl/IrArrayFileReader;", "fileToDebugInfoMap", "fileToDeclarationMap", "Lorg/jetbrains/kotlin/library/impl/DeclarationIrTableFileReader;", "fileToStringMap", "fileToTypeMap", "bodies", "", "fileIndex", "body", "index", "debugInfo", "declarations", "file", "fileCount", "irDeclaration", "signature", "signatureDataReader", "signatures", "string", "strings", ModuleXmlParser.TYPE, SpecTypePair.NAME_types, "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrPerFileLibraryImpl extends IrLibraryImpl {

    /* JADX INFO: renamed from: directories$delegate, reason: from kotlin metadata */
    private final Lazy directories;
    private final Map<Integer, IrArrayFileReader> fileToBodyMap;
    private final Map<Integer, IrArrayFileReader> fileToDebugInfoMap;
    private final Map<Integer, DeclarationIrTableFileReader> fileToDeclarationMap;
    private final Map<Integer, IrArrayFileReader> fileToStringMap;
    private final Map<Integer, IrArrayFileReader> fileToTypeMap;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrPerFileLibraryImpl(IrLibraryAccess<IrKotlinLibraryLayout> _access) {
        super(_access);
        Intrinsics.checkNotNullParameter(_access, "_access");
        this.directories = LazyKt.lazy(new Function0<List<? extends File>>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl$directories$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends File> invoke() {
                return (List) this.this$0.getAccess().realFiles(new Function1<IrKotlinLibraryLayout, List<? extends File>>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl$directories$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final List<File> invoke(IrKotlinLibraryLayout it) throws Exception {
                        Intrinsics.checkNotNullParameter(it, "it");
                        List<File> listFiles = it.getIrDir().getListFiles();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : listFiles) {
                            File file = (File) obj;
                            if (file.isDirectory() && StringsKt.endsWith$default(file.getName(), ".file", false, 2, (Object) null)) {
                                arrayList.add(obj);
                            }
                        }
                        return arrayList;
                    }
                });
            }
        });
        this.fileToDeclarationMap = new LinkedHashMap();
        this.fileToTypeMap = new LinkedHashMap();
        this.fileToStringMap = new LinkedHashMap();
        this.fileToBodyMap = new LinkedHashMap();
        this.fileToDebugInfoMap = new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<File> getDirectories() {
        return (List) this.directories.getValue();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] irDeclaration(int index, int fileIndex) {
        Map<Integer, DeclarationIrTableFileReader> map = this.fileToDeclarationMap;
        Integer numValueOf = Integer.valueOf(fileIndex);
        DeclarationIrTableFileReader declarationIrTableFileReader = map.get(numValueOf);
        if (declarationIrTableFileReader == null) {
            final File file = getDirectories().get(fileIndex);
            declarationIrTableFileReader = new DeclarationIrTableFileReader((File) getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl$irDeclaration$dataReader$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final File invoke(IrKotlinLibraryLayout it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.irDeclarations(file);
                }
            }));
            map.put(numValueOf, declarationIrTableFileReader);
        }
        return declarationIrTableFileReader.tableItemBytes(new DeclarationId(index));
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] type(int index, int fileIndex) {
        Map<Integer, IrArrayFileReader> map = this.fileToTypeMap;
        Integer numValueOf = Integer.valueOf(fileIndex);
        IrArrayFileReader irArrayFileReader = map.get(numValueOf);
        if (irArrayFileReader == null) {
            final File file = getDirectories().get(fileIndex);
            irArrayFileReader = new IrArrayFileReader((File) getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl$type$dataReader$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final File invoke(IrKotlinLibraryLayout it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.irTypes(file);
                }
            }));
            map.put(numValueOf, irArrayFileReader);
        }
        return irArrayFileReader.tableItemBytes(index);
    }

    private final IrArrayFileReader signatureDataReader(int fileIndex) {
        Map<Integer, IrArrayFileReader> map = this.fileToTypeMap;
        Integer numValueOf = Integer.valueOf(fileIndex);
        IrArrayFileReader irArrayFileReader = map.get(numValueOf);
        if (irArrayFileReader == null) {
            final File file = getDirectories().get(fileIndex);
            irArrayFileReader = new IrArrayFileReader((File) getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl$signatureDataReader$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final File invoke(IrKotlinLibraryLayout it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.irSignatures(file);
                }
            }));
            map.put(numValueOf, irArrayFileReader);
        }
        return irArrayFileReader;
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signature(int index, int fileIndex) {
        return signatureDataReader(fileIndex).tableItemBytes(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] string(int index, int fileIndex) {
        Map<Integer, IrArrayFileReader> map = this.fileToStringMap;
        Integer numValueOf = Integer.valueOf(fileIndex);
        IrArrayFileReader irArrayFileReader = map.get(numValueOf);
        if (irArrayFileReader == null) {
            final File file = getDirectories().get(fileIndex);
            irArrayFileReader = new IrArrayFileReader((File) getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl$string$dataReader$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final File invoke(IrKotlinLibraryLayout it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.irStrings(file);
                }
            }));
            map.put(numValueOf, irArrayFileReader);
        }
        return irArrayFileReader.tableItemBytes(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] body(int index, int fileIndex) {
        Map<Integer, IrArrayFileReader> map = this.fileToBodyMap;
        Integer numValueOf = Integer.valueOf(fileIndex);
        IrArrayFileReader irArrayFileReader = map.get(numValueOf);
        if (irArrayFileReader == null) {
            final File file = getDirectories().get(fileIndex);
            irArrayFileReader = new IrArrayFileReader((File) getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl$body$dataReader$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final File invoke(IrKotlinLibraryLayout it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.irBodies(file);
                }
            }));
            map.put(numValueOf, irArrayFileReader);
        }
        return irArrayFileReader.tableItemBytes(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] debugInfo(int index, int fileIndex) {
        Map<Integer, IrArrayFileReader> map = this.fileToDebugInfoMap;
        Integer numValueOf = Integer.valueOf(fileIndex);
        IrArrayFileReader irArrayFileReader = map.get(numValueOf);
        if (irArrayFileReader == null) {
            final File file = getDirectories().get(fileIndex);
            irArrayFileReader = (IrArrayFileReader) getAccess().realFiles(new Function1<IrKotlinLibraryLayout, IrArrayFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl$debugInfo$dataReader$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrArrayFileReader invoke(IrKotlinLibraryLayout it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    File fileIrDebugInfo = it.irDebugInfo(file);
                    if (fileIrDebugInfo.getExists()) {
                        return new IrArrayFileReader(fileIrDebugInfo);
                    }
                    return null;
                }
            });
            map.put(numValueOf, irArrayFileReader);
        }
        IrArrayFileReader irArrayFileReader2 = irArrayFileReader;
        if (irArrayFileReader2 != null) {
            return irArrayFileReader2.tableItemBytes(index);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] file(final int index) {
        Object objRealFiles = getAccess().realFiles(new Function1<IrKotlinLibraryLayout, byte[]>() { // from class: org.jetbrains.kotlin.library.impl.IrPerFileLibraryImpl.file.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final byte[] invoke(IrKotlinLibraryLayout it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.irFile((File) IrPerFileLibraryImpl.this.getDirectories().get(index)).readBytes();
            }
        });
        Intrinsics.checkNotNullExpressionValue(objRealFiles, "override fun file(index:…adBytes()\n        }\n    }");
        return (byte[]) objRealFiles;
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public int fileCount() {
        return getDirectories().size();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] types(int fileIndex) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signatures(int fileIndex) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] strings(int fileIndex) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] declarations(int fileIndex) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] bodies(int fileIndex) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
