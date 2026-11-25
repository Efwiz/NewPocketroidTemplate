package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrFileSymbolImpl;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFileImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0015\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\u000eB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\u000f\u001a\u00020\r¢\u0006\u0002\u0010\u0010R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010\u0007\u001a\u00020\bX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010\u000f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001a\u0010\u0004\u001a\u00020\u00058VX\u0097\u0004¢\u0006\f\u0012\u0004\b2\u00103\u001a\u0004\b4\u00105R\u0014\u00106\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u0010\u001fR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u00109¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrFileImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "fileEntry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "packageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "(Lorg/jetbrains/kotlin/ir/IrFileEntry;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;)V", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "(Lorg/jetbrains/kotlin/ir/IrFileEntry;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/ir/IrFileEntry;Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V", "packageFqName", "(Lorg/jetbrains/kotlin/ir/IrFileEntry;Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;Lorg/jetbrains/kotlin/name/FqName;)V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "declarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getDeclarations", "endOffset", "", "getEndOffset", "()I", "getFileEntry", "()Lorg/jetbrains/kotlin/ir/IrFileEntry;", "setFileEntry", "(Lorg/jetbrains/kotlin/ir/IrFileEntry;)V", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getModule", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "setModule", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setPackageFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "getPackageFragmentDescriptor$annotations", "()V", "getPackageFragmentDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "startOffset", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFileImpl extends IrFile {
    private List<? extends IrConstructorCall> annotations;
    private final List<IrDeclaration> declarations;
    private IrFileEntry fileEntry;
    private MetadataSource metadata;
    public IrModuleFragment module;
    private FqName packageFqName;
    private final IrFileSymbol symbol;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getPackageFragmentDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFile
    public IrFileEntry getFileEntry() {
        return this.fileEntry;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFile
    public void setFileEntry(IrFileEntry irFileEntry) {
        Intrinsics.checkNotNullParameter(irFileEntry, "<set-?>");
        this.fileEntry = irFileEntry;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrFileSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPackageFragment
    public FqName getPackageFqName() {
        return this.packageFqName;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPackageFragment
    public void setPackageFqName(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<set-?>");
        this.packageFqName = fqName;
    }

    public IrFileImpl(IrFileEntry fileEntry, IrFileSymbol symbol, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(fileEntry, "fileEntry");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        this.fileEntry = fileEntry;
        this.symbol = symbol;
        this.packageFqName = packageFqName;
        getSymbol().bind(this);
        this.declarations = new ArrayList();
        this.annotations = CollectionsKt.emptyList();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrFileImpl(IrFileEntry fileEntry, PackageFragmentDescriptor packageFragmentDescriptor) {
        this(fileEntry, new IrFileSymbolImpl(packageFragmentDescriptor), packageFragmentDescriptor.getFqName());
        Intrinsics.checkNotNullParameter(fileEntry, "fileEntry");
        Intrinsics.checkNotNullParameter(packageFragmentDescriptor, "packageFragmentDescriptor");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrFileImpl(IrFileEntry fileEntry, PackageFragmentDescriptor packageFragmentDescriptor, IrModuleFragment module) {
        this(fileEntry, new IrFileSymbolImpl(packageFragmentDescriptor), packageFragmentDescriptor.getFqName(), module);
        Intrinsics.checkNotNullParameter(fileEntry, "fileEntry");
        Intrinsics.checkNotNullParameter(packageFragmentDescriptor, "packageFragmentDescriptor");
        Intrinsics.checkNotNullParameter(module, "module");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrFileImpl(IrFileEntry fileEntry, IrFileSymbol symbol, FqName fqName, IrModuleFragment module) {
        this(fileEntry, symbol, fqName);
        Intrinsics.checkNotNullParameter(fileEntry, "fileEntry");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(module, "module");
        setModule(module);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFile
    public IrModuleFragment getModule() {
        IrModuleFragment irModuleFragment = this.module;
        if (irModuleFragment != null) {
            return irModuleFragment;
        }
        Intrinsics.throwUninitializedPropertyAccessException("module");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFile
    public void setModule(IrModuleFragment irModuleFragment) {
        Intrinsics.checkNotNullParameter(irModuleFragment, "<set-?>");
        this.module = irModuleFragment;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return getFileEntry().getMaxOffset();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPackageFragment
    public PackageFragmentDescriptor getPackageFragmentDescriptor() {
        return getSymbol().getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer
    public List<IrDeclaration> getDeclarations() {
        return this.declarations;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return this.metadata;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        this.metadata = metadataSource;
    }
}
