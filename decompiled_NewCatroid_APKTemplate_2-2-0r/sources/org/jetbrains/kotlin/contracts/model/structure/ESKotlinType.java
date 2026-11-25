package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESKotlinType;", "Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toKotlinType", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESKotlinType extends ESType {
    private final KotlinType type;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ESKotlinType(KotlinType type) {
        super(null);
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
    }

    public final KotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.contracts.model.structure.ESType
    public KotlinType toKotlinType(KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        return this.type;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.model.structure.ESKotlinType");
        return Intrinsics.areEqual(this.type, ((ESKotlinType) other).type);
    }

    public int hashCode() {
        return this.type.hashCode();
    }
}
