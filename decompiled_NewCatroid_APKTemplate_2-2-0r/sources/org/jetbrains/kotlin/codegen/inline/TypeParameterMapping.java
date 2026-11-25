package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ReifiedTypeInliner.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B7\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0012R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMapping;", "KT", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "", ModuleXmlParser.TYPE, "asmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "signature", "", "isReified", "", "reificationArgument", "Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;", "reifiedTypeParametersUsages", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/org/objectweb/asm/Type;Ljava/lang/String;ZLorg/jetbrains/kotlin/codegen/inline/ReificationArgument;Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;)V", "getAsmType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "()Z", "getReificationArgument", "()Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;", "getReifiedTypeParametersUsages", "()Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "getSignature", "()Ljava/lang/String;", "getType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeParameterMapping<KT extends KotlinTypeMarker> {
    private final Type asmType;
    private final boolean isReified;
    private final ReificationArgument reificationArgument;
    private final ReifiedTypeParametersUsages reifiedTypeParametersUsages;
    private final String signature;
    private final KT type;

    public TypeParameterMapping(KT type, Type asmType, String signature, boolean z, ReificationArgument reificationArgument, ReifiedTypeParametersUsages reifiedTypeParametersUsages) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(asmType, "asmType");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(reifiedTypeParametersUsages, "reifiedTypeParametersUsages");
        this.type = type;
        this.asmType = asmType;
        this.signature = signature;
        this.isReified = z;
        this.reificationArgument = reificationArgument;
        this.reifiedTypeParametersUsages = reifiedTypeParametersUsages;
    }

    public final KT getType() {
        return this.type;
    }

    public final Type getAsmType() {
        return this.asmType;
    }

    public final String getSignature() {
        return this.signature;
    }

    /* JADX INFO: renamed from: isReified, reason: from getter */
    public final boolean getIsReified() {
        return this.isReified;
    }

    public final ReificationArgument getReificationArgument() {
        return this.reificationArgument;
    }

    public final ReifiedTypeParametersUsages getReifiedTypeParametersUsages() {
        return this.reifiedTypeParametersUsages;
    }
}
