package org.jetbrains.kotlin.ir.declarations;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.IrElementBase;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.symbols.IrPackageFragmentSymbol;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrPackageFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004R*\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0087\u000e¢\u0006\u0012\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u00020\u0006X¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001a\u0010\u0010\u001a\u00020\u00118&X§\u0004¢\u0006\f\u0012\u0004\b\u0012\u0010\u0004\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "Lorg/jetbrains/kotlin/ir/IrElementBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "()V", "value", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", "getFqName$annotations", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "packageFqName", "getPackageFqName", "setPackageFqName", "packageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getPackageFragmentDescriptor$annotations", "getPackageFragmentDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrPackageFragmentSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPackageFragmentSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrPackageFragment extends IrElementBase implements IrDeclarationContainer, IrSymbolOwner {
    @Deprecated(level = DeprecationLevel.ERROR, message = "Please use `packageFqName` instead", replaceWith = @ReplaceWith(expression = "packageFqName", imports = {}))
    public static /* synthetic */ void getFqName$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getPackageFragmentDescriptor$annotations() {
    }

    public abstract FqName getPackageFqName();

    public abstract PackageFragmentDescriptor getPackageFragmentDescriptor();

    public abstract IrPackageFragmentSymbol getSymbol();

    public abstract void setPackageFqName(FqName fqName);

    public final FqName getFqName() {
        return getPackageFqName();
    }

    public final void setFqName(FqName value) {
        Intrinsics.checkNotNullParameter(value, "value");
        setPackageFqName(value);
    }
}
