package org.jetbrains.kotlin.library.impl;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.IrKotlinLibraryLayout;

/* JADX INFO: compiled from: KotlinLibraryLayoutImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001b\u0010\u0006\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\r\u0010\tR\u001b\u0010\u000f\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000b\u001a\u0004\b\u0010\u0010\tR\u001b\u0010\u0012\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0013\u0010\tR\u001b\u0010\u0015\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0016\u0010\tR\u001b\u0010\u0018\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u0019\u0010\tR\u001b\u0010\u001b\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001c\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/library/impl/ExtractingIrLibraryImpl;", "Lorg/jetbrains/kotlin/library/impl/ExtractingKotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "zipped", "Lorg/jetbrains/kotlin/library/impl/IrLibraryLayoutImpl;", "(Lorg/jetbrains/kotlin/library/impl/IrLibraryLayoutImpl;)V", "irBodies", "Lorg/jetbrains/kotlin/konan/file/File;", "getIrBodies", "()Lorg/jetbrains/kotlin/konan/file/File;", "irBodies$delegate", "Lkotlin/Lazy;", "irDebugInfo", "getIrDebugInfo", "irDebugInfo$delegate", "irDeclarations", "getIrDeclarations", "irDeclarations$delegate", "irFiles", "getIrFiles", "irFiles$delegate", "irSignatures", "getIrSignatures", "irSignatures$delegate", "irStrings", "getIrStrings", "irStrings$delegate", "irTypes", "getIrTypes", "irTypes$delegate", "getZipped", "()Lorg/jetbrains/kotlin/library/impl/IrLibraryLayoutImpl;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ExtractingIrLibraryImpl extends ExtractingKotlinLibraryLayout implements IrKotlinLibraryLayout {

    /* JADX INFO: renamed from: irBodies$delegate, reason: from kotlin metadata */
    private final Lazy irBodies;

    /* JADX INFO: renamed from: irDebugInfo$delegate, reason: from kotlin metadata */
    private final Lazy irDebugInfo;

    /* JADX INFO: renamed from: irDeclarations$delegate, reason: from kotlin metadata */
    private final Lazy irDeclarations;

    /* JADX INFO: renamed from: irFiles$delegate, reason: from kotlin metadata */
    private final Lazy irFiles;

    /* JADX INFO: renamed from: irSignatures$delegate, reason: from kotlin metadata */
    private final Lazy irSignatures;

    /* JADX INFO: renamed from: irStrings$delegate, reason: from kotlin metadata */
    private final Lazy irStrings;

    /* JADX INFO: renamed from: irTypes$delegate, reason: from kotlin metadata */
    private final Lazy irTypes;
    private final IrLibraryLayoutImpl zipped;

    public final IrLibraryLayoutImpl getZipped() {
        return this.zipped;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtractingIrLibraryImpl(IrLibraryLayoutImpl zipped) {
        super(zipped);
        Intrinsics.checkNotNullParameter(zipped, "zipped");
        this.zipped = zipped;
        this.irDeclarations = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingIrLibraryImpl$irDeclarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return KotlinLibraryLayoutImplKt.extract(this.this$0.getZipped(), this.this$0.getZipped().getIrDeclarations());
            }
        });
        this.irTypes = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingIrLibraryImpl$irTypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return KotlinLibraryLayoutImplKt.extract(this.this$0.getZipped(), this.this$0.getZipped().getIrTypes());
            }
        });
        this.irSignatures = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingIrLibraryImpl$irSignatures$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return KotlinLibraryLayoutImplKt.extract(this.this$0.getZipped(), this.this$0.getZipped().getIrSignatures());
            }
        });
        this.irStrings = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingIrLibraryImpl$irStrings$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return KotlinLibraryLayoutImplKt.extract(this.this$0.getZipped(), this.this$0.getZipped().getIrStrings());
            }
        });
        this.irBodies = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingIrLibraryImpl$irBodies$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return KotlinLibraryLayoutImplKt.extract(this.this$0.getZipped(), this.this$0.getZipped().getIrBodies());
            }
        });
        this.irFiles = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingIrLibraryImpl$irFiles$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return KotlinLibraryLayoutImplKt.extract(this.this$0.getZipped(), this.this$0.getZipped().getIrFiles());
            }
        });
        this.irDebugInfo = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.library.impl.ExtractingIrLibraryImpl$irDebugInfo$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return KotlinLibraryLayoutImplKt.extract(this.this$0.getZipped(), this.this$0.getZipped().getIrDebugInfo());
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.IrKotlinLibraryLayout
    public File getIrDeclarations() {
        return (File) this.irDeclarations.getValue();
    }

    @Override // org.jetbrains.kotlin.library.IrKotlinLibraryLayout
    public File getIrTypes() {
        return (File) this.irTypes.getValue();
    }

    @Override // org.jetbrains.kotlin.library.IrKotlinLibraryLayout
    public File getIrSignatures() {
        return (File) this.irSignatures.getValue();
    }

    @Override // org.jetbrains.kotlin.library.IrKotlinLibraryLayout
    public File getIrStrings() {
        return (File) this.irStrings.getValue();
    }

    @Override // org.jetbrains.kotlin.library.IrKotlinLibraryLayout
    public File getIrBodies() {
        return (File) this.irBodies.getValue();
    }

    @Override // org.jetbrains.kotlin.library.IrKotlinLibraryLayout
    public File getIrFiles() {
        return (File) this.irFiles.getValue();
    }

    @Override // org.jetbrains.kotlin.library.IrKotlinLibraryLayout
    public File getIrDebugInfo() {
        return (File) this.irDebugInfo.getValue();
    }
}
