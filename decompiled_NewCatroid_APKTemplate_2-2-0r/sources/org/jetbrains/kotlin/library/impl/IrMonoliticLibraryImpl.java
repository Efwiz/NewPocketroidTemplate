package org.jetbrains.kotlin.library.impl;

import com.reandroid.arsc.container.SpecTypePair;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.IrKotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J\u0018\u0010%\u001a\u00020\"2\u0006\u0010&\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0016J\u001a\u0010'\u001a\u0004\u0018\u00010\"2\u0006\u0010&\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010(\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010)\u001a\u00020\"2\u0006\u0010&\u001a\u00020$H\u0016J\b\u0010*\u001a\u00020$H\u0016J\u0018\u0010+\u001a\u00020\"2\u0006\u0010&\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0016J\u0018\u0010,\u001a\u00020\"2\u0006\u0010&\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0002J\u0018\u0010-\u001a\u00020\"2\u0006\u0010&\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010\u0019\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J\u0018\u0010.\u001a\u00020\"2\u0006\u0010&\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010\u001c\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J\u0018\u0010/\u001a\u00020\"2\u0006\u0010&\u001a\u00020$2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010\u001f\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0012\u0010\tR\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000b\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0019\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u000b\u001a\u0004\b\u001a\u0010\tR\u001b\u0010\u001c\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u000b\u001a\u0004\b\u001d\u0010\tR\u001b\u0010\u001f\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u000b\u001a\u0004\b \u0010\t¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrMonoliticLibraryImpl;", "Lorg/jetbrains/kotlin/library/impl/IrLibraryImpl;", "_access", "Lorg/jetbrains/kotlin/library/impl/IrLibraryAccess;", "Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "(Lorg/jetbrains/kotlin/library/impl/IrLibraryAccess;)V", "bodies", "Lorg/jetbrains/kotlin/library/impl/IrMultiArrayFileReader;", "getBodies", "()Lorg/jetbrains/kotlin/library/impl/IrMultiArrayFileReader;", "bodies$delegate", "Lkotlin/Lazy;", "combinedDeclarations", "Lorg/jetbrains/kotlin/library/impl/DeclarationIrMultiTableFileReader;", "getCombinedDeclarations", "()Lorg/jetbrains/kotlin/library/impl/DeclarationIrMultiTableFileReader;", "combinedDeclarations$delegate", "debugInfos", "getDebugInfos", "debugInfos$delegate", "files", "Lorg/jetbrains/kotlin/library/impl/IrArrayFileReader;", "getFiles", "()Lorg/jetbrains/kotlin/library/impl/IrArrayFileReader;", "files$delegate", "signatures", "getSignatures", "signatures$delegate", "strings", "getStrings", "strings$delegate", SpecTypePair.NAME_types, "getTypes", "types$delegate", "", "fileIndex", "", "body", "index", "debugInfo", "declarations", "file", "fileCount", "irDeclaration", "loadIrDeclaration", "signature", "string", ModuleXmlParser.TYPE, "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrMonoliticLibraryImpl extends IrLibraryImpl {

    /* JADX INFO: renamed from: bodies$delegate, reason: from kotlin metadata */
    private final Lazy bodies;

    /* JADX INFO: renamed from: combinedDeclarations$delegate, reason: from kotlin metadata */
    private final Lazy combinedDeclarations;

    /* JADX INFO: renamed from: debugInfos$delegate, reason: from kotlin metadata */
    private final Lazy debugInfos;

    /* JADX INFO: renamed from: files$delegate, reason: from kotlin metadata */
    private final Lazy files;

    /* JADX INFO: renamed from: signatures$delegate, reason: from kotlin metadata */
    private final Lazy signatures;

    /* JADX INFO: renamed from: strings$delegate, reason: from kotlin metadata */
    private final Lazy strings;

    /* JADX INFO: renamed from: types$delegate, reason: from kotlin metadata */
    private final Lazy types;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrMonoliticLibraryImpl(IrLibraryAccess<IrKotlinLibraryLayout> _access) {
        super(_access);
        Intrinsics.checkNotNullParameter(_access, "_access");
        this.combinedDeclarations = LazyKt.lazy(new Function0<DeclarationIrMultiTableFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl$combinedDeclarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DeclarationIrMultiTableFileReader invoke() {
                return new DeclarationIrMultiTableFileReader((File) this.this$0.getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl$combinedDeclarations$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final File invoke(IrKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getIrDeclarations();
                    }
                }));
            }
        });
        this.types = LazyKt.lazy(new Function0<IrMultiArrayFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl.types.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrMultiArrayFileReader invoke() {
                return new IrMultiArrayFileReader((File) IrMonoliticLibraryImpl.this.getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl.types.2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final File invoke(IrKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getIrTypes();
                    }
                }));
            }
        });
        this.signatures = LazyKt.lazy(new Function0<IrMultiArrayFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl.signatures.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrMultiArrayFileReader invoke() {
                return new IrMultiArrayFileReader((File) IrMonoliticLibraryImpl.this.getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl.signatures.2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final File invoke(IrKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getIrSignatures();
                    }
                }));
            }
        });
        this.strings = LazyKt.lazy(new Function0<IrMultiArrayFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl.strings.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrMultiArrayFileReader invoke() {
                return new IrMultiArrayFileReader((File) IrMonoliticLibraryImpl.this.getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl.strings.2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final File invoke(IrKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getIrStrings();
                    }
                }));
            }
        });
        this.bodies = LazyKt.lazy(new Function0<IrMultiArrayFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl.bodies.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrMultiArrayFileReader invoke() {
                return new IrMultiArrayFileReader((File) IrMonoliticLibraryImpl.this.getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl.bodies.2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final File invoke(IrKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getIrBodies();
                    }
                }));
            }
        });
        this.debugInfos = LazyKt.lazy(new Function0<IrMultiArrayFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl$debugInfos$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrMultiArrayFileReader invoke() {
                return (IrMultiArrayFileReader) this.this$0.getAccess().realFiles(new Function1<IrKotlinLibraryLayout, IrMultiArrayFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl$debugInfos$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final IrMultiArrayFileReader invoke(IrKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        File irDebugInfo = it.getIrDebugInfo();
                        if (irDebugInfo.getExists()) {
                            return new IrMultiArrayFileReader(irDebugInfo);
                        }
                        return null;
                    }
                });
            }
        });
        this.files = LazyKt.lazy(new Function0<IrArrayFileReader>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl$files$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrArrayFileReader invoke() {
                return new IrArrayFileReader((File) this.this$0.getAccess().realFiles(new Function1<IrKotlinLibraryLayout, File>() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl$files$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final File invoke(IrKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getIrFiles();
                    }
                }));
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public int fileCount() {
        return getFiles().entryCount();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] irDeclaration(int index, int fileIndex) {
        return loadIrDeclaration(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] type(int index, int fileIndex) {
        return getTypes().tableItemBytes(fileIndex, index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signature(int index, int fileIndex) {
        return getSignatures().tableItemBytes(fileIndex, index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] string(int index, int fileIndex) {
        return getStrings().tableItemBytes(fileIndex, index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] body(int index, int fileIndex) {
        return getBodies().tableItemBytes(fileIndex, index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] debugInfo(int index, int fileIndex) {
        IrMultiArrayFileReader debugInfos = getDebugInfos();
        if (debugInfos != null) {
            return debugInfos.tableItemBytes(fileIndex, index);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] file(int index) {
        return getFiles().tableItemBytes(index);
    }

    private final byte[] loadIrDeclaration(int index, int fileIndex) {
        return getCombinedDeclarations().tableItemBytes(fileIndex, new DeclarationId(index));
    }

    private final DeclarationIrMultiTableFileReader getCombinedDeclarations() {
        return (DeclarationIrMultiTableFileReader) this.combinedDeclarations.getValue();
    }

    private final IrMultiArrayFileReader getTypes() {
        return (IrMultiArrayFileReader) this.types.getValue();
    }

    private final IrMultiArrayFileReader getSignatures() {
        return (IrMultiArrayFileReader) this.signatures.getValue();
    }

    private final IrMultiArrayFileReader getStrings() {
        return (IrMultiArrayFileReader) this.strings.getValue();
    }

    private final IrMultiArrayFileReader getBodies() {
        return (IrMultiArrayFileReader) this.bodies.getValue();
    }

    private final IrMultiArrayFileReader getDebugInfos() {
        return (IrMultiArrayFileReader) this.debugInfos.getValue();
    }

    private final IrArrayFileReader getFiles() {
        return (IrArrayFileReader) this.files.getValue();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] types(int fileIndex) {
        return getTypes().tableItemBytes(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signatures(int fileIndex) {
        return getSignatures().tableItemBytes(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] strings(int fileIndex) {
        return getStrings().tableItemBytes(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] declarations(int fileIndex) {
        return getCombinedDeclarations().tableItemBytes(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] bodies(int fileIndex) {
        return getBodies().tableItemBytes(fileIndex);
    }
}
