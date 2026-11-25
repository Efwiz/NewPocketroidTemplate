package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrModuleFragmentImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrModuleFragmentImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "irBuiltins", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "files", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Ljava/util/List;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "", "getFiles", "()Ljava/util/List;", "getIrBuiltins", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrModuleFragmentImpl extends IrModuleFragment {
    private final ModuleDescriptor descriptor;
    private final List<IrFile> files;
    private final IrBuiltIns irBuiltins;

    @Override // org.jetbrains.kotlin.ir.declarations.IrModuleFragment
    public ModuleDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrModuleFragment
    public IrBuiltIns getIrBuiltins() {
        return this.irBuiltins;
    }

    public /* synthetic */ IrModuleFragmentImpl(ModuleDescriptor moduleDescriptor, IrBuiltIns irBuiltIns, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, irBuiltIns, (i & 4) != 0 ? CollectionsKt.emptyList() : list);
    }

    public IrModuleFragmentImpl(ModuleDescriptor descriptor, IrBuiltIns irBuiltins, List<? extends IrFile> files) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(irBuiltins, "irBuiltins");
        Intrinsics.checkNotNullParameter(files, "files");
        this.descriptor = descriptor;
        this.irBuiltins = irBuiltins;
        this.files = CollectionsKt.toMutableList((Collection) files);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrModuleFragment
    public Name getName() {
        Name name2 = getDescriptor().getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrModuleFragment
    public List<IrFile> getFiles() {
        return this.files;
    }
}
