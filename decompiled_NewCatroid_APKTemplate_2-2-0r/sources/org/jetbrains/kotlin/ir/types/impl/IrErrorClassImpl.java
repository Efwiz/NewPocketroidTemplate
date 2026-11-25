package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrErrorClassImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrErrorClassImpl;", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrClassImpl;", "()V", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "parent", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrErrorClassImpl extends IrClassImpl {
    public static final IrErrorClassImpl INSTANCE = new IrErrorClassImpl();

    private IrErrorClassImpl() {
        IrDeclarationOrigin.ERROR_CLASS error_class = IrDeclarationOrigin.ERROR_CLASS.INSTANCE;
        IrClassSymbolImpl irClassSymbolImpl = new IrClassSymbolImpl(null, 1, null);
        Name nameSpecial = Name.special("<error>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        ClassKind classKind = ClassKind.CLASS;
        DescriptorVisibility DEFAULT_VISIBILITY = DescriptorVisibilities.DEFAULT_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY, "DEFAULT_VISIBILITY");
        super(-1, -1, error_class, irClassSymbolImpl, nameSpecial, classKind, DEFAULT_VISIBILITY, Modality.FINAL, false, false, false, false, false, false, false, null, null, 130816, null);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl, org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        return new IrFile() { // from class: org.jetbrains.kotlin.ir.types.impl.IrErrorClassImpl$parent$1
            @ObsoleteDescriptorBasedAPI
            public static /* synthetic */ void getPackageFragmentDescriptor$annotations() {
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
            public void setAnnotations(List<? extends IrConstructorCall> list) {
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 0>");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
            public void setMetadata(MetadataSource metadataSource) {
            }

            @Override // org.jetbrains.kotlin.ir.IrElement
            public int getStartOffset() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.IrElement
            public int getEndOffset() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
            public List<IrConstructorCall> getAnnotations() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer
            public List<IrDeclaration> getDeclarations() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
            public IrFileSymbol getSymbol() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrFile
            public IrModuleFragment getModule() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrFile
            public void setModule(IrModuleFragment irModuleFragment) {
                Intrinsics.checkNotNullParameter(irModuleFragment, "<anonymous parameter 0>");
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrFile
            public IrFileEntry getFileEntry() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrFile
            public void setFileEntry(IrFileEntry irFileEntry) {
                Intrinsics.checkNotNullParameter(irFileEntry, "<anonymous parameter 0>");
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
            public MetadataSource getMetadata() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrPackageFragment
            public PackageFragmentDescriptor getPackageFragmentDescriptor() {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrPackageFragment
            public FqName getPackageFqName() {
                FqName ROOT = FqName.ROOT;
                Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
                return ROOT;
            }

            @Override // org.jetbrains.kotlin.ir.declarations.IrPackageFragment
            public void setPackageFqName(FqName fqName) {
                Intrinsics.checkNotNullParameter(fqName, "<anonymous parameter 0>");
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }
        };
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl, org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<anonymous parameter 0>");
        throw new NotImplementedError(null, 1, null);
    }
}
