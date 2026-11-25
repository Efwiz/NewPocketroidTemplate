package org.jetbrains.kotlin.resolve.constants;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0010\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/TypedArrayValue;", "Lorg/jetbrains/kotlin/resolve/constants/ArrayValue;", "value", "", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Ljava/util/List;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypedArrayValue extends ArrayValue {
    private final KotlinType type;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypedArrayValue(List<? extends ConstantValue<?>> value, final KotlinType type) {
        super(value, new Function1<ModuleDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.constants.TypedArrayValue.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(ModuleDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return type;
            }
        });
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
    }

    public final KotlinType getType() {
        return this.type;
    }
}
