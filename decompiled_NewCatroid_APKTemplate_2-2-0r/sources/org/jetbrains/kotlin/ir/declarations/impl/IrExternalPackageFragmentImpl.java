package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.symbols.IrExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrExternalPackageFragmentSymbolImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrExternalPackageFragmentImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 #2\u00020\u0001:\u0001#B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001c\u0010\n\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u0015R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrExternalPackageFragmentImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;Lorg/jetbrains/kotlin/name/FqName;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource$annotations", "()V", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "declarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getDeclarations", "()Ljava/util/List;", "endOffset", "", "getEndOffset", "()I", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setPackageFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "packageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getPackageFragmentDescriptor$annotations", "getPackageFragmentDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "startOffset", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrExternalPackageFragmentImpl extends IrExternalPackageFragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<IrDeclaration> declarations;
    private FqName packageFqName;
    private final IrExternalPackageFragmentSymbol symbol;

    public static /* synthetic */ void getContainerSource$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getPackageFragmentDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return -1;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return -1;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrExternalPackageFragmentSymbol getSymbol() {
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

    public IrExternalPackageFragmentImpl(IrExternalPackageFragmentSymbol symbol, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        this.symbol = symbol;
        this.packageFqName = packageFqName;
        getSymbol().bind(this);
        this.declarations = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPackageFragment
    public PackageFragmentDescriptor getPackageFragmentDescriptor() {
        return getSymbol().getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer
    public List<IrDeclaration> getDeclarations() {
        return this.declarations;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment
    public DeserializedContainerSource getContainerSource() {
        PackageFragmentDescriptor descriptor = getSymbol().getDescriptor();
        DeserializedMemberDescriptor deserializedMemberDescriptor = descriptor instanceof DeserializedMemberDescriptor ? (DeserializedMemberDescriptor) descriptor : null;
        if (deserializedMemberDescriptor != null) {
            return deserializedMemberDescriptor.getContainerSource();
        }
        return null;
    }

    /* JADX INFO: compiled from: IrExternalPackageFragmentImpl.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrExternalPackageFragmentImpl$Companion;", "", "()V", "createEmptyExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IrExternalPackageFragment createEmptyExternalPackageFragment(ModuleDescriptor module, FqName fqName) {
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return new IrExternalPackageFragmentImpl(new IrExternalPackageFragmentSymbolImpl(new EmptyPackageFragmentDescriptor(module, fqName)), fqName);
        }
    }
}
