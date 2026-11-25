package org.jetbrains.kotlin.backend.common.linkage.partial;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy;
import org.jetbrains.kotlin.ir.linkage.partial.PartiallyLinkedDeclarationOrigin;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: IrUnimplementedOverridesStrategy.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J7\u0010\u000e\u001a\u00020\u000f\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\u000e\b\u0001\u0010\u0012*\b\u0012\u0004\u0012\u0002H\u00100\u00132\u0006\u0010\u0014\u001a\u0002H\u00122\u0006\u0010\u0015\u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010\u0016J7\u0010\u0017\u001a\u00020\u0018\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\u000e\b\u0001\u0010\u0012*\b\u0012\u0004\u0012\u0002H\u00100\u00132\u0006\u0010\u0014\u001a\u0002H\u00122\u0006\u0010\u0015\u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010\u0019J+\u0010\u001a\u001a\u00020\u0006\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\u000e\b\u0001\u0010\u0012*\b\u0012\u0004\u0012\u0002H\u00100\u0013*\u0002H\u0012H\u0002¢\u0006\u0002\u0010\u001bJ\f\u0010\u001c\u001a\u00020\u0006*\u00020\u0007H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\u00020\u0006*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0018\u0010\r\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\t¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/ImplementAsErrorThrowingStubs;", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;", "partialLinkageSupport", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "(Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;)V", "delegatesToNothing", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getDelegatesToNothing", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "isAbstract", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;)Z", "isConcrete", "computeCustomization", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "overridableMember", "parent", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", "postProcessGeneratedFakeOverride", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "isAmbiguous", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;)Z", "isEligibleForPartialLinkage", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImplementAsErrorThrowingStubs implements IrUnimplementedOverridesStrategy {
    private final PartialLinkageSupportForLinker partialLinkageSupport;

    public ImplementAsErrorThrowingStubs(PartialLinkageSupportForLinker partialLinkageSupport) {
        Intrinsics.checkNotNullParameter(partialLinkageSupport, "partialLinkageSupport");
        this.partialLinkageSupport = partialLinkageSupport;
    }

    @Override // org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy
    public <S extends IrSymbol, T extends IrOverridableDeclaration<S>> IrUnimplementedOverridesStrategy.Customization computeCustomization(T overridableMember, IrClass parent) {
        Intrinsics.checkNotNullParameter(overridableMember, "overridableMember");
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (isAbstract(overridableMember) && isConcrete(parent) && isEligibleForPartialLinkage(parent) && !getDelegatesToNothing(parent)) {
            return new IrUnimplementedOverridesStrategy.Customization(PartiallyLinkedDeclarationOrigin.UNIMPLEMENTED_ABSTRACT_CALLABLE_MEMBER, parent.getModality());
        }
        return IrUnimplementedOverridesStrategy.Customization.INSTANCE.getNO();
    }

    @Override // org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy
    public <S extends IrSymbol, T extends IrOverridableDeclaration<S>> void postProcessGeneratedFakeOverride(T overridableMember, IrClass parent) {
        Intrinsics.checkNotNullParameter(overridableMember, "overridableMember");
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (isEligibleForPartialLinkage(parent) && isAmbiguous(overridableMember)) {
            postProcessGeneratedFakeOverride$mark(overridableMember);
            if (overridableMember instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) overridableMember;
                IrSimpleFunction getter = irProperty.getGetter();
                if (getter != null) {
                    postProcessGeneratedFakeOverride$mark(getter);
                }
                IrSimpleFunction setter = irProperty.getSetter();
                if (setter != null) {
                    postProcessGeneratedFakeOverride$mark(setter);
                }
            }
        }
    }

    private static final void postProcessGeneratedFakeOverride$mark(IrOverridableDeclaration<?> irOverridableDeclaration) {
        if (irOverridableDeclaration.getIsFakeOverride()) {
            irOverridableDeclaration.setOrigin(PartiallyLinkedDeclarationOrigin.AMBIGUOUS_NON_OVERRIDDEN_CALLABLE_MEMBER);
            irOverridableDeclaration.setFakeOverride(false);
        }
    }

    private final <S extends IrSymbol, T extends IrOverridableDeclaration<S>> boolean isAmbiguous(T t) {
        Set setCollectRealOverrides$default = IrFakeOverrideUtilsKt.collectRealOverrides$default(t, null, null, 3, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : setCollectRealOverrides$default) {
            if (!isAbstract((IrOverridableDeclaration) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = arrayList2;
        if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                IrSymbolOwner owner = ((IrOverridableDeclaration) it.next()).getSymbol().getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                IrDeclarationParent parent = ((IrDeclaration) owner).getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                if (irClass != null && !IrUtilsKt.isInterface(irClass)) {
                    return false;
                }
            }
        }
        return arrayList2.size() > 1;
    }

    private final boolean isEligibleForPartialLinkage(IrClass irClass) {
        return (irClass.getIsExternal() || this.partialLinkageSupport.shouldBeSkipped(irClass)) ? false : true;
    }

    private final boolean isAbstract(IrOverridableMember irOverridableMember) {
        return irOverridableMember.getModality() == Modality.ABSTRACT;
    }

    private final boolean isConcrete(IrClass irClass) {
        return (irClass.getModality() == Modality.ABSTRACT || irClass.getModality() == Modality.SEALED) ? false : true;
    }

    private final boolean getDelegatesToNothing(IrClass irClass) {
        List<IrDeclaration> declarations = irClass.getDeclarations();
        if ((declarations instanceof Collection) && declarations.isEmpty()) {
            return false;
        }
        for (IrDeclaration irDeclaration : declarations) {
            if ((irDeclaration instanceof IrField) && Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.DELEGATE.INSTANCE) && IrTypePredicatesKt.isNothing(((IrField) irDeclaration).getType())) {
                return true;
            }
        }
        return false;
    }
}
