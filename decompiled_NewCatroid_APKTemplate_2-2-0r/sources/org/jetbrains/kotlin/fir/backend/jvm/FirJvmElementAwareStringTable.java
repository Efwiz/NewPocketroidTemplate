package org.jetbrains.kotlin.fir.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.serialization.FirElementAwareStringTable;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirJvmElementAwareStringTable.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\f\u0010\n\u001a\u00020\u000b*\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmElementAwareStringTable;", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "typeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;", "(Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;)V", "getLocalClassIdReplacement", "Lorg/jetbrains/kotlin/name/ClassId;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmElementAwareStringTable extends JvmStringTable implements FirElementAwareStringTable {
    private final Fir2IrComponents components;
    private final IrTypeMapper typeMapper;

    public /* synthetic */ FirJvmElementAwareStringTable(IrTypeMapper irTypeMapper, Fir2IrComponents fir2IrComponents, JvmNameResolver jvmNameResolver, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irTypeMapper, fir2IrComponents, (i & 4) != 0 ? null : jvmNameResolver);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirJvmElementAwareStringTable(IrTypeMapper typeMapper, Fir2IrComponents components, JvmNameResolver jvmNameResolver) {
        super(jvmNameResolver);
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(components, "components");
        this.typeMapper = typeMapper;
        this.components = components;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirElementAwareStringTable
    public ClassId getLocalClassIdReplacement(FirClass firClass) {
        ClassId localClassIdReplacement;
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        IrClass cachedIrClass = this.components.getClassifierStorage().getCachedIrClass(firClass);
        if (cachedIrClass == null || (localClassIdReplacement = getLocalClassIdReplacement(cachedIrClass)) == null) {
            throw new AssertionError("not a local class: " + firClass.getSymbol().getClassId());
        }
        return localClassIdReplacement;
    }

    private final ClassId getLocalClassIdReplacement(IrClass irClass) {
        IrDeclarationParent parent = irClass.getParent();
        if (parent instanceof IrClass) {
            ClassId classIdCreateNestedClassId = getLocalClassIdReplacement((IrClass) parent).createNestedClassId(irClass.getName());
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            return classIdCreateNestedClassId;
        }
        String internalName = IrTypeMappingKt.mapClass(this.typeMapper, irClass).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        FqName fqName = new FqName(StringsKt.replace$default(internalName, '/', '.', false, 4, (Object) null));
        return new ClassId(fqName.parent(), FqName.topLevel(fqName.shortName()), true);
    }
}
