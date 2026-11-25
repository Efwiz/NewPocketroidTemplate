package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SuspendLambdaLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0019\u001a\u00020\tHÆ\u0003J3\u0010\u001a\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u000e2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ParameterInfo;", "", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "getField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "isUsed", "", "()Z", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class ParameterInfo {
    private final IrField field;
    private final boolean isUsed;
    private final Name name;
    private final IrDeclarationOrigin origin;
    private final IrType type;

    public static /* synthetic */ ParameterInfo copy$default(ParameterInfo parameterInfo, IrField irField, IrType irType, Name name2, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irField = parameterInfo.field;
        }
        if ((i & 2) != 0) {
            irType = parameterInfo.type;
        }
        if ((i & 4) != 0) {
            name2 = parameterInfo.name;
        }
        if ((i & 8) != 0) {
            irDeclarationOrigin = parameterInfo.origin;
        }
        return parameterInfo.copy(irField, irType, name2, irDeclarationOrigin);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrField getField() {
        return this.field;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final IrType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Name getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    public final ParameterInfo copy(IrField field, IrType type, Name name2, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return new ParameterInfo(field, type, name2, origin);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParameterInfo)) {
            return false;
        }
        ParameterInfo parameterInfo = (ParameterInfo) other;
        return Intrinsics.areEqual(this.field, parameterInfo.field) && Intrinsics.areEqual(this.type, parameterInfo.type) && Intrinsics.areEqual(this.name, parameterInfo.name) && Intrinsics.areEqual(this.origin, parameterInfo.origin);
    }

    public int hashCode() {
        IrField irField = this.field;
        return ((((((irField == null ? 0 : irField.hashCode()) * 31) + this.type.hashCode()) * 31) + this.name.hashCode()) * 31) + this.origin.hashCode();
    }

    public String toString() {
        return "ParameterInfo(field=" + this.field + ", type=" + this.type + ", name=" + this.name + ", origin=" + this.origin + ')';
    }

    public ParameterInfo(IrField irField, IrType type, Name name2, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.field = irField;
        this.type = type;
        this.name = name2;
        this.origin = origin;
        this.isUsed = irField != null;
    }

    public final IrField getField() {
        return this.field;
    }

    public final Name getName() {
        return this.name;
    }

    public final IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    public final IrType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isUsed, reason: from getter */
    public final boolean getIsUsed() {
        return this.isUsed;
    }
}
