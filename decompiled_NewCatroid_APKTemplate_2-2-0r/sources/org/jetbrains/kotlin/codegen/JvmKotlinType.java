package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: codegenUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmKotlinType {
    private final KotlinType kotlinType;
    private final Type type;

    public JvmKotlinType(Type type, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.kotlinType = kotlinType;
    }

    public /* synthetic */ JvmKotlinType(Type type, KotlinType kotlinType, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(type, (i & 2) != 0 ? null : kotlinType);
    }

    public final KotlinType getKotlinType() {
        return this.kotlinType;
    }

    public final Type getType() {
        return this.type;
    }
}
