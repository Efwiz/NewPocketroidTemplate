package org.jetbrains.kotlin.ir.backend.js.export;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bs\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007¢\u0006\u0002\u0010\u0011J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0007HÆ\u0003J\t\u0010 \u001a\u00020\u0007HÆ\u0003J\t\u0010!\u001a\u00020\u0007HÆ\u0003J\t\u0010\"\u001a\u00020\u0007HÆ\u0003J\t\u0010#\u001a\u00020\u0007HÆ\u0003J\t\u0010$\u001a\u00020\u0007HÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u000eHÆ\u0003J{\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u0007HÆ\u0001J\u0013\u0010'\u001a\u00020\u00072\b\u0010(\u001a\u0004\u0018\u00010)HÖ\u0003J\t\u0010*\u001a\u00020+HÖ\u0001J\t\u0010,\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0015R\u0011\u0010\f\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0015R\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedProperty;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "mutable", "", "isMember", "isStatic", "isAbstract", "isProtected", "isField", "irGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irSetter", "isOptional", "(Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;ZZZZZZLorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Z)V", "getIrGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getIrSetter", "()Z", "getMutable", "getName", "()Ljava/lang/String;", "getType", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExportedProperty extends ExportedDeclaration {
    private final IrFunction irGetter;
    private final IrFunction irSetter;
    private final boolean isAbstract;
    private final boolean isField;
    private final boolean isMember;
    private final boolean isOptional;
    private final boolean isProtected;
    private final boolean isStatic;
    private final boolean mutable;
    private final String name;
    private final ExportedType type;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final IrFunction getIrSetter() {
        return this.irSetter;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final boolean getIsOptional() {
        return this.isOptional;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ExportedType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getMutable() {
        return this.mutable;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsMember() {
        return this.isMember;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getIsStatic() {
        return this.isStatic;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getIsAbstract() {
        return this.isAbstract;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final boolean getIsProtected() {
        return this.isProtected;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final boolean getIsField() {
        return this.isField;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final IrFunction getIrGetter() {
        return this.irGetter;
    }

    public final ExportedProperty copy(String name2, ExportedType type, boolean mutable, boolean isMember, boolean isStatic, boolean isAbstract, boolean isProtected, boolean isField, IrFunction irGetter, IrFunction irSetter, boolean isOptional) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        return new ExportedProperty(name2, type, mutable, isMember, isStatic, isAbstract, isProtected, isField, irGetter, irSetter, isOptional);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportedProperty)) {
            return false;
        }
        ExportedProperty exportedProperty = (ExportedProperty) other;
        return Intrinsics.areEqual(this.name, exportedProperty.name) && Intrinsics.areEqual(this.type, exportedProperty.type) && this.mutable == exportedProperty.mutable && this.isMember == exportedProperty.isMember && this.isStatic == exportedProperty.isStatic && this.isAbstract == exportedProperty.isAbstract && this.isProtected == exportedProperty.isProtected && this.isField == exportedProperty.isField && Intrinsics.areEqual(this.irGetter, exportedProperty.irGetter) && Intrinsics.areEqual(this.irSetter, exportedProperty.irSetter) && this.isOptional == exportedProperty.isOptional;
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((this.name.hashCode() * 31) + this.type.hashCode()) * 31) + Boolean.hashCode(this.mutable)) * 31) + Boolean.hashCode(this.isMember)) * 31) + Boolean.hashCode(this.isStatic)) * 31) + Boolean.hashCode(this.isAbstract)) * 31) + Boolean.hashCode(this.isProtected)) * 31) + Boolean.hashCode(this.isField)) * 31;
        IrFunction irFunction = this.irGetter;
        int iHashCode2 = (iHashCode + (irFunction == null ? 0 : irFunction.hashCode())) * 31;
        IrFunction irFunction2 = this.irSetter;
        return ((iHashCode2 + (irFunction2 != null ? irFunction2.hashCode() : 0)) * 31) + Boolean.hashCode(this.isOptional);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ExportedProperty(name=");
        sb.append(this.name).append(", type=").append(this.type).append(", mutable=").append(this.mutable).append(", isMember=").append(this.isMember).append(", isStatic=").append(this.isStatic).append(", isAbstract=").append(this.isAbstract).append(", isProtected=").append(this.isProtected).append(", isField=").append(this.isField).append(", irGetter=").append(this.irGetter).append(", irSetter=").append(this.irSetter).append(", isOptional=").append(this.isOptional).append(')');
        return sb.toString();
    }

    public /* synthetic */ ExportedProperty(String str, ExportedType exportedType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, IrFunction irFunction, IrFunction irFunction2, boolean z7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, exportedType, (i & 4) != 0 ? true : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3, (i & 32) != 0 ? false : z4, (i & 64) != 0 ? false : z5, (i & 128) != 0 ? false : z6, (i & 256) != 0 ? null : irFunction, (i & 512) != 0 ? null : irFunction2, (i & 1024) != 0 ? false : z7);
    }

    public final String getName() {
        return this.name;
    }

    public final ExportedType getType() {
        return this.type;
    }

    public final boolean getMutable() {
        return this.mutable;
    }

    public final boolean isMember() {
        return this.isMember;
    }

    public final boolean isStatic() {
        return this.isStatic;
    }

    public final boolean isAbstract() {
        return this.isAbstract;
    }

    public final boolean isProtected() {
        return this.isProtected;
    }

    public final boolean isField() {
        return this.isField;
    }

    public final IrFunction getIrGetter() {
        return this.irGetter;
    }

    public final IrFunction getIrSetter() {
        return this.irSetter;
    }

    public final boolean isOptional() {
        return this.isOptional;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExportedProperty(String name2, ExportedType type, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, IrFunction irFunction, IrFunction irFunction2, boolean z7) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        this.name = name2;
        this.type = type;
        this.mutable = z;
        this.isMember = z2;
        this.isStatic = z3;
        this.isAbstract = z4;
        this.isProtected = z5;
        this.isField = z6;
        this.irGetter = irFunction;
        this.irSetter = irFunction2;
        this.isOptional = z7;
    }
}
