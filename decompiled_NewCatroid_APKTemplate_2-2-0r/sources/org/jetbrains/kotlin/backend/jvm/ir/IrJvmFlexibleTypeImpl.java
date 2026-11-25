package org.jetbrains.kotlin.backend.jvm.ir;

import com.reandroid.apkeditor.Options;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.FlexibleTypeBoundsChecker;

/* JADX INFO: compiled from: IrJvmFlexibleType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0014\u0010\u0015\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ir/IrJvmFlexibleTypeImpl;", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrJvmFlexibleType;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "nullability", "", "mutability", Options.TYPE_RAW, "(Lorg/jetbrains/kotlin/ir/types/IrSimpleType;Lorg/jetbrains/kotlin/ir/IrBuiltIns;ZZZ)V", "getBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "lowerBound", "getLowerBound", "getMutability", "()Z", "getNullability", "getRaw", "upperBound", "getUpperBound", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class IrJvmFlexibleTypeImpl implements IrJvmFlexibleType {
    private final IrBuiltIns builtIns;
    private final IrSimpleType irType;
    private final boolean mutability;
    private final boolean nullability;
    private final boolean raw;

    public IrJvmFlexibleTypeImpl(IrSimpleType irType, IrBuiltIns builtIns, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.irType = irType;
        this.builtIns = builtIns;
        this.nullability = z;
        this.mutability = z2;
        this.raw = z3;
    }

    public final IrSimpleType getIrType() {
        return this.irType;
    }

    public final IrBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final boolean getNullability() {
        return this.nullability;
    }

    public final boolean getMutability() {
        return this.mutability;
    }

    public final boolean getRaw() {
        return this.raw;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrJvmFlexibleType
    public IrSimpleType getLowerBound() {
        IrClassSymbol mutableMapEntryClass;
        IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder(this.irType);
        if (this.nullability) {
            builder.setNullability(SimpleTypeNullability.NOT_SPECIFIED);
        }
        builder.setKotlinType(null);
        if (this.mutability) {
            IrClassifierSymbol classifier = builder.getClassifier();
            IrSymbolOwner owner = classifier != null ? classifier.getOwner() : null;
            IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
            if (irClass == null) {
                throw new IllegalStateException(("Mutability-flexible type's classifier is not a class: " + RenderIrElementKt.render$default(this.irType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            FlexibleTypeBoundsChecker flexibleTypeBoundsChecker = FlexibleTypeBoundsChecker.INSTANCE;
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass);
            Intrinsics.checkNotNull(fqNameWhenAvailable);
            FqName baseBoundFqNameByMutability = flexibleTypeBoundsChecker.getBaseBoundFqNameByMutability(fqNameWhenAvailable);
            if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.iterable)) {
                mutableMapEntryClass = this.builtIns.getMutableIterableClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.iterator)) {
                mutableMapEntryClass = this.builtIns.getMutableIteratorClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.listIterator)) {
                mutableMapEntryClass = this.builtIns.getMutableListIteratorClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.list)) {
                mutableMapEntryClass = this.builtIns.getMutableListClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.collection)) {
                mutableMapEntryClass = this.builtIns.getMutableCollectionClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.set)) {
                mutableMapEntryClass = this.builtIns.getMutableSetClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.map)) {
                mutableMapEntryClass = this.builtIns.getMutableMapClass();
            } else {
                if (!Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.mapEntry)) {
                    throw new IllegalStateException(("Mutability-flexible type with unknown classifier: " + RenderIrElementKt.render$default(this.irType, (DumpIrTreeOptions) null, 1, (Object) null) + ", FQ name: " + baseBoundFqNameByMutability).toString());
                }
                mutableMapEntryClass = this.builtIns.getMutableMapEntryClass();
            }
            builder.setClassifier(mutableMapEntryClass);
        }
        return IrSimpleTypeImplKt.buildSimpleType(builder);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrJvmFlexibleType
    public IrSimpleType getUpperBound() {
        IrClassSymbol mapEntryClass;
        IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder(this.irType);
        if (this.nullability) {
            builder.setNullability(SimpleTypeNullability.MARKED_NULLABLE);
        }
        if (this.mutability) {
            IrClassifierSymbol classifier = builder.getClassifier();
            IrSymbolOwner owner = classifier != null ? classifier.getOwner() : null;
            IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
            if (irClass == null) {
                throw new IllegalStateException(("Mutability-flexible type's classifier is not a class: " + RenderIrElementKt.render$default(this.irType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            FlexibleTypeBoundsChecker flexibleTypeBoundsChecker = FlexibleTypeBoundsChecker.INSTANCE;
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass);
            Intrinsics.checkNotNull(fqNameWhenAvailable);
            FqName baseBoundFqNameByMutability = flexibleTypeBoundsChecker.getBaseBoundFqNameByMutability(fqNameWhenAvailable);
            if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.iterable)) {
                mapEntryClass = this.builtIns.getIterableClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.iterator)) {
                mapEntryClass = this.builtIns.getIteratorClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.listIterator)) {
                mapEntryClass = this.builtIns.getListIteratorClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.list)) {
                mapEntryClass = this.builtIns.getListClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.collection)) {
                mapEntryClass = this.builtIns.getCollectionClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.set)) {
                mapEntryClass = this.builtIns.getSetClass();
            } else if (Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.map)) {
                mapEntryClass = this.builtIns.getMapClass();
            } else {
                if (!Intrinsics.areEqual(baseBoundFqNameByMutability, StandardNames.FqNames.mapEntry)) {
                    throw new IllegalStateException(("Mutability-flexible type with unknown classifier: " + RenderIrElementKt.render$default(this.irType, (DumpIrTreeOptions) null, 1, (Object) null) + ", FQ name: " + baseBoundFqNameByMutability).toString());
                }
                mapEntryClass = this.builtIns.getMapEntryClass();
            }
            builder.setClassifier(mapEntryClass);
        }
        if (this.raw) {
            int size = builder.getArguments().size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(IrStarProjectionImpl.INSTANCE);
            }
            builder.setArguments(arrayList);
        }
        builder.setKotlinType(null);
        return IrSimpleTypeImplKt.buildSimpleType(builder);
    }
}
